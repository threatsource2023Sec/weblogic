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

public interface CmpVersionType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CmpVersionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cmpversiontype934ctype");
   Enum X_1_X = CmpVersionType.Enum.forString("1.x");
   Enum X_2_X = CmpVersionType.Enum.forString("2.x");
   int INT_X_1_X = 1;
   int INT_X_2_X = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static CmpVersionType newInstance() {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().newInstance(CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType newInstance(XmlOptions options) {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().newInstance(CmpVersionType.type, options);
      }

      public static CmpVersionType parse(java.lang.String xmlAsString) throws XmlException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmpVersionType.type, options);
      }

      public static CmpVersionType parse(File file) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(file, CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(file, CmpVersionType.type, options);
      }

      public static CmpVersionType parse(URL u) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(u, CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(u, CmpVersionType.type, options);
      }

      public static CmpVersionType parse(InputStream is) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(is, CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(is, CmpVersionType.type, options);
      }

      public static CmpVersionType parse(Reader r) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(r, CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(r, CmpVersionType.type, options);
      }

      public static CmpVersionType parse(XMLStreamReader sr) throws XmlException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(sr, CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(sr, CmpVersionType.type, options);
      }

      public static CmpVersionType parse(Node node) throws XmlException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(node, CmpVersionType.type, (XmlOptions)null);
      }

      public static CmpVersionType parse(Node node, XmlOptions options) throws XmlException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(node, CmpVersionType.type, options);
      }

      /** @deprecated */
      public static CmpVersionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(xis, CmpVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CmpVersionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CmpVersionType)XmlBeans.getContextTypeLoader().parse(xis, CmpVersionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmpVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmpVersionType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_X_1_X = 1;
      static final int INT_X_2_X = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("1.x", 1), new Enum("2.x", 2)});
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
