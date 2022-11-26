package weblogic.servlet;

import javax.servlet.ServletException;

public class FilterUnavailableException extends ServletException {
   public FilterUnavailableException() {
   }

   public FilterUnavailableException(String message) {
      super(message);
   }
}
