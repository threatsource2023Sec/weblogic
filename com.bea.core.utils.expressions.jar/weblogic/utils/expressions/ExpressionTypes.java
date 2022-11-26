package weblogic.utils.expressions;

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
   int XMLSELECT = 25;
   int INTEGER_STRING = 26;
   int FLOAT_STRING = 27;
   String[] TYPE_NAMES = new String[]{"AND", "OR", "NOT", "ISNULL", "NOTNULL", "EQ", "LESS_THAN", "GREATER_THAN", "LESS_THAN_OR_EQUAL", "GREATER_THAN_OR_EQUAL", "NOT_EQUAL", "LIKE", "BETWEEN", "IN", "TRUE", "FALSE", "NULL", "ID", "STRING", "INTEGER", "FLOAT", "MINUS", "PLUS", "TIMES", "DIV", "xpath", "INTEGER_STRING"};
}
