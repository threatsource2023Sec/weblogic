package email;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/iterators.py")
public class iterators$py extends PyFunctionTable implements PyRunnable {
   static iterators$py self;
   static final PyCode f$0;
   static final PyCode walk$1;
   static final PyCode body_line_iterator$2;
   static final PyCode typed_subpart_iterator$3;
   static final PyCode _structure$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Various types of useful iterators and generators."));
      var1.setline(5);
      PyString.fromInterned("Various types of useful iterators and generators.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("body_line_iterator"), PyString.fromInterned("typed_subpart_iterator"), PyString.fromInterned("walk")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(14);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(15);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(20);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, walk$1, PyString.fromInterned("Walk over the message tree, yielding each subpart.\n\n    The walk is performed in depth-first order.  This method is a\n    generator.\n    "));
      var1.setlocal("walk", var8);
      var3 = null;
      var1.setline(35);
      var7 = new PyObject[]{var1.getname("False")};
      var8 = new PyFunction(var1.f_globals, var7, body_line_iterator$2, PyString.fromInterned("Iterate over the parts, returning string payloads line-by-line.\n\n    Optional decode (default False) is passed through to .get_payload().\n    "));
      var1.setlocal("body_line_iterator", var8);
      var3 = null;
      var1.setline(47);
      var7 = new PyObject[]{PyString.fromInterned("text"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, typed_subpart_iterator$3, PyString.fromInterned("Iterate over the subparts with a given MIME type.\n\n    Use `maintype' as the main MIME type to match against; this defaults to\n    \"text\".  Optional `subtype' is the MIME subtype to match against; if\n    omitted, only the main type is matched.\n    "));
      var1.setlocal("typed_subpart_iterator", var8);
      var3 = null;
      var1.setline(61);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("False")};
      var8 = new PyFunction(var1.f_globals, var7, _structure$4, PyString.fromInterned("A handy debugging aid"));
      var1.setlocal("_structure", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject walk$1(PyFrame var1, ThreadState var2) {
      label43: {
         Object var10000;
         PyObject var3;
         PyObject var4;
         PyObject var5;
         PyObject var6;
         Object[] var7;
         Object[] var8;
         PyObject var9;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(25);
               PyString.fromInterned("Walk over the message tree, yielding each subpart.\n\n    The walk is performed in depth-first order.  This method is a\n    generator.\n    ");
               var1.setline(26);
               var1.setline(26);
               var9 = var1.getlocal(0);
               var1.f_lasti = 1;
               var8 = new Object[3];
               var1.f_savedlocals = var8;
               return var9;
            case 1:
               var8 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var9 = (PyObject)var10000;
               var1.setline(27);
               if (!var1.getlocal(0).__getattr__("is_multipart").__call__(var2).__nonzero__()) {
                  break label43;
               }

               var1.setline(28);
               var3 = var1.getlocal(0).__getattr__("get_payload").__call__(var2).__iter__();
               var1.setline(28);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break label43;
               }

               var1.setlocal(1, var4);
               var1.setline(29);
               var5 = var1.getlocal(1).__getattr__("walk").__call__(var2).__iter__();
               break;
            case 2:
               var7 = var1.f_savedlocals;
               var3 = (PyObject)var7[3];
               var4 = (PyObject)var7[4];
               var5 = (PyObject)var7[5];
               var6 = (PyObject)var7[6];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var9 = (PyObject)var10000;
         }

         while(true) {
            var1.setline(29);
            var6 = var5.__iternext__();
            if (var6 != null) {
               var1.setlocal(2, var6);
               var1.setline(30);
               var1.setline(30);
               var9 = var1.getlocal(2);
               var1.f_lasti = 2;
               var7 = new Object[]{null, null, null, var3, var4, var5, var6};
               var1.f_savedlocals = var7;
               return var9;
            }

            var1.setline(28);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(29);
            var5 = var1.getlocal(1).__getattr__("walk").__call__(var2).__iter__();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject body_line_iterator$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var10;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(39);
            PyString.fromInterned("Iterate over the parts, returning string payloads line-by-line.\n\n    Optional decode (default False) is passed through to .get_payload().\n    ");
            var1.setline(40);
            var3 = var1.getlocal(0).__getattr__("walk").__call__(var2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var10 = (PyObject)var10000;
            var1.setline(43);
            var6 = var5.__iternext__();
            if (var6 != null) {
               var1.setlocal(4, var6);
               var1.setline(44);
               var1.setline(44);
               var10 = var1.getlocal(4);
               var1.f_lasti = 1;
               var7 = new Object[]{null, null, null, var3, var4, var5, var6};
               var1.f_savedlocals = var7;
               return var10;
            }
      }

      do {
         do {
            var1.setline(40);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(41);
            var10 = var1.getlocal(2).__getattr__("get_payload");
            PyObject[] var8 = new PyObject[]{var1.getlocal(1)};
            String[] var9 = new String[]{"decode"};
            var10 = var10.__call__(var2, var8, var9);
            var5 = null;
            var5 = var10;
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(42);
         } while(!var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__());

         var1.setline(43);
         var5 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(3)).__iter__();
         var1.setline(43);
         var6 = var5.__iternext__();
      } while(var6 == null);

      var1.setlocal(4, var6);
      var1.setline(44);
      var1.setline(44);
      var10 = var1.getlocal(4);
      var1.f_lasti = 1;
      var7 = new Object[]{null, null, null, var3, var4, var5, var6};
      var1.f_savedlocals = var7;
      return var10;
   }

   public PyObject typed_subpart_iterator$3(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(53);
            PyString.fromInterned("Iterate over the subparts with a given MIME type.\n\n    Use `maintype' as the main MIME type to match against; this defaults to\n    \"text\".  Optional `subtype' is the MIME subtype to match against; if\n    omitted, only the main type is matched.\n    ");
            var1.setline(54);
            var3 = var1.getlocal(0).__getattr__("walk").__call__(var2).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(54);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(55);
         PyObject var6 = var1.getlocal(3).__getattr__("get_content_maintype").__call__(var2);
         var7 = var6._eq(var1.getlocal(1));
         var5 = null;
         if (var7.__nonzero__()) {
            var1.setline(56);
            var6 = var1.getlocal(2);
            var7 = var6._is(var1.getglobal("None"));
            var5 = null;
            if (!var7.__nonzero__()) {
               var6 = var1.getlocal(3).__getattr__("get_content_subtype").__call__(var2);
               var7 = var6._eq(var1.getlocal(2));
               var5 = null;
            }

            if (var7.__nonzero__()) {
               var1.setline(57);
               var1.setline(57);
               var7 = var1.getlocal(3);
               var1.f_lasti = 1;
               var5 = new Object[7];
               var5[3] = var3;
               var5[4] = var4;
               var1.f_savedlocals = var5;
               return var7;
            }
         }
      }
   }

