package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CertRevocCaMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CertRevocCaMBean.class;

   public CertRevocCaMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CertRevocCaMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CertRevocCaMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("CertRevocMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This MBean represents the configuration of certificate revocation checking for a specific certificate authority. Default values for attributes in this MBean are derived from <code>CertRevocMBean</code>.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CertRevocCaMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CrlDpDownloadTimeout")) {
         getterName = "getCrlDpDownloadTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpDownloadTimeout";
         }

         currentResult = new PropertyDescriptor("CrlDpDownloadTimeout", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("CrlDpDownloadTimeout", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the overall timeout for the Distribution Point CRL download, expressed in seconds.</p> <p> The valid range is 1 thru 300 seconds.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Long(300L));
         currentResult.setValue("legalMin", new Long(1L));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("CrlDpUrl")) {
         getterName = "getCrlDpUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpUrl";
         }

         currentResult = new PropertyDescriptor("CrlDpUrl", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("CrlDpUrl", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the CRL Distribution Point URL to use as failover or override for the URL found in the CRLDistributionPoints extension in the certificate.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCrlDpUrlUsage")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_CRL_DP_URL);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlDpUrlUsage")) {
         getterName = "getCrlDpUrlUsage";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpUrlUsage";
         }

         currentResult = new PropertyDescriptor("CrlDpUrlUsage", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("CrlDpUrlUsage", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines how <code>getCrlDpUrl</code> is used: as failover in case the URL in the certificate CRLDistributionPoints extension is invalid or not found, or as a value overriding the URL found in the certificate CRLDistributionPoints extension.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getCrlDpUrl")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_CRL_DP_URL_USAGE);
         currentResult.setValue("legalValues", new Object[]{CertRevocCaMBean.USAGE_FAILOVER, CertRevocCaMBean.USAGE_OVERRIDE});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DistinguishedName")) {
         getterName = "getDistinguishedName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDistinguishedName";
         }

         currentResult = new PropertyDescriptor("DistinguishedName", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("DistinguishedName", currentResult);
         currentResult.setValue("description", "<p>Determines the identity of this per-CA configuration using the distinguished name (defined in RFC 2253), which is used in certificates issued by the represented certificate authority.</p>  <p>For example:</p>  <p><code>&quot;CN=CertGenCAB, OU=FOR TESTING ONLY, O=MyOrganization, L=MyTown, ST=MyState, C=US&quot;</code></p>  <p>This will be used to match this configuration to issued certificates requiring revocation checking.</p> ");
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_DISTINGUISHED_NAME);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MethodOrder")) {
         getterName = "getMethodOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMethodOrder";
         }

         currentResult = new PropertyDescriptor("MethodOrder", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("MethodOrder", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the certificate revocation checking method order.</p>  <p>NOTE THAT omission of a specific method disables that method.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{CertRevocMBean.METHOD_OCSP, CertRevocMBean.METHOD_CRL, CertRevocMBean.METHOD_OCSP_THEN_CRL, CertRevocMBean.METHOD_CRL_THEN_OCSP});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponderCertIssuerName")) {
         getterName = "getOcspResponderCertIssuerName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponderCertIssuerName";
         }

         currentResult = new PropertyDescriptor("OcspResponderCertIssuerName", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponderCertIssuerName", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the explicitly trusted OCSP responder certificate issuer name, when the attribute returned by <code>getOcspResponderExplicitTrustMethod</code> is \"USE_ISSUER_SERIAL_NUMBER\".</p> <p> The issuer name is formatted as a distinguished name per RFC 2253, for example \"CN=CertGenCAB, OU=FOR TESTING ONLY, O=MyOrganization, L=MyTown, ST=MyState, C=US\".</p> <p> When <code>{@link #getOcspResponderCertIssuerName}</code> returns a non-null value then the <code>{@link #getOcspResponderCertSerialNumber}</code> must also be set.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getOcspResponderExplicitTrustMethod")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponderCertSerialNumber")) {
         getterName = "getOcspResponderCertSerialNumber";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponderCertSerialNumber";
         }

         currentResult = new PropertyDescriptor("OcspResponderCertSerialNumber", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponderCertSerialNumber", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the explicitly trusted OCSP responder certificate serial number, when the attribute returned by <code>getOcspResponderExplicitTrustMethod</code> is \"USE_ISSUER_SERIAL_NUMBER\".</p> <p> The serial number is formatted as a hexidecimal string, with optional colon or space separators, for example \"2A:FF:00\".</p> <p> When <code>{@link #getOcspResponderCertSerialNumber}</code> returns a non-null value then the <code>{@link #getOcspResponderCertIssuerName}</code> must also be set.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getOcspResponderExplicitTrustMethod")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponderCertSubjectName")) {
         getterName = "getOcspResponderCertSubjectName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponderCertSubjectName";
         }

         currentResult = new PropertyDescriptor("OcspResponderCertSubjectName", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponderCertSubjectName", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the explicitly trusted OCSP responder certificate subject name, when the attribute returned by <code>getOcspResponderExplicitTrustMethod</code> is \"USE_SUBJECT\".</p> <p> The subject name is formatted as a distinguished name per RFC 2253, for example \"CN=CertGenCAB, OU=FOR TESTING ONLY, O=MyOrganization, L=MyTown, ST=MyState, C=US\".</p> <p> In cases where the subject name alone is not sufficient to uniquely identify the certificate, then both the <code>{@link #getOcspResponderCertIssuerName}</code> and <code>{@link #getOcspResponderCertSerialNumber}</code> may be used instead.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getOcspResponderExplicitTrustMethod")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponderExplicitTrustMethod")) {
         getterName = "getOcspResponderExplicitTrustMethod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponderExplicitTrustMethod";
         }

         currentResult = new PropertyDescriptor("OcspResponderExplicitTrustMethod", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponderExplicitTrustMethod", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines whether the OCSP Explicit Trust model is enabled and how the trusted certificate is specified.</p>  <p>The valid values:</p> <dl> <dt>\"NONE\"</dt> <dd>Explicit Trust is disabled</dd> <dt>\"USE_SUBJECT\"</dt> <dd>Identify the trusted certificate using the subject DN specified in the attribute <code>{@link #getOcspResponderCertSubjectName}</code>.</dd> <dt>\"USE_ISSUER_SERIAL_NUMBER\"</dt> <dd>Identify the trusted certificate using the issuer DN and certificate serial number specified in the attributes <code>{@link #getOcspResponderCertIssuerName}</code> and <code>{@link #getOcspResponderCertSerialNumber}</code>, respectively.</dd> </dl> ");
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD);
         currentResult.setValue("legalValues", new Object[]{CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_NONE, CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_USE_SUBJECT, CertRevocCaMBean.OCSP_EXPLICIT_TRUST_METHOD_USE_ISSUER_SERIAL_NUMBER});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponderUrl")) {
         getterName = "getOcspResponderUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponderUrl";
         }

         currentResult = new PropertyDescriptor("OcspResponderUrl", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponderUrl", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the OCSP responder URL to use as failover or override for the URL found in the certificate AIA. The usage is determined by <code>getOcspResponderUrlUsage</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getOcspResponderUrlUsage")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_URL);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponderUrlUsage")) {
         getterName = "getOcspResponderUrlUsage";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponderUrlUsage";
         }

         currentResult = new PropertyDescriptor("OcspResponderUrlUsage", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponderUrlUsage", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines how <code>getOcspResponderUrl</code> is used: as failover in case the URL in the certificate AIA is invalid or not found, or as a value overriding the URL found in the certificate AIA.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getOcspResponderUrl")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, CertRevocCaMBean.DEFAULT_OCSP_RESPONDER_URL_USAGE);
         currentResult.setValue("legalValues", new Object[]{CertRevocCaMBean.USAGE_FAILOVER, CertRevocCaMBean.USAGE_OVERRIDE});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponseTimeout")) {
         getterName = "getOcspResponseTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponseTimeout";
         }

         currentResult = new PropertyDescriptor("OcspResponseTimeout", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponseTimeout", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the timeout for the OCSP response, expressed in seconds.</p> <p> The valid range is 1 thru 300 seconds.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
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

         currentResult = new PropertyDescriptor("OcspTimeTolerance", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspTimeTolerance", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines the time tolerance value for handling clock-skew differences between clients and responders, expressed in seconds.</p> <p> The validity period of the response is extended both into the future and into the past by the specified amount of time, effectively widening the validity interval.</p> <p> The value is &gt;=0 and &lt;=900. The maximum allowed tolerance is 15 minutes.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMax", new Integer(900));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CheckingDisabled")) {
         getterName = "isCheckingDisabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCheckingDisabled";
         }

         currentResult = new PropertyDescriptor("CheckingDisabled", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("CheckingDisabled", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines whether certificate revocation checking is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(CertRevocCaMBean.DEFAULT_CHECKING_DISABLED));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlDpBackgroundDownloadEnabled")) {
         getterName = "isCrlDpBackgroundDownloadEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpBackgroundDownloadEnabled";
         }

         currentResult = new PropertyDescriptor("CrlDpBackgroundDownloadEnabled", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("CrlDpBackgroundDownloadEnabled", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines whether the CRL Distribution Point background downloading, to automatically update the local CRL cache, is enabled.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CrlDpEnabled")) {
         getterName = "isCrlDpEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCrlDpEnabled";
         }

         currentResult = new PropertyDescriptor("CrlDpEnabled", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("CrlDpEnabled", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines whether the CRL Distribution Point processing to update the local CRL cache is enabled.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailOnUnknownRevocStatus")) {
         getterName = "isFailOnUnknownRevocStatus";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailOnUnknownRevocStatus";
         }

         currentResult = new PropertyDescriptor("FailOnUnknownRevocStatus", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("FailOnUnknownRevocStatus", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines whether certificate path checking should fail, if revocation status could not be determined.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspNonceEnabled")) {
         getterName = "isOcspNonceEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspNonceEnabled";
         }

         currentResult = new PropertyDescriptor("OcspNonceEnabled", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspNonceEnabled", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines whether a nonce is sent with OCSP requests, to force a fresh (not pre-signed) response.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OcspResponseCacheEnabled")) {
         getterName = "isOcspResponseCacheEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOcspResponseCacheEnabled";
         }

         currentResult = new PropertyDescriptor("OcspResponseCacheEnabled", CertRevocCaMBean.class, getterName, setterName);
         descriptors.put("OcspResponseCacheEnabled", currentResult);
         currentResult.setValue("description", "<p>For this CA, determines whether the OCSP response local cache is enabled.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
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
