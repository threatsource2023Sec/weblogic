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

public interface ConfidentialityType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfidentialityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("confidentialitytypecd6atype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConfidentialityType newInstance() {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().newInstance(ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType newInstance(XmlOptions options) {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().newInstance(ConfidentialityType.type, options);
      }

      public static ConfidentialityType parse(String xmlAsString) throws XmlException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfidentialityType.type, options);
      }

      public static ConfidentialityType parse(File file) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(file, ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(file, ConfidentialityType.type, options);
      }

      public static ConfidentialityType parse(URL u) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(u, ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(u, ConfidentialityType.type, options);
      }

      public static ConfidentialityType parse(InputStream is) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(is, ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(is, ConfidentialityType.type, options);
      }

      public static ConfidentialityType parse(Reader r) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(r, ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(r, ConfidentialityType.type, options);
      }

      public static ConfidentialityType parse(XMLStreamReader sr) throws XmlException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(sr, ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(sr, ConfidentialityType.type, options);
      }

      public static ConfidentialityType parse(Node node) throws XmlException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(node, ConfidentialityType.type, (XmlOptions)null);
      }

      public static ConfidentialityType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(node, ConfidentialityType.type, options);
      }

      /** @deprecated */
      public static ConfidentialityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(xis, ConfidentialityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfidentialityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfidentialityType)XmlBeans.getContextTypeLoader().parse(xis, ConfidentialityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfidentialityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfidentialityType.type, options);
      }

      private Factory() {
      }
   }
}
