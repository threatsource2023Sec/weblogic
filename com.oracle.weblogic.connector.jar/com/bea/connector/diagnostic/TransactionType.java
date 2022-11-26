package com.bea.connector.diagnostic;

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

public interface TransactionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("transactiontypeff5ftype");

   String getTransactionType();

   XmlString xgetTransactionType();

   void setTransactionType(String var1);

   void xsetTransactionType(XmlString var1);

   String getId();

   XmlString xgetId();

   void setId(String var1);

   void xsetId(XmlString var1);

   String getStatus();

   XmlString xgetStatus();

   void setStatus(String var1);

   void xsetStatus(XmlString var1);

   String getEnlistmentTime();

   XmlString xgetEnlistmentTime();

   void setEnlistmentTime(String var1);

   void xsetEnlistmentTime(XmlString var1);

   public static final class Factory {
      public static TransactionType newInstance() {
         return (TransactionType)XmlBeans.getContextTypeLoader().newInstance(TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType newInstance(XmlOptions options) {
         return (TransactionType)XmlBeans.getContextTypeLoader().newInstance(TransactionType.type, options);
      }

      public static TransactionType parse(String xmlAsString) throws XmlException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionType.type, options);
      }

      public static TransactionType parse(File file) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(file, TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(file, TransactionType.type, options);
      }

      public static TransactionType parse(URL u) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(u, TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(u, TransactionType.type, options);
      }

      public static TransactionType parse(InputStream is) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(is, TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(is, TransactionType.type, options);
      }

      public static TransactionType parse(Reader r) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(r, TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(r, TransactionType.type, options);
      }

      public static TransactionType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(sr, TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(sr, TransactionType.type, options);
      }

      public static TransactionType parse(Node node) throws XmlException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(node, TransactionType.type, (XmlOptions)null);
      }

      public static TransactionType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(node, TransactionType.type, options);
      }

      /** @deprecated */
      public static TransactionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(xis, TransactionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionType)XmlBeans.getContextTypeLoader().parse(xis, TransactionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionType.type, options);
      }

      private Factory() {
      }
   }
}
