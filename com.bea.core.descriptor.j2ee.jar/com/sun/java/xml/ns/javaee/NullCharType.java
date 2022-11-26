package com.sun.java.xml.ns.javaee;

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

public interface NullCharType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NullCharType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("nullchartype0557type");
   Enum X = NullCharType.Enum.forString("");
   int INT_X = 1;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static NullCharType newValue(Object obj) {
         return (NullCharType)NullCharType.type.newValue(obj);
      }

      public static NullCharType newInstance() {
         return (NullCharType)XmlBeans.getContextTypeLoader().newInstance(NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType newInstance(XmlOptions options) {
         return (NullCharType)XmlBeans.getContextTypeLoader().newInstance(NullCharType.type, options);
      }

      public static NullCharType parse(java.lang.String xmlAsString) throws XmlException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NullCharType.type, options);
      }

      public static NullCharType parse(File file) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(file, NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(file, NullCharType.type, options);
      }

      public static NullCharType parse(URL u) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(u, NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(u, NullCharType.type, options);
      }

      public static NullCharType parse(InputStream is) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(is, NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(is, NullCharType.type, options);
      }

      public static NullCharType parse(Reader r) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(r, NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(r, NullCharType.type, options);
      }

      public static NullCharType parse(XMLStreamReader sr) throws XmlException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(sr, NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(sr, NullCharType.type, options);
      }

      public static NullCharType parse(Node node) throws XmlException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(node, NullCharType.type, (XmlOptions)null);
      }

      public static NullCharType parse(Node node, XmlOptions options) throws XmlException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(node, NullCharType.type, options);
      }

      /** @deprecated */
      public static NullCharType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(xis, NullCharType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NullCharType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NullCharType)XmlBeans.getContextTypeLoader().parse(xis, NullCharType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NullCharType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NullCharType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_X = 1;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("", 1)});
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
