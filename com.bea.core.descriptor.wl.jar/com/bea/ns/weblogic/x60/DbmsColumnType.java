package com.bea.ns.weblogic.x60;

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

public interface DbmsColumnType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DbmsColumnType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("dbmscolumntypead4ctype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DbmsColumnType newInstance() {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().newInstance(DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType newInstance(XmlOptions options) {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().newInstance(DbmsColumnType.type, options);
      }

      public static DbmsColumnType parse(String xmlAsString) throws XmlException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DbmsColumnType.type, options);
      }

      public static DbmsColumnType parse(File file) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(file, DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(file, DbmsColumnType.type, options);
      }

      public static DbmsColumnType parse(URL u) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(u, DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(u, DbmsColumnType.type, options);
      }

      public static DbmsColumnType parse(InputStream is) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(is, DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(is, DbmsColumnType.type, options);
      }

      public static DbmsColumnType parse(Reader r) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(r, DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(r, DbmsColumnType.type, options);
      }

      public static DbmsColumnType parse(XMLStreamReader sr) throws XmlException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(sr, DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(sr, DbmsColumnType.type, options);
      }

      public static DbmsColumnType parse(Node node) throws XmlException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(node, DbmsColumnType.type, (XmlOptions)null);
      }

      public static DbmsColumnType parse(Node node, XmlOptions options) throws XmlException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(node, DbmsColumnType.type, options);
      }

      /** @deprecated */
      public static DbmsColumnType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(xis, DbmsColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DbmsColumnType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DbmsColumnType)XmlBeans.getContextTypeLoader().parse(xis, DbmsColumnType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DbmsColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DbmsColumnType.type, options);
      }

      private Factory() {
      }
   }
}
