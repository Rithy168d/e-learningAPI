package istad.co.eleariningapi.features.category;

import istad.co.eleariningapi.domain.Category;
import istad.co.eleariningapi.features.category.dto.CategoryCreateRequest;
import istad.co.eleariningapi.features.category.dto.CategoryResponse;
import istad.co.eleariningapi.features.category.dto.CategoryUpdateRequest;
import istad.co.eleariningapi.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CategoryServicesImpl implements CategoryServices {

    //  inject categoryRepository
    private final CategoryRepository categoryRepository;

    //  inject categoryMapper
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createNew(CategoryCreateRequest categoryCreateRequest) {

        //  validation alias is exist
        if (categoryRepository.existsByAlias(categoryCreateRequest.alias())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "alias already exists");
        }

        //  transfer DTO to Domain Model
        Category category = categoryMapper.fromCategoryCreateRequest(categoryCreateRequest);

        //  system generate
        category.setStatus(true);

        //  save category into database and get latest data back
        category = categoryRepository.save(category);

        //  transfer Domain Model to DTO
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public Page<CategoryResponse> findList(int pageNumber, int pageSize) {

        //  sort record by id
        Sort sort = Sort.by(Sort.Direction.DESC, "id");

        //  page request from client
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // find record after client request
        Page<Category> categories = categoryRepository.findAll(pageRequest);

        return categories.map(categoryMapper::toCategoryResponse);
    }

    @Override
    public CategoryResponse findByAlias(String alias) {

        //  validation alias
        Category category = categoryRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Category alias has not been found"
        ));

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateByAlias(String alias, CategoryUpdateRequest categoryUpdateRequest) {

        //  validation alias
        Category category = categoryRepository
                .findByAlias(alias)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category alias has not been found"
                ));

        categoryMapper.fromCategoryUpdateRequest(categoryUpdateRequest, category);

        //  update record
        category = categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public void deleteByAlias(String alias) {

        //  validate alias
        Category category = categoryRepository.findByAlias(alias)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category alias has not been found"
                ));

        //  delete record from table database
        categoryRepository.delete(category);
    }
}
