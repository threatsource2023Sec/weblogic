package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFilePool;
import com.bea.core.repackaged.jdt.internal.compiler.ast.ASTNode;
import com.bea.core.repackaged.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRestriction;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryType;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModule;
import com.bea.core.repackaged.jdt.internal.compiler.env.IModuleAwareNameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.env.INameEnvironmentExtension;
import com.bea.core.repackaged.jdt.internal.compiler.env.ITypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.env.NameEnvironmentAnswer;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ITypeRequestor;
import com.bea.core.repackaged.jdt.internal.compiler.problem.AbortCompilation;
import com.bea.core.repackaged.jdt.internal.compiler.problem.ProblemReporter;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfModule;
import com.bea.core.repackaged.jdt.internal.compiler.util.HashtableOfPackage;
import com.bea.core.repackaged.jdt.internal.compiler.util.SimpleLookupTable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class LookupEnvironment implements ProblemReasons, TypeConstants {
   private Map accessRestrictions;
   ImportBinding[] defaultImports;
   public final LookupEnvironment root;
   public ModuleBinding UnNamedModule;
   public ModuleBinding JavaBaseModule;
   public ModuleBinding module;
   public PackageBinding defaultPackage;
   HashtableOfPackage knownPackages;
   private int lastCompletedUnitIndex = -1;
   private int lastUnitIndex = -1;
   TypeSystem typeSystem;
   public INameEnvironment nameEnvironment;
   public CompilerOptions globalOptions;
   public ProblemReporter problemReporter;
   public ClassFilePool classFilePool;
   private int stepCompleted;
   public ITypeRequestor typeRequestor;
   private SimpleLookupTable uniqueParameterizedGenericMethodBindings;
   private SimpleLookupTable uniquePolymorphicMethodBindings;
   private SimpleLookupTable uniqueGetClassMethodBinding;
   boolean useModuleSystem;
   public HashtableOfModule knownModules;
   public CompilationUnitDeclaration unitBeingCompleted = null;
   public Object missingClassFileLocation = null;
   private CompilationUnitDeclaration[] units = new CompilationUnitDeclaration[4];
   private MethodVerifier verifier;
   private ArrayList missingTypes;
   Set typesBeingConnected;
   public boolean isProcessingAnnotations = false;
   public boolean mayTolerateMissingType = false;
   PackageBinding nullableAnnotationPackage;
   PackageBinding nonnullAnnotationPackage;
   PackageBinding nonnullByDefaultAnnotationPackage;
   AnnotationBinding nonNullAnnotation;
   AnnotationBinding nullableAnnotation;
   Map allNullAnnotations = null;
   final List deferredEnumMethods;
   InferenceContext18 currentInferenceContext;
   public boolean suppressImportErrors;
   static final int BUILD_FIELDS_AND_METHODS = 4;
   static final int BUILD_TYPE_HIERARCHY = 1;
   static final int CHECK_AND_SET_IMPORTS = 2;
   static final int CONNECT_TYPE_HIERARCHY = 3;
   static final ProblemPackageBinding TheNotFoundPackage;
   static final ProblemReferenceBinding TheNotFoundType;
   static final ModuleBinding TheNotFoundModule;
   public IQualifiedTypeResolutionListener[] resolutionListeners;

   static {
      TheNotFoundPackage = new ProblemPackageBinding(CharOperation.NO_CHAR, 1, (LookupEnvironment)null);
      TheNotFoundType = new ProblemReferenceBinding(CharOperation.NO_CHAR_CHAR, (ReferenceBinding)null, 1);
      TheNotFoundModule = new ModuleBinding(CharOperation.NO_CHAR);
   }

   public LookupEnvironment(ITypeRequestor typeRequestor, CompilerOptions globalOptions, ProblemReporter problemReporter, INameEnvironment nameEnvironment) {
      this.root = this;
      this.UnNamedModule = new ModuleBinding.UnNamedModule(this);
      this.module = this.UnNamedModule;
      this.typeRequestor = typeRequestor;
      this.globalOptions = globalOptions;
      this.problemReporter = problemReporter;
      this.defaultPackage = new PackageBinding(this);
      this.defaultImports = null;
      this.nameEnvironment = nameEnvironment;
      this.knownPackages = new HashtableOfPackage();
      this.uniqueParameterizedGenericMethodBindings = new SimpleLookupTable(3);
      this.uniquePolymorphicMethodBindings = new SimpleLookupTable(3);
      this.missingTypes = null;
      this.accessRestrictions = new HashMap(3);
      this.classFilePool = ClassFilePool.newInstance();
      this.typesBeingConnected = new HashSet();
      this.deferredEnumMethods = new ArrayList();
      this.typeSystem = (TypeSystem)(this.globalOptions.sourceLevel >= 3407872L && this.globalOptions.storeAnnotations ? new AnnotatableTypeSystem(this) : new TypeSystem(this));
      this.knownModules = new HashtableOfModule();
      this.useModuleSystem = nameEnvironment instanceof IModuleAwareNameEnvironment && globalOptions.complianceLevel >= 3473408L;
      this.resolutionListeners = new IQualifiedTypeResolutionListener[0];
   }

   LookupEnvironment(LookupEnvironment rootEnv, ModuleBinding module) {
      this.root = rootEnv;
      this.UnNamedModule = rootEnv.UnNamedModule;
      this.module = module;
      this.typeRequestor = rootEnv.typeRequestor;
      this.globalOptions = rootEnv.globalOptions;
      this.problemReporter = rootEnv.problemReporter;
      this.defaultPackage = new PackageBinding(this);
      this.defaultImports = null;
      this.nameEnvironment = rootEnv.nameEnvironment;
      this.knownPackages = new HashtableOfPackage();
      this.uniqueParameterizedGenericMethodBindings = new SimpleLookupTable(3);
      this.uniquePolymorphicMethodBindings = new SimpleLookupTable(3);
      this.missingTypes = null;
      this.accessRestrictions = new HashMap(3);
      this.classFilePool = rootEnv.classFilePool;
      this.typesBeingConnected = rootEnv.typesBeingConnected;
      this.deferredEnumMethods = rootEnv.deferredEnumMethods;
      this.typeSystem = rootEnv.typeSystem;
      this.useModuleSystem = rootEnv.useModuleSystem;
   }

   public ModuleBinding getModule(char[] name) {
      if (this.root != this) {
         return this.root.getModule(name);
      } else if (name != null && name != ModuleBinding.UNNAMED && !CharOperation.equals(name, ModuleBinding.ALL_UNNAMED)) {
         ModuleBinding moduleBinding = this.knownModules.get(name);
         if (moduleBinding == null) {
            if (!this.useModuleSystem) {
               return this.UnNamedModule;
            }

            IModule mod = ((IModuleAwareNameEnvironment)this.nameEnvironment).getModule(name);
            if (mod != null) {
               this.typeRequestor.accept(mod, this);
               moduleBinding = this.root.knownModules.get(name);
            }
         }

         return moduleBinding;
      } else {
         return this.UnNamedModule;
      }
   }

   public ReferenceBinding askForType(char[][] compoundName, ModuleBinding clientModule) {
      assert clientModule != null : "lookup needs a module";

      NameEnvironmentAnswer[] answers = null;
      if (this.useModuleSystem) {
         IModuleAwareNameEnvironment moduleEnv = (IModuleAwareNameEnvironment)this.nameEnvironment;
         answers = this.askForTypeFromModules(clientModule, clientModule.getAllRequiredModules(), (mod) -> {
            return moduleEnv.findType(compoundName, mod.nameForLookup());
         });
      } else {
         NameEnvironmentAnswer answer = this.nameEnvironment.findType(compoundName);
         if (answer != null) {
            answer.moduleBinding = this.module;
            answers = new NameEnvironmentAnswer[]{answer};
         }
      }

      if (answers == null) {
         return null;
      } else {
         ReferenceBinding candidate = null;
         NameEnvironmentAnswer[] var8 = answers;
         int var7 = answers.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            NameEnvironmentAnswer answer = var8[var6];
            if (answer != null) {
               ModuleBinding answerModule = answer.moduleBinding != null ? answer.moduleBinding : this.UnNamedModule;
               PackageBinding pkg;
               ReferenceBinding binding;
               if (answer.isBinaryType()) {
                  pkg = answerModule.environment.computePackageFrom(compoundName, false);
                  this.typeRequestor.accept(answer.getBinaryType(), pkg, answer.getAccessRestriction());
                  binding = pkg.getType0(compoundName[compoundName.length - 1]);
                  if (binding instanceof BinaryTypeBinding) {
                     ((BinaryTypeBinding)binding).module = answerModule;
                     if (pkg.enclosingModule == null) {
                        pkg.enclosingModule = answerModule;
                     }
                  }
               } else if (answer.isCompilationUnit()) {
                  this.typeRequestor.accept(answer.getCompilationUnit(), answer.getAccessRestriction());
               } else if (answer.isSourceType()) {
                  pkg = answerModule.environment.computePackageFrom(compoundName, false);
                  this.typeRequestor.accept(answer.getSourceTypes(), pkg, answer.getAccessRestriction());
                  binding = pkg.getType0(compoundName[compoundName.length - 1]);
                  if (binding instanceof SourceTypeBinding) {
                     ((SourceTypeBinding)binding).module = answerModule;
                     if (pkg.enclosingModule == null) {
                        pkg.enclosingModule = answerModule;
                     }
                  }
               }

               candidate = this.combine(candidate, answerModule.environment.getCachedType(compoundName), clientModule);
            }
         }

         return candidate;
      }
   }

   ReferenceBinding askForType(PackageBinding packageBinding, char[] name, ModuleBinding clientModule) {
      assert clientModule != null : "lookup needs a module";

      if (packageBinding == null) {
         packageBinding = this.defaultPackage;
      }

      NameEnvironmentAnswer[] answers = null;
      if (this.useModuleSystem) {
         IModuleAwareNameEnvironment moduleEnv = (IModuleAwareNameEnvironment)this.nameEnvironment;
         answers = this.askForTypeFromModules((ModuleBinding)null, packageBinding.getDeclaringModules(), (mod) -> {
            return fromSplitPackageOrOracle(moduleEnv, mod, packageBinding, name);
         });
      } else {
         NameEnvironmentAnswer answer = this.nameEnvironment.findType(name, packageBinding.compoundName);
         if (answer != null) {
            answer.moduleBinding = this.module;
            answers = new NameEnvironmentAnswer[]{answer};
         }
      }

      if (answers == null) {
         return null;
      } else {
         ReferenceBinding candidate = null;
         NameEnvironmentAnswer[] var9 = answers;
         int var8 = answers.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            NameEnvironmentAnswer answer = var9[var7];
            if (answer != null) {
               if (candidate != null && candidate.problemId() == 3) {
                  return candidate;
               }

               ModuleBinding answerModule = answer.moduleBinding != null ? answer.moduleBinding : this.UnNamedModule;
               PackageBinding answerPackage = packageBinding;
               if (answerModule != null) {
                  if (!packageBinding.isDeclaredIn(answerModule)) {
                     continue;
                  }

                  if (packageBinding instanceof SplitPackageBinding) {
                     answerPackage = ((SplitPackageBinding)packageBinding).getIncarnation(answerModule);
                  }
               }

               if (answer.isResolvedBinding()) {
                  candidate = this.combine(candidate, answer.getResolvedBinding(), clientModule);
               } else {
                  ReferenceBinding binding;
                  if (answer.isBinaryType()) {
                     this.typeRequestor.accept(answer.getBinaryType(), answerPackage, answer.getAccessRestriction());
                     binding = answerPackage.getType0(name);
                     if (binding instanceof BinaryTypeBinding) {
                        ((BinaryTypeBinding)binding).module = answerModule;
                     }
                  } else if (answer.isCompilationUnit()) {
                     try {
                        this.typeRequestor.accept(answer.getCompilationUnit(), answer.getAccessRestriction());
                     } catch (AbortCompilation var14) {
                        if (CharOperation.equals(name, TypeConstants.PACKAGE_INFO_NAME)) {
                           return null;
                        }

                        throw var14;
                     }
                  } else if (answer.isSourceType()) {
                     this.typeRequestor.accept(answer.getSourceTypes(), answerPackage, answer.getAccessRestriction());
                     binding = answerPackage.getType0(name);
                     if (binding instanceof SourceTypeBinding) {
                        ((SourceTypeBinding)binding).module = answerModule;
                     }

                     String externalAnnotationPath = answer.getExternalAnnotationPath();
                     if (externalAnnotationPath != null && this.globalOptions.isAnnotationBasedNullAnalysisEnabled && binding instanceof SourceTypeBinding) {
                        ExternalAnnotationSuperimposer.apply((SourceTypeBinding)binding, externalAnnotationPath);
                     }

                     candidate = this.combine(candidate, binding, clientModule);
                     continue;
                  }

                  candidate = this.combine(candidate, answerPackage.getType0(name), clientModule);
               }
            }
         }

         return candidate;
      }
   }

   private ReferenceBinding combine(ReferenceBinding one, ReferenceBinding two, ModuleBinding clientModule) {
      if (one == null) {
         return two;
      } else if (two == null) {
         return one;
      } else if (!clientModule.canAccess(one.fPackage)) {
         return two;
      } else if (!clientModule.canAccess(two.fPackage)) {
         return one;
      } else {
         return (ReferenceBinding)(one == two ? one : new ProblemReferenceBinding(one.compoundName, one, 3));
      }
   }

   private NameEnvironmentAnswer[] askForTypeFromModules(ModuleBinding clientModule, ModuleBinding[] otherModules, Function oracle) {
      if (clientModule != null && clientModule.nameForLookup().length == 0) {
         NameEnvironmentAnswer answer = (NameEnvironmentAnswer)oracle.apply(clientModule);
         if (answer != null) {
            answer.moduleBinding = this.root.getModuleFromAnswer(answer);
         }

         return new NameEnvironmentAnswer[]{answer};
      } else {
         boolean found = false;
         NameEnvironmentAnswer[] answers = null;
         if (clientModule != null) {
            answers = new NameEnvironmentAnswer[otherModules.length + 1];
            NameEnvironmentAnswer answer = (NameEnvironmentAnswer)oracle.apply(clientModule);
            if (answer != null) {
               answer.moduleBinding = clientModule;
               answers[answers.length - 1] = answer;
               found = true;
            }
         } else {
            answers = new NameEnvironmentAnswer[otherModules.length];
         }

         for(int i = 0; i < otherModules.length; ++i) {
            NameEnvironmentAnswer answer = (NameEnvironmentAnswer)oracle.apply(otherModules[i]);
            if (answer != null) {
               if (answer.moduleBinding == null) {
                  char[] nameFromAnswer = answer.moduleName();
                  if (CharOperation.equals(nameFromAnswer, otherModules[i].moduleName)) {
                     answer.moduleBinding = otherModules[i];
                  } else {
                     answer.moduleBinding = this.getModule(nameFromAnswer);
                  }
               }

               answers[i] = answer;
               found = true;
            }
         }

         return found ? answers : null;
      }
   }

   private static NameEnvironmentAnswer fromSplitPackageOrOracle(IModuleAwareNameEnvironment moduleEnv, ModuleBinding module, PackageBinding packageBinding, char[] name) {
      if (packageBinding instanceof SplitPackageBinding) {
         ReferenceBinding binding = ((SplitPackageBinding)packageBinding).getType0ForModule(module, name);
         if (binding != null && binding.isValidBinding()) {
            if (binding instanceof UnresolvedReferenceBinding) {
               binding = ((UnresolvedReferenceBinding)binding).resolve(module.environment, false);
            }

            if (binding.isValidBinding()) {
               return new NameEnvironmentAnswer(binding, module);
            }
         }
      }

      return moduleEnv.findType(name, packageBinding.compoundName, module.nameForLookup());
   }

   private ModuleBinding getModuleFromAnswer(NameEnvironmentAnswer answer) {
      char[] moduleName = answer.moduleName();
      if (moduleName == null) {
         return null;
      } else {
         ModuleBinding moduleBinding;
         if (this.useModuleSystem && moduleName != ModuleBinding.UNNAMED) {
            moduleBinding = this.knownModules.get(moduleName);
            if (moduleBinding == null && this.nameEnvironment instanceof IModuleAwareNameEnvironment) {
               IModule iModule = ((IModuleAwareNameEnvironment)this.nameEnvironment).getModule(moduleName);

               try {
                  this.typeRequestor.accept(iModule, this);
                  moduleBinding = this.knownModules.get(moduleName);
               } catch (NullPointerException var6) {
                  System.err.println("Bug 529367: moduleName: " + new String(moduleName) + "iModule null" + (iModule == null ? "true" : "false"));
                  throw var6;
               }
            }
         } else {
            moduleBinding = this.UnNamedModule;
         }

         return moduleBinding;
      }
   }

   public boolean canTypeBeAccessed(SourceTypeBinding binding, Scope scope) {
      ModuleBinding client = scope.module();
      return client.canAccess(binding.fPackage);
   }

   public void buildTypeBindings(CompilationUnitDeclaration unit, AccessRestriction accessRestriction) {
      ModuleBinding unitModule = null;
      CompilationUnitScope scope;
      if (unit.moduleDeclaration != null) {
         char[] moduleName = unit.moduleDeclaration.moduleName;
         scope = new CompilationUnitScope(unit, this.globalOptions);
         unitModule = unit.moduleDeclaration.setBinding(new SourceModuleBinding(moduleName, scope, this.root));
      } else {
         unitModule = unit.module(this);
         scope = new CompilationUnitScope(unit, unitModule != null ? unitModule.environment : this);
      }

      scope.buildTypeBindings(accessRestriction);
      LookupEnvironment rootEnv = this.root;
      int unitsLength = rootEnv.units.length;
      if (++rootEnv.lastUnitIndex >= unitsLength) {
         System.arraycopy(rootEnv.units, 0, rootEnv.units = new CompilationUnitDeclaration[2 * unitsLength], 0, unitsLength);
      }

      rootEnv.units[rootEnv.lastUnitIndex] = unit;
   }

   public BinaryTypeBinding cacheBinaryType(IBinaryType binaryType, AccessRestriction accessRestriction) {
      return this.cacheBinaryType(binaryType, true, accessRestriction);
   }

   public BinaryTypeBinding cacheBinaryType(IBinaryType binaryType, boolean needFieldsAndMethods, AccessRestriction accessRestriction) {
      char[][] compoundName = CharOperation.splitOn('/', binaryType.getName());
      ReferenceBinding existingType = this.getCachedType(compoundName);
      return existingType != null && !(existingType instanceof UnresolvedReferenceBinding) ? null : this.createBinaryTypeFrom(binaryType, this.computePackageFrom(compoundName, false), needFieldsAndMethods, accessRestriction);
   }

   public void completeTypeBindings() {
      if (this != this.root) {
         this.root.completeTypeBindings();
      } else {
         this.stepCompleted = 1;

         int i;
         for(i = this.lastCompletedUnitIndex + 1; i <= this.lastUnitIndex; ++i) {
            (this.unitBeingCompleted = this.units[i]).scope.checkAndSetImports();
         }

         this.stepCompleted = 2;

         for(i = this.lastCompletedUnitIndex + 1; i <= this.lastUnitIndex; ++i) {
            (this.unitBeingCompleted = this.units[i]).scope.connectTypeHierarchy();
         }

         this.stepCompleted = 3;

         for(i = this.lastCompletedUnitIndex + 1; i <= this.lastUnitIndex; ++i) {
            CompilationUnitScope unitScope = (this.unitBeingCompleted = this.units[i]).scope;
            unitScope.checkParameterizedTypes();
            unitScope.buildFieldsAndMethods();
            this.units[i] = null;
         }

         this.stepCompleted = 4;
         this.lastCompletedUnitIndex = this.lastUnitIndex;
         this.unitBeingCompleted = null;
      }
   }

   public void completeTypeBindings(CompilationUnitDeclaration parsedUnit) {
      if (this != this.root) {
         this.root.completeTypeBindings(parsedUnit);
      } else {
         if (this.stepCompleted == 4) {
            this.completeTypeBindings();
         } else {
            if (parsedUnit.scope == null) {
               return;
            }

            if (this.stepCompleted >= 2) {
               (this.unitBeingCompleted = parsedUnit).scope.checkAndSetImports();
            }

            if (this.stepCompleted >= 3) {
               (this.unitBeingCompleted = parsedUnit).scope.connectTypeHierarchy();
            }

            this.unitBeingCompleted = null;
         }

      }
   }

   public void completeTypeBindings(CompilationUnitDeclaration parsedUnit, boolean buildFieldsAndMethods) {
      if (parsedUnit.scope != null) {
         LookupEnvironment rootEnv = this.root;
         CompilationUnitDeclaration previousUnitBeingCompleted = rootEnv.unitBeingCompleted;
         (rootEnv.unitBeingCompleted = parsedUnit).scope.checkAndSetImports();
         parsedUnit.scope.connectTypeHierarchy();
         parsedUnit.scope.checkParameterizedTypes();
         if (buildFieldsAndMethods) {
            parsedUnit.scope.buildFieldsAndMethods();
         }

         rootEnv.unitBeingCompleted = previousUnitBeingCompleted;
      }
   }

   public void completeTypeBindings(CompilationUnitDeclaration[] parsedUnits, boolean[] buildFieldsAndMethods, int unitCount) {
      LookupEnvironment rootEnv = this.root;

      int i;
      CompilationUnitDeclaration parsedUnit;
      for(i = 0; i < unitCount; ++i) {
         parsedUnit = parsedUnits[i];
         if (parsedUnit.scope != null) {
            (rootEnv.unitBeingCompleted = parsedUnit).scope.checkAndSetImports();
         }
      }

      for(i = 0; i < unitCount; ++i) {
         parsedUnit = parsedUnits[i];
         if (parsedUnit.scope != null) {
            (rootEnv.unitBeingCompleted = parsedUnit).scope.connectTypeHierarchy();
         }
      }

      for(i = 0; i < unitCount; ++i) {
         parsedUnit = parsedUnits[i];
         if (parsedUnit.scope != null) {
            (rootEnv.unitBeingCompleted = parsedUnit).scope.checkParameterizedTypes();
            if (buildFieldsAndMethods[i]) {
               parsedUnit.scope.buildFieldsAndMethods();
            }
         }
      }

      rootEnv.unitBeingCompleted = null;
   }

   public TypeBinding computeBoxingType(TypeBinding type) {
      ReferenceBinding boxedType;
      switch (type.id) {
         case 2:
            boxedType = this.getType(JAVA_LANG_CHARACTER, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_CHARACTER, (ReferenceBinding)null, 1);
         case 3:
            boxedType = this.getType(JAVA_LANG_BYTE, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_BYTE, (ReferenceBinding)null, 1);
         case 4:
            boxedType = this.getType(JAVA_LANG_SHORT, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_SHORT, (ReferenceBinding)null, 1);
         case 5:
            boxedType = this.getType(JAVA_LANG_BOOLEAN, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_BOOLEAN, (ReferenceBinding)null, 1);
         case 6:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         default:
            switch (type.kind()) {
               case 516:
               case 4100:
               case 8196:
               case 32772:
                  switch (type.erasure().id) {
                     case 26:
                        return TypeBinding.BYTE;
                     case 27:
                        return TypeBinding.SHORT;
                     case 28:
                        return TypeBinding.CHAR;
                     case 29:
                        return TypeBinding.INT;
                     case 30:
                        return TypeBinding.LONG;
                     case 31:
                        return TypeBinding.FLOAT;
                     case 32:
                        return TypeBinding.DOUBLE;
                     case 33:
                        return TypeBinding.BOOLEAN;
                  }
               default:
                  return type;
               case 65540:
                  return ((PolyTypeBinding)type).computeBoxingType();
            }
         case 7:
            boxedType = this.getType(JAVA_LANG_LONG, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_LONG, (ReferenceBinding)null, 1);
         case 8:
            boxedType = this.getType(JAVA_LANG_DOUBLE, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_DOUBLE, (ReferenceBinding)null, 1);
         case 9:
            boxedType = this.getType(JAVA_LANG_FLOAT, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_FLOAT, (ReferenceBinding)null, 1);
         case 10:
            boxedType = this.getType(JAVA_LANG_INTEGER, this.javaBaseModule());
            if (boxedType != null) {
               return boxedType;
            }

            return new ProblemReferenceBinding(JAVA_LANG_INTEGER, (ReferenceBinding)null, 1);
         case 26:
            return TypeBinding.BYTE;
         case 27:
            return TypeBinding.SHORT;
         case 28:
            return TypeBinding.CHAR;
         case 29:
            return TypeBinding.INT;
         case 30:
            return TypeBinding.LONG;
         case 31:
            return TypeBinding.FLOAT;
         case 32:
            return TypeBinding.DOUBLE;
         case 33:
            return TypeBinding.BOOLEAN;
      }
   }

   public ModuleBinding javaBaseModule() {
      if (this.JavaBaseModule != null) {
         return this.JavaBaseModule;
      } else if (this.root != this) {
         return this.JavaBaseModule = this.root.javaBaseModule();
      } else {
         ModuleBinding resolvedModel = null;
         if (this.useModuleSystem) {
            resolvedModel = this.getModule(TypeConstants.JAVA_BASE);
         }

         return this.JavaBaseModule = resolvedModel != null ? resolvedModel : this.UnNamedModule;
      }
   }

   private PackageBinding computePackageFrom(char[][] constantPoolName, boolean isMissing) {
      if (constantPoolName.length == 1) {
         return this.defaultPackage;
      } else {
         PackageBinding packageBinding = this.getPackage0(constantPoolName[0]);
         if (packageBinding == null || packageBinding == TheNotFoundPackage) {
            if (this.useModuleSystem) {
               if (this.module.isUnnamed()) {
                  char[][] declaringModules = ((IModuleAwareNameEnvironment)this.nameEnvironment).getUniqueModulesDeclaringPackage((char[][])null, constantPoolName[0], ModuleBinding.ANY);
                  if (declaringModules != null) {
                     char[][] var8 = declaringModules;
                     int var7 = declaringModules.length;

                     for(int var6 = 0; var6 < var7; ++var6) {
                        char[] mod = var8[var6];
                        ModuleBinding declaringModule = this.root.getModule(mod);
                        if (declaringModule != null) {
                           packageBinding = SplitPackageBinding.combine(declaringModule.getTopLevelPackage(constantPoolName[0]), packageBinding, this.module);
                        }
                     }
                  }
               } else {
                  packageBinding = this.module.getTopLevelPackage(constantPoolName[0]);
               }
            }

            if (packageBinding == null || packageBinding == TheNotFoundPackage) {
               packageBinding = new PackageBinding(constantPoolName[0], this, this.module);
            }

            if (isMissing) {
               packageBinding.tagBits |= 128L;
            }

            this.knownPackages.put(constantPoolName[0], packageBinding);
         }

         int i = 1;

         for(int length = constantPoolName.length - 1; i < length; ++i) {
            PackageBinding parent = packageBinding;
            if ((packageBinding = packageBinding.getPackage0(constantPoolName[i])) == null || packageBinding == TheNotFoundPackage) {
               if (this.useModuleSystem) {
                  if (this.module.isUnnamed()) {
                     char[][] declaringModules = ((IModuleAwareNameEnvironment)this.nameEnvironment).getModulesDeclaringPackage(parent.compoundName, constantPoolName[i], ModuleBinding.ANY);
                     if (declaringModules != null) {
                        char[][] var11 = declaringModules;
                        int var10 = declaringModules.length;

                        for(int var21 = 0; var21 < var10; ++var21) {
                           char[] mod = var11[var21];
                           ModuleBinding declaringModule = this.root.getModule(mod);
                           if (declaringModule != null) {
                              packageBinding = SplitPackageBinding.combine(declaringModule.getPackage(parent.compoundName, constantPoolName[i]), packageBinding, this.module);
                           }
                        }
                     }
                  } else {
                     packageBinding = this.module.getVisiblePackage(parent, constantPoolName[i], true);
                  }
               }

               if (packageBinding == null || packageBinding == TheNotFoundPackage) {
                  packageBinding = new PackageBinding(CharOperation.subarray((char[][])constantPoolName, 0, i + 1), parent, this, this.module);
               }

               if (isMissing) {
                  packageBinding.tagBits |= 128L;
               }

               packageBinding = parent.addPackage(packageBinding, this.module);
            }
         }

         if (packageBinding instanceof SplitPackageBinding) {
            PackageBinding candidate = null;
            Iterator var18 = ((SplitPackageBinding)packageBinding).incarnations.iterator();

            while(var18.hasNext()) {
               PackageBinding incarnation = (PackageBinding)var18.next();
               if (incarnation.hasCompilationUnit(false)) {
                  if (candidate != null) {
                     candidate = null;
                     break;
                  }

                  candidate = incarnation;
               }
            }

            if (candidate != null) {
               return candidate;
            }
         }

         return packageBinding;
      }
   }

   public ReferenceBinding convertToParameterizedType(ReferenceBinding originalType) {
      if (originalType != null) {
         boolean isGeneric = originalType.isGenericType();
         if (!isGeneric && !originalType.hasEnclosingInstanceContext()) {
            return originalType;
         }

         ReferenceBinding originalEnclosingType = originalType.enclosingType();
         ReferenceBinding convertedEnclosingType = originalEnclosingType;
         boolean needToConvert = isGeneric;
         if (originalEnclosingType != null && originalType.hasEnclosingInstanceContext()) {
            convertedEnclosingType = this.convertToParameterizedType(originalEnclosingType);
            needToConvert = isGeneric | TypeBinding.notEquals(originalEnclosingType, convertedEnclosingType);
         }

         if (needToConvert) {
            return this.createParameterizedType(originalType, isGeneric ? originalType.typeVariables() : null, convertedEnclosingType);
         }
      }

      return originalType;
   }

   public TypeBinding convertToRawType(TypeBinding type, boolean forceRawEnclosingType) {
      int dimension;
      TypeBinding originalType;
      switch (type.kind()) {
         case 68:
            dimension = type.dimensions();
            originalType = type.leafComponentType();
            break;
         case 132:
         case 516:
         case 1028:
         case 4100:
         case 8196:
            return type;
         default:
            if (type.id == 1) {
               return type;
            }

            dimension = 0;
            originalType = type;
      }

      boolean needToConvert;
      switch (originalType.kind()) {
         case 132:
            return type;
         case 260:
            ParameterizedTypeBinding paramType = (ParameterizedTypeBinding)originalType;
            needToConvert = paramType.genericType().isGenericType();
            break;
         case 2052:
            needToConvert = true;
            break;
         default:
            needToConvert = false;
      }

      forceRawEnclosingType &= !originalType.isStatic();
      ReferenceBinding originalEnclosing = originalType.enclosingType();
      Object convertedType;
      if (originalEnclosing == null) {
         convertedType = needToConvert ? this.createRawType((ReferenceBinding)originalType.erasure(), (ReferenceBinding)null) : originalType;
      } else {
         ReferenceBinding convertedEnclosing;
         if (!((ReferenceBinding)originalType).hasEnclosingInstanceContext()) {
            convertedEnclosing = (ReferenceBinding)originalEnclosing.original();
         } else if (originalEnclosing.kind() == 1028) {
            convertedEnclosing = originalEnclosing;
            needToConvert = true;
         } else if (forceRawEnclosingType && !needToConvert) {
            convertedEnclosing = (ReferenceBinding)this.convertToRawType(originalEnclosing, forceRawEnclosingType);
            needToConvert = TypeBinding.notEquals(originalEnclosing, convertedEnclosing);
         } else if (needToConvert) {
            convertedEnclosing = (ReferenceBinding)this.convertToRawType(originalEnclosing, false);
         } else {
            convertedEnclosing = this.convertToParameterizedType(originalEnclosing);
         }

         if (needToConvert) {
            convertedType = this.createRawType((ReferenceBinding)originalType.erasure(), convertedEnclosing);
         } else if (TypeBinding.notEquals(originalEnclosing, convertedEnclosing)) {
            convertedType = this.createParameterizedType((ReferenceBinding)originalType.erasure(), (TypeBinding[])null, convertedEnclosing);
         } else {
            convertedType = originalType;
         }
      }

      if (TypeBinding.notEquals(originalType, (TypeBinding)convertedType)) {
         return (TypeBinding)(dimension > 0 ? this.createArrayType((TypeBinding)convertedType, dimension) : convertedType);
      } else {
         return type;
      }
   }

   public ReferenceBinding[] convertToRawTypes(ReferenceBinding[] originalTypes, boolean forceErasure, boolean forceRawEnclosingType) {
      if (originalTypes == null) {
         return null;
      } else {
         ReferenceBinding[] convertedTypes = originalTypes;
         int i = 0;

         for(int length = originalTypes.length; i < length; ++i) {
            ReferenceBinding originalType = originalTypes[i];
            ReferenceBinding convertedType = (ReferenceBinding)this.convertToRawType((TypeBinding)(forceErasure ? originalType.erasure() : originalType), forceRawEnclosingType);
            if (TypeBinding.notEquals(convertedType, originalType)) {
               if (convertedTypes == originalTypes) {
                  System.arraycopy(originalTypes, 0, convertedTypes = new ReferenceBinding[length], 0, i);
               }

               convertedTypes[i] = convertedType;
            } else if (convertedTypes != originalTypes) {
               convertedTypes[i] = originalType;
            }
         }

         return convertedTypes;
      }
   }

   public TypeBinding convertUnresolvedBinaryToRawType(TypeBinding type) {
      int dimension;
      TypeBinding originalType;
      switch (type.kind()) {
         case 68:
            dimension = type.dimensions();
            originalType = type.leafComponentType();
            break;
         case 132:
         case 516:
         case 1028:
         case 4100:
         case 8196:
            return type;
         default:
            if (type.id == 1) {
               return type;
            }

            dimension = 0;
            originalType = type;
      }

      boolean needToConvert;
      switch (originalType.kind()) {
         case 132:
            return type;
         case 260:
            ParameterizedTypeBinding paramType = (ParameterizedTypeBinding)originalType;
            needToConvert = paramType.genericType().isGenericType();
            break;
         case 2052:
            needToConvert = true;
            break;
         default:
            needToConvert = false;
      }

      ReferenceBinding originalEnclosing = originalType.enclosingType();
      Object convertedType;
      if (originalEnclosing == null) {
         convertedType = needToConvert ? this.createRawType((ReferenceBinding)originalType.erasure(), (ReferenceBinding)null) : originalType;
      } else {
         if (!needToConvert && originalType.isStatic()) {
            return originalType;
         }

         ReferenceBinding convertedEnclosing = (ReferenceBinding)this.convertUnresolvedBinaryToRawType(originalEnclosing);
         if (TypeBinding.notEquals(convertedEnclosing, originalEnclosing)) {
            needToConvert = true;
         }

         if (needToConvert) {
            convertedType = this.createRawType((ReferenceBinding)originalType.erasure(), convertedEnclosing);
         } else {
            convertedType = originalType;
         }
      }

      if (TypeBinding.notEquals(originalType, (TypeBinding)convertedType)) {
         return (TypeBinding)(dimension > 0 ? this.createArrayType((TypeBinding)convertedType, dimension) : convertedType);
      } else {
         return type;
      }
   }

   public AnnotationBinding createAnnotation(ReferenceBinding annotationType, ElementValuePair[] pairs) {
      if (pairs.length != 0) {
         AnnotationBinding.setMethodBindings(annotationType, pairs);
         return new AnnotationBinding(annotationType, pairs);
      } else {
         return this.typeSystem.getAnnotationType(annotationType, true);
      }
   }

   public AnnotationBinding createUnresolvedAnnotation(ReferenceBinding annotationType, ElementValuePair[] pairs) {
      return (AnnotationBinding)(pairs.length != 0 ? new UnresolvedAnnotationBinding(annotationType, pairs, this) : this.typeSystem.getAnnotationType(annotationType, false));
   }

   public ArrayBinding createArrayType(TypeBinding leafComponentType, int dimensionCount) {
      return this.typeSystem.getArrayType(leafComponentType, dimensionCount);
   }

   public ArrayBinding createArrayType(TypeBinding leafComponentType, int dimensionCount, AnnotationBinding[] annotations) {
      return this.typeSystem.getArrayType(leafComponentType, dimensionCount, annotations);
   }

   public TypeBinding createIntersectionType18(ReferenceBinding[] intersectingTypes) {
      if (!intersectingTypes[0].isClass()) {
         Arrays.sort(intersectingTypes, new Comparator() {
            public int compare(TypeBinding o1, TypeBinding o2) {
               return o1.isClass() ? -1 : (o2.isClass() ? 1 : CharOperation.compareTo(o1.readableName(), o2.readableName()));
            }
         });
      }

      return this.typeSystem.getIntersectionType18(intersectingTypes);
   }

   public BinaryTypeBinding createBinaryTypeFrom(IBinaryType binaryType, PackageBinding packageBinding, AccessRestriction accessRestriction) {
      return this.createBinaryTypeFrom(binaryType, packageBinding, true, accessRestriction);
   }

   public BinaryTypeBinding createBinaryTypeFrom(IBinaryType binaryType, PackageBinding packageBinding, boolean needFieldsAndMethods, AccessRestriction accessRestriction) {
      if (this != packageBinding.environment) {
         return packageBinding.environment.createBinaryTypeFrom(binaryType, packageBinding, needFieldsAndMethods, accessRestriction);
      } else {
         BinaryTypeBinding binaryBinding = new BinaryTypeBinding(packageBinding, binaryType, this);
         ReferenceBinding cachedType = packageBinding.getType0(binaryBinding.compoundName[binaryBinding.compoundName.length - 1]);
         if (cachedType != null && !cachedType.isUnresolvedType()) {
            return cachedType.isBinaryBinding() ? (BinaryTypeBinding)cachedType : null;
         } else {
            packageBinding.addType(binaryBinding);
            this.setAccessRestriction(binaryBinding, accessRestriction);
            binaryBinding.cachePartsFrom(binaryType, needFieldsAndMethods);
            return binaryBinding;
         }
      }
   }

   public MissingTypeBinding createMissingType(PackageBinding packageBinding, char[][] compoundName) {
      if (packageBinding == null) {
         packageBinding = this.computePackageFrom(compoundName, true);
         if (packageBinding == TheNotFoundPackage) {
            packageBinding = this.defaultPackage;
         }
      }

      MissingTypeBinding missingType = new MissingTypeBinding(packageBinding, compoundName, this);
      if (missingType.id != 1) {
         ReferenceBinding objectType = this.getType(TypeConstants.JAVA_LANG_OBJECT, this.javaBaseModule());
         if (objectType == null) {
            objectType = this.createMissingType((PackageBinding)null, TypeConstants.JAVA_LANG_OBJECT);
         }

         missingType.setMissingSuperclass((ReferenceBinding)objectType);
      }

      packageBinding.addType(missingType);
      if (this.missingTypes == null) {
         this.missingTypes = new ArrayList(3);
      }

      this.missingTypes.add(missingType);
      return missingType;
   }

   public PackageBinding createPackage(char[][] compoundName) {
      PackageBinding packageBinding = this.getPackage0(compoundName[0]);
      if (packageBinding == null || packageBinding == TheNotFoundPackage) {
         packageBinding = new PackageBinding(compoundName[0], this, this.module);
         this.knownPackages.put(compoundName[0], packageBinding);
         if (this.module != null) {
            packageBinding = this.module.addPackage(packageBinding, true);
            this.knownPackages.put(compoundName[0], packageBinding);
         }
      }

      int i = 1;

      for(int length = compoundName.length; i < length; ++i) {
         ReferenceBinding type = packageBinding.getType0(compoundName[i]);
         if (type != null && type != TheNotFoundType && !(type instanceof UnresolvedReferenceBinding)) {
            return null;
         }

         PackageBinding parent = packageBinding;
         if ((packageBinding = packageBinding.getPackage0(compoundName[i])) == null || packageBinding == TheNotFoundPackage) {
            if (this.nameEnvironment instanceof INameEnvironmentExtension) {
               if (((INameEnvironmentExtension)this.nameEnvironment).findType(compoundName[i], parent.compoundName, false, this.module.nameForLookup()) != null) {
                  return null;
               }
            } else if (this.nameEnvironment.findType(compoundName[i], parent.compoundName) != null) {
               return null;
            }

            if (parent instanceof SplitPackageBinding) {
               PackageBinding singleParent = ((SplitPackageBinding)parent).getIncarnation(this.module);
               if (singleParent != null) {
                  packageBinding = singleParent.getPackage0(compoundName[i]);
               }
            }

            if (packageBinding == null) {
               packageBinding = new PackageBinding(CharOperation.subarray((char[][])compoundName, 0, i + 1), parent, this, this.module);
               packageBinding = parent.addPackage(packageBinding, this.module);
            }
         }
      }

      if (packageBinding instanceof SplitPackageBinding) {
         packageBinding = ((SplitPackageBinding)packageBinding).getIncarnation(this.module);
      }

      return packageBinding;
   }

   public ParameterizedGenericMethodBinding createParameterizedGenericMethod(MethodBinding genericMethod, RawTypeBinding rawType) {
      ParameterizedGenericMethodBinding[] cachedInfo = (ParameterizedGenericMethodBinding[])this.uniqueParameterizedGenericMethodBindings.get(genericMethod);
      boolean needToGrow = false;
      int index = 0;
      int length;
      ParameterizedGenericMethodBinding cachedMethod;
      if (cachedInfo != null) {
         for(length = cachedInfo.length; index < length; ++index) {
            cachedMethod = cachedInfo[index];
            if (cachedMethod == null) {
               break;
            }

            if (cachedMethod.isRaw && cachedMethod.declaringClass == (rawType == null ? genericMethod.declaringClass : rawType)) {
               return cachedMethod;
            }
         }

         needToGrow = true;
      } else {
         cachedInfo = new ParameterizedGenericMethodBinding[5];
         this.uniqueParameterizedGenericMethodBindings.put(genericMethod, cachedInfo);
      }

      length = cachedInfo.length;
      if (needToGrow && index == length) {
         System.arraycopy(cachedInfo, 0, cachedInfo = new ParameterizedGenericMethodBinding[length * 2], 0, length);
         this.uniqueParameterizedGenericMethodBindings.put(genericMethod, cachedInfo);
      }

      cachedMethod = new ParameterizedGenericMethodBinding(genericMethod, rawType, this);
      cachedInfo[index] = cachedMethod;
      return cachedMethod;
   }

   public ParameterizedGenericMethodBinding createParameterizedGenericMethod(MethodBinding genericMethod, TypeBinding[] typeArguments) {
      return this.createParameterizedGenericMethod(genericMethod, typeArguments, (TypeBinding)null);
   }

   public ParameterizedGenericMethodBinding createParameterizedGenericMethod(MethodBinding genericMethod, TypeBinding[] typeArguments, TypeBinding targetType) {
      return this.createParameterizedGenericMethod(genericMethod, typeArguments, false, false, targetType);
   }

   public ParameterizedGenericMethodBinding createParameterizedGenericMethod(MethodBinding genericMethod, TypeBinding[] typeArguments, boolean inferredWithUncheckedConversion, boolean hasReturnProblem, TypeBinding targetType) {
      ParameterizedGenericMethodBinding[] cachedInfo = (ParameterizedGenericMethodBinding[])this.uniqueParameterizedGenericMethodBindings.get(genericMethod);
      int argLength = typeArguments == null ? 0 : typeArguments.length;
      boolean needToGrow = false;
      int index = 0;
      int length;
      ParameterizedGenericMethodBinding cachedMethod;
      if (cachedInfo == null) {
         cachedInfo = new ParameterizedGenericMethodBinding[5];
         this.uniqueParameterizedGenericMethodBindings.put(genericMethod, cachedInfo);
      } else {
         length = cachedInfo.length;

         while(true) {
            if (index < length) {
               cachedMethod = cachedInfo[index];
               if (cachedMethod != null) {
                  if (!cachedMethod.isRaw && cachedMethod.targetType == targetType && cachedMethod.inferredWithUncheckedConversion == inferredWithUncheckedConversion) {
                     TypeBinding[] cachedArguments = cachedMethod.typeArguments;
                     int cachedArgLength = cachedArguments == null ? 0 : cachedArguments.length;
                     if (argLength == cachedArgLength) {
                        int j = 0;

                        label76:
                        while(true) {
                           if (j >= cachedArgLength) {
                              if (!inferredWithUncheckedConversion) {
                                 return cachedMethod;
                              }

                              if (!cachedMethod.returnType.isParameterizedType() && !cachedMethod.returnType.isTypeVariable()) {
                                 ReferenceBinding[] var17;
                                 int var16 = (var17 = cachedMethod.thrownExceptions).length;

                                 for(int var15 = 0; var15 < var16; ++var15) {
                                    TypeBinding exc = var17[var15];
                                    if (exc.isParameterizedType() || exc.isTypeVariable()) {
                                       break label76;
                                    }
                                 }

                                 return cachedMethod;
                              }
                              break;
                           }

                           if (typeArguments[j] != cachedArguments[j]) {
                              break;
                           }

                           ++j;
                        }
                     }
                  }

                  ++index;
                  continue;
               }
            }

            needToGrow = true;
            break;
         }
      }

      length = cachedInfo.length;
      if (needToGrow && index == length) {
         System.arraycopy(cachedInfo, 0, cachedInfo = new ParameterizedGenericMethodBinding[length * 2], 0, length);
         this.uniqueParameterizedGenericMethodBindings.put(genericMethod, cachedInfo);
      }

      cachedMethod = new ParameterizedGenericMethodBinding(genericMethod, typeArguments, this, inferredWithUncheckedConversion, hasReturnProblem, targetType);
      cachedInfo[index] = cachedMethod;
      return cachedMethod;
   }

   public PolymorphicMethodBinding createPolymorphicMethod(MethodBinding originalPolymorphicMethod, TypeBinding[] parameters, Scope scope) {
      String key = new String(originalPolymorphicMethod.selector);
      PolymorphicMethodBinding[] cachedInfo = (PolymorphicMethodBinding[])this.uniquePolymorphicMethodBindings.get(key);
      int parametersLength = parameters == null ? 0 : parameters.length;
      TypeBinding[] parametersTypeBinding = new TypeBinding[parametersLength];

      for(int i = 0; i < parametersLength; ++i) {
         TypeBinding parameterTypeBinding = parameters[i];
         if (parameterTypeBinding.id == 12) {
            parametersTypeBinding[i] = this.getType(JAVA_LANG_VOID, this.javaBaseModule());
         } else if (parameterTypeBinding.isPolyType()) {
            PolyTypeBinding ptb = (PolyTypeBinding)parameterTypeBinding;
            if (scope instanceof BlockScope && ptb.expression.resolvedType == null) {
               ptb.expression.setExpectedType(scope.getJavaLangObject());
               parametersTypeBinding[i] = ptb.expression.resolveType((BlockScope)scope);
            } else {
               parametersTypeBinding[i] = ptb.expression.resolvedType;
            }
         } else {
            parametersTypeBinding[i] = parameterTypeBinding.erasure();
         }
      }

      boolean needToGrow = false;
      int index = 0;
      PolymorphicMethodBinding cachedMethod;
      int length;
      if (cachedInfo != null) {
         for(length = cachedInfo.length; index < length; ++index) {
            cachedMethod = cachedInfo[index];
            if (cachedMethod == null) {
               break;
            }

            if (cachedMethod.matches(parametersTypeBinding, originalPolymorphicMethod.returnType)) {
               return cachedMethod;
            }
         }

         needToGrow = true;
      } else {
         cachedInfo = new PolymorphicMethodBinding[5];
         this.uniquePolymorphicMethodBindings.put(key, cachedInfo);
      }

      length = cachedInfo.length;
      if (needToGrow && index == length) {
         System.arraycopy(cachedInfo, 0, cachedInfo = new PolymorphicMethodBinding[length * 2], 0, length);
         this.uniquePolymorphicMethodBindings.put(key, cachedInfo);
      }

      cachedMethod = new PolymorphicMethodBinding(originalPolymorphicMethod, parametersTypeBinding);
      cachedInfo[index] = cachedMethod;
      return cachedMethod;
   }

   public boolean usesAnnotatedTypeSystem() {
      return this.typeSystem.isAnnotatedTypeSystem();
   }

   public MethodBinding updatePolymorphicMethodReturnType(PolymorphicMethodBinding binding, TypeBinding typeBinding) {
      String key = new String(binding.selector);
      PolymorphicMethodBinding[] cachedInfo = (PolymorphicMethodBinding[])this.uniquePolymorphicMethodBindings.get(key);
      boolean needToGrow = false;
      int index = 0;
      TypeBinding[] parameters = binding.parameters;
      int length;
      PolymorphicMethodBinding cachedMethod;
      if (cachedInfo != null) {
         for(length = cachedInfo.length; index < length; ++index) {
            cachedMethod = cachedInfo[index];
            if (cachedMethod == null) {
               break;
            }

            if (cachedMethod.matches(parameters, typeBinding)) {
               return cachedMethod;
            }
         }

         needToGrow = true;
      } else {
         cachedInfo = new PolymorphicMethodBinding[5];
         this.uniquePolymorphicMethodBindings.put(key, cachedInfo);
      }

      length = cachedInfo.length;
      if (needToGrow && index == length) {
         System.arraycopy(cachedInfo, 0, cachedInfo = new PolymorphicMethodBinding[length * 2], 0, length);
         this.uniquePolymorphicMethodBindings.put(key, cachedInfo);
      }

      cachedMethod = new PolymorphicMethodBinding(binding.original(), typeBinding, parameters);
      cachedInfo[index] = cachedMethod;
      return cachedMethod;
   }

   public ParameterizedMethodBinding createGetClassMethod(TypeBinding receiverType, MethodBinding originalMethod, Scope scope) {
      ParameterizedMethodBinding retVal = null;
      if (this.uniqueGetClassMethodBinding == null) {
         this.uniqueGetClassMethodBinding = new SimpleLookupTable(3);
      } else {
         retVal = (ParameterizedMethodBinding)this.uniqueGetClassMethodBinding.get(receiverType);
      }

      if (retVal == null) {
         retVal = ParameterizedMethodBinding.instantiateGetClass(receiverType, originalMethod, scope);
         this.uniqueGetClassMethodBinding.put(receiverType, retVal);
      }

      return retVal;
   }

   public ReferenceBinding createMemberType(ReferenceBinding memberType, ReferenceBinding enclosingType) {
      return this.typeSystem.getMemberType(memberType, enclosingType);
   }

   public ParameterizedTypeBinding createParameterizedType(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType) {
      AnnotationBinding[] annotations = genericType.typeAnnotations;
      return annotations != Binding.NO_ANNOTATIONS ? this.typeSystem.getParameterizedType((ReferenceBinding)genericType.unannotated(), typeArguments, enclosingType, annotations) : this.typeSystem.getParameterizedType(genericType, typeArguments, enclosingType);
   }

   public ParameterizedTypeBinding createParameterizedType(ReferenceBinding genericType, TypeBinding[] typeArguments, ReferenceBinding enclosingType, AnnotationBinding[] annotations) {
      return this.typeSystem.getParameterizedType(genericType, typeArguments, enclosingType, annotations);
   }

   public ReferenceBinding maybeCreateParameterizedType(ReferenceBinding nonGenericType, ReferenceBinding enclosingType) {
      boolean canSeeEnclosingTypeParameters = enclosingType != null && enclosingType.isParameterizedType() | enclosingType.isRawType() && !nonGenericType.isStatic();
      return (ReferenceBinding)(canSeeEnclosingTypeParameters ? this.createParameterizedType(nonGenericType, (TypeBinding[])null, enclosingType) : nonGenericType);
   }

   public TypeBinding createAnnotatedType(TypeBinding type, AnnotationBinding[][] annotations) {
      return this.typeSystem.getAnnotatedType(type, annotations);
   }

   public TypeBinding createAnnotatedType(TypeBinding type, AnnotationBinding[] newbies) {
      int newLength = newbies == null ? 0 : newbies.length;
      if (type != null && newLength != 0) {
         AnnotationBinding[] oldies = type.getTypeAnnotations();
         int oldLength = oldies == null ? 0 : oldies.length;
         if (oldLength > 0) {
            System.arraycopy(newbies, 0, newbies = new AnnotationBinding[newLength + oldLength], 0, newLength);
            System.arraycopy(oldies, 0, newbies, newLength, oldLength);
         }

         if (this.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            long tagBitsSeen = 0L;
            AnnotationBinding[] filtered = new AnnotationBinding[newbies.length];
            int count = 0;

            for(int i = 0; i < newbies.length; ++i) {
               if (newbies[i] == null) {
                  filtered[count++] = null;
                  tagBitsSeen = 0L;
               } else {
                  long tagBits = 0L;
                  if (newbies[i].type.hasNullBit(32)) {
                     tagBits = 72057594037927936L;
                  } else if (newbies[i].type.hasNullBit(64)) {
                     tagBits = 36028797018963968L;
                  }

                  if ((tagBitsSeen & tagBits) == 0L) {
                     tagBitsSeen |= tagBits;
                     filtered[count++] = newbies[i];
                  }
               }
            }

            if (count < newbies.length) {
               System.arraycopy(filtered, 0, newbies = new AnnotationBinding[count], 0, count);
            }
         }

         return this.typeSystem.getAnnotatedType(type, new AnnotationBinding[][]{newbies});
      } else {
         return type;
      }
   }

   public RawTypeBinding createRawType(ReferenceBinding genericType, ReferenceBinding enclosingType) {
      AnnotationBinding[] annotations = genericType.typeAnnotations;
      return annotations != Binding.NO_ANNOTATIONS ? this.typeSystem.getRawType((ReferenceBinding)genericType.unannotated(), enclosingType, annotations) : this.typeSystem.getRawType(genericType, enclosingType);
   }

   public RawTypeBinding createRawType(ReferenceBinding genericType, ReferenceBinding enclosingType, AnnotationBinding[] annotations) {
      return this.typeSystem.getRawType(genericType, enclosingType, annotations);
   }

   public WildcardBinding createWildcard(ReferenceBinding genericType, int rank, TypeBinding bound, TypeBinding[] otherBounds, int boundKind) {
      if (genericType != null) {
         AnnotationBinding[] annotations = genericType.typeAnnotations;
         if (annotations != Binding.NO_ANNOTATIONS) {
            return this.typeSystem.getWildcard((ReferenceBinding)genericType.unannotated(), rank, bound, otherBounds, boundKind, annotations);
         }
      }

      return this.typeSystem.getWildcard(genericType, rank, bound, otherBounds, boundKind);
   }

   public CaptureBinding createCapturedWildcard(WildcardBinding wildcard, ReferenceBinding contextType, int start, int end, ASTNode cud, int id) {
      return this.typeSystem.getCapturedWildcard(wildcard, contextType, start, end, cud, id);
   }

   public WildcardBinding createWildcard(ReferenceBinding genericType, int rank, TypeBinding bound, TypeBinding[] otherBounds, int boundKind, AnnotationBinding[] annotations) {
      return this.typeSystem.getWildcard(genericType, rank, bound, otherBounds, boundKind, annotations);
   }

   public AccessRestriction getAccessRestriction(TypeBinding type) {
      return (AccessRestriction)this.accessRestrictions.get(type);
   }

   public ReferenceBinding getCachedType(char[][] compoundName) {
      ReferenceBinding result = this.getCachedType0(compoundName);
      if (result == null && this.useModuleSystem) {
         ModuleBinding[] modulesToSearch = !this.module.isUnnamed() && !this.module.isAuto ? this.module.getAllRequiredModules() : this.root.knownModules.valueTable;
         ModuleBinding[] var7 = modulesToSearch;
         int var6 = modulesToSearch.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            ModuleBinding someModule = var7[var5];
            if (someModule != null) {
               result = someModule.environment.getCachedType0(compoundName);
               if (result != null && result.isValidBinding()) {
                  break;
               }
            }
         }
      }

      return result;
   }

   public ReferenceBinding getCachedType0(char[][] compoundName) {
      if (compoundName.length == 1) {
         return this.defaultPackage.getType0(compoundName[0]);
      } else {
         PackageBinding packageBinding = this.getPackage0(compoundName[0]);
         if (packageBinding != null && packageBinding != TheNotFoundPackage) {
            int i = 1;

            for(int packageLength = compoundName.length - 1; i < packageLength; ++i) {
               if ((packageBinding = packageBinding.getPackage0Any(compoundName[i])) == null || packageBinding == TheNotFoundPackage) {
                  return null;
               }
            }

            return packageBinding.getType0(compoundName[compoundName.length - 1]);
         } else {
            return null;
         }
      }
   }

   public AnnotationBinding getNullableAnnotation() {
      if (this.nullableAnnotation != null) {
         return this.nullableAnnotation;
      } else if (this.root != this) {
         return this.nullableAnnotation = this.root.getNullableAnnotation();
      } else {
         ReferenceBinding nullable = this.getResolvedType(this.globalOptions.nullableAnnotationName, (Scope)null);
         return this.nullableAnnotation = this.typeSystem.getAnnotationType(nullable, true);
      }
   }

   public char[][] getNullableAnnotationName() {
      return this.globalOptions.nullableAnnotationName;
   }

   public AnnotationBinding getNonNullAnnotation() {
      if (this.nonNullAnnotation != null) {
         return this.nonNullAnnotation;
      } else if (this.root != this) {
         return this.nonNullAnnotation = this.root.getNonNullAnnotation();
      } else {
         ReferenceBinding nonNull = this.getResolvedType(this.globalOptions.nonNullAnnotationName, (Scope)null);
         return this.nonNullAnnotation = this.typeSystem.getAnnotationType(nonNull, true);
      }
   }

   public AnnotationBinding[] nullAnnotationsFromTagBits(long nullTagBits) {
      if (nullTagBits == 72057594037927936L) {
         return new AnnotationBinding[]{this.getNonNullAnnotation()};
      } else {
         return nullTagBits == 36028797018963968L ? new AnnotationBinding[]{this.getNullableAnnotation()} : null;
      }
   }

   public char[][] getNonNullAnnotationName() {
      return this.globalOptions.nonNullAnnotationName;
   }

   public char[][] getNonNullByDefaultAnnotationName() {
      return this.globalOptions.nonNullByDefaultAnnotationName;
   }

   int getNullAnnotationBit(char[][] qualifiedTypeName) {
      String name;
      if (this.allNullAnnotations == null) {
         this.allNullAnnotations = new HashMap();
         this.allNullAnnotations.put(CharOperation.toString(this.globalOptions.nonNullAnnotationName), 32);
         this.allNullAnnotations.put(CharOperation.toString(this.globalOptions.nullableAnnotationName), 64);
         this.allNullAnnotations.put(CharOperation.toString(this.globalOptions.nonNullByDefaultAnnotationName), 128);
         String[] var5;
         int var4 = (var5 = this.globalOptions.nullableAnnotationSecondaryNames).length;

         int var3;
         for(var3 = 0; var3 < var4; ++var3) {
            name = var5[var3];
            this.allNullAnnotations.put(name, 64);
         }

         var4 = (var5 = this.globalOptions.nonNullAnnotationSecondaryNames).length;

         for(var3 = 0; var3 < var4; ++var3) {
            name = var5[var3];
            this.allNullAnnotations.put(name, 32);
         }

         var4 = (var5 = this.globalOptions.nonNullByDefaultAnnotationSecondaryNames).length;

         for(var3 = 0; var3 < var4; ++var3) {
            name = var5[var3];
            this.allNullAnnotations.put(name, 128);
         }
      }

      name = CharOperation.toString(qualifiedTypeName);
      Integer typeBit = (Integer)this.allNullAnnotations.get(name);
      return typeBit == null ? 0 : typeBit;
   }

   public boolean isNullnessAnnotationPackage(PackageBinding pkg) {
      return this.nonnullAnnotationPackage == pkg || this.nullableAnnotationPackage == pkg || this.nonnullByDefaultAnnotationPackage == pkg;
   }

   public boolean usesNullTypeAnnotations() {
      if (this.root != this) {
         return this.root.usesNullTypeAnnotations();
      } else if (this.globalOptions.useNullTypeAnnotations != null) {
         return this.globalOptions.useNullTypeAnnotations;
      } else {
         this.initializeUsesNullTypeAnnotation();
         Iterator var2 = this.deferredEnumMethods.iterator();

         while(var2.hasNext()) {
            MethodBinding enumMethod = (MethodBinding)var2.next();
            int purpose = 0;
            if (CharOperation.equals(enumMethod.selector, TypeConstants.VALUEOF)) {
               purpose = 10;
            } else if (CharOperation.equals(enumMethod.selector, TypeConstants.VALUES)) {
               purpose = 9;
            }

            if (purpose != 0) {
               SyntheticMethodBinding.markNonNull(enumMethod, purpose, this);
            }
         }

         this.deferredEnumMethods.clear();
         return this.globalOptions.useNullTypeAnnotations;
      }
   }

   private void initializeUsesNullTypeAnnotation() {
      this.globalOptions.useNullTypeAnnotations = Boolean.FALSE;
      if (this.globalOptions.isAnnotationBasedNullAnalysisEnabled && this.globalOptions.originalSourceLevel >= 3407872L) {
         boolean origMayTolerateMissingType = this.mayTolerateMissingType;
         this.mayTolerateMissingType = true;

         ReferenceBinding nullable;
         ReferenceBinding nonNull;
         try {
            nullable = this.nullableAnnotation != null ? this.nullableAnnotation.getAnnotationType() : this.getType(this.getNullableAnnotationName(), this.UnNamedModule);
            nonNull = this.nonNullAnnotation != null ? this.nonNullAnnotation.getAnnotationType() : this.getType(this.getNonNullAnnotationName(), this.UnNamedModule);
         } finally {
            this.mayTolerateMissingType = origMayTolerateMissingType;
         }

         if (nullable != null || nonNull != null) {
            if (nullable != null && nonNull != null) {
               long nullableMetaBits = nullable.getAnnotationTagBits() & 9007199254740992L;
               long nonNullMetaBits = nonNull.getAnnotationTagBits() & 9007199254740992L;
               if (nullableMetaBits == nonNullMetaBits) {
                  if (nullableMetaBits != 0L) {
                     this.globalOptions.useNullTypeAnnotations = Boolean.TRUE;
                  }
               }
            }
         }
      }
   }

   PackageBinding getPackage0(char[] name) {
      return this.knownPackages.get(name);
   }

   public ReferenceBinding getResolvedType(char[][] compoundName, Scope scope) {
      return this.getResolvedType(compoundName, scope == null ? this.UnNamedModule : scope.module(), scope);
   }

   public ReferenceBinding getResolvedType(char[][] compoundName, ModuleBinding moduleBinding, Scope scope) {
      if (this.module != moduleBinding) {
         return moduleBinding.environment.getResolvedType(compoundName, moduleBinding, scope);
      } else {
         ReferenceBinding type = this.getType(compoundName, moduleBinding);
         if (type != null) {
            return type;
         } else {
            this.problemReporter.isClassPathCorrect(compoundName, scope == null ? this.root.unitBeingCompleted : scope.referenceCompilationUnit(), this.missingClassFileLocation);
            return this.createMissingType((PackageBinding)null, compoundName);
         }
      }
   }

   public ReferenceBinding getResolvedJavaBaseType(char[][] compoundName, Scope scope) {
      return this.getResolvedType(compoundName, this.javaBaseModule(), scope);
   }

   PackageBinding getTopLevelPackage(char[] name) {
      PackageBinding packageBinding = this.getPackage0(name);
      if (packageBinding != null) {
         return packageBinding == TheNotFoundPackage ? null : packageBinding;
      } else {
         if (this.useModuleSystem) {
            if (this.module != null) {
               packageBinding = this.module.getTopLevelPackage(name);
            }
         } else if (this.nameEnvironment.isPackage((char[][])null, name)) {
            this.knownPackages.put(name, packageBinding = new PackageBinding(name, this, this.module));
         }

         if (packageBinding != null) {
            return packageBinding == TheNotFoundPackage ? null : packageBinding;
         } else {
            this.knownPackages.put(name, TheNotFoundPackage);
            return null;
         }
      }
   }

   public ReferenceBinding getType(char[][] compoundName) {
      return this.getType(compoundName, this.UnNamedModule);
   }

   public ReferenceBinding getType(char[][] compoundName, ModuleBinding mod) {
      ReferenceBinding referenceBinding;
      PackageBinding packageBinding;
      if (compoundName.length == 1) {
         if ((referenceBinding = this.defaultPackage.getType0(compoundName[0])) == null) {
            packageBinding = this.getPackage0(compoundName[0]);
            if (packageBinding != null && packageBinding != TheNotFoundPackage) {
               return null;
            }

            referenceBinding = this.askForType(this.defaultPackage, compoundName[0], mod);
         }
      } else {
         packageBinding = this.getPackage0(compoundName[0]);
         if (packageBinding == TheNotFoundPackage) {
            return null;
         }

         if (packageBinding != null) {
            int i = 1;

            for(int packageLength = compoundName.length - 1; i < packageLength && (packageBinding = packageBinding.getPackage0(compoundName[i])) != null; ++i) {
               if (packageBinding == TheNotFoundPackage) {
                  return null;
               }
            }
         }

         if (packageBinding == null) {
            referenceBinding = this.askForType(compoundName, mod);
         } else if ((referenceBinding = packageBinding.getType0(compoundName[compoundName.length - 1])) == null) {
            referenceBinding = this.askForType(packageBinding, compoundName[compoundName.length - 1], mod);
         }
      }

      if (referenceBinding != null && referenceBinding != TheNotFoundType) {
         referenceBinding = (ReferenceBinding)BinaryTypeBinding.resolveType(referenceBinding, this, false);
         return referenceBinding;
      } else {
         return null;
      }
   }

   private TypeBinding[] getTypeArgumentsFromSignature(SignatureWrapper wrapper, TypeVariableBinding[] staticVariables, ReferenceBinding enclosingType, ReferenceBinding genericType, char[][][] missingTypeNames, ITypeAnnotationWalker walker) {
      ArrayList args = new ArrayList(2);
      int rank = 0;

      do {
         args.add(this.getTypeFromVariantTypeSignature(wrapper, staticVariables, enclosingType, genericType, rank, missingTypeNames, walker.toTypeArgument(rank++)));
      } while(wrapper.signature[wrapper.start] != '>');

      ++wrapper.start;
      TypeBinding[] typeArguments = new TypeBinding[args.size()];
      args.toArray(typeArguments);
      return typeArguments;
   }

   private ReferenceBinding getTypeFromCompoundName(char[][] compoundName, boolean isParameterized, boolean wasMissingType) {
      ReferenceBinding binding = this.getCachedType(compoundName);
      if (binding == null) {
         PackageBinding packageBinding = this.computePackageFrom(compoundName, false);
         if (this.useModuleSystem) {
            binding = packageBinding.getType0(compoundName[compoundName.length - 1]);
         }

         if (binding == null) {
            binding = new UnresolvedReferenceBinding(compoundName, packageBinding);
            if (wasMissingType) {
               ((ReferenceBinding)binding).tagBits |= 128L;
            }

            packageBinding.addType((ReferenceBinding)binding);
         }
      }

      if (binding == TheNotFoundType) {
         if (!wasMissingType) {
            this.problemReporter.isClassPathCorrect(compoundName, this.root.unitBeingCompleted, this.missingClassFileLocation);
         }

         binding = this.createMissingType((PackageBinding)null, compoundName);
      } else if (!isParameterized) {
         binding = (ReferenceBinding)this.convertUnresolvedBinaryToRawType((TypeBinding)binding);
      }

      return (ReferenceBinding)binding;
   }

   ReferenceBinding getTypeFromConstantPoolName(char[] signature, int start, int end, boolean isParameterized, char[][][] missingTypeNames, ITypeAnnotationWalker walker) {
      if (end == -1) {
         end = signature.length;
      }

      char[][] compoundName = CharOperation.splitOn('/', signature, start, end);
      boolean wasMissingType = false;
      if (missingTypeNames != null) {
         int i = 0;

         for(int max = missingTypeNames.length; i < max; ++i) {
            if (CharOperation.equals(compoundName, missingTypeNames[i])) {
               wasMissingType = true;
               break;
            }
         }
      }

      ReferenceBinding binding = this.getTypeFromCompoundName(compoundName, isParameterized, wasMissingType);
      if (walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
         binding = (ReferenceBinding)this.annotateType(binding, walker, missingTypeNames);
      }

      return binding;
   }

   ReferenceBinding getTypeFromConstantPoolName(char[] signature, int start, int end, boolean isParameterized, char[][][] missingTypeNames) {
      return this.getTypeFromConstantPoolName(signature, start, end, isParameterized, missingTypeNames, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
   }

   TypeBinding getTypeFromSignature(char[] signature, int start, int end, boolean isParameterized, TypeBinding enclosingType, char[][][] missingTypeNames, ITypeAnnotationWalker walker) {
      int dimension;
      for(dimension = 0; signature[start] == '['; ++dimension) {
         ++start;
      }

      AnnotationBinding[][] annotationsOnDimensions = null;
      if (dimension > 0 && walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
         for(int i = 0; i < dimension; ++i) {
            AnnotationBinding[] annotations = BinaryTypeBinding.createAnnotations(walker.getAnnotationsAtCursor(0, true), this, missingTypeNames);
            if (annotations != Binding.NO_ANNOTATIONS) {
               if (annotationsOnDimensions == null) {
                  annotationsOnDimensions = new AnnotationBinding[dimension][];
               }

               annotationsOnDimensions[i] = annotations;
            }

            walker = walker.toNextArrayDimension();
         }
      }

      if (end == -1) {
         end = signature.length - 1;
      }

      TypeBinding binding = null;
      if (start == end) {
         switch (signature[start]) {
            case 'B':
               binding = TypeBinding.BYTE;
               break;
            case 'C':
               binding = TypeBinding.CHAR;
               break;
            case 'D':
               binding = TypeBinding.DOUBLE;
               break;
            case 'F':
               binding = TypeBinding.FLOAT;
               break;
            case 'I':
               binding = TypeBinding.INT;
               break;
            case 'J':
               binding = TypeBinding.LONG;
               break;
            case 'S':
               binding = TypeBinding.SHORT;
               break;
            case 'V':
               binding = TypeBinding.VOID;
               break;
            case 'Z':
               binding = TypeBinding.BOOLEAN;
               break;
            default:
               this.problemReporter.corruptedSignature(enclosingType, signature, start);
         }
      } else {
         binding = this.getTypeFromConstantPoolName(signature, start + 1, end, isParameterized, missingTypeNames);
      }

      if (isParameterized) {
         if (dimension != 0) {
            throw new IllegalStateException();
         } else {
            return (TypeBinding)binding;
         }
      } else {
         if (walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
            binding = this.annotateType((TypeBinding)binding, walker, missingTypeNames);
         }

         if (dimension != 0) {
            binding = this.typeSystem.getArrayType((TypeBinding)binding, dimension, AnnotatableTypeSystem.flattenedAnnotations(annotationsOnDimensions));
         }

         return (TypeBinding)binding;
      }
   }

   private TypeBinding annotateType(TypeBinding binding, ITypeAnnotationWalker walker, char[][][] missingTypeNames) {
      if (walker == ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
         return (TypeBinding)binding;
      } else {
         int depth = ((TypeBinding)binding).depth() + 1;
         if (depth > 1) {
            if (((TypeBinding)binding).isUnresolvedType()) {
               binding = ((UnresolvedReferenceBinding)binding).resolve(this, true);
            }

            depth = this.countNonStaticNestingLevels((TypeBinding)binding) + 1;
         }

         AnnotationBinding[][] annotations = null;

         for(int i = 0; i < depth; ++i) {
            AnnotationBinding[] annots = BinaryTypeBinding.createAnnotations(walker.getAnnotationsAtCursor(((TypeBinding)binding).id, i == depth - 1), this, missingTypeNames);
            if (annots != null && annots.length > 0) {
               if (annotations == null) {
                  annotations = new AnnotationBinding[depth][];
               }

               annotations[i] = annots;
            }

            walker = walker.toNextNestedType();
         }

         if (annotations != null) {
            binding = this.createAnnotatedType((TypeBinding)binding, (AnnotationBinding[][])annotations);
         }

         return (TypeBinding)binding;
      }
   }

   private int countNonStaticNestingLevels(TypeBinding binding) {
      if (binding.isUnresolvedType()) {
         throw new IllegalStateException();
      } else {
         int depth = -1;

         for(TypeBinding currentBinding = binding; currentBinding != null; currentBinding = ((TypeBinding)currentBinding).enclosingType()) {
            ++depth;
            if (((TypeBinding)currentBinding).isStatic()) {
               break;
            }
         }

         return depth;
      }
   }

   boolean qualifiedNameMatchesSignature(char[][] name, char[] signature) {
      int s = 1;

      for(int i = 0; i < name.length; ++i) {
         char[] n = name[i];

         for(int j = 0; j < n.length; ++j) {
            if (n[j] != signature[s++]) {
               return false;
            }
         }

         if (signature[s] == ';' && i == name.length - 1) {
            return true;
         }

         if (signature[s++] != '/') {
            return false;
         }
      }

      return false;
   }

   public TypeBinding getTypeFromTypeSignature(SignatureWrapper wrapper, TypeVariableBinding[] staticVariables, ReferenceBinding enclosingType, char[][][] missingTypeNames, ITypeAnnotationWalker walker) {
      int dimension;
      for(dimension = 0; wrapper.signature[wrapper.start] == '['; ++dimension) {
         ++wrapper.start;
      }

      AnnotationBinding[][] annotationsOnDimensions = null;
      int varStart;
      if (dimension > 0 && walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
         for(varStart = 0; varStart < dimension; ++varStart) {
            AnnotationBinding[] annotations = BinaryTypeBinding.createAnnotations(walker.getAnnotationsAtCursor(0, true), this, missingTypeNames);
            if (annotations != Binding.NO_ANNOTATIONS) {
               if (annotationsOnDimensions == null) {
                  annotationsOnDimensions = new AnnotationBinding[dimension][];
               }

               annotationsOnDimensions[varStart] = annotations;
            }

            walker = walker.toNextArrayDimension();
         }
      }

      ReferenceBinding initialType;
      if (wrapper.signature[wrapper.start] == 'T') {
         varStart = wrapper.start + 1;
         int varEnd = wrapper.computeEnd();
         int i = staticVariables.length;

         do {
            --i;
            if (i < 0) {
               initialType = enclosingType;

               label102:
               do {
                  TypeVariableBinding[] enclosingTypeVariables;
                  if (enclosingType instanceof BinaryTypeBinding) {
                     enclosingTypeVariables = ((BinaryTypeBinding)enclosingType).typeVariables;
                  } else {
                     enclosingTypeVariables = enclosingType.typeVariables();
                  }

                  int i = enclosingTypeVariables.length;

                  do {
                     --i;
                     if (i < 0) {
                        continue label102;
                     }
                  } while(!CharOperation.equals(enclosingTypeVariables[i].sourceName, wrapper.signature, varStart, varEnd));

                  return this.getTypeFromTypeVariable(enclosingTypeVariables[i], dimension, annotationsOnDimensions, walker, missingTypeNames);
               } while((enclosingType = enclosingType.enclosingType()) != null);

               this.problemReporter.undefinedTypeVariableSignature(CharOperation.subarray(wrapper.signature, varStart, varEnd), initialType);
               return null;
            }
         } while(!CharOperation.equals(staticVariables[i].sourceName, wrapper.signature, varStart, varEnd));

         return this.getTypeFromTypeVariable(staticVariables[i], dimension, annotationsOnDimensions, walker, missingTypeNames);
      } else {
         boolean isParameterized;
         TypeBinding type = this.getTypeFromSignature(wrapper.signature, wrapper.start, wrapper.computeEnd(), isParameterized = wrapper.end == wrapper.bracket, enclosingType, missingTypeNames, walker);
         if (!isParameterized) {
            return (TypeBinding)(dimension == 0 ? type : this.createArrayType(type, dimension, AnnotatableTypeSystem.flattenedAnnotations(annotationsOnDimensions)));
         } else {
            initialType = (ReferenceBinding)type;
            if (walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER && initialType instanceof UnresolvedReferenceBinding && initialType.depth() > 0) {
               initialType = (ReferenceBinding)BinaryTypeBinding.resolveType(initialType, this, false);
            }

            ReferenceBinding actualEnclosing = initialType.enclosingType();
            ITypeAnnotationWalker savedWalker = walker;
            if (walker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER && initialType.depth() > 0) {
               int nonStaticNestingLevels = this.countNonStaticNestingLevels(initialType);

               for(int i = 0; i < nonStaticNestingLevels; ++i) {
                  walker = walker.toNextNestedType();
               }
            }

            TypeBinding[] typeArguments = this.getTypeArgumentsFromSignature(wrapper, staticVariables, enclosingType, initialType, missingTypeNames, walker);
            ReferenceBinding currentType = this.createParameterizedType(initialType, typeArguments, actualEnclosing);

            ReferenceBinding memberType;
            for(ReferenceBinding plainCurrent = initialType; wrapper.signature[wrapper.start] == '.'; plainCurrent = memberType) {
               ++wrapper.start;
               int memberStart = wrapper.start;
               char[] memberName = wrapper.nextWord();
               plainCurrent = (ReferenceBinding)BinaryTypeBinding.resolveType(plainCurrent, this, false);
               memberType = plainCurrent.getMemberType(memberName);
               if (memberType == null) {
                  this.problemReporter.corruptedSignature((TypeBinding)currentType, wrapper.signature, memberStart);
               }

               if (memberType.isStatic()) {
                  walker = savedWalker;
               } else {
                  walker = walker.toNextNestedType();
               }

               if (wrapper.signature[wrapper.start] == '<') {
                  ++wrapper.start;
                  typeArguments = this.getTypeArgumentsFromSignature(wrapper, staticVariables, enclosingType, memberType, missingTypeNames, walker);
               } else {
                  typeArguments = null;
               }

               if (typeArguments == null && (memberType.isStatic() || !((ReferenceBinding)currentType).isParameterizedType())) {
                  currentType = memberType;
               } else {
                  if (memberType.isStatic()) {
                     currentType = plainCurrent;
                  }

                  currentType = this.createParameterizedType(memberType, typeArguments, (ReferenceBinding)currentType);
               }
            }

            ++wrapper.start;
            TypeBinding annotatedType = this.annotateType((TypeBinding)currentType, savedWalker, missingTypeNames);
            return (TypeBinding)(dimension == 0 ? annotatedType : this.createArrayType(annotatedType, dimension, AnnotatableTypeSystem.flattenedAnnotations(annotationsOnDimensions)));
         }
      }
   }

   private TypeBinding getTypeFromTypeVariable(TypeVariableBinding typeVariableBinding, int dimension, AnnotationBinding[][] annotationsOnDimensions, ITypeAnnotationWalker walker, char[][][] missingTypeNames) {
      AnnotationBinding[] annotations = BinaryTypeBinding.createAnnotations(walker.getAnnotationsAtCursor(-1, false), this, missingTypeNames);
      if (annotations != null && annotations != Binding.NO_ANNOTATIONS) {
         typeVariableBinding = (TypeVariableBinding)this.createAnnotatedType(typeVariableBinding, (AnnotationBinding[][])(new AnnotationBinding[][]{annotations}));
      }

      return (TypeBinding)(dimension == 0 ? typeVariableBinding : this.typeSystem.getArrayType(typeVariableBinding, dimension, AnnotatableTypeSystem.flattenedAnnotations(annotationsOnDimensions)));
   }

   TypeBinding getTypeFromVariantTypeSignature(SignatureWrapper wrapper, TypeVariableBinding[] staticVariables, ReferenceBinding enclosingType, ReferenceBinding genericType, int rank, char[][][] missingTypeNames, ITypeAnnotationWalker walker) {
      TypeBinding bound;
      AnnotationBinding[] annotations;
      switch (wrapper.signature[wrapper.start]) {
         case '*':
            ++wrapper.start;
            annotations = BinaryTypeBinding.createAnnotations(walker.getAnnotationsAtCursor(-1, false), this, missingTypeNames);
            return this.typeSystem.getWildcard(genericType, rank, (TypeBinding)null, (TypeBinding[])null, 0, annotations);
         case '+':
            ++wrapper.start;
            bound = this.getTypeFromTypeSignature(wrapper, staticVariables, enclosingType, missingTypeNames, walker.toWildcardBound());
            annotations = BinaryTypeBinding.createAnnotations(walker.getAnnotationsAtCursor(-1, false), this, missingTypeNames);
            return this.typeSystem.getWildcard(genericType, rank, bound, (TypeBinding[])null, 1, annotations);
         case ',':
         default:
            return this.getTypeFromTypeSignature(wrapper, staticVariables, enclosingType, missingTypeNames, walker);
         case '-':
            ++wrapper.start;
            bound = this.getTypeFromTypeSignature(wrapper, staticVariables, enclosingType, missingTypeNames, walker.toWildcardBound());
            annotations = BinaryTypeBinding.createAnnotations(walker.getAnnotationsAtCursor(-1, false), this, missingTypeNames);
            return this.typeSystem.getWildcard(genericType, rank, bound, (TypeBinding[])null, 2, annotations);
      }
   }

   boolean isMissingType(char[] typeName) {
      int i = this.missingTypes == null ? 0 : this.missingTypes.size();

      MissingTypeBinding missingType;
      do {
         --i;
         if (i < 0) {
            return false;
         }

         missingType = (MissingTypeBinding)this.missingTypes.get(i);
      } while(!CharOperation.equals(missingType.sourceName, typeName));

      return true;
   }

   public MethodVerifier methodVerifier() {
      if (this.verifier == null) {
         this.verifier = this.newMethodVerifier();
      }

      return this.verifier;
   }

   public MethodVerifier newMethodVerifier() {
      return new MethodVerifier15(this);
   }

   public void releaseClassFiles(ClassFile[] classFiles) {
      int i = 0;

      for(int fileCount = classFiles.length; i < fileCount; ++i) {
         this.classFilePool.release(classFiles[i]);
      }

   }

   public void reset() {
      if (this.root != this) {
         this.root.reset();
      } else {
         this.stepCompleted = 0;
         this.knownModules = new HashtableOfModule();
         this.UnNamedModule = new ModuleBinding.UnNamedModule(this);
         this.module = this.UnNamedModule;
         this.JavaBaseModule = null;
         this.defaultPackage = new PackageBinding(this);
         this.defaultImports = null;
         this.knownPackages = new HashtableOfPackage();
         this.accessRestrictions = new HashMap(3);
         this.verifier = null;
         this.uniqueParameterizedGenericMethodBindings = new SimpleLookupTable(3);
         this.uniquePolymorphicMethodBindings = new SimpleLookupTable(3);
         this.uniqueGetClassMethodBinding = null;
         this.missingTypes = null;
         this.typesBeingConnected = new HashSet();
         int i = this.units.length;

         while(true) {
            --i;
            if (i < 0) {
               this.lastUnitIndex = -1;
               this.lastCompletedUnitIndex = -1;
               this.unitBeingCompleted = null;
               this.classFilePool.reset();
               this.typeSystem.reset();
               return;
            }

            this.units[i] = null;
         }
      }
   }

   public void setAccessRestriction(ReferenceBinding type, AccessRestriction accessRestriction) {
      if (accessRestriction != null) {
         type.modifiers |= 262144;
         this.accessRestrictions.put(type, accessRestriction);
      }
   }

   void updateCaches(UnresolvedReferenceBinding unresolvedType, ReferenceBinding resolvedType) {
      this.typeSystem.updateCaches(unresolvedType, resolvedType);
   }

   public void addResolutionListener(IQualifiedTypeResolutionListener resolutionListener) {
      synchronized(this.root) {
         int length = this.root.resolutionListeners.length;

         for(int i = 0; i < length; ++i) {
            if (this.root.resolutionListeners[i].equals(resolutionListener)) {
               return;
            }
         }

         System.arraycopy(this.root.resolutionListeners, 0, this.root.resolutionListeners = new IQualifiedTypeResolutionListener[length + 1], 0, length);
         this.root.resolutionListeners[length] = resolutionListener;
      }
   }

   public TypeBinding getUnannotatedType(TypeBinding typeBinding) {
      return this.typeSystem.getUnannotatedType(typeBinding);
   }

   public TypeBinding[] getAnnotatedTypes(TypeBinding type) {
      return this.typeSystem.getAnnotatedTypes(type);
   }

   public AnnotationBinding[] filterNullTypeAnnotations(AnnotationBinding[] typeAnnotations) {
      if (typeAnnotations.length == 0) {
         return typeAnnotations;
      } else {
         AnnotationBinding[] filtered = new AnnotationBinding[typeAnnotations.length];
         int count = 0;

         for(int i = 0; i < typeAnnotations.length; ++i) {
            AnnotationBinding typeAnnotation = typeAnnotations[i];
            if (typeAnnotation == null) {
               ++count;
            } else if (!typeAnnotation.type.hasNullBit(96)) {
               filtered[count++] = typeAnnotation;
            }
         }

         if (count == 0) {
            return Binding.NO_ANNOTATIONS;
         } else if (count == typeAnnotations.length) {
            return typeAnnotations;
         } else {
            System.arraycopy(filtered, 0, filtered = new AnnotationBinding[count], 0, count);
            return filtered;
         }
      }
   }

   public boolean containsNullTypeAnnotation(IBinaryAnnotation[] typeAnnotations) {
      if (typeAnnotations.length == 0) {
         return false;
      } else {
         for(int i = 0; i < typeAnnotations.length; ++i) {
            IBinaryAnnotation typeAnnotation = typeAnnotations[i];
            char[] typeName = typeAnnotation.getTypeName();
            if (typeName != null && typeName.length >= 3 && typeName[0] == 'L') {
               char[][] name = CharOperation.splitOn('/', typeName, 1, typeName.length - 1);
               if (this.getNullAnnotationBit(name) != 0) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean containsNullTypeAnnotation(AnnotationBinding[] typeAnnotations) {
      if (typeAnnotations.length == 0) {
         return false;
      } else {
         for(int i = 0; i < typeAnnotations.length; ++i) {
            AnnotationBinding typeAnnotation = typeAnnotations[i];
            if (typeAnnotation.type.hasNullBit(96)) {
               return true;
            }
         }

         return false;
      }
   }
}
