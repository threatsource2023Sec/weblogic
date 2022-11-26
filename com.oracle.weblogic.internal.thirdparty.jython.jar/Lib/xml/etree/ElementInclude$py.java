package xml.etree;

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
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("xml/etree/ElementInclude.py")
public class ElementInclude$py extends PyFunctionTable implements PyRunnable {
   static ElementInclude$py self;
   static final PyCode f$0;
   static final PyCode FatalIncludeError$1;
   static final PyCode default_loader$2;
   static final PyCode include$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var3);
      var3 = null;
      var1.setline(52);
      String[] var5 = new String[]{"ElementTree"};
      PyObject[] var6 = imp.importFrom("", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("ElementTree", var4);
      var4 = null;
      var1.setline(54);
      PyString var7 = PyString.fromInterned("{http://www.w3.org/2001/XInclude}");
      var1.setlocal("XINCLUDE", var7);
      var3 = null;
      var1.setline(56);
      var3 = var1.getname("XINCLUDE")._add(PyString.fromInterned("include"));
      var1.setlocal("XINCLUDE_INCLUDE", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getname("XINCLUDE")._add(PyString.fromInterned("fallback"));
      var1.setlocal("XINCLUDE_FALLBACK", var3);
      var3 = null;
      var1.setline(62);
      var6 = new PyObject[]{var1.getname("SyntaxError")};
      var4 = Py.makeClass("FatalIncludeError", var6, FatalIncludeError$1);
      var1.setlocal("FatalIncludeError", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(77);
      var6 = new PyObject[]{var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var6, default_loader$2, (PyObject)null);
      var1.setlocal("default_loader", var8);
      var3 = null;
      var1.setline(98);
      var6 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, include$3, (PyObject)null);
      var1.setlocal("include", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FatalIncludeError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(63);
      return var1.getf_locals();
   }

   public PyObject default_loader$2(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(0)))).__enter__(var2);

      label21: {
         try {
            var1.setlocal(3, var4);
            var1.setline(79);
            var4 = var1.getlocal(1);
            PyObject var10000 = var4._eq(PyString.fromInterned("xml"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(80);
               var4 = var1.getglobal("ElementTree").__getattr__("parse").__call__(var2, var1.getlocal(3)).__getattr__("getroot").__call__(var2);
               var1.setlocal(4, var4);
               var4 = null;
            } else {
               var1.setline(82);
               var4 = var1.getlocal(3).__getattr__("read").__call__(var2);
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(83);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(84);
                  var4 = var1.getlocal(4).__getattr__("decode").__call__(var2, var1.getlocal(2));
                  var1.setlocal(4, var4);
                  var4 = null;
               }
            }
         } catch (Throwable var5) {
            if (var3.__exit__(var2, Py.setException(var5, var1))) {
               break label21;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.setline(85);
      PyObject var6 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject include$3(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(100);
         var3 = var1.getglobal("default_loader");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(102);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal(2, var4);
      var3 = null;

      while(true) {
         while(true) {
            var1.setline(103);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(104);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(105);
            var3 = var1.getlocal(3).__getattr__("tag");
            var10000 = var3._eq(var1.getglobal("XINCLUDE_INCLUDE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(107);
               var3 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("href"));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(108);
               var3 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("parse"), (PyObject)PyString.fromInterned("xml"));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(109);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(PyString.fromInterned("xml"));
               var3 = null;
               Object var5;
               if (!var10000.__nonzero__()) {
                  var1.setline(119);
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(PyString.fromInterned("text"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(133);
                     throw Py.makeException(var1.getglobal("FatalIncludeError").__call__(var2, PyString.fromInterned("unknown parse type in xi:include tag (%r)")._mod(var1.getlocal(5))));
                  }

                  var1.setline(120);
                  var3 = var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("encoding")));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(121);
                  var3 = var1.getlocal(7);
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(122);
                     throw Py.makeException(var1.getglobal("FatalIncludeError").__call__(var2, PyString.fromInterned("cannot load %r as %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}))));
                  }

                  var1.setline(125);
                  Object var10001;
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(126);
                     var3 = var1.getlocal(0).__getitem__(var1.getlocal(2)._sub(Py.newInteger(1)));
                     var1.setlocal(6, var3);
                     var3 = null;
                     var1.setline(127);
                     var5 = var1.getlocal(6).__getattr__("tail");
                     if (!((PyObject)var5).__nonzero__()) {
                        var5 = PyString.fromInterned("");
                     }

                     var10000 = ((PyObject)var5)._add(var1.getlocal(7));
                     var10001 = var1.getlocal(3).__getattr__("tail");
                     if (!((PyObject)var10001).__nonzero__()) {
                        var10001 = PyString.fromInterned("");
                     }

                     var3 = var10000._add((PyObject)var10001);
                     var1.getlocal(6).__setattr__("tail", var3);
                     var3 = null;
                  } else {
                     var1.setline(129);
                     var5 = var1.getlocal(0).__getattr__("text");
                     if (!((PyObject)var5).__nonzero__()) {
                        var5 = PyString.fromInterned("");
                     }

                     var10000 = ((PyObject)var5)._add(var1.getlocal(7));
                     var10001 = var1.getlocal(3).__getattr__("tail");
                     if (!((PyObject)var10001).__nonzero__()) {
                        var10001 = PyString.fromInterned("");
                     }

                     var3 = var10000._add((PyObject)var10001);
                     var1.getlocal(0).__setattr__("text", var3);
                     var3 = null;
                  }

                  var1.setline(130);
                  var1.getlocal(0).__delitem__(var1.getlocal(2));
                  continue;
               }

               var1.setline(110);
               var3 = var1.getlocal(1).__call__(var2, var1.getlocal(4), var1.getlocal(5));
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(111);
               var3 = var1.getlocal(6);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(112);
                  throw Py.makeException(var1.getglobal("FatalIncludeError").__call__(var2, PyString.fromInterned("cannot load %r as %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}))));
               }

               var1.setline(115);
               var3 = var1.getglobal("copy").__getattr__("copy").__call__(var2, var1.getlocal(6));
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(116);
               if (var1.getlocal(3).__getattr__("tail").__nonzero__()) {
                  var1.setline(117);
                  var5 = var1.getlocal(6).__getattr__("tail");
                  if (!((PyObject)var5).__nonzero__()) {
                     var5 = PyString.fromInterned("");
                  }

                  var3 = ((PyObject)var5)._add(var1.getlocal(3).__getattr__("tail"));
                  var1.getlocal(6).__setattr__("tail", var3);
                  var3 = null;
               }

               var1.setline(118);
               var3 = var1.getlocal(6);
               var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
               var3 = null;
            } else {
               var1.setline(136);
               var3 = var1.getlocal(3).__getattr__("tag");
               var10000 = var3._eq(var1.getglobal("XINCLUDE_FALLBACK"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(137);
                  throw Py.makeException(var1.getglobal("FatalIncludeError").__call__(var2, PyString.fromInterned("xi:fallback tag must be child of xi:include (%r)")._mod(var1.getlocal(3).__getattr__("tag"))));
               }

               var1.setline(141);
               var1.getglobal("include").__call__(var2, var1.getlocal(3), var1.getlocal(1));
            }

            var1.setline(142);
            var3 = var1.getlocal(2)._add(Py.newInteger(1));
            var1.setlocal(2, var3);
            var3 = null;
         }
      }
   }

   public ElementInclude$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FatalIncludeError$1 = Py.newCode(0, var2, var1, "FatalIncludeError", 62, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"href", "parse", "encoding", "file", "data"};
      default_loader$2 = Py.newCode(3, var2, var1, "default_loader", 77, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"elem", "loader", "i", "e", "href", "parse", "node", "text"};
      include$3 = Py.newCode(2, var2, var1, "include", 98, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ElementInclude$py("xml/etree/ElementInclude$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ElementInclude$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FatalIncludeError$1(var2, var3);
         case 2:
            return this.default_loader$2(var2, var3);
         case 3:
            return this.include$3(var2, var3);
         default:
            return null;
      }
   }
}
