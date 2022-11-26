package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DefaultPersistentStoreType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultPersistentStoreType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("defaultpersistentstoretype8b9ftype");

   String getNotes();

   XmlString xgetNotes();

   boolean isSetNotes();

   void setNotes(String var1);

   void xsetNotes(XmlString var1);

   void unsetNotes();

   String getDirectoryPath();

   XmlString xgetDirectoryPath();

   boolean isSetDirectoryPath();

   void setDirectoryPath(String var1);

   void xsetDirectoryPath(XmlString var1);

   void unsetDirectoryPath();

   SynchronousWritePolicy.Enum getSynchronousWritePolicy();

   SynchronousWritePolicy xgetSynchronousWritePolicy();

   boolean isSetSynchronousWritePolicy();

   void setSynchronousWritePolicy(SynchronousWritePolicy.Enum var1);

   void xsetSynchronousWritePolicy(SynchronousWritePolicy var1);

   void unsetSynchronousWritePolicy();

   public static final class Factory {
      public static DefaultPersistentStoreType newInstance() {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().newInstance(DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType newInstance(XmlOptions options) {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().newInstance(DefaultPersistentStoreType.type, options);
      }

      public static DefaultPersistentStoreType parse(String xmlAsString) throws XmlException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultPersistentStoreType.type, options);
      }

      public static DefaultPersistentStoreType parse(File file) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(file, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(file, DefaultPersistentStoreType.type, options);
      }

      public static DefaultPersistentStoreType parse(URL u) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(u, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(u, DefaultPersistentStoreType.type, options);
      }

      public static DefaultPersistentStoreType parse(InputStream is) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(is, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(is, DefaultPersistentStoreType.type, options);
      }

      public static DefaultPersistentStoreType parse(Reader r) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(r, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(r, DefaultPersistentStoreType.type, options);
      }

      public static DefaultPersistentStoreType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(sr, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(sr, DefaultPersistentStoreType.type, options);
      }

      public static DefaultPersistentStoreType parse(Node node) throws XmlException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(node, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      public static DefaultPersistentStoreType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(node, DefaultPersistentStoreType.type, options);
      }

      /** @deprecated */
      public static DefaultPersistentStoreType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(xis, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultPersistentStoreType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultPersistentStoreType)XmlBeans.getContextTypeLoader().parse(xis, DefaultPersistentStoreType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultPersistentStoreType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultPersistentStoreType.type, options);
      }

      private Factory() {
      }
   }

   public interface SynchronousWritePolicy extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SynchronousWritePolicy.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("synchronouswritepolicy631felemtype");
      Enum DISABLED = DefaultPersistentStoreType.SynchronousWritePolicy.Enum.forString("Disabled");
      Enum CACHE_FLUSH = DefaultPersistentStoreType.SynchronousWritePolicy.Enum.forString("Cache-Flush");
      Enum DIRECT_WRITE = DefaultPersistentStoreType.SynchronousWritePolicy.Enum.forString("Direct-Write");
      int INT_DISABLED = 1;
      int INT_CACHE_FLUSH = 2;
      int INT_DIRECT_WRITE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static SynchronousWritePolicy newValue(Object obj) {
            return (SynchronousWritePolicy)DefaultPersistentStoreType.SynchronousWritePolicy.type.newValue(obj);
         }

         public static SynchronousWritePolicy newInstance() {
            return (SynchronousWritePolicy)XmlBeans.getContextTypeLoader().newInstance(DefaultPersistentStoreType.SynchronousWritePolicy.type, (XmlOptions)null);
         }

         public static SynchronousWritePolicy newInstance(XmlOptions options) {
            return (SynchronousWritePolicy)XmlBeans.getContextTypeLoader().newInstance(DefaultPersistentStoreType.SynchronousWritePolicy.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_DISABLED = 1;
         static final int INT_CACHE_FLUSH = 2;
         static final int INT_DIRECT_WRITE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Disabled", 1), new Enum("Cache-Flush", 2), new Enum("Direct-Write", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }
}
