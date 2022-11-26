package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.values.XmlListImpl;
import org.apache.xmlbeans.impl.values.XmlUnionImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.NamespaceList;

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
