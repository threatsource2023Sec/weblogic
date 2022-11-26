package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

abstract class PullSoapMarshalResult extends PullMarshalResult {
   private final ObjectRefTable objectRefTable;
   private final boolean writingMultiRefdObjs;

   PullSoapMarshalResult(BindingLoader loader, RuntimeBindingTypeTable tbl, RuntimeBindingProperty property, Object obj, MarshalOptions options, ObjectRefTable object_ref_table, boolean writingMultiRefdObjs) throws XmlException {
      super(loader, tbl, property, obj, options);
      this.objectRefTable = object_ref_table;
      this.writingMultiRefdObjs = writingMultiRefdObjs;
      if (writingMultiRefdObjs) {
         this.topLevelIdInit(obj);
      }

   }

   protected void reset(RuntimeBindingProperty property, Object obj, boolean writingMultiRefdObjs) throws XmlException {
      this.reset(property, obj);
      if (writingMultiRefdObjs) {
         this.topLevelIdInit(obj);
      }

   }

   private void topLevelIdInit(Object top_lvl_obj) throws XmlException {
      this.initAttributes();
      if (this.objectRefTable != null) {
         int id = this.objectRefTable.getId(top_lvl_obj);
         if (id >= 0) {
            this.fillAndAddAttribute(this.getIdAttributeName(), Soap11Constants.constructIdValueFromId(id));
         }
      }

   }

   protected XmlTypeVisitor createInitialVisitor(RuntimeBindingProperty property, Object obj) throws XmlException {
      return this.writingMultiRefdObjs ? super.createVisitor(property, obj) : this.createVisitor(property, obj);
   }

   protected XmlTypeVisitor createVisitor(RuntimeBindingProperty property, Object obj) throws XmlException {
      if (this.objectRefTable != null) {
         int id = this.objectRefTable.getId(obj);
         if (id >= 0) {
            return this.createRefdObjectVisitor(property, obj, id);
         }
      }

      return super.createVisitor(property, obj);
   }

   protected abstract XmlTypeVisitor createRefdObjectVisitor(RuntimeBindingProperty var1, Object var2, int var3) throws XmlException;

   protected abstract QName getIdAttributeName();
}
