package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xsdschema.BlockSet;
import com.bea.xml.SchemaType;

public class BlockSetImpl extends XmlUnionImpl implements BlockSet, BlockSet.Member, BlockSet.Member2 {
   public BlockSetImpl(SchemaType sType) {
      super(sType, false);
   }

   protected BlockSetImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public static class MemberImpl2 extends XmlListImpl implements BlockSet.Member2 {
      public MemberImpl2(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl2(SchemaType sType, boolean b) {
         super(sType, b);
      }

      public static class ItemImpl extends JavaStringEnumerationHolderEx implements BlockSet.Member2.Item {
         public ItemImpl(SchemaType sType) {
            super(sType, false);
         }

         protected ItemImpl(SchemaType sType, boolean b) {
            super(sType, b);
         }
      }
   }

   public static class MemberImpl extends JavaStringEnumerationHolderEx implements BlockSet.Member {
      public MemberImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
