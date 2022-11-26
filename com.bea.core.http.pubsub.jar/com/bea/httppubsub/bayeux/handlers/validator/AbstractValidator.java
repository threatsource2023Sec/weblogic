package com.bea.httppubsub.bayeux.handlers.validator;

import java.util.ArrayList;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;

public abstract class AbstractValidator implements Validator {
   protected final DebugLogger logger = DebugLogger.getDebugLogger("DebugPubSubBayeux");
   protected boolean passed = true;
   protected int errorCode;
   protected List errorArgs = new ArrayList();
   protected Object generatedObject = null;

   protected AbstractValidator() {
   }

   public final boolean isPassed() {
      return this.passed;
   }

   public final int getErrorCode() {
      return this.errorCode;
   }

   public final String[] getErrorArguments() {
      return (String[])this.errorArgs.toArray(new String[this.errorArgs.size()]);
   }

   public final Object getGeneratedObject() {
      return this.generatedObject;
   }

   public final void validate() {
      this.reset();
      this.doValidate();
   }

   protected abstract void doValidate();

   protected void validateFailure() {
      this.passed = false;
   }

   protected void reset() {
      this.passed = true;
      this.errorArgs.clear();
      this.generatedObject = null;
   }
}
