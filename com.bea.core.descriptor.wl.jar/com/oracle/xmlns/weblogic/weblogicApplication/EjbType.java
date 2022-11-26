package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface EjbType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("ejbtypec886type");

   ApplicationEntityCacheType[] getEntityCacheArray();

   ApplicationEntityCacheType getEntityCacheArray(int var1);

   int sizeOfEntityCacheArray();

   void setEntityCacheArray(ApplicationEntityCacheType[] var1);

   void setEntityCacheArray(int var1, ApplicationEntityCacheType var2);

   ApplicationEntityCacheType insertNewEntityCache(int var1);

   ApplicationEntityCacheType addNewEntityCache();

   void removeEntityCache(int var1);

   TrueFalseType getStartMdbsWithApplication();

   boolean isSetStartMdbsWithApplication();

   void setStartMdbsWithApplication(TrueFalseType var1);

   TrueFalseType addNewStartMdbsWithApplication();

   void unsetStartMdbsWithApplication();

   public static final class Factory {
      public static EjbType newInstance() {
         return (EjbType)XmlBeans.getContextTypeLoader().newInstance(EjbType.type, (XmlOptions)null);
      }

      public static EjbType newInstance(XmlOptions options) {
         return (EjbType)XmlBeans.getContextTypeLoader().newInstance(EjbType.type, options);
      }

      public static EjbType parse(String xmlAsString) throws XmlException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbType.type, (XmlOptions)null);
      }

      public static EjbType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbType.type, options);
      }

      public static EjbType parse(File file) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(file, EjbType.type, (XmlOptions)null);
      }

      public static EjbType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(file, EjbType.type, options);
      }

      public static EjbType parse(URL u) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(u, EjbType.type, (XmlOptions)null);
      }

      public static EjbType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(u, EjbType.type, options);
      }

      public static EjbType parse(InputStream is) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(is, EjbType.type, (XmlOptions)null);
      }

      public static EjbType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(is, EjbType.type, options);
      }

      public static EjbType parse(Reader r) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(r, EjbType.type, (XmlOptions)null);
      }

      public static EjbType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(r, EjbType.type, options);
      }

      public static EjbType parse(XMLStreamReader sr) throws XmlException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(sr, EjbType.type, (XmlOptions)null);
      }

      public static EjbType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(sr, EjbType.type, options);
      }

      public static EjbType parse(Node node) throws XmlException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(node, EjbType.type, (XmlOptions)null);
      }

      public static EjbType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(node, EjbType.type, options);
      }

      /** @deprecated */
      public static EjbType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(xis, EjbType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbType)XmlBeans.getContextTypeLoader().parse(xis, EjbType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbType.type, options);
      }

      private Factory() {
      }
   }
}
