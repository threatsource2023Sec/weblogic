package com.bea.connector.monitoring1Dot0;

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

public interface WorkManagerDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WorkManagerDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("workmanagerf95ddoctype");

   WorkManagerType getWorkManager();

   void setWorkManager(WorkManagerType var1);

   WorkManagerType addNewWorkManager();

   public static final class Factory {
      public static WorkManagerDocument newInstance() {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().newInstance(WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument newInstance(XmlOptions options) {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().newInstance(WorkManagerDocument.type, options);
      }

      public static WorkManagerDocument parse(String xmlAsString) throws XmlException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerDocument.type, options);
      }

      public static WorkManagerDocument parse(File file) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(file, WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(file, WorkManagerDocument.type, options);
      }

      public static WorkManagerDocument parse(URL u) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(u, WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(u, WorkManagerDocument.type, options);
      }

      public static WorkManagerDocument parse(InputStream is) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(is, WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(is, WorkManagerDocument.type, options);
      }

      public static WorkManagerDocument parse(Reader r) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(r, WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(r, WorkManagerDocument.type, options);
      }

      public static WorkManagerDocument parse(XMLStreamReader sr) throws XmlException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerDocument.type, options);
      }

      public static WorkManagerDocument parse(Node node) throws XmlException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(node, WorkManagerDocument.type, (XmlOptions)null);
      }

      public static WorkManagerDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(node, WorkManagerDocument.type, options);
      }

      /** @deprecated */
      public static WorkManagerDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WorkManagerDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WorkManagerDocument)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerDocument.type, options);
      }

      private Factory() {
      }
   }
}
