package org.apache.openjpa.conf;

import java.io.File;
import java.io.InputStream;
import java.security.AccessController;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class OpenJPAVersion {
   public static final String VERSION_NUMBER;
   public static final String VERSION_ID;
   public static final String VENDOR_NAME = "OpenJPA";
   public static final int MAJOR_RELEASE;
   public static final int MINOR_RELEASE;
   public static final int PATCH_RELEASE;
   public static final String RELEASE_STATUS;
   public static final String REVISION_NUMBER;

   public static void main(String[] args) {
      System.out.println((new OpenJPAVersion()).toString());
   }

   public String toString() {
      StringBuffer buf = new StringBuffer(2400);
      this.appendOpenJPABanner(buf);
      buf.append("\n");
      this.appendProperty("os.name", buf).append("\n");
      this.appendProperty("os.version", buf).append("\n");
      this.appendProperty("os.arch", buf).append("\n\n");
      this.appendProperty("java.version", buf).append("\n");
      this.appendProperty("java.vendor", buf).append("\n\n");
      buf.append("java.class.path:\n");
      StringTokenizer tok = new StringTokenizer((String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction("java.class.path")), File.pathSeparator);

      while(tok.hasMoreTokens()) {
         buf.append("\t").append(tok.nextToken());
         buf.append("\n");
      }

      buf.append("\n");
      this.appendProperty("user.dir", buf);
      return buf.toString();
   }

   public void appendOpenJPABanner(StringBuffer buf) {
      buf.append("OpenJPA").append(" ");
      buf.append(VERSION_NUMBER);
      buf.append("\n");
      buf.append("version id: ").append(VERSION_ID);
      buf.append("\n");
      buf.append("Apache svn revision: ").append(REVISION_NUMBER);
      buf.append("\n");
   }

   private StringBuffer appendProperty(String prop, StringBuffer buf) {
      return buf.append(prop).append(": ").append((String)AccessController.doPrivileged(J2DoPrivHelper.getPropertyAction(prop)));
   }

   static {
      Properties revisionProps = new Properties();

      try {
         InputStream in = OpenJPAVersion.class.getResourceAsStream("/META-INF/org.apache.openjpa.revision.properties");
         if (in != null) {
            try {
               revisionProps.load(in);
            } finally {
               in.close();
            }
         }
      } catch (Exception var16) {
      }

      String vers = revisionProps.getProperty("openjpa.version");
      if (vers == null || "".equals(vers.trim())) {
         vers = "0.0.0";
      }

      VERSION_NUMBER = vers;
      StringTokenizer tok = new StringTokenizer(VERSION_NUMBER, ".-");

      int major;
      try {
         major = tok.hasMoreTokens() ? Integer.parseInt(tok.nextToken()) : 0;
      } catch (Exception var14) {
         major = 0;
      }

      int minor;
      try {
         minor = tok.hasMoreTokens() ? Integer.parseInt(tok.nextToken()) : 0;
      } catch (Exception var13) {
         minor = 0;
      }

      int patch;
      try {
         patch = tok.hasMoreTokens() ? Integer.parseInt(tok.nextToken()) : 0;
      } catch (Exception var12) {
         patch = 0;
      }

      String revision = revisionProps.getProperty("revision.number");
      MAJOR_RELEASE = major;
      MINOR_RELEASE = minor;
      PATCH_RELEASE = patch;
      RELEASE_STATUS = tok.hasMoreTokens() ? tok.nextToken("!") : "";
      REVISION_NUMBER = revision;
      VERSION_ID = "openjpa-" + VERSION_NUMBER + "-r" + REVISION_NUMBER;
   }
}
