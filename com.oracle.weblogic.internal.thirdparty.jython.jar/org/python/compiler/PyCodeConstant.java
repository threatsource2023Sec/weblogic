package org.python.compiler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.python.antlr.ast.Suite;
import org.python.antlr.base.mod;
import org.python.core.CodeFlag;
import org.python.core.CompilerFlags;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFunctionTable;
import org.python.objectweb.asm.Opcodes;
import org.python.util.CodegenUtils;

class PyCodeConstant extends Constant implements ClassConstants, Opcodes {
   final String co_name;
   final int argcount;
   final List names;
   final int id;
   final int co_firstlineno;
   final boolean arglist;
   final boolean keywordlist;
   final String fname;
   final List cellvars;
   final List freevars;
   final int jy_npurecell;
   final int moreflags;

   PyCodeConstant(mod tree, String name, boolean fast_locals, String className, boolean classBody, boolean printResults, int firstlineno, ScopeInfo scope, CompilerFlags cflags, Module module) throws Exception {
      this.co_name = name;
      this.co_firstlineno = firstlineno;
      this.module = module;
      int _moreflags = 0;
      if (scope.ac != null) {
         this.arglist = scope.ac.arglist;
         this.keywordlist = scope.ac.keywordlist;
         this.argcount = scope.ac.names.size();
         if (scope.ac.init_code.size() > 0) {
            scope.ac.appendInitCode((Suite)tree);
         }
      } else {
         this.arglist = false;
         this.keywordlist = false;
         this.argcount = 0;
      }

      this.id = module.codes.size();
      if (isJavaIdentifier(name)) {
         this.fname = name + "$" + this.id;
      } else {
         this.fname = "f$" + this.id;
      }

      this.name = this.fname;
      if (!classBody) {
         this.names = this.toNameAr(scope.names, false);
      } else {
         this.names = null;
      }

      this.cellvars = this.toNameAr(scope.cellvars, true);
      this.freevars = this.toNameAr(scope.freevars, true);
      this.jy_npurecell = scope.jy_npurecell;
      if (CodeCompiler.checkOptimizeGlobals(fast_locals, scope)) {
         _moreflags |= CodeFlag.CO_OPTIMIZED.flag;
      }

      if (scope.generator) {
         _moreflags |= CodeFlag.CO_GENERATOR.flag;
      }

      if (cflags != null) {
         if (cflags.isFlagSet(CodeFlag.CO_GENERATOR_ALLOWED)) {
            _moreflags |= CodeFlag.CO_GENERATOR_ALLOWED.flag;
         }

         if (cflags.isFlagSet(CodeFlag.CO_FUTURE_DIVISION)) {
            _moreflags |= CodeFlag.CO_FUTURE_DIVISION.flag;
         }
      }

      this.moreflags = _moreflags;
   }

   private List toNameAr(List names, boolean nullok) {
      int sz = names.size();
      if (sz == 0 && nullok) {
         return null;
      } else {
         List nameArray = new ArrayList();
         nameArray.addAll(names);
         return nameArray;
      }
   }

   public static boolean isJavaIdentifier(String s) {
      char[] chars = s.toCharArray();
      if (chars.length == 0) {
         return false;
      } else if (!Character.isJavaIdentifierStart(chars[0])) {
         return false;
      } else {
         for(int i = 1; i < chars.length; ++i) {
            if (!Character.isJavaIdentifierPart(chars[i])) {
               return false;
            }
         }

         return true;
      }
   }

   void get(Code c) throws IOException {
      c.getstatic(this.module.classfile.name, this.name, CodegenUtils.ci(PyCode.class));
   }

   void put(Code c) throws IOException {
      this.module.classfile.addField(this.name, CodegenUtils.ci(PyCode.class), access);
      c.iconst(this.argcount);
      int nameArray;
      if (this.names != null) {
         nameArray = CodeCompiler.makeStrings(c, this.names);
      } else {
         nameArray = CodeCompiler.makeStrings(c, (Collection)null);
      }

      c.aload(nameArray);
      c.freeLocal(nameArray);
      c.aload(1);
      c.ldc(this.co_name);
      c.iconst(this.co_firstlineno);
      c.iconst(this.arglist ? 1 : 0);
      c.iconst(this.keywordlist ? 1 : 0);
      c.getstatic(this.module.classfile.name, "self", "L" + this.module.classfile.name + ";");
      c.iconst(this.id);
      int strArray;
      if (this.cellvars != null) {
         strArray = CodeCompiler.makeStrings(c, this.cellvars);
         c.aload(strArray);
         c.freeLocal(strArray);
      } else {
         c.aconst_null();
      }

      if (this.freevars != null) {
         strArray = CodeCompiler.makeStrings(c, this.freevars);
         c.aload(strArray);
         c.freeLocal(strArray);
      } else {
         c.aconst_null();
      }

      c.iconst(this.jy_npurecell);
      c.iconst(this.moreflags);
      c.invokestatic(CodegenUtils.p(Py.class), "newCode", CodegenUtils.sig(PyCode.class, Integer.TYPE, String[].class, String.class, String.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE, PyFunctionTable.class, Integer.TYPE, String[].class, String[].class, Integer.TYPE, Integer.TYPE));
      c.putstatic(this.module.classfile.name, this.name, CodegenUtils.ci(PyCode.class));
   }
}
