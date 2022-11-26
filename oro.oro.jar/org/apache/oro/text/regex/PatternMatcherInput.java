package org.apache.oro.text.regex;

public final class PatternMatcherInput {
   String _originalStringInput;
   char[] _originalCharInput;
   char[] _originalBuffer;
   char[] _toLowerBuffer;
   int _beginOffset;
   int _endOffset;
   int _currentOffset;
   int _matchBeginOffset;
   int _matchEndOffset;

   public PatternMatcherInput(String var1, int var2, int var3) {
      this._matchBeginOffset = -1;
      this._matchEndOffset = -1;
      this.setInput(var1, var2, var3);
   }

   public PatternMatcherInput(String var1) {
      this((String)var1, 0, var1.length());
   }

   public PatternMatcherInput(char[] var1, int var2, int var3) {
      this._matchBeginOffset = -1;
      this._matchEndOffset = -1;
      this.setInput(var1, var2, var3);
   }

   public PatternMatcherInput(char[] var1) {
      this((char[])var1, 0, var1.length);
   }

   public int length() {
      return this._endOffset - this._beginOffset;
   }

   public void setInput(String var1, int var2, int var3) {
      this._originalStringInput = var1;
      this._originalCharInput = null;
      this._toLowerBuffer = null;
      this._originalBuffer = var1.toCharArray();
      this.setCurrentOffset(var2);
      this.setBeginOffset(var2);
      this.setEndOffset(this._beginOffset + var3);
   }

   public void setInput(String var1) {
      this.setInput((String)var1, 0, var1.length());
   }

   public void setInput(char[] var1, int var2, int var3) {
      this._originalStringInput = null;
      this._toLowerBuffer = null;
      this._originalBuffer = this._originalCharInput = var1;
      this.setCurrentOffset(var2);
      this.setBeginOffset(var2);
      this.setEndOffset(this._beginOffset + var3);
   }

   public void setInput(char[] var1) {
      this.setInput((char[])var1, 0, var1.length);
   }

   public char charAt(int var1) {
      return this._originalBuffer[this._beginOffset + var1];
   }

   public String substring(int var1, int var2) {
      return new String(this._originalBuffer, this._beginOffset + var1, var2 - var1);
   }

   public String substring(int var1) {
      var1 += this._beginOffset;
      return new String(this._originalBuffer, var1, this._endOffset - var1);
   }

   public Object getInput() {
      return this._originalStringInput == null ? this._originalCharInput : this._originalStringInput;
   }

   public char[] getBuffer() {
      return this._originalBuffer;
   }

   public boolean endOfInput() {
      return this._currentOffset >= this._endOffset;
   }

   public int getBeginOffset() {
      return this._beginOffset;
   }

   public int getEndOffset() {
      return this._endOffset;
   }

   public int getCurrentOffset() {
      return this._currentOffset;
   }

   public void setBeginOffset(int var1) {
      this._beginOffset = var1;
   }

   public void setEndOffset(int var1) {
      this._endOffset = var1;
   }

   public void setCurrentOffset(int var1) {
      this._currentOffset = var1;
      this.setMatchOffsets(-1, -1);
   }

   public String toString() {
      return new String(this._originalBuffer, this._beginOffset, this.length());
   }

   public String preMatch() {
      return new String(this._originalBuffer, this._beginOffset, this._matchBeginOffset - this._beginOffset);
   }

   public String postMatch() {
      return new String(this._originalBuffer, this._matchEndOffset, this._endOffset - this._matchEndOffset);
   }

   public String match() {
      return new String(this._originalBuffer, this._matchBeginOffset, this._matchEndOffset - this._matchBeginOffset);
   }

   public void setMatchOffsets(int var1, int var2) {
      this._matchBeginOffset = var1;
      this._matchEndOffset = var2;
   }

   public int getMatchBeginOffset() {
      return this._matchBeginOffset;
   }

   public int getMatchEndOffset() {
      return this._matchEndOffset;
   }
}
