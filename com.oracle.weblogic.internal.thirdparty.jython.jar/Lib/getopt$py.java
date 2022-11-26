import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("getopt.py")
public class getopt$py extends PyFunctionTable implements PyRunnable {
   static getopt$py self;
   static final PyCode f$0;
   static final PyCode GetoptError$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode getopt$4;
   static final PyCode gnu_getopt$5;
   static final PyCode do_longs$6;
   static final PyCode long_has_args$7;
   static final PyCode do_shorts$8;
   static final PyCode short_has_arg$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Parser for command line options.\n\nThis module helps scripts to parse the command line arguments in\nsys.argv.  It supports the same conventions as the Unix getopt()\nfunction (including the special meanings of arguments of the form `-'\nand `--').  Long options similar to those supported by GNU software\nmay be used as well via an optional third argument.  This module\nprovides two functions and an exception:\n\ngetopt() -- Parse command line options\ngnu_getopt() -- Like getopt(), but allow option and non-option arguments\nto be intermixed.\nGetoptError -- exception (class) raised with 'opt' attribute, which is the\noption involved with the exception.\n"));
      var1.setline(15);
      PyString.fromInterned("Parser for command line options.\n\nThis module helps scripts to parse the command line arguments in\nsys.argv.  It supports the same conventions as the Unix getopt()\nfunction (including the special meanings of arguments of the form `-'\nand `--').  Long options similar to those supported by GNU software\nmay be used as well via an optional third argument.  This module\nprovides two functions and an exception:\n\ngetopt() -- Parse command line options\ngnu_getopt() -- Like getopt(), but allow option and non-option arguments\nto be intermixed.\nGetoptError -- exception (class) raised with 'opt' attribute, which is the\noption involved with the exception.\n");
      var1.setline(34);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("GetoptError"), PyString.fromInterned("error"), PyString.fromInterned("getopt"), PyString.fromInterned("gnu_getopt")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(36);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(38);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("GetoptError", var6, GetoptError$1);
      var1.setlocal("GetoptError", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(49);
      var5 = var1.getname("GetoptError");
      var1.setlocal("error", var5);
      var3 = null;
      var1.setline(51);
      var6 = new PyObject[]{new PyList(Py.EmptyObjects)};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, getopt$4, PyString.fromInterned("getopt(args, options[, long_options]) -> opts, args\n\n    Parses command line options and parameter list.  args is the\n    argument list to be parsed, without the leading reference to the\n    running program.  Typically, this means \"sys.argv[1:]\".  shortopts\n    is the string of option letters that the script wants to\n    recognize, with options that require an argument followed by a\n    colon (i.e., the same format that Unix getopt() uses).  If\n    specified, longopts is a list of strings with the names of the\n    long options which should be supported.  The leading '--'\n    characters should not be included in the option name.  Options\n    which require an argument should be followed by an equal sign\n    ('=').\n\n    The return value consists of two elements: the first is a list of\n    (option, value) pairs; the second is the list of program arguments\n    left after the option list was stripped (this is a trailing slice\n    of the first argument).  Each option-and-value pair returned has\n    the option as its first element, prefixed with a hyphen (e.g.,\n    '-x'), and the option argument as its second element, or an empty\n    string if the option has no argument.  The options occur in the\n    list in the same order in which they were found, thus allowing\n    multiple occurrences.  Long and short options may be mixed.\n\n    "));
      var1.setlocal("getopt", var7);
      var3 = null;
      var1.setline(94);
      var6 = new PyObject[]{new PyList(Py.EmptyObjects)};
      var7 = new PyFunction(var1.f_globals, var6, gnu_getopt$5, PyString.fromInterned("getopt(args, options[, long_options]) -> opts, args\n\n    This function works like getopt(), except that GNU style scanning\n    mode is used by default. This means that option and non-option\n    arguments may be intermixed. The getopt() function stops\n    processing options as soon as a non-option argument is\n    encountered.\n\n    If the first character of the option string is `+', or if the\n    environment variable POSIXLY_CORRECT is set, then option\n    processing stops as soon as a non-option argument is encountered.\n\n    "));
      var1.setlocal("gnu_getopt", var7);
      var3 = null;
      var1.setline(144);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, do_longs$6, (PyObject)null);
      var1.setlocal("do_longs", var7);
      var3 = null;
      var1.setline(166);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, long_has_args$7, (PyObject)null);
      var1.setlocal("long_has_args", var7);
      var3 = null;
      var1.setline(187);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, do_shorts$8, (PyObject)null);
      var1.setlocal("do_shorts", var7);
      var3 = null;
      var1.setline(202);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, short_has_arg$9, (PyObject)null);
      var1.setlocal("short_has_arg", var7);
      var3 = null;
      var1.setline(208);
      var5 = var1.getname("__name__");
      PyObject var10000 = var5._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(209);
         var5 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var5);
         var3 = null;
         var1.setline(210);
         Py.println(var1.getname("getopt").__call__((ThreadState)var2, var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("a:b"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("alpha="), PyString.fromInterned("beta")}))));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject GetoptError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(39);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal("opt", var3);
      var3 = null;
      var1.setline(40);
      var3 = PyString.fromInterned("");
      var1.setlocal("msg", var3);
      var3 = null;
      var1.setline(41);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(46);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("opt", var3);
      var3 = null;
      var1.setline(44);
      var1.getglobal("Exception").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getopt$4(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyString.fromInterned("getopt(args, options[, long_options]) -> opts, args\n\n    Parses command line options and parameter list.  args is the\n    argument list to be parsed, without the leading reference to the\n    running program.  Typically, this means \"sys.argv[1:]\".  shortopts\n    is the string of option letters that the script wants to\n    recognize, with options that require an argument followed by a\n    colon (i.e., the same format that Unix getopt() uses).  If\n    specified, longopts is a list of strings with the names of the\n    long options which should be supported.  The leading '--'\n    characters should not be included in the option name.  Options\n    which require an argument should be followed by an equal sign\n    ('=').\n\n    The return value consists of two elements: the first is a list of\n    (option, value) pairs; the second is the list of program arguments\n    left after the option list was stripped (this is a trailing slice\n    of the first argument).  Each option-and-value pair returned has\n    the option as its first element, prefixed with a hyphen (e.g.,\n    '-x'), and the option argument as its second element, or an empty\n    string if the option has no argument.  The options occur in the\n    list in the same order in which they were found, thus allowing\n    multiple occurrences.  Long and short options may be mixed.\n\n    ");
      var1.setline(78);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(79);
      PyObject var6 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var6._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(80);
         var3 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(82);
         var6 = var1.getglobal("list").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var6);
         var3 = null;
      }

      while(true) {
         var1.setline(83);
         var10000 = var1.getlocal(0);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(0).__getitem__(Py.newInteger(0));
               var10000 = var6._ne(PyString.fromInterned("-"));
               var3 = null;
            }
         }

         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(84);
         var6 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var6._eq(PyString.fromInterned("--"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(85);
            var6 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(0, var6);
            var3 = null;
            break;
         }

         var1.setline(87);
         PyObject[] var4;
         PyObject var5;
         if (var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--")).__nonzero__()) {
            var1.setline(88);
            var6 = var1.getglobal("do_longs").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null), var1.getlocal(2), var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(0, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(90);
            var6 = var1.getglobal("do_shorts").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(1), var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(0, var5);
            var5 = null;
            var3 = null;
         }
      }

      var1.setline(92);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(0)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject gnu_getopt$5(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyString.fromInterned("getopt(args, options[, long_options]) -> opts, args\n\n    This function works like getopt(), except that GNU style scanning\n    mode is used by default. This means that option and non-option\n    arguments may be intermixed. The getopt() function stops\n    processing options as soon as a non-option argument is\n    encountered.\n\n    If the first character of the option string is `+', or if the\n    environment variable POSIXLY_CORRECT is set, then option\n    processing stops as soon as a non-option argument is encountered.\n\n    ");
      var1.setline(109);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(110);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(111);
      PyObject var6;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__()) {
         var1.setline(112);
         var3 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(114);
         var6 = var1.getglobal("list").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(117);
      if (var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+")).__nonzero__()) {
         var1.setline(118);
         var6 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(119);
         var6 = var1.getglobal("True");
         var1.setlocal(5, var6);
         var3 = null;
      } else {
         var1.setline(120);
         if (var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POSIXLY_CORRECT")).__nonzero__()) {
            var1.setline(121);
            var6 = var1.getglobal("True");
            var1.setlocal(5, var6);
            var3 = null;
         } else {
            var1.setline(123);
            var6 = var1.getglobal("False");
            var1.setlocal(5, var6);
            var3 = null;
         }
      }

      while(true) {
         var1.setline(125);
         if (!var1.getlocal(0).__nonzero__()) {
            break;
         }

         var1.setline(126);
         var6 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         PyObject var10000 = var6._eq(PyString.fromInterned("--"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(127);
            var6 = var1.getlocal(4);
            var6 = var6._iadd(var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.setlocal(4, var6);
            break;
         }

         var1.setline(130);
         var6 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         var10000 = var6._eq(PyString.fromInterned("--"));
         var3 = null;
         PyObject[] var4;
         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var6 = var1.getglobal("do_longs").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null), var1.getlocal(2), var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(0, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(132);
            var6 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var6._eq(PyString.fromInterned("-"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(0).__getitem__(Py.newInteger(0));
               var10000 = var6._ne(PyString.fromInterned("-"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(133);
               var6 = var1.getglobal("do_shorts").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(1), var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               var4 = Py.unpackSequence(var6, 2);
               var5 = var4[0];
               var1.setlocal(3, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(0, var5);
               var5 = null;
               var3 = null;
            } else {
               var1.setline(135);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(136);
                  var6 = var1.getlocal(4);
                  var6 = var6._iadd(var1.getlocal(0));
                  var1.setlocal(4, var6);
                  break;
               }

               var1.setline(139);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)));
               var1.setline(140);
               var6 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var6);
               var3 = null;
            }
         }
      }

      var1.setline(142);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject do_longs$6(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject[] var5;
      PyObject var8;
      label36: {
         PyObject var4;
         try {
            var1.setline(146);
            var8 = var1.getlocal(1).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
            var1.setlocal(4, var8);
            var3 = null;
         } catch (Throwable var7) {
            var3 = Py.setException(var7, var1);
            if (var3.match(var1.getglobal("ValueError"))) {
               var1.setline(148);
               var4 = var1.getglobal("None");
               var1.setlocal(5, var4);
               var4 = null;
               break label36;
            }

            throw var3;
         }

         var1.setline(150);
         PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null), var1.getlocal(1).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
         var5 = Py.unpackSequence(var9, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var4 = null;
      }

      var1.setline(152);
      var8 = var1.getglobal("long_has_args").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var10 = Py.unpackSequence(var8, 2);
      PyObject var11 = var10[0];
      var1.setlocal(6, var11);
      var5 = null;
      var11 = var10[1];
      var1.setlocal(1, var11);
      var5 = null;
      var3 = null;
      var1.setline(153);
      PyTuple var12;
      PyObject var10000;
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(154);
         var8 = var1.getlocal(5);
         var10000 = var8._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(155);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(156);
               throw Py.makeException(var1.getglobal("GetoptError").__call__(var2, PyString.fromInterned("option --%s requires argument")._mod(var1.getlocal(1)), var1.getlocal(1)));
            }

            var1.setline(157);
            var12 = new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)});
            var10 = Py.unpackSequence(var12, 2);
            var11 = var10[0];
            var1.setlocal(5, var11);
            var5 = null;
            var11 = var10[1];
            var1.setlocal(3, var11);
            var5 = null;
            var3 = null;
         }
      } else {
         var1.setline(158);
         var8 = var1.getlocal(5);
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(159);
            throw Py.makeException(var1.getglobal("GetoptError").__call__(var2, PyString.fromInterned("option --%s must not have an argument")._mod(var1.getlocal(1)), var1.getlocal(1)));
         }
      }

      var1.setline(160);
      var10000 = var1.getlocal(0).__getattr__("append");
      PyTuple var10002 = new PyTuple;
      PyObject[] var10004 = new PyObject[]{PyString.fromInterned("--")._add(var1.getlocal(1)), null};
      Object var10007 = var1.getlocal(5);
      if (!((PyObject)var10007).__nonzero__()) {
         var10007 = PyString.fromInterned("");
      }

      var10004[1] = (PyObject)var10007;
      var10002.<init>(var10004);
      var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setline(161);
      var12 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var12;
   }

   public PyObject long_has_args$7(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(167);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(167);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(167);
            var1.dellocal(3);
            PyList var5 = var10000;
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(168);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(169);
               throw Py.makeException(var1.getglobal("GetoptError").__call__(var2, PyString.fromInterned("option --%s not recognized")._mod(var1.getlocal(0)), var1.getlocal(0)));
            }

            var1.setline(171);
            var3 = var1.getlocal(0);
            PyObject var7 = var3._in(var1.getlocal(2));
            var3 = null;
            PyTuple var6;
            if (var7.__nonzero__()) {
               var1.setline(172);
               var6 = new PyTuple(new PyObject[]{var1.getglobal("False"), var1.getlocal(0)});
               var1.f_lasti = -1;
               return var6;
            }

            var1.setline(173);
            var4 = var1.getlocal(0)._add(PyString.fromInterned("="));
            var7 = var4._in(var1.getlocal(2));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(174);
               var6 = new PyTuple(new PyObject[]{var1.getglobal("True"), var1.getlocal(0)});
               var1.f_lasti = -1;
               return var6;
            }

            var1.setline(176);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var7 = var4._gt(Py.newInteger(1));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(179);
               throw Py.makeException(var1.getglobal("GetoptError").__call__(var2, PyString.fromInterned("option --%s not a unique prefix")._mod(var1.getlocal(0)), var1.getlocal(0)));
            }

            var1.setline(180);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var7 = var4._eq(Py.newInteger(1));
               var4 = null;
               if (!var7.__nonzero__()) {
                  var7 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var7);
               }
            }

            var1.setline(181);
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(182);
            var4 = var1.getlocal(5).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(183);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(184);
               var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(185);
            var6 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(167);
         if (var1.getlocal(4).__getattr__("startswith").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(167);
            var1.getlocal(3).__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject do_shorts$8(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(188);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._ne(PyString.fromInterned(""));
         var3 = null;
         PyTuple var6;
         if (!var10000.__nonzero__()) {
            var1.setline(200);
            var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(189);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)});
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
         var1.setline(190);
         if (var1.getglobal("short_has_arg").__call__(var2, var1.getlocal(4), var1.getlocal(2)).__nonzero__()) {
            var1.setline(191);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(192);
               if (var1.getlocal(3).__not__().__nonzero__()) {
                  var1.setline(193);
                  throw Py.makeException(var1.getglobal("GetoptError").__call__(var2, PyString.fromInterned("option -%s requires argument")._mod(var1.getlocal(4)), var1.getlocal(4)));
               }

               var1.setline(195);
               var6 = new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)});
               var4 = Py.unpackSequence(var6, 2);
               var5 = var4[0];
               var1.setlocal(1, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(3, var5);
               var5 = null;
               var3 = null;
            }

            var1.setline(196);
            var6 = new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned("")});
            var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(1, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(198);
            PyString var7 = PyString.fromInterned("");
            var1.setlocal(5, var7);
            var3 = null;
         }

         var1.setline(199);
         var1.getlocal(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("-")._add(var1.getlocal(4)), var1.getlocal(5)})));
      }
   }

   public PyObject short_has_arg$9(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

      PyObject var5;
      PyObject var6;
      do {
         var1.setline(203);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(206);
            throw Py.makeException(var1.getglobal("GetoptError").__call__(var2, PyString.fromInterned("option -%s not recognized")._mod(var1.getlocal(0)), var1.getlocal(0)));
         }

         var1.setlocal(2, var4);
         var1.setline(204);
         var5 = var1.getlocal(0);
         PyObject var10001 = var1.getlocal(1).__getitem__(var1.getlocal(2));
         PyObject var10000 = var5;
         var5 = var10001;
         if ((var6 = var10000._eq(var10001)).__nonzero__()) {
            var6 = var5._ne(PyString.fromInterned(":"));
         }

         var5 = null;
      } while(!var6.__nonzero__());

      var1.setline(205);
      var5 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)var1.getlocal(2)._add(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var5;
   }

   public getopt$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      GetoptError$1 = Py.newCode(0, var2, var1, "GetoptError", 38, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "opt"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 41, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 46, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "shortopts", "longopts", "opts"};
      getopt$4 = Py.newCode(3, var2, var1, "getopt", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "shortopts", "longopts", "opts", "prog_args", "all_options_first"};
      gnu_getopt$5 = Py.newCode(3, var2, var1, "gnu_getopt", 94, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opts", "opt", "longopts", "args", "i", "optarg", "has_arg"};
      do_longs$6 = Py.newCode(4, var2, var1, "do_longs", 144, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opt", "longopts", "possibilities", "_[167_21]", "o", "unique_match", "has_arg"};
      long_has_args$7 = Py.newCode(2, var2, var1, "long_has_args", 166, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opts", "optstring", "shortopts", "args", "opt", "optarg"};
      do_shorts$8 = Py.newCode(4, var2, var1, "do_shorts", 187, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opt", "shortopts", "i"};
      short_has_arg$9 = Py.newCode(2, var2, var1, "short_has_arg", 202, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new getopt$py("getopt$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(getopt$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.GetoptError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this.getopt$4(var2, var3);
         case 5:
            return this.gnu_getopt$5(var2, var3);
         case 6:
            return this.do_longs$6(var2, var3);
         case 7:
            return this.long_has_args$7(var2, var3);
         case 8:
            return this.do_shorts$8(var2, var3);
         case 9:
            return this.short_has_arg$9(var2, var3);
         default:
            return null;
      }
   }
}
