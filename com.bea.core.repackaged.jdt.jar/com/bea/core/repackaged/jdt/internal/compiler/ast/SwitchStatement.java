package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CaseLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.SwitchFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.Arrays;

public class SwitchStatement extends Expression {
   public Expression expression;
   public Statement[] statements;
   public BlockScope scope;
   public int explicitDeclarations;
   public BranchLabel breakLabel;
   public CaseStatement[] cases;
   public CaseStatement defaultCase;
   public int blockStart;
   public int caseCount;
   int[] constants;
   int[] constMapping;
   String[] stringConstants;
   public boolean switchLabeledRules = false;
   public int nConstants;
   public static final int CASE = 0;
   public static final int FALLTHROUGH = 1;
   public static final int ESCAPING = 2;
   public static final int BREAKING = 3;
   private static final char[] SecretStringVariableName = " switchDispatchString".toCharArray();
   public SyntheticMethodBinding synthetic;
   int preSwitchInitStateIndex = -1;
   int mergedInitStateIndex = -1;
   CaseStatement[] duplicateCaseStatements = null;
   int duplicateCaseStatementsCounter = 0;
   private LocalVariableBinding dispatchStringCopy = null;

   protected int getFallThroughState(Statement stmt, BlockScope blockScope) {
      if (this.switchLabeledRules) {
         if (stmt instanceof Expression && ((Expression)stmt).isTrulyExpression() || stmt instanceof ThrowStatement) {
            return 3;
         }

         if (stmt instanceof Block) {
            Block block = (Block)stmt;
            if (block.doesNotCompleteNormally()) {
               return 3;
            }

            BreakStatement breakStatement = new BreakStatement((char[])null, block.sourceEnd - 1, block.sourceEnd);
            breakStatement.isImplicit = true;
            int l = block.statements == null ? 0 : block.statements.length;
            if (l == 0) {
               block.statements = new Statement[]{breakStatement};
               block.scope = this.scope;
            } else {
               Statement[] newArray = new Statement[l + 1];
               System.arraycopy(block.statements, 0, newArray, 0, l);
               newArray[l] = breakStatement;
               block.statements = newArray;
            }

            return 3;
         }
      }

      return 1;
   }

   protected void completeNormallyCheck(BlockScope blockScope) {
   }

   protected boolean needToCheckFlowInAbsenceOfDefaultBranch() {
      return true;
   }

   public FlowInfo analyseCode(BlockScope currentScope, FlowContext flowContext, FlowInfo flowInfo) {
      UnconditionalFlowInfo var14;
      try {
         flowInfo = this.expression.analyseCode(currentScope, flowContext, flowInfo);
         if ((this.expression.implicitConversion & 1024) != 0 || this.expression.resolvedType != null && (this.expression.resolvedType.id == 11 || this.expression.resolvedType.isEnum())) {
            this.expression.checkNPE(currentScope, flowContext, flowInfo, 1);
         }

         SwitchFlowContext switchContext = new SwitchFlowContext(flowContext, this, this.breakLabel = new BranchLabel(), true, true);
         FlowInfo caseInits = FlowInfo.DEAD_END;
         this.preSwitchInitStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
         int caseIndex = 0;
         if (this.statements != null) {
            int initialComplaintLevel = (flowInfo.reachMode() & 3) != 0 ? 1 : 0;
            int complaintLevel = initialComplaintLevel;
            int fallThroughState = 0;
            int i = 0;
            int max = this.statements.length;

            while(true) {
               if (i >= max) {
                  this.completeNormallyCheck(currentScope);
                  break;
               }

               Statement statement = this.statements[i];
               if (caseIndex < this.caseCount && statement == this.cases[caseIndex]) {
                  this.scope.enclosingCase = this.cases[caseIndex];
                  ++caseIndex;
                  if (fallThroughState == 1 && (statement.bits & 536870912) == 0) {
                     this.scope.problemReporter().possibleFallThroughCase(this.scope.enclosingCase);
                  }

                  caseInits = ((FlowInfo)caseInits).mergedWith(flowInfo.unconditionalInits());
                  complaintLevel = initialComplaintLevel;
                  fallThroughState = 0;
               } else if (statement == this.defaultCase) {
                  this.scope.enclosingCase = this.defaultCase;
                  if (fallThroughState == 1 && (statement.bits & 536870912) == 0) {
                     this.scope.problemReporter().possibleFallThroughCase(this.scope.enclosingCase);
                  }

                  caseInits = ((FlowInfo)caseInits).mergedWith(flowInfo.unconditionalInits());
                  complaintLevel = initialComplaintLevel;
                  fallThroughState = 0;
               } else {
                  fallThroughState = this.getFallThroughState(statement, currentScope);
               }

               if ((complaintLevel = statement.complainIfUnreachable((FlowInfo)caseInits, this.scope, complaintLevel, true)) < 2) {
                  caseInits = statement.analyseCode(this.scope, switchContext, (FlowInfo)caseInits);
                  if (caseInits == FlowInfo.DEAD_END) {
                     fallThroughState = 2;
                  }

                  switchContext.expireNullCheckedFieldInfo();
               }

               ++i;
            }
         }

         TypeBinding resolvedTypeBinding = this.expression.resolvedType;
         if (resolvedTypeBinding.isEnum()) {
            SourceTypeBinding sourceTypeBinding = currentScope.classScope().referenceContext.binding;
            this.synthetic = sourceTypeBinding.addSyntheticMethodForSwitchEnum(resolvedTypeBinding, this);
         }

         if (this.defaultCase == null && this.needToCheckFlowInAbsenceOfDefaultBranch()) {
            flowInfo.addPotentialInitializationsFrom(((FlowInfo)caseInits).mergedWith(switchContext.initsOnBreak));
            this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(flowInfo);
            FlowInfo var20 = flowInfo;
            return var20;
         }

         FlowInfo mergedInfo = ((FlowInfo)caseInits).mergedWith(switchContext.initsOnBreak);
         this.mergedInitStateIndex = currentScope.methodScope().recordInitializationStates(mergedInfo);
         var14 = mergedInfo;
      } finally {
         if (this.scope != null) {
            this.scope.enclosingCase = null;
         }

      }

      return var14;
   }

