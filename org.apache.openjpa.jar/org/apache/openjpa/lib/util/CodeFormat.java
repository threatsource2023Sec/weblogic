package org.apache.openjpa.lib.util;

public final class CodeFormat implements Cloneable {
   private static final String _sep = J2DoPrivHelper.getLineSeparator();
   private String _tab = "\t";
   private boolean _spaceBeforeParen = false;
   private boolean _spaceInParen = false;
   private boolean _braceOnSameLine = true;
   private boolean _braceAtSameTabLevel = false;
   private boolean _scoreBeforeFieldName = false;
   private int _linesBetweenSections = 1;
   private StringBuffer _buf = new StringBuffer();

   public int getTabSpaces() {
      return this._tab.equals("\t") ? 0 : this._tab.length();
   }

   public void setTabSpaces(int tab) {
      if (tab == 0) {
         this._tab = "\t";
      } else {
         StringBuffer tabs = new StringBuffer(tab);

         for(int i = 0; i < tab; ++i) {
            tabs.append(" ");
         }

         this._tab = tabs.toString();
      }

   }

   public boolean getSpaceBeforeParen() {
      return this._spaceBeforeParen;
   }

   public void setSpaceBeforeParen(boolean spaceBeforeParen) {
      this._spaceBeforeParen = spaceBeforeParen;
   }

   public boolean getSpaceInParen() {
      return this._spaceInParen;
   }

   public void setSpaceInParen(boolean spaceInParen) {
      this._spaceInParen = spaceInParen;
   }

   public boolean getBraceOnSameLine() {
      return this._braceOnSameLine;
   }

   public void setBraceOnSameLine(boolean braceOnSameLine) {
      this._braceOnSameLine = braceOnSameLine;
   }

   public boolean getBraceAtSameTabLevel() {
      return this._braceAtSameTabLevel;
   }

   public void setBraceAtSameTabLevel(boolean braceAtSameTabLevel) {
      this._braceAtSameTabLevel = braceAtSameTabLevel;
   }

   public boolean getScoreBeforeFieldName() {
      return this._scoreBeforeFieldName;
   }

   public void setScoreBeforeFieldName(boolean scoreBeforeFieldName) {
      this._scoreBeforeFieldName = scoreBeforeFieldName;
   }

   public int getLinesBetweenSections() {
      return this._linesBetweenSections;
   }

   public void setLinesBetweenSections(int linesBetweenSections) {
      this._linesBetweenSections = linesBetweenSections;
   }

   public String getEndl() {
      return this.getEndl(1);
   }

   public String getEndl(int num) {
      if (num == 0) {
         return "";
      } else if (num == 1) {
         return _sep;
      } else {
         StringBuffer buf = new StringBuffer(_sep.length() * num);

         for(int i = 0; i < num; ++i) {
            buf.append(_sep);
         }

         return buf.toString();
      }
   }

   public String getEndl(int num, int tabs) {
      return this.getEndl(num) + this.getTab(tabs);
   }

   public String getAfterSection() {
      return this.getEndl(this.getLinesBetweenSections() + 1);
   }

   public String getOpenParen(boolean methodOrIf) {
      if (this._spaceBeforeParen && methodOrIf && this._spaceInParen) {
         return " ( ";
      } else if (this._spaceBeforeParen && methodOrIf) {
         return " (";
      } else {
         return this._spaceInParen ? "( " : "(";
      }
   }

   public String getCloseParen() {
      return this._spaceInParen ? " )" : ")";
   }

   public String getParens() {
      return this._spaceBeforeParen ? " ()" : "()";
   }

   public String getOpenBrace(int tabLevel) {
      if (this._braceOnSameLine) {
         return " {";
      } else {
         return this._braceAtSameTabLevel ? this.getEndl() + this.getTab(tabLevel) + "{" : this.getEndl() + this.getTab(tabLevel - 1) + "{";
      }
   }

   public String getCloseBrace(int tabLevel) {
      return this._braceAtSameTabLevel ? this.getTab(tabLevel) + "}" : this.getTab(tabLevel - 1) + "}";
   }

   public String getExtendsDec(int tabLevel) {
      if (this._braceOnSameLine) {
         return " extends";
      } else {
         return this._braceAtSameTabLevel ? this.getEndl() + this.getTab(tabLevel) + "extends" : this.getEndl() + this.getTab(tabLevel) + "extends";
      }
   }

