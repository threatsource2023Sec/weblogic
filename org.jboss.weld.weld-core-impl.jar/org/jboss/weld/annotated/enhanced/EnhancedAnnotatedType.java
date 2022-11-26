package org.jboss.weld.annotated.enhanced;

import java.util.Collection;
import javax.enterprise.inject.spi.AnnotatedType;
import org.jboss.weld.annotated.slim.SlimAnnotatedType;

public interface EnhancedAnnotatedType extends EnhancedAnnotated, AnnotatedType {
   Collection getEnhancedFields();

   Collection getEnhancedMethods();

   Collection getDeclaredEnhancedMethods();

   EnhancedAnnotatedField getDeclaredEnhancedField(String var1);

   Collection getEnhancedFields(Class var1);

   Collection getDeclaredEnhancedFields();

   Collection getDeclaredEnhancedFields(Class var1);

   Collection getEnhancedConstructors();

   Collection getEnhancedConstructors(Class var1);

   EnhancedAnnotatedConstructor getNoArgsEnhancedConstructor();

   EnhancedAnnotatedConstructor getDeclaredEnhancedConstructor(ConstructorSignature var1);

   Collection getEnhancedMethods(Class var1);

   Collection getDeclaredEnhancedMethods(Class var1);

   EnhancedAnnotatedMethod getDeclaredEnhancedMethod(MethodSignature var1);

   EnhancedAnnotatedMethod getEnhancedMethod(MethodSignature var1);

   Collection getDeclaredEnhancedMethodsWithAnnotatedParameters(Class var1);

   Collection getEnhancedMethodsWithAnnotatedParameters(Class var1);

   EnhancedAnnotatedType getEnhancedSuperclass();

   boolean isParameterizedType();

   boolean isAbstract();

   boolean isEnum();

   boolean isMemberClass();

   boolean isLocalClass();

   boolean isAnonymousClass();

   boolean isSerializable();

   boolean isDiscovered();

   Object cast(Object var1);

   EnhancedAnnotatedType asEnhancedSubclass(EnhancedAnnotatedType var1);

   boolean isEquivalent(Class var1);

   String getSimpleName();

   Collection getDeclaredMetaAnnotations(Class var1);

   SlimAnnotatedType slim();
}
