package org.python.icu.impl;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.UResourceTypeMismatchException;

class ICUResourceBundleImpl extends ICUResourceBundle {
   protected int resource;

   protected ICUResourceBundleImpl(ICUResourceBundleImpl container, String key, int resource) {
      super(container, key);
      this.resource = resource;
   }

   ICUResourceBundleImpl(ICUResourceBundle.WholeBundle wholeBundle) {
      super(wholeBundle);
      this.resource = wholeBundle.reader.getRootResource();
   }

   public int getResource() {
      return this.resource;
   }

   protected final ICUResourceBundle createBundleObject(String var1, int var2, HashMap var3, UResourceBundle var4) {
      // $FF: Couldn't be decompiled
   }

   static class ResourceTable extends ResourceContainer {
      public int getType() {
         return 2;
      }

      protected String getKey(int index) {
         return ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, index);
      }

      protected Set handleKeySet() {
         ICUResourceBundleReader reader = this.wholeBundle.reader;
         TreeSet keySet = new TreeSet();
         ICUResourceBundleReader.Table table = (ICUResourceBundleReader.Table)this.value;

         for(int i = 0; i < table.getSize(); ++i) {
            keySet.add(table.getKey(reader, i));
         }

         return keySet;
      }

      protected UResourceBundle handleGet(String resKey, HashMap aliasesVisited, UResourceBundle requested) {
         int i = ((ICUResourceBundleReader.Table)this.value).findTableItem(this.wholeBundle.reader, resKey);
         return i < 0 ? null : this.createBundleObject(resKey, this.getContainerResource(i), aliasesVisited, requested);
      }

      protected UResourceBundle handleGet(int index, HashMap aliasesVisited, UResourceBundle requested) {
         String itemKey = ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, index);
         if (itemKey == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return this.createBundleObject(itemKey, this.getContainerResource(index), aliasesVisited, requested);
         }
      }

      protected Object handleGetObject(String key) {
         ICUResourceBundleReader reader = this.wholeBundle.reader;
         int index = ((ICUResourceBundleReader.Table)this.value).findTableItem(reader, key);
         if (index >= 0) {
            int res = this.value.getContainerResource(reader, index);
            String s = reader.getString(res);
            if (s != null) {
               return s;
            }

            ICUResourceBundleReader.Container array = reader.getArray(res);
            if (array != null) {
               int length = array.getSize();
               String[] strings = new String[length];
               int j = 0;

               while(true) {
                  if (j == length) {
                     return strings;
                  }

                  s = reader.getString(array.getContainerResource(reader, j));
                  if (s == null) {
                     break;
                  }

                  strings[j] = s;
                  ++j;
               }
            }
         }

         return super.handleGetObject(key);
      }

      String findString(String key) {
         ICUResourceBundleReader reader = this.wholeBundle.reader;
         int index = ((ICUResourceBundleReader.Table)this.value).findTableItem(reader, key);
         return index < 0 ? null : reader.getString(this.value.getContainerResource(reader, index));
      }

      ResourceTable(ICUResourceBundleImpl container, String key, int resource) {
         super(container, key, resource);
         this.value = this.wholeBundle.reader.getTable(resource);
      }

      ResourceTable(ICUResourceBundle.WholeBundle wholeBundle, int rootRes) {
         super(wholeBundle);
         this.value = wholeBundle.reader.getTable(rootRes);
      }
   }

   static class ResourceArray extends ResourceContainer {
      public int getType() {
         return 8;
      }

      protected String[] handleGetStringArray() {
         ICUResourceBundleReader reader = this.wholeBundle.reader;
         int length = this.value.getSize();
         String[] strings = new String[length];

         for(int i = 0; i < length; ++i) {
            String s = reader.getString(this.value.getContainerResource(reader, i));
            if (s == null) {
               throw new UResourceTypeMismatchException("");
            }

            strings[i] = s;
         }

         return strings;
      }

      public String[] getStringArray() {
         return this.handleGetStringArray();
      }

      protected UResourceBundle handleGet(String indexStr, HashMap aliasesVisited, UResourceBundle requested) {
         int i = Integer.parseInt(indexStr);
         return this.createBundleObject(i, indexStr, aliasesVisited, requested);
      }

      protected UResourceBundle handleGet(int index, HashMap aliasesVisited, UResourceBundle requested) {
         return this.createBundleObject(index, Integer.toString(index), aliasesVisited, requested);
      }

      ResourceArray(ICUResourceBundleImpl container, String key, int resource) {
         super(container, key, resource);
         this.value = this.wholeBundle.reader.getArray(resource);
      }
   }

   abstract static class ResourceContainer extends ICUResourceBundleImpl {
      protected ICUResourceBundleReader.Container value;

      public int getSize() {
         return this.value.getSize();
      }

      public String getString(int index) {
         int res = this.value.getContainerResource(this.wholeBundle.reader, index);
         if (res == -1) {
            throw new IndexOutOfBoundsException();
         } else {
            String s = this.wholeBundle.reader.getString(res);
            return s != null ? s : super.getString(index);
         }
      }

      protected int getContainerResource(int index) {
         return this.value.getContainerResource(this.wholeBundle.reader, index);
      }

      protected UResourceBundle createBundleObject(int index, String resKey, HashMap aliasesVisited, UResourceBundle requested) {
         int item = this.getContainerResource(index);
         if (item == -1) {
            throw new IndexOutOfBoundsException();
         } else {
            return this.createBundleObject(resKey, item, aliasesVisited, requested);
         }
      }

      ResourceContainer(ICUResourceBundleImpl container, String key, int resource) {
         super(container, key, resource);
      }

      ResourceContainer(ICUResourceBundle.WholeBundle wholeBundle) {
         super(wholeBundle);
      }
   }

   private static final class ResourceIntVector extends ICUResourceBundleImpl {
      public int getType() {
         return 14;
      }

      public int[] getIntVector() {
         return this.wholeBundle.reader.getIntVector(this.resource);
      }

      ResourceIntVector(ICUResourceBundleImpl container, String key, int resource) {
         super(container, key, resource);
      }
   }

   private static final class ResourceString extends ICUResourceBundleImpl {
      private String value;

      public int getType() {
         return 0;
      }

      public String getString() {
         return this.value != null ? this.value : this.wholeBundle.reader.getString(this.resource);
      }

      ResourceString(ICUResourceBundleImpl container, String key, int resource) {
         super(container, key, resource);
         String s = this.wholeBundle.reader.getString(resource);
         if (s.length() < 12 || CacheValue.futureInstancesWillBeStrong()) {
            this.value = s;
         }

      }
   }

   private static final class ResourceInt extends ICUResourceBundleImpl {
      public int getType() {
         return 7;
      }

      public int getInt() {
         return ICUResourceBundleReader.RES_GET_INT(this.resource);
      }

      public int getUInt() {
         return ICUResourceBundleReader.RES_GET_UINT(this.resource);
      }

      ResourceInt(ICUResourceBundleImpl container, String key, int resource) {
         super(container, key, resource);
      }
   }

   private static final class ResourceBinary extends ICUResourceBundleImpl {
      public int getType() {
         return 1;
      }

      public ByteBuffer getBinary() {
         return this.wholeBundle.reader.getBinary(this.resource);
      }

      public byte[] getBinary(byte[] ba) {
         return this.wholeBundle.reader.getBinary(this.resource, ba);
      }

      ResourceBinary(ICUResourceBundleImpl container, String key, int resource) {
         super(container, key, resource);
      }
   }
}
