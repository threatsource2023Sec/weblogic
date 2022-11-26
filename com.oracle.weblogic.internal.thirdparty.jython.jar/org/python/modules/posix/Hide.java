package org.python.modules.posix;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@interface Hide {
   OS[] value() default {};

   PosixImpl posixImpl() default PosixImpl.NOT_APPLICABLE;
}
