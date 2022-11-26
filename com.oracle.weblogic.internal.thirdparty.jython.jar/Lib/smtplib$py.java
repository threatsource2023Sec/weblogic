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
@Filename("smtplib.py")
public class smtplib$py extends PyFunctionTable implements PyRunnable {
   static smtplib$py self;
   static final PyCode f$0;
   static final PyCode SMTPException$1;
   static final PyCode SMTPServerDisconnected$2;
   static final PyCode SMTPResponseException$3;
   static final PyCode __init__$4;
   static final PyCode SMTPSenderRefused$5;
   static final PyCode __init__$6;
   static final PyCode SMTPRecipientsRefused$7;
   static final PyCode __init__$8;
   static final PyCode SMTPDataError$9;
   static final PyCode SMTPConnectError$10;
   static final PyCode SMTPHeloError$11;
   static final PyCode SMTPAuthenticationError$12;
   static final PyCode quoteaddr$13;
   static final PyCode _addr_only$14;
   static final PyCode quotedata$15;
   static final PyCode SSLFakeFile$16;
   static final PyCode __init__$17;
   static final PyCode readline$18;
   static final PyCode close$19;
   static final PyCode SMTP$20;
   static final PyCode __init__$21;
   static final PyCode set_debuglevel$22;
   static final PyCode _get_socket$23;
   static final PyCode connect$24;
   static final PyCode send$25;
   static final PyCode putcmd$26;
   static final PyCode getreply$27;
   static final PyCode docmd$28;
   static final PyCode helo$29;
   static final PyCode ehlo$30;
   static final PyCode has_extn$31;
   static final PyCode help$32;
   static final PyCode rset$33;
   static final PyCode noop$34;
   static final PyCode mail$35;
   static final PyCode rcpt$36;
   static final PyCode data$37;
   static final PyCode verify$38;
   static final PyCode expn$39;
   static final PyCode ehlo_or_helo_if_needed$40;
   static final PyCode login$41;
   static final PyCode encode_cram_md5$42;
   static final PyCode encode_plain$43;
   static final PyCode starttls$44;
   static final PyCode sendmail$45;
   static final PyCode close$46;
   static final PyCode quit$47;
   static final PyCode SMTP_SSL$48;
   static final PyCode __init__$49;
   static final PyCode _get_socket$50;
   static final PyCode LMTP$51;
   static final PyCode __init__$52;
   static final PyCode connect$53;
   static final PyCode prompt$54;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("SMTP/ESMTP client class.\n\nThis should follow RFC 821 (SMTP), RFC 1869 (ESMTP), RFC 2554 (SMTP\nAuthentication) and RFC 2487 (Secure SMTP over TLS).\n\nNotes:\n\nPlease remember, when doing ESMTP, that the names of the SMTP service\nextensions are NOT the same thing as the option keywords for the RCPT\nand MAIL commands!\n\nExample:\n\n  >>> import smtplib\n  >>> s=smtplib.SMTP(\"localhost\")\n  >>> print s.help()\n  This is Sendmail version 8.8.4\n  Topics:\n      HELO    EHLO    MAIL    RCPT    DATA\n      RSET    NOOP    QUIT    HELP    VRFY\n      EXPN    VERB    ETRN    DSN\n  For more info use \"HELP <topic>\".\n  To report bugs in the implementation send email to\n      sendmail-bugs@sendmail.org.\n  For local information send email to Postmaster at your site.\n  End of HELP info\n  >>> s.putcmd(\"vrfy\",\"someone@here\")\n  >>> s.getreply()\n  (250, \"Somebody OverHere <somebody@here.my.org>\")\n  >>> s.quit()\n"));
      var1.setline(33);
      PyString.fromInterned("SMTP/ESMTP client class.\n\nThis should follow RFC 821 (SMTP), RFC 1869 (ESMTP), RFC 2554 (SMTP\nAuthentication) and RFC 2487 (Secure SMTP over TLS).\n\nNotes:\n\nPlease remember, when doing ESMTP, that the names of the SMTP service\nextensions are NOT the same thing as the option keywords for the RCPT\nand MAIL commands!\n\nExample:\n\n  >>> import smtplib\n  >>> s=smtplib.SMTP(\"localhost\")\n  >>> print s.help()\n  This is Sendmail version 8.8.4\n  Topics:\n      HELO    EHLO    MAIL    RCPT    DATA\n      RSET    NOOP    QUIT    HELP    VRFY\n      EXPN    VERB    ETRN    DSN\n  For more info use \"HELP <topic>\".\n  To report bugs in the implementation send email to\n      sendmail-bugs@sendmail.org.\n  For local information send email to Postmaster at your site.\n  End of HELP info\n  >>> s.putcmd(\"vrfy\",\"someone@here\")\n  >>> s.getreply()\n  (250, \"Somebody OverHere <somebody@here.my.org>\")\n  >>> s.quit()\n");
      var1.setline(44);
      PyObject var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(45);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(46);
      var3 = imp.importOne("email.utils", var1, -1);
      var1.setlocal("email", var3);
      var3 = null;
      var1.setline(47);
      var3 = imp.importOne("base64", var1, -1);
      var1.setlocal("base64", var3);
      var3 = null;
      var1.setline(48);
      var3 = imp.importOne("hmac", var1, -1);
      var1.setlocal("hmac", var3);
      var3 = null;
      var1.setline(49);
      String[] var7 = new String[]{"encode"};
      PyObject[] var8 = imp.importFrom("email.base64mime", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("encode_base64", var4);
      var4 = null;
      var1.setline(50);
      var7 = new String[]{"stderr"};
      var8 = imp.importFrom("sys", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("stderr", var4);
      var4 = null;
      var1.setline(52);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("SMTPException"), PyString.fromInterned("SMTPServerDisconnected"), PyString.fromInterned("SMTPResponseException"), PyString.fromInterned("SMTPSenderRefused"), PyString.fromInterned("SMTPRecipientsRefused"), PyString.fromInterned("SMTPDataError"), PyString.fromInterned("SMTPConnectError"), PyString.fromInterned("SMTPHeloError"), PyString.fromInterned("SMTPAuthenticationError"), PyString.fromInterned("quoteaddr"), PyString.fromInterned("quotedata"), PyString.fromInterned("SMTP")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(57);
      PyInteger var10 = Py.newInteger(25);
      var1.setlocal("SMTP_PORT", var10);
      var3 = null;
      var1.setline(58);
      var10 = Py.newInteger(465);
      var1.setlocal("SMTP_SSL_PORT", var10);
      var3 = null;
      var1.setline(59);
      PyString var11 = PyString.fromInterned("\r\n");
      var1.setlocal("CRLF", var11);
      var3 = null;
      var1.setline(60);
      var10 = Py.newInteger(8192);
      var1.setlocal("_MAXLINE", var10);
      var3 = null;
      var1.setline(62);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("auth=(.*)"), (PyObject)var1.getname("re").__getattr__("I"));
      var1.setlocal("OLDSTYLE_AUTH", var3);
      var3 = null;
      var1.setline(66);
      var8 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("SMTPException", var8, SMTPException$1);
      var1.setlocal("SMTPException", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(69);
      var8 = new PyObject[]{var1.getname("SMTPException")};
      var4 = Py.makeClass("SMTPServerDisconnected", var8, SMTPServerDisconnected$2);
      var1.setlocal("SMTPServerDisconnected", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(77);
      var8 = new PyObject[]{var1.getname("SMTPException")};
      var4 = Py.makeClass("SMTPResponseException", var8, SMTPResponseException$3);
      var1.setlocal("SMTPResponseException", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(91);
      var8 = new PyObject[]{var1.getname("SMTPResponseException")};
      var4 = Py.makeClass("SMTPSenderRefused", var8, SMTPSenderRefused$5);
      var1.setlocal("SMTPSenderRefused", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(104);
      var8 = new PyObject[]{var1.getname("SMTPException")};
      var4 = Py.makeClass("SMTPRecipientsRefused", var8, SMTPRecipientsRefused$7);
      var1.setlocal("SMTPRecipientsRefused", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(117);
      var8 = new PyObject[]{var1.getname("SMTPResponseException")};
      var4 = Py.makeClass("SMTPDataError", var8, SMTPDataError$9);
      var1.setlocal("SMTPDataError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(120);
      var8 = new PyObject[]{var1.getname("SMTPResponseException")};
      var4 = Py.makeClass("SMTPConnectError", var8, SMTPConnectError$10);
      var1.setlocal("SMTPConnectError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(123);
      var8 = new PyObject[]{var1.getname("SMTPResponseException")};
      var4 = Py.makeClass("SMTPHeloError", var8, SMTPHeloError$11);
      var1.setlocal("SMTPHeloError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(126);
      var8 = new PyObject[]{var1.getname("SMTPResponseException")};
      var4 = Py.makeClass("SMTPAuthenticationError", var8, SMTPAuthenticationError$12);
      var1.setlocal("SMTPAuthenticationError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(134);
      var8 = Py.EmptyObjects;
      PyFunction var13 = new PyFunction(var1.f_globals, var8, quoteaddr$13, PyString.fromInterned("Quote a subset of the email addresses defined by RFC 821.\n\n    Should be able to handle anything rfc822.parseaddr can handle.\n    "));
      var1.setlocal("quoteaddr", var13);
      var3 = null;
      var1.setline(153);
      var8 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var8, _addr_only$14, (PyObject)null);
      var1.setlocal("_addr_only", var13);
      var3 = null;
      var1.setline(160);
      var8 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var8, quotedata$15, PyString.fromInterned("Quote data for email.\n\n    Double leading '.', and change Unix newline '\\n', or Mac '\\r' into\n    Internet CRLF end-of-line.\n    "));
      var1.setlocal("quotedata", var13);
      var3 = null;

      label41: {
         try {
            var1.setline(171);
            var3 = imp.importOne("ssl", var1, -1);
            var1.setlocal("ssl", var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var14 = Py.setException(var6, var1);
            if (var14.match(var1.getname("ImportError"))) {
               var1.setline(173);
               var4 = var1.getname("False");
               var1.setlocal("_have_ssl", var4);
               var4 = null;
               break label41;
            }

            throw var14;
         }

         var1.setline(175);
         PyObject[] var12 = Py.EmptyObjects;
         PyObject var5 = Py.makeClass("SSLFakeFile", var12, SSLFakeFile$16);
         var1.setlocal("SSLFakeFile", var5);
         var5 = null;
         Arrays.fill(var12, (Object)null);
         var1.setline(200);
         var4 = var1.getname("True");
         var1.setlocal("_have_ssl", var4);
         var4 = null;
      }

      var1.setline(202);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("SMTP", var8, SMTP$20);
      var1.setlocal("SMTP", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(771);
      if (var1.getname("_have_ssl").__nonzero__()) {
         var1.setline(773);
         var8 = new PyObject[]{var1.getname("SMTP")};
         var4 = Py.makeClass("SMTP_SSL", var8, SMTP_SSL$48);
         var1.setlocal("SMTP_SSL", var4);
         var4 = null;
         Arrays.fill(var8, (Object)null);
         var1.setline(802);
         var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SMTP_SSL"));
      }

      var1.setline(807);
      var10 = Py.newInteger(2003);
      var1.setlocal("LMTP_PORT", var10);
      var3 = null;
      var1.setline(809);
      var8 = new PyObject[]{var1.getname("SMTP")};
      var4 = Py.makeClass("LMTP", var8, LMTP$51);
      var1.setlocal("LMTP", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(853);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(854);
         var3 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var3);
         var3 = null;
         var1.setline(856);
         var8 = Py.EmptyObjects;
         var13 = new PyFunction(var1.f_globals, var8, prompt$54, (PyObject)null);
         var1.setlocal("prompt", var13);
         var3 = null;
         var1.setline(860);
         var3 = var1.getname("prompt").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From"));
         var1.setlocal("fromaddr", var3);
         var3 = null;
         var1.setline(861);
         var3 = var1.getname("prompt").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("To")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal("toaddrs", var3);
         var3 = null;
         var1.setline(862);
         Py.println(PyString.fromInterned("Enter message, end with ^D:"));
         var1.setline(863);
         var11 = PyString.fromInterned("");
         var1.setlocal("msg", var11);
         var3 = null;

         while(true) {
            var1.setline(864);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(865);
            var3 = var1.getname("sys").__getattr__("stdin").__getattr__("readline").__call__(var2);
            var1.setlocal("line", var3);
            var3 = null;
            var1.setline(866);
            if (var1.getname("line").__not__().__nonzero__()) {
               break;
            }

            var1.setline(868);
            var3 = var1.getname("msg")._add(var1.getname("line"));
            var1.setlocal("msg", var3);
            var3 = null;
         }

         var1.setline(869);
         Py.println(PyString.fromInterned("Message length is %d")._mod(var1.getname("len").__call__(var2, var1.getname("msg"))));
         var1.setline(871);
         var3 = var1.getname("SMTP").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("localhost"));
         var1.setlocal("server", var3);
         var3 = null;
         var1.setline(872);
         var1.getname("server").__getattr__("set_debuglevel").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setline(873);
         var1.getname("server").__getattr__("sendmail").__call__(var2, var1.getname("fromaddr"), var1.getname("toaddrs"), var1.getname("msg"));
         var1.setline(874);
         var1.getname("server").__getattr__("quit").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SMTPException$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for all exceptions raised by this module."));
      var1.setline(67);
      PyString.fromInterned("Base class for all exceptions raised by this module.");
      return var1.getf_locals();
   }

   public PyObject SMTPServerDisconnected$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Not connected to any SMTP server.\n\n    This exception is raised when the server unexpectedly disconnects,\n    or when an attempt is made to use the SMTP instance before\n    connecting it to a server.\n    "));
      var1.setline(75);
      PyString.fromInterned("Not connected to any SMTP server.\n\n    This exception is raised when the server unexpectedly disconnects,\n    or when an attempt is made to use the SMTP instance before\n    connecting it to a server.\n    ");
      return var1.getf_locals();
   }

   public PyObject SMTPResponseException$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for all exceptions that include an SMTP error code.\n\n    These exceptions are generated in some instances when the SMTP\n    server returns an error code.  The error code is stored in the\n    `smtp_code' attribute of the error, and the `smtp_error' attribute\n    is set to the error message.\n    "));
      var1.setline(84);
      PyString.fromInterned("Base class for all exceptions that include an SMTP error code.\n\n    These exceptions are generated in some instances when the SMTP\n    server returns an error code.  The error code is stored in the\n    `smtp_code' attribute of the error, and the `smtp_error' attribute\n    is set to the error message.\n    ");
      var1.setline(86);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("smtp_code", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("smtp_error", var3);
      var3 = null;
      var1.setline(89);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SMTPSenderRefused$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Sender address refused.\n\n    In addition to the attributes set by on all SMTPResponseException\n    exceptions, this sets `sender' to the string that the SMTP refused.\n    "));
      var1.setline(96);
      PyString.fromInterned("Sender address refused.\n\n    In addition to the attributes set by on all SMTPResponseException\n    exceptions, this sets `sender' to the string that the SMTP refused.\n    ");
      var1.setline(98);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("smtp_code", var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("smtp_error", var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("sender", var3);
      var3 = null;
      var1.setline(102);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SMTPRecipientsRefused$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("All recipient addresses refused.\n\n    The errors for each recipient are accessible through the attribute\n    'recipients', which is a dictionary of exactly the same sort as\n    SMTP.sendmail() returns.\n    "));
      var1.setline(110);
      PyString.fromInterned("All recipient addresses refused.\n\n    The errors for each recipient are accessible through the attribute\n    'recipients', which is a dictionary of exactly the same sort as\n    SMTP.sendmail() returns.\n    ");
      var1.setline(112);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("recipients", var3);
      var3 = null;
      var1.setline(114);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SMTPDataError$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The SMTP server didn't accept the data."));
      var1.setline(118);
      PyString.fromInterned("The SMTP server didn't accept the data.");
      return var1.getf_locals();
   }

   public PyObject SMTPConnectError$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Error during connection establishment."));
      var1.setline(121);
      PyString.fromInterned("Error during connection establishment.");
      return var1.getf_locals();
   }

   public PyObject SMTPHeloError$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The server refused our HELO reply."));
      var1.setline(124);
      PyString.fromInterned("The server refused our HELO reply.");
      return var1.getf_locals();
   }

   public PyObject SMTPAuthenticationError$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Authentication error.\n\n    Most probably the server didn't accept the username/password\n    combination provided.\n    "));
      var1.setline(131);
      PyString.fromInterned("Authentication error.\n\n    Most probably the server didn't accept the username/password\n    combination provided.\n    ");
      return var1.getf_locals();
   }

   public PyObject quoteaddr$13(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("Quote a subset of the email addresses defined by RFC 821.\n\n    Should be able to handle anything rfc822.parseaddr can handle.\n    ");
      var1.setline(139);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var7;
      try {
         var1.setline(141);
         var7 = var1.getglobal("email").__getattr__("utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(0)).__getitem__(Py.newInteger(1));
         var1.setlocal(1, var7);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("AttributeError"))) {
            throw var6;
         }

         var1.setline(143);
      }

      var1.setline(144);
      var7 = var1.getlocal(1);
      PyObject var10000 = var7._eq(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(146);
         var7 = PyString.fromInterned("<%s>")._mod(var1.getlocal(0));
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(147);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(149);
            PyString var8 = PyString.fromInterned("<>");
            var1.f_lasti = -1;
            return var8;
         } else {
            var1.setline(151);
            var7 = PyString.fromInterned("<%s>")._mod(var1.getlocal(1));
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject _addr_only$14(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getglobal("email").__getattr__("utils").__getattr__("parseaddr").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(155);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      PyObject var10000 = var6._eq(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(157);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(158);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject quotedata$15(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyString.fromInterned("Quote data for email.\n\n    Double leading '.', and change Unix newline '\\n', or Mac '\\r' into\n    Internet CRLF end-of-line.\n    ");
      var1.setline(166);
      PyObject var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?m)^\\."), (PyObject)PyString.fromInterned(".."), (PyObject)var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?:\\r\\n|\\n|\\r(?!\\n))"), (PyObject)var1.getglobal("CRLF"), (PyObject)var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SSLFakeFile$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A fake file like object that really wraps a SSLObject.\n\n        It only supports what is needed in smtplib.\n        "));
      var1.setline(179);
      PyString.fromInterned("A fake file like object that really wraps a SSLObject.\n\n        It only supports what is needed in smtplib.\n        ");
      var1.setline(180);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(183);
      var3 = new PyObject[]{Py.newInteger(-1)};
      var4 = new PyFunction(var1.f_globals, var3, readline$18, (PyObject)null);
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$19, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("sslobj", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$18(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(185);
         var3 = var1.getglobal("None");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(186);
      PyString var4 = PyString.fromInterned("");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(187);
      var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(188);
         var3 = var1.getlocal(3);
         var10000 = var3._ne(PyString.fromInterned("\n"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(189);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._ge(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(191);
         var3 = var1.getlocal(0).__getattr__("sslobj").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(192);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            break;
         }

         var1.setline(194);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(3));
         var1.setlocal(2, var3);
      }

      var1.setline(195);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$19(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SMTP$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class manages a connection to an SMTP or ESMTP server.\n    SMTP Objects:\n        SMTP objects have the following attributes:\n            helo_resp\n                This is the message given by the server in response to the\n                most recent HELO command.\n\n            ehlo_resp\n                This is the message given by the server in response to the\n                most recent EHLO command. This is usually multiline.\n\n            does_esmtp\n                This is a True value _after you do an EHLO command_, if the\n                server supports ESMTP.\n\n            esmtp_features\n                This is a dictionary, which, if the server supports ESMTP,\n                will _after you do an EHLO command_, contain the names of the\n                SMTP service extensions this server supports, and their\n                parameters (if any).\n\n                Note, all extension names are mapped to lower case in the\n                dictionary.\n\n        See each method's docstrings for details.  In general, there is a\n        method of the same name to perform each SMTP command.  There is also a\n        method called 'sendmail' that will do an entire mail transaction.\n        "));
      var1.setline(230);
      PyString.fromInterned("This class manages a connection to an SMTP or ESMTP server.\n    SMTP Objects:\n        SMTP objects have the following attributes:\n            helo_resp\n                This is the message given by the server in response to the\n                most recent HELO command.\n\n            ehlo_resp\n                This is the message given by the server in response to the\n                most recent EHLO command. This is usually multiline.\n\n            does_esmtp\n                This is a True value _after you do an EHLO command_, if the\n                server supports ESMTP.\n\n            esmtp_features\n                This is a dictionary, which, if the server supports ESMTP,\n                will _after you do an EHLO command_, contain the names of the\n                SMTP service extensions this server supports, and their\n                parameters (if any).\n\n                Note, all extension names are mapped to lower case in the\n                dictionary.\n\n        See each method's docstrings for details.  In general, there is a\n        method of the same name to perform each SMTP command.  There is also a\n        method called 'sendmail' that will do an entire mail transaction.\n        ");
      var1.setline(231);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("debuglevel", var3);
      var3 = null;
      var1.setline(232);
      PyObject var4 = var1.getname("None");
      var1.setlocal("file", var4);
      var3 = null;
      var1.setline(233);
      var4 = var1.getname("None");
      var1.setlocal("helo_resp", var4);
      var3 = null;
      var1.setline(234);
      PyString var5 = PyString.fromInterned("ehlo");
      var1.setlocal("ehlo_msg", var5);
      var3 = null;
      var1.setline(235);
      var4 = var1.getname("None");
      var1.setlocal("ehlo_resp", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.newInteger(0);
      var1.setlocal("does_esmtp", var3);
      var3 = null;
      var1.setline(237);
      var4 = var1.getname("SMTP_PORT");
      var1.setlocal("default_port", var4);
      var3 = null;
      var1.setline(239);
      PyObject[] var6 = new PyObject[]{PyString.fromInterned(""), Py.newInteger(0), var1.getname("None"), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$21, PyString.fromInterned("Initialize a new instance.\n\n        If specified, `host' is the name of the remote host to which to\n        connect.  If specified, `port' specifies the port to which to connect.\n        By default, smtplib.SMTP_PORT is used.  If a host is specified the\n        connect method is called, and if it returns anything other than a\n        success code an SMTPConnectError is raised.  If specified,\n        `local_hostname` is used as the FQDN of the local host for the\n        HELO/EHLO command.  Otherwise, the local hostname is found using\n        socket.getfqdn().\n\n        "));
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(277);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_debuglevel$22, PyString.fromInterned("Set the debug output level.\n\n        A non-false value results in debug messages for connection and for all\n        messages sent to and received from the server.\n\n        "));
      var1.setlocal("set_debuglevel", var7);
      var3 = null;
      var1.setline(286);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_socket$23, (PyObject)null);
      var1.setlocal("_get_socket", var7);
      var3 = null;
      var1.setline(293);
      var6 = new PyObject[]{PyString.fromInterned("localhost"), Py.newInteger(0)};
      var7 = new PyFunction(var1.f_globals, var6, connect$24, PyString.fromInterned("Connect to a host on a given port.\n\n        If the hostname ends with a colon (`:') followed by a number, and\n        there is no port specified, that suffix will be stripped off and the\n        number interpreted as the port number to use.\n\n        Note: This method is automatically invoked by __init__, if a host is\n        specified during instantiation.\n\n        "));
      var1.setlocal("connect", var7);
      var3 = null;
      var1.setline(322);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, send$25, PyString.fromInterned("Send `str' to the server."));
      var1.setlocal("send", var7);
      var3 = null;
      var1.setline(335);
      var6 = new PyObject[]{PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, putcmd$26, PyString.fromInterned("Send a command to the server."));
      var1.setlocal("putcmd", var7);
      var3 = null;
      var1.setline(343);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, getreply$27, PyString.fromInterned("Get a reply from the server.\n\n        Returns a tuple consisting of:\n\n          - server response code (e.g. '250', or such, if all goes well)\n            Note: returns -1 if it can't read response code.\n\n          - server response string corresponding to response code (multiline\n            responses are converted to a single, multiline string).\n\n        Raises SMTPServerDisconnected if end-of-file is reached.\n        "));
      var1.setlocal("getreply", var7);
      var3 = null;
      var1.setline(391);
      var6 = new PyObject[]{PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, docmd$28, PyString.fromInterned("Send a command, and return its response code."));
      var1.setlocal("docmd", var7);
      var3 = null;
      var1.setline(397);
      var6 = new PyObject[]{PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, helo$29, PyString.fromInterned("SMTP 'helo' command.\n        Hostname to send for this command defaults to the FQDN of the local\n        host.\n        "));
      var1.setlocal("helo", var7);
      var3 = null;
      var1.setline(407);
      var6 = new PyObject[]{PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, ehlo$30, PyString.fromInterned(" SMTP 'ehlo' command.\n        Hostname to send for this command defaults to the FQDN of the local\n        host.\n        "));
      var1.setlocal("ehlo", var7);
      var3 = null;
      var1.setline(457);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, has_extn$31, PyString.fromInterned("Does the server support a given SMTP service extension?"));
      var1.setlocal("has_extn", var7);
      var3 = null;
      var1.setline(461);
      var6 = new PyObject[]{PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, help$32, PyString.fromInterned("SMTP 'help' command.\n        Returns help text from server."));
      var1.setlocal("help", var7);
      var3 = null;
      var1.setline(467);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, rset$33, PyString.fromInterned("SMTP 'rset' command -- resets session."));
      var1.setlocal("rset", var7);
      var3 = null;
      var1.setline(471);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, noop$34, PyString.fromInterned("SMTP 'noop' command -- doesn't do anything :>"));
      var1.setlocal("noop", var7);
      var3 = null;
      var1.setline(475);
      var6 = new PyObject[]{new PyList(Py.EmptyObjects)};
      var7 = new PyFunction(var1.f_globals, var6, mail$35, PyString.fromInterned("SMTP 'mail' command -- begins mail xfer session."));
      var1.setlocal("mail", var7);
      var3 = null;
      var1.setline(483);
      var6 = new PyObject[]{new PyList(Py.EmptyObjects)};
      var7 = new PyFunction(var1.f_globals, var6, rcpt$36, PyString.fromInterned("SMTP 'rcpt' command -- indicates 1 recipient for this mail."));
      var1.setlocal("rcpt", var7);
      var3 = null;
      var1.setline(491);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, data$37, PyString.fromInterned("SMTP 'DATA' command -- sends message data to server.\n\n        Automatically quotes lines beginning with a period per rfc821.\n        Raises SMTPDataError if there is an unexpected reply to the\n        DATA command; the return value from this method is the final\n        response code received when the all data is sent.\n        "));
      var1.setlocal("data", var7);
      var3 = null;
      var1.setline(516);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, verify$38, PyString.fromInterned("SMTP 'verify' command -- checks for address validity."));
      var1.setlocal("verify", var7);
      var3 = null;
      var1.setline(521);
      var4 = var1.getname("verify");
      var1.setlocal("vrfy", var4);
      var3 = null;
      var1.setline(523);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, expn$39, PyString.fromInterned("SMTP 'expn' command -- expands a mailing list."));
      var1.setlocal("expn", var7);
      var3 = null;
      var1.setline(530);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, ehlo_or_helo_if_needed$40, PyString.fromInterned("Call self.ehlo() and/or self.helo() if needed.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError            The server didn't reply properly to\n                                  the helo greeting.\n        "));
      var1.setlocal("ehlo_or_helo_if_needed", var7);
      var3 = null;
      var1.setline(547);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, login$41, PyString.fromInterned("Log in on an SMTP server that requires authentication.\n\n        The arguments are:\n            - user:     The user name to authenticate with.\n            - password: The password for the authentication.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.\n\n        This method will return normally if the authentication was successful.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError            The server didn't reply properly to\n                                  the helo greeting.\n         SMTPAuthenticationError  The server didn't accept the username/\n                                  password combination.\n         SMTPException            No suitable authentication method was\n                                  found.\n        "));
      var1.setlocal("login", var7);
      var3 = null;
      var1.setline(625);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, starttls$44, PyString.fromInterned("Puts the connection to the SMTP server into TLS mode.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.\n\n        If the server supports TLS, this will encrypt the rest of the SMTP\n        session. If you provide the keyfile and certfile parameters,\n        the identity of the SMTP server and client can be checked. This,\n        however, depends on whether the socket module really checks the\n        certificates.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError            The server didn't reply properly to\n                                  the helo greeting.\n        "));
      var1.setlocal("starttls", var7);
      var3 = null;
      var1.setline(661);
      var6 = new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)};
      var7 = new PyFunction(var1.f_globals, var6, sendmail$45, PyString.fromInterned("This command performs an entire mail transaction.\n\n        The arguments are:\n            - from_addr    : The address sending this mail.\n            - to_addrs     : A list of addresses to send this mail to.  A bare\n                             string will be treated as a list with 1 address.\n            - msg          : The message to send.\n            - mail_options : List of ESMTP options (such as 8bitmime) for the\n                             mail command.\n            - rcpt_options : List of ESMTP options (such as DSN commands) for\n                             all the rcpt commands.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.  If the server does ESMTP, message size\n        and each of the specified options will be passed to it.  If EHLO\n        fails, HELO will be tried and ESMTP options suppressed.\n\n        This method will return normally if the mail is accepted for at least\n        one recipient.  It returns a dictionary, with one entry for each\n        recipient that was refused.  Each entry contains a tuple of the SMTP\n        error code and the accompanying error message sent by the server.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError          The server didn't reply properly to\n                                the helo greeting.\n         SMTPRecipientsRefused  The server rejected ALL recipients\n                                (no mail was sent).\n         SMTPSenderRefused      The server didn't accept the from_addr.\n         SMTPDataError          The server replied with an unexpected\n                                error code (other than a refusal of\n                                a recipient).\n\n        Note: the connection will be open even after an exception is raised.\n\n        Example:\n\n         >>> import smtplib\n         >>> s=smtplib.SMTP(\"localhost\")\n         >>> tolist=[\"one@one.org\",\"two@two.org\",\"three@three.org\",\"four@four.org\"]\n         >>> msg = '''\\\n         ... From: Me@my.org\n         ... Subject: testin'...\n         ...\n         ... This is a test '''\n         >>> s.sendmail(\"me@my.org\",tolist,msg)\n         { \"three@three.org\" : ( 550 ,\"User unknown\" ) }\n         >>> s.quit()\n\n        In the above example, the message was accepted for delivery to three\n        of the four addresses, and one was rejected, with the error code\n        550.  If all addresses are accepted, then the method will return an\n        empty dictionary.\n\n        "));
      var1.setlocal("sendmail", var7);
      var3 = null;
      var1.setline(751);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, close$46, PyString.fromInterned("Close the connection to the SMTP server."));
      var1.setlocal("close", var7);
      var3 = null;
      var1.setline(761);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, quit$47, PyString.fromInterned("Terminate the SMTP session."));
      var1.setlocal("quit", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyString.fromInterned("Initialize a new instance.\n\n        If specified, `host' is the name of the remote host to which to\n        connect.  If specified, `port' specifies the port to which to connect.\n        By default, smtplib.SMTP_PORT is used.  If a host is specified the\n        connect method is called, and if it returns anything other than a\n        success code an SMTPConnectError is raised.  If specified,\n        `local_hostname` is used as the FQDN of the local host for the\n        HELO/EHLO command.  Otherwise, the local hostname is found using\n        socket.getfqdn().\n\n        ");
      var1.setline(253);
      PyObject var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("timeout", var3);
      var3 = null;
      var1.setline(254);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"esmtp_features", var7);
      var3 = null;
      var1.setline(255);
      PyObject var10000;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(256);
         var3 = var1.getlocal(0).__getattr__("connect").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(257);
         var3 = var1.getlocal(5);
         var10000 = var3._ne(Py.newInteger(220));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(258);
            throw Py.makeException(var1.getglobal("SMTPConnectError").__call__(var2, var1.getlocal(5), var1.getlocal(6)));
         }
      }

      var1.setline(259);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(260);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("local_hostname", var3);
         var3 = null;
      } else {
         var1.setline(265);
         var3 = var1.getglobal("socket").__getattr__("getfqdn").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(266);
         PyString var8 = PyString.fromInterned(".");
         var10000 = var8._in(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(267);
            var3 = var1.getlocal(7);
            var1.getlocal(0).__setattr__("local_hostname", var3);
            var3 = null;
         } else {
            var1.setline(270);
            var8 = PyString.fromInterned("127.0.0.1");
            var1.setlocal(8, var8);
            var3 = null;

            try {
               var1.setline(272);
               var3 = var1.getglobal("socket").__getattr__("gethostbyname").__call__(var2, var1.getglobal("socket").__getattr__("gethostname").__call__(var2));
               var1.setlocal(8, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (!var9.match(var1.getglobal("socket").__getattr__("gaierror"))) {
                  throw var9;
               }

               var1.setline(274);
            }

            var1.setline(275);
            var3 = PyString.fromInterned("[%s]")._mod(var1.getlocal(8));
            var1.getlocal(0).__setattr__("local_hostname", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_debuglevel$22(PyFrame var1, ThreadState var2) {
      var1.setline(283);
      PyString.fromInterned("Set the debug output level.\n\n        A non-false value results in debug messages for connection and for all\n        messages sent to and received from the server.\n\n        ");
      var1.setline(284);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("debuglevel", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_socket$23(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyObject var3 = var1.getlocal(0).__getattr__("debuglevel");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var3 = var1.getglobal("stderr");
         Py.printComma(var3, PyString.fromInterned("connect:"));
         Py.println(var3, new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
      }

      var1.setline(291);
      var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})), (PyObject)var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject connect$24(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyString.fromInterned("Connect to a host on a given port.\n\n        If the hostname ends with a colon (`:') followed by a number, and\n        there is no port specified, that suffix will be stripped off and the\n        number interpreted as the port number to use.\n\n        Note: This method is automatically invoked by __init__, if a host is\n        specified during instantiation.\n\n        ");
      var1.setline(304);
      PyObject var10000 = var1.getlocal(2).__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var10000 = var3._eq(var1.getlocal(1).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":")));
         var3 = null;
      }

      PyObject[] var4;
      PyObject var5;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(305);
         var3 = var1.getlocal(1).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(306);
         var3 = var1.getlocal(3);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(307);
            var7 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null), var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
            var4 = Py.unpackSequence(var7, 2);
            var5 = var4[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;

            try {
               var1.setline(309);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
               var1.setlocal(2, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var8 = Py.setException(var6, var1);
               if (var8.match(var1.getglobal("ValueError"))) {
                  var1.setline(311);
                  throw Py.makeException(var1.getglobal("socket").__getattr__("error"), PyString.fromInterned("nonnumeric port"));
               }

               throw var8;
            }
         }
      }

      var1.setline(312);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(313);
         var3 = var1.getlocal(0).__getattr__("default_port");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(314);
      var3 = var1.getlocal(0).__getattr__("debuglevel");
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(315);
         var3 = var1.getglobal("stderr");
         Py.printComma(var3, PyString.fromInterned("connect:"));
         Py.println(var3, new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
      }

      var1.setline(316);
      var3 = var1.getlocal(0).__getattr__("_get_socket").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("timeout"));
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.setline(317);
      var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(318);
      var3 = var1.getlocal(0).__getattr__("debuglevel");
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(319);
         var3 = var1.getglobal("stderr");
         Py.printComma(var3, PyString.fromInterned("connect:"));
         Py.println(var3, var1.getlocal(5));
      }

      var1.setline(320);
      var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject send$25(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyString.fromInterned("Send `str' to the server.");
      var1.setline(324);
      PyObject var3 = var1.getlocal(0).__getattr__("debuglevel");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(325);
         var3 = var1.getglobal("stderr");
         Py.printComma(var3, PyString.fromInterned("send:"));
         Py.println(var3, var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(326);
      var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("sock"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("sock");
      }

      if (var10000.__nonzero__()) {
         try {
            var1.setline(328);
            var1.getlocal(0).__getattr__("sock").__getattr__("sendall").__call__(var2, var1.getlocal(1));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("socket").__getattr__("error"))) {
               var1.setline(330);
               var1.getlocal(0).__getattr__("close").__call__(var2);
               var1.setline(331);
               throw Py.makeException(var1.getglobal("SMTPServerDisconnected").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Server not connected")));
            }

            throw var5;
         }

         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(333);
         throw Py.makeException(var1.getglobal("SMTPServerDisconnected").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("please run connect() first")));
      }
   }

   public PyObject putcmd$26(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyString.fromInterned("Send a command to the server.");
      var1.setline(337);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(338);
         var3 = PyString.fromInterned("%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("CRLF")}));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(340);
         var3 = PyString.fromInterned("%s %s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getglobal("CRLF")}));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(341);
      var1.getlocal(0).__getattr__("send").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getreply$27(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyString.fromInterned("Get a reply from the server.\n\n        Returns a tuple consisting of:\n\n          - server response code (e.g. '250', or such, if all goes well)\n            Note: returns -1 if it can't read response code.\n\n          - server response string corresponding to response code (multiline\n            responses are converted to a single, multiline string).\n\n        Raises SMTPServerDisconnected if end-of-file is reached.\n        ");
      var1.setline(356);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(357);
      PyObject var7 = var1.getlocal(0).__getattr__("file");
      PyObject var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(358);
         var7 = var1.getlocal(0).__getattr__("sock").__getattr__("makefile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rb"));
         var1.getlocal(0).__setattr__("file", var7);
         var3 = null;
      }

      do {
         var1.setline(359);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         PyObject var4;
         PyException var9;
         try {
            var1.setline(361);
            var7 = var1.getlocal(0).__getattr__("file").__getattr__("readline").__call__(var2, var1.getglobal("_MAXLINE")._add(Py.newInteger(1)));
            var1.setlocal(2, var7);
            var3 = null;
         } catch (Throwable var5) {
            var9 = Py.setException(var5, var1);
            if (var9.match(var1.getglobal("socket").__getattr__("error"))) {
               var4 = var9.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(363);
               var1.getlocal(0).__getattr__("close").__call__(var2);
               var1.setline(364);
               throw Py.makeException(var1.getglobal("SMTPServerDisconnected").__call__(var2, PyString.fromInterned("Connection unexpectedly closed: ")._add(var1.getglobal("str").__call__(var2, var1.getlocal(3)))));
            }

            throw var9;
         }

         var1.setline(366);
         var7 = var1.getlocal(2);
         var10000 = var7._eq(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(367);
            var1.getlocal(0).__getattr__("close").__call__(var2);
            var1.setline(368);
            throw Py.makeException(var1.getglobal("SMTPServerDisconnected").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Connection unexpectedly closed")));
         }

         var1.setline(369);
         var7 = var1.getlocal(0).__getattr__("debuglevel");
         var10000 = var7._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(370);
            var7 = var1.getglobal("stderr");
            Py.printComma(var7, PyString.fromInterned("reply:"));
            Py.println(var7, var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
         }

         var1.setline(371);
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var7._gt(var1.getglobal("_MAXLINE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(372);
            throw Py.makeException(var1.getglobal("SMTPResponseException").__call__((ThreadState)var2, (PyObject)Py.newInteger(500), (PyObject)PyString.fromInterned("Line too long.")));
         }

         var1.setline(373);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(4), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2));
         var1.setline(374);
         var7 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var1.setlocal(4, var7);
         var3 = null;

         try {
            var1.setline(378);
            var7 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var7);
            var3 = null;
         } catch (Throwable var6) {
            var9 = Py.setException(var6, var1);
            if (var9.match(var1.getglobal("ValueError"))) {
               var1.setline(380);
               PyInteger var8 = Py.newInteger(-1);
               var1.setlocal(5, var8);
               var4 = null;
               break;
            }

            throw var9;
         }

         var1.setline(383);
         var7 = var1.getlocal(2).__getslice__(Py.newInteger(3), Py.newInteger(4), (PyObject)null);
         var10000 = var7._ne(PyString.fromInterned("-"));
         var3 = null;
      } while(!var10000.__nonzero__());

      var1.setline(386);
      var7 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(1));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(387);
      var7 = var1.getlocal(0).__getattr__("debuglevel");
      var10000 = var7._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(388);
         var7 = var1.getglobal("stderr");
         Py.println(var7, PyString.fromInterned("reply: retcode (%s); Msg: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
      }

      var1.setline(389);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject docmd$28(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyString.fromInterned("Send a command, and return its response code.");
      var1.setline(393);
      var1.getlocal(0).__getattr__("putcmd").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(394);
      PyObject var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject helo$29(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyString.fromInterned("SMTP 'helo' command.\n        Hostname to send for this command defaults to the FQDN of the local\n        host.\n        ");
      var1.setline(402);
      PyObject var10000 = var1.getlocal(0).__getattr__("putcmd");
      PyString var10002 = PyString.fromInterned("helo");
      PyObject var10003 = var1.getlocal(1);
      if (!var10003.__nonzero__()) {
         var10003 = var1.getlocal(0).__getattr__("local_hostname");
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003);
      var1.setline(403);
      PyObject var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(404);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("helo_resp", var3);
      var3 = null;
      var1.setline(405);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject ehlo$30(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      PyString.fromInterned(" SMTP 'ehlo' command.\n        Hostname to send for this command defaults to the FQDN of the local\n        host.\n        ");
      var1.setline(412);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"esmtp_features", var3);
      var3 = null;
      var1.setline(413);
      PyObject var10000 = var1.getlocal(0).__getattr__("putcmd");
      PyObject var10002 = var1.getlocal(0).__getattr__("ehlo_msg");
      PyObject var10003 = var1.getlocal(1);
      if (!var10003.__nonzero__()) {
         var10003 = var1.getlocal(0).__getattr__("local_hostname");
      }

      var10000.__call__(var2, var10002, var10003);
      var1.setline(414);
      PyObject var7 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var7, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(418);
      var7 = var1.getlocal(2);
      var10000 = var7._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var7._eq(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(419);
         var1.getlocal(0).__getattr__("close").__call__(var2);
         var1.setline(420);
         throw Py.makeException(var1.getglobal("SMTPServerDisconnected").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Server not connected")));
      } else {
         var1.setline(421);
         var7 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("ehlo_resp", var7);
         var3 = null;
         var1.setline(422);
         var7 = var1.getlocal(2);
         var10000 = var7._ne(Py.newInteger(250));
         var3 = null;
         PyTuple var10;
         if (var10000.__nonzero__()) {
            var1.setline(423);
            var10 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(424);
            PyInteger var8 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"does_esmtp", var8);
            var4 = null;
            var1.setline(426);
            PyObject var9 = var1.getlocal(0).__getattr__("ehlo_resp").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.setlocal(4, var9);
            var4 = null;
            var1.setline(427);
            var1.getlocal(4).__delitem__((PyObject)Py.newInteger(0));
            var1.setline(428);
            var9 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(428);
               var5 = var9.__iternext__();
               if (var5 == null) {
                  var1.setline(455);
                  var10 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
                  var1.f_lasti = -1;
                  return var10;
               }

               var1.setlocal(5, var5);
               var1.setline(435);
               PyObject var6 = var1.getglobal("OLDSTYLE_AUTH").__getattr__("match").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(436);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(438);
                  var6 = var1.getlocal(0).__getattr__("esmtp_features").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("auth"), (PyObject)PyString.fromInterned(""))._add(PyString.fromInterned(" "))._add(var1.getlocal(6).__getattr__("groups").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getitem__(Py.newInteger(0)));
                  var1.getlocal(0).__getattr__("esmtp_features").__setitem__((PyObject)PyString.fromInterned("auth"), var6);
                  var6 = null;
               } else {
                  var1.setline(446);
                  var6 = var1.getglobal("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?P<feature>[A-Za-z0-9][A-Za-z0-9\\-]*) ?"), (PyObject)var1.getlocal(5));
                  var1.setlocal(7, var6);
                  var6 = null;
                  var1.setline(447);
                  if (var1.getlocal(7).__nonzero__()) {
                     var1.setline(448);
                     var6 = var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("feature")).__getattr__("lower").__call__(var2);
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(449);
                     var6 = var1.getlocal(7).__getattr__("string").__getslice__(var1.getlocal(7).__getattr__("end").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("feature")), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(450);
                     var6 = var1.getlocal(8);
                     var10000 = var6._eq(PyString.fromInterned("auth"));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(451);
                        var6 = var1.getlocal(0).__getattr__("esmtp_features").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned(""))._add(PyString.fromInterned(" "))._add(var1.getlocal(9));
                        var1.getlocal(0).__getattr__("esmtp_features").__setitem__(var1.getlocal(8), var6);
                        var6 = null;
                     } else {
                        var1.setline(454);
                        var6 = var1.getlocal(9);
                        var1.getlocal(0).__getattr__("esmtp_features").__setitem__(var1.getlocal(8), var6);
                        var6 = null;
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject has_extn$31(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyString.fromInterned("Does the server support a given SMTP service extension?");
      var1.setline(459);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("esmtp_features"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject help$32(PyFrame var1, ThreadState var2) {
      var1.setline(463);
      PyString.fromInterned("SMTP 'help' command.\n        Returns help text from server.");
      var1.setline(464);
      var1.getlocal(0).__getattr__("putcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("help"), (PyObject)var1.getlocal(1));
      var1.setline(465);
      PyObject var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rset$33(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyString.fromInterned("SMTP 'rset' command -- resets session.");
      var1.setline(469);
      PyObject var3 = var1.getlocal(0).__getattr__("docmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rset"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject noop$34(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      PyString.fromInterned("SMTP 'noop' command -- doesn't do anything :>");
      var1.setline(473);
      PyObject var3 = var1.getlocal(0).__getattr__("docmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("noop"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mail$35(PyFrame var1, ThreadState var2) {
      var1.setline(476);
      PyString.fromInterned("SMTP 'mail' command -- begins mail xfer session.");
      var1.setline(477);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(478);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("does_esmtp");
      }

      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(479);
         var4 = PyString.fromInterned(" ")._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(2)));
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(480);
      var1.getlocal(0).__getattr__("putcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mail"), (PyObject)PyString.fromInterned("FROM:%s%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("quoteaddr").__call__(var2, var1.getlocal(1)), var1.getlocal(3)})));
      var1.setline(481);
      var4 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject rcpt$36(PyFrame var1, ThreadState var2) {
      var1.setline(484);
      PyString.fromInterned("SMTP 'rcpt' command -- indicates 1 recipient for this mail.");
      var1.setline(485);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(486);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("does_esmtp");
      }

      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(487);
         var4 = PyString.fromInterned(" ")._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(2)));
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(488);
      var1.getlocal(0).__getattr__("putcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rcpt"), (PyObject)PyString.fromInterned("TO:%s%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("quoteaddr").__call__(var2, var1.getlocal(1)), var1.getlocal(3)})));
      var1.setline(489);
      var4 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject data$37(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyString.fromInterned("SMTP 'DATA' command -- sends message data to server.\n\n        Automatically quotes lines beginning with a period per rfc821.\n        Raises SMTPDataError if there is an unexpected reply to the\n        DATA command; the return value from this method is the final\n        response code received when the all data is sent.\n        ");
      var1.setline(499);
      var1.getlocal(0).__getattr__("putcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data"));
      var1.setline(500);
      PyObject var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(501);
      var3 = var1.getlocal(0).__getattr__("debuglevel");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(502);
         var3 = var1.getglobal("stderr");
         Py.printComma(var3, PyString.fromInterned("data:"));
         Py.println(var3, new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)}));
      }

      var1.setline(503);
      var3 = var1.getlocal(2);
      var10000 = var3._ne(Py.newInteger(354));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(504);
         throw Py.makeException(var1.getglobal("SMTPDataError").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
      } else {
         var1.setline(506);
         var3 = var1.getglobal("quotedata").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(507);
         var3 = var1.getlocal(4).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
         var10000 = var3._ne(var1.getglobal("CRLF"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(508);
            var3 = var1.getlocal(4)._add(var1.getglobal("CRLF"));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(509);
         var3 = var1.getlocal(4)._add(PyString.fromInterned("."))._add(var1.getglobal("CRLF"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(510);
         var1.getlocal(0).__getattr__("send").__call__(var2, var1.getlocal(4));
         var1.setline(511);
         var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
         var1.setline(512);
         var3 = var1.getlocal(0).__getattr__("debuglevel");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(513);
            var3 = var1.getglobal("stderr");
            Py.printComma(var3, PyString.fromInterned("data:"));
            Py.println(var3, new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)}));
         }

         var1.setline(514);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject verify$38(PyFrame var1, ThreadState var2) {
      var1.setline(517);
      PyString.fromInterned("SMTP 'verify' command -- checks for address validity.");
      var1.setline(518);
      var1.getlocal(0).__getattr__("putcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("vrfy"), (PyObject)var1.getglobal("_addr_only").__call__(var2, var1.getlocal(1)));
      var1.setline(519);
      PyObject var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject expn$39(PyFrame var1, ThreadState var2) {
      var1.setline(524);
      PyString.fromInterned("SMTP 'expn' command -- expands a mailing list.");
      var1.setline(525);
      var1.getlocal(0).__getattr__("putcmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expn"), (PyObject)var1.getglobal("_addr_only").__call__(var2, var1.getlocal(1)));
      var1.setline(526);
      PyObject var3 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ehlo_or_helo_if_needed$40(PyFrame var1, ThreadState var2) {
      var1.setline(540);
      PyString.fromInterned("Call self.ehlo() and/or self.helo() if needed.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError            The server didn't reply properly to\n                                  the helo greeting.\n        ");
      var1.setline(541);
      PyObject var3 = var1.getlocal(0).__getattr__("helo_resp");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("ehlo_resp");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(542);
         PyInteger var6 = Py.newInteger(200);
         PyObject var10001 = var1.getlocal(0).__getattr__("ehlo").__call__(var2).__getitem__(Py.newInteger(0));
         PyInteger var8 = var6;
         var3 = var10001;
         PyObject var4;
         if ((var4 = var8._le(var10001)).__nonzero__()) {
            var4 = var3._le(Py.newInteger(299));
         }

         var3 = null;
         if (var4.__not__().__nonzero__()) {
            var1.setline(543);
            var3 = var1.getlocal(0).__getattr__("helo").__call__(var2);
            PyObject[] var7 = Py.unpackSequence(var3, 2);
            PyObject var5 = var7[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var7[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
            var1.setline(544);
            var6 = Py.newInteger(200);
            var10001 = var1.getlocal(1);
            var8 = var6;
            var3 = var10001;
            if ((var4 = var8._le(var10001)).__nonzero__()) {
               var4 = var3._le(Py.newInteger(299));
            }

            var3 = null;
            if (var4.__not__().__nonzero__()) {
               var1.setline(545);
               throw Py.makeException(var1.getglobal("SMTPHeloError").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject login$41(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyString.fromInterned("Log in on an SMTP server that requires authentication.\n\n        The arguments are:\n            - user:     The user name to authenticate with.\n            - password: The password for the authentication.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.\n\n        This method will return normally if the authentication was successful.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError            The server didn't reply properly to\n                                  the helo greeting.\n         SMTPAuthenticationError  The server didn't accept the username/\n                                  password combination.\n         SMTPException            No suitable authentication method was\n                                  found.\n        ");
      var1.setline(569);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var3, encode_cram_md5$42, (PyObject)null);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(574);
      var3 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var3, encode_plain$43, (PyObject)null);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(578);
      PyString var9 = PyString.fromInterned("PLAIN");
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(579);
      var9 = PyString.fromInterned("CRAM-MD5");
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(580);
      var9 = PyString.fromInterned("LOGIN");
      var1.setlocal(7, var9);
      var3 = null;
      var1.setline(582);
      var1.getlocal(0).__getattr__("ehlo_or_helo_if_needed").__call__(var2);
      var1.setline(584);
      if (var1.getlocal(0).__getattr__("has_extn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("auth")).__not__().__nonzero__()) {
         var1.setline(585);
         throw Py.makeException(var1.getglobal("SMTPException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SMTP AUTH extension not supported by server.")));
      } else {
         var1.setline(588);
         PyObject var10 = var1.getlocal(0).__getattr__("esmtp_features").__getitem__(PyString.fromInterned("auth")).__getattr__("split").__call__(var2);
         var1.setlocal(8, var10);
         var3 = null;
         var1.setline(593);
         PyList var11 = new PyList(new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(7)});
         var1.setlocal(9, var11);
         var3 = null;
         var1.setline(596);
         var10 = var1.getglobal("None");
         var1.setlocal(10, var10);
         var3 = null;
         var1.setline(597);
         var10 = var1.getlocal(9).__iter__();

         PyObject var4;
         PyObject var5;
         PyObject var10000;
         while(true) {
            var1.setline(597);
            var4 = var10.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(11, var4);
            var1.setline(598);
            var5 = var1.getlocal(11);
            var10000 = var5._in(var1.getlocal(8));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(599);
               var5 = var1.getlocal(11);
               var1.setlocal(10, var5);
               var5 = null;
               break;
            }
         }

         var1.setline(602);
         var10 = var1.getlocal(10);
         var10000 = var10._eq(var1.getlocal(6));
         var3 = null;
         PyObject var6;
         PyObject[] var8;
         PyObject[] var12;
         PyTuple var14;
         if (var10000.__nonzero__()) {
            var1.setline(603);
            var10 = var1.getlocal(0).__getattr__("docmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AUTH"), (PyObject)var1.getlocal(6));
            var8 = Py.unpackSequence(var10, 2);
            var5 = var8[0];
            var1.setlocal(12, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(13, var5);
            var5 = null;
            var3 = null;
            var1.setline(604);
            var10 = var1.getlocal(12);
            var10000 = var10._eq(Py.newInteger(503));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(606);
               var14 = new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(13)});
               var1.f_lasti = -1;
               return var14;
            }

            var1.setline(607);
            var4 = var1.getlocal(0).__getattr__("docmd").__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(13), var1.getlocal(1), var1.getlocal(2)));
            var12 = Py.unpackSequence(var4, 2);
            var6 = var12[0];
            var1.setlocal(12, var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal(13, var6);
            var6 = null;
            var4 = null;
         } else {
            var1.setline(608);
            var4 = var1.getlocal(10);
            var10000 = var4._eq(var1.getlocal(5));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(609);
               var4 = var1.getlocal(0).__getattr__("docmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AUTH"), (PyObject)var1.getlocal(5)._add(PyString.fromInterned(" "))._add(var1.getlocal(4).__call__(var2, var1.getlocal(1), var1.getlocal(2))));
               var12 = Py.unpackSequence(var4, 2);
               var6 = var12[0];
               var1.setlocal(12, var6);
               var6 = null;
               var6 = var12[1];
               var1.setlocal(13, var6);
               var6 = null;
               var4 = null;
            } else {
               var1.setline(611);
               var4 = var1.getlocal(10);
               var10000 = var4._eq(var1.getlocal(7));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(612);
                  var10000 = var1.getlocal(0).__getattr__("docmd");
                  PyString var10002 = PyString.fromInterned("AUTH");
                  PyString var10003 = PyString.fromInterned("%s %s");
                  PyObject[] var10006 = new PyObject[]{var1.getlocal(7), null};
                  PyObject var10009 = var1.getglobal("encode_base64");
                  var8 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("")};
                  String[] var13 = new String[]{"eol"};
                  var10009 = var10009.__call__(var2, var8, var13);
                  var4 = null;
                  var10006[1] = var10009;
                  var4 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003._mod(new PyTuple(var10006)));
                  var12 = Py.unpackSequence(var4, 2);
                  var6 = var12[0];
                  var1.setlocal(12, var6);
                  var6 = null;
                  var6 = var12[1];
                  var1.setlocal(13, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(614);
                  var4 = var1.getlocal(12);
                  var10000 = var4._ne(Py.newInteger(334));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(615);
                     throw Py.makeException(var1.getglobal("SMTPAuthenticationError").__call__(var2, var1.getlocal(12), var1.getlocal(13)));
                  }

                  var1.setline(616);
                  var10000 = var1.getlocal(0).__getattr__("docmd");
                  PyObject var15 = var1.getglobal("encode_base64");
                  var8 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("")};
                  var13 = new String[]{"eol"};
                  var15 = var15.__call__(var2, var8, var13);
                  var4 = null;
                  var4 = var10000.__call__(var2, var15);
                  var12 = Py.unpackSequence(var4, 2);
                  var6 = var12[0];
                  var1.setlocal(12, var6);
                  var6 = null;
                  var6 = var12[1];
                  var1.setlocal(13, var6);
                  var6 = null;
                  var4 = null;
               } else {
                  var1.setline(617);
                  var4 = var1.getlocal(10);
                  var10000 = var4._is(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(618);
                     throw Py.makeException(var1.getglobal("SMTPException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No suitable authentication method found.")));
                  }
               }
            }
         }

         var1.setline(619);
         var4 = var1.getlocal(12);
         var10000 = var4._notin(new PyTuple(new PyObject[]{Py.newInteger(235), Py.newInteger(503)}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(622);
            throw Py.makeException(var1.getglobal("SMTPAuthenticationError").__call__(var2, var1.getlocal(12), var1.getlocal(13)));
         } else {
            var1.setline(623);
            var14 = new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(13)});
            var1.f_lasti = -1;
            return var14;
         }
      }
   }

   public PyObject encode_cram_md5$42(PyFrame var1, ThreadState var2) {
      var1.setline(570);
      PyObject var3 = var1.getglobal("base64").__getattr__("decodestring").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(571);
      var3 = var1.getlocal(1)._add(PyString.fromInterned(" "))._add(var1.getglobal("hmac").__getattr__("HMAC").__call__(var2, var1.getlocal(2), var1.getlocal(0)).__getattr__("hexdigest").__call__(var2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(572);
      PyObject var10000 = var1.getglobal("encode_base64");
      PyObject[] var5 = new PyObject[]{var1.getlocal(3), PyString.fromInterned("")};
      String[] var4 = new String[]{"eol"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject encode_plain$43(PyFrame var1, ThreadState var2) {
      var1.setline(575);
      PyObject var10000 = var1.getglobal("encode_base64");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("\u0000%s\u0000%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})), PyString.fromInterned("")};
      String[] var4 = new String[]{"eol"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject starttls$44(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyString.fromInterned("Puts the connection to the SMTP server into TLS mode.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.\n\n        If the server supports TLS, this will encrypt the rest of the SMTP\n        session. If you provide the keyfile and certfile parameters,\n        the identity of the SMTP server and client can be checked. This,\n        however, depends on whether the socket module really checks the\n        certificates.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError            The server didn't reply properly to\n                                  the helo greeting.\n        ");
      var1.setline(642);
      var1.getlocal(0).__getattr__("ehlo_or_helo_if_needed").__call__(var2);
      var1.setline(643);
      if (var1.getlocal(0).__getattr__("has_extn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("starttls")).__not__().__nonzero__()) {
         var1.setline(644);
         throw Py.makeException(var1.getglobal("SMTPException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STARTTLS extension not supported by server.")));
      } else {
         var1.setline(645);
         PyObject var3 = var1.getlocal(0).__getattr__("docmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STARTTLS"));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(646);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._eq(Py.newInteger(220));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(647);
            if (var1.getglobal("_have_ssl").__not__().__nonzero__()) {
               var1.setline(648);
               throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No SSL support included in this Python")));
            }

            var1.setline(649);
            var3 = var1.getglobal("ssl").__getattr__("wrap_socket").__call__(var2, var1.getlocal(0).__getattr__("sock"), var1.getlocal(1), var1.getlocal(2));
            var1.getlocal(0).__setattr__("sock", var3);
            var3 = null;
            var1.setline(650);
            var3 = var1.getglobal("SSLFakeFile").__call__(var2, var1.getlocal(0).__getattr__("sock"));
            var1.getlocal(0).__setattr__("file", var3);
            var3 = null;
            var1.setline(655);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("helo_resp", var3);
            var3 = null;
            var1.setline(656);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("ehlo_resp", var3);
            var3 = null;
            var1.setline(657);
            PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"esmtp_features", var6);
            var3 = null;
            var1.setline(658);
            PyInteger var7 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"does_esmtp", var7);
            var3 = null;
         }

         var1.setline(659);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject sendmail$45(PyFrame var1, ThreadState var2) {
      var1.setline(717);
      PyString.fromInterned("This command performs an entire mail transaction.\n\n        The arguments are:\n            - from_addr    : The address sending this mail.\n            - to_addrs     : A list of addresses to send this mail to.  A bare\n                             string will be treated as a list with 1 address.\n            - msg          : The message to send.\n            - mail_options : List of ESMTP options (such as 8bitmime) for the\n                             mail command.\n            - rcpt_options : List of ESMTP options (such as DSN commands) for\n                             all the rcpt commands.\n\n        If there has been no previous EHLO or HELO command this session, this\n        method tries ESMTP EHLO first.  If the server does ESMTP, message size\n        and each of the specified options will be passed to it.  If EHLO\n        fails, HELO will be tried and ESMTP options suppressed.\n\n        This method will return normally if the mail is accepted for at least\n        one recipient.  It returns a dictionary, with one entry for each\n        recipient that was refused.  Each entry contains a tuple of the SMTP\n        error code and the accompanying error message sent by the server.\n\n        This method may raise the following exceptions:\n\n         SMTPHeloError          The server didn't reply properly to\n                                the helo greeting.\n         SMTPRecipientsRefused  The server rejected ALL recipients\n                                (no mail was sent).\n         SMTPSenderRefused      The server didn't accept the from_addr.\n         SMTPDataError          The server replied with an unexpected\n                                error code (other than a refusal of\n                                a recipient).\n\n        Note: the connection will be open even after an exception is raised.\n\n        Example:\n\n         >>> import smtplib\n         >>> s=smtplib.SMTP(\"localhost\")\n         >>> tolist=[\"one@one.org\",\"two@two.org\",\"three@three.org\",\"four@four.org\"]\n         >>> msg = '''\\\n         ... From: Me@my.org\n         ... Subject: testin'...\n         ...\n         ... This is a test '''\n         >>> s.sendmail(\"me@my.org\",tolist,msg)\n         { \"three@three.org\" : ( 550 ,\"User unknown\" ) }\n         >>> s.quit()\n\n        In the above example, the message was accepted for delivery to three\n        of the four addresses, and one was rejected, with the error code\n        550.  If all addresses are accepted, then the method will return an\n        empty dictionary.\n\n        ");
      var1.setline(718);
      var1.getlocal(0).__getattr__("ehlo_or_helo_if_needed").__call__(var2);
      var1.setline(719);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(720);
      PyObject var4;
      PyObject var8;
      if (var1.getlocal(0).__getattr__("does_esmtp").__nonzero__()) {
         var1.setline(723);
         if (var1.getlocal(0).__getattr__("has_extn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("size")).__nonzero__()) {
            var1.setline(724);
            var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("size=%d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(3))));
         }

         var1.setline(725);
         var8 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(725);
            var4 = var8.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(7, var4);
            var1.setline(726);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(7));
         }
      }

      var1.setline(728);
      var8 = var1.getlocal(0).__getattr__("mail").__call__(var2, var1.getlocal(1), var1.getlocal(6));
      PyObject[] var9 = Py.unpackSequence(var8, 2);
      PyObject var5 = var9[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var9[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(729);
      var8 = var1.getlocal(8);
      PyObject var10000 = var8._ne(Py.newInteger(250));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(730);
         var1.getlocal(0).__getattr__("rset").__call__(var2);
         var1.setline(731);
         throw Py.makeException(var1.getglobal("SMTPSenderRefused").__call__(var2, var1.getlocal(8), var1.getlocal(9), var1.getlocal(1)));
      } else {
         var1.setline(732);
         PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(10, var10);
         var3 = null;
         var1.setline(733);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(734);
            var3 = new PyList(new PyObject[]{var1.getlocal(2)});
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(735);
         var8 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(735);
            var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(739);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
               var10000 = var8._eq(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(741);
                  var1.getlocal(0).__getattr__("rset").__call__(var2);
                  var1.setline(742);
                  throw Py.makeException(var1.getglobal("SMTPRecipientsRefused").__call__(var2, var1.getlocal(10)));
               }

               var1.setline(743);
               var8 = var1.getlocal(0).__getattr__("data").__call__(var2, var1.getlocal(3));
               var9 = Py.unpackSequence(var8, 2);
               var5 = var9[0];
               var1.setlocal(8, var5);
               var5 = null;
               var5 = var9[1];
               var1.setlocal(9, var5);
               var5 = null;
               var3 = null;
               var1.setline(744);
               var8 = var1.getlocal(8);
               var10000 = var8._ne(Py.newInteger(250));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(745);
                  var1.getlocal(0).__getattr__("rset").__call__(var2);
                  var1.setline(746);
                  throw Py.makeException(var1.getglobal("SMTPDataError").__call__(var2, var1.getlocal(8), var1.getlocal(9)));
               }

               var1.setline(748);
               var8 = var1.getlocal(10);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setlocal(11, var4);
            var1.setline(736);
            var5 = var1.getlocal(0).__getattr__("rcpt").__call__(var2, var1.getlocal(11), var1.getlocal(5));
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(8, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(9, var7);
            var7 = null;
            var5 = null;
            var1.setline(737);
            var5 = var1.getlocal(8);
            var10000 = var5._ne(Py.newInteger(250));
            var5 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(8);
               var10000 = var5._ne(Py.newInteger(251));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(738);
               PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)});
               var1.getlocal(10).__setitem__((PyObject)var1.getlocal(11), var11);
               var5 = null;
            }
         }
      }
   }

   public PyObject close$46(PyFrame var1, ThreadState var2) {
      var1.setline(752);
      PyString.fromInterned("Close the connection to the SMTP server.");
      var1.setline(753);
      if (var1.getlocal(0).__getattr__("file").__nonzero__()) {
         var1.setline(754);
         var1.getlocal(0).__getattr__("file").__getattr__("close").__call__(var2);
      }

      var1.setline(755);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(756);
      if (var1.getlocal(0).__getattr__("sock").__nonzero__()) {
         var1.setline(757);
         var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
      }

      var1.setline(758);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sock", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject quit$47(PyFrame var1, ThreadState var2) {
      var1.setline(762);
      PyString.fromInterned("Terminate the SMTP session.");
      var1.setline(763);
      PyObject var3 = var1.getlocal(0).__getattr__("docmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("quit"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(765);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("ehlo_resp", var3);
      var1.getlocal(0).__setattr__("helo_resp", var3);
      var1.setline(766);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"esmtp_features", var4);
      var3 = null;
      var1.setline(767);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("does_esmtp", var3);
      var3 = null;
      var1.setline(768);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.setline(769);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SMTP_SSL$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" This is a subclass derived from SMTP that connects over an SSL\n        encrypted socket (to use this class you need a socket module that was\n        compiled with SSL support). If host is not specified, '' (the local\n        host) is used. If port is omitted, the standard SMTP-over-SSL port\n        (465) is used.  local_hostname has the same meaning as it does in the\n        SMTP class.  keyfile and certfile are also optional - they can contain\n        a PEM formatted private key and certificate chain file for the SSL\n        connection.\n\n        "));
      var1.setline(783);
      PyString.fromInterned(" This is a subclass derived from SMTP that connects over an SSL\n        encrypted socket (to use this class you need a socket module that was\n        compiled with SSL support). If host is not specified, '' (the local\n        host) is used. If port is omitted, the standard SMTP-over-SSL port\n        (465) is used.  local_hostname has the same meaning as it does in the\n        SMTP class.  keyfile and certfile are also optional - they can contain\n        a PEM formatted private key and certificate chain file for the SSL\n        connection.\n\n        ");
      var1.setline(785);
      PyObject var3 = var1.getname("SMTP_SSL_PORT");
      var1.setlocal("default_port", var3);
      var3 = null;
      var1.setline(787);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned(""), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("socket").__getattr__("_GLOBAL_DEFAULT_TIMEOUT")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$49, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(794);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_socket$50, (PyObject)null);
      var1.setlocal("_get_socket", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$49(PyFrame var1, ThreadState var2) {
      var1.setline(790);
      PyObject var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("keyfile", var3);
      var3 = null;
      var1.setline(791);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("certfile", var3);
      var3 = null;
      var1.setline(792);
      PyObject var10000 = var1.getglobal("SMTP").__getattr__("__init__");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(6)};
      var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_socket$50(PyFrame var1, ThreadState var2) {
      var1.setline(795);
      PyObject var3 = var1.getlocal(0).__getattr__("debuglevel");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(796);
         var3 = var1.getglobal("stderr");
         Py.printComma(var3, PyString.fromInterned("connect:"));
         Py.println(var3, new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
      }

      var1.setline(797);
      var3 = var1.getglobal("socket").__getattr__("create_connection").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})), (PyObject)var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(798);
      var3 = var1.getglobal("ssl").__getattr__("wrap_socket").__call__(var2, var1.getlocal(4), var1.getlocal(0).__getattr__("keyfile"), var1.getlocal(0).__getattr__("certfile"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(799);
      var3 = var1.getglobal("SSLFakeFile").__call__(var2, var1.getlocal(4));
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(800);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LMTP$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("LMTP - Local Mail Transfer Protocol\n\n    The LMTP protocol, which is very similar to ESMTP, is heavily based\n    on the standard SMTP client. It's common to use Unix sockets for\n    LMTP, so our connect() method must support that as well as a regular\n    host:port server.  local_hostname has the same meaning as it does in\n    the SMTP class.  To specify a Unix socket, you must use an absolute\n    path as the host, starting with a '/'.\n\n    Authentication is supported, using the regular SMTP mechanism. When\n    using a Unix socket, LMTP generally don't support or require any\n    authentication, but your mileage might vary."));
      var1.setline(821);
      PyString.fromInterned("LMTP - Local Mail Transfer Protocol\n\n    The LMTP protocol, which is very similar to ESMTP, is heavily based\n    on the standard SMTP client. It's common to use Unix sockets for\n    LMTP, so our connect() method must support that as well as a regular\n    host:port server.  local_hostname has the same meaning as it does in\n    the SMTP class.  To specify a Unix socket, you must use an absolute\n    path as the host, starting with a '/'.\n\n    Authentication is supported, using the regular SMTP mechanism. When\n    using a Unix socket, LMTP generally don't support or require any\n    authentication, but your mileage might vary.");
      var1.setline(823);
      PyString var3 = PyString.fromInterned("lhlo");
      var1.setlocal("ehlo_msg", var3);
      var3 = null;
      var1.setline(825);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned(""), var1.getname("LMTP_PORT"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$52, PyString.fromInterned("Initialize a new instance."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(829);
      var4 = new PyObject[]{PyString.fromInterned("localhost"), Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, connect$53, PyString.fromInterned("Connect to the LMTP daemon, on either a Unix or a TCP socket."));
      var1.setlocal("connect", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$52(PyFrame var1, ThreadState var2) {
      var1.setline(826);
      PyString.fromInterned("Initialize a new instance.");
      var1.setline(827);
      var1.getglobal("SMTP").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect$53(PyFrame var1, ThreadState var2) {
      var1.setline(830);
      PyString.fromInterned("Connect to the LMTP daemon, on either a Unix or a TCP socket.");
      var1.setline(831);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(832);
         var3 = var1.getglobal("SMTP").__getattr__("connect").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var9;
         try {
            var1.setline(836);
            var9 = var1.getglobal("socket").__getattr__("socket").__call__(var2, var1.getglobal("socket").__getattr__("AF_UNIX"), var1.getglobal("socket").__getattr__("SOCK_STREAM"));
            var1.getlocal(0).__setattr__("sock", var9);
            var4 = null;
            var1.setline(837);
            var1.getlocal(0).__getattr__("sock").__getattr__("connect").__call__(var2, var1.getlocal(1));
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (var4.match(var1.getglobal("socket").__getattr__("error"))) {
               var1.setline(839);
               PyObject var5 = var1.getlocal(0).__getattr__("debuglevel");
               var10000 = var5._gt(Py.newInteger(0));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(840);
                  var5 = var1.getglobal("stderr");
                  Py.printComma(var5, PyString.fromInterned("connect fail:"));
                  Py.println(var5, var1.getlocal(1));
               }

               var1.setline(841);
               if (var1.getlocal(0).__getattr__("sock").__nonzero__()) {
                  var1.setline(842);
                  var1.getlocal(0).__getattr__("sock").__getattr__("close").__call__(var2);
               }

               var1.setline(843);
               var5 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("sock", var5);
               var5 = null;
               var1.setline(844);
               throw Py.makeException();
            }

            throw var4;
         }

         var1.setline(845);
         var9 = var1.getlocal(0).__getattr__("getreply").__call__(var2);
         PyObject[] var10 = Py.unpackSequence(var9, 2);
         PyObject var6 = var10[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(846);
         var9 = var1.getlocal(0).__getattr__("debuglevel");
         var10000 = var9._gt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(847);
            var9 = var1.getglobal("stderr");
            Py.printComma(var9, PyString.fromInterned("connect:"));
            Py.println(var9, var1.getlocal(4));
         }

         var1.setline(848);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject prompt$54(PyFrame var1, ThreadState var2) {
      var1.setline(857);
      var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(0)._add(PyString.fromInterned(": ")));
      var1.setline(858);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdin").__getattr__("readline").__call__(var2).__getattr__("strip").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public smtplib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SMTPException$1 = Py.newCode(0, var2, var1, "SMTPException", 66, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SMTPServerDisconnected$2 = Py.newCode(0, var2, var1, "SMTPServerDisconnected", 69, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SMTPResponseException$3 = Py.newCode(0, var2, var1, "SMTPResponseException", 77, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "code", "msg"};
      __init__$4 = Py.newCode(3, var2, var1, "__init__", 86, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTPSenderRefused$5 = Py.newCode(0, var2, var1, "SMTPSenderRefused", 91, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "code", "msg", "sender"};
      __init__$6 = Py.newCode(4, var2, var1, "__init__", 98, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTPRecipientsRefused$7 = Py.newCode(0, var2, var1, "SMTPRecipientsRefused", 104, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "recipients"};
      __init__$8 = Py.newCode(2, var2, var1, "__init__", 112, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTPDataError$9 = Py.newCode(0, var2, var1, "SMTPDataError", 117, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SMTPConnectError$10 = Py.newCode(0, var2, var1, "SMTPConnectError", 120, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SMTPHeloError$11 = Py.newCode(0, var2, var1, "SMTPHeloError", 123, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SMTPAuthenticationError$12 = Py.newCode(0, var2, var1, "SMTPAuthenticationError", 126, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"addr", "m"};
      quoteaddr$13 = Py.newCode(1, var2, var1, "quoteaddr", 134, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addrstring", "displayname", "addr"};
      _addr_only$14 = Py.newCode(1, var2, var1, "_addr_only", 153, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data"};
      quotedata$15 = Py.newCode(1, var2, var1, "quotedata", 160, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SSLFakeFile$16 = Py.newCode(0, var2, var1, "SSLFakeFile", 175, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sslobj"};
      __init__$17 = Py.newCode(2, var2, var1, "__init__", 180, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "str", "chr"};
      readline$18 = Py.newCode(2, var2, var1, "readline", 183, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$19 = Py.newCode(1, var2, var1, "close", 197, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTP$20 = Py.newCode(0, var2, var1, "SMTP", 202, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "local_hostname", "timeout", "code", "msg", "fqdn", "addr"};
      __init__$21 = Py.newCode(5, var2, var1, "__init__", 239, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "debuglevel"};
      set_debuglevel$22 = Py.newCode(2, var2, var1, "set_debuglevel", 277, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "timeout"};
      _get_socket$23 = Py.newCode(4, var2, var1, "_get_socket", 286, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "i", "code", "msg"};
      connect$24 = Py.newCode(3, var2, var1, "connect", 293, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "str"};
      send$25 = Py.newCode(2, var2, var1, "send", 322, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "args", "str"};
      putcmd$26 = Py.newCode(3, var2, var1, "putcmd", 335, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "resp", "line", "e", "code", "errcode", "errmsg"};
      getreply$27 = Py.newCode(1, var2, var1, "getreply", 343, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "args"};
      docmd$28 = Py.newCode(3, var2, var1, "docmd", 391, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "code", "msg"};
      helo$29 = Py.newCode(2, var2, var1, "helo", 397, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "code", "msg", "resp", "each", "auth_match", "m", "feature", "params"};
      ehlo$30 = Py.newCode(2, var2, var1, "ehlo", 407, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt"};
      has_extn$31 = Py.newCode(2, var2, var1, "has_extn", 457, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      help$32 = Py.newCode(2, var2, var1, "help", 461, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      rset$33 = Py.newCode(1, var2, var1, "rset", 467, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      noop$34 = Py.newCode(1, var2, var1, "noop", 471, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sender", "options", "optionlist"};
      mail$35 = Py.newCode(3, var2, var1, "mail", 475, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "recip", "options", "optionlist"};
      rcpt$36 = Py.newCode(3, var2, var1, "rcpt", 483, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "code", "repl", "q"};
      data$37 = Py.newCode(2, var2, var1, "data", 491, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "address"};
      verify$38 = Py.newCode(2, var2, var1, "verify", 516, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "address"};
      expn$39 = Py.newCode(2, var2, var1, "expn", 523, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "resp"};
      ehlo_or_helo_if_needed$40 = Py.newCode(1, var2, var1, "ehlo_or_helo_if_needed", 530, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user", "password", "encode_cram_md5", "encode_plain", "AUTH_PLAIN", "AUTH_CRAM_MD5", "AUTH_LOGIN", "authlist", "preferred_auths", "authmethod", "method", "code", "resp"};
      login$41 = Py.newCode(3, var2, var1, "login", 547, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"challenge", "user", "password", "response"};
      encode_cram_md5$42 = Py.newCode(3, var2, var1, "encode_cram_md5", 569, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"user", "password"};
      encode_plain$43 = Py.newCode(2, var2, var1, "encode_plain", 574, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "keyfile", "certfile", "resp", "reply"};
      starttls$44 = Py.newCode(3, var2, var1, "starttls", 625, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "from_addr", "to_addrs", "msg", "mail_options", "rcpt_options", "esmtp_opts", "option", "code", "resp", "senderrs", "each"};
      sendmail$45 = Py.newCode(6, var2, var1, "sendmail", 661, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$46 = Py.newCode(1, var2, var1, "close", 751, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "res"};
      quit$47 = Py.newCode(1, var2, var1, "quit", 761, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SMTP_SSL$48 = Py.newCode(0, var2, var1, "SMTP_SSL", 773, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "local_hostname", "keyfile", "certfile", "timeout"};
      __init__$49 = Py.newCode(7, var2, var1, "__init__", 787, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "timeout", "new_socket"};
      _get_socket$50 = Py.newCode(4, var2, var1, "_get_socket", 794, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LMTP$51 = Py.newCode(0, var2, var1, "LMTP", 809, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "host", "port", "local_hostname"};
      __init__$52 = Py.newCode(4, var2, var1, "__init__", 825, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "host", "port", "code", "msg"};
      connect$53 = Py.newCode(3, var2, var1, "connect", 829, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prompt"};
      prompt$54 = Py.newCode(1, var2, var1, "prompt", 856, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new smtplib$py("smtplib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(smtplib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SMTPException$1(var2, var3);
         case 2:
            return this.SMTPServerDisconnected$2(var2, var3);
         case 3:
            return this.SMTPResponseException$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.SMTPSenderRefused$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.SMTPRecipientsRefused$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.SMTPDataError$9(var2, var3);
         case 10:
            return this.SMTPConnectError$10(var2, var3);
         case 11:
            return this.SMTPHeloError$11(var2, var3);
         case 12:
            return this.SMTPAuthenticationError$12(var2, var3);
         case 13:
            return this.quoteaddr$13(var2, var3);
         case 14:
            return this._addr_only$14(var2, var3);
         case 15:
            return this.quotedata$15(var2, var3);
         case 16:
            return this.SSLFakeFile$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.readline$18(var2, var3);
         case 19:
            return this.close$19(var2, var3);
         case 20:
            return this.SMTP$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.set_debuglevel$22(var2, var3);
         case 23:
            return this._get_socket$23(var2, var3);
         case 24:
            return this.connect$24(var2, var3);
         case 25:
            return this.send$25(var2, var3);
         case 26:
            return this.putcmd$26(var2, var3);
         case 27:
            return this.getreply$27(var2, var3);
         case 28:
            return this.docmd$28(var2, var3);
         case 29:
            return this.helo$29(var2, var3);
         case 30:
            return this.ehlo$30(var2, var3);
         case 31:
            return this.has_extn$31(var2, var3);
         case 32:
            return this.help$32(var2, var3);
         case 33:
            return this.rset$33(var2, var3);
         case 34:
            return this.noop$34(var2, var3);
         case 35:
            return this.mail$35(var2, var3);
         case 36:
            return this.rcpt$36(var2, var3);
         case 37:
            return this.data$37(var2, var3);
         case 38:
            return this.verify$38(var2, var3);
         case 39:
            return this.expn$39(var2, var3);
         case 40:
            return this.ehlo_or_helo_if_needed$40(var2, var3);
         case 41:
            return this.login$41(var2, var3);
         case 42:
            return this.encode_cram_md5$42(var2, var3);
         case 43:
            return this.encode_plain$43(var2, var3);
         case 44:
            return this.starttls$44(var2, var3);
         case 45:
            return this.sendmail$45(var2, var3);
         case 46:
            return this.close$46(var2, var3);
         case 47:
            return this.quit$47(var2, var3);
         case 48:
            return this.SMTP_SSL$48(var2, var3);
         case 49:
            return this.__init__$49(var2, var3);
         case 50:
            return this._get_socket$50(var2, var3);
         case 51:
            return this.LMTP$51(var2, var3);
         case 52:
            return this.__init__$52(var2, var3);
         case 53:
            return this.connect$53(var2, var3);
         case 54:
            return this.prompt$54(var2, var3);
         default:
            return null;
      }
   }
}
