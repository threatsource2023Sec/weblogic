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
@Filename("hmac.py")
public class hmac$py extends PyFunctionTable implements PyRunnable {
   static hmac$py self;
   static final PyCode f$0;
   static final PyCode HMAC$1;
   static final PyCode __init__$2;
   static final PyCode f$3;
   static final PyCode update$4;
   static final PyCode copy$5;
   static final PyCode _current$6;
   static final PyCode digest$7;
   static final PyCode hexdigest$8;
   static final PyCode new$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("HMAC (Keyed-Hashing for Message Authentication) Python module.\n\nImplements the HMAC algorithm as described by RFC 2104.\n"));
      var1.setline(4);
      PyString.fromInterned("HMAC (Keyed-Hashing for Message Authentication) Python module.\n\nImplements the HMAC algorithm as described by RFC 2104.\n");
      var1.setline(6);
      PyObject var3 = imp.importOneAs("warnings", var1, -1);
      var1.setlocal("_warnings", var3);
      var3 = null;
      var1.setline(8);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal("_[8_21]", var3);
      var3 = null;
      var1.setline(8);
      var3 = var1.getname("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(8);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(8);
            var1.dellocal("_[8_21]");
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal("trans_5C", var3);
            var3 = null;
            var1.setline(9);
            var10000 = PyString.fromInterned("").__getattr__("join");
            var10002 = new PyList();
            var3 = var10002.__getattr__("append");
            var1.setlocal("_[9_21]", var3);
            var3 = null;
            var1.setline(9);
            var3 = var1.getname("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

            while(true) {
               var1.setline(9);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(9);
                  var1.dellocal("_[9_21]");
                  var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.setlocal("trans_36", var3);
                  var3 = null;
                  var1.setline(13);
                  var3 = var1.getname("None");
                  var1.setlocal("digest_size", var3);
                  var3 = null;
                  var1.setline(18);
                  PyList var5 = new PyList(Py.EmptyObjects);
                  var1.setlocal("_secret_backdoor_key", var5);
                  var3 = null;
                  var1.setline(20);
                  PyObject[] var6 = Py.EmptyObjects;
                  var4 = Py.makeClass("HMAC", var6, HMAC$1);
                  var1.setlocal("HMAC", var4);
                  var4 = null;
                  Arrays.fill(var6, (Object)null);
                  var1.setline(122);
                  var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
                  PyFunction var7 = new PyFunction(var1.f_globals, var6, new$9, PyString.fromInterned("Create a new hashing object and return it.\n\n    key: The starting key for the hash.\n    msg: if available, will immediately be hashed into the object's starting\n    state.\n\n    You can now feed arbitrary strings into the object using its update()\n    method, and can ask for the hash value at any time by calling its digest()\n    method.\n    "));
                  var1.setlocal("new", var7);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal("x", var4);
               var1.setline(9);
               var1.getname("_[9_21]").__call__(var2, var1.getname("chr").__call__(var2, var1.getname("x")._xor(Py.newInteger(54))));
            }
         }

         var1.setlocal("x", var4);
         var1.setline(8);
         var1.getname("_[8_21]").__call__(var2, var1.getname("chr").__call__(var2, var1.getname("x")._xor(Py.newInteger(92))));
      }
   }

   public PyObject HMAC$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("RFC 2104 HMAC class.  Also complies with RFC 4231.\n\n    This supports the API for Cryptographic Hash Functions (PEP 247).\n    "));
      var1.setline(24);
      PyString.fromInterned("RFC 2104 HMAC class.  Also complies with RFC 4231.\n\n    This supports the API for Cryptographic Hash Functions (PEP 247).\n    ");
      var1.setline(25);
      PyInteger var3 = Py.newInteger(64);
      var1.setlocal("blocksize", var3);
      var3 = null;
      var1.setline(27);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, PyString.fromInterned("Create a new HMAC object.\n\n        key:       key for the keyed hash object.\n        msg:       Initial input for the hash, if provided.\n        digestmod: A module supporting PEP 247.  *OR*\n                   A hashlib constructor returning a new hash object.\n                   Defaults to hashlib.md5.\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(80);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, update$4, PyString.fromInterned("Update this hashing object with the string msg.\n        "));
      var1.setlocal("update", var5);
      var3 = null;
      var1.setline(85);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, copy$5, PyString.fromInterned("Return a separate copy of this hashing object.\n\n        An update to this copy won't affect the original object.\n        "));
      var1.setlocal("copy", var5);
      var3 = null;
      var1.setline(97);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _current$6, PyString.fromInterned("Return a hash object for the current state.\n\n        To be used only internally with digest() and hexdigest().\n        "));
      var1.setlocal("_current", var5);
      var3 = null;
      var1.setline(106);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, digest$7, PyString.fromInterned("Return the hash value of this hashing object.\n\n        This returns a string containing 8-bit data.  The object is\n        not altered in any way by this function; you can continue\n        updating the object after calling this function.\n        "));
      var1.setlocal("digest", var5);
      var3 = null;
      var1.setline(116);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, hexdigest$8, PyString.fromInterned("Like digest(), but returns a string of hexadecimal digits instead.\n        "));
      var1.setlocal("hexdigest", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.to_cell(3, 0);
      var1.setline(35);
      PyString.fromInterned("Create a new HMAC object.\n\n        key:       key for the keyed hash object.\n        msg:       Initial input for the hash, if provided.\n        digestmod: A module supporting PEP 247.  *OR*\n                   A hashlib constructor returning a new hash object.\n                   Defaults to hashlib.md5.\n        ");
      var1.setline(37);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("_secret_backdoor_key"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(38);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(40);
         var3 = var1.getderef(0);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(41);
            var3 = imp.importOne("hashlib", var1, -1);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(42);
            var3 = var1.getlocal(4).__getattr__("md5");
            var1.setderef(0, var3);
            var3 = null;
         }

         var1.setline(44);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
            var1.setline(45);
            var3 = var1.getderef(0);
            var1.getlocal(0).__setattr__("digest_cons", var3);
            var3 = null;
         } else {
            var1.setline(47);
            var1.setline(47);
            PyObject[] var4 = new PyObject[]{PyString.fromInterned("")};
            PyObject[] var10003 = var4;
            PyObject var10002 = var1.f_globals;
            PyCode var10004 = f$3;
            var4 = new PyObject[]{var1.getclosure(0)};
            PyFunction var5 = new PyFunction(var10002, var10003, var10004, var4);
            var1.getlocal(0).__setattr__((String)"digest_cons", var5);
            var3 = null;
         }

         var1.setline(49);
         var3 = var1.getlocal(0).__getattr__("digest_cons").__call__(var2);
         var1.getlocal(0).__setattr__("outer", var3);
         var3 = null;
         var1.setline(50);
         var3 = var1.getlocal(0).__getattr__("digest_cons").__call__(var2);
         var1.getlocal(0).__setattr__("inner", var3);
         var3 = null;
         var1.setline(51);
         var3 = var1.getlocal(0).__getattr__("inner").__getattr__("digest_size");
         var1.getlocal(0).__setattr__("digest_size", var3);
         var3 = null;
         var1.setline(53);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("inner"), (PyObject)PyString.fromInterned("block_size")).__nonzero__()) {
            var1.setline(54);
            var3 = var1.getlocal(0).__getattr__("inner").__getattr__("block_size");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(55);
            var3 = var1.getlocal(5);
            var10000 = var3._lt(Py.newInteger(16));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(58);
               var1.getglobal("_warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("block_size of %d seems too small; using our default of %d.")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(0).__getattr__("blocksize")})), (PyObject)var1.getglobal("RuntimeWarning"), (PyObject)Py.newInteger(2));
               var1.setline(61);
               var3 = var1.getlocal(0).__getattr__("blocksize");
               var1.setlocal(5, var3);
               var3 = null;
            }
         } else {
            var1.setline(63);
            var1.getglobal("_warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("No block_size attribute on given digest object; Assuming %d.")._mod(var1.getlocal(0).__getattr__("blocksize")), (PyObject)var1.getglobal("RuntimeWarning"), (PyObject)Py.newInteger(2));
            var1.setline(66);
            var3 = var1.getlocal(0).__getattr__("blocksize");
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(68);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(var1.getlocal(5));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(69);
            var3 = var1.getlocal(0).__getattr__("digest_cons").__call__(var2, var1.getlocal(1)).__getattr__("digest").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(71);
         var3 = var1.getlocal(1)._add(var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))._mul(var1.getlocal(5)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(1)))));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(72);
         var1.getlocal(0).__getattr__("outer").__getattr__("update").__call__(var2, var1.getlocal(1).__getattr__("translate").__call__(var2, var1.getglobal("trans_5C")));
         var1.setline(73);
         var1.getlocal(0).__getattr__("inner").__getattr__("update").__call__(var2, var1.getlocal(1).__getattr__("translate").__call__(var2, var1.getglobal("trans_36")));
         var1.setline(74);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(75);
            var1.getlocal(0).__getattr__("update").__call__(var2, var1.getlocal(2));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject f$3(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getderef(0).__getattr__("new").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject update$4(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Update this hashing object with the string msg.\n        ");
      var1.setline(83);
      var1.getlocal(0).__getattr__("inner").__getattr__("update").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy$5(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Return a separate copy of this hashing object.\n\n        An update to this copy won't affect the original object.\n        ");
      var1.setline(90);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getglobal("_secret_backdoor_key"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getlocal(0).__getattr__("digest_cons");
      var1.getlocal(1).__setattr__("digest_cons", var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getlocal(0).__getattr__("digest_size");
      var1.getlocal(1).__setattr__("digest_size", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getlocal(0).__getattr__("inner").__getattr__("copy").__call__(var2);
      var1.getlocal(1).__setattr__("inner", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getlocal(0).__getattr__("outer").__getattr__("copy").__call__(var2);
      var1.getlocal(1).__setattr__("outer", var3);
      var3 = null;
      var1.setline(95);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _current$6(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("Return a hash object for the current state.\n\n        To be used only internally with digest() and hexdigest().\n        ");
      var1.setline(102);
      PyObject var3 = var1.getlocal(0).__getattr__("outer").__getattr__("copy").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(103);
      var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("inner").__getattr__("digest").__call__(var2));
      var1.setline(104);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject digest$7(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyString.fromInterned("Return the hash value of this hashing object.\n\n        This returns a string containing 8-bit data.  The object is\n        not altered in any way by this function; you can continue\n        updating the object after calling this function.\n        ");
      var1.setline(113);
      PyObject var3 = var1.getlocal(0).__getattr__("_current").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(114);
      var3 = var1.getlocal(1).__getattr__("digest").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject hexdigest$8(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyString.fromInterned("Like digest(), but returns a string of hexadecimal digits instead.\n        ");
      var1.setline(119);
      PyObject var3 = var1.getlocal(0).__getattr__("_current").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(1).__getattr__("hexdigest").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject new$9(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyString.fromInterned("Create a new hashing object and return it.\n\n    key: The starting key for the hash.\n    msg: if available, will immediately be hashed into the object's starting\n    state.\n\n    You can now feed arbitrary strings into the object using its update()\n    method, and can ask for the hash value at any time by calling its digest()\n    method.\n    ");
      var1.setline(133);
      PyObject var3 = var1.getglobal("HMAC").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public hmac$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HMAC$1 = Py.newCode(0, var2, var1, "HMAC", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "msg", "digestmod", "hashlib", "blocksize"};
      String[] var10001 = var2;
      hmac$py var10007 = self;
      var2 = new String[]{"digestmod"};
      __init__$2 = Py.newCode(4, var10001, var1, "__init__", 27, false, false, var10007, 2, var2, (String[])null, 0, 4097);
      var2 = new String[]{"d"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"digestmod"};
      f$3 = Py.newCode(1, var10001, var1, "<lambda>", 47, false, false, var10007, 3, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "msg"};
      update$4 = Py.newCode(2, var2, var1, "update", 80, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      copy$5 = Py.newCode(1, var2, var1, "copy", 85, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h"};
      _current$6 = Py.newCode(1, var2, var1, "_current", 97, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h"};
      digest$7 = Py.newCode(1, var2, var1, "digest", 106, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "h"};
      hexdigest$8 = Py.newCode(1, var2, var1, "hexdigest", 116, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key", "msg", "digestmod"};
      new$9 = Py.newCode(3, var2, var1, "new", 122, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new hmac$py("hmac$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(hmac$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.HMAC$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.f$3(var2, var3);
         case 4:
            return this.update$4(var2, var3);
         case 5:
            return this.copy$5(var2, var3);
         case 6:
            return this._current$6(var2, var3);
         case 7:
            return this.digest$7(var2, var3);
         case 8:
            return this.hexdigest$8(var2, var3);
         case 9:
            return this.new$9(var2, var3);
         default:
            return null;
      }
   }
}
