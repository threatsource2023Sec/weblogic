package com.bea.core.security.managers.internal;

import weblogic.kernel.ThreadLocalStack;
import weblogic.security.subject.AbstractSubject;

public class WLSClientStackService {
   private static final ThreadLocalStack threadSubject = new ThreadLocalStack(true);

   public AbstractSubject peekIdentity() {
      if (this.getSize() <= 0) {
         return null;
      } else {
         AbstractSubject obj = (AbstractSubject)threadSubject.get();
         return obj;
      }
   }

   public void pushIdentity(AbstractSubject userIdentity) {
      if (userIdentity == null) {
         throw new IllegalArgumentException("Illegal null Subject passed as a parameter.");
      } else {
         threadSubject.push(userIdentity);
      }
   }

   public AbstractSubject popIdentity() {
      return this.getSize() <= 0 ? null : (AbstractSubject)threadSubject.pop();
   }

   public int getSize() {
      return threadSubject.getSize();
   }
}
