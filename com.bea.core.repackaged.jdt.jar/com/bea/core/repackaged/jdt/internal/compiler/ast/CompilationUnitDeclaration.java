package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.Constant;
import com.bea.core.repackaged.jdt.internal.compiler.impl.IrritantSet;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.CompilationUnitScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ImportBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ModuleBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Scope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.parser.NLSTag;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortType;
import com.bea.core.repackaged.jdt.internal.compiler.problem.DefaultProblem;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemSeverities;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashSetOfInt;
import java.util.Arrays;
import java.util.Comparator;

public class CompilationUnitDeclaration extends ASTNode implements ProblemSeverities, ReferenceContext {
   private static final Comparator STRING_LITERAL_COMPARATOR = new Comparator() {
      public int compare(Object o1, Object o2) {
         StringLiteral literal1 = (StringLiteral)o1;
         StringLiteral literal2 = (StringLiteral)o2;
         return literal1.sourceStart - literal2.sourceStart;
      }
   };
   private static final int STRING_LITERALS_INCREMENT = 10;
   public ImportReference currentPackage;
   public ImportReference[] imports;
   public TypeDeclaration[] types;
   public ModuleDeclaration moduleDeclaration;
   public int[][] comments;
   public boolean ignoreFurtherInvestigation = false;
   public boolean ignoreMethodBodies = false;
   public CompilationUnitScope scope;
   public ProblemReporter problemReporter;
   public CompilationResult compilationResult;
   public LocalTypeBinding[] localTypes;
   public int localTypeCount = 0;
   public boolean isPropagatingInnerClassEmulation;
   public Javadoc javadoc;
   public NLSTag[] nlsTags;
   private StringLiteral[] stringLiterals;
   private int stringLiteralsPtr;
   private HashSetOfInt stringLiteralsStart;
   public boolean[] validIdentityComparisonLines;
   IrritantSet[] suppressWarningIrritants;
   Annotation[] suppressWarningAnnotations;
   long[] suppressWarningScopePositions;
   int suppressWarningsCount;
   public int functionalExpressionsCount;
   public FunctionalExpression[] functionalExpressions;

   public CompilationUnitDeclaration(ProblemReporter problemReporter, CompilationResult compilationResult, int sourceLength) {
      this.problemReporter = problemReporter;
      this.compilationResult = compilationResult;
      this.sourceStart = 0;
      this.sourceEnd = sourceLength - 1;
   }

   public void abort(int abortLevel, CategorizedProblem problem) {
      switch (abortLevel) {
         case 8:
            throw new AbortType(this.compilationResult, problem);
         case 16:
            throw new AbortMethod(this.compilationResult, problem);
         default:
            throw new AbortCompilationUnit(this.compilationResult, problem);
      }
   }

   public void analyseCode() {
      if (!this.ignoreFurtherInvestigation) {
         try {
            if (this.types != null) {
               int i = 0;

               for(int count = this.types.length; i < count; ++i) {
                  this.types[i].analyseCode(this.scope);
               }
            }

            if (this.moduleDeclaration != null) {
               this.moduleDeclaration.analyseCode(this.scope);
            }

            this.propagateInnerEmulationForAllLocalTypes();
         } catch (AbortCompilationUnit var3) {
            this.ignoreFurtherInvestigation = true;
         }
      }
   }

   public void cleanUp() {
      int i;
      int i;
      if (this.types != null) {
         i = 0;

         for(i = this.types.length; i < i; ++i) {
            this.cleanUp(this.types[i]);
         }

         i = 0;

         for(i = this.localTypeCount; i < i; ++i) {
            LocalTypeBinding localType = this.localTypes[i];
            localType.scope = null;
            localType.enclosingCase = null;
         }
      }

      if (this.functionalExpressionsCount > 0) {
         i = 0;

         for(i = this.functionalExpressionsCount; i < i; ++i) {
            this.functionalExpressions[i].cleanUp();
         }
      }

      this.compilationResult.recoveryScannerData = null;
      ClassFile[] classFiles = this.compilationResult.getClassFiles();
      i = 0;

      for(int max = classFiles.length; i < max; ++i) {
         ClassFile classFile = classFiles[i];
         classFile.referenceBinding = null;
         classFile.innerClassesBindings = null;
         classFile.bootstrapMethods = null;
         classFile.missingTypes = null;
         classFile.visitedTypes = null;
      }

      this.suppressWarningAnnotations = null;
      if (this.scope != null) {
         this.scope.cleanUpInferenceContexts();
      }

   }

