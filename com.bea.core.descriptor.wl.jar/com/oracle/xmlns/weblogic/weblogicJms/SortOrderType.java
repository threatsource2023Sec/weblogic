package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface SortOrderType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SortOrderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("sortordertype1cb7type");
   Enum ASCENDING = SortOrderType.Enum.forString("Ascending");
   Enum DESCENDING = SortOrderType.Enum.forString("Descending");
   int INT_ASCENDING = 1;
   int INT_DESCENDING = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static SortOrderType newValue(Object obj) {
         return (SortOrderType)SortOrderType.type.newValue(obj);
      }

      public static SortOrderType newInstance() {
         return (SortOrderType)XmlBeans.getContextTypeLoader().newInstance(SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType newInstance(XmlOptions options) {
         return (SortOrderType)XmlBeans.getContextTypeLoader().newInstance(SortOrderType.type, options);
      }

      public static SortOrderType parse(String xmlAsString) throws XmlException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SortOrderType.type, options);
      }

      public static SortOrderType parse(File file) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(file, SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(file, SortOrderType.type, options);
      }

      public static SortOrderType parse(URL u) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(u, SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(u, SortOrderType.type, options);
      }

      public static SortOrderType parse(InputStream is) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(is, SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(is, SortOrderType.type, options);
      }

      public static SortOrderType parse(Reader r) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(r, SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(r, SortOrderType.type, options);
      }

      public static SortOrderType parse(XMLStreamReader sr) throws XmlException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(sr, SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(sr, SortOrderType.type, options);
      }

      public static SortOrderType parse(Node node) throws XmlException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(node, SortOrderType.type, (XmlOptions)null);
      }

      public static SortOrderType parse(Node node, XmlOptions options) throws XmlException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(node, SortOrderType.type, options);
      }

      /** @deprecated */
      public static SortOrderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(xis, SortOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SortOrderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SortOrderType)XmlBeans.getContextTypeLoader().parse(xis, SortOrderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SortOrderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SortOrderType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ASCENDING = 1;
      static final int INT_DESCENDING = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Ascending", 1), new Enum("Descending", 2)});
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
