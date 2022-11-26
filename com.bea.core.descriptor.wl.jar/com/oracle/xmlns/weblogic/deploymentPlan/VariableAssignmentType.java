package com.oracle.xmlns.weblogic.deploymentPlan;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.j2Ee.PathType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface VariableAssignmentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VariableAssignmentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("variableassignmenttypecbd7type");

   String getDescription();

   XmlString xgetDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void unsetDescription();

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   PathType getXpath();

   void setXpath(PathType var1);

   PathType addNewXpath();

   String getOrigin();

   XmlString xgetOrigin();

   boolean isSetOrigin();

   void setOrigin(String var1);

   void xsetOrigin(XmlString var1);

   void unsetOrigin();

   Operation.Enum getOperation();

   Operation xgetOperation();

   boolean isSetOperation();

   void setOperation(Operation.Enum var1);

   void xsetOperation(Operation var1);

   void unsetOperation();

   public static final class Factory {
      public static VariableAssignmentType newInstance() {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().newInstance(VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType newInstance(XmlOptions options) {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().newInstance(VariableAssignmentType.type, options);
      }

      public static VariableAssignmentType parse(String xmlAsString) throws XmlException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VariableAssignmentType.type, options);
      }

      public static VariableAssignmentType parse(File file) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(file, VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(file, VariableAssignmentType.type, options);
      }

      public static VariableAssignmentType parse(URL u) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(u, VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(u, VariableAssignmentType.type, options);
      }

      public static VariableAssignmentType parse(InputStream is) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(is, VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(is, VariableAssignmentType.type, options);
      }

      public static VariableAssignmentType parse(Reader r) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(r, VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(r, VariableAssignmentType.type, options);
      }

      public static VariableAssignmentType parse(XMLStreamReader sr) throws XmlException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(sr, VariableAssignmentType.type, options);
      }

      public static VariableAssignmentType parse(Node node) throws XmlException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(node, VariableAssignmentType.type, (XmlOptions)null);
      }

      public static VariableAssignmentType parse(Node node, XmlOptions options) throws XmlException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(node, VariableAssignmentType.type, options);
      }

      /** @deprecated */
      public static VariableAssignmentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, VariableAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VariableAssignmentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VariableAssignmentType)XmlBeans.getContextTypeLoader().parse(xis, VariableAssignmentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableAssignmentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VariableAssignmentType.type, options);
      }

      private Factory() {
      }
   }

   public interface Operation extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Operation.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("operationda24elemtype");
      Enum ADD = VariableAssignmentType.Operation.Enum.forString("add");
      Enum REMOVE = VariableAssignmentType.Operation.Enum.forString("remove");
      Enum REPLACE = VariableAssignmentType.Operation.Enum.forString("replace");
      int INT_ADD = 1;
      int INT_REMOVE = 2;
      int INT_REPLACE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Operation newValue(Object obj) {
            return (Operation)VariableAssignmentType.Operation.type.newValue(obj);
         }

         public static Operation newInstance() {
            return (Operation)XmlBeans.getContextTypeLoader().newInstance(VariableAssignmentType.Operation.type, (XmlOptions)null);
         }

         public static Operation newInstance(XmlOptions options) {
            return (Operation)XmlBeans.getContextTypeLoader().newInstance(VariableAssignmentType.Operation.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ADD = 1;
         static final int INT_REMOVE = 2;
         static final int INT_REPLACE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("add", 1), new Enum("remove", 2), new Enum("replace", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }
}
