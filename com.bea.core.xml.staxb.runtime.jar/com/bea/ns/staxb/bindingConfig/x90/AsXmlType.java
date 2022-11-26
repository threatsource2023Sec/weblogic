package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNMTOKEN;
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

public interface AsXmlType extends XmlSignature {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AsXmlType.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("asxmltype1156type");

   Whitespace.Enum getWhitespace();

   Whitespace xgetWhitespace();

   boolean isSetWhitespace();

   void setWhitespace(Whitespace.Enum var1);

   void xsetWhitespace(Whitespace var1);

   void unsetWhitespace();

   public static final class Factory {
      public static AsXmlType newInstance() {
         return (AsXmlType)XmlBeans.getContextTypeLoader().newInstance(AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType newInstance(XmlOptions options) {
         return (AsXmlType)XmlBeans.getContextTypeLoader().newInstance(AsXmlType.type, options);
      }

      public static AsXmlType parse(String xmlAsString) throws XmlException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AsXmlType.type, options);
      }

      public static AsXmlType parse(File file) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(file, AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(file, AsXmlType.type, options);
      }

      public static AsXmlType parse(URL u) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(u, AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(u, AsXmlType.type, options);
      }

      public static AsXmlType parse(InputStream is) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(is, AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(is, AsXmlType.type, options);
      }

      public static AsXmlType parse(Reader r) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(r, AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(r, AsXmlType.type, options);
      }

      public static AsXmlType parse(XMLStreamReader sr) throws XmlException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(sr, AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(sr, AsXmlType.type, options);
      }

      public static AsXmlType parse(Node node) throws XmlException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(node, AsXmlType.type, (XmlOptions)null);
      }

      public static AsXmlType parse(Node node, XmlOptions options) throws XmlException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(node, AsXmlType.type, options);
      }

      /** @deprecated */
      public static AsXmlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(xis, AsXmlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AsXmlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AsXmlType)XmlBeans.getContextTypeLoader().parse(xis, AsXmlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AsXmlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AsXmlType.type, options);
      }

      private Factory() {
      }
   }

   public interface Whitespace extends XmlNMTOKEN {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Whitespace.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("whitespaceeb73attrtype");
      Enum PRESERVE = AsXmlType.Whitespace.Enum.forString("preserve");
      Enum REPLACE = AsXmlType.Whitespace.Enum.forString("replace");
      Enum COLLAPSE = AsXmlType.Whitespace.Enum.forString("collapse");
      int INT_PRESERVE = 1;
      int INT_REPLACE = 2;
      int INT_COLLAPSE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Whitespace newValue(Object obj) {
            return (Whitespace)AsXmlType.Whitespace.type.newValue(obj);
         }

         public static Whitespace newInstance() {
            return (Whitespace)XmlBeans.getContextTypeLoader().newInstance(AsXmlType.Whitespace.type, (XmlOptions)null);
         }

         public static Whitespace newInstance(XmlOptions options) {
            return (Whitespace)XmlBeans.getContextTypeLoader().newInstance(AsXmlType.Whitespace.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_PRESERVE = 1;
         static final int INT_REPLACE = 2;
         static final int INT_COLLAPSE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("preserve", 1), new Enum("replace", 2), new Enum("collapse", 3)});
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
}
