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

public interface EjbRefTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbRefTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("ejbreftypetypeb6d1type");
   Enum ENTITY = EjbRefTypeType.Enum.forString("Entity");
   Enum SESSION = EjbRefTypeType.Enum.forString("Session");
   int INT_ENTITY = 1;
   int INT_SESSION = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static EjbRefTypeType newInstance() {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().newInstance(EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType newInstance(XmlOptions options) {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().newInstance(EjbRefTypeType.type, options);
      }

      public static EjbRefTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRefTypeType.type, options);
      }

      public static EjbRefTypeType parse(File file) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(file, EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(file, EjbRefTypeType.type, options);
      }

      public static EjbRefTypeType parse(URL u) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(u, EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(u, EjbRefTypeType.type, options);
      }

      public static EjbRefTypeType parse(InputStream is) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(is, EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(is, EjbRefTypeType.type, options);
      }

      public static EjbRefTypeType parse(Reader r) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(r, EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(r, EjbRefTypeType.type, options);
      }

      public static EjbRefTypeType parse(XMLStreamReader sr) throws XmlException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(sr, EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(sr, EjbRefTypeType.type, options);
      }

      public static EjbRefTypeType parse(Node node) throws XmlException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(node, EjbRefTypeType.type, (XmlOptions)null);
      }

      public static EjbRefTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(node, EjbRefTypeType.type, options);
      }

      /** @deprecated */
      public static EjbRefTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(xis, EjbRefTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbRefTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbRefTypeType)XmlBeans.getContextTypeLoader().parse(xis, EjbRefTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRefTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRefTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ENTITY = 1;
      static final int INT_SESSION = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Entity", 1), new Enum("Session", 2)});
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
