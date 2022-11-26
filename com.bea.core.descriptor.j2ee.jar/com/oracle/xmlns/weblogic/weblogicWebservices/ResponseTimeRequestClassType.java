package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
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

public interface ResponseTimeRequestClassType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResponseTimeRequestClassType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("responsetimerequestclasstype3311type");

   XsdStringType getName();

   void setName(XsdStringType var1);

   XsdStringType addNewName();

   XsdIntegerType getGoalMs();

   void setGoalMs(XsdIntegerType var1);

   XsdIntegerType addNewGoalMs();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResponseTimeRequestClassType newInstance() {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().newInstance(ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType newInstance(XmlOptions options) {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().newInstance(ResponseTimeRequestClassType.type, options);
      }

      public static ResponseTimeRequestClassType parse(String xmlAsString) throws XmlException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResponseTimeRequestClassType.type, options);
      }

      public static ResponseTimeRequestClassType parse(File file) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(file, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(file, ResponseTimeRequestClassType.type, options);
      }

      public static ResponseTimeRequestClassType parse(URL u) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(u, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(u, ResponseTimeRequestClassType.type, options);
      }

      public static ResponseTimeRequestClassType parse(InputStream is) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(is, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(is, ResponseTimeRequestClassType.type, options);
      }

      public static ResponseTimeRequestClassType parse(Reader r) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(r, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(r, ResponseTimeRequestClassType.type, options);
      }

      public static ResponseTimeRequestClassType parse(XMLStreamReader sr) throws XmlException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(sr, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(sr, ResponseTimeRequestClassType.type, options);
      }

      public static ResponseTimeRequestClassType parse(Node node) throws XmlException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(node, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      public static ResponseTimeRequestClassType parse(Node node, XmlOptions options) throws XmlException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(node, ResponseTimeRequestClassType.type, options);
      }

      /** @deprecated */
      public static ResponseTimeRequestClassType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(xis, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResponseTimeRequestClassType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResponseTimeRequestClassType)XmlBeans.getContextTypeLoader().parse(xis, ResponseTimeRequestClassType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResponseTimeRequestClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResponseTimeRequestClassType.type, options);
      }

      private Factory() {
      }
   }
}
