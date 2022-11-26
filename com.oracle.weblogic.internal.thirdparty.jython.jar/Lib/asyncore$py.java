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
@Filename("asyncore.py")
public class asyncore$py extends PyFunctionTable implements PyRunnable {
   static asyncore$py self;
   static final PyCode f$0;
   static final PyCode _strerror$1;
   static final PyCode ExitNow$2;
   static final PyCode read$3;
   static final PyCode write$4;
   static final PyCode _exception$5;
   static final PyCode readwrite$6;
   static final PyCode poll$7;
   static final PyCode poll2$8;
   static final PyCode loop$9;
   static final PyCode dispatcher$10;
   static final PyCode __init__$11;
   static final PyCode __repr__$12;
   static final PyCode add_channel$13;
   static final PyCode del_channel$14;
   static final PyCode create_socket$15;
   static final PyCode set_socket$16;
   static final PyCode set_reuse_addr$17;
   static final PyCode readable$18;
   static final PyCode writable$19;
   static final PyCode listen$20;
   static final PyCode bind$21;
   static final PyCode connect$22;
   static final PyCode accept$23;
   static final PyCode send$24;
   static final PyCode recv$25;
   static final PyCode close$26;
   static final PyCode __getattr__$27;
   static final PyCode log$28;
   static final PyCode log_info$29;
   static final PyCode handle_read_event$30;
   static final PyCode handle_connect_event$31;
   static final PyCode handle_write_event$32;
   static final PyCode handle_expt_event$33;
   static final PyCode handle_error$34;
   static final PyCode handle_expt$35;
   static final PyCode handle_read$36;
   static final PyCode handle_write$37;
   static final PyCode handle_connect$38;
   static final PyCode handle_accept$39;
   static final PyCode handle_close$40;
   static final PyCode dispatcher_with_send$41;
   static final PyCode __init__$42;
   static final PyCode initiate_send$43;
   static final PyCode handle_write$44;
   static final PyCode writable$45;
   static final PyCode send$46;
   static final PyCode compact_traceback$47;
   static final PyCode close_all$48;
   static final PyCode file_wrapper$49;
   static final PyCode __init__$50;
   static final PyCode recv$51;
   static final PyCode send$52;
   static final PyCode getsockopt$53;
   static final PyCode close$54;
   static final PyCode fileno$55;
   static final PyCode file_dispatcher$56;
   static final PyCode __init__$57;
   static final PyCode set_file$58;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Basic infrastructure for asynchronous socket service clients and servers.\n\nThere are only two ways to have a program on a single processor do \"more\nthan one thing at a time\".  Multi-threaded programming is the simplest and\nmost popular way to do it, but there is another very different technique,\nthat lets you have nearly all the advantages of multi-threading, without\nactually using multiple threads. it's really only practical if your program\nis largely I/O bound. If your program is CPU bound, then pre-emptive\nscheduled threads are probably what you really need. Network servers are\nrarely CPU-bound, however.\n\nIf your operating system supports the select() system call in its I/O\nlibrary (and nearly all do), then you can use it to juggle multiple\ncommunication channels at once; doing other work while your I/O is taking\nplace in the \"background.\"  Although this strategy can seem strange and\ncomplex, especially at first, it is in many ways easier to understand and\ncontrol than multi-threaded programming. The module documented here solves\nmany of the difficult problems for you, making the task of building\nsophisticated high-performance network servers and clients a snap.\n"));
      var1.setline(47);
      PyString.fromInterned("Basic infrastructure for asynchronous socket service clients and servers.\n\nThere are only two ways to have a program on a single processor do \"more\nthan one thing at a time\".  Multi-threaded programming is the simplest and\nmost popular way to do it, but there is another very different technique,\nthat lets you have nearly all the advantages of multi-threading, without\nactually using multiple threads. it's really only practical if your program\nis largely I/O bound. If your program is CPU bound, then pre-emptive\nscheduled threads are probably what you really need. Network servers are\nrarely CPU-bound, however.\n\nIf your operating system supports the select() system call in its I/O\nlibrary (and nearly all do), then you can use it to juggle multiple\ncommunication channels at once; doing other work while your I/O is taking\nplace in the \"background.\"  Although this strategy can seem strange and\ncomplex, especially at first, it is in many ways easier to understand and\ncontrol than multi-threaded programming. The module documented here solves\nmany of the difficult problems for you, making the task of building\nsophisticated high-performance network servers and clients a snap.\n");
      var1.setline(49);
      PyObject var3 = imp.importOne("select", var1, -1);
      var1.setlocal("select", var3);
      var3 = null;
      var1.setline(50);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(51);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(52);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(53);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(55);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(56);
      String[] var6 = new String[]{"EALREADY", "EINPROGRESS", "EWOULDBLOCK", "ECONNRESET", "EINVAL", "ENOTCONN", "ESHUTDOWN", "EINTR", "EISCONN", "EBADF", "ECONNABORTED", "EPIPE", "EAGAIN", "errorcode"};
      PyObject[] var7 = imp.importFrom("errno", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("EALREADY", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("EINPROGRESS", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("EWOULDBLOCK", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("ECONNRESET", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("EINVAL", var4);
      var4 = null;
      var4 = var7[5];
      var1.setlocal("ENOTCONN", var4);
      var4 = null;
      var4 = var7[6];
      var1.setlocal("ESHUTDOWN", var4);
      var4 = null;
      var4 = var7[7];
      var1.setlocal("EINTR", var4);
      var4 = null;
      var4 = var7[8];
      var1.setlocal("EISCONN", var4);
      var4 = null;
      var4 = var7[9];
      var1.setlocal("EBADF", var4);
      var4 = null;
      var4 = var7[10];
      var1.setlocal("ECONNABORTED", var4);
      var4 = null;
      var4 = var7[11];
      var1.setlocal("EPIPE", var4);
      var4 = null;
      var4 = var7[12];
      var1.setlocal("EAGAIN", var4);
      var4 = null;
      var4 = var7[13];
      var1.setlocal("errorcode", var4);
      var4 = null;
      var1.setline(60);
      var3 = var1.getname("frozenset").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getname("ECONNRESET"), var1.getname("ENOTCONN"), var1.getname("ESHUTDOWN"), var1.getname("ECONNABORTED"), var1.getname("EPIPE"), var1.getname("EBADF")})));
      var1.setlocal("_DISCONNECTED", var3);
      var3 = null;

      try {
         var1.setline(64);
         var1.getname("socket_map");
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getname("NameError"))) {
            throw var8;
         }

         var1.setline(66);
         PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal("socket_map", var11);
         var4 = null;
      }

      var1.setline(68);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, _strerror$1, (PyObject)null);
      var1.setlocal("_strerror", var9);
      var3 = null;
      var1.setline(76);
      var7 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("ExitNow", var7, ExitNow$2);
      var1.setlocal("ExitNow", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(79);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getname("ExitNow"), var1.getname("KeyboardInterrupt"), var1.getname("SystemExit")});
      var1.setlocal("_reraised_exceptions", var10);
      var3 = null;
      var1.setline(81);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, read$3, (PyObject)null);
      var1.setlocal("read", var9);
      var3 = null;
      var1.setline(89);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, write$4, (PyObject)null);
      var1.setlocal("write", var9);
      var3 = null;
      var1.setline(97);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, _exception$5, (PyObject)null);
      var1.setlocal("_exception", var9);
      var3 = null;
      var1.setline(105);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, readwrite$6, (PyObject)null);
      var1.setlocal("readwrite", var9);
      var3 = null;
      var1.setline(125);
      var7 = new PyObject[]{Py.newFloat(0.0), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var7, poll$7, (PyObject)null);
      var1.setlocal("poll", var9);
      var3 = null;
      var1.setline(170);
      var7 = new PyObject[]{Py.newFloat(0.0), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var7, poll2$8, (PyObject)null);
      var1.setlocal("poll2", var9);
      var3 = null;
      var1.setline(203);
      var3 = var1.getname("poll2");
      var1.setlocal("poll3", var3);
      var3 = null;
      var1.setline(205);
      var7 = new PyObject[]{Py.newFloat(30.0), var1.getname("False"), var1.getname("None"), var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var7, loop$9, (PyObject)null);
      var1.setlocal("loop", var9);
      var3 = null;
      var1.setline(223);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("dispatcher", var7, dispatcher$10);
      var1.setlocal("dispatcher", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(529);
      var7 = new PyObject[]{var1.getname("dispatcher")};
      var4 = Py.makeClass("dispatcher_with_send", var7, dispatcher_with_send$41);
      var1.setlocal("dispatcher_with_send", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(556);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, compact_traceback$47, (PyObject)null);
      var1.setlocal("compact_traceback", var9);
      var3 = null;
      var1.setline(576);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      var9 = new PyFunction(var1.f_globals, var7, close_all$48, (PyObject)null);
      var1.setlocal("close_all", var9);
      var3 = null;
      var1.setline(607);
      var3 = var1.getname("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(608);
         var3 = imp.importOne("fcntl", var1, -1);
         var1.setlocal("fcntl", var3);
         var3 = null;
         var1.setline(610);
         var7 = Py.EmptyObjects;
         var4 = Py.makeClass("file_wrapper", var7, file_wrapper$49);
         var1.setlocal("file_wrapper", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
         var1.setline(641);
         var7 = new PyObject[]{var1.getname("dispatcher")};
         var4 = Py.makeClass("file_dispatcher", var7, file_dispatcher$56);
         var1.setlocal("file_dispatcher", var4);
         var4 = null;
         Arrays.fill(var7, (Object)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _strerror$1(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(70);
         var3 = var1.getglobal("os").__getattr__("strerror").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("OverflowError"), var1.getglobal("NameError")}))) {
            var1.setline(72);
            PyObject var5 = var1.getlocal(0);
            PyObject var10000 = var5._in(var1.getglobal("errorcode"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(73);
               var3 = var1.getglobal("errorcode").__getitem__(var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(74);
               var3 = PyString.fromInterned("Unknown error %s")._mod(var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject ExitNow$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(77);
      return var1.getf_locals();
   }

   public PyObject read$3(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(83);
         var1.getlocal(0).__getattr__("handle_read_event").__call__(var2);
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("_reraised_exceptions"))) {
            var1.setline(85);
            throw Py.makeException();
         }

         var1.setline(87);
         var1.getlocal(0).__getattr__("handle_error").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$4(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(91);
         var1.getlocal(0).__getattr__("handle_write_event").__call__(var2);
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("_reraised_exceptions"))) {
            var1.setline(93);
            throw Py.makeException();
         }

         var1.setline(95);
         var1.getlocal(0).__getattr__("handle_error").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _exception$5(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(99);
         var1.getlocal(0).__getattr__("handle_expt_event").__call__(var2);
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("_reraised_exceptions"))) {
            var1.setline(101);
            throw Py.makeException();
         }

         var1.setline(103);
         var1.getlocal(0).__getattr__("handle_error").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readwrite$6(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(107);
         if (var1.getlocal(1)._and(var1.getglobal("select").__getattr__("POLLIN")).__nonzero__()) {
            var1.setline(108);
            var1.getlocal(0).__getattr__("handle_read_event").__call__(var2);
         }

         var1.setline(109);
         if (var1.getlocal(1)._and(var1.getglobal("select").__getattr__("POLLOUT")).__nonzero__()) {
            var1.setline(110);
            var1.getlocal(0).__getattr__("handle_write_event").__call__(var2);
         }

         var1.setline(111);
         if (var1.getlocal(1)._and(var1.getglobal("select").__getattr__("POLLPRI")).__nonzero__()) {
            var1.setline(112);
            var1.getlocal(0).__getattr__("handle_expt_event").__call__(var2);
         }

         var1.setline(113);
         if (var1.getlocal(1)._and(var1.getglobal("select").__getattr__("POLLHUP")._or(var1.getglobal("select").__getattr__("POLLERR"))._or(var1.getglobal("select").__getattr__("POLLNVAL"))).__nonzero__()) {
            var1.setline(114);
            var1.getlocal(0).__getattr__("handle_close").__call__(var2);
         }
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("socket").__getattr__("error"))) {
            PyObject var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(116);
            var4 = var1.getlocal(2).__getattr__("args").__getitem__(Py.newInteger(0));
            PyObject var10000 = var4._notin(var1.getglobal("_DISCONNECTED"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(117);
               var1.getlocal(0).__getattr__("handle_error").__call__(var2);
            } else {
               var1.setline(119);
               var1.getlocal(0).__getattr__("handle_close").__call__(var2);
            }
         } else {
            if (var3.match(var1.getglobal("_reraised_exceptions"))) {
               var1.setline(121);
               throw Py.makeException();
            }

            var1.setline(123);
            var1.getlocal(0).__getattr__("handle_error").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject poll$7(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(127);
         var3 = var1.getglobal("socket_map");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(128);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(129);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var8);
         var3 = null;
         var1.setline(129);
         var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(129);
         var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(130);
         var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         label96:
         while(true) {
            var1.setline(130);
            PyObject var4 = var3.__iternext__();
            PyObject[] var5;
            PyObject var9;
            if (var4 == null) {
               var1.setline(140);
               var8 = new PyList(Py.EmptyObjects);
               PyObject var10001 = var1.getlocal(2);
               PyList var12 = var8;
               var3 = var10001;
               if ((var4 = var12._eq(var10001)).__nonzero__()) {
                  var10001 = var1.getlocal(3);
                  var10000 = var3;
                  var3 = var10001;
                  if ((var4 = var10000._eq(var10001)).__nonzero__()) {
                     var4 = var3._eq(var1.getlocal(4));
                  }
               }

               var3 = null;
               if (var4.__nonzero__()) {
                  var1.setline(141);
                  var1.getglobal("time").__getattr__("sleep").__call__(var2, var1.getlocal(0));
                  var1.setline(142);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               try {
                  var1.setline(145);
                  var3 = var1.getglobal("select").__getattr__("select").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(0));
                  PyObject[] var10 = Py.unpackSequence(var3, 3);
                  var9 = var10[0];
                  var1.setlocal(2, var9);
                  var5 = null;
                  var9 = var10[1];
                  var1.setlocal(3, var9);
                  var5 = null;
                  var9 = var10[2];
                  var1.setlocal(4, var9);
                  var5 = null;
                  var3 = null;
               } catch (Throwable var7) {
                  PyException var11 = Py.setException(var7, var1);
                  if (var11.match(var1.getglobal("select").__getattr__("error"))) {
                     var4 = var11.value;
                     var1.setlocal(9, var4);
                     var4 = null;
                     var1.setline(147);
                     var4 = var1.getlocal(9).__getattr__("args").__getitem__(Py.newInteger(0));
                     var10000 = var4._ne(var1.getglobal("EINTR"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(148);
                        throw Py.makeException();
                     }

                     var1.setline(150);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  throw var11;
               }

               var1.setline(152);
               var3 = var1.getlocal(2).__iter__();

               while(true) {
                  var1.setline(152);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(158);
                     var3 = var1.getlocal(3).__iter__();

                     while(true) {
                        var1.setline(158);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.setline(164);
                           var3 = var1.getlocal(4).__iter__();

                           while(true) {
                              var1.setline(164);
                              var4 = var3.__iternext__();
                              if (var4 == null) {
                                 break label96;
                              }

                              var1.setlocal(5, var4);
                              var1.setline(165);
                              var9 = var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(5));
                              var1.setlocal(6, var9);
                              var5 = null;
                              var1.setline(166);
                              var9 = var1.getlocal(6);
                              var10000 = var9._is(var1.getglobal("None"));
                              var5 = null;
                              if (!var10000.__nonzero__()) {
                                 var1.setline(168);
                                 var1.getglobal("_exception").__call__(var2, var1.getlocal(6));
                              }
                           }
                        }

                        var1.setlocal(5, var4);
                        var1.setline(159);
                        var9 = var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(5));
                        var1.setlocal(6, var9);
                        var5 = null;
                        var1.setline(160);
                        var9 = var1.getlocal(6);
                        var10000 = var9._is(var1.getglobal("None"));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(162);
                           var1.getglobal("write").__call__(var2, var1.getlocal(6));
                        }
                     }
                  }

                  var1.setlocal(5, var4);
                  var1.setline(153);
                  var9 = var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(5));
                  var1.setlocal(6, var9);
                  var5 = null;
                  var1.setline(154);
                  var9 = var1.getlocal(6);
                  var10000 = var9._is(var1.getglobal("None"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(156);
                     var1.getglobal("read").__call__(var2, var1.getlocal(6));
                  }
               }
            }

            var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(131);
            var9 = var1.getlocal(6).__getattr__("readable").__call__(var2);
            var1.setlocal(7, var9);
            var5 = null;
            var1.setline(132);
            var9 = var1.getlocal(6).__getattr__("writable").__call__(var2);
            var1.setlocal(8, var9);
            var5 = null;
            var1.setline(133);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(134);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5));
            }

            var1.setline(136);
            var10000 = var1.getlocal(8);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(6).__getattr__("accepting").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(137);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
            }

            var1.setline(138);
            var10000 = var1.getlocal(7);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(8);
            }

            if (var10000.__nonzero__()) {
               var1.setline(139);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject poll2$8(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(173);
         var3 = var1.getglobal("socket_map");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(174);
      var3 = var1.getlocal(0);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(176);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0)._mul(Py.newInteger(1000)));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(177);
      var3 = var1.getglobal("select").__getattr__("poll").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(178);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(179);
         var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         label63:
         while(true) {
            var1.setline(179);
            PyObject var4 = var3.__iternext__();
            PyObject[] var5;
            PyObject var6;
            PyObject var10;
            if (var4 == null) {
               try {
                  var1.setline(192);
                  var3 = var1.getlocal(2).__getattr__("poll").__call__(var2, var1.getlocal(0));
                  var1.setlocal(6, var3);
                  var3 = null;
               } catch (Throwable var7) {
                  PyException var11 = Py.setException(var7, var1);
                  if (!var11.match(var1.getglobal("select").__getattr__("error"))) {
                     throw var11;
                  }

                  var4 = var11.value;
                  var1.setlocal(7, var4);
                  var4 = null;
                  var1.setline(194);
                  var4 = var1.getlocal(7).__getattr__("args").__getitem__(Py.newInteger(0));
                  var10000 = var4._ne(var1.getglobal("EINTR"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(195);
                     throw Py.makeException();
                  }

                  var1.setline(196);
                  PyList var9 = new PyList(Py.EmptyObjects);
                  var1.setlocal(6, var9);
                  var4 = null;
               }

               var1.setline(197);
               var3 = var1.getlocal(6).__iter__();

               while(true) {
                  var1.setline(197);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break label63;
                  }

                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var1.setline(198);
                  var10 = var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(3));
                  var1.setlocal(4, var10);
                  var5 = null;
                  var1.setline(199);
                  var10 = var1.getlocal(4);
                  var10000 = var10._is(var1.getglobal("None"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(201);
                     var1.getglobal("readwrite").__call__(var2, var1.getlocal(4), var1.getlocal(5));
                  }
               }
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(180);
            PyInteger var8 = Py.newInteger(0);
            var1.setlocal(5, var8);
            var5 = null;
            var1.setline(181);
            if (var1.getlocal(4).__getattr__("readable").__call__(var2).__nonzero__()) {
               var1.setline(182);
               var10 = var1.getlocal(5);
               var10 = var10._ior(var1.getglobal("select").__getattr__("POLLIN")._or(var1.getglobal("select").__getattr__("POLLPRI")));
               var1.setlocal(5, var10);
            }

            var1.setline(184);
            var10000 = var1.getlocal(4).__getattr__("writable").__call__(var2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4).__getattr__("accepting").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(185);
               var10 = var1.getlocal(5);
               var10 = var10._ior(var1.getglobal("select").__getattr__("POLLOUT"));
               var1.setlocal(5, var10);
            }

            var1.setline(186);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(189);
               var10 = var1.getlocal(5);
               var10 = var10._ior(var1.getglobal("select").__getattr__("POLLERR")._or(var1.getglobal("select").__getattr__("POLLHUP"))._or(var1.getglobal("select").__getattr__("POLLNVAL")));
               var1.setlocal(5, var10);
               var1.setline(190);
               var1.getlocal(2).__getattr__("register").__call__(var2, var1.getlocal(3), var1.getlocal(5));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject loop$9(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(207);
         var3 = var1.getglobal("socket_map");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(209);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("select"), (PyObject)PyString.fromInterned("poll"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(210);
         var3 = var1.getglobal("poll2");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(212);
         var3 = var1.getglobal("poll");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(214);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         while(true) {
            var1.setline(215);
            if (!var1.getlocal(2).__nonzero__()) {
               break;
            }

            var1.setline(216);
            var1.getlocal(4).__call__(var2, var1.getlocal(0), var1.getlocal(2));
         }
      } else {
         while(true) {
            var1.setline(219);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(3);
               var10000 = var3._gt(Py.newInteger(0));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(220);
            var1.getlocal(4).__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.setline(221);
            var3 = var1.getlocal(3)._sub(Py.newInteger(1));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dispatcher$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(225);
      PyObject var3 = var1.getname("False");
      var1.setlocal("debug", var3);
      var3 = null;
      var1.setline(226);
      var3 = var1.getname("False");
      var1.setlocal("connected", var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getname("False");
      var1.setlocal("accepting", var3);
      var3 = null;
      var1.setline(228);
      var3 = var1.getname("False");
      var1.setlocal("connecting", var3);
      var3 = null;
      var1.setline(229);
      var3 = var1.getname("False");
      var1.setlocal("closing", var3);
      var3 = null;
      var1.setline(230);
      var3 = var1.getname("None");
      var1.setlocal("addr", var3);
      var3 = null;
      var1.setline(231);
      var3 = var1.getname("frozenset").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("warning")})));
      var1.setlocal("ignore_log_types", var3);
      var3 = null;
      var1.setline(233);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(265);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$12, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(278);
      var3 = var1.getname("__repr__");
      var1.setlocal("__str__", var3);
      var3 = null;
      var1.setline(280);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, add_channel$13, (PyObject)null);
      var1.setlocal("add_channel", var5);
      var3 = null;
      var1.setline(286);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, del_channel$14, (PyObject)null);
      var1.setlocal("del_channel", var5);
      var3 = null;
      var1.setline(295);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, create_socket$15, (PyObject)null);
      var1.setlocal("create_socket", var5);
      var3 = null;
      var1.setline(301);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, set_socket$16, (PyObject)null);
      var1.setlocal("set_socket", var5);
      var3 = null;
      var1.setline(307);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_reuse_addr$17, (PyObject)null);
      var1.setlocal("set_reuse_addr", var5);
      var3 = null;
      var1.setline(324);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readable$18, (PyObject)null);
      var1.setlocal("readable", var5);
      var3 = null;
      var1.setline(327);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writable$19, (PyObject)null);
      var1.setlocal("writable", var5);
      var3 = null;
      var1.setline(334);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, listen$20, (PyObject)null);
      var1.setlocal("listen", var5);
      var3 = null;
      var1.setline(340);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, bind$21, (PyObject)null);
      var1.setlocal("bind", var5);
      var3 = null;
      var1.setline(344);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, connect$22, (PyObject)null);
      var1.setlocal("connect", var5);
      var3 = null;
      var1.setline(358);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, accept$23, (PyObject)null);
      var1.setlocal("accept", var5);
      var3 = null;
      var1.setline(372);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, send$24, (PyObject)null);
      var1.setlocal("send", var5);
      var3 = null;
      var1.setline(385);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, recv$25, (PyObject)null);
      var1.setlocal("recv", var5);
      var3 = null;
      var1.setline(403);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$26, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(416);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __getattr__$27, (PyObject)null);
      var1.setlocal("__getattr__", var5);
      var3 = null;
      var1.setline(432);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, log$28, (PyObject)null);
      var1.setlocal("log", var5);
      var3 = null;
      var1.setline(435);
      var4 = new PyObject[]{PyString.fromInterned("info")};
      var5 = new PyFunction(var1.f_globals, var4, log_info$29, (PyObject)null);
      var1.setlocal("log_info", var5);
      var3 = null;
      var1.setline(439);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_read_event$30, (PyObject)null);
      var1.setlocal("handle_read_event", var5);
      var3 = null;
      var1.setline(451);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_connect_event$31, (PyObject)null);
      var1.setlocal("handle_connect_event", var5);
      var3 = null;
      var1.setline(459);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_write_event$32, (PyObject)null);
      var1.setlocal("handle_write_event", var5);
      var3 = null;
      var1.setline(470);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_expt_event$33, (PyObject)null);
      var1.setlocal("handle_expt_event", var5);
      var3 = null;
      var1.setline(485);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_error$34, (PyObject)null);
      var1.setlocal("handle_error", var5);
      var3 = null;
      var1.setline(505);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_expt$35, (PyObject)null);
      var1.setlocal("handle_expt", var5);
      var3 = null;
      var1.setline(508);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_read$36, (PyObject)null);
      var1.setlocal("handle_read", var5);
      var3 = null;
      var1.setline(511);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_write$37, (PyObject)null);
      var1.setlocal("handle_write", var5);
      var3 = null;
      var1.setline(514);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_connect$38, (PyObject)null);
      var1.setlocal("handle_connect", var5);
      var3 = null;
      var1.setline(517);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_accept$39, (PyObject)null);
      var1.setlocal("handle_accept", var5);
      var3 = null;
      var1.setline(520);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_close$40, (PyObject)null);
      var1.setlocal("handle_close", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(235);
         var3 = var1.getglobal("socket_map");
         var1.getlocal(0).__setattr__("_map", var3);
         var3 = null;
      } else {
         var1.setline(237);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_map", var3);
         var3 = null;
      }

      var1.setline(239);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_fileno", var3);
      var3 = null;
      var1.setline(241);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(244);
         var1.getlocal(1).__getattr__("setblocking").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(245);
         var1.getlocal(0).__getattr__("set_socket").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(246);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("connected", var3);
         var3 = null;

         try {
            var1.setline(250);
            var3 = var1.getlocal(1).__getattr__("getpeername").__call__(var2);
            var1.getlocal(0).__setattr__("addr", var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var6;
            }

            PyObject var4 = var6.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(252);
            var4 = var1.getlocal(3).__getattr__("args").__getitem__(Py.newInteger(0));
            var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("ENOTCONN"), var1.getglobal("EINVAL")}));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(260);
               var1.getlocal(0).__getattr__("del_channel").__call__(var2, var1.getlocal(2));
               var1.setline(261);
               throw Py.makeException();
            }

            var1.setline(255);
            var4 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("connected", var4);
            var4 = null;
         }
      } else {
         var1.setline(263);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("socket", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$12(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__module__")._add(PyString.fromInterned("."))._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"))});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(267);
      PyObject var10000 = var1.getlocal(0).__getattr__("accepting");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("addr");
      }

      if (var10000.__nonzero__()) {
         var1.setline(268);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("listening"));
      } else {
         var1.setline(269);
         if (var1.getlocal(0).__getattr__("connected").__nonzero__()) {
            var1.setline(270);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("connected"));
         }
      }

      var1.setline(271);
      PyObject var5 = var1.getlocal(0).__getattr__("addr");
      var10000 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(273);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("%s:%d")._mod(var1.getlocal(0).__getattr__("addr")));
         } catch (Throwable var4) {
            PyException var6 = Py.setException(var4, var1);
            if (!var6.match(var1.getglobal("TypeError"))) {
               throw var6;
            }

            var1.setline(275);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("addr")));
         }
      }

      var1.setline(276);
      var5 = PyString.fromInterned("<%s at %#x>")._mod(new PyTuple(new PyObject[]{PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(1)), var1.getglobal("id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject add_channel$13(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(283);
         var3 = var1.getlocal(0).__getattr__("_map");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(284);
      var3 = var1.getlocal(0);
      var1.getlocal(1).__setitem__(var1.getlocal(0).__getattr__("_fileno"), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject del_channel$14(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getlocal(0).__getattr__("_fileno");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(288);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(289);
         var3 = var1.getlocal(0).__getattr__("_map");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(290);
      var3 = var1.getlocal(2);
      var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(292);
         var1.getlocal(1).__delitem__(var1.getlocal(2));
      }

      var1.setline(293);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_fileno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject create_socket$15(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.getlocal(0).__setattr__((String)"family_and_type", var3);
      var3 = null;
      var1.setline(297);
      PyObject var4 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(298);
      var1.getlocal(3).__getattr__("setblocking").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(299);
      var1.getlocal(0).__getattr__("set_socket").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_socket$16(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("socket", var3);
      var3 = null;
      var1.setline(304);
      var3 = var1.getlocal(1).__getattr__("fileno").__call__(var2);
      var1.getlocal(0).__setattr__("_fileno", var3);
      var3 = null;
      var1.setline(305);
      var1.getlocal(0).__getattr__("add_channel").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_reuse_addr$17(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(310);
         var1.getlocal(0).__getattr__("socket").__getattr__("setsockopt").__call__(var2, var1.getglobal("socket").__getattr__("SOL_SOCKET"), var1.getglobal("socket").__getattr__("SO_REUSEADDR"), var1.getlocal(0).__getattr__("socket").__getattr__("getsockopt").__call__(var2, var1.getglobal("socket").__getattr__("SOL_SOCKET"), var1.getglobal("socket").__getattr__("SO_REUSEADDR"))._or(Py.newInteger(1)));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("socket").__getattr__("error"))) {
            throw var3;
         }

         var1.setline(316);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readable$18(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$19(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject listen$20(PyFrame var1, ThreadState var2) {
      var1.setline(335);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("accepting", var3);
      var3 = null;
      var1.setline(336);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._gt(Py.newInteger(5));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(337);
         PyInteger var4 = Py.newInteger(5);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(338);
      var3 = var1.getlocal(0).__getattr__("socket").__getattr__("listen").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject bind$21(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("addr", var3);
      var3 = null;
      var1.setline(342);
      var3 = var1.getlocal(0).__getattr__("socket").__getattr__("bind").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject connect$22(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("connected", var3);
      var3 = null;
      var1.setline(346);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("connecting", var3);
      var3 = null;
      var1.setline(347);
      var3 = var1.getlocal(0).__getattr__("socket").__getattr__("connect_ex").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(348);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("EINPROGRESS"), var1.getglobal("EALREADY"), var1.getglobal("EWOULDBLOCK")}));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("EINVAL"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("name");
            var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("nt"), PyString.fromInterned("ce")}));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(350);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("addr", var3);
         var3 = null;
         var1.setline(351);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(352);
         var3 = var1.getlocal(2);
         var10000 = var3._in(new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("EISCONN")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(353);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("addr", var3);
            var3 = null;
            var1.setline(354);
            var1.getlocal(0).__getattr__("handle_connect_event").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(356);
            throw Py.makeException(var1.getglobal("socket").__getattr__("error").__call__(var2, var1.getlocal(2), var1.getglobal("errorcode").__getitem__(var1.getlocal(2))));
         }
      }
   }

   public PyObject accept$23(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var5;
      try {
         var1.setline(361);
         PyObject var7 = var1.getlocal(0).__getattr__("socket").__getattr__("accept").__call__(var2);
         PyObject[] var8 = Py.unpackSequence(var7, 2);
         var5 = var8[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         PyObject var4;
         if (var3.match(var1.getglobal("TypeError"))) {
            var1.setline(363);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         if (var3.match(var1.getglobal("socket").__getattr__("error"))) {
            var5 = var3.value;
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(365);
            var5 = var1.getlocal(3).__getattr__("args").__getitem__(Py.newInteger(0));
            PyObject var10000 = var5._in(new PyTuple(new PyObject[]{var1.getglobal("EWOULDBLOCK"), var1.getglobal("ECONNABORTED"), var1.getglobal("EAGAIN")}));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(366);
               var4 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var4;
            }

            var1.setline(368);
            throw Py.makeException();
         }

         throw var3;
      }

      var1.setline(370);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject send$24(PyFrame var1, ThreadState var2) {
      PyInteger var3;
      try {
         var1.setline(374);
         PyObject var7 = var1.getlocal(0).__getattr__("socket").__getattr__("send").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(375);
         var7 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var7;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("socket").__getattr__("error"))) {
            PyObject var5 = var4.value;
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(377);
            var5 = var1.getlocal(3).__getattr__("args").__getitem__(Py.newInteger(0));
            PyObject var10000 = var5._eq(var1.getglobal("EWOULDBLOCK"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(378);
               var3 = Py.newInteger(0);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(379);
               var5 = var1.getlocal(3).__getattr__("args").__getitem__(Py.newInteger(0));
               var10000 = var5._in(var1.getglobal("_DISCONNECTED"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(380);
                  var1.getlocal(0).__getattr__("handle_close").__call__(var2);
                  var1.setline(381);
                  var3 = Py.newInteger(0);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(383);
                  throw Py.makeException();
               }
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject recv$25(PyFrame var1, ThreadState var2) {
      PyString var3;
      try {
         var1.setline(387);
         PyObject var7 = var1.getlocal(0).__getattr__("socket").__getattr__("recv").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(388);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(391);
            var1.getlocal(0).__getattr__("handle_close").__call__(var2);
            var1.setline(392);
            var3 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(394);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("socket").__getattr__("error"))) {
            PyObject var5 = var4.value;
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(397);
            var5 = var1.getlocal(3).__getattr__("args").__getitem__(Py.newInteger(0));
            PyObject var10000 = var5._in(var1.getglobal("_DISCONNECTED"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(398);
               var1.getlocal(0).__getattr__("handle_close").__call__(var2);
               var1.setline(399);
               var3 = PyString.fromInterned("");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(401);
               throw Py.makeException();
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject close$26(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("connected", var3);
      var3 = null;
      var1.setline(405);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("accepting", var3);
      var3 = null;
      var1.setline(406);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("connecting", var3);
      var3 = null;
      var1.setline(407);
      var1.getlocal(0).__getattr__("del_channel").__call__(var2);

      try {
         var1.setline(409);
         var1.getlocal(0).__getattr__("socket").__getattr__("close").__call__(var2);
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("socket").__getattr__("error"))) {
            throw var6;
         }

         PyObject var4 = var6.value;
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(411);
         var4 = var1.getlocal(1).__getattr__("args").__getitem__(Py.newInteger(0));
         PyObject var10000 = var4._notin(new PyTuple(new PyObject[]{var1.getglobal("ENOTCONN"), var1.getglobal("EBADF")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(412);
            throw Py.makeException();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$27(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(418);
         PyObject var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("socket"), var1.getlocal(1));
         var1.setlocal(2, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            var1.setline(420);
            throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("%s instance has no attribute '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(1)}))));
         }

         throw var3;
      }

      var1.setline(423);
      PyObject var4 = PyString.fromInterned("%(me)s.%(attr)s is deprecated. Use %(me)s.socket.%(attr)s instead.")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("me"), var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), PyString.fromInterned("attr"), var1.getlocal(1)}));
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(425);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warn");
      PyObject[] var8 = new PyObject[]{var1.getlocal(3), var1.getglobal("DeprecationWarning"), Py.newInteger(2)};
      String[] var5 = new String[]{"stacklevel"};
      var10000.__call__(var2, var8, var5);
      var4 = null;
      var1.setline(426);
      var4 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject log$28(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("log: %s\n")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject log_info$29(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("ignore_log_types"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(437);
         Py.println(PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_read_event$30(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      if (var1.getlocal(0).__getattr__("accepting").__nonzero__()) {
         var1.setline(443);
         var1.getlocal(0).__getattr__("handle_accept").__call__(var2);
      } else {
         var1.setline(444);
         if (var1.getlocal(0).__getattr__("connected").__not__().__nonzero__()) {
            var1.setline(445);
            if (var1.getlocal(0).__getattr__("connecting").__nonzero__()) {
               var1.setline(446);
               var1.getlocal(0).__getattr__("handle_connect_event").__call__(var2);
            }

            var1.setline(447);
            var1.getlocal(0).__getattr__("handle_read").__call__(var2);
         } else {
            var1.setline(449);
            var1.getlocal(0).__getattr__("handle_read").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_connect_event$31(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("getsockopt").__call__(var2, var1.getglobal("socket").__getattr__("SOL_SOCKET"), var1.getglobal("socket").__getattr__("SO_ERROR"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(453);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(454);
         throw Py.makeException(var1.getglobal("socket").__getattr__("error").__call__(var2, var1.getlocal(1), var1.getglobal("_strerror").__call__(var2, var1.getlocal(1))));
      } else {
         var1.setline(455);
         var1.getlocal(0).__getattr__("handle_connect").__call__(var2);
         var1.setline(456);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("connected", var3);
         var3 = null;
         var1.setline(457);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("connecting", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject handle_write_event$32(PyFrame var1, ThreadState var2) {
      var1.setline(460);
      if (var1.getlocal(0).__getattr__("accepting").__nonzero__()) {
         var1.setline(463);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(465);
         if (var1.getlocal(0).__getattr__("connected").__not__().__nonzero__()) {
            var1.setline(466);
            if (var1.getlocal(0).__getattr__("connecting").__nonzero__()) {
               var1.setline(467);
               var1.getlocal(0).__getattr__("handle_connect_event").__call__(var2);
            }
         }

         var1.setline(468);
         var1.getlocal(0).__getattr__("handle_write").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject handle_expt_event$33(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      PyObject var3 = var1.getlocal(0).__getattr__("socket").__getattr__("getsockopt").__call__(var2, var1.getglobal("socket").__getattr__("SOL_SOCKET"), var1.getglobal("socket").__getattr__("SO_ERROR"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(475);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(481);
         var1.getlocal(0).__getattr__("handle_close").__call__(var2);
      } else {
         var1.setline(483);
         var1.getlocal(0).__getattr__("handle_expt").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_error$34(PyFrame var1, ThreadState var2) {
      var1.setline(486);
      PyObject var3 = var1.getglobal("compact_traceback").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;

      try {
         var1.setline(490);
         var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(492);
         PyObject var7 = PyString.fromInterned("<__repr__(self) failed for object at %0x>")._mod(var1.getglobal("id").__call__(var2, var1.getlocal(0)));
         var1.setlocal(5, var7);
         var4 = null;
      }

      var1.setline(494);
      var1.getlocal(0).__getattr__("log_info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("uncaptured python exception, closing channel %s (%s:%s %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})), (PyObject)PyString.fromInterned("error"));
      var1.setline(503);
      var1.getlocal(0).__getattr__("handle_close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_expt$35(PyFrame var1, ThreadState var2) {
      var1.setline(506);
      var1.getlocal(0).__getattr__("log_info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unhandled incoming priority event"), (PyObject)PyString.fromInterned("warning"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_read$36(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      var1.getlocal(0).__getattr__("log_info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unhandled read event"), (PyObject)PyString.fromInterned("warning"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_write$37(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      var1.getlocal(0).__getattr__("log_info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unhandled write event"), (PyObject)PyString.fromInterned("warning"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_connect$38(PyFrame var1, ThreadState var2) {
      var1.setline(515);
      var1.getlocal(0).__getattr__("log_info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unhandled connect event"), (PyObject)PyString.fromInterned("warning"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_accept$39(PyFrame var1, ThreadState var2) {
      var1.setline(518);
      var1.getlocal(0).__getattr__("log_info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unhandled accept event"), (PyObject)PyString.fromInterned("warning"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_close$40(PyFrame var1, ThreadState var2) {
      var1.setline(521);
      var1.getlocal(0).__getattr__("log_info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unhandled close event"), (PyObject)PyString.fromInterned("warning"));
      var1.setline(522);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dispatcher_with_send$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(531);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$42, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(535);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, initiate_send$43, (PyObject)null);
      var1.setlocal("initiate_send", var4);
      var3 = null;
      var1.setline(540);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handle_write$44, (PyObject)null);
      var1.setlocal("handle_write", var4);
      var3 = null;
      var1.setline(543);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writable$45, (PyObject)null);
      var1.setlocal("writable", var4);
      var3 = null;
      var1.setline(546);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$46, (PyObject)null);
      var1.setlocal("send", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$42(PyFrame var1, ThreadState var2) {
      var1.setline(532);
      var1.getglobal("dispatcher").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(533);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"out_buffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initiate_send$43(PyFrame var1, ThreadState var2) {
      var1.setline(536);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(537);
      PyObject var4 = var1.getglobal("dispatcher").__getattr__("send").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("out_buffer").__getslice__((PyObject)null, Py.newInteger(512), (PyObject)null));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(538);
      var4 = var1.getlocal(0).__getattr__("out_buffer").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("out_buffer", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_write$44(PyFrame var1, ThreadState var2) {
      var1.setline(541);
      var1.getlocal(0).__getattr__("initiate_send").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writable$45(PyFrame var1, ThreadState var2) {
      var1.setline(544);
      PyObject var10000 = var1.getlocal(0).__getattr__("connected").__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("out_buffer"));
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject send$46(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
         var1.setline(548);
         var1.getlocal(0).__getattr__("log_info").__call__(var2, PyString.fromInterned("sending %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
      }

      var1.setline(549);
      PyObject var3 = var1.getlocal(0).__getattr__("out_buffer")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("out_buffer", var3);
      var3 = null;
      var1.setline(550);
      var1.getlocal(0).__getattr__("initiate_send").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compact_traceback$47(PyFrame var1, ThreadState var2) {
      var1.setline(557);
      PyObject var3 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(0, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(558);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(559);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(560);
         throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("traceback does not exist")));
      } else {
         while(true) {
            var1.setline(561);
            if (!var1.getlocal(2).__nonzero__()) {
               var1.setline(570);
               var1.dellocal(2);
               var1.setline(572);
               var3 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
               var4 = Py.unpackSequence(var3, 3);
               var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(6, var5);
               var5 = null;
               var3 = null;
               var1.setline(573);
               PyObject var10000 = PyString.fromInterned(" ").__getattr__("join");
               PyList var10002 = new PyList();
               var3 = var10002.__getattr__("append");
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(573);
               var3 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(573);
                  PyObject var7 = var3.__iternext__();
                  if (var7 == null) {
                     var1.setline(573);
                     var1.dellocal(8);
                     var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                     var1.setlocal(7, var3);
                     var3 = null;
                     var1.setline(574);
                     PyTuple var8 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)}), var1.getlocal(0), var1.getlocal(1), var1.getlocal(7)});
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setlocal(9, var7);
                  var1.setline(573);
                  var1.getlocal(8).__call__(var2, PyString.fromInterned("[%s|%s|%s]")._mod(var1.getlocal(9)));
               }
            }

            var1.setline(562);
            var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("tb_frame").__getattr__("f_code").__getattr__("co_filename"), var1.getlocal(2).__getattr__("tb_frame").__getattr__("f_code").__getattr__("co_name"), var1.getglobal("str").__call__(var2, var1.getlocal(2).__getattr__("tb_lineno"))})));
            var1.setline(567);
            var3 = var1.getlocal(2).__getattr__("tb_next");
            var1.setlocal(2, var3);
            var3 = null;
         }
      }
   }

   public PyObject close_all$48(PyFrame var1, ThreadState var2) {
      var1.setline(577);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(578);
         var3 = var1.getglobal("socket_map");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(579);
      var3 = var1.getlocal(0).__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(579);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(592);
            var1.getlocal(0).__getattr__("clear").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);

         try {
            var1.setline(581);
            var1.getlocal(2).__getattr__("close").__call__(var2);
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("OSError"))) {
               PyObject var6 = var5.value;
               var1.setlocal(2, var6);
               var6 = null;
               var1.setline(583);
               var6 = var1.getlocal(2).__getattr__("args").__getitem__(Py.newInteger(0));
               var10000 = var6._eq(var1.getglobal("EBADF"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(584);
               } else {
                  var1.setline(585);
                  if (var1.getlocal(1).__not__().__nonzero__()) {
                     var1.setline(586);
                     throw Py.makeException();
                  }
               }
            } else {
               if (var5.match(var1.getglobal("_reraised_exceptions"))) {
                  var1.setline(588);
                  throw Py.makeException();
               }

               var1.setline(590);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(591);
                  throw Py.makeException();
               }
            }
         }
      }
   }

   public PyObject file_wrapper$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(615);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$50, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(618);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, recv$51, (PyObject)null);
      var1.setlocal("recv", var4);
      var3 = null;
      var1.setline(621);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$52, (PyObject)null);
      var1.setlocal("send", var4);
      var3 = null;
      var1.setline(624);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getsockopt$53, (PyObject)null);
      var1.setlocal("getsockopt", var4);
      var3 = null;
      var1.setline(632);
      PyObject var5 = var1.getname("recv");
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(633);
      var5 = var1.getname("send");
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(635);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$54, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(638);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fileno$55, (PyObject)null);
      var1.setlocal("fileno", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$50(PyFrame var1, ThreadState var2) {
      var1.setline(616);
      PyObject var3 = var1.getglobal("os").__getattr__("dup").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("fd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject recv$51(PyFrame var1, ThreadState var2) {
      var1.setline(619);
      PyObject var10000 = var1.getglobal("os").__getattr__("read");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("fd")};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject send$52(PyFrame var1, ThreadState var2) {
      var1.setline(622);
      PyObject var10000 = var1.getglobal("os").__getattr__("write");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("fd")};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject getsockopt$53(PyFrame var1, ThreadState var2) {
      var1.setline(625);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("socket").__getattr__("SOL_SOCKET"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("socket").__getattr__("SO_ERROR"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__not__();
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(628);
         PyInteger var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(629);
         throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Only asyncore specific behaviour implemented.")));
      }
   }

   public PyObject close$54(PyFrame var1, ThreadState var2) {
      var1.setline(636);
      var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(0).__getattr__("fd"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileno$55(PyFrame var1, ThreadState var2) {
      var1.setline(639);
      PyObject var3 = var1.getlocal(0).__getattr__("fd");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject file_dispatcher$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(643);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$57, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(656);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_file$58, (PyObject)null);
      var1.setlocal("set_file", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$57(PyFrame var1, ThreadState var2) {
      var1.setline(644);
      var1.getglobal("dispatcher").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getglobal("None"), var1.getlocal(2));
      var1.setline(645);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("connected", var3);
      var3 = null;

      try {
         var1.setline(647);
         var3 = var1.getlocal(1).__getattr__("fileno").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("AttributeError"))) {
            throw var5;
         }

         var1.setline(649);
      }

      var1.setline(650);
      var1.getlocal(0).__getattr__("set_file").__call__(var2, var1.getlocal(1));
      var1.setline(652);
      var3 = var1.getglobal("fcntl").__getattr__("fcntl").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getglobal("fcntl").__getattr__("F_GETFL"), (PyObject)Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(653);
      var3 = var1.getlocal(3)._or(var1.getglobal("os").__getattr__("O_NONBLOCK"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(654);
      var1.getglobal("fcntl").__getattr__("fcntl").__call__(var2, var1.getlocal(1), var1.getglobal("fcntl").__getattr__("F_SETFL"), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_file$58(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyObject var3 = var1.getglobal("file_wrapper").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("socket", var3);
      var3 = null;
      var1.setline(658);
      var3 = var1.getlocal(0).__getattr__("socket").__getattr__("fileno").__call__(var2);
      var1.getlocal(0).__setattr__("_fileno", var3);
      var3 = null;
      var1.setline(659);
      var1.getlocal(0).__getattr__("add_channel").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public asyncore$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"err"};
      _strerror$1 = Py.newCode(1, var2, var1, "_strerror", 68, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExitNow$2 = Py.newCode(0, var2, var1, "ExitNow", 76, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"obj"};
      read$3 = Py.newCode(1, var2, var1, "read", 81, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj"};
      write$4 = Py.newCode(1, var2, var1, "write", 89, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj"};
      _exception$5 = Py.newCode(1, var2, var1, "_exception", 97, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "flags", "e"};
      readwrite$6 = Py.newCode(2, var2, var1, "readwrite", 105, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timeout", "map", "r", "w", "e", "fd", "obj", "is_r", "is_w", "err"};
      poll$7 = Py.newCode(2, var2, var1, "poll", 125, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timeout", "map", "pollster", "fd", "obj", "flags", "r", "err"};
      poll2$8 = Py.newCode(2, var2, var1, "poll2", 170, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timeout", "use_poll", "map", "count", "poll_fun"};
      loop$9 = Py.newCode(4, var2, var1, "loop", 205, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      dispatcher$10 = Py.newCode(0, var2, var1, "dispatcher", 223, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sock", "map", "err"};
      __init__$11 = Py.newCode(3, var2, var1, "__init__", 233, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "status"};
      __repr__$12 = Py.newCode(1, var2, var1, "__repr__", 265, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "map"};
      add_channel$13 = Py.newCode(2, var2, var1, "add_channel", 280, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "map", "fd"};
      del_channel$14 = Py.newCode(2, var2, var1, "del_channel", 286, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "family", "type", "sock"};
      create_socket$15 = Py.newCode(3, var2, var1, "create_socket", 295, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sock", "map"};
      set_socket$16 = Py.newCode(3, var2, var1, "set_socket", 301, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      set_reuse_addr$17 = Py.newCode(1, var2, var1, "set_reuse_addr", 307, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$18 = Py.newCode(1, var2, var1, "readable", 324, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$19 = Py.newCode(1, var2, var1, "writable", 327, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "num"};
      listen$20 = Py.newCode(2, var2, var1, "listen", 334, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "addr"};
      bind$21 = Py.newCode(2, var2, var1, "bind", 340, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "address", "err"};
      connect$22 = Py.newCode(2, var2, var1, "connect", 344, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "conn", "addr", "why"};
      accept$23 = Py.newCode(1, var2, var1, "accept", 358, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "result", "why"};
      send$24 = Py.newCode(2, var2, var1, "send", 372, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buffer_size", "data", "why"};
      recv$25 = Py.newCode(2, var2, var1, "recv", 385, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "why"};
      close$26 = Py.newCode(1, var2, var1, "close", 403, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr", "retattr", "msg"};
      __getattr__$27 = Py.newCode(2, var2, var1, "__getattr__", 416, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      log$28 = Py.newCode(2, var2, var1, "log", 432, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "type"};
      log_info$29 = Py.newCode(3, var2, var1, "log_info", 435, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_read_event$30 = Py.newCode(1, var2, var1, "handle_read_event", 439, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "err"};
      handle_connect_event$31 = Py.newCode(1, var2, var1, "handle_connect_event", 451, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_write_event$32 = Py.newCode(1, var2, var1, "handle_write_event", 459, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "err"};
      handle_expt_event$33 = Py.newCode(1, var2, var1, "handle_expt_event", 470, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nil", "t", "v", "tbinfo", "self_repr"};
      handle_error$34 = Py.newCode(1, var2, var1, "handle_error", 485, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_expt$35 = Py.newCode(1, var2, var1, "handle_expt", 505, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_read$36 = Py.newCode(1, var2, var1, "handle_read", 508, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_write$37 = Py.newCode(1, var2, var1, "handle_write", 511, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_connect$38 = Py.newCode(1, var2, var1, "handle_connect", 514, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_accept$39 = Py.newCode(1, var2, var1, "handle_accept", 517, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_close$40 = Py.newCode(1, var2, var1, "handle_close", 520, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      dispatcher_with_send$41 = Py.newCode(0, var2, var1, "dispatcher_with_send", 529, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sock", "map"};
      __init__$42 = Py.newCode(3, var2, var1, "__init__", 531, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "num_sent"};
      initiate_send$43 = Py.newCode(1, var2, var1, "initiate_send", 535, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_write$44 = Py.newCode(1, var2, var1, "handle_write", 540, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$45 = Py.newCode(1, var2, var1, "writable", 543, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send$46 = Py.newCode(2, var2, var1, "send", 546, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t", "v", "tb", "tbinfo", "file", "function", "line", "info", "_[573_21]", "x"};
      compact_traceback$47 = Py.newCode(0, var2, var1, "compact_traceback", 556, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"map", "ignore_all", "x"};
      close_all$48 = Py.newCode(2, var2, var1, "close_all", 576, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      file_wrapper$49 = Py.newCode(0, var2, var1, "file_wrapper", 610, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fd"};
      __init__$50 = Py.newCode(2, var2, var1, "__init__", 615, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      recv$51 = Py.newCode(2, var2, var1, "recv", 618, true, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      send$52 = Py.newCode(2, var2, var1, "send", 621, true, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "optname", "buflen"};
      getsockopt$53 = Py.newCode(4, var2, var1, "getsockopt", 624, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$54 = Py.newCode(1, var2, var1, "close", 635, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      fileno$55 = Py.newCode(1, var2, var1, "fileno", 638, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      file_dispatcher$56 = Py.newCode(0, var2, var1, "file_dispatcher", 641, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fd", "map", "flags"};
      __init__$57 = Py.newCode(3, var2, var1, "__init__", 643, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd"};
      set_file$58 = Py.newCode(2, var2, var1, "set_file", 656, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new asyncore$py("asyncore$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(asyncore$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._strerror$1(var2, var3);
         case 2:
            return this.ExitNow$2(var2, var3);
         case 3:
            return this.read$3(var2, var3);
         case 4:
            return this.write$4(var2, var3);
         case 5:
            return this._exception$5(var2, var3);
         case 6:
            return this.readwrite$6(var2, var3);
         case 7:
            return this.poll$7(var2, var3);
         case 8:
            return this.poll2$8(var2, var3);
         case 9:
            return this.loop$9(var2, var3);
         case 10:
            return this.dispatcher$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.__repr__$12(var2, var3);
         case 13:
            return this.add_channel$13(var2, var3);
         case 14:
            return this.del_channel$14(var2, var3);
         case 15:
            return this.create_socket$15(var2, var3);
         case 16:
            return this.set_socket$16(var2, var3);
         case 17:
            return this.set_reuse_addr$17(var2, var3);
         case 18:
            return this.readable$18(var2, var3);
         case 19:
            return this.writable$19(var2, var3);
         case 20:
            return this.listen$20(var2, var3);
         case 21:
            return this.bind$21(var2, var3);
         case 22:
            return this.connect$22(var2, var3);
         case 23:
            return this.accept$23(var2, var3);
         case 24:
            return this.send$24(var2, var3);
         case 25:
            return this.recv$25(var2, var3);
         case 26:
            return this.close$26(var2, var3);
         case 27:
            return this.__getattr__$27(var2, var3);
         case 28:
            return this.log$28(var2, var3);
         case 29:
            return this.log_info$29(var2, var3);
         case 30:
            return this.handle_read_event$30(var2, var3);
         case 31:
            return this.handle_connect_event$31(var2, var3);
         case 32:
            return this.handle_write_event$32(var2, var3);
         case 33:
            return this.handle_expt_event$33(var2, var3);
         case 34:
            return this.handle_error$34(var2, var3);
         case 35:
            return this.handle_expt$35(var2, var3);
         case 36:
            return this.handle_read$36(var2, var3);
         case 37:
            return this.handle_write$37(var2, var3);
         case 38:
            return this.handle_connect$38(var2, var3);
         case 39:
            return this.handle_accept$39(var2, var3);
         case 40:
            return this.handle_close$40(var2, var3);
         case 41:
            return this.dispatcher_with_send$41(var2, var3);
         case 42:
            return this.__init__$42(var2, var3);
         case 43:
            return this.initiate_send$43(var2, var3);
         case 44:
            return this.handle_write$44(var2, var3);
         case 45:
            return this.writable$45(var2, var3);
         case 46:
            return this.send$46(var2, var3);
         case 47:
            return this.compact_traceback$47(var2, var3);
         case 48:
            return this.close_all$48(var2, var3);
         case 49:
            return this.file_wrapper$49(var2, var3);
         case 50:
            return this.__init__$50(var2, var3);
         case 51:
            return this.recv$51(var2, var3);
         case 52:
            return this.send$52(var2, var3);
         case 53:
            return this.getsockopt$53(var2, var3);
         case 54:
            return this.close$54(var2, var3);
         case 55:
            return this.fileno$55(var2, var3);
         case 56:
            return this.file_dispatcher$56(var2, var3);
         case 57:
            return this.__init__$57(var2, var3);
         case 58:
            return this.set_file$58(var2, var3);
         default:
            return null;
      }
   }
}
