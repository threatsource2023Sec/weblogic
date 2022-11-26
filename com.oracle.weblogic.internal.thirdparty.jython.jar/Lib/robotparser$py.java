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
@Filename("robotparser.py")
public class robotparser$py extends PyFunctionTable implements PyRunnable {
   static robotparser$py self;
   static final PyCode f$0;
   static final PyCode RobotFileParser$1;
   static final PyCode __init__$2;
   static final PyCode mtime$3;
   static final PyCode modified$4;
   static final PyCode set_url$5;
   static final PyCode read$6;
   static final PyCode _add_entry$7;
   static final PyCode parse$8;
   static final PyCode can_fetch$9;
   static final PyCode __str__$10;
   static final PyCode RuleLine$11;
   static final PyCode __init__$12;
   static final PyCode applies_to$13;
   static final PyCode __str__$14;
   static final PyCode Entry$15;
   static final PyCode __init__$16;
   static final PyCode __str__$17;
   static final PyCode applies_to$18;
   static final PyCode allowance$19;
   static final PyCode URLopener$20;
   static final PyCode __init__$21;
   static final PyCode prompt_user_passwd$22;
   static final PyCode http_error_default$23;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" robotparser.py\n\n    Copyright (C) 2000  Bastian Kleineidam\n\n    You can choose between two licenses when using this package:\n    1) GNU GPLv2\n    2) PSF license for Python 2.2\n\n    The robots.txt Exclusion Protocol is implemented as specified in\n    http://www.robotstxt.org/norobots-rfc.txt\n\n"));
      var1.setline(12);
      PyString.fromInterned(" robotparser.py\n\n    Copyright (C) 2000  Bastian Kleineidam\n\n    You can choose between two licenses when using this package:\n    1) GNU GPLv2\n    2) PSF license for Python 2.2\n\n    The robots.txt Exclusion Protocol is implemented as specified in\n    http://www.robotstxt.org/norobots-rfc.txt\n\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var3);
      var3 = null;
      var1.setline(16);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("RobotFileParser")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(19);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("RobotFileParser", var6, RobotFileParser$1);
      var1.setlocal("RobotFileParser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(166);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("RuleLine", var6, RuleLine$11);
      var1.setlocal("RuleLine", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(184);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Entry", var6, Entry$15);
      var1.setlocal("Entry", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(220);
      var6 = new PyObject[]{var1.getname("urllib").__getattr__("FancyURLopener")};
      var4 = Py.makeClass("URLopener", var6, URLopener$20);
      var1.setlocal("URLopener", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject RobotFileParser$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" This class provides a set of methods to read, parse and answer\n    questions about a single robots.txt file.\n\n    "));
      var1.setline(23);
      PyString.fromInterned(" This class provides a set of methods to read, parse and answer\n    questions about a single robots.txt file.\n\n    ");
      var1.setline(25);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, mtime$3, PyString.fromInterned("Returns the time the robots.txt file was last fetched.\n\n        This is useful for long-running web spiders that need to\n        check for new robots.txt files periodically.\n\n        "));
      var1.setlocal("mtime", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, modified$4, PyString.fromInterned("Sets the time the robots.txt file was last fetched to the\n        current time.\n\n        "));
      var1.setlocal("modified", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_url$5, PyString.fromInterned("Sets the URL referring to a robots.txt file."));
      var1.setlocal("set_url", var4);
      var3 = null;
      var1.setline(55);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$6, PyString.fromInterned("Reads the robots.txt URL and feeds it to the parser."));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _add_entry$7, (PyObject)null);
      var1.setlocal("_add_entry", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$8, PyString.fromInterned("parse the input lines from a robots.txt file.\n           We allow that a user-agent: line is not preceded by\n           one or more blank lines."));
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, can_fetch$9, PyString.fromInterned("using the parsed robots.txt decide if useragent can fetch url"));
      var1.setlocal("can_fetch", var4);
      var3 = null;
      var1.setline(162);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$10, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"entries", var3);
      var3 = null;
      var1.setline(27);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("default_entry", var4);
      var3 = null;
      var1.setline(28);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("disallow_all", var4);
      var3 = null;
      var1.setline(29);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("allow_all", var4);
      var3 = null;
      var1.setline(30);
      var1.getlocal(0).__getattr__("set_url").__call__(var2, var1.getlocal(1));
      var1.setline(31);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"last_checked", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mtime$3(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyString.fromInterned("Returns the time the robots.txt file was last fetched.\n\n        This is useful for long-running web spiders that need to\n        check for new robots.txt files periodically.\n\n        ");
      var1.setline(40);
      PyObject var3 = var1.getlocal(0).__getattr__("last_checked");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject modified$4(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyString.fromInterned("Sets the time the robots.txt file was last fetched to the\n        current time.\n\n        ");
      var1.setline(47);
      PyObject var3 = imp.importOne("time", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getlocal(1).__getattr__("time").__call__(var2);
      var1.getlocal(0).__setattr__("last_checked", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_url$5(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Sets the URL referring to a robots.txt file.");
      var1.setline(52);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("url", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(1)).__getslice__(Py.newInteger(1), Py.newInteger(3), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("host", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("path", var5);
      var5 = null;
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$6(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyString.fromInterned("Reads the robots.txt URL and feeds it to the parser.");
      var1.setline(57);
      PyObject var3 = var1.getglobal("URLopener").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(1).__getattr__("open").__call__(var2, var1.getlocal(0).__getattr__("url"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(59);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(59);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(59);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(60);
            var1.getlocal(2).__getattr__("close").__call__(var2);
            var1.setline(61);
            var3 = var1.getlocal(1).__getattr__("errcode");
            var1.getlocal(0).__setattr__("errcode", var3);
            var3 = null;
            var1.setline(62);
            var3 = var1.getlocal(0).__getattr__("errcode");
            PyObject var6 = var3._in(new PyTuple(new PyObject[]{Py.newInteger(401), Py.newInteger(403)}));
            var3 = null;
            if (var6.__nonzero__()) {
               var1.setline(63);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("disallow_all", var3);
               var3 = null;
            } else {
               var1.setline(64);
               var3 = var1.getlocal(0).__getattr__("errcode");
               var6 = var3._ge(Py.newInteger(400));
               var3 = null;
               if (var6.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("errcode");
                  var6 = var3._lt(Py.newInteger(500));
                  var3 = null;
               }

               if (var6.__nonzero__()) {
                  var1.setline(65);
                  var3 = var1.getglobal("True");
                  var1.getlocal(0).__setattr__("allow_all", var3);
                  var3 = null;
               } else {
                  var1.setline(66);
                  var3 = var1.getlocal(0).__getattr__("errcode");
                  var6 = var3._eq(Py.newInteger(200));
                  var3 = null;
                  if (var6.__nonzero__()) {
                     var6 = var1.getlocal(3);
                  }

                  if (var6.__nonzero__()) {
                     var1.setline(67);
                     var1.getlocal(0).__getattr__("parse").__call__(var2, var1.getlocal(3));
                  }
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(59);
         var1.getlocal(4).__call__(var2, var1.getlocal(5).__getattr__("strip").__call__(var2));
      }
   }

   public PyObject _add_entry$7(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyString var3 = PyString.fromInterned("*");
      PyObject var10000 = var3._in(var1.getlocal(1).__getattr__("useragents"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(72);
         PyObject var4 = var1.getlocal(0).__getattr__("default_entry");
         var10000 = var4._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(74);
            var4 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("default_entry", var4);
            var3 = null;
         }
      } else {
         var1.setline(76);
         var1.getlocal(0).__getattr__("entries").__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$8(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("parse the input lines from a robots.txt file.\n           We allow that a user-agent: line is not preceded by\n           one or more blank lines.");
      var1.setline(86);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(87);
      var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(88);
      PyObject var6 = var1.getglobal("Entry").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(90);
      var1.getlocal(0).__getattr__("modified").__call__(var2);
      var1.setline(91);
      var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(91);
         PyObject var4 = var6.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(126);
            var6 = var1.getlocal(2);
            var10000 = var6._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(127);
               var1.getlocal(0).__getattr__("_add_entry").__call__(var2, var1.getlocal(4));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(92);
         PyObject var5 = var1.getlocal(3);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(3, var5);
         var1.setline(93);
         PyInteger var7;
         if (var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(94);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(Py.newInteger(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(95);
               var5 = var1.getglobal("Entry").__call__(var2);
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(96);
               var7 = Py.newInteger(0);
               var1.setlocal(2, var7);
               var5 = null;
            } else {
               var1.setline(97);
               var5 = var1.getlocal(2);
               var10000 = var5._eq(Py.newInteger(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(98);
                  var1.getlocal(0).__getattr__("_add_entry").__call__(var2, var1.getlocal(4));
                  var1.setline(99);
                  var5 = var1.getglobal("Entry").__call__(var2);
                  var1.setlocal(4, var5);
                  var5 = null;
                  var1.setline(100);
                  var7 = Py.newInteger(0);
                  var1.setlocal(2, var7);
                  var5 = null;
               }
            }
         }

         var1.setline(102);
         var5 = var1.getlocal(5).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(103);
         var5 = var1.getlocal(6);
         var10000 = var5._ge(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(104);
            var5 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(105);
         var5 = var1.getlocal(5).__getattr__("strip").__call__(var2);
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(106);
         if (!var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(108);
            var5 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(109);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var10000 = var5._eq(Py.newInteger(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(110);
               var5 = var1.getlocal(5).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2);
               var1.getlocal(5).__setitem__((PyObject)Py.newInteger(0), var5);
               var5 = null;
               var1.setline(111);
               var5 = var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(1)).__getattr__("strip").__call__(var2));
               var1.getlocal(5).__setitem__((PyObject)Py.newInteger(1), var5);
               var5 = null;
               var1.setline(112);
               var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
               var10000 = var5._eq(PyString.fromInterned("user-agent"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(113);
                  var5 = var1.getlocal(2);
                  var10000 = var5._eq(Py.newInteger(2));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(114);
                     var1.getlocal(0).__getattr__("_add_entry").__call__(var2, var1.getlocal(4));
                     var1.setline(115);
                     var5 = var1.getglobal("Entry").__call__(var2);
                     var1.setlocal(4, var5);
                     var5 = null;
                  }

                  var1.setline(116);
                  var1.getlocal(4).__getattr__("useragents").__getattr__("append").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(1)));
                  var1.setline(117);
                  var7 = Py.newInteger(1);
                  var1.setlocal(2, var7);
                  var5 = null;
               } else {
                  var1.setline(118);
                  var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
                  var10000 = var5._eq(PyString.fromInterned("disallow"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(119);
                     var5 = var1.getlocal(2);
                     var10000 = var5._ne(Py.newInteger(0));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(120);
                        var1.getlocal(4).__getattr__("rulelines").__getattr__("append").__call__(var2, var1.getglobal("RuleLine").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(1)), var1.getglobal("False")));
                        var1.setline(121);
                        var7 = Py.newInteger(2);
                        var1.setlocal(2, var7);
                        var5 = null;
                     }
                  } else {
                     var1.setline(122);
                     var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
                     var10000 = var5._eq(PyString.fromInterned("allow"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(123);
                        var5 = var1.getlocal(2);
                        var10000 = var5._ne(Py.newInteger(0));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(124);
                           var1.getlocal(4).__getattr__("rulelines").__getattr__("append").__call__(var2, var1.getglobal("RuleLine").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(1)), var1.getglobal("True")));
                           var1.setline(125);
                           var7 = Py.newInteger(2);
                           var1.setlocal(2, var7);
                           var5 = null;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject can_fetch$9(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("using the parsed robots.txt decide if useragent can fetch url");
      var1.setline(132);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("disallow_all").__nonzero__()) {
         var1.setline(133);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(134);
         if (var1.getlocal(0).__getattr__("allow_all").__nonzero__()) {
            var1.setline(135);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(141);
            if (var1.getlocal(0).__getattr__("last_checked").__not__().__nonzero__()) {
               var1.setline(142);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(146);
               PyObject var4 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getglobal("urllib").__getattr__("unquote").__call__(var2, var1.getlocal(2)));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(147);
               var4 = var1.getglobal("urlparse").__getattr__("urlunparse").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getlocal(3).__getattr__("path"), var1.getlocal(3).__getattr__("params"), var1.getlocal(3).__getattr__("query"), var1.getlocal(3).__getattr__("fragment")})));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(149);
               var4 = var1.getglobal("urllib").__getattr__("quote").__call__(var2, var1.getlocal(2));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(150);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(151);
                  PyString var6 = PyString.fromInterned("/");
                  var1.setlocal(2, var6);
                  var4 = null;
               }

               var1.setline(152);
               var4 = var1.getlocal(0).__getattr__("entries").__iter__();

               do {
                  var1.setline(152);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(156);
                     if (var1.getlocal(0).__getattr__("default_entry").__nonzero__()) {
                        var1.setline(157);
                        var3 = var1.getlocal(0).__getattr__("default_entry").__getattr__("allowance").__call__(var2, var1.getlocal(2));
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(159);
                        var3 = var1.getglobal("True");
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }

                  var1.setlocal(4, var5);
                  var1.setline(153);
               } while(!var1.getlocal(4).__getattr__("applies_to").__call__(var2, var1.getlocal(1)).__nonzero__());

               var1.setline(154);
               var3 = var1.getlocal(4).__getattr__("allowance").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject __str__$10(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      PyList var10002 = new PyList();
      PyObject var3 = var10002.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(163);
      var3 = var1.getlocal(0).__getattr__("entries").__iter__();

      while(true) {
         var1.setline(163);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(163);
            var1.dellocal(1);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(163);
         var1.getlocal(1).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2))._add(PyString.fromInterned("\n")));
      }
   }

   public PyObject RuleLine$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A rule line is a single \"Allow:\" (allowance==True) or \"Disallow:\"\n       (allowance==False) followed by a path."));
      var1.setline(168);
      PyString.fromInterned("A rule line is a single \"Allow:\" (allowance==True) or \"Disallow:\"\n       (allowance==False) followed by a path.");
      var1.setline(169);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, applies_to$13, (PyObject)null);
      var1.setlocal("applies_to", var4);
      var3 = null;
      var1.setline(180);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$14, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(172);
         var3 = var1.getglobal("True");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(173);
      var3 = var1.getglobal("urlparse").__getattr__("urlunparse").__call__(var2, var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(174);
      var3 = var1.getglobal("urllib").__getattr__("quote").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("path", var3);
      var3 = null;
      var1.setline(175);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("allowance", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject applies_to$13(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyObject var3 = var1.getlocal(0).__getattr__("path");
      PyObject var10000 = var3._eq(PyString.fromInterned("*"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(0).__getattr__("path"));
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$14(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      Object var10000 = var1.getlocal(0).__getattr__("allowance");
      if (((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("Allow");
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("Disallow");
      }

      PyObject var3 = ((PyObject)var10000)._add(PyString.fromInterned(": "))._add(var1.getlocal(0).__getattr__("path"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Entry$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An entry has one or more user-agents and zero or more rulelines"));
      var1.setline(185);
      PyString.fromInterned("An entry has one or more user-agents and zero or more rulelines");
      var1.setline(186);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$16, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(190);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$17, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(198);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, applies_to$18, PyString.fromInterned("check if this entry applies to the specified agent"));
      var1.setlocal("applies_to", var4);
      var3 = null;
      var1.setline(211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, allowance$19, PyString.fromInterned("Preconditions:\n        - our agent applies to this entry\n        - filename is URL decoded"));
      var1.setlocal("allowance", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"useragents", var3);
      var3 = null;
      var1.setline(188);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"rulelines", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$17(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(192);
      PyObject var5 = var1.getlocal(0).__getattr__("useragents").__iter__();

      while(true) {
         var1.setline(192);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(194);
            var5 = var1.getlocal(0).__getattr__("rulelines").__iter__();

            while(true) {
               var1.setline(194);
               var4 = var5.__iternext__();
               if (var4 == null) {
                  var1.setline(196);
                  var5 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(3, var4);
               var1.setline(195);
               var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(3)), PyString.fromInterned("\n")})));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(193);
         var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("User-agent: "), var1.getlocal(2), PyString.fromInterned("\n")})));
      }
   }

   public PyObject applies_to$18(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyString.fromInterned("check if this entry applies to the specified agent");
      var1.setline(201);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getlocal(0).__getattr__("useragents").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(202);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(209);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(203);
         var5 = var1.getlocal(2);
         var10000 = var5._eq(PyString.fromInterned("*"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(205);
            var5 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(206);
         PyObject var6 = var1.getlocal(2).__getattr__("lower").__call__(var2);
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(207);
         var6 = var1.getlocal(2);
         var10000 = var6._in(var1.getlocal(1));
         var6 = null;
      } while(!var10000.__nonzero__());

      var1.setline(208);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject allowance$19(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      PyString.fromInterned("Preconditions:\n        - our agent applies to this entry\n        - filename is URL decoded");
      var1.setline(215);
      PyObject var3 = var1.getlocal(0).__getattr__("rulelines").__iter__();

      PyObject var5;
      do {
         var1.setline(215);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(218);
            var5 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(216);
      } while(!var1.getlocal(2).__getattr__("applies_to").__call__(var2, var1.getlocal(1)).__nonzero__());

      var1.setline(217);
      var5 = var1.getlocal(2).__getattr__("allowance");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject URLopener$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(221);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, prompt_user_passwd$22, (PyObject)null);
      var1.setlocal("prompt_user_passwd", var4);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, http_error_default$23, (PyObject)null);
      var1.setlocal("http_error_default", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyObject var10000 = var1.getglobal("urllib").__getattr__("FancyURLopener").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.setline(223);
      PyInteger var5 = Py.newInteger(200);
      var1.getlocal(0).__setattr__((String)"errcode", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject prompt_user_passwd$22(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject http_error_default$23(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("errcode", var3);
      var3 = null;
      var1.setline(232);
      PyObject var10000 = var1.getglobal("urllib").__getattr__("FancyURLopener").__getattr__("http_error_default");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public robotparser$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      RobotFileParser$1 = Py.newCode(0, var2, var1, "RobotFileParser", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "url"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 25, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      mtime$3 = Py.newCode(1, var2, var1, "mtime", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "time"};
      modified$4 = Py.newCode(1, var2, var1, "modified", 42, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url"};
      set_url$5 = Py.newCode(2, var2, var1, "set_url", 50, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opener", "f", "lines", "_[59_17]", "line"};
      read$6 = Py.newCode(1, var2, var1, "read", 55, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "entry"};
      _add_entry$7 = Py.newCode(2, var2, var1, "_add_entry", 69, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "state", "linenumber", "entry", "line", "i"};
      parse$8 = Py.newCode(2, var2, var1, "parse", 78, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "useragent", "url", "parsed_url", "entry"};
      can_fetch$9 = Py.newCode(3, var2, var1, "can_fetch", 130, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[163_24]", "entry"};
      __str__$10 = Py.newCode(1, var2, var1, "__str__", 162, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RuleLine$11 = Py.newCode(0, var2, var1, "RuleLine", 166, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "allowance"};
      __init__$12 = Py.newCode(3, var2, var1, "__init__", 169, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename"};
      applies_to$13 = Py.newCode(2, var2, var1, "applies_to", 177, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$14 = Py.newCode(1, var2, var1, "__str__", 180, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Entry$15 = Py.newCode(0, var2, var1, "Entry", 184, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$16 = Py.newCode(1, var2, var1, "__init__", 186, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ret", "agent", "line"};
      __str__$17 = Py.newCode(1, var2, var1, "__str__", 190, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "useragent", "agent"};
      applies_to$18 = Py.newCode(2, var2, var1, "applies_to", 198, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "line"};
      allowance$19 = Py.newCode(2, var2, var1, "allowance", 211, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      URLopener$20 = Py.newCode(0, var2, var1, "URLopener", 220, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      __init__$21 = Py.newCode(2, var2, var1, "__init__", 221, true, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "realm"};
      prompt_user_passwd$22 = Py.newCode(3, var2, var1, "prompt_user_passwd", 225, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "fp", "errcode", "errmsg", "headers"};
      http_error_default$23 = Py.newCode(6, var2, var1, "http_error_default", 230, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new robotparser$py("robotparser$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(robotparser$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.RobotFileParser$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.mtime$3(var2, var3);
         case 4:
            return this.modified$4(var2, var3);
         case 5:
            return this.set_url$5(var2, var3);
         case 6:
            return this.read$6(var2, var3);
         case 7:
            return this._add_entry$7(var2, var3);
         case 8:
            return this.parse$8(var2, var3);
         case 9:
            return this.can_fetch$9(var2, var3);
         case 10:
            return this.__str__$10(var2, var3);
         case 11:
            return this.RuleLine$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.applies_to$13(var2, var3);
         case 14:
            return this.__str__$14(var2, var3);
         case 15:
            return this.Entry$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this.__str__$17(var2, var3);
         case 18:
            return this.applies_to$18(var2, var3);
         case 19:
            return this.allowance$19(var2, var3);
         case 20:
            return this.URLopener$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.prompt_user_passwd$22(var2, var3);
         case 23:
            return this.http_error_default$23(var2, var3);
         default:
            return null;
      }
   }
}
