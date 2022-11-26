package com.bea.xbean.store;

import com.bea.xbean.soap.Detail;
import com.bea.xbean.soap.DetailEntry;
import com.bea.xbean.soap.SOAPBody;
import com.bea.xbean.soap.SOAPBodyElement;
import com.bea.xbean.soap.SOAPElement;
import com.bea.xbean.soap.SOAPEnvelope;
import com.bea.xbean.soap.SOAPFault;
import com.bea.xbean.soap.SOAPFaultElement;
import com.bea.xbean.soap.SOAPHeader;
import com.bea.xbean.soap.SOAPHeaderElement;
import com.bea.xbean.values.TypeStoreUser;
import com.bea.xml.CDataBookmark;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlCursor;
import com.bea.xml.XmlDocumentProperties;
import com.bea.xml.XmlLineNumber;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.PrintStream;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

final class Cur {
   static final int TEXT = 0;
   static final int ROOT = 1;
   static final int ELEM = 2;
   static final int ATTR = 3;
   static final int COMMENT = 4;
   static final int PROCINST = 5;
   static final int POOLED = 0;
   static final int REGISTERED = 1;
   static final int EMBEDDED = 2;
   static final int DISPOSED = 3;
   static final int END_POS = -1;
   static final int NO_POS = -2;
   static final String LOAD_USE_LOCALE_CHAR_UTIL = "LOAD_USE_LOCALE_CHAR_UTIL";
   Locale _locale;
   Xobj _xobj;
   int _pos;
   int _state;
   String _id;
   Cur _nextTemp;
   Cur _prevTemp;
   int _tempFrame;
   Cur _next;
   Cur _prev;
   Locale.Ref _ref;
   int _stackTop;
   int _selectionFirst;
   int _selectionN;
   int _selectionLoc;
   int _selectionCount;
   private int _posTemp;
   int _offSrc;
   int _cchSrc;

   Cur(Locale l) {
      this._locale = l;
      this._pos = -2;
      this._tempFrame = -1;
      this._state = 0;
      this._stackTop = -1;
      this._selectionFirst = -1;
      this._selectionN = -1;
      this._selectionLoc = -1;
      this._selectionCount = 0;
   }

   boolean isPositioned() {
      assert this.isNormal();

      return this._xobj != null;
   }

   static boolean kindIsContainer(int k) {
      return k == 2 || k == 1;
   }

   static boolean kindIsFinish(int k) {
      return k == -2 || k == -1;
   }

   int kind() {
      assert this.isPositioned();

      int kind = this._xobj.kind();
      return this._pos == 0 ? kind : (this._pos == -1 ? -kind : 0);
   }

   boolean isRoot() {
      assert this.isPositioned();

      return this._pos == 0 && this._xobj.kind() == 1;
   }

   boolean isElem() {
      assert this.isPositioned();

      return this._pos == 0 && this._xobj.kind() == 2;
   }

   boolean isAttr() {
      assert this.isPositioned();

      return this._pos == 0 && this._xobj.kind() == 3;
   }

   boolean isComment() {
      assert this.isPositioned();

      return this._pos == 0 && this._xobj.kind() == 4;
   }

   boolean isProcinst() {
      assert this.isPositioned();

      return this._pos == 0 && this._xobj.kind() == 5;
   }

   boolean isText() {
      assert this.isPositioned();

      return this._pos > 0;
   }

   boolean isEnd() {
      assert this.isPositioned();

      return this._pos == -1 && this._xobj.kind() == 2;
   }

   boolean isEndRoot() {
      assert this.isPositioned();

      return this._pos == -1 && this._xobj.kind() == 1;
   }

   boolean isNode() {
      assert this.isPositioned();

      return this._pos == 0;
   }

   boolean isContainer() {
      assert this.isPositioned();

      return this._pos == 0 && kindIsContainer(this._xobj.kind());
   }

   boolean isFinish() {
      assert this.isPositioned();

      return this._pos == -1 && kindIsContainer(this._xobj.kind());
   }

   boolean isUserNode() {
      assert this.isPositioned();

      int k = this.kind();
      return k == 2 || k == 1 || k == 3 && !this.isXmlns();
   }

   boolean isContainerOrFinish() {
      assert this.isPositioned();

      if (this._pos != 0 && this._pos != -1) {
         return false;
      } else {
         int kind = this._xobj.kind();
         return kind == 2 || kind == -2 || kind == 1 || kind == -1;
      }
   }

   boolean isNormalAttr() {
      return this.isNode() && this._xobj.isNormalAttr();
   }

   boolean isXmlns() {
      return this.isNode() && this._xobj.isXmlns();
   }

   boolean isTextCData() {
      return this._xobj.hasBookmark(CDataBookmark.class, this._pos);
   }

   QName getName() {
      assert this.isNode() || this.isEnd();

      return this._xobj._name;
   }

   String getLocal() {
      return this.getName().getLocalPart();
   }

   String getUri() {
      return this.getName().getNamespaceURI();
   }

   String getXmlnsPrefix() {
      assert this.isXmlns();

      return this._xobj.getXmlnsPrefix();
   }

   String getXmlnsUri() {
      assert this.isXmlns();

      return this._xobj.getXmlnsUri();
   }

   boolean isDomDocRoot() {
      return this.isRoot() && this._xobj.getDom() instanceof Document;
   }

   boolean isDomFragRoot() {
      return this.isRoot() && this._xobj.getDom() instanceof DocumentFragment;
   }

   int cchRight() {
      assert this.isPositioned();

      return this._xobj.cchRight(this._pos);
   }

   int cchLeft() {
      assert this.isPositioned();

      return this._xobj.cchLeft(this._pos);
   }

   void createRoot() {
      this.createDomDocFragRoot();
   }

   void createDomDocFragRoot() {
      this.moveTo(new Xobj.DocumentFragXobj(this._locale));
   }

   void createDomDocumentRoot() {
      this.moveTo(createDomDocumentRootXobj(this._locale));
   }

   void createAttr(QName name) {
      this.createHelper(new Xobj.AttrXobj(this._locale, name));
   }

   void createComment() {
      this.createHelper(new Xobj.CommentXobj(this._locale));
   }

   void createProcinst(String target) {
      this.createHelper(new Xobj.ProcInstXobj(this._locale, target));
   }

   void createElement(QName name) {
      this.createElement(name, (QName)null);
   }

   void createElement(QName name, QName parentName) {
      this.createHelper(createElementXobj(this._locale, name, parentName));
   }

   static Xobj createDomDocumentRootXobj(Locale l) {
      return createDomDocumentRootXobj(l, false);
   }

   static Xobj createDomDocumentRootXobj(Locale l, boolean fragment) {
      Object xo;
      if (l._saaj == null) {
         if (fragment) {
            xo = new Xobj.DocumentFragXobj(l);
         } else {
            xo = new Xobj.DocumentXobj(l);
         }
      } else {
         xo = new Xobj.SoapPartDocXobj(l);
      }

      if (l._ownerDoc == null) {
         l._ownerDoc = ((Xobj)xo).getDom();
      }

      return (Xobj)xo;
   }

   static Xobj createElementXobj(Locale l, QName name, QName parentName) {
      if (l._saaj == null) {
         return new Xobj.ElementXobj(l, name);
      } else {
         Class c = l._saaj.identifyElement(name, parentName);
         if (c == SOAPElement.class) {
            return new Xobj.SoapElementXobj(l, name);
         } else if (c == SOAPBody.class) {
            return new Xobj.SoapBodyXobj(l, name);
         } else if (c == SOAPBodyElement.class) {
            return new Xobj.SoapBodyElementXobj(l, name);
         } else if (c == SOAPEnvelope.class) {
            return new Xobj.SoapEnvelopeXobj(l, name);
         } else if (c == SOAPHeader.class) {
            return new Xobj.SoapHeaderXobj(l, name);
         } else if (c == SOAPHeaderElement.class) {
            return new Xobj.SoapHeaderElementXobj(l, name);
         } else if (c == SOAPFaultElement.class) {
            return new Xobj.SoapFaultElementXobj(l, name);
         } else if (c == Detail.class) {
            return new Xobj.DetailXobj(l, name);
         } else if (c == DetailEntry.class) {
            return new Xobj.DetailEntryXobj(l, name);
         } else if (c == SOAPFault.class) {
            return new Xobj.SoapFaultXobj(l, name);
         } else {
            throw new IllegalStateException("Unknown SAAJ element class: " + c);
         }
      }
   }

   private void createHelper(Xobj x) {
      assert x._locale == this._locale;

      if (this.isPositioned()) {
         Cur from = this.tempCur(x, 0);
         from.moveNode(this);
         from.release();
      }

      this.moveTo(x);
   }

   boolean isSamePos(Cur that) {
      assert this.isNormal() && (that == null || that.isNormal());

      return this._xobj == that._xobj && this._pos == that._pos;
   }

   boolean isJustAfterEnd(Cur that) {
      assert this.isNormal() && that != null && that.isNormal() && that.isNode();

      return that._xobj.isJustAfterEnd(this._xobj, this._pos);
   }

   boolean isJustAfterEnd(Xobj x) {
      return x.isJustAfterEnd(this._xobj, this._pos);
   }

   boolean isAtEndOf(Cur that) {
      assert that != null && that.isNormal() && that.isNode();

      return this._xobj == that._xobj && this._pos == -1;
   }

