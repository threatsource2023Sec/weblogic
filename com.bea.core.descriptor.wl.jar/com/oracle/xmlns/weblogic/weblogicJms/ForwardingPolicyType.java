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

public interface ForwardingPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ForwardingPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("forwardingpolicytypefbe6type");
   Enum PARTITIONED = ForwardingPolicyType.Enum.forString("Partitioned");
   Enum REPLICATED = ForwardingPolicyType.Enum.forString("Replicated");
   int INT_PARTITIONED = 1;
   int INT_REPLICATED = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ForwardingPolicyType newValue(Object obj) {
         return (ForwardingPolicyType)ForwardingPolicyType.type.newValue(obj);
      }

      public static ForwardingPolicyType newInstance() {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().newInstance(ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType newInstance(XmlOptions options) {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().newInstance(ForwardingPolicyType.type, options);
      }

      public static ForwardingPolicyType parse(String xmlAsString) throws XmlException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForwardingPolicyType.type, options);
      }

      public static ForwardingPolicyType parse(File file) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(file, ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(file, ForwardingPolicyType.type, options);
      }

      public static ForwardingPolicyType parse(URL u) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(u, ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(u, ForwardingPolicyType.type, options);
      }

      public static ForwardingPolicyType parse(InputStream is) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(is, ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(is, ForwardingPolicyType.type, options);
      }

      public static ForwardingPolicyType parse(Reader r) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(r, ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(r, ForwardingPolicyType.type, options);
      }

      public static ForwardingPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ForwardingPolicyType.type, options);
      }

      public static ForwardingPolicyType parse(Node node) throws XmlException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(node, ForwardingPolicyType.type, (XmlOptions)null);
      }

      public static ForwardingPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(node, ForwardingPolicyType.type, options);
      }

      /** @deprecated */
      public static ForwardingPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ForwardingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ForwardingPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ForwardingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ForwardingPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForwardingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForwardingPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_PARTITIONED = 1;
      static final int INT_REPLICATED = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Partitioned", 1), new Enum("Replicated", 2)});
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
