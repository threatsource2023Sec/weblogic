package org.python.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PyBytecode extends PyBaseCode implements Traverseproc {
   public static boolean defaultDebug = false;
   private static boolean debug;
   private static PyObject dis;
   private static PyObject opname;
   private int count;
   private int maxCount;
   public static final int CO_MAXBLOCKS = 20;
   public final byte[] co_code;
   public final PyObject[] co_consts;
   public final String[] co_names;
   public final int co_stacksize;
   public final byte[] co_lnotab;
   private static final int CALL_FLAG_VAR = 1;
   private static final int CALL_FLAG_KW = 2;
   private static final String[] __members__ = new String[]{"co_name", "co_argcount", "co_varnames", "co_filename", "co_firstlineno", "co_flags", "co_cellvars", "co_freevars", "co_nlocals", "co_code", "co_consts", "co_names", "co_lnotab", "co_stacksize"};

   private static synchronized PyObject get_dis() {
      if (dis == null) {
         dis = __builtin__.__import__("dis");
      }

      return dis;
   }

   private static synchronized PyObject get_opname() {
      if (opname == null) {
         opname = get_dis().__getattr__("opname");
      }

      return opname;
   }

   public static void _allDebug(boolean setting) {
      defaultDebug = setting;
   }

   public PyObject _debug(int maxCount) {
      debug = maxCount > 0;
      this.maxCount = maxCount;
      return Py.None;
   }

   public PyBytecode(int argcount, int nlocals, int stacksize, int flags, String codestring, PyObject[] constants, String[] names, String[] varnames, String filename, String name, int firstlineno, String lnotab) {
      this(argcount, nlocals, stacksize, flags, codestring, constants, names, varnames, filename, name, firstlineno, lnotab, (String[])null, (String[])null);
   }

   public PyBytecode(int argcount, int nlocals, int stacksize, int flags, String codestring, PyObject[] constants, String[] names, String[] varnames, String filename, String name, int firstlineno, String lnotab, String[] cellvars, String[] freevars) {
      this.count = 0;
      this.maxCount = -1;
      debug = defaultDebug;
      if (argcount < 0) {
         throw Py.ValueError("code: argcount must not be negative");
      } else if (nlocals < 0) {
         throw Py.ValueError("code: nlocals must not be negative");
      } else {
         this.co_argcount = this.nargs = argcount;
         this.co_varnames = varnames;
         this.co_nlocals = nlocals;
         this.co_filename = filename;
         this.co_firstlineno = firstlineno;
         this.co_cellvars = cellvars;
         this.co_freevars = freevars;
         this.co_name = name;
         this.co_flags = new CompilerFlags(flags);
         this.varargs = this.co_flags.isFlagSet(CodeFlag.CO_VARARGS);
         this.varkwargs = this.co_flags.isFlagSet(CodeFlag.CO_VARKEYWORDS);
         this.co_stacksize = stacksize;
         this.co_consts = constants;
         this.co_names = names;
         this.co_code = getBytes(codestring);
         this.co_lnotab = getBytes(lnotab);
      }
   }

   public PyObject __dir__() {
      PyString[] members = new PyString[__members__.length];

      for(int i = 0; i < __members__.length; ++i) {
         members[i] = new PyString(__members__[i]);
      }

      return new PyList(members);
   }

   private void throwReadonly(String name) {
      for(int i = 0; i < __members__.length; ++i) {
         if (__members__[i] == name) {
            throw Py.TypeError("readonly attribute");
         }
      }

      throw Py.AttributeError(name);
   }

   public void __setattr__(String name, PyObject value) {
      this.throwReadonly(name);
   }

   public void __delattr__(String name) {
      this.throwReadonly(name);
   }

   private static PyTuple toPyStringTuple(String[] ar) {
      if (ar == null) {
         return Py.EmptyTuple;
      } else {
         int sz = ar.length;
         PyString[] pystr = new PyString[sz];

         for(int i = 0; i < sz; ++i) {
            pystr[i] = new PyString(ar[i]);
         }

         return new PyTuple(pystr);
      }
   }

   public PyObject __findattr_ex__(String name) {
      if (name == "co_varnames") {
         return toPyStringTuple(this.co_varnames);
      } else if (name == "co_cellvars") {
         return toPyStringTuple(this.co_cellvars);
      } else if (name == "co_freevars") {
         return toPyStringTuple(this.co_freevars);
      } else if (name == "co_filename") {
         return Py.fileSystemEncode(this.co_filename);
      } else if (name == "co_name") {
         return new PyString(this.co_name);
      } else if (name == "co_code") {
         return new PyString(getString(this.co_code));
      } else if (name == "co_lnotab") {
         return new PyString(getString(this.co_lnotab));
      } else if (name == "co_consts") {
         return new PyTuple(this.co_consts);
      } else {
         return (PyObject)(name == "co_flags" ? Py.newInteger(this.co_flags.toBits()) : super.__findattr_ex__(name));
      }
   }

   private static String stringify_blocks(PyFrame f) {
      if (f.f_exits != null && f.f_lineno != 0) {
         StringBuilder buf = new StringBuilder("[");

         for(int i = 0; i < f.f_exits.length; ++i) {
            buf.append(f.f_exits[i].toString());
            if (i < f.f_exits.length - 1) {
               buf.append(", ");
            }
         }

         buf.append("]");
         return buf.toString();
      } else {
         return "[]";
      }
   }

   private void print_debug(int count, int next_instr, int line, int opcode, int oparg, PyStack stack, PyFrame f) {
      if (debug) {
         System.err.println(this.co_name + " " + line + ":" + count + "," + f.f_lasti + "> " + opcode + " " + get_opname().__getitem__(Py.newInteger(opcode)) + (opcode >= 90 ? " " + oparg : "") + ", stack: " + stack.toString() + ", blocks: " + stringify_blocks(f));
      }

   }

   private static PyTryBlock popBlock(PyFrame f) {
      return (PyTryBlock)((PyTryBlock)((PyList)f.f_exits[0]).pop());
   }

   private static void pushBlock(PyFrame f, PyTryBlock block) {
      if (f.f_exits == null) {
         f.f_exits = new PyObject[1];
         f.f_exits[0] = new PyList();
      }

      ((PyList)f.f_exits[0]).append(block);
   }

   private boolean blocksLeft(PyFrame f) {
      return f.f_exits != null ? ((PyList)f.f_exits[0]).__nonzero__() : false;
   }

   protected PyObject interpret(PyFrame f, ThreadState ts) {
      PyStack stack = new PyStack(this.co_stacksize);
      int next_instr = true;
      int oparg = 0;
      Why why = PyBytecode.Why.NOT;
      PyObject retval = null;
      LineCache lineCache = null;
      int last_line = -1;
      int line = 0;
      int na;
      int i;
      if (debug) {
         System.err.println(this.co_name + ":" + f.f_lasti + "/" + this.co_code.length + ", cells:" + Arrays.toString(this.co_cellvars) + ", free:" + Arrays.toString(this.co_freevars));
         int i = 0;
         String[] var12 = this.co_cellvars;
         na = var12.length;

         String freevar;
         for(i = 0; i < na; ++i) {
            freevar = var12[i];
            System.err.println(freevar + " = " + f.f_env[i++]);
         }

         var12 = this.co_freevars;
         na = var12.length;

         for(i = 0; i < na; ++i) {
            freevar = var12[i];
            System.err.println(freevar + " = " + f.f_env[i++]);
         }

         get_dis().invoke("disassemble", (PyObject)this);
      }

      if (f.f_lasti >= this.co_code.length) {
         throw Py.SystemError("");
      } else {
         int next_instr = f.f_lasti;
         boolean checkGeneratorInput = false;
         PyObject w;
         if (f.f_savedlocals != null) {
            for(int i = 0; i < f.f_savedlocals.length; ++i) {
               w = (PyObject)((PyObject)f.f_savedlocals[i]);
               stack.push(w);
            }

            checkGeneratorInput = true;
            f.f_savedlocals = null;
         }

         while(!debug || this.maxCount == -1 || this.count < this.maxCount) {
            if (f.tracefunc != null || debug) {
               if (lineCache == null) {
                  lineCache = new LineCache();
                  if (debug) {
                     System.err.println("LineCache: " + lineCache.toString());
                  }
               }

               line = lineCache.getline(next_instr);
               if (line != last_line) {
                  f.setline(line);
               }
            }

            PyException exc;
            PyTryBlock b;
            try {
               if (checkGeneratorInput) {
                  checkGeneratorInput = false;
                  Object generatorInput = f.getGeneratorInput();
                  if (generatorInput instanceof PyException) {
                     throw (PyException)generatorInput;
                  }

                  stack.push((PyObject)generatorInput);
               }

               int opcode = getUnsigned(this.co_code, next_instr);
               if (opcode >= 'Z') {
                  next_instr += 2;
                  oparg = (getUnsigned(this.co_code, next_instr) << 8) + getUnsigned(this.co_code, next_instr - 1);
               }

               this.print_debug(this.count, next_instr, line, opcode, oparg, stack, f);
               ++this.count;
               ++next_instr;
               f.f_lasti = next_instr;
               String[] var17;
               int var18;
               int var19;
               String match;
               PyObject v;
               PyCell cell;
               PyObject u;
               PyObject v;
               String name;
               String name;
               boolean matched;
               PyObject[] closure_cells;
               PyObject w;
               PyCode code;
               PyFunction x;
               label564:
               switch (opcode) {
                  case '\u0001':
                     stack.pop();
                     break;
                  case '\u0002':
                     stack.rot2();
                     break;
                  case '\u0003':
                     stack.rot3();
                     break;
                  case '\u0004':
                     stack.dup();
                     break;
                  case '\u0005':
                     stack.rot4();
                     break;
                  case '\u0006':
                  case '\u0007':
                  case '\b':
                  case '\u000e':
                  case '\u0010':
                  case '\u0011':
                  case '\u0012':
                  case '"':
                  case '#':
                  case '$':
                  case '%':
                  case '&':
                  case '\'':
                  case ',':
                  case '-':
                  case '.':
                  case '/':
                  case '0':
                  case '1':
                  case 'E':
                  case 'u':
                  case 'v':
                  case '{':
                  case '\u007f':
                  case '\u0080':
                  case '\u0081':
                  case '\u008a':
                  case '\u008b':
                  case '\u0090':
                  default:
                     Py.print(Py.getSystemState().stderr, Py.newString(String.format("XXX lineno: %d, opcode: %d\n", f.f_lasti, Integer.valueOf(opcode))));
                     throw Py.SystemError("unknown opcode");
                  case '\t':
                     break;
                  case '\n':
                     stack.push(stack.pop().__pos__());
                     break;
                  case '\u000b':
                     stack.push(stack.pop().__neg__());
                     break;
                  case '\f':
                     stack.push(stack.pop().__not__());
                     break;
                  case '\r':
                     stack.push(stack.pop().__repr__());
                     break;
                  case '\u000f':
                     stack.push(stack.pop().__invert__());
                     break;
                  case '\u0013':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._pow(v));
                     break;
                  case '\u0014':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._mul(v));
                     break;
                  case '\u0015':
                     v = stack.pop();
                     w = stack.pop();
                     if (!this.co_flags.isFlagSet(CodeFlag.CO_FUTURE_DIVISION)) {
                        stack.push(w._div(v));
                     } else {
                        stack.push(w._truediv(v));
                     }
                     break;
                  case '\u0016':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._mod(v));
                     break;
                  case '\u0017':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._add(v));
                     break;
                  case '\u0018':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._sub(v));
                     break;
                  case '\u0019':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w.__getitem__(v));
                     break;
                  case '\u001a':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._floordiv(v));
                     break;
                  case '\u001b':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._truediv(v));
                     break;
                  case '\u001c':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._ifloordiv(v));
                     break;
                  case '\u001d':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._itruediv(v));
                     break;
                  case '\u001e':
                  case '\u001f':
                  case ' ':
                  case '!':
                     v = (opcode - 30 & 2) != 0 ? stack.pop() : null;
                     w = (opcode - 30 & 1) != 0 ? stack.pop() : null;
                     u = stack.pop();
                     stack.push(u.__getslice__(w, v));
                     break;
                  case '(':
                  case ')':
                  case '*':
                  case '+':
                     v = (opcode - 40 & 2) != 0 ? stack.pop() : null;
                     w = (opcode - 40 & 1) != 0 ? stack.pop() : null;
                     u = stack.pop();
                     v = stack.pop();
                     u.__setslice__(w, v, v);
                     break;
                  case '2':
                  case '3':
                  case '4':
                  case '5':
                     v = (opcode - 50 & 2) != 0 ? stack.pop() : null;
                     w = (opcode - 50 & 1) != 0 ? stack.pop() : null;
                     u = stack.pop();
                     u.__delslice__(w, v);
                     break;
                  case '6':
                     v = stack.pop();
                     w = stack.pop();
                     stack.top().__setitem__(v, w);
                     break;
                  case '7':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._iadd(v));
                     break;
                  case '8':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._isub(v));
                     break;
                  case '9':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._imul(v));
                     break;
                  case ':':
                     v = stack.pop();
                     w = stack.pop();
                     if (!this.co_flags.isFlagSet(CodeFlag.CO_FUTURE_DIVISION)) {
                        stack.push(w._idiv(v));
                     } else {
                        stack.push(w._itruediv(v));
                     }
                     break;
                  case ';':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._imod(v));
                     break;
                  case '<':
                     v = stack.pop();
                     w = stack.pop();
                     u = stack.pop();
                     w.__setitem__(v, u);
                     break;
                  case '=':
                     v = stack.pop();
                     w = stack.pop();
                     w.__delitem__(v);
                     break;
                  case '>':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._lshift(v));
                     break;
                  case '?':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._rshift(v));
                     break;
                  case '@':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._and(v));
                     break;
                  case 'A':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._xor(v));
                     break;
                  case 'B':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._or(v));
                     break;
                  case 'C':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._ipow(v));
                     break;
                  case 'D':
                     w = stack.top().__iter__();
                     if (w != null) {
                        stack.set_top(w);
                     }
                     break;
                  case 'F':
                     PySystemState.displayhook(stack.pop());
                     break;
                  case 'G':
                     Py.printComma(stack.pop());
                     break;
                  case 'H':
                     Py.println();
                     break;
                  case 'I':
                     Py.printComma(stack.pop(), stack.pop());
                     break;
                  case 'J':
                     Py.printlnv(stack.pop());
                     break;
                  case 'K':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._ilshift(v));
                     break;
                  case 'L':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._irshift(v));
                     break;
                  case 'M':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._iand(v));
                     break;
                  case 'N':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._ixor(v));
                     break;
                  case 'O':
                     v = stack.pop();
                     w = stack.pop();
                     stack.push(w._ior(v));
                     break;
                  case 'P':
                     why = PyBytecode.Why.BREAK;
                     break;
                  case 'Q':
                     u = stack.pop();
                     if (u == Py.None) {
                        w = stack.top();
                        stack.set_top(u);
                        v = w = Py.None;
                     } else if (u instanceof PyStackWhy) {
                        switch (((PyStackWhy)u).why) {
                           case RETURN:
                           case CONTINUE:
                              w = stack.top(2);
                              stack.set_top(2, stack.top());
                              stack.set_top(u);
                              break;
                           default:
                              w = stack.top();
                              stack.set_top(u);
                        }

                        u = v = w = Py.None;
                     } else {
                        v = stack.top();
                        w = stack.top(2);
                        w = stack.top(3);
                        stack.set_top(u);
                        stack.set_top(2, v);
                        stack.set_top(3, w);
                     }

                     x = null;
                     PyObject x;
                     if (u instanceof PyStackException) {
                        PyException exc = ((PyStackException)u).exception;
                        x = w.__call__((PyObject)exc.type, (PyObject)exc.value, (PyObject)exc.traceback);
                     } else {
                        x = w.__call__(u, v, w);
                     }

                     if (u != Py.None && x != null && x.__nonzero__()) {
                        stack.top -= 2;
                        stack.set_top(Py.None);
                     }
                     break;
                  case 'R':
                     stack.push(f.f_locals);
                     break;
                  case 'S':
                     retval = stack.pop();
                     why = PyBytecode.Why.RETURN;
                     break;
                  case 'T':
                     v = stack.pop();
                     imp.importAll(v, f);
                     break;
                  case 'U':
                     v = stack.pop();
                     w = stack.pop();
                     u = stack.pop();
                     Py.exec(u, w == Py.None ? null : w, v == Py.None ? null : v);
                     break;
                  case 'V':
                     retval = stack.pop();
                     why = PyBytecode.Why.YIELD;
                     break;
                  case 'W':
                     b = popBlock(f);

                     while(true) {
                        if (stack.size() <= b.b_level) {
                           break label564;
                        }

                        stack.pop();
                     }
                  case 'X':
                     v = stack.pop();
                     if (v instanceof PyStackWhy) {
                        why = ((PyStackWhy)v).why;

                        assert why != PyBytecode.Why.YIELD;

                        if (why == PyBytecode.Why.RETURN || why == PyBytecode.Why.CONTINUE) {
                           retval = stack.pop();
                        }
                     } else if (v instanceof PyStackException) {
                        stack.top -= 2;
                        ts.exception = ((PyStackException)v).exception;
                        why = PyBytecode.Why.RERAISE;
                     } else if (v instanceof PyString) {
                        w = stack.pop();
                        u = stack.pop();
                        ts.exception = new PyException(v, w, (PyTraceback)u);
                        why = PyBytecode.Why.RERAISE;
                     } else if (v != Py.None) {
                        throw Py.SystemError("'finally' pops bad exception");
                     }
                     break;
                  case 'Y':
                     v = stack.pop();
                     PyObject[] bases = ((PySequenceList)((PySequenceList)stack.pop())).getArray();
                     String name = stack.pop().toString();
                     stack.push(Py.makeClass(name, bases, v));
                     break;
                  case 'Z':
                     f.setlocal(this.co_names[oparg], stack.pop());
                     break;
                  case '[':
                     f.dellocal(this.co_names[oparg]);
                     break;
                  case '\\':
                     unpack_iterable(oparg, stack);
                     break;
                  case ']':
                     w = stack.pop();

                     try {
                        u = w.__iternext__();
                        if (u != null) {
                           stack.push(w);
                           stack.push(u);
                           break;
                        }
                     } catch (PyException var22) {
                        if (!var22.match(Py.StopIteration)) {
                           throw var22;
                        }
                     }

                     next_instr += oparg;
                     break;
                  case '^':
                     v = stack.pop();
                     PyList a = (PyList)stack.top(oparg);
                     a.append(v);
                     break;
                  case '_':
                     v = stack.pop();
                     w = stack.pop();
                     v.__setattr__(this.co_names[oparg], w);
                     break;
                  case '`':
                     stack.pop().__delattr__(this.co_names[oparg]);
                     break;
                  case 'a':
                     f.setglobal(this.co_names[oparg], stack.pop());
                     break;
                  case 'b':
                     f.delglobal(this.co_names[oparg]);
                     break;
                  case 'c':
                     if (oparg != 2 && oparg != 3) {
                        throw Py.RuntimeError("invalid argument to DUP_TOPX (bytecode corruption?)");
                     }

                     stack.dup(oparg);
                     break;
                  case 'd':
                     stack.push(this.co_consts[oparg]);
                     break;
                  case 'e':
                     stack.push(f.getname(this.co_names[oparg]));
                     break;
                  case 'f':
                     stack.push(new PyTuple(stack.popN(oparg)));
                     break;
                  case 'g':
                     stack.push(new PyList(stack.popN(oparg)));
                     break;
                  case 'h':
                     stack.push(new PySet(stack.popN(oparg)));
                     break;
                  case 'i':
                     stack.push(new PyDictionary(PyDictionary.TYPE, oparg));
                     break;
                  case 'j':
                     name = this.co_names[oparg];
                     stack.push(stack.pop().__getattr__(name));
                     break;
                  case 'k':
                     v = stack.pop();
                     w = stack.pop();
                     switch (oparg) {
                        case 0:
                           stack.push(w._lt(v));
                           break label564;
                        case 1:
                           stack.push(w._le(v));
                           break label564;
                        case 2:
                           stack.push(w._eq(v));
                           break label564;
                        case 3:
                           stack.push(w._ne(v));
                           break label564;
                        case 4:
                           stack.push(w._gt(v));
                           break label564;
                        case 5:
                           stack.push(w._ge(v));
                           break label564;
                        case 6:
                           stack.push(w._in(v));
                           break label564;
                        case 7:
                           stack.push(w._notin(v));
                           break label564;
                        case 8:
                           stack.push(w._is(v));
                           break label564;
                        case 9:
                           stack.push(w._isnot(v));
                           break label564;
                        case 10:
                           if (w instanceof PyStackException) {
                              PyException pye = ((PyStackException)w).exception;
                              stack.push(Py.newBoolean(pye.match(v)));
                           } else {
                              stack.push(Py.newBoolean((new PyException(w)).match(v)));
                           }
                        default:
                           break label564;
                     }
                  case 'l':
                     v = f.f_builtins.__finditem__("__import__");
                     if (v == null) {
                        throw Py.ImportError("__import__ not found");
                     }

                     PyString name = Py.newString(this.co_names[oparg]);
                     u = stack.pop();
                     v = stack.pop();
                     if (v.asInt() != -1) {
                        stack.push(v.__call__(new PyObject[]{name, f.f_globals, f.f_locals, u, v}));
                     } else {
                        stack.push(v.__call__(new PyObject[]{name, f.f_globals, f.f_locals, u}));
                     }
                     break;
                  case 'm':
                     name = this.co_names[oparg];

                     try {
                        stack.push(stack.top().__getattr__(name));
                        break;
                     } catch (PyException var21) {
                        if (var21.match(Py.AttributeError)) {
                           throw Py.ImportError(String.format("cannot import name %.230s", name));
                        }

                        throw var21;
                     }
                  case 'n':
                     next_instr += oparg;
                     break;
                  case 'o':
                     if (stack.top().__nonzero__()) {
                        --stack.top;
                     } else {
                        next_instr = oparg;
                     }
                     break;
                  case 'p':
                     if (!stack.top().__nonzero__()) {
                        --stack.top;
                     } else {
                        next_instr = oparg;
                     }
                     break;
                  case 'q':
                     next_instr = oparg;
                     break;
                  case 'r':
                     if (!stack.pop().__nonzero__()) {
                        next_instr = oparg;
                     }
                     break;
                  case 's':
                     if (stack.pop().__nonzero__()) {
                        next_instr = oparg;
                     }
                     break;
                  case 't':
                     stack.push(f.getglobal(this.co_names[oparg]));
                     break;
                  case 'w':
                     retval = Py.newInteger(oparg);
                     if (((PyObject)retval).__nonzero__()) {
                        why = PyBytecode.Why.CONTINUE;
                     }
                     break;
                  case 'x':
                  case 'y':
                  case 'z':
                     pushBlock(f, new PyTryBlock(opcode, next_instr + oparg, stack.size()));
                     break;
                  case '|':
                     stack.push(f.getlocal(oparg));
                     break;
                  case '}':
                     f.setlocal(oparg, stack.pop());
                     break;
                  case '~':
                     f.dellocal(oparg);
                     break;
                  case '\u0082':
                     switch (oparg) {
                        case 0:
                           throw PyException.doRaise((PyObject)null, (PyObject)null, (PyObject)null);
                        case 1:
                           v = stack.pop();
                           throw PyException.doRaise(v, (PyObject)null, (PyObject)null);
                        case 2:
                           v = stack.pop();
                           w = stack.pop();
                           throw PyException.doRaise(w, v, (PyObject)null);
                        case 3:
                           PyTraceback tb = (PyTraceback)((PyTraceback)stack.pop());
                           w = stack.pop();
                           u = stack.pop();
                           throw PyException.doRaise(u, w, tb);
                        default:
                           throw Py.SystemError("bad RAISE_VARARGS oparg");
                     }
                  case '\u0083':
                     na = oparg & 255;
                     i = oparg >> 8 & 255;
                     if (i == 0) {
                        call_function(na, stack);
                     } else {
                        call_function(na, i, stack);
                     }
                     break;
                  case '\u0084':
                     code = (PyCode)stack.pop();
                     closure_cells = stack.popN(oparg);
                     v = null;
                     if (code instanceof PyBytecode && ((PyBytecode)code).co_consts.length > 0) {
                        v = ((PyBytecode)code).co_consts[0];
                     }

                     PyFunction func = new PyFunction(f.f_globals, closure_cells, code, v);
                     stack.push(func);
                     break;
                  case '\u0085':
                     w = oparg == 3 ? stack.pop() : null;
                     u = stack.pop();
                     v = stack.pop();
                     stack.push(new PySlice(v, u, w));
                     break;
                  case '\u0086':
                     code = (PyCode)stack.pop();
                     closure_cells = ((PySequenceList)((PySequenceList)stack.pop())).getArray();
                     PyObject[] defaults = stack.popN(oparg);
                     w = null;
                     if (code instanceof PyBytecode && ((PyBytecode)code).co_consts.length > 0) {
                        w = ((PyBytecode)code).co_consts[0];
                     }

                     x = new PyFunction(f.f_globals, defaults, code, w, closure_cells);
                     stack.push(x);
                     break;
                  case '\u0087':
                     cell = (PyCell)((PyCell)f.getclosure(oparg));
                     if (cell.ob_ref == null) {
                        if (oparg >= this.co_cellvars.length) {
                           name = this.co_freevars[oparg - this.co_cellvars.length];
                        } else {
                           name = this.co_cellvars[oparg];
                        }

                        if (f.f_fastlocals == null) {
                           cell.ob_ref = f.f_locals.__finditem__(name);
                        } else {
                           i = 0;
                           matched = false;
                           var17 = this.co_varnames;
                           var18 = var17.length;

                           for(var19 = 0; var19 < var18; ++var19) {
                              match = var17[var19];
                              if (match.equals(name)) {
                                 matched = true;
                                 break;
                              }

                              ++i;
                           }

                           if (matched) {
                              cell.ob_ref = f.f_fastlocals[i];
                           }
                        }
                     }

                     stack.push(cell);
                     break;
                  case '\u0088':
                     cell = (PyCell)((PyCell)f.getclosure(oparg));
                     if (cell.ob_ref == null) {
                        if (oparg >= this.co_cellvars.length) {
                           name = this.co_freevars[oparg - this.co_cellvars.length];
                        } else {
                           name = this.co_cellvars[oparg];
                        }

                        if (f.f_fastlocals == null) {
                           cell.ob_ref = f.f_locals.__finditem__(name);
                        } else {
                           i = 0;
                           matched = false;
                           var17 = this.co_varnames;
                           var18 = var17.length;

                           for(var19 = 0; var19 < var18; ++var19) {
                              match = var17[var19];
                              if (match.equals(name)) {
                                 matched = true;
                                 break;
                              }

                              ++i;
                           }

                           if (matched) {
                              cell.ob_ref = f.f_fastlocals[i];
                           }
                        }
                     }

                     stack.push(cell.ob_ref);
                     break;
                  case '\u0089':
                     f.setderef(oparg, stack.pop());
                     break;
                  case '\u008c':
                  case '\u008d':
                  case '\u008e':
                     na = oparg & 255;
                     i = oparg >> 8 & 255;
                     int flags = opcode - 131 & 3;
                     call_function(na, i, (flags & 1) != 0, (flags & 2) != 0, stack);
                     break;
                  case '\u008f':
                     w = stack.top();
                     u = w.__getattr__("__exit__");
                     if (u != null) {
                        stack.set_top(u);
                        v = w.__getattr__("__enter__");
                        if (v != null) {
                           w = v.__call__();
                           if (w != null) {
                              pushBlock(f, new PyTryBlock(opcode, next_instr + oparg, stack.size()));
                              stack.push(w);
                           }
                        }
                     }
                     break;
                  case '\u0091':
                     opcode = getUnsigned(this.co_code, next_instr++);
                     next_instr += 2;
                     oparg = oparg << 16 | (getUnsigned(this.co_code, next_instr) << 8) + getUnsigned(this.co_code, next_instr - 1);
                     break;
                  case '\u0092':
                     v = stack.pop();
                     PySet a = (PySet)stack.top(oparg);
                     a.add(v);
                     break;
                  case '\u0093':
                     v = stack.pop();
                     w = stack.pop();
                     stack.top(oparg).__setitem__(v, w);
               }
            } catch (Throwable var23) {
               exc = Py.setException(var23, f);
               why = PyBytecode.Why.EXCEPTION;
               ts.exception = exc;
               if (debug) {
                  System.err.println("Caught exception:" + exc);
               }
            }

            if (why != PyBytecode.Why.YIELD) {
               if (why == PyBytecode.Why.RERAISE) {
                  why = PyBytecode.Why.EXCEPTION;
               }

               label621: {
                  do {
                     if (why == PyBytecode.Why.NOT || !this.blocksLeft(f)) {
                        break label621;
                     }

                     b = popBlock(f);
                     if (debug) {
                        System.err.println("Processing block: " + b);
                     }

                     assert why != PyBytecode.Why.YIELD;

                     if (b.b_type == 120 && why == PyBytecode.Why.CONTINUE) {
                        pushBlock(f, b);
                        why = PyBytecode.Why.NOT;
                        next_instr = ((PyObject)retval).asInt();
                        break label621;
                     }

                     while(stack.size() > b.b_level) {
                        stack.pop();
                     }

                     if (b.b_type == 120 && why == PyBytecode.Why.BREAK) {
                        why = PyBytecode.Why.NOT;
                        next_instr = b.b_handler;
                        break label621;
                     }
                  } while(b.b_type != 122 && (b.b_type != 121 || why != PyBytecode.Why.EXCEPTION) && b.b_type != 143);

                  if (why == PyBytecode.Why.EXCEPTION) {
                     exc = ts.exception;
                     if (b.b_type == 121 || b.b_type == 143) {
                        exc.normalize();
                     }

                     stack.push(exc.traceback);
                     stack.push(exc.value);
                     stack.push(new PyStackException(exc));
                  } else {
                     if (why == PyBytecode.Why.RETURN || why == PyBytecode.Why.CONTINUE) {
                        stack.push((PyObject)retval);
                     }

                     stack.push(new PyStackWhy(why));
                  }

                  why = PyBytecode.Why.NOT;
                  next_instr = b.b_handler;
               }

               if (why == PyBytecode.Why.NOT) {
                  continue;
               }
            }
            break;
         }

         if (why == PyBytecode.Why.YIELD) {
            f.f_savedlocals = stack.popN(stack.size());
         } else {
            while(stack.size() > 0) {
               stack.pop();
            }

            if (why != PyBytecode.Why.RETURN) {
               retval = Py.None;
            }
         }

         f.f_lasti = next_instr;
         if (debug) {
            System.err.println(this.count + "," + f.f_lasti + "> Returning from " + why + ": " + retval + ", stack: " + stack.toString() + ", blocks: " + stringify_blocks(f));
         }

         if (why == PyBytecode.Why.EXCEPTION) {
            throw ts.exception;
         } else {
            if (this.co_flags.isFlagSet(CodeFlag.CO_GENERATOR) && why == PyBytecode.Why.RETURN && retval == Py.None) {
               f.f_lasti = -1;
            }

            return (PyObject)retval;
         }
      }
   }

   private static void call_function(int na, PyStack stack) {
      PyObject arg3;
      PyObject arg2;
      PyObject arg1;
      PyObject arg0;
      switch (na) {
         case 0:
            arg3 = stack.pop();
            stack.push(arg3.__call__());
            break;
         case 1:
            arg3 = stack.pop();
            arg2 = stack.pop();
            stack.push(arg2.__call__(arg3));
            break;
         case 2:
            arg3 = stack.pop();
            arg2 = stack.pop();
            arg1 = stack.pop();
            stack.push(arg1.__call__(arg2, arg3));
            break;
         case 3:
            arg3 = stack.pop();
            arg2 = stack.pop();
            arg1 = stack.pop();
            arg0 = stack.pop();
            stack.push(arg0.__call__(arg1, arg2, arg3));
            break;
         case 4:
            arg3 = stack.pop();
            arg2 = stack.pop();
            arg1 = stack.pop();
            arg0 = stack.pop();
            PyObject callable = stack.pop();
            stack.push(callable.__call__(arg0, arg1, arg2, arg3));
            break;
         default:
            PyObject[] args = stack.popN(na);
            arg2 = stack.pop();
            stack.push(arg2.__call__(args));
      }

   }

   private static void call_function(int na, int nk, PyStack stack) {
      int n = na + nk * 2;
      PyObject[] params = stack.popN(n);
      PyObject callable = stack.pop();
      PyObject[] args = new PyObject[na + nk];
      String[] keywords = new String[nk];

      int i;
      for(i = 0; i < na; ++i) {
         args[i] = params[i];
      }

      for(int j = 0; i < n; ++j) {
         keywords[j] = params[i].toString();
         args[na + j] = params[i + 1];
         i += 2;
      }

      stack.push(callable.__call__(args, keywords));
   }

   private static void call_function(int na, int nk, boolean var, boolean kw, PyStack stack) {
      int n = na + nk * 2;
      PyObject kwargs = kw ? stack.pop() : null;
      PyObject starargs = var ? stack.pop() : null;
      PyObject[] params = stack.popN(n);
      PyObject callable = stack.pop();
      PyObject[] args = new PyObject[na + nk];
      String[] keywords = new String[nk];

      int i;
      for(i = 0; i < na; ++i) {
         args[i] = params[i];
      }

      for(int j = 0; i < n; ++j) {
         keywords[j] = params[i].toString();
         args[na + j] = params[i + 1];
         i += 2;
      }

      stack.push(callable._callextra(args, keywords, starargs, kwargs));
   }

   private static void unpack_iterable(int oparg, PyStack stack) {
      PyObject v = stack.pop();
      int i = oparg;
      PyObject[] items = new PyObject[oparg];

      PyObject item;
      for(Iterator var5 = v.asIterable().iterator(); var5.hasNext(); items[i] = item) {
         item = (PyObject)var5.next();
         if (i <= 0) {
            throw Py.ValueError("too many values to unpack");
         }

         --i;
      }

      if (i > 0) {
         throw Py.ValueError(String.format("need more than %d value%s to unpack", i, i == 1 ? "" : "s"));
      } else {
         for(i = 0; i < oparg; ++i) {
            stack.push(items[i]);
         }

      }
   }

   protected int getline(PyFrame f) {
      int addrq = f.f_lasti;
      int size = this.co_lnotab.length / 2;
      int p = 0;
      int line = this.co_firstlineno;
      int addr = 0;

      while(true) {
         --size;
         if (size < 0) {
            break;
         }

         addr += getUnsigned(this.co_lnotab, p++);
         if (addr > addrq) {
            break;
         }

         line += getUnsigned(this.co_lnotab, p++);
      }

      return line;
   }

   private static char getUnsigned(byte[] x, int i) {
      byte b = x[i];
      return b < 0 ? (char)(b + 256) : (char)b;
   }

   private static String getString(byte[] x) {
      StringBuilder buffer = new StringBuilder(x.length);

      for(int i = 0; i < x.length; ++i) {
         buffer.append(getUnsigned(x, i));
      }

      return buffer.toString();
   }

   private static byte[] getBytes(String s) {
      int len = s.length();
      byte[] x = new byte[len];

      for(int i = 0; i < len; ++i) {
         x[i] = (byte)(s.charAt(i) & 255);
      }

      return x;
   }

   public int traverse(Visitproc visit, Object arg) {
      if (this.co_consts != null) {
         PyObject[] var3 = this.co_consts;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PyObject ob = var3[var5];
            if (ob != null) {
               int retValue = visit.visit(ob, arg);
               if (retValue != 0) {
                  return retValue;
               }
            }
         }
      }

      return 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      if (ob != null && this.co_consts != null) {
         PyObject[] var2 = this.co_consts;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PyObject obj = var2[var4];
            if (obj == ob) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private class LineCache {
      List addr_breakpoints;
      List lines;

      private LineCache() {
         this.addr_breakpoints = new ArrayList();
         this.lines = new ArrayList();
         int size = PyBytecode.this.co_lnotab.length / 2;
         int p = 0;
         int lastline = -1;
         int line = PyBytecode.this.co_firstlineno;
         int addr = 0;

         while(true) {
            --size;
            if (size < 0) {
               if (line != lastline) {
                  this.lines.add(line);
               }

               return;
            }

            int byte_incr = PyBytecode.getUnsigned(PyBytecode.this.co_lnotab, p++);
            int line_incr = PyBytecode.getUnsigned(PyBytecode.this.co_lnotab, p++);
            if (byte_incr > 0) {
               if (line != lastline) {
                  this.addr_breakpoints.add(addr);
                  this.lines.add(line);
                  lastline = line;
               }

               addr += byte_incr;
            }

            line += line_incr;
         }
      }

      private int getline(int addrq) {
         int lo = 0;
         int hi = this.addr_breakpoints.size();

         while(lo < hi) {
            int mid = (lo + hi) / 2;
            if (addrq < (Integer)this.addr_breakpoints.get(mid)) {
               hi = mid;
            } else {
               lo = mid + 1;
            }
         }

         return (Integer)this.lines.get(lo);
      }

      public String toString() {
         return this.addr_breakpoints.toString() + ";" + this.lines.toString();
      }

      // $FF: synthetic method
      LineCache(Object x1) {
         this();
      }
   }

   @Untraversable
   private static class PyTryBlock extends PyObject {
      int b_type;
      int b_handler;
      int b_level;

      PyTryBlock(int type, int handler, int level) {
         this.b_type = type;
         this.b_handler = handler;
         this.b_level = level;
      }

      public String toString() {
         return "<" + PyBytecode.get_opname().__getitem__(Py.newInteger(this.b_type)) + "," + this.b_handler + "," + this.b_level + ">";
      }
   }

   private static class PyStack {
      final PyObject[] stack;
      int top = -1;

      PyStack(int size) {
         this.stack = new PyObject[size];
      }

      PyObject top() {
         return this.stack[this.top];
      }

      PyObject top(int n) {
         return this.stack[this.top - n + 1];
      }

      PyObject pop() {
         return this.stack[this.top--];
      }

      void push(PyObject v) {
         this.stack[++this.top] = v;
      }

      void set_top(PyObject v) {
         this.stack[this.top] = v;
      }

      void set_top(int n, PyObject v) {
         this.stack[this.top - n + 1] = v;
      }

      void dup() {
         this.stack[this.top + 1] = this.stack[this.top];
         ++this.top;
      }

      void dup(int n) {
         int oldTop = this.top;
         this.top += n;

         for(int i = 0; i < n; ++i) {
            this.stack[this.top - i] = this.stack[oldTop - i];
         }

      }

      PyObject[] popN(int n) {
         PyObject[] ret = new PyObject[n];
         this.top -= n;

         for(int i = 0; i < n; ++i) {
            ret[i] = this.stack[this.top + i + 1];
         }

         return ret;
      }

      void rot2() {
         PyObject topv = this.stack[this.top];
         this.stack[this.top] = this.stack[this.top - 1];
         this.stack[this.top - 1] = topv;
      }

      void rot3() {
         PyObject v = this.stack[this.top];
         PyObject w = this.stack[this.top - 1];
         PyObject x = this.stack[this.top - 2];
         this.stack[this.top] = w;
         this.stack[this.top - 1] = x;
         this.stack[this.top - 2] = v;
      }

      void rot4() {
         PyObject u = this.stack[this.top];
         PyObject v = this.stack[this.top - 1];
         PyObject w = this.stack[this.top - 2];
         PyObject x = this.stack[this.top - 3];
         this.stack[this.top] = v;
         this.stack[this.top - 1] = w;
         this.stack[this.top - 2] = x;
         this.stack[this.top - 3] = u;
      }

      int size() {
         return this.top + 1;
      }

      public String toString() {
         StringBuilder buffer = new StringBuilder();
         int size = this.size();
         int N = size > 4 ? 4 : size;
         buffer.append("[");

         for(int i = 0; i < N; ++i) {
            if (i > 0) {
               buffer.append(", ");
            }

            PyObject item = this.stack[N - i - 1];
            buffer.append(this.upto(item.__repr__().toString()));
         }

         if (N < size) {
            buffer.append(String.format(", %d more...", size - N));
         }

         buffer.append("]");
         return buffer.toString();
      }

      private String upto(String x) {
         return this.upto(x, 100);
      }

      private String upto(String x, int n) {
         x = x.replace('\n', '|');
         if (x.length() > n) {
            StringBuilder item = new StringBuilder(x.substring(0, n));
            item.append("...");
            return item.toString();
         } else {
            return x;
         }
      }
   }

   private static class PyStackException extends PyObject implements Traverseproc {
      PyException exception;

      PyStackException(PyException exception) {
         this.exception = exception;
      }

      public String toString() {
         return String.format("PyStackException<%s,%s,%.100s>", this.exception.type, this.exception.value, this.exception.traceback);
      }

      public int traverse(Visitproc visit, Object arg) {
         return this.exception != null ? this.exception.traverse(visit, arg) : 0;
      }

      public boolean refersDirectlyTo(PyObject ob) {
         return ob != null && this.exception.refersDirectlyTo(ob);
      }
   }

   @Untraversable
   private static class PyStackWhy extends PyObject {
      Why why;

      PyStackWhy(Why why) {
         this.why = why;
      }

      public String toString() {
         return this.why.toString();
      }
   }

   static enum Why {
      NOT,
      EXCEPTION,
      RERAISE,
      RETURN,
      BREAK,
      CONTINUE,
      YIELD;
   }
}
