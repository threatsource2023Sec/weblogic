package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Annotation;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.env.ClassSignature;
import com.bea.core.repackaged.jdt.internal.compiler.env.EnumConstantSignature;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryElementValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.impl.BooleanConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ByteConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CharConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.DoubleConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.FloatConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IntConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.LongConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ShortConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.StringConstant;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.Arrays;

public class AnnotationInfo extends ClassFileStruct implements IBinaryAnnotation {
   private char[] typename;
   private volatile ElementValuePairInfo[] pairs;
   long standardAnnotationTagBits;
   int readOffset;
   static Object[] EmptyValueArray = new Object[0];
   public RuntimeException exceptionDuringDecode;

   AnnotationInfo(byte[] classFileBytes, int[] contantPoolOffsets, int offset) {
      super(classFileBytes, contantPoolOffsets, offset);
      this.standardAnnotationTagBits = 0L;
      this.readOffset = 0;
   }

   AnnotationInfo(byte[] classFileBytes, int[] contantPoolOffsets, int offset, boolean runtimeVisible, boolean populate) {
      this(classFileBytes, contantPoolOffsets, offset);
      if (populate) {
         this.decodeAnnotation();
      } else {
         this.readOffset = this.scanAnnotation(0, runtimeVisible, true);
      }

   }

   private void decodeAnnotation() {
      this.readOffset = 0;
      int utf8Offset = this.constantPoolOffsets[this.u2At(0)] - this.structOffset;
      this.typename = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      int numberOfPairs = this.u2At(2);
      this.readOffset += 4;
      ElementValuePairInfo[] decodedPairs = numberOfPairs == 0 ? ElementValuePairInfo.NoMembers : new ElementValuePairInfo[numberOfPairs];
      int i = 0;

      try {
         while(i < numberOfPairs) {
            utf8Offset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            char[] membername = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
            this.readOffset += 2;
            Object value = this.decodeDefaultValue();
            decodedPairs[i++] = new ElementValuePairInfo(membername, value);
         }

         this.pairs = decodedPairs;
      } catch (RuntimeException var8) {
         this.sanitizePairs(decodedPairs);
         StringBuilder newMessage = new StringBuilder(var8.getMessage());
         newMessage.append(" while decoding pair #").append(i).append(" of annotation @").append(this.typename);
         newMessage.append(", bytes at structOffset ").append(this.structOffset).append(":");
         int offset = this.structOffset;

         while(offset <= this.structOffset + this.readOffset && offset < this.reference.length) {
            newMessage.append(' ').append(Integer.toHexString(this.reference[offset++] & 255));
         }

         throw new IllegalStateException(newMessage.toString(), var8);
      }
   }

   private void sanitizePairs(ElementValuePairInfo[] oldPairs) {
      if (oldPairs != null) {
         ElementValuePairInfo[] newPairs = new ElementValuePairInfo[oldPairs.length];
         int count = 0;

         for(int i = 0; i < oldPairs.length; ++i) {
            ElementValuePairInfo evpInfo = oldPairs[i];
            if (evpInfo != null) {
               newPairs[count++] = evpInfo;
            }
         }

         if (count < oldPairs.length) {
            this.pairs = (ElementValuePairInfo[])Arrays.copyOf(newPairs, count);
         } else {
            this.pairs = newPairs;
         }
      } else {
         this.pairs = ElementValuePairInfo.NoMembers;
      }

   }

