package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.StackMapFrameCodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.flow.InitializationFlowContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.BlockScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.NestedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.ArrayList;
import java.util.List;

public class ConstructorDeclaration extends AbstractMethodDeclaration {
   public ExplicitConstructorCall constructorCall;
   public TypeParameter[] typeParameters;

   public ConstructorDeclaration(CompilationResult compilationResult) {
      super(compilationResult);
   }

   public void analyseCode(ClassScope classScope, InitializationFlowContext initializerFlowContext, FlowInfo flowInfo, int initialReachMode) {
      if (!this.ignoreFurtherInvestigation) {
         int nonStaticFieldInfoReachMode = ((FlowInfo)flowInfo).reachMode();
         ((FlowInfo)flowInfo).setReachMode(initialReachMode);
         MethodBinding constructorBinding;
         if ((constructorBinding = this.binding) != null && (this.bits & 128) == 0 && !constructorBinding.isUsed()) {
            label183: {
               if (constructorBinding.isPrivate()) {
                  if ((this.binding.declaringClass.tagBits & 1152921504606846976L) == 0L) {
                     break label183;
                  }
               } else if (!constructorBinding.isOrEnclosedByPrivateType()) {
                  break label183;
               }

               if (this.constructorCall != null) {
                  label177: {
                     if (this.constructorCall.accessMode != 3) {
                        ReferenceBinding superClass = constructorBinding.declaringClass.superclass();
                        if (superClass == null) {
                           break label177;
                        }

                        MethodBinding methodBinding = superClass.getExactConstructor(Binding.NO_PARAMETERS);
                        if (methodBinding == null || !methodBinding.canBeSeenBy(SuperReference.implicitSuperConstructorCall(), this.scope)) {
                           break label177;
                        }

                        ReferenceBinding declaringClass = constructorBinding.declaringClass;
                        if (constructorBinding.isPublic() && constructorBinding.parameters.length == 0 && declaringClass.isStatic() && declaringClass.findSuperTypeOriginatingFrom(56, false) != null) {
                           break label177;
                        }
                     }

                     this.scope.problemReporter().unusedPrivateConstructor(this);
                  }
               }
            }
         }

         if (this.isRecursive((ArrayList)null)) {
            this.scope.problemReporter().recursiveConstructorInvocation(this.constructorCall);
         }

         if (this.typeParameters != null && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
            int i = 0;

            for(int length = this.typeParameters.length; i < length; ++i) {
               TypeParameter typeParameter = this.typeParameters[i];
               if ((typeParameter.binding.modifiers & 134217728) == 0) {
                  this.scope.problemReporter().unusedTypeParameter(typeParameter);
               }
            }
         }

         try {
            ExceptionHandlingFlowContext constructorContext = new ExceptionHandlingFlowContext(initializerFlowContext.parent, this, this.binding.thrownExceptions, initializerFlowContext, this.scope, FlowInfo.DEAD_END);
            initializerFlowContext.checkInitializerExceptions(this.scope, constructorContext, (FlowInfo)flowInfo);
            int i;
            if (this.binding.declaringClass.isAnonymousType()) {
               ArrayList computedExceptions = constructorContext.extendedExceptions;
               if (computedExceptions != null && (i = computedExceptions.size()) > 0) {
                  ReferenceBinding[] actuallyThrownExceptions;
                  computedExceptions.toArray(actuallyThrownExceptions = new ReferenceBinding[i]);
                  this.binding.thrownExceptions = actuallyThrownExceptions;
               }
            }

            analyseArguments(classScope.environment(), (FlowInfo)flowInfo, this.arguments, this.binding);
            FieldBinding field;
            FieldBinding[] fields;
            int count;
            if (this.constructorCall != null) {
               if (this.constructorCall.accessMode == 3) {
                  fields = this.binding.declaringClass.fields();
                  i = 0;

                  for(count = fields.length; i < count; ++i) {
                     if (!(field = fields[i]).isStatic()) {
                        ((FlowInfo)flowInfo).markAsDefinitelyAssigned(field);
                     }
                  }
               }

               flowInfo = this.constructorCall.analyseCode(this.scope, constructorContext, (FlowInfo)flowInfo);
            }

            ((FlowInfo)flowInfo).setReachMode(nonStaticFieldInfoReachMode);
            if (this.statements != null) {
               boolean enableSyntacticNullAnalysisForFields = this.scope.compilerOptions().enableSyntacticNullAnalysisForFields;
               i = (nonStaticFieldInfoReachMode & 3) == 0 ? 0 : 1;
               count = 0;

               for(int count = this.statements.length; count < count; ++count) {
                  Statement stat = this.statements[count];
                  if ((i = stat.complainIfUnreachable((FlowInfo)flowInfo, this.scope, i, true)) < 2) {
                     flowInfo = stat.analyseCode(this.scope, constructorContext, (FlowInfo)flowInfo);
                  }

                  if (enableSyntacticNullAnalysisForFields) {
                     constructorContext.expireNullCheckedFieldInfo();
                  }
               }
            }

            if ((((FlowInfo)flowInfo).tagBits & 1) == 0) {
               this.bits |= 64;
            }

            if (this.constructorCall != null && this.constructorCall.accessMode != 3) {
               flowInfo = ((FlowInfo)flowInfo).mergedWith(constructorContext.initsOnReturn);
               fields = this.binding.declaringClass.fields();
               i = 0;

               for(count = fields.length; i < count; ++i) {
                  field = fields[i];
                  if (!field.isStatic() && !((FlowInfo)flowInfo).isDefinitelyAssigned(field)) {
                     if (field.isFinal()) {
                        this.scope.problemReporter().uninitializedBlankFinalField(field, (ASTNode)((this.bits & 128) != 0 ? this.scope.referenceType().declarationOf(field.original()) : this));
                     } else if (field.isNonNull() || field.type.isFreeTypeVariable()) {
                        FieldDeclaration fieldDecl = this.scope.referenceType().declarationOf(field.original());
                        if (!this.isValueProvidedUsingAnnotation(fieldDecl)) {
                           this.scope.problemReporter().uninitializedNonNullField(field, (ASTNode)((this.bits & 128) != 0 ? fieldDecl : this));
                        }
                     }
                  }
               }
            }

            constructorContext.complainIfUnusedExceptionHandlers(this);
            this.scope.checkUnusedParameters(this.binding);
            this.scope.checkUnclosedCloseables((FlowInfo)flowInfo, (FlowContext)null, (ASTNode)null, (BlockScope)null);
         } catch (AbortMethod var12) {
            this.ignoreFurtherInvestigation = true;
         }

      }
   }

