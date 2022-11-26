package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.AttributeNamesConstants;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryMethod;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;

public class MethodInfo extends ClassFileStruct implements IBinaryMethod, Comparable {
   private static final char[][] noException;
   private static final char[][] noArgumentNames;
   private static final char[] ARG;
   protected int accessFlags = -1;
   protected int attributeBytes;
   protected char[] descriptor;
   protected volatile char[][] exceptionNames;
   protected char[] name;
   protected char[] signature;
   protected int signatureUtf8Offset = -1;
   protected long tagBits;
   protected volatile char[][] argumentNames;
   protected long version;

   static {
      noException = CharOperation.NO_CHAR_CHAR;
      noArgumentNames = CharOperation.NO_CHAR_CHAR;
      ARG = "arg".toCharArray();
   }

   public static MethodInfo createMethod(byte[] classFileBytes, int[] offsets, int offset, long version) {
      MethodInfo methodInfo = new MethodInfo(classFileBytes, offsets, offset, version);
      int attributesCount = methodInfo.u2At(6);
      int readOffset = 8;
      AnnotationInfo[] annotations = null;
      AnnotationInfo[][] parameterAnnotations = null;
      TypeAnnotationInfo[] typeAnnotations = null;

      for(int i = 0; i < attributesCount; ++i) {
         int utf8Offset = methodInfo.constantPoolOffsets[methodInfo.u2At(readOffset)] - methodInfo.structOffset;
         char[] attributeName = methodInfo.utf8At(utf8Offset + 3, methodInfo.u2At(utf8Offset + 1));
         if (attributeName.length > 0) {
            label89:
            switch (attributeName[0]) {
               case 'M':
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.MethodParametersName)) {
                     methodInfo.decodeMethodParameters(readOffset, methodInfo);
                  }
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               default:
                  break;
               case 'R':
                  AnnotationInfo[] methodAnnotations = null;
                  AnnotationInfo[][] paramAnnotations = null;
                  TypeAnnotationInfo[] methodTypeAnnotations = null;
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeVisibleAnnotationsName)) {
                     methodAnnotations = decodeMethodAnnotations(readOffset, true, methodInfo);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeInvisibleAnnotationsName)) {
                     methodAnnotations = decodeMethodAnnotations(readOffset, false, methodInfo);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeVisibleParameterAnnotationsName)) {
                     paramAnnotations = decodeParamAnnotations(readOffset, true, methodInfo);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeInvisibleParameterAnnotationsName)) {
                     paramAnnotations = decodeParamAnnotations(readOffset, false, methodInfo);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName)) {
                     methodTypeAnnotations = decodeTypeAnnotations(readOffset, true, methodInfo);
                  } else if (CharOperation.equals(attributeName, AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName)) {
                     methodTypeAnnotations = decodeTypeAnnotations(readOffset, false, methodInfo);
                  }

                  int length;
                  if (methodAnnotations != null) {
                     if (annotations == null) {
                        annotations = methodAnnotations;
                     } else {
                        length = annotations.length;
                        AnnotationInfo[] newAnnotations = new AnnotationInfo[length + methodAnnotations.length];
                        System.arraycopy(annotations, 0, newAnnotations, 0, length);
                        System.arraycopy(methodAnnotations, 0, newAnnotations, length, methodAnnotations.length);
                        annotations = newAnnotations;
                     }
                  } else if (paramAnnotations != null) {
                     length = paramAnnotations.length;
                     if (parameterAnnotations == null) {
                        parameterAnnotations = paramAnnotations;
                     } else {
                        int p = 0;

                        while(true) {
                           if (p >= length) {
                              break label89;
                           }

                           int numberOfAnnotations = paramAnnotations[p] == null ? 0 : paramAnnotations[p].length;
                           if (numberOfAnnotations > 0) {
                              if (parameterAnnotations[p] == null) {
                                 parameterAnnotations[p] = paramAnnotations[p];
                              } else {
                                 int length = parameterAnnotations[p].length;
                                 AnnotationInfo[] newAnnotations = new AnnotationInfo[length + numberOfAnnotations];
                                 System.arraycopy(parameterAnnotations[p], 0, newAnnotations, 0, length);
                                 System.arraycopy(paramAnnotations[p], 0, newAnnotations, length, numberOfAnnotations);
                                 parameterAnnotations[p] = newAnnotations;
                              }
                           }

                           ++p;
                        }
                     }
                  } else if (methodTypeAnnotations != null) {
                     if (typeAnnotations == null) {
                        typeAnnotations = methodTypeAnnotations;
                     } else {
                        length = typeAnnotations.length;
                        TypeAnnotationInfo[] newAnnotations = new TypeAnnotationInfo[length + methodTypeAnnotations.length];
                        System.arraycopy(typeAnnotations, 0, newAnnotations, 0, length);
                        System.arraycopy(methodTypeAnnotations, 0, newAnnotations, length, methodTypeAnnotations.length);
                        typeAnnotations = newAnnotations;
                     }
                  }
                  break;
               case 'S':
                  if (CharOperation.equals(AttributeNamesConstants.SignatureName, attributeName)) {
                     methodInfo.signatureUtf8Offset = methodInfo.constantPoolOffsets[methodInfo.u2At(readOffset + 6)] - methodInfo.structOffset;
                  }
            }
         }

         readOffset = (int)((long)readOffset + 6L + methodInfo.u4At(readOffset + 2));
      }

      methodInfo.attributeBytes = readOffset;
      if (typeAnnotations != null) {
         return new MethodInfoWithTypeAnnotations(methodInfo, annotations, parameterAnnotations, typeAnnotations);
      } else if (parameterAnnotations != null) {
         return new MethodInfoWithParameterAnnotations(methodInfo, annotations, parameterAnnotations);
      } else if (annotations != null) {
         return new MethodInfoWithAnnotations(methodInfo, annotations);
      } else {
         return methodInfo;
      }
   }

   static AnnotationInfo[] decodeAnnotations(int offset, boolean runtimeVisible, int numberOfAnnotations, MethodInfo methodInfo) {
      AnnotationInfo[] result = new AnnotationInfo[numberOfAnnotations];
      int readOffset = offset;

      for(int i = 0; i < numberOfAnnotations; ++i) {
         result[i] = new AnnotationInfo(methodInfo.reference, methodInfo.constantPoolOffsets, readOffset + methodInfo.structOffset, runtimeVisible, false);
         readOffset += result[i].readOffset;
      }

      return result;
   }

   static AnnotationInfo[] decodeMethodAnnotations(int offset, boolean runtimeVisible, MethodInfo methodInfo) {
      int numberOfAnnotations = methodInfo.u2At(offset + 6);
      if (numberOfAnnotations <= 0) {
         return null;
      } else {
         AnnotationInfo[] annos = decodeAnnotations(offset + 8, runtimeVisible, numberOfAnnotations, methodInfo);
         if (runtimeVisible) {
            int numRetainedAnnotations = 0;

            for(int i = 0; i < numberOfAnnotations; ++i) {
               long standardAnnoTagBits = annos[i].standardAnnotationTagBits;
               methodInfo.tagBits |= standardAnnoTagBits;
               if (standardAnnoTagBits == 0L || methodInfo.version >= 3473408L && (standardAnnoTagBits & 70368744177664L) != 0L) {
                  ++numRetainedAnnotations;
               } else {
                  annos[i] = null;
               }
            }

            if (numRetainedAnnotations != numberOfAnnotations) {
               if (numRetainedAnnotations == 0) {
                  return null;
               }

               AnnotationInfo[] temp = new AnnotationInfo[numRetainedAnnotations];
               int tmpIndex = 0;

               for(int i = 0; i < numberOfAnnotations; ++i) {
                  if (annos[i] != null) {
                     temp[tmpIndex++] = annos[i];
                  }
               }

               annos = temp;
            }
         }

         return annos;
      }
   }

   static TypeAnnotationInfo[] decodeTypeAnnotations(int offset, boolean runtimeVisible, MethodInfo methodInfo) {
      int numberOfAnnotations = methodInfo.u2At(offset + 6);
      if (numberOfAnnotations <= 0) {
         return null;
      } else {
         int readOffset = offset + 8;
         TypeAnnotationInfo[] typeAnnos = new TypeAnnotationInfo[numberOfAnnotations];

         for(int i = 0; i < numberOfAnnotations; ++i) {
            TypeAnnotationInfo newInfo = new TypeAnnotationInfo(methodInfo.reference, methodInfo.constantPoolOffsets, readOffset + methodInfo.structOffset, runtimeVisible, false);
            readOffset += newInfo.readOffset;
            typeAnnos[i] = newInfo;
         }

         return typeAnnos;
      }
   }

   static AnnotationInfo[][] decodeParamAnnotations(int offset, boolean runtimeVisible, MethodInfo methodInfo) {
      AnnotationInfo[][] allParamAnnotations = null;
      int numberOfParameters = methodInfo.u1At(offset + 6);
      if (numberOfParameters > 0) {
         int readOffset = offset + 7;

         for(int i = 0; i < numberOfParameters; ++i) {
            int numberOfAnnotations = methodInfo.u2At(readOffset);
            readOffset += 2;
            if (numberOfAnnotations > 0) {
               if (allParamAnnotations == null) {
                  allParamAnnotations = new AnnotationInfo[numberOfParameters][];
               }

               AnnotationInfo[] annos = decodeAnnotations(readOffset, runtimeVisible, numberOfAnnotations, methodInfo);
               allParamAnnotations[i] = annos;

               for(int aIndex = 0; aIndex < annos.length; ++aIndex) {
                  readOffset += annos[aIndex].readOffset;
               }
            }
         }
      }

      return allParamAnnotations;
   }

   protected MethodInfo(byte[] classFileBytes, int[] offsets, int offset, long version) {
      super(classFileBytes, offsets, offset);
      this.version = version;
   }

   public int compareTo(Object o) {
      MethodInfo otherMethod = (MethodInfo)o;
      int result = (new String(this.getSelector())).compareTo(new String(otherMethod.getSelector()));
      return result != 0 ? result : (new String(this.getMethodDescriptor())).compareTo(new String(otherMethod.getMethodDescriptor()));
   }

   public boolean equals(Object o) {
      if (!(o instanceof MethodInfo)) {
         return false;
      } else {
         MethodInfo otherMethod = (MethodInfo)o;
         return CharOperation.equals(this.getSelector(), otherMethod.getSelector()) && CharOperation.equals(this.getMethodDescriptor(), otherMethod.getMethodDescriptor());
      }
   }

   public int hashCode() {
      return CharOperation.hashCode(this.getSelector()) + CharOperation.hashCode(this.getMethodDescriptor());
   }

   public IBinaryAnnotation[] getAnnotations() {
      return null;
   }

   public char[][] getArgumentNames() {
      if (this.argumentNames == null) {
         this.readCodeAttribute();
      }

      return this.argumentNames;
   }

   public Object getDefaultValue() {
      return null;
   }

   public char[][] getExceptionTypeNames() {
      if (this.exceptionNames == null) {
         this.readExceptionAttributes();
      }

      return this.exceptionNames;
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

   public char[] getMethodDescriptor() {
      if (this.descriptor == null) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(4)] - this.structOffset;
         this.descriptor = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      }

      return this.descriptor;
   }

   public int getModifiers() {
      if (this.accessFlags == -1) {
         this.readModifierRelatedAttributes();
      }

      return this.accessFlags;
   }

   public IBinaryAnnotation[] getParameterAnnotations(int index, char[] classFileName) {
      return null;
   }

   public int getAnnotatedParametersCount() {
      return 0;
   }

   public IBinaryTypeAnnotation[] getTypeAnnotations() {
      return null;
   }

   public char[] getSelector() {
      if (this.name == null) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(2)] - this.structOffset;
         this.name = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
      }

      return this.name;
   }

   public long getTagBits() {
      return this.tagBits;
   }

   protected void initialize() {
      this.getModifiers();
      this.getSelector();
      this.getMethodDescriptor();
      this.getExceptionTypeNames();
      this.getGenericSignature();
      this.getArgumentNames();
      this.reset();
   }

   public boolean isClinit() {
      return JavaBinaryNames.isClinit(this.getSelector());
   }

   public boolean isConstructor() {
      return JavaBinaryNames.isConstructor(this.getSelector());
   }

   public boolean isSynthetic() {
      return (this.getModifiers() & 4096) != 0;
   }

   private synchronized void readExceptionAttributes() {
      int attributesCount = this.u2At(6);
      int readOffset = 8;
      char[][] names = null;

      for(int i = 0; i < attributesCount; ++i) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(readOffset)] - this.structOffset;
         char[] attributeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         if (CharOperation.equals(attributeName, AttributeNamesConstants.ExceptionsName)) {
            int entriesNumber = this.u2At(readOffset + 6);
            readOffset += 8;
            if (entriesNumber == 0) {
               names = noException;
            } else {
               names = new char[entriesNumber][];

               for(int j = 0; j < entriesNumber; ++j) {
                  utf8Offset = this.constantPoolOffsets[this.u2At(this.constantPoolOffsets[this.u2At(readOffset)] - this.structOffset + 1)] - this.structOffset;
                  names[j] = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
                  readOffset += 2;
               }
            }
         } else {
            readOffset = (int)((long)readOffset + 6L + this.u4At(readOffset + 2));
         }
      }

      if (names == null) {
         this.exceptionNames = noException;
      } else {
         this.exceptionNames = names;
      }

   }

   private synchronized void readModifierRelatedAttributes() {
      int flags = this.u2At(0);
      int attributesCount = this.u2At(6);
      int readOffset = 8;

      for(int i = 0; i < attributesCount; ++i) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(readOffset)] - this.structOffset;
         char[] attributeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         if (attributeName.length != 0) {
            switch (attributeName[0]) {
               case 'A':
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.AnnotationDefaultName)) {
                     flags |= 131072;
                  }
                  break;
               case 'D':
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.DeprecatedName)) {
                     flags |= 1048576;
                  }
                  break;
               case 'S':
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.SyntheticName)) {
                     flags |= 4096;
                  }
                  break;
               case 'V':
                  if (CharOperation.equals(attributeName, AttributeNamesConstants.VarargsName)) {
                     flags |= 128;
                  }
            }
         }

         readOffset = (int)((long)readOffset + 6L + this.u4At(readOffset + 2));
      }

      this.accessFlags = flags;
   }

   public int sizeInBytes() {
      return this.attributeBytes;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      this.toString(buffer);
      return buffer.toString();
   }

   void toString(StringBuffer buffer) {
      buffer.append(this.getClass().getName());
      this.toStringContent(buffer);
   }

   protected void toStringContent(StringBuffer buffer) {
      BinaryTypeFormatter.methodToStringContent(buffer, this);
   }

   private synchronized void readCodeAttribute() {
      int attributesCount = this.u2At(6);
      int readOffset = 8;
      if (attributesCount != 0) {
         for(int i = 0; i < attributesCount; ++i) {
            int utf8Offset = this.constantPoolOffsets[this.u2At(readOffset)] - this.structOffset;
            char[] attributeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
            if (CharOperation.equals(attributeName, AttributeNamesConstants.CodeName)) {
               this.decodeCodeAttribute(readOffset);
               if (this.argumentNames == null) {
                  this.argumentNames = noArgumentNames;
               }

               return;
            }

            readOffset = (int)((long)readOffset + 6L + this.u4At(readOffset + 2));
         }
      }

      this.argumentNames = noArgumentNames;
   }

   private void decodeCodeAttribute(int offset) {
      int readOffset = offset + 10;
      int codeLength = (int)this.u4At(readOffset);
      readOffset += 4 + codeLength;
      int exceptionTableLength = this.u2At(readOffset);
      readOffset += 2;
      int attributesCount;
      if (exceptionTableLength != 0) {
         for(attributesCount = 0; attributesCount < exceptionTableLength; ++attributesCount) {
            readOffset += 8;
         }
      }

      attributesCount = this.u2At(readOffset);
      readOffset += 2;

      for(int i = 0; i < attributesCount; ++i) {
         int utf8Offset = this.constantPoolOffsets[this.u2At(readOffset)] - this.structOffset;
         char[] attributeName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
         if (CharOperation.equals(attributeName, AttributeNamesConstants.LocalVariableTableName)) {
            this.decodeLocalVariableAttribute(readOffset, codeLength);
         }

         readOffset = (int)((long)readOffset + 6L + this.u4At(readOffset + 2));
      }

   }

   private void decodeLocalVariableAttribute(int offset, int codeLength) {
      int readOffset = offset + 6;
      int length = this.u2At(readOffset);
      if (length != 0) {
         readOffset += 2;
         char[][] names = new char[length][];
         int argumentNamesIndex = 0;

         for(int i = 0; i < length; ++i) {
            int startPC = this.u2At(readOffset);
            if (startPC != 0) {
               break;
            }

            int nameIndex = this.u2At(4 + readOffset);
            int utf8Offset = this.constantPoolOffsets[nameIndex] - this.structOffset;
            char[] localVariableName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
            if (!CharOperation.equals(localVariableName, ConstantPool.This)) {
               names[argumentNamesIndex++] = localVariableName;
            }

            readOffset += 10;
         }

         if (argumentNamesIndex != names.length) {
            System.arraycopy(names, 0, names = new char[argumentNamesIndex][], 0, argumentNamesIndex);
         }

         this.argumentNames = names;
      }

   }

   private void decodeMethodParameters(int offset, MethodInfo methodInfo) {
      int readOffset = offset + 6;
      int length = this.u1At(readOffset);
      if (length != 0) {
         ++readOffset;
         char[][] names = new char[length][];

         for(int i = 0; i < length; ++i) {
            int nameIndex = this.u2At(readOffset);
            if (nameIndex != 0) {
               int utf8Offset = this.constantPoolOffsets[nameIndex] - this.structOffset;
               char[] parameterName = this.utf8At(utf8Offset + 3, this.u2At(utf8Offset + 1));
               names[i] = parameterName;
            } else {
               names[i] = CharOperation.concat(ARG, String.valueOf(i).toCharArray());
            }

            readOffset += 4;
         }

         this.argumentNames = names;
      }

   }
}
