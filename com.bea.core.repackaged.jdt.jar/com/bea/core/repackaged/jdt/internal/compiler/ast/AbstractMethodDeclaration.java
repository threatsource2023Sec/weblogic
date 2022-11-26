package com.bea.core.repackaged.jdt.internal.compiler.ast;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ASTVisitor;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.CompilationResult;
import com.bea.core.repackaged.jdt.internal.compiler.codegen.CodeStream;
import com.bea.core.repackaged.jdt.internal.compiler.flow.FlowInfo;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.AnnotationBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.Binding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ClassScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodScope;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Parser;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortMethod;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortType;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemSeverities;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.List;

public abstract class AbstractMethodDeclaration extends ASTNode implements ProblemSeverities, ReferenceContext {
   public MethodScope scope;
   public char[] selector;
   public int declarationSourceStart;
   public int declarationSourceEnd;
   public int modifiers;
   public int modifiersSourceStart;
   public Annotation[] annotations;
   public Receiver receiver;
   public Argument[] arguments;
   public TypeReference[] thrownExceptions;
   public Statement[] statements;
   public int explicitDeclarations;
   public MethodBinding binding;
   public boolean ignoreFurtherInvestigation = false;
   public Javadoc javadoc;
   public int bodyStart;
   public int bodyEnd = -1;
   public CompilationResult compilationResult;

   AbstractMethodDeclaration(CompilationResult compilationResult) {
      this.compilationResult = compilationResult;
   }

   public void abort(int abortLevel, CategorizedProblem problem) {
      switch (abortLevel) {
         case 2:
            throw new AbortCompilation(this.compilationResult, problem);
         case 3:
         case 5:
         case 6:
         case 7:
         default:
            throw new AbortMethod(this.compilationResult, problem);
         case 4:
            throw new AbortCompilationUnit(this.compilationResult, problem);
         case 8:
            throw new AbortType(this.compilationResult, problem);
      }
   }

   public void createArgumentBindings() {
      createArgumentBindings(this.arguments, this.binding, this.scope);
   }

   static void createArgumentBindings(Argument[] arguments, MethodBinding binding, MethodScope scope) {
      boolean useTypeAnnotations = scope.environment().usesNullTypeAnnotations();
      if (arguments != null && binding != null) {
         int i = 0;

         for(int length = arguments.length; i < length; ++i) {
            Argument argument = arguments[i];
            binding.parameters[i] = argument.createBinding(scope, binding.parameters[i]);
            if (!useTypeAnnotations) {
               long argTypeTagBits = argument.binding.tagBits & 108086391056891904L;
               if (argTypeTagBits != 0L) {
                  if (binding.parameterNonNullness == null) {
                     binding.parameterNonNullness = new Boolean[arguments.length];
                     binding.tagBits |= 4096L;
                  }

                  binding.parameterNonNullness[i] = argTypeTagBits == 72057594037927936L;
               }
            }
         }
      }

   }

   public void bindArguments() {
      if (this.arguments != null) {
         if (this.binding == null) {
            int i = 0;

            for(int length = this.arguments.length; i < length; ++i) {
               this.arguments[i].bind(this.scope, (TypeBinding)null, true);
            }

            return;
         }

         boolean used = this.binding.isAbstract() || this.binding.isNative();
         AnnotationBinding[][] paramAnnotations = null;
         int i = 0;

         for(int length = this.arguments.length; i < length; ++i) {
            Argument argument = this.arguments[i];
            this.binding.parameters[i] = argument.bind(this.scope, this.binding.parameters[i], used);
            if (argument.annotations == null) {
               if (paramAnnotations != null) {
                  paramAnnotations[i] = Binding.NO_ANNOTATIONS;
               }
            } else {
               if (paramAnnotations == null) {
                  paramAnnotations = new AnnotationBinding[length][];

                  for(int j = 0; j < i; ++j) {
                     paramAnnotations[j] = Binding.NO_ANNOTATIONS;
                  }
               }

               paramAnnotations[i] = argument.binding.getAnnotations();
            }
         }

         if (paramAnnotations != null) {
            this.binding.setParameterAnnotations(paramAnnotations);
         }
      }

   }

