package com.bea.core.repackaged.jdt.internal.compiler.env;

public interface ISourceType extends IGenericType {
   int getDeclarationSourceEnd();

   int getDeclarationSourceStart();

   ISourceType getEnclosingType();

   ISourceField[] getFields();

   char[][] getInterfaceNames();

   ISourceType[] getMemberTypes();

   ISourceMethod[] getMethods();

   char[] getName();

   int getNameSourceEnd();

   int getNameSourceStart();

   char[] getSuperclassName();

   char[][][] getTypeParameterBounds();

   char[][] getTypeParameterNames();

   boolean isAnonymous();
}
