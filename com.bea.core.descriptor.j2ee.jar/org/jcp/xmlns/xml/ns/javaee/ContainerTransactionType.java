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

public interface ContainerTransactionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ContainerTransactionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("containertransactiontypebe2btype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   MethodType[] getMethodArray();

   MethodType getMethodArray(int var1);

   int sizeOfMethodArray();

   void setMethodArray(MethodType[] var1);

   void setMethodArray(int var1, MethodType var2);

   MethodType insertNewMethod(int var1);

   MethodType addNewMethod();

   void removeMethod(int var1);

   TransAttributeType getTransAttribute();

   void setTransAttribute(TransAttributeType var1);

   TransAttributeType addNewTransAttribute();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ContainerTransactionType newInstance() {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().newInstance(ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType newInstance(XmlOptions options) {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().newInstance(ContainerTransactionType.type, options);
      }

      public static ContainerTransactionType parse(java.lang.String xmlAsString) throws XmlException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ContainerTransactionType.type, options);
      }

      public static ContainerTransactionType parse(File file) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(file, ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(file, ContainerTransactionType.type, options);
      }

      public static ContainerTransactionType parse(URL u) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(u, ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(u, ContainerTransactionType.type, options);
      }

      public static ContainerTransactionType parse(InputStream is) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(is, ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(is, ContainerTransactionType.type, options);
      }

      public static ContainerTransactionType parse(Reader r) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(r, ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(r, ContainerTransactionType.type, options);
      }

      public static ContainerTransactionType parse(XMLStreamReader sr) throws XmlException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(sr, ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(sr, ContainerTransactionType.type, options);
      }

      public static ContainerTransactionType parse(Node node) throws XmlException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(node, ContainerTransactionType.type, (XmlOptions)null);
      }

      public static ContainerTransactionType parse(Node node, XmlOptions options) throws XmlException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(node, ContainerTransactionType.type, options);
      }

      /** @deprecated */
      public static ContainerTransactionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(xis, ContainerTransactionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ContainerTransactionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ContainerTransactionType)XmlBeans.getContextTypeLoader().parse(xis, ContainerTransactionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContainerTransactionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ContainerTransactionType.type, options);
      }

      private Factory() {
      }
   }
}