   private void cleanUp(TypeDeclaration type) {
      if (type.memberTypes != null) {
         int i = 0;

         for(int max = type.memberTypes.length; i < max; ++i) {
            this.cleanUp(type.memberTypes[i]);
         }
      }

      if (type.binding != null && type.binding.isAnnotationType()) {
         this.compilationResult.hasAnnotations = true;
      }

      if (type.binding != null) {
         type.binding.scope = null;
      }

   }

   public void checkUnusedImports() {
      if (this.scope.imports != null) {
         int i = 0;

         for(int max = this.scope.imports.length; i < max; ++i) {
            ImportBinding importBinding = this.scope.imports[i];
            ImportReference importReference = importBinding.reference;
            if (importReference != null && (importReference.bits & 2) == 0) {
               this.scope.problemReporter().unusedImport(importReference);
            }
         }
      }

   }

   public CompilationResult compilationResult() {
      return this.compilationResult;
   }

   public void createPackageInfoType() {
      TypeDeclaration declaration = new TypeDeclaration(this.compilationResult);
      declaration.name = TypeConstants.PACKAGE_INFO_NAME;
      declaration.modifiers = 512;
      declaration.javadoc = this.javadoc;
      this.types[0] = declaration;
   }

   public TypeDeclaration declarationOfType(char[][] typeName) {
      for(int i = 0; i < this.types.length; ++i) {
         TypeDeclaration typeDecl = this.types[i].declarationOfType(typeName);
         if (typeDecl != null) {
            return typeDecl;
         }
      }

      return null;
   }

