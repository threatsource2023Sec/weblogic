package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface LifecycleCallbackType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LifecycleCallbackType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("lifecyclecallbacktypedcb5type");

   FullyQualifiedClassType getLifecycleCallbackClass();

   boolean isSetLifecycleCallbackClass();

   void setLifecycleCallbackClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewLifecycleCallbackClass();

   void unsetLifecycleCallbackClass();

   JavaIdentifierType getLifecycleCallbackMethod();

   void setLifecycleCallbackMethod(JavaIdentifierType var1);

   JavaIdentifierType addNewLifecycleCallbackMethod();

   public static final class Factory {
      public static LifecycleCallbackType newInstance() {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().newInstance(LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType newInstance(XmlOptions options) {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().newInstance(LifecycleCallbackType.type, options);
      }

      public static LifecycleCallbackType parse(java.lang.String xmlAsString) throws XmlException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LifecycleCallbackType.type, options);
      }

      public static LifecycleCallbackType parse(File file) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(file, LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(file, LifecycleCallbackType.type, options);
      }

      public static LifecycleCallbackType parse(URL u) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(u, LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(u, LifecycleCallbackType.type, options);
      }

      public static LifecycleCallbackType parse(InputStream is) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(is, LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(is, LifecycleCallbackType.type, options);
      }

      public static LifecycleCallbackType parse(Reader r) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(r, LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(r, LifecycleCallbackType.type, options);
      }

      public static LifecycleCallbackType parse(XMLStreamReader sr) throws XmlException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(sr, LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(sr, LifecycleCallbackType.type, options);
      }

      public static LifecycleCallbackType parse(Node node) throws XmlException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(node, LifecycleCallbackType.type, (XmlOptions)null);
      }

      public static LifecycleCallbackType parse(Node node, XmlOptions options) throws XmlException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(node, LifecycleCallbackType.type, options);
      }

      /** @deprecated */
      public static LifecycleCallbackType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(xis, LifecycleCallbackType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LifecycleCallbackType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LifecycleCallbackType)XmlBeans.getContextTypeLoader().parse(xis, LifecycleCallbackType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LifecycleCallbackType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LifecycleCallbackType.type, options);
      }

      private Factory() {
      }
   }
}
