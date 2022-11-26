package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xsdschema.FullDerivationSet;
import com.bea.xml.SchemaType;

public class FullDerivationSetImpl extends XmlUnionImpl implements FullDerivationSet, FullDerivationSet.Member, FullDerivationSet.Member2 {
   public FullDerivationSetImpl(SchemaType sType) {
      super(sType, false);
   }

   protected FullDerivationSetImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public static class MemberImpl2 extends XmlListImpl implements FullDerivationSet.Member2 {
      public MemberImpl2(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl2(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class MemberImpl extends JavaStringEnumerationHolderEx implements FullDerivationSet.Member {
      public MemberImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
