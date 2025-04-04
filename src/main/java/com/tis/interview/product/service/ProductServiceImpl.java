package com.tis.interview.product.service;

import com.tis.interview.product.dto.ProductDto;
import com.tis.interview.product.model.Product;
import com.tis.interview.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper mapper;

    @Override
    public void createOrUpdateProduct(ProductDto request){
        var requestEntity = mapper.map(request, Product.class);

        productRepository.findByCode(request.getProductCode())
                .ifPresentOrElse(
                        existingProduct -> updateProduct(existingProduct, requestEntity),
                        () -> createProduct(requestEntity)
                );
    }

    private void createProduct(Product product){
        //TODO set owner of a product

        var createdProduct = productRepository.save(product);

        log.info("Created Product: {}", createdProduct);
    }

    @Transactional
    private void updateProduct(Product foundProduct, Product request){
        //TODO add user permission verifier

        updateProductDetails(foundProduct, request);
        var updated = productRepository.save(foundProduct);
        log.info("Updated Product: {}", updated);
    }

    private void updateProductDetails(Product foundProduct, Product request){
        foundProduct.setCode(request.getCode());
        foundProduct.setName(request.getName());
        foundProduct.setDescription(request.getDescription());
        foundProduct.setPrice(request.getPrice());
    }

    @Override
    public void deleteProduct(String productCode) {
        var foundProduct = findProductByCodeOrThrow(productCode);
        productRepository.delete(foundProduct);
    }


    private Product findProductByCodeOrThrow(String productCode){
        return productRepository.findByCode(productCode)
                .orElseThrow(()-> new RuntimeException("product not found"));
    }

}