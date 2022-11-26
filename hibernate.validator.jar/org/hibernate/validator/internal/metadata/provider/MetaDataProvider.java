package org.hibernate.validator.internal.metadata.provider;

import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;

public interface MetaDataProvider {
   AnnotationProcessingOptions getAnnotationProcessingOptions();

   BeanConfiguration getBeanConfiguration(Class var1);
}
