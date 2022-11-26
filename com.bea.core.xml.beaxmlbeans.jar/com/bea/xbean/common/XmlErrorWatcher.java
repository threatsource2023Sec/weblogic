package com.bea.xbean.common;

import com.bea.xml.XmlError;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class XmlErrorWatcher extends AbstractCollection {
   private Collection _underlying;
   private XmlError _firstError;

   public XmlErrorWatcher(Collection underlying) {
      this._underlying = underlying;
   }

   public boolean add(Object o) {
      if (this._firstError == null && o instanceof XmlError && ((XmlError)o).getSeverity() == 0) {
         this._firstError = (XmlError)o;
      }

      return this._underlying == null ? false : this._underlying.add(o);
   }

   public Iterator iterator() {
      return this._underlying == null ? Collections.EMPTY_LIST.iterator() : this._underlying.iterator();
   }

   public int size() {
      return this._underlying == null ? 0 : this._underlying.size();
   }

   public boolean hasError() {
      return this._firstError != null;
   }

   public XmlError firstError() {
      return this._firstError;
   }
}
