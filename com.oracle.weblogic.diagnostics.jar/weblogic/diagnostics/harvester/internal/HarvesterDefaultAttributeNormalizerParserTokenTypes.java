package weblogic.diagnostics.harvester.internal;

public interface HarvesterDefaultAttributeNormalizerParserTokenTypes {
   int EOF = 1;
   int NULL_TREE_LOOKAHEAD = 3;
   int WS = 4;
   int LSQPAREN = 5;
   int RSQPAREN = 6;
   int COMMA = 7;
   int PERIOD = 8;
   int SEMICOLON = 9;
   int DIGIT = 10;
   int LETTER = 11;
   int INTEGER = 12;
   int IDENTIFIER = 13;
   int STAR_WILDCARD = 14;
   int KEYSPEC = 15;
}