   boolean isInSameTree(Cur that) {
      assert this.isPositioned() && that.isPositioned();

      return this._xobj.isInSameTree(that._xobj);
   }

   int comparePosition(Cur that) {
      assert this.isPositioned() && that.isPositioned();

      if (this._locale != that._locale) {
         return 2;
      } else {
         Xobj xThis = this._xobj;
         int pThis = this._pos == -1 ? xThis.posAfter() - 1 : this._pos;
         Xobj xThat = that._xobj;
         int pThat = that._pos == -1 ? xThat.posAfter() - 1 : that._pos;
         if (xThis == xThat) {
            return pThis < pThat ? -1 : (pThis == pThat ? 0 : 1);
         } else {
            int dThis = 0;

            for(Xobj x = xThis._parent; x != null; x = x._parent) {
               ++dThis;
               if (x == xThat) {
                  return pThat < xThat.posAfter() - 1 ? 1 : -1;
               }
            }

            int dThat = 0;

            for(Xobj x = xThat._parent; x != null; x = x._parent) {
               ++dThat;
               if (x == xThis) {
                  return pThis < xThis.posAfter() - 1 ? -1 : 1;
               }
            }

            while(dThis > dThat) {
               --dThis;
               xThis = xThis._parent;
            }

            while(dThat > dThis) {
               --dThat;
               xThat = xThat._parent;
            }

            assert dThat == dThis;

            if (dThat == 0) {
               return 2;
            } else {
               assert xThis._parent != null && xThat._parent != null;

               while(xThis._parent != xThat._parent) {
                  if ((xThis = xThis._parent) == null) {
                     return 2;
                  }

                  xThat = xThat._parent;
               }

               if (xThis._prevSibling != null && xThat._nextSibling != null) {
                  if (xThis._nextSibling != null && xThat._prevSibling != null) {
                     do {
                        if (xThis == null) {
                           return -1;
                        }
                     } while((xThis = xThis._prevSibling) != xThat);

                     return 1;
                  } else {
                     return 1;
                  }
               } else {
                  return -1;
               }
            }
         }
      }
   }

   void setName(QName newName) {
      assert this.isNode() && newName != null;

      this._xobj.setName(newName);
   }

   void moveTo(Xobj x) {
      this.moveTo(x, 0);
   }

   void moveTo(Xobj x, int p) {
      assert x == null || this._locale == x._locale;

      assert x != null || p == -2;

      assert x == null || x.isNormal(p) || x.isVacant() && x._cchValue == 0 && x._user == null;

      assert this._state == 1 || this._state == 2;

      assert this._state == 2 || this._xobj == null || !this.isOnList(this._xobj._embedded);

      assert this._state == 1 || this._xobj != null && this.isOnList(this._xobj._embedded);

      this.moveToNoCheck(x, p);

      assert this.isNormal() || this._xobj.isVacant() && this._xobj._cchValue == 0 && this._xobj._user == null;
   }

   void moveToNoCheck(Xobj x, int p) {
      if (this._state == 2 && x != this._xobj) {
         this._xobj._embedded = this.listRemove(this._xobj._embedded);
         this._locale._registered = this.listInsert(this._locale._registered);
         this._state = 1;
      }

      this._xobj = x;
      this._pos = p;
   }

   void moveToCur(Cur to) {
      assert this.isNormal() && (to == null || to.isNormal());

      if (to == null) {
         this.moveTo((Xobj)null, -2);
      } else {
         this.moveTo(to._xobj, to._pos);
      }

   }

   void moveToDom(DomImpl.Dom d) {
      assert this._locale == d.locale();

      assert d instanceof Xobj || d instanceof Xobj.SoapPartDom;

      this.moveTo((Xobj)(d instanceof Xobj ? (Xobj)d : ((Xobj.SoapPartDom)d)._docXobj));
   }

   void push() {
      assert this.isPositioned();

      int i = this._locale._locations.allocate(this);
      this._stackTop = this._locale._locations.insert(this._stackTop, this._stackTop, i);
   }

   void pop(boolean stay) {
      if (stay) {
         this.popButStay();
      } else {
         this.pop();
      }

   }

   void popButStay() {
      if (this._stackTop != -1) {
         this._stackTop = this._locale._locations.remove(this._stackTop, this._stackTop);
      }

   }

   boolean pop() {
      if (this._stackTop == -1) {
         return false;
      } else {
         this._locale._locations.moveTo(this._stackTop, this);
         this._stackTop = this._locale._locations.remove(this._stackTop, this._stackTop);
         return true;
      }
   }

   boolean isAtLastPush() {
      assert this._stackTop != -1;

      return this._locale._locations.isSamePos(this._stackTop, this);
   }

   boolean isAtEndOfLastPush() {
      assert this._stackTop != -1;

      return this._locale._locations.isAtEndOf(this._stackTop, this);
   }

   void addToSelection(Cur that) {
      assert that != null && that.isNormal();

      assert this.isPositioned() && that.isPositioned();

      int i = this._locale._locations.allocate(that);
      this._selectionFirst = this._locale._locations.insert(this._selectionFirst, -1, i);
      ++this._selectionCount;
   }

   void addToSelection() {
      assert this.isPositioned();

      int i = this._locale._locations.allocate(this);
      this._selectionFirst = this._locale._locations.insert(this._selectionFirst, -1, i);
      ++this._selectionCount;
   }

   private int selectionIndex(int i) {
      assert this._selectionN >= -1 && i >= 0 && i < this._selectionCount;

      if (this._selectionN == -1) {
         this._selectionN = 0;
         this._selectionLoc = this._selectionFirst;
      }

      while(this._selectionN < i) {
         this._selectionLoc = this._locale._locations.next(this._selectionLoc);
         ++this._selectionN;
      }

      while(this._selectionN > i) {
         this._selectionLoc = this._locale._locations.prev(this._selectionLoc);
         --this._selectionN;
      }

      return this._selectionLoc;
   }

   void removeSelection(int i) {
      assert i >= 0 && i < this._selectionCount;

      int j = this.selectionIndex(i);
      if (i < this._selectionN) {
         --this._selectionN;
      } else if (i == this._selectionN) {
         --this._selectionN;
         if (i == 0) {
            this._selectionLoc = -1;
         } else {
            this._selectionLoc = this._locale._locations.prev(this._selectionLoc);
         }
      }

      this._selectionFirst = this._locale._locations.remove(this._selectionFirst, j);
      --this._selectionCount;
   }

   int selectionCount() {
      return this._selectionCount;
   }

   void moveToSelection(int i) {
      assert i >= 0 && i < this._selectionCount;

      this._locale._locations.moveTo(this.selectionIndex(i), this);
   }

   void clearSelection() {
      assert this._selectionCount >= 0;

      while(this._selectionCount > 0) {
         this.removeSelection(0);
      }

   }

   boolean toParent() {
      return this.toParent(false);
   }

   boolean toParentRaw() {
      return this.toParent(true);
   }

   Xobj getParent() {
      return this.getParent(false);
   }

   Xobj getParentRaw() {
      return this.getParent(true);
   }

   boolean hasParent() {
      assert this.isPositioned();

      if (this._pos != -1 && (this._pos < 1 || this._pos >= this._xobj.posAfter())) {
         assert this._pos == 0 || this._xobj._parent != null;

         return this._xobj._parent != null;
      } else {
         return true;
      }
   }

   Xobj getParentNoRoot() {
      assert this.isPositioned();

      if (this._pos == -1 || this._pos >= 1 && this._pos < this._xobj.posAfter()) {
         return this._xobj;
      } else {
         assert this._pos == 0 || this._xobj._parent != null;

         return this._xobj._parent != null ? this._xobj._parent : null;
      }
   }

   Xobj getParent(boolean raw) {
      assert this.isPositioned();

      if (this._pos != -1 && (this._pos < 1 || this._pos >= this._xobj.posAfter())) {
         assert this._pos == 0 || this._xobj._parent != null;

         if (this._xobj._parent != null) {
            return this._xobj._parent;
         } else if (!raw && !this._xobj.isRoot()) {
            Cur r = this._locale.tempCur();
            r.createRoot();
            Xobj root = r._xobj;
            r.next();
            this.moveNode(r);
            r.release();
            return root;
         } else {
            return null;
         }
      } else {
         return this._xobj;
      }
   }

   boolean toParent(boolean raw) {
      Xobj parent = this.getParent(raw);
      if (parent == null) {
         return false;
      } else {
         this.moveTo(parent);
         return true;
      }
   }

   void toRoot() {
      Xobj xobj;
      for(xobj = this._xobj; !xobj.isRoot(); xobj = xobj._parent) {
         if (xobj._parent == null) {
            Cur r = this._locale.tempCur();
            r.createRoot();
            Xobj root = r._xobj;
            r.next();
            this.moveNode(r);
            r.release();
            xobj = root;
            break;
         }
      }

      this.moveTo(xobj);
   }

   boolean hasText() {
      assert this.isNode();

      return this._xobj.hasTextEnsureOccupancy();
   }

   boolean hasAttrs() {
      assert this.isNode();

      return this._xobj.hasAttrs();
   }

   boolean hasChildren() {
      assert this.isNode();

      return this._xobj.hasChildren();
   }

   boolean toFirstChild() {
      assert this.isNode();

      if (!this._xobj.hasChildren()) {
         return false;
      } else {
         Xobj x;
         for(x = this._xobj._firstChild; x.isAttr(); x = x._nextSibling) {
         }

         this.moveTo(x);
         return true;
      }
   }

