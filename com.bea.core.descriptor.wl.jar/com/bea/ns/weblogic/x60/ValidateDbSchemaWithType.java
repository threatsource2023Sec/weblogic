package com.bea.ns.weblogic.x60;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ValidateDbSchemaWithType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ValidateDbSchemaWithType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("validatedbschemawithtype4c27type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ValidateDbSchemaWithType newInstance() {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().newInstance(ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType newInstance(XmlOptions options) {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().newInstance(ValidateDbSchemaWithType.type, options);
      }

      public static ValidateDbSchemaWithType parse(String xmlAsString) throws XmlException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ValidateDbSchemaWithType.type, options);
      }

      public static ValidateDbSchemaWithType parse(File file) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(file, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(file, ValidateDbSchemaWithType.type, options);
      }

      public static ValidateDbSchemaWithType parse(URL u) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(u, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(u, ValidateDbSchemaWithType.type, options);
      }

      public static ValidateDbSchemaWithType parse(InputStream is) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(is, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(is, ValidateDbSchemaWithType.type, options);
      }

      public static ValidateDbSchemaWithType parse(Reader r) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(r, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(r, ValidateDbSchemaWithType.type, options);
      }

      public static ValidateDbSchemaWithType parse(XMLStreamReader sr) throws XmlException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(sr, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(sr, ValidateDbSchemaWithType.type, options);
      }

      public static ValidateDbSchemaWithType parse(Node node) throws XmlException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(node, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      public static ValidateDbSchemaWithType parse(Node node, XmlOptions options) throws XmlException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(node, ValidateDbSchemaWithType.type, options);
      }

      /** @deprecated */
      public static ValidateDbSchemaWithType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(xis, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ValidateDbSchemaWithType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ValidateDbSchemaWithType)XmlBeans.getContextTypeLoader().parse(xis, ValidateDbSchemaWithType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ValidateDbSchemaWithType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ValidateDbSchemaWithType.type, options);
      }

      private Factory() {
      }
   }
}
