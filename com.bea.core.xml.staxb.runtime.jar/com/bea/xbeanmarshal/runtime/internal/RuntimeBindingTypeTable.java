package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbean.common.ConcurrentReaderHashMap;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingTypeVisitor;
import com.bea.xbeanmarshal.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.WrappedArrayType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanAnyType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanBuiltinType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanDocumentType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeanType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeansBuiltinBindingLoader;
import com.bea.xml.XmlException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

final class RuntimeBindingTypeTable {
   private final Map initedTypeMap;
   private final Map tempTypeMap;
   private final FactoryTypeVisitor typeVisitor;
   static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
   private static final String SOAPENC_NS = "http://schemas.xmlsoap.org/soap/encoding/";
   private static final ConcurrentReaderHashMap BUILTIN_TYPE_MAP;

   static RuntimeBindingTypeTable createTable() {
      RuntimeBindingTypeTable tbl = new RuntimeBindingTypeTable((Map)BUILTIN_TYPE_MAP.clone());
      return tbl;
   }

   private RuntimeBindingTypeTable(Map typeMap) {
      this.tempTypeMap = new HashMap();
      this.typeVisitor = new FactoryTypeVisitor();
      this.initedTypeMap = typeMap;
   }

   private RuntimeBindingTypeTable() {
      this(new ConcurrentReaderHashMap());
   }

   RuntimeBindingType createRuntimeType(BindingType type, BindingLoader binding_loader) throws XmlException {
      assert type != null;

      RuntimeBindingType rtype = (RuntimeBindingType)this.initedTypeMap.get(type);
      if (rtype != null) {
         return rtype;
      } else {
         synchronized(this) {
            rtype = (RuntimeBindingType)this.tempTypeMap.get(type);
            if (rtype == null) {
               rtype = this.allocateType(type);
               this.tempTypeMap.put(type, rtype);
               rtype.external_initialize(this, binding_loader);
               this.initedTypeMap.put(type, rtype);
               this.tempTypeMap.remove(type);
            }
         }

         assert rtype != null;

         return rtype;
      }
   }

   private RuntimeBindingType allocateType(BindingType type) throws XmlException {
      type.accept(this.typeVisitor);
      return this.typeVisitor.getRuntimeBindingType();
   }

   private void addBuiltinTypes() throws XmlException {
      BuiltinBindingLoader xmlBeansBuiltinLoader = XmlBeansBuiltinBindingLoader.getInstance();
      Iterator it = xmlBeansBuiltinLoader.bindingTypes().iterator();

      while(it.hasNext()) {
         BindingType bt = (BindingType)it.next();
         XmlBeanBuiltinType xbt = new XmlBeanBuiltinType(bt.getName());
         XmlBeanBuiltinUnmarshaller um = new XmlBeanBuiltinUnmarshaller();
         RuntimeBindingType rbt = new XmlBeanBuiltinRuntimeBindingType(xbt, um);
         this.initedTypeMap.put(bt, rbt);
      }

   }

   private XmlBeanTypeRuntimeBindingType createRuntimeType(XmlBeanType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (XmlBeanTypeRuntimeBindingType)rtt;
   }

   private XmlBeanDocumentRuntimeBindingType createRuntimeType(XmlBeanDocumentType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (XmlBeanDocumentRuntimeBindingType)rtt;
   }

   private XmlBeanBuiltinRuntimeBindingType createRuntimeType(XmlBeanBuiltinType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (XmlBeanBuiltinRuntimeBindingType)rtt;
   }

   private WrappedArrayRuntimeBindingType createRuntimeType(WrappedArrayType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (WrappedArrayRuntimeBindingType)rtt;
   }

   private RuntimeBindingType createRuntimeTypeInternal(BindingType type, BindingLoader loader) throws XmlException {
      return this.createRuntimeType(type, loader);
   }

   TypeUnmarshaller createUnmarshaller(BindingType binding_type, BindingLoader loader) throws XmlException {
      TypeVisitor type_visitor = new TypeVisitor(this, loader);
      binding_type.accept(type_visitor);
      TypeUnmarshaller type_um = type_visitor.getUnmarshaller();
      type_um.initialize(this, loader);
      return type_um;
   }

