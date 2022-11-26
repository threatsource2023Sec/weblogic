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

public interface XmlAnyURI extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_anyURI");

   public static final class Factory {
      public static XmlAnyURI newInstance() {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().newInstance(XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI newInstance(XmlOptions options) {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().newInstance(XmlAnyURI.type, options);
      }

      public static XmlAnyURI newValue(Object obj) {
         return (XmlAnyURI)XmlAnyURI.type.newValue(obj);
      }

      public static XmlAnyURI parse(String s) throws XmlException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((String)s, XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI parse(String s, XmlOptions options) throws XmlException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(s, XmlAnyURI.type, options);
      }

      public static XmlAnyURI parse(File f) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((File)f, XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(f, XmlAnyURI.type, options);
      }

      public static XmlAnyURI parse(URL u) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((URL)u, XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(u, XmlAnyURI.type, options);
      }

      public static XmlAnyURI parse(InputStream is) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(is, XmlAnyURI.type, options);
      }

      public static XmlAnyURI parse(Reader r) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(r, XmlAnyURI.type, options);
      }

      public static XmlAnyURI parse(Node node) throws XmlException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((Node)node, XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI parse(Node node, XmlOptions options) throws XmlException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(node, XmlAnyURI.type, options);
      }

      /** @deprecated */
      public static XmlAnyURI parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlAnyURI.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlAnyURI parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(xis, XmlAnyURI.type, options);
      }

      public static XmlAnyURI parse(XMLStreamReader xsr) throws XmlException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlAnyURI.type, (XmlOptions)null);
      }

      public static XmlAnyURI parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlAnyURI)XmlBeans.getContextTypeLoader().parse(xsr, XmlAnyURI.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlAnyURI.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlAnyURI.type, options);
      }

      private Factory() {
      }
   }
}
