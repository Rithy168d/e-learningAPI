package istad.co.eleariningapi.security;

import istad.co.eleariningapi.domain.User;
import istad.co.eleariningapi.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServicesImpl implements UserDetailsService {

    //  inject userRepository
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //  validate user
        User user = userRepository
                .findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("User has been not found"));

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        return customUserDetails;
    }
}