   Object decodeDefaultValue() {
      Object value = null;
      int tag = this.u1At(this.readOffset);
      ++this.readOffset;
      int constValueOffset = true;
      int constValueOffset;
      switch (tag) {
         case 64:
            value = new AnnotationInfo(this.reference, this.constantPoolOffsets, this.readOffset + this.structOffset, false, true);
            this.readOffset += ((AnnotationInfo)value).readOffset;
            break;
         case 66:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = ByteConstant.fromValue((byte)this.i4At(constValueOffset + 1));
            this.readOffset += 2;
            break;
         case 67:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = CharConstant.fromValue((char)this.i4At(constValueOffset + 1));
            this.readOffset += 2;
            break;
         case 68:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = DoubleConstant.fromValue(this.doubleAt(constValueOffset + 1));
            this.readOffset += 2;
            break;
         case 70:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = FloatConstant.fromValue(this.floatAt(constValueOffset + 1));
            this.readOffset += 2;
            break;
         case 73:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = IntConstant.fromValue(this.i4At(constValueOffset + 1));
            this.readOffset += 2;
            break;
         case 74:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = LongConstant.fromValue(this.i8At(constValueOffset + 1));
            this.readOffset += 2;
            break;
         case 83:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = ShortConstant.fromValue((short)this.i4At(constValueOffset + 1));
            this.readOffset += 2;
            break;
         case 90:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = BooleanConstant.fromValue(this.i4At(constValueOffset + 1) == 1);
            this.readOffset += 2;
            break;
         case 91:
            int numberOfValues = this.u2At(this.readOffset);
            this.readOffset += 2;
            if (numberOfValues == 0) {
               value = EmptyValueArray;
               break;
            } else {
               Object[] arrayElements = new Object[numberOfValues];
               value = arrayElements;

               for(int i = 0; i < numberOfValues; ++i) {
                  arrayElements[i] = this.decodeDefaultValue();
               }

               return value;
            }
         case 99:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            char[] className = this.utf8At(constValueOffset + 3, this.u2At(constValueOffset + 1));
            value = new ClassSignature(className);
            this.readOffset += 2;
            break;
         case 101:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            char[] typeName = this.utf8At(constValueOffset + 3, this.u2At(constValueOffset + 1));
            this.readOffset += 2;
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            char[] constName = this.utf8At(constValueOffset + 3, this.u2At(constValueOffset + 1));
            this.readOffset += 2;
            value = new EnumConstantSignature(typeName, constName);
            break;
         case 115:
            constValueOffset = this.constantPoolOffsets[this.u2At(this.readOffset)] - this.structOffset;
            value = StringConstant.fromValue(String.valueOf(this.utf8At(constValueOffset + 3, this.u2At(constValueOffset + 1))));
            this.readOffset += 2;
            break;
         default:
            String tagDisplay = tag == 0 ? "0x00" : (char)tag + " (" + Integer.toHexString(tag & 255) + ')';
            throw new IllegalStateException("Unrecognized tag " + tagDisplay);
      }

      return value;
   }

   public IBinaryElementValuePair[] getElementValuePairs() {
      if (this.pairs == null) {
         this.lazyInitialize();
      }

      return this.pairs;
   }

   public char[] getTypeName() {
      return this.typename;
   }

   public boolean isDeprecatedAnnotation() {
      return (this.standardAnnotationTagBits & 4611756387171565568L) != 0L;
   }

   void initialize() {
      if (this.pairs == null) {
         this.decodeAnnotation();
      }

   }

   synchronized void lazyInitialize() {
      if (this.pairs == null) {
         this.decodeAnnotation();
      }

   }

   private int readRetentionPolicy(int offset) {
      int tag = this.u1At(offset);
      int currentOffset = offset + 1;
      switch (tag) {
         case 64:
            currentOffset = this.scanAnnotation(currentOffset, false, false);
            break;
         case 66:
         case 67:
         case 68:
         case 70:
         case 73:
         case 74:
         case 83:
         case 90:
         case 99:
         case 115:
            currentOffset += 2;
            break;
         case 91:
            int numberOfValues = this.u2At(currentOffset);
            currentOffset += 2;

            for(int i = 0; i < numberOfValues; ++i) {
               currentOffset = this.scanElementValue(currentOffset);
            }

            return currentOffset;
         case 101:
            int utf8Offset = this.constantPoolOffsets[this.u2At(currentOffset)] - this.structOffset;
            char[] typeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
            currentOffset += 2;
            if (typeName.length == 38 && CharOperation.equals(typeName, ConstantPool.JAVA_LANG_ANNOTATION_RETENTIONPOLICY)) {
               utf8Offset = this.constantPoolOffsets[this.u2At(currentOffset)] - this.structOffset;
               char[] constName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
               this.standardAnnotationTagBits |= Annotation.getRetentionPolicy(constName);
            }

            currentOffset += 2;
            break;
         default:
            throw new IllegalStateException();
      }

      return currentOffset;
   }

   private int readTargetValue(int offset) {
      int tag = this.u1At(offset);
      int currentOffset = offset + 1;
      switch (tag) {
         case 64:
            currentOffset = this.scanAnnotation(currentOffset, false, false);
            break;
         case 66:
         case 67:
         case 68:
         case 70:
         case 73:
         case 74:
         case 83:
         case 90:
         case 99:
         case 115:
            currentOffset += 2;
            break;
         case 91:
            int numberOfValues = this.u2At(currentOffset);
            currentOffset += 2;
            if (numberOfValues == 0) {
               this.standardAnnotationTagBits |= 34359738368L;
               break;
            } else {
               for(int i = 0; i < numberOfValues; ++i) {
                  currentOffset = this.readTargetValue(currentOffset);
               }

               return currentOffset;
            }
         case 101:
            int utf8Offset = this.constantPoolOffsets[this.u2At(currentOffset)] - this.structOffset;
            char[] typeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
            currentOffset += 2;
            if (typeName.length == 34 && CharOperation.equals(typeName, ConstantPool.JAVA_LANG_ANNOTATION_ELEMENTTYPE)) {
               utf8Offset = this.constantPoolOffsets[this.u2At(currentOffset)] - this.structOffset;
               char[] constName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
               this.standardAnnotationTagBits |= Annotation.getTargetElementType(constName);
            }

            currentOffset += 2;
            break;
         default:
            throw new IllegalStateException();
      }

      return currentOffset;
   }

