package com.sun.java.xml.ns.javaee;

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

public interface IsolationLevelType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IsolationLevelType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("isolationleveltype6940type");
   Enum TRANSACTION_READ_UNCOMMITTED = IsolationLevelType.Enum.forString("TRANSACTION_READ_UNCOMMITTED");
   Enum TRANSACTION_READ_COMMITTED = IsolationLevelType.Enum.forString("TRANSACTION_READ_COMMITTED");
   Enum TRANSACTION_REPEATABLE_READ = IsolationLevelType.Enum.forString("TRANSACTION_REPEATABLE_READ");
   Enum TRANSACTION_SERIALIZABLE = IsolationLevelType.Enum.forString("TRANSACTION_SERIALIZABLE");
   int INT_TRANSACTION_READ_UNCOMMITTED = 1;
   int INT_TRANSACTION_READ_COMMITTED = 2;
   int INT_TRANSACTION_REPEATABLE_READ = 3;
   int INT_TRANSACTION_SERIALIZABLE = 4;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static IsolationLevelType newValue(Object obj) {
         return (IsolationLevelType)IsolationLevelType.type.newValue(obj);
      }

      public static IsolationLevelType newInstance() {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().newInstance(IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType newInstance(XmlOptions options) {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().newInstance(IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(java.lang.String xmlAsString) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(File file) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(file, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(file, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(URL u) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(u, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(u, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(InputStream is) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(is, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(is, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(Reader r) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(r, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(r, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(XMLStreamReader sr) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(sr, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(sr, IsolationLevelType.type, options);
      }

      public static IsolationLevelType parse(Node node) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(node, IsolationLevelType.type, (XmlOptions)null);
      }

      public static IsolationLevelType parse(Node node, XmlOptions options) throws XmlException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(node, IsolationLevelType.type, options);
      }

      /** @deprecated */
      public static IsolationLevelType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xis, IsolationLevelType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IsolationLevelType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IsolationLevelType)XmlBeans.getContextTypeLoader().parse(xis, IsolationLevelType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IsolationLevelType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IsolationLevelType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_TRANSACTION_READ_UNCOMMITTED = 1;
      static final int INT_TRANSACTION_READ_COMMITTED = 2;
      static final int INT_TRANSACTION_REPEATABLE_READ = 3;
      static final int INT_TRANSACTION_SERIALIZABLE = 4;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("TRANSACTION_READ_UNCOMMITTED", 1), new Enum("TRANSACTION_READ_COMMITTED", 2), new Enum("TRANSACTION_REPEATABLE_READ", 3), new Enum("TRANSACTION_SERIALIZABLE", 4)});
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
