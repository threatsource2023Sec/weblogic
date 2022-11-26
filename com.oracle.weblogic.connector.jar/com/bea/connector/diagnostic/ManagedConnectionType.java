package com.bea.connector.diagnostic;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface ManagedConnectionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ManagedConnectionType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("managedconnectiontypee49etype");

   int getHashcode();

   XmlInt xgetHashcode();

   void setHashcode(int var1);

   void xsetHashcode(XmlInt var1);

   String getId();

   XmlString xgetId();

   void setId(String var1);

   void xsetId(XmlString var1);

   TransactionInfoType getTransactionInfo();

   boolean isSetTransactionInfo();

   void setTransactionInfo(TransactionInfoType var1);

   TransactionInfoType addNewTransactionInfo();

   void unsetTransactionInfo();

   public static final class Factory {
      public static ManagedConnectionType newInstance() {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().newInstance(ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType newInstance(XmlOptions options) {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().newInstance(ManagedConnectionType.type, options);
      }

      public static ManagedConnectionType parse(String xmlAsString) throws XmlException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedConnectionType.type, options);
      }

      public static ManagedConnectionType parse(File file) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(file, ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(file, ManagedConnectionType.type, options);
      }

      public static ManagedConnectionType parse(URL u) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(u, ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(u, ManagedConnectionType.type, options);
      }

      public static ManagedConnectionType parse(InputStream is) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(is, ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(is, ManagedConnectionType.type, options);
      }

      public static ManagedConnectionType parse(Reader r) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(r, ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(r, ManagedConnectionType.type, options);
      }

      public static ManagedConnectionType parse(XMLStreamReader sr) throws XmlException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(sr, ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(sr, ManagedConnectionType.type, options);
      }

      public static ManagedConnectionType parse(Node node) throws XmlException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(node, ManagedConnectionType.type, (XmlOptions)null);
      }

      public static ManagedConnectionType parse(Node node, XmlOptions options) throws XmlException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(node, ManagedConnectionType.type, options);
      }

      /** @deprecated */
      public static ManagedConnectionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(xis, ManagedConnectionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ManagedConnectionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ManagedConnectionType)XmlBeans.getContextTypeLoader().parse(xis, ManagedConnectionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedConnectionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedConnectionType.type, options);
      }

      private Factory() {
      }
   }
}
