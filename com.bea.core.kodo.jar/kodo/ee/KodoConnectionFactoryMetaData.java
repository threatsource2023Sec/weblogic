package kodo.ee;

import javax.resource.cci.ResourceAdapterMetaData;
import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.lib.util.Localizer;

public class KodoConnectionFactoryMetaData implements ResourceAdapterMetaData {
   private static Localizer _loc = Localizer.forPackage(KodoConnectionFactoryMetaData.class);

   public String getAdapterName() {
      return _loc.get("adapter-name").getMessage();
   }

   public String getAdapterShortDescription() {
      return _loc.get("adapter-short-desc").getMessage();
   }

   public String getAdapterVendorName() {
      return _loc.get("adapter-vendor-name").getMessage();
   }

   public String getSpecVersion() {
      return "1.0";
   }

   public String getAdapterVersion() {
      return OpenJPAVersion.VERSION_NUMBER;
   }

   public boolean supportsLocalTransactionDemarcation() {
      return false;
   }

   public boolean supportsExecuteWithInputAndOutputRecord() {
      return false;
   }

   public boolean supportsExecuteWithInputRecordOnly() {
      return false;
   }

   public String[] getInteractionSpecsSupported() {
      return new String[0];
   }
}
