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

public interface QnameScopeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QnameScopeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("qnamescopetype42a4type");
   Enum SIMPLE_TYPE = QnameScopeType.Enum.forString("simpleType");
   Enum COMPLEX_TYPE = QnameScopeType.Enum.forString("complexType");
   Enum ELEMENT = QnameScopeType.Enum.forString("element");
   int INT_SIMPLE_TYPE = 1;
   int INT_COMPLEX_TYPE = 2;
   int INT_ELEMENT = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static QnameScopeType newInstance() {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().newInstance(QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType newInstance(XmlOptions options) {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().newInstance(QnameScopeType.type, options);
      }

      public static QnameScopeType parse(java.lang.String xmlAsString) throws XmlException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QnameScopeType.type, options);
      }

      public static QnameScopeType parse(File file) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(file, QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(file, QnameScopeType.type, options);
      }

      public static QnameScopeType parse(URL u) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(u, QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(u, QnameScopeType.type, options);
      }

      public static QnameScopeType parse(InputStream is) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(is, QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(is, QnameScopeType.type, options);
      }

      public static QnameScopeType parse(Reader r) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(r, QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(r, QnameScopeType.type, options);
      }

      public static QnameScopeType parse(XMLStreamReader sr) throws XmlException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(sr, QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(sr, QnameScopeType.type, options);
      }

      public static QnameScopeType parse(Node node) throws XmlException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(node, QnameScopeType.type, (XmlOptions)null);
      }

      public static QnameScopeType parse(Node node, XmlOptions options) throws XmlException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(node, QnameScopeType.type, options);
      }

      /** @deprecated */
      public static QnameScopeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(xis, QnameScopeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QnameScopeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QnameScopeType)XmlBeans.getContextTypeLoader().parse(xis, QnameScopeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QnameScopeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QnameScopeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_SIMPLE_TYPE = 1;
      static final int INT_COMPLEX_TYPE = 2;
      static final int INT_ELEMENT = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("simpleType", 1), new Enum("complexType", 2), new Enum("element", 3)});
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
