package istad.co.eleariningapi.mapper;

import istad.co.eleariningapi.domain.Category;
import istad.co.eleariningapi.features.category.dto.CategoryCreateRequest;
import istad.co.eleariningapi.features.category.dto.CategoryResponse;
import istad.co.eleariningapi.features.category.dto.CategoryUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //    Map Category domain model to CategoryResponse DTO
    CategoryResponse toCategoryResponse(Category category);

    //    Map from Category domain model to CategoryCreateRequest DTO
    Category fromCategoryCreateRequest(CategoryCreateRequest categoryCreateRequest);

    // Partially map
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromCategoryUpdateRequest(CategoryUpdateRequest categoryUpdateRequest,
                                   @MappingTarget Category category);

}
