package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.util.StringValueResolver;

public interface EmbeddedValueResolverAware extends Aware {
   void setEmbeddedValueResolver(StringValueResolver var1);
}
