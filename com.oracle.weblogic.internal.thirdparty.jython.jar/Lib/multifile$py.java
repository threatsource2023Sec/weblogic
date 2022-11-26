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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("multifile.py")
public class multifile$py extends PyFunctionTable implements PyRunnable {
   static multifile$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode MultiFile$2;
   static final PyCode __init__$3;
   static final PyCode tell$4;
   static final PyCode seek$5;
   static final PyCode readline$6;
   static final PyCode readlines$7;
   static final PyCode read$8;
   static final PyCode next$9;
   static final PyCode push$10;
   static final PyCode pop$11;
   static final PyCode is_data$12;
   static final PyCode section_divider$13;
   static final PyCode end_marker$14;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A readline()-style interface to the parts of a multipart message.\n\nThe MultiFile class makes each part of a multipart message \"feel\" like\nan ordinary file, as long as you use fp.readline().  Allows recursive\nuse, for nested multipart messages.  Probably best used together\nwith module mimetools.\n\nSuggested use:\n\nreal_fp = open(...)\nfp = MultiFile(real_fp)\n\n\"read some lines from fp\"\nfp.push(separator)\nwhile 1:\n        \"read lines from fp until it returns an empty string\" (A)\n        if not fp.next(): break\nfp.pop()\n\"read remaining lines from fp until it returns an empty string\"\n\nThe latter sequence may be used recursively at (A).\nIt is also allowed to use multiple push()...pop() sequences.\n\nIf seekable is given as 0, the class code will not do the bookkeeping\nit normally attempts in order to make seeks relative to the beginning of the\ncurrent file part.  This may be useful when using MultiFile with a non-\nseekable stream object.\n"));
      var1.setline(28);
      PyString.fromInterned("A readline()-style interface to the parts of a multipart message.\n\nThe MultiFile class makes each part of a multipart message \"feel\" like\nan ordinary file, as long as you use fp.readline().  Allows recursive\nuse, for nested multipart messages.  Probably best used together\nwith module mimetools.\n\nSuggested use:\n\nreal_fp = open(...)\nfp = MultiFile(real_fp)\n\n\"read some lines from fp\"\nfp.push(separator)\nwhile 1:\n        \"read lines from fp until it returns an empty string\" (A)\n        if not fp.next(): break\nfp.pop()\n\"read remaining lines from fp until it returns an empty string\"\n\nThe latter sequence may be used recursively at (A).\nIt is also allowed to use multiple push()...pop() sequences.\n\nIf seekable is given as 0, the class code will not do the bookkeeping\nit normally attempts in order to make seeks relative to the beginning of the\ncurrent file part.  This may be useful when using MultiFile with a non-\nseekable stream object.\n");
      var1.setline(29);
      String[] var3 = new String[]{"warn"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warn", var4);
      var4 = null;
      var1.setline(30);
      PyObject var10000 = var1.getname("warn");
      var5 = new PyObject[]{PyString.fromInterned("the multifile module has been deprecated since Python 2.5"), var1.getname("DeprecationWarning"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(32);
      var1.dellocal("warn");
      var1.setline(34);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("MultiFile"), PyString.fromInterned("Error")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(36);
      var5 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var5, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(39);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("MultiFile", var5, MultiFile$2);
      var1.setlocal("MultiFile", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      return var1.getf_locals();
   }

   public PyObject MultiFile$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(41);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("seekable", var3);
      var3 = null;
      var1.setline(43);
      PyObject[] var4 = new PyObject[]{Py.newInteger(1)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(53);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$4, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(58);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$5, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(75);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readline$6, (PyObject)null);
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(112);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readlines$7, (PyObject)null);
      var1.setlocal("readlines", var5);
      var3 = null;
      var1.setline(120);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read$8, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(123);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$9, (PyObject)null);
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(133);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, push$10, (PyObject)null);
      var1.setlocal("push", var5);
      var3 = null;
      var1.setline(141);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, pop$11, (PyObject)null);
      var1.setlocal("pop", var5);
      var3 = null;
      var1.setline(155);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_data$12, (PyObject)null);
      var1.setlocal("is_data", var5);
      var3 = null;
      var1.setline(158);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, section_divider$13, (PyObject)null);
      var1.setlocal("section_divider", var5);
      var3 = null;
      var1.setline(161);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, end_marker$14, (PyObject)null);
      var1.setlocal("end_marker", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fp", var3);
      var3 = null;
      var1.setline(45);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var4);
      var3 = null;
      var1.setline(46);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"level", var5);
      var3 = null;
      var1.setline(47);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"last", var5);
      var3 = null;
      var1.setline(48);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(49);
         var5 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"seekable", var5);
         var3 = null;
         var1.setline(50);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
         var1.getlocal(0).__setattr__("start", var3);
         var3 = null;
         var1.setline(51);
         var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"posstack", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tell$4(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var3 = var1.getlocal(0).__getattr__("level");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(55);
         var3 = var1.getlocal(0).__getattr__("lastpos");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(56);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2)._sub(var1.getlocal(0).__getattr__("start"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seek$5(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(60);
      PyObject var10000;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(61);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(62);
            var3 = var1.getlocal(1)._add(var1.getlocal(3));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(63);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(64);
               var3 = var1.getlocal(0).__getattr__("level");
               var10000 = var3._gt(Py.newInteger(0));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(67);
                  throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("can't use whence=2 yet"));
               }

               var1.setline(65);
               var3 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("lastpos"));
               var1.setlocal(1, var3);
               var3 = null;
            }
         }
      }

      var1.setline(68);
      PyInteger var5 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(1);
      PyInteger var6 = var5;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var6._le(var10001)).__nonzero__()) {
         var4 = var3._le(var1.getlocal(3));
      }

      var3 = null;
      var10000 = var4.__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("level");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._gt(var1.getlocal(0).__getattr__("lastpos"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(70);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad MultiFile.seek() call"));
      } else {
         var1.setline(71);
         var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(1)._add(var1.getlocal(0).__getattr__("start")));
         var1.setline(72);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"level", var5);
         var3 = null;
         var1.setline(73);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"last", var5);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject readline$6(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0).__getattr__("level");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      PyString var8;
      if (var10000.__nonzero__()) {
         var1.setline(77);
         var8 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(78);
         PyObject var4 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(80);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(81);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stack"));
            var1.getlocal(0).__setattr__("level", var4);
            var4 = null;
            var1.setline(82);
            var4 = var1.getlocal(0).__getattr__("level");
            var10000 = var4._gt(Py.newInteger(0));
            var4 = null;
            var4 = var10000;
            var1.getlocal(0).__setattr__("last", var4);
            var4 = null;
            var1.setline(83);
            if (var1.getlocal(0).__getattr__("last").__nonzero__()) {
               var1.setline(84);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("sudden EOF in MultiFile.readline()"));
            } else {
               var1.setline(85);
               var8 = PyString.fromInterned("");
               var1.f_lasti = -1;
               return var8;
            }
         } else {
            var1.setline(86);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("level");
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(88);
            if (var1.getlocal(0).__getattr__("is_data").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(89);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(92);
               var4 = var1.getlocal(1).__getattr__("rstrip").__call__(var2);
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(95);
               var4 = var1.getglobal("enumerate").__call__(var2, var1.getglobal("reversed").__call__(var2, var1.getlocal(0).__getattr__("stack"))).__iter__();

               while(true) {
                  var1.setline(95);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(103);
                     var3 = var1.getlocal(1);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  PyObject[] var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(4, var7);
                  var7 = null;
                  var1.setline(96);
                  PyObject var9 = var1.getlocal(2);
                  var10000 = var9._eq(var1.getlocal(0).__getattr__("section_divider").__call__(var2, var1.getlocal(4)));
                  var6 = null;
                  PyInteger var10;
                  if (var10000.__nonzero__()) {
                     var1.setline(97);
                     var10 = Py.newInteger(0);
                     var1.getlocal(0).__setattr__((String)"last", var10);
                     var6 = null;
                     break;
                  }

                  var1.setline(99);
                  var9 = var1.getlocal(2);
                  var10000 = var9._eq(var1.getlocal(0).__getattr__("end_marker").__call__(var2, var1.getlocal(4)));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(100);
                     var10 = Py.newInteger(1);
                     var1.getlocal(0).__setattr__((String)"last", var10);
                     var6 = null;
                     break;
                  }
               }

               var1.setline(105);
               if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
                  var1.setline(106);
                  var4 = var1.getlocal(0).__getattr__("tell").__call__(var2)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
                  var1.getlocal(0).__setattr__("lastpos", var4);
                  var4 = null;
               }

               var1.setline(107);
               var4 = var1.getlocal(3)._add(Py.newInteger(1));
               var1.getlocal(0).__setattr__("level", var4);
               var4 = null;
               var1.setline(108);
               var4 = var1.getlocal(0).__getattr__("level");
               var10000 = var4._gt(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(109);
                  throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Missing endmarker in MultiFile.readline()"));
               } else {
                  var1.setline(110);
                  var8 = PyString.fromInterned("");
                  var1.f_lasti = -1;
                  return var8;
               }
            }
         }
      }
   }

   public PyObject readlines$7(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      while(true) {
         var1.setline(114);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(115);
         var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(116);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(117);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
      }

      var1.setline(118);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject read$8(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("readlines").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$9(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(124);
         if (!var1.getlocal(0).__getattr__("readline").__call__(var2).__nonzero__()) {
            var1.setline(125);
            PyObject var3 = var1.getlocal(0).__getattr__("level");
            PyObject var10000 = var3._gt(Py.newInteger(1));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("last");
            }

            PyInteger var5;
            if (var10000.__nonzero__()) {
               var1.setline(126);
               var5 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(127);
            PyInteger var4 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"level", var4);
            var4 = null;
            var1.setline(128);
            var4 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"last", var4);
            var4 = null;
            var1.setline(129);
            if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
               var1.setline(130);
               PyObject var6 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
               var1.getlocal(0).__setattr__("start", var6);
               var4 = null;
            }

            var1.setline(131);
            var5 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(124);
      }
   }

   public PyObject push$10(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyObject var3 = var1.getlocal(0).__getattr__("level");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(135);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad MultiFile.push() call"));
      } else {
         var1.setline(136);
         var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__(var2, var1.getlocal(1));
         var1.setline(137);
         if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
            var1.setline(138);
            var1.getlocal(0).__getattr__("posstack").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("start"));
            var1.setline(139);
            var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
            var1.getlocal(0).__setattr__("start", var3);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject pop$11(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      PyObject var10000 = var3._eq(new PyList(Py.EmptyObjects));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(143);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad MultiFile.pop() call"));
      } else {
         var1.setline(144);
         var3 = var1.getlocal(0).__getattr__("level");
         var10000 = var3._le(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(145);
            PyInteger var4 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"last", var4);
            var3 = null;
         } else {
            var1.setline(147);
            var3 = var1.getlocal(0).__getattr__("lastpos")._add(var1.getlocal(0).__getattr__("start"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(148);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("level")._sub(Py.newInteger(1)));
         var1.getlocal(0).__setattr__("level", var3);
         var3 = null;
         var1.setline(149);
         var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2);
         var1.setline(150);
         if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
            var1.setline(151);
            var3 = var1.getlocal(0).__getattr__("posstack").__getattr__("pop").__call__(var2);
            var1.getlocal(0).__setattr__("start", var3);
            var3 = null;
            var1.setline(152);
            var3 = var1.getlocal(0).__getattr__("level");
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(153);
               var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("start"));
               var1.getlocal(0).__setattr__("lastpos", var3);
               var3 = null;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject is_data$12(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("--"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject section_divider$13(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      PyObject var3 = PyString.fromInterned("--")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject end_marker$14(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = PyString.fromInterned("--")._add(var1.getlocal(1))._add(PyString.fromInterned("--"));
      var1.f_lasti = -1;
      return var3;
   }

   public multifile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 36, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MultiFile$2 = Py.newCode(0, var2, var1, "MultiFile", 39, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "seekable"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 43, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$4 = Py.newCode(1, var2, var1, "tell", 53, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence", "here"};
      seek$5 = Py.newCode(3, var2, var1, "seek", 58, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "marker", "i", "sep"};
      readline$6 = Py.newCode(1, var2, var1, "readline", 75, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "line"};
      readlines$7 = Py.newCode(1, var2, var1, "readlines", 112, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read$8 = Py.newCode(1, var2, var1, "read", 120, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      next$9 = Py.newCode(1, var2, var1, "next", 123, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sep"};
      push$10 = Py.newCode(2, var2, var1, "push", 133, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "abslastpos"};
      pop$11 = Py.newCode(1, var2, var1, "pop", 141, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      is_data$12 = Py.newCode(2, var2, var1, "is_data", 155, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "str"};
      section_divider$13 = Py.newCode(2, var2, var1, "section_divider", 158, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "str"};
      end_marker$14 = Py.newCode(2, var2, var1, "end_marker", 161, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new multifile$py("multifile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(multifile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.MultiFile$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.tell$4(var2, var3);
         case 5:
            return this.seek$5(var2, var3);
         case 6:
            return this.readline$6(var2, var3);
         case 7:
            return this.readlines$7(var2, var3);
         case 8:
            return this.read$8(var2, var3);
         case 9:
            return this.next$9(var2, var3);
         case 10:
            return this.push$10(var2, var3);
         case 11:
            return this.pop$11(var2, var3);
         case 12:
            return this.is_data$12(var2, var3);
         case 13:
            return this.section_divider$13(var2, var3);
         case 14:
            return this.end_marker$14(var2, var3);
         default:
            return null;
      }
   }
}
