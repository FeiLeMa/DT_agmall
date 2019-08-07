package com.alag.agmall.business.module.product.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.core.vo.ProductDetailVo;
import com.alag.agmall.business.module.product.api.model.Product;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    ServerResponse saveProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> getDetail(Integer productId);

    ServerResponse<PageInfo> list(int pageNum, int pageSize);

    ServerResponse<PageInfo> getProductByIdAndName(int pageNum, int pageSize, String productName, Integer productId);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getListByKeyword(String keyword, Integer categoryId, Integer pageNum, Integer pageSize);

    ServerResponse<Product> getPOJOProductById(Integer id);

    ServerResponse<Integer> getStockById(Integer id);

    ServerResponse<Integer> modifyProduct(Product product);
}
