package weblogic.rmi.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import weblogic.rmi.extensions.server.RemoteReference;

public final class PhantomRef extends PhantomReference {
   private final int oid;
   private static final boolean DEBUG = false;

   public PhantomRef(ServerReference ref, ReferenceQueue queue) {
      super(ref, queue);
      this.oid = ref.getObjectID();
   }

   public PhantomRef(RemoteReference ref, ReferenceQueue queue) {
      super(ref, queue);
      this.oid = ref.getObjectID();
   }

   public int getOID() {
      return this.oid;
   }
}
