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

public interface ConcurrencyManagementTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConcurrencyManagementTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("concurrencymanagementtypetype1b85type");
   Enum BEAN = ConcurrencyManagementTypeType.Enum.forString("Bean");
   Enum CONTAINER = ConcurrencyManagementTypeType.Enum.forString("Container");
   int INT_BEAN = 1;
   int INT_CONTAINER = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ConcurrencyManagementTypeType newInstance() {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().newInstance(ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType newInstance(XmlOptions options) {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().newInstance(ConcurrencyManagementTypeType.type, options);
      }

      public static ConcurrencyManagementTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrencyManagementTypeType.type, options);
      }

      public static ConcurrencyManagementTypeType parse(File file) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(file, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(file, ConcurrencyManagementTypeType.type, options);
      }

      public static ConcurrencyManagementTypeType parse(URL u) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(u, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(u, ConcurrencyManagementTypeType.type, options);
      }

      public static ConcurrencyManagementTypeType parse(InputStream is) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(is, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(is, ConcurrencyManagementTypeType.type, options);
      }

      public static ConcurrencyManagementTypeType parse(Reader r) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(r, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(r, ConcurrencyManagementTypeType.type, options);
      }

      public static ConcurrencyManagementTypeType parse(XMLStreamReader sr) throws XmlException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrencyManagementTypeType.type, options);
      }

      public static ConcurrencyManagementTypeType parse(Node node) throws XmlException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(node, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      public static ConcurrencyManagementTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(node, ConcurrencyManagementTypeType.type, options);
      }

      /** @deprecated */
      public static ConcurrencyManagementTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConcurrencyManagementTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConcurrencyManagementTypeType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrencyManagementTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrencyManagementTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrencyManagementTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_BEAN = 1;
      static final int INT_CONTAINER = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Bean", 1), new Enum("Container", 2)});
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
