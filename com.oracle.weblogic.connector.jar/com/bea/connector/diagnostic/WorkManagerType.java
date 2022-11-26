package com.bea.connector.diagnostic;

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

public interface WorkManagerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WorkManagerType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("workmanagertypee2ddtype");

   String getWorkManagerName();

   XmlString xgetWorkManagerName();

   void setWorkManagerName(String var1);

   void xsetWorkManagerName(XmlString var1);

   public static final class Factory {
      public static WorkManagerType newInstance() {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().newInstance(WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType newInstance(XmlOptions options) {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().newInstance(WorkManagerType.type, options);
      }

      public static WorkManagerType parse(String xmlAsString) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(File file) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(file, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(file, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(URL u) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(u, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(u, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(InputStream is) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(is, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(is, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(Reader r) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(r, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(r, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(XMLStreamReader sr) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(sr, WorkManagerType.type, options);
      }

      public static WorkManagerType parse(Node node) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(node, WorkManagerType.type, (XmlOptions)null);
      }

      public static WorkManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(node, WorkManagerType.type, options);
      }

      /** @deprecated */
      public static WorkManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WorkManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WorkManagerType)XmlBeans.getContextTypeLoader().parse(xis, WorkManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WorkManagerType.type, options);
      }

      private Factory() {
      }
   }
}
