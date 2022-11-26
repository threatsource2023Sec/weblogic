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

public interface SubscriptionSharingPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SubscriptionSharingPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("subscriptionsharingpolicytype3115type");
   Enum EXCLUSIVE = SubscriptionSharingPolicyType.Enum.forString("Exclusive");
   Enum SHARABLE = SubscriptionSharingPolicyType.Enum.forString("Sharable");
   int INT_EXCLUSIVE = 1;
   int INT_SHARABLE = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static SubscriptionSharingPolicyType newValue(Object obj) {
         return (SubscriptionSharingPolicyType)SubscriptionSharingPolicyType.type.newValue(obj);
      }

      public static SubscriptionSharingPolicyType newInstance() {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().newInstance(SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType newInstance(XmlOptions options) {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().newInstance(SubscriptionSharingPolicyType.type, options);
      }

      public static SubscriptionSharingPolicyType parse(String xmlAsString) throws XmlException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SubscriptionSharingPolicyType.type, options);
      }

      public static SubscriptionSharingPolicyType parse(File file) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(file, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(file, SubscriptionSharingPolicyType.type, options);
      }

      public static SubscriptionSharingPolicyType parse(URL u) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(u, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(u, SubscriptionSharingPolicyType.type, options);
      }

      public static SubscriptionSharingPolicyType parse(InputStream is) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(is, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(is, SubscriptionSharingPolicyType.type, options);
      }

      public static SubscriptionSharingPolicyType parse(Reader r) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(r, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(r, SubscriptionSharingPolicyType.type, options);
      }

      public static SubscriptionSharingPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(sr, SubscriptionSharingPolicyType.type, options);
      }

      public static SubscriptionSharingPolicyType parse(Node node) throws XmlException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(node, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      public static SubscriptionSharingPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(node, SubscriptionSharingPolicyType.type, options);
      }

      /** @deprecated */
      public static SubscriptionSharingPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SubscriptionSharingPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SubscriptionSharingPolicyType)XmlBeans.getContextTypeLoader().parse(xis, SubscriptionSharingPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SubscriptionSharingPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SubscriptionSharingPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_EXCLUSIVE = 1;
      static final int INT_SHARABLE = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Exclusive", 1), new Enum("Sharable", 2)});
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
