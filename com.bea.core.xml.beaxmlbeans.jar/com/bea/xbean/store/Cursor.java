package com.bea.xbean.store;

import com.bea.xbean.common.GlobalLock;
import com.bea.xbean.common.XMLChar;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlCursor;
import com.bea.xml.XmlDocumentProperties;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import weblogic.xml.stream.XMLInputStream;

public final class Cursor implements XmlCursor, Locale.ChangeListener {
   static final int ROOT = 1;
   static final int ELEM = 2;
   static final int ATTR = 3;
   static final int COMMENT = 4;
   static final int PROCINST = 5;
   static final int TEXT = 0;
   private static final int MOVE_XML = 0;
   private static final int COPY_XML = 1;
   private static final int MOVE_XML_CONTENTS = 2;
   private static final int COPY_XML_CONTENTS = 3;
   private static final int MOVE_CHARS = 4;
   private static final int COPY_CHARS = 5;
   private Cur _cur;
   private Path.PathEngine _pathEngine;
   private int _currentSelection;
   private Locale.ChangeListener _nextChangeListener;

   Cursor(Xobj x, int p) {
      this._cur = x._locale.weakCur(this);
      this._cur.moveTo(x, p);
      this._currentSelection = -1;
   }

   Cursor(Cur c) {
      this(c._xobj, c._pos);
   }

   private static boolean isValid(Cur c) {
      if (c.kind() <= 0) {
         c.push();
         if (c.toParentRaw()) {
            int pk = c.kind();
            if (pk == 4 || pk == 5 || pk == 3) {
               return false;
            }
         }

         c.pop();
      }

      return true;
   }

   private boolean isValid() {
      return isValid(this._cur);
   }

   Locale locale() {
      return this._cur._locale;
   }

   Cur tempCur() {
      return this._cur.tempCur();
   }

   public void dump(PrintStream o) {
      this._cur.dump(o);
   }

   static void validateLocalName(QName name) {
      if (name == null) {
         throw new IllegalArgumentException("QName is null");
      } else {
         validateLocalName(name.getLocalPart());
      }
   }

   static void validateLocalName(String name) {
      if (name == null) {
         throw new IllegalArgumentException("Name is null");
      } else if (name.length() == 0) {
         throw new IllegalArgumentException("Name is empty");
      } else if (!XMLChar.isValidNCName(name)) {
         throw new IllegalArgumentException("Name is not valid");
      }
   }

   static void validatePrefix(String name) {
      if (name == null) {
         throw new IllegalArgumentException("Prefix is null");
      } else if (name.length() == 0) {
         throw new IllegalArgumentException("Prefix is empty");
      } else if (Locale.beginsWithXml(name)) {
         throw new IllegalArgumentException("Prefix begins with 'xml'");
      } else if (!XMLChar.isValidNCName(name)) {
         throw new IllegalArgumentException("Prefix is not valid");
      }
   }

   private static void complain(String msg) {
      throw new IllegalArgumentException(msg);
   }

   private void checkInsertionValidity(Cur that) {
      int thatKind = that.kind();
      if (thatKind < 0) {
         complain("Can't move/copy/insert an end token.");
      }

      if (thatKind == 1) {
         complain("Can't move/copy/insert a whole document.");
      }

      int thisKind = this._cur.kind();
      if (thisKind == 1) {
         complain("Can't insert before the start of the document.");
      }

      if (thatKind == 3) {
         this._cur.push();
         this._cur.prevWithAttrs();
         int pk = this._cur.kind();
         this._cur.pop();
         if (pk != 2 && pk != 1 && pk != -3) {
            complain("Can only insert attributes before other attributes or after containers.");
         }
      }

      if (thisKind == 3 && thatKind != 3) {
         complain("Can only insert attributes before other attributes or after containers.");
      }

   }

   private void insertNode(Cur that, String text) {
      assert !that.isRoot();

      assert that.isNode();

      assert isValid(that);

      assert this.isValid();

      if (text != null && text.length() > 0) {
         that.next();
         that.insertString(text);
         that.toParent();
      }

      this.checkInsertionValidity(that);
      that.moveNode(this._cur);
      this._cur.toEnd();
      this._cur.nextWithAttrs();
   }

   public void _dispose() {
      this._cur.release();
      this._cur = null;
   }

   public XmlCursor _newCursor() {
      return new Cursor(this._cur);
   }

   public QName _getName() {
      switch (this._cur.kind()) {
         case 3:
            if (this._cur.isXmlns()) {
               return this._cur._locale.makeQNameNoCheck(this._cur.getXmlnsUri(), this._cur.getXmlnsPrefix());
            }
         case 2:
         case 5:
            return this._cur.getName();
         case 4:
         default:
            return null;
      }
   }

   public void _setName(QName name) {
      if (name == null) {
         throw new IllegalArgumentException("Name is null");
      } else {
         switch (this._cur.kind()) {
            case 2:
            case 3:
               validateLocalName(name.getLocalPart());
               break;
            case 4:
            default:
               throw new IllegalStateException("Can set name on element, atrtribute and procinst only");
            case 5:
               validatePrefix(name.getLocalPart());
               if (name.getNamespaceURI().length() > 0) {
                  throw new IllegalArgumentException("Procinst name must have no URI");
               }

               if (name.getPrefix().length() > 0) {
                  throw new IllegalArgumentException("Procinst name must have no prefix");
               }
         }

         this._cur.setName(name);
      }
   }

   public XmlCursor.TokenType _currentTokenType() {
      assert this.isValid();

      switch (this._cur.kind()) {
         case -2:
            return XmlCursor.TokenType.END;
         case -1:
            return XmlCursor.TokenType.ENDDOC;
         case 0:
            return XmlCursor.TokenType.TEXT;
         case 1:
            return XmlCursor.TokenType.STARTDOC;
         case 2:
            return XmlCursor.TokenType.START;
         case 3:
            return this._cur.isXmlns() ? XmlCursor.TokenType.NAMESPACE : XmlCursor.TokenType.ATTR;
         case 4:
            return XmlCursor.TokenType.COMMENT;
         case 5:
            return XmlCursor.TokenType.PROCINST;
         default:
            throw new IllegalStateException();
      }
   }

   public boolean _isStartdoc() {
      assert this.isValid();

      return this._cur.isRoot();
   }

   public boolean _isEnddoc() {
      assert this.isValid();

      return this._cur.isEndRoot();
   }

   public boolean _isStart() {
      assert this.isValid();

      return this._cur.isElem();
   }

   public boolean _isEnd() {
      assert this.isValid();

      return this._cur.isEnd();
   }

   public boolean _isText() {
      assert this.isValid();

      return this._cur.isText();
   }

   public boolean _isAttr() {
      assert this.isValid();

      return this._cur.isNormalAttr();
   }

   public boolean _isNamespace() {
      assert this.isValid();

      return this._cur.isXmlns();
   }

   public boolean _isComment() {
      assert this.isValid();

      return this._cur.isComment();
   }

   public boolean _isProcinst() {
      assert this.isValid();

      return this._cur.isProcinst();
   }

   public boolean _isContainer() {
      assert this.isValid();

      return this._cur.isContainer();
   }

   public boolean _isFinish() {
      assert this.isValid();

      return this._cur.isFinish();
   }

   public boolean _isAnyAttr() {
      assert this.isValid();

      return this._cur.isAttr();
   }

   public XmlCursor.TokenType _toNextToken() {
      assert this.isValid();

      switch (this._cur.kind()) {
         case 1:
         case 2:
            if (!this._cur.toFirstAttr()) {
               this._cur.next();
            }
            break;
         case 3:
            if (!this._cur.toNextSibling()) {
               this._cur.toParent();
               this._cur.next();
            }
            break;
         case 4:
         case 5:
            this._cur.skip();
            break;
         default:
            if (!this._cur.next()) {
               return XmlCursor.TokenType.NONE;
            }
      }

      return this._currentTokenType();
   }

