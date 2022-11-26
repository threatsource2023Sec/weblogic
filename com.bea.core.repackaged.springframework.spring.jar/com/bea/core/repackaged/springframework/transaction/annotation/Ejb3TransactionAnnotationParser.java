package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.interceptor.DefaultTransactionAttribute;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionAttribute;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import javax.ejb.ApplicationException;
import javax.ejb.TransactionAttributeType;

public class Ejb3TransactionAnnotationParser implements TransactionAnnotationParser, Serializable {
   @Nullable
   public TransactionAttribute parseTransactionAnnotation(AnnotatedElement element) {
      javax.ejb.TransactionAttribute ann = (javax.ejb.TransactionAttribute)element.getAnnotation(javax.ejb.TransactionAttribute.class);
      return ann != null ? this.parseTransactionAnnotation(ann) : null;
   }

   public TransactionAttribute parseTransactionAnnotation(javax.ejb.TransactionAttribute ann) {
      return new Ejb3TransactionAttribute(ann.value());
   }

   public boolean equals(Object other) {
      return this == other || other instanceof Ejb3TransactionAnnotationParser;
   }

   public int hashCode() {
      return Ejb3TransactionAnnotationParser.class.hashCode();
   }

   private static class Ejb3TransactionAttribute extends DefaultTransactionAttribute {
      public Ejb3TransactionAttribute(TransactionAttributeType type) {
         this.setPropagationBehaviorName("PROPAGATION_" + type.name());
      }

      public boolean rollbackOn(Throwable ex) {
         ApplicationException ann = (ApplicationException)ex.getClass().getAnnotation(ApplicationException.class);
         return ann != null ? ann.rollback() : super.rollbackOn(ex);
      }
   }
}
