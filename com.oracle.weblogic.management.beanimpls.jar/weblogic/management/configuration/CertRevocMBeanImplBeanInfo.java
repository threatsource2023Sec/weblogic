package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CertRevocMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CertRevocMBean.class;

   public CertRevocMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CertRevocMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CertRevocMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("CertRevocCaMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean represents the configuration of the certificate revocation checking across all certificate authorities. Many of the attributes in this MBean may be overridden per certificate authority using the specific <code>CertRevocCaMBean</code>.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CertRevocMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CertRevocCas")) {
         getterName = "getCertRevocCas";
         setterName = null;
         currentResult = new PropertyDescriptor("CertRevocCas", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CertRevocCas", currentResult);
         currentResult.setValue("description", "<p>Returns the CertRevocCaMBeans representing the certificate authority overrides, which have been configured to be part of this certificate revocation checking configuration.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCertRevocCa");
         currentResult.setValue("destroyer", "destroyCertRevocCa");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlCacheRefreshPeriodPercent")) {
         getterName = "getCrlCacheRefreshPeriodPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlCacheRefreshPeriodPercent";
         }

         currentResult = new PropertyDescriptor("CrlCacheRefreshPeriodPercent", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlCacheRefreshPeriodPercent", currentResult);
         currentResult.setValue("description", "<p>Determines the refresh period for the CRL local cache, expressed as a percentage of the validity period of the CRL.</p>  <p>For example, for a validity period of 10 hours, a value of 10% specifies a refresh every 1 hour.</p>  <p>The validity period is determined by the CRL, and is calculated as the (next reported update time) - (this update time).</p>  <p>The valid range is 1 through 100.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(CertRevocMBean.DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlCacheType")) {
         getterName = "getCrlCacheType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlCacheType";
         }

         currentResult = new PropertyDescriptor("CrlCacheType", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlCacheType", currentResult);
         currentResult.setValue("description", "<p>Determines the type of CRL cache, related to the physical storage of the CRLs. The <code>ldap</code> CRL cache type can be specified, but is not currently supported.</p>  <p>The value specified in this attribute determines which related <code>CrlCacheType*</code> attributes apply.</p> ");
         setPropertyDescriptorDefault(currentResult, CertRevocMBean.DEFAULT_CRL_CACHE_TYPE);
         currentResult.setValue("legalValues", new Object[]{CertRevocMBean.CRL_CACHE_TYPE_FILE, CertRevocMBean.CRL_CACHE_TYPE_LDAP});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlCacheTypeLdapHostname")) {
         getterName = "getCrlCacheTypeLdapHostname";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlCacheTypeLdapHostname";
         }

         currentResult = new PropertyDescriptor("CrlCacheTypeLdapHostname", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlCacheTypeLdapHostname", currentResult);
         currentResult.setValue("description", "<p>Determines the remote hostname for the LDAP server containing CRLs.</p>  <p>This attribute applies when value <code>{@link CertRevocMBean#CRL_CACHE_TYPE_LDAP}</code> is returned from <code>{@link #getCrlCacheType}</code>.</p> ");
         setPropertyDescriptorDefault(currentResult, CertRevocMBean.DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlCacheTypeLdapPort")) {
         getterName = "getCrlCacheTypeLdapPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlCacheTypeLdapPort";
         }

         currentResult = new PropertyDescriptor("CrlCacheTypeLdapPort", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlCacheTypeLdapPort", currentResult);
         currentResult.setValue("description", "<p>Determines the remote port for the LDAP server containing CRLs.</p>  <p>This attribute applies when value <code>{@link CertRevocMBean#CRL_CACHE_TYPE_LDAP}</code> is returned from <code>{@link #getCrlCacheType}</code>. </p> The valid range is -1, 1 through 65535. ");
         setPropertyDescriptorDefault(currentResult, new Integer(CertRevocMBean.DEFAULT_CRL_CACHE_TYPE_LDAP_PORT));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlCacheTypeLdapSearchTimeout")) {
         getterName = "getCrlCacheTypeLdapSearchTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlCacheTypeLdapSearchTimeout";
         }

         currentResult = new PropertyDescriptor("CrlCacheTypeLdapSearchTimeout", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlCacheTypeLdapSearchTimeout", currentResult);
         currentResult.setValue("description", "<p>Determines how long to wait for CRL search results from the LDAP server.</p> <p> This attribute applies when value <code>{@link CertRevocMBean#CRL_CACHE_TYPE_LDAP}</code> is returned from <code>{@link #getCrlCacheType}</code>. </p> <p>The valid range is 1 thru 300 seconds.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(CertRevocMBean.DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT));
         currentResult.setValue("legalMax", new Integer(300));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlDpDownloadTimeout")) {
         getterName = "getCrlDpDownloadTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpDownloadTimeout";
         }

         currentResult = new PropertyDescriptor("CrlDpDownloadTimeout", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlDpDownloadTimeout", currentResult);
         currentResult.setValue("description", "<p>Determines the overall timeout for the Distribution Point CRL download, expressed in seconds.</p>  <p>The valid range is 1 thru 300 seconds.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(CertRevocMBean.DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT));
         currentResult.setValue("legalMax", new Long(300L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MethodOrder")) {
         getterName = "getMethodOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMethodOrder";
         }

         currentResult = new PropertyDescriptor("MethodOrder", CertRevocMBean.class, getterName, setterName);
         descriptors.put("MethodOrder", currentResult);
         currentResult.setValue("description", "<p>Determines the certificate revocation checking method order.</p>  <p>NOTE that omission of a specific method disables that method.</p> ");
         setPropertyDescriptorDefault(currentResult, CertRevocMBean.DEFAULT_METHOD_ORDER);
         currentResult.setValue("legalValues", new Object[]{CertRevocMBean.METHOD_OCSP, CertRevocMBean.METHOD_CRL, CertRevocMBean.METHOD_OCSP_THEN_CRL, CertRevocMBean.METHOD_CRL_THEN_OCSP});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponseCacheCapacity")) {
         getterName = "getOcspResponseCacheCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponseCacheCapacity";
         }

         currentResult = new PropertyDescriptor("OcspResponseCacheCapacity", CertRevocMBean.class, getterName, setterName);
         descriptors.put("OcspResponseCacheCapacity", currentResult);
         currentResult.setValue("description", "<p>Determines the maximum number of entries supported by the OCSP response local cache. The minimum value is 1.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(CertRevocMBean.DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponseCacheRefreshPeriodPercent")) {
         getterName = "getOcspResponseCacheRefreshPeriodPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponseCacheRefreshPeriodPercent";
         }

         currentResult = new PropertyDescriptor("OcspResponseCacheRefreshPeriodPercent", CertRevocMBean.class, getterName, setterName);
         descriptors.put("OcspResponseCacheRefreshPeriodPercent", currentResult);
         currentResult.setValue("description", "<p>Determines the refresh period for the OCSP response local cache, expressed as a percentage of the validity period of the response.</p>  <p>For example, for a validity period of 10 hours, a value of 10% specifies a refresh every 1 hour.</p>  <p>The validity period is determined by the OCSP response, and is calculated as the (next reported update time) - (this update time).</p>  <p>The valid range is 1 through 100.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(CertRevocMBean.DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponseTimeout")) {
         getterName = "getOcspResponseTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponseTimeout";
         }

         currentResult = new PropertyDescriptor("OcspResponseTimeout", CertRevocMBean.class, getterName, setterName);
         descriptors.put("OcspResponseTimeout", currentResult);
         currentResult.setValue("description", "<p>Determines the timeout for the OCSP response, expressed in seconds.</p>  <p>The valid range is 1 thru 300 seconds.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(CertRevocMBean.DEFAULT_OCSP_RESPONSE_TIMEOUT));
         currentResult.setValue("legalMax", new Long(300L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspTimeTolerance")) {
         getterName = "getOcspTimeTolerance";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspTimeTolerance";
         }

         currentResult = new PropertyDescriptor("OcspTimeTolerance", CertRevocMBean.class, getterName, setterName);
         descriptors.put("OcspTimeTolerance", currentResult);
         currentResult.setValue("description", "<p>Determines the time tolerance value for handling clock-skew differences between clients and responders, expressed in seconds.</p>  <p>The validity period of the response is extended both into the future and into the past by the specified amount of time, effectively widening the validity interval.</p>  <p>The value is &gt;=0 and &lt;=900. The maximum allowed tolerance is 15 minutes.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(CertRevocMBean.DEFAULT_OCSP_TIME_TOLERANCE));
         currentResult.setValue("legalMax", new Integer(900));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CheckingEnabled")) {
         getterName = "isCheckingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCheckingEnabled";
         }

         currentResult = new PropertyDescriptor("CheckingEnabled", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CheckingEnabled", currentResult);
         currentResult.setValue("description", "<p>Determines whether certificate revocation checking is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(CertRevocMBean.DEFAULT_CHECKING_ENABLED));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlDpBackgroundDownloadEnabled")) {
         getterName = "isCrlDpBackgroundDownloadEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpBackgroundDownloadEnabled";
         }

         currentResult = new PropertyDescriptor("CrlDpBackgroundDownloadEnabled", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlDpBackgroundDownloadEnabled", currentResult);
         currentResult.setValue("description", "<p>Determines whether the CRL Distribution Point background downloading, to automatically update the local CRL cache, is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(CertRevocMBean.DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlDpEnabled")) {
         getterName = "isCrlDpEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpEnabled";
         }

         currentResult = new PropertyDescriptor("CrlDpEnabled", CertRevocMBean.class, getterName, setterName);
         descriptors.put("CrlDpEnabled", currentResult);
         currentResult.setValue("description", "<p>Determines whether the CRL Distribution Point processing to update the local CRL cache is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(CertRevocMBean.DEFAULT_CRL_DP_ENABLED));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailOnUnknownRevocStatus")) {
         getterName = "isFailOnUnknownRevocStatus";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailOnUnknownRevocStatus";
         }

         currentResult = new PropertyDescriptor("FailOnUnknownRevocStatus", CertRevocMBean.class, getterName, setterName);
         descriptors.put("FailOnUnknownRevocStatus", currentResult);
         currentResult.setValue("description", "<p>Determines whether certificate path checking should fail, if revocation status could not be determined.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(CertRevocMBean.DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspNonceEnabled")) {
         getterName = "isOcspNonceEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspNonceEnabled";
         }

         currentResult = new PropertyDescriptor("OcspNonceEnabled", CertRevocMBean.class, getterName, setterName);
         descriptors.put("OcspNonceEnabled", currentResult);
         currentResult.setValue("description", "<p>Determines whether a nonce is sent with OCSP requests, to force a fresh (not pre-signed) response.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(CertRevocMBean.DEFAULT_OCSP_NONCE_ENABLED));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponseCacheEnabled")) {
         getterName = "isOcspResponseCacheEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponseCacheEnabled";
         }

         currentResult = new PropertyDescriptor("OcspResponseCacheEnabled", CertRevocMBean.class, getterName, setterName);
         descriptors.put("OcspResponseCacheEnabled", currentResult);
         currentResult.setValue("description", "<p>Determines whether the OCSP response local cache is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(CertRevocMBean.DEFAULT_OCSP_RESPONSE_CACHE_ENABLED));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CertRevocMBean.class.getMethod("createCertRevocCa", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Unique short name ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This is the factory method for certificate revocation checking configuration CA overrides.</p>  <p>The short name, which is specified, must be unique among all object instances of type CertRevocCaMBean. The new CA override, which is created, will have this certificate revocation checking configuration as its parent and must be destroyed with the <code>{@link #destroyCertRevocCa}</code> method.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CertRevocCas");
      }

      mth = CertRevocMBean.class.getMethod("destroyCertRevocCa", CertRevocCaMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("child", "CertRevocCaMBean to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys and removes a certificate authority override, which is a child of this certificate revocation checking configuration.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CertRevocCas");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CertRevocMBean.class.getMethod("lookupCertRevocCa", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Unique short name ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Lookup a particular CertRevocCaMBean from the list.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CertRevocCas");
      }

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
