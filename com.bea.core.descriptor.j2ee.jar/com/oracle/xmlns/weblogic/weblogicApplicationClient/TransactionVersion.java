package com.oracle.xmlns.weblogic.weblogicApplicationClient;

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

public interface TransactionVersion extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionVersion.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("transactionversion7b00type");
   Enum DEFAULT = TransactionVersion.Enum.forString("DEFAULT");
   Enum WSAT_10 = TransactionVersion.Enum.forString("WSAT10");
   Enum WSAT_11 = TransactionVersion.Enum.forString("WSAT11");
   Enum WSAT_12 = TransactionVersion.Enum.forString("WSAT12");
   int INT_DEFAULT = 1;
   int INT_WSAT_10 = 2;
   int INT_WSAT_11 = 3;
   int INT_WSAT_12 = 4;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TransactionVersion newValue(Object obj) {
         return (TransactionVersion)TransactionVersion.type.newValue(obj);
      }

      public static TransactionVersion newInstance() {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().newInstance(TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion newInstance(XmlOptions options) {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().newInstance(TransactionVersion.type, options);
      }

      public static TransactionVersion parse(String xmlAsString) throws XmlException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransactionVersion.type, options);
      }

      public static TransactionVersion parse(File file) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(file, TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(file, TransactionVersion.type, options);
      }

      public static TransactionVersion parse(URL u) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(u, TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(u, TransactionVersion.type, options);
      }

      public static TransactionVersion parse(InputStream is) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(is, TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(is, TransactionVersion.type, options);
      }

      public static TransactionVersion parse(Reader r) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(r, TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(r, TransactionVersion.type, options);
      }

      public static TransactionVersion parse(XMLStreamReader sr) throws XmlException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(sr, TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(sr, TransactionVersion.type, options);
      }

      public static TransactionVersion parse(Node node) throws XmlException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(node, TransactionVersion.type, (XmlOptions)null);
      }

      public static TransactionVersion parse(Node node, XmlOptions options) throws XmlException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(node, TransactionVersion.type, options);
      }

      /** @deprecated */
      public static TransactionVersion parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(xis, TransactionVersion.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransactionVersion parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransactionVersion)XmlBeans.getContextTypeLoader().parse(xis, TransactionVersion.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionVersion.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransactionVersion.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_DEFAULT = 1;
      static final int INT_WSAT_10 = 2;
      static final int INT_WSAT_11 = 3;
      static final int INT_WSAT_12 = 4;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("DEFAULT", 1), new Enum("WSAT10", 2), new Enum("WSAT11", 3), new Enum("WSAT12", 4)});
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
