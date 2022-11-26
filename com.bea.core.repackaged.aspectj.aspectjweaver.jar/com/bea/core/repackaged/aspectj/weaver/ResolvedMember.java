package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ResolvedMember extends Member, AnnotatedElement, TypeVariableDeclaringElement {
   ResolvedMember[] NONE = new ResolvedMember[0];

   int getModifiers(World var1);

   int getModifiers();

   UnresolvedType[] getExceptions(World var1);

   UnresolvedType[] getExceptions();

   ShadowMunger getAssociatedShadowMunger();

   boolean isAjSynthetic();

   boolean isCompatibleWith(Member var1);

   boolean hasAnnotation(UnresolvedType var1);

   AnnotationAJ[] getAnnotations();

   ResolvedType[] getAnnotationTypes();

   void setAnnotationTypes(ResolvedType[] var1);

   void addAnnotation(AnnotationAJ var1);

   boolean isBridgeMethod();

   boolean isVarargsMethod();

   boolean isSynthetic();

   void write(CompressingDataOutputStream var1) throws IOException;

   ISourceContext getSourceContext(World var1);

   String[] getParameterNames();

   void setParameterNames(String[] var1);

   AnnotationAJ[][] getParameterAnnotations();

   ResolvedType[][] getParameterAnnotationTypes();

   String getAnnotationDefaultValue();

   String getParameterSignatureErased();

   String getSignatureErased();

   String[] getParameterNames(World var1);

   AjAttribute.EffectiveSignatureAttribute getEffectiveSignature();

   ISourceLocation getSourceLocation();

   int getStart();

   int getEnd();

   ISourceContext getSourceContext();

   void setPosition(int var1, int var2);

   void setSourceContext(ISourceContext var1);

   boolean isAbstract();

   boolean isPublic();

   boolean isDefault();

   boolean isVisible(ResolvedType var1);

   void setCheckedExceptions(UnresolvedType[] var1);

   void setAnnotatedElsewhere(boolean var1);

   boolean isAnnotatedElsewhere();

   String toGenericString();

   String toDebugString();

   boolean hasBackingGenericMember();

   ResolvedMember getBackingGenericMember();

   UnresolvedType getGenericReturnType();

   UnresolvedType[] getGenericParameterTypes();

   boolean equalsApartFromDeclaringType(Object var1);

   ResolvedMemberImpl parameterizedWith(UnresolvedType[] var1, ResolvedType var2, boolean var3);

   ResolvedMemberImpl parameterizedWith(UnresolvedType[] var1, ResolvedType var2, boolean var3, List var4);

   void setTypeVariables(TypeVariable[] var1);

   TypeVariable[] getTypeVariables();

   boolean matches(ResolvedMember var1, boolean var2);

   void evictWeavingState();

   ResolvedMember parameterizedWith(Map var1, World var2);

   boolean isDefaultConstructor();

   void setAnnotations(AnnotationAJ[] var1);
}
