package com.bea.xbean.xb.xmlconfig.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.values.XmlUnionImpl;
import com.bea.xbean.xb.xmlconfig.NamespaceList;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;

public class NamespaceListImpl extends XmlUnionImpl implements NamespaceList, NamespaceList.Member, NamespaceList.Member2 {
   public NamespaceListImpl(SchemaType sType) {
      super(sType, false);
   }

   protected NamespaceListImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public static class MemberImpl2 extends XmlListImpl implements NamespaceList.Member2 {
      public MemberImpl2(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl2(SchemaType sType, boolean b) {
         super(sType, b);
      }

      public static class ItemImpl extends XmlUnionImpl implements NamespaceList.Member2.Item, XmlAnyURI, NamespaceList.Member2.Item.Member {
         public ItemImpl(SchemaType sType) {
            super(sType, false);
         }

         protected ItemImpl(SchemaType sType, boolean b) {
            super(sType, b);
         }

         public static class MemberImpl extends JavaStringEnumerationHolderEx implements NamespaceList.Member2.Item.Member {
            public MemberImpl(SchemaType sType) {
               super(sType, false);
            }

            protected MemberImpl(SchemaType sType, boolean b) {
               super(sType, b);
            }
         }
      }
   }

   public static class MemberImpl extends JavaStringEnumerationHolderEx implements NamespaceList.Member {
      public MemberImpl(SchemaType sType) {
         super(sType, false);
      }

      protected MemberImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
