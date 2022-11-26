package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.annotated.enhanced.MethodSignature;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class EnhancedAnnotatedMethodImpl extends AbstractEnhancedAnnotatedCallable implements EnhancedAnnotatedMethod {
   private final List parameters;
   private final String propertyName;
   private final MethodSignature signature;
   private final AnnotatedMethod slim;

   public static EnhancedAnnotatedMethodImpl of(AnnotatedMethod annotatedMethod, EnhancedAnnotatedType declaringClass, ClassTransformer classTransformer) {
      EnhancedAnnotatedType downcastDeclaringType = (EnhancedAnnotatedType)Reflections.cast(declaringClass);
      return new EnhancedAnnotatedMethodImpl(annotatedMethod, buildAnnotationMap(annotatedMethod.getAnnotations()), buildAnnotationMap(annotatedMethod.getAnnotations()), downcastDeclaringType, classTransformer);
   }

   private EnhancedAnnotatedMethodImpl(AnnotatedMethod annotatedMethod, Map annotationMap, Map declaredAnnotationMap, EnhancedAnnotatedType declaringClass, ClassTransformer classTransformer) {
      super(annotatedMethod, annotationMap, declaredAnnotationMap, classTransformer, declaringClass);
      this.slim = annotatedMethod;
      ArrayList parameters = new ArrayList(annotatedMethod.getParameters().size());
      validateParameterCount(annotatedMethod);
      Iterator var7 = annotatedMethod.getParameters().iterator();

      while(var7.hasNext()) {
         AnnotatedParameter annotatedParameter = (AnnotatedParameter)var7.next();
         EnhancedAnnotatedParameter parameter = EnhancedAnnotatedParameterImpl.of(annotatedParameter, this, classTransformer);
         parameters.add(parameter);
      }

      this.parameters = WeldCollections.immutableListView(parameters);
      String propertyName = Reflections.getPropertyName(this.getDelegate());
      if (propertyName == null) {
         this.propertyName = this.getName();
      } else {
         this.propertyName = propertyName;
      }

      this.signature = new MethodSignatureImpl(this);
   }

   public Method getDelegate() {
      return this.slim.getJavaMember();
   }

   public List getEnhancedParameters() {
      return this.parameters;
   }

   public Class[] getParameterTypesAsArray() {
      return this.slim.getJavaMember().getParameterTypes();
   }

   public List getEnhancedParameters(Class annotationType) {
      List ret = new ArrayList();
      Iterator var3 = this.parameters.iterator();

      while(var3.hasNext()) {
         EnhancedAnnotatedParameter parameter = (EnhancedAnnotatedParameter)var3.next();
         if (parameter.isAnnotationPresent(annotationType)) {
            ret.add(parameter);
         }
      }

      return ret;
   }

   public boolean isEquivalent(Method method) {
      return this.getDeclaringType().isEquivalent(method.getDeclaringClass()) && this.getName().equals(method.getName()) && Arrays.equals(this.getParameterTypesAsArray(), method.getParameterTypes());
   }

   public String getPropertyName() {
      return this.propertyName;
   }

   public String toString() {
      return Formats.formatAnnotatedMethod(this);
   }

   public MethodSignature getSignature() {
      return this.signature;
   }

   public List getParameters() {
      return (List)Reflections.cast(this.parameters);
   }

   public boolean isGeneric() {
      return ((Method)this.getJavaMember()).getTypeParameters().length > 0;
   }

   public AnnotatedMethod slim() {
      return this.slim;
   }
}
