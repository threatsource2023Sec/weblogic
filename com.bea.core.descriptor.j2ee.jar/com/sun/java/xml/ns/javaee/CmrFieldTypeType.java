package com.sun.java.xml.ns.javaee;

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

public interface CmrFieldTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CmrFieldTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cmrfieldtypetypeb83ftype");
   Enum JAVA_UTIL_COLLECTION = CmrFieldTypeType.Enum.forString("java.util.Collection");
   Enum JAVA_UTIL_SET = CmrFieldTypeType.Enum.forString("java.util.Set");
   int INT_JAVA_UTIL_COLLECTION = 1;
   int INT_JAVA_UTIL_SET = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static CmrFieldTypeType newInstance() {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().newInstance(CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType newInstance(XmlOptions options) {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().newInstance(CmrFieldTypeType.type, options);
      }

      public static CmrFieldTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmrFieldTypeType.type, options);
      }

      public static CmrFieldTypeType parse(File file) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(file, CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(file, CmrFieldTypeType.type, options);
      }

      public static CmrFieldTypeType parse(URL u) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(u, CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(u, CmrFieldTypeType.type, options);
      }

      public static CmrFieldTypeType parse(InputStream is) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(is, CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(is, CmrFieldTypeType.type, options);
      }

      public static CmrFieldTypeType parse(Reader r) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(r, CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(r, CmrFieldTypeType.type, options);
      }

      public static CmrFieldTypeType parse(XMLStreamReader sr) throws XmlException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(sr, CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(sr, CmrFieldTypeType.type, options);
      }

      public static CmrFieldTypeType parse(Node node) throws XmlException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(node, CmrFieldTypeType.type, (XmlOptions)null);
      }

      public static CmrFieldTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(node, CmrFieldTypeType.type, options);
      }

      /** @deprecated */
      public static CmrFieldTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(xis, CmrFieldTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CmrFieldTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CmrFieldTypeType)XmlBeans.getContextTypeLoader().parse(xis, CmrFieldTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmrFieldTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmrFieldTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_JAVA_UTIL_COLLECTION = 1;
      static final int INT_JAVA_UTIL_SET = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("java.util.Collection", 1), new Enum("java.util.Set", 2)});
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
