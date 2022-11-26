package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCOracleParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCOracleParamsBean.class;

   public JDBCOracleParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCOracleParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Contains the Oracle database-related parameters of a data source.  <p> Configuration parameters for a data source's Oracle-specific behavior are specified using a Oracle parameters bean. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCOracleParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AffinityPolicy")) {
         getterName = "getAffinityPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAffinityPolicy";
         }

         currentResult = new PropertyDescriptor("AffinityPolicy", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("AffinityPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies the affinity policy for the datasource.</p> Options include: <ul> <li>Transaction: Transaction affinity</li> <li>Session: Web-session affinity</li> <li>Data: Data affinity</li> </ul> The default value is Session. ");
         setPropertyDescriptorDefault(currentResult, "Session");
         currentResult.setValue("legalValues", new Object[]{"Transaction", "Session", "Data", "None"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionInitializationCallback")) {
         getterName = "getConnectionInitializationCallback";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionInitializationCallback";
         }

         currentResult = new PropertyDescriptor("ConnectionInitializationCallback", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionInitializationCallback", currentResult);
         currentResult.setValue("description", "<p>The name of the Connection Initialization Callback class. </p> <p>This name is automatically passed to <code>registerConnectionInitializationCallback</code> when a data source is created. The class must implement <code>oracle.ucp.jdbc.ConnectionInitializationCallback</code>.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OnsNodeList")) {
         getterName = "getOnsNodeList";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOnsNodeList";
         }

         currentResult = new PropertyDescriptor("OnsNodeList", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("OnsNodeList", currentResult);
         currentResult.setValue("description", "<p>A comma-separate list of ONS daemon listen addresses and ports to which connect to for receiving ONS-based FAN events. It is required when connecting to Oracle 11g databases and optional when connecting to Oracle database releases 12c and higher.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OnsWalletFile")) {
         getterName = "getOnsWalletFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOnsWalletFile";
         }

         currentResult = new PropertyDescriptor("OnsWalletFile", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("OnsWalletFile", currentResult);
         currentResult.setValue("description", "<p>The location of the Oracle wallet file in which the SSL certificates are stored. Only required when the ONS client is configured to communicate with ONS daemons using SSL.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] roleObjectArrayGet;
      if (!descriptors.containsKey("OnsWalletPassword")) {
         getterName = "getOnsWalletPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOnsWalletPassword";
         }

         currentResult = new PropertyDescriptor("OnsWalletPassword", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("OnsWalletPassword", currentResult);
         currentResult.setValue("description", "<p>The wallet password attribute that is included as part of the ONS client configuration string. This attribute is only required when ONS is configured to use the SSL protocol.</p>  <p>The value is stored in an encrypted form in the descriptor file and when displayed in an administration console.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         String[] roleObjectArraySet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedSet", roleObjectArraySet);
      }

      if (!descriptors.containsKey("OnsWalletPasswordEncrypted")) {
         getterName = "getOnsWalletPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOnsWalletPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("OnsWalletPasswordEncrypted", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("OnsWalletPasswordEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted database password as set with <code>setOnsWalletPassword()</code>, or with <code>setOnsWalletPasswordEncrypted(byte[] bytes)</code>.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplayInitiationTimeout")) {
         getterName = "getReplayInitiationTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplayInitiationTimeout";
         }

         currentResult = new PropertyDescriptor("ReplayInitiationTimeout", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("ReplayInitiationTimeout", currentResult);
         currentResult.setValue("description", "<p>The amount of time, in seconds, a data source allows for Application Continuity replay processing before timing out and ending a replay session context.</p> <ul> <li>When set to zero (0) seconds, replay processing (failover) is disabled, although begin/endRequest are still called. You can use this for collecting coverage and measuring performance.</li> <li>The default value is 3600 seconds.</li> <li>See <code>oracle.ucp.jdbc.ConnectionInitializationCallback</code>.</li></ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3600));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ActiveGridlink")) {
         getterName = "isActiveGridlink";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setActiveGridlink";
         }

         currentResult = new PropertyDescriptor("ActiveGridlink", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("ActiveGridlink", currentResult);
         currentResult.setValue("description", "Indicates a configured Active GridLink datasource. This attributed is set to <code>true</code> when using the console to create an Active GridLink datasource. It is ignored if FanEnabled is <code>true</code> or OnsNodeList is a non-null string. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FanEnabled")) {
         getterName = "isFanEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFanEnabled";
         }

         currentResult = new PropertyDescriptor("FanEnabled", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("FanEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the data source to subscribe to and process Oracle FAN events.</p>  <p>This attribute is only applicable for RAC configurations that publish FAN notification events using the ONS protocol.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OracleEnableJavaNetFastPath")) {
         getterName = "isOracleEnableJavaNetFastPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOracleEnableJavaNetFastPath";
         }

         currentResult = new PropertyDescriptor("OracleEnableJavaNetFastPath", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("OracleEnableJavaNetFastPath", currentResult);
         currentResult.setValue("description", "<p>This option no longer does anything.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.3.1 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OracleOptimizeUtf8Conversion")) {
         getterName = "isOracleOptimizeUtf8Conversion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOracleOptimizeUtf8Conversion";
         }

         currentResult = new PropertyDescriptor("OracleOptimizeUtf8Conversion", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("OracleOptimizeUtf8Conversion", currentResult);
         currentResult.setValue("description", "<p>Enables the Oracle JDBC optimize UTF-8 conversion option.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OracleProxySession")) {
         getterName = "isOracleProxySession";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOracleProxySession";
         }

         currentResult = new PropertyDescriptor("OracleProxySession", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("OracleProxySession", currentResult);
         currentResult.setValue("description", "<p>Enables Oracle JDBC Proxy Authentication.</p>  <ul> <li>Only applicable for the Oracle driver.</li> <li>Requires WebLogic Server user IDs are mapped to database user IDs using credential mapping.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseDatabaseCredentials")) {
         getterName = "isUseDatabaseCredentials";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseDatabaseCredentials";
         }

         currentResult = new PropertyDescriptor("UseDatabaseCredentials", JDBCOracleParamsBean.class, getterName, setterName);
         descriptors.put("UseDatabaseCredentials", currentResult);
         currentResult.setValue("description", "<p>If enabled, Oracle database credentials are used in getConnection instead of application server credentials. The database credentials are used to get a proxy session without requiring any work in the credential mapper.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
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
