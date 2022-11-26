package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface DescriptionType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DescriptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("descriptiontypec5bdtype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DescriptionType newInstance() {
         return (DescriptionType)XmlBeans.getContextTypeLoader().newInstance(DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType newInstance(XmlOptions options) {
         return (DescriptionType)XmlBeans.getContextTypeLoader().newInstance(DescriptionType.type, options);
      }

      public static DescriptionType parse(String xmlAsString) throws XmlException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DescriptionType.type, options);
      }

      public static DescriptionType parse(File file) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(file, DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(file, DescriptionType.type, options);
      }

      public static DescriptionType parse(URL u) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(u, DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(u, DescriptionType.type, options);
      }

      public static DescriptionType parse(InputStream is) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(is, DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(is, DescriptionType.type, options);
      }

      public static DescriptionType parse(Reader r) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(r, DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(r, DescriptionType.type, options);
      }

      public static DescriptionType parse(XMLStreamReader sr) throws XmlException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(sr, DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(sr, DescriptionType.type, options);
      }

      public static DescriptionType parse(Node node) throws XmlException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(node, DescriptionType.type, (XmlOptions)null);
      }

      public static DescriptionType parse(Node node, XmlOptions options) throws XmlException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(node, DescriptionType.type, options);
      }

      /** @deprecated */
      public static DescriptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(xis, DescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DescriptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DescriptionType)XmlBeans.getContextTypeLoader().parse(xis, DescriptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DescriptionType.type, options);
      }

      private Factory() {
      }
   }
}
