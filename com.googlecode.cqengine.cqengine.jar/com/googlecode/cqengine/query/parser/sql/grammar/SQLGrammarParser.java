package com.googlecode.cqengine.query.parser.sql.grammar;

import java.util.List;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

public class SQLGrammarParser extends Parser {
   protected static final DFA[] _decisionToDFA;
   protected static final PredictionContextCache _sharedContextCache;
   public static final int STRING_LITERAL_WITH_TRAILING_PERCENT = 1;
   public static final int STRING_LITERAL_WITH_LEADING_PERCENT = 2;
   public static final int STRING_LITERAL_WITH_LEADING_AND_TRAILING_PERCENT = 3;
   public static final int BOOLEAN_LITERAL = 4;
   public static final int SCOL = 5;
   public static final int DOT = 6;
   public static final int OPEN_PAR = 7;
   public static final int CLOSE_PAR = 8;
   public static final int COMMA = 9;
   public static final int ASSIGN = 10;
   public static final int STAR = 11;
   public static final int PLUS = 12;
   public static final int MINUS = 13;
   public static final int TILDE = 14;
   public static final int PIPE2 = 15;
   public static final int DIV = 16;
   public static final int MOD = 17;
   public static final int LT2 = 18;
   public static final int GT2 = 19;
   public static final int AMP = 20;
   public static final int PIPE = 21;
   public static final int LT = 22;
   public static final int LT_EQ = 23;
   public static final int GT = 24;
   public static final int GT_EQ = 25;
   public static final int EQ = 26;
   public static final int NOT_EQ1 = 27;
   public static final int NOT_EQ2 = 28;
   public static final int K_ABORT = 29;
   public static final int K_ACTION = 30;
   public static final int K_ADD = 31;
   public static final int K_AFTER = 32;
   public static final int K_ALL = 33;
   public static final int K_ALTER = 34;
   public static final int K_ANALYZE = 35;
   public static final int K_AND = 36;
   public static final int K_AS = 37;
   public static final int K_ASC = 38;
   public static final int K_ATTACH = 39;
   public static final int K_AUTOINCREMENT = 40;
   public static final int K_BEFORE = 41;
   public static final int K_BEGIN = 42;
   public static final int K_BETWEEN = 43;
   public static final int K_BY = 44;
   public static final int K_CASCADE = 45;
   public static final int K_CASE = 46;
   public static final int K_CAST = 47;
   public static final int K_CHECK = 48;
   public static final int K_COLLATE = 49;
   public static final int K_COLUMN = 50;
   public static final int K_COMMIT = 51;
   public static final int K_CONFLICT = 52;
   public static final int K_CONSTRAINT = 53;
   public static final int K_CREATE = 54;
   public static final int K_CROSS = 55;
   public static final int K_CURRENT_DATE = 56;
   public static final int K_CURRENT_TIME = 57;
   public static final int K_CURRENT_TIMESTAMP = 58;
   public static final int K_DATABASE = 59;
   public static final int K_DEFAULT = 60;
   public static final int K_DEFERRABLE = 61;
   public static final int K_DEFERRED = 62;
   public static final int K_DELETE = 63;
   public static final int K_DESC = 64;
   public static final int K_DETACH = 65;
   public static final int K_DISTINCT = 66;
   public static final int K_DROP = 67;
   public static final int K_EACH = 68;
   public static final int K_ELSE = 69;
   public static final int K_END = 70;
   public static final int K_ESCAPE = 71;
   public static final int K_EXCEPT = 72;
   public static final int K_EXCLUSIVE = 73;
   public static final int K_EXISTS = 74;
   public static final int K_EXPLAIN = 75;
   public static final int K_FAIL = 76;
   public static final int K_FOR = 77;
   public static final int K_FOREIGN = 78;
   public static final int K_FROM = 79;
   public static final int K_FULL = 80;
   public static final int K_GLOB = 81;
   public static final int K_GROUP = 82;
   public static final int K_HAVING = 83;
   public static final int K_IF = 84;
   public static final int K_IGNORE = 85;
   public static final int K_IMMEDIATE = 86;
   public static final int K_IN = 87;
   public static final int K_INDEX = 88;
   public static final int K_INDEXED = 89;
   public static final int K_INITIALLY = 90;
   public static final int K_INNER = 91;
   public static final int K_INSERT = 92;
   public static final int K_INSTEAD = 93;
   public static final int K_INTERSECT = 94;
   public static final int K_INTO = 95;
   public static final int K_IS = 96;
   public static final int K_ISNULL = 97;
   public static final int K_JOIN = 98;
   public static final int K_KEY = 99;
   public static final int K_LEFT = 100;
   public static final int K_LIKE = 101;
   public static final int K_LIMIT = 102;
   public static final int K_MATCH = 103;
   public static final int K_NATURAL = 104;
   public static final int K_NO = 105;
   public static final int K_NOT = 106;
   public static final int K_NOTNULL = 107;
   public static final int K_NULL = 108;
   public static final int K_OF = 109;
   public static final int K_OFFSET = 110;
   public static final int K_ON = 111;
   public static final int K_OR = 112;
   public static final int K_ORDER = 113;
   public static final int K_OUTER = 114;
   public static final int K_PLAN = 115;
   public static final int K_PRAGMA = 116;
   public static final int K_PRIMARY = 117;
   public static final int K_QUERY = 118;
   public static final int K_RAISE = 119;
   public static final int K_RECURSIVE = 120;
   public static final int K_REFERENCES = 121;
   public static final int K_REGEXP = 122;
   public static final int K_REINDEX = 123;
   public static final int K_RELEASE = 124;
   public static final int K_RENAME = 125;
   public static final int K_REPLACE = 126;
   public static final int K_RESTRICT = 127;
   public static final int K_RIGHT = 128;
   public static final int K_ROLLBACK = 129;
   public static final int K_ROW = 130;
   public static final int K_SAVEPOINT = 131;
   public static final int K_SELECT = 132;
   public static final int K_SET = 133;
   public static final int K_TABLE = 134;
   public static final int K_TEMP = 135;
   public static final int K_TEMPORARY = 136;
   public static final int K_THEN = 137;
   public static final int K_TO = 138;
   public static final int K_TRANSACTION = 139;
   public static final int K_TRIGGER = 140;
   public static final int K_UNION = 141;
   public static final int K_UNIQUE = 142;
   public static final int K_UPDATE = 143;
   public static final int K_USING = 144;
   public static final int K_VACUUM = 145;
   public static final int K_VALUES = 146;
   public static final int K_VIEW = 147;
   public static final int K_VIRTUAL = 148;
   public static final int K_WHEN = 149;
   public static final int K_WHERE = 150;
   public static final int K_WITH = 151;
   public static final int K_WITHOUT = 152;
   public static final int IDENTIFIER = 153;
   public static final int NUMERIC_LITERAL = 154;
   public static final int BIND_PARAMETER = 155;
   public static final int STRING_LITERAL = 156;
   public static final int BLOB_LITERAL = 157;
   public static final int SINGLE_LINE_COMMENT = 158;
   public static final int MULTILINE_COMMENT = 159;
   public static final int SPACES = 160;
   public static final int UNEXPECTED_CHAR = 161;
   public static final int RULE_start = 0;
   public static final int RULE_indexedCollection = 1;
   public static final int RULE_whereClause = 2;
   public static final int RULE_orderByClause = 3;
   public static final int RULE_query = 4;
   public static final int RULE_logicalQuery = 5;
   public static final int RULE_andQuery = 6;
   public static final int RULE_orQuery = 7;
   public static final int RULE_notQuery = 8;
   public static final int RULE_simpleQuery = 9;
   public static final int RULE_equalQuery = 10;
   public static final int RULE_notEqualQuery = 11;
   public static final int RULE_lessThanOrEqualToQuery = 12;
   public static final int RULE_lessThanQuery = 13;
   public static final int RULE_greaterThanOrEqualToQuery = 14;
   public static final int RULE_greaterThanQuery = 15;
   public static final int RULE_betweenQuery = 16;
   public static final int RULE_notBetweenQuery = 17;
   public static final int RULE_inQuery = 18;
   public static final int RULE_notInQuery = 19;
   public static final int RULE_startsWithQuery = 20;
   public static final int RULE_endsWithQuery = 21;
   public static final int RULE_containsQuery = 22;
   public static final int RULE_hasQuery = 23;
   public static final int RULE_notHasQuery = 24;
   public static final int RULE_attributeName = 25;
   public static final int RULE_queryParameterTrailingPercent = 26;
   public static final int RULE_queryParameterLeadingPercent = 27;
   public static final int RULE_queryParameterLeadingAndTrailingPercent = 28;
   public static final int RULE_queryParameter = 29;
   public static final int RULE_attributeOrder = 30;
   public static final int RULE_direction = 31;
   public static final int RULE_parse = 32;
   public static final int RULE_error = 33;
   public static final int RULE_sql_stmt_list = 34;
   public static final int RULE_sql_stmt = 35;
   public static final int RULE_alter_table_stmt = 36;
   public static final int RULE_analyze_stmt = 37;
   public static final int RULE_attach_stmt = 38;
   public static final int RULE_begin_stmt = 39;
   public static final int RULE_commit_stmt = 40;
   public static final int RULE_compound_select_stmt = 41;
   public static final int RULE_create_index_stmt = 42;
   public static final int RULE_create_table_stmt = 43;
   public static final int RULE_create_trigger_stmt = 44;
   public static final int RULE_create_view_stmt = 45;
   public static final int RULE_create_virtual_table_stmt = 46;
   public static final int RULE_delete_stmt = 47;
   public static final int RULE_delete_stmt_limited = 48;
   public static final int RULE_detach_stmt = 49;
   public static final int RULE_drop_index_stmt = 50;
   public static final int RULE_drop_table_stmt = 51;
   public static final int RULE_drop_trigger_stmt = 52;
   public static final int RULE_drop_view_stmt = 53;
   public static final int RULE_factored_select_stmt = 54;
   public static final int RULE_insert_stmt = 55;
   public static final int RULE_pragma_stmt = 56;
   public static final int RULE_reindex_stmt = 57;
   public static final int RULE_release_stmt = 58;
   public static final int RULE_rollback_stmt = 59;
   public static final int RULE_savepoint_stmt = 60;
   public static final int RULE_simple_select_stmt = 61;
   public static final int RULE_select_stmt = 62;
   public static final int RULE_select_or_values = 63;
   public static final int RULE_update_stmt = 64;
   public static final int RULE_update_stmt_limited = 65;
   public static final int RULE_vacuum_stmt = 66;
   public static final int RULE_column_def = 67;
   public static final int RULE_type_name = 68;
   public static final int RULE_column_constraint = 69;
   public static final int RULE_conflict_clause = 70;
   public static final int RULE_expr = 71;
   public static final int RULE_foreign_key_clause = 72;
   public static final int RULE_raise_function = 73;
   public static final int RULE_indexed_column = 74;
   public static final int RULE_table_constraint = 75;
   public static final int RULE_with_clause = 76;
   public static final int RULE_qualified_table_name = 77;
   public static final int RULE_ordering_term = 78;
   public static final int RULE_pragma_value = 79;
   public static final int RULE_common_table_expression = 80;
   public static final int RULE_result_column = 81;
   public static final int RULE_table_or_subquery = 82;
   public static final int RULE_join_clause = 83;
   public static final int RULE_join_operator = 84;
   public static final int RULE_join_constraint = 85;
   public static final int RULE_select_core = 86;
   public static final int RULE_compound_operator = 87;
   public static final int RULE_cte_table_name = 88;
   public static final int RULE_signed_number = 89;
   public static final int RULE_literal_value = 90;
   public static final int RULE_unary_operator = 91;
   public static final int RULE_error_message = 92;
   public static final int RULE_module_argument = 93;
   public static final int RULE_column_alias = 94;
   public static final int RULE_keyword = 95;
   public static final int RULE_name = 96;
   public static final int RULE_function_name = 97;
   public static final int RULE_database_name = 98;
   public static final int RULE_table_name = 99;
   public static final int RULE_table_or_index_name = 100;
   public static final int RULE_new_table_name = 101;
   public static final int RULE_column_name = 102;
   public static final int RULE_collation_name = 103;
   public static final int RULE_foreign_table = 104;
   public static final int RULE_index_name = 105;
   public static final int RULE_trigger_name = 106;
   public static final int RULE_view_name = 107;
   public static final int RULE_module_name = 108;
   public static final int RULE_pragma_name = 109;
   public static final int RULE_savepoint_name = 110;
   public static final int RULE_table_alias = 111;
   public static final int RULE_transaction_name = 112;
   public static final int RULE_any_name = 113;
   public static final String[] ruleNames;
   private static final String[] _LITERAL_NAMES;
   private static final String[] _SYMBOLIC_NAMES;
   public static final Vocabulary VOCABULARY;
   /** @deprecated */
   @Deprecated
   public static final String[] tokenNames;
   public static final String _serializedATN = "\u0003悋Ꜫ脳맭䅼㯧瞆奤\u0003£߇\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0004\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013\u0004\u0014\t\u0014\u0004\u0015\t\u0015\u0004\u0016\t\u0016\u0004\u0017\t\u0017\u0004\u0018\t\u0018\u0004\u0019\t\u0019\u0004\u001a\t\u001a\u0004\u001b\t\u001b\u0004\u001c\t\u001c\u0004\u001d\t\u001d\u0004\u001e\t\u001e\u0004\u001f\t\u001f\u0004 \t \u0004!\t!\u0004\"\t\"\u0004#\t#\u0004$\t$\u0004%\t%\u0004&\t&\u0004'\t'\u0004(\t(\u0004)\t)\u0004*\t*\u0004+\t+\u0004,\t,\u0004-\t-\u0004.\t.\u0004/\t/\u00040\t0\u00041\t1\u00042\t2\u00043\t3\u00044\t4\u00045\t5\u00046\t6\u00047\t7\u00048\t8\u00049\t9\u0004:\t:\u0004;\t;\u0004<\t<\u0004=\t=\u0004>\t>\u0004?\t?\u0004@\t@\u0004A\tA\u0004B\tB\u0004C\tC\u0004D\tD\u0004E\tE\u0004F\tF\u0004G\tG\u0004H\tH\u0004I\tI\u0004J\tJ\u0004K\tK\u0004L\tL\u0004M\tM\u0004N\tN\u0004O\tO\u0004P\tP\u0004Q\tQ\u0004R\tR\u0004S\tS\u0004T\tT\u0004U\tU\u0004V\tV\u0004W\tW\u0004X\tX\u0004Y\tY\u0004Z\tZ\u0004[\t[\u0004\\\t\\\u0004]\t]\u0004^\t^\u0004_\t_\u0004`\t`\u0004a\ta\u0004b\tb\u0004c\tc\u0004d\td\u0004e\te\u0004f\tf\u0004g\tg\u0004h\th\u0004i\ti\u0004j\tj\u0004k\tk\u0004l\tl\u0004m\tm\u0004n\tn\u0004o\to\u0004p\tp\u0004q\tq\u0004r\tr\u0004s\ts\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0005\u0002ì\n\u0002\u0003\u0002\u0005\u0002ï\n\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005ý\n\u0005\f\u0005\u000e\u0005Ā\u000b\u0005\u0003\u0006\u0003\u0006\u0005\u0006Ą\n\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0005\u0007ĉ\n\u0007\u0003\b\u0003\b\u0003\b\u0003\b\u0003\b\u0003\b\u0007\bđ\n\b\f\b\u000e\bĔ\u000b\b\u0003\b\u0003\b\u0003\t\u0003\t\u0003\t\u0003\t\u0003\t\u0003\t\u0007\tĞ\n\t\f\t\u000e\tġ\u000b\t\u0003\t\u0003\t\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0005\nĬ\n\n\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0005\u000bŁ\n\u000b\u0003\f\u0003\f\u0003\f\u0003\f\u0003\r\u0003\r\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0007\u0014Ů\n\u0014\f\u0014\u000e\u0014ű\u000b\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0007\u0015ż\n\u0015\f\u0015\u000e\u0015ſ\u000b\u0015\u0003\u0015\u0003\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001d\u0003\u001d\u0003\u001e\u0003\u001e\u0003\u001f\u0003\u001f\u0003 \u0003 \u0005 Ƥ\n \u0003!\u0003!\u0003\"\u0003\"\u0007\"ƪ\n\"\f\"\u000e\"ƭ\u000b\"\u0003\"\u0003\"\u0003#\u0003#\u0003#\u0003$\u0007$Ƶ\n$\f$\u000e$Ƹ\u000b$\u0003$\u0003$\u0006$Ƽ\n$\r$\u000e$ƽ\u0003$\u0007$ǁ\n$\f$\u000e$Ǆ\u000b$\u0003$\u0007$Ǉ\n$\f$\u000e$Ǌ\u000b$\u0003%\u0003%\u0003%\u0005%Ǐ\n%\u0005%Ǒ\n%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0005%Ǳ\n%\u0003&\u0003&\u0003&\u0003&\u0003&\u0005&Ǹ\n&\u0003&\u0003&\u0003&\u0003&\u0003&\u0003&\u0005&Ȁ\n&\u0003&\u0005&ȃ\n&\u0003'\u0003'\u0003'\u0003'\u0003'\u0003'\u0003'\u0005'Ȍ\n'\u0003(\u0003(\u0005(Ȑ\n(\u0003(\u0003(\u0003(\u0003(\u0003)\u0003)\u0005)Ș\n)\u0003)\u0003)\u0005)Ȝ\n)\u0005)Ȟ\n)\u0003*\u0003*\u0003*\u0005*ȣ\n*\u0005*ȥ\n*\u0003+\u0003+\u0005+ȩ\n+\u0003+\u0003+\u0003+\u0007+Ȯ\n+\f+\u000e+ȱ\u000b+\u0005+ȳ\n+\u0003+\u0003+\u0003+\u0005+ȸ\n+\u0003+\u0003+\u0005+ȼ\n+\u0003+\u0006+ȿ\n+\r+\u000e+ɀ\u0003+\u0003+\u0003+\u0003+\u0003+\u0007+Ɉ\n+\f+\u000e+ɋ\u000b+\u0005+ɍ\n+\u0003+\u0003+\u0003+\u0003+\u0005+ɓ\n+\u0005+ɕ\n+\u0003,\u0003,\u0005,ə\n,\u0003,\u0003,\u0003,\u0003,\u0005,ɟ\n,\u0003,\u0003,\u0003,\u0005,ɤ\n,\u0003,\u0003,\u0003,\u0003,\u0003,\u0003,\u0003,\u0007,ɭ\n,\f,\u000e,ɰ\u000b,\u0003,\u0003,\u0003,\u0005,ɵ\n,\u0003-\u0003-\u0005-ɹ\n-\u0003-\u0003-\u0003-\u0003-\u0005-ɿ\n-\u0003-\u0003-\u0003-\u0005-ʄ\n-\u0003-\u0003-\u0003-\u0003-\u0003-\u0007-ʋ\n-\f-\u000e-ʎ\u000b-\u0003-\u0003-\u0007-ʒ\n-\f-\u000e-ʕ\u000b-\u0003-\u0003-\u0003-\u0005-ʚ\n-\u0003-\u0003-\u0005-ʞ\n-\u0003.\u0003.\u0005.ʢ\n.\u0003.\u0003.\u0003.\u0003.\u0005.ʨ\n.\u0003.\u0003.\u0003.\u0005.ʭ\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0005.ʴ\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0007.ʽ\n.\f.\u000e.ˀ\u000b.\u0005.˂\n.\u0005.˄\n.\u0003.\u0003.\u0003.\u0003.\u0005.ˊ\n.\u0003.\u0003.\u0003.\u0003.\u0005.ː\n.\u0003.\u0003.\u0005.˔\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0005.˛\n.\u0003.\u0003.\u0006.˟\n.\r.\u000e.ˠ\u0003.\u0003.\u0003/\u0003/\u0005/˧\n/\u0003/\u0003/\u0003/\u0003/\u0005/˭\n/\u0003/\u0003/\u0003/\u0005/˲\n/\u0003/\u0003/\u0003/\u0003/\u00030\u00030\u00030\u00030\u00030\u00030\u00050˾\n0\u00030\u00030\u00030\u00050̃\n0\u00030\u00030\u00030\u00030\u00030\u00030\u00030\u00070̌\n0\f0\u000e0̏\u000b0\u00030\u00030\u00050̓\n0\u00031\u00051̖\n1\u00031\u00031\u00031\u00031\u00031\u00051̝\n1\u00032\u00052̠\n2\u00032\u00032\u00032\u00032\u00032\u00052̧\n2\u00032\u00032\u00032\u00032\u00032\u00072̮\n2\f2\u000e2̱\u000b2\u00052̳\n2\u00032\u00032\u00032\u00032\u00052̹\n2\u00052̻\n2\u00033\u00033\u00053̿\n3\u00033\u00033\u00034\u00034\u00034\u00034\u00054͇\n4\u00034\u00034\u00034\u00054͌\n4\u00034\u00034\u00035\u00035\u00035\u00035\u00055͔\n5\u00035\u00035\u00035\u00055͙\n5\u00035\u00035\u00036\u00036\u00036\u00036\u00056͡\n6\u00036\u00036\u00036\u00056ͦ\n6\u00036\u00036\u00037\u00037\u00037\u00037\u00057ͮ\n7\u00037\u00037\u00037\u00057ͳ\n7\u00037\u00037\u00038\u00038\u00058\u0379\n8\u00038\u00038\u00038\u00078;\n8\f8\u000e8\u0381\u000b8\u00058\u0383\n8\u00038\u00038\u00038\u00038\u00078Ή\n8\f8\u000e8Ό\u000b8\u00038\u00038\u00038\u00038\u00038\u00078Γ\n8\f8\u000e8Ζ\u000b8\u00058Θ\n8\u00038\u00038\u00038\u00038\u00058Ξ\n8\u00058Π\n8\u00039\u00059Σ\n9\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00059ζ\n9\u00039\u00039\u00039\u00039\u00059μ\n9\u00039\u00039\u00039\u00039\u00039\u00079σ\n9\f9\u000e9φ\u000b9\u00039\u00039\u00059ϊ\n9\u00039\u00039\u00039\u00039\u00039\u00079ϑ\n9\f9\u000e9ϔ\u000b9\u00039\u00039\u00039\u00039\u00039\u00039\u00079Ϝ\n9\f9\u000e9ϟ\u000b9\u00039\u00039\u00079ϣ\n9\f9\u000e9Ϧ\u000b9\u00039\u00039\u00039\u00059ϫ\n9\u0003:\u0003:\u0003:\u0003:\u0005:ϱ\n:\u0003:\u0003:\u0003:\u0003:\u0003:\u0003:\u0003:\u0005:Ϻ\n:\u0003;\u0003;\u0003;\u0003;\u0003;\u0005;Ё\n;\u0003;\u0003;\u0005;Ѕ\n;\u0005;Ї\n;\u0003<\u0003<\u0005<Ћ\n<\u0003<\u0003<\u0003=\u0003=\u0003=\u0005=В\n=\u0005=Д\n=\u0003=\u0003=\u0005=И\n=\u0003=\u0005=Л\n=\u0003>\u0003>\u0003>\u0003?\u0003?\u0005?Т\n?\u0003?\u0003?\u0003?\u0007?Ч\n?\f?\u000e?Ъ\u000b?\u0005?Ь\n?\u0003?\u0003?\u0003?\u0003?\u0003?\u0003?\u0007?д\n?\f?\u000e?з\u000b?\u0005?й\n?\u0003?\u0003?\u0003?\u0003?\u0005?п\n?\u0005?с\n?\u0003@\u0003@\u0005@х\n@\u0003@\u0003@\u0003@\u0007@ъ\n@\f@\u000e@э\u000b@\u0005@я\n@\u0003@\u0003@\u0003@\u0003@\u0007@ѕ\n@\f@\u000e@ј\u000b@\u0003@\u0003@\u0003@\u0003@\u0003@\u0007@џ\n@\f@\u000e@Ѣ\u000b@\u0005@Ѥ\n@\u0003@\u0003@\u0003@\u0003@\u0005@Ѫ\n@\u0005@Ѭ\n@\u0003A\u0003A\u0005AѰ\nA\u0003A\u0003A\u0003A\u0007Aѵ\nA\fA\u000eAѸ\u000bA\u0003A\u0003A\u0003A\u0003A\u0007AѾ\nA\fA\u000eAҁ\u000bA\u0003A\u0005A҄\nA\u0005A҆\nA\u0003A\u0003A\u0005AҊ\nA\u0003A\u0003A\u0003A\u0003A\u0003A\u0007Aґ\nA\fA\u000eAҔ\u000bA\u0003A\u0003A\u0005AҘ\nA\u0005AҚ\nA\u0003A\u0003A\u0003A\u0003A\u0003A\u0007Aҡ\nA\fA\u000eAҤ\u000bA\u0003A\u0003A\u0003A\u0003A\u0003A\u0003A\u0007AҬ\nA\fA\u000eAү\u000bA\u0003A\u0003A\u0007Aҳ\nA\fA\u000eAҶ\u000bA\u0005AҸ\nA\u0003B\u0005Bһ\nB\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0005Bӈ\nB\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0007BӔ\nB\fB\u000eBӗ\u000bB\u0003B\u0003B\u0005Bӛ\nB\u0003C\u0005CӞ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0005Cӫ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0007Cӷ\nC\fC\u000eCӺ\u000bC\u0003C\u0003C\u0005CӾ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0007Cԅ\nC\fC\u000eCԈ\u000bC\u0005CԊ\nC\u0003C\u0003C\u0003C\u0003C\u0005CԐ\nC\u0005CԒ\nC\u0003D\u0003D\u0003E\u0003E\u0005EԘ\nE\u0003E\u0007Eԛ\nE\fE\u000eEԞ\u000bE\u0003F\u0006Fԡ\nF\rF\u000eFԢ\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0005Fԯ\nF\u0003G\u0003G\u0005GԳ\nG\u0003G\u0003G\u0003G\u0005GԸ\nG\u0003G\u0003G\u0005GԼ\nG\u0003G\u0005GԿ\nG\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0005GՑ\nG\u0003G\u0003G\u0003G\u0005GՖ\nG\u0003H\u0003H\u0003H\u0005H՛\nH\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iգ\nI\u0003I\u0003I\u0003I\u0005Iը\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iձ\nI\u0003I\u0003I\u0003I\u0007Iն\nI\fI\u000eIչ\u000bI\u0003I\u0005Iռ\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005I\u058c\nI\u0003I\u0005I֏\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005I֗\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0006I֞\nI\rI\u000eI֟\u0003I\u0003I\u0005I֤\nI\u0003I\u0003I\u0003I\u0005I֩\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iׇ\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iד\nI\u0003I\u0003I\u0003I\u0005Iט\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iפ\nI\u0003I\u0003I\u0003I\u0003I\u0005Iת\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iױ\nI\u0003I\u0003I\u0005I\u05f5\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0007I\u05fd\nI\fI\u000eI\u0600\u000bI\u0005I\u0602\nI\u0003I\u0003I\u0003I\u0003I\u0005I؈\nI\u0003I\u0005I؋\nI\u0007I؍\nI\fI\u000eIؐ\u000bI\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0007Jؘ\nJ\fJ\u000eJ؛\u000bJ\u0003J\u0003J\u0005J؟\nJ\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0005Jث\nJ\u0003J\u0003J\u0005Jد\nJ\u0007Jر\nJ\fJ\u000eJش\u000bJ\u0003J\u0005Jط\nJ\u0003J\u0003J\u0003J\u0003J\u0003J\u0005Jؾ\nJ\u0005Jـ\nJ\u0003K\u0003K\u0003K\u0003K\u0003K\u0003K\u0005Kو\nK\u0003K\u0003K\u0003L\u0003L\u0003L\u0005Lُ\nL\u0003L\u0005Lْ\nL\u0003M\u0003M\u0005Mٖ\nM\u0003M\u0003M\u0003M\u0005Mٛ\nM\u0003M\u0003M\u0003M\u0003M\u0007M١\nM\fM\u000eM٤\u000bM\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0007Mٴ\nM\fM\u000eMٷ\u000bM\u0003M\u0003M\u0003M\u0005Mټ\nM\u0003N\u0003N\u0005Nڀ\nN\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0007Nڎ\nN\fN\u000eNڑ\u000bN\u0003O\u0003O\u0003O\u0005Oږ\nO\u0003O\u0003O\u0003O\u0003O\u0003O\u0003O\u0005Oڞ\nO\u0003P\u0003P\u0003P\u0005Pڣ\nP\u0003P\u0005Pڦ\nP\u0003Q\u0003Q\u0003Q\u0005Qګ\nQ\u0003R\u0003R\u0003R\u0003R\u0003R\u0007Rڲ\nR\fR\u000eRڵ\u000bR\u0003R\u0003R\u0005Rڹ\nR\u0003R\u0003R\u0003R\u0003R\u0003R\u0003S\u0003S\u0003S\u0003S\u0003S\u0003S\u0003S\u0005Sۇ\nS\u0003S\u0005Sۊ\nS\u0005Sی\nS\u0003T\u0003T\u0003T\u0005Tۑ\nT\u0003T\u0003T\u0005Tە\nT\u0003T\u0005Tۘ\nT\u0003T\u0003T\u0003T\u0003T\u0003T\u0005T۟\nT\u0003T\u0003T\u0003T\u0003T\u0007Tۥ\nT\fT\u000eTۨ\u000bT\u0003T\u0005T۫\nT\u0003T\u0003T\u0005Tۯ\nT\u0003T\u0005T۲\nT\u0003T\u0003T\u0003T\u0003T\u0005T۸\nT\u0003T\u0005Tۻ\nT\u0005T۽\nT\u0003U\u0003U\u0003U\u0003U\u0003U\u0007U܄\nU\fU\u000eU܇\u000bU\u0003V\u0003V\u0005V܋\nV\u0003V\u0003V\u0005V\u070f\nV\u0003V\u0003V\u0005Vܓ\nV\u0003V\u0005Vܖ\nV\u0003W\u0003W\u0003W\u0003W\u0003W\u0003W\u0003W\u0007Wܟ\nW\fW\u000eWܢ\u000bW\u0003W\u0003W\u0005Wܦ\nW\u0003X\u0003X\u0005Xܪ\nX\u0003X\u0003X\u0003X\u0007Xܯ\nX\fX\u000eXܲ\u000bX\u0003X\u0003X\u0003X\u0003X\u0007Xܸ\nX\fX\u000eXܻ\u000bX\u0003X\u0005Xܾ\nX\u0005X݀\nX\u0003X\u0003X\u0005X݄\nX\u0003X\u0003X\u0003X\u0003X\u0003X\u0007X\u074b\nX\fX\u000eXݎ\u000bX\u0003X\u0003X\u0005Xݒ\nX\u0005Xݔ\nX\u0003X\u0003X\u0003X\u0003X\u0003X\u0007Xݛ\nX\fX\u000eXݞ\u000bX\u0003X\u0003X\u0003X\u0003X\u0003X\u0003X\u0007Xݦ\nX\fX\u000eXݩ\u000bX\u0003X\u0003X\u0007Xݭ\nX\fX\u000eXݰ\u000bX\u0005Xݲ\nX\u0003Y\u0003Y\u0003Y\u0003Y\u0003Y\u0005Yݹ\nY\u0003Z\u0003Z\u0003Z\u0003Z\u0003Z\u0007Zހ\nZ\fZ\u000eZރ\u000bZ\u0003Z\u0003Z\u0005Zއ\nZ\u0003[\u0005[ފ\n[\u0003[\u0003[\u0003\\\u0003\\\u0003]\u0003]\u0003^\u0003^\u0003_\u0003_\u0005_ޖ\n_\u0003`\u0003`\u0003a\u0003a\u0003b\u0003b\u0003c\u0003c\u0003d\u0003d\u0003e\u0003e\u0003f\u0003f\u0003g\u0003g\u0003h\u0003h\u0003i\u0003i\u0003j\u0003j\u0003k\u0003k\u0003l\u0003l\u0003m\u0003m\u0003n\u0003n\u0003o\u0003o\u0003p\u0003p\u0003q\u0003q\u0003r\u0003r\u0003s\u0003s\u0003s\u0003s\u0003s\u0003s\u0003s\u0005s߅\ns\u0003s\u0002\u0003\u0090t\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e ¢¤¦¨ª¬®°²´¶¸º¼¾ÀÂÄÆÈÊÌÎÐÒÔÖØÚÜÞàâä\u0002\u0015\u0004\u0002\u009b\u009b\u009e\u009e\u0005\u0002\u0006\u0006\u009c\u009c\u009e\u009e\u0004\u0002((BB\u0005\u0002@@KKXX\u0004\u000255HH\u0004\u0002\u000b\u000bpp\u0003\u0002\u0089\u008a\u0004\u0002##DD\u0007\u0002\u001f\u001fNNWW\u0080\u0080\u0083\u0083\u0004\u0002\r\r\u0012\u0013\u0003\u0002\u000e\u000f\u0003\u0002\u0014\u0017\u0003\u0002\u0018\u001b\u0006\u0002SSggii||\u0004\u0002AA\u0091\u0091\u0005\u0002\u001f\u001fNN\u0083\u0083\u0006\u0002:<nn\u009c\u009c\u009e\u009f\u0004\u0002\u000e\u0010ll\u0003\u0002\u001f\u009a\u0002\u08cf\u0002æ\u0003\u0002\u0002\u0002\u0004ò\u0003\u0002\u0002\u0002\u0006ô\u0003\u0002\u0002\u0002\b÷\u0003\u0002\u0002\u0002\nă\u0003\u0002\u0002\u0002\fĈ\u0003\u0002\u0002\u0002\u000eĊ\u0003\u0002\u0002\u0002\u0010ė\u0003\u0002\u0002\u0002\u0012ī\u0003\u0002\u0002\u0002\u0014ŀ\u0003\u0002\u0002\u0002\u0016ł\u0003\u0002\u0002\u0002\u0018ņ\u0003\u0002\u0002\u0002\u001aŊ\u0003\u0002\u0002\u0002\u001cŎ\u0003\u0002\u0002\u0002\u001eŒ\u0003\u0002\u0002\u0002 Ŗ\u0003\u0002\u0002\u0002\"Ś\u0003\u0002\u0002\u0002$Š\u0003\u0002\u0002\u0002&ŧ\u0003\u0002\u0002\u0002(Ŵ\u0003\u0002\u0002\u0002*Ƃ\u0003\u0002\u0002\u0002,Ɔ\u0003\u0002\u0002\u0002.Ɗ\u0003\u0002\u0002\u00020Ǝ\u0003\u0002\u0002\u00022Ɠ\u0003\u0002\u0002\u00024Ɨ\u0003\u0002\u0002\u00026ƙ\u0003\u0002\u0002\u00028ƛ\u0003\u0002\u0002\u0002:Ɲ\u0003\u0002\u0002\u0002<Ɵ\u0003\u0002\u0002\u0002>ơ\u0003\u0002\u0002\u0002@ƥ\u0003\u0002\u0002\u0002Bƫ\u0003\u0002\u0002\u0002Dư\u0003\u0002\u0002\u0002Fƶ\u0003\u0002\u0002\u0002Hǐ\u0003\u0002\u0002\u0002Jǲ\u0003\u0002\u0002\u0002LȄ\u0003\u0002\u0002\u0002Nȍ\u0003\u0002\u0002\u0002Pȕ\u0003\u0002\u0002\u0002Rȟ\u0003\u0002\u0002\u0002TȲ\u0003\u0002\u0002\u0002Vɖ\u0003\u0002\u0002\u0002Xɶ\u0003\u0002\u0002\u0002Zʟ\u0003\u0002\u0002\u0002\\ˤ\u0003\u0002\u0002\u0002^˷\u0003\u0002\u0002\u0002`̕\u0003\u0002\u0002\u0002b̟\u0003\u0002\u0002\u0002d̼\u0003\u0002\u0002\u0002f͂\u0003\u0002\u0002\u0002h͏\u0003\u0002\u0002\u0002j͜\u0003\u0002\u0002\u0002lͩ\u0003\u0002\u0002\u0002n\u0382\u0003\u0002\u0002\u0002p\u03a2\u0003\u0002\u0002\u0002rϬ\u0003\u0002\u0002\u0002tϻ\u0003\u0002\u0002\u0002vЈ\u0003\u0002\u0002\u0002xЎ\u0003\u0002\u0002\u0002zМ\u0003\u0002\u0002\u0002|Ы\u0003\u0002\u0002\u0002~ю\u0003\u0002\u0002\u0002\u0080ҷ\u0003\u0002\u0002\u0002\u0082Һ\u0003\u0002\u0002\u0002\u0084ӝ\u0003\u0002\u0002\u0002\u0086ԓ\u0003\u0002\u0002\u0002\u0088ԕ\u0003\u0002\u0002\u0002\u008aԠ\u0003\u0002\u0002\u0002\u008cԲ\u0003\u0002\u0002\u0002\u008e՚\u0003\u0002\u0002\u0002\u0090֨\u0003\u0002\u0002\u0002\u0092ؑ\u0003\u0002\u0002\u0002\u0094ف\u0003\u0002\u0002\u0002\u0096ً\u0003\u0002\u0002\u0002\u0098ٕ\u0003\u0002\u0002\u0002\u009aٽ\u0003\u0002\u0002\u0002\u009cڕ\u0003\u0002\u0002\u0002\u009eڟ\u0003\u0002\u0002\u0002 ڪ\u0003\u0002\u0002\u0002¢ڬ\u0003\u0002\u0002\u0002¤ۋ\u0003\u0002\u0002\u0002¦ۼ\u0003\u0002\u0002\u0002¨۾\u0003\u0002\u0002\u0002ªܕ\u0003\u0002\u0002\u0002¬ܥ\u0003\u0002\u0002\u0002®ݱ\u0003\u0002\u0002\u0002°ݸ\u0003\u0002\u0002\u0002²ݺ\u0003\u0002\u0002\u0002´މ\u0003\u0002\u0002\u0002¶ލ\u0003\u0002\u0002\u0002¸ޏ\u0003\u0002\u0002\u0002ºޑ\u0003\u0002\u0002\u0002¼ޕ\u0003\u0002\u0002\u0002¾ޗ\u0003\u0002\u0002\u0002Àޙ\u0003\u0002\u0002\u0002Âޛ\u0003\u0002\u0002\u0002Äޝ\u0003\u0002\u0002\u0002Æޟ\u0003\u0002\u0002\u0002Èޡ\u0003\u0002\u0002\u0002Êޣ\u0003\u0002\u0002\u0002Ìޥ\u0003\u0002\u0002\u0002Îާ\u0003\u0002\u0002\u0002Ðީ\u0003\u0002\u0002\u0002Òޫ\u0003\u0002\u0002\u0002Ôޭ\u0003\u0002\u0002\u0002Öޯ\u0003\u0002\u0002\u0002Øޱ\u0003\u0002\u0002\u0002Ú\u07b3\u0003\u0002\u0002\u0002Ü\u07b5\u0003\u0002\u0002\u0002Þ\u07b7\u0003\u0002\u0002\u0002à\u07b9\u0003\u0002\u0002\u0002â\u07bb\u0003\u0002\u0002\u0002ä߄\u0003\u0002\u0002\u0002æç\u0007\u0086\u0002\u0002çè\u0007\r\u0002\u0002èé\u0007Q\u0002\u0002éë\u0005\u0004\u0003\u0002êì\u0005\u0006\u0004\u0002ëê\u0003\u0002\u0002\u0002ëì\u0003\u0002\u0002\u0002ìî\u0003\u0002\u0002\u0002íï\u0005\b\u0005\u0002îí\u0003\u0002\u0002\u0002îï\u0003\u0002\u0002\u0002ïð\u0003\u0002\u0002\u0002ðñ\u0007\u0002\u0002\u0003ñ\u0003\u0003\u0002\u0002\u0002òó\t\u0002\u0002\u0002ó\u0005\u0003\u0002\u0002\u0002ôõ\u0007\u0098\u0002\u0002õö\u0005\n\u0006\u0002ö\u0007\u0003\u0002\u0002\u0002÷ø\u0007s\u0002\u0002øù\u0007.\u0002\u0002ùþ\u0005> \u0002úû\u0007\u000b\u0002\u0002ûý\u0005> \u0002üú\u0003\u0002\u0002\u0002ýĀ\u0003\u0002\u0002\u0002þü\u0003\u0002\u0002\u0002þÿ\u0003\u0002\u0002\u0002ÿ\t\u0003\u0002\u0002\u0002Āþ\u0003\u0002\u0002\u0002āĄ\u0005\f\u0007\u0002ĂĄ\u0005\u0014\u000b\u0002ăā\u0003\u0002\u0002\u0002ăĂ\u0003\u0002\u0002\u0002Ą\u000b\u0003\u0002\u0002\u0002ąĉ\u0005\u000e\b\u0002Ćĉ\u0005\u0010\t\u0002ćĉ\u0005\u0012\n\u0002Ĉą\u0003\u0002\u0002\u0002ĈĆ\u0003\u0002\u0002\u0002Ĉć\u0003\u0002\u0002\u0002ĉ\r\u0003\u0002\u0002\u0002Ċċ\u0007\t\u0002\u0002ċČ\u0005\n\u0006\u0002Čč\u0007&\u0002\u0002čĒ\u0005\n\u0006\u0002Ďď\u0007&\u0002\u0002ďđ\u0005\n\u0006\u0002ĐĎ\u0003\u0002\u0002\u0002đĔ\u0003\u0002\u0002\u0002ĒĐ\u0003\u0002\u0002\u0002Ēē\u0003\u0002\u0002\u0002ēĕ\u0003\u0002\u0002\u0002ĔĒ\u0003\u0002\u0002\u0002ĕĖ\u0007\n\u0002\u0002Ė\u000f\u0003\u0002\u0002\u0002ėĘ\u0007\t\u0002\u0002Ęę\u0005\n\u0006\u0002ęĚ\u0007r\u0002\u0002Ěğ\u0005\n\u0006\u0002ěĜ\u0007r\u0002\u0002ĜĞ\u0005\n\u0006\u0002ĝě\u0003\u0002\u0002\u0002Ğġ\u0003\u0002\u0002\u0002ğĝ\u0003\u0002\u0002\u0002ğĠ\u0003\u0002\u0002\u0002ĠĢ\u0003\u0002\u0002\u0002ġğ\u0003\u0002\u0002\u0002Ģģ\u0007\n\u0002\u0002ģ\u0011\u0003\u0002\u0002\u0002Ĥĥ\u0007l\u0002\u0002ĥĬ\u0005\n\u0006\u0002Ħħ\u0007\t\u0002\u0002ħĨ\u0007l\u0002\u0002Ĩĩ\u0005\n\u0006\u0002ĩĪ\u0007\n\u0002\u0002ĪĬ\u0003\u0002\u0002\u0002īĤ\u0003\u0002\u0002\u0002īĦ\u0003\u0002\u0002\u0002Ĭ\u0013\u0003\u0002\u0002\u0002ĭŁ\u0005\u0016\f\u0002ĮŁ\u0005\u0018\r\u0002įŁ\u0005\u001a\u000e\u0002İŁ\u0005\u001c\u000f\u0002ıŁ\u0005\u001e\u0010\u0002ĲŁ\u0005 \u0011\u0002ĳŁ\u0005\"\u0012\u0002ĴŁ\u0005$\u0013\u0002ĵŁ\u0005&\u0014\u0002ĶŁ\u0005(\u0015\u0002ķŁ\u0005*\u0016\u0002ĸŁ\u0005,\u0017\u0002ĹŁ\u0005.\u0018\u0002ĺŁ\u00050\u0019\u0002ĻŁ\u00052\u001a\u0002ļĽ\u0007\t\u0002\u0002Ľľ\u0005\u0014\u000b\u0002ľĿ\u0007\n\u0002\u0002ĿŁ\u0003\u0002\u0002\u0002ŀĭ\u0003\u0002\u0002\u0002ŀĮ\u0003\u0002\u0002\u0002ŀį\u0003\u0002\u0002\u0002ŀİ\u0003\u0002\u0002\u0002ŀı\u0003\u0002\u0002\u0002ŀĲ\u0003\u0002\u0002\u0002ŀĳ\u0003\u0002\u0002\u0002ŀĴ\u0003\u0002\u0002\u0002ŀĵ\u0003\u0002\u0002\u0002ŀĶ\u0003\u0002\u0002\u0002ŀķ\u0003\u0002\u0002\u0002ŀĸ\u0003\u0002\u0002\u0002ŀĹ\u0003\u0002\u0002\u0002ŀĺ\u0003\u0002\u0002\u0002ŀĻ\u0003\u0002\u0002\u0002ŀļ\u0003\u0002\u0002\u0002Ł\u0015\u0003\u0002\u0002\u0002łŃ\u00054\u001b\u0002Ńń\u0007\f\u0002\u0002ńŅ\u0005<\u001f\u0002Ņ\u0017\u0003\u0002\u0002\u0002ņŇ\u00054\u001b\u0002Ňň\u0007\u001e\u0002\u0002ňŉ\u0005<\u001f\u0002ŉ\u0019\u0003\u0002\u0002\u0002Ŋŋ\u00054\u001b\u0002ŋŌ\u0007\u0019\u0002\u0002Ōō\u0005<\u001f\u0002ō\u001b\u0003\u0002\u0002\u0002Ŏŏ\u00054\u001b\u0002ŏŐ\u0007\u0018\u0002\u0002Őő\u0005<\u001f\u0002ő\u001d\u0003\u0002\u0002\u0002Œœ\u00054\u001b\u0002œŔ\u0007\u001b\u0002\u0002Ŕŕ\u0005<\u001f\u0002ŕ\u001f\u0003\u0002\u0002\u0002Ŗŗ\u00054\u001b\u0002ŗŘ\u0007\u001a\u0002\u0002Řř\u0005<\u001f\u0002ř!\u0003\u0002\u0002\u0002Śś\u00054\u001b\u0002śŜ\u0007-\u0002\u0002Ŝŝ\u0005<\u001f\u0002ŝŞ\u0007&\u0002\u0002Şş\u0005<\u001f\u0002ş#\u0003\u0002\u0002\u0002Šš\u00054\u001b\u0002šŢ\u0007l\u0002\u0002Ţţ\u0007-\u0002\u0002ţŤ\u0005<\u001f\u0002Ťť\u0007&\u0002\u0002ťŦ\u0005<\u001f\u0002Ŧ%\u0003\u0002\u0002\u0002ŧŨ\u00054\u001b\u0002Ũũ\u0007Y\u0002\u0002ũŪ\u0007\t\u0002\u0002Ūů\u0005<\u001f\u0002ūŬ\u0007\u000b\u0002\u0002ŬŮ\u0005<\u001f\u0002ŭū\u0003\u0002\u0002\u0002Ůű\u0003\u0002\u0002\u0002ůŭ\u0003\u0002\u0002\u0002ůŰ\u0003\u0002\u0002\u0002ŰŲ\u0003\u0002\u0002\u0002űů\u0003\u0002\u0002\u0002Ųų\u0007\n\u0002\u0002ų'\u0003\u0002\u0002\u0002Ŵŵ\u00054\u001b\u0002ŵŶ\u0007l\u0002\u0002Ŷŷ\u0007Y\u0002\u0002ŷŸ\u0007\t\u0002\u0002ŸŽ\u0005<\u001f\u0002Źź\u0007\u000b\u0002\u0002źż\u0005<\u001f\u0002ŻŹ\u0003\u0002\u0002\u0002żſ\u0003\u0002\u0002\u0002ŽŻ\u0003\u0002\u0002\u0002Žž\u0003\u0002\u0002\u0002žƀ\u0003\u0002\u0002\u0002ſŽ\u0003\u0002\u0002\u0002ƀƁ\u0007\n\u0002\u0002Ɓ)\u0003\u0002\u0002\u0002Ƃƃ\u00054\u001b\u0002ƃƄ\u0007g\u0002\u0002Ƅƅ\u00056\u001c\u0002ƅ+\u0003\u0002\u0002\u0002ƆƇ\u00054\u001b\u0002Ƈƈ\u0007g\u0002\u0002ƈƉ\u00058\u001d\u0002Ɖ-\u0003\u0002\u0002\u0002ƊƋ\u00054\u001b\u0002Ƌƌ\u0007g\u0002\u0002ƌƍ\u0005:\u001e\u0002ƍ/\u0003\u0002\u0002\u0002ƎƏ\u00054\u001b\u0002ƏƐ\u0007b\u0002\u0002ƐƑ\u0007l\u0002\u0002Ƒƒ\u0007n\u0002\u0002ƒ1\u0003\u0002\u0002\u0002ƓƔ\u00054\u001b\u0002Ɣƕ\u0007b\u0002\u0002ƕƖ\u0007n\u0002\u0002Ɩ3\u0003\u0002\u0002\u0002ƗƘ\t\u0002\u0002\u0002Ƙ5\u0003\u0002\u0002\u0002ƙƚ\u0007\u0003\u0002\u0002ƚ7\u0003\u0002\u0002\u0002ƛƜ\u0007\u0004\u0002\u0002Ɯ9\u0003\u0002\u0002\u0002Ɲƞ\u0007\u0005\u0002\u0002ƞ;\u0003\u0002\u0002\u0002ƟƠ\t\u0003\u0002\u0002Ơ=\u0003\u0002\u0002\u0002ơƣ\u00054\u001b\u0002ƢƤ\u0005@!\u0002ƣƢ\u0003\u0002\u0002\u0002ƣƤ\u0003\u0002\u0002\u0002Ƥ?\u0003\u0002\u0002\u0002ƥƦ\t\u0004\u0002\u0002ƦA\u0003\u0002\u0002\u0002Ƨƪ\u0005F$\u0002ƨƪ\u0005D#\u0002ƩƧ\u0003\u0002\u0002\u0002Ʃƨ\u0003\u0002\u0002\u0002ƪƭ\u0003\u0002\u0002\u0002ƫƩ\u0003\u0002\u0002\u0002ƫƬ\u0003\u0002\u0002\u0002ƬƮ\u0003\u0002\u0002\u0002ƭƫ\u0003\u0002\u0002\u0002ƮƯ\u0007\u0002\u0002\u0003ƯC\u0003\u0002\u0002\u0002ưƱ\u0007£\u0002\u0002ƱƲ\b#\u0001\u0002ƲE\u0003\u0002\u0002\u0002ƳƵ\u0007\u0007\u0002\u0002ƴƳ\u0003\u0002\u0002\u0002ƵƸ\u0003\u0002\u0002\u0002ƶƴ\u0003\u0002\u0002\u0002ƶƷ\u0003\u0002\u0002\u0002Ʒƹ\u0003\u0002\u0002\u0002Ƹƶ\u0003\u0002\u0002\u0002ƹǂ\u0005H%\u0002ƺƼ\u0007\u0007\u0002\u0002ƻƺ\u0003\u0002\u0002\u0002Ƽƽ\u0003\u0002\u0002\u0002ƽƻ\u0003\u0002\u0002\u0002ƽƾ\u0003\u0002\u0002\u0002ƾƿ\u0003\u0002\u0002\u0002ƿǁ\u0005H%\u0002ǀƻ\u0003\u0002\u0002\u0002ǁǄ\u0003\u0002\u0002\u0002ǂǀ\u0003\u0002\u0002\u0002ǂǃ\u0003\u0002\u0002\u0002ǃǈ\u0003\u0002\u0002\u0002Ǆǂ\u0003\u0002\u0002\u0002ǅǇ\u0007\u0007\u0002\u0002ǆǅ\u0003\u0002\u0002\u0002ǇǊ\u0003\u0002\u0002\u0002ǈǆ\u0003\u0002\u0002\u0002ǈǉ\u0003\u0002\u0002\u0002ǉG\u0003\u0002\u0002\u0002Ǌǈ\u0003\u0002\u0002\u0002ǋǎ\u0007M\u0002\u0002ǌǍ\u0007x\u0002\u0002ǍǏ\u0007u\u0002\u0002ǎǌ\u0003\u0002\u0002\u0002ǎǏ\u0003\u0002\u0002\u0002ǏǑ\u0003\u0002\u0002\u0002ǐǋ\u0003\u0002\u0002\u0002ǐǑ\u0003\u0002\u0002\u0002Ǒǰ\u0003\u0002\u0002\u0002ǒǱ\u0005J&\u0002ǓǱ\u0005L'\u0002ǔǱ\u0005N(\u0002ǕǱ\u0005P)\u0002ǖǱ\u0005R*\u0002ǗǱ\u0005T+\u0002ǘǱ\u0005V,\u0002ǙǱ\u0005X-\u0002ǚǱ\u0005Z.\u0002ǛǱ\u0005\\/\u0002ǜǱ\u0005^0\u0002ǝǱ\u0005`1\u0002ǞǱ\u0005b2\u0002ǟǱ\u0005d3\u0002ǠǱ\u0005f4\u0002ǡǱ\u0005h5\u0002ǢǱ\u0005j6\u0002ǣǱ\u0005l7\u0002ǤǱ\u0005n8\u0002ǥǱ\u0005p9\u0002ǦǱ\u0005r:\u0002ǧǱ\u0005t;\u0002ǨǱ\u0005v<\u0002ǩǱ\u0005x=\u0002ǪǱ\u0005z>\u0002ǫǱ\u0005|?\u0002ǬǱ\u0005~@\u0002ǭǱ\u0005\u0082B\u0002ǮǱ\u0005\u0084C\u0002ǯǱ\u0005\u0086D\u0002ǰǒ\u0003\u0002\u0002\u0002ǰǓ\u0003\u0002\u0002\u0002ǰǔ\u0003\u0002\u0002\u0002ǰǕ\u0003\u0002\u0002\u0002ǰǖ\u0003\u0002\u0002\u0002ǰǗ\u0003\u0002\u0002\u0002ǰǘ\u0003\u0002\u0002\u0002ǰǙ\u0003\u0002\u0002\u0002ǰǚ\u0003\u0002\u0002\u0002ǰǛ\u0003\u0002\u0002\u0002ǰǜ\u0003\u0002\u0002\u0002ǰǝ\u0003\u0002\u0002\u0002ǰǞ\u0003\u0002\u0002\u0002ǰǟ\u0003\u0002\u0002\u0002ǰǠ\u0003\u0002\u0002\u0002ǰǡ\u0003\u0002\u0002\u0002ǰǢ\u0003\u0002\u0002\u0002ǰǣ\u0003\u0002\u0002\u0002ǰǤ\u0003\u0002\u0002\u0002ǰǥ\u0003\u0002\u0002\u0002ǰǦ\u0003\u0002\u0002\u0002ǰǧ\u0003\u0002\u0002\u0002ǰǨ\u0003\u0002\u0002\u0002ǰǩ\u0003\u0002\u0002\u0002ǰǪ\u0003\u0002\u0002\u0002ǰǫ\u0003\u0002\u0002\u0002ǰǬ\u0003\u0002\u0002\u0002ǰǭ\u0003\u0002\u0002\u0002ǰǮ\u0003\u0002\u0002\u0002ǰǯ\u0003\u0002\u0002\u0002ǱI\u0003\u0002\u0002\u0002ǲǳ\u0007$\u0002\u0002ǳǷ\u0007\u0088\u0002\u0002Ǵǵ\u0005Æd\u0002ǵǶ\u0007\b\u0002\u0002ǶǸ\u0003\u0002\u0002\u0002ǷǴ\u0003\u0002\u0002\u0002ǷǸ\u0003\u0002\u0002\u0002Ǹǹ\u0003\u0002\u0002\u0002ǹȂ\u0005Èe\u0002Ǻǻ\u0007\u007f\u0002\u0002ǻǼ\u0007\u008c\u0002\u0002Ǽȃ\u0005Ìg\u0002ǽǿ\u0007!\u0002\u0002ǾȀ\u00074\u0002\u0002ǿǾ\u0003\u0002\u0002\u0002ǿȀ\u0003\u0002\u0002\u0002Ȁȁ\u0003\u0002\u0002\u0002ȁȃ\u0005\u0088E\u0002ȂǺ\u0003\u0002\u0002\u0002Ȃǽ\u0003\u0002\u0002\u0002ȃK\u0003\u0002\u0002\u0002Ȅȋ\u0007%\u0002\u0002ȅȌ\u0005Æd\u0002ȆȌ\u0005Êf\u0002ȇȈ\u0005Æd\u0002Ȉȉ\u0007\b\u0002\u0002ȉȊ\u0005Êf\u0002ȊȌ\u0003\u0002\u0002\u0002ȋȅ\u0003\u0002\u0002\u0002ȋȆ\u0003\u0002\u0002\u0002ȋȇ\u0003\u0002\u0002\u0002ȋȌ\u0003\u0002\u0002\u0002ȌM\u0003\u0002\u0002\u0002ȍȏ\u0007)\u0002\u0002ȎȐ\u0007=\u0002\u0002ȏȎ\u0003\u0002\u0002\u0002ȏȐ\u0003\u0002\u0002\u0002Ȑȑ\u0003\u0002\u0002\u0002ȑȒ\u0005\u0090I\u0002Ȓȓ\u0007'\u0002\u0002ȓȔ\u0005Æd\u0002ȔO\u0003\u0002\u0002\u0002ȕȗ\u0007,\u0002\u0002ȖȘ\t\u0005\u0002\u0002ȗȖ\u0003\u0002\u0002\u0002ȗȘ\u0003\u0002\u0002\u0002Șȝ\u0003\u0002\u0002\u0002șț\u0007\u008d\u0002\u0002ȚȜ\u0005âr\u0002țȚ\u0003\u0002\u0002\u0002țȜ\u0003\u0002\u0002\u0002ȜȞ\u0003\u0002\u0002\u0002ȝș\u0003\u0002\u0002\u0002ȝȞ\u0003\u0002\u0002\u0002ȞQ\u0003\u0002\u0002\u0002ȟȤ\t\u0006\u0002\u0002ȠȢ\u0007\u008d\u0002\u0002ȡȣ\u0005âr\u0002Ȣȡ\u0003\u0002\u0002\u0002Ȣȣ\u0003\u0002\u0002\u0002ȣȥ\u0003\u0002\u0002\u0002ȤȠ\u0003\u0002\u0002\u0002Ȥȥ\u0003\u0002\u0002\u0002ȥS\u0003\u0002\u0002\u0002ȦȨ\u0007\u0099\u0002\u0002ȧȩ\u0007z\u0002\u0002Ȩȧ\u0003\u0002\u0002\u0002Ȩȩ\u0003\u0002\u0002\u0002ȩȪ\u0003\u0002\u0002\u0002Ȫȯ\u0005¢R\u0002ȫȬ\u0007\u000b\u0002\u0002ȬȮ\u0005¢R\u0002ȭȫ\u0003\u0002\u0002\u0002Ȯȱ\u0003\u0002\u0002\u0002ȯȭ\u0003\u0002\u0002\u0002ȯȰ\u0003\u0002\u0002\u0002Ȱȳ\u0003\u0002\u0002\u0002ȱȯ\u0003\u0002\u0002\u0002ȲȦ\u0003\u0002\u0002\u0002Ȳȳ\u0003\u0002\u0002\u0002ȳȴ\u0003\u0002\u0002\u0002ȴȾ\u0005®X\u0002ȵȷ\u0007\u008f\u0002\u0002ȶȸ\u0007#\u0002\u0002ȷȶ\u0003\u0002\u0002\u0002ȷȸ\u0003\u0002\u0002\u0002ȸȼ\u0003\u0002\u0002\u0002ȹȼ\u0007`\u0002\u0002Ⱥȼ\u0007J\u0002\u0002Ȼȵ\u0003\u0002\u0002\u0002Ȼȹ\u0003\u0002\u0002\u0002ȻȺ\u0003\u0002\u0002\u0002ȼȽ\u0003\u0002\u0002\u0002Ƚȿ\u0005®X\u0002ȾȻ\u0003\u0002\u0002\u0002ȿɀ\u0003\u0002\u0002\u0002ɀȾ\u0003\u0002\u0002\u0002ɀɁ\u0003\u0002\u0002\u0002ɁɌ\u0003\u0002\u0002\u0002ɂɃ\u0007s\u0002\u0002ɃɄ\u0007.\u0002\u0002Ʉɉ\u0005\u009eP\u0002ɅɆ\u0007\u000b\u0002\u0002ɆɈ\u0005\u009eP\u0002ɇɅ\u0003\u0002\u0002\u0002Ɉɋ\u0003\u0002\u0002\u0002ɉɇ\u0003\u0002\u0002\u0002ɉɊ\u0003\u0002\u0002\u0002Ɋɍ\u0003\u0002\u0002\u0002ɋɉ\u0003\u0002\u0002\u0002Ɍɂ\u0003\u0002\u0002\u0002Ɍɍ\u0003\u0002\u0002\u0002ɍɔ\u0003\u0002\u0002\u0002Ɏɏ\u0007h\u0002\u0002ɏɒ\u0005\u0090I\u0002ɐɑ\t\u0007\u0002\u0002ɑɓ\u0005\u0090I\u0002ɒɐ\u0003\u0002\u0002\u0002ɒɓ\u0003\u0002\u0002\u0002ɓɕ\u0003\u0002\u0002\u0002ɔɎ\u0003\u0002\u0002\u0002ɔɕ\u0003\u0002\u0002\u0002ɕU\u0003\u0002\u0002\u0002ɖɘ\u00078\u0002\u0002ɗə\u0007\u0090\u0002\u0002ɘɗ\u0003\u0002\u0002\u0002ɘə\u0003\u0002\u0002\u0002əɚ\u0003\u0002\u0002\u0002ɚɞ\u0007Z\u0002\u0002ɛɜ\u0007V\u0002\u0002ɜɝ\u0007l\u0002\u0002ɝɟ\u0007L\u0002\u0002ɞɛ\u0003\u0002\u0002\u0002ɞɟ\u0003\u0002\u0002\u0002ɟɣ\u0003\u0002\u0002\u0002ɠɡ\u0005Æd\u0002ɡɢ\u0007\b\u0002\u0002ɢɤ\u0003\u0002\u0002\u0002ɣɠ\u0003\u0002\u0002\u0002ɣɤ\u0003\u0002\u0002\u0002ɤɥ\u0003\u0002\u0002\u0002ɥɦ\u0005Ôk\u0002ɦɧ\u0007q\u0002\u0002ɧɨ\u0005Èe\u0002ɨɩ\u0007\t\u0002\u0002ɩɮ\u0005\u0096L\u0002ɪɫ\u0007\u000b\u0002\u0002ɫɭ\u0005\u0096L\u0002ɬɪ\u0003\u0002\u0002\u0002ɭɰ\u0003\u0002\u0002\u0002ɮɬ\u0003\u0002\u0002\u0002ɮɯ\u0003\u0002\u0002\u0002ɯɱ\u0003\u0002\u0002\u0002ɰɮ\u0003\u0002\u0002\u0002ɱɴ\u0007\n\u0002\u0002ɲɳ\u0007\u0098\u0002\u0002ɳɵ\u0005\u0090I\u0002ɴɲ\u0003\u0002\u0002\u0002ɴɵ\u0003\u0002\u0002\u0002ɵW\u0003\u0002\u0002\u0002ɶɸ\u00078\u0002\u0002ɷɹ\t\b\u0002\u0002ɸɷ\u0003\u0002\u0002\u0002ɸɹ\u0003\u0002\u0002\u0002ɹɺ\u0003\u0002\u0002\u0002ɺɾ\u0007\u0088\u0002\u0002ɻɼ\u0007V\u0002\u0002ɼɽ\u0007l\u0002\u0002ɽɿ\u0007L\u0002\u0002ɾɻ\u0003\u0002\u0002\u0002ɾɿ\u0003\u0002\u0002\u0002ɿʃ\u0003\u0002\u0002\u0002ʀʁ\u0005Æd\u0002ʁʂ\u0007\b\u0002\u0002ʂʄ\u0003\u0002\u0002\u0002ʃʀ\u0003\u0002\u0002\u0002ʃʄ\u0003\u0002\u0002\u0002ʄʅ\u0003\u0002\u0002\u0002ʅʝ\u0005Èe\u0002ʆʇ\u0007\t\u0002\u0002ʇʌ\u0005\u0088E\u0002ʈʉ\u0007\u000b\u0002\u0002ʉʋ\u0005\u0088E\u0002ʊʈ\u0003\u0002\u0002\u0002ʋʎ\u0003\u0002\u0002\u0002ʌʊ\u0003\u0002\u0002\u0002ʌʍ\u0003\u0002\u0002\u0002ʍʓ\u0003\u0002\u0002\u0002ʎʌ\u0003\u0002\u0002\u0002ʏʐ\u0007\u000b\u0002\u0002ʐʒ\u0005\u0098M\u0002ʑʏ\u0003\u0002\u0002\u0002ʒʕ\u0003\u0002\u0002\u0002ʓʑ\u0003\u0002\u0002\u0002ʓʔ\u0003\u0002\u0002\u0002ʔʖ\u0003\u0002\u0002\u0002ʕʓ\u0003\u0002\u0002\u0002ʖʙ\u0007\n\u0002\u0002ʗʘ\u0007\u009a\u0002\u0002ʘʚ\u0007\u009b\u0002\u0002ʙʗ\u0003\u0002\u0002\u0002ʙʚ\u0003\u0002\u0002\u0002ʚʞ\u0003\u0002\u0002\u0002ʛʜ\u0007'\u0002\u0002ʜʞ\u0005~@\u0002ʝʆ\u0003\u0002\u0002\u0002ʝʛ\u0003\u0002\u0002\u0002ʞY\u0003\u0002\u0002\u0002ʟʡ\u00078\u0002\u0002ʠʢ\t\b\u0002\u0002ʡʠ\u0003\u0002\u0002\u0002ʡʢ\u0003\u0002\u0002\u0002ʢʣ\u0003\u0002\u0002\u0002ʣʧ\u0007\u008e\u0002\u0002ʤʥ\u0007V\u0002\u0002ʥʦ\u0007l\u0002\u0002ʦʨ\u0007L\u0002\u0002ʧʤ\u0003\u0002\u0002\u0002ʧʨ\u0003\u0002\u0002\u0002ʨʬ\u0003\u0002\u0002\u0002ʩʪ\u0005Æd\u0002ʪʫ\u0007\b\u0002\u0002ʫʭ\u0003\u0002\u0002\u0002ʬʩ\u0003\u0002\u0002\u0002ʬʭ\u0003\u0002\u0002\u0002ʭʮ\u0003\u0002\u0002\u0002ʮʳ\u0005Öl\u0002ʯʴ\u0007+\u0002\u0002ʰʴ\u0007\"\u0002\u0002ʱʲ\u0007_\u0002\u0002ʲʴ\u0007o\u0002\u0002ʳʯ\u0003\u0002\u0002\u0002ʳʰ\u0003\u0002\u0002\u0002ʳʱ\u0003\u0002\u0002\u0002ʳʴ\u0003\u0002\u0002\u0002ʴ˃\u0003\u0002\u0002\u0002ʵ˄\u0007A\u0002\u0002ʶ˄\u0007^\u0002\u0002ʷˁ\u0007\u0091\u0002\u0002ʸʹ\u0007o\u0002\u0002ʹʾ\u0005Îh\u0002ʺʻ\u0007\u000b\u0002\u0002ʻʽ\u0005Îh\u0002ʼʺ\u0003\u0002\u0002\u0002ʽˀ\u0003\u0002\u0002\u0002ʾʼ\u0003\u0002\u0002\u0002ʾʿ\u0003\u0002\u0002\u0002ʿ˂\u0003\u0002\u0002\u0002ˀʾ\u0003\u0002\u0002\u0002ˁʸ\u0003\u0002\u0002\u0002ˁ˂\u0003\u0002\u0002\u0002˂˄\u0003\u0002\u0002\u0002˃ʵ\u0003\u0002\u0002\u0002˃ʶ\u0003\u0002\u0002\u0002˃ʷ\u0003\u0002\u0002\u0002˄˅\u0003\u0002\u0002\u0002˅ˉ\u0007q\u0002\u0002ˆˇ\u0005Æd\u0002ˇˈ\u0007\b\u0002\u0002ˈˊ\u0003\u0002\u0002\u0002ˉˆ\u0003\u0002\u0002\u0002ˉˊ\u0003\u0002\u0002\u0002ˊˋ\u0003\u0002\u0002\u0002ˋˏ\u0005Èe\u0002ˌˍ\u0007O\u0002\u0002ˍˎ\u0007F\u0002\u0002ˎː\u0007\u0084\u0002\u0002ˏˌ\u0003\u0002\u0002\u0002ˏː\u0003\u0002\u0002\u0002ː˓\u0003\u0002\u0002\u0002ˑ˒\u0007\u0097\u0002\u0002˒˔\u0005\u0090I\u0002˓ˑ\u0003\u0002\u0002\u0002˓˔\u0003\u0002\u0002\u0002˔˕\u0003\u0002\u0002\u0002˕˞\u0007,\u0002\u0002˖˛\u0005\u0082B\u0002˗˛\u0005p9\u0002˘˛\u0005`1\u0002˙˛\u0005~@\u0002˚˖\u0003\u0002\u0002\u0002˚˗\u0003\u0002\u0002\u0002˚˘\u0003\u0002\u0002\u0002˚˙\u0003\u0002\u0002\u0002˛˜\u0003\u0002\u0002\u0002˜˝\u0007\u0007\u0002\u0002˝˟\u0003\u0002\u0002\u0002˞˚\u0003\u0002\u0002\u0002˟ˠ\u0003\u0002\u0002\u0002ˠ˞\u0003\u0002\u0002\u0002ˠˡ\u0003\u0002\u0002\u0002ˡˢ\u0003\u0002\u0002\u0002ˢˣ\u0007H\u0002\u0002ˣ[\u0003\u0002\u0002\u0002ˤ˦\u00078\u0002\u0002˥˧\t\b\u0002\u0002˦˥\u0003\u0002\u0002\u0002˦˧\u0003\u0002\u0002\u0002˧˨\u0003\u0002\u0002\u0002˨ˬ\u0007\u0095\u0002\u0002˩˪\u0007V\u0002\u0002˪˫\u0007l\u0002\u0002˫˭\u0007L\u0002\u0002ˬ˩\u0003\u0002\u0002\u0002ˬ˭\u0003\u0002\u0002\u0002˭˱\u0003\u0002\u0002\u0002ˮ˯\u0005Æd\u0002˯˰\u0007\b\u0002\u0002˰˲\u0003\u0002\u0002\u0002˱ˮ\u0003\u0002\u0002\u0002˱˲\u0003\u0002\u0002\u0002˲˳\u0003\u0002\u0002\u0002˳˴\u0005Øm\u0002˴˵\u0007'\u0002\u0002˵˶\u0005~@\u0002˶]\u0003\u0002\u0002\u0002˷˸\u00078\u0002\u0002˸˹\u0007\u0096\u0002\u0002˹˽\u0007\u0088\u0002\u0002˺˻\u0007V\u0002\u0002˻˼\u0007l\u0002\u0002˼˾\u0007L\u0002\u0002˽˺\u0003\u0002\u0002\u0002˽˾\u0003\u0002\u0002\u0002˾̂\u0003\u0002\u0002\u0002˿̀\u0005Æd\u0002̀́\u0007\b\u0002\u0002́̃\u0003\u0002\u0002\u0002̂˿\u0003\u0002\u0002\u0002̂̃\u0003\u0002\u0002\u0002̃̄\u0003\u0002\u0002\u0002̄̅\u0005Èe\u0002̅̆\u0007\u0092\u0002\u0002̆̒\u0005Ún\u0002̇̈\u0007\t\u0002\u0002̈̍\u0005¼_\u0002̉̊\u0007\u000b\u0002\u0002̊̌\u0005¼_\u0002̋̉\u0003\u0002\u0002\u0002̌̏\u0003\u0002\u0002\u0002̍̋\u0003\u0002\u0002\u0002̍̎\u0003\u0002\u0002\u0002̎̐\u0003\u0002\u0002\u0002̏̍\u0003\u0002\u0002\u0002̐̑\u0007\n\u0002\u0002̑̓\u0003\u0002\u0002\u0002̒̇\u0003\u0002\u0002\u0002̒̓\u0003\u0002\u0002\u0002̓_\u0003\u0002\u0002\u0002̖̔\u0005\u009aN\u0002̔̕\u0003\u0002\u0002\u0002̖̕\u0003\u0002\u0002\u0002̖̗\u0003\u0002\u0002\u0002̗̘\u0007A\u0002\u0002̘̙\u0007Q\u0002\u0002̙̜\u0005\u009cO\u0002̛̚\u0007\u0098\u0002\u0002̛̝\u0005\u0090I\u0002̜̚\u0003\u0002\u0002\u0002̜̝\u0003\u0002\u0002\u0002̝a\u0003\u0002\u0002\u0002̞̠\u0005\u009aN\u0002̟̞\u0003\u0002\u0002\u0002̟̠\u0003\u0002\u0002\u0002̡̠\u0003\u0002\u0002\u0002̡̢\u0007A\u0002\u0002̢̣\u0007Q\u0002\u0002̣̦\u0005\u009cO\u0002̤̥\u0007\u0098\u0002\u0002̧̥\u0005\u0090I\u0002̦̤\u0003\u0002\u0002\u0002̧̦\u0003\u0002\u0002\u0002̧̺\u0003\u0002\u0002\u0002̨̩\u0007s\u0002\u0002̩̪\u0007.\u0002\u0002̪̯\u0005\u009eP\u0002̫̬\u0007\u000b\u0002\u0002̬̮\u0005\u009eP\u0002̭̫\u0003\u0002\u0002\u0002̮̱\u0003\u0002\u0002\u0002̯̭\u0003\u0002\u0002\u0002̯̰\u0003\u0002\u0002\u0002̰̳\u0003\u0002\u0002\u0002̱̯\u0003\u0002\u0002\u0002̨̲\u0003\u0002\u0002\u0002̲̳\u0003\u0002\u0002\u0002̴̳\u0003\u0002\u0002\u0002̴̵\u0007h\u0002\u0002̵̸\u0005\u0090I\u0002̶̷\t\u0007\u0002\u0002̷̹\u0005\u0090I\u0002̸̶\u0003\u0002\u0002\u0002̸̹\u0003\u0002\u0002\u0002̹̻\u0003\u0002\u0002\u0002̺̲\u0003\u0002\u0002\u0002̺̻\u0003\u0002\u0002\u0002̻c\u0003\u0002\u0002\u0002̼̾\u0007C\u0002\u0002̽̿\u0007=\u0002\u0002̾̽\u0003\u0002\u0002\u0002̾̿\u0003\u0002\u0002\u0002̿̀\u0003\u0002\u0002\u0002̀́\u0005Æd\u0002́e\u0003\u0002\u0002\u0002͂̓\u0007E\u0002\u0002̓͆\u0007Z\u0002\u0002̈́ͅ\u0007V\u0002\u0002͇ͅ\u0007L\u0002\u0002͆̈́\u0003\u0002\u0002\u0002͇͆\u0003\u0002\u0002\u0002͇͋\u0003\u0002\u0002\u0002͈͉\u0005Æd\u0002͉͊\u0007\b\u0002\u0002͊͌\u0003\u0002\u0002\u0002͈͋\u0003\u0002\u0002\u0002͋͌\u0003\u0002\u0002\u0002͍͌\u0003\u0002\u0002\u0002͍͎\u0005Ôk\u0002͎g\u0003\u0002\u0002\u0002͏͐\u0007E\u0002\u0002͓͐\u0007\u0088\u0002\u0002͑͒\u0007V\u0002\u0002͔͒\u0007L\u0002\u0002͓͑\u0003\u0002\u0002\u0002͓͔\u0003\u0002\u0002\u0002͔͘\u0003\u0002\u0002\u0002͕͖\u0005Æd\u0002͖͗\u0007\b\u0002\u0002͙͗\u0003\u0002\u0002\u0002͕͘\u0003\u0002\u0002\u0002͙͘\u0003\u0002\u0002\u0002͙͚\u0003\u0002\u0002\u0002͚͛\u0005Èe\u0002͛i\u0003\u0002\u0002\u0002͜͝\u0007E\u0002\u0002͝͠\u0007\u008e\u0002\u0002͟͞\u0007V\u0002\u0002͟͡\u0007L\u0002\u0002͠͞\u0003\u0002\u0002\u0002͠͡\u0003\u0002\u0002\u0002ͥ͡\u0003\u0002\u0002\u0002ͣ͢\u0005Æd\u0002ͣͤ\u0007\b\u0002\u0002ͤͦ\u0003\u0002\u0002\u0002ͥ͢\u0003\u0002\u0002\u0002ͥͦ\u0003\u0002\u0002\u0002ͦͧ\u0003\u0002\u0002\u0002ͧͨ\u0005Öl\u0002ͨk\u0003\u0002\u0002\u0002ͩͪ\u0007E\u0002\u0002ͪͭ\u0007\u0095\u0002\u0002ͫͬ\u0007V\u0002\u0002ͬͮ\u0007L\u0002\u0002ͭͫ\u0003\u0002\u0002\u0002ͭͮ\u0003\u0002\u0002\u0002ͮͲ\u0003\u0002\u0002\u0002ͯͰ\u0005Æd\u0002Ͱͱ\u0007\b\u0002\u0002ͱͳ\u0003\u0002\u0002\u0002Ͳͯ\u0003\u0002\u0002\u0002Ͳͳ\u0003\u0002\u0002\u0002ͳʹ\u0003\u0002\u0002\u0002ʹ͵\u0005Øm\u0002͵m\u0003\u0002\u0002\u0002Ͷ\u0378\u0007\u0099\u0002\u0002ͷ\u0379\u0007z\u0002\u0002\u0378ͷ\u0003\u0002\u0002\u0002\u0378\u0379\u0003\u0002\u0002\u0002\u0379ͺ\u0003\u0002\u0002\u0002ͺͿ\u0005¢R\u0002ͻͼ\u0007\u000b\u0002\u0002ͼ;\u0005¢R\u0002ͽͻ\u0003\u0002\u0002\u0002;\u0381\u0003\u0002\u0002\u0002Ϳͽ\u0003\u0002\u0002\u0002Ϳ\u0380\u0003\u0002\u0002\u0002\u0380\u0383\u0003\u0002\u0002\u0002\u0381Ϳ\u0003\u0002\u0002\u0002\u0382Ͷ\u0003\u0002\u0002\u0002\u0382\u0383\u0003\u0002\u0002\u0002\u0383΄\u0003\u0002\u0002\u0002΄Ί\u0005®X\u0002΅Ά\u0005°Y\u0002Ά·\u0005®X\u0002·Ή\u0003\u0002\u0002\u0002Έ΅\u0003\u0002\u0002\u0002ΉΌ\u0003\u0002\u0002\u0002ΊΈ\u0003\u0002\u0002\u0002Ί\u038b\u0003\u0002\u0002\u0002\u038bΗ\u0003\u0002\u0002\u0002ΌΊ\u0003\u0002\u0002\u0002\u038dΎ\u0007s\u0002\u0002ΎΏ\u0007.\u0002\u0002ΏΔ\u0005\u009eP\u0002ΐΑ\u0007\u000b\u0002\u0002ΑΓ\u0005\u009eP\u0002Βΐ\u0003\u0002\u0002\u0002ΓΖ\u0003\u0002\u0002\u0002ΔΒ\u0003\u0002\u0002\u0002ΔΕ\u0003\u0002\u0002\u0002ΕΘ\u0003\u0002\u0002\u0002ΖΔ\u0003\u0002\u0002\u0002Η\u038d\u0003\u0002\u0002\u0002ΗΘ\u0003\u0002\u0002\u0002ΘΟ\u0003\u0002\u0002\u0002ΙΚ\u0007h\u0002\u0002ΚΝ\u0005\u0090I\u0002ΛΜ\t\u0007\u0002\u0002ΜΞ\u0005\u0090I\u0002ΝΛ\u0003\u0002\u0002\u0002ΝΞ\u0003\u0002\u0002\u0002ΞΠ\u0003\u0002\u0002\u0002ΟΙ\u0003\u0002\u0002\u0002ΟΠ\u0003\u0002\u0002\u0002Πo\u0003\u0002\u0002\u0002ΡΣ\u0005\u009aN\u0002\u03a2Ρ\u0003\u0002\u0002\u0002\u03a2Σ\u0003\u0002\u0002\u0002Σε\u0003\u0002\u0002\u0002Τζ\u0007^\u0002\u0002Υζ\u0007\u0080\u0002\u0002ΦΧ\u0007^\u0002\u0002ΧΨ\u0007r\u0002\u0002Ψζ\u0007\u0080\u0002\u0002ΩΪ\u0007^\u0002\u0002ΪΫ\u0007r\u0002\u0002Ϋζ\u0007\u0083\u0002\u0002άέ\u0007^\u0002\u0002έή\u0007r\u0002\u0002ήζ\u0007\u001f\u0002\u0002ίΰ\u0007^\u0002\u0002ΰα\u0007r\u0002\u0002αζ\u0007N\u0002\u0002βγ\u0007^\u0002\u0002γδ\u0007r\u0002\u0002δζ\u0007W\u0002\u0002εΤ\u0003\u0002\u0002\u0002εΥ\u0003\u0002\u0002\u0002εΦ\u0003\u0002\u0002\u0002εΩ\u0003\u0002\u0002\u0002εά\u0003\u0002\u0002\u0002εί\u0003\u0002\u0002\u0002εβ\u0003\u0002\u0002\u0002ζη\u0003\u0002\u0002\u0002ηλ\u0007a\u0002\u0002θι\u0005Æd\u0002ικ\u0007\b\u0002\u0002κμ\u0003\u0002\u0002\u0002λθ\u0003\u0002\u0002\u0002λμ\u0003\u0002\u0002\u0002μν\u0003\u0002\u0002\u0002νω\u0005Èe\u0002ξο\u0007\t\u0002\u0002οτ\u0005Îh\u0002πρ\u0007\u000b\u0002\u0002ρσ\u0005Îh\u0002ςπ\u0003\u0002\u0002\u0002σφ\u0003\u0002\u0002\u0002τς\u0003\u0002\u0002\u0002τυ\u0003\u0002\u0002\u0002υχ\u0003\u0002\u0002\u0002φτ\u0003\u0002\u0002\u0002χψ\u0007\n\u0002\u0002ψϊ\u0003\u0002\u0002\u0002ωξ\u0003\u0002\u0002\u0002ωϊ\u0003\u0002\u0002\u0002ϊϪ\u0003\u0002\u0002\u0002ϋό\u0007\u0094\u0002\u0002όύ\u0007\t\u0002\u0002ύϒ\u0005\u0090I\u0002ώϏ\u0007\u000b\u0002\u0002Ϗϑ\u0005\u0090I\u0002ϐώ\u0003\u0002\u0002\u0002ϑϔ\u0003\u0002\u0002\u0002ϒϐ\u0003\u0002\u0002\u0002ϒϓ\u0003\u0002\u0002\u0002ϓϕ\u0003\u0002\u0002\u0002ϔϒ\u0003\u0002\u0002\u0002ϕϤ\u0007\n\u0002\u0002ϖϗ\u0007\u000b\u0002\u0002ϗϘ\u0007\t\u0002\u0002Ϙϝ\u0005\u0090I\u0002ϙϚ\u0007\u000b\u0002\u0002ϚϜ\u0005\u0090I\u0002ϛϙ\u0003\u0002\u0002\u0002Ϝϟ\u0003\u0002\u0002\u0002ϝϛ\u0003\u0002\u0002\u0002ϝϞ\u0003\u0002\u0002\u0002ϞϠ\u0003\u0002\u0002\u0002ϟϝ\u0003\u0002\u0002\u0002Ϡϡ\u0007\n\u0002\u0002ϡϣ\u0003\u0002\u0002\u0002Ϣϖ\u0003\u0002\u0002\u0002ϣϦ\u0003\u0002\u0002\u0002ϤϢ\u0003\u0002\u0002\u0002Ϥϥ\u0003\u0002\u0002\u0002ϥϫ\u0003\u0002\u0002\u0002ϦϤ\u0003\u0002\u0002\u0002ϧϫ\u0005~@\u0002Ϩϩ\u0007>\u0002\u0002ϩϫ\u0007\u0094\u0002\u0002Ϫϋ\u0003\u0002\u0002\u0002Ϫϧ\u0003\u0002\u0002\u0002ϪϨ\u0003\u0002\u0002\u0002ϫq\u0003\u0002\u0002\u0002Ϭϰ\u0007v\u0002\u0002ϭϮ\u0005Æd\u0002Ϯϯ\u0007\b\u0002\u0002ϯϱ\u0003\u0002\u0002\u0002ϰϭ\u0003\u0002\u0002\u0002ϰϱ\u0003\u0002\u0002\u0002ϱϲ\u0003\u0002\u0002\u0002ϲϹ\u0005Üo\u0002ϳϴ\u0007\f\u0002\u0002ϴϺ\u0005 Q\u0002ϵ϶\u0007\t\u0002\u0002϶Ϸ\u0005 Q\u0002Ϸϸ\u0007\n\u0002\u0002ϸϺ\u0003\u0002\u0002\u0002Ϲϳ\u0003\u0002\u0002\u0002Ϲϵ\u0003\u0002\u0002\u0002ϹϺ\u0003\u0002\u0002\u0002Ϻs\u0003\u0002\u0002\u0002ϻІ\u0007}\u0002\u0002ϼЇ\u0005Ði\u0002ϽϾ\u0005Æd\u0002ϾϿ\u0007\b\u0002\u0002ϿЁ\u0003\u0002\u0002\u0002ЀϽ\u0003\u0002\u0002\u0002ЀЁ\u0003\u0002\u0002\u0002ЁЄ\u0003\u0002\u0002\u0002ЂЅ\u0005Èe\u0002ЃЅ\u0005Ôk\u0002ЄЂ\u0003\u0002\u0002\u0002ЄЃ\u0003\u0002\u0002\u0002ЅЇ\u0003\u0002\u0002\u0002Іϼ\u0003\u0002\u0002\u0002ІЀ\u0003\u0002\u0002\u0002ІЇ\u0003\u0002\u0002\u0002Їu\u0003\u0002\u0002\u0002ЈЊ\u0007~\u0002\u0002ЉЋ\u0007\u0085\u0002\u0002ЊЉ\u0003\u0002\u0002\u0002ЊЋ\u0003\u0002\u0002\u0002ЋЌ\u0003\u0002\u0002\u0002ЌЍ\u0005Þp\u0002Ѝw\u0003\u0002\u0002\u0002ЎГ\u0007\u0083\u0002\u0002ЏБ\u0007\u008d\u0002\u0002АВ\u0005âr\u0002БА\u0003\u0002\u0002\u0002БВ\u0003\u0002\u0002\u0002ВД\u0003\u0002\u0002\u0002ГЏ\u0003\u0002\u0002\u0002ГД\u0003\u0002\u0002\u0002ДК\u0003\u0002\u0002\u0002ЕЗ\u0007\u008c\u0002\u0002ЖИ\u0007\u0085\u0002\u0002ЗЖ\u0003\u0002\u0002\u0002ЗИ\u0003\u0002\u0002\u0002ИЙ\u0003\u0002\u0002\u0002ЙЛ\u0005Þp\u0002КЕ\u0003\u0002\u0002\u0002КЛ\u0003\u0002\u0002\u0002Лy\u0003\u0002\u0002\u0002МН\u0007\u0085\u0002\u0002НО\u0005Þp\u0002О{\u0003\u0002\u0002\u0002ПС\u0007\u0099\u0002\u0002РТ\u0007z\u0002\u0002СР\u0003\u0002\u0002\u0002СТ\u0003\u0002\u0002\u0002ТУ\u0003\u0002\u0002\u0002УШ\u0005¢R\u0002ФХ\u0007\u000b\u0002\u0002ХЧ\u0005¢R\u0002ЦФ\u0003\u0002\u0002\u0002ЧЪ\u0003\u0002\u0002\u0002ШЦ\u0003\u0002\u0002\u0002ШЩ\u0003\u0002\u0002\u0002ЩЬ\u0003\u0002\u0002\u0002ЪШ\u0003\u0002\u0002\u0002ЫП\u0003\u0002\u0002\u0002ЫЬ\u0003\u0002\u0002\u0002ЬЭ\u0003\u0002\u0002\u0002Эи\u0005®X\u0002ЮЯ\u0007s\u0002\u0002Яа\u0007.\u0002\u0002ае\u0005\u009eP\u0002бв\u0007\u000b\u0002\u0002вд\u0005\u009eP\u0002гб\u0003\u0002\u0002\u0002дз\u0003\u0002\u0002\u0002ег\u0003\u0002\u0002\u0002еж\u0003\u0002\u0002\u0002жй\u0003\u0002\u0002\u0002зе\u0003\u0002\u0002\u0002иЮ\u0003\u0002\u0002\u0002ий\u0003\u0002\u0002\u0002йр\u0003\u0002\u0002\u0002кл\u0007h\u0002\u0002ло\u0005\u0090I\u0002мн\t\u0007\u0002\u0002нп\u0005\u0090I\u0002ом\u0003\u0002\u0002\u0002оп\u0003\u0002\u0002\u0002пс\u0003\u0002\u0002\u0002рк\u0003\u0002\u0002\u0002рс\u0003\u0002\u0002\u0002с}\u0003\u0002\u0002\u0002тф\u0007\u0099\u0002\u0002ух\u0007z\u0002\u0002фу\u0003\u0002\u0002\u0002фх\u0003\u0002\u0002\u0002хц\u0003\u0002\u0002\u0002цы\u0005¢R\u0002чш\u0007\u000b\u0002\u0002шъ\u0005¢R\u0002щч\u0003\u0002\u0002\u0002ъэ\u0003\u0002\u0002\u0002ыщ\u0003\u0002\u0002\u0002ыь\u0003\u0002\u0002\u0002ья\u0003\u0002\u0002\u0002эы\u0003\u0002\u0002\u0002ют\u0003\u0002\u0002\u0002юя\u0003\u0002\u0002\u0002яѐ\u0003\u0002\u0002\u0002ѐі\u0005\u0080A\u0002ёђ\u0005°Y\u0002ђѓ\u0005\u0080A\u0002ѓѕ\u0003\u0002\u0002\u0002єё\u0003\u0002\u0002\u0002ѕј\u0003\u0002\u0002\u0002іє\u0003\u0002\u0002\u0002ії\u0003\u0002\u0002\u0002їѣ\u0003\u0002\u0002\u0002јі\u0003\u0002\u0002\u0002љњ\u0007s\u0002\u0002њћ\u0007.\u0002\u0002ћѠ\u0005\u009eP\u0002ќѝ\u0007\u000b\u0002\u0002ѝџ\u0005\u009eP\u0002ўќ\u0003\u0002\u0002\u0002џѢ\u0003\u0002\u0002\u0002Ѡў\u0003\u0002\u0002\u0002Ѡѡ\u0003\u0002\u0002\u0002ѡѤ\u0003\u0002\u0002\u0002ѢѠ\u0003\u0002\u0002\u0002ѣљ\u0003\u0002\u0002\u0002ѣѤ\u0003\u0002\u0002\u0002Ѥѫ\u0003\u0002\u0002\u0002ѥѦ\u0007h\u0002\u0002Ѧѩ\u0005\u0090I\u0002ѧѨ\t\u0007\u0002\u0002ѨѪ\u0005\u0090I\u0002ѩѧ\u0003\u0002\u0002\u0002ѩѪ\u0003\u0002\u0002\u0002ѪѬ\u0003\u0002\u0002\u0002ѫѥ\u0003\u0002\u0002\u0002ѫѬ\u0003\u0002\u0002\u0002Ѭ\u007f\u0003\u0002\u0002\u0002ѭѯ\u0007\u0086\u0002\u0002ѮѰ\t\t\u0002\u0002ѯѮ\u0003\u0002\u0002\u0002ѯѰ\u0003\u0002\u0002\u0002Ѱѱ\u0003\u0002\u0002\u0002ѱѶ\u0005¤S\u0002Ѳѳ\u0007\u000b\u0002\u0002ѳѵ\u0005¤S\u0002ѴѲ\u0003\u0002\u0002\u0002ѵѸ\u0003\u0002\u0002\u0002ѶѴ\u0003\u0002\u0002\u0002Ѷѷ\u0003\u0002\u0002\u0002ѷ҅\u0003\u0002\u0002\u0002ѸѶ\u0003\u0002\u0002\u0002ѹ҃\u0007Q\u0002\u0002Ѻѿ\u0005¦T\u0002ѻѼ\u0007\u000b\u0002\u0002ѼѾ\u0005¦T\u0002ѽѻ\u0003\u0002\u0002\u0002Ѿҁ\u0003\u0002\u0002\u0002ѿѽ\u0003\u0002\u0002\u0002ѿҀ\u0003\u0002\u0002\u0002Ҁ҄\u0003\u0002\u0002\u0002ҁѿ\u0003\u0002\u0002\u0002҂҄\u0005¨U\u0002҃Ѻ\u0003\u0002\u0002\u0002҃҂\u0003\u0002\u0002\u0002҄҆\u0003\u0002\u0002\u0002҅ѹ\u0003\u0002\u0002\u0002҅҆\u0003\u0002\u0002\u0002҆҉\u0003\u0002\u0002\u0002҇҈\u0007\u0098\u0002\u0002҈Ҋ\u0005\u0090I\u0002҉҇\u0003\u0002\u0002\u0002҉Ҋ\u0003\u0002\u0002\u0002Ҋҙ\u0003\u0002\u0002\u0002ҋҌ\u0007T\u0002\u0002Ҍҍ\u0007.\u0002\u0002ҍҒ\u0005\u0090I\u0002Ҏҏ\u0007\u000b\u0002\u0002ҏґ\u0005\u0090I\u0002ҐҎ\u0003\u0002\u0002\u0002ґҔ\u0003\u0002\u0002\u0002ҒҐ\u0003\u0002\u0002\u0002Ғғ\u0003\u0002\u0002\u0002ғҗ\u0003\u0002\u0002\u0002ҔҒ\u0003\u0002\u0002\u0002ҕҖ\u0007U\u0002\u0002ҖҘ\u0005\u0090I\u0002җҕ\u0003\u0002\u0002\u0002җҘ\u0003\u0002\u0002\u0002ҘҚ\u0003\u0002\u0002\u0002ҙҋ\u0003\u0002\u0002\u0002ҙҚ\u0003\u0002\u0002\u0002ҚҸ\u0003\u0002\u0002\u0002қҜ\u0007\u0094\u0002\u0002Ҝҝ\u0007\t\u0002\u0002ҝҢ\u0005\u0090I\u0002Ҟҟ\u0007\u000b\u0002\u0002ҟҡ\u0005\u0090I\u0002ҠҞ\u0003\u0002\u0002\u0002ҡҤ\u0003\u0002\u0002\u0002ҢҠ\u0003\u0002\u0002\u0002Ңң\u0003\u0002\u0002\u0002ңҥ\u0003\u0002\u0002\u0002ҤҢ\u0003\u0002\u0002\u0002ҥҴ\u0007\n\u0002\u0002Ҧҧ\u0007\u000b\u0002\u0002ҧҨ\u0007\t\u0002\u0002Ҩҭ\u0005\u0090I\u0002ҩҪ\u0007\u000b\u0002\u0002ҪҬ\u0005\u0090I\u0002ҫҩ\u0003\u0002\u0002\u0002Ҭү\u0003\u0002\u0002\u0002ҭҫ\u0003\u0002\u0002\u0002ҭҮ\u0003\u0002\u0002\u0002ҮҰ\u0003\u0002\u0002\u0002үҭ\u0003\u0002\u0002\u0002Ұұ\u0007\n\u0002\u0002ұҳ\u0003\u0002\u0002\u0002ҲҦ\u0003\u0002\u0002\u0002ҳҶ\u0003\u0002\u0002\u0002ҴҲ\u0003\u0002\u0002\u0002Ҵҵ\u0003\u0002\u0002\u0002ҵҸ\u0003\u0002\u0002\u0002ҶҴ\u0003\u0002\u0002\u0002ҷѭ\u0003\u0002\u0002\u0002ҷқ\u0003\u0002\u0002\u0002Ҹ\u0081\u0003\u0002\u0002\u0002ҹһ\u0005\u009aN\u0002Һҹ\u0003\u0002\u0002\u0002Һһ\u0003\u0002\u0002\u0002һҼ\u0003\u0002\u0002\u0002ҼӇ\u0007\u0091\u0002\u0002ҽҾ\u0007r\u0002\u0002Ҿӈ\u0007\u0083\u0002\u0002ҿӀ\u0007r\u0002\u0002Ӏӈ\u0007\u001f\u0002\u0002Ӂӂ\u0007r\u0002\u0002ӂӈ\u0007\u0080\u0002\u0002Ӄӄ\u0007r\u0002\u0002ӄӈ\u0007N\u0002\u0002Ӆӆ\u0007r\u0002\u0002ӆӈ\u0007W\u0002\u0002Ӈҽ\u0003\u0002\u0002\u0002Ӈҿ\u0003\u0002\u0002\u0002ӇӁ\u0003\u0002\u0002\u0002ӇӃ\u0003\u0002\u0002\u0002ӇӅ\u0003\u0002\u0002\u0002Ӈӈ\u0003\u0002\u0002\u0002ӈӉ\u0003\u0002\u0002\u0002Ӊӊ\u0005\u009cO\u0002ӊӋ\u0007\u0087\u0002\u0002Ӌӌ\u0005Îh\u0002ӌӍ\u0007\f\u0002\u0002Ӎӕ\u0005\u0090I\u0002ӎӏ\u0007\u000b\u0002\u0002ӏӐ\u0005Îh\u0002Ӑӑ\u0007\f\u0002\u0002ӑӒ\u0005\u0090I\u0002ӒӔ\u0003\u0002\u0002\u0002ӓӎ\u0003\u0002\u0002\u0002Ӕӗ\u0003\u0002\u0002\u0002ӕӓ\u0003\u0002\u0002\u0002ӕӖ\u0003\u0002\u0002\u0002ӖӚ\u0003\u0002\u0002\u0002ӗӕ\u0003\u0002\u0002\u0002Әә\u0007\u0098\u0002\u0002әӛ\u0005\u0090I\u0002ӚӘ\u0003\u0002\u0002\u0002Ӛӛ\u0003\u0002\u0002\u0002ӛ\u0083\u0003\u0002\u0002\u0002ӜӞ\u0005\u009aN\u0002ӝӜ\u0003\u0002\u0002\u0002ӝӞ\u0003\u0002\u0002\u0002Ӟӟ\u0003\u0002\u0002\u0002ӟӪ\u0007\u0091\u0002\u0002Ӡӡ\u0007r\u0002\u0002ӡӫ\u0007\u0083\u0002\u0002Ӣӣ\u0007r\u0002\u0002ӣӫ\u0007\u001f\u0002\u0002Ӥӥ\u0007r\u0002\u0002ӥӫ\u0007\u0080\u0002\u0002Ӧӧ\u0007r\u0002\u0002ӧӫ\u0007N\u0002\u0002Өө\u0007r\u0002\u0002өӫ\u0007W\u0002\u0002ӪӠ\u0003\u0002\u0002\u0002ӪӢ\u0003\u0002\u0002\u0002ӪӤ\u0003\u0002\u0002\u0002ӪӦ\u0003\u0002\u0002\u0002ӪӨ\u0003\u0002\u0002\u0002Ӫӫ\u0003\u0002\u0002\u0002ӫӬ\u0003\u0002\u0002\u0002Ӭӭ\u0005\u009cO\u0002ӭӮ\u0007\u0087\u0002\u0002Ӯӯ\u0005Îh\u0002ӯӰ\u0007\f\u0002\u0002ӰӸ\u0005\u0090I\u0002ӱӲ\u0007\u000b\u0002\u0002Ӳӳ\u0005Îh\u0002ӳӴ\u0007\f\u0002\u0002Ӵӵ\u0005\u0090I\u0002ӵӷ\u0003\u0002\u0002\u0002Ӷӱ\u0003\u0002\u0002\u0002ӷӺ\u0003\u0002\u0002\u0002ӸӶ\u0003\u0002\u0002\u0002Ӹӹ\u0003\u0002\u0002\u0002ӹӽ\u0003\u0002\u0002\u0002ӺӸ\u0003\u0002\u0002\u0002ӻӼ\u0007\u0098\u0002\u0002ӼӾ\u0005\u0090I\u0002ӽӻ\u0003\u0002\u0002\u0002ӽӾ\u0003\u0002\u0002\u0002Ӿԑ\u0003\u0002\u0002\u0002ӿԀ\u0007s\u0002\u0002Ԁԁ\u0007.\u0002\u0002ԁԆ\u0005\u009eP\u0002Ԃԃ\u0007\u000b\u0002\u0002ԃԅ\u0005\u009eP\u0002ԄԂ\u0003\u0002\u0002\u0002ԅԈ\u0003\u0002\u0002\u0002ԆԄ\u0003\u0002\u0002\u0002Ԇԇ\u0003\u0002\u0002\u0002ԇԊ\u0003\u0002\u0002\u0002ԈԆ\u0003\u0002\u0002\u0002ԉӿ\u0003\u0002\u0002\u0002ԉԊ\u0003\u0002\u0002\u0002Ԋԋ\u0003\u0002\u0002\u0002ԋԌ\u0007h\u0002\u0002Ԍԏ\u0005\u0090I\u0002ԍԎ\t\u0007\u0002\u0002ԎԐ\u0005\u0090I\u0002ԏԍ\u0003\u0002\u0002\u0002ԏԐ\u0003\u0002\u0002\u0002ԐԒ\u0003\u0002\u0002\u0002ԑԉ\u0003\u0002\u0002\u0002ԑԒ\u0003\u0002\u0002\u0002Ԓ\u0085\u0003\u0002\u0002\u0002ԓԔ\u0007\u0093\u0002\u0002Ԕ\u0087\u0003\u0002\u0002\u0002ԕԗ\u0005Îh\u0002ԖԘ\u0005\u008aF\u0002ԗԖ\u0003\u0002\u0002\u0002ԗԘ\u0003\u0002\u0002\u0002ԘԜ\u0003\u0002\u0002\u0002ԙԛ\u0005\u008cG\u0002Ԛԙ\u0003\u0002\u0002\u0002ԛԞ\u0003\u0002\u0002\u0002ԜԚ\u0003\u0002\u0002\u0002Ԝԝ\u0003\u0002\u0002\u0002ԝ\u0089\u0003\u0002\u0002\u0002ԞԜ\u0003\u0002\u0002\u0002ԟԡ\u0005Âb\u0002Ԡԟ\u0003\u0002\u0002\u0002ԡԢ\u0003\u0002\u0002\u0002ԢԠ\u0003\u0002\u0002\u0002Ԣԣ\u0003\u0002\u0002\u0002ԣԮ\u0003\u0002\u0002\u0002Ԥԥ\u0007\t\u0002\u0002ԥԦ\u0005´[\u0002Ԧԧ\u0007\n\u0002\u0002ԧԯ\u0003\u0002\u0002\u0002Ԩԩ\u0007\t\u0002\u0002ԩԪ\u0005´[\u0002Ԫԫ\u0007\u000b\u0002\u0002ԫԬ\u0005´[\u0002Ԭԭ\u0007\n\u0002\u0002ԭԯ\u0003\u0002\u0002\u0002ԮԤ\u0003\u0002\u0002\u0002ԮԨ\u0003\u0002\u0002\u0002Ԯԯ\u0003\u0002\u0002\u0002ԯ\u008b\u0003\u0002\u0002\u0002\u0530Ա\u00077\u0002\u0002ԱԳ\u0005Âb\u0002Բ\u0530\u0003\u0002\u0002\u0002ԲԳ\u0003\u0002\u0002\u0002ԳՕ\u0003\u0002\u0002\u0002ԴԵ\u0007w\u0002\u0002ԵԷ\u0007e\u0002\u0002ԶԸ\t\u0004\u0002\u0002ԷԶ\u0003\u0002\u0002\u0002ԷԸ\u0003\u0002\u0002\u0002ԸԹ\u0003\u0002\u0002\u0002ԹԻ\u0005\u008eH\u0002ԺԼ\u0007*\u0002\u0002ԻԺ\u0003\u0002\u0002\u0002ԻԼ\u0003\u0002\u0002\u0002ԼՖ\u0003\u0002\u0002\u0002ԽԿ\u0007l\u0002\u0002ԾԽ\u0003\u0002\u0002\u0002ԾԿ\u0003\u0002\u0002\u0002ԿՀ\u0003\u0002\u0002\u0002ՀՁ\u0007n\u0002\u0002ՁՖ\u0005\u008eH\u0002ՂՃ\u0007\u0090\u0002\u0002ՃՖ\u0005\u008eH\u0002ՄՅ\u00072\u0002\u0002ՅՆ\u0007\t\u0002\u0002ՆՇ\u0005\u0090I\u0002ՇՈ\u0007\n\u0002\u0002ՈՖ\u0003\u0002\u0002\u0002ՉՐ\u0007>\u0002\u0002ՊՑ\u0005´[\u0002ՋՑ\u0005¶\\\u0002ՌՍ\u0007\t\u0002\u0002ՍՎ\u0005\u0090I\u0002ՎՏ\u0007\n\u0002\u0002ՏՑ\u0003\u0002\u0002\u0002ՐՊ\u0003\u0002\u0002\u0002ՐՋ\u0003\u0002\u0002\u0002ՐՌ\u0003\u0002\u0002\u0002ՑՖ\u0003\u0002\u0002\u0002ՒՓ\u00073\u0002\u0002ՓՖ\u0005Ði\u0002ՔՖ\u0005\u0092J\u0002ՕԴ\u0003\u0002\u0002\u0002ՕԾ\u0003\u0002\u0002\u0002ՕՂ\u0003\u0002\u0002\u0002ՕՄ\u0003\u0002\u0002\u0002ՕՉ\u0003\u0002\u0002\u0002ՕՒ\u0003\u0002\u0002\u0002ՕՔ\u0003\u0002\u0002\u0002Ֆ\u008d\u0003\u0002\u0002\u0002\u0557\u0558\u0007q\u0002\u0002\u0558ՙ\u00076\u0002\u0002ՙ՛\t\n\u0002\u0002՚\u0557\u0003\u0002\u0002\u0002՚՛\u0003\u0002\u0002\u0002՛\u008f\u0003\u0002\u0002\u0002՜՝\bI\u0001\u0002՝֩\u0005¶\\\u0002՞֩\u0007\u009d\u0002\u0002՟\u0560\u0005Æd\u0002\u0560ա\u0007\b\u0002\u0002ագ\u0003\u0002\u0002\u0002բ՟\u0003\u0002\u0002\u0002բգ\u0003\u0002\u0002\u0002գդ\u0003\u0002\u0002\u0002դե\u0005Èe\u0002եզ\u0007\b\u0002\u0002զը\u0003\u0002\u0002\u0002էբ\u0003\u0002\u0002\u0002էը\u0003\u0002\u0002\u0002ըթ\u0003\u0002\u0002\u0002թ֩\u0005Îh\u0002ժի\u0005¸]\u0002իլ\u0005\u0090I\u0017լ֩\u0003\u0002\u0002\u0002խծ\u0005Äc\u0002ծջ\u0007\t\u0002\u0002կձ\u0007D\u0002\u0002հկ\u0003\u0002\u0002\u0002հձ\u0003\u0002\u0002\u0002ձղ\u0003\u0002\u0002\u0002ղշ\u0005\u0090I\u0002ճմ\u0007\u000b\u0002\u0002մն\u0005\u0090I\u0002յճ\u0003\u0002\u0002\u0002նչ\u0003\u0002\u0002\u0002շյ\u0003\u0002\u0002\u0002շո\u0003\u0002\u0002\u0002ոռ\u0003\u0002\u0002\u0002չշ\u0003\u0002\u0002\u0002պռ\u0007\r\u0002\u0002ջհ\u0003\u0002\u0002\u0002ջպ\u0003\u0002\u0002\u0002ջռ\u0003\u0002\u0002\u0002ռս\u0003\u0002\u0002\u0002սվ\u0007\n\u0002\u0002վ֩\u0003\u0002\u0002\u0002տր\u0007\t\u0002\u0002րց\u0005\u0090I\u0002ցւ\u0007\n\u0002\u0002ւ֩\u0003\u0002\u0002\u0002փք\u00071\u0002\u0002քօ\u0007\t\u0002\u0002օֆ\u0005\u0090I\u0002ֆև\u0007'\u0002\u0002և\u0588\u0005\u008aF\u0002\u0588։\u0007\n\u0002\u0002։֩\u0003\u0002\u0002\u0002֊\u058c\u0007l\u0002\u0002\u058b֊\u0003\u0002\u0002\u0002\u058b\u058c\u0003\u0002\u0002\u0002\u058c֍\u0003\u0002\u0002\u0002֍֏\u0007L\u0002\u0002֎\u058b\u0003\u0002\u0002\u0002֎֏\u0003\u0002\u0002\u0002֏\u0590\u0003\u0002\u0002\u0002\u0590֑\u0007\t\u0002\u0002֑֒\u0005~@\u0002֒֓\u0007\n\u0002\u0002֓֩\u0003\u0002\u0002\u0002֖֔\u00070\u0002\u0002֕֗\u0005\u0090I\u0002֖֕\u0003\u0002\u0002\u0002֖֗\u0003\u0002\u0002\u0002֗֝\u0003\u0002\u0002\u0002֘֙\u0007\u0097\u0002\u0002֚֙\u0005\u0090I\u0002֛֚\u0007\u008b\u0002\u0002֛֜\u0005\u0090I\u0002֜֞\u0003\u0002\u0002\u0002֝֘\u0003\u0002\u0002\u0002֞֟\u0003\u0002\u0002\u0002֟֝\u0003\u0002\u0002\u0002֟֠\u0003\u0002\u0002\u0002֣֠\u0003\u0002\u0002\u0002֢֡\u0007G\u0002\u0002֢֤\u0005\u0090I\u0002֣֡\u0003\u0002\u0002\u0002֣֤\u0003\u0002\u0002\u0002֤֥\u0003\u0002\u0002\u0002֥֦\u0007H\u0002\u0002֦֩\u0003\u0002\u0002\u0002֧֩\u0005\u0094K\u0002֨՜\u0003\u0002\u0002\u0002֨՞\u0003\u0002\u0002\u0002֨է\u0003\u0002\u0002\u0002֨ժ\u0003\u0002\u0002\u0002֨խ\u0003\u0002\u0002\u0002֨տ\u0003\u0002\u0002\u0002֨փ\u0003\u0002\u0002\u0002֨֎\u0003\u0002\u0002\u0002֨֔\u0003\u0002\u0002\u0002֧֨\u0003\u0002\u0002\u0002֩؎\u0003\u0002\u0002\u0002֪֫\f\u0016\u0002\u0002֫֬\u0007\u0011\u0002\u0002֬؍\u0005\u0090I\u0017֭֮\f\u0015\u0002\u0002֮֯\t\u000b\u0002\u0002֯؍\u0005\u0090I\u0016ְֱ\f\u0014\u0002\u0002ֱֲ\t\f\u0002\u0002ֲ؍\u0005\u0090I\u0015ֳִ\f\u0013\u0002\u0002ִֵ\t\r\u0002\u0002ֵ؍\u0005\u0090I\u0014ֶַ\f\u0012\u0002\u0002ַָ\t\u000e\u0002\u0002ָ؍\u0005\u0090I\u0013ֹ׆\f\u0011\u0002\u0002ׇֺ\u0007\f\u0002\u0002ׇֻ\u0007\u001c\u0002\u0002ׇּ\u0007\u001d\u0002\u0002ׇֽ\u0007\u001e\u0002\u0002־ׇ\u0007b\u0002\u0002ֿ׀\u0007b\u0002\u0002׀ׇ\u0007l\u0002\u0002ׇׁ\u0007Y\u0002\u0002ׇׂ\u0007g\u0002\u0002׃ׇ\u0007S\u0002\u0002ׇׄ\u0007i\u0002\u0002ׇׅ\u0007|\u0002\u0002׆ֺ\u0003\u0002\u0002\u0002׆ֻ\u0003\u0002\u0002\u0002׆ּ\u0003\u0002\u0002\u0002׆ֽ\u0003\u0002\u0002\u0002׆־\u0003\u0002\u0002\u0002׆ֿ\u0003\u0002\u0002\u0002׆ׁ\u0003\u0002\u0002\u0002׆ׂ\u0003\u0002\u0002\u0002׆׃\u0003\u0002\u0002\u0002׆ׄ\u0003\u0002\u0002\u0002׆ׅ\u0003\u0002\u0002\u0002ׇ\u05c8\u0003\u0002\u0002\u0002\u05c8؍\u0005\u0090I\u0012\u05c9\u05ca\f\u0010\u0002\u0002\u05ca\u05cb\u0007&\u0002\u0002\u05cb؍\u0005\u0090I\u0011\u05cc\u05cd\f\u000f\u0002\u0002\u05cd\u05ce\u0007r\u0002\u0002\u05ce؍\u0005\u0090I\u0010\u05cfא\f\b\u0002\u0002אג\u0007b\u0002\u0002בד\u0007l\u0002\u0002גב\u0003\u0002\u0002\u0002גד\u0003\u0002\u0002\u0002דה\u0003\u0002\u0002\u0002ה؍\u0005\u0090I\tוח\f\u0007\u0002\u0002זט\u0007l\u0002\u0002חז\u0003\u0002\u0002\u0002חט\u0003\u0002\u0002\u0002טי\u0003\u0002\u0002\u0002יך\u0007-\u0002\u0002ךכ\u0005\u0090I\u0002כל\u0007&\u0002\u0002לם\u0005\u0090I\bם؍\u0003\u0002\u0002\u0002מן\f\u000b\u0002\u0002ןנ\u00073\u0002\u0002נ؍\u0005Ði\u0002סף\f\n\u0002\u0002עפ\u0007l\u0002\u0002ףע\u0003\u0002\u0002\u0002ףפ\u0003\u0002\u0002\u0002פץ\u0003\u0002\u0002\u0002ץצ\t\u000f\u0002\u0002צש\u0005\u0090I\u0002קר\u0007I\u0002\u0002רת\u0005\u0090I\u0002שק\u0003\u0002\u0002\u0002שת\u0003\u0002\u0002\u0002ת؍\u0003\u0002\u0002\u0002\u05ebװ\f\t\u0002\u0002\u05ecױ\u0007c\u0002\u0002\u05edױ\u0007m\u0002\u0002\u05ee\u05ef\u0007l\u0002\u0002\u05efױ\u0007n\u0002\u0002װ\u05ec\u0003\u0002\u0002\u0002װ\u05ed\u0003\u0002\u0002\u0002װ\u05ee\u0003\u0002\u0002\u0002ױ؍\u0003\u0002\u0002\u0002ײ״\f\u0006\u0002\u0002׳\u05f5\u0007l\u0002\u0002״׳\u0003\u0002\u0002\u0002״\u05f5\u0003\u0002\u0002\u0002\u05f5\u05f6\u0003\u0002\u0002\u0002\u05f6؊\u0007Y\u0002\u0002\u05f7\u0601\u0007\t\u0002\u0002\u05f8\u0602\u0005~@\u0002\u05f9\u05fe\u0005\u0090I\u0002\u05fa\u05fb\u0007\u000b\u0002\u0002\u05fb\u05fd\u0005\u0090I\u0002\u05fc\u05fa\u0003\u0002\u0002\u0002\u05fd\u0600\u0003\u0002\u0002\u0002\u05fe\u05fc\u0003\u0002\u0002\u0002\u05fe\u05ff\u0003\u0002\u0002\u0002\u05ff\u0602\u0003\u0002\u0002\u0002\u0600\u05fe\u0003\u0002\u0002\u0002\u0601\u05f8\u0003\u0002\u0002\u0002\u0601\u05f9\u0003\u0002\u0002\u0002\u0601\u0602\u0003\u0002\u0002\u0002\u0602\u0603\u0003\u0002\u0002\u0002\u0603؋\u0007\n\u0002\u0002\u0604\u0605\u0005Æd\u0002\u0605؆\u0007\b\u0002\u0002؆؈\u0003\u0002\u0002\u0002؇\u0604\u0003\u0002\u0002\u0002؇؈\u0003\u0002\u0002\u0002؈؉\u0003\u0002\u0002\u0002؉؋\u0005Èe\u0002؊\u05f7\u0003\u0002\u0002\u0002؊؇\u0003\u0002\u0002\u0002؋؍\u0003\u0002\u0002\u0002،֪\u0003\u0002\u0002\u0002،֭\u0003\u0002\u0002\u0002،ְ\u0003\u0002\u0002\u0002،ֳ\u0003\u0002\u0002\u0002،ֶ\u0003\u0002\u0002\u0002،ֹ\u0003\u0002\u0002\u0002،\u05c9\u0003\u0002\u0002\u0002،\u05cc\u0003\u0002\u0002\u0002،\u05cf\u0003\u0002\u0002\u0002،ו\u0003\u0002\u0002\u0002،מ\u0003\u0002\u0002\u0002،ס\u0003\u0002\u0002\u0002،\u05eb\u0003\u0002\u0002\u0002،ײ\u0003\u0002\u0002\u0002؍ؐ\u0003\u0002\u0002\u0002؎،\u0003\u0002\u0002\u0002؎؏\u0003\u0002\u0002\u0002؏\u0091\u0003\u0002\u0002\u0002ؐ؎\u0003\u0002\u0002\u0002ؑؒ\u0007{\u0002\u0002ؒ؞\u0005Òj\u0002ؓؔ\u0007\t\u0002\u0002ؙؔ\u0005Îh\u0002ؕؖ\u0007\u000b\u0002\u0002ؘؖ\u0005Îh\u0002ؗؕ\u0003\u0002\u0002\u0002ؘ؛\u0003\u0002\u0002\u0002ؙؗ\u0003\u0002\u0002\u0002ؙؚ\u0003\u0002\u0002\u0002ؚ\u061c\u0003\u0002\u0002\u0002؛ؙ\u0003\u0002\u0002\u0002\u061c\u061d\u0007\n\u0002\u0002\u061d؟\u0003\u0002\u0002\u0002؞ؓ\u0003\u0002\u0002\u0002؞؟\u0003\u0002\u0002\u0002؟ز\u0003\u0002\u0002\u0002ؠء\u0007q\u0002\u0002ءت\t\u0010\u0002\u0002آأ\u0007\u0087\u0002\u0002أث\u0007n\u0002\u0002ؤإ\u0007\u0087\u0002\u0002إث\u0007>\u0002\u0002ئث\u0007/\u0002\u0002اث\u0007\u0081\u0002\u0002بة\u0007k\u0002\u0002ةث\u0007 \u0002\u0002تآ\u0003\u0002\u0002\u0002تؤ\u0003\u0002\u0002\u0002تئ\u0003\u0002\u0002\u0002تا\u0003\u0002\u0002\u0002تب\u0003\u0002\u0002\u0002ثد\u0003\u0002\u0002\u0002جح\u0007i\u0002\u0002حد\u0005Âb\u0002خؠ\u0003\u0002\u0002\u0002خج\u0003\u0002\u0002\u0002در\u0003\u0002\u0002\u0002ذخ\u0003\u0002\u0002\u0002رش\u0003\u0002\u0002\u0002زذ\u0003\u0002\u0002\u0002زس\u0003\u0002\u0002\u0002سؿ\u0003\u0002\u0002\u0002شز\u0003\u0002\u0002\u0002صط\u0007l\u0002\u0002ضص\u0003\u0002\u0002\u0002ضط\u0003\u0002\u0002\u0002طظ\u0003\u0002\u0002\u0002ظؽ\u0007?\u0002\u0002عغ\u0007\\\u0002\u0002غؾ\u0007@\u0002\u0002ػؼ\u0007\\\u0002\u0002ؼؾ\u0007X\u0002\u0002ؽع\u0003\u0002\u0002\u0002ؽػ\u0003\u0002\u0002\u0002ؽؾ\u0003\u0002\u0002\u0002ؾـ\u0003\u0002\u0002\u0002ؿض\u0003\u0002\u0002\u0002ؿـ\u0003\u0002\u0002\u0002ـ\u0093\u0003\u0002\u0002\u0002فق\u0007y\u0002\u0002قه\u0007\t\u0002\u0002كو\u0007W\u0002\u0002لم\t\u0011\u0002\u0002من\u0007\u000b\u0002\u0002نو\u0005º^\u0002هك\u0003\u0002\u0002\u0002هل\u0003\u0002\u0002\u0002وى\u0003\u0002\u0002\u0002ىي\u0007\n\u0002\u0002ي\u0095\u0003\u0002\u0002\u0002ًَ\u0005Îh\u0002ٌٍ\u00073\u0002\u0002ٍُ\u0005Ði\u0002ٌَ\u0003\u0002\u0002\u0002َُ\u0003\u0002\u0002\u0002ُّ\u0003\u0002\u0002\u0002ِْ\t\u0004\u0002\u0002ِّ\u0003\u0002\u0002\u0002ّْ\u0003\u0002\u0002\u0002ْ\u0097\u0003\u0002\u0002\u0002ٓٔ\u00077\u0002\u0002ٖٔ\u0005Âb\u0002ٕٓ\u0003\u0002\u0002\u0002ٕٖ\u0003\u0002\u0002\u0002ٖٻ\u0003\u0002\u0002\u0002ٗ٘\u0007w\u0002\u0002٘ٛ\u0007e\u0002\u0002ٙٛ\u0007\u0090\u0002\u0002ٚٗ\u0003\u0002\u0002\u0002ٚٙ\u0003\u0002\u0002\u0002ٜٛ\u0003\u0002\u0002\u0002ٜٝ\u0007\t\u0002\u0002ٝ٢\u0005\u0096L\u0002ٟٞ\u0007\u000b\u0002\u0002ٟ١\u0005\u0096L\u0002٠ٞ\u0003\u0002\u0002\u0002١٤\u0003\u0002\u0002\u0002٢٠\u0003\u0002\u0002\u0002٢٣\u0003\u0002\u0002\u0002٣٥\u0003\u0002\u0002\u0002٤٢\u0003\u0002\u0002\u0002٥٦\u0007\n\u0002\u0002٦٧\u0005\u008eH\u0002٧ټ\u0003\u0002\u0002\u0002٨٩\u00072\u0002\u0002٩٪\u0007\t\u0002\u0002٪٫\u0005\u0090I\u0002٫٬\u0007\n\u0002\u0002٬ټ\u0003\u0002\u0002\u0002٭ٮ\u0007P\u0002\u0002ٮٯ\u0007e\u0002\u0002ٯٰ\u0007\t\u0002\u0002ٰٵ\u0005Îh\u0002ٱٲ\u0007\u000b\u0002\u0002ٲٴ\u0005Îh\u0002ٳٱ\u0003\u0002\u0002\u0002ٴٷ\u0003\u0002\u0002\u0002ٵٳ\u0003\u0002\u0002\u0002ٵٶ\u0003\u0002\u0002\u0002ٶٸ\u0003\u0002\u0002\u0002ٷٵ\u0003\u0002\u0002\u0002ٸٹ\u0007\n\u0002\u0002ٹٺ\u0005\u0092J\u0002ٺټ\u0003\u0002\u0002\u0002ٻٚ\u0003\u0002\u0002\u0002ٻ٨\u0003\u0002\u0002\u0002ٻ٭\u0003\u0002\u0002\u0002ټ\u0099\u0003\u0002\u0002\u0002ٽٿ\u0007\u0099\u0002\u0002پڀ\u0007z\u0002\u0002ٿپ\u0003\u0002\u0002\u0002ٿڀ\u0003\u0002\u0002\u0002ڀځ\u0003\u0002\u0002\u0002ځڂ\u0005²Z\u0002ڂڃ\u0007'\u0002\u0002ڃڄ\u0007\t\u0002\u0002ڄڅ\u0005~@\u0002څڏ\u0007\n\u0002\u0002چڇ\u0007\u000b\u0002\u0002ڇڈ\u0005²Z\u0002ڈډ\u0007'\u0002\u0002ډڊ\u0007\t\u0002\u0002ڊڋ\u0005~@\u0002ڋڌ\u0007\n\u0002\u0002ڌڎ\u0003\u0002\u0002\u0002ڍچ\u0003\u0002\u0002\u0002ڎڑ\u0003\u0002\u0002\u0002ڏڍ\u0003\u0002\u0002\u0002ڏڐ\u0003\u0002\u0002\u0002ڐ\u009b\u0003\u0002\u0002\u0002ڑڏ\u0003\u0002\u0002\u0002ڒړ\u0005Æd\u0002ړڔ\u0007\b\u0002\u0002ڔږ\u0003\u0002\u0002\u0002ڕڒ\u0003\u0002\u0002\u0002ڕږ\u0003\u0002\u0002\u0002ږڗ\u0003\u0002\u0002\u0002ڗڝ\u0005Èe\u0002ژڙ\u0007[\u0002\u0002ڙښ\u0007.\u0002\u0002ښڞ\u0005Ôk\u0002ڛڜ\u0007l\u0002\u0002ڜڞ\u0007[\u0002\u0002ڝژ\u0003\u0002\u0002\u0002ڝڛ\u0003\u0002\u0002\u0002ڝڞ\u0003\u0002\u0002\u0002ڞ\u009d\u0003\u0002\u0002\u0002ڟڢ\u0005\u0090I\u0002ڠڡ\u00073\u0002\u0002ڡڣ\u0005Ði\u0002ڢڠ\u0003\u0002\u0002\u0002ڢڣ\u0003\u0002\u0002\u0002ڣڥ\u0003\u0002\u0002\u0002ڤڦ\t\u0004\u0002\u0002ڥڤ\u0003\u0002\u0002\u0002ڥڦ\u0003\u0002\u0002\u0002ڦ\u009f\u0003\u0002\u0002\u0002ڧګ\u0005´[\u0002ڨګ\u0005Âb\u0002کګ\u0007\u009e\u0002\u0002ڪڧ\u0003\u0002\u0002\u0002ڪڨ\u0003\u0002\u0002\u0002ڪک\u0003\u0002\u0002\u0002ګ¡\u0003\u0002\u0002\u0002ڬڸ\u0005Èe\u0002ڭڮ\u0007\t\u0002\u0002ڮڳ\u0005Îh\u0002گڰ\u0007\u000b\u0002\u0002ڰڲ\u0005Îh\u0002ڱگ\u0003\u0002\u0002\u0002ڲڵ\u0003\u0002\u0002\u0002ڳڱ\u0003\u0002\u0002\u0002ڳڴ\u0003\u0002\u0002\u0002ڴڶ\u0003\u0002\u0002\u0002ڵڳ\u0003\u0002\u0002\u0002ڶڷ\u0007\n\u0002\u0002ڷڹ\u0003\u0002\u0002\u0002ڸڭ\u0003\u0002\u0002\u0002ڸڹ\u0003\u0002\u0002\u0002ڹں\u0003\u0002\u0002\u0002ںڻ\u0007'\u0002\u0002ڻڼ\u0007\t\u0002\u0002ڼڽ\u0005~@\u0002ڽھ\u0007\n\u0002\u0002ھ£\u0003\u0002\u0002\u0002ڿی\u0007\r\u0002\u0002ۀہ\u0005Èe\u0002ہۂ\u0007\b\u0002\u0002ۂۃ\u0007\r\u0002\u0002ۃی\u0003\u0002\u0002\u0002ۄۉ\u0005\u0090I\u0002ۅۇ\u0007'\u0002\u0002ۆۅ\u0003\u0002\u0002\u0002ۆۇ\u0003\u0002\u0002\u0002ۇۈ\u0003\u0002\u0002\u0002ۈۊ\u0005¾`\u0002ۉۆ\u0003\u0002\u0002\u0002ۉۊ\u0003\u0002\u0002\u0002ۊی\u0003\u0002\u0002\u0002ۋڿ\u0003\u0002\u0002\u0002ۋۀ\u0003\u0002\u0002\u0002ۋۄ\u0003\u0002\u0002\u0002ی¥\u0003\u0002\u0002\u0002ۍێ\u0005Æd\u0002ێۏ\u0007\b\u0002\u0002ۏۑ\u0003\u0002\u0002\u0002ېۍ\u0003\u0002\u0002\u0002ېۑ\u0003\u0002\u0002\u0002ۑے\u0003\u0002\u0002\u0002ےۗ\u0005Èe\u0002ۓە\u0007'\u0002\u0002۔ۓ\u0003\u0002\u0002\u0002۔ە\u0003\u0002\u0002\u0002ەۖ\u0003\u0002\u0002\u0002ۖۘ\u0005àq\u0002ۗ۔\u0003\u0002\u0002\u0002ۗۘ\u0003\u0002\u0002\u0002ۘ۞\u0003\u0002\u0002\u0002ۙۚ\u0007[\u0002\u0002ۚۛ\u0007.\u0002\u0002ۛ۟\u0005Ôk\u0002ۜ\u06dd\u0007l\u0002\u0002\u06dd۟\u0007[\u0002\u0002۞ۙ\u0003\u0002\u0002\u0002۞ۜ\u0003\u0002\u0002\u0002۞۟\u0003\u0002\u0002\u0002۟۽\u0003\u0002\u0002\u0002۪۠\u0007\t\u0002\u0002ۡۦ\u0005¦T\u0002ۣۢ\u0007\u000b\u0002\u0002ۣۥ\u0005¦T\u0002ۤۢ\u0003\u0002\u0002\u0002ۥۨ\u0003\u0002\u0002\u0002ۦۤ\u0003\u0002\u0002\u0002ۦۧ\u0003\u0002\u0002\u0002ۧ۫\u0003\u0002\u0002\u0002ۨۦ\u0003\u0002\u0002\u0002۩۫\u0005¨U\u0002۪ۡ\u0003\u0002\u0002\u0002۪۩\u0003\u0002\u0002\u0002۫۬\u0003\u0002\u0002\u0002۬۱\u0007\n\u0002\u0002ۭۯ\u0007'\u0002\u0002ۮۭ\u0003\u0002\u0002\u0002ۮۯ\u0003\u0002\u0002\u0002ۯ۰\u0003\u0002\u0002\u0002۰۲\u0005àq\u0002۱ۮ\u0003\u0002\u0002\u0002۱۲\u0003\u0002\u0002\u0002۲۽\u0003\u0002\u0002\u0002۳۴\u0007\t\u0002\u0002۴۵\u0005~@\u0002۵ۺ\u0007\n\u0002\u0002۶۸\u0007'\u0002\u0002۷۶\u0003\u0002\u0002\u0002۷۸\u0003\u0002\u0002\u0002۸۹\u0003\u0002\u0002\u0002۹ۻ\u0005àq\u0002ۺ۷\u0003\u0002\u0002\u0002ۺۻ\u0003\u0002\u0002\u0002ۻ۽\u0003\u0002\u0002\u0002ۼې\u0003\u0002\u0002\u0002ۼ۠\u0003\u0002\u0002\u0002ۼ۳\u0003\u0002\u0002\u0002۽§\u0003\u0002\u0002\u0002۾܅\u0005¦T\u0002ۿ܀\u0005ªV\u0002܀܁\u0005¦T\u0002܁܂\u0005¬W\u0002܂܄\u0003\u0002\u0002\u0002܃ۿ\u0003\u0002\u0002\u0002܄܇\u0003\u0002\u0002\u0002܅܃\u0003\u0002\u0002\u0002܅܆\u0003\u0002\u0002\u0002܆©\u0003\u0002\u0002\u0002܇܅\u0003\u0002\u0002\u0002܈ܖ\u0007\u000b\u0002\u0002܉܋\u0007j\u0002\u0002܊܉\u0003\u0002\u0002\u0002܊܋\u0003\u0002\u0002\u0002܋ܒ\u0003\u0002\u0002\u0002܌\u070e\u0007f\u0002\u0002܍\u070f\u0007t\u0002\u0002\u070e܍\u0003\u0002\u0002\u0002\u070e\u070f\u0003\u0002\u0002\u0002\u070fܓ\u0003\u0002\u0002\u0002ܐܓ\u0007]\u0002\u0002ܑܓ\u00079\u0002\u0002ܒ܌\u0003\u0002\u0002\u0002ܒܐ\u0003\u0002\u0002\u0002ܒܑ\u0003\u0002\u0002\u0002ܒܓ\u0003\u0002\u0002\u0002ܓܔ\u0003\u0002\u0002\u0002ܔܖ\u0007d\u0002\u0002ܕ܈\u0003\u0002\u0002\u0002ܕ܊\u0003\u0002\u0002\u0002ܖ«\u0003\u0002\u0002\u0002ܗܘ\u0007q\u0002\u0002ܘܦ\u0005\u0090I\u0002ܙܚ\u0007\u0092\u0002\u0002ܚܛ\u0007\t\u0002\u0002ܛܠ\u0005Îh\u0002ܜܝ\u0007\u000b\u0002\u0002ܝܟ\u0005Îh\u0002ܞܜ\u0003\u0002\u0002\u0002ܟܢ\u0003\u0002\u0002\u0002ܠܞ\u0003\u0002\u0002\u0002ܠܡ\u0003\u0002\u0002\u0002ܡܣ\u0003\u0002\u0002\u0002ܢܠ\u0003\u0002\u0002\u0002ܣܤ\u0007\n\u0002\u0002ܤܦ\u0003\u0002\u0002\u0002ܥܗ\u0003\u0002\u0002\u0002ܥܙ\u0003\u0002\u0002\u0002ܥܦ\u0003\u0002\u0002\u0002ܦ\u00ad\u0003\u0002\u0002\u0002ܧܩ\u0007\u0086\u0002\u0002ܨܪ\t\t\u0002\u0002ܩܨ\u0003\u0002\u0002\u0002ܩܪ\u0003\u0002\u0002\u0002ܪܫ\u0003\u0002\u0002\u0002ܫܰ\u0005¤S\u0002ܬܭ\u0007\u000b\u0002\u0002ܭܯ\u0005¤S\u0002ܮܬ\u0003\u0002\u0002\u0002ܯܲ\u0003\u0002\u0002\u0002ܰܮ\u0003\u0002\u0002\u0002ܱܰ\u0003\u0002\u0002\u0002ܱܿ\u0003\u0002\u0002\u0002ܲܰ\u0003\u0002\u0002\u0002ܳܽ\u0007Q\u0002\u0002ܴܹ\u0005¦T\u0002ܵܶ\u0007\u000b\u0002\u0002ܸܶ\u0005¦T\u0002ܷܵ\u0003\u0002\u0002\u0002ܸܻ\u0003\u0002\u0002\u0002ܹܷ\u0003\u0002\u0002\u0002ܹܺ\u0003\u0002\u0002\u0002ܾܺ\u0003\u0002\u0002\u0002ܻܹ\u0003\u0002\u0002\u0002ܼܾ\u0005¨U\u0002ܴܽ\u0003\u0002\u0002\u0002ܼܽ\u0003\u0002\u0002\u0002ܾ݀\u0003\u0002\u0002\u0002ܿܳ\u0003\u0002\u0002\u0002ܿ݀\u0003\u0002\u0002\u0002݀݃\u0003\u0002\u0002\u0002݂݁\u0007\u0098\u0002\u0002݂݄\u0005\u0090I\u0002݃݁\u0003\u0002\u0002\u0002݄݃\u0003\u0002\u0002\u0002݄ݓ\u0003\u0002\u0002\u0002݆݅\u0007T\u0002\u0002݆݇\u0007.\u0002\u0002݇\u074c\u0005\u0090I\u0002݈݉\u0007\u000b\u0002\u0002݉\u074b\u0005\u0090I\u0002݈݊\u0003\u0002\u0002\u0002\u074bݎ\u0003\u0002\u0002\u0002\u074c݊\u0003\u0002\u0002\u0002\u074cݍ\u0003\u0002\u0002\u0002ݍݑ\u0003\u0002\u0002\u0002ݎ\u074c\u0003\u0002\u0002\u0002ݏݐ\u0007U\u0002\u0002ݐݒ\u0005\u0090I\u0002ݑݏ\u0003\u0002\u0002\u0002ݑݒ\u0003\u0002\u0002\u0002ݒݔ\u0003\u0002\u0002\u0002ݓ݅\u0003\u0002\u0002\u0002ݓݔ\u0003\u0002\u0002\u0002ݔݲ\u0003\u0002\u0002\u0002ݕݖ\u0007\u0094\u0002\u0002ݖݗ\u0007\t\u0002\u0002ݗݜ\u0005\u0090I\u0002ݘݙ\u0007\u000b\u0002\u0002ݙݛ\u0005\u0090I\u0002ݚݘ\u0003\u0002\u0002\u0002ݛݞ\u0003\u0002\u0002\u0002ݜݚ\u0003\u0002\u0002\u0002ݜݝ\u0003\u0002\u0002\u0002ݝݟ\u0003\u0002\u0002\u0002ݞݜ\u0003\u0002\u0002\u0002ݟݮ\u0007\n\u0002\u0002ݠݡ\u0007\u000b\u0002\u0002ݡݢ\u0007\t\u0002\u0002ݢݧ\u0005\u0090I\u0002ݣݤ\u0007\u000b\u0002\u0002ݤݦ\u0005\u0090I\u0002ݥݣ\u0003\u0002\u0002\u0002ݦݩ\u0003\u0002\u0002\u0002ݧݥ\u0003\u0002\u0002\u0002ݧݨ\u0003\u0002\u0002\u0002ݨݪ\u0003\u0002\u0002\u0002ݩݧ\u0003\u0002\u0002\u0002ݪݫ\u0007\n\u0002\u0002ݫݭ\u0003\u0002\u0002\u0002ݬݠ\u0003\u0002\u0002\u0002ݭݰ\u0003\u0002\u0002\u0002ݮݬ\u0003\u0002\u0002\u0002ݮݯ\u0003\u0002\u0002\u0002ݯݲ\u0003\u0002\u0002\u0002ݰݮ\u0003\u0002\u0002\u0002ݱܧ\u0003\u0002\u0002\u0002ݱݕ\u0003\u0002\u0002\u0002ݲ¯\u0003\u0002\u0002\u0002ݳݹ\u0007\u008f\u0002\u0002ݴݵ\u0007\u008f\u0002\u0002ݵݹ\u0007#\u0002\u0002ݶݹ\u0007`\u0002\u0002ݷݹ\u0007J\u0002\u0002ݸݳ\u0003\u0002\u0002\u0002ݸݴ\u0003\u0002\u0002\u0002ݸݶ\u0003\u0002\u0002\u0002ݸݷ\u0003\u0002\u0002\u0002ݹ±\u0003\u0002\u0002\u0002ݺކ\u0005Èe\u0002ݻݼ\u0007\t\u0002\u0002ݼށ\u0005Îh\u0002ݽݾ\u0007\u000b\u0002\u0002ݾހ\u0005Îh\u0002ݿݽ\u0003\u0002\u0002\u0002ހރ\u0003\u0002\u0002\u0002ށݿ\u0003\u0002\u0002\u0002ށނ\u0003\u0002\u0002\u0002ނބ\u0003\u0002\u0002\u0002ރށ\u0003\u0002\u0002\u0002ބޅ\u0007\n\u0002\u0002ޅއ\u0003\u0002\u0002\u0002ކݻ\u0003\u0002\u0002\u0002ކއ\u0003\u0002\u0002\u0002އ³\u0003\u0002\u0002\u0002ވފ\t\f\u0002\u0002މވ\u0003\u0002\u0002\u0002މފ\u0003\u0002\u0002\u0002ފދ\u0003\u0002\u0002\u0002ދތ\u0007\u009c\u0002\u0002ތµ\u0003\u0002\u0002\u0002ލގ\t\u0012\u0002\u0002ގ·\u0003\u0002\u0002\u0002ޏސ\t\u0013\u0002\u0002ސ¹\u0003\u0002\u0002\u0002ޑޒ\u0007\u009e\u0002\u0002ޒ»\u0003\u0002\u0002\u0002ޓޖ\u0005\u0090I\u0002ޔޖ\u0005\u0088E\u0002ޕޓ\u0003\u0002\u0002\u0002ޕޔ\u0003\u0002\u0002\u0002ޖ½\u0003\u0002\u0002\u0002ޗޘ\t\u0002\u0002\u0002ޘ¿\u0003\u0002\u0002\u0002ޙޚ\t\u0014\u0002\u0002ޚÁ\u0003\u0002\u0002\u0002ޛޜ\u0005äs\u0002ޜÃ\u0003\u0002\u0002\u0002ޝޞ\u0005äs\u0002ޞÅ\u0003\u0002\u0002\u0002ޟޠ\u0005äs\u0002ޠÇ\u0003\u0002\u0002\u0002ޡޢ\u0005äs\u0002ޢÉ\u0003\u0002\u0002\u0002ޣޤ\u0005äs\u0002ޤË\u0003\u0002\u0002\u0002ޥަ\u0005äs\u0002ަÍ\u0003\u0002\u0002\u0002ާި\u0005äs\u0002ިÏ\u0003\u0002\u0002\u0002ީު\u0005äs\u0002ުÑ\u0003\u0002\u0002\u0002ޫެ\u0005äs\u0002ެÓ\u0003\u0002\u0002\u0002ޭޮ\u0005äs\u0002ޮÕ\u0003\u0002\u0002\u0002ޯް\u0005äs\u0002ް×\u0003\u0002\u0002\u0002ޱ\u07b2\u0005äs\u0002\u07b2Ù\u0003\u0002\u0002\u0002\u07b3\u07b4\u0005äs\u0002\u07b4Û\u0003\u0002\u0002\u0002\u07b5\u07b6\u0005äs\u0002\u07b6Ý\u0003\u0002\u0002\u0002\u07b7\u07b8\u0005äs\u0002\u07b8ß\u0003\u0002\u0002\u0002\u07b9\u07ba\u0005äs\u0002\u07baá\u0003\u0002\u0002\u0002\u07bb\u07bc\u0005äs\u0002\u07bcã\u0003\u0002\u0002\u0002\u07bd߅\u0007\u009b\u0002\u0002\u07be߅\u0005Àa\u0002\u07bf߅\u0007\u009e\u0002\u0002߀߁\u0007\t\u0002\u0002߁߂\u0005äs\u0002߂߃\u0007\n\u0002\u0002߃߅\u0003\u0002\u0002\u0002߄\u07bd\u0003\u0002\u0002\u0002߄\u07be\u0003\u0002\u0002\u0002߄\u07bf\u0003\u0002\u0002\u0002߄߀\u0003\u0002\u0002\u0002߅å\u0003\u0002\u0002\u0002ĂëîþăĈĒğīŀůŽƣƩƫƶƽǂǈǎǐǰǷǿȂȋȏȗțȝȢȤȨȯȲȷȻɀɉɌɒɔɘɞɣɮɴɸɾʃʌʓʙʝʡʧʬʳʾˁ˃ˉˏ˓˚ˠ˦ˬ˱˽̸̜̟̦̯̲̺͓̂̍̒̾͆͋ͥͭ̕͘͠Ͳ\u0378Ϳ\u0382ΊΔΗΝΟ\u03a2ελτωϒϝϤϪϰϹЀЄІЊБГЗКСШЫеиорфыюіѠѣѩѫѯѶѿ҃҅҉ҒҗҙҢҭҴҷҺӇӕӚӝӪӸӽԆԉԏԑԗԜԢԮԲԷԻԾՐՕ՚բէհշջ\u058b֎֖֣֟֨׆גחףשװ״\u05fe\u0601؇؊،؎ؙ؞تخزضؽؿهَّٕٚ٢ٵٻٿڏڕڝڢڥڪڳڸۆۉۋې۔ۗ۞ۦ۪ۮ۱۷ۺۼ܅܊\u070eܒܕܠܥܩܹܰܽܿ݃\u074cݑݓݜݧݮݱݸށކމޕ߄";
   public static final ATN _ATN;

   /** @deprecated */
   @Deprecated
   public String[] getTokenNames() {
      return tokenNames;
   }

   public Vocabulary getVocabulary() {
      return VOCABULARY;
   }

   public String getGrammarFileName() {
      return "SQLGrammar.g4";
   }

   public String[] getRuleNames() {
      return ruleNames;
   }

   public String getSerializedATN() {
      return "\u0003悋Ꜫ脳맭䅼㯧瞆奤\u0003£߇\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0004\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013\u0004\u0014\t\u0014\u0004\u0015\t\u0015\u0004\u0016\t\u0016\u0004\u0017\t\u0017\u0004\u0018\t\u0018\u0004\u0019\t\u0019\u0004\u001a\t\u001a\u0004\u001b\t\u001b\u0004\u001c\t\u001c\u0004\u001d\t\u001d\u0004\u001e\t\u001e\u0004\u001f\t\u001f\u0004 \t \u0004!\t!\u0004\"\t\"\u0004#\t#\u0004$\t$\u0004%\t%\u0004&\t&\u0004'\t'\u0004(\t(\u0004)\t)\u0004*\t*\u0004+\t+\u0004,\t,\u0004-\t-\u0004.\t.\u0004/\t/\u00040\t0\u00041\t1\u00042\t2\u00043\t3\u00044\t4\u00045\t5\u00046\t6\u00047\t7\u00048\t8\u00049\t9\u0004:\t:\u0004;\t;\u0004<\t<\u0004=\t=\u0004>\t>\u0004?\t?\u0004@\t@\u0004A\tA\u0004B\tB\u0004C\tC\u0004D\tD\u0004E\tE\u0004F\tF\u0004G\tG\u0004H\tH\u0004I\tI\u0004J\tJ\u0004K\tK\u0004L\tL\u0004M\tM\u0004N\tN\u0004O\tO\u0004P\tP\u0004Q\tQ\u0004R\tR\u0004S\tS\u0004T\tT\u0004U\tU\u0004V\tV\u0004W\tW\u0004X\tX\u0004Y\tY\u0004Z\tZ\u0004[\t[\u0004\\\t\\\u0004]\t]\u0004^\t^\u0004_\t_\u0004`\t`\u0004a\ta\u0004b\tb\u0004c\tc\u0004d\td\u0004e\te\u0004f\tf\u0004g\tg\u0004h\th\u0004i\ti\u0004j\tj\u0004k\tk\u0004l\tl\u0004m\tm\u0004n\tn\u0004o\to\u0004p\tp\u0004q\tq\u0004r\tr\u0004s\ts\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0005\u0002ì\n\u0002\u0003\u0002\u0005\u0002ï\n\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005ý\n\u0005\f\u0005\u000e\u0005Ā\u000b\u0005\u0003\u0006\u0003\u0006\u0005\u0006Ą\n\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0005\u0007ĉ\n\u0007\u0003\b\u0003\b\u0003\b\u0003\b\u0003\b\u0003\b\u0007\bđ\n\b\f\b\u000e\bĔ\u000b\b\u0003\b\u0003\b\u0003\t\u0003\t\u0003\t\u0003\t\u0003\t\u0003\t\u0007\tĞ\n\t\f\t\u000e\tġ\u000b\t\u0003\t\u0003\t\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0005\nĬ\n\n\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0005\u000bŁ\n\u000b\u0003\f\u0003\f\u0003\f\u0003\f\u0003\r\u0003\r\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0007\u0014Ů\n\u0014\f\u0014\u000e\u0014ű\u000b\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0007\u0015ż\n\u0015\f\u0015\u000e\u0015ſ\u000b\u0015\u0003\u0015\u0003\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001d\u0003\u001d\u0003\u001e\u0003\u001e\u0003\u001f\u0003\u001f\u0003 \u0003 \u0005 Ƥ\n \u0003!\u0003!\u0003\"\u0003\"\u0007\"ƪ\n\"\f\"\u000e\"ƭ\u000b\"\u0003\"\u0003\"\u0003#\u0003#\u0003#\u0003$\u0007$Ƶ\n$\f$\u000e$Ƹ\u000b$\u0003$\u0003$\u0006$Ƽ\n$\r$\u000e$ƽ\u0003$\u0007$ǁ\n$\f$\u000e$Ǆ\u000b$\u0003$\u0007$Ǉ\n$\f$\u000e$Ǌ\u000b$\u0003%\u0003%\u0003%\u0005%Ǐ\n%\u0005%Ǒ\n%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0005%Ǳ\n%\u0003&\u0003&\u0003&\u0003&\u0003&\u0005&Ǹ\n&\u0003&\u0003&\u0003&\u0003&\u0003&\u0003&\u0005&Ȁ\n&\u0003&\u0005&ȃ\n&\u0003'\u0003'\u0003'\u0003'\u0003'\u0003'\u0003'\u0005'Ȍ\n'\u0003(\u0003(\u0005(Ȑ\n(\u0003(\u0003(\u0003(\u0003(\u0003)\u0003)\u0005)Ș\n)\u0003)\u0003)\u0005)Ȝ\n)\u0005)Ȟ\n)\u0003*\u0003*\u0003*\u0005*ȣ\n*\u0005*ȥ\n*\u0003+\u0003+\u0005+ȩ\n+\u0003+\u0003+\u0003+\u0007+Ȯ\n+\f+\u000e+ȱ\u000b+\u0005+ȳ\n+\u0003+\u0003+\u0003+\u0005+ȸ\n+\u0003+\u0003+\u0005+ȼ\n+\u0003+\u0006+ȿ\n+\r+\u000e+ɀ\u0003+\u0003+\u0003+\u0003+\u0003+\u0007+Ɉ\n+\f+\u000e+ɋ\u000b+\u0005+ɍ\n+\u0003+\u0003+\u0003+\u0003+\u0005+ɓ\n+\u0005+ɕ\n+\u0003,\u0003,\u0005,ə\n,\u0003,\u0003,\u0003,\u0003,\u0005,ɟ\n,\u0003,\u0003,\u0003,\u0005,ɤ\n,\u0003,\u0003,\u0003,\u0003,\u0003,\u0003,\u0003,\u0007,ɭ\n,\f,\u000e,ɰ\u000b,\u0003,\u0003,\u0003,\u0005,ɵ\n,\u0003-\u0003-\u0005-ɹ\n-\u0003-\u0003-\u0003-\u0003-\u0005-ɿ\n-\u0003-\u0003-\u0003-\u0005-ʄ\n-\u0003-\u0003-\u0003-\u0003-\u0003-\u0007-ʋ\n-\f-\u000e-ʎ\u000b-\u0003-\u0003-\u0007-ʒ\n-\f-\u000e-ʕ\u000b-\u0003-\u0003-\u0003-\u0005-ʚ\n-\u0003-\u0003-\u0005-ʞ\n-\u0003.\u0003.\u0005.ʢ\n.\u0003.\u0003.\u0003.\u0003.\u0005.ʨ\n.\u0003.\u0003.\u0003.\u0005.ʭ\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0005.ʴ\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0007.ʽ\n.\f.\u000e.ˀ\u000b.\u0005.˂\n.\u0005.˄\n.\u0003.\u0003.\u0003.\u0003.\u0005.ˊ\n.\u0003.\u0003.\u0003.\u0003.\u0005.ː\n.\u0003.\u0003.\u0005.˔\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0005.˛\n.\u0003.\u0003.\u0006.˟\n.\r.\u000e.ˠ\u0003.\u0003.\u0003/\u0003/\u0005/˧\n/\u0003/\u0003/\u0003/\u0003/\u0005/˭\n/\u0003/\u0003/\u0003/\u0005/˲\n/\u0003/\u0003/\u0003/\u0003/\u00030\u00030\u00030\u00030\u00030\u00030\u00050˾\n0\u00030\u00030\u00030\u00050̃\n0\u00030\u00030\u00030\u00030\u00030\u00030\u00030\u00070̌\n0\f0\u000e0̏\u000b0\u00030\u00030\u00050̓\n0\u00031\u00051̖\n1\u00031\u00031\u00031\u00031\u00031\u00051̝\n1\u00032\u00052̠\n2\u00032\u00032\u00032\u00032\u00032\u00052̧\n2\u00032\u00032\u00032\u00032\u00032\u00072̮\n2\f2\u000e2̱\u000b2\u00052̳\n2\u00032\u00032\u00032\u00032\u00052̹\n2\u00052̻\n2\u00033\u00033\u00053̿\n3\u00033\u00033\u00034\u00034\u00034\u00034\u00054͇\n4\u00034\u00034\u00034\u00054͌\n4\u00034\u00034\u00035\u00035\u00035\u00035\u00055͔\n5\u00035\u00035\u00035\u00055͙\n5\u00035\u00035\u00036\u00036\u00036\u00036\u00056͡\n6\u00036\u00036\u00036\u00056ͦ\n6\u00036\u00036\u00037\u00037\u00037\u00037\u00057ͮ\n7\u00037\u00037\u00037\u00057ͳ\n7\u00037\u00037\u00038\u00038\u00058\u0379\n8\u00038\u00038\u00038\u00078;\n8\f8\u000e8\u0381\u000b8\u00058\u0383\n8\u00038\u00038\u00038\u00038\u00078Ή\n8\f8\u000e8Ό\u000b8\u00038\u00038\u00038\u00038\u00038\u00078Γ\n8\f8\u000e8Ζ\u000b8\u00058Θ\n8\u00038\u00038\u00038\u00038\u00058Ξ\n8\u00058Π\n8\u00039\u00059Σ\n9\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00059ζ\n9\u00039\u00039\u00039\u00039\u00059μ\n9\u00039\u00039\u00039\u00039\u00039\u00079σ\n9\f9\u000e9φ\u000b9\u00039\u00039\u00059ϊ\n9\u00039\u00039\u00039\u00039\u00039\u00079ϑ\n9\f9\u000e9ϔ\u000b9\u00039\u00039\u00039\u00039\u00039\u00039\u00079Ϝ\n9\f9\u000e9ϟ\u000b9\u00039\u00039\u00079ϣ\n9\f9\u000e9Ϧ\u000b9\u00039\u00039\u00039\u00059ϫ\n9\u0003:\u0003:\u0003:\u0003:\u0005:ϱ\n:\u0003:\u0003:\u0003:\u0003:\u0003:\u0003:\u0003:\u0005:Ϻ\n:\u0003;\u0003;\u0003;\u0003;\u0003;\u0005;Ё\n;\u0003;\u0003;\u0005;Ѕ\n;\u0005;Ї\n;\u0003<\u0003<\u0005<Ћ\n<\u0003<\u0003<\u0003=\u0003=\u0003=\u0005=В\n=\u0005=Д\n=\u0003=\u0003=\u0005=И\n=\u0003=\u0005=Л\n=\u0003>\u0003>\u0003>\u0003?\u0003?\u0005?Т\n?\u0003?\u0003?\u0003?\u0007?Ч\n?\f?\u000e?Ъ\u000b?\u0005?Ь\n?\u0003?\u0003?\u0003?\u0003?\u0003?\u0003?\u0007?д\n?\f?\u000e?з\u000b?\u0005?й\n?\u0003?\u0003?\u0003?\u0003?\u0005?п\n?\u0005?с\n?\u0003@\u0003@\u0005@х\n@\u0003@\u0003@\u0003@\u0007@ъ\n@\f@\u000e@э\u000b@\u0005@я\n@\u0003@\u0003@\u0003@\u0003@\u0007@ѕ\n@\f@\u000e@ј\u000b@\u0003@\u0003@\u0003@\u0003@\u0003@\u0007@џ\n@\f@\u000e@Ѣ\u000b@\u0005@Ѥ\n@\u0003@\u0003@\u0003@\u0003@\u0005@Ѫ\n@\u0005@Ѭ\n@\u0003A\u0003A\u0005AѰ\nA\u0003A\u0003A\u0003A\u0007Aѵ\nA\fA\u000eAѸ\u000bA\u0003A\u0003A\u0003A\u0003A\u0007AѾ\nA\fA\u000eAҁ\u000bA\u0003A\u0005A҄\nA\u0005A҆\nA\u0003A\u0003A\u0005AҊ\nA\u0003A\u0003A\u0003A\u0003A\u0003A\u0007Aґ\nA\fA\u000eAҔ\u000bA\u0003A\u0003A\u0005AҘ\nA\u0005AҚ\nA\u0003A\u0003A\u0003A\u0003A\u0003A\u0007Aҡ\nA\fA\u000eAҤ\u000bA\u0003A\u0003A\u0003A\u0003A\u0003A\u0003A\u0007AҬ\nA\fA\u000eAү\u000bA\u0003A\u0003A\u0007Aҳ\nA\fA\u000eAҶ\u000bA\u0005AҸ\nA\u0003B\u0005Bһ\nB\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0005Bӈ\nB\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0007BӔ\nB\fB\u000eBӗ\u000bB\u0003B\u0003B\u0005Bӛ\nB\u0003C\u0005CӞ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0005Cӫ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0007Cӷ\nC\fC\u000eCӺ\u000bC\u0003C\u0003C\u0005CӾ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0007Cԅ\nC\fC\u000eCԈ\u000bC\u0005CԊ\nC\u0003C\u0003C\u0003C\u0003C\u0005CԐ\nC\u0005CԒ\nC\u0003D\u0003D\u0003E\u0003E\u0005EԘ\nE\u0003E\u0007Eԛ\nE\fE\u000eEԞ\u000bE\u0003F\u0006Fԡ\nF\rF\u000eFԢ\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0005Fԯ\nF\u0003G\u0003G\u0005GԳ\nG\u0003G\u0003G\u0003G\u0005GԸ\nG\u0003G\u0003G\u0005GԼ\nG\u0003G\u0005GԿ\nG\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0005GՑ\nG\u0003G\u0003G\u0003G\u0005GՖ\nG\u0003H\u0003H\u0003H\u0005H՛\nH\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iգ\nI\u0003I\u0003I\u0003I\u0005Iը\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iձ\nI\u0003I\u0003I\u0003I\u0007Iն\nI\fI\u000eIչ\u000bI\u0003I\u0005Iռ\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005I\u058c\nI\u0003I\u0005I֏\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005I֗\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0006I֞\nI\rI\u000eI֟\u0003I\u0003I\u0005I֤\nI\u0003I\u0003I\u0003I\u0005I֩\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iׇ\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iד\nI\u0003I\u0003I\u0003I\u0005Iט\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iפ\nI\u0003I\u0003I\u0003I\u0003I\u0005Iת\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iױ\nI\u0003I\u0003I\u0005I\u05f5\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0007I\u05fd\nI\fI\u000eI\u0600\u000bI\u0005I\u0602\nI\u0003I\u0003I\u0003I\u0003I\u0005I؈\nI\u0003I\u0005I؋\nI\u0007I؍\nI\fI\u000eIؐ\u000bI\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0007Jؘ\nJ\fJ\u000eJ؛\u000bJ\u0003J\u0003J\u0005J؟\nJ\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0005Jث\nJ\u0003J\u0003J\u0005Jد\nJ\u0007Jر\nJ\fJ\u000eJش\u000bJ\u0003J\u0005Jط\nJ\u0003J\u0003J\u0003J\u0003J\u0003J\u0005Jؾ\nJ\u0005Jـ\nJ\u0003K\u0003K\u0003K\u0003K\u0003K\u0003K\u0005Kو\nK\u0003K\u0003K\u0003L\u0003L\u0003L\u0005Lُ\nL\u0003L\u0005Lْ\nL\u0003M\u0003M\u0005Mٖ\nM\u0003M\u0003M\u0003M\u0005Mٛ\nM\u0003M\u0003M\u0003M\u0003M\u0007M١\nM\fM\u000eM٤\u000bM\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0007Mٴ\nM\fM\u000eMٷ\u000bM\u0003M\u0003M\u0003M\u0005Mټ\nM\u0003N\u0003N\u0005Nڀ\nN\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0007Nڎ\nN\fN\u000eNڑ\u000bN\u0003O\u0003O\u0003O\u0005Oږ\nO\u0003O\u0003O\u0003O\u0003O\u0003O\u0003O\u0005Oڞ\nO\u0003P\u0003P\u0003P\u0005Pڣ\nP\u0003P\u0005Pڦ\nP\u0003Q\u0003Q\u0003Q\u0005Qګ\nQ\u0003R\u0003R\u0003R\u0003R\u0003R\u0007Rڲ\nR\fR\u000eRڵ\u000bR\u0003R\u0003R\u0005Rڹ\nR\u0003R\u0003R\u0003R\u0003R\u0003R\u0003S\u0003S\u0003S\u0003S\u0003S\u0003S\u0003S\u0005Sۇ\nS\u0003S\u0005Sۊ\nS\u0005Sی\nS\u0003T\u0003T\u0003T\u0005Tۑ\nT\u0003T\u0003T\u0005Tە\nT\u0003T\u0005Tۘ\nT\u0003T\u0003T\u0003T\u0003T\u0003T\u0005T۟\nT\u0003T\u0003T\u0003T\u0003T\u0007Tۥ\nT\fT\u000eTۨ\u000bT\u0003T\u0005T۫\nT\u0003T\u0003T\u0005Tۯ\nT\u0003T\u0005T۲\nT\u0003T\u0003T\u0003T\u0003T\u0005T۸\nT\u0003T\u0005Tۻ\nT\u0005T۽\nT\u0003U\u0003U\u0003U\u0003U\u0003U\u0007U܄\nU\fU\u000eU܇\u000bU\u0003V\u0003V\u0005V܋\nV\u0003V\u0003V\u0005V\u070f\nV\u0003V\u0003V\u0005Vܓ\nV\u0003V\u0005Vܖ\nV\u0003W\u0003W\u0003W\u0003W\u0003W\u0003W\u0003W\u0007Wܟ\nW\fW\u000eWܢ\u000bW\u0003W\u0003W\u0005Wܦ\nW\u0003X\u0003X\u0005Xܪ\nX\u0003X\u0003X\u0003X\u0007Xܯ\nX\fX\u000eXܲ\u000bX\u0003X\u0003X\u0003X\u0003X\u0007Xܸ\nX\fX\u000eXܻ\u000bX\u0003X\u0005Xܾ\nX\u0005X݀\nX\u0003X\u0003X\u0005X݄\nX\u0003X\u0003X\u0003X\u0003X\u0003X\u0007X\u074b\nX\fX\u000eXݎ\u000bX\u0003X\u0003X\u0005Xݒ\nX\u0005Xݔ\nX\u0003X\u0003X\u0003X\u0003X\u0003X\u0007Xݛ\nX\fX\u000eXݞ\u000bX\u0003X\u0003X\u0003X\u0003X\u0003X\u0003X\u0007Xݦ\nX\fX\u000eXݩ\u000bX\u0003X\u0003X\u0007Xݭ\nX\fX\u000eXݰ\u000bX\u0005Xݲ\nX\u0003Y\u0003Y\u0003Y\u0003Y\u0003Y\u0005Yݹ\nY\u0003Z\u0003Z\u0003Z\u0003Z\u0003Z\u0007Zހ\nZ\fZ\u000eZރ\u000bZ\u0003Z\u0003Z\u0005Zއ\nZ\u0003[\u0005[ފ\n[\u0003[\u0003[\u0003\\\u0003\\\u0003]\u0003]\u0003^\u0003^\u0003_\u0003_\u0005_ޖ\n_\u0003`\u0003`\u0003a\u0003a\u0003b\u0003b\u0003c\u0003c\u0003d\u0003d\u0003e\u0003e\u0003f\u0003f\u0003g\u0003g\u0003h\u0003h\u0003i\u0003i\u0003j\u0003j\u0003k\u0003k\u0003l\u0003l\u0003m\u0003m\u0003n\u0003n\u0003o\u0003o\u0003p\u0003p\u0003q\u0003q\u0003r\u0003r\u0003s\u0003s\u0003s\u0003s\u0003s\u0003s\u0003s\u0005s߅\ns\u0003s\u0002\u0003\u0090t\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e ¢¤¦¨ª¬®°²´¶¸º¼¾ÀÂÄÆÈÊÌÎÐÒÔÖØÚÜÞàâä\u0002\u0015\u0004\u0002\u009b\u009b\u009e\u009e\u0005\u0002\u0006\u0006\u009c\u009c\u009e\u009e\u0004\u0002((BB\u0005\u0002@@KKXX\u0004\u000255HH\u0004\u0002\u000b\u000bpp\u0003\u0002\u0089\u008a\u0004\u0002##DD\u0007\u0002\u001f\u001fNNWW\u0080\u0080\u0083\u0083\u0004\u0002\r\r\u0012\u0013\u0003\u0002\u000e\u000f\u0003\u0002\u0014\u0017\u0003\u0002\u0018\u001b\u0006\u0002SSggii||\u0004\u0002AA\u0091\u0091\u0005\u0002\u001f\u001fNN\u0083\u0083\u0006\u0002:<nn\u009c\u009c\u009e\u009f\u0004\u0002\u000e\u0010ll\u0003\u0002\u001f\u009a\u0002\u08cf\u0002æ\u0003\u0002\u0002\u0002\u0004ò\u0003\u0002\u0002\u0002\u0006ô\u0003\u0002\u0002\u0002\b÷\u0003\u0002\u0002\u0002\nă\u0003\u0002\u0002\u0002\fĈ\u0003\u0002\u0002\u0002\u000eĊ\u0003\u0002\u0002\u0002\u0010ė\u0003\u0002\u0002\u0002\u0012ī\u0003\u0002\u0002\u0002\u0014ŀ\u0003\u0002\u0002\u0002\u0016ł\u0003\u0002\u0002\u0002\u0018ņ\u0003\u0002\u0002\u0002\u001aŊ\u0003\u0002\u0002\u0002\u001cŎ\u0003\u0002\u0002\u0002\u001eŒ\u0003\u0002\u0002\u0002 Ŗ\u0003\u0002\u0002\u0002\"Ś\u0003\u0002\u0002\u0002$Š\u0003\u0002\u0002\u0002&ŧ\u0003\u0002\u0002\u0002(Ŵ\u0003\u0002\u0002\u0002*Ƃ\u0003\u0002\u0002\u0002,Ɔ\u0003\u0002\u0002\u0002.Ɗ\u0003\u0002\u0002\u00020Ǝ\u0003\u0002\u0002\u00022Ɠ\u0003\u0002\u0002\u00024Ɨ\u0003\u0002\u0002\u00026ƙ\u0003\u0002\u0002\u00028ƛ\u0003\u0002\u0002\u0002:Ɲ\u0003\u0002\u0002\u0002<Ɵ\u0003\u0002\u0002\u0002>ơ\u0003\u0002\u0002\u0002@ƥ\u0003\u0002\u0002\u0002Bƫ\u0003\u0002\u0002\u0002Dư\u0003\u0002\u0002\u0002Fƶ\u0003\u0002\u0002\u0002Hǐ\u0003\u0002\u0002\u0002Jǲ\u0003\u0002\u0002\u0002LȄ\u0003\u0002\u0002\u0002Nȍ\u0003\u0002\u0002\u0002Pȕ\u0003\u0002\u0002\u0002Rȟ\u0003\u0002\u0002\u0002TȲ\u0003\u0002\u0002\u0002Vɖ\u0003\u0002\u0002\u0002Xɶ\u0003\u0002\u0002\u0002Zʟ\u0003\u0002\u0002\u0002\\ˤ\u0003\u0002\u0002\u0002^˷\u0003\u0002\u0002\u0002`̕\u0003\u0002\u0002\u0002b̟\u0003\u0002\u0002\u0002d̼\u0003\u0002\u0002\u0002f͂\u0003\u0002\u0002\u0002h͏\u0003\u0002\u0002\u0002j͜\u0003\u0002\u0002\u0002lͩ\u0003\u0002\u0002\u0002n\u0382\u0003\u0002\u0002\u0002p\u03a2\u0003\u0002\u0002\u0002rϬ\u0003\u0002\u0002\u0002tϻ\u0003\u0002\u0002\u0002vЈ\u0003\u0002\u0002\u0002xЎ\u0003\u0002\u0002\u0002zМ\u0003\u0002\u0002\u0002|Ы\u0003\u0002\u0002\u0002~ю\u0003\u0002\u0002\u0002\u0080ҷ\u0003\u0002\u0002\u0002\u0082Һ\u0003\u0002\u0002\u0002\u0084ӝ\u0003\u0002\u0002\u0002\u0086ԓ\u0003\u0002\u0002\u0002\u0088ԕ\u0003\u0002\u0002\u0002\u008aԠ\u0003\u0002\u0002\u0002\u008cԲ\u0003\u0002\u0002\u0002\u008e՚\u0003\u0002\u0002\u0002\u0090֨\u0003\u0002\u0002\u0002\u0092ؑ\u0003\u0002\u0002\u0002\u0094ف\u0003\u0002\u0002\u0002\u0096ً\u0003\u0002\u0002\u0002\u0098ٕ\u0003\u0002\u0002\u0002\u009aٽ\u0003\u0002\u0002\u0002\u009cڕ\u0003\u0002\u0002\u0002\u009eڟ\u0003\u0002\u0002\u0002 ڪ\u0003\u0002\u0002\u0002¢ڬ\u0003\u0002\u0002\u0002¤ۋ\u0003\u0002\u0002\u0002¦ۼ\u0003\u0002\u0002\u0002¨۾\u0003\u0002\u0002\u0002ªܕ\u0003\u0002\u0002\u0002¬ܥ\u0003\u0002\u0002\u0002®ݱ\u0003\u0002\u0002\u0002°ݸ\u0003\u0002\u0002\u0002²ݺ\u0003\u0002\u0002\u0002´މ\u0003\u0002\u0002\u0002¶ލ\u0003\u0002\u0002\u0002¸ޏ\u0003\u0002\u0002\u0002ºޑ\u0003\u0002\u0002\u0002¼ޕ\u0003\u0002\u0002\u0002¾ޗ\u0003\u0002\u0002\u0002Àޙ\u0003\u0002\u0002\u0002Âޛ\u0003\u0002\u0002\u0002Äޝ\u0003\u0002\u0002\u0002Æޟ\u0003\u0002\u0002\u0002Èޡ\u0003\u0002\u0002\u0002Êޣ\u0003\u0002\u0002\u0002Ìޥ\u0003\u0002\u0002\u0002Îާ\u0003\u0002\u0002\u0002Ðީ\u0003\u0002\u0002\u0002Òޫ\u0003\u0002\u0002\u0002Ôޭ\u0003\u0002\u0002\u0002Öޯ\u0003\u0002\u0002\u0002Øޱ\u0003\u0002\u0002\u0002Ú\u07b3\u0003\u0002\u0002\u0002Ü\u07b5\u0003\u0002\u0002\u0002Þ\u07b7\u0003\u0002\u0002\u0002à\u07b9\u0003\u0002\u0002\u0002â\u07bb\u0003\u0002\u0002\u0002ä߄\u0003\u0002\u0002\u0002æç\u0007\u0086\u0002\u0002çè\u0007\r\u0002\u0002èé\u0007Q\u0002\u0002éë\u0005\u0004\u0003\u0002êì\u0005\u0006\u0004\u0002ëê\u0003\u0002\u0002\u0002ëì\u0003\u0002\u0002\u0002ìî\u0003\u0002\u0002\u0002íï\u0005\b\u0005\u0002îí\u0003\u0002\u0002\u0002îï\u0003\u0002\u0002\u0002ïð\u0003\u0002\u0002\u0002ðñ\u0007\u0002\u0002\u0003ñ\u0003\u0003\u0002\u0002\u0002òó\t\u0002\u0002\u0002ó\u0005\u0003\u0002\u0002\u0002ôõ\u0007\u0098\u0002\u0002õö\u0005\n\u0006\u0002ö\u0007\u0003\u0002\u0002\u0002÷ø\u0007s\u0002\u0002øù\u0007.\u0002\u0002ùþ\u0005> \u0002úû\u0007\u000b\u0002\u0002ûý\u0005> \u0002üú\u0003\u0002\u0002\u0002ýĀ\u0003\u0002\u0002\u0002þü\u0003\u0002\u0002\u0002þÿ\u0003\u0002\u0002\u0002ÿ\t\u0003\u0002\u0002\u0002Āþ\u0003\u0002\u0002\u0002āĄ\u0005\f\u0007\u0002ĂĄ\u0005\u0014\u000b\u0002ăā\u0003\u0002\u0002\u0002ăĂ\u0003\u0002\u0002\u0002Ą\u000b\u0003\u0002\u0002\u0002ąĉ\u0005\u000e\b\u0002Ćĉ\u0005\u0010\t\u0002ćĉ\u0005\u0012\n\u0002Ĉą\u0003\u0002\u0002\u0002ĈĆ\u0003\u0002\u0002\u0002Ĉć\u0003\u0002\u0002\u0002ĉ\r\u0003\u0002\u0002\u0002Ċċ\u0007\t\u0002\u0002ċČ\u0005\n\u0006\u0002Čč\u0007&\u0002\u0002čĒ\u0005\n\u0006\u0002Ďď\u0007&\u0002\u0002ďđ\u0005\n\u0006\u0002ĐĎ\u0003\u0002\u0002\u0002đĔ\u0003\u0002\u0002\u0002ĒĐ\u0003\u0002\u0002\u0002Ēē\u0003\u0002\u0002\u0002ēĕ\u0003\u0002\u0002\u0002ĔĒ\u0003\u0002\u0002\u0002ĕĖ\u0007\n\u0002\u0002Ė\u000f\u0003\u0002\u0002\u0002ėĘ\u0007\t\u0002\u0002Ęę\u0005\n\u0006\u0002ęĚ\u0007r\u0002\u0002Ěğ\u0005\n\u0006\u0002ěĜ\u0007r\u0002\u0002ĜĞ\u0005\n\u0006\u0002ĝě\u0003\u0002\u0002\u0002Ğġ\u0003\u0002\u0002\u0002ğĝ\u0003\u0002\u0002\u0002ğĠ\u0003\u0002\u0002\u0002ĠĢ\u0003\u0002\u0002\u0002ġğ\u0003\u0002\u0002\u0002Ģģ\u0007\n\u0002\u0002ģ\u0011\u0003\u0002\u0002\u0002Ĥĥ\u0007l\u0002\u0002ĥĬ\u0005\n\u0006\u0002Ħħ\u0007\t\u0002\u0002ħĨ\u0007l\u0002\u0002Ĩĩ\u0005\n\u0006\u0002ĩĪ\u0007\n\u0002\u0002ĪĬ\u0003\u0002\u0002\u0002īĤ\u0003\u0002\u0002\u0002īĦ\u0003\u0002\u0002\u0002Ĭ\u0013\u0003\u0002\u0002\u0002ĭŁ\u0005\u0016\f\u0002ĮŁ\u0005\u0018\r\u0002įŁ\u0005\u001a\u000e\u0002İŁ\u0005\u001c\u000f\u0002ıŁ\u0005\u001e\u0010\u0002ĲŁ\u0005 \u0011\u0002ĳŁ\u0005\"\u0012\u0002ĴŁ\u0005$\u0013\u0002ĵŁ\u0005&\u0014\u0002ĶŁ\u0005(\u0015\u0002ķŁ\u0005*\u0016\u0002ĸŁ\u0005,\u0017\u0002ĹŁ\u0005.\u0018\u0002ĺŁ\u00050\u0019\u0002ĻŁ\u00052\u001a\u0002ļĽ\u0007\t\u0002\u0002Ľľ\u0005\u0014\u000b\u0002ľĿ\u0007\n\u0002\u0002ĿŁ\u0003\u0002\u0002\u0002ŀĭ\u0003\u0002\u0002\u0002ŀĮ\u0003\u0002\u0002\u0002ŀį\u0003\u0002\u0002\u0002ŀİ\u0003\u0002\u0002\u0002ŀı\u0003\u0002\u0002\u0002ŀĲ\u0003\u0002\u0002\u0002ŀĳ\u0003\u0002\u0002\u0002ŀĴ\u0003\u0002\u0002\u0002ŀĵ\u0003\u0002\u0002\u0002ŀĶ\u0003\u0002\u0002\u0002ŀķ\u0003\u0002\u0002\u0002ŀĸ\u0003\u0002\u0002\u0002ŀĹ\u0003\u0002\u0002\u0002ŀĺ\u0003\u0002\u0002\u0002ŀĻ\u0003\u0002\u0002\u0002ŀļ\u0003\u0002\u0002\u0002Ł\u0015\u0003\u0002\u0002\u0002łŃ\u00054\u001b\u0002Ńń\u0007\f\u0002\u0002ńŅ\u0005<\u001f\u0002Ņ\u0017\u0003\u0002\u0002\u0002ņŇ\u00054\u001b\u0002Ňň\u0007\u001e\u0002\u0002ňŉ\u0005<\u001f\u0002ŉ\u0019\u0003\u0002\u0002\u0002Ŋŋ\u00054\u001b\u0002ŋŌ\u0007\u0019\u0002\u0002Ōō\u0005<\u001f\u0002ō\u001b\u0003\u0002\u0002\u0002Ŏŏ\u00054\u001b\u0002ŏŐ\u0007\u0018\u0002\u0002Őő\u0005<\u001f\u0002ő\u001d\u0003\u0002\u0002\u0002Œœ\u00054\u001b\u0002œŔ\u0007\u001b\u0002\u0002Ŕŕ\u0005<\u001f\u0002ŕ\u001f\u0003\u0002\u0002\u0002Ŗŗ\u00054\u001b\u0002ŗŘ\u0007\u001a\u0002\u0002Řř\u0005<\u001f\u0002ř!\u0003\u0002\u0002\u0002Śś\u00054\u001b\u0002śŜ\u0007-\u0002\u0002Ŝŝ\u0005<\u001f\u0002ŝŞ\u0007&\u0002\u0002Şş\u0005<\u001f\u0002ş#\u0003\u0002\u0002\u0002Šš\u00054\u001b\u0002šŢ\u0007l\u0002\u0002Ţţ\u0007-\u0002\u0002ţŤ\u0005<\u001f\u0002Ťť\u0007&\u0002\u0002ťŦ\u0005<\u001f\u0002Ŧ%\u0003\u0002\u0002\u0002ŧŨ\u00054\u001b\u0002Ũũ\u0007Y\u0002\u0002ũŪ\u0007\t\u0002\u0002Ūů\u0005<\u001f\u0002ūŬ\u0007\u000b\u0002\u0002ŬŮ\u0005<\u001f\u0002ŭū\u0003\u0002\u0002\u0002Ůű\u0003\u0002\u0002\u0002ůŭ\u0003\u0002\u0002\u0002ůŰ\u0003\u0002\u0002\u0002ŰŲ\u0003\u0002\u0002\u0002űů\u0003\u0002\u0002\u0002Ųų\u0007\n\u0002\u0002ų'\u0003\u0002\u0002\u0002Ŵŵ\u00054\u001b\u0002ŵŶ\u0007l\u0002\u0002Ŷŷ\u0007Y\u0002\u0002ŷŸ\u0007\t\u0002\u0002ŸŽ\u0005<\u001f\u0002Źź\u0007\u000b\u0002\u0002źż\u0005<\u001f\u0002ŻŹ\u0003\u0002\u0002\u0002żſ\u0003\u0002\u0002\u0002ŽŻ\u0003\u0002\u0002\u0002Žž\u0003\u0002\u0002\u0002žƀ\u0003\u0002\u0002\u0002ſŽ\u0003\u0002\u0002\u0002ƀƁ\u0007\n\u0002\u0002Ɓ)\u0003\u0002\u0002\u0002Ƃƃ\u00054\u001b\u0002ƃƄ\u0007g\u0002\u0002Ƅƅ\u00056\u001c\u0002ƅ+\u0003\u0002\u0002\u0002ƆƇ\u00054\u001b\u0002Ƈƈ\u0007g\u0002\u0002ƈƉ\u00058\u001d\u0002Ɖ-\u0003\u0002\u0002\u0002ƊƋ\u00054\u001b\u0002Ƌƌ\u0007g\u0002\u0002ƌƍ\u0005:\u001e\u0002ƍ/\u0003\u0002\u0002\u0002ƎƏ\u00054\u001b\u0002ƏƐ\u0007b\u0002\u0002ƐƑ\u0007l\u0002\u0002Ƒƒ\u0007n\u0002\u0002ƒ1\u0003\u0002\u0002\u0002ƓƔ\u00054\u001b\u0002Ɣƕ\u0007b\u0002\u0002ƕƖ\u0007n\u0002\u0002Ɩ3\u0003\u0002\u0002\u0002ƗƘ\t\u0002\u0002\u0002Ƙ5\u0003\u0002\u0002\u0002ƙƚ\u0007\u0003\u0002\u0002ƚ7\u0003\u0002\u0002\u0002ƛƜ\u0007\u0004\u0002\u0002Ɯ9\u0003\u0002\u0002\u0002Ɲƞ\u0007\u0005\u0002\u0002ƞ;\u0003\u0002\u0002\u0002ƟƠ\t\u0003\u0002\u0002Ơ=\u0003\u0002\u0002\u0002ơƣ\u00054\u001b\u0002ƢƤ\u0005@!\u0002ƣƢ\u0003\u0002\u0002\u0002ƣƤ\u0003\u0002\u0002\u0002Ƥ?\u0003\u0002\u0002\u0002ƥƦ\t\u0004\u0002\u0002ƦA\u0003\u0002\u0002\u0002Ƨƪ\u0005F$\u0002ƨƪ\u0005D#\u0002ƩƧ\u0003\u0002\u0002\u0002Ʃƨ\u0003\u0002\u0002\u0002ƪƭ\u0003\u0002\u0002\u0002ƫƩ\u0003\u0002\u0002\u0002ƫƬ\u0003\u0002\u0002\u0002ƬƮ\u0003\u0002\u0002\u0002ƭƫ\u0003\u0002\u0002\u0002ƮƯ\u0007\u0002\u0002\u0003ƯC\u0003\u0002\u0002\u0002ưƱ\u0007£\u0002\u0002ƱƲ\b#\u0001\u0002ƲE\u0003\u0002\u0002\u0002ƳƵ\u0007\u0007\u0002\u0002ƴƳ\u0003\u0002\u0002\u0002ƵƸ\u0003\u0002\u0002\u0002ƶƴ\u0003\u0002\u0002\u0002ƶƷ\u0003\u0002\u0002\u0002Ʒƹ\u0003\u0002\u0002\u0002Ƹƶ\u0003\u0002\u0002\u0002ƹǂ\u0005H%\u0002ƺƼ\u0007\u0007\u0002\u0002ƻƺ\u0003\u0002\u0002\u0002Ƽƽ\u0003\u0002\u0002\u0002ƽƻ\u0003\u0002\u0002\u0002ƽƾ\u0003\u0002\u0002\u0002ƾƿ\u0003\u0002\u0002\u0002ƿǁ\u0005H%\u0002ǀƻ\u0003\u0002\u0002\u0002ǁǄ\u0003\u0002\u0002\u0002ǂǀ\u0003\u0002\u0002\u0002ǂǃ\u0003\u0002\u0002\u0002ǃǈ\u0003\u0002\u0002\u0002Ǆǂ\u0003\u0002\u0002\u0002ǅǇ\u0007\u0007\u0002\u0002ǆǅ\u0003\u0002\u0002\u0002ǇǊ\u0003\u0002\u0002\u0002ǈǆ\u0003\u0002\u0002\u0002ǈǉ\u0003\u0002\u0002\u0002ǉG\u0003\u0002\u0002\u0002Ǌǈ\u0003\u0002\u0002\u0002ǋǎ\u0007M\u0002\u0002ǌǍ\u0007x\u0002\u0002ǍǏ\u0007u\u0002\u0002ǎǌ\u0003\u0002\u0002\u0002ǎǏ\u0003\u0002\u0002\u0002ǏǑ\u0003\u0002\u0002\u0002ǐǋ\u0003\u0002\u0002\u0002ǐǑ\u0003\u0002\u0002\u0002Ǒǰ\u0003\u0002\u0002\u0002ǒǱ\u0005J&\u0002ǓǱ\u0005L'\u0002ǔǱ\u0005N(\u0002ǕǱ\u0005P)\u0002ǖǱ\u0005R*\u0002ǗǱ\u0005T+\u0002ǘǱ\u0005V,\u0002ǙǱ\u0005X-\u0002ǚǱ\u0005Z.\u0002ǛǱ\u0005\\/\u0002ǜǱ\u0005^0\u0002ǝǱ\u0005`1\u0002ǞǱ\u0005b2\u0002ǟǱ\u0005d3\u0002ǠǱ\u0005f4\u0002ǡǱ\u0005h5\u0002ǢǱ\u0005j6\u0002ǣǱ\u0005l7\u0002ǤǱ\u0005n8\u0002ǥǱ\u0005p9\u0002ǦǱ\u0005r:\u0002ǧǱ\u0005t;\u0002ǨǱ\u0005v<\u0002ǩǱ\u0005x=\u0002ǪǱ\u0005z>\u0002ǫǱ\u0005|?\u0002ǬǱ\u0005~@\u0002ǭǱ\u0005\u0082B\u0002ǮǱ\u0005\u0084C\u0002ǯǱ\u0005\u0086D\u0002ǰǒ\u0003\u0002\u0002\u0002ǰǓ\u0003\u0002\u0002\u0002ǰǔ\u0003\u0002\u0002\u0002ǰǕ\u0003\u0002\u0002\u0002ǰǖ\u0003\u0002\u0002\u0002ǰǗ\u0003\u0002\u0002\u0002ǰǘ\u0003\u0002\u0002\u0002ǰǙ\u0003\u0002\u0002\u0002ǰǚ\u0003\u0002\u0002\u0002ǰǛ\u0003\u0002\u0002\u0002ǰǜ\u0003\u0002\u0002\u0002ǰǝ\u0003\u0002\u0002\u0002ǰǞ\u0003\u0002\u0002\u0002ǰǟ\u0003\u0002\u0002\u0002ǰǠ\u0003\u0002\u0002\u0002ǰǡ\u0003\u0002\u0002\u0002ǰǢ\u0003\u0002\u0002\u0002ǰǣ\u0003\u0002\u0002\u0002ǰǤ\u0003\u0002\u0002\u0002ǰǥ\u0003\u0002\u0002\u0002ǰǦ\u0003\u0002\u0002\u0002ǰǧ\u0003\u0002\u0002\u0002ǰǨ\u0003\u0002\u0002\u0002ǰǩ\u0003\u0002\u0002\u0002ǰǪ\u0003\u0002\u0002\u0002ǰǫ\u0003\u0002\u0002\u0002ǰǬ\u0003\u0002\u0002\u0002ǰǭ\u0003\u0002\u0002\u0002ǰǮ\u0003\u0002\u0002\u0002ǰǯ\u0003\u0002\u0002\u0002ǱI\u0003\u0002\u0002\u0002ǲǳ\u0007$\u0002\u0002ǳǷ\u0007\u0088\u0002\u0002Ǵǵ\u0005Æd\u0002ǵǶ\u0007\b\u0002\u0002ǶǸ\u0003\u0002\u0002\u0002ǷǴ\u0003\u0002\u0002\u0002ǷǸ\u0003\u0002\u0002\u0002Ǹǹ\u0003\u0002\u0002\u0002ǹȂ\u0005Èe\u0002Ǻǻ\u0007\u007f\u0002\u0002ǻǼ\u0007\u008c\u0002\u0002Ǽȃ\u0005Ìg\u0002ǽǿ\u0007!\u0002\u0002ǾȀ\u00074\u0002\u0002ǿǾ\u0003\u0002\u0002\u0002ǿȀ\u0003\u0002\u0002\u0002Ȁȁ\u0003\u0002\u0002\u0002ȁȃ\u0005\u0088E\u0002ȂǺ\u0003\u0002\u0002\u0002Ȃǽ\u0003\u0002\u0002\u0002ȃK\u0003\u0002\u0002\u0002Ȅȋ\u0007%\u0002\u0002ȅȌ\u0005Æd\u0002ȆȌ\u0005Êf\u0002ȇȈ\u0005Æd\u0002Ȉȉ\u0007\b\u0002\u0002ȉȊ\u0005Êf\u0002ȊȌ\u0003\u0002\u0002\u0002ȋȅ\u0003\u0002\u0002\u0002ȋȆ\u0003\u0002\u0002\u0002ȋȇ\u0003\u0002\u0002\u0002ȋȌ\u0003\u0002\u0002\u0002ȌM\u0003\u0002\u0002\u0002ȍȏ\u0007)\u0002\u0002ȎȐ\u0007=\u0002\u0002ȏȎ\u0003\u0002\u0002\u0002ȏȐ\u0003\u0002\u0002\u0002Ȑȑ\u0003\u0002\u0002\u0002ȑȒ\u0005\u0090I\u0002Ȓȓ\u0007'\u0002\u0002ȓȔ\u0005Æd\u0002ȔO\u0003\u0002\u0002\u0002ȕȗ\u0007,\u0002\u0002ȖȘ\t\u0005\u0002\u0002ȗȖ\u0003\u0002\u0002\u0002ȗȘ\u0003\u0002\u0002\u0002Șȝ\u0003\u0002\u0002\u0002șț\u0007\u008d\u0002\u0002ȚȜ\u0005âr\u0002țȚ\u0003\u0002\u0002\u0002țȜ\u0003\u0002\u0002\u0002ȜȞ\u0003\u0002\u0002\u0002ȝș\u0003\u0002\u0002\u0002ȝȞ\u0003\u0002\u0002\u0002ȞQ\u0003\u0002\u0002\u0002ȟȤ\t\u0006\u0002\u0002ȠȢ\u0007\u008d\u0002\u0002ȡȣ\u0005âr\u0002Ȣȡ\u0003\u0002\u0002\u0002Ȣȣ\u0003\u0002\u0002\u0002ȣȥ\u0003\u0002\u0002\u0002ȤȠ\u0003\u0002\u0002\u0002Ȥȥ\u0003\u0002\u0002\u0002ȥS\u0003\u0002\u0002\u0002ȦȨ\u0007\u0099\u0002\u0002ȧȩ\u0007z\u0002\u0002Ȩȧ\u0003\u0002\u0002\u0002Ȩȩ\u0003\u0002\u0002\u0002ȩȪ\u0003\u0002\u0002\u0002Ȫȯ\u0005¢R\u0002ȫȬ\u0007\u000b\u0002\u0002ȬȮ\u0005¢R\u0002ȭȫ\u0003\u0002\u0002\u0002Ȯȱ\u0003\u0002\u0002\u0002ȯȭ\u0003\u0002\u0002\u0002ȯȰ\u0003\u0002\u0002\u0002Ȱȳ\u0003\u0002\u0002\u0002ȱȯ\u0003\u0002\u0002\u0002ȲȦ\u0003\u0002\u0002\u0002Ȳȳ\u0003\u0002\u0002\u0002ȳȴ\u0003\u0002\u0002\u0002ȴȾ\u0005®X\u0002ȵȷ\u0007\u008f\u0002\u0002ȶȸ\u0007#\u0002\u0002ȷȶ\u0003\u0002\u0002\u0002ȷȸ\u0003\u0002\u0002\u0002ȸȼ\u0003\u0002\u0002\u0002ȹȼ\u0007`\u0002\u0002Ⱥȼ\u0007J\u0002\u0002Ȼȵ\u0003\u0002\u0002\u0002Ȼȹ\u0003\u0002\u0002\u0002ȻȺ\u0003\u0002\u0002\u0002ȼȽ\u0003\u0002\u0002\u0002Ƚȿ\u0005®X\u0002ȾȻ\u0003\u0002\u0002\u0002ȿɀ\u0003\u0002\u0002\u0002ɀȾ\u0003\u0002\u0002\u0002ɀɁ\u0003\u0002\u0002\u0002ɁɌ\u0003\u0002\u0002\u0002ɂɃ\u0007s\u0002\u0002ɃɄ\u0007.\u0002\u0002Ʉɉ\u0005\u009eP\u0002ɅɆ\u0007\u000b\u0002\u0002ɆɈ\u0005\u009eP\u0002ɇɅ\u0003\u0002\u0002\u0002Ɉɋ\u0003\u0002\u0002\u0002ɉɇ\u0003\u0002\u0002\u0002ɉɊ\u0003\u0002\u0002\u0002Ɋɍ\u0003\u0002\u0002\u0002ɋɉ\u0003\u0002\u0002\u0002Ɍɂ\u0003\u0002\u0002\u0002Ɍɍ\u0003\u0002\u0002\u0002ɍɔ\u0003\u0002\u0002\u0002Ɏɏ\u0007h\u0002\u0002ɏɒ\u0005\u0090I\u0002ɐɑ\t\u0007\u0002\u0002ɑɓ\u0005\u0090I\u0002ɒɐ\u0003\u0002\u0002\u0002ɒɓ\u0003\u0002\u0002\u0002ɓɕ\u0003\u0002\u0002\u0002ɔɎ\u0003\u0002\u0002\u0002ɔɕ\u0003\u0002\u0002\u0002ɕU\u0003\u0002\u0002\u0002ɖɘ\u00078\u0002\u0002ɗə\u0007\u0090\u0002\u0002ɘɗ\u0003\u0002\u0002\u0002ɘə\u0003\u0002\u0002\u0002əɚ\u0003\u0002\u0002\u0002ɚɞ\u0007Z\u0002\u0002ɛɜ\u0007V\u0002\u0002ɜɝ\u0007l\u0002\u0002ɝɟ\u0007L\u0002\u0002ɞɛ\u0003\u0002\u0002\u0002ɞɟ\u0003\u0002\u0002\u0002ɟɣ\u0003\u0002\u0002\u0002ɠɡ\u0005Æd\u0002ɡɢ\u0007\b\u0002\u0002ɢɤ\u0003\u0002\u0002\u0002ɣɠ\u0003\u0002\u0002\u0002ɣɤ\u0003\u0002\u0002\u0002ɤɥ\u0003\u0002\u0002\u0002ɥɦ\u0005Ôk\u0002ɦɧ\u0007q\u0002\u0002ɧɨ\u0005Èe\u0002ɨɩ\u0007\t\u0002\u0002ɩɮ\u0005\u0096L\u0002ɪɫ\u0007\u000b\u0002\u0002ɫɭ\u0005\u0096L\u0002ɬɪ\u0003\u0002\u0002\u0002ɭɰ\u0003\u0002\u0002\u0002ɮɬ\u0003\u0002\u0002\u0002ɮɯ\u0003\u0002\u0002\u0002ɯɱ\u0003\u0002\u0002\u0002ɰɮ\u0003\u0002\u0002\u0002ɱɴ\u0007\n\u0002\u0002ɲɳ\u0007\u0098\u0002\u0002ɳɵ\u0005\u0090I\u0002ɴɲ\u0003\u0002\u0002\u0002ɴɵ\u0003\u0002\u0002\u0002ɵW\u0003\u0002\u0002\u0002ɶɸ\u00078\u0002\u0002ɷɹ\t\b\u0002\u0002ɸɷ\u0003\u0002\u0002\u0002ɸɹ\u0003\u0002\u0002\u0002ɹɺ\u0003\u0002\u0002\u0002ɺɾ\u0007\u0088\u0002\u0002ɻɼ\u0007V\u0002\u0002ɼɽ\u0007l\u0002\u0002ɽɿ\u0007L\u0002\u0002ɾɻ\u0003\u0002\u0002\u0002ɾɿ\u0003\u0002\u0002\u0002ɿʃ\u0003\u0002\u0002\u0002ʀʁ\u0005Æd\u0002ʁʂ\u0007\b\u0002\u0002ʂʄ\u0003\u0002\u0002\u0002ʃʀ\u0003\u0002\u0002\u0002ʃʄ\u0003\u0002\u0002\u0002ʄʅ\u0003\u0002\u0002\u0002ʅʝ\u0005Èe\u0002ʆʇ\u0007\t\u0002\u0002ʇʌ\u0005\u0088E\u0002ʈʉ\u0007\u000b\u0002\u0002ʉʋ\u0005\u0088E\u0002ʊʈ\u0003\u0002\u0002\u0002ʋʎ\u0003\u0002\u0002\u0002ʌʊ\u0003\u0002\u0002\u0002ʌʍ\u0003\u0002\u0002\u0002ʍʓ\u0003\u0002\u0002\u0002ʎʌ\u0003\u0002\u0002\u0002ʏʐ\u0007\u000b\u0002\u0002ʐʒ\u0005\u0098M\u0002ʑʏ\u0003\u0002\u0002\u0002ʒʕ\u0003\u0002\u0002\u0002ʓʑ\u0003\u0002\u0002\u0002ʓʔ\u0003\u0002\u0002\u0002ʔʖ\u0003\u0002\u0002\u0002ʕʓ\u0003\u0002\u0002\u0002ʖʙ\u0007\n\u0002\u0002ʗʘ\u0007\u009a\u0002\u0002ʘʚ\u0007\u009b\u0002\u0002ʙʗ\u0003\u0002\u0002\u0002ʙʚ\u0003\u0002\u0002\u0002ʚʞ\u0003\u0002\u0002\u0002ʛʜ\u0007'\u0002\u0002ʜʞ\u0005~@\u0002ʝʆ\u0003\u0002\u0002\u0002ʝʛ\u0003\u0002\u0002\u0002ʞY\u0003\u0002\u0002\u0002ʟʡ\u00078\u0002\u0002ʠʢ\t\b\u0002\u0002ʡʠ\u0003\u0002\u0002\u0002ʡʢ\u0003\u0002\u0002\u0002ʢʣ\u0003\u0002\u0002\u0002ʣʧ\u0007\u008e\u0002\u0002ʤʥ\u0007V\u0002\u0002ʥʦ\u0007l\u0002\u0002ʦʨ\u0007L\u0002\u0002ʧʤ\u0003\u0002\u0002\u0002ʧʨ\u0003\u0002\u0002\u0002ʨʬ\u0003\u0002\u0002\u0002ʩʪ\u0005Æd\u0002ʪʫ\u0007\b\u0002\u0002ʫʭ\u0003\u0002\u0002\u0002ʬʩ\u0003\u0002\u0002\u0002ʬʭ\u0003\u0002\u0002\u0002ʭʮ\u0003\u0002\u0002\u0002ʮʳ\u0005Öl\u0002ʯʴ\u0007+\u0002\u0002ʰʴ\u0007\"\u0002\u0002ʱʲ\u0007_\u0002\u0002ʲʴ\u0007o\u0002\u0002ʳʯ\u0003\u0002\u0002\u0002ʳʰ\u0003\u0002\u0002\u0002ʳʱ\u0003\u0002\u0002\u0002ʳʴ\u0003\u0002\u0002\u0002ʴ˃\u0003\u0002\u0002\u0002ʵ˄\u0007A\u0002\u0002ʶ˄\u0007^\u0002\u0002ʷˁ\u0007\u0091\u0002\u0002ʸʹ\u0007o\u0002\u0002ʹʾ\u0005Îh\u0002ʺʻ\u0007\u000b\u0002\u0002ʻʽ\u0005Îh\u0002ʼʺ\u0003\u0002\u0002\u0002ʽˀ\u0003\u0002\u0002\u0002ʾʼ\u0003\u0002\u0002\u0002ʾʿ\u0003\u0002\u0002\u0002ʿ˂\u0003\u0002\u0002\u0002ˀʾ\u0003\u0002\u0002\u0002ˁʸ\u0003\u0002\u0002\u0002ˁ˂\u0003\u0002\u0002\u0002˂˄\u0003\u0002\u0002\u0002˃ʵ\u0003\u0002\u0002\u0002˃ʶ\u0003\u0002\u0002\u0002˃ʷ\u0003\u0002\u0002\u0002˄˅\u0003\u0002\u0002\u0002˅ˉ\u0007q\u0002\u0002ˆˇ\u0005Æd\u0002ˇˈ\u0007\b\u0002\u0002ˈˊ\u0003\u0002\u0002\u0002ˉˆ\u0003\u0002\u0002\u0002ˉˊ\u0003\u0002\u0002\u0002ˊˋ\u0003\u0002\u0002\u0002ˋˏ\u0005Èe\u0002ˌˍ\u0007O\u0002\u0002ˍˎ\u0007F\u0002\u0002ˎː\u0007\u0084\u0002\u0002ˏˌ\u0003\u0002\u0002\u0002ˏː\u0003\u0002\u0002\u0002ː˓\u0003\u0002\u0002\u0002ˑ˒\u0007\u0097\u0002\u0002˒˔\u0005\u0090I\u0002˓ˑ\u0003\u0002\u0002\u0002˓˔\u0003\u0002\u0002\u0002˔˕\u0003\u0002\u0002\u0002˕˞\u0007,\u0002\u0002˖˛\u0005\u0082B\u0002˗˛\u0005p9\u0002˘˛\u0005`1\u0002˙˛\u0005~@\u0002˚˖\u0003\u0002\u0002\u0002˚˗\u0003\u0002\u0002\u0002˚˘\u0003\u0002\u0002\u0002˚˙\u0003\u0002\u0002\u0002˛˜\u0003\u0002\u0002\u0002˜˝\u0007\u0007\u0002\u0002˝˟\u0003\u0002\u0002\u0002˞˚\u0003\u0002\u0002\u0002˟ˠ\u0003\u0002\u0002\u0002ˠ˞\u0003\u0002\u0002\u0002ˠˡ\u0003\u0002\u0002\u0002ˡˢ\u0003\u0002\u0002\u0002ˢˣ\u0007H\u0002\u0002ˣ[\u0003\u0002\u0002\u0002ˤ˦\u00078\u0002\u0002˥˧\t\b\u0002\u0002˦˥\u0003\u0002\u0002\u0002˦˧\u0003\u0002\u0002\u0002˧˨\u0003\u0002\u0002\u0002˨ˬ\u0007\u0095\u0002\u0002˩˪\u0007V\u0002\u0002˪˫\u0007l\u0002\u0002˫˭\u0007L\u0002\u0002ˬ˩\u0003\u0002\u0002\u0002ˬ˭\u0003\u0002\u0002\u0002˭˱\u0003\u0002\u0002\u0002ˮ˯\u0005Æd\u0002˯˰\u0007\b\u0002\u0002˰˲\u0003\u0002\u0002\u0002˱ˮ\u0003\u0002\u0002\u0002˱˲\u0003\u0002\u0002\u0002˲˳\u0003\u0002\u0002\u0002˳˴\u0005Øm\u0002˴˵\u0007'\u0002\u0002˵˶\u0005~@\u0002˶]\u0003\u0002\u0002\u0002˷˸\u00078\u0002\u0002˸˹\u0007\u0096\u0002\u0002˹˽\u0007\u0088\u0002\u0002˺˻\u0007V\u0002\u0002˻˼\u0007l\u0002\u0002˼˾\u0007L\u0002\u0002˽˺\u0003\u0002\u0002\u0002˽˾\u0003\u0002\u0002\u0002˾̂\u0003\u0002\u0002\u0002˿̀\u0005Æd\u0002̀́\u0007\b\u0002\u0002́̃\u0003\u0002\u0002\u0002̂˿\u0003\u0002\u0002\u0002̂̃\u0003\u0002\u0002\u0002̃̄\u0003\u0002\u0002\u0002̄̅\u0005Èe\u0002̅̆\u0007\u0092\u0002\u0002̆̒\u0005Ún\u0002̇̈\u0007\t\u0002\u0002̈̍\u0005¼_\u0002̉̊\u0007\u000b\u0002\u0002̊̌\u0005¼_\u0002̋̉\u0003\u0002\u0002\u0002̌̏\u0003\u0002\u0002\u0002̍̋\u0003\u0002\u0002\u0002̍̎\u0003\u0002\u0002\u0002̎̐\u0003\u0002\u0002\u0002̏̍\u0003\u0002\u0002\u0002̐̑\u0007\n\u0002\u0002̑̓\u0003\u0002\u0002\u0002̒̇\u0003\u0002\u0002\u0002̒̓\u0003\u0002\u0002\u0002̓_\u0003\u0002\u0002\u0002̖̔\u0005\u009aN\u0002̔̕\u0003\u0002\u0002\u0002̖̕\u0003\u0002\u0002\u0002̖̗\u0003\u0002\u0002\u0002̗̘\u0007A\u0002\u0002̘̙\u0007Q\u0002\u0002̙̜\u0005\u009cO\u0002̛̚\u0007\u0098\u0002\u0002̛̝\u0005\u0090I\u0002̜̚\u0003\u0002\u0002\u0002̜̝\u0003\u0002\u0002\u0002̝a\u0003\u0002\u0002\u0002̞̠\u0005\u009aN\u0002̟̞\u0003\u0002\u0002\u0002̟̠\u0003\u0002\u0002\u0002̡̠\u0003\u0002\u0002\u0002̡̢\u0007A\u0002\u0002̢̣\u0007Q\u0002\u0002̣̦\u0005\u009cO\u0002̤̥\u0007\u0098\u0002\u0002̧̥\u0005\u0090I\u0002̦̤\u0003\u0002\u0002\u0002̧̦\u0003\u0002\u0002\u0002̧̺\u0003\u0002\u0002\u0002̨̩\u0007s\u0002\u0002̩̪\u0007.\u0002\u0002̪̯\u0005\u009eP\u0002̫̬\u0007\u000b\u0002\u0002̬̮\u0005\u009eP\u0002̭̫\u0003\u0002\u0002\u0002̮̱\u0003\u0002\u0002\u0002̯̭\u0003\u0002\u0002\u0002̯̰\u0003\u0002\u0002\u0002̰̳\u0003\u0002\u0002\u0002̱̯\u0003\u0002\u0002\u0002̨̲\u0003\u0002\u0002\u0002̲̳\u0003\u0002\u0002\u0002̴̳\u0003\u0002\u0002\u0002̴̵\u0007h\u0002\u0002̵̸\u0005\u0090I\u0002̶̷\t\u0007\u0002\u0002̷̹\u0005\u0090I\u0002̸̶\u0003\u0002\u0002\u0002̸̹\u0003\u0002\u0002\u0002̹̻\u0003\u0002\u0002\u0002̺̲\u0003\u0002\u0002\u0002̺̻\u0003\u0002\u0002\u0002̻c\u0003\u0002\u0002\u0002̼̾\u0007C\u0002\u0002̽̿\u0007=\u0002\u0002̾̽\u0003\u0002\u0002\u0002̾̿\u0003\u0002\u0002\u0002̿̀\u0003\u0002\u0002\u0002̀́\u0005Æd\u0002́e\u0003\u0002\u0002\u0002͂̓\u0007E\u0002\u0002̓͆\u0007Z\u0002\u0002̈́ͅ\u0007V\u0002\u0002͇ͅ\u0007L\u0002\u0002͆̈́\u0003\u0002\u0002\u0002͇͆\u0003\u0002\u0002\u0002͇͋\u0003\u0002\u0002\u0002͈͉\u0005Æd\u0002͉͊\u0007\b\u0002\u0002͊͌\u0003\u0002\u0002\u0002͈͋\u0003\u0002\u0002\u0002͋͌\u0003\u0002\u0002\u0002͍͌\u0003\u0002\u0002\u0002͍͎\u0005Ôk\u0002͎g\u0003\u0002\u0002\u0002͏͐\u0007E\u0002\u0002͓͐\u0007\u0088\u0002\u0002͑͒\u0007V\u0002\u0002͔͒\u0007L\u0002\u0002͓͑\u0003\u0002\u0002\u0002͓͔\u0003\u0002\u0002\u0002͔͘\u0003\u0002\u0002\u0002͕͖\u0005Æd\u0002͖͗\u0007\b\u0002\u0002͙͗\u0003\u0002\u0002\u0002͕͘\u0003\u0002\u0002\u0002͙͘\u0003\u0002\u0002\u0002͙͚\u0003\u0002\u0002\u0002͚͛\u0005Èe\u0002͛i\u0003\u0002\u0002\u0002͜͝\u0007E\u0002\u0002͝͠\u0007\u008e\u0002\u0002͟͞\u0007V\u0002\u0002͟͡\u0007L\u0002\u0002͠͞\u0003\u0002\u0002\u0002͠͡\u0003\u0002\u0002\u0002ͥ͡\u0003\u0002\u0002\u0002ͣ͢\u0005Æd\u0002ͣͤ\u0007\b\u0002\u0002ͤͦ\u0003\u0002\u0002\u0002ͥ͢\u0003\u0002\u0002\u0002ͥͦ\u0003\u0002\u0002\u0002ͦͧ\u0003\u0002\u0002\u0002ͧͨ\u0005Öl\u0002ͨk\u0003\u0002\u0002\u0002ͩͪ\u0007E\u0002\u0002ͪͭ\u0007\u0095\u0002\u0002ͫͬ\u0007V\u0002\u0002ͬͮ\u0007L\u0002\u0002ͭͫ\u0003\u0002\u0002\u0002ͭͮ\u0003\u0002\u0002\u0002ͮͲ\u0003\u0002\u0002\u0002ͯͰ\u0005Æd\u0002Ͱͱ\u0007\b\u0002\u0002ͱͳ\u0003\u0002\u0002\u0002Ͳͯ\u0003\u0002\u0002\u0002Ͳͳ\u0003\u0002\u0002\u0002ͳʹ\u0003\u0002\u0002\u0002ʹ͵\u0005Øm\u0002͵m\u0003\u0002\u0002\u0002Ͷ\u0378\u0007\u0099\u0002\u0002ͷ\u0379\u0007z\u0002\u0002\u0378ͷ\u0003\u0002\u0002\u0002\u0378\u0379\u0003\u0002\u0002\u0002\u0379ͺ\u0003\u0002\u0002\u0002ͺͿ\u0005¢R\u0002ͻͼ\u0007\u000b\u0002\u0002ͼ;\u0005¢R\u0002ͽͻ\u0003\u0002\u0002\u0002;\u0381\u0003\u0002\u0002\u0002Ϳͽ\u0003\u0002\u0002\u0002Ϳ\u0380\u0003\u0002\u0002\u0002\u0380\u0383\u0003\u0002\u0002\u0002\u0381Ϳ\u0003\u0002\u0002\u0002\u0382Ͷ\u0003\u0002\u0002\u0002\u0382\u0383\u0003\u0002\u0002\u0002\u0383΄\u0003\u0002\u0002\u0002΄Ί\u0005®X\u0002΅Ά\u0005°Y\u0002Ά·\u0005®X\u0002·Ή\u0003\u0002\u0002\u0002Έ΅\u0003\u0002\u0002\u0002ΉΌ\u0003\u0002\u0002\u0002ΊΈ\u0003\u0002\u0002\u0002Ί\u038b\u0003\u0002\u0002\u0002\u038bΗ\u0003\u0002\u0002\u0002ΌΊ\u0003\u0002\u0002\u0002\u038dΎ\u0007s\u0002\u0002ΎΏ\u0007.\u0002\u0002ΏΔ\u0005\u009eP\u0002ΐΑ\u0007\u000b\u0002\u0002ΑΓ\u0005\u009eP\u0002Βΐ\u0003\u0002\u0002\u0002ΓΖ\u0003\u0002\u0002\u0002ΔΒ\u0003\u0002\u0002\u0002ΔΕ\u0003\u0002\u0002\u0002ΕΘ\u0003\u0002\u0002\u0002ΖΔ\u0003\u0002\u0002\u0002Η\u038d\u0003\u0002\u0002\u0002ΗΘ\u0003\u0002\u0002\u0002ΘΟ\u0003\u0002\u0002\u0002ΙΚ\u0007h\u0002\u0002ΚΝ\u0005\u0090I\u0002ΛΜ\t\u0007\u0002\u0002ΜΞ\u0005\u0090I\u0002ΝΛ\u0003\u0002\u0002\u0002ΝΞ\u0003\u0002\u0002\u0002ΞΠ\u0003\u0002\u0002\u0002ΟΙ\u0003\u0002\u0002\u0002ΟΠ\u0003\u0002\u0002\u0002Πo\u0003\u0002\u0002\u0002ΡΣ\u0005\u009aN\u0002\u03a2Ρ\u0003\u0002\u0002\u0002\u03a2Σ\u0003\u0002\u0002\u0002Σε\u0003\u0002\u0002\u0002Τζ\u0007^\u0002\u0002Υζ\u0007\u0080\u0002\u0002ΦΧ\u0007^\u0002\u0002ΧΨ\u0007r\u0002\u0002Ψζ\u0007\u0080\u0002\u0002ΩΪ\u0007^\u0002\u0002ΪΫ\u0007r\u0002\u0002Ϋζ\u0007\u0083\u0002\u0002άέ\u0007^\u0002\u0002έή\u0007r\u0002\u0002ήζ\u0007\u001f\u0002\u0002ίΰ\u0007^\u0002\u0002ΰα\u0007r\u0002\u0002αζ\u0007N\u0002\u0002βγ\u0007^\u0002\u0002γδ\u0007r\u0002\u0002δζ\u0007W\u0002\u0002εΤ\u0003\u0002\u0002\u0002εΥ\u0003\u0002\u0002\u0002εΦ\u0003\u0002\u0002\u0002εΩ\u0003\u0002\u0002\u0002εά\u0003\u0002\u0002\u0002εί\u0003\u0002\u0002\u0002εβ\u0003\u0002\u0002\u0002ζη\u0003\u0002\u0002\u0002ηλ\u0007a\u0002\u0002θι\u0005Æd\u0002ικ\u0007\b\u0002\u0002κμ\u0003\u0002\u0002\u0002λθ\u0003\u0002\u0002\u0002λμ\u0003\u0002\u0002\u0002μν\u0003\u0002\u0002\u0002νω\u0005Èe\u0002ξο\u0007\t\u0002\u0002οτ\u0005Îh\u0002πρ\u0007\u000b\u0002\u0002ρσ\u0005Îh\u0002ςπ\u0003\u0002\u0002\u0002σφ\u0003\u0002\u0002\u0002τς\u0003\u0002\u0002\u0002τυ\u0003\u0002\u0002\u0002υχ\u0003\u0002\u0002\u0002φτ\u0003\u0002\u0002\u0002χψ\u0007\n\u0002\u0002ψϊ\u0003\u0002\u0002\u0002ωξ\u0003\u0002\u0002\u0002ωϊ\u0003\u0002\u0002\u0002ϊϪ\u0003\u0002\u0002\u0002ϋό\u0007\u0094\u0002\u0002όύ\u0007\t\u0002\u0002ύϒ\u0005\u0090I\u0002ώϏ\u0007\u000b\u0002\u0002Ϗϑ\u0005\u0090I\u0002ϐώ\u0003\u0002\u0002\u0002ϑϔ\u0003\u0002\u0002\u0002ϒϐ\u0003\u0002\u0002\u0002ϒϓ\u0003\u0002\u0002\u0002ϓϕ\u0003\u0002\u0002\u0002ϔϒ\u0003\u0002\u0002\u0002ϕϤ\u0007\n\u0002\u0002ϖϗ\u0007\u000b\u0002\u0002ϗϘ\u0007\t\u0002\u0002Ϙϝ\u0005\u0090I\u0002ϙϚ\u0007\u000b\u0002\u0002ϚϜ\u0005\u0090I\u0002ϛϙ\u0003\u0002\u0002\u0002Ϝϟ\u0003\u0002\u0002\u0002ϝϛ\u0003\u0002\u0002\u0002ϝϞ\u0003\u0002\u0002\u0002ϞϠ\u0003\u0002\u0002\u0002ϟϝ\u0003\u0002\u0002\u0002Ϡϡ\u0007\n\u0002\u0002ϡϣ\u0003\u0002\u0002\u0002Ϣϖ\u0003\u0002\u0002\u0002ϣϦ\u0003\u0002\u0002\u0002ϤϢ\u0003\u0002\u0002\u0002Ϥϥ\u0003\u0002\u0002\u0002ϥϫ\u0003\u0002\u0002\u0002ϦϤ\u0003\u0002\u0002\u0002ϧϫ\u0005~@\u0002Ϩϩ\u0007>\u0002\u0002ϩϫ\u0007\u0094\u0002\u0002Ϫϋ\u0003\u0002\u0002\u0002Ϫϧ\u0003\u0002\u0002\u0002ϪϨ\u0003\u0002\u0002\u0002ϫq\u0003\u0002\u0002\u0002Ϭϰ\u0007v\u0002\u0002ϭϮ\u0005Æd\u0002Ϯϯ\u0007\b\u0002\u0002ϯϱ\u0003\u0002\u0002\u0002ϰϭ\u0003\u0002\u0002\u0002ϰϱ\u0003\u0002\u0002\u0002ϱϲ\u0003\u0002\u0002\u0002ϲϹ\u0005Üo\u0002ϳϴ\u0007\f\u0002\u0002ϴϺ\u0005 Q\u0002ϵ϶\u0007\t\u0002\u0002϶Ϸ\u0005 Q\u0002Ϸϸ\u0007\n\u0002\u0002ϸϺ\u0003\u0002\u0002\u0002Ϲϳ\u0003\u0002\u0002\u0002Ϲϵ\u0003\u0002\u0002\u0002ϹϺ\u0003\u0002\u0002\u0002Ϻs\u0003\u0002\u0002\u0002ϻІ\u0007}\u0002\u0002ϼЇ\u0005Ði\u0002ϽϾ\u0005Æd\u0002ϾϿ\u0007\b\u0002\u0002ϿЁ\u0003\u0002\u0002\u0002ЀϽ\u0003\u0002\u0002\u0002ЀЁ\u0003\u0002\u0002\u0002ЁЄ\u0003\u0002\u0002\u0002ЂЅ\u0005Èe\u0002ЃЅ\u0005Ôk\u0002ЄЂ\u0003\u0002\u0002\u0002ЄЃ\u0003\u0002\u0002\u0002ЅЇ\u0003\u0002\u0002\u0002Іϼ\u0003\u0002\u0002\u0002ІЀ\u0003\u0002\u0002\u0002ІЇ\u0003\u0002\u0002\u0002Їu\u0003\u0002\u0002\u0002ЈЊ\u0007~\u0002\u0002ЉЋ\u0007\u0085\u0002\u0002ЊЉ\u0003\u0002\u0002\u0002ЊЋ\u0003\u0002\u0002\u0002ЋЌ\u0003\u0002\u0002\u0002ЌЍ\u0005Þp\u0002Ѝw\u0003\u0002\u0002\u0002ЎГ\u0007\u0083\u0002\u0002ЏБ\u0007\u008d\u0002\u0002АВ\u0005âr\u0002БА\u0003\u0002\u0002\u0002БВ\u0003\u0002\u0002\u0002ВД\u0003\u0002\u0002\u0002ГЏ\u0003\u0002\u0002\u0002ГД\u0003\u0002\u0002\u0002ДК\u0003\u0002\u0002\u0002ЕЗ\u0007\u008c\u0002\u0002ЖИ\u0007\u0085\u0002\u0002ЗЖ\u0003\u0002\u0002\u0002ЗИ\u0003\u0002\u0002\u0002ИЙ\u0003\u0002\u0002\u0002ЙЛ\u0005Þp\u0002КЕ\u0003\u0002\u0002\u0002КЛ\u0003\u0002\u0002\u0002Лy\u0003\u0002\u0002\u0002МН\u0007\u0085\u0002\u0002НО\u0005Þp\u0002О{\u0003\u0002\u0002\u0002ПС\u0007\u0099\u0002\u0002РТ\u0007z\u0002\u0002СР\u0003\u0002\u0002\u0002СТ\u0003\u0002\u0002\u0002ТУ\u0003\u0002\u0002\u0002УШ\u0005¢R\u0002ФХ\u0007\u000b\u0002\u0002ХЧ\u0005¢R\u0002ЦФ\u0003\u0002\u0002\u0002ЧЪ\u0003\u0002\u0002\u0002ШЦ\u0003\u0002\u0002\u0002ШЩ\u0003\u0002\u0002\u0002ЩЬ\u0003\u0002\u0002\u0002ЪШ\u0003\u0002\u0002\u0002ЫП\u0003\u0002\u0002\u0002ЫЬ\u0003\u0002\u0002\u0002ЬЭ\u0003\u0002\u0002\u0002Эи\u0005®X\u0002ЮЯ\u0007s\u0002\u0002Яа\u0007.\u0002\u0002ае\u0005\u009eP\u0002бв\u0007\u000b\u0002\u0002вд\u0005\u009eP\u0002гб\u0003\u0002\u0002\u0002дз\u0003\u0002\u0002\u0002ег\u0003\u0002\u0002\u0002еж\u0003\u0002\u0002\u0002жй\u0003\u0002\u0002\u0002зе\u0003\u0002\u0002\u0002иЮ\u0003\u0002\u0002\u0002ий\u0003\u0002\u0002\u0002йр\u0003\u0002\u0002\u0002кл\u0007h\u0002\u0002ло\u0005\u0090I\u0002мн\t\u0007\u0002\u0002нп\u0005\u0090I\u0002ом\u0003\u0002\u0002\u0002оп\u0003\u0002\u0002\u0002пс\u0003\u0002\u0002\u0002рк\u0003\u0002\u0002\u0002рс\u0003\u0002\u0002\u0002с}\u0003\u0002\u0002\u0002тф\u0007\u0099\u0002\u0002ух\u0007z\u0002\u0002фу\u0003\u0002\u0002\u0002фх\u0003\u0002\u0002\u0002хц\u0003\u0002\u0002\u0002цы\u0005¢R\u0002чш\u0007\u000b\u0002\u0002шъ\u0005¢R\u0002щч\u0003\u0002\u0002\u0002ъэ\u0003\u0002\u0002\u0002ыщ\u0003\u0002\u0002\u0002ыь\u0003\u0002\u0002\u0002ья\u0003\u0002\u0002\u0002эы\u0003\u0002\u0002\u0002ют\u0003\u0002\u0002\u0002юя\u0003\u0002\u0002\u0002яѐ\u0003\u0002\u0002\u0002ѐі\u0005\u0080A\u0002ёђ\u0005°Y\u0002ђѓ\u0005\u0080A\u0002ѓѕ\u0003\u0002\u0002\u0002єё\u0003\u0002\u0002\u0002ѕј\u0003\u0002\u0002\u0002іє\u0003\u0002\u0002\u0002ії\u0003\u0002\u0002\u0002їѣ\u0003\u0002\u0002\u0002јі\u0003\u0002\u0002\u0002љњ\u0007s\u0002\u0002њћ\u0007.\u0002\u0002ћѠ\u0005\u009eP\u0002ќѝ\u0007\u000b\u0002\u0002ѝџ\u0005\u009eP\u0002ўќ\u0003\u0002\u0002\u0002џѢ\u0003\u0002\u0002\u0002Ѡў\u0003\u0002\u0002\u0002Ѡѡ\u0003\u0002\u0002\u0002ѡѤ\u0003\u0002\u0002\u0002ѢѠ\u0003\u0002\u0002\u0002ѣљ\u0003\u0002\u0002\u0002ѣѤ\u0003\u0002\u0002\u0002Ѥѫ\u0003\u0002\u0002\u0002ѥѦ\u0007h\u0002\u0002Ѧѩ\u0005\u0090I\u0002ѧѨ\t\u0007\u0002\u0002ѨѪ\u0005\u0090I\u0002ѩѧ\u0003\u0002\u0002\u0002ѩѪ\u0003\u0002\u0002\u0002ѪѬ\u0003\u0002\u0002\u0002ѫѥ\u0003\u0002\u0002\u0002ѫѬ\u0003\u0002\u0002\u0002Ѭ\u007f\u0003\u0002\u0002\u0002ѭѯ\u0007\u0086\u0002\u0002ѮѰ\t\t\u0002\u0002ѯѮ\u0003\u0002\u0002\u0002ѯѰ\u0003\u0002\u0002\u0002Ѱѱ\u0003\u0002\u0002\u0002ѱѶ\u0005¤S\u0002Ѳѳ\u0007\u000b\u0002\u0002ѳѵ\u0005¤S\u0002ѴѲ\u0003\u0002\u0002\u0002ѵѸ\u0003\u0002\u0002\u0002ѶѴ\u0003\u0002\u0002\u0002Ѷѷ\u0003\u0002\u0002\u0002ѷ҅\u0003\u0002\u0002\u0002ѸѶ\u0003\u0002\u0002\u0002ѹ҃\u0007Q\u0002\u0002Ѻѿ\u0005¦T\u0002ѻѼ\u0007\u000b\u0002\u0002ѼѾ\u0005¦T\u0002ѽѻ\u0003\u0002\u0002\u0002Ѿҁ\u0003\u0002\u0002\u0002ѿѽ\u0003\u0002\u0002\u0002ѿҀ\u0003\u0002\u0002\u0002Ҁ҄\u0003\u0002\u0002\u0002ҁѿ\u0003\u0002\u0002\u0002҂҄\u0005¨U\u0002҃Ѻ\u0003\u0002\u0002\u0002҃҂\u0003\u0002\u0002\u0002҄҆\u0003\u0002\u0002\u0002҅ѹ\u0003\u0002\u0002\u0002҅҆\u0003\u0002\u0002\u0002҆҉\u0003\u0002\u0002\u0002҇҈\u0007\u0098\u0002\u0002҈Ҋ\u0005\u0090I\u0002҉҇\u0003\u0002\u0002\u0002҉Ҋ\u0003\u0002\u0002\u0002Ҋҙ\u0003\u0002\u0002\u0002ҋҌ\u0007T\u0002\u0002Ҍҍ\u0007.\u0002\u0002ҍҒ\u0005\u0090I\u0002Ҏҏ\u0007\u000b\u0002\u0002ҏґ\u0005\u0090I\u0002ҐҎ\u0003\u0002\u0002\u0002ґҔ\u0003\u0002\u0002\u0002ҒҐ\u0003\u0002\u0002\u0002Ғғ\u0003\u0002\u0002\u0002ғҗ\u0003\u0002\u0002\u0002ҔҒ\u0003\u0002\u0002\u0002ҕҖ\u0007U\u0002\u0002ҖҘ\u0005\u0090I\u0002җҕ\u0003\u0002\u0002\u0002җҘ\u0003\u0002\u0002\u0002ҘҚ\u0003\u0002\u0002\u0002ҙҋ\u0003\u0002\u0002\u0002ҙҚ\u0003\u0002\u0002\u0002ҚҸ\u0003\u0002\u0002\u0002қҜ\u0007\u0094\u0002\u0002Ҝҝ\u0007\t\u0002\u0002ҝҢ\u0005\u0090I\u0002Ҟҟ\u0007\u000b\u0002\u0002ҟҡ\u0005\u0090I\u0002ҠҞ\u0003\u0002\u0002\u0002ҡҤ\u0003\u0002\u0002\u0002ҢҠ\u0003\u0002\u0002\u0002Ңң\u0003\u0002\u0002\u0002ңҥ\u0003\u0002\u0002\u0002ҤҢ\u0003\u0002\u0002\u0002ҥҴ\u0007\n\u0002\u0002Ҧҧ\u0007\u000b\u0002\u0002ҧҨ\u0007\t\u0002\u0002Ҩҭ\u0005\u0090I\u0002ҩҪ\u0007\u000b\u0002\u0002ҪҬ\u0005\u0090I\u0002ҫҩ\u0003\u0002\u0002\u0002Ҭү\u0003\u0002\u0002\u0002ҭҫ\u0003\u0002\u0002\u0002ҭҮ\u0003\u0002\u0002\u0002ҮҰ\u0003\u0002\u0002\u0002үҭ\u0003\u0002\u0002\u0002Ұұ\u0007\n\u0002\u0002ұҳ\u0003\u0002\u0002\u0002ҲҦ\u0003\u0002\u0002\u0002ҳҶ\u0003\u0002\u0002\u0002ҴҲ\u0003\u0002\u0002\u0002Ҵҵ\u0003\u0002\u0002\u0002ҵҸ\u0003\u0002\u0002\u0002ҶҴ\u0003\u0002\u0002\u0002ҷѭ\u0003\u0002\u0002\u0002ҷқ\u0003\u0002\u0002\u0002Ҹ\u0081\u0003\u0002\u0002\u0002ҹһ\u0005\u009aN\u0002Һҹ\u0003\u0002\u0002\u0002Һһ\u0003\u0002\u0002\u0002һҼ\u0003\u0002\u0002\u0002ҼӇ\u0007\u0091\u0002\u0002ҽҾ\u0007r\u0002\u0002Ҿӈ\u0007\u0083\u0002\u0002ҿӀ\u0007r\u0002\u0002Ӏӈ\u0007\u001f\u0002\u0002Ӂӂ\u0007r\u0002\u0002ӂӈ\u0007\u0080\u0002\u0002Ӄӄ\u0007r\u0002\u0002ӄӈ\u0007N\u0002\u0002Ӆӆ\u0007r\u0002\u0002ӆӈ\u0007W\u0002\u0002Ӈҽ\u0003\u0002\u0002\u0002Ӈҿ\u0003\u0002\u0002\u0002ӇӁ\u0003\u0002\u0002\u0002ӇӃ\u0003\u0002\u0002\u0002ӇӅ\u0003\u0002\u0002\u0002Ӈӈ\u0003\u0002\u0002\u0002ӈӉ\u0003\u0002\u0002\u0002Ӊӊ\u0005\u009cO\u0002ӊӋ\u0007\u0087\u0002\u0002Ӌӌ\u0005Îh\u0002ӌӍ\u0007\f\u0002\u0002Ӎӕ\u0005\u0090I\u0002ӎӏ\u0007\u000b\u0002\u0002ӏӐ\u0005Îh\u0002Ӑӑ\u0007\f\u0002\u0002ӑӒ\u0005\u0090I\u0002ӒӔ\u0003\u0002\u0002\u0002ӓӎ\u0003\u0002\u0002\u0002Ӕӗ\u0003\u0002\u0002\u0002ӕӓ\u0003\u0002\u0002\u0002ӕӖ\u0003\u0002\u0002\u0002ӖӚ\u0003\u0002\u0002\u0002ӗӕ\u0003\u0002\u0002\u0002Әә\u0007\u0098\u0002\u0002әӛ\u0005\u0090I\u0002ӚӘ\u0003\u0002\u0002\u0002Ӛӛ\u0003\u0002\u0002\u0002ӛ\u0083\u0003\u0002\u0002\u0002ӜӞ\u0005\u009aN\u0002ӝӜ\u0003\u0002\u0002\u0002ӝӞ\u0003\u0002\u0002\u0002Ӟӟ\u0003\u0002\u0002\u0002ӟӪ\u0007\u0091\u0002\u0002Ӡӡ\u0007r\u0002\u0002ӡӫ\u0007\u0083\u0002\u0002Ӣӣ\u0007r\u0002\u0002ӣӫ\u0007\u001f\u0002\u0002Ӥӥ\u0007r\u0002\u0002ӥӫ\u0007\u0080\u0002\u0002Ӧӧ\u0007r\u0002\u0002ӧӫ\u0007N\u0002\u0002Өө\u0007r\u0002\u0002өӫ\u0007W\u0002\u0002ӪӠ\u0003\u0002\u0002\u0002ӪӢ\u0003\u0002\u0002\u0002ӪӤ\u0003\u0002\u0002\u0002ӪӦ\u0003\u0002\u0002\u0002ӪӨ\u0003\u0002\u0002\u0002Ӫӫ\u0003\u0002\u0002\u0002ӫӬ\u0003\u0002\u0002\u0002Ӭӭ\u0005\u009cO\u0002ӭӮ\u0007\u0087\u0002\u0002Ӯӯ\u0005Îh\u0002ӯӰ\u0007\f\u0002\u0002ӰӸ\u0005\u0090I\u0002ӱӲ\u0007\u000b\u0002\u0002Ӳӳ\u0005Îh\u0002ӳӴ\u0007\f\u0002\u0002Ӵӵ\u0005\u0090I\u0002ӵӷ\u0003\u0002\u0002\u0002Ӷӱ\u0003\u0002\u0002\u0002ӷӺ\u0003\u0002\u0002\u0002ӸӶ\u0003\u0002\u0002\u0002Ӹӹ\u0003\u0002\u0002\u0002ӹӽ\u0003\u0002\u0002\u0002ӺӸ\u0003\u0002\u0002\u0002ӻӼ\u0007\u0098\u0002\u0002ӼӾ\u0005\u0090I\u0002ӽӻ\u0003\u0002\u0002\u0002ӽӾ\u0003\u0002\u0002\u0002Ӿԑ\u0003\u0002\u0002\u0002ӿԀ\u0007s\u0002\u0002Ԁԁ\u0007.\u0002\u0002ԁԆ\u0005\u009eP\u0002Ԃԃ\u0007\u000b\u0002\u0002ԃԅ\u0005\u009eP\u0002ԄԂ\u0003\u0002\u0002\u0002ԅԈ\u0003\u0002\u0002\u0002ԆԄ\u0003\u0002\u0002\u0002Ԇԇ\u0003\u0002\u0002\u0002ԇԊ\u0003\u0002\u0002\u0002ԈԆ\u0003\u0002\u0002\u0002ԉӿ\u0003\u0002\u0002\u0002ԉԊ\u0003\u0002\u0002\u0002Ԋԋ\u0003\u0002\u0002\u0002ԋԌ\u0007h\u0002\u0002Ԍԏ\u0005\u0090I\u0002ԍԎ\t\u0007\u0002\u0002ԎԐ\u0005\u0090I\u0002ԏԍ\u0003\u0002\u0002\u0002ԏԐ\u0003\u0002\u0002\u0002ԐԒ\u0003\u0002\u0002\u0002ԑԉ\u0003\u0002\u0002\u0002ԑԒ\u0003\u0002\u0002\u0002Ԓ\u0085\u0003\u0002\u0002\u0002ԓԔ\u0007\u0093\u0002\u0002Ԕ\u0087\u0003\u0002\u0002\u0002ԕԗ\u0005Îh\u0002ԖԘ\u0005\u008aF\u0002ԗԖ\u0003\u0002\u0002\u0002ԗԘ\u0003\u0002\u0002\u0002ԘԜ\u0003\u0002\u0002\u0002ԙԛ\u0005\u008cG\u0002Ԛԙ\u0003\u0002\u0002\u0002ԛԞ\u0003\u0002\u0002\u0002ԜԚ\u0003\u0002\u0002\u0002Ԝԝ\u0003\u0002\u0002\u0002ԝ\u0089\u0003\u0002\u0002\u0002ԞԜ\u0003\u0002\u0002\u0002ԟԡ\u0005Âb\u0002Ԡԟ\u0003\u0002\u0002\u0002ԡԢ\u0003\u0002\u0002\u0002ԢԠ\u0003\u0002\u0002\u0002Ԣԣ\u0003\u0002\u0002\u0002ԣԮ\u0003\u0002\u0002\u0002Ԥԥ\u0007\t\u0002\u0002ԥԦ\u0005´[\u0002Ԧԧ\u0007\n\u0002\u0002ԧԯ\u0003\u0002\u0002\u0002Ԩԩ\u0007\t\u0002\u0002ԩԪ\u0005´[\u0002Ԫԫ\u0007\u000b\u0002\u0002ԫԬ\u0005´[\u0002Ԭԭ\u0007\n\u0002\u0002ԭԯ\u0003\u0002\u0002\u0002ԮԤ\u0003\u0002\u0002\u0002ԮԨ\u0003\u0002\u0002\u0002Ԯԯ\u0003\u0002\u0002\u0002ԯ\u008b\u0003\u0002\u0002\u0002\u0530Ա\u00077\u0002\u0002ԱԳ\u0005Âb\u0002Բ\u0530\u0003\u0002\u0002\u0002ԲԳ\u0003\u0002\u0002\u0002ԳՕ\u0003\u0002\u0002\u0002ԴԵ\u0007w\u0002\u0002ԵԷ\u0007e\u0002\u0002ԶԸ\t\u0004\u0002\u0002ԷԶ\u0003\u0002\u0002\u0002ԷԸ\u0003\u0002\u0002\u0002ԸԹ\u0003\u0002\u0002\u0002ԹԻ\u0005\u008eH\u0002ԺԼ\u0007*\u0002\u0002ԻԺ\u0003\u0002\u0002\u0002ԻԼ\u0003\u0002\u0002\u0002ԼՖ\u0003\u0002\u0002\u0002ԽԿ\u0007l\u0002\u0002ԾԽ\u0003\u0002\u0002\u0002ԾԿ\u0003\u0002\u0002\u0002ԿՀ\u0003\u0002\u0002\u0002ՀՁ\u0007n\u0002\u0002ՁՖ\u0005\u008eH\u0002ՂՃ\u0007\u0090\u0002\u0002ՃՖ\u0005\u008eH\u0002ՄՅ\u00072\u0002\u0002ՅՆ\u0007\t\u0002\u0002ՆՇ\u0005\u0090I\u0002ՇՈ\u0007\n\u0002\u0002ՈՖ\u0003\u0002\u0002\u0002ՉՐ\u0007>\u0002\u0002ՊՑ\u0005´[\u0002ՋՑ\u0005¶\\\u0002ՌՍ\u0007\t\u0002\u0002ՍՎ\u0005\u0090I\u0002ՎՏ\u0007\n\u0002\u0002ՏՑ\u0003\u0002\u0002\u0002ՐՊ\u0003\u0002\u0002\u0002ՐՋ\u0003\u0002\u0002\u0002ՐՌ\u0003\u0002\u0002\u0002ՑՖ\u0003\u0002\u0002\u0002ՒՓ\u00073\u0002\u0002ՓՖ\u0005Ði\u0002ՔՖ\u0005\u0092J\u0002ՕԴ\u0003\u0002\u0002\u0002ՕԾ\u0003\u0002\u0002\u0002ՕՂ\u0003\u0002\u0002\u0002ՕՄ\u0003\u0002\u0002\u0002ՕՉ\u0003\u0002\u0002\u0002ՕՒ\u0003\u0002\u0002\u0002ՕՔ\u0003\u0002\u0002\u0002Ֆ\u008d\u0003\u0002\u0002\u0002\u0557\u0558\u0007q\u0002\u0002\u0558ՙ\u00076\u0002\u0002ՙ՛\t\n\u0002\u0002՚\u0557\u0003\u0002\u0002\u0002՚՛\u0003\u0002\u0002\u0002՛\u008f\u0003\u0002\u0002\u0002՜՝\bI\u0001\u0002՝֩\u0005¶\\\u0002՞֩\u0007\u009d\u0002\u0002՟\u0560\u0005Æd\u0002\u0560ա\u0007\b\u0002\u0002ագ\u0003\u0002\u0002\u0002բ՟\u0003\u0002\u0002\u0002բգ\u0003\u0002\u0002\u0002գդ\u0003\u0002\u0002\u0002դե\u0005Èe\u0002եզ\u0007\b\u0002\u0002զը\u0003\u0002\u0002\u0002էբ\u0003\u0002\u0002\u0002էը\u0003\u0002\u0002\u0002ըթ\u0003\u0002\u0002\u0002թ֩\u0005Îh\u0002ժի\u0005¸]\u0002իլ\u0005\u0090I\u0017լ֩\u0003\u0002\u0002\u0002խծ\u0005Äc\u0002ծջ\u0007\t\u0002\u0002կձ\u0007D\u0002\u0002հկ\u0003\u0002\u0002\u0002հձ\u0003\u0002\u0002\u0002ձղ\u0003\u0002\u0002\u0002ղշ\u0005\u0090I\u0002ճմ\u0007\u000b\u0002\u0002մն\u0005\u0090I\u0002յճ\u0003\u0002\u0002\u0002նչ\u0003\u0002\u0002\u0002շյ\u0003\u0002\u0002\u0002շո\u0003\u0002\u0002\u0002ոռ\u0003\u0002\u0002\u0002չշ\u0003\u0002\u0002\u0002պռ\u0007\r\u0002\u0002ջհ\u0003\u0002\u0002\u0002ջպ\u0003\u0002\u0002\u0002ջռ\u0003\u0002\u0002\u0002ռս\u0003\u0002\u0002\u0002սվ\u0007\n\u0002\u0002վ֩\u0003\u0002\u0002\u0002տր\u0007\t\u0002\u0002րց\u0005\u0090I\u0002ցւ\u0007\n\u0002\u0002ւ֩\u0003\u0002\u0002\u0002փք\u00071\u0002\u0002քօ\u0007\t\u0002\u0002օֆ\u0005\u0090I\u0002ֆև\u0007'\u0002\u0002և\u0588\u0005\u008aF\u0002\u0588։\u0007\n\u0002\u0002։֩\u0003\u0002\u0002\u0002֊\u058c\u0007l\u0002\u0002\u058b֊\u0003\u0002\u0002\u0002\u058b\u058c\u0003\u0002\u0002\u0002\u058c֍\u0003\u0002\u0002\u0002֍֏\u0007L\u0002\u0002֎\u058b\u0003\u0002\u0002\u0002֎֏\u0003\u0002\u0002\u0002֏\u0590\u0003\u0002\u0002\u0002\u0590֑\u0007\t\u0002\u0002֑֒\u0005~@\u0002֒֓\u0007\n\u0002\u0002֓֩\u0003\u0002\u0002\u0002֖֔\u00070\u0002\u0002֕֗\u0005\u0090I\u0002֖֕\u0003\u0002\u0002\u0002֖֗\u0003\u0002\u0002\u0002֗֝\u0003\u0002\u0002\u0002֘֙\u0007\u0097\u0002\u0002֚֙\u0005\u0090I\u0002֛֚\u0007\u008b\u0002\u0002֛֜\u0005\u0090I\u0002֜֞\u0003\u0002\u0002\u0002֝֘\u0003\u0002\u0002\u0002֞֟\u0003\u0002\u0002\u0002֟֝\u0003\u0002\u0002\u0002֟֠\u0003\u0002\u0002\u0002֣֠\u0003\u0002\u0002\u0002֢֡\u0007G\u0002\u0002֢֤\u0005\u0090I\u0002֣֡\u0003\u0002\u0002\u0002֣֤\u0003\u0002\u0002\u0002֤֥\u0003\u0002\u0002\u0002֥֦\u0007H\u0002\u0002֦֩\u0003\u0002\u0002\u0002֧֩\u0005\u0094K\u0002֨՜\u0003\u0002\u0002\u0002֨՞\u0003\u0002\u0002\u0002֨է\u0003\u0002\u0002\u0002֨ժ\u0003\u0002\u0002\u0002֨խ\u0003\u0002\u0002\u0002֨տ\u0003\u0002\u0002\u0002֨փ\u0003\u0002\u0002\u0002֨֎\u0003\u0002\u0002\u0002֨֔\u0003\u0002\u0002\u0002֧֨\u0003\u0002\u0002\u0002֩؎\u0003\u0002\u0002\u0002֪֫\f\u0016\u0002\u0002֫֬\u0007\u0011\u0002\u0002֬؍\u0005\u0090I\u0017֭֮\f\u0015\u0002\u0002֮֯\t\u000b\u0002\u0002֯؍\u0005\u0090I\u0016ְֱ\f\u0014\u0002\u0002ֱֲ\t\f\u0002\u0002ֲ؍\u0005\u0090I\u0015ֳִ\f\u0013\u0002\u0002ִֵ\t\r\u0002\u0002ֵ؍\u0005\u0090I\u0014ֶַ\f\u0012\u0002\u0002ַָ\t\u000e\u0002\u0002ָ؍\u0005\u0090I\u0013ֹ׆\f\u0011\u0002\u0002ׇֺ\u0007\f\u0002\u0002ׇֻ\u0007\u001c\u0002\u0002ׇּ\u0007\u001d\u0002\u0002ׇֽ\u0007\u001e\u0002\u0002־ׇ\u0007b\u0002\u0002ֿ׀\u0007b\u0002\u0002׀ׇ\u0007l\u0002\u0002ׇׁ\u0007Y\u0002\u0002ׇׂ\u0007g\u0002\u0002׃ׇ\u0007S\u0002\u0002ׇׄ\u0007i\u0002\u0002ׇׅ\u0007|\u0002\u0002׆ֺ\u0003\u0002\u0002\u0002׆ֻ\u0003\u0002\u0002\u0002׆ּ\u0003\u0002\u0002\u0002׆ֽ\u0003\u0002\u0002\u0002׆־\u0003\u0002\u0002\u0002׆ֿ\u0003\u0002\u0002\u0002׆ׁ\u0003\u0002\u0002\u0002׆ׂ\u0003\u0002\u0002\u0002׆׃\u0003\u0002\u0002\u0002׆ׄ\u0003\u0002\u0002\u0002׆ׅ\u0003\u0002\u0002\u0002ׇ\u05c8\u0003\u0002\u0002\u0002\u05c8؍\u0005\u0090I\u0012\u05c9\u05ca\f\u0010\u0002\u0002\u05ca\u05cb\u0007&\u0002\u0002\u05cb؍\u0005\u0090I\u0011\u05cc\u05cd\f\u000f\u0002\u0002\u05cd\u05ce\u0007r\u0002\u0002\u05ce؍\u0005\u0090I\u0010\u05cfא\f\b\u0002\u0002אג\u0007b\u0002\u0002בד\u0007l\u0002\u0002גב\u0003\u0002\u0002\u0002גד\u0003\u0002\u0002\u0002דה\u0003\u0002\u0002\u0002ה؍\u0005\u0090I\tוח\f\u0007\u0002\u0002זט\u0007l\u0002\u0002חז\u0003\u0002\u0002\u0002חט\u0003\u0002\u0002\u0002טי\u0003\u0002\u0002\u0002יך\u0007-\u0002\u0002ךכ\u0005\u0090I\u0002כל\u0007&\u0002\u0002לם\u0005\u0090I\bם؍\u0003\u0002\u0002\u0002מן\f\u000b\u0002\u0002ןנ\u00073\u0002\u0002נ؍\u0005Ði\u0002סף\f\n\u0002\u0002עפ\u0007l\u0002\u0002ףע\u0003\u0002\u0002\u0002ףפ\u0003\u0002\u0002\u0002פץ\u0003\u0002\u0002\u0002ץצ\t\u000f\u0002\u0002צש\u0005\u0090I\u0002קר\u0007I\u0002\u0002רת\u0005\u0090I\u0002שק\u0003\u0002\u0002\u0002שת\u0003\u0002\u0002\u0002ת؍\u0003\u0002\u0002\u0002\u05ebװ\f\t\u0002\u0002\u05ecױ\u0007c\u0002\u0002\u05edױ\u0007m\u0002\u0002\u05ee\u05ef\u0007l\u0002\u0002\u05efױ\u0007n\u0002\u0002װ\u05ec\u0003\u0002\u0002\u0002װ\u05ed\u0003\u0002\u0002\u0002װ\u05ee\u0003\u0002\u0002\u0002ױ؍\u0003\u0002\u0002\u0002ײ״\f\u0006\u0002\u0002׳\u05f5\u0007l\u0002\u0002״׳\u0003\u0002\u0002\u0002״\u05f5\u0003\u0002\u0002\u0002\u05f5\u05f6\u0003\u0002\u0002\u0002\u05f6؊\u0007Y\u0002\u0002\u05f7\u0601\u0007\t\u0002\u0002\u05f8\u0602\u0005~@\u0002\u05f9\u05fe\u0005\u0090I\u0002\u05fa\u05fb\u0007\u000b\u0002\u0002\u05fb\u05fd\u0005\u0090I\u0002\u05fc\u05fa\u0003\u0002\u0002\u0002\u05fd\u0600\u0003\u0002\u0002\u0002\u05fe\u05fc\u0003\u0002\u0002\u0002\u05fe\u05ff\u0003\u0002\u0002\u0002\u05ff\u0602\u0003\u0002\u0002\u0002\u0600\u05fe\u0003\u0002\u0002\u0002\u0601\u05f8\u0003\u0002\u0002\u0002\u0601\u05f9\u0003\u0002\u0002\u0002\u0601\u0602\u0003\u0002\u0002\u0002\u0602\u0603\u0003\u0002\u0002\u0002\u0603؋\u0007\n\u0002\u0002\u0604\u0605\u0005Æd\u0002\u0605؆\u0007\b\u0002\u0002؆؈\u0003\u0002\u0002\u0002؇\u0604\u0003\u0002\u0002\u0002؇؈\u0003\u0002\u0002\u0002؈؉\u0003\u0002\u0002\u0002؉؋\u0005Èe\u0002؊\u05f7\u0003\u0002\u0002\u0002؊؇\u0003\u0002\u0002\u0002؋؍\u0003\u0002\u0002\u0002،֪\u0003\u0002\u0002\u0002،֭\u0003\u0002\u0002\u0002،ְ\u0003\u0002\u0002\u0002،ֳ\u0003\u0002\u0002\u0002،ֶ\u0003\u0002\u0002\u0002،ֹ\u0003\u0002\u0002\u0002،\u05c9\u0003\u0002\u0002\u0002،\u05cc\u0003\u0002\u0002\u0002،\u05cf\u0003\u0002\u0002\u0002،ו\u0003\u0002\u0002\u0002،מ\u0003\u0002\u0002\u0002،ס\u0003\u0002\u0002\u0002،\u05eb\u0003\u0002\u0002\u0002،ײ\u0003\u0002\u0002\u0002؍ؐ\u0003\u0002\u0002\u0002؎،\u0003\u0002\u0002\u0002؎؏\u0003\u0002\u0002\u0002؏\u0091\u0003\u0002\u0002\u0002ؐ؎\u0003\u0002\u0002\u0002ؑؒ\u0007{\u0002\u0002ؒ؞\u0005Òj\u0002ؓؔ\u0007\t\u0002\u0002ؙؔ\u0005Îh\u0002ؕؖ\u0007\u000b\u0002\u0002ؘؖ\u0005Îh\u0002ؗؕ\u0003\u0002\u0002\u0002ؘ؛\u0003\u0002\u0002\u0002ؙؗ\u0003\u0002\u0002\u0002ؙؚ\u0003\u0002\u0002\u0002ؚ\u061c\u0003\u0002\u0002\u0002؛ؙ\u0003\u0002\u0002\u0002\u061c\u061d\u0007\n\u0002\u0002\u061d؟\u0003\u0002\u0002\u0002؞ؓ\u0003\u0002\u0002\u0002؞؟\u0003\u0002\u0002\u0002؟ز\u0003\u0002\u0002\u0002ؠء\u0007q\u0002\u0002ءت\t\u0010\u0002\u0002آأ\u0007\u0087\u0002\u0002أث\u0007n\u0002\u0002ؤإ\u0007\u0087\u0002\u0002إث\u0007>\u0002\u0002ئث\u0007/\u0002\u0002اث\u0007\u0081\u0002\u0002بة\u0007k\u0002\u0002ةث\u0007 \u0002\u0002تآ\u0003\u0002\u0002\u0002تؤ\u0003\u0002\u0002\u0002تئ\u0003\u0002\u0002\u0002تا\u0003\u0002\u0002\u0002تب\u0003\u0002\u0002\u0002ثد\u0003\u0002\u0002\u0002جح\u0007i\u0002\u0002حد\u0005Âb\u0002خؠ\u0003\u0002\u0002\u0002خج\u0003\u0002\u0002\u0002در\u0003\u0002\u0002\u0002ذخ\u0003\u0002\u0002\u0002رش\u0003\u0002\u0002\u0002زذ\u0003\u0002\u0002\u0002زس\u0003\u0002\u0002\u0002سؿ\u0003\u0002\u0002\u0002شز\u0003\u0002\u0002\u0002صط\u0007l\u0002\u0002ضص\u0003\u0002\u0002\u0002ضط\u0003\u0002\u0002\u0002طظ\u0003\u0002\u0002\u0002ظؽ\u0007?\u0002\u0002عغ\u0007\\\u0002\u0002غؾ\u0007@\u0002\u0002ػؼ\u0007\\\u0002\u0002ؼؾ\u0007X\u0002\u0002ؽع\u0003\u0002\u0002\u0002ؽػ\u0003\u0002\u0002\u0002ؽؾ\u0003\u0002\u0002\u0002ؾـ\u0003\u0002\u0002\u0002ؿض\u0003\u0002\u0002\u0002ؿـ\u0003\u0002\u0002\u0002ـ\u0093\u0003\u0002\u0002\u0002فق\u0007y\u0002\u0002قه\u0007\t\u0002\u0002كو\u0007W\u0002\u0002لم\t\u0011\u0002\u0002من\u0007\u000b\u0002\u0002نو\u0005º^\u0002هك\u0003\u0002\u0002\u0002هل\u0003\u0002\u0002\u0002وى\u0003\u0002\u0002\u0002ىي\u0007\n\u0002\u0002ي\u0095\u0003\u0002\u0002\u0002ًَ\u0005Îh\u0002ٌٍ\u00073\u0002\u0002ٍُ\u0005Ði\u0002ٌَ\u0003\u0002\u0002\u0002َُ\u0003\u0002\u0002\u0002ُّ\u0003\u0002\u0002\u0002ِْ\t\u0004\u0002\u0002ِّ\u0003\u0002\u0002\u0002ّْ\u0003\u0002\u0002\u0002ْ\u0097\u0003\u0002\u0002\u0002ٓٔ\u00077\u0002\u0002ٖٔ\u0005Âb\u0002ٕٓ\u0003\u0002\u0002\u0002ٕٖ\u0003\u0002\u0002\u0002ٖٻ\u0003\u0002\u0002\u0002ٗ٘\u0007w\u0002\u0002٘ٛ\u0007e\u0002\u0002ٙٛ\u0007\u0090\u0002\u0002ٚٗ\u0003\u0002\u0002\u0002ٚٙ\u0003\u0002\u0002\u0002ٜٛ\u0003\u0002\u0002\u0002ٜٝ\u0007\t\u0002\u0002ٝ٢\u0005\u0096L\u0002ٟٞ\u0007\u000b\u0002\u0002ٟ١\u0005\u0096L\u0002٠ٞ\u0003\u0002\u0002\u0002١٤\u0003\u0002\u0002\u0002٢٠\u0003\u0002\u0002\u0002٢٣\u0003\u0002\u0002\u0002٣٥\u0003\u0002\u0002\u0002٤٢\u0003\u0002\u0002\u0002٥٦\u0007\n\u0002\u0002٦٧\u0005\u008eH\u0002٧ټ\u0003\u0002\u0002\u0002٨٩\u00072\u0002\u0002٩٪\u0007\t\u0002\u0002٪٫\u0005\u0090I\u0002٫٬\u0007\n\u0002\u0002٬ټ\u0003\u0002\u0002\u0002٭ٮ\u0007P\u0002\u0002ٮٯ\u0007e\u0002\u0002ٯٰ\u0007\t\u0002\u0002ٰٵ\u0005Îh\u0002ٱٲ\u0007\u000b\u0002\u0002ٲٴ\u0005Îh\u0002ٳٱ\u0003\u0002\u0002\u0002ٴٷ\u0003\u0002\u0002\u0002ٵٳ\u0003\u0002\u0002\u0002ٵٶ\u0003\u0002\u0002\u0002ٶٸ\u0003\u0002\u0002\u0002ٷٵ\u0003\u0002\u0002\u0002ٸٹ\u0007\n\u0002\u0002ٹٺ\u0005\u0092J\u0002ٺټ\u0003\u0002\u0002\u0002ٻٚ\u0003\u0002\u0002\u0002ٻ٨\u0003\u0002\u0002\u0002ٻ٭\u0003\u0002\u0002\u0002ټ\u0099\u0003\u0002\u0002\u0002ٽٿ\u0007\u0099\u0002\u0002پڀ\u0007z\u0002\u0002ٿپ\u0003\u0002\u0002\u0002ٿڀ\u0003\u0002\u0002\u0002ڀځ\u0003\u0002\u0002\u0002ځڂ\u0005²Z\u0002ڂڃ\u0007'\u0002\u0002ڃڄ\u0007\t\u0002\u0002ڄڅ\u0005~@\u0002څڏ\u0007\n\u0002\u0002چڇ\u0007\u000b\u0002\u0002ڇڈ\u0005²Z\u0002ڈډ\u0007'\u0002\u0002ډڊ\u0007\t\u0002\u0002ڊڋ\u0005~@\u0002ڋڌ\u0007\n\u0002\u0002ڌڎ\u0003\u0002\u0002\u0002ڍچ\u0003\u0002\u0002\u0002ڎڑ\u0003\u0002\u0002\u0002ڏڍ\u0003\u0002\u0002\u0002ڏڐ\u0003\u0002\u0002\u0002ڐ\u009b\u0003\u0002\u0002\u0002ڑڏ\u0003\u0002\u0002\u0002ڒړ\u0005Æd\u0002ړڔ\u0007\b\u0002\u0002ڔږ\u0003\u0002\u0002\u0002ڕڒ\u0003\u0002\u0002\u0002ڕږ\u0003\u0002\u0002\u0002ږڗ\u0003\u0002\u0002\u0002ڗڝ\u0005Èe\u0002ژڙ\u0007[\u0002\u0002ڙښ\u0007.\u0002\u0002ښڞ\u0005Ôk\u0002ڛڜ\u0007l\u0002\u0002ڜڞ\u0007[\u0002\u0002ڝژ\u0003\u0002\u0002\u0002ڝڛ\u0003\u0002\u0002\u0002ڝڞ\u0003\u0002\u0002\u0002ڞ\u009d\u0003\u0002\u0002\u0002ڟڢ\u0005\u0090I\u0002ڠڡ\u00073\u0002\u0002ڡڣ\u0005Ði\u0002ڢڠ\u0003\u0002\u0002\u0002ڢڣ\u0003\u0002\u0002\u0002ڣڥ\u0003\u0002\u0002\u0002ڤڦ\t\u0004\u0002\u0002ڥڤ\u0003\u0002\u0002\u0002ڥڦ\u0003\u0002\u0002\u0002ڦ\u009f\u0003\u0002\u0002\u0002ڧګ\u0005´[\u0002ڨګ\u0005Âb\u0002کګ\u0007\u009e\u0002\u0002ڪڧ\u0003\u0002\u0002\u0002ڪڨ\u0003\u0002\u0002\u0002ڪک\u0003\u0002\u0002\u0002ګ¡\u0003\u0002\u0002\u0002ڬڸ\u0005Èe\u0002ڭڮ\u0007\t\u0002\u0002ڮڳ\u0005Îh\u0002گڰ\u0007\u000b\u0002\u0002ڰڲ\u0005Îh\u0002ڱگ\u0003\u0002\u0002\u0002ڲڵ\u0003\u0002\u0002\u0002ڳڱ\u0003\u0002\u0002\u0002ڳڴ\u0003\u0002\u0002\u0002ڴڶ\u0003\u0002\u0002\u0002ڵڳ\u0003\u0002\u0002\u0002ڶڷ\u0007\n\u0002\u0002ڷڹ\u0003\u0002\u0002\u0002ڸڭ\u0003\u0002\u0002\u0002ڸڹ\u0003\u0002\u0002\u0002ڹں\u0003\u0002\u0002\u0002ںڻ\u0007'\u0002\u0002ڻڼ\u0007\t\u0002\u0002ڼڽ\u0005~@\u0002ڽھ\u0007\n\u0002\u0002ھ£\u0003\u0002\u0002\u0002ڿی\u0007\r\u0002\u0002ۀہ\u0005Èe\u0002ہۂ\u0007\b\u0002\u0002ۂۃ\u0007\r\u0002\u0002ۃی\u0003\u0002\u0002\u0002ۄۉ\u0005\u0090I\u0002ۅۇ\u0007'\u0002\u0002ۆۅ\u0003\u0002\u0002\u0002ۆۇ\u0003\u0002\u0002\u0002ۇۈ\u0003\u0002\u0002\u0002ۈۊ\u0005¾`\u0002ۉۆ\u0003\u0002\u0002\u0002ۉۊ\u0003\u0002\u0002\u0002ۊی\u0003\u0002\u0002\u0002ۋڿ\u0003\u0002\u0002\u0002ۋۀ\u0003\u0002\u0002\u0002ۋۄ\u0003\u0002\u0002\u0002ی¥\u0003\u0002\u0002\u0002ۍێ\u0005Æd\u0002ێۏ\u0007\b\u0002\u0002ۏۑ\u0003\u0002\u0002\u0002ېۍ\u0003\u0002\u0002\u0002ېۑ\u0003\u0002\u0002\u0002ۑے\u0003\u0002\u0002\u0002ےۗ\u0005Èe\u0002ۓە\u0007'\u0002\u0002۔ۓ\u0003\u0002\u0002\u0002۔ە\u0003\u0002\u0002\u0002ەۖ\u0003\u0002\u0002\u0002ۖۘ\u0005àq\u0002ۗ۔\u0003\u0002\u0002\u0002ۗۘ\u0003\u0002\u0002\u0002ۘ۞\u0003\u0002\u0002\u0002ۙۚ\u0007[\u0002\u0002ۚۛ\u0007.\u0002\u0002ۛ۟\u0005Ôk\u0002ۜ\u06dd\u0007l\u0002\u0002\u06dd۟\u0007[\u0002\u0002۞ۙ\u0003\u0002\u0002\u0002۞ۜ\u0003\u0002\u0002\u0002۞۟\u0003\u0002\u0002\u0002۟۽\u0003\u0002\u0002\u0002۪۠\u0007\t\u0002\u0002ۡۦ\u0005¦T\u0002ۣۢ\u0007\u000b\u0002\u0002ۣۥ\u0005¦T\u0002ۤۢ\u0003\u0002\u0002\u0002ۥۨ\u0003\u0002\u0002\u0002ۦۤ\u0003\u0002\u0002\u0002ۦۧ\u0003\u0002\u0002\u0002ۧ۫\u0003\u0002\u0002\u0002ۨۦ\u0003\u0002\u0002\u0002۩۫\u0005¨U\u0002۪ۡ\u0003\u0002\u0002\u0002۪۩\u0003\u0002\u0002\u0002۫۬\u0003\u0002\u0002\u0002۬۱\u0007\n\u0002\u0002ۭۯ\u0007'\u0002\u0002ۮۭ\u0003\u0002\u0002\u0002ۮۯ\u0003\u0002\u0002\u0002ۯ۰\u0003\u0002\u0002\u0002۰۲\u0005àq\u0002۱ۮ\u0003\u0002\u0002\u0002۱۲\u0003\u0002\u0002\u0002۲۽\u0003\u0002\u0002\u0002۳۴\u0007\t\u0002\u0002۴۵\u0005~@\u0002۵ۺ\u0007\n\u0002\u0002۶۸\u0007'\u0002\u0002۷۶\u0003\u0002\u0002\u0002۷۸\u0003\u0002\u0002\u0002۸۹\u0003\u0002\u0002\u0002۹ۻ\u0005àq\u0002ۺ۷\u0003\u0002\u0002\u0002ۺۻ\u0003\u0002\u0002\u0002ۻ۽\u0003\u0002\u0002\u0002ۼې\u0003\u0002\u0002\u0002ۼ۠\u0003\u0002\u0002\u0002ۼ۳\u0003\u0002\u0002\u0002۽§\u0003\u0002\u0002\u0002۾܅\u0005¦T\u0002ۿ܀\u0005ªV\u0002܀܁\u0005¦T\u0002܁܂\u0005¬W\u0002܂܄\u0003\u0002\u0002\u0002܃ۿ\u0003\u0002\u0002\u0002܄܇\u0003\u0002\u0002\u0002܅܃\u0003\u0002\u0002\u0002܅܆\u0003\u0002\u0002\u0002܆©\u0003\u0002\u0002\u0002܇܅\u0003\u0002\u0002\u0002܈ܖ\u0007\u000b\u0002\u0002܉܋\u0007j\u0002\u0002܊܉\u0003\u0002\u0002\u0002܊܋\u0003\u0002\u0002\u0002܋ܒ\u0003\u0002\u0002\u0002܌\u070e\u0007f\u0002\u0002܍\u070f\u0007t\u0002\u0002\u070e܍\u0003\u0002\u0002\u0002\u070e\u070f\u0003\u0002\u0002\u0002\u070fܓ\u0003\u0002\u0002\u0002ܐܓ\u0007]\u0002\u0002ܑܓ\u00079\u0002\u0002ܒ܌\u0003\u0002\u0002\u0002ܒܐ\u0003\u0002\u0002\u0002ܒܑ\u0003\u0002\u0002\u0002ܒܓ\u0003\u0002\u0002\u0002ܓܔ\u0003\u0002\u0002\u0002ܔܖ\u0007d\u0002\u0002ܕ܈\u0003\u0002\u0002\u0002ܕ܊\u0003\u0002\u0002\u0002ܖ«\u0003\u0002\u0002\u0002ܗܘ\u0007q\u0002\u0002ܘܦ\u0005\u0090I\u0002ܙܚ\u0007\u0092\u0002\u0002ܚܛ\u0007\t\u0002\u0002ܛܠ\u0005Îh\u0002ܜܝ\u0007\u000b\u0002\u0002ܝܟ\u0005Îh\u0002ܞܜ\u0003\u0002\u0002\u0002ܟܢ\u0003\u0002\u0002\u0002ܠܞ\u0003\u0002\u0002\u0002ܠܡ\u0003\u0002\u0002\u0002ܡܣ\u0003\u0002\u0002\u0002ܢܠ\u0003\u0002\u0002\u0002ܣܤ\u0007\n\u0002\u0002ܤܦ\u0003\u0002\u0002\u0002ܥܗ\u0003\u0002\u0002\u0002ܥܙ\u0003\u0002\u0002\u0002ܥܦ\u0003\u0002\u0002\u0002ܦ\u00ad\u0003\u0002\u0002\u0002ܧܩ\u0007\u0086\u0002\u0002ܨܪ\t\t\u0002\u0002ܩܨ\u0003\u0002\u0002\u0002ܩܪ\u0003\u0002\u0002\u0002ܪܫ\u0003\u0002\u0002\u0002ܫܰ\u0005¤S\u0002ܬܭ\u0007\u000b\u0002\u0002ܭܯ\u0005¤S\u0002ܮܬ\u0003\u0002\u0002\u0002ܯܲ\u0003\u0002\u0002\u0002ܰܮ\u0003\u0002\u0002\u0002ܱܰ\u0003\u0002\u0002\u0002ܱܿ\u0003\u0002\u0002\u0002ܲܰ\u0003\u0002\u0002\u0002ܳܽ\u0007Q\u0002\u0002ܴܹ\u0005¦T\u0002ܵܶ\u0007\u000b\u0002\u0002ܸܶ\u0005¦T\u0002ܷܵ\u0003\u0002\u0002\u0002ܸܻ\u0003\u0002\u0002\u0002ܹܷ\u0003\u0002\u0002\u0002ܹܺ\u0003\u0002\u0002\u0002ܾܺ\u0003\u0002\u0002\u0002ܻܹ\u0003\u0002\u0002\u0002ܼܾ\u0005¨U\u0002ܴܽ\u0003\u0002\u0002\u0002ܼܽ\u0003\u0002\u0002\u0002ܾ݀\u0003\u0002\u0002\u0002ܿܳ\u0003\u0002\u0002\u0002ܿ݀\u0003\u0002\u0002\u0002݀݃\u0003\u0002\u0002\u0002݂݁\u0007\u0098\u0002\u0002݂݄\u0005\u0090I\u0002݃݁\u0003\u0002\u0002\u0002݄݃\u0003\u0002\u0002\u0002݄ݓ\u0003\u0002\u0002\u0002݆݅\u0007T\u0002\u0002݆݇\u0007.\u0002\u0002݇\u074c\u0005\u0090I\u0002݈݉\u0007\u000b\u0002\u0002݉\u074b\u0005\u0090I\u0002݈݊\u0003\u0002\u0002\u0002\u074bݎ\u0003\u0002\u0002\u0002\u074c݊\u0003\u0002\u0002\u0002\u074cݍ\u0003\u0002\u0002\u0002ݍݑ\u0003\u0002\u0002\u0002ݎ\u074c\u0003\u0002\u0002\u0002ݏݐ\u0007U\u0002\u0002ݐݒ\u0005\u0090I\u0002ݑݏ\u0003\u0002\u0002\u0002ݑݒ\u0003\u0002\u0002\u0002ݒݔ\u0003\u0002\u0002\u0002ݓ݅\u0003\u0002\u0002\u0002ݓݔ\u0003\u0002\u0002\u0002ݔݲ\u0003\u0002\u0002\u0002ݕݖ\u0007\u0094\u0002\u0002ݖݗ\u0007\t\u0002\u0002ݗݜ\u0005\u0090I\u0002ݘݙ\u0007\u000b\u0002\u0002ݙݛ\u0005\u0090I\u0002ݚݘ\u0003\u0002\u0002\u0002ݛݞ\u0003\u0002\u0002\u0002ݜݚ\u0003\u0002\u0002\u0002ݜݝ\u0003\u0002\u0002\u0002ݝݟ\u0003\u0002\u0002\u0002ݞݜ\u0003\u0002\u0002\u0002ݟݮ\u0007\n\u0002\u0002ݠݡ\u0007\u000b\u0002\u0002ݡݢ\u0007\t\u0002\u0002ݢݧ\u0005\u0090I\u0002ݣݤ\u0007\u000b\u0002\u0002ݤݦ\u0005\u0090I\u0002ݥݣ\u0003\u0002\u0002\u0002ݦݩ\u0003\u0002\u0002\u0002ݧݥ\u0003\u0002\u0002\u0002ݧݨ\u0003\u0002\u0002\u0002ݨݪ\u0003\u0002\u0002\u0002ݩݧ\u0003\u0002\u0002\u0002ݪݫ\u0007\n\u0002\u0002ݫݭ\u0003\u0002\u0002\u0002ݬݠ\u0003\u0002\u0002\u0002ݭݰ\u0003\u0002\u0002\u0002ݮݬ\u0003\u0002\u0002\u0002ݮݯ\u0003\u0002\u0002\u0002ݯݲ\u0003\u0002\u0002\u0002ݰݮ\u0003\u0002\u0002\u0002ݱܧ\u0003\u0002\u0002\u0002ݱݕ\u0003\u0002\u0002\u0002ݲ¯\u0003\u0002\u0002\u0002ݳݹ\u0007\u008f\u0002\u0002ݴݵ\u0007\u008f\u0002\u0002ݵݹ\u0007#\u0002\u0002ݶݹ\u0007`\u0002\u0002ݷݹ\u0007J\u0002\u0002ݸݳ\u0003\u0002\u0002\u0002ݸݴ\u0003\u0002\u0002\u0002ݸݶ\u0003\u0002\u0002\u0002ݸݷ\u0003\u0002\u0002\u0002ݹ±\u0003\u0002\u0002\u0002ݺކ\u0005Èe\u0002ݻݼ\u0007\t\u0002\u0002ݼށ\u0005Îh\u0002ݽݾ\u0007\u000b\u0002\u0002ݾހ\u0005Îh\u0002ݿݽ\u0003\u0002\u0002\u0002ހރ\u0003\u0002\u0002\u0002ށݿ\u0003\u0002\u0002\u0002ށނ\u0003\u0002\u0002\u0002ނބ\u0003\u0002\u0002\u0002ރށ\u0003\u0002\u0002\u0002ބޅ\u0007\n\u0002\u0002ޅއ\u0003\u0002\u0002\u0002ކݻ\u0003\u0002\u0002\u0002ކއ\u0003\u0002\u0002\u0002އ³\u0003\u0002\u0002\u0002ވފ\t\f\u0002\u0002މވ\u0003\u0002\u0002\u0002މފ\u0003\u0002\u0002\u0002ފދ\u0003\u0002\u0002\u0002ދތ\u0007\u009c\u0002\u0002ތµ\u0003\u0002\u0002\u0002ލގ\t\u0012\u0002\u0002ގ·\u0003\u0002\u0002\u0002ޏސ\t\u0013\u0002\u0002ސ¹\u0003\u0002\u0002\u0002ޑޒ\u0007\u009e\u0002\u0002ޒ»\u0003\u0002\u0002\u0002ޓޖ\u0005\u0090I\u0002ޔޖ\u0005\u0088E\u0002ޕޓ\u0003\u0002\u0002\u0002ޕޔ\u0003\u0002\u0002\u0002ޖ½\u0003\u0002\u0002\u0002ޗޘ\t\u0002\u0002\u0002ޘ¿\u0003\u0002\u0002\u0002ޙޚ\t\u0014\u0002\u0002ޚÁ\u0003\u0002\u0002\u0002ޛޜ\u0005äs\u0002ޜÃ\u0003\u0002\u0002\u0002ޝޞ\u0005äs\u0002ޞÅ\u0003\u0002\u0002\u0002ޟޠ\u0005äs\u0002ޠÇ\u0003\u0002\u0002\u0002ޡޢ\u0005äs\u0002ޢÉ\u0003\u0002\u0002\u0002ޣޤ\u0005äs\u0002ޤË\u0003\u0002\u0002\u0002ޥަ\u0005äs\u0002ަÍ\u0003\u0002\u0002\u0002ާި\u0005äs\u0002ިÏ\u0003\u0002\u0002\u0002ީު\u0005äs\u0002ުÑ\u0003\u0002\u0002\u0002ޫެ\u0005äs\u0002ެÓ\u0003\u0002\u0002\u0002ޭޮ\u0005äs\u0002ޮÕ\u0003\u0002\u0002\u0002ޯް\u0005äs\u0002ް×\u0003\u0002\u0002\u0002ޱ\u07b2\u0005äs\u0002\u07b2Ù\u0003\u0002\u0002\u0002\u07b3\u07b4\u0005äs\u0002\u07b4Û\u0003\u0002\u0002\u0002\u07b5\u07b6\u0005äs\u0002\u07b6Ý\u0003\u0002\u0002\u0002\u07b7\u07b8\u0005äs\u0002\u07b8ß\u0003\u0002\u0002\u0002\u07b9\u07ba\u0005äs\u0002\u07baá\u0003\u0002\u0002\u0002\u07bb\u07bc\u0005äs\u0002\u07bcã\u0003\u0002\u0002\u0002\u07bd߅\u0007\u009b\u0002\u0002\u07be߅\u0005Àa\u0002\u07bf߅\u0007\u009e\u0002\u0002߀߁\u0007\t\u0002\u0002߁߂\u0005äs\u0002߂߃\u0007\n\u0002\u0002߃߅\u0003\u0002\u0002\u0002߄\u07bd\u0003\u0002\u0002\u0002߄\u07be\u0003\u0002\u0002\u0002߄\u07bf\u0003\u0002\u0002\u0002߄߀\u0003\u0002\u0002\u0002߅å\u0003\u0002\u0002\u0002ĂëîþăĈĒğīŀůŽƣƩƫƶƽǂǈǎǐǰǷǿȂȋȏȗțȝȢȤȨȯȲȷȻɀɉɌɒɔɘɞɣɮɴɸɾʃʌʓʙʝʡʧʬʳʾˁ˃ˉˏ˓˚ˠ˦ˬ˱˽̸̜̟̦̯̲̺͓̂̍̒̾͆͋ͥͭ̕͘͠Ͳ\u0378Ϳ\u0382ΊΔΗΝΟ\u03a2ελτωϒϝϤϪϰϹЀЄІЊБГЗКСШЫеиорфыюіѠѣѩѫѯѶѿ҃҅҉ҒҗҙҢҭҴҷҺӇӕӚӝӪӸӽԆԉԏԑԗԜԢԮԲԷԻԾՐՕ՚բէհշջ\u058b֎֖֣֟֨׆גחףשװ״\u05fe\u0601؇؊،؎ؙ؞تخزضؽؿهَّٕٚ٢ٵٻٿڏڕڝڢڥڪڳڸۆۉۋې۔ۗ۞ۦ۪ۮ۱۷ۺۼ܅܊\u070eܒܕܠܥܩܹܰܽܿ݃\u074cݑݓݜݧݮݱݸށކމޕ߄";
   }

   public ATN getATN() {
      return _ATN;
   }

   public SQLGrammarParser(TokenStream input) {
      super(input);
      this._interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
   }

   public final StartContext start() throws RecognitionException {
      StartContext _localctx = new StartContext(this._ctx, this.getState());
      this.enterRule(_localctx, 0, 0);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(228);
         this.match(132);
         this.setState(229);
         this.match(11);
         this.setState(230);
         this.match(79);
         this.setState(231);
         this.indexedCollection();
         this.setState(233);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 150) {
            this.setState(232);
            this.whereClause();
         }

         this.setState(236);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 113) {
            this.setState(235);
            this.orderByClause();
         }

         this.setState(238);
         this.match(-1);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final IndexedCollectionContext indexedCollection() throws RecognitionException {
      IndexedCollectionContext _localctx = new IndexedCollectionContext(this._ctx, this.getState());
      this.enterRule(_localctx, 2, 1);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(240);
         int _la = this._input.LA(1);
         if (_la != 153 && _la != 156) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final WhereClauseContext whereClause() throws RecognitionException {
      WhereClauseContext _localctx = new WhereClauseContext(this._ctx, this.getState());
      this.enterRule(_localctx, 4, 2);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(242);
         this.match(150);
         this.setState(243);
         this.query();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final OrderByClauseContext orderByClause() throws RecognitionException {
      OrderByClauseContext _localctx = new OrderByClauseContext(this._ctx, this.getState());
      this.enterRule(_localctx, 6, 3);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(245);
         this.match(113);
         this.setState(246);
         this.match(44);
         this.setState(247);
         this.attributeOrder();
         this.setState(252);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
            this.setState(248);
            this.match(9);
            this.setState(249);
            this.attributeOrder();
            this.setState(254);
            this._errHandler.sync(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final QueryContext query() throws RecognitionException {
      QueryContext _localctx = new QueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 8, 4);

      try {
         this.setState(257);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 3, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(255);
               this.logicalQuery();
               break;
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(256);
               this.simpleQuery();
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final LogicalQueryContext logicalQuery() throws RecognitionException {
      LogicalQueryContext _localctx = new LogicalQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 10, 5);

      try {
         this.setState(262);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 4, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(259);
               this.andQuery();
               break;
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(260);
               this.orQuery();
               break;
            case 3:
               this.enterOuterAlt(_localctx, 3);
               this.setState(261);
               this.notQuery();
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final AndQueryContext andQuery() throws RecognitionException {
      AndQueryContext _localctx = new AndQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 12, 6);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(264);
         this.match(7);
         this.setState(265);
         this.query();
         this.setState(266);
         this.match(36);
         this.setState(267);
         this.query();
         this.setState(272);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 36; _la = this._input.LA(1)) {
            this.setState(268);
            this.match(36);
            this.setState(269);
            this.query();
            this.setState(274);
            this._errHandler.sync(this);
         }

         this.setState(275);
         this.match(8);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final OrQueryContext orQuery() throws RecognitionException {
      OrQueryContext _localctx = new OrQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 14, 7);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(277);
         this.match(7);
         this.setState(278);
         this.query();
         this.setState(279);
         this.match(112);
         this.setState(280);
         this.query();
         this.setState(285);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 112; _la = this._input.LA(1)) {
            this.setState(281);
            this.match(112);
            this.setState(282);
            this.query();
            this.setState(287);
            this._errHandler.sync(this);
         }

         this.setState(288);
         this.match(8);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final NotQueryContext notQuery() throws RecognitionException {
      NotQueryContext _localctx = new NotQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 16, 8);

      try {
         this.setState(297);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 7:
               this.enterOuterAlt(_localctx, 2);
               this.setState(292);
               this.match(7);
               this.setState(293);
               this.match(106);
               this.setState(294);
               this.query();
               this.setState(295);
               this.match(8);
               break;
            case 106:
               this.enterOuterAlt(_localctx, 1);
               this.setState(290);
               this.match(106);
               this.setState(291);
               this.query();
               break;
            default:
               throw new NoViableAltException(this);
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final SimpleQueryContext simpleQuery() throws RecognitionException {
      SimpleQueryContext _localctx = new SimpleQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 18, 9);

      try {
         this.setState(318);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 8, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(299);
               this.equalQuery();
               break;
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(300);
               this.notEqualQuery();
               break;
            case 3:
               this.enterOuterAlt(_localctx, 3);
               this.setState(301);
               this.lessThanOrEqualToQuery();
               break;
            case 4:
               this.enterOuterAlt(_localctx, 4);
               this.setState(302);
               this.lessThanQuery();
               break;
            case 5:
               this.enterOuterAlt(_localctx, 5);
               this.setState(303);
               this.greaterThanOrEqualToQuery();
               break;
            case 6:
               this.enterOuterAlt(_localctx, 6);
               this.setState(304);
               this.greaterThanQuery();
               break;
            case 7:
               this.enterOuterAlt(_localctx, 7);
               this.setState(305);
               this.betweenQuery();
               break;
            case 8:
               this.enterOuterAlt(_localctx, 8);
               this.setState(306);
               this.notBetweenQuery();
               break;
            case 9:
               this.enterOuterAlt(_localctx, 9);
               this.setState(307);
               this.inQuery();
               break;
            case 10:
               this.enterOuterAlt(_localctx, 10);
               this.setState(308);
               this.notInQuery();
               break;
            case 11:
               this.enterOuterAlt(_localctx, 11);
               this.setState(309);
               this.startsWithQuery();
               break;
            case 12:
               this.enterOuterAlt(_localctx, 12);
               this.setState(310);
               this.endsWithQuery();
               break;
            case 13:
               this.enterOuterAlt(_localctx, 13);
               this.setState(311);
               this.containsQuery();
               break;
            case 14:
               this.enterOuterAlt(_localctx, 14);
               this.setState(312);
               this.hasQuery();
               break;
            case 15:
               this.enterOuterAlt(_localctx, 15);
               this.setState(313);
               this.notHasQuery();
               break;
            case 16:
               this.enterOuterAlt(_localctx, 16);
               this.setState(314);
               this.match(7);
               this.setState(315);
               this.simpleQuery();
               this.setState(316);
               this.match(8);
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final EqualQueryContext equalQuery() throws RecognitionException {
      EqualQueryContext _localctx = new EqualQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 20, 10);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(320);
         this.attributeName();
         this.setState(321);
         this.match(10);
         this.setState(322);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final NotEqualQueryContext notEqualQuery() throws RecognitionException {
      NotEqualQueryContext _localctx = new NotEqualQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 22, 11);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(324);
         this.attributeName();
         this.setState(325);
         this.match(28);
         this.setState(326);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final LessThanOrEqualToQueryContext lessThanOrEqualToQuery() throws RecognitionException {
      LessThanOrEqualToQueryContext _localctx = new LessThanOrEqualToQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 24, 12);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(328);
         this.attributeName();
         this.setState(329);
         this.match(23);
         this.setState(330);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final LessThanQueryContext lessThanQuery() throws RecognitionException {
      LessThanQueryContext _localctx = new LessThanQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 26, 13);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(332);
         this.attributeName();
         this.setState(333);
         this.match(22);
         this.setState(334);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final GreaterThanOrEqualToQueryContext greaterThanOrEqualToQuery() throws RecognitionException {
      GreaterThanOrEqualToQueryContext _localctx = new GreaterThanOrEqualToQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 28, 14);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(336);
         this.attributeName();
         this.setState(337);
         this.match(25);
         this.setState(338);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final GreaterThanQueryContext greaterThanQuery() throws RecognitionException {
      GreaterThanQueryContext _localctx = new GreaterThanQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 30, 15);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(340);
         this.attributeName();
         this.setState(341);
         this.match(24);
         this.setState(342);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final BetweenQueryContext betweenQuery() throws RecognitionException {
      BetweenQueryContext _localctx = new BetweenQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 32, 16);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(344);
         this.attributeName();
         this.setState(345);
         this.match(43);
         this.setState(346);
         this.queryParameter();
         this.setState(347);
         this.match(36);
         this.setState(348);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final NotBetweenQueryContext notBetweenQuery() throws RecognitionException {
      NotBetweenQueryContext _localctx = new NotBetweenQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 34, 17);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(350);
         this.attributeName();
         this.setState(351);
         this.match(106);
         this.setState(352);
         this.match(43);
         this.setState(353);
         this.queryParameter();
         this.setState(354);
         this.match(36);
         this.setState(355);
         this.queryParameter();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final InQueryContext inQuery() throws RecognitionException {
      InQueryContext _localctx = new InQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 36, 18);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(357);
         this.attributeName();
         this.setState(358);
         this.match(87);
         this.setState(359);
         this.match(7);
         this.setState(360);
         this.queryParameter();
         this.setState(365);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
            this.setState(361);
            this.match(9);
            this.setState(362);
            this.queryParameter();
            this.setState(367);
            this._errHandler.sync(this);
         }

         this.setState(368);
         this.match(8);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final NotInQueryContext notInQuery() throws RecognitionException {
      NotInQueryContext _localctx = new NotInQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 38, 19);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(370);
         this.attributeName();
         this.setState(371);
         this.match(106);
         this.setState(372);
         this.match(87);
         this.setState(373);
         this.match(7);
         this.setState(374);
         this.queryParameter();
         this.setState(379);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
            this.setState(375);
            this.match(9);
            this.setState(376);
            this.queryParameter();
            this.setState(381);
            this._errHandler.sync(this);
         }

         this.setState(382);
         this.match(8);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final StartsWithQueryContext startsWithQuery() throws RecognitionException {
      StartsWithQueryContext _localctx = new StartsWithQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 40, 20);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(384);
         this.attributeName();
         this.setState(385);
         this.match(101);
         this.setState(386);
         this.queryParameterTrailingPercent();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final EndsWithQueryContext endsWithQuery() throws RecognitionException {
      EndsWithQueryContext _localctx = new EndsWithQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 42, 21);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(388);
         this.attributeName();
         this.setState(389);
         this.match(101);
         this.setState(390);
         this.queryParameterLeadingPercent();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final ContainsQueryContext containsQuery() throws RecognitionException {
      ContainsQueryContext _localctx = new ContainsQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 44, 22);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(392);
         this.attributeName();
         this.setState(393);
         this.match(101);
         this.setState(394);
         this.queryParameterLeadingAndTrailingPercent();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final HasQueryContext hasQuery() throws RecognitionException {
      HasQueryContext _localctx = new HasQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 46, 23);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(396);
         this.attributeName();
         this.setState(397);
         this.match(96);
         this.setState(398);
         this.match(106);
         this.setState(399);
         this.match(108);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final NotHasQueryContext notHasQuery() throws RecognitionException {
      NotHasQueryContext _localctx = new NotHasQueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 48, 24);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(401);
         this.attributeName();
         this.setState(402);
         this.match(96);
         this.setState(403);
         this.match(108);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final AttributeNameContext attributeName() throws RecognitionException {
      AttributeNameContext _localctx = new AttributeNameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 50, 25);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(405);
         int _la = this._input.LA(1);
         if (_la != 153 && _la != 156) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final QueryParameterTrailingPercentContext queryParameterTrailingPercent() throws RecognitionException {
      QueryParameterTrailingPercentContext _localctx = new QueryParameterTrailingPercentContext(this._ctx, this.getState());
      this.enterRule(_localctx, 52, 26);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(407);
         this.match(1);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final QueryParameterLeadingPercentContext queryParameterLeadingPercent() throws RecognitionException {
      QueryParameterLeadingPercentContext _localctx = new QueryParameterLeadingPercentContext(this._ctx, this.getState());
      this.enterRule(_localctx, 54, 27);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(409);
         this.match(2);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final QueryParameterLeadingAndTrailingPercentContext queryParameterLeadingAndTrailingPercent() throws RecognitionException {
      QueryParameterLeadingAndTrailingPercentContext _localctx = new QueryParameterLeadingAndTrailingPercentContext(this._ctx, this.getState());
      this.enterRule(_localctx, 56, 28);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(411);
         this.match(3);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final QueryParameterContext queryParameter() throws RecognitionException {
      QueryParameterContext _localctx = new QueryParameterContext(this._ctx, this.getState());
      this.enterRule(_localctx, 58, 29);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(413);
         int _la = this._input.LA(1);
         if (_la != 4 && _la != 154 && _la != 156) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final AttributeOrderContext attributeOrder() throws RecognitionException {
      AttributeOrderContext _localctx = new AttributeOrderContext(this._ctx, this.getState());
      this.enterRule(_localctx, 60, 30);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(415);
         this.attributeName();
         this.setState(417);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 38 || _la == 64) {
            this.setState(416);
            this.direction();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final DirectionContext direction() throws RecognitionException {
      DirectionContext _localctx = new DirectionContext(this._ctx, this.getState());
      this.enterRule(_localctx, 62, 31);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(419);
         int _la = this._input.LA(1);
         if (_la != 38 && _la != 64) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final ParseContext parse() throws RecognitionException {
      ParseContext _localctx = new ParseContext(this._ctx, this.getState());
      this.enterRule(_localctx, 64, 32);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(425);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); (_la & -64) == 0 && (1L << _la & -9203100839189676000L) != 0L || (_la - 65 & -64) == 0 && (1L << _la - 65 & 3172785937616733221L) != 0L || (_la - 129 & -64) == 0 && (1L << _la - 129 & 4299374605L) != 0L; _la = this._input.LA(1)) {
            this.setState(423);
            this._errHandler.sync(this);
            switch (this._input.LA(1)) {
               case 5:
               case 34:
               case 35:
               case 39:
               case 42:
               case 51:
               case 54:
               case 63:
               case 65:
               case 67:
               case 70:
               case 75:
               case 92:
               case 116:
               case 123:
               case 124:
               case 126:
               case 129:
               case 131:
               case 132:
               case 143:
               case 145:
               case 146:
               case 151:
                  this.setState(421);
                  this.sql_stmt_list();
                  break;
               case 161:
                  this.setState(422);
                  this.error();
                  break;
               default:
                  throw new NoViableAltException(this);
            }

            this.setState(427);
            this._errHandler.sync(this);
         }

         this.setState(428);
         this.match(-1);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final ErrorContext error() throws RecognitionException {
      ErrorContext _localctx = new ErrorContext(this._ctx, this.getState());
      this.enterRule(_localctx, 66, 33);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(430);
         _localctx.UNEXPECTED_CHAR = this.match(161);
         throw new RuntimeException("UNEXPECTED_CHAR=" + (_localctx.UNEXPECTED_CHAR != null ? _localctx.UNEXPECTED_CHAR.getText() : null));
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Sql_stmt_listContext sql_stmt_list() throws RecognitionException {
      Sql_stmt_listContext _localctx = new Sql_stmt_listContext(this._ctx, this.getState());
      this.enterRule(_localctx, 68, 34);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(436);
         this._errHandler.sync(this);

         int _la;
         for(_la = this._input.LA(1); _la == 5; _la = this._input.LA(1)) {
            this.setState(433);
            this.match(5);
            this.setState(438);
            this._errHandler.sync(this);
         }

         this.setState(439);
         this.sql_stmt();
         this.setState(448);
         this._errHandler.sync(this);

         int _alt;
         for(_alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 16, this._ctx); _alt != 2 && _alt != 0; _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 16, this._ctx)) {
            if (_alt == 1) {
               this.setState(441);
               this._errHandler.sync(this);
               _la = this._input.LA(1);

               do {
                  this.setState(440);
                  this.match(5);
                  this.setState(443);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
               } while(_la == 5);

               this.setState(445);
               this.sql_stmt();
            }

            this.setState(450);
            this._errHandler.sync(this);
         }

         this.setState(454);
         this._errHandler.sync(this);

         for(_alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 17, this._ctx); _alt != 2 && _alt != 0; _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 17, this._ctx)) {
            if (_alt == 1) {
               this.setState(451);
               this.match(5);
            }

            this.setState(456);
            this._errHandler.sync(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Sql_stmtContext sql_stmt() throws RecognitionException {
      Sql_stmtContext _localctx = new Sql_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 70, 35);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(462);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 75) {
            this.setState(457);
            this.match(75);
            this.setState(460);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 118) {
               this.setState(458);
               this.match(118);
               this.setState(459);
               this.match(115);
            }
         }

         this.setState(494);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 20, this._ctx)) {
            case 1:
               this.setState(464);
               this.alter_table_stmt();
               break;
            case 2:
               this.setState(465);
               this.analyze_stmt();
               break;
            case 3:
               this.setState(466);
               this.attach_stmt();
               break;
            case 4:
               this.setState(467);
               this.begin_stmt();
               break;
            case 5:
               this.setState(468);
               this.commit_stmt();
               break;
            case 6:
               this.setState(469);
               this.compound_select_stmt();
               break;
            case 7:
               this.setState(470);
               this.create_index_stmt();
               break;
            case 8:
               this.setState(471);
               this.create_table_stmt();
               break;
            case 9:
               this.setState(472);
               this.create_trigger_stmt();
               break;
            case 10:
               this.setState(473);
               this.create_view_stmt();
               break;
            case 11:
               this.setState(474);
               this.create_virtual_table_stmt();
               break;
            case 12:
               this.setState(475);
               this.delete_stmt();
               break;
            case 13:
               this.setState(476);
               this.delete_stmt_limited();
               break;
            case 14:
               this.setState(477);
               this.detach_stmt();
               break;
            case 15:
               this.setState(478);
               this.drop_index_stmt();
               break;
            case 16:
               this.setState(479);
               this.drop_table_stmt();
               break;
            case 17:
               this.setState(480);
               this.drop_trigger_stmt();
               break;
            case 18:
               this.setState(481);
               this.drop_view_stmt();
               break;
            case 19:
               this.setState(482);
               this.factored_select_stmt();
               break;
            case 20:
               this.setState(483);
               this.insert_stmt();
               break;
            case 21:
               this.setState(484);
               this.pragma_stmt();
               break;
            case 22:
               this.setState(485);
               this.reindex_stmt();
               break;
            case 23:
               this.setState(486);
               this.release_stmt();
               break;
            case 24:
               this.setState(487);
               this.rollback_stmt();
               break;
            case 25:
               this.setState(488);
               this.savepoint_stmt();
               break;
            case 26:
               this.setState(489);
               this.simple_select_stmt();
               break;
            case 27:
               this.setState(490);
               this.select_stmt();
               break;
            case 28:
               this.setState(491);
               this.update_stmt();
               break;
            case 29:
               this.setState(492);
               this.update_stmt_limited();
               break;
            case 30:
               this.setState(493);
               this.vacuum_stmt();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Alter_table_stmtContext alter_table_stmt() throws RecognitionException {
      Alter_table_stmtContext _localctx = new Alter_table_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 72, 36);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(496);
         this.match(34);
         this.setState(497);
         this.match(134);
         this.setState(501);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 21, this._ctx)) {
            case 1:
               this.setState(498);
               this.database_name();
               this.setState(499);
               this.match(6);
            default:
               this.setState(503);
               this.table_name();
               this.setState(512);
               this._errHandler.sync(this);
               switch (this._input.LA(1)) {
                  case 31:
                     this.setState(507);
                     this.match(31);
                     this.setState(509);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 22, this._ctx)) {
                        case 1:
                           this.setState(508);
                           this.match(50);
                        default:
                           this.setState(511);
                           this.column_def();
                           return _localctx;
                     }
                  case 125:
                     this.setState(504);
                     this.match(125);
                     this.setState(505);
                     this.match(138);
                     this.setState(506);
                     this.new_table_name();
                     break;
                  default:
                     throw new NoViableAltException(this);
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Analyze_stmtContext analyze_stmt() throws RecognitionException {
      Analyze_stmtContext _localctx = new Analyze_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 74, 37);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(514);
         this.match(35);
         this.setState(521);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 24, this._ctx)) {
            case 1:
               this.setState(515);
               this.database_name();
               break;
            case 2:
               this.setState(516);
               this.table_or_index_name();
               break;
            case 3:
               this.setState(517);
               this.database_name();
               this.setState(518);
               this.match(6);
               this.setState(519);
               this.table_or_index_name();
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Attach_stmtContext attach_stmt() throws RecognitionException {
      Attach_stmtContext _localctx = new Attach_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 76, 38);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(523);
         this.match(39);
         this.setState(525);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 25, this._ctx)) {
            case 1:
               this.setState(524);
               this.match(59);
            default:
               this.setState(527);
               this.expr(0);
               this.setState(528);
               this.match(37);
               this.setState(529);
               this.database_name();
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Begin_stmtContext begin_stmt() throws RecognitionException {
      Begin_stmtContext _localctx = new Begin_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 78, 39);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(531);
         this.match(42);
         this.setState(533);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if ((_la - 62 & -64) == 0 && (1L << _la - 62 & 16779265L) != 0L) {
            this.setState(532);
            _la = this._input.LA(1);
            if ((_la - 62 & -64) == 0 && (1L << _la - 62 & 16779265L) != 0L) {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            } else {
               this._errHandler.recoverInline(this);
            }
         }

         this.setState(539);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 139) {
            this.setState(535);
            this.match(139);
            this.setState(537);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 27, this._ctx)) {
               case 1:
                  this.setState(536);
                  this.transaction_name();
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Commit_stmtContext commit_stmt() throws RecognitionException {
      Commit_stmtContext _localctx = new Commit_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 80, 40);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(541);
         int _la = this._input.LA(1);
         if (_la != 51 && _la != 70) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }

         this.setState(546);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 139) {
            this.setState(542);
            this.match(139);
            this.setState(544);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 29, this._ctx)) {
               case 1:
                  this.setState(543);
                  this.transaction_name();
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Compound_select_stmtContext compound_select_stmt() throws RecognitionException {
      Compound_select_stmtContext _localctx = new Compound_select_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 82, 41);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(560);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(548);
            this.match(151);
            this.setState(550);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 31, this._ctx)) {
               case 1:
                  this.setState(549);
                  this.match(120);
               default:
                  this.setState(552);
                  this.common_table_expression();
                  this.setState(557);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
            }

            while(_la == 9) {
               this.setState(553);
               this.match(9);
               this.setState(554);
               this.common_table_expression();
               this.setState(559);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
            }
         }

         this.setState(562);
         this.select_core();
         this.setState(572);
         this._errHandler.sync(this);
         _la = this._input.LA(1);

         do {
            do {
               do {
                  this.setState(569);
                  this._errHandler.sync(this);
                  switch (this._input.LA(1)) {
                     case 72:
                        this.setState(568);
                        this.match(72);
                        break;
                     case 94:
                        this.setState(567);
                        this.match(94);
                        break;
                     case 141:
                        this.setState(563);
                        this.match(141);
                        this.setState(565);
                        this._errHandler.sync(this);
                        _la = this._input.LA(1);
                        if (_la == 33) {
                           this.setState(564);
                           this.match(33);
                        }
                        break;
                     default:
                        throw new NoViableAltException(this);
                  }

                  this.setState(571);
                  this.select_core();
                  this.setState(574);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
               } while(_la == 72);
            } while(_la == 94);
         } while(_la == 141);

         this.setState(586);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 113) {
            this.setState(576);
            this.match(113);
            this.setState(577);
            this.match(44);
            this.setState(578);
            this.ordering_term();
            this.setState(583);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(579);
               this.match(9);
               this.setState(580);
               this.ordering_term();
               this.setState(585);
               this._errHandler.sync(this);
            }
         }

         this.setState(594);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 102) {
            this.setState(588);
            this.match(102);
            this.setState(589);
            this.expr(0);
            this.setState(592);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 9 || _la == 110) {
               this.setState(590);
               _la = this._input.LA(1);
               if (_la != 9 && _la != 110) {
                  this._errHandler.recoverInline(this);
               } else {
                  if (this._input.LA(1) == -1) {
                     this.matchedEOF = true;
                  }

                  this._errHandler.reportMatch(this);
                  this.consume();
               }

               this.setState(591);
               this.expr(0);
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Create_index_stmtContext create_index_stmt() throws RecognitionException {
      Create_index_stmtContext _localctx = new Create_index_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 84, 42);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(596);
         this.match(54);
         this.setState(598);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 142) {
            this.setState(597);
            this.match(142);
         }

         this.setState(600);
         this.match(88);
         this.setState(604);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 42, this._ctx)) {
            case 1:
               this.setState(601);
               this.match(84);
               this.setState(602);
               this.match(106);
               this.setState(603);
               this.match(74);
            default:
               this.setState(609);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 43, this._ctx)) {
                  case 1:
                     this.setState(606);
                     this.database_name();
                     this.setState(607);
                     this.match(6);
                  default:
                     this.setState(611);
                     this.index_name();
                     this.setState(612);
                     this.match(111);
                     this.setState(613);
                     this.table_name();
                     this.setState(614);
                     this.match(7);
                     this.setState(615);
                     this.indexed_column();
                     this.setState(620);
                     this._errHandler.sync(this);
                     _la = this._input.LA(1);
               }
         }

         while(_la == 9) {
            this.setState(616);
            this.match(9);
            this.setState(617);
            this.indexed_column();
            this.setState(622);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
         }

         this.setState(623);
         this.match(8);
         this.setState(626);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 150) {
            this.setState(624);
            this.match(150);
            this.setState(625);
            this.expr(0);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Create_table_stmtContext create_table_stmt() throws RecognitionException {
      Create_table_stmtContext _localctx = new Create_table_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 86, 43);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(628);
         this.match(54);
         this.setState(630);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 135 || _la == 136) {
            this.setState(629);
            _la = this._input.LA(1);
            if (_la != 135 && _la != 136) {
               this._errHandler.recoverInline(this);
            } else {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            }
         }

         this.setState(632);
         this.match(134);
         this.setState(636);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 47, this._ctx)) {
            case 1:
               this.setState(633);
               this.match(84);
               this.setState(634);
               this.match(106);
               this.setState(635);
               this.match(74);
            default:
               this.setState(641);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 48, this._ctx)) {
                  case 1:
                     this.setState(638);
                     this.database_name();
                     this.setState(639);
                     this.match(6);
               }

               this.setState(643);
               this.table_name();
               this.setState(667);
               this._errHandler.sync(this);
               switch (this._input.LA(1)) {
                  case 7:
                     this.setState(644);
                     this.match(7);
                     this.setState(645);
                     this.column_def();
                     this.setState(650);
                     this._errHandler.sync(this);

                     for(int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 49, this._ctx); _alt != 2 && _alt != 0; _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 49, this._ctx)) {
                        if (_alt == 1) {
                           this.setState(646);
                           this.match(9);
                           this.setState(647);
                           this.column_def();
                        }

                        this.setState(652);
                        this._errHandler.sync(this);
                     }

                     this.setState(657);
                     this._errHandler.sync(this);

                     for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                        this.setState(653);
                        this.match(9);
                        this.setState(654);
                        this.table_constraint();
                        this.setState(659);
                        this._errHandler.sync(this);
                     }

                     this.setState(660);
                     this.match(8);
                     this.setState(663);
                     this._errHandler.sync(this);
                     _la = this._input.LA(1);
                     if (_la == 152) {
                        this.setState(661);
                        this.match(152);
                        this.setState(662);
                        this.match(153);
                     }
                     break;
                  case 37:
                     this.setState(665);
                     this.match(37);
                     this.setState(666);
                     this.select_stmt();
                     break;
                  default:
                     throw new NoViableAltException(this);
               }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Create_trigger_stmtContext create_trigger_stmt() throws RecognitionException {
      Create_trigger_stmtContext _localctx = new Create_trigger_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 88, 44);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(669);
         this.match(54);
         this.setState(671);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 135 || _la == 136) {
            this.setState(670);
            _la = this._input.LA(1);
            if (_la != 135 && _la != 136) {
               this._errHandler.recoverInline(this);
            } else {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            }
         }

         this.setState(673);
         this.match(140);
         this.setState(677);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 54, this._ctx)) {
            case 1:
               this.setState(674);
               this.match(84);
               this.setState(675);
               this.match(106);
               this.setState(676);
               this.match(74);
         }

         this.setState(682);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 55, this._ctx)) {
            case 1:
               this.setState(679);
               this.database_name();
               this.setState(680);
               this.match(6);
         }

         this.setState(684);
         this.trigger_name();
         this.setState(689);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 32:
               this.setState(686);
               this.match(32);
               break;
            case 41:
               this.setState(685);
               this.match(41);
            case 63:
            case 92:
            case 143:
            default:
               break;
            case 93:
               this.setState(687);
               this.match(93);
               this.setState(688);
               this.match(109);
         }

         this.setState(705);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 63:
               this.setState(691);
               this.match(63);
               break;
            case 92:
               this.setState(692);
               this.match(92);
               break;
            case 143:
               this.setState(693);
               this.match(143);
               this.setState(703);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 109) {
                  this.setState(694);
                  this.match(109);
                  this.setState(695);
                  this.column_name();
                  this.setState(700);
                  this._errHandler.sync(this);

                  for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                     this.setState(696);
                     this.match(9);
                     this.setState(697);
                     this.column_name();
                     this.setState(702);
                     this._errHandler.sync(this);
                  }
               }
               break;
            default:
               throw new NoViableAltException(this);
         }

         this.setState(707);
         this.match(111);
         this.setState(711);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 60, this._ctx)) {
            case 1:
               this.setState(708);
               this.database_name();
               this.setState(709);
               this.match(6);
            default:
               this.setState(713);
               this.table_name();
               this.setState(717);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 77) {
                  this.setState(714);
                  this.match(77);
                  this.setState(715);
                  this.match(68);
                  this.setState(716);
                  this.match(130);
               }

               this.setState(721);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 149) {
                  this.setState(719);
                  this.match(149);
                  this.setState(720);
                  this.expr(0);
               }

               this.setState(723);
               this.match(42);
               this.setState(732);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
         }

         do {
            do {
               this.setState(728);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 63, this._ctx)) {
                  case 1:
                     this.setState(724);
                     this.update_stmt();
                     break;
                  case 2:
                     this.setState(725);
                     this.insert_stmt();
                     break;
                  case 3:
                     this.setState(726);
                     this.delete_stmt();
                     break;
                  case 4:
                     this.setState(727);
                     this.select_stmt();
               }

               this.setState(730);
               this.match(5);
               this.setState(734);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
            } while((_la - 63 & -64) == 0 && (1L << _la - 63 & -9223372036317904895L) != 0L);
         } while((_la - 132 & -64) == 0 && (1L << _la - 132 & 542721L) != 0L);

         this.setState(736);
         this.match(70);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Create_view_stmtContext create_view_stmt() throws RecognitionException {
      Create_view_stmtContext _localctx = new Create_view_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 90, 45);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(738);
         this.match(54);
         this.setState(740);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 135 || _la == 136) {
            this.setState(739);
            _la = this._input.LA(1);
            if (_la != 135 && _la != 136) {
               this._errHandler.recoverInline(this);
            } else {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            }
         }

         this.setState(742);
         this.match(147);
         this.setState(746);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 66, this._ctx)) {
            case 1:
               this.setState(743);
               this.match(84);
               this.setState(744);
               this.match(106);
               this.setState(745);
               this.match(74);
            default:
               this.setState(751);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 67, this._ctx)) {
                  case 1:
                     this.setState(748);
                     this.database_name();
                     this.setState(749);
                     this.match(6);
                  default:
                     this.setState(753);
                     this.view_name();
                     this.setState(754);
                     this.match(37);
                     this.setState(755);
                     this.select_stmt();
               }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Create_virtual_table_stmtContext create_virtual_table_stmt() throws RecognitionException {
      Create_virtual_table_stmtContext _localctx = new Create_virtual_table_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 92, 46);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(757);
         this.match(54);
         this.setState(758);
         this.match(148);
         this.setState(759);
         this.match(134);
         this.setState(763);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 68, this._ctx)) {
            case 1:
               this.setState(760);
               this.match(84);
               this.setState(761);
               this.match(106);
               this.setState(762);
               this.match(74);
            default:
               this.setState(768);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 69, this._ctx)) {
                  case 1:
                     this.setState(765);
                     this.database_name();
                     this.setState(766);
                     this.match(6);
               }

               this.setState(770);
               this.table_name();
               this.setState(771);
               this.match(144);
               this.setState(772);
               this.module_name();
               this.setState(784);
               this._errHandler.sync(this);
               int _la = this._input.LA(1);
               if (_la == 7) {
                  this.setState(773);
                  this.match(7);
                  this.setState(774);
                  this.module_argument();
                  this.setState(779);
                  this._errHandler.sync(this);

                  for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                     this.setState(775);
                     this.match(9);
                     this.setState(776);
                     this.module_argument();
                     this.setState(781);
                     this._errHandler.sync(this);
                  }

                  this.setState(782);
                  this.match(8);
               }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Delete_stmtContext delete_stmt() throws RecognitionException {
      Delete_stmtContext _localctx = new Delete_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 94, 47);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(787);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(786);
            this.with_clause();
         }

         this.setState(789);
         this.match(63);
         this.setState(790);
         this.match(79);
         this.setState(791);
         this.qualified_table_name();
         this.setState(794);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 150) {
            this.setState(792);
            this.match(150);
            this.setState(793);
            this.expr(0);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Delete_stmt_limitedContext delete_stmt_limited() throws RecognitionException {
      Delete_stmt_limitedContext _localctx = new Delete_stmt_limitedContext(this._ctx, this.getState());
      this.enterRule(_localctx, 96, 48);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(797);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(796);
            this.with_clause();
         }

         this.setState(799);
         this.match(63);
         this.setState(800);
         this.match(79);
         this.setState(801);
         this.qualified_table_name();
         this.setState(804);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 150) {
            this.setState(802);
            this.match(150);
            this.setState(803);
            this.expr(0);
         }

         this.setState(824);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 102 || _la == 113) {
            this.setState(816);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 113) {
               this.setState(806);
               this.match(113);
               this.setState(807);
               this.match(44);
               this.setState(808);
               this.ordering_term();
               this.setState(813);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(809);
                  this.match(9);
                  this.setState(810);
                  this.ordering_term();
                  this.setState(815);
                  this._errHandler.sync(this);
               }
            }

            this.setState(818);
            this.match(102);
            this.setState(819);
            this.expr(0);
            this.setState(822);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 9 || _la == 110) {
               this.setState(820);
               _la = this._input.LA(1);
               if (_la != 9 && _la != 110) {
                  this._errHandler.recoverInline(this);
               } else {
                  if (this._input.LA(1) == -1) {
                     this.matchedEOF = true;
                  }

                  this._errHandler.reportMatch(this);
                  this.consume();
               }

               this.setState(821);
               this.expr(0);
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Detach_stmtContext detach_stmt() throws RecognitionException {
      Detach_stmtContext _localctx = new Detach_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 98, 49);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(826);
         this.match(65);
         this.setState(828);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 80, this._ctx)) {
            case 1:
               this.setState(827);
               this.match(59);
            default:
               this.setState(830);
               this.database_name();
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Drop_index_stmtContext drop_index_stmt() throws RecognitionException {
      Drop_index_stmtContext _localctx = new Drop_index_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 100, 50);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(832);
         this.match(67);
         this.setState(833);
         this.match(88);
         this.setState(836);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 81, this._ctx)) {
            case 1:
               this.setState(834);
               this.match(84);
               this.setState(835);
               this.match(74);
            default:
               this.setState(841);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 82, this._ctx)) {
                  case 1:
                     this.setState(838);
                     this.database_name();
                     this.setState(839);
                     this.match(6);
                  default:
                     this.setState(843);
                     this.index_name();
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Drop_table_stmtContext drop_table_stmt() throws RecognitionException {
      Drop_table_stmtContext _localctx = new Drop_table_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 102, 51);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(845);
         this.match(67);
         this.setState(846);
         this.match(134);
         this.setState(849);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 83, this._ctx)) {
            case 1:
               this.setState(847);
               this.match(84);
               this.setState(848);
               this.match(74);
            default:
               this.setState(854);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 84, this._ctx)) {
                  case 1:
                     this.setState(851);
                     this.database_name();
                     this.setState(852);
                     this.match(6);
                  default:
                     this.setState(856);
                     this.table_name();
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Drop_trigger_stmtContext drop_trigger_stmt() throws RecognitionException {
      Drop_trigger_stmtContext _localctx = new Drop_trigger_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 104, 52);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(858);
         this.match(67);
         this.setState(859);
         this.match(140);
         this.setState(862);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 85, this._ctx)) {
            case 1:
               this.setState(860);
               this.match(84);
               this.setState(861);
               this.match(74);
            default:
               this.setState(867);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 86, this._ctx)) {
                  case 1:
                     this.setState(864);
                     this.database_name();
                     this.setState(865);
                     this.match(6);
                  default:
                     this.setState(869);
                     this.trigger_name();
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Drop_view_stmtContext drop_view_stmt() throws RecognitionException {
      Drop_view_stmtContext _localctx = new Drop_view_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 106, 53);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(871);
         this.match(67);
         this.setState(872);
         this.match(147);
         this.setState(875);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 87, this._ctx)) {
            case 1:
               this.setState(873);
               this.match(84);
               this.setState(874);
               this.match(74);
            default:
               this.setState(880);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 88, this._ctx)) {
                  case 1:
                     this.setState(877);
                     this.database_name();
                     this.setState(878);
                     this.match(6);
                  default:
                     this.setState(882);
                     this.view_name();
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Factored_select_stmtContext factored_select_stmt() throws RecognitionException {
      Factored_select_stmtContext _localctx = new Factored_select_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 108, 54);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(896);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(884);
            this.match(151);
            this.setState(886);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 89, this._ctx)) {
               case 1:
                  this.setState(885);
                  this.match(120);
               default:
                  this.setState(888);
                  this.common_table_expression();
                  this.setState(893);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
            }

            while(_la == 9) {
               this.setState(889);
               this.match(9);
               this.setState(890);
               this.common_table_expression();
               this.setState(895);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
            }
         }

         this.setState(898);
         this.select_core();
         this.setState(904);
         this._errHandler.sync(this);

         for(_la = this._input.LA(1); _la == 72 || _la == 94 || _la == 141; _la = this._input.LA(1)) {
            this.setState(899);
            this.compound_operator();
            this.setState(900);
            this.select_core();
            this.setState(906);
            this._errHandler.sync(this);
         }

         this.setState(917);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 113) {
            this.setState(907);
            this.match(113);
            this.setState(908);
            this.match(44);
            this.setState(909);
            this.ordering_term();
            this.setState(914);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(910);
               this.match(9);
               this.setState(911);
               this.ordering_term();
               this.setState(916);
               this._errHandler.sync(this);
            }
         }

         this.setState(925);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 102) {
            this.setState(919);
            this.match(102);
            this.setState(920);
            this.expr(0);
            this.setState(923);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 9 || _la == 110) {
               this.setState(921);
               _la = this._input.LA(1);
               if (_la != 9 && _la != 110) {
                  this._errHandler.recoverInline(this);
               } else {
                  if (this._input.LA(1) == -1) {
                     this.matchedEOF = true;
                  }

                  this._errHandler.reportMatch(this);
                  this.consume();
               }

               this.setState(922);
               this.expr(0);
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Insert_stmtContext insert_stmt() throws RecognitionException {
      Insert_stmtContext _localctx = new Insert_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 110, 55);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(928);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(927);
            this.with_clause();
         }

         this.setState(947);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 98, this._ctx)) {
            case 1:
               this.setState(930);
               this.match(92);
               break;
            case 2:
               this.setState(931);
               this.match(126);
               break;
            case 3:
               this.setState(932);
               this.match(92);
               this.setState(933);
               this.match(112);
               this.setState(934);
               this.match(126);
               break;
            case 4:
               this.setState(935);
               this.match(92);
               this.setState(936);
               this.match(112);
               this.setState(937);
               this.match(129);
               break;
            case 5:
               this.setState(938);
               this.match(92);
               this.setState(939);
               this.match(112);
               this.setState(940);
               this.match(29);
               break;
            case 6:
               this.setState(941);
               this.match(92);
               this.setState(942);
               this.match(112);
               this.setState(943);
               this.match(76);
               break;
            case 7:
               this.setState(944);
               this.match(92);
               this.setState(945);
               this.match(112);
               this.setState(946);
               this.match(85);
         }

         this.setState(949);
         this.match(95);
         this.setState(953);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 99, this._ctx)) {
            case 1:
               this.setState(950);
               this.database_name();
               this.setState(951);
               this.match(6);
         }

         this.setState(955);
         this.table_name();
         this.setState(967);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 7) {
            this.setState(956);
            this.match(7);
            this.setState(957);
            this.column_name();
            this.setState(962);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(958);
               this.match(9);
               this.setState(959);
               this.column_name();
               this.setState(964);
               this._errHandler.sync(this);
            }

            this.setState(965);
            this.match(8);
         }

         this.setState(1000);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 105, this._ctx)) {
            case 1:
               this.setState(969);
               this.match(146);
               this.setState(970);
               this.match(7);
               this.setState(971);
               this.expr(0);
               this.setState(976);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(972);
                  this.match(9);
                  this.setState(973);
                  this.expr(0);
                  this.setState(978);
                  this._errHandler.sync(this);
               }

               this.setState(979);
               this.match(8);
               this.setState(994);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(980);
                  this.match(9);
                  this.setState(981);
                  this.match(7);
                  this.setState(982);
                  this.expr(0);
                  this.setState(987);
                  this._errHandler.sync(this);

                  for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                     this.setState(983);
                     this.match(9);
                     this.setState(984);
                     this.expr(0);
                     this.setState(989);
                     this._errHandler.sync(this);
                  }

                  this.setState(990);
                  this.match(8);
                  this.setState(996);
                  this._errHandler.sync(this);
               }

               return _localctx;
            case 2:
               this.setState(997);
               this.select_stmt();
               break;
            case 3:
               this.setState(998);
               this.match(60);
               this.setState(999);
               this.match(146);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Pragma_stmtContext pragma_stmt() throws RecognitionException {
      Pragma_stmtContext _localctx = new Pragma_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 112, 56);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1002);
         this.match(116);
         this.setState(1006);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 106, this._ctx)) {
            case 1:
               this.setState(1003);
               this.database_name();
               this.setState(1004);
               this.match(6);
            default:
               this.setState(1008);
               this.pragma_name();
               this.setState(1015);
               this._errHandler.sync(this);
               switch (this._input.LA(1)) {
                  case -1:
                  case 5:
                  case 34:
                  case 35:
                  case 39:
                  case 42:
                  case 51:
                  case 54:
                  case 63:
                  case 65:
                  case 67:
                  case 70:
                  case 75:
                  case 92:
                  case 116:
                  case 123:
                  case 124:
                  case 126:
                  case 129:
                  case 131:
                  case 132:
                  case 143:
                  case 145:
                  case 146:
                  case 151:
                  case 161:
                  default:
                     break;
                  case 7:
                     this.setState(1011);
                     this.match(7);
                     this.setState(1012);
                     this.pragma_value();
                     this.setState(1013);
                     this.match(8);
                     break;
                  case 10:
                     this.setState(1009);
                     this.match(10);
                     this.setState(1010);
                     this.pragma_value();
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Reindex_stmtContext reindex_stmt() throws RecognitionException {
      Reindex_stmtContext _localctx = new Reindex_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 114, 57);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1017);
         this.match(123);
         this.setState(1028);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 110, this._ctx)) {
            case 1:
               this.setState(1018);
               this.collation_name();
               break;
            case 2:
               this.setState(1022);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 108, this._ctx)) {
                  case 1:
                     this.setState(1019);
                     this.database_name();
                     this.setState(1020);
                     this.match(6);
                  default:
                     this.setState(1026);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 109, this._ctx)) {
                        case 1:
                           this.setState(1024);
                           this.table_name();
                           break;
                        case 2:
                           this.setState(1025);
                           this.index_name();
                     }
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Release_stmtContext release_stmt() throws RecognitionException {
      Release_stmtContext _localctx = new Release_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 116, 58);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1030);
         this.match(124);
         this.setState(1032);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 111, this._ctx)) {
            case 1:
               this.setState(1031);
               this.match(131);
            default:
               this.setState(1034);
               this.savepoint_name();
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Rollback_stmtContext rollback_stmt() throws RecognitionException {
      Rollback_stmtContext _localctx = new Rollback_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 118, 59);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1036);
         this.match(129);
         this.setState(1041);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 139) {
            this.setState(1037);
            this.match(139);
            this.setState(1039);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 112, this._ctx)) {
               case 1:
                  this.setState(1038);
                  this.transaction_name();
            }
         }

         this.setState(1048);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 138) {
            this.setState(1043);
            this.match(138);
            this.setState(1045);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 114, this._ctx)) {
               case 1:
                  this.setState(1044);
                  this.match(131);
               default:
                  this.setState(1047);
                  this.savepoint_name();
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Savepoint_stmtContext savepoint_stmt() throws RecognitionException {
      Savepoint_stmtContext _localctx = new Savepoint_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 120, 60);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1050);
         this.match(131);
         this.setState(1051);
         this.savepoint_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Simple_select_stmtContext simple_select_stmt() throws RecognitionException {
      Simple_select_stmtContext _localctx = new Simple_select_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 122, 61);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1065);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(1053);
            this.match(151);
            this.setState(1055);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 116, this._ctx)) {
               case 1:
                  this.setState(1054);
                  this.match(120);
               default:
                  this.setState(1057);
                  this.common_table_expression();
                  this.setState(1062);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
            }

            while(_la == 9) {
               this.setState(1058);
               this.match(9);
               this.setState(1059);
               this.common_table_expression();
               this.setState(1064);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
            }
         }

         this.setState(1067);
         this.select_core();
         this.setState(1078);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 113) {
            this.setState(1068);
            this.match(113);
            this.setState(1069);
            this.match(44);
            this.setState(1070);
            this.ordering_term();
            this.setState(1075);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(1071);
               this.match(9);
               this.setState(1072);
               this.ordering_term();
               this.setState(1077);
               this._errHandler.sync(this);
            }
         }

         this.setState(1086);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 102) {
            this.setState(1080);
            this.match(102);
            this.setState(1081);
            this.expr(0);
            this.setState(1084);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 9 || _la == 110) {
               this.setState(1082);
               _la = this._input.LA(1);
               if (_la != 9 && _la != 110) {
                  this._errHandler.recoverInline(this);
               } else {
                  if (this._input.LA(1) == -1) {
                     this.matchedEOF = true;
                  }

                  this._errHandler.reportMatch(this);
                  this.consume();
               }

               this.setState(1083);
               this.expr(0);
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Select_stmtContext select_stmt() throws RecognitionException {
      Select_stmtContext _localctx = new Select_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 124, 62);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1100);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(1088);
            this.match(151);
            this.setState(1090);
            this._errHandler.sync(this);
            switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 123, this._ctx)) {
               case 1:
                  this.setState(1089);
                  this.match(120);
               default:
                  this.setState(1092);
                  this.common_table_expression();
                  this.setState(1097);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
            }

            while(_la == 9) {
               this.setState(1093);
               this.match(9);
               this.setState(1094);
               this.common_table_expression();
               this.setState(1099);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
            }
         }

         this.setState(1102);
         this.select_or_values();
         this.setState(1108);
         this._errHandler.sync(this);

         for(_la = this._input.LA(1); _la == 72 || _la == 94 || _la == 141; _la = this._input.LA(1)) {
            this.setState(1103);
            this.compound_operator();
            this.setState(1104);
            this.select_or_values();
            this.setState(1110);
            this._errHandler.sync(this);
         }

         this.setState(1121);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 113) {
            this.setState(1111);
            this.match(113);
            this.setState(1112);
            this.match(44);
            this.setState(1113);
            this.ordering_term();
            this.setState(1118);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(1114);
               this.match(9);
               this.setState(1115);
               this.ordering_term();
               this.setState(1120);
               this._errHandler.sync(this);
            }
         }

         this.setState(1129);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 102) {
            this.setState(1123);
            this.match(102);
            this.setState(1124);
            this.expr(0);
            this.setState(1127);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 9 || _la == 110) {
               this.setState(1125);
               _la = this._input.LA(1);
               if (_la != 9 && _la != 110) {
                  this._errHandler.recoverInline(this);
               } else {
                  if (this._input.LA(1) == -1) {
                     this.matchedEOF = true;
                  }

                  this._errHandler.reportMatch(this);
                  this.consume();
               }

               this.setState(1126);
               this.expr(0);
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Select_or_valuesContext select_or_values() throws RecognitionException {
      Select_or_valuesContext _localctx = new Select_or_valuesContext(this._ctx, this.getState());
      this.enterRule(_localctx, 126, 63);

      try {
         this.setState(1205);
         this._errHandler.sync(this);
         int _la;
         switch (this._input.LA(1)) {
            case 132:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1131);
               this.match(132);
               this.setState(1133);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 131, this._ctx)) {
                  case 1:
                     this.setState(1132);
                     _la = this._input.LA(1);
                     if (_la != 33 && _la != 66) {
                        this._errHandler.recoverInline(this);
                     } else {
                        if (this._input.LA(1) == -1) {
                           this.matchedEOF = true;
                        }

                        this._errHandler.reportMatch(this);
                        this.consume();
                     }
               }

               this.setState(1135);
               this.result_column();
               this.setState(1140);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1136);
                  this.match(9);
                  this.setState(1137);
                  this.result_column();
                  this.setState(1142);
                  this._errHandler.sync(this);
               }

               this.setState(1155);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 79) {
                  this.setState(1143);
                  this.match(79);
                  this.setState(1153);
                  this._errHandler.sync(this);
                  label178:
                  switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 134, this._ctx)) {
                     case 1:
                        this.setState(1144);
                        this.table_or_subquery();
                        this.setState(1149);
                        this._errHandler.sync(this);
                        _la = this._input.LA(1);

                        while(true) {
                           if (_la != 9) {
                              break label178;
                           }

                           this.setState(1145);
                           this.match(9);
                           this.setState(1146);
                           this.table_or_subquery();
                           this.setState(1151);
                           this._errHandler.sync(this);
                           _la = this._input.LA(1);
                        }
                     case 2:
                        this.setState(1152);
                        this.join_clause();
                  }
               }

               this.setState(1159);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 150) {
                  this.setState(1157);
                  this.match(150);
                  this.setState(1158);
                  this.expr(0);
               }

               this.setState(1175);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 82) {
                  this.setState(1161);
                  this.match(82);
                  this.setState(1162);
                  this.match(44);
                  this.setState(1163);
                  this.expr(0);
                  this.setState(1168);
                  this._errHandler.sync(this);

                  for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                     this.setState(1164);
                     this.match(9);
                     this.setState(1165);
                     this.expr(0);
                     this.setState(1170);
                     this._errHandler.sync(this);
                  }

                  this.setState(1173);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
                  if (_la == 83) {
                     this.setState(1171);
                     this.match(83);
                     this.setState(1172);
                     this.expr(0);
                  }
               }
               break;
            case 146:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1177);
               this.match(146);
               this.setState(1178);
               this.match(7);
               this.setState(1179);
               this.expr(0);
               this.setState(1184);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1180);
                  this.match(9);
                  this.setState(1181);
                  this.expr(0);
                  this.setState(1186);
                  this._errHandler.sync(this);
               }

               this.setState(1187);
               this.match(8);
               this.setState(1202);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1188);
                  this.match(9);
                  this.setState(1189);
                  this.match(7);
                  this.setState(1190);
                  this.expr(0);
                  this.setState(1195);
                  this._errHandler.sync(this);

                  for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                     this.setState(1191);
                     this.match(9);
                     this.setState(1192);
                     this.expr(0);
                     this.setState(1197);
                     this._errHandler.sync(this);
                  }

                  this.setState(1198);
                  this.match(8);
                  this.setState(1204);
                  this._errHandler.sync(this);
               }

               return _localctx;
            default:
               throw new NoViableAltException(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Update_stmtContext update_stmt() throws RecognitionException {
      Update_stmtContext _localctx = new Update_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 128, 64);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1208);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(1207);
            this.with_clause();
         }

         this.setState(1210);
         this.match(143);
         this.setState(1221);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 145, this._ctx)) {
            case 1:
               this.setState(1211);
               this.match(112);
               this.setState(1212);
               this.match(129);
               break;
            case 2:
               this.setState(1213);
               this.match(112);
               this.setState(1214);
               this.match(29);
               break;
            case 3:
               this.setState(1215);
               this.match(112);
               this.setState(1216);
               this.match(126);
               break;
            case 4:
               this.setState(1217);
               this.match(112);
               this.setState(1218);
               this.match(76);
               break;
            case 5:
               this.setState(1219);
               this.match(112);
               this.setState(1220);
               this.match(85);
         }

         this.setState(1223);
         this.qualified_table_name();
         this.setState(1224);
         this.match(133);
         this.setState(1225);
         this.column_name();
         this.setState(1226);
         this.match(10);
         this.setState(1227);
         this.expr(0);
         this.setState(1235);
         this._errHandler.sync(this);

         for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
            this.setState(1228);
            this.match(9);
            this.setState(1229);
            this.column_name();
            this.setState(1230);
            this.match(10);
            this.setState(1231);
            this.expr(0);
            this.setState(1237);
            this._errHandler.sync(this);
         }

         this.setState(1240);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 150) {
            this.setState(1238);
            this.match(150);
            this.setState(1239);
            this.expr(0);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Update_stmt_limitedContext update_stmt_limited() throws RecognitionException {
      Update_stmt_limitedContext _localctx = new Update_stmt_limitedContext(this._ctx, this.getState());
      this.enterRule(_localctx, 130, 65);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1243);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 151) {
            this.setState(1242);
            this.with_clause();
         }

         this.setState(1245);
         this.match(143);
         this.setState(1256);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 149, this._ctx)) {
            case 1:
               this.setState(1246);
               this.match(112);
               this.setState(1247);
               this.match(129);
               break;
            case 2:
               this.setState(1248);
               this.match(112);
               this.setState(1249);
               this.match(29);
               break;
            case 3:
               this.setState(1250);
               this.match(112);
               this.setState(1251);
               this.match(126);
               break;
            case 4:
               this.setState(1252);
               this.match(112);
               this.setState(1253);
               this.match(76);
               break;
            case 5:
               this.setState(1254);
               this.match(112);
               this.setState(1255);
               this.match(85);
         }

         this.setState(1258);
         this.qualified_table_name();
         this.setState(1259);
         this.match(133);
         this.setState(1260);
         this.column_name();
         this.setState(1261);
         this.match(10);
         this.setState(1262);
         this.expr(0);
         this.setState(1270);
         this._errHandler.sync(this);

         for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
            this.setState(1263);
            this.match(9);
            this.setState(1264);
            this.column_name();
            this.setState(1265);
            this.match(10);
            this.setState(1266);
            this.expr(0);
            this.setState(1272);
            this._errHandler.sync(this);
         }

         this.setState(1275);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 150) {
            this.setState(1273);
            this.match(150);
            this.setState(1274);
            this.expr(0);
         }

         this.setState(1295);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 102 || _la == 113) {
            this.setState(1287);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 113) {
               this.setState(1277);
               this.match(113);
               this.setState(1278);
               this.match(44);
               this.setState(1279);
               this.ordering_term();
               this.setState(1284);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1280);
                  this.match(9);
                  this.setState(1281);
                  this.ordering_term();
                  this.setState(1286);
                  this._errHandler.sync(this);
               }
            }

            this.setState(1289);
            this.match(102);
            this.setState(1290);
            this.expr(0);
            this.setState(1293);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
            if (_la == 9 || _la == 110) {
               this.setState(1291);
               _la = this._input.LA(1);
               if (_la != 9 && _la != 110) {
                  this._errHandler.recoverInline(this);
               } else {
                  if (this._input.LA(1) == -1) {
                     this.matchedEOF = true;
                  }

                  this._errHandler.reportMatch(this);
                  this.consume();
               }

               this.setState(1292);
               this.expr(0);
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Vacuum_stmtContext vacuum_stmt() throws RecognitionException {
      Vacuum_stmtContext _localctx = new Vacuum_stmtContext(this._ctx, this.getState());
      this.enterRule(_localctx, 132, 66);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1297);
         this.match(145);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Column_defContext column_def() throws RecognitionException {
      Column_defContext _localctx = new Column_defContext(this._ctx, this.getState());
      this.enterRule(_localctx, 134, 67);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1299);
         this.column_name();
         this.setState(1301);
         this._errHandler.sync(this);
         int _la;
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 156, this._ctx)) {
            case 1:
               this.setState(1300);
               this.type_name();
            default:
               this.setState(1306);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
         }

         while((_la & -64) == 0 && (1L << _la & 1162773128791719936L) != 0L || (_la - 106 & -64) == 0 && (1L << _la - 106 & 68719511557L) != 0L) {
            this.setState(1303);
            this.column_constraint();
            this.setState(1308);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Type_nameContext type_name() throws RecognitionException {
      Type_nameContext _localctx = new Type_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 136, 68);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1310);
         this._errHandler.sync(this);
         int _alt = 1;

         do {
            switch (_alt) {
               case 1:
                  this.setState(1309);
                  this.name();
                  this.setState(1312);
                  this._errHandler.sync(this);
                  _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 158, this._ctx);
                  break;
               default:
                  throw new NoViableAltException(this);
            }
         } while(_alt != 2 && _alt != 0);

         this.setState(1324);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 159, this._ctx)) {
            case 1:
               this.setState(1314);
               this.match(7);
               this.setState(1315);
               this.signed_number();
               this.setState(1316);
               this.match(8);
               break;
            case 2:
               this.setState(1318);
               this.match(7);
               this.setState(1319);
               this.signed_number();
               this.setState(1320);
               this.match(9);
               this.setState(1321);
               this.signed_number();
               this.setState(1322);
               this.match(8);
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Column_constraintContext column_constraint() throws RecognitionException {
      Column_constraintContext _localctx = new Column_constraintContext(this._ctx, this.getState());
      this.enterRule(_localctx, 138, 69);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1328);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 53) {
            this.setState(1326);
            this.match(53);
            this.setState(1327);
            this.name();
         }

         this.setState(1363);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 48:
               this.setState(1346);
               this.match(48);
               this.setState(1347);
               this.match(7);
               this.setState(1348);
               this.expr(0);
               this.setState(1349);
               this.match(8);
               break;
            case 49:
               this.setState(1360);
               this.match(49);
               this.setState(1361);
               this.collation_name();
               break;
            case 60:
               this.setState(1351);
               this.match(60);
               this.setState(1358);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 164, this._ctx)) {
                  case 1:
                     this.setState(1352);
                     this.signed_number();
                     return _localctx;
                  case 2:
                     this.setState(1353);
                     this.literal_value();
                     return _localctx;
                  case 3:
                     this.setState(1354);
                     this.match(7);
                     this.setState(1355);
                     this.expr(0);
                     this.setState(1356);
                     this.match(8);
                     return _localctx;
                  default:
                     return _localctx;
               }
            case 106:
            case 108:
               this.setState(1340);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 106) {
                  this.setState(1339);
                  this.match(106);
               }

               this.setState(1342);
               this.match(108);
               this.setState(1343);
               this.conflict_clause();
               break;
            case 117:
               this.setState(1330);
               this.match(117);
               this.setState(1331);
               this.match(99);
               this.setState(1333);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 38 || _la == 64) {
                  this.setState(1332);
                  _la = this._input.LA(1);
                  if (_la != 38 && _la != 64) {
                     this._errHandler.recoverInline(this);
                  } else {
                     if (this._input.LA(1) == -1) {
                        this.matchedEOF = true;
                     }

                     this._errHandler.reportMatch(this);
                     this.consume();
                  }
               }

               this.setState(1335);
               this.conflict_clause();
               this.setState(1337);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 40) {
                  this.setState(1336);
                  this.match(40);
               }
               break;
            case 121:
               this.setState(1362);
               this.foreign_key_clause();
               break;
            case 142:
               this.setState(1344);
               this.match(142);
               this.setState(1345);
               this.conflict_clause();
               break;
            default:
               throw new NoViableAltException(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Conflict_clauseContext conflict_clause() throws RecognitionException {
      Conflict_clauseContext _localctx = new Conflict_clauseContext(this._ctx, this.getState());
      this.enterRule(_localctx, 140, 70);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1368);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 111) {
            this.setState(1365);
            this.match(111);
            this.setState(1366);
            this.match(52);
            this.setState(1367);
            _la = this._input.LA(1);
            if (_la != 29 && ((_la - 76 & -64) != 0 || (1L << _la - 76 & 10133099161584129L) == 0L)) {
               this._errHandler.recoverInline(this);
            } else {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final ExprContext expr() throws RecognitionException {
      return this.expr(0);
   }

   private ExprContext expr(int _p) throws RecognitionException {
      ParserRuleContext _parentctx = this._ctx;
      int _parentState = this.getState();
      ExprContext _localctx = new ExprContext(this._ctx, _parentState);
      int _startState = 142;
      this.enterRecursionRule(_localctx, 142, 71, _p);

      try {
         int _la;
         this.enterOuterAlt(_localctx, 1);
         this.setState(1446);
         this._errHandler.sync(this);
         label547:
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 177, this._ctx)) {
            case 1:
               this.setState(1371);
               this.literal_value();
               break;
            case 2:
               this.setState(1372);
               this.match(155);
               break;
            case 3:
               this.setState(1381);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 168, this._ctx)) {
                  case 1:
                     this.setState(1376);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 167, this._ctx)) {
                        case 1:
                           this.setState(1373);
                           this.database_name();
                           this.setState(1374);
                           this.match(6);
                        default:
                           this.setState(1378);
                           this.table_name();
                           this.setState(1379);
                           this.match(6);
                     }
                  default:
                     this.setState(1383);
                     this.column_name();
                     break label547;
               }
            case 4:
               this.setState(1384);
               this.unary_operator();
               this.setState(1385);
               this.expr(21);
               break;
            case 5:
               this.setState(1387);
               this.function_name();
               this.setState(1388);
               this.match(7);
               this.setState(1401);
               this._errHandler.sync(this);
               switch (this._input.LA(1)) {
                  case 7:
                  case 12:
                  case 13:
                  case 14:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 36:
                  case 37:
                  case 38:
                  case 39:
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 44:
                  case 45:
                  case 46:
                  case 47:
                  case 48:
                  case 49:
                  case 50:
                  case 51:
                  case 52:
                  case 53:
                  case 54:
                  case 55:
                  case 56:
                  case 57:
                  case 58:
                  case 59:
                  case 60:
                  case 61:
                  case 62:
                  case 63:
                  case 64:
                  case 65:
                  case 66:
                  case 67:
                  case 68:
                  case 69:
                  case 70:
                  case 71:
                  case 72:
                  case 73:
                  case 74:
                  case 75:
                  case 76:
                  case 77:
                  case 78:
                  case 79:
                  case 80:
                  case 81:
                  case 82:
                  case 83:
                  case 84:
                  case 85:
                  case 86:
                  case 87:
                  case 88:
                  case 89:
                  case 90:
                  case 91:
                  case 92:
                  case 93:
                  case 94:
                  case 95:
                  case 96:
                  case 97:
                  case 98:
                  case 99:
                  case 100:
                  case 101:
                  case 102:
                  case 103:
                  case 104:
                  case 105:
                  case 106:
                  case 107:
                  case 108:
                  case 109:
                  case 110:
                  case 111:
                  case 112:
                  case 113:
                  case 114:
                  case 115:
                  case 116:
                  case 117:
                  case 118:
                  case 119:
                  case 120:
                  case 121:
                  case 122:
                  case 123:
                  case 124:
                  case 125:
                  case 126:
                  case 127:
                  case 128:
                  case 129:
                  case 130:
                  case 131:
                  case 132:
                  case 133:
                  case 134:
                  case 135:
                  case 136:
                  case 137:
                  case 138:
                  case 139:
                  case 140:
                  case 141:
                  case 142:
                  case 143:
                  case 144:
                  case 145:
                  case 146:
                  case 147:
                  case 148:
                  case 149:
                  case 150:
                  case 151:
                  case 152:
                  case 153:
                  case 154:
                  case 155:
                  case 156:
                  case 157:
                     this.setState(1390);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 169, this._ctx)) {
                        case 1:
                           this.setState(1389);
                           this.match(66);
                        default:
                           this.setState(1392);
                           this.expr(0);
                           this.setState(1397);
                           this._errHandler.sync(this);
                           _la = this._input.LA(1);
                     }

                     while(_la == 9) {
                        this.setState(1393);
                        this.match(9);
                        this.setState(1394);
                        this.expr(0);
                        this.setState(1399);
                        this._errHandler.sync(this);
                        _la = this._input.LA(1);
                     }
                  case 8:
                  case 9:
                  case 10:
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
                  case 26:
                  case 27:
                  case 28:
                  default:
                     break;
                  case 11:
                     this.setState(1400);
                     this.match(11);
               }

               this.setState(1403);
               this.match(8);
               break;
            case 6:
               this.setState(1405);
               this.match(7);
               this.setState(1406);
               this.expr(0);
               this.setState(1407);
               this.match(8);
               break;
            case 7:
               this.setState(1409);
               this.match(47);
               this.setState(1410);
               this.match(7);
               this.setState(1411);
               this.expr(0);
               this.setState(1412);
               this.match(37);
               this.setState(1413);
               this.type_name();
               this.setState(1414);
               this.match(8);
               break;
            case 8:
               this.setState(1420);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 74 || _la == 106) {
                  this.setState(1417);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
                  if (_la == 106) {
                     this.setState(1416);
                     this.match(106);
                  }

                  this.setState(1419);
                  this.match(74);
               }

               this.setState(1422);
               this.match(7);
               this.setState(1423);
               this.select_stmt();
               this.setState(1424);
               this.match(8);
               break;
            case 9:
               this.setState(1426);
               this.match(46);
               this.setState(1428);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 174, this._ctx)) {
                  case 1:
                     this.setState(1427);
                     this.expr(0);
                  default:
                     this.setState(1435);
                     this._errHandler.sync(this);
                     _la = this._input.LA(1);
               }

               do {
                  this.setState(1430);
                  this.match(149);
                  this.setState(1431);
                  this.expr(0);
                  this.setState(1432);
                  this.match(137);
                  this.setState(1433);
                  this.expr(0);
                  this.setState(1437);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
               } while(_la == 149);

               this.setState(1441);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 69) {
                  this.setState(1439);
                  this.match(69);
                  this.setState(1440);
                  this.expr(0);
               }

               this.setState(1443);
               this.match(70);
               break;
            case 10:
               this.setState(1445);
               this.raise_function();
         }

         this._ctx.stop = this._input.LT(-1);
         this.setState(1548);
         this._errHandler.sync(this);

         for(int _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 190, this._ctx); _alt != 2 && _alt != 0; _alt = ((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 190, this._ctx)) {
            if (_alt == 1) {
               if (this._parseListeners != null) {
                  this.triggerExitRuleEvent();
               }

               this.setState(1546);
               this._errHandler.sync(this);
               label616:
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 189, this._ctx)) {
                  case 1:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1448);
                     if (!this.precpred(this._ctx, 20)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 20)");
                     }

                     this.setState(1449);
                     this.match(15);
                     this.setState(1450);
                     this.expr(21);
                     break;
                  case 2:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1451);
                     if (!this.precpred(this._ctx, 19)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 19)");
                     }

                     this.setState(1452);
                     _la = this._input.LA(1);
                     if ((_la & -64) == 0 && (1L << _la & 198656L) != 0L) {
                        if (this._input.LA(1) == -1) {
                           this.matchedEOF = true;
                        }

                        this._errHandler.reportMatch(this);
                        this.consume();
                     } else {
                        this._errHandler.recoverInline(this);
                     }

                     this.setState(1453);
                     this.expr(20);
                     break;
                  case 3:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1454);
                     if (!this.precpred(this._ctx, 18)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 18)");
                     }

                     this.setState(1455);
                     _la = this._input.LA(1);
                     if (_la != 12 && _la != 13) {
                        this._errHandler.recoverInline(this);
                     } else {
                        if (this._input.LA(1) == -1) {
                           this.matchedEOF = true;
                        }

                        this._errHandler.reportMatch(this);
                        this.consume();
                     }

                     this.setState(1456);
                     this.expr(19);
                     break;
                  case 4:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1457);
                     if (!this.precpred(this._ctx, 17)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 17)");
                     }

                     this.setState(1458);
                     _la = this._input.LA(1);
                     if ((_la & -64) == 0 && (1L << _la & 3932160L) != 0L) {
                        if (this._input.LA(1) == -1) {
                           this.matchedEOF = true;
                        }

                        this._errHandler.reportMatch(this);
                        this.consume();
                     } else {
                        this._errHandler.recoverInline(this);
                     }

                     this.setState(1459);
                     this.expr(18);
                     break;
                  case 5:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1460);
                     if (!this.precpred(this._ctx, 16)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 16)");
                     }

                     this.setState(1461);
                     _la = this._input.LA(1);
                     if ((_la & -64) == 0 && (1L << _la & 62914560L) != 0L) {
                        if (this._input.LA(1) == -1) {
                           this.matchedEOF = true;
                        }

                        this._errHandler.reportMatch(this);
                        this.consume();
                     } else {
                        this._errHandler.recoverInline(this);
                     }

                     this.setState(1462);
                     this.expr(17);
                     break;
                  case 6:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1463);
                     if (!this.precpred(this._ctx, 15)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 15)");
                     }

                     this.setState(1476);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 178, this._ctx)) {
                        case 1:
                           this.setState(1464);
                           this.match(10);
                           break;
                        case 2:
                           this.setState(1465);
                           this.match(26);
                           break;
                        case 3:
                           this.setState(1466);
                           this.match(27);
                           break;
                        case 4:
                           this.setState(1467);
                           this.match(28);
                           break;
                        case 5:
                           this.setState(1468);
                           this.match(96);
                           break;
                        case 6:
                           this.setState(1469);
                           this.match(96);
                           this.setState(1470);
                           this.match(106);
                           break;
                        case 7:
                           this.setState(1471);
                           this.match(87);
                           break;
                        case 8:
                           this.setState(1472);
                           this.match(101);
                           break;
                        case 9:
                           this.setState(1473);
                           this.match(81);
                           break;
                        case 10:
                           this.setState(1474);
                           this.match(103);
                           break;
                        case 11:
                           this.setState(1475);
                           this.match(122);
                     }

                     this.setState(1478);
                     this.expr(16);
                     break;
                  case 7:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1479);
                     if (!this.precpred(this._ctx, 14)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 14)");
                     }

                     this.setState(1480);
                     this.match(36);
                     this.setState(1481);
                     this.expr(15);
                     break;
                  case 8:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1482);
                     if (!this.precpred(this._ctx, 13)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 13)");
                     }

                     this.setState(1483);
                     this.match(112);
                     this.setState(1484);
                     this.expr(14);
                     break;
                  case 9:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1485);
                     if (!this.precpred(this._ctx, 6)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 6)");
                     }

                     this.setState(1486);
                     this.match(96);
                     this.setState(1488);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 179, this._ctx)) {
                        case 1:
                           this.setState(1487);
                           this.match(106);
                        default:
                           this.setState(1490);
                           this.expr(7);
                           break label616;
                     }
                  case 10:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1491);
                     if (!this.precpred(this._ctx, 5)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 5)");
                     }

                     this.setState(1493);
                     this._errHandler.sync(this);
                     _la = this._input.LA(1);
                     if (_la == 106) {
                        this.setState(1492);
                        this.match(106);
                     }

                     this.setState(1495);
                     this.match(43);
                     this.setState(1496);
                     this.expr(0);
                     this.setState(1497);
                     this.match(36);
                     this.setState(1498);
                     this.expr(6);
                     break;
                  case 11:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1500);
                     if (!this.precpred(this._ctx, 9)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 9)");
                     }

                     this.setState(1501);
                     this.match(49);
                     this.setState(1502);
                     this.collation_name();
                     break;
                  case 12:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1503);
                     if (!this.precpred(this._ctx, 8)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 8)");
                     }

                     this.setState(1505);
                     this._errHandler.sync(this);
                     _la = this._input.LA(1);
                     if (_la == 106) {
                        this.setState(1504);
                        this.match(106);
                     }

                     this.setState(1507);
                     _la = this._input.LA(1);
                     if ((_la - 81 & -64) == 0 && (1L << _la - 81 & 2199028498433L) != 0L) {
                        if (this._input.LA(1) == -1) {
                           this.matchedEOF = true;
                        }

                        this._errHandler.reportMatch(this);
                        this.consume();
                     } else {
                        this._errHandler.recoverInline(this);
                     }

                     this.setState(1508);
                     this.expr(0);
                     this.setState(1511);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 182, this._ctx)) {
                        case 1:
                           this.setState(1509);
                           this.match(71);
                           this.setState(1510);
                           this.expr(0);
                        default:
                           break label616;
                     }
                  case 13:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1513);
                     if (!this.precpred(this._ctx, 7)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 7)");
                     }

                     this.setState(1518);
                     this._errHandler.sync(this);
                     switch (this._input.LA(1)) {
                        case 97:
                           this.setState(1514);
                           this.match(97);
                           break label616;
                        case 106:
                           this.setState(1516);
                           this.match(106);
                           this.setState(1517);
                           this.match(108);
                           break label616;
                        case 107:
                           this.setState(1515);
                           this.match(107);
                           break label616;
                        default:
                           throw new NoViableAltException(this);
                     }
                  case 14:
                     _localctx = new ExprContext(_parentctx, _parentState);
                     this.pushNewRecursionContext(_localctx, _startState, 71);
                     this.setState(1520);
                     if (!this.precpred(this._ctx, 4)) {
                        throw new FailedPredicateException(this, "precpred(_ctx, 4)");
                     }

                     this.setState(1522);
                     this._errHandler.sync(this);
                     _la = this._input.LA(1);
                     if (_la == 106) {
                        this.setState(1521);
                        this.match(106);
                     }

                     this.setState(1524);
                     this.match(87);
                     this.setState(1544);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 188, this._ctx)) {
                        case 1:
                           this.setState(1525);
                           this.match(7);
                           this.setState(1535);
                           this._errHandler.sync(this);
                           switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 186, this._ctx)) {
                              case 1:
                                 this.setState(1526);
                                 this.select_stmt();
                                 break;
                              case 2:
                                 this.setState(1527);
                                 this.expr(0);
                                 this.setState(1532);
                                 this._errHandler.sync(this);

                                 for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                                    this.setState(1528);
                                    this.match(9);
                                    this.setState(1529);
                                    this.expr(0);
                                    this.setState(1534);
                                    this._errHandler.sync(this);
                                 }
                           }

                           this.setState(1537);
                           this.match(8);
                           break;
                        case 2:
                           this.setState(1541);
                           this._errHandler.sync(this);
                           switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 187, this._ctx)) {
                              case 1:
                                 this.setState(1538);
                                 this.database_name();
                                 this.setState(1539);
                                 this.match(6);
                              default:
                                 this.setState(1543);
                                 this.table_name();
                           }
                     }
               }
            }

            this.setState(1550);
            this._errHandler.sync(this);
         }
      } catch (RecognitionException var12) {
         _localctx.exception = var12;
         this._errHandler.reportError(this, var12);
         this._errHandler.recover(this, var12);
      } finally {
         this.unrollRecursionContexts(_parentctx);
      }

      return _localctx;
   }

   public final Foreign_key_clauseContext foreign_key_clause() throws RecognitionException {
      Foreign_key_clauseContext _localctx = new Foreign_key_clauseContext(this._ctx, this.getState());
      this.enterRule(_localctx, 144, 72);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1551);
         this.match(121);
         this.setState(1552);
         this.foreign_table();
         this.setState(1564);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 7) {
            this.setState(1553);
            this.match(7);
            this.setState(1554);
            this.column_name();
            this.setState(1559);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(1555);
               this.match(9);
               this.setState(1556);
               this.column_name();
               this.setState(1561);
               this._errHandler.sync(this);
            }

            this.setState(1562);
            this.match(8);
         }

         this.setState(1584);
         this._errHandler.sync(this);

         for(_la = this._input.LA(1); _la == 103 || _la == 111; _la = this._input.LA(1)) {
            this.setState(1580);
            this._errHandler.sync(this);
            label168:
            switch (this._input.LA(1)) {
               case 103:
                  this.setState(1578);
                  this.match(103);
                  this.setState(1579);
                  this.name();
                  break;
               case 111:
                  this.setState(1566);
                  this.match(111);
                  this.setState(1567);
                  _la = this._input.LA(1);
                  if (_la != 63 && _la != 143) {
                     this._errHandler.recoverInline(this);
                  } else {
                     if (this._input.LA(1) == -1) {
                        this.matchedEOF = true;
                     }

                     this._errHandler.reportMatch(this);
                     this.consume();
                  }

                  this.setState(1576);
                  this._errHandler.sync(this);
                  switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 193, this._ctx)) {
                     case 1:
                        this.setState(1568);
                        this.match(133);
                        this.setState(1569);
                        this.match(108);
                        break label168;
                     case 2:
                        this.setState(1570);
                        this.match(133);
                        this.setState(1571);
                        this.match(60);
                        break label168;
                     case 3:
                        this.setState(1572);
                        this.match(45);
                        break label168;
                     case 4:
                        this.setState(1573);
                        this.match(127);
                        break label168;
                     case 5:
                        this.setState(1574);
                        this.match(105);
                        this.setState(1575);
                        this.match(30);
                     default:
                        break label168;
                  }
               default:
                  throw new NoViableAltException(this);
            }

            this.setState(1586);
            this._errHandler.sync(this);
         }

         this.setState(1597);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 198, this._ctx)) {
            case 1:
               this.setState(1588);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 106) {
                  this.setState(1587);
                  this.match(106);
               }

               this.setState(1590);
               this.match(61);
               this.setState(1595);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 197, this._ctx)) {
                  case 1:
                     this.setState(1591);
                     this.match(90);
                     this.setState(1592);
                     this.match(62);
                     return _localctx;
                  case 2:
                     this.setState(1593);
                     this.match(90);
                     this.setState(1594);
                     this.match(86);
                     return _localctx;
               }
            default:
               return _localctx;
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Raise_functionContext raise_function() throws RecognitionException {
      Raise_functionContext _localctx = new Raise_functionContext(this._ctx, this.getState());
      this.enterRule(_localctx, 146, 73);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1599);
         this.match(119);
         this.setState(1600);
         this.match(7);
         this.setState(1605);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 29:
            case 76:
            case 129:
               this.setState(1602);
               int _la = this._input.LA(1);
               if (_la != 29 && _la != 76 && _la != 129) {
                  this._errHandler.recoverInline(this);
               } else {
                  if (this._input.LA(1) == -1) {
                     this.matchedEOF = true;
                  }

                  this._errHandler.reportMatch(this);
                  this.consume();
               }

               this.setState(1603);
               this.match(9);
               this.setState(1604);
               this.error_message();
               break;
            case 85:
               this.setState(1601);
               this.match(85);
               break;
            default:
               throw new NoViableAltException(this);
         }

         this.setState(1607);
         this.match(8);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Indexed_columnContext indexed_column() throws RecognitionException {
      Indexed_columnContext _localctx = new Indexed_columnContext(this._ctx, this.getState());
      this.enterRule(_localctx, 148, 74);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1609);
         this.column_name();
         this.setState(1612);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 49) {
            this.setState(1610);
            this.match(49);
            this.setState(1611);
            this.collation_name();
         }

         this.setState(1615);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 38 || _la == 64) {
            this.setState(1614);
            _la = this._input.LA(1);
            if (_la != 38 && _la != 64) {
               this._errHandler.recoverInline(this);
            } else {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Table_constraintContext table_constraint() throws RecognitionException {
      Table_constraintContext _localctx = new Table_constraintContext(this._ctx, this.getState());
      this.enterRule(_localctx, 150, 75);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1619);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 53) {
            this.setState(1617);
            this.match(53);
            this.setState(1618);
            this.name();
         }

         this.setState(1657);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 48:
               this.setState(1638);
               this.match(48);
               this.setState(1639);
               this.match(7);
               this.setState(1640);
               this.expr(0);
               this.setState(1641);
               this.match(8);
               break;
            case 78:
               this.setState(1643);
               this.match(78);
               this.setState(1644);
               this.match(99);
               this.setState(1645);
               this.match(7);
               this.setState(1646);
               this.column_name();
               this.setState(1651);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1647);
                  this.match(9);
                  this.setState(1648);
                  this.column_name();
                  this.setState(1653);
                  this._errHandler.sync(this);
               }

               this.setState(1654);
               this.match(8);
               this.setState(1655);
               this.foreign_key_clause();
               break;
            case 117:
            case 142:
               this.setState(1624);
               this._errHandler.sync(this);
               switch (this._input.LA(1)) {
                  case 117:
                     this.setState(1621);
                     this.match(117);
                     this.setState(1622);
                     this.match(99);
                     break;
                  case 142:
                     this.setState(1623);
                     this.match(142);
                     break;
                  default:
                     throw new NoViableAltException(this);
               }

               this.setState(1626);
               this.match(7);
               this.setState(1627);
               this.indexed_column();
               this.setState(1632);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1628);
                  this.match(9);
                  this.setState(1629);
                  this.indexed_column();
                  this.setState(1634);
                  this._errHandler.sync(this);
               }

               this.setState(1635);
               this.match(8);
               this.setState(1636);
               this.conflict_clause();
               break;
            default:
               throw new NoViableAltException(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final With_clauseContext with_clause() throws RecognitionException {
      With_clauseContext _localctx = new With_clauseContext(this._ctx, this.getState());
      this.enterRule(_localctx, 152, 76);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1659);
         this.match(151);
         this.setState(1661);
         this._errHandler.sync(this);
         int _la;
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 207, this._ctx)) {
            case 1:
               this.setState(1660);
               this.match(120);
            default:
               this.setState(1663);
               this.cte_table_name();
               this.setState(1664);
               this.match(37);
               this.setState(1665);
               this.match(7);
               this.setState(1666);
               this.select_stmt();
               this.setState(1667);
               this.match(8);
               this.setState(1677);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
         }

         while(_la == 9) {
            this.setState(1668);
            this.match(9);
            this.setState(1669);
            this.cte_table_name();
            this.setState(1670);
            this.match(37);
            this.setState(1671);
            this.match(7);
            this.setState(1672);
            this.select_stmt();
            this.setState(1673);
            this.match(8);
            this.setState(1679);
            this._errHandler.sync(this);
            _la = this._input.LA(1);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Qualified_table_nameContext qualified_table_name() throws RecognitionException {
      Qualified_table_nameContext _localctx = new Qualified_table_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 154, 77);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1683);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 209, this._ctx)) {
            case 1:
               this.setState(1680);
               this.database_name();
               this.setState(1681);
               this.match(6);
            default:
               this.setState(1685);
               this.table_name();
               this.setState(1691);
               this._errHandler.sync(this);
               switch (this._input.LA(1)) {
                  case -1:
                  case 5:
                  case 34:
                  case 35:
                  case 39:
                  case 42:
                  case 51:
                  case 54:
                  case 63:
                  case 65:
                  case 67:
                  case 70:
                  case 75:
                  case 92:
                  case 102:
                  case 113:
                  case 116:
                  case 123:
                  case 124:
                  case 126:
                  case 129:
                  case 131:
                  case 132:
                  case 133:
                  case 143:
                  case 145:
                  case 146:
                  case 150:
                  case 151:
                  case 161:
                  default:
                     break;
                  case 89:
                     this.setState(1686);
                     this.match(89);
                     this.setState(1687);
                     this.match(44);
                     this.setState(1688);
                     this.index_name();
                     break;
                  case 106:
                     this.setState(1689);
                     this.match(106);
                     this.setState(1690);
                     this.match(89);
               }
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Ordering_termContext ordering_term() throws RecognitionException {
      Ordering_termContext _localctx = new Ordering_termContext(this._ctx, this.getState());
      this.enterRule(_localctx, 156, 78);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1693);
         this.expr(0);
         this.setState(1696);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 49) {
            this.setState(1694);
            this.match(49);
            this.setState(1695);
            this.collation_name();
         }

         this.setState(1699);
         this._errHandler.sync(this);
         _la = this._input.LA(1);
         if (_la == 38 || _la == 64) {
            this.setState(1698);
            _la = this._input.LA(1);
            if (_la != 38 && _la != 64) {
               this._errHandler.recoverInline(this);
            } else {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Pragma_valueContext pragma_value() throws RecognitionException {
      Pragma_valueContext _localctx = new Pragma_valueContext(this._ctx, this.getState());
      this.enterRule(_localctx, 158, 79);

      try {
         this.setState(1704);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 213, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1701);
               this.signed_number();
               break;
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1702);
               this.name();
               break;
            case 3:
               this.enterOuterAlt(_localctx, 3);
               this.setState(1703);
               this.match(156);
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Common_table_expressionContext common_table_expression() throws RecognitionException {
      Common_table_expressionContext _localctx = new Common_table_expressionContext(this._ctx, this.getState());
      this.enterRule(_localctx, 160, 80);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1706);
         this.table_name();
         this.setState(1718);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 7) {
            this.setState(1707);
            this.match(7);
            this.setState(1708);
            this.column_name();
            this.setState(1713);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(1709);
               this.match(9);
               this.setState(1710);
               this.column_name();
               this.setState(1715);
               this._errHandler.sync(this);
            }

            this.setState(1716);
            this.match(8);
         }

         this.setState(1720);
         this.match(37);
         this.setState(1721);
         this.match(7);
         this.setState(1722);
         this.select_stmt();
         this.setState(1723);
         this.match(8);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Result_columnContext result_column() throws RecognitionException {
      Result_columnContext _localctx = new Result_columnContext(this._ctx, this.getState());
      this.enterRule(_localctx, 162, 81);

      try {
         this.setState(1737);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 218, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1725);
               this.match(11);
               break;
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1726);
               this.table_name();
               this.setState(1727);
               this.match(6);
               this.setState(1728);
               this.match(11);
               break;
            case 3:
               this.enterOuterAlt(_localctx, 3);
               this.setState(1730);
               this.expr(0);
               this.setState(1735);
               this._errHandler.sync(this);
               int _la = this._input.LA(1);
               if (_la == 37 || _la == 153 || _la == 156) {
                  this.setState(1732);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
                  if (_la == 37) {
                     this.setState(1731);
                     this.match(37);
                  }

                  this.setState(1734);
                  this.column_alias();
               }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Table_or_subqueryContext table_or_subquery() throws RecognitionException {
      Table_or_subqueryContext _localctx = new Table_or_subqueryContext(this._ctx, this.getState());
      this.enterRule(_localctx, 164, 82);

      try {
         this.setState(1786);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 229, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1742);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 219, this._ctx)) {
                  case 1:
                     this.setState(1739);
                     this.database_name();
                     this.setState(1740);
                     this.match(6);
                  default:
                     this.setState(1744);
                     this.table_name();
                     this.setState(1749);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 221, this._ctx)) {
                        case 1:
                           this.setState(1746);
                           this._errHandler.sync(this);
                           switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 220, this._ctx)) {
                              case 1:
                                 this.setState(1745);
                                 this.match(37);
                              default:
                                 this.setState(1748);
                                 this.table_alias();
                           }
                        default:
                           this.setState(1756);
                           this._errHandler.sync(this);
                           switch (this._input.LA(1)) {
                              case -1:
                              case 0:
                              case 1:
                              case 2:
                              case 3:
                              case 4:
                              case 5:
                              case 6:
                              case 7:
                              case 8:
                              case 9:
                              case 10:
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
                              case 26:
                              case 27:
                              case 28:
                              case 29:
                              case 30:
                              case 31:
                              case 32:
                              case 33:
                              case 34:
                              case 35:
                              case 36:
                              case 37:
                              case 38:
                              case 39:
                              case 40:
                              case 41:
                              case 42:
                              case 43:
                              case 44:
                              case 45:
                              case 46:
                              case 47:
                              case 48:
                              case 49:
                              case 50:
                              case 51:
                              case 52:
                              case 53:
                              case 54:
                              case 55:
                              case 56:
                              case 57:
                              case 58:
                              case 59:
                              case 60:
                              case 61:
                              case 62:
                              case 63:
                              case 64:
                              case 65:
                              case 66:
                              case 67:
                              case 68:
                              case 69:
                              case 70:
                              case 71:
                              case 72:
                              case 73:
                              case 74:
                              case 75:
                              case 76:
                              case 77:
                              case 78:
                              case 79:
                              case 80:
                              case 81:
                              case 82:
                              case 83:
                              case 84:
                              case 85:
                              case 86:
                              case 87:
                              case 88:
                              case 90:
                              case 91:
                              case 92:
                              case 93:
                              case 94:
                              case 95:
                              case 96:
                              case 97:
                              case 98:
                              case 99:
                              case 100:
                              case 101:
                              case 102:
                              case 103:
                              case 104:
                              case 105:
                              case 107:
                              case 108:
                              case 109:
                              case 110:
                              case 111:
                              case 112:
                              case 113:
                              case 114:
                              case 115:
                              case 116:
                              case 117:
                              case 118:
                              case 119:
                              case 120:
                              case 121:
                              case 122:
                              case 123:
                              case 124:
                              case 125:
                              case 126:
                              case 127:
                              case 128:
                              case 129:
                              case 130:
                              case 131:
                              case 132:
                              case 133:
                              case 134:
                              case 135:
                              case 136:
                              case 137:
                              case 138:
                              case 139:
                              case 140:
                              case 141:
                              case 142:
                              case 143:
                              case 144:
                              case 145:
                              case 146:
                              case 147:
                              case 148:
                              case 149:
                              case 150:
                              case 151:
                              case 152:
                              case 153:
                              case 154:
                              case 155:
                              case 156:
                              case 157:
                              case 158:
                              case 159:
                              case 160:
                              case 161:
                              default:
                                 return _localctx;
                              case 89:
                                 this.setState(1751);
                                 this.match(89);
                                 this.setState(1752);
                                 this.match(44);
                                 this.setState(1753);
                                 this.index_name();
                                 return _localctx;
                              case 106:
                                 this.setState(1754);
                                 this.match(106);
                                 this.setState(1755);
                                 this.match(89);
                                 return _localctx;
                           }
                     }
               }
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1758);
               this.match(7);
               this.setState(1768);
               this._errHandler.sync(this);
               label131:
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 224, this._ctx)) {
                  case 1:
                     this.setState(1759);
                     this.table_or_subquery();
                     this.setState(1764);
                     this._errHandler.sync(this);
                     int _la = this._input.LA(1);

                     while(true) {
                        if (_la != 9) {
                           break label131;
                        }

                        this.setState(1760);
                        this.match(9);
                        this.setState(1761);
                        this.table_or_subquery();
                        this.setState(1766);
                        this._errHandler.sync(this);
                        _la = this._input.LA(1);
                     }
                  case 2:
                     this.setState(1767);
                     this.join_clause();
               }

               this.setState(1770);
               this.match(8);
               this.setState(1775);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 226, this._ctx)) {
                  case 1:
                     this.setState(1772);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 225, this._ctx)) {
                        case 1:
                           this.setState(1771);
                           this.match(37);
                        default:
                           this.setState(1774);
                           this.table_alias();
                           return _localctx;
                     }
                  default:
                     return _localctx;
               }
            case 3:
               this.enterOuterAlt(_localctx, 3);
               this.setState(1777);
               this.match(7);
               this.setState(1778);
               this.select_stmt();
               this.setState(1779);
               this.match(8);
               this.setState(1784);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 228, this._ctx)) {
                  case 1:
                     this.setState(1781);
                     this._errHandler.sync(this);
                     switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 227, this._ctx)) {
                        case 1:
                           this.setState(1780);
                           this.match(37);
                        default:
                           this.setState(1783);
                           this.table_alias();
                     }
               }
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Join_clauseContext join_clause() throws RecognitionException {
      Join_clauseContext _localctx = new Join_clauseContext(this._ctx, this.getState());
      this.enterRule(_localctx, 166, 83);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1788);
         this.table_or_subquery();
         this.setState(1795);
         this._errHandler.sync(this);

         for(int _la = this._input.LA(1); _la == 9 || _la == 55 || (_la - 91 & -64) == 0 && (1L << _la - 91 & 8833L) != 0L; _la = this._input.LA(1)) {
            this.setState(1789);
            this.join_operator();
            this.setState(1790);
            this.table_or_subquery();
            this.setState(1791);
            this.join_constraint();
            this.setState(1797);
            this._errHandler.sync(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Join_operatorContext join_operator() throws RecognitionException {
      Join_operatorContext _localctx = new Join_operatorContext(this._ctx, this.getState());
      this.enterRule(_localctx, 168, 84);

      try {
         this.setState(1811);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 9:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1798);
               this.match(9);
               break;
            case 55:
            case 91:
            case 98:
            case 100:
            case 104:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1800);
               this._errHandler.sync(this);
               int _la = this._input.LA(1);
               if (_la == 104) {
                  this.setState(1799);
                  this.match(104);
               }

               this.setState(1808);
               this._errHandler.sync(this);
               switch (this._input.LA(1)) {
                  case 55:
                     this.setState(1807);
                     this.match(55);
                     break;
                  case 91:
                     this.setState(1806);
                     this.match(91);
                  case 98:
                  default:
                     break;
                  case 100:
                     this.setState(1802);
                     this.match(100);
                     this.setState(1804);
                     this._errHandler.sync(this);
                     _la = this._input.LA(1);
                     if (_la == 114) {
                        this.setState(1803);
                        this.match(114);
                     }
               }

               this.setState(1810);
               this.match(98);
               break;
            default:
               throw new NoViableAltException(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Join_constraintContext join_constraint() throws RecognitionException {
      Join_constraintContext _localctx = new Join_constraintContext(this._ctx, this.getState());
      this.enterRule(_localctx, 170, 85);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1827);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case -1:
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
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
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            default:
               break;
            case 111:
               this.setState(1813);
               this.match(111);
               this.setState(1814);
               this.expr(0);
               break;
            case 144:
               this.setState(1815);
               this.match(144);
               this.setState(1816);
               this.match(7);
               this.setState(1817);
               this.column_name();
               this.setState(1822);
               this._errHandler.sync(this);

               for(int _la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1818);
                  this.match(9);
                  this.setState(1819);
                  this.column_name();
                  this.setState(1824);
                  this._errHandler.sync(this);
               }

               this.setState(1825);
               this.match(8);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Select_coreContext select_core() throws RecognitionException {
      Select_coreContext _localctx = new Select_coreContext(this._ctx, this.getState());
      this.enterRule(_localctx, 172, 86);

      try {
         this.setState(1903);
         this._errHandler.sync(this);
         int _la;
         switch (this._input.LA(1)) {
            case 132:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1829);
               this.match(132);
               this.setState(1831);
               this._errHandler.sync(this);
               switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 237, this._ctx)) {
                  case 1:
                     this.setState(1830);
                     _la = this._input.LA(1);
                     if (_la != 33 && _la != 66) {
                        this._errHandler.recoverInline(this);
                     } else {
                        if (this._input.LA(1) == -1) {
                           this.matchedEOF = true;
                        }

                        this._errHandler.reportMatch(this);
                        this.consume();
                     }
               }

               this.setState(1833);
               this.result_column();
               this.setState(1838);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1834);
                  this.match(9);
                  this.setState(1835);
                  this.result_column();
                  this.setState(1840);
                  this._errHandler.sync(this);
               }

               this.setState(1853);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 79) {
                  this.setState(1841);
                  this.match(79);
                  this.setState(1851);
                  this._errHandler.sync(this);
                  label178:
                  switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 240, this._ctx)) {
                     case 1:
                        this.setState(1842);
                        this.table_or_subquery();
                        this.setState(1847);
                        this._errHandler.sync(this);
                        _la = this._input.LA(1);

                        while(true) {
                           if (_la != 9) {
                              break label178;
                           }

                           this.setState(1843);
                           this.match(9);
                           this.setState(1844);
                           this.table_or_subquery();
                           this.setState(1849);
                           this._errHandler.sync(this);
                           _la = this._input.LA(1);
                        }
                     case 2:
                        this.setState(1850);
                        this.join_clause();
                  }
               }

               this.setState(1857);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 150) {
                  this.setState(1855);
                  this.match(150);
                  this.setState(1856);
                  this.expr(0);
               }

               this.setState(1873);
               this._errHandler.sync(this);
               _la = this._input.LA(1);
               if (_la == 82) {
                  this.setState(1859);
                  this.match(82);
                  this.setState(1860);
                  this.match(44);
                  this.setState(1861);
                  this.expr(0);
                  this.setState(1866);
                  this._errHandler.sync(this);

                  for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                     this.setState(1862);
                     this.match(9);
                     this.setState(1863);
                     this.expr(0);
                     this.setState(1868);
                     this._errHandler.sync(this);
                  }

                  this.setState(1871);
                  this._errHandler.sync(this);
                  _la = this._input.LA(1);
                  if (_la == 83) {
                     this.setState(1869);
                     this.match(83);
                     this.setState(1870);
                     this.expr(0);
                  }
               }
               break;
            case 146:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1875);
               this.match(146);
               this.setState(1876);
               this.match(7);
               this.setState(1877);
               this.expr(0);
               this.setState(1882);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1878);
                  this.match(9);
                  this.setState(1879);
                  this.expr(0);
                  this.setState(1884);
                  this._errHandler.sync(this);
               }

               this.setState(1885);
               this.match(8);
               this.setState(1900);
               this._errHandler.sync(this);

               for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                  this.setState(1886);
                  this.match(9);
                  this.setState(1887);
                  this.match(7);
                  this.setState(1888);
                  this.expr(0);
                  this.setState(1893);
                  this._errHandler.sync(this);

                  for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
                     this.setState(1889);
                     this.match(9);
                     this.setState(1890);
                     this.expr(0);
                     this.setState(1895);
                     this._errHandler.sync(this);
                  }

                  this.setState(1896);
                  this.match(8);
                  this.setState(1902);
                  this._errHandler.sync(this);
               }

               return _localctx;
            default:
               throw new NoViableAltException(this);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Compound_operatorContext compound_operator() throws RecognitionException {
      Compound_operatorContext _localctx = new Compound_operatorContext(this._ctx, this.getState());
      this.enterRule(_localctx, 174, 87);

      try {
         this.setState(1910);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 250, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1905);
               this.match(141);
               break;
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1906);
               this.match(141);
               this.setState(1907);
               this.match(33);
               break;
            case 3:
               this.enterOuterAlt(_localctx, 3);
               this.setState(1908);
               this.match(94);
               break;
            case 4:
               this.enterOuterAlt(_localctx, 4);
               this.setState(1909);
               this.match(72);
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Cte_table_nameContext cte_table_name() throws RecognitionException {
      Cte_table_nameContext _localctx = new Cte_table_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 176, 88);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1912);
         this.table_name();
         this.setState(1924);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 7) {
            this.setState(1913);
            this.match(7);
            this.setState(1914);
            this.column_name();
            this.setState(1919);
            this._errHandler.sync(this);

            for(_la = this._input.LA(1); _la == 9; _la = this._input.LA(1)) {
               this.setState(1915);
               this.match(9);
               this.setState(1916);
               this.column_name();
               this.setState(1921);
               this._errHandler.sync(this);
            }

            this.setState(1922);
            this.match(8);
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Signed_numberContext signed_number() throws RecognitionException {
      Signed_numberContext _localctx = new Signed_numberContext(this._ctx, this.getState());
      this.enterRule(_localctx, 178, 89);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1927);
         this._errHandler.sync(this);
         int _la = this._input.LA(1);
         if (_la == 12 || _la == 13) {
            this.setState(1926);
            _la = this._input.LA(1);
            if (_la != 12 && _la != 13) {
               this._errHandler.recoverInline(this);
            } else {
               if (this._input.LA(1) == -1) {
                  this.matchedEOF = true;
               }

               this._errHandler.reportMatch(this);
               this.consume();
            }
         }

         this.setState(1929);
         this.match(154);
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Literal_valueContext literal_value() throws RecognitionException {
      Literal_valueContext _localctx = new Literal_valueContext(this._ctx, this.getState());
      this.enterRule(_localctx, 180, 90);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1931);
         int _la = this._input.LA(1);
         if (((_la & -64) != 0 || (1L << _la & 504403158265495552L) == 0L) && ((_la - 108 & -64) != 0 || (1L << _la - 108 & 914793674309633L) == 0L)) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Unary_operatorContext unary_operator() throws RecognitionException {
      Unary_operatorContext _localctx = new Unary_operatorContext(this._ctx, this.getState());
      this.enterRule(_localctx, 182, 91);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1933);
         int _la = this._input.LA(1);
         if (((_la & -64) != 0 || (1L << _la & 28672L) == 0L) && _la != 106) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Error_messageContext error_message() throws RecognitionException {
      Error_messageContext _localctx = new Error_messageContext(this._ctx, this.getState());
      this.enterRule(_localctx, 184, 92);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1935);
         this.match(156);
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Module_argumentContext module_argument() throws RecognitionException {
      Module_argumentContext _localctx = new Module_argumentContext(this._ctx, this.getState());
      this.enterRule(_localctx, 186, 93);

      try {
         this.setState(1939);
         this._errHandler.sync(this);
         switch (((ParserATNSimulator)this.getInterpreter()).adaptivePredict(this._input, 254, this._ctx)) {
            case 1:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1937);
               this.expr(0);
               break;
            case 2:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1938);
               this.column_def();
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Column_aliasContext column_alias() throws RecognitionException {
      Column_aliasContext _localctx = new Column_aliasContext(this._ctx, this.getState());
      this.enterRule(_localctx, 188, 94);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1941);
         int _la = this._input.LA(1);
         if (_la != 153 && _la != 156) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final KeywordContext keyword() throws RecognitionException {
      KeywordContext _localctx = new KeywordContext(this._ctx, this.getState());
      this.enterRule(_localctx, 190, 95);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1943);
         int _la = this._input.LA(1);
         if (((_la - 29 & -64) != 0 || (1L << _la - 29 & -1L) == 0L) && ((_la - 93 & -64) != 0 || (1L << _la - 93 & 1152921504606846975L) == 0L)) {
            this._errHandler.recoverInline(this);
         } else {
            if (this._input.LA(1) == -1) {
               this.matchedEOF = true;
            }

            this._errHandler.reportMatch(this);
            this.consume();
         }
      } catch (RecognitionException var7) {
         _localctx.exception = var7;
         this._errHandler.reportError(this, var7);
         this._errHandler.recover(this, var7);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final NameContext name() throws RecognitionException {
      NameContext _localctx = new NameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 192, 96);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1945);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Function_nameContext function_name() throws RecognitionException {
      Function_nameContext _localctx = new Function_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 194, 97);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1947);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Database_nameContext database_name() throws RecognitionException {
      Database_nameContext _localctx = new Database_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 196, 98);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1949);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Table_nameContext table_name() throws RecognitionException {
      Table_nameContext _localctx = new Table_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 198, 99);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1951);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Table_or_index_nameContext table_or_index_name() throws RecognitionException {
      Table_or_index_nameContext _localctx = new Table_or_index_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 200, 100);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1953);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final New_table_nameContext new_table_name() throws RecognitionException {
      New_table_nameContext _localctx = new New_table_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 202, 101);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1955);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Column_nameContext column_name() throws RecognitionException {
      Column_nameContext _localctx = new Column_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 204, 102);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1957);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Collation_nameContext collation_name() throws RecognitionException {
      Collation_nameContext _localctx = new Collation_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 206, 103);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1959);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Foreign_tableContext foreign_table() throws RecognitionException {
      Foreign_tableContext _localctx = new Foreign_tableContext(this._ctx, this.getState());
      this.enterRule(_localctx, 208, 104);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1961);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Index_nameContext index_name() throws RecognitionException {
      Index_nameContext _localctx = new Index_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 210, 105);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1963);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Trigger_nameContext trigger_name() throws RecognitionException {
      Trigger_nameContext _localctx = new Trigger_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 212, 106);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1965);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final View_nameContext view_name() throws RecognitionException {
      View_nameContext _localctx = new View_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 214, 107);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1967);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Module_nameContext module_name() throws RecognitionException {
      Module_nameContext _localctx = new Module_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 216, 108);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1969);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Pragma_nameContext pragma_name() throws RecognitionException {
      Pragma_nameContext _localctx = new Pragma_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 218, 109);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1971);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Savepoint_nameContext savepoint_name() throws RecognitionException {
      Savepoint_nameContext _localctx = new Savepoint_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 220, 110);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1973);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Table_aliasContext table_alias() throws RecognitionException {
      Table_aliasContext _localctx = new Table_aliasContext(this._ctx, this.getState());
      this.enterRule(_localctx, 222, 111);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1975);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Transaction_nameContext transaction_name() throws RecognitionException {
      Transaction_nameContext _localctx = new Transaction_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 224, 112);

      try {
         this.enterOuterAlt(_localctx, 1);
         this.setState(1977);
         this.any_name();
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public final Any_nameContext any_name() throws RecognitionException {
      Any_nameContext _localctx = new Any_nameContext(this._ctx, this.getState());
      this.enterRule(_localctx, 226, 113);

      try {
         this.setState(1986);
         this._errHandler.sync(this);
         switch (this._input.LA(1)) {
            case 7:
               this.enterOuterAlt(_localctx, 4);
               this.setState(1982);
               this.match(7);
               this.setState(1983);
               this.any_name();
               this.setState(1984);
               this.match(8);
               break;
            case 8:
            case 9:
            case 10:
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
            case 26:
            case 27:
            case 28:
            case 154:
            case 155:
            default:
               throw new NoViableAltException(this);
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
               this.enterOuterAlt(_localctx, 2);
               this.setState(1980);
               this.keyword();
               break;
            case 153:
               this.enterOuterAlt(_localctx, 1);
               this.setState(1979);
               this.match(153);
               break;
            case 156:
               this.enterOuterAlt(_localctx, 3);
               this.setState(1981);
               this.match(156);
         }
      } catch (RecognitionException var6) {
         _localctx.exception = var6;
         this._errHandler.reportError(this, var6);
         this._errHandler.recover(this, var6);
      } finally {
         this.exitRule();
      }

      return _localctx;
   }

   public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
      switch (ruleIndex) {
         case 71:
            return this.expr_sempred((ExprContext)_localctx, predIndex);
         default:
            return true;
      }
   }

   private boolean expr_sempred(ExprContext _localctx, int predIndex) {
      switch (predIndex) {
         case 0:
            return this.precpred(this._ctx, 20);
         case 1:
            return this.precpred(this._ctx, 19);
         case 2:
            return this.precpred(this._ctx, 18);
         case 3:
            return this.precpred(this._ctx, 17);
         case 4:
            return this.precpred(this._ctx, 16);
         case 5:
            return this.precpred(this._ctx, 15);
         case 6:
            return this.precpred(this._ctx, 14);
         case 7:
            return this.precpred(this._ctx, 13);
         case 8:
            return this.precpred(this._ctx, 6);
         case 9:
            return this.precpred(this._ctx, 5);
         case 10:
            return this.precpred(this._ctx, 9);
         case 11:
            return this.precpred(this._ctx, 8);
         case 12:
            return this.precpred(this._ctx, 7);
         case 13:
            return this.precpred(this._ctx, 4);
         default:
            return true;
      }
   }

   static {
      RuntimeMetaData.checkVersion("4.7", "4.7.2");
      _sharedContextCache = new PredictionContextCache();
      ruleNames = new String[]{"start", "indexedCollection", "whereClause", "orderByClause", "query", "logicalQuery", "andQuery", "orQuery", "notQuery", "simpleQuery", "equalQuery", "notEqualQuery", "lessThanOrEqualToQuery", "lessThanQuery", "greaterThanOrEqualToQuery", "greaterThanQuery", "betweenQuery", "notBetweenQuery", "inQuery", "notInQuery", "startsWithQuery", "endsWithQuery", "containsQuery", "hasQuery", "notHasQuery", "attributeName", "queryParameterTrailingPercent", "queryParameterLeadingPercent", "queryParameterLeadingAndTrailingPercent", "queryParameter", "attributeOrder", "direction", "parse", "error", "sql_stmt_list", "sql_stmt", "alter_table_stmt", "analyze_stmt", "attach_stmt", "begin_stmt", "commit_stmt", "compound_select_stmt", "create_index_stmt", "create_table_stmt", "create_trigger_stmt", "create_view_stmt", "create_virtual_table_stmt", "delete_stmt", "delete_stmt_limited", "detach_stmt", "drop_index_stmt", "drop_table_stmt", "drop_trigger_stmt", "drop_view_stmt", "factored_select_stmt", "insert_stmt", "pragma_stmt", "reindex_stmt", "release_stmt", "rollback_stmt", "savepoint_stmt", "simple_select_stmt", "select_stmt", "select_or_values", "update_stmt", "update_stmt_limited", "vacuum_stmt", "column_def", "type_name", "column_constraint", "conflict_clause", "expr", "foreign_key_clause", "raise_function", "indexed_column", "table_constraint", "with_clause", "qualified_table_name", "ordering_term", "pragma_value", "common_table_expression", "result_column", "table_or_subquery", "join_clause", "join_operator", "join_constraint", "select_core", "compound_operator", "cte_table_name", "signed_number", "literal_value", "unary_operator", "error_message", "module_argument", "column_alias", "keyword", "name", "function_name", "database_name", "table_name", "table_or_index_name", "new_table_name", "column_name", "collation_name", "foreign_table", "index_name", "trigger_name", "view_name", "module_name", "pragma_name", "savepoint_name", "table_alias", "transaction_name", "any_name"};
      _LITERAL_NAMES = new String[]{null, null, null, null, null, "';'", "'.'", "'('", "')'", "','", "'='", "'*'", "'+'", "'-'", "'~'", "'||'", "'/'", "'%'", "'<<'", "'>>'", "'&'", "'|'", "'<'", "'<='", "'>'", "'>='", "'=='", "'!='", "'<>'"};
      _SYMBOLIC_NAMES = new String[]{null, "STRING_LITERAL_WITH_TRAILING_PERCENT", "STRING_LITERAL_WITH_LEADING_PERCENT", "STRING_LITERAL_WITH_LEADING_AND_TRAILING_PERCENT", "BOOLEAN_LITERAL", "SCOL", "DOT", "OPEN_PAR", "CLOSE_PAR", "COMMA", "ASSIGN", "STAR", "PLUS", "MINUS", "TILDE", "PIPE2", "DIV", "MOD", "LT2", "GT2", "AMP", "PIPE", "LT", "LT_EQ", "GT", "GT_EQ", "EQ", "NOT_EQ1", "NOT_EQ2", "K_ABORT", "K_ACTION", "K_ADD", "K_AFTER", "K_ALL", "K_ALTER", "K_ANALYZE", "K_AND", "K_AS", "K_ASC", "K_ATTACH", "K_AUTOINCREMENT", "K_BEFORE", "K_BEGIN", "K_BETWEEN", "K_BY", "K_CASCADE", "K_CASE", "K_CAST", "K_CHECK", "K_COLLATE", "K_COLUMN", "K_COMMIT", "K_CONFLICT", "K_CONSTRAINT", "K_CREATE", "K_CROSS", "K_CURRENT_DATE", "K_CURRENT_TIME", "K_CURRENT_TIMESTAMP", "K_DATABASE", "K_DEFAULT", "K_DEFERRABLE", "K_DEFERRED", "K_DELETE", "K_DESC", "K_DETACH", "K_DISTINCT", "K_DROP", "K_EACH", "K_ELSE", "K_END", "K_ESCAPE", "K_EXCEPT", "K_EXCLUSIVE", "K_EXISTS", "K_EXPLAIN", "K_FAIL", "K_FOR", "K_FOREIGN", "K_FROM", "K_FULL", "K_GLOB", "K_GROUP", "K_HAVING", "K_IF", "K_IGNORE", "K_IMMEDIATE", "K_IN", "K_INDEX", "K_INDEXED", "K_INITIALLY", "K_INNER", "K_INSERT", "K_INSTEAD", "K_INTERSECT", "K_INTO", "K_IS", "K_ISNULL", "K_JOIN", "K_KEY", "K_LEFT", "K_LIKE", "K_LIMIT", "K_MATCH", "K_NATURAL", "K_NO", "K_NOT", "K_NOTNULL", "K_NULL", "K_OF", "K_OFFSET", "K_ON", "K_OR", "K_ORDER", "K_OUTER", "K_PLAN", "K_PRAGMA", "K_PRIMARY", "K_QUERY", "K_RAISE", "K_RECURSIVE", "K_REFERENCES", "K_REGEXP", "K_REINDEX", "K_RELEASE", "K_RENAME", "K_REPLACE", "K_RESTRICT", "K_RIGHT", "K_ROLLBACK", "K_ROW", "K_SAVEPOINT", "K_SELECT", "K_SET", "K_TABLE", "K_TEMP", "K_TEMPORARY", "K_THEN", "K_TO", "K_TRANSACTION", "K_TRIGGER", "K_UNION", "K_UNIQUE", "K_UPDATE", "K_USING", "K_VACUUM", "K_VALUES", "K_VIEW", "K_VIRTUAL", "K_WHEN", "K_WHERE", "K_WITH", "K_WITHOUT", "IDENTIFIER", "NUMERIC_LITERAL", "BIND_PARAMETER", "STRING_LITERAL", "BLOB_LITERAL", "SINGLE_LINE_COMMENT", "MULTILINE_COMMENT", "SPACES", "UNEXPECTED_CHAR"};
      VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
      tokenNames = new String[_SYMBOLIC_NAMES.length];

      int i;
      for(i = 0; i < tokenNames.length; ++i) {
         tokenNames[i] = VOCABULARY.getLiteralName(i);
         if (tokenNames[i] == null) {
            tokenNames[i] = VOCABULARY.getSymbolicName(i);
         }

         if (tokenNames[i] == null) {
            tokenNames[i] = "<INVALID>";
         }
      }

      _ATN = (new ATNDeserializer()).deserialize("\u0003悋Ꜫ脳맭䅼㯧瞆奤\u0003£߇\u0004\u0002\t\u0002\u0004\u0003\t\u0003\u0004\u0004\t\u0004\u0004\u0005\t\u0005\u0004\u0006\t\u0006\u0004\u0007\t\u0007\u0004\b\t\b\u0004\t\t\t\u0004\n\t\n\u0004\u000b\t\u000b\u0004\f\t\f\u0004\r\t\r\u0004\u000e\t\u000e\u0004\u000f\t\u000f\u0004\u0010\t\u0010\u0004\u0011\t\u0011\u0004\u0012\t\u0012\u0004\u0013\t\u0013\u0004\u0014\t\u0014\u0004\u0015\t\u0015\u0004\u0016\t\u0016\u0004\u0017\t\u0017\u0004\u0018\t\u0018\u0004\u0019\t\u0019\u0004\u001a\t\u001a\u0004\u001b\t\u001b\u0004\u001c\t\u001c\u0004\u001d\t\u001d\u0004\u001e\t\u001e\u0004\u001f\t\u001f\u0004 \t \u0004!\t!\u0004\"\t\"\u0004#\t#\u0004$\t$\u0004%\t%\u0004&\t&\u0004'\t'\u0004(\t(\u0004)\t)\u0004*\t*\u0004+\t+\u0004,\t,\u0004-\t-\u0004.\t.\u0004/\t/\u00040\t0\u00041\t1\u00042\t2\u00043\t3\u00044\t4\u00045\t5\u00046\t6\u00047\t7\u00048\t8\u00049\t9\u0004:\t:\u0004;\t;\u0004<\t<\u0004=\t=\u0004>\t>\u0004?\t?\u0004@\t@\u0004A\tA\u0004B\tB\u0004C\tC\u0004D\tD\u0004E\tE\u0004F\tF\u0004G\tG\u0004H\tH\u0004I\tI\u0004J\tJ\u0004K\tK\u0004L\tL\u0004M\tM\u0004N\tN\u0004O\tO\u0004P\tP\u0004Q\tQ\u0004R\tR\u0004S\tS\u0004T\tT\u0004U\tU\u0004V\tV\u0004W\tW\u0004X\tX\u0004Y\tY\u0004Z\tZ\u0004[\t[\u0004\\\t\\\u0004]\t]\u0004^\t^\u0004_\t_\u0004`\t`\u0004a\ta\u0004b\tb\u0004c\tc\u0004d\td\u0004e\te\u0004f\tf\u0004g\tg\u0004h\th\u0004i\ti\u0004j\tj\u0004k\tk\u0004l\tl\u0004m\tm\u0004n\tn\u0004o\to\u0004p\tp\u0004q\tq\u0004r\tr\u0004s\ts\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0002\u0005\u0002ì\n\u0002\u0003\u0002\u0005\u0002ï\n\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005ý\n\u0005\f\u0005\u000e\u0005Ā\u000b\u0005\u0003\u0006\u0003\u0006\u0005\u0006Ą\n\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0005\u0007ĉ\n\u0007\u0003\b\u0003\b\u0003\b\u0003\b\u0003\b\u0003\b\u0007\bđ\n\b\f\b\u000e\bĔ\u000b\b\u0003\b\u0003\b\u0003\t\u0003\t\u0003\t\u0003\t\u0003\t\u0003\t\u0007\tĞ\n\t\f\t\u000e\tġ\u000b\t\u0003\t\u0003\t\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0003\n\u0005\nĬ\n\n\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0005\u000bŁ\n\u000b\u0003\f\u0003\f\u0003\f\u0003\f\u0003\r\u0003\r\u0003\r\u0003\r\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0007\u0014Ů\n\u0014\f\u0014\u000e\u0014ű\u000b\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0007\u0015ż\n\u0015\f\u0015\u000e\u0015ſ\u000b\u0015\u0003\u0015\u0003\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0017\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001d\u0003\u001d\u0003\u001e\u0003\u001e\u0003\u001f\u0003\u001f\u0003 \u0003 \u0005 Ƥ\n \u0003!\u0003!\u0003\"\u0003\"\u0007\"ƪ\n\"\f\"\u000e\"ƭ\u000b\"\u0003\"\u0003\"\u0003#\u0003#\u0003#\u0003$\u0007$Ƶ\n$\f$\u000e$Ƹ\u000b$\u0003$\u0003$\u0006$Ƽ\n$\r$\u000e$ƽ\u0003$\u0007$ǁ\n$\f$\u000e$Ǆ\u000b$\u0003$\u0007$Ǉ\n$\f$\u000e$Ǌ\u000b$\u0003%\u0003%\u0003%\u0005%Ǐ\n%\u0005%Ǒ\n%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0003%\u0005%Ǳ\n%\u0003&\u0003&\u0003&\u0003&\u0003&\u0005&Ǹ\n&\u0003&\u0003&\u0003&\u0003&\u0003&\u0003&\u0005&Ȁ\n&\u0003&\u0005&ȃ\n&\u0003'\u0003'\u0003'\u0003'\u0003'\u0003'\u0003'\u0005'Ȍ\n'\u0003(\u0003(\u0005(Ȑ\n(\u0003(\u0003(\u0003(\u0003(\u0003)\u0003)\u0005)Ș\n)\u0003)\u0003)\u0005)Ȝ\n)\u0005)Ȟ\n)\u0003*\u0003*\u0003*\u0005*ȣ\n*\u0005*ȥ\n*\u0003+\u0003+\u0005+ȩ\n+\u0003+\u0003+\u0003+\u0007+Ȯ\n+\f+\u000e+ȱ\u000b+\u0005+ȳ\n+\u0003+\u0003+\u0003+\u0005+ȸ\n+\u0003+\u0003+\u0005+ȼ\n+\u0003+\u0006+ȿ\n+\r+\u000e+ɀ\u0003+\u0003+\u0003+\u0003+\u0003+\u0007+Ɉ\n+\f+\u000e+ɋ\u000b+\u0005+ɍ\n+\u0003+\u0003+\u0003+\u0003+\u0005+ɓ\n+\u0005+ɕ\n+\u0003,\u0003,\u0005,ə\n,\u0003,\u0003,\u0003,\u0003,\u0005,ɟ\n,\u0003,\u0003,\u0003,\u0005,ɤ\n,\u0003,\u0003,\u0003,\u0003,\u0003,\u0003,\u0003,\u0007,ɭ\n,\f,\u000e,ɰ\u000b,\u0003,\u0003,\u0003,\u0005,ɵ\n,\u0003-\u0003-\u0005-ɹ\n-\u0003-\u0003-\u0003-\u0003-\u0005-ɿ\n-\u0003-\u0003-\u0003-\u0005-ʄ\n-\u0003-\u0003-\u0003-\u0003-\u0003-\u0007-ʋ\n-\f-\u000e-ʎ\u000b-\u0003-\u0003-\u0007-ʒ\n-\f-\u000e-ʕ\u000b-\u0003-\u0003-\u0003-\u0005-ʚ\n-\u0003-\u0003-\u0005-ʞ\n-\u0003.\u0003.\u0005.ʢ\n.\u0003.\u0003.\u0003.\u0003.\u0005.ʨ\n.\u0003.\u0003.\u0003.\u0005.ʭ\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0005.ʴ\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0003.\u0007.ʽ\n.\f.\u000e.ˀ\u000b.\u0005.˂\n.\u0005.˄\n.\u0003.\u0003.\u0003.\u0003.\u0005.ˊ\n.\u0003.\u0003.\u0003.\u0003.\u0005.ː\n.\u0003.\u0003.\u0005.˔\n.\u0003.\u0003.\u0003.\u0003.\u0003.\u0005.˛\n.\u0003.\u0003.\u0006.˟\n.\r.\u000e.ˠ\u0003.\u0003.\u0003/\u0003/\u0005/˧\n/\u0003/\u0003/\u0003/\u0003/\u0005/˭\n/\u0003/\u0003/\u0003/\u0005/˲\n/\u0003/\u0003/\u0003/\u0003/\u00030\u00030\u00030\u00030\u00030\u00030\u00050˾\n0\u00030\u00030\u00030\u00050̃\n0\u00030\u00030\u00030\u00030\u00030\u00030\u00030\u00070̌\n0\f0\u000e0̏\u000b0\u00030\u00030\u00050̓\n0\u00031\u00051̖\n1\u00031\u00031\u00031\u00031\u00031\u00051̝\n1\u00032\u00052̠\n2\u00032\u00032\u00032\u00032\u00032\u00052̧\n2\u00032\u00032\u00032\u00032\u00032\u00072̮\n2\f2\u000e2̱\u000b2\u00052̳\n2\u00032\u00032\u00032\u00032\u00052̹\n2\u00052̻\n2\u00033\u00033\u00053̿\n3\u00033\u00033\u00034\u00034\u00034\u00034\u00054͇\n4\u00034\u00034\u00034\u00054͌\n4\u00034\u00034\u00035\u00035\u00035\u00035\u00055͔\n5\u00035\u00035\u00035\u00055͙\n5\u00035\u00035\u00036\u00036\u00036\u00036\u00056͡\n6\u00036\u00036\u00036\u00056ͦ\n6\u00036\u00036\u00037\u00037\u00037\u00037\u00057ͮ\n7\u00037\u00037\u00037\u00057ͳ\n7\u00037\u00037\u00038\u00038\u00058\u0379\n8\u00038\u00038\u00038\u00078;\n8\f8\u000e8\u0381\u000b8\u00058\u0383\n8\u00038\u00038\u00038\u00038\u00078Ή\n8\f8\u000e8Ό\u000b8\u00038\u00038\u00038\u00038\u00038\u00078Γ\n8\f8\u000e8Ζ\u000b8\u00058Θ\n8\u00038\u00038\u00038\u00038\u00058Ξ\n8\u00058Π\n8\u00039\u00059Σ\n9\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00039\u00059ζ\n9\u00039\u00039\u00039\u00039\u00059μ\n9\u00039\u00039\u00039\u00039\u00039\u00079σ\n9\f9\u000e9φ\u000b9\u00039\u00039\u00059ϊ\n9\u00039\u00039\u00039\u00039\u00039\u00079ϑ\n9\f9\u000e9ϔ\u000b9\u00039\u00039\u00039\u00039\u00039\u00039\u00079Ϝ\n9\f9\u000e9ϟ\u000b9\u00039\u00039\u00079ϣ\n9\f9\u000e9Ϧ\u000b9\u00039\u00039\u00039\u00059ϫ\n9\u0003:\u0003:\u0003:\u0003:\u0005:ϱ\n:\u0003:\u0003:\u0003:\u0003:\u0003:\u0003:\u0003:\u0005:Ϻ\n:\u0003;\u0003;\u0003;\u0003;\u0003;\u0005;Ё\n;\u0003;\u0003;\u0005;Ѕ\n;\u0005;Ї\n;\u0003<\u0003<\u0005<Ћ\n<\u0003<\u0003<\u0003=\u0003=\u0003=\u0005=В\n=\u0005=Д\n=\u0003=\u0003=\u0005=И\n=\u0003=\u0005=Л\n=\u0003>\u0003>\u0003>\u0003?\u0003?\u0005?Т\n?\u0003?\u0003?\u0003?\u0007?Ч\n?\f?\u000e?Ъ\u000b?\u0005?Ь\n?\u0003?\u0003?\u0003?\u0003?\u0003?\u0003?\u0007?д\n?\f?\u000e?з\u000b?\u0005?й\n?\u0003?\u0003?\u0003?\u0003?\u0005?п\n?\u0005?с\n?\u0003@\u0003@\u0005@х\n@\u0003@\u0003@\u0003@\u0007@ъ\n@\f@\u000e@э\u000b@\u0005@я\n@\u0003@\u0003@\u0003@\u0003@\u0007@ѕ\n@\f@\u000e@ј\u000b@\u0003@\u0003@\u0003@\u0003@\u0003@\u0007@џ\n@\f@\u000e@Ѣ\u000b@\u0005@Ѥ\n@\u0003@\u0003@\u0003@\u0003@\u0005@Ѫ\n@\u0005@Ѭ\n@\u0003A\u0003A\u0005AѰ\nA\u0003A\u0003A\u0003A\u0007Aѵ\nA\fA\u000eAѸ\u000bA\u0003A\u0003A\u0003A\u0003A\u0007AѾ\nA\fA\u000eAҁ\u000bA\u0003A\u0005A҄\nA\u0005A҆\nA\u0003A\u0003A\u0005AҊ\nA\u0003A\u0003A\u0003A\u0003A\u0003A\u0007Aґ\nA\fA\u000eAҔ\u000bA\u0003A\u0003A\u0005AҘ\nA\u0005AҚ\nA\u0003A\u0003A\u0003A\u0003A\u0003A\u0007Aҡ\nA\fA\u000eAҤ\u000bA\u0003A\u0003A\u0003A\u0003A\u0003A\u0003A\u0007AҬ\nA\fA\u000eAү\u000bA\u0003A\u0003A\u0007Aҳ\nA\fA\u000eAҶ\u000bA\u0005AҸ\nA\u0003B\u0005Bһ\nB\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0005Bӈ\nB\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0003B\u0007BӔ\nB\fB\u000eBӗ\u000bB\u0003B\u0003B\u0005Bӛ\nB\u0003C\u0005CӞ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0005Cӫ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0003C\u0007Cӷ\nC\fC\u000eCӺ\u000bC\u0003C\u0003C\u0005CӾ\nC\u0003C\u0003C\u0003C\u0003C\u0003C\u0007Cԅ\nC\fC\u000eCԈ\u000bC\u0005CԊ\nC\u0003C\u0003C\u0003C\u0003C\u0005CԐ\nC\u0005CԒ\nC\u0003D\u0003D\u0003E\u0003E\u0005EԘ\nE\u0003E\u0007Eԛ\nE\fE\u000eEԞ\u000bE\u0003F\u0006Fԡ\nF\rF\u000eFԢ\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0003F\u0005Fԯ\nF\u0003G\u0003G\u0005GԳ\nG\u0003G\u0003G\u0003G\u0005GԸ\nG\u0003G\u0003G\u0005GԼ\nG\u0003G\u0005GԿ\nG\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0003G\u0005GՑ\nG\u0003G\u0003G\u0003G\u0005GՖ\nG\u0003H\u0003H\u0003H\u0005H՛\nH\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iգ\nI\u0003I\u0003I\u0003I\u0005Iը\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iձ\nI\u0003I\u0003I\u0003I\u0007Iն\nI\fI\u000eIչ\u000bI\u0003I\u0005Iռ\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005I\u058c\nI\u0003I\u0005I֏\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005I֗\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0006I֞\nI\rI\u000eI֟\u0003I\u0003I\u0005I֤\nI\u0003I\u0003I\u0003I\u0005I֩\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iׇ\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iד\nI\u0003I\u0003I\u0003I\u0005Iט\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iפ\nI\u0003I\u0003I\u0003I\u0003I\u0005Iת\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0005Iױ\nI\u0003I\u0003I\u0005I\u05f5\nI\u0003I\u0003I\u0003I\u0003I\u0003I\u0003I\u0007I\u05fd\nI\fI\u000eI\u0600\u000bI\u0005I\u0602\nI\u0003I\u0003I\u0003I\u0003I\u0005I؈\nI\u0003I\u0005I؋\nI\u0007I؍\nI\fI\u000eIؐ\u000bI\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0007Jؘ\nJ\fJ\u000eJ؛\u000bJ\u0003J\u0003J\u0005J؟\nJ\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0003J\u0005Jث\nJ\u0003J\u0003J\u0005Jد\nJ\u0007Jر\nJ\fJ\u000eJش\u000bJ\u0003J\u0005Jط\nJ\u0003J\u0003J\u0003J\u0003J\u0003J\u0005Jؾ\nJ\u0005Jـ\nJ\u0003K\u0003K\u0003K\u0003K\u0003K\u0003K\u0005Kو\nK\u0003K\u0003K\u0003L\u0003L\u0003L\u0005Lُ\nL\u0003L\u0005Lْ\nL\u0003M\u0003M\u0005Mٖ\nM\u0003M\u0003M\u0003M\u0005Mٛ\nM\u0003M\u0003M\u0003M\u0003M\u0007M١\nM\fM\u000eM٤\u000bM\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0003M\u0007Mٴ\nM\fM\u000eMٷ\u000bM\u0003M\u0003M\u0003M\u0005Mټ\nM\u0003N\u0003N\u0005Nڀ\nN\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0003N\u0007Nڎ\nN\fN\u000eNڑ\u000bN\u0003O\u0003O\u0003O\u0005Oږ\nO\u0003O\u0003O\u0003O\u0003O\u0003O\u0003O\u0005Oڞ\nO\u0003P\u0003P\u0003P\u0005Pڣ\nP\u0003P\u0005Pڦ\nP\u0003Q\u0003Q\u0003Q\u0005Qګ\nQ\u0003R\u0003R\u0003R\u0003R\u0003R\u0007Rڲ\nR\fR\u000eRڵ\u000bR\u0003R\u0003R\u0005Rڹ\nR\u0003R\u0003R\u0003R\u0003R\u0003R\u0003S\u0003S\u0003S\u0003S\u0003S\u0003S\u0003S\u0005Sۇ\nS\u0003S\u0005Sۊ\nS\u0005Sی\nS\u0003T\u0003T\u0003T\u0005Tۑ\nT\u0003T\u0003T\u0005Tە\nT\u0003T\u0005Tۘ\nT\u0003T\u0003T\u0003T\u0003T\u0003T\u0005T۟\nT\u0003T\u0003T\u0003T\u0003T\u0007Tۥ\nT\fT\u000eTۨ\u000bT\u0003T\u0005T۫\nT\u0003T\u0003T\u0005Tۯ\nT\u0003T\u0005T۲\nT\u0003T\u0003T\u0003T\u0003T\u0005T۸\nT\u0003T\u0005Tۻ\nT\u0005T۽\nT\u0003U\u0003U\u0003U\u0003U\u0003U\u0007U܄\nU\fU\u000eU܇\u000bU\u0003V\u0003V\u0005V܋\nV\u0003V\u0003V\u0005V\u070f\nV\u0003V\u0003V\u0005Vܓ\nV\u0003V\u0005Vܖ\nV\u0003W\u0003W\u0003W\u0003W\u0003W\u0003W\u0003W\u0007Wܟ\nW\fW\u000eWܢ\u000bW\u0003W\u0003W\u0005Wܦ\nW\u0003X\u0003X\u0005Xܪ\nX\u0003X\u0003X\u0003X\u0007Xܯ\nX\fX\u000eXܲ\u000bX\u0003X\u0003X\u0003X\u0003X\u0007Xܸ\nX\fX\u000eXܻ\u000bX\u0003X\u0005Xܾ\nX\u0005X݀\nX\u0003X\u0003X\u0005X݄\nX\u0003X\u0003X\u0003X\u0003X\u0003X\u0007X\u074b\nX\fX\u000eXݎ\u000bX\u0003X\u0003X\u0005Xݒ\nX\u0005Xݔ\nX\u0003X\u0003X\u0003X\u0003X\u0003X\u0007Xݛ\nX\fX\u000eXݞ\u000bX\u0003X\u0003X\u0003X\u0003X\u0003X\u0003X\u0007Xݦ\nX\fX\u000eXݩ\u000bX\u0003X\u0003X\u0007Xݭ\nX\fX\u000eXݰ\u000bX\u0005Xݲ\nX\u0003Y\u0003Y\u0003Y\u0003Y\u0003Y\u0005Yݹ\nY\u0003Z\u0003Z\u0003Z\u0003Z\u0003Z\u0007Zހ\nZ\fZ\u000eZރ\u000bZ\u0003Z\u0003Z\u0005Zއ\nZ\u0003[\u0005[ފ\n[\u0003[\u0003[\u0003\\\u0003\\\u0003]\u0003]\u0003^\u0003^\u0003_\u0003_\u0005_ޖ\n_\u0003`\u0003`\u0003a\u0003a\u0003b\u0003b\u0003c\u0003c\u0003d\u0003d\u0003e\u0003e\u0003f\u0003f\u0003g\u0003g\u0003h\u0003h\u0003i\u0003i\u0003j\u0003j\u0003k\u0003k\u0003l\u0003l\u0003m\u0003m\u0003n\u0003n\u0003o\u0003o\u0003p\u0003p\u0003q\u0003q\u0003r\u0003r\u0003s\u0003s\u0003s\u0003s\u0003s\u0003s\u0003s\u0005s߅\ns\u0003s\u0002\u0003\u0090t\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\u0098\u009a\u009c\u009e ¢¤¦¨ª¬®°²´¶¸º¼¾ÀÂÄÆÈÊÌÎÐÒÔÖØÚÜÞàâä\u0002\u0015\u0004\u0002\u009b\u009b\u009e\u009e\u0005\u0002\u0006\u0006\u009c\u009c\u009e\u009e\u0004\u0002((BB\u0005\u0002@@KKXX\u0004\u000255HH\u0004\u0002\u000b\u000bpp\u0003\u0002\u0089\u008a\u0004\u0002##DD\u0007\u0002\u001f\u001fNNWW\u0080\u0080\u0083\u0083\u0004\u0002\r\r\u0012\u0013\u0003\u0002\u000e\u000f\u0003\u0002\u0014\u0017\u0003\u0002\u0018\u001b\u0006\u0002SSggii||\u0004\u0002AA\u0091\u0091\u0005\u0002\u001f\u001fNN\u0083\u0083\u0006\u0002:<nn\u009c\u009c\u009e\u009f\u0004\u0002\u000e\u0010ll\u0003\u0002\u001f\u009a\u0002\u08cf\u0002æ\u0003\u0002\u0002\u0002\u0004ò\u0003\u0002\u0002\u0002\u0006ô\u0003\u0002\u0002\u0002\b÷\u0003\u0002\u0002\u0002\nă\u0003\u0002\u0002\u0002\fĈ\u0003\u0002\u0002\u0002\u000eĊ\u0003\u0002\u0002\u0002\u0010ė\u0003\u0002\u0002\u0002\u0012ī\u0003\u0002\u0002\u0002\u0014ŀ\u0003\u0002\u0002\u0002\u0016ł\u0003\u0002\u0002\u0002\u0018ņ\u0003\u0002\u0002\u0002\u001aŊ\u0003\u0002\u0002\u0002\u001cŎ\u0003\u0002\u0002\u0002\u001eŒ\u0003\u0002\u0002\u0002 Ŗ\u0003\u0002\u0002\u0002\"Ś\u0003\u0002\u0002\u0002$Š\u0003\u0002\u0002\u0002&ŧ\u0003\u0002\u0002\u0002(Ŵ\u0003\u0002\u0002\u0002*Ƃ\u0003\u0002\u0002\u0002,Ɔ\u0003\u0002\u0002\u0002.Ɗ\u0003\u0002\u0002\u00020Ǝ\u0003\u0002\u0002\u00022Ɠ\u0003\u0002\u0002\u00024Ɨ\u0003\u0002\u0002\u00026ƙ\u0003\u0002\u0002\u00028ƛ\u0003\u0002\u0002\u0002:Ɲ\u0003\u0002\u0002\u0002<Ɵ\u0003\u0002\u0002\u0002>ơ\u0003\u0002\u0002\u0002@ƥ\u0003\u0002\u0002\u0002Bƫ\u0003\u0002\u0002\u0002Dư\u0003\u0002\u0002\u0002Fƶ\u0003\u0002\u0002\u0002Hǐ\u0003\u0002\u0002\u0002Jǲ\u0003\u0002\u0002\u0002LȄ\u0003\u0002\u0002\u0002Nȍ\u0003\u0002\u0002\u0002Pȕ\u0003\u0002\u0002\u0002Rȟ\u0003\u0002\u0002\u0002TȲ\u0003\u0002\u0002\u0002Vɖ\u0003\u0002\u0002\u0002Xɶ\u0003\u0002\u0002\u0002Zʟ\u0003\u0002\u0002\u0002\\ˤ\u0003\u0002\u0002\u0002^˷\u0003\u0002\u0002\u0002`̕\u0003\u0002\u0002\u0002b̟\u0003\u0002\u0002\u0002d̼\u0003\u0002\u0002\u0002f͂\u0003\u0002\u0002\u0002h͏\u0003\u0002\u0002\u0002j͜\u0003\u0002\u0002\u0002lͩ\u0003\u0002\u0002\u0002n\u0382\u0003\u0002\u0002\u0002p\u03a2\u0003\u0002\u0002\u0002rϬ\u0003\u0002\u0002\u0002tϻ\u0003\u0002\u0002\u0002vЈ\u0003\u0002\u0002\u0002xЎ\u0003\u0002\u0002\u0002zМ\u0003\u0002\u0002\u0002|Ы\u0003\u0002\u0002\u0002~ю\u0003\u0002\u0002\u0002\u0080ҷ\u0003\u0002\u0002\u0002\u0082Һ\u0003\u0002\u0002\u0002\u0084ӝ\u0003\u0002\u0002\u0002\u0086ԓ\u0003\u0002\u0002\u0002\u0088ԕ\u0003\u0002\u0002\u0002\u008aԠ\u0003\u0002\u0002\u0002\u008cԲ\u0003\u0002\u0002\u0002\u008e՚\u0003\u0002\u0002\u0002\u0090֨\u0003\u0002\u0002\u0002\u0092ؑ\u0003\u0002\u0002\u0002\u0094ف\u0003\u0002\u0002\u0002\u0096ً\u0003\u0002\u0002\u0002\u0098ٕ\u0003\u0002\u0002\u0002\u009aٽ\u0003\u0002\u0002\u0002\u009cڕ\u0003\u0002\u0002\u0002\u009eڟ\u0003\u0002\u0002\u0002 ڪ\u0003\u0002\u0002\u0002¢ڬ\u0003\u0002\u0002\u0002¤ۋ\u0003\u0002\u0002\u0002¦ۼ\u0003\u0002\u0002\u0002¨۾\u0003\u0002\u0002\u0002ªܕ\u0003\u0002\u0002\u0002¬ܥ\u0003\u0002\u0002\u0002®ݱ\u0003\u0002\u0002\u0002°ݸ\u0003\u0002\u0002\u0002²ݺ\u0003\u0002\u0002\u0002´މ\u0003\u0002\u0002\u0002¶ލ\u0003\u0002\u0002\u0002¸ޏ\u0003\u0002\u0002\u0002ºޑ\u0003\u0002\u0002\u0002¼ޕ\u0003\u0002\u0002\u0002¾ޗ\u0003\u0002\u0002\u0002Àޙ\u0003\u0002\u0002\u0002Âޛ\u0003\u0002\u0002\u0002Äޝ\u0003\u0002\u0002\u0002Æޟ\u0003\u0002\u0002\u0002Èޡ\u0003\u0002\u0002\u0002Êޣ\u0003\u0002\u0002\u0002Ìޥ\u0003\u0002\u0002\u0002Îާ\u0003\u0002\u0002\u0002Ðީ\u0003\u0002\u0002\u0002Òޫ\u0003\u0002\u0002\u0002Ôޭ\u0003\u0002\u0002\u0002Öޯ\u0003\u0002\u0002\u0002Øޱ\u0003\u0002\u0002\u0002Ú\u07b3\u0003\u0002\u0002\u0002Ü\u07b5\u0003\u0002\u0002\u0002Þ\u07b7\u0003\u0002\u0002\u0002à\u07b9\u0003\u0002\u0002\u0002â\u07bb\u0003\u0002\u0002\u0002ä߄\u0003\u0002\u0002\u0002æç\u0007\u0086\u0002\u0002çè\u0007\r\u0002\u0002èé\u0007Q\u0002\u0002éë\u0005\u0004\u0003\u0002êì\u0005\u0006\u0004\u0002ëê\u0003\u0002\u0002\u0002ëì\u0003\u0002\u0002\u0002ìî\u0003\u0002\u0002\u0002íï\u0005\b\u0005\u0002îí\u0003\u0002\u0002\u0002îï\u0003\u0002\u0002\u0002ïð\u0003\u0002\u0002\u0002ðñ\u0007\u0002\u0002\u0003ñ\u0003\u0003\u0002\u0002\u0002òó\t\u0002\u0002\u0002ó\u0005\u0003\u0002\u0002\u0002ôõ\u0007\u0098\u0002\u0002õö\u0005\n\u0006\u0002ö\u0007\u0003\u0002\u0002\u0002÷ø\u0007s\u0002\u0002øù\u0007.\u0002\u0002ùþ\u0005> \u0002úû\u0007\u000b\u0002\u0002ûý\u0005> \u0002üú\u0003\u0002\u0002\u0002ýĀ\u0003\u0002\u0002\u0002þü\u0003\u0002\u0002\u0002þÿ\u0003\u0002\u0002\u0002ÿ\t\u0003\u0002\u0002\u0002Āþ\u0003\u0002\u0002\u0002āĄ\u0005\f\u0007\u0002ĂĄ\u0005\u0014\u000b\u0002ăā\u0003\u0002\u0002\u0002ăĂ\u0003\u0002\u0002\u0002Ą\u000b\u0003\u0002\u0002\u0002ąĉ\u0005\u000e\b\u0002Ćĉ\u0005\u0010\t\u0002ćĉ\u0005\u0012\n\u0002Ĉą\u0003\u0002\u0002\u0002ĈĆ\u0003\u0002\u0002\u0002Ĉć\u0003\u0002\u0002\u0002ĉ\r\u0003\u0002\u0002\u0002Ċċ\u0007\t\u0002\u0002ċČ\u0005\n\u0006\u0002Čč\u0007&\u0002\u0002čĒ\u0005\n\u0006\u0002Ďď\u0007&\u0002\u0002ďđ\u0005\n\u0006\u0002ĐĎ\u0003\u0002\u0002\u0002đĔ\u0003\u0002\u0002\u0002ĒĐ\u0003\u0002\u0002\u0002Ēē\u0003\u0002\u0002\u0002ēĕ\u0003\u0002\u0002\u0002ĔĒ\u0003\u0002\u0002\u0002ĕĖ\u0007\n\u0002\u0002Ė\u000f\u0003\u0002\u0002\u0002ėĘ\u0007\t\u0002\u0002Ęę\u0005\n\u0006\u0002ęĚ\u0007r\u0002\u0002Ěğ\u0005\n\u0006\u0002ěĜ\u0007r\u0002\u0002ĜĞ\u0005\n\u0006\u0002ĝě\u0003\u0002\u0002\u0002Ğġ\u0003\u0002\u0002\u0002ğĝ\u0003\u0002\u0002\u0002ğĠ\u0003\u0002\u0002\u0002ĠĢ\u0003\u0002\u0002\u0002ġğ\u0003\u0002\u0002\u0002Ģģ\u0007\n\u0002\u0002ģ\u0011\u0003\u0002\u0002\u0002Ĥĥ\u0007l\u0002\u0002ĥĬ\u0005\n\u0006\u0002Ħħ\u0007\t\u0002\u0002ħĨ\u0007l\u0002\u0002Ĩĩ\u0005\n\u0006\u0002ĩĪ\u0007\n\u0002\u0002ĪĬ\u0003\u0002\u0002\u0002īĤ\u0003\u0002\u0002\u0002īĦ\u0003\u0002\u0002\u0002Ĭ\u0013\u0003\u0002\u0002\u0002ĭŁ\u0005\u0016\f\u0002ĮŁ\u0005\u0018\r\u0002įŁ\u0005\u001a\u000e\u0002İŁ\u0005\u001c\u000f\u0002ıŁ\u0005\u001e\u0010\u0002ĲŁ\u0005 \u0011\u0002ĳŁ\u0005\"\u0012\u0002ĴŁ\u0005$\u0013\u0002ĵŁ\u0005&\u0014\u0002ĶŁ\u0005(\u0015\u0002ķŁ\u0005*\u0016\u0002ĸŁ\u0005,\u0017\u0002ĹŁ\u0005.\u0018\u0002ĺŁ\u00050\u0019\u0002ĻŁ\u00052\u001a\u0002ļĽ\u0007\t\u0002\u0002Ľľ\u0005\u0014\u000b\u0002ľĿ\u0007\n\u0002\u0002ĿŁ\u0003\u0002\u0002\u0002ŀĭ\u0003\u0002\u0002\u0002ŀĮ\u0003\u0002\u0002\u0002ŀį\u0003\u0002\u0002\u0002ŀİ\u0003\u0002\u0002\u0002ŀı\u0003\u0002\u0002\u0002ŀĲ\u0003\u0002\u0002\u0002ŀĳ\u0003\u0002\u0002\u0002ŀĴ\u0003\u0002\u0002\u0002ŀĵ\u0003\u0002\u0002\u0002ŀĶ\u0003\u0002\u0002\u0002ŀķ\u0003\u0002\u0002\u0002ŀĸ\u0003\u0002\u0002\u0002ŀĹ\u0003\u0002\u0002\u0002ŀĺ\u0003\u0002\u0002\u0002ŀĻ\u0003\u0002\u0002\u0002ŀļ\u0003\u0002\u0002\u0002Ł\u0015\u0003\u0002\u0002\u0002łŃ\u00054\u001b\u0002Ńń\u0007\f\u0002\u0002ńŅ\u0005<\u001f\u0002Ņ\u0017\u0003\u0002\u0002\u0002ņŇ\u00054\u001b\u0002Ňň\u0007\u001e\u0002\u0002ňŉ\u0005<\u001f\u0002ŉ\u0019\u0003\u0002\u0002\u0002Ŋŋ\u00054\u001b\u0002ŋŌ\u0007\u0019\u0002\u0002Ōō\u0005<\u001f\u0002ō\u001b\u0003\u0002\u0002\u0002Ŏŏ\u00054\u001b\u0002ŏŐ\u0007\u0018\u0002\u0002Őő\u0005<\u001f\u0002ő\u001d\u0003\u0002\u0002\u0002Œœ\u00054\u001b\u0002œŔ\u0007\u001b\u0002\u0002Ŕŕ\u0005<\u001f\u0002ŕ\u001f\u0003\u0002\u0002\u0002Ŗŗ\u00054\u001b\u0002ŗŘ\u0007\u001a\u0002\u0002Řř\u0005<\u001f\u0002ř!\u0003\u0002\u0002\u0002Śś\u00054\u001b\u0002śŜ\u0007-\u0002\u0002Ŝŝ\u0005<\u001f\u0002ŝŞ\u0007&\u0002\u0002Şş\u0005<\u001f\u0002ş#\u0003\u0002\u0002\u0002Šš\u00054\u001b\u0002šŢ\u0007l\u0002\u0002Ţţ\u0007-\u0002\u0002ţŤ\u0005<\u001f\u0002Ťť\u0007&\u0002\u0002ťŦ\u0005<\u001f\u0002Ŧ%\u0003\u0002\u0002\u0002ŧŨ\u00054\u001b\u0002Ũũ\u0007Y\u0002\u0002ũŪ\u0007\t\u0002\u0002Ūů\u0005<\u001f\u0002ūŬ\u0007\u000b\u0002\u0002ŬŮ\u0005<\u001f\u0002ŭū\u0003\u0002\u0002\u0002Ůű\u0003\u0002\u0002\u0002ůŭ\u0003\u0002\u0002\u0002ůŰ\u0003\u0002\u0002\u0002ŰŲ\u0003\u0002\u0002\u0002űů\u0003\u0002\u0002\u0002Ųų\u0007\n\u0002\u0002ų'\u0003\u0002\u0002\u0002Ŵŵ\u00054\u001b\u0002ŵŶ\u0007l\u0002\u0002Ŷŷ\u0007Y\u0002\u0002ŷŸ\u0007\t\u0002\u0002ŸŽ\u0005<\u001f\u0002Źź\u0007\u000b\u0002\u0002źż\u0005<\u001f\u0002ŻŹ\u0003\u0002\u0002\u0002żſ\u0003\u0002\u0002\u0002ŽŻ\u0003\u0002\u0002\u0002Žž\u0003\u0002\u0002\u0002žƀ\u0003\u0002\u0002\u0002ſŽ\u0003\u0002\u0002\u0002ƀƁ\u0007\n\u0002\u0002Ɓ)\u0003\u0002\u0002\u0002Ƃƃ\u00054\u001b\u0002ƃƄ\u0007g\u0002\u0002Ƅƅ\u00056\u001c\u0002ƅ+\u0003\u0002\u0002\u0002ƆƇ\u00054\u001b\u0002Ƈƈ\u0007g\u0002\u0002ƈƉ\u00058\u001d\u0002Ɖ-\u0003\u0002\u0002\u0002ƊƋ\u00054\u001b\u0002Ƌƌ\u0007g\u0002\u0002ƌƍ\u0005:\u001e\u0002ƍ/\u0003\u0002\u0002\u0002ƎƏ\u00054\u001b\u0002ƏƐ\u0007b\u0002\u0002ƐƑ\u0007l\u0002\u0002Ƒƒ\u0007n\u0002\u0002ƒ1\u0003\u0002\u0002\u0002ƓƔ\u00054\u001b\u0002Ɣƕ\u0007b\u0002\u0002ƕƖ\u0007n\u0002\u0002Ɩ3\u0003\u0002\u0002\u0002ƗƘ\t\u0002\u0002\u0002Ƙ5\u0003\u0002\u0002\u0002ƙƚ\u0007\u0003\u0002\u0002ƚ7\u0003\u0002\u0002\u0002ƛƜ\u0007\u0004\u0002\u0002Ɯ9\u0003\u0002\u0002\u0002Ɲƞ\u0007\u0005\u0002\u0002ƞ;\u0003\u0002\u0002\u0002ƟƠ\t\u0003\u0002\u0002Ơ=\u0003\u0002\u0002\u0002ơƣ\u00054\u001b\u0002ƢƤ\u0005@!\u0002ƣƢ\u0003\u0002\u0002\u0002ƣƤ\u0003\u0002\u0002\u0002Ƥ?\u0003\u0002\u0002\u0002ƥƦ\t\u0004\u0002\u0002ƦA\u0003\u0002\u0002\u0002Ƨƪ\u0005F$\u0002ƨƪ\u0005D#\u0002ƩƧ\u0003\u0002\u0002\u0002Ʃƨ\u0003\u0002\u0002\u0002ƪƭ\u0003\u0002\u0002\u0002ƫƩ\u0003\u0002\u0002\u0002ƫƬ\u0003\u0002\u0002\u0002ƬƮ\u0003\u0002\u0002\u0002ƭƫ\u0003\u0002\u0002\u0002ƮƯ\u0007\u0002\u0002\u0003ƯC\u0003\u0002\u0002\u0002ưƱ\u0007£\u0002\u0002ƱƲ\b#\u0001\u0002ƲE\u0003\u0002\u0002\u0002ƳƵ\u0007\u0007\u0002\u0002ƴƳ\u0003\u0002\u0002\u0002ƵƸ\u0003\u0002\u0002\u0002ƶƴ\u0003\u0002\u0002\u0002ƶƷ\u0003\u0002\u0002\u0002Ʒƹ\u0003\u0002\u0002\u0002Ƹƶ\u0003\u0002\u0002\u0002ƹǂ\u0005H%\u0002ƺƼ\u0007\u0007\u0002\u0002ƻƺ\u0003\u0002\u0002\u0002Ƽƽ\u0003\u0002\u0002\u0002ƽƻ\u0003\u0002\u0002\u0002ƽƾ\u0003\u0002\u0002\u0002ƾƿ\u0003\u0002\u0002\u0002ƿǁ\u0005H%\u0002ǀƻ\u0003\u0002\u0002\u0002ǁǄ\u0003\u0002\u0002\u0002ǂǀ\u0003\u0002\u0002\u0002ǂǃ\u0003\u0002\u0002\u0002ǃǈ\u0003\u0002\u0002\u0002Ǆǂ\u0003\u0002\u0002\u0002ǅǇ\u0007\u0007\u0002\u0002ǆǅ\u0003\u0002\u0002\u0002ǇǊ\u0003\u0002\u0002\u0002ǈǆ\u0003\u0002\u0002\u0002ǈǉ\u0003\u0002\u0002\u0002ǉG\u0003\u0002\u0002\u0002Ǌǈ\u0003\u0002\u0002\u0002ǋǎ\u0007M\u0002\u0002ǌǍ\u0007x\u0002\u0002ǍǏ\u0007u\u0002\u0002ǎǌ\u0003\u0002\u0002\u0002ǎǏ\u0003\u0002\u0002\u0002ǏǑ\u0003\u0002\u0002\u0002ǐǋ\u0003\u0002\u0002\u0002ǐǑ\u0003\u0002\u0002\u0002Ǒǰ\u0003\u0002\u0002\u0002ǒǱ\u0005J&\u0002ǓǱ\u0005L'\u0002ǔǱ\u0005N(\u0002ǕǱ\u0005P)\u0002ǖǱ\u0005R*\u0002ǗǱ\u0005T+\u0002ǘǱ\u0005V,\u0002ǙǱ\u0005X-\u0002ǚǱ\u0005Z.\u0002ǛǱ\u0005\\/\u0002ǜǱ\u0005^0\u0002ǝǱ\u0005`1\u0002ǞǱ\u0005b2\u0002ǟǱ\u0005d3\u0002ǠǱ\u0005f4\u0002ǡǱ\u0005h5\u0002ǢǱ\u0005j6\u0002ǣǱ\u0005l7\u0002ǤǱ\u0005n8\u0002ǥǱ\u0005p9\u0002ǦǱ\u0005r:\u0002ǧǱ\u0005t;\u0002ǨǱ\u0005v<\u0002ǩǱ\u0005x=\u0002ǪǱ\u0005z>\u0002ǫǱ\u0005|?\u0002ǬǱ\u0005~@\u0002ǭǱ\u0005\u0082B\u0002ǮǱ\u0005\u0084C\u0002ǯǱ\u0005\u0086D\u0002ǰǒ\u0003\u0002\u0002\u0002ǰǓ\u0003\u0002\u0002\u0002ǰǔ\u0003\u0002\u0002\u0002ǰǕ\u0003\u0002\u0002\u0002ǰǖ\u0003\u0002\u0002\u0002ǰǗ\u0003\u0002\u0002\u0002ǰǘ\u0003\u0002\u0002\u0002ǰǙ\u0003\u0002\u0002\u0002ǰǚ\u0003\u0002\u0002\u0002ǰǛ\u0003\u0002\u0002\u0002ǰǜ\u0003\u0002\u0002\u0002ǰǝ\u0003\u0002\u0002\u0002ǰǞ\u0003\u0002\u0002\u0002ǰǟ\u0003\u0002\u0002\u0002ǰǠ\u0003\u0002\u0002\u0002ǰǡ\u0003\u0002\u0002\u0002ǰǢ\u0003\u0002\u0002\u0002ǰǣ\u0003\u0002\u0002\u0002ǰǤ\u0003\u0002\u0002\u0002ǰǥ\u0003\u0002\u0002\u0002ǰǦ\u0003\u0002\u0002\u0002ǰǧ\u0003\u0002\u0002\u0002ǰǨ\u0003\u0002\u0002\u0002ǰǩ\u0003\u0002\u0002\u0002ǰǪ\u0003\u0002\u0002\u0002ǰǫ\u0003\u0002\u0002\u0002ǰǬ\u0003\u0002\u0002\u0002ǰǭ\u0003\u0002\u0002\u0002ǰǮ\u0003\u0002\u0002\u0002ǰǯ\u0003\u0002\u0002\u0002ǱI\u0003\u0002\u0002\u0002ǲǳ\u0007$\u0002\u0002ǳǷ\u0007\u0088\u0002\u0002Ǵǵ\u0005Æd\u0002ǵǶ\u0007\b\u0002\u0002ǶǸ\u0003\u0002\u0002\u0002ǷǴ\u0003\u0002\u0002\u0002ǷǸ\u0003\u0002\u0002\u0002Ǹǹ\u0003\u0002\u0002\u0002ǹȂ\u0005Èe\u0002Ǻǻ\u0007\u007f\u0002\u0002ǻǼ\u0007\u008c\u0002\u0002Ǽȃ\u0005Ìg\u0002ǽǿ\u0007!\u0002\u0002ǾȀ\u00074\u0002\u0002ǿǾ\u0003\u0002\u0002\u0002ǿȀ\u0003\u0002\u0002\u0002Ȁȁ\u0003\u0002\u0002\u0002ȁȃ\u0005\u0088E\u0002ȂǺ\u0003\u0002\u0002\u0002Ȃǽ\u0003\u0002\u0002\u0002ȃK\u0003\u0002\u0002\u0002Ȅȋ\u0007%\u0002\u0002ȅȌ\u0005Æd\u0002ȆȌ\u0005Êf\u0002ȇȈ\u0005Æd\u0002Ȉȉ\u0007\b\u0002\u0002ȉȊ\u0005Êf\u0002ȊȌ\u0003\u0002\u0002\u0002ȋȅ\u0003\u0002\u0002\u0002ȋȆ\u0003\u0002\u0002\u0002ȋȇ\u0003\u0002\u0002\u0002ȋȌ\u0003\u0002\u0002\u0002ȌM\u0003\u0002\u0002\u0002ȍȏ\u0007)\u0002\u0002ȎȐ\u0007=\u0002\u0002ȏȎ\u0003\u0002\u0002\u0002ȏȐ\u0003\u0002\u0002\u0002Ȑȑ\u0003\u0002\u0002\u0002ȑȒ\u0005\u0090I\u0002Ȓȓ\u0007'\u0002\u0002ȓȔ\u0005Æd\u0002ȔO\u0003\u0002\u0002\u0002ȕȗ\u0007,\u0002\u0002ȖȘ\t\u0005\u0002\u0002ȗȖ\u0003\u0002\u0002\u0002ȗȘ\u0003\u0002\u0002\u0002Șȝ\u0003\u0002\u0002\u0002șț\u0007\u008d\u0002\u0002ȚȜ\u0005âr\u0002țȚ\u0003\u0002\u0002\u0002țȜ\u0003\u0002\u0002\u0002ȜȞ\u0003\u0002\u0002\u0002ȝș\u0003\u0002\u0002\u0002ȝȞ\u0003\u0002\u0002\u0002ȞQ\u0003\u0002\u0002\u0002ȟȤ\t\u0006\u0002\u0002ȠȢ\u0007\u008d\u0002\u0002ȡȣ\u0005âr\u0002Ȣȡ\u0003\u0002\u0002\u0002Ȣȣ\u0003\u0002\u0002\u0002ȣȥ\u0003\u0002\u0002\u0002ȤȠ\u0003\u0002\u0002\u0002Ȥȥ\u0003\u0002\u0002\u0002ȥS\u0003\u0002\u0002\u0002ȦȨ\u0007\u0099\u0002\u0002ȧȩ\u0007z\u0002\u0002Ȩȧ\u0003\u0002\u0002\u0002Ȩȩ\u0003\u0002\u0002\u0002ȩȪ\u0003\u0002\u0002\u0002Ȫȯ\u0005¢R\u0002ȫȬ\u0007\u000b\u0002\u0002ȬȮ\u0005¢R\u0002ȭȫ\u0003\u0002\u0002\u0002Ȯȱ\u0003\u0002\u0002\u0002ȯȭ\u0003\u0002\u0002\u0002ȯȰ\u0003\u0002\u0002\u0002Ȱȳ\u0003\u0002\u0002\u0002ȱȯ\u0003\u0002\u0002\u0002ȲȦ\u0003\u0002\u0002\u0002Ȳȳ\u0003\u0002\u0002\u0002ȳȴ\u0003\u0002\u0002\u0002ȴȾ\u0005®X\u0002ȵȷ\u0007\u008f\u0002\u0002ȶȸ\u0007#\u0002\u0002ȷȶ\u0003\u0002\u0002\u0002ȷȸ\u0003\u0002\u0002\u0002ȸȼ\u0003\u0002\u0002\u0002ȹȼ\u0007`\u0002\u0002Ⱥȼ\u0007J\u0002\u0002Ȼȵ\u0003\u0002\u0002\u0002Ȼȹ\u0003\u0002\u0002\u0002ȻȺ\u0003\u0002\u0002\u0002ȼȽ\u0003\u0002\u0002\u0002Ƚȿ\u0005®X\u0002ȾȻ\u0003\u0002\u0002\u0002ȿɀ\u0003\u0002\u0002\u0002ɀȾ\u0003\u0002\u0002\u0002ɀɁ\u0003\u0002\u0002\u0002ɁɌ\u0003\u0002\u0002\u0002ɂɃ\u0007s\u0002\u0002ɃɄ\u0007.\u0002\u0002Ʉɉ\u0005\u009eP\u0002ɅɆ\u0007\u000b\u0002\u0002ɆɈ\u0005\u009eP\u0002ɇɅ\u0003\u0002\u0002\u0002Ɉɋ\u0003\u0002\u0002\u0002ɉɇ\u0003\u0002\u0002\u0002ɉɊ\u0003\u0002\u0002\u0002Ɋɍ\u0003\u0002\u0002\u0002ɋɉ\u0003\u0002\u0002\u0002Ɍɂ\u0003\u0002\u0002\u0002Ɍɍ\u0003\u0002\u0002\u0002ɍɔ\u0003\u0002\u0002\u0002Ɏɏ\u0007h\u0002\u0002ɏɒ\u0005\u0090I\u0002ɐɑ\t\u0007\u0002\u0002ɑɓ\u0005\u0090I\u0002ɒɐ\u0003\u0002\u0002\u0002ɒɓ\u0003\u0002\u0002\u0002ɓɕ\u0003\u0002\u0002\u0002ɔɎ\u0003\u0002\u0002\u0002ɔɕ\u0003\u0002\u0002\u0002ɕU\u0003\u0002\u0002\u0002ɖɘ\u00078\u0002\u0002ɗə\u0007\u0090\u0002\u0002ɘɗ\u0003\u0002\u0002\u0002ɘə\u0003\u0002\u0002\u0002əɚ\u0003\u0002\u0002\u0002ɚɞ\u0007Z\u0002\u0002ɛɜ\u0007V\u0002\u0002ɜɝ\u0007l\u0002\u0002ɝɟ\u0007L\u0002\u0002ɞɛ\u0003\u0002\u0002\u0002ɞɟ\u0003\u0002\u0002\u0002ɟɣ\u0003\u0002\u0002\u0002ɠɡ\u0005Æd\u0002ɡɢ\u0007\b\u0002\u0002ɢɤ\u0003\u0002\u0002\u0002ɣɠ\u0003\u0002\u0002\u0002ɣɤ\u0003\u0002\u0002\u0002ɤɥ\u0003\u0002\u0002\u0002ɥɦ\u0005Ôk\u0002ɦɧ\u0007q\u0002\u0002ɧɨ\u0005Èe\u0002ɨɩ\u0007\t\u0002\u0002ɩɮ\u0005\u0096L\u0002ɪɫ\u0007\u000b\u0002\u0002ɫɭ\u0005\u0096L\u0002ɬɪ\u0003\u0002\u0002\u0002ɭɰ\u0003\u0002\u0002\u0002ɮɬ\u0003\u0002\u0002\u0002ɮɯ\u0003\u0002\u0002\u0002ɯɱ\u0003\u0002\u0002\u0002ɰɮ\u0003\u0002\u0002\u0002ɱɴ\u0007\n\u0002\u0002ɲɳ\u0007\u0098\u0002\u0002ɳɵ\u0005\u0090I\u0002ɴɲ\u0003\u0002\u0002\u0002ɴɵ\u0003\u0002\u0002\u0002ɵW\u0003\u0002\u0002\u0002ɶɸ\u00078\u0002\u0002ɷɹ\t\b\u0002\u0002ɸɷ\u0003\u0002\u0002\u0002ɸɹ\u0003\u0002\u0002\u0002ɹɺ\u0003\u0002\u0002\u0002ɺɾ\u0007\u0088\u0002\u0002ɻɼ\u0007V\u0002\u0002ɼɽ\u0007l\u0002\u0002ɽɿ\u0007L\u0002\u0002ɾɻ\u0003\u0002\u0002\u0002ɾɿ\u0003\u0002\u0002\u0002ɿʃ\u0003\u0002\u0002\u0002ʀʁ\u0005Æd\u0002ʁʂ\u0007\b\u0002\u0002ʂʄ\u0003\u0002\u0002\u0002ʃʀ\u0003\u0002\u0002\u0002ʃʄ\u0003\u0002\u0002\u0002ʄʅ\u0003\u0002\u0002\u0002ʅʝ\u0005Èe\u0002ʆʇ\u0007\t\u0002\u0002ʇʌ\u0005\u0088E\u0002ʈʉ\u0007\u000b\u0002\u0002ʉʋ\u0005\u0088E\u0002ʊʈ\u0003\u0002\u0002\u0002ʋʎ\u0003\u0002\u0002\u0002ʌʊ\u0003\u0002\u0002\u0002ʌʍ\u0003\u0002\u0002\u0002ʍʓ\u0003\u0002\u0002\u0002ʎʌ\u0003\u0002\u0002\u0002ʏʐ\u0007\u000b\u0002\u0002ʐʒ\u0005\u0098M\u0002ʑʏ\u0003\u0002\u0002\u0002ʒʕ\u0003\u0002\u0002\u0002ʓʑ\u0003\u0002\u0002\u0002ʓʔ\u0003\u0002\u0002\u0002ʔʖ\u0003\u0002\u0002\u0002ʕʓ\u0003\u0002\u0002\u0002ʖʙ\u0007\n\u0002\u0002ʗʘ\u0007\u009a\u0002\u0002ʘʚ\u0007\u009b\u0002\u0002ʙʗ\u0003\u0002\u0002\u0002ʙʚ\u0003\u0002\u0002\u0002ʚʞ\u0003\u0002\u0002\u0002ʛʜ\u0007'\u0002\u0002ʜʞ\u0005~@\u0002ʝʆ\u0003\u0002\u0002\u0002ʝʛ\u0003\u0002\u0002\u0002ʞY\u0003\u0002\u0002\u0002ʟʡ\u00078\u0002\u0002ʠʢ\t\b\u0002\u0002ʡʠ\u0003\u0002\u0002\u0002ʡʢ\u0003\u0002\u0002\u0002ʢʣ\u0003\u0002\u0002\u0002ʣʧ\u0007\u008e\u0002\u0002ʤʥ\u0007V\u0002\u0002ʥʦ\u0007l\u0002\u0002ʦʨ\u0007L\u0002\u0002ʧʤ\u0003\u0002\u0002\u0002ʧʨ\u0003\u0002\u0002\u0002ʨʬ\u0003\u0002\u0002\u0002ʩʪ\u0005Æd\u0002ʪʫ\u0007\b\u0002\u0002ʫʭ\u0003\u0002\u0002\u0002ʬʩ\u0003\u0002\u0002\u0002ʬʭ\u0003\u0002\u0002\u0002ʭʮ\u0003\u0002\u0002\u0002ʮʳ\u0005Öl\u0002ʯʴ\u0007+\u0002\u0002ʰʴ\u0007\"\u0002\u0002ʱʲ\u0007_\u0002\u0002ʲʴ\u0007o\u0002\u0002ʳʯ\u0003\u0002\u0002\u0002ʳʰ\u0003\u0002\u0002\u0002ʳʱ\u0003\u0002\u0002\u0002ʳʴ\u0003\u0002\u0002\u0002ʴ˃\u0003\u0002\u0002\u0002ʵ˄\u0007A\u0002\u0002ʶ˄\u0007^\u0002\u0002ʷˁ\u0007\u0091\u0002\u0002ʸʹ\u0007o\u0002\u0002ʹʾ\u0005Îh\u0002ʺʻ\u0007\u000b\u0002\u0002ʻʽ\u0005Îh\u0002ʼʺ\u0003\u0002\u0002\u0002ʽˀ\u0003\u0002\u0002\u0002ʾʼ\u0003\u0002\u0002\u0002ʾʿ\u0003\u0002\u0002\u0002ʿ˂\u0003\u0002\u0002\u0002ˀʾ\u0003\u0002\u0002\u0002ˁʸ\u0003\u0002\u0002\u0002ˁ˂\u0003\u0002\u0002\u0002˂˄\u0003\u0002\u0002\u0002˃ʵ\u0003\u0002\u0002\u0002˃ʶ\u0003\u0002\u0002\u0002˃ʷ\u0003\u0002\u0002\u0002˄˅\u0003\u0002\u0002\u0002˅ˉ\u0007q\u0002\u0002ˆˇ\u0005Æd\u0002ˇˈ\u0007\b\u0002\u0002ˈˊ\u0003\u0002\u0002\u0002ˉˆ\u0003\u0002\u0002\u0002ˉˊ\u0003\u0002\u0002\u0002ˊˋ\u0003\u0002\u0002\u0002ˋˏ\u0005Èe\u0002ˌˍ\u0007O\u0002\u0002ˍˎ\u0007F\u0002\u0002ˎː\u0007\u0084\u0002\u0002ˏˌ\u0003\u0002\u0002\u0002ˏː\u0003\u0002\u0002\u0002ː˓\u0003\u0002\u0002\u0002ˑ˒\u0007\u0097\u0002\u0002˒˔\u0005\u0090I\u0002˓ˑ\u0003\u0002\u0002\u0002˓˔\u0003\u0002\u0002\u0002˔˕\u0003\u0002\u0002\u0002˕˞\u0007,\u0002\u0002˖˛\u0005\u0082B\u0002˗˛\u0005p9\u0002˘˛\u0005`1\u0002˙˛\u0005~@\u0002˚˖\u0003\u0002\u0002\u0002˚˗\u0003\u0002\u0002\u0002˚˘\u0003\u0002\u0002\u0002˚˙\u0003\u0002\u0002\u0002˛˜\u0003\u0002\u0002\u0002˜˝\u0007\u0007\u0002\u0002˝˟\u0003\u0002\u0002\u0002˞˚\u0003\u0002\u0002\u0002˟ˠ\u0003\u0002\u0002\u0002ˠ˞\u0003\u0002\u0002\u0002ˠˡ\u0003\u0002\u0002\u0002ˡˢ\u0003\u0002\u0002\u0002ˢˣ\u0007H\u0002\u0002ˣ[\u0003\u0002\u0002\u0002ˤ˦\u00078\u0002\u0002˥˧\t\b\u0002\u0002˦˥\u0003\u0002\u0002\u0002˦˧\u0003\u0002\u0002\u0002˧˨\u0003\u0002\u0002\u0002˨ˬ\u0007\u0095\u0002\u0002˩˪\u0007V\u0002\u0002˪˫\u0007l\u0002\u0002˫˭\u0007L\u0002\u0002ˬ˩\u0003\u0002\u0002\u0002ˬ˭\u0003\u0002\u0002\u0002˭˱\u0003\u0002\u0002\u0002ˮ˯\u0005Æd\u0002˯˰\u0007\b\u0002\u0002˰˲\u0003\u0002\u0002\u0002˱ˮ\u0003\u0002\u0002\u0002˱˲\u0003\u0002\u0002\u0002˲˳\u0003\u0002\u0002\u0002˳˴\u0005Øm\u0002˴˵\u0007'\u0002\u0002˵˶\u0005~@\u0002˶]\u0003\u0002\u0002\u0002˷˸\u00078\u0002\u0002˸˹\u0007\u0096\u0002\u0002˹˽\u0007\u0088\u0002\u0002˺˻\u0007V\u0002\u0002˻˼\u0007l\u0002\u0002˼˾\u0007L\u0002\u0002˽˺\u0003\u0002\u0002\u0002˽˾\u0003\u0002\u0002\u0002˾̂\u0003\u0002\u0002\u0002˿̀\u0005Æd\u0002̀́\u0007\b\u0002\u0002́̃\u0003\u0002\u0002\u0002̂˿\u0003\u0002\u0002\u0002̂̃\u0003\u0002\u0002\u0002̃̄\u0003\u0002\u0002\u0002̄̅\u0005Èe\u0002̅̆\u0007\u0092\u0002\u0002̆̒\u0005Ún\u0002̇̈\u0007\t\u0002\u0002̈̍\u0005¼_\u0002̉̊\u0007\u000b\u0002\u0002̊̌\u0005¼_\u0002̋̉\u0003\u0002\u0002\u0002̌̏\u0003\u0002\u0002\u0002̍̋\u0003\u0002\u0002\u0002̍̎\u0003\u0002\u0002\u0002̎̐\u0003\u0002\u0002\u0002̏̍\u0003\u0002\u0002\u0002̐̑\u0007\n\u0002\u0002̑̓\u0003\u0002\u0002\u0002̒̇\u0003\u0002\u0002\u0002̒̓\u0003\u0002\u0002\u0002̓_\u0003\u0002\u0002\u0002̖̔\u0005\u009aN\u0002̔̕\u0003\u0002\u0002\u0002̖̕\u0003\u0002\u0002\u0002̖̗\u0003\u0002\u0002\u0002̗̘\u0007A\u0002\u0002̘̙\u0007Q\u0002\u0002̙̜\u0005\u009cO\u0002̛̚\u0007\u0098\u0002\u0002̛̝\u0005\u0090I\u0002̜̚\u0003\u0002\u0002\u0002̜̝\u0003\u0002\u0002\u0002̝a\u0003\u0002\u0002\u0002̞̠\u0005\u009aN\u0002̟̞\u0003\u0002\u0002\u0002̟̠\u0003\u0002\u0002\u0002̡̠\u0003\u0002\u0002\u0002̡̢\u0007A\u0002\u0002̢̣\u0007Q\u0002\u0002̣̦\u0005\u009cO\u0002̤̥\u0007\u0098\u0002\u0002̧̥\u0005\u0090I\u0002̦̤\u0003\u0002\u0002\u0002̧̦\u0003\u0002\u0002\u0002̧̺\u0003\u0002\u0002\u0002̨̩\u0007s\u0002\u0002̩̪\u0007.\u0002\u0002̪̯\u0005\u009eP\u0002̫̬\u0007\u000b\u0002\u0002̬̮\u0005\u009eP\u0002̭̫\u0003\u0002\u0002\u0002̮̱\u0003\u0002\u0002\u0002̯̭\u0003\u0002\u0002\u0002̯̰\u0003\u0002\u0002\u0002̰̳\u0003\u0002\u0002\u0002̱̯\u0003\u0002\u0002\u0002̨̲\u0003\u0002\u0002\u0002̲̳\u0003\u0002\u0002\u0002̴̳\u0003\u0002\u0002\u0002̴̵\u0007h\u0002\u0002̵̸\u0005\u0090I\u0002̶̷\t\u0007\u0002\u0002̷̹\u0005\u0090I\u0002̸̶\u0003\u0002\u0002\u0002̸̹\u0003\u0002\u0002\u0002̹̻\u0003\u0002\u0002\u0002̺̲\u0003\u0002\u0002\u0002̺̻\u0003\u0002\u0002\u0002̻c\u0003\u0002\u0002\u0002̼̾\u0007C\u0002\u0002̽̿\u0007=\u0002\u0002̾̽\u0003\u0002\u0002\u0002̾̿\u0003\u0002\u0002\u0002̿̀\u0003\u0002\u0002\u0002̀́\u0005Æd\u0002́e\u0003\u0002\u0002\u0002͂̓\u0007E\u0002\u0002̓͆\u0007Z\u0002\u0002̈́ͅ\u0007V\u0002\u0002͇ͅ\u0007L\u0002\u0002͆̈́\u0003\u0002\u0002\u0002͇͆\u0003\u0002\u0002\u0002͇͋\u0003\u0002\u0002\u0002͈͉\u0005Æd\u0002͉͊\u0007\b\u0002\u0002͊͌\u0003\u0002\u0002\u0002͈͋\u0003\u0002\u0002\u0002͋͌\u0003\u0002\u0002\u0002͍͌\u0003\u0002\u0002\u0002͍͎\u0005Ôk\u0002͎g\u0003\u0002\u0002\u0002͏͐\u0007E\u0002\u0002͓͐\u0007\u0088\u0002\u0002͑͒\u0007V\u0002\u0002͔͒\u0007L\u0002\u0002͓͑\u0003\u0002\u0002\u0002͓͔\u0003\u0002\u0002\u0002͔͘\u0003\u0002\u0002\u0002͕͖\u0005Æd\u0002͖͗\u0007\b\u0002\u0002͙͗\u0003\u0002\u0002\u0002͕͘\u0003\u0002\u0002\u0002͙͘\u0003\u0002\u0002\u0002͙͚\u0003\u0002\u0002\u0002͚͛\u0005Èe\u0002͛i\u0003\u0002\u0002\u0002͜͝\u0007E\u0002\u0002͝͠\u0007\u008e\u0002\u0002͟͞\u0007V\u0002\u0002͟͡\u0007L\u0002\u0002͠͞\u0003\u0002\u0002\u0002͠͡\u0003\u0002\u0002\u0002ͥ͡\u0003\u0002\u0002\u0002ͣ͢\u0005Æd\u0002ͣͤ\u0007\b\u0002\u0002ͤͦ\u0003\u0002\u0002\u0002ͥ͢\u0003\u0002\u0002\u0002ͥͦ\u0003\u0002\u0002\u0002ͦͧ\u0003\u0002\u0002\u0002ͧͨ\u0005Öl\u0002ͨk\u0003\u0002\u0002\u0002ͩͪ\u0007E\u0002\u0002ͪͭ\u0007\u0095\u0002\u0002ͫͬ\u0007V\u0002\u0002ͬͮ\u0007L\u0002\u0002ͭͫ\u0003\u0002\u0002\u0002ͭͮ\u0003\u0002\u0002\u0002ͮͲ\u0003\u0002\u0002\u0002ͯͰ\u0005Æd\u0002Ͱͱ\u0007\b\u0002\u0002ͱͳ\u0003\u0002\u0002\u0002Ͳͯ\u0003\u0002\u0002\u0002Ͳͳ\u0003\u0002\u0002\u0002ͳʹ\u0003\u0002\u0002\u0002ʹ͵\u0005Øm\u0002͵m\u0003\u0002\u0002\u0002Ͷ\u0378\u0007\u0099\u0002\u0002ͷ\u0379\u0007z\u0002\u0002\u0378ͷ\u0003\u0002\u0002\u0002\u0378\u0379\u0003\u0002\u0002\u0002\u0379ͺ\u0003\u0002\u0002\u0002ͺͿ\u0005¢R\u0002ͻͼ\u0007\u000b\u0002\u0002ͼ;\u0005¢R\u0002ͽͻ\u0003\u0002\u0002\u0002;\u0381\u0003\u0002\u0002\u0002Ϳͽ\u0003\u0002\u0002\u0002Ϳ\u0380\u0003\u0002\u0002\u0002\u0380\u0383\u0003\u0002\u0002\u0002\u0381Ϳ\u0003\u0002\u0002\u0002\u0382Ͷ\u0003\u0002\u0002\u0002\u0382\u0383\u0003\u0002\u0002\u0002\u0383΄\u0003\u0002\u0002\u0002΄Ί\u0005®X\u0002΅Ά\u0005°Y\u0002Ά·\u0005®X\u0002·Ή\u0003\u0002\u0002\u0002Έ΅\u0003\u0002\u0002\u0002ΉΌ\u0003\u0002\u0002\u0002ΊΈ\u0003\u0002\u0002\u0002Ί\u038b\u0003\u0002\u0002\u0002\u038bΗ\u0003\u0002\u0002\u0002ΌΊ\u0003\u0002\u0002\u0002\u038dΎ\u0007s\u0002\u0002ΎΏ\u0007.\u0002\u0002ΏΔ\u0005\u009eP\u0002ΐΑ\u0007\u000b\u0002\u0002ΑΓ\u0005\u009eP\u0002Βΐ\u0003\u0002\u0002\u0002ΓΖ\u0003\u0002\u0002\u0002ΔΒ\u0003\u0002\u0002\u0002ΔΕ\u0003\u0002\u0002\u0002ΕΘ\u0003\u0002\u0002\u0002ΖΔ\u0003\u0002\u0002\u0002Η\u038d\u0003\u0002\u0002\u0002ΗΘ\u0003\u0002\u0002\u0002ΘΟ\u0003\u0002\u0002\u0002ΙΚ\u0007h\u0002\u0002ΚΝ\u0005\u0090I\u0002ΛΜ\t\u0007\u0002\u0002ΜΞ\u0005\u0090I\u0002ΝΛ\u0003\u0002\u0002\u0002ΝΞ\u0003\u0002\u0002\u0002ΞΠ\u0003\u0002\u0002\u0002ΟΙ\u0003\u0002\u0002\u0002ΟΠ\u0003\u0002\u0002\u0002Πo\u0003\u0002\u0002\u0002ΡΣ\u0005\u009aN\u0002\u03a2Ρ\u0003\u0002\u0002\u0002\u03a2Σ\u0003\u0002\u0002\u0002Σε\u0003\u0002\u0002\u0002Τζ\u0007^\u0002\u0002Υζ\u0007\u0080\u0002\u0002ΦΧ\u0007^\u0002\u0002ΧΨ\u0007r\u0002\u0002Ψζ\u0007\u0080\u0002\u0002ΩΪ\u0007^\u0002\u0002ΪΫ\u0007r\u0002\u0002Ϋζ\u0007\u0083\u0002\u0002άέ\u0007^\u0002\u0002έή\u0007r\u0002\u0002ήζ\u0007\u001f\u0002\u0002ίΰ\u0007^\u0002\u0002ΰα\u0007r\u0002\u0002αζ\u0007N\u0002\u0002βγ\u0007^\u0002\u0002γδ\u0007r\u0002\u0002δζ\u0007W\u0002\u0002εΤ\u0003\u0002\u0002\u0002εΥ\u0003\u0002\u0002\u0002εΦ\u0003\u0002\u0002\u0002εΩ\u0003\u0002\u0002\u0002εά\u0003\u0002\u0002\u0002εί\u0003\u0002\u0002\u0002εβ\u0003\u0002\u0002\u0002ζη\u0003\u0002\u0002\u0002ηλ\u0007a\u0002\u0002θι\u0005Æd\u0002ικ\u0007\b\u0002\u0002κμ\u0003\u0002\u0002\u0002λθ\u0003\u0002\u0002\u0002λμ\u0003\u0002\u0002\u0002μν\u0003\u0002\u0002\u0002νω\u0005Èe\u0002ξο\u0007\t\u0002\u0002οτ\u0005Îh\u0002πρ\u0007\u000b\u0002\u0002ρσ\u0005Îh\u0002ςπ\u0003\u0002\u0002\u0002σφ\u0003\u0002\u0002\u0002τς\u0003\u0002\u0002\u0002τυ\u0003\u0002\u0002\u0002υχ\u0003\u0002\u0002\u0002φτ\u0003\u0002\u0002\u0002χψ\u0007\n\u0002\u0002ψϊ\u0003\u0002\u0002\u0002ωξ\u0003\u0002\u0002\u0002ωϊ\u0003\u0002\u0002\u0002ϊϪ\u0003\u0002\u0002\u0002ϋό\u0007\u0094\u0002\u0002όύ\u0007\t\u0002\u0002ύϒ\u0005\u0090I\u0002ώϏ\u0007\u000b\u0002\u0002Ϗϑ\u0005\u0090I\u0002ϐώ\u0003\u0002\u0002\u0002ϑϔ\u0003\u0002\u0002\u0002ϒϐ\u0003\u0002\u0002\u0002ϒϓ\u0003\u0002\u0002\u0002ϓϕ\u0003\u0002\u0002\u0002ϔϒ\u0003\u0002\u0002\u0002ϕϤ\u0007\n\u0002\u0002ϖϗ\u0007\u000b\u0002\u0002ϗϘ\u0007\t\u0002\u0002Ϙϝ\u0005\u0090I\u0002ϙϚ\u0007\u000b\u0002\u0002ϚϜ\u0005\u0090I\u0002ϛϙ\u0003\u0002\u0002\u0002Ϝϟ\u0003\u0002\u0002\u0002ϝϛ\u0003\u0002\u0002\u0002ϝϞ\u0003\u0002\u0002\u0002ϞϠ\u0003\u0002\u0002\u0002ϟϝ\u0003\u0002\u0002\u0002Ϡϡ\u0007\n\u0002\u0002ϡϣ\u0003\u0002\u0002\u0002Ϣϖ\u0003\u0002\u0002\u0002ϣϦ\u0003\u0002\u0002\u0002ϤϢ\u0003\u0002\u0002\u0002Ϥϥ\u0003\u0002\u0002\u0002ϥϫ\u0003\u0002\u0002\u0002ϦϤ\u0003\u0002\u0002\u0002ϧϫ\u0005~@\u0002Ϩϩ\u0007>\u0002\u0002ϩϫ\u0007\u0094\u0002\u0002Ϫϋ\u0003\u0002\u0002\u0002Ϫϧ\u0003\u0002\u0002\u0002ϪϨ\u0003\u0002\u0002\u0002ϫq\u0003\u0002\u0002\u0002Ϭϰ\u0007v\u0002\u0002ϭϮ\u0005Æd\u0002Ϯϯ\u0007\b\u0002\u0002ϯϱ\u0003\u0002\u0002\u0002ϰϭ\u0003\u0002\u0002\u0002ϰϱ\u0003\u0002\u0002\u0002ϱϲ\u0003\u0002\u0002\u0002ϲϹ\u0005Üo\u0002ϳϴ\u0007\f\u0002\u0002ϴϺ\u0005 Q\u0002ϵ϶\u0007\t\u0002\u0002϶Ϸ\u0005 Q\u0002Ϸϸ\u0007\n\u0002\u0002ϸϺ\u0003\u0002\u0002\u0002Ϲϳ\u0003\u0002\u0002\u0002Ϲϵ\u0003\u0002\u0002\u0002ϹϺ\u0003\u0002\u0002\u0002Ϻs\u0003\u0002\u0002\u0002ϻІ\u0007}\u0002\u0002ϼЇ\u0005Ði\u0002ϽϾ\u0005Æd\u0002ϾϿ\u0007\b\u0002\u0002ϿЁ\u0003\u0002\u0002\u0002ЀϽ\u0003\u0002\u0002\u0002ЀЁ\u0003\u0002\u0002\u0002ЁЄ\u0003\u0002\u0002\u0002ЂЅ\u0005Èe\u0002ЃЅ\u0005Ôk\u0002ЄЂ\u0003\u0002\u0002\u0002ЄЃ\u0003\u0002\u0002\u0002ЅЇ\u0003\u0002\u0002\u0002Іϼ\u0003\u0002\u0002\u0002ІЀ\u0003\u0002\u0002\u0002ІЇ\u0003\u0002\u0002\u0002Їu\u0003\u0002\u0002\u0002ЈЊ\u0007~\u0002\u0002ЉЋ\u0007\u0085\u0002\u0002ЊЉ\u0003\u0002\u0002\u0002ЊЋ\u0003\u0002\u0002\u0002ЋЌ\u0003\u0002\u0002\u0002ЌЍ\u0005Þp\u0002Ѝw\u0003\u0002\u0002\u0002ЎГ\u0007\u0083\u0002\u0002ЏБ\u0007\u008d\u0002\u0002АВ\u0005âr\u0002БА\u0003\u0002\u0002\u0002БВ\u0003\u0002\u0002\u0002ВД\u0003\u0002\u0002\u0002ГЏ\u0003\u0002\u0002\u0002ГД\u0003\u0002\u0002\u0002ДК\u0003\u0002\u0002\u0002ЕЗ\u0007\u008c\u0002\u0002ЖИ\u0007\u0085\u0002\u0002ЗЖ\u0003\u0002\u0002\u0002ЗИ\u0003\u0002\u0002\u0002ИЙ\u0003\u0002\u0002\u0002ЙЛ\u0005Þp\u0002КЕ\u0003\u0002\u0002\u0002КЛ\u0003\u0002\u0002\u0002Лy\u0003\u0002\u0002\u0002МН\u0007\u0085\u0002\u0002НО\u0005Þp\u0002О{\u0003\u0002\u0002\u0002ПС\u0007\u0099\u0002\u0002РТ\u0007z\u0002\u0002СР\u0003\u0002\u0002\u0002СТ\u0003\u0002\u0002\u0002ТУ\u0003\u0002\u0002\u0002УШ\u0005¢R\u0002ФХ\u0007\u000b\u0002\u0002ХЧ\u0005¢R\u0002ЦФ\u0003\u0002\u0002\u0002ЧЪ\u0003\u0002\u0002\u0002ШЦ\u0003\u0002\u0002\u0002ШЩ\u0003\u0002\u0002\u0002ЩЬ\u0003\u0002\u0002\u0002ЪШ\u0003\u0002\u0002\u0002ЫП\u0003\u0002\u0002\u0002ЫЬ\u0003\u0002\u0002\u0002ЬЭ\u0003\u0002\u0002\u0002Эи\u0005®X\u0002ЮЯ\u0007s\u0002\u0002Яа\u0007.\u0002\u0002ае\u0005\u009eP\u0002бв\u0007\u000b\u0002\u0002вд\u0005\u009eP\u0002гб\u0003\u0002\u0002\u0002дз\u0003\u0002\u0002\u0002ег\u0003\u0002\u0002\u0002еж\u0003\u0002\u0002\u0002жй\u0003\u0002\u0002\u0002зе\u0003\u0002\u0002\u0002иЮ\u0003\u0002\u0002\u0002ий\u0003\u0002\u0002\u0002йр\u0003\u0002\u0002\u0002кл\u0007h\u0002\u0002ло\u0005\u0090I\u0002мн\t\u0007\u0002\u0002нп\u0005\u0090I\u0002ом\u0003\u0002\u0002\u0002оп\u0003\u0002\u0002\u0002пс\u0003\u0002\u0002\u0002рк\u0003\u0002\u0002\u0002рс\u0003\u0002\u0002\u0002с}\u0003\u0002\u0002\u0002тф\u0007\u0099\u0002\u0002ух\u0007z\u0002\u0002фу\u0003\u0002\u0002\u0002фх\u0003\u0002\u0002\u0002хц\u0003\u0002\u0002\u0002цы\u0005¢R\u0002чш\u0007\u000b\u0002\u0002шъ\u0005¢R\u0002щч\u0003\u0002\u0002\u0002ъэ\u0003\u0002\u0002\u0002ыщ\u0003\u0002\u0002\u0002ыь\u0003\u0002\u0002\u0002ья\u0003\u0002\u0002\u0002эы\u0003\u0002\u0002\u0002ют\u0003\u0002\u0002\u0002юя\u0003\u0002\u0002\u0002яѐ\u0003\u0002\u0002\u0002ѐі\u0005\u0080A\u0002ёђ\u0005°Y\u0002ђѓ\u0005\u0080A\u0002ѓѕ\u0003\u0002\u0002\u0002єё\u0003\u0002\u0002\u0002ѕј\u0003\u0002\u0002\u0002іє\u0003\u0002\u0002\u0002ії\u0003\u0002\u0002\u0002їѣ\u0003\u0002\u0002\u0002јі\u0003\u0002\u0002\u0002љњ\u0007s\u0002\u0002њћ\u0007.\u0002\u0002ћѠ\u0005\u009eP\u0002ќѝ\u0007\u000b\u0002\u0002ѝџ\u0005\u009eP\u0002ўќ\u0003\u0002\u0002\u0002џѢ\u0003\u0002\u0002\u0002Ѡў\u0003\u0002\u0002\u0002Ѡѡ\u0003\u0002\u0002\u0002ѡѤ\u0003\u0002\u0002\u0002ѢѠ\u0003\u0002\u0002\u0002ѣљ\u0003\u0002\u0002\u0002ѣѤ\u0003\u0002\u0002\u0002Ѥѫ\u0003\u0002\u0002\u0002ѥѦ\u0007h\u0002\u0002Ѧѩ\u0005\u0090I\u0002ѧѨ\t\u0007\u0002\u0002ѨѪ\u0005\u0090I\u0002ѩѧ\u0003\u0002\u0002\u0002ѩѪ\u0003\u0002\u0002\u0002ѪѬ\u0003\u0002\u0002\u0002ѫѥ\u0003\u0002\u0002\u0002ѫѬ\u0003\u0002\u0002\u0002Ѭ\u007f\u0003\u0002\u0002\u0002ѭѯ\u0007\u0086\u0002\u0002ѮѰ\t\t\u0002\u0002ѯѮ\u0003\u0002\u0002\u0002ѯѰ\u0003\u0002\u0002\u0002Ѱѱ\u0003\u0002\u0002\u0002ѱѶ\u0005¤S\u0002Ѳѳ\u0007\u000b\u0002\u0002ѳѵ\u0005¤S\u0002ѴѲ\u0003\u0002\u0002\u0002ѵѸ\u0003\u0002\u0002\u0002ѶѴ\u0003\u0002\u0002\u0002Ѷѷ\u0003\u0002\u0002\u0002ѷ҅\u0003\u0002\u0002\u0002ѸѶ\u0003\u0002\u0002\u0002ѹ҃\u0007Q\u0002\u0002Ѻѿ\u0005¦T\u0002ѻѼ\u0007\u000b\u0002\u0002ѼѾ\u0005¦T\u0002ѽѻ\u0003\u0002\u0002\u0002Ѿҁ\u0003\u0002\u0002\u0002ѿѽ\u0003\u0002\u0002\u0002ѿҀ\u0003\u0002\u0002\u0002Ҁ҄\u0003\u0002\u0002\u0002ҁѿ\u0003\u0002\u0002\u0002҂҄\u0005¨U\u0002҃Ѻ\u0003\u0002\u0002\u0002҃҂\u0003\u0002\u0002\u0002҄҆\u0003\u0002\u0002\u0002҅ѹ\u0003\u0002\u0002\u0002҅҆\u0003\u0002\u0002\u0002҆҉\u0003\u0002\u0002\u0002҇҈\u0007\u0098\u0002\u0002҈Ҋ\u0005\u0090I\u0002҉҇\u0003\u0002\u0002\u0002҉Ҋ\u0003\u0002\u0002\u0002Ҋҙ\u0003\u0002\u0002\u0002ҋҌ\u0007T\u0002\u0002Ҍҍ\u0007.\u0002\u0002ҍҒ\u0005\u0090I\u0002Ҏҏ\u0007\u000b\u0002\u0002ҏґ\u0005\u0090I\u0002ҐҎ\u0003\u0002\u0002\u0002ґҔ\u0003\u0002\u0002\u0002ҒҐ\u0003\u0002\u0002\u0002Ғғ\u0003\u0002\u0002\u0002ғҗ\u0003\u0002\u0002\u0002ҔҒ\u0003\u0002\u0002\u0002ҕҖ\u0007U\u0002\u0002ҖҘ\u0005\u0090I\u0002җҕ\u0003\u0002\u0002\u0002җҘ\u0003\u0002\u0002\u0002ҘҚ\u0003\u0002\u0002\u0002ҙҋ\u0003\u0002\u0002\u0002ҙҚ\u0003\u0002\u0002\u0002ҚҸ\u0003\u0002\u0002\u0002қҜ\u0007\u0094\u0002\u0002Ҝҝ\u0007\t\u0002\u0002ҝҢ\u0005\u0090I\u0002Ҟҟ\u0007\u000b\u0002\u0002ҟҡ\u0005\u0090I\u0002ҠҞ\u0003\u0002\u0002\u0002ҡҤ\u0003\u0002\u0002\u0002ҢҠ\u0003\u0002\u0002\u0002Ңң\u0003\u0002\u0002\u0002ңҥ\u0003\u0002\u0002\u0002ҤҢ\u0003\u0002\u0002\u0002ҥҴ\u0007\n\u0002\u0002Ҧҧ\u0007\u000b\u0002\u0002ҧҨ\u0007\t\u0002\u0002Ҩҭ\u0005\u0090I\u0002ҩҪ\u0007\u000b\u0002\u0002ҪҬ\u0005\u0090I\u0002ҫҩ\u0003\u0002\u0002\u0002Ҭү\u0003\u0002\u0002\u0002ҭҫ\u0003\u0002\u0002\u0002ҭҮ\u0003\u0002\u0002\u0002ҮҰ\u0003\u0002\u0002\u0002үҭ\u0003\u0002\u0002\u0002Ұұ\u0007\n\u0002\u0002ұҳ\u0003\u0002\u0002\u0002ҲҦ\u0003\u0002\u0002\u0002ҳҶ\u0003\u0002\u0002\u0002ҴҲ\u0003\u0002\u0002\u0002Ҵҵ\u0003\u0002\u0002\u0002ҵҸ\u0003\u0002\u0002\u0002ҶҴ\u0003\u0002\u0002\u0002ҷѭ\u0003\u0002\u0002\u0002ҷқ\u0003\u0002\u0002\u0002Ҹ\u0081\u0003\u0002\u0002\u0002ҹһ\u0005\u009aN\u0002Һҹ\u0003\u0002\u0002\u0002Һһ\u0003\u0002\u0002\u0002һҼ\u0003\u0002\u0002\u0002ҼӇ\u0007\u0091\u0002\u0002ҽҾ\u0007r\u0002\u0002Ҿӈ\u0007\u0083\u0002\u0002ҿӀ\u0007r\u0002\u0002Ӏӈ\u0007\u001f\u0002\u0002Ӂӂ\u0007r\u0002\u0002ӂӈ\u0007\u0080\u0002\u0002Ӄӄ\u0007r\u0002\u0002ӄӈ\u0007N\u0002\u0002Ӆӆ\u0007r\u0002\u0002ӆӈ\u0007W\u0002\u0002Ӈҽ\u0003\u0002\u0002\u0002Ӈҿ\u0003\u0002\u0002\u0002ӇӁ\u0003\u0002\u0002\u0002ӇӃ\u0003\u0002\u0002\u0002ӇӅ\u0003\u0002\u0002\u0002Ӈӈ\u0003\u0002\u0002\u0002ӈӉ\u0003\u0002\u0002\u0002Ӊӊ\u0005\u009cO\u0002ӊӋ\u0007\u0087\u0002\u0002Ӌӌ\u0005Îh\u0002ӌӍ\u0007\f\u0002\u0002Ӎӕ\u0005\u0090I\u0002ӎӏ\u0007\u000b\u0002\u0002ӏӐ\u0005Îh\u0002Ӑӑ\u0007\f\u0002\u0002ӑӒ\u0005\u0090I\u0002ӒӔ\u0003\u0002\u0002\u0002ӓӎ\u0003\u0002\u0002\u0002Ӕӗ\u0003\u0002\u0002\u0002ӕӓ\u0003\u0002\u0002\u0002ӕӖ\u0003\u0002\u0002\u0002ӖӚ\u0003\u0002\u0002\u0002ӗӕ\u0003\u0002\u0002\u0002Әә\u0007\u0098\u0002\u0002әӛ\u0005\u0090I\u0002ӚӘ\u0003\u0002\u0002\u0002Ӛӛ\u0003\u0002\u0002\u0002ӛ\u0083\u0003\u0002\u0002\u0002ӜӞ\u0005\u009aN\u0002ӝӜ\u0003\u0002\u0002\u0002ӝӞ\u0003\u0002\u0002\u0002Ӟӟ\u0003\u0002\u0002\u0002ӟӪ\u0007\u0091\u0002\u0002Ӡӡ\u0007r\u0002\u0002ӡӫ\u0007\u0083\u0002\u0002Ӣӣ\u0007r\u0002\u0002ӣӫ\u0007\u001f\u0002\u0002Ӥӥ\u0007r\u0002\u0002ӥӫ\u0007\u0080\u0002\u0002Ӧӧ\u0007r\u0002\u0002ӧӫ\u0007N\u0002\u0002Өө\u0007r\u0002\u0002өӫ\u0007W\u0002\u0002ӪӠ\u0003\u0002\u0002\u0002ӪӢ\u0003\u0002\u0002\u0002ӪӤ\u0003\u0002\u0002\u0002ӪӦ\u0003\u0002\u0002\u0002ӪӨ\u0003\u0002\u0002\u0002Ӫӫ\u0003\u0002\u0002\u0002ӫӬ\u0003\u0002\u0002\u0002Ӭӭ\u0005\u009cO\u0002ӭӮ\u0007\u0087\u0002\u0002Ӯӯ\u0005Îh\u0002ӯӰ\u0007\f\u0002\u0002ӰӸ\u0005\u0090I\u0002ӱӲ\u0007\u000b\u0002\u0002Ӳӳ\u0005Îh\u0002ӳӴ\u0007\f\u0002\u0002Ӵӵ\u0005\u0090I\u0002ӵӷ\u0003\u0002\u0002\u0002Ӷӱ\u0003\u0002\u0002\u0002ӷӺ\u0003\u0002\u0002\u0002ӸӶ\u0003\u0002\u0002\u0002Ӹӹ\u0003\u0002\u0002\u0002ӹӽ\u0003\u0002\u0002\u0002ӺӸ\u0003\u0002\u0002\u0002ӻӼ\u0007\u0098\u0002\u0002ӼӾ\u0005\u0090I\u0002ӽӻ\u0003\u0002\u0002\u0002ӽӾ\u0003\u0002\u0002\u0002Ӿԑ\u0003\u0002\u0002\u0002ӿԀ\u0007s\u0002\u0002Ԁԁ\u0007.\u0002\u0002ԁԆ\u0005\u009eP\u0002Ԃԃ\u0007\u000b\u0002\u0002ԃԅ\u0005\u009eP\u0002ԄԂ\u0003\u0002\u0002\u0002ԅԈ\u0003\u0002\u0002\u0002ԆԄ\u0003\u0002\u0002\u0002Ԇԇ\u0003\u0002\u0002\u0002ԇԊ\u0003\u0002\u0002\u0002ԈԆ\u0003\u0002\u0002\u0002ԉӿ\u0003\u0002\u0002\u0002ԉԊ\u0003\u0002\u0002\u0002Ԋԋ\u0003\u0002\u0002\u0002ԋԌ\u0007h\u0002\u0002Ԍԏ\u0005\u0090I\u0002ԍԎ\t\u0007\u0002\u0002ԎԐ\u0005\u0090I\u0002ԏԍ\u0003\u0002\u0002\u0002ԏԐ\u0003\u0002\u0002\u0002ԐԒ\u0003\u0002\u0002\u0002ԑԉ\u0003\u0002\u0002\u0002ԑԒ\u0003\u0002\u0002\u0002Ԓ\u0085\u0003\u0002\u0002\u0002ԓԔ\u0007\u0093\u0002\u0002Ԕ\u0087\u0003\u0002\u0002\u0002ԕԗ\u0005Îh\u0002ԖԘ\u0005\u008aF\u0002ԗԖ\u0003\u0002\u0002\u0002ԗԘ\u0003\u0002\u0002\u0002ԘԜ\u0003\u0002\u0002\u0002ԙԛ\u0005\u008cG\u0002Ԛԙ\u0003\u0002\u0002\u0002ԛԞ\u0003\u0002\u0002\u0002ԜԚ\u0003\u0002\u0002\u0002Ԝԝ\u0003\u0002\u0002\u0002ԝ\u0089\u0003\u0002\u0002\u0002ԞԜ\u0003\u0002\u0002\u0002ԟԡ\u0005Âb\u0002Ԡԟ\u0003\u0002\u0002\u0002ԡԢ\u0003\u0002\u0002\u0002ԢԠ\u0003\u0002\u0002\u0002Ԣԣ\u0003\u0002\u0002\u0002ԣԮ\u0003\u0002\u0002\u0002Ԥԥ\u0007\t\u0002\u0002ԥԦ\u0005´[\u0002Ԧԧ\u0007\n\u0002\u0002ԧԯ\u0003\u0002\u0002\u0002Ԩԩ\u0007\t\u0002\u0002ԩԪ\u0005´[\u0002Ԫԫ\u0007\u000b\u0002\u0002ԫԬ\u0005´[\u0002Ԭԭ\u0007\n\u0002\u0002ԭԯ\u0003\u0002\u0002\u0002ԮԤ\u0003\u0002\u0002\u0002ԮԨ\u0003\u0002\u0002\u0002Ԯԯ\u0003\u0002\u0002\u0002ԯ\u008b\u0003\u0002\u0002\u0002\u0530Ա\u00077\u0002\u0002ԱԳ\u0005Âb\u0002Բ\u0530\u0003\u0002\u0002\u0002ԲԳ\u0003\u0002\u0002\u0002ԳՕ\u0003\u0002\u0002\u0002ԴԵ\u0007w\u0002\u0002ԵԷ\u0007e\u0002\u0002ԶԸ\t\u0004\u0002\u0002ԷԶ\u0003\u0002\u0002\u0002ԷԸ\u0003\u0002\u0002\u0002ԸԹ\u0003\u0002\u0002\u0002ԹԻ\u0005\u008eH\u0002ԺԼ\u0007*\u0002\u0002ԻԺ\u0003\u0002\u0002\u0002ԻԼ\u0003\u0002\u0002\u0002ԼՖ\u0003\u0002\u0002\u0002ԽԿ\u0007l\u0002\u0002ԾԽ\u0003\u0002\u0002\u0002ԾԿ\u0003\u0002\u0002\u0002ԿՀ\u0003\u0002\u0002\u0002ՀՁ\u0007n\u0002\u0002ՁՖ\u0005\u008eH\u0002ՂՃ\u0007\u0090\u0002\u0002ՃՖ\u0005\u008eH\u0002ՄՅ\u00072\u0002\u0002ՅՆ\u0007\t\u0002\u0002ՆՇ\u0005\u0090I\u0002ՇՈ\u0007\n\u0002\u0002ՈՖ\u0003\u0002\u0002\u0002ՉՐ\u0007>\u0002\u0002ՊՑ\u0005´[\u0002ՋՑ\u0005¶\\\u0002ՌՍ\u0007\t\u0002\u0002ՍՎ\u0005\u0090I\u0002ՎՏ\u0007\n\u0002\u0002ՏՑ\u0003\u0002\u0002\u0002ՐՊ\u0003\u0002\u0002\u0002ՐՋ\u0003\u0002\u0002\u0002ՐՌ\u0003\u0002\u0002\u0002ՑՖ\u0003\u0002\u0002\u0002ՒՓ\u00073\u0002\u0002ՓՖ\u0005Ði\u0002ՔՖ\u0005\u0092J\u0002ՕԴ\u0003\u0002\u0002\u0002ՕԾ\u0003\u0002\u0002\u0002ՕՂ\u0003\u0002\u0002\u0002ՕՄ\u0003\u0002\u0002\u0002ՕՉ\u0003\u0002\u0002\u0002ՕՒ\u0003\u0002\u0002\u0002ՕՔ\u0003\u0002\u0002\u0002Ֆ\u008d\u0003\u0002\u0002\u0002\u0557\u0558\u0007q\u0002\u0002\u0558ՙ\u00076\u0002\u0002ՙ՛\t\n\u0002\u0002՚\u0557\u0003\u0002\u0002\u0002՚՛\u0003\u0002\u0002\u0002՛\u008f\u0003\u0002\u0002\u0002՜՝\bI\u0001\u0002՝֩\u0005¶\\\u0002՞֩\u0007\u009d\u0002\u0002՟\u0560\u0005Æd\u0002\u0560ա\u0007\b\u0002\u0002ագ\u0003\u0002\u0002\u0002բ՟\u0003\u0002\u0002\u0002բգ\u0003\u0002\u0002\u0002գդ\u0003\u0002\u0002\u0002դե\u0005Èe\u0002եզ\u0007\b\u0002\u0002զը\u0003\u0002\u0002\u0002էբ\u0003\u0002\u0002\u0002էը\u0003\u0002\u0002\u0002ըթ\u0003\u0002\u0002\u0002թ֩\u0005Îh\u0002ժի\u0005¸]\u0002իլ\u0005\u0090I\u0017լ֩\u0003\u0002\u0002\u0002խծ\u0005Äc\u0002ծջ\u0007\t\u0002\u0002կձ\u0007D\u0002\u0002հկ\u0003\u0002\u0002\u0002հձ\u0003\u0002\u0002\u0002ձղ\u0003\u0002\u0002\u0002ղշ\u0005\u0090I\u0002ճմ\u0007\u000b\u0002\u0002մն\u0005\u0090I\u0002յճ\u0003\u0002\u0002\u0002նչ\u0003\u0002\u0002\u0002շյ\u0003\u0002\u0002\u0002շո\u0003\u0002\u0002\u0002ոռ\u0003\u0002\u0002\u0002չշ\u0003\u0002\u0002\u0002պռ\u0007\r\u0002\u0002ջհ\u0003\u0002\u0002\u0002ջպ\u0003\u0002\u0002\u0002ջռ\u0003\u0002\u0002\u0002ռս\u0003\u0002\u0002\u0002սվ\u0007\n\u0002\u0002վ֩\u0003\u0002\u0002\u0002տր\u0007\t\u0002\u0002րց\u0005\u0090I\u0002ցւ\u0007\n\u0002\u0002ւ֩\u0003\u0002\u0002\u0002փք\u00071\u0002\u0002քօ\u0007\t\u0002\u0002օֆ\u0005\u0090I\u0002ֆև\u0007'\u0002\u0002և\u0588\u0005\u008aF\u0002\u0588։\u0007\n\u0002\u0002։֩\u0003\u0002\u0002\u0002֊\u058c\u0007l\u0002\u0002\u058b֊\u0003\u0002\u0002\u0002\u058b\u058c\u0003\u0002\u0002\u0002\u058c֍\u0003\u0002\u0002\u0002֍֏\u0007L\u0002\u0002֎\u058b\u0003\u0002\u0002\u0002֎֏\u0003\u0002\u0002\u0002֏\u0590\u0003\u0002\u0002\u0002\u0590֑\u0007\t\u0002\u0002֑֒\u0005~@\u0002֒֓\u0007\n\u0002\u0002֓֩\u0003\u0002\u0002\u0002֖֔\u00070\u0002\u0002֕֗\u0005\u0090I\u0002֖֕\u0003\u0002\u0002\u0002֖֗\u0003\u0002\u0002\u0002֗֝\u0003\u0002\u0002\u0002֘֙\u0007\u0097\u0002\u0002֚֙\u0005\u0090I\u0002֛֚\u0007\u008b\u0002\u0002֛֜\u0005\u0090I\u0002֜֞\u0003\u0002\u0002\u0002֝֘\u0003\u0002\u0002\u0002֞֟\u0003\u0002\u0002\u0002֟֝\u0003\u0002\u0002\u0002֟֠\u0003\u0002\u0002\u0002֣֠\u0003\u0002\u0002\u0002֢֡\u0007G\u0002\u0002֢֤\u0005\u0090I\u0002֣֡\u0003\u0002\u0002\u0002֣֤\u0003\u0002\u0002\u0002֤֥\u0003\u0002\u0002\u0002֥֦\u0007H\u0002\u0002֦֩\u0003\u0002\u0002\u0002֧֩\u0005\u0094K\u0002֨՜\u0003\u0002\u0002\u0002֨՞\u0003\u0002\u0002\u0002֨է\u0003\u0002\u0002\u0002֨ժ\u0003\u0002\u0002\u0002֨խ\u0003\u0002\u0002\u0002֨տ\u0003\u0002\u0002\u0002֨փ\u0003\u0002\u0002\u0002֨֎\u0003\u0002\u0002\u0002֨֔\u0003\u0002\u0002\u0002֧֨\u0003\u0002\u0002\u0002֩؎\u0003\u0002\u0002\u0002֪֫\f\u0016\u0002\u0002֫֬\u0007\u0011\u0002\u0002֬؍\u0005\u0090I\u0017֭֮\f\u0015\u0002\u0002֮֯\t\u000b\u0002\u0002֯؍\u0005\u0090I\u0016ְֱ\f\u0014\u0002\u0002ֱֲ\t\f\u0002\u0002ֲ؍\u0005\u0090I\u0015ֳִ\f\u0013\u0002\u0002ִֵ\t\r\u0002\u0002ֵ؍\u0005\u0090I\u0014ֶַ\f\u0012\u0002\u0002ַָ\t\u000e\u0002\u0002ָ؍\u0005\u0090I\u0013ֹ׆\f\u0011\u0002\u0002ׇֺ\u0007\f\u0002\u0002ׇֻ\u0007\u001c\u0002\u0002ׇּ\u0007\u001d\u0002\u0002ׇֽ\u0007\u001e\u0002\u0002־ׇ\u0007b\u0002\u0002ֿ׀\u0007b\u0002\u0002׀ׇ\u0007l\u0002\u0002ׇׁ\u0007Y\u0002\u0002ׇׂ\u0007g\u0002\u0002׃ׇ\u0007S\u0002\u0002ׇׄ\u0007i\u0002\u0002ׇׅ\u0007|\u0002\u0002׆ֺ\u0003\u0002\u0002\u0002׆ֻ\u0003\u0002\u0002\u0002׆ּ\u0003\u0002\u0002\u0002׆ֽ\u0003\u0002\u0002\u0002׆־\u0003\u0002\u0002\u0002׆ֿ\u0003\u0002\u0002\u0002׆ׁ\u0003\u0002\u0002\u0002׆ׂ\u0003\u0002\u0002\u0002׆׃\u0003\u0002\u0002\u0002׆ׄ\u0003\u0002\u0002\u0002׆ׅ\u0003\u0002\u0002\u0002ׇ\u05c8\u0003\u0002\u0002\u0002\u05c8؍\u0005\u0090I\u0012\u05c9\u05ca\f\u0010\u0002\u0002\u05ca\u05cb\u0007&\u0002\u0002\u05cb؍\u0005\u0090I\u0011\u05cc\u05cd\f\u000f\u0002\u0002\u05cd\u05ce\u0007r\u0002\u0002\u05ce؍\u0005\u0090I\u0010\u05cfא\f\b\u0002\u0002אג\u0007b\u0002\u0002בד\u0007l\u0002\u0002גב\u0003\u0002\u0002\u0002גד\u0003\u0002\u0002\u0002דה\u0003\u0002\u0002\u0002ה؍\u0005\u0090I\tוח\f\u0007\u0002\u0002זט\u0007l\u0002\u0002חז\u0003\u0002\u0002\u0002חט\u0003\u0002\u0002\u0002טי\u0003\u0002\u0002\u0002יך\u0007-\u0002\u0002ךכ\u0005\u0090I\u0002כל\u0007&\u0002\u0002לם\u0005\u0090I\bם؍\u0003\u0002\u0002\u0002מן\f\u000b\u0002\u0002ןנ\u00073\u0002\u0002נ؍\u0005Ði\u0002סף\f\n\u0002\u0002עפ\u0007l\u0002\u0002ףע\u0003\u0002\u0002\u0002ףפ\u0003\u0002\u0002\u0002פץ\u0003\u0002\u0002\u0002ץצ\t\u000f\u0002\u0002צש\u0005\u0090I\u0002קר\u0007I\u0002\u0002רת\u0005\u0090I\u0002שק\u0003\u0002\u0002\u0002שת\u0003\u0002\u0002\u0002ת؍\u0003\u0002\u0002\u0002\u05ebװ\f\t\u0002\u0002\u05ecױ\u0007c\u0002\u0002\u05edױ\u0007m\u0002\u0002\u05ee\u05ef\u0007l\u0002\u0002\u05efױ\u0007n\u0002\u0002װ\u05ec\u0003\u0002\u0002\u0002װ\u05ed\u0003\u0002\u0002\u0002װ\u05ee\u0003\u0002\u0002\u0002ױ؍\u0003\u0002\u0002\u0002ײ״\f\u0006\u0002\u0002׳\u05f5\u0007l\u0002\u0002״׳\u0003\u0002\u0002\u0002״\u05f5\u0003\u0002\u0002\u0002\u05f5\u05f6\u0003\u0002\u0002\u0002\u05f6؊\u0007Y\u0002\u0002\u05f7\u0601\u0007\t\u0002\u0002\u05f8\u0602\u0005~@\u0002\u05f9\u05fe\u0005\u0090I\u0002\u05fa\u05fb\u0007\u000b\u0002\u0002\u05fb\u05fd\u0005\u0090I\u0002\u05fc\u05fa\u0003\u0002\u0002\u0002\u05fd\u0600\u0003\u0002\u0002\u0002\u05fe\u05fc\u0003\u0002\u0002\u0002\u05fe\u05ff\u0003\u0002\u0002\u0002\u05ff\u0602\u0003\u0002\u0002\u0002\u0600\u05fe\u0003\u0002\u0002\u0002\u0601\u05f8\u0003\u0002\u0002\u0002\u0601\u05f9\u0003\u0002\u0002\u0002\u0601\u0602\u0003\u0002\u0002\u0002\u0602\u0603\u0003\u0002\u0002\u0002\u0603؋\u0007\n\u0002\u0002\u0604\u0605\u0005Æd\u0002\u0605؆\u0007\b\u0002\u0002؆؈\u0003\u0002\u0002\u0002؇\u0604\u0003\u0002\u0002\u0002؇؈\u0003\u0002\u0002\u0002؈؉\u0003\u0002\u0002\u0002؉؋\u0005Èe\u0002؊\u05f7\u0003\u0002\u0002\u0002؊؇\u0003\u0002\u0002\u0002؋؍\u0003\u0002\u0002\u0002،֪\u0003\u0002\u0002\u0002،֭\u0003\u0002\u0002\u0002،ְ\u0003\u0002\u0002\u0002،ֳ\u0003\u0002\u0002\u0002،ֶ\u0003\u0002\u0002\u0002،ֹ\u0003\u0002\u0002\u0002،\u05c9\u0003\u0002\u0002\u0002،\u05cc\u0003\u0002\u0002\u0002،\u05cf\u0003\u0002\u0002\u0002،ו\u0003\u0002\u0002\u0002،מ\u0003\u0002\u0002\u0002،ס\u0003\u0002\u0002\u0002،\u05eb\u0003\u0002\u0002\u0002،ײ\u0003\u0002\u0002\u0002؍ؐ\u0003\u0002\u0002\u0002؎،\u0003\u0002\u0002\u0002؎؏\u0003\u0002\u0002\u0002؏\u0091\u0003\u0002\u0002\u0002ؐ؎\u0003\u0002\u0002\u0002ؑؒ\u0007{\u0002\u0002ؒ؞\u0005Òj\u0002ؓؔ\u0007\t\u0002\u0002ؙؔ\u0005Îh\u0002ؕؖ\u0007\u000b\u0002\u0002ؘؖ\u0005Îh\u0002ؗؕ\u0003\u0002\u0002\u0002ؘ؛\u0003\u0002\u0002\u0002ؙؗ\u0003\u0002\u0002\u0002ؙؚ\u0003\u0002\u0002\u0002ؚ\u061c\u0003\u0002\u0002\u0002؛ؙ\u0003\u0002\u0002\u0002\u061c\u061d\u0007\n\u0002\u0002\u061d؟\u0003\u0002\u0002\u0002؞ؓ\u0003\u0002\u0002\u0002؞؟\u0003\u0002\u0002\u0002؟ز\u0003\u0002\u0002\u0002ؠء\u0007q\u0002\u0002ءت\t\u0010\u0002\u0002آأ\u0007\u0087\u0002\u0002أث\u0007n\u0002\u0002ؤإ\u0007\u0087\u0002\u0002إث\u0007>\u0002\u0002ئث\u0007/\u0002\u0002اث\u0007\u0081\u0002\u0002بة\u0007k\u0002\u0002ةث\u0007 \u0002\u0002تآ\u0003\u0002\u0002\u0002تؤ\u0003\u0002\u0002\u0002تئ\u0003\u0002\u0002\u0002تا\u0003\u0002\u0002\u0002تب\u0003\u0002\u0002\u0002ثد\u0003\u0002\u0002\u0002جح\u0007i\u0002\u0002حد\u0005Âb\u0002خؠ\u0003\u0002\u0002\u0002خج\u0003\u0002\u0002\u0002در\u0003\u0002\u0002\u0002ذخ\u0003\u0002\u0002\u0002رش\u0003\u0002\u0002\u0002زذ\u0003\u0002\u0002\u0002زس\u0003\u0002\u0002\u0002سؿ\u0003\u0002\u0002\u0002شز\u0003\u0002\u0002\u0002صط\u0007l\u0002\u0002ضص\u0003\u0002\u0002\u0002ضط\u0003\u0002\u0002\u0002طظ\u0003\u0002\u0002\u0002ظؽ\u0007?\u0002\u0002عغ\u0007\\\u0002\u0002غؾ\u0007@\u0002\u0002ػؼ\u0007\\\u0002\u0002ؼؾ\u0007X\u0002\u0002ؽع\u0003\u0002\u0002\u0002ؽػ\u0003\u0002\u0002\u0002ؽؾ\u0003\u0002\u0002\u0002ؾـ\u0003\u0002\u0002\u0002ؿض\u0003\u0002\u0002\u0002ؿـ\u0003\u0002\u0002\u0002ـ\u0093\u0003\u0002\u0002\u0002فق\u0007y\u0002\u0002قه\u0007\t\u0002\u0002كو\u0007W\u0002\u0002لم\t\u0011\u0002\u0002من\u0007\u000b\u0002\u0002نو\u0005º^\u0002هك\u0003\u0002\u0002\u0002هل\u0003\u0002\u0002\u0002وى\u0003\u0002\u0002\u0002ىي\u0007\n\u0002\u0002ي\u0095\u0003\u0002\u0002\u0002ًَ\u0005Îh\u0002ٌٍ\u00073\u0002\u0002ٍُ\u0005Ði\u0002ٌَ\u0003\u0002\u0002\u0002َُ\u0003\u0002\u0002\u0002ُّ\u0003\u0002\u0002\u0002ِْ\t\u0004\u0002\u0002ِّ\u0003\u0002\u0002\u0002ّْ\u0003\u0002\u0002\u0002ْ\u0097\u0003\u0002\u0002\u0002ٓٔ\u00077\u0002\u0002ٖٔ\u0005Âb\u0002ٕٓ\u0003\u0002\u0002\u0002ٕٖ\u0003\u0002\u0002\u0002ٖٻ\u0003\u0002\u0002\u0002ٗ٘\u0007w\u0002\u0002٘ٛ\u0007e\u0002\u0002ٙٛ\u0007\u0090\u0002\u0002ٚٗ\u0003\u0002\u0002\u0002ٚٙ\u0003\u0002\u0002\u0002ٜٛ\u0003\u0002\u0002\u0002ٜٝ\u0007\t\u0002\u0002ٝ٢\u0005\u0096L\u0002ٟٞ\u0007\u000b\u0002\u0002ٟ١\u0005\u0096L\u0002٠ٞ\u0003\u0002\u0002\u0002١٤\u0003\u0002\u0002\u0002٢٠\u0003\u0002\u0002\u0002٢٣\u0003\u0002\u0002\u0002٣٥\u0003\u0002\u0002\u0002٤٢\u0003\u0002\u0002\u0002٥٦\u0007\n\u0002\u0002٦٧\u0005\u008eH\u0002٧ټ\u0003\u0002\u0002\u0002٨٩\u00072\u0002\u0002٩٪\u0007\t\u0002\u0002٪٫\u0005\u0090I\u0002٫٬\u0007\n\u0002\u0002٬ټ\u0003\u0002\u0002\u0002٭ٮ\u0007P\u0002\u0002ٮٯ\u0007e\u0002\u0002ٯٰ\u0007\t\u0002\u0002ٰٵ\u0005Îh\u0002ٱٲ\u0007\u000b\u0002\u0002ٲٴ\u0005Îh\u0002ٳٱ\u0003\u0002\u0002\u0002ٴٷ\u0003\u0002\u0002\u0002ٵٳ\u0003\u0002\u0002\u0002ٵٶ\u0003\u0002\u0002\u0002ٶٸ\u0003\u0002\u0002\u0002ٷٵ\u0003\u0002\u0002\u0002ٸٹ\u0007\n\u0002\u0002ٹٺ\u0005\u0092J\u0002ٺټ\u0003\u0002\u0002\u0002ٻٚ\u0003\u0002\u0002\u0002ٻ٨\u0003\u0002\u0002\u0002ٻ٭\u0003\u0002\u0002\u0002ټ\u0099\u0003\u0002\u0002\u0002ٽٿ\u0007\u0099\u0002\u0002پڀ\u0007z\u0002\u0002ٿپ\u0003\u0002\u0002\u0002ٿڀ\u0003\u0002\u0002\u0002ڀځ\u0003\u0002\u0002\u0002ځڂ\u0005²Z\u0002ڂڃ\u0007'\u0002\u0002ڃڄ\u0007\t\u0002\u0002ڄڅ\u0005~@\u0002څڏ\u0007\n\u0002\u0002چڇ\u0007\u000b\u0002\u0002ڇڈ\u0005²Z\u0002ڈډ\u0007'\u0002\u0002ډڊ\u0007\t\u0002\u0002ڊڋ\u0005~@\u0002ڋڌ\u0007\n\u0002\u0002ڌڎ\u0003\u0002\u0002\u0002ڍچ\u0003\u0002\u0002\u0002ڎڑ\u0003\u0002\u0002\u0002ڏڍ\u0003\u0002\u0002\u0002ڏڐ\u0003\u0002\u0002\u0002ڐ\u009b\u0003\u0002\u0002\u0002ڑڏ\u0003\u0002\u0002\u0002ڒړ\u0005Æd\u0002ړڔ\u0007\b\u0002\u0002ڔږ\u0003\u0002\u0002\u0002ڕڒ\u0003\u0002\u0002\u0002ڕږ\u0003\u0002\u0002\u0002ږڗ\u0003\u0002\u0002\u0002ڗڝ\u0005Èe\u0002ژڙ\u0007[\u0002\u0002ڙښ\u0007.\u0002\u0002ښڞ\u0005Ôk\u0002ڛڜ\u0007l\u0002\u0002ڜڞ\u0007[\u0002\u0002ڝژ\u0003\u0002\u0002\u0002ڝڛ\u0003\u0002\u0002\u0002ڝڞ\u0003\u0002\u0002\u0002ڞ\u009d\u0003\u0002\u0002\u0002ڟڢ\u0005\u0090I\u0002ڠڡ\u00073\u0002\u0002ڡڣ\u0005Ði\u0002ڢڠ\u0003\u0002\u0002\u0002ڢڣ\u0003\u0002\u0002\u0002ڣڥ\u0003\u0002\u0002\u0002ڤڦ\t\u0004\u0002\u0002ڥڤ\u0003\u0002\u0002\u0002ڥڦ\u0003\u0002\u0002\u0002ڦ\u009f\u0003\u0002\u0002\u0002ڧګ\u0005´[\u0002ڨګ\u0005Âb\u0002کګ\u0007\u009e\u0002\u0002ڪڧ\u0003\u0002\u0002\u0002ڪڨ\u0003\u0002\u0002\u0002ڪک\u0003\u0002\u0002\u0002ګ¡\u0003\u0002\u0002\u0002ڬڸ\u0005Èe\u0002ڭڮ\u0007\t\u0002\u0002ڮڳ\u0005Îh\u0002گڰ\u0007\u000b\u0002\u0002ڰڲ\u0005Îh\u0002ڱگ\u0003\u0002\u0002\u0002ڲڵ\u0003\u0002\u0002\u0002ڳڱ\u0003\u0002\u0002\u0002ڳڴ\u0003\u0002\u0002\u0002ڴڶ\u0003\u0002\u0002\u0002ڵڳ\u0003\u0002\u0002\u0002ڶڷ\u0007\n\u0002\u0002ڷڹ\u0003\u0002\u0002\u0002ڸڭ\u0003\u0002\u0002\u0002ڸڹ\u0003\u0002\u0002\u0002ڹں\u0003\u0002\u0002\u0002ںڻ\u0007'\u0002\u0002ڻڼ\u0007\t\u0002\u0002ڼڽ\u0005~@\u0002ڽھ\u0007\n\u0002\u0002ھ£\u0003\u0002\u0002\u0002ڿی\u0007\r\u0002\u0002ۀہ\u0005Èe\u0002ہۂ\u0007\b\u0002\u0002ۂۃ\u0007\r\u0002\u0002ۃی\u0003\u0002\u0002\u0002ۄۉ\u0005\u0090I\u0002ۅۇ\u0007'\u0002\u0002ۆۅ\u0003\u0002\u0002\u0002ۆۇ\u0003\u0002\u0002\u0002ۇۈ\u0003\u0002\u0002\u0002ۈۊ\u0005¾`\u0002ۉۆ\u0003\u0002\u0002\u0002ۉۊ\u0003\u0002\u0002\u0002ۊی\u0003\u0002\u0002\u0002ۋڿ\u0003\u0002\u0002\u0002ۋۀ\u0003\u0002\u0002\u0002ۋۄ\u0003\u0002\u0002\u0002ی¥\u0003\u0002\u0002\u0002ۍێ\u0005Æd\u0002ێۏ\u0007\b\u0002\u0002ۏۑ\u0003\u0002\u0002\u0002ېۍ\u0003\u0002\u0002\u0002ېۑ\u0003\u0002\u0002\u0002ۑے\u0003\u0002\u0002\u0002ےۗ\u0005Èe\u0002ۓە\u0007'\u0002\u0002۔ۓ\u0003\u0002\u0002\u0002۔ە\u0003\u0002\u0002\u0002ەۖ\u0003\u0002\u0002\u0002ۖۘ\u0005àq\u0002ۗ۔\u0003\u0002\u0002\u0002ۗۘ\u0003\u0002\u0002\u0002ۘ۞\u0003\u0002\u0002\u0002ۙۚ\u0007[\u0002\u0002ۚۛ\u0007.\u0002\u0002ۛ۟\u0005Ôk\u0002ۜ\u06dd\u0007l\u0002\u0002\u06dd۟\u0007[\u0002\u0002۞ۙ\u0003\u0002\u0002\u0002۞ۜ\u0003\u0002\u0002\u0002۞۟\u0003\u0002\u0002\u0002۟۽\u0003\u0002\u0002\u0002۪۠\u0007\t\u0002\u0002ۡۦ\u0005¦T\u0002ۣۢ\u0007\u000b\u0002\u0002ۣۥ\u0005¦T\u0002ۤۢ\u0003\u0002\u0002\u0002ۥۨ\u0003\u0002\u0002\u0002ۦۤ\u0003\u0002\u0002\u0002ۦۧ\u0003\u0002\u0002\u0002ۧ۫\u0003\u0002\u0002\u0002ۨۦ\u0003\u0002\u0002\u0002۩۫\u0005¨U\u0002۪ۡ\u0003\u0002\u0002\u0002۪۩\u0003\u0002\u0002\u0002۫۬\u0003\u0002\u0002\u0002۬۱\u0007\n\u0002\u0002ۭۯ\u0007'\u0002\u0002ۮۭ\u0003\u0002\u0002\u0002ۮۯ\u0003\u0002\u0002\u0002ۯ۰\u0003\u0002\u0002\u0002۰۲\u0005àq\u0002۱ۮ\u0003\u0002\u0002\u0002۱۲\u0003\u0002\u0002\u0002۲۽\u0003\u0002\u0002\u0002۳۴\u0007\t\u0002\u0002۴۵\u0005~@\u0002۵ۺ\u0007\n\u0002\u0002۶۸\u0007'\u0002\u0002۷۶\u0003\u0002\u0002\u0002۷۸\u0003\u0002\u0002\u0002۸۹\u0003\u0002\u0002\u0002۹ۻ\u0005àq\u0002ۺ۷\u0003\u0002\u0002\u0002ۺۻ\u0003\u0002\u0002\u0002ۻ۽\u0003\u0002\u0002\u0002ۼې\u0003\u0002\u0002\u0002ۼ۠\u0003\u0002\u0002\u0002ۼ۳\u0003\u0002\u0002\u0002۽§\u0003\u0002\u0002\u0002۾܅\u0005¦T\u0002ۿ܀\u0005ªV\u0002܀܁\u0005¦T\u0002܁܂\u0005¬W\u0002܂܄\u0003\u0002\u0002\u0002܃ۿ\u0003\u0002\u0002\u0002܄܇\u0003\u0002\u0002\u0002܅܃\u0003\u0002\u0002\u0002܅܆\u0003\u0002\u0002\u0002܆©\u0003\u0002\u0002\u0002܇܅\u0003\u0002\u0002\u0002܈ܖ\u0007\u000b\u0002\u0002܉܋\u0007j\u0002\u0002܊܉\u0003\u0002\u0002\u0002܊܋\u0003\u0002\u0002\u0002܋ܒ\u0003\u0002\u0002\u0002܌\u070e\u0007f\u0002\u0002܍\u070f\u0007t\u0002\u0002\u070e܍\u0003\u0002\u0002\u0002\u070e\u070f\u0003\u0002\u0002\u0002\u070fܓ\u0003\u0002\u0002\u0002ܐܓ\u0007]\u0002\u0002ܑܓ\u00079\u0002\u0002ܒ܌\u0003\u0002\u0002\u0002ܒܐ\u0003\u0002\u0002\u0002ܒܑ\u0003\u0002\u0002\u0002ܒܓ\u0003\u0002\u0002\u0002ܓܔ\u0003\u0002\u0002\u0002ܔܖ\u0007d\u0002\u0002ܕ܈\u0003\u0002\u0002\u0002ܕ܊\u0003\u0002\u0002\u0002ܖ«\u0003\u0002\u0002\u0002ܗܘ\u0007q\u0002\u0002ܘܦ\u0005\u0090I\u0002ܙܚ\u0007\u0092\u0002\u0002ܚܛ\u0007\t\u0002\u0002ܛܠ\u0005Îh\u0002ܜܝ\u0007\u000b\u0002\u0002ܝܟ\u0005Îh\u0002ܞܜ\u0003\u0002\u0002\u0002ܟܢ\u0003\u0002\u0002\u0002ܠܞ\u0003\u0002\u0002\u0002ܠܡ\u0003\u0002\u0002\u0002ܡܣ\u0003\u0002\u0002\u0002ܢܠ\u0003\u0002\u0002\u0002ܣܤ\u0007\n\u0002\u0002ܤܦ\u0003\u0002\u0002\u0002ܥܗ\u0003\u0002\u0002\u0002ܥܙ\u0003\u0002\u0002\u0002ܥܦ\u0003\u0002\u0002\u0002ܦ\u00ad\u0003\u0002\u0002\u0002ܧܩ\u0007\u0086\u0002\u0002ܨܪ\t\t\u0002\u0002ܩܨ\u0003\u0002\u0002\u0002ܩܪ\u0003\u0002\u0002\u0002ܪܫ\u0003\u0002\u0002\u0002ܫܰ\u0005¤S\u0002ܬܭ\u0007\u000b\u0002\u0002ܭܯ\u0005¤S\u0002ܮܬ\u0003\u0002\u0002\u0002ܯܲ\u0003\u0002\u0002\u0002ܰܮ\u0003\u0002\u0002\u0002ܱܰ\u0003\u0002\u0002\u0002ܱܿ\u0003\u0002\u0002\u0002ܲܰ\u0003\u0002\u0002\u0002ܳܽ\u0007Q\u0002\u0002ܴܹ\u0005¦T\u0002ܵܶ\u0007\u000b\u0002\u0002ܸܶ\u0005¦T\u0002ܷܵ\u0003\u0002\u0002\u0002ܸܻ\u0003\u0002\u0002\u0002ܹܷ\u0003\u0002\u0002\u0002ܹܺ\u0003\u0002\u0002\u0002ܾܺ\u0003\u0002\u0002\u0002ܻܹ\u0003\u0002\u0002\u0002ܼܾ\u0005¨U\u0002ܴܽ\u0003\u0002\u0002\u0002ܼܽ\u0003\u0002\u0002\u0002ܾ݀\u0003\u0002\u0002\u0002ܿܳ\u0003\u0002\u0002\u0002ܿ݀\u0003\u0002\u0002\u0002݀݃\u0003\u0002\u0002\u0002݂݁\u0007\u0098\u0002\u0002݂݄\u0005\u0090I\u0002݃݁\u0003\u0002\u0002\u0002݄݃\u0003\u0002\u0002\u0002݄ݓ\u0003\u0002\u0002\u0002݆݅\u0007T\u0002\u0002݆݇\u0007.\u0002\u0002݇\u074c\u0005\u0090I\u0002݈݉\u0007\u000b\u0002\u0002݉\u074b\u0005\u0090I\u0002݈݊\u0003\u0002\u0002\u0002\u074bݎ\u0003\u0002\u0002\u0002\u074c݊\u0003\u0002\u0002\u0002\u074cݍ\u0003\u0002\u0002\u0002ݍݑ\u0003\u0002\u0002\u0002ݎ\u074c\u0003\u0002\u0002\u0002ݏݐ\u0007U\u0002\u0002ݐݒ\u0005\u0090I\u0002ݑݏ\u0003\u0002\u0002\u0002ݑݒ\u0003\u0002\u0002\u0002ݒݔ\u0003\u0002\u0002\u0002ݓ݅\u0003\u0002\u0002\u0002ݓݔ\u0003\u0002\u0002\u0002ݔݲ\u0003\u0002\u0002\u0002ݕݖ\u0007\u0094\u0002\u0002ݖݗ\u0007\t\u0002\u0002ݗݜ\u0005\u0090I\u0002ݘݙ\u0007\u000b\u0002\u0002ݙݛ\u0005\u0090I\u0002ݚݘ\u0003\u0002\u0002\u0002ݛݞ\u0003\u0002\u0002\u0002ݜݚ\u0003\u0002\u0002\u0002ݜݝ\u0003\u0002\u0002\u0002ݝݟ\u0003\u0002\u0002\u0002ݞݜ\u0003\u0002\u0002\u0002ݟݮ\u0007\n\u0002\u0002ݠݡ\u0007\u000b\u0002\u0002ݡݢ\u0007\t\u0002\u0002ݢݧ\u0005\u0090I\u0002ݣݤ\u0007\u000b\u0002\u0002ݤݦ\u0005\u0090I\u0002ݥݣ\u0003\u0002\u0002\u0002ݦݩ\u0003\u0002\u0002\u0002ݧݥ\u0003\u0002\u0002\u0002ݧݨ\u0003\u0002\u0002\u0002ݨݪ\u0003\u0002\u0002\u0002ݩݧ\u0003\u0002\u0002\u0002ݪݫ\u0007\n\u0002\u0002ݫݭ\u0003\u0002\u0002\u0002ݬݠ\u0003\u0002\u0002\u0002ݭݰ\u0003\u0002\u0002\u0002ݮݬ\u0003\u0002\u0002\u0002ݮݯ\u0003\u0002\u0002\u0002ݯݲ\u0003\u0002\u0002\u0002ݰݮ\u0003\u0002\u0002\u0002ݱܧ\u0003\u0002\u0002\u0002ݱݕ\u0003\u0002\u0002\u0002ݲ¯\u0003\u0002\u0002\u0002ݳݹ\u0007\u008f\u0002\u0002ݴݵ\u0007\u008f\u0002\u0002ݵݹ\u0007#\u0002\u0002ݶݹ\u0007`\u0002\u0002ݷݹ\u0007J\u0002\u0002ݸݳ\u0003\u0002\u0002\u0002ݸݴ\u0003\u0002\u0002\u0002ݸݶ\u0003\u0002\u0002\u0002ݸݷ\u0003\u0002\u0002\u0002ݹ±\u0003\u0002\u0002\u0002ݺކ\u0005Èe\u0002ݻݼ\u0007\t\u0002\u0002ݼށ\u0005Îh\u0002ݽݾ\u0007\u000b\u0002\u0002ݾހ\u0005Îh\u0002ݿݽ\u0003\u0002\u0002\u0002ހރ\u0003\u0002\u0002\u0002ށݿ\u0003\u0002\u0002\u0002ށނ\u0003\u0002\u0002\u0002ނބ\u0003\u0002\u0002\u0002ރށ\u0003\u0002\u0002\u0002ބޅ\u0007\n\u0002\u0002ޅއ\u0003\u0002\u0002\u0002ކݻ\u0003\u0002\u0002\u0002ކއ\u0003\u0002\u0002\u0002އ³\u0003\u0002\u0002\u0002ވފ\t\f\u0002\u0002މވ\u0003\u0002\u0002\u0002މފ\u0003\u0002\u0002\u0002ފދ\u0003\u0002\u0002\u0002ދތ\u0007\u009c\u0002\u0002ތµ\u0003\u0002\u0002\u0002ލގ\t\u0012\u0002\u0002ގ·\u0003\u0002\u0002\u0002ޏސ\t\u0013\u0002\u0002ސ¹\u0003\u0002\u0002\u0002ޑޒ\u0007\u009e\u0002\u0002ޒ»\u0003\u0002\u0002\u0002ޓޖ\u0005\u0090I\u0002ޔޖ\u0005\u0088E\u0002ޕޓ\u0003\u0002\u0002\u0002ޕޔ\u0003\u0002\u0002\u0002ޖ½\u0003\u0002\u0002\u0002ޗޘ\t\u0002\u0002\u0002ޘ¿\u0003\u0002\u0002\u0002ޙޚ\t\u0014\u0002\u0002ޚÁ\u0003\u0002\u0002\u0002ޛޜ\u0005äs\u0002ޜÃ\u0003\u0002\u0002\u0002ޝޞ\u0005äs\u0002ޞÅ\u0003\u0002\u0002\u0002ޟޠ\u0005äs\u0002ޠÇ\u0003\u0002\u0002\u0002ޡޢ\u0005äs\u0002ޢÉ\u0003\u0002\u0002\u0002ޣޤ\u0005äs\u0002ޤË\u0003\u0002\u0002\u0002ޥަ\u0005äs\u0002ަÍ\u0003\u0002\u0002\u0002ާި\u0005äs\u0002ިÏ\u0003\u0002\u0002\u0002ީު\u0005äs\u0002ުÑ\u0003\u0002\u0002\u0002ޫެ\u0005äs\u0002ެÓ\u0003\u0002\u0002\u0002ޭޮ\u0005äs\u0002ޮÕ\u0003\u0002\u0002\u0002ޯް\u0005äs\u0002ް×\u0003\u0002\u0002\u0002ޱ\u07b2\u0005äs\u0002\u07b2Ù\u0003\u0002\u0002\u0002\u07b3\u07b4\u0005äs\u0002\u07b4Û\u0003\u0002\u0002\u0002\u07b5\u07b6\u0005äs\u0002\u07b6Ý\u0003\u0002\u0002\u0002\u07b7\u07b8\u0005äs\u0002\u07b8ß\u0003\u0002\u0002\u0002\u07b9\u07ba\u0005äs\u0002\u07baá\u0003\u0002\u0002\u0002\u07bb\u07bc\u0005äs\u0002\u07bcã\u0003\u0002\u0002\u0002\u07bd߅\u0007\u009b\u0002\u0002\u07be߅\u0005Àa\u0002\u07bf߅\u0007\u009e\u0002\u0002߀߁\u0007\t\u0002\u0002߁߂\u0005äs\u0002߂߃\u0007\n\u0002\u0002߃߅\u0003\u0002\u0002\u0002߄\u07bd\u0003\u0002\u0002\u0002߄\u07be\u0003\u0002\u0002\u0002߄\u07bf\u0003\u0002\u0002\u0002߄߀\u0003\u0002\u0002\u0002߅å\u0003\u0002\u0002\u0002ĂëîþăĈĒğīŀůŽƣƩƫƶƽǂǈǎǐǰǷǿȂȋȏȗțȝȢȤȨȯȲȷȻɀɉɌɒɔɘɞɣɮɴɸɾʃʌʓʙʝʡʧʬʳʾˁ˃ˉˏ˓˚ˠ˦ˬ˱˽̸̜̟̦̯̲̺͓̂̍̒̾͆͋ͥͭ̕͘͠Ͳ\u0378Ϳ\u0382ΊΔΗΝΟ\u03a2ελτωϒϝϤϪϰϹЀЄІЊБГЗКСШЫеиорфыюіѠѣѩѫѯѶѿ҃҅҉ҒҗҙҢҭҴҷҺӇӕӚӝӪӸӽԆԉԏԑԗԜԢԮԲԷԻԾՐՕ՚բէհշջ\u058b֎֖֣֟֨׆גחףשװ״\u05fe\u0601؇؊،؎ؙ؞تخزضؽؿهَّٕٚ٢ٵٻٿڏڕڝڢڥڪڳڸۆۉۋې۔ۗ۞ۦ۪ۮ۱۷ۺۼ܅܊\u070eܒܕܠܥܩܹܰܽܿ݃\u074cݑݓݜݧݮݱݸށކމޕ߄".toCharArray());
      _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];

      for(i = 0; i < _ATN.getNumberOfDecisions(); ++i) {
         _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
      }

   }

   public static class Any_nameContext extends ParserRuleContext {
      public TerminalNode IDENTIFIER() {
         return this.getToken(153, 0);
      }

      public KeywordContext keyword() {
         return (KeywordContext)this.getRuleContext(KeywordContext.class, 0);
      }

      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Any_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 113;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterAny_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitAny_name(this);
         }

      }
   }

   public static class Transaction_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Transaction_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 112;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterTransaction_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitTransaction_name(this);
         }

      }
   }

   public static class Table_aliasContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Table_aliasContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 111;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterTable_alias(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitTable_alias(this);
         }

      }
   }

   public static class Savepoint_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Savepoint_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 110;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSavepoint_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSavepoint_name(this);
         }

      }
   }

   public static class Pragma_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Pragma_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 109;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterPragma_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitPragma_name(this);
         }

      }
   }

   public static class Module_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Module_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 108;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterModule_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitModule_name(this);
         }

      }
   }

   public static class View_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public View_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 107;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterView_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitView_name(this);
         }

      }
   }

   public static class Trigger_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Trigger_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 106;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterTrigger_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitTrigger_name(this);
         }

      }
   }

   public static class Index_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Index_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 105;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterIndex_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitIndex_name(this);
         }

      }
   }

   public static class Foreign_tableContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Foreign_tableContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 104;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterForeign_table(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitForeign_table(this);
         }

      }
   }

   public static class Collation_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Collation_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 103;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCollation_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCollation_name(this);
         }

      }
   }

   public static class Column_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Column_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 102;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterColumn_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitColumn_name(this);
         }

      }
   }

   public static class New_table_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public New_table_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 101;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterNew_table_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitNew_table_name(this);
         }

      }
   }

   public static class Table_or_index_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Table_or_index_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 100;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterTable_or_index_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitTable_or_index_name(this);
         }

      }
   }

   public static class Table_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Table_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 99;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterTable_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitTable_name(this);
         }

      }
   }

   public static class Database_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Database_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 98;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDatabase_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDatabase_name(this);
         }

      }
   }

   public static class Function_nameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public Function_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 97;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterFunction_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitFunction_name(this);
         }

      }
   }

   public static class NameContext extends ParserRuleContext {
      public Any_nameContext any_name() {
         return (Any_nameContext)this.getRuleContext(Any_nameContext.class, 0);
      }

      public NameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 96;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterName(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitName(this);
         }

      }
   }

   public static class KeywordContext extends ParserRuleContext {
      public TerminalNode K_ABORT() {
         return this.getToken(29, 0);
      }

      public TerminalNode K_ACTION() {
         return this.getToken(30, 0);
      }

      public TerminalNode K_ADD() {
         return this.getToken(31, 0);
      }

      public TerminalNode K_AFTER() {
         return this.getToken(32, 0);
      }

      public TerminalNode K_ALL() {
         return this.getToken(33, 0);
      }

      public TerminalNode K_ALTER() {
         return this.getToken(34, 0);
      }

      public TerminalNode K_ANALYZE() {
         return this.getToken(35, 0);
      }

      public TerminalNode K_AND() {
         return this.getToken(36, 0);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public TerminalNode K_ASC() {
         return this.getToken(38, 0);
      }

      public TerminalNode K_ATTACH() {
         return this.getToken(39, 0);
      }

      public TerminalNode K_AUTOINCREMENT() {
         return this.getToken(40, 0);
      }

      public TerminalNode K_BEFORE() {
         return this.getToken(41, 0);
      }

      public TerminalNode K_BEGIN() {
         return this.getToken(42, 0);
      }

      public TerminalNode K_BETWEEN() {
         return this.getToken(43, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public TerminalNode K_CASCADE() {
         return this.getToken(45, 0);
      }

      public TerminalNode K_CASE() {
         return this.getToken(46, 0);
      }

      public TerminalNode K_CAST() {
         return this.getToken(47, 0);
      }

      public TerminalNode K_CHECK() {
         return this.getToken(48, 0);
      }

      public TerminalNode K_COLLATE() {
         return this.getToken(49, 0);
      }

      public TerminalNode K_COLUMN() {
         return this.getToken(50, 0);
      }

      public TerminalNode K_COMMIT() {
         return this.getToken(51, 0);
      }

      public TerminalNode K_CONFLICT() {
         return this.getToken(52, 0);
      }

      public TerminalNode K_CONSTRAINT() {
         return this.getToken(53, 0);
      }

      public TerminalNode K_CREATE() {
         return this.getToken(54, 0);
      }

      public TerminalNode K_CROSS() {
         return this.getToken(55, 0);
      }

      public TerminalNode K_CURRENT_DATE() {
         return this.getToken(56, 0);
      }

      public TerminalNode K_CURRENT_TIME() {
         return this.getToken(57, 0);
      }

      public TerminalNode K_CURRENT_TIMESTAMP() {
         return this.getToken(58, 0);
      }

      public TerminalNode K_DATABASE() {
         return this.getToken(59, 0);
      }

      public TerminalNode K_DEFAULT() {
         return this.getToken(60, 0);
      }

      public TerminalNode K_DEFERRABLE() {
         return this.getToken(61, 0);
      }

      public TerminalNode K_DEFERRED() {
         return this.getToken(62, 0);
      }

      public TerminalNode K_DELETE() {
         return this.getToken(63, 0);
      }

      public TerminalNode K_DESC() {
         return this.getToken(64, 0);
      }

      public TerminalNode K_DETACH() {
         return this.getToken(65, 0);
      }

      public TerminalNode K_DISTINCT() {
         return this.getToken(66, 0);
      }

      public TerminalNode K_DROP() {
         return this.getToken(67, 0);
      }

      public TerminalNode K_EACH() {
         return this.getToken(68, 0);
      }

      public TerminalNode K_ELSE() {
         return this.getToken(69, 0);
      }

      public TerminalNode K_END() {
         return this.getToken(70, 0);
      }

      public TerminalNode K_ESCAPE() {
         return this.getToken(71, 0);
      }

      public TerminalNode K_EXCEPT() {
         return this.getToken(72, 0);
      }

      public TerminalNode K_EXCLUSIVE() {
         return this.getToken(73, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public TerminalNode K_EXPLAIN() {
         return this.getToken(75, 0);
      }

      public TerminalNode K_FAIL() {
         return this.getToken(76, 0);
      }

      public TerminalNode K_FOR() {
         return this.getToken(77, 0);
      }

      public TerminalNode K_FOREIGN() {
         return this.getToken(78, 0);
      }

      public TerminalNode K_FROM() {
         return this.getToken(79, 0);
      }

      public TerminalNode K_FULL() {
         return this.getToken(80, 0);
      }

      public TerminalNode K_GLOB() {
         return this.getToken(81, 0);
      }

      public TerminalNode K_GROUP() {
         return this.getToken(82, 0);
      }

      public TerminalNode K_HAVING() {
         return this.getToken(83, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_IGNORE() {
         return this.getToken(85, 0);
      }

      public TerminalNode K_IMMEDIATE() {
         return this.getToken(86, 0);
      }

      public TerminalNode K_IN() {
         return this.getToken(87, 0);
      }

      public TerminalNode K_INDEX() {
         return this.getToken(88, 0);
      }

      public TerminalNode K_INDEXED() {
         return this.getToken(89, 0);
      }

      public TerminalNode K_INITIALLY() {
         return this.getToken(90, 0);
      }

      public TerminalNode K_INNER() {
         return this.getToken(91, 0);
      }

      public TerminalNode K_INSERT() {
         return this.getToken(92, 0);
      }

      public TerminalNode K_INSTEAD() {
         return this.getToken(93, 0);
      }

      public TerminalNode K_INTERSECT() {
         return this.getToken(94, 0);
      }

      public TerminalNode K_INTO() {
         return this.getToken(95, 0);
      }

      public TerminalNode K_IS() {
         return this.getToken(96, 0);
      }

      public TerminalNode K_ISNULL() {
         return this.getToken(97, 0);
      }

      public TerminalNode K_JOIN() {
         return this.getToken(98, 0);
      }

      public TerminalNode K_KEY() {
         return this.getToken(99, 0);
      }

      public TerminalNode K_LEFT() {
         return this.getToken(100, 0);
      }

      public TerminalNode K_LIKE() {
         return this.getToken(101, 0);
      }

      public TerminalNode K_LIMIT() {
         return this.getToken(102, 0);
      }

      public TerminalNode K_MATCH() {
         return this.getToken(103, 0);
      }

      public TerminalNode K_NATURAL() {
         return this.getToken(104, 0);
      }

      public TerminalNode K_NO() {
         return this.getToken(105, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_NOTNULL() {
         return this.getToken(107, 0);
      }

      public TerminalNode K_NULL() {
         return this.getToken(108, 0);
      }

      public TerminalNode K_OF() {
         return this.getToken(109, 0);
      }

      public TerminalNode K_OFFSET() {
         return this.getToken(110, 0);
      }

      public TerminalNode K_ON() {
         return this.getToken(111, 0);
      }

      public TerminalNode K_OR() {
         return this.getToken(112, 0);
      }

      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_OUTER() {
         return this.getToken(114, 0);
      }

      public TerminalNode K_PLAN() {
         return this.getToken(115, 0);
      }

      public TerminalNode K_PRAGMA() {
         return this.getToken(116, 0);
      }

      public TerminalNode K_PRIMARY() {
         return this.getToken(117, 0);
      }

      public TerminalNode K_QUERY() {
         return this.getToken(118, 0);
      }

      public TerminalNode K_RAISE() {
         return this.getToken(119, 0);
      }

      public TerminalNode K_RECURSIVE() {
         return this.getToken(120, 0);
      }

      public TerminalNode K_REFERENCES() {
         return this.getToken(121, 0);
      }

      public TerminalNode K_REGEXP() {
         return this.getToken(122, 0);
      }

      public TerminalNode K_REINDEX() {
         return this.getToken(123, 0);
      }

      public TerminalNode K_RELEASE() {
         return this.getToken(124, 0);
      }

      public TerminalNode K_RENAME() {
         return this.getToken(125, 0);
      }

      public TerminalNode K_REPLACE() {
         return this.getToken(126, 0);
      }

      public TerminalNode K_RESTRICT() {
         return this.getToken(127, 0);
      }

      public TerminalNode K_RIGHT() {
         return this.getToken(128, 0);
      }

      public TerminalNode K_ROLLBACK() {
         return this.getToken(129, 0);
      }

      public TerminalNode K_ROW() {
         return this.getToken(130, 0);
      }

      public TerminalNode K_SAVEPOINT() {
         return this.getToken(131, 0);
      }

      public TerminalNode K_SELECT() {
         return this.getToken(132, 0);
      }

      public TerminalNode K_SET() {
         return this.getToken(133, 0);
      }

      public TerminalNode K_TABLE() {
         return this.getToken(134, 0);
      }

      public TerminalNode K_TEMP() {
         return this.getToken(135, 0);
      }

      public TerminalNode K_TEMPORARY() {
         return this.getToken(136, 0);
      }

      public TerminalNode K_THEN() {
         return this.getToken(137, 0);
      }

      public TerminalNode K_TO() {
         return this.getToken(138, 0);
      }

      public TerminalNode K_TRANSACTION() {
         return this.getToken(139, 0);
      }

      public TerminalNode K_TRIGGER() {
         return this.getToken(140, 0);
      }

      public TerminalNode K_UNION() {
         return this.getToken(141, 0);
      }

      public TerminalNode K_UNIQUE() {
         return this.getToken(142, 0);
      }

      public TerminalNode K_UPDATE() {
         return this.getToken(143, 0);
      }

      public TerminalNode K_USING() {
         return this.getToken(144, 0);
      }

      public TerminalNode K_VACUUM() {
         return this.getToken(145, 0);
      }

      public TerminalNode K_VALUES() {
         return this.getToken(146, 0);
      }

      public TerminalNode K_VIEW() {
         return this.getToken(147, 0);
      }

      public TerminalNode K_VIRTUAL() {
         return this.getToken(148, 0);
      }

      public TerminalNode K_WHEN() {
         return this.getToken(149, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public TerminalNode K_WITH() {
         return this.getToken(151, 0);
      }

      public TerminalNode K_WITHOUT() {
         return this.getToken(152, 0);
      }

      public KeywordContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 95;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterKeyword(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitKeyword(this);
         }

      }
   }

   public static class Column_aliasContext extends ParserRuleContext {
      public TerminalNode IDENTIFIER() {
         return this.getToken(153, 0);
      }

      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public Column_aliasContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 94;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterColumn_alias(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitColumn_alias(this);
         }

      }
   }

   public static class Module_argumentContext extends ParserRuleContext {
      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public Column_defContext column_def() {
         return (Column_defContext)this.getRuleContext(Column_defContext.class, 0);
      }

      public Module_argumentContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 93;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterModule_argument(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitModule_argument(this);
         }

      }
   }

   public static class Error_messageContext extends ParserRuleContext {
      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public Error_messageContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 92;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterError_message(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitError_message(this);
         }

      }
   }

   public static class Unary_operatorContext extends ParserRuleContext {
      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public Unary_operatorContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 91;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterUnary_operator(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitUnary_operator(this);
         }

      }
   }

   public static class Literal_valueContext extends ParserRuleContext {
      public TerminalNode NUMERIC_LITERAL() {
         return this.getToken(154, 0);
      }

      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public TerminalNode BLOB_LITERAL() {
         return this.getToken(157, 0);
      }

      public TerminalNode K_NULL() {
         return this.getToken(108, 0);
      }

      public TerminalNode K_CURRENT_TIME() {
         return this.getToken(57, 0);
      }

      public TerminalNode K_CURRENT_DATE() {
         return this.getToken(56, 0);
      }

      public TerminalNode K_CURRENT_TIMESTAMP() {
         return this.getToken(58, 0);
      }

      public Literal_valueContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 90;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterLiteral_value(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitLiteral_value(this);
         }

      }
   }

   public static class Signed_numberContext extends ParserRuleContext {
      public TerminalNode NUMERIC_LITERAL() {
         return this.getToken(154, 0);
      }

      public Signed_numberContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 89;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSigned_number(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSigned_number(this);
         }

      }
   }

   public static class Cte_table_nameContext extends ParserRuleContext {
      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public Cte_table_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 88;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCte_table_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCte_table_name(this);
         }

      }
   }

   public static class Compound_operatorContext extends ParserRuleContext {
      public TerminalNode K_UNION() {
         return this.getToken(141, 0);
      }

      public TerminalNode K_ALL() {
         return this.getToken(33, 0);
      }

      public TerminalNode K_INTERSECT() {
         return this.getToken(94, 0);
      }

      public TerminalNode K_EXCEPT() {
         return this.getToken(72, 0);
      }

      public Compound_operatorContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 87;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCompound_operator(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCompound_operator(this);
         }

      }
   }

   public static class Select_coreContext extends ParserRuleContext {
      public TerminalNode K_SELECT() {
         return this.getToken(132, 0);
      }

      public List result_column() {
         return this.getRuleContexts(Result_columnContext.class);
      }

      public Result_columnContext result_column(int i) {
         return (Result_columnContext)this.getRuleContext(Result_columnContext.class, i);
      }

      public TerminalNode K_FROM() {
         return this.getToken(79, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public TerminalNode K_GROUP() {
         return this.getToken(82, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public TerminalNode K_DISTINCT() {
         return this.getToken(66, 0);
      }

      public TerminalNode K_ALL() {
         return this.getToken(33, 0);
      }

      public List table_or_subquery() {
         return this.getRuleContexts(Table_or_subqueryContext.class);
      }

      public Table_or_subqueryContext table_or_subquery(int i) {
         return (Table_or_subqueryContext)this.getRuleContext(Table_or_subqueryContext.class, i);
      }

      public Join_clauseContext join_clause() {
         return (Join_clauseContext)this.getRuleContext(Join_clauseContext.class, 0);
      }

      public TerminalNode K_HAVING() {
         return this.getToken(83, 0);
      }

      public TerminalNode K_VALUES() {
         return this.getToken(146, 0);
      }

      public Select_coreContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 86;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSelect_core(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSelect_core(this);
         }

      }
   }

   public static class Join_constraintContext extends ParserRuleContext {
      public TerminalNode K_ON() {
         return this.getToken(111, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public TerminalNode K_USING() {
         return this.getToken(144, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public Join_constraintContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 85;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterJoin_constraint(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitJoin_constraint(this);
         }

      }
   }

   public static class Join_operatorContext extends ParserRuleContext {
      public TerminalNode K_JOIN() {
         return this.getToken(98, 0);
      }

      public TerminalNode K_NATURAL() {
         return this.getToken(104, 0);
      }

      public TerminalNode K_LEFT() {
         return this.getToken(100, 0);
      }

      public TerminalNode K_INNER() {
         return this.getToken(91, 0);
      }

      public TerminalNode K_CROSS() {
         return this.getToken(55, 0);
      }

      public TerminalNode K_OUTER() {
         return this.getToken(114, 0);
      }

      public Join_operatorContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 84;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterJoin_operator(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitJoin_operator(this);
         }

      }
   }

   public static class Join_clauseContext extends ParserRuleContext {
      public List table_or_subquery() {
         return this.getRuleContexts(Table_or_subqueryContext.class);
      }

      public Table_or_subqueryContext table_or_subquery(int i) {
         return (Table_or_subqueryContext)this.getRuleContext(Table_or_subqueryContext.class, i);
      }

      public List join_operator() {
         return this.getRuleContexts(Join_operatorContext.class);
      }

      public Join_operatorContext join_operator(int i) {
         return (Join_operatorContext)this.getRuleContext(Join_operatorContext.class, i);
      }

      public List join_constraint() {
         return this.getRuleContexts(Join_constraintContext.class);
      }

      public Join_constraintContext join_constraint(int i) {
         return (Join_constraintContext)this.getRuleContext(Join_constraintContext.class, i);
      }

      public Join_clauseContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 83;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterJoin_clause(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitJoin_clause(this);
         }

      }
   }

   public static class Table_or_subqueryContext extends ParserRuleContext {
      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Table_aliasContext table_alias() {
         return (Table_aliasContext)this.getRuleContext(Table_aliasContext.class, 0);
      }

      public TerminalNode K_INDEXED() {
         return this.getToken(89, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public Index_nameContext index_name() {
         return (Index_nameContext)this.getRuleContext(Index_nameContext.class, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public List table_or_subquery() {
         return this.getRuleContexts(Table_or_subqueryContext.class);
      }

      public Table_or_subqueryContext table_or_subquery(int i) {
         return (Table_or_subqueryContext)this.getRuleContext(Table_or_subqueryContext.class, i);
      }

      public Join_clauseContext join_clause() {
         return (Join_clauseContext)this.getRuleContext(Join_clauseContext.class, 0);
      }

      public Select_stmtContext select_stmt() {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, 0);
      }

      public Table_or_subqueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 82;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterTable_or_subquery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitTable_or_subquery(this);
         }

      }
   }

   public static class Result_columnContext extends ParserRuleContext {
      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public Column_aliasContext column_alias() {
         return (Column_aliasContext)this.getRuleContext(Column_aliasContext.class, 0);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public Result_columnContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 81;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterResult_column(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitResult_column(this);
         }

      }
   }

   public static class Common_table_expressionContext extends ParserRuleContext {
      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public Select_stmtContext select_stmt() {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public Common_table_expressionContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 80;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCommon_table_expression(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCommon_table_expression(this);
         }

      }
   }

   public static class Pragma_valueContext extends ParserRuleContext {
      public Signed_numberContext signed_number() {
         return (Signed_numberContext)this.getRuleContext(Signed_numberContext.class, 0);
      }

      public NameContext name() {
         return (NameContext)this.getRuleContext(NameContext.class, 0);
      }

      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public Pragma_valueContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 79;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterPragma_value(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitPragma_value(this);
         }

      }
   }

   public static class Ordering_termContext extends ParserRuleContext {
      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public TerminalNode K_COLLATE() {
         return this.getToken(49, 0);
      }

      public Collation_nameContext collation_name() {
         return (Collation_nameContext)this.getRuleContext(Collation_nameContext.class, 0);
      }

      public TerminalNode K_ASC() {
         return this.getToken(38, 0);
      }

      public TerminalNode K_DESC() {
         return this.getToken(64, 0);
      }

      public Ordering_termContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 78;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterOrdering_term(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitOrdering_term(this);
         }

      }
   }

   public static class Qualified_table_nameContext extends ParserRuleContext {
      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public TerminalNode K_INDEXED() {
         return this.getToken(89, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public Index_nameContext index_name() {
         return (Index_nameContext)this.getRuleContext(Index_nameContext.class, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public Qualified_table_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 77;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterQualified_table_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitQualified_table_name(this);
         }

      }
   }

   public static class With_clauseContext extends ParserRuleContext {
      public TerminalNode K_WITH() {
         return this.getToken(151, 0);
      }

      public List cte_table_name() {
         return this.getRuleContexts(Cte_table_nameContext.class);
      }

      public Cte_table_nameContext cte_table_name(int i) {
         return (Cte_table_nameContext)this.getRuleContext(Cte_table_nameContext.class, i);
      }

      public List K_AS() {
         return this.getTokens(37);
      }

      public TerminalNode K_AS(int i) {
         return this.getToken(37, i);
      }

      public List select_stmt() {
         return this.getRuleContexts(Select_stmtContext.class);
      }

      public Select_stmtContext select_stmt(int i) {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, i);
      }

      public TerminalNode K_RECURSIVE() {
         return this.getToken(120, 0);
      }

      public With_clauseContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 76;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterWith_clause(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitWith_clause(this);
         }

      }
   }

   public static class Table_constraintContext extends ParserRuleContext {
      public List indexed_column() {
         return this.getRuleContexts(Indexed_columnContext.class);
      }

      public Indexed_columnContext indexed_column(int i) {
         return (Indexed_columnContext)this.getRuleContext(Indexed_columnContext.class, i);
      }

      public Conflict_clauseContext conflict_clause() {
         return (Conflict_clauseContext)this.getRuleContext(Conflict_clauseContext.class, 0);
      }

      public TerminalNode K_CHECK() {
         return this.getToken(48, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public TerminalNode K_FOREIGN() {
         return this.getToken(78, 0);
      }

      public TerminalNode K_KEY() {
         return this.getToken(99, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public Foreign_key_clauseContext foreign_key_clause() {
         return (Foreign_key_clauseContext)this.getRuleContext(Foreign_key_clauseContext.class, 0);
      }

      public TerminalNode K_CONSTRAINT() {
         return this.getToken(53, 0);
      }

      public NameContext name() {
         return (NameContext)this.getRuleContext(NameContext.class, 0);
      }

      public TerminalNode K_PRIMARY() {
         return this.getToken(117, 0);
      }

      public TerminalNode K_UNIQUE() {
         return this.getToken(142, 0);
      }

      public Table_constraintContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 75;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterTable_constraint(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitTable_constraint(this);
         }

      }
   }

   public static class Indexed_columnContext extends ParserRuleContext {
      public Column_nameContext column_name() {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, 0);
      }

      public TerminalNode K_COLLATE() {
         return this.getToken(49, 0);
      }

      public Collation_nameContext collation_name() {
         return (Collation_nameContext)this.getRuleContext(Collation_nameContext.class, 0);
      }

      public TerminalNode K_ASC() {
         return this.getToken(38, 0);
      }

      public TerminalNode K_DESC() {
         return this.getToken(64, 0);
      }

      public Indexed_columnContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 74;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterIndexed_column(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitIndexed_column(this);
         }

      }
   }

   public static class Raise_functionContext extends ParserRuleContext {
      public TerminalNode K_RAISE() {
         return this.getToken(119, 0);
      }

      public TerminalNode K_IGNORE() {
         return this.getToken(85, 0);
      }

      public Error_messageContext error_message() {
         return (Error_messageContext)this.getRuleContext(Error_messageContext.class, 0);
      }

      public TerminalNode K_ROLLBACK() {
         return this.getToken(129, 0);
      }

      public TerminalNode K_ABORT() {
         return this.getToken(29, 0);
      }

      public TerminalNode K_FAIL() {
         return this.getToken(76, 0);
      }

      public Raise_functionContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 73;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterRaise_function(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitRaise_function(this);
         }

      }
   }

   public static class Foreign_key_clauseContext extends ParserRuleContext {
      public TerminalNode K_REFERENCES() {
         return this.getToken(121, 0);
      }

      public Foreign_tableContext foreign_table() {
         return (Foreign_tableContext)this.getRuleContext(Foreign_tableContext.class, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public TerminalNode K_DEFERRABLE() {
         return this.getToken(61, 0);
      }

      public List K_ON() {
         return this.getTokens(111);
      }

      public TerminalNode K_ON(int i) {
         return this.getToken(111, i);
      }

      public List K_MATCH() {
         return this.getTokens(103);
      }

      public TerminalNode K_MATCH(int i) {
         return this.getToken(103, i);
      }

      public List name() {
         return this.getRuleContexts(NameContext.class);
      }

      public NameContext name(int i) {
         return (NameContext)this.getRuleContext(NameContext.class, i);
      }

      public List K_DELETE() {
         return this.getTokens(63);
      }

      public TerminalNode K_DELETE(int i) {
         return this.getToken(63, i);
      }

      public List K_UPDATE() {
         return this.getTokens(143);
      }

      public TerminalNode K_UPDATE(int i) {
         return this.getToken(143, i);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_INITIALLY() {
         return this.getToken(90, 0);
      }

      public TerminalNode K_DEFERRED() {
         return this.getToken(62, 0);
      }

      public TerminalNode K_IMMEDIATE() {
         return this.getToken(86, 0);
      }

      public List K_SET() {
         return this.getTokens(133);
      }

      public TerminalNode K_SET(int i) {
         return this.getToken(133, i);
      }

      public List K_NULL() {
         return this.getTokens(108);
      }

      public TerminalNode K_NULL(int i) {
         return this.getToken(108, i);
      }

      public List K_DEFAULT() {
         return this.getTokens(60);
      }

      public TerminalNode K_DEFAULT(int i) {
         return this.getToken(60, i);
      }

      public List K_CASCADE() {
         return this.getTokens(45);
      }

      public TerminalNode K_CASCADE(int i) {
         return this.getToken(45, i);
      }

      public List K_RESTRICT() {
         return this.getTokens(127);
      }

      public TerminalNode K_RESTRICT(int i) {
         return this.getToken(127, i);
      }

      public List K_NO() {
         return this.getTokens(105);
      }

      public TerminalNode K_NO(int i) {
         return this.getToken(105, i);
      }

      public List K_ACTION() {
         return this.getTokens(30);
      }

      public TerminalNode K_ACTION(int i) {
         return this.getToken(30, i);
      }

      public Foreign_key_clauseContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 72;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterForeign_key_clause(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitForeign_key_clause(this);
         }

      }
   }

   public static class ExprContext extends ParserRuleContext {
      public Literal_valueContext literal_value() {
         return (Literal_valueContext)this.getRuleContext(Literal_valueContext.class, 0);
      }

      public TerminalNode BIND_PARAMETER() {
         return this.getToken(155, 0);
      }

      public Column_nameContext column_name() {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Unary_operatorContext unary_operator() {
         return (Unary_operatorContext)this.getRuleContext(Unary_operatorContext.class, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public Function_nameContext function_name() {
         return (Function_nameContext)this.getRuleContext(Function_nameContext.class, 0);
      }

      public TerminalNode K_DISTINCT() {
         return this.getToken(66, 0);
      }

      public TerminalNode K_CAST() {
         return this.getToken(47, 0);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public Type_nameContext type_name() {
         return (Type_nameContext)this.getRuleContext(Type_nameContext.class, 0);
      }

      public Select_stmtContext select_stmt() {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_CASE() {
         return this.getToken(46, 0);
      }

      public TerminalNode K_END() {
         return this.getToken(70, 0);
      }

      public List K_WHEN() {
         return this.getTokens(149);
      }

      public TerminalNode K_WHEN(int i) {
         return this.getToken(149, i);
      }

      public List K_THEN() {
         return this.getTokens(137);
      }

      public TerminalNode K_THEN(int i) {
         return this.getToken(137, i);
      }

      public TerminalNode K_ELSE() {
         return this.getToken(69, 0);
      }

      public Raise_functionContext raise_function() {
         return (Raise_functionContext)this.getRuleContext(Raise_functionContext.class, 0);
      }

      public TerminalNode K_IS() {
         return this.getToken(96, 0);
      }

      public TerminalNode K_IN() {
         return this.getToken(87, 0);
      }

      public TerminalNode K_LIKE() {
         return this.getToken(101, 0);
      }

      public TerminalNode K_GLOB() {
         return this.getToken(81, 0);
      }

      public TerminalNode K_MATCH() {
         return this.getToken(103, 0);
      }

      public TerminalNode K_REGEXP() {
         return this.getToken(122, 0);
      }

      public TerminalNode K_AND() {
         return this.getToken(36, 0);
      }

      public TerminalNode K_OR() {
         return this.getToken(112, 0);
      }

      public TerminalNode K_BETWEEN() {
         return this.getToken(43, 0);
      }

      public TerminalNode K_COLLATE() {
         return this.getToken(49, 0);
      }

      public Collation_nameContext collation_name() {
         return (Collation_nameContext)this.getRuleContext(Collation_nameContext.class, 0);
      }

      public TerminalNode K_ESCAPE() {
         return this.getToken(71, 0);
      }

      public TerminalNode K_ISNULL() {
         return this.getToken(97, 0);
      }

      public TerminalNode K_NOTNULL() {
         return this.getToken(107, 0);
      }

      public TerminalNode K_NULL() {
         return this.getToken(108, 0);
      }

      public ExprContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 71;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterExpr(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitExpr(this);
         }

      }
   }

   public static class Conflict_clauseContext extends ParserRuleContext {
      public TerminalNode K_ON() {
         return this.getToken(111, 0);
      }

      public TerminalNode K_CONFLICT() {
         return this.getToken(52, 0);
      }

      public TerminalNode K_ROLLBACK() {
         return this.getToken(129, 0);
      }

      public TerminalNode K_ABORT() {
         return this.getToken(29, 0);
      }

      public TerminalNode K_FAIL() {
         return this.getToken(76, 0);
      }

      public TerminalNode K_IGNORE() {
         return this.getToken(85, 0);
      }

      public TerminalNode K_REPLACE() {
         return this.getToken(126, 0);
      }

      public Conflict_clauseContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 70;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterConflict_clause(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitConflict_clause(this);
         }

      }
   }

   public static class Column_constraintContext extends ParserRuleContext {
      public TerminalNode K_PRIMARY() {
         return this.getToken(117, 0);
      }

      public TerminalNode K_KEY() {
         return this.getToken(99, 0);
      }

      public Conflict_clauseContext conflict_clause() {
         return (Conflict_clauseContext)this.getRuleContext(Conflict_clauseContext.class, 0);
      }

      public TerminalNode K_NULL() {
         return this.getToken(108, 0);
      }

      public TerminalNode K_UNIQUE() {
         return this.getToken(142, 0);
      }

      public TerminalNode K_CHECK() {
         return this.getToken(48, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public TerminalNode K_DEFAULT() {
         return this.getToken(60, 0);
      }

      public TerminalNode K_COLLATE() {
         return this.getToken(49, 0);
      }

      public Collation_nameContext collation_name() {
         return (Collation_nameContext)this.getRuleContext(Collation_nameContext.class, 0);
      }

      public Foreign_key_clauseContext foreign_key_clause() {
         return (Foreign_key_clauseContext)this.getRuleContext(Foreign_key_clauseContext.class, 0);
      }

      public TerminalNode K_CONSTRAINT() {
         return this.getToken(53, 0);
      }

      public NameContext name() {
         return (NameContext)this.getRuleContext(NameContext.class, 0);
      }

      public Signed_numberContext signed_number() {
         return (Signed_numberContext)this.getRuleContext(Signed_numberContext.class, 0);
      }

      public Literal_valueContext literal_value() {
         return (Literal_valueContext)this.getRuleContext(Literal_valueContext.class, 0);
      }

      public TerminalNode K_AUTOINCREMENT() {
         return this.getToken(40, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_ASC() {
         return this.getToken(38, 0);
      }

      public TerminalNode K_DESC() {
         return this.getToken(64, 0);
      }

      public Column_constraintContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 69;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterColumn_constraint(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitColumn_constraint(this);
         }

      }
   }

   public static class Type_nameContext extends ParserRuleContext {
      public List name() {
         return this.getRuleContexts(NameContext.class);
      }

      public NameContext name(int i) {
         return (NameContext)this.getRuleContext(NameContext.class, i);
      }

      public List signed_number() {
         return this.getRuleContexts(Signed_numberContext.class);
      }

      public Signed_numberContext signed_number(int i) {
         return (Signed_numberContext)this.getRuleContext(Signed_numberContext.class, i);
      }

      public Type_nameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 68;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterType_name(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitType_name(this);
         }

      }
   }

   public static class Column_defContext extends ParserRuleContext {
      public Column_nameContext column_name() {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, 0);
      }

      public Type_nameContext type_name() {
         return (Type_nameContext)this.getRuleContext(Type_nameContext.class, 0);
      }

      public List column_constraint() {
         return this.getRuleContexts(Column_constraintContext.class);
      }

      public Column_constraintContext column_constraint(int i) {
         return (Column_constraintContext)this.getRuleContext(Column_constraintContext.class, i);
      }

      public Column_defContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 67;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterColumn_def(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitColumn_def(this);
         }

      }
   }

   public static class Vacuum_stmtContext extends ParserRuleContext {
      public TerminalNode K_VACUUM() {
         return this.getToken(145, 0);
      }

      public Vacuum_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 66;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterVacuum_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitVacuum_stmt(this);
         }

      }
   }

   public static class Update_stmt_limitedContext extends ParserRuleContext {
      public TerminalNode K_UPDATE() {
         return this.getToken(143, 0);
      }

      public Qualified_table_nameContext qualified_table_name() {
         return (Qualified_table_nameContext)this.getRuleContext(Qualified_table_nameContext.class, 0);
      }

      public TerminalNode K_SET() {
         return this.getToken(133, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public With_clauseContext with_clause() {
         return (With_clauseContext)this.getRuleContext(With_clauseContext.class, 0);
      }

      public TerminalNode K_OR() {
         return this.getToken(112, 0);
      }

      public TerminalNode K_ROLLBACK() {
         return this.getToken(129, 0);
      }

      public TerminalNode K_ABORT() {
         return this.getToken(29, 0);
      }

      public TerminalNode K_REPLACE() {
         return this.getToken(126, 0);
      }

      public TerminalNode K_FAIL() {
         return this.getToken(76, 0);
      }

      public TerminalNode K_IGNORE() {
         return this.getToken(85, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public TerminalNode K_LIMIT() {
         return this.getToken(102, 0);
      }

      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public List ordering_term() {
         return this.getRuleContexts(Ordering_termContext.class);
      }

      public Ordering_termContext ordering_term(int i) {
         return (Ordering_termContext)this.getRuleContext(Ordering_termContext.class, i);
      }

      public TerminalNode K_OFFSET() {
         return this.getToken(110, 0);
      }

      public Update_stmt_limitedContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 65;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterUpdate_stmt_limited(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitUpdate_stmt_limited(this);
         }

      }
   }

   public static class Update_stmtContext extends ParserRuleContext {
      public TerminalNode K_UPDATE() {
         return this.getToken(143, 0);
      }

      public Qualified_table_nameContext qualified_table_name() {
         return (Qualified_table_nameContext)this.getRuleContext(Qualified_table_nameContext.class, 0);
      }

      public TerminalNode K_SET() {
         return this.getToken(133, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public With_clauseContext with_clause() {
         return (With_clauseContext)this.getRuleContext(With_clauseContext.class, 0);
      }

      public TerminalNode K_OR() {
         return this.getToken(112, 0);
      }

      public TerminalNode K_ROLLBACK() {
         return this.getToken(129, 0);
      }

      public TerminalNode K_ABORT() {
         return this.getToken(29, 0);
      }

      public TerminalNode K_REPLACE() {
         return this.getToken(126, 0);
      }

      public TerminalNode K_FAIL() {
         return this.getToken(76, 0);
      }

      public TerminalNode K_IGNORE() {
         return this.getToken(85, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public Update_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 64;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterUpdate_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitUpdate_stmt(this);
         }

      }
   }

   public static class Select_or_valuesContext extends ParserRuleContext {
      public TerminalNode K_SELECT() {
         return this.getToken(132, 0);
      }

      public List result_column() {
         return this.getRuleContexts(Result_columnContext.class);
      }

      public Result_columnContext result_column(int i) {
         return (Result_columnContext)this.getRuleContext(Result_columnContext.class, i);
      }

      public TerminalNode K_FROM() {
         return this.getToken(79, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public TerminalNode K_GROUP() {
         return this.getToken(82, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public TerminalNode K_DISTINCT() {
         return this.getToken(66, 0);
      }

      public TerminalNode K_ALL() {
         return this.getToken(33, 0);
      }

      public List table_or_subquery() {
         return this.getRuleContexts(Table_or_subqueryContext.class);
      }

      public Table_or_subqueryContext table_or_subquery(int i) {
         return (Table_or_subqueryContext)this.getRuleContext(Table_or_subqueryContext.class, i);
      }

      public Join_clauseContext join_clause() {
         return (Join_clauseContext)this.getRuleContext(Join_clauseContext.class, 0);
      }

      public TerminalNode K_HAVING() {
         return this.getToken(83, 0);
      }

      public TerminalNode K_VALUES() {
         return this.getToken(146, 0);
      }

      public Select_or_valuesContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 63;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSelect_or_values(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSelect_or_values(this);
         }

      }
   }

   public static class Select_stmtContext extends ParserRuleContext {
      public List select_or_values() {
         return this.getRuleContexts(Select_or_valuesContext.class);
      }

      public Select_or_valuesContext select_or_values(int i) {
         return (Select_or_valuesContext)this.getRuleContext(Select_or_valuesContext.class, i);
      }

      public TerminalNode K_WITH() {
         return this.getToken(151, 0);
      }

      public List common_table_expression() {
         return this.getRuleContexts(Common_table_expressionContext.class);
      }

      public Common_table_expressionContext common_table_expression(int i) {
         return (Common_table_expressionContext)this.getRuleContext(Common_table_expressionContext.class, i);
      }

      public List compound_operator() {
         return this.getRuleContexts(Compound_operatorContext.class);
      }

      public Compound_operatorContext compound_operator(int i) {
         return (Compound_operatorContext)this.getRuleContext(Compound_operatorContext.class, i);
      }

      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public List ordering_term() {
         return this.getRuleContexts(Ordering_termContext.class);
      }

      public Ordering_termContext ordering_term(int i) {
         return (Ordering_termContext)this.getRuleContext(Ordering_termContext.class, i);
      }

      public TerminalNode K_LIMIT() {
         return this.getToken(102, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public TerminalNode K_RECURSIVE() {
         return this.getToken(120, 0);
      }

      public TerminalNode K_OFFSET() {
         return this.getToken(110, 0);
      }

      public Select_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 62;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSelect_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSelect_stmt(this);
         }

      }
   }

   public static class Simple_select_stmtContext extends ParserRuleContext {
      public Select_coreContext select_core() {
         return (Select_coreContext)this.getRuleContext(Select_coreContext.class, 0);
      }

      public TerminalNode K_WITH() {
         return this.getToken(151, 0);
      }

      public List common_table_expression() {
         return this.getRuleContexts(Common_table_expressionContext.class);
      }

      public Common_table_expressionContext common_table_expression(int i) {
         return (Common_table_expressionContext)this.getRuleContext(Common_table_expressionContext.class, i);
      }

      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public List ordering_term() {
         return this.getRuleContexts(Ordering_termContext.class);
      }

      public Ordering_termContext ordering_term(int i) {
         return (Ordering_termContext)this.getRuleContext(Ordering_termContext.class, i);
      }

      public TerminalNode K_LIMIT() {
         return this.getToken(102, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public TerminalNode K_RECURSIVE() {
         return this.getToken(120, 0);
      }

      public TerminalNode K_OFFSET() {
         return this.getToken(110, 0);
      }

      public Simple_select_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 61;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSimple_select_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSimple_select_stmt(this);
         }

      }
   }

   public static class Savepoint_stmtContext extends ParserRuleContext {
      public TerminalNode K_SAVEPOINT() {
         return this.getToken(131, 0);
      }

      public Savepoint_nameContext savepoint_name() {
         return (Savepoint_nameContext)this.getRuleContext(Savepoint_nameContext.class, 0);
      }

      public Savepoint_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 60;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSavepoint_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSavepoint_stmt(this);
         }

      }
   }

   public static class Rollback_stmtContext extends ParserRuleContext {
      public TerminalNode K_ROLLBACK() {
         return this.getToken(129, 0);
      }

      public TerminalNode K_TRANSACTION() {
         return this.getToken(139, 0);
      }

      public TerminalNode K_TO() {
         return this.getToken(138, 0);
      }

      public Savepoint_nameContext savepoint_name() {
         return (Savepoint_nameContext)this.getRuleContext(Savepoint_nameContext.class, 0);
      }

      public Transaction_nameContext transaction_name() {
         return (Transaction_nameContext)this.getRuleContext(Transaction_nameContext.class, 0);
      }

      public TerminalNode K_SAVEPOINT() {
         return this.getToken(131, 0);
      }

      public Rollback_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 59;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterRollback_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitRollback_stmt(this);
         }

      }
   }

   public static class Release_stmtContext extends ParserRuleContext {
      public TerminalNode K_RELEASE() {
         return this.getToken(124, 0);
      }

      public Savepoint_nameContext savepoint_name() {
         return (Savepoint_nameContext)this.getRuleContext(Savepoint_nameContext.class, 0);
      }

      public TerminalNode K_SAVEPOINT() {
         return this.getToken(131, 0);
      }

      public Release_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 58;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterRelease_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitRelease_stmt(this);
         }

      }
   }

   public static class Reindex_stmtContext extends ParserRuleContext {
      public TerminalNode K_REINDEX() {
         return this.getToken(123, 0);
      }

      public Collation_nameContext collation_name() {
         return (Collation_nameContext)this.getRuleContext(Collation_nameContext.class, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public Index_nameContext index_name() {
         return (Index_nameContext)this.getRuleContext(Index_nameContext.class, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Reindex_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 57;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterReindex_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitReindex_stmt(this);
         }

      }
   }

   public static class Pragma_stmtContext extends ParserRuleContext {
      public TerminalNode K_PRAGMA() {
         return this.getToken(116, 0);
      }

      public Pragma_nameContext pragma_name() {
         return (Pragma_nameContext)this.getRuleContext(Pragma_nameContext.class, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Pragma_valueContext pragma_value() {
         return (Pragma_valueContext)this.getRuleContext(Pragma_valueContext.class, 0);
      }

      public Pragma_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 56;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterPragma_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitPragma_stmt(this);
         }

      }
   }

   public static class Insert_stmtContext extends ParserRuleContext {
      public TerminalNode K_INTO() {
         return this.getToken(95, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public TerminalNode K_INSERT() {
         return this.getToken(92, 0);
      }

      public TerminalNode K_REPLACE() {
         return this.getToken(126, 0);
      }

      public TerminalNode K_OR() {
         return this.getToken(112, 0);
      }

      public TerminalNode K_ROLLBACK() {
         return this.getToken(129, 0);
      }

      public TerminalNode K_ABORT() {
         return this.getToken(29, 0);
      }

      public TerminalNode K_FAIL() {
         return this.getToken(76, 0);
      }

      public TerminalNode K_IGNORE() {
         return this.getToken(85, 0);
      }

      public TerminalNode K_VALUES() {
         return this.getToken(146, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public Select_stmtContext select_stmt() {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, 0);
      }

      public TerminalNode K_DEFAULT() {
         return this.getToken(60, 0);
      }

      public With_clauseContext with_clause() {
         return (With_clauseContext)this.getRuleContext(With_clauseContext.class, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public Insert_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 55;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterInsert_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitInsert_stmt(this);
         }

      }
   }

   public static class Factored_select_stmtContext extends ParserRuleContext {
      public List select_core() {
         return this.getRuleContexts(Select_coreContext.class);
      }

      public Select_coreContext select_core(int i) {
         return (Select_coreContext)this.getRuleContext(Select_coreContext.class, i);
      }

      public TerminalNode K_WITH() {
         return this.getToken(151, 0);
      }

      public List common_table_expression() {
         return this.getRuleContexts(Common_table_expressionContext.class);
      }

      public Common_table_expressionContext common_table_expression(int i) {
         return (Common_table_expressionContext)this.getRuleContext(Common_table_expressionContext.class, i);
      }

      public List compound_operator() {
         return this.getRuleContexts(Compound_operatorContext.class);
      }

      public Compound_operatorContext compound_operator(int i) {
         return (Compound_operatorContext)this.getRuleContext(Compound_operatorContext.class, i);
      }

      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public List ordering_term() {
         return this.getRuleContexts(Ordering_termContext.class);
      }

      public Ordering_termContext ordering_term(int i) {
         return (Ordering_termContext)this.getRuleContext(Ordering_termContext.class, i);
      }

      public TerminalNode K_LIMIT() {
         return this.getToken(102, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public TerminalNode K_RECURSIVE() {
         return this.getToken(120, 0);
      }

      public TerminalNode K_OFFSET() {
         return this.getToken(110, 0);
      }

      public Factored_select_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 54;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterFactored_select_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitFactored_select_stmt(this);
         }

      }
   }

   public static class Drop_view_stmtContext extends ParserRuleContext {
      public TerminalNode K_DROP() {
         return this.getToken(67, 0);
      }

      public TerminalNode K_VIEW() {
         return this.getToken(147, 0);
      }

      public View_nameContext view_name() {
         return (View_nameContext)this.getRuleContext(View_nameContext.class, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Drop_view_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 53;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDrop_view_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDrop_view_stmt(this);
         }

      }
   }

   public static class Drop_trigger_stmtContext extends ParserRuleContext {
      public TerminalNode K_DROP() {
         return this.getToken(67, 0);
      }

      public TerminalNode K_TRIGGER() {
         return this.getToken(140, 0);
      }

      public Trigger_nameContext trigger_name() {
         return (Trigger_nameContext)this.getRuleContext(Trigger_nameContext.class, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Drop_trigger_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 52;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDrop_trigger_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDrop_trigger_stmt(this);
         }

      }
   }

   public static class Drop_table_stmtContext extends ParserRuleContext {
      public TerminalNode K_DROP() {
         return this.getToken(67, 0);
      }

      public TerminalNode K_TABLE() {
         return this.getToken(134, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Drop_table_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 51;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDrop_table_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDrop_table_stmt(this);
         }

      }
   }

   public static class Drop_index_stmtContext extends ParserRuleContext {
      public TerminalNode K_DROP() {
         return this.getToken(67, 0);
      }

      public TerminalNode K_INDEX() {
         return this.getToken(88, 0);
      }

      public Index_nameContext index_name() {
         return (Index_nameContext)this.getRuleContext(Index_nameContext.class, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Drop_index_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 50;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDrop_index_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDrop_index_stmt(this);
         }

      }
   }

   public static class Detach_stmtContext extends ParserRuleContext {
      public TerminalNode K_DETACH() {
         return this.getToken(65, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public TerminalNode K_DATABASE() {
         return this.getToken(59, 0);
      }

      public Detach_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 49;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDetach_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDetach_stmt(this);
         }

      }
   }

   public static class Delete_stmt_limitedContext extends ParserRuleContext {
      public TerminalNode K_DELETE() {
         return this.getToken(63, 0);
      }

      public TerminalNode K_FROM() {
         return this.getToken(79, 0);
      }

      public Qualified_table_nameContext qualified_table_name() {
         return (Qualified_table_nameContext)this.getRuleContext(Qualified_table_nameContext.class, 0);
      }

      public With_clauseContext with_clause() {
         return (With_clauseContext)this.getRuleContext(With_clauseContext.class, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public TerminalNode K_LIMIT() {
         return this.getToken(102, 0);
      }

      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public List ordering_term() {
         return this.getRuleContexts(Ordering_termContext.class);
      }

      public Ordering_termContext ordering_term(int i) {
         return (Ordering_termContext)this.getRuleContext(Ordering_termContext.class, i);
      }

      public TerminalNode K_OFFSET() {
         return this.getToken(110, 0);
      }

      public Delete_stmt_limitedContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 48;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDelete_stmt_limited(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDelete_stmt_limited(this);
         }

      }
   }

   public static class Delete_stmtContext extends ParserRuleContext {
      public TerminalNode K_DELETE() {
         return this.getToken(63, 0);
      }

      public TerminalNode K_FROM() {
         return this.getToken(79, 0);
      }

      public Qualified_table_nameContext qualified_table_name() {
         return (Qualified_table_nameContext)this.getRuleContext(Qualified_table_nameContext.class, 0);
      }

      public With_clauseContext with_clause() {
         return (With_clauseContext)this.getRuleContext(With_clauseContext.class, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public Delete_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 47;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDelete_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDelete_stmt(this);
         }

      }
   }

   public static class Create_virtual_table_stmtContext extends ParserRuleContext {
      public TerminalNode K_CREATE() {
         return this.getToken(54, 0);
      }

      public TerminalNode K_VIRTUAL() {
         return this.getToken(148, 0);
      }

      public TerminalNode K_TABLE() {
         return this.getToken(134, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public TerminalNode K_USING() {
         return this.getToken(144, 0);
      }

      public Module_nameContext module_name() {
         return (Module_nameContext)this.getRuleContext(Module_nameContext.class, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public List module_argument() {
         return this.getRuleContexts(Module_argumentContext.class);
      }

      public Module_argumentContext module_argument(int i) {
         return (Module_argumentContext)this.getRuleContext(Module_argumentContext.class, i);
      }

      public Create_virtual_table_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 46;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCreate_virtual_table_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCreate_virtual_table_stmt(this);
         }

      }
   }

   public static class Create_view_stmtContext extends ParserRuleContext {
      public TerminalNode K_CREATE() {
         return this.getToken(54, 0);
      }

      public TerminalNode K_VIEW() {
         return this.getToken(147, 0);
      }

      public View_nameContext view_name() {
         return (View_nameContext)this.getRuleContext(View_nameContext.class, 0);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public Select_stmtContext select_stmt() {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public TerminalNode K_TEMP() {
         return this.getToken(135, 0);
      }

      public TerminalNode K_TEMPORARY() {
         return this.getToken(136, 0);
      }

      public Create_view_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 45;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCreate_view_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCreate_view_stmt(this);
         }

      }
   }

   public static class Create_trigger_stmtContext extends ParserRuleContext {
      public TerminalNode K_CREATE() {
         return this.getToken(54, 0);
      }

      public TerminalNode K_TRIGGER() {
         return this.getToken(140, 0);
      }

      public Trigger_nameContext trigger_name() {
         return (Trigger_nameContext)this.getRuleContext(Trigger_nameContext.class, 0);
      }

      public TerminalNode K_ON() {
         return this.getToken(111, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public TerminalNode K_BEGIN() {
         return this.getToken(42, 0);
      }

      public TerminalNode K_END() {
         return this.getToken(70, 0);
      }

      public TerminalNode K_DELETE() {
         return this.getToken(63, 0);
      }

      public TerminalNode K_INSERT() {
         return this.getToken(92, 0);
      }

      public TerminalNode K_UPDATE() {
         return this.getToken(143, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public List database_name() {
         return this.getRuleContexts(Database_nameContext.class);
      }

      public Database_nameContext database_name(int i) {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, i);
      }

      public TerminalNode K_BEFORE() {
         return this.getToken(41, 0);
      }

      public TerminalNode K_AFTER() {
         return this.getToken(32, 0);
      }

      public TerminalNode K_INSTEAD() {
         return this.getToken(93, 0);
      }

      public List K_OF() {
         return this.getTokens(109);
      }

      public TerminalNode K_OF(int i) {
         return this.getToken(109, i);
      }

      public TerminalNode K_FOR() {
         return this.getToken(77, 0);
      }

      public TerminalNode K_EACH() {
         return this.getToken(68, 0);
      }

      public TerminalNode K_ROW() {
         return this.getToken(130, 0);
      }

      public TerminalNode K_WHEN() {
         return this.getToken(149, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public TerminalNode K_TEMP() {
         return this.getToken(135, 0);
      }

      public TerminalNode K_TEMPORARY() {
         return this.getToken(136, 0);
      }

      public List column_name() {
         return this.getRuleContexts(Column_nameContext.class);
      }

      public Column_nameContext column_name(int i) {
         return (Column_nameContext)this.getRuleContext(Column_nameContext.class, i);
      }

      public List update_stmt() {
         return this.getRuleContexts(Update_stmtContext.class);
      }

      public Update_stmtContext update_stmt(int i) {
         return (Update_stmtContext)this.getRuleContext(Update_stmtContext.class, i);
      }

      public List insert_stmt() {
         return this.getRuleContexts(Insert_stmtContext.class);
      }

      public Insert_stmtContext insert_stmt(int i) {
         return (Insert_stmtContext)this.getRuleContext(Insert_stmtContext.class, i);
      }

      public List delete_stmt() {
         return this.getRuleContexts(Delete_stmtContext.class);
      }

      public Delete_stmtContext delete_stmt(int i) {
         return (Delete_stmtContext)this.getRuleContext(Delete_stmtContext.class, i);
      }

      public List select_stmt() {
         return this.getRuleContexts(Select_stmtContext.class);
      }

      public Select_stmtContext select_stmt(int i) {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, i);
      }

      public Create_trigger_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 44;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCreate_trigger_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCreate_trigger_stmt(this);
         }

      }
   }

   public static class Create_table_stmtContext extends ParserRuleContext {
      public TerminalNode K_CREATE() {
         return this.getToken(54, 0);
      }

      public TerminalNode K_TABLE() {
         return this.getToken(134, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public List column_def() {
         return this.getRuleContexts(Column_defContext.class);
      }

      public Column_defContext column_def(int i) {
         return (Column_defContext)this.getRuleContext(Column_defContext.class, i);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public Select_stmtContext select_stmt() {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public TerminalNode K_TEMP() {
         return this.getToken(135, 0);
      }

      public TerminalNode K_TEMPORARY() {
         return this.getToken(136, 0);
      }

      public List table_constraint() {
         return this.getRuleContexts(Table_constraintContext.class);
      }

      public Table_constraintContext table_constraint(int i) {
         return (Table_constraintContext)this.getRuleContext(Table_constraintContext.class, i);
      }

      public TerminalNode K_WITHOUT() {
         return this.getToken(152, 0);
      }

      public TerminalNode IDENTIFIER() {
         return this.getToken(153, 0);
      }

      public Create_table_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 43;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCreate_table_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCreate_table_stmt(this);
         }

      }
   }

   public static class Create_index_stmtContext extends ParserRuleContext {
      public TerminalNode K_CREATE() {
         return this.getToken(54, 0);
      }

      public TerminalNode K_INDEX() {
         return this.getToken(88, 0);
      }

      public Index_nameContext index_name() {
         return (Index_nameContext)this.getRuleContext(Index_nameContext.class, 0);
      }

      public TerminalNode K_ON() {
         return this.getToken(111, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public List indexed_column() {
         return this.getRuleContexts(Indexed_columnContext.class);
      }

      public Indexed_columnContext indexed_column(int i) {
         return (Indexed_columnContext)this.getRuleContext(Indexed_columnContext.class, i);
      }

      public TerminalNode K_UNIQUE() {
         return this.getToken(142, 0);
      }

      public TerminalNode K_IF() {
         return this.getToken(84, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_EXISTS() {
         return this.getToken(74, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public Create_index_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 42;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCreate_index_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCreate_index_stmt(this);
         }

      }
   }

   public static class Compound_select_stmtContext extends ParserRuleContext {
      public List select_core() {
         return this.getRuleContexts(Select_coreContext.class);
      }

      public Select_coreContext select_core(int i) {
         return (Select_coreContext)this.getRuleContext(Select_coreContext.class, i);
      }

      public TerminalNode K_WITH() {
         return this.getToken(151, 0);
      }

      public List common_table_expression() {
         return this.getRuleContexts(Common_table_expressionContext.class);
      }

      public Common_table_expressionContext common_table_expression(int i) {
         return (Common_table_expressionContext)this.getRuleContext(Common_table_expressionContext.class, i);
      }

      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public List ordering_term() {
         return this.getRuleContexts(Ordering_termContext.class);
      }

      public Ordering_termContext ordering_term(int i) {
         return (Ordering_termContext)this.getRuleContext(Ordering_termContext.class, i);
      }

      public TerminalNode K_LIMIT() {
         return this.getToken(102, 0);
      }

      public List expr() {
         return this.getRuleContexts(ExprContext.class);
      }

      public ExprContext expr(int i) {
         return (ExprContext)this.getRuleContext(ExprContext.class, i);
      }

      public List K_UNION() {
         return this.getTokens(141);
      }

      public TerminalNode K_UNION(int i) {
         return this.getToken(141, i);
      }

      public List K_INTERSECT() {
         return this.getTokens(94);
      }

      public TerminalNode K_INTERSECT(int i) {
         return this.getToken(94, i);
      }

      public List K_EXCEPT() {
         return this.getTokens(72);
      }

      public TerminalNode K_EXCEPT(int i) {
         return this.getToken(72, i);
      }

      public TerminalNode K_RECURSIVE() {
         return this.getToken(120, 0);
      }

      public TerminalNode K_OFFSET() {
         return this.getToken(110, 0);
      }

      public List K_ALL() {
         return this.getTokens(33);
      }

      public TerminalNode K_ALL(int i) {
         return this.getToken(33, i);
      }

      public Compound_select_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 41;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCompound_select_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCompound_select_stmt(this);
         }

      }
   }

   public static class Commit_stmtContext extends ParserRuleContext {
      public TerminalNode K_COMMIT() {
         return this.getToken(51, 0);
      }

      public TerminalNode K_END() {
         return this.getToken(70, 0);
      }

      public TerminalNode K_TRANSACTION() {
         return this.getToken(139, 0);
      }

      public Transaction_nameContext transaction_name() {
         return (Transaction_nameContext)this.getRuleContext(Transaction_nameContext.class, 0);
      }

      public Commit_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 40;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterCommit_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitCommit_stmt(this);
         }

      }
   }

   public static class Begin_stmtContext extends ParserRuleContext {
      public TerminalNode K_BEGIN() {
         return this.getToken(42, 0);
      }

      public TerminalNode K_TRANSACTION() {
         return this.getToken(139, 0);
      }

      public TerminalNode K_DEFERRED() {
         return this.getToken(62, 0);
      }

      public TerminalNode K_IMMEDIATE() {
         return this.getToken(86, 0);
      }

      public TerminalNode K_EXCLUSIVE() {
         return this.getToken(73, 0);
      }

      public Transaction_nameContext transaction_name() {
         return (Transaction_nameContext)this.getRuleContext(Transaction_nameContext.class, 0);
      }

      public Begin_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 39;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterBegin_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitBegin_stmt(this);
         }

      }
   }

   public static class Attach_stmtContext extends ParserRuleContext {
      public TerminalNode K_ATTACH() {
         return this.getToken(39, 0);
      }

      public ExprContext expr() {
         return (ExprContext)this.getRuleContext(ExprContext.class, 0);
      }

      public TerminalNode K_AS() {
         return this.getToken(37, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public TerminalNode K_DATABASE() {
         return this.getToken(59, 0);
      }

      public Attach_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 38;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterAttach_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitAttach_stmt(this);
         }

      }
   }

   public static class Analyze_stmtContext extends ParserRuleContext {
      public TerminalNode K_ANALYZE() {
         return this.getToken(35, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public Table_or_index_nameContext table_or_index_name() {
         return (Table_or_index_nameContext)this.getRuleContext(Table_or_index_nameContext.class, 0);
      }

      public Analyze_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 37;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterAnalyze_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitAnalyze_stmt(this);
         }

      }
   }

   public static class Alter_table_stmtContext extends ParserRuleContext {
      public TerminalNode K_ALTER() {
         return this.getToken(34, 0);
      }

      public TerminalNode K_TABLE() {
         return this.getToken(134, 0);
      }

      public Table_nameContext table_name() {
         return (Table_nameContext)this.getRuleContext(Table_nameContext.class, 0);
      }

      public TerminalNode K_RENAME() {
         return this.getToken(125, 0);
      }

      public TerminalNode K_TO() {
         return this.getToken(138, 0);
      }

      public New_table_nameContext new_table_name() {
         return (New_table_nameContext)this.getRuleContext(New_table_nameContext.class, 0);
      }

      public TerminalNode K_ADD() {
         return this.getToken(31, 0);
      }

      public Column_defContext column_def() {
         return (Column_defContext)this.getRuleContext(Column_defContext.class, 0);
      }

      public Database_nameContext database_name() {
         return (Database_nameContext)this.getRuleContext(Database_nameContext.class, 0);
      }

      public TerminalNode K_COLUMN() {
         return this.getToken(50, 0);
      }

      public Alter_table_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 36;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterAlter_table_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitAlter_table_stmt(this);
         }

      }
   }

   public static class Sql_stmtContext extends ParserRuleContext {
      public Alter_table_stmtContext alter_table_stmt() {
         return (Alter_table_stmtContext)this.getRuleContext(Alter_table_stmtContext.class, 0);
      }

      public Analyze_stmtContext analyze_stmt() {
         return (Analyze_stmtContext)this.getRuleContext(Analyze_stmtContext.class, 0);
      }

      public Attach_stmtContext attach_stmt() {
         return (Attach_stmtContext)this.getRuleContext(Attach_stmtContext.class, 0);
      }

      public Begin_stmtContext begin_stmt() {
         return (Begin_stmtContext)this.getRuleContext(Begin_stmtContext.class, 0);
      }

      public Commit_stmtContext commit_stmt() {
         return (Commit_stmtContext)this.getRuleContext(Commit_stmtContext.class, 0);
      }

      public Compound_select_stmtContext compound_select_stmt() {
         return (Compound_select_stmtContext)this.getRuleContext(Compound_select_stmtContext.class, 0);
      }

      public Create_index_stmtContext create_index_stmt() {
         return (Create_index_stmtContext)this.getRuleContext(Create_index_stmtContext.class, 0);
      }

      public Create_table_stmtContext create_table_stmt() {
         return (Create_table_stmtContext)this.getRuleContext(Create_table_stmtContext.class, 0);
      }

      public Create_trigger_stmtContext create_trigger_stmt() {
         return (Create_trigger_stmtContext)this.getRuleContext(Create_trigger_stmtContext.class, 0);
      }

      public Create_view_stmtContext create_view_stmt() {
         return (Create_view_stmtContext)this.getRuleContext(Create_view_stmtContext.class, 0);
      }

      public Create_virtual_table_stmtContext create_virtual_table_stmt() {
         return (Create_virtual_table_stmtContext)this.getRuleContext(Create_virtual_table_stmtContext.class, 0);
      }

      public Delete_stmtContext delete_stmt() {
         return (Delete_stmtContext)this.getRuleContext(Delete_stmtContext.class, 0);
      }

      public Delete_stmt_limitedContext delete_stmt_limited() {
         return (Delete_stmt_limitedContext)this.getRuleContext(Delete_stmt_limitedContext.class, 0);
      }

      public Detach_stmtContext detach_stmt() {
         return (Detach_stmtContext)this.getRuleContext(Detach_stmtContext.class, 0);
      }

      public Drop_index_stmtContext drop_index_stmt() {
         return (Drop_index_stmtContext)this.getRuleContext(Drop_index_stmtContext.class, 0);
      }

      public Drop_table_stmtContext drop_table_stmt() {
         return (Drop_table_stmtContext)this.getRuleContext(Drop_table_stmtContext.class, 0);
      }

      public Drop_trigger_stmtContext drop_trigger_stmt() {
         return (Drop_trigger_stmtContext)this.getRuleContext(Drop_trigger_stmtContext.class, 0);
      }

      public Drop_view_stmtContext drop_view_stmt() {
         return (Drop_view_stmtContext)this.getRuleContext(Drop_view_stmtContext.class, 0);
      }

      public Factored_select_stmtContext factored_select_stmt() {
         return (Factored_select_stmtContext)this.getRuleContext(Factored_select_stmtContext.class, 0);
      }

      public Insert_stmtContext insert_stmt() {
         return (Insert_stmtContext)this.getRuleContext(Insert_stmtContext.class, 0);
      }

      public Pragma_stmtContext pragma_stmt() {
         return (Pragma_stmtContext)this.getRuleContext(Pragma_stmtContext.class, 0);
      }

      public Reindex_stmtContext reindex_stmt() {
         return (Reindex_stmtContext)this.getRuleContext(Reindex_stmtContext.class, 0);
      }

      public Release_stmtContext release_stmt() {
         return (Release_stmtContext)this.getRuleContext(Release_stmtContext.class, 0);
      }

      public Rollback_stmtContext rollback_stmt() {
         return (Rollback_stmtContext)this.getRuleContext(Rollback_stmtContext.class, 0);
      }

      public Savepoint_stmtContext savepoint_stmt() {
         return (Savepoint_stmtContext)this.getRuleContext(Savepoint_stmtContext.class, 0);
      }

      public Simple_select_stmtContext simple_select_stmt() {
         return (Simple_select_stmtContext)this.getRuleContext(Simple_select_stmtContext.class, 0);
      }

      public Select_stmtContext select_stmt() {
         return (Select_stmtContext)this.getRuleContext(Select_stmtContext.class, 0);
      }

      public Update_stmtContext update_stmt() {
         return (Update_stmtContext)this.getRuleContext(Update_stmtContext.class, 0);
      }

      public Update_stmt_limitedContext update_stmt_limited() {
         return (Update_stmt_limitedContext)this.getRuleContext(Update_stmt_limitedContext.class, 0);
      }

      public Vacuum_stmtContext vacuum_stmt() {
         return (Vacuum_stmtContext)this.getRuleContext(Vacuum_stmtContext.class, 0);
      }

      public TerminalNode K_EXPLAIN() {
         return this.getToken(75, 0);
      }

      public TerminalNode K_QUERY() {
         return this.getToken(118, 0);
      }

      public TerminalNode K_PLAN() {
         return this.getToken(115, 0);
      }

      public Sql_stmtContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 35;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSql_stmt(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSql_stmt(this);
         }

      }
   }

   public static class Sql_stmt_listContext extends ParserRuleContext {
      public List sql_stmt() {
         return this.getRuleContexts(Sql_stmtContext.class);
      }

      public Sql_stmtContext sql_stmt(int i) {
         return (Sql_stmtContext)this.getRuleContext(Sql_stmtContext.class, i);
      }

      public Sql_stmt_listContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 34;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSql_stmt_list(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSql_stmt_list(this);
         }

      }
   }

   public static class ErrorContext extends ParserRuleContext {
      public Token UNEXPECTED_CHAR;

      public TerminalNode UNEXPECTED_CHAR() {
         return this.getToken(161, 0);
      }

      public ErrorContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 33;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterError(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitError(this);
         }

      }
   }

   public static class ParseContext extends ParserRuleContext {
      public TerminalNode EOF() {
         return this.getToken(-1, 0);
      }

      public List sql_stmt_list() {
         return this.getRuleContexts(Sql_stmt_listContext.class);
      }

      public Sql_stmt_listContext sql_stmt_list(int i) {
         return (Sql_stmt_listContext)this.getRuleContext(Sql_stmt_listContext.class, i);
      }

      public List error() {
         return this.getRuleContexts(ErrorContext.class);
      }

      public ErrorContext error(int i) {
         return (ErrorContext)this.getRuleContext(ErrorContext.class, i);
      }

      public ParseContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 32;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterParse(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitParse(this);
         }

      }
   }

   public static class DirectionContext extends ParserRuleContext {
      public TerminalNode K_ASC() {
         return this.getToken(38, 0);
      }

      public TerminalNode K_DESC() {
         return this.getToken(64, 0);
      }

      public DirectionContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 31;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterDirection(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitDirection(this);
         }

      }
   }

   public static class AttributeOrderContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public DirectionContext direction() {
         return (DirectionContext)this.getRuleContext(DirectionContext.class, 0);
      }

      public AttributeOrderContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 30;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterAttributeOrder(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitAttributeOrder(this);
         }

      }
   }

   public static class QueryParameterContext extends ParserRuleContext {
      public TerminalNode NUMERIC_LITERAL() {
         return this.getToken(154, 0);
      }

      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public TerminalNode BOOLEAN_LITERAL() {
         return this.getToken(4, 0);
      }

      public QueryParameterContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 29;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterQueryParameter(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitQueryParameter(this);
         }

      }
   }

   public static class QueryParameterLeadingAndTrailingPercentContext extends ParserRuleContext {
      public TerminalNode STRING_LITERAL_WITH_LEADING_AND_TRAILING_PERCENT() {
         return this.getToken(3, 0);
      }

      public QueryParameterLeadingAndTrailingPercentContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 28;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterQueryParameterLeadingAndTrailingPercent(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitQueryParameterLeadingAndTrailingPercent(this);
         }

      }
   }

   public static class QueryParameterLeadingPercentContext extends ParserRuleContext {
      public TerminalNode STRING_LITERAL_WITH_LEADING_PERCENT() {
         return this.getToken(2, 0);
      }

      public QueryParameterLeadingPercentContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 27;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterQueryParameterLeadingPercent(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitQueryParameterLeadingPercent(this);
         }

      }
   }

   public static class QueryParameterTrailingPercentContext extends ParserRuleContext {
      public TerminalNode STRING_LITERAL_WITH_TRAILING_PERCENT() {
         return this.getToken(1, 0);
      }

      public QueryParameterTrailingPercentContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 26;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterQueryParameterTrailingPercent(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitQueryParameterTrailingPercent(this);
         }

      }
   }

   public static class AttributeNameContext extends ParserRuleContext {
      public TerminalNode IDENTIFIER() {
         return this.getToken(153, 0);
      }

      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public AttributeNameContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 25;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterAttributeName(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitAttributeName(this);
         }

      }
   }

   public static class NotHasQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_IS() {
         return this.getToken(96, 0);
      }

      public TerminalNode K_NULL() {
         return this.getToken(108, 0);
      }

      public NotHasQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 24;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterNotHasQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitNotHasQuery(this);
         }

      }
   }

   public static class HasQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_IS() {
         return this.getToken(96, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_NULL() {
         return this.getToken(108, 0);
      }

      public HasQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 23;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterHasQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitHasQuery(this);
         }

      }
   }

   public static class ContainsQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_LIKE() {
         return this.getToken(101, 0);
      }

      public QueryParameterLeadingAndTrailingPercentContext queryParameterLeadingAndTrailingPercent() {
         return (QueryParameterLeadingAndTrailingPercentContext)this.getRuleContext(QueryParameterLeadingAndTrailingPercentContext.class, 0);
      }

      public ContainsQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 22;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterContainsQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitContainsQuery(this);
         }

      }
   }

   public static class EndsWithQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_LIKE() {
         return this.getToken(101, 0);
      }

      public QueryParameterLeadingPercentContext queryParameterLeadingPercent() {
         return (QueryParameterLeadingPercentContext)this.getRuleContext(QueryParameterLeadingPercentContext.class, 0);
      }

      public EndsWithQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 21;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterEndsWithQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitEndsWithQuery(this);
         }

      }
   }

   public static class StartsWithQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_LIKE() {
         return this.getToken(101, 0);
      }

      public QueryParameterTrailingPercentContext queryParameterTrailingPercent() {
         return (QueryParameterTrailingPercentContext)this.getRuleContext(QueryParameterTrailingPercentContext.class, 0);
      }

      public StartsWithQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 20;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterStartsWithQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitStartsWithQuery(this);
         }

      }
   }

   public static class NotInQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_IN() {
         return this.getToken(87, 0);
      }

      public TerminalNode OPEN_PAR() {
         return this.getToken(7, 0);
      }

      public List queryParameter() {
         return this.getRuleContexts(QueryParameterContext.class);
      }

      public QueryParameterContext queryParameter(int i) {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, i);
      }

      public TerminalNode CLOSE_PAR() {
         return this.getToken(8, 0);
      }

      public NotInQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 19;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterNotInQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitNotInQuery(this);
         }

      }
   }

   public static class InQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_IN() {
         return this.getToken(87, 0);
      }

      public TerminalNode OPEN_PAR() {
         return this.getToken(7, 0);
      }

      public List queryParameter() {
         return this.getRuleContexts(QueryParameterContext.class);
      }

      public QueryParameterContext queryParameter(int i) {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, i);
      }

      public TerminalNode CLOSE_PAR() {
         return this.getToken(8, 0);
      }

      public InQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 18;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterInQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitInQuery(this);
         }

      }
   }

   public static class NotBetweenQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public TerminalNode K_BETWEEN() {
         return this.getToken(43, 0);
      }

      public List queryParameter() {
         return this.getRuleContexts(QueryParameterContext.class);
      }

      public QueryParameterContext queryParameter(int i) {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, i);
      }

      public TerminalNode K_AND() {
         return this.getToken(36, 0);
      }

      public NotBetweenQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 17;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterNotBetweenQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitNotBetweenQuery(this);
         }

      }
   }

   public static class BetweenQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode K_BETWEEN() {
         return this.getToken(43, 0);
      }

      public List queryParameter() {
         return this.getRuleContexts(QueryParameterContext.class);
      }

      public QueryParameterContext queryParameter(int i) {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, i);
      }

      public TerminalNode K_AND() {
         return this.getToken(36, 0);
      }

      public BetweenQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 16;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterBetweenQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitBetweenQuery(this);
         }

      }
   }

   public static class GreaterThanQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode GT() {
         return this.getToken(24, 0);
      }

      public QueryParameterContext queryParameter() {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, 0);
      }

      public GreaterThanQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 15;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterGreaterThanQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitGreaterThanQuery(this);
         }

      }
   }

   public static class GreaterThanOrEqualToQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode GT_EQ() {
         return this.getToken(25, 0);
      }

      public QueryParameterContext queryParameter() {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, 0);
      }

      public GreaterThanOrEqualToQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 14;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterGreaterThanOrEqualToQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitGreaterThanOrEqualToQuery(this);
         }

      }
   }

   public static class LessThanQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode LT() {
         return this.getToken(22, 0);
      }

      public QueryParameterContext queryParameter() {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, 0);
      }

      public LessThanQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 13;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterLessThanQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitLessThanQuery(this);
         }

      }
   }

   public static class LessThanOrEqualToQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode LT_EQ() {
         return this.getToken(23, 0);
      }

      public QueryParameterContext queryParameter() {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, 0);
      }

      public LessThanOrEqualToQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 12;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterLessThanOrEqualToQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitLessThanOrEqualToQuery(this);
         }

      }
   }

   public static class NotEqualQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode NOT_EQ2() {
         return this.getToken(28, 0);
      }

      public QueryParameterContext queryParameter() {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, 0);
      }

      public NotEqualQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 11;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterNotEqualQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitNotEqualQuery(this);
         }

      }
   }

   public static class EqualQueryContext extends ParserRuleContext {
      public AttributeNameContext attributeName() {
         return (AttributeNameContext)this.getRuleContext(AttributeNameContext.class, 0);
      }

      public TerminalNode ASSIGN() {
         return this.getToken(10, 0);
      }

      public QueryParameterContext queryParameter() {
         return (QueryParameterContext)this.getRuleContext(QueryParameterContext.class, 0);
      }

      public EqualQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 10;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterEqualQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitEqualQuery(this);
         }

      }
   }

   public static class SimpleQueryContext extends ParserRuleContext {
      public EqualQueryContext equalQuery() {
         return (EqualQueryContext)this.getRuleContext(EqualQueryContext.class, 0);
      }

      public NotEqualQueryContext notEqualQuery() {
         return (NotEqualQueryContext)this.getRuleContext(NotEqualQueryContext.class, 0);
      }

      public LessThanOrEqualToQueryContext lessThanOrEqualToQuery() {
         return (LessThanOrEqualToQueryContext)this.getRuleContext(LessThanOrEqualToQueryContext.class, 0);
      }

      public LessThanQueryContext lessThanQuery() {
         return (LessThanQueryContext)this.getRuleContext(LessThanQueryContext.class, 0);
      }

      public GreaterThanOrEqualToQueryContext greaterThanOrEqualToQuery() {
         return (GreaterThanOrEqualToQueryContext)this.getRuleContext(GreaterThanOrEqualToQueryContext.class, 0);
      }

      public GreaterThanQueryContext greaterThanQuery() {
         return (GreaterThanQueryContext)this.getRuleContext(GreaterThanQueryContext.class, 0);
      }

      public BetweenQueryContext betweenQuery() {
         return (BetweenQueryContext)this.getRuleContext(BetweenQueryContext.class, 0);
      }

      public NotBetweenQueryContext notBetweenQuery() {
         return (NotBetweenQueryContext)this.getRuleContext(NotBetweenQueryContext.class, 0);
      }

      public InQueryContext inQuery() {
         return (InQueryContext)this.getRuleContext(InQueryContext.class, 0);
      }

      public NotInQueryContext notInQuery() {
         return (NotInQueryContext)this.getRuleContext(NotInQueryContext.class, 0);
      }

      public StartsWithQueryContext startsWithQuery() {
         return (StartsWithQueryContext)this.getRuleContext(StartsWithQueryContext.class, 0);
      }

      public EndsWithQueryContext endsWithQuery() {
         return (EndsWithQueryContext)this.getRuleContext(EndsWithQueryContext.class, 0);
      }

      public ContainsQueryContext containsQuery() {
         return (ContainsQueryContext)this.getRuleContext(ContainsQueryContext.class, 0);
      }

      public HasQueryContext hasQuery() {
         return (HasQueryContext)this.getRuleContext(HasQueryContext.class, 0);
      }

      public NotHasQueryContext notHasQuery() {
         return (NotHasQueryContext)this.getRuleContext(NotHasQueryContext.class, 0);
      }

      public TerminalNode OPEN_PAR() {
         return this.getToken(7, 0);
      }

      public SimpleQueryContext simpleQuery() {
         return (SimpleQueryContext)this.getRuleContext(SimpleQueryContext.class, 0);
      }

      public TerminalNode CLOSE_PAR() {
         return this.getToken(8, 0);
      }

      public SimpleQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 9;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterSimpleQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitSimpleQuery(this);
         }

      }
   }

   public static class NotQueryContext extends ParserRuleContext {
      public TerminalNode K_NOT() {
         return this.getToken(106, 0);
      }

      public QueryContext query() {
         return (QueryContext)this.getRuleContext(QueryContext.class, 0);
      }

      public TerminalNode OPEN_PAR() {
         return this.getToken(7, 0);
      }

      public TerminalNode CLOSE_PAR() {
         return this.getToken(8, 0);
      }

      public NotQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 8;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterNotQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitNotQuery(this);
         }

      }
   }

   public static class OrQueryContext extends ParserRuleContext {
      public TerminalNode OPEN_PAR() {
         return this.getToken(7, 0);
      }

      public List query() {
         return this.getRuleContexts(QueryContext.class);
      }

      public QueryContext query(int i) {
         return (QueryContext)this.getRuleContext(QueryContext.class, i);
      }

      public List K_OR() {
         return this.getTokens(112);
      }

      public TerminalNode K_OR(int i) {
         return this.getToken(112, i);
      }

      public TerminalNode CLOSE_PAR() {
         return this.getToken(8, 0);
      }

      public OrQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 7;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterOrQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitOrQuery(this);
         }

      }
   }

   public static class AndQueryContext extends ParserRuleContext {
      public TerminalNode OPEN_PAR() {
         return this.getToken(7, 0);
      }

      public List query() {
         return this.getRuleContexts(QueryContext.class);
      }

      public QueryContext query(int i) {
         return (QueryContext)this.getRuleContext(QueryContext.class, i);
      }

      public List K_AND() {
         return this.getTokens(36);
      }

      public TerminalNode K_AND(int i) {
         return this.getToken(36, i);
      }

      public TerminalNode CLOSE_PAR() {
         return this.getToken(8, 0);
      }

      public AndQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 6;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterAndQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitAndQuery(this);
         }

      }
   }

   public static class LogicalQueryContext extends ParserRuleContext {
      public AndQueryContext andQuery() {
         return (AndQueryContext)this.getRuleContext(AndQueryContext.class, 0);
      }

      public OrQueryContext orQuery() {
         return (OrQueryContext)this.getRuleContext(OrQueryContext.class, 0);
      }

      public NotQueryContext notQuery() {
         return (NotQueryContext)this.getRuleContext(NotQueryContext.class, 0);
      }

      public LogicalQueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 5;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterLogicalQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitLogicalQuery(this);
         }

      }
   }

   public static class QueryContext extends ParserRuleContext {
      public LogicalQueryContext logicalQuery() {
         return (LogicalQueryContext)this.getRuleContext(LogicalQueryContext.class, 0);
      }

      public SimpleQueryContext simpleQuery() {
         return (SimpleQueryContext)this.getRuleContext(SimpleQueryContext.class, 0);
      }

      public QueryContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 4;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterQuery(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitQuery(this);
         }

      }
   }

   public static class OrderByClauseContext extends ParserRuleContext {
      public TerminalNode K_ORDER() {
         return this.getToken(113, 0);
      }

      public TerminalNode K_BY() {
         return this.getToken(44, 0);
      }

      public List attributeOrder() {
         return this.getRuleContexts(AttributeOrderContext.class);
      }

      public AttributeOrderContext attributeOrder(int i) {
         return (AttributeOrderContext)this.getRuleContext(AttributeOrderContext.class, i);
      }

      public OrderByClauseContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 3;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterOrderByClause(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitOrderByClause(this);
         }

      }
   }

   public static class WhereClauseContext extends ParserRuleContext {
      public TerminalNode K_WHERE() {
         return this.getToken(150, 0);
      }

      public QueryContext query() {
         return (QueryContext)this.getRuleContext(QueryContext.class, 0);
      }

      public WhereClauseContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 2;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterWhereClause(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitWhereClause(this);
         }

      }
   }

   public static class IndexedCollectionContext extends ParserRuleContext {
      public TerminalNode IDENTIFIER() {
         return this.getToken(153, 0);
      }

      public TerminalNode STRING_LITERAL() {
         return this.getToken(156, 0);
      }

      public IndexedCollectionContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 1;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterIndexedCollection(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitIndexedCollection(this);
         }

      }
   }

   public static class StartContext extends ParserRuleContext {
      public TerminalNode K_SELECT() {
         return this.getToken(132, 0);
      }

      public TerminalNode STAR() {
         return this.getToken(11, 0);
      }

      public TerminalNode K_FROM() {
         return this.getToken(79, 0);
      }

      public IndexedCollectionContext indexedCollection() {
         return (IndexedCollectionContext)this.getRuleContext(IndexedCollectionContext.class, 0);
      }

      public TerminalNode EOF() {
         return this.getToken(-1, 0);
      }

      public WhereClauseContext whereClause() {
         return (WhereClauseContext)this.getRuleContext(WhereClauseContext.class, 0);
      }

      public OrderByClauseContext orderByClause() {
         return (OrderByClauseContext)this.getRuleContext(OrderByClauseContext.class, 0);
      }

      public StartContext(ParserRuleContext parent, int invokingState) {
         super(parent, invokingState);
      }

      public int getRuleIndex() {
         return 0;
      }

      public void enterRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).enterStart(this);
         }

      }

      public void exitRule(ParseTreeListener listener) {
         if (listener instanceof SQLGrammarListener) {
            ((SQLGrammarListener)listener).exitStart(this);
         }

      }
   }
}
