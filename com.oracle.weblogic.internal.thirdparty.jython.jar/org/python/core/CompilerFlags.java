package org.python.core;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import org.python.Version;

public class CompilerFlags implements Serializable {
   public static final int PyCF_SOURCE_IS_UTF8 = 256;
   public static final int PyCF_DONT_IMPLY_DEDENT = 512;
   public static final int PyCF_ONLY_AST = 1024;
   public boolean only_ast;
   public boolean dont_imply_dedent;
   public boolean source_is_utf8;
   public String encoding;
   private final Set flags = Version.getDefaultCodeFlags();
   private static final int CO_ALL_FEATURES;

   public CompilerFlags() {
   }

   public CompilerFlags(int co_flags) {
      Iterator var2 = CodeFlag.parse(co_flags).iterator();

      while(var2.hasNext()) {
         CodeFlag flag = (CodeFlag)var2.next();
         this.setFlag(flag);
      }

      this.only_ast = this.isEnabled(co_flags, 1024);
      this.dont_imply_dedent = this.isEnabled(co_flags, 512);
      this.source_is_utf8 = this.isEnabled(co_flags, 256);
   }

   private boolean isEnabled(int co_flags, int codeConstant) {
      return (co_flags & codeConstant) != 0;
   }

   public int toBits() {
      int bits = (this.only_ast ? 1024 : 0) | (this.dont_imply_dedent ? 512 : 0) | (this.source_is_utf8 ? 256 : 0);

      CodeFlag flag;
      for(Iterator var2 = this.flags.iterator(); var2.hasNext(); bits |= flag.flag) {
         flag = (CodeFlag)var2.next();
      }

      return bits;
   }

   public void setFlag(CodeFlag flag) {
      this.flags.add(flag);
   }

   public boolean isFlagSet(CodeFlag flag) {
      return this.flags.contains(flag);
   }

   public String toString() {
      return String.format("CompilerFlags[division=%s nested_scopes=%s generators=%s with_statement=%s absolute_import=%s only_ast=%s dont_imply_dedent=%s  source_is_utf8=%s]", this.isFlagSet(CodeFlag.CO_FUTURE_DIVISION), this.isFlagSet(CodeFlag.CO_NESTED), this.isFlagSet(CodeFlag.CO_GENERATOR_ALLOWED), this.isFlagSet(CodeFlag.CO_FUTURE_WITH_STATEMENT), this.isFlagSet(CodeFlag.CO_FUTURE_ABSOLUTE_IMPORT), this.isFlagSet(CodeFlag.CO_FUTURE_PRINT_FUNCTION), this.isFlagSet(CodeFlag.CO_FUTURE_UNICODE_LITERALS), this.only_ast, this.dont_imply_dedent, this.source_is_utf8);
   }

   public static CompilerFlags getCompilerFlags() {
      return getCompilerFlags(0, (PyFrame)null);
   }

   public static CompilerFlags getCompilerFlags(int flags, PyFrame frame) {
      if ((flags & ~CO_ALL_FEATURES) != 0) {
         throw Py.ValueError("compile(): unrecognised flags");
      } else {
         return getCompilerFlags(new CompilerFlags(flags), frame);
      }
   }

   public static CompilerFlags getCompilerFlags(CompilerFlags flags, PyFrame frame) {
      return frame != null && frame.f_code != null ? frame.f_code.co_flags.combine(flags) : flags;
   }

   public CompilerFlags combine(CompilerFlags flags) {
      return new CompilerFlags(this.toBits() | flags.toBits());
   }

   public CompilerFlags combine(int flags) {
      return new CompilerFlags(this.toBits() | flags);
   }

   static {
      CO_ALL_FEATURES = 1792 | CodeFlag.CO_NESTED.flag | CodeFlag.CO_GENERATOR_ALLOWED.flag | CodeFlag.CO_FUTURE_DIVISION.flag | CodeFlag.CO_FUTURE_ABSOLUTE_IMPORT.flag | CodeFlag.CO_FUTURE_WITH_STATEMENT.flag | CodeFlag.CO_FUTURE_PRINT_FUNCTION.flag | CodeFlag.CO_FUTURE_UNICODE_LITERALS.flag;
   }
}
