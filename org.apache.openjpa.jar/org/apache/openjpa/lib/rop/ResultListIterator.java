package org.apache.openjpa.lib.rop;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.apache.openjpa.lib.util.Localizer;

public class ResultListIterator extends AbstractListIterator {
   private static final Localizer _loc = Localizer.forPackage(ResultListIterator.class);
   private final ListIterator _li;
   private final ResultList _rl;

   public ResultListIterator(ListIterator li, ResultList rl) {
      this._li = li;
      this._rl = rl;
   }

   public ResultList getResultList() {
      return this._rl;
   }

   public boolean hasNext() {
      return this._rl.isClosed() ? false : this._li.hasNext();
   }

   public boolean hasPrevious() {
      return this._li.hasPrevious();
   }

   public Object next() {
      if (this._rl.isClosed()) {
         throw new NoSuchElementException(_loc.get("closed").getMessage());
      } else {
         return this._li.next();
      }
   }

   public int nextIndex() {
      return this._li.nextIndex();
   }

   public Object previous() {
      return this._li.previous();
   }

   public int previousIndex() {
      return this._li.previousIndex();
   }
}
