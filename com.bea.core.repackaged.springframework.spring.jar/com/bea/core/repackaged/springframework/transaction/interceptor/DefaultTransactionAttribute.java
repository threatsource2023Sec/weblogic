package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.support.DefaultTransactionDefinition;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class DefaultTransactionAttribute extends DefaultTransactionDefinition implements TransactionAttribute {
   @Nullable
   private String qualifier;
   @Nullable
   private String descriptor;

   public DefaultTransactionAttribute() {
   }

   public DefaultTransactionAttribute(TransactionAttribute other) {
      super(other);
   }

   public DefaultTransactionAttribute(int propagationBehavior) {
      super(propagationBehavior);
   }

   public void setQualifier(@Nullable String qualifier) {
      this.qualifier = qualifier;
   }

   @Nullable
   public String getQualifier() {
      return this.qualifier;
   }

   public void setDescriptor(@Nullable String descriptor) {
      this.descriptor = descriptor;
   }

   @Nullable
   public String getDescriptor() {
      return this.descriptor;
   }

   public boolean rollbackOn(Throwable ex) {
      return ex instanceof RuntimeException || ex instanceof Error;
   }

   protected final StringBuilder getAttributeDescription() {
      StringBuilder result = this.getDefinitionDescription();
      if (StringUtils.hasText(this.qualifier)) {
         result.append("; '").append(this.qualifier).append("'");
      }

      return result;
   }
}