   public String getImplementsDec(int tabLevel) {
      if (this._braceOnSameLine) {
         return " implements";
      } else {
         return this._braceAtSameTabLevel ? this.getEndl() + this.getTab(tabLevel) + "implements" : this.getEndl() + this.getTab(tabLevel) + "implements";
      }
   }

   public String getThrowsDec(int tabLevel) {
      if (this._braceOnSameLine) {
         return " throws";
      } else {
         return this._braceAtSameTabLevel ? this.getEndl() + this.getTab(tabLevel) + "throws" : this.getEndl() + this.getTab(tabLevel) + "throws";
      }
   }

   public String getTab() {
      return this.getTab(1);
   }

   public String getTab(int tabLevel) {
      if (tabLevel == 0) {
         return "";
      } else if (tabLevel == 1) {
         return this._tab;
      } else {
         StringBuffer tabs = new StringBuffer(this._tab.length() * tabLevel);

         for(int i = 0; i < tabLevel; ++i) {
            tabs.append(this._tab);
         }

         return tabs.toString();
      }
   }

   public String getParametrizedType(String[] typenames) {
      StringBuffer buf = new StringBuffer();
      buf.append("<");

      for(int i = 0; i < typenames.length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(typenames[i]);
      }

      buf.append(">");
      return buf.toString();
   }

   public String getFieldName(String fieldName) {
      return this._scoreBeforeFieldName ? "_" + fieldName : fieldName;
   }

   public StringBuffer getBuffer() {
      return this._buf;
   }

   public CodeFormat append(boolean val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(byte val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(char val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(double val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(float val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(int val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(long val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(short val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat append(Object val) {
      this._buf.append(val);
      return this;
   }

   public CodeFormat endl() {
      this._buf.append(this.getEndl());
      return this;
   }

   public CodeFormat endl(int num) {
      this._buf.append(this.getEndl(num));
      return this;
   }

   public CodeFormat endl(int num, int tabs) {
      this._buf.append(this.getEndl(num, tabs));
      return this;
   }

   public CodeFormat afterSection() {
      this._buf.append(this.getAfterSection());
      return this;
   }

   public CodeFormat openParen(boolean methodOrIf) {
      this._buf.append(this.getOpenParen(methodOrIf));
      return this;
   }

   public CodeFormat closeParen() {
      this._buf.append(this.getCloseParen());
      return this;
   }

   public CodeFormat parens() {
      this._buf.append(this.getParens());
      return this;
   }

   public CodeFormat openBrace(int tabLevel) {
      this._buf.append(this.getOpenBrace(tabLevel));
      return this;
   }

   public CodeFormat closeBrace(int tabLevel) {
      this._buf.append(this.getCloseBrace(tabLevel));
      return this;
   }

   public CodeFormat extendsDec(int tabLevel) {
      this._buf.append(this.getExtendsDec(tabLevel));
      return this;
   }

   public CodeFormat implementsDec(int tabLevel) {
      this._buf.append(this.getImplementsDec(tabLevel));
      return this;
   }

   public CodeFormat throwsDec(int tabLevel) {
      this._buf.append(this.getThrowsDec(tabLevel));
      return this;
   }

   public CodeFormat tab() {
      this._buf.append(this.getTab());
      return this;
   }

   public CodeFormat tab(int tabLevel) {
      this._buf.append(this.getTab(tabLevel));
      return this;
   }

   public CodeFormat fieldName(String name) {
      this._buf.append(this.getFieldName(name));
      return this;
   }

   public void clear() {
      this._buf = new StringBuffer();
   }

   public String toString() {
      return this._buf.toString();
   }

   public int length() {
      return this._buf.length();
   }

   public Object clone() {
      CodeFormat format = new CodeFormat();
      format._tab = this._tab;
      format._spaceBeforeParen = this._spaceBeforeParen;
      format._spaceInParen = this._spaceInParen;
      format._braceOnSameLine = this._braceOnSameLine;
      format._braceAtSameTabLevel = this._braceAtSameTabLevel;
      format._scoreBeforeFieldName = this._scoreBeforeFieldName;
      format._linesBetweenSections = this._linesBetweenSections;
      return format;
   }
}
