package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface DatabaseTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DatabaseTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("databasetypetype80c9type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DatabaseTypeType newInstance() {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().newInstance(DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType newInstance(XmlOptions options) {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().newInstance(DatabaseTypeType.type, options);
      }

      public static DatabaseTypeType parse(String xmlAsString) throws XmlException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DatabaseTypeType.type, options);
      }

      public static DatabaseTypeType parse(File file) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(file, DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(file, DatabaseTypeType.type, options);
      }

      public static DatabaseTypeType parse(URL u) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(u, DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(u, DatabaseTypeType.type, options);
      }

      public static DatabaseTypeType parse(InputStream is) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(is, DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(is, DatabaseTypeType.type, options);
      }

      public static DatabaseTypeType parse(Reader r) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(r, DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(r, DatabaseTypeType.type, options);
      }

      public static DatabaseTypeType parse(XMLStreamReader sr) throws XmlException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(sr, DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(sr, DatabaseTypeType.type, options);
      }

      public static DatabaseTypeType parse(Node node) throws XmlException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(node, DatabaseTypeType.type, (XmlOptions)null);
      }

      public static DatabaseTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(node, DatabaseTypeType.type, options);
      }

      /** @deprecated */
      public static DatabaseTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(xis, DatabaseTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DatabaseTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DatabaseTypeType)XmlBeans.getContextTypeLoader().parse(xis, DatabaseTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DatabaseTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DatabaseTypeType.type, options);
      }

      private Factory() {
      }
   }
}
