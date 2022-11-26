package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface CreateAsPrincipalNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CreateAsPrincipalNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("createasprincipalnametypecd6atype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CreateAsPrincipalNameType newInstance() {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType newInstance(XmlOptions options) {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(CreateAsPrincipalNameType.type, options);
      }

      public static CreateAsPrincipalNameType parse(String xmlAsString) throws XmlException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CreateAsPrincipalNameType.type, options);
      }

      public static CreateAsPrincipalNameType parse(File file) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, CreateAsPrincipalNameType.type, options);
      }

      public static CreateAsPrincipalNameType parse(URL u) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, CreateAsPrincipalNameType.type, options);
      }

      public static CreateAsPrincipalNameType parse(InputStream is) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, CreateAsPrincipalNameType.type, options);
      }

      public static CreateAsPrincipalNameType parse(Reader r) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, CreateAsPrincipalNameType.type, options);
      }

      public static CreateAsPrincipalNameType parse(XMLStreamReader sr) throws XmlException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, CreateAsPrincipalNameType.type, options);
      }

      public static CreateAsPrincipalNameType parse(Node node) throws XmlException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static CreateAsPrincipalNameType parse(Node node, XmlOptions options) throws XmlException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, CreateAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static CreateAsPrincipalNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CreateAsPrincipalNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CreateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, CreateAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CreateAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CreateAsPrincipalNameType.type, options);
      }

      private Factory() {
      }
   }
}
