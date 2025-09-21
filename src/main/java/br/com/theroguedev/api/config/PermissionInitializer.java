package br.com.theroguedev.api.config;

import br.com.theroguedev.api.user.service.PermissionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PermissionInitializer {

    @Autowired
    private PermissionService permissionService;

    @PostConstruct
    @Transactional
    public void extractAndSavePermission() {
        Reflections reflections = new Reflections(
                "br.com.theroguedev.api",
                Scanners.TypesAnnotated,
                Scanners.MethodsAnnotated
        );

        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(RestController.class);

        Pattern permissionPattern = Pattern.compile("hasAuthority\\('([^']+)'\\)");

        for (Class<?> controllerClass : controllerClasses) {
            for (var method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(PreAuthorize.class)) {
                    String expression = method.getAnnotation(PreAuthorize.class).value();

                    Matcher matcher = permissionPattern.matcher(expression);
                    while (matcher.find()) {
                        String permission = matcher.group(1);
                        permissionService.registerPermission(permission);
                    }
                }
            }
        }
    }
}
