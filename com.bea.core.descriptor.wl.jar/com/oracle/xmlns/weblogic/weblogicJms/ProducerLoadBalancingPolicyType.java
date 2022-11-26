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

public interface ProducerLoadBalancingPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProducerLoadBalancingPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("producerloadbalancingpolicytype17c4type");
   Enum PER_MEMBER = ProducerLoadBalancingPolicyType.Enum.forString("Per-Member");
   Enum PER_JVM = ProducerLoadBalancingPolicyType.Enum.forString("Per-JVM");
   int INT_PER_MEMBER = 1;
   int INT_PER_JVM = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ProducerLoadBalancingPolicyType newValue(Object obj) {
         return (ProducerLoadBalancingPolicyType)ProducerLoadBalancingPolicyType.type.newValue(obj);
      }

      public static ProducerLoadBalancingPolicyType newInstance() {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().newInstance(ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType newInstance(XmlOptions options) {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().newInstance(ProducerLoadBalancingPolicyType.type, options);
      }

      public static ProducerLoadBalancingPolicyType parse(String xmlAsString) throws XmlException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProducerLoadBalancingPolicyType.type, options);
      }

      public static ProducerLoadBalancingPolicyType parse(File file) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(file, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(file, ProducerLoadBalancingPolicyType.type, options);
      }

      public static ProducerLoadBalancingPolicyType parse(URL u) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(u, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(u, ProducerLoadBalancingPolicyType.type, options);
      }

      public static ProducerLoadBalancingPolicyType parse(InputStream is) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(is, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(is, ProducerLoadBalancingPolicyType.type, options);
      }

      public static ProducerLoadBalancingPolicyType parse(Reader r) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(r, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(r, ProducerLoadBalancingPolicyType.type, options);
      }

      public static ProducerLoadBalancingPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ProducerLoadBalancingPolicyType.type, options);
      }

      public static ProducerLoadBalancingPolicyType parse(Node node) throws XmlException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(node, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      public static ProducerLoadBalancingPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(node, ProducerLoadBalancingPolicyType.type, options);
      }

      /** @deprecated */
      public static ProducerLoadBalancingPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProducerLoadBalancingPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProducerLoadBalancingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ProducerLoadBalancingPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProducerLoadBalancingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProducerLoadBalancingPolicyType.type, options);
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
