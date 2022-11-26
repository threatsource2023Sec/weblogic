package org.python.google.common.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.python.google.common.annotations.Beta;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Beta
public @interface AllowConcurrentEvents {
}
