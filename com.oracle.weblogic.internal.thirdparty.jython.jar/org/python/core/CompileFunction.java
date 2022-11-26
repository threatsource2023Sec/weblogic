package org.python.core;

import org.python.antlr.base.mod;

@Untraversable
class CompileFunction extends PyBuiltinFunction {
   CompileFunction() {
      super("compile", "compile(source, filename, mode[, flags[, dont_inherit]]) -> code object\n\nCompile the source string (a Python module, statement or expression)\ninto a code object that can be executed by the exec statement or eval().\nThe filename will be used for run-time error messages.\nThe mode must be 'exec' to compile a module, 'single' to compile a\nsingle (interactive) statement, or 'eval' to compile an expression.\nThe flags argument, if present, controls which future statements influence\nthe compilation of the code.\nThe dont_inherit argument, if non-zero, stops the compilation inheriting\nthe effects of any future statements in effect in the code calling\ncompile; if absent or zero these statements do influence the compilation,\nin addition to any features explicitly specified.");
   }

   public PyObject __call__(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("compile", args, kwds, new String[]{"source", "filename", "mode", "flags", "dont_inherit"}, 3);
      PyObject source = ap.getPyObject(0);
      String filename = Py.fileSystemDecode(ap.getPyObject(1));
      String mode = ap.getString(2);
      int flags = ap.getInt(3, 0);
      boolean dont_inherit = ap.getPyObject(4, Py.False).__nonzero__();
      return compile(source, filename, mode, flags, dont_inherit);
   }

   public static PyObject compile(PyObject source, String filename, String mode) {
      return compile(source, filename, mode, 0, false);
   }

   public static PyObject compile(PyObject source, String filename, String mode, int flags, boolean dont_inherit) {
      CompilerFlags cflags = Py.getCompilerFlags(flags, dont_inherit);
      CompileMode kind = CompileMode.getMode(mode);
      return compile(source, filename, kind, cflags, dont_inherit);
   }

   public static PyObject compile(PyObject source, String filename, CompileMode kind, CompilerFlags cflags, boolean dont_inherit) {
      cflags = Py.getCompilerFlags(cflags, dont_inherit);
      mod ast = py2node(source);
      if (ast == null) {
         if (!(source instanceof PyString)) {
            throw Py.TypeError("expected a readable buffer object");
         }

         cflags.source_is_utf8 = source instanceof PyUnicode;
         String data = source.toString();
         if (data.contains("\u0000")) {
            throw Py.TypeError("compile() expected string without null bytes");
         }

         if (cflags != null && cflags.dont_imply_dedent) {
            data = data + "\n";
         } else {
            data = data + "\n\n";
         }

         ast = ParserFacade.parse(data, kind, filename, cflags);
      }

      return (PyObject)(cflags.only_ast ? Py.java2py(ast) : Py.compile_flags(ast, filename, kind, cflags));
   }

   private static mod py2node(PyObject obj) {
      Object node = obj.__tojava__(mod.class);
      return node == Py.NoConversion ? null : (mod)node;
   }
}
