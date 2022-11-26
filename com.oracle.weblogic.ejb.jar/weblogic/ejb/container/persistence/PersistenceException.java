package weblogic.ejb.container.persistence;

import weblogic.utils.ErrorCollectionException;

public final class PersistenceException extends ErrorCollectionException {
   private static final long serialVersionUID = 4725685066548964391L;

   public PersistenceException(String msg) {
      super(msg);
   }
}
