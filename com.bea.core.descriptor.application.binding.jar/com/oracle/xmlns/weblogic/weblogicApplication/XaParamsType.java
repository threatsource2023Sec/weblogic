package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XaParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XaParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("xaparamstypead93type");

   int getDebugLevel();

   XmlInt xgetDebugLevel();

   boolean isSetDebugLevel();

   void setDebugLevel(int var1);

   void xsetDebugLevel(XmlInt var1);

   void unsetDebugLevel();

   TrueFalseType getKeepConnUntilTxCompleteEnabled();

   boolean isSetKeepConnUntilTxCompleteEnabled();

   void setKeepConnUntilTxCompleteEnabled(TrueFalseType var1);

   TrueFalseType addNewKeepConnUntilTxCompleteEnabled();

   void unsetKeepConnUntilTxCompleteEnabled();

   TrueFalseType getEndOnlyOnceEnabled();

   boolean isSetEndOnlyOnceEnabled();

   void setEndOnlyOnceEnabled(TrueFalseType var1);

   TrueFalseType addNewEndOnlyOnceEnabled();

   void unsetEndOnlyOnceEnabled();

   TrueFalseType getRecoverOnlyOnceEnabled();

   boolean isSetRecoverOnlyOnceEnabled();

   void setRecoverOnlyOnceEnabled(TrueFalseType var1);

   TrueFalseType addNewRecoverOnlyOnceEnabled();

   void unsetRecoverOnlyOnceEnabled();

   TrueFalseType getTxContextOnCloseNeeded();

   boolean isSetTxContextOnCloseNeeded();

   void setTxContextOnCloseNeeded(TrueFalseType var1);

   TrueFalseType addNewTxContextOnCloseNeeded();

   void unsetTxContextOnCloseNeeded();

   TrueFalseType getNewConnForCommitEnabled();

   boolean isSetNewConnForCommitEnabled();

   void setNewConnForCommitEnabled(TrueFalseType var1);

   TrueFalseType addNewNewConnForCommitEnabled();

   void unsetNewConnForCommitEnabled();

   int getPreparedStatementCacheSize();

   XmlInt xgetPreparedStatementCacheSize();

   boolean isSetPreparedStatementCacheSize();

   void setPreparedStatementCacheSize(int var1);

   void xsetPreparedStatementCacheSize(XmlInt var1);

   void unsetPreparedStatementCacheSize();

   TrueFalseType getKeepLogicalConnOpenOnRelease();

   boolean isSetKeepLogicalConnOpenOnRelease();

   void setKeepLogicalConnOpenOnRelease(TrueFalseType var1);

   TrueFalseType addNewKeepLogicalConnOpenOnRelease();

   void unsetKeepLogicalConnOpenOnRelease();

   TrueFalseType getLocalTransactionSupported();

   boolean isSetLocalTransactionSupported();

   void setLocalTransactionSupported(TrueFalseType var1);

   TrueFalseType addNewLocalTransactionSupported();

   void unsetLocalTransactionSupported();

   TrueFalseType getResourceHealthMonitoringEnabled();

   boolean isSetResourceHealthMonitoringEnabled();

   void setResourceHealthMonitoringEnabled(TrueFalseType var1);

   TrueFalseType addNewResourceHealthMonitoringEnabled();

   void unsetResourceHealthMonitoringEnabled();

   TrueFalseType getXaSetTransactionTimeout();

   boolean isSetXaSetTransactionTimeout();

   void setXaSetTransactionTimeout(TrueFalseType var1);

   TrueFalseType addNewXaSetTransactionTimeout();

   void unsetXaSetTransactionTimeout();

   int getXaTransactionTimeout();

   XmlInt xgetXaTransactionTimeout();

   boolean isSetXaTransactionTimeout();

   void setXaTransactionTimeout(int var1);

   void xsetXaTransactionTimeout(XmlInt var1);

   void unsetXaTransactionTimeout();

   TrueFalseType getRollbackLocaltxUponConnclose();

   boolean isSetRollbackLocaltxUponConnclose();

   void setRollbackLocaltxUponConnclose(TrueFalseType var1);

   TrueFalseType addNewRollbackLocaltxUponConnclose();

   void unsetRollbackLocaltxUponConnclose();

   public static final class Factory {
      public static XaParamsType newInstance() {
         return (XaParamsType)XmlBeans.getContextTypeLoader().newInstance(XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType newInstance(XmlOptions options) {
         return (XaParamsType)XmlBeans.getContextTypeLoader().newInstance(XaParamsType.type, options);
      }

      public static XaParamsType parse(String xmlAsString) throws XmlException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, XaParamsType.type, options);
      }

      public static XaParamsType parse(File file) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(file, XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(file, XaParamsType.type, options);
      }

      public static XaParamsType parse(URL u) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(u, XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(u, XaParamsType.type, options);
      }

      public static XaParamsType parse(InputStream is) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(is, XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(is, XaParamsType.type, options);
      }

      public static XaParamsType parse(Reader r) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(r, XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(r, XaParamsType.type, options);
      }

      public static XaParamsType parse(XMLStreamReader sr) throws XmlException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(sr, XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(sr, XaParamsType.type, options);
      }

      public static XaParamsType parse(Node node) throws XmlException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(node, XaParamsType.type, (XmlOptions)null);
      }

      public static XaParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(node, XaParamsType.type, options);
      }

      /** @deprecated */
      public static XaParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(xis, XaParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XaParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XaParamsType)XmlBeans.getContextTypeLoader().parse(xis, XaParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XaParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XaParamsType.type, options);
      }

      private Factory() {
      }
   }
}
