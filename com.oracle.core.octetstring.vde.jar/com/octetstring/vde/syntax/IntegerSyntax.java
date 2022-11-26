package com.octetstring.vde.syntax;

import com.octetstring.vde.util.Logger;
import java.io.Serializable;

public class IntegerSyntax extends Syntax implements Serializable, Comparable {
   long value;

   public IntegerSyntax() {
   }

   public IntegerSyntax(byte[] value) {
      this.setValue(value);
   }

   public IntegerSyntax(byte[] value, int hashCode) {
      this.setValue(value);
   }

   public IntegerSyntax(int value) {
      this.setValue((long)value);
   }

   public IntegerSyntax(long value) {
      this.setValue(value);
   }

   public IntegerSyntax(Long value) {
      this.setValue(value);
   }

   public IntegerSyntax(String value) {
      this.setValue(Long.parseLong(value));
   }

   public byte[] getValue() {
      return String.valueOf(this.value).getBytes();
   }

   public void setValue(byte[] value) {
      try {
         this.value = Long.parseLong(new String(value));
      } catch (NumberFormatException var3) {
         Logger.getInstance().log(3, this, "Invalid Integer: " + new String(value));
         this.value = 0L;
      }

   }

   public void setValue(byte[] value, int hashCode) {
      try {
         this.value = Long.parseLong(new String(value));
      } catch (NumberFormatException var4) {
         Logger.getInstance().log(3, this, "Invalid Integer: " + new String(value));
         this.value = 0L;
      }

   }

   public void setValue(long value) {
      this.value = value;
   }

   public void setValue(Long value) {
      this.value = value;
   }

   public int compareTo(IntegerSyntax is) {
      return (int)(this.getLongValue() - is.getLongValue());
   }

   public int compareTo(Syntax val) {
      return this.compareTo((IntegerSyntax)val);
   }

   public int compareTo(Object obj) {
      return this.compareTo((IntegerSyntax)obj);
   }

   public boolean endsWith(Syntax val) {
      return false;
   }

   public boolean equals(IntegerSyntax is) {
      return this.compareTo(is) == 0;
   }

   public boolean equals(Object obj) {
      return this.compareTo((IntegerSyntax)obj) == 0;
   }

   public byte[] getBytes() {
      return String.valueOf(this.value).getBytes();
   }

   public long getLongValue() {
      return this.value;
   }

   public int hashCode() {
      return (int)this.value;
   }

   public int indexOf(Syntax val) {
      return -1;
   }

   public String normalize() {
      return String.valueOf(this.value);
   }

   public Syntax reverse() {
      return this;
   }

   public boolean startsWith(Syntax val) {
      return false;
   }

   public String toString() {
      return String.valueOf(this.value);
   }

   public String returnAttributeValue(byte[] decryptedValue) {
      try {
         long value = Long.parseLong(new String(decryptedValue));
         return String.valueOf(value);
      } catch (NumberFormatException var4) {
         Logger.getInstance().log(3, this, "Invalid Integer: " + new String(decryptedValue));
         return null;
      }
   }
}
