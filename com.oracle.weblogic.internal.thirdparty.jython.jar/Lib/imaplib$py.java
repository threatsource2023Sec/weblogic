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
@Filename("imaplib.py")
public class imaplib$py extends PyFunctionTable implements PyRunnable {
   static imaplib$py self;
   static final PyCode f$0;
   static final PyCode IMAP4$1;
   static final PyCode error$2;
   static final PyCode abort$3;
   static final PyCode readonly$4;
   static final PyCode __init__$5;
   static final PyCode __getattr__$6;
   static final PyCode open$7;
   static final PyCode read$8;
   static final PyCode readline$9;
   static final PyCode send$10;
   static final PyCode shutdown$11;
   static final PyCode socket$12;
   static final PyCode recent$13;
   static final PyCode response$14;
   static final PyCode append$15;
   static final PyCode authenticate$16;
   static final PyCode capability$17;
   static final PyCode check$18;
   static final PyCode close$19;
   static final PyCode copy$20;
   static final PyCode create$21;
   static final PyCode delete$22;
   static final PyCode deleteacl$23;
   static final PyCode expunge$24;
   static final PyCode fetch$25;
   static final PyCode getacl$26;
   static final PyCode getannotation$27;
   static final PyCode getquota$28;
   static final PyCode getquotaroot$29;
   static final PyCode list$30;
   static final PyCode login$31;
   static final PyCode login_cram_md5$32;
   static final PyCode _CRAM_MD5_AUTH$33;
   static final PyCode logout$34;
   static final PyCode lsub$35;
   static final PyCode myrights$36;
   static final PyCode namespace$37;
   static final PyCode noop$38;
   static final PyCode partial$39;
   static final PyCode proxyauth$40;
   static final PyCode rename$41;
   static final PyCode search$42;
   static final PyCode select$43;
   static final PyCode setacl$44;
   static final PyCode setannotation$45;
   static final PyCode setquota$46;
   static final PyCode sort$47;
   static final PyCode status$48;
   static final PyCode store$49;
   static final PyCode subscribe$50;
   static final PyCode thread$51;
   static final PyCode uid$52;
   static final PyCode unsubscribe$53;
   static final PyCode xatom$54;
   static final PyCode _append_untagged$55;
   static final PyCode _check_bye$56;
   static final PyCode _command$57;
   static final PyCode _command_complete$58;
   static final PyCode _get_response$59;
   static final PyCode _get_tagged_response$60;
   static final PyCode _get_line$61;
   static final PyCode _match$62;
   static final PyCode _new_tag$63;
   static final PyCode _checkquote$64;
   static final PyCode _quote$65;
   static final PyCode _simple_command$66;
   static final PyCode _untagged_response$67;
   static final PyCode _mesg$68;
   static final PyCode _dump_ur$69;
   static final PyCode f$70;
   static final PyCode _log$71;
   static final PyCode print_log$72;
   static final PyCode IMAP4_SSL$73;
   static final PyCode __init__$74;
   static final PyCode open$75;
   static final PyCode read$76;
   static final PyCode readline$77;
   static final PyCode send$78;
   static final PyCode shutdown$79;
   static final PyCode socket$80;
   static final PyCode ssl$81;
   static final PyCode IMAP4_stream$82;
   static final PyCode __init__$83;
   static final PyCode open$84;
   static final PyCode read$85;
   static final PyCode readline$86;
   static final PyCode send$87;
   static final PyCode shutdown$88;
   static final PyCode _Authenticator$89;
   static final PyCode __init__$90;
   static final PyCode process$91;
   static final PyCode encode$92;
   static final PyCode decode$93;
   static final PyCode Internaldate2tuple$94;
   static final PyCode Int2AP$95;
   static final PyCode ParseFlags$96;
   static final PyCode Time2Internaldate$97;
   static final PyCode run$98;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("IMAP4 client.\n\nBased on RFC 2060.\n\nPublic class:           IMAP4\nPublic variable:        Debug\nPublic functions:       Internaldate2tuple\n                        Int2AP\n                        ParseFlags\n                        Time2Internaldate\n"));
      var1.setline(11);
      PyString.fromInterned("IMAP4 client.\n\nBased on RFC 2060.\n\nPublic class:           IMAP4\nPublic variable:        Debug\nPublic functions:       Internaldate2tuple\n                        Int2AP\n                        ParseFlags\n                        Time2Internaldate\n");
      var1.setline(23);
      PyString var3 = PyString.fromInterned("2.58");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(25);
      PyObject var10 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var10);
      var3 = null;
      var10 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var10);
      var3 = null;
      var10 = imp.importOne("random", var1, -1);
      var1.setlocal("random", var10);
      var3 = null;
      var10 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var10);
      var3 = null;
      var10 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var10);
      var3 = null;
      var10 = imp.importOne("subprocess", var1, -1);
      var1.setlocal("subprocess", var10);
      var3 = null;
      var10 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var10);
      var3 = null;
      var10 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var10);
      var3 = null;
      var1.setline(27);
      PyList var14 = new PyList(new PyObject[]{PyString.fromInterned("IMAP4"), PyString.fromInterned("IMAP4_stream"), PyString.fromInterned("Internaldate2tuple"), PyString.fromInterned("Int2AP"), PyString.fromInterned("ParseFlags"), PyString.fromInterned("Time2Internaldate")});
      var1.setlocal("__all__", var14);
      var3 = null;
      var1.setline(32);
      var3 = PyString.fromInterned("\r\n");
      var1.setlocal("CRLF", var3);
      var3 = null;
      var1.setline(33);
      PyInteger var15 = Py.newInteger(0);
      var1.setlocal("Debug", var15);
      var3 = null;
      var1.setline(34);
      var15 = Py.newInteger(143);
      var1.setlocal("IMAP4_PORT", var15);
      var3 = null;
      var1.setline(35);
      var15 = Py.newInteger(993);
      var1.setlocal("IMAP4_SSL_PORT", var15);
      var3 = null;
      var1.setline(36);
      PyTuple var17 = new PyTuple(new PyObject[]{PyString.fromInterned("IMAP4REV1"), PyString.fromInterned("IMAP4")});
      var1.setlocal("AllowedVersions", var17);
      var3 = null;
      var1.setline(44);
      var15 = Py.newInteger(10000);
      var1.setlocal("_MAXLINE", var15);
      var3 = null;
      var1.setline(49);
      PyDictionary var18 = new PyDictionary(new PyObject[]{PyString.fromInterned("APPEND"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("AUTHENTICATE"), new PyTuple(new PyObject[]{PyString.fromInterned("NONAUTH")}), PyString.fromInterned("CAPABILITY"), new PyTuple(new PyObject[]{PyString.fromInterned("NONAUTH"), PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED"), PyString.fromInterned("LOGOUT")}), PyString.fromInterned("CHECK"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("CLOSE"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("COPY"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("CREATE"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("DELETE"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("DELETEACL"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("EXAMINE"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("EXPUNGE"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("FETCH"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("GETACL"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("GETANNOTATION"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("GETQUOTA"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("GETQUOTAROOT"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("MYRIGHTS"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("LIST"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("LOGIN"), new PyTuple(new PyObject[]{PyString.fromInterned("NONAUTH")}), PyString.fromInterned("LOGOUT"), new PyTuple(new PyObject[]{PyString.fromInterned("NONAUTH"), PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED"), PyString.fromInterned("LOGOUT")}), PyString.fromInterned("LSUB"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("NAMESPACE"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("NOOP"), new PyTuple(new PyObject[]{PyString.fromInterned("NONAUTH"), PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED"), PyString.fromInterned("LOGOUT")}), PyString.fromInterned("PARTIAL"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("PROXYAUTH"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH")}), PyString.fromInterned("RENAME"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("SEARCH"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("SELECT"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("SETACL"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("SETANNOTATION"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("SETQUOTA"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("SORT"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("STATUS"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("STORE"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("SUBSCRIBE"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")}), PyString.fromInterned("THREAD"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("UID"), new PyTuple(new PyObject[]{PyString.fromInterned("SELECTED")}), PyString.fromInterned("UNSUBSCRIBE"), new PyTuple(new PyObject[]{PyString.fromInterned("AUTH"), PyString.fromInterned("SELECTED")})});
      var1.setlocal("Commands", var18);
      var3 = null;
      var1.setline(93);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\+( (?P<data>.*))?"));
      var1.setlocal("Continuation", var10);
      var3 = null;
      var1.setline(94);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".*FLAGS \\((?P<flags>[^\\)]*)\\)"));
      var1.setlocal("Flags", var10);
      var3 = null;
      var1.setline(95);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".*INTERNALDATE \"(?P<day>[ 0123][0-9])-(?P<mon>[A-Z][a-z][a-z])-(?P<year>[0-9][0-9][0-9][0-9]) (?P<hour>[0-9][0-9]):(?P<min>[0-9][0-9]):(?P<sec>[0-9][0-9]) (?P<zonen>[-+])(?P<zoneh>[0-9][0-9])(?P<zonem>[0-9][0-9])\""));
      var1.setlocal("InternalDate", var10);
      var3 = null;
      var1.setline(100);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".*{(?P<size>\\d+)}$"));
      var1.setlocal("Literal", var10);
      var3 = null;
      var1.setline(101);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\r\\n|\\r|\\n"));
      var1.setlocal("MapCRLF", var10);
      var3 = null;
      var1.setline(102);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\[(?P<type>[A-Z-]+)( (?P<data>[^\\]]*))?\\]"));
      var1.setlocal("Response_code", var10);
      var3 = null;
      var1.setline(103);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\* (?P<type>[A-Z-]+)( (?P<data>.*))?"));
      var1.setlocal("Untagged_response", var10);
      var3 = null;
      var1.setline(104);
      var10 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\* (?P<data>\\d+) (?P<type>[A-Z-]+)( (?P<data2>.*))?"));
      var1.setlocal("Untagged_status", var10);
      var3 = null;
      var1.setline(108);
      PyObject[] var19 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("IMAP4", var19, IMAP4$1);
      var1.setlocal("IMAP4", var4);
      var4 = null;
      Arrays.fill(var19, (Object)null);

      PyObject var5;
      PyObject[] var11;
      PyException var20;
      label126: {
         try {
            var1.setline(1143);
            var10 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var10);
            var3 = null;
         } catch (Throwable var9) {
            var20 = Py.setException(var9, var1);
            if (var20.match(var1.getname("ImportError"))) {
               var1.setline(1145);
               break label126;
            }

            throw var20;
         }

         var1.setline(1147);
         var11 = new PyObject[]{var1.getname("IMAP4")};
         var5 = Py.makeClass("IMAP4_SSL", var11, IMAP4_SSL$73);
         var1.setlocal("IMAP4_SSL", var5);
         var5 = null;
         Arrays.fill(var11, (Object)null);
         var1.setline(1223);
         var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMAP4_SSL"));
      }

      var1.setline(1226);
      var19 = new PyObject[]{var1.getname("IMAP4")};
      var4 = Py.makeClass("IMAP4_stream", var19, IMAP4_stream$82);
      var1.setlocal("IMAP4_stream", var4);
      var4 = null;
      Arrays.fill(var19, (Object)null);
      var1.setline(1283);
      var19 = Py.EmptyObjects;
      var4 = Py.makeClass("_Authenticator", var19, _Authenticator$89);
      var1.setlocal("_Authenticator", var4);
      var4 = null;
      Arrays.fill(var19, (Object)null);
      var1.setline(1327);
      var18 = new PyDictionary(new PyObject[]{PyString.fromInterned("Jan"), Py.newInteger(1), PyString.fromInterned("Feb"), Py.newInteger(2), PyString.fromInterned("Mar"), Py.newInteger(3), PyString.fromInterned("Apr"), Py.newInteger(4), PyString.fromInterned("May"), Py.newInteger(5), PyString.fromInterned("Jun"), Py.newInteger(6), PyString.fromInterned("Jul"), Py.newInteger(7), PyString.fromInterned("Aug"), Py.newInteger(8), PyString.fromInterned("Sep"), Py.newInteger(9), PyString.fromInterned("Oct"), Py.newInteger(10), PyString.fromInterned("Nov"), Py.newInteger(11), PyString.fromInterned("Dec"), Py.newInteger(12)});
      var1.setlocal("Mon2num", var18);
      var3 = null;
      var1.setline(1330);
      var19 = Py.EmptyObjects;
      PyFunction var21 = new PyFunction(var1.f_globals, var19, Internaldate2tuple$94, PyString.fromInterned("Parse an IMAP4 INTERNALDATE string.\n\n    Return corresponding local time.  The return value is a\n    time.struct_time instance or None if the string has wrong format.\n    "));
      var1.setlocal("Internaldate2tuple", var21);
      var3 = null;
      var1.setline(1375);
      var19 = Py.EmptyObjects;
      var21 = new PyFunction(var1.f_globals, var19, Int2AP$95, PyString.fromInterned("Convert integer to A-P string representation."));
      var1.setlocal("Int2AP", var21);
      var3 = null;
      var1.setline(1388);
      var19 = Py.EmptyObjects;
      var21 = new PyFunction(var1.f_globals, var19, ParseFlags$96, PyString.fromInterned("Convert IMAP4 flags response to python tuple."));
      var1.setlocal("ParseFlags", var21);
      var3 = null;
      var1.setline(1399);
      var19 = Py.EmptyObjects;
      var21 = new PyFunction(var1.f_globals, var19, Time2Internaldate$97, PyString.fromInterned("Convert date_time to IMAP4 INTERNALDATE representation.\n\n    Return string in form: '\"DD-Mmm-YYYY HH:MM:SS +HHMM\"'.  The\n    date_time argument can be a number (int or float) representing\n    seconds since epoch (as returned by time.time()), a 9-tuple\n    representing local time (as returned by time.localtime()), or a\n    double-quoted string.  In the last case, it is assumed to already\n    be in the correct format.\n    "));
      var1.setlocal("Time2Internaldate", var21);
      var3 = null;
      var1.setline(1431);
      var10 = var1.getname("__name__");
      PyObject var10000 = var10._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1437);
         var10 = imp.importOne("getopt", var1, -1);
         var1.setlocal("getopt", var10);
         var3 = null;
         var10 = imp.importOne("getpass", var1, -1);
         var1.setlocal("getpass", var10);
         var3 = null;

         PyObject var6;
         PyObject[] var12;
         try {
            var1.setline(1440);
            var10 = var1.getname("getopt").__getattr__("getopt").__call__((ThreadState)var2, (PyObject)var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("d:s:"));
            var11 = Py.unpackSequence(var10, 2);
            var5 = var11[0];
            var1.setlocal("optlist", var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal("args", var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var8) {
            var20 = Py.setException(var8, var1);
            if (!var20.match(var1.getname("getopt").__getattr__("error"))) {
               throw var20;
            }

            var4 = var20.value;
            var1.setlocal("val", var4);
            var4 = null;
            var1.setline(1442);
            PyTuple var13 = new PyTuple(new PyObject[]{new PyTuple(Py.EmptyObjects), new PyTuple(Py.EmptyObjects)});
            var12 = Py.unpackSequence(var13, 2);
            var6 = var12[0];
            var1.setlocal("optlist", var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal("args", var6);
            var6 = null;
            var4 = null;
         }

         var1.setline(1444);
         var10 = var1.getname("None");
         var1.setlocal("stream_command", var10);
         var3 = null;
         var1.setline(1445);
         var10 = var1.getname("optlist").__iter__();

         label107:
         while(true) {
            var1.setline(1445);
            var4 = var10.__iternext__();
            PyTuple var16;
            if (var4 == null) {
               var1.setline(1452);
               if (var1.getname("args").__not__().__nonzero__()) {
                  var1.setline(1452);
                  var17 = new PyTuple(new PyObject[]{PyString.fromInterned("")});
                  var1.setlocal("args", var17);
                  var3 = null;
               }

               var1.setline(1454);
               var10 = var1.getname("args").__getitem__(Py.newInteger(0));
               var1.setlocal("host", var10);
               var3 = null;
               var1.setline(1456);
               var10 = var1.getname("getpass").__getattr__("getuser").__call__(var2);
               var1.setlocal("USER", var10);
               var3 = null;
               var1.setline(1457);
               var10000 = var1.getname("getpass").__getattr__("getpass");
               PyString var10002 = PyString.fromInterned("IMAP password for %s on %s: ");
               PyTuple var10003 = new PyTuple;
               PyObject[] var10005 = new PyObject[]{var1.getname("USER"), null};
               Object var10008 = var1.getname("host");
               if (!((PyObject)var10008).__nonzero__()) {
                  var10008 = PyString.fromInterned("localhost");
               }

               var10005[1] = (PyObject)var10008;
               var10003.<init>(var10005);
               var10 = var10000.__call__(var2, var10002._mod(var10003));
               var1.setlocal("PASSWD", var10);
               var3 = null;
               var1.setline(1459);
               var10 = PyString.fromInterned("From: %(user)s@localhost%(lf)sSubject: IMAP4 test%(lf)s%(lf)sdata...%(lf)s")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("user"), var1.getname("USER"), PyString.fromInterned("lf"), PyString.fromInterned("\n")}));
               var1.setlocal("test_mesg", var10);
               var3 = null;
               var1.setline(1460);
               var17 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("login"), new PyTuple(new PyObject[]{var1.getname("USER"), var1.getname("PASSWD")})}), new PyTuple(new PyObject[]{PyString.fromInterned("create"), new PyTuple(new PyObject[]{PyString.fromInterned("/tmp/xxx 1")})}), new PyTuple(new PyObject[]{PyString.fromInterned("rename"), new PyTuple(new PyObject[]{PyString.fromInterned("/tmp/xxx 1"), PyString.fromInterned("/tmp/yyy")})}), new PyTuple(new PyObject[]{PyString.fromInterned("CREATE"), new PyTuple(new PyObject[]{PyString.fromInterned("/tmp/yyz 2")})}), new PyTuple(new PyObject[]{PyString.fromInterned("append"), new PyTuple(new PyObject[]{PyString.fromInterned("/tmp/yyz 2"), var1.getname("None"), var1.getname("None"), var1.getname("test_mesg")})}), new PyTuple(new PyObject[]{PyString.fromInterned("list"), new PyTuple(new PyObject[]{PyString.fromInterned("/tmp"), PyString.fromInterned("yy*")})}), new PyTuple(new PyObject[]{PyString.fromInterned("select"), new PyTuple(new PyObject[]{PyString.fromInterned("/tmp/yyz 2")})}), new PyTuple(new PyObject[]{PyString.fromInterned("search"), new PyTuple(new PyObject[]{var1.getname("None"), PyString.fromInterned("SUBJECT"), PyString.fromInterned("test")})}), new PyTuple(new PyObject[]{PyString.fromInterned("fetch"), new PyTuple(new PyObject[]{PyString.fromInterned("1"), PyString.fromInterned("(FLAGS INTERNALDATE RFC822)")})}), new PyTuple(new PyObject[]{PyString.fromInterned("store"), new PyTuple(new PyObject[]{PyString.fromInterned("1"), PyString.fromInterned("FLAGS"), PyString.fromInterned("(\\Deleted)")})}), new PyTuple(new PyObject[]{PyString.fromInterned("namespace"), new PyTuple(Py.EmptyObjects)}), new PyTuple(new PyObject[]{PyString.fromInterned("expunge"), new PyTuple(Py.EmptyObjects)}), new PyTuple(new PyObject[]{PyString.fromInterned("recent"), new PyTuple(Py.EmptyObjects)}), new PyTuple(new PyObject[]{PyString.fromInterned("close"), new PyTuple(Py.EmptyObjects)})});
               var1.setlocal("test_seq1", var17);
               var3 = null;
               var1.setline(1477);
               var17 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("select"), new PyTuple(Py.EmptyObjects)}), new PyTuple(new PyObject[]{PyString.fromInterned("response"), new PyTuple(new PyObject[]{PyString.fromInterned("UIDVALIDITY")})}), new PyTuple(new PyObject[]{PyString.fromInterned("uid"), new PyTuple(new PyObject[]{PyString.fromInterned("SEARCH"), PyString.fromInterned("ALL")})}), new PyTuple(new PyObject[]{PyString.fromInterned("response"), new PyTuple(new PyObject[]{PyString.fromInterned("EXISTS")})}), new PyTuple(new PyObject[]{PyString.fromInterned("append"), new PyTuple(new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("test_mesg")})}), new PyTuple(new PyObject[]{PyString.fromInterned("recent"), new PyTuple(Py.EmptyObjects)}), new PyTuple(new PyObject[]{PyString.fromInterned("logout"), new PyTuple(Py.EmptyObjects)})});
               var1.setlocal("test_seq2", var17);
               var3 = null;
               var1.setline(1487);
               var19 = Py.EmptyObjects;
               var21 = new PyFunction(var1.f_globals, var19, run$98, (PyObject)null);
               var1.setlocal("run", var21);
               var3 = null;

               try {
                  var1.setline(1495);
                  if (var1.getname("stream_command").__nonzero__()) {
                     var1.setline(1496);
                     var10 = var1.getname("IMAP4_stream").__call__(var2, var1.getname("stream_command"));
                     var1.setlocal("M", var10);
                     var3 = null;
                  } else {
                     var1.setline(1498);
                     var10 = var1.getname("IMAP4").__call__(var2, var1.getname("host"));
                     var1.setlocal("M", var10);
                     var3 = null;
                  }

                  var1.setline(1499);
                  var10 = var1.getname("M").__getattr__("state");
                  var10000 = var10._eq(PyString.fromInterned("AUTH"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1500);
                     var10 = var1.getname("test_seq1").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                     var1.setlocal("test_seq1", var10);
                     var3 = null;
                  }

                  var1.setline(1501);
                  var1.getname("M").__getattr__("_mesg").__call__(var2, PyString.fromInterned("PROTOCOL_VERSION = %s")._mod(var1.getname("M").__getattr__("PROTOCOL_VERSION")));
                  var1.setline(1502);
                  var1.getname("M").__getattr__("_mesg").__call__(var2, PyString.fromInterned("CAPABILITIES = %r")._mod(new PyTuple(new PyObject[]{var1.getname("M").__getattr__("capabilities")})));
                  var1.setline(1504);
                  var10 = var1.getname("test_seq1").__iter__();

                  while(true) {
                     var1.setline(1504);
                     var4 = var10.__iternext__();
                     if (var4 == null) {
                        var1.setline(1507);
                        var10 = var1.getname("run").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("list"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("/tmp/"), PyString.fromInterned("yy%")}))).__iter__();

                        while(true) {
                           var1.setline(1507);
                           var4 = var10.__iternext__();
                           if (var4 == null) {
                              var1.setline(1513);
                              var10 = var1.getname("test_seq2").__iter__();

                              while(true) {
                                 var1.setline(1513);
                                 var4 = var10.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(1524);
                                    Py.println(PyString.fromInterned("\nAll tests OK."));
                                    break label107;
                                 }

                                 var12 = Py.unpackSequence(var4, 2);
                                 var6 = var12[0];
                                 var1.setlocal("cmd", var6);
                                 var6 = null;
                                 var6 = var12[1];
                                 var1.setlocal("args", var6);
                                 var6 = null;
                                 var1.setline(1514);
                                 var5 = var1.getname("run").__call__(var2, var1.getname("cmd"), var1.getname("args"));
                                 var1.setlocal("dat", var5);
                                 var5 = null;
                                 var1.setline(1516);
                                 var16 = new PyTuple(new PyObject[]{var1.getname("cmd"), var1.getname("args")});
                                 var10000 = var16._ne(new PyTuple(new PyObject[]{PyString.fromInterned("uid"), new PyTuple(new PyObject[]{PyString.fromInterned("SEARCH"), PyString.fromInterned("ALL")})}));
                                 var5 = null;
                                 if (!var10000.__nonzero__()) {
                                    var1.setline(1519);
                                    var5 = var1.getname("dat").__getitem__(Py.newInteger(-1)).__getattr__("split").__call__(var2);
                                    var1.setlocal("uid", var5);
                                    var5 = null;
                                    var1.setline(1520);
                                    if (!var1.getname("uid").__not__().__nonzero__()) {
                                       var1.setline(1521);
                                       var1.getname("run").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("uid"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("FETCH"), PyString.fromInterned("%s")._mod(var1.getname("uid").__getitem__(Py.newInteger(-1))), PyString.fromInterned("(FLAGS INTERNALDATE RFC822.SIZE RFC822.HEADER RFC822.TEXT)")})));
                                    }
                                 }
                              }
                           }

                           var1.setlocal("ml", var4);
                           var1.setline(1508);
                           var5 = var1.getname("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".*\"([^\"]+)\"$"), (PyObject)var1.getname("ml"));
                           var1.setlocal("mo", var5);
                           var5 = null;
                           var1.setline(1509);
                           if (var1.getname("mo").__nonzero__()) {
                              var1.setline(1509);
                              var5 = var1.getname("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                              var1.setlocal("path", var5);
                              var5 = null;
                           } else {
                              var1.setline(1510);
                              var5 = var1.getname("ml").__getattr__("split").__call__(var2).__getitem__(Py.newInteger(-1));
                              var1.setlocal("path", var5);
                              var5 = null;
                           }

                           var1.setline(1511);
                           var1.getname("run").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("delete"), (PyObject)(new PyTuple(new PyObject[]{var1.getname("path")})));
                        }
                     }

                     var12 = Py.unpackSequence(var4, 2);
                     var6 = var12[0];
                     var1.setlocal("cmd", var6);
                     var6 = null;
                     var6 = var12[1];
                     var1.setlocal("args", var6);
                     var6 = null;
                     var1.setline(1505);
                     var1.getname("run").__call__(var2, var1.getname("cmd"), var1.getname("args"));
                  }
               } catch (Throwable var7) {
                  Py.setException(var7, var1);
                  var1.setline(1527);
                  Py.println(PyString.fromInterned("\nTests failed."));
                  var1.setline(1529);
                  if (var1.getname("Debug").__not__().__nonzero__()) {
                     var1.setline(1530);
                     Py.println(PyString.fromInterned("\nIf you would like to see debugging output,\ntry: %s -d5\n")._mod(var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(0))));
                  }

                  var1.setline(1535);
                  throw Py.makeException();
               }
            }

            var12 = Py.unpackSequence(var4, 2);
            var6 = var12[0];
            var1.setlocal("opt", var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal("val", var6);
            var6 = null;
            var1.setline(1446);
            var5 = var1.getname("opt");
            var10000 = var5._eq(PyString.fromInterned("-d"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1447);
               var5 = var1.getname("int").__call__(var2, var1.getname("val"));
               var1.setlocal("Debug", var5);
               var5 = null;
            } else {
               var1.setline(1448);
               var5 = var1.getname("opt");
               var10000 = var5._eq(PyString.fromInterned("-s"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1449);
                  var5 = var1.getname("val");
                  var1.setlocal("stream_command", var5);
                  var5 = null;
                  var1.setline(1450);
                  if (var1.getname("args").__not__().__nonzero__()) {
                     var1.setline(1450);
                     var16 = new PyTuple(new PyObject[]{var1.getname("stream_command")});
                     var1.setlocal("args", var16);
                     var5 = null;
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IMAP4$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("IMAP4 client class.\n\n    Instantiate with: IMAP4([host[, port]])\n\n            host - host's name (default: localhost);\n            port - port number (default: standard IMAP4 port).\n\n    All IMAP4rev1 commands are supported by methods of the same\n    name (in lower-case).\n\n    All arguments to commands are converted to strings, except for\n    AUTHENTICATE, and the last argument to APPEND which is passed as\n    an IMAP4 literal.  If necessary (the string contains any\n    non-printing characters or white-space and isn't enclosed with\n    either parentheses or double quotes) each string is quoted.\n    However, the 'password' argument to the LOGIN command is always\n    quoted.  If you want to avoid having an argument string quoted\n    (eg: the 'flags' argument to STORE) then enclose the string in\n    parentheses (eg: \"(\\Deleted)\").\n\n    Each command returns a tuple: (type, [data, ...]) where 'type'\n    is usually 'OK' or 'NO', and 'data' is either the text from the\n    tagged response, or untagged results from command. Each 'data'\n    is either a string, or a tuple. If a tuple, then the first part\n    is the header of the response, and the second part contains\n    the data (ie: 'literal' value).\n\n    Errors raise the exception class <instance>.error(\"<reason>\").\n    IMAP4 server errors raise <instance>.abort(\"<reason>\"),\n    which is a sub-class of 'error'. Mailbox status changes\n    from READ-WRITE to READ-ONLY raise the exception class\n    <instance>.readonly(\"<reason>\"), which is a sub-class of 'abort'.\n\n    \"error\" exceptions imply a program error.\n    \"abort\" exceptions imply the connection should be reset, and\n            the command re-tried.\n    \"readonly\" exceptions imply the command should be re-tried.\n\n    Note: to use this module, you must read the RFCs pertaining to the\n    IMAP4 protocol, as the semantics of the arguments to each IMAP4\n    command are left to the invoker, not to mention the results. Also,\n    most IMAP servers implement a sub-set of the commands available here.\n    "));
      var1.setline(152);
      PyString.fromInterned("IMAP4 client class.\n\n    Instantiate with: IMAP4([host[, port]])\n\n            host - host's name (default: localhost);\n            port - port number (default: standard IMAP4 port).\n\n    All IMAP4rev1 commands are supported by methods of the same\n    name (in lower-case).\n\n    All arguments to commands are converted to strings, except for\n    AUTHENTICATE, and the last argument to APPEND which is passed as\n    an IMAP4 literal.  If necessary (the string contains any\n    non-printing characters or white-space and isn't enclosed with\n    either parentheses or double quotes) each string is quoted.\n    However, the 'password' argument to the LOGIN command is always\n    quoted.  If you want to avoid having an argument string quoted\n    (eg: the 'flags' argument to STORE) then enclose the string in\n    parentheses (eg: \"(\\Deleted)\").\n\n    Each command returns a tuple: (type, [data, ...]) where 'type'\n    is usually 'OK' or 'NO', and 'data' is either the text from the\n    tagged response, or untagged results from command. Each 'data'\n    is either a string, or a tuple. If a tuple, then the first part\n    is the header of the response, and the second part contains\n    the data (ie: 'literal' value).\n\n    Errors raise the exception class <instance>.error(\"<reason>\").\n    IMAP4 server errors raise <instance>.abort(\"<reason>\"),\n    which is a sub-class of 'error'. Mailbox status changes\n    from READ-WRITE to READ-ONLY raise the exception class\n    <instance>.readonly(\"<reason>\"), which is a sub-class of 'abort'.\n\n    \"error\" exceptions imply a program error.\n    \"abort\" exceptions imply the connection should be reset, and\n            the command re-tried.\n    \"readonly\" exceptions imply the command should be re-tried.\n\n    Note: to use this module, you must read the RFCs pertaining to the\n    IMAP4 protocol, as the semantics of the arguments to each IMAP4\n    command are left to the invoker, not to mention the results. Also,\n    most IMAP servers implement a sub-set of the commands available here.\n    ");
      var1.setline(154);
      PyObject[] var3 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("error", var3, error$2);
      var1.setlocal("error", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(155);
      var3 = new PyObject[]{var1.getname("error")};
      var4 = Py.makeClass("abort", var3, abort$3);
      var1.setlocal("abort", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(156);
      var3 = new PyObject[]{var1.getname("abort")};
      var4 = Py.makeClass("readonly", var3, readonly$4);
      var1.setlocal("readonly", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(158);
      PyObject var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[^\\w!#$%&'*+,.:;<=>?^`|~-]"));
      var1.setlocal("mustquote", var5);
      var3 = null;
      var1.setline(160);
      var3 = new PyObject[]{PyString.fromInterned(""), var1.getname("IMAP4_PORT")};
      PyFunction var6 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(219);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, __getattr__$6, (PyObject)null);
      var1.setlocal("__getattr__", var6);
      var3 = null;
      var1.setline(230);
      var3 = new PyObject[]{PyString.fromInterned(""), var1.getname("IMAP4_PORT")};
      var6 = new PyFunction(var1.f_globals, var3, open$7, PyString.fromInterned("Setup connection to remote server on \"host:port\"\n            (default: localhost:standard IMAP4 port).\n        This connection will be used by the routines:\n            read, readline, send, shutdown.\n        "));
      var1.setlocal("open", var6);
      var3 = null;
      var1.setline(242);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, read$8, PyString.fromInterned("Read 'size' bytes from remote."));
      var1.setlocal("read", var6);
      var3 = null;
      var1.setline(247);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, readline$9, PyString.fromInterned("Read line from remote."));
      var1.setlocal("readline", var6);
      var3 = null;
      var1.setline(255);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, send$10, PyString.fromInterned("Send data to remote."));
      var1.setlocal("send", var6);
      var3 = null;
      var1.setline(260);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, shutdown$11, PyString.fromInterned("Close I/O established in \"open\"."));
      var1.setlocal("shutdown", var6);
      var3 = null;
      var1.setline(273);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, socket$12, PyString.fromInterned("Return socket instance used to connect to IMAP4 server.\n\n        socket = <instance>.socket()\n        "));
      var1.setlocal("socket", var6);
      var3 = null;
      var1.setline(285);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, recent$13, PyString.fromInterned("Return most recent 'RECENT' responses if any exist,\n        else prompt server for an update using the 'NOOP' command.\n\n        (typ, [data]) = <instance>.recent()\n\n        'data' is None if no new messages,\n        else list of RECENT responses, most recent last.\n        "));
      var1.setlocal("recent", var6);
      var3 = null;
      var1.setline(302);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, response$14, PyString.fromInterned("Return data for response 'code' if received, or None.\n\n        Old value for response 'code' is cleared.\n\n        (code, [data]) = <instance>.response(code)\n        "));
      var1.setlocal("response", var6);
      var3 = null;
      var1.setline(316);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, append$15, PyString.fromInterned("Append message to named mailbox.\n\n        (typ, [data]) = <instance>.append(mailbox, flags, date_time, message)\n\n                All args except `message' can be None.\n        "));
      var1.setlocal("append", var6);
      var3 = null;
      var1.setline(339);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, authenticate$16, PyString.fromInterned("Authenticate command - requires response processing.\n\n        'mechanism' specifies which authentication mechanism is to\n        be used - it must appear in <instance>.capabilities in the\n        form AUTH=<mechanism>.\n\n        'authobject' must be a callable object:\n\n                data = authobject(response)\n\n        It will be called to process server continuation responses.\n        It should return data that will be encoded and sent to server.\n        It should return None if the client abort response '*' should\n        be sent instead.\n        "));
      var1.setlocal("authenticate", var6);
      var3 = null;
      var1.setline(368);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, capability$17, PyString.fromInterned("(typ, [data]) = <instance>.capability()\n        Fetch capabilities list from server."));
      var1.setlocal("capability", var6);
      var3 = null;
      var1.setline(377);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, check$18, PyString.fromInterned("Checkpoint mailbox on server.\n\n        (typ, [data]) = <instance>.check()\n        "));
      var1.setlocal("check", var6);
      var3 = null;
      var1.setline(385);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, close$19, PyString.fromInterned("Close currently selected mailbox.\n\n        Deleted messages are removed from writable mailbox.\n        This is the recommended command before 'LOGOUT'.\n\n        (typ, [data]) = <instance>.close()\n        "));
      var1.setlocal("close", var6);
      var3 = null;
      var1.setline(400);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, copy$20, PyString.fromInterned("Copy 'message_set' messages onto end of 'new_mailbox'.\n\n        (typ, [data]) = <instance>.copy(message_set, new_mailbox)\n        "));
      var1.setlocal("copy", var6);
      var3 = null;
      var1.setline(408);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, create$21, PyString.fromInterned("Create new mailbox.\n\n        (typ, [data]) = <instance>.create(mailbox)\n        "));
      var1.setlocal("create", var6);
      var3 = null;
      var1.setline(416);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, delete$22, PyString.fromInterned("Delete old mailbox.\n\n        (typ, [data]) = <instance>.delete(mailbox)\n        "));
      var1.setlocal("delete", var6);
      var3 = null;
      var1.setline(423);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, deleteacl$23, PyString.fromInterned("Delete the ACLs (remove any rights) set for who on mailbox.\n\n        (typ, [data]) = <instance>.deleteacl(mailbox, who)\n        "));
      var1.setlocal("deleteacl", var6);
      var3 = null;
      var1.setline(430);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, expunge$24, PyString.fromInterned("Permanently remove deleted items from selected mailbox.\n\n        Generates 'EXPUNGE' response for each deleted message.\n\n        (typ, [data]) = <instance>.expunge()\n\n        'data' is list of 'EXPUNGE'd message numbers in order received.\n        "));
      var1.setlocal("expunge", var6);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, fetch$25, PyString.fromInterned("Fetch (parts of) messages.\n\n        (typ, [data, ...]) = <instance>.fetch(message_set, message_parts)\n\n        'message_parts' should be a string of selected parts\n        enclosed in parentheses, eg: \"(UID BODY[TEXT])\".\n\n        'data' are tuples of message part envelope and data.\n        "));
      var1.setlocal("fetch", var6);
      var3 = null;
      var1.setline(459);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, getacl$26, PyString.fromInterned("Get the ACLs for a mailbox.\n\n        (typ, [data]) = <instance>.getacl(mailbox)\n        "));
      var1.setlocal("getacl", var6);
      var3 = null;
      var1.setline(468);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, getannotation$27, PyString.fromInterned("(typ, [data]) = <instance>.getannotation(mailbox, entry, attribute)\n        Retrieve ANNOTATIONs."));
      var1.setlocal("getannotation", var6);
      var3 = null;
      var1.setline(476);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, getquota$28, PyString.fromInterned("Get the quota root's resource usage and limits.\n\n        Part of the IMAP4 QUOTA extension defined in rfc2087.\n\n        (typ, [data]) = <instance>.getquota(root)\n        "));
      var1.setlocal("getquota", var6);
      var3 = null;
      var1.setline(487);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, getquotaroot$29, PyString.fromInterned("Get the list of quota roots for the named mailbox.\n\n        (typ, [[QUOTAROOT responses...], [QUOTA responses]]) = <instance>.getquotaroot(mailbox)\n        "));
      var1.setlocal("getquotaroot", var6);
      var3 = null;
      var1.setline(498);
      var3 = new PyObject[]{PyString.fromInterned("\"\""), PyString.fromInterned("*")};
      var6 = new PyFunction(var1.f_globals, var3, list$30, PyString.fromInterned("List mailbox names in directory matching pattern.\n\n        (typ, [data]) = <instance>.list(directory='\"\"', pattern='*')\n\n        'data' is list of LIST responses.\n        "));
      var1.setlocal("list", var6);
      var3 = null;
      var1.setline(510);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, login$31, PyString.fromInterned("Identify client using plaintext password.\n\n        (typ, [data]) = <instance>.login(user, password)\n\n        NB: 'password' will be quoted.\n        "));
      var1.setlocal("login", var6);
      var3 = null;
      var1.setline(524);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, login_cram_md5$32, PyString.fromInterned(" Force use of CRAM-MD5 authentication.\n\n        (typ, [data]) = <instance>.login_cram_md5(user, password)\n        "));
      var1.setlocal("login_cram_md5", var6);
      var3 = null;
      var1.setline(533);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _CRAM_MD5_AUTH$33, PyString.fromInterned(" Authobject to use with CRAM-MD5 authentication. "));
      var1.setlocal("_CRAM_MD5_AUTH", var6);
      var3 = null;
      var1.setline(539);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, logout$34, PyString.fromInterned("Shutdown connection to server.\n\n        (typ, [data]) = <instance>.logout()\n\n        Returns server 'BYE' response.\n        "));
      var1.setlocal("logout", var6);
      var3 = null;
      var1.setline(555);
      var3 = new PyObject[]{PyString.fromInterned("\"\""), PyString.fromInterned("*")};
      var6 = new PyFunction(var1.f_globals, var3, lsub$35, PyString.fromInterned("List 'subscribed' mailbox names in directory matching pattern.\n\n        (typ, [data, ...]) = <instance>.lsub(directory='\"\"', pattern='*')\n\n        'data' are tuples of message part envelope and data.\n        "));
      var1.setlocal("lsub", var6);
      var3 = null;
      var1.setline(566);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, myrights$36, PyString.fromInterned("Show my ACLs for a mailbox (i.e. the rights that I have on mailbox).\n\n        (typ, [data]) = <instance>.myrights(mailbox)\n        "));
      var1.setlocal("myrights", var6);
      var3 = null;
      var1.setline(574);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, namespace$37, PyString.fromInterned(" Returns IMAP namespaces ala rfc2342\n\n        (typ, [data, ...]) = <instance>.namespace()\n        "));
      var1.setlocal("namespace", var6);
      var3 = null;
      var1.setline(584);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, noop$38, PyString.fromInterned("Send NOOP command.\n\n        (typ, [data]) = <instance>.noop()\n        "));
      var1.setlocal("noop", var6);
      var3 = null;
      var1.setline(595);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, partial$39, PyString.fromInterned("Fetch truncated part of a message.\n\n        (typ, [data, ...]) = <instance>.partial(message_num, message_part, start, length)\n\n        'data' is tuple of message part envelope and data.\n        "));
      var1.setlocal("partial", var6);
      var3 = null;
      var1.setline(607);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, proxyauth$40, PyString.fromInterned("Assume authentication as \"user\".\n\n        Allows an authorised administrator to proxy into any user's\n        mailbox.\n\n        (typ, [data]) = <instance>.proxyauth(user)\n        "));
      var1.setlocal("proxyauth", var6);
      var3 = null;
      var1.setline(620);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, rename$41, PyString.fromInterned("Rename old mailbox name to new.\n\n        (typ, [data]) = <instance>.rename(oldmailbox, newmailbox)\n        "));
      var1.setlocal("rename", var6);
      var3 = null;
      var1.setline(628);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, search$42, PyString.fromInterned("Search mailbox for matching messages.\n\n        (typ, [data]) = <instance>.search(charset, criterion, ...)\n\n        'data' is space separated list of matching message numbers.\n        "));
      var1.setlocal("search", var6);
      var3 = null;
      var1.setline(643);
      var3 = new PyObject[]{PyString.fromInterned("INBOX"), var1.getname("False")};
      var6 = new PyFunction(var1.f_globals, var3, select$43, PyString.fromInterned("Select a mailbox.\n\n        Flush all untagged responses.\n\n        (typ, [data]) = <instance>.select(mailbox='INBOX', readonly=False)\n\n        'data' is count of messages in mailbox ('EXISTS' response).\n\n        Mandated responses are ('FLAGS', 'EXISTS', 'RECENT', 'UIDVALIDITY'), so\n        other responses should be obtained via <instance>.response('FLAGS') etc.\n        "));
      var1.setlocal("select", var6);
      var3 = null;
      var1.setline(675);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, setacl$44, PyString.fromInterned("Set a mailbox acl.\n\n        (typ, [data]) = <instance>.setacl(mailbox, who, what)\n        "));
      var1.setlocal("setacl", var6);
      var3 = null;
      var1.setline(683);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, setannotation$45, PyString.fromInterned("(typ, [data]) = <instance>.setannotation(mailbox[, entry, attribute]+)\n        Set ANNOTATIONs."));
      var1.setlocal("setannotation", var6);
      var3 = null;
      var1.setline(691);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, setquota$46, PyString.fromInterned("Set the quota root's resource limits.\n\n        (typ, [data]) = <instance>.setquota(root, limits)\n        "));
      var1.setlocal("setquota", var6);
      var3 = null;
      var1.setline(700);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, sort$47, PyString.fromInterned("IMAP4rev1 extension SORT command.\n\n        (typ, [data]) = <instance>.sort(sort_criteria, charset, search_criteria, ...)\n        "));
      var1.setlocal("sort", var6);
      var3 = null;
      var1.setline(714);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, status$48, PyString.fromInterned("Request named status conditions for mailbox.\n\n        (typ, [data]) = <instance>.status(mailbox, names)\n        "));
      var1.setlocal("status", var6);
      var3 = null;
      var1.setline(726);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, store$49, PyString.fromInterned("Alters flag dispositions for messages in mailbox.\n\n        (typ, [data]) = <instance>.store(message_set, command, flags)\n        "));
      var1.setlocal("store", var6);
      var3 = null;
      var1.setline(737);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, subscribe$50, PyString.fromInterned("Subscribe to new mailbox.\n\n        (typ, [data]) = <instance>.subscribe(mailbox)\n        "));
      var1.setlocal("subscribe", var6);
      var3 = null;
      var1.setline(745);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, thread$51, PyString.fromInterned("IMAPrev1 extension THREAD command.\n\n        (type, [data]) = <instance>.thread(threading_algorithm, charset, search_criteria, ...)\n        "));
      var1.setlocal("thread", var6);
      var3 = null;
      var1.setline(755);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, uid$52, PyString.fromInterned("Execute \"command arg ...\" with messages identified by UID,\n                rather than message number.\n\n        (typ, [data]) = <instance>.uid(command, arg1, arg2, ...)\n\n        Returns response appropriate to 'command'.\n        "));
      var1.setlocal("uid", var6);
      var3 = null;
      var1.setline(780);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, unsubscribe$53, PyString.fromInterned("Unsubscribe from old mailbox.\n\n        (typ, [data]) = <instance>.unsubscribe(mailbox)\n        "));
      var1.setlocal("unsubscribe", var6);
      var3 = null;
      var1.setline(788);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, xatom$54, PyString.fromInterned("Allow simple extension commands\n                notified by server in CAPABILITY response.\n\n        Assumes command is legal in current state.\n\n        (typ, [data]) = <instance>.xatom(name, arg, ...)\n\n        Returns response appropriate to extension command `name'.\n        "));
      var1.setlocal("xatom", var6);
      var3 = null;
      var1.setline(810);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _append_untagged$55, (PyObject)null);
      var1.setlocal("_append_untagged", var6);
      var3 = null;
      var1.setline(824);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _check_bye$56, (PyObject)null);
      var1.setlocal("_check_bye", var6);
      var3 = null;
      var1.setline(830);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _command$57, (PyObject)null);
      var1.setlocal("_command", var6);
      var3 = null;
      var1.setline(904);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _command_complete$58, (PyObject)null);
      var1.setlocal("_command_complete", var6);
      var3 = null;
      var1.setline(921);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _get_response$59, (PyObject)null);
      var1.setlocal("_get_response", var6);
      var3 = null;
      var1.setline(997);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _get_tagged_response$60, (PyObject)null);
      var1.setlocal("_get_tagged_response", var6);
      var3 = null;
      var1.setline(1024);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _get_line$61, (PyObject)null);
      var1.setlocal("_get_line", var6);
      var3 = null;
      var1.setline(1043);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _match$62, (PyObject)null);
      var1.setlocal("_match", var6);
      var3 = null;
      var1.setline(1055);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _new_tag$63, (PyObject)null);
      var1.setlocal("_new_tag", var6);
      var3 = null;
      var1.setline(1063);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _checkquote$64, (PyObject)null);
      var1.setlocal("_checkquote", var6);
      var3 = null;
      var1.setline(1077);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _quote$65, (PyObject)null);
      var1.setlocal("_quote", var6);
      var3 = null;
      var1.setline(1085);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _simple_command$66, (PyObject)null);
      var1.setlocal("_simple_command", var6);
      var3 = null;
      var1.setline(1090);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, _untagged_response$67, (PyObject)null);
      var1.setlocal("_untagged_response", var6);
      var3 = null;
      var1.setline(1103);
      if (var1.getname("__debug__").__nonzero__()) {
         var1.setline(1105);
         var3 = new PyObject[]{var1.getname("None")};
         var6 = new PyFunction(var1.f_globals, var3, _mesg$68, (PyObject)null);
         var1.setlocal("_mesg", var6);
         var3 = null;
         var1.setline(1112);
         var3 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var3, _dump_ur$69, (PyObject)null);
         var1.setlocal("_dump_ur", var6);
         var3 = null;
         var1.setline(1120);
         var3 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var3, _log$71, (PyObject)null);
         var1.setlocal("_log", var6);
         var3 = null;
         var1.setline(1127);
         var3 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var3, print_log$72, (PyObject)null);
         var1.setlocal("print_log", var6);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject error$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(154);
      return var1.getf_locals();
   }

   public PyObject abort$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(155);
      return var1.getf_locals();
   }

   public PyObject readonly$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(156);
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyObject var3 = var1.getglobal("Debug");
      var1.getlocal(0).__setattr__("debug", var3);
      var3 = null;
      var1.setline(162);
      PyString var6 = PyString.fromInterned("LOGOUT");
      var1.getlocal(0).__setattr__((String)"state", var6);
      var3 = null;
      var1.setline(163);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("literal", var3);
      var3 = null;
      var1.setline(164);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"tagged_commands", var8);
      var3 = null;
      var1.setline(165);
      var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"untagged_responses", var8);
      var3 = null;
      var1.setline(166);
      var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"continuation_response", var6);
      var3 = null;
      var1.setline(167);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("is_readonly", var3);
      var3 = null;
      var1.setline(168);
      PyInteger var9 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"tagnum", var9);
      var3 = null;
      var1.setline(172);
      var1.getlocal(0).__getattr__("open").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(177);
      var3 = var1.getglobal("Int2AP").__call__(var2, var1.getglobal("random").__getattr__("randint").__call__((ThreadState)var2, (PyObject)Py.newInteger(4096), (PyObject)Py.newInteger(65535)));
      var1.getlocal(0).__setattr__("tagpre", var3);
      var3 = null;
      var1.setline(178);
      var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("(?P<tag>")._add(var1.getlocal(0).__getattr__("tagpre"))._add(PyString.fromInterned("\\d+) (?P<type>[A-Z]+) (?P<data>.*)")));
      var1.getlocal(0).__setattr__("tagre", var3);
      var3 = null;
      var1.setline(185);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var1.setline(186);
         var9 = Py.newInteger(10);
         var1.getlocal(0).__setattr__((String)"_cmd_log_len", var9);
         var3 = null;
         var1.setline(187);
         var9 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_cmd_log_idx", var9);
         var3 = null;
         var1.setline(188);
         var8 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_cmd_log", var8);
         var3 = null;
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("debug");
         var10000 = var3._ge(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("imaplib version %s")._mod(var1.getglobal("__version__")));
            var1.setline(191);
            var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("new IMAP4 connection, tag=%s")._mod(var1.getlocal(0).__getattr__("tagpre")));
         }
      }

      var1.setline(193);
      var3 = var1.getlocal(0).__getattr__("_get_response").__call__(var2);
      var1.getlocal(0).__setattr__("welcome", var3);
      var3 = null;
      var1.setline(194);
      var6 = PyString.fromInterned("PREAUTH");
      var10000 = var6._in(var1.getlocal(0).__getattr__("untagged_responses"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(195);
         var6 = PyString.fromInterned("AUTH");
         var1.getlocal(0).__setattr__((String)"state", var6);
         var3 = null;
      } else {
         var1.setline(196);
         var6 = PyString.fromInterned("OK");
         var10000 = var6._in(var1.getlocal(0).__getattr__("untagged_responses"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(199);
            throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, var1.getlocal(0).__getattr__("welcome")));
         }

         var1.setline(197);
         var6 = PyString.fromInterned("NONAUTH");
         var1.getlocal(0).__setattr__((String)"state", var6);
         var3 = null;
      }

      var1.setline(201);
      var3 = var1.getlocal(0).__getattr__("capability").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(202);
      var3 = var1.getlocal(4);
      var10000 = var3._eq(new PyList(new PyObject[]{var1.getglobal("None")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(203);
         throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no CAPABILITY response from server")));
      } else {
         var1.setline(204);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(-1)).__getattr__("upper").__call__(var2).__getattr__("split").__call__(var2));
         var1.getlocal(0).__setattr__("capabilities", var3);
         var3 = null;
         var1.setline(206);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var1.setline(207);
            var3 = var1.getlocal(0).__getattr__("debug");
            var10000 = var3._ge(Py.newInteger(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(208);
               var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("CAPABILITIES: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("capabilities")})));
            }
         }

         var1.setline(210);
         var3 = var1.getglobal("AllowedVersions").__iter__();

         do {
            var1.setline(210);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               var1.setline(216);
               throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("server not IMAP4 compliant")));
            }

            var1.setlocal(5, var7);
            var1.setline(211);
            var5 = var1.getlocal(5);
            var10000 = var5._in(var1.getlocal(0).__getattr__("capabilities"));
            var5 = null;
         } while(var10000.__not__().__nonzero__());

         var1.setline(213);
         var5 = var1.getlocal(5);
         var1.getlocal(0).__setattr__("PROTOCOL_VERSION", var5);
         var5 = null;
         var1.setline(214);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __getattr__$6(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("Commands"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(222);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getattr__("lower").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(223);
         throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("Unknown IMAP4 command: '%s'")._mod(var1.getlocal(1))));
      }
   }

   public PyObject open$7(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyString.fromInterned("Setup connection to remote server on \"host:port\"\n            (default: localhost:standard IMAP4 port).\n        This connection will be used by the routines:\n            read, readline, send, shutdown.\n        ");
      var1.setline(236);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(238);
      var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(239);
      var3 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$8(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      PyString.fromInterned("Read 'size' bytes from remote.");
      var1.setline(244);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$9(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("Read line from remote.");
      var1.setline(249);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(250);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(var1.getglobal("_MAXLINE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(251);
         throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("got more than %d bytes")._mod(var1.getglobal("_MAXLINE"))));
      } else {
         var1.setline(252);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject send$10(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyString.fromInterned("Send data to remote.");
      var1.setline(257);
      var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown$11(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyString.fromInterned("Close I/O established in \"open\".");
      var1.setline(262);
      var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
      Object var3 = null;

      try {
         try {
            var1.setline(264);
            var1.getlocal(0).__getattr__("sock").__getattr__("shutdown").__call__(var2, var1.getglobal("socket").__getattr__("SHUT_RDWR"));
         } catch (Throwable var6) {
            PyException var4 = Py.setException(var6, var1);
            if (!var4.match(var1.getglobal("socket").__getattr__("error"))) {
               throw var4;
            }

            PyObject var5 = var4.value;
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(267);
            var5 = var1.getlocal(1).__getattr__("errno");
            PyObject var10000 = var5._ne(var1.getglobal("errno").__getattr__("ENOTCONN"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(268);
               throw Py.makeException();
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(270);
         var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(270);
      var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject socket$12(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyString.fromInterned("Return socket instance used to connect to IMAP4 server.\n\n        socket = <instance>.socket()\n        ");
      var1.setline(278);
      PyObject var3 = var1.getlocal(0).__getattr__("sock");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject recent$13(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyString.fromInterned("Return most recent 'RECENT' responses if any exist,\n        else prompt server for an update using the 'NOOP' command.\n\n        (typ, [data]) = <instance>.recent()\n\n        'data' is None if no new messages,\n        else list of RECENT responses, most recent last.\n        ");
      var1.setline(294);
      PyString var3 = PyString.fromInterned("RECENT");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(295);
      PyObject var7 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, PyString.fromInterned("OK"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("None")})), (PyObject)var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(296);
      if (var1.getlocal(3).__getitem__(Py.newInteger(-1)).__nonzero__()) {
         var1.setline(297);
         PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(298);
         PyObject var8 = var1.getlocal(0).__getattr__("noop").__call__(var2);
         PyObject[] var10 = Py.unpackSequence(var8, 2);
         PyObject var6 = var10[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;
         var1.setline(299);
         var7 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject response$14(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyString.fromInterned("Return data for response 'code' if received, or None.\n\n        Old value for response 'code' is cleared.\n\n        (code, [data]) = <instance>.response(code)\n        ");
      var1.setline(309);
      PyObject var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)(new PyList(new PyObject[]{var1.getglobal("None")})), (PyObject)var1.getlocal(1).__getattr__("upper").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject append$15(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyString.fromInterned("Append message to named mailbox.\n\n        (typ, [data]) = <instance>.append(mailbox, flags, date_time, message)\n\n                All args except `message' can be None.\n        ");
      var1.setline(323);
      PyString var3 = PyString.fromInterned("APPEND");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(324);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(325);
         var3 = PyString.fromInterned("INBOX");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(326);
      PyObject var5;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(327);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(Py.newInteger(-1))});
         PyObject var10000 = var4._ne(new PyTuple(new PyObject[]{PyString.fromInterned("("), PyString.fromInterned(")")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(328);
            var5 = PyString.fromInterned("(%s)")._mod(var1.getlocal(2));
            var1.setlocal(2, var5);
            var3 = null;
         }
      } else {
         var1.setline(330);
         var5 = var1.getglobal("None");
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(331);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(332);
         var5 = var1.getglobal("Time2Internaldate").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var5);
         var3 = null;
      } else {
         var1.setline(334);
         var5 = var1.getglobal("None");
         var1.setlocal(3, var5);
         var3 = null;
      }

      var1.setline(335);
      var5 = var1.getglobal("MapCRLF").__getattr__("sub").__call__(var2, var1.getglobal("CRLF"), var1.getlocal(4));
      var1.getlocal(0).__setattr__("literal", var5);
      var3 = null;
      var1.setline(336);
      var5 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(5), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject authenticate$16(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      PyString.fromInterned("Authenticate command - requires response processing.\n\n        'mechanism' specifies which authentication mechanism is to\n        be used - it must appear in <instance>.capabilities in the\n        form AUTH=<mechanism>.\n\n        'authobject' must be a callable object:\n\n                data = authobject(response)\n\n        It will be called to process server continuation responses.\n        It should return data that will be encoded and sent to server.\n        It should return None if the client abort response '*' should\n        be sent instead.\n        ");
      var1.setline(355);
      PyObject var3 = var1.getlocal(1).__getattr__("upper").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(360);
      var3 = var1.getglobal("_Authenticator").__call__(var2, var1.getlocal(2)).__getattr__("process");
      var1.getlocal(0).__setattr__("literal", var3);
      var3 = null;
      var1.setline(361);
      var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AUTHENTICATE"), (PyObject)var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(362);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._ne(PyString.fromInterned("OK"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(363);
         throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(-1))));
      } else {
         var1.setline(364);
         PyString var6 = PyString.fromInterned("AUTH");
         var1.getlocal(0).__setattr__((String)"state", var6);
         var3 = null;
         var1.setline(365);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject capability$17(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyString.fromInterned("(typ, [data]) = <instance>.capability()\n        Fetch capabilities list from server.");
      var1.setline(372);
      PyString var3 = PyString.fromInterned("CAPABILITY");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(373);
      PyObject var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(374);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject check$18(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyString.fromInterned("Checkpoint mailbox on server.\n\n        (typ, [data]) = <instance>.check()\n        ");
      var1.setline(382);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CHECK"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$19(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyString.fromInterned("Close currently selected mailbox.\n\n        Deleted messages are removed from writable mailbox.\n        This is the recommended command before 'LOGOUT'.\n\n        (typ, [data]) = <instance>.close()\n        ");
      PyTuple var3 = null;

      PyString var4;
      try {
         var1.setline(394);
         PyObject var8 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CLOSE"));
         PyObject[] var5 = Py.unpackSequence(var8, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(396);
         var4 = PyString.fromInterned("AUTH");
         var1.getlocal(0).__setattr__((String)"state", var4);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(396);
      var4 = PyString.fromInterned("AUTH");
      var1.getlocal(0).__setattr__((String)"state", var4);
      var4 = null;
      var1.setline(397);
      var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject copy$20(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyString.fromInterned("Copy 'message_set' messages onto end of 'new_mailbox'.\n\n        (typ, [data]) = <instance>.copy(message_set, new_mailbox)\n        ");
      var1.setline(405);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, PyString.fromInterned("COPY"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject create$21(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyString.fromInterned("Create new mailbox.\n\n        (typ, [data]) = <instance>.create(mailbox)\n        ");
      var1.setline(413);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CREATE"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject delete$22(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      PyString.fromInterned("Delete old mailbox.\n\n        (typ, [data]) = <instance>.delete(mailbox)\n        ");
      var1.setline(421);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject deleteacl$23(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      PyString.fromInterned("Delete the ACLs (remove any rights) set for who on mailbox.\n\n        (typ, [data]) = <instance>.deleteacl(mailbox, who)\n        ");
      var1.setline(428);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, PyString.fromInterned("DELETEACL"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject expunge$24(PyFrame var1, ThreadState var2) {
      var1.setline(438);
      PyString.fromInterned("Permanently remove deleted items from selected mailbox.\n\n        Generates 'EXPUNGE' response for each deleted message.\n\n        (typ, [data]) = <instance>.expunge()\n\n        'data' is list of 'EXPUNGE'd message numbers in order received.\n        ");
      var1.setline(439);
      PyString var3 = PyString.fromInterned("EXPUNGE");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(440);
      PyObject var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(441);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject fetch$25(PyFrame var1, ThreadState var2) {
      var1.setline(453);
      PyString.fromInterned("Fetch (parts of) messages.\n\n        (typ, [data, ...]) = <instance>.fetch(message_set, message_parts)\n\n        'message_parts' should be a string of selected parts\n        enclosed in parentheses, eg: \"(UID BODY[TEXT])\".\n\n        'data' are tuples of message part envelope and data.\n        ");
      var1.setline(454);
      PyString var3 = PyString.fromInterned("FETCH");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(455);
      PyObject var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(456);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(3));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getacl$26(PyFrame var1, ThreadState var2) {
      var1.setline(463);
      PyString.fromInterned("Get the ACLs for a mailbox.\n\n        (typ, [data]) = <instance>.getacl(mailbox)\n        ");
      var1.setline(464);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GETACL"), (PyObject)var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(465);
      var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("ACL"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getannotation$27(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("(typ, [data]) = <instance>.getannotation(mailbox, entry, attribute)\n        Retrieve ANNOTATIONs.");
      var1.setline(472);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, PyString.fromInterned("GETANNOTATION"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(473);
      var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("ANNOTATION"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getquota$28(PyFrame var1, ThreadState var2) {
      var1.setline(482);
      PyString.fromInterned("Get the quota root's resource usage and limits.\n\n        Part of the IMAP4 QUOTA extension defined in rfc2087.\n\n        (typ, [data]) = <instance>.getquota(root)\n        ");
      var1.setline(483);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GETQUOTA"), (PyObject)var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(484);
      var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("QUOTA"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getquotaroot$29(PyFrame var1, ThreadState var2) {
      var1.setline(491);
      PyString.fromInterned("Get the list of quota roots for the named mailbox.\n\n        (typ, [[QUOTAROOT responses...], [QUOTA responses]]) = <instance>.getquotaroot(mailbox)\n        ");
      var1.setline(492);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GETQUOTAROOT"), (PyObject)var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(493);
      var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("QUOTA"));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(494);
      var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("QUOTAROOT"));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(495);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), new PyList(new PyObject[]{var1.getlocal(5), var1.getlocal(4)})});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject list$30(PyFrame var1, ThreadState var2) {
      var1.setline(504);
      PyString.fromInterned("List mailbox names in directory matching pattern.\n\n        (typ, [data]) = <instance>.list(directory='\"\"', pattern='*')\n\n        'data' is list of LIST responses.\n        ");
      var1.setline(505);
      PyString var3 = PyString.fromInterned("LIST");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(506);
      PyObject var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(507);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(3));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject login$31(PyFrame var1, ThreadState var2) {
      var1.setline(516);
      PyString.fromInterned("Identify client using plaintext password.\n\n        (typ, [data]) = <instance>.login(user, password)\n\n        NB: 'password' will be quoted.\n        ");
      var1.setline(517);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, PyString.fromInterned("LOGIN"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(0).__getattr__("_quote").__call__(var2, var1.getlocal(2)));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(518);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._ne(PyString.fromInterned("OK"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(519);
         throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(-1))));
      } else {
         var1.setline(520);
         PyString var6 = PyString.fromInterned("AUTH");
         var1.getlocal(0).__setattr__((String)"state", var6);
         var3 = null;
         var1.setline(521);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject login_cram_md5$32(PyFrame var1, ThreadState var2) {
      var1.setline(528);
      PyString.fromInterned(" Force use of CRAM-MD5 authentication.\n\n        (typ, [data]) = <instance>.login_cram_md5(user, password)\n        ");
      var1.setline(529);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("user", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("password", var5);
      var5 = null;
      var3 = null;
      var1.setline(530);
      PyObject var6 = var1.getlocal(0).__getattr__("authenticate").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CRAM-MD5"), (PyObject)var1.getlocal(0).__getattr__("_CRAM_MD5_AUTH"));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _CRAM_MD5_AUTH$33(PyFrame var1, ThreadState var2) {
      var1.setline(534);
      PyString.fromInterned(" Authobject to use with CRAM-MD5 authentication. ");
      var1.setline(535);
      PyObject var3 = imp.importOne("hmac", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(536);
      var3 = var1.getlocal(0).__getattr__("user")._add(PyString.fromInterned(" "))._add(var1.getlocal(2).__getattr__("HMAC").__call__(var2, var1.getlocal(0).__getattr__("password"), var1.getlocal(1)).__getattr__("hexdigest").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject logout$34(PyFrame var1, ThreadState var2) {
      var1.setline(545);
      PyString.fromInterned("Shutdown connection to server.\n\n        (typ, [data]) = <instance>.logout()\n\n        Returns server 'BYE' response.\n        ");
      var1.setline(546);
      PyString var3 = PyString.fromInterned("LOGOUT");
      var1.getlocal(0).__setattr__((String)"state", var3);
      var3 = null;

      PyObject[] var5;
      try {
         var1.setline(547);
         PyObject var8 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOGOUT"));
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         PyObject var11 = var9[0];
         var1.setlocal(1, var11);
         var5 = null;
         var11 = var9[1];
         var1.setlocal(2, var11);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         Py.setException(var7, var1);
         var1.setline(548);
         PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("NO"), new PyList(new PyObject[]{PyString.fromInterned("%s: %s")._mod(var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null))})});
         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;
      }

      var1.setline(549);
      var1.getlocal(0).__getattr__("shutdown").__call__(var2);
      var1.setline(550);
      var3 = PyString.fromInterned("BYE");
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("untagged_responses"));
      var3 = null;
      PyTuple var10;
      if (var10000.__nonzero__()) {
         var1.setline(551);
         var10 = new PyTuple(new PyObject[]{PyString.fromInterned("BYE"), var1.getlocal(0).__getattr__("untagged_responses").__getitem__(PyString.fromInterned("BYE"))});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(552);
         var10 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var10;
      }
   }

   public PyObject lsub$35(PyFrame var1, ThreadState var2) {
      var1.setline(561);
      PyString.fromInterned("List 'subscribed' mailbox names in directory matching pattern.\n\n        (typ, [data, ...]) = <instance>.lsub(directory='\"\"', pattern='*')\n\n        'data' are tuples of message part envelope and data.\n        ");
      var1.setline(562);
      PyString var3 = PyString.fromInterned("LSUB");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(563);
      PyObject var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(564);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(3));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject myrights$36(PyFrame var1, ThreadState var2) {
      var1.setline(570);
      PyString.fromInterned("Show my ACLs for a mailbox (i.e. the rights that I have on mailbox).\n\n        (typ, [data]) = <instance>.myrights(mailbox)\n        ");
      var1.setline(571);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MYRIGHTS"), (PyObject)var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(572);
      var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("MYRIGHTS"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject namespace$37(PyFrame var1, ThreadState var2) {
      var1.setline(578);
      PyString.fromInterned(" Returns IMAP namespaces ala rfc2342\n\n        (typ, [data, ...]) = <instance>.namespace()\n        ");
      var1.setline(579);
      PyString var3 = PyString.fromInterned("NAMESPACE");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(580);
      PyObject var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(581);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject noop$38(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyString.fromInterned("Send NOOP command.\n\n        (typ, [data]) = <instance>.noop()\n        ");
      var1.setline(589);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var1.setline(590);
         var3 = var1.getlocal(0).__getattr__("debug");
         PyObject var10000 = var3._ge(Py.newInteger(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(591);
            var1.getlocal(0).__getattr__("_dump_ur").__call__(var2, var1.getlocal(0).__getattr__("untagged_responses"));
         }
      }

      var1.setline(592);
      var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NOOP"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject partial$39(PyFrame var1, ThreadState var2) {
      var1.setline(601);
      PyString.fromInterned("Fetch truncated part of a message.\n\n        (typ, [data, ...]) = <instance>.partial(message_num, message_part, start, length)\n\n        'data' is tuple of message part envelope and data.\n        ");
      var1.setline(602);
      PyString var3 = PyString.fromInterned("PARTIAL");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(603);
      PyObject var10000 = var1.getlocal(0).__getattr__("_simple_command");
      PyObject[] var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      PyObject var7 = var10000.__call__(var2, var6);
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(604);
      var7 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(6), (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("FETCH"));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject proxyauth$40(PyFrame var1, ThreadState var2) {
      var1.setline(614);
      PyString.fromInterned("Assume authentication as \"user\".\n\n        Allows an authorised administrator to proxy into any user's\n        mailbox.\n\n        (typ, [data]) = <instance>.proxyauth(user)\n        ");
      var1.setline(616);
      PyString var3 = PyString.fromInterned("PROXYAUTH");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(617);
      PyObject var4 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PROXYAUTH"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject rename$41(PyFrame var1, ThreadState var2) {
      var1.setline(624);
      PyString.fromInterned("Rename old mailbox name to new.\n\n        (typ, [data]) = <instance>.rename(oldmailbox, newmailbox)\n        ");
      var1.setline(625);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, PyString.fromInterned("RENAME"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject search$42(PyFrame var1, ThreadState var2) {
      var1.setline(634);
      PyString.fromInterned("Search mailbox for matching messages.\n\n        (typ, [data]) = <instance>.search(charset, criterion, ...)\n\n        'data' is space separated list of matching message numbers.\n        ");
      var1.setline(635);
      PyString var3 = PyString.fromInterned("SEARCH");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(636);
      PyObject var10000;
      String[] var4;
      PyObject var5;
      PyObject[] var6;
      PyObject var7;
      PyObject[] var8;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(637);
         var10000 = var1.getlocal(0).__getattr__("_simple_command");
         var6 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("CHARSET"), var1.getlocal(1)};
         var4 = new String[0];
         var10000 = var10000._callextra(var6, var4, var1.getlocal(2), (PyObject)null);
         var3 = null;
         var7 = var10000;
         var8 = Py.unpackSequence(var7, 2);
         var5 = var8[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(639);
         var10000 = var1.getlocal(0).__getattr__("_simple_command");
         var6 = new PyObject[]{var1.getlocal(3)};
         var4 = new String[0];
         var10000 = var10000._callextra(var6, var4, var1.getlocal(2), (PyObject)null);
         var3 = null;
         var7 = var10000;
         var8 = Py.unpackSequence(var7, 2);
         var5 = var8[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(640);
      var7 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(3));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject select$43(PyFrame var1, ThreadState var2) {
      var1.setline(654);
      PyString.fromInterned("Select a mailbox.\n\n        Flush all untagged responses.\n\n        (typ, [data]) = <instance>.select(mailbox='INBOX', readonly=False)\n\n        'data' is count of messages in mailbox ('EXISTS' response).\n\n        Mandated responses are ('FLAGS', 'EXISTS', 'RECENT', 'UIDVALIDITY'), so\n        other responses should be obtained via <instance>.response('FLAGS') etc.\n        ");
      var1.setline(655);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"untagged_responses", var3);
      var3 = null;
      var1.setline(656);
      PyObject var6 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("is_readonly", var6);
      var3 = null;
      var1.setline(657);
      PyString var7;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(658);
         var7 = PyString.fromInterned("EXAMINE");
         var1.setlocal(3, var7);
         var3 = null;
      } else {
         var1.setline(660);
         var7 = PyString.fromInterned("SELECT");
         var1.setlocal(3, var7);
         var3 = null;
      }

      var1.setline(661);
      var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(662);
      var6 = var1.getlocal(4);
      PyObject var10000 = var6._ne(PyString.fromInterned("OK"));
      var3 = null;
      PyTuple var10;
      if (var10000.__nonzero__()) {
         var1.setline(663);
         var7 = PyString.fromInterned("AUTH");
         var1.getlocal(0).__setattr__((String)"state", var7);
         var3 = null;
         var1.setline(664);
         var10 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(665);
         PyString var8 = PyString.fromInterned("SELECTED");
         var1.getlocal(0).__setattr__((String)"state", var8);
         var4 = null;
         var1.setline(666);
         var8 = PyString.fromInterned("READ-ONLY");
         var10000 = var8._in(var1.getlocal(0).__getattr__("untagged_responses"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(668);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var1.setline(669);
               PyObject var9 = var1.getlocal(0).__getattr__("debug");
               var10000 = var9._ge(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(670);
                  var1.getlocal(0).__getattr__("_dump_ur").__call__(var2, var1.getlocal(0).__getattr__("untagged_responses"));
               }
            }

            var1.setline(671);
            throw Py.makeException(var1.getlocal(0).__getattr__("readonly").__call__(var2, PyString.fromInterned("%s is not writable")._mod(var1.getlocal(1))));
         } else {
            var1.setline(672);
            var10 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("untagged_responses").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EXISTS"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("None")})))});
            var1.f_lasti = -1;
            return var10;
         }
      }
   }

   public PyObject setacl$44(PyFrame var1, ThreadState var2) {
      var1.setline(679);
      PyString.fromInterned("Set a mailbox acl.\n\n        (typ, [data]) = <instance>.setacl(mailbox, who, what)\n        ");
      var1.setline(680);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, PyString.fromInterned("SETACL"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setannotation$45(PyFrame var1, ThreadState var2) {
      var1.setline(685);
      PyString.fromInterned("(typ, [data]) = <instance>.setannotation(mailbox[, entry, attribute]+)\n        Set ANNOTATIONs.");
      var1.setline(687);
      PyObject var10000 = var1.getlocal(0).__getattr__("_simple_command");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("SETANNOTATION")};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var6 = var10000;
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(688);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("ANNOTATION"));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject setquota$46(PyFrame var1, ThreadState var2) {
      var1.setline(695);
      PyString.fromInterned("Set the quota root's resource limits.\n\n        (typ, [data]) = <instance>.setquota(root, limits)\n        ");
      var1.setline(696);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, PyString.fromInterned("SETQUOTA"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(697);
      var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("QUOTA"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sort$47(PyFrame var1, ThreadState var2) {
      var1.setline(704);
      PyString.fromInterned("IMAP4rev1 extension SORT command.\n\n        (typ, [data]) = <instance>.sort(sort_criteria, charset, search_criteria, ...)\n        ");
      var1.setline(705);
      PyString var3 = PyString.fromInterned("SORT");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(708);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(-1))});
      PyObject var10000 = var6._ne(new PyTuple(new PyObject[]{PyString.fromInterned("("), PyString.fromInterned(")")}));
      var3 = null;
      PyObject var7;
      if (var10000.__nonzero__()) {
         var1.setline(709);
         var7 = PyString.fromInterned("(%s)")._mod(var1.getlocal(1));
         var1.setlocal(1, var7);
         var3 = null;
      }

      var1.setline(710);
      var10000 = var1.getlocal(0).__getattr__("_simple_command");
      PyObject[] var9 = new PyObject[]{var1.getlocal(4), var1.getlocal(1), var1.getlocal(2)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var9, var4, var1.getlocal(3), (PyObject)null);
      var3 = null;
      var7 = var10000;
      PyObject[] var8 = Py.unpackSequence(var7, 2);
      PyObject var5 = var8[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(711);
      var7 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(4));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject status$48(PyFrame var1, ThreadState var2) {
      var1.setline(718);
      PyString.fromInterned("Request named status conditions for mailbox.\n\n        (typ, [data]) = <instance>.status(mailbox, names)\n        ");
      var1.setline(719);
      PyString var3 = PyString.fromInterned("STATUS");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(722);
      PyObject var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(723);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(3));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject store$49(PyFrame var1, ThreadState var2) {
      var1.setline(730);
      PyString.fromInterned("Alters flag dispositions for messages in mailbox.\n\n        (typ, [data]) = <instance>.store(message_set, command, flags)\n        ");
      var1.setline(731);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getitem__(Py.newInteger(-1))});
      PyObject var10000 = var3._ne(new PyTuple(new PyObject[]{PyString.fromInterned("("), PyString.fromInterned(")")}));
      var3 = null;
      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(732);
         var6 = PyString.fromInterned("(%s)")._mod(var1.getlocal(3));
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(733);
      var6 = var1.getlocal(0).__getattr__("_simple_command").__call__(var2, PyString.fromInterned("STORE"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(734);
      var6 = var1.getlocal(0).__getattr__("_untagged_response").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("FETCH"));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject subscribe$50(PyFrame var1, ThreadState var2) {
      var1.setline(741);
      PyString.fromInterned("Subscribe to new mailbox.\n\n        (typ, [data]) = <instance>.subscribe(mailbox)\n        ");
      var1.setline(742);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SUBSCRIBE"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject thread$51(PyFrame var1, ThreadState var2) {
      var1.setline(749);
      PyString.fromInterned("IMAPrev1 extension THREAD command.\n\n        (type, [data]) = <instance>.thread(threading_algorithm, charset, search_criteria, ...)\n        ");
      var1.setline(750);
      PyString var3 = PyString.fromInterned("THREAD");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(751);
      PyObject var10000 = var1.getlocal(0).__getattr__("_simple_command");
      PyObject[] var6 = new PyObject[]{var1.getlocal(4), var1.getlocal(1), var1.getlocal(2)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var6, var4, var1.getlocal(3), (PyObject)null);
      var3 = null;
      PyObject var7 = var10000;
      PyObject[] var8 = Py.unpackSequence(var7, 2);
      PyObject var5 = var8[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(752);
      var7 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(4));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject uid$52(PyFrame var1, ThreadState var2) {
      var1.setline(762);
      PyString.fromInterned("Execute \"command arg ...\" with messages identified by UID,\n                rather than message number.\n\n        (typ, [data]) = <instance>.uid(command, arg1, arg2, ...)\n\n        Returns response appropriate to 'command'.\n        ");
      var1.setline(763);
      PyObject var3 = var1.getlocal(1).__getattr__("upper").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(764);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("Commands"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(765);
         throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("Unknown IMAP4 UID command: %s")._mod(var1.getlocal(1))));
      } else {
         var1.setline(766);
         var3 = var1.getlocal(0).__getattr__("state");
         var10000 = var3._notin(var1.getglobal("Commands").__getitem__(var1.getlocal(1)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(767);
            throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("command %s illegal in state %s, only allowed in states %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("state"), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("Commands").__getitem__(var1.getlocal(1)))}))));
         } else {
            var1.setline(771);
            PyString var7 = PyString.fromInterned("UID");
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(772);
            var10000 = var1.getlocal(0).__getattr__("_simple_command");
            PyObject[] var8 = new PyObject[]{var1.getlocal(3), var1.getlocal(1)};
            String[] var4 = new String[0];
            var10000 = var10000._callextra(var8, var4, var1.getlocal(2), (PyObject)null);
            var3 = null;
            var3 = var10000;
            PyObject[] var6 = Py.unpackSequence(var3, 2);
            PyObject var5 = var6[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var6[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
            var1.setline(773);
            var3 = var1.getlocal(1);
            var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("SEARCH"), PyString.fromInterned("SORT"), PyString.fromInterned("THREAD")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(774);
               var3 = var1.getlocal(1);
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(776);
               var7 = PyString.fromInterned("FETCH");
               var1.setlocal(3, var7);
               var3 = null;
            }

            var1.setline(777);
            var3 = var1.getlocal(0).__getattr__("_untagged_response").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject unsubscribe$53(PyFrame var1, ThreadState var2) {
      var1.setline(784);
      PyString.fromInterned("Unsubscribe from old mailbox.\n\n        (typ, [data]) = <instance>.unsubscribe(mailbox)\n        ");
      var1.setline(785);
      PyObject var3 = var1.getlocal(0).__getattr__("_simple_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNSUBSCRIBE"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject xatom$54(PyFrame var1, ThreadState var2) {
      var1.setline(797);
      PyString.fromInterned("Allow simple extension commands\n                notified by server in CAPABILITY response.\n\n        Assumes command is legal in current state.\n\n        (typ, [data]) = <instance>.xatom(name, arg, ...)\n\n        Returns response appropriate to extension command `name'.\n        ");
      var1.setline(798);
      PyObject var3 = var1.getlocal(1).__getattr__("upper").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(801);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("Commands"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(802);
         PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("state")});
         var1.getglobal("Commands").__setitem__((PyObject)var1.getlocal(1), var5);
         var3 = null;
      }

      var1.setline(803);
      var10000 = var1.getlocal(0).__getattr__("_simple_command");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var6, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _append_untagged$55(PyFrame var1, ThreadState var2) {
      var1.setline(812);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(812);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(813);
      var3 = var1.getlocal(0).__getattr__("untagged_responses");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(814);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var1.setline(815);
         var3 = var1.getlocal(0).__getattr__("debug");
         var10000 = var3._ge(Py.newInteger(5));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(816);
            var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("untagged_responses[%s] %s += [\"%s\"]")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned(""))), var1.getlocal(2)})));
         }
      }

      var1.setline(818);
      var3 = var1.getlocal(1);
      var10000 = var3._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(819);
         var1.getlocal(3).__getitem__(var1.getlocal(1)).__getattr__("append").__call__(var2, var1.getlocal(2));
      } else {
         var1.setline(821);
         PyList var5 = new PyList(new PyObject[]{var1.getlocal(2)});
         var1.getlocal(3).__setitem__((PyObject)var1.getlocal(1), var5);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_bye$56(PyFrame var1, ThreadState var2) {
      var1.setline(825);
      PyObject var3 = var1.getlocal(0).__getattr__("untagged_responses").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BYE"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(826);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(827);
         throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _command$57(PyFrame var1, ThreadState var2) {
      var1.setline(832);
      PyObject var3 = var1.getlocal(0).__getattr__("state");
      PyObject var10000 = var3._notin(var1.getglobal("Commands").__getitem__(var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(833);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("literal", var3);
         var3 = null;
         var1.setline(834);
         throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("command %s illegal in state %s, only allowed in states %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("state"), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("Commands").__getitem__(var1.getlocal(1)))}))));
      } else {
         var1.setline(839);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("OK"), PyString.fromInterned("NO"), PyString.fromInterned("BAD")})).__iter__();

         while(true) {
            var1.setline(839);
            PyObject var4 = var3.__iternext__();
            PyObject var5;
            if (var4 == null) {
               var1.setline(843);
               PyString var8 = PyString.fromInterned("READ-ONLY");
               var10000 = var8._in(var1.getlocal(0).__getattr__("untagged_responses"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("is_readonly").__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(845);
                  throw Py.makeException(var1.getlocal(0).__getattr__("readonly").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mailbox status changed to READ-ONLY")));
               } else {
                  var1.setline(847);
                  var3 = var1.getlocal(0).__getattr__("_new_tag").__call__(var2);
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(848);
                  var3 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(1)}));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(849);
                  var3 = var1.getlocal(2).__iter__();

                  while(true) {
                     var1.setline(849);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(853);
                        var3 = var1.getlocal(0).__getattr__("literal");
                        var1.setlocal(7, var3);
                        var3 = null;
                        var1.setline(854);
                        var3 = var1.getlocal(7);
                        var10000 = var3._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(855);
                           var3 = var1.getglobal("None");
                           var1.getlocal(0).__setattr__("literal", var3);
                           var3 = null;
                           var1.setline(856);
                           var3 = var1.getglobal("type").__call__(var2, var1.getlocal(7));
                           var10000 = var3._is(var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("_command")));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(857);
                              var3 = var1.getlocal(7);
                              var1.setlocal(8, var3);
                              var3 = null;
                           } else {
                              var1.setline(859);
                              var3 = var1.getglobal("None");
                              var1.setlocal(8, var3);
                              var3 = null;
                              var1.setline(860);
                              var3 = PyString.fromInterned("%s {%s}")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getglobal("len").__call__(var2, var1.getlocal(7))}));
                              var1.setlocal(5, var3);
                              var3 = null;
                           }
                        }

                        var1.setline(862);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var1.setline(863);
                           var3 = var1.getlocal(0).__getattr__("debug");
                           var10000 = var3._ge(Py.newInteger(4));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(864);
                              var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("> %s")._mod(var1.getlocal(5)));
                           } else {
                              var1.setline(866);
                              var1.getlocal(0).__getattr__("_log").__call__(var2, PyString.fromInterned("> %s")._mod(var1.getlocal(5)));
                           }
                        }

                        try {
                           var1.setline(869);
                           var1.getlocal(0).__getattr__("send").__call__(var2, PyString.fromInterned("%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getglobal("CRLF")})));
                        } catch (Throwable var6) {
                           PyException var10 = Py.setException(var6, var1);
                           if (var10.match(new PyTuple(new PyObject[]{var1.getglobal("socket").__getattr__("error"), var1.getglobal("OSError")}))) {
                              var4 = var10.value;
                              var1.setlocal(9, var4);
                              var4 = null;
                              var1.setline(871);
                              throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__(var2, PyString.fromInterned("socket error: %s")._mod(var1.getlocal(9))));
                           }

                           throw var10;
                        }

                        var1.setline(873);
                        var3 = var1.getlocal(7);
                        var10000 = var3._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(874);
                           var3 = var1.getlocal(4);
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           label102:
                           do {
                              var1.setline(876);
                              if (!Py.newInteger(1).__nonzero__()) {
                                 break;
                              }

                              do {
                                 var1.setline(879);
                                 if (!var1.getlocal(0).__getattr__("_get_response").__call__(var2).__nonzero__()) {
                                    var1.setline(885);
                                    if (var1.getlocal(8).__nonzero__()) {
                                       var1.setline(886);
                                       var4 = var1.getlocal(8).__call__(var2, var1.getlocal(0).__getattr__("continuation_response"));
                                       var1.setlocal(7, var4);
                                       var4 = null;
                                    }

                                    var1.setline(888);
                                    if (var1.getglobal("__debug__").__nonzero__()) {
                                       var1.setline(889);
                                       var4 = var1.getlocal(0).__getattr__("debug");
                                       var10000 = var4._ge(Py.newInteger(4));
                                       var4 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(890);
                                          var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("write literal size %s")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(7))));
                                       }
                                    }

                                    try {
                                       var1.setline(893);
                                       var1.getlocal(0).__getattr__("send").__call__(var2, var1.getlocal(7));
                                       var1.setline(894);
                                       var1.getlocal(0).__getattr__("send").__call__(var2, var1.getglobal("CRLF"));
                                    } catch (Throwable var7) {
                                       PyException var9 = Py.setException(var7, var1);
                                       if (var9.match(new PyTuple(new PyObject[]{var1.getglobal("socket").__getattr__("error"), var1.getglobal("OSError")}))) {
                                          var5 = var9.value;
                                          var1.setlocal(9, var5);
                                          var5 = null;
                                          var1.setline(896);
                                          throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__(var2, PyString.fromInterned("socket error: %s")._mod(var1.getlocal(9))));
                                       }

                                       throw var9;
                                    }

                                    var1.setline(898);
                                    continue label102;
                                 }

                                 var1.setline(880);
                              } while(!var1.getlocal(0).__getattr__("tagged_commands").__getitem__(var1.getlocal(4)).__nonzero__());

                              var1.setline(881);
                              var3 = var1.getlocal(4);
                              var1.f_lasti = -1;
                              return var3;
                           } while(!var1.getlocal(8).__not__().__nonzero__());

                           var1.setline(901);
                           var3 = var1.getlocal(4);
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }

                     var1.setlocal(6, var4);
                     var1.setline(850);
                     var5 = var1.getlocal(6);
                     var10000 = var5._is(var1.getglobal("None"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(851);
                        var5 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(0).__getattr__("_checkquote").__call__(var2, var1.getlocal(6))}));
                        var1.setlocal(5, var5);
                        var5 = null;
                     }
                  }
               }
            }

            var1.setlocal(3, var4);
            var1.setline(840);
            var5 = var1.getlocal(3);
            var10000 = var5._in(var1.getlocal(0).__getattr__("untagged_responses"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(841);
               var1.getlocal(0).__getattr__("untagged_responses").__delitem__(var1.getlocal(3));
            }
         }
      }
   }

   public PyObject _command_complete$58(PyFrame var1, ThreadState var2) {
      var1.setline(906);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(PyString.fromInterned("LOGOUT"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(907);
         var1.getlocal(0).__getattr__("_check_bye").__call__(var2);
      }

      try {
         var1.setline(909);
         var3 = var1.getlocal(0).__getattr__("_get_tagged_response").__call__(var2, var1.getlocal(2));
         PyObject[] var8 = Py.unpackSequence(var3, 2);
         PyObject var5 = var8[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         PyObject var4;
         if (var7.match(var1.getlocal(0).__getattr__("abort"))) {
            var4 = var7.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(911);
            throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__(var2, PyString.fromInterned("command: %s => %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(5)}))));
         }

         if (var7.match(var1.getlocal(0).__getattr__("error"))) {
            var4 = var7.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(913);
            throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("command: %s => %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(5)}))));
         }

         throw var7;
      }

      var1.setline(914);
      var3 = var1.getlocal(1);
      var10000 = var3._ne(PyString.fromInterned("LOGOUT"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(915);
         var1.getlocal(0).__getattr__("_check_bye").__call__(var2);
      }

      var1.setline(916);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(PyString.fromInterned("BAD"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(917);
         throw Py.makeException(var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("%s command error: %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(4)}))));
      } else {
         var1.setline(918);
         PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var9;
      }
   }

   public PyObject _get_response$59(PyFrame var1, ThreadState var2) {
      var1.setline(928);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_line").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(932);
      PyObject var10000;
      PyObject var4;
      if (var1.getlocal(0).__getattr__("_match").__call__(var2, var1.getlocal(0).__getattr__("tagre"), var1.getlocal(1)).__nonzero__()) {
         var1.setline(933);
         var3 = var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tag"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(934);
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(0).__getattr__("tagged_commands"));
         var3 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(935);
            throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__(var2, PyString.fromInterned("unexpected tagged response: %s")._mod(var1.getlocal(1))));
         }

         var1.setline(937);
         var3 = var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("type"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(938);
         var3 = var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(939);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), new PyList(new PyObject[]{var1.getlocal(4)})});
         var1.getlocal(0).__getattr__("tagged_commands").__setitem__((PyObject)var1.getlocal(2), var6);
         var3 = null;
      } else {
         var1.setline(941);
         var3 = var1.getglobal("None");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(945);
         if (var1.getlocal(0).__getattr__("_match").__call__(var2, var1.getglobal("Untagged_response"), var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(946);
            if (var1.getlocal(0).__getattr__("_match").__call__(var2, var1.getglobal("Untagged_status"), var1.getlocal(1)).__nonzero__()) {
               var1.setline(947);
               var3 = var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data2"));
               var1.setlocal(5, var3);
               var3 = null;
            }
         }

         var1.setline(949);
         var3 = var1.getlocal(0).__getattr__("mo");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(952);
            if (var1.getlocal(0).__getattr__("_match").__call__(var2, var1.getglobal("Continuation"), var1.getlocal(1)).__nonzero__()) {
               var1.setline(953);
               var3 = var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data"));
               var1.getlocal(0).__setattr__("continuation_response", var3);
               var3 = null;
               var1.setline(954);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(956);
            throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__(var2, PyString.fromInterned("unexpected response: '%s'")._mod(var1.getlocal(1))));
         }

         var1.setline(958);
         var4 = var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("type"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(959);
         var4 = var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data"));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(960);
         var4 = var1.getlocal(4);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(960);
            PyString var5 = PyString.fromInterned("");
            var1.setlocal(4, var5);
            var4 = null;
         }

         var1.setline(961);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(961);
            var4 = var1.getlocal(4)._add(PyString.fromInterned(" "))._add(var1.getlocal(5));
            var1.setlocal(4, var4);
            var4 = null;
         }

         while(true) {
            var1.setline(965);
            if (!var1.getlocal(0).__getattr__("_match").__call__(var2, var1.getglobal("Literal"), var1.getlocal(4)).__nonzero__()) {
               var1.setline(983);
               var1.getlocal(0).__getattr__("_append_untagged").__call__(var2, var1.getlocal(3), var1.getlocal(4));
               break;
            }

            var1.setline(969);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("size")));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(970);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var1.setline(971);
               var4 = var1.getlocal(0).__getattr__("debug");
               var10000 = var4._ge(Py.newInteger(4));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(972);
                  var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("read literal size %s")._mod(var1.getlocal(6)));
               }
            }

            var1.setline(973);
            var4 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(6));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(977);
            var1.getlocal(0).__getattr__("_append_untagged").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(7)})));
            var1.setline(981);
            var4 = var1.getlocal(0).__getattr__("_get_line").__call__(var2);
            var1.setlocal(4, var4);
            var4 = null;
         }
      }

      var1.setline(987);
      var4 = var1.getlocal(3);
      var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("OK"), PyString.fromInterned("NO"), PyString.fromInterned("BAD")}));
      var4 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_match").__call__(var2, var1.getglobal("Response_code"), var1.getlocal(4));
      }

      if (var10000.__nonzero__()) {
         var1.setline(988);
         var1.getlocal(0).__getattr__("_append_untagged").__call__(var2, var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("type")), var1.getlocal(0).__getattr__("mo").__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data")));
      }

      var1.setline(990);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var1.setline(991);
         var4 = var1.getlocal(0).__getattr__("debug");
         var10000 = var4._ge(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(3);
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("NO"), PyString.fromInterned("BAD"), PyString.fromInterned("BYE")}));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(992);
            var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("%s response: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
         }
      }

      var1.setline(994);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_tagged_response$60(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(999);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(1000);
         PyObject var3 = var1.getlocal(0).__getattr__("tagged_commands").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1001);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1002);
            var1.getlocal(0).__getattr__("tagged_commands").__delitem__(var1.getlocal(1));
            var1.setline(1003);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1008);
         var1.getlocal(0).__getattr__("_check_bye").__call__(var2);

         try {
            var1.setline(1016);
            var1.getlocal(0).__getattr__("_get_response").__call__(var2);
         } catch (Throwable var6) {
            PyException var4 = Py.setException(var6, var1);
            if (var4.match(var1.getlocal(0).__getattr__("abort"))) {
               PyObject var5 = var4.value;
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(1018);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var1.setline(1019);
                  var5 = var1.getlocal(0).__getattr__("debug");
                  var10000 = var5._ge(Py.newInteger(1));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1020);
                     var1.getlocal(0).__getattr__("print_log").__call__(var2);
                  }
               }

               var1.setline(1021);
               throw Py.makeException();
            }

            throw var4;
         }
      }
   }

   public PyObject _get_line$61(PyFrame var1, ThreadState var2) {
      var1.setline(1026);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1027);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1028);
         throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("socket error: EOF")));
      } else {
         var1.setline(1031);
         if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n")).__not__().__nonzero__()) {
            var1.setline(1032);
            throw Py.makeException(var1.getlocal(0).__getattr__("abort").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("socket error: unterminated line")));
         } else {
            var1.setline(1034);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(1035);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var1.setline(1036);
               var3 = var1.getlocal(0).__getattr__("debug");
               PyObject var10000 = var3._ge(Py.newInteger(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1037);
                  var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("< %s")._mod(var1.getlocal(1)));
               } else {
                  var1.setline(1039);
                  var1.getlocal(0).__getattr__("_log").__call__(var2, PyString.fromInterned("< %s")._mod(var1.getlocal(1)));
               }
            }

            var1.setline(1040);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _match$62(PyFrame var1, ThreadState var2) {
      var1.setline(1048);
      PyObject var3 = var1.getlocal(1).__getattr__("match").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("mo", var3);
      var3 = null;
      var1.setline(1049);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var1.setline(1050);
         var3 = var1.getlocal(0).__getattr__("mo");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("debug");
            var10000 = var3._ge(Py.newInteger(5));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1051);
            var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("\tmatched r'%s' => %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("pattern"), var1.getlocal(0).__getattr__("mo").__getattr__("groups").__call__(var2)})));
         }
      }

      var1.setline(1052);
      var3 = var1.getlocal(0).__getattr__("mo");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _new_tag$63(PyFrame var1, ThreadState var2) {
      var1.setline(1057);
      PyObject var3 = PyString.fromInterned("%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tagpre"), var1.getlocal(0).__getattr__("tagnum")}));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1058);
      var3 = var1.getlocal(0).__getattr__("tagnum")._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("tagnum", var3);
      var3 = null;
      var1.setline(1059);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__getattr__("tagged_commands").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(1060);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkquote$64(PyFrame var1, ThreadState var2) {
      var1.setline(1068);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._isnot(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1069);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1070);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var4._ge(Py.newInteger(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(-1))});
            var10000 = var5._in(new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("("), PyString.fromInterned(")")}), new PyTuple(new PyObject[]{PyString.fromInterned("\""), PyString.fromInterned("\"")})}));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1071);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1072);
            var10000 = var1.getlocal(1);
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("mustquote").__getattr__("search").__call__(var2, var1.getlocal(1));
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1073);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1074);
               var3 = var1.getlocal(0).__getattr__("_quote").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _quote$65(PyFrame var1, ThreadState var2) {
      var1.setline(1079);
      PyObject var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("\\\\"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1080);
      var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\\\""));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1082);
      var3 = PyString.fromInterned("\"%s\"")._mod(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _simple_command$66(PyFrame var1, ThreadState var2) {
      var1.setline(1087);
      PyObject var10000 = var1.getlocal(0).__getattr__("_command_complete");
      PyObject var10002 = var1.getlocal(1);
      PyObject var10003 = var1.getlocal(0).__getattr__("_command");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10003 = var10003._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002, var10003);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _untagged_response$67(PyFrame var1, ThreadState var2) {
      var1.setline(1092);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("NO"));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(1093);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(1094);
         PyObject var4 = var1.getlocal(3);
         var10000 = var4._in(var1.getlocal(0).__getattr__("untagged_responses"));
         var4 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(1095);
            var5 = new PyTuple(new PyObject[]{var1.getlocal(1), new PyList(new PyObject[]{var1.getglobal("None")})});
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(1096);
            var4 = var1.getlocal(0).__getattr__("untagged_responses").__getattr__("pop").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1097);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var1.setline(1098);
               var4 = var1.getlocal(0).__getattr__("debug");
               var10000 = var4._ge(Py.newInteger(5));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1099);
                  var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("untagged_responses[%s] => %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
               }
            }

            var1.setline(1100);
            var5 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4)});
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject _mesg$68(PyFrame var1, ThreadState var2) {
      var1.setline(1106);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1107);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1108);
      var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%M:%S"), (PyObject)var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1109);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("  %s.%02d %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)._mul(Py.newInteger(100))._mod(Py.newInteger(100)), var1.getlocal(1)})));
      var1.setline(1110);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _dump_ur$69(PyFrame var1, ThreadState var2) {
      var1.setline(1114);
      PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1115);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(1115);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1116);
         PyString var4 = PyString.fromInterned("\n\t\t");
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(1117);
         PyObject var10000 = var1.getglobal("map");
         var1.setline(1117);
         PyObject[] var5 = Py.EmptyObjects;
         var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$70)), (PyObject)var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1118);
         var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("untagged responses dump:%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(3).__getattr__("join").__call__(var2, var1.getlocal(2))})));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject f$70(PyFrame var1, ThreadState var2) {
      var1.setline(1117);
      PyString var10000 = PyString.fromInterned("%s: \"%s\"");
      PyTuple var10001 = new PyTuple;
      PyObject[] var10003 = new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), null};
      Object var10006 = var1.getlocal(0).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
      if (((PyObject)var10006).__nonzero__()) {
         var10006 = PyString.fromInterned("\" \"").__getattr__("join").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)));
      }

      if (!((PyObject)var10006).__nonzero__()) {
         var10006 = PyString.fromInterned("");
      }

      var10003[1] = (PyObject)var10006;
      var10001.<init>(var10003);
      PyObject var3 = var10000._mod(var10001);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _log$71(PyFrame var1, ThreadState var2) {
      var1.setline(1122);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("time").__getattr__("time").__call__(var2)});
      var1.getlocal(0).__getattr__("_cmd_log").__setitem__((PyObject)var1.getlocal(0).__getattr__("_cmd_log_idx"), var3);
      var3 = null;
      var1.setline(1123);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "_cmd_log_idx";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.setline(1124);
      PyObject var7 = var1.getlocal(0).__getattr__("_cmd_log_idx");
      var10000 = var7._ge(var1.getlocal(0).__getattr__("_cmd_log_len"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1125);
         PyInteger var8 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_cmd_log_idx", var8);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_log$72(PyFrame var1, ThreadState var2) {
      var1.setline(1128);
      var1.getlocal(0).__getattr__("_mesg").__call__(var2, PyString.fromInterned("last %d IMAP4 interactions:")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_cmd_log"))));
      var1.setline(1129);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_cmd_log_idx"), var1.getlocal(0).__getattr__("_cmd_log_len")});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(1130);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject var10000;
         try {
            var1.setline(1132);
            var10000 = var1.getlocal(0).__getattr__("_mesg");
            PyObject[] var7 = Py.EmptyObjects;
            String[] var8 = new String[0];
            var10000._callextra(var7, var8, var1.getlocal(0).__getattr__("_cmd_log").__getitem__(var1.getlocal(1)), (PyObject)null);
            var3 = null;
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            var1.setline(1134);
         }

         var1.setline(1135);
         PyObject var9 = var1.getlocal(1);
         var9 = var9._iadd(Py.newInteger(1));
         var1.setlocal(1, var9);
         var1.setline(1136);
         var9 = var1.getlocal(1);
         var10000 = var9._ge(var1.getlocal(0).__getattr__("_cmd_log_len"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1137);
            PyInteger var10 = Py.newInteger(0);
            var1.setlocal(1, var10);
            var3 = null;
         }

         var1.setline(1138);
         var9 = var1.getlocal(2);
         var9 = var9._isub(Py.newInteger(1));
         var1.setlocal(2, var9);
      }
   }

   public PyObject IMAP4_SSL$73(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("IMAP4 client class over SSL connection\n\n        Instantiate with: IMAP4_SSL([host[, port[, keyfile[, certfile]]]])\n\n                host - host's name (default: localhost);\n                port - port number (default: standard IMAP4 SSL port).\n                keyfile - PEM formatted file that contains your private key (default: None);\n                certfile - PEM formatted certificate chain file (default: None);\n\n        for more documentation see the docstring of the parent class IMAP4.\n        "));
      var1.setline(1159);
      PyString.fromInterned("IMAP4 client class over SSL connection\n\n        Instantiate with: IMAP4_SSL([host[, port[, keyfile[, certfile]]]])\n\n                host - host's name (default: localhost);\n                port - port number (default: standard IMAP4 SSL port).\n                keyfile - PEM formatted file that contains your private key (default: None);\n                certfile - PEM formatted certificate chain file (default: None);\n\n        for more documentation see the docstring of the parent class IMAP4.\n        ");
      var1.setline(1162);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned(""), var1.getname("IMAP4_SSL_PORT"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$74, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1168);
      var3 = new PyObject[]{PyString.fromInterned(""), var1.getname("IMAP4_SSL_PORT")};
      var4 = new PyFunction(var1.f_globals, var3, open$75, PyString.fromInterned("Setup connection to remote server on \"host:port\".\n                (default: localhost:standard IMAP4 SSL port).\n            This connection will be used by the routines:\n                read, readline, send, shutdown.\n            "));
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(1181);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$76, PyString.fromInterned("Read 'size' bytes from remote."));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(1186);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$77, PyString.fromInterned("Read line from remote."));
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(1191);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$78, PyString.fromInterned("Send data to remote."));
      var1.setlocal("send", var4);
      var3 = null;
      var1.setline(1202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shutdown$79, PyString.fromInterned("Close I/O established in \"open\"."));
      var1.setlocal("shutdown", var4);
      var3 = null;
      var1.setline(1208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, socket$80, PyString.fromInterned("Return socket instance used to connect to IMAP4 server.\n\n            socket = <instance>.socket()\n            "));
      var1.setlocal("socket", var4);
      var3 = null;
      var1.setline(1216);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ssl$81, PyString.fromInterned("Return SSLObject instance used to communicate with the IMAP4 server.\n\n            ssl = ssl.wrap_socket(<instance>.socket)\n            "));
      var1.setlocal("ssl", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$74(PyFrame var1, ThreadState var2) {
      var1.setline(1163);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("keyfile", var3);
      var3 = null;
      var1.setline(1164);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("certfile", var3);
      var3 = null;
      var1.setline(1165);
      var1.getglobal("IMAP4").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$75(PyFrame var1, ThreadState var2) {
      var1.setline(1173);
      PyString.fromInterned("Setup connection to remote server on \"host:port\".\n                (default: localhost:standard IMAP4 SSL port).\n            This connection will be used by the routines:\n                read, readline, send, shutdown.\n            ");
      var1.setline(1174);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(1175);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(1176);
      var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(1177);
      var3 = var1.getglobal("ssl").__getattr__("wrap_socket").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getlocal(0).__getattr__("keyfile"), var1.getlocal(0).__getattr__("certfile"));
      var1.getlocal(0).__setattr__("sslobj", var3);
      var3 = null;
      var1.setline(1178);
      var3 = var1.getlocal(0).__getattr__("sslobj").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$76(PyFrame var1, ThreadState var2) {
      var1.setline(1182);
      PyString.fromInterned("Read 'size' bytes from remote.");
      var1.setline(1183);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$77(PyFrame var1, ThreadState var2) {
      var1.setline(1187);
      PyString.fromInterned("Read line from remote.");
      var1.setline(1188);
      PyObject var3 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject send$78(PyFrame var1, ThreadState var2) {
      var1.setline(1192);
      PyString.fromInterned("Send data to remote.");
      var1.setline(1193);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(1194);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(1195);
         var3 = var1.getlocal(0).__getattr__("sslobj").__getattr__("write").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1196);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(1198);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1199);
         var3 = var1.getlocal(2)._sub(var1.getlocal(3));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown$79(PyFrame var1, ThreadState var2) {
      var1.setline(1203);
      PyString.fromInterned("Close I/O established in \"open\".");
      var1.setline(1204);
      var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
      var1.setline(1205);
      var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject socket$80(PyFrame var1, ThreadState var2) {
      var1.setline(1212);
      PyString.fromInterned("Return socket instance used to connect to IMAP4 server.\n\n            socket = <instance>.socket()\n            ");
      var1.setline(1213);
      PyObject var3 = var1.getlocal(0).__getattr__("sock");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ssl$81(PyFrame var1, ThreadState var2) {
      var1.setline(1220);
      PyString.fromInterned("Return SSLObject instance used to communicate with the IMAP4 server.\n\n            ssl = ssl.wrap_socket(<instance>.socket)\n            ");
      var1.setline(1221);
      PyObject var3 = var1.getlocal(0).__getattr__("sslobj");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IMAP4_stream$82(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("IMAP4 client class over a stream\n\n    Instantiate with: IMAP4_stream(command)\n\n            where \"command\" is a string that can be passed to subprocess.Popen()\n\n    for more documentation see the docstring of the parent class IMAP4.\n    "));
      var1.setline(1235);
      PyString.fromInterned("IMAP4 client class over a stream\n\n    Instantiate with: IMAP4_stream(command)\n\n            where \"command\" is a string that can be passed to subprocess.Popen()\n\n    for more documentation see the docstring of the parent class IMAP4.\n    ");
      var1.setline(1238);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$83, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1243);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, open$84, PyString.fromInterned("Setup a stream connection.\n        This connection will be used by the routines:\n            read, readline, send, shutdown.\n        "));
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(1259);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$85, PyString.fromInterned("Read 'size' bytes from remote."));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(1264);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readline$86, PyString.fromInterned("Read line from remote."));
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(1269);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send$87, PyString.fromInterned("Send data to remote."));
      var1.setlocal("send", var4);
      var3 = null;
      var1.setline(1275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shutdown$88, PyString.fromInterned("Close I/O established in \"open\"."));
      var1.setlocal("shutdown", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$83(PyFrame var1, ThreadState var2) {
      var1.setline(1239);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("command", var3);
      var3 = null;
      var1.setline(1240);
      var1.getglobal("IMAP4").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$84(PyFrame var1, ThreadState var2) {
      var1.setline(1247);
      PyString.fromInterned("Setup a stream connection.\n        This connection will be used by the routines:\n            read, readline, send, shutdown.\n        ");
      var1.setline(1248);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("host", var3);
      var3 = null;
      var1.setline(1249);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("port", var3);
      var3 = null;
      var1.setline(1250);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(1251);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(1252);
      PyObject var10000 = var1.getglobal("subprocess").__getattr__("Popen");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("command"), var1.getglobal("subprocess").__getattr__("PIPE"), var1.getglobal("subprocess").__getattr__("PIPE"), var1.getglobal("True"), var1.getglobal("True")};
      String[] var4 = new String[]{"stdin", "stdout", "shell", "close_fds"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("process", var3);
      var3 = null;
      var1.setline(1255);
      var3 = var1.getlocal(0).__getattr__("process").__getattr__("stdin");
      var1.getlocal(0).__setattr__("writefile", var3);
      var3 = null;
      var1.setline(1256);
      var3 = var1.getlocal(0).__getattr__("process").__getattr__("stdout");
      var1.getlocal(0).__setattr__("readfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$85(PyFrame var1, ThreadState var2) {
      var1.setline(1260);
      PyString.fromInterned("Read 'size' bytes from remote.");
      var1.setline(1261);
      PyObject var3 = var1.getlocal(0).__getattr__("readfile").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$86(PyFrame var1, ThreadState var2) {
      var1.setline(1265);
      PyString.fromInterned("Read line from remote.");
      var1.setline(1266);
      PyObject var3 = var1.getlocal(0).__getattr__("readfile").__getattr__("readline").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject send$87(PyFrame var1, ThreadState var2) {
      var1.setline(1270);
      PyString.fromInterned("Send data to remote.");
      var1.setline(1271);
      var1.getlocal(0).__getattr__("writefile").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setline(1272);
      var1.getlocal(0).__getattr__("writefile").__getattr__("flush").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject shutdown$88(PyFrame var1, ThreadState var2) {
      var1.setline(1276);
      PyString.fromInterned("Close I/O established in \"open\".");
      var1.setline(1277);
      var1.getlocal(0).__getattr__("readfile").__getattr__("close").__call__(var2);
      var1.setline(1278);
      var1.getlocal(0).__getattr__("writefile").__getattr__("close").__call__(var2);
      var1.setline(1279);
      var1.getlocal(0).__getattr__("process").__getattr__("wait").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Authenticator$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Private class to provide en/decoding\n            for base64-based authentication conversation.\n    "));
      var1.setline(1287);
      PyString.fromInterned("Private class to provide en/decoding\n            for base64-based authentication conversation.\n    ");
      var1.setline(1289);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$90, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1292);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, process$91, (PyObject)null);
      var1.setlocal("process", var4);
      var3 = null;
      var1.setline(1298);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encode$92, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(1320);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, decode$93, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$90(PyFrame var1, ThreadState var2) {
      var1.setline(1290);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("mech", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject process$91(PyFrame var1, ThreadState var2) {
      var1.setline(1293);
      PyObject var3 = var1.getlocal(0).__getattr__("mech").__call__(var2, var1.getlocal(0).__getattr__("decode").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1294);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1295);
         PyString var4 = PyString.fromInterned("*");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1296);
         var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject encode$92(PyFrame var1, ThreadState var2) {
      var1.setline(1307);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(1308);
         PyObject var4;
         if (!var1.getlocal(1).__nonzero__()) {
            var1.setline(1318);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(1309);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var4._gt(Py.newInteger(48));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1310);
            var4 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(48), (PyObject)null);
            var1.setlocal(3, var4);
            var3 = null;
            var1.setline(1311);
            var4 = var1.getlocal(1).__getslice__(Py.newInteger(48), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var3 = null;
         } else {
            var1.setline(1313);
            var4 = var1.getlocal(1);
            var1.setlocal(3, var4);
            var3 = null;
            var1.setline(1314);
            var3 = PyString.fromInterned("");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(1315);
         var4 = var1.getglobal("binascii").__getattr__("b2a_base64").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(1316);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(1317);
            var4 = var1.getlocal(2)._add(var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
            var1.setlocal(2, var4);
            var3 = null;
         }
      }
   }

   public PyObject decode$93(PyFrame var1, ThreadState var2) {
      var1.setline(1321);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1322);
         PyString var4 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1323);
         PyObject var3 = var1.getglobal("binascii").__getattr__("a2b_base64").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Internaldate2tuple$94(PyFrame var1, ThreadState var2) {
      var1.setline(1335);
      PyString.fromInterned("Parse an IMAP4 INTERNALDATE string.\n\n    Return corresponding local time.  The return value is a\n    time.struct_time instance or None if the string has wrong format.\n    ");
      var1.setline(1337);
      PyObject var3 = var1.getglobal("InternalDate").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1338);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1339);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1341);
         PyObject var4 = var1.getglobal("Mon2num").__getitem__(var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mon")));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1342);
         var4 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("zonen"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1344);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("day")));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1345);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("year")));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(1346);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hour")));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(1347);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("min")));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(1348);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sec")));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(1349);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("zoneh")));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(1350);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("zonem")));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(1354);
         var4 = var1.getlocal(9)._mul(Py.newInteger(60))._add(var1.getlocal(10))._mul(Py.newInteger(60));
         var1.setlocal(11, var4);
         var4 = null;
         var1.setline(1355);
         var4 = var1.getlocal(3);
         PyObject var10000 = var4._eq(PyString.fromInterned("-"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1356);
            var4 = var1.getlocal(11).__neg__();
            var1.setlocal(11, var4);
            var4 = null;
         }

         var1.setline(1358);
         PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(2), var1.getlocal(4), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), Py.newInteger(-1), Py.newInteger(-1), Py.newInteger(-1)});
         var1.setlocal(12, var5);
         var4 = null;
         var1.setline(1360);
         var4 = var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(12));
         var1.setlocal(13, var4);
         var4 = null;
         var1.setline(1365);
         var4 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(13));
         var1.setlocal(14, var4);
         var4 = null;
         var1.setline(1366);
         var10000 = var1.getglobal("time").__getattr__("daylight");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(14).__getitem__(Py.newInteger(-1));
         }

         if (var10000.__nonzero__()) {
            var1.setline(1367);
            var4 = var1.getlocal(11)._add(var1.getglobal("time").__getattr__("altzone"));
            var1.setlocal(11, var4);
            var4 = null;
         } else {
            var1.setline(1369);
            var4 = var1.getlocal(11)._add(var1.getglobal("time").__getattr__("timezone"));
            var1.setlocal(11, var4);
            var4 = null;
         }

         var1.setline(1371);
         var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(13)._sub(var1.getlocal(11)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Int2AP$95(PyFrame var1, ThreadState var2) {
      var1.setline(1377);
      PyString.fromInterned("Convert integer to A-P string representation.");
      var1.setline(1379);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1379);
      var3 = PyString.fromInterned("ABCDEFGHIJKLMNOP");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1380);
      PyObject var6 = var1.getglobal("int").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(0)));
      var1.setlocal(0, var6);
      var3 = null;

      while(true) {
         var1.setline(1381);
         if (!var1.getlocal(0).__nonzero__()) {
            var1.setline(1384);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(1382);
         var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(16));
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(0, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(1383);
         var6 = var1.getlocal(2).__getitem__(var1.getlocal(3))._add(var1.getlocal(1));
         var1.setlocal(1, var6);
         var3 = null;
      }
   }

   public PyObject ParseFlags$96(PyFrame var1, ThreadState var2) {
      var1.setline(1390);
      PyString.fromInterned("Convert IMAP4 flags response to python tuple.");
      var1.setline(1392);
      PyObject var3 = var1.getglobal("Flags").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1393);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1394);
         PyTuple var4 = new PyTuple(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1396);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("flags")).__getattr__("split").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Time2Internaldate$97(PyFrame var1, ThreadState var2) {
      var1.setline(1409);
      PyString.fromInterned("Convert date_time to IMAP4 INTERNALDATE representation.\n\n    Return string in form: '\"DD-Mmm-YYYY HH:MM:SS +HHMM\"'.  The\n    date_time argument can be a number (int or float) representing\n    seconds since epoch (as returned by time.time()), a 9-tuple\n    representing local time (as returned by time.localtime()), or a\n    double-quoted string.  In the last case, it is assumed to already\n    be in the correct format.\n    ");
      var1.setline(1411);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("float")}))).__nonzero__()) {
         var1.setline(1412);
         var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1413);
         if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("tuple"), var1.getglobal("time").__getattr__("struct_time")}))).__nonzero__()) {
            var1.setline(1415);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str"));
            if (var10000.__nonzero__()) {
               PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getitem__(Py.newInteger(-1))});
               var10000 = var5._eq(new PyTuple(new PyObject[]{PyString.fromInterned("\""), PyString.fromInterned("\"")}));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1416);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(1418);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("date_time not of a known type")));
         }

         var1.setline(1414);
         var3 = var1.getlocal(0);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1420);
      PyObject var4 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%d-%b-%Y %H:%M:%S"), (PyObject)var1.getlocal(1));
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(1421);
      var4 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var10000 = var4._eq(PyString.fromInterned("0"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1422);
         var4 = PyString.fromInterned(" ")._add(var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var1.setlocal(2, var4);
         var4 = null;
      }

      var1.setline(1423);
      var10000 = var1.getglobal("time").__getattr__("daylight");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      }

      if (var10000.__nonzero__()) {
         var1.setline(1424);
         var4 = var1.getglobal("time").__getattr__("altzone").__neg__();
         var1.setlocal(3, var4);
         var4 = null;
      } else {
         var1.setline(1426);
         var4 = var1.getglobal("time").__getattr__("timezone").__neg__();
         var1.setlocal(3, var4);
         var4 = null;
      }

      var1.setline(1427);
      var3 = PyString.fromInterned("\"")._add(var1.getlocal(2))._add(PyString.fromInterned(" %+03d%02d")._mod(var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3)._floordiv(Py.newInteger(60)), (PyObject)Py.newInteger(60))))._add(PyString.fromInterned("\""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$98(PyFrame var1, ThreadState var2) {
      var1.setline(1488);
      var1.getglobal("M").__getattr__("_mesg").__call__(var2, PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})));
      var1.setline(1489);
      PyObject var10000 = var1.getglobal("getattr").__call__(var2, var1.getglobal("M"), var1.getlocal(0));
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var6 = var10000;
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1490);
      var1.getglobal("M").__getattr__("_mesg").__call__(var2, PyString.fromInterned("%s => %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(2), var1.getlocal(3)})));
      var1.setline(1491);
      var6 = var1.getlocal(2);
      var10000 = var6._eq(PyString.fromInterned("NO"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1491);
         throw Py.makeException(var1.getlocal(3).__getitem__(Py.newInteger(0)));
      } else {
         var1.setline(1492);
         var6 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public imaplib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IMAP4$1 = Py.newCode(0, var2, var1, "IMAP4", 108, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      error$2 = Py.newCode(0, var2, var1, "error", 154, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      abort$3 = Py.newCode(0, var2, var1, "abort", 155, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      readonly$4 = Py.newCode(0, var2, var1, "readonly", 156, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "typ", "dat", "version"};
      __init__$5 = Py.newCode(3, var2, var1, "__init__", 160, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$6 = Py.newCode(2, var2, var1, "__getattr__", 219, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port"};
      open$7 = Py.newCode(3, var2, var1, "open", 230, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$8 = Py.newCode(2, var2, var1, "read", 242, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      readline$9 = Py.newCode(1, var2, var1, "readline", 247, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send$10 = Py.newCode(2, var2, var1, "send", 255, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "e"};
      shutdown$11 = Py.newCode(1, var2, var1, "shutdown", 260, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      socket$12 = Py.newCode(1, var2, var1, "socket", 273, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "typ", "dat"};
      recent$13 = Py.newCode(1, var2, var1, "recent", 285, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code"};
      response$14 = Py.newCode(2, var2, var1, "response", 302, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "flags", "date_time", "message", "name"};
      append$15 = Py.newCode(5, var2, var1, "append", 316, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mechanism", "authobject", "mech", "typ", "dat"};
      authenticate$16 = Py.newCode(3, var2, var1, "authenticate", 339, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "typ", "dat"};
      capability$17 = Py.newCode(1, var2, var1, "capability", 368, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      check$18 = Py.newCode(1, var2, var1, "check", 377, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "typ", "dat"};
      close$19 = Py.newCode(1, var2, var1, "close", 385, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message_set", "new_mailbox"};
      copy$20 = Py.newCode(3, var2, var1, "copy", 400, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox"};
      create$21 = Py.newCode(2, var2, var1, "create", 408, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox"};
      delete$22 = Py.newCode(2, var2, var1, "delete", 416, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "who"};
      deleteacl$23 = Py.newCode(3, var2, var1, "deleteacl", 423, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "typ", "dat"};
      expunge$24 = Py.newCode(1, var2, var1, "expunge", 430, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message_set", "message_parts", "name", "typ", "dat"};
      fetch$25 = Py.newCode(3, var2, var1, "fetch", 444, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "typ", "dat"};
      getacl$26 = Py.newCode(2, var2, var1, "getacl", 459, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "entry", "attribute", "typ", "dat"};
      getannotation$27 = Py.newCode(4, var2, var1, "getannotation", 468, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "root", "typ", "dat"};
      getquota$28 = Py.newCode(2, var2, var1, "getquota", 476, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "typ", "dat", "quota", "quotaroot"};
      getquotaroot$29 = Py.newCode(2, var2, var1, "getquotaroot", 487, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "directory", "pattern", "name", "typ", "dat"};
      list$30 = Py.newCode(3, var2, var1, "list", 498, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "password", "typ", "dat"};
      login$31 = Py.newCode(3, var2, var1, "login", 510, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "password"};
      login_cram_md5$32 = Py.newCode(3, var2, var1, "login_cram_md5", 524, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "challenge", "hmac"};
      _CRAM_MD5_AUTH$33 = Py.newCode(2, var2, var1, "_CRAM_MD5_AUTH", 533, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "typ", "dat"};
      logout$34 = Py.newCode(1, var2, var1, "logout", 539, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "directory", "pattern", "name", "typ", "dat"};
      lsub$35 = Py.newCode(3, var2, var1, "lsub", 555, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "typ", "dat"};
      myrights$36 = Py.newCode(2, var2, var1, "myrights", 566, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "typ", "dat"};
      namespace$37 = Py.newCode(1, var2, var1, "namespace", 574, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      noop$38 = Py.newCode(1, var2, var1, "noop", 584, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message_num", "message_part", "start", "length", "name", "typ", "dat"};
      partial$39 = Py.newCode(5, var2, var1, "partial", 595, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "name"};
      proxyauth$40 = Py.newCode(2, var2, var1, "proxyauth", 607, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldmailbox", "newmailbox"};
      rename$41 = Py.newCode(3, var2, var1, "rename", 620, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset", "criteria", "name", "typ", "dat"};
      search$42 = Py.newCode(3, var2, var1, "search", 628, true, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "readonly", "name", "typ", "dat"};
      select$43 = Py.newCode(3, var2, var1, "select", 643, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "who", "what"};
      setacl$44 = Py.newCode(4, var2, var1, "setacl", 675, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "typ", "dat"};
      setannotation$45 = Py.newCode(2, var2, var1, "setannotation", 683, true, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "root", "limits", "typ", "dat"};
      setquota$46 = Py.newCode(3, var2, var1, "setquota", 691, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sort_criteria", "charset", "search_criteria", "name", "typ", "dat"};
      sort$47 = Py.newCode(4, var2, var1, "sort", 700, true, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox", "names", "name", "typ", "dat"};
      status$48 = Py.newCode(3, var2, var1, "status", 714, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message_set", "command", "flags", "typ", "dat"};
      store$49 = Py.newCode(4, var2, var1, "store", 726, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox"};
      subscribe$50 = Py.newCode(2, var2, var1, "subscribe", 737, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "threading_algorithm", "charset", "search_criteria", "name", "typ", "dat"};
      thread$51 = Py.newCode(4, var2, var1, "thread", 745, true, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "command", "args", "name", "typ", "dat"};
      uid$52 = Py.newCode(3, var2, var1, "uid", 755, true, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mailbox"};
      unsubscribe$53 = Py.newCode(2, var2, var1, "unsubscribe", 780, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "args"};
      xatom$54 = Py.newCode(3, var2, var1, "xatom", 788, true, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "typ", "dat", "ur"};
      _append_untagged$55 = Py.newCode(3, var2, var1, "_append_untagged", 810, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bye"};
      _check_bye$56 = Py.newCode(1, var2, var1, "_check_bye", 824, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "args", "typ", "tag", "data", "arg", "literal", "literator", "val"};
      _command$57 = Py.newCode(3, var2, var1, "_command", 830, true, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "tag", "typ", "data", "val"};
      _command_complete$58 = Py.newCode(3, var2, var1, "_command_complete", 904, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "tag", "typ", "dat", "dat2", "size", "data"};
      _get_response$59 = Py.newCode(1, var2, var1, "_get_response", 921, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "result", "val"};
      _get_tagged_response$60 = Py.newCode(2, var2, var1, "_get_tagged_response", 997, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _get_line$61 = Py.newCode(1, var2, var1, "_get_line", 1024, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cre", "s"};
      _match$62 = Py.newCode(3, var2, var1, "_match", 1043, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      _new_tag$63 = Py.newCode(1, var2, var1, "_new_tag", 1055, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _checkquote$64 = Py.newCode(2, var2, var1, "_checkquote", 1063, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      _quote$65 = Py.newCode(2, var2, var1, "_quote", 1077, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "args"};
      _simple_command$66 = Py.newCode(3, var2, var1, "_simple_command", 1085, true, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "typ", "dat", "name", "data"};
      _untagged_response$67 = Py.newCode(4, var2, var1, "_untagged_response", 1090, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "secs", "tm"};
      _mesg$68 = Py.newCode(3, var2, var1, "_mesg", 1105, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "l", "t"};
      _dump_ur$69 = Py.newCode(2, var2, var1, "_dump_ur", 1112, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$70 = Py.newCode(1, var2, var1, "<lambda>", 1117, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _log$71 = Py.newCode(2, var2, var1, "_log", 1120, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "n"};
      print_log$72 = Py.newCode(1, var2, var1, "print_log", 1127, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IMAP4_SSL$73 = Py.newCode(0, var2, var1, "IMAP4_SSL", 1147, false, false, self, 73, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "keyfile", "certfile"};
      __init__$74 = Py.newCode(5, var2, var1, "__init__", 1162, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port"};
      open$75 = Py.newCode(3, var2, var1, "open", 1168, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$76 = Py.newCode(2, var2, var1, "read", 1181, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readline$77 = Py.newCode(1, var2, var1, "readline", 1186, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "bytes", "sent"};
      send$78 = Py.newCode(2, var2, var1, "send", 1191, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      shutdown$79 = Py.newCode(1, var2, var1, "shutdown", 1202, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      socket$80 = Py.newCode(1, var2, var1, "socket", 1208, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      ssl$81 = Py.newCode(1, var2, var1, "ssl", 1216, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IMAP4_stream$82 = Py.newCode(0, var2, var1, "IMAP4_stream", 1226, false, false, self, 82, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "command"};
      __init__$83 = Py.newCode(2, var2, var1, "__init__", 1238, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port"};
      open$84 = Py.newCode(3, var2, var1, "open", 1243, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$85 = Py.newCode(2, var2, var1, "read", 1259, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readline$86 = Py.newCode(1, var2, var1, "readline", 1264, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send$87 = Py.newCode(2, var2, var1, "send", 1269, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      shutdown$88 = Py.newCode(1, var2, var1, "shutdown", 1275, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Authenticator$89 = Py.newCode(0, var2, var1, "_Authenticator", 1283, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mechinst"};
      __init__$90 = Py.newCode(2, var2, var1, "__init__", 1289, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "ret"};
      process$91 = Py.newCode(2, var2, var1, "process", 1292, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "inp", "oup", "t", "e"};
      encode$92 = Py.newCode(2, var2, var1, "encode", 1298, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "inp"};
      decode$93 = Py.newCode(2, var2, var1, "decode", 1320, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"resp", "mo", "mon", "zonen", "day", "year", "hour", "min", "sec", "zoneh", "zonem", "zone", "tt", "utc", "lt"};
      Internaldate2tuple$94 = Py.newCode(1, var2, var1, "Internaldate2tuple", 1330, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"num", "val", "AP", "mod"};
      Int2AP$95 = Py.newCode(1, var2, var1, "Int2AP", 1375, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"resp", "mo"};
      ParseFlags$96 = Py.newCode(1, var2, var1, "ParseFlags", 1388, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"date_time", "tt", "dt", "zone"};
      Time2Internaldate$97 = Py.newCode(1, var2, var1, "Time2Internaldate", 1399, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "args", "typ", "dat"};
      run$98 = Py.newCode(2, var2, var1, "run", 1487, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new imaplib$py("imaplib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(imaplib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.IMAP4$1(var2, var3);
         case 2:
            return this.error$2(var2, var3);
         case 3:
            return this.abort$3(var2, var3);
         case 4:
            return this.readonly$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.__getattr__$6(var2, var3);
         case 7:
            return this.open$7(var2, var3);
         case 8:
            return this.read$8(var2, var3);
         case 9:
            return this.readline$9(var2, var3);
         case 10:
            return this.send$10(var2, var3);
         case 11:
            return this.shutdown$11(var2, var3);
         case 12:
            return this.socket$12(var2, var3);
         case 13:
            return this.recent$13(var2, var3);
         case 14:
            return this.response$14(var2, var3);
         case 15:
            return this.append$15(var2, var3);
         case 16:
            return this.authenticate$16(var2, var3);
         case 17:
            return this.capability$17(var2, var3);
         case 18:
            return this.check$18(var2, var3);
         case 19:
            return this.close$19(var2, var3);
         case 20:
            return this.copy$20(var2, var3);
         case 21:
            return this.create$21(var2, var3);
         case 22:
            return this.delete$22(var2, var3);
         case 23:
            return this.deleteacl$23(var2, var3);
         case 24:
            return this.expunge$24(var2, var3);
         case 25:
            return this.fetch$25(var2, var3);
         case 26:
            return this.getacl$26(var2, var3);
         case 27:
            return this.getannotation$27(var2, var3);
         case 28:
            return this.getquota$28(var2, var3);
         case 29:
            return this.getquotaroot$29(var2, var3);
         case 30:
            return this.list$30(var2, var3);
         case 31:
            return this.login$31(var2, var3);
         case 32:
            return this.login_cram_md5$32(var2, var3);
         case 33:
            return this._CRAM_MD5_AUTH$33(var2, var3);
         case 34:
            return this.logout$34(var2, var3);
         case 35:
            return this.lsub$35(var2, var3);
         case 36:
            return this.myrights$36(var2, var3);
         case 37:
            return this.namespace$37(var2, var3);
         case 38:
            return this.noop$38(var2, var3);
         case 39:
            return this.partial$39(var2, var3);
         case 40:
            return this.proxyauth$40(var2, var3);
         case 41:
            return this.rename$41(var2, var3);
         case 42:
            return this.search$42(var2, var3);
         case 43:
            return this.select$43(var2, var3);
         case 44:
            return this.setacl$44(var2, var3);
         case 45:
            return this.setannotation$45(var2, var3);
         case 46:
            return this.setquota$46(var2, var3);
         case 47:
            return this.sort$47(var2, var3);
         case 48:
            return this.status$48(var2, var3);
         case 49:
            return this.store$49(var2, var3);
         case 50:
            return this.subscribe$50(var2, var3);
         case 51:
            return this.thread$51(var2, var3);
         case 52:
            return this.uid$52(var2, var3);
         case 53:
            return this.unsubscribe$53(var2, var3);
         case 54:
            return this.xatom$54(var2, var3);
         case 55:
            return this._append_untagged$55(var2, var3);
         case 56:
            return this._check_bye$56(var2, var3);
         case 57:
            return this._command$57(var2, var3);
         case 58:
            return this._command_complete$58(var2, var3);
         case 59:
            return this._get_response$59(var2, var3);
         case 60:
            return this._get_tagged_response$60(var2, var3);
         case 61:
            return this._get_line$61(var2, var3);
         case 62:
            return this._match$62(var2, var3);
         case 63:
            return this._new_tag$63(var2, var3);
         case 64:
            return this._checkquote$64(var2, var3);
         case 65:
            return this._quote$65(var2, var3);
         case 66:
            return this._simple_command$66(var2, var3);
         case 67:
            return this._untagged_response$67(var2, var3);
         case 68:
            return this._mesg$68(var2, var3);
         case 69:
            return this._dump_ur$69(var2, var3);
         case 70:
            return this.f$70(var2, var3);
         case 71:
            return this._log$71(var2, var3);
         case 72:
            return this.print_log$72(var2, var3);
         case 73:
            return this.IMAP4_SSL$73(var2, var3);
         case 74:
            return this.__init__$74(var2, var3);
         case 75:
            return this.open$75(var2, var3);
         case 76:
            return this.read$76(var2, var3);
         case 77:
            return this.readline$77(var2, var3);
         case 78:
            return this.send$78(var2, var3);
         case 79:
            return this.shutdown$79(var2, var3);
         case 80:
            return this.socket$80(var2, var3);
         case 81:
            return this.ssl$81(var2, var3);
         case 82:
            return this.IMAP4_stream$82(var2, var3);
         case 83:
            return this.__init__$83(var2, var3);
         case 84:
            return this.open$84(var2, var3);
         case 85:
            return this.read$85(var2, var3);
         case 86:
            return this.readline$86(var2, var3);
         case 87:
            return this.send$87(var2, var3);
         case 88:
            return this.shutdown$88(var2, var3);
         case 89:
            return this._Authenticator$89(var2, var3);
         case 90:
            return this.__init__$90(var2, var3);
         case 91:
            return this.process$91(var2, var3);
         case 92:
            return this.encode$92(var2, var3);
         case 93:
            return this.decode$93(var2, var3);
         case 94:
            return this.Internaldate2tuple$94(var2, var3);
         case 95:
            return this.Int2AP$95(var2, var3);
         case 96:
            return this.ParseFlags$96(var2, var3);
         case 97:
            return this.Time2Internaldate$97(var2, var3);
         case 98:
            return this.run$98(var2, var3);
         default:
            return null;
      }
   }
}
