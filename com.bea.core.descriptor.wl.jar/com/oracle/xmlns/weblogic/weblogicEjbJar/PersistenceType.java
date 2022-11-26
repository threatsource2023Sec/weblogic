package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface PersistenceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("persistencetype7ca3type");

   IsModifiedMethodNameType getIsModifiedMethodName();

   boolean isSetIsModifiedMethodName();

   void setIsModifiedMethodName(IsModifiedMethodNameType var1);

   IsModifiedMethodNameType addNewIsModifiedMethodName();

   void unsetIsModifiedMethodName();

   TrueFalseType getDelayUpdatesUntilEndOfTx();

   boolean isSetDelayUpdatesUntilEndOfTx();

   void setDelayUpdatesUntilEndOfTx(TrueFalseType var1);

   TrueFalseType addNewDelayUpdatesUntilEndOfTx();

   void unsetDelayUpdatesUntilEndOfTx();

   TrueFalseType getFindersLoadBean();

   boolean isSetFindersLoadBean();

   void setFindersLoadBean(TrueFalseType var1);

   TrueFalseType addNewFindersLoadBean();

   void unsetFindersLoadBean();

   PersistenceUseType getPersistenceUse();

   boolean isSetPersistenceUse();

   void setPersistenceUse(PersistenceUseType var1);

   PersistenceUseType addNewPersistenceUse();

   void unsetPersistenceUse();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PersistenceType newInstance() {
         return (PersistenceType)XmlBeans.getContextTypeLoader().newInstance(PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType newInstance(XmlOptions options) {
         return (PersistenceType)XmlBeans.getContextTypeLoader().newInstance(PersistenceType.type, options);
      }

      public static PersistenceType parse(String xmlAsString) throws XmlException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceType.type, options);
      }

      public static PersistenceType parse(File file) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(file, PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(file, PersistenceType.type, options);
      }

      public static PersistenceType parse(URL u) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(u, PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(u, PersistenceType.type, options);
      }

      public static PersistenceType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(is, PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(is, PersistenceType.type, options);
      }

      public static PersistenceType parse(Reader r) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(r, PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(r, PersistenceType.type, options);
      }

      public static PersistenceType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceType.type, options);
      }

      public static PersistenceType parse(Node node) throws XmlException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(node, PersistenceType.type, (XmlOptions)null);
      }

      public static PersistenceType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(node, PersistenceType.type, options);
      }

      /** @deprecated */
      public static PersistenceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceType.type, options);
      }

      private Factory() {
      }
   }
}
