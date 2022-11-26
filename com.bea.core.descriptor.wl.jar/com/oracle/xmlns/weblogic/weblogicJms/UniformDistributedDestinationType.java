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

public interface UniformDistributedDestinationType extends DestinationType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UniformDistributedDestinationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("uniformdistributeddestinationtype50bdtype");

   public static final class Factory {
      public static UniformDistributedDestinationType newInstance() {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().newInstance(UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType newInstance(XmlOptions options) {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().newInstance(UniformDistributedDestinationType.type, options);
      }

      public static UniformDistributedDestinationType parse(String xmlAsString) throws XmlException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UniformDistributedDestinationType.type, options);
      }

      public static UniformDistributedDestinationType parse(File file) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(file, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(file, UniformDistributedDestinationType.type, options);
      }

      public static UniformDistributedDestinationType parse(URL u) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(u, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(u, UniformDistributedDestinationType.type, options);
      }

      public static UniformDistributedDestinationType parse(InputStream is) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(is, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(is, UniformDistributedDestinationType.type, options);
      }

      public static UniformDistributedDestinationType parse(Reader r) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(r, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(r, UniformDistributedDestinationType.type, options);
      }

      public static UniformDistributedDestinationType parse(XMLStreamReader sr) throws XmlException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(sr, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(sr, UniformDistributedDestinationType.type, options);
      }

      public static UniformDistributedDestinationType parse(Node node) throws XmlException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(node, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      public static UniformDistributedDestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(node, UniformDistributedDestinationType.type, options);
      }

      /** @deprecated */
      public static UniformDistributedDestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xis, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UniformDistributedDestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UniformDistributedDestinationType)XmlBeans.getContextTypeLoader().parse(xis, UniformDistributedDestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniformDistributedDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UniformDistributedDestinationType.type, options);
      }

      private Factory() {
      }
   }
}
