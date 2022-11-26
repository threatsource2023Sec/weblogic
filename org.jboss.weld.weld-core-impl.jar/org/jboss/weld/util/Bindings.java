package org.jboss.weld.util;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.inject.Any.Literal;
import javax.enterprise.inject.spi.BeanManager;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.metadata.cache.InterceptorBindingModel;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.metadata.cache.QualifierModel;
import org.jboss.weld.resolution.QualifierInstance;
import org.jboss.weld.util.collections.ImmutableSet;

public class Bindings {
   public static final Set DEFAULT_QUALIFIERS;

   private Bindings() {
   }

   public static boolean areQualifiersEquivalent(Annotation qualifier1, Annotation qualifier2, MetaAnnotationStore store) {
      checkQualifier(qualifier1, store);
      checkQualifier(qualifier2, store);
      QualifierInstance q1 = QualifierInstance.of(qualifier1, store);
      QualifierInstance q2 = QualifierInstance.of(qualifier2, store);
      return q1.equals(q2);
   }

   public static int getQualifierHashCode(Annotation qualifier, MetaAnnotationStore store) {
      checkQualifier(qualifier, store);
      return QualifierInstance.of(qualifier, store).hashCode();
   }

   private static void checkQualifier(Annotation qualifier, MetaAnnotationStore store) {
      Preconditions.checkNotNull(qualifier);
      QualifierModel model = store.getBindingTypeModel(qualifier.annotationType());
      if (model == null || !model.isValid()) {
         throw BeanManagerLogger.LOG.invalidQualifier(qualifier);
      }
   }

   public static void validateQualifiers(Iterable qualifiers, BeanManager manager, Object definer, String nullErrorMessage) {
      if (qualifiers == null) {
         throw MetadataLogger.LOG.qualifiersNull(nullErrorMessage, definer);
      } else {
         Iterator var4 = qualifiers.iterator();

         Annotation annotation;
         do {
            if (!var4.hasNext()) {
               return;
            }

            annotation = (Annotation)var4.next();
         } while(manager.isQualifier(annotation.annotationType()));

         throw MetadataLogger.LOG.notAQualifier(annotation.annotationType(), definer);
      }
   }

   public static boolean areInterceptorBindingsEquivalent(Annotation qualifier1, Annotation qualifier2, MetaAnnotationStore store) {
      checkInterceptorBinding(qualifier1, store);
      checkInterceptorBinding(qualifier2, store);
      QualifierInstance q1 = QualifierInstance.of(qualifier1, store);
      QualifierInstance q2 = QualifierInstance.of(qualifier2, store);
      return q1.equals(q2);
   }

   public static int getInterceptorBindingHashCode(Annotation qualifier, MetaAnnotationStore store) {
      checkInterceptorBinding(qualifier, store);
      return QualifierInstance.of(qualifier, store).hashCode();
   }

   private static void checkInterceptorBinding(Annotation qualifier, MetaAnnotationStore store) {
      Preconditions.checkNotNull(qualifier);
      InterceptorBindingModel model = store.getInterceptorBindingModel(qualifier.annotationType());
      if (model == null || !model.isValid()) {
         throw BeanManagerLogger.LOG.interceptorResolutionWithNonbindingType(qualifier);
      }
   }

   static {
      DEFAULT_QUALIFIERS = ImmutableSet.of(Literal.INSTANCE, javax.enterprise.inject.Default.Literal.INSTANCE);
   }
}
