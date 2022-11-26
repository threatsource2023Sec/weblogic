package com.bea.core.repackaged.springframework.expression.spel;

import java.text.MessageFormat;

public enum SpelMessage {
   TYPE_CONVERSION_ERROR(SpelMessage.Kind.ERROR, 1001, "Type conversion problem, cannot convert from {0} to {1}"),
   CONSTRUCTOR_NOT_FOUND(SpelMessage.Kind.ERROR, 1002, "Constructor call: No suitable constructor found on type {0} for arguments {1}"),
   CONSTRUCTOR_INVOCATION_PROBLEM(SpelMessage.Kind.ERROR, 1003, "A problem occurred whilst attempting to construct an object of type ''{0}'' using arguments ''{1}''"),
   METHOD_NOT_FOUND(SpelMessage.Kind.ERROR, 1004, "Method call: Method {0} cannot be found on type {1}"),
   TYPE_NOT_FOUND(SpelMessage.Kind.ERROR, 1005, "Type cannot be found ''{0}''"),
   FUNCTION_NOT_DEFINED(SpelMessage.Kind.ERROR, 1006, "Function ''{0}'' could not be found"),
   PROPERTY_OR_FIELD_NOT_READABLE_ON_NULL(SpelMessage.Kind.ERROR, 1007, "Property or field ''{0}'' cannot be found on null"),
   PROPERTY_OR_FIELD_NOT_READABLE(SpelMessage.Kind.ERROR, 1008, "Property or field ''{0}'' cannot be found on object of type ''{1}'' - maybe not public or not valid?"),
   PROPERTY_OR_FIELD_NOT_WRITABLE_ON_NULL(SpelMessage.Kind.ERROR, 1009, "Property or field ''{0}'' cannot be set on null"),
   PROPERTY_OR_FIELD_NOT_WRITABLE(SpelMessage.Kind.ERROR, 1010, "Property or field ''{0}'' cannot be set on object of type ''{1}'' - maybe not public or not writable?"),
   METHOD_CALL_ON_NULL_OBJECT_NOT_ALLOWED(SpelMessage.Kind.ERROR, 1011, "Method call: Attempted to call method {0} on null context object"),
   CANNOT_INDEX_INTO_NULL_VALUE(SpelMessage.Kind.ERROR, 1012, "Cannot index into a null value"),
   NOT_COMPARABLE(SpelMessage.Kind.ERROR, 1013, "Cannot compare instances of {0} and {1}"),
   INCORRECT_NUMBER_OF_ARGUMENTS_TO_FUNCTION(SpelMessage.Kind.ERROR, 1014, "Incorrect number of arguments for function, {0} supplied but function takes {1}"),
   INVALID_TYPE_FOR_SELECTION(SpelMessage.Kind.ERROR, 1015, "Cannot perform selection on input data of type ''{0}''"),
   RESULT_OF_SELECTION_CRITERIA_IS_NOT_BOOLEAN(SpelMessage.Kind.ERROR, 1016, "Result of selection criteria is not boolean"),
   BETWEEN_RIGHT_OPERAND_MUST_BE_TWO_ELEMENT_LIST(SpelMessage.Kind.ERROR, 1017, "Right operand for the 'between' operator has to be a two-element list"),
   INVALID_PATTERN(SpelMessage.Kind.ERROR, 1018, "Pattern is not valid ''{0}''"),
   PROJECTION_NOT_SUPPORTED_ON_TYPE(SpelMessage.Kind.ERROR, 1019, "Projection is not supported on the type ''{0}''"),
   ARGLIST_SHOULD_NOT_BE_EVALUATED(SpelMessage.Kind.ERROR, 1020, "The argument list of a lambda expression should never have getValue() called upon it"),
   EXCEPTION_DURING_PROPERTY_READ(SpelMessage.Kind.ERROR, 1021, "A problem occurred whilst attempting to access the property ''{0}'': ''{1}''"),
   FUNCTION_REFERENCE_CANNOT_BE_INVOKED(SpelMessage.Kind.ERROR, 1022, "The function ''{0}'' mapped to an object of type ''{1}'' which cannot be invoked"),
   EXCEPTION_DURING_FUNCTION_CALL(SpelMessage.Kind.ERROR, 1023, "A problem occurred whilst attempting to invoke the function ''{0}'': ''{1}''"),
   ARRAY_INDEX_OUT_OF_BOUNDS(SpelMessage.Kind.ERROR, 1024, "The array has ''{0}'' elements, index ''{1}'' is invalid"),
   COLLECTION_INDEX_OUT_OF_BOUNDS(SpelMessage.Kind.ERROR, 1025, "The collection has ''{0}'' elements, index ''{1}'' is invalid"),
   STRING_INDEX_OUT_OF_BOUNDS(SpelMessage.Kind.ERROR, 1026, "The string has ''{0}'' characters, index ''{1}'' is invalid"),
   INDEXING_NOT_SUPPORTED_FOR_TYPE(SpelMessage.Kind.ERROR, 1027, "Indexing into type ''{0}'' is not supported"),
   INSTANCEOF_OPERATOR_NEEDS_CLASS_OPERAND(SpelMessage.Kind.ERROR, 1028, "The operator 'instanceof' needs the right operand to be a class, not a ''{0}''"),
   EXCEPTION_DURING_METHOD_INVOCATION(SpelMessage.Kind.ERROR, 1029, "A problem occurred when trying to execute method ''{0}'' on object of type ''{1}'': ''{2}''"),
   OPERATOR_NOT_SUPPORTED_BETWEEN_TYPES(SpelMessage.Kind.ERROR, 1030, "The operator ''{0}'' is not supported between objects of type ''{1}'' and ''{2}''"),
   PROBLEM_LOCATING_METHOD(SpelMessage.Kind.ERROR, 1031, "Problem locating method {0} on type {1}"),
   SETVALUE_NOT_SUPPORTED(SpelMessage.Kind.ERROR, 1032, "setValue(ExpressionState, Object) not supported for ''{0}''"),
   MULTIPLE_POSSIBLE_METHODS(SpelMessage.Kind.ERROR, 1033, "Method call of ''{0}'' is ambiguous, supported type conversions allow multiple variants to match"),
   EXCEPTION_DURING_PROPERTY_WRITE(SpelMessage.Kind.ERROR, 1034, "A problem occurred whilst attempting to set the property ''{0}'': {1}"),
   NOT_AN_INTEGER(SpelMessage.Kind.ERROR, 1035, "The value ''{0}'' cannot be parsed as an int"),
   NOT_A_LONG(SpelMessage.Kind.ERROR, 1036, "The value ''{0}'' cannot be parsed as a long"),
   INVALID_FIRST_OPERAND_FOR_MATCHES_OPERATOR(SpelMessage.Kind.ERROR, 1037, "First operand to matches operator must be a string. ''{0}'' is not"),
   INVALID_SECOND_OPERAND_FOR_MATCHES_OPERATOR(SpelMessage.Kind.ERROR, 1038, "Second operand to matches operator must be a string. ''{0}'' is not"),
   FUNCTION_MUST_BE_STATIC(SpelMessage.Kind.ERROR, 1039, "Only static methods can be called via function references. The method ''{0}'' referred to by name ''{1}'' is not static."),
   NOT_A_REAL(SpelMessage.Kind.ERROR, 1040, "The value ''{0}'' cannot be parsed as a double"),
   MORE_INPUT(SpelMessage.Kind.ERROR, 1041, "After parsing a valid expression, there is still more data in the expression: ''{0}''"),
   RIGHT_OPERAND_PROBLEM(SpelMessage.Kind.ERROR, 1042, "Problem parsing right operand"),
   NOT_EXPECTED_TOKEN(SpelMessage.Kind.ERROR, 1043, "Unexpected token. Expected ''{0}'' but was ''{1}''"),
   OOD(SpelMessage.Kind.ERROR, 1044, "Unexpectedly ran out of input"),
   NON_TERMINATING_DOUBLE_QUOTED_STRING(SpelMessage.Kind.ERROR, 1045, "Cannot find terminating \" for string"),
   NON_TERMINATING_QUOTED_STRING(SpelMessage.Kind.ERROR, 1046, "Cannot find terminating '' for string"),
   MISSING_LEADING_ZERO_FOR_NUMBER(SpelMessage.Kind.ERROR, 1047, "A real number must be prefixed by zero, it cannot start with just ''.''"),
   REAL_CANNOT_BE_LONG(SpelMessage.Kind.ERROR, 1048, "Real number cannot be suffixed with a long (L or l) suffix"),
   UNEXPECTED_DATA_AFTER_DOT(SpelMessage.Kind.ERROR, 1049, "Unexpected data after ''.'': ''{0}''"),
   MISSING_CONSTRUCTOR_ARGS(SpelMessage.Kind.ERROR, 1050, "The arguments '(...)' for the constructor call are missing"),
   RUN_OUT_OF_ARGUMENTS(SpelMessage.Kind.ERROR, 1051, "Unexpectedly ran out of arguments"),
   UNABLE_TO_GROW_COLLECTION(SpelMessage.Kind.ERROR, 1052, "Unable to grow collection"),
   UNABLE_TO_GROW_COLLECTION_UNKNOWN_ELEMENT_TYPE(SpelMessage.Kind.ERROR, 1053, "Unable to grow collection: unable to determine list element type"),
   UNABLE_TO_CREATE_LIST_FOR_INDEXING(SpelMessage.Kind.ERROR, 1054, "Unable to dynamically create a List to replace a null value"),
   UNABLE_TO_CREATE_MAP_FOR_INDEXING(SpelMessage.Kind.ERROR, 1055, "Unable to dynamically create a Map to replace a null value"),
   UNABLE_TO_DYNAMICALLY_CREATE_OBJECT(SpelMessage.Kind.ERROR, 1056, "Unable to dynamically create instance of ''{0}'' to replace a null value"),
   NO_BEAN_RESOLVER_REGISTERED(SpelMessage.Kind.ERROR, 1057, "No bean resolver registered in the context to resolve access to bean ''{0}''"),
   EXCEPTION_DURING_BEAN_RESOLUTION(SpelMessage.Kind.ERROR, 1058, "A problem occurred when trying to resolve bean ''{0}'':''{1}''"),
   INVALID_BEAN_REFERENCE(SpelMessage.Kind.ERROR, 1059, "@ or & can only be followed by an identifier or a quoted name"),
   TYPE_NAME_EXPECTED_FOR_ARRAY_CONSTRUCTION(SpelMessage.Kind.ERROR, 1060, "Expected the type of the new array to be specified as a String but found ''{0}''"),
   INCORRECT_ELEMENT_TYPE_FOR_ARRAY(SpelMessage.Kind.ERROR, 1061, "The array of type ''{0}'' cannot have an element of type ''{1}'' inserted"),
   MULTIDIM_ARRAY_INITIALIZER_NOT_SUPPORTED(SpelMessage.Kind.ERROR, 1062, "Using an initializer to build a multi-dimensional array is not currently supported"),
   MISSING_ARRAY_DIMENSION(SpelMessage.Kind.ERROR, 1063, "A required array dimension has not been specified"),
   INITIALIZER_LENGTH_INCORRECT(SpelMessage.Kind.ERROR, 1064, "Array initializer size does not match array dimensions"),
   UNEXPECTED_ESCAPE_CHAR(SpelMessage.Kind.ERROR, 1065, "Unexpected escape character"),
   OPERAND_NOT_INCREMENTABLE(SpelMessage.Kind.ERROR, 1066, "The expression component ''{0}'' does not support increment"),
   OPERAND_NOT_DECREMENTABLE(SpelMessage.Kind.ERROR, 1067, "The expression component ''{0}'' does not support decrement"),
   NOT_ASSIGNABLE(SpelMessage.Kind.ERROR, 1068, "The expression component ''{0}'' is not assignable"),
   MISSING_CHARACTER(SpelMessage.Kind.ERROR, 1069, "Missing expected character ''{0}''"),
   LEFT_OPERAND_PROBLEM(SpelMessage.Kind.ERROR, 1070, "Problem parsing left operand"),
   MISSING_SELECTION_EXPRESSION(SpelMessage.Kind.ERROR, 1071, "A required selection expression has not been specified"),
   EXCEPTION_RUNNING_COMPILED_EXPRESSION(SpelMessage.Kind.ERROR, 1072, "An exception occurred whilst evaluating a compiled expression"),
   FLAWED_PATTERN(SpelMessage.Kind.ERROR, 1073, "Failed to efficiently evaluate pattern ''{0}'': consider redesigning it");

   private final Kind kind;
   private final int code;
   private final String message;

   private SpelMessage(Kind kind, int code, String message) {
      this.kind = kind;
      this.code = code;
      this.message = message;
   }

   public String formatMessage(Object... inserts) {
      StringBuilder formattedMessage = new StringBuilder();
      formattedMessage.append("EL").append(this.code);
      switch (this.kind) {
         case ERROR:
            formattedMessage.append("E");
         default:
            formattedMessage.append(": ");
            formattedMessage.append(MessageFormat.format(this.message, inserts));
            return formattedMessage.toString();
      }
   }

   public static enum Kind {
      INFO,
      WARNING,
      ERROR;
   }
}
