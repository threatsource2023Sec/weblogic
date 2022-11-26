package org.jboss.classfilewriter.code;

import java.io.DataOutputStream;
import java.io.IOException;
import org.jboss.classfilewriter.constpool.ConstPool;
import org.jboss.classfilewriter.util.DescriptorUtils;

public class StackEntry {
   private final StackEntryType type;
   private final String descriptor;
   private final int descriptorIndex;
   private final int newInstructionLocation;

   public StackEntry(StackEntryType type, String descriptor) {
      if (type == StackEntryType.OBJECT) {
         throw new RuntimeException("OBJECT stack entries must provide a const pool index for the class");
      } else {
         this.type = type;
         if (descriptor != null && descriptor.contains(".")) {
            throw new RuntimeException("invalid descriptor " + descriptor);
         } else {
            this.descriptor = descriptor;
            this.newInstructionLocation = -1;
            this.descriptorIndex = -1;
         }
      }
   }

   public StackEntry(StackEntryType type, String descriptor, ConstPool pool) {
      this.type = type;
      this.descriptor = descriptor;
      this.newInstructionLocation = -1;
      if (descriptor.contains(".")) {
         throw new RuntimeException("invalid descriptor " + descriptor);
      } else {
         if (type == StackEntryType.OBJECT) {
            if (descriptor.charAt(0) == 'L') {
               this.descriptorIndex = pool.addClassEntry(descriptor.substring(1, descriptor.length() - 1));
            } else {
               this.descriptorIndex = pool.addClassEntry(descriptor);
            }
         } else {
            this.descriptorIndex = -1;
         }

      }
   }

   public StackEntry(StackEntryType type, String descriptor, int newInstructionLocation) {
      this.type = type;
      this.descriptor = descriptor;
      this.newInstructionLocation = newInstructionLocation;
      this.descriptorIndex = -1;
      if (descriptor != null && descriptor.contains(".")) {
         throw new RuntimeException("invalid descriptor " + descriptor);
      }
   }

   public String getDescriptor() {
      return this.descriptor;
   }

   public int getNewInstructionLocation() {
      return this.newInstructionLocation;
   }

   public StackEntryType getType() {
      return this.type;
   }

   public String toString() {
      return this.type == StackEntryType.OBJECT ? "StackEntry [descriptor=" + this.descriptor + ", type=" + this.type + "]" : "StackEntry [type=" + this.type + "]";
   }

   public static StackEntry of(String descriptor, ConstPool pool) {
      String desc = descriptor.replace('.', '/');
      if (!DescriptorUtils.isPrimitive(desc)) {
         return new StackEntry(StackEntryType.OBJECT, desc, pool);
      } else {
         char ret = desc.charAt(0);
         switch (ret) {
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z':
               return new StackEntry(StackEntryType.INT, desc);
            case 'D':
               return new StackEntry(StackEntryType.DOUBLE, desc);
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new RuntimeException("Unknown descriptor: " + desc);
            case 'F':
               return new StackEntry(StackEntryType.FLOAT, desc);
            case 'J':
               return new StackEntry(StackEntryType.LONG, desc);
         }
      }
   }

   public boolean isWide() {
      return this.type == StackEntryType.DOUBLE || this.type == StackEntryType.LONG;
   }

   public void write(DataOutputStream dstream) throws IOException {
      dstream.writeByte(this.type.ordinal());
      if (this.type == StackEntryType.OBJECT) {
         dstream.writeShort(this.descriptorIndex);
      } else if (this.type == StackEntryType.UNITITIALIZED_OBJECT) {
         dstream.writeShort(this.newInstructionLocation);
      }

   }
}
