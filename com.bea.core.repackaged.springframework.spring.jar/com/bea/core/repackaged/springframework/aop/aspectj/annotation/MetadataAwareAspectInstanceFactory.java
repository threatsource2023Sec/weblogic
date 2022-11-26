package com.bea.core.repackaged.springframework.aop.aspectj.annotation;

import com.bea.core.repackaged.springframework.aop.aspectj.AspectInstanceFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;

public interface MetadataAwareAspectInstanceFactory extends AspectInstanceFactory {
   AspectMetadata getAspectMetadata();

   @Nullable
   Object getAspectCreationMutex();
}
