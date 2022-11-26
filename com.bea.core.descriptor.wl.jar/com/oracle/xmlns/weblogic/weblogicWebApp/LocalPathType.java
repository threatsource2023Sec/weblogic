package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface LocalPathType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalPathType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("localpathtype447atype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static LocalPathType newInstance() {
         return (LocalPathType)XmlBeans.getContextTypeLoader().newInstance(LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType newInstance(XmlOptions options) {
         return (LocalPathType)XmlBeans.getContextTypeLoader().newInstance(LocalPathType.type, options);
      }

      public static LocalPathType parse(String xmlAsString) throws XmlException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalPathType.type, options);
      }

      public static LocalPathType parse(File file) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(file, LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(file, LocalPathType.type, options);
      }

      public static LocalPathType parse(URL u) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(u, LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(u, LocalPathType.type, options);
      }

      public static LocalPathType parse(InputStream is) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(is, LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(is, LocalPathType.type, options);
      }

      public static LocalPathType parse(Reader r) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(r, LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(r, LocalPathType.type, options);
      }

      public static LocalPathType parse(XMLStreamReader sr) throws XmlException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(sr, LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(sr, LocalPathType.type, options);
      }

      public static LocalPathType parse(Node node) throws XmlException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(node, LocalPathType.type, (XmlOptions)null);
      }

      public static LocalPathType parse(Node node, XmlOptions options) throws XmlException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(node, LocalPathType.type, options);
      }

      /** @deprecated */
      public static LocalPathType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(xis, LocalPathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalPathType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalPathType)XmlBeans.getContextTypeLoader().parse(xis, LocalPathType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalPathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalPathType.type, options);
      }

      private Factory() {
      }
   }
}
