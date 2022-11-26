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

public interface ReconnectPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ReconnectPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("reconnectpolicytype92cetype");
   Enum NONE = ReconnectPolicyType.Enum.forString("none");
   Enum PRODUCER = ReconnectPolicyType.Enum.forString("producer");
   Enum ALL = ReconnectPolicyType.Enum.forString("all");
   int INT_NONE = 1;
   int INT_PRODUCER = 2;
   int INT_ALL = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ReconnectPolicyType newValue(Object obj) {
         return (ReconnectPolicyType)ReconnectPolicyType.type.newValue(obj);
      }

      public static ReconnectPolicyType newInstance() {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().newInstance(ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType newInstance(XmlOptions options) {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().newInstance(ReconnectPolicyType.type, options);
      }

      public static ReconnectPolicyType parse(String xmlAsString) throws XmlException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReconnectPolicyType.type, options);
      }

      public static ReconnectPolicyType parse(File file) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(file, ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(file, ReconnectPolicyType.type, options);
      }

      public static ReconnectPolicyType parse(URL u) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(u, ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(u, ReconnectPolicyType.type, options);
      }

      public static ReconnectPolicyType parse(InputStream is) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(is, ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(is, ReconnectPolicyType.type, options);
      }

      public static ReconnectPolicyType parse(Reader r) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(r, ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(r, ReconnectPolicyType.type, options);
      }

      public static ReconnectPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ReconnectPolicyType.type, options);
      }

      public static ReconnectPolicyType parse(Node node) throws XmlException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(node, ReconnectPolicyType.type, (XmlOptions)null);
      }

      public static ReconnectPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(node, ReconnectPolicyType.type, options);
      }

      /** @deprecated */
      public static ReconnectPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ReconnectPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ReconnectPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ReconnectPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ReconnectPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReconnectPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReconnectPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_NONE = 1;
      static final int INT_PRODUCER = 2;
      static final int INT_ALL = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("none", 1), new Enum("producer", 2), new Enum("all", 3)});
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
