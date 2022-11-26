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
@Filename("mailcap.py")
public class mailcap$py extends PyFunctionTable implements PyRunnable {
   static mailcap$py self;
   static final PyCode f$0;
   static final PyCode getcaps$1;
   static final PyCode listmailcapfiles$2;
   static final PyCode readmailcapfile$3;
   static final PyCode parseline$4;
   static final PyCode parsefield$5;
   static final PyCode findmatch$6;
   static final PyCode lookup$7;
   static final PyCode f$8;
   static final PyCode subst$9;
   static final PyCode findparam$10;
   static final PyCode test$11;
   static final PyCode show$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Mailcap file handling.  See RFC 1524."));
      var1.setline(1);
      PyString.fromInterned("Mailcap file handling.  See RFC 1524.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(5);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("getcaps"), PyString.fromInterned("findmatch")});
      var1.setlocal("__all__", var4);
      var3 = null;
      var1.setline(9);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getcaps$1, PyString.fromInterned("Return a dictionary containing the mailcap database.\n\n    The dictionary maps a MIME type (in all lowercase, e.g. 'text/plain')\n    to a list of dictionaries corresponding to mailcap entries.  The list\n    collects all the entries for that MIME type from all available mailcap\n    files.  Each dictionary contains key-value pairs for that MIME type,\n    where the viewing command is stored with the key \"view\".\n\n    "));
      var1.setlocal("getcaps", var6);
      var3 = null;
      var1.setline(34);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, listmailcapfiles$2, PyString.fromInterned("Return a list of all mailcap files found on the system."));
      var1.setlocal("listmailcapfiles", var6);
      var3 = null;
      var1.setline(53);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, readmailcapfile$3, PyString.fromInterned("Read a mailcap file and return a dictionary keyed by MIME type.\n\n    Each MIME type is mapped to an entry consisting of a list of\n    dictionaries; the list will contain more than one such dictionary\n    if a given MIME type appears more than once in the mailcap file.\n    Each dictionary contains key-value pairs for that MIME type, where\n    the viewing command is stored with the key \"view\".\n    "));
      var1.setlocal("readmailcapfile", var6);
      var3 = null;
      var1.setline(91);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parseline$4, PyString.fromInterned("Parse one entry in a mailcap file and return a dictionary.\n\n    The viewing command is stored as the value with the key \"view\",\n    and the rest of the fields produce key-value pairs in the dict.\n    "));
      var1.setlocal("parseline", var6);
      var3 = null;
      var1.setline(122);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, parsefield$5, PyString.fromInterned("Separate one key-value pair in a mailcap entry."));
      var1.setlocal("parsefield", var6);
      var3 = null;
      var1.setline(138);
      var5 = new PyObject[]{PyString.fromInterned("view"), PyString.fromInterned("/dev/null"), new PyList(Py.EmptyObjects)};
      var6 = new PyFunction(var1.f_globals, var5, findmatch$6, PyString.fromInterned("Find a match for a mailcap entry.\n\n    Return a tuple containing the command line, and the mailcap entry\n    used; (None, None) if no match is found.  This may invoke the\n    'test' command of several matching entries before deciding which\n    entry to use.\n\n    "));
      var1.setlocal("findmatch", var6);
      var3 = null;
      var1.setline(158);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, lookup$7, (PyObject)null);
      var1.setlocal("lookup", var6);
      var3 = null;
      var1.setline(170);
      var5 = new PyObject[]{new PyList(Py.EmptyObjects)};
      var6 = new PyFunction(var1.f_globals, var5, subst$9, (PyObject)null);
      var1.setlocal("subst", var6);
      var3 = null;
      var1.setline(202);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, findparam$10, (PyObject)null);
      var1.setlocal("findparam", var6);
      var3 = null;
      var1.setline(213);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test$11, (PyObject)null);
      var1.setlocal("test", var6);
      var3 = null;
      var1.setline(235);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, show$12, (PyObject)null);
      var1.setlocal("show", var6);
      var3 = null;
      var1.setline(254);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(255);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getcaps$1(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyString.fromInterned("Return a dictionary containing the mailcap database.\n\n    The dictionary maps a MIME type (in all lowercase, e.g. 'text/plain')\n    to a list of dictionaries corresponding to mailcap entries.  The list\n    collects all the entries for that MIME type from all available mailcap\n    files.  Each dictionary contains key-value pairs for that MIME type,\n    where the viewing command is stored with the key \"view\".\n\n    ");
      var1.setline(19);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(20);
      PyObject var10 = var1.getglobal("listmailcapfiles").__call__(var2).__iter__();

      while(true) {
         var1.setline(20);
         PyObject var4 = var10.__iternext__();
         if (var4 == null) {
            var1.setline(32);
            var10 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var10;
         }

         var1.setlocal(1, var4);

         PyException var5;
         PyObject var11;
         try {
            var1.setline(22);
            var11 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("r"));
            var1.setlocal(2, var11);
            var5 = null;
         } catch (Throwable var9) {
            var5 = Py.setException(var9, var1);
            if (var5.match(var1.getglobal("IOError"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(25);
         var11 = var1.getglobal("readmailcapfile").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var11);
         var5 = null;
         var1.setline(26);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         var1.setline(27);
         var11 = var1.getlocal(3).__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(27);
            PyObject var6 = var11.__iternext__();
            if (var6 == null) {
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(4, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(5, var8);
            var8 = null;
            var1.setline(28);
            PyObject var12 = var1.getlocal(4);
            PyObject var10000 = var12._in(var1.getlocal(0));
            var7 = null;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(29);
               var12 = var1.getlocal(5);
               var1.getlocal(0).__setitem__(var1.getlocal(4), var12);
               var7 = null;
            } else {
               var1.setline(31);
               var12 = var1.getlocal(0).__getitem__(var1.getlocal(4))._add(var1.getlocal(5));
               var1.getlocal(0).__setitem__(var1.getlocal(4), var12);
               var7 = null;
            }
         }
      }
   }

   public PyObject listmailcapfiles$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyString.fromInterned("Return a list of all mailcap files found on the system.");
      var1.setline(37);
      PyString var3 = PyString.fromInterned("MAILCAPS");
      PyObject var10000 = var3._in(var1.getglobal("os").__getattr__("environ"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(38);
         var4 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("MAILCAPS"));
         var1.setlocal(0, var4);
         var3 = null;
         var1.setline(39);
         var4 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(41);
         var3 = PyString.fromInterned("HOME");
         var10000 = var3._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(42);
            var4 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME"));
            var1.setlocal(2, var4);
            var3 = null;
         } else {
            var1.setline(45);
            var3 = PyString.fromInterned(".");
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(46);
         PyList var5 = new PyList(new PyObject[]{var1.getlocal(2)._add(PyString.fromInterned("/.mailcap")), PyString.fromInterned("/etc/mailcap"), PyString.fromInterned("/usr/etc/mailcap"), PyString.fromInterned("/usr/local/etc/mailcap")});
         var1.setlocal(1, var5);
         var3 = null;
      }

      var1.setline(48);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject readmailcapfile$3(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyString.fromInterned("Read a mailcap file and return a dictionary keyed by MIME type.\n\n    Each MIME type is mapped to an entry consisting of a list of\n    dictionaries; the list will contain more than one such dictionary\n    if a given MIME type appears more than once in the mailcap file.\n    Each dictionary contains key-value pairs for that MIME type, where\n    the viewing command is stored with the key \"view\".\n    ");
      var1.setline(62);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var6;
      label57:
      while(true) {
         var1.setline(63);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(64);
         var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(65);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(67);
         var6 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         PyObject var10000 = var6._eq(PyString.fromInterned("#"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var6 = var1.getlocal(2).__getattr__("strip").__call__(var2);
            var10000 = var6._eq(PyString.fromInterned(""));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(69);
            var6 = var1.getlocal(2);
            var1.setlocal(3, var6);
            var3 = null;

            while(true) {
               var1.setline(71);
               var6 = var1.getlocal(3).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
               var10000 = var6._eq(PyString.fromInterned("\\\n"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(76);
                  var6 = var1.getglobal("parseline").__call__(var2, var1.getlocal(2));
                  PyObject[] var4 = Py.unpackSequence(var6, 2);
                  PyObject var5 = var4[0];
                  var1.setlocal(4, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(77);
                  var10000 = var1.getlocal(4);
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(5);
                  }

                  if (!var10000.__not__().__nonzero__()) {
                     var1.setline(80);
                     var6 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
                     var1.setlocal(6, var6);
                     var3 = null;
                     var1.setline(81);
                     var6 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(6))).__iter__();

                     while(true) {
                        var1.setline(81);
                        PyObject var7 = var6.__iternext__();
                        if (var7 == null) {
                           var1.setline(83);
                           var6 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(6)).__getattr__("lower").__call__(var2);
                           var1.setlocal(4, var6);
                           var3 = null;
                           var1.setline(85);
                           var6 = var1.getlocal(4);
                           var10000 = var6._in(var1.getlocal(1));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(86);
                              var1.getlocal(1).__getitem__(var1.getlocal(4)).__getattr__("append").__call__(var2, var1.getlocal(5));
                           } else {
                              var1.setline(88);
                              PyList var9 = new PyList(new PyObject[]{var1.getlocal(5)});
                              var1.getlocal(1).__setitem__((PyObject)var1.getlocal(4), var9);
                              var3 = null;
                           }
                           continue label57;
                        }

                        var1.setlocal(7, var7);
                        var1.setline(82);
                        var5 = var1.getlocal(6).__getitem__(var1.getlocal(7)).__getattr__("strip").__call__(var2);
                        var1.getlocal(6).__setitem__(var1.getlocal(7), var5);
                        var5 = null;
                     }
                  }
                  break;
               }

               var1.setline(72);
               var6 = var1.getlocal(0).__getattr__("readline").__call__(var2);
               var1.setlocal(3, var6);
               var3 = null;
               var1.setline(73);
               if (var1.getlocal(3).__not__().__nonzero__()) {
                  var1.setline(73);
                  PyString var8 = PyString.fromInterned("\n");
                  var1.setlocal(3, var8);
                  var3 = null;
               }

               var1.setline(74);
               var6 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null)._add(var1.getlocal(3));
               var1.setlocal(2, var6);
               var3 = null;
            }
         }
      }

      var1.setline(89);
      var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject parseline$4(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyString.fromInterned("Parse one entry in a mailcap file and return a dictionary.\n\n    The viewing command is stored as the value with the key \"view\",\n    and the rest of the fields produce key-value pairs in the dict.\n    ");
      var1.setline(97);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(98);
      PyTuple var7 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(99);
         PyObject var8 = var1.getlocal(2);
         PyObject var10000 = var8._lt(var1.getlocal(3));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(103);
            var8 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var8._lt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(104);
               var7 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(105);
               PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)});
               PyObject[] var12 = Py.unpackSequence(var9, 3);
               PyObject var6 = var12[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var12[1];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var12[2];
               var1.setlocal(7, var6);
               var6 = null;
               var4 = null;
               var1.setline(106);
               PyDictionary var10 = new PyDictionary(new PyObject[]{PyString.fromInterned("view"), var1.getlocal(6)});
               var1.setlocal(1, var10);
               var4 = null;
               var1.setline(107);
               PyObject var11 = var1.getlocal(7).__iter__();

               while(true) {
                  var1.setline(107);
                  var5 = var11.__iternext__();
                  if (var5 == null) {
                     var1.setline(120);
                     var7 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(1)});
                     var1.f_lasti = -1;
                     return var7;
                  }

                  var1.setlocal(4, var5);
                  var1.setline(108);
                  var6 = var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
                  var1.setlocal(2, var6);
                  var6 = null;
                  var1.setline(109);
                  var6 = var1.getlocal(2);
                  var10000 = var6._lt(Py.newInteger(0));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(110);
                     var6 = var1.getlocal(4);
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(111);
                     PyString var13 = PyString.fromInterned("");
                     var1.setlocal(9, var13);
                     var6 = null;
                  } else {
                     var1.setline(113);
                     var6 = var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("strip").__call__(var2);
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(114);
                     var6 = var1.getlocal(4).__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
                     var1.setlocal(9, var6);
                     var6 = null;
                  }

                  var1.setline(115);
                  var6 = var1.getlocal(8);
                  var10000 = var6._in(var1.getlocal(1));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(117);
                  } else {
                     var1.setline(119);
                     var6 = var1.getlocal(9);
                     var1.getlocal(1).__setitem__(var1.getlocal(8), var6);
                     var6 = null;
                  }
               }
            }
         }

         var1.setline(100);
         var8 = var1.getglobal("parsefield").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
         var4 = Py.unpackSequence(var8, 2);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(101);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(102);
         var8 = var1.getlocal(2)._add(Py.newInteger(1));
         var1.setlocal(2, var8);
         var3 = null;
      }
   }

   public PyObject parsefield$5(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("Separate one key-value pair in a mailcap entry.");
      var1.setline(124);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(125);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._lt(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(126);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(127);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(PyString.fromInterned(";"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(129);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(PyString.fromInterned("\\"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(130);
            var3 = var1.getlocal(1)._add(Py.newInteger(2));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(132);
            var3 = var1.getlocal(1)._add(Py.newInteger(1));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(133);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getslice__(var1.getlocal(3), var1.getlocal(1), (PyObject)null).__getattr__("strip").__call__(var2), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject findmatch$6(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyString.fromInterned("Find a match for a mailcap entry.\n\n    Return a tuple containing the command line, and the mailcap entry\n    used; (None, None) if no match is found.  This may invoke the\n    'test' command of several matching entries before deciding which\n    entry to use.\n\n    ");
      var1.setline(147);
      PyObject var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(149);
      var3 = var1.getlocal(5).__iter__();

      PyObject var10000;
      PyString var5;
      PyObject var6;
      PyTuple var7;
      do {
         var1.setline(149);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(156);
            var7 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(6, var4);
         var1.setline(150);
         var5 = PyString.fromInterned("test");
         var10000 = var5._in(var1.getlocal(6));
         var5 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(151);
         var6 = var1.getglobal("subst").__call__(var2, var1.getlocal(6).__getitem__(PyString.fromInterned("test")), var1.getlocal(3), var1.getlocal(4));
         var1.setlocal(7, var6);
         var5 = null;
         var1.setline(152);
         var10000 = var1.getlocal(7);
         if (var10000.__nonzero__()) {
            var6 = var1.getglobal("os").__getattr__("system").__call__(var2, var1.getlocal(7));
            var10000 = var6._ne(Py.newInteger(0));
            var5 = null;
         }
      } while(var10000.__nonzero__());

      var1.setline(154);
      var6 = var1.getglobal("subst").__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(2)), var1.getlocal(1), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(8, var6);
      var5 = null;
      var1.setline(155);
      var7 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject lookup$7(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(160);
      PyObject var4 = var1.getlocal(1);
      PyObject var10000 = var4._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(161);
         var4 = var1.getlocal(3)._add(var1.getlocal(0).__getitem__(var1.getlocal(1)));
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(162);
      var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(163);
      var4 = var1.getlocal(4).__getitem__(Py.newInteger(0))._add(PyString.fromInterned("/*"));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(164);
      var4 = var1.getlocal(1);
      var10000 = var4._in(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(165);
         var4 = var1.getlocal(3)._add(var1.getlocal(0).__getitem__(var1.getlocal(1)));
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(166);
      var4 = var1.getlocal(2);
      var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(167);
         var10000 = var1.getglobal("filter");
         var1.setline(167);
         PyObject[] var5 = new PyObject[]{var1.getlocal(2)};
         var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$8)), (PyObject)var1.getlocal(3));
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(168);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject subst$9(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(173);
      PyTuple var6 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;

      while(true) {
         while(true) {
            var1.setline(174);
            PyObject var7 = var1.getlocal(5);
            PyObject var10000 = var7._lt(var1.getlocal(6));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(200);
               var7 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(175);
            var7 = var1.getlocal(0).__getitem__(var1.getlocal(5));
            var1.setlocal(7, var7);
            var3 = null;
            var1.setline(175);
            var7 = var1.getlocal(5)._add(Py.newInteger(1));
            var1.setlocal(5, var7);
            var3 = null;
            var1.setline(176);
            var7 = var1.getlocal(7);
            var10000 = var7._ne(PyString.fromInterned("%"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(177);
               var7 = var1.getlocal(7);
               var10000 = var7._eq(PyString.fromInterned("\\"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(178);
                  var7 = var1.getlocal(0).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
                  var1.setlocal(7, var7);
                  var3 = null;
                  var1.setline(178);
                  var7 = var1.getlocal(5)._add(Py.newInteger(1));
                  var1.setlocal(5, var7);
                  var3 = null;
               }

               var1.setline(179);
               var7 = var1.getlocal(4)._add(var1.getlocal(7));
               var1.setlocal(4, var7);
               var3 = null;
            } else {
               var1.setline(181);
               var7 = var1.getlocal(0).__getitem__(var1.getlocal(5));
               var1.setlocal(7, var7);
               var3 = null;
               var1.setline(181);
               var7 = var1.getlocal(5)._add(Py.newInteger(1));
               var1.setlocal(5, var7);
               var3 = null;
               var1.setline(182);
               var7 = var1.getlocal(7);
               var10000 = var7._eq(PyString.fromInterned("%"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(183);
                  var7 = var1.getlocal(4)._add(var1.getlocal(7));
                  var1.setlocal(4, var7);
                  var3 = null;
               } else {
                  var1.setline(184);
                  var7 = var1.getlocal(7);
                  var10000 = var7._eq(PyString.fromInterned("s"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(185);
                     var7 = var1.getlocal(4)._add(var1.getlocal(2));
                     var1.setlocal(4, var7);
                     var3 = null;
                  } else {
                     var1.setline(186);
                     var7 = var1.getlocal(7);
                     var10000 = var7._eq(PyString.fromInterned("t"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(187);
                        var7 = var1.getlocal(4)._add(var1.getlocal(1));
                        var1.setlocal(4, var7);
                        var3 = null;
                     } else {
                        var1.setline(188);
                        var7 = var1.getlocal(7);
                        var10000 = var7._eq(PyString.fromInterned("{"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(189);
                           var7 = var1.getlocal(5);
                           var1.setlocal(8, var7);
                           var3 = null;

                           while(true) {
                              var1.setline(190);
                              var7 = var1.getlocal(5);
                              var10000 = var7._lt(var1.getlocal(6));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var7 = var1.getlocal(0).__getitem__(var1.getlocal(5));
                                 var10000 = var7._ne(PyString.fromInterned("}"));
                                 var3 = null;
                              }

                              if (!var10000.__nonzero__()) {
                                 var1.setline(192);
                                 var7 = var1.getlocal(0).__getslice__(var1.getlocal(8), var1.getlocal(5), (PyObject)null);
                                 var1.setlocal(9, var7);
                                 var3 = null;
                                 var1.setline(193);
                                 var7 = var1.getlocal(5)._add(Py.newInteger(1));
                                 var1.setlocal(5, var7);
                                 var3 = null;
                                 var1.setline(194);
                                 var7 = var1.getlocal(4)._add(var1.getglobal("findparam").__call__(var2, var1.getlocal(9), var1.getlocal(3)));
                                 var1.setlocal(4, var7);
                                 var3 = null;
                                 break;
                              }

                              var1.setline(191);
                              var7 = var1.getlocal(5)._add(Py.newInteger(1));
                              var1.setlocal(5, var7);
                              var3 = null;
                           }
                        } else {
                           var1.setline(199);
                           var7 = var1.getlocal(4)._add(PyString.fromInterned("%"))._add(var1.getlocal(7));
                           var1.setlocal(4, var7);
                           var3 = null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject findparam$10(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2)._add(PyString.fromInterned("="));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(204);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(205);
      var3 = var1.getlocal(1).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(205);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(208);
            PyString var6 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(206);
         var5 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("lower").__call__(var2);
         var10000 = var5._eq(var1.getlocal(0));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(207);
      var5 = var1.getlocal(3).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject test$11(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(215);
      var3 = var1.getglobal("getcaps").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(216);
      if (var1.getlocal(0).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__not__().__nonzero__()) {
         var1.setline(217);
         var1.getglobal("show").__call__(var2, var1.getlocal(1));
         var1.setline(218);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(219);
         var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("argv")), (PyObject)Py.newInteger(2)).__iter__();

         while(true) {
            var1.setline(219);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(220);
            PyObject var5 = var1.getlocal(0).__getattr__("argv").__getslice__(var1.getlocal(2), var1.getlocal(2)._add(Py.newInteger(2)), (PyObject)null);
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(221);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            PyObject var10000 = var5._lt(Py.newInteger(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(222);
               Py.println(PyString.fromInterned("usage: mailcap [MIMEtype file] ..."));
               var1.setline(223);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(224);
            var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(225);
            var5 = var1.getlocal(3).__getitem__(Py.newInteger(1));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(226);
            var5 = var1.getglobal("findmatch").__call__(var2, var1.getlocal(1), var1.getlocal(4), PyString.fromInterned("view"), var1.getlocal(5));
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(6, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(7, var7);
            var7 = null;
            var5 = null;
            var1.setline(227);
            if (var1.getlocal(6).__not__().__nonzero__()) {
               var1.setline(228);
               Py.printComma(PyString.fromInterned("No viewer found for"));
               Py.println(var1.getglobal("type"));
            } else {
               var1.setline(230);
               Py.printComma(PyString.fromInterned("Executing:"));
               Py.println(var1.getlocal(6));
               var1.setline(231);
               var5 = var1.getglobal("os").__getattr__("system").__call__(var2, var1.getlocal(6));
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(232);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(233);
                  Py.printComma(PyString.fromInterned("Exit status:"));
                  Py.println(var1.getlocal(8));
               }
            }
         }
      }
   }

   public PyObject show$12(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      Py.println(PyString.fromInterned("Mailcap files:"));
      var1.setline(237);
      PyObject var3 = var1.getglobal("listmailcapfiles").__call__(var2).__iter__();

      while(true) {
         var1.setline(237);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(238);
            Py.println();
            var1.setline(239);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(239);
               var3 = var1.getglobal("getcaps").__call__(var2);
               var1.setlocal(0, var3);
               var3 = null;
            }

            var1.setline(240);
            Py.println(PyString.fromInterned("Mailcap entries:"));
            var1.setline(241);
            Py.println();
            var1.setline(242);
            var3 = var1.getlocal(0).__getattr__("keys").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(243);
            var1.getlocal(2).__getattr__("sort").__call__(var2);
            var1.setline(244);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(244);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(245);
               Py.println(var1.getlocal(3));
               var1.setline(246);
               PyObject var5 = var1.getlocal(0).__getitem__(var1.getlocal(3));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(247);
               var5 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(247);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(5, var6);
                  var1.setline(248);
                  PyObject var7 = var1.getlocal(5).__getattr__("keys").__call__(var2);
                  var1.setlocal(6, var7);
                  var7 = null;
                  var1.setline(249);
                  var1.getlocal(6).__getattr__("sort").__call__(var2);
                  var1.setline(250);
                  var7 = var1.getlocal(6).__iter__();

                  while(true) {
                     var1.setline(250);
                     PyObject var8 = var7.__iternext__();
                     if (var8 == null) {
                        var1.setline(252);
                        Py.println();
                        break;
                     }

                     var1.setlocal(7, var8);
                     var1.setline(251);
                     Py.printComma(PyString.fromInterned("  %-15s")._mod(var1.getlocal(7)));
                     Py.println(var1.getlocal(5).__getitem__(var1.getlocal(7)));
                  }
               }
            }
         }

         var1.setlocal(1, var4);
         var1.setline(237);
         Py.println(PyString.fromInterned("\t")._add(var1.getlocal(1)));
      }
   }

   public mailcap$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"caps", "mailcap", "fp", "morecaps", "key", "value"};
      getcaps$1 = Py.newCode(0, var2, var1, "getcaps", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "mailcaps", "home"};
      listmailcapfiles$2 = Py.newCode(0, var2, var1, "listmailcapfiles", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp", "caps", "line", "nextline", "key", "fields", "types", "j"};
      readmailcapfile$3 = Py.newCode(1, var2, var1, "readmailcapfile", 53, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "fields", "i", "n", "field", "key", "view", "rest", "fkey", "fvalue"};
      parseline$4 = Py.newCode(1, var2, var1, "parseline", 91, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "i", "n", "start", "c"};
      parsefield$5 = Py.newCode(3, var2, var1, "parsefield", 122, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"caps", "MIMEtype", "key", "filename", "plist", "entries", "e", "test", "command"};
      findmatch$6 = Py.newCode(5, var2, var1, "findmatch", 138, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"caps", "MIMEtype", "key", "entries", "MIMEtypes"};
      lookup$7 = Py.newCode(3, var2, var1, "lookup", 158, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"e", "key"};
      f$8 = Py.newCode(2, var2, var1, "<lambda>", 167, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"field", "MIMEtype", "filename", "plist", "res", "i", "n", "c", "start", "name"};
      subst$9 = Py.newCode(4, var2, var1, "subst", 170, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "plist", "n", "p"};
      findparam$10 = Py.newCode(2, var2, var1, "findparam", 202, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "caps", "i", "args", "MIMEtype", "file", "command", "e", "sts"};
      test$11 = Py.newCode(0, var2, var1, "test", 213, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"caps", "fn", "ckeys", "type", "entries", "e", "keys", "k"};
      show$12 = Py.newCode(1, var2, var1, "show", 235, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mailcap$py("mailcap$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mailcap$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.getcaps$1(var2, var3);
         case 2:
            return this.listmailcapfiles$2(var2, var3);
         case 3:
            return this.readmailcapfile$3(var2, var3);
         case 4:
            return this.parseline$4(var2, var3);
         case 5:
            return this.parsefield$5(var2, var3);
         case 6:
            return this.findmatch$6(var2, var3);
         case 7:
            return this.lookup$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.subst$9(var2, var3);
         case 10:
            return this.findparam$10(var2, var3);
         case 11:
            return this.test$11(var2, var3);
         case 12:
            return this.show$12(var2, var3);
         default:
            return null;
      }
   }
}
