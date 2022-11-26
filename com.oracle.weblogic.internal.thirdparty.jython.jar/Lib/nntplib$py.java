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
@Filename("nntplib.py")
public class nntplib$py extends PyFunctionTable implements PyRunnable {
   static nntplib$py self;
   static final PyCode f$0;
   static final PyCode NNTPError$1;
   static final PyCode __init__$2;
   static final PyCode NNTPReplyError$3;
   static final PyCode NNTPTemporaryError$4;
   static final PyCode NNTPPermanentError$5;
   static final PyCode NNTPProtocolError$6;
   static final PyCode NNTPDataError$7;
   static final PyCode NNTP$8;
   static final PyCode __init__$9;
   static final PyCode getwelcome$10;
   static final PyCode set_debuglevel$11;
   static final PyCode putline$12;
   static final PyCode putcmd$13;
   static final PyCode getline$14;
   static final PyCode getresp$15;
   static final PyCode getlongresp$16;
   static final PyCode shortcmd$17;
   static final PyCode longcmd$18;
   static final PyCode newgroups$19;
   static final PyCode newnews$20;
   static final PyCode list$21;
   static final PyCode description$22;
   static final PyCode descriptions$23;
   static final PyCode group$24;
   static final PyCode help$25;
   static final PyCode statparse$26;
   static final PyCode statcmd$27;
   static final PyCode stat$28;
   static final PyCode next$29;
   static final PyCode last$30;
   static final PyCode artcmd$31;
   static final PyCode head$32;
   static final PyCode body$33;
   static final PyCode article$34;
   static final PyCode slave$35;
   static final PyCode xhdr$36;
   static final PyCode xover$37;
   static final PyCode xgtitle$38;
   static final PyCode xpath$39;
   static final PyCode date$40;
   static final PyCode post$41;
   static final PyCode ihave$42;
   static final PyCode quit$43;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("An NNTP client class based on RFC 977: Network News Transfer Protocol.\n\nExample:\n\n>>> from nntplib import NNTP\n>>> s = NNTP('news')\n>>> resp, count, first, last, name = s.group('comp.lang.python')\n>>> print 'Group', name, 'has', count, 'articles, range', first, 'to', last\nGroup comp.lang.python has 51 articles, range 5770 to 5821\n>>> resp, subs = s.xhdr('subject', first + '-' + last)\n>>> resp = s.quit()\n>>>\n\nHere 'resp' is the server response line.\nError responses are turned into exceptions.\n\nTo post an article from a file:\n>>> f = open(filename, 'r') # file containing article, including header\n>>> resp = s.post(f)\n>>>\n\nFor descriptions of all methods, read the comments in the code below.\nNote that all arguments and return values representing article numbers\nare strings, not numbers, since they are rarely used for calculations.\n"));
      var1.setline(25);
      PyString.fromInterned("An NNTP client class based on RFC 977: Network News Transfer Protocol.\n\nExample:\n\n>>> from nntplib import NNTP\n>>> s = NNTP('news')\n>>> resp, count, first, last, name = s.group('comp.lang.python')\n>>> print 'Group', name, 'has', count, 'articles, range', first, 'to', last\nGroup comp.lang.python has 51 articles, range 5770 to 5821\n>>> resp, subs = s.xhdr('subject', first + '-' + last)\n>>> resp = s.quit()\n>>>\n\nHere 'resp' is the server response line.\nError responses are turned into exceptions.\n\nTo post an article from a file:\n>>> f = open(filename, 'r') # file containing article, including header\n>>> resp = s.post(f)\n>>>\n\nFor descriptions of all methods, read the comments in the code below.\nNote that all arguments and return values representing article numbers\nare strings, not numbers, since they are rarely used for calculations.\n");
      var1.setline(32);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(33);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(35);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("NNTP"), PyString.fromInterned("NNTPReplyError"), PyString.fromInterned("NNTPTemporaryError"), PyString.fromInterned("NNTPPermanentError"), PyString.fromInterned("NNTPProtocolError"), PyString.fromInterned("NNTPDataError"), PyString.fromInterned("error_reply"), PyString.fromInterned("error_temp"), PyString.fromInterned("error_perm"), PyString.fromInterned("error_proto"), PyString.fromInterned("error_data")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(44);
      PyInteger var7 = Py.newInteger(2048);
      var1.setlocal("_MAXLINE", var7);
      var3 = null;
      var1.setline(48);
      PyObject[] var8 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("NNTPError", var8, NNTPError$1);
      var1.setlocal("NNTPError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(57);
      var8 = new PyObject[]{var1.getname("NNTPError")};
      var4 = Py.makeClass("NNTPReplyError", var8, NNTPReplyError$3);
      var1.setlocal("NNTPReplyError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(61);
      var8 = new PyObject[]{var1.getname("NNTPError")};
      var4 = Py.makeClass("NNTPTemporaryError", var8, NNTPTemporaryError$4);
      var1.setlocal("NNTPTemporaryError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(65);
      var8 = new PyObject[]{var1.getname("NNTPError")};
      var4 = Py.makeClass("NNTPPermanentError", var8, NNTPPermanentError$5);
      var1.setlocal("NNTPPermanentError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(69);
      var8 = new PyObject[]{var1.getname("NNTPError")};
      var4 = Py.makeClass("NNTPProtocolError", var8, NNTPProtocolError$6);
      var1.setlocal("NNTPProtocolError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(73);
      var8 = new PyObject[]{var1.getname("NNTPError")};
      var4 = Py.makeClass("NNTPDataError", var8, NNTPDataError$7);
      var1.setlocal("NNTPDataError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(78);
      var3 = var1.getname("NNTPReplyError");
      var1.setlocal("error_reply", var3);
      var3 = null;
      var1.setline(79);
      var3 = var1.getname("NNTPTemporaryError");
      var1.setlocal("error_temp", var3);
      var3 = null;
      var1.setline(80);
      var3 = var1.getname("NNTPPermanentError");
      var1.setlocal("error_perm", var3);
      var3 = null;
      var1.setline(81);
      var3 = var1.getname("NNTPProtocolError");
      var1.setlocal("error_proto", var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getname("NNTPDataError");
      var1.setlocal("error_data", var3);
      var3 = null;
      var1.setline(87);
      var7 = Py.newInteger(119);
      var1.setlocal("NNTP_PORT", var7);
      var3 = null;
      var1.setline(91);
      var6 = new PyList(new PyObject[]{PyString.fromInterned("100"), PyString.fromInterned("215"), PyString.fromInterned("220"), PyString.fromInterned("221"), PyString.fromInterned("222"), PyString.fromInterned("224"), PyString.fromInterned("230"), PyString.fromInterned("231"), PyString.fromInterned("282")});
      var1.setlocal("LONGRESP", var6);
      var3 = null;
      var1.setline(95);
      PyString var11 = PyString.fromInterned("\r\n");
      var1.setlocal("CRLF", var11);
      var3 = null;
      var1.setline(100);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("NNTP", var8, NNTP$8);
      var1.setlocal("NNTP", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(620);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(621);
         var3 = imp.importOne("os", var1, -1);
         var1.setlocal("os", var3);
         var3 = null;
         var1.setline(622);
         Object var13 = PyString.fromInterned("news");
         if (((PyObject)var13).__nonzero__()) {
            var13 = var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("NNTPSERVER"));
         }

         Object var12 = var13;
         var1.setlocal("newshost", (PyObject)var12);
         var3 = null;
         var1.setline(623);
         var3 = var1.getname("newshost").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(624);
            var11 = PyString.fromInterned("readermode");
            var1.setlocal("mode", var11);
            var3 = null;
         } else {
            var1.setline(626);
            var3 = var1.getname("None");
            var1.setlocal("mode", var3);
            var3 = null;
         }

         var1.setline(627);
         var10000 = var1.getname("NNTP");
         var8 = new PyObject[]{var1.getname("newshost"), var1.getname("mode")};
         String[] var9 = new String[]{"readermode"};
         var10000 = var10000.__call__(var2, var8, var9);
         var3 = null;
         var3 = var10000;
         var1.setlocal("s", var3);
         var3 = null;
         var1.setline(628);
         var3 = var1.getname("s").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("comp.lang.python"));
         PyObject[] var10 = Py.unpackSequence(var3, 5);
         PyObject var5 = var10[0];
         var1.setlocal("resp", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("count", var5);
         var5 = null;
         var5 = var10[2];
         var1.setlocal("first", var5);
         var5 = null;
         var5 = var10[3];
         var1.setlocal("last", var5);
         var5 = null;
         var5 = var10[4];
         var1.setlocal("name", var5);
         var5 = null;
         var3 = null;
         var1.setline(629);
         Py.println(var1.getname("resp"));
         var1.setline(630);
         Py.printComma(PyString.fromInterned("Group"));
         Py.printComma(var1.getname("name"));
         Py.printComma(PyString.fromInterned("has"));
         Py.printComma(var1.getname("count"));
         Py.printComma(PyString.fromInterned("articles, range"));
         Py.printComma(var1.getname("first"));
         Py.printComma(PyString.fromInterned("to"));
         Py.println(var1.getname("last"));
         var1.setline(631);
         var3 = var1.getname("s").__getattr__("xhdr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("subject"), (PyObject)var1.getname("first")._add(PyString.fromInterned("-"))._add(var1.getname("last")));
         var10 = Py.unpackSequence(var3, 2);
         var5 = var10[0];
         var1.setlocal("resp", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("subs", var5);
         var5 = null;
         var3 = null;
         var1.setline(632);
         Py.println(var1.getname("resp"));
         var1.setline(633);
         var3 = var1.getname("subs").__iter__();

         while(true) {
            var1.setline(633);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(635);
               var3 = var1.getname("s").__getattr__("quit").__call__(var2);
               var1.setlocal("resp", var3);
               var3 = null;
               var1.setline(636);
               Py.println(var1.getname("resp"));
               break;
            }

            var1.setlocal("item", var4);
            var1.setline(634);
            Py.println(PyString.fromInterned("%7s %s")._mod(var1.getname("item")));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NNTPError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for all nntplib exceptions"));
      var1.setline(49);
      PyString.fromInterned("Base class for all nntplib exceptions");
      var1.setline(50);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var10000 = var1.getglobal("Exception").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;

      try {
         var1.setline(53);
         PyObject var7 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.getlocal(0).__setattr__("response", var7);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("IndexError"))) {
            throw var6;
         }

         var1.setline(55);
         PyString var8 = PyString.fromInterned("No response given");
         var1.getlocal(0).__setattr__((String)"response", var8);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NNTPReplyError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Unexpected [123]xx reply"));
      var1.setline(58);
      PyString.fromInterned("Unexpected [123]xx reply");
      var1.setline(59);
      return var1.getf_locals();
   }

   public PyObject NNTPTemporaryError$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("4xx errors"));
      var1.setline(62);
      PyString.fromInterned("4xx errors");
      var1.setline(63);
      return var1.getf_locals();
   }

   public PyObject NNTPPermanentError$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("5xx errors"));
      var1.setline(66);
      PyString.fromInterned("5xx errors");
      var1.setline(67);
      return var1.getf_locals();
   }

   public PyObject NNTPProtocolError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Response does not begin with [1-5]"));
      var1.setline(70);
      PyString.fromInterned("Response does not begin with [1-5]");
      var1.setline(71);
      return var1.getf_locals();
   }

   public PyObject NNTPDataError$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Error in response data"));
      var1.setline(74);
      PyString.fromInterned("Error in response data");
      var1.setline(75);
      return var1.getf_locals();
   }

   public PyObject NNTP$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(101);
      PyObject[] var3 = new PyObject[]{var1.getname("NNTP_PORT"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, PyString.fromInterned("Initialize an instance.  Arguments:\n        - host: hostname to connect to\n        - port: port to connect to (default the standard NNTP port)\n        - user: username to authenticate with\n        - password: password to use with username\n        - readermode: if true, send 'mode reader' command after\n                      connecting.\n\n        readermode is sometimes necessary if you are connecting to an\n        NNTP server on the local machine and intend to call\n        reader-specific commands, such as `group'.  If you get\n        unexpected NNTPPermanentErrors, you might need to set\n        readermode.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getwelcome$10, PyString.fromInterned("Get the welcome message from the server\n        (this is read and squirreled away by __init__()).\n        If the response code is 200, posting is allowed;\n        if it 201, posting is not allowed."));
      var1.setlocal("getwelcome", var4);
      var3 = null;
      var1.setline(187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_debuglevel$11, PyString.fromInterned("Set the debugging level.  Argument 'level' means:\n        0: no debugging output (default)\n        1: print commands and responses but not body text etc.\n        2: also print raw lines read and sent before stripping CR/LF"));
      var1.setlocal("set_debuglevel", var4);
      var3 = null;
      var1.setline(194);
      PyObject var5 = var1.getname("set_debuglevel");
      var1.setlocal("debug", var5);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, putline$12, PyString.fromInterned("Internal: send one line to the server, appending CRLF."));
      var1.setlocal("putline", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, putcmd$13, PyString.fromInterned("Internal: send one command to the server (through putline())."));
      var1.setlocal("putcmd", var4);
      var3 = null;
      var1.setline(207);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getline$14, PyString.fromInterned("Internal: return one line from the server, stripping CRLF.\n        Raise EOFError if the connection is closed."));
      var1.setlocal("getline", var4);
      var3 = null;
      var1.setline(220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getresp$15, PyString.fromInterned("Internal: get a response from the server.\n        Raise various errors if the response indicates an error."));
      var1.setlocal("getresp", var4);
      var3 = null;
      var1.setline(234);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getlongresp$16, PyString.fromInterned("Internal: get a response plus following text from the server.\n        Raise various errors if the response indicates an error."));
      var1.setlocal("getlongresp", var4);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shortcmd$17, PyString.fromInterned("Internal: send a command and get the response."));
      var1.setlocal("shortcmd", var4);
      var3 = null;
      var1.setline(270);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, longcmd$18, PyString.fromInterned("Internal: send a command and get the response plus following text."));
      var1.setlocal("longcmd", var4);
      var3 = null;
      var1.setline(275);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, newgroups$19, PyString.fromInterned("Process a NEWGROUPS command.  Arguments:\n        - date: string 'yymmdd' indicating the date\n        - time: string 'hhmmss' indicating the time\n        Return:\n        - resp: server response if successful\n        - list: list of newsgroup names"));
      var1.setlocal("newgroups", var4);
      var3 = null;
      var1.setline(285);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, newnews$20, PyString.fromInterned("Process a NEWNEWS command.  Arguments:\n        - group: group name or '*'\n        - date: string 'yymmdd' indicating the date\n        - time: string 'hhmmss' indicating the time\n        Return:\n        - resp: server response if successful\n        - list: list of message ids"));
      var1.setlocal("newnews", var4);
      var3 = null;
      var1.setline(297);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, list$21, PyString.fromInterned("Process a LIST command.  Return:\n        - resp: server response if successful\n        - list: list of (group, last, first, flag) (strings)"));
      var1.setlocal("list", var4);
      var3 = null;
      var1.setline(308);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, description$22, PyString.fromInterned("Get a description for a single group.  If more than one\n        group matches ('group' is a pattern), return the first.  If no\n        group matches, return an empty string.\n\n        This elides the response code from the server, since it can\n        only be '215' or '285' (for xgtitle) anyway.  If the response\n        code is needed, use the 'descriptions' method.\n\n        NOTE: This neither checks for a wildcard in 'group' nor does\n        it check whether the group actually exists."));
      var1.setlocal("description", var4);
      var3 = null;
      var1.setline(327);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, descriptions$23, PyString.fromInterned("Get descriptions for a range of groups."));
      var1.setlocal("descriptions", var4);
      var3 = null;
      var1.setline(344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, group$24, PyString.fromInterned("Process a GROUP command.  Argument:\n        - group: the group name\n        Returns:\n        - resp: server response if successful\n        - count: number of articles (string)\n        - first: first article number (string)\n        - last: last article number (string)\n        - name: the group name"));
      var1.setlocal("group", var4);
      var3 = null;
      var1.setline(370);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, help$25, PyString.fromInterned("Process a HELP command.  Returns:\n        - resp: server response if successful\n        - list: list of strings"));
      var1.setlocal("help", var4);
      var3 = null;
      var1.setline(377);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, statparse$26, PyString.fromInterned("Internal: parse the response of a STAT, NEXT or LAST command."));
      var1.setlocal("statparse", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, statcmd$27, PyString.fromInterned("Internal: process a STAT, NEXT or LAST command."));
      var1.setlocal("statcmd", var4);
      var3 = null;
      var1.setline(396);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stat$28, PyString.fromInterned("Process a STAT command.  Argument:\n        - id: article number or message id\n        Returns:\n        - resp: server response if successful\n        - nr:   the article number\n        - id:   the message id"));
      var1.setlocal("stat", var4);
      var3 = null;
      var1.setline(406);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$29, PyString.fromInterned("Process a NEXT command.  No arguments.  Return as for STAT."));
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(410);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, last$30, PyString.fromInterned("Process a LAST command.  No arguments.  Return as for STAT."));
      var1.setlocal("last", var4);
      var3 = null;
      var1.setline(414);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, artcmd$31, PyString.fromInterned("Internal: process a HEAD, BODY or ARTICLE command."));
      var1.setlocal("artcmd", var4);
      var3 = null;
      var1.setline(420);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, head$32, PyString.fromInterned("Process a HEAD command.  Argument:\n        - id: article number or message id\n        Returns:\n        - resp: server response if successful\n        - nr: article number\n        - id: message id\n        - list: the lines of the article's header"));
      var1.setlocal("head", var4);
      var3 = null;
      var1.setline(431);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, body$33, PyString.fromInterned("Process a BODY command.  Argument:\n        - id: article number or message id\n        - file: Filename string or file object to store the article in\n        Returns:\n        - resp: server response if successful\n        - nr: article number\n        - id: message id\n        - list: the lines of the article's body or an empty list\n                if file was used"));
      var1.setlocal("body", var4);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, article$34, PyString.fromInterned("Process an ARTICLE command.  Argument:\n        - id: article number or message id\n        Returns:\n        - resp: server response if successful\n        - nr: article number\n        - id: message id\n        - list: the lines of the article"));
      var1.setlocal("article", var4);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, slave$35, PyString.fromInterned("Process a SLAVE command.  Returns:\n        - resp: server response if successful"));
      var1.setlocal("slave", var4);
      var3 = null;
      var1.setline(461);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, xhdr$36, PyString.fromInterned("Process an XHDR command (optional server extension).  Arguments:\n        - hdr: the header type (e.g. 'subject')\n        - str: an article nr, a message id, or a range nr1-nr2\n        Returns:\n        - resp: server response if successful\n        - list: list of (nr, value) strings"));
      var1.setlocal("xhdr", var4);
      var3 = null;
      var1.setline(478);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, xover$37, PyString.fromInterned("Process an XOVER command (optional server extension) Arguments:\n        - start: start of range\n        - end: end of range\n        Returns:\n        - resp: server response if successful\n        - list: list of (art-nr, subject, poster, date,\n                         id, references, size, lines)"));
      var1.setlocal("xover", var4);
      var3 = null;
      var1.setline(504);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, xgtitle$38, PyString.fromInterned("Process an XGTITLE command (optional server extension) Arguments:\n        - group: group name wildcard (i.e. news.*)\n        Returns:\n        - resp: server response if successful\n        - list: list of (name,title) strings"));
      var1.setlocal("xgtitle", var4);
      var3 = null;
      var1.setline(520);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, xpath$39, PyString.fromInterned("Process an XPATH command (optional server extension) Arguments:\n        - id: Message id of article\n        Returns:\n        resp: server response if successful\n        path: directory path to article"));
      var1.setlocal("xpath", var4);
      var3 = null;
      var1.setline(537);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, date$40, PyString.fromInterned("Process the DATE command. Arguments:\n        None\n        Returns:\n        resp: server response if successful\n        date: Date suitable for newnews/newgroups commands etc.\n        time: Time suitable for newnews/newgroups commands etc."));
      var1.setlocal("date", var4);
      var3 = null;
      var1.setline(558);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, post$41, PyString.fromInterned("Process a POST command.  Arguments:\n        - f: file containing the article\n        Returns:\n        - resp: server response if successful"));
      var1.setlocal("post", var4);
      var3 = null;
      var1.setline(580);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ihave$42, PyString.fromInterned("Process an IHAVE command.  Arguments:\n        - id: message-id of the article\n        - f:  file containing the article\n        Returns:\n        - resp: server response if successful\n        Note that if the server refuses the article an exception is raised."));
      var1.setlocal("ihave", var4);
      var3 = null;
      var1.setline(604);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, quit$43, PyString.fromInterned("Process a QUIT command and close the socket.  Returns:\n        - resp: server response if successful"));
      var1.setlocal("quit", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyString.fromInterned("Initialize an instance.  Arguments:\n        - host: hostname to connect to\n        - port: port to connect to (default the standard NNTP port)\n        - user: username to authenticate with\n        - password: password to use with username\n        - readermode: if true, send 'mode reader' command after\n                      connecting.\n\n        readermode is sometimes necessary if you are connecting to an\n        NNTP server on the local machine and intend to call\n        reader-specific commands, such as `group'.  If you get\n        unexpected NNTPPermanentErrors, you might need to set\n        readermode.\n        ");
      var1.setline(117);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(118);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(121);
      PyInteger var9 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"debugging", var9);
      var3 = null;
      var1.setline(122);
      var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
      var1.getlocal(0).__setattr__("welcome", var3);
      var3 = null;
      var1.setline(129);
      var9 = Py.newInteger(0);
      var1.setlocal(7, var9);
      var3 = null;
      var1.setline(130);
      PyObject var10000;
      PyException var10;
      if (var1.getlocal(5).__nonzero__()) {
         try {
            var1.setline(132);
            var3 = var1.getlocal(0).__getattr__("shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode reader"));
            var1.getlocal(0).__setattr__("welcome", var3);
            var3 = null;
         } catch (Throwable var7) {
            var10 = Py.setException(var7, var1);
            if (var10.match(var1.getglobal("NNTPPermanentError"))) {
               var1.setline(135);
            } else {
               if (!var10.match(var1.getglobal("NNTPTemporaryError"))) {
                  throw var10;
               }

               PyObject var4 = var10.value;
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(137);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(8).__getattr__("response").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("480"));
                  var4 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(141);
                  throw Py.makeException();
               }

               var1.setline(139);
               PyInteger var8 = Py.newInteger(1);
               var1.setlocal(7, var8);
               var4 = null;
            }
         }
      }

      try {
         var1.setline(145);
         var10000 = var1.getlocal(6);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(146);
            var3 = imp.importOne("netrc", var1, -1);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(147);
            var3 = var1.getlocal(9).__getattr__("netrc").__call__(var2);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(148);
            var3 = var1.getlocal(10).__getattr__("authenticators").__call__(var2, var1.getlocal(1));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(149);
            if (var1.getlocal(11).__nonzero__()) {
               var1.setline(150);
               var3 = var1.getlocal(11).__getitem__(Py.newInteger(0));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(151);
               var3 = var1.getlocal(11).__getitem__(Py.newInteger(2));
               var1.setlocal(4, var3);
               var3 = null;
            }
         }
      } catch (Throwable var6) {
         var10 = Py.setException(var6, var1);
         if (!var10.match(var1.getglobal("IOError"))) {
            throw var10;
         }

         var1.setline(153);
      }

      var1.setline(155);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(156);
         var3 = var1.getlocal(0).__getattr__("shortcmd").__call__(var2, PyString.fromInterned("authinfo user ")._add(var1.getlocal(3)));
         var1.setlocal(12, var3);
         var3 = null;
         var1.setline(157);
         var3 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("381"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(158);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(159);
               throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(12)));
            }

            var1.setline(161);
            var3 = var1.getlocal(0).__getattr__("shortcmd").__call__(var2, PyString.fromInterned("authinfo pass ")._add(var1.getlocal(4)));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(163);
            var3 = var1.getlocal(12).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
            var10000 = var3._ne(PyString.fromInterned("281"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(164);
               throw Py.makeException(var1.getglobal("NNTPPermanentError").__call__(var2, var1.getlocal(12)));
            }
         }

         var1.setline(165);
         if (var1.getlocal(7).__nonzero__()) {
            try {
               var1.setline(167);
               var3 = var1.getlocal(0).__getattr__("shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode reader"));
               var1.getlocal(0).__setattr__("welcome", var3);
               var3 = null;
            } catch (Throwable var5) {
               var10 = Py.setException(var5, var1);
               if (!var10.match(var1.getglobal("NNTPPermanentError"))) {
                  throw var10;
               }

               var1.setline(170);
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getwelcome$10(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyString.fromInterned("Get the welcome message from the server\n        (this is read and squirreled away by __init__()).\n        If the response code is 200, posting is allowed;\n        if it 201, posting is not allowed.");
      var1.setline(184);
      if (var1.getlocal(0).__getattr__("debugging").__nonzero__()) {
         var1.setline(184);
         Py.printComma(PyString.fromInterned("*welcome*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("welcome")));
      }

      var1.setline(185);
      PyObject var3 = var1.getlocal(0).__getattr__("welcome");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_debuglevel$11(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyString.fromInterned("Set the debugging level.  Argument 'level' means:\n        0: no debugging output (default)\n        1: print commands and responses but not body text etc.\n        2: also print raw lines read and sent before stripping CR/LF");
      var1.setline(193);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("debugging", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject putline$12(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("Internal: send one line to the server, appending CRLF.");
      var1.setline(198);
      PyObject var3 = var1.getlocal(1)._add(var1.getglobal("CRLF"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(199);
      var3 = var1.getlocal(0).__getattr__("debugging");
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(199);
         Py.printComma(PyString.fromInterned("*put*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(200);
      var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject putcmd$13(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyString.fromInterned("Internal: send one command to the server (through putline()).");
      var1.setline(204);
      if (var1.getlocal(0).__getattr__("debugging").__nonzero__()) {
         var1.setline(204);
         Py.printComma(PyString.fromInterned("*cmd*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(205);
      var1.getlocal(0).__getattr__("putline").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getline$14(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyString.fromInterned("Internal: return one line from the server, stripping CRLF.\n        Raise EOFError if the connection is closed.");
      var1.setline(210);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(211);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(var1.getglobal("_MAXLINE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(212);
         throw Py.makeException(var1.getglobal("NNTPDataError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("line too long")));
      } else {
         var1.setline(213);
         var3 = var1.getlocal(0).__getattr__("debugging");
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(214);
            Py.printComma(PyString.fromInterned("*get*"));
            Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
         }

         var1.setline(215);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(215);
            throw Py.makeException(var1.getglobal("EOFError"));
         } else {
            var1.setline(216);
            var3 = var1.getlocal(1).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
            var10000 = var3._eq(var1.getglobal("CRLF"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(216);
               var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
               var1.setlocal(1, var3);
               var3 = null;
            } else {
               var1.setline(217);
               var3 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
               var10000 = var3._in(var1.getglobal("CRLF"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(217);
                  var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(1, var3);
                  var3 = null;
               }
            }

            var1.setline(218);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject getresp$15(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyString.fromInterned("Internal: get a response from the server.\n        Raise various errors if the response indicates an error.");
      var1.setline(223);
      PyObject var3 = var1.getlocal(0).__getattr__("getline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(224);
      if (var1.getlocal(0).__getattr__("debugging").__nonzero__()) {
         var1.setline(224);
         Py.printComma(PyString.fromInterned("*resp*"));
         Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(225);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(226);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("4"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(227);
         throw Py.makeException(var1.getglobal("NNTPTemporaryError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(228);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("5"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(229);
            throw Py.makeException(var1.getglobal("NNTPPermanentError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(230);
            var3 = var1.getlocal(2);
            var10000 = var3._notin(PyString.fromInterned("123"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(231);
               throw Py.makeException(var1.getglobal("NNTPProtocolError").__call__(var2, var1.getlocal(1)));
            } else {
               var1.setline(232);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject getlongresp$16(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      PyString.fromInterned("Internal: get a response plus following text from the server.\n        Raise various errors if the response indicates an error.");
      var1.setline(238);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(241);
         PyObject var4;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(242);
            var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
            var1.setlocal(2, var4);
            var1.setlocal(1, var4);
         }

         var1.setline(244);
         var4 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(245);
         var4 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         PyObject var10000 = var4._notin(var1.getglobal("LONGRESP"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(246);
            throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(3)));
         }

         var1.setline(247);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var7);
         var4 = null;

         while(true) {
            var1.setline(248);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(249);
            var4 = var1.getlocal(0).__getattr__("getline").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(250);
            var4 = var1.getlocal(5);
            var10000 = var4._eq(PyString.fromInterned("."));
            var4 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(252);
            var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned(".."));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(253);
               var4 = var1.getlocal(5).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(254);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(255);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(5)._add(PyString.fromInterned("\n")));
            } else {
               var1.setline(257);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(260);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(261);
            var1.getlocal(2).__getattr__("close").__call__(var2);
         }

         throw (Throwable)var5;
      }

      var1.setline(260);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(261);
         var1.getlocal(2).__getattr__("close").__call__(var2);
      }

      var1.setline(263);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject shortcmd$17(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyString.fromInterned("Internal: send a command and get the response.");
      var1.setline(267);
      var1.getlocal(0).__getattr__("putcmd").__call__(var2, var1.getlocal(1));
      var1.setline(268);
      PyObject var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject longcmd$18(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyString.fromInterned("Internal: send a command and get the response plus following text.");
      var1.setline(272);
      var1.getlocal(0).__getattr__("putcmd").__call__(var2, var1.getlocal(1));
      var1.setline(273);
      PyObject var3 = var1.getlocal(0).__getattr__("getlongresp").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject newgroups$19(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyString.fromInterned("Process a NEWGROUPS command.  Arguments:\n        - date: string 'yymmdd' indicating the date\n        - time: string 'hhmmss' indicating the time\n        Return:\n        - resp: server response if successful\n        - list: list of newsgroup names");
      var1.setline(283);
      PyObject var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, PyString.fromInterned("NEWGROUPS ")._add(var1.getlocal(1))._add(PyString.fromInterned(" "))._add(var1.getlocal(2)), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject newnews$20(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyString.fromInterned("Process a NEWNEWS command.  Arguments:\n        - group: group name or '*'\n        - date: string 'yymmdd' indicating the date\n        - time: string 'hhmmss' indicating the time\n        Return:\n        - resp: server response if successful\n        - list: list of message ids");
      var1.setline(294);
      PyObject var3 = PyString.fromInterned("NEWNEWS ")._add(var1.getlocal(1))._add(PyString.fromInterned(" "))._add(var1.getlocal(2))._add(PyString.fromInterned(" "))._add(var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(295);
      var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, var1.getlocal(5), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject list$21(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyString.fromInterned("Process a LIST command.  Return:\n        - resp: server response if successful\n        - list: list of (group, last, first, flag) (strings)");
      var1.setline(302);
      PyObject var3 = var1.getlocal(0).__getattr__("longcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LIST"), (PyObject)var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(303);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(3))).__iter__();

      while(true) {
         var1.setline(303);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(306);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var7);
         var1.setline(305);
         var5 = var1.getglobal("tuple").__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(4)).__getattr__("split").__call__(var2));
         var1.getlocal(3).__setitem__(var1.getlocal(4), var5);
         var5 = null;
      }
   }

   public PyObject description$22(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyString.fromInterned("Get a description for a single group.  If more than one\n        group matches ('group' is a pattern), return the first.  If no\n        group matches, return an empty string.\n\n        This elides the response code from the server, since it can\n        only be '215' or '285' (for xgtitle) anyway.  If the response\n        code is needed, use the 'descriptions' method.\n\n        NOTE: This neither checks for a wildcard in 'group' nor does\n        it check whether the group actually exists.");
      var1.setline(321);
      PyObject var3 = var1.getlocal(0).__getattr__("descriptions").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(322);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(323);
         PyString var6 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(325);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject descriptions$23(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyString.fromInterned("Get descriptions for a range of groups.");
      var1.setline(329);
      PyObject var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(?P<group>[^ \t]+)[ \t]+(.*)$"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(331);
      var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, PyString.fromInterned("LIST NEWSGROUPS ")._add(var1.getlocal(1)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(332);
      var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("215"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(336);
         var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, PyString.fromInterned("XGTITLE ")._add(var1.getlocal(1)));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(337);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(338);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(338);
         PyObject var6 = var3.__iternext__();
         if (var6 == null) {
            var1.setline(342);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(6, var6);
         var1.setline(339);
         var5 = var1.getlocal(2).__getattr__("search").__call__(var2, var1.getlocal(6).__getattr__("strip").__call__(var2));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(340);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(341);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2)));
         }
      }
   }

   public PyObject group$24(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyString.fromInterned("Process a GROUP command.  Argument:\n        - group: the group name\n        Returns:\n        - resp: server response if successful\n        - count: number of articles (string)\n        - first: first article number (string)\n        - last: last article number (string)\n        - name: the group name");
      var1.setline(354);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__(var2, PyString.fromInterned("GROUP ")._add(var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(355);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("211"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(356);
         throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(2)));
      } else {
         var1.setline(357);
         var3 = var1.getlocal(2).__getattr__("split").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(358);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(4, var4);
         var1.setlocal(5, var4);
         var1.setlocal(6, var4);
         var1.setline(359);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(360);
         var3 = var1.getlocal(7);
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(361);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(1));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(362);
            var3 = var1.getlocal(7);
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(363);
               var3 = var1.getlocal(3).__getitem__(Py.newInteger(2));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(364);
               var3 = var1.getlocal(7);
               var10000 = var3._gt(Py.newInteger(3));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(365);
                  var3 = var1.getlocal(3).__getitem__(Py.newInteger(3));
                  var1.setlocal(6, var3);
                  var3 = null;
                  var1.setline(366);
                  var3 = var1.getlocal(7);
                  var10000 = var3._gt(Py.newInteger(4));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(367);
                     var3 = var1.getlocal(3).__getitem__(Py.newInteger(4)).__getattr__("lower").__call__(var2);
                     var1.setlocal(1, var3);
                     var3 = null;
                  }
               }
            }
         }

         var1.setline(368);
         PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject help$25(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyString.fromInterned("Process a HELP command.  Returns:\n        - resp: server response if successful\n        - list: list of strings");
      var1.setline(375);
      PyObject var3 = var1.getlocal(0).__getattr__("longcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("HELP"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject statparse$26(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyString.fromInterned("Internal: parse the response of a STAT, NEXT or LAST command.");
      var1.setline(379);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("22"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(380);
         throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(381);
         var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(382);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(383);
         PyString var5 = PyString.fromInterned("");
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(384);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(385);
         var3 = var1.getlocal(5);
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(386);
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(387);
            var3 = var1.getlocal(5);
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(388);
               var3 = var1.getlocal(2).__getitem__(Py.newInteger(2));
               var1.setlocal(4, var3);
               var3 = null;
            }
         }

         var1.setline(389);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject statcmd$27(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyString.fromInterned("Internal: process a STAT, NEXT or LAST command.");
      var1.setline(393);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(394);
      var3 = var1.getlocal(0).__getattr__("statparse").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject stat$28(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("Process a STAT command.  Argument:\n        - id: article number or message id\n        Returns:\n        - resp: server response if successful\n        - nr:   the article number\n        - id:   the message id");
      var1.setline(404);
      PyObject var3 = var1.getlocal(0).__getattr__("statcmd").__call__(var2, PyString.fromInterned("STAT ")._add(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$29(PyFrame var1, ThreadState var2) {
      var1.setline(407);
      PyString.fromInterned("Process a NEXT command.  No arguments.  Return as for STAT.");
      var1.setline(408);
      PyObject var3 = var1.getlocal(0).__getattr__("statcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NEXT"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject last$30(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      PyString.fromInterned("Process a LAST command.  No arguments.  Return as for STAT.");
      var1.setline(412);
      PyObject var3 = var1.getlocal(0).__getattr__("statcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LAST"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject artcmd$31(PyFrame var1, ThreadState var2) {
      var1.setline(415);
      PyString.fromInterned("Internal: process a HEAD, BODY or ARTICLE command.");
      var1.setline(416);
      PyObject var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(417);
      var3 = var1.getlocal(0).__getattr__("statparse").__call__(var2, var1.getlocal(3));
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(418);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(5), var1.getlocal(6), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject head$32(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      PyString.fromInterned("Process a HEAD command.  Argument:\n        - id: article number or message id\n        Returns:\n        - resp: server response if successful\n        - nr: article number\n        - id: message id\n        - list: the lines of the article's header");
      var1.setline(429);
      PyObject var3 = var1.getlocal(0).__getattr__("artcmd").__call__(var2, PyString.fromInterned("HEAD ")._add(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject body$33(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyString.fromInterned("Process a BODY command.  Argument:\n        - id: article number or message id\n        - file: Filename string or file object to store the article in\n        Returns:\n        - resp: server response if successful\n        - nr: article number\n        - id: message id\n        - list: the lines of the article's body or an empty list\n                if file was used");
      var1.setline(442);
      PyObject var3 = var1.getlocal(0).__getattr__("artcmd").__call__(var2, PyString.fromInterned("BODY ")._add(var1.getlocal(1)), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject article$34(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyString.fromInterned("Process an ARTICLE command.  Argument:\n        - id: article number or message id\n        Returns:\n        - resp: server response if successful\n        - nr: article number\n        - id: message id\n        - list: the lines of the article");
      var1.setline(453);
      PyObject var3 = var1.getlocal(0).__getattr__("artcmd").__call__(var2, PyString.fromInterned("ARTICLE ")._add(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject slave$35(PyFrame var1, ThreadState var2) {
      var1.setline(457);
      PyString.fromInterned("Process a SLAVE command.  Returns:\n        - resp: server response if successful");
      var1.setline(459);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SLAVE"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject xhdr$36(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyString.fromInterned("Process an XHDR command (optional server extension).  Arguments:\n        - hdr: the header type (e.g. 'subject')\n        - str: an article nr, a message id, or a range nr1-nr2\n        Returns:\n        - resp: server response if successful\n        - list: list of (nr, value) strings");
      var1.setline(469);
      PyObject var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([0-9]+) ?(.*)\n?"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(470);
      var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, PyString.fromInterned("XHDR ")._add(var1.getlocal(1))._add(PyString.fromInterned(" "))._add(var1.getlocal(2)), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(471);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(6))).__iter__();

      while(true) {
         var1.setline(471);
         PyObject var6 = var3.__iternext__();
         if (var6 == null) {
            var1.setline(476);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(7, var6);
         var1.setline(472);
         var5 = var1.getlocal(6).__getitem__(var1.getlocal(7));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(473);
         var5 = var1.getlocal(4).__getattr__("match").__call__(var2, var1.getlocal(8));
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(474);
         if (var1.getlocal(9).__nonzero__()) {
            var1.setline(475);
            var5 = var1.getlocal(9).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
            var1.getlocal(6).__setitem__(var1.getlocal(7), var5);
            var5 = null;
         }
      }
   }

   public PyObject xover$37(PyFrame var1, ThreadState var2) {
      var1.setline(485);
      PyString.fromInterned("Process an XOVER command (optional server extension) Arguments:\n        - start: start of range\n        - end: end of range\n        Returns:\n        - resp: server response if successful\n        - list: list of (art-nr, subject, poster, date,\n                         id, references, size, lines)");
      var1.setline(487);
      PyObject var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, PyString.fromInterned("XOVER ")._add(var1.getlocal(1))._add(PyString.fromInterned("-"))._add(var1.getlocal(2)), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(488);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(489);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(489);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(502);
            PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6)});
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(7, var8);
         var1.setline(490);
         var5 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"));
         var1.setlocal(8, var5);
         var5 = null;

         try {
            var1.setline(492);
            var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(8).__getitem__(Py.newInteger(0)), var1.getlocal(8).__getitem__(Py.newInteger(1)), var1.getlocal(8).__getitem__(Py.newInteger(2)), var1.getlocal(8).__getitem__(Py.newInteger(3)), var1.getlocal(8).__getitem__(Py.newInteger(4)), var1.getlocal(8).__getitem__(Py.newInteger(5)).__getattr__("split").__call__(var2), var1.getlocal(8).__getitem__(Py.newInteger(6)), var1.getlocal(8).__getitem__(Py.newInteger(7))})));
         } catch (Throwable var6) {
            PyException var10 = Py.setException(var6, var1);
            if (var10.match(var1.getglobal("IndexError"))) {
               var1.setline(501);
               throw Py.makeException(var1.getglobal("NNTPDataError").__call__(var2, var1.getlocal(7)));
            }

            throw var10;
         }
      }
   }

   public PyObject xgtitle$38(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      PyString.fromInterned("Process an XGTITLE command (optional server extension) Arguments:\n        - group: group name wildcard (i.e. news.*)\n        Returns:\n        - resp: server response if successful\n        - list: list of (name,title) strings");
      var1.setline(511);
      PyObject var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([^ \t]+)[ \t]+(.*)$"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(512);
      var3 = var1.getlocal(0).__getattr__("longcmd").__call__(var2, PyString.fromInterned("XGTITLE ")._add(var1.getlocal(1)), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(513);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(514);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(514);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(518);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6)});
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(7, var7);
         var1.setline(515);
         var5 = var1.getlocal(3).__getattr__("search").__call__(var2, var1.getlocal(7).__getattr__("strip").__call__(var2));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(516);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(517);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2)));
         }
      }
   }

   public PyObject xpath$39(PyFrame var1, ThreadState var2) {
      var1.setline(525);
      PyString.fromInterned("Process an XPATH command (optional server extension) Arguments:\n        - id: Message id of article\n        Returns:\n        resp: server response if successful\n        path: directory path to article");
      var1.setline(527);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__(var2, PyString.fromInterned("XPATH ")._add(var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(528);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("223"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(529);
         throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(2)));
      } else {
         try {
            var1.setline(531);
            var3 = var1.getlocal(2).__getattr__("split").__call__(var2);
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("ValueError"))) {
               var1.setline(533);
               throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(2)));
            }

            throw var7;
         }

         var1.setline(535);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject date$40(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      PyString.fromInterned("Process the DATE command. Arguments:\n        None\n        Returns:\n        resp: server response if successful\n        date: Date suitable for newnews/newgroups commands etc.\n        time: Time suitable for newnews/newgroups commands etc.");
      var1.setline(545);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DATE"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(546);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("111"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(547);
         throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(548);
         var3 = var1.getlocal(1).__getattr__("split").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(549);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._ne(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(550);
            throw Py.makeException(var1.getglobal("NNTPDataError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(551);
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getslice__(Py.newInteger(2), Py.newInteger(8), (PyObject)null);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(552);
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(1)).__getslice__(Py.newInteger(-6), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(553);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var3._ne(Py.newInteger(6));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var10000 = var3._ne(Py.newInteger(6));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(554);
               throw Py.makeException(var1.getglobal("NNTPDataError").__call__(var2, var1.getlocal(1)));
            } else {
               var1.setline(555);
               PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(4)});
               var1.f_lasti = -1;
               return var4;
            }
         }
      }
   }

   public PyObject post$41(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyString.fromInterned("Process a POST command.  Arguments:\n        - f: file containing the article\n        Returns:\n        - resp: server response if successful");
      var1.setline(564);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POST"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(566);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(PyString.fromInterned("3"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(567);
         throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(2)));
      } else {
         while(true) {
            var1.setline(568);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(569);
            var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(570);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               break;
            }

            var1.setline(572);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
            var10000 = var3._eq(PyString.fromInterned("\n"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(573);
               var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(574);
            var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("."));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(575);
               var3 = PyString.fromInterned(".")._add(var1.getlocal(3));
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(576);
            var1.getlocal(0).__getattr__("putline").__call__(var2, var1.getlocal(3));
         }

         var1.setline(577);
         var1.getlocal(0).__getattr__("putline").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setline(578);
         var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ihave$42(PyFrame var1, ThreadState var2) {
      var1.setline(586);
      PyString.fromInterned("Process an IHAVE command.  Arguments:\n        - id: message-id of the article\n        - f:  file containing the article\n        Returns:\n        - resp: server response if successful\n        Note that if the server refuses the article an exception is raised.");
      var1.setline(588);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__(var2, PyString.fromInterned("IHAVE ")._add(var1.getlocal(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(590);
      var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(PyString.fromInterned("3"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(591);
         throw Py.makeException(var1.getglobal("NNTPReplyError").__call__(var2, var1.getlocal(3)));
      } else {
         while(true) {
            var1.setline(592);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(593);
            var3 = var1.getlocal(2).__getattr__("readline").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(594);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               break;
            }

            var1.setline(596);
            var3 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
            var10000 = var3._eq(PyString.fromInterned("\n"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(597);
               var3 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(598);
            var3 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("."));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(599);
               var3 = PyString.fromInterned(".")._add(var1.getlocal(4));
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(600);
            var1.getlocal(0).__getattr__("putline").__call__(var2, var1.getlocal(4));
         }

         var1.setline(601);
         var1.getlocal(0).__getattr__("putline").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setline(602);
         var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject quit$43(PyFrame var1, ThreadState var2) {
      var1.setline(606);
      PyString.fromInterned("Process a QUIT command and close the socket.  Returns:\n        - resp: server response if successful");
      var1.setline(608);
      PyObject var3 = var1.getlocal(0).__getattr__("shortcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("QUIT"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(609);
      var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
      var1.setline(610);
      var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      var1.setline(611);
      var1.getlocal(0).__delattr__("file");
      var1.getlocal(0).__delattr__("sock");
      var1.setline(612);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public nntplib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NNTPError$1 = Py.newCode(0, var2, var1, "NNTPError", 48, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 50, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NNTPReplyError$3 = Py.newCode(0, var2, var1, "NNTPReplyError", 57, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NNTPTemporaryError$4 = Py.newCode(0, var2, var1, "NNTPTemporaryError", 61, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NNTPPermanentError$5 = Py.newCode(0, var2, var1, "NNTPPermanentError", 65, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NNTPProtocolError$6 = Py.newCode(0, var2, var1, "NNTPProtocolError", 69, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NNTPDataError$7 = Py.newCode(0, var2, var1, "NNTPDataError", 73, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NNTP$8 = Py.newCode(0, var2, var1, "NNTP", 100, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "user", "password", "readermode", "usenetrc", "readermode_afterauth", "e", "netrc", "credentials", "auth", "resp"};
      __init__$9 = Py.newCode(7, var2, var1, "__init__", 101, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getwelcome$10 = Py.newCode(1, var2, var1, "getwelcome", 178, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      set_debuglevel$11 = Py.newCode(2, var2, var1, "set_debuglevel", 187, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      putline$12 = Py.newCode(2, var2, var1, "putline", 196, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      putcmd$13 = Py.newCode(2, var2, var1, "putcmd", 202, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      getline$14 = Py.newCode(1, var2, var1, "getline", 207, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "c"};
      getresp$15 = Py.newCode(1, var2, var1, "getresp", 220, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "openedFile", "resp", "list", "line"};
      getlongresp$16 = Py.newCode(2, var2, var1, "getlongresp", 234, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      shortcmd$17 = Py.newCode(2, var2, var1, "shortcmd", 265, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "file"};
      longcmd$18 = Py.newCode(3, var2, var1, "longcmd", 270, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "date", "time", "file"};
      newgroups$19 = Py.newCode(4, var2, var1, "newgroups", 275, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "group", "date", "time", "file", "cmd"};
      newnews$20 = Py.newCode(5, var2, var1, "newnews", 285, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "resp", "list", "i"};
      list$21 = Py.newCode(2, var2, var1, "list", 297, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "group", "resp", "lines"};
      description$22 = Py.newCode(2, var2, var1, "description", 308, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "group_pattern", "line_pat", "resp", "raw_lines", "lines", "raw_line", "match"};
      descriptions$23 = Py.newCode(2, var2, var1, "descriptions", 327, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "resp", "words", "count", "first", "last", "n"};
      group$24 = Py.newCode(2, var2, var1, "group", 344, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      help$25 = Py.newCode(2, var2, var1, "help", 370, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "words", "nr", "id", "n"};
      statparse$26 = Py.newCode(2, var2, var1, "statparse", 377, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "resp"};
      statcmd$27 = Py.newCode(2, var2, var1, "statcmd", 391, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id"};
      stat$28 = Py.newCode(2, var2, var1, "stat", 396, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      next$29 = Py.newCode(1, var2, var1, "next", 406, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      last$30 = Py.newCode(1, var2, var1, "last", 410, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "file", "resp", "list", "nr", "id"};
      artcmd$31 = Py.newCode(3, var2, var1, "artcmd", 414, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id"};
      head$32 = Py.newCode(2, var2, var1, "head", 420, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id", "file"};
      body$33 = Py.newCode(3, var2, var1, "body", 431, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id"};
      article$34 = Py.newCode(2, var2, var1, "article", 444, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      slave$35 = Py.newCode(1, var2, var1, "slave", 455, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hdr", "str", "file", "pat", "resp", "lines", "i", "line", "m"};
      xhdr$36 = Py.newCode(4, var2, var1, "xhdr", 461, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "end", "file", "resp", "lines", "xover_lines", "line", "elem"};
      xover$37 = Py.newCode(4, var2, var1, "xover", 478, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "group", "file", "line_pat", "resp", "raw_lines", "lines", "raw_line", "match"};
      xgtitle$38 = Py.newCode(3, var2, var1, "xgtitle", 504, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id", "resp", "resp_num", "path"};
      xpath$39 = Py.newCode(2, var2, var1, "xpath", 520, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "elem", "date", "time"};
      date$40 = Py.newCode(1, var2, var1, "date", 537, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "resp", "line"};
      post$41 = Py.newCode(2, var2, var1, "post", 558, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id", "f", "resp", "line"};
      ihave$42 = Py.newCode(3, var2, var1, "ihave", 580, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp"};
      quit$43 = Py.newCode(1, var2, var1, "quit", 604, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new nntplib$py("nntplib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(nntplib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NNTPError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.NNTPReplyError$3(var2, var3);
         case 4:
            return this.NNTPTemporaryError$4(var2, var3);
         case 5:
            return this.NNTPPermanentError$5(var2, var3);
         case 6:
            return this.NNTPProtocolError$6(var2, var3);
         case 7:
            return this.NNTPDataError$7(var2, var3);
         case 8:
            return this.NNTP$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.getwelcome$10(var2, var3);
         case 11:
            return this.set_debuglevel$11(var2, var3);
         case 12:
            return this.putline$12(var2, var3);
         case 13:
            return this.putcmd$13(var2, var3);
         case 14:
            return this.getline$14(var2, var3);
         case 15:
            return this.getresp$15(var2, var3);
         case 16:
            return this.getlongresp$16(var2, var3);
         case 17:
            return this.shortcmd$17(var2, var3);
         case 18:
            return this.longcmd$18(var2, var3);
         case 19:
            return this.newgroups$19(var2, var3);
         case 20:
            return this.newnews$20(var2, var3);
         case 21:
            return this.list$21(var2, var3);
         case 22:
            return this.description$22(var2, var3);
         case 23:
            return this.descriptions$23(var2, var3);
         case 24:
            return this.group$24(var2, var3);
         case 25:
            return this.help$25(var2, var3);
         case 26:
            return this.statparse$26(var2, var3);
         case 27:
            return this.statcmd$27(var2, var3);
         case 28:
            return this.stat$28(var2, var3);
         case 29:
            return this.next$29(var2, var3);
         case 30:
            return this.last$30(var2, var3);
         case 31:
            return this.artcmd$31(var2, var3);
         case 32:
            return this.head$32(var2, var3);
         case 33:
            return this.body$33(var2, var3);
         case 34:
            return this.article$34(var2, var3);
         case 35:
            return this.slave$35(var2, var3);
         case 36:
            return this.xhdr$36(var2, var3);
         case 37:
            return this.xover$37(var2, var3);
         case 38:
            return this.xgtitle$38(var2, var3);
         case 39:
            return this.xpath$39(var2, var3);
         case 40:
            return this.date$40(var2, var3);
         case 41:
            return this.post$41(var2, var3);
         case 42:
            return this.ihave$42(var2, var3);
         case 43:
            return this.quit$43(var2, var3);
         default:
            return null;
      }
   }
}
