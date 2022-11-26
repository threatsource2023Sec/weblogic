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

public interface OverrunPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OverrunPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("overrunpolicytype0e6etype");
   Enum KEEP_OLD = OverrunPolicyType.Enum.forString("KeepOld");
   Enum KEEP_NEW = OverrunPolicyType.Enum.forString("KeepNew");
   int INT_KEEP_OLD = 1;
   int INT_KEEP_NEW = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static OverrunPolicyType newValue(Object obj) {
         return (OverrunPolicyType)OverrunPolicyType.type.newValue(obj);
      }

      public static OverrunPolicyType newInstance() {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().newInstance(OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType newInstance(XmlOptions options) {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().newInstance(OverrunPolicyType.type, options);
      }

      public static OverrunPolicyType parse(String xmlAsString) throws XmlException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OverrunPolicyType.type, options);
      }

      public static OverrunPolicyType parse(File file) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(file, OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(file, OverrunPolicyType.type, options);
      }

      public static OverrunPolicyType parse(URL u) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(u, OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(u, OverrunPolicyType.type, options);
      }

      public static OverrunPolicyType parse(InputStream is) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(is, OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(is, OverrunPolicyType.type, options);
      }

      public static OverrunPolicyType parse(Reader r) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(r, OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(r, OverrunPolicyType.type, options);
      }

      public static OverrunPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(sr, OverrunPolicyType.type, options);
      }

      public static OverrunPolicyType parse(Node node) throws XmlException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(node, OverrunPolicyType.type, (XmlOptions)null);
      }

      public static OverrunPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(node, OverrunPolicyType.type, options);
      }

      /** @deprecated */
      public static OverrunPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OverrunPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OverrunPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OverrunPolicyType)XmlBeans.getContextTypeLoader().parse(xis, OverrunPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OverrunPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OverrunPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_KEEP_OLD = 1;
      static final int INT_KEEP_NEW = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("KeepOld", 1), new Enum("KeepNew", 2)});
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
