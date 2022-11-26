package weblogic.messaging.interception.interfaces;

public abstract class InterceptionPointNameDescriptor {
   public static final String DEFAULT_PREFIX_NAME = "NAME_SEGMENT";
   public static int UNRESTRICTED_VALUE = Integer.MAX_VALUE;

   public static String getDefaultPrefixName() {
      return "NAME_SEGMENT";
   }

   public abstract String getTitle();

   public abstract int getTotalNumberOfUniqueValue();

   public abstract boolean isValid(String var1);
}
