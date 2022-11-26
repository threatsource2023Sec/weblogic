package weblogic.nodemanager.util;

public class ServerInfo {
   private String name;
   private String type;
   private String state;
   private boolean configComplete;

   public ServerInfo(String name, String type) {
      this(name, type, (String)null);
   }

   public ServerInfo(String name, String type, String state) {
      this.name = name;
      this.type = type;
      this.state = state;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getState() {
      return this.state;
   }

   public void setState(String state) {
      this.state = state;
   }

   public boolean isConfigComplete() {
      return this.configComplete;
   }

   public void setConfigComplete(boolean configComplete) {
      this.configComplete = configComplete;
   }
}