   public XmlCursor.TokenType _toPrevToken() {
      assert this.isValid();

      boolean wasText = this._cur.isText();
      if (!this._cur.prev()) {
         assert this._cur.isRoot() || this._cur.isAttr();

         if (this._cur.isRoot()) {
            return XmlCursor.TokenType.NONE;
         }

         this._cur.toParent();
      } else {
         int k = this._cur.kind();
         if (k >= 0 || k != -4 && k != -5 && k != -3) {
            if (this._cur.isContainer()) {
               this._cur.toLastAttr();
            } else if (wasText && this._cur.isText()) {
               return this._toPrevToken();
            }
         } else {
            this._cur.toParent();
         }
      }

      return this._currentTokenType();
   }

   public Object _monitor() {
      return this._cur._locale;
   }

   public boolean _toParent() {
      Cur c = this._cur.tempCur();
      if (!c.toParent()) {
         return false;
      } else {
         this._cur.moveToCur(c);
         c.release();
         return true;
      }
   }

   public XmlCursor.ChangeStamp _getDocChangeStamp() {
      return new ChangeStampImpl(this._cur._locale);
   }

   /** @deprecated */
   public XMLInputStream _newXMLInputStream() {
      return this._newXMLInputStream((XmlOptions)null);
   }

   public XMLStreamReader _newXMLStreamReader() {
      return this._newXMLStreamReader((XmlOptions)null);
   }

   public Node _newDomNode() {
      return this._newDomNode((XmlOptions)null);
   }

   public InputStream _newInputStream() {
      return this._newInputStream((XmlOptions)null);
   }

   public String _xmlText() {
      return this._xmlText((XmlOptions)null);
   }

   public Reader _newReader() {
      return this._newReader((XmlOptions)null);
   }

   public void _save(File file) throws IOException {
      this._save((File)file, (XmlOptions)null);
   }

   public void _save(OutputStream os) throws IOException {
      this._save((OutputStream)os, (XmlOptions)null);
   }

   public void _save(Writer w) throws IOException {
      this._save((Writer)w, (XmlOptions)null);
   }

   public void _save(ContentHandler ch, LexicalHandler lh) throws SAXException {
      this._save(ch, lh, (XmlOptions)null);
   }

   public XmlDocumentProperties _documentProperties() {
      return Locale.getDocProps(this._cur, true);
   }

   public XMLStreamReader _newXMLStreamReader(XmlOptions options) {
      return Jsr173.newXmlStreamReader(this._cur, options);
   }

   /** @deprecated */
   public XMLInputStream _newXMLInputStream(XmlOptions options) {
      return new Saver.XmlInputStreamImpl(this._cur, options);
   }

   public String _xmlText(XmlOptions options) {
      assert this.isValid();

      return (new Saver.TextSaver(this._cur, options, (String)null)).saveToString();
   }

   public InputStream _newInputStream(XmlOptions options) {
      return new Saver.InputStreamSaver(this._cur, options);
   }

   public Reader _newReader(XmlOptions options) {
      return new Saver.TextReader(this._cur, options);
   }

   public void _save(ContentHandler ch, LexicalHandler lh, XmlOptions options) throws SAXException {
      new Saver.SaxSaver(this._cur, options, ch, lh);
   }

   public void _save(File file, XmlOptions options) throws IOException {
      if (file == null) {
         throw new IllegalArgumentException("Null file specified");
      } else {
         OutputStream os = new FileOutputStream(file);

         try {
            this._save((OutputStream)os, (XmlOptions)options);
         } finally {
            os.close();
         }

      }
   }

   public void _save(OutputStream os, XmlOptions options) throws IOException {
      if (os == null) {
         throw new IllegalArgumentException("Null OutputStream specified");
      } else {
         InputStream is = this._newInputStream(options);

         try {
            byte[] bytes = new byte[8192];

            while(true) {
               int n = is.read(bytes);
               if (n < 0) {
                  return;
               }

               os.write(bytes, 0, n);
            }
         } finally {
            is.close();
         }
      }
   }

   public void _save(Writer w, XmlOptions options) throws IOException {
      if (w == null) {
         throw new IllegalArgumentException("Null Writer specified");
      } else if (options != null && options.hasOption("SAVE_OPTIMIZE_FOR_SPEED")) {
         Saver.OptimizedForSpeedSaver.save(this._cur, w);
      } else {
         Reader r = this._newReader(options);

         try {
            char[] chars = new char[8192];

            while(true) {
               int n = r.read(chars);
               if (n < 0) {
                  return;
               }

               w.write(chars, 0, n);
            }
         } finally {
            r.close();
         }
      }
   }

   public Node _getDomNode() {
      return (Node)this._cur.getDom();
   }

   private boolean isDomFragment() {
      if (!this.isStartdoc()) {
         return true;
      } else {
         boolean seenElement = false;
         XmlCursor c = this.newCursor();
         int token = c.toNextToken().intValue();

         try {
            while(true) {
               boolean var4;
               switch (token) {
                  case 0:
                  case 2:
                     return !seenElement;
                  case 1:
                     assert false;

                     return !seenElement;
                  case 3:
                     if (seenElement) {
                        var4 = true;
                        return var4;
                     }

                     seenElement = true;
                     token = c.toEndToken().intValue();
                     break;
                  case 4:
                  case 8:
                  case 9:
                     token = c.toNextToken().intValue();
                     break;
                  case 5:
                     if (!Locale.isWhiteSpace(c.getChars())) {
                        var4 = true;
                        return var4;
                     }

                     token = c.toNextToken().intValue();
                     break;
                  case 6:
                  case 7:
                     var4 = true;
                     return var4;
               }
            }
         } finally {
            c.dispose();
         }
      }
   }

   public Node _newDomNode(XmlOptions options) {
      if (XmlOptions.hasOption(options, "SAVE_INNER")) {
         options = new XmlOptions(options);
         options.remove("SAVE_INNER");
      }

      return (new DomSaver(this._cur, this.isDomFragment(), options)).saveDom();
   }

   public boolean _toCursor(Cursor other) {
      assert this._cur._locale == other._cur._locale;

      this._cur.moveToCur(other._cur);
      return true;
   }

   public void _push() {
      this._cur.push();
   }

   public boolean _pop() {
      return this._cur.pop();
   }

   public void notifyChange() {
      if (this._cur != null) {
         this._getSelectionCount();
      }

   }

   public void setNextChangeListener(Locale.ChangeListener listener) {
      this._nextChangeListener = listener;
   }

   public Locale.ChangeListener getNextChangeListener() {
      return this._nextChangeListener;
   }

   public void _selectPath(String path) {
      this._selectPath(path, (XmlOptions)null);
   }

   public void _selectPath(String pathExpr, XmlOptions options) {
      this._clearSelections();

      assert this._pathEngine == null;

      this._pathEngine = Path.getCompiledPath(pathExpr, options).execute(this._cur, options);
      this._cur._locale.registerForChange(this);
   }

   public boolean _hasNextSelection() {
      int curr = this._currentSelection;
      this.push();

      boolean var2;
      try {
         var2 = this._toNextSelection();
      } finally {
         this._currentSelection = curr;
         this.pop();
      }

      return var2;
   }

   public boolean _toNextSelection() {
      return this._toSelection(this._currentSelection + 1);
   }

   public boolean _toSelection(int i) {
      if (i < 0) {
         return false;
      } else {
         do {
            if (i < this._cur.selectionCount()) {
               this._cur.moveToSelection(this._currentSelection = i);
               return true;
            }

            if (this._pathEngine == null) {
               return false;
            }
         } while(this._pathEngine.next(this._cur));

         this._pathEngine.release();
         this._pathEngine = null;
         return false;
      }
   }

   public int _getSelectionCount() {
      this._toSelection(Integer.MAX_VALUE);
      return this._cur.selectionCount();
   }

   public void _addToSelection() {
      this._toSelection(Integer.MAX_VALUE);
      this._cur.addToSelection();
   }

   public void _clearSelections() {
      if (this._pathEngine != null) {
         this._pathEngine.release();
         this._pathEngine = null;
      }

      this._cur.clearSelection();
      this._currentSelection = -1;
   }

   public String _namespaceForPrefix(String prefix) {
      if (!this._cur.isContainer()) {
         throw new IllegalStateException("Not on a container");
      } else {
         return this._cur.namespaceForPrefix(prefix, true);
      }
   }

