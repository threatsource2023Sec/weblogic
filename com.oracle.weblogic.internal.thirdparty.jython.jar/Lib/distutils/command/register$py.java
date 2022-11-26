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
@Filename("distutils/command/register.py")
public class register$py extends PyFunctionTable implements PyRunnable {
   static register$py self;
   static final PyCode f$0;
   static final PyCode register$1;
   static final PyCode f$2;
   static final PyCode initialize_options$3;
   static final PyCode finalize_options$4;
   static final PyCode run$5;
   static final PyCode check_metadata$6;
   static final PyCode _set_config$7;
   static final PyCode classifiers$8;
   static final PyCode verify_metadata$9;
   static final PyCode send_metadata$10;
   static final PyCode build_post_data$11;
   static final PyCode post_to_server$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.register\n\nImplements the Distutils 'register' command (register with the repository).\n"));
      var1.setline(4);
      PyString.fromInterned("distutils.command.register\n\nImplements the Distutils 'register' command (register with the repository).\n");
      var1.setline(8);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(10);
      PyObject var5 = imp.importOne("urllib2", var1, -1);
      var1.setlocal("urllib2", var5);
      var3 = null;
      var1.setline(11);
      var5 = imp.importOne("getpass", var1, -1);
      var1.setlocal("getpass", var5);
      var3 = null;
      var1.setline(12);
      var5 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var5);
      var3 = null;
      var1.setline(13);
      String[] var6 = new String[]{"warn"};
      PyObject[] var7 = imp.importFrom("warnings", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("warn", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"PyPIRCCommand"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("PyPIRCCommand", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(18);
      var7 = new PyObject[]{var1.getname("PyPIRCCommand")};
      var4 = Py.makeClass("register", var7, register$1);
      var1.setlocal("register", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(20);
      PyString var3 = PyString.fromInterned("register the distribution with the Python package index");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(21);
      PyObject var4 = var1.getname("PyPIRCCommand").__getattr__("user_options")._add(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("list-classifiers"), var1.getname("None"), PyString.fromInterned("list the valid Trove classifiers")}), new PyTuple(new PyObject[]{PyString.fromInterned("strict"), var1.getname("None"), PyString.fromInterned("Will stop the registering if the meta-data are not fully compliant")})}));
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(27);
      var4 = var1.getname("PyPIRCCommand").__getattr__("boolean_options")._add(new PyList(new PyObject[]{PyString.fromInterned("verify"), PyString.fromInterned("list-classifiers"), PyString.fromInterned("strict")}));
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(30);
      PyObject[] var10002 = new PyObject[1];
      PyObject[] var10007 = new PyObject[]{PyString.fromInterned("check"), null};
      var1.setline(30);
      PyObject[] var5 = Py.EmptyObjects;
      var10007[1] = new PyFunction(var1.f_globals, var5, f$2);
      var10002[0] = new PyTuple(var10007);
      PyList var6 = new PyList(var10002);
      var1.setlocal("sub_commands", var6);
      var3 = null;
      var1.setline(32);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, initialize_options$3, (PyObject)null);
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(37);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, finalize_options$4, (PyObject)null);
      var1.setlocal("finalize_options", var7);
      var3 = null;
      var1.setline(44);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, run$5, (PyObject)null);
      var1.setlocal("run", var7);
      var3 = null;
      var1.setline(59);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, check_metadata$6, PyString.fromInterned("Deprecated API."));
      var1.setlocal("check_metadata", var7);
      var3 = null;
      var1.setline(69);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, _set_config$7, PyString.fromInterned(" Reads the configuration file and set attributes.\n        "));
      var1.setlocal("_set_config", var7);
      var3 = null;
      var1.setline(86);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, classifiers$8, PyString.fromInterned(" Fetch the list of classifiers from the server.\n        "));
      var1.setlocal("classifiers", var7);
      var3 = null;
      var1.setline(92);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, verify_metadata$9, PyString.fromInterned(" Send the metadata to the package index server to be checked.\n        "));
      var1.setlocal("verify_metadata", var7);
      var3 = null;
      var1.setline(100);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, send_metadata$10, PyString.fromInterned(" Send the metadata to the package index server.\n\n            Well, do the following:\n            1. figure who the user is, and then\n            2. send the data as a Basic auth'ed POST.\n\n            First we try to read the username/password from $HOME/.pypirc,\n            which is a ConfigParser-formatted file with a section\n            [distutils] containing username and password entries (both\n            in clear text). Eg:\n\n                [distutils]\n                index-servers =\n                    pypi\n\n                [pypi]\n                username: fred\n                password: sekrit\n\n            Otherwise, to figure who the user is, we offer the user three\n            choices:\n\n             1. use existing login,\n             2. register as a new user, or\n             3. set the password to a random string and email the user.\n\n        "));
      var1.setlocal("send_metadata", var7);
      var3 = null;
      var1.setline(223);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, build_post_data$11, (PyObject)null);
      var1.setlocal("build_post_data", var7);
      var3 = null;
      var1.setline(251);
      var5 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var5, post_to_server$12, PyString.fromInterned(" Post a query to the server, and return a string response.\n        "));
      var1.setlocal("post_to_server", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject initialize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      var1.getglobal("PyPIRCCommand").__getattr__("initialize_options").__call__(var2, var1.getlocal(0));
      var1.setline(34);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"list_classifiers", var3);
      var3 = null;
      var1.setline(35);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"strict", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$4(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.getglobal("PyPIRCCommand").__getattr__("finalize_options").__call__(var2, var1.getlocal(0));
      var1.setline(40);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("strict"), new PyTuple(new PyObject[]{PyString.fromInterned("register"), var1.getlocal(0).__getattr__("strict")}), PyString.fromInterned("restructuredtext"), new PyTuple(new PyObject[]{PyString.fromInterned("register"), Py.newInteger(1)})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(42);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("distribution").__getattr__("command_options").__setitem__((PyObject)PyString.fromInterned("check"), var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$5(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      var1.getlocal(0).__getattr__("finalize_options").__call__(var2);
      var1.setline(46);
      var1.getlocal(0).__getattr__("_set_config").__call__(var2);
      var1.setline(49);
      PyObject var3 = var1.getlocal(0).__getattr__("get_sub_commands").__call__(var2).__iter__();

      while(true) {
         var1.setline(49);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(52);
            if (var1.getlocal(0).__getattr__("dry_run").__nonzero__()) {
               var1.setline(53);
               var1.getlocal(0).__getattr__("verify_metadata").__call__(var2);
            } else {
               var1.setline(54);
               if (var1.getlocal(0).__getattr__("list_classifiers").__nonzero__()) {
                  var1.setline(55);
                  var1.getlocal(0).__getattr__("classifiers").__call__(var2);
               } else {
                  var1.setline(57);
                  var1.getlocal(0).__getattr__("send_metadata").__call__(var2);
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(50);
         var1.getlocal(0).__getattr__("run_command").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject check_metadata$6(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Deprecated API.");
      var1.setline(61);
      var1.getglobal("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distutils.command.register.check_metadata is deprecated,               use the check command instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"));
      var1.setline(63);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_command_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("check"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(64);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(65);
      var3 = var1.getlocal(0).__getattr__("strict");
      var1.getlocal(1).__setattr__("strict", var3);
      var3 = null;
      var1.setline(66);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"restructuredtext", var4);
      var3 = null;
      var1.setline(67);
      var1.getlocal(1).__getattr__("run").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_config$7(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyString.fromInterned(" Reads the configuration file and set attributes.\n        ");
      var1.setline(72);
      PyObject var3 = var1.getlocal(0).__getattr__("_read_pypirc").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(new PyDictionary(Py.EmptyObjects));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(74);
         var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("username"));
         var1.getlocal(0).__setattr__("username", var3);
         var3 = null;
         var1.setline(75);
         var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("password"));
         var1.getlocal(0).__setattr__("password", var3);
         var3 = null;
         var1.setline(76);
         var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("repository"));
         var1.getlocal(0).__setattr__("repository", var3);
         var3 = null;
         var1.setline(77);
         var3 = var1.getlocal(1).__getitem__(PyString.fromInterned("realm"));
         var1.getlocal(0).__setattr__("realm", var3);
         var3 = null;
         var1.setline(78);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("has_config", var3);
         var3 = null;
      } else {
         var1.setline(80);
         var3 = var1.getlocal(0).__getattr__("repository");
         var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("pypi"), var1.getlocal(0).__getattr__("DEFAULT_REPOSITORY")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(81);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s not found in .pypirc")._mod(var1.getlocal(0).__getattr__("repository"))));
         }

         var1.setline(82);
         var3 = var1.getlocal(0).__getattr__("repository");
         var10000 = var3._eq(PyString.fromInterned("pypi"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(83);
            var3 = var1.getlocal(0).__getattr__("DEFAULT_REPOSITORY");
            var1.getlocal(0).__setattr__("repository", var3);
            var3 = null;
         }

         var1.setline(84);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("has_config", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject classifiers$8(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyString.fromInterned(" Fetch the list of classifiers from the server.\n        ");
      var1.setline(89);
      PyObject var3 = var1.getglobal("urllib2").__getattr__("urlopen").__call__(var2, var1.getlocal(0).__getattr__("repository")._add(PyString.fromInterned("?:action=list_classifiers")));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(90);
      var1.getglobal("log").__getattr__("info").__call__(var2, var1.getlocal(1).__getattr__("read").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject verify_metadata$9(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned(" Send the metadata to the package index server to be checked.\n        ");
      var1.setline(96);
      PyObject var3 = var1.getlocal(0).__getattr__("post_to_server").__call__(var2, var1.getlocal(0).__getattr__("build_post_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("verify")));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(97);
      var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("Server response (%s): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_metadata$10(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyString.fromInterned(" Send the metadata to the package index server.\n\n            Well, do the following:\n            1. figure who the user is, and then\n            2. send the data as a Basic auth'ed POST.\n\n            First we try to read the username/password from $HOME/.pypirc,\n            which is a ConfigParser-formatted file with a section\n            [distutils] containing username and password entries (both\n            in clear text). Eg:\n\n                [distutils]\n                index-servers =\n                    pypi\n\n                [pypi]\n                username: fred\n                password: sekrit\n\n            Otherwise, to figure who the user is, we offer the user three\n            choices:\n\n             1. use existing login,\n             2. register as a new user, or\n             3. set the password to a random string and email the user.\n\n        ");
      var1.setline(130);
      PyString var3;
      PyObject var6;
      if (var1.getlocal(0).__getattr__("has_config").__nonzero__()) {
         var1.setline(131);
         var3 = PyString.fromInterned("1");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(132);
         var6 = var1.getlocal(0).__getattr__("username");
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(133);
         var6 = var1.getlocal(0).__getattr__("password");
         var1.setlocal(3, var6);
         var3 = null;
      } else {
         var1.setline(135);
         var3 = PyString.fromInterned("x");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(136);
         var3 = PyString.fromInterned("");
         var1.setlocal(2, var3);
         var1.setlocal(3, var3);
      }

      var1.setline(139);
      var6 = PyString.fromInterned("1 2 3 4").__getattr__("split").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;

      while(true) {
         var1.setline(140);
         var6 = var1.getlocal(1);
         PyObject var10000 = var6._notin(var1.getlocal(4));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(155);
            var6 = var1.getlocal(1);
            var10000 = var6._eq(PyString.fromInterned("1"));
            var3 = null;
            PyObject[] var4;
            PyObject var5;
            if (!var10000.__nonzero__()) {
               var1.setline(191);
               var6 = var1.getlocal(1);
               var10000 = var6._eq(PyString.fromInterned("2"));
               var3 = null;
               PyDictionary var7;
               if (var10000.__nonzero__()) {
                  var1.setline(192);
                  var7 = new PyDictionary(new PyObject[]{PyString.fromInterned(":action"), PyString.fromInterned("user")});
                  var1.setlocal(9, var7);
                  var3 = null;
                  var1.setline(193);
                  var3 = PyString.fromInterned("");
                  var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("name"), var3);
                  var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("password"), var3);
                  var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("email"), var3);
                  var1.setline(194);
                  var6 = var1.getglobal("None");
                  var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("confirm"), var6);
                  var3 = null;

                  label77:
                  while(true) {
                     var1.setline(195);
                     if (!var1.getlocal(9).__getitem__(PyString.fromInterned("name")).__not__().__nonzero__()) {
                        label92:
                        while(true) {
                           var1.setline(197);
                           var6 = var1.getlocal(9).__getitem__(PyString.fromInterned("password"));
                           var10000 = var6._ne(var1.getlocal(9).__getitem__(PyString.fromInterned("confirm")));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              while(true) {
                                 var1.setline(206);
                                 if (!var1.getlocal(9).__getitem__(PyString.fromInterned("email")).__not__().__nonzero__()) {
                                    var1.setline(208);
                                    var6 = var1.getlocal(0).__getattr__("post_to_server").__call__(var2, var1.getlocal(9));
                                    var4 = Py.unpackSequence(var6, 2);
                                    var5 = var4[0];
                                    var1.setlocal(7, var5);
                                    var5 = null;
                                    var5 = var4[1];
                                    var1.setlocal(8, var5);
                                    var5 = null;
                                    var3 = null;
                                    var1.setline(209);
                                    var6 = var1.getlocal(7);
                                    var10000 = var6._ne(Py.newInteger(200));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(210);
                                       var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("Server response (%s): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)})));
                                    } else {
                                       var1.setline(212);
                                       var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("You will receive an email shortly."));
                                       var1.setline(213);
                                       var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Follow the instructions in it to complete registration."));
                                    }
                                    break label77;
                                 }

                                 var1.setline(207);
                                 var6 = var1.getglobal("raw_input").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   EMail: "));
                                 var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("email"), var6);
                                 var3 = null;
                              }
                           }

                           while(true) {
                              var1.setline(198);
                              if (!var1.getlocal(9).__getitem__(PyString.fromInterned("password")).__not__().__nonzero__()) {
                                 while(true) {
                                    var1.setline(200);
                                    if (!var1.getlocal(9).__getitem__(PyString.fromInterned("confirm")).__not__().__nonzero__()) {
                                       var1.setline(202);
                                       var6 = var1.getlocal(9).__getitem__(PyString.fromInterned("password"));
                                       var10000 = var6._ne(var1.getlocal(9).__getitem__(PyString.fromInterned("confirm")));
                                       var3 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(203);
                                          var3 = PyString.fromInterned("");
                                          var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("password"), var3);
                                          var3 = null;
                                          var1.setline(204);
                                          var6 = var1.getglobal("None");
                                          var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("confirm"), var6);
                                          var3 = null;
                                          var1.setline(205);
                                          Py.println(PyString.fromInterned("Password and confirm don't match!"));
                                       }
                                       continue label92;
                                    }

                                    var1.setline(201);
                                    var6 = var1.getglobal("getpass").__getattr__("getpass").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" Confirm: "));
                                    var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("confirm"), var6);
                                    var3 = null;
                                 }
                              }

                              var1.setline(199);
                              var6 = var1.getglobal("getpass").__getattr__("getpass").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Password: "));
                              var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("password"), var6);
                              var3 = null;
                           }
                        }
                     }

                     var1.setline(196);
                     var6 = var1.getglobal("raw_input").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Username: "));
                     var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("name"), var6);
                     var3 = null;
                  }
               } else {
                  var1.setline(215);
                  var6 = var1.getlocal(1);
                  var10000 = var6._eq(PyString.fromInterned("3"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(216);
                     var7 = new PyDictionary(new PyObject[]{PyString.fromInterned(":action"), PyString.fromInterned("password_reset")});
                     var1.setlocal(9, var7);
                     var3 = null;
                     var1.setline(217);
                     var3 = PyString.fromInterned("");
                     var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("email"), var3);
                     var3 = null;

                     while(true) {
                        var1.setline(218);
                        if (!var1.getlocal(9).__getitem__(PyString.fromInterned("email")).__not__().__nonzero__()) {
                           var1.setline(220);
                           var6 = var1.getlocal(0).__getattr__("post_to_server").__call__(var2, var1.getlocal(9));
                           var4 = Py.unpackSequence(var6, 2);
                           var5 = var4[0];
                           var1.setlocal(7, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(8, var5);
                           var5 = null;
                           var3 = null;
                           var1.setline(221);
                           var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("Server response (%s): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)})));
                           break;
                        }

                        var1.setline(219);
                        var6 = var1.getglobal("raw_input").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Your email address: "));
                        var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("email"), var6);
                        var3 = null;
                     }
                  }
               }
            } else {
               label118:
               while(true) {
                  var1.setline(157);
                  if (!var1.getlocal(2).__not__().__nonzero__()) {
                     while(true) {
                        var1.setline(159);
                        if (!var1.getlocal(3).__not__().__nonzero__()) {
                           var1.setline(163);
                           var6 = var1.getglobal("urllib2").__getattr__("HTTPPasswordMgr").__call__(var2);
                           var1.setlocal(5, var6);
                           var3 = null;
                           var1.setline(164);
                           var6 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(0).__getattr__("repository")).__getitem__(Py.newInteger(1));
                           var1.setlocal(6, var6);
                           var3 = null;
                           var1.setline(165);
                           var1.getlocal(5).__getattr__("add_password").__call__(var2, var1.getlocal(0).__getattr__("realm"), var1.getlocal(6), var1.getlocal(2), var1.getlocal(3));
                           var1.setline(167);
                           var6 = var1.getlocal(0).__getattr__("post_to_server").__call__(var2, var1.getlocal(0).__getattr__("build_post_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("submit")), var1.getlocal(5));
                           var4 = Py.unpackSequence(var6, 2);
                           var5 = var4[0];
                           var1.setlocal(7, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(8, var5);
                           var5 = null;
                           var3 = null;
                           var1.setline(169);
                           var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("Server response (%s): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)})), var1.getglobal("log").__getattr__("INFO"));
                           var1.setline(173);
                           var6 = var1.getlocal(7);
                           var10000 = var6._eq(Py.newInteger(200));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(174);
                              if (var1.getlocal(0).__getattr__("has_config").__nonzero__()) {
                                 var1.setline(177);
                                 var6 = var1.getlocal(3);
                                 var1.getlocal(0).__getattr__("distribution").__setattr__("password", var6);
                                 var3 = null;
                              } else {
                                 var1.setline(179);
                                 var1.getlocal(0).__getattr__("announce").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("I can store your PyPI login so future submissions will be faster."), (PyObject)var1.getglobal("log").__getattr__("INFO"));
                                 var1.setline(181);
                                 var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("(the login will be stored in %s)")._mod(var1.getlocal(0).__getattr__("_get_rc_file").__call__(var2)), var1.getglobal("log").__getattr__("INFO"));
                                 var1.setline(183);
                                 var3 = PyString.fromInterned("X");
                                 var1.setlocal(1, var3);
                                 var3 = null;

                                 while(true) {
                                    var1.setline(184);
                                    var6 = var1.getlocal(1).__getattr__("lower").__call__(var2);
                                    var10000 = var6._notin(PyString.fromInterned("yn"));
                                    var3 = null;
                                    if (!var10000.__nonzero__()) {
                                       var1.setline(188);
                                       var6 = var1.getlocal(1).__getattr__("lower").__call__(var2);
                                       var10000 = var6._eq(PyString.fromInterned("y"));
                                       var3 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(189);
                                          var1.getlocal(0).__getattr__("_store_pypirc").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                                       }
                                       break label118;
                                    }

                                    var1.setline(185);
                                    var6 = var1.getglobal("raw_input").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Save your login (y/N)?"));
                                    var1.setlocal(1, var6);
                                    var3 = null;
                                    var1.setline(186);
                                    if (var1.getlocal(1).__not__().__nonzero__()) {
                                       var1.setline(187);
                                       var3 = PyString.fromInterned("n");
                                       var1.setlocal(1, var3);
                                       var3 = null;
                                    }
                                 }
                              }
                           }
                           break label118;
                        }

                        var1.setline(160);
                        var6 = var1.getglobal("getpass").__getattr__("getpass").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Password: "));
                        var1.setlocal(3, var6);
                        var3 = null;
                     }
                  }

                  var1.setline(158);
                  var6 = var1.getglobal("raw_input").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Username: "));
                  var1.setlocal(2, var6);
                  var3 = null;
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(141);
         var1.getlocal(0).__getattr__("announce").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("We need to know who you are, so please choose either:\n 1. use your existing login,\n 2. register as a new user,\n 3. have the server generate a new password for you (and email it to you), or\n 4. quit\nYour selection [default 1]: "), (PyObject)var1.getglobal("log").__getattr__("INFO"));
         var1.setline(149);
         var6 = var1.getglobal("raw_input").__call__(var2);
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(150);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(151);
            var3 = PyString.fromInterned("1");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(152);
            var6 = var1.getlocal(1);
            var10000 = var6._notin(var1.getlocal(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(153);
               Py.println(PyString.fromInterned("Please choose one of the four options!"));
            }
         }
      }
   }

   public PyObject build_post_data$11(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("metadata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(227);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned(":action"), var1.getlocal(1), PyString.fromInterned("metadata_version"), PyString.fromInterned("1.0"), PyString.fromInterned("name"), var1.getlocal(2).__getattr__("get_name").__call__(var2), PyString.fromInterned("version"), var1.getlocal(2).__getattr__("get_version").__call__(var2), PyString.fromInterned("summary"), var1.getlocal(2).__getattr__("get_description").__call__(var2), PyString.fromInterned("home_page"), var1.getlocal(2).__getattr__("get_url").__call__(var2), PyString.fromInterned("author"), var1.getlocal(2).__getattr__("get_contact").__call__(var2), PyString.fromInterned("author_email"), var1.getlocal(2).__getattr__("get_contact_email").__call__(var2), PyString.fromInterned("license"), var1.getlocal(2).__getattr__("get_licence").__call__(var2), PyString.fromInterned("description"), var1.getlocal(2).__getattr__("get_long_description").__call__(var2), PyString.fromInterned("keywords"), var1.getlocal(2).__getattr__("get_keywords").__call__(var2), PyString.fromInterned("platform"), var1.getlocal(2).__getattr__("get_platforms").__call__(var2), PyString.fromInterned("classifiers"), var1.getlocal(2).__getattr__("get_classifiers").__call__(var2), PyString.fromInterned("download_url"), var1.getlocal(2).__getattr__("get_download_url").__call__(var2), PyString.fromInterned("provides"), var1.getlocal(2).__getattr__("get_provides").__call__(var2), PyString.fromInterned("requires"), var1.getlocal(2).__getattr__("get_requires").__call__(var2), PyString.fromInterned("obsoletes"), var1.getlocal(2).__getattr__("get_obsoletes").__call__(var2)});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(247);
      PyObject var10000 = var1.getlocal(3).__getitem__(PyString.fromInterned("provides"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(3).__getitem__(PyString.fromInterned("requires"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__getitem__(PyString.fromInterned("obsoletes"));
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(248);
         PyString var5 = PyString.fromInterned("1.1");
         var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("metadata_version"), var5);
         var3 = null;
      }

      var1.setline(249);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject post_to_server$12(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned(" Post a query to the server, and return a string response.\n        ");
      var1.setline(254);
      PyString var3 = PyString.fromInterned("name");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(255);
         var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("Registering %s to %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(PyString.fromInterned("name")), var1.getlocal(0).__getattr__("repository")})), var1.getglobal("log").__getattr__("INFO"));
      }

      var1.setline(259);
      var3 = PyString.fromInterned("--------------GHSKFJDLGDS7543FJKLFHRE75642756743254");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(260);
      PyObject var9 = PyString.fromInterned("\n--")._add(var1.getlocal(3));
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(261);
      var9 = var1.getlocal(4)._add(PyString.fromInterned("--"));
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(262);
      PyList var12 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var12);
      var3 = null;
      var1.setline(263);
      var9 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(263);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(274);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(5));
            var1.setline(275);
            var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.setline(278);
            var12 = new PyList(Py.EmptyObjects);
            var1.setlocal(9, var12);
            var3 = null;
            var1.setline(279);
            var9 = var1.getlocal(6).__iter__();

            while(true) {
               var1.setline(279);
               var4 = var9.__iternext__();
               if (var4 == null) {
                  var1.setline(285);
                  var9 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(9));
                  var1.setlocal(9, var9);
                  var3 = null;
                  var1.setline(288);
                  PyDictionary var15 = new PyDictionary(new PyObject[]{PyString.fromInterned("Content-type"), PyString.fromInterned("multipart/form-data; boundary=%s; charset=utf-8")._mod(var1.getlocal(3)), PyString.fromInterned("Content-length"), var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(9)))});
                  var1.setlocal(11, var15);
                  var3 = null;
                  var1.setline(292);
                  var9 = var1.getglobal("urllib2").__getattr__("Request").__call__(var2, var1.getlocal(0).__getattr__("repository"), var1.getlocal(9), var1.getlocal(11));
                  var1.setlocal(12, var9);
                  var3 = null;
                  var1.setline(295);
                  var10000 = var1.getglobal("urllib2").__getattr__("build_opener");
                  PyObject var10002 = var1.getglobal("urllib2").__getattr__("HTTPBasicAuthHandler");
                  PyObject[] var16 = new PyObject[]{var1.getlocal(2)};
                  String[] var10 = new String[]{"password_mgr"};
                  var10002 = var10002.__call__(var2, var16, var10);
                  var3 = null;
                  var9 = var10000.__call__(var2, var10002);
                  var1.setlocal(13, var9);
                  var3 = null;
                  var1.setline(298);
                  var3 = PyString.fromInterned("");
                  var1.setlocal(1, var3);
                  var3 = null;

                  label81: {
                     PyTuple var13;
                     try {
                        var1.setline(300);
                        var9 = var1.getlocal(13).__getattr__("open").__call__(var2, var1.getlocal(12));
                        var1.setlocal(14, var9);
                        var3 = null;
                     } catch (Throwable var8) {
                        PyException var17 = Py.setException(var8, var1);
                        if (var17.match(var1.getglobal("urllib2").__getattr__("HTTPError"))) {
                           var4 = var17.value;
                           var1.setlocal(15, var4);
                           var4 = null;
                           var1.setline(302);
                           if (var1.getlocal(0).__getattr__("show_response").__nonzero__()) {
                              var1.setline(303);
                              var4 = var1.getlocal(15).__getattr__("fp").__getattr__("read").__call__(var2);
                              var1.setlocal(1, var4);
                              var4 = null;
                           }

                           var1.setline(304);
                           var13 = new PyTuple(new PyObject[]{var1.getlocal(15).__getattr__("code"), var1.getlocal(15).__getattr__("msg")});
                           var1.setlocal(14, var13);
                           var4 = null;
                        } else {
                           if (!var17.match(var1.getglobal("urllib2").__getattr__("URLError"))) {
                              throw var17;
                           }

                           var4 = var17.value;
                           var1.setlocal(15, var4);
                           var4 = null;
                           var1.setline(306);
                           var13 = new PyTuple(new PyObject[]{Py.newInteger(500), var1.getglobal("str").__call__(var2, var1.getlocal(15))});
                           var1.setlocal(14, var13);
                           var4 = null;
                        }
                        break label81;
                     }

                     var1.setline(308);
                     if (var1.getlocal(0).__getattr__("show_response").__nonzero__()) {
                        var1.setline(309);
                        var4 = var1.getlocal(14).__getattr__("read").__call__(var2);
                        var1.setlocal(1, var4);
                        var4 = null;
                     }

                     var1.setline(310);
                     var13 = new PyTuple(new PyObject[]{Py.newInteger(200), PyString.fromInterned("OK")});
                     var1.setlocal(14, var13);
                     var4 = null;
                  }

                  var1.setline(311);
                  if (var1.getlocal(0).__getattr__("show_response").__nonzero__()) {
                     var1.setline(312);
                     var9 = PyString.fromInterned("-")._mul(Py.newInteger(75));
                     var1.setlocal(16, var9);
                     var3 = null;
                     var1.setline(313);
                     var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(16), var1.getlocal(1), var1.getlocal(16)})));
                  }

                  var1.setline(315);
                  var9 = var1.getlocal(14);
                  var1.f_lasti = -1;
                  return var9;
               }

               var1.setlocal(10, var4);
               var1.setline(280);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("unicode")).__nonzero__()) {
                  var1.setline(281);
                  var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(10).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8")));
               } else {
                  var1.setline(283);
                  var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(10));
               }
            }
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(265);
         PyObject var11 = var1.getglobal("type").__call__(var2, var1.getlocal(8));
         var10000 = var11._notin(new PyTuple(new PyObject[]{var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))), var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects)))}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(266);
            PyList var14 = new PyList(new PyObject[]{var1.getlocal(8)});
            var1.setlocal(8, var14);
            var5 = null;
         }

         var1.setline(267);
         var11 = var1.getlocal(8).__iter__();

         while(true) {
            var1.setline(267);
            var6 = var11.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(8, var6);
            var1.setline(268);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(4));
            var1.setline(269);
            var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("\nContent-Disposition: form-data; name=\"%s\"")._mod(var1.getlocal(7)));
            var1.setline(270);
            var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n\n"));
            var1.setline(271);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
            var1.setline(272);
            var10000 = var1.getlocal(8);
            if (var10000.__nonzero__()) {
               PyObject var7 = var1.getlocal(8).__getitem__(Py.newInteger(-1));
               var10000 = var7._eq(PyString.fromInterned("\r"));
               var7 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(273);
               var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            }
         }
      }
   }

   public register$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      register$1 = Py.newCode(0, var2, var1, "register", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      f$2 = Py.newCode(1, var2, var1, "<lambda>", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      initialize_options$3 = Py.newCode(1, var2, var1, "initialize_options", 32, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "check_options"};
      finalize_options$4 = Py.newCode(1, var2, var1, "finalize_options", 37, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd_name"};
      run$5 = Py.newCode(1, var2, var1, "run", 44, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "check"};
      check_metadata$6 = Py.newCode(1, var2, var1, "check_metadata", 59, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "config"};
      _set_config$7 = Py.newCode(1, var2, var1, "_set_config", 69, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "response"};
      classifiers$8 = Py.newCode(1, var2, var1, "classifiers", 86, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "result"};
      verify_metadata$9 = Py.newCode(1, var2, var1, "verify_metadata", 92, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "choice", "username", "password", "choices", "auth", "host", "code", "result", "data"};
      send_metadata$10 = Py.newCode(1, var2, var1, "send_metadata", 100, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "meta", "data"};
      build_post_data$11 = Py.newCode(2, var2, var1, "build_post_data", 223, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "auth", "boundary", "sep_boundary", "end_boundary", "chunks", "key", "value", "body", "chunk", "headers", "req", "opener", "result", "e", "dashes"};
      post_to_server$12 = Py.newCode(3, var2, var1, "post_to_server", 251, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new register$py("distutils/command/register$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(register$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.register$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.initialize_options$3(var2, var3);
         case 4:
            return this.finalize_options$4(var2, var3);
         case 5:
            return this.run$5(var2, var3);
         case 6:
            return this.check_metadata$6(var2, var3);
         case 7:
            return this._set_config$7(var2, var3);
         case 8:
            return this.classifiers$8(var2, var3);
         case 9:
            return this.verify_metadata$9(var2, var3);
         case 10:
            return this.send_metadata$10(var2, var3);
         case 11:
            return this.build_post_data$11(var2, var3);
         case 12:
            return this.post_to_server$12(var2, var3);
         default:
            return null;
      }
   }
}
