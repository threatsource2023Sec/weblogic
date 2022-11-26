package weblogic.management.internal;

import java.io.Serializable;

public class ServerInfo implements Serializable {
   private static final long serialVersionUID = 3990262384160459292L;
   private String serverName = null;
   private String adminURL = null;

   public ServerInfo(String name, String url) {
      this.serverName = name;
      this.adminURL = url;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getAdministrationURL() {
      return this.adminURL;
   }

   public void setServerName(String name) {
      this.serverName = name;
   }

   public void setAdministrationURL(String url) {
      this.adminURL = url;
   }

   public String getT3URL() {
      return this.getAdministrationURL();
   }
}
