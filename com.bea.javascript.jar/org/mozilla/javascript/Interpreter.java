package org.mozilla.javascript;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

public class Interpreter extends LabelTable {
   public static final boolean printICode = false;
   boolean itsInFunctionFlag;
   Vector itsFunctionList;
   InterpreterData itsData;
   VariableTable itsVariableTable;
   int itsTryDepth = 0;
   int itsStackDepth = 0;
   int itsEpilogLabel = -1;
   String itsSourceFile;
   int itsLineNumber = 0;
   InterpretedFunction[] itsNestedFunctions = null;
   static PrintWriter out;
   private int version;
   private boolean inLineStepMode;
   private StringBuffer debugSource;
   private static final Object DBL_MRK = new Object();

   private final int addByte(byte var1, int var2) {
      if (this.itsData.itsICode.length == var2) {
         byte[] var3 = new byte[var2 * 2];
         System.arraycopy(this.itsData.itsICode, 0, var3, 0, var2);
         this.itsData.itsICode = var3;
      }

      this.itsData.itsICode[var2++] = var1;
      return var2;
   }

   private int addGoto(int var1, int var2, int var3) {
      int var4 = var3;
      var3 = this.addByte((byte)var2, var3);
      int var5 = var1 & Integer.MAX_VALUE;
      short var6 = super.itsLabelTable[var5].getPC();
      if (var6 != -1) {
         short var7 = (short)(var6 - var4);
         var3 = this.addByte((byte)(var7 >> 8), var3);
         var3 = this.addByte((byte)var7, var3);
      } else {
         super.itsLabelTable[var5].addFixup(var4 + 1);
         var3 = this.addByte((byte)0, var3);
         var3 = this.addByte((byte)0, var3);
      }

      return var3;
   }

   private int addGoto(Node var1, int var2, int var3) {
      int var4;
      if (var1.getType() == 5) {
         if (this.itsEpilogLabel == -1) {
            this.itsEpilogLabel = this.acquireLabel();
         }

         var4 = this.itsEpilogLabel;
      } else {
         Node var5 = (Node)var1.getProp(1);
         Object var6 = var5.getProp(20);
         if (var6 == null) {
            var4 = this.acquireLabel();
            var5.putProp(20, new Integer(var4));
         } else {
            var4 = (Integer)var6;
         }
      }

      var3 = this.addGoto(var4, (byte)var2, var3);
      return var3;
   }

   private int addLocalRef(Node var1, int var2) {
      Integer var4 = (Integer)var1.getProp(7);
      int var3;
      if (var4 == null) {
         var3 = this.itsData.itsMaxLocals++;
         var1.putProp(7, new Integer(var3));
      } else {
         var3 = var4;
      }

      var2 = this.addByte((byte)var3, var2);
      if (var3 >= this.itsData.itsMaxLocals) {
         this.itsData.itsMaxLocals = var3 + 1;
      }

      return var2;
   }

   private final int addNumber(double var1, int var3) {
      int var4 = this.itsData.itsNumberTableIndex;
      if (this.itsData.itsNumberTable.length == var4) {
         double[] var5 = new double[var4 * 2];
         System.arraycopy(this.itsData.itsNumberTable, 0, var5, 0, var4);
         this.itsData.itsNumberTable = var5;
      }

      this.itsData.itsNumberTable[var4] = var1;
      this.itsData.itsNumberTableIndex = var4 + 1;
      var3 = this.addByte((byte)(var4 >> 8), var3);
      var3 = this.addByte((byte)(var4 & 255), var3);
      return var3;
   }

   private final int addString(String var1, int var2) {
      int var3 = this.itsData.itsStringTableIndex;
      if (this.itsData.itsStringTable.length == var3) {
         String[] var4 = new String[var3 * 2];
         System.arraycopy(this.itsData.itsStringTable, 0, var4, 0, var3);
         this.itsData.itsStringTable = var4;
      }

      this.itsData.itsStringTable[var3] = var1;
      this.itsData.itsStringTableIndex = var3 + 1;
      var2 = this.addByte((byte)(var3 >> 8), var2);
      var2 = this.addByte((byte)(var3 & 255), var2);
      return var2;
   }

   private void badTree(Node var1) {
      try {
         out = new PrintWriter(new FileOutputStream("icode.txt", true));
         out.println("Un-handled node : " + var1.toString());
         out.close();
      } catch (IOException var2) {
      }

      throw new RuntimeException("Un-handled node : " + var1.toString());
   }

   public Object compile(Context var1, Scriptable var2, Node var3, Object var4, SecuritySupport var5, ClassNameHelper var6) throws IOException {
      this.version = var1.getLanguageVersion();
      this.itsData = new InterpreterData(0, 0, 0, var4, var1.hasCompileFunctionsWithDynamicScope(), false);
      if (var3 instanceof FunctionNode) {
         FunctionNode var7 = (FunctionNode)var3;
         InterpretedFunction var8 = this.generateFunctionICode(var1, var2, var7, var4);
         var8.itsData.itsFunctionType = var7.getFunctionType();
         createFunctionObject(var8, var2);
         return var8;
      } else {
         return this.generateScriptICode(var1, var2, var3, var4);
      }
   }

   private static void createFunctionObject(InterpretedFunction var0, Scriptable var1) {
      var0.setPrototype(ScriptableObject.getClassPrototype(var1, "Function"));
      var0.setParentScope(var1);
      InterpreterData var2 = var0.itsData;
      if (var2.itsName.length() != 0) {
         if (var2.itsFunctionType == 1 && var0.itsClosure == null || var2.itsFunctionType == 3 && var0.itsClosure != null) {
            ScriptRuntime.setProp(var1, var0.itsData.itsName, var0, var1);
         }

      }
   }

   public IRFactory createIRFactory(TokenStream var1, ClassNameHelper var2, Scriptable var3) {
      return new IRFactory(var1, var3);
   }

   private static void do_add(Object var0, double var1, Object[] var3, double[] var4, int var5, boolean var6) {
      if (var0 instanceof Scriptable) {
         if (var0 == Undefined.instance) {
            var0 = ScriptRuntime.NaNobj;
         }

         var0 = ((Scriptable)var0).getDefaultValue((Class)null);
      }

      if (var0 instanceof String) {
         if (var6) {
            var3[var5] = (String)var0 + ScriptRuntime.toString(var1);
         } else {
            var3[var5] = ScriptRuntime.toString(var1) + (String)var0;
         }
      } else {
         double var7 = var0 instanceof Number ? ((Number)var0).doubleValue() : ScriptRuntime.toNumber(var0);
         var3[var5] = DBL_MRK;
         var4[var5] = var7 + var1;
      }

   }

   private static void do_add(Object[] var0, double[] var1, int var2) {
      Object var3 = var0[var2 + 1];
      Object var4 = var0[var2];
      double var5;
      if (var3 == DBL_MRK) {
         var5 = var1[var2 + 1];
         if (var4 == DBL_MRK) {
            var1[var2] += var5;
         } else {
            do_add(var4, var5, var0, var1, var2, true);
         }
      } else if (var4 == DBL_MRK) {
         do_add(var3, var1[var2], var0, var1, var2, false);
      } else {
         if (var4 instanceof Scriptable) {
            var4 = ((Scriptable)var4).getDefaultValue((Class)null);
         }

         if (var3 instanceof Scriptable) {
            var3 = ((Scriptable)var3).getDefaultValue((Class)null);
         }

         if (!(var4 instanceof String) && !(var3 instanceof String)) {
            var5 = var4 instanceof Number ? ((Number)var4).doubleValue() : ScriptRuntime.toNumber(var4);
            double var7 = var3 instanceof Number ? ((Number)var3).doubleValue() : ScriptRuntime.toNumber(var3);
            var0[var2] = DBL_MRK;
            var1[var2] = var5 + var7;
         } else {
            var0[var2] = ScriptRuntime.toString(var4) + ScriptRuntime.toString(var3);
         }
      }

   }

