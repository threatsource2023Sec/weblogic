package org.apache.xmlbeans.impl.xb.xmlconfig.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.values.XmlUnionImpl;
import org.apache.xmlbeans.impl.xb.xmlconfig.JavaNameList;

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
