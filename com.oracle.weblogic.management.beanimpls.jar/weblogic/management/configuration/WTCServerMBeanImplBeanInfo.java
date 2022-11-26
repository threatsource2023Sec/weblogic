package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCServerMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCServerMBean.class;

   public WTCServerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCServerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCServerMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This MBean defines a WTC Server. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCServerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Exports")) {
         getterName = "getExports";
         setterName = null;
         currentResult = new PropertyDescriptor("Exports", WTCServerMBean.class, getterName, setterName);
         descriptors.put("Exports", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Imports")) {
         getterName = "getImports";
         setterName = null;
         currentResult = new PropertyDescriptor("Imports", WTCServerMBean.class, getterName, setterName);
         descriptors.put("Imports", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalTuxDoms")) {
         getterName = "getLocalTuxDoms";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalTuxDoms", WTCServerMBean.class, getterName, setterName);
         descriptors.put("LocalTuxDoms", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", WTCServerMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Passwords")) {
         getterName = "getPasswords";
         setterName = null;
         currentResult = new PropertyDescriptor("Passwords", WTCServerMBean.class, getterName, setterName);
         descriptors.put("Passwords", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteTuxDoms")) {
         getterName = "getRemoteTuxDoms";
         setterName = null;
         currentResult = new PropertyDescriptor("RemoteTuxDoms", WTCServerMBean.class, getterName, setterName);
         descriptors.put("RemoteTuxDoms", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("Resource")) {
         getterName = "getResource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResource";
         }

         currentResult = new PropertyDescriptor("Resource", WTCServerMBean.class, getterName, setterName);
         descriptors.put("Resource", currentResult);
         currentResult.setValue("description", "<p>Specifies global field table classes, view table classes, and application passwords for domains. Defines your Resources when configured using the Administration Console.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#createWTCResources")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Resources")) {
         getterName = "getResources";
         setterName = null;
         currentResult = new PropertyDescriptor("Resources", WTCServerMBean.class, getterName, setterName);
         descriptors.put("Resources", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", WTCServerMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("WTCExports")) {
         getterName = "getWTCExports";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCExports", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCExports", currentResult);
         currentResult.setValue("description", "<p>Provides information on services exported by a local Tuxedo access point. Defines your Exported Services when configured using the Administration Console.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWTCExport");
         currentResult.setValue("destroyer", "destroyWTCExport");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCImports")) {
         getterName = "getWTCImports";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCImports", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCImports", currentResult);
         currentResult.setValue("description", "<p>Provides information on services imported and available on remote domains. Defines your Imported Services when configured using the Administration Console.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWTCImport");
         currentResult.setValue("creator", "createWTCImport");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCLocalTuxDoms")) {
         getterName = "getWTCLocalTuxDoms";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCLocalTuxDoms", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCLocalTuxDoms", currentResult);
         currentResult.setValue("description", "<p>The local Tuxedo domains defined for this WTC Server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWTCLocalTuxDom");
         currentResult.setValue("destroyer", "destroyWTCLocalTuxDom");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCPasswords")) {
         getterName = "getWTCPasswords";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCPasswords", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCPasswords", currentResult);
         currentResult.setValue("description", "<p>Specifies the configuration information for inter-domain authentication. Defines your Passwords when configured using the Administration Console.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWTCPassword");
         currentResult.setValue("destroyer", "destroyWTCPassword");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCRemoteTuxDoms")) {
         getterName = "getWTCRemoteTuxDoms";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCRemoteTuxDoms", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCRemoteTuxDoms", currentResult);
         currentResult.setValue("description", "<p>The remote Tuxedo domains defined for this WTC Server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWTCRemoteTuxDom");
         currentResult.setValue("creator", "createWTCRemoteTuxDom");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCResources")) {
         getterName = "getWTCResources";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCResources", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCResources", currentResult);
         currentResult.setValue("description", "<p>Specifies global field table classes, view table classes, and application passwords for domains. Defines your Resources when configured using the Administration Console.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#createWTCResources")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWTCResources");
         currentResult.setValue("destroyer", "destroyWTCResources");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCtBridgeGlobal")) {
         getterName = "getWTCtBridgeGlobal";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCtBridgeGlobal", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCtBridgeGlobal", currentResult);
         currentResult.setValue("description", "<p>Specifies global configuration information for the transfer of messages between WebLogic Server and Tuxedo. Defines your Tuxedo Queuing Bridge when configured using the Administration Console.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#createWTCtBridgeGlobal")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWTCtBridgeGlobal");
         currentResult.setValue("destroyer", "destroyWTCtBridgeGlobal");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCtBridgeRedirects")) {
         getterName = "getWTCtBridgeRedirects";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCtBridgeRedirects", WTCServerMBean.class, getterName, setterName);
         descriptors.put("WTCtBridgeRedirects", currentResult);
         currentResult.setValue("description", "gets all WTCtBridgeRedirect objects ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWTCtBridgeRedirect");
         currentResult.setValue("destroyer", "destroyWTCtBridgeRedirect");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("tBridgeGlobal")) {
         getterName = "gettBridgeGlobal";
         setterName = null;
         if (!this.readOnly) {
            setterName = "settBridgeGlobal";
         }

         currentResult = new PropertyDescriptor("tBridgeGlobal", WTCServerMBean.class, getterName, setterName);
         descriptors.put("tBridgeGlobal", currentResult);
         currentResult.setValue("description", "<p>Specifies global configuration information for the transfer of messages between WebLogic Server and Tuxedo. Defines your Tuxedo Queuing Bridge when configured using the Administration Console.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#createWTCtBridgeGlobal")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("tBridgeRedirects")) {
         getterName = "gettBridgeRedirects";
         setterName = null;
         currentResult = new PropertyDescriptor("tBridgeRedirects", WTCServerMBean.class, getterName, setterName);
         descriptors.put("tBridgeRedirects", currentResult);
         currentResult.setValue("description", "<p>Specifies the source, target, direction, and transport of messages between WebLogic Server and Tuxedo. Defines your Tuxedo Queuing Bridge Redirects when configured using the Administration Console.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", WTCServerMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WTCServerMBean.class.getMethod("createWTCLocalTuxDom", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCLocalTuxDomMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCLocalTuxDoms");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCLocalTuxDom", WTCLocalTuxDomMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("locTuxDomName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a WTCLocalTuxDomMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCLocalTuxDoms");
      }

      mth = WTCServerMBean.class.getMethod("createWTCRemoteTuxDom", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCRemoteTuxDomMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCRemoteTuxDoms");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCRemoteTuxDom", WTCRemoteTuxDomMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("remTuxDomName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a WTCRemoteTuxDomMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCRemoteTuxDoms");
      }

      mth = WTCServerMBean.class.getMethod("createWTCExport", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCExportMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCExports");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCExport", WTCExportMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("expName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a WTCExportMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCExports");
      }

      mth = WTCServerMBean.class.getMethod("createWTCImport", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCImportMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCImports");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCImport", WTCImportMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("impName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a WTCImportMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCImports");
      }

      mth = WTCServerMBean.class.getMethod("createWTCPassword", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCPasswordMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCPasswords");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCPassword", WTCPasswordMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("passwdName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a WTCPasswordMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCPasswords");
      }

      mth = WTCServerMBean.class.getMethod("createWTCResources", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("InstanceAlreadyExistsException if Resource exists")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCResourcesMBean object</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#destroyWTCResources")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCResources");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCResources", WTCResourcesMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("toDestroy", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes this WTCResourcesMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCResources");
      }

      mth = WTCServerMBean.class.getMethod("createWTCtBridgeGlobal");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("InstanceAlreadyExistsException if tBridgeGlobal exists")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCtBridgeGlobalMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCtBridgeGlobal");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCtBridgeGlobal");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes this WTCtBridgeGlobalMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCtBridgeGlobal");
      }

      mth = WTCServerMBean.class.getMethod("createWTCtBridgeRedirect", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a WTCtBridgeRedirectMBean object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCtBridgeRedirects");
      }

      mth = WTCServerMBean.class.getMethod("destroyWTCtBridgeRedirect", WTCtBridgeRedirectMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tBredirect", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a WTCtBridgeRedirectMBean from this WTCServer</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCtBridgeRedirects");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WTCServerMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = WTCServerMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WTCServerMBean.class.getMethod("lookupWTCLocalTuxDom", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WTCLocalTuxDoms");
      }

      mth = WTCServerMBean.class.getMethod("lookupWTCRemoteTuxDom", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WTCRemoteTuxDoms");
      }

      mth = WTCServerMBean.class.getMethod("lookupWTCExport", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WTCExports");
      }

      mth = WTCServerMBean.class.getMethod("lookupWTCImport", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WTCImports");
      }

      mth = WTCServerMBean.class.getMethod("lookupWTCPassword", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WTCPasswords");
      }

      mth = WTCServerMBean.class.getMethod("lookupWTCtBridgeRedirect", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WTCtBridgeRedirects");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WTCServerMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = WTCServerMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