   public void generateCodeForStringSwitch(BlockScope currentScope, CodeStream codeStream) {
      try {
         if ((this.bits & Integer.MIN_VALUE) != 0) {
            int pc = codeStream.position;
            boolean hasCases = this.caseCount != 0;
            int constSize = hasCases ? this.stringConstants.length : 0;
            BranchLabel[] sourceCaseLabels;
            int j;
            int i;
            int uniqHashCount;
            int i;
            int i;
            if (currentScope.compilerOptions().complianceLevel >= 3670016L) {
               j = 0;

               for(i = this.caseCount; j < i; ++j) {
                  uniqHashCount = this.cases[j].constantExpressions.length;
                  this.cases[j].targetLabels = new BranchLabel[uniqHashCount];
               }

               sourceCaseLabels = new BranchLabel[this.nConstants];
               j = 0;
               i = 0;

               for(uniqHashCount = this.caseCount; i < uniqHashCount; ++i) {
                  CaseStatement stmt = this.cases[i];
                  i = 0;

                  for(i = stmt.constantExpressions.length; i < i; ++i) {
                     stmt.targetLabels[i] = sourceCaseLabels[j] = new BranchLabel(codeStream);
                     int var10001 = j++;
                     sourceCaseLabels[var10001].tagBits |= 2;
                  }
               }
            } else {
               sourceCaseLabels = new BranchLabel[this.caseCount];
               j = 0;

               for(i = this.caseCount; j < i; ++j) {
                  this.cases[j].targetLabel = sourceCaseLabels[j] = new BranchLabel(codeStream);
                  sourceCaseLabels[j].tagBits |= 2;
               }
            }

            class StringSwitchCase implements Comparable {
               int hashCode;
               String string;
               BranchLabel label;

               public StringSwitchCase(int hashCode, String string, BranchLabel label) {
                  this.hashCode = hashCode;
                  this.string = string;
                  this.label = label;
               }

               public int compareTo(Object o) {
                  StringSwitchCase that = (StringSwitchCase)o;
                  if (this.hashCode == that.hashCode) {
                     return 0;
                  } else {
                     return this.hashCode > that.hashCode ? 1 : -1;
                  }
               }

               public String toString() {
                  return "StringSwitchCase :\ncase " + this.hashCode + ":(" + this.string + ")\n";
               }
            }

            StringSwitchCase[] stringCases = new StringSwitchCase[constSize];
            CaseLabel[] hashCodeCaseLabels = new CaseLabel[constSize];
            this.constants = new int[constSize];

            for(uniqHashCount = 0; uniqHashCount < constSize; ++uniqHashCount) {
               stringCases[uniqHashCount] = new StringSwitchCase(this.stringConstants[uniqHashCount].hashCode(), this.stringConstants[uniqHashCount], sourceCaseLabels[this.constMapping[uniqHashCount]]);
               hashCodeCaseLabels[uniqHashCount] = new CaseLabel(codeStream);
               hashCodeCaseLabels[uniqHashCount].tagBits |= 2;
            }

            Arrays.sort(stringCases);
            uniqHashCount = 0;
            int lastHashCode = 0;
            i = 0;

            for(i = constSize; i < i; ++i) {
               int hashCode = stringCases[i].hashCode;
               if (i == 0 || hashCode != lastHashCode) {
                  lastHashCode = this.constants[uniqHashCount++] = hashCode;
               }
            }

            if (uniqHashCount != constSize) {
               System.arraycopy(this.constants, 0, this.constants = new int[uniqHashCount], 0, uniqHashCount);
               System.arraycopy(hashCodeCaseLabels, 0, hashCodeCaseLabels = new CaseLabel[uniqHashCount], 0, uniqHashCount);
            }

            int[] sortedIndexes = new int[uniqHashCount];

            for(i = 0; i < uniqHashCount; sortedIndexes[i] = i++) {
            }

            CaseLabel defaultCaseLabel = new CaseLabel(codeStream);
            defaultCaseLabel.tagBits |= 2;
            this.breakLabel.initialize(codeStream);
            BranchLabel defaultBranchLabel = new BranchLabel(codeStream);
            if (hasCases) {
               defaultBranchLabel.tagBits |= 2;
            }

            if (this.defaultCase != null) {
               this.defaultCase.targetLabel = defaultBranchLabel;
            }

            this.expression.generateCode(currentScope, codeStream, true);
            codeStream.store(this.dispatchStringCopy, true);
            codeStream.addVariable(this.dispatchStringCopy);
            codeStream.invokeStringHashCode();
            int caseIndex;
            int i;
            int maxCases;
            if (hasCases) {
               codeStream.lookupswitch(defaultCaseLabel, this.constants, sortedIndexes, hashCodeCaseLabels);
               caseIndex = 0;
               i = 0;

               for(maxCases = constSize; caseIndex < maxCases; ++caseIndex) {
                  int hashCode = stringCases[caseIndex].hashCode;
                  if (caseIndex == 0 || hashCode != lastHashCode) {
                     lastHashCode = hashCode;
                     if (caseIndex != 0) {
                        codeStream.goto_(defaultBranchLabel);
                     }

                     hashCodeCaseLabels[i++].place();
                  }

                  codeStream.load(this.dispatchStringCopy);
                  codeStream.ldc(stringCases[caseIndex].string);
                  codeStream.invokeStringEquals();
                  codeStream.ifne(stringCases[caseIndex].label);
               }

               codeStream.goto_(defaultBranchLabel);
            } else {
               codeStream.pop();
            }

            caseIndex = 0;
            if (this.statements != null) {
               i = 0;

               for(maxCases = this.statements.length; i < maxCases; ++i) {
                  Statement statement = this.statements[i];
                  if (caseIndex < this.caseCount && statement == this.cases[caseIndex]) {
                     this.scope.enclosingCase = this.cases[caseIndex];
                     if (this.preSwitchInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preSwitchInitStateIndex);
                     }

                     ++caseIndex;
                  } else if (statement == this.defaultCase) {
                     defaultCaseLabel.place();
                     this.scope.enclosingCase = this.defaultCase;
                     if (this.preSwitchInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preSwitchInitStateIndex);
                     }
                  }

                  this.statementGenerateCode(currentScope, codeStream, statement);
               }
            }

            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

            codeStream.removeVariable(this.dispatchStringCopy);
            if (this.scope != currentScope) {
               codeStream.exitUserScope(this.scope);
            }

            this.breakLabel.place();
            if (this.defaultCase == null) {
               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd, true);
               defaultCaseLabel.place();
               defaultBranchLabel.place();
            }

