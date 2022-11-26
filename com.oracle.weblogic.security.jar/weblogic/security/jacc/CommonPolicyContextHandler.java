package weblogic.security.jacc;

import javax.security.jacc.PolicyContextException;
import javax.security.jacc.PolicyContextHandler;
import weblogic.security.Security;

public class CommonPolicyContextHandler implements PolicyContextHandler {
   public static final String SUBJECT_KEY = "javax.security.auth.Subject.container";
   private static final String[] keys = new String[]{"javax.security.auth.Subject.container"};

   public Object getContext(String key, Object data) throws PolicyContextException {
      return key != null && data != null && key.equals("javax.security.auth.Subject.container") ? Security.getCurrentSubject() : null;
   }

   public String[] getKeys() throws PolicyContextException {
      return keys;
   }

   public boolean supports(String key) throws PolicyContextException {
      return key != null && key.equals("javax.security.auth.Subject.container");
   }
}
