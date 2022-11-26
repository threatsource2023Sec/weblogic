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

public interface ApplicationExceptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationExceptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("applicationexceptiontyped1abtype");

   FullyQualifiedClassType getExceptionClass();

   void setExceptionClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewExceptionClass();

   TrueFalseType getRollback();

   boolean isSetRollback();

   void setRollback(TrueFalseType var1);

   TrueFalseType addNewRollback();

   void unsetRollback();

   TrueFalseType getInherited();

   boolean isSetInherited();

   void setInherited(TrueFalseType var1);

   TrueFalseType addNewInherited();

   void unsetInherited();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ApplicationExceptionType newInstance() {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().newInstance(ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType newInstance(XmlOptions options) {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().newInstance(ApplicationExceptionType.type, options);
      }

      public static ApplicationExceptionType parse(java.lang.String xmlAsString) throws XmlException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationExceptionType.type, options);
      }

      public static ApplicationExceptionType parse(File file) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(file, ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(file, ApplicationExceptionType.type, options);
      }

      public static ApplicationExceptionType parse(URL u) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(u, ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(u, ApplicationExceptionType.type, options);
      }

      public static ApplicationExceptionType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(is, ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(is, ApplicationExceptionType.type, options);
      }

      public static ApplicationExceptionType parse(Reader r) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(r, ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(r, ApplicationExceptionType.type, options);
      }

      public static ApplicationExceptionType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationExceptionType.type, options);
      }

      public static ApplicationExceptionType parse(Node node) throws XmlException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(node, ApplicationExceptionType.type, (XmlOptions)null);
      }

      public static ApplicationExceptionType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(node, ApplicationExceptionType.type, options);
      }

      /** @deprecated */
      public static ApplicationExceptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationExceptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationExceptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationExceptionType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationExceptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationExceptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationExceptionType.type, options);
      }

      private Factory() {
      }
   }
}
