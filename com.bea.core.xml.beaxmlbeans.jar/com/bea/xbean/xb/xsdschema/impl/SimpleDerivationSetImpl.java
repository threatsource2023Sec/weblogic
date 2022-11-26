package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xsdschema.SimpleDerivationSet;
import com.bea.xml.SchemaType;

public class SimpleDerivationSetImpl extends XmlUnionImpl implements SimpleDerivationSet, SimpleDerivationSet.Member, SimpleDerivationSet.Member2 {
   public SimpleDerivationSetImpl(SchemaType sType) {
      super(sType, false);
   }

   protected SimpleDerivationSetImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public static class MemberImpl2 extends XmlListImpl implements SimpleDerivationSet.Member2 {
      public MemberImpl2(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl2(SchemaType sType, boolean b) {
         super(sType, b);
      }

      public static class ItemImpl extends JavaStringEnumerationHolderEx implements SimpleDerivationSet.Member2.Item {
         public ItemImpl(SchemaType sType) {
            super(sType, false);
         }

         protected ItemImpl(SchemaType sType, boolean b) {
            super(sType, b);
         }
      }
   }

   public static class MemberImpl extends JavaStringEnumerationHolderEx implements SimpleDerivationSet.Member {
      public MemberImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