   protected boolean toLastChild() {
      assert this.isNode();

      if (!this._xobj.hasChildren()) {
         return false;
      } else {
         this.moveTo(this._xobj._lastChild);
         return true;
      }
   }

   boolean toNextSibling() {
      assert this.isNode();

      if (this._xobj.isAttr()) {
         if (this._xobj._nextSibling != null && this._xobj._nextSibling.isAttr()) {
            this.moveTo(this._xobj._nextSibling);
            return true;
         }
      } else if (this._xobj._nextSibling != null) {
         this.moveTo(this._xobj._nextSibling);
         return true;
      }

      return false;
   }

   void setValueAsQName(QName qname) {
      assert this.isNode();

      String value = qname.getLocalPart();
      String ns = qname.getNamespaceURI();
      String prefix = this.prefixForNamespace(ns, qname.getPrefix().length() > 0 ? qname.getPrefix() : null, true);
      if (prefix.length() > 0) {
         value = prefix + ":" + value;
      }

      this.setValue(value);
   }

   void setValue(String value) {
      assert this.isNode();

      this.moveNodeContents((Cur)null, false);
      this.next();
      this.insertString(value);
      this.toParent();
   }

   void removeFollowingAttrs() {
      assert this.isAttr();

      QName attrName = this.getName();
      this.push();
      if (this.toNextAttr()) {
         while(this.isAttr()) {
            if (this.getName().equals(attrName)) {
               this.moveNode((Cur)null);
            } else if (!this.toNextAttr()) {
               break;
            }
         }
      }

      this.pop();
   }

   String getAttrValue(QName name) {
      String s = null;
      this.push();
      if (this.toAttr(name)) {
         s = this.getValueAsString();
      }

      this.pop();
      return s;
   }

   void setAttrValueAsQName(QName name, QName value) {
      assert this.isContainer();

      if (value == null) {
         this._xobj.removeAttr(name);
      } else {
         if (this.toAttr(name)) {
            this.removeFollowingAttrs();
         } else {
            this.next();
            this.createAttr(name);
         }

         this.setValueAsQName(value);
         this.toParent();
      }

   }

   boolean removeAttr(QName name) {
      assert this.isContainer();

      return this._xobj.removeAttr(name);
   }

   void setAttrValue(QName name, String value) {
      assert this.isContainer();

      this._xobj.setAttr(name, value);
   }

   boolean toAttr(QName name) {
      assert this.isNode();

      Xobj a = this._xobj.getAttr(name);
      if (a == null) {
         return false;
      } else {
         this.moveTo(a);
         return true;
      }
   }

   boolean toFirstAttr() {
      assert this.isNode();

      Xobj firstAttr = this._xobj.firstAttr();
      if (firstAttr == null) {
         return false;
      } else {
         this.moveTo(firstAttr);
         return true;
      }
   }

   boolean toLastAttr() {
      assert this.isNode();

      if (!this.toFirstAttr()) {
         return false;
      } else {
         while(this.toNextAttr()) {
         }

         return true;
      }
   }

   boolean toNextAttr() {
      assert this.isAttr() || this.isContainer();

      Xobj nextAttr = this._xobj.nextAttr();
      if (nextAttr == null) {
         return false;
      } else {
         this.moveTo(nextAttr);
         return true;
      }
   }

   boolean toPrevAttr() {
      if (this.isAttr()) {
         if (this._xobj._prevSibling == null) {
            this.moveTo(this._xobj.ensureParent());
         } else {
            this.moveTo(this._xobj._prevSibling);
         }

         return true;
      } else {
         this.prev();
         if (!this.isContainer()) {
            this.next();
            return false;
         } else {
            return this.toLastAttr();
         }
      }
   }

   boolean skipWithAttrs() {
      assert this.isNode();

      if (this.skip()) {
         return true;
      } else if (this._xobj.isRoot()) {
         return false;
      } else {
         assert this._xobj.isAttr();

         this.toParent();
         this.next();
         return true;
      }
   }

   boolean skip() {
      assert this.isNode();

      if (this._xobj.isRoot()) {
         return false;
      } else {
         if (this._xobj.isAttr()) {
            if (this._xobj._nextSibling == null || !this._xobj._nextSibling.isAttr()) {
               return false;
            }

            this.moveTo(this._xobj._nextSibling, 0);
         } else {
            this.moveTo(this.getNormal(this._xobj, this._xobj.posAfter()), this._posTemp);
         }

         return true;
      }
   }

   void toEnd() {
      assert this.isNode();

      this.moveTo(this._xobj, -1);
   }

   void moveToCharNode(DomImpl.CharNode node) {
      assert node.getDom() != null && node.getDom().locale() == this._locale;

      this.moveToDom(node.getDom());
      this._xobj.ensureOccupancy();

      DomImpl.CharNode n;
      for(n = this._xobj._charNodesValue = updateCharNodes(this._locale, this._xobj, this._xobj._charNodesValue, this._xobj._cchValue); n != null; n = n._next) {
         if (node == n) {
            this.moveTo(this.getNormal(this._xobj, n._off + 1), this._posTemp);
            return;
         }
      }

      for(n = this._xobj._charNodesAfter = updateCharNodes(this._locale, this._xobj, this._xobj._charNodesAfter, this._xobj._cchAfter); n != null; n = n._next) {
         if (node == n) {
            this.moveTo(this.getNormal(this._xobj, n._off + this._xobj._cchValue + 2), this._posTemp);
            return;
         }
      }

      assert false;

   }

   boolean prevWithAttrs() {
      if (this.prev()) {
         return true;
      } else if (!this.isAttr()) {
         return false;
      } else {
         this.toParent();
         return true;
      }
   }

   boolean prev() {
      assert this.isPositioned();

      if (this._xobj.isRoot() && this._pos == 0) {
         return false;
      } else if (this._xobj.isAttr() && this._pos == 0 && this._xobj._prevSibling == null) {
         return false;
      } else {
         Xobj x = this.getDenormal();
         int p = this._posTemp;

         assert p > 0 && p != -1;

         int pa = x.posAfter();
         if (p > pa) {
            p = pa;
         } else if (p == pa) {
            if (!x.isAttr() || x._cchAfter <= 0 && x._nextSibling != null && x._nextSibling.isAttr()) {
               p = -1;
            } else {
               x = x.ensureParent();
               p = 0;
            }
         } else if (p == pa - 1) {
            x.ensureOccupancy();
            p = x._cchValue > 0 ? 1 : 0;
         } else if (p > 1) {
            p = 1;
         } else {
            assert p == 1;

            p = 0;
         }

         this.moveTo(this.getNormal(x, p), this._posTemp);
         return true;
      }
   }

   boolean next(boolean withAttrs) {
      return withAttrs ? this.nextWithAttrs() : this.next();
   }

   boolean nextWithAttrs() {
      int k = this.kind();
      if (kindIsContainer(k)) {
         if (this.toFirstAttr()) {
            return true;
         }
      } else if (k == -3) {
         if (this.next()) {
            return true;
         }

         this.toParent();
         if (!this.toParentRaw()) {
            return false;
         }
      }

      return this.next();
   }

   boolean next() {
      assert this.isNormal();

      Xobj x = this._xobj;
      int p = this._pos;
      int pa = x.posAfter();
      if (p >= pa) {
         p = this._xobj.posMax();
      } else if (p != -1) {
         if (p > 0) {
            assert x._firstChild == null || !x._firstChild.isAttr();

            if (x._firstChild != null) {
               x = x._firstChild;
               p = 0;
            } else {
               p = -1;
            }
         } else {
            assert p == 0;

            x.ensureOccupancy();
            p = 1;
            if (x._cchValue == 0 && x._firstChild != null) {
               if (x._firstChild.isAttr()) {
                  Xobj a;
                  for(a = x._firstChild; a._nextSibling != null && a._nextSibling.isAttr(); a = a._nextSibling) {
                  }

                  if (a._cchAfter > 0) {
                     x = a;
                     p = a.posAfter();
                  } else if (a._nextSibling != null) {
                     x = a._nextSibling;
                     p = 0;
                  }
               } else {
                  x = x._firstChild;
                  p = 0;
               }
            }
         }
      } else {
         if (x.isRoot() || x.isAttr() && (x._nextSibling == null || !x._nextSibling.isAttr())) {
            return false;
         }

         p = pa;
      }

      this.moveTo(this.getNormal(x, p), this._posTemp);
      return true;
   }

   int prevChars(int cch) {
      assert this.isPositioned();

      int cchLeft = this.cchLeft();
      if (cch < 0 || cch > cchLeft) {
         cch = cchLeft;
      }

      if (cch != 0) {
         this.moveTo(this.getNormal(this.getDenormal(), this._posTemp - cch), this._posTemp);
      }

      return cch;
   }

   int nextChars(int cch) {
      assert this.isPositioned();

      int cchRight = this.cchRight();
      if (cchRight == 0) {
         return 0;
      } else if (cch >= 0 && cch < cchRight) {
         this.moveTo(this.getNormal(this._xobj, this._pos + cch), this._posTemp);
         return cch;
      } else {
         this.next();
         return cchRight;
      }
   }

   void setCharNodes(DomImpl.CharNode nodes) {
      assert nodes == null || this._locale == nodes.locale();

      assert this.isPositioned();

      Xobj x = this.getDenormal();
      int p = this._posTemp;

      assert !x.isRoot() || p > 0 && p < x.posAfter();

      if (p >= x.posAfter()) {
         x._charNodesAfter = nodes;
      } else {
         x._charNodesValue = nodes;
      }

      while(nodes != null) {
         nodes.setDom((DomImpl.Dom)x);
         nodes = nodes._next;
      }

   }

