package org.python.core;

class ReflectedCallData {
   public static final int UNABLE_TO_CONVERT_SELF = -1;
   public static final int BAD_ARG_COUNT = -2;
   public Object[] args;
   public int length;
   public Object self;
   public int errArg;

   ReflectedCallData() {
      this.args = Py.EmptyObjects;
      this.errArg = -2;
   }

   public void setLength(int newLength) {
      this.length = newLength;
      if (newLength > this.args.length) {
         this.args = new Object[newLength];
      }
   }

   public Object[] getArgsArray() {
      if (this.length == this.args.length) {
         return this.args;
      } else {
         Object[] newArgs = new Object[this.length];
         System.arraycopy(this.args, 0, newArgs, 0, this.length);
         this.args = newArgs;
         return newArgs;
      }
   }
}
