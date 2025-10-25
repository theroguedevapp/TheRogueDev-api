package br.com.theroguedev.api.config.security.annotation.read;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('SCOPE_admin:all', 'SCOPE_currency_virtual_forum_publication_balance:read')")
public @interface CanReadCurrencyVirtualForumPublicationBalance {
}
