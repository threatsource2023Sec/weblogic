package com.bea.core.repackaged.jdt.internal.compiler.codegen;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;

public class VerificationTypeInfo {
   public static final int ITEM_TOP = 0;
   public static final int ITEM_INTEGER = 1;
   public static final int ITEM_FLOAT = 2;
   public static final int ITEM_DOUBLE = 3;
   public static final int ITEM_LONG = 4;
   public static final int ITEM_NULL = 5;
   public static final int ITEM_UNINITIALIZED_THIS = 6;
   public static final int ITEM_OBJECT = 7;
   public static final int ITEM_UNINITIALIZED = 8;
   public int tag;
   private int id;
   private char[] constantPoolName;
   public int offset;

   private VerificationTypeInfo() {
   }

   public VerificationTypeInfo(int id, char[] constantPoolName) {
      this(id, 7, constantPoolName);
   }

   public VerificationTypeInfo(int id, int tag, char[] constantPoolName) {
      this.id = id;
      this.tag = tag;
      this.constantPoolName = constantPoolName;
   }

   public VerificationTypeInfo(int tag, TypeBinding binding) {
      this(binding);
      this.tag = tag;
   }

   public VerificationTypeInfo(TypeBinding binding) {
      this.id = binding.id;
      switch (binding.id) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 10:
            this.tag = 1;
            break;
         case 6:
         case 11:
         default:
            this.tag = 7;
            this.constantPoolName = binding.constantPoolName();
            break;
         case 7:
            this.tag = 4;
            break;
         case 8:
            this.tag = 3;
            break;
         case 9:
            this.tag = 2;
            break;
         case 12:
            this.tag = 5;
      }

   }

   public void setBinding(TypeBinding binding) {
      this.constantPoolName = binding.constantPoolName();
      int typeBindingId = binding.id;
      this.id = typeBindingId;
      switch (typeBindingId) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 10:
            this.tag = 1;
            break;
         case 6:
         case 11:
         default:
            this.tag = 7;
            break;
         case 7:
            this.tag = 4;
            break;
         case 8:
            this.tag = 3;
            break;
         case 9:
            this.tag = 2;
            break;
         case 12:
            this.tag = 5;
      }

   }

   public int id() {
      return this.id;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      switch (this.tag) {
         case 0:
            buffer.append("top");
            break;
         case 1:
            buffer.append('I');
            break;
         case 2:
            buffer.append('F');
            break;
         case 3:
            buffer.append('D');
            break;
         case 4:
            buffer.append('J');
            break;
         case 5:
            buffer.append("null");
            break;
         case 6:
            buffer.append("uninitialized_this(").append(this.readableName()).append(")");
            break;
         case 7:
            buffer.append(this.readableName());
            break;
         case 8:
            buffer.append("uninitialized(").append(this.readableName()).append(")");
      }

      return String.valueOf(buffer);
   }

   public VerificationTypeInfo duplicate() {
      VerificationTypeInfo verificationTypeInfo = new VerificationTypeInfo();
      verificationTypeInfo.id = this.id;
      verificationTypeInfo.tag = this.tag;
      verificationTypeInfo.constantPoolName = this.constantPoolName;
      verificationTypeInfo.offset = this.offset;
      return verificationTypeInfo;
   }

   public boolean equals(Object obj) {
      if (obj instanceof VerificationTypeInfo) {
         VerificationTypeInfo info1 = (VerificationTypeInfo)obj;
         return info1.tag == this.tag && CharOperation.equals(info1.constantPoolName(), this.constantPoolName());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.tag + this.id + this.constantPoolName.length + this.offset;
   }

   public char[] constantPoolName() {
      return this.constantPoolName;
   }

   public char[] readableName() {
      return this.constantPoolName;
   }

   public void replaceWithElementType() {
      if (this.constantPoolName[1] == 'L') {
         this.constantPoolName = CharOperation.subarray((char[])this.constantPoolName, 2, this.constantPoolName.length - 1);
      } else {
         this.constantPoolName = CharOperation.subarray((char[])this.constantPoolName, 1, this.constantPoolName.length);
         if (this.constantPoolName.length == 1) {
            switch (this.constantPoolName[0]) {
               case 'B':
                  this.id = 3;
                  break;
               case 'C':
                  this.id = 2;
                  break;
               case 'D':
                  this.id = 8;
               case 'E':
               case 'G':
               case 'H':
               case 'K':
               case 'L':
               case 'M':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'T':
               case 'U':
               case 'W':
               case 'X':
               case 'Y':
               default:
                  break;
               case 'F':
                  this.id = 9;
                  break;
               case 'I':
                  this.id = 10;
                  break;
               case 'J':
                  this.id = 7;
                  break;
               case 'N':
                  this.id = 12;
                  break;
               case 'S':
                  this.id = 4;
                  break;
               case 'V':
                  this.id = 6;
                  break;
               case 'Z':
                  this.id = 5;
            }
         }
      }

   }
}
