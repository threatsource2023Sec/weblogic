package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
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

public interface WeblogicCompatibilityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicCompatibilityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogiccompatibilitytypecb23type");

   TrueFalseType getEntityAlwaysUsesTransaction();

   boolean isSetEntityAlwaysUsesTransaction();

   void setEntityAlwaysUsesTransaction(TrueFalseType var1);

   TrueFalseType addNewEntityAlwaysUsesTransaction();

   void unsetEntityAlwaysUsesTransaction();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicCompatibilityType newInstance() {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType newInstance(XmlOptions options) {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().newInstance(WeblogicCompatibilityType.type, options);
      }

      public static WeblogicCompatibilityType parse(String xmlAsString) throws XmlException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicCompatibilityType.type, options);
      }

      public static WeblogicCompatibilityType parse(File file) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(file, WeblogicCompatibilityType.type, options);
      }

      public static WeblogicCompatibilityType parse(URL u) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(u, WeblogicCompatibilityType.type, options);
      }

      public static WeblogicCompatibilityType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(is, WeblogicCompatibilityType.type, options);
      }

      public static WeblogicCompatibilityType parse(Reader r) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(r, WeblogicCompatibilityType.type, options);
      }

      public static WeblogicCompatibilityType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicCompatibilityType.type, options);
      }

      public static WeblogicCompatibilityType parse(Node node) throws XmlException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      public static WeblogicCompatibilityType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(node, WeblogicCompatibilityType.type, options);
      }

      /** @deprecated */
      public static WeblogicCompatibilityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicCompatibilityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicCompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicCompatibilityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicCompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicCompatibilityType.type, options);
      }

      private Factory() {
      }
   }
}
