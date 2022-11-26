package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CallbackProtocolType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CallbackProtocolType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("callbackprotocoltypebbabtype");
   Enum HTTP = CallbackProtocolType.Enum.forString("http");
   Enum HTTPS = CallbackProtocolType.Enum.forString("https");
   Enum JMS = CallbackProtocolType.Enum.forString("jms");
   int INT_HTTP = 1;
   int INT_HTTPS = 2;
   int INT_JMS = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static CallbackProtocolType newInstance() {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().newInstance(CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType newInstance(XmlOptions options) {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().newInstance(CallbackProtocolType.type, options);
      }

      public static CallbackProtocolType parse(java.lang.String xmlAsString) throws XmlException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CallbackProtocolType.type, options);
      }

      public static CallbackProtocolType parse(File file) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(file, CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(file, CallbackProtocolType.type, options);
      }

      public static CallbackProtocolType parse(URL u) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(u, CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(u, CallbackProtocolType.type, options);
      }

      public static CallbackProtocolType parse(InputStream is) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(is, CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(is, CallbackProtocolType.type, options);
      }

      public static CallbackProtocolType parse(Reader r) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(r, CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(r, CallbackProtocolType.type, options);
      }

      public static CallbackProtocolType parse(XMLStreamReader sr) throws XmlException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(sr, CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(sr, CallbackProtocolType.type, options);
      }

      public static CallbackProtocolType parse(Node node) throws XmlException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(node, CallbackProtocolType.type, (XmlOptions)null);
      }

      public static CallbackProtocolType parse(Node node, XmlOptions options) throws XmlException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(node, CallbackProtocolType.type, options);
      }

      /** @deprecated */
      public static CallbackProtocolType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(xis, CallbackProtocolType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CallbackProtocolType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CallbackProtocolType)XmlBeans.getContextTypeLoader().parse(xis, CallbackProtocolType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CallbackProtocolType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CallbackProtocolType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_HTTP = 1;
      static final int INT_HTTPS = 2;
      static final int INT_JMS = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("http", 1), new Enum("https", 2), new Enum("jms", 3)});
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
