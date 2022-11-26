package org.glassfish.hk2.xml.internal;

import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class XmlHandleTransactionImpl implements XmlHandleTransaction {
   private final XmlRootHandle root;
   private final DynamicChangeInfo changeInfo;

   public XmlHandleTransactionImpl(XmlRootHandle root, DynamicChangeInfo changeInfo) {
      this.root = root;
      this.changeInfo = changeInfo;
      changeInfo.getWriteLock().lock();
      changeInfo.startOrContinueChange((BaseHK2JAXBBean)null);
   }

   public XmlRootHandle getRootHandle() {
      return this.root;
   }

   public void commit() throws MultiException {
      try {
         this.changeInfo.endOrDeferChange(true);
      } finally {
         this.changeInfo.getWriteLock().unlock();
      }

   }

   public void abandon() {
      try {
         this.changeInfo.endOrDeferChange(false);
      } finally {
         this.changeInfo.getWriteLock().unlock();
      }

   }

   public String toString() {
      return "XmlHandleTransactionImpl(" + this.root + "," + System.identityHashCode(this) + ")";
   }
}
