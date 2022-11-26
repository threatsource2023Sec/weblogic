package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlIDREFS extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_IDREFS");

   List getListValue();

   List xgetListValue();

   void setListValue(List var1);

   /** @deprecated */
   List listValue();

   /** @deprecated */
   List xlistValue();

   /** @deprecated */
   void set(List var1);

   public static final class Factory {
      public static XmlIDREFS newInstance() {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().newInstance(XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS newInstance(XmlOptions options) {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().newInstance(XmlIDREFS.type, options);
      }

      public static XmlIDREFS newValue(Object obj) {
         return (XmlIDREFS)XmlIDREFS.type.newValue(obj);
      }

      public static XmlIDREFS parse(String s) throws XmlException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((String)s, XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS parse(String s, XmlOptions options) throws XmlException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(s, XmlIDREFS.type, options);
      }

      public static XmlIDREFS parse(File f) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((File)f, XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(f, XmlIDREFS.type, options);
      }

      public static XmlIDREFS parse(URL u) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((URL)u, XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(u, XmlIDREFS.type, options);
      }

      public static XmlIDREFS parse(InputStream is) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(is, XmlIDREFS.type, options);
      }

      public static XmlIDREFS parse(Reader r) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(r, XmlIDREFS.type, options);
      }

      public static XmlIDREFS parse(Node node) throws XmlException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((Node)node, XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS parse(Node node, XmlOptions options) throws XmlException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(node, XmlIDREFS.type, options);
      }

      /** @deprecated */
      public static XmlIDREFS parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlIDREFS.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlIDREFS parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(xis, XmlIDREFS.type, options);
      }

      public static XmlIDREFS parse(XMLStreamReader xsr) throws XmlException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlIDREFS.type, (XmlOptions)null);
      }

      public static XmlIDREFS parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlIDREFS)XmlBeans.getContextTypeLoader().parse(xsr, XmlIDREFS.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlIDREFS.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlIDREFS.type, options);
      }

      private Factory() {
      }
   }
}
