package kodo.conf;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.lib.util.Localizer;

public class KodoVersion {
   public static final String VERSION_NUMBER;
   private static final long RELEASE_SECONDS;
   public static final Date RELEASE_DATE;
   public static final String VERSION_ID;
   public static final String VENDOR_NAME = "BEA Kodo";
   public static final int MAJOR_RELEASE;
   public static final int MINOR_RELEASE;
   public static final int PATCH_RELEASE;
   public static final String RELEASE_STATUS;
   private static final Localizer _loc = Localizer.forPackage(KodoVersion.class);

   public static void main(String[] args) {
      System.out.println((new KodoVersion()).toString());
   }

   public static Object getInitializationBanner() {
      if (KodoVersion.class.getClassLoader().getResource("weblogic/version.class") == null && !Patch.getPatches().isEmpty()) {
         List patches = Patch.getPatches();
         StringBuffer buf = new StringBuffer(32 * patches.size());
         String eol = System.getProperty("line.separator");
         Iterator iter = patches.iterator();

         while(iter.hasNext()) {
            buf.append("    ").append(iter.next());
            if (iter.hasNext()) {
               buf.append(eol);
            }
         }

         return _loc.get("kodo-initialization-banner-with-patches", VERSION_NUMBER, buf.toString());
      } else {
         return _loc.get("kodo-initialization-banner", VERSION_NUMBER);
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer(2400);
      buf.append("BEA Kodo").append(" ");
      buf.append(VERSION_NUMBER);
      buf.append("\n");
      buf.append("version id: ").append(VERSION_ID);
      buf.append("\n");
      buf.append("\n");
      buf.append("Built with ");
      (new OpenJPAVersion()).appendOpenJPABanner(buf);
      buf.append("\n");
      if (!Patch.getPatches().isEmpty()) {
         buf.append("Installed patches:\n");
         Iterator iter = Patch.getPatches().iterator();

         while(iter.hasNext()) {
            buf.append("    ").append(iter.next()).append("\n");
         }

         buf.append("\n");
      }

      this.appendProperty("os.name", buf).append("\n");
      this.appendProperty("os.version", buf).append("\n");
      this.appendProperty("os.arch", buf).append("\n\n");
      this.appendProperty("java.version", buf).append("\n");
      this.appendProperty("java.vendor", buf).append("\n\n");
      this.appendProperty("user.dir", buf).append("\n\n");
      buf.append("java.class.path:\n");
      StringTokenizer tok = new StringTokenizer(System.getProperty("java.class.path"), File.pathSeparator);

      while(tok.hasMoreTokens()) {
         buf.append("\t").append(tok.nextToken());
         if (tok.hasMoreTokens()) {
            buf.append("\n");
         }
      }

      return buf.toString();
   }

   private StringBuffer appendProperty(String prop, StringBuffer buf) {
      buf.append(prop).append(": ").append(System.getProperty(prop));
      return buf;
   }

   static {
      int major = 0;
      int minor = 0;
      int patch = 0;
      String status = null;
      long secs = 0L;
      String id = null;

      try {
         InputStream in = KodoVersion.class.getResourceAsStream("version.properties");
         Properties p = new Properties();
         p.load(in);
         major = Integer.parseInt(p.getProperty("MAJOR_RELEASE"));
         minor = Integer.parseInt(p.getProperty("MINOR_RELEASE"));
         patch = Integer.parseInt(p.getProperty("PATCH_RELEASE"));
         status = p.getProperty("RELEASE_STATUS").trim();
         secs = Long.parseLong(p.getProperty("RELEASE_SECONDS"));
         id = p.getProperty("VERSION_ID").trim();
         in.close();
      } catch (Exception var9) {
      }

      MAJOR_RELEASE = major;
      MINOR_RELEASE = minor;
      PATCH_RELEASE = patch;
      RELEASE_STATUS = status;
      RELEASE_SECONDS = secs;
      VERSION_ID = id;
      if (RELEASE_STATUS != null && !"".equals(RELEASE_STATUS)) {
         VERSION_NUMBER = MAJOR_RELEASE + "." + MINOR_RELEASE + "." + PATCH_RELEASE + RELEASE_STATUS;
      } else {
         VERSION_NUMBER = MAJOR_RELEASE + "." + MINOR_RELEASE + "." + PATCH_RELEASE;
      }

      RELEASE_DATE = new Date(RELEASE_SECONDS * 1000L);
   }
}