   public PyObject _structure$4(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned("A handy debugging aid");
      var1.setline(63);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(64);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(65);
      var3 = PyString.fromInterned(" ")._mul(var1.getlocal(2)._mul(Py.newInteger(4)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getlocal(1);
      Py.printComma(var3, var1.getlocal(4)._add(var1.getlocal(0).__getattr__("get_content_type").__call__(var2)));
      var1.setline(67);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(68);
         var3 = var1.getlocal(1);
         Py.println(var3, PyString.fromInterned("[%s]")._mod(var1.getlocal(0).__getattr__("get_default_type").__call__(var2)));
      } else {
         var1.setline(70);
         var3 = var1.getlocal(1);
         Py.printlnv(var3);
      }

      var1.setline(71);
      if (var1.getlocal(0).__getattr__("is_multipart").__call__(var2).__nonzero__()) {
         var1.setline(72);
         var3 = var1.getlocal(0).__getattr__("get_payload").__call__(var2).__iter__();

         while(true) {
            var1.setline(72);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(73);
            var1.getglobal("_structure").__call__(var2, var1.getlocal(5), var1.getlocal(1), var1.getlocal(2)._add(Py.newInteger(1)), var1.getlocal(3));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public iterators$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "subpart", "subsubpart"};
      walk$1 = Py.newCode(1, var2, var1, "walk", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"msg", "decode", "subpart", "payload", "line"};
      body_line_iterator$2 = Py.newCode(2, var2, var1, "body_line_iterator", 35, false, false, self, 2, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"msg", "maintype", "subtype", "subpart"};
      typed_subpart_iterator$3 = Py.newCode(3, var2, var1, "typed_subpart_iterator", 47, false, false, self, 3, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"msg", "fp", "level", "include_default", "tab", "subpart"};
      _structure$4 = Py.newCode(4, var2, var1, "_structure", 61, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new iterators$py("email/iterators$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(iterators$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.walk$1(var2, var3);
         case 2:
            return this.body_line_iterator$2(var2, var3);
         case 3:
            return this.typed_subpart_iterator$3(var2, var3);
         case 4:
            return this._structure$4(var2, var3);
         default:
            return null;
      }
   }
}
