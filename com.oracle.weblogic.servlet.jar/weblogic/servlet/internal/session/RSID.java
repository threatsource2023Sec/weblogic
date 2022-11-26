package weblogic.servlet.internal.session;

import java.util.Map;
import java.util.StringTokenizer;
import weblogic.protocol.ServerIdentity;

public final class RSID implements SessionConstants {
   public String id;
   private ServerIdentity primary;
   private ServerIdentity secondary;
   private static final boolean DEBUG = false;

   public RSID(String sessionid) {
      this(sessionid, (Map)null);
   }

   public RSID(String sessionid, Map servers) {
      this.id = null;
      if (sessionid != null) {
         try {
            StringTokenizer st = new StringTokenizer(sessionid, "!");
            int total = st.countTokens();
            if (total < 1) {
               return;
            }

            this.id = st.nextToken();
            if (servers == null || servers.isEmpty() || total < 2) {
               return;
            }

            String jvmid2 = null;
            String jvmid1;
            if (total <= 4) {
               jvmid1 = st.nextToken();
               if (total > 2) {
                  jvmid2 = st.nextToken();
               }
            } else {
               jvmid1 = st.nextToken();
               if (total > 5) {
                  st.nextToken();
                  st.nextToken();
                  st.nextToken();
                  jvmid2 = st.nextToken();
               }
            }

            this.findPrimarySecondary(jvmid1, jvmid2, servers);
         } catch (Exception var7) {
            HTTPSessionLogger.logCookieFormatError(sessionid, var7);
         }

      }
   }

   private void findPrimarySecondary(String jvmid1, String jvmid2, Map servers) {
      boolean noSecondary = jvmid2 == null || jvmid2.equals("NONE");
      Integer id1 = new Integer(jvmid1);
      this.primary = (ServerIdentity)servers.get(id1);
      if (!noSecondary) {
         Integer id2 = new Integer(jvmid2);
         this.secondary = (ServerIdentity)servers.get(id2);
      }

   }

   public ServerIdentity getPrimary() {
      return this.primary;
   }

   public ServerIdentity getSecondary() {
      return this.secondary;
   }

   public String getPrimaryServerName() {
      return this.primary == null ? null : this.primary.getServerName();
   }

   public String getSecondaryServerName() {
      return this.secondary == null ? null : this.secondary.getServerName();
   }

   public static String getID(String id) {
      return SessionData.getID(id);
   }

   public String toString() {
      return "[ID: " + this.id + " Primary: " + this.primary + " Secondary: " + this.secondary + "]";
   }
}
