package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PrincipalNameDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PrincipalNameDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("principalnamee358doctype");

   String getPrincipalName();

   void setPrincipalName(String var1);

   String addNewPrincipalName();

   public static final class Factory {
      public static PrincipalNameDocument newInstance() {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().newInstance(PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument newInstance(XmlOptions options) {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().newInstance(PrincipalNameDocument.type, options);
      }

      public static PrincipalNameDocument parse(java.lang.String xmlAsString) throws XmlException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PrincipalNameDocument.type, options);
      }

      public static PrincipalNameDocument parse(File file) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(file, PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(file, PrincipalNameDocument.type, options);
      }

      public static PrincipalNameDocument parse(URL u) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(u, PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(u, PrincipalNameDocument.type, options);
      }

      public static PrincipalNameDocument parse(InputStream is) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(is, PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(is, PrincipalNameDocument.type, options);
      }

      public static PrincipalNameDocument parse(Reader r) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(r, PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(r, PrincipalNameDocument.type, options);
      }

      public static PrincipalNameDocument parse(XMLStreamReader sr) throws XmlException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(sr, PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(sr, PrincipalNameDocument.type, options);
      }

      public static PrincipalNameDocument parse(Node node) throws XmlException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(node, PrincipalNameDocument.type, (XmlOptions)null);
      }

      public static PrincipalNameDocument parse(Node node, XmlOptions options) throws XmlException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(node, PrincipalNameDocument.type, options);
      }

      /** @deprecated */
      public static PrincipalNameDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(xis, PrincipalNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PrincipalNameDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PrincipalNameDocument)XmlBeans.getContextTypeLoader().parse(xis, PrincipalNameDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PrincipalNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PrincipalNameDocument.type, options);
      }

      private Factory() {
      }
   }
}
