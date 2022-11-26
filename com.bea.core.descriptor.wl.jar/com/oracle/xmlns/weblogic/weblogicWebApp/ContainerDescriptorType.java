package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.EmptyType;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ContainerDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ContainerDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("containerdescriptortype0d2etype");

   RefererValidationType.Enum getRefererValidation();

   RefererValidationType xgetRefererValidation();

   boolean isSetRefererValidation();

   void setRefererValidation(RefererValidationType.Enum var1);

   void xsetRefererValidation(RefererValidationType var1);

   void unsetRefererValidation();

   EmptyType getCheckAuthOnForward();

   boolean isSetCheckAuthOnForward();

   void setCheckAuthOnForward(EmptyType var1);

   EmptyType addNewCheckAuthOnForward();

   void unsetCheckAuthOnForward();

   TrueFalseType getFilterDispatchedRequestsEnabled();

   boolean isSetFilterDispatchedRequestsEnabled();

   void setFilterDispatchedRequestsEnabled(TrueFalseType var1);

   TrueFalseType addNewFilterDispatchedRequestsEnabled();

   void unsetFilterDispatchedRequestsEnabled();

   RedirectContentTypeType getRedirectContentType();

   boolean isSetRedirectContentType();

   void setRedirectContentType(RedirectContentTypeType var1);

   RedirectContentTypeType addNewRedirectContentType();

   void unsetRedirectContentType();

   RedirectContentType getRedirectContent();

   boolean isSetRedirectContent();

   void setRedirectContent(RedirectContentType var1);

   RedirectContentType addNewRedirectContent();

   void unsetRedirectContent();

   TrueFalseType getRedirectWithAbsoluteUrl();

   boolean isSetRedirectWithAbsoluteUrl();

   void setRedirectWithAbsoluteUrl(TrueFalseType var1);

   TrueFalseType addNewRedirectWithAbsoluteUrl();

   void unsetRedirectWithAbsoluteUrl();

   TrueFalseType getIndexDirectoryEnabled();

   boolean isSetIndexDirectoryEnabled();

   void setIndexDirectoryEnabled(TrueFalseType var1);

   TrueFalseType addNewIndexDirectoryEnabled();

   void unsetIndexDirectoryEnabled();

   IndexDirectorySortByType getIndexDirectorySortBy();

   boolean isSetIndexDirectorySortBy();

   void setIndexDirectorySortBy(IndexDirectorySortByType var1);

   IndexDirectorySortByType addNewIndexDirectorySortBy();

   void unsetIndexDirectorySortBy();

   XsdIntegerType getServletReloadCheckSecs();

   boolean isSetServletReloadCheckSecs();

   void setServletReloadCheckSecs(XsdIntegerType var1);

   XsdIntegerType addNewServletReloadCheckSecs();

   void unsetServletReloadCheckSecs();

   XsdIntegerType getResourceReloadCheckSecs();

   boolean isSetResourceReloadCheckSecs();

   void setResourceReloadCheckSecs(XsdIntegerType var1);

   XsdIntegerType addNewResourceReloadCheckSecs();

   void unsetResourceReloadCheckSecs();

   XsdNonNegativeIntegerType getSingleThreadedServletPoolSize();

   boolean isSetSingleThreadedServletPoolSize();

   void setSingleThreadedServletPoolSize(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewSingleThreadedServletPoolSize();

   void unsetSingleThreadedServletPoolSize();

   TrueFalseType getSessionMonitoringEnabled();

   boolean isSetSessionMonitoringEnabled();

   void setSessionMonitoringEnabled(TrueFalseType var1);

   TrueFalseType addNewSessionMonitoringEnabled();

   void unsetSessionMonitoringEnabled();

   TrueFalseType getSaveSessionsEnabled();

   boolean isSetSaveSessionsEnabled();

   void setSaveSessionsEnabled(TrueFalseType var1);

   TrueFalseType addNewSaveSessionsEnabled();

   void unsetSaveSessionsEnabled();

   TrueFalseType getPreferWebInfClasses();

   boolean isSetPreferWebInfClasses();

   void setPreferWebInfClasses(TrueFalseType var1);

   TrueFalseType addNewPreferWebInfClasses();

   void unsetPreferWebInfClasses();

   PreferApplicationPackagesType getPreferApplicationPackages();

   boolean isSetPreferApplicationPackages();

   void setPreferApplicationPackages(PreferApplicationPackagesType var1);

   PreferApplicationPackagesType addNewPreferApplicationPackages();

   void unsetPreferApplicationPackages();

   PreferApplicationResourcesType getPreferApplicationResources();

   boolean isSetPreferApplicationResources();

   void setPreferApplicationResources(PreferApplicationResourcesType var1);

   PreferApplicationResourcesType addNewPreferApplicationResources();

   void unsetPreferApplicationResources();

   DefaultMimeTypeType getDefaultMimeType();

   boolean isSetDefaultMimeType();

   void setDefaultMimeType(DefaultMimeTypeType var1);

   DefaultMimeTypeType addNewDefaultMimeType();

   void unsetDefaultMimeType();

   TrueFalseType getClientCertProxyEnabled();

   boolean isSetClientCertProxyEnabled();

   void setClientCertProxyEnabled(TrueFalseType var1);

   TrueFalseType addNewClientCertProxyEnabled();

   void unsetClientCertProxyEnabled();

   TrueFalseType getReloginEnabled();

   boolean isSetReloginEnabled();

   void setReloginEnabled(TrueFalseType var1);

   TrueFalseType addNewReloginEnabled();

   void unsetReloginEnabled();

   TrueFalseType getAllowAllRoles();

   boolean isSetAllowAllRoles();

   void setAllowAllRoles(TrueFalseType var1);

   TrueFalseType addNewAllowAllRoles();

   void unsetAllowAllRoles();

   TrueFalseType getNativeIoEnabled();

   boolean isSetNativeIoEnabled();

   void setNativeIoEnabled(TrueFalseType var1);

   TrueFalseType addNewNativeIoEnabled();

   void unsetNativeIoEnabled();

   long getMinimumNativeFileSize();

   XmlLong xgetMinimumNativeFileSize();

   boolean isSetMinimumNativeFileSize();

   void setMinimumNativeFileSize(long var1);

   void xsetMinimumNativeFileSize(XmlLong var1);

   void unsetMinimumNativeFileSize();

   TrueFalseType getDisableImplicitServletMappings();

   boolean isSetDisableImplicitServletMappings();

   void setDisableImplicitServletMappings(TrueFalseType var1);

   TrueFalseType addNewDisableImplicitServletMappings();

   void unsetDisableImplicitServletMappings();

   String getTempDir();

   XmlString xgetTempDir();

   boolean isSetTempDir();

   void setTempDir(String var1);

   void xsetTempDir(XmlString var1);

   void unsetTempDir();

   TrueFalseType getOptimisticSerialization();

   boolean isSetOptimisticSerialization();

   void setOptimisticSerialization(TrueFalseType var1);

   TrueFalseType addNewOptimisticSerialization();

   void unsetOptimisticSerialization();

   TrueFalseType getRetainOriginalUrl();

   boolean isSetRetainOriginalUrl();

   void setRetainOriginalUrl(TrueFalseType var1);

   TrueFalseType addNewRetainOriginalUrl();

   void unsetRetainOriginalUrl();

   TrueFalseType getShowArchivedRealPathEnabled();

   boolean isSetShowArchivedRealPathEnabled();

   void setShowArchivedRealPathEnabled(TrueFalseType var1);

   TrueFalseType addNewShowArchivedRealPathEnabled();

   void unsetShowArchivedRealPathEnabled();

   TrueFalseType getRequireAdminTraffic();

   boolean isSetRequireAdminTraffic();

   void setRequireAdminTraffic(TrueFalseType var1);

   TrueFalseType addNewRequireAdminTraffic();

   void unsetRequireAdminTraffic();

   TrueFalseType getAccessLoggingDisabled();

   boolean isSetAccessLoggingDisabled();

   void setAccessLoggingDisabled(TrueFalseType var1);

   TrueFalseType addNewAccessLoggingDisabled();

   void unsetAccessLoggingDisabled();

   TrueFalseType getPreferForwardQueryString();

   boolean isSetPreferForwardQueryString();

   void setPreferForwardQueryString(TrueFalseType var1);

   TrueFalseType addNewPreferForwardQueryString();

   void unsetPreferForwardQueryString();

   TrueFalseType getFailDeployOnFilterInitError();

   boolean isSetFailDeployOnFilterInitError();

   void setFailDeployOnFilterInitError(TrueFalseType var1);

   TrueFalseType addNewFailDeployOnFilterInitError();

   void unsetFailDeployOnFilterInitError();

   TrueFalseType getSendPermanentRedirects();

   boolean isSetSendPermanentRedirects();

   void setSendPermanentRedirects(TrueFalseType var1);

   TrueFalseType addNewSendPermanentRedirects();

   void unsetSendPermanentRedirects();

   TrueFalseType getContainerInitializerEnabled();

   boolean isSetContainerInitializerEnabled();

   void setContainerInitializerEnabled(TrueFalseType var1);

   TrueFalseType addNewContainerInitializerEnabled();

   void unsetContainerInitializerEnabled();

   String getLangtagRevision();

   XmlString xgetLangtagRevision();

   boolean isSetLangtagRevision();

   void setLangtagRevision(String var1);

   void xsetLangtagRevision(XmlString var1);

   void unsetLangtagRevision();

   GzipCompressionType getGzipCompression();

   boolean isSetGzipCompression();

   void setGzipCompression(GzipCompressionType var1);

   GzipCompressionType addNewGzipCompression();

   void unsetGzipCompression();

   ClassLoadingType getClassLoading();

   boolean isSetClassLoading();

   void setClassLoading(ClassLoadingType var1);

   ClassLoadingType addNewClassLoading();

   void unsetClassLoading();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ContainerDescriptorType newInstance() {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().newInstance(ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType newInstance(XmlOptions options) {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().newInstance(ContainerDescriptorType.type, options);
      }

      public static ContainerDescriptorType parse(String xmlAsString) throws XmlException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContainerDescriptorType.type, options);
      }

      public static ContainerDescriptorType parse(File file) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(file, ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(file, ContainerDescriptorType.type, options);
      }

      public static ContainerDescriptorType parse(URL u) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(u, ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(u, ContainerDescriptorType.type, options);
      }

      public static ContainerDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(is, ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(is, ContainerDescriptorType.type, options);
      }

      public static ContainerDescriptorType parse(Reader r) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(r, ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(r, ContainerDescriptorType.type, options);
      }

      public static ContainerDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, ContainerDescriptorType.type, options);
      }

      public static ContainerDescriptorType parse(Node node) throws XmlException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(node, ContainerDescriptorType.type, (XmlOptions)null);
      }

      public static ContainerDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(node, ContainerDescriptorType.type, options);
      }

      /** @deprecated */
      public static ContainerDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, ContainerDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ContainerDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ContainerDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, ContainerDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContainerDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContainerDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
