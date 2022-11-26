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

public interface FieldGroupType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FieldGroupType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("fieldgrouptypeb629type");

   GroupNameType getGroupName();

   void setGroupName(GroupNameType var1);

   GroupNameType addNewGroupName();

   CmpFieldType[] getCmpFieldArray();

   CmpFieldType getCmpFieldArray(int var1);

   int sizeOfCmpFieldArray();

   void setCmpFieldArray(CmpFieldType[] var1);

   void setCmpFieldArray(int var1, CmpFieldType var2);

   CmpFieldType insertNewCmpField(int var1);

   CmpFieldType addNewCmpField();

   void removeCmpField(int var1);

   CmrFieldType[] getCmrFieldArray();

   CmrFieldType getCmrFieldArray(int var1);

   int sizeOfCmrFieldArray();

   void setCmrFieldArray(CmrFieldType[] var1);

   void setCmrFieldArray(int var1, CmrFieldType var2);

   CmrFieldType insertNewCmrField(int var1);

   CmrFieldType addNewCmrField();

   void removeCmrField(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FieldGroupType newInstance() {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().newInstance(FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType newInstance(XmlOptions options) {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().newInstance(FieldGroupType.type, options);
      }

      public static FieldGroupType parse(String xmlAsString) throws XmlException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FieldGroupType.type, options);
      }

      public static FieldGroupType parse(File file) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(file, FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(file, FieldGroupType.type, options);
      }

      public static FieldGroupType parse(URL u) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(u, FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(u, FieldGroupType.type, options);
      }

      public static FieldGroupType parse(InputStream is) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(is, FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(is, FieldGroupType.type, options);
      }

      public static FieldGroupType parse(Reader r) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(r, FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(r, FieldGroupType.type, options);
      }

      public static FieldGroupType parse(XMLStreamReader sr) throws XmlException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(sr, FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(sr, FieldGroupType.type, options);
      }

      public static FieldGroupType parse(Node node) throws XmlException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(node, FieldGroupType.type, (XmlOptions)null);
      }

      public static FieldGroupType parse(Node node, XmlOptions options) throws XmlException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(node, FieldGroupType.type, options);
      }

      /** @deprecated */
      public static FieldGroupType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(xis, FieldGroupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FieldGroupType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FieldGroupType)XmlBeans.getContextTypeLoader().parse(xis, FieldGroupType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FieldGroupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FieldGroupType.type, options);
      }

      private Factory() {
      }
   }
}
