package com.bea.core.repackaged.springframework.scheduling.annotation;

import com.bea.core.repackaged.springframework.context.annotation.Bean;
import com.bea.core.repackaged.springframework.context.annotation.Configuration;
import com.bea.core.repackaged.springframework.context.annotation.Role;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.util.Assert;

@Configuration
@Role(2)
public class ProxyAsyncConfiguration extends AbstractAsyncConfiguration {
   @Bean(
      name = {"com.bea.core.repackaged.springframework.context.annotation.internalAsyncAnnotationProcessor"}
   )
   @Role(2)
   public AsyncAnnotationBeanPostProcessor asyncAdvisor() {
      Assert.notNull(this.enableAsync, (String)"@EnableAsync annotation metadata was not injected");
      AsyncAnnotationBeanPostProcessor bpp = new AsyncAnnotationBeanPostProcessor();
      bpp.configure(this.executor, this.exceptionHandler);
      Class customAsyncAnnotation = this.enableAsync.getClass("annotation");
      if (customAsyncAnnotation != AnnotationUtils.getDefaultValue(EnableAsync.class, "annotation")) {
         bpp.setAsyncAnnotationType(customAsyncAnnotation);
      }

      bpp.setProxyTargetClass(this.enableAsync.getBoolean("proxyTargetClass"));
      bpp.setOrder((Integer)this.enableAsync.getNumber("order"));
      return bpp;
   }
}
