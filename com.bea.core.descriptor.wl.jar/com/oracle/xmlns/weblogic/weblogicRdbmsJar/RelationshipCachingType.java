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

public interface RelationshipCachingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RelationshipCachingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("relationshipcachingtypea559type");

   CachingNameType getCachingName();

   void setCachingName(CachingNameType var1);

   CachingNameType addNewCachingName();

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
      public static RelationshipCachingType newInstance() {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().newInstance(RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType newInstance(XmlOptions options) {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().newInstance(RelationshipCachingType.type, options);
      }

      public static RelationshipCachingType parse(String xmlAsString) throws XmlException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationshipCachingType.type, options);
      }

      public static RelationshipCachingType parse(File file) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(file, RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(file, RelationshipCachingType.type, options);
      }

      public static RelationshipCachingType parse(URL u) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(u, RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(u, RelationshipCachingType.type, options);
      }

      public static RelationshipCachingType parse(InputStream is) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(is, RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(is, RelationshipCachingType.type, options);
      }

      public static RelationshipCachingType parse(Reader r) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(r, RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(r, RelationshipCachingType.type, options);
      }

      public static RelationshipCachingType parse(XMLStreamReader sr) throws XmlException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(sr, RelationshipCachingType.type, options);
      }

      public static RelationshipCachingType parse(Node node) throws XmlException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(node, RelationshipCachingType.type, (XmlOptions)null);
      }

      public static RelationshipCachingType parse(Node node, XmlOptions options) throws XmlException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(node, RelationshipCachingType.type, options);
      }

      /** @deprecated */
      public static RelationshipCachingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipCachingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RelationshipCachingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RelationshipCachingType)XmlBeans.getContextTypeLoader().parse(xis, RelationshipCachingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipCachingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationshipCachingType.type, options);
      }

      private Factory() {
      }
   }
}