   boolean isValueProvidedUsingAnnotation(FieldDeclaration fieldDecl) {
      if (fieldDecl.annotations != null) {
         int length = fieldDecl.annotations.length;

         for(int i = 0; i < length; ++i) {
            Annotation annotation = fieldDecl.annotations[i];
            if (annotation.resolvedType.id == 80) {
               return true;
            }

            MemberValuePair[] memberValuePairs;
            int j;
            if (annotation.resolvedType.id == 81) {
               memberValuePairs = annotation.memberValuePairs();
               if (memberValuePairs == Annotation.NoValuePairs) {
                  return true;
               }

               for(j = 0; j < memberValuePairs.length; ++j) {
                  if (CharOperation.equals(memberValuePairs[j].name, TypeConstants.OPTIONAL)) {
                     return memberValuePairs[j].value instanceof FalseLiteral;
                  }
               }
            } else if (annotation.resolvedType.id == 82) {
               memberValuePairs = annotation.memberValuePairs();
               if (memberValuePairs == Annotation.NoValuePairs) {
                  return true;
               }

               for(j = 0; j < memberValuePairs.length; ++j) {
                  if (CharOperation.equals(memberValuePairs[j].name, TypeConstants.REQUIRED)) {
                     return memberValuePairs[j].value instanceof TrueLiteral;
                  }
               }
            }
         }
      }

      return false;
   }

