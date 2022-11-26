package com.oracle.xmlns.weblogic.weblogicInterception;

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

public interface InterceptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InterceptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("interceptiontypeb5a6type");

   AssociationType[] getAssociationArray();

   AssociationType getAssociationArray(int var1);

   int sizeOfAssociationArray();

   void setAssociationArray(AssociationType[] var1);

   void setAssociationArray(int var1, AssociationType var2);

   AssociationType insertNewAssociation(int var1);

   AssociationType addNewAssociation();

   void removeAssociation(int var1);

   ProcessorType[] getProcessorArray();

   ProcessorType getProcessorArray(int var1);

   int sizeOfProcessorArray();

   void setProcessorArray(ProcessorType[] var1);

   void setProcessorArray(int var1, ProcessorType var2);

   ProcessorType insertNewProcessor(int var1);

   ProcessorType addNewProcessor();

   void removeProcessor(int var1);

   ProcessorTypeType[] getProcessorTypeArray();

   ProcessorTypeType getProcessorTypeArray(int var1);

   int sizeOfProcessorTypeArray();

   void setProcessorTypeArray(ProcessorTypeType[] var1);

   void setProcessorTypeArray(int var1, ProcessorTypeType var2);

   ProcessorTypeType insertNewProcessorType(int var1);

   ProcessorTypeType addNewProcessorType();

   void removeProcessorType(int var1);

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static InterceptionType newInstance() {
         return (InterceptionType)XmlBeans.getContextTypeLoader().newInstance(InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType newInstance(XmlOptions options) {
         return (InterceptionType)XmlBeans.getContextTypeLoader().newInstance(InterceptionType.type, options);
      }

      public static InterceptionType parse(String xmlAsString) throws XmlException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InterceptionType.type, options);
      }

      public static InterceptionType parse(File file) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(file, InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(file, InterceptionType.type, options);
      }

      public static InterceptionType parse(URL u) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(u, InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(u, InterceptionType.type, options);
      }

      public static InterceptionType parse(InputStream is) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(is, InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(is, InterceptionType.type, options);
      }

      public static InterceptionType parse(Reader r) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(r, InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(r, InterceptionType.type, options);
      }

      public static InterceptionType parse(XMLStreamReader sr) throws XmlException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(sr, InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(sr, InterceptionType.type, options);
      }

      public static InterceptionType parse(Node node) throws XmlException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(node, InterceptionType.type, (XmlOptions)null);
      }

      public static InterceptionType parse(Node node, XmlOptions options) throws XmlException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(node, InterceptionType.type, options);
      }

      /** @deprecated */
      public static InterceptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(xis, InterceptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InterceptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InterceptionType)XmlBeans.getContextTypeLoader().parse(xis, InterceptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InterceptionType.type, options);
      }

      private Factory() {
      }
   }
}
