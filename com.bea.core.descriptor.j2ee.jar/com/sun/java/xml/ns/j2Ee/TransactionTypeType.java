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

public interface TransactionTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("transactiontypetype0e1ctype");
   Enum BEAN = TransactionTypeType.Enum.forString("Bean");
   Enum CONTAINER = TransactionTypeType.Enum.forString("Container");
   int INT_BEAN = 1;
   int INT_CONTAINER = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TransactionTypeType newInstance() {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().newInstance(TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType newInstance(XmlOptions options) {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().newInstance(TransactionTypeType.type, options);
      }

      public static TransactionTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionTypeType.type, options);
      }

      public static TransactionTypeType parse(File file) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(file, TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(file, TransactionTypeType.type, options);
      }

      public static TransactionTypeType parse(URL u) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(u, TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(u, TransactionTypeType.type, options);
      }

      public static TransactionTypeType parse(InputStream is) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(is, TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(is, TransactionTypeType.type, options);
      }

      public static TransactionTypeType parse(Reader r) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(r, TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(r, TransactionTypeType.type, options);
      }

      public static TransactionTypeType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(sr, TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(sr, TransactionTypeType.type, options);
      }

      public static TransactionTypeType parse(Node node) throws XmlException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(node, TransactionTypeType.type, (XmlOptions)null);
      }

      public static TransactionTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(node, TransactionTypeType.type, options);
      }

      /** @deprecated */
      public static TransactionTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(xis, TransactionTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionTypeType)XmlBeans.getContextTypeLoader().parse(xis, TransactionTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionTypeType.type, options);
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
