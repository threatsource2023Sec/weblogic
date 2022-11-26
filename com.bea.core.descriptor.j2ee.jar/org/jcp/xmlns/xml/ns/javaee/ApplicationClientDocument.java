package org.jcp.xmlns.xml.ns.javaee;

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

public interface ApplicationClientDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationClientDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("applicationclient51eddoctype");

   ApplicationClientType getApplicationClient();

   void setApplicationClient(ApplicationClientType var1);

   ApplicationClientType addNewApplicationClient();

   public static final class Factory {
      public static ApplicationClientDocument newInstance() {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().newInstance(ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument newInstance(XmlOptions options) {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().newInstance(ApplicationClientDocument.type, options);
      }

      public static ApplicationClientDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationClientDocument.type, options);
      }

      public static ApplicationClientDocument parse(File file) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(file, ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(file, ApplicationClientDocument.type, options);
      }

      public static ApplicationClientDocument parse(URL u) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(u, ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(u, ApplicationClientDocument.type, options);
      }

      public static ApplicationClientDocument parse(InputStream is) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(is, ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(is, ApplicationClientDocument.type, options);
      }

      public static ApplicationClientDocument parse(Reader r) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(r, ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(r, ApplicationClientDocument.type, options);
      }

      public static ApplicationClientDocument parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(sr, ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(sr, ApplicationClientDocument.type, options);
      }

      public static ApplicationClientDocument parse(Node node) throws XmlException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(node, ApplicationClientDocument.type, (XmlOptions)null);
      }

      public static ApplicationClientDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(node, ApplicationClientDocument.type, options);
      }

      /** @deprecated */
      public static ApplicationClientDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xis, ApplicationClientDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationClientDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationClientDocument)XmlBeans.getContextTypeLoader().parse(xis, ApplicationClientDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationClientDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationClientDocument.type, options);
      }

      private Factory() {
      }
   }
}
