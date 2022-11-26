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
@Filename("asynchat.py")
public class asynchat$py extends PyFunctionTable implements PyRunnable {
   static asynchat$py self;
   static final PyCode f$0;
   static final PyCode async_chat$1;
   static final PyCode __init__$2;
   static final PyCode collect_incoming_data$3;
   static final PyCode _collect_incoming_data$4;
   static final PyCode _get_data$5;
   static final PyCode found_terminator$6;
   static final PyCode set_terminator$7;
   static final PyCode get_terminator$8;
   static final PyCode handle_read$9;
   static final PyCode handle_write$10;
   static final PyCode handle_close$11;
   static final PyCode push$12;
   static final PyCode push_with_producer$13;
   static final PyCode readable$14;
   static final PyCode writable$15;
   static final PyCode close_when_done$16;
   static final PyCode initiate_send$17;
   static final PyCode discard_buffers$18;
   static final PyCode simple_producer$19;
   static final PyCode __init__$20;
   static final PyCode more$21;
   static final PyCode fifo$22;
   static final PyCode __init__$23;
   static final PyCode __len__$24;
   static final PyCode is_empty$25;
   static final PyCode first$26;
   static final PyCode push$27;
   static final PyCode pop$28;
   static final PyCode find_prefix_at_end$29;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A class supporting chat-style (command/response) protocols.\n\nThis class adds support for 'chat' style protocols - where one side\nsends a 'command', and the other sends a response (examples would be\nthe common internet protocols - smtp, nntp, ftp, etc..).\n\nThe handle_read() method looks at the input stream for the current\n'terminator' (usually '\\r\\n' for single-line responses, '\\r\\n.\\r\\n'\nfor multi-line output), calling self.found_terminator() on its\nreceipt.\n\nfor example:\nSay you build an async nntp client using this class.  At the start\nof the connection, you'll have self.terminator set to '\\r\\n', in\norder to process the single-line greeting.  Just before issuing a\n'LIST' command you'll set it to '\\r\\n.\\r\\n'.  The output of the LIST\ncommand will be accumulated (using your own 'collect_incoming_data'\nmethod) up to the terminator, and then control will be returned to\nyou - by calling your self.found_terminator() method.\n"));
      var1.setline(47);
      PyString.fromInterned("A class supporting chat-style (command/response) protocols.\n\nThis class adds support for 'chat' style protocols - where one side\nsends a 'command', and the other sends a response (examples would be\nthe common internet protocols - smtp, nntp, ftp, etc..).\n\nThe handle_read() method looks at the input stream for the current\n'terminator' (usually '\\r\\n' for single-line responses, '\\r\\n.\\r\\n'\nfor multi-line output), calling self.found_terminator() on its\nreceipt.\n\nfor example:\nSay you build an async nntp client using this class.  At the start\nof the connection, you'll have self.terminator set to '\\r\\n', in\norder to process the single-line greeting.  Just before issuing a\n'LIST' command you'll set it to '\\r\\n.\\r\\n'.  The output of the LIST\ncommand will be accumulated (using your own 'collect_incoming_data'\nmethod) up to the terminator, and then control will be returned to\nyou - by calling your self.found_terminator() method.\n");
      var1.setline(49);
      PyObject var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(50);
      var3 = imp.importOne("asyncore", var1, -1);
      var1.setlocal("asyncore", var3);
      var3 = null;
      var1.setline(51);
      String[] var5 = new String[]{"deque"};
      PyObject[] var6 = imp.importFrom("collections", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("deque", var4);
      var4 = null;
      var1.setline(52);
      var5 = new String[]{"py3kwarning"};
      var6 = imp.importFrom("sys", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("py3kwarning", var4);
      var4 = null;
      var1.setline(53);
      var5 = new String[]{"filterwarnings", "catch_warnings"};
      var6 = imp.importFrom("warnings", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("filterwarnings", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("catch_warnings", var4);
      var4 = null;
      var1.setline(55);
      var6 = new PyObject[]{var1.getname("asyncore").__getattr__("dispatcher")};
      var4 = Py.makeClass("async_chat", var6, async_chat$1);
      var1.setlocal("async_chat", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(254);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("simple_producer", var6, simple_producer$19);
      var1.setlocal("simple_producer", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(270);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("fifo", var6, fifo$22);
      var1.setlocal("fifo", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(310);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, find_prefix_at_end$29, (PyObject)null);
      var1.setlocal("find_prefix_at_end", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject async_chat$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This is an abstract class.  You must derive from this class, and add\n    the two methods collect_incoming_data() and found_terminator()"));
      var1.setline(57);
      PyString.fromInterned("This is an abstract class.  You must derive from this class, and add\n    the two methods collect_incoming_data() and found_terminator()");
      var1.setline(61);
      PyInteger var3 = Py.newInteger(4096);
      var1.setlocal("ac_in_buffer_size", var3);
      var3 = null;
      var1.setline(62);
      var3 = Py.newInteger(4096);
      var1.setlocal("ac_out_buffer_size", var3);
      var3 = null;
      var1.setline(64);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(81);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, collect_incoming_data$3, (PyObject)null);
      var1.setlocal("collect_incoming_data", var5);
      var3 = null;
      var1.setline(84);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _collect_incoming_data$4, (PyObject)null);
      var1.setlocal("_collect_incoming_data", var5);
      var3 = null;
      var1.setline(87);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_data$5, (PyObject)null);
      var1.setlocal("_get_data", var5);
      var3 = null;
      var1.setline(92);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, found_terminator$6, (PyObject)null);
      var1.setlocal("found_terminator", var5);
      var3 = null;
      var1.setline(95);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_terminator$7, PyString.fromInterned("Set the input delimiter.  Can be a fixed string of any length, an integer, or None"));
      var1.setlocal("set_terminator", var5);
      var3 = null;
      var1.setline(99);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_terminator$8, (PyObject)null);
      var1.setlocal("get_terminator", var5);
      var3 = null;
      var1.setline(107);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_read$9, (PyObject)null);
      var1.setlocal("handle_read", var5);
      var3 = null;
      var1.setline(173);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_write$10, (PyObject)null);
      var1.setlocal("handle_write", var5);
      var3 = null;
      var1.setline(176);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, handle_close$11, (PyObject)null);
      var1.setlocal("handle_close", var5);
      var3 = null;
      var1.setline(179);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, push$12, (PyObject)null);
      var1.setlocal("push", var5);
      var3 = null;
      var1.setline(188);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, push_with_producer$13, (PyObject)null);
      var1.setlocal("push_with_producer", var5);
      var3 = null;
      var1.setline(192);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readable$14, PyString.fromInterned("predicate for inclusion in the readable for select()"));
      var1.setlocal("readable", var5);
      var3 = null;
      var1.setline(200);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writable$15, PyString.fromInterned("predicate for inclusion in the writable for select()"));
      var1.setlocal("writable", var5);
      var3 = null;
      var1.setline(204);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close_when_done$16, PyString.fromInterned("automatically close this channel once the outgoing queue is empty"));
      var1.setlocal("close_when_done", var5);
      var3 = null;
      var1.setline(208);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, initiate_send$17, (PyObject)null);
      var1.setlocal("initiate_send", var5);
      var3 = null;
      var1.setline(248);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, discard_buffers$18, (PyObject)null);
      var1.setlocal("discard_buffers", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"ac_in_buffer", var3);
      var3 = null;
      var1.setline(74);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"incoming", var4);
      var3 = null;
      var1.setline(78);
      PyObject var5 = var1.getglobal("deque").__call__(var2);
      var1.getlocal(0).__setattr__("producer_fifo", var5);
      var3 = null;
      var1.setline(79);
      var1.getglobal("asyncore").__getattr__("dispatcher").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject collect_incoming_data$3(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("must be implemented in subclass")));
   }

   public PyObject _collect_incoming_data$4(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      var1.getlocal(0).__getattr__("incoming").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_data$5(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("incoming"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(89);
      var1.getlocal(0).__getattr__("incoming").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setline(90);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject found_terminator$6(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("must be implemented in subclass")));
   }

   public PyObject set_terminator$7(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyString.fromInterned("Set the input delimiter.  Can be a fixed string of any length, an integer, or None");
      var1.setline(97);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("terminator", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_terminator$8(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject var3 = var1.getlocal(0).__getattr__("terminator");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject handle_read$9(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(110);
         var6 = var1.getlocal(0).__getattr__("recv").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer_size"));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("socket").__getattr__("error"))) {
            PyObject var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(112);
            var1.getlocal(0).__getattr__("handle_error").__call__(var2);
            var1.setline(113);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var3;
      }

      var1.setline(115);
      var6 = var1.getlocal(0).__getattr__("ac_in_buffer")._add(var1.getlocal(1));
      var1.getlocal(0).__setattr__("ac_in_buffer", var6);
      var3 = null;

      while(true) {
         var1.setline(122);
         if (!var1.getlocal(0).__getattr__("ac_in_buffer").__nonzero__()) {
            break;
         }

         var1.setline(123);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer"));
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(124);
         var6 = var1.getlocal(0).__getattr__("get_terminator").__call__(var2);
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(125);
         PyString var7;
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(127);
            var1.getlocal(0).__getattr__("collect_incoming_data").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer"));
            var1.setline(128);
            var7 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"ac_in_buffer", var7);
            var3 = null;
         } else {
            var1.setline(129);
            PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("int"));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("long"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(131);
               var6 = var1.getlocal(4);
               var1.setlocal(5, var6);
               var3 = null;
               var1.setline(132);
               var6 = var1.getlocal(3);
               var10000 = var6._lt(var1.getlocal(5));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(133);
                  var1.getlocal(0).__getattr__("collect_incoming_data").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer"));
                  var1.setline(134);
                  var7 = PyString.fromInterned("");
                  var1.getlocal(0).__setattr__((String)"ac_in_buffer", var7);
                  var3 = null;
                  var1.setline(135);
                  var6 = var1.getlocal(0).__getattr__("terminator")._sub(var1.getlocal(3));
                  var1.getlocal(0).__setattr__("terminator", var6);
                  var3 = null;
               } else {
                  var1.setline(137);
                  var1.getlocal(0).__getattr__("collect_incoming_data").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer").__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null));
                  var1.setline(138);
                  var6 = var1.getlocal(0).__getattr__("ac_in_buffer").__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null);
                  var1.getlocal(0).__setattr__("ac_in_buffer", var6);
                  var3 = null;
                  var1.setline(139);
                  PyInteger var8 = Py.newInteger(0);
                  var1.getlocal(0).__setattr__((String)"terminator", var8);
                  var3 = null;
                  var1.setline(140);
                  var1.getlocal(0).__getattr__("found_terminator").__call__(var2);
               }
            } else {
               var1.setline(149);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(150);
               var6 = var1.getlocal(0).__getattr__("ac_in_buffer").__getattr__("find").__call__(var2, var1.getlocal(4));
               var1.setlocal(7, var6);
               var3 = null;
               var1.setline(151);
               var6 = var1.getlocal(7);
               var10000 = var6._ne(Py.newInteger(-1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(153);
                  var6 = var1.getlocal(7);
                  var10000 = var6._gt(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(155);
                     var1.getlocal(0).__getattr__("collect_incoming_data").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer").__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null));
                  }

                  var1.setline(156);
                  var6 = var1.getlocal(0).__getattr__("ac_in_buffer").__getslice__(var1.getlocal(7)._add(var1.getlocal(6)), (PyObject)null, (PyObject)null);
                  var1.getlocal(0).__setattr__("ac_in_buffer", var6);
                  var3 = null;
                  var1.setline(158);
                  var1.getlocal(0).__getattr__("found_terminator").__call__(var2);
               } else {
                  var1.setline(161);
                  var6 = var1.getglobal("find_prefix_at_end").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer"), var1.getlocal(4));
                  var1.setlocal(7, var6);
                  var3 = null;
                  var1.setline(162);
                  if (var1.getlocal(7).__nonzero__()) {
                     var1.setline(163);
                     var6 = var1.getlocal(7);
                     var10000 = var6._ne(var1.getlocal(3));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(165);
                        var1.getlocal(0).__getattr__("collect_incoming_data").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer").__getslice__((PyObject)null, var1.getlocal(7).__neg__(), (PyObject)null));
                        var1.setline(166);
                        var6 = var1.getlocal(0).__getattr__("ac_in_buffer").__getslice__(var1.getlocal(7).__neg__(), (PyObject)null, (PyObject)null);
                        var1.getlocal(0).__setattr__("ac_in_buffer", var6);
                        var3 = null;
                     }
                     break;
                  }

                  var1.setline(170);
                  var1.getlocal(0).__getattr__("collect_incoming_data").__call__(var2, var1.getlocal(0).__getattr__("ac_in_buffer"));
                  var1.setline(171);
                  var7 = PyString.fromInterned("");
                  var1.getlocal(0).__setattr__((String)"ac_in_buffer", var7);
                  var3 = null;
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_write$10(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      var1.getlocal(0).__getattr__("initiate_send").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_close$11(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push$12(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyObject var3 = var1.getlocal(0).__getattr__("ac_out_buffer_size");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(181);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(182);
         var3 = var1.getglobal("xrange").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(182);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(183);
            var1.getlocal(0).__getattr__("producer_fifo").__getattr__("append").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(3), var1.getlocal(3)._add(var1.getlocal(2)), (PyObject)null));
         }
      } else {
         var1.setline(185);
         var1.getlocal(0).__getattr__("producer_fifo").__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      var1.setline(186);
      var1.getlocal(0).__getattr__("initiate_send").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_with_producer$13(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      var1.getlocal(0).__getattr__("producer_fifo").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(190);
      var1.getlocal(0).__getattr__("initiate_send").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readable$14(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyString.fromInterned("predicate for inclusion in the readable for select()");
      var1.setline(198);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writable$15(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyString.fromInterned("predicate for inclusion in the writable for select()");
      var1.setline(202);
      PyObject var10000 = var1.getlocal(0).__getattr__("producer_fifo");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("connected").__not__();
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close_when_done$16(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyString.fromInterned("automatically close this channel once the outgoing queue is empty");
      var1.setline(206);
      var1.getlocal(0).__getattr__("producer_fifo").__getattr__("append").__call__(var2, var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initiate_send$17(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];

      PyObject var10000;
      PyObject var3;
      PyException var8;
      while(true) {
         var1.setline(209);
         var10000 = var1.getlocal(0).__getattr__("producer_fifo");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("connected");
         }

         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(210);
         var3 = var1.getlocal(0).__getattr__("producer_fifo").__getitem__(Py.newInteger(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(212);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(213);
            var1.getlocal(0).__getattr__("producer_fifo").__delitem__((PyObject)Py.newInteger(0));
            var1.setline(214);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(215);
               var1.getlocal(0).__getattr__("handle_close").__call__(var2);
               var1.setline(216);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(219);
         var3 = var1.getlocal(0).__getattr__("ac_out_buffer_size");
         var1.setlocal(2, var3);
         var3 = null;

         PyObject var4;
         try {
            ContextManager var9;
            var4 = (var9 = ContextGuard.getManager(var1.getglobal("catch_warnings").__call__(var2))).__enter__(var2);

            try {
               var1.setline(222);
               if (var1.getglobal("py3kwarning").__nonzero__()) {
                  var1.setline(223);
                  var1.getglobal("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned(".*buffer"), (PyObject)var1.getglobal("DeprecationWarning"));
               }

               var1.setline(224);
               var4 = var1.getglobal("buffer").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2));
               var1.setlocal(3, var4);
               var4 = null;
            } catch (Throwable var6) {
               if (var9.__exit__(var2, Py.setException(var6, var1))) {
                  break;
               }

               throw (Throwable)Py.makeException();
            }

            var9.__exit__(var2, (PyException)null);
            break;
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("TypeError"))) {
               throw var8;
            }

            var1.setline(226);
            var4 = var1.getlocal(1).__getattr__("more").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(227);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(228);
               var1.getlocal(0).__getattr__("producer_fifo").__getattr__("appendleft").__call__(var2, var1.getlocal(3));
            } else {
               var1.setline(230);
               var1.getlocal(0).__getattr__("producer_fifo").__delitem__((PyObject)Py.newInteger(0));
            }
         }
      }

      try {
         var1.setline(235);
         var3 = var1.getlocal(0).__getattr__("send").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var5) {
         var8 = Py.setException(var5, var1);
         if (var8.match(var1.getglobal("socket").__getattr__("error"))) {
            var1.setline(237);
            var1.getlocal(0).__getattr__("handle_error").__call__(var2);
            var1.setline(238);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var8;
      }

      var1.setline(240);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(241);
         var3 = var1.getlocal(4);
         var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(242);
            var3 = var1.getlocal(1).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
            var1.getlocal(0).__getattr__("producer_fifo").__setitem__((PyObject)Py.newInteger(0), var3);
            var3 = null;
         } else {
            var1.setline(244);
            var1.getlocal(0).__getattr__("producer_fifo").__delitem__((PyObject)Py.newInteger(0));
         }
      }

      var1.setline(246);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject discard_buffers$18(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"ac_in_buffer", var3);
      var3 = null;
      var1.setline(251);
      var1.getlocal(0).__getattr__("incoming").__delslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setline(252);
      var1.getlocal(0).__getattr__("producer_fifo").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject simple_producer$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(256);
      PyObject[] var3 = new PyObject[]{Py.newInteger(512)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, more$21, (PyObject)null);
      var1.setlocal("more", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.setline(258);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("buffer_size", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject more$21(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("data"));
      PyObject var10000 = var3._gt(var1.getlocal(0).__getattr__("buffer_size"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(262);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__((PyObject)null, var1.getlocal(0).__getattr__("buffer_size"), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(263);
         var3 = var1.getlocal(0).__getattr__("data").__getslice__(var1.getlocal(0).__getattr__("buffer_size"), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("data", var3);
         var3 = null;
         var1.setline(264);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(266);
         PyObject var4 = var1.getlocal(0).__getattr__("data");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(267);
         PyString var5 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"data", var5);
         var4 = null;
         var1.setline(268);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject fifo$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(271);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$24, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(280);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_empty$25, (PyObject)null);
      var1.setlocal("is_empty", var4);
      var3 = null;
      var1.setline(283);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, first$26, (PyObject)null);
      var1.setlocal("first", var4);
      var3 = null;
      var1.setline(286);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push$27, (PyObject)null);
      var1.setlocal("push", var4);
      var3 = null;
      var1.setline(289);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop$28, (PyObject)null);
      var1.setlocal("pop", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(273);
         var3 = var1.getglobal("deque").__call__(var2);
         var1.getlocal(0).__setattr__("list", var3);
         var3 = null;
      } else {
         var1.setline(275);
         var3 = var1.getglobal("deque").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("list", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$24(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("list"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_empty$25(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyObject var3 = var1.getlocal(0).__getattr__("list").__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject first$26(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyObject var3 = var1.getlocal(0).__getattr__("list").__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject push$27(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      var1.getlocal(0).__getattr__("list").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop$28(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyTuple var3;
      if (var1.getlocal(0).__getattr__("list").__nonzero__()) {
         var1.setline(291);
         var3 = new PyTuple(new PyObject[]{Py.newInteger(1), var1.getlocal(0).__getattr__("list").__getattr__("popleft").__call__(var2)});
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(293);
         var3 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject find_prefix_at_end$29(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(312);
         PyObject var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)).__not__();
         }

         if (!var10000.__nonzero__()) {
            var1.setline(314);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(313);
         var3 = var1.getlocal(2);
         var3 = var3._isub(Py.newInteger(1));
         var1.setlocal(2, var3);
      }
   }

   public asynchat$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      async_chat$1 = Py.newCode(0, var2, var1, "async_chat", 55, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sock", "map"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 64, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      collect_incoming_data$3 = Py.newCode(2, var2, var1, "collect_incoming_data", 81, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      _collect_incoming_data$4 = Py.newCode(2, var2, var1, "_collect_incoming_data", 84, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      _get_data$5 = Py.newCode(1, var2, var1, "_get_data", 87, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      found_terminator$6 = Py.newCode(1, var2, var1, "found_terminator", 92, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "term"};
      set_terminator$7 = Py.newCode(2, var2, var1, "set_terminator", 95, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_terminator$8 = Py.newCode(1, var2, var1, "get_terminator", 99, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "why", "lb", "terminator", "n", "terminator_len", "index"};
      handle_read$9 = Py.newCode(1, var2, var1, "handle_read", 107, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_write$10 = Py.newCode(1, var2, var1, "handle_write", 173, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      handle_close$11 = Py.newCode(1, var2, var1, "handle_close", 176, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "sabs", "i"};
      push$12 = Py.newCode(2, var2, var1, "push", 179, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "producer"};
      push_with_producer$13 = Py.newCode(2, var2, var1, "push_with_producer", 188, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$14 = Py.newCode(1, var2, var1, "readable", 192, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      writable$15 = Py.newCode(1, var2, var1, "writable", 200, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close_when_done$16 = Py.newCode(1, var2, var1, "close_when_done", 204, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "obs", "data", "num_sent"};
      initiate_send$17 = Py.newCode(1, var2, var1, "initiate_send", 208, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      discard_buffers$18 = Py.newCode(1, var2, var1, "discard_buffers", 248, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      simple_producer$19 = Py.newCode(0, var2, var1, "simple_producer", 254, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data", "buffer_size"};
      __init__$20 = Py.newCode(3, var2, var1, "__init__", 256, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      more$21 = Py.newCode(1, var2, var1, "more", 260, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      fifo$22 = Py.newCode(0, var2, var1, "fifo", 270, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "list"};
      __init__$23 = Py.newCode(2, var2, var1, "__init__", 271, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$24 = Py.newCode(1, var2, var1, "__len__", 277, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_empty$25 = Py.newCode(1, var2, var1, "is_empty", 280, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      first$26 = Py.newCode(1, var2, var1, "first", 283, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      push$27 = Py.newCode(2, var2, var1, "push", 286, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pop$28 = Py.newCode(1, var2, var1, "pop", 289, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"haystack", "needle", "l"};
      find_prefix_at_end$29 = Py.newCode(2, var2, var1, "find_prefix_at_end", 310, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new asynchat$py("asynchat$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(asynchat$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.async_chat$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.collect_incoming_data$3(var2, var3);
         case 4:
            return this._collect_incoming_data$4(var2, var3);
         case 5:
            return this._get_data$5(var2, var3);
         case 6:
            return this.found_terminator$6(var2, var3);
         case 7:
            return this.set_terminator$7(var2, var3);
         case 8:
            return this.get_terminator$8(var2, var3);
         case 9:
            return this.handle_read$9(var2, var3);
         case 10:
            return this.handle_write$10(var2, var3);
         case 11:
            return this.handle_close$11(var2, var3);
         case 12:
            return this.push$12(var2, var3);
         case 13:
            return this.push_with_producer$13(var2, var3);
         case 14:
            return this.readable$14(var2, var3);
         case 15:
            return this.writable$15(var2, var3);
         case 16:
            return this.close_when_done$16(var2, var3);
         case 17:
            return this.initiate_send$17(var2, var3);
         case 18:
            return this.discard_buffers$18(var2, var3);
         case 19:
            return this.simple_producer$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.more$21(var2, var3);
         case 22:
            return this.fifo$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.__len__$24(var2, var3);
         case 25:
            return this.is_empty$25(var2, var3);
         case 26:
            return this.first$26(var2, var3);
         case 27:
            return this.push$27(var2, var3);
         case 28:
            return this.pop$28(var2, var3);
         case 29:
            return this.find_prefix_at_end$29(var2, var3);
         default:
            return null;
      }
   }
}
