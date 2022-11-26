package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ConcurrentMethodType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConcurrentMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("concurrentmethodtyped8e6type");

   NamedMethodType getMethod();

   void setMethod(NamedMethodType var1);

   NamedMethodType addNewMethod();

   ConcurrentLockTypeType getLock();

   boolean isSetLock();

   void setLock(ConcurrentLockTypeType var1);

   ConcurrentLockTypeType addNewLock();

   void unsetLock();

   AccessTimeoutType getAccessTimeout();

   boolean isSetAccessTimeout();

   void setAccessTimeout(AccessTimeoutType var1);

   AccessTimeoutType addNewAccessTimeout();

   void unsetAccessTimeout();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConcurrentMethodType newInstance() {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().newInstance(ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType newInstance(XmlOptions options) {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().newInstance(ConcurrentMethodType.type, options);
      }

      public static ConcurrentMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConcurrentMethodType.type, options);
      }

      public static ConcurrentMethodType parse(File file) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(file, ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(file, ConcurrentMethodType.type, options);
      }

      public static ConcurrentMethodType parse(URL u) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(u, ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(u, ConcurrentMethodType.type, options);
      }

      public static ConcurrentMethodType parse(InputStream is) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(is, ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(is, ConcurrentMethodType.type, options);
      }

      public static ConcurrentMethodType parse(Reader r) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(r, ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(r, ConcurrentMethodType.type, options);
      }

      public static ConcurrentMethodType parse(XMLStreamReader sr) throws XmlException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(sr, ConcurrentMethodType.type, options);
      }

      public static ConcurrentMethodType parse(Node node) throws XmlException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(node, ConcurrentMethodType.type, (XmlOptions)null);
      }

      public static ConcurrentMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(node, ConcurrentMethodType.type, options);
      }

      /** @deprecated */
      public static ConcurrentMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrentMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConcurrentMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConcurrentMethodType)XmlBeans.getContextTypeLoader().parse(xis, ConcurrentMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrentMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConcurrentMethodType.type, options);
      }

      private Factory() {
      }
   }
}
