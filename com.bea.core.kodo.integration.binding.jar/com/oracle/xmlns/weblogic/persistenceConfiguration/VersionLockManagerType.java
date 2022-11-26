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

public interface VersionLockManagerType extends LockManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VersionLockManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("versionlockmanagertypee0datype");

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
      public static VersionLockManagerType newInstance() {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().newInstance(VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType newInstance(XmlOptions options) {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().newInstance(VersionLockManagerType.type, options);
      }

      public static VersionLockManagerType parse(String xmlAsString) throws XmlException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VersionLockManagerType.type, options);
      }

      public static VersionLockManagerType parse(File file) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(file, VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(file, VersionLockManagerType.type, options);
      }

      public static VersionLockManagerType parse(URL u) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(u, VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(u, VersionLockManagerType.type, options);
      }

      public static VersionLockManagerType parse(InputStream is) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(is, VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(is, VersionLockManagerType.type, options);
      }

      public static VersionLockManagerType parse(Reader r) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(r, VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(r, VersionLockManagerType.type, options);
      }

      public static VersionLockManagerType parse(XMLStreamReader sr) throws XmlException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, VersionLockManagerType.type, options);
      }

      public static VersionLockManagerType parse(Node node) throws XmlException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(node, VersionLockManagerType.type, (XmlOptions)null);
      }

      public static VersionLockManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(node, VersionLockManagerType.type, options);
      }

      /** @deprecated */
      public static VersionLockManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, VersionLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VersionLockManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VersionLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, VersionLockManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VersionLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VersionLockManagerType.type, options);
      }

      private Factory() {
      }
   }
}
