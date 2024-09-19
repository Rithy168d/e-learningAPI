package istad.co.eleariningapi.features.category;

import istad.co.eleariningapi.features.category.dto.CategoryCreateRequest;
import istad.co.eleariningapi.features.category.dto.CategoryResponse;
import istad.co.eleariningapi.features.category.dto.CategoryUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    //  inject service
    private final CategoryServices categoryServices;

    //  endpoint create new category
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CategoryResponse createNew(@Valid @RequestBody CategoryCreateRequest categoryCreateRequest) {
        return categoryServices.createNew(categoryCreateRequest);
    }

    //  endpoint findList
    @GetMapping
    Page<CategoryResponse> findList(@RequestParam(required = false, defaultValue = "0") int pageNumber,
                                    @RequestParam(required = false, defaultValue = "25") int pageSize) {
        return categoryServices.findList(pageNumber, pageSize);
    }

    //  endpoint find by alias
    @GetMapping("/{alias}")
    public CategoryResponse findByAlias(@PathVariable String alias) {
        return categoryServices.findByAlias(alias);
    }

    //  endpoint update by alias
    @PatchMapping("/{alias}")
    CategoryResponse updateByAlias(@PathVariable String alias,
                                   @RequestBody CategoryUpdateRequest categoryUpdateRequest){
        return categoryServices.updateByAlias(alias, categoryUpdateRequest);
    }

    // endpoint delete by alias
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{alias}")
    void deleteByAlias(@PathVariable("alias") String alias) {
        categoryServices.deleteByAlias(alias);
    }

}
