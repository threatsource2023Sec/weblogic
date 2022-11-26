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

public interface PessimisticLockManagerType extends LockManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PessimisticLockManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("pessimisticlockmanagertype93cdtype");

   boolean getVersionCheckOnReadLock();

   XmlBoolean xgetVersionCheckOnReadLock();

   boolean isSetVersionCheckOnReadLock();

   void setVersionCheckOnReadLock(boolean var1);

   void xsetVersionCheckOnReadLock(XmlBoolean var1);

   void unsetVersionCheckOnReadLock();

   boolean getVersionUpdateOnWriteLock();

   XmlBoolean xgetVersionUpdateOnWriteLock();

   boolean isSetVersionUpdateOnWriteLock();

   void setVersionUpdateOnWriteLock(boolean var1);

   void xsetVersionUpdateOnWriteLock(XmlBoolean var1);

   void unsetVersionUpdateOnWriteLock();

   public static final class Factory {
      public static PessimisticLockManagerType newInstance() {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().newInstance(PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType newInstance(XmlOptions options) {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().newInstance(PessimisticLockManagerType.type, options);
      }

      public static PessimisticLockManagerType parse(String xmlAsString) throws XmlException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PessimisticLockManagerType.type, options);
      }

      public static PessimisticLockManagerType parse(File file) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(file, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(file, PessimisticLockManagerType.type, options);
      }

      public static PessimisticLockManagerType parse(URL u) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(u, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(u, PessimisticLockManagerType.type, options);
      }

      public static PessimisticLockManagerType parse(InputStream is) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(is, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(is, PessimisticLockManagerType.type, options);
      }

      public static PessimisticLockManagerType parse(Reader r) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(r, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(r, PessimisticLockManagerType.type, options);
      }

      public static PessimisticLockManagerType parse(XMLStreamReader sr) throws XmlException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, PessimisticLockManagerType.type, options);
      }

      public static PessimisticLockManagerType parse(Node node) throws XmlException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(node, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      public static PessimisticLockManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(node, PessimisticLockManagerType.type, options);
      }

      /** @deprecated */
      public static PessimisticLockManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PessimisticLockManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PessimisticLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, PessimisticLockManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PessimisticLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PessimisticLockManagerType.type, options);
      }

      private Factory() {
      }
   }
}
