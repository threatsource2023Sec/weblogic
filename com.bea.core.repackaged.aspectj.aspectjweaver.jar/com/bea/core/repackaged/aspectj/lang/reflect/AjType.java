package com.bea.core.repackaged.aspectj.lang.reflect;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public interface AjType extends Type, AnnotatedElement {
   String getName();

   Package getPackage();

   AjType[] getInterfaces();

   int getModifiers();

   Class getJavaClass();

   AjType getSupertype();

   Type getGenericSupertype();

   Method getEnclosingMethod();

   Constructor getEnclosingConstructor();

   AjType getEnclosingType();

   AjType getDeclaringType();

   PerClause getPerClause();

   AjType[] getAjTypes();

   AjType[] getDeclaredAjTypes();

   Constructor getConstructor(AjType... var1) throws NoSuchMethodException;

   Constructor[] getConstructors();

   Constructor getDeclaredConstructor(AjType... var1) throws NoSuchMethodException;

   Constructor[] getDeclaredConstructors();

   Field getDeclaredField(String var1) throws NoSuchFieldException;

   Field[] getDeclaredFields();

   Field getField(String var1) throws NoSuchFieldException;

   Field[] getFields();

   Method getDeclaredMethod(String var1, AjType... var2) throws NoSuchMethodException;

   Method getMethod(String var1, AjType... var2) throws NoSuchMethodException;

   Method[] getDeclaredMethods();

   Method[] getMethods();

   Pointcut getDeclaredPointcut(String var1) throws NoSuchPointcutException;

   Pointcut getPointcut(String var1) throws NoSuchPointcutException;

   Pointcut[] getDeclaredPointcuts();

   Pointcut[] getPointcuts();

   Advice[] getDeclaredAdvice(AdviceKind... var1);

   Advice[] getAdvice(AdviceKind... var1);

   Advice getAdvice(String var1) throws NoSuchAdviceException;

   Advice getDeclaredAdvice(String var1) throws NoSuchAdviceException;

   InterTypeMethodDeclaration getDeclaredITDMethod(String var1, AjType var2, AjType... var3) throws NoSuchMethodException;

   InterTypeMethodDeclaration[] getDeclaredITDMethods();

   InterTypeMethodDeclaration getITDMethod(String var1, AjType var2, AjType... var3) throws NoSuchMethodException;

   InterTypeMethodDeclaration[] getITDMethods();

   InterTypeConstructorDeclaration getDeclaredITDConstructor(AjType var1, AjType... var2) throws NoSuchMethodException;

   InterTypeConstructorDeclaration[] getDeclaredITDConstructors();

   InterTypeConstructorDeclaration getITDConstructor(AjType var1, AjType... var2) throws NoSuchMethodException;

   InterTypeConstructorDeclaration[] getITDConstructors();

   InterTypeFieldDeclaration getDeclaredITDField(String var1, AjType var2) throws NoSuchFieldException;

   InterTypeFieldDeclaration[] getDeclaredITDFields();

   InterTypeFieldDeclaration getITDField(String var1, AjType var2) throws NoSuchFieldException;

   InterTypeFieldDeclaration[] getITDFields();

   DeclareErrorOrWarning[] getDeclareErrorOrWarnings();

   DeclareParents[] getDeclareParents();

   DeclareSoft[] getDeclareSofts();

   DeclareAnnotation[] getDeclareAnnotations();

   DeclarePrecedence[] getDeclarePrecedence();

   Object[] getEnumConstants();

   TypeVariable[] getTypeParameters();

   boolean isEnum();

   boolean isInstance(Object var1);

   boolean isInterface();

   boolean isLocalClass();

   boolean isMemberClass();

   boolean isArray();

   boolean isPrimitive();

   boolean isAspect();

   boolean isMemberAspect();

   boolean isPrivileged();
}
