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

public interface EjbLocalRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbLocalRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("ejblocalreftype6e1ctype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   EjbRefNameType getEjbRefName();

   void setEjbRefName(EjbRefNameType var1);

   EjbRefNameType addNewEjbRefName();

   EjbRefTypeType getEjbRefType();

   boolean isSetEjbRefType();

   void setEjbRefType(EjbRefTypeType var1);

   EjbRefTypeType addNewEjbRefType();

   void unsetEjbRefType();

   LocalHomeType getLocalHome();

   boolean isSetLocalHome();

   void setLocalHome(LocalHomeType var1);

   LocalHomeType addNewLocalHome();

   void unsetLocalHome();

   LocalType getLocal();

   boolean isSetLocal();

   void setLocal(LocalType var1);

   LocalType addNewLocal();

   void unsetLocal();

   EjbLinkType getEjbLink();

   boolean isSetEjbLink();

   void setEjbLink(EjbLinkType var1);

   EjbLinkType addNewEjbLink();

   void unsetEjbLink();

   XsdStringType getMappedName();

   boolean isSetMappedName();

   void setMappedName(XsdStringType var1);

   XsdStringType addNewMappedName();

   void unsetMappedName();

   InjectionTargetType[] getInjectionTargetArray();

   InjectionTargetType getInjectionTargetArray(int var1);

   int sizeOfInjectionTargetArray();

   void setInjectionTargetArray(InjectionTargetType[] var1);

   void setInjectionTargetArray(int var1, InjectionTargetType var2);

   InjectionTargetType insertNewInjectionTarget(int var1);

   InjectionTargetType addNewInjectionTarget();

   void removeInjectionTarget(int var1);

   XsdStringType getLookupName();

   boolean isSetLookupName();

   void setLookupName(XsdStringType var1);

   XsdStringType addNewLookupName();

   void unsetLookupName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EjbLocalRefType newInstance() {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().newInstance(EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType newInstance(XmlOptions options) {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().newInstance(EjbLocalRefType.type, options);
      }

      public static EjbLocalRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbLocalRefType.type, options);
      }

      public static EjbLocalRefType parse(File file) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(file, EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(file, EjbLocalRefType.type, options);
      }

      public static EjbLocalRefType parse(URL u) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(u, EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(u, EjbLocalRefType.type, options);
      }

      public static EjbLocalRefType parse(InputStream is) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(is, EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(is, EjbLocalRefType.type, options);
      }

      public static EjbLocalRefType parse(Reader r) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(r, EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(r, EjbLocalRefType.type, options);
      }

      public static EjbLocalRefType parse(XMLStreamReader sr) throws XmlException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(sr, EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(sr, EjbLocalRefType.type, options);
      }

      public static EjbLocalRefType parse(Node node) throws XmlException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(node, EjbLocalRefType.type, (XmlOptions)null);
      }

      public static EjbLocalRefType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(node, EjbLocalRefType.type, options);
      }

      /** @deprecated */
      public static EjbLocalRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(xis, EjbLocalRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbLocalRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbLocalRefType)XmlBeans.getContextTypeLoader().parse(xis, EjbLocalRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbLocalRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbLocalRefType.type, options);
      }

      private Factory() {
      }
   }
}
