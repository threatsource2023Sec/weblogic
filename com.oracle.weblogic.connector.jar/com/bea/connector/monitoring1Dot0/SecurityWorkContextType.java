package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface SecurityWorkContextType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityWorkContextType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("securityworkcontexttypea736type");

   boolean getInboundMappingRequired();

   XmlBoolean xgetInboundMappingRequired();

   boolean isSetInboundMappingRequired();

   void setInboundMappingRequired(boolean var1);

   void xsetInboundMappingRequired(XmlBoolean var1);

   void unsetInboundMappingRequired();

   AnonPrincipalType getCallerPrincipalDefaultMapped();

   boolean isSetCallerPrincipalDefaultMapped();

   void setCallerPrincipalDefaultMapped(AnonPrincipalType var1);

   AnonPrincipalType addNewCallerPrincipalDefaultMapped();

   void unsetCallerPrincipalDefaultMapped();

   InboundCallerPrincipalMappingType[] getCallerPrincipalMappingArray();

   InboundCallerPrincipalMappingType getCallerPrincipalMappingArray(int var1);

   int sizeOfCallerPrincipalMappingArray();

   void setCallerPrincipalMappingArray(InboundCallerPrincipalMappingType[] var1);

   void setCallerPrincipalMappingArray(int var1, InboundCallerPrincipalMappingType var2);

   InboundCallerPrincipalMappingType insertNewCallerPrincipalMapping(int var1);

   InboundCallerPrincipalMappingType addNewCallerPrincipalMapping();

   void removeCallerPrincipalMapping(int var1);

   String getGroupPrincipalDefaultMapped();

   XmlString xgetGroupPrincipalDefaultMapped();

   boolean isSetGroupPrincipalDefaultMapped();

   void setGroupPrincipalDefaultMapped(String var1);

   void xsetGroupPrincipalDefaultMapped(XmlString var1);

   void unsetGroupPrincipalDefaultMapped();

   InboundGroupPrincipalMappingType[] getGroupPrincipalMappingArray();

   InboundGroupPrincipalMappingType getGroupPrincipalMappingArray(int var1);

   int sizeOfGroupPrincipalMappingArray();

   void setGroupPrincipalMappingArray(InboundGroupPrincipalMappingType[] var1);

   void setGroupPrincipalMappingArray(int var1, InboundGroupPrincipalMappingType var2);

   InboundGroupPrincipalMappingType insertNewGroupPrincipalMapping(int var1);

   InboundGroupPrincipalMappingType addNewGroupPrincipalMapping();

   void removeGroupPrincipalMapping(int var1);

   public static final class Factory {
      public static SecurityWorkContextType newInstance() {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().newInstance(SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType newInstance(XmlOptions options) {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().newInstance(SecurityWorkContextType.type, options);
      }

      public static SecurityWorkContextType parse(String xmlAsString) throws XmlException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityWorkContextType.type, options);
      }

      public static SecurityWorkContextType parse(File file) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(file, SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(file, SecurityWorkContextType.type, options);
      }

      public static SecurityWorkContextType parse(URL u) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(u, SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(u, SecurityWorkContextType.type, options);
      }

      public static SecurityWorkContextType parse(InputStream is) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(is, SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(is, SecurityWorkContextType.type, options);
      }

      public static SecurityWorkContextType parse(Reader r) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(r, SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(r, SecurityWorkContextType.type, options);
      }

      public static SecurityWorkContextType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(sr, SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(sr, SecurityWorkContextType.type, options);
      }

      public static SecurityWorkContextType parse(Node node) throws XmlException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(node, SecurityWorkContextType.type, (XmlOptions)null);
      }

      public static SecurityWorkContextType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(node, SecurityWorkContextType.type, options);
      }

      /** @deprecated */
      public static SecurityWorkContextType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(xis, SecurityWorkContextType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityWorkContextType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityWorkContextType)XmlBeans.getContextTypeLoader().parse(xis, SecurityWorkContextType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityWorkContextType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityWorkContextType.type, options);
      }

      private Factory() {
      }
   }
}
