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

public interface CompatibilityType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CompatibilityType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("compatibilitytype35edtype");

   TrueFalseType getSerializeByteArrayToOracleBlob();

   boolean isSetSerializeByteArrayToOracleBlob();

   void setSerializeByteArrayToOracleBlob(TrueFalseType var1);

   TrueFalseType addNewSerializeByteArrayToOracleBlob();

   void unsetSerializeByteArrayToOracleBlob();

   TrueFalseType getSerializeCharArrayToBytes();

   boolean isSetSerializeCharArrayToBytes();

   void setSerializeCharArrayToBytes(TrueFalseType var1);

   TrueFalseType addNewSerializeCharArrayToBytes();

   void unsetSerializeCharArrayToBytes();

   TrueFalseType getAllowReadonlyCreateAndRemove();

   boolean isSetAllowReadonlyCreateAndRemove();

   void setAllowReadonlyCreateAndRemove(TrueFalseType var1);

   TrueFalseType addNewAllowReadonlyCreateAndRemove();

   void unsetAllowReadonlyCreateAndRemove();

   TrueFalseType getDisableStringTrimming();

   boolean isSetDisableStringTrimming();

   void setDisableStringTrimming(TrueFalseType var1);

   TrueFalseType addNewDisableStringTrimming();

   void unsetDisableStringTrimming();

   TrueFalseType getFindersReturnNulls();

   boolean isSetFindersReturnNulls();

   void setFindersReturnNulls(TrueFalseType var1);

   TrueFalseType addNewFindersReturnNulls();

   void unsetFindersReturnNulls();

   TrueFalseType getLoadRelatedBeansFromDbInPostCreate();

   boolean isSetLoadRelatedBeansFromDbInPostCreate();

   void setLoadRelatedBeansFromDbInPostCreate(TrueFalseType var1);

   TrueFalseType addNewLoadRelatedBeansFromDbInPostCreate();

   void unsetLoadRelatedBeansFromDbInPostCreate();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CompatibilityType newInstance() {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().newInstance(CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType newInstance(XmlOptions options) {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().newInstance(CompatibilityType.type, options);
      }

      public static CompatibilityType parse(String xmlAsString) throws XmlException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CompatibilityType.type, options);
      }

      public static CompatibilityType parse(File file) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(file, CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(file, CompatibilityType.type, options);
      }

      public static CompatibilityType parse(URL u) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(u, CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(u, CompatibilityType.type, options);
      }

      public static CompatibilityType parse(InputStream is) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(is, CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(is, CompatibilityType.type, options);
      }

      public static CompatibilityType parse(Reader r) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(r, CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(r, CompatibilityType.type, options);
      }

      public static CompatibilityType parse(XMLStreamReader sr) throws XmlException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(sr, CompatibilityType.type, options);
      }

      public static CompatibilityType parse(Node node) throws XmlException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(node, CompatibilityType.type, (XmlOptions)null);
      }

      public static CompatibilityType parse(Node node, XmlOptions options) throws XmlException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(node, CompatibilityType.type, options);
      }

      /** @deprecated */
      public static CompatibilityType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, CompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CompatibilityType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CompatibilityType)XmlBeans.getContextTypeLoader().parse(xis, CompatibilityType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CompatibilityType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CompatibilityType.type, options);
      }

      private Factory() {
      }
   }
}
