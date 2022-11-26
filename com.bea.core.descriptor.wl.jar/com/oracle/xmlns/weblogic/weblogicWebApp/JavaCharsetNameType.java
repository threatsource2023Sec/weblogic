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

public interface JavaCharsetNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaCharsetNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("javacharsetnametype6e78type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JavaCharsetNameType newInstance() {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().newInstance(JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType newInstance(XmlOptions options) {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().newInstance(JavaCharsetNameType.type, options);
      }

      public static JavaCharsetNameType parse(String xmlAsString) throws XmlException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaCharsetNameType.type, options);
      }

      public static JavaCharsetNameType parse(File file) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(file, JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(file, JavaCharsetNameType.type, options);
      }

      public static JavaCharsetNameType parse(URL u) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(u, JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(u, JavaCharsetNameType.type, options);
      }

      public static JavaCharsetNameType parse(InputStream is) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(is, JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(is, JavaCharsetNameType.type, options);
      }

      public static JavaCharsetNameType parse(Reader r) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(r, JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(r, JavaCharsetNameType.type, options);
      }

      public static JavaCharsetNameType parse(XMLStreamReader sr) throws XmlException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(sr, JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(sr, JavaCharsetNameType.type, options);
      }

      public static JavaCharsetNameType parse(Node node) throws XmlException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(node, JavaCharsetNameType.type, (XmlOptions)null);
      }

      public static JavaCharsetNameType parse(Node node, XmlOptions options) throws XmlException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(node, JavaCharsetNameType.type, options);
      }

      /** @deprecated */
      public static JavaCharsetNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xis, JavaCharsetNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaCharsetNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaCharsetNameType)XmlBeans.getContextTypeLoader().parse(xis, JavaCharsetNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaCharsetNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaCharsetNameType.type, options);
      }

      private Factory() {
      }
   }
}
