package org.glassfish.grizzly.attributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class IndexedAttributeHolder implements AttributeHolder {
   private final Object sync = new Object();
   private volatile int count;
   private Snapshot state;
   protected final DefaultAttributeBuilder attributeBuilder;
   protected final IndexedAttributeAccessor indexedAttributeAccessor;

   /** @deprecated */
   public IndexedAttributeHolder(AttributeBuilder attributeBuilder) {
      this.attributeBuilder = (DefaultAttributeBuilder)attributeBuilder;
      this.state = new Snapshot(new Object[4], new int[]{-1, -1, -1, -1}, 0);
      this.indexedAttributeAccessor = new IndexedAttributeAccessorImpl();
   }

   public Object getAttribute(String name) {
      return this.getAttribute(name, (org.glassfish.grizzly.utils.NullaryFunction)null);
   }

   public Object getAttribute(String name, org.glassfish.grizzly.utils.NullaryFunction initializer) {
      Attribute attribute = this.attributeBuilder.getAttributeByName(name);
      if (attribute != null) {
         return this.indexedAttributeAccessor.getAttribute(attribute.index(), initializer);
      } else {
         return initializer != null ? initializer : null;
      }
   }

   public void setAttribute(String name, Object value) {
      Attribute attribute = this.attributeBuilder.getAttributeByName(name);
      if (attribute == null) {
         attribute = this.attributeBuilder.createAttribute(name);
      }

      this.indexedAttributeAccessor.setAttribute(attribute.index(), value);
   }

   public Object removeAttribute(String name) {
      Attribute attribute = this.attributeBuilder.getAttributeByName(name);
      return attribute != null ? this.indexedAttributeAccessor.removeAttribute(attribute.index()) : null;
   }

   public Set getAttributeNames() {
      if (this.count != 0) {
         Set result = new HashSet();
         Snapshot stateNow = this.state;
         int localSize = stateNow.size;
         Object[] localAttributeValues = stateNow.values;

         for(int i = 0; i < localSize; ++i) {
            Object value = localAttributeValues[i];
            if (value != null) {
               Attribute attribute = this.attributeBuilder.getAttributeByIndex(i);
               result.add(attribute.name());
            }
         }

         return result;
      } else {
         return Collections.emptySet();
      }
   }

   public void copyFrom(AttributeHolder srcAttributes) {
      if (srcAttributes instanceof IndexedAttributeHolder) {
         IndexedAttributeHolder iah = (IndexedAttributeHolder)srcAttributes;
         Snapshot stateNow = this.state;
         Snapshot srcState = iah.state;
         int[] newI2v = stateNow.i2v;
         if (newI2v.length < srcState.i2v.length) {
            newI2v = Arrays.copyOf(srcState.i2v, srcState.i2v.length);
         } else {
            System.arraycopy(srcState.i2v, 0, newI2v, 0, srcState.i2v.length);

            for(int i = srcState.i2v.length; i < newI2v.length; ++i) {
               newI2v[i] = -1;
            }
         }

         Object[] newValues = stateNow.values;
         if (newValues.length < srcState.size) {
            newValues = Arrays.copyOf(srcState.values, srcState.size);
         } else {
            System.arraycopy(srcState.values, 0, newValues, 0, srcState.size);

            for(int i = srcState.size; i < stateNow.size; ++i) {
               newValues[i] = null;
            }
         }

         Snapshot newState = new Snapshot(newValues, newI2v, srcState.size);
         this.state = newState;
         ++this.count;
      } else {
         this.clear();
         Set names = srcAttributes.getAttributeNames();
         if (names.isEmpty()) {
            return;
         }

         Iterator var9 = names.iterator();

         while(var9.hasNext()) {
            String name = (String)var9.next();
            this.setAttribute(name, srcAttributes.getAttribute(name));
         }
      }

   }

   public void copyTo(AttributeHolder dstAttributes) {
      if (this.count != 0) {
         if (dstAttributes instanceof IndexedAttributeHolder) {
            IndexedAttributeHolder iah = (IndexedAttributeHolder)dstAttributes;
            Snapshot stateNow = this.state;
            Snapshot dstState = iah.state;
            int[] newI2v = dstState.i2v;
            if (newI2v.length < stateNow.i2v.length) {
               newI2v = Arrays.copyOf(stateNow.i2v, stateNow.i2v.length);
            } else {
               System.arraycopy(stateNow.i2v, 0, newI2v, 0, stateNow.i2v.length);

               for(int i = stateNow.i2v.length; i < newI2v.length; ++i) {
                  newI2v[i] = -1;
               }
            }

            Object[] newValues = dstState.values;
            if (newValues.length < stateNow.size) {
               newValues = Arrays.copyOf(stateNow.values, stateNow.size);
            } else {
               System.arraycopy(stateNow.values, 0, newValues, 0, stateNow.size);

               for(int i = stateNow.size; i < dstState.size; ++i) {
                  newValues[i] = null;
               }
            }

            Snapshot newState = new Snapshot(newValues, newI2v, stateNow.size);
            iah.state = newState;
            ++iah.count;
         } else {
            dstAttributes.clear();
            Snapshot stateNow = this.state;
            int localSize = stateNow.size;
            Object[] localAttributeValues = stateNow.values;

            for(int i = 0; i < localSize; ++i) {
               Object value = localAttributeValues[i];
               if (value != null) {
                  Attribute attribute = this.attributeBuilder.getAttributeByIndex(i);
                  dstAttributes.setAttribute(attribute.name(), value);
               }
            }
         }
      } else {
         dstAttributes.clear();
      }

   }

   public void recycle() {
      if (this.count != 0) {
         Snapshot stateNow = this.state;

         for(int i = 0; i < stateNow.size; ++i) {
            stateNow.values[i] = null;
         }
      } else {
         this.count = 0;
      }

   }

   public void clear() {
      if (this.count != 0) {
         this.count = 0;

         for(int i = 0; i < this.state.size; ++i) {
            this.state.values[i] = null;
         }
      }

   }

   public AttributeBuilder getAttributeBuilder() {
      return this.attributeBuilder;
   }

   public IndexedAttributeAccessor getIndexedAttributeAccessor() {
      return this.indexedAttributeAccessor;
   }

   private static Object[] ensureSize(Object[] array, int size) {
      int arrayLength = array.length;
      int delta = size - arrayLength;
      int newLength = Math.max(arrayLength + delta, arrayLength * 3 / 2 + 1);
      return Arrays.copyOf(array, newLength);
   }

   private static int[] ensureSize(int[] array, int size) {
      int arrayLength = array.length;
      int delta = size - arrayLength;
      int newLength = Math.max(arrayLength + delta, arrayLength * 3 / 2 + 1);
      int[] newArray = Arrays.copyOf(array, newLength);
      Arrays.fill(newArray, array.length, newLength, -1);
      return newArray;
   }

   private static class Snapshot {
      private final Object[] values;
      private final int[] i2v;
      private final int size;

      public Snapshot(Object[] values, int[] i2v, int size) {
         this.values = values;
         this.i2v = i2v;
         this.size = size;
      }
   }

   protected final class IndexedAttributeAccessorImpl implements IndexedAttributeAccessor {
      public Object getAttribute(int index) {
         return this.getAttribute(index, (org.glassfish.grizzly.utils.NullaryFunction)null);
      }

      public Object getAttribute(int index, org.glassfish.grizzly.utils.NullaryFunction initializer) {
         Object value = this.weakGet(index);
         if (value == null && initializer != null) {
            synchronized(IndexedAttributeHolder.this.sync) {
               value = this.weakGet(index);
               if (value == null) {
                  value = initializer.evaluate();
                  this.setAttribute(index, value);
               }
            }
         }

         return value;
      }

      private Object weakGet(int index) {
         if (IndexedAttributeHolder.this.count != 0) {
            Snapshot stateNow = IndexedAttributeHolder.this.state;
            if (index < stateNow.i2v.length) {
               int idx = stateNow.i2v[index];
               if (idx != -1 && idx < stateNow.size) {
                  return stateNow.values[idx];
               }
            }
         }

         return null;
      }

      public void setAttribute(int index, Object value) {
         Snapshot stateNow = IndexedAttributeHolder.this.state;
         int mappedIdx;
         if (index < stateNow.i2v.length && (mappedIdx = stateNow.i2v[index]) != -1) {
            stateNow.values[mappedIdx] = value;
            IndexedAttributeHolder.this.count++;
         } else if (value != null) {
            this.setSync(index, value);
         }

      }

      public Object removeAttribute(int index) {
         Snapshot stateNow = IndexedAttributeHolder.this.state;
         Object oldValue = null;
         int mappedIdx;
         if (index < stateNow.i2v.length && (mappedIdx = stateNow.i2v[index]) != -1) {
            oldValue = stateNow.values[mappedIdx];
            stateNow.values[mappedIdx] = null;
            IndexedAttributeHolder.this.count++;
         }

         return oldValue;
      }

      private void setSync(int index, Object value) {
         synchronized(IndexedAttributeHolder.this.sync) {
            Snapshot stateNow = IndexedAttributeHolder.this.state;
            int mappedIdx;
            int[] newI2v;
            if (index < stateNow.i2v.length) {
               if ((mappedIdx = stateNow.i2v[index]) != -1 && mappedIdx < stateNow.size) {
                  stateNow.values[mappedIdx] = value;
                  IndexedAttributeHolder.this.count++;
                  return;
               }

               newI2v = stateNow.i2v;
            } else {
               newI2v = IndexedAttributeHolder.ensureSize(stateNow.i2v, index + 1);
            }

            mappedIdx = stateNow.size;
            int newSize = mappedIdx + 1;
            Object[] newValues = mappedIdx < stateNow.values.length ? stateNow.values : IndexedAttributeHolder.ensureSize(stateNow.values, newSize);
            newValues[mappedIdx] = value;
            newI2v[index] = mappedIdx;
            IndexedAttributeHolder.this.state = new Snapshot(newValues, newI2v, newSize);
            IndexedAttributeHolder.this.count++;
         }
      }
   }
}
