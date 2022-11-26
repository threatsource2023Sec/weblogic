package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SimpleContentProperty extends BindingProperty {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleContentProperty.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("simplecontentproperty580ftype");

   public static final class Factory {
      public static SimpleContentProperty newInstance() {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().newInstance(SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty newInstance(XmlOptions options) {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().newInstance(SimpleContentProperty.type, options);
      }

      public static SimpleContentProperty parse(String xmlAsString) throws XmlException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleContentProperty.type, options);
      }

      public static SimpleContentProperty parse(File file) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(file, SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(file, SimpleContentProperty.type, options);
      }

      public static SimpleContentProperty parse(URL u) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(u, SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(u, SimpleContentProperty.type, options);
      }

      public static SimpleContentProperty parse(InputStream is) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(is, SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(is, SimpleContentProperty.type, options);
      }

      public static SimpleContentProperty parse(Reader r) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(r, SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(r, SimpleContentProperty.type, options);
      }

      public static SimpleContentProperty parse(XMLStreamReader sr) throws XmlException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(sr, SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(sr, SimpleContentProperty.type, options);
      }

      public static SimpleContentProperty parse(Node node) throws XmlException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(node, SimpleContentProperty.type, (XmlOptions)null);
      }

      public static SimpleContentProperty parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(node, SimpleContentProperty.type, options);
      }

      /** @deprecated */
      public static SimpleContentProperty parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(xis, SimpleContentProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleContentProperty parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleContentProperty)XmlBeans.getContextTypeLoader().parse(xis, SimpleContentProperty.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleContentProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleContentProperty.type, options);
      }

      private Factory() {
      }
   }
}
