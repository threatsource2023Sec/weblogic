package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.AttributeNamesConstants;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryField;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.impl.BooleanConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ByteConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CharConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.DoubleConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.FloatConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IntConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.LongConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ShortConstant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.StringConstant;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class FieldInfo extends ClassFileStruct implements IBinaryField, Comparable {
   protected int accessFlags = -1;
   protected int attributeBytes;
   protected Constant constant;
   protected char[] descriptor;
   protected char[] name;
   protected char[] signature;
   protected int signatureUtf8Offset = -1;
   protected long tagBits;
   protected Object wrappedConstantValue;
   protected long version;

   public static FieldInfo createField(byte[] classFileBytes, int[] offsets, int offset, long version) {
      FieldInfo fieldInfo = new FieldInfo(classFileBytes, offsets, offset, version);
      int attributesCount = fieldInfo.u2At(6);
      int readOffset = 8;
      AnnotationInfo[] annotations = null;
      TypeAnnotationInfo[] typeAnnotations = null;

      for(int i = 0; i < attributesCount; ++i) {
         int utf8Offset = fieldInfo.constantPoolOffsets[fieldInfo.u2At(readOffset)] - fieldInfo.structOffset;
         char[] attributeName = fieldInfo.utf8At(utf8Offset + 3, fieldInfo.u2At(utf8Offset + 1));
         if (attributeName.length > 0) {
            switch (attributeName[0]) {
               case 'R':
                  AnnotationInfo[] decodedAnnotations = null;
                  TypeAnnotationInfo[] decodedTypeAnnotations = null;
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeVisibleAnnotationsName)) {
                     decodedAnnotations = fieldInfo.decodeAnnotations(readOffset, true);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeInvisibleAnnotationsName)) {
                     decodedAnnotations = fieldInfo.decodeAnnotations(readOffset, false);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName)) {
                     decodedTypeAnnotations = fieldInfo.decodeTypeAnnotations(readOffset, true);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName)) {
                     decodedTypeAnnotations = fieldInfo.decodeTypeAnnotations(readOffset, false);
                  }

                  int length;
                  if (decodedAnnotations != null) {
                     if (annotations == null) {
                        annotations = decodedAnnotations;
                     } else {
                        length = annotations.length;
                        AnnotationInfo[] combined = new AnnotationInfo[length + decodedAnnotations.length];
                        System.arraycopy(annotations, 0, combined, 0, length);
                        System.arraycopy(decodedAnnotations, 0, combined, length, decodedAnnotations.length);
                        annotations = combined;
                     }
                  } else if (decodedTypeAnnotations != null) {
                     if (typeAnnotations == null) {
                        typeAnnotations = decodedTypeAnnotations;
                     } else {
                        length = typeAnnotations.length;
                        TypeAnnotationInfo[] combined = new TypeAnnotationInfo[length + decodedTypeAnnotations.length];
                        System.arraycopy(typeAnnotations, 0, combined, 0, length);
                        System.arraycopy(decodedTypeAnnotations, 0, combined, length, decodedTypeAnnotations.length);
                        typeAnnotations = combined;
                     }
                  }
                  break;
               case 'S':
                  if (CharOperation.equals(AttributeNamesConstants.SignatureName, attributeName)) {
                     fieldInfo.signatureUtf8Offset = fieldInfo.constantPoolOffsets[fieldInfo.u2At(readOffset + 6)] - fieldInfo.structOffset;
                  }
            }
         }

         readOffset = (int)((long)readOffset + 6L + fieldInfo.u4At(readOffset + 2));
      }

      fieldInfo.attributeBytes = readOffset;
      if (typeAnnotations != null) {
         return new FieldInfoWithTypeAnnotation(fieldInfo, annotations, typeAnnotations);
      } else if (annotations != null) {
         return new FieldInfoWithAnnotation(fieldInfo, annotations);
      } else {
         return fieldInfo;
      }
   }

   protected FieldInfo(byte[] classFileBytes, int[] offsets, int offset, long version) {
      super(classFileBytes, offsets, offset);
      this.version = version;
   }

   private AnnotationInfo[] decodeAnnotations(int offset, boolean runtimeVisible) {
      int numberOfAnnotations = this.u2At(offset + 6);
      if (numberOfAnnotations > 0) {
         int readOffset = offset + 8;
         AnnotationInfo[] newInfos = null;
         int newInfoCount = 0;

         for(int i = 0; i < numberOfAnnotations; ++i) {
            AnnotationInfo newInfo = new AnnotationInfo(this.reference, this.constantPoolOffsets, readOffset + this.structOffset, runtimeVisible, false);
            readOffset += newInfo.readOffset;
            long standardTagBits = newInfo.standardAnnotationTagBits;
            if (standardTagBits != 0L) {
               this.tagBits |= standardTagBits;
               if (this.version < 3473408L || (standardTagBits & 70368744177664L) == 0L) {
                  continue;
               }
            }

            if (newInfos == null) {
               newInfos = new AnnotationInfo[numberOfAnnotations - i];
            }

            newInfos[newInfoCount++] = newInfo;
         }

         if (newInfos != null) {
            if (newInfoCount != newInfos.length) {
               System.arraycopy(newInfos, 0, newInfos = new AnnotationInfo[newInfoCount], 0, newInfoCount);
            }

            return newInfos;
         }
      }

      return null;
   }

   TypeAnnotationInfo[] decodeTypeAnnotations(int offset, boolean runtimeVisible) {
      int numberOfAnnotations = this.u2At(offset + 6);
      if (numberOfAnnotations <= 0) {
         return null;
      } else {
         int readOffset = offset + 8;
         TypeAnnotationInfo[] typeAnnos = new TypeAnnotationInfo[numberOfAnnotations];

         for(int i = 0; i < numberOfAnnotations; ++i) {
            TypeAnnotationInfo newInfo = new TypeAnnotationInfo(this.reference, this.constantPoolOffsets, readOffset + this.structOffset, runtimeVisible, false);
            readOffset += newInfo.readOffset;
            typeAnnos[i] = newInfo;
         }

         return typeAnnos;
      }
   }

   public int compareTo(Object o) {
      return (new String(this.getName())).compareTo(new String(((FieldInfo)o).getName()));
   }

   public boolean equals(Object o) {
      return !(o instanceof FieldInfo) ? false : CharOperation.equals(this.getName(), ((FieldInfo)o).getName());
   }

   public int hashCode() {
      return CharOperation.hashCode(this.getName());
   }

   public Constant getConstant() {
      if (this.constant == null) {
         this.readConstantAttribute();
      }

      return this.constant;
   }

   public char[] getGenericSignature() {
      if (this.signatureUtf8Offset != -1) {
         if (this.signature == null) {
            this.signature = this.utf8At(this.signatureUtf8Offset + 3, this.u2At(this.signatureUtf8Offset + 1));
         }

         return this.signature;
      } else {
         return null;
      }
   }

   public int getModifiers() {
      if (this.accessFlags == -1) {
         this.accessFlags = this.u2At(0);
         this.readModifierRelatedAttributes();
      }

      return this.accessFlags;
   }

   public char[] getName() {
      if (this.name == null) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(2)] - this.structOffset;
         this.name = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      }

      return this.name;
   }

   public long getTagBits() {
      return this.tagBits;
   }

   public char[] getTypeName() {
      if (this.descriptor == null) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(4)] - this.structOffset;
         this.descriptor = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      }

      return this.descriptor;
   }

   public IBinaryAnnotation[] getAnnotations() {
      return null;
   }

   public IBinaryTypeAnnotation[] getTypeAnnotations() {
      return null;
   }

   public Object getWrappedConstantValue() {
      if (this.wrappedConstantValue == null && this.hasConstant()) {
         Constant fieldConstant = this.getConstant();
         switch (fieldConstant.typeID()) {
            case 2:
               this.wrappedConstantValue = fieldConstant.charValue();
               break;
            case 3:
               this.wrappedConstantValue = fieldConstant.byteValue();
               break;
            case 4:
               this.wrappedConstantValue = fieldConstant.shortValue();
               break;
            case 5:
               this.wrappedConstantValue = Util.toBoolean(fieldConstant.booleanValue());
            case 6:
            default:
               break;
            case 7:
               this.wrappedConstantValue = fieldConstant.longValue();
               break;
            case 8:
               this.wrappedConstantValue = new Double(fieldConstant.doubleValue());
               break;
            case 9:
               this.wrappedConstantValue = new Float(fieldConstant.floatValue());
               break;
            case 10:
               this.wrappedConstantValue = fieldConstant.intValue();
               break;
            case 11:
               this.wrappedConstantValue = fieldConstant.stringValue();
         }
      }

      return this.wrappedConstantValue;
   }

   public boolean hasConstant() {
      return this.getConstant() != Constant.NotAConstant;
   }

   protected void initialize() {
      this.getModifiers();
      this.getName();
      this.getConstant();
      this.getTypeName();
      this.getGenericSignature();
      this.reset();
   }

   public boolean isSynthetic() {
      return (this.getModifiers() & 4096) != 0;
   }

   private void readConstantAttribute() {
      int attributesCount = this.u2At(6);
      int readOffset = 8;
      boolean isConstant = false;

      for(int i = 0; i < attributesCount; ++i) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(readOffset)] - this.structOffset;
         char[] attributeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         if (CharOperation.equals(attributeName, AttributeNamesConstants.ConstantValueName)) {
            isConstant = true;
            int relativeOffset = this.constantPoolOffsets[this.u2At(readOffset + 6)] - this.structOffset;
            label35:
            switch (this.u1At(relativeOffset)) {
               case 3:
                  char[] sign = this.getTypeName();
                  if (sign.length == 1) {
                     switch (sign[0]) {
                        case 'B':
                           this.constant = ByteConstant.fromValue((byte)this.i4At(relativeOffset + 1));
                           break label35;
                        case 'C':
                           this.constant = CharConstant.fromValue((char)this.i4At(relativeOffset + 1));
                           break label35;
                        case 'I':
                           this.constant = IntConstant.fromValue(this.i4At(relativeOffset + 1));
                           break label35;
                        case 'S':
                           this.constant = ShortConstant.fromValue((short)this.i4At(relativeOffset + 1));
                           break label35;
                        case 'Z':
                           this.constant = BooleanConstant.fromValue(this.i4At(relativeOffset + 1) == 1);
                           break label35;
                        default:
                           this.constant = Constant.NotAConstant;
                     }
                  } else {
                     this.constant = Constant.NotAConstant;
                  }
                  break;
               case 4:
                  this.constant = FloatConstant.fromValue(this.floatAt(relativeOffset + 1));
                  break;
               case 5:
                  this.constant = LongConstant.fromValue(this.i8At(relativeOffset + 1));
                  break;
               case 6:
                  this.constant = DoubleConstant.fromValue(this.doubleAt(relativeOffset + 1));
               case 7:
               default:
                  break;
               case 8:
                  utf8Offset = this.constantPoolOffsets[this.u2At(relativeOffset + 1)] - this.structOffset;
                  this.constant = StringConstant.fromValue(String.valueOf(this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1))));
            }
         }

         readOffset = (int)((long)readOffset + 6L + this.u4At(readOffset + 2));
      }

      if (!isConstant) {
         this.constant = Constant.NotAConstant;
      }

   }

   private void readModifierRelatedAttributes() {
      int attributesCount = this.u2At(6);
      int readOffset = 8;

      for(int i = 0; i < attributesCount; ++i) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(readOffset)] - this.structOffset;
         char[] attributeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         if (attributeName.length != 0) {
            switch (attributeName[0]) {
               case 'D':
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.DeprecatedName)) {
                     this.accessFlags |= 1048576;
                  }
                  break;
               case 'S':
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.SyntheticName)) {
                     this.accessFlags |= 4096;
                  }
            }
         }

         readOffset = (int)((long)readOffset + 6L + this.u4At(readOffset + 2));
      }

   }

   public int sizeInBytes() {
      return this.attributeBytes;
   }

   public void throwFormatException() throws ClassFormatException {
      throw new ClassFormatException(17);
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer(this.getClass().getName());
      this.toStringContent(buffer);
      return buffer.toString();
   }

   protected void toStringContent(StringBuffer buffer) {
      int modifiers = this.getModifiers();
      buffer.append('{').append(((modifiers & 1048576) != 0 ? "deprecated " : Util.EMPTY_STRING) + ((modifiers & 1) == 1 ? "public " : Util.EMPTY_STRING) + ((modifiers & 2) == 2 ? "private " : Util.EMPTY_STRING) + ((modifiers & 4) == 4 ? "protected " : Util.EMPTY_STRING) + ((modifiers & 8) == 8 ? "static " : Util.EMPTY_STRING) + ((modifiers & 16) == 16 ? "final " : Util.EMPTY_STRING) + ((modifiers & 64) == 64 ? "volatile " : Util.EMPTY_STRING) + ((modifiers & 128) == 128 ? "transient " : Util.EMPTY_STRING)).append(this.getTypeName()).append(' ').append(this.getName()).append(' ').append(this.getConstant()).append('}').toString();
   }
}
