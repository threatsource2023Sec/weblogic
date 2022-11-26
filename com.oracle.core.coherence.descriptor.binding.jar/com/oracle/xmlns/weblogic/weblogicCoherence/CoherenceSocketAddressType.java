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

public interface CoherenceSocketAddressType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceSocketAddressType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherencesocketaddresstype3952type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getAddress();

   XmlString xgetAddress();

   boolean isNilAddress();

   boolean isSetAddress();

   void setAddress(String var1);

   void xsetAddress(XmlString var1);

   void setNilAddress();

   void unsetAddress();

   XsdNonNegativeIntegerType getPort();

   boolean isSetPort();

   void setPort(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewPort();

   void unsetPort();

   public static final class Factory {
      public static CoherenceSocketAddressType newInstance() {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().newInstance(CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType newInstance(XmlOptions options) {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().newInstance(CoherenceSocketAddressType.type, options);
      }

      public static CoherenceSocketAddressType parse(String xmlAsString) throws XmlException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceSocketAddressType.type, options);
      }

      public static CoherenceSocketAddressType parse(File file) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(file, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(file, CoherenceSocketAddressType.type, options);
      }

      public static CoherenceSocketAddressType parse(URL u) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(u, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(u, CoherenceSocketAddressType.type, options);
      }

      public static CoherenceSocketAddressType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(is, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(is, CoherenceSocketAddressType.type, options);
      }

      public static CoherenceSocketAddressType parse(Reader r) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(r, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(r, CoherenceSocketAddressType.type, options);
      }

      public static CoherenceSocketAddressType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceSocketAddressType.type, options);
      }

      public static CoherenceSocketAddressType parse(Node node) throws XmlException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(node, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      public static CoherenceSocketAddressType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(node, CoherenceSocketAddressType.type, options);
      }

      /** @deprecated */
      public static CoherenceSocketAddressType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceSocketAddressType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceSocketAddressType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceSocketAddressType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceSocketAddressType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceSocketAddressType.type, options);
      }

      private Factory() {
      }
   }
}
