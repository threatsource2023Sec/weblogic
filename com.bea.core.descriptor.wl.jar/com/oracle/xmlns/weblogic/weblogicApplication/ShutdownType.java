package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface ShutdownType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ShutdownType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("shutdowntype7b31type");

   String getShutdownClass();

   XmlString xgetShutdownClass();

   void setShutdownClass(String var1);

   void xsetShutdownClass(XmlString var1);

   String getShutdownUri();

   XmlString xgetShutdownUri();

   boolean isSetShutdownUri();

   void setShutdownUri(String var1);

   void xsetShutdownUri(XmlString var1);

   void unsetShutdownUri();

   public static final class Factory {
      public static ShutdownType newInstance() {
         return (ShutdownType)XmlBeans.getContextTypeLoader().newInstance(ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType newInstance(XmlOptions options) {
         return (ShutdownType)XmlBeans.getContextTypeLoader().newInstance(ShutdownType.type, options);
      }

      public static ShutdownType parse(String xmlAsString) throws XmlException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ShutdownType.type, options);
      }

      public static ShutdownType parse(File file) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(file, ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(file, ShutdownType.type, options);
      }

      public static ShutdownType parse(URL u) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(u, ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(u, ShutdownType.type, options);
      }

      public static ShutdownType parse(InputStream is) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(is, ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(is, ShutdownType.type, options);
      }

      public static ShutdownType parse(Reader r) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(r, ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(r, ShutdownType.type, options);
      }

      public static ShutdownType parse(XMLStreamReader sr) throws XmlException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(sr, ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(sr, ShutdownType.type, options);
      }

      public static ShutdownType parse(Node node) throws XmlException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(node, ShutdownType.type, (XmlOptions)null);
      }

      public static ShutdownType parse(Node node, XmlOptions options) throws XmlException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(node, ShutdownType.type, options);
      }

      /** @deprecated */
      public static ShutdownType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(xis, ShutdownType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ShutdownType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ShutdownType)XmlBeans.getContextTypeLoader().parse(xis, ShutdownType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ShutdownType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ShutdownType.type, options);
      }

      private Factory() {
      }
   }
}
