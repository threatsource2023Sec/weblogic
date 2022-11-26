package weblogic.application.naming;

public class JavaAppNamingHelper {
   public static String indivisableJndiApplicationName(String jndiName) {
      return jndiName.replace('.', '$');
   }
}
