package com.bea.core.security.managers.internal;

import java.util.Iterator;
import java.util.LinkedList;
import weblogic.security.subject.AbstractSubject;

public class WLSStackService {
   private static ThreadLocal stack = new ThreadLocal() {
      protected synchronized LinkedList initialValue() {
         return new LinkedList();
      }
   };

   public AbstractSubject peekIdentity() {
      LinkedList subjectStack = (LinkedList)stack.get();
      return subjectStack.size() <= 0 ? null : (AbstractSubject)subjectStack.getLast();
   }

   public AbstractSubject popIdentity() {
      LinkedList subjectStack = (LinkedList)stack.get();
      return subjectStack.size() <= 0 ? null : (AbstractSubject)subjectStack.removeLast();
   }

   public void pushIdentity(AbstractSubject identity) {
      if (identity == null) {
         throw new IllegalArgumentException();
      } else {
         LinkedList subjectStack = (LinkedList)stack.get();
         subjectStack.addLast(identity);
      }
   }

   public int getSize() {
      return ((LinkedList)stack.get()).size();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      LinkedList ids = (LinkedList)stack.get();
      int lcv = 0;
      Iterator var4 = ids.iterator();

      while(var4.hasNext()) {
         AbstractSubject id = (AbstractSubject)var4.next();
         sb.append("\t" + lcv++ + ". " + id + "\n");
      }

      return "SecurityStackService(" + System.identityHashCode(this) + ",\n" + sb.toString() + ")";
   }
}
