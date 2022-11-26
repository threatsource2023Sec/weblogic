package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PoolType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PoolType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("pooltype2cd2type");

   XsdNonNegativeIntegerType getMaxBeansInFreePool();

   boolean isSetMaxBeansInFreePool();

   void setMaxBeansInFreePool(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxBeansInFreePool();

   void unsetMaxBeansInFreePool();

   XsdNonNegativeIntegerType getInitialBeansInFreePool();

   boolean isSetInitialBeansInFreePool();

   void setInitialBeansInFreePool(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewInitialBeansInFreePool();

   void unsetInitialBeansInFreePool();

   XsdNonNegativeIntegerType getIdleTimeoutSeconds();

   boolean isSetIdleTimeoutSeconds();

   void setIdleTimeoutSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewIdleTimeoutSeconds();

   void unsetIdleTimeoutSeconds();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PoolType newInstance() {
         return (PoolType)XmlBeans.getContextTypeLoader().newInstance(PoolType.type, (XmlOptions)null);
      }

      public static PoolType newInstance(XmlOptions options) {
         return (PoolType)XmlBeans.getContextTypeLoader().newInstance(PoolType.type, options);
      }

      public static PoolType parse(String xmlAsString) throws XmlException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolType.type, (XmlOptions)null);
      }

      public static PoolType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolType.type, options);
      }

      public static PoolType parse(File file) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(file, PoolType.type, (XmlOptions)null);
      }

      public static PoolType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(file, PoolType.type, options);
      }

      public static PoolType parse(URL u) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(u, PoolType.type, (XmlOptions)null);
      }

      public static PoolType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(u, PoolType.type, options);
      }

      public static PoolType parse(InputStream is) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(is, PoolType.type, (XmlOptions)null);
      }

      public static PoolType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(is, PoolType.type, options);
      }

      public static PoolType parse(Reader r) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(r, PoolType.type, (XmlOptions)null);
      }

      public static PoolType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(r, PoolType.type, options);
      }

      public static PoolType parse(XMLStreamReader sr) throws XmlException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(sr, PoolType.type, (XmlOptions)null);
      }

      public static PoolType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(sr, PoolType.type, options);
      }

      public static PoolType parse(Node node) throws XmlException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(node, PoolType.type, (XmlOptions)null);
      }

      public static PoolType parse(Node node, XmlOptions options) throws XmlException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(node, PoolType.type, options);
      }

      /** @deprecated */
      public static PoolType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(xis, PoolType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PoolType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PoolType)XmlBeans.getContextTypeLoader().parse(xis, PoolType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolType.type, options);
      }

      private Factory() {
      }
   }
}
