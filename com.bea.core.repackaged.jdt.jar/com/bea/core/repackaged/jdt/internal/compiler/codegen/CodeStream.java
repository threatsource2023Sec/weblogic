package com.bea.core.repackaged.jdt.internal.compiler.codegen;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ArrayAllocationExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ExplicitConstructorCall;
import com.bea.core.repackaged.jdt.internal.compiler.ast.Expression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FieldDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.FunctionalExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.LambdaExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ReferenceExpression;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeReference;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ArrayBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.IntersectionTypeBinding18;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.NestedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.VariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CodeStream {
   public static FieldBinding[] ImplicitThis = new FieldBinding[0];
   public static final int LABELS_INCREMENT = 5;
   public static final int LOCALS_INCREMENT = 10;
   public static final CompilationResult RESTART_IN_WIDE_MODE = new CompilationResult((char[])null, 0, 0, 0);
   public static final CompilationResult RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE = new CompilationResult((char[])null, 0, 0, 0);
   public int allLocalsCounter;
   public byte[] bCodeStream;
   public ClassFile classFile;
   public int classFileOffset;
   public ConstantPool constantPool;
   public int countLabels;
   public ExceptionLabel[] exceptionLabels = new ExceptionLabel[5];
   public int exceptionLabelsCounter;
   public int generateAttributes;
   static final int L_UNKNOWN = 0;
   static final int L_OPTIMIZABLE = 2;
   static final int L_CANNOT_OPTIMIZE = 4;
   public BranchLabel[] labels = new BranchLabel[5];
   public int lastEntryPC;
   public int lastAbruptCompletion;
   public int[] lineSeparatorPositions;
   public int lineNumberStart;
   public int lineNumberEnd;
   public LocalVariableBinding[] locals = new LocalVariableBinding[10];
   public int maxFieldCount;
   public int maxLocals;
   public AbstractMethodDeclaration methodDeclaration;
   public LambdaExpression lambdaExpression;
   public int[] pcToSourceMap = new int[24];
   public int pcToSourceMapSize;
   public int position;
   public boolean preserveUnusedLocals;
   public int stackDepth;
   public int stackMax;
   public int startingClassFileOffset;
   protected long targetLevel;
   public LocalVariableBinding[] visibleLocals = new LocalVariableBinding[10];
   int visibleLocalsCount;
   public boolean wideMode = false;

   public CodeStream(ClassFile givenClassFile) {
      this.targetLevel = givenClassFile.targetJDK;
      this.generateAttributes = givenClassFile.produceAttributes;
      if ((givenClassFile.produceAttributes & 2) != 0) {
         this.lineSeparatorPositions = givenClassFile.referenceBinding.scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions();
      }

   }

   public static int insertionIndex(int[] pcToSourceMap, int length, int pc) {
      int g = 0;
      int d = length - 2;
      int m = 0;

      while(g <= d) {
         m = (g + d) / 2;
         if ((m & 1) != 0) {
            --m;
         }

         int currentPC = pcToSourceMap[m];
         if (pc < currentPC) {
            d = m - 2;
         } else {
            if (pc <= currentPC) {
               return -1;
            }

            g = m + 2;
         }
      }

      if (pc < pcToSourceMap[m]) {
         return m;
      } else {
         return m + 2;
      }
   }

   public static final void sort(int[] tab, int lo0, int hi0, int[] result) {
      int lo = lo0;
      int hi = hi0;
      if (hi0 > lo0) {
         int mid = tab[lo0 + (hi0 - lo0) / 2];

         while(lo <= hi) {
            while(lo < hi0 && tab[lo] < mid) {
               ++lo;
            }

            while(hi > lo0 && tab[hi] > mid) {
               --hi;
            }

            if (lo <= hi) {
               swap(tab, lo, hi, result);
               ++lo;
               --hi;
            }
         }

         if (lo0 < hi) {
            sort(tab, lo0, hi, result);
         }

         if (lo < hi0) {
            sort(tab, lo, hi0, result);
         }
      }

   }

   private static final void swap(int[] a, int i, int j, int[] result) {
      int T = a[i];
      a[i] = a[j];
      a[j] = T;
      T = result[j];
      result[j] = result[i];
      result[i] = T;
   }

   public void aaload() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 50;
   }

   public void aastore() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 83;
   }

   public void aconst_null() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 1;
   }

   public void addDefinitelyAssignedVariables(Scope scope, int initStateIndex) {
      if ((this.generateAttributes & 28) != 0) {
         for(int i = 0; i < this.visibleLocalsCount; ++i) {
            LocalVariableBinding localBinding = this.visibleLocals[i];
            if (localBinding != null && this.isDefinitelyAssigned(scope, initStateIndex, localBinding) && (localBinding.initializationCount == 0 || localBinding.initializationPCs[(localBinding.initializationCount - 1 << 1) + 1] != -1)) {
               localBinding.recordInitializationStartPC(this.position);
            }
         }

      }
   }

   public void addLabel(BranchLabel aLabel) {
      if (this.countLabels == this.labels.length) {
         System.arraycopy(this.labels, 0, this.labels = new BranchLabel[this.countLabels + 5], 0, this.countLabels);
      }

      this.labels[this.countLabels++] = aLabel;
   }

   public void addVariable(LocalVariableBinding localBinding) {
   }

   public void addVisibleLocalVariable(LocalVariableBinding localBinding) {
      if ((this.generateAttributes & 28) != 0) {
         if (this.visibleLocalsCount >= this.visibleLocals.length) {
            System.arraycopy(this.visibleLocals, 0, this.visibleLocals = new LocalVariableBinding[this.visibleLocalsCount * 2], 0, this.visibleLocalsCount);
         }

         this.visibleLocals[this.visibleLocalsCount++] = localBinding;
      }
   }

   public void aload(int iArg) {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals <= iArg) {
         this.maxLocals = iArg + 1;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 25;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 25;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void aload_0() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals == 0) {
         this.maxLocals = 1;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 42;
   }

   public void aload_1() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals <= 1) {
         this.maxLocals = 2;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 43;
   }

   public void aload_2() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals <= 2) {
         this.maxLocals = 3;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 44;
   }

   public void aload_3() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals <= 3) {
         this.maxLocals = 4;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 45;
   }

   public void anewarray(TypeBinding typeBinding) {
      this.countLabels = 0;
      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -67;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
   }

   public void areturn() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -80;
      this.lastAbruptCompletion = this.position;
   }

   public void arrayAt(int typeBindingID) {
      switch (typeBindingID) {
         case 2:
            this.caload();
            break;
         case 3:
         case 5:
            this.baload();
            break;
         case 4:
            this.saload();
            break;
         case 6:
         default:
            this.aaload();
            break;
         case 7:
            this.laload();
            break;
         case 8:
            this.daload();
            break;
         case 9:
            this.faload();
            break;
         case 10:
            this.iaload();
      }

   }

   public void arrayAtPut(int elementTypeID, boolean valueRequired) {
      switch (elementTypeID) {
         case 2:
            if (valueRequired) {
               this.dup_x2();
            }

            this.castore();
            break;
         case 3:
         case 5:
            if (valueRequired) {
               this.dup_x2();
            }

            this.bastore();
            break;
         case 4:
            if (valueRequired) {
               this.dup_x2();
            }

            this.sastore();
            break;
         case 6:
         default:
            if (valueRequired) {
               this.dup_x2();
            }

            this.aastore();
            break;
         case 7:
            if (valueRequired) {
               this.dup2_x2();
            }

            this.lastore();
            break;
         case 8:
            if (valueRequired) {
               this.dup2_x2();
            }

            this.dastore();
            break;
         case 9:
            if (valueRequired) {
               this.dup_x2();
            }

            this.fastore();
            break;
         case 10:
            if (valueRequired) {
               this.dup_x2();
            }

            this.iastore();
      }

   }

   public void arraylength() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -66;
   }

   public void astore(int iArg) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= iArg) {
         this.maxLocals = iArg + 1;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 58;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 58;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void astore_0() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals == 0) {
         this.maxLocals = 1;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 75;
   }

   public void astore_1() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 1) {
         this.maxLocals = 2;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 76;
   }

   public void astore_2() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 2) {
         this.maxLocals = 3;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 77;
   }

   public void astore_3() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 3) {
         this.maxLocals = 4;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 78;
   }

   public void athrow() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -65;
      this.lastAbruptCompletion = this.position;
   }

   public void baload() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 51;
   }

   public void bastore() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 84;
   }

   public void bipush(byte b) {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 1 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      this.position += 2;
      this.bCodeStream[this.classFileOffset++] = 16;
      this.bCodeStream[this.classFileOffset++] = b;
   }

   public void caload() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 52;
   }

   public void castore() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 85;
   }

   public void checkcast(int baseId) {
      this.countLabels = 0;
      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -64;
      switch (baseId) {
         case 2:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangCharacterConstantPoolName));
            break;
         case 3:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangByteConstantPoolName));
            break;
         case 4:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangShortConstantPoolName));
            break;
         case 5:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangBooleanConstantPoolName));
         case 6:
         default:
            break;
         case 7:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangLongConstantPoolName));
            break;
         case 8:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangDoubleConstantPoolName));
            break;
         case 9:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangFloatConstantPoolName));
            break;
         case 10:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangIntegerConstantPoolName));
      }

   }

   public void checkcast(TypeBinding typeBinding) {
      this.checkcast((TypeReference)null, typeBinding, -1);
   }

   public void checkcast(TypeReference typeReference, TypeBinding typeBinding, int currentPosition) {
      this.countLabels = 0;
      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -64;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
   }

   public void d2f() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -112;
   }

   public void d2i() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -114;
   }

   public void d2l() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -113;
   }

   public void dadd() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 99;
   }

   public void daload() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 49;
   }

   public void dastore() {
      this.countLabels = 0;
      this.stackDepth -= 4;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 82;
   }

   public void dcmpg() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -104;
   }

   public void dcmpl() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -105;
   }

   public void dconst_0() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 14;
   }

   public void dconst_1() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 15;
   }

   public void ddiv() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 111;
   }

   public void decrStackSize(int offset) {
      this.stackDepth -= offset;
   }

   public void dload(int iArg) {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals < iArg + 2) {
         this.maxLocals = iArg + 2;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 24;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 24;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void dload_0() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals < 2) {
         this.maxLocals = 2;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 38;
   }

   public void dload_1() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals < 3) {
         this.maxLocals = 3;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 39;
   }

   public void dload_2() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals < 4) {
         this.maxLocals = 4;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 40;
   }

   public void dload_3() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.maxLocals < 5) {
         this.maxLocals = 5;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 41;
   }

   public void dmul() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 107;
   }

   public void dneg() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 119;
   }

   public void drem() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 115;
   }

   public void dreturn() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -81;
      this.lastAbruptCompletion = this.position;
   }

   public void dstore(int iArg) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals <= iArg + 1) {
         this.maxLocals = iArg + 2;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 57;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 57;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void dstore_0() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 2) {
         this.maxLocals = 2;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 71;
   }

   public void dstore_1() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 3) {
         this.maxLocals = 3;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 72;
   }

   public void dstore_2() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 4) {
         this.maxLocals = 4;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 73;
   }

   public void dstore_3() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 5) {
         this.maxLocals = 5;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 74;
   }

   public void dsub() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 103;
   }

   public void dup() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 89;
   }

   public void dup_x1() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 90;
   }

   public void dup_x2() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 91;
   }

   public void dup2() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 92;
   }

   public void dup2_x1() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 93;
   }

   public void dup2_x2() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 94;
   }

   public void exitUserScope(BlockScope currentScope) {
      if ((this.generateAttributes & 28) != 0) {
         int index = this.visibleLocalsCount - 1;

         while(true) {
            while(index >= 0) {
               LocalVariableBinding visibleLocal = this.visibleLocals[index];
               if (visibleLocal != null && visibleLocal.declaringScope == currentScope) {
                  if (visibleLocal.initializationCount > 0) {
                     visibleLocal.recordInitializationEndPC(this.position);
                  }

                  this.visibleLocals[index--] = null;
               } else {
                  --index;
               }
            }

            return;
         }
      }
   }

   public void exitUserScope(BlockScope currentScope, LocalVariableBinding binding) {
      if ((this.generateAttributes & 28) != 0) {
         int index = this.visibleLocalsCount - 1;

         while(true) {
            while(index >= 0) {
               LocalVariableBinding visibleLocal = this.visibleLocals[index];
               if (visibleLocal != null && visibleLocal.declaringScope == currentScope && visibleLocal != binding) {
                  if (visibleLocal.initializationCount > 0) {
                     visibleLocal.recordInitializationEndPC(this.position);
                  }

                  this.visibleLocals[index--] = null;
               } else {
                  --index;
               }
            }

            return;
         }
      }
   }

   public void f2d() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -115;
   }

   public void f2i() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -117;
   }

   public void f2l() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -116;
   }

   public void fadd() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 98;
   }

   public void faload() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 48;
   }

   public void fastore() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 81;
   }

   public void fcmpg() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -106;
   }

   public void fcmpl() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -107;
   }

   public void fconst_0() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 11;
   }

   public void fconst_1() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 12;
   }

   public void fconst_2() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 13;
   }

   public void fdiv() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 110;
   }

   public void fieldAccess(byte opcode, FieldBinding fieldBinding, TypeBinding declaringClass) {
      if (declaringClass == null) {
         declaringClass = fieldBinding.declaringClass;
      }

      if ((((TypeBinding)declaringClass).tagBits & 2048L) != 0L) {
         Util.recordNestedType(this.classFile, (TypeBinding)declaringClass);
      }

      TypeBinding returnType = fieldBinding.type;
      byte returnTypeSize;
      switch (returnType.id) {
         case 7:
         case 8:
            returnTypeSize = 2;
            break;
         default:
            returnTypeSize = 1;
      }

      this.fieldAccess(opcode, returnTypeSize, ((TypeBinding)declaringClass).constantPoolName(), fieldBinding.name, returnType.signature());
   }

   private void fieldAccess(byte opcode, int returnTypeSize, char[] declaringClass, char[] fieldName, char[] signature) {
      this.countLabels = 0;
      switch (opcode) {
         case -78:
            if (returnTypeSize == 2) {
               this.stackDepth += 2;
            } else {
               ++this.stackDepth;
            }
            break;
         case -77:
            if (returnTypeSize == 2) {
               this.stackDepth -= 2;
            } else {
               --this.stackDepth;
            }
            break;
         case -76:
            if (returnTypeSize == 2) {
               ++this.stackDepth;
            }
            break;
         case -75:
            if (returnTypeSize == 2) {
               this.stackDepth -= 3;
            } else {
               this.stackDepth -= 2;
            }
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = opcode;
      this.writeUnsignedShort(this.constantPool.literalIndexForField(declaringClass, fieldName, signature));
   }

   public void fload(int iArg) {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= iArg) {
         this.maxLocals = iArg + 1;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 23;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 23;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void fload_0() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals == 0) {
         this.maxLocals = 1;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 34;
   }

   public void fload_1() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= 1) {
         this.maxLocals = 2;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 35;
   }

   public void fload_2() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= 2) {
         this.maxLocals = 3;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 36;
   }

   public void fload_3() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= 3) {
         this.maxLocals = 4;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 37;
   }

   public void fmul() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 106;
   }

   public void fneg() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 118;
   }

   public void frem() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 114;
   }

   public void freturn() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -82;
      this.lastAbruptCompletion = this.position;
   }

   public void fstore(int iArg) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= iArg) {
         this.maxLocals = iArg + 1;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 56;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 56;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void fstore_0() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals == 0) {
         this.maxLocals = 1;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 67;
   }

   public void fstore_1() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 1) {
         this.maxLocals = 2;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 68;
   }

   public void fstore_2() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 2) {
         this.maxLocals = 3;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 69;
   }

   public void fstore_3() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 3) {
         this.maxLocals = 4;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 70;
   }

   public void fsub() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 102;
   }

   public void generateBoxingConversion(int unboxedTypeID) {
      switch (unboxedTypeID) {
         case 2:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 1, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.ValueOf, ConstantPool.charCharacterSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x1();
               this.swap();
               this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.Init, ConstantPool.CharConstrSignature);
            }
            break;
         case 3:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 1, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.ValueOf, ConstantPool.byteByteSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x1();
               this.swap();
               this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.Init, ConstantPool.ByteConstrSignature);
            }
            break;
         case 4:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 1, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.ValueOf, ConstantPool.shortShortSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x1();
               this.swap();
               this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.Init, ConstantPool.ShortConstrSignature);
            }
            break;
         case 5:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 1, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.ValueOf, ConstantPool.booleanBooleanSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x1();
               this.swap();
               this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.Init, ConstantPool.BooleanConstrSignature);
            }
         case 6:
         default:
            break;
         case 7:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 2, 1, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.ValueOf, ConstantPool.longLongSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x2();
               this.dup_x2();
               this.pop();
               this.invoke((byte)-73, 3, 0, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.Init, ConstantPool.LongConstrSignature);
            }
            break;
         case 8:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 2, 1, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.ValueOf, ConstantPool.doubleDoubleSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x2();
               this.dup_x2();
               this.pop();
               this.invoke((byte)-73, 3, 0, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.Init, ConstantPool.DoubleConstrSignature);
            }
            break;
         case 9:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 1, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.ValueOf, ConstantPool.floatFloatSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x1();
               this.swap();
               this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.Init, ConstantPool.FloatConstrSignature);
            }
            break;
         case 10:
            if (this.targetLevel >= 3211264L) {
               this.invoke((byte)-72, 1, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.ValueOf, ConstantPool.IntIntegerSignature);
            } else {
               this.newWrapperFor(unboxedTypeID);
               this.dup_x1();
               this.swap();
               this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.Init, ConstantPool.IntConstrSignature);
            }
      }

   }

   public void generateClassLiteralAccessForType(TypeBinding accessedType, FieldBinding syntheticFieldBinding) {
      if (accessedType.isBaseType() && accessedType != TypeBinding.NULL) {
         this.getTYPE(accessedType.id);
      } else {
         if (this.targetLevel >= 3211264L) {
            this.ldc(accessedType);
         } else {
            BranchLabel endLabel = new BranchLabel(this);
            if (syntheticFieldBinding != null) {
               this.fieldAccess((byte)-78, syntheticFieldBinding, (TypeBinding)null);
               this.dup();
               this.ifnonnull(endLabel);
               this.pop();
            }

            ExceptionLabel classNotFoundExceptionHandler = new ExceptionLabel(this, TypeBinding.NULL);
            classNotFoundExceptionHandler.placeStart();
            this.ldc(accessedType == TypeBinding.NULL ? "java.lang.Object" : String.valueOf(accessedType.constantPoolName()).replace('/', '.'));
            this.invokeClassForName();
            classNotFoundExceptionHandler.placeEnd();
            if (syntheticFieldBinding != null) {
               this.dup();
               this.fieldAccess((byte)-77, syntheticFieldBinding, (TypeBinding)null);
            }

            this.goto_(endLabel);
            int savedStackDepth = this.stackDepth;
            this.pushExceptionOnStack(TypeBinding.NULL);
            classNotFoundExceptionHandler.place();
            this.newNoClassDefFoundError();
            this.dup_x1();
            this.swap();
            this.invokeThrowableGetMessage();
            this.invokeNoClassDefFoundErrorStringConstructor();
            this.athrow();
            endLabel.place();
            this.stackDepth = savedStackDepth;
         }

      }
   }

   public final void generateCodeAttributeForProblemMethod(String problemMessage) {
      this.newJavaLangError();
      this.dup();
      this.ldc(problemMessage);
      this.invokeJavaLangErrorConstructor();
      this.athrow();
   }

   public void generateConstant(Constant constant, int implicitConversionCode) {
      int targetTypeID = (implicitConversionCode & 255) >> 4;
      if (targetTypeID == 0) {
         targetTypeID = constant.typeID();
      }

      switch (targetTypeID) {
         case 2:
            this.generateInlinedValue(constant.charValue());
            break;
         case 3:
            this.generateInlinedValue(constant.byteValue());
            break;
         case 4:
            this.generateInlinedValue(constant.shortValue());
            break;
         case 5:
            this.generateInlinedValue(constant.booleanValue());
         case 6:
         default:
            break;
         case 7:
            this.generateInlinedValue(constant.longValue());
            break;
         case 8:
            this.generateInlinedValue(constant.doubleValue());
            break;
         case 9:
            this.generateInlinedValue(constant.floatValue());
            break;
         case 10:
            this.generateInlinedValue(constant.intValue());
            break;
         case 11:
            this.ldc(constant.stringValue());
      }

      if ((implicitConversionCode & 512) != 0) {
         this.generateBoxingConversion(targetTypeID);
      }

   }

   public void generateEmulatedReadAccessForField(FieldBinding fieldBinding) {
      this.generateEmulationForField(fieldBinding);
      this.swap();
      this.invokeJavaLangReflectFieldGetter(fieldBinding.type.id);
      if (!fieldBinding.type.isBaseType()) {
         this.checkcast(fieldBinding.type);
      }

   }

   public void generateEmulatedWriteAccessForField(FieldBinding fieldBinding) {
      this.invokeJavaLangReflectFieldSetter(fieldBinding.type.id);
   }

   public void generateEmulationForConstructor(Scope scope, MethodBinding methodBinding) {
      this.ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
      this.invokeClassForName();
      int paramLength = methodBinding.parameters.length;
      this.generateInlinedValue(paramLength);
      this.newArray(scope.createArrayType(scope.getType(TypeConstants.JAVA_LANG_CLASS, 3), 1));
      if (paramLength > 0) {
         this.dup();

         for(int i = 0; i < paramLength; ++i) {
            this.generateInlinedValue(i);
            TypeBinding parameter = methodBinding.parameters[i];
            if (parameter.isBaseType()) {
               this.getTYPE(parameter.id);
            } else if (parameter.isArrayType()) {
               ArrayBinding array = (ArrayBinding)parameter;
               if (array.leafComponentType.isBaseType()) {
                  this.getTYPE(array.leafComponentType.id);
               } else {
                  this.ldc(String.valueOf(array.leafComponentType.constantPoolName()).replace('/', '.'));
                  this.invokeClassForName();
               }

               int dimensions = array.dimensions;
               this.generateInlinedValue(dimensions);
               this.newarray(10);
               this.invokeArrayNewInstance();
               this.invokeObjectGetClass();
            } else {
               this.ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
               this.invokeClassForName();
            }

            this.aastore();
            if (i < paramLength - 1) {
               this.dup();
            }
         }
      }

      this.invokeClassGetDeclaredConstructor();
      this.dup();
      this.iconst_1();
      this.invokeAccessibleObjectSetAccessible();
   }

   public void generateEmulationForField(FieldBinding fieldBinding) {
      this.ldc(String.valueOf(fieldBinding.declaringClass.constantPoolName()).replace('/', '.'));
      this.invokeClassForName();
      this.ldc(String.valueOf(fieldBinding.name));
      this.invokeClassGetDeclaredField();
      this.dup();
      this.iconst_1();
      this.invokeAccessibleObjectSetAccessible();
   }

   public void generateEmulationForMethod(Scope scope, MethodBinding methodBinding) {
      this.ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
      this.invokeClassForName();
      this.ldc(String.valueOf(methodBinding.selector));
      int paramLength = methodBinding.parameters.length;
      this.generateInlinedValue(paramLength);
      this.newArray(scope.createArrayType(scope.getType(TypeConstants.JAVA_LANG_CLASS, 3), 1));
      if (paramLength > 0) {
         this.dup();

         for(int i = 0; i < paramLength; ++i) {
            this.generateInlinedValue(i);
            TypeBinding parameter = methodBinding.parameters[i];
            if (parameter.isBaseType()) {
               this.getTYPE(parameter.id);
            } else if (parameter.isArrayType()) {
               ArrayBinding array = (ArrayBinding)parameter;
               if (array.leafComponentType.isBaseType()) {
                  this.getTYPE(array.leafComponentType.id);
               } else {
                  this.ldc(String.valueOf(array.leafComponentType.constantPoolName()).replace('/', '.'));
                  this.invokeClassForName();
               }

               int dimensions = array.dimensions;
               this.generateInlinedValue(dimensions);
               this.newarray(10);
               this.invokeArrayNewInstance();
               this.invokeObjectGetClass();
            } else {
               this.ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
               this.invokeClassForName();
            }

            this.aastore();
            if (i < paramLength - 1) {
               this.dup();
            }
         }
      }

      this.invokeClassGetDeclaredMethod();
      this.dup();
      this.iconst_1();
      this.invokeAccessibleObjectSetAccessible();
   }

   public void generateImplicitConversion(int implicitConversionCode) {
      int typeId;
      if ((implicitConversionCode & 1024) != 0) {
         typeId = implicitConversionCode & 15;
         this.generateUnboxingConversion(typeId);
      }

      switch (implicitConversionCode & 255) {
         case 33:
         case 49:
         case 65:
         case 81:
         case 113:
         case 129:
         case 145:
         case 161:
            typeId = (implicitConversionCode & 255) >> 4;
            this.checkcast(typeId);
            this.generateUnboxingConversion(typeId);
            break;
         case 35:
         case 36:
         case 42:
            this.i2c();
            break;
         case 39:
            this.l2i();
            this.i2c();
            break;
         case 40:
            this.d2i();
            this.i2c();
            break;
         case 41:
            this.f2i();
            this.i2c();
            break;
         case 50:
         case 52:
         case 58:
            this.i2b();
            break;
         case 55:
            this.l2i();
            this.i2b();
            break;
         case 56:
            this.d2i();
            this.i2b();
            break;
         case 57:
            this.f2i();
            this.i2b();
            break;
         case 66:
         case 67:
         case 74:
            this.i2s();
            break;
         case 71:
            this.l2i();
            this.i2s();
            break;
         case 72:
            this.d2i();
            this.i2s();
            break;
         case 73:
            this.f2i();
            this.i2s();
            break;
         case 114:
         case 115:
         case 116:
         case 122:
            this.i2l();
            break;
         case 120:
            this.d2l();
            break;
         case 121:
            this.f2l();
            break;
         case 130:
         case 131:
         case 132:
         case 138:
            this.i2d();
            break;
         case 135:
            this.l2d();
            break;
         case 137:
            this.f2d();
            break;
         case 146:
         case 147:
         case 148:
         case 154:
            this.i2f();
            break;
         case 151:
            this.l2f();
            break;
         case 152:
            this.d2f();
            break;
         case 167:
            this.l2i();
            break;
         case 168:
            this.d2i();
            break;
         case 169:
            this.f2i();
      }

      if ((implicitConversionCode & 512) != 0) {
         typeId = (implicitConversionCode & 255) >> 4;
         this.generateBoxingConversion(typeId);
      }

   }

   public void generateInlinedValue(boolean inlinedValue) {
      if (inlinedValue) {
         this.iconst_1();
      } else {
         this.iconst_0();
      }

   }

   public void generateInlinedValue(byte inlinedValue) {
      switch (inlinedValue) {
         case -1:
            this.iconst_m1();
            break;
         case 0:
            this.iconst_0();
            break;
         case 1:
            this.iconst_1();
            break;
         case 2:
            this.iconst_2();
            break;
         case 3:
            this.iconst_3();
            break;
         case 4:
            this.iconst_4();
            break;
         case 5:
            this.iconst_5();
            break;
         default:
            if (-128 <= inlinedValue && inlinedValue <= 127) {
               this.bipush(inlinedValue);
               return;
            }
      }

   }

   public void generateInlinedValue(char inlinedValue) {
      switch (inlinedValue) {
         case '\u0000':
            this.iconst_0();
            break;
         case '\u0001':
            this.iconst_1();
            break;
         case '\u0002':
            this.iconst_2();
            break;
         case '\u0003':
            this.iconst_3();
            break;
         case '\u0004':
            this.iconst_4();
            break;
         case '\u0005':
            this.iconst_5();
            break;
         default:
            if (6 <= inlinedValue && inlinedValue <= 127) {
               this.bipush((byte)inlinedValue);
               return;
            }

            if (128 <= inlinedValue && inlinedValue <= 32767) {
               this.sipush(inlinedValue);
               return;
            }

            this.ldc(inlinedValue);
      }

   }

   public void generateInlinedValue(double inlinedValue) {
      if (inlinedValue == 0.0) {
         if (Double.doubleToLongBits(inlinedValue) != 0L) {
            this.ldc2_w(inlinedValue);
         } else {
            this.dconst_0();
         }

      } else if (inlinedValue == 1.0) {
         this.dconst_1();
      } else {
         this.ldc2_w(inlinedValue);
      }
   }

   public void generateInlinedValue(float inlinedValue) {
      if (inlinedValue == 0.0F) {
         if (Float.floatToIntBits(inlinedValue) != 0) {
            this.ldc(inlinedValue);
         } else {
            this.fconst_0();
         }

      } else if (inlinedValue == 1.0F) {
         this.fconst_1();
      } else if (inlinedValue == 2.0F) {
         this.fconst_2();
      } else {
         this.ldc(inlinedValue);
      }
   }

   public void generateInlinedValue(int inlinedValue) {
      switch (inlinedValue) {
         case -1:
            this.iconst_m1();
            break;
         case 0:
            this.iconst_0();
            break;
         case 1:
            this.iconst_1();
            break;
         case 2:
            this.iconst_2();
            break;
         case 3:
            this.iconst_3();
            break;
         case 4:
            this.iconst_4();
            break;
         case 5:
            this.iconst_5();
            break;
         default:
            if (-128 <= inlinedValue && inlinedValue <= 127) {
               this.bipush((byte)inlinedValue);
               return;
            }

            if (-32768 <= inlinedValue && inlinedValue <= 32767) {
               this.sipush(inlinedValue);
               return;
            }

            this.ldc(inlinedValue);
      }

   }

   public void generateInlinedValue(long inlinedValue) {
      if (inlinedValue == 0L) {
         this.lconst_0();
      } else if (inlinedValue == 1L) {
         this.lconst_1();
      } else {
         this.ldc2_w(inlinedValue);
      }
   }

   public void generateInlinedValue(short inlinedValue) {
      switch (inlinedValue) {
         case -1:
            this.iconst_m1();
            break;
         case 0:
            this.iconst_0();
            break;
         case 1:
            this.iconst_1();
            break;
         case 2:
            this.iconst_2();
            break;
         case 3:
            this.iconst_3();
            break;
         case 4:
            this.iconst_4();
            break;
         case 5:
            this.iconst_5();
            break;
         default:
            if (-128 <= inlinedValue && inlinedValue <= 127) {
               this.bipush((byte)inlinedValue);
               return;
            }

            this.sipush(inlinedValue);
      }

   }

   public void generateOuterAccess(Object[] mappingSequence, ASTNode invocationSite, Binding target, Scope scope) {
      if (mappingSequence == null) {
         if (target instanceof LocalVariableBinding) {
            scope.problemReporter().needImplementation(invocationSite);
         } else {
            scope.problemReporter().noSuchEnclosingInstance((ReferenceBinding)target, invocationSite, false);
         }

      } else if (mappingSequence == BlockScope.NoEnclosingInstanceInConstructorCall) {
         scope.problemReporter().noSuchEnclosingInstance((ReferenceBinding)target, invocationSite, true);
      } else if (mappingSequence == BlockScope.NoEnclosingInstanceInStaticContext) {
         scope.problemReporter().noSuchEnclosingInstance((ReferenceBinding)target, invocationSite, false);
      } else if (mappingSequence == BlockScope.EmulationPathToImplicitThis) {
         this.aload_0();
      } else {
         if (mappingSequence[0] instanceof FieldBinding) {
            FieldBinding fieldBinding = (FieldBinding)mappingSequence[0];
            this.aload_0();
            this.fieldAccess((byte)-76, fieldBinding, (TypeBinding)null);
         } else {
            this.load((LocalVariableBinding)mappingSequence[0]);
         }

         int i = 1;

         for(int length = mappingSequence.length; i < length; ++i) {
            if (mappingSequence[i] instanceof FieldBinding) {
               FieldBinding fieldBinding = (FieldBinding)mappingSequence[i];
               this.fieldAccess((byte)-76, fieldBinding, (TypeBinding)null);
            } else {
               this.invoke((byte)-72, (MethodBinding)mappingSequence[i], (TypeBinding)null);
            }
         }

      }
   }

   public void generateReturnBytecode(Expression expression) {
      if (expression == null) {
         this.return_();
      } else {
         int implicitConversion = expression.implicitConversion;
         if ((implicitConversion & 512) != 0) {
            this.areturn();
            return;
         }

         int runtimeType = (implicitConversion & 255) >> 4;
         switch (runtimeType) {
            case 5:
            case 10:
               this.ireturn();
               break;
            case 6:
            default:
               this.areturn();
               break;
            case 7:
               this.lreturn();
               break;
            case 8:
               this.dreturn();
               break;
            case 9:
               this.freturn();
         }
      }

   }

   public void generateStringConcatenationAppend(BlockScope blockScope, Expression oper1, Expression oper2) {
      int pc;
      if (oper1 == null) {
         this.newStringContatenation();
         this.dup_x1();
         this.swap();
         this.invokeStringValueOf(1);
         this.invokeStringConcatenationStringConstructor();
      } else {
         pc = this.position;
         oper1.generateOptimizedStringConcatenationCreation(blockScope, this, oper1.implicitConversion & 15);
         this.recordPositionsFrom(pc, oper1.sourceStart);
      }

      pc = this.position;
      oper2.generateOptimizedStringConcatenation(blockScope, this, oper2.implicitConversion & 15);
      this.recordPositionsFrom(pc, oper2.sourceStart);
      this.invokeStringConcatenationToString();
   }

   public void generateSyntheticBodyForConstructorAccess(SyntheticMethodBinding accessBinding) {
      this.initializeMaxLocals(accessBinding);
      MethodBinding constructorBinding = accessBinding.targetMethod;
      TypeBinding[] parameters = constructorBinding.parameters;
      int length = parameters.length;
      int resolvedPosition = 1;
      this.aload_0();
      TypeBinding declaringClass = constructorBinding.declaringClass;
      if (declaringClass.erasure().id == 41 || declaringClass.isEnum()) {
         this.aload_1();
         this.iload_2();
         resolvedPosition += 2;
      }

      NestedTypeBinding nestedType;
      SyntheticArgumentBinding[] syntheticArguments;
      int i;
      TypeBinding type;
      if (declaringClass.isNestedType()) {
         nestedType = (NestedTypeBinding)declaringClass;
         syntheticArguments = nestedType.syntheticEnclosingInstances();

         for(i = 0; i < (syntheticArguments == null ? 0 : syntheticArguments.length); ++i) {
            this.load(type = syntheticArguments[i].type, resolvedPosition);
            switch (type.id) {
               case 7:
               case 8:
                  resolvedPosition += 2;
                  break;
               default:
                  ++resolvedPosition;
            }
         }
      }

      for(int i = 0; i < length; ++i) {
         TypeBinding parameter;
         this.load(parameter = parameters[i], resolvedPosition);
         switch (parameter.id) {
            case 7:
            case 8:
               resolvedPosition += 2;
               break;
            default:
               ++resolvedPosition;
         }
      }

      if (declaringClass.isNestedType()) {
         nestedType = (NestedTypeBinding)declaringClass;
         syntheticArguments = nestedType.syntheticOuterLocalVariables();

         for(i = 0; i < (syntheticArguments == null ? 0 : syntheticArguments.length); ++i) {
            this.load(type = syntheticArguments[i].type, resolvedPosition);
            switch (type.id) {
               case 7:
               case 8:
                  resolvedPosition += 2;
                  break;
               default:
                  ++resolvedPosition;
            }
         }
      }

      this.invoke((byte)-73, constructorBinding, (TypeBinding)null);
      this.return_();
   }

   public void generateSyntheticBodyForArrayConstructor(SyntheticMethodBinding methodBinding) {
      this.initializeMaxLocals(methodBinding);
      this.iload_0();
      this.newArray((TypeReference)null, (ArrayAllocationExpression)null, (ArrayBinding)methodBinding.returnType);
      this.areturn();
   }

   public void generateSyntheticBodyForArrayClone(SyntheticMethodBinding methodBinding) {
      this.initializeMaxLocals(methodBinding);
      TypeBinding arrayType = methodBinding.parameters[0];
      this.aload_0();
      this.invoke((byte)-74, 1, 1, arrayType.signature(), ConstantPool.Clone, ConstantPool.CloneSignature);
      this.checkcast(arrayType);
      this.areturn();
   }

   public void generateSyntheticBodyForFactoryMethod(SyntheticMethodBinding methodBinding) {
      this.initializeMaxLocals(methodBinding);
      MethodBinding constructorBinding = methodBinding.targetMethod;
      TypeBinding[] parameters = methodBinding.parameters;
      int length = parameters.length;
      this.new_(constructorBinding.declaringClass);
      this.dup();
      int resolvedPosition = 0;

      int i;
      for(i = 0; i < length; ++i) {
         TypeBinding parameter;
         this.load(parameter = parameters[i], resolvedPosition);
         switch (parameter.id) {
            case 7:
            case 8:
               resolvedPosition += 2;
               break;
            default:
               ++resolvedPosition;
         }
      }

      for(i = 0; i < methodBinding.fakePaddedParameters; ++i) {
         this.aconst_null();
      }

      this.invoke((byte)-73, constructorBinding, (TypeBinding)null);
      this.areturn();
   }

   public void generateSyntheticBodyForEnumValueOf(SyntheticMethodBinding methodBinding) {
      this.initializeMaxLocals(methodBinding);
      ReferenceBinding declaringClass = methodBinding.declaringClass;
      this.generateClassLiteralAccessForType(declaringClass, (FieldBinding)null);
      this.aload_0();
      this.invokeJavaLangEnumvalueOf(declaringClass);
      this.checkcast(declaringClass);
      this.areturn();
   }

   public void generateSyntheticBodyForDeserializeLambda(SyntheticMethodBinding methodBinding, SyntheticMethodBinding[] syntheticMethodBindings) {
      this.initializeMaxLocals(methodBinding);
      Map hashcodesTosynthetics = new LinkedHashMap();
      int i = 0;

      for(int max = syntheticMethodBindings.length; i < max; ++i) {
         SyntheticMethodBinding syntheticMethodBinding = syntheticMethodBindings[i];
         if (syntheticMethodBinding.lambda != null && syntheticMethodBinding.lambda.isSerializable || syntheticMethodBinding.serializableMethodRef != null) {
            Integer hashcode = (new String(syntheticMethodBinding.selector)).hashCode();
            List syntheticssForThisHashcode = (List)hashcodesTosynthetics.get(hashcode);
            if (syntheticssForThisHashcode == null) {
               syntheticssForThisHashcode = new ArrayList();
               hashcodesTosynthetics.put(hashcode, syntheticssForThisHashcode);
            }

            ((List)syntheticssForThisHashcode).add(syntheticMethodBinding);
         }
      }

      ClassScope scope = ((SourceTypeBinding)methodBinding.declaringClass).scope;
      this.aload_0();
      this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplMethodName, ConstantPool.GetImplMethodNameSignature);
      this.astore_1();
      LocalVariableBinding lvb1 = new LocalVariableBinding("hashcode".toCharArray(), scope.getJavaLangString(), 0, false);
      lvb1.resolvedPosition = 1;
      this.addVariable(lvb1);
      this.iconst_m1();
      this.istore_2();
      LocalVariableBinding lvb2 = new LocalVariableBinding("id".toCharArray(), TypeBinding.INT, 0, false);
      lvb2.resolvedPosition = 2;
      this.addVariable(lvb2);
      this.aload_1();
      this.invokeStringHashCode();
      BranchLabel label = new BranchLabel(this);
      CaseLabel defaultLabel = new CaseLabel(this);
      int numberOfHashcodes = hashcodesTosynthetics.size();
      CaseLabel[] switchLabels = new CaseLabel[numberOfHashcodes];
      int[] keys = new int[numberOfHashcodes];
      int[] sortedIndexes = new int[numberOfHashcodes];
      Set hashcodes = hashcodesTosynthetics.keySet();
      Iterator hashcodeIterator = hashcodes.iterator();

      int index;
      for(index = 0; hashcodeIterator.hasNext(); sortedIndexes[index] = index++) {
         Integer hashcode = (Integer)hashcodeIterator.next();
         switchLabels[index] = new CaseLabel(this);
         keys[index] = hashcode;
      }

      int[] localKeysCopy;
      System.arraycopy(keys, 0, localKeysCopy = new int[numberOfHashcodes], 0, numberOfHashcodes);
      sort(localKeysCopy, 0, numberOfHashcodes - 1, sortedIndexes);
      this.lookupswitch(defaultLabel, keys, sortedIndexes, switchLabels);
      hashcodeIterator = hashcodes.iterator();
      index = 0;

      while(hashcodeIterator.hasNext()) {
         Integer hashcode = (Integer)hashcodeIterator.next();
         List synthetics = (List)hashcodesTosynthetics.get(hashcode);
         switchLabels[index].place();
         BranchLabel nextOne = new BranchLabel(this);
         int j = 0;

         for(int max = synthetics.size(); j < max; ++j) {
            SyntheticMethodBinding syntheticMethodBinding = (SyntheticMethodBinding)synthetics.get(j);
            this.aload_1();
            this.ldc(new String(syntheticMethodBinding.selector));
            this.invokeStringEquals();
            this.ifeq(nextOne);
            this.loadInt(index);
            this.istore_2();
            this.goto_(label);
            nextOne.place();
            nextOne = new BranchLabel(this);
         }

         ++index;
         this.goto_(label);
      }

      defaultLabel.place();
      label.place();
      int syntheticsCount = hashcodes.size();
      switchLabels = new CaseLabel[syntheticsCount];
      keys = new int[syntheticsCount];
      sortedIndexes = new int[syntheticsCount];
      BranchLabel errorLabel = new BranchLabel(this);
      defaultLabel = new CaseLabel(this);
      this.iload_2();

      int j;
      for(j = 0; j < syntheticsCount; sortedIndexes[j] = j++) {
         switchLabels[j] = new CaseLabel(this);
         keys[j] = j;
      }

      System.arraycopy(keys, 0, localKeysCopy = new int[syntheticsCount], 0, syntheticsCount);
      sort(localKeysCopy, 0, syntheticsCount - 1, sortedIndexes);
      this.lookupswitch(defaultLabel, keys, sortedIndexes, switchLabels);
      hashcodeIterator = hashcodes.iterator();
      j = 0;

      while(hashcodeIterator.hasNext()) {
         Integer hashcode = (Integer)hashcodeIterator.next();
         List synthetics = (List)hashcodesTosynthetics.get(hashcode);
         switchLabels[j++].place();
         BranchLabel nextOne = synthetics.size() > 1 ? new BranchLabel(this) : errorLabel;
         int j = 0;

         for(int count = synthetics.size(); j < count; ++j) {
            SyntheticMethodBinding syntheticMethodBinding = (SyntheticMethodBinding)synthetics.get(j);
            this.aload_0();
            FunctionalExpression funcEx = syntheticMethodBinding.lambda != null ? syntheticMethodBinding.lambda : syntheticMethodBinding.serializableMethodRef;
            MethodBinding mb = ((FunctionalExpression)funcEx).binding;
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplMethodKind, ConstantPool.GetImplMethodKindSignature);
            byte methodKind = false;
            byte methodKind;
            if (mb.isStatic()) {
               methodKind = 6;
            } else if (mb.isPrivate()) {
               methodKind = 7;
            } else if (mb.isConstructor()) {
               methodKind = 8;
            } else if (mb.declaringClass.isInterface()) {
               methodKind = 9;
            } else {
               methodKind = 5;
            }

            this.bipush(methodKind);
            this.if_icmpne(nextOne);
            this.aload_0();
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetFunctionalInterfaceClass, ConstantPool.GetFunctionalInterfaceClassSignature);
            String functionalInterface = null;
            TypeBinding expectedType = ((FunctionalExpression)funcEx).expectedType();
            if (expectedType instanceof IntersectionTypeBinding18) {
               functionalInterface = new String(((IntersectionTypeBinding18)expectedType).getSAMType(scope).constantPoolName());
            } else {
               functionalInterface = new String(expectedType.constantPoolName());
            }

            this.ldc(functionalInterface);
            this.invokeObjectEquals();
            this.ifeq(nextOne);
            this.aload_0();
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetFunctionalInterfaceMethodName, ConstantPool.GetFunctionalInterfaceMethodNameSignature);
            this.ldc(new String(((FunctionalExpression)funcEx).descriptor.selector));
            this.invokeObjectEquals();
            this.ifeq(nextOne);
            this.aload_0();
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetFunctionalInterfaceMethodSignature, ConstantPool.GetFunctionalInterfaceMethodSignatureSignature);
            this.ldc(new String(((FunctionalExpression)funcEx).descriptor.original().signature()));
            this.invokeObjectEquals();
            this.ifeq(nextOne);
            this.aload_0();
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplClass, ConstantPool.GetImplClassSignature);
            this.ldc(new String(mb.declaringClass.constantPoolName()));
            this.invokeObjectEquals();
            this.ifeq(nextOne);
            this.aload_0();
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplMethodSignature, ConstantPool.GetImplMethodSignatureSignature);
            this.ldc(new String(mb.signature()));
            this.invokeObjectEquals();
            this.ifeq(nextOne);
            StringBuffer sig = new StringBuffer("(");
            index = 0;
            boolean isLambda = funcEx instanceof LambdaExpression;
            TypeBinding receiverType = null;
            SyntheticArgumentBinding[] outerLocalVariables = null;
            if (isLambda) {
               LambdaExpression lambdaEx = (LambdaExpression)funcEx;
               if (lambdaEx.shouldCaptureInstance) {
                  receiverType = mb.declaringClass;
               }

               outerLocalVariables = lambdaEx.outerLocalVariables;
            } else {
               ReferenceExpression refEx = (ReferenceExpression)funcEx;
               if (refEx.haveReceiver) {
                  receiverType = ((ReferenceExpression)funcEx).receiverType;
               }
            }

            if (receiverType != null) {
               this.aload_0();
               this.loadInt(index++);
               this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetCapturedArg, ConstantPool.GetCapturedArgSignature);
               this.checkcast((TypeBinding)receiverType);
               sig.append(((TypeBinding)receiverType).signature());
            }

            int p = 0;

            for(int max = outerLocalVariables == null ? 0 : outerLocalVariables.length; p < max; ++p) {
               TypeBinding varType = outerLocalVariables[p].type;
               this.aload_0();
               this.loadInt(index);
               this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetCapturedArg, ConstantPool.GetCapturedArgSignature);
               if (varType.isBaseType()) {
                  this.checkcast(scope.boxing(varType));
                  this.generateUnboxingConversion(varType.id);
                  if (varType.id == 30 || varType.id == 32) {
                     ++index;
                  }
               } else {
                  this.checkcast(varType);
               }

               ++index;
               sig.append(varType.signature());
            }

            sig.append(")");
            if (((FunctionalExpression)funcEx).resolvedType instanceof IntersectionTypeBinding18) {
               sig.append(((IntersectionTypeBinding18)((FunctionalExpression)funcEx).resolvedType).getSAMType(scope).signature());
            } else {
               sig.append(((FunctionalExpression)funcEx).resolvedType.signature());
            }

            this.invokeDynamic(((FunctionalExpression)funcEx).bootstrapMethodNumber, index, 1, ((FunctionalExpression)funcEx).descriptor.selector, sig.toString().toCharArray());
            this.areturn();
            if (j < count - 1) {
               nextOne.place();
               nextOne = j < count - 2 ? new BranchLabel(this) : errorLabel;
            }
         }
      }

      this.removeVariable(lvb1);
      this.removeVariable(lvb2);
      defaultLabel.place();
      errorLabel.place();
      this.new_(scope.getJavaLangIllegalArgumentException());
      this.dup();
      this.ldc("Invalid lambda deserialization");
      this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangIllegalArgumentExceptionConstantPoolName, ConstantPool.Init, ConstantPool.IllegalArgumentExceptionConstructorSignature);
      this.athrow();
   }

   public void loadInt(int value) {
      if (value < 6) {
         if (value == 0) {
            this.iconst_0();
         } else if (value == 1) {
            this.iconst_1();
         } else if (value == 2) {
            this.iconst_2();
         } else if (value == 3) {
            this.iconst_3();
         } else if (value == 4) {
            this.iconst_4();
         } else if (value == 5) {
            this.iconst_5();
         }
      } else if (value < 128) {
         this.bipush((byte)value);
      } else {
         this.ldc(value);
      }

   }

   public void generateSyntheticBodyForEnumValues(SyntheticMethodBinding methodBinding) {
      ClassScope scope = ((SourceTypeBinding)methodBinding.declaringClass).scope;
      this.initializeMaxLocals(methodBinding);
      TypeBinding enumArray = methodBinding.returnType;
      this.fieldAccess((byte)-78, scope.referenceContext.enumValuesSyntheticfield, (TypeBinding)null);
      this.dup();
      this.astore_0();
      this.iconst_0();
      this.aload_0();
      this.arraylength();
      this.dup();
      this.istore_1();
      this.newArray((ArrayBinding)enumArray);
      this.dup();
      this.astore_2();
      this.iconst_0();
      this.iload_1();
      this.invokeSystemArraycopy();
      this.aload_2();
      this.areturn();
   }

   public void generateSyntheticBodyForEnumInitializationMethod(SyntheticMethodBinding methodBinding) {
      this.maxLocals = 0;
      SourceTypeBinding sourceTypeBinding = (SourceTypeBinding)methodBinding.declaringClass;
      TypeDeclaration typeDeclaration = sourceTypeBinding.scope.referenceContext;
      BlockScope staticInitializerScope = typeDeclaration.staticInitializerScope;
      FieldDeclaration[] fieldDeclarations = typeDeclaration.fields;
      int i = methodBinding.startIndex;

      for(int max = methodBinding.endIndex; i < max; ++i) {
         FieldDeclaration fieldDecl = fieldDeclarations[i];
         if (fieldDecl.isStatic() && fieldDecl.getKind() == 3) {
            fieldDecl.generateCode(staticInitializerScope, this);
         }
      }

      this.return_();
   }

   public void generateSyntheticBodyForFieldReadAccess(SyntheticMethodBinding accessMethod) {
      this.initializeMaxLocals(accessMethod);
      FieldBinding fieldBinding = accessMethod.targetReadField;
      TypeBinding declaringClass = accessMethod.purpose == 3 ? accessMethod.declaringClass.superclass() : accessMethod.declaringClass;
      if (fieldBinding.isStatic()) {
         this.fieldAccess((byte)-78, fieldBinding, declaringClass);
      } else {
         this.aload_0();
         this.fieldAccess((byte)-76, fieldBinding, declaringClass);
      }

      switch (fieldBinding.type.id) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 10:
            this.ireturn();
            break;
         case 6:
         default:
            this.areturn();
            break;
         case 7:
            this.lreturn();
            break;
         case 8:
            this.dreturn();
            break;
         case 9:
            this.freturn();
      }

   }

   public void generateSyntheticBodyForFieldWriteAccess(SyntheticMethodBinding accessMethod) {
      this.initializeMaxLocals(accessMethod);
      FieldBinding fieldBinding = accessMethod.targetWriteField;
      TypeBinding declaringClass = accessMethod.purpose == 4 ? accessMethod.declaringClass.superclass() : accessMethod.declaringClass;
      if (fieldBinding.isStatic()) {
         this.load(fieldBinding.type, 0);
         this.fieldAccess((byte)-77, fieldBinding, declaringClass);
      } else {
         this.aload_0();
         this.load(fieldBinding.type, 1);
         this.fieldAccess((byte)-75, fieldBinding, declaringClass);
      }

      this.return_();
   }

   public void generateSyntheticBodyForMethodAccess(SyntheticMethodBinding accessMethod) {
      this.initializeMaxLocals(accessMethod);
      MethodBinding targetMethod = accessMethod.targetMethod;
      TypeBinding[] parameters = targetMethod.parameters;
      int length = parameters.length;
      TypeBinding[] arguments = accessMethod.purpose == 8 ? accessMethod.parameters : null;
      int resolvedPosition;
      if (targetMethod.isStatic()) {
         resolvedPosition = 0;
      } else {
         this.aload_0();
         resolvedPosition = 1;
      }

      TypeBinding match;
      for(int i = 0; i < length; ++i) {
         match = parameters[i];
         if (arguments != null) {
            TypeBinding argument = arguments[i];
            this.load(argument, resolvedPosition);
            if (TypeBinding.notEquals(argument, match)) {
               this.checkcast(match);
            }
         } else {
            this.load(match, resolvedPosition);
         }

         switch (match.id) {
            case 7:
            case 8:
               resolvedPosition += 2;
               break;
            default:
               ++resolvedPosition;
         }
      }

      if (targetMethod.isStatic()) {
         this.invoke((byte)-72, targetMethod, accessMethod.declaringClass);
      } else if (!targetMethod.isConstructor() && !targetMethod.isPrivate() && accessMethod.purpose != 7) {
         if (targetMethod.declaringClass.isInterface()) {
            this.invoke((byte)-71, targetMethod, (TypeBinding)null);
         } else {
            this.invoke((byte)-74, targetMethod, accessMethod.declaringClass);
         }
      } else {
         TypeBinding declaringClass = accessMethod.purpose == 7 ? this.findDirectSuperTypeTowards(accessMethod, targetMethod) : accessMethod.declaringClass;
         this.invoke((byte)-73, targetMethod, declaringClass);
      }

      switch (targetMethod.returnType.id) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 10:
            this.ireturn();
            break;
         case 6:
            this.return_();
            break;
         case 7:
            this.lreturn();
            break;
         case 8:
            this.dreturn();
            break;
         case 9:
            this.freturn();
            break;
         default:
            TypeBinding accessErasure = accessMethod.returnType.erasure();
            match = targetMethod.returnType.findSuperTypeOriginatingFrom(accessErasure);
            if (match == null) {
               this.checkcast(accessErasure);
            }

            this.areturn();
      }

   }

   ReferenceBinding findDirectSuperTypeTowards(SyntheticMethodBinding accessMethod, MethodBinding targetMethod) {
      ReferenceBinding currentType = accessMethod.declaringClass;
      ReferenceBinding superclass = currentType.superclass();
      if (targetMethod.isDefaultMethod()) {
         ReferenceBinding targetType = targetMethod.declaringClass;
         if (superclass.isCompatibleWith(targetType)) {
            return superclass;
         } else {
            ReferenceBinding[] superInterfaces = currentType.superInterfaces();
            if (superInterfaces != null) {
               for(int i = 0; i < superInterfaces.length; ++i) {
                  ReferenceBinding superIfc = superInterfaces[i];
                  if (superIfc.isCompatibleWith(targetType)) {
                     return superIfc;
                  }
               }
            }

            throw new RuntimeException("Assumption violated: some super type must be conform to the declaring class of a super method");
         }
      } else {
         return superclass;
      }
   }

   public void generateSyntheticBodyForSwitchTable(SyntheticMethodBinding methodBinding) {
      ClassScope scope = ((SourceTypeBinding)methodBinding.declaringClass).scope;
      this.initializeMaxLocals(methodBinding);
      BranchLabel nullLabel = new BranchLabel(this);
      FieldBinding syntheticFieldBinding = methodBinding.targetReadField;
      this.fieldAccess((byte)-78, syntheticFieldBinding, (TypeBinding)null);
      this.dup();
      this.ifnull(nullLabel);
      this.areturn();
      this.pushOnStack(syntheticFieldBinding.type);
      nullLabel.place();
      this.pop();
      ReferenceBinding enumBinding = (ReferenceBinding)methodBinding.targetEnumType;
      ArrayBinding arrayBinding = scope.createArrayType(enumBinding, 1);
      this.invokeJavaLangEnumValues(enumBinding, arrayBinding);
      this.arraylength();
      this.newarray(10);
      this.astore_0();
      LocalVariableBinding localVariableBinding = new LocalVariableBinding(" tab".toCharArray(), scope.createArrayType(TypeBinding.INT, 1), 0, false);
      this.addVariable(localVariableBinding);
      FieldBinding[] fields = enumBinding.fields();
      if (fields != null) {
         int i = 0;

         for(int max = fields.length; i < max; ++i) {
            FieldBinding fieldBinding = fields[i];
            if ((fieldBinding.getAccessFlags() & 16384) != 0) {
               BranchLabel endLabel = new BranchLabel(this);
               ExceptionLabel anyExceptionHandler = new ExceptionLabel(this, TypeBinding.LONG);
               anyExceptionHandler.placeStart();
               this.aload_0();
               this.fieldAccess((byte)-78, fieldBinding, (TypeBinding)null);
               this.invokeEnumOrdinal(enumBinding.constantPoolName());
               this.generateInlinedValue(fieldBinding.id + 1);
               this.iastore();
               anyExceptionHandler.placeEnd();
               this.goto_(endLabel);
               this.pushExceptionOnStack(TypeBinding.LONG);
               anyExceptionHandler.place();
               this.pop();
               endLabel.place();
            }
         }
      }

      this.aload_0();
      if (scope.compilerOptions().complianceLevel < 3473408L || !syntheticFieldBinding.isFinal()) {
         this.dup();
         this.fieldAccess((byte)-77, syntheticFieldBinding, (TypeBinding)null);
      }

      this.areturn();
      this.removeVariable(localVariableBinding);
   }

   public void generateSyntheticEnclosingInstanceValues(BlockScope currentScope, ReferenceBinding targetType, Expression enclosingInstance, ASTNode invocationSite) {
      ReferenceBinding checkedTargetType = targetType.isAnonymousType() ? (ReferenceBinding)targetType.superclass().erasure() : targetType;
      boolean hasExtraEnclosingInstance = enclosingInstance != null;
      if (hasExtraEnclosingInstance && (!checkedTargetType.isNestedType() || checkedTargetType.isStatic())) {
         currentScope.problemReporter().unnecessaryEnclosingInstanceSpecification(enclosingInstance, checkedTargetType);
      } else {
         ReferenceBinding[] syntheticArgumentTypes;
         if ((syntheticArgumentTypes = targetType.syntheticEnclosingInstanceTypes()) != null) {
            ReferenceBinding targetEnclosingType = checkedTargetType.enclosingType();
            long compliance = currentScope.compilerOptions().complianceLevel;
            boolean denyEnclosingArgInConstructorCall;
            if (compliance <= 3080192L) {
               denyEnclosingArgInConstructorCall = invocationSite instanceof AllocationExpression;
            } else if (compliance == 3145728L) {
               denyEnclosingArgInConstructorCall = invocationSite instanceof AllocationExpression || invocationSite instanceof ExplicitConstructorCall && ((ExplicitConstructorCall)invocationSite).isSuperAccess();
            } else if (compliance < 3342336L) {
               denyEnclosingArgInConstructorCall = (invocationSite instanceof AllocationExpression || invocationSite instanceof ExplicitConstructorCall && ((ExplicitConstructorCall)invocationSite).isSuperAccess()) && !targetType.isLocalType();
            } else if (invocationSite instanceof AllocationExpression) {
               denyEnclosingArgInConstructorCall = !targetType.isLocalType();
            } else if (invocationSite instanceof ExplicitConstructorCall && ((ExplicitConstructorCall)invocationSite).isSuperAccess()) {
               MethodScope enclosingMethodScope = currentScope.enclosingMethodScope();
               denyEnclosingArgInConstructorCall = !targetType.isLocalType() && enclosingMethodScope != null && enclosingMethodScope.isConstructorCall;
            } else {
               denyEnclosingArgInConstructorCall = false;
            }

            boolean complyTo14 = compliance >= 3145728L;
            int i = 0;

            for(int max = syntheticArgumentTypes.length; i < max; ++i) {
               ReferenceBinding syntheticArgType = syntheticArgumentTypes[i];
               if (hasExtraEnclosingInstance && TypeBinding.equalsEquals(syntheticArgType, targetEnclosingType)) {
                  hasExtraEnclosingInstance = false;
                  enclosingInstance.generateCode(currentScope, this, true);
                  if (complyTo14) {
                     this.dup();
                     this.invokeObjectGetClass();
                     this.pop();
                  }
               } else {
                  Object[] emulationPath = currentScope.getEmulationPath(syntheticArgType, false, denyEnclosingArgInConstructorCall);
                  this.generateOuterAccess(emulationPath, invocationSite, syntheticArgType, currentScope);
               }
            }

            if (hasExtraEnclosingInstance) {
               currentScope.problemReporter().unnecessaryEnclosingInstanceSpecification(enclosingInstance, checkedTargetType);
            }
         }

      }
   }

   public void generateSyntheticOuterArgumentValues(BlockScope currentScope, ReferenceBinding targetType, ASTNode invocationSite) {
      SyntheticArgumentBinding[] syntheticArguments;
      if ((syntheticArguments = targetType.syntheticOuterLocalVariables()) != null) {
         int i = 0;

         for(int max = syntheticArguments.length; i < max; ++i) {
            LocalVariableBinding targetVariable = syntheticArguments[i].actualOuterLocalVariable;
            VariableBinding[] emulationPath = currentScope.getEmulationPath(targetVariable);
            this.generateOuterAccess(emulationPath, invocationSite, targetVariable, currentScope);
         }
      }

   }

   public void generateUnboxingConversion(int unboxedTypeID) {
      switch (unboxedTypeID) {
         case 2:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.CHARVALUE_CHARACTER_METHOD_NAME, ConstantPool.CHARVALUE_CHARACTER_METHOD_SIGNATURE);
            break;
         case 3:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.BYTEVALUE_BYTE_METHOD_NAME, ConstantPool.BYTEVALUE_BYTE_METHOD_SIGNATURE);
            break;
         case 4:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.SHORTVALUE_SHORT_METHOD_NAME, ConstantPool.SHORTVALUE_SHORT_METHOD_SIGNATURE);
            break;
         case 5:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_NAME, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_SIGNATURE);
         case 6:
         default:
            break;
         case 7:
            this.invoke((byte)-74, 1, 2, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.LONGVALUE_LONG_METHOD_NAME, ConstantPool.LONGVALUE_LONG_METHOD_SIGNATURE);
            break;
         case 8:
            this.invoke((byte)-74, 1, 2, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_NAME, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE);
            break;
         case 9:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.FLOATVALUE_FLOAT_METHOD_NAME, ConstantPool.FLOATVALUE_FLOAT_METHOD_SIGNATURE);
            break;
         case 10:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.INTVALUE_INTEGER_METHOD_NAME, ConstantPool.INTVALUE_INTEGER_METHOD_SIGNATURE);
      }

   }

   public void generateWideRevertedConditionalBranch(byte revertedOpcode, BranchLabel wideTarget) {
      BranchLabel intermediate = new BranchLabel(this);
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = revertedOpcode;
      intermediate.branch();
      this.goto_w(wideTarget);
      intermediate.place();
   }

   public void getBaseTypeValue(int baseTypeID) {
      switch (baseTypeID) {
         case 2:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.CHARVALUE_CHARACTER_METHOD_NAME, ConstantPool.CHARVALUE_CHARACTER_METHOD_SIGNATURE);
            break;
         case 3:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.BYTEVALUE_BYTE_METHOD_NAME, ConstantPool.BYTEVALUE_BYTE_METHOD_SIGNATURE);
            break;
         case 4:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.SHORTVALUE_SHORT_METHOD_NAME, ConstantPool.SHORTVALUE_SHORT_METHOD_SIGNATURE);
            break;
         case 5:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_NAME, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_SIGNATURE);
         case 6:
         default:
            break;
         case 7:
            this.invoke((byte)-74, 1, 2, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.LONGVALUE_LONG_METHOD_NAME, ConstantPool.LONGVALUE_LONG_METHOD_SIGNATURE);
            break;
         case 8:
            this.invoke((byte)-74, 1, 2, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_NAME, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE);
            break;
         case 9:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.FLOATVALUE_FLOAT_METHOD_NAME, ConstantPool.FLOATVALUE_FLOAT_METHOD_SIGNATURE);
            break;
         case 10:
            this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.INTVALUE_INTEGER_METHOD_NAME, ConstantPool.INTVALUE_INTEGER_METHOD_SIGNATURE);
      }

   }

   public final byte[] getContents() {
      byte[] contents;
      System.arraycopy(this.bCodeStream, 0, contents = new byte[this.position], 0, this.position);
      return contents;
   }

   public static TypeBinding getConstantPoolDeclaringClass(Scope currentScope, FieldBinding codegenBinding, TypeBinding actualReceiverType, boolean isImplicitThisReceiver) {
      ReferenceBinding constantPoolDeclaringClass = codegenBinding.declaringClass;
      if (TypeBinding.notEquals(constantPoolDeclaringClass, actualReceiverType.erasure()) && !actualReceiverType.isArrayType() && constantPoolDeclaringClass != null && codegenBinding.constant() == Constant.NotAConstant) {
         CompilerOptions options = currentScope.compilerOptions();
         if (options.targetJDK >= 3014656L && (options.complianceLevel >= 3145728L || !isImplicitThisReceiver || !codegenBinding.isStatic()) && constantPoolDeclaringClass.id != 1 || !constantPoolDeclaringClass.canBeSeenBy(currentScope)) {
            return actualReceiverType.erasure();
         }
      }

      return constantPoolDeclaringClass;
   }

   public static TypeBinding getConstantPoolDeclaringClass(Scope currentScope, MethodBinding codegenBinding, TypeBinding actualReceiverType, boolean isImplicitThisReceiver) {
      TypeBinding constantPoolDeclaringClass = codegenBinding.declaringClass;
      CompilerOptions options;
      if (ArrayBinding.isArrayClone(actualReceiverType, codegenBinding)) {
         options = currentScope.compilerOptions();
         if (options.sourceLevel > 3145728L) {
            constantPoolDeclaringClass = actualReceiverType.erasure();
         }
      } else if (TypeBinding.notEquals((TypeBinding)constantPoolDeclaringClass, actualReceiverType.erasure()) && !actualReceiverType.isArrayType()) {
         options = currentScope.compilerOptions();
         if (options.targetJDK >= 3014656L && (options.complianceLevel >= 3145728L || !isImplicitThisReceiver || !codegenBinding.isStatic()) && codegenBinding.declaringClass.id != 1 || !codegenBinding.declaringClass.canBeSeenBy(currentScope)) {
            if (actualReceiverType.isIntersectionType18()) {
               TypeBinding[] intersectingTypes = ((IntersectionTypeBinding18)actualReceiverType).getIntersectingTypes();

               for(int i = 0; i < intersectingTypes.length; ++i) {
                  if (intersectingTypes[i].findSuperTypeOriginatingFrom((TypeBinding)constantPoolDeclaringClass) != null) {
                     constantPoolDeclaringClass = intersectingTypes[i].erasure();
                     break;
                  }
               }
            } else {
               constantPoolDeclaringClass = actualReceiverType.erasure();
            }
         }
      }

      return (TypeBinding)constantPoolDeclaringClass;
   }

   protected int getPosition() {
      return this.position;
   }

   public void getTYPE(int baseTypeID) {
      this.countLabels = 0;
      switch (baseTypeID) {
         case 2:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 3:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 4:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 5:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 6:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangVoidConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 7:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 8:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 9:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
            break;
         case 10:
            this.fieldAccess((byte)-78, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
      }

   }

   public void goto_(BranchLabel label) {
      if (this.wideMode) {
         this.goto_w(label);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         boolean chained = this.inlineForwardReferencesFromLabelsTargeting(label, this.position);
         if (chained && this.lastAbruptCompletion == this.position) {
            if (label.position != -1) {
               int[] forwardRefs = label.forwardReferences();
               int i = 0;

               for(int max = label.forwardReferenceCount(); i < max; ++i) {
                  this.writePosition(label, forwardRefs[i]);
               }

               this.countLabels = 0;
            }

         } else {
            ++this.position;
            this.bCodeStream[this.classFileOffset++] = -89;
            label.branch();
            this.lastAbruptCompletion = this.position;
         }
      }
   }

   public void goto_w(BranchLabel label) {
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -56;
      label.branchWide();
      this.lastAbruptCompletion = this.position;
   }

   public void i2b() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -111;
   }

   public void i2c() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -110;
   }

   public void i2d() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -121;
   }

   public void i2f() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -122;
   }

   public void i2l() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -123;
   }

   public void i2s() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -109;
   }

   public void iadd() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 96;
   }

   public void iaload() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 46;
   }

   public void iand() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 126;
   }

   public void iastore() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 79;
   }

   public void iconst_0() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 3;
   }

   public void iconst_1() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 4;
   }

   public void iconst_2() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 5;
   }

   public void iconst_3() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 6;
   }

   public void iconst_4() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 7;
   }

   public void iconst_5() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 8;
   }

   public void iconst_m1() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 2;
   }

   public void idiv() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 108;
   }

   public void if_acmpeq(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-90, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -91;
         lbl.branch();
      }

   }

   public void if_acmpne(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-91, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -90;
         lbl.branch();
      }

   }

   public void if_icmpeq(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-96, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -97;
         lbl.branch();
      }

   }

   public void if_icmpge(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-95, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -94;
         lbl.branch();
      }

   }

   public void if_icmpgt(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-92, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -93;
         lbl.branch();
      }

   }

   public void if_icmple(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-93, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -92;
         lbl.branch();
      }

   }

   public void if_icmplt(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-94, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -95;
         lbl.branch();
      }

   }

   public void if_icmpne(BranchLabel lbl) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-97, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -96;
         lbl.branch();
      }

   }

   public void ifeq(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-102, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -103;
         lbl.branch();
      }

   }

   public void ifge(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-101, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -100;
         lbl.branch();
      }

   }

   public void ifgt(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-98, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -99;
         lbl.branch();
      }

   }

   public void ifle(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-99, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -98;
         lbl.branch();
      }

   }

   public void iflt(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-100, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -101;
         lbl.branch();
      }

   }

   public void ifne(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-103, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -102;
         lbl.branch();
      }

   }

   public void ifnonnull(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-58, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -57;
         lbl.branch();
      }

   }

   public void ifnull(BranchLabel lbl) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.wideMode) {
         this.generateWideRevertedConditionalBranch((byte)-57, lbl);
      } else {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -58;
         lbl.branch();
      }

   }

   public final void iinc(int index, int value) {
      this.countLabels = 0;
      if (index <= 255 && value >= -128 && value <= 127) {
         if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 3;
         this.bCodeStream[this.classFileOffset++] = -124;
         this.bCodeStream[this.classFileOffset++] = (byte)index;
         this.bCodeStream[this.classFileOffset++] = (byte)value;
      } else {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = -124;
         this.writeUnsignedShort(index);
         this.writeSignedShort(value);
      }

   }

   public void iload(int iArg) {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= iArg) {
         this.maxLocals = iArg + 1;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 21;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 21;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void iload_0() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= 0) {
         this.maxLocals = 1;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 26;
   }

   public void iload_1() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= 1) {
         this.maxLocals = 2;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 27;
   }

   public void iload_2() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= 2) {
         this.maxLocals = 3;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 28;
   }

   public void iload_3() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.maxLocals <= 3) {
         this.maxLocals = 4;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 29;
   }

   public void imul() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 104;
   }

   public void ineg() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 116;
   }

   public void init(ClassFile targetClassFile) {
      this.classFile = targetClassFile;
      this.constantPool = targetClassFile.constantPool;
      this.bCodeStream = targetClassFile.contents;
      this.classFileOffset = targetClassFile.contentsOffset;
      this.startingClassFileOffset = this.classFileOffset;
      this.pcToSourceMapSize = 0;
      this.lastEntryPC = 0;
      this.visibleLocalsCount = 0;
      this.allLocalsCounter = 0;
      this.exceptionLabelsCounter = 0;
      this.countLabels = 0;
      this.lastAbruptCompletion = -1;
      this.stackMax = 0;
      this.stackDepth = 0;
      this.maxLocals = 0;
      this.position = 0;
   }

   public void initializeMaxLocals(MethodBinding methodBinding) {
      if (methodBinding == null) {
         this.maxLocals = 0;
      } else {
         this.maxLocals = methodBinding.isStatic() ? 0 : 1;
         ReferenceBinding declaringClass = methodBinding.declaringClass;
         if (methodBinding.isConstructor() && declaringClass.isEnum()) {
            this.maxLocals += 2;
         }

         if (methodBinding.isConstructor() && declaringClass.isNestedType()) {
            this.maxLocals += declaringClass.getEnclosingInstancesSlotSize();
            this.maxLocals += declaringClass.getOuterLocalVariablesSlotSize();
         }

         TypeBinding[] parameterTypes;
         if ((parameterTypes = methodBinding.parameters) != null) {
            int i = 0;

            for(int max = parameterTypes.length; i < max; ++i) {
               switch (parameterTypes[i].id) {
                  case 7:
                  case 8:
                     this.maxLocals += 2;
                     break;
                  default:
                     ++this.maxLocals;
               }
            }
         }

      }
   }

   public boolean inlineForwardReferencesFromLabelsTargeting(BranchLabel targetLabel, int gotoLocation) {
      if (targetLabel.delegate != null) {
         return false;
      } else {
         int chaining = 0;

         for(int i = this.countLabels - 1; i >= 0; --i) {
            BranchLabel currentLabel = this.labels[i];
            if (currentLabel.position != gotoLocation) {
               break;
            }

            if (currentLabel == targetLabel) {
               chaining |= 4;
            } else if (currentLabel.isStandardLabel()) {
               if (currentLabel.delegate == null) {
                  targetLabel.becomeDelegateFor(currentLabel);
                  chaining |= 2;
               }
            } else {
               chaining |= 4;
            }
         }

         return (chaining & 6) == 2;
      }
   }

   public void instance_of(TypeBinding typeBinding) {
      this.instance_of((TypeReference)null, typeBinding);
   }

   public void instance_of(TypeReference typeReference, TypeBinding typeBinding) {
      this.countLabels = 0;
      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -63;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
   }

   protected void invoke(byte opcode, int receiverAndArgsSize, int returnTypeSize, char[] declaringClass, char[] selector, char[] signature) {
      this.invoke18(opcode, receiverAndArgsSize, returnTypeSize, declaringClass, opcode == -71, selector, signature);
   }

   private void invoke18(byte opcode, int receiverAndArgsSize, int returnTypeSize, char[] declaringClass, boolean isInterface, char[] selector, char[] signature) {
      this.countLabels = 0;
      if (opcode == -71) {
         if (this.classFileOffset + 4 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 3;
         this.bCodeStream[this.classFileOffset++] = opcode;
         this.writeUnsignedShort(this.constantPool.literalIndexForMethod(declaringClass, selector, signature, true));
         this.bCodeStream[this.classFileOffset++] = (byte)receiverAndArgsSize;
         this.bCodeStream[this.classFileOffset++] = 0;
      } else {
         if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = opcode;
         this.writeUnsignedShort(this.constantPool.literalIndexForMethod(declaringClass, selector, signature, isInterface));
      }

      this.stackDepth += returnTypeSize - receiverAndArgsSize;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

   }

   public void invokeDynamic(int bootStrapIndex, int argsSize, int returnTypeSize, char[] selector, char[] signature) {
      this.invokeDynamic(bootStrapIndex, argsSize, returnTypeSize, selector, signature, false, (TypeReference)null, (TypeReference[])null);
   }

   public void invokeDynamic(int bootStrapIndex, int argsSize, int returnTypeSize, char[] selector, char[] signature, boolean isConstructorReference, TypeReference lhsTypeReference, TypeReference[] typeArguments) {
      if (this.classFileOffset + 4 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      int invokeDynamicIndex = this.constantPool.literalIndexForInvokeDynamic(bootStrapIndex, selector, signature);
      this.position += 3;
      this.bCodeStream[this.classFileOffset++] = -70;
      this.writeUnsignedShort(invokeDynamicIndex);
      this.bCodeStream[this.classFileOffset++] = 0;
      this.bCodeStream[this.classFileOffset++] = 0;
      this.stackDepth += returnTypeSize - argsSize;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

   }

   public void invoke(byte opcode, MethodBinding methodBinding, TypeBinding declaringClass) {
      this.invoke(opcode, methodBinding, declaringClass, (TypeReference[])null);
   }

   public void invoke(byte opcode, MethodBinding methodBinding, TypeBinding declaringClass, TypeReference[] typeArguments) {
      if (declaringClass == null) {
         declaringClass = methodBinding.declaringClass;
      }

      if ((((TypeBinding)declaringClass).tagBits & 2048L) != 0L) {
         Util.recordNestedType(this.classFile, (TypeBinding)declaringClass);
      }

      int receiverAndArgsSize;
      switch (opcode) {
         case -74:
         case -71:
            receiverAndArgsSize = 1;
            break;
         case -73:
            receiverAndArgsSize = 1;
            if (methodBinding.isConstructor()) {
               if (((TypeBinding)declaringClass).isNestedType()) {
                  ReferenceBinding nestedType = (ReferenceBinding)declaringClass;
                  receiverAndArgsSize += nestedType.getEnclosingInstancesSlotSize();
                  SyntheticArgumentBinding[] syntheticArguments = nestedType.syntheticOuterLocalVariables();
                  if (syntheticArguments != null) {
                     int i = 0;

                     for(int max = syntheticArguments.length; i < max; ++i) {
                        switch (syntheticArguments[i].id) {
                           case 7:
                           case 8:
                              receiverAndArgsSize += 2;
                              break;
                           default:
                              ++receiverAndArgsSize;
                        }
                     }
                  }
               }

               if (((TypeBinding)declaringClass).isEnum()) {
                  receiverAndArgsSize += 2;
               }
            }
            break;
         case -72:
            receiverAndArgsSize = 0;
            break;
         default:
            return;
      }

      for(int i = methodBinding.parameters.length - 1; i >= 0; --i) {
         switch (methodBinding.parameters[i].id) {
            case 7:
            case 8:
               receiverAndArgsSize += 2;
               break;
            default:
               ++receiverAndArgsSize;
         }
      }

      byte returnTypeSize;
      switch (methodBinding.returnType.id) {
         case 6:
            returnTypeSize = 0;
            break;
         case 7:
         case 8:
            returnTypeSize = 2;
            break;
         default:
            returnTypeSize = 1;
      }

      this.invoke18(opcode, receiverAndArgsSize, returnTypeSize, ((TypeBinding)declaringClass).constantPoolName(), ((TypeBinding)declaringClass).isInterface(), methodBinding.selector, methodBinding.signature(this.classFile));
   }

   protected void invokeAccessibleObjectSetAccessible() {
      this.invoke((byte)-74, 2, 0, ConstantPool.JAVALANGREFLECTACCESSIBLEOBJECT_CONSTANTPOOLNAME, ConstantPool.SETACCESSIBLE_NAME, ConstantPool.SETACCESSIBLE_SIGNATURE);
   }

   protected void invokeArrayNewInstance() {
      this.invoke((byte)-72, 2, 1, ConstantPool.JAVALANGREFLECTARRAY_CONSTANTPOOLNAME, ConstantPool.NewInstance, ConstantPool.NewInstanceSignature);
   }

   public void invokeClassForName() {
      this.invoke((byte)-72, 1, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.ForName, ConstantPool.ForNameSignature);
   }

   protected void invokeClassGetDeclaredConstructor() {
      this.invoke((byte)-74, 2, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.GETDECLAREDCONSTRUCTOR_NAME, ConstantPool.GETDECLAREDCONSTRUCTOR_SIGNATURE);
   }

   protected void invokeClassGetDeclaredField() {
      this.invoke((byte)-74, 2, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.GETDECLAREDFIELD_NAME, ConstantPool.GETDECLAREDFIELD_SIGNATURE);
   }

   protected void invokeClassGetDeclaredMethod() {
      this.invoke((byte)-74, 3, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.GETDECLAREDMETHOD_NAME, ConstantPool.GETDECLAREDMETHOD_SIGNATURE);
   }

   public void invokeEnumOrdinal(char[] enumTypeConstantPoolName) {
      this.invoke((byte)-74, 1, 1, enumTypeConstantPoolName, ConstantPool.Ordinal, ConstantPool.OrdinalSignature);
   }

   public void invokeIterableIterator(TypeBinding iterableReceiverType) {
      if ((iterableReceiverType.tagBits & 2048L) != 0L) {
         Util.recordNestedType(this.classFile, iterableReceiverType);
      }

      this.invoke((byte)(iterableReceiverType.isInterface() ? -71 : -74), 1, 1, iterableReceiverType.constantPoolName(), ConstantPool.ITERATOR_NAME, ConstantPool.ITERATOR_SIGNATURE);
   }

   public void invokeAutoCloseableClose(TypeBinding resourceType) {
      this.invoke((byte)(resourceType.erasure().isInterface() ? -71 : -74), 1, 0, resourceType.constantPoolName(), ConstantPool.Close, ConstantPool.CloseSignature);
   }

   public void invokeThrowableAddSuppressed() {
      this.invoke((byte)-74, 2, 0, ConstantPool.JavaLangThrowableConstantPoolName, ConstantPool.AddSuppressed, ConstantPool.AddSuppressedSignature);
   }

   public void invokeJavaLangAssertionErrorConstructor(int typeBindingID) {
      byte receiverAndArgsSize;
      char[] signature;
      switch (typeBindingID) {
         case 1:
         case 11:
         case 12:
            signature = ConstantPool.ObjectConstrSignature;
            receiverAndArgsSize = 2;
            break;
         case 2:
            signature = ConstantPool.CharConstrSignature;
            receiverAndArgsSize = 2;
            break;
         case 3:
         case 4:
         case 10:
            signature = ConstantPool.IntConstrSignature;
            receiverAndArgsSize = 2;
            break;
         case 5:
            signature = ConstantPool.BooleanConstrSignature;
            receiverAndArgsSize = 2;
            break;
         case 6:
         default:
            return;
         case 7:
            signature = ConstantPool.LongConstrSignature;
            receiverAndArgsSize = 3;
            break;
         case 8:
            signature = ConstantPool.DoubleConstrSignature;
            receiverAndArgsSize = 3;
            break;
         case 9:
            signature = ConstantPool.FloatConstrSignature;
            receiverAndArgsSize = 2;
      }

      this.invoke((byte)-73, receiverAndArgsSize, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, signature);
   }

   public void invokeJavaLangAssertionErrorDefaultConstructor() {
      this.invoke((byte)-73, 1, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, ConstantPool.DefaultConstructorSignature);
   }

   public void invokeJavaLangIncompatibleClassChangeErrorDefaultConstructor() {
      this.invoke((byte)-73, 1, 0, ConstantPool.JavaLangIncompatibleClassChangeErrorConstantPoolName, ConstantPool.Init, ConstantPool.DefaultConstructorSignature);
   }

   public void invokeJavaLangClassDesiredAssertionStatus() {
      this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.DesiredAssertionStatus, ConstantPool.DesiredAssertionStatusSignature);
   }

   public void invokeJavaLangEnumvalueOf(ReferenceBinding binding) {
      this.invoke((byte)-72, 2, 1, ConstantPool.JavaLangEnumConstantPoolName, ConstantPool.ValueOf, ConstantPool.ValueOfStringClassSignature);
   }

   public void invokeJavaLangEnumValues(TypeBinding enumBinding, ArrayBinding arrayBinding) {
      char[] signature = "()".toCharArray();
      signature = CharOperation.concat(signature, arrayBinding.constantPoolName());
      this.invoke((byte)-72, 0, 1, enumBinding.constantPoolName(), TypeConstants.VALUES, signature);
   }

   public void invokeJavaLangErrorConstructor() {
      this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangErrorConstantPoolName, ConstantPool.Init, ConstantPool.StringConstructorSignature);
   }

   public void invokeJavaLangReflectConstructorNewInstance() {
      this.invoke((byte)-74, 2, 1, ConstantPool.JavaLangReflectConstructorConstantPoolName, ConstantPool.NewInstance, ConstantPool.JavaLangReflectConstructorNewInstanceSignature);
   }

   protected void invokeJavaLangReflectFieldGetter(int typeID) {
      char[] selector;
      char[] signature;
      byte returnTypeSize;
      switch (typeID) {
         case 2:
            selector = ConstantPool.GET_CHAR_METHOD_NAME;
            signature = ConstantPool.GET_CHAR_METHOD_SIGNATURE;
            returnTypeSize = 1;
            break;
         case 3:
            selector = ConstantPool.GET_BYTE_METHOD_NAME;
            signature = ConstantPool.GET_BYTE_METHOD_SIGNATURE;
            returnTypeSize = 1;
            break;
         case 4:
            selector = ConstantPool.GET_SHORT_METHOD_NAME;
            signature = ConstantPool.GET_SHORT_METHOD_SIGNATURE;
            returnTypeSize = 1;
            break;
         case 5:
            selector = ConstantPool.GET_BOOLEAN_METHOD_NAME;
            signature = ConstantPool.GET_BOOLEAN_METHOD_SIGNATURE;
            returnTypeSize = 1;
            break;
         case 6:
         default:
            selector = ConstantPool.GET_OBJECT_METHOD_NAME;
            signature = ConstantPool.GET_OBJECT_METHOD_SIGNATURE;
            returnTypeSize = 1;
            break;
         case 7:
            selector = ConstantPool.GET_LONG_METHOD_NAME;
            signature = ConstantPool.GET_LONG_METHOD_SIGNATURE;
            returnTypeSize = 2;
            break;
         case 8:
            selector = ConstantPool.GET_DOUBLE_METHOD_NAME;
            signature = ConstantPool.GET_DOUBLE_METHOD_SIGNATURE;
            returnTypeSize = 2;
            break;
         case 9:
            selector = ConstantPool.GET_FLOAT_METHOD_NAME;
            signature = ConstantPool.GET_FLOAT_METHOD_SIGNATURE;
            returnTypeSize = 1;
            break;
         case 10:
            selector = ConstantPool.GET_INT_METHOD_NAME;
            signature = ConstantPool.GET_INT_METHOD_SIGNATURE;
            returnTypeSize = 1;
      }

      this.invoke((byte)-74, 2, returnTypeSize, ConstantPool.JAVALANGREFLECTFIELD_CONSTANTPOOLNAME, selector, signature);
   }

   protected void invokeJavaLangReflectFieldSetter(int typeID) {
      char[] selector;
      char[] signature;
      byte receiverAndArgsSize;
      switch (typeID) {
         case 2:
            selector = ConstantPool.SET_CHAR_METHOD_NAME;
            signature = ConstantPool.SET_CHAR_METHOD_SIGNATURE;
            receiverAndArgsSize = 3;
            break;
         case 3:
            selector = ConstantPool.SET_BYTE_METHOD_NAME;
            signature = ConstantPool.SET_BYTE_METHOD_SIGNATURE;
            receiverAndArgsSize = 3;
            break;
         case 4:
            selector = ConstantPool.SET_SHORT_METHOD_NAME;
            signature = ConstantPool.SET_SHORT_METHOD_SIGNATURE;
            receiverAndArgsSize = 3;
            break;
         case 5:
            selector = ConstantPool.SET_BOOLEAN_METHOD_NAME;
            signature = ConstantPool.SET_BOOLEAN_METHOD_SIGNATURE;
            receiverAndArgsSize = 3;
            break;
         case 6:
         default:
            selector = ConstantPool.SET_OBJECT_METHOD_NAME;
            signature = ConstantPool.SET_OBJECT_METHOD_SIGNATURE;
            receiverAndArgsSize = 3;
            break;
         case 7:
            selector = ConstantPool.SET_LONG_METHOD_NAME;
            signature = ConstantPool.SET_LONG_METHOD_SIGNATURE;
            receiverAndArgsSize = 4;
            break;
         case 8:
            selector = ConstantPool.SET_DOUBLE_METHOD_NAME;
            signature = ConstantPool.SET_DOUBLE_METHOD_SIGNATURE;
            receiverAndArgsSize = 4;
            break;
         case 9:
            selector = ConstantPool.SET_FLOAT_METHOD_NAME;
            signature = ConstantPool.SET_FLOAT_METHOD_SIGNATURE;
            receiverAndArgsSize = 3;
            break;
         case 10:
            selector = ConstantPool.SET_INT_METHOD_NAME;
            signature = ConstantPool.SET_INT_METHOD_SIGNATURE;
            receiverAndArgsSize = 3;
      }

      this.invoke((byte)-74, receiverAndArgsSize, 0, ConstantPool.JAVALANGREFLECTFIELD_CONSTANTPOOLNAME, selector, signature);
   }

   public void invokeJavaLangReflectMethodInvoke() {
      this.invoke((byte)-74, 3, 1, ConstantPool.JAVALANGREFLECTMETHOD_CONSTANTPOOLNAME, ConstantPool.INVOKE_METHOD_METHOD_NAME, ConstantPool.INVOKE_METHOD_METHOD_SIGNATURE);
   }

   public void invokeJavaUtilIteratorHasNext() {
      this.invoke((byte)-71, 1, 1, ConstantPool.JavaUtilIteratorConstantPoolName, ConstantPool.HasNext, ConstantPool.HasNextSignature);
   }

   public void invokeJavaUtilIteratorNext() {
      this.invoke((byte)-71, 1, 1, ConstantPool.JavaUtilIteratorConstantPoolName, ConstantPool.Next, ConstantPool.NextSignature);
   }

   public void invokeNoClassDefFoundErrorStringConstructor() {
      this.invoke((byte)-73, 2, 0, ConstantPool.JavaLangNoClassDefFoundErrorConstantPoolName, ConstantPool.Init, ConstantPool.StringConstructorSignature);
   }

   public void invokeObjectGetClass() {
      this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangObjectConstantPoolName, ConstantPool.GetClass, ConstantPool.GetClassSignature);
   }

   public void invokeStringConcatenationAppendForType(int typeID) {
      char[] declaringClass = null;
      char[] selector = ConstantPool.Append;
      char[] signature = null;
      byte receiverAndArgsSize;
      char[] declaringClass;
      char[] signature;
      switch (typeID) {
         case 2:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendCharSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendCharSignature;
            }

            receiverAndArgsSize = 2;
            break;
         case 3:
         case 4:
         case 10:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendIntSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendIntSignature;
            }

            receiverAndArgsSize = 2;
            break;
         case 5:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendBooleanSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendBooleanSignature;
            }

            receiverAndArgsSize = 2;
            break;
         case 6:
         default:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendObjectSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendObjectSignature;
            }

            receiverAndArgsSize = 2;
            break;
         case 7:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendLongSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendLongSignature;
            }

            receiverAndArgsSize = 3;
            break;
         case 8:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendDoubleSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendDoubleSignature;
            }

            receiverAndArgsSize = 3;
            break;
         case 9:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendFloatSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendFloatSignature;
            }

            receiverAndArgsSize = 2;
            break;
         case 11:
            if (this.targetLevel >= 3211264L) {
               declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
               signature = ConstantPool.StringBuilderAppendStringSignature;
            } else {
               declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
               signature = ConstantPool.StringBufferAppendStringSignature;
            }

            receiverAndArgsSize = 2;
      }

      this.invoke((byte)-74, receiverAndArgsSize, 1, declaringClass, selector, signature);
   }

   public void invokeStringConcatenationDefaultConstructor() {
      char[] declaringClass;
      if (this.targetLevel < 3211264L) {
         declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
      } else {
         declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
      }

      this.invoke((byte)-73, 1, 0, declaringClass, ConstantPool.Init, ConstantPool.DefaultConstructorSignature);
   }

   public void invokeStringConcatenationStringConstructor() {
      char[] declaringClass;
      if (this.targetLevel < 3211264L) {
         declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
      } else {
         declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
      }

      this.invoke((byte)-73, 2, 0, declaringClass, ConstantPool.Init, ConstantPool.StringConstructorSignature);
   }

   public void invokeStringConcatenationToString() {
      char[] declaringClass;
      if (this.targetLevel < 3211264L) {
         declaringClass = ConstantPool.JavaLangStringBufferConstantPoolName;
      } else {
         declaringClass = ConstantPool.JavaLangStringBuilderConstantPoolName;
      }

      this.invoke((byte)-74, 1, 1, declaringClass, ConstantPool.ToString, ConstantPool.ToStringSignature);
   }

   public void invokeStringEquals() {
      this.invoke((byte)-74, 2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.Equals, ConstantPool.EqualsSignature);
   }

   public void invokeObjectEquals() {
      this.invoke((byte)-74, 2, 1, ConstantPool.JavaLangObjectConstantPoolName, ConstantPool.Equals, ConstantPool.EqualsSignature);
   }

   public void invokeStringHashCode() {
      this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.HashCode, ConstantPool.HashCodeSignature);
   }

   public void invokeStringIntern() {
      this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.Intern, ConstantPool.InternSignature);
   }

   public void invokeStringValueOf(int typeID) {
      char[] signature;
      byte receiverAndArgsSize;
      switch (typeID) {
         case 0:
         case 1:
         case 11:
         case 12:
            signature = ConstantPool.ValueOfObjectSignature;
            receiverAndArgsSize = 1;
            break;
         case 2:
            signature = ConstantPool.ValueOfCharSignature;
            receiverAndArgsSize = 1;
            break;
         case 3:
         case 4:
         case 10:
            signature = ConstantPool.ValueOfIntSignature;
            receiverAndArgsSize = 1;
            break;
         case 5:
            signature = ConstantPool.ValueOfBooleanSignature;
            receiverAndArgsSize = 1;
            break;
         case 6:
         default:
            return;
         case 7:
            signature = ConstantPool.ValueOfLongSignature;
            receiverAndArgsSize = 2;
            break;
         case 8:
            signature = ConstantPool.ValueOfDoubleSignature;
            receiverAndArgsSize = 2;
            break;
         case 9:
            signature = ConstantPool.ValueOfFloatSignature;
            receiverAndArgsSize = 1;
      }

      this.invoke((byte)-72, receiverAndArgsSize, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, signature);
   }

   public void invokeSystemArraycopy() {
      this.invoke((byte)-72, 5, 0, ConstantPool.JavaLangSystemConstantPoolName, ConstantPool.ArrayCopy, ConstantPool.ArrayCopySignature);
   }

   public void invokeThrowableGetMessage() {
      this.invoke((byte)-74, 1, 1, ConstantPool.JavaLangThrowableConstantPoolName, ConstantPool.GetMessage, ConstantPool.GetMessageSignature);
   }

   public void ior() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -128;
   }

   public void irem() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 112;
   }

   public void ireturn() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -84;
      this.lastAbruptCompletion = this.position;
   }

   public boolean isDefinitelyAssigned(Scope scope, int initStateIndex, LocalVariableBinding local) {
      if ((local.tagBits & 1024L) != 0L) {
         return true;
      } else if (initStateIndex == -1) {
         return false;
      } else {
         int localPosition = local.id + this.maxFieldCount;
         MethodScope methodScope = scope.methodScope();
         if (localPosition < 64) {
            return (methodScope.definiteInits[initStateIndex] & 1L << localPosition) != 0L;
         } else {
            long[] extraInits = methodScope.extraDefiniteInits[initStateIndex];
            if (extraInits == null) {
               return false;
            } else {
               int vectorIndex;
               if ((vectorIndex = localPosition / 64 - 1) >= extraInits.length) {
                  return false;
               } else {
                  return (extraInits[vectorIndex] & 1L << localPosition % 64) != 0L;
               }
            }
         }
      }
   }

   public void ishl() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 120;
   }

   public void ishr() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 122;
   }

   public void istore(int iArg) {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= iArg) {
         this.maxLocals = iArg + 1;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 54;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 54;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void istore_0() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals == 0) {
         this.maxLocals = 1;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 59;
   }

   public void istore_1() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 1) {
         this.maxLocals = 2;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 60;
   }

   public void istore_2() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 2) {
         this.maxLocals = 3;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 61;
   }

   public void istore_3() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.maxLocals <= 3) {
         this.maxLocals = 4;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 62;
   }

   public void isub() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 100;
   }

   public void iushr() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 124;
   }

   public void ixor() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -126;
   }

   public final void jsr(BranchLabel lbl) {
      if (this.wideMode) {
         this.jsr_w(lbl);
      } else {
         this.countLabels = 0;
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = -88;
         lbl.branch();
      }
   }

   public final void jsr_w(BranchLabel lbl) {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -55;
      lbl.branchWide();
   }

   public void l2d() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -118;
   }

   public void l2f() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -119;
   }

   public void l2i() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -120;
   }

   public void ladd() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 97;
   }

   public void laload() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 47;
   }

   public void land() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 127;
   }

   public void lastore() {
      this.countLabels = 0;
      this.stackDepth -= 4;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 80;
   }

   public void lcmp() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -108;
   }

   public void lconst_0() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 9;
   }

   public void lconst_1() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 10;
   }

   public void ldc(float constant) {
      this.countLabels = 0;
      int index = this.constantPool.literalIndex(constant);
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (index > 255) {
         if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = 19;
         this.writeUnsignedShort(index);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 18;
         this.bCodeStream[this.classFileOffset++] = (byte)index;
      }

   }

   public void ldc(int constant) {
      this.countLabels = 0;
      int index = this.constantPool.literalIndex(constant);
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (index > 255) {
         if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = 19;
         this.writeUnsignedShort(index);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 18;
         this.bCodeStream[this.classFileOffset++] = (byte)index;
      }

   }

   public void ldc(String constant) {
      this.countLabels = 0;
      int currentCodeStreamPosition = this.position;
      char[] constantChars = constant.toCharArray();
      int index = this.constantPool.literalIndexForLdc(constantChars);
      if (index > 0) {
         this.ldcForIndex(index);
      } else {
         this.position = currentCodeStreamPosition;
         int i = 0;
         int length = 0;
         int constantLength = constant.length();
         byte[] utf8encoding = new byte[Math.min(constantLength + 100, 65535)];

         int utf8encodingLength;
         for(int utf8encodingLength = false; length < 65532 && i < constantLength; ++i) {
            char current = constantChars[i];
            if (length + 3 > (utf8encodingLength = utf8encoding.length)) {
               System.arraycopy(utf8encoding, 0, utf8encoding = new byte[Math.min(utf8encodingLength + 100, 65535)], 0, length);
            }

            if (current >= 1 && current <= 127) {
               utf8encoding[length++] = (byte)current;
            } else if (current > 2047) {
               utf8encoding[length++] = (byte)(224 | current >> 12 & 15);
               utf8encoding[length++] = (byte)(128 | current >> 6 & 63);
               utf8encoding[length++] = (byte)(128 | current & 63);
            } else {
               utf8encoding[length++] = (byte)(192 | current >> 6 & 31);
               utf8encoding[length++] = (byte)(128 | current & 63);
            }
         }

         this.newStringContatenation();
         this.dup();
         char[] subChars = new char[i];
         System.arraycopy(constantChars, 0, subChars, 0, i);
         System.arraycopy(utf8encoding, 0, utf8encoding = new byte[length], 0, length);
         index = this.constantPool.literalIndex(subChars, utf8encoding);
         this.ldcForIndex(index);
         this.invokeStringConcatenationStringConstructor();

         while(i < constantLength) {
            length = 0;
            utf8encoding = new byte[Math.min(constantLength - i + 100, 65535)];

            int startIndex;
            int current;
            for(startIndex = i; length < 65532 && i < constantLength; ++i) {
               current = constantChars[i];
               if (length + 3 > (utf8encodingLength = utf8encoding.length)) {
                  System.arraycopy(utf8encoding, 0, utf8encoding = new byte[Math.min(utf8encodingLength + 100, 65535)], 0, length);
               }

               if (current >= 1 && current <= 127) {
                  utf8encoding[length++] = (byte)current;
               } else if (current > 2047) {
                  utf8encoding[length++] = (byte)(224 | current >> 12 & 15);
                  utf8encoding[length++] = (byte)(128 | current >> 6 & 63);
                  utf8encoding[length++] = (byte)(128 | current & 63);
               } else {
                  utf8encoding[length++] = (byte)(192 | current >> 6 & 31);
                  utf8encoding[length++] = (byte)(128 | current & 63);
               }
            }

            current = i - startIndex;
            subChars = new char[current];
            System.arraycopy(constantChars, startIndex, subChars, 0, current);
            System.arraycopy(utf8encoding, 0, utf8encoding = new byte[length], 0, length);
            index = this.constantPool.literalIndex(subChars, utf8encoding);
            this.ldcForIndex(index);
            this.invokeStringConcatenationAppendForType(11);
         }

         this.invokeStringConcatenationToString();
         this.invokeStringIntern();
      }

   }

   public void ldc(TypeBinding typeBinding) {
      this.countLabels = 0;
      int index = this.constantPool.literalIndexForType(typeBinding);
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (index > 255) {
         if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = 19;
         this.writeUnsignedShort(index);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 18;
         this.bCodeStream[this.classFileOffset++] = (byte)index;
      }

   }

   public void ldc2_w(double constant) {
      this.countLabels = 0;
      int index = this.constantPool.literalIndex(constant);
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 20;
      this.writeUnsignedShort(index);
   }

   public void ldc2_w(long constant) {
      this.countLabels = 0;
      int index = this.constantPool.literalIndex(constant);
      this.stackDepth += 2;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 20;
      this.writeUnsignedShort(index);
   }

   public void ldcForIndex(int index) {
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (index > 255) {
         if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = 19;
         this.writeUnsignedShort(index);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 18;
         this.bCodeStream[this.classFileOffset++] = (byte)index;
      }

   }

   public void ldiv() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 109;
   }

   public void lload(int iArg) {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.maxLocals <= iArg + 1) {
         this.maxLocals = iArg + 2;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 22;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 22;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void lload_0() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.maxLocals < 2) {
         this.maxLocals = 2;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 30;
   }

   public void lload_1() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.maxLocals < 3) {
         this.maxLocals = 3;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 31;
   }

   public void lload_2() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.maxLocals < 4) {
         this.maxLocals = 4;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 32;
   }

   public void lload_3() {
      this.countLabels = 0;
      this.stackDepth += 2;
      if (this.maxLocals < 5) {
         this.maxLocals = 5;
      }

      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 33;
   }

   public void lmul() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 105;
   }

   public void lneg() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 117;
   }

   public final void load(LocalVariableBinding localBinding) {
      this.load(localBinding.type, localBinding.resolvedPosition);
   }

   protected final void load(TypeBinding typeBinding, int resolvedPosition) {
      this.countLabels = 0;
      switch (typeBinding.id) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 10:
            switch (resolvedPosition) {
               case 0:
                  this.iload_0();
                  return;
               case 1:
                  this.iload_1();
                  return;
               case 2:
                  this.iload_2();
                  return;
               case 3:
                  this.iload_3();
                  return;
               default:
                  this.iload(resolvedPosition);
                  return;
            }
         case 6:
         default:
            switch (resolvedPosition) {
               case 0:
                  this.aload_0();
                  return;
               case 1:
                  this.aload_1();
                  return;
               case 2:
                  this.aload_2();
                  return;
               case 3:
                  this.aload_3();
                  return;
               default:
                  this.aload(resolvedPosition);
                  return;
            }
         case 7:
            switch (resolvedPosition) {
               case 0:
                  this.lload_0();
                  return;
               case 1:
                  this.lload_1();
                  return;
               case 2:
                  this.lload_2();
                  return;
               case 3:
                  this.lload_3();
                  return;
               default:
                  this.lload(resolvedPosition);
                  return;
            }
         case 8:
            switch (resolvedPosition) {
               case 0:
                  this.dload_0();
                  return;
               case 1:
                  this.dload_1();
                  return;
               case 2:
                  this.dload_2();
                  return;
               case 3:
                  this.dload_3();
                  return;
               default:
                  this.dload(resolvedPosition);
                  return;
            }
         case 9:
            switch (resolvedPosition) {
               case 0:
                  this.fload_0();
                  break;
               case 1:
                  this.fload_1();
                  break;
               case 2:
                  this.fload_2();
                  break;
               case 3:
                  this.fload_3();
                  break;
               default:
                  this.fload(resolvedPosition);
            }
      }

   }

   public void lookupswitch(CaseLabel defaultLabel, int[] keys, int[] sortedIndexes, CaseLabel[] casesLabel) {
      this.countLabels = 0;
      --this.stackDepth;
      int length = keys.length;
      int pos = this.position;
      defaultLabel.placeInstruction();

      int i;
      for(i = 0; i < length; ++i) {
         casesLabel[i].placeInstruction();
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -85;

      for(i = 3 - (pos & 3); i > 0; --i) {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = 0;
      }

      defaultLabel.branch();
      this.writeSignedWord(length);

      for(i = 0; i < length; ++i) {
         this.writeSignedWord(keys[sortedIndexes[i]]);
         casesLabel[sortedIndexes[i]].branch();
      }

   }

   public void lor() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -127;
   }

   public void lrem() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 113;
   }

   public void lreturn() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -83;
      this.lastAbruptCompletion = this.position;
   }

   public void lshl() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 121;
   }

   public void lshr() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 123;
   }

   public void lstore(int iArg) {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals <= iArg + 1) {
         this.maxLocals = iArg + 2;
      }

      if (iArg > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = 55;
         this.writeUnsignedShort(iArg);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = 55;
         this.bCodeStream[this.classFileOffset++] = (byte)iArg;
      }

   }

   public void lstore_0() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 2) {
         this.maxLocals = 2;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 63;
   }

   public void lstore_1() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 3) {
         this.maxLocals = 3;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 64;
   }

   public void lstore_2() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 4) {
         this.maxLocals = 4;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 65;
   }

   public void lstore_3() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.maxLocals < 5) {
         this.maxLocals = 5;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 66;
   }

   public void lsub() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 101;
   }

   public void lushr() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 125;
   }

   public void lxor() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -125;
   }

   public void monitorenter() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -62;
   }

   public void monitorexit() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -61;
   }

   public void multianewarray(TypeReference typeReference, TypeBinding typeBinding, int dimensions, ArrayAllocationExpression allocationExpression) {
      this.countLabels = 0;
      this.stackDepth += 1 - dimensions;
      if (this.classFileOffset + 3 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      this.position += 2;
      this.bCodeStream[this.classFileOffset++] = -59;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
      this.bCodeStream[this.classFileOffset++] = (byte)dimensions;
   }

   public void new_(TypeBinding typeBinding) {
      this.new_((TypeReference)null, typeBinding);
   }

   public void new_(TypeReference typeReference, TypeBinding typeBinding) {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 3 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -69;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
   }

   public void newarray(int array_Type) {
      this.countLabels = 0;
      if (this.classFileOffset + 1 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      this.position += 2;
      this.bCodeStream[this.classFileOffset++] = -68;
      this.bCodeStream[this.classFileOffset++] = (byte)array_Type;
   }

   public void newArray(ArrayBinding arrayBinding) {
      this.newArray((TypeReference)null, (ArrayAllocationExpression)null, arrayBinding);
   }

   public void newArray(TypeReference typeReference, ArrayAllocationExpression allocationExpression, ArrayBinding arrayBinding) {
      TypeBinding component = arrayBinding.elementsType();
      switch (component.id) {
         case 2:
            this.newarray(5);
            break;
         case 3:
            this.newarray(8);
            break;
         case 4:
            this.newarray(9);
            break;
         case 5:
            this.newarray(4);
            break;
         case 6:
         default:
            this.anewarray(component);
            break;
         case 7:
            this.newarray(11);
            break;
         case 8:
            this.newarray(7);
            break;
         case 9:
            this.newarray(6);
            break;
         case 10:
            this.newarray(10);
      }

   }

   public void newJavaLangAssertionError() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -69;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangAssertionErrorConstantPoolName));
   }

   public void newJavaLangError() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -69;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangErrorConstantPoolName));
   }

   public void newJavaLangIncompatibleClassChangeError() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -69;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangIncompatibleClassChangeErrorConstantPoolName));
   }

   public void newNoClassDefFoundError() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -69;
      this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangNoClassDefFoundErrorConstantPoolName));
   }

   public void newStringContatenation() {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -69;
      if (this.targetLevel >= 3211264L) {
         this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangStringBuilderConstantPoolName));
      } else {
         this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangStringBufferConstantPoolName));
      }

   }

   public void newWrapperFor(int typeID) {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset + 2 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -69;
      switch (typeID) {
         case 2:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangCharacterConstantPoolName));
            break;
         case 3:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangByteConstantPoolName));
            break;
         case 4:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangShortConstantPoolName));
            break;
         case 5:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangBooleanConstantPoolName));
            break;
         case 6:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangVoidConstantPoolName));
            break;
         case 7:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangLongConstantPoolName));
            break;
         case 8:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangDoubleConstantPoolName));
            break;
         case 9:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangFloatConstantPoolName));
            break;
         case 10:
            this.writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangIntegerConstantPoolName));
      }

   }

   public void nop() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 0;
   }

   public void optimizeBranch(int oldPosition, BranchLabel lbl) {
      for(int i = 0; i < this.countLabels; ++i) {
         BranchLabel label = this.labels[i];
         if (oldPosition == label.position) {
            label.position = this.position;
            int j;
            int forwardRef;
            if (label instanceof CaseLabel) {
               int offset = this.position - ((CaseLabel)label).instructionPosition;
               int[] forwardRefs = label.forwardReferences();
               j = 0;

               for(forwardRef = label.forwardReferenceCount(); j < forwardRef; ++j) {
                  int forwardRef = forwardRefs[j];
                  this.writeSignedWord(forwardRef, offset);
               }
            } else {
               int[] forwardRefs = label.forwardReferences();
               int j = 0;

               for(j = label.forwardReferenceCount(); j < j; ++j) {
                  forwardRef = forwardRefs[j];
                  this.writePosition(lbl, forwardRef);
               }
            }
         }
      }

   }

   public void pop() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 87;
   }

   public void pop2() {
      this.countLabels = 0;
      this.stackDepth -= 2;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 88;
   }

   public void pushExceptionOnStack(TypeBinding binding) {
      this.stackDepth = 1;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

   }

   public void pushOnStack(TypeBinding binding) {
      if (++this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

   }

   public void record(LocalVariableBinding local) {
      if ((this.generateAttributes & 28) != 0) {
         if (this.allLocalsCounter == this.locals.length) {
            System.arraycopy(this.locals, 0, this.locals = new LocalVariableBinding[this.allLocalsCounter + 10], 0, this.allLocalsCounter);
         }

         this.locals[this.allLocalsCounter++] = local;
         local.initializationPCs = new int[4];
         local.initializationCount = 0;
      }
   }

   public void recordExpressionType(TypeBinding typeBinding) {
   }

   public void recordExpressionType(TypeBinding typeBinding, int delta, boolean adjustStackDepth) {
   }

   public void recordPositionsFrom(int startPC, int sourcePos) {
      this.recordPositionsFrom(startPC, sourcePos, false);
   }

   public void recordPositionsFrom(int startPC, int sourcePos, boolean widen) {
      if ((this.generateAttributes & 2) != 0 && sourcePos != 0 && (startPC != this.position || widen) && startPC <= this.position) {
         if (this.pcToSourceMapSize + 4 > this.pcToSourceMap.length) {
            System.arraycopy(this.pcToSourceMap, 0, this.pcToSourceMap = new int[this.pcToSourceMapSize << 1], 0, this.pcToSourceMapSize);
         }

         int lineNumber;
         if (this.pcToSourceMapSize > 0) {
            lineNumber = -1;
            int previousLineNumber = this.pcToSourceMap[this.pcToSourceMapSize - 1];
            if (this.lineNumberStart == this.lineNumberEnd) {
               lineNumber = this.lineNumberStart;
            } else {
               int[] lineSeparatorPositions2 = this.lineSeparatorPositions;
               int length = lineSeparatorPositions2.length;
               if (previousLineNumber == 1) {
                  if (sourcePos < lineSeparatorPositions2[0]) {
                     lineNumber = 1;
                  } else if (length == 1 || sourcePos < lineSeparatorPositions2[1]) {
                     lineNumber = 2;
                  }
               } else if (previousLineNumber < length) {
                  if (lineSeparatorPositions2[previousLineNumber - 2] < sourcePos) {
                     if (sourcePos < lineSeparatorPositions2[previousLineNumber - 1]) {
                        lineNumber = previousLineNumber;
                     } else if (sourcePos < lineSeparatorPositions2[previousLineNumber]) {
                        lineNumber = previousLineNumber + 1;
                     }
                  }
               } else if (lineSeparatorPositions2[length - 1] < sourcePos) {
                  lineNumber = length + 1;
               }

               if (lineNumber == -1) {
                  lineNumber = Util.getLineNumber(sourcePos, lineSeparatorPositions2, this.lineNumberStart - 1, this.lineNumberEnd - 1);
               }
            }

            int insertionIndex;
            if (previousLineNumber != lineNumber) {
               if (startPC <= this.lastEntryPC) {
                  insertionIndex = insertionIndex(this.pcToSourceMap, this.pcToSourceMapSize, startPC);
                  if (insertionIndex != -1) {
                     if (insertionIndex <= 1 || this.pcToSourceMap[insertionIndex - 1] != lineNumber) {
                        if (insertionIndex < this.pcToSourceMapSize && this.pcToSourceMap[insertionIndex + 1] == lineNumber) {
                           this.pcToSourceMap[insertionIndex] = startPC;
                        } else {
                           System.arraycopy(this.pcToSourceMap, insertionIndex, this.pcToSourceMap, insertionIndex + 2, this.pcToSourceMapSize - insertionIndex);
                           this.pcToSourceMap[insertionIndex++] = startPC;
                           this.pcToSourceMap[insertionIndex] = lineNumber;
                           this.pcToSourceMapSize += 2;
                        }
                     }
                  } else if (this.position != this.lastEntryPC) {
                     if (this.lastEntryPC != startPC && this.lastEntryPC != this.pcToSourceMap[this.pcToSourceMapSize - 2]) {
                        this.pcToSourceMap[this.pcToSourceMapSize++] = this.lastEntryPC;
                        this.pcToSourceMap[this.pcToSourceMapSize++] = lineNumber;
                     } else {
                        this.pcToSourceMap[this.pcToSourceMapSize - 1] = lineNumber;
                     }
                  } else if (this.pcToSourceMap[this.pcToSourceMapSize - 1] < lineNumber && widen) {
                     this.pcToSourceMap[this.pcToSourceMapSize - 1] = lineNumber;
                  }
               } else {
                  this.pcToSourceMap[this.pcToSourceMapSize++] = startPC;
                  this.pcToSourceMap[this.pcToSourceMapSize++] = lineNumber;
               }
            } else if (startPC < this.pcToSourceMap[this.pcToSourceMapSize - 2]) {
               insertionIndex = insertionIndex(this.pcToSourceMap, this.pcToSourceMapSize, startPC);
               if (insertionIndex != -1 && (insertionIndex <= 1 || this.pcToSourceMap[insertionIndex - 1] != lineNumber)) {
                  if (this.pcToSourceMap[insertionIndex + 1] != lineNumber) {
                     System.arraycopy(this.pcToSourceMap, insertionIndex, this.pcToSourceMap, insertionIndex + 2, this.pcToSourceMapSize - insertionIndex);
                     this.pcToSourceMap[insertionIndex++] = startPC;
                     this.pcToSourceMap[insertionIndex] = lineNumber;
                     this.pcToSourceMapSize += 2;
                  } else {
                     this.pcToSourceMap[insertionIndex] = startPC;
                  }
               }
            }

            this.lastEntryPC = this.position;
         } else {
            int lineNumber = false;
            if (this.lineNumberStart == this.lineNumberEnd) {
               lineNumber = this.lineNumberStart;
            } else {
               lineNumber = Util.getLineNumber(sourcePos, this.lineSeparatorPositions, this.lineNumberStart - 1, this.lineNumberEnd - 1);
            }

            this.pcToSourceMap[this.pcToSourceMapSize++] = startPC;
            this.pcToSourceMap[this.pcToSourceMapSize++] = lineNumber;
            this.lastEntryPC = this.position;
         }

      }
   }

   public void registerExceptionHandler(ExceptionLabel anExceptionLabel) {
      int length;
      if (this.exceptionLabelsCounter == (length = this.exceptionLabels.length)) {
         System.arraycopy(this.exceptionLabels, 0, this.exceptionLabels = new ExceptionLabel[length + 5], 0, length);
      }

      this.exceptionLabels[this.exceptionLabelsCounter++] = anExceptionLabel;
   }

   public void removeNotDefinitelyAssignedVariables(Scope scope, int initStateIndex) {
      if ((this.generateAttributes & 28) != 0) {
         for(int i = 0; i < this.visibleLocalsCount; ++i) {
            LocalVariableBinding localBinding = this.visibleLocals[i];
            if (localBinding != null && !this.isDefinitelyAssigned(scope, initStateIndex, localBinding) && localBinding.initializationCount > 0) {
               localBinding.recordInitializationEndPC(this.position);
            }
         }

      }
   }

   public void removeUnusedPcToSourceMapEntries() {
      if (this.pcToSourceMapSize != 0) {
         while(this.pcToSourceMapSize >= 2 && this.pcToSourceMap[this.pcToSourceMapSize - 2] > this.position) {
            this.pcToSourceMapSize -= 2;
         }
      }

   }

   public void removeVariable(LocalVariableBinding localBinding) {
      if (localBinding != null) {
         if (localBinding.initializationCount > 0) {
            localBinding.recordInitializationEndPC(this.position);
         }

         for(int i = this.visibleLocalsCount - 1; i >= 0; --i) {
            LocalVariableBinding visibleLocal = this.visibleLocals[i];
            if (visibleLocal == localBinding) {
               this.visibleLocals[i] = null;
               return;
            }
         }

      }
   }

   public void reset(AbstractMethodDeclaration referenceMethod, ClassFile targetClassFile) {
      this.init(targetClassFile);
      this.methodDeclaration = referenceMethod;
      this.lambdaExpression = null;
      int[] lineSeparatorPositions2 = this.lineSeparatorPositions;
      if (lineSeparatorPositions2 != null) {
         int length = lineSeparatorPositions2.length;
         int lineSeparatorPositionsEnd = length - 1;
         if (!referenceMethod.isClinit() && !referenceMethod.isConstructor()) {
            int start = Util.getLineNumber(referenceMethod.bodyStart, lineSeparatorPositions2, 0, lineSeparatorPositionsEnd);
            this.lineNumberStart = start;
            if (start > lineSeparatorPositionsEnd) {
               this.lineNumberEnd = start;
            } else {
               int end = Util.getLineNumber(referenceMethod.bodyEnd, lineSeparatorPositions2, start - 1, lineSeparatorPositionsEnd);
               if (end >= lineSeparatorPositionsEnd) {
                  end = length;
               }

               this.lineNumberEnd = end == 0 ? 1 : end;
            }
         } else {
            this.lineNumberStart = 1;
            this.lineNumberEnd = length == 0 ? 1 : length;
         }
      }

      this.preserveUnusedLocals = referenceMethod.scope.compilerOptions().preserveAllLocalVariables;
      this.initializeMaxLocals(referenceMethod.binding);
   }

   public void reset(LambdaExpression lambda, ClassFile targetClassFile) {
      this.init(targetClassFile);
      this.lambdaExpression = lambda;
      this.methodDeclaration = null;
      int[] lineSeparatorPositions2 = this.lineSeparatorPositions;
      if (lineSeparatorPositions2 != null) {
         int length = lineSeparatorPositions2.length;
         int lineSeparatorPositionsEnd = length - 1;
         int start = Util.getLineNumber(lambda.body().sourceStart, lineSeparatorPositions2, 0, lineSeparatorPositionsEnd);
         this.lineNumberStart = start;
         if (start > lineSeparatorPositionsEnd) {
            this.lineNumberEnd = start;
         } else {
            int end = Util.getLineNumber(lambda.body().sourceEnd, lineSeparatorPositions2, start - 1, lineSeparatorPositionsEnd);
            if (end >= lineSeparatorPositionsEnd) {
               end = length;
            }

            this.lineNumberEnd = end == 0 ? 1 : end;
         }
      }

      this.preserveUnusedLocals = lambda.scope.compilerOptions().preserveAllLocalVariables;
      this.initializeMaxLocals(lambda.binding);
   }

   public void reset(ClassFile givenClassFile) {
      this.targetLevel = givenClassFile.targetJDK;
      int produceAttributes = givenClassFile.produceAttributes;
      this.generateAttributes = produceAttributes;
      if ((produceAttributes & 2) != 0 && givenClassFile.referenceBinding != null) {
         this.lineSeparatorPositions = givenClassFile.referenceBinding.scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions();
      } else {
         this.lineSeparatorPositions = null;
      }

   }

   public void resetForProblemClinit(ClassFile targetClassFile) {
      this.init(targetClassFile);
      this.initializeMaxLocals((MethodBinding)null);
   }

   public void resetInWideMode() {
      this.wideMode = true;
   }

   public void resetForCodeGenUnusedLocals() {
   }

   private final void resizeByteArray() {
      int length = this.bCodeStream.length;
      int requiredSize = length + length;
      if (this.classFileOffset >= requiredSize) {
         requiredSize = this.classFileOffset + length;
      }

      System.arraycopy(this.bCodeStream, 0, this.bCodeStream = new byte[requiredSize], 0, length);
   }

   public final void ret(int index) {
      this.countLabels = 0;
      if (index > 255) {
         if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -60;
         this.bCodeStream[this.classFileOffset++] = -87;
         this.writeUnsignedShort(index);
      } else {
         if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         this.position += 2;
         this.bCodeStream[this.classFileOffset++] = -87;
         this.bCodeStream[this.classFileOffset++] = (byte)index;
      }

   }

   public void return_() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -79;
      this.lastAbruptCompletion = this.position;
   }

   public void saload() {
      this.countLabels = 0;
      --this.stackDepth;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 53;
   }

   public void sastore() {
      this.countLabels = 0;
      this.stackDepth -= 3;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 86;
   }

   public void sendOperator(int operatorConstant, int type_ID) {
      switch (type_ID) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 10:
            switch (operatorConstant) {
               case 2:
                  this.iand();
                  break;
               case 3:
                  this.ior();
               case 4:
               case 5:
               case 6:
               case 7:
               case 11:
               case 12:
               case 18:
               default:
                  break;
               case 8:
                  this.ixor();
                  break;
               case 9:
                  this.idiv();
                  break;
               case 10:
                  this.ishl();
                  break;
               case 13:
                  this.isub();
                  break;
               case 14:
                  this.iadd();
                  break;
               case 15:
                  this.imul();
                  break;
               case 16:
                  this.irem();
                  break;
               case 17:
                  this.ishr();
                  break;
               case 19:
                  this.iushr();
            }
         case 6:
         default:
            break;
         case 7:
            switch (operatorConstant) {
               case 2:
                  this.land();
                  return;
               case 3:
                  this.lor();
                  return;
               case 4:
               case 5:
               case 6:
               case 7:
               case 11:
               case 12:
               case 18:
               default:
                  return;
               case 8:
                  this.lxor();
                  return;
               case 9:
                  this.ldiv();
                  return;
               case 10:
                  this.lshl();
                  return;
               case 13:
                  this.lsub();
                  return;
               case 14:
                  this.ladd();
                  return;
               case 15:
                  this.lmul();
                  return;
               case 16:
                  this.lrem();
                  return;
               case 17:
                  this.lshr();
                  return;
               case 19:
                  this.lushr();
                  return;
            }
         case 8:
            switch (operatorConstant) {
               case 9:
                  this.ddiv();
                  return;
               case 10:
               case 11:
               case 12:
               default:
                  return;
               case 13:
                  this.dsub();
                  return;
               case 14:
                  this.dadd();
                  return;
               case 15:
                  this.dmul();
                  return;
               case 16:
                  this.drem();
                  return;
            }
         case 9:
            switch (operatorConstant) {
               case 9:
                  this.fdiv();
               case 10:
               case 11:
               case 12:
               default:
                  break;
               case 13:
                  this.fsub();
                  break;
               case 14:
                  this.fadd();
                  break;
               case 15:
                  this.fmul();
                  break;
               case 16:
                  this.frem();
            }
      }

   }

   public void sipush(int s) {
      this.countLabels = 0;
      ++this.stackDepth;
      if (this.stackDepth > this.stackMax) {
         this.stackMax = this.stackDepth;
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 17;
      this.writeSignedShort(s);
   }

   public void store(LocalVariableBinding localBinding, boolean valueRequired) {
      int localPosition = localBinding.resolvedPosition;
      switch (localBinding.type.id) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 10:
            if (valueRequired) {
               this.dup();
            }

            switch (localPosition) {
               case 0:
                  this.istore_0();
                  return;
               case 1:
                  this.istore_1();
                  return;
               case 2:
                  this.istore_2();
                  return;
               case 3:
                  this.istore_3();
                  return;
               default:
                  this.istore(localPosition);
                  return;
            }
         case 6:
         default:
            if (valueRequired) {
               this.dup();
            }

            switch (localPosition) {
               case 0:
                  this.astore_0();
                  return;
               case 1:
                  this.astore_1();
                  return;
               case 2:
                  this.astore_2();
                  return;
               case 3:
                  this.astore_3();
                  return;
               default:
                  this.astore(localPosition);
                  return;
            }
         case 7:
            if (valueRequired) {
               this.dup2();
            }

            switch (localPosition) {
               case 0:
                  this.lstore_0();
                  return;
               case 1:
                  this.lstore_1();
                  return;
               case 2:
                  this.lstore_2();
                  return;
               case 3:
                  this.lstore_3();
                  return;
               default:
                  this.lstore(localPosition);
                  return;
            }
         case 8:
            if (valueRequired) {
               this.dup2();
            }

            switch (localPosition) {
               case 0:
                  this.dstore_0();
                  return;
               case 1:
                  this.dstore_1();
                  return;
               case 2:
                  this.dstore_2();
                  return;
               case 3:
                  this.dstore_3();
                  return;
               default:
                  this.dstore(localPosition);
                  return;
            }
         case 9:
            if (valueRequired) {
               this.dup();
            }

            switch (localPosition) {
               case 0:
                  this.fstore_0();
                  break;
               case 1:
                  this.fstore_1();
                  break;
               case 2:
                  this.fstore_2();
                  break;
               case 3:
                  this.fstore_3();
                  break;
               default:
                  this.fstore(localPosition);
            }
      }

   }

   public void swap() {
      this.countLabels = 0;
      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = 95;
   }

   public void tableswitch(CaseLabel defaultLabel, int low, int high, int[] keys, int[] sortedIndexes, int[] mapping, CaseLabel[] casesLabel) {
      this.countLabels = 0;
      --this.stackDepth;
      int length = casesLabel.length;
      int pos = this.position;
      defaultLabel.placeInstruction();

      int i;
      for(i = 0; i < length; ++i) {
         casesLabel[i].placeInstruction();
      }

      if (this.classFileOffset >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      ++this.position;
      this.bCodeStream[this.classFileOffset++] = -86;

      for(i = 3 - (pos & 3); i > 0; --i) {
         if (this.classFileOffset >= this.bCodeStream.length) {
            this.resizeByteArray();
         }

         ++this.position;
         this.bCodeStream[this.classFileOffset++] = 0;
      }

      defaultLabel.branch();
      this.writeSignedWord(low);
      this.writeSignedWord(high);
      i = low;
      int j = low;

      while(true) {
         int index;
         int key = keys[index = sortedIndexes[j - low]];
         if (key == i) {
            casesLabel[mapping[index]].branch();
            ++j;
            if (i == high) {
               return;
            }
         } else {
            defaultLabel.branch();
         }

         ++i;
      }
   }

   public void throwAnyException(LocalVariableBinding anyExceptionVariable) {
      this.load(anyExceptionVariable);
      this.athrow();
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer("( position:");
      buffer.append(this.position);
      buffer.append(",\nstackDepth:");
      buffer.append(this.stackDepth);
      buffer.append(",\nmaxStack:");
      buffer.append(this.stackMax);
      buffer.append(",\nmaxLocals:");
      buffer.append(this.maxLocals);
      buffer.append(")");
      return buffer.toString();
   }

   protected void writePosition(BranchLabel label) {
      int offset = label.position - this.position + 1;
      if (Math.abs(offset) > 32767 && !this.wideMode) {
         throw new AbortMethod(RESTART_IN_WIDE_MODE, (CategorizedProblem)null);
      } else {
         this.writeSignedShort(offset);
         int[] forwardRefs = label.forwardReferences();
         int i = 0;

         for(int max = label.forwardReferenceCount(); i < max; ++i) {
            this.writePosition(label, forwardRefs[i]);
         }

      }
   }

   protected void writePosition(BranchLabel label, int forwardReference) {
      int offset = label.position - forwardReference + 1;
      if (Math.abs(offset) > 32767 && !this.wideMode) {
         throw new AbortMethod(RESTART_IN_WIDE_MODE, (CategorizedProblem)null);
      } else {
         if (this.wideMode) {
            if ((label.tagBits & 1) != 0) {
               this.writeSignedWord(forwardReference, offset);
            } else {
               this.writeSignedShort(forwardReference, offset);
            }
         } else {
            this.writeSignedShort(forwardReference, offset);
         }

      }
   }

   private final void writeSignedShort(int value) {
      if (this.classFileOffset + 1 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      this.position += 2;
      this.bCodeStream[this.classFileOffset++] = (byte)(value >> 8);
      this.bCodeStream[this.classFileOffset++] = (byte)value;
   }

   private final void writeSignedShort(int pos, int value) {
      int currentOffset = this.startingClassFileOffset + pos;
      if (currentOffset + 1 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      this.bCodeStream[currentOffset] = (byte)(value >> 8);
      this.bCodeStream[currentOffset + 1] = (byte)value;
   }

   protected final void writeSignedWord(int value) {
      if (this.classFileOffset + 3 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      this.position += 4;
      this.bCodeStream[this.classFileOffset++] = (byte)((value & -16777216) >> 24);
      this.bCodeStream[this.classFileOffset++] = (byte)((value & 16711680) >> 16);
      this.bCodeStream[this.classFileOffset++] = (byte)((value & '\uff00') >> 8);
      this.bCodeStream[this.classFileOffset++] = (byte)(value & 255);
   }

   protected void writeSignedWord(int pos, int value) {
      int currentOffset = this.startingClassFileOffset + pos;
      if (currentOffset + 3 >= this.bCodeStream.length) {
         this.resizeByteArray();
      }

      this.bCodeStream[currentOffset++] = (byte)((value & -16777216) >> 24);
      this.bCodeStream[currentOffset++] = (byte)((value & 16711680) >> 16);
      this.bCodeStream[currentOffset++] = (byte)((value & '\uff00') >> 8);
      this.bCodeStream[currentOffset++] = (byte)(value & 255);
   }

   private final void writeUnsignedShort(int value) {
      this.position += 2;
      this.bCodeStream[this.classFileOffset++] = (byte)(value >>> 8);
      this.bCodeStream[this.classFileOffset++] = (byte)value;
   }

   protected void writeWidePosition(BranchLabel label) {
      int labelPos = label.position;
      int offset = labelPos - this.position + 1;
      this.writeSignedWord(offset);
      int[] forwardRefs = label.forwardReferences();
      int i = 0;

      for(int max = label.forwardReferenceCount(); i < max; ++i) {
         int forward = forwardRefs[i];
         offset = labelPos - forward + 1;
         this.writeSignedWord(forward, offset);
      }

   }
}
