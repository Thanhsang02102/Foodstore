package com.example.food_store.controller.client;

import org.springframework.web.bind.annotation.*;

import com.example.food_store.controller.BaseController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/gemini-proxy")
public class GeminiController extends BaseController {
    @Value("${api-gemini:}")
    private String apiUrl;

    @PostMapping
    public ResponseEntity<String> proxyToGemini(@RequestBody(required = false) Map<String, Object> requestMap) {
        try {
            log.info("=== Request to /gemini-proxy ===");

            // Xử lý request body an toàn
            String requestBody = "{}";
            try {
                if (requestMap == null || requestMap.isEmpty()) {
                    log.warn("Request body is null or empty - returning default message");
                    return ResponseEntity.ok()
                            .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                            .body("Xin chào! Tôi là chatbot tư vấn về thực phẩm. Vui lòng nhập câu hỏi của bạn.");
                }

                // Chuyển Map thành JSON string
                ObjectMapper objectMapper = new ObjectMapper();
                requestBody = objectMapper.writeValueAsString(requestMap);
                if (requestBody == null || requestBody.trim().isEmpty()) {
                    requestBody = "{}";
                }
                log.info("Request body converted, length: {}", requestBody.length());
            } catch (Exception e) {
                log.error("Lỗi chuyển đổi Map thành JSON: {}", e.getMessage());
                requestBody = "{}";
            }

            // Kiểm tra API URL an toàn (không log nếu null để tránh exception)
            boolean apiUrlConfigured = false;
            try {
                apiUrlConfigured = (apiUrl != null && !apiUrl.isEmpty() && !apiUrl.equals("your-gemini-api-url"));
                log.info("API URL configured: {}", apiUrlConfigured ? "Yes" : "No");
            } catch (Exception e) {
                log.warn("Lỗi kiểm tra API URL: {}", e.getMessage());
                apiUrlConfigured = false;
            }

            // Nếu API URL chưa được cấu hình, trả về default response
            if (!apiUrlConfigured) {
                log.info("API Gemini URL chưa được cấu hình. Sử dụng phản hồi mặc định.");
                String defaultResponse = getDefaultResponse(requestBody);
                return ResponseEntity.ok()
                        .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                        .body(defaultResponse != null ? defaultResponse
                                : "Xin chào! Tôi là chatbot tư vấn về thực phẩm.");
            }

            // Gọi API Gemini
            try {
                String response = sendRequestToGeminiAPI(requestBody);
                return ResponseEntity.ok()
                        .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                        .body(response);
            } catch (Exception e) {
                // Catch mọi exception và trả về default response
                log.error("Lỗi khi gọi API Gemini: {}", e.getMessage());
                String defaultResponse = getDefaultResponse(requestBody);
                return ResponseEntity.ok()
                        .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                        .body(defaultResponse != null ? defaultResponse
                                : "Xin chào! Tôi là chatbot tư vấn về thực phẩm.");
            }
        } catch (Exception e) {
            // Catch mọi exception ở level ngoài cùng để đảm bảo luôn trả về response hợp lệ
            log.error("=== LỖI TỔNG QUÁT TRONG proxyToGemini ===", e);
            log.error("Exception type: {}", e.getClass().getName());
            log.error("Exception message: {}", e.getMessage());
            // Luôn trả về response OK để tránh Spring Security redirect
            return ResponseEntity.ok()
                    .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                    .body("Xin chào! Tôi là chatbot tư vấn về thực phẩm. Hiện tại có lỗi xảy ra. Vui lòng thử lại sau.");
        }
    }

