package jnr.ffi.provider.jffi;

import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.DefaultTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.TypeMapper;
import jnr.ffi.types.intptr_t;
import jnr.ffi.types.size_t;
import jnr.ffi.types.u_int64_t;
import jnr.ffi.types.u_int8_t;

class X86Disassembler {
   private final UDis86 udis86;
   final Pointer ud;

   static UDis86 loadUDis86() {
      DefaultTypeMapper typeMapper = new DefaultTypeMapper();
      typeMapper.put(X86Disassembler.class, (ToNativeConverter)(new X86DisassemblerConverter()));
      return (UDis86)jnr.ffi.LibraryLoader.create(UDis86.class).library("udis86").search("/usr/local/lib").search("/opt/local/lib").search("/usr/lib").mapper((TypeMapper)typeMapper).load();
   }

   static boolean isAvailable() {
      try {
         return X86Disassembler.SingletonHolder.INSTANCE != null;
      } catch (Throwable var1) {
         return false;
      }
   }

   static X86Disassembler create() {
      return new X86Disassembler(X86Disassembler.SingletonHolder.INSTANCE);
   }

   private X86Disassembler(UDis86 udis86) {
      this.udis86 = udis86;
      this.ud = Memory.allocateDirect(Runtime.getRuntime(udis86), 1024, true);
      this.udis86.ud_init(this.ud);
   }

   public void setSyntax(Syntax syntax) {
      this.udis86.ud_set_syntax(this, syntax == X86Disassembler.Syntax.INTEL ? X86Disassembler.SingletonHolder.intel : X86Disassembler.SingletonHolder.att);
   }

   public void setMode(Mode mode) {
      this.udis86.ud_set_mode(this, mode == X86Disassembler.Mode.I386 ? 32 : 64);
   }

   public void setInputBuffer(Pointer buffer, int size) {
      this.udis86.ud_set_input_buffer(this, buffer, (long)size);
   }

   public boolean disassemble() {
      return this.udis86.ud_disassemble(this) != 0;
   }

   public String insn() {
      return this.udis86.ud_insn_asm(this);
   }

   public long offset() {
      return this.udis86.ud_insn_off(this);
   }

   public String hex() {
      return this.udis86.ud_insn_hex(this);
   }

   @NoX86
   @NoTrace
   public interface UDis86 {
      void ud_init(Pointer var1);

      void ud_set_mode(X86Disassembler var1, @u_int8_t int var2);

      void ud_set_pc(X86Disassembler var1, @u_int64_t int var2);

      void ud_set_input_buffer(X86Disassembler var1, Pointer var2, @size_t long var3);

      void ud_set_vendor(X86Disassembler var1, int var2);

      void ud_set_syntax(X86Disassembler var1, @intptr_t long var2);

      void ud_input_skip(X86Disassembler var1, @size_t long var2);

      int ud_input_end(X86Disassembler var1);

      int ud_decode(X86Disassembler var1);

      int ud_disassemble(X86Disassembler var1);

      String ud_insn_asm(X86Disassembler var1);

      @intptr_t
      long ud_insn_ptr(X86Disassembler var1);

      @u_int64_t
      long ud_insn_off(X86Disassembler var1);

      String ud_insn_hex(X86Disassembler var1);

      int ud_insn_len(X86Disassembler var1);
   }

   @ToNativeConverter.NoContext
   public static final class X86DisassemblerConverter implements ToNativeConverter {
      public Pointer toNative(X86Disassembler value, ToNativeContext context) {
         return value.ud;
      }

      public Class nativeType() {
         return Pointer.class;
      }
   }

   static final class SingletonHolder {
      static final UDis86 INSTANCE = X86Disassembler.loadUDis86();
      static final long intel;
      static final long att;

      static {
         intel = ((AbstractAsmLibraryInterface)INSTANCE).getLibrary().findSymbolAddress("ud_translate_intel");
         att = ((AbstractAsmLibraryInterface)INSTANCE).getLibrary().findSymbolAddress("ud_translate_att");
      }
   }

   public static enum Mode {
      I386,
      X86_64;
   }

   public static enum Syntax {
      INTEL,
      ATT;
   }
}
