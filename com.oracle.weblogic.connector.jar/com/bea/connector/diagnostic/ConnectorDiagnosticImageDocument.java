package com.bea.connector.diagnostic;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ConnectorDiagnosticImageDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectorDiagnosticImageDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("connectordiagnosticimagecc30doctype");

   XmlObject getConnectorDiagnosticImage();

   void setConnectorDiagnosticImage(XmlObject var1);

   XmlObject addNewConnectorDiagnosticImage();

   public static final class Factory {
      public static ConnectorDiagnosticImageDocument newInstance() {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument newInstance(XmlOptions options) {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().newInstance(ConnectorDiagnosticImageDocument.type, options);
      }

      public static ConnectorDiagnosticImageDocument parse(String xmlAsString) throws XmlException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDiagnosticImageDocument.type, options);
      }

      public static ConnectorDiagnosticImageDocument parse(File file) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(file, ConnectorDiagnosticImageDocument.type, options);
      }

      public static ConnectorDiagnosticImageDocument parse(URL u) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(u, ConnectorDiagnosticImageDocument.type, options);
      }

      public static ConnectorDiagnosticImageDocument parse(InputStream is) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(is, ConnectorDiagnosticImageDocument.type, options);
      }

      public static ConnectorDiagnosticImageDocument parse(Reader r) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(r, ConnectorDiagnosticImageDocument.type, options);
      }

      public static ConnectorDiagnosticImageDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDiagnosticImageDocument.type, options);
      }

      public static ConnectorDiagnosticImageDocument parse(Node node) throws XmlException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(node, ConnectorDiagnosticImageDocument.type, options);
      }

      /** @deprecated */
      public static ConnectorDiagnosticImageDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectorDiagnosticImageDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectorDiagnosticImageDocument)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDiagnosticImageDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDiagnosticImageDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDiagnosticImageDocument.type, options);
      }

      private Factory() {
      }
   }
}
