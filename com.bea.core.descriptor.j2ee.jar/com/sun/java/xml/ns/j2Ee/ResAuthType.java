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

public interface ResAuthType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResAuthType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("resauthtype3308type");
   Enum APPLICATION = ResAuthType.Enum.forString("Application");
   Enum CONTAINER = ResAuthType.Enum.forString("Container");
   int INT_APPLICATION = 1;
   int INT_CONTAINER = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ResAuthType newInstance() {
         return (ResAuthType)XmlBeans.getContextTypeLoader().newInstance(ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType newInstance(XmlOptions options) {
         return (ResAuthType)XmlBeans.getContextTypeLoader().newInstance(ResAuthType.type, options);
      }

      public static ResAuthType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResAuthType.type, options);
      }

      public static ResAuthType parse(File file) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(file, ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(file, ResAuthType.type, options);
      }

      public static ResAuthType parse(URL u) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(u, ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(u, ResAuthType.type, options);
      }

      public static ResAuthType parse(InputStream is) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(is, ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(is, ResAuthType.type, options);
      }

      public static ResAuthType parse(Reader r) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(r, ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(r, ResAuthType.type, options);
      }

      public static ResAuthType parse(XMLStreamReader sr) throws XmlException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(sr, ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(sr, ResAuthType.type, options);
      }

      public static ResAuthType parse(Node node) throws XmlException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(node, ResAuthType.type, (XmlOptions)null);
      }

      public static ResAuthType parse(Node node, XmlOptions options) throws XmlException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(node, ResAuthType.type, options);
      }

      /** @deprecated */
      public static ResAuthType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(xis, ResAuthType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResAuthType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResAuthType)XmlBeans.getContextTypeLoader().parse(xis, ResAuthType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResAuthType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResAuthType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_APPLICATION = 1;
      static final int INT_CONTAINER = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Application", 1), new Enum("Container", 2)});
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
