package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TransactionSupportDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionSupportDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("transactionsupport6da0doctype");

   String getTransactionSupport();

   XmlString xgetTransactionSupport();

   void setTransactionSupport(String var1);

   void xsetTransactionSupport(XmlString var1);

   public static final class Factory {
      public static TransactionSupportDocument newInstance() {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().newInstance(TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument newInstance(XmlOptions options) {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().newInstance(TransactionSupportDocument.type, options);
      }

      public static TransactionSupportDocument parse(String xmlAsString) throws XmlException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionSupportDocument.type, options);
      }

      public static TransactionSupportDocument parse(File file) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(file, TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(file, TransactionSupportDocument.type, options);
      }

      public static TransactionSupportDocument parse(URL u) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(u, TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(u, TransactionSupportDocument.type, options);
      }

      public static TransactionSupportDocument parse(InputStream is) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(is, TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(is, TransactionSupportDocument.type, options);
      }

      public static TransactionSupportDocument parse(Reader r) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(r, TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(r, TransactionSupportDocument.type, options);
      }

      public static TransactionSupportDocument parse(XMLStreamReader sr) throws XmlException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(sr, TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(sr, TransactionSupportDocument.type, options);
      }

      public static TransactionSupportDocument parse(Node node) throws XmlException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(node, TransactionSupportDocument.type, (XmlOptions)null);
      }

      public static TransactionSupportDocument parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(node, TransactionSupportDocument.type, options);
      }

      /** @deprecated */
      public static TransactionSupportDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(xis, TransactionSupportDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionSupportDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionSupportDocument)XmlBeans.getContextTypeLoader().parse(xis, TransactionSupportDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionSupportDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionSupportDocument.type, options);
      }

      private Factory() {
      }
   }
}
