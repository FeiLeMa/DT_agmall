package com.alag.agmall.business.module.category.server.service;

import com.alag.agmall.business.core.common.ServerResponse;
import com.alag.agmall.business.module.category.api.model.Category;

import java.util.List;

public interface CategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse modifyCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getParallelCategoryByParentId(Integer categoryId);

    ServerResponse<List<Integer>> getAllDeepChildId(Integer categoryId);

    ServerResponse<Category> getCategoryById(Integer categoryId);
}
