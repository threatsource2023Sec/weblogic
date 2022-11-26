package email;

import java.util.Arrays;
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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("email/feedparser.py")
public class feedparser$py extends PyFunctionTable implements PyRunnable {
   static feedparser$py self;
   static final PyCode f$0;
   static final PyCode BufferedSubFile$1;
   static final PyCode __init__$2;
   static final PyCode push_eof_matcher$3;
   static final PyCode pop_eof_matcher$4;
   static final PyCode close$5;
   static final PyCode readline$6;
   static final PyCode unreadline$7;
   static final PyCode push$8;
   static final PyCode pushlines$9;
   static final PyCode is_closed$10;
   static final PyCode __iter__$11;
   static final PyCode next$12;
   static final PyCode FeedParser$13;
   static final PyCode __init__$14;
   static final PyCode _set_headersonly$15;
   static final PyCode feed$16;
   static final PyCode _call_parse$17;
   static final PyCode close$18;
   static final PyCode _new_message$19;
   static final PyCode _pop_message$20;
   static final PyCode _parsegen$21;
   static final PyCode _parse_headers$22;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("FeedParser - An email feed parser.\n\nThe feed parser implements an interface for incrementally parsing an email\nmessage, line by line.  This has advantages for certain applications, such as\nthose reading email messages off a socket.\n\nFeedParser.feed() is the primary interface for pushing new data into the\nparser.  It returns when there's nothing more it can do with the available\ndata.  When you have no more data to push into the parser, call .close().\nThis completes the parsing and returns the root message object.\n\nThe other advantage of this parser is that it will never raise a parsing\nexception.  Instead, when it finds something unexpected, it adds a 'defect' to\nthe current message.  Defects are just instances that live on the message\nobject's .defects attribute.\n"));
      var1.setline(20);
      PyString.fromInterned("FeedParser - An email feed parser.\n\nThe feed parser implements an interface for incrementally parsing an email\nmessage, line by line.  This has advantages for certain applications, such as\nthose reading email messages off a socket.\n\nFeedParser.feed() is the primary interface for pushing new data into the\nparser.  It returns when there's nothing more it can do with the available\ndata.  When you have no more data to push into the parser, call .close().\nThis completes the parsing and returns the root message object.\n\nThe other advantage of this parser is that it will never raise a parsing\nexception.  Instead, when it finds something unexpected, it adds a 'defect' to\nthe current message.  Defects are just instances that live on the message\nobject's .defects attribute.\n");
      var1.setline(22);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("FeedParser")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(24);
      PyObject var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(26);
      String[] var6 = new String[]{"errors"};
      PyObject[] var7 = imp.importFrom("email", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("errors", var4);
      var4 = null;
      var1.setline(27);
      var6 = new String[]{"message"};
      var7 = imp.importFrom("email", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("message", var4);
      var4 = null;
      var1.setline(29);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n|\r|\n"));
      var1.setlocal("NLCRE", var5);
      var3 = null;
      var1.setline(30);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\r\n|\r|\n)"));
      var1.setlocal("NLCRE_bol", var5);
      var3 = null;
      var1.setline(31);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\r\n|\r|\n)\\Z"));
      var1.setlocal("NLCRE_eol", var5);
      var3 = null;
      var1.setline(32);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\r\n|\r|\n)"));
      var1.setlocal("NLCRE_crack", var5);
      var3 = null;
      var1.setline(35);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(From |[\\041-\\071\\073-\\176]{1,}:|[\\t ])"));
      var1.setlocal("headerRE", var5);
      var3 = null;
      var1.setline(36);
      PyString var8 = PyString.fromInterned("");
      var1.setlocal("EMPTYSTRING", var8);
      var3 = null;
      var1.setline(37);
      var8 = PyString.fromInterned("\n");
      var1.setlocal("NL", var8);
      var3 = null;
      var1.setline(39);
      var5 = var1.getname("object").__call__(var2);
      var1.setlocal("NeedMoreData", var5);
      var3 = null;
      var1.setline(43);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BufferedSubFile", var7, BufferedSubFile$1);
      var1.setlocal("BufferedSubFile", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(137);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("FeedParser", var7, FeedParser$13);
      var1.setlocal("FeedParser", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BufferedSubFile$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A file-ish object that can have new data loaded into it.\n\n    You can also push and pop line-matching predicates onto a stack.  When the\n    current predicate matches the current line, a false EOF response\n    (i.e. empty string) is returned instead.  This lets the parser adhere to a\n    simple abstraction -- it parses until EOF closes the current message.\n    "));
      var1.setline(50);
      PyString.fromInterned("A file-ish object that can have new data loaded into it.\n\n    You can also push and pop line-matching predicates onto a stack.  When the\n    current predicate matches the current line, a false EOF response\n    (i.e. empty string) is returned instead.  This lets the parser adhere to a\n    simple abstraction -- it parses until EOF closes the current message.\n    ");
      var1.setline(51);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_eof_matcher$3, (PyObject)null);
      var1.setlocal("push_eof_matcher", var4);
      var3 = null;
      var1.setline(64);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_eof_matcher$4, (PyObject)null);
      var1.setlocal("pop_eof_matcher", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$5, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$6, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(91);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unreadline$7, (PyObject)null);
      var1.setlocal("unreadline", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push$8, PyString.fromInterned("Push some new data into this object."));
      var1.setlocal("push", var4);
      var3 = null;
      var1.setline(119);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pushlines$9, (PyObject)null);
      var1.setlocal("pushlines", var4);
      var3 = null;
      var1.setline(123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_closed$10, (PyObject)null);
      var1.setlocal("is_closed", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$11, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$12, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_partial", var3);
      var3 = null;
      var1.setline(55);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_lines", var4);
      var3 = null;
      var1.setline(57);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_eofstack", var4);
      var3 = null;
      var1.setline(59);
      PyObject var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_closed", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_eof_matcher$3(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      var1.getlocal(0).__getattr__("_eofstack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_eof_matcher$4(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getlocal(0).__getattr__("_eofstack").__getattr__("pop").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$5(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      var1.getlocal(0).__getattr__("_lines").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_partial"));
      var1.setline(70);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_partial", var3);
      var3 = null;
      var1.setline(71);
      PyObject var4 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_closed", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$6(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString var3;
      PyObject var6;
      if (var1.getlocal(0).__getattr__("_lines").__not__().__nonzero__()) {
         var1.setline(75);
         if (var1.getlocal(0).__getattr__("_closed").__nonzero__()) {
            var1.setline(76);
            var3 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(77);
            var6 = var1.getglobal("NeedMoreData");
            var1.f_lasti = -1;
            return var6;
         }
      } else {
         var1.setline(80);
         PyObject var4 = var1.getlocal(0).__getattr__("_lines").__getattr__("pop").__call__(var2);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(84);
         var4 = var1.getlocal(0).__getattr__("_eofstack").__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1)).__iter__();

         do {
            var1.setline(84);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(89);
               var6 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var6;
            }

            var1.setlocal(2, var5);
            var1.setline(85);
         } while(!var1.getlocal(2).__call__(var2, var1.getlocal(1)).__nonzero__());

         var1.setline(87);
         var1.getlocal(0).__getattr__("_lines").__getattr__("append").__call__(var2, var1.getlocal(1));
         var1.setline(88);
         var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unreadline$7(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      if (var1.getglobal("__debug__").__nonzero__()) {
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._isnot(var1.getglobal("NeedMoreData"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(94);
      var1.getlocal(0).__getattr__("_lines").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push$8(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("Push some new data into this object.");
      var1.setline(99);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_partial")._add(var1.getlocal(1)), PyString.fromInterned("")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("_partial", var5);
      var5 = null;
      var3 = null;
      var1.setline(101);
      PyObject var6 = var1.getglobal("NLCRE_crack").__getattr__("split").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(106);
      var6 = var1.getlocal(2).__getattr__("pop").__call__(var2);
      var1.getlocal(0).__setattr__("_partial", var6);
      var3 = null;
      var1.setline(109);
      PyObject var10000 = var1.getlocal(0).__getattr__("_partial").__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__getitem__(Py.newInteger(-1)).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r"));
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(110);
         var6 = var1.getlocal(2).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(-2))._add(var1.getlocal(2).__getattr__("pop").__call__(var2));
         var1.getlocal(0).__setattr__("_partial", var6);
         var3 = null;
      }

      var1.setline(114);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(115);
      var6 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2))._floordiv(Py.newInteger(2))).__iter__();

      while(true) {
         var1.setline(115);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.setline(117);
            var1.getlocal(0).__getattr__("pushlines").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var7);
         var1.setline(116);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(4)._mul(Py.newInteger(2)))._add(var1.getlocal(2).__getitem__(var1.getlocal(4)._mul(Py.newInteger(2))._add(Py.newInteger(1)))));
      }
   }

   public PyObject pushlines$9(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1));
      var1.getlocal(0).__getattr__("_lines").__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_closed$10(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getlocal(0).__getattr__("_closed");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$11(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$12(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         throw Py.makeException(var1.getglobal("StopIteration"));
      } else {
         var1.setline(133);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject FeedParser$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A feed-style parser of email."));
      var1.setline(138);
      PyString.fromInterned("A feed-style parser of email.");
      var1.setline(140);
      PyObject[] var3 = new PyObject[]{var1.getname("message").__getattr__("Message")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, PyString.fromInterned("_factory is called with no arguments to create a new message obj"));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(151);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _set_headersonly$15, (PyObject)null);
      var1.setlocal("_set_headersonly", var4);
      var3 = null;
      var1.setline(154);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, feed$16, PyString.fromInterned("Push more data into the parser."));
      var1.setlocal("feed", var4);
      var3 = null;
      var1.setline(159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _call_parse$17, (PyObject)null);
      var1.setlocal("_call_parse", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$18, PyString.fromInterned("Parse all remaining data and return the root message object."));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _new_message$19, (PyObject)null);
      var1.setlocal("_new_message", var4);
      var3 = null;
      var1.setline(187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _pop_message$20, (PyObject)null);
      var1.setlocal("_pop_message", var4);
      var3 = null;
      var1.setline(195);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parsegen$21, (PyObject)null);
      var1.setlocal("_parsegen", var4);
      var3 = null;
      var1.setline(431);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_headers$22, (PyObject)null);
      var1.setlocal("_parse_headers", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("_factory is called with no arguments to create a new message obj");
      var1.setline(142);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_factory", var3);
      var3 = null;
      var1.setline(143);
      var3 = var1.getglobal("BufferedSubFile").__call__(var2);
      var1.getlocal(0).__setattr__("_input", var3);
      var3 = null;
      var1.setline(144);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_msgstack", var4);
      var3 = null;
      var1.setline(145);
      var3 = var1.getlocal(0).__getattr__("_parsegen").__call__(var2).__getattr__("next");
      var1.getlocal(0).__setattr__("_parse", var3);
      var3 = null;
      var1.setline(146);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_cur", var3);
      var3 = null;
      var1.setline(147);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_last", var3);
      var3 = null;
      var1.setline(148);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_headersonly", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_headersonly$15(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_headersonly", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject feed$16(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyString.fromInterned("Push more data into the parser.");
      var1.setline(156);
      var1.getlocal(0).__getattr__("_input").__getattr__("push").__call__(var2, var1.getlocal(1));
      var1.setline(157);
      var1.getlocal(0).__getattr__("_call_parse").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _call_parse$17(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(161);
         var1.getlocal(0).__getattr__("_parse").__call__(var2);
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("StopIteration"))) {
            throw var3;
         }

         var1.setline(163);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$18(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyString.fromInterned("Parse all remaining data and return the root message object.");
      var1.setline(167);
      var1.getlocal(0).__getattr__("_input").__getattr__("close").__call__(var2);
      var1.setline(168);
      var1.getlocal(0).__getattr__("_call_parse").__call__(var2);
      var1.setline(169);
      PyObject var3 = var1.getlocal(0).__getattr__("_pop_message").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(170);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("_msgstack").__not__().__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(172);
         var3 = var1.getlocal(1).__getattr__("get_content_maintype").__call__(var2);
         var10000 = var3._eq(PyString.fromInterned("multipart"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("is_multipart").__call__(var2).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(174);
            var1.getlocal(1).__getattr__("defects").__getattr__("append").__call__(var2, var1.getglobal("errors").__getattr__("MultipartInvariantViolationDefect").__call__(var2));
         }

         var1.setline(175);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _new_message$19(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyObject var3 = var1.getlocal(0).__getattr__("_factory").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(179);
      PyObject var10000 = var1.getlocal(0).__getattr__("_cur");
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_cur").__getattr__("get_content_type").__call__(var2);
         var10000 = var3._eq(PyString.fromInterned("multipart/digest"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(180);
         var1.getlocal(1).__getattr__("set_default_type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("message/rfc822"));
      }

      var1.setline(181);
      if (var1.getlocal(0).__getattr__("_msgstack").__nonzero__()) {
         var1.setline(182);
         var1.getlocal(0).__getattr__("_msgstack").__getitem__(Py.newInteger(-1)).__getattr__("attach").__call__(var2, var1.getlocal(1));
      }

      var1.setline(183);
      var1.getlocal(0).__getattr__("_msgstack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(184);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_cur", var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_last", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _pop_message$20(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = var1.getlocal(0).__getattr__("_msgstack").__getattr__("pop").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(189);
      if (var1.getlocal(0).__getattr__("_msgstack").__nonzero__()) {
         var1.setline(190);
         var3 = var1.getlocal(0).__getattr__("_msgstack").__getitem__(Py.newInteger(-1));
         var1.getlocal(0).__setattr__("_cur", var3);
         var3 = null;
      } else {
         var1.setline(192);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_cur", var3);
         var3 = null;
      }

      var1.setline(193);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _parsegen$21(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject _parse_headers$22(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(434);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(435);
      PyObject var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(435);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(482);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(484);
               var9 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(3)).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
               var1.getlocal(0).__getattr__("_cur").__setitem__(var1.getlocal(2), var9);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(437);
         PyObject var10 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         PyObject var10000 = var10._in(PyString.fromInterned(" \t"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(438);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(442);
               var10 = var1.getglobal("errors").__getattr__("FirstHeaderLineIsContinuationDefect").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var10);
               var5 = null;
               var1.setline(443);
               var1.getlocal(0).__getattr__("_cur").__getattr__("defects").__getattr__("append").__call__(var2, var1.getlocal(6));
            } else {
               var1.setline(445);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         } else {
            var1.setline(447);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(449);
               var10 = var1.getglobal("EMPTYSTRING").__getattr__("join").__call__(var2, var1.getlocal(3)).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
               var1.setlocal(7, var10);
               var5 = null;
               var1.setline(450);
               var10 = var1.getlocal(7);
               var1.getlocal(0).__getattr__("_cur").__setitem__(var1.getlocal(2), var10);
               var5 = null;
               var1.setline(451);
               PyTuple var12 = new PyTuple(new PyObject[]{PyString.fromInterned(""), new PyList(Py.EmptyObjects)});
               PyObject[] var11 = Py.unpackSequence(var12, 2);
               PyObject var7 = var11[0];
               var1.setlocal(2, var7);
               var7 = null;
               var7 = var11[1];
               var1.setlocal(3, var7);
               var7 = null;
               var5 = null;
            }

            var1.setline(453);
            if (var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From ")).__nonzero__()) {
               var1.setline(454);
               var10 = var1.getlocal(4);
               var10000 = var10._eq(Py.newInteger(0));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(456);
                  var10 = var1.getglobal("NLCRE_eol").__getattr__("search").__call__(var2, var1.getlocal(5));
                  var1.setlocal(8, var10);
                  var5 = null;
                  var1.setline(457);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(458);
                     var10 = var1.getlocal(5).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))).__neg__(), (PyObject)null);
                     var1.setlocal(5, var10);
                     var5 = null;
                  }

                  var1.setline(459);
                  var1.getlocal(0).__getattr__("_cur").__getattr__("set_unixfrom").__call__(var2, var1.getlocal(5));
               } else {
                  var1.setline(461);
                  var10 = var1.getlocal(4);
                  var10000 = var10._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1)));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(465);
                     var1.getlocal(0).__getattr__("_input").__getattr__("unreadline").__call__(var2, var1.getlocal(5));
                     var1.setline(466);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(470);
                  var10 = var1.getglobal("errors").__getattr__("MisplacedEnvelopeHeaderDefect").__call__(var2, var1.getlocal(5));
                  var1.setlocal(6, var10);
                  var5 = null;
                  var1.setline(471);
                  var1.getlocal(0).__getattr__("_cur").__getattr__("defects").__getattr__("append").__call__(var2, var1.getlocal(6));
               }
            } else {
               var1.setline(474);
               var10 = var1.getlocal(5).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
               var1.setlocal(9, var10);
               var5 = null;
               var1.setline(475);
               var10 = var1.getlocal(9);
               var10000 = var10._lt(Py.newInteger(0));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(476);
                  var10 = var1.getglobal("errors").__getattr__("MalformedHeaderDefect").__call__(var2, var1.getlocal(5));
                  var1.setlocal(6, var10);
                  var5 = null;
                  var1.setline(477);
                  var1.getlocal(0).__getattr__("_cur").__getattr__("defects").__getattr__("append").__call__(var2, var1.getlocal(6));
               } else {
                  var1.setline(479);
                  var10 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(9), (PyObject)null);
                  var1.setlocal(2, var10);
                  var5 = null;
                  var1.setline(480);
                  PyList var13 = new PyList(new PyObject[]{var1.getlocal(5).__getslice__(var1.getlocal(9)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("lstrip").__call__(var2)});
                  var1.setlocal(3, var13);
                  var5 = null;
               }
            }
         }
      }
   }

   public feedparser$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BufferedSubFile$1 = Py.newCode(0, var2, var1, "BufferedSubFile", 43, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 51, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pred"};
      push_eof_matcher$3 = Py.newCode(2, var2, var1, "push_eof_matcher", 61, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pop_eof_matcher$4 = Py.newCode(1, var2, var1, "pop_eof_matcher", 64, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$5 = Py.newCode(1, var2, var1, "close", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "ateof"};
      readline$6 = Py.newCode(1, var2, var1, "readline", 73, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      unreadline$7 = Py.newCode(2, var2, var1, "unreadline", 91, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "parts", "lines", "i"};
      push$8 = Py.newCode(2, var2, var1, "push", 96, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines"};
      pushlines$9 = Py.newCode(2, var2, var1, "pushlines", 119, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_closed$10 = Py.newCode(1, var2, var1, "is_closed", 123, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$11 = Py.newCode(1, var2, var1, "__iter__", 126, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$12 = Py.newCode(1, var2, var1, "next", 129, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FeedParser$13 = Py.newCode(0, var2, var1, "FeedParser", 137, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "_factory"};
      __init__$14 = Py.newCode(2, var2, var1, "__init__", 140, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _set_headersonly$15 = Py.newCode(1, var2, var1, "_set_headersonly", 151, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      feed$16 = Py.newCode(2, var2, var1, "feed", 154, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _call_parse$17 = Py.newCode(1, var2, var1, "_call_parse", 159, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "root"};
      close$18 = Py.newCode(1, var2, var1, "close", 165, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      _new_message$19 = Py.newCode(1, var2, var1, "_new_message", 177, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "retval"};
      _pop_message$20 = Py.newCode(1, var2, var1, "_pop_message", 187, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "headers", "line", "lines", "retval", "msg", "boundary", "separator", "boundaryre", "capturing_preamble", "preamble", "linesep", "mo", "lastline", "eolmo", "epilogue", "end", "payload", "firstline", "bolmo"};
      _parsegen$21 = Py.newCode(1, var2, var1, "_parsegen", 195, false, false, self, 21, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "lines", "lastheader", "lastvalue", "lineno", "line", "defect", "lhdr", "mo", "i"};
      _parse_headers$22 = Py.newCode(2, var2, var1, "_parse_headers", 431, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new feedparser$py("email/feedparser$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(feedparser$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BufferedSubFile$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.push_eof_matcher$3(var2, var3);
         case 4:
            return this.pop_eof_matcher$4(var2, var3);
         case 5:
            return this.close$5(var2, var3);
         case 6:
            return this.readline$6(var2, var3);
         case 7:
            return this.unreadline$7(var2, var3);
         case 8:
            return this.push$8(var2, var3);
         case 9:
            return this.pushlines$9(var2, var3);
         case 10:
            return this.is_closed$10(var2, var3);
         case 11:
            return this.__iter__$11(var2, var3);
         case 12:
            return this.next$12(var2, var3);
         case 13:
            return this.FeedParser$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this._set_headersonly$15(var2, var3);
         case 16:
            return this.feed$16(var2, var3);
         case 17:
            return this._call_parse$17(var2, var3);
         case 18:
            return this.close$18(var2, var3);
         case 19:
            return this._new_message$19(var2, var3);
         case 20:
            return this._pop_message$20(var2, var3);
         case 21:
            return this._parsegen$21(var2, var3);
         case 22:
            return this._parse_headers$22(var2, var3);
         default:
            return null;
      }
   }
}
