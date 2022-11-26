package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlString extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_string");

   public static final class Factory {
      public static XmlString newInstance() {
         return (XmlString)XmlBeans.getContextTypeLoader().newInstance(XmlString.type, (XmlOptions)null);
      }

      public static XmlString newInstance(XmlOptions options) {
         return (XmlString)XmlBeans.getContextTypeLoader().newInstance(XmlString.type, options);
      }

      public static XmlString newValue(Object obj) {
         return (XmlString)XmlString.type.newValue(obj);
      }

      public static XmlString parse(String s) throws XmlException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((String)s, XmlString.type, (XmlOptions)null);
      }

      public static XmlString parse(String s, XmlOptions options) throws XmlException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(s, XmlString.type, options);
      }

      public static XmlString parse(File f) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((File)f, XmlString.type, (XmlOptions)null);
      }

      public static XmlString parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(f, XmlString.type, options);
      }

      public static XmlString parse(URL u) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((URL)u, XmlString.type, (XmlOptions)null);
      }

      public static XmlString parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(u, XmlString.type, options);
      }

      public static XmlString parse(InputStream is) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlString.type, (XmlOptions)null);
      }

      public static XmlString parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(is, XmlString.type, options);
      }

      public static XmlString parse(Reader r) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlString.type, (XmlOptions)null);
      }

      public static XmlString parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(r, XmlString.type, options);
      }

      public static XmlString parse(Node node) throws XmlException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((Node)node, XmlString.type, (XmlOptions)null);
      }

      public static XmlString parse(Node node, XmlOptions options) throws XmlException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(node, XmlString.type, options);
      }

      /** @deprecated */
      public static XmlString parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlString.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlString parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(xis, XmlString.type, options);
      }

      public static XmlString parse(XMLStreamReader xsr) throws XmlException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlString.type, (XmlOptions)null);
      }

      public static XmlString parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlString)XmlBeans.getContextTypeLoader().parse(xsr, XmlString.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlString.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlString.type, options);
      }

      private Factory() {
      }
   }
}
