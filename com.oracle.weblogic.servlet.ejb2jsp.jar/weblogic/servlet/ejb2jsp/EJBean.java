package weblogic.servlet.ejb2jsp;

public class EJBean {
   private String name;
   private String remoteType;
   private String homeType;
   private String jndiName;
   private boolean isEntity;
   private boolean isSession;
   private boolean isStatefulSession;

   public String toString() {
      String type = null;
      if (this.isEntity) {
         type = "ENTITY";
      } else if (this.isStatefulSession) {
         type = "STATEFUL";
      } else {
         type = "STATELESS";
      }

      return "[EJBean: name=" + this.name + " rt=" + this.remoteType + " ht=" + this.homeType + " jn=" + this.jndiName + " type=" + type + "]";
   }

   public EJBean(String n, String rt, String ht, String jn, boolean isE, boolean isS, boolean isSS) {
      this.name = n;
      this.remoteType = rt;
      this.homeType = ht;
      this.jndiName = jn;
      this.isEntity = isE;
      this.isSession = isS;
      this.isStatefulSession = isSS;
   }

   public String getEJBName() {
      return this.name;
   }

   public String getRemoteInterfaceName() {
      return this.remoteType;
   }

   public String getHomeInterfaceName() {
      return this.homeType;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public boolean isEntityBean() {
      return this.isEntity;
   }

   public boolean isSessionBean() {
      return this.isSession;
   }

   public boolean isStatefulSessionBean() {
      return this.isStatefulSession;
   }
}
