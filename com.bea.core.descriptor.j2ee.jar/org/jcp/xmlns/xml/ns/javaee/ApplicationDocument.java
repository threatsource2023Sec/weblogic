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

public interface ApplicationDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("application7e17doctype");

   ApplicationType getApplication();

   void setApplication(ApplicationType var1);

   ApplicationType addNewApplication();

   public static final class Factory {
      public static ApplicationDocument newInstance() {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().newInstance(ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument newInstance(XmlOptions options) {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().newInstance(ApplicationDocument.type, options);
      }

      public static ApplicationDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationDocument.type, options);
      }

      public static ApplicationDocument parse(File file) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(file, ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(file, ApplicationDocument.type, options);
      }

      public static ApplicationDocument parse(URL u) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(u, ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(u, ApplicationDocument.type, options);
      }

      public static ApplicationDocument parse(InputStream is) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(is, ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(is, ApplicationDocument.type, options);
      }

      public static ApplicationDocument parse(Reader r) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(r, ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(r, ApplicationDocument.type, options);
      }

      public static ApplicationDocument parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(sr, ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(sr, ApplicationDocument.type, options);
      }

      public static ApplicationDocument parse(Node node) throws XmlException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(node, ApplicationDocument.type, (XmlOptions)null);
      }

      public static ApplicationDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(node, ApplicationDocument.type, options);
      }

      /** @deprecated */
      public static ApplicationDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(xis, ApplicationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationDocument)XmlBeans.getContextTypeLoader().parse(xis, ApplicationDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationDocument.type, options);
      }

      private Factory() {
      }
   }
}
