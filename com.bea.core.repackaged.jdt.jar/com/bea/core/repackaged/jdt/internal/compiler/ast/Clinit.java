package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.BranchLabel;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.ConstantPool;
import com.bea.core.repackaged.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InitializationFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;

public class Clinit extends AbstractMethodDeclaration {
   private static int ENUM_CONSTANTS_THRESHOLD = 2000;
   private FieldBinding assertionSyntheticFieldBinding = null;
   private FieldBinding classLiteralSyntheticField = null;

   public Clinit(CompilationResult compilationResult) {
      super(compilationResult);
      this.modifiers = 0;
      this.selector = TypeConstants.CLINIT;
   }

   public void analyseCode(ClassScope classScope, InitializationFlowContext staticInitializerFlowContext, FlowInfo flowInfo) {
      if (!this.ignoreFurtherInvestigation) {
         try {
            ExceptionHandlingFlowContext clinitContext = new ExceptionHandlingFlowContext(staticInitializerFlowContext.parent, this, Binding.NO_EXCEPTIONS, staticInitializerFlowContext, this.scope, FlowInfo.DEAD_END);
            if ((flowInfo.tagBits & 1) == 0) {
               this.bits |= 64;
            }

            FlowInfo flowInfo = flowInfo.mergedWith(staticInitializerFlowContext.initsOnReturn);
            FieldBinding[] fields = this.scope.enclosingSourceType().fields();
            int i = 0;

            for(int count = fields.length; i < count; ++i) {
               FieldBinding field = fields[i];
               if (field.isStatic() && !flowInfo.isDefinitelyAssigned(field)) {
                  if (field.isFinal()) {
                     this.scope.problemReporter().uninitializedBlankFinalField(field, this.scope.referenceType().declarationOf(field.original()));
                  } else if (field.isNonNull()) {
                     this.scope.problemReporter().uninitializedNonNullField(field, this.scope.referenceType().declarationOf(field.original()));
                  }
               }
            }

            staticInitializerFlowContext.checkInitializerExceptions(this.scope, clinitContext, flowInfo);
         } catch (AbortMethod var9) {
            this.ignoreFurtherInvestigation = true;
         }

      }
   }

   public void generateCode(ClassScope classScope, ClassFile classFile) {
      int clinitOffset = 0;
      if (!this.ignoreFurtherInvestigation) {
         CompilationResult unitResult = null;
         int problemCount = 0;
         if (classScope != null) {
            TypeDeclaration referenceContext = classScope.referenceContext;
            if (referenceContext != null) {
               unitResult = referenceContext.compilationResult();
               problemCount = unitResult.problemCount;
            }
         }

         boolean restart = false;

         do {
            try {
               clinitOffset = classFile.contentsOffset;
               this.generateCode(classScope, classFile, clinitOffset);
               restart = false;
            } catch (AbortMethod var8) {
               if (var8.compilationResult == CodeStream.RESTART_IN_WIDE_MODE) {
                  classFile.contentsOffset = clinitOffset;
                  --classFile.methodCount;
                  classFile.codeStream.resetInWideMode();
                  if (unitResult != null) {
                     unitResult.problemCount = problemCount;
                  }

                  restart = true;
               } else if (var8.compilationResult == CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE) {
                  classFile.contentsOffset = clinitOffset;
                  --classFile.methodCount;
                  classFile.codeStream.resetForCodeGenUnusedLocals();
                  if (unitResult != null) {
                     unitResult.problemCount = problemCount;
                  }

                  restart = true;
               } else {
                  classFile.contentsOffset = clinitOffset;
                  --classFile.methodCount;
                  restart = false;
               }
            }
         } while(restart);

      }
   }

