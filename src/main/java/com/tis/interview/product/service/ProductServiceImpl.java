package com.tis.interview.product.service;

import com.tis.interview.product.dto.ProductDto;
import com.tis.interview.product.model.Product;
import com.tis.interview.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Override
    public void createOrUpdateProduct(ProductDto request){
        var entity = mapper.map(request, Product.class);

        productRepository.findByCode(request.getProductCode())
                .ifPresentOrElse(
                        existingProduct -> updateProduct(entity),
                        () -> createProduct(entity)
                );
    }

    private void createProduct(Product product){

    }

    @Transactional
    private void updateProduct(Product product){

    }
}
