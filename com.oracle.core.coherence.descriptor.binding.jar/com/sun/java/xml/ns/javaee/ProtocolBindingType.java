package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ProtocolBindingType extends XmlAnySimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProtocolBindingType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("protocolbindingtype31fdtype");

   Object getObjectValue();

   void setObjectValue(Object var1);

   /** @deprecated */
   Object objectValue();

   /** @deprecated */
   void objectSet(Object var1);

   SchemaType instanceType();

   public static final class Factory {
      public static ProtocolBindingType newValue(Object obj) {
         return (ProtocolBindingType)ProtocolBindingType.type.newValue(obj);
      }

      public static ProtocolBindingType newInstance() {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().newInstance(ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType newInstance(XmlOptions options) {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().newInstance(ProtocolBindingType.type, options);
      }

      public static ProtocolBindingType parse(java.lang.String xmlAsString) throws XmlException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProtocolBindingType.type, options);
      }

      public static ProtocolBindingType parse(File file) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(file, ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(file, ProtocolBindingType.type, options);
      }

      public static ProtocolBindingType parse(URL u) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(u, ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(u, ProtocolBindingType.type, options);
      }

      public static ProtocolBindingType parse(InputStream is) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(is, ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(is, ProtocolBindingType.type, options);
      }

      public static ProtocolBindingType parse(Reader r) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(r, ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(r, ProtocolBindingType.type, options);
      }

      public static ProtocolBindingType parse(XMLStreamReader sr) throws XmlException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(sr, ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(sr, ProtocolBindingType.type, options);
      }

      public static ProtocolBindingType parse(Node node) throws XmlException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(node, ProtocolBindingType.type, (XmlOptions)null);
      }

      public static ProtocolBindingType parse(Node node, XmlOptions options) throws XmlException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(node, ProtocolBindingType.type, options);
      }

      /** @deprecated */
      public static ProtocolBindingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(xis, ProtocolBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProtocolBindingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProtocolBindingType)XmlBeans.getContextTypeLoader().parse(xis, ProtocolBindingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProtocolBindingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProtocolBindingType.type, options);
      }

      private Factory() {
      }
   }
}
