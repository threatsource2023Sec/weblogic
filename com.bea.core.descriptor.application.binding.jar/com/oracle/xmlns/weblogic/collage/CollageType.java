package com.oracle.xmlns.weblogic.collage;

import com.bea.xml.SchemaType;
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

public interface CollageType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CollageType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("collagetypeb304type");

   String getMappingStyle();

   XmlString xgetMappingStyle();

   boolean isSetMappingStyle();

   void setMappingStyle(String var1);

   void xsetMappingStyle(XmlString var1);

   void unsetMappingStyle();

   PatternsetType[] getPatternsetArray();

   PatternsetType getPatternsetArray(int var1);

   int sizeOfPatternsetArray();

   void setPatternsetArray(PatternsetType[] var1);

   void setPatternsetArray(int var1, PatternsetType var2);

   PatternsetType insertNewPatternset(int var1);

   PatternsetType addNewPatternset();

   void removePatternset(int var1);

   MappingType[] getMappingArray();

   MappingType getMappingArray(int var1);

   int sizeOfMappingArray();

   void setMappingArray(MappingType[] var1);

   void setMappingArray(int var1, MappingType var2);

   MappingType insertNewMapping(int var1);

   MappingType addNewMapping();

   void removeMapping(int var1);

   String getPathToWritableRoot();

   XmlString xgetPathToWritableRoot();

   boolean isSetPathToWritableRoot();

   void setPathToWritableRoot(String var1);

   void xsetPathToWritableRoot(XmlString var1);

   void unsetPathToWritableRoot();

   public static final class Factory {
      public static CollageType newInstance() {
         return (CollageType)XmlBeans.getContextTypeLoader().newInstance(CollageType.type, (XmlOptions)null);
      }

      public static CollageType newInstance(XmlOptions options) {
         return (CollageType)XmlBeans.getContextTypeLoader().newInstance(CollageType.type, options);
      }

      public static CollageType parse(String xmlAsString) throws XmlException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CollageType.type, (XmlOptions)null);
      }

      public static CollageType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CollageType.type, options);
      }

      public static CollageType parse(File file) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(file, CollageType.type, (XmlOptions)null);
      }

      public static CollageType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(file, CollageType.type, options);
      }

      public static CollageType parse(URL u) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(u, CollageType.type, (XmlOptions)null);
      }

      public static CollageType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(u, CollageType.type, options);
      }

      public static CollageType parse(InputStream is) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(is, CollageType.type, (XmlOptions)null);
      }

      public static CollageType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(is, CollageType.type, options);
      }

      public static CollageType parse(Reader r) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(r, CollageType.type, (XmlOptions)null);
      }

      public static CollageType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(r, CollageType.type, options);
      }

      public static CollageType parse(XMLStreamReader sr) throws XmlException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(sr, CollageType.type, (XmlOptions)null);
      }

      public static CollageType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(sr, CollageType.type, options);
      }

      public static CollageType parse(Node node) throws XmlException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(node, CollageType.type, (XmlOptions)null);
      }

      public static CollageType parse(Node node, XmlOptions options) throws XmlException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(node, CollageType.type, options);
      }

      /** @deprecated */
      public static CollageType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(xis, CollageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CollageType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CollageType)XmlBeans.getContextTypeLoader().parse(xis, CollageType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CollageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CollageType.type, options);
      }

      private Factory() {
      }
   }
}
