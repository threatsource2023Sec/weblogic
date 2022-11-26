package kodo.conf;

import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.StringValue;

public class LicenseKey extends StringValue {
   public static final String KEY = "LicenseKey";
   private final OpenJPAConfiguration conf;

   public LicenseKey(OpenJPAConfiguration c) {
      super("LicenseKey");
      this.conf = c;
   }

   public Class getScope() {
      return this.getClass();
   }
}
