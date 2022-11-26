package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface InjectionTargetType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InjectionTargetType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("injectiontargettype8bb4type");

   FullyQualifiedClassType getInjectionTargetClass();

   void setInjectionTargetClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewInjectionTargetClass();

   JavaIdentifierType getInjectionTargetName();

   void setInjectionTargetName(JavaIdentifierType var1);

   JavaIdentifierType addNewInjectionTargetName();

   public static final class Factory {
      public static InjectionTargetType newInstance() {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().newInstance(InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType newInstance(XmlOptions options) {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().newInstance(InjectionTargetType.type, options);
      }

      public static InjectionTargetType parse(java.lang.String xmlAsString) throws XmlException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InjectionTargetType.type, options);
      }

      public static InjectionTargetType parse(File file) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(file, InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(file, InjectionTargetType.type, options);
      }

      public static InjectionTargetType parse(URL u) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(u, InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(u, InjectionTargetType.type, options);
      }

      public static InjectionTargetType parse(InputStream is) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(is, InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(is, InjectionTargetType.type, options);
      }

      public static InjectionTargetType parse(Reader r) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(r, InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(r, InjectionTargetType.type, options);
      }

      public static InjectionTargetType parse(XMLStreamReader sr) throws XmlException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(sr, InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(sr, InjectionTargetType.type, options);
      }

      public static InjectionTargetType parse(Node node) throws XmlException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(node, InjectionTargetType.type, (XmlOptions)null);
      }

      public static InjectionTargetType parse(Node node, XmlOptions options) throws XmlException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(node, InjectionTargetType.type, options);
      }

      /** @deprecated */
      public static InjectionTargetType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(xis, InjectionTargetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InjectionTargetType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InjectionTargetType)XmlBeans.getContextTypeLoader().parse(xis, InjectionTargetType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InjectionTargetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InjectionTargetType.type, options);
      }

      private Factory() {
      }
   }
}
