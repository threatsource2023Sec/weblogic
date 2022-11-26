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

public interface DbmsColumnTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DbmsColumnTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("dbmscolumntypetype0c57type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DbmsColumnTypeType newInstance() {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().newInstance(DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType newInstance(XmlOptions options) {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().newInstance(DbmsColumnTypeType.type, options);
      }

      public static DbmsColumnTypeType parse(String xmlAsString) throws XmlException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DbmsColumnTypeType.type, options);
      }

      public static DbmsColumnTypeType parse(File file) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(file, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(file, DbmsColumnTypeType.type, options);
      }

      public static DbmsColumnTypeType parse(URL u) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(u, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(u, DbmsColumnTypeType.type, options);
      }

      public static DbmsColumnTypeType parse(InputStream is) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(is, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(is, DbmsColumnTypeType.type, options);
      }

      public static DbmsColumnTypeType parse(Reader r) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(r, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(r, DbmsColumnTypeType.type, options);
      }

      public static DbmsColumnTypeType parse(XMLStreamReader sr) throws XmlException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(sr, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(sr, DbmsColumnTypeType.type, options);
      }

      public static DbmsColumnTypeType parse(Node node) throws XmlException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(node, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      public static DbmsColumnTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(node, DbmsColumnTypeType.type, options);
      }

      /** @deprecated */
      public static DbmsColumnTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(xis, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DbmsColumnTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DbmsColumnTypeType)XmlBeans.getContextTypeLoader().parse(xis, DbmsColumnTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DbmsColumnTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DbmsColumnTypeType.type, options);
      }

      private Factory() {
      }
   }
}
