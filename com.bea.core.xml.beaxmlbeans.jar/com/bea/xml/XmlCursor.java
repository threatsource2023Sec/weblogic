package com.bea.xml;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map;
import javax.xml.namespace.QName;

public interface XmlCursor extends XmlTokenSource {
   void dispose();

   boolean toCursor(XmlCursor var1);

   void push();

   boolean pop();

   void selectPath(String var1);

   void selectPath(String var1, XmlOptions var2);

   boolean hasNextSelection();

   boolean toNextSelection();

   boolean toSelection(int var1);

   int getSelectionCount();

   void addToSelection();

   void clearSelections();

   boolean toBookmark(XmlBookmark var1);

   XmlBookmark toNextBookmark(Object var1);

   XmlBookmark toPrevBookmark(Object var1);

   QName getName();

   void setName(QName var1);

   String namespaceForPrefix(String var1);

   String prefixForNamespace(String var1);

   void getAllNamespaces(Map var1);

   XmlObject getObject();

   TokenType currentTokenType();

   boolean isStartdoc();

   boolean isEnddoc();

   boolean isStart();

   boolean isEnd();

   boolean isText();

   boolean isAttr();

   boolean isNamespace();

   boolean isComment();

   boolean isProcinst();

   boolean isContainer();

   boolean isFinish();

   boolean isAnyAttr();

   TokenType prevTokenType();

   boolean hasNextToken();

   boolean hasPrevToken();

   TokenType toNextToken();

   TokenType toPrevToken();

   TokenType toFirstContentToken();

   TokenType toEndToken();

   int toNextChar(int var1);

   int toPrevChar(int var1);

   boolean toNextSibling();

   boolean toPrevSibling();

   boolean toParent();

   boolean toFirstChild();

   boolean toLastChild();

   boolean toChild(String var1);

   boolean toChild(String var1, String var2);

   boolean toChild(QName var1);

   boolean toChild(int var1);

   boolean toChild(QName var1, int var2);

   boolean toNextSibling(String var1);

   boolean toNextSibling(String var1, String var2);

   boolean toNextSibling(QName var1);

   boolean toFirstAttribute();

   boolean toLastAttribute();

   boolean toNextAttribute();

   boolean toPrevAttribute();

   String getAttributeText(QName var1);

   boolean setAttributeText(QName var1, String var2);

   boolean removeAttribute(QName var1);

   String getTextValue();

   int getTextValue(char[] var1, int var2, int var3);

   void setTextValue(String var1);

   void setTextValue(char[] var1, int var2, int var3);

   String getChars();

   int getChars(char[] var1, int var2, int var3);

   void toStartDoc();

   void toEndDoc();

   boolean isInSameDocument(XmlCursor var1);

   int comparePosition(XmlCursor var1);

   boolean isLeftOf(XmlCursor var1);

   boolean isAtSamePositionAs(XmlCursor var1);

   boolean isRightOf(XmlCursor var1);

   XmlCursor execQuery(String var1);

   XmlCursor execQuery(String var1, XmlOptions var2);

   ChangeStamp getDocChangeStamp();

   void setBookmark(XmlBookmark var1);

   XmlBookmark getBookmark(Object var1);

   void clearBookmark(Object var1);

   void getAllBookmarkRefs(Collection var1);

   boolean removeXml();

   boolean moveXml(XmlCursor var1);

   boolean copyXml(XmlCursor var1);

   boolean removeXmlContents();

   boolean moveXmlContents(XmlCursor var1);

   boolean copyXmlContents(XmlCursor var1);

   int removeChars(int var1);

   int moveChars(int var1, XmlCursor var2);

   int copyChars(int var1, XmlCursor var2);

   void insertChars(String var1);

   void insertElement(QName var1);

   void insertElement(String var1);

   void insertElement(String var1, String var2);

   void beginElement(QName var1);

   void beginElement(String var1);

   void beginElement(String var1, String var2);

   void insertElementWithText(QName var1, String var2);

   void insertElementWithText(String var1, String var2);

   void insertElementWithText(String var1, String var2, String var3);

   void insertAttribute(String var1);

   void insertAttribute(String var1, String var2);

   void insertAttribute(QName var1);

   void insertAttributeWithValue(String var1, String var2);

   void insertAttributeWithValue(String var1, String var2, String var3);

   void insertAttributeWithValue(QName var1, String var2);

   void insertNamespace(String var1, String var2);

   void insertComment(String var1);

   void insertProcInst(String var1, String var2);

   public interface XmlMark {
      XmlCursor createCursor();
   }

   public abstract static class XmlBookmark {
      public XmlMark _currentMark;
      public final Reference _ref;

      public XmlBookmark() {
         this(false);
      }

      public XmlBookmark(boolean weak) {
         this._ref = weak ? new WeakReference(this) : null;
      }

      public final XmlCursor createCursor() {
         return this._currentMark == null ? null : this._currentMark.createCursor();
      }

      public final XmlCursor toBookmark(XmlCursor c) {
         return c != null && c.toBookmark(this) ? c : this.createCursor();
      }

      public Object getKey() {
         return this.getClass();
      }
   }

   public interface ChangeStamp {
      boolean hasChanged();
   }

   public static final class TokenType {
      public static final int INT_NONE = 0;
      public static final int INT_STARTDOC = 1;
      public static final int INT_ENDDOC = 2;
      public static final int INT_START = 3;
      public static final int INT_END = 4;
      public static final int INT_TEXT = 5;
      public static final int INT_ATTR = 6;
      public static final int INT_NAMESPACE = 7;
      public static final int INT_COMMENT = 8;
      public static final int INT_PROCINST = 9;
      public static final TokenType NONE = new TokenType("NONE", 0);
      public static final TokenType STARTDOC = new TokenType("STARTDOC", 1);
      public static final TokenType ENDDOC = new TokenType("ENDDOC", 2);
      public static final TokenType START = new TokenType("START", 3);
      public static final TokenType END = new TokenType("END", 4);
      public static final TokenType TEXT = new TokenType("TEXT", 5);
      public static final TokenType ATTR = new TokenType("ATTR", 6);
      public static final TokenType NAMESPACE = new TokenType("NAMESPACE", 7);
      public static final TokenType COMMENT = new TokenType("COMMENT", 8);
      public static final TokenType PROCINST = new TokenType("PROCINST", 9);
      private String _name;
      private int _value;

      public String toString() {
         return this._name;
      }

      public int intValue() {
         return this._value;
      }

      public boolean isNone() {
         return this == NONE;
      }

      public boolean isStartdoc() {
         return this == STARTDOC;
      }

      public boolean isEnddoc() {
         return this == ENDDOC;
      }

      public boolean isStart() {
         return this == START;
      }

      public boolean isEnd() {
         return this == END;
      }

      public boolean isText() {
         return this == TEXT;
      }

      public boolean isAttr() {
         return this == ATTR;
      }

      public boolean isNamespace() {
         return this == NAMESPACE;
      }

      public boolean isComment() {
         return this == COMMENT;
      }

      public boolean isProcinst() {
         return this == PROCINST;
      }

      public boolean isContainer() {
         return this == STARTDOC || this == START;
      }

      public boolean isFinish() {
         return this == ENDDOC || this == END;
      }

      public boolean isAnyAttr() {
         return this == NAMESPACE || this == ATTR;
      }

      private TokenType(String name, int value) {
         this._name = name;
         this._value = value;
      }
   }
}
