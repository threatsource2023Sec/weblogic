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

public interface SessionTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SessionTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("sessiontypetypec9e4type");
   Enum STATEFUL = SessionTypeType.Enum.forString("Stateful");
   Enum STATELESS = SessionTypeType.Enum.forString("Stateless");
   int INT_STATEFUL = 1;
   int INT_STATELESS = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static SessionTypeType newInstance() {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().newInstance(SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType newInstance(XmlOptions options) {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().newInstance(SessionTypeType.type, options);
      }

      public static SessionTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SessionTypeType.type, options);
      }

      public static SessionTypeType parse(File file) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(file, SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(file, SessionTypeType.type, options);
      }

      public static SessionTypeType parse(URL u) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(u, SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(u, SessionTypeType.type, options);
      }

      public static SessionTypeType parse(InputStream is) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(is, SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(is, SessionTypeType.type, options);
      }

      public static SessionTypeType parse(Reader r) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(r, SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(r, SessionTypeType.type, options);
      }

      public static SessionTypeType parse(XMLStreamReader sr) throws XmlException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(sr, SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(sr, SessionTypeType.type, options);
      }

      public static SessionTypeType parse(Node node) throws XmlException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(node, SessionTypeType.type, (XmlOptions)null);
      }

      public static SessionTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(node, SessionTypeType.type, options);
      }

      /** @deprecated */
      public static SessionTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(xis, SessionTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SessionTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SessionTypeType)XmlBeans.getContextTypeLoader().parse(xis, SessionTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SessionTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_STATEFUL = 1;
      static final int INT_STATELESS = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Stateful", 1), new Enum("Stateless", 2)});
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
