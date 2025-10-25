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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PermissionInitializer {

    @Autowired
    private PermissionService permissionService;

    private static final Pattern PERMISSION_PATTERN = Pattern.compile("hasAnyAuthority\\((.*?)\\)");

    @PostConstruct
    @Transactional
    public void extractAndSavePermission() {
        Reflections reflections = new Reflections(
                "br.com.theroguedev.api",
                Scanners.TypesAnnotated,
                Scanners.MethodsAnnotated
        );

        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(RestController.class);

        for (Class<?> controllerClass : controllerClasses) {
            for (Method method : controllerClass.getDeclaredMethods()) {

                if (method.isAnnotationPresent(PreAuthorize.class)) {
                    String expression = method.getAnnotation(PreAuthorize.class).value();
                    extractPermissionsFromExpression(expression);
                }

                for (Annotation annotation : method.getAnnotations()) {
                    PreAuthorize metaPreAuth = annotation.annotationType().getAnnotation(PreAuthorize.class);
                    if (metaPreAuth != null) {
                        String expression = metaPreAuth.value();
                        extractPermissionsFromExpression(expression);
                    }
                }
            }
        }
    }

    private void extractPermissionsFromExpression(String expression) {
        Matcher matcher = PERMISSION_PATTERN.matcher(expression);
        while (matcher.find()) {
            String group = matcher.group(1);
            String[] permissions = group.split(",");

            for (String rawPermission : permissions) {
                String permission = rawPermission.trim()
                        .replaceAll("['\"]", "")
                        .replaceFirst("^SCOPE_", "");
                if (!permission.isEmpty()) {
                    permissionService.registerPermission(permission);
                }
            }
        }
    }
}
