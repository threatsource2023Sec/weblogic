package org.apache.xmlbeans.impl.store;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.impl.common.ValidatorListener;

final class Validate implements ValidatorListener.Event {
   private ValidatorListener _sink;
   private Cur _cur;
   private boolean _hasText;
   private boolean _oneChunk;
   private Cur _textCur;
   private StringBuffer _textSb;

   Validate(Cur c, ValidatorListener sink) {
      if (!c.isUserNode()) {
         throw new IllegalStateException("Inappropriate location to validate");
      } else {
         this._sink = sink;
         this._cur = c;
         this._textCur = c.tempCur();
         this._hasText = false;
         this._cur.push();

         try {
            this.process();
         } finally {
            this._cur.pop();
            this._cur = null;
            this._sink = null;
            this._textCur.release();
         }

      }
   }

   private void process() {
      this.emitEvent(1);
      if (this._cur.isAttr()) {
         this._cur.next();
         if (this._cur.isText()) {
            this.emitText();
         }
      } else {
         assert this._cur.isContainer();

         this.doAttrs();
         this._cur.next();

         for(; !this._cur.isAtEndOfLastPush(); this._cur.next()) {
            switch (this._cur.kind()) {
               case -2:
                  this.emitEvent(2);
                  break;
               case -1:
               case 1:
               case 3:
               default:
                  throw new RuntimeException("Unexpected kind: " + this._cur.kind());
               case 0:
                  this.emitText();
                  break;
               case 2:
                  this.emitEvent(1);
                  this.doAttrs();
                  break;
               case 4:
               case 5:
                  this._cur.toEnd();
            }
         }
      }

      this.emitEvent(2);
   }

   private void doAttrs() {
      assert !this._hasText;

      if (this._cur.toFirstAttr()) {
         do {
            if (this._cur.isNormalAttr() && !this._cur.getUri().equals("http://www.w3.org/2001/XMLSchema-instance")) {
               this._sink.nextEvent(4, this);
            }
         } while(this._cur.toNextAttr());

         this._cur.toParent();
      }

      this._sink.nextEvent(5, this);
   }

   private void emitText() {
      assert this._cur.isText();

      if (this._hasText) {
         if (this._oneChunk) {
            if (this._textSb == null) {
               this._textSb = new StringBuffer();
            } else {
               this._textSb.delete(0, this._textSb.length());
            }

            assert this._textCur.isText();

            CharUtil.getString(this._textSb, this._textCur.getChars(-1), this._textCur._offSrc, this._textCur._cchSrc);
            this._oneChunk = false;
         }

         assert this._textSb != null && this._textSb.length() > 0;

         CharUtil.getString(this._textSb, this._cur.getChars(-1), this._cur._offSrc, this._cur._cchSrc);
      } else {
         this._hasText = true;
         this._oneChunk = true;
         this._textCur.moveToCur(this._cur);
      }

   }

   private void emitEvent(int kind) {
      assert kind != 3;

      assert kind != 4 || !this._hasText;

      assert kind != 5 || !this._hasText;

      if (this._hasText) {
         this._sink.nextEvent(3, this);
         this._hasText = false;
      }

      this._sink.nextEvent(kind, this);
   }

   public String getText() {
      if (this._cur.isAttr()) {
         return this._cur.getValueAsString();
      } else {
         assert this._hasText;

         assert this._oneChunk || this._textSb != null && this._textSb.length() > 0;

         assert !this._oneChunk || this._textCur.isText();

         return this._oneChunk ? this._textCur.getCharsAsString(-1) : this._textSb.toString();
      }
   }

   public String getText(int wsr) {
      if (this._cur.isAttr()) {
         return this._cur.getValueAsString(wsr);
      } else {
         assert this._hasText;

         assert this._oneChunk || this._textSb != null && this._textSb.length() > 0;

         assert !this._oneChunk || this._textCur.isText();

         return this._oneChunk ? this._textCur.getCharsAsString(-1, wsr) : Locale.applyWhiteSpaceRule(this._textSb.toString(), wsr);
      }
   }

   public boolean textIsWhitespace() {
      if (this._cur.isAttr()) {
         return this._cur._locale.getCharUtil().isWhiteSpace(this._cur.getFirstChars(), this._cur._offSrc, this._cur._cchSrc);
      } else {
         assert this._hasText;

         if (this._oneChunk) {
            return this._cur._locale.getCharUtil().isWhiteSpace(this._textCur.getChars(-1), this._textCur._offSrc, this._textCur._cchSrc);
         } else {
            String s = this._textSb.toString();
            return this._cur._locale.getCharUtil().isWhiteSpace(s, 0, s.length());
         }
      }
   }

   public String getNamespaceForPrefix(String prefix) {
      return this._cur.namespaceForPrefix(prefix, true);
   }

   public XmlCursor getLocationAsCursor() {
      return new Cursor(this._cur);
   }

   public Location getLocation() {
      return null;
   }

   public String getXsiType() {
      return this._cur.getAttrValue(Locale._xsiType);
   }

   public String getXsiNil() {
      return this._cur.getAttrValue(Locale._xsiNil);
   }

   public String getXsiLoc() {
      return this._cur.getAttrValue(Locale._xsiLoc);
   }

   public String getXsiNoLoc() {
      return this._cur.getAttrValue(Locale._xsiNoLoc);
   }

   public QName getName() {
      return this._cur.isAtLastPush() ? null : this._cur.getName();
   }
}
