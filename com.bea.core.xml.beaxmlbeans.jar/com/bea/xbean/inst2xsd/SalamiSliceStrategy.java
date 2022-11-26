package com.bea.xbean.inst2xsd;

import com.bea.xbean.inst2xsd.util.Element;
import com.bea.xbean.inst2xsd.util.TypeSystemHolder;

public class SalamiSliceStrategy extends RussianDollStrategy implements XsdGenStrategy {
   protected void checkIfElementReferenceIsNeeded(Element child, String parentNamespace, TypeSystemHolder typeSystemHolder, Inst2XsdOptions options) {
      Element referencedElem = new Element();
      referencedElem.setGlobal(true);
      referencedElem.setName(child.getName());
      referencedElem.setType(child.getType());
      if (child.isNillable()) {
         referencedElem.setNillable(true);
         child.setNillable(false);
      }

      referencedElem = this.addGlobalElement(referencedElem, typeSystemHolder, options);
      child.setRef(referencedElem);
   }
}
