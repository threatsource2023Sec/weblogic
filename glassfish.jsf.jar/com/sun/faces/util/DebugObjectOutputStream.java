package com.sun.faces.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DebugObjectOutputStream extends ObjectOutputStream {
   private static final Field DEPTH_FIELD;
   final List stack = new ArrayList();
   boolean broken = false;

   public DebugObjectOutputStream(OutputStream out) throws IOException {
      super(out);
      this.enableReplaceObject(true);
   }

   protected Object replaceObject(Object o) {
      int currentDepth = this.currentDepth();
      if (o instanceof IOException && currentDepth == 0) {
         this.broken = true;
      }

      if (!this.broken) {
         this.truncate(currentDepth);
         this.stack.add(o);
      }

      return o;
   }

   private void truncate(int depth) {
      while(this.stack.size() > depth) {
         this.pop();
      }

   }

   private Object pop() {
      return this.stack.remove(this.stack.size() - 1);
   }

   private int currentDepth() {
      try {
         Integer oneBased = (Integer)DEPTH_FIELD.get(this);
         return oneBased - 1;
      } catch (IllegalAccessException var2) {
         throw new AssertionError(var2);
      }
   }

   public List getStack() {
      return this.stack;
   }

   static {
      try {
         DEPTH_FIELD = ObjectOutputStream.class.getDeclaredField("depth");
         DEPTH_FIELD.setAccessible(true);
      } catch (NoSuchFieldException var1) {
         throw new AssertionError(var1);
      }
   }
}
