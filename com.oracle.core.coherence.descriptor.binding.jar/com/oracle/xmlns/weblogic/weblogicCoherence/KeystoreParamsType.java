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

public interface KeystoreParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KeystoreParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("keystoreparamstype431etype");

   String getCoherenceIdentityAlias();

   XmlString xgetCoherenceIdentityAlias();

   boolean isNilCoherenceIdentityAlias();

   boolean isSetCoherenceIdentityAlias();

   void setCoherenceIdentityAlias(String var1);

   void xsetCoherenceIdentityAlias(XmlString var1);

   void setNilCoherenceIdentityAlias();

   void unsetCoherenceIdentityAlias();

   String getCoherencePrivateKeyPassPhraseEncrypted();

   XmlString xgetCoherencePrivateKeyPassPhraseEncrypted();

   boolean isNilCoherencePrivateKeyPassPhraseEncrypted();

   boolean isSetCoherencePrivateKeyPassPhraseEncrypted();

   void setCoherencePrivateKeyPassPhraseEncrypted(String var1);

   void xsetCoherencePrivateKeyPassPhraseEncrypted(XmlString var1);

   void setNilCoherencePrivateKeyPassPhraseEncrypted();

   void unsetCoherencePrivateKeyPassPhraseEncrypted();

   public static final class Factory {
      public static KeystoreParamsType newInstance() {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().newInstance(KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType newInstance(XmlOptions options) {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().newInstance(KeystoreParamsType.type, options);
      }

      public static KeystoreParamsType parse(String xmlAsString) throws XmlException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KeystoreParamsType.type, options);
      }

      public static KeystoreParamsType parse(File file) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(file, KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(file, KeystoreParamsType.type, options);
      }

      public static KeystoreParamsType parse(URL u) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(u, KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(u, KeystoreParamsType.type, options);
      }

      public static KeystoreParamsType parse(InputStream is) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(is, KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(is, KeystoreParamsType.type, options);
      }

      public static KeystoreParamsType parse(Reader r) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(r, KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(r, KeystoreParamsType.type, options);
      }

      public static KeystoreParamsType parse(XMLStreamReader sr) throws XmlException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(sr, KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(sr, KeystoreParamsType.type, options);
      }

      public static KeystoreParamsType parse(Node node) throws XmlException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(node, KeystoreParamsType.type, (XmlOptions)null);
      }

      public static KeystoreParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(node, KeystoreParamsType.type, options);
      }

      /** @deprecated */
      public static KeystoreParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(xis, KeystoreParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KeystoreParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KeystoreParamsType)XmlBeans.getContextTypeLoader().parse(xis, KeystoreParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeystoreParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KeystoreParamsType.type, options);
      }

      private Factory() {
      }
   }
}
