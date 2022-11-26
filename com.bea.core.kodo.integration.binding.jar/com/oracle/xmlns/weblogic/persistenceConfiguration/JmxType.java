package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface JmxType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmxType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jmxtype07b7type");

   NoneJmxType getNoneJmx();

   boolean isNilNoneJmx();

   boolean isSetNoneJmx();

   void setNoneJmx(NoneJmxType var1);

   NoneJmxType addNewNoneJmx();

   void setNilNoneJmx();

   void unsetNoneJmx();

   LocalJmxType getLocalJmx();

   boolean isNilLocalJmx();

   boolean isSetLocalJmx();

   void setLocalJmx(LocalJmxType var1);

   LocalJmxType addNewLocalJmx();

   void setNilLocalJmx();

   void unsetLocalJmx();

   GuiJmxType getGuiJmx();

   boolean isNilGuiJmx();

   boolean isSetGuiJmx();

   void setGuiJmx(GuiJmxType var1);

   GuiJmxType addNewGuiJmx();

   void setNilGuiJmx();

   void unsetGuiJmx();

   Jmx2JmxType getJmx2Jmx();

   boolean isNilJmx2Jmx();

   boolean isSetJmx2Jmx();

   void setJmx2Jmx(Jmx2JmxType var1);

   Jmx2JmxType addNewJmx2Jmx();

   void setNilJmx2Jmx();

   void unsetJmx2Jmx();

   Mx4J1JmxType getMx4J1Jmx();

   boolean isNilMx4J1Jmx();

   boolean isSetMx4J1Jmx();

   void setMx4J1Jmx(Mx4J1JmxType var1);

   Mx4J1JmxType addNewMx4J1Jmx();

   void setNilMx4J1Jmx();

   void unsetMx4J1Jmx();

   Wls81JmxType getWls81Jmx();

   boolean isNilWls81Jmx();

   boolean isSetWls81Jmx();

   void setWls81Jmx(Wls81JmxType var1);

   Wls81JmxType addNewWls81Jmx();

   void setNilWls81Jmx();

   void unsetWls81Jmx();

   public static final class Factory {
      public static JmxType newInstance() {
         return (JmxType)XmlBeans.getContextTypeLoader().newInstance(JmxType.type, (XmlOptions)null);
      }

      public static JmxType newInstance(XmlOptions options) {
         return (JmxType)XmlBeans.getContextTypeLoader().newInstance(JmxType.type, options);
      }

      public static JmxType parse(String xmlAsString) throws XmlException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmxType.type, (XmlOptions)null);
      }

      public static JmxType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmxType.type, options);
      }

      public static JmxType parse(File file) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(file, JmxType.type, (XmlOptions)null);
      }

      public static JmxType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(file, JmxType.type, options);
      }

      public static JmxType parse(URL u) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(u, JmxType.type, (XmlOptions)null);
      }

      public static JmxType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(u, JmxType.type, options);
      }

      public static JmxType parse(InputStream is) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(is, JmxType.type, (XmlOptions)null);
      }

      public static JmxType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(is, JmxType.type, options);
      }

      public static JmxType parse(Reader r) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(r, JmxType.type, (XmlOptions)null);
      }

      public static JmxType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(r, JmxType.type, options);
      }

      public static JmxType parse(XMLStreamReader sr) throws XmlException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(sr, JmxType.type, (XmlOptions)null);
      }

      public static JmxType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(sr, JmxType.type, options);
      }

      public static JmxType parse(Node node) throws XmlException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(node, JmxType.type, (XmlOptions)null);
      }

      public static JmxType parse(Node node, XmlOptions options) throws XmlException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(node, JmxType.type, options);
      }

      /** @deprecated */
      public static JmxType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(xis, JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmxType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmxType)XmlBeans.getContextTypeLoader().parse(xis, JmxType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmxType.type, options);
      }

      private Factory() {
      }
   }
}
