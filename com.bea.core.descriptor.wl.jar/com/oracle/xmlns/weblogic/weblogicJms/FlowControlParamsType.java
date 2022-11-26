package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface FlowControlParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FlowControlParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("flowcontrolparamstype3e53type");

   int getFlowMinimum();

   XmlInt xgetFlowMinimum();

   boolean isSetFlowMinimum();

   void setFlowMinimum(int var1);

   void xsetFlowMinimum(XmlInt var1);

   void unsetFlowMinimum();

   int getFlowMaximum();

   XmlInt xgetFlowMaximum();

   boolean isSetFlowMaximum();

   void setFlowMaximum(int var1);

   void xsetFlowMaximum(XmlInt var1);

   void unsetFlowMaximum();

   int getFlowInterval();

   XmlInt xgetFlowInterval();

   boolean isSetFlowInterval();

   void setFlowInterval(int var1);

   void xsetFlowInterval(XmlInt var1);

   void unsetFlowInterval();

   int getFlowSteps();

   XmlInt xgetFlowSteps();

   boolean isSetFlowSteps();

   void setFlowSteps(int var1);

   void xsetFlowSteps(XmlInt var1);

   void unsetFlowSteps();

   boolean getFlowControlEnabled();

   XmlBoolean xgetFlowControlEnabled();

   boolean isSetFlowControlEnabled();

   void setFlowControlEnabled(boolean var1);

   void xsetFlowControlEnabled(XmlBoolean var1);

   void unsetFlowControlEnabled();

   OneWaySendModeType.Enum getOneWaySendMode();

   OneWaySendModeType xgetOneWaySendMode();

   boolean isSetOneWaySendMode();

   void setOneWaySendMode(OneWaySendModeType.Enum var1);

   void xsetOneWaySendMode(OneWaySendModeType var1);

   void unsetOneWaySendMode();

   int getOneWaySendWindowSize();

   XmlInt xgetOneWaySendWindowSize();

   boolean isSetOneWaySendWindowSize();

   void setOneWaySendWindowSize(int var1);

   void xsetOneWaySendWindowSize(XmlInt var1);

   void unsetOneWaySendWindowSize();

   public static final class Factory {
      public static FlowControlParamsType newInstance() {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().newInstance(FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType newInstance(XmlOptions options) {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().newInstance(FlowControlParamsType.type, options);
      }

      public static FlowControlParamsType parse(String xmlAsString) throws XmlException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FlowControlParamsType.type, options);
      }

      public static FlowControlParamsType parse(File file) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(file, FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(file, FlowControlParamsType.type, options);
      }

      public static FlowControlParamsType parse(URL u) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(u, FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(u, FlowControlParamsType.type, options);
      }

      public static FlowControlParamsType parse(InputStream is) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(is, FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(is, FlowControlParamsType.type, options);
      }

      public static FlowControlParamsType parse(Reader r) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(r, FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(r, FlowControlParamsType.type, options);
      }

      public static FlowControlParamsType parse(XMLStreamReader sr) throws XmlException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(sr, FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(sr, FlowControlParamsType.type, options);
      }

      public static FlowControlParamsType parse(Node node) throws XmlException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(node, FlowControlParamsType.type, (XmlOptions)null);
      }

      public static FlowControlParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(node, FlowControlParamsType.type, options);
      }

      /** @deprecated */
      public static FlowControlParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(xis, FlowControlParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FlowControlParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FlowControlParamsType)XmlBeans.getContextTypeLoader().parse(xis, FlowControlParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FlowControlParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FlowControlParamsType.type, options);
      }

      private Factory() {
      }
   }
}
