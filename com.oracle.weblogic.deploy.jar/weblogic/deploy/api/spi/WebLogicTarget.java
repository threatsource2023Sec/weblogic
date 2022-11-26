package weblogic.deploy.api.spi;

import javax.enterprise.deploy.spi.Target;
import weblogic.deploy.api.shared.WebLogicTargetType;

public abstract class WebLogicTarget extends WebLogicTargetType implements Target {
   private static final long serialVersionUID = 1L;

   public WebLogicTarget() {
   }

   protected WebLogicTarget(int type) {
      super(type);
   }

   public abstract String getName();

   public abstract String getDescription();

   public abstract boolean isServer();

   public abstract boolean isCluster();

   public abstract boolean isVirtualHost();

   public abstract boolean isVirtualTarget();

   public abstract boolean isJMSServer();

   public abstract boolean isSAFAgent();

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (o == this) {
         return true;
      } else {
         if (o instanceof WebLogicTarget) {
            WebLogicTarget t = (WebLogicTarget)o;
            if (this.hashCode() == t.hashCode()) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return this.getName().concat(this.getDescription()).hashCode();
   }
}
