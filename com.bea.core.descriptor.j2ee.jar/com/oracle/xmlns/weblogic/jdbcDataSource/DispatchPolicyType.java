package com.oracle.xmlns.weblogic.jdbcDataSource;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface DispatchPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DispatchPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("dispatchpolicytypec54dtype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DispatchPolicyType newInstance() {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().newInstance(DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType newInstance(XmlOptions options) {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().newInstance(DispatchPolicyType.type, options);
      }

      public static DispatchPolicyType parse(String xmlAsString) throws XmlException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DispatchPolicyType.type, options);
      }

      public static DispatchPolicyType parse(File file) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(file, DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(file, DispatchPolicyType.type, options);
      }

      public static DispatchPolicyType parse(URL u) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(u, DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(u, DispatchPolicyType.type, options);
      }

      public static DispatchPolicyType parse(InputStream is) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(is, DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(is, DispatchPolicyType.type, options);
      }

      public static DispatchPolicyType parse(Reader r) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(r, DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(r, DispatchPolicyType.type, options);
      }

      public static DispatchPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(sr, DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(sr, DispatchPolicyType.type, options);
      }

      public static DispatchPolicyType parse(Node node) throws XmlException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(node, DispatchPolicyType.type, (XmlOptions)null);
      }

      public static DispatchPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(node, DispatchPolicyType.type, options);
      }

      /** @deprecated */
      public static DispatchPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xis, DispatchPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DispatchPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DispatchPolicyType)XmlBeans.getContextTypeLoader().parse(xis, DispatchPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DispatchPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DispatchPolicyType.type, options);
      }

      private Factory() {
      }
   }
}
