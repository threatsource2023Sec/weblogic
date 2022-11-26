package weblogic.jdbc.common.internal;

import java.util.Arrays;
import weblogic.common.resourcepool.PooledResourceInfo;

public class ConnectionInfo implements PooledResourceInfo {
   private final String username;
   private final char[] p;
   private final String wls_user_id;

   public ConnectionInfo(String u, String p) {
      this.username = u;
      if (p != null) {
         this.p = p.toCharArray();
      } else {
         this.p = null;
      }

      this.wls_user_id = "";
   }

   public ConnectionInfo(String u, char[] p) {
      this.username = u;
      if (p != null) {
         this.p = (char[])p.clone();
      } else {
         this.p = null;
      }

      this.wls_user_id = "";
   }

   public ConnectionInfo(String u, String p, String wluser_id) {
      this.username = u;
      if (p != null) {
         this.p = p.toCharArray();
      } else {
         this.p = null;
      }

      if (wluser_id != null) {
         this.wls_user_id = wluser_id;
      } else {
         this.wls_user_id = "";
      }

   }

   public ConnectionInfo(String u, char[] p, String wluser_id) {
      this.username = u;
      if (p != null) {
         this.p = (char[])p.clone();
      } else {
         this.p = null;
      }

      if (wluser_id != null) {
         this.wls_user_id = wluser_id;
      } else {
         this.wls_user_id = "";
      }

   }

   public boolean equals(PooledResourceInfo info) {
      try {
         return this.username.equals(((ConnectionInfo)info).getUsername()) && this.wls_user_id.equals(((ConnectionInfo)info).getWLUserID());
      } catch (Throwable var3) {
         return false;
      }
   }

   public String getUsername() {
      return this.username;
   }

   public String getPassword() {
      return this.p == null ? null : new String(this.p);
   }

   public String getWLUserID() {
      return this.wls_user_id;
   }

   public String toString() {
      return this.username + "/" + this.wls_user_id;
   }

   public void cleanup() {
      if (this.p != null) {
         Arrays.fill(this.p, '\u0000');
      }

   }
}