   public void bindThrownExceptions() {
      if (this.thrownExceptions != null && this.binding != null && this.binding.thrownExceptions != null) {
         int thrownExceptionLength = this.thrownExceptions.length;
         int length = this.binding.thrownExceptions.length;
         int bindingIndex;
         if (length == thrownExceptionLength) {
            for(bindingIndex = 0; bindingIndex < length; ++bindingIndex) {
               this.thrownExceptions[bindingIndex].resolvedType = this.binding.thrownExceptions[bindingIndex];
            }
         } else {
            bindingIndex = 0;

            for(int i = 0; i < thrownExceptionLength && bindingIndex < length; ++i) {
               TypeReference thrownException = this.thrownExceptions[i];
               ReferenceBinding thrownExceptionBinding = this.binding.thrownExceptions[bindingIndex];
               char[][] bindingCompoundName = thrownExceptionBinding.compoundName;
               if (bindingCompoundName != null) {
                  if (thrownException instanceof SingleTypeReference) {
                     int lengthName = bindingCompoundName.length;
                     char[] thrownExceptionTypeName = thrownException.getTypeName()[0];
                     if (CharOperation.equals(thrownExceptionTypeName, bindingCompoundName[lengthName - 1])) {
                        thrownException.resolvedType = thrownExceptionBinding;
                        ++bindingIndex;
                     }
                  } else if (CharOperation.equals(thrownException.getTypeName(), bindingCompoundName)) {
                     thrownException.resolvedType = thrownExceptionBinding;
                     ++bindingIndex;
                  }
               }
            }
         }
      }

   }

   static void analyseArguments(LookupEnvironment environment, FlowInfo flowInfo, Argument[] methodArguments, MethodBinding methodBinding) {
      if (methodArguments != null) {
         boolean usesNullTypeAnnotations = environment.usesNullTypeAnnotations();
         int length = Math.min(methodBinding.parameters.length, methodArguments.length);

         for(int i = 0; i < length; ++i) {
            if (usesNullTypeAnnotations) {
               long tagBits = methodBinding.parameters[i].tagBits & 108086391056891904L;
               if (tagBits == 72057594037927936L) {
                  flowInfo.markAsDefinitelyNonNull(methodArguments[i].binding);
               } else if (tagBits == 36028797018963968L) {
                  flowInfo.markPotentiallyNullBit(methodArguments[i].binding);
               } else if (methodBinding.parameters[i].isFreeTypeVariable()) {
                  flowInfo.markNullStatus(methodArguments[i].binding, 48);
               }
            } else if (methodBinding.parameterNonNullness != null) {
               Boolean nonNullNess = methodBinding.parameterNonNullness[i];
               if (nonNullNess != null) {
                  if (nonNullNess) {
                     flowInfo.markAsDefinitelyNonNull(methodArguments[i].binding);
                  } else {
                     flowInfo.markPotentiallyNullBit(methodArguments[i].binding);
                  }
               }
            }

            flowInfo.markAsDefinitelyAssigned(methodArguments[i].binding);
         }
      }

   }

   public CompilationResult compilationResult() {
      return this.compilationResult;
   }

