package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.values.XmlUnionImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.FullDerivationSet;

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
