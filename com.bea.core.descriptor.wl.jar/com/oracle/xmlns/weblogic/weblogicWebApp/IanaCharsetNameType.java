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

public interface IanaCharsetNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IanaCharsetNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("ianacharsetnametype3f2ftype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IanaCharsetNameType newInstance() {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().newInstance(IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType newInstance(XmlOptions options) {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().newInstance(IanaCharsetNameType.type, options);
      }

      public static IanaCharsetNameType parse(String xmlAsString) throws XmlException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IanaCharsetNameType.type, options);
      }

      public static IanaCharsetNameType parse(File file) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(file, IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(file, IanaCharsetNameType.type, options);
      }

      public static IanaCharsetNameType parse(URL u) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(u, IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(u, IanaCharsetNameType.type, options);
      }

      public static IanaCharsetNameType parse(InputStream is) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(is, IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(is, IanaCharsetNameType.type, options);
      }

      public static IanaCharsetNameType parse(Reader r) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(r, IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(r, IanaCharsetNameType.type, options);
      }

      public static IanaCharsetNameType parse(XMLStreamReader sr) throws XmlException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(sr, IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(sr, IanaCharsetNameType.type, options);
      }

      public static IanaCharsetNameType parse(Node node) throws XmlException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(node, IanaCharsetNameType.type, (XmlOptions)null);
      }

      public static IanaCharsetNameType parse(Node node, XmlOptions options) throws XmlException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(node, IanaCharsetNameType.type, options);
      }

      /** @deprecated */
      public static IanaCharsetNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xis, IanaCharsetNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IanaCharsetNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IanaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xis, IanaCharsetNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IanaCharsetNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IanaCharsetNameType.type, options);
      }

      private Factory() {
      }
   }
}
