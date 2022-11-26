package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AttributeUtils {
   public static Attribute[] readAttributes(DataInputStream dataInputstream, ConstantPool cpool) {
      try {
         int length = dataInputstream.readUnsignedShort();
         if (length == 0) {
            return Attribute.NoAttributes;
         } else {
            Attribute[] attrs = new Attribute[length];

            for(int i = 0; i < length; ++i) {
               attrs[i] = Attribute.readAttribute(dataInputstream, cpool);
            }

            return attrs;
         }
      } catch (IOException var5) {
         throw new ClassFormatException("IOException whilst reading set of attributes: " + var5.toString());
      }
   }

   public static void writeAttributes(Attribute[] attributes, DataOutputStream file) throws IOException {
      if (attributes == null) {
         file.writeShort(0);
      } else {
         file.writeShort(attributes.length);

         for(int i = 0; i < attributes.length; ++i) {
            attributes[i].dump(file);
         }
      }

   }

   public static Signature getSignatureAttribute(Attribute[] attributes) {
      for(int i = 0; i < attributes.length; ++i) {
         if (attributes[i].tag == 10) {
            return (Signature)attributes[i];
         }
      }

      return null;
   }

   public static Code getCodeAttribute(Attribute[] attributes) {
      for(int i = 0; i < attributes.length; ++i) {
         if (attributes[i].tag == 2) {
            return (Code)attributes[i];
         }
      }

      return null;
   }

   public static ExceptionTable getExceptionTableAttribute(Attribute[] attributes) {
      for(int i = 0; i < attributes.length; ++i) {
         if (attributes[i].tag == 3) {
            return (ExceptionTable)attributes[i];
         }
      }

      return null;
   }

   public static ConstantValue getConstantValueAttribute(Attribute[] attributes) {
      for(int i = 0; i < attributes.length; ++i) {
         if (attributes[i].getTag() == 1) {
            return (ConstantValue)attributes[i];
         }
      }

      return null;
   }

   public static void accept(Attribute[] attributes, ClassVisitor visitor) {
      for(int i = 0; i < attributes.length; ++i) {
         attributes[i].accept(visitor);
      }

   }

   public static boolean hasSyntheticAttribute(Attribute[] attributes) {
      for(int i = 0; i < attributes.length; ++i) {
         if (attributes[i].tag == 7) {
            return true;
         }
      }

      return false;
   }

   public static SourceFile getSourceFileAttribute(Attribute[] attributes) {
      for(int i = 0; i < attributes.length; ++i) {
         if (attributes[i].tag == 0) {
            return (SourceFile)attributes[i];
         }
      }

      return null;
   }
}
