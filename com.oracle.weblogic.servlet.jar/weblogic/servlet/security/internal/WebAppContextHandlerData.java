package weblogic.servlet.security.internal;

import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import weblogic.security.jacc.PolicyContextHandlerData;

public class WebAppContextHandlerData implements PolicyContextHandlerData {
   public static final String HTTP_REQUEST_KEY = "javax.servlet.http.HttpServletRequest";
   private static final String[] keys = new String[]{"javax.servlet.http.HttpServletRequest"};
   private final HttpServletRequest request;

   public WebAppContextHandlerData(HttpServletRequest request) {
      this.request = request;
   }

   public Object getContext(String key) throws PolicyContextException {
      return key != null && key.equals("javax.servlet.http.HttpServletRequest") ? this.request : null;
   }

   public static String[] getKeys() {
      return keys;
   }
}
