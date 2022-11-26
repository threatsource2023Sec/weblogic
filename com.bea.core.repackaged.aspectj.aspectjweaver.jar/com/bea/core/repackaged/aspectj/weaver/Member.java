package com.bea.core.repackaged.aspectj.weaver;

import java.util.Collection;

public interface Member extends Comparable {
   Member[] NONE = new Member[0];
   MemberKind METHOD = new MemberKind("METHOD", 1);
   MemberKind FIELD = new MemberKind("FIELD", 2);
   MemberKind CONSTRUCTOR = new MemberKind("CONSTRUCTOR", 3);
   MemberKind STATIC_INITIALIZATION = new MemberKind("STATIC_INITIALIZATION", 4);
   MemberKind POINTCUT = new MemberKind("POINTCUT", 5);
   MemberKind ADVICE = new MemberKind("ADVICE", 6);
   MemberKind HANDLER = new MemberKind("HANDLER", 7);
   MemberKind MONITORENTER = new MemberKind("MONITORENTER", 8);
   MemberKind MONITOREXIT = new MemberKind("MONITOREXIT", 9);
   AnnotationAJ[][] NO_PARAMETER_ANNOTATIONXS = new AnnotationAJ[0][];
   ResolvedType[][] NO_PARAMETER_ANNOTATION_TYPES = new ResolvedType[0][];

   MemberKind getKind();

   String getName();

   UnresolvedType getDeclaringType();

   UnresolvedType[] getParameterTypes();

   UnresolvedType[] getGenericParameterTypes();

   UnresolvedType getType();

   UnresolvedType getReturnType();

   UnresolvedType getGenericReturnType();

   String getSignature();

   JoinPointSignatureIterator getJoinPointSignatures(World var1);

   int getArity();

   String getParameterSignature();

   int getModifiers(World var1);

   int getModifiers();

   boolean canBeParameterized();

   AnnotationAJ[] getAnnotations();

   Collection getDeclaringTypes(World var1);

   String[] getParameterNames(World var1);

   UnresolvedType[] getExceptions(World var1);

   ResolvedMember resolve(World var1);

   int compareTo(Member var1);
}
