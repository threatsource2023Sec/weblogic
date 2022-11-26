package com.bea.adaptive.harvester.utils.collections;

import java.util.List;

public class ListIndex extends ExtensibleList {
   private static final long serialVersionUID = 1L;
   Class listType = ListSet.class;

   public ListIndex() {
   }

   public ListIndex(int size) {
      super(size);
   }

   public ListIndex(Class listType) {
      this.listType = listType;
   }

   public void addEntry(int index, Object value) {
      CollectionUtils.extendTo(this, index + 1);
      List list = (List)this.get(index);
      if (list == null) {
         try {
            list = (List)this.listType.newInstance();
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }

         this.set(index, list);
      }

      list.add(value);
   }

   public int size(int index) {
      return ((List)this.get(index)).size();
   }

   public int listCount() {
      return super.elementCount();
   }

   public int elementCount() {
      int count = 0;
      java.util.Iterator it = this.iterator();

      while(it.hasNext()) {
         List list = (List)it.next();
         if (list != null) {
            count += list.size();
         }
      }

      return count;
   }
}
