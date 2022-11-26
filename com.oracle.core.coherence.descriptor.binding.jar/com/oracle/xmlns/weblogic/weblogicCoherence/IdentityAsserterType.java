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

public interface IdentityAsserterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IdentityAsserterType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("identityassertertype052ftype");

   String getClassName();

   XmlString xgetClassName();

   boolean isNilClassName();

   boolean isSetClassName();

   void setClassName(String var1);

   void xsetClassName(XmlString var1);

   void setNilClassName();

   void unsetClassName();

   CoherenceInitParamType[] getCoherenceInitParamArray();

   CoherenceInitParamType getCoherenceInitParamArray(int var1);

   int sizeOfCoherenceInitParamArray();

   void setCoherenceInitParamArray(CoherenceInitParamType[] var1);

   void setCoherenceInitParamArray(int var1, CoherenceInitParamType var2);

   CoherenceInitParamType insertNewCoherenceInitParam(int var1);

   CoherenceInitParamType addNewCoherenceInitParam();

   void removeCoherenceInitParam(int var1);

   public static final class Factory {
      public static IdentityAsserterType newInstance() {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().newInstance(IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType newInstance(XmlOptions options) {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().newInstance(IdentityAsserterType.type, options);
      }

      public static IdentityAsserterType parse(String xmlAsString) throws XmlException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IdentityAsserterType.type, options);
      }

      public static IdentityAsserterType parse(File file) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(file, IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(file, IdentityAsserterType.type, options);
      }

      public static IdentityAsserterType parse(URL u) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(u, IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(u, IdentityAsserterType.type, options);
      }

      public static IdentityAsserterType parse(InputStream is) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(is, IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(is, IdentityAsserterType.type, options);
      }

      public static IdentityAsserterType parse(Reader r) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(r, IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(r, IdentityAsserterType.type, options);
      }

      public static IdentityAsserterType parse(XMLStreamReader sr) throws XmlException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(sr, IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(sr, IdentityAsserterType.type, options);
      }

      public static IdentityAsserterType parse(Node node) throws XmlException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(node, IdentityAsserterType.type, (XmlOptions)null);
      }

      public static IdentityAsserterType parse(Node node, XmlOptions options) throws XmlException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(node, IdentityAsserterType.type, options);
      }

      /** @deprecated */
      public static IdentityAsserterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(xis, IdentityAsserterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IdentityAsserterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IdentityAsserterType)XmlBeans.getContextTypeLoader().parse(xis, IdentityAsserterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IdentityAsserterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IdentityAsserterType.type, options);
      }

      private Factory() {
      }
   }
}
