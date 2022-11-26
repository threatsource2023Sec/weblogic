package com.bea.x2004.x03.wlw.externalConfig;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
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

public interface AnnotationInstanceBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationInstanceBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotationinstancebeand3d8type");

   String getAnnotationClassName();

   XmlString xgetAnnotationClassName();

   void setAnnotationClassName(String var1);

   void xsetAnnotationClassName(XmlString var1);

   MemberBean[] getMemberArray();

   MemberBean getMemberArray(int var1);

   int sizeOfMemberArray();

   void setMemberArray(MemberBean[] var1);

   void setMemberArray(int var1, MemberBean var2);

   MemberBean insertNewMember(int var1);

   MemberBean addNewMember();

   void removeMember(int var1);

   ArrayMemberBean[] getArrayMemberArray();

   ArrayMemberBean getArrayMemberArray(int var1);

   int sizeOfArrayMemberArray();

   void setArrayMemberArray(ArrayMemberBean[] var1);

   void setArrayMemberArray(int var1, ArrayMemberBean var2);

   ArrayMemberBean insertNewArrayMember(int var1);

   ArrayMemberBean addNewArrayMember();

   void removeArrayMember(int var1);

   NestedAnnotationBean[] getNestedAnnotationArray();

   NestedAnnotationBean getNestedAnnotationArray(int var1);

   int sizeOfNestedAnnotationArray();

   void setNestedAnnotationArray(NestedAnnotationBean[] var1);

   void setNestedAnnotationArray(int var1, NestedAnnotationBean var2);

   NestedAnnotationBean insertNewNestedAnnotation(int var1);

   NestedAnnotationBean addNewNestedAnnotation();

   void removeNestedAnnotation(int var1);

   NestedAnnotationArrayBean[] getNestedAnnotationArray1Array();

   NestedAnnotationArrayBean getNestedAnnotationArray1Array(int var1);

   int sizeOfNestedAnnotationArray1Array();

   void setNestedAnnotationArray1Array(NestedAnnotationArrayBean[] var1);

   void setNestedAnnotationArray1Array(int var1, NestedAnnotationArrayBean var2);

   NestedAnnotationArrayBean insertNewNestedAnnotationArray1(int var1);

   NestedAnnotationArrayBean addNewNestedAnnotationArray1();

   void removeNestedAnnotationArray1(int var1);

   long getUpdateCount();

   XmlLong xgetUpdateCount();

   boolean isSetUpdateCount();

   void setUpdateCount(long var1);

   void xsetUpdateCount(XmlLong var1);

   void unsetUpdateCount();

   public static final class Factory {
      public static AnnotationInstanceBean newInstance() {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean newInstance(XmlOptions options) {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationInstanceBean.type, options);
      }

      public static AnnotationInstanceBean parse(String xmlAsString) throws XmlException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationInstanceBean.type, options);
      }

      public static AnnotationInstanceBean parse(File file) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationInstanceBean.type, options);
      }

      public static AnnotationInstanceBean parse(URL u) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationInstanceBean.type, options);
      }

      public static AnnotationInstanceBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationInstanceBean.type, options);
      }

      public static AnnotationInstanceBean parse(Reader r) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationInstanceBean.type, options);
      }

      public static AnnotationInstanceBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationInstanceBean.type, options);
      }

      public static AnnotationInstanceBean parse(Node node) throws XmlException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      public static AnnotationInstanceBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationInstanceBean.type, options);
      }

      /** @deprecated */
      public static AnnotationInstanceBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationInstanceBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationInstanceBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationInstanceBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationInstanceBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationInstanceBean.type, options);
      }

      private Factory() {
      }
   }
}
