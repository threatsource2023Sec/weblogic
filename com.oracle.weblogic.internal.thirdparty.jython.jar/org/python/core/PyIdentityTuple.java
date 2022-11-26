package org.python.core;

class PyIdentityTuple extends PyObject implements Traverseproc {
   PyObject[] list;

   public PyIdentityTuple(PyObject[] elements) {
      this.list = elements;
   }

   public int hashCode() {
      int len = this.list.length;
      int x = 3430008;
      --len;

      while(len >= 0) {
         int y = System.identityHashCode(this.list[len]);
         x = x + x + x ^ y;
         --len;
      }

      x ^= this.list.length;
      return x;
   }

   public boolean equals(Object o) {
      if (!(o instanceof PyIdentityTuple)) {
         return false;
      } else {
         PyIdentityTuple that = (PyIdentityTuple)o;
         if (this.list.length != that.list.length) {
            return false;
         } else {
            for(int i = 0; i < this.list.length; ++i) {
               if (this.list[i] != that.list[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.list != null) {
         PyObject[] var3 = this.list;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject ob = var3[var5];
            if (ob != null) {
               int retVal = visit.visit(ob, arg);
               if (retVal != 0) {
                  return retVal;
               }
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob != null && this.list != null) {
         PyObject[] var2 = this.list;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject obj = var2[var4];
            if (ob == obj) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
