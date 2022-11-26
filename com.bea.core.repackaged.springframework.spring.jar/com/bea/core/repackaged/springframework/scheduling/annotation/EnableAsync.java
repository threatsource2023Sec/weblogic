package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.context.annotation.AdviceMode;
import com.bea.core.repackaged.springframework.context.annotation.Import;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AsyncConfigurationSelector.class})
public @interface EnableAsync {
   Class annotation() default Annotation.class;

   boolean proxyTargetClass() default false;

   AdviceMode mode() default AdviceMode.PROXY;

   int order() default Integer.MAX_VALUE;
}
