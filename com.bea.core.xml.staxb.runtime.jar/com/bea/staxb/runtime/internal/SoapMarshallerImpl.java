package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.EncodingStyle;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.staxb.runtime.SoapMarshaller;
import com.bea.staxb.runtime.internal.util.ArrayUtils;
import com.bea.staxb.runtime.internal.util.collections.EmptyIterator;
import com.bea.xml.XmlException;
import com.bea.xml.XmlRuntimeException;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

final class SoapMarshallerImpl implements SoapMarshaller {
   private final BindingLoader loader;
   private final RuntimeBindingTypeTable typeTable;
   private final EncodingStyle encodingStyle;
   private final InstanceVisitor instanceVisitor = new InstanceVisitor();
   private ObjectRefTable objectRefTable;

   SoapMarshallerImpl(BindingLoader loader, RuntimeBindingTypeTable typeTable, EncodingStyle encodingStyle) {
      this.loader = loader;
      this.typeTable = typeTable;
      this.encodingStyle = encodingStyle;
   }

   public void marshalType(XMLStreamWriter writer, Object obj, QName elementName, QName schemaType, String javaType, MarshalOptions options) throws XmlException {
      this.marshalType(writer, obj, elementName, XmlTypeName.forTypeNamed(schemaType), javaType, options);
   }

   public void marshalType(XMLStreamWriter writer, Object obj, QName elementName, XmlTypeName schemaType, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      RuntimeGlobalProperty prop = this.createGlobalProperty(schemaType, javaType, elementName, obj);
      if (this.objectRefTable == null && prop.getRuntimeBindingType().hasElementChildren()) {
         this.objectRefTable = new ObjectRefTable();
      }

      PushSoapMarshalResult result = this.createPushSoapResult(writer, options);
      RuntimeBindingType actual_rtt = prop.getActualRuntimeType(obj, result);
      this.addObjectGraphToRefTable(obj, prop, actual_rtt, result);
      result.marshalTopType(obj, prop, actual_rtt, options);
   }

   public void marshalType(XMLStreamWriter writer, Object obj, XmlTypeName elementName, XmlTypeName schemaType, String javaType, MarshalOptions options) throws XmlException {
      QName elem_qname = elementName.getQName();
      if (elem_qname == null) {
         throw new IllegalArgumentException("invalid element name (must have a qname): " + elementName);
      } else {
         this.marshalType(writer, obj, elem_qname, schemaType, javaType, options);
      }
   }

   private static MarshalOptions maskNull(MarshalOptions options) {
      return MarshallerImpl.maskNull(options);
   }

   private RuntimeGlobalProperty createGlobalProperty(XmlTypeName schemaType, String javaType, QName elementName, Object obj) throws XmlException {
      BindingType btype = MarshallerImpl.lookupBindingType(schemaType, javaType, elementName, obj, this.loader);

      assert btype != null;

      RuntimeBindingType runtime_type = this.typeTable.createRuntimeType(btype, this.loader);
      runtime_type.checkInstance(obj);
      RuntimeGlobalProperty prop = new RuntimeGlobalProperty(elementName, runtime_type);
      return prop;
   }

   private PushSoapMarshalResult createPushSoapResult(XMLStreamWriter writer, MarshalOptions options) throws XmlException {
      Object result;
      if (EncodingStyle.SOAP11.equals(this.encodingStyle)) {
         result = new PushSoap11MarshalResult(this.loader, this.typeTable, writer, (String)null, options, this.objectRefTable);
      } else {
         if (!EncodingStyle.SOAP12.equals(this.encodingStyle)) {
            throw new AssertionError("UNKNOWN ENCODING: " + this.encodingStyle);
         }

         result = new PushSoap12MarshalResult(this.loader, this.typeTable, writer, (String)null, options, this.objectRefTable);
      }

      return (PushSoapMarshalResult)result;
   }

   public void marshalReferenced(XMLStreamWriter writer, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      if (this.objectRefTable != null && this.objectRefTable.hasMultiplyRefdObjects()) {
         PushSoapMarshalResult result = this.createPushSoapResult(writer, options);
         result.writeIdParts();
      }
   }

   public XMLStreamReader marshalType(Object obj, QName elementName, QName schemaType, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      RuntimeGlobalProperty prop = this.createGlobalProperty(XmlTypeName.forTypeNamed(schemaType), javaType, elementName, obj);
      if (prop.getRuntimeBindingType().hasElementChildren()) {
         this.objectRefTable = new ObjectRefTable();
      }

      NamespaceContext nscontext = MarshallerImpl.getNamespaceContextFromOptions(options);
      PullSoapMarshalResult retval = this.createPullMarshalResult(prop, obj, options, false);
      this.addObjectGraphToRefTable(obj, prop, prop.getActualRuntimeType(obj, retval), retval);
      return retval;
   }

