package com.bea.common.security.xacml.attr;

import java.util.Arrays;

public abstract class ByteArrayHolder {
   private byte[] data;

   public ByteArrayHolder(byte[] data) {
      this.data = data;
   }

   public byte[] getData() {
      return this.data;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return !(o instanceof ByteArrayHolder) ? false : Arrays.equals(this.data, ((ByteArrayHolder)o).data);
      }
   }

   public int hashCode() {
      return com.bea.common.security.jdkutils.WeaverUtil.Arrays.hashCode(this.data);
   }

   public abstract String toString();
}
