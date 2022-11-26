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

public interface SecurityPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("securitypolicytype1ca9type");
   Enum THREAD_BASED = SecurityPolicyType.Enum.forString("ThreadBased");
   Enum OBJECT_BASED_DELEGATED = SecurityPolicyType.Enum.forString("ObjectBasedDelegated");
   Enum OBJECT_BASED_ANONYMOUS = SecurityPolicyType.Enum.forString("ObjectBasedAnonymous");
   Enum OBJECT_BASED_THREAD = SecurityPolicyType.Enum.forString("ObjectBasedThread");
   int INT_THREAD_BASED = 1;
   int INT_OBJECT_BASED_DELEGATED = 2;
   int INT_OBJECT_BASED_ANONYMOUS = 3;
   int INT_OBJECT_BASED_THREAD = 4;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static SecurityPolicyType newValue(Object obj) {
         return (SecurityPolicyType)SecurityPolicyType.type.newValue(obj);
      }

      public static SecurityPolicyType newInstance() {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().newInstance(SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType newInstance(XmlOptions options) {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().newInstance(SecurityPolicyType.type, options);
      }

      public static SecurityPolicyType parse(String xmlAsString) throws XmlException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPolicyType.type, options);
      }

      public static SecurityPolicyType parse(File file) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(file, SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(file, SecurityPolicyType.type, options);
      }

      public static SecurityPolicyType parse(URL u) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(u, SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(u, SecurityPolicyType.type, options);
      }

      public static SecurityPolicyType parse(InputStream is) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(is, SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(is, SecurityPolicyType.type, options);
      }

      public static SecurityPolicyType parse(Reader r) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(r, SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(r, SecurityPolicyType.type, options);
      }

      public static SecurityPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPolicyType.type, options);
      }

      public static SecurityPolicyType parse(Node node) throws XmlException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(node, SecurityPolicyType.type, (XmlOptions)null);
      }

      public static SecurityPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(node, SecurityPolicyType.type, options);
      }

      /** @deprecated */
      public static SecurityPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityPolicyType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_THREAD_BASED = 1;
      static final int INT_OBJECT_BASED_DELEGATED = 2;
      static final int INT_OBJECT_BASED_ANONYMOUS = 3;
      static final int INT_OBJECT_BASED_THREAD = 4;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("ThreadBased", 1), new Enum("ObjectBasedDelegated", 2), new Enum("ObjectBasedAnonymous", 3), new Enum("ObjectBasedThread", 4)});
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
