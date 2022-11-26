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

public interface DefaultDbmsTablesDdlType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultDbmsTablesDdlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("defaultdbmstablesddltypecc7etype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DefaultDbmsTablesDdlType newInstance() {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().newInstance(DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType newInstance(XmlOptions options) {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().newInstance(DefaultDbmsTablesDdlType.type, options);
      }

      public static DefaultDbmsTablesDdlType parse(String xmlAsString) throws XmlException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDbmsTablesDdlType.type, options);
      }

      public static DefaultDbmsTablesDdlType parse(File file) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(file, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(file, DefaultDbmsTablesDdlType.type, options);
      }

      public static DefaultDbmsTablesDdlType parse(URL u) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(u, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(u, DefaultDbmsTablesDdlType.type, options);
      }

      public static DefaultDbmsTablesDdlType parse(InputStream is) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(is, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(is, DefaultDbmsTablesDdlType.type, options);
      }

      public static DefaultDbmsTablesDdlType parse(Reader r) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(r, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(r, DefaultDbmsTablesDdlType.type, options);
      }

      public static DefaultDbmsTablesDdlType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDbmsTablesDdlType.type, options);
      }

      public static DefaultDbmsTablesDdlType parse(Node node) throws XmlException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(node, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      public static DefaultDbmsTablesDdlType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(node, DefaultDbmsTablesDdlType.type, options);
      }

      /** @deprecated */
      public static DefaultDbmsTablesDdlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultDbmsTablesDdlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultDbmsTablesDdlType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDbmsTablesDdlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDbmsTablesDdlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDbmsTablesDdlType.type, options);
      }

      private Factory() {
      }
   }
}
