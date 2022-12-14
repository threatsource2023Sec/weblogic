package weblogic.ejb.container.cmp.rdbms.finders;

public interface ExpressionTypes {
   int AND = 0;
   int OR = 1;
   int NOT = 2;
   int ISNULL = 3;
   int NOTNULL = 4;
   int EQ = 5;
   int LESS_THAN = 6;
   int GREATER_THAN = 7;
   int LESS_THAN_OR_EQUAL = 8;
   int GREATER_THAN_OR_EQUAL = 9;
   int NOT_EQUAL = 10;
   int LIKE = 11;
   int BETWEEN = 12;
   int IN = 13;
   int TRUE = 14;
   int FALSE = 15;
   int NULL = 16;
   int ID = 17;
   int STRING = 18;
   int INTEGER = 19;
   int FLOAT = 20;
   int MINUS = 21;
   int PLUS = 22;
   int TIMES = 23;
   int DIV = 24;
   int VARIABLE = 25;
   int WHERE = 26;
   int FROM = 27;
   int CORR_IN = 28;
   int CORR_FOR = 29;
   int CORR_ID = 30;
   int CORR_COLLECTION_EXPR = 31;
   int CORR_SINGLE_EXPR = 32;
   int ROOT = 33;
   int SELECT = 34;
   int FINDERSTRING = 35;
   int ORDERBY = 36;
   int DUMMY = 37;
   int FINDERPROPS = 38;
   int AS = 39;
   int ENTITY_OBJECT_CAST = 40;
   int ISEMPTY = 41;
   int NOTEMPTY = 42;
   int SUBQUERY = 43;
   int MIN = 44;
   int MAX = 45;
   int AVG = 46;
   int SUM = 47;
   int COUNT = 48;
   int ALL = 49;
   int STAR = 50;
   int DISTINCT = 51;
   int ORDERBY_ELEMENT = 52;
   int PRIMITIVE_WRAPPER_CONSTRUCTOR = 53;
   int FUNCTION_CONCAT = 54;
   int FUNCTION_SUBSTRING = 55;
   int FUNCTION_LOCATE = 56;
   int FUNCTION_LENGTH = 57;
   int FUNCTION_ABS = 58;
   int FUNCTION_SQRT = 59;
   int SELECT_HINT = 60;
   int OBJECT = 61;
   int MEMBER = 62;
   int NOT_MEMBER = 63;
   int ANY = 64;
   int EXISTS = 65;
   int ORDERBY_ASC = 66;
   int ORDERBY_DESC = 67;
   int GROUP_BY = 68;
   int HAVING = 69;
   int UPPER = 70;
   int LOWER = 71;
   int NOOP = 72;
   int COLLECTION_MEMBER = 73;
   int RANGE_VARIABLE = 74;
   int ESCAPE = 75;
   int FUNCTION_MOD = 76;
   int NOT_BETWEEN = 77;
   String[] TYPE_NAMES = new String[]{"AND", "OR", "NOT", "ISNULL", "NOTNULL", "EQ", "LESS_THAN", "GREATER_THAN", "LESS_THAN_OR_EQUAL", "GREATER_THAN_OR_EQUAL", "NOT_EQUAL", "LIKE", "BETWEEN", "IN", "TRUE", "FALSE", "NULL", "ID", "STRING", "INTEGER", "FLOAT", "MINUS", "PLUS", "TIMES", "DIV", "VARIABLE", "WHERE", "FROM", "CORR_IN", "CORR_FOR", "CORR_ID", "CORR_COLLECTION_EXPR", "CORR_SINGLE_EXPR", "ROOT", "SELECT", "FINDERSTRING", "ORDERBY", "DUMMY", "FINDERPROPS", "AS", "ENTITY_OBJECT_CAST", "ISEMPTY", "NOTEMPTY", "SUBQUERY", "MIN", "MAX", "AVG", "SUM", "COUNT", "ALL", "STAR", "DISTINCT", "ORDERBY_ELEMENT", "PRIMITIVE_WRAPPER_CONSTRUCTOR", "FUNCTION_CONCAT", "FUNCTION_SUBSTRING", "FUNCTION_LOCATE", "FUNCTION_LENGTH", "FUNCTION_ABS", "FUNCTION_SQRT", "SELECT_HINT", "OBJECT", "MEMBER", "NOT_MEMBER", "ANY", "EXISTS", "ORDERBY_ASC", "ORDERBY_DESC", "GROUP_BY", "HAVING", "UPPER", "LOWER", "NOOP", "COLLECTION_MEMBER", "RANGE_VARIABLE", "ESCAPE", "FUNCTION_MOD", "NOT_BETWEEN"};
}
