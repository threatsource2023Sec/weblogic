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

public interface JavaPropertyName extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaPropertyName.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("javapropertynamee7f5type");

   public static final class Factory {
      public static JavaPropertyName newValue(Object obj) {
         return (JavaPropertyName)JavaPropertyName.type.newValue(obj);
      }

      public static JavaPropertyName newInstance() {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().newInstance(JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName newInstance(XmlOptions options) {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().newInstance(JavaPropertyName.type, options);
      }

      public static JavaPropertyName parse(String xmlAsString) throws XmlException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaPropertyName.type, options);
      }

      public static JavaPropertyName parse(File file) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(file, JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(file, JavaPropertyName.type, options);
      }

      public static JavaPropertyName parse(URL u) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(u, JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(u, JavaPropertyName.type, options);
      }

      public static JavaPropertyName parse(InputStream is) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(is, JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(is, JavaPropertyName.type, options);
      }

      public static JavaPropertyName parse(Reader r) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(r, JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(r, JavaPropertyName.type, options);
      }

      public static JavaPropertyName parse(XMLStreamReader sr) throws XmlException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(sr, JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(sr, JavaPropertyName.type, options);
      }

      public static JavaPropertyName parse(Node node) throws XmlException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(node, JavaPropertyName.type, (XmlOptions)null);
      }

      public static JavaPropertyName parse(Node node, XmlOptions options) throws XmlException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(node, JavaPropertyName.type, options);
      }

      /** @deprecated */
      public static JavaPropertyName parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(xis, JavaPropertyName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaPropertyName parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaPropertyName)XmlBeans.getContextTypeLoader().parse(xis, JavaPropertyName.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaPropertyName.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaPropertyName.type, options);
      }

      private Factory() {
      }
   }
}
