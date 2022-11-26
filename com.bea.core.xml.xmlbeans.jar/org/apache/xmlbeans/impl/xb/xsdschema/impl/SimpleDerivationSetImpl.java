package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.values.XmlUnionImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.SimpleDerivationSet;

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
