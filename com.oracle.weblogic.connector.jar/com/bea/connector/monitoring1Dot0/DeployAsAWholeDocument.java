package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface DeployAsAWholeDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeployAsAWholeDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("deployasawhole9c2adoctype");

   boolean getDeployAsAWhole();

   XmlBoolean xgetDeployAsAWhole();

   void setDeployAsAWhole(boolean var1);

   void xsetDeployAsAWhole(XmlBoolean var1);

   public static final class Factory {
      public static DeployAsAWholeDocument newInstance() {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().newInstance(DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument newInstance(XmlOptions options) {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().newInstance(DeployAsAWholeDocument.type, options);
      }

      public static DeployAsAWholeDocument parse(String xmlAsString) throws XmlException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeployAsAWholeDocument.type, options);
      }

      public static DeployAsAWholeDocument parse(File file) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(file, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(file, DeployAsAWholeDocument.type, options);
      }

      public static DeployAsAWholeDocument parse(URL u) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(u, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(u, DeployAsAWholeDocument.type, options);
      }

      public static DeployAsAWholeDocument parse(InputStream is) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(is, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(is, DeployAsAWholeDocument.type, options);
      }

      public static DeployAsAWholeDocument parse(Reader r) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(r, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(r, DeployAsAWholeDocument.type, options);
      }

      public static DeployAsAWholeDocument parse(XMLStreamReader sr) throws XmlException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(sr, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(sr, DeployAsAWholeDocument.type, options);
      }

      public static DeployAsAWholeDocument parse(Node node) throws XmlException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(node, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      public static DeployAsAWholeDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(node, DeployAsAWholeDocument.type, options);
      }

      /** @deprecated */
      public static DeployAsAWholeDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(xis, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeployAsAWholeDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeployAsAWholeDocument)XmlBeans.getContextTypeLoader().parse(xis, DeployAsAWholeDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeployAsAWholeDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeployAsAWholeDocument.type, options);
      }

      private Factory() {
      }
   }
}
