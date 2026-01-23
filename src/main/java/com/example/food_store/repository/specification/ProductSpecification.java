package com.example.food_store.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import com.example.food_store.domain.Product;

public class ProductSpecification {
    public static Specification<Product> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    // case 1
    public static Specification<Product> minPrice(int price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get("price"), price);
    }

    // case 2
    public static Specification<Product> maxPrice(int price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get("price"), price);
    }

    // case4

    public static Specification<Product> matchListcustomerTarget(List<String> customerTarget) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("customerTarget"))
                .value(customerTarget);
    }

    // case4
    public static Specification<Product> matchListType(List<String> type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("type")).value(type);
    }

    // case4
    public static Specification<Product> matchListTarget(List<String> target) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("target")).value(target);
    }

    // case5

    public static Specification<Product> matchPrice(int min, int max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.gt(root.get("price"), min),
                criteriaBuilder.le(root.get("price"), max));
    }

    // case6
    public static Specification<Product> matchMultiplePrice(int min, int max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(
                root.get("price"), min, max);
    }

}
