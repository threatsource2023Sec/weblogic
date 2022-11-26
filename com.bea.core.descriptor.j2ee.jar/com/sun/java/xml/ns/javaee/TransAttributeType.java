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

public interface TransAttributeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransAttributeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("transattributetyped5b2type");
   Enum NOT_SUPPORTED = TransAttributeType.Enum.forString("NotSupported");
   Enum SUPPORTS = TransAttributeType.Enum.forString("Supports");
   Enum REQUIRED = TransAttributeType.Enum.forString("Required");
   Enum REQUIRES_NEW = TransAttributeType.Enum.forString("RequiresNew");
   Enum MANDATORY = TransAttributeType.Enum.forString("Mandatory");
   Enum NEVER = TransAttributeType.Enum.forString("Never");
   int INT_NOT_SUPPORTED = 1;
   int INT_SUPPORTS = 2;
   int INT_REQUIRED = 3;
   int INT_REQUIRES_NEW = 4;
   int INT_MANDATORY = 5;
   int INT_NEVER = 6;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TransAttributeType newInstance() {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().newInstance(TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType newInstance(XmlOptions options) {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().newInstance(TransAttributeType.type, options);
      }

      public static TransAttributeType parse(java.lang.String xmlAsString) throws XmlException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransAttributeType.type, options);
      }

      public static TransAttributeType parse(File file) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(file, TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(file, TransAttributeType.type, options);
      }

      public static TransAttributeType parse(URL u) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(u, TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(u, TransAttributeType.type, options);
      }

      public static TransAttributeType parse(InputStream is) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(is, TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(is, TransAttributeType.type, options);
      }

      public static TransAttributeType parse(Reader r) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(r, TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(r, TransAttributeType.type, options);
      }

      public static TransAttributeType parse(XMLStreamReader sr) throws XmlException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(sr, TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(sr, TransAttributeType.type, options);
      }

      public static TransAttributeType parse(Node node) throws XmlException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(node, TransAttributeType.type, (XmlOptions)null);
      }

      public static TransAttributeType parse(Node node, XmlOptions options) throws XmlException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(node, TransAttributeType.type, options);
      }

      /** @deprecated */
      public static TransAttributeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(xis, TransAttributeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransAttributeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransAttributeType)XmlBeans.getContextTypeLoader().parse(xis, TransAttributeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransAttributeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransAttributeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_NOT_SUPPORTED = 1;
      static final int INT_SUPPORTS = 2;
      static final int INT_REQUIRED = 3;
      static final int INT_REQUIRES_NEW = 4;
      static final int INT_MANDATORY = 5;
      static final int INT_NEVER = 6;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("NotSupported", 1), new Enum("Supports", 2), new Enum("Required", 3), new Enum("RequiresNew", 4), new Enum("Mandatory", 5), new Enum("Never", 6)});
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
