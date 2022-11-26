package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface RefererValidationType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RefererValidationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("referervalidationtype35cctype");
   Enum NONE = RefererValidationType.Enum.forString("NONE");
   Enum LENIENT = RefererValidationType.Enum.forString("LENIENT");
   Enum STRICT = RefererValidationType.Enum.forString("STRICT");
   int INT_NONE = 1;
   int INT_LENIENT = 2;
   int INT_STRICT = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static RefererValidationType newValue(Object obj) {
         return (RefererValidationType)RefererValidationType.type.newValue(obj);
      }

      public static RefererValidationType newInstance() {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().newInstance(RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType newInstance(XmlOptions options) {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().newInstance(RefererValidationType.type, options);
      }

      public static RefererValidationType parse(String xmlAsString) throws XmlException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RefererValidationType.type, options);
      }

      public static RefererValidationType parse(File file) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(file, RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(file, RefererValidationType.type, options);
      }

      public static RefererValidationType parse(URL u) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(u, RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(u, RefererValidationType.type, options);
      }

      public static RefererValidationType parse(InputStream is) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(is, RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(is, RefererValidationType.type, options);
      }

      public static RefererValidationType parse(Reader r) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(r, RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(r, RefererValidationType.type, options);
      }

      public static RefererValidationType parse(XMLStreamReader sr) throws XmlException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(sr, RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(sr, RefererValidationType.type, options);
      }

      public static RefererValidationType parse(Node node) throws XmlException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(node, RefererValidationType.type, (XmlOptions)null);
      }

      public static RefererValidationType parse(Node node, XmlOptions options) throws XmlException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(node, RefererValidationType.type, options);
      }

      /** @deprecated */
      public static RefererValidationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(xis, RefererValidationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RefererValidationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RefererValidationType)XmlBeans.getContextTypeLoader().parse(xis, RefererValidationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RefererValidationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RefererValidationType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_NONE = 1;
      static final int INT_LENIENT = 2;
      static final int INT_STRICT = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("NONE", 1), new Enum("LENIENT", 2), new Enum("STRICT", 3)});
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
