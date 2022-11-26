package org.apache.openjpa.lib.meta;

import java.security.AccessController;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;

public class SourceTrackers {
   private static final Localizer _loc = Localizer.forPackage(SourceTrackers.class);
   private static final String SEP = J2DoPrivHelper.getLineSeparator();

   public static String getSourceLocationMessage(SourceTracker[] trackers) {
      StringBuffer buf = new StringBuffer(20 * (trackers.length + 1));
      buf.append(_loc.get("source-trackers-location-header")).append(SEP);

      for(int i = 0; i < trackers.length; ++i) {
         String sourceFilePath = trackers[i].getSourceFile() == null ? _loc.get("source-tracker-file-unknown").getMessage() : (String)AccessController.doPrivileged(J2DoPrivHelper.getAbsolutePathAction(trackers[i].getSourceFile()));
         buf.append("  ").append(_loc.get("source-trackers-location-line-item", trackers[i].getResourceName(), sourceFilePath));
         if (i < trackers.length - 1) {
            buf.append(SEP);
         }
      }

      return buf.toString();
   }
}