   public void finalizeProblems() {
      int problemCount = this.compilationResult.problemCount;
      CategorizedProblem[] problems = this.compilationResult.problems;
      if (this.suppressWarningsCount != 0) {
         int removed = 0;
         IrritantSet[] foundIrritants = new IrritantSet[this.suppressWarningsCount];
         CompilerOptions options = this.scope.compilerOptions();
         boolean hasMandatoryErrors = false;
         int iProblem = 0;

         int index;
         CategorizedProblem problem;
         int suppressCount;
         int iPair;
         int pairCount;
         int iToken;
         for(index = problemCount; iProblem < index; ++iProblem) {
            problem = problems[iProblem];
            suppressCount = problem.getID();
            int irritant = ProblemReporter.getIrritant(suppressCount);
            boolean isError = problem.isError();
            if (isError) {
               if (irritant == 0) {
                  hasMandatoryErrors = true;
                  continue;
               }

               if (!options.suppressOptionalErrors) {
                  continue;
               }
            }

            int start = problem.getSourceStart();
            iPair = problem.getSourceEnd();
            pairCount = 0;

            for(int suppressCount = this.suppressWarningsCount; pairCount < suppressCount; ++pairCount) {
               long position = this.suppressWarningScopePositions[pairCount];
               int startSuppress = (int)(position >>> 32);
               iToken = (int)position;
               if (start >= startSuppress && iPair <= iToken) {
                  if (this.suppressWarningIrritants[pairCount].isSet(irritant)) {
                     ++removed;
                     problems[iProblem] = null;
                     this.compilationResult.removeProblem(problem);
                     if (foundIrritants[pairCount] == null) {
                        foundIrritants[pairCount] = new IrritantSet(irritant);
                     } else {
                        foundIrritants[pairCount].set(irritant);
                     }
                     break;
                  }

                  if (problem instanceof DefaultProblem) {
                     ((DefaultProblem)problem).reportError();
                  }
               }
            }
         }

         if (removed > 0) {
            iProblem = 0;

            for(index = 0; iProblem < problemCount; ++iProblem) {
               if ((problem = problems[iProblem]) != null) {
                  if (iProblem > index) {
                     problems[index++] = problem;
                  } else {
                     ++index;
                  }
               }
            }
         }

         if (!hasMandatoryErrors) {
            iProblem = options.getSeverity(570425344);
            if (iProblem != 256) {
               boolean unusedWarningTokenIsWarning = (iProblem & 1) == 0;
               int iSuppress = 0;

               label181:
               for(suppressCount = this.suppressWarningsCount; iSuppress < suppressCount; ++iSuppress) {
                  Annotation annotation = this.suppressWarningAnnotations[iSuppress];
                  if (annotation != null) {
                     IrritantSet irritants = this.suppressWarningIrritants[iSuppress];
                     if ((!unusedWarningTokenIsWarning || !irritants.areAllSet()) && irritants != foundIrritants[iSuppress]) {
                        MemberValuePair[] pairs = annotation.memberValuePairs();
                        iPair = 0;

                        for(pairCount = pairs.length; iPair < pairCount; ++iPair) {
                           MemberValuePair pair = pairs[iPair];
                           if (CharOperation.equals(pair.name, TypeConstants.VALUE)) {
                              Expression value = pair.value;
                              int end;
                              int end;
                              int jSuppress;
                              if (value instanceof ArrayInitializer) {
                                 ArrayInitializer initializer = (ArrayInitializer)value;
                                 Expression[] inits = initializer.expressions;
                                 if (inits == null) {
                                    break;
                                 }

                                 iToken = 0;
                                 end = inits.length;

                                 while(true) {
                                    if (iToken >= end) {
                                       continue label181;
                                    }

                                    Constant cst = inits[iToken].constant;
                                    if (cst != Constant.NotAConstant && cst.typeID() == 11) {
                                       IrritantSet tokenIrritants = CompilerOptions.warningTokenToIrritants(cst.stringValue());
                                       if (tokenIrritants != null && !tokenIrritants.areAllSet() && (foundIrritants[iSuppress] == null || !foundIrritants[iSuppress].isAnySet(tokenIrritants))) {
                                          int start;
                                          if (unusedWarningTokenIsWarning) {
                                             start = value.sourceStart;
                                             end = value.sourceEnd;

                                             for(jSuppress = iSuppress - 1; jSuppress >= 0; --jSuppress) {
                                                long position = this.suppressWarningScopePositions[jSuppress];
                                                int startSuppress = (int)(position >>> 32);
                                                int endSuppress = (int)position;
                                                if (start >= startSuppress && end <= endSuppress && this.suppressWarningIrritants[jSuppress].areAllSet()) {
                                                   continue label181;
                                                }
                                             }
                                          }

                                          start = options.getIgnoredIrritant(tokenIrritants);
                                          if (start > 0) {
                                             String key = CompilerOptions.optionKeyFromIrritant(start);
                                             this.scope.problemReporter().problemNotAnalysed(inits[iToken], key);
                                          } else {
                                             this.scope.problemReporter().unusedWarningToken(inits[iToken]);
                                          }
                                       }
                                    }

                                    ++iToken;
                                 }
                              }

                              Constant cst = value.constant;
                              if (cst == Constant.NotAConstant || cst.typeID() != 11) {
                                 break;
                              }

                              IrritantSet tokenIrritants = CompilerOptions.warningTokenToIrritants(cst.stringValue());
                              if (tokenIrritants == null || tokenIrritants.areAllSet() || foundIrritants[iSuppress] != null && foundIrritants[iSuppress].isAnySet(tokenIrritants)) {
                                 break;
                              }

                              if (unusedWarningTokenIsWarning) {
                                 iToken = value.sourceStart;
                                 end = value.sourceEnd;

                                 for(int jSuppress = iSuppress - 1; jSuppress >= 0; --jSuppress) {
                                    long position = this.suppressWarningScopePositions[jSuppress];
                                    end = (int)(position >>> 32);
                                    jSuppress = (int)position;
                                    if (iToken >= end && end <= jSuppress && this.suppressWarningIrritants[jSuppress].areAllSet()) {
                                       continue label181;
                                    }
                                 }
                              }

                              iToken = options.getIgnoredIrritant(tokenIrritants);
                              if (iToken > 0) {
                                 String key = CompilerOptions.optionKeyFromIrritant(iToken);
                                 this.scope.problemReporter().problemNotAnalysed(value, key);
                              } else {
                                 this.scope.problemReporter().unusedWarningToken(value);
                              }
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public void generateCode() {
      int i;
      int count;
      if (this.ignoreFurtherInvestigation) {
         if (this.types != null) {
            i = 0;

            for(count = this.types.length; i < count; ++i) {
               this.types[i].ignoreFurtherInvestigation = true;
               this.types[i].generateCode(this.scope);
            }
         }

      } else {
         try {
            if (this.types != null) {
               i = 0;

               for(count = this.types.length; i < count; ++i) {
                  this.types[i].generateCode(this.scope);
               }
            }

            if (this.moduleDeclaration != null) {
               this.moduleDeclaration.generateCode();
            }
         } catch (AbortCompilationUnit var3) {
         }

      }
   }

   public CompilationUnitDeclaration getCompilationUnitDeclaration() {
      return this;
   }

   public char[] getFileName() {
      return this.compilationResult.getFileName();
   }

   public char[] getMainTypeName() {
      if (this.compilationResult.compilationUnit != null) {
         return this.compilationResult.compilationUnit.getMainTypeName();
      } else {
         char[] fileName = this.compilationResult.getFileName();
         int start = CharOperation.lastIndexOf('/', fileName) + 1;
         if (start == 0 || start < CharOperation.lastIndexOf('\\', fileName)) {
            start = CharOperation.lastIndexOf('\\', fileName) + 1;
         }

         int end = CharOperation.lastIndexOf('.', fileName);
         if (end == -1) {
            end = fileName.length;
         }

         return CharOperation.subarray(fileName, start, end);
      }
   }

   public boolean isEmpty() {
      return this.currentPackage == null && this.imports == null && this.types == null;
   }

   public boolean isPackageInfo() {
      return CharOperation.equals(this.getMainTypeName(), TypeConstants.PACKAGE_INFO_NAME);
   }

   public boolean isModuleInfo() {
      return CharOperation.equals(this.getMainTypeName(), TypeConstants.MODULE_INFO_NAME);
   }

   public boolean isSuppressed(CategorizedProblem problem) {
      if (this.suppressWarningsCount == 0) {
         return false;
      } else {
         int irritant = ProblemReporter.getIrritant(problem.getID());
         if (irritant == 0) {
            return false;
         } else {
            int start = problem.getSourceStart();
            int end = problem.getSourceEnd();
            int iSuppress = 0;

            for(int suppressCount = this.suppressWarningsCount; iSuppress < suppressCount; ++iSuppress) {
               long position = this.suppressWarningScopePositions[iSuppress];
               int startSuppress = (int)(position >>> 32);
               int endSuppress = (int)position;
               if (start >= startSuppress && end <= endSuppress && this.suppressWarningIrritants[iSuppress].isSet(irritant)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public boolean hasFunctionalTypes() {
      return this.compilationResult.hasFunctionalTypes;
   }

   public boolean hasErrors() {
      return this.ignoreFurtherInvestigation;
   }

   public StringBuffer print(int indent, StringBuffer output) {
      if (this.currentPackage != null) {
         printIndent(indent, output).append("package ");
         this.currentPackage.print(0, output, false).append(";\n");
      }

      int i;
      if (this.imports != null) {
         for(i = 0; i < this.imports.length; ++i) {
            printIndent(indent, output).append("import ");
            ImportReference currentImport = this.imports[i];
            if (currentImport.isStatic()) {
               output.append("static ");
            }

            currentImport.print(0, output).append(";\n");
         }
      }

      if (this.moduleDeclaration != null) {
         this.moduleDeclaration.print(indent, output).append("\n");
      } else if (this.types != null) {
         for(i = 0; i < this.types.length; ++i) {
            this.types[i].print(indent, output).append("\n");
         }
      }

      return output;
   }

   public void propagateInnerEmulationForAllLocalTypes() {
      this.isPropagatingInnerClassEmulation = true;
      int i = 0;

      for(int max = this.localTypeCount; i < max; ++i) {
         LocalTypeBinding localType = this.localTypes[i];
         if ((localType.scope.referenceType().bits & Integer.MIN_VALUE) != 0) {
            localType.updateInnerEmulationDependents();
         }
      }

   }

   public void recordStringLiteral(StringLiteral literal, boolean fromRecovery) {
      int stackLength;
      if (this.stringLiteralsStart != null) {
         if (this.stringLiteralsStart.contains(literal.sourceStart)) {
            return;
         }

         this.stringLiteralsStart.add(literal.sourceStart);
      } else if (fromRecovery) {
         this.stringLiteralsStart = new HashSetOfInt(this.stringLiteralsPtr + 10);

         for(stackLength = 0; stackLength < this.stringLiteralsPtr; ++stackLength) {
            this.stringLiteralsStart.add(this.stringLiterals[stackLength].sourceStart);
         }

         if (this.stringLiteralsStart.contains(literal.sourceStart)) {
            return;
         }

         this.stringLiteralsStart.add(literal.sourceStart);
      }

      if (this.stringLiterals == null) {
         this.stringLiterals = new StringLiteral[10];
         this.stringLiteralsPtr = 0;
      } else {
         stackLength = this.stringLiterals.length;
         if (this.stringLiteralsPtr == stackLength) {
            System.arraycopy(this.stringLiterals, 0, this.stringLiterals = new StringLiteral[stackLength + 10], 0, stackLength);
         }
      }

      this.stringLiterals[this.stringLiteralsPtr++] = literal;
   }

   private boolean isLambdaExpressionCopyContext(ReferenceContext context) {
      if (context instanceof LambdaExpression && context != ((LambdaExpression)context).original()) {
         return true;
      } else {
         Scope cScope = context instanceof AbstractMethodDeclaration ? ((AbstractMethodDeclaration)context).scope : (context instanceof TypeDeclaration ? ((TypeDeclaration)context).scope : (context instanceof LambdaExpression ? ((LambdaExpression)context).scope : null));
         return cScope != null ? this.isLambdaExpressionCopyContext(((Scope)cScope).parent.referenceContext()) : false;
      }
   }

   public void recordSuppressWarnings(IrritantSet irritants, Annotation annotation, int scopeStart, int scopeEnd, ReferenceContext context) {
      if (!this.isLambdaExpressionCopyContext(context)) {
         if (this.suppressWarningIrritants == null) {
            this.suppressWarningIrritants = new IrritantSet[3];
            this.suppressWarningAnnotations = new Annotation[3];
            this.suppressWarningScopePositions = new long[3];
         } else if (this.suppressWarningIrritants.length == this.suppressWarningsCount) {
            System.arraycopy(this.suppressWarningIrritants, 0, this.suppressWarningIrritants = new IrritantSet[2 * this.suppressWarningsCount], 0, this.suppressWarningsCount);
            System.arraycopy(this.suppressWarningAnnotations, 0, this.suppressWarningAnnotations = new Annotation[2 * this.suppressWarningsCount], 0, this.suppressWarningsCount);
            System.arraycopy(this.suppressWarningScopePositions, 0, this.suppressWarningScopePositions = new long[2 * this.suppressWarningsCount], 0, this.suppressWarningsCount);
         }

         long scopePositions = ((long)scopeStart << 32) + (long)scopeEnd;
         int i = 0;

         for(int max = this.suppressWarningsCount; i < max; ++i) {
            if (this.suppressWarningAnnotations[i] == annotation && this.suppressWarningScopePositions[i] == scopePositions && this.suppressWarningIrritants[i].hasSameIrritants(irritants)) {
               return;
            }
         }

         this.suppressWarningIrritants[this.suppressWarningsCount] = irritants;
         this.suppressWarningAnnotations[this.suppressWarningsCount] = annotation;
         this.suppressWarningScopePositions[this.suppressWarningsCount++] = scopePositions;
      }
   }

   public void record(LocalTypeBinding localType) {
      if (this.localTypeCount == 0) {
         this.localTypes = new LocalTypeBinding[5];
      } else if (this.localTypeCount == this.localTypes.length) {
         System.arraycopy(this.localTypes, 0, this.localTypes = new LocalTypeBinding[this.localTypeCount * 2], 0, this.localTypeCount);
      }

      this.localTypes[this.localTypeCount++] = localType;
   }

   public int record(FunctionalExpression expression) {
      if (this.functionalExpressionsCount == 0) {
         this.functionalExpressions = new FunctionalExpression[5];
      } else if (this.functionalExpressionsCount == this.functionalExpressions.length) {
         System.arraycopy(this.functionalExpressions, 0, this.functionalExpressions = new FunctionalExpression[this.functionalExpressionsCount * 2], 0, this.functionalExpressionsCount);
      }

      this.functionalExpressions[this.functionalExpressionsCount++] = expression;
      return expression.enclosingScope.classScope().referenceContext.record(expression);
   }

   public void resolve() {
      int startingTypeIndex = 0;
      boolean isPackageInfo = this.isPackageInfo();
      if (this.types != null && isPackageInfo) {
         TypeDeclaration syntheticTypeDeclaration = this.types[0];
         if (syntheticTypeDeclaration.javadoc == null) {
            syntheticTypeDeclaration.javadoc = new Javadoc(syntheticTypeDeclaration.declarationSourceStart, syntheticTypeDeclaration.declarationSourceStart);
         }

         syntheticTypeDeclaration.resolve(this.scope);
         if (this.javadoc != null && syntheticTypeDeclaration.staticInitializerScope != null) {
            this.javadoc.resolve(syntheticTypeDeclaration.staticInitializerScope);
         }

         startingTypeIndex = 1;
      } else if (this.javadoc != null) {
         this.javadoc.resolve(this.scope);
      }

      if (this.currentPackage != null && this.currentPackage.annotations != null && !isPackageInfo) {
         this.scope.problemReporter().invalidFileNameForPackageAnnotations(this.currentPackage.annotations[0]);
      }

      try {
         if (this.types != null) {
            int i = startingTypeIndex;

            for(int count = this.types.length; i < count; ++i) {
               this.types[i].resolve(this.scope);
            }
         }

         if (!this.compilationResult.hasMandatoryErrors()) {
            this.checkUnusedImports();
         }

         this.reportNLSProblems();
      } catch (AbortCompilationUnit var5) {
         this.ignoreFurtherInvestigation = true;
      }
   }

   private void reportNLSProblems() {
      if (this.nlsTags != null || this.stringLiterals != null) {
         int stringLiteralsLength = this.stringLiteralsPtr;
         int nlsTagsLength = this.nlsTags == null ? 0 : this.nlsTags.length;
         int indexInLine;
         if (stringLiteralsLength == 0) {
            if (nlsTagsLength != 0) {
               for(indexInLine = 0; indexInLine < nlsTagsLength; ++indexInLine) {
                  NLSTag tag = this.nlsTags[indexInLine];
                  if (tag != null) {
                     this.scope.problemReporter().unnecessaryNLSTags(tag.start, tag.end);
                  }
               }
            }
         } else if (nlsTagsLength == 0) {
            if (this.stringLiterals.length != stringLiteralsLength) {
               System.arraycopy(this.stringLiterals, 0, this.stringLiterals = new StringLiteral[stringLiteralsLength], 0, stringLiteralsLength);
            }

            Arrays.sort(this.stringLiterals, STRING_LITERAL_COMPARATOR);

            for(indexInLine = 0; indexInLine < stringLiteralsLength; ++indexInLine) {
               this.scope.problemReporter().nonExternalizedStringLiteral(this.stringLiterals[indexInLine]);
            }
         } else {
            if (this.stringLiterals.length != stringLiteralsLength) {
               System.arraycopy(this.stringLiterals, 0, this.stringLiterals = new StringLiteral[stringLiteralsLength], 0, stringLiteralsLength);
            }

            Arrays.sort(this.stringLiterals, STRING_LITERAL_COMPARATOR);
            indexInLine = 1;
            int lastLineNumber = -1;
            StringLiteral literal = null;
            int index = 0;

            int i;
            label109:
            for(i = 0; i < stringLiteralsLength; ++i) {
               literal = this.stringLiterals[i];
               int literalLineNumber = literal.lineNumber;
               if (lastLineNumber != literalLineNumber) {
                  indexInLine = 1;
                  lastLineNumber = literalLineNumber;
               } else {
                  ++indexInLine;
               }

               if (index >= nlsTagsLength) {
                  break;
               }

               while(true) {
                  if (index >= nlsTagsLength) {
                     break label109;
                  }

                  NLSTag tag = this.nlsTags[index];
                  if (tag != null) {
                     int tagLineNumber = tag.lineNumber;
                     if (literalLineNumber < tagLineNumber) {
                        this.scope.problemReporter().nonExternalizedStringLiteral(literal);
                        break;
                     }

                     if (literalLineNumber == tagLineNumber) {
                        if (tag.index == indexInLine) {
                           this.nlsTags[index] = null;
                           ++index;
                           break;
                        }

                        for(int index2 = index + 1; index2 < nlsTagsLength; ++index2) {
                           NLSTag tag2 = this.nlsTags[index2];
                           if (tag2 != null) {
                              int tagLineNumber2 = tag2.lineNumber;
                              if (literalLineNumber != tagLineNumber2) {
                                 this.scope.problemReporter().nonExternalizedStringLiteral(literal);
                                 continue label109;
                              }

                              if (tag2.index == indexInLine) {
                                 this.nlsTags[index2] = null;
                                 continue label109;
                              }
                           }
                        }

                        this.scope.problemReporter().nonExternalizedStringLiteral(literal);
                        break;
                     }

                     this.scope.problemReporter().unnecessaryNLSTags(tag.start, tag.end);
                  }

                  ++index;
               }
            }

            while(i < stringLiteralsLength) {
               this.scope.problemReporter().nonExternalizedStringLiteral(this.stringLiterals[i]);
               ++i;
            }

            if (index < nlsTagsLength) {
               for(; index < nlsTagsLength; ++index) {
                  NLSTag tag = this.nlsTags[index];
                  if (tag != null) {
                     this.scope.problemReporter().unnecessaryNLSTags(tag.start, tag.end);
                  }
               }
            }
         }
      }

   }

   public void tagAsHavingErrors() {
      this.ignoreFurtherInvestigation = true;
   }

   public void tagAsHavingIgnoredMandatoryErrors(int problemId) {
   }

   public void traverse(ASTVisitor visitor, CompilationUnitScope unitScope) {
      this.traverse(visitor, unitScope, true);
   }

   public void traverse(ASTVisitor visitor, CompilationUnitScope unitScope, boolean skipOnError) {
      if (!skipOnError || !this.ignoreFurtherInvestigation) {
         try {
            if (visitor.visit(this, this.scope)) {
               if (this.types != null && this.isPackageInfo()) {
                  TypeDeclaration syntheticTypeDeclaration = this.types[0];
                  MethodScope methodScope = syntheticTypeDeclaration.staticInitializerScope;
                  if (this.javadoc != null && methodScope != null) {
                     this.javadoc.traverse(visitor, (BlockScope)methodScope);
                  }

                  if (this.currentPackage != null && methodScope != null) {
                     Annotation[] annotations = this.currentPackage.annotations;
                     if (annotations != null) {
                        int annotationsLength = annotations.length;

                        for(int i = 0; i < annotationsLength; ++i) {
                           annotations[i].traverse(visitor, (BlockScope)methodScope);
                        }
                     }
                  }
               }

               if (this.currentPackage != null) {
                  this.currentPackage.traverse(visitor, this.scope);
               }

               int typesLength;
               int i;
               if (this.imports != null) {
                  typesLength = this.imports.length;

                  for(i = 0; i < typesLength; ++i) {
                     this.imports[i].traverse(visitor, this.scope);
                  }
               }

               if (this.types != null) {
                  typesLength = this.types.length;

                  for(i = 0; i < typesLength; ++i) {
                     this.types[i].traverse(visitor, this.scope);
                  }
               }

               if (this.isModuleInfo() && this.moduleDeclaration != null) {
                  this.moduleDeclaration.traverse(visitor, this.scope);
               }
            }

            visitor.endVisit(this, this.scope);
         } catch (AbortCompilationUnit var9) {
         }

      }
   }

   public ModuleBinding module(LookupEnvironment environment) {
      if (this.moduleDeclaration != null) {
         ModuleBinding binding = this.moduleDeclaration.binding;
         if (binding != null) {
            return binding;
         }
      }

      if (this.compilationResult != null) {
         ICompilationUnit compilationUnit = this.compilationResult.compilationUnit;
         if (compilationUnit != null) {
            return compilationUnit.module(environment);
         }
      }

      return environment.module;
   }
}
