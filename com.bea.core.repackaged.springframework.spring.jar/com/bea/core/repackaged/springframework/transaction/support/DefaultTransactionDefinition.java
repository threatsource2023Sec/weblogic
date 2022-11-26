package com.bea.core.repackaged.springframework.transaction.support;

import com.bea.core.repackaged.springframework.core.Constants;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.TransactionDefinition;
import java.io.Serializable;

public class DefaultTransactionDefinition implements TransactionDefinition, Serializable {
   public static final String PREFIX_PROPAGATION = "PROPAGATION_";
   public static final String PREFIX_ISOLATION = "ISOLATION_";
   public static final String PREFIX_TIMEOUT = "timeout_";
   public static final String READ_ONLY_MARKER = "readOnly";
   static final Constants constants = new Constants(TransactionDefinition.class);
   private int propagationBehavior = 0;
   private int isolationLevel = -1;
   private int timeout = -1;
   private boolean readOnly = false;
   @Nullable
   private String name;

   public DefaultTransactionDefinition() {
   }

   public DefaultTransactionDefinition(TransactionDefinition other) {
      this.propagationBehavior = other.getPropagationBehavior();
      this.isolationLevel = other.getIsolationLevel();
      this.timeout = other.getTimeout();
      this.readOnly = other.isReadOnly();
      this.name = other.getName();
   }

   public DefaultTransactionDefinition(int propagationBehavior) {
      this.propagationBehavior = propagationBehavior;
   }

   public final void setPropagationBehaviorName(String constantName) throws IllegalArgumentException {
      if (!constantName.startsWith("PROPAGATION_")) {
         throw new IllegalArgumentException("Only propagation constants allowed");
      } else {
         this.setPropagationBehavior(constants.asNumber(constantName).intValue());
      }
   }

   public final void setPropagationBehavior(int propagationBehavior) {
      if (!constants.getValues("PROPAGATION_").contains(propagationBehavior)) {
         throw new IllegalArgumentException("Only values of propagation constants allowed");
      } else {
         this.propagationBehavior = propagationBehavior;
      }
   }

   public final int getPropagationBehavior() {
      return this.propagationBehavior;
   }

   public final void setIsolationLevelName(String constantName) throws IllegalArgumentException {
      if (!constantName.startsWith("ISOLATION_")) {
         throw new IllegalArgumentException("Only isolation constants allowed");
      } else {
         this.setIsolationLevel(constants.asNumber(constantName).intValue());
      }
   }

   public final void setIsolationLevel(int isolationLevel) {
      if (!constants.getValues("ISOLATION_").contains(isolationLevel)) {
         throw new IllegalArgumentException("Only values of isolation constants allowed");
      } else {
         this.isolationLevel = isolationLevel;
      }
   }

   public final int getIsolationLevel() {
      return this.isolationLevel;
   }

   public final void setTimeout(int timeout) {
      if (timeout < -1) {
         throw new IllegalArgumentException("Timeout must be a positive integer or TIMEOUT_DEFAULT");
      } else {
         this.timeout = timeout;
      }
   }

   public final int getTimeout() {
      return this.timeout;
   }

   public final void setReadOnly(boolean readOnly) {
      this.readOnly = readOnly;
   }

   public final boolean isReadOnly() {
      return this.readOnly;
   }

   public final void setName(String name) {
      this.name = name;
   }

   @Nullable
   public final String getName() {
      return this.name;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof TransactionDefinition && this.toString().equals(other.toString());
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public String toString() {
      return this.getDefinitionDescription().toString();
   }

   protected final StringBuilder getDefinitionDescription() {
      StringBuilder result = new StringBuilder();
      result.append(constants.toCode(this.propagationBehavior, "PROPAGATION_"));
      result.append(',');
      result.append(constants.toCode(this.isolationLevel, "ISOLATION_"));
      if (this.timeout != -1) {
         result.append(',');
         result.append("timeout_").append(this.timeout);
      }

      if (this.readOnly) {
         result.append(',');
         result.append("readOnly");
      }

      return result;
   }
}