   DomImpl.CharNode getCharNodes() {
      assert this.isPositioned();

      assert !this.isRoot();

      Xobj x = this.getDenormal();
      DomImpl.CharNode nodes;
      if (this._posTemp >= x.posAfter()) {
         nodes = x._charNodesAfter = updateCharNodes(this._locale, x, x._charNodesAfter, x._cchAfter);
      } else {
         x.ensureOccupancy();
         nodes = x._charNodesValue = updateCharNodes(this._locale, x, x._charNodesValue, x._cchValue);
      }

      return nodes;
   }

   static DomImpl.CharNode updateCharNodes(Locale l, Xobj x, DomImpl.CharNode nodes, int cch) {
      assert nodes == null || nodes.locale() == l;

      DomImpl.CharNode node = nodes;

      int i;
      for(i = 0; node != null && cch > 0; node = node._next) {
         assert node.getDom() == x;

         if (node._cch > cch) {
            node._cch = cch;
         }

         node._off = i;
         i += node._cch;
         cch -= node._cch;
      }

      if (cch <= 0) {
         while(node != null) {
            assert node.getDom() == x;

            if (node._cch != 0) {
               node._cch = 0;
            }

            node._off = i;
            node = node._next;
         }
      } else {
         DomImpl.CharNode node = l.createTextNode();
         node.setDom((DomImpl.Dom)x);
         node._cch = cch;
         node._off = i;
         nodes = DomImpl.CharNode.appendNode(nodes, node);
      }

      return nodes;
   }

   final QName getXsiTypeName() {
      assert this.isNode();

      return this._xobj.getXsiTypeName();
   }

   final void setXsiType(QName value) {
      assert this.isContainer();

      this.setAttrValueAsQName(Locale._xsiType, value);
   }

   final QName valueAsQName() {
      throw new RuntimeException("Not implemented");
   }

   final String namespaceForPrefix(String prefix, boolean defaultAlwaysMapped) {
      return this._xobj.namespaceForPrefix(prefix, defaultAlwaysMapped);
   }

   final String prefixForNamespace(String ns, String suggestion, boolean createIfMissing) {
      return (this.isContainer() ? this._xobj : this.getParent()).prefixForNamespace(ns, suggestion, createIfMissing);
   }

   boolean contains(Cur that) {
      assert this.isNode();

      assert that != null && that.isPositioned();

      return this._xobj.contains(that);
   }

   void insertString(String s) {
      if (s != null) {
         this.insertChars(s, 0, s.length());
      }

   }

   void insertChars(Object src, int off, int cch) {
      assert this.isPositioned() && !this.isRoot();

      assert CharUtil.isValid(src, off, cch);

      if (cch > 0) {
         this._locale.notifyChange();
         if (this._pos == -1) {
            this._xobj.ensureOccupancy();
         }

         Xobj x = this.getDenormal();
         int p = this._posTemp;

         assert p > 0;

         x.insertCharsHelper(p, src, off, cch, true);
         this.moveTo(x, p);
         ++this._locale._versionAll;
      }
   }

   Object moveChars(Cur to, int cchMove) {
      assert this.isPositioned();

      assert cchMove <= 0 || cchMove <= this.cchRight();

      assert to == null || to.isPositioned() && !to.isRoot();

      if (cchMove < 0) {
         cchMove = this.cchRight();
      }

      if (cchMove == 0) {
         this._offSrc = 0;
         this._cchSrc = 0;
         return null;
      } else {
         Object srcMoved = this.getChars(cchMove);
         int offMoved = this._offSrc;

         assert this.isText() && (this._pos >= this._xobj.posAfter() ? this._xobj._parent : this._xobj).isOccupied();

         if (to == null) {
            for(Xobj.Bookmark b = this._xobj._bookmarks; b != null; b = b._next) {
               if (this.inChars(b, cchMove, false)) {
                  Cur c = this._locale.tempCur();
                  c.createRoot();
                  c.next();
                  Object chars = this.moveChars(c, cchMove);
                  c.release();
                  return chars;
               }
            }
         } else {
            if (this.inChars(to, cchMove, true)) {
               to.moveToCur(this);
               this.nextChars(cchMove);
               this._offSrc = offMoved;
               this._cchSrc = cchMove;
               return srcMoved;
            }

            to.insertChars(srcMoved, offMoved, cchMove);
         }

         this._locale.notifyChange();
         if (to == null) {
            this._xobj.removeCharsHelper(this._pos, cchMove, (Xobj)null, -2, false, true);
         } else {
            this._xobj.removeCharsHelper(this._pos, cchMove, to._xobj, to._pos, false, true);
         }

         ++this._locale._versionAll;
         this._offSrc = offMoved;
         this._cchSrc = cchMove;
         return srcMoved;
      }
   }

   void moveNode(Cur to) {
      assert this.isNode() && !this.isRoot();

      assert to == null || to.isPositioned();

      assert to == null || !this.contains(to);

      assert to == null || !to.isRoot();

      Xobj x = this._xobj;
      this.skip();
      moveNode(x, to);
   }

   private static void transferChars(Xobj xFrom, int pFrom, Xobj xTo, int pTo, int cch) {
      assert xFrom != xTo;

      assert xFrom._locale == xTo._locale;

      assert pFrom > 0 && pFrom < xFrom.posMax();

      assert pTo > 0 && pTo <= xTo.posMax();

      assert cch > 0 && cch <= xFrom.cchRight(pFrom);

      assert pTo >= xTo.posAfter() || xTo.isOccupied();

      xTo.insertCharsHelper(pTo, xFrom.getCharsHelper(pFrom, cch), xFrom._locale._offSrc, xFrom._locale._cchSrc, false);
      xFrom.removeCharsHelper(pFrom, cch, xTo, pTo, true, false);
   }

   static void moveNode(Xobj x, Cur to) {
      assert x != null && !x.isRoot();

      assert to == null || to.isPositioned();

      assert to == null || !x.contains(to);

      assert to == null || !to.isRoot();

      if (to != null) {
         if (to._pos == -1) {
            to._xobj.ensureOccupancy();
         }

         if (to._pos == 0 && to._xobj == x || to.isJustAfterEnd(x)) {
            to.moveTo(x);
            return;
         }
      }

      x._locale.notifyChange();
      ++x._locale._versionAll;
      ++x._locale._versionSansText;
      if (to != null && to._locale != x._locale) {
         to._locale.notifyChange();
         ++to._locale._versionAll;
         ++to._locale._versionSansText;
      }

      if (x.isAttr()) {
         x.invalidateSpecialAttr(to == null ? null : to.getParentRaw());
      } else {
         if (x._parent != null) {
            x._parent.invalidateUser();
         }

         if (to != null && to.hasParent()) {
            to.getParent().invalidateUser();
         }
      }

      if (x._cchAfter > 0) {
         transferChars(x, x.posAfter(), x.getDenormal(0), x.posTemp(), x._cchAfter);
      }

      assert x._cchAfter == 0;

      x._locale.embedCurs();

      Xobj here;
      for(here = x; here != null; here = here.walk(x, true)) {
         while(here._embedded != null) {
            here._embedded.moveTo(x.getNormal(x.posAfter()));
         }

         here.disconnectUser();
         if (to != null) {
            here._locale = to._locale;
         }
      }

      x.removeXobj();
      if (to != null) {
         here = to._xobj;
         boolean append = to._pos != 0;
         int cchRight = to.cchRight();
         if (cchRight > 0) {
            to.push();
            to.next();
            here = to._xobj;
            append = to._pos != 0;
            to.pop();
         }

         if (append) {
            here.appendXobj(x);
         } else {
            here.insertXobj(x);
         }

         if (cchRight > 0) {
            transferChars(to._xobj, to._pos, x, x.posAfter(), cchRight);
         }

         to.moveTo(x);
      }

   }

   void moveNodeContents(Cur to, boolean moveAttrs) {
      assert this._pos == 0;

      assert to == null || !to.isRoot();

      moveNodeContents(this._xobj, to, moveAttrs);
   }

