package weblogic.security.jaspic.servlet;

import javax.security.auth.Subject;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletPasswordValidationCallback extends PasswordValidationCallback {
   private HttpServletRequest request;
   private HttpServletResponse response;

   public ServletPasswordValidationCallback(Subject subject, String username, char[] password, HttpServletRequest request, HttpServletResponse response) {
      super(subject, username, password);
      this.request = request;
      this.response = response;
   }

   public HttpServletRequest getRequest() {
      return this.request;
   }

   public HttpServletResponse getResponse() {
      return this.response;
   }
}
