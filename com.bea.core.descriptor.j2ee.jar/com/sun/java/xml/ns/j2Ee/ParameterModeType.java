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

public interface ParameterModeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ParameterModeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("parametermodetype9ddatype");
   Enum IN = ParameterModeType.Enum.forString("IN");
   Enum OUT = ParameterModeType.Enum.forString("OUT");
   Enum INOUT = ParameterModeType.Enum.forString("INOUT");
   int INT_IN = 1;
   int INT_OUT = 2;
   int INT_INOUT = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ParameterModeType newInstance() {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().newInstance(ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType newInstance(XmlOptions options) {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().newInstance(ParameterModeType.type, options);
      }

      public static ParameterModeType parse(java.lang.String xmlAsString) throws XmlException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParameterModeType.type, options);
      }

      public static ParameterModeType parse(File file) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(file, ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(file, ParameterModeType.type, options);
      }

      public static ParameterModeType parse(URL u) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(u, ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(u, ParameterModeType.type, options);
      }

      public static ParameterModeType parse(InputStream is) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(is, ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(is, ParameterModeType.type, options);
      }

      public static ParameterModeType parse(Reader r) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(r, ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(r, ParameterModeType.type, options);
      }

      public static ParameterModeType parse(XMLStreamReader sr) throws XmlException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(sr, ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(sr, ParameterModeType.type, options);
      }

      public static ParameterModeType parse(Node node) throws XmlException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(node, ParameterModeType.type, (XmlOptions)null);
      }

      public static ParameterModeType parse(Node node, XmlOptions options) throws XmlException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(node, ParameterModeType.type, options);
      }

      /** @deprecated */
      public static ParameterModeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(xis, ParameterModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ParameterModeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ParameterModeType)XmlBeans.getContextTypeLoader().parse(xis, ParameterModeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParameterModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParameterModeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_IN = 1;
      static final int INT_OUT = 2;
      static final int INT_INOUT = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("IN", 1), new Enum("OUT", 2), new Enum("INOUT", 3)});
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
