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

public interface TransactionSupportType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionSupportType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("transactionsupporttypea59btype");
   Enum NO_TRANSACTION = TransactionSupportType.Enum.forString("NoTransaction");
   Enum LOCAL_TRANSACTION = TransactionSupportType.Enum.forString("LocalTransaction");
   Enum XA_TRANSACTION = TransactionSupportType.Enum.forString("XATransaction");
   int INT_NO_TRANSACTION = 1;
   int INT_LOCAL_TRANSACTION = 2;
   int INT_XA_TRANSACTION = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TransactionSupportType newInstance() {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().newInstance(TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType newInstance(XmlOptions options) {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().newInstance(TransactionSupportType.type, options);
      }

      public static TransactionSupportType parse(java.lang.String xmlAsString) throws XmlException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionSupportType.type, options);
      }

      public static TransactionSupportType parse(File file) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(file, TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(file, TransactionSupportType.type, options);
      }

      public static TransactionSupportType parse(URL u) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(u, TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(u, TransactionSupportType.type, options);
      }

      public static TransactionSupportType parse(InputStream is) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(is, TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(is, TransactionSupportType.type, options);
      }

      public static TransactionSupportType parse(Reader r) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(r, TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(r, TransactionSupportType.type, options);
      }

      public static TransactionSupportType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(sr, TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(sr, TransactionSupportType.type, options);
      }

      public static TransactionSupportType parse(Node node) throws XmlException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(node, TransactionSupportType.type, (XmlOptions)null);
      }

      public static TransactionSupportType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(node, TransactionSupportType.type, options);
      }

      /** @deprecated */
      public static TransactionSupportType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(xis, TransactionSupportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionSupportType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionSupportType)XmlBeans.getContextTypeLoader().parse(xis, TransactionSupportType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionSupportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionSupportType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_NO_TRANSACTION = 1;
      static final int INT_LOCAL_TRANSACTION = 2;
      static final int INT_XA_TRANSACTION = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("NoTransaction", 1), new Enum("LocalTransaction", 2), new Enum("XATransaction", 3)});
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
