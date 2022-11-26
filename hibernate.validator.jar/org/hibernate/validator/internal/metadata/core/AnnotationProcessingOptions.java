package org.hibernate.validator.internal.metadata.core;

import java.lang.reflect.Member;

public interface AnnotationProcessingOptions {
   boolean areClassLevelConstraintsIgnoredFor(Class var1);

   boolean areMemberConstraintsIgnoredFor(Member var1);

   boolean areReturnValueConstraintsIgnoredFor(Member var1);

   boolean areCrossParameterConstraintsIgnoredFor(Member var1);

   boolean areParameterConstraintsIgnoredFor(Member var1, int var2);

   void merge(AnnotationProcessingOptions var1);
}
