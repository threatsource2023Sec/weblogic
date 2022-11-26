package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.JavaTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlTypeName;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

abstract class UnmarshalResult {
   private static final boolean VERBOSE = Verbose.isVerbose(UnmarshalResult.class);
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private boolean isWrapped;
   private SOAPElement sourceSOAPElement;
   private static final int INVALID = -1;

   UnmarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable) {
      this.bindingLoader = bindingLoader;
      this.typeTable = typeTable;
   }

   protected RuntimeBindingType getRuntimeType(BindingType type) throws XmlException {
      return this.typeTable.createRuntimeType(type, this.bindingLoader);
   }

   final Object deserializeXmlObjects(boolean isWrapped, Element xml, Class targetJavaClass, QName argToDeserializeElementQName) throws XmlException {
      this.isWrapped = isWrapped;
      BindingType bindingType = this.getBindingTypeFromJavaClass(targetJavaClass, argToDeserializeElementQName);
      Object obj = this.unmarshalBindingType(xml, this.getRuntimeType(bindingType), targetJavaClass);
      if (VERBOSE) {
         Verbose.log(" deserializeXmlObjects input: " + XmlBeanUtil.toXMLString(xml));
         if (obj == null) {
            Verbose.log(" returning NULL Object");
         } else {
            Verbose.log("   returning Object of type: '" + obj.getClass().getName() + "'\n\n");
         }
      }

      return obj;
   }

   protected BindingType getBindingTypeFromJavaClass(Class javaClass, QName argToDeserializeElementQName) {
      return XmlBeanUtil.getBindingTypeFromJavaClass(javaClass, this.bindingLoader, true, argToDeserializeElementQName);
   }

   protected Object unmarshalBindingType(Element xml, RuntimeBindingType actual_rtt, Class javaClass) throws XmlException {
      if (this.hasXsiNil(xml)) {
         return NullUnmarshaller.getInstance().unmarshal(this, xml, javaClass);
      } else {
         TypeUnmarshaller um = actual_rtt.getUnmarshaller();
         return um.unmarshal(this, xml, javaClass);
      }
   }

   private BindingType determineBindingType(XmlTypeName xname, String javaType) throws XmlException {
      JavaTypeName jname = JavaTypeName.forClassName(javaType);
      BindingTypeName btname = BindingTypeName.forPair(jname, xname);
      BindingType btype = this.bindingLoader.getBindingType(btname);
      if (btype == null) {
         String msg = "unable to find binding type for " + xname + " : " + javaType;
         throw new XmlException(msg);
      } else {
         return btype;
      }
   }

   boolean isWrapped() {
      return this.isWrapped;
   }

   SOAPElement getSourceSOAPElement() {
      return this.sourceSOAPElement;
   }

   protected boolean hasXsiNil(Node n) throws XmlException {
      return XmlBeanUtil.hasXsiNil(n);
   }

   abstract void extractAndFillElementProp(RuntimeBindingProperty var1, Object var2, Node var3, Class var4) throws XmlException;
}
