package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface CachingElementType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CachingElementType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("cachingelementtypeb3abtype");

   CmrFieldType getCmrField();

   void setCmrField(CmrFieldType var1);

   CmrFieldType addNewCmrField();

   GroupNameType getGroupName();

   boolean isSetGroupName();

   void setGroupName(GroupNameType var1);

   GroupNameType addNewGroupName();

   void unsetGroupName();

   CachingElementType[] getCachingElementArray();

   CachingElementType getCachingElementArray(int var1);

   int sizeOfCachingElementArray();

   void setCachingElementArray(CachingElementType[] var1);

   void setCachingElementArray(int var1, CachingElementType var2);

   CachingElementType insertNewCachingElement(int var1);

   CachingElementType addNewCachingElement();

   void removeCachingElement(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CachingElementType newInstance() {
         return (CachingElementType)XmlBeans.getContextTypeLoader().newInstance(CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType newInstance(XmlOptions options) {
         return (CachingElementType)XmlBeans.getContextTypeLoader().newInstance(CachingElementType.type, options);
      }

      public static CachingElementType parse(String xmlAsString) throws XmlException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CachingElementType.type, options);
      }

      public static CachingElementType parse(File file) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(file, CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(file, CachingElementType.type, options);
      }

      public static CachingElementType parse(URL u) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(u, CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(u, CachingElementType.type, options);
      }

      public static CachingElementType parse(InputStream is) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(is, CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(is, CachingElementType.type, options);
      }

      public static CachingElementType parse(Reader r) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(r, CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(r, CachingElementType.type, options);
      }

      public static CachingElementType parse(XMLStreamReader sr) throws XmlException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(sr, CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(sr, CachingElementType.type, options);
      }

      public static CachingElementType parse(Node node) throws XmlException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(node, CachingElementType.type, (XmlOptions)null);
      }

      public static CachingElementType parse(Node node, XmlOptions options) throws XmlException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(node, CachingElementType.type, options);
      }

      /** @deprecated */
      public static CachingElementType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(xis, CachingElementType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CachingElementType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CachingElementType)XmlBeans.getContextTypeLoader().parse(xis, CachingElementType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CachingElementType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CachingElementType.type, options);
      }

      private Factory() {
      }
   }
}
