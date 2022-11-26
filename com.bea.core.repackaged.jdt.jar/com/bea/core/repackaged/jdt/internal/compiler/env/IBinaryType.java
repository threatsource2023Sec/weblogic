package com.bea.core.repackaged.jdt.internal.compiler.env;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BinaryTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;

public interface IBinaryType extends IGenericType {
   char[][] NoInterface = CharOperation.NO_CHAR_CHAR;
   IBinaryNestedType[] NoNestedType = new IBinaryNestedType[0];
   IBinaryField[] NoField = new IBinaryField[0];
   IBinaryMethod[] NoMethod = new IBinaryMethod[0];

   IBinaryAnnotation[] getAnnotations();

   IBinaryTypeAnnotation[] getTypeAnnotations();

   char[] getEnclosingMethod();

   char[] getEnclosingTypeName();

   IBinaryField[] getFields();

   char[] getModule();

   char[] getGenericSignature();

   char[][] getInterfaceNames();

   IBinaryNestedType[] getMemberTypes();

   IBinaryMethod[] getMethods();

   char[][][] getMissingTypeNames();

   char[] getName();

   char[] getSourceName();

   char[] getSuperclassName();

   long getTagBits();

   boolean isAnonymous();

   boolean isLocal();

   boolean isMember();

   char[] sourceFileName();

   ITypeAnnotationWalker enrichWithExternalAnnotationsFor(ITypeAnnotationWalker var1, Object var2, LookupEnvironment var3);

   BinaryTypeBinding.ExternalAnnotationStatus getExternalAnnotationStatus();
}