   private void generateCode(ClassScope classScope, ClassFile classFile, int clinitOffset) {
      ConstantPool constantPool = classFile.constantPool;
      int constantPoolOffset = constantPool.currentOffset;
      int constantPoolIndex = constantPool.currentIndex;
      classFile.generateMethodInfoHeaderForClinit();
      int codeAttributeOffset = classFile.contentsOffset;
      classFile.generateCodeAttributeHeader();
      CodeStream codeStream = classFile.codeStream;
      this.resolve(classScope);
      codeStream.reset((AbstractMethodDeclaration)this, classFile);
      TypeDeclaration declaringType = classScope.referenceContext;
      MethodScope staticInitializerScope = declaringType.staticInitializerScope;
      staticInitializerScope.computeLocalVariablePositions(0, codeStream);
      if (this.assertionSyntheticFieldBinding != null) {
         codeStream.generateClassLiteralAccessForType(classScope.outerMostClassScope().enclosingSourceType(), this.classLiteralSyntheticField);
         codeStream.invokeJavaLangClassDesiredAssertionStatus();
         BranchLabel falseLabel = new BranchLabel(codeStream);
         codeStream.ifne(falseLabel);
         codeStream.iconst_1();
         BranchLabel jumpLabel = new BranchLabel(codeStream);
         codeStream.decrStackSize(1);
         codeStream.goto_(jumpLabel);
         falseLabel.place();
         codeStream.iconst_0();
         jumpLabel.place();
         codeStream.fieldAccess((byte)-77, this.assertionSyntheticFieldBinding, (TypeBinding)null);
      }

      boolean isJava9 = classScope.compilerOptions().complianceLevel >= 3473408L;
      FieldDeclaration[] fieldDeclarations = declaringType.fields;
      int sourcePosition = -1;
      int remainingFieldCount = 0;
      int before;
      int begin;
      if (TypeDeclaration.kind(declaringType.modifiers) == 3) {
         before = declaringType.enumConstantsCounter;
         int count;
         FieldDeclaration fieldDecl;
         if (!isJava9 && before > ENUM_CONSTANTS_THRESHOLD) {
            begin = -1;
            count = 0;
            if (fieldDeclarations != null) {
               int max = fieldDeclarations.length;

               for(int i = 0; i < max; ++i) {
                  FieldDeclaration fieldDecl = fieldDeclarations[i];
                  if (fieldDecl.isStatic()) {
                     if (fieldDecl.getKind() == 3) {
                        if (begin == -1) {
                           begin = i;
                        }

                        ++count;
                        if (count > ENUM_CONSTANTS_THRESHOLD) {
                           SyntheticMethodBinding syntheticMethod = declaringType.binding.addSyntheticMethodForEnumInitialization(begin, i);
                           codeStream.invoke((byte)-72, syntheticMethod, (TypeBinding)null);
                           begin = i;
                           count = 1;
                        }
                     } else {
                        ++remainingFieldCount;
                     }
                  }
               }

               if (count != 0) {
                  SyntheticMethodBinding syntheticMethod = declaringType.binding.addSyntheticMethodForEnumInitialization(begin, max);
                  codeStream.invoke((byte)-72, syntheticMethod, (TypeBinding)null);
               }
            }
         } else if (fieldDeclarations != null) {
            begin = 0;

            for(count = fieldDeclarations.length; begin < count; ++begin) {
               fieldDecl = fieldDeclarations[begin];
               if (fieldDecl.isStatic()) {
                  if (fieldDecl.getKind() == 3) {
                     fieldDecl.generateCode(staticInitializerScope, codeStream);
                  } else {
                     ++remainingFieldCount;
                  }
               }
            }
         }

         codeStream.generateInlinedValue(before);
         codeStream.anewarray(declaringType.binding);
         if (before > 0 && fieldDeclarations != null) {
            begin = 0;

            for(count = fieldDeclarations.length; begin < count; ++begin) {
               fieldDecl = fieldDeclarations[begin];
               if (fieldDecl.getKind() == 3) {
                  codeStream.dup();
                  codeStream.generateInlinedValue(fieldDecl.binding.id);
                  codeStream.fieldAccess((byte)-78, fieldDecl.binding, (TypeBinding)null);
                  codeStream.aastore();
               }
            }
         }

         codeStream.fieldAccess((byte)-77, declaringType.enumValuesSyntheticfield, (TypeBinding)null);
         if (remainingFieldCount != 0) {
            begin = 0;

            for(count = fieldDeclarations.length; begin < count && remainingFieldCount >= 0; ++begin) {
               fieldDecl = fieldDeclarations[begin];
               switch (fieldDecl.getKind()) {
                  case 1:
                     if (fieldDecl.binding.isStatic()) {
                        --remainingFieldCount;
                        sourcePosition = fieldDecl.declarationEnd;
                        fieldDecl.generateCode(staticInitializerScope, codeStream);
                     }
                     break;
                  case 2:
                     if (fieldDecl.isStatic()) {
                        --remainingFieldCount;
                        sourcePosition = ((Initializer)fieldDecl).block.sourceEnd;
                        fieldDecl.generateCode(staticInitializerScope, codeStream);
                     }
                  case 3:
               }
            }
         }
      } else {
         if (fieldDeclarations != null) {
            before = 0;

            for(begin = fieldDeclarations.length; before < begin; ++before) {
               FieldDeclaration fieldDecl = fieldDeclarations[before];
               switch (fieldDecl.getKind()) {
                  case 1:
                     if (fieldDecl.binding.isStatic()) {
                        sourcePosition = fieldDecl.declarationEnd;
                        fieldDecl.generateCode(staticInitializerScope, codeStream);
                     }
                     break;
                  case 2:
                     if (fieldDecl.isStatic()) {
                        sourcePosition = ((Initializer)fieldDecl).block.sourceEnd;
                        fieldDecl.generateCode(staticInitializerScope, codeStream);
                     }
               }
            }
         }

         if (isJava9) {
            declaringType.binding.generateSyntheticFinalFieldInitialization(codeStream);
         }
      }

      if (codeStream.position == 0) {
         classFile.contentsOffset = clinitOffset;
         --classFile.methodCount;
         constantPool.resetForClinit(constantPoolIndex, constantPoolOffset);
      } else {
         if ((this.bits & 64) != 0) {
            before = codeStream.position;
            codeStream.return_();
            if (sourcePosition != -1) {
               codeStream.recordPositionsFrom(before, sourcePosition);
            }
         }

         codeStream.recordPositionsFrom(0, declaringType.sourceStart);
         classFile.completeCodeAttributeForClinit(codeAttributeOffset);
      }

   }

   public boolean isClinit() {
      return true;
   }

   public boolean isInitializationMethod() {
      return true;
   }

   public boolean isStatic() {
      return true;
   }

   public void parseStatements(Parser parser, CompilationUnitDeclaration unit) {
   }

   public StringBuffer print(int tab, StringBuffer output) {
      printIndent(tab, output).append("<clinit>()");
      this.printBody(tab + 1, output);
      return output;
   }

   public void resolve(ClassScope classScope) {
      this.scope = new MethodScope(classScope, classScope.referenceContext, true);
   }

   public void traverse(ASTVisitor visitor, ClassScope classScope) {
      visitor.visit(this, classScope);
      visitor.endVisit(this, classScope);
   }

   public void setAssertionSupport(FieldBinding assertionSyntheticFieldBinding, boolean needClassLiteralField) {
      this.assertionSyntheticFieldBinding = assertionSyntheticFieldBinding;
      if (needClassLiteralField) {
         SourceTypeBinding sourceType = this.scope.outerMostClassScope().enclosingSourceType();
         if (!sourceType.isInterface() && !sourceType.isBaseType()) {
            this.classLiteralSyntheticField = sourceType.addSyntheticFieldForClassLiteral(sourceType, this.scope);
         }
      }

   }
}
