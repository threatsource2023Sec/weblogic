package com.octetstring.vde.syntax;

import java.io.UnsupportedEncodingException;

public abstract class Syntax {
   public abstract int compareTo(Syntax var1);

   public abstract boolean endsWith(Syntax var1);

   public abstract byte[] getValue();

   public abstract int indexOf(Syntax var1);

   public abstract String normalize();

   public abstract Syntax reverse();

   public abstract void setValue(byte[] var1);

   public abstract void setValue(byte[] var1, int var2);

   public abstract boolean startsWith(Syntax var1);

   public String returnAttributeValue(byte[] decryptedValue) {
      try {
         return new String(decryptedValue, "UTF8");
      } catch (UnsupportedEncodingException var3) {
         return new String(decryptedValue);
      }
   }
}