   private PullSoapMarshalResult createPullMarshalResult(RuntimeBindingProperty prop, Object obj, MarshalOptions options, boolean doing_id_parts) throws XmlException {
      if (EncodingStyle.SOAP11.equals(this.encodingStyle)) {
         PullSoapMarshalResult retval = new Soap11MarshalResult(this.loader, this.typeTable, prop, obj, options, this.objectRefTable, doing_id_parts);
         return retval;
      } else if (EncodingStyle.SOAP12.equals(this.encodingStyle)) {
         throw new AssertionError("UNIMP");
      } else {
         throw new AssertionError("UNKNOWN ENCODING: " + this.encodingStyle);
      }
   }

   private void addObjectGraphToRefTable(Object obj, RuntimeBindingProperty prop, RuntimeBindingType actual_rtt, MarshalResult result) throws XmlException {
      if (this.objectRefTable != null) {
         this.instanceVisitor.setCurrObject(obj, prop);
         this.instanceVisitor.setMarshalResult(result);
         actual_rtt.accept(this.instanceVisitor);
      }
   }

   public Iterator marshalReferenced(MarshalOptions options) throws XmlException {
      options = maskNull(options);
      return (Iterator)(this.objectRefTable != null && this.objectRefTable.hasMultiplyRefdObjects() ? new ReaderIterator(options) : EmptyIterator.getInstance());
   }

   private final class InstanceVisitor implements RuntimeTypeVisitor {
      private Object currObject;
      private RuntimeBindingProperty currProp;
      private MarshalResult marshalResult;

      private InstanceVisitor() {
      }

      public void setCurrObject(Object obj, RuntimeBindingProperty prop) {
         this.currObject = obj;
         this.currProp = prop;
      }

      public void setMarshalResult(MarshalResult marshalResult) {
         this.marshalResult = marshalResult;
      }

      public void visit(BuiltinRuntimeBindingType builtinRuntimeBindingType) throws XmlException {
         this.firstVisit(this.currObject, this.currProp, builtinRuntimeBindingType);
      }

      public void visit(AnyTypeRuntimeBindingType anyTypeRuntimeBindingType) throws XmlException {
      }

      public void visit(ByNameRuntimeBindingType byNameRuntimeBindingType) throws XmlException {
         Object curr_obj = this.currObject;
         RuntimeBindingProperty curr_prop = this.currProp;
         if (!this.firstVisit(curr_obj, curr_prop, byNameRuntimeBindingType)) {
            int elem_cnt = byNameRuntimeBindingType.getElementPropertyCount();

            for(int i = 0; i < elem_cnt; ++i) {
               RuntimeBindingProperty prop = byNameRuntimeBindingType.getElementProperty(i);
               if (!prop.getRuntimeBindingType().isJavaPrimitive() && prop.isSet(curr_obj)) {
                  Object prop_val = prop.getValue(curr_obj);
                  if (prop.isMultiple()) {
                     Iterator itr = ArrayUtils.getCollectionIterator(prop_val);

                     while(itr.hasNext()) {
                        this.visitProp(itr.next(), prop, curr_obj, curr_prop);
                     }
                  } else {
                     this.visitProp(prop_val, prop, curr_obj, curr_prop);
                  }
               }
            }

         }
      }

      private void visitProp(Object prop_val, RuntimeBindingProperty prop, Object curr_obj, RuntimeBindingProperty curr_prop) throws XmlException {
         this.setCurrObject(prop_val, prop);
         RuntimeBindingType rtt = prop.getRuntimeBindingType();
         if (rtt.hasElementChildren()) {
            prop.getActualRuntimeType(prop_val, this.marshalResult).accept(this);
         } else {
            rtt.accept(this);
         }

         this.setCurrObject(curr_obj, curr_prop);
      }

      public void visit(SimpleContentRuntimeBindingType simpleContentRuntimeBindingType) throws XmlException {
         this.firstVisit(this.currObject, this.currProp, simpleContentRuntimeBindingType);
      }

      public void visit(SimpleRuntimeBindingType simpleRuntimeBindingType) throws XmlException {
         this.firstVisit(this.currObject, this.currProp, simpleRuntimeBindingType);
      }

      public void visit(JaxrpcEnumRuntimeBindingType jaxrpcEnumRuntimeBindingType) throws XmlException {
         this.firstVisit(this.currObject, this.currProp, jaxrpcEnumRuntimeBindingType);
      }

