package com.bea.adaptive.harvester.utils.collections;

import java.util.Set;

public class SetIndex extends ExtensibleList {
   private static final long serialVersionUID = 1L;
   Class setType = ListSet.class;

   public SetIndex() {
   }

   public SetIndex(Class setType) {
      this.setType = setType;
   }

   public void addEntry(int index, Object value) {
      CollectionUtils.extendTo(this, index + 1);
      Set set = (Set)this.get(index);
      if (set == null) {
         try {
            set = (Set)this.setType.newInstance();
         } catch (RuntimeException var5) {
            throw var5;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }

         this.set(index, set);
      }

      set.add(value);
   }

   public int size(int index) {
      return ((Set)this.get(index)).size();
   }

   public int setCount() {
      return super.elementCount();
   }

   public int elementCount() {
      int count = 0;
      java.util.Iterator it = this.iterator();

      while(it.hasNext()) {
         Set list = (Set)it.next();
         if (list != null) {
            count += list.size();
         }
      }

      return count;
   }
}
