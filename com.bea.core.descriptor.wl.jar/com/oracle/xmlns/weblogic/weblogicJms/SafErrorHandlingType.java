package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface SafErrorHandlingType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafErrorHandlingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("saferrorhandlingtypeae77type");

   String getPolicy();

   XmlString xgetPolicy();

   boolean isNilPolicy();

   boolean isSetPolicy();

   void setPolicy(String var1);

   void xsetPolicy(XmlString var1);

   void setNilPolicy();

   void unsetPolicy();

   String getLogFormat();

   XmlString xgetLogFormat();

   boolean isNilLogFormat();

   boolean isSetLogFormat();

   void setLogFormat(String var1);

   void xsetLogFormat(XmlString var1);

   void setNilLogFormat();

   void unsetLogFormat();

   String getSafErrorDestination();

   XmlString xgetSafErrorDestination();

   boolean isNilSafErrorDestination();

   boolean isSetSafErrorDestination();

   void setSafErrorDestination(String var1);

   void xsetSafErrorDestination(XmlString var1);

   void setNilSafErrorDestination();

   void unsetSafErrorDestination();

   public static final class Factory {
      public static SafErrorHandlingType newInstance() {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().newInstance(SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType newInstance(XmlOptions options) {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().newInstance(SafErrorHandlingType.type, options);
      }

      public static SafErrorHandlingType parse(String xmlAsString) throws XmlException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafErrorHandlingType.type, options);
      }

      public static SafErrorHandlingType parse(File file) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(file, SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(file, SafErrorHandlingType.type, options);
      }

      public static SafErrorHandlingType parse(URL u) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(u, SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(u, SafErrorHandlingType.type, options);
      }

      public static SafErrorHandlingType parse(InputStream is) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(is, SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(is, SafErrorHandlingType.type, options);
      }

      public static SafErrorHandlingType parse(Reader r) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(r, SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(r, SafErrorHandlingType.type, options);
      }

      public static SafErrorHandlingType parse(XMLStreamReader sr) throws XmlException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(sr, SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(sr, SafErrorHandlingType.type, options);
      }

      public static SafErrorHandlingType parse(Node node) throws XmlException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(node, SafErrorHandlingType.type, (XmlOptions)null);
      }

      public static SafErrorHandlingType parse(Node node, XmlOptions options) throws XmlException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(node, SafErrorHandlingType.type, options);
      }

      /** @deprecated */
      public static SafErrorHandlingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(xis, SafErrorHandlingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafErrorHandlingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafErrorHandlingType)XmlBeans.getContextTypeLoader().parse(xis, SafErrorHandlingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafErrorHandlingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafErrorHandlingType.type, options);
      }

      private Factory() {
      }
   }
}
