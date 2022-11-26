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

public interface GenericXmlProperty extends BindingProperty {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GenericXmlProperty.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("genericxmlproperty79detype");

   public static final class Factory {
      public static GenericXmlProperty newInstance() {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().newInstance(GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty newInstance(XmlOptions options) {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().newInstance(GenericXmlProperty.type, options);
      }

      public static GenericXmlProperty parse(String xmlAsString) throws XmlException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(xmlAsString, GenericXmlProperty.type, options);
      }

      public static GenericXmlProperty parse(File file) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(file, GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(file, GenericXmlProperty.type, options);
      }

      public static GenericXmlProperty parse(URL u) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(u, GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(u, GenericXmlProperty.type, options);
      }

      public static GenericXmlProperty parse(InputStream is) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(is, GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(is, GenericXmlProperty.type, options);
      }

      public static GenericXmlProperty parse(Reader r) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(r, GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(r, GenericXmlProperty.type, options);
      }

      public static GenericXmlProperty parse(XMLStreamReader sr) throws XmlException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(sr, GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(sr, GenericXmlProperty.type, options);
      }

      public static GenericXmlProperty parse(Node node) throws XmlException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(node, GenericXmlProperty.type, (XmlOptions)null);
      }

      public static GenericXmlProperty parse(Node node, XmlOptions options) throws XmlException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(node, GenericXmlProperty.type, options);
      }

      /** @deprecated */
      public static GenericXmlProperty parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(xis, GenericXmlProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GenericXmlProperty parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GenericXmlProperty)XmlBeans.getContextTypeLoader().parse(xis, GenericXmlProperty.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GenericXmlProperty.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GenericXmlProperty.type, options);
      }

      private Factory() {
      }
   }
}
