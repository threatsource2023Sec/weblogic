package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import java.util.Arrays;

public class TypeAnnotationInfo extends ClassFileStruct implements IBinaryTypeAnnotation {
   private AnnotationInfo annotation;
   private int targetType;
   private int info;
   private int info2;
   private int[] typePath;
   int readOffset;

   TypeAnnotationInfo(byte[] classFileBytes, int[] contantPoolOffsets, int offset) {
      super(classFileBytes, contantPoolOffsets, offset);
      this.targetType = 0;
      this.readOffset = 0;
   }

   TypeAnnotationInfo(byte[] classFileBytes, int[] contantPoolOffsets, int offset, boolean runtimeVisible, boolean populate) {
      this(classFileBytes, contantPoolOffsets, offset);
      this.readOffset = 0;
      this.targetType = this.u1At(0);
      switch (this.targetType) {
         case 0:
         case 1:
            this.info = this.u1At(1);
            this.readOffset += 2;
            break;
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         default:
            throw new IllegalStateException("Target type not handled " + this.targetType);
         case 16:
            this.info = this.u2At(1);
            this.readOffset += 3;
            break;
         case 17:
         case 18:
            this.info = this.u1At(1);
            this.info2 = this.u1At(2);
            this.readOffset += 3;
            break;
         case 19:
         case 20:
         case 21:
            ++this.readOffset;
            break;
         case 22:
            this.info = this.u1At(1);
            this.readOffset += 2;
            break;
         case 23:
            this.info = this.u2At(1);
            this.readOffset += 3;
      }

      int typePathLength = this.u1At(this.readOffset);
      ++this.readOffset;
      if (typePathLength == 0) {
         this.typePath = NO_TYPE_PATH;
      } else {
         this.typePath = new int[typePathLength * 2];
         int index = 0;

         for(int i = 0; i < typePathLength; ++i) {
            this.typePath[index++] = this.u1At(this.readOffset++);
            this.typePath[index++] = this.u1At(this.readOffset++);
         }
      }

      this.annotation = new AnnotationInfo(classFileBytes, this.constantPoolOffsets, this.structOffset + this.readOffset, runtimeVisible, populate);
      this.readOffset += this.annotation.readOffset;
   }

   public IBinaryAnnotation getAnnotation() {
      return this.annotation;
   }

   protected void initialize() {
      this.annotation.initialize();
   }

   protected void reset() {
      this.annotation.reset();
      super.reset();
   }

   public String toString() {
      return BinaryTypeFormatter.annotationToString((IBinaryTypeAnnotation)this);
   }

   public int getTargetType() {
      return this.targetType;
   }

   public int getSupertypeIndex() {
      return this.info;
   }

   public int getTypeParameterIndex() {
      return this.info;
   }

   public int getBoundIndex() {
      return this.info2;
   }

   public int getMethodFormalParameterIndex() {
      return this.info;
   }

   public int getThrowsTypeIndex() {
      return this.info;
   }

   public int[] getTypePath() {
      return this.typePath;
   }

   public int hashCode() {
      int result = 1;
      result = 31 * result + this.targetType;
      result = 31 * result + this.info;
      result = 31 * result + this.info2;
      if (this.typePath != null) {
         int i = 0;

         for(int max = this.typePath.length; i < max; ++i) {
            result = 31 * result + this.typePath[i];
         }
      }

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
         TypeAnnotationInfo other = (TypeAnnotationInfo)obj;
         if (this.targetType != other.targetType) {
            return false;
         } else if (this.info != other.info) {
            return false;
         } else if (this.info2 != other.info2) {
            return false;
         } else {
            return !Arrays.equals(this.typePath, other.typePath) ? false : this.annotation.equals(other.annotation);
         }
      }
   }
}
