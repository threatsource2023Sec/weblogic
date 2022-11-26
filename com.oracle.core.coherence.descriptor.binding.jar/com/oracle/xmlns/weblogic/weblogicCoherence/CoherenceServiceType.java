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

public interface CoherenceServiceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceServiceType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceservicetypeabe9type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getPartition();

   XmlString xgetPartition();

   boolean isNilPartition();

   boolean isSetPartition();

   void setPartition(String var1);

   void xsetPartition(XmlString var1);

   void setNilPartition();

   void unsetPartition();

   public static final class Factory {
      public static CoherenceServiceType newInstance() {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().newInstance(CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType newInstance(XmlOptions options) {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().newInstance(CoherenceServiceType.type, options);
      }

      public static CoherenceServiceType parse(String xmlAsString) throws XmlException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceServiceType.type, options);
      }

      public static CoherenceServiceType parse(File file) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(file, CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(file, CoherenceServiceType.type, options);
      }

      public static CoherenceServiceType parse(URL u) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(u, CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(u, CoherenceServiceType.type, options);
      }

      public static CoherenceServiceType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(is, CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(is, CoherenceServiceType.type, options);
      }

      public static CoherenceServiceType parse(Reader r) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(r, CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(r, CoherenceServiceType.type, options);
      }

      public static CoherenceServiceType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceServiceType.type, options);
      }

      public static CoherenceServiceType parse(Node node) throws XmlException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(node, CoherenceServiceType.type, (XmlOptions)null);
      }

      public static CoherenceServiceType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(node, CoherenceServiceType.type, options);
      }

      /** @deprecated */
      public static CoherenceServiceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceServiceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceServiceType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceServiceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceServiceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceServiceType.type, options);
      }

      private Factory() {
      }
   }
}
