package weblogic.management.provider.internal.federatedconfig;

public class FederatedConfigUtils {
   public static final String LINE_SEP = System.getProperty("line.separator");

   public static boolean isEmpty(String buf) {
      return buf.length() == 0;
   }
}
