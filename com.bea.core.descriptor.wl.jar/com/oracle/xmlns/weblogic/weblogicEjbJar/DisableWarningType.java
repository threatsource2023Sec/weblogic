package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface DisableWarningType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DisableWarningType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("disablewarningtype123btype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DisableWarningType newInstance() {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().newInstance(DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType newInstance(XmlOptions options) {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().newInstance(DisableWarningType.type, options);
      }

      public static DisableWarningType parse(String xmlAsString) throws XmlException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DisableWarningType.type, options);
      }

      public static DisableWarningType parse(File file) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(file, DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(file, DisableWarningType.type, options);
      }

      public static DisableWarningType parse(URL u) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(u, DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(u, DisableWarningType.type, options);
      }

      public static DisableWarningType parse(InputStream is) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(is, DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(is, DisableWarningType.type, options);
      }

      public static DisableWarningType parse(Reader r) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(r, DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(r, DisableWarningType.type, options);
      }

      public static DisableWarningType parse(XMLStreamReader sr) throws XmlException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(sr, DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(sr, DisableWarningType.type, options);
      }

      public static DisableWarningType parse(Node node) throws XmlException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(node, DisableWarningType.type, (XmlOptions)null);
      }

      public static DisableWarningType parse(Node node, XmlOptions options) throws XmlException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(node, DisableWarningType.type, options);
      }

      /** @deprecated */
      public static DisableWarningType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(xis, DisableWarningType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DisableWarningType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DisableWarningType)XmlBeans.getContextTypeLoader().parse(xis, DisableWarningType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DisableWarningType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DisableWarningType.type, options);
      }

      private Factory() {
      }
   }
}
