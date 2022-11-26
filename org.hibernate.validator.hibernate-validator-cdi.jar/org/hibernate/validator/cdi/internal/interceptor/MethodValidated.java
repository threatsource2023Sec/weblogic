package org.hibernate.validator.cdi.internal.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.interceptor.InterceptorBinding;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface MethodValidated {
}
