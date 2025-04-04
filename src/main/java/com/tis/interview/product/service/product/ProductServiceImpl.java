package com.tis.interview.product.service.product;

import com.tis.interview.product.model.dto.ProductDto;
import com.tis.interview.product.model.dto.response.PageResponse;
import com.tis.interview.product.exception.domain.ProductNotFoundException;
import com.tis.interview.product.model.Product;
import com.tis.interview.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.tis.interview.product.transformer.PageResponseTransformer.transformToPageResponse;

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

        product.setCreatedAt(LocalDateTime.now());
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

    @Override
    public PageResponse<ProductDto> getAllDisplayableProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return transformToPageResponse(productRepository.findAllBy(pageable), ProductDto.class);
    }

    public ProductDto findProductByCode(String productCode){
        var foundProduct = findProductByCodeOrThrow(productCode);
        return mapper.map(foundProduct, ProductDto.class);
    }

    private Product findProductByCodeOrThrow(String productCode){
        return productRepository.findByCode(productCode)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }
}