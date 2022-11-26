package distutils;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
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
@Filename("distutils/fancy_getopt.py")
public class fancy_getopt$py extends PyFunctionTable implements PyRunnable {
   static fancy_getopt$py self;
   static final PyCode f$0;
   static final PyCode FancyGetopt$1;
   static final PyCode __init__$2;
   static final PyCode _build_index$3;
   static final PyCode set_option_table$4;
   static final PyCode add_option$5;
   static final PyCode has_option$6;
   static final PyCode get_attr_name$7;
   static final PyCode _check_alias_dict$8;
   static final PyCode set_aliases$9;
   static final PyCode set_negative_aliases$10;
   static final PyCode _grok_option_table$11;
   static final PyCode getopt$12;
   static final PyCode get_option_order$13;
   static final PyCode generate_help$14;
   static final PyCode print_help$15;
   static final PyCode fancy_getopt$16;
   static final PyCode wrap_text$17;
   static final PyCode translate_longopt$18;
   static final PyCode OptionDummy$19;
   static final PyCode __init__$20;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.fancy_getopt\n\nWrapper around the standard getopt module that provides the following\nadditional features:\n  * short and long options are tied together\n  * options have help strings, so fancy_getopt could potentially\n    create a complete usage summary\n  * options set attributes of a passed-in object\n"));
      var1.setline(9);
      PyString.fromInterned("distutils.fancy_getopt\n\nWrapper around the standard getopt module that provides the following\nadditional features:\n  * short and long options are tied together\n  * options have help strings, so fancy_getopt could potentially\n    create a complete usage summary\n  * options set attributes of a passed-in object\n");
      var1.setline(11);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(13);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(14);
      var5 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var5);
      var3 = null;
      var1.setline(15);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(16);
      var5 = imp.importOne("getopt", var1, -1);
      var1.setlocal("getopt", var5);
      var3 = null;
      var1.setline(17);
      String[] var6 = new String[]{"DistutilsGetoptError", "DistutilsArgError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsGetoptError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsArgError", var4);
      var4 = null;
      var1.setline(23);
      var3 = PyString.fromInterned("[a-zA-Z](?:[a-zA-Z0-9-]*)");
      var1.setlocal("longopt_pat", var3);
      var3 = null;
      var1.setline(24);
      var5 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("^%s$")._mod(var1.getname("longopt_pat")));
      var1.setlocal("longopt_re", var5);
      var3 = null;
      var1.setline(27);
      var5 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("^(%s)=!(%s)$")._mod(new PyTuple(new PyObject[]{var1.getname("longopt_pat"), var1.getname("longopt_pat")})));
      var1.setlocal("neg_alias_re", var5);
      var3 = null;
      var1.setline(31);
      var5 = var1.getname("string").__getattr__("maketrans").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
      var1.setlocal("longopt_xlate", var5);
      var3 = null;
      var1.setline(33);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("FancyGetopt", var7, FancyGetopt$1);
      var1.setlocal("FancyGetopt", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(402);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, fancy_getopt$16, (PyObject)null);
      var1.setlocal("fancy_getopt", var8);
      var3 = null;
      var1.setline(408);
      var5 = var1.getname("string").__getattr__("maketrans").__call__(var2, var1.getname("string").__getattr__("whitespace"), PyString.fromInterned(" ")._mul(var1.getname("len").__call__(var2, var1.getname("string").__getattr__("whitespace"))));
      var1.setlocal("WS_TRANS", var5);
      var3 = null;
      var1.setline(410);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, wrap_text$17, PyString.fromInterned("wrap_text(text : string, width : int) -> [string]\n\n    Split 'text' into multiple lines of no more than 'width' characters\n    each, and return the list of strings that results.\n    "));
      var1.setlocal("wrap_text", var8);
      var3 = null;
      var1.setline(469);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, translate_longopt$18, PyString.fromInterned("Convert a long option name to a valid Python identifier by\n    changing \"-\" to \"_\".\n    "));
      var1.setlocal("translate_longopt", var8);
      var3 = null;
      var1.setline(476);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("OptionDummy", var7, OptionDummy$19);
      var1.setlocal("OptionDummy", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FancyGetopt$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Wrapper around the standard 'getopt()' module that provides some\n    handy extra functionality:\n      * short and long options are tied together\n      * options have help strings, and help text can be assembled\n        from them\n      * options set attributes of a passed-in object\n      * boolean options can have \"negative aliases\" -- eg. if\n        --quiet is the \"negative alias\" of --verbose, then \"--quiet\"\n        on the command line sets 'verbose' to false\n    "));
      var1.setline(43);
      PyString.fromInterned("Wrapper around the standard 'getopt()' module that provides some\n    handy extra functionality:\n      * short and long options are tied together\n      * options have help strings, and help text can be assembled\n        from them\n      * options set attributes of a passed-in object\n      * boolean options can have \"negative aliases\" -- eg. if\n        --quiet is the \"negative alias\" of --verbose, then \"--quiet\"\n        on the command line sets 'verbose' to false\n    ");
      var1.setline(45);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(89);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _build_index$3, (PyObject)null);
      var1.setlocal("_build_index", var4);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_option_table$4, (PyObject)null);
      var1.setlocal("set_option_table", var4);
      var3 = null;
      var1.setline(98);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, add_option$5, (PyObject)null);
      var1.setlocal("add_option", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_option$6, PyString.fromInterned("Return true if the option table for this parser has an\n        option with long name 'long_option'."));
      var1.setlocal("has_option", var4);
      var3 = null;
      var1.setline(113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_attr_name$7, PyString.fromInterned("Translate long option name 'long_option' to the form it\n        has as an attribute of some object: ie., translate hyphens\n        to underscores."));
      var1.setlocal("get_attr_name", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _check_alias_dict$8, (PyObject)null);
      var1.setlocal("_check_alias_dict", var4);
      var3 = null;
      var1.setline(132);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_aliases$9, PyString.fromInterned("Set the aliases for this option parser."));
      var1.setlocal("set_aliases", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_negative_aliases$10, PyString.fromInterned("Set the negative aliases for this option parser.\n        'negative_alias' should be a dictionary mapping option names to\n        option names, both the key and value must already be defined\n        in the option table."));
      var1.setlocal("set_negative_aliases", var4);
      var3 = null;
      var1.setline(146);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _grok_option_table$11, PyString.fromInterned("Populate the various data structures that keep tabs on the\n        option table.  Called by 'getopt()' before it can do anything\n        worthwhile.\n        "));
      var1.setlocal("_grok_option_table", var4);
      var3 = null;
      var1.setline(234);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getopt$12, PyString.fromInterned("Parse command-line options in args. Store as attributes on object.\n\n        If 'args' is None or not supplied, uses 'sys.argv[1:]'.  If\n        'object' is None or not supplied, creates a new OptionDummy\n        object, stores option values there, and returns a tuple (args,\n        object).  If 'object' is supplied, it is modified in place and\n        'getopt()' just returns 'args'; in both cases, the returned\n        'args' is a modified copy of the passed-in 'args' list, which\n        is left untouched.\n        "));
      var1.setlocal("getopt", var4);
      var3 = null;
      var1.setline(298);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_option_order$13, PyString.fromInterned("Returns the list of (option, value) tuples processed by the\n        previous run of 'getopt()'.  Raises RuntimeError if\n        'getopt()' hasn't been called yet.\n        "));
      var1.setlocal("get_option_order", var4);
      var3 = null;
      var1.setline(309);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, generate_help$14, PyString.fromInterned("Generate help text (a list of strings, one per suggested line of\n        output) from the option table for this FancyGetopt object.\n        "));
      var1.setlocal("generate_help", var4);
      var3 = null;
      var1.setline(393);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, print_help$15, (PyObject)null);
      var1.setlocal("print_help", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("option_table", var3);
      var3 = null;
      var1.setline(59);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"option_index", var4);
      var3 = null;
      var1.setline(60);
      if (var1.getlocal(0).__getattr__("option_table").__nonzero__()) {
         var1.setline(61);
         var1.getlocal(0).__getattr__("_build_index").__call__(var2);
      }

      var1.setline(65);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"alias", var4);
      var3 = null;
      var1.setline(69);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"negative_alias", var4);
      var3 = null;
      var1.setline(75);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"short_opts", var5);
      var3 = null;
      var1.setline(76);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"long_opts", var5);
      var3 = null;
      var1.setline(77);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"short2long", var4);
      var3 = null;
      var1.setline(78);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"attr_name", var4);
      var3 = null;
      var1.setline(79);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"takes_arg", var4);
      var3 = null;
      var1.setline(84);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"option_order", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _build_index$3(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      var1.getlocal(0).__getattr__("option_index").__getattr__("clear").__call__(var2);
      var1.setline(91);
      PyObject var3 = var1.getlocal(0).__getattr__("option_table").__iter__();

      while(true) {
         var1.setline(91);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(92);
         PyObject var5 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("option_index").__setitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)), var5);
         var5 = null;
      }
   }

   public PyObject set_option_table$4(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("option_table", var3);
      var3 = null;
      var1.setline(96);
      var1.getlocal(0).__getattr__("_build_index").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_option$5(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("option_index"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(100);
         throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("option conflict: already an option '%s'")._mod(var1.getlocal(1)));
      } else {
         var1.setline(103);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(104);
         var1.getlocal(0).__getattr__("option_table").__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(105);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__getattr__("option_index").__setitem__(var1.getlocal(1), var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject has_option$6(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("Return true if the option table for this parser has an\n        option with long name 'long_option'.");
      var1.setline(111);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("option_index"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_attr_name$7(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyString.fromInterned("Translate long option name 'long_option' to the form it\n        has as an attribute of some object: ie., translate hyphens\n        to underscores.");
      var1.setline(117);
      PyObject var3 = var1.getglobal("string").__getattr__("translate").__call__(var2, var1.getlocal(1), var1.getglobal("longopt_xlate"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _check_alias_dict$8(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("dict")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(122);
         PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         do {
            var1.setline(122);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(123);
            PyObject var7 = var1.getlocal(3);
            var10000 = var7._notin(var1.getlocal(0).__getattr__("option_index"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(124);
               throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("invalid %s '%s': option '%s' not defined")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(3)})));
            }

            var1.setline(127);
            var7 = var1.getlocal(4);
            var10000 = var7._notin(var1.getlocal(0).__getattr__("option_index"));
            var5 = null;
         } while(!var10000.__nonzero__());

         var1.setline(128);
         throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("invalid %s '%s': aliased option '%s' not defined")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})));
      }
   }

   public PyObject set_aliases$9(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Set the aliases for this option parser.");
      var1.setline(134);
      var1.getlocal(0).__getattr__("_check_alias_dict").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("alias"));
      var1.setline(135);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("alias", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_negative_aliases$10(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Set the negative aliases for this option parser.\n        'negative_alias' should be a dictionary mapping option names to\n        option names, both the key and value must already be defined\n        in the option table.");
      var1.setline(142);
      var1.getlocal(0).__getattr__("_check_alias_dict").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("negative alias"));
      var1.setline(143);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("negative_alias", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _grok_option_table$11(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("Populate the various data structures that keep tabs on the\n        option table.  Called by 'getopt()' before it can do anything\n        worthwhile.\n        ");
      var1.setline(151);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"long_opts", var3);
      var3 = null;
      var1.setline(152);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"short_opts", var3);
      var3 = null;
      var1.setline(153);
      var1.getlocal(0).__getattr__("short2long").__getattr__("clear").__call__(var2);
      var1.setline(154);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"repeat", var8);
      var3 = null;
      var1.setline(156);
      PyObject var9 = var1.getlocal(0).__getattr__("option_table").__iter__();

      while(true) {
         var1.setline(156);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(157);
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var5._eq(Py.newInteger(3));
         var5 = null;
         PyObject[] var6;
         PyObject var7;
         PyInteger var10;
         if (var10000.__nonzero__()) {
            var1.setline(158);
            var5 = var1.getlocal(1);
            var6 = Py.unpackSequence(var5, 3);
            var7 = var6[0];
            var1.setlocal(2, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(4, var7);
            var7 = null;
            var5 = null;
            var1.setline(159);
            var10 = Py.newInteger(0);
            var1.setlocal(5, var10);
            var5 = null;
         } else {
            var1.setline(160);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var5._eq(Py.newInteger(4));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(165);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid option tuple: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
            }

            var1.setline(161);
            var5 = var1.getlocal(1);
            var6 = Py.unpackSequence(var5, 4);
            var7 = var6[0];
            var1.setlocal(2, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[3];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
         }

         var1.setline(168);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__not__();
         if (!var10000.__nonzero__()) {
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var5._lt(Py.newInteger(2));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(169);
            throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("invalid long option '%s': must be a string of length >= 2")._mod(var1.getlocal(2)));
         }

         var1.setline(173);
         var5 = var1.getlocal(3);
         var10000 = var5._is(var1.getglobal("None"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("str"));
            if (var10000.__nonzero__()) {
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
               var10000 = var5._eq(Py.newInteger(1));
               var5 = null;
            }
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(175);
            throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("invalid short option '%s': must a single character or None")._mod(var1.getlocal(3)));
         }

         var1.setline(179);
         var5 = var1.getlocal(5);
         var1.getlocal(0).__getattr__("repeat").__setitem__(var1.getlocal(2), var5);
         var5 = null;
         var1.setline(180);
         var1.getlocal(0).__getattr__("long_opts").__getattr__("append").__call__(var2, var1.getlocal(2));
         var1.setline(182);
         var5 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         var10000 = var5._eq(PyString.fromInterned("="));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(183);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(183);
               var5 = var1.getlocal(3)._add(PyString.fromInterned(":"));
               var1.setlocal(3, var5);
               var5 = null;
            }

            var1.setline(184);
            var5 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(185);
            var10 = Py.newInteger(1);
            var1.getlocal(0).__getattr__("takes_arg").__setitem__((PyObject)var1.getlocal(2), var10);
            var5 = null;
         } else {
            var1.setline(190);
            var5 = var1.getlocal(0).__getattr__("negative_alias").__getattr__("get").__call__(var2, var1.getlocal(2));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(191);
            var5 = var1.getlocal(6);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(192);
               if (var1.getlocal(0).__getattr__("takes_arg").__getitem__(var1.getlocal(6)).__nonzero__()) {
                  var1.setline(193);
                  throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("invalid negative alias '%s': aliased option '%s' takes a value")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(6)})));
               }

               var1.setline(198);
               var5 = var1.getlocal(2);
               var1.getlocal(0).__getattr__("long_opts").__setitem__((PyObject)Py.newInteger(-1), var5);
               var5 = null;
               var1.setline(199);
               var10 = Py.newInteger(0);
               var1.getlocal(0).__getattr__("takes_arg").__setitem__((PyObject)var1.getlocal(2), var10);
               var5 = null;
            } else {
               var1.setline(202);
               var10 = Py.newInteger(0);
               var1.getlocal(0).__getattr__("takes_arg").__setitem__((PyObject)var1.getlocal(2), var10);
               var5 = null;
            }
         }

         var1.setline(206);
         var5 = var1.getlocal(0).__getattr__("alias").__getattr__("get").__call__(var2, var1.getlocal(2));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(207);
         var5 = var1.getlocal(6);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(208);
            var5 = var1.getlocal(0).__getattr__("takes_arg").__getitem__(var1.getlocal(2));
            var10000 = var5._ne(var1.getlocal(0).__getattr__("takes_arg").__getitem__(var1.getlocal(6)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(209);
               throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("invalid alias '%s': inconsistent with aliased option '%s' (one of them takes a value, the other doesn't")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(6)})));
            }
         }

         var1.setline(219);
         if (var1.getglobal("longopt_re").__getattr__("match").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
            var1.setline(220);
            throw Py.makeException(var1.getglobal("DistutilsGetoptError"), PyString.fromInterned("invalid long option name '%s' ")._add(PyString.fromInterned("(must be letters, numbers, hyphens only"))._mod(var1.getlocal(2)));
         }

         var1.setline(224);
         var5 = var1.getlocal(0).__getattr__("get_attr_name").__call__(var2, var1.getlocal(2));
         var1.getlocal(0).__getattr__("attr_name").__setitem__(var1.getlocal(2), var5);
         var5 = null;
         var1.setline(225);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(226);
            var1.getlocal(0).__getattr__("short_opts").__getattr__("append").__call__(var2, var1.getlocal(3));
            var1.setline(227);
            var5 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("short2long").__setitem__(var1.getlocal(3).__getitem__(Py.newInteger(0)), var5);
            var5 = null;
         }
      }
   }

   public PyObject getopt$12(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyString.fromInterned("Parse command-line options in args. Store as attributes on object.\n\n        If 'args' is None or not supplied, uses 'sys.argv[1:]'.  If\n        'object' is None or not supplied, creates a new OptionDummy\n        object, stores option values there, and returns a tuple (args,\n        object).  If 'object' is supplied, it is modified in place and\n        'getopt()' just returns 'args'; in both cases, the returned\n        'args' is a modified copy of the passed-in 'args' list, which\n        is left untouched.\n        ");
      var1.setline(245);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(246);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(247);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var9;
      if (var10000.__nonzero__()) {
         var1.setline(248);
         var3 = var1.getglobal("OptionDummy").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(249);
         var9 = Py.newInteger(1);
         var1.setlocal(3, var9);
         var3 = null;
      } else {
         var1.setline(251);
         var9 = Py.newInteger(0);
         var1.setlocal(3, var9);
         var3 = null;
      }

      var1.setline(253);
      var1.getlocal(0).__getattr__("_grok_option_table").__call__(var2);
      var1.setline(255);
      var3 = var1.getglobal("string").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("short_opts"));
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var4;
      PyObject var5;
      try {
         var1.setline(257);
         var3 = var1.getglobal("getopt").__getattr__("getopt").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(0).__getattr__("long_opts"));
         PyObject[] var8 = Py.unpackSequence(var3, 2);
         var5 = var8[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         PyException var11 = Py.setException(var7, var1);
         if (var11.match(var1.getglobal("getopt").__getattr__("error"))) {
            var4 = var11.value;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(259);
            throw Py.makeException(var1.getglobal("DistutilsArgError"), var1.getlocal(6));
         }

         throw var11;
      }

      var1.setline(261);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(261);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(290);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(291);
               PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var12;
            }

            var1.setline(293);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var10 = Py.unpackSequence(var4, 2);
         PyObject var6 = var10[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(262);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
         var10000 = var5._eq(Py.newInteger(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(PyString.fromInterned("-"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(263);
            var5 = var1.getlocal(0).__getattr__("short2long").__getitem__(var1.getlocal(7).__getitem__(Py.newInteger(1)));
            var1.setlocal(7, var5);
            var5 = null;
         } else {
            var1.setline(265);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var10000 = var5._gt(Py.newInteger(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
                  var10000 = var5._eq(PyString.fromInterned("--"));
                  var5 = null;
               }

               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(266);
            var5 = var1.getlocal(7).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(7, var5);
            var5 = null;
         }

         var1.setline(268);
         var5 = var1.getlocal(0).__getattr__("alias").__getattr__("get").__call__(var2, var1.getlocal(7));
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(269);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(270);
            var5 = var1.getlocal(9);
            var1.setlocal(7, var5);
            var5 = null;
         }

         var1.setline(272);
         if (var1.getlocal(0).__getattr__("takes_arg").__getitem__(var1.getlocal(7)).__not__().__nonzero__()) {
            var1.setline(273);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var5 = var1.getlocal(8);
               var10000 = var5._eq(PyString.fromInterned(""));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("boolean option can't have value"));
               }
            }

            var1.setline(274);
            var5 = var1.getlocal(0).__getattr__("negative_alias").__getattr__("get").__call__(var2, var1.getlocal(7));
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(275);
            PyInteger var13;
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(276);
               var5 = var1.getlocal(9);
               var1.setlocal(7, var5);
               var5 = null;
               var1.setline(277);
               var13 = Py.newInteger(0);
               var1.setlocal(8, var13);
               var5 = null;
            } else {
               var1.setline(279);
               var13 = Py.newInteger(1);
               var1.setlocal(8, var13);
               var5 = null;
            }
         }

         var1.setline(281);
         var5 = var1.getlocal(0).__getattr__("attr_name").__getitem__(var1.getlocal(7));
         var1.setlocal(10, var5);
         var5 = null;
         var1.setline(284);
         var10000 = var1.getlocal(8);
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(0).__getattr__("repeat").__getattr__("get").__call__(var2, var1.getlocal(10));
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(285);
            var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(10), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
            var1.setlocal(8, var5);
            var5 = null;
         }

         var1.setline(286);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(10), var1.getlocal(8));
         var1.setline(287);
         var1.getlocal(0).__getattr__("option_order").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)})));
      }
   }

   public PyObject get_option_order$13(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyString.fromInterned("Returns the list of (option, value) tuples processed by the\n        previous run of 'getopt()'.  Raises RuntimeError if\n        'getopt()' hasn't been called yet.\n        ");
      var1.setline(303);
      PyObject var3 = var1.getlocal(0).__getattr__("option_order");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(304);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("'getopt()' hasn't been called yet"));
      } else {
         var1.setline(306);
         var3 = var1.getlocal(0).__getattr__("option_order");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject generate_help$14(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      PyString.fromInterned("Generate help text (a list of strings, one per suggested line of\n        output) from the option table for this FancyGetopt object.\n        ");
      var1.setline(317);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(318);
      PyObject var8 = var1.getlocal(0).__getattr__("option_table").__iter__();

      while(true) {
         var1.setline(318);
         PyObject var4 = var8.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(329);
            var8 = var1.getlocal(2)._add(Py.newInteger(2))._add(Py.newInteger(2))._add(Py.newInteger(2));
            var1.setlocal(7, var8);
            var3 = null;
            var1.setline(353);
            var3 = Py.newInteger(78);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(354);
            var8 = var1.getlocal(8)._sub(var1.getlocal(7));
            var1.setlocal(9, var8);
            var3 = null;
            var1.setline(355);
            var8 = PyString.fromInterned(" ")._mul(var1.getlocal(7));
            var1.setlocal(10, var8);
            var3 = null;
            var1.setline(356);
            PyList var10;
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(357);
               var10 = new PyList(new PyObject[]{var1.getlocal(1)});
               var1.setlocal(11, var10);
               var3 = null;
            } else {
               var1.setline(359);
               var10 = new PyList(new PyObject[]{PyString.fromInterned("Option summary:")});
               var1.setlocal(11, var10);
               var3 = null;
            }

            var1.setline(361);
            var8 = var1.getlocal(0).__getattr__("option_table").__iter__();

            while(true) {
               var1.setline(361);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.setline(389);
                  var8 = var1.getlocal(11);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setlocal(3, var4);
               var1.setline(362);
               var5 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
               PyObject[] var6 = Py.unpackSequence(var5, 3);
               PyObject var7 = var6[0];
               var1.setlocal(4, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(5, var7);
               var7 = null;
               var7 = var6[2];
               var1.setlocal(12, var7);
               var7 = null;
               var5 = null;
               var1.setline(363);
               var5 = var1.getglobal("wrap_text").__call__(var2, var1.getlocal(12), var1.getlocal(9));
               var1.setlocal(13, var5);
               var5 = null;
               var1.setline(364);
               var5 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
               var10000 = var5._eq(PyString.fromInterned("="));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(365);
                  var5 = var1.getlocal(4).__getslice__(Py.newInteger(0), Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(4, var5);
                  var5 = null;
               }

               var1.setline(368);
               var5 = var1.getlocal(5);
               var10000 = var5._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(369);
                  if (var1.getlocal(13).__nonzero__()) {
                     var1.setline(370);
                     var1.getlocal(11).__getattr__("append").__call__(var2, PyString.fromInterned("  --%-*s  %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(13).__getitem__(Py.newInteger(0))})));
                  } else {
                     var1.setline(372);
                     var1.getlocal(11).__getattr__("append").__call__(var2, PyString.fromInterned("  --%-*s  ")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)})));
                  }
               } else {
                  var1.setline(377);
                  var5 = PyString.fromInterned("%s (-%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
                  var1.setlocal(14, var5);
                  var5 = null;
                  var1.setline(378);
                  if (var1.getlocal(13).__nonzero__()) {
                     var1.setline(379);
                     var1.getlocal(11).__getattr__("append").__call__(var2, PyString.fromInterned("  --%-*s  %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(14), var1.getlocal(13).__getitem__(Py.newInteger(0))})));
                  } else {
                     var1.setline(382);
                     var1.getlocal(11).__getattr__("append").__call__(var2, PyString.fromInterned("  --%-*s")._mod(var1.getlocal(14)));
                  }
               }

               var1.setline(384);
               var5 = var1.getlocal(13).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

               while(true) {
                  var1.setline(384);
                  PyObject var9 = var5.__iternext__();
                  if (var9 == null) {
                     break;
                  }

                  var1.setlocal(6, var9);
                  var1.setline(385);
                  var1.getlocal(11).__getattr__("append").__call__(var2, var1.getlocal(10)._add(var1.getlocal(6)));
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(319);
         var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(320);
         var5 = var1.getlocal(3).__getitem__(Py.newInteger(1));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(321);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(322);
         var5 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
         var10000 = var5._eq(PyString.fromInterned("="));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(323);
            var5 = var1.getlocal(6)._sub(Py.newInteger(1));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(324);
         var5 = var1.getlocal(5);
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(325);
            var5 = var1.getlocal(6)._add(Py.newInteger(5));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(326);
         var5 = var1.getlocal(6);
         var10000 = var5._gt(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(327);
            var5 = var1.getlocal(6);
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject print_help$15(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(395);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(396);
      var3 = var1.getlocal(0).__getattr__("generate_help").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(396);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(397);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("\n")));
      }
   }

   public PyObject fancy_getopt$16(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyObject var3 = var1.getglobal("FancyGetopt").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(404);
      var1.getlocal(4).__getattr__("set_negative_aliases").__call__(var2, var1.getlocal(1));
      var1.setline(405);
      var3 = var1.getlocal(4).__getattr__("getopt").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wrap_text$17(PyFrame var1, ThreadState var2) {
      var1.setline(415);
      PyString.fromInterned("wrap_text(text : string, width : int) -> [string]\n\n    Split 'text' into multiple lines of no more than 'width' characters\n    each, and return the list of strings that results.\n    ");
      var1.setline(417);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyList var5;
      if (var10000.__nonzero__()) {
         var1.setline(418);
         var5 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(419);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var4._le(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(420);
            var5 = new PyList(new PyObject[]{var1.getlocal(0)});
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(422);
            var4 = var1.getglobal("string").__getattr__("expandtabs").__call__(var2, var1.getlocal(0));
            var1.setlocal(0, var4);
            var4 = null;
            var1.setline(423);
            var4 = var1.getglobal("string").__getattr__("translate").__call__(var2, var1.getlocal(0), var1.getglobal("WS_TRANS"));
            var1.setlocal(0, var4);
            var4 = null;
            var1.setline(424);
            var4 = var1.getglobal("re").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("( +|-+)"), (PyObject)var1.getlocal(0));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(425);
            var4 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(2));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(426);
            PyList var6 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var6);
            var4 = null;

            while(true) {
               var1.setline(428);
               if (!var1.getlocal(2).__nonzero__()) {
                  var1.setline(466);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(430);
               var6 = new PyList(Py.EmptyObjects);
               var1.setlocal(4, var6);
               var4 = null;
               var1.setline(431);
               PyInteger var7 = Py.newInteger(0);
               var1.setlocal(5, var7);
               var4 = null;

               while(true) {
                  var1.setline(433);
                  if (!var1.getlocal(2).__nonzero__()) {
                     break;
                  }

                  var1.setline(434);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(435);
                  var4 = var1.getlocal(5)._add(var1.getlocal(6));
                  var10000 = var4._le(var1.getlocal(1));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(441);
                     var10000 = var1.getlocal(4);
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(4).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
                        var10000 = var4._eq(PyString.fromInterned(" "));
                        var4 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(442);
                        var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
                     }
                     break;
                  }

                  var1.setline(436);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
                  var1.setline(437);
                  var1.getlocal(2).__delitem__((PyObject)Py.newInteger(0));
                  var1.setline(438);
                  var4 = var1.getlocal(5)._add(var1.getlocal(6));
                  var1.setlocal(5, var4);
                  var4 = null;
               }

               var1.setline(445);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(450);
                  var4 = var1.getlocal(5);
                  var10000 = var4._eq(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(451);
                     var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(0), var1.getlocal(1), (PyObject)null));
                     var1.setline(452);
                     var4 = var1.getlocal(2).__getitem__(Py.newInteger(0)).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
                     var1.getlocal(2).__setitem__((PyObject)Py.newInteger(0), var4);
                     var4 = null;
                  }

                  var1.setline(457);
                  var4 = var1.getlocal(2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
                  var10000 = var4._eq(PyString.fromInterned(" "));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(458);
                     var1.getlocal(2).__delitem__((PyObject)Py.newInteger(0));
                  }
               }

               var1.setline(462);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("")));
            }
         }
      }
   }

   public PyObject translate_longopt$18(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyString.fromInterned("Convert a long option name to a valid Python identifier by\n    changing \"-\" to \"_\".\n    ");
      var1.setline(473);
      PyObject var3 = var1.getglobal("string").__getattr__("translate").__call__(var2, var1.getlocal(0), var1.getglobal("longopt_xlate"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OptionDummy$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Dummy class just used as a place to hold command-line option\n    values as instance attributes."));
      var1.setline(478);
      PyString.fromInterned("Dummy class just used as a place to hold command-line option\n    values as instance attributes.");
      var1.setline(480);
      PyObject[] var3 = new PyObject[]{new PyList(Py.EmptyObjects)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, PyString.fromInterned("Create a new OptionDummy instance.  The attributes listed in\n        'options' will be initialized to None."));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(482);
      PyString.fromInterned("Create a new OptionDummy instance.  The attributes listed in\n        'options' will be initialized to None.");
      var1.setline(483);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(483);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(484);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getglobal("None"));
      }
   }

   public fancy_getopt$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FancyGetopt$1 = Py.newCode(0, var2, var1, "FancyGetopt", 33, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_table"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 45, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option"};
      _build_index$3 = Py.newCode(1, var2, var1, "_build_index", 89, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option_table"};
      set_option_table$4 = Py.newCode(2, var2, var1, "set_option_table", 94, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "long_option", "short_option", "help_string", "option"};
      add_option$5 = Py.newCode(4, var2, var1, "add_option", 98, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "long_option"};
      has_option$6 = Py.newCode(2, var2, var1, "has_option", 108, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "long_option"};
      get_attr_name$7 = Py.newCode(2, var2, var1, "get_attr_name", 113, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "aliases", "what", "alias", "opt"};
      _check_alias_dict$8 = Py.newCode(3, var2, var1, "_check_alias_dict", 120, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "alias"};
      set_aliases$9 = Py.newCode(2, var2, var1, "set_aliases", 132, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "negative_alias"};
      set_negative_aliases$10 = Py.newCode(2, var2, var1, "set_negative_aliases", 137, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "long", "short", "help", "repeat", "alias_to"};
      _grok_option_table$11 = Py.newCode(1, var2, var1, "_grok_option_table", 146, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "object", "created_object", "short_opts", "opts", "msg", "opt", "val", "alias", "attr"};
      getopt$12 = Py.newCode(3, var2, var1, "getopt", 234, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_option_order$13 = Py.newCode(1, var2, var1, "get_option_order", 298, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "max_opt", "option", "long", "short", "l", "opt_width", "line_width", "text_width", "big_indent", "lines", "help", "text", "opt_names"};
      generate_help$14 = Py.newCode(2, var2, var1, "generate_help", 309, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "file", "line"};
      print_help$15 = Py.newCode(3, var2, var1, "print_help", 393, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"options", "negative_opt", "object", "args", "parser"};
      fancy_getopt$16 = Py.newCode(4, var2, var1, "fancy_getopt", 402, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "width", "chunks", "lines", "cur_line", "cur_len", "l"};
      wrap_text$17 = Py.newCode(2, var2, var1, "wrap_text", 410, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"opt"};
      translate_longopt$18 = Py.newCode(1, var2, var1, "translate_longopt", 469, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OptionDummy$19 = Py.newCode(0, var2, var1, "OptionDummy", 476, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "options", "opt"};
      __init__$20 = Py.newCode(2, var2, var1, "__init__", 480, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fancy_getopt$py("distutils/fancy_getopt$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fancy_getopt$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FancyGetopt$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._build_index$3(var2, var3);
         case 4:
            return this.set_option_table$4(var2, var3);
         case 5:
            return this.add_option$5(var2, var3);
         case 6:
            return this.has_option$6(var2, var3);
         case 7:
            return this.get_attr_name$7(var2, var3);
         case 8:
            return this._check_alias_dict$8(var2, var3);
         case 9:
            return this.set_aliases$9(var2, var3);
         case 10:
            return this.set_negative_aliases$10(var2, var3);
         case 11:
            return this._grok_option_table$11(var2, var3);
         case 12:
            return this.getopt$12(var2, var3);
         case 13:
            return this.get_option_order$13(var2, var3);
         case 14:
            return this.generate_help$14(var2, var3);
         case 15:
            return this.print_help$15(var2, var3);
         case 16:
            return this.fancy_getopt$16(var2, var3);
         case 17:
            return this.wrap_text$17(var2, var3);
         case 18:
            return this.translate_longopt$18(var2, var3);
         case 19:
            return this.OptionDummy$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         default:
            return null;
      }
   }
}