   public void generateCode(ClassScope classScope, ClassFile classFile) {
      int problemResetPC = 0;
      if (this.ignoreFurtherInvestigation) {
         if (this.binding != null) {
            CategorizedProblem[] problems = this.scope.referenceCompilationUnit().compilationResult.getProblems();
            int problemsLength;
            CategorizedProblem[] problemsCopy = new CategorizedProblem[problemsLength = problems.length];
            System.arraycopy(problems, 0, problemsCopy, 0, problemsLength);
            classFile.addProblemConstructor(this, this.binding, problemsCopy);
         }
      } else {
         boolean restart = false;
         boolean abort = false;
         CompilationResult unitResult = null;
         int problemCount = 0;
         if (classScope != null) {
            TypeDeclaration referenceContext = classScope.referenceContext;
            if (referenceContext != null) {
               unitResult = referenceContext.compilationResult();
               problemCount = unitResult.problemCount;
            }
         }

         do {
            try {
               problemResetPC = classFile.contentsOffset;
               this.internalGenerateCode(classScope, classFile);
               restart = false;
            } catch (AbortMethod var11) {
               if (var11.compilationResult == CodeStream.RESTART_IN_WIDE_MODE) {
                  classFile.contentsOffset = problemResetPC;
                  --classFile.methodCount;
                  classFile.codeStream.resetInWideMode();
                  if (unitResult != null) {
                     unitResult.problemCount = problemCount;
                  }

                  restart = true;
               } else if (var11.compilationResult == CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE) {
                  classFile.contentsOffset = problemResetPC;
                  --classFile.methodCount;
                  classFile.codeStream.resetForCodeGenUnusedLocals();
                  if (unitResult != null) {
                     unitResult.problemCount = problemCount;
                  }

                  restart = true;
               } else {
                  restart = false;
                  abort = true;
               }
            }
         } while(restart);

         if (abort) {
            CategorizedProblem[] problems = this.scope.referenceCompilationUnit().compilationResult.getAllProblems();
            int problemsLength;
            CategorizedProblem[] problemsCopy = new CategorizedProblem[problemsLength = problems.length];
            System.arraycopy(problems, 0, problemsCopy, 0, problemsLength);
            classFile.addProblemConstructor(this, this.binding, problemsCopy, problemResetPC);
         }

      }
   }

   public void generateSyntheticFieldInitializationsIfNecessary(MethodScope methodScope, CodeStream codeStream, ReferenceBinding declaringClass) {
      if (declaringClass.isNestedType()) {
         NestedTypeBinding nestedType = (NestedTypeBinding)declaringClass;
         SyntheticArgumentBinding[] syntheticArgs = nestedType.syntheticEnclosingInstances();
         int i;
         int max;
         SyntheticArgumentBinding syntheticArg;
         if (syntheticArgs != null) {
            i = 0;

            for(max = syntheticArgs.length; i < max; ++i) {
               if ((syntheticArg = syntheticArgs[i]).matchingField != null) {
                  codeStream.aload_0();
                  codeStream.load(syntheticArg);
                  codeStream.fieldAccess((byte)-75, syntheticArg.matchingField, (TypeBinding)null);
               }
            }
         }

         syntheticArgs = nestedType.syntheticOuterLocalVariables();
         if (syntheticArgs != null) {
            i = 0;

            for(max = syntheticArgs.length; i < max; ++i) {
               if ((syntheticArg = syntheticArgs[i]).matchingField != null) {
                  codeStream.aload_0();
                  codeStream.load(syntheticArg);
                  codeStream.fieldAccess((byte)-75, syntheticArg.matchingField, (TypeBinding)null);
               }
            }
         }

      }
   }

