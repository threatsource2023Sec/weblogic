package org.jcp.xmlns.xml.ns.javaee;

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

public interface ConfigPropertyTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigPropertyTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("configpropertytypetype0844type");
   Enum JAVA_LANG_BOOLEAN = ConfigPropertyTypeType.Enum.forString("java.lang.Boolean");
   Enum JAVA_LANG_STRING = ConfigPropertyTypeType.Enum.forString("java.lang.String");
   Enum JAVA_LANG_INTEGER = ConfigPropertyTypeType.Enum.forString("java.lang.Integer");
   Enum JAVA_LANG_DOUBLE = ConfigPropertyTypeType.Enum.forString("java.lang.Double");
   Enum JAVA_LANG_BYTE = ConfigPropertyTypeType.Enum.forString("java.lang.Byte");
   Enum JAVA_LANG_SHORT = ConfigPropertyTypeType.Enum.forString("java.lang.Short");
   Enum JAVA_LANG_LONG = ConfigPropertyTypeType.Enum.forString("java.lang.Long");
   Enum JAVA_LANG_FLOAT = ConfigPropertyTypeType.Enum.forString("java.lang.Float");
   Enum JAVA_LANG_CHARACTER = ConfigPropertyTypeType.Enum.forString("java.lang.Character");
   int INT_JAVA_LANG_BOOLEAN = 1;
   int INT_JAVA_LANG_STRING = 2;
   int INT_JAVA_LANG_INTEGER = 3;
   int INT_JAVA_LANG_DOUBLE = 4;
   int INT_JAVA_LANG_BYTE = 5;
   int INT_JAVA_LANG_SHORT = 6;
   int INT_JAVA_LANG_LONG = 7;
   int INT_JAVA_LANG_FLOAT = 8;
   int INT_JAVA_LANG_CHARACTER = 9;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ConfigPropertyTypeType newInstance() {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType newInstance(XmlOptions options) {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyTypeType.type, options);
      }

      public static ConfigPropertyTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyTypeType.type, options);
      }

      public static ConfigPropertyTypeType parse(File file) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyTypeType.type, options);
      }

      public static ConfigPropertyTypeType parse(URL u) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyTypeType.type, options);
      }

      public static ConfigPropertyTypeType parse(InputStream is) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyTypeType.type, options);
      }

      public static ConfigPropertyTypeType parse(Reader r) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyTypeType.type, options);
      }

      public static ConfigPropertyTypeType parse(XMLStreamReader sr) throws XmlException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyTypeType.type, options);
      }

      public static ConfigPropertyTypeType parse(Node node) throws XmlException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      public static ConfigPropertyTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyTypeType.type, options);
      }

      /** @deprecated */
      public static ConfigPropertyTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigPropertyTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigPropertyTypeType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_JAVA_LANG_BOOLEAN = 1;
      static final int INT_JAVA_LANG_STRING = 2;
      static final int INT_JAVA_LANG_INTEGER = 3;
      static final int INT_JAVA_LANG_DOUBLE = 4;
      static final int INT_JAVA_LANG_BYTE = 5;
      static final int INT_JAVA_LANG_SHORT = 6;
      static final int INT_JAVA_LANG_LONG = 7;
      static final int INT_JAVA_LANG_FLOAT = 8;
      static final int INT_JAVA_LANG_CHARACTER = 9;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("java.lang.Boolean", 1), new Enum("java.lang.String", 2), new Enum("java.lang.Integer", 3), new Enum("java.lang.Double", 4), new Enum("java.lang.Byte", 5), new Enum("java.lang.Short", 6), new Enum("java.lang.Long", 7), new Enum("java.lang.Float", 8), new Enum("java.lang.Character", 9)});
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
