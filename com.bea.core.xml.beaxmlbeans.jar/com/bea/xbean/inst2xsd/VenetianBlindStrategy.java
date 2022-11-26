package com.bea.xbean.inst2xsd;

import com.bea.xbean.inst2xsd.util.Element;
import com.bea.xbean.inst2xsd.util.Type;
import com.bea.xbean.inst2xsd.util.TypeSystemHolder;
import javax.xml.namespace.QName;

public class VenetianBlindStrategy extends RussianDollStrategy implements XsdGenStrategy {
   protected void checkIfReferenceToGlobalTypeIsNeeded(Element elem, TypeSystemHolder typeSystemHolder, Inst2XsdOptions options) {
      Type elemType = elem.getType();
      QName elemName = elem.getName();
      if (!elemType.isGlobal()) {
         if (elemType.isComplexType()) {
            int i = 0;

            while(true) {
               elemType.setName(new QName(elemName.getNamespaceURI(), elemName.getLocalPart() + "Type" + (i == 0 ? "" : "" + i)));
               Type candidate = typeSystemHolder.getGlobalType(elemType.getName());
               if (candidate == null) {
                  elemType.setGlobal(true);
                  typeSystemHolder.addGlobalType(elemType);
                  break;
               }

               if (this.compatibleTypes(candidate, elemType)) {
                  this.combineTypes(candidate, elemType, options);
                  elem.setType(candidate);
                  break;
               }

               ++i;
            }
         }

      }
   }

   private boolean compatibleTypes(Type elemType, Type candidate) {
      return elemType == candidate ? true : true;
   }
}
