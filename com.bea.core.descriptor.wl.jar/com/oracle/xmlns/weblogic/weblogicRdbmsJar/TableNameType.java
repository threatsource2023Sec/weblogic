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

public interface TableNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("tablenametype0099type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TableNameType newInstance() {
         return (TableNameType)XmlBeans.getContextTypeLoader().newInstance(TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType newInstance(XmlOptions options) {
         return (TableNameType)XmlBeans.getContextTypeLoader().newInstance(TableNameType.type, options);
      }

      public static TableNameType parse(String xmlAsString) throws XmlException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableNameType.type, options);
      }

      public static TableNameType parse(File file) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(file, TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(file, TableNameType.type, options);
      }

      public static TableNameType parse(URL u) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(u, TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(u, TableNameType.type, options);
      }

      public static TableNameType parse(InputStream is) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(is, TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(is, TableNameType.type, options);
      }

      public static TableNameType parse(Reader r) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(r, TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(r, TableNameType.type, options);
      }

      public static TableNameType parse(XMLStreamReader sr) throws XmlException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(sr, TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(sr, TableNameType.type, options);
      }

      public static TableNameType parse(Node node) throws XmlException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(node, TableNameType.type, (XmlOptions)null);
      }

      public static TableNameType parse(Node node, XmlOptions options) throws XmlException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(node, TableNameType.type, options);
      }

      /** @deprecated */
      public static TableNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(xis, TableNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableNameType)XmlBeans.getContextTypeLoader().parse(xis, TableNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableNameType.type, options);
      }

      private Factory() {
      }
   }
}
