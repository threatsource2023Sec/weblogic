package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlNCName;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface TopLevelAttribute extends Attribute {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TopLevelAttribute.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("toplevelattributeb338type");

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   public static final class Factory {
      public static TopLevelAttribute newInstance() {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().newInstance(TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute newInstance(XmlOptions options) {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().newInstance(TopLevelAttribute.type, options);
      }

      public static TopLevelAttribute parse(String xmlAsString) throws XmlException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(xmlAsString, TopLevelAttribute.type, options);
      }

      public static TopLevelAttribute parse(File file) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((File)file, TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(file, TopLevelAttribute.type, options);
      }

      public static TopLevelAttribute parse(URL u) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((URL)u, TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(u, TopLevelAttribute.type, options);
      }

      public static TopLevelAttribute parse(InputStream is) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((InputStream)is, TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(is, TopLevelAttribute.type, options);
      }

      public static TopLevelAttribute parse(Reader r) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((Reader)r, TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(r, TopLevelAttribute.type, options);
      }

      public static TopLevelAttribute parse(XMLStreamReader sr) throws XmlException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(sr, TopLevelAttribute.type, options);
      }

      public static TopLevelAttribute parse(Node node) throws XmlException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((Node)node, TopLevelAttribute.type, (XmlOptions)null);
      }

      public static TopLevelAttribute parse(Node node, XmlOptions options) throws XmlException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(node, TopLevelAttribute.type, options);
      }

      /** @deprecated */
      public static TopLevelAttribute parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TopLevelAttribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TopLevelAttribute parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TopLevelAttribute)XmlBeans.getContextTypeLoader().parse(xis, TopLevelAttribute.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopLevelAttribute.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopLevelAttribute.type, options);
      }

      private Factory() {
      }
   }
}
