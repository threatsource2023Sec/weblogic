package org.jcp.xmlns.xml.ns.javaee;

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

public interface CmrFieldType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CmrFieldType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cmrfieldtypec9a8type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   String getCmrFieldName();

   void setCmrFieldName(String var1);

   String addNewCmrFieldName();

   CmrFieldTypeType getCmrFieldType();

   boolean isSetCmrFieldType();

   void setCmrFieldType(CmrFieldTypeType var1);

   CmrFieldTypeType addNewCmrFieldType();

   void unsetCmrFieldType();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CmrFieldType newInstance() {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().newInstance(CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType newInstance(XmlOptions options) {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().newInstance(CmrFieldType.type, options);
      }

      public static CmrFieldType parse(java.lang.String xmlAsString) throws XmlException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmrFieldType.type, options);
      }

      public static CmrFieldType parse(File file) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(file, CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(file, CmrFieldType.type, options);
      }

      public static CmrFieldType parse(URL u) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(u, CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(u, CmrFieldType.type, options);
      }

      public static CmrFieldType parse(InputStream is) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(is, CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(is, CmrFieldType.type, options);
      }

      public static CmrFieldType parse(Reader r) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(r, CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(r, CmrFieldType.type, options);
      }

      public static CmrFieldType parse(XMLStreamReader sr) throws XmlException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(sr, CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(sr, CmrFieldType.type, options);
      }

      public static CmrFieldType parse(Node node) throws XmlException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(node, CmrFieldType.type, (XmlOptions)null);
      }

      public static CmrFieldType parse(Node node, XmlOptions options) throws XmlException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(node, CmrFieldType.type, options);
      }

      /** @deprecated */
      public static CmrFieldType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(xis, CmrFieldType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CmrFieldType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CmrFieldType)XmlBeans.getContextTypeLoader().parse(xis, CmrFieldType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmrFieldType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmrFieldType.type, options);
      }

      private Factory() {
      }
   }
}
