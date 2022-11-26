package com.bea.core.repackaged.springframework.asm;

final class AnnotationWriter extends AnnotationVisitor {
   private final SymbolTable symbolTable;
   private final boolean useNamedValues;
   private final ByteVector annotation;
   private final int numElementValuePairsOffset;
   private int numElementValuePairs;
   private final AnnotationWriter previousAnnotation;
   private AnnotationWriter nextAnnotation;

   AnnotationWriter(SymbolTable symbolTable, boolean useNamedValues, ByteVector annotation, AnnotationWriter previousAnnotation) {
      super(458752);
      this.symbolTable = symbolTable;
      this.useNamedValues = useNamedValues;
      this.annotation = annotation;
      this.numElementValuePairsOffset = annotation.length == 0 ? -1 : annotation.length - 2;
      this.previousAnnotation = previousAnnotation;
      if (previousAnnotation != null) {
         previousAnnotation.nextAnnotation = this;
      }

   }

   AnnotationWriter(SymbolTable symbolTable, ByteVector annotation, AnnotationWriter previousAnnotation) {
      this(symbolTable, true, annotation, previousAnnotation);
   }

   public void visit(String name, Object value) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      if (value instanceof String) {
         this.annotation.put12(115, this.symbolTable.addConstantUtf8((String)value));
      } else if (value instanceof Byte) {
         this.annotation.put12(66, this.symbolTable.addConstantInteger((Byte)value).index);
      } else if (value instanceof Boolean) {
         int booleanValue = (Boolean)value ? 1 : 0;
         this.annotation.put12(90, this.symbolTable.addConstantInteger(booleanValue).index);
      } else if (value instanceof Character) {
         this.annotation.put12(67, this.symbolTable.addConstantInteger((Character)value).index);
      } else if (value instanceof Short) {
         this.annotation.put12(83, this.symbolTable.addConstantInteger((Short)value).index);
      } else if (value instanceof Type) {
         this.annotation.put12(99, this.symbolTable.addConstantUtf8(((Type)value).getDescriptor()));
      } else {
         int var5;
         int var6;
         int intValue;
         if (value instanceof byte[]) {
            byte[] byteArray = (byte[])((byte[])value);
            this.annotation.put12(91, byteArray.length);
            byte[] var4 = byteArray;
            var5 = byteArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               intValue = var4[var6];
               this.annotation.put12(66, this.symbolTable.addConstantInteger(intValue).index);
            }
         } else if (value instanceof boolean[]) {
            boolean[] booleanArray = (boolean[])((boolean[])value);
            this.annotation.put12(91, booleanArray.length);
            boolean[] var12 = booleanArray;
            var5 = booleanArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               boolean booleanValue = var12[var6];
               this.annotation.put12(90, this.symbolTable.addConstantInteger(booleanValue ? 1 : 0).index);
            }
         } else if (value instanceof short[]) {
            short[] shortArray = (short[])((short[])value);
            this.annotation.put12(91, shortArray.length);
            short[] var14 = shortArray;
            var5 = shortArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               intValue = var14[var6];
               this.annotation.put12(83, this.symbolTable.addConstantInteger(intValue).index);
            }
         } else if (value instanceof char[]) {
            char[] charArray = (char[])((char[])value);
            this.annotation.put12(91, charArray.length);
            char[] var16 = charArray;
            var5 = charArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               char charValue = var16[var6];
               this.annotation.put12(67, this.symbolTable.addConstantInteger(charValue).index);
            }
         } else if (value instanceof int[]) {
            int[] intArray = (int[])((int[])value);
            this.annotation.put12(91, intArray.length);
            int[] var18 = intArray;
            var5 = intArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               intValue = var18[var6];
               this.annotation.put12(73, this.symbolTable.addConstantInteger(intValue).index);
            }
         } else if (value instanceof long[]) {
            long[] longArray = (long[])((long[])value);
            this.annotation.put12(91, longArray.length);
            long[] var20 = longArray;
            var5 = longArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               long longValue = var20[var6];
               this.annotation.put12(74, this.symbolTable.addConstantLong(longValue).index);
            }
         } else if (value instanceof float[]) {
            float[] floatArray = (float[])((float[])value);
            this.annotation.put12(91, floatArray.length);
            float[] var22 = floatArray;
            var5 = floatArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               float floatValue = var22[var6];
               this.annotation.put12(70, this.symbolTable.addConstantFloat(floatValue).index);
            }
         } else if (value instanceof double[]) {
            double[] doubleArray = (double[])((double[])value);
            this.annotation.put12(91, doubleArray.length);
            double[] var24 = doubleArray;
            var5 = doubleArray.length;

            for(var6 = 0; var6 < var5; ++var6) {
               double doubleValue = var24[var6];
               this.annotation.put12(68, this.symbolTable.addConstantDouble(doubleValue).index);
            }
         } else {
            Symbol symbol = this.symbolTable.addConstant(value);
            this.annotation.put12(".s.IFJDCS".charAt(symbol.tag), symbol.index);
         }
      }

   }

   public void visitEnum(String name, String descriptor, String value) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      this.annotation.put12(101, this.symbolTable.addConstantUtf8(descriptor)).putShort(this.symbolTable.addConstantUtf8(value));
   }

   public AnnotationVisitor visitAnnotation(String name, String descriptor) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      this.annotation.put12(64, this.symbolTable.addConstantUtf8(descriptor)).putShort(0);
      return new AnnotationWriter(this.symbolTable, this.annotation, (AnnotationWriter)null);
   }

   public AnnotationVisitor visitArray(String name) {
      ++this.numElementValuePairs;
      if (this.useNamedValues) {
         this.annotation.putShort(this.symbolTable.addConstantUtf8(name));
      }

      this.annotation.put12(91, 0);
      return new AnnotationWriter(this.symbolTable, false, this.annotation, (AnnotationWriter)null);
   }

   public void visitEnd() {
      if (this.numElementValuePairsOffset != -1) {
         byte[] data = this.annotation.data;
         data[this.numElementValuePairsOffset] = (byte)(this.numElementValuePairs >>> 8);
         data[this.numElementValuePairsOffset + 1] = (byte)this.numElementValuePairs;
      }

   }

   int computeAnnotationsSize(String attributeName) {
      if (attributeName != null) {
         this.symbolTable.addConstantUtf8(attributeName);
      }

      int attributeSize = 8;

      for(AnnotationWriter annotationWriter = this; annotationWriter != null; annotationWriter = annotationWriter.previousAnnotation) {
         attributeSize += annotationWriter.annotation.length;
      }

      return attributeSize;
   }

   void putAnnotations(int attributeNameIndex, ByteVector output) {
      int attributeLength = 2;
      int numAnnotations = 0;
      AnnotationWriter annotationWriter = this;

      AnnotationWriter firstAnnotation;
      for(firstAnnotation = null; annotationWriter != null; annotationWriter = annotationWriter.previousAnnotation) {
         annotationWriter.visitEnd();
         attributeLength += annotationWriter.annotation.length;
         ++numAnnotations;
         firstAnnotation = annotationWriter;
      }

      output.putShort(attributeNameIndex);
      output.putInt(attributeLength);
      output.putShort(numAnnotations);

      for(annotationWriter = firstAnnotation; annotationWriter != null; annotationWriter = annotationWriter.nextAnnotation) {
         output.putByteArray(annotationWriter.annotation.data, 0, annotationWriter.annotation.length);
      }

   }

   static int computeParameterAnnotationsSize(String attributeName, AnnotationWriter[] annotationWriters, int annotableParameterCount) {
      int attributeSize = 7 + 2 * annotableParameterCount;

      for(int i = 0; i < annotableParameterCount; ++i) {
         AnnotationWriter annotationWriter = annotationWriters[i];
         attributeSize += annotationWriter == null ? 0 : annotationWriter.computeAnnotationsSize(attributeName) - 8;
      }

      return attributeSize;
   }

   static void putParameterAnnotations(int attributeNameIndex, AnnotationWriter[] annotationWriters, int annotableParameterCount, ByteVector output) {
      int attributeLength = 1 + 2 * annotableParameterCount;

      int i;
      AnnotationWriter annotationWriter;
      for(i = 0; i < annotableParameterCount; ++i) {
         annotationWriter = annotationWriters[i];
         attributeLength += annotationWriter == null ? 0 : annotationWriter.computeAnnotationsSize((String)null) - 8;
      }

      output.putShort(attributeNameIndex);
      output.putInt(attributeLength);
      output.putByte(annotableParameterCount);

      for(i = 0; i < annotableParameterCount; ++i) {
         annotationWriter = annotationWriters[i];
         AnnotationWriter firstAnnotation = null;

         int numAnnotations;
         for(numAnnotations = 0; annotationWriter != null; annotationWriter = annotationWriter.previousAnnotation) {
            annotationWriter.visitEnd();
            ++numAnnotations;
            firstAnnotation = annotationWriter;
         }

         output.putShort(numAnnotations);

         for(annotationWriter = firstAnnotation; annotationWriter != null; annotationWriter = annotationWriter.nextAnnotation) {
            output.putByteArray(annotationWriter.annotation.data, 0, annotationWriter.annotation.length);
         }
      }

   }
}