   public void generateCode(ClassScope classScope, ClassFile classFile) {
      classFile.codeStream.wideMode = false;
      int problemResetPC;
      if (this.ignoreFurtherInvestigation) {
         if (this.binding != null) {
            CategorizedProblem[] problems = this.scope.referenceCompilationUnit().compilationResult.getProblems();
            CategorizedProblem[] problemsCopy = new CategorizedProblem[problemResetPC = problems.length];
            System.arraycopy(problems, 0, problemsCopy, 0, problemResetPC);
            classFile.addProblemMethod(this, this.binding, problemsCopy);
         }
      } else {
         problemResetPC = 0;
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
         boolean abort = false;

         do {
            try {
               problemResetPC = classFile.contentsOffset;
               this.generateCode(classFile);
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
            classFile.addProblemMethod(this, this.binding, problemsCopy, problemResetPC);
         }

      }
   }

   public void generateCode(ClassFile classFile) {
      classFile.generateMethodInfoHeader(this.binding);
      int methodAttributeOffset = classFile.contentsOffset;
      int attributeNumber = classFile.generateMethodInfoAttributes(this.binding);
      if (!this.binding.isNative() && !this.binding.isAbstract()) {
         int codeAttributeOffset = classFile.contentsOffset;
         classFile.generateCodeAttributeHeader();
         CodeStream codeStream = classFile.codeStream;
         codeStream.reset(this, classFile);
         this.scope.computeLocalVariablePositions(this.binding.isStatic() ? 0 : 1, codeStream);
         int i;
         int max;
         if (this.arguments != null) {
            i = 0;

            for(max = this.arguments.length; i < max; ++i) {
               LocalVariableBinding argBinding;
               codeStream.addVisibleLocalVariable(argBinding = this.arguments[i].binding);
               argBinding.recordInitializationStartPC(0);
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
         codeStream.recordPositionsFrom(0, this.declarationSourceEnd);

         try {
            classFile.completeCodeAttribute(codeAttributeOffset);
         } catch (NegativeArraySizeException var9) {
            throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, (CategorizedProblem)null);
         }

         ++attributeNumber;
      } else {
         this.checkArgumentsSize();
      }

      classFile.completeMethodInfo(this.binding, methodAttributeOffset, attributeNumber);
   }

   public void getAllAnnotationContexts(int targetType, List allAnnotationContexts) {
   }

   private void checkArgumentsSize() {
      TypeBinding[] parameters = this.binding.parameters;
      int size = 1;
      int i = 0;

      for(int max = parameters.length; i < max; ++i) {
         switch (parameters[i].id) {
            case 7:
            case 8:
               size += 2;
               break;
            default:
               ++size;
         }

         if (size > 255) {
            this.scope.problemReporter().noMoreAvailableSpaceForArgument(this.scope.locals[i], this.scope.locals[i].declaration);
         }
      }

   }

   public CompilationUnitDeclaration getCompilationUnitDeclaration() {
      return this.scope != null ? this.scope.compilationUnitScope().referenceContext : null;
   }

   public boolean hasErrors() {
      return this.ignoreFurtherInvestigation;
   }

   public boolean isAbstract() {
      if (this.binding != null) {
         return this.binding.isAbstract();
      } else {
         return (this.modifiers & 1024) != 0;
      }
   }

   public boolean isAnnotationMethod() {
      return false;
   }

   public boolean isClinit() {
      return false;
   }

   public boolean isConstructor() {
      return false;
   }

   public boolean isDefaultConstructor() {
      return false;
   }

   public boolean isDefaultMethod() {
      return false;
   }

   public boolean isInitializationMethod() {
      return false;
   }

   public boolean isMethod() {
      return false;
   }

   public boolean isNative() {
      if (this.binding != null) {
         return this.binding.isNative();
      } else {
         return (this.modifiers & 256) != 0;
      }
   }

   public boolean isStatic() {
      if (this.binding != null) {
         return this.binding.isStatic();
      } else {
         return (this.modifiers & 8) != 0;
      }
   }

   public abstract void parseStatements(Parser var1, CompilationUnitDeclaration var2);

   public StringBuffer print(int tab, StringBuffer output) {
      if (this.javadoc != null) {
         this.javadoc.print(tab, output);
      }

      printIndent(tab, output);
      printModifiers(this.modifiers, output);
      if (this.annotations != null) {
         printAnnotations(this.annotations, output);
         output.append(' ');
      }

      TypeParameter[] typeParams = this.typeParameters();
      int i;
      if (typeParams != null) {
         output.append('<');
         i = typeParams.length - 1;

         for(int j = 0; j < i; ++j) {
            typeParams[j].print(0, output);
            output.append(", ");
         }

         typeParams[i].print(0, output);
         output.append('>');
      }

      this.printReturnType(0, output).append(this.selector).append('(');
      if (this.receiver != null) {
         this.receiver.print(0, output);
      }

      if (this.arguments != null) {
         for(i = 0; i < this.arguments.length; ++i) {
            if (i > 0 || this.receiver != null) {
               output.append(", ");
            }

            this.arguments[i].print(0, output);
         }
      }

      output.append(')');
      if (this.thrownExceptions != null) {
         output.append(" throws ");

         for(i = 0; i < this.thrownExceptions.length; ++i) {
            if (i > 0) {
               output.append(", ");
            }

            this.thrownExceptions[i].print(0, output);
         }
      }

      this.printBody(tab + 1, output);
      return output;
   }

   public StringBuffer printBody(int indent, StringBuffer output) {
      if (!this.isAbstract() && (this.modifiers & 16777216) == 0) {
         output.append(" {");
         if (this.statements != null) {
            for(int i = 0; i < this.statements.length; ++i) {
               output.append('\n');
               this.statements[i].printStatement(indent, output);
            }
         }

         output.append('\n');
         printIndent(indent == 0 ? 0 : indent - 1, output).append('}');
         return output;
      } else {
         return output.append(';');
      }
   }

   public StringBuffer printReturnType(int indent, StringBuffer output) {
      return output;
   }

   public void resolve(ClassScope upperScope) {
      if (this.binding == null) {
         this.ignoreFurtherInvestigation = true;
      }

      try {
         this.bindArguments();
         this.resolveReceiver();
         this.bindThrownExceptions();
         resolveAnnotations(this.scope, this.annotations, this.binding, this.isConstructor());
         long sourceLevel = this.scope.compilerOptions().sourceLevel;
         if (sourceLevel < 3407872L) {
            this.validateNullAnnotations(this.scope.environment().usesNullTypeAnnotations());
         }

         this.resolveStatements();
         if (this.binding != null && (this.binding.getAnnotationTagBits() & 70368744177664L) == 0L && (this.binding.modifiers & 1048576) != 0 && sourceLevel >= 3211264L) {
            this.scope.problemReporter().missingDeprecatedAnnotationForMethod(this);
         }
      } catch (AbortMethod var4) {
         this.ignoreFurtherInvestigation = true;
      }

   }

   public void resolveReceiver() {
      if (this.receiver != null) {
         if (this.receiver.modifiers != 0) {
            this.scope.problemReporter().illegalModifiers(this.receiver.declarationSourceStart, this.receiver.declarationSourceEnd);
         }

         TypeBinding resolvedReceiverType = this.receiver.type.resolvedType;
         if (this.binding != null && resolvedReceiverType != null && resolvedReceiverType.isValidBinding()) {
            ReferenceBinding declaringClass = this.binding.declaringClass;
            if (!this.isStatic() && !declaringClass.isAnonymousType()) {
               ReferenceBinding enclosingReceiver = this.scope.enclosingReceiverType();
               if (this.isConstructor()) {
                  if (declaringClass.isStatic() || (declaringClass.tagBits & 24L) == 0L) {
                     this.scope.problemReporter().disallowedThisParameter(this.receiver);
                     return;
                  }

                  enclosingReceiver = enclosingReceiver.enclosingType();
               }

               char[][] tokens = this.receiver.qualifyingName == null ? null : this.receiver.qualifyingName.getName();
               if (this.isConstructor()) {
                  if (tokens == null || tokens.length > 1 || !CharOperation.equals(enclosingReceiver.sourceName(), tokens[0])) {
                     this.scope.problemReporter().illegalQualifierForExplicitThis(this.receiver, enclosingReceiver);
                     this.receiver.qualifyingName = null;
                  }
               } else if (tokens != null && tokens.length > 0) {
                  this.scope.problemReporter().illegalQualifierForExplicitThis2(this.receiver);
                  this.receiver.qualifyingName = null;
               }

               if (TypeBinding.notEquals(enclosingReceiver, resolvedReceiverType)) {
                  this.scope.problemReporter().illegalTypeForExplicitThis(this.receiver, enclosingReceiver);
               }

               if (this.receiver.type.hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY)) {
                  this.scope.problemReporter().nullAnnotationUnsupportedLocation(this.receiver.type);
               }

            } else {
               this.scope.problemReporter().disallowedThisParameter(this.receiver);
            }
         }
      }
   }

   public void resolveJavadoc() {
      if (this.binding != null) {
         if (this.javadoc != null) {
            this.javadoc.resolve(this.scope);
         } else {
            if (this.binding.declaringClass != null && !this.binding.declaringClass.isLocalType()) {
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

         }
      }
   }

   public void resolveStatements() {
      if (this.statements != null) {
         int i = 0;

         for(int length = this.statements.length; i < length; ++i) {
            this.statements[i].resolve(this.scope);
         }
      } else if ((this.bits & 8) != 0 && (!this.isConstructor() || this.arguments != null)) {
         this.scope.problemReporter().undocumentedEmptyBlock(this.bodyStart - 1, this.bodyEnd + 1);
      }

   }

   public void tagAsHavingErrors() {
      this.ignoreFurtherInvestigation = true;
   }

   public void tagAsHavingIgnoredMandatoryErrors(int problemId) {
   }

   public void traverse(ASTVisitor visitor, ClassScope classScope) {
   }

   public TypeParameter[] typeParameters() {
      return null;
   }

   void validateNullAnnotations(boolean useTypeAnnotations) {
      if (this.binding != null) {
         int length;
         int i;
         if (!useTypeAnnotations) {
            if (this.binding.parameterNonNullness != null) {
               length = this.binding.parameters.length;

               for(i = 0; i < length; ++i) {
                  if (this.binding.parameterNonNullness[i] != null) {
                     long nullAnnotationTagBit = this.binding.parameterNonNullness[i] ? 72057594037927936L : 36028797018963968L;
                     if (!this.scope.validateNullAnnotation(nullAnnotationTagBit, this.arguments[i].type, this.arguments[i].annotations)) {
                        this.binding.parameterNonNullness[i] = null;
                     }
                  }
               }
            }
         } else {
            length = this.binding.parameters.length;

            for(i = 0; i < length; ++i) {
               this.scope.validateNullAnnotation(this.binding.parameters[i].tagBits, this.arguments[i].type, this.arguments[i].annotations);
            }
         }

      }
   }
}
