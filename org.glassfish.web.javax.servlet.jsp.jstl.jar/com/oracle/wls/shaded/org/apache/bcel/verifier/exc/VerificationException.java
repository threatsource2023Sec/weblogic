package com.oracle.wls.shaded.org.apache.bcel.verifier.exc;

public abstract class VerificationException extends VerifierConstraintViolatedException {
   VerificationException() {
   }

   VerificationException(String message) {
      super(message);
   }
}
