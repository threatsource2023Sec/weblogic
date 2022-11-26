package jnr.ffi.provider.jffi;

import com.kenai.jffi.Function;
import jnr.ffi.CallingConvention;
import jnr.ffi.Runtime;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.x86asm.Asm;
import jnr.x86asm.Assembler;
import jnr.x86asm.Register;

final class X86_64StubCompiler extends AbstractX86StubCompiler {
   static final Register[] srcRegisters8;
   static final Register[] srcRegisters16;
   static final Register[] srcRegisters32;
   static final Register[] srcRegisters64;
   static final Register[] dstRegisters32;
   static final Register[] dstRegisters64;

   X86_64StubCompiler(Runtime runtime) {
      super(runtime);
   }

   boolean canCompile(ResultType returnType, ParameterType[] parameterTypes, CallingConvention convention) {
      if (convention != CallingConvention.DEFAULT) {
         return false;
      } else {
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
               int fCount = 0;
               int iCount = 0;
               ParameterType[] var6 = parameterTypes;
               int var7 = parameterTypes.length;
               int var8 = 0;

               for(; var8 < var7; ++var8) {
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

               return iCount <= 6 && fCount <= 8;
            default:
               return false;
         }
      }
   }

   final void compile(Function function, String name, ResultType resultType, ParameterType[] parameterTypes, Class resultClass, Class[] parameterClasses, CallingConvention convention, boolean saveErrno) {
      Assembler a = new Assembler(Asm.X86_64);
      int iCount = iCount(parameterTypes);
      int fCount = fCount(parameterTypes);
      boolean canJumpToTarget = !saveErrno & iCount <= 6 & fCount <= 8;
      switch (resultType.getNativeType()) {
         case VOID:
            break;
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SLONG:
         case ULONG:
         default:
            canJumpToTarget = false;
            break;
         case SINT:
         case UINT:
            canJumpToTarget &= Integer.TYPE == resultClass;
            break;
         case SLONGLONG:
         case ULONGLONG:
            canJumpToTarget &= Long.TYPE == resultClass;
            break;
         case FLOAT:
            canJumpToTarget &= Float.TYPE == resultClass;
            break;
         case DOUBLE:
            canJumpToTarget &= Double.TYPE == resultClass;
      }

      int space;
      for(space = 0; space < Math.min(iCount, 4); ++space) {
         switch (parameterTypes[space].getNativeType()) {
            case SCHAR:
               a.movsx(dstRegisters64[space], srcRegisters8[space]);
               break;
            case UCHAR:
               a.movzx(dstRegisters64[space], srcRegisters8[space]);
               break;
            case SSHORT:
               a.movsx(dstRegisters64[space], srcRegisters16[space]);
               break;
            case USHORT:
               a.movzx(dstRegisters64[space], srcRegisters16[space]);
               break;
            case SINT:
               a.movsxd(dstRegisters64[space], srcRegisters32[space]);
               break;
            case UINT:
               a.mov(dstRegisters32[space], srcRegisters32[space]);
               break;
            default:
               a.mov(dstRegisters64[space], srcRegisters64[space]);
         }
      }

      if (iCount > 6) {
         throw new IllegalArgumentException("integer argument count > 6");
      } else {
         for(space = 4; space < iCount; ++space) {
            int disp = 8 + (4 - space) * 8;
            switch (parameterTypes[space].getNativeType()) {
               case SCHAR:
                  a.movsx(dstRegisters64[space], Asm.byte_ptr(Asm.rsp, (long)disp));
                  break;
               case UCHAR:
                  a.movzx(dstRegisters64[space], Asm.byte_ptr(Asm.rsp, (long)disp));
                  break;
               case SSHORT:
                  a.movsx(dstRegisters64[space], Asm.word_ptr(Asm.rsp, (long)disp));
                  break;
               case USHORT:
                  a.movzx(dstRegisters64[space], Asm.word_ptr(Asm.rsp, (long)disp));
                  break;
               case SINT:
                  a.movsxd(dstRegisters64[space], Asm.dword_ptr(Asm.rsp, (long)disp));
                  break;
               case UINT:
                  a.mov(dstRegisters32[space], Asm.dword_ptr(Asm.rsp, (long)disp));
                  break;
               default:
                  a.mov(dstRegisters64[space], Asm.qword_ptr(Asm.rsp, (long)disp));
            }
         }

         if (fCount > 8) {
            throw new IllegalArgumentException("float argument count > 8");
         } else if (canJumpToTarget) {
            a.jmp(Asm.imm(function.getFunctionAddress()));
            this.stubs.add(new AbstractX86StubCompiler.Stub(name, CodegenUtils.sig(resultClass, parameterClasses), a));
         } else {
            space = resultClass != Float.TYPE && resultClass != Double.TYPE ? 8 : 24;
            a.sub(Asm.rsp, Asm.imm((long)space));
            a.mov(Asm.rax, Asm.imm(0L));
            a.call(Asm.imm(function.getFunctionAddress()));
            if (saveErrno) {
               switch (resultType.getNativeType()) {
                  case VOID:
                     break;
                  case FLOAT:
                     a.movss(Asm.dword_ptr(Asm.rsp, 0L), Asm.xmm0);
                     break;
                  case DOUBLE:
                     a.movsd(Asm.qword_ptr(Asm.rsp, 0L), Asm.xmm0);
                     break;
                  default:
                     a.mov(Asm.qword_ptr(Asm.rsp, 0L), Asm.rax);
               }

               a.call(Asm.imm(errnoFunctionAddress));
               switch (resultType.getNativeType()) {
                  case VOID:
                     break;
                  case SCHAR:
                     a.movsx(Asm.rax, Asm.byte_ptr(Asm.rsp, 0L));
                     break;
                  case UCHAR:
                     a.movzx(Asm.rax, Asm.byte_ptr(Asm.rsp, 0L));
                     break;
                  case SSHORT:
                     a.movsx(Asm.rax, Asm.word_ptr(Asm.rsp, 0L));
                     break;
                  case USHORT:
                     a.movzx(Asm.rax, Asm.word_ptr(Asm.rsp, 0L));
                     break;
                  case SINT:
                     a.movsxd(Asm.rax, Asm.dword_ptr(Asm.rsp, 0L));
                     break;
                  case UINT:
                     a.mov(Asm.eax, Asm.dword_ptr(Asm.rsp, 0L));
                     break;
                  case SLONG:
                  case ULONG:
                  case SLONGLONG:
                  case ULONGLONG:
                  default:
                     a.mov(Asm.rax, Asm.qword_ptr(Asm.rsp, 0L));
                     break;
                  case FLOAT:
                     a.movss(Asm.xmm0, Asm.dword_ptr(Asm.rsp, 0L));
                     break;
                  case DOUBLE:
                     a.movsd(Asm.xmm0, Asm.qword_ptr(Asm.rsp, 0L));
               }
            } else {
               switch (resultType.getNativeType()) {
                  case SCHAR:
                     a.movsx(Asm.rax, Asm.al);
                     break;
                  case UCHAR:
                     a.movzx(Asm.rax, Asm.al);
                     break;
                  case SSHORT:
                     a.movsx(Asm.rax, Asm.ax);
                     break;
                  case USHORT:
                     a.movzx(Asm.rax, Asm.ax);
                     break;
                  case SINT:
                     if (Long.TYPE == resultClass) {
                        a.movsxd(Asm.rax, Asm.eax);
                     }
                     break;
                  case UINT:
                     if (Long.TYPE == resultClass) {
                        a.mov(Asm.eax, Asm.eax);
                     }
               }
            }

            a.add(Asm.rsp, Asm.imm((long)space));
            a.ret();
            this.stubs.add(new AbstractX86StubCompiler.Stub(name, CodegenUtils.sig(resultClass, parameterClasses), a));
         }
      }
   }

