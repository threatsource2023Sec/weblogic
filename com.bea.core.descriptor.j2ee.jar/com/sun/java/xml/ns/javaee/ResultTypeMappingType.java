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

public interface ResultTypeMappingType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResultTypeMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("resulttypemappingtypef266type");
   Enum LOCAL = ResultTypeMappingType.Enum.forString("Local");
   Enum REMOTE = ResultTypeMappingType.Enum.forString("Remote");
   int INT_LOCAL = 1;
   int INT_REMOTE = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ResultTypeMappingType newInstance() {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().newInstance(ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType newInstance(XmlOptions options) {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().newInstance(ResultTypeMappingType.type, options);
      }

      public static ResultTypeMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResultTypeMappingType.type, options);
      }

      public static ResultTypeMappingType parse(File file) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(file, ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(file, ResultTypeMappingType.type, options);
      }

      public static ResultTypeMappingType parse(URL u) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(u, ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(u, ResultTypeMappingType.type, options);
      }

      public static ResultTypeMappingType parse(InputStream is) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(is, ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(is, ResultTypeMappingType.type, options);
      }

      public static ResultTypeMappingType parse(Reader r) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(r, ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(r, ResultTypeMappingType.type, options);
      }

      public static ResultTypeMappingType parse(XMLStreamReader sr) throws XmlException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(sr, ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(sr, ResultTypeMappingType.type, options);
      }

      public static ResultTypeMappingType parse(Node node) throws XmlException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(node, ResultTypeMappingType.type, (XmlOptions)null);
      }

      public static ResultTypeMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(node, ResultTypeMappingType.type, options);
      }

      /** @deprecated */
      public static ResultTypeMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(xis, ResultTypeMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResultTypeMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResultTypeMappingType)XmlBeans.getContextTypeLoader().parse(xis, ResultTypeMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResultTypeMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResultTypeMappingType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_LOCAL = 1;
      static final int INT_REMOTE = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Local", 1), new Enum("Remote", 2)});
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
