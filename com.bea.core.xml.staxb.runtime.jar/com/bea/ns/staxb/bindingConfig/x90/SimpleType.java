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

public interface SimpleType extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleType.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("simpletypee7e0type");

   AsXmlType getAsXml();

   void setAsXml(AsXmlType var1);

   AsXmlType addNewAsXml();

   public static final class Factory {
      public static SimpleType newInstance() {
         return (SimpleType)XmlBeans.getContextTypeLoader().newInstance(SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType newInstance(XmlOptions options) {
         return (SimpleType)XmlBeans.getContextTypeLoader().newInstance(SimpleType.type, options);
      }

      public static SimpleType parse(String xmlAsString) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleType.type, options);
      }

      public static SimpleType parse(File file) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(file, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(file, SimpleType.type, options);
      }

      public static SimpleType parse(URL u) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(u, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(u, SimpleType.type, options);
      }

      public static SimpleType parse(InputStream is) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(is, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(is, SimpleType.type, options);
      }

      public static SimpleType parse(Reader r) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(r, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(r, SimpleType.type, options);
      }

      public static SimpleType parse(XMLStreamReader sr) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(sr, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(sr, SimpleType.type, options);
      }

      public static SimpleType parse(Node node) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(node, SimpleType.type, (XmlOptions)null);
      }

      public static SimpleType parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(node, SimpleType.type, options);
      }

      /** @deprecated */
      public static SimpleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(xis, SimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleType)XmlBeans.getContextTypeLoader().parse(xis, SimpleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleType.type, options);
      }

      private Factory() {
      }
   }
}
