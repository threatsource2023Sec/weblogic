package weblogic.connector.utils;

public class PropertyNameNormalizerFactory {
   private static final PropertyNameNormalizer JCA15PropertyNameNormalizer = new JCA15PropertyNameNormalizer();
   private static final PropertyNameNormalizer JCA16PropertyNameNormalizer = new JCA16PropertyNameNormalizer();

   public static PropertyNameNormalizer getPropertyNameNormalizer(String version) {
      return version.compareTo("1.6") < 0 ? JCA15PropertyNameNormalizer : JCA16PropertyNameNormalizer;
   }
}
