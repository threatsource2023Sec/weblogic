package weblogic.ejb.container.cmp11.rdbms.finders;

public interface WLQLExpressionTypes {
   int AND = 0;
   int OR = 1;
   int NOT = 2;
   int EQ = 3;
   int LESS_THAN = 4;
   int GREATER_THAN = 5;
   int LESS_THAN_OR_EQUAL = 6;
   int GREATER_THAN_OR_EQUAL = 7;
   int LIKE = 8;
   int ID = 9;
   int STRING = 10;
   int NUMBER = 11;
   int SPECIAL = 12;
   int VARIABLE = 13;
   int IS_NULL = 14;
   int IS_NOT_NULL = 15;
   int ORDER_BY = 16;
   int EMPTY = 17;
   String[] TYPE_NAMES = new String[]{"AND", "OR", "NOT", "EQ", "LESS_THAN", "GREATER_THAN", "LESS_THAN_OR_EQUAL", "GREATER_THAN_OR_EQUAL", "LIKE", "ID", "STRING", "NUMBER", "SPECIAL", "VARIABLE", "IS_NULL", "IS_NOT_NULL", "ORDER_BY", "[]"};
}
