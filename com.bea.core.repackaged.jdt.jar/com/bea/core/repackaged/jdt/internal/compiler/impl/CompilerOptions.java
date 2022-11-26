package com.bea.core.repackaged.jdt.internal.compiler.impl;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.Compiler;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CompilerOptions {
   public static final String OPTION_LocalVariableAttribute = "com.bea.core.repackaged.jdt.core.compiler.debug.localVariable";
   public static final String OPTION_LineNumberAttribute = "com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber";
   public static final String OPTION_SourceFileAttribute = "com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile";
   public static final String OPTION_PreserveUnusedLocal = "com.bea.core.repackaged.jdt.core.compiler.codegen.unusedLocal";
   public static final String OPTION_MethodParametersAttribute = "com.bea.core.repackaged.jdt.core.compiler.codegen.methodParameters";
   public static final String OPTION_LambdaGenericSignature = "com.bea.core.repackaged.jdt.core.compiler.codegen.lambda.genericSignature";
   public static final String OPTION_DocCommentSupport = "com.bea.core.repackaged.jdt.core.compiler.doc.comment.support";
   public static final String OPTION_ReportMethodWithConstructorName = "com.bea.core.repackaged.jdt.core.compiler.problem.methodWithConstructorName";
   public static final String OPTION_ReportOverridingPackageDefaultMethod = "com.bea.core.repackaged.jdt.core.compiler.problem.overridingPackageDefaultMethod";
   public static final String OPTION_ReportDeprecation = "com.bea.core.repackaged.jdt.core.compiler.problem.deprecation";
   public static final String OPTION_ReportTerminalDeprecation = "com.bea.core.repackaged.jdt.core.compiler.problem.terminalDeprecation";
   public static final String OPTION_ReportDeprecationInDeprecatedCode = "com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode";
   public static final String OPTION_ReportDeprecationWhenOverridingDeprecatedMethod = "com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod";
   public static final String OPTION_ReportHiddenCatchBlock = "com.bea.core.repackaged.jdt.core.compiler.problem.hiddenCatchBlock";
   public static final String OPTION_ReportUnusedLocal = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedLocal";
   public static final String OPTION_ReportUnusedParameter = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter";
   public static final String OPTION_ReportUnusedExceptionParameter = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedExceptionParameter";
   public static final String OPTION_ReportUnusedParameterWhenImplementingAbstract = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenImplementingAbstract";
   public static final String OPTION_ReportUnusedParameterWhenOverridingConcrete = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenOverridingConcrete";
   public static final String OPTION_ReportUnusedParameterIncludeDocCommentReference = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterIncludeDocCommentReference";
   public static final String OPTION_ReportUnusedImport = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedImport";
   public static final String OPTION_ReportSyntheticAccessEmulation = "com.bea.core.repackaged.jdt.core.compiler.problem.syntheticAccessEmulation";
   public static final String OPTION_ReportNoEffectAssignment = "com.bea.core.repackaged.jdt.core.compiler.problem.noEffectAssignment";
   public static final String OPTION_ReportLocalVariableHiding = "com.bea.core.repackaged.jdt.core.compiler.problem.localVariableHiding";
   public static final String OPTION_ReportSpecialParameterHidingField = "com.bea.core.repackaged.jdt.core.compiler.problem.specialParameterHidingField";
   public static final String OPTION_ReportFieldHiding = "com.bea.core.repackaged.jdt.core.compiler.problem.fieldHiding";
   public static final String OPTION_ReportTypeParameterHiding = "com.bea.core.repackaged.jdt.core.compiler.problem.typeParameterHiding";
   public static final String OPTION_ReportPossibleAccidentalBooleanAssignment = "com.bea.core.repackaged.jdt.core.compiler.problem.possibleAccidentalBooleanAssignment";
   public static final String OPTION_ReportNonExternalizedStringLiteral = "com.bea.core.repackaged.jdt.core.compiler.problem.nonExternalizedStringLiteral";
   public static final String OPTION_ReportIncompatibleNonInheritedInterfaceMethod = "com.bea.core.repackaged.jdt.core.compiler.problem.incompatibleNonInheritedInterfaceMethod";
   public static final String OPTION_ReportUnusedPrivateMember = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedPrivateMember";
   public static final String OPTION_ReportNoImplicitStringConversion = "com.bea.core.repackaged.jdt.core.compiler.problem.noImplicitStringConversion";
   public static final String OPTION_ReportAssertIdentifier = "com.bea.core.repackaged.jdt.core.compiler.problem.assertIdentifier";
   public static final String OPTION_ReportEnumIdentifier = "com.bea.core.repackaged.jdt.core.compiler.problem.enumIdentifier";
   public static final String OPTION_ReportNonStaticAccessToStatic = "com.bea.core.repackaged.jdt.core.compiler.problem.staticAccessReceiver";
   public static final String OPTION_ReportIndirectStaticAccess = "com.bea.core.repackaged.jdt.core.compiler.problem.indirectStaticAccess";
   public static final String OPTION_ReportEmptyStatement = "com.bea.core.repackaged.jdt.core.compiler.problem.emptyStatement";
   public static final String OPTION_ReportUnnecessaryTypeCheck = "com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryTypeCheck";
   public static final String OPTION_ReportUnnecessaryElse = "com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryElse";
   public static final String OPTION_ReportUndocumentedEmptyBlock = "com.bea.core.repackaged.jdt.core.compiler.problem.undocumentedEmptyBlock";
   public static final String OPTION_ReportInvalidJavadoc = "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc";
   public static final String OPTION_ReportInvalidJavadocTags = "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTags";
   public static final String OPTION_ReportInvalidJavadocTagsDeprecatedRef = "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef";
   public static final String OPTION_ReportInvalidJavadocTagsNotVisibleRef = "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef";
   public static final String OPTION_ReportInvalidJavadocTagsVisibility = "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsVisibility";
   public static final String OPTION_ReportMissingJavadocTags = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags";
   public static final String OPTION_ReportMissingJavadocTagsVisibility = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsVisibility";
   public static final String OPTION_ReportMissingJavadocTagsOverriding = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsOverriding";
   public static final String OPTION_ReportMissingJavadocTagsMethodTypeParameters = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsMethodTypeParameters";
   public static final String OPTION_ReportMissingJavadocComments = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments";
   public static final String OPTION_ReportMissingJavadocTagDescription = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagDescription";
   public static final String OPTION_ReportMissingJavadocCommentsVisibility = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsVisibility";
   public static final String OPTION_ReportMissingJavadocCommentsOverriding = "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsOverriding";
   public static final String OPTION_ReportFinallyBlockNotCompletingNormally = "com.bea.core.repackaged.jdt.core.compiler.problem.finallyBlockNotCompletingNormally";
   public static final String OPTION_ReportUnusedDeclaredThrownException = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownException";
   public static final String OPTION_ReportUnusedDeclaredThrownExceptionWhenOverriding = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionWhenOverriding";
   public static final String OPTION_ReportUnusedDeclaredThrownExceptionIncludeDocCommentReference = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionIncludeDocCommentReference";
   public static final String OPTION_ReportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionExemptExceptionAndThrowable";
   public static final String OPTION_ReportUnqualifiedFieldAccess = "com.bea.core.repackaged.jdt.core.compiler.problem.unqualifiedFieldAccess";
   public static final String OPTION_ReportUnavoidableGenericTypeProblems = "com.bea.core.repackaged.jdt.core.compiler.problem.unavoidableGenericTypeProblems";
   public static final String OPTION_ReportUncheckedTypeOperation = "com.bea.core.repackaged.jdt.core.compiler.problem.uncheckedTypeOperation";
   public static final String OPTION_ReportRawTypeReference = "com.bea.core.repackaged.jdt.core.compiler.problem.rawTypeReference";
   public static final String OPTION_ReportFinalParameterBound = "com.bea.core.repackaged.jdt.core.compiler.problem.finalParameterBound";
   public static final String OPTION_ReportMissingSerialVersion = "com.bea.core.repackaged.jdt.core.compiler.problem.missingSerialVersion";
   public static final String OPTION_ReportVarargsArgumentNeedCast = "com.bea.core.repackaged.jdt.core.compiler.problem.varargsArgumentNeedCast";
   public static final String OPTION_ReportUnusedTypeArgumentsForMethodInvocation = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation";
   public static final String OPTION_Source = "com.bea.core.repackaged.jdt.core.compiler.source";
   public static final String OPTION_TargetPlatform = "com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform";
   public static final String OPTION_Compliance = "com.bea.core.repackaged.jdt.core.compiler.compliance";
   public static final String OPTION_Release = "com.bea.core.repackaged.jdt.core.compiler.release";
   public static final String OPTION_Encoding = "com.bea.core.repackaged.jdt.core.encoding";
   public static final String OPTION_MaxProblemPerUnit = "com.bea.core.repackaged.jdt.core.compiler.maxProblemPerUnit";
   public static final String OPTION_TaskTags = "com.bea.core.repackaged.jdt.core.compiler.taskTags";
   public static final String OPTION_TaskPriorities = "com.bea.core.repackaged.jdt.core.compiler.taskPriorities";
   public static final String OPTION_TaskCaseSensitive = "com.bea.core.repackaged.jdt.core.compiler.taskCaseSensitive";
   public static final String OPTION_InlineJsr = "com.bea.core.repackaged.jdt.core.compiler.codegen.inlineJsrBytecode";
   public static final String OPTION_ShareCommonFinallyBlocks = "com.bea.core.repackaged.jdt.core.compiler.codegen.shareCommonFinallyBlocks";
   public static final String OPTION_ReportNullReference = "com.bea.core.repackaged.jdt.core.compiler.problem.nullReference";
   public static final String OPTION_ReportPotentialNullReference = "com.bea.core.repackaged.jdt.core.compiler.problem.potentialNullReference";
   public static final String OPTION_ReportRedundantNullCheck = "com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullCheck";
   public static final String OPTION_ReportAutoboxing = "com.bea.core.repackaged.jdt.core.compiler.problem.autoboxing";
   public static final String OPTION_ReportAnnotationSuperInterface = "com.bea.core.repackaged.jdt.core.compiler.problem.annotationSuperInterface";
   public static final String OPTION_ReportMissingOverrideAnnotation = "com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotation";
   public static final String OPTION_ReportMissingOverrideAnnotationForInterfaceMethodImplementation = "com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotationForInterfaceMethodImplementation";
   public static final String OPTION_ReportMissingDeprecatedAnnotation = "com.bea.core.repackaged.jdt.core.compiler.problem.missingDeprecatedAnnotation";
   public static final String OPTION_ReportIncompleteEnumSwitch = "com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch";
   public static final String OPTION_ReportMissingEnumCaseDespiteDefault = "com.bea.core.repackaged.jdt.core.compiler.problem.missingEnumCaseDespiteDefault";
   public static final String OPTION_ReportMissingDefaultCase = "com.bea.core.repackaged.jdt.core.compiler.problem.missingDefaultCase";
   public static final String OPTION_ReportForbiddenReference = "com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference";
   public static final String OPTION_ReportDiscouragedReference = "com.bea.core.repackaged.jdt.core.compiler.problem.discouragedReference";
   public static final String OPTION_SuppressWarnings = "com.bea.core.repackaged.jdt.core.compiler.problem.suppressWarnings";
   public static final String OPTION_SuppressOptionalErrors = "com.bea.core.repackaged.jdt.core.compiler.problem.suppressOptionalErrors";
   public static final String OPTION_ReportUnhandledWarningToken = "com.bea.core.repackaged.jdt.core.compiler.problem.unhandledWarningToken";
   public static final String OPTION_ReportUnusedTypeParameter = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeParameter";
   public static final String OPTION_ReportUnusedWarningToken = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedWarningToken";
   public static final String OPTION_ReportUnusedLabel = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedLabel";
   public static final String OPTION_FatalOptionalError = "com.bea.core.repackaged.jdt.core.compiler.problem.fatalOptionalError";
   public static final String OPTION_ReportParameterAssignment = "com.bea.core.repackaged.jdt.core.compiler.problem.parameterAssignment";
   public static final String OPTION_ReportFallthroughCase = "com.bea.core.repackaged.jdt.core.compiler.problem.fallthroughCase";
   public static final String OPTION_ReportOverridingMethodWithoutSuperInvocation = "com.bea.core.repackaged.jdt.core.compiler.problem.overridingMethodWithoutSuperInvocation";
   public static final String OPTION_GenerateClassFiles = "com.bea.core.repackaged.jdt.core.compiler.generateClassFiles";
   public static final String OPTION_Process_Annotations = "com.bea.core.repackaged.jdt.core.compiler.processAnnotations";
   public static final String OPTION_Store_Annotations = "com.bea.core.repackaged.jdt.core.compiler.storeAnnotations";
   public static final String OPTION_EmulateJavacBug8031744 = "com.bea.core.repackaged.jdt.core.compiler.emulateJavacBug8031744";
   public static final String OPTION_ReportRedundantSuperinterface = "com.bea.core.repackaged.jdt.core.compiler.problem.redundantSuperinterface";
   public static final String OPTION_ReportComparingIdentical = "com.bea.core.repackaged.jdt.core.compiler.problem.comparingIdentical";
   public static final String OPTION_ReportMissingSynchronizedOnInheritedMethod = "com.bea.core.repackaged.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod";
   public static final String OPTION_ReportMissingHashCodeMethod = "com.bea.core.repackaged.jdt.core.compiler.problem.missingHashCodeMethod";
   public static final String OPTION_ReportDeadCode = "com.bea.core.repackaged.jdt.core.compiler.problem.deadCode";
   public static final String OPTION_ReportDeadCodeInTrivialIfStatement = "com.bea.core.repackaged.jdt.core.compiler.problem.deadCodeInTrivialIfStatement";
   public static final String OPTION_ReportTasks = "com.bea.core.repackaged.jdt.core.compiler.problem.tasks";
   public static final String OPTION_ReportUnusedObjectAllocation = "com.bea.core.repackaged.jdt.core.compiler.problem.unusedObjectAllocation";
   public static final String OPTION_IncludeNullInfoFromAsserts = "com.bea.core.repackaged.jdt.core.compiler.problem.includeNullInfoFromAsserts";
   public static final String OPTION_ReportMethodCanBeStatic = "com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBeStatic";
   public static final String OPTION_ReportMethodCanBePotentiallyStatic = "com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBePotentiallyStatic";
   public static final String OPTION_ReportRedundantSpecificationOfTypeArguments = "com.bea.core.repackaged.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments";
   public static final String OPTION_ReportUnclosedCloseable = "com.bea.core.repackaged.jdt.core.compiler.problem.unclosedCloseable";
   public static final String OPTION_ReportPotentiallyUnclosedCloseable = "com.bea.core.repackaged.jdt.core.compiler.problem.potentiallyUnclosedCloseable";
   public static final String OPTION_ReportExplicitlyClosedAutoCloseable = "com.bea.core.repackaged.jdt.core.compiler.problem.explicitlyClosedAutoCloseable";
   public static final String OPTION_ReportNullSpecViolation = "com.bea.core.repackaged.jdt.core.compiler.problem.nullSpecViolation";
   public static final String OPTION_ReportNullAnnotationInferenceConflict = "com.bea.core.repackaged.jdt.core.compiler.problem.nullAnnotationInferenceConflict";
   public static final String OPTION_ReportNullUncheckedConversion = "com.bea.core.repackaged.jdt.core.compiler.problem.nullUncheckedConversion";
   public static final String OPTION_ReportRedundantNullAnnotation = "com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullAnnotation";
   public static final String OPTION_AnnotationBasedNullAnalysis = "com.bea.core.repackaged.jdt.core.compiler.annotation.nullanalysis";
   public static final String OPTION_NullableAnnotationName = "com.bea.core.repackaged.jdt.core.compiler.annotation.nullable";
   public static final String OPTION_NonNullAnnotationName = "com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull";
   public static final String OPTION_NonNullByDefaultAnnotationName = "com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault";
   public static final String OPTION_NullableAnnotationSecondaryNames = "com.bea.core.repackaged.jdt.core.compiler.annotation.nullable.secondary";
   public static final String OPTION_NonNullAnnotationSecondaryNames = "com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull.secondary";
   public static final String OPTION_NonNullByDefaultAnnotationSecondaryNames = "com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault.secondary";
   public static final String OPTION_ReportUninternedIdentityComparison = "com.bea.core.repackaged.jdt.core.compiler.problem.uninternedIdentityComparison";
   static final char[][] DEFAULT_NULLABLE_ANNOTATION_NAME = CharOperation.splitOn('.', "com.bea.core.repackaged.jdt.annotation.Nullable".toCharArray());
   static final char[][] DEFAULT_NONNULL_ANNOTATION_NAME = CharOperation.splitOn('.', "com.bea.core.repackaged.jdt.annotation.NonNull".toCharArray());
   static final char[][] DEFAULT_NONNULLBYDEFAULT_ANNOTATION_NAME = CharOperation.splitOn('.', "com.bea.core.repackaged.jdt.annotation.NonNullByDefault".toCharArray());
   public static final String OPTION_ReportMissingNonNullByDefaultAnnotation = "com.bea.core.repackaged.jdt.core.compiler.annotation.missingNonNullByDefaultAnnotation";
   public static final String OPTION_SyntacticNullAnalysisForFields = "com.bea.core.repackaged.jdt.core.compiler.problem.syntacticNullAnalysisForFields";
   public static final String OPTION_InheritNullAnnotations = "com.bea.core.repackaged.jdt.core.compiler.annotation.inheritNullAnnotations";
   public static final String OPTION_ReportNonnullParameterAnnotationDropped = "com.bea.core.repackaged.jdt.core.compiler.problem.nonnullParameterAnnotationDropped";
   public static final String OPTION_PessimisticNullAnalysisForFreeTypeVariables = "com.bea.core.repackaged.jdt.core.compiler.problem.pessimisticNullAnalysisForFreeTypeVariables";
   public static final String OPTION_ReportNonNullTypeVariableFromLegacyInvocation = "com.bea.core.repackaged.jdt.core.compiler.problem.nonnullTypeVariableFromLegacyInvocation";
   public static final String OPTION_ReportUnlikelyCollectionMethodArgumentType = "com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentType";
   public static final String OPTION_ReportUnlikelyCollectionMethodArgumentTypeStrict = "com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentTypeStrict";
   public static final String OPTION_ReportUnlikelyEqualsArgumentType = "com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyEqualsArgumentType";
   public static final String OPTION_ReportAPILeak = "com.bea.core.repackaged.jdt.core.compiler.problem.APILeak";
   public static final String OPTION_ReportUnstableAutoModuleName = "com.bea.core.repackaged.jdt.core.compiler.problem.unstableAutoModuleName";
   public static final String OPTION_EnablePreviews = "com.bea.core.repackaged.jdt.core.compiler.problem.enablePreviewFeatures";
   public static final String OPTION_ReportPreviewFeatures = "com.bea.core.repackaged.jdt.core.compiler.problem.reportPreviewFeatures";
   public static final String OPTION_JdtDebugCompileMode = "com.bea.core.repackaged.jdt.internal.debug.compile.mode";
   public static final String GENERATE = "generate";
   public static final String DO_NOT_GENERATE = "do not generate";
   public static final String PRESERVE = "preserve";
   public static final String OPTIMIZE_OUT = "optimize out";
   public static final String VERSION_1_1 = "1.1";
   public static final String VERSION_1_2 = "1.2";
   public static final String VERSION_1_3 = "1.3";
   public static final String VERSION_1_4 = "1.4";
   public static final String VERSION_JSR14 = "jsr14";
   public static final String VERSION_CLDC1_1 = "cldc1.1";
   public static final String VERSION_1_5 = "1.5";
   public static final String VERSION_1_6 = "1.6";
   public static final String VERSION_1_7 = "1.7";
   public static final String VERSION_1_8 = "1.8";
   public static final String VERSION_9 = "9";
   public static final String VERSION_10 = "10";
   public static final String VERSION_11 = "11";
   public static final String VERSION_12 = "12";
   public static final String ERROR = "error";
   public static final String WARNING = "warning";
   public static final String INFO = "info";
   public static final String IGNORE = "ignore";
   public static final String ENABLED = "enabled";
   public static final String DISABLED = "disabled";
   public static final String PUBLIC = "public";
   public static final String PROTECTED = "protected";
   public static final String DEFAULT = "default";
   public static final String PRIVATE = "private";
   public static final String RETURN_TAG = "return_tag";
   public static final String NO_TAG = "no_tag";
   public static final String ALL_STANDARD_TAGS = "all_standard_tags";
   private static final String[] NO_STRINGS = new String[0];
   public static final int MethodWithConstructorName = 1;
   public static final int OverriddenPackageDefaultMethod = 2;
   public static final int UsingDeprecatedAPI = 4;
   public static final int MaskedCatchBlock = 8;
   public static final int UnusedLocalVariable = 16;
   public static final int UnusedArgument = 32;
   public static final int NoImplicitStringConversion = 64;
   public static final int AccessEmulation = 128;
   public static final int NonExternalizedString = 256;
   public static final int AssertUsedAsAnIdentifier = 512;
   public static final int UnusedImport = 1024;
   public static final int NonStaticAccessToStatic = 2048;
   public static final int Task = 4096;
   public static final int NoEffectAssignment = 8192;
   public static final int IncompatibleNonInheritedInterfaceMethod = 16384;
   public static final int UnusedPrivateMember = 32768;
   public static final int LocalVariableHiding = 65536;
   public static final int FieldHiding = 131072;
   public static final int AccidentalBooleanAssign = 262144;
   public static final int EmptyStatement = 524288;
   public static final int MissingJavadocComments = 1048576;
   public static final int MissingJavadocTags = 2097152;
   public static final int UnqualifiedFieldAccess = 4194304;
   public static final int UnusedDeclaredThrownException = 8388608;
   public static final int FinallyBlockNotCompleting = 16777216;
   public static final int InvalidJavadoc = 33554432;
   public static final int UnnecessaryTypeCheck = 67108864;
   public static final int UndocumentedEmptyBlock = 134217728;
   public static final int IndirectStaticAccess = 268435456;
   public static final int UnnecessaryElse = 536870913;
   public static final int UncheckedTypeOperation = 536870914;
   public static final int FinalParameterBound = 536870916;
   public static final int MissingSerialVersion = 536870920;
   public static final int EnumUsedAsAnIdentifier = 536870928;
   public static final int ForbiddenReference = 536870944;
   public static final int VarargsArgumentNeedCast = 536870976;
   public static final int NullReference = 536871040;
   public static final int AutoBoxing = 536871168;
   public static final int AnnotationSuperInterface = 536871424;
   public static final int TypeHiding = 536871936;
   public static final int MissingOverrideAnnotation = 536872960;
   public static final int MissingEnumConstantCase = 536875008;
   public static final int MissingDeprecatedAnnotation = 536879104;
   public static final int DiscouragedReference = 536887296;
   public static final int UnhandledWarningToken = 536903680;
   public static final int RawTypeReference = 536936448;
   public static final int UnusedLabel = 537001984;
   public static final int ParameterAssignment = 537133056;
   public static final int FallthroughCase = 537395200;
   public static final int OverridingMethodWithoutSuperInvocation = 537919488;
   public static final int PotentialNullReference = 538968064;
   public static final int RedundantNullCheck = 541065216;
   public static final int MissingJavadocTagDescription = 545259520;
   public static final int UnusedTypeArguments = 553648128;
   public static final int UnusedWarningToken = 570425344;
   public static final int RedundantSuperinterface = 603979776;
   public static final int ComparingIdentical = 671088640;
   public static final int MissingSynchronizedModifierInInheritedMethod = 805306368;
   public static final int ShouldImplementHashcode = 1073741825;
   public static final int DeadCode = 1073741826;
   public static final int Tasks = 1073741828;
   public static final int UnusedObjectAllocation = 1073741832;
   public static final int MethodCanBeStatic = 1073741840;
   public static final int MethodCanBePotentiallyStatic = 1073741856;
   public static final int RedundantSpecificationOfTypeArguments = 1073741888;
   public static final int UnclosedCloseable = 1073741952;
   public static final int PotentiallyUnclosedCloseable = 1073742080;
   public static final int ExplicitlyClosedAutoCloseable = 1073742336;
   public static final int NullSpecViolation = 1073742848;
   public static final int NullAnnotationInferenceConflict = 1073743872;
   public static final int NullUncheckedConversion = 1073745920;
   public static final int RedundantNullAnnotation = 1073750016;
   public static final int MissingNonNullByDefaultAnnotation = 1073758208;
   public static final int MissingDefaultCase = 1073774592;
   public static final int UnusedTypeParameter = 1073807360;
   public static final int NonnullParameterAnnotationDropped = 1073872896;
   public static final int UnusedExceptionParameter = 1074003968;
   public static final int PessimisticNullAnalysisForFreeTypeVariables = 1074266112;
   public static final int NonNullTypeVariableFromLegacyInvocation = 1074790400;
   public static final int UnlikelyCollectionMethodArgumentType = 1075838976;
   public static final int UnlikelyEqualsArgumentType = 1077936128;
   public static final int UsingTerminallyDeprecatedAPI = 1082130432;
   public static final int APILeak = 1090519040;
   public static final int UnstableAutoModuleName = 1107296256;
   public static final int PreviewFeatureUsed = 1140850688;
   protected IrritantSet errorThreshold;
   protected IrritantSet warningThreshold;
   protected IrritantSet infoThreshold;
   public int produceDebugAttributes;
   public boolean produceMethodParameters;
   public boolean generateGenericSignatureForLambdaExpressions;
   public long complianceLevel;
   public long originalComplianceLevel;
   public long sourceLevel;
   public long originalSourceLevel;
   public long targetJDK;
   public String defaultEncoding;
   public boolean verbose;
   public boolean produceReferenceInfo;
   public boolean preserveAllLocalVariables;
   public boolean parseLiteralExpressionsAsConstants;
   public int maxProblemsPerUnit;
   public char[][] taskTags;
   public char[][] taskPriorities;
   public boolean isTaskCaseSensitive;
   public boolean reportDeprecationInsideDeprecatedCode;
   public boolean reportDeprecationWhenOverridingDeprecatedMethod;
   public boolean reportUnusedParameterWhenImplementingAbstract;
   public boolean reportUnusedParameterWhenOverridingConcrete;
   public boolean reportUnusedParameterIncludeDocCommentReference;
   public boolean reportUnusedDeclaredThrownExceptionWhenOverriding;
   public boolean reportUnusedDeclaredThrownExceptionIncludeDocCommentReference;
   public boolean reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable;
   public boolean reportSpecialParameterHidingField;
   public boolean reportDeadCodeInTrivialIfStatement;
   public boolean docCommentSupport;
   public boolean reportInvalidJavadocTags;
   public int reportInvalidJavadocTagsVisibility;
   public boolean reportInvalidJavadocTagsDeprecatedRef;
   public boolean reportInvalidJavadocTagsNotVisibleRef;
   public String reportMissingJavadocTagDescription;
   public int reportMissingJavadocTagsVisibility;
   public boolean reportMissingJavadocTagsOverriding;
   public boolean reportMissingJavadocTagsMethodTypeParameters;
   public int reportMissingJavadocCommentsVisibility;
   public boolean reportMissingJavadocCommentsOverriding;
   public boolean inlineJsrBytecode;
   public boolean shareCommonFinallyBlocks;
   public boolean suppressWarnings;
   public boolean suppressOptionalErrors;
   public boolean treatOptionalErrorAsFatal;
   public boolean performMethodsFullRecovery;
   public boolean performStatementsRecovery;
   public boolean processAnnotations;
   public boolean storeAnnotations;
   public boolean reportMissingOverrideAnnotationForInterfaceMethodImplementation;
   public boolean generateClassFiles;
   public boolean ignoreMethodBodies;
   public boolean includeNullInfoFromAsserts;
   public boolean reportUnavoidableGenericTypeProblems;
   public boolean ignoreSourceFolderWarningOption;
   public boolean isAnnotationBasedNullAnalysisEnabled;
   public char[][] nullableAnnotationName;
   public char[][] nonNullAnnotationName;
   public char[][] nonNullByDefaultAnnotationName;
   public String[] nullableAnnotationSecondaryNames;
   public String[] nonNullAnnotationSecondaryNames;
   public String[] nonNullByDefaultAnnotationSecondaryNames;
   public long intendedDefaultNonNullness;
   public boolean analyseResourceLeaks;
   public boolean reportMissingEnumCaseDespiteDefault;
   public boolean reportUnlikelyCollectionMethodArgumentTypeStrict;
   public static boolean tolerateIllegalAmbiguousVarargsInvocation;
   public boolean inheritNullAnnotations;
   public boolean enableSyntacticNullAnalysisForFields;
   public boolean pessimisticNullAnalysisForFreeTypeVariablesEnabled;
   public boolean complainOnUninternedIdentityComparison;
   public boolean emulateJavacBug8031744;
   public Boolean useNullTypeAnnotations;
   public boolean enablePreviewFeatures;
   public boolean enableJdtDebugCompileMode;
   public static final String[] warningTokens = new String[]{"all", "boxing", "cast", "dep-ann", "deprecation", "exports", "fallthrough", "finally", "hiding", "incomplete-switch", "javadoc", "module", "nls", "null", "rawtypes", "removal", "resource", "restriction", "serial", "static-access", "static-method", "super", "synthetic-access", "sync-override", "unchecked", "unlikely-arg-type", "unqualified-field-access", "unused", "preview"};

   public CompilerOptions() {
      this((Map)null);
   }

   public CompilerOptions(Map settings) {
      this.nullableAnnotationSecondaryNames = NO_STRINGS;
      this.nonNullAnnotationSecondaryNames = NO_STRINGS;
      this.nonNullByDefaultAnnotationSecondaryNames = NO_STRINGS;
      String tolerateIllegalAmbiguousVarargs = System.getProperty("tolerateIllegalAmbiguousVarargsInvocation");
      tolerateIllegalAmbiguousVarargsInvocation = tolerateIllegalAmbiguousVarargs != null && tolerateIllegalAmbiguousVarargs.equalsIgnoreCase("true");
      this.emulateJavacBug8031744 = true;
      this.useNullTypeAnnotations = null;
      this.resetDefaults();
      if (settings != null) {
         this.set(settings);
      }

   }

   /** @deprecated */
   public CompilerOptions(Map settings, boolean parseLiteralExpressionsAsConstants) {
      this(settings);
      this.parseLiteralExpressionsAsConstants = parseLiteralExpressionsAsConstants;
   }

   public static String getLatestVersion() {
      return "12";
   }

   public static String optionKeyFromIrritant(int irritant) {
      switch (irritant) {
         case 1:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.methodWithConstructorName";
         case 2:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.overridingPackageDefaultMethod";
         case 4:
         case 33554436:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.deprecation";
         case 8:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.hiddenCatchBlock";
         case 16:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedLocal";
         case 32:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter";
         case 64:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.noImplicitStringConversion";
         case 128:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.syntheticAccessEmulation";
         case 256:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.nonExternalizedStringLiteral";
         case 512:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.assertIdentifier";
         case 1024:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedImport";
         case 2048:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.staticAccessReceiver";
         case 4096:
            return "com.bea.core.repackaged.jdt.core.compiler.taskTags";
         case 8192:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.noEffectAssignment";
         case 16384:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.incompatibleNonInheritedInterfaceMethod";
         case 32768:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedPrivateMember";
         case 65536:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.localVariableHiding";
         case 131072:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.fieldHiding";
         case 262144:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.possibleAccidentalBooleanAssignment";
         case 524288:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.emptyStatement";
         case 1048576:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments";
         case 2097152:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags";
         case 4194304:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unqualifiedFieldAccess";
         case 8388608:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownException";
         case 16777216:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.finallyBlockNotCompletingNormally";
         case 33554432:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc";
         case 67108864:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryTypeCheck";
         case 134217728:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.undocumentedEmptyBlock";
         case 268435456:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.indirectStaticAccess";
         case 536870913:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryElse";
         case 536870914:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.uncheckedTypeOperation";
         case 536870916:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.finalParameterBound";
         case 536870920:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingSerialVersion";
         case 536870928:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.enumIdentifier";
         case 536870944:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference";
         case 536870976:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.varargsArgumentNeedCast";
         case 536871040:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.nullReference";
         case 536871168:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.autoboxing";
         case 536871424:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.annotationSuperInterface";
         case 536871936:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.typeParameterHiding";
         case 536872960:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotation";
         case 536875008:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch";
         case 536879104:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingDeprecatedAnnotation";
         case 536887296:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.discouragedReference";
         case 536903680:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unhandledWarningToken";
         case 536936448:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.rawTypeReference";
         case 537001984:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedLabel";
         case 537133056:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.parameterAssignment";
         case 537395200:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.fallthroughCase";
         case 537919488:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.overridingMethodWithoutSuperInvocation";
         case 538968064:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.potentialNullReference";
         case 541065216:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullCheck";
         case 545259520:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagDescription";
         case 553648128:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation";
         case 570425344:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedWarningToken";
         case 603979776:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.redundantSuperinterface";
         case 671088640:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.comparingIdentical";
         case 805306368:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod";
         case 1073741825:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingHashCodeMethod";
         case 1073741826:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.deadCode";
         case 1073741832:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedObjectAllocation";
         case 1073741840:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBeStatic";
         case 1073741856:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBePotentiallyStatic";
         case 1073741888:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments";
         case 1073741952:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unclosedCloseable";
         case 1073742080:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.potentiallyUnclosedCloseable";
         case 1073742336:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.explicitlyClosedAutoCloseable";
         case 1073742848:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.nullSpecViolation";
         case 1073743872:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.nullAnnotationInferenceConflict";
         case 1073745920:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.nullUncheckedConversion";
         case 1073750016:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullAnnotation";
         case 1073758208:
            return "com.bea.core.repackaged.jdt.core.compiler.annotation.missingNonNullByDefaultAnnotation";
         case 1073774592:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.missingDefaultCase";
         case 1073807360:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeParameter";
         case 1073872896:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.nonnullParameterAnnotationDropped";
         case 1074003968:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unusedExceptionParameter";
         case 1074266112:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.pessimisticNullAnalysisForFreeTypeVariables";
         case 1074790400:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.nonnullTypeVariableFromLegacyInvocation";
         case 1075838976:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentType";
         case 1077936128:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyEqualsArgumentType";
         case 1082130432:
         case 1115684864:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.terminalDeprecation";
         case 1090519040:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.APILeak";
         case 1107296256:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.unstableAutoModuleName";
         case 1140850688:
            return "com.bea.core.repackaged.jdt.core.compiler.problem.reportPreviewFeatures";
         default:
            return null;
      }
   }

   public static String versionFromJdkLevel(long jdkLevel) {
      int major = (int)(jdkLevel >> 16);
      switch (major) {
         case 45:
            if (jdkLevel == 2949123L) {
               return "1.1";
            }
            break;
         case 46:
            if (jdkLevel == 3014656L) {
               return "1.2";
            }
            break;
         case 47:
            if (jdkLevel == 3080192L) {
               return "1.3";
            }
            break;
         case 48:
            if (jdkLevel == 3145728L) {
               return "1.4";
            }
            break;
         case 49:
            if (jdkLevel == 3211264L) {
               return "1.5";
            }
            break;
         case 50:
            if (jdkLevel == 3276800L) {
               return "1.6";
            }
            break;
         case 51:
            if (jdkLevel == 3342336L) {
               return "1.7";
            }
            break;
         case 52:
            if (jdkLevel == 3407872L) {
               return "1.8";
            }
            break;
         case 53:
            if (jdkLevel == 3473408L) {
               return "9";
            }
            break;
         case 54:
            if (jdkLevel == 3538944L) {
               return "10";
            }
            break;
         default:
            if (major > 54) {
               return "" + (major - 44);
            }

            return Util.EMPTY_STRING;
      }

      return Util.EMPTY_STRING;
   }

   public static long releaseToJDKLevel(String release) {
      if (release != null && release.length() > 0) {
         int major = Integer.parseInt(release) + 44;
         if (major <= 56) {
            long jdkLevel = ((long)major << 16) + 0L;
            return jdkLevel;
         }
      }

      return 0L;
   }

   public static long versionToJdkLevel(String versionID) {
      return versionToJdkLevel(versionID, true);
   }

   public static long versionToJdkLevel(String versionID, boolean supportUnreleased) {
      String version = versionID;
      if (versionID != null && versionID.length() > 0) {
         if (versionID.length() >= 3 && versionID.charAt(0) == '1' && versionID.charAt(1) == '.') {
            switch (versionID.charAt(2)) {
               case '1':
                  return 2949123L;
               case '2':
                  return 3014656L;
               case '3':
                  return 3080192L;
               case '4':
                  return 3145728L;
               case '5':
                  return 3211264L;
               case '6':
                  return 3276800L;
               case '7':
                  return 3342336L;
               case '8':
                  return 3407872L;
               default:
                  return 0L;
            }
         }

         try {
            int index = version.indexOf(46);
            if (index != -1) {
               version = version.substring(0, index);
            } else {
               index = version.indexOf(45);
               if (index != -1) {
                  version = version.substring(0, index);
               }
            }

            int major = Integer.parseInt(version) + 44;
            if (major > 56) {
               if (!supportUnreleased) {
                  return 0L;
               }

               major = 56;
            }

            return ((long)major << 16) + 0L;
         } catch (NumberFormatException var5) {
         }
      }

      if ("jsr14".equals(versionID)) {
         return 3145728L;
      } else {
         return "cldc1.1".equals(versionID) ? 2949124L : 0L;
      }
   }

   public static String[] warningOptionNames() {
      String[] result = new String[]{"com.bea.core.repackaged.jdt.core.compiler.problem.annotationSuperInterface", "com.bea.core.repackaged.jdt.core.compiler.problem.assertIdentifier", "com.bea.core.repackaged.jdt.core.compiler.problem.autoboxing", "com.bea.core.repackaged.jdt.core.compiler.problem.comparingIdentical", "com.bea.core.repackaged.jdt.core.compiler.problem.deadCode", "com.bea.core.repackaged.jdt.core.compiler.problem.deadCodeInTrivialIfStatement", "com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", "com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", "com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", "com.bea.core.repackaged.jdt.core.compiler.problem.discouragedReference", "com.bea.core.repackaged.jdt.core.compiler.problem.emptyStatement", "com.bea.core.repackaged.jdt.core.compiler.problem.enumIdentifier", "com.bea.core.repackaged.jdt.core.compiler.problem.fallthroughCase", "com.bea.core.repackaged.jdt.core.compiler.problem.fieldHiding", "com.bea.core.repackaged.jdt.core.compiler.problem.finallyBlockNotCompletingNormally", "com.bea.core.repackaged.jdt.core.compiler.problem.finalParameterBound", "com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference", "com.bea.core.repackaged.jdt.core.compiler.problem.hiddenCatchBlock", "com.bea.core.repackaged.jdt.core.compiler.problem.incompatibleNonInheritedInterfaceMethod", "com.bea.core.repackaged.jdt.core.compiler.problem.missingDefaultCase", "com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch", "com.bea.core.repackaged.jdt.core.compiler.problem.missingEnumCaseDespiteDefault", "com.bea.core.repackaged.jdt.core.compiler.problem.indirectStaticAccess", "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc", "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTags", "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef", "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef", "com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsVisibility", "com.bea.core.repackaged.jdt.core.compiler.problem.localVariableHiding", "com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBePotentiallyStatic", "com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBeStatic", "com.bea.core.repackaged.jdt.core.compiler.problem.methodWithConstructorName", "com.bea.core.repackaged.jdt.core.compiler.problem.missingDeprecatedAnnotation", "com.bea.core.repackaged.jdt.core.compiler.problem.missingHashCodeMethod", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsOverriding", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsVisibility", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagDescription", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsMethodTypeParameters", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsOverriding", "com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsVisibility", "com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotation", "com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotationForInterfaceMethodImplementation", "com.bea.core.repackaged.jdt.core.compiler.problem.missingSerialVersion", "com.bea.core.repackaged.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod", "com.bea.core.repackaged.jdt.core.compiler.problem.noEffectAssignment", "com.bea.core.repackaged.jdt.core.compiler.problem.noImplicitStringConversion", "com.bea.core.repackaged.jdt.core.compiler.problem.nonExternalizedStringLiteral", "com.bea.core.repackaged.jdt.core.compiler.problem.staticAccessReceiver", "com.bea.core.repackaged.jdt.core.compiler.problem.nullReference", "com.bea.core.repackaged.jdt.core.compiler.problem.overridingMethodWithoutSuperInvocation", "com.bea.core.repackaged.jdt.core.compiler.problem.overridingPackageDefaultMethod", "com.bea.core.repackaged.jdt.core.compiler.problem.parameterAssignment", "com.bea.core.repackaged.jdt.core.compiler.problem.possibleAccidentalBooleanAssignment", "com.bea.core.repackaged.jdt.core.compiler.problem.potentialNullReference", "com.bea.core.repackaged.jdt.core.compiler.problem.rawTypeReference", "com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullCheck", "com.bea.core.repackaged.jdt.core.compiler.problem.redundantSuperinterface", "com.bea.core.repackaged.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments", "com.bea.core.repackaged.jdt.core.compiler.problem.specialParameterHidingField", "com.bea.core.repackaged.jdt.core.compiler.problem.syntheticAccessEmulation", "com.bea.core.repackaged.jdt.core.compiler.problem.tasks", "com.bea.core.repackaged.jdt.core.compiler.problem.typeParameterHiding", "com.bea.core.repackaged.jdt.core.compiler.problem.unavoidableGenericTypeProblems", "com.bea.core.repackaged.jdt.core.compiler.problem.uncheckedTypeOperation", "com.bea.core.repackaged.jdt.core.compiler.problem.undocumentedEmptyBlock", "com.bea.core.repackaged.jdt.core.compiler.problem.unhandledWarningToken", "com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryElse", "com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryTypeCheck", "com.bea.core.repackaged.jdt.core.compiler.problem.unqualifiedFieldAccess", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownException", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionExemptExceptionAndThrowable", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionIncludeDocCommentReference", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionWhenOverriding", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedImport", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedLabel", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedLocal", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedObjectAllocation", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedExceptionParameter", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterIncludeDocCommentReference", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenImplementingAbstract", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenOverridingConcrete", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedPrivateMember", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedWarningToken", "com.bea.core.repackaged.jdt.core.compiler.problem.varargsArgumentNeedCast", "com.bea.core.repackaged.jdt.core.compiler.problem.unclosedCloseable", "com.bea.core.repackaged.jdt.core.compiler.problem.potentiallyUnclosedCloseable", "com.bea.core.repackaged.jdt.core.compiler.problem.explicitlyClosedAutoCloseable", "com.bea.core.repackaged.jdt.core.compiler.annotation.nullanalysis", "com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull", "com.bea.core.repackaged.jdt.core.compiler.annotation.nullable", "com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault", "com.bea.core.repackaged.jdt.core.compiler.annotation.missingNonNullByDefaultAnnotation", "com.bea.core.repackaged.jdt.core.compiler.problem.nullSpecViolation", "com.bea.core.repackaged.jdt.core.compiler.problem.nullAnnotationInferenceConflict", "com.bea.core.repackaged.jdt.core.compiler.problem.nullUncheckedConversion", "com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullAnnotation", "com.bea.core.repackaged.jdt.core.compiler.problem.syntacticNullAnalysisForFields", "com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeParameter", "com.bea.core.repackaged.jdt.core.compiler.annotation.inheritNullAnnotations", "com.bea.core.repackaged.jdt.core.compiler.problem.nonnullParameterAnnotationDropped", "com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentType", "com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyEqualsArgumentType", "com.bea.core.repackaged.jdt.core.compiler.problem.APILeak", "com.bea.core.repackaged.jdt.core.compiler.problem.reportPreviewFeatures"};
      return result;
   }

   public static String warningTokenFromIrritant(int irritant) {
      switch (irritant) {
         case 4:
         case 33554436:
            return "deprecation";
         case 8:
         case 65536:
         case 131072:
            return "hiding";
         case 16:
         case 32:
         case 1024:
         case 32768:
         case 8388608:
         case 537001984:
         case 553648128:
         case 603979776:
         case 1073741826:
         case 1073741832:
         case 1073741888:
         case 1073807360:
         case 1074003968:
            return "unused";
         case 128:
            return "synthetic-access";
         case 256:
            return "nls";
         case 2048:
         case 268435456:
            return "static-access";
         case 1048576:
         case 2097152:
         case 33554432:
            return "javadoc";
         case 4194304:
            return "unqualified-field-access";
         case 16777216:
            return "finally";
         case 67108864:
            return "cast";
         case 536870914:
            return "unchecked";
         case 536870920:
            return "serial";
         case 536870944:
         case 536887296:
            return "restriction";
         case 536871040:
         case 538968064:
         case 541065216:
         case 1073742848:
         case 1073743872:
         case 1073745920:
         case 1073750016:
         case 1073758208:
         case 1073872896:
         case 1074266112:
         case 1074790400:
            return "null";
         case 536871168:
            return "boxing";
         case 536871936:
            return "hiding";
         case 536875008:
         case 1073774592:
            return "incomplete-switch";
         case 536879104:
            return "dep-ann";
         case 536936448:
            return "rawtypes";
         case 537395200:
            return "fallthrough";
         case 537919488:
            return "super";
         case 805306368:
            return "sync-override";
         case 1073741840:
         case 1073741856:
            return "static-method";
         case 1073741952:
         case 1073742080:
         case 1073742336:
            return "resource";
         case 1075838976:
         case 1077936128:
            return "unlikely-arg-type";
         case 1082130432:
         case 1115684864:
            return "removal";
         case 1090519040:
            return "exports";
         case 1107296256:
            return "module";
         case 1140850688:
            return "preview";
         default:
            return null;
      }
   }

   public static IrritantSet warningTokenToIrritants(String warningToken) {
      if (warningToken != null && warningToken.length() != 0) {
         switch (warningToken.charAt(0)) {
            case 'a':
               if ("all".equals(warningToken)) {
                  return IrritantSet.ALL;
               }
               break;
            case 'b':
               if ("boxing".equals(warningToken)) {
                  return IrritantSet.BOXING;
               }
               break;
            case 'c':
               if ("cast".equals(warningToken)) {
                  return IrritantSet.CAST;
               }
               break;
            case 'd':
               if ("deprecation".equals(warningToken)) {
                  return IrritantSet.DEPRECATION;
               }

               if ("dep-ann".equals(warningToken)) {
                  return IrritantSet.DEP_ANN;
               }
               break;
            case 'e':
               if ("exports".equals(warningToken)) {
                  return IrritantSet.API_LEAK;
               }
               break;
            case 'f':
               if ("fallthrough".equals(warningToken)) {
                  return IrritantSet.FALLTHROUGH;
               }

               if ("finally".equals(warningToken)) {
                  return IrritantSet.FINALLY;
               }
            case 'g':
            case 'k':
            case 'l':
            case 'o':
            case 'q':
            case 't':
            default:
               break;
            case 'h':
               if ("hiding".equals(warningToken)) {
                  return IrritantSet.HIDING;
               }
               break;
            case 'i':
               if ("incomplete-switch".equals(warningToken)) {
                  return IrritantSet.INCOMPLETE_SWITCH;
               }
               break;
            case 'j':
               if ("javadoc".equals(warningToken)) {
                  return IrritantSet.JAVADOC;
               }
               break;
            case 'm':
               if ("module".equals(warningToken)) {
                  return IrritantSet.MODULE;
               }
               break;
            case 'n':
               if ("nls".equals(warningToken)) {
                  return IrritantSet.NLS;
               }

               if ("null".equals(warningToken)) {
                  return IrritantSet.NULL;
               }
               break;
            case 'p':
               if ("preview".equals(warningToken)) {
                  return IrritantSet.PREVIEW;
               }
               break;
            case 'r':
               if ("rawtypes".equals(warningToken)) {
                  return IrritantSet.RAW;
               }

               if ("resource".equals(warningToken)) {
                  return IrritantSet.RESOURCE;
               }

               if ("restriction".equals(warningToken)) {
                  return IrritantSet.RESTRICTION;
               }

               if ("removal".equals(warningToken)) {
                  return IrritantSet.TERMINAL_DEPRECATION;
               }
               break;
            case 's':
               if ("serial".equals(warningToken)) {
                  return IrritantSet.SERIAL;
               }

               if ("static-access".equals(warningToken)) {
                  return IrritantSet.STATIC_ACCESS;
               }

               if ("static-method".equals(warningToken)) {
                  return IrritantSet.STATIC_METHOD;
               }

               if ("synthetic-access".equals(warningToken)) {
                  return IrritantSet.SYNTHETIC_ACCESS;
               }

               if ("super".equals(warningToken)) {
                  return IrritantSet.SUPER;
               }

               if ("sync-override".equals(warningToken)) {
                  return IrritantSet.SYNCHRONIZED;
               }
               break;
            case 'u':
               if ("unused".equals(warningToken)) {
                  return IrritantSet.UNUSED;
               }

               if ("unchecked".equals(warningToken)) {
                  return IrritantSet.UNCHECKED;
               }

               if ("unqualified-field-access".equals(warningToken)) {
                  return IrritantSet.UNQUALIFIED_FIELD_ACCESS;
               }

               if ("unlikely-arg-type".equals(warningToken)) {
                  return IrritantSet.UNLIKELY_ARGUMENT_TYPE;
               }
         }

         return null;
      } else {
         return null;
      }
   }

   public Map getMap() {
      Map optionsMap = new HashMap(30);
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable", (this.produceDebugAttributes & 4) != 0 ? "generate" : "do not generate");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber", (this.produceDebugAttributes & 2) != 0 ? "generate" : "do not generate");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile", (this.produceDebugAttributes & 1) != 0 ? "generate" : "do not generate");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.codegen.methodParameters", this.produceMethodParameters ? "generate" : "do not generate");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.codegen.lambda.genericSignature", this.generateGenericSignatureForLambdaExpressions ? "generate" : "do not generate");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.codegen.unusedLocal", this.preserveAllLocalVariables ? "preserve" : "optimize out");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support", this.docCommentSupport ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.methodWithConstructorName", this.getSeverityString(1));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.overridingPackageDefaultMethod", this.getSeverityString(2));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation", this.getSeverityString(4));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.terminalDeprecation", this.getSeverityString(1082130432));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode", this.reportDeprecationInsideDeprecatedCode ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod", this.reportDeprecationWhenOverridingDeprecatedMethod ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.hiddenCatchBlock", this.getSeverityString(8));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLocal", this.getSeverityString(16));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter", this.getSeverityString(32));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedExceptionParameter", this.getSeverityString(1074003968));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedImport", this.getSeverityString(1024));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.syntheticAccessEmulation", this.getSeverityString(128));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.noEffectAssignment", this.getSeverityString(8192));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.nonExternalizedStringLiteral", this.getSeverityString(256));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.noImplicitStringConversion", this.getSeverityString(64));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.staticAccessReceiver", this.getSeverityString(2048));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.indirectStaticAccess", this.getSeverityString(268435456));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.incompatibleNonInheritedInterfaceMethod", this.getSeverityString(16384));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedPrivateMember", this.getSeverityString(32768));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.localVariableHiding", this.getSeverityString(65536));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.fieldHiding", this.getSeverityString(131072));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.typeParameterHiding", this.getSeverityString(536871936));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.possibleAccidentalBooleanAssignment", this.getSeverityString(262144));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.emptyStatement", this.getSeverityString(524288));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.assertIdentifier", this.getSeverityString(512));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.enumIdentifier", this.getSeverityString(536870928));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.undocumentedEmptyBlock", this.getSeverityString(134217728));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryTypeCheck", this.getSeverityString(67108864));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryElse", this.getSeverityString(536870913));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.autoboxing", this.getSeverityString(536871168));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.annotationSuperInterface", this.getSeverityString(536871424));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch", this.getSeverityString(536875008));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingEnumCaseDespiteDefault", this.reportMissingEnumCaseDespiteDefault ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingDefaultCase", this.getSeverityString(1073774592));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc", this.getSeverityString(33554432));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsVisibility", this.getVisibilityString(this.reportInvalidJavadocTagsVisibility));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTags", this.reportInvalidJavadocTags ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef", this.reportInvalidJavadocTagsDeprecatedRef ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef", this.reportInvalidJavadocTagsNotVisibleRef ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags", this.getSeverityString(2097152));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsVisibility", this.getVisibilityString(this.reportMissingJavadocTagsVisibility));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsOverriding", this.reportMissingJavadocTagsOverriding ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsMethodTypeParameters", this.reportMissingJavadocTagsMethodTypeParameters ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments", this.getSeverityString(1048576));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagDescription", this.reportMissingJavadocTagDescription);
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsVisibility", this.getVisibilityString(this.reportMissingJavadocCommentsVisibility));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsOverriding", this.reportMissingJavadocCommentsOverriding ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.finallyBlockNotCompletingNormally", this.getSeverityString(16777216));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownException", this.getSeverityString(8388608));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionWhenOverriding", this.reportUnusedDeclaredThrownExceptionWhenOverriding ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionIncludeDocCommentReference", this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionExemptExceptionAndThrowable", this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unqualifiedFieldAccess", this.getSeverityString(4194304));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unavoidableGenericTypeProblems", this.reportUnavoidableGenericTypeProblems ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.uncheckedTypeOperation", this.getSeverityString(536870914));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.rawTypeReference", this.getSeverityString(536936448));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.finalParameterBound", this.getSeverityString(536870916));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingSerialVersion", this.getSeverityString(536870920));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference", this.getSeverityString(536870944));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.discouragedReference", this.getSeverityString(536887296));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.varargsArgumentNeedCast", this.getSeverityString(536870976));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotation", this.getSeverityString(536872960));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotationForInterfaceMethodImplementation", this.reportMissingOverrideAnnotationForInterfaceMethodImplementation ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingDeprecatedAnnotation", this.getSeverityString(536879104));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLabel", this.getSeverityString(537001984));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation", this.getSeverityString(553648128));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.compliance", versionFromJdkLevel(this.complianceLevel));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.release", "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.source", versionFromJdkLevel(this.sourceLevel));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform", versionFromJdkLevel(this.targetJDK));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.fatalOptionalError", this.treatOptionalErrorAsFatal ? "enabled" : "disabled");
      if (this.defaultEncoding != null) {
         optionsMap.put("com.bea.core.repackaged.jdt.core.encoding", this.defaultEncoding);
      }

      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.taskTags", this.taskTags == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskTags, ',')));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.taskPriorities", this.taskPriorities == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskPriorities, ',')));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.taskCaseSensitive", this.isTaskCaseSensitive ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenImplementingAbstract", this.reportUnusedParameterWhenImplementingAbstract ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenOverridingConcrete", this.reportUnusedParameterWhenOverridingConcrete ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterIncludeDocCommentReference", this.reportUnusedParameterIncludeDocCommentReference ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.specialParameterHidingField", this.reportSpecialParameterHidingField ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.maxProblemPerUnit", String.valueOf(this.maxProblemsPerUnit));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.codegen.inlineJsrBytecode", this.inlineJsrBytecode ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.codegen.shareCommonFinallyBlocks", this.shareCommonFinallyBlocks ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.nullReference", this.getSeverityString(536871040));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.potentialNullReference", this.getSeverityString(538968064));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullCheck", this.getSeverityString(541065216));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.suppressWarnings", this.suppressWarnings ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.suppressOptionalErrors", this.suppressOptionalErrors ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unhandledWarningToken", this.getSeverityString(536903680));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedWarningToken", this.getSeverityString(570425344));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.parameterAssignment", this.getSeverityString(537133056));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.fallthroughCase", this.getSeverityString(537395200));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.overridingMethodWithoutSuperInvocation", this.getSeverityString(537919488));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.generateClassFiles", this.generateClassFiles ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.processAnnotations", this.processAnnotations ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.storeAnnotations", this.storeAnnotations ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.emulateJavacBug8031744", this.emulateJavacBug8031744 ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSuperinterface", this.getSeverityString(603979776));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.comparingIdentical", this.getSeverityString(671088640));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod", this.getSeverityString(805306368));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.missingHashCodeMethod", this.getSeverityString(1073741825));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.deadCode", this.getSeverityString(1073741826));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.deadCodeInTrivialIfStatement", this.reportDeadCodeInTrivialIfStatement ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.tasks", this.getSeverityString(1073741828));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedObjectAllocation", this.getSeverityString(1073741832));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.includeNullInfoFromAsserts", this.includeNullInfoFromAsserts ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBeStatic", this.getSeverityString(1073741840));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBePotentiallyStatic", this.getSeverityString(1073741856));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments", this.getSeverityString(1073741888));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unclosedCloseable", this.getSeverityString(1073741952));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.potentiallyUnclosedCloseable", this.getSeverityString(1073742080));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.explicitlyClosedAutoCloseable", this.getSeverityString(1073742336));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nullanalysis", this.isAnnotationBasedNullAnalysisEnabled ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.nullSpecViolation", this.getSeverityString(1073742848));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.nullAnnotationInferenceConflict", this.getSeverityString(1073743872));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.nullUncheckedConversion", this.getSeverityString(1073745920));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullAnnotation", this.getSeverityString(1073750016));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nullable", String.valueOf(CharOperation.concatWith(this.nullableAnnotationName, '.')));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull", String.valueOf(CharOperation.concatWith(this.nonNullAnnotationName, '.')));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault", String.valueOf(CharOperation.concatWith(this.nonNullByDefaultAnnotationName, '.')));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nullable.secondary", this.nameListToString(this.nullableAnnotationSecondaryNames));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull.secondary", this.nameListToString(this.nonNullAnnotationSecondaryNames));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault.secondary", this.nameListToString(this.nonNullByDefaultAnnotationSecondaryNames));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.missingNonNullByDefaultAnnotation", this.getSeverityString(1073758208));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeParameter", this.getSeverityString(1073807360));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.syntacticNullAnalysisForFields", this.enableSyntacticNullAnalysisForFields ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.annotation.inheritNullAnnotations", this.inheritNullAnnotations ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.nonnullParameterAnnotationDropped", this.getSeverityString(1073872896));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.uninternedIdentityComparison", this.complainOnUninternedIdentityComparison ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.pessimisticNullAnalysisForFreeTypeVariables", this.getSeverityString(1074266112));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.nonnullTypeVariableFromLegacyInvocation", this.getSeverityString(1074790400));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentType", this.getSeverityString(1075838976));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentTypeStrict", this.reportUnlikelyCollectionMethodArgumentTypeStrict ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyEqualsArgumentType", this.getSeverityString(1077936128));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.APILeak", this.getSeverityString(1090519040));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.unstableAutoModuleName", this.getSeverityString(1107296256));
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.enablePreviewFeatures", this.enablePreviewFeatures ? "enabled" : "disabled");
      optionsMap.put("com.bea.core.repackaged.jdt.core.compiler.problem.reportPreviewFeatures", this.getSeverityString(1140850688));
      return optionsMap;
   }

   public int getSeverity(int irritant) {
      if (this.errorThreshold.isSet(irritant)) {
         if ((irritant & -503316480) == 570425344) {
            return 33;
         } else {
            return this.treatOptionalErrorAsFatal ? 161 : 33;
         }
      } else if (this.warningThreshold.isSet(irritant)) {
         return 32;
      } else {
         return this.infoThreshold.isSet(irritant) ? 1056 : 256;
      }
   }

   public String getSeverityString(int irritant) {
      if (this.errorThreshold.isSet(irritant)) {
         return "error";
      } else if (this.warningThreshold.isSet(irritant)) {
         return "warning";
      } else {
         return this.infoThreshold.isSet(irritant) ? "info" : "ignore";
      }
   }

   public String getVisibilityString(int level) {
      switch (level & 7) {
         case 1:
            return "public";
         case 2:
            return "private";
         case 3:
         default:
            return "default";
         case 4:
            return "protected";
      }
   }

   public boolean isAnyEnabled(IrritantSet irritants) {
      return this.warningThreshold.isAnySet(irritants) || this.errorThreshold.isAnySet(irritants) || this.infoThreshold.isAnySet(irritants);
   }

   public int getIgnoredIrritant(IrritantSet irritants) {
      int[] bits = irritants.getBits();

      for(int i = 0; i < 3; ++i) {
         int bit = bits[i];

         for(int b = 0; b < 29; ++b) {
            int single = bit & 1 << b;
            if (single > 0) {
               single |= i << 29;
               if (single != 1073758208 && !this.warningThreshold.isSet(single) && !this.errorThreshold.isSet(single) && !this.infoThreshold.isSet(single)) {
                  return single;
               }
            }
         }
      }

      return 0;
   }

   protected void resetDefaults() {
      this.errorThreshold = new IrritantSet(IrritantSet.COMPILER_DEFAULT_ERRORS);
      this.warningThreshold = new IrritantSet(IrritantSet.COMPILER_DEFAULT_WARNINGS);
      this.infoThreshold = new IrritantSet(IrritantSet.COMPILER_DEFAULT_INFOS);
      this.produceDebugAttributes = 3;
      this.complianceLevel = this.originalComplianceLevel = 3145728L;
      this.sourceLevel = this.originalSourceLevel = 3080192L;
      this.targetJDK = 3014656L;
      this.defaultEncoding = null;
      this.verbose = Compiler.DEBUG;
      this.produceReferenceInfo = false;
      this.preserveAllLocalVariables = false;
      this.produceMethodParameters = false;
      this.parseLiteralExpressionsAsConstants = true;
      this.maxProblemsPerUnit = 100;
      this.taskTags = null;
      this.taskPriorities = null;
      this.isTaskCaseSensitive = true;
      this.reportDeprecationInsideDeprecatedCode = false;
      this.reportDeprecationWhenOverridingDeprecatedMethod = false;
      this.reportUnusedParameterWhenImplementingAbstract = false;
      this.reportUnusedParameterWhenOverridingConcrete = false;
      this.reportUnusedParameterIncludeDocCommentReference = true;
      this.reportUnusedDeclaredThrownExceptionWhenOverriding = false;
      this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference = true;
      this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = true;
      this.reportSpecialParameterHidingField = false;
      this.reportUnavoidableGenericTypeProblems = true;
      this.reportInvalidJavadocTagsVisibility = 1;
      this.reportInvalidJavadocTags = false;
      this.reportInvalidJavadocTagsDeprecatedRef = false;
      this.reportInvalidJavadocTagsNotVisibleRef = false;
      this.reportMissingJavadocTagDescription = "return_tag";
      this.reportMissingJavadocTagsVisibility = 1;
      this.reportMissingJavadocTagsOverriding = false;
      this.reportMissingJavadocTagsMethodTypeParameters = false;
      this.reportMissingJavadocCommentsVisibility = 1;
      this.reportMissingJavadocCommentsOverriding = false;
      this.inlineJsrBytecode = false;
      this.shareCommonFinallyBlocks = false;
      this.docCommentSupport = false;
      this.suppressWarnings = true;
      this.suppressOptionalErrors = false;
      this.treatOptionalErrorAsFatal = false;
      this.performMethodsFullRecovery = true;
      this.performStatementsRecovery = true;
      this.storeAnnotations = false;
      this.generateClassFiles = true;
      this.processAnnotations = false;
      this.reportMissingOverrideAnnotationForInterfaceMethodImplementation = true;
      this.reportDeadCodeInTrivialIfStatement = false;
      this.ignoreMethodBodies = false;
      this.ignoreSourceFolderWarningOption = false;
      this.includeNullInfoFromAsserts = false;
      this.isAnnotationBasedNullAnalysisEnabled = false;
      this.nullableAnnotationName = DEFAULT_NULLABLE_ANNOTATION_NAME;
      this.nonNullAnnotationName = DEFAULT_NONNULL_ANNOTATION_NAME;
      this.nonNullByDefaultAnnotationName = DEFAULT_NONNULLBYDEFAULT_ANNOTATION_NAME;
      this.intendedDefaultNonNullness = 0L;
      this.enableSyntacticNullAnalysisForFields = false;
      this.inheritNullAnnotations = false;
      this.analyseResourceLeaks = true;
      this.reportMissingEnumCaseDespiteDefault = false;
      this.complainOnUninternedIdentityComparison = false;
      this.enablePreviewFeatures = false;
      this.enableJdtDebugCompileMode = false;
   }

   public void set(Map optionsMap) {
      String optionValue;
      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.debug.localVariable")) != null) {
         if ("generate".equals(optionValue)) {
            this.produceDebugAttributes |= 4;
         } else if ("do not generate".equals(optionValue)) {
            this.produceDebugAttributes &= -5;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.debug.lineNumber")) != null) {
         if ("generate".equals(optionValue)) {
            this.produceDebugAttributes |= 2;
         } else if ("do not generate".equals(optionValue)) {
            this.produceDebugAttributes &= -3;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.debug.sourceFile")) != null) {
         if ("generate".equals(optionValue)) {
            this.produceDebugAttributes |= 1;
         } else if ("do not generate".equals(optionValue)) {
            this.produceDebugAttributes &= -2;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.codegen.unusedLocal")) != null) {
         if ("preserve".equals(optionValue)) {
            this.preserveAllLocalVariables = true;
         } else if ("optimize out".equals(optionValue)) {
            this.preserveAllLocalVariables = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationInDeprecatedCode")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportDeprecationInsideDeprecatedCode = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportDeprecationInsideDeprecatedCode = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.deprecationWhenOverridingDeprecatedMethod")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportDeprecationWhenOverridingDeprecatedMethod = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportDeprecationWhenOverridingDeprecatedMethod = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionWhenOverriding")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportUnusedDeclaredThrownExceptionWhenOverriding = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportUnusedDeclaredThrownExceptionWhenOverriding = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionIncludeDocCommentReference")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownExceptionExemptExceptionAndThrowable")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable = false;
         }
      }

      long level;
      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.compliance")) != null) {
         level = versionToJdkLevel(optionValue);
         if (level != 0L) {
            this.complianceLevel = this.originalComplianceLevel = level;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.source")) != null) {
         level = versionToJdkLevel(optionValue);
         if (level != 0L) {
            this.sourceLevel = this.originalSourceLevel = level;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.codegen.targetPlatform")) != null) {
         level = versionToJdkLevel(optionValue);
         if (level != 0L) {
            if (this.enablePreviewFeatures) {
               level |= 65535L;
            }

            this.targetJDK = level;
         }

         if (this.targetJDK >= 3211264L) {
            this.inlineJsrBytecode = true;
         }
      }

      String stringValue;
      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.encoding")) != null) {
         this.defaultEncoding = null;
         stringValue = optionValue;
         if (optionValue.length() > 0) {
            try {
               new InputStreamReader(new ByteArrayInputStream(new byte[0]), stringValue);
               this.defaultEncoding = stringValue;
            } catch (UnsupportedEncodingException var6) {
            }
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenImplementingAbstract")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportUnusedParameterWhenImplementingAbstract = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportUnusedParameterWhenImplementingAbstract = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterWhenOverridingConcrete")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportUnusedParameterWhenOverridingConcrete = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportUnusedParameterWhenOverridingConcrete = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameterIncludeDocCommentReference")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportUnusedParameterIncludeDocCommentReference = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportUnusedParameterIncludeDocCommentReference = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.specialParameterHidingField")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportSpecialParameterHidingField = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportSpecialParameterHidingField = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unavoidableGenericTypeProblems")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportUnavoidableGenericTypeProblems = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportUnavoidableGenericTypeProblems = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.deadCodeInTrivialIfStatement")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportDeadCodeInTrivialIfStatement = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportDeadCodeInTrivialIfStatement = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.maxProblemPerUnit")) != null) {
         stringValue = optionValue;

         try {
            int val = Integer.parseInt(stringValue);
            if (val >= 0) {
               this.maxProblemsPerUnit = val;
            }
         } catch (NumberFormatException var5) {
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.taskTags")) != null) {
         if (optionValue.length() == 0) {
            this.taskTags = null;
         } else {
            this.taskTags = CharOperation.splitAndTrimOn(',', optionValue.toCharArray());
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.taskPriorities")) != null) {
         if (optionValue.length() == 0) {
            this.taskPriorities = null;
         } else {
            this.taskPriorities = CharOperation.splitAndTrimOn(',', optionValue.toCharArray());
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.taskCaseSensitive")) != null) {
         if ("enabled".equals(optionValue)) {
            this.isTaskCaseSensitive = true;
         } else if ("disabled".equals(optionValue)) {
            this.isTaskCaseSensitive = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.codegen.inlineJsrBytecode")) != null && this.targetJDK < 3211264L) {
         if ("enabled".equals(optionValue)) {
            this.inlineJsrBytecode = true;
         } else if ("disabled".equals(optionValue)) {
            this.inlineJsrBytecode = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.codegen.shareCommonFinallyBlocks")) != null) {
         if ("enabled".equals(optionValue)) {
            this.shareCommonFinallyBlocks = true;
         } else if ("disabled".equals(optionValue)) {
            this.shareCommonFinallyBlocks = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.codegen.methodParameters")) != null) {
         if ("generate".equals(optionValue)) {
            this.produceMethodParameters = true;
         } else if ("do not generate".equals(optionValue)) {
            this.produceMethodParameters = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.codegen.lambda.genericSignature")) != null) {
         if ("generate".equals(optionValue)) {
            this.generateGenericSignatureForLambdaExpressions = true;
         } else if ("do not generate".equals(optionValue)) {
            this.generateGenericSignatureForLambdaExpressions = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.suppressWarnings")) != null) {
         if ("enabled".equals(optionValue)) {
            this.suppressWarnings = true;
         } else if ("disabled".equals(optionValue)) {
            this.suppressWarnings = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.suppressOptionalErrors")) != null) {
         if ("enabled".equals(optionValue)) {
            this.suppressOptionalErrors = true;
         } else if ("disabled".equals(optionValue)) {
            this.suppressOptionalErrors = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.fatalOptionalError")) != null) {
         if ("enabled".equals(optionValue)) {
            this.treatOptionalErrorAsFatal = true;
         } else if ("disabled".equals(optionValue)) {
            this.treatOptionalErrorAsFatal = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotationForInterfaceMethodImplementation")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportMissingOverrideAnnotationForInterfaceMethodImplementation = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportMissingOverrideAnnotationForInterfaceMethodImplementation = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.includeNullInfoFromAsserts")) != null) {
         if ("enabled".equals(optionValue)) {
            this.includeNullInfoFromAsserts = true;
         } else if ("disabled".equals(optionValue)) {
            this.includeNullInfoFromAsserts = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.methodWithConstructorName")) != null) {
         this.updateSeverity(1, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.overridingPackageDefaultMethod")) != null) {
         this.updateSeverity(2, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.deprecation")) != null) {
         this.updateSeverity(4, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.terminalDeprecation")) != null) {
         this.updateSeverity(1082130432, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.hiddenCatchBlock")) != null) {
         this.updateSeverity(8, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLocal")) != null) {
         this.updateSeverity(16, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedParameter")) != null) {
         this.updateSeverity(32, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedExceptionParameter")) != null) {
         this.updateSeverity(1074003968, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedImport")) != null) {
         this.updateSeverity(1024, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedPrivateMember")) != null) {
         this.updateSeverity(32768, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedDeclaredThrownException")) != null) {
         this.updateSeverity(8388608, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.noImplicitStringConversion")) != null) {
         this.updateSeverity(64, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.syntheticAccessEmulation")) != null) {
         this.updateSeverity(128, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.localVariableHiding")) != null) {
         this.updateSeverity(65536, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.fieldHiding")) != null) {
         this.updateSeverity(131072, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.typeParameterHiding")) != null) {
         this.updateSeverity(536871936, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.possibleAccidentalBooleanAssignment")) != null) {
         this.updateSeverity(262144, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.emptyStatement")) != null) {
         this.updateSeverity(524288, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.nonExternalizedStringLiteral")) != null) {
         this.updateSeverity(256, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.assertIdentifier")) != null) {
         this.updateSeverity(512, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.enumIdentifier")) != null) {
         this.updateSeverity(536870928, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.staticAccessReceiver")) != null) {
         this.updateSeverity(2048, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.indirectStaticAccess")) != null) {
         this.updateSeverity(268435456, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.incompatibleNonInheritedInterfaceMethod")) != null) {
         this.updateSeverity(16384, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.undocumentedEmptyBlock")) != null) {
         this.updateSeverity(134217728, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryTypeCheck")) != null) {
         this.updateSeverity(67108864, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unnecessaryElse")) != null) {
         this.updateSeverity(536870913, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.finallyBlockNotCompletingNormally")) != null) {
         this.updateSeverity(16777216, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unqualifiedFieldAccess")) != null) {
         this.updateSeverity(4194304, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.noEffectAssignment")) != null) {
         this.updateSeverity(8192, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.uncheckedTypeOperation")) != null) {
         this.updateSeverity(536870914, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.rawTypeReference")) != null) {
         this.updateSeverity(536936448, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.finalParameterBound")) != null) {
         this.updateSeverity(536870916, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingSerialVersion")) != null) {
         this.updateSeverity(536870920, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.forbiddenReference")) != null) {
         this.updateSeverity(536870944, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.discouragedReference")) != null) {
         this.updateSeverity(536887296, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.varargsArgumentNeedCast")) != null) {
         this.updateSeverity(536870976, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.nullReference")) != null) {
         this.updateSeverity(536871040, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.potentialNullReference")) != null) {
         this.updateSeverity(538968064, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullCheck")) != null) {
         this.updateSeverity(541065216, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.autoboxing")) != null) {
         this.updateSeverity(536871168, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.annotationSuperInterface")) != null) {
         this.updateSeverity(536871424, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingOverrideAnnotation")) != null) {
         this.updateSeverity(536872960, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingDeprecatedAnnotation")) != null) {
         this.updateSeverity(536879104, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.incompleteEnumSwitch")) != null) {
         this.updateSeverity(536875008, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingEnumCaseDespiteDefault")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportMissingEnumCaseDespiteDefault = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportMissingEnumCaseDespiteDefault = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingDefaultCase")) != null) {
         this.updateSeverity(1073774592, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unhandledWarningToken")) != null) {
         this.updateSeverity(536903680, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedWarningToken")) != null) {
         this.updateSeverity(570425344, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedLabel")) != null) {
         this.updateSeverity(537001984, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.parameterAssignment")) != null) {
         this.updateSeverity(537133056, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.fallthroughCase")) != null) {
         this.updateSeverity(537395200, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.overridingMethodWithoutSuperInvocation")) != null) {
         this.updateSeverity(537919488, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeArgumentsForMethodInvocation")) != null) {
         this.updateSeverity(553648128, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSuperinterface")) != null) {
         this.updateSeverity(603979776, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.comparingIdentical")) != null) {
         this.updateSeverity(671088640, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingSynchronizedOnInheritedMethod")) != null) {
         this.updateSeverity(805306368, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingHashCodeMethod")) != null) {
         this.updateSeverity(1073741825, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.deadCode")) != null) {
         this.updateSeverity(1073741826, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.tasks")) != null) {
         this.updateSeverity(1073741828, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedObjectAllocation")) != null) {
         this.updateSeverity(1073741832, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBeStatic")) != null) {
         this.updateSeverity(1073741840, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.reportMethodCanBePotentiallyStatic")) != null) {
         this.updateSeverity(1073741856, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.redundantSpecificationOfTypeArguments")) != null) {
         this.updateSeverity(1073741888, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unclosedCloseable")) != null) {
         this.updateSeverity(1073741952, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.potentiallyUnclosedCloseable")) != null) {
         this.updateSeverity(1073742080, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.explicitlyClosedAutoCloseable")) != null) {
         this.updateSeverity(1073742336, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unusedTypeParameter")) != null) {
         this.updateSeverity(1073807360, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentType")) != null) {
         this.updateSeverity(1075838976, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyCollectionMethodArgumentTypeStrict")) != null) {
         this.reportUnlikelyCollectionMethodArgumentTypeStrict = "enabled".equals(optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unlikelyEqualsArgumentType")) != null) {
         this.updateSeverity(1077936128, optionValue);
      }

      if (this.getSeverity(1073741952) == 256 && this.getSeverity(1073742080) == 256 && this.getSeverity(1073742336) == 256) {
         this.analyseResourceLeaks = false;
      } else {
         this.analyseResourceLeaks = true;
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.APILeak")) != null) {
         this.updateSeverity(1090519040, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.unstableAutoModuleName")) != null) {
         this.updateSeverity(1107296256, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nullanalysis")) != null) {
         this.isAnnotationBasedNullAnalysisEnabled = "enabled".equals(optionValue);
      }

      if (this.isAnnotationBasedNullAnalysisEnabled) {
         this.storeAnnotations = true;
         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.nullSpecViolation")) != null) {
            if ("error".equals(optionValue)) {
               this.errorThreshold.set(1073742848);
               this.warningThreshold.clear(1073742848);
            } else if ("warning".equals(optionValue)) {
               this.errorThreshold.clear(1073742848);
               this.warningThreshold.set(1073742848);
            }
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.nullAnnotationInferenceConflict")) != null) {
            this.updateSeverity(1073743872, optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.nullUncheckedConversion")) != null) {
            this.updateSeverity(1073745920, optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.redundantNullAnnotation")) != null) {
            this.updateSeverity(1073750016, optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nullable")) != null) {
            this.nullableAnnotationName = CharOperation.splitAndTrimOn('.', optionValue.toCharArray());
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull")) != null) {
            this.nonNullAnnotationName = CharOperation.splitAndTrimOn('.', optionValue.toCharArray());
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault")) != null) {
            this.nonNullByDefaultAnnotationName = CharOperation.splitAndTrimOn('.', optionValue.toCharArray());
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nullable.secondary")) != null) {
            this.nullableAnnotationSecondaryNames = this.stringToNameList(optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnull.secondary")) != null) {
            this.nonNullAnnotationSecondaryNames = this.stringToNameList(optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.nonnullbydefault.secondary")) != null) {
            this.nonNullByDefaultAnnotationSecondaryNames = this.stringToNameList(optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.missingNonNullByDefaultAnnotation")) != null) {
            this.updateSeverity(1073758208, optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.syntacticNullAnalysisForFields")) != null) {
            this.enableSyntacticNullAnalysisForFields = "enabled".equals(optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.annotation.inheritNullAnnotations")) != null) {
            this.inheritNullAnnotations = "enabled".equals(optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.nonnullParameterAnnotationDropped")) != null) {
            this.updateSeverity(1073872896, optionValue);
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.pessimisticNullAnalysisForFreeTypeVariables")) != null) {
            this.updateSeverity(1074266112, optionValue);
         }

         if (this.getSeverity(1074266112) == 256) {
            this.pessimisticNullAnalysisForFreeTypeVariablesEnabled = false;
         } else {
            this.pessimisticNullAnalysisForFreeTypeVariablesEnabled = true;
         }

         if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.nonnullTypeVariableFromLegacyInvocation")) != null) {
            this.updateSeverity(1074790400, optionValue);
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.doc.comment.support")) != null) {
         if ("enabled".equals(optionValue)) {
            this.docCommentSupport = true;
         } else if ("disabled".equals(optionValue)) {
            this.docCommentSupport = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadoc")) != null) {
         this.updateSeverity(33554432, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsVisibility")) != null) {
         if ("public".equals(optionValue)) {
            this.reportInvalidJavadocTagsVisibility = 1;
         } else if ("protected".equals(optionValue)) {
            this.reportInvalidJavadocTagsVisibility = 4;
         } else if ("default".equals(optionValue)) {
            this.reportInvalidJavadocTagsVisibility = 0;
         } else if ("private".equals(optionValue)) {
            this.reportInvalidJavadocTagsVisibility = 2;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTags")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportInvalidJavadocTags = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportInvalidJavadocTags = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsDeprecatedRef")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportInvalidJavadocTagsDeprecatedRef = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportInvalidJavadocTagsDeprecatedRef = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.invalidJavadocTagsNotVisibleRef")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportInvalidJavadocTagsNotVisibleRef = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportInvalidJavadocTagsNotVisibleRef = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTags")) != null) {
         this.updateSeverity(2097152, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsVisibility")) != null) {
         if ("public".equals(optionValue)) {
            this.reportMissingJavadocTagsVisibility = 1;
         } else if ("protected".equals(optionValue)) {
            this.reportMissingJavadocTagsVisibility = 4;
         } else if ("default".equals(optionValue)) {
            this.reportMissingJavadocTagsVisibility = 0;
         } else if ("private".equals(optionValue)) {
            this.reportMissingJavadocTagsVisibility = 2;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsOverriding")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportMissingJavadocTagsOverriding = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportMissingJavadocTagsOverriding = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagsMethodTypeParameters")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportMissingJavadocTagsMethodTypeParameters = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportMissingJavadocTagsMethodTypeParameters = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocComments")) != null) {
         this.updateSeverity(1048576, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocTagDescription")) != null) {
         this.reportMissingJavadocTagDescription = optionValue;
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsVisibility")) != null) {
         if ("public".equals(optionValue)) {
            this.reportMissingJavadocCommentsVisibility = 1;
         } else if ("protected".equals(optionValue)) {
            this.reportMissingJavadocCommentsVisibility = 4;
         } else if ("default".equals(optionValue)) {
            this.reportMissingJavadocCommentsVisibility = 0;
         } else if ("private".equals(optionValue)) {
            this.reportMissingJavadocCommentsVisibility = 2;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.missingJavadocCommentsOverriding")) != null) {
         if ("enabled".equals(optionValue)) {
            this.reportMissingJavadocCommentsOverriding = true;
         } else if ("disabled".equals(optionValue)) {
            this.reportMissingJavadocCommentsOverriding = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.generateClassFiles")) != null) {
         if ("enabled".equals(optionValue)) {
            this.generateClassFiles = true;
         } else if ("disabled".equals(optionValue)) {
            this.generateClassFiles = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.processAnnotations")) != null) {
         if ("enabled".equals(optionValue)) {
            this.processAnnotations = true;
            this.storeAnnotations = true;
         } else if ("disabled".equals(optionValue)) {
            this.processAnnotations = false;
            if (!this.isAnnotationBasedNullAnalysisEnabled) {
               this.storeAnnotations = false;
            }
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.storeAnnotations")) != null) {
         if ("enabled".equals(optionValue)) {
            this.storeAnnotations = true;
         } else if ("disabled".equals(optionValue) && !this.isAnnotationBasedNullAnalysisEnabled && !this.processAnnotations) {
            this.storeAnnotations = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.emulateJavacBug8031744")) != null) {
         if ("enabled".equals(optionValue)) {
            this.emulateJavacBug8031744 = true;
         } else if ("disabled".equals(optionValue)) {
            this.emulateJavacBug8031744 = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.uninternedIdentityComparison")) != null) {
         if ("enabled".equals(optionValue)) {
            this.complainOnUninternedIdentityComparison = true;
         } else if ("disabled".equals(optionValue)) {
            this.complainOnUninternedIdentityComparison = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.enablePreviewFeatures")) != null) {
         if ("enabled".equals(optionValue)) {
            this.enablePreviewFeatures = true;
            if (this.targetJDK != 0L) {
               this.targetJDK |= 65535L;
            }
         } else if ("disabled".equals(optionValue)) {
            this.enablePreviewFeatures = false;
         }
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.core.compiler.problem.reportPreviewFeatures")) != null) {
         this.updateSeverity(1140850688, optionValue);
      }

      if ((optionValue = (String)optionsMap.get("com.bea.core.repackaged.jdt.internal.debug.compile.mode")) != null) {
         if ("enabled".equals(optionValue)) {
            this.enableJdtDebugCompileMode = true;
         } else if ("disabled".equals(optionValue)) {
            this.enableJdtDebugCompileMode = false;
         }
      }

   }

   private String[] stringToNameList(String optionValue) {
      String[] result = optionValue.split(",");
      if (result == null) {
         return NO_STRINGS;
      } else {
         for(int i = 0; i < result.length; ++i) {
            result[i] = result[i].trim();
         }

         return result;
      }
   }

   String nameListToString(String[] names) {
      if (names == null) {
         return "";
      } else {
         StringBuilder buf = new StringBuilder();

         for(int i = 0; i < names.length; ++i) {
            if (i > 0) {
               buf.append(',');
            }

            buf.append(names[i]);
         }

         return buf.toString();
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("CompilerOptions:");
      buf.append("\n\t- local variables debug attributes: ").append((this.produceDebugAttributes & 4) != 0 ? "ON" : " OFF");
      buf.append("\n\t- line number debug attributes: ").append((this.produceDebugAttributes & 2) != 0 ? "ON" : " OFF");
      buf.append("\n\t- source debug attributes: ").append((this.produceDebugAttributes & 1) != 0 ? "ON" : " OFF");
      buf.append("\n\t- MethodParameters attributes: ").append(this.produceMethodParameters ? "generate" : "do not generate");
      buf.append("\n\t- Generic signature for lambda expressions: ").append(this.generateGenericSignatureForLambdaExpressions ? "generate" : "do not generate");
      buf.append("\n\t- preserve all local variables: ").append(this.preserveAllLocalVariables ? "ON" : " OFF");
      buf.append("\n\t- method with constructor name: ").append(this.getSeverityString(1));
      buf.append("\n\t- overridden package default method: ").append(this.getSeverityString(2));
      buf.append("\n\t- deprecation: ").append(this.getSeverityString(4));
      buf.append("\n\t- removal: ").append(this.getSeverityString(1082130432));
      buf.append("\n\t- masked catch block: ").append(this.getSeverityString(8));
      buf.append("\n\t- unused local variable: ").append(this.getSeverityString(16));
      buf.append("\n\t- unused parameter: ").append(this.getSeverityString(32));
      buf.append("\n\t- unused exception parameter: ").append(this.getSeverityString(1074003968));
      buf.append("\n\t- unused import: ").append(this.getSeverityString(1024));
      buf.append("\n\t- synthetic access emulation: ").append(this.getSeverityString(128));
      buf.append("\n\t- assignment with no effect: ").append(this.getSeverityString(8192));
      buf.append("\n\t- non externalized string: ").append(this.getSeverityString(256));
      buf.append("\n\t- static access receiver: ").append(this.getSeverityString(2048));
      buf.append("\n\t- indirect static access: ").append(this.getSeverityString(268435456));
      buf.append("\n\t- incompatible non inherited interface method: ").append(this.getSeverityString(16384));
      buf.append("\n\t- unused private member: ").append(this.getSeverityString(32768));
      buf.append("\n\t- local variable hiding another variable: ").append(this.getSeverityString(65536));
      buf.append("\n\t- field hiding another variable: ").append(this.getSeverityString(131072));
      buf.append("\n\t- type hiding another type: ").append(this.getSeverityString(536871936));
      buf.append("\n\t- possible accidental boolean assignment: ").append(this.getSeverityString(262144));
      buf.append("\n\t- superfluous semicolon: ").append(this.getSeverityString(524288));
      buf.append("\n\t- uncommented empty block: ").append(this.getSeverityString(134217728));
      buf.append("\n\t- unnecessary type check: ").append(this.getSeverityString(67108864));
      buf.append("\n\t- javadoc comment support: ").append(this.docCommentSupport ? "ON" : " OFF");
      buf.append("\n\t\t+ invalid javadoc: ").append(this.getSeverityString(33554432));
      buf.append("\n\t\t+ report invalid javadoc tags: ").append(this.reportInvalidJavadocTags ? "enabled" : "disabled");
      buf.append("\n\t\t\t* deprecated references: ").append(this.reportInvalidJavadocTagsDeprecatedRef ? "enabled" : "disabled");
      buf.append("\n\t\t\t* not visible references: ").append(this.reportInvalidJavadocTagsNotVisibleRef ? "enabled" : "disabled");
      buf.append("\n\t\t+ visibility level to report invalid javadoc tags: ").append(this.getVisibilityString(this.reportInvalidJavadocTagsVisibility));
      buf.append("\n\t\t+ missing javadoc tags: ").append(this.getSeverityString(2097152));
      buf.append("\n\t\t+ visibility level to report missing javadoc tags: ").append(this.getVisibilityString(this.reportMissingJavadocTagsVisibility));
      buf.append("\n\t\t+ report missing javadoc tags for method type parameters: ").append(this.reportMissingJavadocTagsMethodTypeParameters ? "enabled" : "disabled");
      buf.append("\n\t\t+ report missing javadoc tags in overriding methods: ").append(this.reportMissingJavadocTagsOverriding ? "enabled" : "disabled");
      buf.append("\n\t\t+ missing javadoc comments: ").append(this.getSeverityString(1048576));
      buf.append("\n\t\t+ report missing tag description option: ").append(this.reportMissingJavadocTagDescription);
      buf.append("\n\t\t+ visibility level to report missing javadoc comments: ").append(this.getVisibilityString(this.reportMissingJavadocCommentsVisibility));
      buf.append("\n\t\t+ report missing javadoc comments in overriding methods: ").append(this.reportMissingJavadocCommentsOverriding ? "enabled" : "disabled");
      buf.append("\n\t- finally block not completing normally: ").append(this.getSeverityString(16777216));
      buf.append("\n\t- report unused declared thrown exception: ").append(this.getSeverityString(8388608));
      buf.append("\n\t- report unused declared thrown exception when overriding: ").append(this.reportUnusedDeclaredThrownExceptionWhenOverriding ? "enabled" : "disabled");
      buf.append("\n\t- report unused declared thrown exception include doc comment reference: ").append(this.reportUnusedDeclaredThrownExceptionIncludeDocCommentReference ? "enabled" : "disabled");
      buf.append("\n\t- report unused declared thrown exception exempt exception and throwable: ").append(this.reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable ? "enabled" : "disabled");
      buf.append("\n\t- unnecessary else: ").append(this.getSeverityString(536870913));
      buf.append("\n\t- JDK compliance level: " + versionFromJdkLevel(this.complianceLevel));
      buf.append("\n\t- JDK source level: " + versionFromJdkLevel(this.sourceLevel));
      buf.append("\n\t- JDK target level: " + versionFromJdkLevel(this.targetJDK));
      buf.append("\n\t- verbose : ").append(this.verbose ? "ON" : "OFF");
      buf.append("\n\t- produce reference info : ").append(this.produceReferenceInfo ? "ON" : "OFF");
      buf.append("\n\t- parse literal expressions as constants : ").append(this.parseLiteralExpressionsAsConstants ? "ON" : "OFF");
      buf.append("\n\t- encoding : ").append(this.defaultEncoding == null ? "<default>" : this.defaultEncoding);
      buf.append("\n\t- task tags: ").append(this.taskTags == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskTags, ',')));
      buf.append("\n\t- task priorities : ").append(this.taskPriorities == null ? Util.EMPTY_STRING : new String(CharOperation.concatWith(this.taskPriorities, ',')));
      buf.append("\n\t- report deprecation inside deprecated code : ").append(this.reportDeprecationInsideDeprecatedCode ? "enabled" : "disabled");
      buf.append("\n\t- report deprecation when overriding deprecated method : ").append(this.reportDeprecationWhenOverridingDeprecatedMethod ? "enabled" : "disabled");
      buf.append("\n\t- report unused parameter when implementing abstract method : ").append(this.reportUnusedParameterWhenImplementingAbstract ? "enabled" : "disabled");
      buf.append("\n\t- report unused parameter when overriding concrete method : ").append(this.reportUnusedParameterWhenOverridingConcrete ? "enabled" : "disabled");
      buf.append("\n\t- report unused parameter include doc comment reference : ").append(this.reportUnusedParameterIncludeDocCommentReference ? "enabled" : "disabled");
      buf.append("\n\t- report constructor/setter parameter hiding existing field : ").append(this.reportSpecialParameterHidingField ? "enabled" : "disabled");
      buf.append("\n\t- inline JSR bytecode : ").append(this.inlineJsrBytecode ? "enabled" : "disabled");
      buf.append("\n\t- share common finally blocks : ").append(this.shareCommonFinallyBlocks ? "enabled" : "disabled");
      buf.append("\n\t- report unavoidable generic type problems : ").append(this.reportUnavoidableGenericTypeProblems ? "enabled" : "disabled");
      buf.append("\n\t- unsafe type operation: ").append(this.getSeverityString(536870914));
      buf.append("\n\t- unsafe raw type: ").append(this.getSeverityString(536936448));
      buf.append("\n\t- final bound for type parameter: ").append(this.getSeverityString(536870916));
      buf.append("\n\t- missing serialVersionUID: ").append(this.getSeverityString(536870920));
      buf.append("\n\t- varargs argument need cast: ").append(this.getSeverityString(536870976));
      buf.append("\n\t- forbidden reference to type with access restriction: ").append(this.getSeverityString(536870944));
      buf.append("\n\t- discouraged reference to type with access restriction: ").append(this.getSeverityString(536887296));
      buf.append("\n\t- null reference: ").append(this.getSeverityString(536871040));
      buf.append("\n\t- potential null reference: ").append(this.getSeverityString(538968064));
      buf.append("\n\t- redundant null check: ").append(this.getSeverityString(541065216));
      buf.append("\n\t- autoboxing: ").append(this.getSeverityString(536871168));
      buf.append("\n\t- annotation super interface: ").append(this.getSeverityString(536871424));
      buf.append("\n\t- missing @Override annotation: ").append(this.getSeverityString(536872960));
      buf.append("\n\t- missing @Override annotation for interface method implementation: ").append(this.reportMissingOverrideAnnotationForInterfaceMethodImplementation ? "enabled" : "disabled");
      buf.append("\n\t- missing @Deprecated annotation: ").append(this.getSeverityString(536879104));
      buf.append("\n\t- incomplete enum switch: ").append(this.getSeverityString(536875008));
      buf.append("\n\t- raise null related warnings for variables tainted in assert statements: ").append(this.includeNullInfoFromAsserts ? "enabled" : "disabled");
      buf.append("\n\t- suppress warnings: ").append(this.suppressWarnings ? "enabled" : "disabled");
      buf.append("\n\t- suppress optional errors: ").append(this.suppressOptionalErrors ? "enabled" : "disabled");
      buf.append("\n\t- unhandled warning token: ").append(this.getSeverityString(536903680));
      buf.append("\n\t- unused warning token: ").append(this.getSeverityString(570425344));
      buf.append("\n\t- unused label: ").append(this.getSeverityString(537001984));
      buf.append("\n\t- treat optional error as fatal: ").append(this.treatOptionalErrorAsFatal ? "enabled" : "disabled");
      buf.append("\n\t- parameter assignment: ").append(this.getSeverityString(537133056));
      buf.append("\n\t- generate class files: ").append(this.generateClassFiles ? "enabled" : "disabled");
      buf.append("\n\t- process annotations: ").append(this.processAnnotations ? "enabled" : "disabled");
      buf.append("\n\t- unused type arguments for method/constructor invocation: ").append(this.getSeverityString(553648128));
      buf.append("\n\t- redundant superinterface: ").append(this.getSeverityString(603979776));
      buf.append("\n\t- comparing identical expr: ").append(this.getSeverityString(671088640));
      buf.append("\n\t- missing synchronized on inherited method: ").append(this.getSeverityString(805306368));
      buf.append("\n\t- should implement hashCode() method: ").append(this.getSeverityString(1073741825));
      buf.append("\n\t- dead code: ").append(this.getSeverityString(1073741826));
      buf.append("\n\t- dead code in trivial if statement: ").append(this.reportDeadCodeInTrivialIfStatement ? "enabled" : "disabled");
      buf.append("\n\t- tasks severity: ").append(this.getSeverityString(1073741828));
      buf.append("\n\t- unused object allocation: ").append(this.getSeverityString(1073741832));
      buf.append("\n\t- method can be static: ").append(this.getSeverityString(1073741840));
      buf.append("\n\t- method can be potentially static: ").append(this.getSeverityString(1073741856));
      buf.append("\n\t- redundant specification of type arguments: ").append(this.getSeverityString(1073741888));
      buf.append("\n\t- resource is not closed: ").append(this.getSeverityString(1073741952));
      buf.append("\n\t- resource may not be closed: ").append(this.getSeverityString(1073742080));
      buf.append("\n\t- resource should be handled by try-with-resources: ").append(this.getSeverityString(1073742336));
      buf.append("\n\t- Unused Type Parameter: ").append(this.getSeverityString(1073807360));
      buf.append("\n\t- pessimistic null analysis for free type variables: ").append(this.getSeverityString(1074266112));
      buf.append("\n\t- report unsafe nonnull return from legacy method: ").append(this.getSeverityString(1074790400));
      buf.append("\n\t- unlikely argument type for collection methods: ").append(this.getSeverityString(1075838976));
      buf.append("\n\t- unlikely argument type for collection methods, strict check against expected type: ").append(this.reportUnlikelyCollectionMethodArgumentTypeStrict ? "enabled" : "disabled");
      buf.append("\n\t- unlikely argument types for equals(): ").append(this.getSeverityString(1077936128));
      buf.append("\n\t- API leak: ").append(this.getSeverityString(1090519040));
      buf.append("\n\t- unstable auto module name: ").append(this.getSeverityString(1107296256));
      return buf.toString();
   }

   protected void updateSeverity(int irritant, Object severityString) {
      if ("error".equals(severityString)) {
         this.errorThreshold.set(irritant);
         this.warningThreshold.clear(irritant);
         this.infoThreshold.clear(irritant);
      } else if ("warning".equals(severityString)) {
         this.errorThreshold.clear(irritant);
         this.warningThreshold.set(irritant);
         this.infoThreshold.clear(irritant);
      } else if ("info".equals(severityString)) {
         this.errorThreshold.clear(irritant);
         this.warningThreshold.clear(irritant);
         this.infoThreshold.set(irritant);
      } else if ("ignore".equals(severityString)) {
         this.errorThreshold.clear(irritant);
         this.warningThreshold.clear(irritant);
         this.infoThreshold.clear(irritant);
      }

   }

   public boolean usesNullTypeAnnotations() {
      if (this.useNullTypeAnnotations != null) {
         return this.useNullTypeAnnotations;
      } else {
         return this.isAnnotationBasedNullAnalysisEnabled && this.sourceLevel >= 3407872L;
      }
   }
}
