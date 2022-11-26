package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.annotated.enhanced.ConstructorSignature;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.collections.WeldCollections;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public class EnhancedAnnotatedConstructorImpl extends AbstractEnhancedAnnotatedCallable implements EnhancedAnnotatedConstructor {
   private final List parameters;
   private final ConstructorSignature signature;
   private final AnnotatedConstructor slim;

   public static EnhancedAnnotatedConstructor of(AnnotatedConstructor annotatedConstructor, EnhancedAnnotatedType declaringClass, ClassTransformer classTransformer) {
      return new EnhancedAnnotatedConstructorImpl(annotatedConstructor, buildAnnotationMap(annotatedConstructor.getAnnotations()), buildAnnotationMap(annotatedConstructor.getAnnotations()), declaringClass, classTransformer);
   }

   private EnhancedAnnotatedConstructorImpl(AnnotatedConstructor annotatedConstructor, Map annotationMap, Map declaredAnnotationMap, EnhancedAnnotatedType declaringClass, ClassTransformer classTransformer) {
      super(annotatedConstructor, annotationMap, declaredAnnotationMap, classTransformer, declaringClass);
      this.slim = annotatedConstructor;
      ArrayList parameters = new ArrayList();
      validateParameterCount(annotatedConstructor);
      Iterator var7 = annotatedConstructor.getParameters().iterator();

      while(var7.hasNext()) {
         AnnotatedParameter annotatedParameter = (AnnotatedParameter)var7.next();
         EnhancedAnnotatedParameter parameter = EnhancedAnnotatedParameterImpl.of(annotatedParameter, this, classTransformer);
         parameters.add(parameter);
      }

      this.parameters = WeldCollections.immutableListView(parameters);
      this.signature = new ConstructorSignatureImpl(this);
   }

   public Constructor getAnnotatedConstructor() {
      return this.slim.getJavaMember();
   }

   public Constructor getDelegate() {
      return this.slim.getJavaMember();
   }

   public List getEnhancedParameters() {
      return this.parameters;
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

   public boolean equals(Object other) {
      if (super.equals(other) && other instanceof EnhancedAnnotatedConstructor) {
         EnhancedAnnotatedConstructor that = (EnhancedAnnotatedConstructor)other;
         return ((Constructor)this.getJavaMember()).equals(that.getJavaMember()) && this.getEnhancedParameters().equals(that.getEnhancedParameters());
      } else {
         return false;
      }
   }

   public int hashCode() {
      int hash = 1;
      hash = hash * 31 + ((Constructor)this.getJavaMember()).hashCode();
      hash = hash * 31 + this.getEnhancedParameters().hashCode();
      return hash;
   }

   public String toString() {
      return Formats.formatAnnotatedConstructor(this);
   }

   public ConstructorSignature getSignature() {
      return this.signature;
   }

   public List getParameters() {
      return (List)Reflections.cast(this.parameters);
   }

   public boolean isGeneric() {
      return ((Constructor)this.getJavaMember()).getTypeParameters().length > 0;
   }

   public AnnotatedConstructor slim() {
      return this.slim;
   }
}
