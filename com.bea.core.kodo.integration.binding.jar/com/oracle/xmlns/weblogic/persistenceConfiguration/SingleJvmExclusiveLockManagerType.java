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

public interface SingleJvmExclusiveLockManagerType extends LockManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SingleJvmExclusiveLockManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("singlejvmexclusivelockmanagertype77a1type");

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
      public static SingleJvmExclusiveLockManagerType newInstance() {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().newInstance(SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType newInstance(XmlOptions options) {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().newInstance(SingleJvmExclusiveLockManagerType.type, options);
      }

      public static SingleJvmExclusiveLockManagerType parse(String xmlAsString) throws XmlException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingleJvmExclusiveLockManagerType.type, options);
      }

      public static SingleJvmExclusiveLockManagerType parse(File file) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(file, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(file, SingleJvmExclusiveLockManagerType.type, options);
      }

      public static SingleJvmExclusiveLockManagerType parse(URL u) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(u, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(u, SingleJvmExclusiveLockManagerType.type, options);
      }

      public static SingleJvmExclusiveLockManagerType parse(InputStream is) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(is, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(is, SingleJvmExclusiveLockManagerType.type, options);
      }

      public static SingleJvmExclusiveLockManagerType parse(Reader r) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(r, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(r, SingleJvmExclusiveLockManagerType.type, options);
      }

      public static SingleJvmExclusiveLockManagerType parse(XMLStreamReader sr) throws XmlException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(sr, SingleJvmExclusiveLockManagerType.type, options);
      }

      public static SingleJvmExclusiveLockManagerType parse(Node node) throws XmlException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(node, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      public static SingleJvmExclusiveLockManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(node, SingleJvmExclusiveLockManagerType.type, options);
      }

      /** @deprecated */
      public static SingleJvmExclusiveLockManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SingleJvmExclusiveLockManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SingleJvmExclusiveLockManagerType)XmlBeans.getContextTypeLoader().parse(xis, SingleJvmExclusiveLockManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingleJvmExclusiveLockManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingleJvmExclusiveLockManagerType.type, options);
      }

      private Factory() {
      }
   }
}
