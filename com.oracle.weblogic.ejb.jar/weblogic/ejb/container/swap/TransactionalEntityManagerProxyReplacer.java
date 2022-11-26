package weblogic.ejb.container.swap;

import java.io.Serializable;
import javax.persistence.SynchronizationType;

public class TransactionalEntityManagerProxyReplacer implements Serializable {
   private String appName;
   private String moduleName;
   private String unitName;
   private SynchronizationType synchronizationType;

   public TransactionalEntityManagerProxyReplacer(String appName, String moduleName, String unitName, SynchronizationType syncType) {
      this.appName = appName;
      this.moduleName = moduleName;
      this.unitName = unitName;
      this.synchronizationType = syncType;
   }

   public SynchronizationType getSynchronizationType() {
      return this.synchronizationType;
   }

   public String getAppName() {
      return this.appName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getUnitName() {
      return this.unitName;
   }
}
