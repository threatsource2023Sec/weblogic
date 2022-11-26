package weblogic.trace;

import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.protocol.LocalServerIdentity;

/** @deprecated */
@Deprecated
@Service
public class Trace implements FastThreadLocalMarker {
   private static final AuditableThreadLocal txLocal = AuditableThreadLocalFactory.createThreadLocal();
   private static int nextId = -1;
   private final int id;
   private final byte[] serialObject;

   /** @deprecated */
   @Deprecated
   public static byte[] currentTrace() {
      Trace trace = (Trace)txLocal.get();
      return trace == null ? null : trace.getBytes();
   }

   /** @deprecated */
   @Deprecated
   public static byte[] beginTrace(byte[] serialObject) {
      Trace trace = (Trace)txLocal.get();
      if (trace == null) {
         trace = new Trace(serialObject);
         txLocal.set(trace);
      }

      return trace.getBytes();
   }

   /** @deprecated */
   @Deprecated
   public static byte[] endTrace() {
      Trace trace = (Trace)txLocal.get();
      txLocal.set((Object)null);
      return trace == null ? null : trace.getBytes();
   }

   public static void propagateTrace(byte[] serialObject) {
      txLocal.set(new Trace(serialObject));
   }

   private static synchronized int getNextID() {
      if (nextId < 0) {
         nextId = Math.abs(LocalServerIdentity.getIdentity().hashCode());
      }

      return nextId++;
   }

   private Trace(byte[] serialObject) {
      this(getNextID(), serialObject);
   }

   private Trace(int tid, byte[] serialObject) {
      this.id = tid;
      this.serialObject = serialObject;
   }

   public byte[] getBytes() {
      return this.serialObject;
   }

   public int hashCode() {
      return this.id;
   }

   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object instanceof Trace) {
         Trace t = (Trace)object;
         return t.id == this.id;
      } else {
         return false;
      }
   }

   public String toString() {
      return super.toString() + " id: '" + this.id + "'";
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
