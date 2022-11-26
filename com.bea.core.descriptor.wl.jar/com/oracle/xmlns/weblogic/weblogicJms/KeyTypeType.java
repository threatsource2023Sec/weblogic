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

public interface KeyTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KeyTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("keytypetype284etype");
   Enum BOOLEAN = KeyTypeType.Enum.forString("Boolean");
   Enum BYTE = KeyTypeType.Enum.forString("Byte");
   Enum SHORT = KeyTypeType.Enum.forString("Short");
   Enum INT = KeyTypeType.Enum.forString("Int");
   Enum LONG = KeyTypeType.Enum.forString("Long");
   Enum FLOAT = KeyTypeType.Enum.forString("Float");
   Enum DOUBLE = KeyTypeType.Enum.forString("Double");
   Enum STRING = KeyTypeType.Enum.forString("String");
   int INT_BOOLEAN = 1;
   int INT_BYTE = 2;
   int INT_SHORT = 3;
   int INT_INT = 4;
   int INT_LONG = 5;
   int INT_FLOAT = 6;
   int INT_DOUBLE = 7;
   int INT_STRING = 8;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static KeyTypeType newValue(Object obj) {
         return (KeyTypeType)KeyTypeType.type.newValue(obj);
      }

      public static KeyTypeType newInstance() {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().newInstance(KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType newInstance(XmlOptions options) {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().newInstance(KeyTypeType.type, options);
      }

      public static KeyTypeType parse(String xmlAsString) throws XmlException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeyTypeType.type, options);
      }

      public static KeyTypeType parse(File file) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(file, KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(file, KeyTypeType.type, options);
      }

      public static KeyTypeType parse(URL u) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(u, KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(u, KeyTypeType.type, options);
      }

      public static KeyTypeType parse(InputStream is) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(is, KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(is, KeyTypeType.type, options);
      }

      public static KeyTypeType parse(Reader r) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(r, KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(r, KeyTypeType.type, options);
      }

      public static KeyTypeType parse(XMLStreamReader sr) throws XmlException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(sr, KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(sr, KeyTypeType.type, options);
      }

      public static KeyTypeType parse(Node node) throws XmlException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(node, KeyTypeType.type, (XmlOptions)null);
      }

      public static KeyTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(node, KeyTypeType.type, options);
      }

      /** @deprecated */
      public static KeyTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(xis, KeyTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KeyTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KeyTypeType)XmlBeans.getContextTypeLoader().parse(xis, KeyTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeyTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_BOOLEAN = 1;
      static final int INT_BYTE = 2;
      static final int INT_SHORT = 3;
      static final int INT_INT = 4;
      static final int INT_LONG = 5;
      static final int INT_FLOAT = 6;
      static final int INT_DOUBLE = 7;
      static final int INT_STRING = 8;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Boolean", 1), new Enum("Byte", 2), new Enum("Short", 3), new Enum("Int", 4), new Enum("Long", 5), new Enum("Float", 6), new Enum("Double", 7), new Enum("String", 8)});
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
