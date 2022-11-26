package com.oracle.weblogic.lifecycle.provisioning.api;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public abstract class ProvisioningOperationDescriptor implements Serializable {
   private static final long serialVersionUID = 1L;
   private final Annotation provisioningOperationQualifier = this.getProvisioningOperationQualifier();

   protected ProvisioningOperationDescriptor() {
   }

   public abstract Set getProvisioningSequence(Set var1) throws ProvisioningException;

   public final Annotation getProvisioningOperationQualifier() {
      Annotation returnValue;
      if (this.provisioningOperationQualifier != null) {
         returnValue = this.provisioningOperationQualifier;
      } else {
         AnnotatedElement myClass = this.getClass();
         Annotation[] annotations = myClass.getAnnotations();
         if (annotations == null) {
            throw new IllegalStateException("this.getClass().getAnnotations() == null");
         }

         if (annotations.length <= 0) {
            throw new IllegalStateException("this.getClass().getAnnotations().length == 0");
         }

         Annotation povisioningOperationQualifierAnnotation = null;
         Annotation[] var5 = annotations;
         int var6 = annotations.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Annotation annotation = var5[var7];
            if (isProvisioningOperationQualifier(annotation)) {
               povisioningOperationQualifierAnnotation = annotation;
               break;
            }
         }

         if (povisioningOperationQualifierAnnotation == null) {
            throw new IllegalStateException(myClass + " is not annotated with an annotation that is annotated with ProvisioningOperation");
         }

         returnValue = povisioningOperationQualifierAnnotation;
      }

      return returnValue;
   }

   public final int hashCode() {
      int hashCode = 17;
      Object annotation = this.getProvisioningOperationQualifier();
      int c = annotation == null ? 0 : annotation.hashCode();
      hashCode = hashCode * 37 + c;
      return hashCode;
   }

   public final boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof ProvisioningOperationDescriptor) {
         ProvisioningOperationDescriptor her = (ProvisioningOperationDescriptor)other;
         Object annotation = this.getProvisioningOperationQualifier();
         if (annotation == null) {
            if (her.getProvisioningOperationQualifier() != null) {
               return false;
            }
         } else if (!annotation.equals(her.getProvisioningOperationQualifier())) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public static final boolean isProvisioningOperationQualifier(Annotation annotation) {
      return isOrHasNormalOrMetaQualifier(annotation, com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperation.class, (Set)null);
   }

   private static final boolean isOrHasNormalOrMetaQualifier(Annotation annotation, Class qualifier, Set seen) {
      boolean returnValue = false;
      if (annotation != null && qualifier != null) {
         AnnotatedElement annotationType = annotation.annotationType();

         assert annotationType != null;

         if (annotationType.equals(qualifier)) {
            returnValue = true;
         } else {
            if (seen == null) {
               seen = new HashSet();
            }

            if (!((Set)seen).contains(annotation)) {
               ((Set)seen).add(annotation);
               Annotation[] metaAnnotations = annotationType.getAnnotations();
               if (metaAnnotations != null && metaAnnotations.length > 0) {
                  Annotation[] var6 = metaAnnotations;
                  int var7 = metaAnnotations.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     Annotation metaAnnotation = var6[var8];
                     if (metaAnnotation != null && (qualifier.equals(metaAnnotation.annotationType()) || isOrHasNormalOrMetaQualifier(metaAnnotation, qualifier, (Set)seen))) {
                        returnValue = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      return returnValue;
   }
}
