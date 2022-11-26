package org.antlr.runtime;

public class ClassicToken implements Token {
   protected String text;
   protected int type;
   protected int line;
   protected int charPositionInLine;
   protected int channel = 0;
   protected int index;

   public ClassicToken(int type) {
      this.type = type;
   }

   public ClassicToken(Token oldToken) {
      this.text = oldToken.getText();
      this.type = oldToken.getType();
      this.line = oldToken.getLine();
      this.charPositionInLine = oldToken.getCharPositionInLine();
      this.channel = oldToken.getChannel();
   }

   public ClassicToken(int type, String text) {
      this.type = type;
      this.text = text;
   }

   public ClassicToken(int type, String text, int channel) {
      this.type = type;
      this.text = text;
      this.channel = channel;
   }

   public int getType() {
      return this.type;
   }

   public void setLine(int line) {
      this.line = line;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public int getLine() {
      return this.line;
   }

   public int getCharPositionInLine() {
      return this.charPositionInLine;
   }

   public void setCharPositionInLine(int charPositionInLine) {
      this.charPositionInLine = charPositionInLine;
   }

   public int getChannel() {
      return this.channel;
   }

   public void setChannel(int channel) {
      this.channel = channel;
   }

   public void setType(int type) {
      this.type = type;
   }

   public int getTokenIndex() {
      return this.index;
   }

   public void setTokenIndex(int index) {
      this.index = index;
   }

   public CharStream getInputStream() {
      return null;
   }

   public void setInputStream(CharStream input) {
   }

   public String toString() {
      String channelStr = "";
      if (this.channel > 0) {
         channelStr = ",channel=" + this.channel;
      }

      String txt = this.getText();
      if (txt != null) {
         txt = txt.replaceAll("\n", "\\\\n");
         txt = txt.replaceAll("\r", "\\\\r");
         txt = txt.replaceAll("\t", "\\\\t");
      } else {
         txt = "<no text>";
      }

      return "[@" + this.getTokenIndex() + ",'" + txt + "',<" + this.type + ">" + channelStr + "," + this.line + ":" + this.getCharPositionInLine() + "]";
   }
}
