package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNMTOKEN;
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

public interface CoherenceInitParamType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceInitParamType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherenceinitparamtypeaa48type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getParamType();

   XmlString xgetParamType();

   boolean isSetParamType();

   void setParamType(String var1);

   void xsetParamType(XmlString var1);

   void unsetParamType();

   String getParamValue();

   XmlString xgetParamValue();

   boolean isSetParamValue();

   void setParamValue(String var1);

   void xsetParamValue(XmlString var1);

   void unsetParamValue();

   String getId();

   XmlNMTOKEN xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlNMTOKEN var1);

   void unsetId();

   public static final class Factory {
      public static CoherenceInitParamType newInstance() {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().newInstance(CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType newInstance(XmlOptions options) {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().newInstance(CoherenceInitParamType.type, options);
      }

      public static CoherenceInitParamType parse(String xmlAsString) throws XmlException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceInitParamType.type, options);
      }

      public static CoherenceInitParamType parse(File file) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(file, CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(file, CoherenceInitParamType.type, options);
      }

      public static CoherenceInitParamType parse(URL u) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(u, CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(u, CoherenceInitParamType.type, options);
      }

      public static CoherenceInitParamType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(is, CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(is, CoherenceInitParamType.type, options);
      }

      public static CoherenceInitParamType parse(Reader r) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(r, CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(r, CoherenceInitParamType.type, options);
      }

      public static CoherenceInitParamType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceInitParamType.type, options);
      }

      public static CoherenceInitParamType parse(Node node) throws XmlException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(node, CoherenceInitParamType.type, (XmlOptions)null);
      }

      public static CoherenceInitParamType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(node, CoherenceInitParamType.type, options);
      }

      /** @deprecated */
      public static CoherenceInitParamType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceInitParamType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceInitParamType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceInitParamType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceInitParamType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceInitParamType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceInitParamType.type, options);
      }

      private Factory() {
      }
   }
}
