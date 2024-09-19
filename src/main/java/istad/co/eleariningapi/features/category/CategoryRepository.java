package istad.co.eleariningapi.features.category;

import istad.co.eleariningapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Integer>{

    //  SELECT EXIST(SELECT * FROM categories WHERE alias = ?)
    boolean existsByAlias(String alias);

    //  SELECT * FROM categories WHERE alias = ?
    Optional<Category> findByAlias(String alias);


    
}
