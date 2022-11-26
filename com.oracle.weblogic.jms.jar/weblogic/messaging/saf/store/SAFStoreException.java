package weblogic.messaging.saf.store;

import weblogic.messaging.saf.SAFException;

public final class SAFStoreException extends SAFException {
   static final long serialVersionUID = -1658399020464780884L;
   private final SAFStore store;

   public SAFStoreException(SAFStore store, Throwable thrown) {
      super(thrown);
      this.store = store;
   }

   public String toString() {
      String s = super.toString();
      return "<SAFStoreException> : " + this.store + s;
   }
}
