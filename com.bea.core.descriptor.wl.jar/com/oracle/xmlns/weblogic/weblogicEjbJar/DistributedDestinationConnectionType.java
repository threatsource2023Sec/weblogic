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

public interface DistributedDestinationConnectionType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DistributedDestinationConnectionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("distributeddestinationconnectiontypea51dtype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DistributedDestinationConnectionType newInstance() {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType newInstance(XmlOptions options) {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().newInstance(DistributedDestinationConnectionType.type, options);
      }

      public static DistributedDestinationConnectionType parse(String xmlAsString) throws XmlException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedDestinationConnectionType.type, options);
      }

      public static DistributedDestinationConnectionType parse(File file) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(file, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(file, DistributedDestinationConnectionType.type, options);
      }

      public static DistributedDestinationConnectionType parse(URL u) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(u, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(u, DistributedDestinationConnectionType.type, options);
      }

      public static DistributedDestinationConnectionType parse(InputStream is) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(is, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(is, DistributedDestinationConnectionType.type, options);
      }

      public static DistributedDestinationConnectionType parse(Reader r) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(r, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(r, DistributedDestinationConnectionType.type, options);
      }

      public static DistributedDestinationConnectionType parse(XMLStreamReader sr) throws XmlException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(sr, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(sr, DistributedDestinationConnectionType.type, options);
      }

      public static DistributedDestinationConnectionType parse(Node node) throws XmlException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(node, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      public static DistributedDestinationConnectionType parse(Node node, XmlOptions options) throws XmlException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(node, DistributedDestinationConnectionType.type, options);
      }

      /** @deprecated */
      public static DistributedDestinationConnectionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(xis, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DistributedDestinationConnectionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DistributedDestinationConnectionType)XmlBeans.getContextTypeLoader().parse(xis, DistributedDestinationConnectionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedDestinationConnectionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedDestinationConnectionType.type, options);
      }

      private Factory() {
      }
   }
}
