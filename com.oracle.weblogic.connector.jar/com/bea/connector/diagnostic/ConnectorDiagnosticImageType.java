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

public interface ConnectorDiagnosticImageType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectorDiagnosticImageType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("connectordiagnosticimagetypec35atype");

   AdapterType[] getAdapterArray();

   AdapterType getAdapterArray(int var1);

   int sizeOfAdapterArray();

   void setAdapterArray(AdapterType[] var1);

   void setAdapterArray(int var1, AdapterType var2);

   AdapterType insertNewAdapter(int var1);

   AdapterType addNewAdapter();

   void removeAdapter(int var1);

   public static final class Factory {
      public static ConnectorDiagnosticImageType newInstance() {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().newInstance(ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType newInstance(XmlOptions options) {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().newInstance(ConnectorDiagnosticImageType.type, options);
      }

      public static ConnectorDiagnosticImageType parse(String xmlAsString) throws XmlException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectorDiagnosticImageType.type, options);
      }

      public static ConnectorDiagnosticImageType parse(File file) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(file, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(file, ConnectorDiagnosticImageType.type, options);
      }

      public static ConnectorDiagnosticImageType parse(URL u) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(u, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(u, ConnectorDiagnosticImageType.type, options);
      }

      public static ConnectorDiagnosticImageType parse(InputStream is) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(is, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(is, ConnectorDiagnosticImageType.type, options);
      }

      public static ConnectorDiagnosticImageType parse(Reader r) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(r, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(r, ConnectorDiagnosticImageType.type, options);
      }

      public static ConnectorDiagnosticImageType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(sr, ConnectorDiagnosticImageType.type, options);
      }

      public static ConnectorDiagnosticImageType parse(Node node) throws XmlException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(node, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      public static ConnectorDiagnosticImageType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(node, ConnectorDiagnosticImageType.type, options);
      }

      /** @deprecated */
      public static ConnectorDiagnosticImageType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectorDiagnosticImageType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectorDiagnosticImageType)XmlBeans.getContextTypeLoader().parse(xis, ConnectorDiagnosticImageType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDiagnosticImageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectorDiagnosticImageType.type, options);
      }

      private Factory() {
      }
   }
}
