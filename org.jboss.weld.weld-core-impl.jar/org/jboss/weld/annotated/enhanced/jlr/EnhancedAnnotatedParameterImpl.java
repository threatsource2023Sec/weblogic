package org.jboss.weld.annotated.enhanced.jlr;

import java.util.Map;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedCallable;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedType;
import org.jboss.weld.logging.ReflectionLogger;
import org.jboss.weld.resources.ClassTransformer;
import org.jboss.weld.util.reflection.Formats;

public class EnhancedAnnotatedParameterImpl extends AbstractEnhancedAnnotated implements EnhancedAnnotatedParameter {
   private final EnhancedAnnotatedCallable declaringMember;
   private final AnnotatedParameter slim;

   public static EnhancedAnnotatedParameter of(AnnotatedParameter annotatedParameter, EnhancedAnnotatedCallable declaringMember, ClassTransformer classTransformer) {
      return new EnhancedAnnotatedParameterImpl(annotatedParameter, declaringMember, buildAnnotationMap(annotatedParameter.getAnnotations()), classTransformer);
   }

   protected EnhancedAnnotatedParameterImpl(AnnotatedParameter annotatedParameter, EnhancedAnnotatedCallable declaringMember, Map annotationMap, ClassTransformer classTransformer) {
      super(annotatedParameter, annotationMap, annotationMap, classTransformer);
      this.slim = annotatedParameter;
      this.declaringMember = declaringMember;
   }

   public boolean isFinal() {
      return false;
   }

   public boolean isStatic() {
      return false;
   }

   public boolean isPublic() {
      return false;
   }

   public boolean isPrivate() {
      return false;
   }

   public boolean isPackagePrivate() {
      return false;
   }

   public boolean isGeneric() {
      return false;
   }

   public Package getPackage() {
      return this.declaringMember.getPackage();
   }

   public String getName() {
      throw ReflectionLogger.LOG.unableToGetParameterName();
   }

   public String toString() {
      return Formats.formatAnnotatedParameter(this);
   }

   public AnnotatedCallable getDeclaringCallable() {
      return this.declaringMember;
   }

   public EnhancedAnnotatedCallable getDeclaringEnhancedCallable() {
      return this.declaringMember;
   }

   public int getPosition() {
      return this.slim.getPosition();
   }

   public Object getDelegate() {
      return null;
   }

   public EnhancedAnnotatedType getDeclaringType() {
      return this.getDeclaringEnhancedCallable().getDeclaringType();
   }

   public AnnotatedParameter slim() {
      return this.slim;
   }
}
