package com.kenai.jffi;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class NativeMethods {
   private static final Map registeredMethods = new WeakHashMap();
   private final ResourceHolder memory;

   private NativeMethods(ResourceHolder memory) {
      this.memory = memory;
   }

   public static final synchronized void register(Class clazz, List methods) {
      int stringSize = 0;

      NativeMethod m;
      for(Iterator var3 = methods.iterator(); var3.hasNext(); stringSize += m.signature.getBytes().length + 1) {
         m = (NativeMethod)var3.next();
         stringSize += m.name.getBytes().length + 1;
      }

      int ptrSize = Platform.getPlatform().addressSize() / 8;
      MemoryIO mm = MemoryIO.getInstance();
      int structSize = methods.size() * 3 * ptrSize;
      long memory = mm.allocateMemory((long)(structSize + stringSize), true);
      if (memory == 0L) {
         throw new OutOfMemoryError("could not allocate native memory");
      } else {
         NativeMethods nm = new NativeMethods(new ResourceHolder(mm, memory));
         int off = 0;
         int stringOff = structSize;

         for(Iterator var11 = methods.iterator(); var11.hasNext(); off += ptrSize) {
            NativeMethod m = (NativeMethod)var11.next();
            byte[] name = m.name.getBytes();
            long nameAddress = memory + (long)stringOff;
            stringOff += name.length + 1;
            mm.putZeroTerminatedByteArray(nameAddress, name, 0, name.length);
            byte[] sig = m.signature.getBytes();
            long sigAddress = memory + (long)stringOff;
            stringOff += sig.length + 1;
            mm.putZeroTerminatedByteArray(sigAddress, sig, 0, sig.length);
            mm.putAddress(memory + (long)off, nameAddress);
            off += ptrSize;
            mm.putAddress(memory + (long)off, sigAddress);
            off += ptrSize;
            mm.putAddress(memory + (long)off, m.function);
         }

         if (Foreign.getInstance().registerNatives(clazz, memory, methods.size()) != 0) {
            throw new RuntimeException("failed to register native methods");
         } else {
            registeredMethods.put(clazz, nm);
         }
      }
   }

   public static final synchronized void unregister(Class clazz) {
      if (!registeredMethods.containsKey(clazz)) {
         throw new IllegalArgumentException("methods were not registered on class via NativeMethods.register");
      } else if (Foreign.getInstance().unregisterNatives(clazz) != 0) {
         throw new RuntimeException("failed to unregister native methods");
      } else {
         registeredMethods.remove(clazz);
      }
   }

   private static final class ResourceHolder {
      private final MemoryIO mm;
      private final long memory;

      public ResourceHolder(MemoryIO mm, long memory) {
         this.mm = mm;
         this.memory = memory;
      }

      protected void finalize() throws Throwable {
         try {
            this.mm.freeMemory(this.memory);
         } catch (Throwable var5) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Exception when freeing native method struct array: %s", var5.getLocalizedMessage());
         } finally {
            super.finalize();
         }

      }
   }
}
