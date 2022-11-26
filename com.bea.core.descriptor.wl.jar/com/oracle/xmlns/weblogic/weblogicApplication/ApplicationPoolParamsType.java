package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface ApplicationPoolParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ApplicationPoolParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("applicationpoolparamstype2bc3type");

   SizeParamsType getSizeParams();

   boolean isSetSizeParams();

   void setSizeParams(SizeParamsType var1);

   SizeParamsType addNewSizeParams();

   void unsetSizeParams();

   XaParamsType getXaParams();

   boolean isSetXaParams();

   void setXaParams(XaParamsType var1);

   XaParamsType addNewXaParams();

   void unsetXaParams();

   int getLoginDelaySeconds();

   XmlInt xgetLoginDelaySeconds();

   boolean isSetLoginDelaySeconds();

   void setLoginDelaySeconds(int var1);

   void xsetLoginDelaySeconds(XmlInt var1);

   void unsetLoginDelaySeconds();

   TrueFalseType getLeakProfilingEnabled();

   boolean isSetLeakProfilingEnabled();

   void setLeakProfilingEnabled(TrueFalseType var1);

   TrueFalseType addNewLeakProfilingEnabled();

   void unsetLeakProfilingEnabled();

   ConnectionCheckParamsType getConnectionCheckParams();

   boolean isSetConnectionCheckParams();

   void setConnectionCheckParams(ConnectionCheckParamsType var1);

   ConnectionCheckParamsType addNewConnectionCheckParams();

   void unsetConnectionCheckParams();

   int getJdbcxaDebugLevel();

   XmlInt xgetJdbcxaDebugLevel();

   boolean isSetJdbcxaDebugLevel();

   void setJdbcxaDebugLevel(int var1);

   void xsetJdbcxaDebugLevel(XmlInt var1);

   void unsetJdbcxaDebugLevel();

   TrueFalseType getRemoveInfectedConnectionsEnabled();

   boolean isSetRemoveInfectedConnectionsEnabled();

   void setRemoveInfectedConnectionsEnabled(TrueFalseType var1);

   TrueFalseType addNewRemoveInfectedConnectionsEnabled();

   void unsetRemoveInfectedConnectionsEnabled();

   public static final class Factory {
      public static ApplicationPoolParamsType newInstance() {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().newInstance(ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType newInstance(XmlOptions options) {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().newInstance(ApplicationPoolParamsType.type, options);
      }

      public static ApplicationPoolParamsType parse(String xmlAsString) throws XmlException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ApplicationPoolParamsType.type, options);
      }

      public static ApplicationPoolParamsType parse(File file) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(file, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(file, ApplicationPoolParamsType.type, options);
      }

      public static ApplicationPoolParamsType parse(URL u) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(u, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(u, ApplicationPoolParamsType.type, options);
      }

      public static ApplicationPoolParamsType parse(InputStream is) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(is, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(is, ApplicationPoolParamsType.type, options);
      }

      public static ApplicationPoolParamsType parse(Reader r) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(r, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(r, ApplicationPoolParamsType.type, options);
      }

      public static ApplicationPoolParamsType parse(XMLStreamReader sr) throws XmlException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(sr, ApplicationPoolParamsType.type, options);
      }

      public static ApplicationPoolParamsType parse(Node node) throws XmlException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(node, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      public static ApplicationPoolParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(node, ApplicationPoolParamsType.type, options);
      }

      /** @deprecated */
      public static ApplicationPoolParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ApplicationPoolParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ApplicationPoolParamsType)XmlBeans.getContextTypeLoader().parse(xis, ApplicationPoolParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationPoolParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ApplicationPoolParamsType.type, options);
      }

      private Factory() {
      }
   }
}
