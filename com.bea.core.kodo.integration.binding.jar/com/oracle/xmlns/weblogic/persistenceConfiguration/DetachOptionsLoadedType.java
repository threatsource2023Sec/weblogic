package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DetachOptionsLoadedType extends DetachStateType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DetachOptionsLoadedType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("detachoptionsloadedtype0c5etype");

   boolean getDetachedStateManager();

   XmlBoolean xgetDetachedStateManager();

   boolean isSetDetachedStateManager();

   void setDetachedStateManager(boolean var1);

   void xsetDetachedStateManager(XmlBoolean var1);

   void unsetDetachedStateManager();

   boolean getDetachedStateTransient();

   XmlBoolean xgetDetachedStateTransient();

   boolean isSetDetachedStateTransient();

   void setDetachedStateTransient(boolean var1);

   void xsetDetachedStateTransient(XmlBoolean var1);

   void unsetDetachedStateTransient();

   boolean getAccessUnloaded();

   XmlBoolean xgetAccessUnloaded();

   boolean isSetAccessUnloaded();

   void setAccessUnloaded(boolean var1);

   void xsetAccessUnloaded(XmlBoolean var1);

   void unsetAccessUnloaded();

   boolean getDetachedStateField();

   XmlBoolean xgetDetachedStateField();

   boolean isSetDetachedStateField();

   void setDetachedStateField(boolean var1);

   void xsetDetachedStateField(XmlBoolean var1);

   void unsetDetachedStateField();

   public static final class Factory {
      public static DetachOptionsLoadedType newInstance() {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().newInstance(DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType newInstance(XmlOptions options) {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().newInstance(DetachOptionsLoadedType.type, options);
      }

      public static DetachOptionsLoadedType parse(String xmlAsString) throws XmlException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachOptionsLoadedType.type, options);
      }

      public static DetachOptionsLoadedType parse(File file) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(file, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(file, DetachOptionsLoadedType.type, options);
      }

      public static DetachOptionsLoadedType parse(URL u) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(u, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(u, DetachOptionsLoadedType.type, options);
      }

      public static DetachOptionsLoadedType parse(InputStream is) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(is, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(is, DetachOptionsLoadedType.type, options);
      }

      public static DetachOptionsLoadedType parse(Reader r) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(r, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(r, DetachOptionsLoadedType.type, options);
      }

      public static DetachOptionsLoadedType parse(XMLStreamReader sr) throws XmlException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(sr, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(sr, DetachOptionsLoadedType.type, options);
      }

      public static DetachOptionsLoadedType parse(Node node) throws XmlException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(node, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      public static DetachOptionsLoadedType parse(Node node, XmlOptions options) throws XmlException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(node, DetachOptionsLoadedType.type, options);
      }

      /** @deprecated */
      public static DetachOptionsLoadedType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(xis, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DetachOptionsLoadedType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DetachOptionsLoadedType)XmlBeans.getContextTypeLoader().parse(xis, DetachOptionsLoadedType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachOptionsLoadedType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachOptionsLoadedType.type, options);
      }

      private Factory() {
      }
   }
}
