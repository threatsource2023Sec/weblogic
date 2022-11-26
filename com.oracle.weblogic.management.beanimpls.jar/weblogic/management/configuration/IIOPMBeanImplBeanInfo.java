package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class IIOPMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = IIOPMBean.class;

   public IIOPMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public IIOPMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.IIOPMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Configuration for IIOP properties. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.IIOPMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CompleteMessageTimeout")) {
         getterName = "getCompleteMessageTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompleteMessageTimeout";
         }

         currentResult = new PropertyDescriptor("CompleteMessageTimeout", IIOPMBean.class, getterName, setterName);
         descriptors.put("CompleteMessageTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds spent waiting for a complete IIOP message to be received. This timeout helps guard against denial of service attacks in which a caller indicates that they will be sending a message of a certain size which they never finish sending.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(480));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultCharCodeset")) {
         getterName = "getDefaultCharCodeset";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultCharCodeset";
         }

         currentResult = new PropertyDescriptor("DefaultCharCodeset", IIOPMBean.class, getterName, setterName);
         descriptors.put("DefaultCharCodeset", currentResult);
         currentResult.setValue("description", "<p>The standard character code set that this server will publish as its native code set. (Older ORBs may have trouble interoperating with anything other than the default.)</p> ");
         setPropertyDescriptorDefault(currentResult, "US-ASCII");
         currentResult.setValue("legalValues", new Object[]{"US-ASCII", "UTF-8", "ISO-8859-1"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultMinorVersion")) {
         getterName = "getDefaultMinorVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultMinorVersion";
         }

         currentResult = new PropertyDescriptor("DefaultMinorVersion", IIOPMBean.class, getterName, setterName);
         descriptors.put("DefaultMinorVersion", currentResult);
         currentResult.setValue("description", "<p>The default GIOP (General Inter-ORB Protocol) version that this server will negotiate for incoming connections. (You may have to modify the default to work with other vendor's ORBs.)</p>  <p>This attribute is useful for client orbs with broken GIOP 1.2 implementations.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("legalMax", new Integer(2));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultWideCharCodeset")) {
         getterName = "getDefaultWideCharCodeset";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultWideCharCodeset";
         }

         currentResult = new PropertyDescriptor("DefaultWideCharCodeset", IIOPMBean.class, getterName, setterName);
         descriptors.put("DefaultWideCharCodeset", currentResult);
         currentResult.setValue("description", "<p>The wide character code set that this server will publish as its native code set. (Older ORBs may have trouble interoperating with anything other than the default.)</p> ");
         setPropertyDescriptorDefault(currentResult, "UCS-2");
         currentResult.setValue("legalValues", new Object[]{"UCS-2", "UTF-16", "UTF-8", "UTF-16BE", "UTF-16LE"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EnableIORServlet")) {
         getterName = "getEnableIORServlet";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableIORServlet";
         }

         currentResult = new PropertyDescriptor("EnableIORServlet", IIOPMBean.class, getterName, setterName);
         descriptors.put("EnableIORServlet", currentResult);
         currentResult.setValue("description", "Enable getior servlet used to publish COS Naming Service IORs ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdleConnectionTimeout")) {
         getterName = "getIdleConnectionTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdleConnectionTimeout";
         }

         currentResult = new PropertyDescriptor("IdleConnectionTimeout", IIOPMBean.class, getterName, setterName);
         descriptors.put("IdleConnectionTimeout", currentResult);
         currentResult.setValue("description", "<p>The maximum number of seconds an IIOP connection is allowed to be idle before it is closed by the server. This timeout helps guard against server deadlock through too many open connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("deprecated", "8.1.0.0 use {@link NetworkAccessPointMBean#getIdleConnectionTimeout()} ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxMessageSize")) {
         getterName = "getMaxMessageSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxMessageSize";
         }

         currentResult = new PropertyDescriptor("MaxMessageSize", IIOPMBean.class, getterName, setterName);
         descriptors.put("MaxMessageSize", currentResult);
         currentResult.setValue("description", "<p>The maximum IIOP message size allowable in a message header. This attribute attempts to prevent a denial of service attack whereby a caller attempts to force the server to allocate more memory than is available thereby keeping the server from responding quickly to other requests.</p> <p>An IIOP client can set this value using the <code>-Dweblogic.MaxMessageSize</code> property.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemSecurity")) {
         getterName = "getSystemSecurity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemSecurity";
         }

         currentResult = new PropertyDescriptor("SystemSecurity", IIOPMBean.class, getterName, setterName);
         descriptors.put("SystemSecurity", currentResult);
         currentResult.setValue("description", "Specify the value System Security. The following variables are affected. clientCertAuthentication, clientAuthentication, identityAssertion confidentiality, integrity. The value set in this MBean would only be picked up if the value set in RTD.xml is \"config\". ");
         setPropertyDescriptorDefault(currentResult, "supported");
         currentResult.setValue("legalValues", new Object[]{"none", "supported", "required"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TxMechanism")) {
         getterName = "getTxMechanism";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTxMechanism";
         }

         currentResult = new PropertyDescriptor("TxMechanism", IIOPMBean.class, getterName, setterName);
         descriptors.put("TxMechanism", currentResult);
         currentResult.setValue("description", "<p>The transaction mechanism used by IIOP invocations. The default is the Object Transaction Service (OTS) required by J2EE 1.3.</p> ");
         setPropertyDescriptorDefault(currentResult, "OTS");
         currentResult.setValue("legalValues", new Object[]{"OTS", "JTA", "OTSv11", "none"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseFullRepositoryIdList")) {
         getterName = "getUseFullRepositoryIdList";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseFullRepositoryIdList";
         }

         currentResult = new PropertyDescriptor("UseFullRepositoryIdList", IIOPMBean.class, getterName, setterName);
         descriptors.put("UseFullRepositoryIdList", currentResult);
         currentResult.setValue("description", "<p>Specify whether to use full Repository ID lists when sending value type information for custom-marshaled types. Full Repository ID lists allow C++ ORBS to truncate values to base types. For RMI-IIOP and Java ORBs doing this merely increases transmission overhead. JDK ORBs are known to have problems with these so setting this will prevent JDK ORB access from working.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseJavaSerialization")) {
         getterName = "getUseJavaSerialization";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseJavaSerialization";
         }

         currentResult = new PropertyDescriptor("UseJavaSerialization", IIOPMBean.class, getterName, setterName);
         descriptors.put("UseJavaSerialization", currentResult);
         currentResult.setValue("description", "Specity whether to use java serialization for marshalling objects. Setting this property improves performance when marshalling objects with very large object graphs. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseSerialFormatVersion2")) {
         getterName = "getUseSerialFormatVersion2";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseSerialFormatVersion2";
         }

         currentResult = new PropertyDescriptor("UseSerialFormatVersion2", IIOPMBean.class, getterName, setterName);
         descriptors.put("UseSerialFormatVersion2", currentResult);
         currentResult.setValue("description", "<p>Specify whether to advertise RMI objects and EJBs as supporting RMI-IIOP serial format version 2 for custom marshaled objects.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseStatefulAuthentication")) {
         getterName = "getUseStatefulAuthentication";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseStatefulAuthentication";
         }

         currentResult = new PropertyDescriptor("UseStatefulAuthentication", IIOPMBean.class, getterName, setterName);
         descriptors.put("UseStatefulAuthentication", currentResult);
         currentResult.setValue("description", "<p>Specify whether to advertise RMI objects and EJBs as supporting stateful CSIv2. Stateful CSIv2 is more efficient than stateless, requiring only a single authentication step for each remote principal. Stateless CSIv2 requires per-request authentication. Stateful CSIv2 is not required by J2EE 1.3 and so some ORBs do not support it. Stateful CSIv2 is enabled by default. This property can be changed at the object-level by changing the object's &lt;stateful-authentication&gt; runtime descriptor property.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
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
