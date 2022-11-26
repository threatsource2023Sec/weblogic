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
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AnyAttributeDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnyAttributeDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anyattribute23b3doctype");

   Wildcard getAnyAttribute();

   void setAnyAttribute(Wildcard var1);

   Wildcard addNewAnyAttribute();

   public static final class Factory {
      public static AnyAttributeDocument newInstance() {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().newInstance(AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument newInstance(XmlOptions options) {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().newInstance(AnyAttributeDocument.type, options);
      }

      public static AnyAttributeDocument parse(String xmlAsString) throws XmlException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnyAttributeDocument.type, options);
      }

      public static AnyAttributeDocument parse(File file) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((File)file, AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(file, AnyAttributeDocument.type, options);
      }

      public static AnyAttributeDocument parse(URL u) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((URL)u, AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(u, AnyAttributeDocument.type, options);
      }

      public static AnyAttributeDocument parse(InputStream is) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(is, AnyAttributeDocument.type, options);
      }

      public static AnyAttributeDocument parse(Reader r) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(r, AnyAttributeDocument.type, options);
      }

      public static AnyAttributeDocument parse(XMLStreamReader sr) throws XmlException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(sr, AnyAttributeDocument.type, options);
      }

      public static AnyAttributeDocument parse(Node node) throws XmlException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((Node)node, AnyAttributeDocument.type, (XmlOptions)null);
      }

      public static AnyAttributeDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(node, AnyAttributeDocument.type, options);
      }

      /** @deprecated */
      public static AnyAttributeDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AnyAttributeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnyAttributeDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnyAttributeDocument)XmlBeans.getContextTypeLoader().parse(xis, AnyAttributeDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnyAttributeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnyAttributeDocument.type, options);
      }

      private Factory() {
      }
   }
}
