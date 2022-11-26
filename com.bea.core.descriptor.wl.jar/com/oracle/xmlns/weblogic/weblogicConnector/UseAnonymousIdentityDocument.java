package com.oracle.xmlns.weblogic.weblogicConnector;

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

public interface UseAnonymousIdentityDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UseAnonymousIdentityDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("useanonymousidentitye8b2doctype");

   TrueFalseType getUseAnonymousIdentity();

   void setUseAnonymousIdentity(TrueFalseType var1);

   TrueFalseType addNewUseAnonymousIdentity();

   public static final class Factory {
      public static UseAnonymousIdentityDocument newInstance() {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().newInstance(UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument newInstance(XmlOptions options) {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().newInstance(UseAnonymousIdentityDocument.type, options);
      }

      public static UseAnonymousIdentityDocument parse(String xmlAsString) throws XmlException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, UseAnonymousIdentityDocument.type, options);
      }

      public static UseAnonymousIdentityDocument parse(File file) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(file, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(file, UseAnonymousIdentityDocument.type, options);
      }

      public static UseAnonymousIdentityDocument parse(URL u) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(u, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(u, UseAnonymousIdentityDocument.type, options);
      }

      public static UseAnonymousIdentityDocument parse(InputStream is) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(is, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(is, UseAnonymousIdentityDocument.type, options);
      }

      public static UseAnonymousIdentityDocument parse(Reader r) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(r, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(r, UseAnonymousIdentityDocument.type, options);
      }

      public static UseAnonymousIdentityDocument parse(XMLStreamReader sr) throws XmlException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(sr, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(sr, UseAnonymousIdentityDocument.type, options);
      }

      public static UseAnonymousIdentityDocument parse(Node node) throws XmlException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(node, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      public static UseAnonymousIdentityDocument parse(Node node, XmlOptions options) throws XmlException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(node, UseAnonymousIdentityDocument.type, options);
      }

      /** @deprecated */
      public static UseAnonymousIdentityDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(xis, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UseAnonymousIdentityDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UseAnonymousIdentityDocument)XmlBeans.getContextTypeLoader().parse(xis, UseAnonymousIdentityDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UseAnonymousIdentityDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UseAnonymousIdentityDocument.type, options);
      }

      private Factory() {
      }
   }
}
