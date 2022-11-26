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

public interface EjbRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("ejbreftypee13atype");

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

   HomeType getHome();

   boolean isSetHome();

   void setHome(HomeType var1);

   HomeType addNewHome();

   void unsetHome();

   RemoteType getRemote();

   boolean isSetRemote();

   void setRemote(RemoteType var1);

   RemoteType addNewRemote();

   void unsetRemote();

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
      public static EjbRefType newInstance() {
         return (EjbRefType)XmlBeans.getContextTypeLoader().newInstance(EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType newInstance(XmlOptions options) {
         return (EjbRefType)XmlBeans.getContextTypeLoader().newInstance(EjbRefType.type, options);
      }

      public static EjbRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRefType.type, options);
      }

      public static EjbRefType parse(File file) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(file, EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(file, EjbRefType.type, options);
      }

      public static EjbRefType parse(URL u) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(u, EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(u, EjbRefType.type, options);
      }

      public static EjbRefType parse(InputStream is) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(is, EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(is, EjbRefType.type, options);
      }

      public static EjbRefType parse(Reader r) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(r, EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(r, EjbRefType.type, options);
      }

      public static EjbRefType parse(XMLStreamReader sr) throws XmlException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(sr, EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(sr, EjbRefType.type, options);
      }

      public static EjbRefType parse(Node node) throws XmlException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(node, EjbRefType.type, (XmlOptions)null);
      }

      public static EjbRefType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(node, EjbRefType.type, options);
      }

      /** @deprecated */
      public static EjbRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(xis, EjbRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbRefType)XmlBeans.getContextTypeLoader().parse(xis, EjbRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRefType.type, options);
      }

      private Factory() {
      }
   }
}
