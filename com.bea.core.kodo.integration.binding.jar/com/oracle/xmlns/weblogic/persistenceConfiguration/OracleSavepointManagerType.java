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

public interface OracleSavepointManagerType extends SavepointManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OracleSavepointManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("oraclesavepointmanagertype8936type");

   boolean getRestoreFieldState();

   XmlBoolean xgetRestoreFieldState();

   boolean isSetRestoreFieldState();

   void setRestoreFieldState(boolean var1);

   void xsetRestoreFieldState(XmlBoolean var1);

   void unsetRestoreFieldState();

   public static final class Factory {
      public static OracleSavepointManagerType newInstance() {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType newInstance(XmlOptions options) {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().newInstance(OracleSavepointManagerType.type, options);
      }

      public static OracleSavepointManagerType parse(String xmlAsString) throws XmlException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OracleSavepointManagerType.type, options);
      }

      public static OracleSavepointManagerType parse(File file) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(file, OracleSavepointManagerType.type, options);
      }

      public static OracleSavepointManagerType parse(URL u) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(u, OracleSavepointManagerType.type, options);
      }

      public static OracleSavepointManagerType parse(InputStream is) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(is, OracleSavepointManagerType.type, options);
      }

      public static OracleSavepointManagerType parse(Reader r) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(r, OracleSavepointManagerType.type, options);
      }

      public static OracleSavepointManagerType parse(XMLStreamReader sr) throws XmlException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(sr, OracleSavepointManagerType.type, options);
      }

      public static OracleSavepointManagerType parse(Node node) throws XmlException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      public static OracleSavepointManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(node, OracleSavepointManagerType.type, options);
      }

      /** @deprecated */
      public static OracleSavepointManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OracleSavepointManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OracleSavepointManagerType)XmlBeans.getContextTypeLoader().parse(xis, OracleSavepointManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OracleSavepointManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OracleSavepointManagerType.type, options);
      }

      private Factory() {
      }
   }
}
