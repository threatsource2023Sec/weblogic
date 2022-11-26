package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.MemberSignature;

abstract class MemberSignatureImpl extends SignatureImpl implements MemberSignature {
   MemberSignatureImpl(int modifiers, String name, Class declaringType) {
      super(modifiers, name, declaringType);
   }

   public MemberSignatureImpl(String stringRep) {
      super(stringRep);
   }
}