   private TypeMarshaller createMarshaller(BindingTypeName type_name, BindingLoader loader) throws XmlException {
      BindingType binding_type = loader.getBindingType(type_name);
      if (binding_type == null) {
         String msg = "unable to load type for " + type_name;
         throw new XmlException(msg);
      } else {
         return this.createMarshaller(binding_type, loader);
      }
   }

   TypeMarshaller createMarshaller(BindingType binding_type, BindingLoader loader) throws XmlException {
      TypeMarshaller m = null;
      return (TypeMarshaller)m;
   }

   static {
      RuntimeBindingTypeTable tbl = new RuntimeBindingTypeTable();

      try {
         tbl.addBuiltinTypes();
      } catch (XmlException var2) {
         throw new IllegalArgumentException(" could not initialize XmlBean builtin type map " + var2.getMessage());
      }

      BUILTIN_TYPE_MAP = (ConcurrentReaderHashMap)tbl.initedTypeMap;
   }

   private static final class FactoryTypeVisitor implements BindingTypeVisitor {
      private RuntimeBindingType runtimeBindingType;

      private FactoryTypeVisitor() {
      }

      public RuntimeBindingType getRuntimeBindingType() {
         return this.runtimeBindingType;
      }

      public void visit(WrappedArrayType wrappedArrayType) throws XmlException {
         this.runtimeBindingType = new WrappedArrayRuntimeBindingType(wrappedArrayType);
      }

      public void visit(XmlBeanType xmlBeanType) throws XmlException {
         this.runtimeBindingType = new XmlBeanTypeRuntimeBindingType(xmlBeanType);
      }

      public void visit(XmlBeanDocumentType xmlBeanDocumentType) throws XmlException {
         this.runtimeBindingType = new XmlBeanDocumentRuntimeBindingType(xmlBeanDocumentType);
      }

      public void visit(XmlBeanBuiltinType xmlBeanBuiltinType) throws XmlException {
         throw new AssertionError("internal error: no builtin unmarshaller for " + xmlBeanBuiltinType);
      }

      public void visit(XmlBeanAnyType bindingType) throws XmlException {
         this.runtimeBindingType = new XmlBeanAnyRuntimeBindingType(bindingType);
      }

      // $FF: synthetic method
      FactoryTypeVisitor(Object x0) {
         this();
      }
   }

   private static final class TypeVisitor implements BindingTypeVisitor {
      private final BindingLoader loader;
      private final RuntimeBindingTypeTable typeTable;
      private TypeUnmarshaller typeUnmarshaller;

      public TypeVisitor(RuntimeBindingTypeTable runtimeBindingTypeTable, BindingLoader loader) {
         this.typeTable = runtimeBindingTypeTable;
         this.loader = loader;
      }

      public void visit(WrappedArrayType wrappedArrayType) throws XmlException {
         WrappedArrayRuntimeBindingType rtt = this.typeTable.createRuntimeType(wrappedArrayType, this.loader);
         this.typeUnmarshaller = new WrappedArrayUnmarshaller(rtt);
      }

      public void visit(XmlBeanType xmlBeanType) throws XmlException {
         XmlBeanTypeRuntimeBindingType rtt = this.typeTable.createRuntimeType(xmlBeanType, this.loader);
         this.typeUnmarshaller = new XmlBeanTypeUnmarshaller(rtt);
      }

      public void visit(XmlBeanDocumentType xmlBeanDocumentType) throws XmlException {
         XmlBeanDocumentRuntimeBindingType rtt = this.typeTable.createRuntimeType(xmlBeanDocumentType, this.loader);
         this.typeUnmarshaller = new XmlBeanDocumentUnmarshaller(rtt);
      }

      public void visit(XmlBeanAnyType xmlBeanAnyType) throws XmlException {
         this.typeUnmarshaller = new XmlBeanAnyUnmarshaller();
      }

      public void visit(XmlBeanBuiltinType xmlBeanBuiltinType) throws XmlException {
         String s = "internal error: no builtin runtime type for " + xmlBeanBuiltinType;
         throw new AssertionError(s);
      }

      public TypeUnmarshaller getUnmarshaller() {
         assert this.typeUnmarshaller != null;

         return this.typeUnmarshaller;
      }
   }
}
