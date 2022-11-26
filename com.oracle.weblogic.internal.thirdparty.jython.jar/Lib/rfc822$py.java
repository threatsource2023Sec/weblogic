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
@Filename("rfc822.py")
public class rfc822$py extends PyFunctionTable implements PyRunnable {
   static rfc822$py self;
   static final PyCode f$0;
   static final PyCode Message$1;
   static final PyCode __init__$2;
   static final PyCode rewindbody$3;
   static final PyCode readheaders$4;
   static final PyCode isheader$5;
   static final PyCode islast$6;
   static final PyCode iscomment$7;
   static final PyCode getallmatchingheaders$8;
   static final PyCode getfirstmatchingheader$9;
   static final PyCode getrawheader$10;
   static final PyCode getheader$11;
   static final PyCode getheaders$12;
   static final PyCode getaddr$13;
   static final PyCode getaddrlist$14;
   static final PyCode getdate$15;
   static final PyCode getdate_tz$16;
   static final PyCode __len__$17;
   static final PyCode __getitem__$18;
   static final PyCode __setitem__$19;
   static final PyCode __delitem__$20;
   static final PyCode setdefault$21;
   static final PyCode has_key$22;
   static final PyCode __contains__$23;
   static final PyCode __iter__$24;
   static final PyCode keys$25;
   static final PyCode values$26;
   static final PyCode items$27;
   static final PyCode __str__$28;
   static final PyCode unquote$29;
   static final PyCode quote$30;
   static final PyCode parseaddr$31;
   static final PyCode AddrlistClass$32;
   static final PyCode __init__$33;
   static final PyCode gotonext$34;
   static final PyCode getaddrlist$35;
   static final PyCode getaddress$36;
   static final PyCode getrouteaddr$37;
   static final PyCode getaddrspec$38;
   static final PyCode getdomain$39;
   static final PyCode getdelimited$40;
   static final PyCode getquote$41;
   static final PyCode getcomment$42;
   static final PyCode getdomainliteral$43;
   static final PyCode getatom$44;
   static final PyCode getphraselist$45;
   static final PyCode AddressList$46;
   static final PyCode __init__$47;
   static final PyCode __len__$48;
   static final PyCode __str__$49;
   static final PyCode __add__$50;
   static final PyCode __iadd__$51;
   static final PyCode __sub__$52;
   static final PyCode __isub__$53;
   static final PyCode __getitem__$54;
   static final PyCode dump_address_pair$55;
   static final PyCode parsedate_tz$56;
   static final PyCode parsedate$57;
   static final PyCode mktime_tz$58;
   static final PyCode formatdate$59;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("RFC 2822 message manipulation.\n\nNote: This is only a very rough sketch of a full RFC-822 parser; in particular\nthe tokenizing of addresses does not adhere to all the quoting rules.\n\nNote: RFC 2822 is a long awaited update to RFC 822.  This module should\nconform to RFC 2822, and is thus mis-named (it's not worth renaming it).  Some\neffort at RFC 2822 updates have been made, but a thorough audit has not been\nperformed.  Consider any RFC 2822 non-conformance to be a bug.\n\n    RFC 2822: http://www.faqs.org/rfcs/rfc2822.html\n    RFC 822 : http://www.faqs.org/rfcs/rfc822.html (obsolete)\n\nDirections for use:\n\nTo create a Message object: first open a file, e.g.:\n\n  fp = open(file, 'r')\n\nYou can use any other legal way of getting an open file object, e.g. use\nsys.stdin or call os.popen().  Then pass the open file object to the Message()\nconstructor:\n\n  m = Message(fp)\n\nThis class can work with any input object that supports a readline method.  If\nthe input object has seek and tell capability, the rewindbody method will\nwork; also illegal lines will be pushed back onto the input stream.  If the\ninput object lacks seek but has an `unread' method that can push back a line\nof input, Message will use that to push back illegal lines.  Thus this class\ncan be used to parse messages coming from a buffered stream.\n\nThe optional `seekable' argument is provided as a workaround for certain stdio\nlibraries in which tell() discards buffered data before discovering that the\nlseek() system call doesn't work.  For maximum portability, you should set the\nseekable argument to zero to prevent that initial \\code{tell} when passing in\nan unseekable object such as a file object created from a socket object.  If\nit is 1 on entry -- which it is by default -- the tell() method of the open\nfile object is called once; if this raises an exception, seekable is reset to\n0.  For other nonzero values of seekable, this test is not made.\n\nTo get the text of a particular header there are several methods:\n\n  str = m.getheader(name)\n  str = m.getrawheader(name)\n\nwhere name is the name of the header, e.g. 'Subject'.  The difference is that\ngetheader() strips the leading and trailing whitespace, while getrawheader()\ndoesn't.  Both functions retain embedded whitespace (including newlines)\nexactly as they are specified in the header, and leave the case of the text\nunchanged.\n\nFor addresses and address lists there are functions\n\n  realname, mailaddress = m.getaddr(name)\n  list = m.getaddrlist(name)\n\nwhere the latter returns a list of (realname, mailaddr) tuples.\n\nThere is also a method\n\n  time = m.getdate(name)\n\nwhich parses a Date-like field and returns a time-compatible tuple,\ni.e. a tuple such as returned by time.localtime() or accepted by\ntime.mktime().\n\nSee the class definition for lower level access methods.\n\nThere are also some utility functions here.\n"));
      var1.setline(71);
      PyString.fromInterned("RFC 2822 message manipulation.\n\nNote: This is only a very rough sketch of a full RFC-822 parser; in particular\nthe tokenizing of addresses does not adhere to all the quoting rules.\n\nNote: RFC 2822 is a long awaited update to RFC 822.  This module should\nconform to RFC 2822, and is thus mis-named (it's not worth renaming it).  Some\neffort at RFC 2822 updates have been made, but a thorough audit has not been\nperformed.  Consider any RFC 2822 non-conformance to be a bug.\n\n    RFC 2822: http://www.faqs.org/rfcs/rfc2822.html\n    RFC 822 : http://www.faqs.org/rfcs/rfc822.html (obsolete)\n\nDirections for use:\n\nTo create a Message object: first open a file, e.g.:\n\n  fp = open(file, 'r')\n\nYou can use any other legal way of getting an open file object, e.g. use\nsys.stdin or call os.popen().  Then pass the open file object to the Message()\nconstructor:\n\n  m = Message(fp)\n\nThis class can work with any input object that supports a readline method.  If\nthe input object has seek and tell capability, the rewindbody method will\nwork; also illegal lines will be pushed back onto the input stream.  If the\ninput object lacks seek but has an `unread' method that can push back a line\nof input, Message will use that to push back illegal lines.  Thus this class\ncan be used to parse messages coming from a buffered stream.\n\nThe optional `seekable' argument is provided as a workaround for certain stdio\nlibraries in which tell() discards buffered data before discovering that the\nlseek() system call doesn't work.  For maximum portability, you should set the\nseekable argument to zero to prevent that initial \\code{tell} when passing in\nan unseekable object such as a file object created from a socket object.  If\nit is 1 on entry -- which it is by default -- the tell() method of the open\nfile object is called once; if this raises an exception, seekable is reset to\n0.  For other nonzero values of seekable, this test is not made.\n\nTo get the text of a particular header there are several methods:\n\n  str = m.getheader(name)\n  str = m.getrawheader(name)\n\nwhere name is the name of the header, e.g. 'Subject'.  The difference is that\ngetheader() strips the leading and trailing whitespace, while getrawheader()\ndoesn't.  Both functions retain embedded whitespace (including newlines)\nexactly as they are specified in the header, and leave the case of the text\nunchanged.\n\nFor addresses and address lists there are functions\n\n  realname, mailaddress = m.getaddr(name)\n  list = m.getaddrlist(name)\n\nwhere the latter returns a list of (realname, mailaddr) tuples.\n\nThere is also a method\n\n  time = m.getdate(name)\n\nwhich parses a Date-like field and returns a time-compatible tuple,\ni.e. a tuple such as returned by time.localtime() or accepted by\ntime.mktime().\n\nSee the class definition for lower level access methods.\n\nThere are also some utility functions here.\n");
      var1.setline(74);
      PyObject var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(76);
      String[] var6 = new String[]{"warnpy3k"};
      PyObject[] var7 = imp.importFrom("warnings", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(77);
      PyObject var10000 = var1.getname("warnpy3k");
      var7 = new PyObject[]{PyString.fromInterned("in 3.x, rfc822 has been removed in favor of the email package"), Py.newInteger(2)};
      String[] var8 = new String[]{"stacklevel"};
      var10000.__call__(var2, var7, var8);
      var3 = null;
      var1.setline(80);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("Message"), PyString.fromInterned("AddressList"), PyString.fromInterned("parsedate"), PyString.fromInterned("parsedate_tz"), PyString.fromInterned("mktime_tz")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(82);
      PyTuple var10 = new PyTuple(new PyObject[]{PyString.fromInterned("\r\n"), PyString.fromInterned("\n")});
      var1.setlocal("_blanklines", var10);
      var3 = null;
      var1.setline(85);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Message", var7, Message$1);
      var1.setlocal("Message", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(477);
      var7 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var7, unquote$29, PyString.fromInterned("Remove quotes from a string."));
      var1.setlocal("unquote", var11);
      var3 = null;
      var1.setline(487);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, quote$30, PyString.fromInterned("Add quotes around a string."));
      var1.setlocal("quote", var11);
      var3 = null;
      var1.setline(492);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, parseaddr$31, PyString.fromInterned("Parse an address into a (realname, mailaddr) tuple."));
      var1.setlocal("parseaddr", var11);
      var3 = null;
      var1.setline(501);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("AddrlistClass", var7, AddrlistClass$32);
      var1.setlocal("AddrlistClass", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(775);
      var7 = new PyObject[]{var1.getname("AddrlistClass")};
      var4 = Py.makeClass("AddressList", var7, AddressList$46);
      var1.setlocal("AddressList", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(825);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, dump_address_pair$55, PyString.fromInterned("Dump a (name, address) pair in a canonicalized form."));
      var1.setlocal("dump_address_pair", var11);
      var3 = null;
      var1.setline(834);
      var9 = new PyList(new PyObject[]{PyString.fromInterned("jan"), PyString.fromInterned("feb"), PyString.fromInterned("mar"), PyString.fromInterned("apr"), PyString.fromInterned("may"), PyString.fromInterned("jun"), PyString.fromInterned("jul"), PyString.fromInterned("aug"), PyString.fromInterned("sep"), PyString.fromInterned("oct"), PyString.fromInterned("nov"), PyString.fromInterned("dec"), PyString.fromInterned("january"), PyString.fromInterned("february"), PyString.fromInterned("march"), PyString.fromInterned("april"), PyString.fromInterned("may"), PyString.fromInterned("june"), PyString.fromInterned("july"), PyString.fromInterned("august"), PyString.fromInterned("september"), PyString.fromInterned("october"), PyString.fromInterned("november"), PyString.fromInterned("december")});
      var1.setlocal("_monthnames", var9);
      var3 = null;
      var1.setline(838);
      var9 = new PyList(new PyObject[]{PyString.fromInterned("mon"), PyString.fromInterned("tue"), PyString.fromInterned("wed"), PyString.fromInterned("thu"), PyString.fromInterned("fri"), PyString.fromInterned("sat"), PyString.fromInterned("sun")});
      var1.setlocal("_daynames", var9);
      var3 = null;
      var1.setline(846);
      PyDictionary var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("UT"), Py.newInteger(0), PyString.fromInterned("UTC"), Py.newInteger(0), PyString.fromInterned("GMT"), Py.newInteger(0), PyString.fromInterned("Z"), Py.newInteger(0), PyString.fromInterned("AST"), Py.newInteger(-400), PyString.fromInterned("ADT"), Py.newInteger(-300), PyString.fromInterned("EST"), Py.newInteger(-500), PyString.fromInterned("EDT"), Py.newInteger(-400), PyString.fromInterned("CST"), Py.newInteger(-600), PyString.fromInterned("CDT"), Py.newInteger(-500), PyString.fromInterned("MST"), Py.newInteger(-700), PyString.fromInterned("MDT"), Py.newInteger(-600), PyString.fromInterned("PST"), Py.newInteger(-800), PyString.fromInterned("PDT"), Py.newInteger(-700)});
      var1.setlocal("_timezones", var13);
      var3 = null;
      var1.setline(855);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, parsedate_tz$56, PyString.fromInterned("Convert a date string to a time tuple.\n\n    Accounts for military timezones.\n    "));
      var1.setlocal("parsedate_tz", var11);
      var3 = null;
      var1.setline(940);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, parsedate$57, PyString.fromInterned("Convert a time string to a time tuple."));
      var1.setlocal("parsedate", var11);
      var3 = null;
      var1.setline(948);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, mktime_tz$58, PyString.fromInterned("Turn a 10-tuple as returned by parsedate_tz() into a UTC timestamp."));
      var1.setlocal("mktime_tz", var11);
      var3 = null;
      var1.setline(957);
      var7 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var7, formatdate$59, PyString.fromInterned("Returns time format preferred for Internet standards.\n\n    Sun, 06 Nov 1994 08:49:37 GMT  ; RFC 822, updated by RFC 1123\n\n    According to RFC 1123, day and month names must always be in\n    English.  If not for that, this code could use strftime().  It\n    can't because strftime() honors the locale and could generate\n    non-English names.\n    "));
      var1.setlocal("formatdate", var11);
      var3 = null;
      var1.setline(982);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(983);
         var3 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var3);
         var3 = null;
         var3 = imp.importOne("os", var1, -1);
         var1.setlocal("os", var3);
         var3 = null;
         var1.setline(984);
         var3 = var1.getname("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME")), (PyObject)PyString.fromInterned("Mail/inbox/1"));
         var1.setlocal("file", var3);
         var3 = null;
         var1.setline(985);
         if (var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
            var1.setline(985);
            var3 = var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
            var1.setlocal("file", var3);
            var3 = null;
         }

         var1.setline(986);
         var3 = var1.getname("open").__call__((ThreadState)var2, (PyObject)var1.getname("file"), (PyObject)PyString.fromInterned("r"));
         var1.setlocal("f", var3);
         var3 = null;
         var1.setline(987);
         var3 = var1.getname("Message").__call__(var2, var1.getname("f"));
         var1.setlocal("m", var3);
         var3 = null;
         var1.setline(988);
         Py.printComma(PyString.fromInterned("From:"));
         Py.println(var1.getname("m").__getattr__("getaddr").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("from")));
         var1.setline(989);
         Py.printComma(PyString.fromInterned("To:"));
         Py.println(var1.getname("m").__getattr__("getaddrlist").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("to")));
         var1.setline(990);
         Py.printComma(PyString.fromInterned("Subject:"));
         Py.println(var1.getname("m").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("subject")));
         var1.setline(991);
         Py.printComma(PyString.fromInterned("Date:"));
         Py.println(var1.getname("m").__getattr__("getheader").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("date")));
         var1.setline(992);
         var3 = var1.getname("m").__getattr__("getdate_tz").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("date"));
         var1.setlocal("date", var3);
         var3 = null;
         var1.setline(993);
         var3 = var1.getname("date").__getitem__(Py.newInteger(-1));
         var1.setlocal("tz", var3);
         var3 = null;
         var1.setline(994);
         var3 = var1.getname("time").__getattr__("localtime").__call__(var2, var1.getname("mktime_tz").__call__(var2, var1.getname("date")));
         var1.setlocal("date", var3);
         var3 = null;
         var1.setline(995);
         if (var1.getname("date").__nonzero__()) {
            var1.setline(996);
            Py.printComma(PyString.fromInterned("ParsedDate:"));
            Py.printComma(var1.getname("time").__getattr__("asctime").__call__(var2, var1.getname("date")));
            var1.setline(997);
            var3 = var1.getname("tz");
            var1.setlocal("hhmmss", var3);
            var3 = null;
            var1.setline(998);
            var3 = var1.getname("divmod").__call__((ThreadState)var2, (PyObject)var1.getname("hhmmss"), (PyObject)Py.newInteger(60));
            PyObject[] var12 = Py.unpackSequence(var3, 2);
            PyObject var5 = var12[0];
            var1.setlocal("hhmm", var5);
            var5 = null;
            var5 = var12[1];
            var1.setlocal("ss", var5);
            var5 = null;
            var3 = null;
            var1.setline(999);
            var3 = var1.getname("divmod").__call__((ThreadState)var2, (PyObject)var1.getname("hhmm"), (PyObject)Py.newInteger(60));
            var12 = Py.unpackSequence(var3, 2);
            var5 = var12[0];
            var1.setlocal("hh", var5);
            var5 = null;
            var5 = var12[1];
            var1.setlocal("mm", var5);
            var5 = null;
            var3 = null;
            var1.setline(1000);
            Py.printComma(PyString.fromInterned("%+03d%02d")._mod(new PyTuple(new PyObject[]{var1.getname("hh"), var1.getname("mm")})));
            var1.setline(1001);
            if (var1.getname("ss").__nonzero__()) {
               var1.setline(1001);
               Py.printComma(PyString.fromInterned(".%02d")._mod(var1.getname("ss")));
            }

            var1.setline(1002);
            Py.println();
         } else {
            var1.setline(1004);
            Py.printComma(PyString.fromInterned("ParsedDate:"));
            Py.println(var1.getname("None"));
         }

         var1.setline(1005);
         var1.getname("m").__getattr__("rewindbody").__call__(var2);
         var1.setline(1006);
         PyInteger var14 = Py.newInteger(0);
         var1.setlocal("n", var14);
         var3 = null;

         while(true) {
            var1.setline(1007);
            if (!var1.getname("f").__getattr__("readline").__call__(var2).__nonzero__()) {
               var1.setline(1009);
               Py.printComma(PyString.fromInterned("Lines:"));
               Py.println(var1.getname("n"));
               var1.setline(1010);
               Py.println(PyString.fromInterned("-")._mul(Py.newInteger(70)));
               var1.setline(1011);
               Py.printComma(PyString.fromInterned("len ="));
               Py.println(var1.getname("len").__call__(var2, var1.getname("m")));
               var1.setline(1012);
               PyString var15 = PyString.fromInterned("Date");
               var10000 = var15._in(var1.getname("m"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1012);
                  Py.printComma(PyString.fromInterned("Date ="));
                  Py.println(var1.getname("m").__getitem__(PyString.fromInterned("Date")));
               }

               var1.setline(1013);
               var15 = PyString.fromInterned("X-Nonsense");
               var10000 = var15._in(var1.getname("m"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1013);
               }

               var1.setline(1014);
               Py.printComma(PyString.fromInterned("keys ="));
               Py.println(var1.getname("m").__getattr__("keys").__call__(var2));
               var1.setline(1015);
               Py.printComma(PyString.fromInterned("values ="));
               Py.println(var1.getname("m").__getattr__("values").__call__(var2));
               var1.setline(1016);
               Py.printComma(PyString.fromInterned("items ="));
               Py.println(var1.getname("m").__getattr__("items").__call__(var2));
               break;
            }

            var1.setline(1008);
            var3 = var1.getname("n");
            var3 = var3._iadd(Py.newInteger(1));
            var1.setlocal("n", var3);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Message$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Represents a single RFC 2822-compliant message."));
      var1.setline(86);
      PyString.fromInterned("Represents a single RFC 2822-compliant message.");
      var1.setline(88);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Initialize the class instance and read the headers."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(116);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rewindbody$3, PyString.fromInterned("Rewind the file to the start of the body (if seekable)."));
      var1.setlocal("rewindbody", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readheaders$4, PyString.fromInterned("Read header lines.\n\n        Read header lines up to the entirely blank line that terminates them.\n        The (normally blank) line that ends the headers is skipped, but not\n        included in the returned list.  If a non-header line ends the headers,\n        (which is an error), an attempt is made to backspace over it; it is\n        never included in the returned list.\n\n        The variable self.status is set to the empty string if all went well,\n        otherwise it is an error message.  The variable self.headers is a\n        completely uninterpreted list of lines contained in the header (so\n        printing them will reproduce the header exactly as it appears in the\n        file).\n        "));
      var1.setlocal("readheaders", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isheader$5, PyString.fromInterned("Determine whether a given line is a legal header.\n\n        This method should return the header name, suitably canonicalized.\n        You may override this method in order to use Message parsing on tagged\n        data in RFC 2822-like formats with special header formats.\n        "));
      var1.setlocal("isheader", var4);
      var3 = null;
      var1.setline(214);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, islast$6, PyString.fromInterned("Determine whether a line is a legal end of RFC 2822 headers.\n\n        You may override this method if your application wants to bend the\n        rules, e.g. to strip trailing whitespace, or to recognize MH template\n        separators ('--------').  For convenience (e.g. for code reading from\n        sockets) a line consisting of \\r\\n also matches.\n        "));
      var1.setlocal("islast", var4);
      var3 = null;
      var1.setline(224);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iscomment$7, PyString.fromInterned("Determine whether a line should be skipped entirely.\n\n        You may override this method in order to use Message parsing on tagged\n        data in RFC 2822-like formats that support embedded comments or\n        free-text data.\n        "));
      var1.setlocal("iscomment", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getallmatchingheaders$8, PyString.fromInterned("Find all header lines matching a given header name.\n\n        Look through the list of headers and find all lines matching a given\n        header name (and their continuation lines).  A list of the lines is\n        returned, without interpretation.  If the header does not occur, an\n        empty list is returned.  If the header occurs multiple times, all\n        occurrences are returned.  Case is not important in the header name.\n        "));
      var1.setlocal("getallmatchingheaders", var4);
      var3 = null;
      var1.setline(255);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getfirstmatchingheader$9, PyString.fromInterned("Get the first header line matching name.\n\n        This is similar to getallmatchingheaders, but it returns only the\n        first matching header (and its continuation lines).\n        "));
      var1.setlocal("getfirstmatchingheader", var4);
      var3 = null;
      var1.setline(275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getrawheader$10, PyString.fromInterned("A higher-level interface to getfirstmatchingheader().\n\n        Return a string containing the literal text of the header but with the\n        keyword stripped.  All leading, trailing and embedded whitespace is\n        kept in the string, however.  Return None if the header does not\n        occur.\n        "));
      var1.setlocal("getrawheader", var4);
      var3 = null;
      var1.setline(290);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getheader$11, PyString.fromInterned("Get the header value for a name.\n\n        This is the normal interface: it returns a stripped version of the\n        header value for a given header name, or None if it doesn't exist.\n        This uses the dictionary version which finds the *last* such header.\n        "));
      var1.setlocal("getheader", var4);
      var3 = null;
      var1.setline(298);
      PyObject var5 = var1.getname("getheader");
      var1.setlocal("get", var5);
      var3 = null;
      var1.setline(300);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getheaders$12, PyString.fromInterned("Get all values for a header.\n\n        This returns a list of values for headers given more than once; each\n        value in the result list is stripped in the same way as the result of\n        getheader().  If the header is not given, return an empty list.\n        "));
      var1.setlocal("getheaders", var4);
      var3 = null;
      var1.setline(325);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddr$13, PyString.fromInterned("Get a single address from a header, as a tuple.\n\n        An example return value:\n        ('Guido van Rossum', 'guido@cwi.nl')\n        "));
      var1.setlocal("getaddr", var4);
      var3 = null;
      var1.setline(338);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddrlist$14, PyString.fromInterned("Get a list of addresses from a header.\n\n        Retrieves a list of addresses from a header, where each address is a\n        tuple as returned by getaddr().  Scans all named headers, so it works\n        properly with multiple To: or Cc: headers for example.\n        "));
      var1.setlocal("getaddrlist", var4);
      var3 = null;
      var1.setline(360);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdate$15, PyString.fromInterned("Retrieve a date field from a header.\n\n        Retrieves a date field from the named header, returning a tuple\n        compatible with time.mktime().\n        "));
      var1.setlocal("getdate", var4);
      var3 = null;
      var1.setline(372);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdate_tz$16, PyString.fromInterned("Retrieve a date field from a header as a 10-tuple.\n\n        The first 9 elements make up a tuple compatible with time.mktime(),\n        and the 10th is the offset of the poster's time zone from GMT/UTC.\n        "));
      var1.setlocal("getdate_tz", var4);
      var3 = null;
      var1.setline(387);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$17, PyString.fromInterned("Get the number of headers in a message."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$18, PyString.fromInterned("Get a specific header, as from a dictionary."));
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$19, PyString.fromInterned("Set the value of a header.\n\n        Note: This is not a perfect inversion of __getitem__, because any\n        changed headers get stuck at the end of the raw-headers list rather\n        than where the altered header was.\n        "));
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(408);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$20, PyString.fromInterned("Delete all occurrences of a specific header, if it is present."));
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(429);
      var3 = new PyObject[]{PyString.fromInterned("")};
      var4 = new PyFunction(var1.f_globals, var3, setdefault$21, (PyObject)null);
      var1.setlocal("setdefault", var4);
      var3 = null;
      var1.setline(440);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$22, PyString.fromInterned("Determine whether a message contains the named header."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$23, PyString.fromInterned("Determine whether a message contains the named header."));
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(448);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$24, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(451);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$25, PyString.fromInterned("Get all of a message's header field names."));
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$26, PyString.fromInterned("Get all of a message's header field values."));
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(459);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$27, PyString.fromInterned("Get all of a message's headers.\n\n        Returns a list of name, value tuples.\n        "));
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(466);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$28, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Initialize the class instance and read the headers.");
      var1.setline(90);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyInteger var4;
      PyException var8;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(94);
            var1.getlocal(1).__getattr__("tell").__call__(var2);
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (!var8.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("IOError")}))) {
               throw var8;
            }

            var1.setline(96);
            var4 = Py.newInteger(0);
            var1.setlocal(2, var4);
            var4 = null;
         }
      }

      var1.setline(97);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fp", var3);
      var3 = null;
      var1.setline(98);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("seekable", var3);
      var3 = null;
      var1.setline(99);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("startofheaders", var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("startofbody", var3);
      var3 = null;
      var1.setline(102);
      if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
         try {
            var1.setline(104);
            var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
            var1.getlocal(0).__setattr__("startofheaders", var3);
            var3 = null;
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (!var8.match(var1.getglobal("IOError"))) {
               throw var8;
            }

            var1.setline(106);
            var4 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"seekable", var4);
            var4 = null;
         }
      }

      var1.setline(108);
      var1.getlocal(0).__getattr__("readheaders").__call__(var2);
      var1.setline(110);
      if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
         try {
            var1.setline(112);
            var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
            var1.getlocal(0).__setattr__("startofbody", var3);
            var3 = null;
         } catch (Throwable var5) {
            var8 = Py.setException(var5, var1);
            if (!var8.match(var1.getglobal("IOError"))) {
               throw var8;
            }

            var1.setline(114);
            var4 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"seekable", var4);
            var4 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject rewindbody$3(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Rewind the file to the start of the body (if seekable).");
      var1.setline(118);
      if (var1.getlocal(0).__getattr__("seekable").__not__().__nonzero__()) {
         var1.setline(119);
         throw Py.makeException(var1.getglobal("IOError"), PyString.fromInterned("unseekable file"));
      } else {
         var1.setline(120);
         var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("startofbody"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject readheaders$4(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyString.fromInterned("Read header lines.\n\n        Read header lines up to the entirely blank line that terminates them.\n        The (normally blank) line that ends the headers is skipped, but not\n        included in the returned list.  If a non-header line ends the headers,\n        (which is an error), an attempt is made to backspace over it; it is\n        never included in the returned list.\n\n        The variable self.status is set to the empty string if all went well,\n        otherwise it is an error message.  The variable self.headers is a\n        completely uninterpreted list of lines contained in the header (so\n        printing them will reproduce the header exactly as it appears in the\n        file).\n        ");
      var1.setline(137);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"dict", var3);
      var3 = null;
      var1.setline(138);
      PyString var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"unixfrom", var6);
      var3 = null;
      var1.setline(139);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"headers", var7);
      var1.setlocal(1, var7);
      var1.setline(140);
      var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"status", var6);
      var3 = null;
      var1.setline(141);
      var6 = PyString.fromInterned("");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(142);
      PyInteger var9 = Py.newInteger(1);
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(143);
      PyObject var10 = var1.getglobal("None");
      var1.setlocal(4, var10);
      var1.setlocal(5, var10);
      var1.setlocal(6, var10);
      var1.setline(144);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("fp"), (PyObject)PyString.fromInterned("unread")).__nonzero__()) {
         var1.setline(145);
         var10 = var1.getlocal(0).__getattr__("fp").__getattr__("unread");
         var1.setlocal(5, var10);
         var3 = null;
      } else {
         var1.setline(146);
         if (var1.getlocal(0).__getattr__("seekable").__nonzero__()) {
            var1.setline(147);
            var10 = var1.getlocal(0).__getattr__("fp").__getattr__("tell");
            var1.setlocal(6, var10);
            var3 = null;
         }
      }

      while(true) {
         var1.setline(148);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(149);
         if (var1.getlocal(6).__nonzero__()) {
            try {
               var1.setline(151);
               var10 = var1.getlocal(6).__call__(var2);
               var1.setlocal(4, var10);
               var3 = null;
            } catch (Throwable var5) {
               PyException var11 = Py.setException(var5, var1);
               if (!var11.match(var1.getglobal("IOError"))) {
                  throw var11;
               }

               var1.setline(153);
               PyObject var4 = var1.getglobal("None");
               var1.setlocal(4, var4);
               var1.setlocal(6, var4);
               var1.setline(154);
               PyInteger var8 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"seekable", var8);
               var4 = null;
            }
         }

         var1.setline(155);
         var10 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(7, var10);
         var3 = null;
         var1.setline(156);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            var1.setline(157);
            var6 = PyString.fromInterned("EOF in headers");
            var1.getlocal(0).__setattr__((String)"status", var6);
            var3 = null;
            break;
         }

         var1.setline(160);
         PyObject var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From "));
         }

         if (var10000.__nonzero__()) {
            var1.setline(161);
            var10 = var1.getlocal(0).__getattr__("unixfrom")._add(var1.getlocal(7));
            var1.getlocal(0).__setattr__("unixfrom", var10);
            var3 = null;
         } else {
            var1.setline(163);
            var9 = Py.newInteger(0);
            var1.setlocal(3, var9);
            var3 = null;
            var1.setline(164);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var10 = var1.getlocal(7).__getitem__(Py.newInteger(0));
               var10000 = var10._in(PyString.fromInterned(" \t"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(166);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(7));
               var1.setline(167);
               var10 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(2))._add(PyString.fromInterned("\n "))._add(var1.getlocal(7).__getattr__("strip").__call__(var2));
               var1.setlocal(8, var10);
               var3 = null;
               var1.setline(168);
               var10 = var1.getlocal(8).__getattr__("strip").__call__(var2);
               var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(2), var10);
               var3 = null;
            } else {
               var1.setline(170);
               if (!var1.getlocal(0).__getattr__("iscomment").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                  var1.setline(173);
                  if (var1.getlocal(0).__getattr__("islast").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                     break;
                  }

                  var1.setline(176);
                  var10 = var1.getlocal(0).__getattr__("isheader").__call__(var2, var1.getlocal(7));
                  var1.setlocal(2, var10);
                  var3 = null;
                  var1.setline(177);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(179);
                     var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(7));
                     var1.setline(180);
                     var10 = var1.getlocal(7).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
                     var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(2), var10);
                     var3 = null;
                  } else {
                     var1.setline(182);
                     var10 = var1.getlocal(2);
                     var10000 = var10._isnot(var1.getglobal("None"));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(189);
                        if (var1.getlocal(0).__getattr__("dict").__not__().__nonzero__()) {
                           var1.setline(190);
                           var6 = PyString.fromInterned("No headers");
                           var1.getlocal(0).__setattr__((String)"status", var6);
                           var3 = null;
                        } else {
                           var1.setline(192);
                           var6 = PyString.fromInterned("Non-header line where header expected");
                           var1.getlocal(0).__setattr__((String)"status", var6);
                           var3 = null;
                        }

                        var1.setline(194);
                        if (var1.getlocal(5).__nonzero__()) {
                           var1.setline(195);
                           var1.getlocal(5).__call__(var2, var1.getlocal(7));
                        } else {
                           var1.setline(196);
                           if (var1.getlocal(6).__nonzero__()) {
                              var1.setline(197);
                              var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(4));
                           } else {
                              var1.setline(199);
                              var10 = var1.getlocal(0).__getattr__("status")._add(PyString.fromInterned("; bad seek"));
                              var1.getlocal(0).__setattr__("status", var10);
                              var3 = null;
                           }
                        }
                        break;
                     }
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isheader$5(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyString.fromInterned("Determine whether a given line is a legal header.\n\n        This method should return the header name, suitably canonicalized.\n        You may override this method in order to use Message parsing on tagged\n        data in RFC 2822-like formats with special header formats.\n        ");
      var1.setline(209);
      PyObject var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(210);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._gt(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(211);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("lower").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(212);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject islast$6(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyString.fromInterned("Determine whether a line is a legal end of RFC 2822 headers.\n\n        You may override this method if your application wants to bend the\n        rules, e.g. to strip trailing whitespace, or to recognize MH template\n        separators ('--------').  For convenience (e.g. for code reading from\n        sockets) a line consisting of \\r\\n also matches.\n        ");
      var1.setline(222);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getglobal("_blanklines"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iscomment$7(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("Determine whether a line should be skipped entirely.\n\n        You may override this method in order to use Message parsing on tagged\n        data in RFC 2822-like formats that support embedded comments or\n        free-text data.\n        ");
      var1.setline(231);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getallmatchingheaders$8(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyString.fromInterned("Find all header lines matching a given header name.\n\n        Look through the list of headers and find all lines matching a given\n        header name (and their continuation lines).  A list of the lines is\n        returned, without interpretation.  If the header does not occur, an\n        empty list is returned.  If the header occurs multiple times, all\n        occurrences are returned.  Case is not important in the header name.\n        ");
      var1.setline(242);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2)._add(PyString.fromInterned(":"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(243);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(244);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(245);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(246);
      var3 = var1.getlocal(0).__getattr__("headers").__iter__();

      while(true) {
         var1.setline(246);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(253);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(247);
         PyObject var5 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("lower").__call__(var2);
         PyObject var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
         PyInteger var8;
         if (var10000.__nonzero__()) {
            var1.setline(248);
            var8 = Py.newInteger(1);
            var1.setlocal(4, var8);
            var5 = null;
         } else {
            var1.setline(249);
            if (var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null).__getattr__("isspace").__call__(var2).__not__().__nonzero__()) {
               var1.setline(250);
               var8 = Py.newInteger(0);
               var1.setlocal(4, var8);
               var5 = null;
            }
         }

         var1.setline(251);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(252);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject getfirstmatchingheader$9(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Get the first header line matching name.\n\n        This is similar to getallmatchingheaders, but it returns only the\n        first matching header (and its continuation lines).\n        ");
      var1.setline(261);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2)._add(PyString.fromInterned(":"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(262);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(263);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(264);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(265);
      var3 = var1.getlocal(0).__getattr__("headers").__iter__();

      while(true) {
         var1.setline(265);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(5, var4);
         var1.setline(266);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(267);
            if (var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null).__getattr__("isspace").__call__(var2).__not__().__nonzero__()) {
               break;
            }
         } else {
            var1.setline(269);
            PyObject var5 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("lower").__call__(var2);
            PyObject var10000 = var5._eq(var1.getlocal(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(270);
               PyInteger var8 = Py.newInteger(1);
               var1.setlocal(4, var8);
               var5 = null;
            }
         }

         var1.setline(271);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(272);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         }
      }

      var1.setline(273);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getrawheader$10(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyString.fromInterned("A higher-level interface to getfirstmatchingheader().\n\n        Return a string containing the literal text of the header but with the\n        keyword stripped.  All leading, trailing and embedded whitespace is\n        kept in the string, however.  Return None if the header does not\n        occur.\n        ");
      var1.setline(284);
      PyObject var3 = var1.getlocal(0).__getattr__("getfirstmatchingheader").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(285);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(286);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(287);
         PyObject var4 = var1.getlocal(2).__getitem__(Py.newInteger(0)).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(1))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.getlocal(2).__setitem__((PyObject)Py.newInteger(0), var4);
         var4 = null;
         var1.setline(288);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getheader$11(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyString.fromInterned("Get the header value for a name.\n\n        This is the normal interface: it returns a stripped version of the\n        header value for a given header name, or None if it doesn't exist.\n        This uses the dictionary version which finds the *last* such header.\n        ");
      var1.setline(297);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("lower").__call__(var2), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getheaders$12(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Get all values for a header.\n\n        This returns a list of values for headers given more than once; each\n        value in the result list is stripped in the same way as the result of\n        getheader().  If the header is not given, return an empty list.\n        ");
      var1.setline(307);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(308);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(309);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(310);
      PyObject var8 = var1.getlocal(0).__getattr__("getallmatchingheaders").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(310);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(321);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(322);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
            }

            var1.setline(323);
            var8 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(5, var4);
         var1.setline(311);
         PyObject var5;
         if (var1.getlocal(5).__getitem__(Py.newInteger(0)).__getattr__("isspace").__call__(var2).__nonzero__()) {
            var1.setline(312);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(313);
               var5 = PyString.fromInterned("%s\n %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(5).__getattr__("strip").__call__(var2)}));
               var1.setlocal(3, var5);
               var5 = null;
            } else {
               var1.setline(315);
               var5 = var1.getlocal(5).__getattr__("strip").__call__(var2);
               var1.setlocal(3, var5);
               var5 = null;
            }
         } else {
            var1.setline(317);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(318);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
            }

            var1.setline(319);
            var5 = var1.getlocal(5).__getslice__(var1.getlocal(5).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2);
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(320);
            PyInteger var9 = Py.newInteger(1);
            var1.setlocal(4, var9);
            var5 = null;
         }
      }
   }

   public PyObject getaddr$13(PyFrame var1, ThreadState var2) {
      var1.setline(330);
      PyString.fromInterned("Get a single address from a header, as a tuple.\n\n        An example return value:\n        ('Guido van Rossum', 'guido@cwi.nl')\n        ");
      var1.setline(332);
      PyObject var3 = var1.getlocal(0).__getattr__("getaddrlist").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(333);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(334);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(336);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject getaddrlist$14(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyString.fromInterned("Get a list of addresses from a header.\n\n        Retrieves a list of addresses from a header, where each address is a\n        tuple as returned by getaddr().  Scans all named headers, so it works\n        properly with multiple To: or Cc: headers for example.\n        ");
      var1.setline(345);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(346);
      PyObject var6 = var1.getlocal(0).__getattr__("getallmatchingheaders").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(346);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(356);
            var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
            var1.setlocal(6, var6);
            var3 = null;
            var1.setline(357);
            var6 = var1.getglobal("AddressList").__call__(var2, var1.getlocal(6));
            var1.setlocal(7, var6);
            var3 = null;
            var1.setline(358);
            var6 = var1.getlocal(7).__getattr__("addresslist");
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(347);
         PyObject var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._in(PyString.fromInterned(" \t"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(348);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
         } else {
            var1.setline(350);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(351);
               var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(", "));
            }

            var1.setline(352);
            var5 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(353);
            var5 = var1.getlocal(4);
            var10000 = var5._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(354);
               var5 = var1.getlocal(3).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.setlocal(5, var5);
               var5 = null;
            }

            var1.setline(355);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject getdate$15(PyFrame var1, ThreadState var2) {
      var1.setline(365);
      PyString.fromInterned("Retrieve a date field from a header.\n\n        Retrieves a date field from the named header, returning a tuple\n        compatible with time.mktime().\n        ");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(367);
         PyObject var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(369);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(370);
      var4 = var1.getglobal("parsedate").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject getdate_tz$16(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyString.fromInterned("Retrieve a date field from a header as a 10-tuple.\n\n        The first 9 elements make up a tuple compatible with time.mktime(),\n        and the 10th is the offset of the poster's time zone from GMT/UTC.\n        ");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(379);
         PyObject var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(381);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(382);
      var4 = var1.getglobal("parsedate_tz").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __len__$17(PyFrame var1, ThreadState var2) {
      var1.setline(388);
      PyString.fromInterned("Get the number of headers in a message.");
      var1.setline(389);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("dict"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$18(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyString.fromInterned("Get a specific header, as from a dictionary.");
      var1.setline(393);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(1).__getattr__("lower").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setitem__$19(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyString.fromInterned("Set the value of a header.\n\n        Note: This is not a perfect inversion of __getitem__, because any\n        changed headers get stuck at the end of the raw-headers list rather\n        than where the altered header was.\n        ");
      var1.setline(402);
      var1.getlocal(0).__delitem__(var1.getlocal(1));
      var1.setline(403);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(1).__getattr__("lower").__call__(var2), var3);
      var3 = null;
      var1.setline(404);
      var3 = var1.getlocal(1)._add(PyString.fromInterned(": "))._add(var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(405);
      var3 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

      while(true) {
         var1.setline(405);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(406);
         var1.getlocal(0).__getattr__("headers").__getattr__("append").__call__(var2, var1.getlocal(4)._add(PyString.fromInterned("\n")));
      }
   }

   public PyObject __delitem__$20(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      PyString.fromInterned("Delete all occurrences of a specific header, if it is present.");
      var1.setline(410);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(411);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(412);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(413);
         var1.getlocal(0).__getattr__("dict").__delitem__(var1.getlocal(1));
         var1.setline(414);
         var3 = var1.getlocal(1)._add(PyString.fromInterned(":"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(415);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(416);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(417);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(418);
         var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("headers"))).__iter__();

         while(true) {
            var1.setline(418);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(426);
               var3 = var1.getglobal("reversed").__call__(var2, var1.getlocal(3)).__iter__();

               while(true) {
                  var1.setline(426);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(427);
                  var1.getlocal(0).__getattr__("headers").__delitem__(var1.getlocal(5));
               }
            }

            var1.setlocal(5, var4);
            var1.setline(419);
            PyObject var5 = var1.getlocal(0).__getattr__("headers").__getitem__(var1.getlocal(5));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(420);
            var5 = var1.getlocal(6).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("lower").__call__(var2);
            var10000 = var5._eq(var1.getlocal(1));
            var5 = null;
            PyInteger var8;
            if (var10000.__nonzero__()) {
               var1.setline(421);
               var8 = Py.newInteger(1);
               var1.setlocal(4, var8);
               var5 = null;
            } else {
               var1.setline(422);
               if (var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null).__getattr__("isspace").__call__(var2).__not__().__nonzero__()) {
                  var1.setline(423);
                  var8 = Py.newInteger(0);
                  var1.setlocal(4, var8);
                  var5 = null;
               }
            }

            var1.setline(424);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(425);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject setdefault$21(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(431);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(432);
         var3 = var1.getlocal(0).__getattr__("dict").__getitem__(var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(434);
         PyObject var4 = var1.getlocal(1)._add(PyString.fromInterned(": "))._add(var1.getlocal(2));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(435);
         var4 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(435);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(437);
               var4 = var1.getlocal(2);
               var1.getlocal(0).__getattr__("dict").__setitem__(var1.getlocal(3), var4);
               var4 = null;
               var1.setline(438);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(5, var5);
            var1.setline(436);
            var1.getlocal(0).__getattr__("headers").__getattr__("append").__call__(var2, var1.getlocal(5)._add(PyString.fromInterned("\n")));
         }
      }
   }

   public PyObject has_key$22(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyString.fromInterned("Determine whether a message contains the named header.");
      var1.setline(442);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$23(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyString.fromInterned("Determine whether a message contains the named header.");
      var1.setline(446);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("dict"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$24(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("dict"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject keys$25(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyString.fromInterned("Get all of a message's header field names.");
      var1.setline(453);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$26(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyString.fromInterned("Get all of a message's header field values.");
      var1.setline(457);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("values").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject items$27(PyFrame var1, ThreadState var2) {
      var1.setline(463);
      PyString.fromInterned("Get all of a message's headers.\n\n        Returns a list of name, value tuples.\n        ");
      var1.setline(464);
      PyObject var3 = var1.getlocal(0).__getattr__("dict").__getattr__("items").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$28(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("headers"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unquote$29(PyFrame var1, ThreadState var2) {
      var1.setline(478);
      PyString.fromInterned("Remove quotes from a string.");
      var1.setline(479);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(480);
         var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""));
         }

         if (var10000.__nonzero__()) {
            var1.setline(481);
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\"), (PyObject)PyString.fromInterned("\\")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\""), (PyObject)PyString.fromInterned("\""));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(482);
         var10000 = var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(483);
            var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(484);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject quote$30(PyFrame var1, ThreadState var2) {
      var1.setline(488);
      PyString.fromInterned("Add quotes around a string.");
      var1.setline(489);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("\\\\")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\\\""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseaddr$31(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyString.fromInterned("Parse an address into a (realname, mailaddr) tuple.");
      var1.setline(494);
      PyObject var3 = var1.getglobal("AddressList").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(495);
      var3 = var1.getlocal(1).__getattr__("addresslist");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(496);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(497);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(498);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject AddrlistClass$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Address parser class by Ben Escoto.\n\n    To understand what this class does, it helps to have a copy of\n    RFC 2822 in front of you.\n\n    http://www.faqs.org/rfcs/rfc2822.html\n\n    Note: this class interface is deprecated and may be removed in the future.\n    Use rfc822.AddressList instead.\n    "));
      var1.setline(511);
      PyString.fromInterned("Address parser class by Ben Escoto.\n\n    To understand what this class does, it helps to have a copy of\n    RFC 2822 in front of you.\n\n    http://www.faqs.org/rfcs/rfc2822.html\n\n    Note: this class interface is deprecated and may be removed in the future.\n    Use rfc822.AddressList instead.\n    ");
      var1.setline(513);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$33, PyString.fromInterned("Initialize a new instance.\n\n        `field' is an unparsed address header field, containing one or more\n        addresses.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(531);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, gotonext$34, PyString.fromInterned("Parse up to the start of the next address."));
      var1.setlocal("gotonext", var4);
      var3 = null;
      var1.setline(540);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddrlist$35, PyString.fromInterned("Parse all addresses.\n\n        Returns a list containing all of the addresses.\n        "));
      var1.setlocal("getaddrlist", var4);
      var3 = null;
      var1.setline(552);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddress$36, PyString.fromInterned("Parse the next address."));
      var1.setlocal("getaddress", var4);
      var3 = null;
      var1.setline(610);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getrouteaddr$37, PyString.fromInterned("Parse a route address (Return-path value).\n\n        This method just skips all the route stuff and returns the addrspec.\n        "));
      var1.setlocal("getrouteaddr", var4);
      var3 = null;
      var1.setline(642);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getaddrspec$38, PyString.fromInterned("Parse an RFC 2822 addr-spec."));
      var1.setlocal("getaddrspec", var4);
      var3 = null;
      var1.setline(666);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdomain$39, PyString.fromInterned("Get the complete domain name from an address."));
      var1.setlocal("getdomain", var4);
      var3 = null;
      var1.setline(684);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, getdelimited$40, PyString.fromInterned("Parse a header fragment delimited by special characters.\n\n        `beginchar' is the start character for the fragment.  If self is not\n        looking at an instance of `beginchar' then getdelimited returns the\n        empty string.\n\n        `endchars' is a sequence of allowable end-delimiting characters.\n        Parsing stops when one of these is encountered.\n\n        If `allowcomments' is non-zero, embedded RFC 2822 comments are allowed\n        within the parsed fragment.\n        "));
      var1.setlocal("getdelimited", var4);
      var3 = null;
      var1.setline(721);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getquote$41, PyString.fromInterned("Get a quote-delimited fragment from self's field."));
      var1.setlocal("getquote", var4);
      var3 = null;
      var1.setline(725);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcomment$42, PyString.fromInterned("Get a parenthesis-delimited fragment from self's field."));
      var1.setlocal("getcomment", var4);
      var3 = null;
      var1.setline(729);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getdomainliteral$43, PyString.fromInterned("Parse an RFC 2822 domain-literal."));
      var1.setlocal("getdomainliteral", var4);
      var3 = null;
      var1.setline(733);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getatom$44, PyString.fromInterned("Parse an RFC 2822 atom.\n\n        Optional atomends specifies a different set of end token delimiters\n        (the default is to use self.atomends).  This is used e.g. in\n        getphraselist() since phrase endings must not include the `.' (which\n        is legal in phrases)."));
      var1.setlocal("getatom", var4);
      var3 = null;
      var1.setline(752);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getphraselist$45, PyString.fromInterned("Parse a sequence of RFC 2822 phrases.\n\n        A phrase is a sequence of words, which are in turn either RFC 2822\n        atoms or quoted-strings.  Phrases are canonicalized by squeezing all\n        runs of continuous whitespace into one space.\n        "));
      var1.setlocal("getphraselist", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$33(PyFrame var1, ThreadState var2) {
      var1.setline(518);
      PyString.fromInterned("Initialize a new instance.\n\n        `field' is an unparsed address header field, containing one or more\n        addresses.\n        ");
      var1.setline(519);
      PyString var3 = PyString.fromInterned("()<>@,:;.\"[]");
      var1.getlocal(0).__setattr__((String)"specials", var3);
      var3 = null;
      var1.setline(520);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"pos", var4);
      var3 = null;
      var1.setline(521);
      var3 = PyString.fromInterned(" \t");
      var1.getlocal(0).__setattr__((String)"LWS", var3);
      var3 = null;
      var1.setline(522);
      var3 = PyString.fromInterned("\r\n");
      var1.getlocal(0).__setattr__((String)"CR", var3);
      var3 = null;
      var1.setline(523);
      PyObject var5 = var1.getlocal(0).__getattr__("specials")._add(var1.getlocal(0).__getattr__("LWS"))._add(var1.getlocal(0).__getattr__("CR"));
      var1.getlocal(0).__setattr__("atomends", var5);
      var3 = null;
      var1.setline(527);
      var5 = var1.getlocal(0).__getattr__("atomends").__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)PyString.fromInterned(""));
      var1.getlocal(0).__setattr__("phraseends", var5);
      var3 = null;
      var1.setline(528);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("field", var5);
      var3 = null;
      var1.setline(529);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commentlist", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gotonext$34(PyFrame var1, ThreadState var2) {
      var1.setline(532);
      PyString.fromInterned("Parse up to the start of the next address.");

      while(true) {
         var1.setline(533);
         PyObject var3 = var1.getlocal(0).__getattr__("pos");
         PyObject var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(534);
         var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var3._in(var1.getlocal(0).__getattr__("LWS")._add(PyString.fromInterned("\n\r")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(535);
            var3 = var1.getlocal(0).__getattr__("pos")._add(Py.newInteger(1));
            var1.getlocal(0).__setattr__("pos", var3);
            var3 = null;
         } else {
            var1.setline(536);
            var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var3._eq(PyString.fromInterned("("));
            var3 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(537);
            var1.getlocal(0).__getattr__("commentlist").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getaddrlist$35(PyFrame var1, ThreadState var2) {
      var1.setline(544);
      PyString.fromInterned("Parse all addresses.\n\n        Returns a list containing all of the addresses.\n        ");
      var1.setline(545);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(546);
      PyObject var4 = var1.getlocal(0).__getattr__("getaddress").__call__(var2);
      var1.setlocal(2, var4);
      var3 = null;

      while(true) {
         var1.setline(547);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.setline(550);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(548);
         var4 = var1.getlocal(1);
         var4 = var4._iadd(var1.getlocal(2));
         var1.setlocal(1, var4);
         var1.setline(549);
         var4 = var1.getlocal(0).__getattr__("getaddress").__call__(var2);
         var1.setlocal(2, var4);
         var3 = null;
      }
   }

   public PyObject getaddress$36(PyFrame var1, ThreadState var2) {
      var1.setline(553);
      PyString.fromInterned("Parse the next address.");
      var1.setline(554);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"commentlist", var3);
      var3 = null;
      var1.setline(555);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      var1.setline(557);
      PyObject var6 = var1.getlocal(0).__getattr__("pos");
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(558);
      var6 = var1.getlocal(0).__getattr__("commentlist");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(559);
      var6 = var1.getlocal(0).__getattr__("getphraselist").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(561);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      var1.setline(562);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(564);
      var6 = var1.getlocal(0).__getattr__("pos");
      PyObject var10000 = var6._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
      var3 = null;
      PyObject var4;
      PyObject var5;
      String var7;
      if (var10000.__nonzero__()) {
         var1.setline(566);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(567);
            var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")), var1.getlocal(3).__getitem__(Py.newInteger(0))})});
            var1.setlocal(4, var3);
            var3 = null;
         }
      } else {
         var1.setline(569);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(PyString.fromInterned(".@"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(572);
            var6 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("pos", var6);
            var3 = null;
            var1.setline(573);
            var6 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("commentlist", var6);
            var3 = null;
            var1.setline(574);
            var6 = var1.getlocal(0).__getattr__("getaddrspec").__call__(var2);
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(575);
            var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")), var1.getlocal(5)})});
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(577);
            var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var6._eq(PyString.fromInterned(":"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(579);
               var3 = new PyList(Py.EmptyObjects);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(581);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field"));
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(582);
               var10000 = var1.getlocal(0);
               var7 = "pos";
               var4 = var10000;
               var5 = var4.__getattr__(var7);
               var5 = var5._iadd(Py.newInteger(1));
               var4.__setattr__(var7, var5);

               while(true) {
                  var1.setline(583);
                  var6 = var1.getlocal(0).__getattr__("pos");
                  var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(584);
                  var1.getlocal(0).__getattr__("gotonext").__call__(var2);
                  var1.setline(585);
                  var6 = var1.getlocal(0).__getattr__("pos");
                  var10000 = var6._lt(var1.getlocal(6));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                     var10000 = var6._eq(PyString.fromInterned(";"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(586);
                     var10000 = var1.getlocal(0);
                     var7 = "pos";
                     var4 = var10000;
                     var5 = var4.__getattr__(var7);
                     var5 = var5._iadd(Py.newInteger(1));
                     var4.__setattr__(var7, var5);
                     break;
                  }

                  var1.setline(588);
                  var6 = var1.getlocal(4)._add(var1.getlocal(0).__getattr__("getaddress").__call__(var2));
                  var1.setlocal(4, var6);
                  var3 = null;
               }
            } else {
               var1.setline(590);
               var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var6._eq(PyString.fromInterned("<"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(592);
                  var6 = var1.getlocal(0).__getattr__("getrouteaddr").__call__(var2);
                  var1.setlocal(7, var6);
                  var3 = null;
                  var1.setline(594);
                  if (var1.getlocal(0).__getattr__("commentlist").__nonzero__()) {
                     var1.setline(595);
                     var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))._add(PyString.fromInterned(" ("))._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")))._add(PyString.fromInterned(")")), var1.getlocal(7)})});
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     var1.setline(597);
                     var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3)), var1.getlocal(7)})});
                     var1.setlocal(4, var3);
                     var3 = null;
                  }
               } else {
                  var1.setline(600);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(601);
                     var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("commentlist")), var1.getlocal(3).__getitem__(Py.newInteger(0))})});
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     var1.setline(602);
                     var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                     var10000 = var6._in(var1.getlocal(0).__getattr__("specials"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(603);
                        var10000 = var1.getlocal(0);
                        var7 = "pos";
                        var4 = var10000;
                        var5 = var4.__getattr__(var7);
                        var5 = var5._iadd(Py.newInteger(1));
                        var4.__setattr__(var7, var5);
                     }
                  }
               }
            }
         }
      }

      var1.setline(605);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      var1.setline(606);
      var6 = var1.getlocal(0).__getattr__("pos");
      var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._eq(PyString.fromInterned(","));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(607);
         var10000 = var1.getlocal(0);
         var7 = "pos";
         var4 = var10000;
         var5 = var4.__getattr__(var7);
         var5 = var5._iadd(Py.newInteger(1));
         var4.__setattr__(var7, var5);
      }

      var1.setline(608);
      var6 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getrouteaddr$37(PyFrame var1, ThreadState var2) {
      var1.setline(614);
      PyString.fromInterned("Parse a route address (Return-path value).\n\n        This method just skips all the route stuff and returns the addrspec.\n        ");
      var1.setline(615);
      PyObject var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
      PyObject var10000 = var3._ne(PyString.fromInterned("<"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(616);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(618);
         PyInteger var6 = Py.newInteger(0);
         var1.setlocal(1, var6);
         var3 = null;
         var1.setline(619);
         var10000 = var1.getlocal(0);
         String var7 = "pos";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var7);
         var5 = var5._iadd(Py.newInteger(1));
         var4.__setattr__(var7, var5);
         var1.setline(620);
         var1.getlocal(0).__getattr__("gotonext").__call__(var2);
         var1.setline(621);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(2, var8);
         var3 = null;

         while(true) {
            var1.setline(622);
            var3 = var1.getlocal(0).__getattr__("pos");
            var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(623);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(624);
               var1.getlocal(0).__getattr__("getdomain").__call__(var2);
               var1.setline(625);
               var6 = Py.newInteger(0);
               var1.setlocal(1, var6);
               var3 = null;
            } else {
               var1.setline(626);
               var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var3._eq(PyString.fromInterned(">"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(627);
                  var10000 = var1.getlocal(0);
                  var7 = "pos";
                  var4 = var10000;
                  var5 = var4.__getattr__(var7);
                  var5 = var5._iadd(Py.newInteger(1));
                  var4.__setattr__(var7, var5);
                  break;
               }

               var1.setline(629);
               var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var3._eq(PyString.fromInterned("@"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(630);
                  var10000 = var1.getlocal(0);
                  var7 = "pos";
                  var4 = var10000;
                  var5 = var4.__getattr__(var7);
                  var5 = var5._iadd(Py.newInteger(1));
                  var4.__setattr__(var7, var5);
                  var1.setline(631);
                  var6 = Py.newInteger(1);
                  var1.setlocal(1, var6);
                  var3 = null;
               } else {
                  var1.setline(632);
                  var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var3._eq(PyString.fromInterned(":"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(635);
                     var3 = var1.getlocal(0).__getattr__("getaddrspec").__call__(var2);
                     var1.setlocal(2, var3);
                     var3 = null;
                     var1.setline(636);
                     var10000 = var1.getlocal(0);
                     var7 = "pos";
                     var4 = var10000;
                     var5 = var4.__getattr__(var7);
                     var5 = var5._iadd(Py.newInteger(1));
                     var4.__setattr__(var7, var5);
                     break;
                  }

                  var1.setline(633);
                  var10000 = var1.getlocal(0);
                  var7 = "pos";
                  var4 = var10000;
                  var5 = var4.__getattr__(var7);
                  var5 = var5._iadd(Py.newInteger(1));
                  var4.__setattr__(var7, var5);
               }
            }

            var1.setline(638);
            var1.getlocal(0).__getattr__("gotonext").__call__(var2);
         }

         var1.setline(640);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getaddrspec$38(PyFrame var1, ThreadState var2) {
      var1.setline(643);
      PyString.fromInterned("Parse an RFC 2822 addr-spec.");
      var1.setline(644);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(646);
      var1.getlocal(0).__getattr__("gotonext").__call__(var2);

      PyObject var10000;
      PyObject var5;
      PyObject var7;
      while(true) {
         var1.setline(647);
         var7 = var1.getlocal(0).__getattr__("pos");
         var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(648);
         var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var7._eq(PyString.fromInterned("."));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(649);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.setline(650);
            var10000 = var1.getlocal(0);
            String var9 = "pos";
            PyObject var4 = var10000;
            var5 = var4.__getattr__(var9);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var9, var5);
         } else {
            var1.setline(651);
            var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var7._eq(PyString.fromInterned("\""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(652);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("\"%s\"")._mod(var1.getlocal(0).__getattr__("getquote").__call__(var2)));
            } else {
               var1.setline(653);
               var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var7._in(var1.getlocal(0).__getattr__("atomends"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(655);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getatom").__call__(var2));
            }
         }

         var1.setline(656);
         var1.getlocal(0).__getattr__("gotonext").__call__(var2);
      }

      var1.setline(658);
      var7 = var1.getlocal(0).__getattr__("pos");
      var10000 = var7._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var7 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var7._ne(PyString.fromInterned("@"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(659);
         var7 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(661);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"));
         var1.setline(662);
         var10000 = var1.getlocal(0);
         String var8 = "pos";
         var5 = var10000;
         PyObject var6 = var5.__getattr__(var8);
         var6 = var6._iadd(Py.newInteger(1));
         var5.__setattr__(var8, var6);
         var1.setline(663);
         var1.getlocal(0).__getattr__("gotonext").__call__(var2);
         var1.setline(664);
         var7 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1))._add(var1.getlocal(0).__getattr__("getdomain").__call__(var2));
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject getdomain$39(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      PyString.fromInterned("Get the complete domain name from an address.");
      var1.setline(668);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var6;
      while(true) {
         var1.setline(669);
         var6 = var1.getlocal(0).__getattr__("pos");
         PyObject var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(670);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(var1.getlocal(0).__getattr__("LWS"));
         var3 = null;
         PyObject var4;
         PyObject var5;
         String var7;
         if (var10000.__nonzero__()) {
            var1.setline(671);
            var10000 = var1.getlocal(0);
            var7 = "pos";
            var4 = var10000;
            var5 = var4.__getattr__(var7);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var7, var5);
         } else {
            var1.setline(672);
            var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var6._eq(PyString.fromInterned("("));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(673);
               var1.getlocal(0).__getattr__("commentlist").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
            } else {
               var1.setline(674);
               var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var6._eq(PyString.fromInterned("["));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(675);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getdomainliteral").__call__(var2));
               } else {
                  var1.setline(676);
                  var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var6._eq(PyString.fromInterned("."));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(677);
                     var10000 = var1.getlocal(0);
                     var7 = "pos";
                     var4 = var10000;
                     var5 = var4.__getattr__(var7);
                     var5 = var5._iadd(Py.newInteger(1));
                     var4.__setattr__(var7, var5);
                     var1.setline(678);
                     var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                  } else {
                     var1.setline(679);
                     var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                     var10000 = var6._in(var1.getlocal(0).__getattr__("atomends"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(681);
                     var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getatom").__call__(var2));
                  }
               }
            }
         }
      }

      var1.setline(682);
      var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getdelimited$40(PyFrame var1, ThreadState var2) {
      var1.setline(696);
      PyString.fromInterned("Parse a header fragment delimited by special characters.\n\n        `beginchar' is the start character for the fragment.  If self is not\n        looking at an instance of `beginchar' then getdelimited returns the\n        empty string.\n\n        `endchars' is a sequence of allowable end-delimiting characters.\n        Parsing stops when one of these is encountered.\n\n        If `allowcomments' is non-zero, embedded RFC 2822 comments are allowed\n        within the parsed fragment.\n        ");
      var1.setline(697);
      PyObject var3 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(698);
         PyString var7 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(700);
         PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("")});
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(701);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(5, var8);
         var4 = null;
         var1.setline(702);
         var10000 = var1.getlocal(0);
         String var9 = "pos";
         PyObject var5 = var10000;
         PyObject var6 = var5.__getattr__(var9);
         var6 = var6._iadd(Py.newInteger(1));
         var5.__setattr__(var9, var6);

         while(true) {
            var1.setline(703);
            PyObject var10 = var1.getlocal(0).__getattr__("pos");
            var10000 = var10._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
            var4 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(704);
            var10 = var1.getlocal(5);
            var10000 = var10._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(705);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos")));
               var1.setline(706);
               var8 = Py.newInteger(0);
               var1.setlocal(5, var8);
               var4 = null;
            } else {
               var1.setline(707);
               var10 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var10._in(var1.getlocal(2));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(708);
                  var10000 = var1.getlocal(0);
                  var9 = "pos";
                  var5 = var10000;
                  var6 = var5.__getattr__(var9);
                  var6 = var6._iadd(Py.newInteger(1));
                  var5.__setattr__(var9, var6);
                  break;
               }

               var1.setline(710);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var10 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var10._eq(PyString.fromInterned("("));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(711);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
                  continue;
               }

               var1.setline(713);
               var10 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var10._eq(PyString.fromInterned("\\"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(714);
                  var8 = Py.newInteger(1);
                  var1.setlocal(5, var8);
                  var4 = null;
               } else {
                  var1.setline(716);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos")));
               }
            }

            var1.setline(717);
            var10000 = var1.getlocal(0);
            var9 = "pos";
            var5 = var10000;
            var6 = var5.__getattr__(var9);
            var6 = var6._iadd(Py.newInteger(1));
            var5.__setattr__(var9, var6);
         }

         var1.setline(719);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getquote$41(PyFrame var1, ThreadState var2) {
      var1.setline(722);
      PyString.fromInterned("Get a quote-delimited fragment from self's field.");
      var1.setline(723);
      PyObject var3 = var1.getlocal(0).__getattr__("getdelimited").__call__((ThreadState)var2, PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\"\r"), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcomment$42(PyFrame var1, ThreadState var2) {
      var1.setline(726);
      PyString.fromInterned("Get a parenthesis-delimited fragment from self's field.");
      var1.setline(727);
      PyObject var3 = var1.getlocal(0).__getattr__("getdelimited").__call__((ThreadState)var2, PyString.fromInterned("("), (PyObject)PyString.fromInterned(")\r"), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getdomainliteral$43(PyFrame var1, ThreadState var2) {
      var1.setline(730);
      PyString.fromInterned("Parse an RFC 2822 domain-literal.");
      var1.setline(731);
      PyObject var3 = PyString.fromInterned("[%s]")._mod(var1.getlocal(0).__getattr__("getdelimited").__call__((ThreadState)var2, PyString.fromInterned("["), (PyObject)PyString.fromInterned("]\r"), (PyObject)Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getatom$44(PyFrame var1, ThreadState var2) {
      var1.setline(739);
      PyString.fromInterned("Parse an RFC 2822 atom.\n\n        Optional atomends specifies a different set of end token delimiters\n        (the default is to use self.atomends).  This is used e.g. in\n        getphraselist() since phrase endings must not include the `.' (which\n        is legal in phrases).");
      var1.setline(740);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(741);
      PyObject var6 = var1.getlocal(1);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(742);
         var6 = var1.getlocal(0).__getattr__("atomends");
         var1.setlocal(1, var6);
         var3 = null;
      }

      while(true) {
         var1.setline(744);
         var6 = var1.getlocal(0).__getattr__("pos");
         var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(745);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(747);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos")));
         var1.setline(748);
         var10000 = var1.getlocal(0);
         String var7 = "pos";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var7);
         var5 = var5._iadd(Py.newInteger(1));
         var4.__setattr__(var7, var5);
      }

      var1.setline(750);
      var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getphraselist$45(PyFrame var1, ThreadState var2) {
      var1.setline(758);
      PyString.fromInterned("Parse a sequence of RFC 2822 phrases.\n\n        A phrase is a sequence of words, which are in turn either RFC 2822\n        atoms or quoted-strings.  Phrases are canonicalized by squeezing all\n        runs of continuous whitespace into one space.\n        ");
      var1.setline(759);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var6;
      while(true) {
         var1.setline(761);
         var6 = var1.getlocal(0).__getattr__("pos");
         PyObject var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("field")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(762);
         var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
         var10000 = var6._in(var1.getlocal(0).__getattr__("LWS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(763);
            var10000 = var1.getlocal(0);
            String var7 = "pos";
            PyObject var4 = var10000;
            PyObject var5 = var4.__getattr__(var7);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var7, var5);
         } else {
            var1.setline(764);
            var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
            var10000 = var6._eq(PyString.fromInterned("\""));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(765);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getquote").__call__(var2));
            } else {
               var1.setline(766);
               var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
               var10000 = var6._eq(PyString.fromInterned("("));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(767);
                  var1.getlocal(0).__getattr__("commentlist").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getcomment").__call__(var2));
               } else {
                  var1.setline(768);
                  var6 = var1.getlocal(0).__getattr__("field").__getitem__(var1.getlocal(0).__getattr__("pos"));
                  var10000 = var6._in(var1.getlocal(0).__getattr__("phraseends"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(771);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("getatom").__call__(var2, var1.getlocal(0).__getattr__("phraseends")));
               }
            }
         }
      }

      var1.setline(773);
      var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject AddressList$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An AddressList encapsulates a list of parsed RFC 2822 addresses."));
      var1.setline(776);
      PyString.fromInterned("An AddressList encapsulates a list of parsed RFC 2822 addresses.");
      var1.setline(777);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$47, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(784);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$48, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(787);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$49, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(790);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __add__$50, (PyObject)null);
      var1.setlocal("__add__", var4);
      var3 = null;
      var1.setline(799);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iadd__$51, (PyObject)null);
      var1.setlocal("__iadd__", var4);
      var3 = null;
      var1.setline(806);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __sub__$52, (PyObject)null);
      var1.setlocal("__sub__", var4);
      var3 = null;
      var1.setline(814);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __isub__$53, (PyObject)null);
      var1.setlocal("__isub__", var4);
      var3 = null;
      var1.setline(821);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$54, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(778);
      var1.getglobal("AddrlistClass").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(779);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(780);
         var3 = var1.getlocal(0).__getattr__("getaddrlist").__call__(var2);
         var1.getlocal(0).__setattr__("addresslist", var3);
         var3 = null;
      } else {
         var1.setline(782);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"addresslist", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __len__$48(PyFrame var1, ThreadState var2) {
      var1.setline(785);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("addresslist"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$49(PyFrame var1, ThreadState var2) {
      var1.setline(788);
      PyObject var3 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("dump_address_pair"), var1.getlocal(0).__getattr__("addresslist")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __add__$50(PyFrame var1, ThreadState var2) {
      var1.setline(792);
      PyObject var3 = var1.getglobal("AddressList").__call__(var2, var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(793);
      var3 = var1.getlocal(0).__getattr__("addresslist").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(2).__setattr__("addresslist", var3);
      var3 = null;
      var1.setline(794);
      var3 = var1.getlocal(1).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(794);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(797);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(795);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(796);
            var1.getlocal(2).__getattr__("addresslist").__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject __iadd__$51(PyFrame var1, ThreadState var2) {
      var1.setline(801);
      PyObject var3 = var1.getlocal(1).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(801);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(804);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(802);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(803);
            var1.getlocal(0).__getattr__("addresslist").__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject __sub__$52(PyFrame var1, ThreadState var2) {
      var1.setline(808);
      PyObject var3 = var1.getglobal("AddressList").__call__(var2, var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(809);
      var3 = var1.getlocal(0).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(809);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(812);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(810);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._in(var1.getlocal(1).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(811);
            var1.getlocal(2).__getattr__("addresslist").__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject __isub__$53(PyFrame var1, ThreadState var2) {
      var1.setline(816);
      PyObject var3 = var1.getlocal(1).__getattr__("addresslist").__iter__();

      while(true) {
         var1.setline(816);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(819);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(817);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("addresslist"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(818);
            var1.getlocal(0).__getattr__("addresslist").__getattr__("remove").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject __getitem__$54(PyFrame var1, ThreadState var2) {
      var1.setline(823);
      PyObject var3 = var1.getlocal(0).__getattr__("addresslist").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dump_address_pair$55(PyFrame var1, ThreadState var2) {
      var1.setline(826);
      PyString.fromInterned("Dump a (name, address) pair in a canonicalized form.");
      var1.setline(827);
      PyObject var3;
      if (var1.getlocal(0).__getitem__(Py.newInteger(0)).__nonzero__()) {
         var1.setline(828);
         var3 = PyString.fromInterned("\"")._add(var1.getlocal(0).__getitem__(Py.newInteger(0)))._add(PyString.fromInterned("\" <"))._add(var1.getlocal(0).__getitem__(Py.newInteger(1)))._add(PyString.fromInterned(">"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(830);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parsedate_tz$56(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyString.fromInterned("Convert a date string to a time tuple.\n\n    Accounts for military timezones.\n    ");
      var1.setline(860);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(861);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(862);
         PyObject var4 = var1.getlocal(0).__getattr__("split").__call__(var2);
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(863);
         var4 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(-1));
         PyObject var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned(","), PyString.fromInterned(".")}));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
            var10000 = var4._in(var1.getglobal("_daynames"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(865);
            var1.getlocal(0).__delitem__((PyObject)Py.newInteger(0));
         } else {
            var1.setline(868);
            var4 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(869);
            var4 = var1.getlocal(1);
            var10000 = var4._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(870);
               var4 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setitem__((PyObject)Py.newInteger(0), var4);
               var4 = null;
            }
         }

         var1.setline(871);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var4._eq(Py.newInteger(3));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(872);
            var4 = var1.getlocal(0).__getitem__(Py.newInteger(0)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(873);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var4._eq(Py.newInteger(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(874);
               var4 = var1.getlocal(2)._add(var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
               var1.setlocal(0, var4);
               var4 = null;
            }
         }

         var1.setline(875);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var4._eq(Py.newInteger(4));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(876);
            var4 = var1.getlocal(0).__getitem__(Py.newInteger(3));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(877);
            var4 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+"));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(878);
            var4 = var1.getlocal(1);
            var10000 = var4._gt(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(879);
               PyList var10 = new PyList(new PyObject[]{var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null), var1.getlocal(3).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
               var1.getlocal(0).__setslice__(Py.newInteger(3), (PyObject)null, (PyObject)null, var10);
               var4 = null;
            } else {
               var1.setline(881);
               var1.getlocal(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
            }
         }

         var1.setline(882);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var4._lt(Py.newInteger(5));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(883);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(884);
            var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
            var1.setlocal(0, var4);
            var4 = null;
            var1.setline(885);
            var4 = var1.getlocal(0);
            PyObject[] var5 = Py.unpackSequence(var4, 5);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[4];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
            var1.setline(886);
            var4 = var1.getlocal(5).__getattr__("lower").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(887);
            var4 = var1.getlocal(5);
            var10000 = var4._in(var1.getglobal("_monthnames"));
            var4 = null;
            PyTuple var11;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(888);
               var11 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4).__getattr__("lower").__call__(var2)});
               var5 = Py.unpackSequence(var11, 2);
               var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
               var1.setline(889);
               var4 = var1.getlocal(5);
               var10000 = var4._in(var1.getglobal("_monthnames"));
               var4 = null;
               if (var10000.__not__().__nonzero__()) {
                  var1.setline(890);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }
            }

            var1.setline(891);
            var4 = var1.getglobal("_monthnames").__getattr__("index").__call__(var2, var1.getlocal(5))._add(Py.newInteger(1));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(892);
            var4 = var1.getlocal(5);
            var10000 = var4._gt(Py.newInteger(12));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(892);
               var4 = var1.getlocal(5)._sub(Py.newInteger(12));
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(893);
            var4 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
            var10000 = var4._eq(PyString.fromInterned(","));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(894);
               var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(4, var4);
               var4 = null;
            }

            var1.setline(895);
            var4 = var1.getlocal(6).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(896);
            var4 = var1.getlocal(1);
            var10000 = var4._gt(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(897);
               var11 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(6)});
               var5 = Py.unpackSequence(var11, 2);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(7, var6);
               var6 = null;
               var4 = null;
            }

            var1.setline(898);
            var4 = var1.getlocal(6).__getitem__(Py.newInteger(-1));
            var10000 = var4._eq(PyString.fromInterned(","));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(899);
               var4 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(900);
            if (var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("isdigit").__call__(var2).__not__().__nonzero__()) {
               var1.setline(901);
               var11 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(6)});
               var5 = Py.unpackSequence(var11, 2);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(8, var6);
               var6 = null;
               var4 = null;
            }

            var1.setline(902);
            var4 = var1.getlocal(7).__getitem__(Py.newInteger(-1));
            var10000 = var4._eq(PyString.fromInterned(","));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(903);
               var4 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
               var1.setlocal(7, var4);
               var4 = null;
            }

            var1.setline(904);
            var4 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(905);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
            var10000 = var4._eq(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(906);
               var4 = var1.getlocal(7);
               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var4 = null;
               var1.setline(907);
               PyString var12 = PyString.fromInterned("0");
               var1.setlocal(11, var12);
               var4 = null;
            } else {
               var1.setline(908);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var10000 = var4._eq(Py.newInteger(3));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(911);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(909);
               var4 = var1.getlocal(7);
               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(11, var6);
               var6 = null;
               var4 = null;
            }

            PyException var13;
            try {
               var1.setline(913);
               var4 = var1.getglobal("int").__call__(var2, var1.getlocal(6));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(914);
               var4 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(915);
               var4 = var1.getglobal("int").__call__(var2, var1.getlocal(9));
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(916);
               var4 = var1.getglobal("int").__call__(var2, var1.getlocal(10));
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(917);
               var4 = var1.getglobal("int").__call__(var2, var1.getlocal(11));
               var1.setlocal(11, var4);
               var4 = null;
            } catch (Throwable var7) {
               var13 = Py.setException(var7, var1);
               if (var13.match(var1.getglobal("ValueError"))) {
                  var1.setline(919);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }

               throw var13;
            }

            var1.setline(920);
            var4 = var1.getglobal("None");
            var1.setlocal(12, var4);
            var4 = null;
            var1.setline(921);
            var4 = var1.getlocal(8).__getattr__("upper").__call__(var2);
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(922);
            var4 = var1.getlocal(8);
            var10000 = var4._in(var1.getglobal("_timezones"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(923);
               var4 = var1.getglobal("_timezones").__getitem__(var1.getlocal(8));
               var1.setlocal(12, var4);
               var4 = null;
            } else {
               try {
                  var1.setline(926);
                  var4 = var1.getglobal("int").__call__(var2, var1.getlocal(8));
                  var1.setlocal(12, var4);
                  var4 = null;
               } catch (Throwable var8) {
                  var13 = Py.setException(var8, var1);
                  if (!var13.match(var1.getglobal("ValueError"))) {
                     throw var13;
                  }

                  var1.setline(928);
               }
            }

            var1.setline(930);
            if (var1.getlocal(12).__nonzero__()) {
               var1.setline(931);
               var4 = var1.getlocal(12);
               var10000 = var4._lt(Py.newInteger(0));
               var4 = null;
               PyInteger var14;
               if (var10000.__nonzero__()) {
                  var1.setline(932);
                  var14 = Py.newInteger(-1);
                  var1.setlocal(13, var14);
                  var4 = null;
                  var1.setline(933);
                  var4 = var1.getlocal(12).__neg__();
                  var1.setlocal(12, var4);
                  var4 = null;
               } else {
                  var1.setline(935);
                  var14 = Py.newInteger(1);
                  var1.setlocal(13, var14);
                  var4 = null;
               }

               var1.setline(936);
               var4 = var1.getlocal(13)._mul(var1.getlocal(12)._floordiv(Py.newInteger(100))._mul(Py.newInteger(3600))._add(var1.getlocal(12)._mod(Py.newInteger(100))._mul(Py.newInteger(60))));
               var1.setlocal(12, var4);
               var4 = null;
            }

            var1.setline(937);
            PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(4), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0), var1.getlocal(12)});
            var1.f_lasti = -1;
            return var9;
         }
      }
   }

   public PyObject parsedate$57(PyFrame var1, ThreadState var2) {
      var1.setline(941);
      PyString.fromInterned("Convert a time string to a time tuple.");
      var1.setline(942);
      PyObject var3 = var1.getglobal("parsedate_tz").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(943);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(944);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(945);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(9), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject mktime_tz$58(PyFrame var1, ThreadState var2) {
      var1.setline(949);
      PyString.fromInterned("Turn a 10-tuple as returned by parsedate_tz() into a UTC timestamp.");
      var1.setline(950);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(9));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(952);
         var3 = var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null)._add(new PyTuple(new PyObject[]{Py.newInteger(-1)})));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(954);
         PyObject var4 = var1.getglobal("time").__getattr__("mktime").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null)._add(new PyTuple(new PyObject[]{Py.newInteger(0)})));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(955);
         var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getitem__(Py.newInteger(9)))._sub(var1.getglobal("time").__getattr__("timezone"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject formatdate$59(PyFrame var1, ThreadState var2) {
      var1.setline(966);
      PyString.fromInterned("Returns time format preferred for Internet standards.\n\n    Sun, 06 Nov 1994 08:49:37 GMT  ; RFC 822, updated by RFC 1123\n\n    According to RFC 1123, day and month names must always be in\n    English.  If not for that, this code could use strftime().  It\n    can't because strftime() honors the locale and could generate\n    non-English names.\n    ");
      var1.setline(967);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(968);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(969);
      var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(970);
      var3 = PyString.fromInterned("%s, %02d %s %04d %02d:%02d:%02d GMT")._mod(new PyTuple(new PyObject[]{(new PyTuple(new PyObject[]{PyString.fromInterned("Mon"), PyString.fromInterned("Tue"), PyString.fromInterned("Wed"), PyString.fromInterned("Thu"), PyString.fromInterned("Fri"), PyString.fromInterned("Sat"), PyString.fromInterned("Sun")})).__getitem__(var1.getlocal(0).__getitem__(Py.newInteger(6))), var1.getlocal(0).__getitem__(Py.newInteger(2)), (new PyTuple(new PyObject[]{PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")})).__getitem__(var1.getlocal(0).__getitem__(Py.newInteger(1))._sub(Py.newInteger(1))), var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getitem__(Py.newInteger(3)), var1.getlocal(0).__getitem__(Py.newInteger(4)), var1.getlocal(0).__getitem__(Py.newInteger(5))}));
      var1.f_lasti = -1;
      return var3;
   }

   public rfc822$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Message$1 = Py.newCode(0, var2, var1, "Message", 85, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "seekable"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 88, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      rewindbody$3 = Py.newCode(1, var2, var1, "rewindbody", 116, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lst", "headerseen", "firstline", "startofline", "unread", "tell", "line", "x"};
      readheaders$4 = Py.newCode(1, var2, var1, "readheaders", 122, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "i"};
      isheader$5 = Py.newCode(2, var2, var1, "isheader", 202, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      islast$6 = Py.newCode(2, var2, var1, "islast", 214, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      iscomment$7 = Py.newCode(2, var2, var1, "iscomment", 224, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n", "lst", "hit", "line"};
      getallmatchingheaders$8 = Py.newCode(2, var2, var1, "getallmatchingheaders", 233, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n", "lst", "hit", "line"};
      getfirstmatchingheader$9 = Py.newCode(2, var2, var1, "getfirstmatchingheader", 255, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "lst"};
      getrawheader$10 = Py.newCode(2, var2, var1, "getrawheader", 275, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "default"};
      getheader$11 = Py.newCode(3, var2, var1, "getheader", 290, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "result", "current", "have_header", "s"};
      getheaders$12 = Py.newCode(2, var2, var1, "getheaders", 300, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "alist"};
      getaddr$13 = Py.newCode(2, var2, var1, "getaddr", 325, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "raw", "h", "i", "addr", "alladdrs", "a"};
      getaddrlist$14 = Py.newCode(2, var2, var1, "getaddrlist", 338, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "data"};
      getdate$15 = Py.newCode(2, var2, var1, "getdate", 360, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "data"};
      getdate_tz$16 = Py.newCode(2, var2, var1, "getdate_tz", 372, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$17 = Py.newCode(1, var2, var1, "__len__", 387, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$18 = Py.newCode(2, var2, var1, "__getitem__", 391, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "text", "line"};
      __setitem__$19 = Py.newCode(3, var2, var1, "__setitem__", 395, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "n", "lst", "hit", "i", "line"};
      __delitem__$20 = Py.newCode(2, var2, var1, "__delitem__", 408, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "default", "lowername", "text", "line"};
      setdefault$21 = Py.newCode(3, var2, var1, "setdefault", 429, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      has_key$22 = Py.newCode(2, var2, var1, "has_key", 440, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __contains__$23 = Py.newCode(2, var2, var1, "__contains__", 444, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$24 = Py.newCode(1, var2, var1, "__iter__", 448, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$25 = Py.newCode(1, var2, var1, "keys", 451, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$26 = Py.newCode(1, var2, var1, "values", 455, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$27 = Py.newCode(1, var2, var1, "items", 459, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$28 = Py.newCode(1, var2, var1, "__str__", 466, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      unquote$29 = Py.newCode(1, var2, var1, "unquote", 477, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      quote$30 = Py.newCode(1, var2, var1, "quote", 487, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address", "a", "lst"};
      parseaddr$31 = Py.newCode(1, var2, var1, "parseaddr", 492, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AddrlistClass$32 = Py.newCode(0, var2, var1, "AddrlistClass", 501, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "field"};
      __init__$33 = Py.newCode(2, var2, var1, "__init__", 513, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      gotonext$34 = Py.newCode(1, var2, var1, "gotonext", 531, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "ad"};
      getaddrlist$35 = Py.newCode(1, var2, var1, "getaddrlist", 540, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldpos", "oldcl", "plist", "returnlist", "addrspec", "fieldlen", "routeaddr"};
      getaddress$36 = Py.newCode(1, var2, var1, "getaddress", 552, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expectroute", "adlist"};
      getrouteaddr$37 = Py.newCode(1, var2, var1, "getrouteaddr", 610, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "aslist"};
      getaddrspec$38 = Py.newCode(1, var2, var1, "getaddrspec", 642, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sdlist"};
      getdomain$39 = Py.newCode(1, var2, var1, "getdomain", 666, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "beginchar", "endchars", "allowcomments", "slist", "quote"};
      getdelimited$40 = Py.newCode(4, var2, var1, "getdelimited", 684, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getquote$41 = Py.newCode(1, var2, var1, "getquote", 721, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcomment$42 = Py.newCode(1, var2, var1, "getcomment", 725, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getdomainliteral$43 = Py.newCode(1, var2, var1, "getdomainliteral", 729, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "atomends", "atomlist"};
      getatom$44 = Py.newCode(2, var2, var1, "getatom", 733, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "plist"};
      getphraselist$45 = Py.newCode(1, var2, var1, "getphraselist", 752, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AddressList$46 = Py.newCode(0, var2, var1, "AddressList", 775, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "field"};
      __init__$47 = Py.newCode(2, var2, var1, "__init__", 777, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$48 = Py.newCode(1, var2, var1, "__len__", 784, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$49 = Py.newCode(1, var2, var1, "__str__", 787, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "newaddr", "x"};
      __add__$50 = Py.newCode(2, var2, var1, "__add__", 790, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "x"};
      __iadd__$51 = Py.newCode(2, var2, var1, "__iadd__", 799, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "newaddr", "x"};
      __sub__$52 = Py.newCode(2, var2, var1, "__sub__", 806, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "x"};
      __isub__$53 = Py.newCode(2, var2, var1, "__isub__", 814, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      __getitem__$54 = Py.newCode(2, var2, var1, "__getitem__", 821, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pair"};
      dump_address_pair$55 = Py.newCode(1, var2, var1, "dump_address_pair", 825, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "i", "stuff", "s", "dd", "mm", "yy", "tm", "tz", "thh", "tmm", "tss", "tzoffset", "tzsign"};
      parsedate_tz$56 = Py.newCode(1, var2, var1, "parsedate_tz", 855, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "t"};
      parsedate$57 = Py.newCode(1, var2, var1, "parsedate", 940, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "t"};
      mktime_tz$58 = Py.newCode(1, var2, var1, "mktime_tz", 948, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timeval"};
      formatdate$59 = Py.newCode(1, var2, var1, "formatdate", 957, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new rfc822$py("rfc822$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(rfc822$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Message$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.rewindbody$3(var2, var3);
         case 4:
            return this.readheaders$4(var2, var3);
         case 5:
            return this.isheader$5(var2, var3);
         case 6:
            return this.islast$6(var2, var3);
         case 7:
            return this.iscomment$7(var2, var3);
         case 8:
            return this.getallmatchingheaders$8(var2, var3);
         case 9:
            return this.getfirstmatchingheader$9(var2, var3);
         case 10:
            return this.getrawheader$10(var2, var3);
         case 11:
            return this.getheader$11(var2, var3);
         case 12:
            return this.getheaders$12(var2, var3);
         case 13:
            return this.getaddr$13(var2, var3);
         case 14:
            return this.getaddrlist$14(var2, var3);
         case 15:
            return this.getdate$15(var2, var3);
         case 16:
            return this.getdate_tz$16(var2, var3);
         case 17:
            return this.__len__$17(var2, var3);
         case 18:
            return this.__getitem__$18(var2, var3);
         case 19:
            return this.__setitem__$19(var2, var3);
         case 20:
            return this.__delitem__$20(var2, var3);
         case 21:
            return this.setdefault$21(var2, var3);
         case 22:
            return this.has_key$22(var2, var3);
         case 23:
            return this.__contains__$23(var2, var3);
         case 24:
            return this.__iter__$24(var2, var3);
         case 25:
            return this.keys$25(var2, var3);
         case 26:
            return this.values$26(var2, var3);
         case 27:
            return this.items$27(var2, var3);
         case 28:
            return this.__str__$28(var2, var3);
         case 29:
            return this.unquote$29(var2, var3);
         case 30:
            return this.quote$30(var2, var3);
         case 31:
            return this.parseaddr$31(var2, var3);
         case 32:
            return this.AddrlistClass$32(var2, var3);
         case 33:
            return this.__init__$33(var2, var3);
         case 34:
            return this.gotonext$34(var2, var3);
         case 35:
            return this.getaddrlist$35(var2, var3);
         case 36:
            return this.getaddress$36(var2, var3);
         case 37:
            return this.getrouteaddr$37(var2, var3);
         case 38:
            return this.getaddrspec$38(var2, var3);
         case 39:
            return this.getdomain$39(var2, var3);
         case 40:
            return this.getdelimited$40(var2, var3);
         case 41:
            return this.getquote$41(var2, var3);
         case 42:
            return this.getcomment$42(var2, var3);
         case 43:
            return this.getdomainliteral$43(var2, var3);
         case 44:
            return this.getatom$44(var2, var3);
         case 45:
            return this.getphraselist$45(var2, var3);
         case 46:
            return this.AddressList$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this.__len__$48(var2, var3);
         case 49:
            return this.__str__$49(var2, var3);
         case 50:
            return this.__add__$50(var2, var3);
         case 51:
            return this.__iadd__$51(var2, var3);
         case 52:
            return this.__sub__$52(var2, var3);
         case 53:
            return this.__isub__$53(var2, var3);
         case 54:
            return this.__getitem__$54(var2, var3);
         case 55:
            return this.dump_address_pair$55(var2, var3);
         case 56:
            return this.parsedate_tz$56(var2, var3);
         case 57:
            return this.parsedate$57(var2, var3);
         case 58:
            return this.mktime_tz$58(var2, var3);
         case 59:
            return this.formatdate$59(var2, var3);
         default:
            return null;
      }
   }
}
