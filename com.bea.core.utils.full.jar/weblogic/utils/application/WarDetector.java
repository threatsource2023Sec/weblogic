package weblogic.utils.application;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public enum WarDetector {
   instance;

   private final Set suffixes = new HashSet(Arrays.asList(".war"));
   private final Set markers;

   private WarDetector() {
      this.markers = new HashSet(Arrays.asList("WEB-INF" + File.separator + "web.xml", "WEB-INF" + File.separator + "weblogic.xml"));
   }

   public void addSuffix(String suffix) {
      this.suffixes.add(suffix);
   }

   public void addExplodedApplicationMarker(String marker) {
      this.markers.add(marker);
   }

   public String[] getSuffixes() {
      return (String[])((String[])this.suffixes.toArray(new String[0]));
   }

   public boolean suffixed(String name) {
      Iterator var2 = this.suffixes.iterator();

      String suffix;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         suffix = (String)var2.next();
      } while(!name.endsWith(suffix));

      return true;
   }

   public String stem(String name) {
      Iterator var2 = this.suffixes.iterator();

      String suffix;
      do {
         if (!var2.hasNext()) {
            return name;
         }

         suffix = (String)var2.next();
      } while(!name.endsWith(suffix));

      return name.substring(0, name.indexOf(suffix));
   }

   public boolean isWar(File file) {
      if (file.isDirectory()) {
         String[] explodedAppMarkers = (String[])((String[])this.markers.toArray(new String[0]));
         String[] var3 = explodedAppMarkers;
         int var4 = explodedAppMarkers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String marker = var3[var5];
            if ((new File(file, marker)).exists()) {
               return true;
            }
         }

         if (this.suffixed(file.getName())) {
            return true;
         }
      }

      return this.suffixed(file.getName().toLowerCase());
   }
}
