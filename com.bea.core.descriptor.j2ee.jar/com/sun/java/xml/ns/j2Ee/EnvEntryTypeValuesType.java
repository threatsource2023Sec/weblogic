package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface EnvEntryTypeValuesType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnvEntryTypeValuesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("enventrytypevaluestype43fbtype");
   Enum JAVA_LANG_BOOLEAN = EnvEntryTypeValuesType.Enum.forString("java.lang.Boolean");
   Enum JAVA_LANG_BYTE = EnvEntryTypeValuesType.Enum.forString("java.lang.Byte");
   Enum JAVA_LANG_CHARACTER = EnvEntryTypeValuesType.Enum.forString("java.lang.Character");
   Enum JAVA_LANG_STRING = EnvEntryTypeValuesType.Enum.forString("java.lang.String");
   Enum JAVA_LANG_SHORT = EnvEntryTypeValuesType.Enum.forString("java.lang.Short");
   Enum JAVA_LANG_INTEGER = EnvEntryTypeValuesType.Enum.forString("java.lang.Integer");
   Enum JAVA_LANG_LONG = EnvEntryTypeValuesType.Enum.forString("java.lang.Long");
   Enum JAVA_LANG_FLOAT = EnvEntryTypeValuesType.Enum.forString("java.lang.Float");
   Enum JAVA_LANG_DOUBLE = EnvEntryTypeValuesType.Enum.forString("java.lang.Double");
   int INT_JAVA_LANG_BOOLEAN = 1;
   int INT_JAVA_LANG_BYTE = 2;
   int INT_JAVA_LANG_CHARACTER = 3;
   int INT_JAVA_LANG_STRING = 4;
   int INT_JAVA_LANG_SHORT = 5;
   int INT_JAVA_LANG_INTEGER = 6;
   int INT_JAVA_LANG_LONG = 7;
   int INT_JAVA_LANG_FLOAT = 8;
   int INT_JAVA_LANG_DOUBLE = 9;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static EnvEntryTypeValuesType newInstance() {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().newInstance(EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType newInstance(XmlOptions options) {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().newInstance(EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(java.lang.String xmlAsString) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(File file) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(file, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(file, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(URL u) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(u, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(u, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(InputStream is) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(is, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(is, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(Reader r) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(r, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(r, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(XMLStreamReader sr) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(sr, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(sr, EnvEntryTypeValuesType.type, options);
      }

      public static EnvEntryTypeValuesType parse(Node node) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(node, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      public static EnvEntryTypeValuesType parse(Node node, XmlOptions options) throws XmlException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(node, EnvEntryTypeValuesType.type, options);
      }

      /** @deprecated */
      public static EnvEntryTypeValuesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xis, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnvEntryTypeValuesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnvEntryTypeValuesType)XmlBeans.getContextTypeLoader().parse(xis, EnvEntryTypeValuesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnvEntryTypeValuesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnvEntryTypeValuesType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_JAVA_LANG_BOOLEAN = 1;
      static final int INT_JAVA_LANG_BYTE = 2;
      static final int INT_JAVA_LANG_CHARACTER = 3;
      static final int INT_JAVA_LANG_STRING = 4;
      static final int INT_JAVA_LANG_SHORT = 5;
      static final int INT_JAVA_LANG_INTEGER = 6;
      static final int INT_JAVA_LANG_LONG = 7;
      static final int INT_JAVA_LANG_FLOAT = 8;
      static final int INT_JAVA_LANG_DOUBLE = 9;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("java.lang.Boolean", 1), new Enum("java.lang.Byte", 2), new Enum("java.lang.Character", 3), new Enum("java.lang.String", 4), new Enum("java.lang.Short", 5), new Enum("java.lang.Integer", 6), new Enum("java.lang.Long", 7), new Enum("java.lang.Float", 8), new Enum("java.lang.Double", 9)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(java.lang.String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(java.lang.String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
