package weblogic.security.internal;

import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import weblogic.security.SecurityLogger;
import weblogic.utils.enumerations.SequencingEnumerator;

public class CombiningPermissionCollection extends PermissionCollection {
   private PermissionCollection pc1;
   private PermissionCollection pc2;
   private boolean treatFirstReadOnly;
   private boolean treatSecondReadOnly;
   private static final boolean DEBUG = false;

   public CombiningPermissionCollection(PermissionCollection pc1, boolean treatFirstReadOnly, PermissionCollection pc2, boolean treatSecondReadOnly) {
      this.pc1 = pc1;
      this.pc2 = pc2;
      this.treatFirstReadOnly = treatFirstReadOnly;
      this.treatSecondReadOnly = treatSecondReadOnly;
   }

   public Enumeration elements() {
      SequencingEnumerator enum_ = new SequencingEnumerator();
      enum_.addEnumeration(this.pc1.elements());
      enum_.addEnumeration(this.pc2.elements());
      return enum_;
   }

   public boolean isReadOnly() {
      return (this.pc1.isReadOnly() || this.treatFirstReadOnly) && (this.pc2.isReadOnly() || this.treatSecondReadOnly);
   }

   public void setReadOnly() {
      this.pc1.setReadOnly();
      this.pc2.setReadOnly();
   }

   public String toString() {
      return this.pc1.toString() + " PLUS " + this.pc2.toString();
   }

   public boolean implies(Permission p) {
      return this.pc1.implies(p) || this.pc2.implies(p);
   }

   public void add(Permission p) {
      if (!this.pc1.isReadOnly() && !this.treatFirstReadOnly) {
         this.pc1.add(p);
      } else {
         if (this.pc2.isReadOnly() || this.treatSecondReadOnly) {
            throw new SecurityException(SecurityLogger.getCantUpdateReadonlyPermColl());
         }

         this.pc2.add(p);
      }

   }
}
