package org.hibernate.validator.internal.util.annotation;

import java.lang.annotation.Annotation;
import java.util.Map;
import javax.validation.ConstraintTarget;

public class ConstraintAnnotationDescriptor extends AnnotationDescriptor {
   public ConstraintAnnotationDescriptor(Annotation annotation) {
      super(annotation);
   }

   public ConstraintAnnotationDescriptor(AnnotationDescriptor descriptor) {
      super(descriptor);
   }

   public String getMessage() {
      return (String)this.getMandatoryAttribute("message", String.class);
   }

   public Class[] getGroups() {
      return (Class[])this.getMandatoryAttribute("groups", Class[].class);
   }

   public Class[] getPayload() {
      return (Class[])this.getMandatoryAttribute("payload", Class[].class);
   }

   public ConstraintTarget getValidationAppliesTo() {
      return (ConstraintTarget)this.getAttribute("validationAppliesTo", ConstraintTarget.class);
   }

   public static class Builder extends AnnotationDescriptor.Builder {
      public Builder(Class type) {
         super(type);
      }

      public Builder(Class type, Map attributes) {
         super(type, attributes);
      }

      public Builder(Annotation annotation) {
         super(annotation);
      }

      public Builder setMessage(String message) {
         this.setAttribute("message", message);
         return this;
      }

      public Builder setGroups(Class[] groups) {
         this.setAttribute("groups", groups);
         return this;
      }

      public Builder setPayload(Class[] payload) {
         this.setAttribute("payload", payload);
         return this;
      }

      public Builder setValidationAppliesTo(ConstraintTarget validationAppliesTo) {
         this.setAttribute("validationAppliesTo", validationAppliesTo);
         return this;
      }

      public ConstraintAnnotationDescriptor build() {
         return new ConstraintAnnotationDescriptor(super.build());
      }
   }
}
