package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CoherenceClusterWellKnownAddressType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceClusterWellKnownAddressType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceclusterwellknownaddresstyped616type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getListenAddress();

   XmlString xgetListenAddress();

   boolean isNilListenAddress();

   boolean isSetListenAddress();

   void setListenAddress(String var1);

   void xsetListenAddress(XmlString var1);

   void setNilListenAddress();

   void unsetListenAddress();

   XsdNonNegativeIntegerType getListenPort();

   boolean isSetListenPort();

   void setListenPort(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewListenPort();

   void unsetListenPort();

   public static final class Factory {
      public static CoherenceClusterWellKnownAddressType newInstance() {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType newInstance(XmlOptions options) {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterWellKnownAddressType.type, options);
      }

      public static CoherenceClusterWellKnownAddressType parse(String xmlAsString) throws XmlException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterWellKnownAddressType.type, options);
      }

      public static CoherenceClusterWellKnownAddressType parse(File file) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterWellKnownAddressType.type, options);
      }

      public static CoherenceClusterWellKnownAddressType parse(URL u) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterWellKnownAddressType.type, options);
      }

      public static CoherenceClusterWellKnownAddressType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterWellKnownAddressType.type, options);
      }

      public static CoherenceClusterWellKnownAddressType parse(Reader r) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterWellKnownAddressType.type, options);
      }

      public static CoherenceClusterWellKnownAddressType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterWellKnownAddressType.type, options);
      }

      public static CoherenceClusterWellKnownAddressType parse(Node node) throws XmlException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterWellKnownAddressType.type, options);
      }

      /** @deprecated */
      public static CoherenceClusterWellKnownAddressType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceClusterWellKnownAddressType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceClusterWellKnownAddressType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterWellKnownAddressType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterWellKnownAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterWellKnownAddressType.type, options);
      }

      private Factory() {
      }
   }
}
