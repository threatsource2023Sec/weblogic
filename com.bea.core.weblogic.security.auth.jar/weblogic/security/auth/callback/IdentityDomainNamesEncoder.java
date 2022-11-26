package weblogic.security.auth.callback;

public class IdentityDomainNamesEncoder {
   public static final String IDD_USER_GRP_NAME_PREFIX = "_#NAM#_";
   public static final String IDD_DOMAIN_NAME_PREFIX = "_#IDD#_";
   private static final String IDD_NULL_NAME = "_#NULL#_";
   private static final int IDD_USER_GRP_NAME_LEN = "_#NAM#_".length();
   private static final int IDD_DOMAIN_NAME_LEN = "_#IDD#_".length();

   public static String encodeNames(String name, String identityDomain) throws IllegalArgumentException {
      String localName = name == null ? "_#NULL#_" : name;
      String localIdd = identityDomain == null ? "_#NULL#_" : identityDomain;
      return "_#NAM#_" + localName + "_#IDD#_" + localIdd;
   }

   public static IdentityDomainNames decodeNames(String encodedNames) throws IllegalArgumentException {
      validate(encodedNames);
      if (!isEncodedNames(encodedNames)) {
         throw new IllegalArgumentException("no encoded names");
      } else {
         String name = null;
         String iddName = null;
         int iddDomainInx = encodedNames.indexOf("_#IDD#_", IDD_USER_GRP_NAME_LEN);
         if (iddDomainInx == -1) {
            throw new IllegalArgumentException("improperly encoded names");
         } else {
            name = encodedNames.substring(IDD_USER_GRP_NAME_LEN, iddDomainInx);
            iddName = encodedNames.substring(iddDomainInx + IDD_DOMAIN_NAME_LEN);
            name = name.equals("_#NULL#_") ? null : name;
            iddName = iddName.equals("_#NULL#_") ? null : iddName;
            return new IdentityDomainNames(name, iddName);
         }
      }
   }

   public static boolean isEncodedNames(String test) throws IllegalArgumentException {
      validate(test);
      return test.startsWith("_#NAM#_");
   }

   private static void validate(String name) {
      if (name == null || name.isEmpty()) {
         throw new IllegalArgumentException("String cannot be null or empty");
      }
   }
}
