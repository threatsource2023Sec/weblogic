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

public interface AnonPrincipalType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnonPrincipalType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("anonprincipaltype4197type");

   boolean getUseAnonymousIdentity();

   XmlBoolean xgetUseAnonymousIdentity();

   boolean isSetUseAnonymousIdentity();

   void setUseAnonymousIdentity(boolean var1);

   void xsetUseAnonymousIdentity(XmlBoolean var1);

   void unsetUseAnonymousIdentity();

   String getPrincipalName();

   XmlString xgetPrincipalName();

   boolean isSetPrincipalName();

   void setPrincipalName(String var1);

   void xsetPrincipalName(XmlString var1);

   void unsetPrincipalName();

   public static final class Factory {
      public static AnonPrincipalType newInstance() {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().newInstance(AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType newInstance(XmlOptions options) {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().newInstance(AnonPrincipalType.type, options);
      }

      public static AnonPrincipalType parse(String xmlAsString) throws XmlException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnonPrincipalType.type, options);
      }

      public static AnonPrincipalType parse(File file) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(file, AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(file, AnonPrincipalType.type, options);
      }

      public static AnonPrincipalType parse(URL u) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(u, AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(u, AnonPrincipalType.type, options);
      }

      public static AnonPrincipalType parse(InputStream is) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(is, AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(is, AnonPrincipalType.type, options);
      }

      public static AnonPrincipalType parse(Reader r) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(r, AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(r, AnonPrincipalType.type, options);
      }

      public static AnonPrincipalType parse(XMLStreamReader sr) throws XmlException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(sr, AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(sr, AnonPrincipalType.type, options);
      }

      public static AnonPrincipalType parse(Node node) throws XmlException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(node, AnonPrincipalType.type, (XmlOptions)null);
      }

      public static AnonPrincipalType parse(Node node, XmlOptions options) throws XmlException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(node, AnonPrincipalType.type, options);
      }

      /** @deprecated */
      public static AnonPrincipalType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(xis, AnonPrincipalType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnonPrincipalType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnonPrincipalType)XmlBeans.getContextTypeLoader().parse(xis, AnonPrincipalType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnonPrincipalType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnonPrincipalType.type, options);
      }

      private Factory() {
      }
   }
}
