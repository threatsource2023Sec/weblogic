package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public interface ProblemReasons {
   int NoError = 0;
   int NotFound = 1;
   int NotVisible = 2;
   int Ambiguous = 3;
   int InternalNameProvided = 4;
   int InheritedNameHidesEnclosingName = 5;
   int NonStaticReferenceInConstructorInvocation = 6;
   int NonStaticReferenceInStaticContext = 7;
   int ReceiverTypeNotVisible = 8;
   int IllegalSuperTypeVariable = 9;
   int ParameterBoundMismatch = 10;
   int TypeParameterArityMismatch = 11;
   int ParameterizedMethodTypeMismatch = 12;
   int TypeArgumentsForRawGenericMethod = 13;
   int InvalidTypeForStaticImport = 14;
   int InvalidTypeForAutoManagedResource = 15;
   int VarargsElementTypeNotVisible = 16;
   int NoSuchSingleAbstractMethod = 17;
   int NotAWellFormedParameterizedType = 18;
   int NonStaticOrAlienTypeReceiver = 20;
   int AttemptToBypassDirectSuper = 21;
   int DefectiveContainerAnnotationType = 22;
   int InvocationTypeInferenceFailure = 23;
   int ApplicableMethodOverriddenByInapplicable = 24;
   int ContradictoryNullAnnotations = 25;
   int NoSuchMethodOnArray = 26;
   int InferredApplicableMethodInapplicable = 27;
   int NoProperEnclosingInstance = 28;
   int InterfaceMethodInvocationNotBelow18 = 29;
   int NotAccessible = 30;
   int ErrorAlreadyReported = 31;
}
