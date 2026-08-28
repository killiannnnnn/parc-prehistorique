package net.ent.etnc.parc_prehistorique.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class ControllerUtils {

    public static Pageable getPageable(Integer page, Integer size, String sort) {
        String[] sortParams = sort.split(",");

        List<Sort.Order> orders = new ArrayList<>();

        for (String sortParam : sortParams) {
            String[] parts = sortParam.split(":");

            String property = parts[0].trim();

            if (parts.length > 1) {
                Sort.Direction direction =
                        Sort.Direction.fromString(parts[1].trim());

                orders.add(new Sort.Order(direction, property));
            } else {
                orders.add(Sort.Order.asc(property));
            }
        }

        return PageRequest.of(page - 1, size, Sort.by(orders));
    }

}
