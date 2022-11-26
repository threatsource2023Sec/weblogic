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

public interface DetachOptionsAllType extends DetachStateType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DetachOptionsAllType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("detachoptionsalltype60c2type");

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
      public static DetachOptionsAllType newInstance() {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().newInstance(DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType newInstance(XmlOptions options) {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().newInstance(DetachOptionsAllType.type, options);
      }

      public static DetachOptionsAllType parse(String xmlAsString) throws XmlException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachOptionsAllType.type, options);
      }

      public static DetachOptionsAllType parse(File file) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(file, DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(file, DetachOptionsAllType.type, options);
      }

      public static DetachOptionsAllType parse(URL u) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(u, DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(u, DetachOptionsAllType.type, options);
      }

      public static DetachOptionsAllType parse(InputStream is) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(is, DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(is, DetachOptionsAllType.type, options);
      }

      public static DetachOptionsAllType parse(Reader r) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(r, DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(r, DetachOptionsAllType.type, options);
      }

      public static DetachOptionsAllType parse(XMLStreamReader sr) throws XmlException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(sr, DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(sr, DetachOptionsAllType.type, options);
      }

      public static DetachOptionsAllType parse(Node node) throws XmlException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(node, DetachOptionsAllType.type, (XmlOptions)null);
      }

      public static DetachOptionsAllType parse(Node node, XmlOptions options) throws XmlException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(node, DetachOptionsAllType.type, options);
      }

      /** @deprecated */
      public static DetachOptionsAllType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(xis, DetachOptionsAllType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DetachOptionsAllType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DetachOptionsAllType)XmlBeans.getContextTypeLoader().parse(xis, DetachOptionsAllType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachOptionsAllType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachOptionsAllType.type, options);
      }

      private Factory() {
      }
   }
}
