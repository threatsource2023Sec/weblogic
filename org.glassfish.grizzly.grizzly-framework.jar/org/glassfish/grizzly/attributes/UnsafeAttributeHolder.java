package org.glassfish.grizzly.attributes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class UnsafeAttributeHolder implements AttributeHolder {
   final DefaultAttributeBuilder attributeBuilder;
   final IndexedAttributeAccessorImpl indexedAttributeAccessor;
   private final Holder h1 = new Holder();
   private final Holder h2 = new Holder();
   private final Holder h3 = new Holder();
   private final Holder h4 = new Holder();
   private Map valueMap;
   private boolean isSet;

   UnsafeAttributeHolder(DefaultAttributeBuilder attributeBuilder) {
      this.attributeBuilder = attributeBuilder;
      this.indexedAttributeAccessor = new IndexedAttributeAccessorImpl();
   }

   public Object getAttribute(String name) {
      return this.getAttribute(name, (org.glassfish.grizzly.utils.NullaryFunction)null);
   }

   public Object getAttribute(String name, org.glassfish.grizzly.utils.NullaryFunction initializer) {
      if (!this.isSet && initializer == null) {
         return null;
      } else {
         Attribute attribute = this.attributeBuilder.getAttributeByName(name);
         if (attribute != null) {
            return this.indexedAttributeAccessor.getAttribute(attribute, initializer);
         } else {
            return initializer != null ? initializer.evaluate() : null;
         }
      }
   }

   public void setAttribute(String name, Object value) {
      Attribute attribute = this.attributeBuilder.getAttributeByName(name);
      if (attribute == null) {
         attribute = this.attributeBuilder.createAttribute(name);
      }

      this.indexedAttributeAccessor.setAttribute(attribute, value);
   }

   public Object removeAttribute(String name) {
      if (!this.isSet) {
         return null;
      } else {
         Attribute attribute = this.attributeBuilder.getAttributeByName(name);
         return attribute != null ? this.indexedAttributeAccessor.removeAttribute(attribute) : null;
      }
   }

   public Set getAttributeNames() {
      if (!this.isSet) {
         return null;
      } else {
         Set tmpSet = new HashSet(4);
         if (this.h1.isSet && this.h1.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(this.h1.idx).name());
         }

         if (this.h2.isSet && this.h2.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(this.h2.idx).name());
         }

         if (this.h3.isSet && this.h3.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(this.h3.idx).name());
         }

         if (this.h4.isSet && this.h4.value != null) {
            tmpSet.add(this.attributeBuilder.getAttributeByIndex(this.h4.idx).name());
         }

         if (this.valueMap != null) {
            Iterator var2 = this.valueMap.keySet().iterator();

            while(var2.hasNext()) {
               Integer idx = (Integer)var2.next();
               tmpSet.add(this.attributeBuilder.getAttributeByIndex(idx).name());
            }
         }

         return tmpSet;
      }
   }

   public void clear() {
      if (this.isSet) {
         this.isSet = false;
         this.h1.clear();
         this.h2.clear();
         this.h3.clear();
         this.h4.clear();
         this.valueMap = null;
      }
   }

   public void recycle() {
      this.clear();
   }

   public AttributeBuilder getAttributeBuilder() {
      return this.attributeBuilder;
   }

   public IndexedAttributeAccessor getIndexedAttributeAccessor() {
      return this.indexedAttributeAccessor;
   }

   public void copyFrom(AttributeHolder srcAttributes) {
      if (srcAttributes == null) {
         throw new NullPointerException("srcAttributes can't be null");
      } else {
         if (srcAttributes instanceof UnsafeAttributeHolder) {
            UnsafeAttributeHolder srcUnsafe = (UnsafeAttributeHolder)srcAttributes;
            if (!srcUnsafe.isSet) {
               this.clear();
               return;
            }

            this.isSet = true;
            this.h1.copyFrom(srcUnsafe.h1);
            this.h2.copyFrom(srcUnsafe.h2);
            this.h3.copyFrom(srcUnsafe.h3);
            this.h4.copyFrom(srcUnsafe.h4);
            if (this.valueMap != null || srcUnsafe.valueMap != null) {
               UnsafeAttributeHolder.MapperAccessor.copy(srcUnsafe, this);
            }
         } else {
            this.clear();
            Set names = srcAttributes.getAttributeNames();
            Iterator var3 = names.iterator();

            while(var3.hasNext()) {
               String name = (String)var3.next();
               this.setAttribute(name, srcAttributes.getAttribute(name));
            }
         }

      }
   }

   public void copyTo(AttributeHolder dstAttributes) {
      if (dstAttributes == null) {
         throw new NullPointerException("dstAttributes can't be null");
      } else if (!this.isSet) {
         dstAttributes.clear();
      } else {
         if (dstAttributes instanceof UnsafeAttributeHolder) {
            UnsafeAttributeHolder dstUnsafe = (UnsafeAttributeHolder)dstAttributes;
            dstUnsafe.isSet = true;
            dstUnsafe.h1.copyFrom(this.h1);
            dstUnsafe.h2.copyFrom(this.h2);
            dstUnsafe.h3.copyFrom(this.h3);
            dstUnsafe.h4.copyFrom(this.h4);
            if (this.valueMap != null || dstUnsafe.valueMap != null) {
               UnsafeAttributeHolder.MapperAccessor.copy(this, dstUnsafe);
            }
         } else {
            dstAttributes.clear();
            Set names = this.getAttributeNames();
            Iterator var3 = names.iterator();

            while(var3.hasNext()) {
               String name = (String)var3.next();
               dstAttributes.setAttribute(name, this.getAttribute(name));
            }
         }

      }
   }

   private static final class MapperAccessor {
      private static Object getValue(UnsafeAttributeHolder holder, Integer idx) {
         return holder.valueMap.get(idx);
      }

      private static Object setValue(UnsafeAttributeHolder holder, Integer idx, Object value) {
         if (value == null) {
            return holder.valueMap != null ? holder.valueMap.remove(idx) : null;
         } else {
            if (holder.valueMap == null) {
               holder.valueMap = new HashMap(4);
            }

            return holder.valueMap.put(idx, value);
         }
      }

      private static void copy(UnsafeAttributeHolder src, UnsafeAttributeHolder dst) {
         if (src.valueMap != null) {
            if (dst.valueMap == null) {
               dst.valueMap = new HashMap(src.valueMap.size());
            } else {
               dst.valueMap.clear();
            }

            dst.valueMap.putAll(src.valueMap);
         } else {
            dst.valueMap = null;
         }

      }
   }

   private static final class Holder {
      int idx;
      Object value;
      boolean isSet;

      private Holder() {
      }

      Object set(int idx, Object value) {
         Object oldValue = this.value;
         this.idx = idx;
         this.value = value;
         this.isSet = true;
         return oldValue;
      }

      void clear() {
         if (this.isSet) {
            this.idx = -1;
            this.value = null;
            this.isSet = false;
         }

      }

      private boolean is(int idx) {
         return this.isSet && this.idx == idx;
      }

      private void copyFrom(Holder src) {
         this.isSet = src.isSet;
         this.idx = src.idx;
         this.value = src.value;
      }

      // $FF: synthetic method
      Holder(Object x0) {
         this();
      }
   }

   protected final class IndexedAttributeAccessorImpl implements IndexedAttributeAccessor {
      public Object getAttribute(int index) {
         return this.getAttribute(index, (org.glassfish.grizzly.utils.NullaryFunction)null);
      }

      public Object getAttribute(int index, org.glassfish.grizzly.utils.NullaryFunction initializer) {
         return !UnsafeAttributeHolder.this.isSet && initializer == null ? null : this.getAttribute(UnsafeAttributeHolder.this.attributeBuilder.getAttributeByIndex(index), initializer);
      }

      public void setAttribute(int index, Object value) {
         this.setAttribute(UnsafeAttributeHolder.this.attributeBuilder.getAttributeByIndex(index), value);
      }

      public Object removeAttribute(int index) {
         return this.removeAttribute(UnsafeAttributeHolder.this.attributeBuilder.getAttributeByIndex(index));
      }

      private Object getAttribute(Attribute attribute, org.glassfish.grizzly.utils.NullaryFunction initializer) {
         int idx = attribute.index();
         Holder h = this.holderByIdx(idx);
         if (h != null) {
            if (h.value == null && initializer != null) {
               h.value = initializer.evaluate();
            }

            return h.value;
         } else {
            Object value = UnsafeAttributeHolder.this.valueMap != null ? UnsafeAttributeHolder.MapperAccessor.getValue(UnsafeAttributeHolder.this, idx) : null;
            if (value == null && initializer != null) {
               value = initializer.evaluate();
               this.setAttribute(attribute, value);
            }

            return value;
         }
      }

      private Object setAttribute(Attribute attribute, Object value) {
         if (!UnsafeAttributeHolder.this.isSet) {
            if (value != null) {
               UnsafeAttributeHolder.this.isSet = true;
               UnsafeAttributeHolder.this.h1.set(attribute.index(), value);
            }

            return null;
         } else {
            UnsafeAttributeHolder.this.isSet = true;
            int idx = attribute.index();
            Holder h = this.holderByIdx(idx);
            if (h != null) {
               return h.set(idx, value);
            } else if (UnsafeAttributeHolder.this.valueMap != null && UnsafeAttributeHolder.this.valueMap.get(idx) != value) {
               return UnsafeAttributeHolder.MapperAccessor.setValue(UnsafeAttributeHolder.this, idx, value);
            } else if (value == null) {
               return null;
            } else {
               h = this.emptyHolder();
               if (h != null) {
                  h.set(idx, value);
                  return null;
               } else {
                  h = this.nullHolder();
                  if (h != null) {
                     h.set(idx, value);
                     return null;
                  } else {
                     return UnsafeAttributeHolder.MapperAccessor.setValue(UnsafeAttributeHolder.this, idx, value);
                  }
               }
            }
         }
      }

      private Object removeAttribute(Attribute attribute) {
         return this.setAttribute(attribute, (Object)null);
      }

      private Holder holderByIdx(int idx) {
         if (UnsafeAttributeHolder.this.h1.is(idx)) {
            return UnsafeAttributeHolder.this.h1;
         } else if (UnsafeAttributeHolder.this.h2.is(idx)) {
            return UnsafeAttributeHolder.this.h2;
         } else if (UnsafeAttributeHolder.this.h3.is(idx)) {
            return UnsafeAttributeHolder.this.h3;
         } else {
            return UnsafeAttributeHolder.this.h4.is(idx) ? UnsafeAttributeHolder.this.h4 : null;
         }
      }

      private Holder emptyHolder() {
         if (!UnsafeAttributeHolder.this.h1.isSet) {
            return UnsafeAttributeHolder.this.h1;
         } else if (!UnsafeAttributeHolder.this.h2.isSet) {
            return UnsafeAttributeHolder.this.h2;
         } else if (!UnsafeAttributeHolder.this.h3.isSet) {
            return UnsafeAttributeHolder.this.h3;
         } else {
            return !UnsafeAttributeHolder.this.h4.isSet ? UnsafeAttributeHolder.this.h4 : null;
         }
      }

      private Holder nullHolder() {
         if (UnsafeAttributeHolder.this.h1.value == null) {
            return UnsafeAttributeHolder.this.h1;
         } else if (UnsafeAttributeHolder.this.h2.value == null) {
            return UnsafeAttributeHolder.this.h2;
         } else if (UnsafeAttributeHolder.this.h3.value == null) {
            return UnsafeAttributeHolder.this.h3;
         } else {
            return UnsafeAttributeHolder.this.h4.value == null ? UnsafeAttributeHolder.this.h4 : null;
         }
      }
   }
}
