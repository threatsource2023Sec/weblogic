package org.python.icu.lang;

public interface UProperty {
   /** @deprecated */
   @Deprecated
   int UNDEFINED = -1;
   int ALPHABETIC = 0;
   int BINARY_START = 0;
   int ASCII_HEX_DIGIT = 1;
   int BIDI_CONTROL = 2;
   int BIDI_MIRRORED = 3;
   int DASH = 4;
   int DEFAULT_IGNORABLE_CODE_POINT = 5;
   int DEPRECATED = 6;
   int DIACRITIC = 7;
   int EXTENDER = 8;
   int FULL_COMPOSITION_EXCLUSION = 9;
   int GRAPHEME_BASE = 10;
   int GRAPHEME_EXTEND = 11;
   int GRAPHEME_LINK = 12;
   int HEX_DIGIT = 13;
   int HYPHEN = 14;
   int ID_CONTINUE = 15;
   int ID_START = 16;
   int IDEOGRAPHIC = 17;
   int IDS_BINARY_OPERATOR = 18;
   int IDS_TRINARY_OPERATOR = 19;
   int JOIN_CONTROL = 20;
   int LOGICAL_ORDER_EXCEPTION = 21;
   int LOWERCASE = 22;
   int MATH = 23;
   int NONCHARACTER_CODE_POINT = 24;
   int QUOTATION_MARK = 25;
   int RADICAL = 26;
   int SOFT_DOTTED = 27;
   int TERMINAL_PUNCTUATION = 28;
   int UNIFIED_IDEOGRAPH = 29;
   int UPPERCASE = 30;
   int WHITE_SPACE = 31;
   int XID_CONTINUE = 32;
   int XID_START = 33;
   int CASE_SENSITIVE = 34;
   int S_TERM = 35;
   int VARIATION_SELECTOR = 36;
   int NFD_INERT = 37;
   int NFKD_INERT = 38;
   int NFC_INERT = 39;
   int NFKC_INERT = 40;
   int SEGMENT_STARTER = 41;
   int PATTERN_SYNTAX = 42;
   int PATTERN_WHITE_SPACE = 43;
   int POSIX_ALNUM = 44;
   int POSIX_BLANK = 45;
   int POSIX_GRAPH = 46;
   int POSIX_PRINT = 47;
   int POSIX_XDIGIT = 48;
   int CASED = 49;
   int CASE_IGNORABLE = 50;
   int CHANGES_WHEN_LOWERCASED = 51;
   int CHANGES_WHEN_UPPERCASED = 52;
   int CHANGES_WHEN_TITLECASED = 53;
   int CHANGES_WHEN_CASEFOLDED = 54;
   int CHANGES_WHEN_CASEMAPPED = 55;
   int CHANGES_WHEN_NFKC_CASEFOLDED = 56;
   int EMOJI = 57;
   int EMOJI_PRESENTATION = 58;
   int EMOJI_MODIFIER = 59;
   int EMOJI_MODIFIER_BASE = 60;
   /** @deprecated */
   @Deprecated
   int BINARY_LIMIT = 61;
   int BIDI_CLASS = 4096;
   int INT_START = 4096;
   int BLOCK = 4097;
   int CANONICAL_COMBINING_CLASS = 4098;
   int DECOMPOSITION_TYPE = 4099;
   int EAST_ASIAN_WIDTH = 4100;
   int GENERAL_CATEGORY = 4101;
   int JOINING_GROUP = 4102;
   int JOINING_TYPE = 4103;
   int LINE_BREAK = 4104;
   int NUMERIC_TYPE = 4105;
   int SCRIPT = 4106;
   int HANGUL_SYLLABLE_TYPE = 4107;
   int NFD_QUICK_CHECK = 4108;
   int NFKD_QUICK_CHECK = 4109;
   int NFC_QUICK_CHECK = 4110;
   int NFKC_QUICK_CHECK = 4111;
   int LEAD_CANONICAL_COMBINING_CLASS = 4112;
   int TRAIL_CANONICAL_COMBINING_CLASS = 4113;
   int GRAPHEME_CLUSTER_BREAK = 4114;
   int SENTENCE_BREAK = 4115;
   int WORD_BREAK = 4116;
   int BIDI_PAIRED_BRACKET_TYPE = 4117;
   /** @deprecated */
   @Deprecated
   int INT_LIMIT = 4118;
   int GENERAL_CATEGORY_MASK = 8192;
   int MASK_START = 8192;
   /** @deprecated */
   @Deprecated
   int MASK_LIMIT = 8193;
   int NUMERIC_VALUE = 12288;
   int DOUBLE_START = 12288;
   /** @deprecated */
   @Deprecated
   int DOUBLE_LIMIT = 12289;
   int AGE = 16384;
   int STRING_START = 16384;
   int BIDI_MIRRORING_GLYPH = 16385;
   int CASE_FOLDING = 16386;
   /** @deprecated */
   @Deprecated
   int ISO_COMMENT = 16387;
   int LOWERCASE_MAPPING = 16388;
   int NAME = 16389;
   int SIMPLE_CASE_FOLDING = 16390;
   int SIMPLE_LOWERCASE_MAPPING = 16391;
   int SIMPLE_TITLECASE_MAPPING = 16392;
   int SIMPLE_UPPERCASE_MAPPING = 16393;
   int TITLECASE_MAPPING = 16394;
   /** @deprecated */
   @Deprecated
   int UNICODE_1_NAME = 16395;
   int UPPERCASE_MAPPING = 16396;
   int BIDI_PAIRED_BRACKET = 16397;
   /** @deprecated */
   @Deprecated
   int STRING_LIMIT = 16398;
   int SCRIPT_EXTENSIONS = 28672;
   int OTHER_PROPERTY_START = 28672;
   /** @deprecated */
   @Deprecated
   int OTHER_PROPERTY_LIMIT = 28673;

   public interface NameChoice {
      int SHORT = 0;
      int LONG = 1;
      /** @deprecated */
      @Deprecated
      int COUNT = 2;
   }
}
