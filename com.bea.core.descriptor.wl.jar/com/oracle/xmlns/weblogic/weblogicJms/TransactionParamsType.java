package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
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

public interface TransactionParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("transactionparamstype7501type");

   long getTransactionTimeout();

   XmlLong xgetTransactionTimeout();

   boolean isSetTransactionTimeout();

   void setTransactionTimeout(long var1);

   void xsetTransactionTimeout(XmlLong var1);

   void unsetTransactionTimeout();

   boolean getXaConnectionFactoryEnabled();

   XmlBoolean xgetXaConnectionFactoryEnabled();

   boolean isSetXaConnectionFactoryEnabled();

   void setXaConnectionFactoryEnabled(boolean var1);

   void xsetXaConnectionFactoryEnabled(XmlBoolean var1);

   void unsetXaConnectionFactoryEnabled();

   public static final class Factory {
      public static TransactionParamsType newInstance() {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().newInstance(TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType newInstance(XmlOptions options) {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().newInstance(TransactionParamsType.type, options);
      }

      public static TransactionParamsType parse(String xmlAsString) throws XmlException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionParamsType.type, options);
      }

      public static TransactionParamsType parse(File file) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(file, TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(file, TransactionParamsType.type, options);
      }

      public static TransactionParamsType parse(URL u) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(u, TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(u, TransactionParamsType.type, options);
      }

      public static TransactionParamsType parse(InputStream is) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(is, TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(is, TransactionParamsType.type, options);
      }

      public static TransactionParamsType parse(Reader r) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(r, TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(r, TransactionParamsType.type, options);
      }

      public static TransactionParamsType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(sr, TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(sr, TransactionParamsType.type, options);
      }

      public static TransactionParamsType parse(Node node) throws XmlException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(node, TransactionParamsType.type, (XmlOptions)null);
      }

      public static TransactionParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(node, TransactionParamsType.type, options);
      }

      /** @deprecated */
      public static TransactionParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(xis, TransactionParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionParamsType)XmlBeans.getContextTypeLoader().parse(xis, TransactionParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionParamsType.type, options);
      }

      private Factory() {
      }
   }
}
