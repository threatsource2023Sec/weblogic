package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

final class Soap11MarshalResult extends PullSoapMarshalResult {
   private static final QName ID_NAME = new QName("id");

   Soap11MarshalResult(BindingLoader loader, RuntimeBindingTypeTable tbl, RuntimeBindingProperty property, Object obj, MarshalOptions options, ObjectRefTable object_ref_table, boolean writingMultiRefdObjs) throws XmlException {
      super(loader, tbl, property, obj, options, object_ref_table, writingMultiRefdObjs);
   }

   protected XmlTypeVisitor createRefdObjectVisitor(RuntimeBindingProperty property, Object obj, int id) throws XmlException {
      return new Soap11RefdObjectVisitor(property, obj, this, id);
   }

   protected QName getIdAttributeName() {
      return ID_NAME;
   }
}
