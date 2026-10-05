package com.example.secureFort.ser;



import java.util.Locale;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.secureFort.model.RegistrationRequest;
import com.example.secureFort.model.UsernameAlreadyExistsException;
import com.example.secureFort.model.FortUser;
import com.example.secureFort.repo.FortUserRepository;

@Service
public class RegistrationService {

    private final FortUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            FortUserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegistrationRequest request) {

        String username = request.getUsername()
                .trim()
                .toLowerCase(Locale.ROOT);

        if ((userRepository.findByUsername(username)).isPresent()) {
            throw new UsernameAlreadyExistsException();
        }

        String encodedPassword =
                passwordEncoder.encode(request.getPassword());

        FortUser newUser = new FortUser(
                username,
                encodedPassword,
                "CUSTOMER"
        );

        userRepository.save(newUser);
    }
}
	

