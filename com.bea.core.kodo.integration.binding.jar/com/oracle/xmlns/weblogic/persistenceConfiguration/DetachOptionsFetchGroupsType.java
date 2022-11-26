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

public interface DetachOptionsFetchGroupsType extends DetachStateType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DetachOptionsFetchGroupsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("detachoptionsfetchgroupstypeb3dctype");

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
      public static DetachOptionsFetchGroupsType newInstance() {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().newInstance(DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType newInstance(XmlOptions options) {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().newInstance(DetachOptionsFetchGroupsType.type, options);
      }

      public static DetachOptionsFetchGroupsType parse(String xmlAsString) throws XmlException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DetachOptionsFetchGroupsType.type, options);
      }

      public static DetachOptionsFetchGroupsType parse(File file) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(file, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(file, DetachOptionsFetchGroupsType.type, options);
      }

      public static DetachOptionsFetchGroupsType parse(URL u) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(u, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(u, DetachOptionsFetchGroupsType.type, options);
      }

      public static DetachOptionsFetchGroupsType parse(InputStream is) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(is, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(is, DetachOptionsFetchGroupsType.type, options);
      }

      public static DetachOptionsFetchGroupsType parse(Reader r) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(r, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(r, DetachOptionsFetchGroupsType.type, options);
      }

      public static DetachOptionsFetchGroupsType parse(XMLStreamReader sr) throws XmlException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(sr, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(sr, DetachOptionsFetchGroupsType.type, options);
      }

      public static DetachOptionsFetchGroupsType parse(Node node) throws XmlException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(node, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      public static DetachOptionsFetchGroupsType parse(Node node, XmlOptions options) throws XmlException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(node, DetachOptionsFetchGroupsType.type, options);
      }

      /** @deprecated */
      public static DetachOptionsFetchGroupsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(xis, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DetachOptionsFetchGroupsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DetachOptionsFetchGroupsType)XmlBeans.getContextTypeLoader().parse(xis, DetachOptionsFetchGroupsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachOptionsFetchGroupsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DetachOptionsFetchGroupsType.type, options);
      }

      private Factory() {
      }
   }
}
