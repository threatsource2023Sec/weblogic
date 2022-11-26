package com.bea.core.repackaged.springframework.cglib.core;

import java.lang.ref.WeakReference;

public class WeakCacheKey extends WeakReference {
   private final int hash;

   public WeakCacheKey(Object referent) {
      super(referent);
      this.hash = referent.hashCode();
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof WeakCacheKey)) {
         return false;
      } else {
         Object ours = this.get();
         Object theirs = ((WeakCacheKey)obj).get();
         return ours != null && theirs != null && ours.equals(theirs);
      }
   }

   public int hashCode() {
      return this.hash;
   }

   public String toString() {
      Object t = this.get();
      return t == null ? "Clean WeakIdentityKey, hash: " + this.hash : t.toString();
   }
}
