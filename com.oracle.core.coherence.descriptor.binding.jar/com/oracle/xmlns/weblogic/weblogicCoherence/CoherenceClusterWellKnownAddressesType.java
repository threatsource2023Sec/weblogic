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

public interface CoherenceClusterWellKnownAddressesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceClusterWellKnownAddressesType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceclusterwellknownaddressestypeb0c4type");

   CoherenceClusterWellKnownAddressType[] getCoherenceClusterWellKnownAddressArray();

   CoherenceClusterWellKnownAddressType getCoherenceClusterWellKnownAddressArray(int var1);

   int sizeOfCoherenceClusterWellKnownAddressArray();

   void setCoherenceClusterWellKnownAddressArray(CoherenceClusterWellKnownAddressType[] var1);

   void setCoherenceClusterWellKnownAddressArray(int var1, CoherenceClusterWellKnownAddressType var2);

   CoherenceClusterWellKnownAddressType insertNewCoherenceClusterWellKnownAddress(int var1);

   CoherenceClusterWellKnownAddressType addNewCoherenceClusterWellKnownAddress();

   void removeCoherenceClusterWellKnownAddress(int var1);

   public static final class Factory {
      public static CoherenceClusterWellKnownAddressesType newInstance() {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType newInstance(XmlOptions options) {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().newInstance(CoherenceClusterWellKnownAddressesType.type, options);
      }

      public static CoherenceClusterWellKnownAddressesType parse(String xmlAsString) throws XmlException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceClusterWellKnownAddressesType.type, options);
      }

      public static CoherenceClusterWellKnownAddressesType parse(File file) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(file, CoherenceClusterWellKnownAddressesType.type, options);
      }

      public static CoherenceClusterWellKnownAddressesType parse(URL u) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(u, CoherenceClusterWellKnownAddressesType.type, options);
      }

      public static CoherenceClusterWellKnownAddressesType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(is, CoherenceClusterWellKnownAddressesType.type, options);
      }

      public static CoherenceClusterWellKnownAddressesType parse(Reader r) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(r, CoherenceClusterWellKnownAddressesType.type, options);
      }

      public static CoherenceClusterWellKnownAddressesType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceClusterWellKnownAddressesType.type, options);
      }

      public static CoherenceClusterWellKnownAddressesType parse(Node node) throws XmlException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      public static CoherenceClusterWellKnownAddressesType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(node, CoherenceClusterWellKnownAddressesType.type, options);
      }

      /** @deprecated */
      public static CoherenceClusterWellKnownAddressesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceClusterWellKnownAddressesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceClusterWellKnownAddressesType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceClusterWellKnownAddressesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterWellKnownAddressesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceClusterWellKnownAddressesType.type, options);
      }

      private Factory() {
      }
   }
}