    private String sendRequestToGeminiAPI(String requestBody) throws IOException {
        // Kiểm tra apiUrl trước khi tạo URL
        if (apiUrl == null || apiUrl.isEmpty() || apiUrl.equals("your-gemini-api-url")) {
            throw new IOException("API URL chưa được cấu hình");
        }

        HttpURLConnection conn = null;
        int maxRedirects = 5; // Giới hạn số lần redirect
        int redirectCount = 0;
        String currentUrl = apiUrl;

        try {
            while (redirectCount < maxRedirects) {
                String line;
                URL url = new URL(currentUrl);
                conn = (HttpURLConnection) url.openConnection();

                // Cấu hình connection
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Java)");
                conn.setConnectTimeout(10000); // 10 seconds timeout
                conn.setReadTimeout(30000); // 30 seconds read timeout
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setInstanceFollowRedirects(false); // Tắt auto redirect để xử lý thủ công

                // Ghi dữ liệu vào output stream TRƯỚC khi gọi getResponseCode()
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                    os.flush();
                }

                // Sau khi ghi xong, mới gọi getResponseCode()
                int responseCode = conn.getResponseCode();

                // Xử lý redirect (3xx)
                if (responseCode >= 300 && responseCode < 400) {
                    String location = conn.getHeaderField("Location");
                    if (location != null && !location.isEmpty()) {
                        redirectCount++;
                        log.info("Redirect {}: {} -> {}", redirectCount, currentUrl, location);
                        conn.disconnect();
                        // Xử lý relative URL
                        if (location.startsWith("/")) {
                            URL baseUrl = new URL(currentUrl);
                            currentUrl = baseUrl.getProtocol() + "://" + baseUrl.getHost() +
                                    (baseUrl.getPort() != -1 ? ":" + baseUrl.getPort() : "") + location;
                        } else {
                            currentUrl = location;
                        }
                        continue; // Thử lại với URL mới
                    } else {
                        log.error("Redirect không có Location header: {}", responseCode);
                        throw new IOException("Redirect không hợp lệ: " + responseCode);
                    }
                }

                // Xử lý lỗi (4xx, 5xx)
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    // Đọc error stream nếu có (phải đọc TRƯỚC khi đọc input stream)
                    StringBuilder errorResponse = new StringBuilder();
                    InputStream errorStream = conn.getErrorStream();
                    if (errorStream != null) {
                        try (BufferedReader errorReader = new BufferedReader(
                                new InputStreamReader(errorStream, StandardCharsets.UTF_8))) {
                            String errorLine;
                            while ((errorLine = errorReader.readLine()) != null) {
                                errorResponse.append(errorLine).append("\n");
                            }
                        }
                    }

                    // Nếu không có error stream, thử đọc response message
                    String responseMessage = conn.getResponseMessage();
                    log.error("API Gemini trả về lỗi: {} {} - {}", responseCode,
                            responseMessage != null ? responseMessage : "",
                            errorResponse.toString());
                    throw new IOException("API trả về lỗi: " + responseCode +
                            (responseMessage != null ? " " + responseMessage : "") +
                            " - " + errorResponse.toString());
                }

