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
@Filename("Cookie.py")
public class Cookie$py extends PyFunctionTable implements PyRunnable {
   static Cookie$py self;
   static final PyCode f$0;
   static final PyCode CookieError$1;
   static final PyCode f$2;
   static final PyCode _quote$3;
   static final PyCode _unquote$4;
   static final PyCode _getdate$5;
   static final PyCode Morsel$6;
   static final PyCode __init__$7;
   static final PyCode __setitem__$8;
   static final PyCode isReservedKey$9;
   static final PyCode set$10;
   static final PyCode output$11;
   static final PyCode __repr__$12;
   static final PyCode js_output$13;
   static final PyCode OutputString$14;
   static final PyCode BaseCookie$15;
   static final PyCode value_decode$16;
   static final PyCode value_encode$17;
   static final PyCode __init__$18;
   static final PyCode _BaseCookie__set$19;
   static final PyCode __setitem__$20;
   static final PyCode output$21;
   static final PyCode __repr__$22;
   static final PyCode js_output$23;
   static final PyCode load$24;
   static final PyCode _BaseCookie__ParseString$25;
   static final PyCode SimpleCookie$26;
   static final PyCode value_decode$27;
   static final PyCode value_encode$28;
   static final PyCode SerialCookie$29;
   static final PyCode __init__$30;
   static final PyCode value_decode$31;
   static final PyCode value_encode$32;
   static final PyCode SmartCookie$33;
   static final PyCode __init__$34;
   static final PyCode value_decode$35;
   static final PyCode value_encode$36;
   static final PyCode _test$37;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nHere's a sample session to show how to use this module.\nAt the moment, this is the only documentation.\n\nThe Basics\n----------\n\nImporting is easy..\n\n   >>> import Cookie\n\nMost of the time you start by creating a cookie.  Cookies come in\nthree flavors, each with slightly different encoding semantics, but\nmore on that later.\n\n   >>> C = Cookie.SimpleCookie()\n   >>> C = Cookie.SerialCookie()\n   >>> C = Cookie.SmartCookie()\n\n[Note: Long-time users of Cookie.py will remember using\nCookie.Cookie() to create an Cookie object.  Although deprecated, it\nis still supported by the code.  See the Backward Compatibility notes\nfor more information.]\n\nOnce you've created your Cookie, you can add values just as if it were\na dictionary.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"fig\"] = \"newton\"\n   >>> C[\"sugar\"] = \"wafer\"\n   >>> C.output()\n   'Set-Cookie: fig=newton\\r\\nSet-Cookie: sugar=wafer'\n\nNotice that the printable representation of a Cookie is the\nappropriate format for a Set-Cookie: header.  This is the\ndefault behavior.  You can change the header and printed\nattributes by using the .output() function\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"rocky\"] = \"road\"\n   >>> C[\"rocky\"][\"path\"] = \"/cookie\"\n   >>> print C.output(header=\"Cookie:\")\n   Cookie: rocky=road; Path=/cookie\n   >>> print C.output(attrs=[], header=\"Cookie:\")\n   Cookie: rocky=road\n\nThe load() method of a Cookie extracts cookies from a string.  In a\nCGI script, you would use this method to extract the cookies from the\nHTTP_COOKIE environment variable.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C.load(\"chips=ahoy; vienna=finger\")\n   >>> C.output()\n   'Set-Cookie: chips=ahoy\\r\\nSet-Cookie: vienna=finger'\n\nThe load() method is darn-tootin smart about identifying cookies\nwithin a string.  Escaped quotation marks, nested semicolons, and other\nsuch trickeries do not confuse it.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C.load('keebler=\"E=everybody; L=\\\\\"Loves\\\\\"; fudge=\\\\012;\";')\n   >>> print C\n   Set-Cookie: keebler=\"E=everybody; L=\\\"Loves\\\"; fudge=\\012;\"\n\nEach element of the Cookie also supports all of the RFC 2109\nCookie attributes.  Here's an example which sets the Path\nattribute.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"oreo\"] = \"doublestuff\"\n   >>> C[\"oreo\"][\"path\"] = \"/\"\n   >>> print C\n   Set-Cookie: oreo=doublestuff; Path=/\n\nEach dictionary element has a 'value' attribute, which gives you\nback the value associated with the key.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"twix\"] = \"none for you\"\n   >>> C[\"twix\"].value\n   'none for you'\n\n\nA Bit More Advanced\n-------------------\n\nAs mentioned before, there are three different flavors of Cookie\nobjects, each with different encoding/decoding semantics.  This\nsection briefly discusses the differences.\n\nSimpleCookie\n\nThe SimpleCookie expects that all values should be standard strings.\nJust to be sure, SimpleCookie invokes the str() builtin to convert\nthe value to a string, when the values are set dictionary-style.\n\n   >>> C = Cookie.SimpleCookie()\n   >>> C[\"number\"] = 7\n   >>> C[\"string\"] = \"seven\"\n   >>> C[\"number\"].value\n   '7'\n   >>> C[\"string\"].value\n   'seven'\n   >>> C.output()\n   'Set-Cookie: number=7\\r\\nSet-Cookie: string=seven'\n\n\nSerialCookie\n\nThe SerialCookie expects that all values should be serialized using\ncPickle (or pickle, if cPickle isn't available).  As a result of\nserializing, SerialCookie can save almost any Python object to a\nvalue, and recover the exact same object when the cookie has been\nreturned.  (SerialCookie can yield some strange-looking cookie\nvalues, however.)\n\n   >>> C = Cookie.SerialCookie()\n   >>> C[\"number\"] = 7\n   >>> C[\"string\"] = \"seven\"\n   >>> C[\"number\"].value\n   7\n   >>> C[\"string\"].value\n   'seven'\n   >>> C.output()\n   'Set-Cookie: number=\"I7\\\\012.\"\\r\\nSet-Cookie: string=\"S\\'seven\\'\\\\012p1\\\\012.\"'\n\nBe warned, however, if SerialCookie cannot de-serialize a value (because\nit isn't a valid pickle'd object), IT WILL RAISE AN EXCEPTION.\n\n\nSmartCookie\n\nThe SmartCookie combines aspects of each of the other two flavors.\nWhen setting a value in a dictionary-fashion, the SmartCookie will\nserialize (ala cPickle) the value *if and only if* it isn't a\nPython string.  String objects are *not* serialized.  Similarly,\nwhen the load() method parses out values, it attempts to de-serialize\nthe value.  If it fails, then it fallsback to treating the value\nas a string.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"number\"] = 7\n   >>> C[\"string\"] = \"seven\"\n   >>> C[\"number\"].value\n   7\n   >>> C[\"string\"].value\n   'seven'\n   >>> C.output()\n   'Set-Cookie: number=\"I7\\\\012.\"\\r\\nSet-Cookie: string=seven'\n\n\nBackwards Compatibility\n-----------------------\n\nIn order to keep compatibilty with earlier versions of Cookie.py,\nit is still possible to use Cookie.Cookie() to create a Cookie.  In\nfact, this simply returns a SmartCookie.\n\n   >>> C = Cookie.Cookie()\n   >>> print C.__class__.__name__\n   SmartCookie\n\n\nFinis.\n"));
      var1.setline(206);
      PyString.fromInterned("\nHere's a sample session to show how to use this module.\nAt the moment, this is the only documentation.\n\nThe Basics\n----------\n\nImporting is easy..\n\n   >>> import Cookie\n\nMost of the time you start by creating a cookie.  Cookies come in\nthree flavors, each with slightly different encoding semantics, but\nmore on that later.\n\n   >>> C = Cookie.SimpleCookie()\n   >>> C = Cookie.SerialCookie()\n   >>> C = Cookie.SmartCookie()\n\n[Note: Long-time users of Cookie.py will remember using\nCookie.Cookie() to create an Cookie object.  Although deprecated, it\nis still supported by the code.  See the Backward Compatibility notes\nfor more information.]\n\nOnce you've created your Cookie, you can add values just as if it were\na dictionary.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"fig\"] = \"newton\"\n   >>> C[\"sugar\"] = \"wafer\"\n   >>> C.output()\n   'Set-Cookie: fig=newton\\r\\nSet-Cookie: sugar=wafer'\n\nNotice that the printable representation of a Cookie is the\nappropriate format for a Set-Cookie: header.  This is the\ndefault behavior.  You can change the header and printed\nattributes by using the .output() function\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"rocky\"] = \"road\"\n   >>> C[\"rocky\"][\"path\"] = \"/cookie\"\n   >>> print C.output(header=\"Cookie:\")\n   Cookie: rocky=road; Path=/cookie\n   >>> print C.output(attrs=[], header=\"Cookie:\")\n   Cookie: rocky=road\n\nThe load() method of a Cookie extracts cookies from a string.  In a\nCGI script, you would use this method to extract the cookies from the\nHTTP_COOKIE environment variable.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C.load(\"chips=ahoy; vienna=finger\")\n   >>> C.output()\n   'Set-Cookie: chips=ahoy\\r\\nSet-Cookie: vienna=finger'\n\nThe load() method is darn-tootin smart about identifying cookies\nwithin a string.  Escaped quotation marks, nested semicolons, and other\nsuch trickeries do not confuse it.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C.load('keebler=\"E=everybody; L=\\\\\"Loves\\\\\"; fudge=\\\\012;\";')\n   >>> print C\n   Set-Cookie: keebler=\"E=everybody; L=\\\"Loves\\\"; fudge=\\012;\"\n\nEach element of the Cookie also supports all of the RFC 2109\nCookie attributes.  Here's an example which sets the Path\nattribute.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"oreo\"] = \"doublestuff\"\n   >>> C[\"oreo\"][\"path\"] = \"/\"\n   >>> print C\n   Set-Cookie: oreo=doublestuff; Path=/\n\nEach dictionary element has a 'value' attribute, which gives you\nback the value associated with the key.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"twix\"] = \"none for you\"\n   >>> C[\"twix\"].value\n   'none for you'\n\n\nA Bit More Advanced\n-------------------\n\nAs mentioned before, there are three different flavors of Cookie\nobjects, each with different encoding/decoding semantics.  This\nsection briefly discusses the differences.\n\nSimpleCookie\n\nThe SimpleCookie expects that all values should be standard strings.\nJust to be sure, SimpleCookie invokes the str() builtin to convert\nthe value to a string, when the values are set dictionary-style.\n\n   >>> C = Cookie.SimpleCookie()\n   >>> C[\"number\"] = 7\n   >>> C[\"string\"] = \"seven\"\n   >>> C[\"number\"].value\n   '7'\n   >>> C[\"string\"].value\n   'seven'\n   >>> C.output()\n   'Set-Cookie: number=7\\r\\nSet-Cookie: string=seven'\n\n\nSerialCookie\n\nThe SerialCookie expects that all values should be serialized using\ncPickle (or pickle, if cPickle isn't available).  As a result of\nserializing, SerialCookie can save almost any Python object to a\nvalue, and recover the exact same object when the cookie has been\nreturned.  (SerialCookie can yield some strange-looking cookie\nvalues, however.)\n\n   >>> C = Cookie.SerialCookie()\n   >>> C[\"number\"] = 7\n   >>> C[\"string\"] = \"seven\"\n   >>> C[\"number\"].value\n   7\n   >>> C[\"string\"].value\n   'seven'\n   >>> C.output()\n   'Set-Cookie: number=\"I7\\\\012.\"\\r\\nSet-Cookie: string=\"S\\'seven\\'\\\\012p1\\\\012.\"'\n\nBe warned, however, if SerialCookie cannot de-serialize a value (because\nit isn't a valid pickle'd object), IT WILL RAISE AN EXCEPTION.\n\n\nSmartCookie\n\nThe SmartCookie combines aspects of each of the other two flavors.\nWhen setting a value in a dictionary-fashion, the SmartCookie will\nserialize (ala cPickle) the value *if and only if* it isn't a\nPython string.  String objects are *not* serialized.  Similarly,\nwhen the load() method parses out values, it attempts to de-serialize\nthe value.  If it fails, then it fallsback to treating the value\nas a string.\n\n   >>> C = Cookie.SmartCookie()\n   >>> C[\"number\"] = 7\n   >>> C[\"string\"] = \"seven\"\n   >>> C[\"number\"].value\n   7\n   >>> C[\"string\"].value\n   'seven'\n   >>> C.output()\n   'Set-Cookie: number=\"I7\\\\012.\"\\r\\nSet-Cookie: string=seven'\n\n\nBackwards Compatibility\n-----------------------\n\nIn order to keep compatibilty with earlier versions of Cookie.py,\nit is still possible to use Cookie.Cookie() to create a Cookie.  In\nfact, this simply returns a SmartCookie.\n\n   >>> C = Cookie.Cookie()\n   >>> print C.__class__.__name__\n   SmartCookie\n\n\nFinis.\n");
      var1.setline(213);
      PyObject var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;

      String[] var4;
      PyObject[] var9;
      PyObject var11;
      try {
         var1.setline(216);
         String[] var8 = new String[]{"dumps", "loads"};
         var9 = imp.importFrom("cPickle", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("dumps", var11);
         var4 = null;
         var11 = var9[1];
         var1.setlocal("loads", var11);
         var4 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(218);
         var4 = new String[]{"dumps", "loads"};
         PyObject[] var10 = imp.importFrom("pickle", var4, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal("dumps", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("loads", var5);
         var5 = null;
      }

      var1.setline(220);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(222);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("CookieError"), PyString.fromInterned("BaseCookie"), PyString.fromInterned("SimpleCookie"), PyString.fromInterned("SerialCookie"), PyString.fromInterned("SmartCookie"), PyString.fromInterned("Cookie")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(225);
      var3 = PyString.fromInterned("").__getattr__("join");
      var1.setlocal("_nulljoin", var3);
      var3 = null;
      var1.setline(226);
      var3 = PyString.fromInterned("; ").__getattr__("join");
      var1.setlocal("_semispacejoin", var3);
      var3 = null;
      var1.setline(227);
      var3 = PyString.fromInterned(" ").__getattr__("join");
      var1.setlocal("_spacejoin", var3);
      var3 = null;
      var1.setline(232);
      var9 = new PyObject[]{var1.getname("Exception")};
      var11 = Py.makeClass("CookieError", var9, CookieError$1);
      var1.setlocal("CookieError", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(247);
      var3 = var1.getname("string").__getattr__("ascii_letters")._add(var1.getname("string").__getattr__("digits"))._add(PyString.fromInterned("!#$%&'*+-.^_`|~"));
      var1.setlocal("_LegalChars", var3);
      var3 = null;
      var1.setline(248);
      PyObject[] var10002 = new PyObject[330];
      set$$0(var10002);
      PyDictionary var14 = new PyDictionary(var10002);
      var1.setlocal("_Translator", var14);
      var3 = null;
      var1.setline(313);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      var1.setline(313);
      var9 = Py.EmptyObjects;
      PyFunction var13 = new PyFunction(var1.f_globals, var9, f$2, (PyObject)null);
      PyObject var17 = var13.__call__(var2, var1.getname("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__());
      Arrays.fill(var9, (Object)null);
      var3 = var10000.__call__(var2, var17);
      var1.setlocal("_idmap", var3);
      var3 = null;
      var1.setline(315);
      var9 = new PyObject[]{var1.getname("_LegalChars"), var1.getname("_idmap"), var1.getname("string").__getattr__("translate")};
      PyFunction var15 = new PyFunction(var1.f_globals, var9, _quote$3, (PyObject)null);
      var1.setlocal("_quote", var15);
      var3 = null;
      var1.setline(330);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\[0-3][0-7][0-7]"));
      var1.setlocal("_OctalPatt", var3);
      var3 = null;
      var1.setline(331);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\\\]."));
      var1.setlocal("_QuotePatt", var3);
      var3 = null;
      var1.setline(333);
      var9 = Py.EmptyObjects;
      var15 = new PyFunction(var1.f_globals, var9, _unquote$4, (PyObject)null);
      var1.setlocal("_unquote", var15);
      var3 = null;
      var1.setline(383);
      var12 = new PyList(new PyObject[]{PyString.fromInterned("Mon"), PyString.fromInterned("Tue"), PyString.fromInterned("Wed"), PyString.fromInterned("Thu"), PyString.fromInterned("Fri"), PyString.fromInterned("Sat"), PyString.fromInterned("Sun")});
      var1.setlocal("_weekdayname", var12);
      var3 = null;
      var1.setline(385);
      var12 = new PyList(new PyObject[]{var1.getname("None"), PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")});
      var1.setlocal("_monthname", var12);
      var3 = null;
      var1.setline(389);
      var9 = new PyObject[]{Py.newInteger(0), var1.getname("_weekdayname"), var1.getname("_monthname")};
      var15 = new PyFunction(var1.f_globals, var9, _getdate$5, (PyObject)null);
      var1.setlocal("_getdate", var15);
      var3 = null;
      var1.setline(408);
      var9 = new PyObject[]{var1.getname("dict")};
      var11 = Py.makeClass("Morsel", var9, Morsel$6);
      var1.setlocal("Morsel", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(532);
      PyString var16 = PyString.fromInterned("[\\w\\d!#%&'~_`><@,:/\\$\\*\\+\\-\\.\\^\\|\\)\\(\\?\\}\\{\\=]");
      var1.setlocal("_LegalCharsPatt", var16);
      var3 = null;
      var1.setline(533);
      var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("(?x)(?P<key>")._add(var1.getname("_LegalCharsPatt"))._add(PyString.fromInterned("+?)\\s*=\\s*(?P<val>\"(?:[^\\\\\"]|\\\\.)*\"|\\w{3},\\s[\\s\\w\\d-]{9,11}\\s[\\d:]{8}\\sGMT|"))._add(var1.getname("_LegalCharsPatt"))._add(PyString.fromInterned("*)\\s*;?")));
      var1.setlocal("_CookiePattern", var3);
      var3 = null;
      var1.setline(554);
      var9 = new PyObject[]{var1.getname("dict")};
      var11 = Py.makeClass("BaseCookie", var9, BaseCookie$15);
      var1.setlocal("BaseCookie", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(670);
      var9 = new PyObject[]{var1.getname("BaseCookie")};
      var11 = Py.makeClass("SimpleCookie", var9, SimpleCookie$26);
      var1.setlocal("SimpleCookie", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(684);
      var9 = new PyObject[]{var1.getname("BaseCookie")};
      var11 = Py.makeClass("SerialCookie", var9, SerialCookie$29);
      var1.setlocal("SerialCookie", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(710);
      var9 = new PyObject[]{var1.getname("BaseCookie")};
      var11 = Py.makeClass("SmartCookie", var9, SmartCookie$33);
      var1.setlocal("SmartCookie", var11);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(746);
      var3 = var1.getname("SmartCookie");
      var1.setlocal("Cookie", var3);
      var3 = null;
      var1.setline(751);
      var9 = Py.EmptyObjects;
      var15 = new PyFunction(var1.f_globals, var9, _test$37, (PyObject)null);
      var1.setlocal("_test", var15);
      var3 = null;
      var1.setline(755);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(756);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CookieError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(233);
      return var1.getf_locals();
   }

   private static void set$$0(PyObject[] var0) {
      var0[0] = PyString.fromInterned("\u0000");
      var0[1] = PyString.fromInterned("\\000");
      var0[2] = PyString.fromInterned("\u0001");
      var0[3] = PyString.fromInterned("\\001");
      var0[4] = PyString.fromInterned("\u0002");
      var0[5] = PyString.fromInterned("\\002");
      var0[6] = PyString.fromInterned("\u0003");
      var0[7] = PyString.fromInterned("\\003");
      var0[8] = PyString.fromInterned("\u0004");
      var0[9] = PyString.fromInterned("\\004");
      var0[10] = PyString.fromInterned("\u0005");
      var0[11] = PyString.fromInterned("\\005");
      var0[12] = PyString.fromInterned("\u0006");
      var0[13] = PyString.fromInterned("\\006");
      var0[14] = PyString.fromInterned("\u0007");
      var0[15] = PyString.fromInterned("\\007");
      var0[16] = PyString.fromInterned("\b");
      var0[17] = PyString.fromInterned("\\010");
      var0[18] = PyString.fromInterned("\t");
      var0[19] = PyString.fromInterned("\\011");
      var0[20] = PyString.fromInterned("\n");
      var0[21] = PyString.fromInterned("\\012");
      var0[22] = PyString.fromInterned("\u000b");
      var0[23] = PyString.fromInterned("\\013");
      var0[24] = PyString.fromInterned("\f");
      var0[25] = PyString.fromInterned("\\014");
      var0[26] = PyString.fromInterned("\r");
      var0[27] = PyString.fromInterned("\\015");
      var0[28] = PyString.fromInterned("\u000e");
      var0[29] = PyString.fromInterned("\\016");
      var0[30] = PyString.fromInterned("\u000f");
      var0[31] = PyString.fromInterned("\\017");
      var0[32] = PyString.fromInterned("\u0010");
      var0[33] = PyString.fromInterned("\\020");
      var0[34] = PyString.fromInterned("\u0011");
      var0[35] = PyString.fromInterned("\\021");
      var0[36] = PyString.fromInterned("\u0012");
      var0[37] = PyString.fromInterned("\\022");
      var0[38] = PyString.fromInterned("\u0013");
      var0[39] = PyString.fromInterned("\\023");
      var0[40] = PyString.fromInterned("\u0014");
      var0[41] = PyString.fromInterned("\\024");
      var0[42] = PyString.fromInterned("\u0015");
      var0[43] = PyString.fromInterned("\\025");
      var0[44] = PyString.fromInterned("\u0016");
      var0[45] = PyString.fromInterned("\\026");
      var0[46] = PyString.fromInterned("\u0017");
      var0[47] = PyString.fromInterned("\\027");
      var0[48] = PyString.fromInterned("\u0018");
      var0[49] = PyString.fromInterned("\\030");
      var0[50] = PyString.fromInterned("\u0019");
      var0[51] = PyString.fromInterned("\\031");
      var0[52] = PyString.fromInterned("\u001a");
      var0[53] = PyString.fromInterned("\\032");
      var0[54] = PyString.fromInterned("\u001b");
      var0[55] = PyString.fromInterned("\\033");
      var0[56] = PyString.fromInterned("\u001c");
      var0[57] = PyString.fromInterned("\\034");
      var0[58] = PyString.fromInterned("\u001d");
      var0[59] = PyString.fromInterned("\\035");
      var0[60] = PyString.fromInterned("\u001e");
      var0[61] = PyString.fromInterned("\\036");
      var0[62] = PyString.fromInterned("\u001f");
      var0[63] = PyString.fromInterned("\\037");
      var0[64] = PyString.fromInterned(",");
      var0[65] = PyString.fromInterned("\\054");
      var0[66] = PyString.fromInterned(";");
      var0[67] = PyString.fromInterned("\\073");
      var0[68] = PyString.fromInterned("\"");
      var0[69] = PyString.fromInterned("\\\"");
      var0[70] = PyString.fromInterned("\\");
      var0[71] = PyString.fromInterned("\\\\");
      var0[72] = PyString.fromInterned("\u007f");
      var0[73] = PyString.fromInterned("\\177");
      var0[74] = PyString.fromInterned("\u0080");
      var0[75] = PyString.fromInterned("\\200");
      var0[76] = PyString.fromInterned("\u0081");
      var0[77] = PyString.fromInterned("\\201");
      var0[78] = PyString.fromInterned("\u0082");
      var0[79] = PyString.fromInterned("\\202");
      var0[80] = PyString.fromInterned("\u0083");
      var0[81] = PyString.fromInterned("\\203");
      var0[82] = PyString.fromInterned("\u0084");
      var0[83] = PyString.fromInterned("\\204");
      var0[84] = PyString.fromInterned("\u0085");
      var0[85] = PyString.fromInterned("\\205");
      var0[86] = PyString.fromInterned("\u0086");
      var0[87] = PyString.fromInterned("\\206");
      var0[88] = PyString.fromInterned("\u0087");
      var0[89] = PyString.fromInterned("\\207");
      var0[90] = PyString.fromInterned("\u0088");
      var0[91] = PyString.fromInterned("\\210");
      var0[92] = PyString.fromInterned("\u0089");
      var0[93] = PyString.fromInterned("\\211");
      var0[94] = PyString.fromInterned("\u008a");
      var0[95] = PyString.fromInterned("\\212");
      var0[96] = PyString.fromInterned("\u008b");
      var0[97] = PyString.fromInterned("\\213");
      var0[98] = PyString.fromInterned("\u008c");
      var0[99] = PyString.fromInterned("\\214");
      var0[100] = PyString.fromInterned("\u008d");
      var0[101] = PyString.fromInterned("\\215");
      var0[102] = PyString.fromInterned("\u008e");
      var0[103] = PyString.fromInterned("\\216");
      var0[104] = PyString.fromInterned("\u008f");
      var0[105] = PyString.fromInterned("\\217");
      var0[106] = PyString.fromInterned("\u0090");
      var0[107] = PyString.fromInterned("\\220");
      var0[108] = PyString.fromInterned("\u0091");
      var0[109] = PyString.fromInterned("\\221");
      var0[110] = PyString.fromInterned("\u0092");
      var0[111] = PyString.fromInterned("\\222");
      var0[112] = PyString.fromInterned("\u0093");
      var0[113] = PyString.fromInterned("\\223");
      var0[114] = PyString.fromInterned("\u0094");
      var0[115] = PyString.fromInterned("\\224");
      var0[116] = PyString.fromInterned("\u0095");
      var0[117] = PyString.fromInterned("\\225");
      var0[118] = PyString.fromInterned("\u0096");
      var0[119] = PyString.fromInterned("\\226");
      var0[120] = PyString.fromInterned("\u0097");
      var0[121] = PyString.fromInterned("\\227");
      var0[122] = PyString.fromInterned("\u0098");
      var0[123] = PyString.fromInterned("\\230");
      var0[124] = PyString.fromInterned("\u0099");
      var0[125] = PyString.fromInterned("\\231");
      var0[126] = PyString.fromInterned("\u009a");
      var0[127] = PyString.fromInterned("\\232");
      var0[128] = PyString.fromInterned("\u009b");
      var0[129] = PyString.fromInterned("\\233");
      var0[130] = PyString.fromInterned("\u009c");
      var0[131] = PyString.fromInterned("\\234");
      var0[132] = PyString.fromInterned("\u009d");
      var0[133] = PyString.fromInterned("\\235");
      var0[134] = PyString.fromInterned("\u009e");
      var0[135] = PyString.fromInterned("\\236");
      var0[136] = PyString.fromInterned("\u009f");
      var0[137] = PyString.fromInterned("\\237");
      var0[138] = PyString.fromInterned(" ");
      var0[139] = PyString.fromInterned("\\240");
      var0[140] = PyString.fromInterned("¡");
      var0[141] = PyString.fromInterned("\\241");
      var0[142] = PyString.fromInterned("¢");
      var0[143] = PyString.fromInterned("\\242");
      var0[144] = PyString.fromInterned("£");
      var0[145] = PyString.fromInterned("\\243");
      var0[146] = PyString.fromInterned("¤");
      var0[147] = PyString.fromInterned("\\244");
      var0[148] = PyString.fromInterned("¥");
      var0[149] = PyString.fromInterned("\\245");
      var0[150] = PyString.fromInterned("¦");
      var0[151] = PyString.fromInterned("\\246");
      var0[152] = PyString.fromInterned("§");
      var0[153] = PyString.fromInterned("\\247");
      var0[154] = PyString.fromInterned("¨");
      var0[155] = PyString.fromInterned("\\250");
      var0[156] = PyString.fromInterned("©");
      var0[157] = PyString.fromInterned("\\251");
      var0[158] = PyString.fromInterned("ª");
      var0[159] = PyString.fromInterned("\\252");
      var0[160] = PyString.fromInterned("«");
      var0[161] = PyString.fromInterned("\\253");
      var0[162] = PyString.fromInterned("¬");
      var0[163] = PyString.fromInterned("\\254");
      var0[164] = PyString.fromInterned("\u00ad");
      var0[165] = PyString.fromInterned("\\255");
      var0[166] = PyString.fromInterned("®");
      var0[167] = PyString.fromInterned("\\256");
      var0[168] = PyString.fromInterned("¯");
      var0[169] = PyString.fromInterned("\\257");
      var0[170] = PyString.fromInterned("°");
      var0[171] = PyString.fromInterned("\\260");
      var0[172] = PyString.fromInterned("±");
      var0[173] = PyString.fromInterned("\\261");
      var0[174] = PyString.fromInterned("²");
      var0[175] = PyString.fromInterned("\\262");
      var0[176] = PyString.fromInterned("³");
      var0[177] = PyString.fromInterned("\\263");
      var0[178] = PyString.fromInterned("´");
      var0[179] = PyString.fromInterned("\\264");
      var0[180] = PyString.fromInterned("µ");
      var0[181] = PyString.fromInterned("\\265");
      var0[182] = PyString.fromInterned("¶");
      var0[183] = PyString.fromInterned("\\266");
      var0[184] = PyString.fromInterned("·");
      var0[185] = PyString.fromInterned("\\267");
      var0[186] = PyString.fromInterned("¸");
      var0[187] = PyString.fromInterned("\\270");
      var0[188] = PyString.fromInterned("¹");
      var0[189] = PyString.fromInterned("\\271");
      var0[190] = PyString.fromInterned("º");
      var0[191] = PyString.fromInterned("\\272");
      var0[192] = PyString.fromInterned("»");
      var0[193] = PyString.fromInterned("\\273");
      var0[194] = PyString.fromInterned("¼");
      var0[195] = PyString.fromInterned("\\274");
      var0[196] = PyString.fromInterned("½");
      var0[197] = PyString.fromInterned("\\275");
      var0[198] = PyString.fromInterned("¾");
      var0[199] = PyString.fromInterned("\\276");
      var0[200] = PyString.fromInterned("¿");
      var0[201] = PyString.fromInterned("\\277");
      var0[202] = PyString.fromInterned("À");
      var0[203] = PyString.fromInterned("\\300");
      var0[204] = PyString.fromInterned("Á");
      var0[205] = PyString.fromInterned("\\301");
      var0[206] = PyString.fromInterned("Â");
      var0[207] = PyString.fromInterned("\\302");
      var0[208] = PyString.fromInterned("Ã");
      var0[209] = PyString.fromInterned("\\303");
      var0[210] = PyString.fromInterned("Ä");
      var0[211] = PyString.fromInterned("\\304");
      var0[212] = PyString.fromInterned("Å");
      var0[213] = PyString.fromInterned("\\305");
      var0[214] = PyString.fromInterned("Æ");
      var0[215] = PyString.fromInterned("\\306");
      var0[216] = PyString.fromInterned("Ç");
      var0[217] = PyString.fromInterned("\\307");
      var0[218] = PyString.fromInterned("È");
      var0[219] = PyString.fromInterned("\\310");
      var0[220] = PyString.fromInterned("É");
      var0[221] = PyString.fromInterned("\\311");
      var0[222] = PyString.fromInterned("Ê");
      var0[223] = PyString.fromInterned("\\312");
      var0[224] = PyString.fromInterned("Ë");
      var0[225] = PyString.fromInterned("\\313");
      var0[226] = PyString.fromInterned("Ì");
      var0[227] = PyString.fromInterned("\\314");
      var0[228] = PyString.fromInterned("Í");
      var0[229] = PyString.fromInterned("\\315");
      var0[230] = PyString.fromInterned("Î");
      var0[231] = PyString.fromInterned("\\316");
      var0[232] = PyString.fromInterned("Ï");
      var0[233] = PyString.fromInterned("\\317");
      var0[234] = PyString.fromInterned("Ð");
      var0[235] = PyString.fromInterned("\\320");
      var0[236] = PyString.fromInterned("Ñ");
      var0[237] = PyString.fromInterned("\\321");
      var0[238] = PyString.fromInterned("Ò");
      var0[239] = PyString.fromInterned("\\322");
      var0[240] = PyString.fromInterned("Ó");
      var0[241] = PyString.fromInterned("\\323");
      var0[242] = PyString.fromInterned("Ô");
      var0[243] = PyString.fromInterned("\\324");
      var0[244] = PyString.fromInterned("Õ");
      var0[245] = PyString.fromInterned("\\325");
      var0[246] = PyString.fromInterned("Ö");
      var0[247] = PyString.fromInterned("\\326");
      var0[248] = PyString.fromInterned("×");
      var0[249] = PyString.fromInterned("\\327");
      var0[250] = PyString.fromInterned("Ø");
      var0[251] = PyString.fromInterned("\\330");
      var0[252] = PyString.fromInterned("Ù");
      var0[253] = PyString.fromInterned("\\331");
      var0[254] = PyString.fromInterned("Ú");
      var0[255] = PyString.fromInterned("\\332");
      var0[256] = PyString.fromInterned("Û");
      var0[257] = PyString.fromInterned("\\333");
      var0[258] = PyString.fromInterned("Ü");
      var0[259] = PyString.fromInterned("\\334");
      var0[260] = PyString.fromInterned("Ý");
      var0[261] = PyString.fromInterned("\\335");
      var0[262] = PyString.fromInterned("Þ");
      var0[263] = PyString.fromInterned("\\336");
      var0[264] = PyString.fromInterned("ß");
      var0[265] = PyString.fromInterned("\\337");
      var0[266] = PyString.fromInterned("à");
      var0[267] = PyString.fromInterned("\\340");
      var0[268] = PyString.fromInterned("á");
      var0[269] = PyString.fromInterned("\\341");
      var0[270] = PyString.fromInterned("â");
      var0[271] = PyString.fromInterned("\\342");
      var0[272] = PyString.fromInterned("ã");
      var0[273] = PyString.fromInterned("\\343");
      var0[274] = PyString.fromInterned("ä");
      var0[275] = PyString.fromInterned("\\344");
      var0[276] = PyString.fromInterned("å");
      var0[277] = PyString.fromInterned("\\345");
      var0[278] = PyString.fromInterned("æ");
      var0[279] = PyString.fromInterned("\\346");
      var0[280] = PyString.fromInterned("ç");
      var0[281] = PyString.fromInterned("\\347");
      var0[282] = PyString.fromInterned("è");
      var0[283] = PyString.fromInterned("\\350");
      var0[284] = PyString.fromInterned("é");
      var0[285] = PyString.fromInterned("\\351");
      var0[286] = PyString.fromInterned("ê");
      var0[287] = PyString.fromInterned("\\352");
      var0[288] = PyString.fromInterned("ë");
      var0[289] = PyString.fromInterned("\\353");
      var0[290] = PyString.fromInterned("ì");
      var0[291] = PyString.fromInterned("\\354");
      var0[292] = PyString.fromInterned("í");
      var0[293] = PyString.fromInterned("\\355");
      var0[294] = PyString.fromInterned("î");
      var0[295] = PyString.fromInterned("\\356");
      var0[296] = PyString.fromInterned("ï");
      var0[297] = PyString.fromInterned("\\357");
      var0[298] = PyString.fromInterned("ð");
      var0[299] = PyString.fromInterned("\\360");
      var0[300] = PyString.fromInterned("ñ");
      var0[301] = PyString.fromInterned("\\361");
      var0[302] = PyString.fromInterned("ò");
      var0[303] = PyString.fromInterned("\\362");
      var0[304] = PyString.fromInterned("ó");
      var0[305] = PyString.fromInterned("\\363");
      var0[306] = PyString.fromInterned("ô");
      var0[307] = PyString.fromInterned("\\364");
      var0[308] = PyString.fromInterned("õ");
      var0[309] = PyString.fromInterned("\\365");
      var0[310] = PyString.fromInterned("ö");
      var0[311] = PyString.fromInterned("\\366");
      var0[312] = PyString.fromInterned("÷");
      var0[313] = PyString.fromInterned("\\367");
      var0[314] = PyString.fromInterned("ø");
      var0[315] = PyString.fromInterned("\\370");
      var0[316] = PyString.fromInterned("ù");
      var0[317] = PyString.fromInterned("\\371");
      var0[318] = PyString.fromInterned("ú");
      var0[319] = PyString.fromInterned("\\372");
      var0[320] = PyString.fromInterned("û");
      var0[321] = PyString.fromInterned("\\373");
      var0[322] = PyString.fromInterned("ü");
      var0[323] = PyString.fromInterned("\\374");
      var0[324] = PyString.fromInterned("ý");
      var0[325] = PyString.fromInterned("\\375");
      var0[326] = PyString.fromInterned("þ");
      var0[327] = PyString.fromInterned("\\376");
      var0[328] = PyString.fromInterned("ÿ");
      var0[329] = PyString.fromInterned("\\377");
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(313);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(313);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(313);
         var1.setline(313);
         var6 = var1.getglobal("chr").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject _quote$3(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyString var3 = PyString.fromInterned("");
      PyObject var10000 = var3._eq(var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1)));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(324);
         var4 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(326);
         var4 = PyString.fromInterned("\"")._add(var1.getglobal("_nulljoin").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("_Translator").__getattr__("get"), var1.getlocal(0), var1.getlocal(0))))._add(PyString.fromInterned("\""));
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _unquote$4(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._lt(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(337);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(338);
         PyObject var4 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var4._ne(PyString.fromInterned("\""));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
            var10000 = var4._ne(PyString.fromInterned("\""));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(339);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(345);
            var4 = var1.getlocal(0).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(0, var4);
            var4 = null;
            var1.setline(351);
            PyInteger var6 = Py.newInteger(0);
            var1.setlocal(1, var6);
            var4 = null;
            var1.setline(352);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(353);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var7);
            var4 = null;

            while(true) {
               var1.setline(354);
               var6 = Py.newInteger(0);
               PyObject var10001 = var1.getlocal(1);
               PyInteger var8 = var6;
               var4 = var10001;
               PyObject var5;
               if ((var5 = var8._le(var10001)).__nonzero__()) {
                  var5 = var4._lt(var1.getlocal(2));
               }

               var4 = null;
               if (!var5.__nonzero__()) {
                  break;
               }

               var1.setline(355);
               var4 = var1.getglobal("_OctalPatt").__getattr__("search").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(356);
               var4 = var1.getglobal("_QuotePatt").__getattr__("search").__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(357);
               var10000 = var1.getlocal(4).__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(5).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(358);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null));
                  break;
               }

               var1.setline(361);
               var6 = Py.newInteger(-1);
               var1.setlocal(6, var6);
               var1.setlocal(7, var6);
               var1.setline(362);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(362);
                  var4 = var1.getlocal(4).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setlocal(6, var4);
                  var4 = null;
               }

               var1.setline(363);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(363);
                  var4 = var1.getlocal(5).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setlocal(7, var4);
                  var4 = null;
               }

               var1.setline(364);
               var10000 = var1.getlocal(5);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4).__not__();
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getlocal(7);
                     var10000 = var4._lt(var1.getlocal(6));
                     var4 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(365);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(7), (PyObject)null));
                  var1.setline(366);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(7)._add(Py.newInteger(1))));
                  var1.setline(367);
                  var4 = var1.getlocal(7)._add(Py.newInteger(2));
                  var1.setlocal(1, var4);
                  var4 = null;
               } else {
                  var1.setline(369);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(1), var1.getlocal(6), (PyObject)null));
                  var1.setline(370);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("chr").__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getslice__(var1.getlocal(6)._add(Py.newInteger(1)), var1.getlocal(6)._add(Py.newInteger(4)), (PyObject)null), (PyObject)Py.newInteger(8))));
                  var1.setline(371);
                  var4 = var1.getlocal(6)._add(Py.newInteger(4));
                  var1.setlocal(1, var4);
                  var4 = null;
               }
            }

            var1.setline(372);
            var3 = var1.getglobal("_nulljoin").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _getdate$5(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      String[] var3 = new String[]{"gmtime", "time"};
      PyObject[] var6 = imp.importFrom("time", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(3, var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(391);
      PyObject var7 = var1.getlocal(4).__call__(var2);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(392);
      var7 = var1.getlocal(3).__call__(var2, var1.getlocal(5)._add(var1.getlocal(0)));
      PyObject[] var8 = Py.unpackSequence(var7, 9);
      PyObject var5 = var8[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var8[2];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var8[3];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var8[4];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var8[5];
      var1.setlocal(11, var5);
      var5 = null;
      var5 = var8[6];
      var1.setlocal(12, var5);
      var5 = null;
      var5 = var8[7];
      var1.setlocal(13, var5);
      var5 = null;
      var5 = var8[8];
      var1.setlocal(14, var5);
      var5 = null;
      var3 = null;
      var1.setline(393);
      var7 = PyString.fromInterned("%s, %02d %3s %4d %02d:%02d:%02d GMT")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getitem__(var1.getlocal(12)), var1.getlocal(8), var1.getlocal(2).__getitem__(var1.getlocal(7)), var1.getlocal(6), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)}));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject Morsel$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(422);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("expires"), PyString.fromInterned("expires"), PyString.fromInterned("path"), PyString.fromInterned("Path"), PyString.fromInterned("comment"), PyString.fromInterned("Comment"), PyString.fromInterned("domain"), PyString.fromInterned("Domain"), PyString.fromInterned("max-age"), PyString.fromInterned("Max-Age"), PyString.fromInterned("secure"), PyString.fromInterned("secure"), PyString.fromInterned("httponly"), PyString.fromInterned("httponly"), PyString.fromInterned("version"), PyString.fromInterned("Version")});
      var1.setlocal("_reserved", var3);
      var3 = null;
      var1.setline(432);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(441);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setitem__$8, (PyObject)null);
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(448);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isReservedKey$9, (PyObject)null);
      var1.setlocal("isReservedKey", var5);
      var3 = null;
      var1.setline(452);
      var4 = new PyObject[]{var1.getname("_LegalChars"), var1.getname("_idmap"), var1.getname("string").__getattr__("translate")};
      var5 = new PyFunction(var1.f_globals, var4, set$10, (PyObject)null);
      var1.setlocal("set", var5);
      var3 = null;
      var1.setline(468);
      var4 = new PyObject[]{var1.getname("None"), PyString.fromInterned("Set-Cookie:")};
      var5 = new PyFunction(var1.f_globals, var4, output$11, (PyObject)null);
      var1.setlocal("output", var5);
      var3 = null;
      var1.setline(471);
      PyObject var6 = var1.getname("output");
      var1.setlocal("__str__", var6);
      var3 = null;
      var1.setline(473);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$12, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(477);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, js_output$13, (PyObject)null);
      var1.setlocal("js_output", var5);
      var3 = null;
      var1.setline(488);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, OutputString$14, (PyObject)null);
      var1.setlocal("OutputString", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(434);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("key", var3);
      var1.getlocal(0).__setattr__("value", var3);
      var1.getlocal(0).__setattr__("coded_value", var3);
      var1.setline(437);
      var3 = var1.getlocal(0).__getattr__("_reserved").__iter__();

      while(true) {
         var1.setline(437);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(438);
         var1.getglobal("dict").__getattr__("__setitem__").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned(""));
      }
   }

   public PyObject __setitem__$8(PyFrame var1, ThreadState var2) {
      var1.setline(442);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(443);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_reserved"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(444);
         throw Py.makeException(var1.getglobal("CookieError").__call__(var2, PyString.fromInterned("Invalid Attribute %s")._mod(var1.getlocal(1))));
      } else {
         var1.setline(445);
         var1.getglobal("dict").__getattr__("__setitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject isReservedKey$9(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_reserved"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set$10(PyFrame var1, ThreadState var2) {
      var1.setline(457);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_reserved"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(458);
         throw Py.makeException(var1.getglobal("CookieError").__call__(var2, PyString.fromInterned("Attempt to set a reserved key: %s")._mod(var1.getlocal(1))));
      } else {
         var1.setline(459);
         PyString var4 = PyString.fromInterned("");
         var10000 = var4._ne(var1.getlocal(6).__call__(var2, var1.getlocal(1), var1.getlocal(5), var1.getlocal(4)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(460);
            throw Py.makeException(var1.getglobal("CookieError").__call__(var2, PyString.fromInterned("Illegal key value: %s")._mod(var1.getlocal(1))));
         } else {
            var1.setline(463);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("key", var3);
            var3 = null;
            var1.setline(464);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("value", var3);
            var3 = null;
            var1.setline(465);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("coded_value", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject output$11(PyFrame var1, ThreadState var2) {
      var1.setline(469);
      PyObject var3 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("OutputString").__call__(var2, var1.getlocal(1))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$12(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      PyObject var3 = PyString.fromInterned("<%s: %s=%s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(0).__getattr__("key"), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("value"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject js_output$13(PyFrame var1, ThreadState var2) {
      var1.setline(479);
      PyObject var3 = PyString.fromInterned("\n        <script type=\"text/javascript\">\n        <!-- begin hiding\n        document.cookie = \"%s\";\n        // end hiding -->\n        </script>\n        ")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("OutputString").__call__(var2, var1.getlocal(1)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("\\\""))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OutputString$14(PyFrame var1, ThreadState var2) {
      var1.setline(491);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(492);
      PyObject var7 = var1.getlocal(2).__getattr__("append");
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(495);
      var1.getlocal(3).__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("key"), var1.getlocal(0).__getattr__("coded_value")})));
      var1.setline(498);
      var7 = var1.getlocal(1);
      PyObject var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(499);
         var7 = var1.getlocal(0).__getattr__("_reserved");
         var1.setlocal(1, var7);
         var3 = null;
      }

      var1.setline(500);
      var7 = var1.getlocal(0).__getattr__("items").__call__(var2);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(501);
      var1.getlocal(4).__getattr__("sort").__call__(var2);
      var1.setline(502);
      var7 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(502);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(517);
            var7 = var1.getglobal("_semispacejoin").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(503);
         PyObject var8 = var1.getlocal(6);
         var10000 = var8._eq(PyString.fromInterned(""));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(504);
            var8 = var1.getlocal(5);
            var10000 = var8._notin(var1.getlocal(1));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(505);
               var8 = var1.getlocal(5);
               var10000 = var8._eq(PyString.fromInterned("expires"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var8 = var1.getglobal("type").__call__(var2, var1.getlocal(6));
                  var10000 = var8._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(506);
                  var1.getlocal(3).__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_reserved").__getitem__(var1.getlocal(5)), var1.getglobal("_getdate").__call__(var2, var1.getlocal(6))})));
               } else {
                  var1.setline(507);
                  var8 = var1.getlocal(5);
                  var10000 = var8._eq(PyString.fromInterned("max-age"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getglobal("type").__call__(var2, var1.getlocal(6));
                     var10000 = var8._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                     var5 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(508);
                     var1.getlocal(3).__call__(var2, PyString.fromInterned("%s=%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_reserved").__getitem__(var1.getlocal(5)), var1.getlocal(6)})));
                  } else {
                     var1.setline(509);
                     var8 = var1.getlocal(5);
                     var10000 = var8._eq(PyString.fromInterned("secure"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(510);
                        var1.getlocal(3).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("_reserved").__getitem__(var1.getlocal(5))));
                     } else {
                        var1.setline(511);
                        var8 = var1.getlocal(5);
                        var10000 = var8._eq(PyString.fromInterned("httponly"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(512);
                           var1.getlocal(3).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("_reserved").__getitem__(var1.getlocal(5))));
                        } else {
                           var1.setline(514);
                           var1.getlocal(3).__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_reserved").__getitem__(var1.getlocal(5)), var1.getlocal(6)})));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject BaseCookie$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(558);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, value_decode$16, PyString.fromInterned("real_value, coded_value = value_decode(STRING)\n        Called prior to setting a cookie's value from the network\n        representation.  The VALUE is the value read from HTTP\n        header.\n        Override this function to modify the behavior of cookies.\n        "));
      var1.setlocal("value_decode", var4);
      var3 = null;
      var1.setline(568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, value_encode$17, PyString.fromInterned("real_value, coded_value = value_encode(VALUE)\n        Called prior to setting a cookie's value from the dictionary\n        representation.  The VALUE is the value being assigned.\n        Override this function to modify the behavior of cookies.\n        "));
      var1.setlocal("value_encode", var4);
      var3 = null;
      var1.setline(578);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(582);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _BaseCookie__set$19, PyString.fromInterned("Private method for setting a cookie's value"));
      var1.setlocal("_BaseCookie__set", var4);
      var3 = null;
      var1.setline(589);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$20, PyString.fromInterned("Dictionary style assignment."));
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(595);
      var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned("Set-Cookie:"), PyString.fromInterned("\r\n")};
      var4 = new PyFunction(var1.f_globals, var3, output$21, PyString.fromInterned("Return a string suitable for HTTP."));
      var1.setlocal("output", var4);
      var3 = null;
      var1.setline(605);
      PyObject var5 = var1.getname("output");
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(607);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$22, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(615);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, js_output$23, PyString.fromInterned("Return a string suitable for JavaScript."));
      var1.setlocal("js_output", var4);
      var3 = null;
      var1.setline(625);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load$24, PyString.fromInterned("Load cookies from a string (presumably HTTP_COOKIE) or\n        from a dictionary.  Loading cookies from a dictionary 'd'\n        is equivalent to calling:\n            map(Cookie.__setitem__, d.keys(), d.values())\n        "));
      var1.setlocal("load", var4);
      var3 = null;
      var1.setline(640);
      var3 = new PyObject[]{var1.getname("_CookiePattern")};
      var4 = new PyFunction(var1.f_globals, var3, _BaseCookie__ParseString$25, (PyObject)null);
      var1.setlocal("_BaseCookie__ParseString", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject value_decode$16(PyFrame var1, ThreadState var2) {
      var1.setline(564);
      PyString.fromInterned("real_value, coded_value = value_decode(STRING)\n        Called prior to setting a cookie's value from the network\n        representation.  The VALUE is the value read from HTTP\n        header.\n        Override this function to modify the behavior of cookies.\n        ");
      var1.setline(565);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject value_encode$17(PyFrame var1, ThreadState var2) {
      var1.setline(573);
      PyString.fromInterned("real_value, coded_value = value_encode(VALUE)\n        Called prior to setting a cookie's value from the dictionary\n        representation.  The VALUE is the value being assigned.\n        Override this function to modify the behavior of cookies.\n        ");
      var1.setline(574);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(575);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(579);
         var1.getlocal(0).__getattr__("load").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _BaseCookie__set$19(PyFrame var1, ThreadState var2) {
      var1.setline(583);
      PyString.fromInterned("Private method for setting a cookie's value");
      var1.setline(584);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1), var1.getglobal("Morsel").__call__(var2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(585);
      var1.getlocal(4).__getattr__("set").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(586);
      var1.getglobal("dict").__getattr__("__setitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$20(PyFrame var1, ThreadState var2) {
      var1.setline(590);
      PyString.fromInterned("Dictionary style assignment.");
      var1.setline(591);
      PyObject var3 = var1.getlocal(0).__getattr__("value_encode").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(592);
      var1.getlocal(0).__getattr__("_BaseCookie__set").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject output$21(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      PyString.fromInterned("Return a string suitable for HTTP.");
      var1.setline(597);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(598);
      PyObject var7 = var1.getlocal(0).__getattr__("items").__call__(var2);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(599);
      var1.getlocal(5).__getattr__("sort").__call__(var2);
      var1.setline(600);
      var7 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(600);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(602);
            var7 = var1.getlocal(3).__getattr__("join").__call__(var2, var1.getlocal(4));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(601);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7).__getattr__("output").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      }
   }

   public PyObject __repr__$22(PyFrame var1, ThreadState var2) {
      var1.setline(608);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(609);
      PyObject var7 = var1.getlocal(0).__getattr__("items").__call__(var2);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(610);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(611);
      var7 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(611);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(613);
            var7 = PyString.fromInterned("<%s: %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("_spacejoin").__call__(var2, var1.getlocal(1))}));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(612);
         var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("repr").__call__(var2, var1.getlocal(4).__getattr__("value"))})));
      }
   }

   public PyObject js_output$23(PyFrame var1, ThreadState var2) {
      var1.setline(616);
      PyString.fromInterned("Return a string suitable for JavaScript.");
      var1.setline(617);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(618);
      PyObject var7 = var1.getlocal(0).__getattr__("items").__call__(var2);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(619);
      var1.getlocal(3).__getattr__("sort").__call__(var2);
      var1.setline(620);
      var7 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(620);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(622);
            var7 = var1.getglobal("_nulljoin").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(621);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5).__getattr__("js_output").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject load$24(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyString.fromInterned("Load cookies from a string (presumably HTTP_COOKIE) or\n        from a dictionary.  Loading cookies from a dictionary 'd'\n        is equivalent to calling:\n            map(Cookie.__setitem__, d.keys(), d.values())\n        ");
      var1.setline(631);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(632);
         var1.getlocal(0).__getattr__("_BaseCookie__ParseString").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(635);
         var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(635);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(636);
            PyObject var7 = var1.getlocal(3);
            var1.getlocal(0).__setitem__(var1.getlocal(2), var7);
            var5 = null;
         }
      }

      var1.setline(637);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _BaseCookie__ParseString$25(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(642);
      PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(643);
      var6 = var1.getglobal("None");
      var1.setlocal(5, var6);
      var3 = null;

      while(true) {
         var1.setline(645);
         var3 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(3);
         PyInteger var10000 = var3;
         var6 = var10001;
         PyObject var4;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var6._lt(var1.getlocal(4));
         }

         var3 = null;
         if (!var4.__nonzero__()) {
            break;
         }

         var1.setline(647);
         var6 = var1.getlocal(2).__getattr__("search").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(648);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            break;
         }

         var1.setline(650);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("key")), var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("val"))});
         PyObject[] var7 = Py.unpackSequence(var8, 2);
         PyObject var5 = var7[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
         var1.setline(651);
         var6 = var1.getlocal(6).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(654);
         var6 = var1.getlocal(7).__getitem__(Py.newInteger(0));
         PyObject var9 = var6._eq(PyString.fromInterned("$"));
         var3 = null;
         if (var9.__nonzero__()) {
            var1.setline(658);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(659);
               var6 = var1.getlocal(8);
               var1.getlocal(5).__setitem__(var1.getlocal(7).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var6);
               var3 = null;
            }
         } else {
            var1.setline(660);
            var6 = var1.getlocal(7).__getattr__("lower").__call__(var2);
            var9 = var6._in(var1.getglobal("Morsel").__getattr__("_reserved"));
            var3 = null;
            if (var9.__nonzero__()) {
               var1.setline(661);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(662);
                  var6 = var1.getglobal("_unquote").__call__(var2, var1.getlocal(8));
                  var1.getlocal(5).__setitem__(var1.getlocal(7), var6);
                  var3 = null;
               }
            } else {
               var1.setline(664);
               var6 = var1.getlocal(0).__getattr__("value_decode").__call__(var2, var1.getlocal(8));
               var7 = Py.unpackSequence(var6, 2);
               var5 = var7[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var7[1];
               var1.setlocal(10, var5);
               var5 = null;
               var3 = null;
               var1.setline(665);
               var1.getlocal(0).__getattr__("_BaseCookie__set").__call__(var2, var1.getlocal(7), var1.getlocal(9), var1.getlocal(10));
               var1.setline(666);
               var6 = var1.getlocal(0).__getitem__(var1.getlocal(7));
               var1.setlocal(5, var6);
               var3 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SimpleCookie$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("SimpleCookie\n    SimpleCookie supports strings as cookie values.  When setting\n    the value using the dictionary assignment notation, SimpleCookie\n    calls the builtin str() to convert the value to a string.  Values\n    received from HTTP are kept as strings.\n    "));
      var1.setline(676);
      PyString.fromInterned("SimpleCookie\n    SimpleCookie supports strings as cookie values.  When setting\n    the value using the dictionary assignment notation, SimpleCookie\n    calls the builtin str() to convert the value to a string.  Values\n    received from HTTP are kept as strings.\n    ");
      var1.setline(677);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, value_decode$27, (PyObject)null);
      var1.setlocal("value_decode", var4);
      var3 = null;
      var1.setline(679);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, value_encode$28, (PyObject)null);
      var1.setlocal("value_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject value_decode$27(PyFrame var1, ThreadState var2) {
      var1.setline(678);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("_unquote").__call__(var2, var1.getlocal(1)), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject value_encode$28(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(681);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("_quote").__call__(var2, var1.getlocal(2))});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject SerialCookie$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("SerialCookie\n    SerialCookie supports arbitrary objects as cookie values. All\n    values are serialized (using cPickle) before being sent to the\n    client.  All incoming values are assumed to be valid Pickle\n    representations.  IF AN INCOMING VALUE IS NOT IN A VALID PICKLE\n    FORMAT, THEN AN EXCEPTION WILL BE RAISED.\n\n    Note: Large cookie values add overhead because they must be\n    retransmitted on every HTTP transaction.\n\n    Note: HTTP has a 2k limit on the size of a cookie.  This class\n    does not check for this limit, so be careful!!!\n    "));
      var1.setline(697);
      PyString.fromInterned("SerialCookie\n    SerialCookie supports arbitrary objects as cookie values. All\n    values are serialized (using cPickle) before being sent to the\n    client.  All incoming values are assumed to be valid Pickle\n    representations.  IF AN INCOMING VALUE IS NOT IN A VALID PICKLE\n    FORMAT, THEN AN EXCEPTION WILL BE RAISED.\n\n    Note: Large cookie values add overhead because they must be\n    retransmitted on every HTTP transaction.\n\n    Note: HTTP has a 2k limit on the size of a cookie.  This class\n    does not check for this limit, so be careful!!!\n    ");
      var1.setline(698);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$30, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(703);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, value_decode$31, (PyObject)null);
      var1.setlocal("value_decode", var4);
      var3 = null;
      var1.setline(706);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, value_encode$32, (PyObject)null);
      var1.setlocal("value_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$30(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SerialCookie class is insecure; do not use it"), (PyObject)var1.getglobal("DeprecationWarning"));
      var1.setline(701);
      var1.getglobal("BaseCookie").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject value_decode$31(PyFrame var1, ThreadState var2) {
      var1.setline(705);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("loads").__call__(var2, var1.getglobal("_unquote").__call__(var2, var1.getlocal(1))), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject value_encode$32(PyFrame var1, ThreadState var2) {
      var1.setline(707);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("_quote").__call__(var2, var1.getglobal("dumps").__call__(var2, var1.getlocal(1)))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject SmartCookie$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("SmartCookie\n    SmartCookie supports arbitrary objects as cookie values.  If the\n    object is a string, then it is quoted.  If the object is not a\n    string, however, then SmartCookie will use cPickle to serialize\n    the object into a string representation.\n\n    Note: Large cookie values add overhead because they must be\n    retransmitted on every HTTP transaction.\n\n    Note: HTTP has a 2k limit on the size of a cookie.  This class\n    does not check for this limit, so be careful!!!\n    "));
      var1.setline(722);
      PyString.fromInterned("SmartCookie\n    SmartCookie supports arbitrary objects as cookie values.  If the\n    object is a string, then it is quoted.  If the object is not a\n    string, however, then SmartCookie will use cPickle to serialize\n    the object into a string representation.\n\n    Note: Large cookie values add overhead because they must be\n    retransmitted on every HTTP transaction.\n\n    Note: HTTP has a 2k limit on the size of a cookie.  This class\n    does not check for this limit, so be careful!!!\n    ");
      var1.setline(723);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$34, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(728);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, value_decode$35, (PyObject)null);
      var1.setlocal("value_decode", var4);
      var3 = null;
      var1.setline(734);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, value_encode$36, (PyObject)null);
      var1.setlocal("value_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$34(PyFrame var1, ThreadState var2) {
      var1.setline(724);
      var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cookie/SmartCookie class is insecure; do not use it"), (PyObject)var1.getglobal("DeprecationWarning"));
      var1.setline(726);
      var1.getglobal("BaseCookie").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject value_decode$35(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      PyObject var3 = var1.getglobal("_unquote").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;

      PyTuple var6;
      try {
         var1.setline(731);
         var6 = new PyTuple(new PyObject[]{var1.getglobal("loads").__call__(var2, var1.getlocal(2)), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var6;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(733);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject value_encode$36(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      PyTuple var4;
      if (var10000.__nonzero__()) {
         var1.setline(736);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("_quote").__call__(var2, var1.getlocal(1))});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(738);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("_quote").__call__(var2, var1.getglobal("dumps").__call__(var2, var1.getlocal(1)))});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _test$37(PyFrame var1, ThreadState var2) {
      var1.setline(752);
      PyObject var3 = imp.importOne("doctest", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var3 = imp.importOne("Cookie", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(753);
      var3 = var1.getlocal(0).__getattr__("testmod").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public Cookie$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CookieError$1 = Py.newCode(0, var2, var1, "CookieError", 232, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"_(x)", "x"};
      f$2 = Py.newCode(1, var2, var1, "<genexpr>", 313, false, false, self, 2, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"str", "LegalChars", "idmap", "translate"};
      _quote$3 = Py.newCode(4, var2, var1, "_quote", 315, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "i", "n", "res", "Omatch", "Qmatch", "j", "k"};
      _unquote$4 = Py.newCode(1, var2, var1, "_unquote", 333, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"future", "weekdayname", "monthname", "gmtime", "time", "now", "year", "month", "day", "hh", "mm", "ss", "wd", "y", "z"};
      _getdate$5 = Py.newCode(3, var2, var1, "_getdate", 389, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Morsel$6 = Py.newCode(0, var2, var1, "Morsel", 408, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "K"};
      __init__$7 = Py.newCode(1, var2, var1, "__init__", 432, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "K", "V"};
      __setitem__$8 = Py.newCode(3, var2, var1, "__setitem__", 441, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "K"};
      isReservedKey$9 = Py.newCode(2, var2, var1, "isReservedKey", 448, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "val", "coded_val", "LegalChars", "idmap", "translate"};
      set$10 = Py.newCode(7, var2, var1, "set", 452, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "header"};
      output$11 = Py.newCode(3, var2, var1, "output", 468, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$12 = Py.newCode(1, var2, var1, "__repr__", 473, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      js_output$13 = Py.newCode(2, var2, var1, "js_output", 477, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "result", "RA", "items", "K", "V"};
      OutputString$14 = Py.newCode(2, var2, var1, "OutputString", 488, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseCookie$15 = Py.newCode(0, var2, var1, "BaseCookie", 554, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "val"};
      value_decode$16 = Py.newCode(2, var2, var1, "value_decode", 558, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val", "strval"};
      value_encode$17 = Py.newCode(2, var2, var1, "value_encode", 568, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input"};
      __init__$18 = Py.newCode(2, var2, var1, "__init__", 578, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "real_value", "coded_value", "M"};
      _BaseCookie__set$19 = Py.newCode(4, var2, var1, "_BaseCookie__set", 582, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "rval", "cval"};
      __setitem__$20 = Py.newCode(3, var2, var1, "__setitem__", 589, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "header", "sep", "result", "items", "K", "V"};
      output$21 = Py.newCode(4, var2, var1, "output", 595, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "L", "items", "K", "V"};
      __repr__$22 = Py.newCode(1, var2, var1, "__repr__", 607, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "result", "items", "K", "V"};
      js_output$23 = Py.newCode(2, var2, var1, "js_output", 615, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rawdata", "k", "v"};
      load$24 = Py.newCode(2, var2, var1, "load", 625, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "str", "patt", "i", "n", "M", "match", "K", "V", "rval", "cval"};
      _BaseCookie__ParseString$25 = Py.newCode(3, var2, var1, "_BaseCookie__ParseString", 640, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SimpleCookie$26 = Py.newCode(0, var2, var1, "SimpleCookie", 670, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "val"};
      value_decode$27 = Py.newCode(2, var2, var1, "value_decode", 677, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val", "strval"};
      value_encode$28 = Py.newCode(2, var2, var1, "value_encode", 679, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SerialCookie$29 = Py.newCode(0, var2, var1, "SerialCookie", 684, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input"};
      __init__$30 = Py.newCode(2, var2, var1, "__init__", 698, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val"};
      value_decode$31 = Py.newCode(2, var2, var1, "value_decode", 703, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val"};
      value_encode$32 = Py.newCode(2, var2, var1, "value_encode", 706, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SmartCookie$33 = Py.newCode(0, var2, var1, "SmartCookie", 710, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input"};
      __init__$34 = Py.newCode(2, var2, var1, "__init__", 723, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val", "strval"};
      value_decode$35 = Py.newCode(2, var2, var1, "value_decode", 728, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val"};
      value_encode$36 = Py.newCode(2, var2, var1, "value_encode", 734, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"doctest", "Cookie"};
      _test$37 = Py.newCode(0, var2, var1, "_test", 751, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new Cookie$py("Cookie$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(Cookie$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CookieError$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this._quote$3(var2, var3);
         case 4:
            return this._unquote$4(var2, var3);
         case 5:
            return this._getdate$5(var2, var3);
         case 6:
            return this.Morsel$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.__setitem__$8(var2, var3);
         case 9:
            return this.isReservedKey$9(var2, var3);
         case 10:
            return this.set$10(var2, var3);
         case 11:
            return this.output$11(var2, var3);
         case 12:
            return this.__repr__$12(var2, var3);
         case 13:
            return this.js_output$13(var2, var3);
         case 14:
            return this.OutputString$14(var2, var3);
         case 15:
            return this.BaseCookie$15(var2, var3);
         case 16:
            return this.value_decode$16(var2, var3);
         case 17:
            return this.value_encode$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this._BaseCookie__set$19(var2, var3);
         case 20:
            return this.__setitem__$20(var2, var3);
         case 21:
            return this.output$21(var2, var3);
         case 22:
            return this.__repr__$22(var2, var3);
         case 23:
            return this.js_output$23(var2, var3);
         case 24:
            return this.load$24(var2, var3);
         case 25:
            return this._BaseCookie__ParseString$25(var2, var3);
         case 26:
            return this.SimpleCookie$26(var2, var3);
         case 27:
            return this.value_decode$27(var2, var3);
         case 28:
            return this.value_encode$28(var2, var3);
         case 29:
            return this.SerialCookie$29(var2, var3);
         case 30:
            return this.__init__$30(var2, var3);
         case 31:
            return this.value_decode$31(var2, var3);
         case 32:
            return this.value_encode$32(var2, var3);
         case 33:
            return this.SmartCookie$33(var2, var3);
         case 34:
            return this.__init__$34(var2, var3);
         case 35:
            return this.value_decode$35(var2, var3);
         case 36:
            return this.value_encode$36(var2, var3);
         case 37:
            return this._test$37(var2, var3);
         default:
            return null;
      }
   }
}