   static void moveNodeContents(Xobj x, Cur to, boolean moveAttrs) {
      assert to == null || !to.isRoot();

      boolean hasAttrs = x.hasAttrs();
      boolean noSubNodesToMove = !x.hasChildren() && (!moveAttrs || !hasAttrs);
      if (noSubNodesToMove) {
         if (x.isVacant() && to == null) {
            x.clearBit(256);
            x.invalidateUser();
            x.invalidateSpecialAttr((Xobj)null);
            ++x._locale._versionAll;
         } else if (x.hasTextEnsureOccupancy()) {
            Cur c = x.tempCur();
            c.next();
            c.moveChars(to, -1);
            c.release();
         }

      } else {
         if (to != null) {
            if (x == to._xobj && to._pos == -1) {
               to.moveTo(x);
               to.next(moveAttrs && hasAttrs);
               return;
            }

            boolean isAtLeftEdge = false;
            if (to._locale == x._locale) {
               to.push();
               to.moveTo(x);
               to.next(moveAttrs && hasAttrs);
               isAtLeftEdge = to.isAtLastPush();
               to.pop();
            }

            if (isAtLeftEdge) {
               return;
            }

            assert !x.contains(to);

            assert to.getParent().isOccupied();
         }

         int valueMovedCch = 0;
         if (x.hasTextNoEnsureOccupancy()) {
            Cur c = x.tempCur();
            c.next();
            c.moveChars(to, -1);
            c.release();
            if (to != null) {
               to.nextChars(valueMovedCch = c._cchSrc);
            }
         }

         x._locale.embedCurs();
         Xobj firstToMove = x.walk(x, true);
         boolean sawBookmark = false;

         Xobj lastToMove;
         Cur surragateTo;
         for(lastToMove = firstToMove; lastToMove != null; lastToMove = lastToMove.walk(x, true)) {
            if (lastToMove._parent == x && lastToMove.isAttr()) {
               assert lastToMove._cchAfter == 0;

               if (!moveAttrs) {
                  firstToMove = lastToMove._nextSibling;
                  continue;
               }

               lastToMove.invalidateSpecialAttr(to == null ? null : to.getParent());
            }

            while((surragateTo = lastToMove._embedded) != null) {
               surragateTo.moveTo(x, -1);
            }

            lastToMove.disconnectUser();
            if (to != null) {
               lastToMove._locale = to._locale;
            }

            sawBookmark = sawBookmark || lastToMove._bookmarks != null;
         }

         lastToMove = x._lastChild;
         surragateTo = null;
         if (sawBookmark && to == null) {
            surragateTo = to = x._locale.tempCur();
            to.createRoot();
            to.next();
         }

         if (!lastToMove.isAttr()) {
            x.invalidateUser();
         }

         ++x._locale._versionAll;
         ++x._locale._versionSansText;
         if (to != null && valueMovedCch == 0) {
            to.getParent().invalidateUser();
            ++to._locale._versionAll;
            ++to._locale._versionSansText;
         }

         x.removeXobjs(firstToMove, lastToMove);
         if (to != null) {
            Xobj here = to._xobj;
            boolean append = to._pos != 0;
            int cchRight = to.cchRight();
            if (cchRight > 0) {
               to.push();
               to.next();
               here = to._xobj;
               append = to._pos != 0;
               to.pop();
            }

            if (!firstToMove.isAttr()) {
               if (cchRight > 0) {
                  transferChars(to._xobj, to._pos, lastToMove, lastToMove.posMax(), cchRight);
               }
            } else {
               Xobj lastNewAttr;
               for(lastNewAttr = firstToMove; lastNewAttr._nextSibling != null && lastNewAttr._nextSibling.isAttr(); lastNewAttr = lastNewAttr._nextSibling) {
               }

               Xobj y = to.getParent();
               if (cchRight > 0) {
                  transferChars(to._xobj, to._pos, lastNewAttr, lastNewAttr.posMax(), cchRight);
               }

               if (y.hasTextNoEnsureOccupancy()) {
                  int p;
                  int cch;
                  if (y._cchValue > 0) {
                     p = 1;
                     cch = y._cchValue;
                  } else {
                     y = y.lastAttr();
                     p = y.posAfter();
                     cch = y._cchAfter;
                  }

                  transferChars(y, p, lastNewAttr, lastNewAttr.posAfter(), cch);
               }
            }

            if (append) {
               here.appendXobjs(firstToMove, lastToMove);
            } else {
               here.insertXobjs(firstToMove, lastToMove);
            }

            to.moveTo(firstToMove);
            to.prevChars(valueMovedCch);
         }

         if (surragateTo != null) {
            surragateTo.release();
         }

      }
   }

   protected final Xobj.Bookmark setBookmark(Object key, Object value) {
      assert this.isNormal();

      assert key != null;

      return this._xobj.setBookmark(this._pos, key, value);
   }

   Object getBookmark(Object key) {
      assert this.isNormal();

      assert key != null;

      for(Xobj.Bookmark b = this._xobj._bookmarks; b != null; b = b._next) {
         if (b._pos == this._pos && b._key == key) {
            return b._value;
         }
      }

      return null;
   }

   int firstBookmarkInChars(Object key, int cch) {
      assert this.isNormal();

      assert key != null;

      assert cch > 0;

      assert cch <= this.cchRight();

      int d = -1;
      if (this.isText()) {
         for(Xobj.Bookmark b = this._xobj._bookmarks; b != null; b = b._next) {
            if (b._key == key && this.inChars(b, cch, false)) {
               d = d != -1 && b._pos - this._pos >= d ? d : b._pos - this._pos;
            }
         }
      }

      return d;
   }

   int firstBookmarkInCharsLeft(Object key, int cch) {
      assert this.isNormal();

      assert key != null;

      assert cch > 0;

      assert cch <= this.cchLeft();

      int d = -1;
      if (this.cchLeft() > 0) {
         Xobj x = this.getDenormal();
         int p = this._posTemp - cch;

         for(Xobj.Bookmark b = x._bookmarks; b != null; b = b._next) {
            if (b._key == key && x.inChars(p, b._xobj, b._pos, cch, false)) {
               d = d != -1 && b._pos - p >= d ? d : b._pos - p;
            }
         }
      }

      return d;
   }

   String getCharsAsString(int cch) {
      assert this.isNormal() && this._xobj != null;

      return this.getCharsAsString(cch, 1);
   }

   String getCharsAsString(int cch, int wsr) {
      return this._xobj.getCharsAsString(this._pos, cch, wsr);
   }

   String getValueAsString(int wsr) {
      assert this.isNode();

      return this._xobj.getValueAsString(wsr);
   }

   String getValueAsString() {
      assert this.isNode();

      assert !this.hasChildren();

      return this._xobj.getValueAsString();
   }

   Object getChars(int cch) {
      assert this.isPositioned();

      return this._xobj.getChars(this._pos, cch, this);
   }

   Object getFirstChars() {
      assert this.isNode();

      Object src = this._xobj.getFirstChars();
      this._offSrc = this._locale._offSrc;
      this._cchSrc = this._locale._cchSrc;
      return src;
   }

   void copyNode(Cur to) {
      assert to != null;

      assert this.isNode();

      Xobj copy = this._xobj.copyNode(to._locale);
      if (to.isPositioned()) {
         moveNode(copy, to);
      } else {
         to.moveTo(copy);
      }

   }

   Cur weakCur(Object o) {
      Cur c = this._locale.weakCur(o);
      c.moveToCur(this);
      return c;
   }

   Cur tempCur() {
      return this.tempCur((String)null);
   }

   Cur tempCur(String id) {
      Cur c = this._locale.tempCur(id);
      c.moveToCur(this);
      return c;
   }

   private Cur tempCur(Xobj x, int p) {
      assert this._locale == x._locale;

      assert x != null || p == -2;

      Cur c = this._locale.tempCur();
      if (x != null) {
         c.moveTo(this.getNormal(x, p), this._posTemp);
      }

      return c;
   }

   boolean inChars(Cur c, int cch, boolean includeEnd) {
      assert this.isPositioned() && this.isText() && this.cchRight() >= cch;

      assert c.isNormal();

      return this._xobj.inChars(this._pos, c._xobj, c._pos, cch, includeEnd);
   }

   boolean inChars(Xobj.Bookmark b, int cch, boolean includeEnd) {
      assert this.isPositioned() && this.isText() && this.cchRight() >= cch;

      assert b._xobj.isNormal(b._pos);

      return this._xobj.inChars(this._pos, b._xobj, b._pos, cch, includeEnd);
   }

   private Xobj getNormal(Xobj x, int p) {
      Xobj nx = x.getNormal(p);
      this._posTemp = x._locale._posTemp;
      return nx;
   }

   private Xobj getDenormal() {
      assert this.isPositioned();

      return this.getDenormal(this._xobj, this._pos);
   }

   private Xobj getDenormal(Xobj x, int p) {
      Xobj dx = x.getDenormal(p);
      this._posTemp = x._locale._posTemp;
      return dx;
   }

   void setType(SchemaType type) {
      this.setType(type, true);
   }

   void setType(SchemaType type, boolean complain) {
      assert type != null;

      assert this.isUserNode();

      TypeStoreUser user = this.peekUser();
      if (user == null || user.get_schema_type() != type) {
         if (this.isRoot()) {
            this._xobj.setStableType(type);
         } else {
            TypeStoreUser parentUser = this._xobj.ensureParent().getUser();
            if (this.isAttr()) {
               if (complain && parentUser.get_attribute_type(this.getName()) != type) {
                  throw new IllegalArgumentException("Can't set type of attribute to " + type.toString());
               }
            } else {
               assert this.isElem();

               if (parentUser.get_element_type(this.getName(), (QName)null) == type) {
                  this.removeAttr(Locale._xsiType);
               } else {
                  QName typeName = type.getName();
                  if (typeName == null) {
                     if (complain) {
                        throw new IllegalArgumentException("Can't set type of element, type is un-named");
                     }
                  } else if (parentUser.get_element_type(this.getName(), typeName) != type) {
                     if (complain) {
                        throw new IllegalArgumentException("Can't set type of element, invalid type");
                     }
                  } else {
                     this.setAttrValueAsQName(Locale._xsiType, typeName);
                  }
               }
            }
         }
      }
   }

   void setSubstitution(QName name, SchemaType type) {
      this.setSubstitution(name, type, true);
   }