   public String _prefixForNamespace(String ns) {
      if (ns != null && ns.length() != 0) {
         return this._cur.prefixForNamespace(ns, (String)null, true);
      } else {
         throw new IllegalArgumentException("Must specify a namespace");
      }
   }

   public void _getAllNamespaces(Map addToThis) {
      if (!this._cur.isContainer()) {
         throw new IllegalStateException("Not on a container");
      } else {
         if (addToThis != null) {
            Locale.getAllNamespaces(this._cur, addToThis);
         }

      }
   }

   public XmlObject _getObject() {
      return this._cur.getObject();
   }

   public XmlCursor.TokenType _prevTokenType() {
      this._cur.push();
      XmlCursor.TokenType tt = this._toPrevToken();
      this._cur.pop();
      return tt;
   }

   public boolean _hasNextToken() {
      return this._cur._pos != -1 || this._cur._xobj.kind() != 1;
   }

   public boolean _hasPrevToken() {
      return this._cur.kind() != 1;
   }

   public XmlCursor.TokenType _toFirstContentToken() {
      if (!this._cur.isContainer()) {
         return XmlCursor.TokenType.NONE;
      } else {
         this._cur.next();
         return this.currentTokenType();
      }
   }

   public XmlCursor.TokenType _toEndToken() {
      if (!this._cur.isContainer()) {
         return XmlCursor.TokenType.NONE;
      } else {
         this._cur.toEnd();
         return this.currentTokenType();
      }
   }

   public boolean _toChild(String local) {
      return this._toChild((String)null, local);
   }

   public boolean _toChild(QName name) {
      return this._toChild(name, 0);
   }

   public boolean _toChild(int index) {
      return this._toChild((QName)null, index);
   }

   public boolean _toChild(String uri, String local) {
      validateLocalName(local);
      return this._toChild(this._cur._locale.makeQName(uri, local), 0);
   }

   public boolean _toChild(QName name, int index) {
      return Locale.toChild(this._cur, name, index);
   }

   public int _toNextChar(int maxCharacterCount) {
      return this._cur.nextChars(maxCharacterCount);
   }

   public int _toPrevChar(int maxCharacterCount) {
      return this._cur.prevChars(maxCharacterCount);
   }

   public boolean _toPrevSibling() {
      return Locale.toPrevSiblingElement(this._cur);
   }

   public boolean _toLastChild() {
      return Locale.toLastChildElement(this._cur);
   }

   public boolean _toFirstChild() {
      return Locale.toFirstChildElement(this._cur);
   }

   public boolean _toNextSibling(String name) {
      return this._toNextSibling(new QName(name));
   }

   public boolean _toNextSibling(String uri, String local) {
      validateLocalName(local);
      return this._toNextSibling(this._cur._locale._qnameFactory.getQName(uri, local));
   }

   public boolean _toNextSibling(QName name) {
      this._cur.push();

      do {
         if (!this.___toNextSibling()) {
            this._cur.pop();
            return false;
         }
      } while(!this._cur.getName().equals(name));

      this._cur.popButStay();
      return true;
   }

   public boolean _toFirstAttribute() {
      return this._cur.isContainer() && Locale.toFirstNormalAttr(this._cur);
   }

   public boolean _toLastAttribute() {
      if (this._cur.isContainer()) {
         this._cur.push();
         this._cur.push();
         boolean foundAttr = false;

         while(this._cur.toNextAttr()) {
            if (this._cur.isNormalAttr()) {
               this._cur.popButStay();
               this._cur.push();
               foundAttr = true;
            }
         }

         this._cur.pop();
         if (foundAttr) {
            this._cur.popButStay();
            return true;
         }

         this._cur.pop();
      }

      return false;
   }

   public boolean _toNextAttribute() {
      return this._cur.isAttr() && Locale.toNextNormalAttr(this._cur);
   }

   public boolean _toPrevAttribute() {
      return this._cur.isAttr() && Locale.toPrevNormalAttr(this._cur);
   }

   public String _getAttributeText(QName attrName) {
      if (attrName == null) {
         throw new IllegalArgumentException("Attr name is null");
      } else {
         return !this._cur.isContainer() ? null : this._cur.getAttrValue(attrName);
      }
   }

   public boolean _setAttributeText(QName attrName, String value) {
      if (attrName == null) {
         throw new IllegalArgumentException("Attr name is null");
      } else {
         validateLocalName(attrName.getLocalPart());
         if (!this._cur.isContainer()) {
            return false;
         } else {
            this._cur.setAttrValue(attrName, value);
            return true;
         }
      }
   }

   public boolean _removeAttribute(QName attrName) {
      if (attrName == null) {
         throw new IllegalArgumentException("Attr name is null");
      } else {
         return !this._cur.isContainer() ? false : this._cur.removeAttr(attrName);
      }
   }

   public String _getTextValue() {
      if (this._cur.isText()) {
         return this._getChars();
      } else if (!this._cur.isNode()) {
         throw new IllegalStateException("Can't get text value, current token can have no text value");
      } else {
         return this._cur.hasChildren() ? Locale.getTextValue(this._cur) : this._cur.getValueAsString();
      }
   }

   public int _getTextValue(char[] chars, int offset, int max) {
      if (this._cur.isText()) {
         return this._getChars(chars, offset, max);
      } else if (chars == null) {
         throw new IllegalArgumentException("char buffer is null");
      } else if (offset < 0) {
         throw new IllegalArgumentException("offset < 0");
      } else if (offset >= chars.length) {
         throw new IllegalArgumentException("offset off end");
      } else {
         if (max < 0) {
            max = Integer.MAX_VALUE;
         }

         if (offset + max > chars.length) {
            max = chars.length - offset;
         }

         if (!this._cur.isNode()) {
            throw new IllegalStateException("Can't get text value, current token can have no text value");
         } else if (this._cur.hasChildren()) {
            return Locale.getTextValue(this._cur, 1, chars, offset, max);
         } else {
            Object src = this._cur.getFirstChars();
            if (this._cur._cchSrc > max) {
               this._cur._cchSrc = max;
            }

            if (this._cur._cchSrc <= 0) {
               return 0;
            } else {
               CharUtil.getChars(chars, offset, src, this._cur._offSrc, this._cur._cchSrc);
               return this._cur._cchSrc;
            }
         }
      }
   }

   private void setTextValue(Object src, int off, int cch) {
      if (!this._cur.isNode()) {
         throw new IllegalStateException("Can't set text value, current token can have no text value");
      } else {
         this._cur.moveNodeContents((Cur)null, false);
         this._cur.next();
         this._cur.insertChars(src, off, cch);
         this._cur.toParent();
      }
   }

   public void _setTextValue(String text) {
      if (text == null) {
         text = "";
      }

      this.setTextValue((Object)text, 0, text.length());
   }

   public void _setTextValue(char[] sourceChars, int offset, int length) {
      if (length < 0) {
         throw new IndexOutOfBoundsException("setTextValue: length < 0");
      } else if (sourceChars == null) {
         if (length > 0) {
            throw new IllegalArgumentException("setTextValue: sourceChars == null");
         } else {
            this.setTextValue((char[])null, 0, 0);
         }
      } else if (offset >= 0 && offset < sourceChars.length) {
         if (offset + length > sourceChars.length) {
            length = sourceChars.length - offset;
         }

         CharUtil cu = this._cur._locale.getCharUtil();
         this.setTextValue(cu.saveChars(sourceChars, offset, length), cu._offSrc, cu._cchSrc);
      } else {
         throw new IndexOutOfBoundsException("setTextValue: offset out of bounds");
      }
   }

   public String _getChars() {
      return this._cur.getCharsAsString(-1);
   }

   public int _getChars(char[] buf, int off, int cch) {
      int cchRight = this._cur.cchRight();
      if (cch < 0 || cch > cchRight) {
         cch = cchRight;
      }

      if (buf != null && off < buf.length) {
         if (buf.length - off < cch) {
            cch = buf.length - off;
         }

         Object src = this._cur.getChars(cch);
         CharUtil.getChars(buf, off, src, this._cur._offSrc, this._cur._cchSrc);
         return this._cur._cchSrc;
      } else {
         return 0;
      }
   }

   public void _toStartDoc() {
      this._cur.toRoot();
   }

   public void _toEndDoc() {
      this._toStartDoc();
      this._cur.toEnd();
   }

