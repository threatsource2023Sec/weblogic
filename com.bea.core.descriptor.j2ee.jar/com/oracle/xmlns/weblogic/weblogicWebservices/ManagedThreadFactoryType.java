package com.oracle.xmlns.weblogic.weblogicWebservices;

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

public interface ManagedThreadFactoryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ManagedThreadFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("managedthreadfactorytypedaf2type");

   DispatchPolicyType getName();

   void setName(DispatchPolicyType var1);

   DispatchPolicyType addNewName();

   XsdNonNegativeIntegerType getMaxConcurrentNewThreads();

   boolean isSetMaxConcurrentNewThreads();

   void setMaxConcurrentNewThreads(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMaxConcurrentNewThreads();

   void unsetMaxConcurrentNewThreads();

   XsdNonNegativeIntegerType getPriority();

   boolean isSetPriority();

   void setPriority(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewPriority();

   void unsetPriority();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ManagedThreadFactoryType newInstance() {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().newInstance(ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType newInstance(XmlOptions options) {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().newInstance(ManagedThreadFactoryType.type, options);
      }

      public static ManagedThreadFactoryType parse(String xmlAsString) throws XmlException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ManagedThreadFactoryType.type, options);
      }

      public static ManagedThreadFactoryType parse(File file) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(file, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(file, ManagedThreadFactoryType.type, options);
      }

      public static ManagedThreadFactoryType parse(URL u) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(u, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(u, ManagedThreadFactoryType.type, options);
      }

      public static ManagedThreadFactoryType parse(InputStream is) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(is, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(is, ManagedThreadFactoryType.type, options);
      }

      public static ManagedThreadFactoryType parse(Reader r) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(r, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(r, ManagedThreadFactoryType.type, options);
      }

      public static ManagedThreadFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(sr, ManagedThreadFactoryType.type, options);
      }

      public static ManagedThreadFactoryType parse(Node node) throws XmlException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(node, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      public static ManagedThreadFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(node, ManagedThreadFactoryType.type, options);
      }

      /** @deprecated */
      public static ManagedThreadFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ManagedThreadFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ManagedThreadFactoryType)XmlBeans.getContextTypeLoader().parse(xis, ManagedThreadFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedThreadFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ManagedThreadFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