   void setSubstitution(QName name, SchemaType type, boolean complain) {
      assert name != null;

      assert type != null;

      assert this.isUserNode();

      TypeStoreUser user = this.peekUser();
      if (user == null || user.get_schema_type() != type || !name.equals(this.getName())) {
         if (!this.isRoot()) {
            TypeStoreUser parentUser = this._xobj.ensureParent().getUser();
            if (this.isAttr()) {
               if (complain) {
                  throw new IllegalArgumentException("Can't use substitution with attributes");
               }
            } else {
               assert this.isElem();

               if (parentUser.get_element_type(name, (QName)null) == type) {
                  this.setName(name);
                  this.removeAttr(Locale._xsiType);
               } else {
                  QName typeName = type.getName();
                  if (typeName == null) {
                     if (complain) {
                        throw new IllegalArgumentException("Can't set xsi:type on element, type is un-named");
                     }
                  } else if (parentUser.get_element_type(name, typeName) != type) {
                     if (complain) {
                        throw new IllegalArgumentException("Can't set xsi:type on element, invalid type");
                     }
                  } else {
                     this.setName(name);
                     this.setAttrValueAsQName(Locale._xsiType, typeName);
                  }
               }
            }
         }
      }
   }

   TypeStoreUser peekUser() {
      assert this.isUserNode();

      return this._xobj._user;
   }

   XmlObject getObject() {
      return this.isUserNode() ? (XmlObject)this.getUser() : null;
   }

   TypeStoreUser getUser() {
      assert this.isUserNode();

      return this._xobj.getUser();
   }

   DomImpl.Dom getDom() {
      assert this.isNormal();

      assert this.isPositioned();

      if (!this.isText()) {
         return this._xobj.getDom();
      } else {
         int cch = this.cchLeft();

         DomImpl.CharNode cn;
         for(cn = this.getCharNodes(); (cch -= cn._cch) >= 0; cn = cn._next) {
         }

         return cn;
      }
   }

   static void release(Cur c) {
      if (c != null) {
         c.release();
      }

   }

   void release() {
      if (this._tempFrame >= 0) {
         if (this._nextTemp != null) {
            this._nextTemp._prevTemp = this._prevTemp;
         }

         if (this._prevTemp == null) {
            this._locale._tempFrames[this._tempFrame] = this._nextTemp;
         } else {
            this._prevTemp._nextTemp = this._nextTemp;
         }

         this._prevTemp = this._nextTemp = null;
         this._tempFrame = -1;
      }

      if (this._state != 0 && this._state != 3) {
         while(this._stackTop != -1) {
            this.popButStay();
         }

         this.clearSelection();
         this._id = null;
         this.moveToCur((Cur)null);

         assert this.isNormal();

         assert this._xobj == null;

         assert this._pos == -2;

         if (this._ref != null) {
            this._ref.clear();
            this._ref._cur = null;
         }

         this._ref = null;

         assert this._state == 1;

         this._locale._registered = this.listRemove(this._locale._registered);
         if (this._locale._curPoolCount < 16) {
            this._locale._curPool = this.listInsert(this._locale._curPool);
            this._state = 0;
            ++this._locale._curPoolCount;
         } else {
            this._locale = null;
            this._state = 3;
         }
      }

   }

   boolean isOnList(Cur head) {
      while(head != null) {
         if (head == this) {
            return true;
         }

         head = head._next;
      }

      return false;
   }

   Cur listInsert(Cur head) {
      assert this._next == null && this._prev == null;

      if (head == null) {
         head = this._prev = this;
      } else {
         this._prev = head._prev;
         head._prev = head._prev._next = this;
      }

      return head;
   }

   Cur listRemove(Cur head) {
      assert this._prev != null && this.isOnList(head);

      if (this._prev == this) {
         head = null;
      } else {
         if (head == this) {
            head = this._next;
         } else {
            this._prev._next = this._next;
         }

         if (this._next == null) {
            head._prev = this._prev;
         } else {
            this._next._prev = this._prev;
            this._next = null;
         }
      }

      this._prev = null;

      assert this._next == null;

      return head;
   }

   boolean isNormal() {
      if (this._state != 0 && this._state != 3) {
         if (this._xobj == null) {
            return this._pos == -2;
         } else if (!this._xobj.isNormal(this._pos)) {
            return false;
         } else if (this._state == 2) {
            return this.isOnList(this._xobj._embedded);
         } else {
            assert this._state == 1;

            return this.isOnList(this._locale._registered);
         }
      } else {
         return false;
      }
   }

   static String kindName(int kind) {
      switch (kind) {
         case 0:
            return "TEXT";
         case 1:
            return "ROOT";
         case 2:
            return "ELEM";
         case 3:
            return "ATTR";
         case 4:
            return "COMMENT";
         case 5:
            return "PROCINST";
         default:
            return "<< Unknown Kind (" + kind + ") >>";
      }
   }

   static void dump(PrintStream o, DomImpl.Dom d, Object ref) {
   }

   static void dump(PrintStream o, DomImpl.Dom d) {
      d.dump(o);
   }

   static void dump(DomImpl.Dom d) {
      dump(System.out, d);
   }

   static void dump(Node n) {
      dump(System.out, n);
   }

   static void dump(PrintStream o, Node n) {
      dump(o, (DomImpl.Dom)n);
   }

   void dump() {
      dump(System.out, (Xobj)this._xobj, this);
   }

   void dump(PrintStream o) {
      if (this._xobj == null) {
         o.println("Unpositioned xptr");
      } else {
         dump(o, (Xobj)this._xobj, this);
      }
   }

   public static void dump(PrintStream o, Xobj xo, Object ref) {
      if (ref == null) {
         ref = xo;
      }

      while(xo._parent != null) {
         xo = xo._parent;
      }

      dumpXobj(o, xo, 0, ref);
      o.println();
   }

   private static void dumpCur(PrintStream o, String prefix, Cur c, Object ref) {
      o.print(" ");
      if (ref == c) {
         o.print("*:");
      }

      o.print(prefix + (c._id == null ? "<cur>" : c._id) + "[" + c._pos + "]");
   }

   private static void dumpCurs(PrintStream o, Xobj xo, Object ref) {
      Cur c;
      for(c = xo._embedded; c != null; c = c._next) {
         dumpCur(o, "E:", c, ref);
      }

      for(c = xo._locale._registered; c != null; c = c._next) {
         if (c._xobj == xo) {
            dumpCur(o, "R:", c, ref);
         }
      }

   }

   private static void dumpBookmarks(PrintStream o, Xobj xo, Object ref) {
      for(Xobj.Bookmark b = xo._bookmarks; b != null; b = b._next) {
         o.print(" ");
         if (ref == b) {
            o.print("*:");
         }

         if (b._value instanceof XmlLineNumber) {
            XmlLineNumber l = (XmlLineNumber)b._value;
            o.print("<line:" + l.getLine() + ">" + "[" + b._pos + "]");
         } else {
            o.print("<mark>[" + b._pos + "]");
         }
      }

   }

   private static void dumpCharNodes(PrintStream o, DomImpl.CharNode nodes, Object ref) {
      for(DomImpl.CharNode n = nodes; n != null; n = n._next) {
         o.print(" ");
         if (n == ref) {
            o.print("*");
         }

         o.print((n instanceof DomImpl.TextNode ? "TEXT" : "CDATA") + "[" + n._cch + "]");
      }

   }

   private static void dumpChars(PrintStream o, Object src, int off, int cch) {
      o.print("\"");
      String s = CharUtil.getString(src, off, cch);

      for(int i = 0; i < s.length(); ++i) {
         if (i == 36) {
            o.print("...");
            break;
         }

         char ch = s.charAt(i);
         if (ch >= ' ' && ch < 127) {
            o.print(ch);
         } else if (ch == '\n') {
            o.print("\\n");
         } else if (ch == '\r') {
            o.print("\\r");
         } else if (ch == '\t') {
            o.print("\\t");
         } else if (ch == '"') {
            o.print("\\\"");
         } else {
            o.print("<#" + ch + ">");
         }
      }

      o.print("\"");
   }

   private static void dumpXobj(PrintStream o, Xobj xo, int level, Object ref) {
      if (xo != null) {
         if (xo == ref) {
            o.print("* ");
         } else {
            o.print("  ");
         }

         for(int i = 0; i < level; ++i) {
            o.print("  ");
         }

         o.print(kindName(xo.kind()));
         if (xo._name != null) {
            o.print(" ");
            if (xo._name.getPrefix().length() > 0) {
               o.print(xo._name.getPrefix() + ":");
            }

            o.print(xo._name.getLocalPart());
            if (xo._name.getNamespaceURI().length() > 0) {
               o.print("@" + xo._name.getNamespaceURI());
            }
         }

         if (xo._srcValue != null || xo._charNodesValue != null) {
            o.print(" Value( ");
            dumpChars(o, xo._srcValue, xo._offValue, xo._cchValue);
            dumpCharNodes(o, xo._charNodesValue, ref);
            o.print(" )");
         }

         if (xo._user != null) {
            o.print(" (USER)");
         }

         if (xo.isVacant()) {
            o.print(" (VACANT)");
         }

         if (xo._srcAfter != null || xo._charNodesAfter != null) {
            o.print(" After( ");
            dumpChars(o, xo._srcAfter, xo._offAfter, xo._cchAfter);
            dumpCharNodes(o, xo._charNodesAfter, ref);
            o.print(" )");
         }

         dumpCurs(o, xo, ref);
         dumpBookmarks(o, xo, ref);
         String className = xo.getClass().getName();
         int i = className.lastIndexOf(46);
         if (i > 0) {
            className = className.substring(i + 1);
            i = className.lastIndexOf(36);
            if (i > 0) {
               className = className.substring(i + 1);
            }
         }

         o.print(" (");
         o.print(className);
         o.print(")");
         o.println();

         for(xo = xo._firstChild; xo != null; xo = xo._nextSibling) {
            dumpXobj(o, xo, level + 1, ref);
         }

      }
   }

