package org.mozilla.classfile;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import org.mozilla.javascript.LabelTable;
import org.mozilla.javascript.LocalVariable;
import org.mozilla.javascript.VariableTable;

public class ClassFileWriter extends LabelTable {
   public static final short ACC_PUBLIC = 1;
   public static final short ACC_PRIVATE = 2;
   public static final short ACC_PROTECTED = 4;
   public static final short ACC_STATIC = 8;
   public static final short ACC_FINAL = 16;
   public static final short ACC_SYNCHRONIZED = 32;
   public static final short ACC_VOLATILE = 64;
   public static final short ACC_TRANSIENT = 128;
   public static final short ACC_NATIVE = 256;
   public static final short ACC_ABSTRACT = 1024;
   private static final int LineNumberTableSize = 16;
   private static final int ExceptionTableSize = 4;
   private static final long FileHeaderConstant = -3819410108756852691L;
   private static final boolean DEBUG = false;
   private static final boolean DEBUGSTACK = false;
   private static final boolean DEBUGLABELS = false;
   private static final boolean DEBUGCODE = false;
   private static final int CodeBufferSize = 128;
   private ExceptionTableEntry[] itsExceptionTable;
   private int itsExceptionTableTop;
   private int[] itsLineNumberTable;
   private int itsLineNumberTableTop;
   private byte[] itsCodeBuffer;
   private int itsCodeBufferTop;
   private ConstantPool itsConstantPool = new ConstantPool();
   private short itsSourceFileAttributeIndex;
   private ClassFileMethod itsCurrentMethod;
   private short itsStackTop;
   private short itsMaxStack;
   private short itsMaxLocals;
   private Vector itsMethods = new Vector();
   private Vector itsFields = new Vector();
   private Vector itsInterfaces = new Vector();
   private short itsFlags;
   private short itsThisClassIndex;
   private short itsSuperClassIndex;
   private short itsSourceFileNameIndex;

   public ClassFileWriter(String var1, String var2, String var3) {
      this.itsThisClassIndex = this.itsConstantPool.addClass(var1);
      this.itsSuperClassIndex = this.itsConstantPool.addClass(var2);
      if (var3 != null) {
         this.itsSourceFileNameIndex = this.itsConstantPool.addUtf8(var3);
      }

      this.itsFlags = 1;
   }

   public void add(byte var1) {
      this.addToCodeBuffer(var1);
      this.itsStackTop = (short)(this.itsStackTop + ByteCode.stackChange[var1 & 255]);
      if (this.itsStackTop > this.itsMaxStack) {
         this.itsMaxStack = this.itsStackTop;
      }

   }

