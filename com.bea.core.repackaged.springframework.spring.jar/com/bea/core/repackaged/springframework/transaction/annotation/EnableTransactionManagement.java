package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.context.annotation.AdviceMode;
import com.bea.core.repackaged.springframework.context.annotation.Import;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({TransactionManagementConfigurationSelector.class})
public @interface EnableTransactionManagement {
   boolean proxyTargetClass() default false;

   AdviceMode mode() default AdviceMode.PROXY;

   int order() default Integer.MAX_VALUE;
}
