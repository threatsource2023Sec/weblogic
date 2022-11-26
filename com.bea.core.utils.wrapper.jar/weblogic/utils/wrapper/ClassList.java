package weblogic.utils.wrapper;

class ClassList {
   private Class[] data;
   private int size;

   public ClassList(int initialCapacity) {
      this.data = (Class[])(new Class[initialCapacity]);
   }

   public void addUnique(Class elem) {
      for(int i = 0; i < this.size; ++i) {
         if (elem.getName().equals(this.data[i].getName())) {
            return;
         }
      }

      this.ensureCapacity(this.size + 1);
      this.data[this.size++] = elem;
   }

   public Class[] toArray() {
      Class[] result = new Class[this.size];
      System.arraycopy(this.data, 0, result, 0, this.size);
      return result;
   }

   public void ensureCapacity(int minCapacity) {
      int oldCapacity = this.data.length;
      if (minCapacity > oldCapacity) {
         Object[] oldData = this.data;
         int newCapacity = oldCapacity * 3 / 2 + 1;
         if (newCapacity < minCapacity) {
            newCapacity = minCapacity;
         }

         this.data = (Class[])(new Class[newCapacity]);
         System.arraycopy(oldData, 0, this.data, 0, this.size);
      }

   }
}
