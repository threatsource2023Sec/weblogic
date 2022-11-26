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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("hashlib.py")
public class hashlib$py extends PyFunctionTable implements PyRunnable {
   static hashlib$py self;
   static final PyCode f$0;
   static final PyCode __get_builtin_constructor$1;
   static final PyCode __get_openssl_constructor$2;
   static final PyCode __py_new$3;
   static final PyCode __hash_new$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyString var3 = PyString.fromInterned("hashlib module - A common interface to many hash functions.\n\nnew(name, string='') - returns a new hash object implementing the\n                       given hash function; initializing the hash\n                       using the given string data.\n\nNamed constructor functions are also available, these are much faster\nthan using new():\n\nmd5(), sha1(), sha224(), sha256(), sha384(), and sha512()\n\nMore algorithms may be available on your platform but the above are\nguaranteed to exist.\n\nNOTE: If you want the adler32 or crc32 hash functions they are available in\nthe zlib module.\n\nChoose your hash function wisely.  Some have known collision weaknesses.\nsha384 and sha512 will be slow on 32 bit platforms.\n\nHash objects have these methods:\n - update(arg): Update the hash object with the string arg. Repeated calls\n                are equivalent to a single call with the concatenation of all\n                the arguments.\n - digest():    Return the digest of the strings passed to the update() method\n                so far. This may contain non-ASCII characters, including\n                NUL bytes.\n - hexdigest(): Like digest() except the digest is returned as a string of\n                double length, containing only hexadecimal digits.\n - copy():      Return a copy (clone) of the hash object. This can be used to\n                efficiently compute the digests of strings that share a common\n                initial substring.\n\nFor example, to obtain the digest of the string 'Nobody inspects the\nspammish repetition':\n\n    >>> import hashlib\n    >>> m = hashlib.md5()\n    >>> m.update(\"Nobody inspects\")\n    >>> m.update(\" the spammish repetition\")\n    >>> m.digest()\n    '\\xbbd\\x9c\\x83\\xdd\\x1e\\xa5\\xc9\\xd9\\xde\\xc9\\xa1\\x8d\\xf0\\xff\\xe9'\n\nMore condensed:\n\n    >>> hashlib.sha224(\"Nobody inspects the spammish repetition\").hexdigest()\n    'a4337bc45a8fc544c03f52dc550cd6e1e87021bc896588bd79e901e2'\n\n");
      var1.setlocal("__doc__", var3);
      var3 = null;
      var1.setline(59);
      PyTuple var9 = new PyTuple(new PyObject[]{PyString.fromInterned("md5"), PyString.fromInterned("sha1"), PyString.fromInterned("sha224"), PyString.fromInterned("sha256"), PyString.fromInterned("sha384"), PyString.fromInterned("sha512")});
      var1.setlocal("__always_supported", var9);
      var3 = null;
      var1.setline(61);
      PyObject var10 = var1.getname("__always_supported");
      var1.setlocal("algorithms", var10);
      var3 = null;
      var1.setline(63);
      var10 = var1.getname("__always_supported")._add(new PyTuple(new PyObject[]{PyString.fromInterned("new"), PyString.fromInterned("algorithms")}));
      var1.setlocal("__all__", var10);
      var3 = null;
      var1.setline(66);
      PyObject[] var12 = Py.EmptyObjects;
      PyFunction var13 = new PyFunction(var1.f_globals, var12, __get_builtin_constructor$1, (PyObject)null);
      var1.setlocal("__get_builtin_constructor", var13);
      var3 = null;
      var1.setline(94);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, __get_openssl_constructor$2, (PyObject)null);
      var1.setlocal("__get_openssl_constructor", var13);
      var3 = null;
      var1.setline(106);
      var12 = new PyObject[]{PyString.fromInterned("")};
      var13 = new PyFunction(var1.f_globals, var12, __py_new$3, PyString.fromInterned("new(name, string='') - Return a new hashing object using the named algorithm;\n    optionally initialized with a string.\n    "));
      var1.setlocal("__py_new", var13);
      var3 = null;
      var1.setline(113);
      var12 = new PyObject[]{PyString.fromInterned("")};
      var13 = new PyFunction(var1.f_globals, var12, __hash_new$4, PyString.fromInterned("new(name, string='') - Return a new hashing object using the named algorithm;\n    optionally initialized with a string.\n    "));
      var1.setlocal("__hash_new", var13);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(128);
         var10 = imp.importOne("_hashlib", var1, -1);
         var1.setlocal("_hashlib", var10);
         var3 = null;
         var1.setline(129);
         var10 = var1.getname("__hash_new");
         var1.setlocal("new", var10);
         var3 = null;
         var1.setline(130);
         var10 = var1.getname("__get_openssl_constructor");
         var1.setlocal("__get_hash", var10);
         var3 = null;
      } catch (Throwable var8) {
         PyException var14 = Py.setException(var8, var1);
         if (!var14.match(var1.getname("ImportError"))) {
            throw var14;
         }

         var1.setline(132);
         var4 = var1.getname("__py_new");
         var1.setlocal("new", var4);
         var4 = null;
         var1.setline(133);
         var4 = var1.getname("__get_builtin_constructor");
         var1.setlocal("__get_hash", var4);
         var4 = null;
      }

      var1.setline(135);
      var10 = var1.getname("__always_supported").__iter__();

      while(true) {
         var1.setline(135);
         var4 = var10.__iternext__();
         if (var4 == null) {
            var1.setline(145);
            var1.dellocal("__always_supported");
            var1.dellocal("__func_name");
            var1.dellocal("__get_hash");
            var1.setline(146);
            var1.dellocal("__py_new");
            var1.dellocal("__hash_new");
            var1.dellocal("__get_openssl_constructor");
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("__func_name", var4);

         PyException var5;
         try {
            var1.setline(139);
            PyObject var11 = var1.getname("__get_hash").__call__(var2, var1.getname("__func_name"));
            var1.getname("globals").__call__(var2).__setitem__(var1.getname("__func_name"), var11);
            var5 = null;
         } catch (Throwable var7) {
            var5 = Py.setException(var7, var1);
            if (!var5.match(var1.getname("ValueError"))) {
               throw var5;
            }

            var1.setline(141);
            PyObject var6 = imp.importOne("logging", var1, -1);
            var1.setlocal("logging", var6);
            var6 = null;
            var1.setline(142);
            var1.getname("logging").__getattr__("exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("code for hash %s was not found."), (PyObject)var1.getname("__func_name"));
         }
      }
   }

   public PyObject __get_builtin_constructor$1(PyFrame var1, ThreadState var2) {
      PyException var4;
      try {
         var1.setline(68);
         PyObject var3 = var1.getlocal(0);
         PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("SHA1"), PyString.fromInterned("sha1")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(69);
            var3 = imp.importOne("_sha", var1, -1);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(70);
            var3 = var1.getlocal(1).__getattr__("new");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(71);
         PyObject var6 = var1.getlocal(0);
         var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned("MD5"), PyString.fromInterned("md5")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(72);
            var6 = imp.importOne("_md5", var1, -1);
            var1.setlocal(2, var6);
            var4 = null;
            var1.setline(73);
            var3 = var1.getlocal(2).__getattr__("new");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(74);
         var6 = var1.getlocal(0);
         var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned("SHA256"), PyString.fromInterned("sha256"), PyString.fromInterned("SHA224"), PyString.fromInterned("sha224")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(75);
            var6 = imp.importOne("_sha256", var1, -1);
            var1.setlocal(3, var6);
            var4 = null;
            var1.setline(76);
            var6 = var1.getlocal(0).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var6);
            var4 = null;
            var1.setline(77);
            var6 = var1.getlocal(4);
            var10000 = var6._eq(PyString.fromInterned("256"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(78);
               var3 = var1.getlocal(3).__getattr__("sha256");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(79);
            var6 = var1.getlocal(4);
            var10000 = var6._eq(PyString.fromInterned("224"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(80);
               var3 = var1.getlocal(3).__getattr__("sha224");
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(81);
            var6 = var1.getlocal(0);
            var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned("SHA512"), PyString.fromInterned("sha512"), PyString.fromInterned("SHA384"), PyString.fromInterned("sha384")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(82);
               var6 = imp.importOne("_sha512", var1, -1);
               var1.setlocal(5, var6);
               var4 = null;
               var1.setline(83);
               var6 = var1.getlocal(0).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
               var1.setlocal(4, var6);
               var4 = null;
               var1.setline(84);
               var6 = var1.getlocal(4);
               var10000 = var6._eq(PyString.fromInterned("512"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(85);
                  var3 = var1.getlocal(5).__getattr__("sha512");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(86);
               var6 = var1.getlocal(4);
               var10000 = var6._eq(PyString.fromInterned("384"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(87);
                  var3 = var1.getlocal(5).__getattr__("sha384");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      } catch (Throwable var5) {
         var4 = Py.setException(var5, var1);
         if (!var4.match(var1.getglobal("ImportError"))) {
            throw var4;
         }

         var1.setline(89);
      }

      var1.setline(91);
      throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unsupported hash type ")._add(var1.getlocal(0))));
   }

   public PyObject __get_openssl_constructor$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(96);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getglobal("_hashlib"), PyString.fromInterned("openssl_")._add(var1.getlocal(0)));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(99);
         var1.getlocal(1).__call__(var2);
         var1.setline(101);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("ValueError")}))) {
            var1.setline(103);
            var3 = var1.getglobal("__get_builtin_constructor").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject __py_new$3(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("new(name, string='') - Return a new hashing object using the named algorithm;\n    optionally initialized with a string.\n    ");
      var1.setline(110);
      PyObject var3 = var1.getglobal("__get_builtin_constructor").__call__(var2, var1.getlocal(0)).__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash_new$4(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyString.fromInterned("new(name, string='') - Return a new hashing object using the named algorithm;\n    optionally initialized with a string.\n    ");

      PyObject var3;
      try {
         var1.setline(118);
         var3 = var1.getglobal("_hashlib").__getattr__("new").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("ValueError"))) {
            var1.setline(124);
            var3 = var1.getglobal("__get_builtin_constructor").__call__(var2, var1.getlocal(0)).__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public hashlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "_sha", "_md5", "_sha256", "bs", "_sha512"};
      __get_builtin_constructor$1 = Py.newCode(1, var2, var1, "__get_builtin_constructor", 66, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "f"};
      __get_openssl_constructor$2 = Py.newCode(1, var2, var1, "__get_openssl_constructor", 94, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "string"};
      __py_new$3 = Py.newCode(2, var2, var1, "__py_new", 106, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "string"};
      __hash_new$4 = Py.newCode(2, var2, var1, "__hash_new", 113, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new hashlib$py("hashlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(hashlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.__get_builtin_constructor$1(var2, var3);
         case 2:
            return this.__get_openssl_constructor$2(var2, var3);
         case 3:
            return this.__py_new$3(var2, var3);
         case 4:
            return this.__hash_new$4(var2, var3);
         default:
            return null;
      }
   }
}
