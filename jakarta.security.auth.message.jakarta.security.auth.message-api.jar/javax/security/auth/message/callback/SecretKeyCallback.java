package javax.security.auth.message.callback;

import javax.crypto.SecretKey;
import javax.security.auth.callback.Callback;

public class SecretKeyCallback implements Callback {
   private Request request;
   private SecretKey key;

   public SecretKeyCallback(Request request) {
      this.request = request;
   }

   public Request getRequest() {
      return this.request;
   }

   public void setKey(SecretKey key) {
      this.key = key;
   }

   public SecretKey getKey() {
      return this.key;
   }

   public static class AliasRequest implements Request {
      private String alias;

      public AliasRequest(String alias) {
         this.alias = alias;
      }

      public String getAlias() {
         return this.alias;
      }
   }

   public interface Request {
   }
}
