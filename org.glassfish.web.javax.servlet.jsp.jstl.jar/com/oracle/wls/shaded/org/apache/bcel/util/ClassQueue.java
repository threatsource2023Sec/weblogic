package com.oracle.wls.shaded.org.apache.bcel.util;

import com.oracle.wls.shaded.org.apache.bcel.classfile.JavaClass;
import java.util.ArrayList;

public class ClassQueue {
   protected int left = 0;
   private ArrayList vec = new ArrayList();

   public void enqueue(JavaClass clazz) {
      this.vec.add(clazz);
   }

   public JavaClass dequeue() {
      JavaClass clazz = (JavaClass)this.vec.get(this.left);
      this.vec.remove(this.left++);
      return clazz;
   }

   public boolean empty() {
      return this.vec.size() <= this.left;
   }
}
