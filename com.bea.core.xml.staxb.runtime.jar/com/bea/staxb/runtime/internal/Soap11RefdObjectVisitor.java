package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

class Soap11RefdObjectVisitor extends RefdObjectVisitor {
   public Soap11RefdObjectVisitor(RuntimeBindingProperty property, Object obj, PullMarshalResult result, int id) throws XmlException {
      super(property, obj, result, id);
   }

   protected QName getRefQName() {
      return Soap11Constants.REF_NAME;
   }

   protected String getRefValue() {
      return Soap11Constants.constructRefValueFromId(this.id);
   }
}
