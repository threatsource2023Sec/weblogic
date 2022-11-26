package com.bea.core.repackaged.aspectj.util;

import java.io.DataOutputStream;
import java.io.IOException;

public class TypeSafeEnum {
   private byte key;
   private String name;

   public TypeSafeEnum(String name, int key) {
      this.name = name;
      if (key <= 127 && key >= -128) {
         this.key = (byte)key;
      } else {
         throw new IllegalArgumentException("key doesn't fit into a byte: " + key);
      }
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public byte getKey() {
      return this.key;
   }

   public void write(DataOutputStream s) throws IOException {
      s.writeByte(this.key);
   }
}
