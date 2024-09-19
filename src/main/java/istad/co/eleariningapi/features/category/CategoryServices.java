package istad.co.eleariningapi.features.category;


import istad.co.eleariningapi.features.category.dto.CategoryCreateRequest;
import istad.co.eleariningapi.features.category.dto.CategoryResponse;
import istad.co.eleariningapi.features.category.dto.CategoryUpdateRequest;
import org.springframework.data.domain.Page;

public interface CategoryServices {

    /**
     * Create new category
     * @param categoryCreateRequest {@link CategoryCreateRequest}
     * @return {@link CategoryResponse}
     */
    CategoryResponse createNew(CategoryCreateRequest categoryCreateRequest);

    /**
     * Find all category
     * @param pageNumber is current page request from client
     * @param pageSize is size of record
     * @return {@link Page<CategoryResponse>}
     */
    Page<CategoryResponse> findList(int pageNumber, int pageSize);

    /**
     * Find by alias
     * @param alias is required
     * @return {@link CategoryResponse}
     */
    public CategoryResponse findByAlias(String alias);

    /**
     * Update category
     * @param alias is required
     * @param categoryUpdateRequest {@link CategoryUpdateRequest}
     * @return {@link CategoryResponse}
     */
    CategoryResponse updateByAlias(String alias, CategoryUpdateRequest categoryUpdateRequest);

    /**
     * Delete category by alias
     * @param alias is required
     */
    void deleteByAlias(String alias);
    
}
