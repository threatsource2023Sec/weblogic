package weblogic.descriptor.internal;

import java.util.ArrayList;
import java.util.List;
import weblogic.descriptor.DescriptorBean;

public class QualifiedKey {
   private KeyComponent[] key = new KeyComponent[0];

   QualifiedKey(AbstractDescriptorBean bean) {
      List list = new ArrayList();
      list.add(new KeyComponent(bean._getKey(), bean._getParentProperty()));

      for(AbstractDescriptorBean parent = (AbstractDescriptorBean)bean.getParentBean(); parent != null; parent = (AbstractDescriptorBean)parent.getParentBean()) {
         list.add(new KeyComponent(parent._getKey(), parent._getParentProperty()));
      }

      this.key = (KeyComponent[])((KeyComponent[])list.toArray(this.key));
   }

   public int getLength() {
      return this.key.length;
   }

   public String toString(DescriptorBean bean) {
      StringBuffer buf = new StringBuffer();
      DescriptorBean cur = bean;

      for(int i = 0; i < this.key.length; ++i) {
         DescriptorBean parent = cur.getParentBean();
         String component = null;

         try {
            component = this.key[i].toString(parent);
         } catch (AssertionError var8) {
            component = this.key[i].toRawString(parent);
         }

         buf.insert(0, component + (i > 0 ? "/" : ""));
         cur = parent;
      }

      return buf.toString();
   }

   public int hashCode() {
      int hashCode = 0;

      for(int i = 0; i < this.key.length; ++i) {
         hashCode += this.key[i].hashCode();
      }

      return hashCode;
   }

   public boolean equals(Object o) {
      if (!(o instanceof QualifiedKey)) {
         return false;
      } else {
         KeyComponent[] otherKey = ((QualifiedKey)o).key;

         for(int i = 0; i < this.key.length && i < otherKey.length; ++i) {
            if (!this.key[i].equals(otherKey[i])) {
               return false;
            }
         }

         return otherKey.length == this.key.length;
      }
   }

   private static class KeyComponent {
      private Object localKey;
      private int propertyIdx;

      private KeyComponent(Object localKey, int propertyIdx) {
         this.localKey = localKey == null ? "" : localKey;
         this.propertyIdx = propertyIdx;
      }

      public String toString(DescriptorBean parent) {
         String s = "";
         if (parent != null && this.propertyIdx != -1) {
            s = s + ((AbstractDescriptorBean)parent)._getHelper().getPropertyName(this.propertyIdx);
         }

         if (!this.localKey.equals("")) {
            s = s + "[" + this.localKey + "]";
         }

         return s;
      }

      public String toRawString(DescriptorBean parent) {
         return "(" + parent.getClass().getName() + "." + this.propertyIdx + ")[" + this.localKey + "]";
      }

      public int hashCode() {
         return this.localKey.hashCode() ^ this.propertyIdx;
      }

      public boolean equals(Object o) {
         if (!(o instanceof KeyComponent)) {
            return false;
         } else {
            KeyComponent other = (KeyComponent)o;
            return this.propertyIdx != other.propertyIdx ? false : this.localKey.equals(other.localKey);
         }
      }

      // $FF: synthetic method
      KeyComponent(Object x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
