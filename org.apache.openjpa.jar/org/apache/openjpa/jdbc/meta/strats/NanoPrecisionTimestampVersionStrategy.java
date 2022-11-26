package org.apache.openjpa.jdbc.meta.strats;

import org.apache.openjpa.lib.util.TimestampHelper;

public class NanoPrecisionTimestampVersionStrategy extends TimestampVersionStrategy {
   public static final String ALIAS = "nano-timestamp";

   public String getAlias() {
      return "nano-timestamp";
   }

   protected Object nextVersion(Object version) {
      return TimestampHelper.getNanoPrecisionTimestamp();
   }
}
