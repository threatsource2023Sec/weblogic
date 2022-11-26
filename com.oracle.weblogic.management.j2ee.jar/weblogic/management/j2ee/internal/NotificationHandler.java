package weblogic.management.j2ee.internal;

import java.util.HashMap;
import java.util.Map;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;

final class NotificationHandler {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJ2EEManagement");
   private final Map j2eeobjectNameToImpl = new HashMap(3);
   private final String j2eeType;
   private final String wlsType;
   private final ObjectName wlsObjectName;
   private final String domain;
   private boolean registerThisObject = true;

   public NotificationHandler(ObjectName runtime, String domainName) {
      this.wlsObjectName = runtime;
      this.wlsType = this.wlsObjectName.getKeyProperty("Type");
      this.j2eeType = Types.getJ2EETypeForWLSType(this.wlsType);
      this.domain = domainName;
      this.processWLSObjectName();
   }

   private ObjectName getJ2EEObjectName() {
      String name = this.wlsObjectName.getKeyProperty("Name");
      StringBuffer buf = new StringBuffer(this.domain + ":" + "name" + "=" + name + ",");
      StringBuffer wlsParentName = new StringBuffer(this.domain + ":" + "Name" + "=" + name + ",");
      String[] parents = JMOTypesHelper.getParentsForType(this.j2eeType);
      if (parents != null) {
         for(int i = 0; i < parents.length; ++i) {
            String j2eeParentType = parents[i];
            String wlsParentType = Types.getWLSTypeForJ2EEType(j2eeParentType);
            String j2eeParentValue = this.wlsObjectName.getKeyProperty(wlsParentType);
            wlsParentName.append(wlsParentType + "=" + j2eeParentValue + ",");
            buf.append(j2eeParentType + "=" + j2eeParentValue + ",");
         }
      }

      buf.append("j2eeType=" + this.j2eeType);
      wlsParentName.append("Type=" + this.wlsType);
      ObjectName j2eeObjectName = this.getJ2EEObjectName(buf);
      return j2eeObjectName;
   }

   private ObjectName getJ2EEObjectName(StringBuffer buf) {
      try {
         return new ObjectName(buf.toString());
      } catch (MalformedObjectNameException var3) {
         throw new AssertionError("Malformed ObjectName" + var3);
      }
   }

   private ObjectName getJ2EEObjectNameForModule(boolean isParentEar) {
      String name = this.wlsObjectName.getKeyProperty("Name");
      StringBuffer buf = new StringBuffer(this.domain + ":" + "name" + "=" + name + ",");
      String appName = isParentEar ? this.wlsObjectName.getKeyProperty("ApplicationRuntime") : null;
      buf.append("J2EEApplication=" + appName + ",");
      buf.append("J2EEServer=" + this.wlsObjectName.getKeyProperty("ServerRuntime") + ",");
      buf.append("j2eeType=" + this.j2eeType);
      return this.getJ2EEObjectName(buf);
   }

   private void processWLSObjectName() {
      // $FF: Couldn't be decompiled
   }

   private void setValues(ObjectName objectName, Object impl) {
      if (debug.isDebugEnabled()) {
         debug.debug("setValues: objectName = " + objectName + " value = " + impl);
      }

      this.j2eeobjectNameToImpl.put(objectName, impl);
   }

   private void processJ2EEDomainBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new J2EEDomainMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEServerBeanObject() {
      ObjectName j2eeServer = this.getJ2EEObjectName();
      this.setValues(j2eeServer, new J2EEServerMBeanImpl(j2eeServer.getCanonicalName()));
      ObjectName jndiResource = this.makeObjectNameForType("JNDIResource");
      this.setValues(jndiResource, new JNDIResourceMBeanImpl(jndiResource.getCanonicalName()));
      ObjectName rmi_iiop = this.makeObjectNameForType("RMI_IIOPResource");
      this.setValues(rmi_iiop, new RMI_IIOPResourceMBeanImpl(rmi_iiop.getCanonicalName()));
   }

   private void processJ2EEApplicationBeanObject() {
      ApplicationInfo info = new ApplicationInfo(this.wlsObjectName, 1);
      if (!info.isEar()) {
         this.registerThisObject = false;
      } else {
         ObjectName j2eeObjectName = this.getJ2EEObjectName();
         this.setValues(j2eeObjectName, new J2EEApplicationMBeanImpl(j2eeObjectName.getCanonicalName(), this.getJ2EEServer(), info));
      }
   }

