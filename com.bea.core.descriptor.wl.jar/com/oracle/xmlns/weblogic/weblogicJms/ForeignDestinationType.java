package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ForeignDestinationType extends ForeignJndiObjectType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ForeignDestinationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("foreigndestinationtype2833type");

   public static final class Factory {
      public static ForeignDestinationType newInstance() {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().newInstance(ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType newInstance(XmlOptions options) {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().newInstance(ForeignDestinationType.type, options);
      }

      public static ForeignDestinationType parse(String xmlAsString) throws XmlException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ForeignDestinationType.type, options);
      }

      public static ForeignDestinationType parse(File file) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(file, ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(file, ForeignDestinationType.type, options);
      }

      public static ForeignDestinationType parse(URL u) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(u, ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(u, ForeignDestinationType.type, options);
      }

      public static ForeignDestinationType parse(InputStream is) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(is, ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(is, ForeignDestinationType.type, options);
      }

      public static ForeignDestinationType parse(Reader r) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(r, ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(r, ForeignDestinationType.type, options);
      }

      public static ForeignDestinationType parse(XMLStreamReader sr) throws XmlException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(sr, ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(sr, ForeignDestinationType.type, options);
      }

      public static ForeignDestinationType parse(Node node) throws XmlException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(node, ForeignDestinationType.type, (XmlOptions)null);
      }

      public static ForeignDestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(node, ForeignDestinationType.type, options);
      }

      /** @deprecated */
      public static ForeignDestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(xis, ForeignDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ForeignDestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ForeignDestinationType)XmlBeans.getContextTypeLoader().parse(xis, ForeignDestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ForeignDestinationType.type, options);
      }

      private Factory() {
      }
   }
}
