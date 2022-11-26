package org.hibernate.validator.internal.util;

import com.fasterxml.classmate.TypeResolver;

public class TypeResolutionHelper {
   private final TypeResolver typeResolver = new TypeResolver();

   public TypeResolver getTypeResolver() {
      return this.typeResolver;
   }
}
