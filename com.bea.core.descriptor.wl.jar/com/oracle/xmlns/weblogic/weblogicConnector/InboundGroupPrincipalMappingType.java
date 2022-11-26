package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface InboundGroupPrincipalMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InboundGroupPrincipalMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("inboundgroupprincipalmappingtypedc33type");

   String getEisGroupPrincipal();

   void setEisGroupPrincipal(String var1);

   String addNewEisGroupPrincipal();

   String getMappedGroupPrincipal();

   void setMappedGroupPrincipal(String var1);

   String addNewMappedGroupPrincipal();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InboundGroupPrincipalMappingType newInstance() {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().newInstance(InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType newInstance(XmlOptions options) {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().newInstance(InboundGroupPrincipalMappingType.type, options);
      }

      public static InboundGroupPrincipalMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundGroupPrincipalMappingType.type, options);
      }

      public static InboundGroupPrincipalMappingType parse(File file) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(file, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(file, InboundGroupPrincipalMappingType.type, options);
      }

      public static InboundGroupPrincipalMappingType parse(URL u) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(u, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(u, InboundGroupPrincipalMappingType.type, options);
      }

      public static InboundGroupPrincipalMappingType parse(InputStream is) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(is, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(is, InboundGroupPrincipalMappingType.type, options);
      }

      public static InboundGroupPrincipalMappingType parse(Reader r) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(r, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(r, InboundGroupPrincipalMappingType.type, options);
      }

      public static InboundGroupPrincipalMappingType parse(XMLStreamReader sr) throws XmlException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(sr, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(sr, InboundGroupPrincipalMappingType.type, options);
      }

      public static InboundGroupPrincipalMappingType parse(Node node) throws XmlException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(node, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundGroupPrincipalMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(node, InboundGroupPrincipalMappingType.type, options);
      }

      /** @deprecated */
      public static InboundGroupPrincipalMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xis, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InboundGroupPrincipalMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InboundGroupPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xis, InboundGroupPrincipalMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundGroupPrincipalMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundGroupPrincipalMappingType.type, options);
      }

      private Factory() {
      }
   }
}