   void setId(String id) {
      this._id = id;
   }

   static final class CurLoadContext extends Locale.LoadContext {
      private boolean _stripLeft = true;
      private Locale _locale;
      private CharUtil _charUtil;
      private Xobj _frontier;
      private boolean _after;
      private Xobj _lastXobj;
      private int _lastPos;
      private boolean _discardDocElem;
      private QName _replaceDocElem;
      private boolean _stripWhitespace;
      private boolean _stripComments;
      private boolean _stripProcinsts;
      private Map _substituteNamespaces;
      private Map _additionalNamespaces;
      private String _doctypeName;
      private String _doctypePublicId;
      private String _doctypeSystemId;

      CurLoadContext(Locale l, XmlOptions options) {
         options = XmlOptions.maskNull(options);
         this._locale = l;
         this._charUtil = options.hasOption("LOAD_USE_LOCALE_CHAR_UTIL") ? this._locale.getCharUtil() : CharUtil.getThreadLocalCharUtil();
         this._frontier = Cur.createDomDocumentRootXobj(this._locale);
         this._after = false;
         this._lastXobj = this._frontier;
         this._lastPos = 0;
         if (options.hasOption("LOAD_REPLACE_DOCUMENT_ELEMENT")) {
            this._replaceDocElem = (QName)options.get("LOAD_REPLACE_DOCUMENT_ELEMENT");
            this._discardDocElem = true;
         }

         this._stripWhitespace = options.hasOption("LOAD_STRIP_WHITESPACE");
         this._stripComments = options.hasOption("LOAD_STRIP_COMMENTS");
         this._stripProcinsts = options.hasOption("LOAD_STRIP_PROCINSTS");
         this._substituteNamespaces = (Map)options.get("LOAD_SUBSTITUTE_NAMESPACES");
         this._additionalNamespaces = (Map)options.get("LOAD_ADDITIONAL_NAMESPACES");
         ++this._locale._versionAll;
         ++this._locale._versionSansText;
      }

      private void start(Xobj xo) {
         assert this._frontier != null;

         assert !this._after || this._frontier._parent != null;

         this.flushText();
         if (this._after) {
            this._frontier = this._frontier._parent;
            this._after = false;
         }

         this._frontier.appendXobj(xo);
         this._frontier = xo;
         this._lastXobj = xo;
         this._lastPos = 0;
      }

      private void end() {
         assert this._frontier != null;

         assert !this._after || this._frontier._parent != null;

         this.flushText();
         if (this._after) {
            this._frontier = this._frontier._parent;
         } else {
            this._after = true;
         }

         this._lastXobj = this._frontier;
         this._lastPos = -1;
      }

      private void text(Object src, int off, int cch) {
         if (cch > 0) {
            this._lastXobj = this._frontier;
            this._lastPos = this._frontier._cchValue + 1;
            if (this._after) {
               this._lastPos += this._frontier._cchAfter + 1;
               this._frontier._srcAfter = this._charUtil.saveChars(src, off, cch, this._frontier._srcAfter, this._frontier._offAfter, this._frontier._cchAfter);
               this._frontier._offAfter = this._charUtil._offSrc;
               this._frontier._cchAfter = this._charUtil._cchSrc;
            } else {
               this._frontier._srcValue = this._charUtil.saveChars(src, off, cch, this._frontier._srcValue, this._frontier._offValue, this._frontier._cchValue);
               this._frontier._offValue = this._charUtil._offSrc;
               this._frontier._cchValue = this._charUtil._cchSrc;
            }

         }
      }

      private void flushText() {
         if (this._stripWhitespace) {
            if (this._after) {
               this._frontier._srcAfter = this._charUtil.stripRight(this._frontier._srcAfter, this._frontier._offAfter, this._frontier._cchAfter);
               this._frontier._offAfter = this._charUtil._offSrc;
               this._frontier._cchAfter = this._charUtil._cchSrc;
            } else {
               this._frontier._srcValue = this._charUtil.stripRight(this._frontier._srcValue, this._frontier._offValue, this._frontier._cchValue);
               this._frontier._offValue = this._charUtil._offSrc;
               this._frontier._cchValue = this._charUtil._cchSrc;
            }
         }

      }

      private Xobj parent() {
         return this._after ? this._frontier._parent : this._frontier;
      }

      private QName checkName(QName name, boolean local) {
         if (this._substituteNamespaces != null && (!local || name.getNamespaceURI().length() > 0)) {
            String substituteUri = (String)this._substituteNamespaces.get(name.getNamespaceURI());
            if (substituteUri != null) {
               name = this._locale.makeQName(substituteUri, name.getLocalPart(), name.getPrefix());
            }
         }

         return name;
      }

      protected void startDTD(String name, String publicId, String systemId) {
         this._doctypeName = name;
         this._doctypePublicId = publicId;
         this._doctypeSystemId = systemId;
      }

      protected void endDTD() {
      }

      protected void startElement(QName name) {
         this.start(Cur.createElementXobj(this._locale, this.checkName(name, false), this.parent()._name));
         this._stripLeft = true;
      }

      protected void endElement() {
         assert this.parent().isElem();

         this.end();
         this._stripLeft = true;
      }

      protected void xmlns(String prefix, String uri) {
         assert this.parent().isContainer();

         if (this._substituteNamespaces != null) {
            String substituteUri = (String)this._substituteNamespaces.get(uri);
            if (substituteUri != null) {
               uri = substituteUri;
            }
         }

         Xobj x = new Xobj.AttrXobj(this._locale, this._locale.createXmlns(prefix));
         this.start(x);
         this.text((Object)uri, 0, uri.length());
         this.end();
         this._lastXobj = x;
         this._lastPos = 0;
      }

      protected void attr(QName name, String value) {
         assert this.parent().isContainer();

         QName parentName = this._after ? this._lastXobj._parent.getQName() : this._lastXobj.getQName();
         boolean isId = this.isAttrOfTypeId(name, parentName);
         Xobj x = isId ? new Xobj.AttrIdXobj(this._locale, this.checkName(name, true)) : new Xobj.AttrXobj(this._locale, this.checkName(name, true));
         this.start((Xobj)x);
         this.text((Object)value, 0, value.length());
         this.end();
         if (isId) {
            Cur c1 = ((Xobj)x).tempCur();
            c1.toRoot();
            Xobj doc = c1._xobj;
            c1.release();
            if (doc instanceof Xobj.DocumentXobj) {
               ((Xobj.DocumentXobj)doc).addIdElement(value, ((Xobj)x)._parent.getDom());
            }
         }

         this._lastXobj = (Xobj)x;
         this._lastPos = 0;
      }

      protected void attr(String local, String uri, String prefix, String value) {
         this.attr(this._locale.makeQName(uri, local, prefix), value);
      }

      protected void procInst(String target, String value) {
         if (!this._stripProcinsts) {
            Xobj x = new Xobj.ProcInstXobj(this._locale, target);
            this.start(x);
            this.text((Object)value, 0, value.length());
            this.end();
            this._lastXobj = x;
            this._lastPos = 0;
         }

         this._stripLeft = true;
      }

      protected void comment(String comment) {
         if (!this._stripComments) {
            this.comment((Object)comment, 0, comment.length());
         }

         this._stripLeft = true;
      }

      protected void comment(char[] chars, int off, int cch) {
         if (!this._stripComments) {
            this.comment(this._charUtil.saveChars(chars, off, cch), this._charUtil._offSrc, this._charUtil._cchSrc);
         }

         this._stripLeft = true;
      }

      private void comment(Object src, int off, int cch) {
         Xobj x = new Xobj.CommentXobj(this._locale);
         this.start(x);
         this.text(src, off, cch);
         this.end();
         this._lastXobj = x;
         this._lastPos = 0;
      }

      private void stripText(Object src, int off, int cch) {
         if (this._stripWhitespace && this._stripLeft) {
            src = this._charUtil.stripLeft(src, off, cch);
            this._stripLeft = false;
            off = this._charUtil._offSrc;
            cch = this._charUtil._cchSrc;
         }

         this.text(src, off, cch);
      }

      protected void text(String s) {
         if (s != null) {
            this.stripText(s, 0, s.length());
         }
      }

      protected void text(char[] src, int off, int cch) {
         this.stripText(src, off, cch);
      }

      protected void bookmark(XmlCursor.XmlBookmark bm) {
         this._lastXobj.setBookmark(this._lastPos, bm.getKey(), bm);
      }

      protected void bookmarkLastNonAttr(XmlCursor.XmlBookmark bm) {
         if (this._lastPos <= 0 && this._lastXobj.isAttr()) {
            assert this._lastXobj._parent != null;

            this._lastXobj._parent.setBookmark(0, bm.getKey(), bm);
         } else {
            this._lastXobj.setBookmark(this._lastPos, bm.getKey(), bm);
         }

      }

      protected void bookmarkLastAttr(QName attrName, XmlCursor.XmlBookmark bm) {
         if (this._lastPos == 0 && this._lastXobj.isAttr()) {
            assert this._lastXobj._parent != null;

            Xobj a = this._lastXobj._parent.getAttr(attrName);
            if (a != null) {
               a.setBookmark(0, bm.getKey(), bm);
            }
         }

      }

