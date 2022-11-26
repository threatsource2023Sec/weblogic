package weblogic.descriptor;

public class BootstrapProperties {
   public static final BooleanSystemProperty PRODUCTION_MODE = new BooleanSystemProperty("weblogic.ProductionModeEnabled");
   private static boolean INCLUDE_OBSOLETE_PROPS_IN_DIFF = false;

   public static void setIncludeObsoletePropsInDiff(boolean doInclude) {
      INCLUDE_OBSOLETE_PROPS_IN_DIFF = doInclude;
   }

   public static boolean getIncludeObsoletePropsInDiff() {
      return INCLUDE_OBSOLETE_PROPS_IN_DIFF;
   }
}
