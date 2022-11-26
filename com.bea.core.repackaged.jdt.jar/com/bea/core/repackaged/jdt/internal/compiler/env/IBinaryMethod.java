package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface IBinaryMethod extends IGenericMethod {
   IBinaryAnnotation[] getAnnotations();

   Object getDefaultValue();

   char[][] getExceptionTypeNames();

   char[] getGenericSignature();

   char[] getMethodDescriptor();

   IBinaryAnnotation[] getParameterAnnotations(int var1, char[] var2);

   int getAnnotatedParametersCount();

   char[] getSelector();

   long getTagBits();

   boolean isClinit();

   IBinaryTypeAnnotation[] getTypeAnnotations();
}
