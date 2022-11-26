package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
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

public interface HttpMethodType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HttpMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("httpmethodtype6727type");
   Enum GET = HttpMethodType.Enum.forString("GET");
   Enum POST = HttpMethodType.Enum.forString("POST");
   Enum PUT = HttpMethodType.Enum.forString("PUT");
   Enum DELETE = HttpMethodType.Enum.forString("DELETE");
   Enum HEAD = HttpMethodType.Enum.forString("HEAD");
   Enum OPTIONS = HttpMethodType.Enum.forString("OPTIONS");
   Enum TRACE = HttpMethodType.Enum.forString("TRACE");
   int INT_GET = 1;
   int INT_POST = 2;
   int INT_PUT = 3;
   int INT_DELETE = 4;
   int INT_HEAD = 5;
   int INT_OPTIONS = 6;
   int INT_TRACE = 7;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static HttpMethodType newInstance() {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().newInstance(HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType newInstance(XmlOptions options) {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().newInstance(HttpMethodType.type, options);
      }

      public static HttpMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(File file) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(file, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(file, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(URL u) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(u, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(u, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(InputStream is) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(is, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(is, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(Reader r) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(r, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(r, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(XMLStreamReader sr) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(sr, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(sr, HttpMethodType.type, options);
      }

      public static HttpMethodType parse(Node node) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(node, HttpMethodType.type, (XmlOptions)null);
      }

      public static HttpMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(node, HttpMethodType.type, options);
      }

      /** @deprecated */
      public static HttpMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xis, HttpMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HttpMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HttpMethodType)XmlBeans.getContextTypeLoader().parse(xis, HttpMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HttpMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HttpMethodType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_GET = 1;
      static final int INT_POST = 2;
      static final int INT_PUT = 3;
      static final int INT_DELETE = 4;
      static final int INT_HEAD = 5;
      static final int INT_OPTIONS = 6;
      static final int INT_TRACE = 7;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("GET", 1), new Enum("POST", 2), new Enum("PUT", 3), new Enum("DELETE", 4), new Enum("HEAD", 5), new Enum("OPTIONS", 6), new Enum("TRACE", 7)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(java.lang.String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(java.lang.String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
