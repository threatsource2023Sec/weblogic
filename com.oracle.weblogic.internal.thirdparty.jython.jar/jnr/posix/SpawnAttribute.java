package jnr.posix;

import jnr.ffi.Pointer;

public abstract class SpawnAttribute {
   public static final int RESETIDS = 1;
   public static final int SETPGROUP = 2;
   public static final int SETSIGDEF = 4;
   public static final int SETSIGMASK = 8;

   abstract boolean set(POSIX var1, Pointer var2);

   public static SpawnAttribute pgroup(long pgroup) {
      return new PGroup(pgroup);
   }

   public static SpawnAttribute flags(short flags) {
      return new SetFlags(flags);
   }

   public static SpawnAttribute sigdef(long sigdef) {
      throw new RuntimeException("sigdefault not yet supported");
   }

   public static SpawnAttribute sigmask(long sigmask) {
      throw new RuntimeException("sigmask not yet supported");
   }

   private static final class Sigdef extends SpawnAttribute {
      final long sigdef;

      public Sigdef(long sigdef) {
         this.sigdef = sigdef;
      }

      final boolean set(POSIX posix, Pointer nativeSpawnAttr) {
         throw new RuntimeException("sigdefault not yet supported");
      }

      public String toString() {
         return "SpawnAttribute::Sigdef(def = " + Long.toHexString(this.sigdef) + ")";
      }
   }

   private static final class Sigmask extends SpawnAttribute {
      final long sigmask;

      public Sigmask(long sigmask) {
         this.sigmask = sigmask;
      }

      final boolean set(POSIX posix, Pointer nativeSpawnAttr) {
         throw new RuntimeException("sigmask not yet supported");
      }

      public String toString() {
         return "SpawnAttribute::Sigmask(mask = " + Long.toHexString(this.sigmask) + ")";
      }
   }

   private static final class SetFlags extends SpawnAttribute {
      final short flags;

      public SetFlags(short flags) {
         this.flags = flags;
      }

      final boolean set(POSIX posix, Pointer nativeSpawnAttr) {
         return ((UnixLibC)posix.libc()).posix_spawnattr_setflags(nativeSpawnAttr, this.flags) == 0;
      }

      public String toString() {
         return "SpawnAttribute::SetFlags(flags = " + Integer.toHexString(this.flags) + ")";
      }
   }

   private static final class PGroup extends SpawnAttribute {
      final long pgroup;

      public PGroup(long pgroup) {
         this.pgroup = pgroup;
      }

      final boolean set(POSIX posix, Pointer nativeSpawnAttr) {
         return ((UnixLibC)posix.libc()).posix_spawnattr_setpgroup(nativeSpawnAttr, this.pgroup) == 0;
      }

      public String toString() {
         return "SpawnAttribute::PGroup(pgroup = " + this.pgroup + ")";
      }
   }
}
