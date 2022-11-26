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
@Filename("ftplib.py")
public class ftplib$py extends PyFunctionTable implements PyRunnable {
   static ftplib$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode error_reply$2;
   static final PyCode error_temp$3;
   static final PyCode error_perm$4;
   static final PyCode error_proto$5;
   static final PyCode FTP$6;
   static final PyCode __init__$7;
   static final PyCode connect$8;
   static final PyCode getwelcome$9;
   static final PyCode set_debuglevel$10;
   static final PyCode set_pasv$11;
   static final PyCode sanitize$12;
   static final PyCode putline$13;
   static final PyCode putcmd$14;
   static final PyCode getline$15;
   static final PyCode getmultiline$16;
   static final PyCode getresp$17;
   static final PyCode voidresp$18;
   static final PyCode abort$19;
   static final PyCode sendcmd$20;
   static final PyCode voidcmd$21;
   static final PyCode sendport$22;
   static final PyCode sendeprt$23;
   static final PyCode makeport$24;
   static final PyCode makepasv$25;
   static final PyCode ntransfercmd$26;
   static final PyCode transfercmd$27;
   static final PyCode login$28;
   static final PyCode retrbinary$29;
   static final PyCode retrlines$30;
   static final PyCode storbinary$31;
   static final PyCode storlines$32;
   static final PyCode acct$33;
   static final PyCode nlst$34;
   static final PyCode dir$35;
   static final PyCode rename$36;
   static final PyCode delete$37;
   static final PyCode cwd$38;
   static final PyCode size$39;
   static final PyCode mkd$40;
   static final PyCode rmd$41;
   static final PyCode pwd$42;
   static final PyCode quit$43;
   static final PyCode close$44;
   static final PyCode FTP_TLS$45;
   static final PyCode __init__$46;
   static final PyCode login$47;
   static final PyCode auth$48;
   static final PyCode prot_p$49;
   static final PyCode prot_c$50;
   static final PyCode ntransfercmd$51;
   static final PyCode retrbinary$52;
   static final PyCode retrlines$53;
   static final PyCode storbinary$54;
   static final PyCode storlines$55;
   static final PyCode parse150$56;
   static final PyCode parse227$57;
   static final PyCode parse229$58;
   static final PyCode parse257$59;
   static final PyCode print_line$60;
   static final PyCode ftpcp$61;
   static final PyCode Netrc$62;
   static final PyCode __init__$63;
   static final PyCode get_hosts$64;
   static final PyCode get_account$65;
   static final PyCode get_macros$66;
   static final PyCode get_macro$67;
   static final PyCode test$68;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("An FTP client class and some helper functions.\n\nBased on RFC 959: File Transfer Protocol (FTP), by J. Postel and J. Reynolds\n\nExample:\n\n>>> from ftplib import FTP\n>>> ftp = FTP('ftp.python.org') # connect to host, default port\n>>> ftp.login() # default, i.e.: user anonymous, passwd anonymous@\n'230 Guest login ok, access restrictions apply.'\n>>> ftp.retrlines('LIST') # list directory contents\ntotal 9\ndrwxr-xr-x   8 root     wheel        1024 Jan  3  1994 .\ndrwxr-xr-x   8 root     wheel        1024 Jan  3  1994 ..\ndrwxr-xr-x   2 root     wheel        1024 Jan  3  1994 bin\ndrwxr-xr-x   2 root     wheel        1024 Jan  3  1994 etc\nd-wxrwxr-x   2 ftp      wheel        1024 Sep  5 13:43 incoming\ndrwxr-xr-x   2 root     wheel        1024 Nov 17  1993 lib\ndrwxr-xr-x   6 1094     wheel        1024 Sep 13 19:07 pub\ndrwxr-xr-x   3 root     wheel        1024 Jan  3  1994 usr\n-rw-r--r--   1 root     root          312 Aug  1  1994 welcome.msg\n'226 Transfer complete.'\n>>> ftp.quit()\n'221 Goodbye.'\n>>>\n\nA nice test that reveals some of the network dialogue would be:\npython ftplib.py -d localhost -l -p -l\n"));
      var1.setline(29);
      PyString.fromInterned("An FTP client class and some helper functions.\n\nBased on RFC 959: File Transfer Protocol (FTP), by J. Postel and J. Reynolds\n\nExample:\n\n>>> from ftplib import FTP\n>>> ftp = FTP('ftp.python.org') # connect to host, default port\n>>> ftp.login() # default, i.e.: user anonymous, passwd anonymous@\n'230 Guest login ok, access restrictions apply.'\n>>> ftp.retrlines('LIST') # list directory contents\ntotal 9\ndrwxr-xr-x   8 root     wheel        1024 Jan  3  1994 .\ndrwxr-xr-x   8 root     wheel        1024 Jan  3  1994 ..\ndrwxr-xr-x   2 root     wheel        1024 Jan  3  1994 bin\ndrwxr-xr-x   2 root     wheel        1024 Jan  3  1994 etc\nd-wxrwxr-x   2 ftp      wheel        1024 Sep  5 13:43 incoming\ndrwxr-xr-x   2 root     wheel        1024 Nov 17  1993 lib\ndrwxr-xr-x   6 1094     wheel        1024 Sep 13 19:07 pub\ndrwxr-xr-x   3 root     wheel        1024 Jan  3  1994 usr\n-rw-r--r--   1 root     root          312 Aug  1  1994 welcome.msg\n'226 Transfer complete.'\n>>> ftp.quit()\n'221 Goodbye.'\n>>>\n\nA nice test that reveals some of the network dialogue would be:\npython ftplib.py -d localhost -l -p -l\n");
      var1.setline(39);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(40);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;

      PyObject var4;
      PyException var8;
      String[] var9;
      PyObject[] var10;
      try {
         var1.setline(44);
         var3 = imp.importOne("SOCKS", var1, -1);
         var1.setlocal("SOCKS", var3);
         var3 = null;
         var1.setline(44);
         var3 = var1.getname("SOCKS");
         var1.setlocal("socket", var3);
         var3 = null;
         var1.setline(44);
         var1.dellocal("SOCKS");
         var1.setline(45);
         var9 = new String[]{"getfqdn"};
         var10 = imp.importFrom("socket", var9, var1, -1);
         var4 = var10[0];
         var1.setlocal("getfqdn", var4);
         var4 = null;
         var1.setline(45);
         var3 = var1.getname("getfqdn");
         var1.getname("socket").__setattr__("getfqdn", var3);
         var3 = null;
         var1.setline(45);
         var1.dellocal("getfqdn");
      } catch (Throwable var7) {
         var8 = Py.setException(var7, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(47);
         var4 = imp.importOne("socket", var1, -1);
         var1.setlocal("socket", var4);
         var4 = null;
      }

      var1.setline(48);
      var9 = new String[]{"_GLOBAL_DEFAULT_TIMEOUT"};
      var10 = imp.importFrom("socket", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("_GLOBAL_DEFAULT_TIMEOUT", var4);
      var4 = null;
      var1.setline(50);
      PyList var11 = new PyList(new PyObject[]{PyString.fromInterned("FTP"), PyString.fromInterned("Netrc")});
      var1.setlocal("__all__", var11);
      var3 = null;
      var1.setline(53);
      PyInteger var12 = Py.newInteger(1);
      var1.setlocal("MSG_OOB", var12);
      var3 = null;
      var1.setline(57);
      var12 = Py.newInteger(21);
      var1.setlocal("FTP_PORT", var12);
      var3 = null;
      var1.setline(59);
      var12 = Py.newInteger(8192);
      var1.setlocal("MAXLINE", var12);
      var3 = null;
      var1.setline(63);
      var10 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var10, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(64);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("error_reply", var10, error_reply$2);
      var1.setlocal("error_reply", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(65);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("error_temp", var10, error_temp$3);
      var1.setlocal("error_temp", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(66);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("error_perm", var10, error_perm$4);
      var1.setlocal("error_perm", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(67);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("error_proto", var10, error_proto$5);
      var1.setlocal("error_proto", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(72);
      PyTuple var15 = new PyTuple(new PyObject[]{var1.getname("Error"), var1.getname("IOError"), var1.getname("EOFError")});
      var1.setlocal("all_errors", var15);
      var3 = null;
      var1.setline(76);
      PyString var16 = PyString.fromInterned("\r\n");
      var1.setlocal("CRLF", var16);
      var3 = null;
      var1.setline(79);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("FTP", var10, FTP$6);
      var1.setlocal("FTP", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);

      label30: {
         try {
            var1.setline(604);
            var3 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var3);
            var3 = null;
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (var8.match(var1.getname("ImportError"))) {
               var1.setline(606);
               break label30;
            }

            throw var8;
         }

         var1.setline(608);
         PyObject[] var13 = new PyObject[]{var1.getname("FTP")};
         PyObject var5 = Py.makeClass("FTP_TLS", var13, FTP_TLS$45);
         var1.setlocal("FTP_TLS", var5);
         var5 = null;
         Arrays.fill(var13, (Object)null);
         var1.setline(778);
         var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FTP_TLS"));
         var1.setline(779);
         PyTuple var14 = new PyTuple(new PyObject[]{var1.getname("Error"), var1.getname("IOError"), var1.getname("EOFError"), var1.getname("ssl").__getattr__("SSLError")});
         var1.setlocal("all_errors", var14);
         var4 = null;
      }

      var1.setline(782);
      var3 = var1.getname("None");
      var1.setlocal("_150_re", var3);
      var3 = null;
      var1.setline(784);
      var10 = Py.EmptyObjects;
      PyFunction var17 = new PyFunction(var1.f_globals, var10, parse150$56, PyString.fromInterned("Parse the '150' response for a RETR request.\n    Returns the expected transfer size or None; size is not guaranteed to\n    be present in the 150 message.\n    "));
      var1.setlocal("parse150", var17);
      var3 = null;
      var1.setline(805);
      var3 = var1.getname("None");
      var1.setlocal("_227_re", var3);
      var3 = null;
      var1.setline(807);
      var10 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var10, parse227$57, PyString.fromInterned("Parse the '227' response for a PASV request.\n    Raises error_proto if it does not contain '(h1,h2,h3,h4,p1,p2)'\n    Return ('host.addr.as.numbers', port#) tuple."));
      var1.setlocal("parse227", var17);
      var3 = null;
      var1.setline(827);
      var10 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var10, parse229$58, PyString.fromInterned("Parse the '229' response for a EPSV request.\n    Raises error_proto if it does not contain '(|||port|)'\n    Return ('host.addr.as.numbers', port#) tuple."));
      var1.setlocal("parse229", var17);
      var3 = null;
      var1.setline(849);
      var10 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var10, parse257$59, PyString.fromInterned("Parse the '257' response for a MKD or PWD request.\n    This is a response to a MKD or PWD request: a directory name.\n    Returns the directoryname in the 257 reply."));
      var1.setlocal("parse257", var17);
      var3 = null;
      var1.setline(872);
      var10 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var10, print_line$60, PyString.fromInterned("Default retrlines callback to print a line."));
      var1.setlocal("print_line", var17);
      var3 = null;
      var1.setline(877);
      var10 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("I")};
      var17 = new PyFunction(var1.f_globals, var10, ftpcp$61, PyString.fromInterned("Copy file from one FTP-instance to another."));
      var1.setlocal("ftpcp", var17);
      var3 = null;
      var1.setline(896);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Netrc", var10, Netrc$62);
      var1.setlocal("Netrc", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1005);
      var10 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var10, test$68, PyString.fromInterned("Test program.\n    Usage: ftp [-d] [-r[file]] host [-l[dir]] [-d[dir]] [-p] [file] ...\n\n    -d dir\n    -l list\n    -p password\n    "));
      var1.setlocal("test", var17);
      var3 = null;
      var1.setline(1060);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1061);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(63);
      return var1.getf_locals();
   }

   public PyObject error_reply$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(64);
      return var1.getf_locals();
   }

   public PyObject error_temp$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(65);
      return var1.getf_locals();
   }

   public PyObject error_perm$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(66);
      return var1.getf_locals();
   }

   public PyObject error_proto$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(67);
      return var1.getf_locals();
   }

   public PyObject FTP$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An FTP client class.\n\n    To create a connection, call the class using these arguments:\n            host, user, passwd, acct, timeout\n\n    The first four arguments are all strings, and have default value ''.\n    timeout must be numeric and defaults to None if not passed,\n    meaning that no timeout will be set on any ftp socket(s)\n    If a timeout is passed, then this is now the default timeout for all ftp\n    socket operations for this instance.\n\n    Then use self.connect() with optional host and port argument.\n\n    To download a file, use ftp.retrlines('RETR ' + filename),\n    or ftp.retrbinary() with slightly different arguments.\n    To upload a file, use ftp.storlines() or ftp.storbinary(),\n    which have an open file as argument (see their definitions\n    below for details).\n    The download/upload functions first issue appropriate TYPE\n    and PORT or PASV commands.\n"));
      var1.setline(101);
      PyString.fromInterned("An FTP client class.\n\n    To create a connection, call the class using these arguments:\n            host, user, passwd, acct, timeout\n\n    The first four arguments are all strings, and have default value ''.\n    timeout must be numeric and defaults to None if not passed,\n    meaning that no timeout will be set on any ftp socket(s)\n    If a timeout is passed, then this is now the default timeout for all ftp\n    socket operations for this instance.\n\n    Then use self.connect() with optional host and port argument.\n\n    To download a file, use ftp.retrlines('RETR ' + filename),\n    or ftp.retrbinary() with slightly different arguments.\n    To upload a file, use ftp.storlines() or ftp.storbinary(),\n    which have an open file as argument (see their definitions\n    below for details).\n    The download/upload functions first issue appropriate TYPE\n    and PORT or PASV commands.\n");
      var1.setline(103);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("debugging", var3);
      var3 = null;
      var1.setline(104);
      PyString var4 = PyString.fromInterned("");
      var1.setlocal("host", var4);
      var3 = null;
      var1.setline(105);
      PyObject var5 = var1.getname("FTP_PORT");
      var1.setlocal("port", var5);
      var3 = null;
      var1.setline(106);
      var5 = var1.getname("MAXLINE");
      var1.setlocal("maxline", var5);
      var3 = null;
      var1.setline(107);
      var5 = var1.getname("None");
      var1.setlocal("sock", var5);
      var3 = null;
      var1.setline(108);
      var5 = var1.getname("None");
      var1.setlocal("file", var5);
      var3 = null;
      var1.setline(109);
      var5 = var1.getname("None");
      var1.setlocal("welcome", var5);
      var3 = null;
      var1.setline(110);
      var3 = Py.newInteger(1);
      var1.setlocal("passiveserver", var3);
      var3 = null;
      var1.setline(116);
      PyObject[] var6 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("_GLOBAL_DEFAULT_TIMEOUT")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(124);
      var6 = new PyObject[]{PyString.fromInterned(""), Py.newInteger(0), Py.newInteger(-999)};
      var7 = new PyFunction(var1.f_globals, var6, connect$8, PyString.fromInterned("Connect to host.  Arguments are:\n         - host: hostname to connect to (string, default previous host)\n         - port: port to connect to (integer, default previous port)\n        "));
      var1.setlocal("connect", var7);
      var3 = null;
      var1.setline(141);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getwelcome$9, PyString.fromInterned("Get the welcome message from the server.\n        (this is read and squirreled away by connect())"));
      var1.setlocal("getwelcome", var7);
      var3 = null;
      var1.setline(148);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_debuglevel$10, PyString.fromInterned("Set the debugging level.\n        The required argument level means:\n        0: no debugging output (default)\n        1: print commands and responses but not body text etc.\n        2: also print raw lines read and sent before stripping CR/LF"));
      var1.setlocal("set_debuglevel", var7);
      var3 = null;
      var1.setline(155);
      var5 = var1.getname("set_debuglevel");
      var1.setlocal("debug", var5);
      var3 = null;
      var1.setline(157);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_pasv$11, PyString.fromInterned("Use passive or active mode for data transfers.\n        With a false argument, use the normal PORT mode,\n        With a true argument, use the PASV command."));
      var1.setlocal("set_pasv", var7);
      var3 = null;
      var1.setline(164);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, sanitize$12, (PyObject)null);
      var1.setlocal("sanitize", var7);
      var3 = null;
      var1.setline(173);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, putline$13, (PyObject)null);
      var1.setlocal("putline", var7);
      var3 = null;
      var1.setline(179);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, putcmd$14, (PyObject)null);
      var1.setlocal("putcmd", var7);
      var3 = null;
      var1.setline(185);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getline$15, (PyObject)null);
      var1.setlocal("getline", var7);
      var3 = null;
      var1.setline(200);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getmultiline$16, (PyObject)null);
      var1.setlocal("getmultiline", var7);
      var3 = null;
      var1.setline(214);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getresp$17, (PyObject)null);
      var1.setlocal("getresp", var7);
      var3 = null;
      var1.setline(227);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, voidresp$18, PyString.fromInterned("Expect a response beginning with '2'."));
      var1.setlocal("voidresp", var7);
      var3 = null;
      var1.setline(234);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, abort$19, PyString.fromInterned("Abort a file transfer.  Uses out-of-band data.\n        This does not follow the procedure from the RFC to send Telnet\n        IP and Synch; that doesn't seem to work with the servers I've\n        tried.  Instead, just send the ABOR command as OOB data."));
      var1.setlocal("abort", var7);
      var3 = null;
      var1.setline(246);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, sendcmd$20, PyString.fromInterned("Send a command and return the response."));
      var1.setlocal("sendcmd", var7);
      var3 = null;
      var1.setline(251);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, voidcmd$21, PyString.fromInterned("Send a command and expect a response beginning with '2'."));
      var1.setlocal("voidcmd", var7);
      var3 = null;
      var1.setline(256);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, sendport$22, PyString.fromInterned("Send a PORT command with the current host and the given\n        port number.\n        "));
      var1.setlocal("sendport", var7);
      var3 = null;
      var1.setline(266);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, sendeprt$23, PyString.fromInterned("Send a EPRT command with the current host and the given port number."));
      var1.setlocal("sendeprt", var7);
      var3 = null;
      var1.setline(279);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, makeport$24, PyString.fromInterned("Create a new socket and send a PORT command for it."));
      var1.setlocal("makeport", var7);
      var3 = null;
      var1.setline(310);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, makepasv$25, (PyObject)null);
      var1.setlocal("makepasv", var7);
      var3 = null;
      var1.setline(317);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, ntransfercmd$26, PyString.fromInterned("Initiate a transfer over the data connection.\n\n        If the transfer is active, send a port command and the\n        transfer command, and accept the connection.  If the server is\n        passive, send a pasv command, connect to it, and start the\n        transfer command.  Either way, return the socket for the\n        connection and the expected size of the transfer.  The\n        expected size may be None if it could not be determined.\n\n        Optional `rest' argument can be a string that is sent as the\n        argument to a REST command.  This is essentially a server\n        marker used to tell the server to skip over any data up to the\n        given marker.\n        "));
      var1.setlocal("ntransfercmd", var7);
      var3 = null;
      var1.setline(374);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, transfercmd$27, PyString.fromInterned("Like ntransfercmd() but returns only the socket."));
      var1.setlocal("transfercmd", var7);
      var3 = null;
      var1.setline(378);
      var6 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, login$28, PyString.fromInterned("Login, default anonymous."));
      var1.setlocal("login", var7);
      var3 = null;
      var1.setline(399);
      var6 = new PyObject[]{Py.newInteger(8192), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, retrbinary$29, PyString.fromInterned("Retrieve data in binary mode.  A new port is created for you.\n\n        Args:\n          cmd: A RETR command.\n          callback: A single parameter callable to be called on each\n                    block of data read.\n          blocksize: The maximum number of bytes to read from the\n                     socket at one time.  [default: 8192]\n          rest: Passed to transfercmd().  [default: None]\n\n        Returns:\n          The response code.\n        "));
      var1.setlocal("retrbinary", var7);
      var3 = null;
      var1.setline(423);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, retrlines$30, PyString.fromInterned("Retrieve data in line mode.  A new port is created for you.\n\n        Args:\n          cmd: A RETR, LIST, NLST, or MLSD command.\n          callback: An optional single parameter callable that is called\n                    for each line with the trailing CRLF stripped.\n                    [default: print_line()]\n\n        Returns:\n          The response code.\n        "));
      var1.setlocal("retrlines", var7);
      var3 = null;
      var1.setline(455);
      var6 = new PyObject[]{Py.newInteger(8192), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, storbinary$31, PyString.fromInterned("Store a file in binary mode.  A new port is created for you.\n\n        Args:\n          cmd: A STOR command.\n          fp: A file-like object with a read(num_bytes) method.\n          blocksize: The maximum data size to read from fp and send over\n                     the connection at once.  [default: 8192]\n          callback: An optional single parameter callable that is called on\n                    each block of data after it is sent.  [default: None]\n          rest: Passed to transfercmd().  [default: None]\n\n        Returns:\n          The response code.\n        "));
      var1.setlocal("storbinary", var7);
      var3 = null;
      var1.setline(480);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, storlines$32, PyString.fromInterned("Store a file in line mode.  A new port is created for you.\n\n        Args:\n          cmd: A STOR command.\n          fp: A file-like object with a readline() method.\n          callback: An optional single parameter callable that is called on\n                    each line after it is sent.  [default: None]\n\n        Returns:\n          The response code.\n        "));
      var1.setlocal("storlines", var7);
      var3 = null;
      var1.setline(507);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, acct$33, PyString.fromInterned("Send new account name."));
      var1.setlocal("acct", var7);
      var3 = null;
      var1.setline(512);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, nlst$34, PyString.fromInterned("Return a list of files in a given directory (default the current)."));
      var1.setlocal("nlst", var7);
      var3 = null;
      var1.setline(521);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, dir$35, PyString.fromInterned("List a directory in long form.\n        By default list current directory to stdout.\n        Optional last argument is callback function; all\n        non-empty arguments before it are concatenated to the\n        LIST command.  (This *should* only be used for a pathname.)"));
      var1.setlocal("dir", var7);
      var3 = null;
      var1.setline(536);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, rename$36, PyString.fromInterned("Rename a file."));
      var1.setlocal("rename", var7);
      var3 = null;
      var1.setline(543);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, delete$37, PyString.fromInterned("Delete a file."));
      var1.setlocal("delete", var7);
      var3 = null;
      var1.setline(551);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, cwd$38, PyString.fromInterned("Change to a directory."));
      var1.setlocal("cwd", var7);
      var3 = null;
      var1.setline(564);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, size$39, PyString.fromInterned("Retrieve the size of a file."));
      var1.setlocal("size", var7);
      var3 = null;
      var1.setline(575);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, mkd$40, PyString.fromInterned("Make a directory, return its full pathname."));
      var1.setlocal("mkd", var7);
      var3 = null;
      var1.setline(580);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, rmd$41, PyString.fromInterned("Remove a directory."));
      var1.setlocal("rmd", var7);
      var3 = null;
      var1.setline(584);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, pwd$42, PyString.fromInterned("Return current working directory."));
      var1.setlocal("pwd", var7);
      var3 = null;
      var1.setline(589);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, quit$43, PyString.fromInterned("Quit, and close the connection."));
      var1.setlocal("quit", var7);
      var3 = null;
      var1.setline(595);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, close$44, PyString.fromInterned("Close the connection without assuming anything about it."));
      var1.setlocal("close", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("timeout", var3);
      var3 = null;
      var1.setline(119);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(120);
         var1.getlocal(0).__getattr__("connect").__call__(var2, var1.getlocal(1));
         var1.setline(121);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(122);
            var1.getlocal(0).__getattr__("login").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect$8(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyString.fromInterned("Connect to host.  Arguments are:\n         - host: hostname to connect to (string, default previous host)\n         - port: port to connect to (integer, default previous port)\n        ");
      var1.setline(129);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(130);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("host", var3);
         var3 = null;
      }

      var1.setline(131);
      var3 = var1.getlocal(2);
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("port", var3);
         var3 = null;
      }

      var1.setline(133);
      var3 = var1.getlocal(3);
      var10000 = var3._ne(Py.newInteger(-999));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(134);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("timeout", var3);
         var3 = null;
      }

      var1.setline(135);
      var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("host"), var1.getlocal(0).__getattr__("port")})), (PyObject)var1.getlocal(0).__getattr__("timeout"));
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(136);
      var3 = var1.getlocal(0).__getattr__("sock").__getattr__("family");
      var1.getlocal(0).__setattr__("af", var3);
      var3 = null;
      var1.setline(137);
      var3 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(138);
      var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
      var1.getlocal(0).__setattr__("welcome", var3);
      var3 = null;
      var1.setline(139);
      var3 = var1.getlocal(0).__getattr__("welcome");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getwelcome$9(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("Get the welcome message from the server.\n        (this is read and squirreled away by connect())");
      var1.setline(144);
      if (var1.getlocal(0).__getattr__("debugging").__nonzero__()) {
         var1.setline(145);
         Py.printComma(PyString.fromInterned("*welcome*"));
         Py.println(var1.getlocal(0).__getattr__("sanitize").__call__(var2, var1.getlocal(0).__getattr__("welcome")));
      }

      var1.setline(146);
      PyObject var3 = var1.getlocal(0).__getattr__("welcome");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_debuglevel$10(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("Set the debugging level.\n        The required argument level means:\n        0: no debugging output (default)\n        1: print commands and responses but not body text etc.\n        2: also print raw lines read and sent before stripping CR/LF");
      var1.setline(154);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("debugging", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_pasv$11(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyString.fromInterned("Use passive or active mode for data transfers.\n        With a false argument, use the normal PORT mode,\n        With a true argument, use the PASV command.");
      var1.setline(161);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("passiveserver", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject sanitize$12(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("pass "));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("PASS "));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(166);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;

         while(true) {
            var1.setline(167);
            var3 = var1.getlocal(2);
            var10000 = var3._gt(Py.newInteger(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1).__getitem__(var1.getlocal(2)._sub(Py.newInteger(1)));
               var10000 = var3._in(PyString.fromInterned("\r\n"));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(169);
               var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null)._add(PyString.fromInterned("*")._mul(var1.getlocal(2)._sub(Py.newInteger(5))))._add(var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
               var1.setlocal(1, var3);
               var3 = null;
               break;
            }

            var1.setline(168);
            var3 = var1.getlocal(2)._sub(Py.newInteger(1));
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(170);
      var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject putline$13(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      PyObject var3 = var1.getlocal(1)._add(var1.getglobal("CRLF"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(175);
      var3 = var1.getlocal(0).__getattr__("debugging");
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(175);
         Py.printComma(PyString.fromInterned("*put*"));
         Py.println(var1.getlocal(0).__getattr__("sanitize").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(176);
      var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject putcmd$14(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      if (var1.getlocal(0).__getattr__("debugging").__nonzero__()) {
         var1.setline(180);
         Py.printComma(PyString.fromInterned("*cmd*"));
         Py.println(var1.getlocal(0).__getattr__("sanitize").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(181);
      var1.getlocal(0).__getattr__("putline").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getline$15(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2, var1.getlocal(0).__getattr__("maxline")._add(Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(var1.getlocal(0).__getattr__("maxline"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(188);
         throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("got more than %d bytes")._mod(var1.getlocal(0).__getattr__("maxline"))));
      } else {
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("debugging");
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            Py.printComma(PyString.fromInterned("*get*"));
            Py.println(var1.getlocal(0).__getattr__("sanitize").__call__(var2, var1.getlocal(1)));
         }

         var1.setline(191);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(191);
            throw Py.makeException(var1.getglobal("EOFError"));
         } else {
            var1.setline(192);
            var3 = var1.getlocal(1).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
            var10000 = var3._eq(var1.getglobal("CRLF"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(192);
               var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
               var1.setlocal(1, var3);
               var3 = null;
            } else {
               var1.setline(193);
               var3 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
               var10000 = var3._in(var1.getglobal("CRLF"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(193);
                  var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(1, var3);
                  var3 = null;
               }
            }

            var1.setline(194);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject getmultiline$16(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = var1.getlocal(0).__getattr__("getline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getlocal(1).__getslice__(Py.newInteger(3), Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("-"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(203);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var1.setlocal(2, var3);
         var3 = null;

         do {
            var1.setline(204);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(205);
            var3 = var1.getlocal(0).__getattr__("getline").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(206);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("\n")._add(var1.getlocal(3)));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(207);
            var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
            var10000 = var3._eq(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(3).__getslice__(Py.newInteger(3), Py.newInteger(4), (PyObject)null);
               var10000 = var3._ne(PyString.fromInterned("-"));
               var3 = null;
            }
         } while(!var10000.__nonzero__());
      }

      var1.setline(210);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getresp$17(PyFrame var1, ThreadState var2) {
      var1.setline(215);
      PyObject var3 = var1.getlocal(0).__getattr__("getmultiline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(216);
      if (var1.getlocal(0).__getattr__("debugging").__nonzero__()) {
         var1.setline(216);
         Py.printComma(PyString.fromInterned("*resp*"));
         Py.println(var1.getlocal(0).__getattr__("sanitize").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(217);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var1.getlocal(0).__setattr__("lastresp", var3);
      var3 = null;
      var1.setline(218);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(219);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("1"), PyString.fromInterned("2"), PyString.fromInterned("3")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(220);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(221);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("4"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(222);
            throw Py.makeException(var1.getglobal("error_temp"), var1.getlocal(1));
         } else {
            var1.setline(223);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("5"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(224);
               throw Py.makeException(var1.getglobal("error_perm"), var1.getlocal(1));
            } else {
               var1.setline(225);
               throw Py.makeException(var1.getglobal("error_proto"), var1.getlocal(1));
            }
         }
      }
   }

   public PyObject voidresp$18(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyString.fromInterned("Expect a response beginning with '2'.");
      var1.setline(229);
      PyObject var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(230);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(231);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(1));
      } else {
         var1.setline(232);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject abort$19(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      PyString.fromInterned("Abort a file transfer.  Uses out-of-band data.\n        This does not follow the procedure from the RFC to send Telnet\n        IP and Synch; that doesn't seem to work with the servers I've\n        tried.  Instead, just send the ABOR command as OOB data.");
      var1.setline(239);
      PyObject var3 = PyString.fromInterned("ABOR")._add(var1.getglobal("CRLF"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(240);
      var3 = var1.getlocal(0).__getattr__("debugging");
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(240);
         Py.printComma(PyString.fromInterned("*put urgent*"));
         Py.println(var1.getlocal(0).__getattr__("sanitize").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(241);
      var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1), var1.getglobal("MSG_OOB"));
      var1.setline(242);
      var3 = var1.getlocal(0).__getattr__("getmultiline").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(243);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("426"), PyString.fromInterned("225"), PyString.fromInterned("226")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(244);
         throw Py.makeException(var1.getglobal("error_proto"), var1.getlocal(2));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject sendcmd$20(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyString.fromInterned("Send a command and return the response.");
      var1.setline(248);
      var1.getlocal(0).__getattr__("putcmd").__call__(var2, var1.getlocal(1));
      var1.setline(249);
      PyObject var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject voidcmd$21(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyString.fromInterned("Send a command and expect a response beginning with '2'.");
      var1.setline(253);
      var1.getlocal(0).__getattr__("putcmd").__call__(var2, var1.getlocal(1));
      var1.setline(254);
      PyObject var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sendport$22(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      PyString.fromInterned("Send a PORT command with the current host and the given\n        port number.\n        ");
      var1.setline(260);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(261);
      PyList var4 = new PyList(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(2)._floordiv(Py.newInteger(256))), var1.getglobal("repr").__call__(var2, var1.getlocal(2)._mod(Py.newInteger(256)))});
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(262);
      var3 = var1.getlocal(3)._add(var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(263);
      var3 = PyString.fromInterned("PORT ")._add(PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(5)));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(264);
      var3 = var1.getlocal(0).__getattr__("voidcmd").__call__(var2, var1.getlocal(6));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sendeprt$23(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyString.fromInterned("Send a EPRT command with the current host and the given port number.");
      var1.setline(268);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(269);
      PyObject var4 = var1.getlocal(0).__getattr__("af");
      PyObject var10000 = var4._eq(var1.getglobal("socket").__getattr__("AF_INET"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(270);
         var3 = Py.newInteger(1);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(271);
      var4 = var1.getlocal(0).__getattr__("af");
      var10000 = var4._eq(var1.getglobal("socket").__getattr__("AF_INET6"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(272);
         var3 = Py.newInteger(2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(273);
      var4 = var1.getlocal(3);
      var10000 = var4._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(274);
         throw Py.makeException(var1.getglobal("error_proto"), PyString.fromInterned("unsupported address family"));
      } else {
         var1.setline(275);
         PyList var5 = new PyList(new PyObject[]{PyString.fromInterned(""), var1.getglobal("repr").__call__(var2, var1.getlocal(3)), var1.getlocal(1), var1.getglobal("repr").__call__(var2, var1.getlocal(2)), PyString.fromInterned("")});
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(276);
         var4 = PyString.fromInterned("EPRT ")._add(PyString.fromInterned("|").__getattr__("join").__call__(var2, var1.getlocal(4)));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(277);
         var4 = var1.getlocal(0).__getattr__("voidcmd").__call__(var2, var1.getlocal(5));
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject makeport$24(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyString.fromInterned("Create a new socket and send a PORT command for it.");
      var1.setline(281);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(282);
      var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(283);
      PyObject var10000 = var1.getglobal("socket").__getattr__("getaddrinfo");
      PyObject[] var9 = new PyObject[]{var1.getglobal("None"), Py.newInteger(0), var1.getlocal(0).__getattr__("af"), var1.getglobal("socket").__getattr__("SOCK_STREAM"), Py.newInteger(0), var1.getglobal("socket").__getattr__("AI_PASSIVE")};
      var3 = var10000.__call__(var2, var9).__iter__();

      while(true) {
         var1.setline(283);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(3, var4);
         var1.setline(284);
         PyObject var5 = var1.getlocal(3);
         PyObject[] var6 = Py.unpackSequence(var5, 5);
         PyObject var7 = var6[0];
         var1.setlocal(4, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var6[2];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[3];
         var1.setlocal(7, var7);
         var7 = null;
         var7 = var6[4];
         var1.setlocal(8, var7);
         var7 = null;
         var5 = null;

         try {
            var1.setline(286);
            var5 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(287);
            var1.getlocal(2).__getattr__("bind").__call__(var2, var1.getlocal(8));
            break;
         } catch (Throwable var8) {
            PyException var10 = Py.setException(var8, var1);
            if (!var10.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var10;
            }

            PyObject var11 = var10.value;
            var1.setlocal(1, var11);
            var6 = null;
            var1.setline(289);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(290);
               var1.getlocal(2).__getattr__("close").__call__(var2);
            }

            var1.setline(291);
            var11 = var1.getglobal("None");
            var1.setlocal(2, var11);
            var6 = null;
         }
      }

      var1.setline(294);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(295);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(296);
            throw Py.makeException(var1.getlocal(1));
         } else {
            var1.setline(298);
            throw Py.makeException(var1.getglobal("socket").__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("getaddrinfo returns an empty list")));
         }
      } else {
         var1.setline(299);
         var1.getlocal(2).__getattr__("listen").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setline(300);
         var3 = var1.getlocal(2).__getattr__("getsockname").__call__(var2).__getitem__(Py.newInteger(1));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(301);
         var3 = var1.getlocal(0).__getattr__("sock").__getattr__("getsockname").__call__(var2).__getitem__(Py.newInteger(0));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(302);
         var3 = var1.getlocal(0).__getattr__("af");
         var10000 = var3._eq(var1.getglobal("socket").__getattr__("AF_INET"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(303);
            var3 = var1.getlocal(0).__getattr__("sendport").__call__(var2, var1.getlocal(10), var1.getlocal(9));
            var1.setlocal(11, var3);
            var3 = null;
         } else {
            var1.setline(305);
            var3 = var1.getlocal(0).__getattr__("sendeprt").__call__(var2, var1.getlocal(10), var1.getlocal(9));
            var1.setlocal(11, var3);
            var3 = null;
         }

         var1.setline(306);
         var3 = var1.getlocal(0).__getattr__("timeout");
         var10000 = var3._isnot(var1.getglobal("_GLOBAL_DEFAULT_TIMEOUT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(307);
            var1.getlocal(2).__getattr__("settimeout").__call__(var2, var1.getlocal(0).__getattr__("timeout"));
         }

         var1.setline(308);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject makepasv$25(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyObject var3 = var1.getlocal(0).__getattr__("af");
      PyObject var10000 = var3._eq(var1.getglobal("socket").__getattr__("AF_INET"));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(312);
         var3 = var1.getglobal("parse227").__call__(var2, var1.getlocal(0).__getattr__("sendcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PASV")));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(314);
         var3 = var1.getglobal("parse229").__call__(var2, var1.getlocal(0).__getattr__("sendcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EPSV")), var1.getlocal(0).__getattr__("sock").__getattr__("getpeername").__call__(var2));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(315);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject ntransfercmd$26(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyString.fromInterned("Initiate a transfer over the data connection.\n\n        If the transfer is active, send a port command and the\n        transfer command, and accept the connection.  If the server is\n        passive, send a pasv command, connect to it, and start the\n        transfer command.  Either way, return the socket for the\n        connection and the expected size of the transfer.  The\n        expected size may be None if it could not be determined.\n\n        Optional `rest' argument can be a string that is sent as the\n        argument to a REST command.  This is essentially a server\n        marker used to tell the server to skip over any data up to the\n        given marker.\n        ");
      var1.setline(332);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(333);
      PyObject var10000;
      PyObject[] var4;
      if (var1.getlocal(0).__getattr__("passiveserver").__nonzero__()) {
         var1.setline(334);
         var3 = var1.getlocal(0).__getattr__("makepasv").__call__(var2);
         var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(335);
         var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})), (PyObject)var1.getlocal(0).__getattr__("timeout"));
         var1.setlocal(6, var3);
         var3 = null;

         try {
            var1.setline(337);
            var3 = var1.getlocal(2);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(338);
               var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("REST %s")._mod(var1.getlocal(2)));
            }

            var1.setline(339);
            var3 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, var1.getlocal(1));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(346);
            var3 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(PyString.fromInterned("2"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(347);
               var3 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(348);
            var3 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var3._ne(PyString.fromInterned("1"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(349);
               throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(7));
            }
         } catch (Throwable var7) {
            Py.setException(var7, var1);
            var1.setline(351);
            var1.getlocal(6).__getattr__("close").__call__(var2);
            var1.setline(352);
            throw Py.makeException();
         }
      } else {
         var1.setline(354);
         var3 = var1.getlocal(0).__getattr__("makeport").__call__(var2);
         var1.setlocal(8, var3);
         var3 = null;
         var3 = null;

         try {
            var1.setline(356);
            PyObject var9 = var1.getlocal(2);
            var10000 = var9._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(357);
               var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("REST %s")._mod(var1.getlocal(2)));
            }

            var1.setline(358);
            var9 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, var1.getlocal(1));
            var1.setlocal(7, var9);
            var4 = null;
            var1.setline(360);
            var9 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var9._eq(PyString.fromInterned("2"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(361);
               var9 = var1.getlocal(0).__getattr__("getresp").__call__(var2);
               var1.setlocal(7, var9);
               var4 = null;
            }

            var1.setline(362);
            var9 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var9._ne(PyString.fromInterned("1"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(363);
               throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(7));
            }

            var1.setline(364);
            var9 = var1.getlocal(8).__getattr__("accept").__call__(var2);
            PyObject[] var10 = Py.unpackSequence(var9, 2);
            PyObject var6 = var10[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var10[1];
            var1.setlocal(9, var6);
            var6 = null;
            var4 = null;
            var1.setline(365);
            var9 = var1.getlocal(0).__getattr__("timeout");
            var10000 = var9._isnot(var1.getglobal("_GLOBAL_DEFAULT_TIMEOUT"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(366);
               var1.getlocal(6).__getattr__("settimeout").__call__(var2, var1.getlocal(0).__getattr__("timeout"));
            }
         } catch (Throwable var8) {
            Py.addTraceback(var8, var1);
            var1.setline(368);
            var1.getlocal(8).__getattr__("close").__call__(var2);
            throw (Throwable)var8;
         }

         var1.setline(368);
         var1.getlocal(8).__getattr__("close").__call__(var2);
      }

      var1.setline(369);
      var3 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned("150"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(371);
         var3 = var1.getglobal("parse150").__call__(var2, var1.getlocal(7));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(372);
      PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var11;
   }

   public PyObject transfercmd$27(PyFrame var1, ThreadState var2) {
      var1.setline(375);
      PyString.fromInterned("Like ntransfercmd() but returns only the socket.");
      var1.setline(376);
      PyObject var3 = var1.getlocal(0).__getattr__("ntransfercmd").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject login$28(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyString.fromInterned("Login, default anonymous.");
      var1.setline(380);
      PyString var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(380);
         var3 = PyString.fromInterned("anonymous");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(381);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(381);
         var3 = PyString.fromInterned("");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(382);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(382);
         var3 = PyString.fromInterned("");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(383);
      PyObject var4 = var1.getlocal(1);
      PyObject var10000 = var4._eq(PyString.fromInterned("anonymous"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var4 = var1.getlocal(2);
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("-")}));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(391);
         var4 = var1.getlocal(2)._add(PyString.fromInterned("anonymous@"));
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(392);
      var4 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("USER ")._add(var1.getlocal(1)));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(393);
      var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
      var10000 = var4._eq(PyString.fromInterned("3"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(393);
         var4 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("PASS ")._add(var1.getlocal(2)));
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(394);
      var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
      var10000 = var4._eq(PyString.fromInterned("3"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(394);
         var4 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("ACCT ")._add(var1.getlocal(3)));
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(395);
      var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
      var10000 = var4._ne(PyString.fromInterned("2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(396);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(4));
      } else {
         var1.setline(397);
         var4 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject retrbinary$29(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyString.fromInterned("Retrieve data in binary mode.  A new port is created for you.\n\n        Args:\n          cmd: A RETR command.\n          callback: A single parameter callable to be called on each\n                    block of data read.\n          blocksize: The maximum number of bytes to read from the\n                     socket at one time.  [default: 8192]\n          rest: Passed to transfercmd().  [default: None]\n\n        Returns:\n          The response code.\n        ");
      var1.setline(413);
      var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE I"));
      var1.setline(414);
      PyObject var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;

      while(true) {
         var1.setline(415);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(416);
         var3 = var1.getlocal(5).__getattr__("recv").__call__(var2, var1.getlocal(3));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(417);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            break;
         }

         var1.setline(419);
         var1.getlocal(2).__call__(var2, var1.getlocal(6));
      }

      var1.setline(420);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(421);
      var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject retrlines$30(PyFrame var1, ThreadState var2) {
      var1.setline(434);
      PyString.fromInterned("Retrieve data in line mode.  A new port is created for you.\n\n        Args:\n          cmd: A RETR, LIST, NLST, or MLSD command.\n          callback: An optional single parameter callable that is called\n                    for each line with the trailing CRLF stripped.\n                    [default: print_line()]\n\n        Returns:\n          The response code.\n        ");
      var1.setline(435);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(435);
         var3 = var1.getglobal("print_line");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(436);
      var3 = var1.getlocal(0).__getattr__("sendcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE A"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(437);
      var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(438);
      var3 = var1.getlocal(4).__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(5, var3);
      var3 = null;

      while(true) {
         var1.setline(439);
         if (Py.newInteger(1).__nonzero__()) {
            var1.setline(440);
            var3 = var1.getlocal(5).__getattr__("readline").__call__(var2, var1.getlocal(0).__getattr__("maxline")._add(Py.newInteger(1)));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(441);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
            var10000 = var3._gt(var1.getlocal(0).__getattr__("maxline"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(442);
               throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("got more than %d bytes")._mod(var1.getlocal(0).__getattr__("maxline"))));
            }

            var1.setline(443);
            var3 = var1.getlocal(0).__getattr__("debugging");
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(443);
               Py.printComma(PyString.fromInterned("*retr*"));
               Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(6)));
            }

            var1.setline(444);
            if (!var1.getlocal(6).__not__().__nonzero__()) {
               var1.setline(446);
               var3 = var1.getlocal(6).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
               var10000 = var3._eq(var1.getglobal("CRLF"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(447);
                  var3 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
                  var1.setlocal(6, var3);
                  var3 = null;
               } else {
                  var1.setline(448);
                  var3 = var1.getlocal(6).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
                  var10000 = var3._eq(PyString.fromInterned("\n"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(449);
                     var3 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(6, var3);
                     var3 = null;
                  }
               }

               var1.setline(450);
               var1.getlocal(2).__call__(var2, var1.getlocal(6));
               continue;
            }
         }

         var1.setline(451);
         var1.getlocal(5).__getattr__("close").__call__(var2);
         var1.setline(452);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.setline(453);
         var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject storbinary$31(PyFrame var1, ThreadState var2) {
      var1.setline(469);
      PyString.fromInterned("Store a file in binary mode.  A new port is created for you.\n\n        Args:\n          cmd: A STOR command.\n          fp: A file-like object with a read(num_bytes) method.\n          blocksize: The maximum data size to read from fp and send over\n                     the connection at once.  [default: 8192]\n          callback: An optional single parameter callable that is called on\n                    each block of data after it is sent.  [default: None]\n          rest: Passed to transfercmd().  [default: None]\n\n        Returns:\n          The response code.\n        ");
      var1.setline(470);
      var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE I"));
      var1.setline(471);
      PyObject var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;

      while(true) {
         var1.setline(472);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(473);
         var3 = var1.getlocal(2).__getattr__("read").__call__(var2, var1.getlocal(3));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(474);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            break;
         }

         var1.setline(475);
         var1.getlocal(6).__getattr__("sendall").__call__(var2, var1.getlocal(7));
         var1.setline(476);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(476);
            var1.getlocal(4).__call__(var2, var1.getlocal(7));
         }
      }

      var1.setline(477);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.setline(478);
      var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject storlines$32(PyFrame var1, ThreadState var2) {
      var1.setline(491);
      PyString.fromInterned("Store a file in line mode.  A new port is created for you.\n\n        Args:\n          cmd: A STOR command.\n          fp: A file-like object with a readline() method.\n          callback: An optional single parameter callable that is called on\n                    each line after it is sent.  [default: None]\n\n        Returns:\n          The response code.\n        ");
      var1.setline(492);
      var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE A"));
      var1.setline(493);
      PyObject var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(494);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(495);
         var3 = var1.getlocal(2).__getattr__("readline").__call__(var2, var1.getlocal(0).__getattr__("maxline")._add(Py.newInteger(1)));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(496);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         PyObject var10000 = var3._gt(var1.getlocal(0).__getattr__("maxline"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(497);
            throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("got more than %d bytes")._mod(var1.getlocal(0).__getattr__("maxline"))));
         }

         var1.setline(498);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            break;
         }

         var1.setline(499);
         var3 = var1.getlocal(5).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
         var10000 = var3._ne(var1.getglobal("CRLF"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(500);
            var3 = var1.getlocal(5).__getitem__(Py.newInteger(-1));
            var10000 = var3._in(var1.getglobal("CRLF"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(500);
               var3 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(5, var3);
               var3 = null;
            }

            var1.setline(501);
            var3 = var1.getlocal(5)._add(var1.getglobal("CRLF"));
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(502);
         var1.getlocal(4).__getattr__("sendall").__call__(var2, var1.getlocal(5));
         var1.setline(503);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(503);
            var1.getlocal(3).__call__(var2, var1.getlocal(5));
         }
      }

      var1.setline(504);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(505);
      var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject acct$33(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      PyString.fromInterned("Send new account name.");
      var1.setline(509);
      PyObject var3 = PyString.fromInterned("ACCT ")._add(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(510);
      var3 = var1.getlocal(0).__getattr__("voidcmd").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject nlst$34(PyFrame var1, ThreadState var2) {
      var1.setline(513);
      PyString.fromInterned("Return a list of files in a given directory (default the current).");
      var1.setline(514);
      PyString var3 = PyString.fromInterned("NLST");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(515);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(515);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(517);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(4, var7);
            var3 = null;
            var1.setline(518);
            var1.getlocal(0).__getattr__("retrlines").__call__(var2, var1.getlocal(2), var1.getlocal(4).__getattr__("append"));
            var1.setline(519);
            var6 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(516);
         PyObject var5 = var1.getlocal(2)._add(PyString.fromInterned(" ")._add(var1.getlocal(3)));
         var1.setlocal(2, var5);
         var5 = null;
      }
   }

   public PyObject dir$35(PyFrame var1, ThreadState var2) {
      var1.setline(526);
      PyString.fromInterned("List a directory in long form.\n        By default list current directory to stdout.\n        Optional last argument is callback function; all\n        non-empty arguments before it are concatenated to the\n        LIST command.  (This *should* only be used for a pathname.)");
      var1.setline(527);
      PyString var3 = PyString.fromInterned("LIST");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(528);
      PyObject var6 = var1.getglobal("None");
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(529);
      PyObject var10000 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
      if (var10000.__nonzero__()) {
         var6 = var1.getglobal("type").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
         var10000 = var6._ne(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var3 = null;
      }

      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(530);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var1.getlocal(1).__getitem__(Py.newInteger(-1))});
         PyObject[] var4 = Py.unpackSequence(var8, 2);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(531);
      var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(531);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.setline(534);
            var1.getlocal(0).__getattr__("retrlines").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var7);
         var1.setline(532);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(533);
            var5 = var1.getlocal(2)._add(PyString.fromInterned(" ")._add(var1.getlocal(4)));
            var1.setlocal(2, var5);
            var5 = null;
         }
      }
   }

   public PyObject rename$36(PyFrame var1, ThreadState var2) {
      var1.setline(537);
      PyString.fromInterned("Rename a file.");
      var1.setline(538);
      PyObject var3 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("RNFR ")._add(var1.getlocal(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(539);
      var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(PyString.fromInterned("3"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(540);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(3));
      } else {
         var1.setline(541);
         var3 = var1.getlocal(0).__getattr__("voidcmd").__call__(var2, PyString.fromInterned("RNTO ")._add(var1.getlocal(2)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject delete$37(PyFrame var1, ThreadState var2) {
      var1.setline(544);
      PyString.fromInterned("Delete a file.");
      var1.setline(545);
      PyObject var3 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("DELE ")._add(var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(546);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("250"), PyString.fromInterned("200")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(547);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(549);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(2));
      }
   }

   public PyObject cwd$38(PyFrame var1, ThreadState var2) {
      var1.setline(552);
      PyString.fromInterned("Change to a directory.");
      var1.setline(553);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned(".."));
      var3 = null;
      PyException var4;
      PyObject var7;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(555);
            var3 = var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CDUP"));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (!var4.match(var1.getglobal("error_perm"))) {
               throw var4;
            }
         }

         PyObject var5 = var4.value;
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(557);
         var5 = var1.getlocal(2).__getattr__("args").__getitem__(Py.newInteger(0)).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var10000 = var5._ne(PyString.fromInterned("500"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(558);
            throw Py.makeException();
         }
      } else {
         var1.setline(559);
         var7 = var1.getlocal(1);
         var10000 = var7._eq(PyString.fromInterned(""));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(560);
            PyString var8 = PyString.fromInterned(".");
            var1.setlocal(1, var8);
            var4 = null;
         }
      }

      var1.setline(561);
      var7 = PyString.fromInterned("CWD ")._add(var1.getlocal(1));
      var1.setlocal(3, var7);
      var4 = null;
      var1.setline(562);
      var3 = var1.getlocal(0).__getattr__("voidcmd").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject size$39(PyFrame var1, ThreadState var2) {
      var1.setline(565);
      PyString.fromInterned("Retrieve the size of a file.");
      var1.setline(567);
      PyObject var3 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("SIZE ")._add(var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(568);
      var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("213"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(569);
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;

         try {
            var1.setline(571);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("OverflowError"), var1.getglobal("ValueError")}))) {
               var1.setline(573);
               var3 = var1.getglobal("long").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject mkd$40(PyFrame var1, ThreadState var2) {
      var1.setline(576);
      PyString.fromInterned("Make a directory, return its full pathname.");
      var1.setline(577);
      PyObject var3 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("MKD ")._add(var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(578);
      var3 = var1.getglobal("parse257").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rmd$41(PyFrame var1, ThreadState var2) {
      var1.setline(581);
      PyString.fromInterned("Remove a directory.");
      var1.setline(582);
      PyObject var3 = var1.getlocal(0).__getattr__("voidcmd").__call__(var2, PyString.fromInterned("RMD ")._add(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject pwd$42(PyFrame var1, ThreadState var2) {
      var1.setline(585);
      PyString.fromInterned("Return current working directory.");
      var1.setline(586);
      PyObject var3 = var1.getlocal(0).__getattr__("sendcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PWD"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(587);
      var3 = var1.getglobal("parse257").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject quit$43(PyFrame var1, ThreadState var2) {
      var1.setline(590);
      PyString.fromInterned("Quit, and close the connection.");
      var1.setline(591);
      PyObject var3 = var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("QUIT"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(592);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.setline(593);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$44(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      PyString.fromInterned("Close the connection without assuming anything about it.");
      var1.setline(597);
      PyObject var3 = var1.getlocal(0).__getattr__("file");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(598);
         var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
      }

      var1.setline(599);
      var3 = var1.getlocal(0).__getattr__("sock");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(600);
         var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      }

      var1.setline(601);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("file", var3);
      var1.getlocal(0).__setattr__("sock", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FTP_TLS$45(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A FTP subclass which adds TLS support to FTP as described\n        in RFC-4217.\n\n        Connect as usual to port 21 implicitly securing the FTP control\n        connection before authenticating.\n\n        Securing the data connection requires user to explicitly ask\n        for it by calling prot_p() method.\n\n        Usage example:\n        >>> from ftplib import FTP_TLS\n        >>> ftps = FTP_TLS('ftp.python.org')\n        >>> ftps.login()  # login anonymously previously securing control channel\n        '230 Guest login ok, access restrictions apply.'\n        >>> ftps.prot_p()  # switch to secure data connection\n        '200 Protection level set to P'\n        >>> ftps.retrlines('LIST')  # list directory content securely\n        total 9\n        drwxr-xr-x   8 root     wheel        1024 Jan  3  1994 .\n        drwxr-xr-x   8 root     wheel        1024 Jan  3  1994 ..\n        drwxr-xr-x   2 root     wheel        1024 Jan  3  1994 bin\n        drwxr-xr-x   2 root     wheel        1024 Jan  3  1994 etc\n        d-wxrwxr-x   2 ftp      wheel        1024 Sep  5 13:43 incoming\n        drwxr-xr-x   2 root     wheel        1024 Nov 17  1993 lib\n        drwxr-xr-x   6 1094     wheel        1024 Sep 13 19:07 pub\n        drwxr-xr-x   3 root     wheel        1024 Jan  3  1994 usr\n        -rw-r--r--   1 root     root          312 Aug  1  1994 welcome.msg\n        '226 Transfer complete.'\n        >>> ftps.quit()\n        '221 Goodbye.'\n        >>>\n        "));
      var1.setline(640);
      PyString.fromInterned("A FTP subclass which adds TLS support to FTP as described\n        in RFC-4217.\n\n        Connect as usual to port 21 implicitly securing the FTP control\n        connection before authenticating.\n\n        Securing the data connection requires user to explicitly ask\n        for it by calling prot_p() method.\n\n        Usage example:\n        >>> from ftplib import FTP_TLS\n        >>> ftps = FTP_TLS('ftp.python.org')\n        >>> ftps.login()  # login anonymously previously securing control channel\n        '230 Guest login ok, access restrictions apply.'\n        >>> ftps.prot_p()  # switch to secure data connection\n        '200 Protection level set to P'\n        >>> ftps.retrlines('LIST')  # list directory content securely\n        total 9\n        drwxr-xr-x   8 root     wheel        1024 Jan  3  1994 .\n        drwxr-xr-x   8 root     wheel        1024 Jan  3  1994 ..\n        drwxr-xr-x   2 root     wheel        1024 Jan  3  1994 bin\n        drwxr-xr-x   2 root     wheel        1024 Jan  3  1994 etc\n        d-wxrwxr-x   2 ftp      wheel        1024 Sep  5 13:43 incoming\n        drwxr-xr-x   2 root     wheel        1024 Nov 17  1993 lib\n        drwxr-xr-x   6 1094     wheel        1024 Sep 13 19:07 pub\n        drwxr-xr-x   3 root     wheel        1024 Jan  3  1994 usr\n        -rw-r--r--   1 root     root          312 Aug  1  1994 welcome.msg\n        '226 Transfer complete.'\n        >>> ftps.quit()\n        '221 Goodbye.'\n        >>>\n        ");
      var1.setline(641);
      PyObject var3 = var1.getname("ssl").__getattr__("PROTOCOL_TLSv1");
      var1.setlocal("ssl_version", var3);
      var3 = null;
      var1.setline(643);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("None"), var1.getname("None"), var1.getname("_GLOBAL_DEFAULT_TIMEOUT")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$46, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(650);
      var4 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, login$47, (PyObject)null);
      var1.setlocal("login", var5);
      var3 = null;
      var1.setline(655);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, auth$48, PyString.fromInterned("Set up secure control connection by using TLS/SSL."));
      var1.setlocal("auth", var5);
      var3 = null;
      var1.setline(668);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, prot_p$49, PyString.fromInterned("Set up secure data connection."));
      var1.setlocal("prot_p", var5);
      var3 = null;
      var1.setline(684);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, prot_c$50, PyString.fromInterned("Set up clear text data connection."));
      var1.setlocal("prot_c", var5);
      var3 = null;
      var1.setline(692);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, ntransfercmd$51, (PyObject)null);
      var1.setlocal("ntransfercmd", var5);
      var3 = null;
      var1.setline(699);
      var4 = new PyObject[]{Py.newInteger(8192), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, retrbinary$52, (PyObject)null);
      var1.setlocal("retrbinary", var5);
      var3 = null;
      var1.setline(715);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, retrlines$53, (PyObject)null);
      var1.setlocal("retrlines", var5);
      var3 = null;
      var1.setline(741);
      var4 = new PyObject[]{Py.newInteger(8192), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, storbinary$54, (PyObject)null);
      var1.setlocal("storbinary", var5);
      var3 = null;
      var1.setline(757);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, storlines$55, (PyObject)null);
      var1.setlocal("storlines", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$46(PyFrame var1, ThreadState var2) {
      var1.setline(645);
      PyObject var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("keyfile", var3);
      var3 = null;
      var1.setline(646);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("certfile", var3);
      var3 = null;
      var1.setline(647);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_prot_p", var3);
      var3 = null;
      var1.setline(648);
      PyObject var10000 = var1.getglobal("FTP").__getattr__("__init__");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(7)};
      var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject login$47(PyFrame var1, ThreadState var2) {
      var1.setline(651);
      PyObject var10000 = var1.getlocal(4);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getglobal("ssl").__getattr__("SSLSocket")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(652);
         var1.getlocal(0).__getattr__("auth").__call__(var2);
      }

      var1.setline(653);
      PyObject var3 = var1.getglobal("FTP").__getattr__("login").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject auth$48(PyFrame var1, ThreadState var2) {
      var1.setline(656);
      PyString.fromInterned("Set up secure control connection by using TLS/SSL.");
      var1.setline(657);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getglobal("ssl").__getattr__("SSLSocket")).__nonzero__()) {
         var1.setline(658);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Already using TLS")));
      } else {
         var1.setline(659);
         PyObject var3 = var1.getlocal(0).__getattr__("ssl_version");
         PyObject var10000 = var3._eq(var1.getglobal("ssl").__getattr__("PROTOCOL_TLSv1"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(660);
            var3 = var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AUTH TLS"));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(662);
            var3 = var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AUTH SSL"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(663);
         var10000 = var1.getglobal("ssl").__getattr__("wrap_socket");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("sock"), var1.getlocal(0).__getattr__("keyfile"), var1.getlocal(0).__getattr__("certfile"), var1.getlocal(0).__getattr__("ssl_version")};
         String[] var4 = new String[]{"ssl_version"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.getlocal(0).__setattr__("sock", var3);
         var3 = null;
         var1.setline(665);
         var10000 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile");
         var5 = new PyObject[]{PyString.fromInterned("rb")};
         var4 = new String[]{"mode"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.getlocal(0).__setattr__("file", var3);
         var3 = null;
         var1.setline(666);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject prot_p$49(PyFrame var1, ThreadState var2) {
      var1.setline(669);
      PyString.fromInterned("Set up secure data connection.");
      var1.setline(679);
      var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PBSZ 0"));
      var1.setline(680);
      PyObject var3 = var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PROT P"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(681);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_prot_p", var3);
      var3 = null;
      var1.setline(682);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject prot_c$50(PyFrame var1, ThreadState var2) {
      var1.setline(685);
      PyString.fromInterned("Set up clear text data connection.");
      var1.setline(686);
      PyObject var3 = var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PROT C"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(687);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_prot_p", var3);
      var3 = null;
      var1.setline(688);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ntransfercmd$51(PyFrame var1, ThreadState var2) {
      var1.setline(693);
      PyObject var3 = var1.getglobal("FTP").__getattr__("ntransfercmd").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(694);
      if (var1.getlocal(0).__getattr__("_prot_p").__nonzero__()) {
         var1.setline(695);
         PyObject var10000 = var1.getglobal("ssl").__getattr__("wrap_socket");
         PyObject[] var6 = new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("keyfile"), var1.getlocal(0).__getattr__("certfile"), var1.getlocal(0).__getattr__("ssl_version")};
         String[] var7 = new String[]{"ssl_version"};
         var10000 = var10000.__call__(var2, var6, var7);
         var3 = null;
         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(697);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject retrbinary$52(PyFrame var1, ThreadState var2) {
      var1.setline(700);
      var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE I"));
      var1.setline(701);
      PyObject var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var3 = null;

      try {
         while(true) {
            var1.setline(703);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(704);
            PyObject var4 = var1.getlocal(5).__getattr__("recv").__call__(var2, var1.getlocal(3));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(705);
            if (var1.getlocal(6).__not__().__nonzero__()) {
               break;
            }

            var1.setline(707);
            var1.getlocal(2).__call__(var2, var1.getlocal(6));
         }

         var1.setline(709);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("ssl").__getattr__("SSLSocket")).__nonzero__()) {
            var1.setline(710);
            var1.getlocal(5).__getattr__("unwrap").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(712);
         var1.getlocal(5).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(712);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(713);
      var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject retrlines$53(PyFrame var1, ThreadState var2) {
      var1.setline(716);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(716);
         var3 = var1.getglobal("print_line");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(717);
      var3 = var1.getlocal(0).__getattr__("sendcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE A"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(718);
      var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(719);
      var3 = var1.getlocal(4).__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(5, var3);
      var3 = null;
      var3 = null;

      try {
         while(true) {
            var1.setline(721);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(722);
            PyObject var4 = var1.getlocal(5).__getattr__("readline").__call__(var2, var1.getlocal(0).__getattr__("maxline")._add(Py.newInteger(1)));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(723);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
            var10000 = var4._gt(var1.getlocal(0).__getattr__("maxline"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(724);
               throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("got more than %d bytes")._mod(var1.getlocal(0).__getattr__("maxline"))));
            }

            var1.setline(725);
            var4 = var1.getlocal(0).__getattr__("debugging");
            var10000 = var4._gt(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(725);
               Py.printComma(PyString.fromInterned("*retr*"));
               Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(6)));
            }

            var1.setline(726);
            if (var1.getlocal(6).__not__().__nonzero__()) {
               break;
            }

            var1.setline(728);
            var4 = var1.getlocal(6).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
            var10000 = var4._eq(var1.getglobal("CRLF"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(729);
               var4 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
               var1.setlocal(6, var4);
               var4 = null;
            } else {
               var1.setline(730);
               var4 = var1.getlocal(6).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("\n"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(731);
                  var4 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(6, var4);
                  var4 = null;
               }
            }

            var1.setline(732);
            var1.getlocal(2).__call__(var2, var1.getlocal(6));
         }

         var1.setline(734);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("ssl").__getattr__("SSLSocket")).__nonzero__()) {
            var1.setline(735);
            var1.getlocal(4).__getattr__("unwrap").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(737);
         var1.getlocal(5).__getattr__("close").__call__(var2);
         var1.setline(738);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(737);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(738);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(739);
      var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject storbinary$54(PyFrame var1, ThreadState var2) {
      var1.setline(742);
      var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE I"));
      var1.setline(743);
      PyObject var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1), var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var3 = null;

      try {
         while(true) {
            var1.setline(745);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(746);
            PyObject var4 = var1.getlocal(2).__getattr__("read").__call__(var2, var1.getlocal(3));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(747);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               break;
            }

            var1.setline(748);
            var1.getlocal(6).__getattr__("sendall").__call__(var2, var1.getlocal(7));
            var1.setline(749);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(749);
               var1.getlocal(4).__call__(var2, var1.getlocal(7));
            }
         }

         var1.setline(751);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("ssl").__getattr__("SSLSocket")).__nonzero__()) {
            var1.setline(752);
            var1.getlocal(6).__getattr__("unwrap").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(754);
         var1.getlocal(6).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(754);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.setline(755);
      var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject storlines$55(PyFrame var1, ThreadState var2) {
      var1.setline(758);
      var1.getlocal(0).__getattr__("voidcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TYPE A"));
      var1.setline(759);
      PyObject var3 = var1.getlocal(0).__getattr__("transfercmd").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      try {
         while(true) {
            var1.setline(761);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(762);
            PyObject var4 = var1.getlocal(2).__getattr__("readline").__call__(var2, var1.getlocal(0).__getattr__("maxline")._add(Py.newInteger(1)));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(763);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            PyObject var10000 = var4._gt(var1.getlocal(0).__getattr__("maxline"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(764);
               throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("got more than %d bytes")._mod(var1.getlocal(0).__getattr__("maxline"))));
            }

            var1.setline(765);
            if (var1.getlocal(5).__not__().__nonzero__()) {
               break;
            }

            var1.setline(766);
            var4 = var1.getlocal(5).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
            var10000 = var4._ne(var1.getglobal("CRLF"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(767);
               var4 = var1.getlocal(5).__getitem__(Py.newInteger(-1));
               var10000 = var4._in(var1.getglobal("CRLF"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(767);
                  var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(5, var4);
                  var4 = null;
               }

               var1.setline(768);
               var4 = var1.getlocal(5)._add(var1.getglobal("CRLF"));
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(769);
            var1.getlocal(4).__getattr__("sendall").__call__(var2, var1.getlocal(5));
            var1.setline(770);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(770);
               var1.getlocal(3).__call__(var2, var1.getlocal(5));
            }
         }

         var1.setline(772);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("ssl").__getattr__("SSLSocket")).__nonzero__()) {
            var1.setline(773);
            var1.getlocal(4).__getattr__("unwrap").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(775);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(775);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(776);
      var3 = var1.getlocal(0).__getattr__("voidresp").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse150$56(PyFrame var1, ThreadState var2) {
      var1.setline(788);
      PyString.fromInterned("Parse the '150' response for a RETR request.\n    Returns the expected transfer size or None; size is not guaranteed to\n    be present in the 150 message.\n    ");
      var1.setline(789);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("150"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(790);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(0));
      } else {
         var1.setline(792);
         var3 = var1.getglobal("_150_re");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(793);
            var3 = imp.importOne("re", var1, -1);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(794);
            var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("150 .* \\((\\d+) bytes\\)"), (PyObject)var1.getlocal(1).__getattr__("IGNORECASE"));
            var1.setglobal("_150_re", var3);
            var3 = null;
         }

         var1.setline(795);
         var3 = var1.getglobal("_150_re").__getattr__("match").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(796);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(797);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(798);
            PyObject var4 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(3, var4);
            var4 = null;

            try {
               var1.setline(800);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (var6.match(new PyTuple(new PyObject[]{var1.getglobal("OverflowError"), var1.getglobal("ValueError")}))) {
                  var1.setline(802);
                  var3 = var1.getglobal("long").__call__(var2, var1.getlocal(3));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  throw var6;
               }
            }
         }
      }
   }

   public PyObject parse227$57(PyFrame var1, ThreadState var2) {
      var1.setline(810);
      PyString.fromInterned("Parse the '227' response for a PASV request.\n    Raises error_proto if it does not contain '(h1,h2,h3,h4,p1,p2)'\n    Return ('host.addr.as.numbers', port#) tuple.");
      var1.setline(812);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("227"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(813);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(0));
      } else {
         var1.setline(815);
         var3 = var1.getglobal("_227_re");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(816);
            var3 = imp.importOne("re", var1, -1);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(817);
            var3 = var1.getlocal(1).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\d+),(\\d+),(\\d+),(\\d+),(\\d+),(\\d+)"));
            var1.setglobal("_227_re", var3);
            var3 = null;
         }

         var1.setline(818);
         var3 = var1.getglobal("_227_re").__getattr__("search").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(819);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(820);
            throw Py.makeException(var1.getglobal("error_proto"), var1.getlocal(0));
         } else {
            var1.setline(821);
            var3 = var1.getlocal(2).__getattr__("groups").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(822);
            var3 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(823);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(4)))._lshift(Py.newInteger(8))._add(var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(5))));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(824);
            PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject parse229$58(PyFrame var1, ThreadState var2) {
      var1.setline(830);
      PyString.fromInterned("Parse the '229' response for a EPSV request.\n    Raises error_proto if it does not contain '(|||port|)'\n    Return ('host.addr.as.numbers', port#) tuple.");
      var1.setline(832);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("229"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(833);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(0));
      } else {
         var1.setline(834);
         var3 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("("));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(835);
         var3 = var1.getlocal(2);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(835);
            throw Py.makeException(var1.getglobal("error_proto"), var1.getlocal(0));
         } else {
            var1.setline(836);
            var3 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")"), (PyObject)var1.getlocal(2)._add(Py.newInteger(1)));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(837);
            var3 = var1.getlocal(3);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(838);
               throw Py.makeException(var1.getglobal("error_proto"), var1.getlocal(0));
            } else {
               var1.setline(839);
               var3 = var1.getlocal(0).__getitem__(var1.getlocal(2)._add(Py.newInteger(1)));
               var10000 = var3._ne(var1.getlocal(0).__getitem__(var1.getlocal(3)._sub(Py.newInteger(1))));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(840);
                  throw Py.makeException(var1.getglobal("error_proto"), var1.getlocal(0));
               } else {
                  var1.setline(841);
                  var3 = var1.getlocal(0).__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), var1.getlocal(3), (PyObject)null).__getattr__("split").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(2)._add(Py.newInteger(1))));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(842);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
                  var10000 = var3._ne(Py.newInteger(5));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(843);
                     throw Py.makeException(var1.getglobal("error_proto"), var1.getlocal(0));
                  } else {
                     var1.setline(844);
                     var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(845);
                     var3 = var1.getglobal("int").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(3)));
                     var1.setlocal(6, var3);
                     var3 = null;
                     var1.setline(846);
                     PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
                     var1.f_lasti = -1;
                     return var4;
                  }
               }
            }
         }
      }
   }

   public PyObject parse257$59(PyFrame var1, ThreadState var2) {
      var1.setline(852);
      PyString.fromInterned("Parse the '257' response for a MKD or PWD request.\n    This is a response to a MKD or PWD request: a directory name.\n    Returns the directoryname in the 257 reply.");
      var1.setline(854);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("257"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(855);
         throw Py.makeException(var1.getglobal("error_reply"), var1.getlocal(0));
      } else {
         var1.setline(856);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(3), Py.newInteger(5), (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned(" \""));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(857);
            PyString var5 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(858);
            PyString var4 = PyString.fromInterned("");
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(859);
            PyInteger var6 = Py.newInteger(5);
            var1.setlocal(2, var6);
            var4 = null;
            var1.setline(860);
            PyObject var7 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var7);
            var4 = null;

            while(true) {
               var1.setline(861);
               var7 = var1.getlocal(2);
               var10000 = var7._lt(var1.getlocal(3));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(862);
               var7 = var1.getlocal(0).__getitem__(var1.getlocal(2));
               var1.setlocal(4, var7);
               var4 = null;
               var1.setline(863);
               var7 = var1.getlocal(2)._add(Py.newInteger(1));
               var1.setlocal(2, var7);
               var4 = null;
               var1.setline(864);
               var7 = var1.getlocal(4);
               var10000 = var7._eq(PyString.fromInterned("\""));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(865);
                  var7 = var1.getlocal(2);
                  var10000 = var7._ge(var1.getlocal(3));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var7 = var1.getlocal(0).__getitem__(var1.getlocal(2));
                     var10000 = var7._ne(PyString.fromInterned("\""));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(867);
                  var7 = var1.getlocal(2)._add(Py.newInteger(1));
                  var1.setlocal(2, var7);
                  var4 = null;
               }

               var1.setline(868);
               var7 = var1.getlocal(1)._add(var1.getlocal(4));
               var1.setlocal(1, var7);
               var4 = null;
            }

            var1.setline(869);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject print_line$60(PyFrame var1, ThreadState var2) {
      var1.setline(873);
      PyString.fromInterned("Default retrlines callback to print a line.");
      var1.setline(874);
      Py.println(var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ftpcp$61(PyFrame var1, ThreadState var2) {
      var1.setline(878);
      PyString.fromInterned("Copy file from one FTP-instance to another.");
      var1.setline(879);
      PyObject var3;
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(879);
         var3 = var1.getlocal(1);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(880);
      var3 = PyString.fromInterned("TYPE ")._add(var1.getlocal(4));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(881);
      var1.getlocal(0).__getattr__("voidcmd").__call__(var2, var1.getlocal(4));
      var1.setline(882);
      var1.getlocal(2).__getattr__("voidcmd").__call__(var2, var1.getlocal(4));
      var1.setline(883);
      var3 = var1.getglobal("parse227").__call__(var2, var1.getlocal(0).__getattr__("sendcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PASV")));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(884);
      var1.getlocal(2).__getattr__("sendport").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.setline(888);
      var3 = var1.getlocal(2).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("STOR ")._add(var1.getlocal(3)));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(889);
      var3 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("125"), PyString.fromInterned("150")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(889);
         throw Py.makeException(var1.getglobal("error_proto"));
      } else {
         var1.setline(890);
         var3 = var1.getlocal(0).__getattr__("sendcmd").__call__(var2, PyString.fromInterned("RETR ")._add(var1.getlocal(1)));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(891);
         var3 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("125"), PyString.fromInterned("150")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(891);
            throw Py.makeException(var1.getglobal("error_proto"));
         } else {
            var1.setline(892);
            var1.getlocal(0).__getattr__("voidresp").__call__(var2);
            var1.setline(893);
            var1.getlocal(2).__getattr__("voidresp").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject Netrc$62(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to parse & provide access to 'netrc' format files.\n\n    See the netrc(4) man page for information on the file format.\n\n    WARNING: This class is obsolete -- use module netrc instead.\n\n    "));
      var1.setline(903);
      PyString.fromInterned("Class to parse & provide access to 'netrc' format files.\n\n    See the netrc(4) man page for information on the file format.\n\n    WARNING: This class is obsolete -- use module netrc instead.\n\n    ");
      var1.setline(904);
      PyObject var3 = var1.getname("None");
      var1.setlocal("_Netrc__defuser", var3);
      var3 = null;
      var1.setline(905);
      var3 = var1.getname("None");
      var1.setlocal("_Netrc__defpasswd", var3);
      var3 = null;
      var1.setline(906);
      var3 = var1.getname("None");
      var1.setlocal("_Netrc__defacct", var3);
      var3 = null;
      var1.setline(908);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$63, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(975);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_hosts$64, PyString.fromInterned("Return a list of hosts mentioned in the .netrc file."));
      var1.setlocal("get_hosts", var5);
      var3 = null;
      var1.setline(979);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_account$65, PyString.fromInterned("Returns login information for the named host.\n\n        The return value is a triple containing userid,\n        password, and the accounting field.\n\n        "));
      var1.setlocal("get_account", var5);
      var3 = null;
      var1.setline(995);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_macros$66, PyString.fromInterned("Return a list of all defined macro names."));
      var1.setlocal("get_macros", var5);
      var3 = null;
      var1.setline(999);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_macro$67, PyString.fromInterned("Return a sequence of lines which define a named macro."));
      var1.setlocal("get_macro", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$63(PyFrame var1, ThreadState var2) {
      var1.setline(909);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(910);
         PyString var6 = PyString.fromInterned("HOME");
         var10000 = var6._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(914);
            throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("specify file to load or set $HOME"));
         }

         var1.setline(911);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME")), (PyObject)PyString.fromInterned(".netrc"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(916);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_Netrc__hosts", var7);
      var3 = null;
      var1.setline(917);
      var7 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_Netrc__macros", var7);
      var3 = null;
      var1.setline(918);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(919);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal(3, var8);
      var3 = null;

      while(true) {
         var1.setline(920);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(921);
         var3 = var1.getlocal(2).__getattr__("readline").__call__(var2, var1.getlocal(0).__getattr__("maxline")._add(Py.newInteger(1)));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(922);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var3._gt(var1.getlocal(0).__getattr__("maxline"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(923);
            throw Py.makeException(var1.getglobal("Error").__call__(var2, PyString.fromInterned("got more than %d bytes")._mod(var1.getlocal(0).__getattr__("maxline"))));
         }

         var1.setline(924);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(925);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4).__getattr__("strip").__call__(var2);
         }

         if (var10000.__nonzero__()) {
            var1.setline(926);
            var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(928);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(929);
               var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(5));
               var1.getlocal(0).__getattr__("_Netrc__macros").__setitem__(var1.getlocal(6), var3);
               var3 = null;
               var1.setline(930);
               var8 = Py.newInteger(0);
               var1.setlocal(3, var8);
               var3 = null;
            }

            var1.setline(931);
            var3 = var1.getlocal(4).__getattr__("split").__call__(var2);
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(932);
            var3 = var1.getglobal("None");
            var1.setlocal(8, var3);
            var1.setlocal(9, var3);
            var1.setlocal(10, var3);
            var1.setlocal(11, var3);
            var1.setline(933);
            var8 = Py.newInteger(0);
            var1.setlocal(12, var8);
            var3 = null;
            var1.setline(934);
            var8 = Py.newInteger(0);
            var1.setlocal(13, var8);
            var3 = null;

            while(true) {
               var1.setline(935);
               var3 = var1.getlocal(13);
               var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(7)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(936);
               var3 = var1.getlocal(7).__getitem__(var1.getlocal(13));
               var1.setlocal(14, var3);
               var3 = null;
               var1.setline(937);
               var3 = var1.getlocal(13)._add(Py.newInteger(1));
               var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(7)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(938);
                  var3 = var1.getlocal(7).__getitem__(var1.getlocal(13)._add(Py.newInteger(1)));
                  var1.setlocal(15, var3);
                  var3 = null;
               } else {
                  var1.setline(940);
                  var3 = var1.getglobal("None");
                  var1.setlocal(15, var3);
                  var3 = null;
               }

               var1.setline(941);
               var3 = var1.getlocal(14);
               var10000 = var3._eq(PyString.fromInterned("default"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(942);
                  var8 = Py.newInteger(1);
                  var1.setlocal(12, var8);
                  var3 = null;
               } else {
                  var1.setline(943);
                  var3 = var1.getlocal(14);
                  var10000 = var3._eq(PyString.fromInterned("machine"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(15);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(944);
                     var3 = var1.getlocal(15).__getattr__("lower").__call__(var2);
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(945);
                     var3 = var1.getlocal(13)._add(Py.newInteger(1));
                     var1.setlocal(13, var3);
                     var3 = null;
                  } else {
                     var1.setline(946);
                     var3 = var1.getlocal(14);
                     var10000 = var3._eq(PyString.fromInterned("login"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(15);
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(947);
                        var3 = var1.getlocal(15);
                        var1.setlocal(9, var3);
                        var3 = null;
                        var1.setline(948);
                        var3 = var1.getlocal(13)._add(Py.newInteger(1));
                        var1.setlocal(13, var3);
                        var3 = null;
                     } else {
                        var1.setline(949);
                        var3 = var1.getlocal(14);
                        var10000 = var3._eq(PyString.fromInterned("password"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(15);
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(950);
                           var3 = var1.getlocal(15);
                           var1.setlocal(10, var3);
                           var3 = null;
                           var1.setline(951);
                           var3 = var1.getlocal(13)._add(Py.newInteger(1));
                           var1.setlocal(13, var3);
                           var3 = null;
                        } else {
                           var1.setline(952);
                           var3 = var1.getlocal(14);
                           var10000 = var3._eq(PyString.fromInterned("account"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var10000 = var1.getlocal(15);
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(953);
                              var3 = var1.getlocal(15);
                              var1.setlocal(11, var3);
                              var3 = null;
                              var1.setline(954);
                              var3 = var1.getlocal(13)._add(Py.newInteger(1));
                              var1.setlocal(13, var3);
                              var3 = null;
                           } else {
                              var1.setline(955);
                              var3 = var1.getlocal(14);
                              var10000 = var3._eq(PyString.fromInterned("macdef"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(15);
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(956);
                                 var3 = var1.getlocal(15);
                                 var1.setlocal(6, var3);
                                 var3 = null;
                                 var1.setline(957);
                                 PyList var9 = new PyList(Py.EmptyObjects);
                                 var1.setlocal(5, var9);
                                 var3 = null;
                                 var1.setline(958);
                                 var8 = Py.newInteger(1);
                                 var1.setlocal(3, var8);
                                 var3 = null;
                                 break;
                              }
                           }
                        }
                     }
                  }
               }

               var1.setline(960);
               var3 = var1.getlocal(13)._add(Py.newInteger(1));
               var1.setlocal(13, var3);
               var3 = null;
            }

            var1.setline(961);
            if (var1.getlocal(12).__nonzero__()) {
               var1.setline(962);
               var10000 = var1.getlocal(9);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("_Netrc__defuser");
               }

               var3 = var10000;
               var1.getlocal(0).__setattr__("_Netrc__defuser", var3);
               var3 = null;
               var1.setline(963);
               var10000 = var1.getlocal(10);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("_Netrc__defpasswd");
               }

               var3 = var10000;
               var1.getlocal(0).__setattr__("_Netrc__defpasswd", var3);
               var3 = null;
               var1.setline(964);
               var10000 = var1.getlocal(11);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("_Netrc__defacct");
               }

               var3 = var10000;
               var1.getlocal(0).__setattr__("_Netrc__defacct", var3);
               var3 = null;
            }

            var1.setline(965);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(966);
               var3 = var1.getlocal(8);
               var10000 = var3._in(var1.getlocal(0).__getattr__("_Netrc__hosts"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(967);
                  var3 = var1.getlocal(0).__getattr__("_Netrc__hosts").__getitem__(var1.getlocal(8));
                  PyObject[] var4 = Py.unpackSequence(var3, 3);
                  PyObject var5 = var4[0];
                  var1.setlocal(16, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(17, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(18, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(969);
                  var10000 = var1.getlocal(9);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(16);
                  }

                  var3 = var10000;
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(970);
                  var10000 = var1.getlocal(10);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(17);
                  }

                  var3 = var10000;
                  var1.setlocal(10, var3);
                  var3 = null;
                  var1.setline(971);
                  var10000 = var1.getlocal(11);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(18);
                  }

                  var3 = var10000;
                  var1.setlocal(11, var3);
                  var3 = null;
               }

               var1.setline(972);
               PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)});
               var1.getlocal(0).__getattr__("_Netrc__hosts").__setitem__((PyObject)var1.getlocal(8), var10);
               var3 = null;
            }
         }
      }

      var1.setline(973);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_hosts$64(PyFrame var1, ThreadState var2) {
      var1.setline(976);
      PyString.fromInterned("Return a list of hosts mentioned in the .netrc file.");
      var1.setline(977);
      PyObject var3 = var1.getlocal(0).__getattr__("_Netrc__hosts").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_account$65(PyFrame var1, ThreadState var2) {
      var1.setline(985);
      PyString.fromInterned("Returns login information for the named host.\n\n        The return value is a triple containing userid,\n        password, and the accounting field.\n\n        ");
      var1.setline(986);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(987);
      var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var1.setlocal(3, var3);
      var1.setlocal(4, var3);
      var1.setline(988);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_Netrc__hosts"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(989);
         var3 = var1.getlocal(0).__getattr__("_Netrc__hosts").__getitem__(var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(990);
      var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_Netrc__defuser");
      }

      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(991);
      var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_Netrc__defpasswd");
      }

      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(992);
      var10000 = var1.getlocal(4);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_Netrc__defacct");
      }

      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(993);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject get_macros$66(PyFrame var1, ThreadState var2) {
      var1.setline(996);
      PyString.fromInterned("Return a list of all defined macro names.");
      var1.setline(997);
      PyObject var3 = var1.getlocal(0).__getattr__("_Netrc__macros").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_macro$67(PyFrame var1, ThreadState var2) {
      var1.setline(1000);
      PyString.fromInterned("Return a sequence of lines which define a named macro.");
      var1.setline(1001);
      PyObject var3 = var1.getlocal(0).__getattr__("_Netrc__macros").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$68(PyFrame var1, ThreadState var2) {
      var1.setline(1012);
      PyString.fromInterned("Test program.\n    Usage: ftp [-d] [-r[file]] host [-l[dir]] [-d[dir]] [-p] [file] ...\n\n    -d dir\n    -l list\n    -p password\n    ");
      var1.setline(1014);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("sys").__getattr__("argv"));
      PyObject var10000 = var3._lt(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1015);
         Py.println(var1.getglobal("test").__getattr__("__doc__"));
         var1.setline(1016);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      }

      var1.setline(1018);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(0, var9);
      var3 = null;
      var1.setline(1019);
      var3 = var1.getglobal("None");
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(1020);
         var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
         var10000 = var3._eq(PyString.fromInterned("-d"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1023);
            var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1)).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("-r"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1025);
               var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(1026);
               var1.getglobal("sys").__getattr__("argv").__delitem__((PyObject)Py.newInteger(1));
            }

            var1.setline(1027);
            var3 = var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1028);
            var3 = var1.getglobal("FTP").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1029);
            var1.getlocal(3).__getattr__("set_debuglevel").__call__(var2, var1.getlocal(0));
            var1.setline(1030);
            PyString var13 = PyString.fromInterned("");
            var1.setlocal(4, var13);
            var1.setlocal(5, var13);
            var1.setlocal(6, var13);

            PyObject var4;
            PyObject[] var5;
            label60: {
               try {
                  var1.setline(1032);
                  var3 = var1.getglobal("Netrc").__call__(var2, var1.getlocal(1));
                  var1.setlocal(7, var3);
                  var3 = null;
               } catch (Throwable var8) {
                  PyException var14 = Py.setException(var8, var1);
                  if (var14.match(var1.getglobal("IOError"))) {
                     var1.setline(1034);
                     var4 = var1.getlocal(1);
                     var10000 = var4._isnot(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1035);
                        var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Could not open account file -- using anonymous login."));
                     }
                     break label60;
                  }

                  throw var14;
               }

               try {
                  var1.setline(1039);
                  var4 = var1.getlocal(7).__getattr__("get_account").__call__(var2, var1.getlocal(2));
                  var5 = Py.unpackSequence(var4, 3);
                  PyObject var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var5[2];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var4 = null;
               } catch (Throwable var7) {
                  PyException var10 = Py.setException(var7, var1);
                  if (!var10.match(var1.getglobal("KeyError"))) {
                     throw var10;
                  }

                  var1.setline(1042);
                  var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No account -- using anonymous login."));
               }
            }

            var1.setline(1044);
            var1.getlocal(3).__getattr__("login").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
            var1.setline(1045);
            var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null).__iter__();

            while(true) {
               var1.setline(1045);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1057);
                  var1.getlocal(3).__getattr__("quit").__call__(var2);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(8, var4);
               var1.setline(1046);
               PyObject var11 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
               var10000 = var11._eq(PyString.fromInterned("-l"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1047);
                  var1.getlocal(3).__getattr__("dir").__call__(var2, var1.getlocal(8).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
               } else {
                  var1.setline(1048);
                  var11 = var1.getlocal(8).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
                  var10000 = var11._eq(PyString.fromInterned("-d"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1049);
                     PyString var12 = PyString.fromInterned("CWD");
                     var1.setlocal(9, var12);
                     var5 = null;
                     var1.setline(1050);
                     if (var1.getlocal(8).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null).__nonzero__()) {
                        var1.setline(1050);
                        var11 = var1.getlocal(9)._add(PyString.fromInterned(" "))._add(var1.getlocal(8).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
                        var1.setlocal(9, var11);
                        var5 = null;
                     }

                     var1.setline(1051);
                     var11 = var1.getlocal(3).__getattr__("sendcmd").__call__(var2, var1.getlocal(9));
                     var1.setlocal(10, var11);
                     var5 = null;
                  } else {
                     var1.setline(1052);
                     var11 = var1.getlocal(8);
                     var10000 = var11._eq(PyString.fromInterned("-p"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1053);
                        var1.getlocal(3).__getattr__("set_pasv").__call__(var2, var1.getlocal(3).__getattr__("passiveserver").__not__());
                     } else {
                        var1.setline(1055);
                        var1.getlocal(3).__getattr__("retrbinary").__call__((ThreadState)var2, PyString.fromInterned("RETR ")._add(var1.getlocal(8)), (PyObject)var1.getglobal("sys").__getattr__("stdout").__getattr__("write"), (PyObject)Py.newInteger(1024));
                     }
                  }
               }
            }
         }

         var1.setline(1021);
         var3 = var1.getlocal(0)._add(Py.newInteger(1));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(1022);
         var1.getglobal("sys").__getattr__("argv").__delitem__((PyObject)Py.newInteger(1));
      }
   }

   public ftplib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 63, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error_reply$2 = Py.newCode(0, var2, var1, "error_reply", 64, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error_temp$3 = Py.newCode(0, var2, var1, "error_temp", 65, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error_perm$4 = Py.newCode(0, var2, var1, "error_perm", 66, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error_proto$5 = Py.newCode(0, var2, var1, "error_proto", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FTP$6 = Py.newCode(0, var2, var1, "FTP", 79, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "user", "passwd", "acct", "timeout"};
      __init__$7 = Py.newCode(6, var2, var1, "__init__", 116, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "timeout"};
      connect$8 = Py.newCode(4, var2, var1, "connect", 124, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getwelcome$9 = Py.newCode(1, var2, var1, "getwelcome", 141, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level"};
      set_debuglevel$10 = Py.newCode(2, var2, var1, "set_debuglevel", 148, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val"};
      set_pasv$11 = Py.newCode(2, var2, var1, "set_pasv", 157, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "i"};
      sanitize$12 = Py.newCode(2, var2, var1, "sanitize", 164, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      putline$13 = Py.newCode(2, var2, var1, "putline", 173, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      putcmd$14 = Py.newCode(2, var2, var1, "putcmd", 179, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      getline$15 = Py.newCode(1, var2, var1, "getline", 185, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "code", "nextline"};
      getmultiline$16 = Py.newCode(1, var2, var1, "getmultiline", 200, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "c"};
      getresp$17 = Py.newCode(1, var2, var1, "getresp", 214, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp"};
      voidresp$18 = Py.newCode(1, var2, var1, "voidresp", 227, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "resp"};
      abort$19 = Py.newCode(1, var2, var1, "abort", 234, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      sendcmd$20 = Py.newCode(2, var2, var1, "sendcmd", 246, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      voidcmd$21 = Py.newCode(2, var2, var1, "voidcmd", 251, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "hbytes", "pbytes", "bytes", "cmd"};
      sendport$22 = Py.newCode(3, var2, var1, "sendport", 256, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "af", "fields", "cmd"};
      sendeprt$23 = Py.newCode(3, var2, var1, "sendeprt", 266, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "err", "sock", "res", "af", "socktype", "proto", "canonname", "sa", "port", "host", "resp"};
      makeport$24 = Py.newCode(1, var2, var1, "makeport", 279, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port"};
      makepasv$25 = Py.newCode(1, var2, var1, "makepasv", 310, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "rest", "size", "host", "port", "conn", "resp", "sock", "sockaddr"};
      ntransfercmd$26 = Py.newCode(3, var2, var1, "ntransfercmd", 317, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "rest"};
      transfercmd$27 = Py.newCode(3, var2, var1, "transfercmd", 374, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "passwd", "acct", "resp"};
      login$28 = Py.newCode(4, var2, var1, "login", 378, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "callback", "blocksize", "rest", "conn", "data"};
      retrbinary$29 = Py.newCode(5, var2, var1, "retrbinary", 399, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "callback", "resp", "conn", "fp", "line"};
      retrlines$30 = Py.newCode(3, var2, var1, "retrlines", 423, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "fp", "blocksize", "callback", "rest", "conn", "buf"};
      storbinary$31 = Py.newCode(6, var2, var1, "storbinary", 455, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "fp", "callback", "conn", "buf"};
      storlines$32 = Py.newCode(4, var2, var1, "storlines", 480, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "password", "cmd"};
      acct$33 = Py.newCode(2, var2, var1, "acct", 507, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "cmd", "arg", "files"};
      nlst$34 = Py.newCode(2, var2, var1, "nlst", 512, true, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "cmd", "func", "arg"};
      dir$35 = Py.newCode(2, var2, var1, "dir", 521, true, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fromname", "toname", "resp"};
      rename$36 = Py.newCode(3, var2, var1, "rename", 536, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "resp"};
      delete$37 = Py.newCode(2, var2, var1, "delete", 543, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirname", "msg", "cmd"};
      cwd$38 = Py.newCode(2, var2, var1, "cwd", 551, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "resp", "s"};
      size$39 = Py.newCode(2, var2, var1, "size", 564, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirname", "resp"};
      mkd$40 = Py.newCode(2, var2, var1, "mkd", 575, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirname"};
      rmd$41 = Py.newCode(2, var2, var1, "rmd", 580, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp"};
      pwd$42 = Py.newCode(1, var2, var1, "pwd", 584, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp"};
      quit$43 = Py.newCode(1, var2, var1, "quit", 589, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$44 = Py.newCode(1, var2, var1, "close", 595, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FTP_TLS$45 = Py.newCode(0, var2, var1, "FTP_TLS", 608, false, false, self, 45, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "user", "passwd", "acct", "keyfile", "certfile", "timeout"};
      __init__$46 = Py.newCode(8, var2, var1, "__init__", 643, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "passwd", "acct", "secure"};
      login$47 = Py.newCode(5, var2, var1, "login", 650, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp"};
      auth$48 = Py.newCode(1, var2, var1, "auth", 655, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp"};
      prot_p$49 = Py.newCode(1, var2, var1, "prot_p", 668, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp"};
      prot_c$50 = Py.newCode(1, var2, var1, "prot_c", 684, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "rest", "conn", "size"};
      ntransfercmd$51 = Py.newCode(3, var2, var1, "ntransfercmd", 692, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "callback", "blocksize", "rest", "conn", "data"};
      retrbinary$52 = Py.newCode(5, var2, var1, "retrbinary", 699, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "callback", "resp", "conn", "fp", "line"};
      retrlines$53 = Py.newCode(3, var2, var1, "retrlines", 715, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "fp", "blocksize", "callback", "rest", "conn", "buf"};
      storbinary$54 = Py.newCode(6, var2, var1, "storbinary", 741, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "fp", "callback", "conn", "buf"};
      storlines$55 = Py.newCode(4, var2, var1, "storlines", 757, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"resp", "re", "m", "s"};
      parse150$56 = Py.newCode(1, var2, var1, "parse150", 784, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"resp", "re", "m", "numbers", "host", "port"};
      parse227$57 = Py.newCode(1, var2, var1, "parse227", 807, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"resp", "peer", "left", "right", "parts", "host", "port"};
      parse229$58 = Py.newCode(2, var2, var1, "parse229", 827, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"resp", "dirname", "i", "n", "c"};
      parse257$59 = Py.newCode(1, var2, var1, "parse257", 849, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line"};
      print_line$60 = Py.newCode(1, var2, var1, "print_line", 872, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"source", "sourcename", "target", "targetname", "type", "sourcehost", "sourceport", "treply", "sreply"};
      ftpcp$61 = Py.newCode(5, var2, var1, "ftpcp", 877, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Netrc$62 = Py.newCode(0, var2, var1, "Netrc", 896, false, false, self, 62, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "fp", "in_macro", "line", "macro_lines", "macro_name", "words", "host", "user", "passwd", "acct", "default", "i", "w1", "w2", "ouser", "opasswd", "oacct"};
      __init__$63 = Py.newCode(2, var2, var1, "__init__", 908, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_hosts$64 = Py.newCode(1, var2, var1, "get_hosts", 975, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "user", "passwd", "acct"};
      get_account$65 = Py.newCode(2, var2, var1, "get_account", 979, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_macros$66 = Py.newCode(1, var2, var1, "get_macros", 995, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "macro"};
      get_macro$67 = Py.newCode(2, var2, var1, "get_macro", 999, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"debugging", "rcfile", "host", "ftp", "userid", "passwd", "acct", "netrc", "file", "cmd", "resp"};
      test$68 = Py.newCode(0, var2, var1, "test", 1005, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ftplib$py("ftplib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ftplib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.error_reply$2(var2, var3);
         case 3:
            return this.error_temp$3(var2, var3);
         case 4:
            return this.error_perm$4(var2, var3);
         case 5:
            return this.error_proto$5(var2, var3);
         case 6:
            return this.FTP$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.connect$8(var2, var3);
         case 9:
            return this.getwelcome$9(var2, var3);
         case 10:
            return this.set_debuglevel$10(var2, var3);
         case 11:
            return this.set_pasv$11(var2, var3);
         case 12:
            return this.sanitize$12(var2, var3);
         case 13:
            return this.putline$13(var2, var3);
         case 14:
            return this.putcmd$14(var2, var3);
         case 15:
            return this.getline$15(var2, var3);
         case 16:
            return this.getmultiline$16(var2, var3);
         case 17:
            return this.getresp$17(var2, var3);
         case 18:
            return this.voidresp$18(var2, var3);
         case 19:
            return this.abort$19(var2, var3);
         case 20:
            return this.sendcmd$20(var2, var3);
         case 21:
            return this.voidcmd$21(var2, var3);
         case 22:
            return this.sendport$22(var2, var3);
         case 23:
            return this.sendeprt$23(var2, var3);
         case 24:
            return this.makeport$24(var2, var3);
         case 25:
            return this.makepasv$25(var2, var3);
         case 26:
            return this.ntransfercmd$26(var2, var3);
         case 27:
            return this.transfercmd$27(var2, var3);
         case 28:
            return this.login$28(var2, var3);
         case 29:
            return this.retrbinary$29(var2, var3);
         case 30:
            return this.retrlines$30(var2, var3);
         case 31:
            return this.storbinary$31(var2, var3);
         case 32:
            return this.storlines$32(var2, var3);
         case 33:
            return this.acct$33(var2, var3);
         case 34:
            return this.nlst$34(var2, var3);
         case 35:
            return this.dir$35(var2, var3);
         case 36:
            return this.rename$36(var2, var3);
         case 37:
            return this.delete$37(var2, var3);
         case 38:
            return this.cwd$38(var2, var3);
         case 39:
            return this.size$39(var2, var3);
         case 40:
            return this.mkd$40(var2, var3);
         case 41:
            return this.rmd$41(var2, var3);
         case 42:
            return this.pwd$42(var2, var3);
         case 43:
            return this.quit$43(var2, var3);
         case 44:
            return this.close$44(var2, var3);
         case 45:
            return this.FTP_TLS$45(var2, var3);
         case 46:
            return this.__init__$46(var2, var3);
         case 47:
            return this.login$47(var2, var3);
         case 48:
            return this.auth$48(var2, var3);
         case 49:
            return this.prot_p$49(var2, var3);
         case 50:
            return this.prot_c$50(var2, var3);
         case 51:
            return this.ntransfercmd$51(var2, var3);
         case 52:
            return this.retrbinary$52(var2, var3);
         case 53:
            return this.retrlines$53(var2, var3);
         case 54:
            return this.storbinary$54(var2, var3);
         case 55:
            return this.storlines$55(var2, var3);
         case 56:
            return this.parse150$56(var2, var3);
         case 57:
            return this.parse227$57(var2, var3);
         case 58:
            return this.parse229$58(var2, var3);
         case 59:
            return this.parse257$59(var2, var3);
         case 60:
            return this.print_line$60(var2, var3);
         case 61:
            return this.ftpcp$61(var2, var3);
         case 62:
            return this.Netrc$62(var2, var3);
         case 63:
            return this.__init__$63(var2, var3);
         case 64:
            return this.get_hosts$64(var2, var3);
         case 65:
            return this.get_account$65(var2, var3);
         case 66:
            return this.get_macros$66(var2, var3);
         case 67:
            return this.get_macro$67(var2, var3);
         case 68:
            return this.test$68(var2, var3);
         default:
            return null;
      }
   }
}
