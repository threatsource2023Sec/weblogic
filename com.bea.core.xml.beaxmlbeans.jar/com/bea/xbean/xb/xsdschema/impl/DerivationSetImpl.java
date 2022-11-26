package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xsdschema.DerivationSet;
import com.bea.xml.SchemaType;

public class DerivationSetImpl extends XmlUnionImpl implements DerivationSet, DerivationSet.Member, DerivationSet.Member2 {
   public DerivationSetImpl(SchemaType sType) {
      super(sType, false);
   }

   protected DerivationSetImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public static class MemberImpl2 extends XmlListImpl implements DerivationSet.Member2 {
      public MemberImpl2(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl2(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class MemberImpl extends JavaStringEnumerationHolderEx implements DerivationSet.Member {
      public MemberImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
