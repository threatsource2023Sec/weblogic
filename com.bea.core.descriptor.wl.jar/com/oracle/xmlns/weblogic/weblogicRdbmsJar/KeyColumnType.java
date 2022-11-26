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

public interface KeyColumnType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KeyColumnType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("keycolumntype1ff3type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static KeyColumnType newInstance() {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().newInstance(KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType newInstance(XmlOptions options) {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().newInstance(KeyColumnType.type, options);
      }

      public static KeyColumnType parse(String xmlAsString) throws XmlException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeyColumnType.type, options);
      }

      public static KeyColumnType parse(File file) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(file, KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(file, KeyColumnType.type, options);
      }

      public static KeyColumnType parse(URL u) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(u, KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(u, KeyColumnType.type, options);
      }

      public static KeyColumnType parse(InputStream is) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(is, KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(is, KeyColumnType.type, options);
      }

      public static KeyColumnType parse(Reader r) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(r, KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(r, KeyColumnType.type, options);
      }

      public static KeyColumnType parse(XMLStreamReader sr) throws XmlException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(sr, KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(sr, KeyColumnType.type, options);
      }

      public static KeyColumnType parse(Node node) throws XmlException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(node, KeyColumnType.type, (XmlOptions)null);
      }

      public static KeyColumnType parse(Node node, XmlOptions options) throws XmlException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(node, KeyColumnType.type, options);
      }

      /** @deprecated */
      public static KeyColumnType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(xis, KeyColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KeyColumnType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KeyColumnType)XmlBeans.getContextTypeLoader().parse(xis, KeyColumnType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyColumnType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyColumnType.type, options);
      }

      private Factory() {
      }
   }
}
