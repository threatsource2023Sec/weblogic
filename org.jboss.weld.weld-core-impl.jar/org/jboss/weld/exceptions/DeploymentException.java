package org.jboss.weld.exceptions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Iterator;
import java.util.List;

@SuppressFBWarnings(
   value = {"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"},
   justification = "Renaming the class would break backwards compatibility."
)
public class DeploymentException extends javax.enterprise.inject.spi.DeploymentException {
   private static final long serialVersionUID = 8014646336322875707L;
   private final WeldExceptionMessage message;

   public DeploymentException(Throwable throwable) {
      super(throwable);
      this.message = new WeldExceptionStringMessage(throwable.getLocalizedMessage());
   }

   public DeploymentException(List errors) {
      super(DeploymentException.class.getName());
      this.message = new WeldExceptionListMessage(errors);
      Iterator var2 = errors.iterator();

      while(var2.hasNext()) {
         Throwable error = (Throwable)var2.next();
         this.addSuppressed(error);
      }

   }

   public DeploymentException(String message) {
      super(DeploymentException.class.getName());
      this.message = new WeldExceptionStringMessage(message);
   }

   public DeploymentException(String message, Throwable throwable) {
      super(throwable);
      this.message = new WeldExceptionStringMessage(message);
   }

   public String getLocalizedMessage() {
      return this.getMessage();
   }

   public String getMessage() {
      return this.message.getAsString();
   }
}
