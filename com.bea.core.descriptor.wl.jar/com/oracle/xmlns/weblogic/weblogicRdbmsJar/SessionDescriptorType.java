package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdPositiveIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SessionDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SessionDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("sessiondescriptortype5575type");

   XsdIntegerType getTimeoutSecs();

   boolean isSetTimeoutSecs();

   void setTimeoutSecs(XsdIntegerType var1);

   XsdIntegerType addNewTimeoutSecs();

   void unsetTimeoutSecs();

   XsdIntegerType getInvalidationIntervalSecs();

   boolean isSetInvalidationIntervalSecs();

   void setInvalidationIntervalSecs(XsdIntegerType var1);

   XsdIntegerType addNewInvalidationIntervalSecs();

   void unsetInvalidationIntervalSecs();

   XsdIntegerType getMaxSavePostSize();

   boolean isSetMaxSavePostSize();

   void setMaxSavePostSize(XsdIntegerType var1);

   XsdIntegerType addNewMaxSavePostSize();

   void unsetMaxSavePostSize();

   XsdIntegerType getSavePostTimeoutSecs();

   boolean isSetSavePostTimeoutSecs();

   void setSavePostTimeoutSecs(XsdIntegerType var1);

   XsdIntegerType addNewSavePostTimeoutSecs();

   void unsetSavePostTimeoutSecs();

   XsdIntegerType getSavePostTimeoutIntervalSecs();

   boolean isSetSavePostTimeoutIntervalSecs();

   void setSavePostTimeoutIntervalSecs(XsdIntegerType var1);

   XsdIntegerType addNewSavePostTimeoutIntervalSecs();

   void unsetSavePostTimeoutIntervalSecs();

   TrueFalseType getDebugEnabled();

   boolean isSetDebugEnabled();

   void setDebugEnabled(TrueFalseType var1);

   TrueFalseType addNewDebugEnabled();

   void unsetDebugEnabled();

   XsdIntegerType getIdLength();

   boolean isSetIdLength();

   void setIdLength(XsdIntegerType var1);

   XsdIntegerType addNewIdLength();

   void unsetIdLength();

   XsdIntegerType getAuthCookieIdLength();

   boolean isSetAuthCookieIdLength();

   void setAuthCookieIdLength(XsdIntegerType var1);

   XsdIntegerType addNewAuthCookieIdLength();

   void unsetAuthCookieIdLength();

   TrueFalseType getTrackingEnabled();

   boolean isSetTrackingEnabled();

   void setTrackingEnabled(TrueFalseType var1);

   TrueFalseType addNewTrackingEnabled();

   void unsetTrackingEnabled();

   XsdIntegerType getCacheSize();

   boolean isSetCacheSize();

   void setCacheSize(XsdIntegerType var1);

   XsdIntegerType addNewCacheSize();

   void unsetCacheSize();

   XsdIntegerType getMaxInMemorySessions();

   boolean isSetMaxInMemorySessions();

   void setMaxInMemorySessions(XsdIntegerType var1);

   XsdIntegerType addNewMaxInMemorySessions();

   void unsetMaxInMemorySessions();

   TrueFalseType getCookiesEnabled();

   boolean isSetCookiesEnabled();

   void setCookiesEnabled(TrueFalseType var1);

   TrueFalseType addNewCookiesEnabled();

   void unsetCookiesEnabled();

   String getCookieName();

   XmlString xgetCookieName();

   boolean isSetCookieName();

   void setCookieName(String var1);

   void xsetCookieName(XmlString var1);

   void unsetCookieName();

   String getCookiePath();

   XmlString xgetCookiePath();

   boolean isSetCookiePath();

   void setCookiePath(String var1);

   void xsetCookiePath(XmlString var1);

   void unsetCookiePath();

   String getCookieDomain();

   XmlString xgetCookieDomain();

   boolean isSetCookieDomain();

   void setCookieDomain(String var1);

   void xsetCookieDomain(XmlString var1);

   void unsetCookieDomain();

   String getCookieComment();

   XmlString xgetCookieComment();

   boolean isSetCookieComment();

   void setCookieComment(String var1);

   void xsetCookieComment(XmlString var1);

   void unsetCookieComment();

   TrueFalseType getCookieSecure();

   boolean isSetCookieSecure();

   void setCookieSecure(TrueFalseType var1);

   TrueFalseType addNewCookieSecure();

   void unsetCookieSecure();

   XsdIntegerType getCookieMaxAgeSecs();

   boolean isSetCookieMaxAgeSecs();

   void setCookieMaxAgeSecs(XsdIntegerType var1);

   XsdIntegerType addNewCookieMaxAgeSecs();

   void unsetCookieMaxAgeSecs();

   TrueFalseType getCookieHttpOnly();

   boolean isSetCookieHttpOnly();

   void setCookieHttpOnly(TrueFalseType var1);

   TrueFalseType addNewCookieHttpOnly();

   void unsetCookieHttpOnly();

   String getPersistentStoreType();

   XmlString xgetPersistentStoreType();

   boolean isSetPersistentStoreType();

   void setPersistentStoreType(String var1);

   void xsetPersistentStoreType(XmlString var1);

   void unsetPersistentStoreType();

   String getPersistentStoreCookieName();

   XmlString xgetPersistentStoreCookieName();

   boolean isSetPersistentStoreCookieName();

   void setPersistentStoreCookieName(String var1);

   void xsetPersistentStoreCookieName(XmlString var1);

   void unsetPersistentStoreCookieName();

   String getPersistentStoreDir();

   XmlString xgetPersistentStoreDir();

   boolean isSetPersistentStoreDir();

   void setPersistentStoreDir(String var1);

   void xsetPersistentStoreDir(XmlString var1);

   void unsetPersistentStoreDir();

   String getPersistentStorePool();

   XmlString xgetPersistentStorePool();

   boolean isSetPersistentStorePool();

   void setPersistentStorePool(String var1);

   void xsetPersistentStorePool(XmlString var1);

   void unsetPersistentStorePool();

   String getPersistentDataSourceJndiName();

   XmlString xgetPersistentDataSourceJndiName();

   boolean isSetPersistentDataSourceJndiName();

   void setPersistentDataSourceJndiName(String var1);

   void xsetPersistentDataSourceJndiName(XmlString var1);

   void unsetPersistentDataSourceJndiName();

   XsdIntegerType getPersistentSessionFlushInterval();

   boolean isSetPersistentSessionFlushInterval();

   void setPersistentSessionFlushInterval(XsdIntegerType var1);

   XsdIntegerType addNewPersistentSessionFlushInterval();

   void unsetPersistentSessionFlushInterval();

   XsdPositiveIntegerType getPersistentSessionFlushThreshold();

   boolean isSetPersistentSessionFlushThreshold();

   void setPersistentSessionFlushThreshold(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewPersistentSessionFlushThreshold();

   void unsetPersistentSessionFlushThreshold();

   XsdPositiveIntegerType getPersistentAsyncQueueTimeout();

   boolean isSetPersistentAsyncQueueTimeout();

   void setPersistentAsyncQueueTimeout(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewPersistentAsyncQueueTimeout();

   void unsetPersistentAsyncQueueTimeout();

   String getPersistentStoreTable();

   XmlString xgetPersistentStoreTable();

   boolean isSetPersistentStoreTable();

   void setPersistentStoreTable(String var1);

   void xsetPersistentStoreTable(XmlString var1);

   void unsetPersistentStoreTable();

   String getJdbcColumnNameMaxInactiveInterval();

   XmlString xgetJdbcColumnNameMaxInactiveInterval();

   boolean isSetJdbcColumnNameMaxInactiveInterval();

   void setJdbcColumnNameMaxInactiveInterval(String var1);

   void xsetJdbcColumnNameMaxInactiveInterval(XmlString var1);

   void unsetJdbcColumnNameMaxInactiveInterval();

   TrueFalseType getUrlRewritingEnabled();

   boolean isSetUrlRewritingEnabled();

   void setUrlRewritingEnabled(TrueFalseType var1);

   TrueFalseType addNewUrlRewritingEnabled();

   void unsetUrlRewritingEnabled();

   TrueFalseType getHttpProxyCachingOfCookies();

   boolean isSetHttpProxyCachingOfCookies();

   void setHttpProxyCachingOfCookies(TrueFalseType var1);

   TrueFalseType addNewHttpProxyCachingOfCookies();

   void unsetHttpProxyCachingOfCookies();

   TrueFalseType getEncodeSessionIdInQueryParams();

   boolean isSetEncodeSessionIdInQueryParams();

   void setEncodeSessionIdInQueryParams(TrueFalseType var1);

   TrueFalseType addNewEncodeSessionIdInQueryParams();

   void unsetEncodeSessionIdInQueryParams();

   String getMonitoringAttributeName();

   XmlString xgetMonitoringAttributeName();

   boolean isSetMonitoringAttributeName();

   void setMonitoringAttributeName(String var1);

   void xsetMonitoringAttributeName(XmlString var1);

   void unsetMonitoringAttributeName();

   TrueFalseType getSharingEnabled();

   boolean isSetSharingEnabled();

   void setSharingEnabled(TrueFalseType var1);

   TrueFalseType addNewSharingEnabled();

   void unsetSharingEnabled();

   TrueFalseType getInvalidateOnRelogin();

   boolean isSetInvalidateOnRelogin();

   void setInvalidateOnRelogin(TrueFalseType var1);

   TrueFalseType addNewInvalidateOnRelogin();

   void unsetInvalidateOnRelogin();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SessionDescriptorType newInstance() {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType newInstance(XmlOptions options) {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(SessionDescriptorType.type, options);
      }

      public static SessionDescriptorType parse(String xmlAsString) throws XmlException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionDescriptorType.type, options);
      }

      public static SessionDescriptorType parse(File file) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, SessionDescriptorType.type, options);
      }

      public static SessionDescriptorType parse(URL u) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, SessionDescriptorType.type, options);
      }

      public static SessionDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, SessionDescriptorType.type, options);
      }

      public static SessionDescriptorType parse(Reader r) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, SessionDescriptorType.type, options);
      }

      public static SessionDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, SessionDescriptorType.type, options);
      }

      public static SessionDescriptorType parse(Node node) throws XmlException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, SessionDescriptorType.type, (XmlOptions)null);
      }

      public static SessionDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, SessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static SessionDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, SessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SessionDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, SessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
