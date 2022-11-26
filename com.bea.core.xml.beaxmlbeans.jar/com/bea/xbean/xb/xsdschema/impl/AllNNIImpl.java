package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xsdschema.AllNNI;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlNonNegativeInteger;

public class AllNNIImpl extends XmlUnionImpl implements AllNNI, XmlNonNegativeInteger, AllNNI.Member {
   public AllNNIImpl(SchemaType sType) {
      super(sType, false);
   }

   protected AllNNIImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public static class MemberImpl extends JavaStringEnumerationHolderEx implements AllNNI.Member {
      public MemberImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