   private static boolean do_eq(double var0, Object var2) {
      while(!(var2 instanceof Number)) {
         if (var2 instanceof String) {
            return var0 == ScriptRuntime.toNumber((String)var2);
         }

         if (var2 instanceof Boolean) {
            return var0 == (double)((Boolean)var2 ? 1 : 0);
         }

         if (!(var2 instanceof Scriptable)) {
            return false;
         }

         if (var2 == Undefined.instance) {
            return false;
         }

         var2 = ScriptRuntime.toPrimitive(var2);
      }

      return var0 == ((Number)var2).doubleValue();
   }

   private static boolean do_eq(Object[] var0, double[] var1, int var2) {
      Object var4 = var0[var2 + 1];
      Object var5 = var0[var2];
      boolean var3;
      if (var4 == DBL_MRK) {
         if (var5 == DBL_MRK) {
            var3 = var1[var2] == var1[var2 + 1];
         } else {
            var3 = do_eq(var1[var2 + 1], var5);
         }
      } else if (var5 == DBL_MRK) {
         var3 = do_eq(var1[var2], var4);
      } else {
         var3 = ScriptRuntime.eq(var5, var4);
      }

      return var3;
   }

   private static boolean do_sheq(Object[] var0, double[] var1, int var2) {
      Object var4 = var0[var2 + 1];
      Object var5 = var0[var2];
      boolean var3;
      double var6;
      if (var4 == DBL_MRK) {
         var6 = var1[var2 + 1];
         if (var5 == DBL_MRK) {
            var3 = var1[var2] == var6;
         } else {
            var3 = var5 instanceof Number;
            if (var3) {
               var3 = ((Number)var5).doubleValue() == var6;
            }
         }
      } else if (var4 instanceof Number) {
         var6 = ((Number)var4).doubleValue();
         if (var5 == DBL_MRK) {
            var3 = var1[var2] == var6;
         } else {
            var3 = var5 instanceof Number;
            if (var3) {
               var3 = ((Number)var5).doubleValue() == var6;
            }
         }
      } else {
         var3 = ScriptRuntime.shallowEq(var5, var4);
      }

      return var3;
   }

   private static Object doubleWrap(double var0) {
      return new Double(var0);
   }

   private static void dumpICode(InterpreterData var0) {
   }

   private InterpretedFunction generateFunctionICode(Context var1, Scriptable var2, FunctionNode var3, Object var4) {
      this.itsFunctionList = (Vector)var3.getProp(5);
      if (this.itsFunctionList != null) {
         this.generateNestedFunctions(var2, var1, var4);
      }

      Object[] var5 = null;
      Vector var6 = (Vector)var3.getProp(12);
      if (var6 != null) {
         var5 = this.generateRegExpLiterals(var1, var2, var6);
      }

      VariableTable var7 = var3.getVariableTable();
      boolean var8 = var3.requiresActivation() || var1.isGeneratingDebugChanged() && var1.isGeneratingDebug();
      this.generateICodeFromTree(var3.getLastChild(), var7, var8, var4);
      this.itsData.itsName = var3.getFunctionName();
      this.itsData.itsSourceFile = (String)var3.getProp(16);
      this.itsData.itsSource = (String)var3.getProp(17);
      this.itsData.itsNestedFunctions = this.itsNestedFunctions;
      this.itsData.itsRegExpLiterals = var5;
      String[] var9 = this.itsVariableTable.getAllNames();
      short var10 = (short)this.itsVariableTable.getParameterCount();
      InterpretedFunction var11 = new InterpretedFunction(var1, this.itsData, var9, var10);
      if (var1.debugger != null) {
         var1.debugger.handleCompilationDone(var1, var11, this.debugSource);
      }

      return var11;
   }

   private int generateICode(Node var1, int var2) {
      int var3 = var1.getType();
      Node var4 = var1.getFirstChild();
      Node var5 = var4;
      Node var6;
      int var7;
      int var8;
      Node var9;
      Node var11;
      Object var14;
      int var15;
      Node var19;
      String var22;
      switch (var3) {
         case 2:
         case 57:
            var2 = this.updateLineNumber(var1, var2);
         case 3:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)var3, var2);
            --this.itsStackDepth;
            break;
         case 4:
            var2 = this.addByte((byte)var3, var2);
            break;
         case 5:
            var2 = this.updateLineNumber(var1, var2);
            if (var4 != null) {
               var2 = this.generateICode(var4, var2);
            } else {
               var2 = this.addByte((byte)74, var2);
               ++this.itsStackDepth;
               if (this.itsStackDepth > this.itsData.itsMaxStack) {
                  this.itsData.itsMaxStack = this.itsStackDepth;
               }
            }

