package weblogic.security.jacc;

import javax.security.jacc.PolicyContextException;
import javax.security.jacc.PolicyContextHandler;

public class DelegatingPolicyContextHandler implements PolicyContextHandler {
   private String[] keys;

   public DelegatingPolicyContextHandler(String[] keys) {
      this.keys = keys;
   }

   public Object getContext(String key, Object data) throws PolicyContextException {
      if (data != null && data instanceof PolicyContextHandlerData) {
         PolicyContextHandlerData handlerData = (PolicyContextHandlerData)data;
         return handlerData.getContext(key);
      } else {
         return null;
      }
   }

   public String[] getKeys() throws PolicyContextException {
      return this.keys;
   }

   public boolean supports(String key) throws PolicyContextException {
      if (key != null && this.keys != null) {
         for(int i = 0; i < this.keys.length; ++i) {
            if (key.equals(this.keys[i])) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
