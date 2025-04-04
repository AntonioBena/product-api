package com.tis.interview.product.transformer;

import com.tis.interview.product.model.dto.response.PageResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class PageResponseTransformer {
    private static final ModelMapper mapper = new ModelMapper();

    private PageResponseTransformer() {
    }

    public static <E, D> PageResponse<D> transformToPageResponse(Page<E> entities, Class<D> dtoClass) {
        return transformToPageResponse(entities, entity -> mapper.map(entity, dtoClass));
    }

    private static <E, D> PageResponse<D> transformToPageResponse(
            Page<E> entities, Function<E, D> dtoMapper) {

        List<D> dtos = entities.stream()
                .map(dtoMapper)
                .toList();
        return new PageResponse<>(
                dtos,
                entities.getNumber(),
                entities.getSize(),
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.isFirst()
        );
    }
}