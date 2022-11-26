package com.bea.core.repackaged.springframework.aop.framework.adapter;

public class UnknownAdviceTypeException extends IllegalArgumentException {
   public UnknownAdviceTypeException(Object advice) {
      super("Advice object [" + advice + "] is neither a supported subinterface of [org.aopalliance.aop.Advice] nor an [org.springframework.aop.Advisor]");
   }

   public UnknownAdviceTypeException(String message) {
      super(message);
   }
}
