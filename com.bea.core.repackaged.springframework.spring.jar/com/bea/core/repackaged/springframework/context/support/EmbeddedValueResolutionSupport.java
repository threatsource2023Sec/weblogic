package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.context.EmbeddedValueResolverAware;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringValueResolver;

public class EmbeddedValueResolutionSupport implements EmbeddedValueResolverAware {
   @Nullable
   private StringValueResolver embeddedValueResolver;

   public void setEmbeddedValueResolver(StringValueResolver resolver) {
      this.embeddedValueResolver = resolver;
   }

   @Nullable
   protected String resolveEmbeddedValue(String value) {
      return this.embeddedValueResolver != null ? this.embeddedValueResolver.resolveStringValue(value) : value;
   }
}
