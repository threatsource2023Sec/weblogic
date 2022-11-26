package weblogic.ejb.spi;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.ModuleRegistry;
import weblogic.cluster.GroupMessage;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.rmi.spi.HostID;

public class InvalidationMessage implements GroupMessage {
   private static final long serialVersionUID = -8198958808164681941L;
   private String applicationId;
   private String moduleId;
   private String ejbName;
   private Object pk;
   private Collection pks;

   public String toString() {
      return "[InvalidationMessage]: applicationName: " + this.applicationId + " module: " + this.moduleId + " ejbName: " + this.ejbName + " pk: " + this.pk + " pks: " + this.pks;
   }

   public InvalidationMessage() {
   }

   public InvalidationMessage(String applicationId, String moduleId, String ejbName) {
      this.applicationId = applicationId;
      this.moduleId = moduleId;
      this.ejbName = ejbName;
      this.pk = null;
      this.pks = null;
   }

   public InvalidationMessage(String applicationId, String moduleId, String ejbName, Object key) {
      this.applicationId = applicationId;
      this.moduleId = moduleId;
      this.ejbName = ejbName;
      this.pk = key;
      this.pks = null;
   }

   public InvalidationMessage(String applicationId, String moduleId, String ejbName, Collection keys) {
      this.applicationId = applicationId;
      this.moduleId = moduleId;
      this.ejbName = ejbName;
      this.pk = null;
      this.pks = keys;
   }

   public String getApplicationName() {
      return this.applicationId;
   }

   public String getModuleId() {
      return this.moduleId;
   }

   public String getEjbName() {
      return this.ejbName;
   }

   public Object getPrimaryKey() {
      return this.pk;
   }

   public Collection getPrimaryKeys() {
      return this.pks;
   }

   public void execute(HostID sender) {
      ApplicationAccess aa = ApplicationAccess.getApplicationAccess();
      ApplicationContextInternal ac = aa.getApplicationContext(this.applicationId);
      if (ac != null) {
         ModuleRegistry mr = ac.getModuleContext(this.moduleId).getRegistry();
         if (mr != null) {
            Object di = mr.get(DeploymentInfo.class.getName());
            if (di != null) {
               Object bi = ((DeploymentInfo)di).getBeanInfo(this.ejbName);
               if (bi != null) {
                  EntityBeanInfo ebi = (EntityBeanInfo)bi;
                  InvalidationBeanManager ibm = (InvalidationBeanManager)ebi.getBeanManager();
                  if (this.getPrimaryKey() != null) {
                     ibm.invalidateLocalServer((Object)null, (Object)this.getPrimaryKey());
                  } else if (this.getPrimaryKeys() != null) {
                     ibm.invalidateLocalServer((Object)null, (Collection)this.getPrimaryKeys());
                  } else {
                     ibm.invalidateAllLocalServer((Object)null);
                  }

               }
            }
         }
      }
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      oo.writeObject(this.applicationId);
      oo.writeObject(this.moduleId);
      oo.writeObject(this.ejbName);
      oo.writeObject(this.pk);
      oo.writeObject(this.pks);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.applicationId = (String)oi.readObject();
      this.moduleId = (String)oi.readObject();
      this.ejbName = (String)oi.readObject();
      this.pk = oi.readObject();
      this.pks = (Collection)oi.readObject();
   }
}
