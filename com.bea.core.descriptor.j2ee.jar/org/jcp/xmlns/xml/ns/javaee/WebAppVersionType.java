package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WebAppVersionType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebAppVersionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("webappversiontype0d2atype");
   Enum X_4_0 = WebAppVersionType.Enum.forString("4.0");
   int INT_X_4_0 = 1;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static WebAppVersionType newValue(Object obj) {
         return (WebAppVersionType)WebAppVersionType.type.newValue(obj);
      }

      public static WebAppVersionType newInstance() {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().newInstance(WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType newInstance(XmlOptions options) {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().newInstance(WebAppVersionType.type, options);
      }

      public static WebAppVersionType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebAppVersionType.type, options);
      }

      public static WebAppVersionType parse(File file) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(file, WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(file, WebAppVersionType.type, options);
      }

      public static WebAppVersionType parse(URL u) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(u, WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(u, WebAppVersionType.type, options);
      }

      public static WebAppVersionType parse(InputStream is) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(is, WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(is, WebAppVersionType.type, options);
      }

      public static WebAppVersionType parse(Reader r) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(r, WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(r, WebAppVersionType.type, options);
      }

      public static WebAppVersionType parse(XMLStreamReader sr) throws XmlException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(sr, WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(sr, WebAppVersionType.type, options);
      }

      public static WebAppVersionType parse(Node node) throws XmlException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(node, WebAppVersionType.type, (XmlOptions)null);
      }

      public static WebAppVersionType parse(Node node, XmlOptions options) throws XmlException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(node, WebAppVersionType.type, options);
      }

      /** @deprecated */
      public static WebAppVersionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(xis, WebAppVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebAppVersionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebAppVersionType)XmlBeans.getContextTypeLoader().parse(xis, WebAppVersionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebAppVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebAppVersionType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_X_4_0 = 1;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("4.0", 1)});
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
