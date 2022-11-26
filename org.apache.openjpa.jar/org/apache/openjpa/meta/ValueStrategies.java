package org.apache.openjpa.meta;

import java.util.Collection;
import org.apache.commons.collections.bidimap.TreeBidiMap;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.StringDistance;
import org.apache.openjpa.util.MetaDataException;
import serp.util.Numbers;

public class ValueStrategies {
   public static final int NONE = 0;
   public static final int NATIVE = 1;
   public static final int SEQUENCE = 2;
   public static final int AUTOASSIGN = 3;
   public static final int INCREMENT = 4;
   public static final int UUID_STRING = 5;
   public static final int UUID_HEX = 6;
   private static final Localizer _loc = Localizer.forPackage(ValueStrategies.class);
   private static final TreeBidiMap _map = new TreeBidiMap();

   public static String getName(int strategy) {
      Object code = Numbers.valueOf(strategy);
      String name = (String)_map.getKey(code);
      if (name != null) {
         return name;
      } else {
         throw new IllegalArgumentException(code.toString());
      }
   }

   public static int getCode(String val, Object context) {
      if (val == null) {
         return 0;
      } else {
         Object code = _map.get(val);
         if (code != null) {
            return ((Number)code).intValue();
         } else {
            String closest = StringDistance.getClosestLevenshteinDistance(val, (Collection)_map.keySet(), 0.5F);
            String msg;
            if (closest != null) {
               msg = _loc.get("bad-value-strategy-hint", new Object[]{context, val, closest, _map.keySet()}).getMessage();
            } else {
               msg = _loc.get("bad-value-strategy", context, val, _map.keySet()).getMessage();
            }

            throw new IllegalArgumentException(msg);
         }
      }
   }

   public static void assertSupported(int strategy, MetaDataContext context, String attributeName) {
      OpenJPAConfiguration conf = context.getRepository().getConfiguration();
      boolean supported = true;
      switch (strategy) {
         case 1:
            supported = context instanceof ClassMetaData;
         case 2:
         default:
            break;
         case 3:
            supported = conf.supportedOptions().contains("openjpa.option.AutoassignValue");
            break;
         case 4:
            supported = conf.supportedOptions().contains("openjpa.option.IncrementValue");
      }

      if (!supported) {
         throw new MetaDataException(_loc.get("unsupported-value-strategy", context, getName(strategy), attributeName));
      }
   }

   static {
      _map.put("none", Numbers.valueOf(0));
      _map.put("native", Numbers.valueOf(1));
      _map.put("sequence", Numbers.valueOf(2));
      _map.put("autoassign", Numbers.valueOf(3));
      _map.put("increment", Numbers.valueOf(4));
      _map.put("uuid-string", Numbers.valueOf(5));
      _map.put("uuid-hex", Numbers.valueOf(6));
   }
}
