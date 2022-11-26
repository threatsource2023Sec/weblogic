package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JavaClassName extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaClassName.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("javaclassnamec5b6type");

   public static final class Factory {
      public static JavaClassName newValue(Object obj) {
         return (JavaClassName)JavaClassName.type.newValue(obj);
      }

      public static JavaClassName newInstance() {
         return (JavaClassName)XmlBeans.getContextTypeLoader().newInstance(JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName newInstance(XmlOptions options) {
         return (JavaClassName)XmlBeans.getContextTypeLoader().newInstance(JavaClassName.type, options);
      }

      public static JavaClassName parse(String xmlAsString) throws XmlException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaClassName.type, options);
      }

      public static JavaClassName parse(File file) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(file, JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(file, JavaClassName.type, options);
      }

      public static JavaClassName parse(URL u) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(u, JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(u, JavaClassName.type, options);
      }

      public static JavaClassName parse(InputStream is) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(is, JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(is, JavaClassName.type, options);
      }

      public static JavaClassName parse(Reader r) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(r, JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(r, JavaClassName.type, options);
      }

      public static JavaClassName parse(XMLStreamReader sr) throws XmlException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(sr, JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(sr, JavaClassName.type, options);
      }

      public static JavaClassName parse(Node node) throws XmlException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(node, JavaClassName.type, (XmlOptions)null);
      }

      public static JavaClassName parse(Node node, XmlOptions options) throws XmlException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(node, JavaClassName.type, options);
      }

      /** @deprecated */
      public static JavaClassName parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(xis, JavaClassName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaClassName parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaClassName)XmlBeans.getContextTypeLoader().parse(xis, JavaClassName.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaClassName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaClassName.type, options);
      }

      private Factory() {
      }
   }
}
