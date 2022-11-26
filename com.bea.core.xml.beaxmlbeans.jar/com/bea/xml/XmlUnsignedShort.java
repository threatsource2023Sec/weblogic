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

public interface XmlUnsignedShort extends XmlUnsignedInt {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_unsignedShort");

   int getIntValue();

   void setIntValue(int var1);

   /** @deprecated */
   int intValue();

   /** @deprecated */
   void set(int var1);

   public static final class Factory {
      public static XmlUnsignedShort newInstance() {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort newInstance(XmlOptions options) {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().newInstance(XmlUnsignedShort.type, options);
      }

      public static XmlUnsignedShort newValue(Object obj) {
         return (XmlUnsignedShort)XmlUnsignedShort.type.newValue(obj);
      }

      public static XmlUnsignedShort parse(String s) throws XmlException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((String)s, XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort parse(String s, XmlOptions options) throws XmlException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(s, XmlUnsignedShort.type, options);
      }

      public static XmlUnsignedShort parse(File f) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((File)f, XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(f, XmlUnsignedShort.type, options);
      }

      public static XmlUnsignedShort parse(URL u) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((URL)u, XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(u, XmlUnsignedShort.type, options);
      }

      public static XmlUnsignedShort parse(InputStream is) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(is, XmlUnsignedShort.type, options);
      }

      public static XmlUnsignedShort parse(Reader r) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(r, XmlUnsignedShort.type, options);
      }

      public static XmlUnsignedShort parse(Node node) throws XmlException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((Node)node, XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort parse(Node node, XmlOptions options) throws XmlException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(node, XmlUnsignedShort.type, options);
      }

      /** @deprecated */
      public static XmlUnsignedShort parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlUnsignedShort.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlUnsignedShort parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(xis, XmlUnsignedShort.type, options);
      }

      public static XmlUnsignedShort parse(XMLStreamReader xsr) throws XmlException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlUnsignedShort.type, (XmlOptions)null);
      }

      public static XmlUnsignedShort parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlUnsignedShort)XmlBeans.getContextTypeLoader().parse(xsr, XmlUnsignedShort.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedShort.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlUnsignedShort.type, options);
      }

      private Factory() {
      }
   }
}