   public void add(byte var1, int var2) {
      this.itsStackTop = (short)(this.itsStackTop + ByteCode.stackChange[var1 & 255]);
      if (this.itsStackTop > this.itsMaxStack) {
         this.itsMaxStack = this.itsStackTop;
      }

      switch (var1) {
         case -103:
         case -102:
         case -101:
         case -100:
         case -99:
         case -98:
         case -97:
         case -96:
         case -95:
         case -94:
         case -93:
         case -92:
         case -91:
         case -90:
         case -89:
         case -88:
         case -58:
         case -57:
            int var3 = this.itsCodeBufferTop;
            this.addToCodeBuffer(var1);
            int var4;
            if ((var2 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
               var4 = this.acquireLabel();
               int var5 = var4 & Integer.MAX_VALUE;
               super.itsLabelTable[var5].setPC((short)(var3 + var2));
               this.addToCodeBuffer((byte)(var2 >> 8));
               this.addToCodeBuffer((byte)var2);
            } else {
               var4 = var2 & Integer.MAX_VALUE;
               short var7 = super.itsLabelTable[var4].getPC();
               if (var7 != -1) {
                  short var6 = (short)(var7 - var3);
                  this.addToCodeBuffer((byte)(var6 >> 8));
                  this.addToCodeBuffer((byte)var6);
               } else {
                  super.itsLabelTable[var4].addFixup(var3 + 1);
                  this.addToCodeBuffer((byte)0);
                  this.addToCodeBuffer((byte)0);
               }
            }
            break;
         case -87:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
            if (var2 >= 256) {
               this.addToCodeBuffer((byte)-60);
               this.addToCodeBuffer(var1);
               this.addToCodeBuffer((byte)(var2 >> 8));
               this.addToCodeBuffer((byte)var2);
            } else {
               this.addToCodeBuffer(var1);
               this.addToCodeBuffer((byte)var2);
            }
            break;
         case -76:
         case -75:
            this.addToCodeBuffer(var1);
            this.addToCodeBuffer((byte)(var2 >> 8));
            this.addToCodeBuffer((byte)var2);
            break;
         case -68:
            this.addToCodeBuffer(var1);
            this.addToCodeBuffer((byte)var2);
            break;
         case 16:
            this.addToCodeBuffer(var1);
            this.addToCodeBuffer((byte)var2);
            break;
         case 17:
            this.addToCodeBuffer(var1);
            this.addToCodeBuffer((byte)(var2 >> 8));
            this.addToCodeBuffer((byte)var2);
            break;
         case 18:
         case 19:
         case 20:
            if (var2 < 256 && var1 != 19 && var1 != 20) {
               this.addToCodeBuffer(var1);
               this.addToCodeBuffer((byte)var2);
            } else {
               if (var1 == 18) {
                  this.addToCodeBuffer((byte)19);
               } else {
                  this.addToCodeBuffer(var1);
               }

               this.addToCodeBuffer((byte)(var2 >> 8));
               this.addToCodeBuffer((byte)var2);
            }
            break;
         default:
            throw new RuntimeException("Unexpected opcode for 1 operand");
      }

   }

   public void add(byte var1, int var2, int var3) {
      this.itsStackTop = (short)(this.itsStackTop + ByteCode.stackChange[var1 & 255]);
      if (this.itsStackTop > this.itsMaxStack) {
         this.itsMaxStack = this.itsStackTop;
      }

      if (var1 == -124) {
         if (var2 <= 255 && var3 >= -128 && var3 <= 127) {
            this.addToCodeBuffer((byte)-60);
            this.addToCodeBuffer((byte)-124);
            this.addToCodeBuffer((byte)var2);
            this.addToCodeBuffer((byte)var3);
         } else {
            this.addToCodeBuffer((byte)-60);
            this.addToCodeBuffer((byte)-124);
            this.addToCodeBuffer((byte)(var2 >> 8));
            this.addToCodeBuffer((byte)var2);
            this.addToCodeBuffer((byte)(var3 >> 8));
            this.addToCodeBuffer((byte)var3);
         }
      } else {
         if (var1 != -59) {
            throw new RuntimeException("Unexpected opcode for 2 operands");
         }

         this.addToCodeBuffer((byte)-59);
         this.addToCodeBuffer((byte)(var2 >> 8));
         this.addToCodeBuffer((byte)var2);
         this.addToCodeBuffer((byte)var3);
      }

   }

   public void add(byte var1, String var2) {
      this.itsStackTop = (short)(this.itsStackTop + ByteCode.stackChange[var1 & 255]);
      switch (var1) {
         case -69:
         case -67:
         case -64:
         case -63:
            short var3 = this.itsConstantPool.addClass(var2);
            this.addToCodeBuffer(var1);
            this.addToCodeBuffer((byte)(var3 >> 8));
            this.addToCodeBuffer((byte)var3);
            if (this.itsStackTop > this.itsMaxStack) {
               this.itsMaxStack = this.itsStackTop;
            }

            return;
         case -68:
         case -66:
         case -65:
         default:
            throw new RuntimeException("bad opcode for class reference");
      }
   }

   public void add(byte var1, String var2, String var3, String var4) {
      this.itsStackTop = (short)(this.itsStackTop + ByteCode.stackChange[var1 & 255]);
      char var5 = var4.charAt(0);
      int var6 = var5 != 'J' && var5 != 'D' ? 1 : 2;
      switch (var1) {
         case -78:
         case -76:
            this.itsStackTop = (short)(this.itsStackTop + var6);
            break;
         case -77:
         case -75:
            this.itsStackTop = (short)(this.itsStackTop - var6);
            break;
         default:
            throw new RuntimeException("bad opcode for field reference");
      }

      short var7 = this.itsConstantPool.addFieldRef(var2, var3, var4);
      this.addToCodeBuffer(var1);
      this.addToCodeBuffer((byte)(var7 >> 8));
      this.addToCodeBuffer((byte)var7);
      if (this.itsStackTop > this.itsMaxStack) {
         this.itsMaxStack = this.itsStackTop;
      }

   }

   public void add(byte var1, String var2, String var3, String var4, String var5) {
      int var6 = this.sizeOfParameters(var4);
      this.itsStackTop = (short)(this.itsStackTop - (var6 & '\uffff'));
      this.itsStackTop = (short)(this.itsStackTop + ByteCode.stackChange[var1 & 255]);
      if (this.itsStackTop > this.itsMaxStack) {
         this.itsMaxStack = this.itsStackTop;
      }

      switch (var1) {
         case -74:
         case -73:
         case -72:
         case -71:
            char var7 = var5.charAt(0);
            if (var7 != 'V') {
               if (var7 != 'J' && var7 != 'D') {
                  ++this.itsStackTop;
               } else {
                  this.itsStackTop = (short)(this.itsStackTop + 2);
               }
            }

            this.addToCodeBuffer(var1);
            short var8;
            if (var1 == -71) {
               var8 = this.itsConstantPool.addInterfaceMethodRef(var2, var3, var4 + var5);
               this.addToCodeBuffer((byte)(var8 >> 8));
               this.addToCodeBuffer((byte)var8);
               this.addToCodeBuffer((byte)((var6 >> 16) + 1));
               this.addToCodeBuffer((byte)0);
            } else {
               var8 = this.itsConstantPool.addMethodRef(var2, var3, var4 + var5);
               this.addToCodeBuffer((byte)(var8 >> 8));
               this.addToCodeBuffer((byte)var8);
            }

            if (this.itsStackTop > this.itsMaxStack) {
               this.itsMaxStack = this.itsStackTop;
            }

            return;
         default:
            throw new RuntimeException("bad opcode for method reference");
      }
   }

   public void addExceptionHandler(int var1, int var2, int var3, String var4) {
      if ((var1 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
         throw new RuntimeException("Bad startLabel");
      } else if ((var2 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
         throw new RuntimeException("Bad endLabel");
      } else if ((var3 & Integer.MIN_VALUE) != Integer.MIN_VALUE) {
         throw new RuntimeException("Bad handlerLabel");
      } else {
         ExceptionTableEntry var5 = new ExceptionTableEntry(var1, var2, var3, var4 == null ? 0 : this.itsConstantPool.addClass(var4));
         if (this.itsExceptionTable == null) {
            this.itsExceptionTable = new ExceptionTableEntry[4];
            this.itsExceptionTable[0] = var5;
            this.itsExceptionTableTop = 1;
         } else {
            if (this.itsExceptionTableTop == this.itsExceptionTable.length) {
               ExceptionTableEntry[] var6 = this.itsExceptionTable;
               this.itsExceptionTable = new ExceptionTableEntry[this.itsExceptionTableTop * 2];
               System.arraycopy(var6, 0, this.itsExceptionTable, 0, this.itsExceptionTableTop);
            }

            this.itsExceptionTable[this.itsExceptionTableTop++] = var5;
         }

      }
   }

   public void addField(String var1, String var2, short var3) {
      short var4 = this.itsConstantPool.addUtf8(var1);
      short var5 = this.itsConstantPool.addUtf8(var2);
      this.itsFields.addElement(new ClassFileField(var4, var5, var3));
   }

   public void addField(String var1, String var2, short var3, double var4) {
      short var6 = this.itsConstantPool.addUtf8(var1);
      short var7 = this.itsConstantPool.addUtf8(var2);
      short[] var8 = new short[]{this.itsConstantPool.addUtf8("ConstantValue"), 0, 2, this.itsConstantPool.addConstant(var4)};
      this.itsFields.addElement(new ClassFileField(var6, var7, var3, var8));
   }

   public void addField(String var1, String var2, short var3, int var4) {
      short var5 = this.itsConstantPool.addUtf8(var1);
      short var6 = this.itsConstantPool.addUtf8(var2);
      short[] var7 = new short[]{this.itsConstantPool.addUtf8("ConstantValue"), 0, 2, this.itsConstantPool.addConstant(var4)};
      this.itsFields.addElement(new ClassFileField(var5, var6, var3, var7));
   }

   public void addField(String var1, String var2, short var3, long var4) {
      short var6 = this.itsConstantPool.addUtf8(var1);
      short var7 = this.itsConstantPool.addUtf8(var2);
      short[] var8 = new short[]{this.itsConstantPool.addUtf8("ConstantValue"), 0, 2, this.itsConstantPool.addConstant(var4)};
      this.itsFields.addElement(new ClassFileField(var6, var7, var3, var8));
   }

   public void addInterface(String var1) {
      short var2 = this.itsConstantPool.addClass(var1);
      this.itsInterfaces.addElement(new Short(var2));
   }

   public void addLineNumberEntry(short var1) {
      if (this.itsLineNumberTable == null) {
         this.itsLineNumberTable = new int[16];
         this.itsLineNumberTable[0] = (this.itsCodeBufferTop << 16) + var1;
         this.itsLineNumberTableTop = 1;
      } else {
         if (this.itsLineNumberTableTop == this.itsLineNumberTable.length) {
            int[] var2 = this.itsLineNumberTable;
            this.itsLineNumberTable = new int[this.itsLineNumberTableTop * 2];
            System.arraycopy(var2, 0, this.itsLineNumberTable, 0, this.itsLineNumberTableTop);
         }

         this.itsLineNumberTable[this.itsLineNumberTableTop++] = (this.itsCodeBufferTop << 16) + var1;
      }

   }

   public void addLoadConstant(double var1) {
      this.add((byte)20, this.itsConstantPool.addConstant(var1));
   }

   public void addLoadConstant(float var1) {
      this.add((byte)18, this.itsConstantPool.addConstant(var1));
   }

   public void addLoadConstant(int var1) {
      this.add((byte)18, this.itsConstantPool.addConstant(var1));
   }

   public void addLoadConstant(long var1) {
      this.add((byte)20, this.itsConstantPool.addConstant(var1));
   }

   public void addLoadConstant(String var1) {
      this.add((byte)18, this.itsConstantPool.addConstant(var1));
   }

   public void addToCodeBuffer(byte var1) {
      if (this.itsCodeBuffer == null) {
         this.itsCodeBuffer = new byte[128];
         this.itsCodeBuffer[0] = var1;
         this.itsCodeBufferTop = 1;
      } else {
         if (this.itsCodeBufferTop == this.itsCodeBuffer.length) {
            byte[] var2 = this.itsCodeBuffer;
            this.itsCodeBuffer = new byte[this.itsCodeBufferTop * 2];
            System.arraycopy(var2, 0, this.itsCodeBuffer, 0, this.itsCodeBufferTop);
         }

         this.itsCodeBuffer[this.itsCodeBufferTop++] = var1;
      }

   }

   public void adjustStackTop(int var1) {
      this.itsStackTop = (short)(this.itsStackTop + var1);
      if (this.itsStackTop > this.itsMaxStack) {
         this.itsMaxStack = this.itsStackTop;
      }

   }

   public static String fullyQualifiedForm(String var0) {
      return var0.replace('.', '/');
   }

   public int getCurrentCodeOffset() {
      return this.itsCodeBufferTop;
   }

   public short getStackTop() {
      return this.itsStackTop;
   }

   public int markHandler(int var1) {
      this.itsStackTop = 1;
      return this.markLabel(var1);
   }

   public int markLabel(int var1) {
      return super.markLabel(var1, (short)this.itsCodeBufferTop);
   }

   public int markLabel(int var1, short var2) {
      this.itsStackTop = var2;
      return super.markLabel(var1, (short)this.itsCodeBufferTop);
   }

   public void setFlags(short var1) {
      this.itsFlags = var1;
   }

   private int sizeOfParameters(String var1) {
      int var2 = 1;
      int var3 = 0;
      int var4 = 0;

      label44:
      while(var1.charAt(var2) != ')') {
         label49: {
            label40: {
               switch (var1.charAt(var2)) {
                  case 'B':
                  case 'C':
                  case 'F':
                  case 'I':
                  case 'S':
                  case 'Z':
                     break label49;
                  case 'D':
                  case 'J':
                     ++var3;
                     break label49;
                  case 'E':
                  case 'G':
                  case 'H':
                  case 'K':
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
                     throw new RuntimeException("Bad signature character");
                  case 'L':
                     break label40;
                  case '[':
               }

               while(true) {
                  if (var1.charAt(var2) != '[') {
                     if (var1.charAt(var2) != 'L') {
                        ++var3;
                        ++var4;
                        ++var2;
                        continue label44;
                     }
                     break;
                  }

                  ++var2;
               }
            }

            ++var3;
            ++var4;

            while(true) {
               if (var1.charAt(var2++) == ';') {
                  continue label44;
               }
            }
         }

         ++var3;
         ++var4;
         ++var2;
      }

      return var4 << 16 | var3;
   }

   public void startMethod(String var1, String var2, short var3) {
      short var4 = this.itsConstantPool.addUtf8(var1);
      short var5 = this.itsConstantPool.addUtf8(var2);
      this.itsCurrentMethod = new ClassFileMethod(var4, var5, var3);
      this.itsMethods.addElement(this.itsCurrentMethod);
   }

   public void stopMethod(short var1, VariableTable var2) {
      for(int var3 = 0; var3 < super.itsLabelTableTop; ++var3) {
         super.itsLabelTable[var3].fixGotos(this.itsCodeBuffer);
      }

      this.itsMaxLocals = var1;
      int var4 = 0;
      if (this.itsLineNumberTable != null) {
         var4 = 8 + this.itsLineNumberTableTop * 4;
      }

      int var5 = 0;
      if (var2 != null) {
         var5 = 8 + var2.size() * 10;
      }

      int var6 = 14 + this.itsCodeBufferTop + 2 + this.itsExceptionTableTop * 8 + 2 + var4 + var5;
      byte[] var7 = new byte[var6];
      int var8 = 0;
      short var9 = this.itsConstantPool.addUtf8("Code");
      var7[var8++] = (byte)(var9 >> 8);
      var7[var8++] = (byte)var9;
      var6 -= 6;
      var7[var8++] = (byte)(var6 >> 24);
      var7[var8++] = (byte)(var6 >> 16);
      var7[var8++] = (byte)(var6 >> 8);
      var7[var8++] = (byte)var6;
      var7[var8++] = (byte)(this.itsMaxStack >> 8);
      var7[var8++] = (byte)this.itsMaxStack;
      var7[var8++] = (byte)(this.itsMaxLocals >> 8);
      var7[var8++] = (byte)this.itsMaxLocals;
      var7[var8++] = (byte)(this.itsCodeBufferTop >> 24);
      var7[var8++] = (byte)(this.itsCodeBufferTop >> 16);
      var7[var8++] = (byte)(this.itsCodeBufferTop >> 8);
      var7[var8++] = (byte)this.itsCodeBufferTop;
      System.arraycopy(this.itsCodeBuffer, 0, var7, var8, this.itsCodeBufferTop);
      var8 += this.itsCodeBufferTop;
      int var10;
      short var11;
      int var12;
      int var13;
      int var14;
      if (this.itsExceptionTableTop > 0) {
         var7[var8++] = (byte)(this.itsExceptionTableTop >> 8);
         var7[var8++] = (byte)this.itsExceptionTableTop;

         for(var10 = 0; var10 < this.itsExceptionTableTop; ++var10) {
            var11 = this.itsExceptionTable[var10].getStartPC(super.itsLabelTable);
            var7[var8++] = (byte)(var11 >> 8);
            var7[var8++] = (byte)var11;
            var12 = this.itsExceptionTable[var10].getEndPC(super.itsLabelTable);
            var7[var8++] = (byte)(var12 >> 8);
            var7[var8++] = (byte)var12;
            var13 = this.itsExceptionTable[var10].getHandlerPC(super.itsLabelTable);
            var7[var8++] = (byte)(var13 >> 8);
            var7[var8++] = (byte)var13;
            var14 = this.itsExceptionTable[var10].getCatchType();
            var7[var8++] = (byte)(var14 >> 8);
            var7[var8++] = (byte)var14;
         }
      } else {
         var7[var8++] = 0;
         var7[var8++] = 0;
      }

      var10 = 0;
      if (this.itsLineNumberTable != null) {
         ++var10;
      }

      if (var2 != null) {
         ++var10;
      }

      var7[var8++] = 0;
      var7[var8++] = (byte)var10;
      if (this.itsLineNumberTable != null) {
         var11 = this.itsConstantPool.addUtf8("LineNumberTable");
         var7[var8++] = (byte)(var11 >> 8);
         var7[var8++] = (byte)var11;
         var12 = 2 + this.itsLineNumberTableTop * 4;
         var7[var8++] = (byte)(var12 >> 24);
         var7[var8++] = (byte)(var12 >> 16);
         var7[var8++] = (byte)(var12 >> 8);
         var7[var8++] = (byte)var12;
         var7[var8++] = (byte)(this.itsLineNumberTableTop >> 8);
         var7[var8++] = (byte)this.itsLineNumberTableTop;

         for(var13 = 0; var13 < this.itsLineNumberTableTop; ++var13) {
            var7[var8++] = (byte)(this.itsLineNumberTable[var13] >> 24);
            var7[var8++] = (byte)(this.itsLineNumberTable[var13] >> 16);
            var7[var8++] = (byte)(this.itsLineNumberTable[var13] >> 8);
            var7[var8++] = (byte)this.itsLineNumberTable[var13];
         }
      }

      if (var2 != null) {
         var11 = this.itsConstantPool.addUtf8("LocalVariableTable");
         var7[var8++] = (byte)(var11 >> 8);
         var7[var8++] = (byte)var11;
         var12 = var2.size();
         var13 = 2 + var12 * 10;
         var7[var8++] = (byte)(var13 >> 24);
         var7[var8++] = (byte)(var13 >> 16);
         var7[var8++] = (byte)(var13 >> 8);
         var7[var8++] = (byte)var13;
         var7[var8++] = (byte)(var12 >> 8);
         var7[var8++] = (byte)var12;

         for(var14 = 0; var14 < var12; ++var14) {
            LocalVariable var15 = var2.getVariable(var14);
            int var16 = var15.getStartPC();
            var7[var8++] = (byte)(var16 >> 8);
            var7[var8++] = (byte)var16;
            int var17 = this.itsCodeBufferTop - var16;
            var7[var8++] = (byte)(var17 >> 8);
            var7[var8++] = (byte)var17;
            short var18 = this.itsConstantPool.addUtf8(var15.getName());
            var7[var8++] = (byte)(var18 >> 8);
            var7[var8++] = (byte)var18;
            short var19 = this.itsConstantPool.addUtf8(var15.isNumber() ? "D" : "Ljava/lang/Object;");
            var7[var8++] = (byte)(var19 >> 8);
            var7[var8++] = (byte)var19;
            short var20 = var15.getJRegister();
            var7[var8++] = (byte)(var20 >> 8);
            var7[var8++] = (byte)var20;
         }
      }

      this.itsCurrentMethod.setCodeAttribute(var7);
      this.itsExceptionTable = null;
      this.itsExceptionTableTop = 0;
      super.itsLabelTableTop = 0;
      this.itsLineNumberTable = null;
      this.itsCodeBufferTop = 0;
      this.itsCurrentMethod = null;
      this.itsMaxStack = 0;
      this.itsStackTop = 0;
   }

   public void write(OutputStream var1) throws IOException {
      DataOutputStream var2 = new DataOutputStream(var1);
      short var3 = 0;
      if (this.itsSourceFileNameIndex != 0) {
         var3 = this.itsConstantPool.addUtf8("SourceFile");
      }

      var2.writeLong(-3819410108756852691L);
      this.itsConstantPool.write(var2);
      var2.writeShort(this.itsFlags);
      var2.writeShort(this.itsThisClassIndex);
      var2.writeShort(this.itsSuperClassIndex);
      var2.writeShort(this.itsInterfaces.size());

      for(int var4 = 0; var4 < this.itsInterfaces.size(); ++var4) {
         var2.writeShort((Short)this.itsInterfaces.elementAt(var4));
      }

      var2.writeShort(this.itsFields.size());

      for(int var5 = 0; var5 < this.itsFields.size(); ++var5) {
         ((ClassFileField)this.itsFields.elementAt(var5)).write(var2);
      }

      var2.writeShort(this.itsMethods.size());

      for(int var6 = 0; var6 < this.itsMethods.size(); ++var6) {
         ((ClassFileMethod)this.itsMethods.elementAt(var6)).write(var2);
      }

      if (this.itsSourceFileNameIndex != 0) {
         var2.writeShort(1);
         var2.writeShort(var3);
         var2.writeInt(2);
         var2.writeShort(this.itsSourceFileNameIndex);
      } else {
         var2.writeShort(0);
      }

   }
}
