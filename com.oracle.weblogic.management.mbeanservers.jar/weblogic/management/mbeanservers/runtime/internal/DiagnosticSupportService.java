package weblogic.management.mbeanservers.runtime.internal;

import java.beans.BeanInfo;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.mbeanservers.internal.WLSObjectNameManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class DiagnosticSupportService extends AbstractServerService {
   @Inject
   @Named("RuntimeAccessService")
   private ServerService dependencyOnRuntimeAccessService;
   @Inject
   @Named("BeanInfoAccessService")
   private ServerService dependencyOnBeanInfoAccessService;
   @Inject
   private Provider runtimeAccessProvider;
   boolean trackingInstances = true;
   BeanInfoAccess beanInfoAccess;
   ObjectNameManager objectNameManager;
   private static DiagnosticSupportService diagnosticSupportService;

   public static DiagnosticSupportService getDiagnosticSupportService() {
      return diagnosticSupportService;
   }

   public void start() throws ServiceFailureException {
      this.beanInfoAccess = ManagementService.getBeanInfoAccess();
      this.objectNameManager = new WLSObjectNameManager(((RuntimeAccess)this.runtimeAccessProvider.get()).getDomainName());
      diagnosticSupportService = this;
      RuntimeServerService.setSupportService(this);
   }

   public BeanInfo getBeanInfo(String typeName) {
      return this.beanInfoAccess.getBeanInfoForInterface(typeName, true, (String)null);
   }

   public void unregisterInstance(Object instance) {
      if (this.trackingInstances) {
         this.objectNameManager.unregisterObjectInstance(instance);
      }

   }

   public String getInterfaceClassForObjectIdentifier(ObjectName instanceIdentifier) {
      Object instance = this.objectNameManager.lookupObject(instanceIdentifier);
      return instance == null ? null : this.beanInfoAccess.getInterfaceForInstance(instance).getName();
   }

   public String getInterfaceClassForObjectIdentifier(String instanceIdentifier) {
      try {
         return this.getInterfaceClassForObjectIdentifier(new ObjectName(instanceIdentifier));
      } catch (MalformedObjectNameException var3) {
         return null;
      }
   }

   public ObjectName getObjectNameForInstance(Object instance) {
      return this.objectNameManager.lookupObjectName(instance);
   }

   public String getObjectIdentifier(Object instance) {
      ObjectName objectName = this.objectNameManager.lookupObjectName(instance);
      return objectName == null ? null : objectName.getCanonicalName();
   }

   public String getRegisteredObjectIdentifier(Object instance) {
      ObjectName objectName = this.objectNameManager.lookupRegisteredObjectName(instance);
      if (objectName == null) {
         objectName = this.objectNameManager.buildObjectName(instance);
      }

      return objectName == null ? null : objectName.getCanonicalName();
   }

   public ObjectName getRegisteredObjectName(Object instance) {
      ObjectName objectName = this.objectNameManager.lookupRegisteredObjectName(instance);
      if (objectName == null) {
         objectName = this.objectNameManager.buildObjectName(instance);
      }

      return objectName;
   }

   ObjectNameManager getObjectNameManager() {
      this.trackingInstances = false;
      return this.objectNameManager;
   }

   public Object getInstanceForObjectIdentifier(String instanceIdentifier) {
      try {
         return this.objectNameManager.lookupObject(new ObjectName(instanceIdentifier));
      } catch (MalformedObjectNameException var3) {
         return null;
      }
   }
}