   private void processJ2EEAppClientModuleBeanObject() {
      ApplicationInfo info = new ApplicationInfo(this.wlsObjectName, 5);
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new AppClientModuleMBeanImpl(j2eeObjectName.getCanonicalName(), this.getJ2EEServer(), this.getJ2EEJVM(), info));
   }

   private void processJ2EEEJBModuleBeanObject() {
      ApplicationInfo info = new ApplicationInfo(this.wlsObjectName, 3);
      ObjectName j2eeObjectName = this.getJ2EEObjectNameForModule(info.isParentEar());
      this.setValues(j2eeObjectName, new EJBModuleMBeanImpl(j2eeObjectName.getCanonicalName(), this.getJ2EEServer(), this.getJ2EEJVM(), info));
   }

   private void processJ2EEWebModuleBeanObject() {
      ApplicationInfo info = new ApplicationInfo(this.wlsObjectName, 2);
      ObjectName j2eeObjectName = this.getJ2EEObjectNameForModule(info.isParentEar());
      this.setValues(j2eeObjectName, new WebModuleMBeanImpl(j2eeObjectName.getCanonicalName(), this.getJ2EEServer(), this.getJ2EEJVM(), info));
   }

   private void processJ2EEResourceAdaptorModuleBeanObject() {
      ApplicationInfo info = new ApplicationInfo(this.wlsObjectName, 4);
      ObjectName j2eeObjectName = this.getJ2EEObjectNameForModule(info.isParentEar());
      this.setValues(j2eeObjectName, new ResourceAdapterModuleMBeanImpl(j2eeObjectName.getCanonicalName(), this.getJ2EEServer(), this.getJ2EEJVM(), info));
      ObjectName resourceAdapter = this.getResourceAdapterObjectName(j2eeObjectName);
      ObjectName jcaResource = this.getJCAResourceObjectName(j2eeObjectName);
      this.setValues(resourceAdapter, new ResourceAdapterMBeanImpl(resourceAdapter.getCanonicalName(), jcaResource.getCanonicalName()));
      this.setValues(jcaResource, new JCAResourceMBeanImpl(jcaResource.getCanonicalName(), this.wlsObjectName));
   }

   private void processJ2EEEntityBeanBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new EntityBeanMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEMessageDrivenBeanBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new MessageDrivenBeanMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEStatelessBeanBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new StatelessSessionBeanMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEStatefulBeanBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new StatefulSessionBeanMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEServletBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new ServletMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEJCAConFactoryBeanObject() {
      String jcaResourceName = this.wlsObjectName.getKeyProperty("ConnectorComponentRuntime");
      ObjectName j2eeObjectName = this.getJ2EEConnectionFactoryObjectName(jcaResourceName);
      ObjectName managedConnectionFactory = this.getJCAManagedConnectionFactoryObjectName(j2eeObjectName);
      this.setValues(j2eeObjectName, new JCAConnectionFactoryMBeanImpl(j2eeObjectName.getCanonicalName(), managedConnectionFactory.getCanonicalName()));
      this.setValues(managedConnectionFactory, new JCAManagedConnectionFactoryMBeanImpl(managedConnectionFactory.getCanonicalName()));
   }

   private void processJ2EEJDBCResourceBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new JDBCResourceMBeanImpl(j2eeObjectName.getCanonicalName(), this.wlsObjectName.getKeyProperty("ServerRuntime")));
   }

   private void processJ2EEJMSResourceBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new JMSResourceMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEJavaMailResourceBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new JavaMailResourceMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEJNDIResourceBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new JNDIResourceMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EERMI_IIOP_ResourceBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new RMI_IIOPResourceMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEURLResourceBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new URLResourceMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEJTAResourceBeanObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new JTAResourceMBeanImpl(j2eeObjectName.getCanonicalName()));
   }

   private void processJ2EEJVMObject() {
      ObjectName j2eeObjectName = this.getJ2EEObjectName();
      this.setValues(j2eeObjectName, new JVMMBeanImpl(j2eeObjectName.getCanonicalName(), this.wlsObjectName));
   }

   private String getJ2EEServer() {
      String serverName = this.wlsObjectName.getKeyProperty("ServerRuntime");
      return this.domain + ":" + "name" + "=" + serverName + "," + "j2eeType" + "=" + "J2EEServer";
   }

   private String getJ2EEJVM() {
      String serverName = this.wlsObjectName.getKeyProperty("ServerRuntime");
      return this.domain + ":" + "name" + "=" + serverName + "," + "j2eeType" + "=" + "JVM" + "," + "J2EEServer" + "=" + serverName;
   }

   private ObjectName getResourceAdapterObjectName(ObjectName resourceAdapterModule) {
      String name = resourceAdapterModule.getKeyProperty("name");
      StringBuffer buff = new StringBuffer(this.domain + ":" + "J2EEServer" + "=" + resourceAdapterModule.getKeyProperty("J2EEServer") + "," + "J2EEApplication" + "=" + resourceAdapterModule.getKeyProperty("J2EEApplication") + ",name=" + name + "," + "ResourceAdapterModule" + "=" + name + "," + "j2eeType" + "=" + "ResourceAdapter");
      return this.getJ2EEObjectName(buff);
   }

   private ObjectName getJCAResourceObjectName(ObjectName resourceAdapterModule) {
      String name = resourceAdapterModule.getKeyProperty("name");
      StringBuffer buff = new StringBuffer(this.domain + ":" + "J2EEServer" + "=" + resourceAdapterModule.getKeyProperty("J2EEServer") + "," + "ResourceAdapter" + "=" + name + ",name=" + name + "," + "j2eeType" + "=" + "JCAResource");
      return this.getJ2EEObjectName(buff);
   }

   private ObjectName getJCAManagedConnectionFactoryObjectName(ObjectName managedFactory) {
      StringBuffer buff = new StringBuffer(this.domain + ":" + "J2EEServer" + "=" + managedFactory.getKeyProperty("J2EEServer") + ",name=" + managedFactory.getKeyProperty("name") + "," + "j2eeType" + "=" + "JCAManagedConnectionFactory");
      return this.getJ2EEObjectName(buff);
   }

   private ObjectName getJ2EEConnectionFactoryObjectName(String jcaResource) {
      StringBuffer buff = new StringBuffer(this.domain + ":" + "J2EEServer" + "=" + this.wlsObjectName.getKeyProperty("ServerRuntime") + "," + "JCAResource" + "=" + jcaResource + "," + "ResourceAdapterModule" + "=" + jcaResource + ",name=" + this.wlsObjectName.getKeyProperty("Name") + "," + "j2eeType" + "=" + "JCAConnectionFactory");
      return this.getJ2EEObjectName(buff);
   }

   private ObjectName getObjectNameWithServerAsParent(String type) {
      String name = this.wlsObjectName.getKeyProperty("Name");
      String serverName = this.wlsObjectName.getKeyProperty("ServerRuntime");
      StringBuffer buff = new StringBuffer(this.domain + ":name=" + name + "," + "J2EEServer" + "=" + serverName + "," + "j2eeType" + "=" + type);
      return this.getJ2EEObjectName(buff);
   }

   private ObjectName makeObjectNameForType(String type) {
      String name = this.wlsObjectName.getKeyProperty("Name");
      StringBuffer buff = new StringBuffer(this.domain + ":name=" + name + "," + "J2EEServer" + "=" + name + "," + "j2eeType" + "=" + type);
      return this.getJ2EEObjectName(buff);
   }

   boolean registerThisObject() {
      return this.registerThisObject;
   }

   Map getJ2EEObjectNameToImplMap() {
      return this.j2eeobjectNameToImpl;
   }

   ObjectName getWLSObjectName() {
      return this.wlsObjectName;
   }

   private boolean validApp() {
      boolean isValidApp = false;

      try {
         ObjectInstance object = null;
         String appName = (String)MBeanServerConnectionProvider.getDomainMBeanServerConnection().getAttribute(this.wlsObjectName, "ApplicationName");
         String sObjectName = this.wlsObjectName.getDomain() + ":Name=" + appName + ",Type=AppDeployment";

         try {
            MBeanServerConnection editConnection = MBeanServerConnectionProvider.getEditMBeanServerConnection();
            editConnection.getObjectInstance(new ObjectName(sObjectName));
            isValidApp = true;
         } catch (InstanceNotFoundException var7) {
            if (InternalAppDataCacheService.isInternalApp(appName)) {
               return true;
            }

            MBeanServerConnection runtimeConnection = MBeanServerConnectionProvider.geRuntimeMBeanServerConnection();
            runtimeConnection.getObjectInstance(new ObjectName(sObjectName));
            isValidApp = true;
         }

         return isValidApp;
      } catch (Throwable var8) {
         if (debug.isDebugEnabled()) {
            debug.debug("validApp got exception ", var8);
         }

         this.registerThisObject = false;
         return isValidApp;
      }
   }
}
