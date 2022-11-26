package com.bea.httppubsub.bayeux.handlers.validator;

public interface Validator {
   void validate();

   boolean isPassed();

   int getErrorCode();

   String[] getErrorArguments();

   Object getGeneratedObject();
}
