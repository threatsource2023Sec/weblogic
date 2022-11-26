package org.apache.openjpa.meta;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.StringDistance;

public class UpdateStrategies {
   public static final int NONE = 0;
   public static final int IGNORE = 1;
   public static final int RESTRICT = 2;
   private static final Localizer _loc = Localizer.forPackage(UpdateStrategies.class);

   public static String getName(int strategy) {
      switch (strategy) {
         case 0:
            return "none";
         case 1:
            return "ignore";
         case 2:
            return "restrict";
         default:
            throw new IllegalArgumentException(String.valueOf(strategy));
      }
   }

   public static int getCode(String val, Object context) {
      if ("none".equals(val)) {
         return 0;
      } else if ("ignore".equals(val)) {
         return 1;
      } else if ("restrict".equals(val)) {
         return 2;
      } else {
         List opts = Arrays.asList("none", "ignore", "restrict");
         String closest = StringDistance.getClosestLevenshteinDistance(val, (Collection)opts, 0.5F);
         String msg;
         if (closest != null) {
            msg = _loc.get("bad-update-strategy-hint", new Object[]{context, val, closest, opts}).getMessage();
         } else {
            msg = _loc.get("bad-update-strategy", context, val, opts).getMessage();
         }

         throw new IllegalArgumentException(msg);
      }
   }
}
