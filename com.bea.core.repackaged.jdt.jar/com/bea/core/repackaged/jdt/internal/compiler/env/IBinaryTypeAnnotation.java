package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface IBinaryTypeAnnotation {
   int[] NO_TYPE_PATH = new int[0];

   IBinaryAnnotation getAnnotation();

   int getTargetType();

   int[] getTypePath();

   int getSupertypeIndex();

   int getTypeParameterIndex();

   int getBoundIndex();

   int getMethodFormalParameterIndex();

   int getThrowsTypeIndex();
}
