package weblogic.descriptor.beangen;

import com.bea.util.jam.JElement;

public class BeanGenerationException extends RuntimeException {
   public BeanGenerationException(String message) {
      super(message);
   }

   public BeanGenerationException(String message, JElement pos) {
      super(constructMessage(message, pos));
   }

   public BeanGenerationException(String message, Throwable cause) {
      super(message, cause);
   }

   public BeanGenerationException(String msg, Throwable cause, JElement pos) {
      super(constructMessage(msg, pos), cause);
   }

   private static String constructMessage(String msg, JElement pos) {
      return Context.get().getLog().constructMessage("Error", msg, pos);
   }
}
