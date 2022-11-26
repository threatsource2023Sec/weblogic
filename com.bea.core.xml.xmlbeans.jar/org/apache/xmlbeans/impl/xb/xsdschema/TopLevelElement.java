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

public interface TopLevelElement extends Element {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TopLevelElement.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("toplevelelement98d8type");

   String getName();

   XmlNCName xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   void unsetName();

   public static final class Factory {
      public static TopLevelElement newInstance() {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().newInstance(TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement newInstance(XmlOptions options) {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().newInstance(TopLevelElement.type, options);
      }

      public static TopLevelElement parse(String xmlAsString) throws XmlException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(xmlAsString, TopLevelElement.type, options);
      }

      public static TopLevelElement parse(File file) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((File)file, TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(file, TopLevelElement.type, options);
      }

      public static TopLevelElement parse(URL u) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((URL)u, TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(u, TopLevelElement.type, options);
      }

      public static TopLevelElement parse(InputStream is) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((InputStream)is, TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(is, TopLevelElement.type, options);
      }

      public static TopLevelElement parse(Reader r) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((Reader)r, TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(r, TopLevelElement.type, options);
      }

      public static TopLevelElement parse(XMLStreamReader sr) throws XmlException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(sr, TopLevelElement.type, options);
      }

      public static TopLevelElement parse(Node node) throws XmlException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((Node)node, TopLevelElement.type, (XmlOptions)null);
      }

      public static TopLevelElement parse(Node node, XmlOptions options) throws XmlException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(node, TopLevelElement.type, options);
      }

      /** @deprecated */
      public static TopLevelElement parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TopLevelElement.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TopLevelElement parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TopLevelElement)XmlBeans.getContextTypeLoader().parse(xis, TopLevelElement.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopLevelElement.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TopLevelElement.type, options);
      }

      private Factory() {
      }
   }
}
