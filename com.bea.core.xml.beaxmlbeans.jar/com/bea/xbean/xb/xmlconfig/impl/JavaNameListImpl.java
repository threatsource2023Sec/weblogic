package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xmlconfig.JavaNameList;
import com.bea.xml.SchemaType;

public class JavaNameListImpl extends XmlUnionImpl implements JavaNameList, JavaNameList.Member, JavaNameList.Member2 {
   public JavaNameListImpl(SchemaType sType) {
      super(sType, false);
   }

   protected JavaNameListImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public static class MemberImpl2 extends XmlListImpl implements JavaNameList.Member2 {
      public MemberImpl2(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl2(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class MemberImpl extends JavaStringEnumerationHolderEx implements JavaNameList.Member {
      public MemberImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
