package org.python.antlr.runtime;

import java.util.Map;

public class RecognizerSharedState {
   public BitSet[] following = new BitSet[100];
   public int _fsp = -1;
   public boolean errorRecovery = false;
   public int lastErrorIndex = -1;
   public boolean failed = false;
   public int syntaxErrors = 0;
   public int backtracking = 0;
   public Map[] ruleMemo;
   public Token token;
   public int tokenStartCharIndex = -1;
   public int tokenStartLine;
   public int tokenStartCharPositionInLine;
   public int channel;
   public int type;
   public String text;
}
