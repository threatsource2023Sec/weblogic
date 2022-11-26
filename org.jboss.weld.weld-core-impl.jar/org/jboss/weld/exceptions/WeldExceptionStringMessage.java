package org.jboss.weld.exceptions;

import java.io.Serializable;

public class WeldExceptionStringMessage implements Serializable, WeldExceptionMessage {
   private static final long serialVersionUID = 2L;
   private String message;

   public WeldExceptionStringMessage(String message) {
      this.message = message;
   }

   public String getAsString() {
      return this.message;
   }
}
