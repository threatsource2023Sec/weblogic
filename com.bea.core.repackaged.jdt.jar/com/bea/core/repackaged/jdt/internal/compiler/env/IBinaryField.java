package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;

public interface IBinaryField extends IGenericField {
   IBinaryAnnotation[] getAnnotations();

   IBinaryTypeAnnotation[] getTypeAnnotations();

   Constant getConstant();

   char[] getGenericSignature();

   char[] getName();

   long getTagBits();

   char[] getTypeName();
}
