package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface TransactionFlowType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionFlowType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("transactionflowtypede15type");
   Enum SUPPORTS = TransactionFlowType.Enum.forString("SUPPORTS");
   Enum MANDATORY = TransactionFlowType.Enum.forString("MANDATORY");
   Enum NEVER = TransactionFlowType.Enum.forString("NEVER");
   int INT_SUPPORTS = 1;
   int INT_MANDATORY = 2;
   int INT_NEVER = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TransactionFlowType newValue(Object obj) {
         return (TransactionFlowType)TransactionFlowType.type.newValue(obj);
      }

      public static TransactionFlowType newInstance() {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().newInstance(TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType newInstance(XmlOptions options) {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().newInstance(TransactionFlowType.type, options);
      }

      public static TransactionFlowType parse(String xmlAsString) throws XmlException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionFlowType.type, options);
      }

      public static TransactionFlowType parse(File file) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(file, TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(file, TransactionFlowType.type, options);
      }

      public static TransactionFlowType parse(URL u) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(u, TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(u, TransactionFlowType.type, options);
      }

      public static TransactionFlowType parse(InputStream is) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(is, TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(is, TransactionFlowType.type, options);
      }

      public static TransactionFlowType parse(Reader r) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(r, TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(r, TransactionFlowType.type, options);
      }

      public static TransactionFlowType parse(XMLStreamReader sr) throws XmlException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(sr, TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(sr, TransactionFlowType.type, options);
      }

      public static TransactionFlowType parse(Node node) throws XmlException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(node, TransactionFlowType.type, (XmlOptions)null);
      }

      public static TransactionFlowType parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(node, TransactionFlowType.type, options);
      }

      /** @deprecated */
      public static TransactionFlowType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(xis, TransactionFlowType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionFlowType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionFlowType)XmlBeans.getContextTypeLoader().parse(xis, TransactionFlowType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionFlowType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionFlowType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_SUPPORTS = 1;
      static final int INT_MANDATORY = 2;
      static final int INT_NEVER = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("SUPPORTS", 1), new Enum("MANDATORY", 2), new Enum("NEVER", 3)});
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