   private void internalGenerateCode(ClassScope classScope, ClassFile classFile) {
      classFile.generateMethodInfoHeader(this.binding);
      int methodAttributeOffset = classFile.contentsOffset;
      int attributeNumber = classFile.generateMethodInfoAttributes(this.binding);
      if (!this.binding.isNative() && !this.binding.isAbstract()) {
         TypeDeclaration declaringType = classScope.referenceContext;
         int codeAttributeOffset = classFile.contentsOffset;
         classFile.generateCodeAttributeHeader();
         CodeStream codeStream = classFile.codeStream;
         codeStream.reset((AbstractMethodDeclaration)this, classFile);
         ReferenceBinding declaringClass = this.binding.declaringClass;
         int enumOffset = declaringClass.isEnum() ? 2 : 0;
         int argSlotSize = 1 + enumOffset;
         if (declaringClass.isNestedType()) {
            this.scope.extraSyntheticArguments = declaringClass.syntheticOuterLocalVariables();
            this.scope.computeLocalVariablePositions(declaringClass.getEnclosingInstancesSlotSize() + 1 + enumOffset, codeStream);
            argSlotSize += declaringClass.getEnclosingInstancesSlotSize();
            argSlotSize += declaringClass.getOuterLocalVariablesSlotSize();
         } else {
            this.scope.computeLocalVariablePositions(1 + enumOffset, codeStream);
         }

         if (this.arguments != null) {
            int i = 0;

            for(int max = this.arguments.length; i < max; ++i) {
               LocalVariableBinding argBinding;
               codeStream.addVisibleLocalVariable(argBinding = this.arguments[i].binding);
               argBinding.recordInitializationStartPC(0);
               switch (argBinding.type.id) {
                  case 7:
                  case 8:
                     argSlotSize += 2;
                     break;
                  default:
                     ++argSlotSize;
               }
            }
         }

         MethodScope initializerScope = declaringType.initializerScope;
         initializerScope.computeLocalVariablePositions(argSlotSize, codeStream);
         boolean needFieldInitializations = this.constructorCall == null || this.constructorCall.accessMode != 3;
         boolean preInitSyntheticFields = this.scope.compilerOptions().targetJDK >= 3145728L;
         if (needFieldInitializations && preInitSyntheticFields) {
            this.generateSyntheticFieldInitializationsIfNecessary(this.scope, codeStream, declaringClass);
            codeStream.recordPositionsFrom(0, this.bodyStart > 0 ? this.bodyStart : this.sourceStart);
         }

         if (this.constructorCall != null) {
            this.constructorCall.generateCode(this.scope, codeStream);
         }

         int i;
         int max;
         if (needFieldInitializations) {
            if (!preInitSyntheticFields) {
               this.generateSyntheticFieldInitializationsIfNecessary(this.scope, codeStream, declaringClass);
            }

            if (declaringType.fields != null) {
               i = 0;

               for(max = declaringType.fields.length; i < max; ++i) {
                  FieldDeclaration fieldDecl;
                  if (!(fieldDecl = declaringType.fields[i]).isStatic()) {
                     fieldDecl.generateCode(initializerScope, codeStream);
                  }
               }
            }
         }

         if (this.statements != null) {
            i = 0;

            for(max = this.statements.length; i < max; ++i) {
               this.statements[i].generateCode(this.scope, codeStream);
            }
         }

         if (this.ignoreFurtherInvestigation) {
            throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, (CategorizedProblem)null);
         }

         if ((this.bits & 64) != 0) {
            codeStream.return_();
         }

         codeStream.exitUserScope(this.scope);
         codeStream.recordPositionsFrom(0, this.bodyEnd > 0 ? this.bodyEnd : this.sourceStart);

         try {
            classFile.completeCodeAttribute(codeAttributeOffset);
         } catch (NegativeArraySizeException var17) {
            throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, (CategorizedProblem)null);
         }