            var2 = this.addGoto(var1, 5, var2);
            --this.itsStackDepth;
            break;
         case 7:
         case 8:
            var2 = this.generateICode(var4, var2);
            --this.itsStackDepth;
         case 6:
            var2 = this.addGoto(var1, (byte)var3, var2);
            break;
         case 9:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 28:
         case 29:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 47:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 58:
         case 59:
         case 60:
         case 63:
         case 64:
         case 65:
         case 66:
         case 67:
         case 74:
         case 76:
         case 78:
         case 81:
         case 82:
         case 83:
         case 84:
         case 85:
         case 86:
         case 87:
         case 88:
         case 89:
         case 90:
         case 91:
         case 92:
         case 93:
         case 94:
         case 95:
         case 97:
         case 98:
         case 99:
         case 104:
         case 108:
         case 111:
         case 112:
         case 113:
         case 114:
         case 118:
         case 119:
         case 120:
         case 121:
         case 122:
         case 123:
         case 125:
         case 126:
         case 127:
         case 129:
         case 130:
         case 131:
         case 134:
         case 135:
         case 140:
         default:
            this.badTree(var1);
            break;
         case 10:
            var2 = this.generateICode(var4, var2);
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)10, var2);
            var2 = this.addString(var5.getString(), var2);
            --this.itsStackDepth;
            break;
         case 11:
         case 12:
         case 13:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 31:
         case 41:
            var2 = this.generateICode(var4, var2);
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)var3, var2);
            --this.itsStackDepth;
            break;
         case 30:
         case 43:
            if (this.itsSourceFile != null && (this.itsData.itsSourceFile == null || !this.itsSourceFile.equals(this.itsData.itsSourceFile))) {
               this.itsData.itsSourceFile = this.itsSourceFile;
            }

            var2 = this.addByte((byte)-108, var2);
            var15 = 0;

            short var23;
            for(var23 = -1; var4 != null; ++var15) {
               var2 = this.generateICode(var4, var2);
               if (var23 == -1) {
                  if (var4.getType() == 44) {
                     var23 = (short)(this.itsData.itsStringTableIndex - 1);
                  } else if (var4.getType() == 39) {
                     var23 = (short)(this.itsData.itsStringTableIndex - 1);
                  }
               }

               var4 = var4.getNextSibling();
            }

            if (var1.getProp(30) != null) {
               var2 = this.addByte((byte)67, var2);
               var2 = this.addByte((byte)(this.itsLineNumber >> 8), var2);
               var2 = this.addByte((byte)(this.itsLineNumber & 255), var2);
               var2 = this.addString(this.itsSourceFile, var2);
            } else {
               var2 = this.addByte((byte)var3, var2);
               var2 = this.addByte((byte)(var23 >> 8), var2);
               var2 = this.addByte((byte)(var23 & 255), var2);
            }

            this.itsStackDepth -= var15 - 1;
            if (var3 == 30) {
               --var15;
            } else {
               var15 -= 2;
            }

            var2 = this.addByte((byte)(var15 >> 8), var2);
            var2 = this.addByte((byte)(var15 & 255), var2);
            if (var15 > this.itsData.itsMaxArgs) {
               this.itsData.itsMaxArgs = var15;
            }

            var2 = this.addByte((byte)-108, var2);
            break;
         case 32:
            var22 = var1.getString();
            var7 = -1;
            if (this.itsInFunctionFlag && !this.itsData.itsNeedsActivation) {
               var7 = this.itsVariableTable.getOrdinal(var22);
            }

            if (var7 == -1) {
               var2 = this.addByte((byte)78, var2);
               var2 = this.addString(var22, var2);
            } else {
               var2 = this.addByte((byte)72, var2);
               var2 = this.addByte((byte)var7, var2);
               var2 = this.addByte((byte)32, var2);
            }

            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 39:
            var2 = this.generateICode(var4, var2);
            var22 = (String)var1.getProp(19);
            if (var22 != null) {
               if (var22.equals("__proto__")) {
                  var2 = this.addByte((byte)81, var2);
               } else if (var22.equals("__parent__")) {
                  var2 = this.addByte((byte)86, var2);
               } else {
                  this.badTree(var1);
               }
            } else {
               var4 = var4.getNextSibling();
               var2 = this.generateICode(var4, var2);
               var2 = this.addByte((byte)39, var2);
               --this.itsStackDepth;
            }
            break;
         case 40:
            var2 = this.generateICode(var4, var2);
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            var22 = (String)var1.getProp(19);
            if (var22 != null) {
               if (var22.equals("__proto__")) {
                  var2 = this.addByte((byte)83, var2);
               } else if (var22.equals("__parent__")) {
                  var2 = this.addByte((byte)84, var2);
               } else {
                  this.badTree(var1);
               }
            } else {
               var4 = var4.getNextSibling();
               var2 = this.generateICode(var4, var2);
               var2 = this.addByte((byte)40, var2);
               this.itsStackDepth -= 2;
            }
            break;
         case 42:
            var2 = this.generateICode(var4, var2);
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)var3, var2);
            this.itsStackDepth -= 2;
            break;
         case 44:
         case 46:
         case 61:
         case 71:
            var2 = this.addByte((byte)var3, var2);
            var2 = this.addString(var1.getString(), var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 45:
            double var26 = var1.getDouble();
            if (var26 == 0.0) {
               var2 = this.addByte((byte)47, var2);
            } else if (var26 == 1.0) {
               var2 = this.addByte((byte)48, var2);
            } else {
               var2 = this.addByte((byte)45, var2);
               var2 = this.addNumber(var26, var2);
            }

            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 56:
            var6 = (Node)var1.getProp(12);
            var7 = (Integer)var6.getProp(12);
            var2 = this.addByte((byte)56, var2);
            var2 = this.addByte((byte)(var7 >> 8), var2);
            var2 = this.addByte((byte)(var7 & 255), var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 62:
            var2 = this.updateLineNumber(var1, var2);
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)62, var2);
            --this.itsStackDepth;
            break;
         case 68:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)var3, var2);
            break;
         case 69:
         case 144:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)69, var2);
            var2 = this.addLocalRef(var1, var2);
            break;
         case 70:
            var2 = this.addByte((byte)70, var2);
            var6 = (Node)var1.getProp(6);
            var2 = this.addLocalRef(var6, var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 72:
            var22 = var1.getString();
            if (this.itsData.itsNeedsActivation) {
               var2 = this.addByte((byte)85, var2);
               var2 = this.addByte((byte)46, var2);
               var2 = this.addString(var22, var2);
               this.itsStackDepth += 2;
               if (this.itsStackDepth > this.itsData.itsMaxStack) {
                  this.itsData.itsMaxStack = this.itsStackDepth;
               }

               var2 = this.addByte((byte)39, var2);
               --this.itsStackDepth;
            } else {
               var7 = this.itsVariableTable.getOrdinal(var22);
               var2 = this.addByte((byte)72, var2);
               var2 = this.addByte((byte)var7, var2);
               ++this.itsStackDepth;
               if (this.itsStackDepth > this.itsData.itsMaxStack) {
                  this.itsData.itsMaxStack = this.itsStackDepth;
               }
            }
            break;
         case 73:
            if (this.itsData.itsNeedsActivation) {
               var4.setType(61);
               var1.setType(10);
               var2 = this.generateICode(var1, var2);
            } else {
               var22 = var4.getString();
               var4 = var4.getNextSibling();
               var2 = this.generateICode(var4, var2);
               var7 = this.itsVariableTable.getOrdinal(var22);
               var2 = this.addByte((byte)73, var2);
               var2 = this.addByte((byte)var7, var2);
            }
            break;
         case 75:
            ++this.itsTryDepth;
            if (this.itsTryDepth > this.itsData.itsMaxTryDepth) {
               this.itsData.itsMaxTryDepth = this.itsTryDepth;
            }

            var6 = (Node)var1.getProp(1);
            var19 = (Node)var1.getProp(21);
            if (var6 == null) {
               var2 = this.addByte((byte)75, var2);
               var2 = this.addByte((byte)0, var2);
               var2 = this.addByte((byte)0, var2);
            } else {
               var2 = this.addGoto(var1, 75, var2);
            }

            var8 = 0;
            if (var19 != null) {
               var8 = this.acquireLabel();
               int var20 = var8 & Integer.MAX_VALUE;
               super.itsLabelTable[var20].addFixup(var2);
            }

            var2 = this.addByte((byte)0, var2);
            var2 = this.addByte((byte)0, var2);
            var9 = null;

            for(boolean var21 = false; var4 != null; var4 = var4.getNextSibling()) {
               if (var6 != null && var9 == var6) {
                  this.itsStackDepth = 1;
                  if (this.itsStackDepth > this.itsData.itsMaxStack) {
                     this.itsData.itsMaxStack = this.itsStackDepth;
                  }
               }

               var11 = var4.getNextSibling();
               if (!var21 && var11 != null && (var11 == var6 || var11 == var19)) {
                  var2 = this.addByte((byte)76, var2);
                  var21 = true;
               }

               var2 = this.generateICode(var4, var2);
               var9 = var4;
            }

            this.itsStackDepth = 0;
            if (var19 != null) {
               int var24 = this.acquireLabel();
               var2 = this.addGoto(var24, 6, var2);
               this.markLabel(var8, var2);
               this.itsStackDepth = 1;
               if (this.itsStackDepth > this.itsData.itsMaxStack) {
                  this.itsData.itsMaxStack = this.itsStackDepth;
               }

               int var25 = this.itsData.itsMaxLocals++;
               var2 = this.addByte((byte)69, var2);
               var2 = this.addByte((byte)var25, var2);
               var2 = this.addByte((byte)57, var2);
               Integer var13 = (Integer)var19.getProp(20);
               var2 = this.addGoto(var13, 65, var2);
               var2 = this.addByte((byte)70, var2);
               var2 = this.addByte((byte)var25, var2);
               var2 = this.addByte((byte)88, var2);
               this.itsStackDepth = 0;
               this.markLabel(var24, var2);
            }

            --this.itsTryDepth;
            break;
         case 77:
            var2 = this.addByte((byte)var3, var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 79:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)79, var2);
            var2 = this.addLocalRef(var1, var2);
            --this.itsStackDepth;
            break;
         case 80:
            var2 = this.addByte((byte)80, var2);
            var6 = (Node)var1.getProp(4);
            var2 = this.addLocalRef(var6, var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 96:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)57, var2);
            --this.itsStackDepth;
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            break;
         case 100:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)9, var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }

            var15 = this.acquireLabel();
            var2 = this.addGoto(var15, 7, var2);
            var2 = this.addByte((byte)57, var2);
            --this.itsStackDepth;
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            this.markLabel(var15, var2);
            break;
         case 101:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)9, var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }

            var15 = this.acquireLabel();
            var2 = this.addGoto(var15, 8, var2);
            var2 = this.addByte((byte)57, var2);
            --this.itsStackDepth;
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            this.markLabel(var15, var2);
            break;
         case 102:
         case 103:
            var2 = this.generateICode(var4, var2);
            var4 = var4.getNextSibling();
            var2 = this.generateICode(var4, var2);
            var15 = var1.getInt();
            if (this.version == 120) {
               if (var15 == 14) {
                  var15 = 53;
               } else if (var15 == 15) {
                  var15 = 54;
               }
            }

            var2 = this.addByte((byte)var15, var2);
            --this.itsStackDepth;
            break;
         case 105:
            var2 = this.generateICode(var4, var2);
            switch (var1.getInt()) {
               case 23:
                  var2 = this.addByte((byte)58, var2);
                  return var2;
               case 24:
                  var2 = this.addByte((byte)29, var2);
                  return var2;
               case 28:
                  var2 = this.addByte((byte)28, var2);
                  return var2;
               case 32:
                  var2 = this.addByte((byte)32, var2);
                  return var2;
               case 129:
                  var15 = this.acquireLabel();
                  var7 = this.acquireLabel();
                  var2 = this.addGoto(var15, 7, var2);
                  var2 = this.addByte((byte)52, var2);
                  var2 = this.addGoto(var7, 6, var2);
                  this.markLabel(var15, var2);
                  var2 = this.addByte((byte)51, var2);
                  this.markLabel(var7, var2);
                  return var2;
               case 132:
                  var2 = this.addByte((byte)57, var2);
                  var2 = this.addByte((byte)74, var2);
                  return var2;
               default:
                  this.badTree(var1);
                  return var2;
            }
         case 106:
         case 107:
            var15 = var4.getType();
            switch (var15) {
               case 39:
               case 41:
                  var19 = var4.getFirstChild();
                  var2 = this.generateICode(var19, var2);
                  var19 = var19.getNextSibling();
                  var2 = this.generateICode(var19, var2);
                  if (var15 == 39) {
                     var2 = this.addByte((byte)(var3 == 106 ? 34 : 37), var2);
                  } else {
                     var2 = this.addByte((byte)(var3 == 106 ? 35 : 38), var2);
                  }

                  --this.itsStackDepth;
                  return var2;
               case 72:
                  String var18 = var4.getString();
                  if (this.itsData.itsNeedsActivation) {
                     var2 = this.addByte((byte)85, var2);
                     var2 = this.addByte((byte)46, var2);
                     var2 = this.addString(var18, var2);
                     this.itsStackDepth += 2;
                     if (this.itsStackDepth > this.itsData.itsMaxStack) {
                        this.itsData.itsMaxStack = this.itsStackDepth;
                     }

                     var2 = this.addByte((byte)(var3 == 106 ? 34 : 37), var2);
                     --this.itsStackDepth;
                  } else {
                     var2 = this.addByte((byte)(var3 == 106 ? 59 : 60), var2);
                     var8 = this.itsVariableTable.getOrdinal(var18);
                     var2 = this.addByte((byte)var8, var2);
                     ++this.itsStackDepth;
                     if (this.itsStackDepth > this.itsData.itsMaxStack) {
                        this.itsData.itsMaxStack = this.itsStackDepth;
                        return var2;
                     }
                  }

                  return var2;
               default:
                  var2 = this.addByte((byte)(var3 == 106 ? 33 : 36), var2);
                  var2 = this.addString(var4.getString(), var2);
                  ++this.itsStackDepth;
                  if (this.itsStackDepth > this.itsData.itsMaxStack) {
                     this.itsData.itsMaxStack = this.itsStackDepth;
                  }

                  return var2;
            }
         case 109:
            var2 = this.addByte((byte)var1.getInt(), var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 110:
            var2 = this.addByte((byte)55, var2);
            var6 = (Node)var1.getProp(5);
            Short var17 = (Short)var6.getProp(5);
            var2 = this.addByte((byte)(var17 >> 8), var2);
            var2 = this.addByte((byte)(var17 & 255), var2);
            ++this.itsStackDepth;
            if (this.itsStackDepth > this.itsData.itsMaxStack) {
               this.itsData.itsMaxStack = this.itsStackDepth;
            }
            break;
         case 115:
            var2 = this.updateLineNumber(var1, var2);
            var2 = this.generateICode(var4, var2);
            var15 = this.itsData.itsMaxLocals++;
            var2 = this.addByte((byte)69, var2);
            var2 = this.addByte((byte)var15, var2);
            var2 = this.addByte((byte)57, var2);
            --this.itsStackDepth;
            Vector var16 = (Vector)var1.getProp(13);

            Node var10;
            for(var8 = 0; var8 < var16.size(); ++var8) {
               var9 = (Node)var16.elementAt(var8);
               var10 = var9.getFirstChild();
               var2 = this.generateICode(var10, var2);
               var2 = this.addByte((byte)70, var2);
               ++this.itsStackDepth;
               if (this.itsStackDepth > this.itsData.itsMaxStack) {
                  this.itsData.itsMaxStack = this.itsStackDepth;
               }

               var2 = this.addByte((byte)var15, var2);
               var2 = this.addByte((byte)53, var2);
               var11 = new Node(137);
               var9.addChildAfter(var11, var10);
               Node var12 = new Node(7);
               var12.putProp(1, var11);
               var2 = this.addGoto(var12, 7, var2);
               --this.itsStackDepth;
            }

            var9 = (Node)var1.getProp(14);
            if (var9 != null) {
               var10 = new Node(137);
               var9.getFirstChild().addChildToFront(var10);
               var11 = new Node(6);
               var11.putProp(1, var10);
               var2 = this.addGoto(var11, 6, var2);
            }

            var10 = (Node)var1.getProp(2);
            var11 = new Node(6);
            var11.putProp(1, var10);
            var2 = this.addGoto(var11, 6, var2);
            break;
         case 116:
            var2 = this.updateLineNumber(var1, var2);

            for(var4 = var4.getNextSibling(); var4 != null; var4 = var4.getNextSibling()) {
               var2 = this.generateICode(var4, var2);
            }

            return var2;
         case 117:
         case 124:
         case 128:
         case 132:
         case 133:
         case 136:
         case 138:
            for(var2 = this.updateLineNumber(var1, var2); var4 != null; var4 = var4.getNextSibling()) {
               var2 = this.generateICode(var4, var2);
            }

            return var2;
         case 137:
            var14 = var1.getProp(20);
            if (var14 == null) {
               var7 = this.markLabel(this.acquireLabel(), var2);
               var1.putProp(20, new Integer(var7));
            } else {
               var7 = (Integer)var14;
               this.markLabel(var7, var2);
            }

            if (var1.getProp(21) != null) {
               this.itsStackDepth = 1;
               if (this.itsStackDepth > this.itsData.itsMaxStack) {
                  this.itsData.itsMaxStack = this.itsStackDepth;
               }
            }
         case 139:
            break;
         case 141:
            var2 = this.generateICode(var4, var2);
            var2 = this.addByte((byte)82, var2);
            break;
         case 142:
            var2 = this.generateICode(var4, var2);
            var14 = var1.getProp(18);
            if (var14 == ScriptRuntime.NumberClass) {
               var2 = this.addByte((byte)58, var2);
            } else {
               this.badTree(var1);
            }
            break;
         case 143:
            var6 = (Node)var1.getProp(1);
            var6.putProp(21, var1);
            var2 = this.addGoto(var1, 65, var2);
            break;
         case 145:
            if (var1.getProp(1) != null) {
               var2 = this.addByte((byte)66, var2);
            } else {
               var2 = this.addByte((byte)70, var2);
               ++this.itsStackDepth;
               if (this.itsStackDepth > this.itsData.itsMaxStack) {
                  this.itsData.itsMaxStack = this.itsStackDepth;
               }
            }

            var6 = (Node)var1.getProp(7);
            var2 = this.addLocalRef(var6, var2);
            break;
         case 146:
            for(var2 = this.updateLineNumber(var1, var2); var4 != null; var4 = var4.getNextSibling()) {
               if (var4.getType() != 110) {
                  var2 = this.generateICode(var4, var2);
               }
            }
      }

      return var2;
   }

   private void generateICodeFromTree(Node var1, VariableTable var2, boolean var3, Object var4) {
      int var5 = 0;
      this.itsVariableTable = var2;
      this.itsData.itsNeedsActivation = var3;
      var5 = this.generateICode(var1, var5);
      this.itsData.itsICodeTop = var5;
      if (this.itsEpilogLabel != -1) {
         this.markLabel(this.itsEpilogLabel, var5);
      }

      for(int var6 = 0; var6 < super.itsLabelTableTop; ++var6) {
         super.itsLabelTable[var6].fixGotos(this.itsData.itsICode);
      }

   }

   private void generateNestedFunctions(Scriptable var1, Context var2, Object var3) {
      this.itsNestedFunctions = new InterpretedFunction[this.itsFunctionList.size()];

      for(short var4 = 0; var4 < this.itsFunctionList.size(); ++var4) {
         FunctionNode var5 = (FunctionNode)this.itsFunctionList.elementAt(var4);
         Interpreter var6 = new Interpreter();
         var6.itsSourceFile = this.itsSourceFile;
         var6.itsData = new InterpreterData(0, 0, 0, var3, var2.hasCompileFunctionsWithDynamicScope(), var5.getCheckThis());
         var6.itsData.itsFunctionType = var5.getFunctionType();
         var6.itsInFunctionFlag = true;
         var6.debugSource = this.debugSource;
         this.itsNestedFunctions[var4] = var6.generateFunctionICode(var2, var1, var5, var3);
         var5.putProp(5, new Short(var4));
      }

   }

   private Object[] generateRegExpLiterals(Context var1, Scriptable var2, Vector var3) {
      Object[] var4 = new Object[var3.size()];
      RegExpProxy var5 = var1.getRegExpProxy();

      for(int var6 = 0; var6 < var3.size(); ++var6) {
         Node var7 = (Node)var3.elementAt(var6);
         Node var8 = var7.getFirstChild();
         Node var9 = var7.getLastChild();
         var4[var6] = var5.newRegExp(var1, var2, var8.getString(), var8 != var9 ? var9.getString() : null, false);
         var7.putProp(12, new Integer(var6));
      }

      return var4;
   }

   private InterpretedScript generateScriptICode(Context var1, Scriptable var2, Node var3, Object var4) {
      this.itsSourceFile = (String)var3.getProp(16);
      this.itsData.itsSourceFile = this.itsSourceFile;
      this.itsFunctionList = (Vector)var3.getProp(5);
      this.debugSource = (StringBuffer)var3.getProp(31);
      if (this.itsFunctionList != null) {
         this.generateNestedFunctions(var2, var1, var4);
      }

      Object[] var5 = null;
      Vector var6 = (Vector)var3.getProp(12);
      if (var6 != null) {
         var5 = this.generateRegExpLiterals(var1, var2, var6);
      }

      VariableTable var7 = (VariableTable)var3.getProp(10);
      boolean var8 = var1.isGeneratingDebugChanged() && var1.isGeneratingDebug();
      this.generateICodeFromTree(var3, var7, var8, var4);
      this.itsData.itsNestedFunctions = this.itsNestedFunctions;
      this.itsData.itsRegExpLiterals = var5;
      String[] var9 = this.itsVariableTable.getAllNames();
      short var10 = (short)this.itsVariableTable.getParameterCount();
      InterpretedScript var11 = new InterpretedScript(var1, this.itsData, var9, var10);
      if (var1.debugger != null) {
         var1.debugger.handleCompilationDone(var1, var11, this.debugSource);
      }

      return var11;
   }

   private static double getNumber(double[] var0, byte[] var1, int var2) {
      int var3 = (var1[var2] << 8) + (var1[var2 + 1] & 255);
      return var0[var3];
   }

   private static String getString(String[] var0, byte[] var1, int var2) {
      int var3 = (var1[var2] << 8) + (var1[var2 + 1] & 255);
      return var0[var3];
   }

   private static int getTarget(byte[] var0, int var1) {
      int var2 = (var0[var1] << 8) + (var0[var1 + 1] & 255);
      return var1 - 1 + var2;
   }

   public static Object interpret(Context var0, Scriptable var1, Scriptable var2, Object[] var3, NativeFunction var4, InterpreterData var5) throws JavaScriptException {
      int var8 = var5.itsMaxStack;
      int var9 = var4.argNames == null ? 0 : var4.argNames.length;
      int var10 = var5.itsMaxLocals;
      int var11 = var5.itsMaxTryDepth;
      int var12 = var8;
      int var13 = var8 + var9;
      int var14 = var13 + var10;
      Object var15 = DBL_MRK;
      Object[] var16 = new Object[var14 + var11];
      double[] var17 = new double[var14];
      int var18 = -1;
      byte[] var19 = var5.itsICode;
      int var20 = 0;
      int var21 = var5.itsICodeTop;
      Scriptable var22 = Undefined.instance;
      int var6;
      if (var9 != 0) {
         int var23 = var4.argCount;
         if (var23 != 0) {
            if (var23 > var3.length) {
               var23 = var3.length;
            }

            for(var6 = 0; var6 != var23; ++var6) {
               var16[var12 + var6] = var3[var6];
            }
         }

         for(var6 = var23; var6 != var9; ++var6) {
            var16[var12 + var6] = var22;
         }
      }

      if (var5.itsNestedFunctions != null) {
         for(var6 = 0; var6 < var5.itsNestedFunctions.length; ++var6) {
            createFunctionObject(var5.itsNestedFunctions[var6], var1);
         }
      }

      String var31 = null;
      int[] var41 = null;
      int var42 = 0;
      InterpreterFrame var43 = null;
      if (var0.debugger != null) {
         var43 = new InterpreterFrame(var1, var5, var4);
         var0.pushFrame(var43);
      }

      if (var11 != 0) {
         var41 = new int[var11 * 2];
      }

      Object var44 = var0.interpreterSecurityDomain;
      var0.interpreterSecurityDomain = var5.securityDomain;
      Object var45 = var22;
      int var46 = var20;
      int var47 = var0.instructionThreshold;
      int var48 = var0.instructionCount;
      boolean var49 = true;

      while(var20 < var21) {
         try {
            Object var7;
            Object var24;
            Object var25;
            double var26;
            boolean var28;
            int var29;
            int var30;
            Object[] var32;
            int var33;
            double var36;
            int var38;
            double var39;
            Object var59;
            boolean var60;
            switch (var19[var20] & 255) {
               case 2:
                  var45 = var16[var18];
                  if (var45 == var15) {
                     var45 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  break;
               case 3:
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var1 = ScriptRuntime.enterWith(var7, var1);
                  break;
               case 4:
                  var1 = ScriptRuntime.leaveWith(var1);
                  break;
               case 5:
                  var45 = var16[var18];
                  if (var45 == var15) {
                     var45 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var20 = getTarget(var19, var20 + 1);
                  break;
               case 6:
                  if (var47 != 0) {
                     var48 += var20 + 3 - var46;
                     if (var48 > var47) {
                        var0.observeInstructionCount(var48);
                        var48 = 0;
                     }
                  }

                  var46 = var20 = getTarget(var19, var20 + 1);
                  continue;
               case 7:
                  var25 = var16[var18];
                  if (var25 != var15) {
                     var28 = ScriptRuntime.toBoolean(var25);
                  } else {
                     var26 = var17[var18];
                     var28 = var26 == var26 && var26 != 0.0;
                  }

                  --var18;
                  if (var28) {
                     if (var47 != 0) {
                        var48 += var20 + 3 - var46;
                        if (var48 > var47) {
                           var0.observeInstructionCount(var48);
                           var48 = 0;
                        }
                     }

                     var46 = var20 = getTarget(var19, var20 + 1);
                     continue;
                  }

                  var20 += 2;
                  break;
               case 8:
                  var25 = var16[var18];
                  if (var25 != var15) {
                     var28 = ScriptRuntime.toBoolean(var25) ^ true;
                  } else {
                     var26 = var17[var18];
                     var28 = (var26 == var26 && var26 != 0.0) ^ true;
                  }

                  --var18;
                  if (var28) {
                     if (var47 != 0) {
                        var48 += var20 + 3 - var46;
                        if (var48 > var47) {
                           var0.observeInstructionCount(var48);
                           var48 = 0;
                        }
                     }

                     var46 = var20 = getTarget(var19, var20 + 1);
                     continue;
                  }

                  var20 += 2;
                  break;
               case 9:
                  var16[var18 + 1] = var16[var18];
                  var17[var18 + 1] = var17[var18];
                  ++var18;
                  break;
               case 10:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.setName((Scriptable)var7, var24, var1, getString(var5.itsStringTable, var19, var20 + 1));
                  var20 += 2;
                  break;
               case 11:
                  var38 = stack_int32(var16, var17, var18);
                  --var18;
                  var33 = stack_int32(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = (double)(var33 | var38);
                  break;
               case 12:
                  var38 = stack_int32(var16, var17, var18);
                  --var18;
                  var33 = stack_int32(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = (double)(var33 ^ var38);
                  break;
               case 13:
                  var38 = stack_int32(var16, var17, var18);
                  --var18;
                  var33 = stack_int32(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = (double)(var33 & var38);
                  break;
               case 14:
                  --var18;
                  var28 = do_eq(var16, var17, var18);
                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 15:
                  --var18;
                  var28 = do_eq(var16, var17, var18) ^ true;
                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 16:
                  --var18;
                  var24 = var16[var18 + 1];
                  var7 = var16[var18];
                  if (var24 != var15 && var7 != var15) {
                     var28 = ScriptRuntime.cmp_LT(var7, var24) == 1;
                  } else {
                     var39 = stack_double(var16, var17, var18 + 1);
                     var36 = stack_double(var16, var17, var18);
                     var28 = var39 == var39 && var36 == var36 && var36 < var39;
                  }

                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 17:
                  --var18;
                  var24 = var16[var18 + 1];
                  var7 = var16[var18];
                  if (var24 != var15 && var7 != var15) {
                     var28 = ScriptRuntime.cmp_LE(var7, var24) == 1;
                  } else {
                     var39 = stack_double(var16, var17, var18 + 1);
                     var36 = stack_double(var16, var17, var18);
                     var28 = var39 == var39 && var36 == var36 && var36 <= var39;
                  }

                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 18:
                  --var18;
                  var24 = var16[var18 + 1];
                  var7 = var16[var18];
                  if (var24 != var15 && var7 != var15) {
                     var28 = ScriptRuntime.cmp_LT(var24, var7) == 1;
                  } else {
                     var39 = stack_double(var16, var17, var18 + 1);
                     var36 = stack_double(var16, var17, var18);
                     var28 = var39 == var39 && var36 == var36 && var39 < var36;
                  }

                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 19:
                  --var18;
                  var24 = var16[var18 + 1];
                  var7 = var16[var18];
                  if (var24 != var15 && var7 != var15) {
                     var28 = ScriptRuntime.cmp_LE(var24, var7) == 1;
                  } else {
                     var39 = stack_double(var16, var17, var18 + 1);
                     var36 = stack_double(var16, var17, var18);
                     var28 = var39 == var39 && var36 == var36 && var39 <= var36;
                  }

                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 20:
                  var38 = stack_int32(var16, var17, var18);
                  --var18;
                  var33 = stack_int32(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = (double)(var33 << var38);
                  break;
               case 21:
                  var38 = stack_int32(var16, var17, var18);
                  --var18;
                  var33 = stack_int32(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = (double)(var33 >> var38);
                  break;
               case 22:
                  var38 = stack_int32(var16, var17, var18) & 31;
                  --var18;
                  long var34 = stack_uint32(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = (double)(var34 >>> var38);
                  break;
               case 23:
                  --var18;
                  do_add(var16, var17, var18);
                  break;
               case 24:
                  var39 = stack_double(var16, var17, var18);
                  --var18;
                  var36 = stack_double(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = var36 - var39;
                  break;
               case 25:
                  var39 = stack_double(var16, var17, var18);
                  --var18;
                  var36 = stack_double(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = var36 * var39;
                  break;
               case 26:
                  var39 = stack_double(var16, var17, var18);
                  --var18;
                  var36 = stack_double(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = var36 / var39;
                  break;
               case 27:
                  var39 = stack_double(var16, var17, var18);
                  --var18;
                  var36 = stack_double(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = var36 % var39;
                  break;
               case 28:
                  var38 = stack_int32(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = (double)(~var38);
                  break;
               case 29:
                  var39 = stack_double(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = -var39;
                  break;
               case 30:
                  if (var47 != 0) {
                     var48 += 100;
                     var0.instructionCount = var48;
                     var60 = true;
                  }

                  var29 = var19[var20 + 3] << 8 | var19[var20 + 4] & 255;
                  var32 = new Object[var29];

                  for(var6 = var29 - 1; var6 >= 0; --var6) {
                     var25 = var16[var18];
                     if (var25 == var15) {
                        var25 = doubleWrap(var17[var18]);
                     }

                     var32[var6] = var25;
                     --var18;
                  }

                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  if (var7 == var22 && (var19[var20 + 1] << 8) + (var19[var20 + 2] & 255) != -1) {
                     var7 = getString(var5.itsStringTable, var19, var20 + 1);
                  }

                  var16[var18] = ScriptRuntime.newObject(var0, var7, var32, var1);
                  var20 += 4;
                  var48 = var0.instructionCount;
                  break;
               case 31:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.delete(var7, var24);
                  break;
               case 32:
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.typeof(var7);
                  break;
               case 33:
                  ++var18;
                  var16[var18] = ScriptRuntime.postIncrement(var1, getString(var5.itsStringTable, var19, var20 + 1));
                  var20 += 2;
                  break;
               case 34:
                  var31 = (String)var16[var18];
                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.postIncrement(var7, var31, var1);
                  break;
               case 35:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.postIncrementElem(var7, var24, var1);
                  break;
               case 36:
                  ++var18;
                  var16[var18] = ScriptRuntime.postDecrement(var1, getString(var5.itsStringTable, var19, var20 + 1));
                  var20 += 2;
                  break;
               case 37:
                  var31 = (String)var16[var18];
                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.postDecrement(var7, var31, var1);
                  break;
               case 38:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.postDecrementElem(var7, var24, var1);
                  break;
               case 39:
                  var31 = (String)var16[var18];
                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.getProp(var7, var31, var1);
                  break;
               case 40:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var31 = (String)var16[var18];
                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.setProp(var7, var31, var24, var1);
                  break;
               case 41:
                  var59 = var16[var18];
                  if (var59 == var15) {
                     var59 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.getElem(var7, var59, var1);
                  break;
               case 42:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var59 = var16[var18];
                  if (var59 == var15) {
                     var59 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.setElem(var7, var59, var24, var1);
                  break;
               case 43:
                  if (var47 != 0) {
                     var48 += 100;
                     var0.instructionCount = var48;
                     var48 = -1;
                  }

                  var0.instructionCount = var48;
                  var29 = var19[var20 + 3] << 8 | var19[var20 + 4] & 255;
                  var32 = new Object[var29];

                  for(var6 = var29 - 1; var6 >= 0; --var6) {
                     var25 = var16[var18];
                     if (var25 == var15) {
                        var25 = doubleWrap(var17[var18]);
                     }

                     var32[var6] = var25;
                     --var18;
                  }

                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  if (var7 == var22) {
                     var7 = getString(var5.itsStringTable, var19, var20 + 1);
                  }

                  Scriptable var61 = var1;
                  if (var5.itsNeedsActivation) {
                     var61 = ScriptableObject.getTopLevelScope(var1);
                  }

                  var16[var18] = ScriptRuntime.call(var0, var7, var24, var32, var61);
                  var20 += 4;
                  var48 = var0.instructionCount;
                  break;
               case 44:
                  ++var18;
                  var16[var18] = ScriptRuntime.name(var1, getString(var5.itsStringTable, var19, var20 + 1));
                  var20 += 2;
                  break;
               case 45:
                  ++var18;
                  var16[var18] = var15;
                  var17[var18] = getNumber(var5.itsNumberTable, var19, var20 + 1);
                  var20 += 2;
                  break;
               case 46:
                  ++var18;
                  var16[var18] = getString(var5.itsStringTable, var19, var20 + 1);
                  var20 += 2;
                  break;
               case 47:
                  ++var18;
                  var16[var18] = var15;
                  var17[var18] = 0.0;
                  break;
               case 48:
                  ++var18;
                  var16[var18] = var15;
                  var17[var18] = 1.0;
                  break;
               case 49:
                  ++var18;
                  var16[var18] = null;
                  break;
               case 50:
                  ++var18;
                  var16[var18] = var2;
                  break;
               case 51:
                  ++var18;
                  var16[var18] = Boolean.FALSE;
                  break;
               case 52:
                  ++var18;
                  var16[var18] = Boolean.TRUE;
                  break;
               case 53:
                  --var18;
                  var28 = do_sheq(var16, var17, var18);
                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 54:
                  --var18;
                  var28 = do_sheq(var16, var17, var18) ^ true;
                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 55:
                  var6 = var19[var20 + 1] << 8 | var19[var20 + 2] & 255;
                  ++var18;
                  var16[var18] = new InterpretedFunction(var5.itsNestedFunctions[var6], var1, var0);
                  createFunctionObject((InterpretedFunction)var16[var18], var1);
                  var20 += 2;
                  break;
               case 56:
                  var6 = var19[var20 + 1] << 8 | var19[var20 + 2] & 255;
                  ++var18;
                  var16[var18] = var5.itsRegExpLiterals[var6];
                  var20 += 2;
                  break;
               case 57:
                  --var18;
                  break;
               case 58:
                  var39 = stack_double(var16, var17, var18);
                  var16[var18] = var15;
                  var17[var18] = var39;
                  break;
               case 59:
                  ++var20;
                  var30 = var19[var20] & 255;
                  ++var18;
                  var16[var18] = var16[var12 + var30];
                  var17[var18] = var17[var12 + var30];
                  var16[var12 + var30] = var15;
                  var17[var12 + var30] = stack_double(var16, var17, var18) + 1.0;
                  break;
               case 60:
                  ++var20;
                  var30 = var19[var20] & 255;
                  ++var18;
                  var16[var18] = var16[var12 + var30];
                  var17[var18] = var17[var12 + var30];
                  var16[var12 + var30] = var15;
                  var17[var12 + var30] = stack_double(var16, var17, var18) - 1.0;
                  break;
               case 61:
                  ++var18;
                  var16[var18] = ScriptRuntime.bind(var1, getString(var5.itsStringTable, var19, var20 + 1));
                  var20 += 2;
                  break;
               case 62:
                  var45 = var16[var18];
                  if (var45 == var15) {
                     var45 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  throw new JavaScriptException(var45);
               case 63:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var28 = ScriptRuntime.in(var7, var24, var1);
                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 64:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var28 = ScriptRuntime.instanceOf(var1, var7, var24);
                  var16[var18] = var28 ? Boolean.TRUE : Boolean.FALSE;
                  break;
               case 65:
                  ++var18;
                  var17[var18] = (double)(var20 + 3);
                  if (var47 != 0) {
                     var48 += var20 + 3 - var46;
                     if (var48 > var47) {
                        var0.observeInstructionCount(var48);
                        var48 = 0;
                     }
                  }

                  var46 = var20 = getTarget(var19, var20 + 1);
                  continue;
               case 66:
                  var30 = var19[var20 + 1] & 255;
                  if (var47 != 0) {
                     var48 += var20 + 2 - var46;
                     if (var48 > var47) {
                        var0.observeInstructionCount(var48);
                        var48 = 0;
                     }
                  }

                  var46 = var20 = (int)var17[var13 + var30];
                  continue;
               case 67:
                  if (var47 != 0) {
                     var48 += 100;
                     var0.instructionCount = var48;
                     var60 = true;
                  }

                  int var50 = var19[var20 + 1] << 8 | var19[var20 + 2] & 255;
                  var31 = getString(var5.itsStringTable, var19, var20 + 3);
                  var29 = var19[var20 + 5] << 8 | var19[var20 + 6] & 255;
                  var32 = new Object[var29];

                  for(var6 = var29 - 1; var6 >= 0; --var6) {
                     var25 = var16[var18];
                     if (var25 == var15) {
                        var25 = doubleWrap(var17[var18]);
                     }

                     var32[var6] = var25;
                     --var18;
                  }

                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.callSpecial(var0, var7, var24, var32, var2, var1, var31, var50);
                  var20 += 6;
                  var48 = var0.instructionCount;
                  break;
               case 68:
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.getThis((Scriptable)var7);
                  break;
               case 69:
                  ++var20;
                  var30 = var19[var20] & 255;
                  var16[var13 + var30] = var16[var18];
                  var17[var13 + var30] = var17[var18];
                  break;
               case 70:
                  ++var20;
                  var30 = var19[var20] & 255;
                  ++var18;
                  var16[var18] = var16[var13 + var30];
                  var17[var18] = var17[var13 + var30];
                  break;
               case 71:
                  ++var18;
                  var16[var18] = ScriptRuntime.getBase(var1, getString(var5.itsStringTable, var19, var20 + 1));
                  var20 += 2;
                  break;
               case 72:
                  ++var20;
                  var30 = var19[var20] & 255;
                  ++var18;
                  var16[var18] = var16[var12 + var30];
                  var17[var18] = var17[var12 + var30];
                  break;
               case 73:
                  ++var20;
                  var30 = var19[var20] & 255;
                  var16[var12 + var30] = var16[var18];
                  var17[var12 + var30] = var17[var18];
                  break;
               case 74:
                  ++var18;
                  var16[var18] = Undefined.instance;
                  break;
               case 75:
                  var6 = getTarget(var19, var20 + 1);
                  if (var6 == var20) {
                     var6 = 0;
                  }

                  var41[var42 * 2] = var6;
                  var6 = getTarget(var19, var20 + 3);
                  if (var6 == var20 + 2) {
                     var6 = 0;
                  }

                  var41[var42 * 2 + 1] = var6;
                  var16[var14 + var42] = var1;
                  ++var42;
                  var20 += 4;
                  break;
               case 76:
                  --var42;
                  break;
               case 77:
                  ++var18;
                  var16[var18] = ScriptRuntime.newScope();
                  break;
               case 78:
                  var31 = getString(var5.itsStringTable, var19, var20 + 1);
                  ++var18;
                  var16[var18] = ScriptRuntime.typeofName(var1, var31);
                  var20 += 2;
                  break;
               case 79:
                  ++var20;
                  var30 = var19[var20] & 255;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var16[var13 + var30] = ScriptRuntime.initEnum(var7, var1);
                  break;
               case 80:
                  ++var20;
                  var30 = var19[var20] & 255;
                  var25 = var16[var13 + var30];
                  ++var18;
                  var16[var18] = ScriptRuntime.nextEnum((Enumeration)var25);
                  break;
               case 81:
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.getProto(var7, var1);
                  break;
               case 82:
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.getParent(var7);
                  break;
               case 83:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.setProto(var7, var24, var1);
                  break;
               case 84:
                  var24 = var16[var18];
                  if (var24 == var15) {
                     var24 = doubleWrap(var17[var18]);
                  }

                  --var18;
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.setParent(var7, var24, var1);
                  break;
               case 85:
                  ++var18;
                  var16[var18] = var1;
                  break;
               case 86:
                  var7 = var16[var18];
                  if (var7 == var15) {
                     var7 = doubleWrap(var17[var18]);
                  }

                  var16[var18] = ScriptRuntime.getParent(var7, var1);
                  break;
               case 87:
                  ++var18;
                  var16[var18] = var4;
                  break;
               case 88:
                  var45 = var16[var18];
                  --var18;
                  if (var45 instanceof JavaScriptException) {
                     throw (JavaScriptException)var45;
                  }

                  throw (RuntimeException)var45;
               case 89:
               case 90:
               case 91:
               case 92:
               case 93:
               case 94:
               case 95:
               case 96:
               case 97:
               case 98:
               case 99:
               case 100:
               case 101:
               case 102:
               case 103:
               case 104:
               case 105:
               case 106:
               case 107:
               case 108:
               case 109:
               case 110:
               case 111:
               case 112:
               case 113:
               case 114:
               case 115:
               case 116:
               case 117:
               case 118:
               case 119:
               case 120:
               case 121:
               case 122:
               case 123:
               case 124:
               case 125:
               case 126:
               case 127:
               case 128:
               case 129:
               case 130:
               case 131:
               case 132:
               case 133:
               case 134:
               case 135:
               case 136:
               case 137:
               case 138:
               case 139:
               case 140:
               case 141:
               case 142:
               case 143:
               case 144:
               case 145:
               case 146:
               default:
                  dumpICode(var5);
                  throw new RuntimeException("Unknown icode : " + (var19[var20] & 255) + " @ pc : " + var20);
               case 147:
               case 149:
                  var6 = var19[var20 + 1] << 8 | var19[var20 + 2] & 255;
                  var0.interpreterLine = var6;
                  if (var43 != null) {
                     var43.setLineNumber(var6);
                  }

                  if ((var19[var20] & 255) == 149 || var0.inLineStepMode) {
                     var0.getDebuggableEngine().getDebugger().handleBreakpointHit(var0);
                  }

                  var20 += 2;
                  break;
               case 148:
                  var0.interpreterSourceFile = var5.itsSourceFile;
            }

            ++var20;
         } catch (Throwable var58) {
            var0.interpreterSecurityDomain = null;
            if (var47 != 0) {
               if (var48 < 0) {
                  var48 = var0.instructionCount;
               } else {
                  var48 += var20 - var46;
                  var0.instructionCount = var48;
               }
            }

            boolean var51 = false;
            boolean var52 = true;
            boolean var53 = true;
            boolean var54 = true;
            byte var55;
            Object var56;
            if (var58 instanceof JavaScriptException) {
               var56 = ScriptRuntime.unwrapJavaScriptException((JavaScriptException)var58);
               var55 = 0;
            } else if (var58 instanceof EcmaError) {
               var56 = ((EcmaError)var58).getErrorObject();
               var55 = 1;
            } else if (var58 instanceof RuntimeException) {
               var56 = var58;
               var55 = 2;
            } else {
               var56 = var58;
               var55 = 3;
            }

            if (var55 != 3 && var0.debugger != null) {
               var0.debugger.handleExceptionThrown(var0, var56);
            }

            boolean var57 = true;
            if (var55 != 3 && var42 > 0) {
               --var42;
               if (var55 == 0 || var55 == 1) {
                  var20 = var41[var42 * 2];
                  if (var20 != 0) {
                     var57 = false;
                  }
               }

               if (var57) {
                  var20 = var41[var42 * 2 + 1];
                  if (var20 != 0) {
                     var57 = false;
                     var56 = var58;
                  }
               }
            }

            if (var57) {
               if (var43 != null) {
                  var0.popFrame();
               }

               if (var55 == 0) {
                  throw (JavaScriptException)var58;
               }

               if (var55 != 1 && var55 != 2) {
                  throw (Error)var58;
               }

               throw (RuntimeException)var58;
            }

            if (var47 != 0 && var48 > var47) {
               var0.observeInstructionCount(var48);
               var48 = 0;
            }

            var46 = var20;
            var1 = (Scriptable)var16[var14 + var42];
            var18 = 0;
            var16[0] = var56;
            var0.interpreterSecurityDomain = var5.securityDomain;
         }
      }

      var0.interpreterSecurityDomain = var44;
      if (var43 != null) {
         var0.popFrame();
      }

      if (var47 != 0) {
         if (var48 > var47) {
            var0.observeInstructionCount(var48);
            var48 = 0;
         }

         var0.instructionCount = var48;
      }

      return var45;
   }

   private static double stack_double(Object[] var0, double[] var1, int var2) {
      Object var3 = var0[var2];
      return var3 != DBL_MRK ? ScriptRuntime.toNumber(var3) : var1[var2];
   }

   private static int stack_int32(Object[] var0, double[] var1, int var2) {
      Object var3 = var0[var2];
      return var3 != DBL_MRK ? ScriptRuntime.toInt32(var3) : ScriptRuntime.toInt32(var1[var2]);
   }

   private static long stack_uint32(Object[] var0, double[] var1, int var2) {
      Object var3 = var0[var2];
      return var3 != DBL_MRK ? ScriptRuntime.toUint32(var3) : ScriptRuntime.toUint32(var1[var2]);
   }

   public Node transform(Node var1, TokenStream var2, Scriptable var3) {
      return (new NodeTransformer()).transform(var1, (Node)null, var2, var3);
   }

   private int updateLineNumber(Node var1, int var2) {
      Object var3 = var1.getDatum();
      if (var3 != null && var3 instanceof Number) {
         short var4 = ((Number)var3).shortValue();
         if (var4 != this.itsLineNumber) {
            this.itsLineNumber = var4;
            if (this.itsData.itsLineNumberTable == null && Context.getCurrentContext().isGeneratingDebug()) {
               this.itsData.itsLineNumberTable = new UintMap();
            }

            if (this.itsData.itsLineNumberTable != null) {
               this.itsData.itsLineNumberTable.put(var4, var2);
            }

            var2 = this.addByte((byte)-109, var2);
            var2 = this.addByte((byte)(var4 >> 8), var2);
            var2 = this.addByte((byte)(var4 & 255), var2);
         }

         return var2;
      } else {
         return var2;
      }
   }
}
