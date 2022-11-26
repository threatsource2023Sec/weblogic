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

public interface MultiplicityType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MultiplicityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("multiplicitytype206ctype");
   Enum ONE = MultiplicityType.Enum.forString("One");
   Enum MANY = MultiplicityType.Enum.forString("Many");
   int INT_ONE = 1;
   int INT_MANY = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static MultiplicityType newInstance() {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().newInstance(MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType newInstance(XmlOptions options) {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().newInstance(MultiplicityType.type, options);
      }

      public static MultiplicityType parse(java.lang.String xmlAsString) throws XmlException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultiplicityType.type, options);
      }

      public static MultiplicityType parse(File file) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(file, MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(file, MultiplicityType.type, options);
      }

      public static MultiplicityType parse(URL u) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(u, MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(u, MultiplicityType.type, options);
      }

      public static MultiplicityType parse(InputStream is) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(is, MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(is, MultiplicityType.type, options);
      }

      public static MultiplicityType parse(Reader r) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(r, MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(r, MultiplicityType.type, options);
      }

      public static MultiplicityType parse(XMLStreamReader sr) throws XmlException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(sr, MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(sr, MultiplicityType.type, options);
      }

      public static MultiplicityType parse(Node node) throws XmlException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(node, MultiplicityType.type, (XmlOptions)null);
      }

      public static MultiplicityType parse(Node node, XmlOptions options) throws XmlException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(node, MultiplicityType.type, options);
      }

      /** @deprecated */
      public static MultiplicityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(xis, MultiplicityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MultiplicityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MultiplicityType)XmlBeans.getContextTypeLoader().parse(xis, MultiplicityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultiplicityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultiplicityType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ONE = 1;
      static final int INT_MANY = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("One", 1), new Enum("Many", 2)});
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
