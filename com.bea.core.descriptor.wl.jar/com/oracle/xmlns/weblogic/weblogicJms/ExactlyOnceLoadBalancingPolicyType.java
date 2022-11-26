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

public interface ExactlyOnceLoadBalancingPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExactlyOnceLoadBalancingPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("exactlyonceloadbalancingpolicytype22f4type");
   Enum PER_MEMBER = ExactlyOnceLoadBalancingPolicyType.Enum.forString("Per-Member");
   Enum PER_JVM = ExactlyOnceLoadBalancingPolicyType.Enum.forString("Per-JVM");
   int INT_PER_MEMBER = 1;
   int INT_PER_JVM = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ExactlyOnceLoadBalancingPolicyType newValue(Object obj) {
         return (ExactlyOnceLoadBalancingPolicyType)ExactlyOnceLoadBalancingPolicyType.type.newValue(obj);
      }

      public static ExactlyOnceLoadBalancingPolicyType newInstance() {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().newInstance(ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType newInstance(XmlOptions options) {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().newInstance(ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(String xmlAsString) throws XmlException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(File file) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(file, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(file, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(URL u) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(u, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(u, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(InputStream is) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(is, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(is, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(Reader r) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(r, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(r, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(Node node) throws XmlException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(node, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ExactlyOnceLoadBalancingPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(node, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      /** @deprecated */
      public static ExactlyOnceLoadBalancingPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExactlyOnceLoadBalancingPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExactlyOnceLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExactlyOnceLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExactlyOnceLoadBalancingPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_PER_MEMBER = 1;
      static final int INT_PER_JVM = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Per-Member", 1), new Enum("Per-JVM", 2)});
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
