package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public abstract class Binding {
   public static final int FIELD = 1;
   public static final int LOCAL = 2;
   public static final int VARIABLE = 3;
   public static final int TYPE = 4;
   public static final int METHOD = 8;
   public static final int PACKAGE = 16;
   public static final int IMPORT = 32;
   public static final int MODULE = 64;
   public static final int ARRAY_TYPE = 68;
   public static final int BASE_TYPE = 132;
   public static final int PARAMETERIZED_TYPE = 260;
   public static final int WILDCARD_TYPE = 516;
   public static final int RAW_TYPE = 1028;
   public static final int GENERIC_TYPE = 2052;
   public static final int TYPE_PARAMETER = 4100;
   public static final int INTERSECTION_TYPE = 8196;
   public static final int TYPE_USE = 16388;
   public static final int INTERSECTION_TYPE18 = 32772;
   public static final int POLY_TYPE = 65540;
   public static final ModuleBinding[] NO_MODULES = new ModuleBinding[0];
   public static final PackageBinding[] NO_PACKAGES = new PackageBinding[0];
   public static final TypeBinding[] NO_TYPES = new TypeBinding[0];
   public static final ReferenceBinding[] NO_REFERENCE_TYPES = new ReferenceBinding[0];
   public static final TypeBinding[] NO_PARAMETERS = new TypeBinding[0];
   public static final ReferenceBinding[] NO_EXCEPTIONS = new ReferenceBinding[0];
   public static final ReferenceBinding[] ANY_EXCEPTION = new ReferenceBinding[1];
   public static final FieldBinding[] NO_FIELDS = new FieldBinding[0];
   public static final MethodBinding[] NO_METHODS = new MethodBinding[0];
   public static final ReferenceBinding[] NO_SUPERINTERFACES = new ReferenceBinding[0];
   public static final ReferenceBinding[] NO_MEMBER_TYPES = new ReferenceBinding[0];
   public static final TypeVariableBinding[] NO_TYPE_VARIABLES = new TypeVariableBinding[0];
   public static final AnnotationBinding[] NO_ANNOTATIONS = new AnnotationBinding[0];
   public static final ElementValuePair[] NO_ELEMENT_VALUE_PAIRS = new ElementValuePair[0];
   public static final char[][] NO_PARAMETER_NAMES = new char[0][];
   public static final FieldBinding[] UNINITIALIZED_FIELDS = new FieldBinding[0];
   public static final MethodBinding[] UNINITIALIZED_METHODS = new MethodBinding[0];
   public static final ReferenceBinding[] UNINITIALIZED_REFERENCE_TYPES = new ReferenceBinding[0];
   static final InferenceVariable[] NO_INFERENCE_VARIABLES = new InferenceVariable[0];
   static final TypeBound[] NO_TYPE_BOUNDS = new TypeBound[0];
   public static final int NO_NULL_DEFAULT = 0;
   public static final int NULL_UNSPECIFIED_BY_DEFAULT = 2;
   public static final int DefaultLocationParameter = 8;
   public static final int DefaultLocationReturnType = 16;
   public static final int DefaultLocationField = 32;
   public static final int DefaultLocationTypeArgument = 64;
   public static final int DefaultLocationTypeParameter = 128;
   public static final int DefaultLocationTypeBound = 256;
   public static final int DefaultLocationArrayContents = 512;
   public static final int DefaultLocationsForTrueValue = 56;
   public static final int NullnessDefaultMASK = 1018;

   public abstract int kind();

   public char[] computeUniqueKey() {
      return this.computeUniqueKey(true);
   }

   public char[] computeUniqueKey(boolean isLeaf) {
      return null;
   }

   public long getAnnotationTagBits() {
      return 0L;
   }

   public void initializeDeprecatedAnnotationTagBits() {
   }

   public boolean isAnnotationType() {
      return false;
   }

   public final boolean isValidBinding() {
      return this.problemId() == 0;
   }

   public static boolean isValid(Binding binding) {
      return binding != null && binding.isValidBinding();
   }

   public boolean isVolatile() {
      return false;
   }

   public boolean isTaggedRepeatable() {
      return false;
   }

   public boolean isParameter() {
      return false;
   }

   public int problemId() {
      return 0;
   }

   public abstract char[] readableName();

   public char[] shortReadableName() {
      return this.readableName();
   }

   public AnnotationBinding[] getAnnotations() {
      return NO_ANNOTATIONS;
   }

   public void setAnnotations(AnnotationBinding[] annotations, Scope scope, boolean forceStore) {
      this.setAnnotations(annotations, forceStore);
   }

   public void setAnnotations(AnnotationBinding[] annotations, boolean forceStore) {
   }
}
