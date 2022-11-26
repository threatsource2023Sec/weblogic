package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.xml.XmlException;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

abstract class PushSoapMarshalResult extends PushMarshalResult {
   private final ObjectRefTable objectRefTable;

   PushSoapMarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, XMLStreamWriter writer, String default_namespace_uri, MarshalOptions options, ObjectRefTable objectRefTable) throws XmlException {
      super(bindingLoader, typeTable, writer, default_namespace_uri, options);
      this.objectRefTable = objectRefTable;
   }

   final void writeIdParts() throws XmlException {
      Iterator itr = this.objectRefTable.getMultipleRefTableEntries();

      while(itr.hasNext()) {
         ObjectRefTable.Value cur_val = (ObjectRefTable.Value)itr.next();
         this.marshalTypeWithId(cur_val);
      }

   }

   private void marshalTypeWithId(ObjectRefTable.Value val) throws XmlException {
      RuntimeBindingProperty prop = val.getProp();
      Object obj = val.object;
      RuntimeBindingType actual_rtt = prop.getActualRuntimeType(obj, this);

      try {
         boolean needs_wrapped = !actual_rtt.containsOwnContainingElement(obj);
         if (needs_wrapped) {
            this.writeStartElement(actual_rtt.getMultiRefElementName(), actual_rtt.canUseDefaultNamespace(obj));
            this.fillAndAddAttribute(this.getIdQName(), this.getIdValue(val.getId()));
         }

         this.updateState(obj, prop);
         if (needs_wrapped) {
            if (obj == null) {
               this.addXsiNilAttribute();
               if (val.needsXsiType) {
                  this.addXsiTypeAttribute(actual_rtt);
               }
            } else if (val.needsXsiType) {
               this.addXsiTypeAttribute(actual_rtt);
            }
         }

         actual_rtt.accept(this);
         if (needs_wrapped) {
            this.writeEndElement();
         }

      } catch (XMLStreamException var6) {
         throw new XmlException(var6);
      }
   }

   protected final void writeContents(RuntimeBindingType actual_rtt) throws XmlException {
      int id = this.objectRefTable == null ? -1 : this.objectRefTable.getId(this.getCurrObject());
      if (id < 0) {
         super.writeContents(actual_rtt);
      } else {
         this.fillAndAddAttribute(this.getRefQName(), this.getRefValue(id));
      }

   }

   protected abstract QName getRefQName();

   protected abstract String getRefValue(int var1);

   protected abstract QName getIdQName();

   protected abstract String getIdValue(int var1);
}
