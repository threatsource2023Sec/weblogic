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

public interface CoherenceAddressProvidersType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceAddressProvidersType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceaddressproviderstype91bdtype");

   CoherenceAddressProviderType[] getCoherenceAddressProviderArray();

   CoherenceAddressProviderType getCoherenceAddressProviderArray(int var1);

   int sizeOfCoherenceAddressProviderArray();

   void setCoherenceAddressProviderArray(CoherenceAddressProviderType[] var1);

   void setCoherenceAddressProviderArray(int var1, CoherenceAddressProviderType var2);

   CoherenceAddressProviderType insertNewCoherenceAddressProvider(int var1);

   CoherenceAddressProviderType addNewCoherenceAddressProvider();

   void removeCoherenceAddressProvider(int var1);

   public static final class Factory {
      public static CoherenceAddressProvidersType newInstance() {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().newInstance(CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType newInstance(XmlOptions options) {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().newInstance(CoherenceAddressProvidersType.type, options);
      }

      public static CoherenceAddressProvidersType parse(String xmlAsString) throws XmlException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceAddressProvidersType.type, options);
      }

      public static CoherenceAddressProvidersType parse(File file) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(file, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(file, CoherenceAddressProvidersType.type, options);
      }

      public static CoherenceAddressProvidersType parse(URL u) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(u, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(u, CoherenceAddressProvidersType.type, options);
      }

      public static CoherenceAddressProvidersType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(is, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(is, CoherenceAddressProvidersType.type, options);
      }

      public static CoherenceAddressProvidersType parse(Reader r) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(r, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(r, CoherenceAddressProvidersType.type, options);
      }

      public static CoherenceAddressProvidersType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceAddressProvidersType.type, options);
      }

      public static CoherenceAddressProvidersType parse(Node node) throws XmlException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(node, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProvidersType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(node, CoherenceAddressProvidersType.type, options);
      }

      /** @deprecated */
      public static CoherenceAddressProvidersType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceAddressProvidersType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceAddressProvidersType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceAddressProvidersType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceAddressProvidersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceAddressProvidersType.type, options);
      }

      private Factory() {
      }
   }
}
