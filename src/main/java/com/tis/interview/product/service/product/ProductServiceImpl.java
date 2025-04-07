package com.tis.interview.product.service.product;

import com.tis.interview.product.model.dto.ProductDto;
import com.tis.interview.product.model.dto.response.PageResponse;
import com.tis.interview.product.exception.domain.ProductNotFoundException;
import com.tis.interview.product.model.Product;
import com.tis.interview.product.model.dto.response.PopularProductsResponse;
import com.tis.interview.product.repository.ExchangeRepository;
import com.tis.interview.product.repository.ProductRepository;
import com.tis.interview.product.service.exchange.PriceCalculator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.tis.interview.product.transformer.PageResponseTransformer.transformToPageResponse;
import static java.math.RoundingMode.HALF_UP;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ExchangeRepository exchangeRepository;
    private final ModelMapper mapper;
    private final PriceCalculator priceCalculator = new PriceCalculator(exchangeRepository);

    @Override
    public void createOrUpdateProduct(ProductDto request) {
        var requestEntity = mapper.map(request, Product.class);

        productRepository.findByCode(request.getProductCode())
                .ifPresentOrElse(
                        existingProduct -> updateProduct(existingProduct, requestEntity),
                        () -> createProduct(requestEntity)
                );
    }

    private void createProduct(Product product) {
        //TODO set owner of a product

        product.setCreatedAt(LocalDateTime.now());
        var createdProduct = productRepository.save(product);

        log.info("Created Product: {}", createdProduct);
    }

    @Transactional
    private void updateProduct(Product foundProduct, Product request) {
        //TODO add user permission verifier

        updateProductDetails(foundProduct, request);
        var updated = productRepository.save(foundProduct);
        log.info("Updated Product: {}", updated);
    }

    private void updateProductDetails(Product foundProduct, Product request) {
        foundProduct.setCode(request.getCode());
        foundProduct.setName(request.getName());
        foundProduct.setDescription(request.getDescription());
        foundProduct.setPrice(request.getPrice());
    }

    @Override
    public void deleteProduct(String productCode) {
        //TODO product should only be deleted by store owner or product owner
        var foundProduct = findProductByCodeOrThrow(productCode);
        productRepository.delete(foundProduct);
    }

    @Override
    public PageResponse<PopularProductsResponse> getPopularProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return transformToPageResponse(productRepository.findTopPopularProducts(pageable),
                pp -> PopularProductsResponse.builder()
                        .name(pp.getProductName())
                        .averageRating(pp.getProductAverageRating())
                        .build()
        );
    }

    @Override
    public PageResponse<ProductDto> getAllDisplayableProducts(int page, int size,
                                                              String productName, String productCode) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Product> filteredProducts = productRepository
                .findAllByFilters(productName, productCode, pageable);

        var transformed = transformToPageResponse(filteredProducts, ProductDto.class);
        transformed.getContent()
                .forEach(p -> p.setProductPriceUsd(calculateUsdPrice(p.getProductPrice())));

        return transformed;
    }

    public ProductDto findProductByCode(String productCode) {
        var foundProduct = findProductByCodeOrThrow(productCode);
        var found = mapper.map(foundProduct, ProductDto.class);
        found
                .setProductPriceUsd(calculateUsdPrice(found.getProductPrice()));
        return found;
    }

    private Product findProductByCodeOrThrow(String productCode) {
        return productRepository.findByCode(productCode)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private BigDecimal calculateUsdPrice(BigDecimal eurPrice){
        return priceCalculator.calculatePriceUSD(eurPrice, 2, HALF_UP);
    }
}