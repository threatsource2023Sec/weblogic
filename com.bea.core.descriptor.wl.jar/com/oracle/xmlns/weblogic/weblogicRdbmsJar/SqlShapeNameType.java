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

public interface SqlShapeNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SqlShapeNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("sqlshapenametype8985type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SqlShapeNameType newInstance() {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().newInstance(SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType newInstance(XmlOptions options) {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().newInstance(SqlShapeNameType.type, options);
      }

      public static SqlShapeNameType parse(String xmlAsString) throws XmlException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SqlShapeNameType.type, options);
      }

      public static SqlShapeNameType parse(File file) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(file, SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(file, SqlShapeNameType.type, options);
      }

      public static SqlShapeNameType parse(URL u) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(u, SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(u, SqlShapeNameType.type, options);
      }

      public static SqlShapeNameType parse(InputStream is) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(is, SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(is, SqlShapeNameType.type, options);
      }

      public static SqlShapeNameType parse(Reader r) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(r, SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(r, SqlShapeNameType.type, options);
      }

      public static SqlShapeNameType parse(XMLStreamReader sr) throws XmlException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(sr, SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(sr, SqlShapeNameType.type, options);
      }

      public static SqlShapeNameType parse(Node node) throws XmlException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(node, SqlShapeNameType.type, (XmlOptions)null);
      }

      public static SqlShapeNameType parse(Node node, XmlOptions options) throws XmlException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(node, SqlShapeNameType.type, options);
      }

      /** @deprecated */
      public static SqlShapeNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(xis, SqlShapeNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SqlShapeNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SqlShapeNameType)XmlBeans.getContextTypeLoader().parse(xis, SqlShapeNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlShapeNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SqlShapeNameType.type, options);
      }

      private Factory() {
      }
   }
}
