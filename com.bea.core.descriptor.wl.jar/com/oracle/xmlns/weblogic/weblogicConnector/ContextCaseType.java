package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ContextCaseType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ContextCaseType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("contextcasetypea026type");

   XsdStringType getUserName();

   boolean isSetUserName();

   void setUserName(XsdStringType var1);

   XsdStringType addNewUserName();

   void unsetUserName();

   GroupNameType getGroupName();

   boolean isSetGroupName();

   void setGroupName(GroupNameType var1);

   GroupNameType addNewGroupName();

   void unsetGroupName();

   ResponseTimeRequestClassType getResponseTimeRequestClass();

   boolean isSetResponseTimeRequestClass();

   void setResponseTimeRequestClass(ResponseTimeRequestClassType var1);

   ResponseTimeRequestClassType addNewResponseTimeRequestClass();

   void unsetResponseTimeRequestClass();

   FairShareRequestClassType getFairShareRequestClass();

   boolean isSetFairShareRequestClass();

   void setFairShareRequestClass(FairShareRequestClassType var1);

   FairShareRequestClassType addNewFairShareRequestClass();

   void unsetFairShareRequestClass();

   XsdStringType getRequestClassName();

   boolean isSetRequestClassName();

   void setRequestClassName(XsdStringType var1);

   XsdStringType addNewRequestClassName();

   void unsetRequestClassName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ContextCaseType newInstance() {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().newInstance(ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType newInstance(XmlOptions options) {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().newInstance(ContextCaseType.type, options);
      }

      public static ContextCaseType parse(String xmlAsString) throws XmlException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContextCaseType.type, options);
      }

      public static ContextCaseType parse(File file) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(file, ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(file, ContextCaseType.type, options);
      }

      public static ContextCaseType parse(URL u) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(u, ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(u, ContextCaseType.type, options);
      }

      public static ContextCaseType parse(InputStream is) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(is, ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(is, ContextCaseType.type, options);
      }

      public static ContextCaseType parse(Reader r) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(r, ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(r, ContextCaseType.type, options);
      }

      public static ContextCaseType parse(XMLStreamReader sr) throws XmlException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(sr, ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(sr, ContextCaseType.type, options);
      }

      public static ContextCaseType parse(Node node) throws XmlException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(node, ContextCaseType.type, (XmlOptions)null);
      }

      public static ContextCaseType parse(Node node, XmlOptions options) throws XmlException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(node, ContextCaseType.type, options);
      }

      /** @deprecated */
      public static ContextCaseType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(xis, ContextCaseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ContextCaseType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ContextCaseType)XmlBeans.getContextTypeLoader().parse(xis, ContextCaseType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContextCaseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContextCaseType.type, options);
      }

      private Factory() {
      }
   }
}
