package javax.security.enterprise.identitystore;

import java.util.Map;

public interface PasswordHash {
   default void initialize(Map parameters) {
   }

   String generate(char[] var1);

   boolean verify(char[] var1, String var2);
}
