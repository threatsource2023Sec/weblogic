package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WTCResourcesMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WTCResourcesMBean.class;

   public WTCResourcesMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WTCResourcesMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.WTCResourcesMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "7.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This interface provides access to the WTC resources configuration attributes. The methods defined herein are applicable for WTC configuration at the WLS domain level.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.WTCResourcesMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AppPassword")) {
         getterName = "getAppPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAppPassword";
         }

         currentResult = new PropertyDescriptor("AppPassword", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("AppPassword", currentResult);
         currentResult.setValue("description", "<p>The application password as returned from the <code>genpasswd</code> utility.</p>  <p><i>Note:</i> This Tuxedo application password is the encrypted password used to authenticate connections.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AppPasswordIV")) {
         getterName = "getAppPasswordIV";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAppPasswordIV";
         }

         currentResult = new PropertyDescriptor("AppPasswordIV", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("AppPasswordIV", currentResult);
         currentResult.setValue("description", "<p>The initialization vector used to encrypt the <code>AppPassword</code>.</p>  <p><i>Note:</i> This value is returned from the <code>genpasswd</code> utility with the AppPassword.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FldTbl16Classes")) {
         getterName = "getFldTbl16Classes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFldTbl16Classes";
         }

         currentResult = new PropertyDescriptor("FldTbl16Classes", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("FldTbl16Classes", currentResult);
         currentResult.setValue("description", "<p>The names of <code>FldTbl16Classes</code> that are loaded via a class loader and added to a <code>FldTbl</code> array.</p>  <p style=\"font-weight: bold\">Value Requirements:</p>  <ul> <li>Used fully qualified names of the desired classes. </li>  <li>Use a comma-separated list to enter multiple classes. </li> </ul> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FldTbl32Classes")) {
         getterName = "getFldTbl32Classes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFldTbl32Classes";
         }

         currentResult = new PropertyDescriptor("FldTbl32Classes", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("FldTbl32Classes", currentResult);
         currentResult.setValue("description", "<p>The names of <code>FldTbl32Classes</code> that are loaded via a class loader and added to a <code>FldTbl</code> array.</p>  <p style=\"font-weight: bold\">Value Requirements:</p>  <ul> <li>Used fully qualified names of the desired classes. </li>  <li>Use a comma-separated list to enter multiple classes </li> </ul> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MBEncodingMapFile")) {
         getterName = "getMBEncodingMapFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMBEncodingMapFile";
         }

         currentResult = new PropertyDescriptor("MBEncodingMapFile", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("MBEncodingMapFile", currentResult);
         currentResult.setValue("description", "<p>The encoding name map file between Java and Tuxedo MBSTRING.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteMBEncoding")) {
         getterName = "getRemoteMBEncoding";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteMBEncoding";
         }

         currentResult = new PropertyDescriptor("RemoteMBEncoding", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("RemoteMBEncoding", currentResult);
         currentResult.setValue("description", "<p>The default encoding name of sending MBSTRING data.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TpUsrFile")) {
         getterName = "getTpUsrFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTpUsrFile";
         }

         currentResult = new PropertyDescriptor("TpUsrFile", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("TpUsrFile", currentResult);
         currentResult.setValue("description", "<p>The full path to the <code>TPUSR</code> file, which contains Tuxedo UID/GID information.</p>  <p><i>Note:</i> This file is generated by the Tuxedo <code>tpusradd</code> utility on the remote Tuxedo domain.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ViewTbl16Classes")) {
         getterName = "getViewTbl16Classes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setViewTbl16Classes";
         }

         currentResult = new PropertyDescriptor("ViewTbl16Classes", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("ViewTbl16Classes", currentResult);
         currentResult.setValue("description", "<p>The names of <code>ViewTbl16Classes</code> that are loaded via a class loader and added to a <code>ViewTbl</code> array.</p>  <p style=\"font-weight: bold\">Value Requirements:</p>  <ul> <li>Used fully qualified names of the desired classes. </li>  <li>Use a comma-separated list to enter multiple classes. </li> </ul> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ViewTbl32Classes")) {
         getterName = "getViewTbl32Classes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setViewTbl32Classes";
         }

         currentResult = new PropertyDescriptor("ViewTbl32Classes", WTCResourcesMBean.class, getterName, setterName);
         descriptors.put("ViewTbl32Classes", currentResult);
         currentResult.setValue("description", "<p>The names of <code>ViewTbl32Classes</code> that are loaded via a class loader and added to a <code>ViewTbl</code> array.</p>  <p style=\"font-weight: bold\">Value Requirements:</p>  <ul> <li>Used fully qualified names of the desired classes. </li>  <li>Use a comma-separated list to enter multiple classes. </li> </ul> ");
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
