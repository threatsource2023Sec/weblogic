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

public interface PersistenceTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("persistencetypetype8b7btype");
   Enum BEAN = PersistenceTypeType.Enum.forString("Bean");
   Enum CONTAINER = PersistenceTypeType.Enum.forString("Container");
   int INT_BEAN = 1;
   int INT_CONTAINER = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static PersistenceTypeType newInstance() {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().newInstance(PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType newInstance(XmlOptions options) {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().newInstance(PersistenceTypeType.type, options);
      }

      public static PersistenceTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceTypeType.type, options);
      }

      public static PersistenceTypeType parse(File file) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(file, PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(file, PersistenceTypeType.type, options);
      }

      public static PersistenceTypeType parse(URL u) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(u, PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(u, PersistenceTypeType.type, options);
      }

      public static PersistenceTypeType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(is, PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(is, PersistenceTypeType.type, options);
      }

      public static PersistenceTypeType parse(Reader r) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(r, PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(r, PersistenceTypeType.type, options);
      }

      public static PersistenceTypeType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceTypeType.type, options);
      }

      public static PersistenceTypeType parse(Node node) throws XmlException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(node, PersistenceTypeType.type, (XmlOptions)null);
      }

      public static PersistenceTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(node, PersistenceTypeType.type, options);
      }

      /** @deprecated */
      public static PersistenceTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceTypeType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceTypeType.type, options);
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
