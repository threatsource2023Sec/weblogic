package org.antlr.runtime;

public interface Token {
   int EOR_TOKEN_TYPE = 1;
   int DOWN = 2;
   int UP = 3;
   int MIN_TOKEN_TYPE = 4;
   int EOF = -1;
   int INVALID_TOKEN_TYPE = 0;
   Token INVALID_TOKEN = new CommonToken(0);
   Token SKIP_TOKEN = new CommonToken(0);
   int DEFAULT_CHANNEL = 0;
   int HIDDEN_CHANNEL = 99;

   String getText();

   void setText(String var1);

   int getType();

   void setType(int var1);

   int getLine();

   void setLine(int var1);

   int getCharPositionInLine();

   void setCharPositionInLine(int var1);

   int getChannel();

   void setChannel(int var1);

   int getTokenIndex();

   void setTokenIndex(int var1);

   CharStream getInputStream();

   void setInputStream(CharStream var1);
}
