<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <meta name="description" />
    <meta name="author" />
    <title>Cap nhat nguoi dung</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script>
        $(document).ready(() => {
            $(".role-checkbox").change(function () {
                $(".role-checkbox").not(this).prop("checked", false);
                $("#selectedRole").val($(this).is(":checked") ? $(this).val() : "");
            });
        });
    </script>
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>

<body class="sb-nav-fixed">
    <jsp:include page="../layout/header.jsp" />
    <div id="layoutSidenav">
        <jsp:include page="../layout/sidebar.jsp" />
        <div id="layoutSidenav_content">
            <main>
                <div class="container-fluid px-4">
                    <h1 class="mt-4">Cap nhat nguoi dung</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="/admin">Bang dieu khien</a></li>
                        <li class="breadcrumb-item active">Nguoi dung</li>
                    </ol>
                    <div class="container mt-5">
                        <div class="row">
                            <div class="col-md-6 col-12 mx-auto">
                                <h3>Cap nhat nguoi dung</h3>
                                <hr />
                                <form:form method="post" action="/admin/user/update" modelAttribute="newUser">
                                    <div class="mb-3" style="display: none;">
                                        <label class="form-label">Id:</label>
                                        <form:input type="text" class="form-control" path="id" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Email:</label>
                                        <form:input type="email" class="form-control" path="email" disabled="true" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">So dien thoai:</label>
                                        <form:input type="text" class="form-control" path="phone" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Ten day du:</label>
                                        <form:input type="text" class="form-control" path="fullName" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Dia chi:</label>
                                        <form:input type="text" class="form-control" path="address" />
                                    </div>
                                    <div class="mb-3">
                                        <label class="form-label">Phan quyen:</label>
                                        <form:hidden path="role.name" id="selectedRole" />
                                        <div class="form-check">
                                            <input class="form-check-input role-checkbox" type="checkbox" value="ADMIN" id="roleAdmin"
                                                <c:if test="${newUser.role.name == 'ADMIN'}">checked</c:if> />
                                            <label class="form-check-label" for="roleAdmin">ADMIN</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input role-checkbox" type="checkbox" value="STAFF" id="roleStaff"
                                                <c:if test="${newUser.role.name == 'STAFF'}">checked</c:if> />
                                            <label class="form-check-label" for="roleStaff">STAFF</label>
                                        </div>
                                        <div class="form-check">
                                            <input class="form-check-input role-checkbox" type="checkbox" value="USER" id="roleUser"
                                                <c:if test="${newUser.role.name == 'USER'}">checked</c:if> />
                                            <label class="form-check-label" for="roleUser">USER</label>
                                        </div>
                                    </div>

                                    <button type="submit" class="btn btn-primary">Cap nhat</button>
                                </form:form>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
            <jsp:include page="../../client/layout/chat-bot.jsp" />
            <jsp:include page="../layout/footer.jsp" />
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        crossorigin="anonymous"></script>
    <script src="/js/scripts.js"></script>
</body>

</html>
