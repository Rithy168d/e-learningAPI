package istad.co.eleariningapi.init;

import istad.co.eleariningapi.domain.Category;
import istad.co.eleariningapi.domain.Role;
import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.features.category.CategoryRepository;
import istad.co.eleariningapi.features.user.RoleRepository;
import istad.co.eleariningapi.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {

    //  injection userRepository
    private final UserRepository userRepository;

    //  injection categoryRepository
    private final CategoryRepository categoryRepository;

    //  injection roleRepository
    private final RoleRepository roleRepository;

    //  injection bean passwordEncoder
    private final PasswordEncoder passwordEncoder;



    @PostConstruct
    void init(){
        if (userRepository.count() == 0){

            Role user = new Role();
            user.setName("USER");

            Role customer = new Role();
            customer.setName("CUSTOMER");

            Role manager = new Role();
            manager.setName("MANAGER");

            Role admin = new Role();
            admin.setName("ADMIN");

            roleRepository.saveAll(List.of(user, customer, manager, admin));

            User user1 = new User();
            user1.setUuid(UUID.randomUUID().toString());
            user1.setName("Reach");
            user1.setEmail("reach@gmail.com");
            user1.setPassword(passwordEncoder.encode("Reach123"));
            user1.setProfile("info1-img.png");
            user1.setPin("1234");
            user1.setIsDeleted(false);
            user1.setIsVerified(false);
            user1.setRoles(List.of(user, admin));

            User user2 = new User();
            user2.setUuid(UUID.randomUUID().toString());
            user2.setName("Rithy");
            user2.setEmail("rithy@gmail.com");
            user2.setPassword(passwordEncoder.encode("Rithy123"));
            user2.setProfile("info2-img.png");
            user2.setPin("1234");
            user2.setIsDeleted(false);
            user2.setIsVerified(false);
            user2.setRoles(List.of(user, manager));

            User user3 = new User();
            user3.setUuid(UUID.randomUUID().toString());
            user3.setName("Dodo");
            user3.setEmail("dodo@gmail.com");
            user3.setPassword(passwordEncoder.encode("Dodo123"));
            user3.setProfile("info3-img.png");
            user3.setPin("1234");
            user3.setIsDeleted(false);
            user3.setIsVerified(false);
            user3.setRoles(List.of(user, customer));

            userRepository.saveAll(List.of(user1, user2, user3));
        }

        if (categoryRepository.count() == 0){
            Category web = new Category();
            web.setAlias("web-development");
            web.setName("Web Development");
            web.setDescription("Learn about creating web");
            web.setStatus(true);

            Category app = new Category();
            app.setAlias("mobile-development");
            app.setName("Mobile Development");
            app.setDescription("Learn about creating app");
            app.setStatus(true);

            categoryRepository.saveAll(List.of(web, app));
        }



    }


}