   public int _comparePosition(Cursor other) {
      int s = this._cur.comparePosition(other._cur);
      if (s == 2) {
         throw new IllegalArgumentException("Cursors not in same document");
      } else {
         assert s >= -1 && s <= 1;

         return s;
      }
   }

   public boolean _isLeftOf(Cursor other) {
      return this._comparePosition(other) < 0;
   }

   public boolean _isAtSamePositionAs(Cursor other) {
      return this._cur.isSamePos(other._cur);
   }

   public boolean _isRightOf(Cursor other) {
      return this._comparePosition(other) > 0;
   }

   public XmlCursor _execQuery(String query) {
      return this._execQuery(query, (XmlOptions)null);
   }

   public XmlCursor _execQuery(String query, XmlOptions options) {
      this.checkThisCursor();
      return Query.cursorExecQuery(this._cur, query, options);
   }

   public boolean _toBookmark(XmlCursor.XmlBookmark bookmark) {
      if (bookmark != null && bookmark._currentMark instanceof Xobj.Bookmark) {
         Xobj.Bookmark m = (Xobj.Bookmark)bookmark._currentMark;
         if (m._xobj != null && m._xobj._locale == this._cur._locale) {
            this._cur.moveTo(m._xobj, m._pos);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public XmlCursor.XmlBookmark _toNextBookmark(Object key) {
      if (key == null) {
         return null;
      } else {
         this._cur.push();

         do {
            int cch;
            if ((cch = this._cur.cchRight()) > 1) {
               this._cur.nextChars(1);
               this._cur.nextChars((cch = this._cur.firstBookmarkInChars(key, cch - 1)) >= 0 ? cch : -1);
            } else if (this._toNextToken().isNone()) {
               this._cur.pop();
               return null;
            }

            XmlCursor.XmlBookmark bm = getBookmark(key, this._cur);
            if (bm != null) {
               this._cur.popButStay();
               return bm;
            }
         } while(this._cur.kind() != -1);

         this._cur.pop();
         return null;
      }
   }

   public XmlCursor.XmlBookmark _toPrevBookmark(Object key) {
      if (key == null) {
         return null;
      } else {
         this._cur.push();

         do {
            int cch;
            if ((cch = this._cur.cchLeft()) > 1) {
               this._cur.prevChars(1);
               this._cur.prevChars((cch = this._cur.firstBookmarkInCharsLeft(key, cch - 1)) >= 0 ? cch : -1);
            } else if (cch == 1) {
               this._cur.prevChars(1);
            } else if (this._toPrevToken().isNone()) {
               this._cur.pop();
               return null;
            }

            XmlCursor.XmlBookmark bm = getBookmark(key, this._cur);
            if (bm != null) {
               this._cur.popButStay();
               return bm;
            }
         } while(this._cur.kind() != 1);

         this._cur.pop();
         return null;
      }
   }

   public void _setBookmark(XmlCursor.XmlBookmark bookmark) {
      if (bookmark != null) {
         if (bookmark.getKey() == null) {
            throw new IllegalArgumentException("Annotation key is null");
         }

         bookmark._currentMark = this._cur.setBookmark(bookmark.getKey(), bookmark);
      }

   }

   static XmlCursor.XmlBookmark getBookmark(Object key, Cur c) {
      if (key == null) {
         return null;
      } else {
         Object bm = c.getBookmark(key);
         return bm != null && bm instanceof XmlCursor.XmlBookmark ? (XmlCursor.XmlBookmark)bm : null;
      }
   }

   public XmlCursor.XmlBookmark _getBookmark(Object key) {
      return key == null ? null : getBookmark(key, this._cur);
   }

   public void _clearBookmark(Object key) {
      if (key != null) {
         this._cur.setBookmark(key, (Object)null);
      }

   }

   public void _getAllBookmarkRefs(Collection listToFill) {
      if (listToFill != null) {
         for(Xobj.Bookmark b = this._cur._xobj._bookmarks; b != null; b = b._next) {
            if (b._value instanceof XmlCursor.XmlBookmark) {
               listToFill.add(b._value);
            }
         }
      }

   }

   public boolean _removeXml() {
      if (this._cur.isRoot()) {
         throw new IllegalStateException("Can't remove a whole document.");
      } else if (this._cur.isFinish()) {
         return false;
      } else {
         assert this._cur.isText() || this._cur.isNode();

         if (this._cur.isText()) {
            this._cur.moveChars((Cur)null, -1);
         } else {
            this._cur.moveNode((Cur)null);
         }

         return true;
      }
   }

   public boolean _moveXml(Cursor to) {
      to.checkInsertionValidity(this._cur);
      if (this._cur.isText()) {
         int cchRight = this._cur.cchRight();

         assert cchRight > 0;

         if (this._cur.inChars(to._cur, cchRight, true)) {
            return false;
         } else {
            this._cur.moveChars(to._cur, cchRight);
            to._cur.nextChars(cchRight);
            return true;
         }
      } else if (this._cur.contains(to._cur)) {
         return false;
      } else {
         Cur c = to.tempCur();
         this._cur.moveNode(to._cur);
         to._cur.moveToCur(c);
         c.release();
         return true;
      }
   }

   public boolean _copyXml(Cursor to) {
      to.checkInsertionValidity(this._cur);

      assert this._cur.isText() || this._cur.isNode();

      Cur c = to.tempCur();
      if (this._cur.isText()) {
         to._cur.insertChars(this._cur.getChars(-1), this._cur._offSrc, this._cur._cchSrc);
      } else {
         this._cur.copyNode(to._cur);
      }

      to._cur.moveToCur(c);
      c.release();
      return true;
   }

   public boolean _removeXmlContents() {
      if (!this._cur.isContainer()) {
         return false;
      } else {
         this._cur.moveNodeContents((Cur)null, false);
         return true;
      }
   }

   private boolean checkContentInsertionValidity(Cursor to) {
      this._cur.push();
      this._cur.next();
      if (this._cur.isFinish()) {
         this._cur.pop();
         return false;
      } else {
         try {
            to.checkInsertionValidity(this._cur);
         } catch (IllegalArgumentException var3) {
            this._cur.pop();
            throw var3;
         }

         this._cur.pop();
         return true;
      }
   }

   public boolean _moveXmlContents(Cursor to) {
      if (this._cur.isContainer() && !this._cur.contains(to._cur)) {
         if (!this.checkContentInsertionValidity(to)) {
            return false;
         } else {
            Cur c = to.tempCur();
            this._cur.moveNodeContents(to._cur, false);
            to._cur.moveToCur(c);
            c.release();
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean _copyXmlContents(Cursor to) {
      if (this._cur.isContainer() && !this._cur.contains(to._cur)) {
         if (!this.checkContentInsertionValidity(to)) {
            return false;
         } else {
            Cur c = this._cur._locale.tempCur();
            this._cur.copyNode(c);
            Cur c2 = to._cur.tempCur();
            c.moveNodeContents(to._cur, false);
            c.release();
            to._cur.moveToCur(c2);
            c2.release();
            return true;
         }
      } else {
         return false;
      }
   }

   public int _removeChars(int cch) {
      int cchRight = this._cur.cchRight();
      if (cchRight != 0 && cch != 0) {
         if (cch < 0 || cch > cchRight) {
            cch = cchRight;
         }

         this._cur.moveChars((Cur)null, cch);
         return this._cur._cchSrc;
      } else {
         return 0;
      }
   }

   public int _moveChars(int cch, Cursor to) {
      int cchRight = this._cur.cchRight();
      if (cchRight > 0 && cch != 0) {
         if (cch < 0 || cch > cchRight) {
            cch = cchRight;
         }

         to.checkInsertionValidity(this._cur);
         this._cur.moveChars(to._cur, cch);
         to._cur.nextChars(this._cur._cchSrc);
         return this._cur._cchSrc;
      } else {
         return 0;
      }
   }

   public int _copyChars(int cch, Cursor to) {
      int cchRight = this._cur.cchRight();
      if (cchRight > 0 && cch != 0) {
         if (cch < 0 || cch > cchRight) {
            cch = cchRight;
         }

         to.checkInsertionValidity(this._cur);
         to._cur.insertChars(this._cur.getChars(cch), this._cur._offSrc, this._cur._cchSrc);
         to._cur.nextChars(this._cur._cchSrc);
         return this._cur._cchSrc;
      } else {
         return 0;
      }
   }

   public void _insertChars(String text) {
      int l = text == null ? 0 : text.length();
      if (l > 0) {
         if (this._cur.isRoot() || this._cur.isAttr()) {
            throw new IllegalStateException("Can't insert before the document or an attribute.");
         }

         this._cur.insertChars(text, 0, l);
         this._cur.nextChars(l);
      }

   }

   public void _beginElement(String localName) {
      this._insertElementWithText(localName, (String)null, (String)null);
      this._toPrevToken();
   }

   public void _beginElement(String localName, String uri) {
      this._insertElementWithText(localName, uri, (String)null);
      this._toPrevToken();
   }

   public void _beginElement(QName name) {
      this._insertElementWithText((QName)name, (String)null);
      this._toPrevToken();
   }

   public void _insertElement(String localName) {
      this._insertElementWithText(localName, (String)null, (String)null);
   }

   public void _insertElement(String localName, String uri) {
      this._insertElementWithText(localName, uri, (String)null);
   }

   public void _insertElement(QName name) {
      this._insertElementWithText((QName)name, (String)null);
   }

   public void _insertElementWithText(String localName, String text) {
      this._insertElementWithText(localName, (String)null, text);
   }

   public void _insertElementWithText(String localName, String uri, String text) {
      validateLocalName(localName);
      this._insertElementWithText(this._cur._locale.makeQName(uri, localName), text);
   }

   public void _insertElementWithText(QName name, String text) {
      validateLocalName(name.getLocalPart());
      Cur c = this._cur._locale.tempCur();
      c.createElement(name);
      this.insertNode(c, text);
      c.release();
   }

   public void _insertAttribute(String localName) {
      this._insertAttributeWithValue((String)localName, (String)null);
   }

   public void _insertAttribute(String localName, String uri) {
      this._insertAttributeWithValue(localName, uri, (String)null);
   }

   public void _insertAttribute(QName name) {
      this._insertAttributeWithValue((QName)name, (String)null);
   }

   public void _insertAttributeWithValue(String localName, String value) {
      this._insertAttributeWithValue(localName, (String)null, value);
   }

   public void _insertAttributeWithValue(String localName, String uri, String value) {
      validateLocalName(localName);
      this._insertAttributeWithValue(this._cur._locale.makeQName(uri, localName), value);
   }

   public void _insertAttributeWithValue(QName name, String text) {
      validateLocalName(name.getLocalPart());
      Cur c = this._cur._locale.tempCur();
      c.createAttr(name);
      this.insertNode(c, text);
      c.release();
   }

   public void _insertNamespace(String prefix, String namespace) {
      this._insertAttributeWithValue(this._cur._locale.createXmlns(prefix), namespace);
   }

   public void _insertComment(String text) {
      Cur c = this._cur._locale.tempCur();
      c.createComment();
      this.insertNode(c, text);
      c.release();
   }

   public void _insertProcInst(String target, String text) {
      validateLocalName(target);
      if (Locale.beginsWithXml(target) && target.length() == 3) {
         throw new IllegalArgumentException("Target is 'xml'");
      } else {
         Cur c = this._cur._locale.tempCur();
         c.createProcinst(target);
         this.insertNode(c, text);
         c.release();
      }
   }

   public void _dump() {
      this._cur.dump();
   }

   private void checkThisCursor() {
      if (this._cur == null) {
         throw new IllegalStateException("This cursor has been disposed");
      }
   }

   private Cursor checkCursors(XmlCursor xOther) {
      this.checkThisCursor();
      if (xOther == null) {
         throw new IllegalArgumentException("Other cursor is <null>");
      } else if (!(xOther instanceof Cursor)) {
         throw new IllegalArgumentException("Incompatible cursors: " + xOther);
      } else {
         Cursor other = (Cursor)xOther;
         if (other._cur == null) {
            throw new IllegalStateException("Other cursor has been disposed");
         } else {
            return other;
         }
      }
   }

   private int twoLocaleOp(XmlCursor xOther, int op, int arg) {
      Cursor other = this.checkCursors(xOther);
      Locale locale = this._cur._locale;
      Locale otherLocale = other._cur._locale;
      if (locale == otherLocale) {
         if (locale.noSync()) {
            return this.twoLocaleOp(other, op, arg);
         } else {
            synchronized(locale) {
               return this.twoLocaleOp(other, op, arg);
            }
         }
      } else if (locale.noSync()) {
         if (otherLocale.noSync()) {
            return this.twoLocaleOp(other, op, arg);
         } else {
            synchronized(otherLocale) {
               return this.twoLocaleOp(other, op, arg);
            }
         }
      } else if (otherLocale.noSync()) {
         synchronized(locale) {
            return this.twoLocaleOp(other, op, arg);
         }
      } else {
         boolean acquired = false;

         int var10;
         try {
            GlobalLock.acquire();
            acquired = true;
            synchronized(locale) {
               synchronized(otherLocale) {
                  GlobalLock.release();
                  acquired = false;
                  var10 = this.twoLocaleOp(other, op, arg);
               }
            }
         } catch (InterruptedException var26) {
            throw new RuntimeException(var26.getMessage(), var26);
         } finally {
            if (acquired) {
               GlobalLock.release();
            }

         }

         return var10;
      }
   }

   private int twoLocaleOp(Cursor other, int op, int arg) {
      Locale locale = this._cur._locale;
      Locale otherLocale = other._cur._locale;
      locale.enter(otherLocale);

      try {
         int var6;
         switch (op) {
            case 0:
               var6 = this._moveXml(other) ? 1 : 0;
               return var6;
            case 1:
               var6 = this._copyXml(other) ? 1 : 0;
               return var6;
            case 2:
               var6 = this._moveXmlContents(other) ? 1 : 0;
               return var6;
            case 3:
               var6 = this._copyXmlContents(other) ? 1 : 0;
               return var6;
            case 4:
               var6 = this._moveChars(arg, other);
               return var6;
            case 5:
               var6 = this._copyChars(arg, other);
               return var6;
            default:
               throw new RuntimeException("Unknown operation: " + op);
         }
      } finally {
         locale.exit(otherLocale);
      }
   }

   public boolean moveXml(XmlCursor xTo) {
      return this.twoLocaleOp((XmlCursor)xTo, 0, 0) == 1;
   }

   public boolean copyXml(XmlCursor xTo) {
      return this.twoLocaleOp((XmlCursor)xTo, 1, 0) == 1;
   }

   public boolean moveXmlContents(XmlCursor xTo) {
      return this.twoLocaleOp((XmlCursor)xTo, 2, 0) == 1;
   }

   public boolean copyXmlContents(XmlCursor xTo) {
      return this.twoLocaleOp((XmlCursor)xTo, 3, 0) == 1;
   }

   public int moveChars(int cch, XmlCursor xTo) {
      return this.twoLocaleOp((XmlCursor)xTo, 4, cch);
   }

   public int copyChars(int cch, XmlCursor xTo) {
      return this.twoLocaleOp((XmlCursor)xTo, 5, cch);
   }

   public boolean toCursor(XmlCursor xOther) {
      Cursor other = this.checkCursors(xOther);
      if (this._cur._locale != other._cur._locale) {
         return false;
      } else if (this._cur._locale.noSync()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._toCursor(other);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._toCursor(other);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean isInSameDocument(XmlCursor xOther) {
      return xOther == null ? false : this._cur.isInSameTree(this.checkCursors(xOther)._cur);
   }

   private Cursor preCheck(XmlCursor xOther) {
      Cursor other = this.checkCursors(xOther);
      if (this._cur._locale != other._cur._locale) {
         throw new IllegalArgumentException("Cursors not in same document");
      } else {
         return other;
      }
   }

   public int comparePosition(XmlCursor xOther) {
      Cursor other = this.preCheck(xOther);
      if (this._cur._locale.noSync()) {
         this._cur._locale.enter();

         int var3;
         try {
            var3 = this._comparePosition(other);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            int var4;
            try {
               var4 = this._comparePosition(other);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean isLeftOf(XmlCursor xOther) {
      Cursor other = this.preCheck(xOther);
      if (this._cur._locale.noSync()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._isLeftOf(other);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._isLeftOf(other);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean isAtSamePositionAs(XmlCursor xOther) {
      Cursor other = this.preCheck(xOther);
      if (this._cur._locale.noSync()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._isAtSamePositionAs(other);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._isAtSamePositionAs(other);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean isRightOf(XmlCursor xOther) {
      Cursor other = this.preCheck(xOther);
      if (this._cur._locale.noSync()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._isRightOf(other);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._isRightOf(other);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public static XmlCursor newCursor(Xobj x, int p) {
      Locale l = x._locale;
      if (l.noSync()) {
         l.enter();

         Cursor var3;
         try {
            var3 = new Cursor(x, p);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Cursor var4;
            try {
               var4 = new Cursor(x, p);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   private boolean preCheck() {
      this.checkThisCursor();
      return this._cur._locale.noSync();
   }

   public void dispose() {
      if (this._cur != null) {
         Locale l = this._cur._locale;
         if (this.preCheck()) {
            l.enter();

            try {
               this._dispose();
            } finally {
               l.exit();
            }
         } else {
            synchronized(l) {
               l.enter();

               try {
                  this._dispose();
               } finally {
                  l.exit();
               }
            }
         }
      }

   }

   public Object monitor() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         Object var1;
         try {
            var1 = this._monitor();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            Object var2;
            try {
               var2 = this._monitor();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XmlDocumentProperties documentProperties() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlDocumentProperties var1;
         try {
            var1 = this._documentProperties();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlDocumentProperties var2;
            try {
               var2 = this._documentProperties();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XmlCursor newCursor() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor var1;
         try {
            var1 = this._newCursor();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor var2;
            try {
               var2 = this._newCursor();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XMLStreamReader newXMLStreamReader() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XMLStreamReader var1;
         try {
            var1 = this._newXMLStreamReader();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XMLStreamReader var2;
            try {
               var2 = this._newXMLStreamReader();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XMLStreamReader newXMLStreamReader(XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XMLStreamReader var2;
         try {
            var2 = this._newXMLStreamReader(options);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XMLStreamReader var3;
            try {
               var3 = this._newXMLStreamReader(options);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   /** @deprecated */
   public XMLInputStream newXMLInputStream() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XMLInputStream var1;
         try {
            var1 = this._newXMLInputStream();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XMLInputStream var2;
            try {
               var2 = this._newXMLInputStream();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public String xmlText() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         String var1;
         try {
            var1 = this._xmlText();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            String var2;
            try {
               var2 = this._xmlText();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public InputStream newInputStream() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         InputStream var1;
         try {
            var1 = this._newInputStream();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            InputStream var2;
            try {
               var2 = this._newInputStream();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public Reader newReader() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         Reader var1;
         try {
            var1 = this._newReader();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            Reader var2;
            try {
               var2 = this._newReader();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public Node newDomNode() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         Node var1;
         try {
            var1 = this._newDomNode();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            Node var2;
            try {
               var2 = this._newDomNode();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public Node getDomNode() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         Node var1;
         try {
            var1 = this._getDomNode();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            Node var2;
            try {
               var2 = this._getDomNode();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public void save(ContentHandler ch, LexicalHandler lh) throws SAXException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(ch, lh);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(ch, lh);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void save(File file) throws IOException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(file);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(file);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void save(OutputStream os) throws IOException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(os);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(os);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void save(Writer w) throws IOException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(w);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(w);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   /** @deprecated */
   public XMLInputStream newXMLInputStream(XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XMLInputStream var2;
         try {
            var2 = this._newXMLInputStream(options);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XMLInputStream var3;
            try {
               var3 = this._newXMLInputStream(options);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public String xmlText(XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         String var2;
         try {
            var2 = this._xmlText(options);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            String var3;
            try {
               var3 = this._xmlText(options);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public InputStream newInputStream(XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         InputStream var2;
         try {
            var2 = this._newInputStream(options);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            InputStream var3;
            try {
               var3 = this._newInputStream(options);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public Reader newReader(XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         Reader var2;
         try {
            var2 = this._newReader(options);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            Reader var3;
            try {
               var3 = this._newReader(options);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public Node newDomNode(XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         Node var2;
         try {
            var2 = this._newDomNode(options);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            Node var3;
            try {
               var3 = this._newDomNode(options);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public void save(ContentHandler ch, LexicalHandler lh, XmlOptions options) throws SAXException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(ch, lh, options);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(ch, lh, options);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void save(File file, XmlOptions options) throws IOException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(file, options);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(file, options);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void save(OutputStream os, XmlOptions options) throws IOException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(os, options);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(os, options);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void save(Writer w, XmlOptions options) throws IOException {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._save(w, options);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._save(w, options);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void push() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._push();
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._push();
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public boolean pop() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._pop();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._pop();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public void selectPath(String path) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._selectPath(path);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._selectPath(path);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void selectPath(String path, XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._selectPath(path, options);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._selectPath(path, options);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public boolean hasNextSelection() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._hasNextSelection();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._hasNextSelection();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean toNextSelection() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._toNextSelection();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._toNextSelection();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean toSelection(int i) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._toSelection(i);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._toSelection(i);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public int getSelectionCount() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         int var1;
         try {
            var1 = this._getSelectionCount();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            int var2;
            try {
               var2 = this._getSelectionCount();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public void addToSelection() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._addToSelection();
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._addToSelection();
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void clearSelections() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._clearSelections();
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._clearSelections();
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public boolean toBookmark(XmlCursor.XmlBookmark bookmark) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._toBookmark(bookmark);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._toBookmark(bookmark);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public XmlCursor.XmlBookmark toNextBookmark(Object key) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.XmlBookmark var2;
         try {
            var2 = this._toNextBookmark(key);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.XmlBookmark var3;
            try {
               var3 = this._toNextBookmark(key);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public XmlCursor.XmlBookmark toPrevBookmark(Object key) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.XmlBookmark var2;
         try {
            var2 = this._toPrevBookmark(key);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.XmlBookmark var3;
            try {
               var3 = this._toPrevBookmark(key);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public QName getName() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         QName var1;
         try {
            var1 = this._getName();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            QName var2;
            try {
               var2 = this._getName();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public void setName(QName name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._setName(name);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._setName(name);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public String namespaceForPrefix(String prefix) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         String var2;
         try {
            var2 = this._namespaceForPrefix(prefix);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            String var3;
            try {
               var3 = this._namespaceForPrefix(prefix);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public String prefixForNamespace(String namespaceURI) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         String var2;
         try {
            var2 = this._prefixForNamespace(namespaceURI);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            String var3;
            try {
               var3 = this._prefixForNamespace(namespaceURI);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public void getAllNamespaces(Map addToThis) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._getAllNamespaces(addToThis);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._getAllNamespaces(addToThis);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public XmlObject getObject() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlObject var1;
         try {
            var1 = this._getObject();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlObject var2;
            try {
               var2 = this._getObject();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XmlCursor.TokenType currentTokenType() {
      if (this.preCheck()) {
         return this._currentTokenType();
      } else {
         synchronized(this._cur._locale) {
            return this._currentTokenType();
         }
      }
   }

   public boolean isStartdoc() {
      if (this.preCheck()) {
         return this._isStartdoc();
      } else {
         synchronized(this._cur._locale) {
            return this._isStartdoc();
         }
      }
   }

   public boolean isEnddoc() {
      if (this.preCheck()) {
         return this._isEnddoc();
      } else {
         synchronized(this._cur._locale) {
            return this._isEnddoc();
         }
      }
   }

   public boolean isStart() {
      if (this.preCheck()) {
         return this._isStart();
      } else {
         synchronized(this._cur._locale) {
            return this._isStart();
         }
      }
   }

   public boolean isEnd() {
      if (this.preCheck()) {
         return this._isEnd();
      } else {
         synchronized(this._cur._locale) {
            return this._isEnd();
         }
      }
   }

   public boolean isText() {
      if (this.preCheck()) {
         return this._isText();
      } else {
         synchronized(this._cur._locale) {
            return this._isText();
         }
      }
   }

   public boolean isAttr() {
      if (this.preCheck()) {
         return this._isAttr();
      } else {
         synchronized(this._cur._locale) {
            return this._isAttr();
         }
      }
   }

   public boolean isNamespace() {
      if (this.preCheck()) {
         return this._isNamespace();
      } else {
         synchronized(this._cur._locale) {
            return this._isNamespace();
         }
      }
   }

   public boolean isComment() {
      if (this.preCheck()) {
         return this._isComment();
      } else {
         synchronized(this._cur._locale) {
            return this._isComment();
         }
      }
   }

   public boolean isProcinst() {
      if (this.preCheck()) {
         return this._isProcinst();
      } else {
         synchronized(this._cur._locale) {
            return this._isProcinst();
         }
      }
   }

   public boolean isContainer() {
      if (this.preCheck()) {
         return this._isContainer();
      } else {
         synchronized(this._cur._locale) {
            return this._isContainer();
         }
      }
   }

   public boolean isFinish() {
      if (this.preCheck()) {
         return this._isFinish();
      } else {
         synchronized(this._cur._locale) {
            return this._isFinish();
         }
      }
   }

   public boolean isAnyAttr() {
      if (this.preCheck()) {
         return this._isAnyAttr();
      } else {
         synchronized(this._cur._locale) {
            return this._isAnyAttr();
         }
      }
   }

   public XmlCursor.TokenType prevTokenType() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.TokenType var1;
         try {
            var1 = this._prevTokenType();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.TokenType var2;
            try {
               var2 = this._prevTokenType();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean hasNextToken() {
      if (this.preCheck()) {
         return this._hasNextToken();
      } else {
         synchronized(this._cur._locale) {
            return this._hasNextToken();
         }
      }
   }

   public boolean hasPrevToken() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._hasPrevToken();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._hasPrevToken();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XmlCursor.TokenType toNextToken() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.TokenType var1;
         try {
            var1 = this._toNextToken();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.TokenType var2;
            try {
               var2 = this._toNextToken();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XmlCursor.TokenType toPrevToken() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.TokenType var1;
         try {
            var1 = this._toPrevToken();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.TokenType var2;
            try {
               var2 = this._toPrevToken();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XmlCursor.TokenType toFirstContentToken() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.TokenType var1;
         try {
            var1 = this._toFirstContentToken();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.TokenType var2;
            try {
               var2 = this._toFirstContentToken();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public XmlCursor.TokenType toEndToken() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.TokenType var1;
         try {
            var1 = this._toEndToken();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.TokenType var2;
            try {
               var2 = this._toEndToken();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public int toNextChar(int cch) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         int var2;
         try {
            var2 = this._toNextChar(cch);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            int var3;
            try {
               var3 = this._toNextChar(cch);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public int toPrevChar(int cch) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         int var2;
         try {
            var2 = this._toPrevChar(cch);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            int var3;
            try {
               var3 = this._toPrevChar(cch);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public boolean ___toNextSibling() {
      if (!this._cur.hasParent()) {
         return false;
      } else {
         Xobj parent = this._cur.getParentNoRoot();
         if (parent == null) {
            this._cur._locale.enter();

            try {
               parent = this._cur.getParent();
            } finally {
               this._cur._locale.exit();
            }
         }

         return Locale.toNextSiblingElement(this._cur, parent);
      }
   }

   public boolean toNextSibling() {
      if (this.preCheck()) {
         return this.___toNextSibling();
      } else {
         synchronized(this._cur._locale) {
            return this.___toNextSibling();
         }
      }
   }

   public boolean toPrevSibling() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._toPrevSibling();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._toPrevSibling();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean toParent() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._toParent();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._toParent();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean toFirstChild() {
      if (this.preCheck()) {
         return this._toFirstChild();
      } else {
         synchronized(this._cur._locale) {
            return this._toFirstChild();
         }
      }
   }

   public boolean toLastChild() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._toLastChild();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._toLastChild();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean toChild(String name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._toChild(name);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._toChild(name);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public boolean toChild(String namespace, String name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._toChild(namespace, name);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._toChild(namespace, name);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean toChild(QName name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._toChild(name);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._toChild(name);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public boolean toChild(int index) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._toChild(index);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._toChild(index);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public boolean toChild(QName name, int index) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._toChild(name, index);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._toChild(name, index);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean toNextSibling(String name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._toNextSibling(name);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._toNextSibling(name);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public boolean toNextSibling(String namespace, String name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._toNextSibling(namespace, name);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._toNextSibling(namespace, name);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean toNextSibling(QName name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._toNextSibling(name);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._toNextSibling(name);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public boolean toFirstAttribute() {
      if (this.preCheck()) {
         return this._toFirstAttribute();
      } else {
         synchronized(this._cur._locale) {
            return this._toFirstAttribute();
         }
      }
   }

   public boolean toLastAttribute() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._toLastAttribute();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._toLastAttribute();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean toNextAttribute() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._toNextAttribute();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._toNextAttribute();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean toPrevAttribute() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._toPrevAttribute();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._toPrevAttribute();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public String getAttributeText(QName attrName) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         String var2;
         try {
            var2 = this._getAttributeText(attrName);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            String var3;
            try {
               var3 = this._getAttributeText(attrName);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public boolean setAttributeText(QName attrName, String value) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var3;
         try {
            var3 = this._setAttributeText(attrName, value);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var4;
            try {
               var4 = this._setAttributeText(attrName, value);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public boolean removeAttribute(QName attrName) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var2;
         try {
            var2 = this._removeAttribute(attrName);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var3;
            try {
               var3 = this._removeAttribute(attrName);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public String getTextValue() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         String var1;
         try {
            var1 = this._getTextValue();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            String var2;
            try {
               var2 = this._getTextValue();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public int getTextValue(char[] chars, int offset, int cch) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         int var4;
         try {
            var4 = this._getTextValue(chars, offset, cch);
         } finally {
            this._cur._locale.exit();
         }

         return var4;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            int var5;
            try {
               var5 = this._getTextValue(chars, offset, cch);
            } finally {
               this._cur._locale.exit();
            }

            return var5;
         }
      }
   }

   public void setTextValue(String text) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._setTextValue(text);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._setTextValue(text);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void setTextValue(char[] sourceChars, int offset, int length) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._setTextValue(sourceChars, offset, length);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._setTextValue(sourceChars, offset, length);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public String getChars() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         String var1;
         try {
            var1 = this._getChars();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            String var2;
            try {
               var2 = this._getChars();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public int getChars(char[] chars, int offset, int cch) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         int var4;
         try {
            var4 = this._getChars(chars, offset, cch);
         } finally {
            this._cur._locale.exit();
         }

         return var4;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            int var5;
            try {
               var5 = this._getChars(chars, offset, cch);
            } finally {
               this._cur._locale.exit();
            }

            return var5;
         }
      }
   }

   public void toStartDoc() {
      if (this.preCheck()) {
         this._toStartDoc();
      } else {
         synchronized(this._cur._locale) {
            this._toStartDoc();
         }
      }

   }

   public void toEndDoc() {
      if (this.preCheck()) {
         this._toEndDoc();
      } else {
         synchronized(this._cur._locale) {
            this._toEndDoc();
         }
      }

   }

   public XmlCursor execQuery(String query) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor var2;
         try {
            var2 = this._execQuery(query);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor var3;
            try {
               var3 = this._execQuery(query);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public XmlCursor execQuery(String query, XmlOptions options) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor var3;
         try {
            var3 = this._execQuery(query, options);
         } finally {
            this._cur._locale.exit();
         }

         return var3;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor var4;
            try {
               var4 = this._execQuery(query, options);
            } finally {
               this._cur._locale.exit();
            }

            return var4;
         }
      }
   }

   public XmlCursor.ChangeStamp getDocChangeStamp() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.ChangeStamp var1;
         try {
            var1 = this._getDocChangeStamp();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.ChangeStamp var2;
            try {
               var2 = this._getDocChangeStamp();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public void setBookmark(XmlCursor.XmlBookmark bookmark) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._setBookmark(bookmark);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._setBookmark(bookmark);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public XmlCursor.XmlBookmark getBookmark(Object key) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         XmlCursor.XmlBookmark var2;
         try {
            var2 = this._getBookmark(key);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            XmlCursor.XmlBookmark var3;
            try {
               var3 = this._getBookmark(key);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public void clearBookmark(Object key) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._clearBookmark(key);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._clearBookmark(key);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void getAllBookmarkRefs(Collection listToFill) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._getAllBookmarkRefs(listToFill);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._getAllBookmarkRefs(listToFill);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public boolean removeXml() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._removeXml();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._removeXml();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public boolean removeXmlContents() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         boolean var1;
         try {
            var1 = this._removeXmlContents();
         } finally {
            this._cur._locale.exit();
         }

         return var1;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            boolean var2;
            try {
               var2 = this._removeXmlContents();
            } finally {
               this._cur._locale.exit();
            }

            return var2;
         }
      }
   }

   public int removeChars(int cch) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         int var2;
         try {
            var2 = this._removeChars(cch);
         } finally {
            this._cur._locale.exit();
         }

         return var2;
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            int var3;
            try {
               var3 = this._removeChars(cch);
            } finally {
               this._cur._locale.exit();
            }

            return var3;
         }
      }
   }

   public void insertChars(String text) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertChars(text);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertChars(text);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertElement(QName name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertElement(name);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertElement(name);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertElement(String localName) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertElement(localName);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertElement(localName);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertElement(String localName, String uri) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertElement(localName, uri);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertElement(localName, uri);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void beginElement(QName name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._beginElement(name);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._beginElement(name);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void beginElement(String localName) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._beginElement(localName);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._beginElement(localName);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void beginElement(String localName, String uri) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._beginElement(localName, uri);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._beginElement(localName, uri);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertElementWithText(QName name, String text) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertElementWithText(name, text);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertElementWithText(name, text);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertElementWithText(String localName, String text) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertElementWithText(localName, text);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertElementWithText(localName, text);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertElementWithText(String localName, String uri, String text) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertElementWithText(localName, uri, text);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertElementWithText(localName, uri, text);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertAttribute(String localName) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertAttribute(localName);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertAttribute(localName);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertAttribute(String localName, String uri) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertAttribute(localName, uri);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertAttribute(localName, uri);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertAttribute(QName name) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertAttribute(name);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertAttribute(name);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertAttributeWithValue(String Name, String value) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertAttributeWithValue(Name, value);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertAttributeWithValue(Name, value);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertAttributeWithValue(String name, String uri, String value) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertAttributeWithValue(name, uri, value);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertAttributeWithValue(name, uri, value);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertAttributeWithValue(QName name, String value) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertAttributeWithValue(name, value);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertAttributeWithValue(name, value);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertNamespace(String prefix, String namespace) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertNamespace(prefix, namespace);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertNamespace(prefix, namespace);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertComment(String text) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertComment(text);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertComment(text);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void insertProcInst(String target, String text) {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._insertProcInst(target, text);
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._insertProcInst(target, text);
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   public void dump() {
      if (this.preCheck()) {
         this._cur._locale.enter();

         try {
            this._dump();
         } finally {
            this._cur._locale.exit();
         }
      } else {
         synchronized(this._cur._locale) {
            this._cur._locale.enter();

            try {
               this._dump();
            } finally {
               this._cur._locale.exit();
            }
         }
      }

   }

   private static final class DomSaver extends Saver {
      private Cur _nodeCur;
      private SchemaType _type;
      private SchemaTypeLoader _stl;
      private XmlOptions _options;
      private boolean _isFrag;

      DomSaver(Cur c, boolean isFrag, XmlOptions options) {
         super(c, options);
         if (c.isUserNode()) {
            this._type = c.getUser().get_schema_type();
         }

         this._stl = c._locale._schemaTypeLoader;
         this._options = options;
         this._isFrag = isFrag;
      }

      Node saveDom() {
         Locale l = Locale.getLocale(this._stl, this._options);
         l.enter();

         Node var3;
         try {
            this._nodeCur = l.getCur();

            while(this.process()) {
            }

            while(!this._nodeCur.isRoot()) {
               this._nodeCur.toParent();
            }

            if (this._type != null) {
               this._nodeCur.setType(this._type);
            }

            Node node = (Node)this._nodeCur.getDom();
            this._nodeCur.release();
            this._nodeCur = null;
            var3 = node;
         } finally {
            l.exit();
         }

         return var3;
      }

      protected boolean emitElement(Saver.SaveCur c, ArrayList attrNames, ArrayList attrValues) {
         if (Locale.isFragmentQName(c.getName())) {
            this._nodeCur.moveTo((Xobj)null, -2);
         }

         this.ensureDoc();
         this._nodeCur.createElement(this.getQualifiedName(c, c.getName()));
         this._nodeCur.next();
         this.iterateMappings();

         while(this.hasMapping()) {
            this._nodeCur.createAttr(this._nodeCur._locale.createXmlns(this.mappingPrefix()));
            this._nodeCur.next();
            this._nodeCur.insertString(this.mappingUri());
            this._nodeCur.toParent();
            this._nodeCur.skipWithAttrs();
            this.nextMapping();
         }

         for(int i = 0; i < attrNames.size(); ++i) {
            this._nodeCur.createAttr(this.getQualifiedName(c, (QName)attrNames.get(i)));
            this._nodeCur.next();
            this._nodeCur.insertString((String)attrValues.get(i));
            this._nodeCur.toParent();
            this._nodeCur.skipWithAttrs();
         }

         return false;
      }

      protected void emitFinish(Saver.SaveCur c) {
         if (!Locale.isFragmentQName(c.getName())) {
            assert this._nodeCur.isEnd();

            this._nodeCur.next();
         }

      }

      protected void emitText(Saver.SaveCur c) {
         this.ensureDoc();
         Object src = c.getChars();
         if (c._cchSrc > 0) {
            this._nodeCur.insertChars(src, c._offSrc, c._cchSrc);
            this._nodeCur.next();
         }

      }

      protected void emitComment(Saver.SaveCur c) {
         this.ensureDoc();
         this._nodeCur.createComment();
         this.emitTextValue(c);
         this._nodeCur.skip();
      }

      protected void emitProcinst(Saver.SaveCur c) {
         this.ensureDoc();
         this._nodeCur.createProcinst(c.getName().getLocalPart());
         this.emitTextValue(c);
         this._nodeCur.skip();
      }

      protected void emitDocType(String docTypeName, String publicId, String systemId) {
         this.ensureDoc();
         XmlDocumentProperties props = Locale.getDocProps(this._nodeCur, true);
         props.setDoctypeName(docTypeName);
         props.setDoctypePublicId(publicId);
         props.setDoctypeSystemId(systemId);
      }

      protected void emitStartDoc(Saver.SaveCur c) {
         this.ensureDoc();
      }

      protected void emitEndDoc(Saver.SaveCur c) {
      }

      private QName getQualifiedName(Saver.SaveCur c, QName name) {
         String uri = name.getNamespaceURI();
         String prefix = uri.length() > 0 ? this.getUriMapping(uri) : "";
         return prefix.equals(name.getPrefix()) ? name : this._nodeCur._locale.makeQName(uri, name.getLocalPart(), prefix);
      }

      private void emitTextValue(Saver.SaveCur c) {
         c.push();
         c.next();
         if (c.isText()) {
            this._nodeCur.next();
            this._nodeCur.insertChars(c.getChars(), c._offSrc, c._cchSrc);
            this._nodeCur.toParent();
         }

         c.pop();
      }

      private void ensureDoc() {
         if (!this._nodeCur.isPositioned()) {
            if (this._isFrag) {
               this._nodeCur.createDomDocFragRoot();
            } else {
               this._nodeCur.createDomDocumentRoot();
            }

            this._nodeCur.next();
         }

      }
   }

   private static final class ChangeStampImpl implements XmlCursor.ChangeStamp {
      private final Locale _locale;
      private final long _versionStamp;

      ChangeStampImpl(Locale l) {
         this._locale = l;
         this._versionStamp = this._locale.version();
      }

      public boolean hasChanged() {
         return this._versionStamp != this._locale.version();
      }
   }
}
