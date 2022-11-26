package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WebserviceDescriptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebserviceDescriptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("webservicedescriptiontypea00dtype");

   String getWebserviceDescriptionName();

   void setWebserviceDescriptionName(String var1);

   String addNewWebserviceDescriptionName();

   WebserviceType.Enum getWebserviceType();

   WebserviceType xgetWebserviceType();

   boolean isSetWebserviceType();

   void setWebserviceType(WebserviceType.Enum var1);

   void xsetWebserviceType(WebserviceType var1);

   void unsetWebserviceType();

   String getWsdlPublishFile();

   boolean isSetWsdlPublishFile();

   void setWsdlPublishFile(String var1);

   String addNewWsdlPublishFile();

   void unsetWsdlPublishFile();

   PortComponentType[] getPortComponentArray();

   PortComponentType getPortComponentArray(int var1);

   int sizeOfPortComponentArray();

   void setPortComponentArray(PortComponentType[] var1);

   void setPortComponentArray(int var1, PortComponentType var2);

   PortComponentType insertNewPortComponent(int var1);

   PortComponentType addNewPortComponent();

   void removePortComponent(int var1);

   public static final class Factory {
      public static WebserviceDescriptionType newInstance() {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType newInstance(XmlOptions options) {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(java.lang.String xmlAsString) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(File file) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(URL u) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(InputStream is) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(Reader r) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(XMLStreamReader sr) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, WebserviceDescriptionType.type, options);
      }

      public static WebserviceDescriptionType parse(Node node) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      public static WebserviceDescriptionType parse(Node node, XmlOptions options) throws XmlException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, WebserviceDescriptionType.type, options);
      }

      /** @deprecated */
      public static WebserviceDescriptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WebserviceDescriptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WebserviceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, WebserviceDescriptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WebserviceDescriptionType.type, options);
      }

      private Factory() {
      }
   }

   public interface WebserviceType extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WebserviceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("webservicetype2dbbelemtype");
      Enum JAXRPC = WebserviceDescriptionType.WebserviceType.Enum.forString("JAXRPC");
      Enum JAXWS = WebserviceDescriptionType.WebserviceType.Enum.forString("JAXWS");
      int INT_JAXRPC = 1;
      int INT_JAXWS = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static WebserviceType newValue(Object obj) {
            return (WebserviceType)WebserviceDescriptionType.WebserviceType.type.newValue(obj);
         }

         public static WebserviceType newInstance() {
            return (WebserviceType)XmlBeans.getContextTypeLoader().newInstance(WebserviceDescriptionType.WebserviceType.type, (XmlOptions)null);
         }

         public static WebserviceType newInstance(XmlOptions options) {
            return (WebserviceType)XmlBeans.getContextTypeLoader().newInstance(WebserviceDescriptionType.WebserviceType.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_JAXRPC = 1;
         static final int INT_JAXWS = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("JAXRPC", 1), new Enum("JAXWS", 2)});
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
}
