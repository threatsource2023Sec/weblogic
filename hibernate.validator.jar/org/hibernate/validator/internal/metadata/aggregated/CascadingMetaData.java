package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.TypeVariable;
import java.util.Set;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;

public interface CascadingMetaData {
   TypeVariable getTypeParameter();

   boolean isCascading();

   boolean isMarkedForCascadingOnAnnotatedObjectOrContainerElements();

   Class convertGroup(Class var1);

   Set getGroupConversionDescriptors();

   boolean isContainer();

   CascadingMetaData as(Class var1);

   CascadingMetaData addRuntimeContainerSupport(ValueExtractorManager var1, Class var2);
}
