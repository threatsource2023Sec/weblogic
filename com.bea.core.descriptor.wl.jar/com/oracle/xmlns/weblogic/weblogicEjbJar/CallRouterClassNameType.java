package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface CallRouterClassNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CallRouterClassNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("callrouterclassnametype40cbtype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CallRouterClassNameType newInstance() {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().newInstance(CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType newInstance(XmlOptions options) {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().newInstance(CallRouterClassNameType.type, options);
      }

      public static CallRouterClassNameType parse(String xmlAsString) throws XmlException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CallRouterClassNameType.type, options);
      }

      public static CallRouterClassNameType parse(File file) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(file, CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(file, CallRouterClassNameType.type, options);
      }

      public static CallRouterClassNameType parse(URL u) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(u, CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(u, CallRouterClassNameType.type, options);
      }

      public static CallRouterClassNameType parse(InputStream is) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(is, CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(is, CallRouterClassNameType.type, options);
      }

      public static CallRouterClassNameType parse(Reader r) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(r, CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(r, CallRouterClassNameType.type, options);
      }

      public static CallRouterClassNameType parse(XMLStreamReader sr) throws XmlException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(sr, CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(sr, CallRouterClassNameType.type, options);
      }

      public static CallRouterClassNameType parse(Node node) throws XmlException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(node, CallRouterClassNameType.type, (XmlOptions)null);
      }

      public static CallRouterClassNameType parse(Node node, XmlOptions options) throws XmlException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(node, CallRouterClassNameType.type, options);
      }

      /** @deprecated */
      public static CallRouterClassNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(xis, CallRouterClassNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CallRouterClassNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CallRouterClassNameType)XmlBeans.getContextTypeLoader().parse(xis, CallRouterClassNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CallRouterClassNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CallRouterClassNameType.type, options);
      }

      private Factory() {
      }
   }
}
