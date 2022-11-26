package org.mozilla.javascript.optimizer;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.mozilla.classfile.ClassFileWriter;
import org.mozilla.classfile.DefiningClassLoader;
import org.mozilla.javascript.ClassNameHelper;
import org.mozilla.javascript.ClassOutput;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.FunctionNode;
import org.mozilla.javascript.IRFactory;
import org.mozilla.javascript.Interpreter;
import org.mozilla.javascript.JavaAdapter;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.NativeScript;
import org.mozilla.javascript.Node;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.SecuritySupport;
import org.mozilla.javascript.ShallowNodeIterator;
import org.mozilla.javascript.TokenStream;
import org.mozilla.javascript.VariableTable;
import org.mozilla.javascript.WrappedException;

public class Codegen extends Interpreter {
   private final int JAVASCRIPTEXCEPTION = 0;
   private final int WRAPPEDEXCEPTION = 1;
   private static final String normalFunctionSuperClassName = "org.mozilla.javascript.NativeFunction";
   private static final String normalScriptSuperClassName = "org.mozilla.javascript.NativeScript";
   private static final String debugFunctionSuperClassName = "org.mozilla.javascript.debug.NativeFunctionDebug";
   private static final String debugScriptSuperClassName = "org.mozilla.javascript.debug.NativeScriptDebug";
   private String superClassName;
   private String superClassSlashName;
   private String name;
   private int ordinal;
   boolean inFunction;
   boolean inDirectCallFunction;
   private ClassFileWriter classFile;
   private Vector namesVector;
   private Vector classFilesVector;
   private short scriptRuntimeIndex;
   private int version;
   private OptClassNameHelper itsNameHelper;
   private String itsSourceFile;
   private int itsLineNumber;
   private int stackDepth;
   private int stackDepthMax;
   private static final int MAX_LOCALS = 256;
   private boolean[] locals;
   private short firstFreeLocal;
   private short localsMax;
   private ConstantList itsConstantList = new ConstantList();
   private short variableObjectLocal;
   private short scriptResultLocal;
   private short contextLocal;
   private short argsLocal;
   private short thisObjLocal;
   private short funObjLocal;
   private short debug_pcLocal;
   private short debugStopSubRetLocal;
   private short itsZeroArgArray;
   private short itsOneArgArray;
   private boolean hasVarsInRegs;
   private boolean itsForcedObjectParameters;
   private boolean trivialInit;
   private short itsLocalAllocationBase;
   private OptVariableTable vars;
   private OptVariableTable debugVars;
   private int epilogueLabel;
   private int optLevel;
   // $FF: synthetic field
   static Class class$java$lang$Object;

   public int acquireLabel() {
      return this.classFile.acquireLabel();
   }

   void addByteCode(byte var1) {
      this.classFile.add(var1);
   }

   void addByteCode(byte var1, int var2) {
      this.classFile.add(var1, var2);
   }

   void addByteCode(byte var1, String var2) {
      this.classFile.add(var1, var2);
   }

   void addDoubleConstructor() {
      this.classFile.add((byte)-73, "java/lang/Double", "<init>", "(D)", "V");
   }

   void addOptRuntimeInvoke(String var1, String var2, String var3) {
      this.classFile.add((byte)-72, "org/mozilla/javascript/optimizer/OptRuntime", var1, var2, var3);
   }

   void addScriptRuntimeInvoke(String var1, String var2, String var3) {
      this.classFile.add((byte)-72, "org/mozilla/javascript/ScriptRuntime", var1, var2, var3);
   }

   void addSpecialInvoke(String var1, String var2, String var3, String var4) {
      this.classFile.add((byte)-73, var1, var2, var3, var4);
   }

   void addStaticInvoke(String var1, String var2, String var3, String var4) {
      this.classFile.add((byte)-72, var1, var2, var3, var4);
   }

   void addVirtualInvoke(String var1, String var2, String var3, String var4) {
      this.classFile.add((byte)-74, var1, var2, var3, var4);
   }

   private void aload(short var1) {
      this.xop((byte)42, (byte)25, var1);
   }

   private void astore(short var1) {
      this.xop((byte)75, (byte)58, var1);
   }

