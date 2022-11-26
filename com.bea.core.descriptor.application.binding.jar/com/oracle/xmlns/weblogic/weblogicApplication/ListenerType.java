package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface ListenerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("listenertypee80ftype");

   String getListenerClass();

   XmlString xgetListenerClass();

   void setListenerClass(String var1);

   void xsetListenerClass(XmlString var1);

   String getListenerUri();

   XmlString xgetListenerUri();

   boolean isSetListenerUri();

   void setListenerUri(String var1);

   void xsetListenerUri(XmlString var1);

   void unsetListenerUri();

   String getRunAsPrincipalName();

   XmlString xgetRunAsPrincipalName();

   boolean isSetRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);

   void xsetRunAsPrincipalName(XmlString var1);

   void unsetRunAsPrincipalName();

   public static final class Factory {
      public static ListenerType newInstance() {
         return (ListenerType)XmlBeans.getContextTypeLoader().newInstance(ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType newInstance(XmlOptions options) {
         return (ListenerType)XmlBeans.getContextTypeLoader().newInstance(ListenerType.type, options);
      }

      public static ListenerType parse(String xmlAsString) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ListenerType.type, options);
      }

      public static ListenerType parse(File file) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(file, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(file, ListenerType.type, options);
      }

      public static ListenerType parse(URL u) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(u, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(u, ListenerType.type, options);
      }

      public static ListenerType parse(InputStream is) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(is, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(is, ListenerType.type, options);
      }

      public static ListenerType parse(Reader r) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(r, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(r, ListenerType.type, options);
      }

      public static ListenerType parse(XMLStreamReader sr) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(sr, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(sr, ListenerType.type, options);
      }

      public static ListenerType parse(Node node) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(node, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(node, ListenerType.type, options);
      }

      /** @deprecated */
      public static ListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xis, ListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xis, ListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListenerType.type, options);
      }

      private Factory() {
      }
   }
}