      protected void lineNumber(int line, int column, int offset) {
         this._lastXobj.setBookmark(this._lastPos, XmlLineNumber.class, new XmlLineNumber(line, column, offset));
      }

      protected void abort() {
         this._stripLeft = true;

         while(!this.parent().isRoot()) {
            this.end();
         }

         this.finish().release();
      }

      protected Cur finish() {
         this.flushText();
         if (this._after) {
            this._frontier = this._frontier._parent;
         }

         assert this._frontier != null && this._frontier._parent == null && this._frontier.isRoot();

         Cur c = this._frontier.tempCur();
         if (!Locale.toFirstChildElement(c)) {
            return c;
         } else {
            boolean isFrag = Locale.isFragmentQName(c.getName());
            if (this._discardDocElem || isFrag) {
               Cur c2;
               if (this._replaceDocElem != null) {
                  c.setName(this._replaceDocElem);
               } else {
                  while(true) {
                     if (!c.toParent()) {
                        c.next();

                        while(!c.isElem()) {
                           if (c.isText()) {
                              c.moveChars((Cur)null, -1);
                           } else {
                              c.moveNode((Cur)null);
                           }
                        }

                        assert c.isElem();

                        c.skip();

                        while(!c.isFinish()) {
                           if (c.isText()) {
                              c.moveChars((Cur)null, -1);
                           } else {
                              c.moveNode((Cur)null);
                           }
                        }

                        c.toParent();
                        c.next();

                        assert c.isElem();

                        c2 = c.tempCur();
                        c.moveNodeContents(c, true);
                        c.moveToCur(c2);
                        c2.release();
                        c.moveNode((Cur)null);
                        break;
                     }
                  }
               }

               if (isFrag) {
                  c.moveTo(this._frontier);
                  if (c.toFirstAttr()) {
                     label92:
                     do {
                        while(!c.isXmlns() || !c.getXmlnsUri().equals("http://www.openuri.org/fragment")) {
                           if (!c.toNextAttr()) {
                              break label92;
                           }
                        }

                        c.moveNode((Cur)null);
                     } while(c.isAttr());
                  }

                  c.moveTo(this._frontier);
                  this._frontier = Cur.createDomDocumentRootXobj(this._locale, true);
                  c2 = this._frontier.tempCur();
                  c2.next();
                  c.moveNodeContents(c2, true);
                  c.moveTo(this._frontier);
                  c2.release();
               }
            }

            if (this._additionalNamespaces != null) {
               c.moveTo(this._frontier);
               Locale.toFirstChildElement(c);
               Locale.applyNamespaces(c, this._additionalNamespaces);
            }

            if (this._doctypeName != null && (this._doctypePublicId != null || this._doctypeSystemId != null)) {
               XmlDocumentProperties props = Locale.getDocProps(c, true);
               props.setDoctypeName(this._doctypeName);
               if (this._doctypePublicId != null) {
                  props.setDoctypePublicId(this._doctypePublicId);
               }

               if (this._doctypeSystemId != null) {
                  props.setDoctypeSystemId(this._doctypeSystemId);
               }
            }

            c.moveTo(this._frontier);

            assert c.isRoot();

            return c;
         }
      }

      public void dump() {
         this._frontier.dump();
      }
   }

   static final class Locations {
      private static final int NULL = -1;
      private static final int _initialSize = 32;
      private Locale _locale;
      private Xobj[] _xobjs;
      private int[] _poses;
      private Cur[] _curs;
      private int[] _next;
      private int[] _prev;
      private int[] _nextN;
      private int[] _prevN;
      private int _free;
      private int _naked;

      Locations(Locale l) {
         this._locale = l;
         this._xobjs = new Xobj[32];
         this._poses = new int[32];
         this._curs = new Cur[32];
         this._next = new int[32];
         this._prev = new int[32];
         this._nextN = new int[32];
         this._prevN = new int[32];

         for(int i = 31; i >= 0; --i) {
            assert this._xobjs[i] == null;

            this._poses[i] = -2;
            this._next[i] = i + 1;
            this._prev[i] = -1;
            this._nextN[i] = -1;
            this._prevN[i] = -1;
         }

         this._next[31] = -1;
         this._free = 0;
         this._naked = -1;
      }

      boolean isSamePos(int i, Cur c) {
         if (this._curs[i] != null) {
            return c.isSamePos(this._curs[i]);
         } else {
            return c._xobj == this._xobjs[i] && c._pos == this._poses[i];
         }
      }

      boolean isAtEndOf(int i, Cur c) {
         assert this._curs[i] != null || this._poses[i] == 0;

         assert this._curs[i] == null || this._curs[i].isNode();

         if (this._curs[i] != null) {
            return c.isAtEndOf(this._curs[i]);
         } else {
            return c._xobj == this._xobjs[i] && c._pos == -1;
         }
      }

      void moveTo(int i, Cur c) {
         if (this._curs[i] == null) {
            c.moveTo(this._xobjs[i], this._poses[i]);
         } else {
            c.moveToCur(this._curs[i]);
         }

      }

      int insert(int head, int before, int i) {
         return insert(head, before, i, this._next, this._prev);
      }

      int remove(int head, int i) {
         Cur c = this._curs[i];

         assert c != null || this._xobjs[i] != null;

         assert c != null || this._xobjs[i] != null;

         if (c != null) {
            this._curs[i].release();
            this._curs[i] = null;

            assert this._xobjs[i] == null;

            assert this._poses[i] == -2;
         } else {
            assert this._xobjs[i] != null && this._poses[i] != -2;

            this._xobjs[i] = null;
            this._poses[i] = -2;
            this._naked = remove(this._naked, i, this._nextN, this._prevN);
         }

         head = remove(head, i, this._next, this._prev);
         this._next[i] = this._free;
         this._free = i;
         return head;
      }

      int allocate(Cur addThis) {
         assert addThis.isPositioned();

         if (this._free == -1) {
            this.makeRoom();
         }

         int i = this._free;
         this._free = this._next[i];
         this._next[i] = -1;

         assert this._prev[i] == -1;

         assert this._curs[i] == null;

         assert this._xobjs[i] == null;

         assert this._poses[i] == -2;

         this._xobjs[i] = addThis._xobj;
         this._poses[i] = addThis._pos;
         this._naked = insert(this._naked, -1, i, this._nextN, this._prevN);
         return i;
      }

      private static int insert(int head, int before, int i, int[] next, int[] prev) {
         if (head == -1) {
            assert before == -1;

            prev[i] = i;
            head = i;
         } else if (before != -1) {
            prev[i] = prev[before];
            next[i] = before;
            prev[before] = i;
            if (head == before) {
               head = i;
            }
         } else {
            prev[i] = prev[head];

            assert next[i] == -1;

            next[prev[head]] = i;
            prev[head] = i;
         }

         return head;
      }

      private static int remove(int head, int i, int[] next, int[] prev) {
         if (prev[i] == i) {
            assert head == i;

            head = -1;
         } else {
            if (head == i) {
               head = next[i];
            } else {
               next[prev[i]] = next[i];
            }

            if (next[i] == -1) {
               prev[head] = prev[i];
            } else {
               prev[next[i]] = prev[i];
               next[i] = -1;
            }
         }

         prev[i] = -1;

         assert next[i] == -1;

         return head;
      }

      void notifyChange() {
         int i;
         while((i = this._naked) != -1) {
            assert this._curs[i] == null && this._xobjs[i] != null && this._poses[i] != -2;

            this._naked = remove(this._naked, i, this._nextN, this._prevN);
            this._curs[i] = this._locale.getCur();
            this._curs[i].moveTo(this._xobjs[i], this._poses[i]);
            this._xobjs[i] = null;
            this._poses[i] = -2;
         }

      }

      int next(int i) {
         return this._next[i];
      }

      int prev(int i) {
         return this._prev[i];
      }

      private void makeRoom() {
         assert this._free == -1;

         int l = this._xobjs.length;
         Xobj[] oldXobjs = this._xobjs;
         int[] oldPoses = this._poses;
         Cur[] oldCurs = this._curs;
         int[] oldNext = this._next;
         int[] oldPrev = this._prev;
         int[] oldNextN = this._nextN;
         int[] oldPrevN = this._prevN;
         this._xobjs = new Xobj[l * 2];
         this._poses = new int[l * 2];
         this._curs = new Cur[l * 2];
         this._next = new int[l * 2];
         this._prev = new int[l * 2];
         this._nextN = new int[l * 2];
         this._prevN = new int[l * 2];
         System.arraycopy(oldXobjs, 0, this._xobjs, 0, l);
         System.arraycopy(oldPoses, 0, this._poses, 0, l);
         System.arraycopy(oldCurs, 0, this._curs, 0, l);
         System.arraycopy(oldNext, 0, this._next, 0, l);
         System.arraycopy(oldPrev, 0, this._prev, 0, l);
         System.arraycopy(oldNextN, 0, this._nextN, 0, l);
         System.arraycopy(oldPrevN, 0, this._prevN, 0, l);

         for(int i = l * 2 - 1; i >= l; --i) {
            this._next[i] = i + 1;
            this._prev[i] = -1;
            this._nextN[i] = -1;
            this._prevN[i] = -1;
            this._poses[i] = -2;
         }

         this._next[l * 2 - 1] = -1;
         this._free = l;
      }
   }
}