                // Đọc response từ input stream (chỉ khi response code là 200)
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder responseStr = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        responseStr.append(line);
                    }
                    return extractTextFromResponse(responseStr.toString());
                }
            }

            // Nếu vượt quá số lần redirect cho phép
            throw new IOException("Vượt quá số lần redirect cho phép: " + maxRedirects);

        } catch (MalformedURLException e) {
            log.error("URL không hợp lệ: {}", currentUrl, e);
            throw new IOException("URL không hợp lệ: " + currentUrl, e);
        } catch (SocketTimeoutException e) {
            log.error("Timeout khi kết nối API Gemini", e);
            throw new IOException("Timeout khi kết nối API", e);
        } catch (IOException e) {
            log.error("Lỗi kết nối API Gemini: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Lỗi không xác định khi xử lý request Gemini", e);
            throw new IOException("Lỗi không xác định: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String extractTextFromResponse(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            // Trích xuất phần "text" từ JSON
            String text = rootNode.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();
            if (text == null || text.isEmpty()) {
                return "Xin lỗi, tôi không thể tạo phản hồi cho câu hỏi này. Vui lòng thử lại với câu hỏi khác.";
            }
            return text;
        } catch (Exception e) {
            log.error("Lỗi xử lý JSON response: {}", e.getMessage());
            return "Xin lỗi, có lỗi xảy ra khi xử lý phản hồi. Vui lòng thử lại sau.";
        }
    }

    private String getDefaultResponse(String requestBody) {
        try {
            if (requestBody == null || requestBody.trim().isEmpty()) {
                return "Xin chào! Tôi là chatbot tư vấn về thực phẩm. Vui lòng nhập câu hỏi của bạn.";
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(requestBody);
            String userQuestion = rootNode.path("contents")
                    .path(0)
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText("");

            if (userQuestion == null || userQuestion.isEmpty()) {
                return "Xin chào! Tôi là chatbot tư vấn về thực phẩm. Vui lòng nhập câu hỏi của bạn.";
            }

            // Phản hồi mặc định dựa trên từ khóa
            if (userQuestion.toLowerCase().contains("giảm cân") || userQuestion.toLowerCase().contains("diet")) {
                return "Để giảm cân hiệu quả, bạn nên:\n\n" +
                        "1. Ăn nhiều rau xanh và trái cây\n" +
                        "2. Uống đủ nước (2-3 lít/ngày)\n" +
                        "3. Hạn chế đồ ngọt và thức ăn nhanh\n" +
                        "4. Tập thể dục đều đặn\n" +
                        "5. Ngủ đủ giấc (7-8 tiếng/ngày)\n\n" +
                        "Bạn có thể tìm các sản phẩm hỗ trợ giảm cân tại cửa hàng của chúng tôi!";
            } else if (userQuestion.toLowerCase().contains("tăng cân")
                    || userQuestion.toLowerCase().contains("weight gain")) {
                return "Để tăng cân lành mạnh, bạn nên:\n\n" +
                        "1. Ăn đủ 3 bữa chính và 2-3 bữa phụ\n" +
                        "2. Tăng cường protein (thịt, cá, trứng, sữa)\n" +
                        "3. Bổ sung tinh bột (gạo, bánh mì, khoai tây)\n" +
                        "4. Tập thể dục để tăng cơ\n" +
                        "5. Ngủ đủ giấc để cơ thể phục hồi\n\n" +
                        "Chúng tôi có nhiều sản phẩm giàu dinh dưỡng phù hợp cho bạn!";
            } else if (userQuestion.toLowerCase().contains("rau") || userQuestion.toLowerCase().contains("vegetable")) {
                return "Rau xanh rất tốt cho sức khỏe vì:\n\n" +
                        "1. Giàu vitamin và khoáng chất\n" +
                        "2. Nhiều chất xơ, tốt cho tiêu hóa\n" +
                        "3. Ít calo, phù hợp giảm cân\n" +
                        "4. Chứa chất chống oxy hóa\n\n" +
                        "Cửa hàng chúng tôi có nhiều loại rau tươi ngon từ Đà Lạt!";
            } else if (userQuestion.toLowerCase().contains("trái cây")
                    || userQuestion.toLowerCase().contains("fruit")) {
                return "Trái cây mang lại nhiều lợi ích:\n\n" +
                        "1. Giàu vitamin C, tăng sức đề kháng\n" +
                        "2. Nhiều chất xơ, tốt cho tiêu hóa\n" +
                        "3. Chứa chất chống oxy hóa\n" +
                        "4. Cung cấp nước tự nhiên cho cơ thể\n\n" +
                        "Chúng tôi có nhiều loại trái cây tươi ngon, nhập khẩu và trong nước!";
            } else if (userQuestion.toLowerCase().contains("protein") || userQuestion.toLowerCase().contains("đạm")) {
                return "Protein rất quan trọng cho cơ thể:\n\n" +
                        "1. Xây dựng và phục hồi cơ bắp\n" +
                        "2. Tăng cường trao đổi chất\n" +
                        "3. Hỗ trợ giảm cân hiệu quả\n" +
                        "4. Tăng cảm giác no\n\n" +
                        "Cửa hàng có nhiều thực phẩm giàu protein: thịt, cá, trứng, đậu phụ!";
            } else {
                return "Xin chào! Tôi là chatbot tư vấn về thực phẩm và dinh dưỡng.\n\n" +
                        "Tôi có thể giúp bạn:\n" +
                        "- Tư vấn về chế độ ăn uống\n" +
                        "- Thông tin về sản phẩm\n" +
                        "- Lời khuyên dinh dưỡng\n\n" +
                        "Hiện tại chatbot đang trong chế độ demo. Vui lòng liên hệ với chúng tôi để được tư vấn chi tiết hơn!\n\n"
                        +
                        "Bạn có thể xem các sản phẩm tại: /products";
            }
        } catch (Exception e) {
            log.error("Lỗi xử lý request body: {}", e.getMessage());
            return "Xin chào! Tôi là chatbot tư vấn về thực phẩm. Hiện tại chatbot đang trong chế độ demo. " +
                    "Vui lòng liên hệ với chúng tôi để được tư vấn chi tiết hơn!";
        }
    }
}
