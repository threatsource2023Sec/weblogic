package weblogic.corba.j2ee.naming;

import java.util.Hashtable;

abstract class JmxAuthenticationSupport {
   private static final int JMX_CREDENTIALS_LENGTH = 2;

   static boolean useJmxCredentials(Hashtable env) {
      return env != null && hasNoUsernameOrPassword(env) && hasValidJmxCredentials(env);
   }

   private static boolean hasNoUsernameOrPassword(Hashtable env) {
      return !env.containsKey("java.naming.security.principal") && !env.containsKey("java.naming.security.credentials");
   }

   private static boolean hasValidJmxCredentials(Hashtable env) {
      return env.containsKey("jmx.remote.credentials") && isValidJmxCredentials(env.get("jmx.remote.credentials"));
   }

   private static boolean isValidJmxCredentials(Object credentials) {
      return credentials instanceof String[] && ((String[])((String[])credentials)).length == 2;
   }

   static void establishJmxCredentials(Hashtable env) {
      String[] jmxCredentials = (String[])((String[])env.get("jmx.remote.credentials"));
      env.put("java.naming.security.principal", jmxCredentials[0]);
      env.put("java.naming.security.credentials", jmxCredentials[1]);
   }
}
