package org.jboss.weld.contexts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.jboss.weld.exceptions.WeldExceptionMessage;
import org.jboss.weld.exceptions.WeldExceptionStringMessage;

@SuppressFBWarnings(
   value = {"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"},
   justification = "Workaround for exception classes poor i8ln support"
)
public class NonexistentConversationException extends javax.enterprise.context.NonexistentConversationException {
   private static final long serialVersionUID = 2L;
   private final WeldExceptionMessage message;

   public NonexistentConversationException(Throwable throwable) {
      super(throwable);
      this.message = new WeldExceptionStringMessage(throwable.getLocalizedMessage());
   }

   public NonexistentConversationException(String message) {
      this.message = new WeldExceptionStringMessage(message);
   }

   public String getLocalizedMessage() {
      return this.getMessage();
   }

   public String getMessage() {
      return this.message.getAsString();
   }
}