            if (this.expectedType() != null) {
               TypeBinding expectedType = this.expectedType().erasure();
               boolean optimizedGoto = codeStream.lastAbruptCompletion == -1;
               codeStream.recordExpressionType(expectedType, optimizedGoto ? 0 : 1, optimizedGoto);
            }

            codeStream.recordPositionsFrom(pc, this.sourceStart);
            return;
         }
      } finally {
         if (this.scope != null) {
            this.scope.enclosingCase = null;
         }

      }

   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream) {
      if (this.expression.resolvedType.id == 11) {
         this.generateCodeForStringSwitch(currentScope, codeStream);
      } else {
         try {
            if ((this.bits & Integer.MIN_VALUE) == 0) {
               return;
            }

            int pc = codeStream.position;
            this.breakLabel.initialize(codeStream);
            int constantCount = this.constants == null ? 0 : this.constants.length;
            int nCaseLabels = 0;
            CaseLabel[] caseLabels;
            int j;
            int i;
            int caseIndex;
            int i;
            if (currentScope.compilerOptions().complianceLevel < 3670016L) {
               caseLabels = new CaseLabel[this.caseCount];
               j = 0;

               for(i = this.caseCount; j < i; ++j) {
                  this.cases[j].targetLabel = caseLabels[j] = new CaseLabel(codeStream);
                  caseLabels[j].tagBits |= 2;
               }
            } else {
               j = 0;

               int max;
               for(i = this.caseCount; j < i; ++j) {
                  max = this.cases[j].constantExpressions.length;
                  nCaseLabels += max;
                  this.cases[j].targetLabels = new BranchLabel[max];
               }

               caseLabels = new CaseLabel[nCaseLabels];
               j = 0;
               i = 0;

               for(max = this.caseCount; i < max; ++i) {
                  CaseStatement stmt = this.cases[i];
                  caseIndex = 0;

                  for(i = stmt.constantExpressions.length; caseIndex < i; ++caseIndex) {
                     stmt.targetLabels[caseIndex] = caseLabels[j] = new CaseLabel(codeStream);
                     int var10001 = j++;
                     caseLabels[var10001].tagBits |= 2;
                  }
               }
            }

            CaseLabel defaultLabel = new CaseLabel(codeStream);
            boolean hasCases = this.caseCount != 0;
            if (hasCases) {
               defaultLabel.tagBits |= 2;
            }

            if (this.defaultCase != null) {
               this.defaultCase.targetLabel = defaultLabel;
            }

            TypeBinding resolvedType1 = this.expression.resolvedType;
            boolean valueRequired = false;
            if (resolvedType1.isEnum()) {
               codeStream.invoke((byte)-72, this.synthetic, (TypeBinding)null);
               this.expression.generateCode(currentScope, codeStream, true);
               codeStream.invokeEnumOrdinal(resolvedType1.constantPoolName());
               codeStream.iaload();
               if (!hasCases) {
                  codeStream.pop();
               }

               valueRequired = hasCases;
            } else {
               valueRequired = this.expression.constant == Constant.NotAConstant || hasCases;
               this.expression.generateCode(currentScope, codeStream, valueRequired);
            }

            int max;
            if (!hasCases) {
               if (valueRequired) {
                  codeStream.pop();
               }
            } else {
               int[] sortedIndexes = new int[constantCount];

               for(i = 0; i < constantCount; sortedIndexes[i] = i++) {
               }

               int[] localKeysCopy;
               System.arraycopy(this.constants, 0, localKeysCopy = new int[constantCount], 0, constantCount);
               CodeStream.sort(localKeysCopy, 0, constantCount - 1, sortedIndexes);
               max = localKeysCopy[constantCount - 1];
               int min = localKeysCopy[0];
               if ((long)((double)constantCount * 2.5) > (long)max - (long)min) {
                  if (max > 2147418112 && currentScope.compilerOptions().complianceLevel < 3145728L) {
                     codeStream.lookupswitch(defaultLabel, this.constants, sortedIndexes, caseLabels);
                  } else {
                     codeStream.tableswitch(defaultLabel, min, max, this.constants, sortedIndexes, this.constMapping, caseLabels);
                  }
               } else {
                  codeStream.lookupswitch(defaultLabel, this.constants, sortedIndexes, caseLabels);
               }

               codeStream.recordPositionsFrom(codeStream.position, this.expression.sourceEnd);
            }

            caseIndex = 0;
            if (this.statements != null) {
               i = 0;

               for(max = this.statements.length; i < max; ++i) {
                  Statement statement = this.statements[i];
                  if (caseIndex < constantCount && statement == this.cases[caseIndex]) {
                     this.scope.enclosingCase = this.cases[caseIndex];
                     if (this.preSwitchInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preSwitchInitStateIndex);
                     }

                     ++caseIndex;
                  } else if (statement == this.defaultCase) {
                     this.scope.enclosingCase = this.defaultCase;
                     if (this.preSwitchInitStateIndex != -1) {
                        codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preSwitchInitStateIndex);
                     }
                  }

                  this.statementGenerateCode(currentScope, codeStream, statement);
               }
            }

            boolean enumInSwitchExpression = resolvedType1.isEnum() && this instanceof SwitchExpression;
            boolean isEnumSwitchWithoutDefaultCase = this.defaultCase == null && enumInSwitchExpression;
            if (isEnumSwitchWithoutDefaultCase) {
               if (this.preSwitchInitStateIndex != -1) {
                  codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.preSwitchInitStateIndex);
               }

               defaultLabel.place();
               codeStream.newJavaLangIncompatibleClassChangeError();
               codeStream.dup();
               codeStream.invokeJavaLangIncompatibleClassChangeErrorDefaultConstructor();
               codeStream.athrow();
            }

            if (this.mergedInitStateIndex != -1) {
               codeStream.removeNotDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
               codeStream.addDefinitelyAssignedVariables(currentScope, this.mergedInitStateIndex);
            }

            if (this.scope != currentScope) {
               codeStream.exitUserScope(this.scope);
            }

            this.breakLabel.place();
            if (this.defaultCase == null && !enumInSwitchExpression) {
               codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd, true);
               defaultLabel.place();
            }

            if (this instanceof SwitchExpression) {
               TypeBinding switchResolveType = this.resolvedType;
               if (this.expectedType() != null) {
                  switchResolveType = this.expectedType().erasure();
               }

               boolean optimizedGoto = codeStream.lastAbruptCompletion == -1;
               codeStream.recordExpressionType(switchResolveType, optimizedGoto ? 0 : 1, optimizedGoto || isEnumSwitchWithoutDefaultCase);
            }

            codeStream.recordPositionsFrom(pc, this.sourceStart);
         } finally {
            if (this.scope != null) {
               this.scope.enclosingCase = null;
            }

         }

      }
   }

   protected void statementGenerateCode(BlockScope currentScope, CodeStream codeStream, Statement statement) {
      statement.generateCode(this.scope, codeStream);
   }

   public void generateCode(BlockScope currentScope, CodeStream codeStream, boolean valueRequired) {
      this.generateCode(currentScope, codeStream);
   }

   public StringBuffer printStatement(int indent, StringBuffer output) {
      printIndent(indent, output).append("switch (");
      this.expression.printExpression(0, output).append(") {");
      if (this.statements != null) {
         for(int i = 0; i < this.statements.length; ++i) {
            output.append('\n');
            if (this.statements[i] instanceof CaseStatement) {
               this.statements[i].printStatement(indent, output);
            } else {
               this.statements[i].printStatement(indent + 2, output);
            }
         }
      }

      output.append("\n");
      return printIndent(indent, output).append('}');
   }

   private int getNConstants() {
      int n = 0;
      int i = 0;

      for(int l = this.statements.length; i < l; ++i) {
         Statement statement = this.statements[i];
         if (statement instanceof CaseStatement) {
            CaseStatement caseStmt = (CaseStatement)statement;
            n += caseStmt.constantExpressions != null ? caseStmt.constantExpressions.length : (caseStmt.constantExpression != null ? 1 : 0);
         }
      }

      return n;
   }

   public void resolve(BlockScope upperScope) {
      try {
         boolean isEnumSwitch = false;
         boolean isStringSwitch = false;
         TypeBinding expressionType = this.expression.resolveType(upperScope);
         CompilerOptions compilerOptions = upperScope.compilerOptions();
         if (expressionType != null) {
            this.expression.computeConversion(upperScope, expressionType, expressionType);
            if (!expressionType.isValidBinding()) {
               expressionType = null;
            } else {
               label443: {
                  if (expressionType.isBaseType()) {
                     if (this.expression.isConstantValueOfTypeAssignableToType(expressionType, TypeBinding.INT) || expressionType.isCompatibleWith(TypeBinding.INT)) {
                        break label443;
                     }
                  } else {
                     if (expressionType.isEnum()) {
                        isEnumSwitch = true;
                        if (compilerOptions.complianceLevel < 3211264L) {
                           upperScope.problemReporter().incorrectSwitchType(this.expression, expressionType);
                        }
                        break label443;
                     }

                     if (upperScope.isBoxingCompatibleWith(expressionType, TypeBinding.INT)) {
                        this.expression.computeConversion(upperScope, TypeBinding.INT, expressionType);
                        break label443;
                     }

                     if (compilerOptions.complianceLevel >= 3342336L && expressionType.id == 11) {
                        isStringSwitch = true;
                        break label443;
                     }
                  }

                  upperScope.problemReporter().incorrectSwitchType(this.expression, expressionType);
                  expressionType = null;
               }
            }
         }

         if (isStringSwitch) {
            this.dispatchStringCopy = new LocalVariableBinding(SecretStringVariableName, upperScope.getJavaLangString(), 0, false);
            upperScope.addLocalVariable(this.dispatchStringCopy);
            this.dispatchStringCopy.setConstant(Constant.NotAConstant);
            this.dispatchStringCopy.useFlag = 1;
         }

         int length;
         int caseCounter;
         int max;
         if (this.statements == null) {
            if ((this.bits & 8) != 0) {
               upperScope.problemReporter().undocumentedEmptyBlock(this.blockStart, this.sourceEnd);
            }
         } else {
            this.scope = new BlockScope(upperScope);
            this.cases = new CaseStatement[length = this.statements.length];
            this.nConstants = this.getNConstants();
            if (!isStringSwitch) {
               this.constants = new int[this.nConstants];
               this.constMapping = new int[this.nConstants];
            } else {
               this.stringConstants = new String[this.nConstants];
               this.constMapping = new int[this.nConstants];
            }

            int counter = 0;
            caseCounter = 0;
            max = 0;

            while(true) {
               if (max >= length) {
                  if (length != counter) {
                     if (!isStringSwitch) {
                        System.arraycopy(this.constants, 0, this.constants = new int[counter], 0, counter);
                     } else {
                        System.arraycopy(this.stringConstants, 0, this.stringConstants = new String[counter], 0, counter);
                     }

                     System.arraycopy(this.constMapping, 0, this.constMapping = new int[counter], 0, counter);
                  }
                  break;
               }

               int[] caseIndex = new int[this.nConstants];
               Statement statement = this.statements[max];
               if (!(statement instanceof CaseStatement)) {
                  statement.resolve(this.scope);
               } else {
                  Constant[] constantsList;
                  if ((constantsList = statement.resolveCase(this.scope, expressionType, this)) != Constant.NotAConstantList) {
                     Constant[] var16 = constantsList;
                     int var15 = constantsList.length;

                     for(int var14 = 0; var14 < var15; ++var14) {
                        Constant con = var16[var14];
                        if (con != Constant.NotAConstant) {
                           int j;
                           if (!isStringSwitch) {
                              int key = con.intValue();

                              for(j = 0; j < counter; ++j) {
                                 if (this.constants[j] == key) {
                                    this.reportDuplicateCase((CaseStatement)statement, this.cases[caseIndex[j]], length);
                                 }
                              }

                              this.constants[counter] = key;
                           } else {
                              String key = con.stringValue();

                              for(j = 0; j < counter; ++j) {
                                 if (this.stringConstants[j].equals(key)) {
                                    this.reportDuplicateCase((CaseStatement)statement, this.cases[caseIndex[j]], length);
                                 }
                              }

                              this.stringConstants[counter] = key;
                           }

                           this.constMapping[counter] = counter;
                           caseIndex[counter] = caseCounter;
                           ++counter;
                        }
                     }
                  }

                  ++caseCounter;
               }

               ++max;
            }
         }

         this.reportMixingCaseTypes();
         if (this.defaultCase == null) {
            if (this.ignoreMissingDefaultCase(compilerOptions, isEnumSwitch)) {
               if (isEnumSwitch) {
                  upperScope.methodScope().hasMissingSwitchDefault = true;
               }
            } else {
               upperScope.problemReporter().missingDefaultCase(this, isEnumSwitch, expressionType);
            }
         }

         if (isEnumSwitch && compilerOptions.complianceLevel >= 3211264L && (this.defaultCase == null || compilerOptions.reportMissingEnumCaseDespiteDefault)) {
            length = this.constants == null ? 0 : this.constants.length;
            if (length >= this.caseCount && length != ((ReferenceBinding)expressionType).enumConstantCount()) {
               FieldBinding[] enumFields = ((ReferenceBinding)expressionType.erasure()).fields();
               caseCounter = 0;

               label357:
               for(max = enumFields.length; caseCounter < max; ++caseCounter) {
                  FieldBinding enumConstant = enumFields[caseCounter];
                  if ((enumConstant.modifiers & 16384) != 0) {
                     for(int j = 0; j < length; ++j) {
                        if (enumConstant.id + 1 == this.constants[j]) {
                           continue label357;
                        }
                     }

                     boolean suppress = this.defaultCase != null && (this.defaultCase.bits & 1073741824) != 0;
                     if (!suppress) {
                        this.reportMissingEnumConstantCase(upperScope, enumConstant);
                     }
                  }
               }
            }
         }
      } finally {
         if (this.scope != null) {
            this.scope.enclosingCase = null;
         }

      }

   }

   protected void reportMissingEnumConstantCase(BlockScope upperScope, FieldBinding enumConstant) {
      upperScope.problemReporter().missingEnumConstantCase(this, enumConstant);
   }

   protected boolean ignoreMissingDefaultCase(CompilerOptions compilerOptions, boolean isEnumSwitch) {
      return compilerOptions.getSeverity(1073774592) == 256;
   }

   public boolean isTrulyExpression() {
      return false;
   }

   private void reportMixingCaseTypes() {
      if (this.caseCount == 0) {
         this.switchLabeledRules = this.defaultCase != null ? this.defaultCase.isExpr : this.switchLabeledRules;
      } else {
         boolean isExpr = this.switchLabeledRules = this.cases[0].isExpr;
         int i = 1;

         for(int l = this.caseCount; i < l; ++i) {
            if (this.cases[i].isExpr != isExpr) {
               this.scope.problemReporter().switchExpressionMixedCase(this.cases[i]);
               return;
            }
         }

         if (this.defaultCase != null && this.defaultCase.isExpr != isExpr) {
            this.scope.problemReporter().switchExpressionMixedCase(this.defaultCase);
         }

      }
   }

   private void reportDuplicateCase(CaseStatement duplicate, CaseStatement original, int length) {
      if (this.duplicateCaseStatements == null) {
         this.scope.problemReporter().duplicateCase(original);
         if (duplicate != original) {
            this.scope.problemReporter().duplicateCase(duplicate);
         }

         this.duplicateCaseStatements = new CaseStatement[length];
         this.duplicateCaseStatements[this.duplicateCaseStatementsCounter++] = original;
         if (duplicate != original) {
            this.duplicateCaseStatements[this.duplicateCaseStatementsCounter++] = duplicate;
         }
      } else {
         boolean found = false;

         for(int k = 2; k < this.duplicateCaseStatementsCounter; ++k) {
            if (this.duplicateCaseStatements[k] == duplicate) {
               found = true;
               break;
            }
         }

         if (!found) {
            this.scope.problemReporter().duplicateCase(duplicate);
            this.duplicateCaseStatements[this.duplicateCaseStatementsCounter++] = duplicate;
         }
      }

   }

   public void traverse(ASTVisitor visitor, BlockScope blockScope) {
      if (visitor.visit(this, blockScope)) {
         this.expression.traverse(visitor, blockScope);
         if (this.statements != null) {
            int statementsLength = this.statements.length;

            for(int i = 0; i < statementsLength; ++i) {
               this.statements[i].traverse(visitor, this.scope);
            }
         }
      }

      visitor.endVisit(this, blockScope);
   }

   public void branchChainTo(BranchLabel label) {
      if (this.breakLabel.forwardReferenceCount() > 0) {
         label.becomeDelegateFor(this.breakLabel);
      }

   }

   public boolean doesNotCompleteNormally() {
      if (this.statements != null && this.statements.length != 0) {
         int i = 0;

         for(int length = this.statements.length; i < length; ++i) {
            if (this.statements[i].breaksOut((char[])null)) {
               return false;
            }
         }

         return this.statements[this.statements.length - 1].doesNotCompleteNormally();
      } else {
         return false;
      }
   }

   public boolean completesByContinue() {
      if (this.statements != null && this.statements.length != 0) {
         int i = 0;

         for(int length = this.statements.length; i < length; ++i) {
            if (this.statements[i].completesByContinue()) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public StringBuffer printExpression(int indent, StringBuffer output) {
      return this.printStatement(indent, output);
   }
}
