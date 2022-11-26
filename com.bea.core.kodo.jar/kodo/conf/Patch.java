package kodo.conf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class Patch {
   private static List patches;
   private String title;
   private String version;
   private String vendor;

   public static synchronized List getPatches() {
      if (patches == null) {
         List l = new ArrayList();

         try {
            Enumeration resources = Patch.class.getClassLoader().getResources("META-INF/MANIFEST.MF");

            while(resources != null && resources.hasMoreElements()) {
               URL url = (URL)resources.nextElement();

               try {
                  Patch p = parsePatch(url);
                  if (p != null) {
                     l.add(p);
                  }
               } catch (IOException var4) {
               }
            }

            patches = Collections.unmodifiableList(l);
         } catch (IOException var5) {
         }
      }

      return patches;
   }

   static Patch parsePatch(URL patchUrl) throws IOException {
      BufferedReader in = new BufferedReader(new InputStreamReader(patchUrl.openStream()));
      Patch p = new Patch();

      while(in.ready() && (p.version == null || p.title == null || p.vendor == null)) {
         String line = in.readLine();
         if (line == null) {
            break;
         }

         String[] tuple = tokenize(line);
         if (p.version == null && "Implementation-Version".equals(tuple[0])) {
            p.version = tuple[1];
         } else if (p.title == null && "Implementation-Title".equals(tuple[0])) {
            p.title = tuple[1];
         } else if (p.vendor == null && "Implementation-Vendor".equals(tuple[0])) {
            p.vendor = tuple[1];
         }
      }

      return p.title == null || !p.title.startsWith("Kodo") && !p.title.startsWith("WebLogic") && !p.title.startsWith("AquaLogic") ? null : p;
   }

   private static String[] tokenize(String line) {
      String[] tuple = new String[2];
      int colon = line.indexOf(":");
      if (colon > 0) {
         tuple[0] = line.substring(0, colon).trim();
         tuple[1] = line.substring(colon + 1).trim();
      }

      return tuple;
   }

   public String getTitle() {
      return this.title;
   }

   public String getVendor() {
      return this.vendor;
   }

   public String getVersion() {
      return this.version;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer(32);
      buf.append(this.title);
      if (this.version != null) {
         buf.append(" v").append(this.version);
      }

      return buf.toString();
   }
}
