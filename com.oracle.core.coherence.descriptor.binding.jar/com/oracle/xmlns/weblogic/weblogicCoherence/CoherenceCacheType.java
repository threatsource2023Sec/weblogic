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

public interface CoherenceCacheType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceCacheType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherencecachetype0936type");

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
      public static CoherenceCacheType newInstance() {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().newInstance(CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType newInstance(XmlOptions options) {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().newInstance(CoherenceCacheType.type, options);
      }

      public static CoherenceCacheType parse(String xmlAsString) throws XmlException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceCacheType.type, options);
      }

      public static CoherenceCacheType parse(File file) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(file, CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(file, CoherenceCacheType.type, options);
      }

      public static CoherenceCacheType parse(URL u) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(u, CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(u, CoherenceCacheType.type, options);
      }

      public static CoherenceCacheType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(is, CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(is, CoherenceCacheType.type, options);
      }

      public static CoherenceCacheType parse(Reader r) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(r, CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(r, CoherenceCacheType.type, options);
      }

      public static CoherenceCacheType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceCacheType.type, options);
      }

      public static CoherenceCacheType parse(Node node) throws XmlException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(node, CoherenceCacheType.type, (XmlOptions)null);
      }

      public static CoherenceCacheType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(node, CoherenceCacheType.type, options);
      }

      /** @deprecated */
      public static CoherenceCacheType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceCacheType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceCacheType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceCacheType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceCacheType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceCacheType.type, options);
      }

      private Factory() {
      }
   }
}