   private int scanAnnotation(int offset, boolean expectRuntimeVisibleAnno, boolean toplevel) {
      int utf8Offset = this.constantPoolOffsets[this.u2At(offset)] - this.structOffset;
      char[] typeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      if (toplevel) {
         this.typename = typeName;
      }

      int numberOfPairs = this.u2At(offset + 2);
      int currentOffset = offset + 4;
      if (expectRuntimeVisibleAnno && toplevel) {
         switch (typeName.length) {
            case 22:
               if (CharOperation.equals(typeName, ConstantPool.JAVA_LANG_DEPRECATED)) {
                  this.standardAnnotationTagBits |= 70368744177664L;
               }
               break;
            case 23:
               if (CharOperation.equals(typeName, ConstantPool.JAVA_LANG_SAFEVARARGS)) {
                  this.standardAnnotationTagBits |= 2251799813685248L;
                  return currentOffset;
               }
               break;
            case 29:
               if (CharOperation.equals(typeName, ConstantPool.JAVA_LANG_ANNOTATION_TARGET)) {
                  currentOffset += 2;
                  return this.readTargetValue(currentOffset);
               }
               break;
            case 32:
               if (CharOperation.equals(typeName, ConstantPool.JAVA_LANG_ANNOTATION_RETENTION)) {
                  currentOffset += 2;
                  return this.readRetentionPolicy(currentOffset);
               }

               if (CharOperation.equals(typeName, ConstantPool.JAVA_LANG_ANNOTATION_INHERITED)) {
                  this.standardAnnotationTagBits |= 281474976710656L;
                  return currentOffset;
               }
               break;
            case 33:
               if (CharOperation.equals(typeName, ConstantPool.JAVA_LANG_ANNOTATION_DOCUMENTED)) {
                  this.standardAnnotationTagBits |= 140737488355328L;
                  return currentOffset;
               }
               break;
            case 52:
               if (CharOperation.equals(typeName, ConstantPool.JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE)) {
                  this.standardAnnotationTagBits |= 4503599627370496L;
                  return currentOffset;
               }
         }
      }

      for(int i = 0; i < numberOfPairs; ++i) {
         currentOffset += 2;
         currentOffset = this.scanElementValue(currentOffset);
      }

      return currentOffset;
   }

   private int scanElementValue(int offset) {
      int tag = this.u1At(offset);
      int currentOffset = offset + 1;
      int constantOffset;
      switch (tag) {
         case 64:
            currentOffset = this.scanAnnotation(currentOffset, false, false);
            break;
         case 66:
         case 67:
         case 68:
         case 70:
         case 73:
         case 74:
         case 83:
         case 99:
         case 115:
            currentOffset += 2;
            break;
         case 90:
            if ((this.standardAnnotationTagBits & 70368744177664L) != 0L) {
               constantOffset = this.constantPoolOffsets[this.u2At(currentOffset)] - this.structOffset + 1;
               if (this.i4At(constantOffset) == 1) {
                  this.standardAnnotationTagBits |= 4611686018427387904L;
               }
            }

            currentOffset += 2;
            break;
         case 91:
            constantOffset = this.u2At(currentOffset);
            currentOffset += 2;

            for(int i = 0; i < constantOffset; ++i) {
               currentOffset = this.scanElementValue(currentOffset);
            }

            return currentOffset;
         case 101:
            currentOffset += 4;
            break;
         default:
            throw new IllegalStateException();
      }

      return currentOffset;
   }

   public String toString() {
      return BinaryTypeFormatter.annotationToString((IBinaryAnnotation)this);
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + Util.hashCode(this.pairs);
      result = 31 * result + CharOperation.hashCode(this.typename);
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         AnnotationInfo other = (AnnotationInfo)obj;
         if (!Arrays.equals(this.pairs, other.pairs)) {
            return false;
         } else {
            return Arrays.equals(this.typename, other.typename);
         }
      }
   }
}
