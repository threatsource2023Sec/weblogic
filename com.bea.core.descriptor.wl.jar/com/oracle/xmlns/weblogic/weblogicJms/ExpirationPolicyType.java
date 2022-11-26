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

public interface ExpirationPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExpirationPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("expirationpolicytype9118type");
   Enum DISCARD = ExpirationPolicyType.Enum.forString("Discard");
   Enum LOG = ExpirationPolicyType.Enum.forString("Log");
   Enum REDIRECT = ExpirationPolicyType.Enum.forString("Redirect");
   int INT_DISCARD = 1;
   int INT_LOG = 2;
   int INT_REDIRECT = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ExpirationPolicyType newValue(Object obj) {
         return (ExpirationPolicyType)ExpirationPolicyType.type.newValue(obj);
      }

      public static ExpirationPolicyType newInstance() {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().newInstance(ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType newInstance(XmlOptions options) {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().newInstance(ExpirationPolicyType.type, options);
      }

      public static ExpirationPolicyType parse(String xmlAsString) throws XmlException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExpirationPolicyType.type, options);
      }

      public static ExpirationPolicyType parse(File file) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(file, ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(file, ExpirationPolicyType.type, options);
      }

      public static ExpirationPolicyType parse(URL u) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(u, ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(u, ExpirationPolicyType.type, options);
      }

      public static ExpirationPolicyType parse(InputStream is) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(is, ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(is, ExpirationPolicyType.type, options);
      }

      public static ExpirationPolicyType parse(Reader r) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(r, ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(r, ExpirationPolicyType.type, options);
      }

      public static ExpirationPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ExpirationPolicyType.type, options);
      }

      public static ExpirationPolicyType parse(Node node) throws XmlException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(node, ExpirationPolicyType.type, (XmlOptions)null);
      }

      public static ExpirationPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(node, ExpirationPolicyType.type, options);
      }

      /** @deprecated */
      public static ExpirationPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ExpirationPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExpirationPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExpirationPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ExpirationPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExpirationPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExpirationPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_DISCARD = 1;
      static final int INT_LOG = 2;
      static final int INT_REDIRECT = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Discard", 1), new Enum("Log", 2), new Enum("Redirect", 3)});
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
