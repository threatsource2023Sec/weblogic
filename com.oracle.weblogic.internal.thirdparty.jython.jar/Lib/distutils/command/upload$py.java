package distutils.command;

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
@Filename("distutils/command/upload.py")
public class upload$py extends PyFunctionTable implements PyRunnable {
   static upload$py self;
   static final PyCode f$0;
   static final PyCode upload$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode upload_file$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.upload\n\nImplements the Distutils 'upload' subcommand (upload package to PyPI)."));
      var1.setline(3);
      PyString.fromInterned("distutils.command.upload\n\nImplements the Distutils 'upload' subcommand (upload package to PyPI).");
      var1.setline(4);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("platform", var1, -1);
      var1.setlocal("platform", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"urlopen", "Request", "HTTPError"};
      PyObject[] var6 = imp.importFrom("urllib2", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("urlopen", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("Request", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("HTTPError", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"standard_b64encode"};
      var6 = imp.importFrom("base64", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("standard_b64encode", var4);
      var4 = null;
      var1.setline(9);
      var3 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOneAs("cStringIO", var1, -1);
      var1.setlocal("StringIO", var3);
      var3 = null;
      var1.setline(11);
      var5 = new String[]{"md5"};
      var6 = imp.importFrom("hashlib", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("md5", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"DistutilsError", "DistutilsOptionError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsError", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(14);
      var5 = new String[]{"PyPIRCCommand"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("PyPIRCCommand", var4);
      var4 = null;
      var1.setline(15);
      var5 = new String[]{"spawn"};
      var6 = imp.importFrom("distutils.spawn", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("spawn", var4);
      var4 = null;
      var1.setline(16);
      var5 = new String[]{"log"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(18);
      var6 = new PyObject[]{var1.getname("PyPIRCCommand")};
      var4 = Py.makeClass("upload", var6, upload$1);
      var1.setlocal("upload", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject upload$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(20);
      PyString var3 = PyString.fromInterned("upload binary package to PyPI");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(22);
      PyObject var4 = var1.getname("PyPIRCCommand").__getattr__("user_options")._add(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("sign"), PyString.fromInterned("s"), PyString.fromInterned("sign files to upload using gpg")}), new PyTuple(new PyObject[]{PyString.fromInterned("identity="), PyString.fromInterned("i"), PyString.fromInterned("GPG identity used to sign files")})}));
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(28);
      var4 = var1.getname("PyPIRCCommand").__getattr__("boolean_options")._add(new PyList(new PyObject[]{PyString.fromInterned("sign")}));
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(30);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(38);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(56);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(62);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, upload_file$5, (PyObject)null);
      var1.setlocal("upload_file", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      var1.getglobal("PyPIRCCommand").__getattr__("initialize_options").__call__(var2, var1.getlocal(0));
      var1.setline(32);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"username", var3);
      var3 = null;
      var1.setline(33);
      var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"password", var3);
      var3 = null;
      var1.setline(34);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"show_response", var4);
      var3 = null;
      var1.setline(35);
      PyObject var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("sign", var5);
      var3 = null;
      var1.setline(36);
      var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("identity", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      var1.getglobal("PyPIRCCommand").__getattr__("finalize_options").__call__(var2, var1.getlocal(0));
      var1.setline(40);
      PyObject var10000 = var1.getlocal(0).__getattr__("identity");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("sign").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(41);
         throw Py.makeException(var1.getglobal("DistutilsOptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Must use --sign for --identity to have meaning")));
      } else {
         var1.setline(44);
         PyObject var3 = var1.getlocal(0).__getattr__("_read_pypirc").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(45);
         var3 = var1.getlocal(1);
         var10000 = var3._ne(new PyDictionary(Py.EmptyObjects));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(46);
            var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("username"));
            var1.getlocal(0).__setattr__("username", var3);
            var3 = null;
            var1.setline(47);
            var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("password"));
            var1.getlocal(0).__setattr__("password", var3);
            var3 = null;
            var1.setline(48);
            var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("repository"));
            var1.getlocal(0).__setattr__("repository", var3);
            var3 = null;
            var1.setline(49);
            var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("realm"));
            var1.getlocal(0).__setattr__("realm", var3);
            var3 = null;
         }

         var1.setline(53);
         var10000 = var1.getlocal(0).__getattr__("password").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("password");
         }

         if (var10000.__nonzero__()) {
            var1.setline(54);
            var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("password");
            var1.getlocal(0).__setattr__("password", var3);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      if (var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__not__().__nonzero__()) {
         var1.setline(58);
         throw Py.makeException(var1.getglobal("DistutilsOptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No dist file created in earlier command")));
      } else {
         var1.setline(59);
         PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__iter__();

         while(true) {
            var1.setline(59);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 3);
            PyObject var6 = var5[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(60);
            var1.getlocal(0).__getattr__("upload_file").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         }
      }
   }

   public PyObject upload_file$5(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(0).__getattr__("repository"));
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(66);
      PyObject var10000 = var1.getlocal(7);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(8);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(9);
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(67);
         throw Py.makeException(var1.getglobal("AssertionError").__call__(var2, PyString.fromInterned("Incompatible url %s")._mod(var1.getlocal(0).__getattr__("repository"))));
      } else {
         var1.setline(69);
         var3 = var1.getlocal(4);
         var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("http"), PyString.fromInterned("https")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(70);
            throw Py.makeException(var1.getglobal("AssertionError").__call__(var2, PyString.fromInterned("unsupported schema ")._add(var1.getlocal(4))));
         } else {
            var1.setline(73);
            String[] var11;
            PyObject[] var13;
            if (var1.getlocal(0).__getattr__("sign").__nonzero__()) {
               var1.setline(74);
               PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("gpg"), PyString.fromInterned("--detach-sign"), PyString.fromInterned("-a"), var1.getlocal(3)});
               var1.setlocal(10, var10);
               var3 = null;
               var1.setline(75);
               if (var1.getlocal(0).__getattr__("identity").__nonzero__()) {
                  var1.setline(76);
                  var10 = new PyList(new PyObject[]{PyString.fromInterned("--local-user"), var1.getlocal(0).__getattr__("identity")});
                  var1.getlocal(10).__setslice__(Py.newInteger(2), Py.newInteger(2), (PyObject)null, var10);
                  var3 = null;
               }

               var1.setline(77);
               var10000 = var1.getglobal("spawn");
               var13 = new PyObject[]{var1.getlocal(10), var1.getlocal(0).__getattr__("dry_run")};
               var11 = new String[]{"dry_run"};
               var10000.__call__(var2, var13, var11);
               var3 = null;
            }

            var1.setline(82);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("rb"));
            var1.setlocal(11, var3);
            var3 = null;
            var3 = null;

            PyObject var12;
            try {
               var1.setline(84);
               var12 = var1.getlocal(11).__getattr__("read").__call__(var2);
               var1.setlocal(12, var12);
               var4 = null;
            } catch (Throwable var8) {
               Py.addTraceback(var8, var1);
               var1.setline(86);
               var1.getlocal(11).__getattr__("close").__call__(var2);
               throw (Throwable)var8;
            }

            var1.setline(86);
            var1.getlocal(11).__getattr__("close").__call__(var2);
            var1.setline(87);
            var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("metadata");
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(88);
            PyDictionary var14 = new PyDictionary(new PyObject[]{PyString.fromInterned(":action"), PyString.fromInterned("file_upload"), PyString.fromInterned("protcol_version"), PyString.fromInterned("1"), PyString.fromInterned("name"), var1.getlocal(13).__getattr__("get_name").__call__(var2), PyString.fromInterned("version"), var1.getlocal(13).__getattr__("get_version").__call__(var2), PyString.fromInterned("content"), new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)), var1.getlocal(12)}), PyString.fromInterned("filetype"), var1.getlocal(1), PyString.fromInterned("pyversion"), var1.getlocal(2), PyString.fromInterned("md5_digest"), var1.getglobal("md5").__call__(var2, var1.getlocal(12)).__getattr__("hexdigest").__call__(var2), PyString.fromInterned("metadata_version"), PyString.fromInterned("1.0"), PyString.fromInterned("summary"), var1.getlocal(13).__getattr__("get_description").__call__(var2), PyString.fromInterned("home_page"), var1.getlocal(13).__getattr__("get_url").__call__(var2), PyString.fromInterned("author"), var1.getlocal(13).__getattr__("get_contact").__call__(var2), PyString.fromInterned("author_email"), var1.getlocal(13).__getattr__("get_contact_email").__call__(var2), PyString.fromInterned("license"), var1.getlocal(13).__getattr__("get_licence").__call__(var2), PyString.fromInterned("description"), var1.getlocal(13).__getattr__("get_long_description").__call__(var2), PyString.fromInterned("keywords"), var1.getlocal(13).__getattr__("get_keywords").__call__(var2), PyString.fromInterned("platform"), var1.getlocal(13).__getattr__("get_platforms").__call__(var2), PyString.fromInterned("classifiers"), var1.getlocal(13).__getattr__("get_classifiers").__call__(var2), PyString.fromInterned("download_url"), var1.getlocal(13).__getattr__("get_download_url").__call__(var2), PyString.fromInterned("provides"), var1.getlocal(13).__getattr__("get_provides").__call__(var2), PyString.fromInterned("requires"), var1.getlocal(13).__getattr__("get_requires").__call__(var2), PyString.fromInterned("obsoletes"), var1.getlocal(13).__getattr__("get_obsoletes").__call__(var2)});
            var1.setlocal(14, var14);
            var3 = null;
            var1.setline(120);
            PyString var16 = PyString.fromInterned("");
            var1.setlocal(15, var16);
            var3 = null;
            var1.setline(121);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(PyString.fromInterned("bdist_rpm"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(122);
               var3 = var1.getglobal("platform").__getattr__("dist").__call__(var2);
               var4 = Py.unpackSequence(var3, 3);
               var5 = var4[0];
               var1.setlocal(16, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(17, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(18, var5);
               var5 = null;
               var3 = null;
               var1.setline(123);
               if (var1.getlocal(16).__nonzero__()) {
                  var1.setline(124);
                  var3 = PyString.fromInterned("built for %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(16), var1.getlocal(17)}));
                  var1.setlocal(15, var3);
                  var3 = null;
               }
            } else {
               var1.setline(125);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(PyString.fromInterned("bdist_dumb"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(126);
                  PyString var21 = PyString.fromInterned("built for %s");
                  PyObject var10001 = var1.getglobal("platform").__getattr__("platform");
                  var13 = new PyObject[]{Py.newInteger(1)};
                  var11 = new String[]{"terse"};
                  var10001 = var10001.__call__(var2, var13, var11);
                  var3 = null;
                  var3 = var21._mod(var10001);
                  var1.setlocal(15, var3);
                  var3 = null;
               }
            }

            var1.setline(127);
            var3 = var1.getlocal(15);
            var1.getlocal(14).__setitem__((PyObject)PyString.fromInterned("comment"), var3);
            var3 = null;
            var1.setline(129);
            if (var1.getlocal(0).__getattr__("sign").__nonzero__()) {
               var1.setline(130);
               PyTuple var19 = new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3))._add(PyString.fromInterned(".asc")), var1.getglobal("open").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned(".asc"))).__getattr__("read").__call__(var2)});
               var1.getlocal(14).__setitem__((PyObject)PyString.fromInterned("gpg_signature"), var19);
               var3 = null;
            }

            var1.setline(134);
            var3 = PyString.fromInterned("Basic ")._add(var1.getglobal("standard_b64encode").__call__(var2, var1.getlocal(0).__getattr__("username")._add(PyString.fromInterned(":"))._add(var1.getlocal(0).__getattr__("password"))));
            var1.setlocal(19, var3);
            var3 = null;
            var1.setline(138);
            var16 = PyString.fromInterned("--------------GHSKFJDLGDS7543FJKLFHRE75642756743254");
            var1.setlocal(20, var16);
            var3 = null;
            var1.setline(139);
            var3 = PyString.fromInterned("\r\n--")._add(var1.getlocal(20));
            var1.setlocal(21, var3);
            var3 = null;
            var1.setline(140);
            var3 = var1.getlocal(21)._add(PyString.fromInterned("--\r\n"));
            var1.setlocal(22, var3);
            var3 = null;
            var1.setline(141);
            var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
            var1.setlocal(23, var3);
            var3 = null;
            var1.setline(142);
            var3 = var1.getlocal(14).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(142);
               var12 = var3.__iternext__();
               if (var12 == null) {
                  var1.setline(160);
                  var1.getlocal(23).__getattr__("write").__call__(var2, var1.getlocal(22));
                  var1.setline(161);
                  var3 = var1.getlocal(23).__getattr__("getvalue").__call__(var2);
                  var1.setlocal(23, var3);
                  var3 = null;
                  var1.setline(163);
                  var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("Submitting %s to %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("repository")})), var1.getglobal("log").__getattr__("INFO"));
                  var1.setline(166);
                  var14 = new PyDictionary(new PyObject[]{PyString.fromInterned("Content-type"), PyString.fromInterned("multipart/form-data; boundary=%s")._mod(var1.getlocal(20)), PyString.fromInterned("Content-length"), var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(23))), PyString.fromInterned("Authorization"), var1.getlocal(19)});
                  var1.setlocal(27, var14);
                  var3 = null;
                  var1.setline(171);
                  var10000 = var1.getglobal("Request");
                  var13 = new PyObject[]{var1.getlocal(0).__getattr__("repository"), var1.getlocal(23), var1.getlocal(27)};
                  var11 = new String[]{"data", "headers"};
                  var10000 = var10000.__call__(var2, var13, var11);
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(28, var3);
                  var3 = null;

                  try {
                     var1.setline(175);
                     var3 = var1.getglobal("urlopen").__call__(var2, var1.getlocal(28));
                     var1.setlocal(29, var3);
                     var3 = null;
                     var1.setline(176);
                     var3 = var1.getlocal(29).__getattr__("getcode").__call__(var2);
                     var1.setlocal(30, var3);
                     var3 = null;
                     var1.setline(177);
                     var3 = var1.getlocal(29).__getattr__("msg");
                     var1.setlocal(31, var3);
                     var3 = null;
                     var1.setline(178);
                     if (var1.getlocal(0).__getattr__("show_response").__nonzero__()) {
                        var1.setline(179);
                        var3 = PyString.fromInterned("\n").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("-")._mul(Py.newInteger(75)), var1.getlocal(29).__getattr__("read").__call__(var2), PyString.fromInterned("-")._mul(Py.newInteger(75))})));
                        var1.setlocal(32, var3);
                        var3 = null;
                        var1.setline(180);
                        var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getlocal(32), var1.getglobal("log").__getattr__("INFO"));
                     }
                  } catch (Throwable var9) {
                     PyException var20 = Py.setException(var9, var1);
                     if (var20.match(var1.getglobal("socket").__getattr__("error"))) {
                        var12 = var20.value;
                        var1.setlocal(33, var12);
                        var4 = null;
                        var1.setline(182);
                        var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(33)), var1.getglobal("log").__getattr__("ERROR"));
                        var1.setline(183);
                        throw Py.makeException();
                     }

                     if (!var20.match(var1.getglobal("HTTPError"))) {
                        throw var20;
                     }

                     var12 = var20.value;
                     var1.setlocal(33, var12);
                     var4 = null;
                     var1.setline(185);
                     var12 = var1.getlocal(33).__getattr__("code");
                     var1.setlocal(30, var12);
                     var4 = null;
                     var1.setline(186);
                     var12 = var1.getlocal(33).__getattr__("msg");
                     var1.setlocal(31, var12);
                     var4 = null;
                  }

                  var1.setline(188);
                  var3 = var1.getlocal(30);
                  var10000 = var3._eq(Py.newInteger(200));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(189);
                     var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("Server response (%s): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(30), var1.getlocal(31)})), var1.getglobal("log").__getattr__("INFO"));
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(192);
                  var3 = PyString.fromInterned("Upload failed (%s): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(30), var1.getlocal(31)}));
                  var1.setlocal(32, var3);
                  var3 = null;
                  var1.setline(193);
                  var1.getlocal(0).__getattr__("announce").__call__(var2, var1.getlocal(32), var1.getglobal("log").__getattr__("ERROR"));
                  var1.setline(194);
                  throw Py.makeException(var1.getglobal("DistutilsError").__call__(var2, var1.getlocal(32)));
               }

               PyObject[] var17 = Py.unpackSequence(var12, 2);
               PyObject var6 = var17[0];
               var1.setlocal(24, var6);
               var6 = null;
               var6 = var17[1];
               var1.setlocal(25, var6);
               var6 = null;
               var1.setline(144);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(25), var1.getglobal("list")).__not__().__nonzero__()) {
                  var1.setline(145);
                  PyList var18 = new PyList(new PyObject[]{var1.getlocal(25)});
                  var1.setlocal(25, var18);
                  var5 = null;
               }

               var1.setline(146);
               var5 = var1.getlocal(25).__iter__();

               while(true) {
                  var1.setline(146);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(25, var6);
                  var1.setline(147);
                  PyObject var7;
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(25), var1.getglobal("tuple")).__nonzero__()) {
                     var1.setline(148);
                     var7 = PyString.fromInterned(";filename=\"%s\"")._mod(var1.getlocal(25).__getitem__(Py.newInteger(0)));
                     var1.setlocal(26, var7);
                     var7 = null;
                     var1.setline(149);
                     var7 = var1.getlocal(25).__getitem__(Py.newInteger(1));
                     var1.setlocal(25, var7);
                     var7 = null;
                  } else {
                     var1.setline(151);
                     PyString var15 = PyString.fromInterned("");
                     var1.setlocal(26, var15);
                     var7 = null;
                  }

                  var1.setline(153);
                  var1.getlocal(23).__getattr__("write").__call__(var2, var1.getlocal(21));
                  var1.setline(154);
                  var1.getlocal(23).__getattr__("write").__call__(var2, PyString.fromInterned("\r\nContent-Disposition: form-data; name=\"%s\"")._mod(var1.getlocal(24)));
                  var1.setline(155);
                  var1.getlocal(23).__getattr__("write").__call__(var2, var1.getlocal(26));
                  var1.setline(156);
                  var1.getlocal(23).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n\r\n"));
                  var1.setline(157);
                  var1.getlocal(23).__getattr__("write").__call__(var2, var1.getlocal(25));
                  var1.setline(158);
                  var10000 = var1.getlocal(25);
                  if (var10000.__nonzero__()) {
                     var7 = var1.getlocal(25).__getitem__(Py.newInteger(-1));
                     var10000 = var7._eq(PyString.fromInterned("\r"));
                     var7 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(159);
                     var1.getlocal(23).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
                  }
               }
            }
         }
      }
   }

   public upload$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      upload$1 = Py.newCode(0, var2, var1, "upload", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "config"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 38, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "pyversion", "filename"};
      run$4 = Py.newCode(1, var2, var1, "run", 56, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "pyversion", "filename", "schema", "netloc", "url", "params", "query", "fragments", "gpg_args", "f", "content", "meta", "data", "comment", "dist", "version", "id", "auth", "boundary", "sep_boundary", "end_boundary", "body", "key", "value", "fn", "headers", "request", "result", "status", "reason", "msg", "e"};
      upload_file$5 = Py.newCode(4, var2, var1, "upload_file", 62, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new upload$py("distutils/command/upload$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(upload$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.upload$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this.upload_file$5(var2, var3);
         default:
            return null;
      }
   }
}
