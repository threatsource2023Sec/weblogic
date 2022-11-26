package com.bea.httppubsub.bayeux.handlers.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidatorSuite implements Validator {
   private List validators = new ArrayList();
   private boolean isPassed = true;
   private int errorCode;
   private String[] errorArgs;
   private Object[] generatedObject;

   public void setValidators(Validator... validators) {
      if (validators != null) {
         this.validators.addAll(Arrays.asList(validators));
      }

   }

   public void clear() {
      this.validators.clear();
   }

   public void validate() {
      this.generatedObject = new Object[this.validators.size()];

      for(int i = 0; i < this.validators.size(); ++i) {
         Validator validator = (Validator)this.validators.get(i);
         validator.validate();
         this.generatedObject[i] = validator.getGeneratedObject();
         this.isPassed = validator.isPassed();
         if (!this.isPassed) {
            this.errorCode = validator.getErrorCode();
            this.errorArgs = validator.getErrorArguments();
            break;
         }
      }

   }

   public boolean isPassed() {
      return this.isPassed;
   }

   public int getErrorCode() {
      return this.errorCode;
   }

   public String[] getErrorArguments() {
      return this.errorArgs;
   }

   public Object getGeneratedObject() {
      return this.generatedObject;
   }
}
