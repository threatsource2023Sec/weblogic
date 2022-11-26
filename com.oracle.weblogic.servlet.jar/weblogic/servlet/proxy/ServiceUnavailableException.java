package weblogic.servlet.proxy;

import javax.servlet.ServletException;

class ServiceUnavailableException extends ServletException {
   ServiceUnavailableException() {
      super("No server available to process request");
   }
}