      public void visit(WrappedArrayRuntimeBindingType wrappedArrayRuntimeBindingType) throws XmlException {
         Object curr_obj = this.currObject;
         RuntimeBindingProperty curr_prop = this.currProp;
         if (!this.firstVisit(curr_obj, curr_prop, wrappedArrayRuntimeBindingType)) {
            RuntimeBindingProperty elem_prop = wrappedArrayRuntimeBindingType.getElementProperty();
            if (!elem_prop.getRuntimeBindingType().isJavaPrimitive()) {
               Iterator itr = ArrayUtils.getCollectionIterator(curr_obj);

               while(itr.hasNext()) {
                  Object item = itr.next();
                  this.setCurrObject(item, elem_prop);
                  elem_prop.getActualRuntimeType(item, this.marshalResult).accept(this);
               }

               this.setCurrObject(curr_obj, curr_prop);
            }
         }
      }

      public void visit(SoapArrayRuntimeBindingType soapArrayRuntimeBindingType) throws XmlException {
         Object curr_obj = this.currObject;
         RuntimeBindingProperty curr_prop = this.currProp;
         if (!this.firstVisit(curr_obj, curr_prop, soapArrayRuntimeBindingType)) {
            RuntimeBindingProperty elem_prop = soapArrayRuntimeBindingType.getElementProperty();
            if (!elem_prop.getRuntimeBindingType().isJavaPrimitive()) {
               assert curr_obj.getClass().isArray();

               assert !curr_obj.getClass().getComponentType().isPrimitive();

               if (soapArrayRuntimeBindingType.isJavaCollection()) {
                  assert 1 == soapArrayRuntimeBindingType.getRanks();

                  this.walkCollectionSoapArray(curr_obj, elem_prop);
               } else {
                  this.walkSoapArray(soapArrayRuntimeBindingType.getRanks(), curr_obj, elem_prop);
               }

               this.setCurrObject(curr_obj, curr_prop);
            }
         }
      }

      private void walkCollectionSoapArray(Object coll, RuntimeBindingProperty elem_prop) throws XmlException {
         if (coll != null) {
            assert coll instanceof Collection;

            Collection c = (Collection)coll;
            Iterator itr = c.iterator();

            while(itr.hasNext()) {
               Object item = itr.next();
               this.setCurrObject(item, elem_prop);
               elem_prop.getActualRuntimeType(item, this.marshalResult).accept(this);
            }

         }
      }

      private void walkSoapArray(int ranks, Object array, RuntimeBindingProperty elem_prop) throws XmlException {
         if (array != null) {
            assert array instanceof Object[];

            assert ranks > 0;

            int i;
            if (ranks == 1) {
               Object[] ax = (Object[])((Object[])array);
               int ix = 0;

               for(i = ax.length; ix < i; ++ix) {
                  Object item = ax[ix];
                  this.setCurrObject(item, elem_prop);
                  elem_prop.getActualRuntimeType(item, this.marshalResult).accept(this);
               }
            } else {
               int new_rank = ranks - 1;
               Object[] a = (Object[])((Object[])array);
               i = 0;

               for(int alen = a.length; i < alen; ++i) {
                  this.walkSoapArray(new_rank, a[i], elem_prop);
               }
            }

         }
      }

      public void visit(ListArrayRuntimeBindingType listArrayRuntimeBindingType) throws XmlException {
         this.firstVisit(this.currObject, this.currProp, listArrayRuntimeBindingType);
      }

      private boolean firstVisit(Object obj, RuntimeBindingProperty property, RuntimeBindingType actual_rtt) {
         if (obj == null) {
            return true;
         } else {
            boolean needs_xsi_type = actual_rtt != property.getRuntimeBindingType();
            return SoapMarshallerImpl.this.objectRefTable.incrementRefCount(obj, property, needs_xsi_type) > 1;
         }
      }

      // $FF: synthetic method
      InstanceVisitor(Object x1) {
         this();
      }
   }

   private final class ReaderIterator implements Iterator {
      private final MarshalOptions options;
      private final NamespaceContext nscontext;
      private final Iterator tblItr;
      private PullSoapMarshalResult marshalResult;

      public ReaderIterator(MarshalOptions opts) {
         this.tblItr = SoapMarshallerImpl.this.objectRefTable.getMultipleRefTableEntries();
         this.options = opts;
         this.nscontext = MarshallerImpl.getNamespaceContextFromOptions(opts);
      }

      public boolean hasNext() {
         return this.tblItr.hasNext();
      }

      public Object next() {
         ObjectRefTable.Value cur_val = (ObjectRefTable.Value)this.tblItr.next();

         assert cur_val.getCnt() > 1;

         try {
            if (this.marshalResult == null) {
               this.marshalResult = SoapMarshallerImpl.this.createPullMarshalResult(cur_val.getProp(), cur_val.object, this.options, true);
            } else {
               this.marshalResult.reset(cur_val.getProp(), cur_val.object, true);
            }

            return this.marshalResult;
         } catch (XmlException var3) {
            throw new XmlRuntimeException(var3);
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
