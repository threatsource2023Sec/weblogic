package org.apache.xmlbeans;

public abstract class XmlErrorCodes {
   public static final int MISMATCHED_TARGET_NAMESPACE = 4;
   public static final int CANNOT_FIND_RESOURCE = 56;
   public static final int REDUNDANT_NESTED_TYPE = 7;
   public static final int ELEMENT_MISSING_NAME = 8;
   public static final int REDUNDANT_DEFAULT_FIXED = 9;
   public static final int MODEL_GROUP_MISSING_NAME = 10;
   public static final int ATTRIBUTE_GROUP_MISSING_NAME = 12;
   public static final int CYCLIC_DEPENDENCY = 13;
   public static final int FACET_DUPLICATED = 19;
   public static final int FACET_VALUE_MALFORMED = 20;
   public static final int MALFORMED_NUMBER = 21;
   public static final int REDUNDANT_CONTENT_MODEL = 26;
   public static final int MISSING_RESTRICTION_OR_EXTENSION = 27;
   public static final int MISSING_BASE = 28;
   public static final int ELEMENT_EXTRA_REF = 30;
   public static final int EXPLICIT_GROUP_NEEDED = 32;
   public static final int GROUP_MISSING_REF = 33;
   public static final int DUPLICATE_ATTRIBUTE_NAME = 37;
   public static final int DUPLICATE_ANY_ATTRIBUTE = 38;
   public static final int ATTRIBUTE_GROUP_MISSING_REF = 39;
   public static final int CANNOT_EXTEND_ALL = 42;
   public static final int INVALID_SCHEMA = 46;
   public static final int DUPLICATE_GLOBAL_ELEMENT = 47;
   public static final int DUPLICATE_GLOBAL_ATTRIBUTE = 48;
   public static final int DUPLICATE_GLOBAL_TYPE = 49;
   public static final int MALFORMED_SIMPLE_TYPE_DEFN = 52;
   public static final int INVALID_NAME = 53;
   public static final int CANNOT_DERIVE_FINAL = 54;
   public static final int IDC_NOT_FOUND = 55;
   public static final int NONDETERMINISTIC_MODEL = 57;
   public static final int XPATH_COMPILATION_FAILURE = 58;
   public static final int DUPLICATE_IDENTITY_CONSTRAINT = 59;
   public static final int ILLEGAL_RESTRICTION = 45;
   public static final int INCONSISTENT_TYPE = 50;
   public static final int UNSUPPORTED_FEATURE = 51;
   public static final int GENERIC_ERROR = 60;
   public static final String INVALID_DOCUMENT_TYPE = "invalid.document.type";
   public static final String CANNOT_LOAD_FILE = "cannot.load.file";
   public static final String EXCEPTION_EXCEEDED_ENTITY_BYTES = "exceeded-entity-bytes";
   public static final String EXCEPTION_EXCEEDED_TOTAL_ENTITY_BYTES = "exceeded-total-entity-bytes";
   public static final String EXCEPTION_LOADING_URL = "exception.loading.url";
   public static final String EXCEPTION_VALUE_NOT_SUPPORTED_J2S = "exception.value.not.supported.j2s";
   public static final String EXCEPTION_VALUE_NOT_SUPPORTED_S2J = "exception.value.not.supported.s2j";
   public static final String EXCEPTION_XQRL_XPATH_NOT_VALID = "exception.xqrl.xpath.not.valid";
   public static final String EXCEPTION_XQRL_EXCEPTION = "exception.xqrl.exception";
   public static final String XML_DUPLICATE_ATTRIBUTE = "uniqattspec";
   public static final String ASSESS_ATTR_SCHEMA_VALID = "cvc-assess-attr";
   public static final String ASSESS_ATTR_SCHEMA_VALID$NOT_RESOLVED = "cvc-assess-attr.1.2";
   public static final String ASSESS_ELEM_SCHEMA_VALID = "cvc-assess-elt";
   public static final String ASSESS_ELEM_SCHEMA_VALID$NOT_RESOLVED = "cvc-assess-elt.1.1.1.3.2";
   public static final String ATTR_LOCALLY_VALID = "cvc-attribute";
   public static final String ATTR_LOCALLY_VALID$NO_TYPE = "cvc-attribute.1";
   public static final String ATTR_LOCALLY_VALID$FIXED = "cvc-attribute.4";
   public static final String ATTR_USE_LOCALLY_VALID = "cvc-au";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID = "cvc-complex-type";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$EMPTY_WITH_CONTENT = "cvc-complex-type.2.1";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$SIMPLE_TYPE_WITH_CONTENT = "cvc-complex-type.2.2";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$EXPECTED_DIFFERENT_ELEMENT = "cvc-complex-type.2.4a";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$ELEMENT_NOT_ALLOWED = "cvc-complex-type.2.4b";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$MISSING_ELEMENT = "cvc-complex-type.2.4c";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$EXPECTED_ELEMENT = "cvc-complex-type.2.4d";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$ELEMENT_ONLY_WITH_TEXT = "cvc-complex-type.2.3";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$ATTRIBUTE_VALID = "cvc-complex-type.3.1";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$NO_WILDCARD = "cvc-complex-type.3.2.1";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$NOT_WILDCARD_VALID = "cvc-complex-type.3.2.2";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$MISSING_REQUIRED_ATTRIBUTE = "cvc-complex-type.4";
   public static final String DATATYPE_VALID = "cvc-datatype-valid";
   public static final String DATATYPE_VALID$PATTERN_VALID = "cvc-datatype-valid.1.1";
   public static final String DATATYPE_VALID$PATTERN_VALID$NO_VALUE = "cvc-datatype-valid.1.1b";
   public static final String DATATYPE_VALID$UNION = "cvc-datatype-valid.1.2.3";
   public static final String ELEM_LOCALLY_VALID = "cvc-elt";
   public static final String ELEM_LOCALLY_VALID$NO_TYPE = "cvc-elt.1";
   public static final String ELEM_LOCALLY_VALID$ABSTRACT = "cvc-elt.2";
   public static final String ELEM_LOCALLY_VALID$NOT_NILLABLE = "cvc-elt.3.1";
   public static final String ELEM_LOCALLY_VALID$NIL_WITH_CONTENT = "cvc-elt.3.2.1";
   public static final String ELEM_LOCALLY_VALID$NIL_WITH_FIXED = "cvc-elt.3.2.2";
   public static final String ELEM_LOCALLY_VALID$XSI_TYPE_INVALID_QNAME = "cvc-elt.4.1";
   public static final String ELEM_LOCALLY_VALID$XSI_TYPE_NOT_FOUND = "cvc-elt.4.2";
   public static final String ELEM_LOCALLY_VALID$XSI_TYPE_NOT_DERIVED = "cvc-elt.4.3a";
   public static final String ELEM_LOCALLY_VALID$XSI_TYPE_BLOCK_EXTENSION = "cvc-elt.4.3b";
   public static final String ELEM_LOCALLY_VALID$XSI_TYPE_BLOCK_RESTRICTION = "cvc-elt.4.3c";
   public static final String ELEM_LOCALLY_VALID$FIXED_WITH_CONTENT = "cvc-elt.5.2.2.1";
   public static final String ELEM_LOCALLY_VALID$FIXED_VALID_MIXED_CONTENT = "cvc-elt.5.2.2.2.1";
   public static final String ELEM_LOCALLY_VALID$FIXED_VALID_SIMPLE_TYPE = "cvc-elt.5.2.2.2.2";
   public static final String ELEM_LOCALLY_VALID$XSI_TYPE_PROHIBITED_SUBST = "cvc-elt.4.3d";
   public static final String DATATYPE_ENUM_VALID = "cvc-enumeration-valid";
   public static final String DATATYPE_ENUM_VALID$NO_VALUE = "cvc-enumeration-valid.b";
   public static final String DATATYPE_FACET_VALID = "cvc-facet-valid";
   public static final String DATATYPE_FRACTION_DIGITS_VALID = "cvc-fractionDigits-valid";
   public static final String ID_VALID$DUPLICATE = "cvc-id.2";
   public static final String IDENTITY_CONSTRAINT_VALID = "cvc-identity-constraint";
   public static final String IDENTITY_CONSTRAINT_VALID$DUPLICATE_UNIQUE = "cvc-identity-constraint.4.1";
   public static final String IDENTITY_CONSTRAINT_VALID$DUPLICATE_KEY = "cvc-identity-constraint.4.2.2";
   public static final String IDENTITY_CONSTRAINT_VALID$KEYREF_KEY_NOT_FOUND = "cvc-identity-constraint.4.3";
   public static final String DATATYPE_LENGTH_VALID = "cvc-length-valid";
   public static final String DATATYPE_LENGTH_VALID$STRING = "cvc-length-valid.1.1";
   public static final String DATATYPE_LENGTH_VALID$BINARY = "cvc-length-valid.1.2";
   public static final String DATATYPE_LENGTH_VALID$LIST_LENGTH = "cvc-length-valid.2";
   public static final String DATATYPE_MAX_EXCLUSIVE_VALID = "cvc-maxExclusive-valid";
   public static final String DATATYPE_MAX_INCLUSIVE_VALID = "cvc-maxInclusive-valid";
   public static final String DATATYPE_MAX_LENGTH_VALID = "cvc-maxLength-valid";
   public static final String DATATYPE_MAX_LENGTH_VALID$STRING = "cvc-maxLength-valid.1.1";
   public static final String DATATYPE_MAX_LENGTH_VALID$BINARY = "cvc-maxLength-valid.1.2";
   public static final String DATATYPE_MAX_LENGTH_VALID$LIST_LENGTH = "cvc-maxLength-valid.2";
   public static final String DATATYPE_MIN_EXCLUSIVE_VALID = "cvc-minExclusive-valid";
   public static final String DATATYPE_MIN_INCLUSIVE_VALID = "cvc-minInclusive-valid";
   public static final String DATATYPE_MIN_LENGTH_VALID = "cvc-minLength-valid";
   public static final String DATATYPE_MIN_LENGTH_VALID$STRING = "cvc-minLength-valid.1.1";
   public static final String DATATYPE_MIN_LENGTH_VALID$BINARY = "cvc-minLength-valid.1.2";
   public static final String DATATYPE_MIN_LENGTH_VALID$LIST_LENGTH = "cvc-minLength-valid.2";
   public static final String MODEL_GROUP_VALID = "cvc-model-group";
   public static final String PARTICLE_VALID = "cvc-particle";
   public static final String PARTICLE_VALID$NOT_WILDCARD_VALID = "cvc-particle.1.3";
   public static final String PARTICLE_VALID$BLOCK_SUBSTITUTION = "cvc-particle.2.3.3a";
   public static final String DATATYPE_PATTERN_VALID = "cvc-pattern-valid";
   public static final String RESOLVE_QNAME_INSTANCE_VALID = "cvc-resolve-instance";
   public static final String SIMPLE_TYPE_STRING_VALID = "cvc-simple-type";
   public static final String DATATYPE_TOTAL_DIGITS_VALID = "cvc-totalDigits-valid";
   public static final String ELEM_TYPE_LOCALLY_VALID = "cvc-type";
   public static final String ELEM_TYPE_LOCALLY_VALID$ABSTRACT = "cvc-type.2";
   public static final String WILDCARD_ITEM_VALID = "cvc-wildcard";
   public static final String WILDCARD_NAMESPACE_NAME_VALID = "cvc-wildcard-namespace";
   public static final String SCHEMA_REFERENCE = "schema_reference";
   public static final String SCHEMA_ANNOTATION = "src-annotation";
   public static final String SCHEMA_ATTR = "src-attribute";
   public static final String SCHEMA_ATTR$DEFAULT_OR_FIXED = "src-attribute.1";
   public static final String SCHEMA_ATTR$FIXED_NOT_MATCH = "au-value_constraint";
   public static final String SCHEMA_ATTR$DEFAULT_AND_USE_OPTIONAL = "src-attribute.2";
   public static final String SCHEMA_ATTR$REF_OR_NAME_HAS_BOTH = "src-attribute.3.1a";
   public static final String SCHEMA_ATTR$REF_OR_NAME_HAS_NEITHER = "src-attribute.3.1b";
   public static final String SCHEMA_ATTR$REF_FEATURES = "src-attribute.3.2";
   public static final String SCHEMA_ATTR$TYPE_ATTR_OR_NESTED_TYPE = "src-attribute.4";
   public static final String SCHEMA_ATTR_GROUP = "src-attribute_group";
   public static final String SCHEMA_ATTR_GROUP$SELF_REF = "src-attribute_group.3";
   public static final String SCHEMA_COMPLEX_TYPE = "src-ct";
   public static final String SCHEMA_COMPLEX_TYPE$COMPLEX_CONTENT = "src-ct.1";
   public static final String SCHEMA_COMPLEX_TYPE$SIMPLE_CONTENT = "src-ct.2";
   public static final String SCHEMA_ELEM = "src-element";
   public static final String SCHEMA_ELEM$DEFAULT_OR_FIXED = "src-element.1";
   public static final String SCHEMA_ELEM$REF_OR_NAME_HAS_BOTH = "src-element.2.1a";
   public static final String SCHEMA_ELEM$REF_OR_NAME_HAS_NEITHER = "src-element.2.1b";
   public static final String SCHEMA_ELEM$REF_FEATURES = "src-element.2.2";
   public static final String SCHEMA_ELEM$TYPE_ATTR_OR_NESTED_TYPE = "src-element.3";
   public static final String SCHEMA_REDEFINITION = "src-expredef";
   public static final String SCHEMA_IDENTITY_CONSTRAINT = "src-identity-constraint";
   public static final String SCHEMA_IMPORT = "src-import";
   public static final String SCHEMA_INCLUDE = "src-include";
   public static final String SCHEMA_SIMPLE_TYPE$LIST_ITEM_TYPE_OR_SIMPLE_TYPE = "src-list-itemType-or-simpleType";
   public static final String SCHEMA_MODEL_GROUP = "src-model_group";
   public static final String SCHEMA_MODEL_GROUP_DEFN = "src-model_group_defn";
   public static final String SCHEMA_MULTIPLE_ENUMS = "src-multiple-enumerations";
   public static final String SCHEMA_MULTIPLE_PATTERNS = "src-multiple-patterns";
   public static final String SCHEMA_NOTATION = "src-notation";
   public static final String SCHEMA_QNAME = "src-qname";
   public static final String SCHEMA_REDEFINE = "src-redefine";
   public static final String SCHEMA_REDEFINE$EXTEND_OR_RESTRICT = "src-redefine.5a";
   public static final String SCHEMA_REDEFINE$SAME_TYPE = "src-redefine.5b";
   public static final String SCHEMA_REDEFINE$GROUP_SELF_REF = "src-redefine.6.1.1";
   public static final String SCHEMA_REDEFINE$GROUP_SELF_REF_MIN_MAX_1 = "src-redefine.6.1.2";
   public static final String SCHEMA_REDEFINE$ATTR_GROUP_SELF_REF = "src-redefine.7.1";
   public static final String SCHEMA_QNAME_RESOLVE = "src-resolve";
   public static final String SCHEMA_QNAME_RESOLVE$HELP = "src-resolve.a";
   public static final String SCHEMA_SIMPLE_TYPE$RESTICTION_HAS_BASE_OR_SIMPLE_TYPE = "src-restriction-base-or-simpleType";
   public static final String SCHEMA_SIMPLE_TYPE = "src-simple-type";
   public static final String SCHEMA_SIMPLE_TYPE$RESTRICTION_HAS_BOTH_BASE_OR_SIMPLE_TYPE = "src-simple-type.2a";
   public static final String SCHEMA_SIMPLE_TYPE$RESTRICTION_HAS_NEITHER_BASE_OR_SIMPLE_TYPE = "src-simple-type.2b";
   public static final String SCHEMA_SIMPLE_TYPE$LIST_HAS_BOTH_ITEM_OR_SIMPLE_TYPE = "src-simple-type.3a";
   public static final String SCHEMA_SIMPLE_TYPE$LIST_HAS_NEITHER_ITEM_OR_SIMPLE_TYPE = "src-simple-type.3b";
   public static final String SCHEMA_SIMPLE_TYPE$CYCLIC_UNION = "src-simple-type.4";
   public static final String DATATYPE_SINGLE_FACET_VALUE = "src-single-facet-value";
   public static final String SCHEMA_SIMPLE_TYPE$UNION_HAS_MEMBER_TYPES_OR_SIMPLE_TYPES = "src-union-memberTypes-or-simpleTypes";
   public static final String SCHEMA_WILDCARD = "src-wildcard";
   public static final String ATTR_PROPERTIES = "a-props-correct";
   public static final String ATTR_PROPERTIES$CONSTRAINT_VALID = "a-props-correct.2";
   public static final String ATTR_PROPERTIES$ID_FIXED_OR_DEFAULT = "a-props-correct.3";
   public static final String ATTR_GROUP_PROPERTIES = "ag-props-correct";
   public static final String ATTR_GROUP_PROPERTIES$2 = "ag-props-correct.2";
   public static final String ATTR_GROUP_PROPERTIES$TWO_IDS = "ag-props-correct.3";
   public static final String ANNO_PROPERTIES = "an-props-correct";
   public static final String ATTR_USE_PROPERTIES = "au-props-correct";
   public static final String FIELDS_XPATH = "c-fields-xpaths";
   public static final String IDENTITY_CONSTRAINT_PROPERTIES = "c-props-correct";
   public static final String IDENTITY_CONSTRAINT_PROPERTIES$KEYREF_REFERS_TO_KEYREF = "c-props-correct.1";
   public static final String IDENTITY_CONSTRAINT_PROPERTIES$KEY_KEYREF_FIELD_COUNT_EQ = "c-props-correct.2";
   public static final String SELECTOR_XPATH = "c-selector-xpath";
   public static final String ALL_GROUP_LIMITED = "cos-all-limited";
   public static final String ALL_GROUP_LIMITED$IN_MIN_MAX_1_PARTICLE = "cos-all-limited.1.2a";
   public static final String ALL_GROUP_LIMITED$IN_COMPLEX_TYPE_DEF_PARTICLE = "cos-all-limited.1.2b";
   public static final String ALL_GROUP_LIMITED$CHILD_PARTICLES_MAX_LTE_1 = "cos-all-limited.2";
   public static final String FACETS_APPLICABLE = "cos-applicable-facets";
   public static final String FACETS_DEPRECATED_NOTATION = "notation-facets";
   public static final String ATTR_WILDCARD_INTERSECT = "cos-aw-intersect";
   public static final String ATTR_WILDCARD_UNION = "cos-aw-union";
   public static final String ATTR_NOTATION_TYPE_FORBIDDEN = "enumeration-required-notation-attr";
   public static final String ATTR_COMPATIBILITY_TARGETNS = "notation-targetns-attr";
   public static final String CHOICE_RANGE = "cos-choice-range";
   public static final String COMPLEX_TYPE_DERIVATION = "cos-ct-derived-ok";
   public static final String COMPLEX_TYPE_EXTENSION = "cos-ct-extends";
   public static final String COMPLEX_TYPE_EXTENSION$EXTENDING_SIMPLE_CONTENT = "cos-ct-extends.1.4.1";
   public static final String COMPLEX_TYPE_EXTENSION$BOTH_ELEMEMENT_OR_MIXED = "cos-ct-extends.1.4.2.2";
   public static final String COMPLEX_TYPE_EXTENSION$FINAL = "cos-ct-extends.1.1";
   public static final String ELEM_CONSISTANT = "cos-element-consistent";
   public static final String SUBST_GROUP = "cos-equiv-class";
   public static final String SUBST_GROUP_DERIVED = "cos-equiv-derived-ok-rec";
   public static final String PARTICLE_EMPTIABLE = "cos-group-emptiable";
   public static final String DATATYPE_LIST_OF_ATOMIC = "cos-list-of-atomic";
   public static final String DATATYPE_CIRCULAR_UNION = "cos-no-circular-unions";
   public static final String UNIQUE_PARTICLE_ATTRIBUTION = "cos-nonambig";
   public static final String WILDCARD_SUBSET = "cos-ns-subset";
   public static final String PARTICLE_EXTENSION = "cos-particle-extend";
   public static final String PARTICLE_RESTRICTION = "cos-particle-restrict";
   public static final String PARTICLE_RESTRICTION$INVALID_RESTRICTION = "cos-particle-restrict.2";
   public static final String SEQUENCE_RANGE = "cos-seq-range";
   public static final String SIMPLE_TYPE_DERIVATION = "cos-st-derived-ok";
   public static final String SIMPLE_TYPE_RESTRICTION = "cos-st-restricts";
   public static final String SIMPLE_TYPE_RESTRICTION$ATOMIC_NOT_SIMPLE = "cos-st-restricts.1.1";
   public static final String SIMPLE_TYPE_RESTRICTION$LIST_ITEM_NOT_SIMPLE = "cos-st-restricts.2.1a";
   public static final String SIMPLE_TYPE_RESTRICTION$LIST_ITEM_IS_LIST = "cos-st-restricts.2.1b";
   public static final String SIMPLE_TYPE_RESTRICTION$LIST_ITEM_IS_UNION_OF_LIST = "cos-st-restricts.2.1c";
   public static final String SIMPLE_TYPE_RESTRICTION$UNION_MEMBER_NOT_SIMPLE = "cos-st-restricts.3.1";
   public static final String ELEM_DEFAULT_VALID = "cos-valid-default";
   public static final String ELEM_DEFAULT_VALID$SIMPLE_TYPE_OR_MIXED = "cos-valid-default.2.1";
   public static final String ELEM_DEFAULT_VALID$MIXED_AND_EMPTIABLE = "cos-valid-default.2.2.2";
   public static final String ELEM_COMPATIBILITY_TYPE = "id-idref-idrefs-entity-entities-notation";
   public static final String ELEM_COMPATIBILITY_TARGETNS = "notation-targetns-elem";
   public static final String COMPLEX_TYPE_PROPERTIES = "ct-props-correct";
   public static final String COMPLEX_TYPE_PROPERTIES$SIMPLE_TYPE_EXTENSION = "ct-props-correct.2";
   public static final String COMPLEX_TYPE_PROPERTIES$DUPLICATE_ATTRIBUTE = "ct-props-correct.4";
   public static final String COMPLEX_TYPE_RESTRICTION = "derivation-ok-restriction";
   public static final String COMPLEX_TYPE_RESTRICTION$FINAL = "derivation-ok-restriction.1";
   public static final String COMPLEX_TYPE_RESTRICTION$ATTR_REQUIRED = "derivation-ok-restriction.2.1.1";
   public static final String COMPLEX_TYPE_RESTRICTION$ATTR_IN_BASE_WILDCARD_SET = "derivation-ok-restriction.2.2";
   public static final String COMPLEX_TYPE_RESTRICTION$BASE_HAS_ATTR_WILDCARD = "derivation-ok-restriction.4.1";
   public static final String COMPLEX_TYPE_RESTRICTION$ATTR_WILDCARD_SUBSET = "derivation-ok-restriction.4.2";
   public static final String COMPLEX_TYPE_RESTRICTION$SC_AND_SIMPLE_TYPE_OR_MIXED = "derivation-ok-restriction.5.1";
   public static final String COMPLEX_TYPE_RESTRICTION$SC_AND_MIXED_EMPTIABLE = "derivation-ok-restriction.5.1.2";
   public static final String COMPLEX_TYPE_RESTRICTION$SC_NOT_DERIVED = "derivation-ok-restriction.5.2.2.1";
   public static final String COMPLEX_TYPE_RESTRICTION$EMPTY_AND_NOT_SIMPLE = "derivation-ok-restriction.5.2";
   public static final String COMPLEX_TYPE_RESTRICTION$EMPTY_AND_ELEMENT_OR_MIXED_EMPTIABLE = "derivation-ok-restriction.5.2.2";
   public static final String COMPLEX_TYPE_RESTRICTION$ELEMENT_OR_MIXED_AND_VALID = "derivation-ok-restriction.5.3";
   public static final String COMPLEX_TYPE_RESTRICTION$ELEMENT_OR_MIXED_AND_MIXED = "derivation-ok-restriction.5.3a";
   public static final String COMPLEX_TYPE_RESTRICTION$ELEMENT_OR_MIXED_AND_EMPTY = "derivation-ok-restriction.5.3b";
   public static final String COMPLEX_TYPE_RESTRICTION$ELEMENT_OR_MIXED_AND_SIMPLE = "derivation-ok-restriction.5.3c";
   public static final String ELEM_PROPERTIES = "e-props-correct";
   public static final String ELEM_PROPERTIES$CONSTRAINT_VALID = "e-props-correct.2";
   public static final String ELEM_PROPERTIES$SUBSTITUTION_VALID = "e-props-correct.4";
   public static final String ELEM_PROPERTIES$SUBSTITUTION_FINAL = "e-props-correct.4a";
   public static final String ELEM_NOTATION_TYPE_FORBIDDEN = "enumeration-required-notation-elem";
   public static final String DATATYPE_ENUM_NOTATION = "enumeration-required-notation";
   public static final String DATATYPE_ENUM_RESTRICTION = "enumeration-valid-restriction";
   public static final String DATATYPE_FRACTION_DIGITS_LE_TOTAL_DIGITS = "fractionDigits-totalDigits";
   public static final String DATATYPE_FRACTION_DIGITS_RESTRICTION = "fractionDigits-valid-restriction";
   public static final String DATATYPE_LENGTH = "length-minLength-maxLength";
   public static final String DATATYPE_LENGTH_RESTRICTION = "length-valid-restriction";
   public static final String DATATYPE_MAX_EXCLUSIVE_RESTRICTION = "maxExclusive-valid-restriction";
   public static final String DATATYPE_MIN_INCLUSIVE_MAX_EXCLUSIVE = "maxInclusive-maxExclusive";
   public static final String DATATYPE_MAX_INCLUSIVE_RESTRICTION = "maxInclusive-valid-restriction";
   public static final String DATATYPE_MAX_LENGTH_RESTRICTION = "maxLength-valid-restriction";
   public static final String MODEL_GROUP_PROPERTIES = "mg-props-correct";
   public static final String MODEL_GROUP_PROPERTIES$CIRCULAR = "mg-props-correct.2";
   public static final String MODEL_GROUP_DEFN_PROPERTIES = "mgd-props-correct";
   public static final String DATATYPE_MIN_EXCLUSIVE_LE_MAX_EXCLUSIVE = "minExclusive-less-than-equal-to-maxExclusive";
   public static final String DATATYPE_MIN_EXCLUSIVE_LE_MAX_INCLUSIVE = "minExclusive-less-than-maxInclusive";
   public static final String DATATYPE_MIN_EXCLUSIVE_RESTRICTION = "minExclusive-valid-restriction";
   public static final String DATATYPE_MIN_INCLUSIVE_LTE_MAX_INCLUSIVE = "minInclusive-less-than-equal-to-maxInclusive";
   public static final String DATATYPE_MIN_INCLUSIVE_LE_MAX_EXCLUSIVE = "minInclusive-less-than-maxExclusive";
   public static final String DATATYPE_MIN_INCLUSIVE_MIN_EXCLUSIVE = "minInclusive-minExclusive";
   public static final String DATATYPE_MIN_INCLUSIVE_RESTRICTION = "minInclusive-valid-restriction";
   public static final String DATATYPE_MIN_LENGTH_LE_MAX_LENGTH = "minLength-less-than-equal-to-maxLength";
   public static final String DATATYPE_MIN_LENGTH_RESTRICTION = "minLength-valid-restriction";
   public static final String NOTATION_PROPERTIES = "n-props-correct";
   public static final String NO_XMLNS = "no-xmlns";
   public static final String NO_XSI = "no-xsi";
   public static final String PARTICLE_PROPERTIES = "p-props-correct";
   public static final String PARTICLE_PROPERTIES$MIN_LTE_MAX = "p-props-correct.2.1";
   public static final String PARTICLE_PROPERTIES$MAX_GTE_1 = "p-props-correct.2.2";
   public static final String OCCURRENCE_RANGE = "range-ok";
   public static final String OCCURRENCE_RANGE$MIN_GTE_MIN = "range-ok.1";
   public static final String OCCURRENCE_RANGE$MAX_LTE_MAX = "range-ok.2";
   public static final String PARTICLE_DERIVATION_MAP_AND_SUM = "rcase-MapAndSum";
   public static final String PARTICLE_DERIVATION_MAP_AND_SUM$MAP = "rcase-MapAndSum.1";
   public static final String PARTICLE_DERIVATION_MAP_AND_SUM$SUM_MIN_OCCURS_GTE_MIN_OCCURS = "rcase-MapAndSum.2a";
   public static final String PARTICLE_DERIVATION_MAP_AND_SUM$SUM_MAX_OCCURS_LTE_MAX_OCCURS = "rcase-MapAndSum.2b";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE = "rcase-NameAndTypeOK";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE$NAME = "rcase-NameAndTypeOK.1";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE$NILLABLE = "rcase-NameAndTypeOK.2";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE$FIXED = "rcase-NameAndTypeOK.4";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE$IDENTITY_CONSTRAINTS = "rcase-NameAndTypeOK.5";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE$DISALLOWED_SUBSTITUTIONS = "rcase-NameAndTypeOK.6";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE$TYPE_VALID = "rcase-NameAndTypeOK.7a";
   public static final String PARTICLE_RESTRICTION_NAME_AND_TYPE$TYPE_RESTRICTED = "rcase-NameAndTypeOK.7b";
   public static final String PARTICLE_DERIVATION_NS_COMPAT = "rcase-NSCompat";
   public static final String PARTICLE_DERIVATION_NS_COMPAT$WILDCARD_VALID = "rcase-NSCompat.1";
   public static final String PARTICLE_DERIVATION_NS_RECURSE = "rcase-NSRecurseCheckCardinality";
   public static final String PARTICLE_DERIVATION_NS_SUBST = "rcase-NSSubset";
   public static final String PARTICLE_DERIVATION_NS_SUBST$WILDCARD_SUBSET = "rcase-NSSubset.2";
   public static final String PARTICLE_DERIVATION_RECURSE = "rcase-Recurse";
   public static final String PARTICLE_DERIVATION_RECURSE$MAP = "rcase-Recurse.2";
   public static final String PARTICLE_DERIVATION_RECURSE$MAP_VALID = "rcase-Recurse.2.1";
   public static final String PARTICLE_DERIVATION_RECURSE$UNMAPPED_ARE_EMPTIABLE = "rcase-Recurse.2.2";
   public static final String PARTICLE_DERIVATION_RECURSE_GROUP = "rcase-RecurseAsIfGroup";
   public static final String PARTICLE_DERIVATION_RECURSE_LAX = "rcase-RecurseLax";
   public static final String PARTICLE_DERIVATION_RECURSE_LAX$MAP = "rcase-RecurseLax.2";
   public static final String PARTICLE_DERIVATION_RECURSE_UNORDERED = "rcase-RecurseUnordered";
   public static final String PARTICLE_DERIVATION_RECURSE_UNORDERED$MAP = "rcase-RecurseUnordered.2";
   public static final String PARTICLE_DERIVATION_RECURSE_UNORDERED$MAP_UNIQUE = "rcase-RecurseUnordered.2.1";
   public static final String PARTICLE_DERIVATION_RECURSE_UNORDERED$MAP_VALID = "rcase-RecurseUnordered.2.2";
   public static final String PARTICLE_DERIVATION_RECURSE_UNORDERED$MAP_MAX_OCCURS_1 = "rcase-RecurseUnordered.2.2a";
   public static final String PARTICLE_DERIVATION_RECURSE_UNORDERED$UNMAPPED_ARE_EMPTIABLE = "rcase-RecurseUnordered.2.3";
   public static final String SCHEMA_PROPERTIES = "sch-props-correct";
   public static final String SCHEMA_PROPERTIES$DUPLICATE = "sch-props-correct.2";
   public static final String SIMPLE_TYPE_PROPERTIES = "st-props-correct";
   public static final String SIMPLE_TYPE_PROPERTIES$RESTRICTION_FINAL = "st-props-correct.3";
   public static final String SIMPLE_TYPE_PROPERTIES$LIST_FINAL = "st-props-correct.4.2.1";
   public static final String SIMPLE_TYPE_PROPERTIES$UNION_FINAL = "st-props-correct.4.2.2";
   public static final String SIMPLE_TYPE_RESTRICTION_FACETS = "st-restrict-facets";
   public static final String DATATYPE_TOTAL_DIGITS_RESTRICTION = "totalDigits-valid-restriction";
   public static final String WILDCARD_PROPERTIES = "w-props-correct";
   public static final String DATATYPE_WHITESPACE_RESTRICTION = "whiteSpace-valid-restriction";
   public static final String ANYURI = "anyURI";
   public static final String BASE64BINARY = "base64Binary";
   public static final String BOOLEAN = "boolean";
   public static final String DATE = "date";
   public static final String DECIMAL = "decimal";
   public static final String DOUBLE = "double";
   public static final String DURATION = "duration";
   public static final String FLOAT = "float";
   public static final String HEXBINARY = "hexBinary";
   public static final String INT = "int";
   public static final String INTEGER = "integer";
   public static final String LIST = "list";
   public static final String LONG = "long";
   public static final String NCNAME = "NCName";
   public static final String NMTOKEN = "NMTOKEN";
   public static final String QNAME = "QName";
   public static final String UNION = "union";
   public static final String ELEM_COMPLEX_TYPE_LOCALLY_VALID$PROHIBITED_ATTRIBUTE = "cvc-complex-type.prohibited-attribute";
   public static final String SOAPARRAY = "soaparray";
   public static final String FACET_FIXED = "facet-fixed";
   public static final String PATTERN_REGEX = "pattern-regex";
   public static final String MISSING_NAME = "missing-name";
   public static final String RESERVED_TYPE_NAME = "reserved-type-name";
   public static final String INVALID_VALUE = "invalid-value";
   public static final String INVALID_VALUE_DETAIL = "invalid-value-detail";
   public static final String INVALID_XPATH = "invalid-xpath";
}
