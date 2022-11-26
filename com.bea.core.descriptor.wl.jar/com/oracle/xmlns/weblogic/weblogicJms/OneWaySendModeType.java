package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface OneWaySendModeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OneWaySendModeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("onewaysendmodetype4005type");
   Enum ENABLED = OneWaySendModeType.Enum.forString("enabled");
   Enum DISABLED = OneWaySendModeType.Enum.forString("disabled");
   Enum TOPIC_ONLY = OneWaySendModeType.Enum.forString("topicOnly");
   int INT_ENABLED = 1;
   int INT_DISABLED = 2;
   int INT_TOPIC_ONLY = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static OneWaySendModeType newValue(Object obj) {
         return (OneWaySendModeType)OneWaySendModeType.type.newValue(obj);
      }

      public static OneWaySendModeType newInstance() {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().newInstance(OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType newInstance(XmlOptions options) {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().newInstance(OneWaySendModeType.type, options);
      }

      public static OneWaySendModeType parse(String xmlAsString) throws XmlException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OneWaySendModeType.type, options);
      }

      public static OneWaySendModeType parse(File file) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(file, OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(file, OneWaySendModeType.type, options);
      }

      public static OneWaySendModeType parse(URL u) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(u, OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(u, OneWaySendModeType.type, options);
      }

      public static OneWaySendModeType parse(InputStream is) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(is, OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(is, OneWaySendModeType.type, options);
      }

      public static OneWaySendModeType parse(Reader r) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(r, OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(r, OneWaySendModeType.type, options);
      }

      public static OneWaySendModeType parse(XMLStreamReader sr) throws XmlException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(sr, OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(sr, OneWaySendModeType.type, options);
      }

      public static OneWaySendModeType parse(Node node) throws XmlException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(node, OneWaySendModeType.type, (XmlOptions)null);
      }

      public static OneWaySendModeType parse(Node node, XmlOptions options) throws XmlException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(node, OneWaySendModeType.type, options);
      }

      /** @deprecated */
      public static OneWaySendModeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(xis, OneWaySendModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OneWaySendModeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OneWaySendModeType)XmlBeans.getContextTypeLoader().parse(xis, OneWaySendModeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OneWaySendModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OneWaySendModeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ENABLED = 1;
      static final int INT_DISABLED = 2;
      static final int INT_TOPIC_ONLY = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("enabled", 1), new Enum("disabled", 2), new Enum("topicOnly", 3)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
