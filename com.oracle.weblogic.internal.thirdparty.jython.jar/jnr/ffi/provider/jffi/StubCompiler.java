package jnr.ffi.provider.jffi;

import com.kenai.jffi.Function;
import com.kenai.jffi.Internals;
import com.kenai.jffi.PageManager;
import com.kenai.jffi.Platform;
import jnr.ffi.CallingConvention;
import jnr.ffi.Runtime;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.x86asm.Assembler;
import jnr.x86asm.CPU;

abstract class StubCompiler {
   static final long errnoFunctionAddress = getErrnoSaveFunction();
   static final boolean hasPageManager = hasPageManager();
   static final boolean hasAssembler = hasAssembler();

   public static StubCompiler newCompiler(Runtime runtime) {
      if (errnoFunctionAddress != 0L && hasPageManager && hasAssembler) {
         switch (Platform.getPlatform().getCPU()) {
            case I386:
               if (Platform.getPlatform().getOS() != Platform.OS.WINDOWS) {
                  return new X86_32StubCompiler(runtime);
               }
               break;
            case X86_64:
               if (Platform.getPlatform().getOS() != Platform.OS.WINDOWS) {
                  return new X86_64StubCompiler(runtime);
               }
         }
      }

      return new DummyStubCompiler();
   }

   abstract boolean canCompile(ResultType var1, ParameterType[] var2, CallingConvention var3);

   abstract void compile(Function var1, String var2, ResultType var3, ParameterType[] var4, Class var5, Class[] var6, CallingConvention var7, boolean var8);

   abstract void attach(Class var1);

   private static long getErrnoSaveFunction() {
      try {
         return Internals.getErrnoSaveFunction();
      } catch (Throwable var1) {
         return 0L;
      }
   }

   private static boolean hasPageManager() {
      try {
         long page = PageManager.getInstance().allocatePages(1, 3);
         PageManager.getInstance().freePages(page, 1);
         return true;
      } catch (Throwable var3) {
         return false;
      }
   }

   private static boolean hasAssembler() {
      try {
         switch (Platform.getPlatform().getCPU()) {
            case I386:
               new Assembler(CPU.X86_32);
               return true;
            case X86_64:
               new Assembler(CPU.X86_64);
               return true;
            default:
               return false;
         }
      } catch (Throwable var1) {
         return false;
      }
   }

   static final class DummyStubCompiler extends StubCompiler {
      boolean canCompile(ResultType returnType, ParameterType[] parameterTypes, CallingConvention convention) {
         return false;
      }

      void compile(Function function, String name, ResultType returnType, ParameterType[] parameterTypes, Class resultClass, Class[] parameterClasses, CallingConvention convention, boolean saveErrno) {
         throw new UnsupportedOperationException("Not supported yet.");
      }

      void attach(Class clazz) {
      }
   }
}
