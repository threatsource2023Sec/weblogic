package org.glassfish.hk2.runlevel.internal;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.runlevel.ErrorInformation;

public class ErrorInformationImpl implements ErrorInformation {
   private final Throwable error;
   private ErrorInformation.ErrorAction action;
   private final Descriptor descriptor;

   ErrorInformationImpl(Throwable error, ErrorInformation.ErrorAction action, Descriptor descriptor) {
      this.error = error;
      this.action = action;
      this.descriptor = descriptor;
   }

   public Throwable getError() {
      return this.error;
   }

   public ErrorInformation.ErrorAction getAction() {
      return this.action;
   }

   public void setAction(ErrorInformation.ErrorAction action) {
      if (action == null) {
         throw new IllegalArgumentException("action may not be null in setAction");
      } else {
         this.action = action;
      }
   }

   public Descriptor getFailedDescriptor() {
      return this.descriptor;
   }

   public String toString() {
      String descriptorString = this.descriptor == null ? "null" : this.descriptor.getImplementation();
      return "ErrorInformationImpl(" + this.action + "," + descriptorString + "," + System.identityHashCode(this) + ")";
   }
}
