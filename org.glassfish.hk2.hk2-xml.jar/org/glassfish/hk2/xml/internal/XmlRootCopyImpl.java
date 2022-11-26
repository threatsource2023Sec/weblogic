package org.glassfish.hk2.xml.internal;

import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlRootCopy;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.jaxb.internal.BaseHK2JAXBBean;

public class XmlRootCopyImpl implements XmlRootCopy {
   private final XmlRootHandleImpl parent;
   private final long basis;
   private final Object copy;

   XmlRootCopyImpl(XmlRootHandleImpl parent, long basis, Object copy) {
      if (copy == null) {
         throw new IllegalStateException("Only a non-empty Handle can be copied");
      } else {
         this.parent = parent;
         this.basis = basis;
         this.copy = copy;
      }
   }

   public XmlRootHandle getParent() {
      return this.parent;
   }

   public Object getChildRoot() {
      return this.copy;
   }

   public boolean isMergeable() {
      return this.parent.getRevision() == this.basis;
   }

   public void merge() {
      boolean success = false;
      XmlHandleTransaction handle = this.parent.lockForTransaction();

      try {
         if (!this.isMergeable()) {
            throw new AssertionError("Parent has changed since copy was made, no merge possible");
         }

         BaseHK2JAXBBean copyBean = (BaseHK2JAXBBean)this.copy;
         BaseHK2JAXBBean original = (BaseHK2JAXBBean)this.parent.getRoot();
         Differences differences = Utilities.getDiff(original, copyBean);
         if (!differences.getDifferences().isEmpty()) {
            Utilities.applyDiff(differences, this.parent.getChangeInfo());
         }

         success = true;
      } finally {
         if (success) {
            handle.commit();
         } else {
            handle.abandon();
         }

      }

   }

   public String toString() {
      return "XmlRootCopyImpl(" + this.parent + "," + this.basis + "," + this.copy + "," + System.identityHashCode(this) + ")";
   }
}
