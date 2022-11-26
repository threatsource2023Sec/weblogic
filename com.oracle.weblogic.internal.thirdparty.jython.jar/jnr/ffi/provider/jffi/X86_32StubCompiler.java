package jnr.ffi.provider.jffi;

import com.kenai.jffi.Function;
import jnr.ffi.CallingConvention;
import jnr.ffi.NativeType;
import jnr.ffi.Runtime;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.x86asm.Asm;
import jnr.x86asm.Assembler;
import jnr.x86asm.Mem;
import jnr.x86asm.Register;

final class X86_32StubCompiler extends AbstractX86StubCompiler {
   X86_32StubCompiler(Runtime runtime) {
      super(runtime);
   }

   boolean canCompile(ResultType returnType, ParameterType[] parameterTypes, CallingConvention convention) {
      switch (returnType.getNativeType()) {
         case VOID:
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SINT:
         case UINT:
         case SLONG:
         case ULONG:
         case SLONGLONG:
         case ULONGLONG:
         case FLOAT:
         case DOUBLE:
         case ADDRESS:
            if (convention != CallingConvention.DEFAULT) {
               return false;
            } else {
               int fCount = 0;
               int iCount = 0;
               ParameterType[] var6 = parameterTypes;
               int var7 = parameterTypes.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  ParameterType t = var6[var8];
                  switch (t.getNativeType()) {
                     case SCHAR:
                     case UCHAR:
                     case SSHORT:
                     case USHORT:
                     case SINT:
                     case UINT:
                     case SLONG:
                     case ULONG:
                     case SLONGLONG:
                     case ULONGLONG:
                     case ADDRESS:
                        ++iCount;
                        break;
                     case FLOAT:
                     case DOUBLE:
                        ++fCount;
                        break;
                     default:
                        return false;
                  }
               }

               return true;
            }
         default:
            return false;
      }
   }

   void compile(Function function, String name, ResultType resultType, ParameterType[] parameterTypes, Class resultClass, Class[] parameterClasses, CallingConvention convention, boolean saveErrno) {
      int psize = 0;
      ParameterType[] var10 = parameterTypes;
      int stackadj = parameterTypes.length;

      for(int var12 = 0; var12 < stackadj; ++var12) {
         ParameterType t = var10[var12];
         psize += parameterSize(t);
      }

      int rsize = resultSize(resultType);
      stackadj = align(Math.max(psize, rsize) + 4, 16) - 4;
      Assembler a = new Assembler(Asm.X86_32);
      a.sub(Asm.esp, Asm.imm((long)stackadj));
      int i = 0;
      int srcoff = 0;

      for(int dstoff = 0; i < parameterTypes.length; ++i) {
         int srcParameterSize = parameterSize(parameterClasses[i]);
         int dstParameterSize = parameterSize(parameterTypes[i]);
         int disp = stackadj + 4 + 8 + srcoff;
         switch (parameterTypes[i].getNativeType()) {
            case SCHAR:
            case SSHORT:
               a.movsx(Asm.eax, ptr(Asm.esp, (long)disp, parameterTypes[i].getNativeType()));
               break;
            case UCHAR:
            case USHORT:
               a.movzx(Asm.eax, ptr(Asm.esp, (long)disp, parameterTypes[i].getNativeType()));
               break;
            default:
               a.mov(Asm.eax, Asm.dword_ptr(Asm.esp, (long)disp));
         }

         a.mov(Asm.dword_ptr(Asm.esp, (long)dstoff), Asm.eax);
         if (dstParameterSize > 4) {
            if (parameterTypes[i].getNativeType() == NativeType.SLONGLONG && Long.TYPE != parameterClasses[i]) {
               a.sar(Asm.eax, Asm.imm(31L));
            } else if (parameterTypes[i].getNativeType() == NativeType.ULONGLONG && Long.TYPE != parameterClasses[i]) {
               a.mov(Asm.dword_ptr(Asm.esp, (long)(dstoff + 4)), Asm.imm(0L));
            } else {
               a.mov(Asm.eax, Asm.dword_ptr(Asm.esp, (long)(disp + 4)));
            }

            a.mov(Asm.dword_ptr(Asm.esp, (long)(dstoff + 4)), Asm.eax);
         }

         dstoff += dstParameterSize;
         srcoff += srcParameterSize;
      }

      a.call(Asm.imm(function.getFunctionAddress() & 4294967295L));
      if (saveErrno) {
         int save = 0;
         switch (resultType.getNativeType()) {
            case VOID:
               break;
            case SCHAR:
            case UCHAR:
            case SSHORT:
            case USHORT:
            case SINT:
            case UINT:
            case SLONG:
            case ULONG:
            default:
               a.mov(Asm.dword_ptr(Asm.esp, (long)save), Asm.eax);
               break;
            case SLONGLONG:
            case ULONGLONG:
               a.mov(Asm.dword_ptr(Asm.esp, (long)save), Asm.eax);
               a.mov(Asm.dword_ptr(Asm.esp, (long)(save + 4)), Asm.edx);
               break;
            case FLOAT:
               a.fstp(Asm.dword_ptr(Asm.esp, (long)save));
               break;
            case DOUBLE:
               a.fstp(Asm.qword_ptr(Asm.esp, (long)save));
         }

         a.call(Asm.imm(errnoFunctionAddress & 4294967295L));
         switch (resultType.getNativeType()) {
            case VOID:
               break;
            case SCHAR:
               a.movsx(Asm.eax, Asm.byte_ptr(Asm.esp, (long)save));
               break;
            case UCHAR:
               a.movzx(Asm.eax, Asm.byte_ptr(Asm.esp, (long)save));
               break;
            case SSHORT:
               a.movsx(Asm.eax, Asm.word_ptr(Asm.esp, (long)save));
               break;
            case USHORT:
               a.movzx(Asm.eax, Asm.word_ptr(Asm.esp, (long)save));
               break;
            case SINT:
            case UINT:
            case SLONG:
            case ULONG:
            default:
               a.mov(Asm.eax, Asm.dword_ptr(Asm.esp, (long)save));
               break;
            case SLONGLONG:
            case ULONGLONG:
               a.mov(Asm.eax, Asm.dword_ptr(Asm.esp, (long)save));
               a.mov(Asm.edx, Asm.dword_ptr(Asm.esp, (long)(save + 4)));
               break;
            case FLOAT:
               a.fld(Asm.dword_ptr(Asm.esp, (long)save));
               break;
            case DOUBLE:
               a.fld(Asm.qword_ptr(Asm.esp, (long)save));
         }
      } else {
         switch (resultType.getNativeType()) {
            case SCHAR:
               a.movsx(Asm.eax, Asm.al);
               break;
            case UCHAR:
               a.movzx(Asm.eax, Asm.al);
               break;
            case SSHORT:
               a.movsx(Asm.eax, Asm.ax);
               break;
            case USHORT:
               a.movzx(Asm.eax, Asm.ax);
         }
      }

      if (Long.TYPE == resultClass) {
         switch (resultType.getNativeType()) {
            case SCHAR:
            case SSHORT:
            case SINT:
            case SLONG:
               a.mov(Asm.edx, Asm.eax);
               a.sar(Asm.edx, Asm.imm(31L));
               break;
            case UCHAR:
            case USHORT:
            case UINT:
            case ULONG:
            case ADDRESS:
               a.mov(Asm.edx, Asm.imm(0L));
            case SLONGLONG:
            case ULONGLONG:
            case FLOAT:
            case DOUBLE:
         }
      }

      a.add(Asm.esp, Asm.imm((long)stackadj));
      a.ret();
      this.stubs.add(new AbstractX86StubCompiler.Stub(name, CodegenUtils.sig(resultClass, parameterClasses), a));
   }

   static int parameterSize(ParameterType parameterType) {
      switch (parameterType.getNativeType()) {
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SINT:
         case UINT:
         case SLONG:
         case ULONG:
         case FLOAT:
         case ADDRESS:
            return 4;
         case SLONGLONG:
         case ULONGLONG:
         case DOUBLE:
            return 8;
         default:
            throw new IllegalArgumentException("invalid parameter type" + parameterType);
      }
   }

   static int parameterSize(Class t) {
      if (Byte.TYPE != t && Short.TYPE != t && !(Character.TYPE == t | Integer.TYPE == t) && Float.TYPE != t) {
         if (Long.TYPE != t && Double.TYPE != t) {
            throw new IllegalArgumentException("invalid parameter type" + t);
         } else {
            return 8;
         }
      } else {
         return 4;
      }
   }

   static int resultSize(ResultType resultType) {
      switch (resultType.getNativeType()) {
         case VOID:
            return 0;
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SINT:
         case UINT:
         case SLONG:
         case ULONG:
         case ADDRESS:
            return 4;
         case SLONGLONG:
         case ULONGLONG:
            return 8;
         case FLOAT:
         case DOUBLE:
            return 16;
         default:
            throw new IllegalArgumentException("invalid return type " + resultType);
      }
   }

   static Mem ptr(Register base, long disp, NativeType nativeType) {
      switch (nativeType) {
         case SCHAR:
         case UCHAR:
            return Asm.byte_ptr(base, disp);
         case SSHORT:
         case USHORT:
            return Asm.word_ptr(base, disp);
         default:
            return Asm.dword_ptr(base, disp);
      }
   }
}
