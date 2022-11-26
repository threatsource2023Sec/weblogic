package com.oracle.xmlns.weblogic.weblogicCoherence;

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

public interface WeblogicCoherenceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicCoherenceDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("weblogiccoherencedda6doctype");

   WeblogicCoherenceType getWeblogicCoherence();

   void setWeblogicCoherence(WeblogicCoherenceType var1);

   WeblogicCoherenceType addNewWeblogicCoherence();

   public static final class Factory {
      public static WeblogicCoherenceDocument newInstance() {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument newInstance(XmlOptions options) {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().newInstance(WeblogicCoherenceDocument.type, options);
      }

      public static WeblogicCoherenceDocument parse(String xmlAsString) throws XmlException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicCoherenceDocument.type, options);
      }

      public static WeblogicCoherenceDocument parse(File file) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(file, WeblogicCoherenceDocument.type, options);
      }

      public static WeblogicCoherenceDocument parse(URL u) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(u, WeblogicCoherenceDocument.type, options);
      }

      public static WeblogicCoherenceDocument parse(InputStream is) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(is, WeblogicCoherenceDocument.type, options);
      }

      public static WeblogicCoherenceDocument parse(Reader r) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(r, WeblogicCoherenceDocument.type, options);
      }

      public static WeblogicCoherenceDocument parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(sr, WeblogicCoherenceDocument.type, options);
      }

      public static WeblogicCoherenceDocument parse(Node node) throws XmlException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      public static WeblogicCoherenceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(node, WeblogicCoherenceDocument.type, options);
      }

      /** @deprecated */
      public static WeblogicCoherenceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicCoherenceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicCoherenceDocument)XmlBeans.getContextTypeLoader().parse(xis, WeblogicCoherenceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicCoherenceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicCoherenceDocument.type, options);
      }

      private Factory() {
      }
   }
}