   static int fCount(ParameterType[] parameterTypes) {
      int fCount = 0;
      ParameterType[] var2 = parameterTypes;
      int var3 = parameterTypes.length;
      int var4 = 0;

      while(var4 < var3) {
         ParameterType t = var2[var4];
         switch (t.getNativeType()) {
            case FLOAT:
            case DOUBLE:
               ++fCount;
            default:
               ++var4;
         }
      }

      return fCount;
   }

   static int iCount(ParameterType[] parameterTypes) {
      int iCount = 0;
      ParameterType[] var2 = parameterTypes;
      int var3 = parameterTypes.length;
      int var4 = 0;

      while(var4 < var3) {
         ParameterType t = var2[var4];
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
            case FLOAT:
            case DOUBLE:
            default:
               ++var4;
         }
      }

      return iCount;
   }

   static {
      srcRegisters8 = new Register[]{Asm.dl, Asm.cl, Asm.r8b, Asm.r9b};
      srcRegisters16 = new Register[]{Asm.dx, Asm.cx, Asm.r8w, Asm.r9w};
      srcRegisters32 = new Register[]{Asm.edx, Asm.ecx, Register.gpr(40), Register.gpr(41)};
      srcRegisters64 = new Register[]{Asm.rdx, Asm.rcx, Asm.r8, Asm.r9};
      dstRegisters32 = new Register[]{Asm.edi, Asm.esi, Asm.edx, Asm.ecx, Register.gpr(40), Register.gpr(41)};
      dstRegisters64 = new Register[]{Asm.rdi, Asm.rsi, Asm.rdx, Asm.rcx, Asm.r8, Asm.r9};
   }
}
