package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import java.io.IOException;
import java.util.List;

public class HasMemberTypePatternForPerThisMatching extends HasMemberTypePattern {
   public HasMemberTypePatternForPerThisMatching(SignaturePattern aSignaturePattern) {
      super(aSignaturePattern);
   }

   protected boolean hasMethod(ResolvedType type) {
      boolean b = super.hasMethod(type);
      if (b) {
         return true;
      } else {
         List mungers = type.getInterTypeMungersIncludingSupers();
         return mungers.size() != 0;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      throw new IllegalAccessError("Should never be called, these are transient and don't get serialized");
   }
}
