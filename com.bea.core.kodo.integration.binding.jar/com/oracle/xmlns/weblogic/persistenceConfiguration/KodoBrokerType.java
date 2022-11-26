package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface KodoBrokerType extends BrokerImplType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoBrokerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodobrokertype1f95type");

   boolean getLargeTransaction();

   XmlBoolean xgetLargeTransaction();

   boolean isSetLargeTransaction();

   void setLargeTransaction(boolean var1);

   void xsetLargeTransaction(XmlBoolean var1);

   void unsetLargeTransaction();

   int getAutoClear();

   XmlInt xgetAutoClear();

   boolean isSetAutoClear();

   void setAutoClear(int var1);

   void xsetAutoClear(XmlInt var1);

   void unsetAutoClear();

   int getDetachState();

   XmlInt xgetDetachState();

   boolean isSetDetachState();

   void setDetachState(int var1);

   void xsetDetachState(XmlInt var1);

   void unsetDetachState();

   boolean getNontransactionalRead();

   XmlBoolean xgetNontransactionalRead();

   boolean isSetNontransactionalRead();

   void setNontransactionalRead(boolean var1);

   void xsetNontransactionalRead(XmlBoolean var1);

   void unsetNontransactionalRead();

   boolean getRetainState();

   XmlBoolean xgetRetainState();

   boolean isSetRetainState();

   void setRetainState(boolean var1);

   void xsetRetainState(XmlBoolean var1);

   void unsetRetainState();

   boolean getEvictFromDataCache();

   XmlBoolean xgetEvictFromDataCache();

   boolean isSetEvictFromDataCache();

   void setEvictFromDataCache(boolean var1);

   void xsetEvictFromDataCache(XmlBoolean var1);

   void unsetEvictFromDataCache();

   boolean getDetachedNew();

   XmlBoolean xgetDetachedNew();

   boolean isSetDetachedNew();

   void setDetachedNew(boolean var1);

   void xsetDetachedNew(XmlBoolean var1);

   void unsetDetachedNew();

   boolean getOptimistic();

   XmlBoolean xgetOptimistic();

   boolean isSetOptimistic();

   void setOptimistic(boolean var1);

   void xsetOptimistic(XmlBoolean var1);

   void unsetOptimistic();

   boolean getNontransactionalWrite();

   XmlBoolean xgetNontransactionalWrite();

   boolean isSetNontransactionalWrite();

   void setNontransactionalWrite(boolean var1);

   void xsetNontransactionalWrite(XmlBoolean var1);

   void unsetNontransactionalWrite();

   boolean getSyncWithManagedTransactions();

   XmlBoolean xgetSyncWithManagedTransactions();

   boolean isSetSyncWithManagedTransactions();

   void setSyncWithManagedTransactions(boolean var1);

   void xsetSyncWithManagedTransactions(XmlBoolean var1);

   void unsetSyncWithManagedTransactions();

   boolean getMultithreaded();

   XmlBoolean xgetMultithreaded();

   boolean isSetMultithreaded();

   void setMultithreaded(boolean var1);

   void xsetMultithreaded(XmlBoolean var1);

   void unsetMultithreaded();

   boolean getPopulateDataCache();

   XmlBoolean xgetPopulateDataCache();

   boolean isSetPopulateDataCache();

   void setPopulateDataCache(boolean var1);

   void xsetPopulateDataCache(XmlBoolean var1);

   void unsetPopulateDataCache();

   boolean getIgnoreChanges();

   XmlBoolean xgetIgnoreChanges();

   boolean isSetIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   void xsetIgnoreChanges(XmlBoolean var1);

   void unsetIgnoreChanges();

   int getAutoDetach();

   XmlInt xgetAutoDetach();

   boolean isSetAutoDetach();

   void setAutoDetach(int var1);

   void xsetAutoDetach(XmlInt var1);

   void unsetAutoDetach();

   int getRestoreState();

   XmlInt xgetRestoreState();

   boolean isSetRestoreState();

   void setRestoreState(int var1);

   void xsetRestoreState(XmlInt var1);

   void unsetRestoreState();

   boolean getOrderDirtyObjects();

   XmlBoolean xgetOrderDirtyObjects();

   boolean isSetOrderDirtyObjects();

   void setOrderDirtyObjects(boolean var1);

   void xsetOrderDirtyObjects(XmlBoolean var1);

   void unsetOrderDirtyObjects();

   public static final class Factory {
      public static KodoBrokerType newInstance() {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().newInstance(KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType newInstance(XmlOptions options) {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().newInstance(KodoBrokerType.type, options);
      }

      public static KodoBrokerType parse(String xmlAsString) throws XmlException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoBrokerType.type, options);
      }

      public static KodoBrokerType parse(File file) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(file, KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(file, KodoBrokerType.type, options);
      }

      public static KodoBrokerType parse(URL u) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(u, KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(u, KodoBrokerType.type, options);
      }

      public static KodoBrokerType parse(InputStream is) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(is, KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(is, KodoBrokerType.type, options);
      }

      public static KodoBrokerType parse(Reader r) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(r, KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(r, KodoBrokerType.type, options);
      }

      public static KodoBrokerType parse(XMLStreamReader sr) throws XmlException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(sr, KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(sr, KodoBrokerType.type, options);
      }

      public static KodoBrokerType parse(Node node) throws XmlException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(node, KodoBrokerType.type, (XmlOptions)null);
      }

      public static KodoBrokerType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(node, KodoBrokerType.type, options);
      }

      /** @deprecated */
      public static KodoBrokerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(xis, KodoBrokerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoBrokerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoBrokerType)XmlBeans.getContextTypeLoader().parse(xis, KodoBrokerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoBrokerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoBrokerType.type, options);
      }

      private Factory() {
      }
   }
}
