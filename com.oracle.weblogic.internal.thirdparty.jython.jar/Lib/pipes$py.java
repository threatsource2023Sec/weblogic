import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("pipes.py")
public class pipes$py extends PyFunctionTable implements PyRunnable {
   static pipes$py self;
   static final PyCode f$0;
   static final PyCode Template$1;
   static final PyCode __init__$2;
   static final PyCode __repr__$3;
   static final PyCode reset$4;
   static final PyCode clone$5;
   static final PyCode debug$6;
   static final PyCode append$7;
   static final PyCode prepend$8;
   static final PyCode open$9;
   static final PyCode open_r$10;
   static final PyCode open_w$11;
   static final PyCode copy$12;
   static final PyCode makepipeline$13;
   static final PyCode makepipeline$14;
   static final PyCode quote$15;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Conversion pipeline templates.\n\nThe problem:\n------------\n\nSuppose you have some data that you want to convert to another format,\nsuch as from GIF image format to PPM image format.  Maybe the\nconversion involves several steps (e.g. piping it through compress or\nuuencode).  Some of the conversion steps may require that their input\nis a disk file, others may be able to read standard input; similar for\ntheir output.  The input to the entire conversion may also be read\nfrom a disk file or from an open file, and similar for its output.\n\nThe module lets you construct a pipeline template by sticking one or\nmore conversion steps together.  It will take care of creating and\nremoving temporary files if they are necessary to hold intermediate\ndata.  You can then use the template to do conversions from many\ndifferent sources to many different destinations.  The temporary\nfile names used are different each time the template is used.\n\nThe templates are objects so you can create templates for many\ndifferent conversion steps and store them in a dictionary, for\ninstance.\n\n\nDirections:\n-----------\n\nTo create a template:\n    t = Template()\n\nTo add a conversion step to a template:\n   t.append(command, kind)\nwhere kind is a string of two characters: the first is '-' if the\ncommand reads its standard input or 'f' if it requires a file; the\nsecond likewise for the output. The command must be valid /bin/sh\nsyntax.  If input or output files are required, they are passed as\n$IN and $OUT; otherwise, it must be  possible to use the command in\na pipeline.\n\nTo add a conversion step at the beginning:\n   t.prepend(command, kind)\n\nTo convert a file to another file using a template:\n  sts = t.copy(infile, outfile)\nIf infile or outfile are the empty string, standard input is read or\nstandard output is written, respectively.  The return value is the\nexit status of the conversion pipeline.\n\nTo open a file for reading or writing through a conversion pipeline:\n   fp = t.open(file, mode)\nwhere mode is 'r' to read the file, or 'w' to write it -- just like\nfor the built-in function open() or for os.popen().\n\nTo create a new template object initialized to a given one:\n   t2 = t.clone()\n"));
      var1.setline(57);
      PyString.fromInterned("Conversion pipeline templates.\n\nThe problem:\n------------\n\nSuppose you have some data that you want to convert to another format,\nsuch as from GIF image format to PPM image format.  Maybe the\nconversion involves several steps (e.g. piping it through compress or\nuuencode).  Some of the conversion steps may require that their input\nis a disk file, others may be able to read standard input; similar for\ntheir output.  The input to the entire conversion may also be read\nfrom a disk file or from an open file, and similar for its output.\n\nThe module lets you construct a pipeline template by sticking one or\nmore conversion steps together.  It will take care of creating and\nremoving temporary files if they are necessary to hold intermediate\ndata.  You can then use the template to do conversions from many\ndifferent sources to many different destinations.  The temporary\nfile names used are different each time the template is used.\n\nThe templates are objects so you can create templates for many\ndifferent conversion steps and store them in a dictionary, for\ninstance.\n\n\nDirections:\n-----------\n\nTo create a template:\n    t = Template()\n\nTo add a conversion step to a template:\n   t.append(command, kind)\nwhere kind is a string of two characters: the first is '-' if the\ncommand reads its standard input or 'f' if it requires a file; the\nsecond likewise for the output. The command must be valid /bin/sh\nsyntax.  If input or output files are required, they are passed as\n$IN and $OUT; otherwise, it must be  possible to use the command in\na pipeline.\n\nTo add a conversion step at the beginning:\n   t.prepend(command, kind)\n\nTo convert a file to another file using a template:\n  sts = t.copy(infile, outfile)\nIf infile or outfile are the empty string, standard input is read or\nstandard output is written, respectively.  The return value is the\nexit status of the conversion pipeline.\n\nTo open a file for reading or writing through a conversion pipeline:\n   fp = t.open(file, mode)\nwhere mode is 'r' to read the file, or 'w' to write it -- just like\nfor the built-in function open() or for os.popen().\n\nTo create a new template object initialized to a given one:\n   t2 = t.clone()\n");
      var1.setline(60);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(61);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(62);
      var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var3);
      var3 = null;
      var1.setline(63);
      var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;
      var1.setline(65);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("Template")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(69);
      PyString var6 = PyString.fromInterned("ff");
      var1.setlocal("FILEIN_FILEOUT", var6);
      var3 = null;
      var1.setline(70);
      var6 = PyString.fromInterned("-f");
      var1.setlocal("STDIN_FILEOUT", var6);
      var3 = null;
      var1.setline(71);
      var6 = PyString.fromInterned("f-");
      var1.setlocal("FILEIN_STDOUT", var6);
      var3 = null;
      var1.setline(72);
      var6 = PyString.fromInterned("--");
      var1.setlocal("STDIN_STDOUT", var6);
      var3 = null;
      var1.setline(73);
      var6 = PyString.fromInterned(".-");
      var1.setlocal("SOURCE", var6);
      var3 = null;
      var1.setline(74);
      var6 = PyString.fromInterned("-.");
      var1.setlocal("SINK", var6);
      var3 = null;
      var1.setline(76);
      var5 = new PyList(new PyObject[]{var1.getname("FILEIN_FILEOUT"), var1.getname("STDIN_FILEOUT"), var1.getname("FILEIN_STDOUT"), var1.getname("STDIN_STDOUT"), var1.getname("SOURCE"), var1.getname("SINK")});
      var1.setlocal("stepkinds", var5);
      var3 = null;
      var1.setline(80);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Template", var7, Template$1);
      var1.setlocal("Template", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(193);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, makepipeline$14, (PyObject)null);
      var1.setlocal("makepipeline", var8);
      var3 = null;
      var1.setline(265);
      var3 = var1.getname("frozenset").__call__(var2, var1.getname("string").__getattr__("ascii_letters")._add(var1.getname("string").__getattr__("digits"))._add(PyString.fromInterned("@%_-+=:,./")));
      var1.setlocal("_safechars", var3);
      var3 = null;
      var1.setline(267);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, quote$15, PyString.fromInterned("Return a shell-escaped version of the file string."));
      var1.setlocal("quote", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Template$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class representing a pipeline template."));
      var1.setline(81);
      PyString.fromInterned("Class representing a pipeline template.");
      var1.setline(83);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Template() returns a fresh pipeline template."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, PyString.fromInterned("t.__repr__() implements repr(t)."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(92);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$4, PyString.fromInterned("t.reset() restores a pipeline template to its initial state."));
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clone$5, PyString.fromInterned("t.clone() returns a new pipeline template with identical\n        initial state as the current one."));
      var1.setlocal("clone", var4);
      var3 = null;
      var1.setline(104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug$6, PyString.fromInterned("t.debug(flag) turns debugging on or off."));
      var1.setlocal("debug", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$7, PyString.fromInterned("t.append(cmd, kind) adds a new step at the end."));
      var1.setlocal("append", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, prepend$8, PyString.fromInterned("t.prepend(cmd, kind) adds a new step at the front."));
      var1.setlocal("prepend", var4);
      var3 = null;
      var1.setline(152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, open$9, PyString.fromInterned("t.open(file, rw) returns a pipe or file object open for\n        reading or writing; the file is the other end of the pipeline."));
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(162);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, open_r$10, PyString.fromInterned("t.open_r(file) and t.open_w(file) implement\n        t.open(file, 'r') and t.open(file, 'w') respectively."));
      var1.setlocal("open_r", var4);
      var3 = null;
      var1.setline(173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, open_w$11, (PyObject)null);
      var1.setlocal("open_w", var4);
      var3 = null;
      var1.setline(182);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy$12, (PyObject)null);
      var1.setlocal("copy", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, makepipeline$13, (PyObject)null);
      var1.setlocal("makepipeline", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyString.fromInterned("Template() returns a fresh pipeline template.");
      var1.setline(85);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"debugging", var3);
      var3 = null;
      var1.setline(86);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("t.__repr__() implements repr(t).");
      var1.setline(90);
      PyObject var3 = PyString.fromInterned("<Template instance, steps=%r>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("steps")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$4(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyString.fromInterned("t.reset() restores a pipeline template to its initial state.");
      var1.setline(94);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"steps", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clone$5(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyString.fromInterned("t.clone() returns a new pipeline template with identical\n        initial state as the current one.");
      var1.setline(99);
      PyObject var3 = var1.getglobal("Template").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(0).__getattr__("steps").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(1).__setattr__("steps", var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getlocal(0).__getattr__("debugging");
      var1.getlocal(1).__setattr__("debugging", var3);
      var3 = null;
      var1.setline(102);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject debug$6(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyString.fromInterned("t.debug(flag) turns debugging on or off.");
      var1.setline(106);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("debugging", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject append$7(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("t.append(cmd, kind) adds a new step at the end.");
      var1.setline(110);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._isnot(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(111);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("Template.append: cmd must be a string"));
      } else {
         var1.setline(113);
         var3 = var1.getlocal(2);
         var10000 = var3._notin(var1.getglobal("stepkinds"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(114);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.append: bad kind %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
         } else {
            var1.setline(116);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("SOURCE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(117);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.append: SOURCE can only be prepended"));
            } else {
               var1.setline(119);
               var10000 = var1.getlocal(0).__getattr__("steps");
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("steps").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
                  var10000 = var3._eq(var1.getglobal("SINK"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(120);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.append: already ends with SINK"));
               } else {
                  var1.setline(122);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var10000 = var3._eq(PyString.fromInterned("f"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\$IN\\b"), (PyObject)var1.getlocal(1)).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(123);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.append: missing $IN in cmd"));
                  } else {
                     var1.setline(125);
                     var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
                     var10000 = var3._eq(PyString.fromInterned("f"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\$OUT\\b"), (PyObject)var1.getlocal(1)).__not__();
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(126);
                        throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.append: missing $OUT in cmd"));
                     } else {
                        var1.setline(128);
                        var1.getlocal(0).__getattr__("steps").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
                        var1.f_lasti = -1;
                        return Py.None;
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject prepend$8(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("t.prepend(cmd, kind) adds a new step at the front.");
      var1.setline(132);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._isnot(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(133);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("Template.prepend: cmd must be a string"));
      } else {
         var1.setline(135);
         var3 = var1.getlocal(2);
         var10000 = var3._notin(var1.getglobal("stepkinds"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(136);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.prepend: bad kind %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
         } else {
            var1.setline(138);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("SINK"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(139);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.prepend: SINK can only be appended"));
            } else {
               var1.setline(141);
               var10000 = var1.getlocal(0).__getattr__("steps");
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("steps").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
                  var10000 = var3._eq(var1.getglobal("SOURCE"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(142);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.prepend: already begins with SOURCE"));
               } else {
                  var1.setline(144);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var10000 = var3._eq(PyString.fromInterned("f"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\$IN\\b"), (PyObject)var1.getlocal(1)).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(145);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.prepend: missing $IN in cmd"));
                  } else {
                     var1.setline(147);
                     var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
                     var10000 = var3._eq(PyString.fromInterned("f"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\$OUT\\b"), (PyObject)var1.getlocal(1)).__not__();
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(148);
                        throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.prepend: missing $OUT in cmd"));
                     } else {
                        var1.setline(150);
                        var1.getlocal(0).__getattr__("steps").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
                        var1.f_lasti = -1;
                        return Py.None;
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject open$9(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyString.fromInterned("t.open(file, rw) returns a pipe or file object open for\n        reading or writing; the file is the other end of the pipeline.");
      var1.setline(155);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("r"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(156);
         var3 = var1.getlocal(0).__getattr__("open_r").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(157);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("w"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(158);
            var3 = var1.getlocal(0).__getattr__("open_w").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(159);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.open: rw must be 'r' or 'w', not %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
         }
      }
   }

   public PyObject open_r$10(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyString.fromInterned("t.open_r(file) and t.open_w(file) implement\n        t.open(file, 'r') and t.open(file, 'w') respectively.");
      var1.setline(165);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("steps").__not__().__nonzero__()) {
         var1.setline(166);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("r"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(167);
         PyObject var4 = var1.getlocal(0).__getattr__("steps").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(1));
         PyObject var10000 = var4._eq(var1.getglobal("SINK"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(168);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.open_r: pipeline ends width SINK"));
         } else {
            var1.setline(170);
            var4 = var1.getlocal(0).__getattr__("makepipeline").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned(""));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(171);
            var3 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("r"));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject open_w$11(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("steps").__not__().__nonzero__()) {
         var1.setline(175);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(176);
         PyObject var4 = var1.getlocal(0).__getattr__("steps").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
         PyObject var10000 = var4._eq(var1.getglobal("SOURCE"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(177);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Template.open_w: pipeline begins with SOURCE"));
         } else {
            var1.setline(179);
            var4 = var1.getlocal(0).__getattr__("makepipeline").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(180);
            var3 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("w"));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject copy$12(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      PyObject var3 = var1.getglobal("os").__getattr__("system").__call__(var2, var1.getlocal(0).__getattr__("makepipeline").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makepipeline$13(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getglobal("makepipeline").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("steps"), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(187);
      if (var1.getlocal(0).__getattr__("debugging").__nonzero__()) {
         var1.setline(188);
         Py.println(var1.getlocal(3));
         var1.setline(189);
         var3 = PyString.fromInterned("set -x; ")._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(190);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makepipeline$14(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(198);
      PyObject var8 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(198);
         PyObject var4 = var8.__iternext__();
         PyObject[] var5;
         if (var4 == null) {
            var1.setline(203);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(204);
               var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("cat"), PyString.fromInterned("--"), PyString.fromInterned("")})));
            }

            var1.setline(208);
            var8 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(1), Py.newInteger(3), (PyObject)null);
            PyObject[] var9 = Py.unpackSequence(var8, 2);
            PyObject var10 = var9[0];
            var1.setlocal(4, var10);
            var5 = null;
            var10 = var9[1];
            var1.setlocal(5, var10);
            var5 = null;
            var3 = null;
            var1.setline(209);
            var8 = var1.getlocal(5).__getitem__(Py.newInteger(0));
            PyObject var10000 = var8._eq(PyString.fromInterned("f"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(210);
               var1.getlocal(3).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("cat"), PyString.fromInterned("--"), PyString.fromInterned("")})));
            }

            var1.setline(211);
            var8 = var1.getlocal(0);
            var1.getlocal(3).__getitem__(Py.newInteger(0)).__setitem__((PyObject)Py.newInteger(0), var8);
            var3 = null;
            var1.setline(213);
            var8 = var1.getlocal(3).__getitem__(Py.newInteger(-1)).__getslice__(Py.newInteger(1), Py.newInteger(3), (PyObject)null);
            var9 = Py.unpackSequence(var8, 2);
            var10 = var9[0];
            var1.setlocal(4, var10);
            var5 = null;
            var10 = var9[1];
            var1.setlocal(5, var10);
            var5 = null;
            var3 = null;
            var1.setline(214);
            var8 = var1.getlocal(5).__getitem__(Py.newInteger(1));
            var10000 = var8._eq(PyString.fromInterned("f"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(215);
               var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("cat"), PyString.fromInterned("--"), PyString.fromInterned("")})));
            }

            var1.setline(216);
            var8 = var1.getlocal(2);
            var1.getlocal(3).__getitem__(Py.newInteger(-1)).__setitem__((PyObject)Py.newInteger(-1), var8);
            var3 = null;
            var1.setline(220);
            var3 = new PyList(Py.EmptyObjects);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(221);
            var8 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3))).__iter__();

            while(true) {
               var1.setline(221);
               var4 = var8.__iternext__();
               PyObject var7;
               PyObject[] var11;
               if (var4 == null) {
                  var1.setline(230);
                  var8 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(230);
                     var4 = var8.__iternext__();
                     if (var4 == null) {
                        var1.setline(242);
                        var8 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
                        var1.setlocal(15, var8);
                        var3 = null;
                        var1.setline(243);
                        var8 = var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

                        while(true) {
                           var1.setline(243);
                           var4 = var8.__iternext__();
                           if (var4 == null) {
                              var1.setline(252);
                              if (var1.getlocal(6).__nonzero__()) {
                                 var1.setline(253);
                                 PyString var12 = PyString.fromInterned("rm -f");
                                 var1.setlocal(16, var12);
                                 var3 = null;
                                 var1.setline(254);
                                 var8 = var1.getlocal(6).__iter__();

                                 while(true) {
                                    var1.setline(254);
                                    var4 = var8.__iternext__();
                                    if (var4 == null) {
                                       var1.setline(256);
                                       var8 = PyString.fromInterned("trap ")._add(var1.getglobal("quote").__call__(var2, var1.getlocal(16)._add(PyString.fromInterned("; exit"))))._add(PyString.fromInterned(" 1 2 3 13 14 15"));
                                       var1.setlocal(18, var8);
                                       var3 = null;
                                       var1.setline(257);
                                       var8 = var1.getlocal(18)._add(PyString.fromInterned("\n"))._add(var1.getlocal(15))._add(PyString.fromInterned("\n"))._add(var1.getlocal(16));
                                       var1.setlocal(15, var8);
                                       var3 = null;
                                       break;
                                    }

                                    var1.setlocal(17, var4);
                                    var1.setline(255);
                                    var10 = var1.getlocal(16)._add(PyString.fromInterned(" "))._add(var1.getglobal("quote").__call__(var2, var1.getlocal(17)));
                                    var1.setlocal(16, var10);
                                    var5 = null;
                                 }
                              }

                              var1.setline(259);
                              var8 = var1.getlocal(15);
                              var1.f_lasti = -1;
                              return var8;
                           }

                           var1.setlocal(12, var4);
                           var1.setline(244);
                           var10 = var1.getlocal(12).__getslice__(Py.newInteger(1), Py.newInteger(3), (PyObject)null);
                           var11 = Py.unpackSequence(var10, 2);
                           var7 = var11[0];
                           var1.setlocal(4, var7);
                           var7 = null;
                           var7 = var11[1];
                           var1.setlocal(5, var7);
                           var7 = null;
                           var5 = null;
                           var1.setline(245);
                           var10 = var1.getlocal(12).__getitem__(Py.newInteger(0));
                           var10000 = var10._eq(PyString.fromInterned(""));
                           var5 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(246);
                              PyString var13 = PyString.fromInterned("f");
                              var10000 = var13._in(var1.getlocal(5));
                              var5 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(247);
                                 var10 = PyString.fromInterned("{ ")._add(var1.getlocal(4))._add(PyString.fromInterned("; }"));
                                 var1.setlocal(4, var10);
                                 var5 = null;
                              }

                              var1.setline(248);
                              var10 = var1.getlocal(15)._add(PyString.fromInterned(" |\n"))._add(var1.getlocal(4));
                              var1.setlocal(15, var10);
                              var5 = null;
                           } else {
                              var1.setline(250);
                              var10 = var1.getlocal(15)._add(PyString.fromInterned("\n"))._add(var1.getlocal(4));
                              var1.setlocal(15, var10);
                              var5 = null;
                           }
                        }
                     }

                     var1.setlocal(12, var4);
                     var1.setline(231);
                     var10 = var1.getlocal(12);
                     var11 = Py.unpackSequence(var10, 4);
                     var7 = var11[0];
                     var1.setlocal(13, var7);
                     var7 = null;
                     var7 = var11[1];
                     var1.setlocal(4, var7);
                     var7 = null;
                     var7 = var11[2];
                     var1.setlocal(5, var7);
                     var7 = null;
                     var7 = var11[3];
                     var1.setlocal(14, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(232);
                     var10 = var1.getlocal(5).__getitem__(Py.newInteger(1));
                     var10000 = var10._eq(PyString.fromInterned("f"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(233);
                        var10 = PyString.fromInterned("OUT=")._add(var1.getglobal("quote").__call__(var2, var1.getlocal(14)))._add(PyString.fromInterned("; "))._add(var1.getlocal(4));
                        var1.setlocal(4, var10);
                        var5 = null;
                     }

                     var1.setline(234);
                     var10 = var1.getlocal(5).__getitem__(Py.newInteger(0));
                     var10000 = var10._eq(PyString.fromInterned("f"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(235);
                        var10 = PyString.fromInterned("IN=")._add(var1.getglobal("quote").__call__(var2, var1.getlocal(13)))._add(PyString.fromInterned("; "))._add(var1.getlocal(4));
                        var1.setlocal(4, var10);
                        var5 = null;
                     }

                     var1.setline(236);
                     var10 = var1.getlocal(5).__getitem__(Py.newInteger(0));
                     var10000 = var10._eq(PyString.fromInterned("-"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(13);
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(237);
                        var10 = var1.getlocal(4)._add(PyString.fromInterned(" <"))._add(var1.getglobal("quote").__call__(var2, var1.getlocal(13)));
                        var1.setlocal(4, var10);
                        var5 = null;
                     }

                     var1.setline(238);
                     var10 = var1.getlocal(5).__getitem__(Py.newInteger(1));
                     var10000 = var10._eq(PyString.fromInterned("-"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(14);
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(239);
                        var10 = var1.getlocal(4)._add(PyString.fromInterned(" >"))._add(var1.getglobal("quote").__call__(var2, var1.getlocal(14)));
                        var1.setlocal(4, var10);
                        var5 = null;
                     }

                     var1.setline(240);
                     var10 = var1.getlocal(4);
                     var1.getlocal(12).__setitem__((PyObject)Py.newInteger(1), var10);
                     var5 = null;
                  }
               }

               var1.setlocal(7, var4);
               var1.setline(222);
               var10 = var1.getlocal(3).__getitem__(var1.getlocal(7)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(2));
               var1.setlocal(8, var10);
               var5 = null;
               var1.setline(223);
               var10 = var1.getlocal(3).__getitem__(var1.getlocal(7)).__getitem__(Py.newInteger(2));
               var1.setlocal(9, var10);
               var5 = null;
               var1.setline(224);
               var10 = var1.getlocal(8).__getitem__(Py.newInteger(1));
               var10000 = var10._eq(PyString.fromInterned("f"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10 = var1.getlocal(9).__getitem__(Py.newInteger(0));
                  var10000 = var10._eq(PyString.fromInterned("f"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(225);
                  var10 = var1.getglobal("tempfile").__getattr__("mkstemp").__call__(var2);
                  var11 = Py.unpackSequence(var10, 2);
                  var7 = var11[0];
                  var1.setlocal(10, var7);
                  var7 = null;
                  var7 = var11[1];
                  var1.setlocal(11, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(226);
                  var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(10));
                  var1.setline(227);
                  var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(11));
                  var1.setline(228);
                  var10 = var1.getlocal(11);
                  var1.getlocal(3).__getitem__(var1.getlocal(7)._sub(Py.newInteger(1))).__setitem__((PyObject)Py.newInteger(-1), var10);
                  var1.getlocal(3).__getitem__(var1.getlocal(7)).__setitem__((PyObject)Py.newInteger(0), var10);
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(199);
         var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), var1.getlocal(4), var1.getlocal(5), PyString.fromInterned("")})));
      }
   }

   public PyObject quote$15(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyString.fromInterned("Return a shell-escaped version of the file string.");
      var1.setline(269);
      PyObject var3 = var1.getlocal(0).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(269);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(273);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(274);
               PyString var6 = PyString.fromInterned("''");
               var1.f_lasti = -1;
               return var6;
            } else {
               var1.setline(275);
               var5 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var5;
            }
         }

         var1.setlocal(1, var4);
         var1.setline(270);
         var5 = var1.getlocal(1);
         var10000 = var5._notin(var1.getglobal("_safechars"));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(278);
      var5 = PyString.fromInterned("'")._add(var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'"), (PyObject)PyString.fromInterned("'\"'\"'")))._add(PyString.fromInterned("'"));
      var1.f_lasti = -1;
      return var5;
   }

   public pipes$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Template$1 = Py.newCode(0, var2, var1, "Template", 80, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 83, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 88, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$4 = Py.newCode(1, var2, var1, "reset", 92, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "t"};
      clone$5 = Py.newCode(1, var2, var1, "clone", 96, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      debug$6 = Py.newCode(2, var2, var1, "debug", 104, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "kind"};
      append$7 = Py.newCode(3, var2, var1, "append", 108, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "kind"};
      prepend$8 = Py.newCode(3, var2, var1, "prepend", 130, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "rw"};
      open$9 = Py.newCode(3, var2, var1, "open", 152, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "cmd"};
      open_r$10 = Py.newCode(2, var2, var1, "open_r", 162, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "cmd"};
      open_w$11 = Py.newCode(2, var2, var1, "open_w", 173, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "outfile"};
      copy$12 = Py.newCode(3, var2, var1, "copy", 182, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "outfile", "cmd"};
      makepipeline$13 = Py.newCode(3, var2, var1, "makepipeline", 185, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"infile", "steps", "outfile", "list", "cmd", "kind", "garbage", "i", "lkind", "rkind", "fd", "temp", "item", "inf", "outf", "cmdlist", "rmcmd", "file", "trapcmd"};
      makepipeline$14 = Py.newCode(3, var2, var1, "makepipeline", 193, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "c"};
      quote$15 = Py.newCode(1, var2, var1, "quote", 267, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pipes$py("pipes$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pipes$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Template$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.reset$4(var2, var3);
         case 5:
            return this.clone$5(var2, var3);
         case 6:
            return this.debug$6(var2, var3);
         case 7:
            return this.append$7(var2, var3);
         case 8:
            return this.prepend$8(var2, var3);
         case 9:
            return this.open$9(var2, var3);
         case 10:
            return this.open_r$10(var2, var3);
         case 11:
            return this.open_w$11(var2, var3);
         case 12:
            return this.copy$12(var2, var3);
         case 13:
            return this.makepipeline$13(var2, var3);
         case 14:
            return this.makepipeline$14(var2, var3);
         case 15:
            return this.quote$15(var2, var3);
         default:
            return null;
      }
   }
}
