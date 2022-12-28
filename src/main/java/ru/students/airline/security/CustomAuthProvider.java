package ru.students.airline.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.students.airline.persistence.entity.User;
import ru.students.airline.services.CustomDetailsService;
import ru.students.airline.services.RegistrationService;

@Component
@RequiredArgsConstructor
public class CustomAuthProvider implements AuthenticationProvider {
    //Пользовательский поставщик авторизации. При помощи интерфейса AuthenticationProvider

    private final CustomDetailsService detailsService;
    private final RegistrationService registrationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (username.isEmpty() || password.isEmpty())
            throw new BadCredentialsException("Incorrect password");

        UserDetails personDetails;

        try {
            personDetails = detailsService.loadUserByUsername(username);
            // мы пытаемся найти информацию по имени, а имени нет, значит имя нужно создать
        } catch (UsernameNotFoundException ignore) {
            registrationService.register(new User(username, password, "ROLE_USER"));
            personDetails = detailsService.loadUserByUsername(username); // новые данные о пользователе, котого создали
        }

        if (!password.equals(personDetails.getPassword()))// смотрим на правильность пароля
            throw new BadCredentialsException("Incorrect password");

        return new UsernamePasswordAuthenticationToken(personDetails, password, personDetails.getAuthorities());
        // Token позволяет отслеживать активность пользователя на сайте
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
        // supports проверяет этот токен. Если он есть то все хорошо
    }

    public static String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();// данные от чего имени ведутся запросы
        CustomUserDetails personDetails = (CustomUserDetails) authentication.getPrincipal();// берет детали о пользователи
        return personDetails.getUsername();
    }
}
