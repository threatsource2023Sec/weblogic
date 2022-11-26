package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PersistenceConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("persistenceconfigtype3dadtype");

   boolean getCustomized();

   XmlBoolean xgetCustomized();

   boolean isSetCustomized();

   void setCustomized(boolean var1);

   void xsetCustomized(XmlBoolean var1);

   void unsetCustomized();

   String getDefaultLogicalStoreName();

   boolean isSetDefaultLogicalStoreName();

   void setDefaultLogicalStoreName(String var1);

   String addNewDefaultLogicalStoreName();

   void unsetDefaultLogicalStoreName();

   public static final class Factory {
      public static PersistenceConfigType newInstance() {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().newInstance(PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType newInstance(XmlOptions options) {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().newInstance(PersistenceConfigType.type, options);
      }

      public static PersistenceConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceConfigType.type, options);
      }

      public static PersistenceConfigType parse(File file) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(file, PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(file, PersistenceConfigType.type, options);
      }

      public static PersistenceConfigType parse(URL u) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(u, PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(u, PersistenceConfigType.type, options);
      }

      public static PersistenceConfigType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(is, PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(is, PersistenceConfigType.type, options);
      }

      public static PersistenceConfigType parse(Reader r) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(r, PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(r, PersistenceConfigType.type, options);
      }

      public static PersistenceConfigType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceConfigType.type, options);
      }

      public static PersistenceConfigType parse(Node node) throws XmlException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(node, PersistenceConfigType.type, (XmlOptions)null);
      }

      public static PersistenceConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(node, PersistenceConfigType.type, options);
      }

      /** @deprecated */
      public static PersistenceConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceConfigType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceConfigType.type, options);
      }

      private Factory() {
      }
   }
}
