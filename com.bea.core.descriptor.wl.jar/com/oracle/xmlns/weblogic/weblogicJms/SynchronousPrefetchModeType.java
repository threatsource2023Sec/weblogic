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

public interface SynchronousPrefetchModeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SynchronousPrefetchModeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("synchronousprefetchmodetypea5d3type");
   Enum ENABLED = SynchronousPrefetchModeType.Enum.forString("enabled");
   Enum DISABLED = SynchronousPrefetchModeType.Enum.forString("disabled");
   Enum TOPIC_SUBSCRIBER_ONLY = SynchronousPrefetchModeType.Enum.forString("topicSubscriberOnly");
   int INT_ENABLED = 1;
   int INT_DISABLED = 2;
   int INT_TOPIC_SUBSCRIBER_ONLY = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static SynchronousPrefetchModeType newValue(Object obj) {
         return (SynchronousPrefetchModeType)SynchronousPrefetchModeType.type.newValue(obj);
      }

      public static SynchronousPrefetchModeType newInstance() {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().newInstance(SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType newInstance(XmlOptions options) {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().newInstance(SynchronousPrefetchModeType.type, options);
      }

      public static SynchronousPrefetchModeType parse(String xmlAsString) throws XmlException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SynchronousPrefetchModeType.type, options);
      }

      public static SynchronousPrefetchModeType parse(File file) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(file, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(file, SynchronousPrefetchModeType.type, options);
      }

      public static SynchronousPrefetchModeType parse(URL u) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(u, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(u, SynchronousPrefetchModeType.type, options);
      }

      public static SynchronousPrefetchModeType parse(InputStream is) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(is, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(is, SynchronousPrefetchModeType.type, options);
      }

      public static SynchronousPrefetchModeType parse(Reader r) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(r, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(r, SynchronousPrefetchModeType.type, options);
      }

      public static SynchronousPrefetchModeType parse(XMLStreamReader sr) throws XmlException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(sr, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(sr, SynchronousPrefetchModeType.type, options);
      }

      public static SynchronousPrefetchModeType parse(Node node) throws XmlException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(node, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      public static SynchronousPrefetchModeType parse(Node node, XmlOptions options) throws XmlException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(node, SynchronousPrefetchModeType.type, options);
      }

      /** @deprecated */
      public static SynchronousPrefetchModeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(xis, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SynchronousPrefetchModeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SynchronousPrefetchModeType)XmlBeans.getContextTypeLoader().parse(xis, SynchronousPrefetchModeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SynchronousPrefetchModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SynchronousPrefetchModeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ENABLED = 1;
      static final int INT_DISABLED = 2;
      static final int INT_TOPIC_SUBSCRIBER_ONLY = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("enabled", 1), new Enum("disabled", 2), new Enum("topicSubscriberOnly", 3)});
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
