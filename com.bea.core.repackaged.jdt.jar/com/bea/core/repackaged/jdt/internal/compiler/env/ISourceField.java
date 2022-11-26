package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface ISourceField extends IGenericField {
   int getDeclarationSourceEnd();

   int getDeclarationSourceStart();

   char[] getInitializationSource();

   int getNameSourceEnd();

   int getNameSourceStart();

   char[] getTypeName();
}
