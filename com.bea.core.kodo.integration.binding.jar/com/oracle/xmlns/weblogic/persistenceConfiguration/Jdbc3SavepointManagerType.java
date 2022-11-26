package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface Jdbc3SavepointManagerType extends SavepointManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Jdbc3SavepointManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jdbc3savepointmanagertype078etype");

   boolean getRestoreFieldState();

   XmlBoolean xgetRestoreFieldState();

   boolean isSetRestoreFieldState();

   void setRestoreFieldState(boolean var1);

   void xsetRestoreFieldState(XmlBoolean var1);

   void unsetRestoreFieldState();

   public static final class Factory {
      public static Jdbc3SavepointManagerType newInstance() {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType newInstance(XmlOptions options) {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(Jdbc3SavepointManagerType.type, options);
      }

      public static Jdbc3SavepointManagerType parse(String xmlAsString) throws XmlException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Jdbc3SavepointManagerType.type, options);
      }

      public static Jdbc3SavepointManagerType parse(File file) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, Jdbc3SavepointManagerType.type, options);
      }

      public static Jdbc3SavepointManagerType parse(URL u) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, Jdbc3SavepointManagerType.type, options);
      }

      public static Jdbc3SavepointManagerType parse(InputStream is) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, Jdbc3SavepointManagerType.type, options);
      }

      public static Jdbc3SavepointManagerType parse(Reader r) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, Jdbc3SavepointManagerType.type, options);
      }

      public static Jdbc3SavepointManagerType parse(XMLStreamReader sr) throws XmlException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, Jdbc3SavepointManagerType.type, options);
      }

      public static Jdbc3SavepointManagerType parse(Node node) throws XmlException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      public static Jdbc3SavepointManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, Jdbc3SavepointManagerType.type, options);
      }

      /** @deprecated */
      public static Jdbc3SavepointManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Jdbc3SavepointManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Jdbc3SavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, Jdbc3SavepointManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Jdbc3SavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Jdbc3SavepointManagerType.type, options);
      }

      private Factory() {
      }
   }
}
