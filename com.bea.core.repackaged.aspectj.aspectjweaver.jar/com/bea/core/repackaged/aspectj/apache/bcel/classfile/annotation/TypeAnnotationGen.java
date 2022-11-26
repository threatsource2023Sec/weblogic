package com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TypeAnnotationGen {
   public static final TypeAnnotationGen[] NO_TYPE_ANNOTATIONS = new TypeAnnotationGen[0];
   public static final int[] NO_TYPE_PATH = new int[0];
   private ConstantPool cpool;
   private int targetType;
   private int[] typePath;
   private AnnotationGen annotation;
   private int info;
   private int info2;
   private int[] localVarTarget;
   public static final int CLASS_TYPE_PARAMETER = 0;
   public static final int METHOD_TYPE_PARAMETER = 1;
   public static final int CLASS_EXTENDS = 16;
   public static final int CLASS_TYPE_PARAMETER_BOUND = 17;
   public static final int METHOD_TYPE_PARAMETER_BOUND = 18;
   public static final int FIELD = 19;
   public static final int METHOD_RETURN = 20;
   public static final int METHOD_RECEIVER = 21;
   public static final int METHOD_FORMAL_PARAMETER = 22;
   public static final int THROWS = 23;
   public static final int LOCAL_VARIABLE = 64;
   public static final int RESOURCE_VARIABLE = 65;
   public static final int EXCEPTION_PARAMETER = 66;
   public static final int INSTANCEOF = 67;
   public static final int NEW = 68;
   public static final int CONSTRUCTOR_REFERENCE = 69;
   public static final int METHOD_REFERENCE = 70;
   public static final int CAST = 71;
   public static final int CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT = 72;
   public static final int METHOD_INVOCATION_TYPE_ARGUMENT = 73;
   public static final int CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT = 74;
   public static final int METHOD_REFERENCE_TYPE_ARGUMENT = 75;
   public static final int TYPE_PATH_ENTRY_KIND_ARRAY = 0;
   public static final int TYPE_PATH_ENTRY_KIND_INNER_TYPE = 1;
   public static final int TYPE_PATH_ENTRY_KIND_WILDCARD = 2;
   public static final int TYPE_PATH_ENTRY_KIND_TYPE_ARGUMENT = 3;

   private TypeAnnotationGen(ConstantPool cpool) {
      this.cpool = cpool;
   }

   public static TypeAnnotationGen read(DataInputStream dis, ConstantPool cpool, boolean isVisible) throws IOException {
      TypeAnnotationGen typeAnno = new TypeAnnotationGen(cpool);
      typeAnno.targetType = dis.readUnsignedByte();
      int typepathlength;
      switch (typeAnno.targetType) {
         case 0:
            typeAnno.info = dis.readUnsignedByte();
            break;
         case 1:
            typeAnno.info = dis.readUnsignedByte();
            break;
         case 16:
            typepathlength = dis.readUnsignedShort();
            if (typepathlength == 65535) {
               typeAnno.info = -1;
            } else {
               typeAnno.info = typepathlength;
            }
            break;
         case 17:
         case 18:
            typeAnno.info = dis.readUnsignedByte();
            typeAnno.info2 = dis.readUnsignedByte();
         case 19:
         case 20:
         case 21:
            break;
         case 22:
            typeAnno.info = dis.readUnsignedByte();
            break;
         case 23:
            typeAnno.info = dis.readUnsignedShort();
            break;
         case 64:
         case 65:
            typeAnno.localVarTarget = readLocalVarTarget(dis);
            break;
         case 66:
            typeAnno.info = dis.readUnsignedByte();
            break;
         case 67:
         case 68:
         case 69:
         case 70:
            typeAnno.info = dis.readUnsignedShort();
            break;
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
            typeAnno.info = dis.readUnsignedShort();
            typeAnno.info2 = dis.readUnsignedByte();
            break;
         default:
            throw new IllegalStateException("nyi " + typeAnno.targetType);
      }

      typepathlength = dis.readUnsignedByte();
      if (typepathlength == 0) {
         typeAnno.typePath = NO_TYPE_PATH;
      } else {
         typeAnno.typePath = new int[typepathlength * 2];
         int i = 0;

         for(int max = typepathlength * 2; i < max; ++i) {
            typeAnno.typePath[i] = dis.readUnsignedByte();
         }
      }

      typeAnno.annotation = AnnotationGen.read(dis, cpool, isVisible);
      return typeAnno;
   }

   public static int[] readLocalVarTarget(DataInputStream dis) throws IOException {
      int tableLength = dis.readUnsignedShort();
      int[] table = new int[tableLength * 3];
      int count = 0;

      for(int i = 0; i < tableLength; ++i) {
         table[count++] = dis.readUnsignedShort();
         table[count++] = dis.readUnsignedShort();
         table[count++] = dis.readUnsignedShort();
      }

      return table;
   }

   public void dump(DataOutputStream dos) throws IOException {
      int i;
      dos.writeByte(this.targetType);
      label35:
      switch (this.targetType) {
         case 0:
            dos.writeByte(this.info);
            break;
         case 1:
            dos.writeByte(this.info);
            break;
         case 16:
            dos.writeShort(this.info);
            break;
         case 17:
         case 18:
            dos.writeByte(this.info);
            dos.writeByte(this.info2);
         case 19:
         case 20:
         case 21:
            break;
         case 22:
            dos.writeByte(this.info);
            break;
         case 23:
            dos.writeShort(this.info);
            break;
         case 64:
         case 65:
            dos.writeShort(this.localVarTarget.length / 3);
            i = 0;

            while(true) {
               if (i >= this.localVarTarget.length) {
                  break label35;
               }

               dos.writeShort(this.localVarTarget[i]);
               ++i;
            }
         case 66:
            dos.writeByte(this.info);
            break;
         case 67:
         case 68:
         case 69:
         case 70:
            dos.writeShort(this.info);
            break;
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
            dos.writeShort(this.info);
            dos.writeByte(this.info);
            break;
         default:
            throw new IllegalStateException("nyi " + this.targetType);
      }

      dos.writeByte(this.typePath.length);

      for(i = 0; i < this.typePath.length; ++i) {
         dos.writeByte(this.typePath[i]);
      }

      this.annotation.dump(dos);
   }

   public int getSupertypeIndex() {
      assert this.targetType == 16;

      return this.info;
   }

   public int getOffset() {
      assert this.targetType == 67 || this.targetType == 68 || this.targetType == 69 || this.targetType == 70 || this.targetType == 71 || this.targetType == 72 || this.targetType == 73 || this.targetType == 74 || this.targetType == 75;

      return this.info;
   }

   public int getTypeParameterIndex() {
      assert this.targetType == 0 || this.targetType == 1 || this.targetType == 17 || this.targetType == 18;

      return this.info;
   }

   public int getTypeArgumentIndex() {
      assert this.targetType == 71 || this.targetType == 72 || this.targetType == 73 || this.targetType == 74 || this.targetType == 75;

      return this.info2;
   }

   public int getBoundIndex() {
      assert this.targetType == 17 || this.targetType == 18;

      return this.info2;
   }

   public int getMethodFormalParameterIndex() {
      assert this.targetType == 22;

      return this.info;
   }

   public int getThrowsTypeIndex() {
      assert this.targetType == 23;

      return this.info;
   }

   public int[] getLocalVarTarget() {
      assert this.targetType == 64 || this.targetType == 65;

      return this.localVarTarget;
   }

   public int getExceptionTableIndex() {
      assert this.targetType == 66;

      return this.info;
   }

   public int getTargetType() {
      return this.targetType;
   }

   public AnnotationGen getAnnotation() {
      return this.annotation;
   }

   public int[] getTypePath() {
      return this.typePath;
   }

   public String getTypePathString() {
      return toTypePathString(this.typePath);
   }

   public static String toTypePathString(int[] typepath) {
      StringBuilder sb = new StringBuilder();
      int count = 0;
      sb.append("[");

      while(count < typepath.length) {
         if (count > 0) {
            sb.append(",");
         }

         switch (typepath[count++]) {
            case 0:
               sb.append("ARRAY");
               ++count;
               break;
            case 1:
               sb.append("INNER_TYPE");
               ++count;
               break;
            case 2:
               sb.append("WILDCARD");
               ++count;
               break;
            case 3:
               sb.append("TYPE_ARGUMENT(").append(typepath[count++]).append(")");
         }
      }

      sb.append("]");
      return sb.toString();
   }
}
