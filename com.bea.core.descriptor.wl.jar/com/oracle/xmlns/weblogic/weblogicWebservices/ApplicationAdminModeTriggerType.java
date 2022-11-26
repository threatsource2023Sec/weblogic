package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ApplicationAdminModeTriggerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationAdminModeTriggerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("applicationadminmodetriggertypeb5a6type");

   XsdIntegerType getMaxStuckThreadTime();

   boolean isSetMaxStuckThreadTime();

   void setMaxStuckThreadTime(XsdIntegerType var1);

   XsdIntegerType addNewMaxStuckThreadTime();

   void unsetMaxStuckThreadTime();

   XsdIntegerType getStuckThreadCount();

   void setStuckThreadCount(XsdIntegerType var1);

   XsdIntegerType addNewStuckThreadCount();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ApplicationAdminModeTriggerType newInstance() {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().newInstance(ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType newInstance(XmlOptions options) {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().newInstance(ApplicationAdminModeTriggerType.type, options);
      }

      public static ApplicationAdminModeTriggerType parse(String xmlAsString) throws XmlException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationAdminModeTriggerType.type, options);
      }

      public static ApplicationAdminModeTriggerType parse(File file) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(file, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(file, ApplicationAdminModeTriggerType.type, options);
      }

      public static ApplicationAdminModeTriggerType parse(URL u) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(u, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(u, ApplicationAdminModeTriggerType.type, options);
      }

      public static ApplicationAdminModeTriggerType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(is, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(is, ApplicationAdminModeTriggerType.type, options);
      }

      public static ApplicationAdminModeTriggerType parse(Reader r) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(r, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(r, ApplicationAdminModeTriggerType.type, options);
      }

      public static ApplicationAdminModeTriggerType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationAdminModeTriggerType.type, options);
      }

      public static ApplicationAdminModeTriggerType parse(Node node) throws XmlException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(node, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      public static ApplicationAdminModeTriggerType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(node, ApplicationAdminModeTriggerType.type, options);
      }

      /** @deprecated */
      public static ApplicationAdminModeTriggerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationAdminModeTriggerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationAdminModeTriggerType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationAdminModeTriggerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationAdminModeTriggerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationAdminModeTriggerType.type, options);
      }

      private Factory() {
      }
   }
}
