package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface CoherenceAddressProviderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceAddressProviderType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceaddressprovidertype3ce2type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   CoherenceSocketAddressType[] getCoherenceSocketAddressArray();

   CoherenceSocketAddressType getCoherenceSocketAddressArray(int var1);

   int sizeOfCoherenceSocketAddressArray();

   void setCoherenceSocketAddressArray(CoherenceSocketAddressType[] var1);

   void setCoherenceSocketAddressArray(int var1, CoherenceSocketAddressType var2);

   CoherenceSocketAddressType insertNewCoherenceSocketAddress(int var1);

   CoherenceSocketAddressType addNewCoherenceSocketAddress();

   void removeCoherenceSocketAddress(int var1);

   public static final class Factory {
      public static CoherenceAddressProviderType newInstance() {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().newInstance(CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType newInstance(XmlOptions options) {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().newInstance(CoherenceAddressProviderType.type, options);
      }

      public static CoherenceAddressProviderType parse(String xmlAsString) throws XmlException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceAddressProviderType.type, options);
      }

      public static CoherenceAddressProviderType parse(File file) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(file, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(file, CoherenceAddressProviderType.type, options);
      }

      public static CoherenceAddressProviderType parse(URL u) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(u, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(u, CoherenceAddressProviderType.type, options);
      }

      public static CoherenceAddressProviderType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(is, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(is, CoherenceAddressProviderType.type, options);
      }

      public static CoherenceAddressProviderType parse(Reader r) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(r, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(r, CoherenceAddressProviderType.type, options);
      }

      public static CoherenceAddressProviderType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceAddressProviderType.type, options);
      }

      public static CoherenceAddressProviderType parse(Node node) throws XmlException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(node, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      public static CoherenceAddressProviderType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(node, CoherenceAddressProviderType.type, options);
      }

      /** @deprecated */
      public static CoherenceAddressProviderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceAddressProviderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceAddressProviderType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceAddressProviderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceAddressProviderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceAddressProviderType.type, options);
      }

      private Factory() {
      }
   }
}
