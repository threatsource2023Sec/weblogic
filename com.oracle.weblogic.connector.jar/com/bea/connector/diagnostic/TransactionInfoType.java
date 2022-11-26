package com.bea.connector.diagnostic;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface TransactionInfoType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionInfoType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("transactioninfotypea8adtype");

   TransactionType[] getTransactionArray();

   TransactionType getTransactionArray(int var1);

   int sizeOfTransactionArray();

   void setTransactionArray(TransactionType[] var1);

   void setTransactionArray(int var1, TransactionType var2);

   TransactionType insertNewTransaction(int var1);

   TransactionType addNewTransaction();

   void removeTransaction(int var1);

   public static final class Factory {
      public static TransactionInfoType newInstance() {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().newInstance(TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType newInstance(XmlOptions options) {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().newInstance(TransactionInfoType.type, options);
      }

      public static TransactionInfoType parse(String xmlAsString) throws XmlException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionInfoType.type, options);
      }

      public static TransactionInfoType parse(File file) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(file, TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(file, TransactionInfoType.type, options);
      }

      public static TransactionInfoType parse(URL u) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(u, TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(u, TransactionInfoType.type, options);
      }

      public static TransactionInfoType parse(InputStream is) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(is, TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(is, TransactionInfoType.type, options);
      }

      public static TransactionInfoType parse(Reader r) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(r, TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(r, TransactionInfoType.type, options);
      }

      public static TransactionInfoType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(sr, TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(sr, TransactionInfoType.type, options);
      }

      public static TransactionInfoType parse(Node node) throws XmlException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(node, TransactionInfoType.type, (XmlOptions)null);
      }

      public static TransactionInfoType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(node, TransactionInfoType.type, options);
      }

      /** @deprecated */
      public static TransactionInfoType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(xis, TransactionInfoType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionInfoType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionInfoType)XmlBeans.getContextTypeLoader().parse(xis, TransactionInfoType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionInfoType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionInfoType.type, options);
      }

      private Factory() {
      }
   }
}
