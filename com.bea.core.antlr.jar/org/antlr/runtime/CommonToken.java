package org.antlr.runtime;

import java.io.Serializable;

public class CommonToken implements Token, Serializable {
   protected int type;
   protected int line;
   protected int charPositionInLine = -1;
   protected int channel = 0;
   protected transient CharStream input;
   protected String text;
   protected int index = -1;
   protected int start;
   protected int stop;

   public CommonToken(int type) {
      this.type = type;
   }

   public CommonToken(CharStream input, int type, int channel, int start, int stop) {
      this.input = input;
      this.type = type;
      this.channel = channel;
      this.start = start;
      this.stop = stop;
   }

   public CommonToken(int type, String text) {
      this.type = type;
      this.channel = 0;
      this.text = text;
   }

   public CommonToken(Token oldToken) {
      this.text = oldToken.getText();
      this.type = oldToken.getType();
      this.line = oldToken.getLine();
      this.index = oldToken.getTokenIndex();
      this.charPositionInLine = oldToken.getCharPositionInLine();
      this.channel = oldToken.getChannel();
      this.input = oldToken.getInputStream();
      if (oldToken instanceof CommonToken) {
         this.start = ((CommonToken)oldToken).start;
         this.stop = ((CommonToken)oldToken).stop;
      }

   }

   public int getType() {
      return this.type;
   }

   public void setLine(int line) {
      this.line = line;
   }

   public String getText() {
      if (this.text != null) {
         return this.text;
      } else if (this.input == null) {
         return null;
      } else {
         int n = this.input.size();
         return this.start < n && this.stop < n ? this.input.substring(this.start, this.stop) : "<EOF>";
      }
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

   public int getStartIndex() {
      return this.start;
   }

   public void setStartIndex(int start) {
      this.start = start;
   }

   public int getStopIndex() {
      return this.stop;
   }

   public void setStopIndex(int stop) {
      this.stop = stop;
   }

   public int getTokenIndex() {
      return this.index;
   }

   public void setTokenIndex(int index) {
      this.index = index;
   }

   public CharStream getInputStream() {
      return this.input;
   }

   public void setInputStream(CharStream input) {
      this.input = input;
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

      return "[@" + this.getTokenIndex() + "," + this.start + ":" + this.stop + "='" + txt + "',<" + this.type + ">" + channelStr + "," + this.line + ":" + this.getCharPositionInLine() + "]";
   }
}