   private void badTree() {
      throw new RuntimeException("Bad tree in codegen");
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public Object compile(Context var1, Scriptable var2, Node var3, Object var4, SecuritySupport var5, ClassNameHelper var6) throws IOException {
      Vector var7 = new Vector();
      Vector var8 = new Vector();
      String var9 = null;
      Object var10 = null;
      Class var11 = null;
      DefiningClassLoader var12 = new DefiningClassLoader();

      String var14;
      try {
         if (var1.getOptimizationLevel() > 0) {
            (new Optimizer()).optimize(var3, var1.getOptimizationLevel());
         }

         var9 = this.generateCode(var3, var8, var7, var6);

         for(int var13 = 0; var13 < var8.size(); ++var13) {
            var14 = (String)var8.elementAt(var13);
            byte[] var15 = (byte[])var7.elementAt(var13);
            if (var6.getGeneratingDirectory() != null) {
               try {
                  int var16 = var14.lastIndexOf(46);
                  if (var16 != -1) {
                     var14 = var14.substring(var16 + 1);
                  }

                  String var17 = var6.getTargetClassFileName(var14);
                  FileOutputStream var18 = new FileOutputStream(var17);
                  var18.write(var15);
                  var18.close();
               } catch (IOException var26) {
                  throw WrappedException.wrapException(var26);
               }
            } else {
               boolean var32 = var14.equals(var9);
               ClassOutput var34 = var6.getClassOutput();
               if (var34 != null) {
                  try {
                     OutputStream var37 = var34.getOutputStream(var14, var32);
                     var37.write(var15);
                     var37.close();
                  } catch (IOException var25) {
                     throw WrappedException.wrapException(var25);
                  }
               }

               try {
                  Class var38 = null;
                  if (var5 != null) {
                     var38 = var5.defineClass(var14, var15, var4);
                  }

                  if (var38 == null) {
                     Context.checkSecurityDomainRequired();
                     var38 = var12.defineClass(var14, var15);
                     ClassLoader var19 = var38.getClassLoader();
                     var38 = var19.loadClass(var14);
                  }

                  if (var32) {
                     var11 = var38;
                  }
               } catch (ClassFormatError var23) {
                  throw new RuntimeException(var23.toString());
               } catch (ClassNotFoundException var24) {
                  throw new RuntimeException(var24.toString());
               }
            }
         }
      } catch (SecurityException var27) {
         var10 = var27;
      } catch (IllegalArgumentException var28) {
         var10 = var28;
      }

      if (var10 != null) {
         throw new RuntimeException("Malformed optimizer package " + var10);
      } else {
         OptClassNameHelper var29 = (OptClassNameHelper)var6;
         if (var29.getTargetImplements() != null || var29.getTargetExtends() != null) {
            var14 = var29.getJavaScriptClassName((String)null, true);
            NativeObject var30 = new NativeObject();
            ShallowNodeIterator var33 = var3.getChildIterator();

            while(var33.hasMoreElements()) {
               Node var35 = var33.nextNode();
               if (var35.getType() == 110) {
                  var30.put((String)var35.getDatum(), var30, var35.getProp(5));
               }
            }

            try {
               Class var36 = var29.getTargetExtends();
               if (var36 == null) {
                  var36 = class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object"));
               }

               JavaAdapter.createAdapterClass(var1, var30, var14, var36, var29.getTargetImplements(), var9, var29);
            } catch (ClassNotFoundException var22) {
               throw new Error(var22.toString());
            }
         }

         if (var3 instanceof OptFunctionNode) {
            return ScriptRuntime.createFunctionObject(var2, var11, var1, true);
         } else {
            try {
               if (var11 == null) {
                  return null;
               }

               NativeScript var31 = (NativeScript)var11.newInstance();
               if (var2 != null) {
                  var31.setPrototype(ScriptableObject.getClassPrototype(var2, "Script"));
                  var31.setParentScope(var2);
               }

               return var31;
            } catch (InstantiationException var20) {
            } catch (IllegalAccessException var21) {
            }

            throw new RuntimeException("Unable to instantiate compiled class");
         }
      }
   }

   private void constructArgArray(int var1) {
      if (var1 == 0) {
         if (this.itsZeroArgArray >= 0) {
            this.aload(this.itsZeroArgArray);
         } else {
            this.push(0L);
            this.addByteCode((byte)-67, "java/lang/Object");
         }
      } else if (var1 == 1) {
         if (this.itsOneArgArray >= 0) {
            this.aload(this.itsOneArgArray);
         } else {
            this.push(1L);
            this.addByteCode((byte)-67, "java/lang/Object");
         }
      } else {
         this.push((long)var1);
         this.addByteCode((byte)-67, "java/lang/Object");
      }

   }

   public IRFactory createIRFactory(TokenStream var1, ClassNameHelper var2, Scriptable var3) {
      return new OptIRFactory(var1, var2, var3);
   }

   private void dload(short var1) {
      this.xop((byte)38, (byte)24, var1);
   }

   private void dstore(short var1) {
      this.xop((byte)71, (byte)57, var1);
   }

   private void emitConstantDudeInitializers() {
      if (this.itsConstantList.itsTop != 0) {
         this.classFile.startMethod("<clinit>", "()V", (short)24);

         for(int var1 = 0; var1 < this.itsConstantList.itsTop; ++var1) {
            this.addByteCode((byte)-69, "java/lang/" + this.itsConstantList.itsList[var1].itsWrapperType);
            this.addByteCode((byte)89);
            if (this.itsConstantList.itsList[var1].itsIsInteger) {
               this.push(this.itsConstantList.itsList[var1].itsLValue);
            } else {
               this.push(this.itsConstantList.itsList[var1].itsDValue);
            }

            this.addSpecialInvoke("java/lang/" + this.itsConstantList.itsList[var1].itsWrapperType, "<init>", "(" + this.itsConstantList.itsList[var1].itsSignature + ")", "V");
            this.classFile.addField("jsK_" + var1, "Ljava/lang/" + this.itsConstantList.itsList[var1].itsWrapperType + ";", (short)8);
            this.classFile.add((byte)-77, ClassFileWriter.fullyQualifiedForm(this.name), "jsK_" + var1, "Ljava/lang/" + this.itsConstantList.itsList[var1].itsWrapperType + ";");
         }

         this.addByteCode((byte)-79);
         this.classFile.stopMethod((short)0, (VariableTable)null);
      }
   }

   public void emitDirectConstructor(OptFunctionNode var1) {
      byte var2 = 17;
      this.classFile.startMethod("constructDirect", var1.getDirectCallParameterSignature() + "Ljava/lang/Object;", var2);
      int var3 = var1.getVariableTable().getParameterCount();
      short var4 = (short)(4 + var3 * 3 + 1);
      this.addByteCode((byte)-69, "org/mozilla/javascript/NativeObject");
      this.addByteCode((byte)89);
      this.classFile.add((byte)-73, "org/mozilla/javascript/NativeObject", "<init>", "()", "V");
      this.astore(var4);
      this.aload(var4);
      this.aload((short)0);
      this.addVirtualInvoke("org/mozilla/javascript/NativeFunction", "getClassPrototype", "()", "Lorg/mozilla/javascript/Scriptable;");
      this.classFile.add((byte)-71, "org/mozilla/javascript/Scriptable", "setPrototype", "(Lorg/mozilla/javascript/Scriptable;)", "V");
      this.aload(var4);
      this.aload((short)0);
      this.addVirtualInvoke("org/mozilla/javascript/NativeFunction", "getParentScope", "()", "Lorg/mozilla/javascript/Scriptable;");
      this.classFile.add((byte)-71, "org/mozilla/javascript/Scriptable", "setPrototype", "(Lorg/mozilla/javascript/Scriptable;)", "V");
      this.aload((short)0);
      this.aload((short)1);
      this.aload((short)2);
      this.aload(var4);

      for(int var5 = 0; var5 < var3; ++var5) {
         this.aload((short)(4 + var5 * 3));
         this.dload((short)(5 + var5 * 3));
      }

      this.aload((short)(4 + var3 * 3));
      this.addVirtualInvoke(this.name, "callDirect", var1.getDirectCallParameterSignature(), "Ljava/lang/Object;");
      this.astore((short)(var4 + 1));
      int var6 = this.acquireLabel();
      this.aload((short)(var4 + 1));
      this.addByteCode((byte)-58, var6);
      this.aload((short)(var4 + 1));
      this.pushUndefined();
      this.addByteCode((byte)-91, var6);
      this.aload((short)(var4 + 1));
      this.addByteCode((byte)-63, "org/mozilla/javascript/Scriptable");
      this.addByteCode((byte)-103, var6);
      this.aload((short)(var4 + 1));
      this.addByteCode((byte)-64, "org/mozilla/javascript/Scriptable");
      this.addByteCode((byte)-80);
      this.markLabel(var6);
      this.aload(var4);
      this.addByteCode((byte)-80);
      this.classFile.stopMethod((short)(var4 + 2), (VariableTable)null);
   }

   private void finishMethod(Context var1, VariableTable var2) {
      this.classFile.stopMethod((short)(this.localsMax + 1), var2);
      this.contextLocal = -1;
   }

   private void genSimpleCompare(int var1, int var2, int var3) {
      switch (var1) {
         case 16:
            this.addByteCode((byte)-104);
            this.addByteCode((byte)-101, var2);
            break;
         case 17:
            this.addByteCode((byte)-104);
            this.addByteCode((byte)-98, var2);
            break;
         case 18:
            this.addByteCode((byte)-105);
            this.addByteCode((byte)-99, var2);
            break;
         case 19:
            this.addByteCode((byte)-105);
            this.addByteCode((byte)-100, var2);
      }

      if (var3 != -1) {
         this.addByteCode((byte)-89, var3);
      }

   }

   private void generateCatchBlock(int var1, short var2, int var3, int var4) {
      int var5 = this.classFile.markHandler(this.acquireLabel());
      short var6 = this.getNewWordLocal();
      this.astore(var6);
      this.aload(var2);
      this.astore(this.variableObjectLocal);
      this.aload(var6);
      this.releaseWordLocal(var6);
      if (var1 == 0) {
         this.addScriptRuntimeInvoke("unwrapJavaScriptException", "(Lorg/mozilla/javascript/JavaScriptException;)", "Ljava/lang/Object;");
      } else {
         this.addScriptRuntimeInvoke("unwrapWrappedException", "(Lorg/mozilla/javascript/WrappedException;)", "Ljava/lang/Object;");
      }

      String var7 = var1 == 0 ? "org/mozilla/javascript/JavaScriptException" : "org/mozilla/javascript/WrappedException";
      this.classFile.addExceptionHandler(var4, var3, var5, var7);
      this.addByteCode((byte)-89, var3);
   }

   public String generateCode(Node var1, Vector var2, Vector var3, ClassNameHelper var4) {
      this.itsNameHelper = (OptClassNameHelper)var4;
      this.namesVector = var2;
      this.classFilesVector = var3;
      Context var5 = Context.getCurrentContext();
      this.itsSourceFile = null;
      if (!var5.isGeneratingDebugChanged() || var5.isGeneratingDebug()) {
         this.itsSourceFile = (String)var1.getProp(16);
      }

      this.version = var5.getLanguageVersion();
      this.optLevel = var5.getOptimizationLevel();
      this.inFunction = var1.getType() == 110;
      this.superClassName = this.inFunction ? "org.mozilla.javascript.NativeFunction" : "org.mozilla.javascript.NativeScript";
      this.superClassSlashName = this.superClassName.replace('.', '/');
      Node var6;
      if (this.inFunction) {
         OptFunctionNode var7 = (OptFunctionNode)var1;
         this.inDirectCallFunction = var7.isTargetOfDirectCall();
         this.vars = (OptVariableTable)var7.getVariableTable();
         this.name = var7.getClassName();
         this.classFile = new ClassFileWriter(this.name, this.superClassName, this.itsSourceFile);
         Node var8 = var1.getFirstChild();
         String var9 = var7.getFunctionName();
         this.generateInit(var5, "<init>", var1, var9, var8);
         if (!var7.isTargetOfDirectCall()) {
            this.startNewMethod("call", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;", 1, false, true);
            this.generatePrologue(var5, var1, true, -1);
         } else {
            this.classFile.startMethod("call", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;", (short)17);
            this.addByteCode((byte)42);
            this.addByteCode((byte)43);
            this.addByteCode((byte)44);
            this.addByteCode((byte)45);

            int var11;
            for(int var10 = 0; var10 < this.vars.getParameterCount(); ++var10) {
               this.push((long)var10);
               this.addByteCode((byte)25, 4);
               this.addByteCode((byte)-66);
               var11 = this.acquireLabel();
               int var12 = this.acquireLabel();
               this.addByteCode((byte)-94, var11);
               this.addByteCode((byte)25, 4);
               this.push((long)var10);
               this.addByteCode((byte)50);
               this.push(0.0);
               this.addByteCode((byte)-89, var12);
               this.markLabel(var11);
               this.pushUndefined();
               this.push(0.0);
               this.markLabel(var12);
            }

            this.addByteCode((byte)25, 4);
            this.addVirtualInvoke(this.name, "callDirect", var7.getDirectCallParameterSignature(), "Ljava/lang/Object;");
            this.addByteCode((byte)-80);
            this.classFile.stopMethod((short)5, (VariableTable)null);
            this.emitDirectConstructor(var7);
            this.startNewMethod("callDirect", var7.getDirectCallParameterSignature() + "Ljava/lang/Object;", 1, false, true);
            this.vars.assignParameterJRegs();
            if (!var7.getParameterNumberContext()) {
               this.itsForcedObjectParameters = true;

               for(var11 = 0; var11 < this.vars.getParameterCount(); ++var11) {
                  OptLocalVariable var19 = (OptLocalVariable)this.vars.getVariable(var11);
                  this.aload(var19.getJRegister());
                  this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
                  int var13 = this.acquireLabel();
                  this.addByteCode((byte)-90, var13);
                  this.addByteCode((byte)-69, "java/lang/Double");
                  this.addByteCode((byte)89);
                  this.dload((short)(var19.getJRegister() + 1));
                  this.addDoubleConstructor();
                  this.astore(var19.getJRegister());
                  this.markLabel(var13);
               }
            }

            this.generatePrologue(var5, var1, true, this.vars.getParameterCount());
         }

         var6 = var1.getLastChild();
      } else {
         if (var1.getType() != 146) {
            this.badTree();
         }

         this.vars = (OptVariableTable)var1.getProp(10);
         boolean var15 = this.itsNameHelper.getTargetExtends() == null && this.itsNameHelper.getTargetImplements() == null;
         this.name = this.itsNameHelper.getJavaScriptClassName((String)null, var15);
         this.classFile = new ClassFileWriter(this.name, this.superClassName, this.itsSourceFile);
         this.classFile.addInterface("org/mozilla/javascript/Script");
         this.generateScriptCtor(var5, var1);
         this.generateMain(var5);
         this.generateInit(var5, "initScript", var1, "", (Node)null);
         this.generateExecute(var5);
         this.startNewMethod("call", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;", 1, false, true);
         this.generatePrologue(var5, var1, false, -1);
         Object var17 = var1.getProp(29);
         if (var17 != null) {
            this.classFile.addLineNumberEntry(((Integer)var17).shortValue());
         }

         var1.addChildToBack(new Node(5));
         var6 = var1;
      }

      this.generateCodeFromNode(var6, (Node)null, -1, -1);
      this.generateEpilogue();
      this.finishMethod(var5, this.debugVars);
      this.emitConstantDudeInitializers();
      ByteArrayOutputStream var16 = new ByteArrayOutputStream(512);

      try {
         this.classFile.write(var16);
      } catch (IOException var14) {
         throw new RuntimeException("unexpected IOException");
      }

      byte[] var18 = var16.toByteArray();
      this.namesVector.addElement(this.name);
      this.classFilesVector.addElement(var18);
      this.classFile = null;
      this.namesVector = null;
      this.classFilesVector = null;
      return this.name;
   }

   private void generateCodeFromNode(Node var1, Node var2, int var3, int var4) {
      int var5 = var1.getType();
      Node var6 = var1.getFirstChild();
      switch (var5) {
         case 2:
            this.visitStatement(var1);

            while(var6 != null) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               var6 = var6.getNextSibling();
            }

            this.astore(this.scriptResultLocal);
            break;
         case 3:
            this.visitEnterWith(var1, var6);
            break;
         case 4:
            this.visitLeaveWith(var1, var6);
            break;
         case 5:
            this.visitReturn(var1, var6);
            break;
         case 6:
         case 7:
         case 8:
         case 143:
            this.visitGOTO(var1, var5, var6);
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
            throw new RuntimeException("Unexpected node type " + TokenStream.tokenToName(var5));
         case 10:
            this.visitSetName(var1, var6);
            break;
         case 11:
         case 12:
         case 13:
         case 20:
         case 21:
         case 22:
            this.visitBitOp(var1, var5, var6);
            break;
         case 23:
            this.generateCodeFromNode(var6, var1, var3, var4);
            this.generateCodeFromNode(var6.getNextSibling(), var1, var3, var4);
            Integer var13 = (Integer)var1.getProp(26);
            if (var13 != null) {
               if (var13 == 0) {
                  this.addByteCode((byte)99);
               } else if (var13 == 1) {
                  this.addOptRuntimeInvoke("add", "(DLjava/lang/Object;)", "Ljava/lang/Object;");
               } else {
                  this.addOptRuntimeInvoke("add", "(Ljava/lang/Object;D)", "Ljava/lang/Object;");
               }
            } else {
               this.addScriptRuntimeInvoke("add", "(Ljava/lang/Object;Ljava/lang/Object;)", "Ljava/lang/Object;");
            }
            break;
         case 24:
            this.visitArithmetic(var1, (byte)103, var6, var2);
            break;
         case 25:
            this.visitArithmetic(var1, (byte)107, var6, var2);
            break;
         case 26:
         case 27:
            this.visitArithmetic(var1, (byte)(var5 == 26 ? 111 : 115), var6, var2);
            break;
         case 30:
         case 43:
            this.visitCall(var1, var5, var6);
            break;
         case 31:
            while(var6 != null) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               var6 = var6.getNextSibling();
            }

            this.addScriptRuntimeInvoke("delete", "(Ljava/lang/Object;Ljava/lang/Object;)", "Ljava/lang/Object;");
            break;
         case 32:
            this.visitTypeof(var1, var6);
            break;
         case 39:
            this.visitGetProp(var1, var6);
            break;
         case 40:
            this.visitSetProp(var1, var6);
            break;
         case 41:
            while(var6 != null) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               var6 = var6.getNextSibling();
            }

            this.aload(this.variableObjectLocal);
            if (var1.getProp(26) != null) {
               this.addOptRuntimeInvoke("getElem", "(Ljava/lang/Object;DLorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            } else {
               this.addScriptRuntimeInvoke("getElem", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            }
            break;
         case 42:
            while(var6 != null) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               var6 = var6.getNextSibling();
            }

            this.aload(this.variableObjectLocal);
            if (var1.getProp(26) != null) {
               this.addOptRuntimeInvoke("setElem", "(Ljava/lang/Object;DLjava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            } else {
               this.addScriptRuntimeInvoke("setElem", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            }
            break;
         case 44:
            this.visitName(var1);
            break;
         case 45:
         case 46:
            this.visitLiteral(var1);
            break;
         case 56:
            this.visitObject(var1);
            break;
         case 57:
            this.visitStatement(var1);
            if (var6.getType() == 73) {
               this.visitSetVar(var6, var6.getFirstChild(), false);
            } else {
               while(var6 != null) {
                  this.generateCodeFromNode(var6, var1, var3, var4);
                  var6 = var6.getNextSibling();
               }

               if (var1.getProp(26) != null) {
                  this.addByteCode((byte)88);
               } else {
                  this.addByteCode((byte)87);
               }
            }
            break;
         case 61:
         case 71:
            this.visitBind(var1, var5, var6);
            break;
         case 62:
            this.visitThrow(var1, var6);
            break;
         case 68:
            this.generateCodeFromNode(var6, var1, var3, var4);
            this.addScriptRuntimeInvoke("getThis", "(Lorg/mozilla/javascript/Scriptable;)", "Lorg/mozilla/javascript/Scriptable;");
            break;
         case 69:
            this.visitNewTemp(var1, var6);
            break;
         case 70:
            this.visitUseTemp(var1, var6);
            break;
         case 72:
            OptLocalVariable var11 = (OptLocalVariable)var1.getProp(24);
            this.visitGetVar(var11, var1.getProp(26) != null, var1.getString());
            break;
         case 73:
            this.visitSetVar(var1, var6, true);
            break;
         case 75:
            this.visitTryCatchFinally(var1, var6);
            break;
         case 77:
            this.addScriptRuntimeInvoke("newScope", "()", "Lorg/mozilla/javascript/Scriptable;");
            break;
         case 79:
            this.visitEnumInit(var1, var6);
            break;
         case 80:
            this.visitEnumNext(var1, var6);
            break;
         case 96:
            this.generateCodeFromNode(var6, var1, -1, -1);
            this.addByteCode((byte)87);
            this.generateCodeFromNode(var6.getNextSibling(), var1, var3, var4);
            break;
         case 100:
         case 101:
            int var10;
            if (var3 == -1) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               this.addByteCode((byte)89);
               this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
               var10 = this.acquireLabel();
               if (var5 == 101) {
                  this.addByteCode((byte)-103, var10);
               } else {
                  this.addByteCode((byte)-102, var10);
               }

               this.addByteCode((byte)87);
               this.generateCodeFromNode(var6.getNextSibling(), var1, var3, var4);
               this.markLabel(var10);
            } else {
               var10 = this.acquireLabel();
               if (var5 == 101) {
                  this.generateCodeFromNode(var6, var1, var10, var4);
                  if ((var6.getType() != 105 || var6.getInt() != 129) && var6.getType() != 101 && var6.getType() != 100 && var6.getType() != 103 && var6.getType() != 102) {
                     this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
                     this.addByteCode((byte)-102, var10);
                     this.addByteCode((byte)-89, var4);
                  }
               } else {
                  this.generateCodeFromNode(var6, var1, var3, var10);
                  if ((var6.getType() != 105 || var6.getInt() != 129) && var6.getType() != 101 && var6.getType() != 100 && var6.getType() != 103 && var6.getType() != 102) {
                     this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
                     this.addByteCode((byte)-102, var3);
                     this.addByteCode((byte)-89, var10);
                  }
               }

               this.markLabel(var10);
               var6 = var6.getNextSibling();
               this.generateCodeFromNode(var6, var1, var3, var4);
               if ((var6.getType() != 105 || var6.getInt() != 129) && var6.getType() != 101 && var6.getType() != 100 && var6.getType() != 103 && var6.getType() != 102) {
                  this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
                  this.addByteCode((byte)-102, var3);
                  this.addByteCode((byte)-89, var4);
               }
            }
            break;
         case 102:
            this.visitEqOp(var1, var6, var2, var3, var4);
            break;
         case 103:
            if (var3 == -1) {
               this.visitRelOp(var1, var6, var2);
            } else {
               this.visitGOTOingRelOp(var1, var6, var2, var3, var4);
            }
            break;
         case 105:
            this.visitUnary(var1, var6, var3, var4);
            break;
         case 106:
            this.visitIncDec(var1, true);
            break;
         case 107:
            this.visitIncDec(var1, false);
            break;
         case 109:
            this.visitPrimary(var1);
            break;
         case 110:
            if (this.inFunction || var2.getType() != 146) {
               FunctionNode var9 = (FunctionNode)var1.getProp(5);
               byte var12 = var9.getFunctionType();
               if (var12 != 1) {
                  this.visitFunction(var9, var12 == 3);
               }
            }
            break;
         case 115:
            this.visitSwitch(var1, var6);
            break;
         case 116:
         case 117:
         case 128:
         case 132:
         case 133:
         case 146:
            this.visitStatement(var1);

            while(var6 != null) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               var6 = var6.getNextSibling();
            }

            return;
         case 124:
         case 136:
         case 138:
            this.visitStatement(var1);

            while(var6 != null) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               var6 = var6.getNextSibling();
            }

            return;
         case 137:
            this.visitTarget(var1);
            break;
         case 139:
            this.visitEnumDone(var1, var6);
            break;
         case 141:
            this.generateCodeFromNode(var6, var1, var3, var4);
            this.addScriptRuntimeInvoke("getParent", "(Ljava/lang/Object;)", "Lorg/mozilla/javascript/Scriptable;");
            break;
         case 142:
            Object var7 = var1.getProp(18);
            if (var7 == ScriptRuntime.NumberClass) {
               this.addByteCode((byte)-69, "java/lang/Double");
               this.addByteCode((byte)89);
               this.generateCodeFromNode(var6, var1, var3, var4);
               this.addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)", "D");
               this.addDoubleConstructor();
            } else if (var7 == ScriptRuntime.DoubleClass) {
               this.generateCodeFromNode(var6, var1, var3, var4);
               this.addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)", "D");
            } else if (var7 == ScriptRuntime.ObjectClass) {
               if (var6.getType() == 45 && var6.getProp(26) != null) {
                  Object var8 = var6.getProp(26);
                  var6.putProp(26, (Object)null);
                  this.generateCodeFromNode(var6, var1, var3, var4);
                  var6.putProp(26, var8);
               } else {
                  this.addByteCode((byte)-69, "java/lang/Double");
                  this.addByteCode((byte)89);
                  this.generateCodeFromNode(var6, var1, var3, var4);
                  this.addDoubleConstructor();
               }
            } else {
               this.badTree();
            }
            break;
         case 144:
            this.visitNewLocal(var1, var6);
            break;
         case 145:
            this.visitUseLocal(var1, var6);
      }

   }

   private void generateEpilogue() {
      if (this.epilogueLabel != -1) {
         this.classFile.markLabel(this.epilogueLabel);
      }

      if (!this.hasVarsInRegs || !this.inFunction) {
         this.aload(this.contextLocal);
         this.addScriptRuntimeInvoke("popActivation", "(Lorg/mozilla/javascript/Context;)", "V");
      }

      this.addByteCode((byte)-80);
   }

   private void generateExecute(Context var1) {
      String var2 = "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;)Ljava/lang/Object;";
      this.startNewMethod("exec", var2, 2, false, true);
      String var3 = this.name.replace('.', '/');
      if (!this.trivialInit) {
         this.addByteCode((byte)42);
         this.addByteCode((byte)44);
         this.addByteCode((byte)43);
         this.addVirtualInvoke(var3, "initScript", "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;)", "V");
      }

      this.addByteCode((byte)42);
      this.addByteCode((byte)43);
      this.addByteCode((byte)44);
      this.addByteCode((byte)89);
      this.addByteCode((byte)1);
      this.addVirtualInvoke(var3, "call", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)", "Ljava/lang/Object;");
      this.addByteCode((byte)-80);
      this.finishMethod(var1, (VariableTable)null);
   }

   private void generateFunctionInits(Vector var1) {
      this.push((long)var1.size());
      this.addByteCode((byte)-67, "org/mozilla/javascript/NativeFunction");

      for(short var2 = 0; var2 < var1.size(); ++var2) {
         this.addByteCode((byte)89);
         this.push((long)var2);
         Node var3 = (Node)var1.elementAt(var2);
         Codegen var4 = new Codegen();
         String var5 = var4.generateCode(var3, this.namesVector, this.classFilesVector, this.itsNameHelper);
         this.addByteCode((byte)-69, var5);
         this.addByteCode((byte)89);
         if (this.inFunction) {
            this.addByteCode((byte)42);
         } else {
            this.aload(this.variableObjectLocal);
         }

         this.aload(this.contextLocal);
         this.addSpecialInvoke(var5, "<init>", "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;)", "V");
         if (this.inFunction) {
            this.addByteCode((byte)42);
         } else {
            this.aload(this.variableObjectLocal);
         }

         String var6 = var3.getString();
         if (var6 != null) {
            this.push(var6);
         } else {
            this.addByteCode((byte)1);
         }

         this.aload(this.contextLocal);
         boolean var7 = var6 != null && var6.length() > 0 && ((FunctionNode)var3).getFunctionType() == 1;
         this.addByteCode((byte)(var7 ? 4 : 3));
         this.addScriptRuntimeInvoke("initFunction", "(Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Context;Z)", "Lorg/mozilla/javascript/NativeFunction;");
         var3.putProp(5, new Short(var2));
         this.addByteCode((byte)83);
      }

      this.addByteCode((byte)42);
      this.addByteCode((byte)95);
      this.classFile.add((byte)-75, "org/mozilla/javascript/NativeFunction", "nestedFunctions", "[Lorg/mozilla/javascript/NativeFunction;");
   }

   private void generateGOTO(int var1, Node var2) {
      Node var3 = new Node(var1);
      var3.putProp(1, var2);
      this.visitGOTO(var3, var1, (Node)null);
   }

   private void generateInit(Context var1, String var2, Node var3, String var4, Node var5) {
      this.trivialInit = true;
      boolean var6 = false;
      VariableTable var7;
      if (var3 instanceof OptFunctionNode) {
         var7 = ((OptFunctionNode)var3).getVariableTable();
      } else {
         var7 = (VariableTable)var3.getProp(10);
      }

      if (var2.equals("<init>")) {
         var6 = true;
         this.setNonTrivialInit(var2);
         this.addByteCode((byte)42);
         this.addSpecialInvoke(this.superClassSlashName, "<init>", "()", "V");
         this.addByteCode((byte)42);
         this.addByteCode((byte)43);
         this.classFile.add((byte)-75, "org/mozilla/javascript/ScriptableObject", "parent", "Lorg/mozilla/javascript/Scriptable;");
      }

      if (var4.length() != 0) {
         this.setNonTrivialInit(var2);
         this.addByteCode((byte)42);
         this.classFile.addLoadConstant(var4);
         this.classFile.add((byte)-75, "org/mozilla/javascript/NativeFunction", "functionName", "Ljava/lang/String;");
      }

      int var8;
      if (var7 != null) {
         var8 = var7.size();
         if (var8 != 0) {
            this.setNonTrivialInit(var2);
            this.push((long)var8);
            this.addByteCode((byte)-67, "java/lang/String");

            for(int var9 = 0; var9 != var8; ++var9) {
               this.addByteCode((byte)89);
               this.push((long)var9);
               this.push(var7.getName(var9));
               this.addByteCode((byte)83);
            }

            this.addByteCode((byte)42);
            this.addByteCode((byte)95);
            this.classFile.add((byte)-75, "org/mozilla/javascript/NativeFunction", "argNames", "[Ljava/lang/String;");
         }
      }

      var8 = var7 == null ? 0 : var7.getParameterCount();
      if (var8 != 0) {
         this.setNonTrivialInit(var2);
         this.addByteCode((byte)42);
         this.push((long)var8);
         this.classFile.add((byte)-75, "org/mozilla/javascript/NativeFunction", "argCount", "S");
      }

      if (var1.getLanguageVersion() != 0) {
         this.setNonTrivialInit(var2);
         this.addByteCode((byte)42);
         this.push((long)var1.getLanguageVersion());
         this.classFile.add((byte)-75, "org/mozilla/javascript/NativeFunction", "version", "S");
      }

      String var16 = (String)var3.getProp(17);
      if (var16 != null && var1.isGeneratingSource() && var16.length() < 65536) {
         this.setNonTrivialInit(var2);
         this.addByteCode((byte)42);
         this.push(var16);
         this.classFile.add((byte)-75, "org/mozilla/javascript/NativeFunction", "source", "Ljava/lang/String;");
      }

      Vector var10 = (Vector)var3.getProp(12);
      if (var10 != null) {
         this.setNonTrivialInit(var2);
         this.generateRegExpLiterals(var10, var6);
      }

      Vector var11 = (Vector)var3.getProp(5);
      if (var11 != null) {
         this.setNonTrivialInit(var2);
         this.generateFunctionInits(var11);
      }

      if (var3 instanceof OptFunctionNode) {
         OptFunctionNode var12 = (OptFunctionNode)var3;
         Vector var13 = ((OptFunctionNode)var3).getDirectCallTargets();
         if (var13 != null) {
            this.setNonTrivialInit(var2);
            this.classFile.addField("EmptyArray", "[Ljava/lang/Object;", (short)2);
            this.addByteCode((byte)42);
            this.push(0L);
            this.addByteCode((byte)-67, "java/lang/Object");
            this.classFile.add((byte)-75, ClassFileWriter.fullyQualifiedForm(this.name), "EmptyArray", "[Ljava/lang/Object;");
         }

         if (var12.isTargetOfDirectCall()) {
            this.setNonTrivialInit(var2);
            String var14 = ClassFileWriter.fullyQualifiedForm(var12.getClassName());
            String var15 = var14.replace('/', '_');
            this.classFile.addField(var15, "L" + var14 + ";", (short)9);
            this.addByteCode((byte)42);
            this.classFile.add((byte)-77, var14, var15, "L" + var14 + ";");
         }
      }

      if (!this.trivialInit) {
         this.addByteCode((byte)-79);
         this.finishMethod(var1, (VariableTable)null);
      }

   }

   private void generateMain(Context var1) {
      this.startNewMethod("main", "([Ljava/lang/String;)V", 1, true, true);
      this.push(this.name);
      this.addByteCode((byte)42);
      this.addScriptRuntimeInvoke("main", "(Ljava/lang/String;[Ljava/lang/String;)", "V");
      this.addByteCode((byte)-79);
      this.finishMethod(var1, (VariableTable)null);
   }

   private void generatePrologue(Context var1, Node var2, boolean var3, int var4) {
      this.funObjLocal = this.reserveWordLocal(0);
      this.contextLocal = this.reserveWordLocal(1);
      this.variableObjectLocal = this.reserveWordLocal(2);
      this.thisObjLocal = this.reserveWordLocal(3);
      if (var3 && !var1.hasCompileFunctionsWithDynamicScope() && var4 == -1) {
         this.aload(this.funObjLocal);
         this.classFile.add((byte)-71, "org/mozilla/javascript/Scriptable", "getParentScope", "()", "Lorg/mozilla/javascript/Scriptable;");
         this.astore(this.variableObjectLocal);
      }

      if (var4 > 0) {
         for(int var5 = 0; var5 < 3 * var4; ++var5) {
            this.reserveWordLocal(var5 + 4);
         }
      }

      this.argsLocal = this.reserveWordLocal(var4 <= 0 ? 4 : 3 * var4 + 4);
      Integer var10 = (Integer)var2.getProp(22);
      int var6;
      if (var10 != null) {
         this.itsLocalAllocationBase = (short)(this.argsLocal + 1);

         for(var6 = 0; var6 < var10; ++var6) {
            this.reserveWordLocal(this.itsLocalAllocationBase + var6);
         }
      }

      if (var3 && ((OptFunctionNode)var2).getCheckThis()) {
         this.aload(this.thisObjLocal);
         this.addScriptRuntimeInvoke("getThis", "(Lorg/mozilla/javascript/Scriptable;)", "Lorg/mozilla/javascript/Scriptable;");
         this.astore(this.thisObjLocal);
      }

      this.hasVarsInRegs = var3 && !((OptFunctionNode)var2).requiresActivation();
      int var8;
      if (this.hasVarsInRegs) {
         var6 = this.vars.getParameterCount();
         if (var3 && var6 > 0 && var4 < 0) {
            this.aload(this.argsLocal);
            this.addByteCode((byte)-66);
            this.push((long)var6);
            int var11 = this.acquireLabel();
            this.addByteCode((byte)-94, var11);
            this.aload(this.argsLocal);
            this.push((long)var6);
            this.addScriptRuntimeInvoke("padArguments", "([Ljava/lang/Object;I)", "[Ljava/lang/Object;");
            this.astore(this.argsLocal);
            this.markLabel(var11);
         }

         short var13 = -1;

         for(var8 = 0; var8 < this.vars.size(); ++var8) {
            OptLocalVariable var15 = (OptLocalVariable)this.vars.getVariable(var8);
            if (var15.isNumber()) {
               var15.assignJRegister(this.getNewWordPairLocal());
               this.push(0.0);
               this.dstore(var15.getJRegister());
            } else if (var15.isParameter()) {
               if (var4 < 0) {
                  var15.assignJRegister(this.getNewWordLocal());
                  this.aload(this.argsLocal);
                  this.push((long)var8);
                  this.addByteCode((byte)50);
                  this.astore(var15.getJRegister());
               }
            } else {
               var15.assignJRegister(this.getNewWordLocal());
               if (var13 == -1) {
                  this.pushUndefined();
                  var13 = var15.getJRegister();
               } else {
                  this.aload(var13);
               }

               this.astore(var15.getJRegister());
            }

            var15.setStartPC(this.classFile.getCurrentCodeOffset());
         }

         this.debugVars = this.vars;
      } else {
         if (var4 > 0) {
            this.aload(this.argsLocal);
            this.push((long)var4);
            this.addOptRuntimeInvoke("padStart", "([Ljava/lang/Object;I)", "[Ljava/lang/Object;");
            this.astore(this.argsLocal);

            for(var6 = 0; var6 < var4; ++var6) {
               this.aload(this.argsLocal);
               this.push((long)var6);
               this.aload((short)(3 * var6 + 4));
               this.addByteCode((byte)83);
            }
         }

         String var12;
         if (var3) {
            this.aload(this.contextLocal);
            this.aload(this.variableObjectLocal);
            this.aload(this.funObjLocal);
            this.aload(this.thisObjLocal);
            this.aload(this.argsLocal);
            this.addScriptRuntimeInvoke("initVarObj", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)", "Lorg/mozilla/javascript/Scriptable;");
            var12 = "activation";
         } else {
            this.aload(this.contextLocal);
            this.aload(this.variableObjectLocal);
            this.aload(this.funObjLocal);
            this.aload(this.thisObjLocal);
            this.push(0L);
            this.addScriptRuntimeInvoke("initScript", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/NativeFunction;Lorg/mozilla/javascript/Scriptable;Z)", "Lorg/mozilla/javascript/Scriptable;");
            var12 = "global";
         }

         this.astore(this.variableObjectLocal);
         Vector var7 = (Vector)var2.getProp(5);
         if (var3 && var7 != null) {
            for(var8 = 0; var8 < var7.size(); ++var8) {
               FunctionNode var9 = (FunctionNode)var7.elementAt(var8);
               if (var9.getFunctionType() == 1) {
                  this.visitFunction(var9, true);
                  this.addByteCode((byte)87);
               }
            }
         }

         if (!var1.isGeneratingDebugChanged() || var1.isGeneratingDebug()) {
            this.debugVars = new OptVariableTable();
            this.debugVars.addLocal(var12);
            OptLocalVariable var14 = (OptLocalVariable)this.debugVars.getVariable(var12);
            var14.assignJRegister(this.variableObjectLocal);
            var14.setStartPC(this.classFile.getCurrentCodeOffset());
         }

         if (!var3) {
            this.scriptResultLocal = this.getNewWordLocal();
            this.pushUndefined();
            this.astore(this.scriptResultLocal);
         }

         if (var3 && ((OptFunctionNode)var2).containsCalls(-1)) {
            if (((OptFunctionNode)var2).containsCalls(0)) {
               this.itsZeroArgArray = this.getNewWordLocal();
               this.classFile.add((byte)-78, "org/mozilla/javascript/ScriptRuntime", "emptyArgs", "[Ljava/lang/Object;");
               this.astore(this.itsZeroArgArray);
            }

            if (((OptFunctionNode)var2).containsCalls(1)) {
               this.itsOneArgArray = this.getNewWordLocal();
               this.push(1L);
               this.addByteCode((byte)-67, "java/lang/Object");
               this.astore(this.itsOneArgArray);
            }
         }

      }
   }

   private void generateRegExpLiterals(Vector var1, boolean var2) {
      for(int var3 = 0; var3 < var1.size(); ++var3) {
         Node var4 = (Node)var1.elementAt(var3);
         StringBuffer var5 = new StringBuffer("_re");
         var5.append(var3);
         String var6 = var5.toString();
         short var7 = 2;
         if (var2) {
            var7 = (short)(var7 | 16);
         }

         this.classFile.addField(var6, "Lorg/mozilla/javascript/regexp/NativeRegExp;", var7);
         this.addByteCode((byte)42);
         this.addByteCode((byte)-69, "org/mozilla/javascript/regexp/NativeRegExp");
         this.addByteCode((byte)89);
         this.aload(this.contextLocal);
         this.aload(this.variableObjectLocal);
         Node var8 = var4.getFirstChild();
         this.push(var8.getString());
         Node var9 = var4.getLastChild();
         if (var8 == var9) {
            this.addByteCode((byte)1);
         } else {
            this.push(var9.getString());
         }

         this.push(0L);
         this.addSpecialInvoke("org/mozilla/javascript/regexp/NativeRegExp", "<init>", "(Lorg/mozilla/javascript/Context;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Ljava/lang/String;Z)", "V");
         var4.putProp(12, var6);
         this.classFile.add((byte)-75, ClassFileWriter.fullyQualifiedForm(this.name), var6, "Lorg/mozilla/javascript/regexp/NativeRegExp;");
      }

   }

   private void generateScriptCtor(Context var1, Node var2) {
      this.startNewMethod("<init>", "()V", 1, false, false);
      this.addByteCode((byte)42);
      this.addSpecialInvoke(this.superClassSlashName, "<init>", "()", "V");
      this.addByteCode((byte)-79);
      this.finishMethod(var1, (VariableTable)null);
   }

   private short getLocalFromNode(Node var1) {
      Integer var2 = (Integer)var1.getProp(7);
      if (var2 != null) {
         return var2.shortValue();
      } else {
         short var10000;
         if (var1.getType() != 144 && var1.getType() != 145) {
            var10000 = this.getNewWordLocal();
         } else {
            short var10002 = this.itsLocalAllocationBase;
            var10000 = var10002;
            this.itsLocalAllocationBase = (short)(var10002 + 1);
         }

         short var3 = var10000;
         var1.putProp(7, new Integer(var3));
         return var3;
      }
   }

   private short getNewWordLocal() {
      short var1 = this.firstFreeLocal;
      this.locals[var1] = true;

      for(int var2 = this.firstFreeLocal + 1; var2 < 256; ++var2) {
         if (!this.locals[var2]) {
            this.firstFreeLocal = (short)var2;
            if (this.localsMax < this.firstFreeLocal) {
               this.localsMax = this.firstFreeLocal;
            }

            return var1;
         }
      }

      throw Context.reportRuntimeError("Program too complex (out of locals)");
   }

   private short getNewWordPairLocal() {
      short var1;
      for(var1 = this.firstFreeLocal; var1 < 255 && (this.locals[var1] || this.locals[var1 + 1]); ++var1) {
      }

      if (var1 < 255) {
         this.locals[var1] = true;
         this.locals[var1 + 1] = true;
         if (var1 != this.firstFreeLocal) {
            return var1;
         }

         for(int var2 = this.firstFreeLocal + 2; var2 < 256; ++var2) {
            if (!this.locals[var2]) {
               this.firstFreeLocal = (short)var2;
               if (this.localsMax < this.firstFreeLocal) {
                  this.localsMax = this.firstFreeLocal;
               }

               return var1;
            }
         }
      }

      throw Context.reportRuntimeError("Program too complex (out of locals)");
   }

   private String getSimpleCallName(Node var1) {
      Node var2 = var1.getFirstChild();
      if (var2.getType() == 39) {
         Node var3 = var2.getFirstChild();
         if (var3.getType() == 69) {
            Node var4 = var3.getNextSibling();
            Node var5 = var3.getFirstChild();
            if (var5.getType() == 71) {
               String var6 = var5.getString();
               if (var4 != null && var4.getType() == 46 && var6.equals(var4.getString())) {
                  Node var7 = var2.getNextSibling();
                  if (var7.getType() == 68) {
                     Node var8 = var7.getFirstChild();
                     if (var8.getType() == 70) {
                        Node var9 = (Node)var8.getProp(6);
                        if (var9 == var3) {
                           return var6;
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private void iload(short var1) {
      this.xop((byte)26, (byte)21, var1);
   }

   private boolean isArithmeticNode(Node var1) {
      int var2 = var1.getType();
      return var2 == 24 || var2 == 27 || var2 == 26 || var2 == 25;
   }

   private void istore(short var1) {
      this.xop((byte)59, (byte)54, var1);
   }

   public int markLabel(int var1) {
      return this.classFile.markLabel(var1);
   }

   public int markLabel(int var1, short var2) {
      return this.classFile.markLabel(var1, var2);
   }

   private Number nodeIsConvertToObjectOfNumber(Node var1) {
      if (var1.getType() == 142) {
         Object var2 = var1.getProp(18);
         if (var2 == ScriptRuntime.ObjectClass) {
            Node var3 = var1.getFirstChild();
            if (var3.getType() == 45) {
               return (Number)var3.getDatum();
            }
         }
      }

      return null;
   }

   private boolean nodeIsDirectCallParameter(Node var1) {
      if (var1.getType() == 72) {
         OptLocalVariable var2 = (OptLocalVariable)var1.getProp(24);
         if (var2 != null && var2.isParameter() && this.inDirectCallFunction && !this.itsForcedObjectParameters) {
            return true;
         }
      }

      return false;
   }

   private void push(double var1) {
      if (var1 == 0.0) {
         this.addByteCode((byte)14);
      } else if (var1 == 1.0) {
         this.addByteCode((byte)15);
      } else {
         this.classFile.addLoadConstant(var1);
      }

   }

   private void push(long var1) {
      if (var1 == -1L) {
         this.addByteCode((byte)2);
      } else if (var1 >= 0L && var1 <= 5L) {
         this.addByteCode((byte)((int)(3L + var1)));
      } else if (var1 >= -128L && var1 <= 127L) {
         this.addByteCode((byte)16, (byte)((int)var1));
      } else if (var1 >= -32768L && var1 <= 32767L) {
         this.addByteCode((byte)17, (short)((int)var1));
      } else if (var1 >= -2147483648L && var1 <= 2147483647L) {
         this.classFile.addLoadConstant((int)var1);
      } else {
         this.classFile.addLoadConstant(var1);
      }

   }

   private void push(String var1) {
      this.classFile.addLoadConstant(var1);
   }

   private void pushUndefined() {
      this.classFile.add((byte)-78, "org/mozilla/javascript/Undefined", "instance", "Lorg/mozilla/javascript/Scriptable;");
   }

   private void releaseWordLocal(short var1) {
      if (var1 < this.firstFreeLocal) {
         this.firstFreeLocal = var1;
      }

      this.locals[var1] = false;
   }

   private void releaseWordpairLocal(short var1) {
      if (var1 < this.firstFreeLocal) {
         this.firstFreeLocal = var1;
      }

      this.locals[var1] = false;
      this.locals[var1 + 1] = false;
   }

   private short reserveWordLocal(int var1) {
      if (this.getNewWordLocal() != var1) {
         throw new RuntimeException("Local allocation error");
      } else {
         return (short)var1;
      }
   }

   private void resetTargets(Node var1) {
      if (var1.getType() == 137) {
         var1.putProp(20, (Object)null);
      }

      for(Node var2 = var1.getFirstChild(); var2 != null; var2 = var2.getNextSibling()) {
         this.resetTargets(var2);
      }

   }

   private void setNonTrivialInit(String var1) {
      if (this.trivialInit) {
         this.trivialInit = false;
         this.startNewMethod(var1, "(Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Context;)V", 1, false, false);
         this.reserveWordLocal(0);
         this.variableObjectLocal = this.reserveWordLocal(1);
         this.contextLocal = this.reserveWordLocal(2);
      }
   }

   private void startNewMethod(String var1, String var2, int var3, boolean var4, boolean var5) {
      this.locals = new boolean[256];
      this.localsMax = (short)(var3 + 1);
      this.firstFreeLocal = 0;
      this.contextLocal = -1;
      this.variableObjectLocal = -1;
      this.scriptResultLocal = -1;
      this.argsLocal = -1;
      this.thisObjLocal = -1;
      this.funObjLocal = -1;
      this.debug_pcLocal = -1;
      this.debugStopSubRetLocal = -1;
      this.itsZeroArgArray = -1;
      this.itsOneArgArray = -1;
      short var6 = 1;
      if (var4) {
         var6 = (short)(var6 | 8);
      }

      if (var5) {
         var6 = (short)(var6 | 16);
      }

      this.epilogueLabel = -1;
      this.classFile.startMethod(var1, var2, var6);
   }

   public Node transform(Node var1, TokenStream var2, Scriptable var3) {
      OptTransformer var4 = new OptTransformer(new Hashtable(11));
      return var4.transform(var1, (Node)null, var2, var3);
   }

   private void visitArithmetic(Node var1, byte var2, Node var3, Node var4) {
      Integer var5 = (Integer)var1.getProp(26);
      if (var5 != null) {
         this.generateCodeFromNode(var3, var1, -1, -1);
         this.generateCodeFromNode(var3.getNextSibling(), var1, -1, -1);
         this.addByteCode(var2);
      } else {
         boolean var6 = this.isArithmeticNode(var4);
         if (!var6) {
            this.addByteCode((byte)-69, "java/lang/Double");
            this.addByteCode((byte)89);
         }

         this.generateCodeFromNode(var3, var1, -1, -1);
         if (!this.isArithmeticNode(var3)) {
            this.addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)", "D");
         }

         this.generateCodeFromNode(var3.getNextSibling(), var1, -1, -1);
         if (!this.isArithmeticNode(var3.getNextSibling())) {
            this.addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)", "D");
         }

         this.addByteCode(var2);
         if (!var6) {
            this.addDoubleConstructor();
         }
      }

   }

   private void visitBind(Node var1, int var2, Node var3) {
      while(var3 != null) {
         this.generateCodeFromNode(var3, var1, -1, -1);
         var3 = var3.getNextSibling();
      }

      this.aload(this.variableObjectLocal);
      this.push(var1.getString());
      this.addScriptRuntimeInvoke(var2 == 61 ? "bind" : "getBase", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)", "Lorg/mozilla/javascript/Scriptable;");
   }

   private void visitBitOp(Node var1, int var2, Node var3) {
      Integer var4 = (Integer)var1.getProp(26);
      if (var4 == null) {
         this.addByteCode((byte)-69, "java/lang/Double");
         this.addByteCode((byte)89);
      }

      this.generateCodeFromNode(var3, var1, -1, -1);
      if (var2 == 22) {
         this.addScriptRuntimeInvoke("toUint32", "(Ljava/lang/Object;)", "J");
         this.generateCodeFromNode(var3.getNextSibling(), var1, -1, -1);
         this.addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)", "I");
         this.push(31L);
         this.addByteCode((byte)126);
         this.addByteCode((byte)125);
         this.addByteCode((byte)-118);
         this.addDoubleConstructor();
      } else {
         if (var4 == null) {
            this.addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)", "I");
            this.generateCodeFromNode(var3.getNextSibling(), var1, -1, -1);
            this.addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)", "I");
         } else {
            this.addScriptRuntimeInvoke("toInt32", "(D)", "I");
            this.generateCodeFromNode(var3.getNextSibling(), var1, -1, -1);
            this.addScriptRuntimeInvoke("toInt32", "(D)", "I");
         }

         switch (var2) {
            case 11:
               this.addByteCode((byte)-128);
               break;
            case 12:
               this.addByteCode((byte)-126);
               break;
            case 13:
               this.addByteCode((byte)126);
               break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               this.badTree();
               break;
            case 20:
               this.addByteCode((byte)120);
               break;
            case 21:
               this.addByteCode((byte)122);
         }

         this.addByteCode((byte)-121);
         if (var4 == null) {
            this.addDoubleConstructor();
         }

      }
   }

   private void visitCall(Node var1, int var2, Node var3) {
      Node var4 = var3;
      OptFunctionNode var5 = (OptFunctionNode)var1.getProp(27);
      if (var5 != null) {
         this.generateCodeFromNode(var3, var1, -1, -1);
         int var6 = this.acquireLabel();
         String var7 = ClassFileWriter.fullyQualifiedForm(var5.getClassName());
         String var8 = var7.replace('/', '_');
         this.classFile.add((byte)-78, ClassFileWriter.fullyQualifiedForm(var7), var8, "L" + var7 + ";");
         short var9 = this.classFile.getStackTop();
         this.addByteCode((byte)92);
         this.addByteCode((byte)-90, var6);
         this.addByteCode((byte)95);
         this.addByteCode((byte)87);
         this.addByteCode((byte)89);
         this.classFile.add((byte)-71, "org/mozilla/javascript/Scriptable", "getParentScope", "()", "Lorg/mozilla/javascript/Scriptable;");
         this.aload(this.contextLocal);
         this.addByteCode((byte)95);
         if (var2 == 30) {
            this.addByteCode((byte)1);
         } else {
            var3 = var3.getNextSibling();
            this.generateCodeFromNode(var3, var1, -1, -1);
         }

         for(var3 = var3.getNextSibling(); var3 != null; var3 = var3.getNextSibling()) {
            boolean var10 = false;
            if (var3.getType() == 72 && this.inDirectCallFunction) {
               OptLocalVariable var11 = (OptLocalVariable)var3.getProp(24);
               if (var11.isParameter()) {
                  var10 = true;
                  this.aload(var11.getJRegister());
                  this.dload((short)(var11.getJRegister() + 1));
               }
            }

            if (!var10) {
               Integer var13 = (Integer)var3.getProp(26);
               if (var13 != null && var13 == 0) {
                  this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
                  this.generateCodeFromNode(var3, var1, -1, -1);
               } else {
                  this.generateCodeFromNode(var3, var1, -1, -1);
                  this.push(0.0);
               }
            }

            this.resetTargets(var3);
         }

         this.addByteCode((byte)42);
         this.classFile.add((byte)-76, ClassFileWriter.fullyQualifiedForm(this.name), "EmptyArray", "[Ljava/lang/Object;");
         if (var2 == 30) {
            this.addVirtualInvoke(var5.getClassName(), "constructDirect", var5.getDirectCallParameterSignature(), "Ljava/lang/Object;");
         } else {
            this.addVirtualInvoke(var5.getClassName(), "callDirect", var5.getDirectCallParameterSignature(), "Ljava/lang/Object;");
         }

         int var12 = this.acquireLabel();
         this.addByteCode((byte)-89, var12);
         this.markLabel(var6, var9);
         this.addByteCode((byte)87);
         this.visitRegularCall(var1, var2, var4, true);
         this.markLabel(var12);
      } else {
         this.visitRegularCall(var1, var2, var3, false);
      }

   }

   private void visitEnterWith(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      this.aload(this.variableObjectLocal);
      this.addScriptRuntimeInvoke("enterWith", "(Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Lorg/mozilla/javascript/Scriptable;");
      this.astore(this.variableObjectLocal);
   }

   private void visitEnumDone(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      Node var3 = (Node)var1.getProp(4);
      Integer var4 = (Integer)var3.getProp(7);
      this.releaseWordLocal(var4.shortValue());
   }

   private void visitEnumInit(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      this.aload(this.variableObjectLocal);
      this.addScriptRuntimeInvoke("initEnum", "(Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Ljava/util/Enumeration;");
      short var3 = this.getNewWordLocal();
      this.astore(var3);
      var1.putProp(7, new Integer(var3));
   }

   private void visitEnumNext(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      Node var3 = (Node)var1.getProp(4);
      Integer var4 = (Integer)var3.getProp(7);
      this.aload(var4.shortValue());
      this.addScriptRuntimeInvoke("nextEnum", "(Ljava/util/Enumeration;)", "Ljava/lang/Object;");
   }

   private void visitEqOp(Node var1, Node var2, Node var3, int var4, int var5) {
      int var6 = var1.getInt();
      Node var7 = var2.getNextSibling();
      if (var4 == -1) {
         if (var7.getType() == 109 && var7.getInt() == 49) {
            this.generateCodeFromNode(var2, var1, -1, -1);
            this.addByteCode((byte)89);
            this.addByteCode((byte)-58, 15);
            this.pushUndefined();
            this.addByteCode((byte)-91, 10);
            if (var6 != 14 && var6 != 53) {
               this.classFile.add((byte)-78, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
            } else {
               this.classFile.add((byte)-78, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
            }

            this.addByteCode((byte)-89, 7);
            this.addByteCode((byte)87);
            if (var6 != 14 && var6 != 53) {
               this.classFile.add((byte)-78, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
            } else {
               this.classFile.add((byte)-78, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
            }

            return;
         }

         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var2.getNextSibling(), var1, -1, -1);
         String var8;
         switch (var6) {
            case 14:
               var8 = this.version == 120 ? "seqB" : "eqB";
               break;
            case 15:
               var8 = this.version == 120 ? "sneB" : "neB";
               break;
            case 53:
               var8 = "seqB";
               break;
            case 54:
               var8 = "sneB";
               break;
            default:
               var8 = null;
               this.badTree();
         }

         this.addScriptRuntimeInvoke(var8, "(Ljava/lang/Object;Ljava/lang/Object;)", "Ljava/lang/Boolean;");
      } else {
         if (var7.getType() == 109 && var7.getInt() == 49) {
            this.generateCodeFromNode(var2, var1, -1, -1);
            boolean var14 = var2.getType() == 72;
            if (!var14) {
               this.addByteCode((byte)89);
            }

            int var12 = this.acquireLabel();
            short var16;
            if (var6 != 14 && var6 != 53) {
               this.addByteCode((byte)-58, var14 ? var5 : var12);
               var16 = this.classFile.getStackTop();
               if (var14) {
                  this.generateCodeFromNode(var2, var1, -1, -1);
               }

               this.pushUndefined();
               this.addByteCode((byte)-91, var5);
               this.addByteCode((byte)-89, var4);
               if (!var14) {
                  this.markLabel(var12, var16);
                  this.addByteCode((byte)87);
                  this.addByteCode((byte)-89, var5);
               }
            } else {
               this.addByteCode((byte)-58, var14 ? var4 : var12);
               var16 = this.classFile.getStackTop();
               if (var14) {
                  this.generateCodeFromNode(var2, var1, -1, -1);
               }

               this.pushUndefined();
               this.addByteCode((byte)-91, var4);
               this.addByteCode((byte)-89, var5);
               if (!var14) {
                  this.markLabel(var12, var16);
                  this.addByteCode((byte)87);
                  this.addByteCode((byte)-89, var4);
               }
            }

            return;
         }

         Node var13 = var2.getNextSibling();
         Number var9 = this.nodeIsConvertToObjectOfNumber(var13);
         OptLocalVariable var10;
         if (this.nodeIsDirectCallParameter(var2) && var9 != null) {
            var10 = (OptLocalVariable)var2.getProp(24);
            this.aload(var10.getJRegister());
            this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
            int var11 = this.acquireLabel();
            this.addByteCode((byte)-90, var11);
            this.dload((short)(var10.getJRegister() + 1));
            this.push(var9.doubleValue());
            this.addByteCode((byte)-105);
            if (var6 == 14) {
               this.addByteCode((byte)-103, var4);
            } else {
               this.addByteCode((byte)-102, var4);
            }

            this.addByteCode((byte)-89, var5);
            this.markLabel(var11);
         }

         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var13, var1, -1, -1);
         String var15;
         switch (var6) {
            case 14:
               var15 = this.version == 120 ? "shallowEq" : "eq";
               this.addScriptRuntimeInvoke(var15, "(Ljava/lang/Object;Ljava/lang/Object;)", "Z");
               break;
            case 15:
               var15 = this.version == 120 ? "shallowNeq" : "neq";
               this.addOptRuntimeInvoke(var15, "(Ljava/lang/Object;Ljava/lang/Object;)", "Z");
               break;
            case 53:
               var15 = "shallowEq";
               this.addScriptRuntimeInvoke(var15, "(Ljava/lang/Object;Ljava/lang/Object;)", "Z");
               break;
            case 54:
               var15 = "shallowNeq";
               this.addOptRuntimeInvoke(var15, "(Ljava/lang/Object;Ljava/lang/Object;)", "Z");
               break;
            default:
               var10 = null;
               this.badTree();
         }

         this.addByteCode((byte)-102, var4);
         this.addByteCode((byte)-89, var5);
      }

   }

   private void visitFunction(Node var1, boolean var2) {
      this.aload(this.variableObjectLocal);
      Short var3 = (Short)var1.getProp(5);
      this.aload(this.funObjLocal);
      this.classFile.add((byte)-76, "org/mozilla/javascript/NativeFunction", "nestedFunctions", "[Lorg/mozilla/javascript/NativeFunction;");
      this.push((long)var3);
      this.addByteCode((byte)50);
      this.addVirtualInvoke("java/lang/Object", "getClass", "()", "Ljava/lang/Class;");
      this.aload(this.contextLocal);
      this.addByteCode((byte)16, var2 ? 1 : 0);
      this.addScriptRuntimeInvoke("createFunctionObject", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Class;Lorg/mozilla/javascript/Context;Z)", "Lorg/mozilla/javascript/NativeFunction;");
   }

   private void visitGOTO(Node var1, int var2, Node var3) {
      Node var4 = (Node)var1.getProp(1);
      Object var5 = var4.getProp(20);
      int var6;
      if (var5 == null) {
         var6 = this.acquireLabel();
         var4.putProp(20, new Integer(var6));
      } else {
         var6 = (Integer)var5;
      }

      int var7 = this.acquireLabel();
      if (var2 != 7 && var2 != 8) {
         while(var3 != null) {
            this.generateCodeFromNode(var3, var1, -1, -1);
            var3 = var3.getNextSibling();
         }

         if (var2 == 143) {
            this.addByteCode((byte)-88, var6);
         } else {
            this.addByteCode((byte)-89, var6);
         }
      } else if (var3 == null) {
         this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
         if (var2 == 7) {
            this.addByteCode((byte)-102, var6);
         } else {
            this.addByteCode((byte)-103, var6);
         }
      } else {
         if (var2 == 7) {
            this.generateCodeFromNode(var3, var1, var6, var7);
         } else {
            this.generateCodeFromNode(var3, var1, var7, var6);
         }

         if ((var3.getType() != 105 || var3.getInt() != 129) && var3.getType() != 101 && var3.getType() != 100 && var3.getType() != 103 && var3.getType() != 102) {
            this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
            if (var2 == 7) {
               this.addByteCode((byte)-102, var6);
            } else {
               this.addByteCode((byte)-103, var6);
            }
         }
      }

      this.markLabel(var7);
   }

   private void visitGOTOingRelOp(Node var1, Node var2, Node var3, int var4, int var5) {
      int var6 = var1.getInt();
      Integer var7 = (Integer)var1.getProp(26);
      if (var7 != null && var7 == 0) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var2.getNextSibling(), var1, -1, -1);
         this.genSimpleCompare(var6, var4, var5);
      } else if (var6 == 64) {
         this.aload(this.variableObjectLocal);
         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var2.getNextSibling(), var1, -1, -1);
         this.addScriptRuntimeInvoke("instanceOf", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;)", "Z");
         this.addByteCode((byte)-102, var4);
         this.addByteCode((byte)-89, var5);
      } else if (var6 == 63) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var2.getNextSibling(), var1, -1, -1);
         this.aload(this.variableObjectLocal);
         this.addScriptRuntimeInvoke("in", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Z");
         this.addByteCode((byte)-102, var4);
         this.addByteCode((byte)-89, var5);
      } else {
         Node var8 = var2.getNextSibling();
         boolean var9 = this.nodeIsDirectCallParameter(var2);
         boolean var10 = this.nodeIsDirectCallParameter(var8);
         if (var9 || var10) {
            OptLocalVariable var11;
            int var12;
            if (var9) {
               if (var10) {
                  var11 = (OptLocalVariable)var2.getProp(24);
                  this.aload(var11.getJRegister());
                  this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
                  var12 = this.acquireLabel();
                  this.addByteCode((byte)-90, var12);
                  OptLocalVariable var13 = (OptLocalVariable)var8.getProp(24);
                  this.aload(var13.getJRegister());
                  this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
                  this.addByteCode((byte)-90, var12);
                  this.dload((short)(var11.getJRegister() + 1));
                  this.dload((short)(var13.getJRegister() + 1));
                  this.genSimpleCompare(var6, var4, var5);
                  this.markLabel(var12);
               } else if (var7 != null && var7 == 2) {
                  var11 = (OptLocalVariable)var2.getProp(24);
                  this.aload(var11.getJRegister());
                  this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
                  var12 = this.acquireLabel();
                  this.addByteCode((byte)-90, var12);
                  this.dload((short)(var11.getJRegister() + 1));
                  this.generateCodeFromNode(var8, var1, -1, -1);
                  this.genSimpleCompare(var6, var4, var5);
                  this.markLabel(var12);
               }
            } else if (var7 != null && var7 == 1) {
               var11 = (OptLocalVariable)var8.getProp(24);
               this.aload(var11.getJRegister());
               this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
               var12 = this.acquireLabel();
               this.addByteCode((byte)-90, var12);
               this.generateCodeFromNode(var2, var1, -1, -1);
               this.dload((short)(var11.getJRegister() + 1));
               this.genSimpleCompare(var6, var4, var5);
               this.markLabel(var12);
            }
         }

         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var8, var1, -1, -1);
         if (var7 == null) {
            if (var6 == 19 || var6 == 18) {
               this.addByteCode((byte)95);
            }

            String var16 = var6 != 16 && var6 != 18 ? "cmp_LE" : "cmp_LT";
            this.addScriptRuntimeInvoke(var16, "(Ljava/lang/Object;Ljava/lang/Object;)", "I");
         } else {
            boolean var14 = var7 == 1;
            if (var6 == 19 || var6 == 18) {
               if (var14) {
                  this.addByteCode((byte)91);
                  this.addByteCode((byte)87);
                  var14 = false;
               } else {
                  this.addByteCode((byte)93);
                  this.addByteCode((byte)88);
                  var14 = true;
               }
            }

            String var15 = var6 != 16 && var6 != 18 ? "cmp_LE" : "cmp_LT";
            if (var14) {
               this.addOptRuntimeInvoke(var15, "(DLjava/lang/Object;)", "I");
            } else {
               this.addOptRuntimeInvoke(var15, "(Ljava/lang/Object;D)", "I");
            }
         }

         this.addByteCode((byte)-102, var4);
         this.addByteCode((byte)-89, var5);
      }

   }

   private void visitGetProp(Node var1, Node var2) {
      String var3 = (String)var1.getProp(19);
      if (var3 != null) {
         while(var2 != null) {
            this.generateCodeFromNode(var2, var1, -1, -1);
            var2 = var2.getNextSibling();
         }

         this.aload(this.variableObjectLocal);
         String var5 = null;
         if (var3.equals("__proto__")) {
            var5 = "getProto";
         } else if (var3.equals("__parent__")) {
            var5 = "getParent";
         } else {
            this.badTree();
         }

         this.addScriptRuntimeInvoke(var5, "(Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Lorg/mozilla/javascript/Scriptable;");
      } else {
         Node var4 = var2.getNextSibling();
         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var4, var1, -1, -1);
         if (var4.getType() == 46) {
            if ((var2.getType() != 109 || var2.getInt() != 50) && (var2.getType() != 69 || var2.getFirstChild().getType() != 109 || var2.getFirstChild().getInt() != 50)) {
               this.aload(this.variableObjectLocal);
               this.addScriptRuntimeInvoke("getProp", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            } else {
               this.aload(this.variableObjectLocal);
               this.addOptRuntimeInvoke("thisGet", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            }
         } else {
            this.aload(this.variableObjectLocal);
            this.addScriptRuntimeInvoke("getProp", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
         }

      }
   }

   private void visitGetVar(OptLocalVariable var1, boolean var2, String var3) {
      if (this.hasVarsInRegs && var1 == null) {
         var1 = (OptLocalVariable)this.vars.getVariable(var3);
      }

      if (var1 != null) {
         if (var1.getJRegister() == -1) {
            if (var1.isNumber()) {
               var1.assignJRegister(this.getNewWordPairLocal());
            } else {
               var1.assignJRegister(this.getNewWordLocal());
            }
         }

         if (var1.isParameter() && this.inDirectCallFunction && !this.itsForcedObjectParameters) {
            int var4;
            int var5;
            if (var2) {
               this.aload(var1.getJRegister());
               this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
               var4 = this.acquireLabel();
               var5 = this.acquireLabel();
               this.addByteCode((byte)-91, var4);
               this.aload(var1.getJRegister());
               this.addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)", "D");
               this.addByteCode((byte)-89, var5);
               this.markLabel(var4);
               this.dload((short)(var1.getJRegister() + 1));
               this.markLabel(var5);
            } else {
               this.aload(var1.getJRegister());
               this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
               var4 = this.acquireLabel();
               var5 = this.acquireLabel();
               this.addByteCode((byte)-91, var4);
               this.aload(var1.getJRegister());
               this.addByteCode((byte)-89, var5);
               this.markLabel(var4);
               this.addByteCode((byte)-69, "java/lang/Double");
               this.addByteCode((byte)89);
               this.dload((short)(var1.getJRegister() + 1));
               this.addDoubleConstructor();
               this.markLabel(var5);
            }
         } else if (var1.isNumber()) {
            this.dload(var1.getJRegister());
         } else {
            this.aload(var1.getJRegister());
         }

      } else {
         this.aload(this.variableObjectLocal);
         this.push(var3);
         this.aload(this.variableObjectLocal);
         this.addScriptRuntimeInvoke("getProp", "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
      }
   }

   private void visitIncDec(Node var1, boolean var2) {
      Node var3 = var1.getFirstChild();
      OptLocalVariable var4;
      if (var1.getProp(26) != null) {
         var4 = (OptLocalVariable)var3.getProp(24);
         if (var4.getJRegister() == -1) {
            var4.assignJRegister(this.getNewWordPairLocal());
         }

         this.dload(var4.getJRegister());
         this.addByteCode((byte)92);
         this.push(1.0);
         this.addByteCode((byte)(var2 ? 99 : 103));
         this.dstore(var4.getJRegister());
      } else {
         var4 = (OptLocalVariable)var3.getProp(24);
         String var5 = var2 ? "postIncrement" : "postDecrement";
         if (this.hasVarsInRegs && var3.getType() == 72) {
            if (var4 == null) {
               var4 = (OptLocalVariable)this.vars.getVariable(var3.getString());
            }

            if (var4.getJRegister() == -1) {
               var4.assignJRegister(this.getNewWordLocal());
            }

            this.aload(var4.getJRegister());
            this.addByteCode((byte)89);
            this.addScriptRuntimeInvoke(var5, "(Ljava/lang/Object;)", "Ljava/lang/Object;");
            this.astore(var4.getJRegister());
         } else {
            Node var6;
            if (var3.getType() == 39) {
               var6 = var3.getFirstChild();
               this.generateCodeFromNode(var6, var1, -1, -1);
               this.generateCodeFromNode(var6.getNextSibling(), var1, -1, -1);
               this.aload(this.variableObjectLocal);
               this.addScriptRuntimeInvoke(var5, "(Ljava/lang/Object;Ljava/lang/String;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            } else if (var3.getType() == 41) {
               var5 = var5 + "Elem";
               var6 = var3.getFirstChild();
               this.generateCodeFromNode(var6, var1, -1, -1);
               this.generateCodeFromNode(var6.getNextSibling(), var1, -1, -1);
               this.aload(this.variableObjectLocal);
               this.addScriptRuntimeInvoke(var5, "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
            } else {
               this.aload(this.variableObjectLocal);
               this.push(var3.getString());
               this.addScriptRuntimeInvoke(var5, "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)", "Ljava/lang/Object;");
            }
         }
      }

   }

   private void visitLeaveWith(Node var1, Node var2) {
      this.aload(this.variableObjectLocal);
      this.addScriptRuntimeInvoke("leaveWith", "(Lorg/mozilla/javascript/Scriptable;)", "Lorg/mozilla/javascript/Scriptable;");
      this.astore(this.variableObjectLocal);
   }

   private void visitLiteral(Node var1) {
      if (var1.getType() == 46) {
         this.push(var1.getString());
      } else {
         Object var2 = (Number)var1.getDatum();
         if (var1.getProp(26) != null) {
            this.push(((Number)var2).doubleValue());
         } else {
            String var3 = "";
            String var4 = "";
            boolean var5 = false;
            if (var2 instanceof Float) {
               var2 = new Double((double)((Number)var2).floatValue());
            }

            if (var2 instanceof Integer) {
               var3 = "Integer";
               var4 = "I";
               var5 = true;
            } else if (var2 instanceof Double) {
               var3 = "Double";
               var4 = "D";
            } else if (var2 instanceof Byte) {
               var3 = "Byte";
               var4 = "B";
               var5 = true;
            } else {
               if (!(var2 instanceof Short)) {
                  throw Context.reportRuntimeError("NumberNode contains unsupported Number type: " + var2.getClass().getName());
               }

               var3 = "Short";
               var4 = "S";
               var5 = true;
            }

            if (this.itsConstantList.itsTop >= 2000) {
               this.addByteCode((byte)-69, "java/lang/" + var3);
               this.addByteCode((byte)89);
               if (var5) {
                  this.push(((Number)var2).longValue());
               } else {
                  this.push(((Number)var2).doubleValue());
               }

               this.addSpecialInvoke("java/lang/" + var3, "<init>", "(" + var4 + ")", "V");
            } else {
               this.classFile.add((byte)-78, ClassFileWriter.fullyQualifiedForm(this.name), "jsK_" + this.itsConstantList.addConstant(var3, var4, (Number)var2, var5), "Ljava/lang/" + var3 + ";");
            }
         }
      }

   }

   private void visitName(Node var1) {
      this.aload(this.variableObjectLocal);
      this.push(var1.getString());
      this.addScriptRuntimeInvoke("name", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)", "Ljava/lang/Object;");
   }

   private void visitNewLocal(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      short var3 = this.getLocalFromNode(var1);
      this.addByteCode((byte)89);
      this.astore(var3);
   }

   private void visitNewTemp(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      short var3 = this.getLocalFromNode(var1);
      this.addByteCode((byte)89);
      this.astore(var3);
      Integer var4 = (Integer)var1.getProp(11);
      if (var4 == null || var4 == 0) {
         this.releaseWordLocal(var3);
      }

   }

   private void visitObject(Node var1) {
      Node var2 = (Node)var1.getProp(12);
      String var3 = (String)var2.getProp(12);
      this.aload(this.funObjLocal);
      this.classFile.add((byte)-76, ClassFileWriter.fullyQualifiedForm(this.name), var3, "Lorg/mozilla/javascript/regexp/NativeRegExp;");
   }

   private void visitPrimary(Node var1) {
      int var2 = var1.getInt();
      switch (var2) {
         case 49:
            this.addByteCode((byte)1);
            break;
         case 50:
            this.aload(this.thisObjLocal);
            break;
         case 51:
            this.classFile.add((byte)-78, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
            break;
         case 52:
            this.classFile.add((byte)-78, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
            break;
         case 74:
            this.pushUndefined();
            break;
         case 87:
            this.classFile.add((byte)42);
            break;
         default:
            this.badTree();
      }

   }

   private void visitRegularCall(Node var1, int var2, Node var3, boolean var4) {
      OptFunctionNode var5 = (OptFunctionNode)var1.getProp(27);
      Node var6 = var3;
      int var7 = 0;

      int var8;
      for(var8 = var2 == 30 ? 1 : 2; var3 != null; var3 = var3.getNextSibling()) {
         ++var7;
      }

      var3 = var6;
      int var9 = -var8;
      if (var4 && var6 != null) {
         var3 = var6.getNextSibling();
         ++var9;
         this.aload(this.contextLocal);
         this.addByteCode((byte)95);
      } else {
         this.aload(this.contextLocal);
      }

      if (var4 && var2 == 30) {
         this.constructArgArray(var7 - var8);
      }

      boolean var10 = var1.getProp(30) != null;
      boolean var11 = false;
      String var12 = null;
      if (var2 != 30) {
         var12 = this.getSimpleCallName(var1);
         if (var12 != null && !var10) {
            var11 = true;
            this.push(var12);
            this.aload(this.variableObjectLocal);
            var3 = var3.getNextSibling().getNextSibling();
            var9 = 0;
            this.push((long)(var7 - var8));
            this.addByteCode((byte)-67, "java/lang/Object");
         }
      }

      for(; var3 != null; var3 = var3.getNextSibling()) {
         if (var9 < 0) {
            this.generateCodeFromNode(var3, var1, -1, -1);
         } else {
            this.addByteCode((byte)89);
            this.push((long)var9);
            if (var5 != null) {
               boolean var13 = false;
               if (var3.getType() == 72 && this.inDirectCallFunction) {
                  OptLocalVariable var14 = (OptLocalVariable)var3.getProp(24);
                  if (var14.isParameter()) {
                     var3.putProp(26, (Object)null);
                     this.generateCodeFromNode(var3, var1, -1, -1);
                     var13 = true;
                  }
               }

               if (!var13) {
                  Integer var18 = (Integer)var3.getProp(26);
                  if (var18 != null && var18 == 0) {
                     this.addByteCode((byte)-69, "java/lang/Double");
                     this.addByteCode((byte)89);
                     this.generateCodeFromNode(var3, var1, -1, -1);
                     this.addDoubleConstructor();
                  } else {
                     this.generateCodeFromNode(var3, var1, -1, -1);
                  }
               }
            } else {
               this.generateCodeFromNode(var3, var1, -1, -1);
            }

            this.addByteCode((byte)83);
         }

         ++var9;
         if (var9 == 0) {
            this.constructArgArray(var7 - var8);
         }
      }

      String var15;
      String var16;
      String var17;
      String var19;
      if (var10) {
         var17 = "org/mozilla/javascript/ScriptRuntime";
         var19 = "newObjectSpecial";
         var15 = "callSpecial";
         if (var2 != 30) {
            var16 = "(Lorg/mozilla/javascript/Context;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;I)";
            this.aload(this.thisObjLocal);
            this.aload(this.variableObjectLocal);
            this.push(this.itsSourceFile == null ? "" : this.itsSourceFile);
            this.push((long)this.itsLineNumber);
         } else {
            var16 = "(Lorg/mozilla/javascript/Context;Ljava/lang/Object;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)";
            this.aload(this.variableObjectLocal);
         }
      } else {
         var19 = "newObject";
         if (var11) {
            var16 = "(Lorg/mozilla/javascript/Context;Ljava/lang/String;Lorg/mozilla/javascript/Scriptable;[Ljava/lang/Object;)";
            var15 = "callSimple";
            var17 = "org/mozilla/javascript/optimizer/OptRuntime";
         } else {
            this.aload(this.variableObjectLocal);
            if (var2 == 30) {
               var16 = "(Lorg/mozilla/javascript/Context;Ljava/lang/Object;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)";
            } else {
               var16 = "(Lorg/mozilla/javascript/Context;Ljava/lang/Object;Ljava/lang/Object;[Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)";
            }

            var15 = "call";
            var17 = "org/mozilla/javascript/ScriptRuntime";
         }
      }

      if (var2 == 30) {
         this.addStaticInvoke(var17, var19, var16, "Lorg/mozilla/javascript/Scriptable;");
      } else {
         this.addStaticInvoke(var17, var15, var16, "Ljava/lang/Object;");
      }

   }

   private void visitRelOp(Node var1, Node var2, Node var3) {
      int var4 = var1.getInt();
      Integer var5 = (Integer)var1.getProp(26);
      if ((var5 == null || var5 != 0) && var4 != 64 && var4 != 63) {
         String var8 = var4 != 16 && var4 != 18 ? "cmp_LEB" : "cmp_LTB";
         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var2.getNextSibling(), var1, -1, -1);
         if (var5 == null) {
            if (var4 == 19 || var4 == 18) {
               this.addByteCode((byte)95);
            }

            this.addScriptRuntimeInvoke(var8, "(Ljava/lang/Object;Ljava/lang/Object;)", "Ljava/lang/Boolean;");
         } else {
            boolean var9 = var5 == 1;
            if (var4 == 19 || var4 == 18) {
               if (var9) {
                  this.addByteCode((byte)91);
                  this.addByteCode((byte)87);
                  var9 = false;
               } else {
                  this.addByteCode((byte)93);
                  this.addByteCode((byte)88);
                  var9 = true;
               }
            }

            if (var9) {
               this.addOptRuntimeInvoke(var8, "(DLjava/lang/Object;)", "Ljava/lang/Boolean;");
            } else {
               this.addOptRuntimeInvoke(var8, "(Ljava/lang/Object;D)", "Ljava/lang/Boolean;");
            }
         }
      } else {
         if (var4 == 64) {
            this.aload(this.variableObjectLocal);
         }

         this.generateCodeFromNode(var2, var1, -1, -1);
         this.generateCodeFromNode(var2.getNextSibling(), var1, -1, -1);
         int var6 = this.acquireLabel();
         int var7 = this.acquireLabel();
         if (var4 == 64) {
            this.addScriptRuntimeInvoke("instanceOf", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/Object;)", "Z");
            this.addByteCode((byte)-102, var6);
         } else if (var4 == 63) {
            this.aload(this.variableObjectLocal);
            this.addScriptRuntimeInvoke("in", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Z");
            this.addByteCode((byte)-102, var6);
         } else {
            this.genSimpleCompare(var4, var6, -1);
         }

         this.classFile.add((byte)-78, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
         this.addByteCode((byte)-89, var7);
         this.markLabel(var6);
         this.classFile.add((byte)-78, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
         this.markLabel(var7);
         this.classFile.adjustStackTop(-1);
      }

   }

   private void visitReturn(Node var1, Node var2) {
      this.visitStatement(var1);
      if (var2 != null) {
         do {
            this.generateCodeFromNode(var2, var1, -1, -1);
            var2 = var2.getNextSibling();
         } while(var2 != null);
      } else if (this.inFunction) {
         this.pushUndefined();
      } else {
         this.aload(this.scriptResultLocal);
      }

      if (this.epilogueLabel == -1) {
         this.epilogueLabel = this.classFile.acquireLabel();
      }

      this.addByteCode((byte)-89, this.epilogueLabel);
   }

   private void visitSetName(Node var1, Node var2) {
      String var3;
      for(var3 = var1.getFirstChild().getString(); var2 != null; var2 = var2.getNextSibling()) {
         this.generateCodeFromNode(var2, var1, -1, -1);
      }

      this.aload(this.variableObjectLocal);
      this.push(var3);
      this.addScriptRuntimeInvoke("setName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)", "Ljava/lang/Object;");
   }

   private void visitSetProp(Node var1, Node var2) {
      String var3 = (String)var1.getProp(19);
      if (var3 != null) {
         while(var2 != null) {
            this.generateCodeFromNode(var2, var1, -1, -1);
            var2 = var2.getNextSibling();
         }

         this.aload(this.variableObjectLocal);
         String var4 = null;
         if (var3.equals("__proto__")) {
            var4 = "setProto";
         } else if (var3.equals("__parent__")) {
            var4 = "setParent";
         } else {
            this.badTree();
         }

         this.addScriptRuntimeInvoke(var4, "(Ljava/lang/Object;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
      } else {
         while(var2 != null) {
            this.generateCodeFromNode(var2, var1, -1, -1);
            var2 = var2.getNextSibling();
         }

         this.aload(this.variableObjectLocal);
         this.addScriptRuntimeInvoke("setProp", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;)", "Ljava/lang/Object;");
      }
   }

   private void visitSetVar(Node var1, Node var2, boolean var3) {
      OptLocalVariable var4 = (OptLocalVariable)var1.getProp(24);
      if (this.hasVarsInRegs && var4 == null) {
         var4 = (OptLocalVariable)this.vars.getVariable(var2.getString());
      }

      if (var4 != null) {
         this.generateCodeFromNode(var2.getNextSibling(), var1, -1, -1);
         if (var4.getJRegister() == -1) {
            if (var4.isNumber()) {
               var4.assignJRegister(this.getNewWordPairLocal());
            } else {
               var4.assignJRegister(this.getNewWordLocal());
            }
         }

         if (var4.isParameter() && this.inDirectCallFunction && !this.itsForcedObjectParameters) {
            if (var1.getProp(26) != null) {
               if (var3) {
                  this.addByteCode((byte)92);
               }

               this.aload(var4.getJRegister());
               this.classFile.add((byte)-78, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
               int var5 = this.acquireLabel();
               int var6 = this.acquireLabel();
               this.addByteCode((byte)-91, var5);
               this.addByteCode((byte)-69, "java/lang/Double");
               this.addByteCode((byte)89);
               this.addByteCode((byte)94);
               this.addByteCode((byte)88);
               this.addDoubleConstructor();
               this.astore(var4.getJRegister());
               this.addByteCode((byte)-89, var6);
               this.markLabel(var5);
               this.dstore((short)(var4.getJRegister() + 1));
               this.markLabel(var6);
            } else {
               if (var3) {
                  this.addByteCode((byte)89);
               }

               this.astore(var4.getJRegister());
            }
         } else if (var1.getProp(26) != null) {
            this.dstore(var4.getJRegister());
            if (var3) {
               this.dload(var4.getJRegister());
            }
         } else {
            this.astore(var4.getJRegister());
            if (var3) {
               this.aload(var4.getJRegister());
            }
         }

      } else {
         var2.setType(61);
         var1.setType(10);
         this.visitSetName(var1, var2);
         if (!var3) {
            this.addByteCode((byte)87);
         }

      }
   }

   private void visitStatement(Node var1) {
      Object var2 = var1.getDatum();
      if (var2 != null && var2 instanceof Number) {
         this.itsLineNumber = ((Number)var2).shortValue();
         if (this.itsLineNumber != -1) {
            this.classFile.addLineNumberEntry((short)this.itsLineNumber);
         }
      }
   }

   private void visitSwitch(Node var1, Node var2) {
      this.visitStatement(var1);

      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      short var3 = this.getNewWordLocal();
      this.astore(var3);
      Vector var4 = (Vector)var1.getProp(13);

      Node var6;
      Node var7;
      for(int var5 = 0; var5 < var4.size(); ++var5) {
         var6 = (Node)var4.elementAt(var5);
         var7 = var6.getFirstChild();
         this.generateCodeFromNode(var7, var6, -1, -1);
         this.aload(var3);
         this.addScriptRuntimeInvoke("seqB", "(Ljava/lang/Object;Ljava/lang/Object;)", "Ljava/lang/Boolean;");
         Node var8 = new Node(137);
         var6.replaceChild(var7, var8);
         this.generateGOTO(7, var8);
      }

      var6 = (Node)var1.getProp(14);
      if (var6 != null) {
         var7 = new Node(137);
         var6.getFirstChild().addChildToFront(var7);
         this.generateGOTO(6, var7);
      }

      var7 = (Node)var1.getProp(2);
      this.generateGOTO(6, var7);
   }

   private void visitTarget(Node var1) {
      Object var2 = var1.getProp(20);
      int var3;
      if (var2 == null) {
         var3 = this.markLabel(this.acquireLabel());
         var1.putProp(20, new Integer(var3));
      } else {
         var3 = (Integer)var2;
         this.markLabel(var3);
      }

   }

   private void visitThrow(Node var1, Node var2) {
      this.visitStatement(var1);

      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      this.addByteCode((byte)-69, "org/mozilla/javascript/JavaScriptException");
      this.addByteCode((byte)90);
      this.addByteCode((byte)95);
      this.addSpecialInvoke("org/mozilla/javascript/JavaScriptException", "<init>", "(Ljava/lang/Object;)", "V");
      this.addByteCode((byte)-65);
   }

   private void visitTryCatchFinally(Node var1, Node var2) {
      short var3 = this.getNewWordLocal();
      this.aload(this.variableObjectLocal);
      this.astore(var3);
      int var4 = this.markLabel(this.acquireLabel(), (short)1);
      this.visitStatement(var1);

      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      Node var5 = (Node)var1.getProp(1);
      Node var6 = (Node)var1.getProp(21);
      int var7 = this.acquireLabel();
      this.addByteCode((byte)-89, var7);
      int var8;
      int var10;
      if (var5 != null) {
         var8 = (Integer)var5.getProp(20);
         this.generateCatchBlock(0, var3, var8, var4);
         this.generateCatchBlock(1, var3, var8, var4);
         int var9 = this.classFile.markHandler(this.acquireLabel());
         var10 = this.getNewWordLocal();
         this.astore((short)var10);
         this.aload(var3);
         this.astore(this.variableObjectLocal);
         this.aload((short)var10);
         this.addVirtualInvoke("org/mozilla/javascript/EcmaError", "getErrorObject", "()", "Lorg/mozilla/javascript/Scriptable;");
         this.releaseWordLocal((short)var10);
         this.addByteCode((byte)-89, var8);
         this.classFile.addExceptionHandler(var4, var8, var9, "org/mozilla/javascript/EcmaError");
      }

      if (var6 != null) {
         var8 = this.classFile.markHandler(this.acquireLabel());
         this.aload(var3);
         this.astore(this.variableObjectLocal);
         short var10002 = this.itsLocalAllocationBase;
         this.itsLocalAllocationBase = (short)(var10002 + 1);
         short var11 = var10002;
         this.astore(var11);
         var10 = (Integer)var6.getProp(20);
         this.addByteCode((byte)-88, var10);
         this.aload(var11);
         this.addByteCode((byte)-65);
         this.classFile.addExceptionHandler(var4, var10, var8, (String)null);
      }

      this.releaseWordLocal(var3);
      this.markLabel(var7);
   }

   private void visitTypeof(Node var1, Node var2) {
      if (var1.getType() == 105) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         this.addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)", "Ljava/lang/String;");
      } else {
         String var3 = var1.getString();
         if (this.hasVarsInRegs) {
            OptLocalVariable var4 = (OptLocalVariable)this.vars.getVariable(var3);
            if (var4 != null) {
               if (var4.isNumber()) {
                  this.push("number");
                  return;
               }

               this.visitGetVar(var4, false, var3);
               this.addScriptRuntimeInvoke("typeof", "(Ljava/lang/Object;)", "Ljava/lang/String;");
               return;
            }
         }

         this.aload(this.variableObjectLocal);
         this.push(var3);
         this.addScriptRuntimeInvoke("typeofName", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/String;)", "Ljava/lang/String;");
      }
   }

   private void visitUnary(Node var1, Node var2, int var3, int var4) {
      int var5 = var1.getInt();
      switch (var5) {
         case 23:
         case 24:
            this.addByteCode((byte)-69, "java/lang/Double");
            this.addByteCode((byte)89);
            this.generateCodeFromNode(var2, var1, -1, -1);
            this.addScriptRuntimeInvoke("toNumber", "(Ljava/lang/Object;)", "D");
            if (var5 == 24) {
               this.addByteCode((byte)119);
            }

            this.addDoubleConstructor();
            break;
         case 28:
            this.addByteCode((byte)-69, "java/lang/Double");
            this.addByteCode((byte)89);
            this.generateCodeFromNode(var2, var1, -1, -1);
            this.addScriptRuntimeInvoke("toInt32", "(Ljava/lang/Object;)", "I");
            this.push(-1L);
            this.addByteCode((byte)-126);
            this.addByteCode((byte)-121);
            this.addDoubleConstructor();
            break;
         case 32:
            this.visitTypeof(var1, var2);
            break;
         case 129:
            if (var3 != -1) {
               this.generateCodeFromNode(var2, var1, var4, var3);
               if ((var2.getType() != 105 || var2.getInt() != 129) && var2.getType() != 101 && var2.getType() != 100 && var2.getType() != 103 && var2.getType() != 102) {
                  this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
                  this.addByteCode((byte)-102, var4);
                  this.addByteCode((byte)-89, var3);
               }
            } else {
               int var6 = this.acquireLabel();
               int var7 = this.acquireLabel();
               int var8 = this.acquireLabel();
               this.generateCodeFromNode(var2, var1, var6, var7);
               if ((var2.getType() != 105 || var2.getInt() != 129) && var2.getType() != 101 && var2.getType() != 100 && var2.getType() != 103 && var2.getType() != 102) {
                  this.addScriptRuntimeInvoke("toBoolean", "(Ljava/lang/Object;)", "Z");
                  this.addByteCode((byte)-103, var7);
                  this.addByteCode((byte)-89, var6);
               }

               this.markLabel(var6);
               this.classFile.add((byte)-78, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
               this.addByteCode((byte)-89, var8);
               this.markLabel(var7);
               this.classFile.add((byte)-78, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
               this.markLabel(var8);
               this.classFile.adjustStackTop(-1);
            }
            break;
         case 132:
            this.generateCodeFromNode(var2, var1, -1, -1);
            this.addByteCode((byte)87);
            this.pushUndefined();
            break;
         default:
            this.badTree();
      }

   }

   private void visitUseLocal(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      Node var3 = (Node)var1.getProp(7);
      short var4 = this.getLocalFromNode(var3);
      if (var1.getProp(1) != null) {
         this.addByteCode((byte)-87, var4);
      } else {
         this.aload(var4);
      }

   }

   private void visitUseTemp(Node var1, Node var2) {
      while(var2 != null) {
         this.generateCodeFromNode(var2, var1, -1, -1);
         var2 = var2.getNextSibling();
      }

      Node var3 = (Node)var1.getProp(6);
      short var4 = this.getLocalFromNode(var3);
      if (var1.getProp(1) != null) {
         this.addByteCode((byte)-87, var4);
      } else {
         this.aload(var4);
      }

      Integer var5 = (Integer)var3.getProp(11);
      if (var5 == null) {
         this.releaseWordLocal(var4);
      } else if (var5 < Integer.MAX_VALUE) {
         int var6 = var5 - 1;
         if (var6 == 0) {
            this.releaseWordLocal(var4);
         }

         var3.putProp(11, new Integer(var6));
      }

   }

   private void xop(byte var1, byte var2, short var3) {
      switch (var3) {
         case 0:
            this.addByteCode(var1);
            break;
         case 1:
            this.addByteCode((byte)(var1 + 1));
            break;
         case 2:
            this.addByteCode((byte)(var1 + 2));
            break;
         case 3:
            this.addByteCode((byte)(var1 + 3));
            break;
         default:
            if (var3 < 0 || var3 >= 32767) {
               throw new RuntimeException("bad local");
            }

            if (var3 < 127) {
               this.addByteCode(var2, (byte)var3);
            } else {
               this.addByteCode((byte)-60);
               this.addByteCode(var2);
               this.addByteCode((byte)(var3 >> 8));
               this.addByteCode((byte)(var3 & 255));
            }
      }

   }
}
