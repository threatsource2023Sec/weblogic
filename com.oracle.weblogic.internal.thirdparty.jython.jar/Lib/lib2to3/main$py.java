package lib2to3;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@Filename("lib2to3/main.py")
public class main$py extends PyFunctionTable implements PyRunnable {
   static main$py self;
   static final PyCode f$0;
   static final PyCode diff_texts$1;
   static final PyCode StdoutRefactoringTool$2;
   static final PyCode __init__$3;
   static final PyCode log_error$4;
   static final PyCode write_file$5;
   static final PyCode print_output$6;
   static final PyCode warn$7;
   static final PyCode main$8;
   static final PyCode f$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nMain program for 2to3.\n"));
      var1.setline(3);
      PyString.fromInterned("\nMain program for 2to3.\n");
      var1.setline(5);
      String[] var3 = new String[]{"with_statement"};
      PyObject[] var5 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("with_statement", var4);
      var4 = null;
      var1.setline(7);
      PyObject var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(8);
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(9);
      var6 = imp.importOne("difflib", var1, -1);
      var1.setlocal("difflib", var6);
      var3 = null;
      var1.setline(10);
      var6 = imp.importOne("logging", var1, -1);
      var1.setlocal("logging", var6);
      var3 = null;
      var1.setline(11);
      var6 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var6);
      var3 = null;
      var1.setline(12);
      var6 = imp.importOne("optparse", var1, -1);
      var1.setlocal("optparse", var6);
      var3 = null;
      var1.setline(14);
      var3 = new String[]{"refactor"};
      var5 = imp.importFrom("", var3, var1, 1);
      var4 = var5[0];
      var1.setlocal("refactor", var4);
      var4 = null;
      var1.setline(17);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, diff_texts$1, PyString.fromInterned("Return a unified diff of two strings."));
      var1.setlocal("diff_texts", var7);
      var3 = null;
      var1.setline(26);
      var5 = new PyObject[]{var1.getname("refactor").__getattr__("MultiprocessRefactoringTool")};
      var4 = Py.makeClass("StdoutRefactoringTool", var5, StdoutRefactoringTool$2);
      var1.setlocal("StdoutRefactoringTool", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(130);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, warn$7, (PyObject)null);
      var1.setlocal("warn", var7);
      var3 = null;
      var1.setline(134);
      var5 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var5, main$8, PyString.fromInterned("Main program.\n\n    Args:\n        fixer_pkg: the name of a package where the fixers are located.\n        args: optional; a list of command line arguments. If omitted,\n              sys.argv[1:] is used.\n\n    Returns a suggested exit status (0, 1, 2).\n    "));
      var1.setlocal("main", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject diff_texts$1(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyString.fromInterned("Return a unified diff of two strings.");
      var1.setline(19);
      PyObject var3 = var1.getlocal(0).__getattr__("splitlines").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(20);
      var3 = var1.getlocal(1).__getattr__("splitlines").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(21);
      PyObject var10000 = var1.getglobal("difflib").__getattr__("unified_diff");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(2), PyString.fromInterned("(original)"), PyString.fromInterned("(refactored)"), PyString.fromInterned("")};
      String[] var4 = new String[]{"lineterm"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StdoutRefactoringTool$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    A refactoring tool that can avoid overwriting its input files.\n    Prints output to stdout.\n\n    Output files can optionally be written to a different directory and or\n    have an extra file suffix appended to their name for use in situations\n    where you do not want to replace the input files.\n    "));
      var1.setline(34);
      PyString.fromInterned("\n    A refactoring tool that can avoid overwriting its input files.\n    Prints output to stdout.\n\n    Output files can optionally be written to a different directory and or\n    have an extra file suffix appended to their name for use in situations\n    where you do not want to replace the input files.\n    ");
      var1.setline(36);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("\n        Args:\n            fixers: A list of fixers to import.\n            options: A dict with RefactoringTool configuration.\n            explicit: A list of fixers to run even if they are explicit.\n            nobackups: If true no backup '.bak' files will be created for those\n                files that are being refactored.\n            show_diffs: Should diffs of the refactoring be printed to stdout?\n            input_base_dir: The base directory for all input files.  This class\n                will strip this path prefix off of filenames before substituting\n                it with output_dir.  Only meaningful if output_dir is supplied.\n                All files processed by refactor() must start with this path.\n            output_dir: If supplied, all converted files will be written into\n                this directory tree instead of input_base_dir.\n            append_suffix: If supplied, all files output by this tool will have\n                this appended to their filename.  Useful for changing .py to\n                .py3 for example by passing append_suffix='3'.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, log_error$4, (PyObject)null);
      var1.setlocal("log_error", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write_file$5, (PyObject)null);
      var1.setlocal("write_file", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_output$6, (PyObject)null);
      var1.setlocal("print_output", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("\n        Args:\n            fixers: A list of fixers to import.\n            options: A dict with RefactoringTool configuration.\n            explicit: A list of fixers to run even if they are explicit.\n            nobackups: If true no backup '.bak' files will be created for those\n                files that are being refactored.\n            show_diffs: Should diffs of the refactoring be printed to stdout?\n            input_base_dir: The base directory for all input files.  This class\n                will strip this path prefix off of filenames before substituting\n                it with output_dir.  Only meaningful if output_dir is supplied.\n                All files processed by refactor() must start with this path.\n            output_dir: If supplied, all converted files will be written into\n                this directory tree instead of input_base_dir.\n            append_suffix: If supplied, all files output by this tool will have\n                this appended to their filename.  Useful for changing .py to\n                .py3 for example by passing append_suffix='3'.\n        ");
      var1.setline(56);
      PyObject var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("nobackups", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("show_diffs", var3);
      var3 = null;
      var1.setline(58);
      PyObject var10000 = var1.getlocal(6);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(6).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("sep")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(59);
         var3 = var1.getlocal(6);
         var3 = var3._iadd(var1.getglobal("os").__getattr__("sep"));
         var1.setlocal(6, var3);
      }

      var1.setline(60);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("_input_base_dir", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("_output_dir", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("_append_suffix", var3);
      var3 = null;
      var1.setline(63);
      var1.getglobal("super").__call__(var2, var1.getglobal("StdoutRefactoringTool"), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log_error$4(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      var1.getlocal(0).__getattr__("errors").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
      var1.setline(67);
      PyObject var10000 = var1.getlocal(0).__getattr__("logger").__getattr__("error");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(2), var1.getlocal(3));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_file$5(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = var1.getlocal(2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(71);
      if (var1.getlocal(0).__getattr__("_output_dir").__nonzero__()) {
         var1.setline(72);
         if (!var1.getlocal(2).__getattr__("startswith").__call__(var2, var1.getlocal(0).__getattr__("_input_base_dir")).__nonzero__()) {
            var1.setline(76);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("filename %s does not start with the input_base_dir %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("_input_base_dir")}))));
         }

         var1.setline(73);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_output_dir"), var1.getlocal(2).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_input_base_dir")), (PyObject)null, (PyObject)null));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(79);
      if (var1.getlocal(0).__getattr__("_append_suffix").__nonzero__()) {
         var1.setline(80);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(0).__getattr__("_append_suffix"));
         var1.setlocal(2, var3);
      }

      var1.setline(81);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(82);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(83);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(6)).__not__().__nonzero__()) {
            var1.setline(84);
            var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(6));
         }

         var1.setline(85);
         var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, PyString.fromInterned("Writing converted %s to %s."), (PyObject)var1.getlocal(5), (PyObject)var1.getlocal(2));
      }

      var1.setline(87);
      if (var1.getlocal(0).__getattr__("nobackups").__not__().__nonzero__()) {
         var1.setline(89);
         var3 = var1.getlocal(2)._add(PyString.fromInterned(".bak"));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(90);
         PyObject var4;
         PyException var7;
         if (var1.getglobal("os").__getattr__("path").__getattr__("lexists").__call__(var2, var1.getlocal(7)).__nonzero__()) {
            try {
               var1.setline(92);
               var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(7));
            } catch (Throwable var6) {
               var7 = Py.setException(var6, var1);
               if (!var7.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var7;
               }

               var4 = var7.value;
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(94);
               var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't remove backup %s"), (PyObject)var1.getlocal(7));
            }
         }

         try {
            var1.setline(96);
            var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(2), var1.getlocal(7));
         } catch (Throwable var5) {
            var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getglobal("os").__getattr__("error"))) {
               throw var7;
            }

            var4 = var7.value;
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(98);
            var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, PyString.fromInterned("Can't rename %s to %s"), (PyObject)var1.getlocal(2), (PyObject)var1.getlocal(7));
         }
      }

      var1.setline(100);
      var3 = var1.getglobal("super").__call__(var2, var1.getglobal("StdoutRefactoringTool"), var1.getlocal(0)).__getattr__("write_file");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(101);
      var1.getlocal(9).__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.setline(102);
      if (var1.getlocal(0).__getattr__("nobackups").__not__().__nonzero__()) {
         var1.setline(103);
         var1.getglobal("shutil").__getattr__("copymode").__call__(var2, var1.getlocal(7), var1.getlocal(2));
      }

      var1.setline(104);
      var3 = var1.getlocal(5);
      var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(106);
         var1.getglobal("shutil").__getattr__("copymode").__call__(var2, var1.getlocal(5), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_output$6(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(109);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(110);
         var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No changes to %s"), (PyObject)var1.getlocal(3));
      } else {
         var1.setline(112);
         var1.getlocal(0).__getattr__("log_message").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Refactored %s"), (PyObject)var1.getlocal(3));
         var1.setline(113);
         if (var1.getlocal(0).__getattr__("show_diffs").__nonzero__()) {
            var1.setline(114);
            PyObject var3 = var1.getglobal("diff_texts").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(5, var3);
            var3 = null;

            try {
               var1.setline(116);
               var3 = var1.getlocal(0).__getattr__("output_lock");
               PyObject var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               PyObject var4;
               if (var10000.__nonzero__()) {
                  label61: {
                     ContextManager var9;
                     var4 = (var9 = ContextGuard.getManager(var1.getlocal(0).__getattr__("output_lock"))).__enter__(var2);

                     try {
                        var1.setline(118);
                        var4 = var1.getlocal(5).__iter__();

                        while(true) {
                           var1.setline(118);
                           PyObject var5 = var4.__iternext__();
                           if (var5 == null) {
                              var1.setline(120);
                              var1.getglobal("sys").__getattr__("stdout").__getattr__("flush").__call__(var2);
                              break;
                           }

                           var1.setlocal(6, var5);
                           var1.setline(119);
                           Py.println(var1.getlocal(6));
                        }
                     } catch (Throwable var6) {
                        if (var9.__exit__(var2, Py.setException(var6, var1))) {
                           break label61;
                        }

                        throw (Throwable)Py.makeException();
                     }

                     var9.__exit__(var2, (PyException)null);
                  }
               } else {
                  var1.setline(122);
                  var3 = var1.getlocal(5).__iter__();

                  while(true) {
                     var1.setline(122);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(6, var4);
                     var1.setline(123);
                     Py.println(var1.getlocal(6));
                  }
               }
            } catch (Throwable var7) {
               PyException var8 = Py.setException(var7, var1);
               if (var8.match(var1.getglobal("UnicodeEncodeError"))) {
                  var1.setline(125);
                  var1.getglobal("warn").__call__(var2, PyString.fromInterned("couldn't encode %s's diff for your terminal")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)})));
                  var1.setline(127);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               throw var8;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warn$7(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.println(var3, PyString.fromInterned("WARNING: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$8(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(143);
      PyString.fromInterned("Main program.\n\n    Args:\n        fixer_pkg: the name of a package where the fixers are located.\n        args: optional; a list of command line arguments. If omitted,\n              sys.argv[1:] is used.\n\n    Returns a suggested exit status (0, 1, 2).\n    ");
      var1.setline(145);
      PyObject var10000 = var1.getglobal("optparse").__getattr__("OptionParser");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("2to3 [options] file|dir ...")};
      String[] var4 = new String[]{"usage"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var8 = var10000;
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(146);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-d"), PyString.fromInterned("--doctests_only"), PyString.fromInterned("store_true"), PyString.fromInterned("Fix up doctests only")};
      var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(148);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-f"), PyString.fromInterned("--fix"), PyString.fromInterned("append"), new PyList(Py.EmptyObjects), PyString.fromInterned("Each FIX specifies a transformation; default: all")};
      var4 = new String[]{"action", "default", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(150);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-j"), PyString.fromInterned("--processes"), PyString.fromInterned("store"), Py.newInteger(1), PyString.fromInterned("int"), PyString.fromInterned("Run 2to3 concurrently")};
      var4 = new String[]{"action", "default", "type", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(152);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-x"), PyString.fromInterned("--nofix"), PyString.fromInterned("append"), new PyList(Py.EmptyObjects), PyString.fromInterned("Prevent a transformation from being run")};
      var4 = new String[]{"action", "default", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(154);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-l"), PyString.fromInterned("--list-fixes"), PyString.fromInterned("store_true"), PyString.fromInterned("List available transformations")};
      var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(156);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-p"), PyString.fromInterned("--print-function"), PyString.fromInterned("store_true"), PyString.fromInterned("Modify the grammar so that print() is a function")};
      var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(158);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-v"), PyString.fromInterned("--verbose"), PyString.fromInterned("store_true"), PyString.fromInterned("More verbose logging")};
      var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(160);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("--no-diffs"), PyString.fromInterned("store_true"), PyString.fromInterned("Don't show diffs of the refactoring")};
      var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(162);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-w"), PyString.fromInterned("--write"), PyString.fromInterned("store_true"), PyString.fromInterned("Write back modified files")};
      var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(164);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-n"), PyString.fromInterned("--nobackups"), PyString.fromInterned("store_true"), var1.getglobal("False"), PyString.fromInterned("Don't write backups for modified files")};
      var4 = new String[]{"action", "default", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(166);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-o"), PyString.fromInterned("--output-dir"), PyString.fromInterned("store"), PyString.fromInterned("str"), PyString.fromInterned(""), PyString.fromInterned("Put output files in this directory instead of overwriting the input files.  Requires -n.")};
      var4 = new String[]{"action", "type", "default", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(169);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("-W"), PyString.fromInterned("--write-unchanged-files"), PyString.fromInterned("store_true"), PyString.fromInterned("Also write files even if no changes were required (useful with --output-dir); implies -w.")};
      var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(172);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var3 = new PyObject[]{PyString.fromInterned("--add-suffix"), PyString.fromInterned("store"), PyString.fromInterned("str"), PyString.fromInterned(""), PyString.fromInterned("Append this string to all output filenames. Requires -n if non-empty.  ex: --add-suffix='3' will generate .py3 files.")};
      var4 = new String[]{"action", "type", "default", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(178);
      var8 = var1.getglobal("False");
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(179);
      PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var15);
      var3 = null;
      var1.setline(180);
      var8 = var1.getlocal(2).__getattr__("parse_args").__call__(var2, var1.getlocal(1));
      PyObject[] var12 = Py.unpackSequence(var8, 2);
      PyObject var5 = var12[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var12[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(181);
      if (var1.getlocal(5).__getattr__("write_unchanged_files").__nonzero__()) {
         var1.setline(182);
         var8 = var1.getglobal("True");
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("write_unchanged_files"), var8);
         var3 = null;
         var1.setline(183);
         if (var1.getlocal(5).__getattr__("write").__not__().__nonzero__()) {
            var1.setline(184);
            var1.getglobal("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--write-unchanged-files/-W implies -w."));
         }

         var1.setline(185);
         var8 = var1.getglobal("True");
         var1.getlocal(5).__setattr__("write", var8);
         var3 = null;
      }

      var1.setline(188);
      var10000 = var1.getlocal(5).__getattr__("output_dir");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__getattr__("nobackups").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(189);
         var1.getlocal(2).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't use --output-dir/-o without -n."));
      }

      var1.setline(190);
      var10000 = var1.getlocal(5).__getattr__("add_suffix");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__getattr__("nobackups").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(191);
         var1.getlocal(2).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't use --add-suffix without -n."));
      }

      var1.setline(193);
      var10000 = var1.getlocal(5).__getattr__("write").__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__getattr__("no_diffs");
      }

      if (var10000.__nonzero__()) {
         var1.setline(194);
         var1.getglobal("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not writing files and not printing diffs; that's not very useful"));
      }

      var1.setline(195);
      var10000 = var1.getlocal(5).__getattr__("write").__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__getattr__("nobackups");
      }

      if (var10000.__nonzero__()) {
         var1.setline(196);
         var1.getlocal(2).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't use -n without -w"));
      }

      var1.setline(197);
      PyObject var13;
      PyInteger var16;
      if (var1.getlocal(5).__getattr__("list_fixes").__nonzero__()) {
         var1.setline(198);
         Py.println(PyString.fromInterned("Available transformations for the -f/--fix option:"));
         var1.setline(199);
         var8 = var1.getglobal("refactor").__getattr__("get_all_fix_names").__call__(var2, var1.getderef(0)).__iter__();

         while(true) {
            var1.setline(199);
            var13 = var8.__iternext__();
            if (var13 == null) {
               var1.setline(201);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(202);
                  var16 = Py.newInteger(0);
                  var1.f_lasti = -1;
                  return var16;
               }
               break;
            }

            var1.setlocal(6, var13);
            var1.setline(200);
            Py.println(var1.getlocal(6));
         }
      }

      var1.setline(203);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(204);
         var13 = var1.getglobal("sys").__getattr__("stderr");
         Py.println(var13, PyString.fromInterned("At least one file or directory argument required."));
         var1.setline(205);
         var13 = var1.getglobal("sys").__getattr__("stderr");
         Py.println(var13, PyString.fromInterned("Use --help to show usage."));
         var1.setline(206);
         var16 = Py.newInteger(2);
         var1.f_lasti = -1;
         return var16;
      } else {
         var1.setline(207);
         PyString var14 = PyString.fromInterned("-");
         var10000 = var14._in(var1.getlocal(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(208);
            var13 = var1.getglobal("True");
            var1.setlocal(3, var13);
            var4 = null;
            var1.setline(209);
            if (var1.getlocal(5).__getattr__("write").__nonzero__()) {
               var1.setline(210);
               var13 = var1.getglobal("sys").__getattr__("stderr");
               Py.println(var13, PyString.fromInterned("Can't write to stdin."));
               var1.setline(211);
               var16 = Py.newInteger(2);
               var1.f_lasti = -1;
               return var16;
            }
         }

         var1.setline(212);
         if (var1.getlocal(5).__getattr__("print_function").__nonzero__()) {
            var1.setline(213);
            var13 = var1.getglobal("True");
            var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("print_function"), var13);
            var4 = null;
         }

         var1.setline(216);
         var1.setline(216);
         var13 = var1.getlocal(5).__getattr__("verbose").__nonzero__() ? var1.getglobal("logging").__getattr__("DEBUG") : var1.getglobal("logging").__getattr__("INFO");
         var1.setlocal(7, var13);
         var4 = null;
         var1.setline(217);
         var10000 = var1.getglobal("logging").__getattr__("basicConfig");
         var12 = new PyObject[]{PyString.fromInterned("%(name)s: %(message)s"), var1.getlocal(7)};
         String[] var9 = new String[]{"format", "level"};
         var10000.__call__(var2, var12, var9);
         var4 = null;
         var1.setline(218);
         var13 = var1.getglobal("logging").__getattr__("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lib2to3.main"));
         var1.setlocal(8, var13);
         var4 = null;
         var1.setline(221);
         var13 = var1.getglobal("set").__call__(var2, var1.getglobal("refactor").__getattr__("get_fixers_from_package").__call__(var2, var1.getderef(0)));
         var1.setlocal(9, var13);
         var4 = null;
         var1.setline(222);
         var10000 = var1.getglobal("set");
         var1.setline(222);
         PyObject var10004 = var1.f_globals;
         var12 = Py.EmptyObjects;
         PyCode var10006 = f$9;
         PyObject[] var10 = new PyObject[]{var1.getclosure(0)};
         PyFunction var11 = new PyFunction(var10004, var12, var10006, (PyObject)null, var10);
         PyObject var10002 = var11.__call__(var2, var1.getlocal(5).__getattr__("nofix").__iter__());
         Arrays.fill(var12, (Object)null);
         var13 = var10000.__call__(var2, var10002);
         var1.setlocal(10, var13);
         var4 = null;
         var1.setline(223);
         var13 = var1.getglobal("set").__call__(var2);
         var1.setlocal(12, var13);
         var4 = null;
         var1.setline(224);
         if (var1.getlocal(5).__getattr__("fix").__nonzero__()) {
            var1.setline(225);
            var13 = var1.getglobal("False");
            var1.setlocal(13, var13);
            var4 = null;
            var1.setline(226);
            var13 = var1.getlocal(5).__getattr__("fix").__iter__();

            while(true) {
               var1.setline(226);
               var5 = var13.__iternext__();
               if (var5 == null) {
                  var1.setline(231);
                  var1.setline(231);
                  var13 = var1.getlocal(13).__nonzero__() ? var1.getlocal(9).__getattr__("union").__call__(var2, var1.getlocal(12)) : var1.getlocal(12);
                  var1.setlocal(15, var13);
                  var4 = null;
                  break;
               }

               var1.setlocal(14, var5);
               var1.setline(227);
               PyObject var6 = var1.getlocal(14);
               var10000 = var6._eq(PyString.fromInterned("all"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(228);
                  var6 = var1.getglobal("True");
                  var1.setlocal(13, var6);
                  var6 = null;
               } else {
                  var1.setline(230);
                  var1.getlocal(12).__getattr__("add").__call__(var2, var1.getderef(0)._add(PyString.fromInterned(".fix_"))._add(var1.getlocal(14)));
               }
            }
         } else {
            var1.setline(233);
            var13 = var1.getlocal(9).__getattr__("union").__call__(var2, var1.getlocal(12));
            var1.setlocal(15, var13);
            var4 = null;
         }

         var1.setline(234);
         var13 = var1.getlocal(15).__getattr__("difference").__call__(var2, var1.getlocal(10));
         var1.setlocal(16, var13);
         var4 = null;
         var1.setline(235);
         var13 = var1.getglobal("os").__getattr__("path").__getattr__("commonprefix").__call__(var2, var1.getlocal(1));
         var1.setlocal(17, var13);
         var4 = null;
         var1.setline(236);
         var10000 = var1.getlocal(17);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(17).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("sep")).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(17)).__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(241);
            var13 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(17));
            var1.setlocal(17, var13);
            var4 = null;
         }

         var1.setline(242);
         if (var1.getlocal(5).__getattr__("output_dir").__nonzero__()) {
            var1.setline(243);
            var13 = var1.getlocal(17).__getattr__("rstrip").__call__(var2, var1.getglobal("os").__getattr__("sep"));
            var1.setlocal(17, var13);
            var4 = null;
            var1.setline(244);
            var1.getlocal(8).__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("Output in %r will mirror the input directory %r layout."), (PyObject)var1.getlocal(5).__getattr__("output_dir"), (PyObject)var1.getlocal(17));
         }

         var1.setline(246);
         var10000 = var1.getglobal("StdoutRefactoringTool");
         var12 = new PyObject[]{var1.getglobal("sorted").__call__(var2, var1.getlocal(16)), var1.getlocal(4), var1.getglobal("sorted").__call__(var2, var1.getlocal(12)), var1.getlocal(5).__getattr__("nobackups"), var1.getlocal(5).__getattr__("no_diffs").__not__(), var1.getlocal(17), var1.getlocal(5).__getattr__("output_dir"), var1.getlocal(5).__getattr__("add_suffix")};
         var9 = new String[]{"input_base_dir", "output_dir", "append_suffix"};
         var10000 = var10000.__call__(var2, var12, var9);
         var4 = null;
         var13 = var10000;
         var1.setlocal(18, var13);
         var4 = null;
         var1.setline(254);
         if (var1.getlocal(18).__getattr__("errors").__not__().__nonzero__()) {
            var1.setline(255);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(256);
               var1.getlocal(18).__getattr__("refactor_stdin").__call__(var2);
            } else {
               try {
                  var1.setline(259);
                  var1.getlocal(18).__getattr__("refactor").__call__(var2, var1.getlocal(1), var1.getlocal(5).__getattr__("write"), var1.getlocal(5).__getattr__("doctests_only"), var1.getlocal(5).__getattr__("processes"));
               } catch (Throwable var7) {
                  PyException var17 = Py.setException(var7, var1);
                  if (var17.match(var1.getglobal("refactor").__getattr__("MultiprocessingUnsupported"))) {
                     var1.setline(262);
                     if (var1.getglobal("__debug__").__nonzero__()) {
                        var5 = var1.getlocal(5).__getattr__("processes");
                        var10000 = var5._gt(Py.newInteger(1));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           var10000 = Py.None;
                           throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                        }
                     }

                     var1.setline(263);
                     var5 = var1.getglobal("sys").__getattr__("stderr");
                     Py.println(var5, PyString.fromInterned("Sorry, -j isn't supported on this platform."));
                     var1.setline(265);
                     var16 = Py.newInteger(1);
                     var1.f_lasti = -1;
                     return var16;
                  }

                  throw var17;
               }
            }

            var1.setline(266);
            var1.getlocal(18).__getattr__("summarize").__call__(var2);
         }

         var1.setline(269);
         var8 = var1.getglobal("int").__call__(var2, var1.getglobal("bool").__call__(var2, var1.getlocal(18).__getattr__("errors")));
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject f$9(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(222);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(222);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(222);
         var1.setline(222);
         var6 = var1.getderef(0)._add(PyString.fromInterned(".fix_"))._add(var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public main$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"a", "b", "filename"};
      diff_texts$1 = Py.newCode(3, var2, var1, "diff_texts", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StdoutRefactoringTool$2 = Py.newCode(0, var2, var1, "StdoutRefactoringTool", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fixers", "options", "explicit", "nobackups", "show_diffs", "input_base_dir", "output_dir", "append_suffix"};
      __init__$3 = Py.newCode(9, var2, var1, "__init__", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args", "kwargs"};
      log_error$4 = Py.newCode(4, var2, var1, "log_error", 65, true, true, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new_text", "filename", "old_text", "encoding", "orig_filename", "output_dir", "backup", "err", "write"};
      write_file$5 = Py.newCode(5, var2, var1, "write_file", 69, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "old", "new", "filename", "equal", "diff_lines", "line"};
      print_output$6 = Py.newCode(5, var2, var1, "print_output", 108, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg"};
      warn$7 = Py.newCode(1, var2, var1, "warn", 130, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fixer_pkg", "args", "parser", "refactor_stdin", "flags", "options", "fixname", "level", "logger", "avail_fixes", "unwanted_fixes", "_(222_25)", "explicit", "all_present", "fix", "requested", "fixer_names", "input_base_dir", "rt"};
      String[] var10001 = var2;
      main$py var10007 = self;
      var2 = new String[]{"fixer_pkg"};
      main$8 = Py.newCode(2, var10001, var1, "main", 134, false, false, var10007, 8, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "fix"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"fixer_pkg"};
      f$9 = Py.newCode(1, var10001, var1, "<genexpr>", 222, false, false, var10007, 9, (String[])null, var2, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new main$py("lib2to3/main$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(main$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.diff_texts$1(var2, var3);
         case 2:
            return this.StdoutRefactoringTool$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.log_error$4(var2, var3);
         case 5:
            return this.write_file$5(var2, var3);
         case 6:
            return this.print_output$6(var2, var3);
         case 7:
            return this.warn$7(var2, var3);
         case 8:
            return this.main$8(var2, var3);
         case 9:
            return this.f$9(var2, var3);
         default:
            return null;
      }
   }
}
