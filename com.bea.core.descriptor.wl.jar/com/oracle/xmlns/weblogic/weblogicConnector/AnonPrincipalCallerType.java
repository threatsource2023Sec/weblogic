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

public interface AnonPrincipalCallerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnonPrincipalCallerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("anonprincipalcallertype39d1type");

   TrueFalseType getUseAnonymousIdentity();

   boolean isSetUseAnonymousIdentity();

   void setUseAnonymousIdentity(TrueFalseType var1);

   TrueFalseType addNewUseAnonymousIdentity();

   void unsetUseAnonymousIdentity();

   String getPrincipalName();

   boolean isSetPrincipalName();

   void setPrincipalName(String var1);

   String addNewPrincipalName();

   void unsetPrincipalName();

   TrueFalseType getUseCallerIdentity();

   boolean isSetUseCallerIdentity();

   void setUseCallerIdentity(TrueFalseType var1);

   TrueFalseType addNewUseCallerIdentity();

   void unsetUseCallerIdentity();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AnonPrincipalCallerType newInstance() {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().newInstance(AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType newInstance(XmlOptions options) {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().newInstance(AnonPrincipalCallerType.type, options);
      }

      public static AnonPrincipalCallerType parse(java.lang.String xmlAsString) throws XmlException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnonPrincipalCallerType.type, options);
      }

      public static AnonPrincipalCallerType parse(File file) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(file, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(file, AnonPrincipalCallerType.type, options);
      }

      public static AnonPrincipalCallerType parse(URL u) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(u, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(u, AnonPrincipalCallerType.type, options);
      }

      public static AnonPrincipalCallerType parse(InputStream is) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(is, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(is, AnonPrincipalCallerType.type, options);
      }

      public static AnonPrincipalCallerType parse(Reader r) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(r, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(r, AnonPrincipalCallerType.type, options);
      }

      public static AnonPrincipalCallerType parse(XMLStreamReader sr) throws XmlException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(sr, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(sr, AnonPrincipalCallerType.type, options);
      }

      public static AnonPrincipalCallerType parse(Node node) throws XmlException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(node, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      public static AnonPrincipalCallerType parse(Node node, XmlOptions options) throws XmlException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(node, AnonPrincipalCallerType.type, options);
      }

      /** @deprecated */
      public static AnonPrincipalCallerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(xis, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnonPrincipalCallerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnonPrincipalCallerType)XmlBeans.getContextTypeLoader().parse(xis, AnonPrincipalCallerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnonPrincipalCallerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnonPrincipalCallerType.type, options);
      }

      private Factory() {
      }
   }
}
