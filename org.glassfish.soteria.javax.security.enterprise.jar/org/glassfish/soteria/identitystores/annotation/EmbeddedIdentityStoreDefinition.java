package org.glassfish.soteria.identitystores.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStore.ValidationType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EmbeddedIdentityStoreDefinition {
   Credentials[] value() default {};

   int priority() default 90;

   IdentityStore.ValidationType[] useFor() default {ValidationType.VALIDATE, ValidationType.PROVIDE_GROUPS};
}