         ++attributeNumber;
         if (codeStream instanceof StackMapFrameCodeStream && needFieldInitializations && declaringType.fields != null) {
            ((StackMapFrameCodeStream)codeStream).resetSecretLocals();
         }
      }

      classFile.completeMethodInfo(this.binding, methodAttributeOffset, attributeNumber);
   }

   public void getAllAnnotationContexts(int targetType, List allAnnotationContexts) {
      TypeReference fakeReturnType = new SingleTypeReference(this.selector, 0L);
      fakeReturnType.resolvedType = this.binding.declaringClass;
      TypeReference.AnnotationCollector collector = new TypeReference.AnnotationCollector(fakeReturnType, targetType, allAnnotationContexts);
      int i = 0;

      for(int max = this.annotations.length; i < max; ++i) {
         Annotation annotation = this.annotations[i];
         annotation.traverse(collector, (BlockScope)null);
      }

   }

   public boolean isConstructor() {
      return true;
   }

   public boolean isDefaultConstructor() {
      return (this.bits & 128) != 0;
   }

   public boolean isInitializationMethod() {
      return true;
   }

   public boolean isRecursive(ArrayList visited) {
      if (this.binding != null && this.constructorCall != null && this.constructorCall.binding != null && !this.constructorCall.isSuperAccess() && this.constructorCall.binding.isValidBinding()) {
         ConstructorDeclaration targetConstructor = (ConstructorDeclaration)this.scope.referenceType().declarationOf(this.constructorCall.binding.original());
         if (targetConstructor == null) {
            return false;
         } else if (this == targetConstructor) {
            return true;
         } else {
            if (visited == null) {
               visited = new ArrayList(1);
            } else {
               int index = visited.indexOf(this);
               if (index >= 0) {
                  if (index == 0) {
                     return true;
                  }

                  return false;
               }
            }

            visited.add(this);
            return targetConstructor.isRecursive(visited);
         }
      } else {
         return false;
      }
   }

   public void parseStatements(Parser parser, CompilationUnitDeclaration unit) {
      if ((this.bits & 128) != 0 && this.constructorCall == null) {
         this.constructorCall = SuperReference.implicitSuperConstructorCall();
         this.constructorCall.sourceStart = this.sourceStart;
         this.constructorCall.sourceEnd = this.sourceEnd;
      } else {
         parser.parse(this, unit, false);
      }
   }

   public StringBuffer printBody(int indent, StringBuffer output) {
      output.append(" {");
      if (this.constructorCall != null) {
         output.append('\n');
         this.constructorCall.printStatement(indent, output);
      }

      if (this.statements != null) {
         for(int i = 0; i < this.statements.length; ++i) {
            output.append('\n');
            this.statements[i].printStatement(indent, output);
         }
      }

      output.append('\n');
      printIndent(indent == 0 ? 0 : indent - 1, output).append('}');
      return output;
   }

   public void resolveJavadoc() {
      if (this.binding != null && this.javadoc == null) {
         if ((this.bits & 128) == 0 && this.binding.declaringClass != null && !this.binding.declaringClass.isLocalType()) {
            int javadocVisibility = this.binding.modifiers & 7;
            ClassScope classScope = this.scope.classScope();
            ProblemReporter reporter = this.scope.problemReporter();
            int severity = reporter.computeSeverity(-1610612250);
            if (severity != 256) {
               if (classScope != null) {
                  javadocVisibility = Util.computeOuterMostVisibility(classScope.referenceType(), javadocVisibility);
               }

               int javadocModifiers = this.binding.modifiers & -8 | javadocVisibility;
               reporter.javadocMissing(this.sourceStart, this.sourceEnd, severity, javadocModifiers);
            }
         }
      } else {
         super.resolveJavadoc();
      }

   }

   public void resolveStatements() {
      SourceTypeBinding sourceType = this.scope.enclosingSourceType();
      if (!CharOperation.equals(sourceType.sourceName, this.selector)) {
         this.scope.problemReporter().missingReturnType(this);
      }

      if (this.binding != null && !this.binding.isPrivate()) {
         sourceType.tagBits |= 1152921504606846976L;
      }

      if (this.constructorCall != null) {
         if (sourceType.id == 1 && this.constructorCall.accessMode != 3) {
            if (this.constructorCall.accessMode == 2) {
               this.scope.problemReporter().cannotUseSuperInJavaLangObject(this.constructorCall);
            }

            this.constructorCall = null;
         } else {
            this.constructorCall.resolve(this.scope);
         }
      }

      if ((this.modifiers & 16777216) != 0) {
         this.scope.problemReporter().methodNeedBody(this);
      }

      super.resolveStatements();
   }

   public void traverse(ASTVisitor visitor, ClassScope classScope) {
      if (visitor.visit(this, classScope)) {
         if (this.javadoc != null) {
            this.javadoc.traverse(visitor, (BlockScope)this.scope);
         }

         int statementsLength;
         int i;
         if (this.annotations != null) {
            statementsLength = this.annotations.length;

            for(i = 0; i < statementsLength; ++i) {
               this.annotations[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.typeParameters != null) {
            statementsLength = this.typeParameters.length;

            for(i = 0; i < statementsLength; ++i) {
               this.typeParameters[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.arguments != null) {
            statementsLength = this.arguments.length;

            for(i = 0; i < statementsLength; ++i) {
               this.arguments[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.thrownExceptions != null) {
            statementsLength = this.thrownExceptions.length;

            for(i = 0; i < statementsLength; ++i) {
               this.thrownExceptions[i].traverse(visitor, (BlockScope)this.scope);
            }
         }

         if (this.constructorCall != null) {
            this.constructorCall.traverse(visitor, this.scope);
         }

         if (this.statements != null) {
            statementsLength = this.statements.length;

            for(i = 0; i < statementsLength; ++i) {
               this.statements[i].traverse(visitor, this.scope);
            }
         }
      }

      visitor.endVisit(this, classScope);
   }

   public TypeParameter[] typeParameters() {
      return this.typeParameters;
   }
}
