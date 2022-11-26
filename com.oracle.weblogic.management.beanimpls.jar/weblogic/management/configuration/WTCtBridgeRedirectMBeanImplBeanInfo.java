package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCtBridgeRedirectMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCtBridgeRedirectMBean.class;

   public WTCtBridgeRedirectMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCtBridgeRedirectMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCtBridgeRedirectMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This interface provides access to the WTC tBridge Redirect configuration attributes. The methods defined herein are applicable for tBridge configuration at the WLS domain level. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCtBridgeRedirectMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Direction")) {
         getterName = "getDirection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDirection";
         }

         currentResult = new PropertyDescriptor("Direction", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("Direction", currentResult);
         currentResult.setValue("description", "<p>The direction of data flow. At least one redirection must be specified or the Tuxedo queuing bridge will fail to start and an error will be logged.</p>  <p>Each defined direction is handled by starting a new thread.</p>  <p style=\"font-weight: bold\">Redirection keywords:</p>  <ul> <li><code>JmsQ2TuxQ</code>  <p>- From JMS to TUXEDO /Q</p> </li>  <li><code>TuxQ2JmsQ</code>  <p>- From TUXEDO /Q to JMS</p> </li>  <li><code>JmsQ2TuxS</code>  <p>- From JMS to TUXEDO Service reply to JMS</p> </li>  <li><code>JmsQ2JmsQ</code>  <p>- From JMS to JMS</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "JmsQ2TuxQ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"JmsQ2TuxQ", "TuxQ2JmsQ", "JmsQ2TuxS", "JmsQ2JmsQ"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MetaDataFile")) {
         getterName = "getMetaDataFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMetaDataFile";
         }

         currentResult = new PropertyDescriptor("MetaDataFile", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("MetaDataFile", currentResult);
         currentResult.setValue("description", "<p>The name of the metadata file URL used to pass the call to the XML-to-non-XML WebLogic XML Translator (WLXT).</p>  <p><i>Note:</i> Not supported for this release.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplyQ")) {
         getterName = "getReplyQ";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplyQ";
         }

         currentResult = new PropertyDescriptor("ReplyQ", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("ReplyQ", currentResult);
         currentResult.setValue("description", "<p>The name of the JMS queue used specifically for synchronous calls to a Tuxedo service. The response is returned to the JMS ReplyQ.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceAccessPoint")) {
         getterName = "getSourceAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourceAccessPoint";
         }

         currentResult = new PropertyDescriptor("SourceAccessPoint", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("SourceAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The name of the local or remote access point where the source is located.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceName")) {
         getterName = "getSourceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourceName";
         }

         currentResult = new PropertyDescriptor("SourceName", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("SourceName", currentResult);
         currentResult.setValue("description", "<p>The name of a source queue or service. Specifies a JMS queue name, a Tuxedo queue name, or the name of a Tuxedo service.</p> ");
         setPropertyDescriptorDefault(currentResult, "mySource");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SourceQspace")) {
         getterName = "getSourceQspace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSourceQspace";
         }

         currentResult = new PropertyDescriptor("SourceQspace", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("SourceQspace", currentResult);
         currentResult.setValue("description", "<p>The name of the Qspace for a source location.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TargetAccessPoint")) {
         getterName = "getTargetAccessPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetAccessPoint";
         }

         currentResult = new PropertyDescriptor("TargetAccessPoint", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("TargetAccessPoint", currentResult);
         currentResult.setValue("description", "<p>The name of the local or remote access point where the target is located.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TargetName")) {
         getterName = "getTargetName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetName";
         }

         currentResult = new PropertyDescriptor("TargetName", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("TargetName", currentResult);
         currentResult.setValue("description", "<p>The name of the target queue or service. Specifies a JMS queue name, a Tuxedo queue name, or the name of a Tuxedo service.</p> ");
         setPropertyDescriptorDefault(currentResult, "myTarget");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TargetQspace")) {
         getterName = "getTargetQspace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargetQspace";
         }

         currentResult = new PropertyDescriptor("TargetQspace", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("TargetQspace", currentResult);
         currentResult.setValue("description", "<p>The name of the Qspace for a target location.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TranslateFML")) {
         getterName = "getTranslateFML";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTranslateFML";
         }

         currentResult = new PropertyDescriptor("TranslateFML", WTCtBridgeRedirectMBean.class, getterName, setterName);
         descriptors.put("TranslateFML", currentResult);
         currentResult.setValue("description", "<p>The type of XML/FML translation.</p>  <p><code>NO</code> indicates that no data translation is performed. <code>FLAT</code> indicates that the message payload is transformed using the WebLogic Tuxedo Connector translator. <code>WLXT</code> indicates that translation is performed by the XML-to-non-XML WebLogic XML Translator (WLXT).</p>  <p><i>Note:</i> WLXT is not supported for this release.</p> ");
         setPropertyDescriptorDefault(currentResult, "NO");
         currentResult.setValue("legalValues", new Object[]{"NO", "FLAT", "WLXT"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
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
