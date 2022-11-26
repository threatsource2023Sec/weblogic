package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface PortComponentRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PortComponentRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("portcomponentreftypee5a4type");

   FullyQualifiedClassType getServiceEndpointInterface();

   void setServiceEndpointInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServiceEndpointInterface();

   TrueFalseType getEnableMtom();

   boolean isSetEnableMtom();

   void setEnableMtom(TrueFalseType var1);

   TrueFalseType addNewEnableMtom();

   void unsetEnableMtom();

   XsdNonNegativeIntegerType getMtomThreshold();

   boolean isSetMtomThreshold();

   void setMtomThreshold(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMtomThreshold();

   void unsetMtomThreshold();

   AddressingType getAddressing();

   boolean isSetAddressing();

   void setAddressing(AddressingType var1);

   AddressingType addNewAddressing();

   void unsetAddressing();

   RespectBindingType getRespectBinding();

   boolean isSetRespectBinding();

   void setRespectBinding(RespectBindingType var1);

   RespectBindingType addNewRespectBinding();

   void unsetRespectBinding();

   String getPortComponentLink();

   boolean isSetPortComponentLink();

   void setPortComponentLink(String var1);

   String addNewPortComponentLink();

   void unsetPortComponentLink();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PortComponentRefType newInstance() {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().newInstance(PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType newInstance(XmlOptions options) {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().newInstance(PortComponentRefType.type, options);
      }

      public static PortComponentRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortComponentRefType.type, options);
      }

      public static PortComponentRefType parse(File file) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(file, PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(file, PortComponentRefType.type, options);
      }

      public static PortComponentRefType parse(URL u) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(u, PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(u, PortComponentRefType.type, options);
      }

      public static PortComponentRefType parse(InputStream is) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(is, PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(is, PortComponentRefType.type, options);
      }

      public static PortComponentRefType parse(Reader r) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(r, PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(r, PortComponentRefType.type, options);
      }

      public static PortComponentRefType parse(XMLStreamReader sr) throws XmlException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(sr, PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(sr, PortComponentRefType.type, options);
      }

      public static PortComponentRefType parse(Node node) throws XmlException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(node, PortComponentRefType.type, (XmlOptions)null);
      }

      public static PortComponentRefType parse(Node node, XmlOptions options) throws XmlException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(node, PortComponentRefType.type, options);
      }

      /** @deprecated */
      public static PortComponentRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(xis, PortComponentRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PortComponentRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PortComponentRefType)XmlBeans.getContextTypeLoader().parse(xis, PortComponentRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortComponentRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortComponentRefType.type, options);
      }

      private Factory() {
      }
   }
}
