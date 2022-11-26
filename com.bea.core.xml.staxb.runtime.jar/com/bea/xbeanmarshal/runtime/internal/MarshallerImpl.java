package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BaseBindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.JavaTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.WrappedArrayType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanDocumentType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlTypeName;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.Marshaller;
import com.bea.xbeanmarshal.runtime.internal.util.Verbose;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import org.w3c.dom.Document;

final class MarshallerImpl implements Marshaller {
   private final BindingLoader loader;
   private final RuntimeBindingTypeTable typeTable;
   private static final boolean VERBOSE = Verbose.isVerbose(MarshallerImpl.class);
   private static final String XML_VERSION = "1.0";

   public MarshallerImpl(BindingLoader loader, RuntimeBindingTypeTable typeTable) {
      this.loader = loader;
      this.typeTable = typeTable;
      if (VERBOSE) {
         Verbose.log("\n DUMPING XmlBean Loader \n");
         Verbose.log(((BaseBindingLoader)loader).dumpBindingTypes());
      }

   }

   public void serializeXmlObject(boolean isWrapped, Document document, SOAPElement parent, Object argToSerialize, QName argToSerializeElementQName) throws XmlException {
      if (argToSerialize == null) {
         PushMarshalResult pmr = new LiteralPushMarshalResult(this.loader, this.typeTable, parent);
         pmr.serializeNullXmlObject(document, parent, argToSerialize, argToSerializeElementQName);
      } else {
         JavaTypeName jtn = determineJavaType(argToSerialize);
         BindingType bt = lookupBindingType(argToSerialize.getClass(), jtn, this.loader, true, argToSerializeElementQName);
         if (bt == null) {
            bt = this.createBindingType(argToSerializeElementQName, jtn, argToSerialize);
         }

         RuntimeBindingType runtime_type = this.typeTable.createRuntimeType(bt, this.loader);
         runtime_type.checkInstance(argToSerialize);
         RuntimeGlobalProperty prop = new RuntimeGlobalProperty(isWrapped, document, argToSerializeElementQName, runtime_type);
         PushMarshalResult pmr = new LiteralPushMarshalResult(this.loader, this.typeTable, parent);
         RuntimeBindingType actual_rtt = prop.getActualRuntimeType(argToSerialize, pmr);
         pmr.marshalTopType(argToSerialize, prop, actual_rtt);
      }
   }

   private BindingType createBindingType(QName argToSerializeElementQName, JavaTypeName jtn, Object argToSerialize) {
      XmlTypeName xName = XmlTypeName.forGlobalName('e', argToSerializeElementQName);
      BindingTypeName btName = BindingTypeName.forPair(jtn, xName);
      if (argToSerialize.getClass().isArray()) {
         return WrappedArrayType.wrappedArrayTypeFor(argToSerialize.getClass().getComponentType());
      } else {
         return (BindingType)(XmlBeanUtil.isXmlBeanDocument(argToSerialize) ? new XmlBeanDocumentType(btName) : new XmlBeanType(btName));
      }
   }

   private XmlTypeName lookupRootElementName(JavaTypeName jname) throws XmlException {
      BindingTypeName root_elem_btype = this.loader.lookupElementFor(jname);
      if (root_elem_btype == null) {
         String msg = "failed to find root element corresponding to " + jname;
         throw new XmlException(msg);
      } else {
         XmlTypeName elem = root_elem_btype.getXmlName();

         assert elem.getComponentType() == 101;

         return elem;
      }
   }

   private static JavaTypeName determineJavaType(Object obj) {
      return determineJavaType(obj.getClass());
   }

   private static JavaTypeName determineJavaType(Class clazz) {
      return JavaTypeName.forClassName(clazz.getName());
   }

   private BindingType getPojoBindingType(XmlTypeName type_name) throws XmlException {
      BindingTypeName btName = this.loader.lookupPojoFor(type_name);
      if (btName == null) {
         String msg = "failed to load java type corresponding to " + type_name;
         throw new XmlException(msg);
      } else {
         BindingType bt = this.loader.getBindingType(btName);
         if (bt == null) {
            String msg = "failed to load BindingType for " + btName;
            throw new XmlException(msg);
         } else {
            return bt;
         }
      }
   }

   static BindingType lookupBindingType(Class instance_type, JavaTypeName java_type, BindingLoader loader, boolean check_supertypes, QName argToSerializeElementQName) throws XmlException {
      return XmlBeanUtil.getBindingTypeFromJavaClass(instance_type, loader, check_supertypes, argToSerializeElementQName);
   }
}
