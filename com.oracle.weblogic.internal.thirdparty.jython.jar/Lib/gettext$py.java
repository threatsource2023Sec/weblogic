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
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("gettext.py")
public class gettext$py extends PyFunctionTable implements PyRunnable {
   static gettext$py self;
   static final PyCode f$0;
   static final PyCode test$1;
   static final PyCode c2py$2;
   static final PyCode repl$3;
   static final PyCode _expand_lang$4;
   static final PyCode NullTranslations$5;
   static final PyCode __init__$6;
   static final PyCode _parse$7;
   static final PyCode add_fallback$8;
   static final PyCode gettext$9;
   static final PyCode lgettext$10;
   static final PyCode ngettext$11;
   static final PyCode lngettext$12;
   static final PyCode ugettext$13;
   static final PyCode ungettext$14;
   static final PyCode info$15;
   static final PyCode charset$16;
   static final PyCode output_charset$17;
   static final PyCode set_output_charset$18;
   static final PyCode install$19;
   static final PyCode GNUTranslations$20;
   static final PyCode _parse$21;
   static final PyCode f$22;
   static final PyCode gettext$23;
   static final PyCode lgettext$24;
   static final PyCode ngettext$25;
   static final PyCode lngettext$26;
   static final PyCode ugettext$27;
   static final PyCode ungettext$28;
   static final PyCode find$29;
   static final PyCode translation$30;
   static final PyCode install$31;
   static final PyCode textdomain$32;
   static final PyCode bindtextdomain$33;
   static final PyCode bind_textdomain_codeset$34;
   static final PyCode dgettext$35;
   static final PyCode ldgettext$36;
   static final PyCode dngettext$37;
   static final PyCode ldngettext$38;
   static final PyCode gettext$39;
   static final PyCode lgettext$40;
   static final PyCode ngettext$41;
   static final PyCode lngettext$42;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Internationalization and localization support.\n\nThis module provides internationalization (I18N) and localization (L10N)\nsupport for your Python programs by providing an interface to the GNU gettext\nmessage catalog library.\n\nI18N refers to the operation by which a program is made aware of multiple\nlanguages.  L10N refers to the adaptation of your program, once\ninternationalized, to the local language and cultural habits.\n\n"));
      var1.setline(11);
      PyString.fromInterned("Internationalization and localization support.\n\nThis module provides internationalization (I18N) and localization (L10N)\nsupport for your Python programs by providing an interface to the GNU gettext\nmessage catalog library.\n\nI18N refers to the operation by which a program is made aware of multiple\nlanguages.  L10N refers to the adaptation of your program, once\ninternationalized, to the local language and cultural habits.\n\n");
      var1.setline(49);
      PyObject var3 = imp.importOne("locale", var1, -1);
      var1.setlocal("locale", var3);
      var3 = null;
      var3 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(50);
      String[] var5 = new String[]{"ENOENT"};
      PyObject[] var6 = imp.importFrom("errno", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("ENOENT", var4);
      var4 = null;
      var1.setline(53);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("NullTranslations"), PyString.fromInterned("GNUTranslations"), PyString.fromInterned("Catalog"), PyString.fromInterned("find"), PyString.fromInterned("translation"), PyString.fromInterned("install"), PyString.fromInterned("textdomain"), PyString.fromInterned("bindtextdomain"), PyString.fromInterned("dgettext"), PyString.fromInterned("dngettext"), PyString.fromInterned("gettext"), PyString.fromInterned("ngettext")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(58);
      var3 = var1.getname("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getname("sys").__getattr__("prefix"), (PyObject)PyString.fromInterned("share"), (PyObject)PyString.fromInterned("locale"));
      var1.setlocal("_default_localedir", var3);
      var3 = null;
      var1.setline(61);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, test$1, PyString.fromInterned("\n    Implements the C expression:\n\n      condition ? true : false\n\n    Required to correctly interpret plural forms.\n    "));
      var1.setlocal("test", var8);
      var3 = null;
      var1.setline(75);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, c2py$2, PyString.fromInterned("Gets a C expression as used in PO files for plural forms and returns a\n    Python lambda function that implements an equivalent expression.\n    "));
      var1.setlocal("c2py", var8);
      var3 = null;
      var1.setline(130);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _expand_lang$4, (PyObject)null);
      var1.setlocal("_expand_lang", var8);
      var3 = null;
      var1.setline(173);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("NullTranslations", var6, NullTranslations$5);
      var1.setlocal("NullTranslations", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(257);
      var6 = new PyObject[]{var1.getname("NullTranslations")};
      var4 = Py.makeClass("GNUTranslations", var6, GNUTranslations$20);
      var1.setlocal("GNUTranslations", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(421);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var6, find$29, (PyObject)null);
      var1.setlocal("find", var8);
      var3 = null;
      var1.setline(459);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_translations", var9);
      var3 = null;
      var1.setline(461);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, translation$30, (PyObject)null);
      var1.setlocal("translation", var8);
      var3 = null;
      var1.setline(492);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, install$31, (PyObject)null);
      var1.setlocal("install", var8);
      var3 = null;
      var1.setline(499);
      var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_localedirs", var9);
      var3 = null;
      var1.setline(501);
      var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_localecodesets", var9);
      var3 = null;
      var1.setline(503);
      PyString var10 = PyString.fromInterned("messages");
      var1.setlocal("_current_domain", var10);
      var3 = null;
      var1.setline(506);
      var6 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, textdomain$32, (PyObject)null);
      var1.setlocal("textdomain", var8);
      var3 = null;
      var1.setline(513);
      var6 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, bindtextdomain$33, (PyObject)null);
      var1.setlocal("bindtextdomain", var8);
      var3 = null;
      var1.setline(520);
      var6 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, bind_textdomain_codeset$34, (PyObject)null);
      var1.setlocal("bind_textdomain_codeset", var8);
      var3 = null;
      var1.setline(527);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, dgettext$35, (PyObject)null);
      var1.setlocal("dgettext", var8);
      var3 = null;
      var1.setline(535);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, ldgettext$36, (PyObject)null);
      var1.setlocal("ldgettext", var8);
      var3 = null;
      var1.setline(543);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, dngettext$37, (PyObject)null);
      var1.setlocal("dngettext", var8);
      var3 = null;
      var1.setline(554);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, ldngettext$38, (PyObject)null);
      var1.setlocal("ldngettext", var8);
      var3 = null;
      var1.setline(565);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, gettext$39, (PyObject)null);
      var1.setlocal("gettext", var8);
      var3 = null;
      var1.setline(568);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, lgettext$40, (PyObject)null);
      var1.setlocal("lgettext", var8);
      var3 = null;
      var1.setline(571);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, ngettext$41, (PyObject)null);
      var1.setlocal("ngettext", var8);
      var3 = null;
      var1.setline(574);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, lngettext$42, (PyObject)null);
      var1.setlocal("lngettext", var8);
      var3 = null;
      var1.setline(591);
      var3 = var1.getname("translation");
      var1.setlocal("Catalog", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$1(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("\n    Implements the C expression:\n\n      condition ? true : false\n\n    Required to correctly interpret plural forms.\n    ");
      var1.setline(69);
      PyObject var3;
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(70);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(72);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject c2py$2(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyString.fromInterned("Gets a C expression as used in PO files for plural forms and returns a\n    Python lambda function that implements an equivalent expression.\n    ");

      PyException var3;
      String[] var4;
      PyObject var5;
      PyObject[] var11;
      PyObject var14;
      try {
         var1.setline(81);
         String[] var10 = new String[]{"StringIO"};
         var11 = imp.importFrom("cStringIO", var10, var1, -1);
         var14 = var11[0];
         var1.setlocal(1, var14);
         var4 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(83);
         var4 = new String[]{"StringIO"};
         PyObject[] var13 = imp.importFrom("StringIO", var4, var1, -1);
         var5 = var13[0];
         var1.setlocal(1, var5);
         var5 = null;
      }

      var1.setline(84);
      PyObject var12 = imp.importOne("token", var1, -1);
      var1.setlocal(2, var12);
      var3 = null;
      var12 = imp.importOne("tokenize", var1, -1);
      var1.setlocal(3, var12);
      var3 = null;
      var1.setline(85);
      var12 = var1.getlocal(3).__getattr__("generate_tokens").__call__(var2, var1.getlocal(1).__call__(var2, var1.getlocal(0)).__getattr__("readline"));
      var1.setlocal(4, var12);
      var3 = null;

      PyList var15;
      try {
         var1.setline(87);
         PyList var10000 = new PyList();
         var12 = var10000.__getattr__("append");
         var1.setlocal(6, var12);
         var3 = null;
         var1.setline(87);
         var12 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(87);
            var14 = var12.__iternext__();
            if (var14 == null) {
               var1.setline(87);
               var1.dellocal(6);
               var15 = var10000;
               var1.setlocal(5, var15);
               var3 = null;
               break;
            }

            var1.setlocal(7, var14);
            var1.setline(87);
            var5 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            PyObject var10001 = var5._eq(var1.getlocal(2).__getattr__("NAME"));
            var5 = null;
            if (var10001.__nonzero__()) {
               var5 = var1.getlocal(7).__getitem__(Py.newInteger(1));
               var10001 = var5._ne(PyString.fromInterned("n"));
               var5 = null;
            }

            if (var10001.__nonzero__()) {
               var1.setline(87);
               var1.getlocal(6).__call__(var2, var1.getlocal(7));
            }
         }
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (var3.match(var1.getlocal(3).__getattr__("TokenError"))) {
            var1.setline(89);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("plural forms expression error, maybe unbalanced parenthesis"));
         }

         throw var3;
      }

      var1.setline(92);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(93);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("plural forms expression could be dangerous"));
      } else {
         var1.setline(96);
         var12 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&&"), (PyObject)PyString.fromInterned(" and "));
         var1.setlocal(0, var12);
         var3 = null;
         var1.setline(97);
         var12 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("||"), (PyObject)PyString.fromInterned(" or "));
         var1.setlocal(0, var12);
         var3 = null;
         var1.setline(99);
         var12 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\!([^=])"));
         var1.setderef(1, var12);
         var3 = null;
         var1.setline(100);
         var12 = var1.getderef(1).__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" not \\1"), (PyObject)var1.getlocal(0));
         var1.setlocal(0, var12);
         var3 = null;
         var1.setline(104);
         var12 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(.*?)\\?(.*?):(.*)"));
         var1.setderef(1, var12);
         var3 = null;
         var1.setline(105);
         var11 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var11;
         PyCode var10004 = repl$3;
         var11 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
         PyFunction var17 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var11);
         var1.setderef(0, var17);
         var3 = null;
         var1.setline(110);
         var15 = new PyList(new PyObject[]{PyString.fromInterned("")});
         var1.setlocal(8, var15);
         var3 = null;
         var1.setline(111);
         var12 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(111);
            var14 = var12.__iternext__();
            if (var14 == null) {
               var1.setline(124);
               var12 = var1.getderef(1).__getattr__("sub").__call__(var2, var1.getderef(0), var1.getlocal(8).__getattr__("pop").__call__(var2));
               var1.setlocal(0, var12);
               var3 = null;
               var1.setline(126);
               var12 = var1.getglobal("eval").__call__(var2, PyString.fromInterned("lambda n: int(%s)")._mod(var1.getlocal(0)));
               var1.f_lasti = -1;
               return var12;
            }

            var1.setlocal(9, var14);
            var1.setline(112);
            var5 = var1.getlocal(9);
            PyObject var18 = var5._eq(PyString.fromInterned("("));
            var5 = null;
            if (var18.__nonzero__()) {
               var1.setline(113);
               var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
            } else {
               var1.setline(114);
               var5 = var1.getlocal(9);
               var18 = var5._eq(PyString.fromInterned(")"));
               var5 = null;
               PyObject var6;
               PyObject var7;
               PyInteger var16;
               if (var18.__nonzero__()) {
                  var1.setline(115);
                  var5 = var1.getglobal("len").__call__(var2, var1.getlocal(8));
                  var18 = var5._eq(Py.newInteger(1));
                  var5 = null;
                  if (var18.__nonzero__()) {
                     var1.setline(119);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unbalanced parenthesis in plural form"));
                  }

                  var1.setline(120);
                  var5 = var1.getderef(1).__getattr__("sub").__call__(var2, var1.getderef(0), var1.getlocal(8).__getattr__("pop").__call__(var2));
                  var1.setlocal(10, var5);
                  var5 = null;
                  var1.setline(121);
                  var18 = var1.getlocal(8);
                  var16 = Py.newInteger(-1);
                  var6 = var18;
                  var7 = var6.__getitem__(var16);
                  var7 = var7._iadd(PyString.fromInterned("(%s)")._mod(var1.getlocal(10)));
                  var6.__setitem__((PyObject)var16, var7);
               } else {
                  var1.setline(123);
                  var18 = var1.getlocal(8);
                  var16 = Py.newInteger(-1);
                  var6 = var18;
                  var7 = var6.__getitem__(var16);
                  var7 = var7._iadd(var1.getlocal(9));
                  var6.__setitem__((PyObject)var16, var7);
               }
            }
         }
      }
   }

   public PyObject repl$3(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = PyString.fromInterned("test(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)), var1.getderef(0).__getattr__("sub").__call__(var2, var1.getderef(1), var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(3)))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _expand_lang$4(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      String[] var3 = new String[]{"normalize"};
      PyObject[] var6 = imp.importFrom("locale", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(132);
      PyObject var7 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var7);
      var3 = null;
      var1.setline(133);
      var7 = Py.newInteger(1)._lshift(Py.newInteger(0));
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(134);
      var7 = Py.newInteger(1)._lshift(Py.newInteger(1));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(135);
      var7 = Py.newInteger(1)._lshift(Py.newInteger(2));
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(137);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(138);
      var7 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(139);
      var7 = var1.getlocal(6);
      PyObject var10000 = var7._ge(Py.newInteger(0));
      var3 = null;
      PyString var9;
      if (var10000.__nonzero__()) {
         var1.setline(140);
         var7 = var1.getlocal(0).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
         var1.setlocal(7, var7);
         var3 = null;
         var1.setline(141);
         var7 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
         var1.setlocal(0, var7);
         var3 = null;
         var1.setline(142);
         var7 = var1.getlocal(5);
         var7 = var7._ior(var1.getlocal(4));
         var1.setlocal(5, var7);
      } else {
         var1.setline(144);
         var9 = PyString.fromInterned("");
         var1.setlocal(7, var9);
         var3 = null;
      }

      var1.setline(145);
      var7 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(146);
      var7 = var1.getlocal(6);
      var10000 = var7._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(147);
         var7 = var1.getlocal(0).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
         var1.setlocal(8, var7);
         var3 = null;
         var1.setline(148);
         var7 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
         var1.setlocal(0, var7);
         var3 = null;
         var1.setline(149);
         var7 = var1.getlocal(5);
         var7 = var7._ior(var1.getlocal(2));
         var1.setlocal(5, var7);
      } else {
         var1.setline(151);
         var9 = PyString.fromInterned("");
         var1.setlocal(8, var9);
         var3 = null;
      }

      var1.setline(152);
      var7 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(153);
      var7 = var1.getlocal(6);
      var10000 = var7._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(154);
         var7 = var1.getlocal(0).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
         var1.setlocal(9, var7);
         var3 = null;
         var1.setline(155);
         var7 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
         var1.setlocal(0, var7);
         var3 = null;
         var1.setline(156);
         var7 = var1.getlocal(5);
         var7 = var7._ior(var1.getlocal(3));
         var1.setlocal(5, var7);
      } else {
         var1.setline(158);
         var9 = PyString.fromInterned("");
         var1.setlocal(9, var9);
         var3 = null;
      }

      var1.setline(159);
      var7 = var1.getlocal(0);
      var1.setlocal(10, var7);
      var3 = null;
      var1.setline(160);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(11, var10);
      var3 = null;
      var1.setline(161);
      var7 = var1.getglobal("range").__call__(var2, var1.getlocal(5)._add(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(161);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(168);
            var1.getlocal(11).__getattr__("reverse").__call__(var2);
            var1.setline(169);
            var7 = var1.getlocal(11);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(12, var4);
         var1.setline(162);
         if (var1.getlocal(12)._and(var1.getlocal(5).__invert__()).__not__().__nonzero__()) {
            var1.setline(163);
            PyObject var5 = var1.getlocal(10);
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(164);
            if (var1.getlocal(12)._and(var1.getlocal(3)).__nonzero__()) {
               var1.setline(164);
               var5 = var1.getlocal(13);
               var5 = var5._iadd(var1.getlocal(9));
               var1.setlocal(13, var5);
            }

            var1.setline(165);
            if (var1.getlocal(12)._and(var1.getlocal(2)).__nonzero__()) {
               var1.setline(165);
               var5 = var1.getlocal(13);
               var5 = var5._iadd(var1.getlocal(8));
               var1.setlocal(13, var5);
            }

            var1.setline(166);
            if (var1.getlocal(12)._and(var1.getlocal(4)).__nonzero__()) {
               var1.setline(166);
               var5 = var1.getlocal(13);
               var5 = var5._iadd(var1.getlocal(7));
               var1.setlocal(13, var5);
            }

            var1.setline(167);
            var1.getlocal(11).__getattr__("append").__call__(var2, var1.getlocal(13));
         }
      }
   }

   public PyObject NullTranslations$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(174);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(182);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse$7, (PyObject)null);
      var1.setlocal("_parse", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_fallback$8, (PyObject)null);
      var1.setlocal("add_fallback", var4);
      var3 = null;
      var1.setline(191);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, gettext$9, (PyObject)null);
      var1.setlocal("gettext", var4);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lgettext$10, (PyObject)null);
      var1.setlocal("lgettext", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ngettext$11, (PyObject)null);
      var1.setlocal("ngettext", var4);
      var3 = null;
      var1.setline(209);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lngettext$12, (PyObject)null);
      var1.setlocal("lngettext", var4);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ugettext$13, (PyObject)null);
      var1.setlocal("ugettext", var4);
      var3 = null;
      var1.setline(222);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ungettext$14, (PyObject)null);
      var1.setlocal("ungettext", var4);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, info$15, (PyObject)null);
      var1.setlocal("info", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, charset$16, (PyObject)null);
      var1.setlocal("charset", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, output_charset$17, (PyObject)null);
      var1.setlocal("output_charset", var4);
      var3 = null;
      var1.setline(239);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_output_charset$18, (PyObject)null);
      var1.setlocal("set_output_charset", var4);
      var3 = null;
      var1.setline(242);
      var3 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, install$19, (PyObject)null);
      var1.setlocal("install", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_info", var3);
      var3 = null;
      var1.setline(176);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_charset", var4);
      var3 = null;
      var1.setline(177);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_output_charset", var4);
      var3 = null;
      var1.setline(178);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_fallback", var4);
      var3 = null;
      var1.setline(179);
      var4 = var1.getlocal(1);
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(180);
         var1.getlocal(0).__getattr__("_parse").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _parse$7(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_fallback$8(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
         var1.setline(187);
         var1.getlocal(0).__getattr__("_fallback").__getattr__("add_fallback").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(189);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_fallback", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gettext$9(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
         var1.setline(193);
         var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("gettext").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(194);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject lgettext$10(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
         var1.setline(198);
         var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("lgettext").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(199);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ngettext$11(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
         var1.setline(203);
         var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("ngettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(204);
         PyObject var4 = var1.getlocal(3);
         PyObject var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(205);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(207);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject lngettext$12(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
         var1.setline(211);
         var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("lngettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(212);
         PyObject var4 = var1.getlocal(3);
         PyObject var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(213);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(215);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject ugettext$13(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
         var1.setline(219);
         var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("ugettext").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(220);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ungettext$14(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
         var1.setline(224);
         var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("ungettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(225);
         PyObject var4 = var1.getlocal(3);
         PyObject var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(226);
            var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(228);
            var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject info$15(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var3 = var1.getlocal(0).__getattr__("_info");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject charset$16(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = var1.getlocal(0).__getattr__("_charset");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject output_charset$17(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getlocal(0).__getattr__("_output_charset");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_output_charset$18(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_output_charset", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install$19(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      PyObject var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(244);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("ugettext");
      }

      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("gettext");
      }

      var3 = var10000;
      var1.getlocal(3).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("_"), var3);
      var3 = null;
      var1.setline(245);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__contains__")).__nonzero__()) {
         var1.setline(246);
         PyString var4 = PyString.fromInterned("gettext");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(247);
            var3 = var1.getlocal(3).__getattr__("__dict__").__getitem__(PyString.fromInterned("_"));
            var1.getlocal(3).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("gettext"), var3);
            var3 = null;
         }

         var1.setline(248);
         var4 = PyString.fromInterned("ngettext");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(249);
            var10000 = var1.getlocal(1);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("ungettext");
            }

            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("ngettext");
            }

            var3 = var10000;
            var1.getlocal(3).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("ngettext"), var3);
            var3 = null;
         }

         var1.setline(251);
         var4 = PyString.fromInterned("lgettext");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(252);
            var3 = var1.getlocal(0).__getattr__("lgettext");
            var1.getlocal(3).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("lgettext"), var3);
            var3 = null;
         }

         var1.setline(253);
         var4 = PyString.fromInterned("lngettext");
         var10000 = var4._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(254);
            var3 = var1.getlocal(0).__getattr__("lngettext");
            var1.getlocal(3).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("lngettext"), var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject GNUTranslations$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(259);
      PyLong var3 = Py.newLong("2500072158");
      var1.setlocal("LE_MAGIC", var3);
      var3 = null;
      var1.setline(260);
      var3 = Py.newLong("3725722773");
      var1.setlocal("BE_MAGIC", var3);
      var3 = null;
      var1.setline(262);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _parse$21, PyString.fromInterned("Override this method to support alternative .mo formats."));
      var1.setlocal("_parse", var5);
      var3 = null;
      var1.setline(343);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, gettext$23, (PyObject)null);
      var1.setlocal("gettext", var5);
      var3 = null;
      var1.setline(357);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, lgettext$24, (PyObject)null);
      var1.setlocal("lgettext", var5);
      var3 = null;
      var1.setline(368);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ngettext$25, (PyObject)null);
      var1.setlocal("ngettext", var5);
      var3 = null;
      var1.setline(384);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, lngettext$26, (PyObject)null);
      var1.setlocal("lngettext", var5);
      var3 = null;
      var1.setline(398);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ugettext$27, (PyObject)null);
      var1.setlocal("ugettext", var5);
      var3 = null;
      var1.setline(407);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ungettext$28, (PyObject)null);
      var1.setlocal("ungettext", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _parse$21(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyString.fromInterned("Override this method to support alternative .mo formats.");
      var1.setline(264);
      PyObject var3 = var1.getglobal("struct").__getattr__("unpack");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(265);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("name"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(268);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_catalog", var10);
      var1.setlocal(4, var10);
      var1.setline(269);
      var1.setline(269);
      PyObject[] var11 = Py.EmptyObjects;
      PyFunction var13 = new PyFunction(var1.f_globals, var11, f$22);
      var1.getlocal(0).__setattr__((String)"plural", var13);
      var3 = null;
      var1.setline(270);
      var3 = var1.getlocal(1).__getattr__("read").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(271);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(273);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<I"), (PyObject)var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null)).__getitem__(Py.newInteger(0));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(274);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("LE_MAGIC"));
      var3 = null;
      PyObject[] var4;
      PyObject var5;
      PyString var16;
      if (var10000.__nonzero__()) {
         var1.setline(275);
         var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<4I"), (PyObject)var1.getlocal(5).__getslice__(Py.newInteger(4), Py.newInteger(20), (PyObject)null));
         var4 = Py.unpackSequence(var3, 4);
         var5 = var4[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(10, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(11, var5);
         var5 = null;
         var3 = null;
         var1.setline(276);
         var16 = PyString.fromInterned("<II");
         var1.setlocal(12, var16);
         var3 = null;
      } else {
         var1.setline(277);
         var3 = var1.getlocal(7);
         var10000 = var3._eq(var1.getlocal(0).__getattr__("BE_MAGIC"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(281);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("Bad magic number"), (PyObject)var1.getlocal(3)));
         }

         var1.setline(278);
         var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">4I"), (PyObject)var1.getlocal(5).__getslice__(Py.newInteger(4), Py.newInteger(20), (PyObject)null));
         var4 = Py.unpackSequence(var3, 4);
         var5 = var4[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(10, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(11, var5);
         var5 = null;
         var3 = null;
         var1.setline(279);
         var16 = PyString.fromInterned(">II");
         var1.setlocal(12, var16);
         var3 = null;
      }

      var1.setline(284);
      var3 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(9)).__iter__();

      while(true) {
         var1.setline(284);
         PyObject var12 = var3.__iternext__();
         if (var12 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(13, var12);
         var1.setline(285);
         var5 = var1.getlocal(2).__call__(var2, var1.getlocal(12), var1.getlocal(5).__getslice__(var1.getlocal(10), var1.getlocal(10)._add(Py.newInteger(8)), (PyObject)null));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(14, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(15, var7);
         var7 = null;
         var5 = null;
         var1.setline(286);
         var5 = var1.getlocal(15)._add(var1.getlocal(14));
         var1.setlocal(16, var5);
         var5 = null;
         var1.setline(287);
         var5 = var1.getlocal(2).__call__(var2, var1.getlocal(12), var1.getlocal(5).__getslice__(var1.getlocal(11), var1.getlocal(11)._add(Py.newInteger(8)), (PyObject)null));
         var6 = Py.unpackSequence(var5, 2);
         var7 = var6[0];
         var1.setlocal(17, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(18, var7);
         var7 = null;
         var5 = null;
         var1.setline(288);
         var5 = var1.getlocal(18)._add(var1.getlocal(17));
         var1.setlocal(19, var5);
         var5 = null;
         var1.setline(289);
         var5 = var1.getlocal(16);
         var10000 = var5._lt(var1.getlocal(6));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(19);
            var10000 = var5._lt(var1.getlocal(6));
            var5 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(293);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)PyString.fromInterned("File is corrupt"), (PyObject)var1.getlocal(3)));
         }

         var1.setline(290);
         var5 = var1.getlocal(5).__getslice__(var1.getlocal(15), var1.getlocal(16), (PyObject)null);
         var1.setlocal(20, var5);
         var5 = null;
         var1.setline(291);
         var5 = var1.getlocal(5).__getslice__(var1.getlocal(18), var1.getlocal(19), (PyObject)null);
         var1.setlocal(21, var5);
         var5 = null;
         var1.setline(295);
         var5 = var1.getlocal(14);
         var10000 = var5._eq(Py.newInteger(0));
         var5 = null;
         PyObject var14;
         if (var10000.__nonzero__()) {
            var1.setline(297);
            var5 = var1.getglobal("None");
            var1.setlocal(22, var5);
            var1.setlocal(23, var5);
            var1.setline(298);
            var5 = var1.getlocal(21).__getattr__("splitlines").__call__(var2).__iter__();

            while(true) {
               var1.setline(298);
               var14 = var5.__iternext__();
               if (var14 == null) {
                  break;
               }

               var1.setlocal(24, var14);
               var1.setline(299);
               var7 = var1.getlocal(24).__getattr__("strip").__call__(var2);
               var1.setlocal(24, var7);
               var7 = null;
               var1.setline(300);
               if (!var1.getlocal(24).__not__().__nonzero__()) {
                  var1.setline(302);
                  PyString var17 = PyString.fromInterned(":");
                  var10000 = var17._in(var1.getlocal(24));
                  var7 = null;
                  PyObject var9;
                  if (var10000.__nonzero__()) {
                     var1.setline(303);
                     var7 = var1.getlocal(24).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
                     PyObject[] var8 = Py.unpackSequence(var7, 2);
                     var9 = var8[0];
                     var1.setlocal(23, var9);
                     var9 = null;
                     var9 = var8[1];
                     var1.setlocal(25, var9);
                     var9 = null;
                     var7 = null;
                     var1.setline(304);
                     var7 = var1.getlocal(23).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2);
                     var1.setlocal(23, var7);
                     var7 = null;
                     var1.setline(305);
                     var7 = var1.getlocal(25).__getattr__("strip").__call__(var2);
                     var1.setlocal(25, var7);
                     var7 = null;
                     var1.setline(306);
                     var7 = var1.getlocal(25);
                     var1.getlocal(0).__getattr__("_info").__setitem__(var1.getlocal(23), var7);
                     var7 = null;
                     var1.setline(307);
                     var7 = var1.getlocal(23);
                     var1.setlocal(22, var7);
                     var7 = null;
                  } else {
                     var1.setline(308);
                     if (var1.getlocal(22).__nonzero__()) {
                        var1.setline(309);
                        var10000 = var1.getlocal(0).__getattr__("_info");
                        var7 = var1.getlocal(22);
                        PyObject var15 = var10000;
                        var9 = var15.__getitem__(var7);
                        var9 = var9._iadd(PyString.fromInterned("\n")._add(var1.getlocal(24)));
                        var15.__setitem__(var7, var9);
                     }
                  }

                  var1.setline(310);
                  var7 = var1.getlocal(23);
                  var10000 = var7._eq(PyString.fromInterned("content-type"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(311);
                     var7 = var1.getlocal(25).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset=")).__getitem__(Py.newInteger(1));
                     var1.getlocal(0).__setattr__("_charset", var7);
                     var7 = null;
                  } else {
                     var1.setline(312);
                     var7 = var1.getlocal(23);
                     var10000 = var7._eq(PyString.fromInterned("plural-forms"));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(313);
                        var7 = var1.getlocal(25).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
                        var1.setlocal(25, var7);
                        var7 = null;
                        var1.setline(314);
                        var7 = var1.getlocal(25).__getitem__(Py.newInteger(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("plural=")).__getitem__(Py.newInteger(1));
                        var1.setlocal(26, var7);
                        var7 = null;
                        var1.setline(315);
                        var7 = var1.getglobal("c2py").__call__(var2, var1.getlocal(26));
                        var1.getlocal(0).__setattr__("plural", var7);
                        var7 = null;
                     }
                  }
               }
            }
         }

         var1.setline(325);
         PyString var18 = PyString.fromInterned("\u0000");
         var10000 = var18._in(var1.getlocal(20));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(335);
            if (var1.getlocal(0).__getattr__("_charset").__nonzero__()) {
               var1.setline(336);
               var5 = var1.getglobal("unicode").__call__(var2, var1.getlocal(20), var1.getlocal(0).__getattr__("_charset"));
               var1.setlocal(20, var5);
               var5 = null;
               var1.setline(337);
               var5 = var1.getglobal("unicode").__call__(var2, var1.getlocal(21), var1.getlocal(0).__getattr__("_charset"));
               var1.setlocal(21, var5);
               var5 = null;
            }

            var1.setline(338);
            var5 = var1.getlocal(21);
            var1.getlocal(4).__setitem__(var1.getlocal(20), var5);
            var5 = null;
         } else {
            var1.setline(327);
            var5 = var1.getlocal(20).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000"));
            var6 = Py.unpackSequence(var5, 2);
            var7 = var6[0];
            var1.setlocal(27, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(28, var7);
            var7 = null;
            var5 = null;
            var1.setline(328);
            var5 = var1.getlocal(21).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000"));
            var1.setlocal(21, var5);
            var5 = null;
            var1.setline(329);
            if (var1.getlocal(0).__getattr__("_charset").__nonzero__()) {
               var1.setline(330);
               var5 = var1.getglobal("unicode").__call__(var2, var1.getlocal(27), var1.getlocal(0).__getattr__("_charset"));
               var1.setlocal(27, var5);
               var5 = null;
               var1.setline(331);
               PyList var20 = new PyList();
               var5 = var20.__getattr__("append");
               var1.setlocal(29, var5);
               var5 = null;
               var1.setline(331);
               var5 = var1.getlocal(21).__iter__();

               while(true) {
                  var1.setline(331);
                  var14 = var5.__iternext__();
                  if (var14 == null) {
                     var1.setline(331);
                     var1.dellocal(29);
                     PyList var19 = var20;
                     var1.setlocal(21, var19);
                     var5 = null;
                     break;
                  }

                  var1.setlocal(30, var14);
                  var1.setline(331);
                  var1.getlocal(29).__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getlocal(30), var1.getlocal(0).__getattr__("_charset")));
               }
            }

            var1.setline(332);
            var5 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(21))).__iter__();

            while(true) {
               var1.setline(332);
               var14 = var5.__iternext__();
               if (var14 == null) {
                  break;
               }

               var1.setlocal(13, var14);
               var1.setline(333);
               var7 = var1.getlocal(21).__getitem__(var1.getlocal(13));
               var1.getlocal(4).__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(27), var1.getlocal(13)})), var7);
               var7 = null;
            }
         }

         var1.setline(340);
         var5 = var1.getlocal(10);
         var5 = var5._iadd(Py.newInteger(8));
         var1.setlocal(10, var5);
         var1.setline(341);
         var5 = var1.getlocal(11);
         var5 = var5._iadd(Py.newInteger(8));
         var1.setlocal(11, var5);
      }
   }

   public PyObject f$22(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyObject var10000 = var1.getglobal("int");
      PyObject var3 = var1.getlocal(0);
      PyObject var10002 = var3._ne(Py.newInteger(1));
      var3 = null;
      var3 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gettext$23(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(345);
      var3 = var1.getlocal(0).__getattr__("_catalog").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(346);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(347);
         if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
            var1.setline(348);
            var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("gettext").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(349);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(351);
         if (var1.getlocal(0).__getattr__("_output_charset").__nonzero__()) {
            var1.setline(352);
            var3 = var1.getlocal(3).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("_output_charset"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(353);
            if (var1.getlocal(0).__getattr__("_charset").__nonzero__()) {
               var1.setline(354);
               var3 = var1.getlocal(3).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("_charset"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(355);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject lgettext$24(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(359);
      var3 = var1.getlocal(0).__getattr__("_catalog").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(360);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(361);
         if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
            var1.setline(362);
            var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("lgettext").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(363);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(364);
         if (var1.getlocal(0).__getattr__("_output_charset").__nonzero__()) {
            var1.setline(365);
            var3 = var1.getlocal(3).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("_output_charset"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(366);
            var3 = var1.getlocal(3).__getattr__("encode").__call__(var2, var1.getglobal("locale").__getattr__("getpreferredencoding").__call__(var2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject ngettext$25(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(370);
         var3 = var1.getlocal(0).__getattr__("_catalog").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("plural").__call__(var2, var1.getlocal(3))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(371);
         if (var1.getlocal(0).__getattr__("_output_charset").__nonzero__()) {
            var1.setline(372);
            var3 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("_output_charset"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(373);
            if (var1.getlocal(0).__getattr__("_charset").__nonzero__()) {
               var1.setline(374);
               var3 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("_charset"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(375);
               var3 = var1.getlocal(4);
               var1.f_lasti = -1;
               return var3;
            }
         }
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(377);
            if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
               var1.setline(378);
               var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("ngettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(379);
               PyObject var5 = var1.getlocal(3);
               PyObject var10000 = var5._eq(Py.newInteger(1));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(380);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(382);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject lngettext$26(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(386);
         var3 = var1.getlocal(0).__getattr__("_catalog").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("plural").__call__(var2, var1.getlocal(3))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(387);
         if (var1.getlocal(0).__getattr__("_output_charset").__nonzero__()) {
            var1.setline(388);
            var3 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("_output_charset"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(389);
            var3 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getglobal("locale").__getattr__("getpreferredencoding").__call__(var2));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(391);
            if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
               var1.setline(392);
               var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("lngettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(393);
               PyObject var5 = var1.getlocal(3);
               PyObject var10000 = var5._eq(Py.newInteger(1));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(394);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(396);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject ugettext$27(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(400);
      var3 = var1.getlocal(0).__getattr__("_catalog").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(401);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(402);
         if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
            var1.setline(403);
            var3 = var1.getlocal(0).__getattr__("_fallback").__getattr__("ugettext").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(404);
            var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(405);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject ungettext$28(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      try {
         var1.setline(409);
         PyObject var7 = var1.getlocal(0).__getattr__("_catalog").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("plural").__call__(var2, var1.getlocal(3))}));
         var1.setlocal(4, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(411);
         if (var1.getlocal(0).__getattr__("_fallback").__nonzero__()) {
            var1.setline(412);
            var4 = var1.getlocal(0).__getattr__("_fallback").__getattr__("ungettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(413);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(414);
            var5 = var1.getglobal("unicode").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var5);
            var5 = null;
         } else {
            var1.setline(416);
            var5 = var1.getglobal("unicode").__call__(var2, var1.getlocal(2));
            var1.setlocal(4, var5);
            var5 = null;
         }
      }

      var1.setline(417);
      var4 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject find$29(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(424);
         var3 = var1.getglobal("_default_localedir");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(425);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      PyObject var5;
      PyList var8;
      if (var10000.__nonzero__()) {
         var1.setline(426);
         var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var8);
         var3 = null;
         var1.setline(427);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("LANGUAGE"), PyString.fromInterned("LC_ALL"), PyString.fromInterned("LC_MESSAGES"), PyString.fromInterned("LANG")})).__iter__();

         while(true) {
            var1.setline(427);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(428);
            var5 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(429);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(430);
               var5 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
               var1.setlocal(2, var5);
               var5 = null;
               break;
            }
         }

         var1.setline(432);
         PyString var9 = PyString.fromInterned("C");
         var10000 = var9._notin(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(433);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("C"));
         }
      }

      var1.setline(435);
      var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(436);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(436);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(441);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(442);
               var8 = new PyList(Py.EmptyObjects);
               var1.setlocal(9, var8);
               var3 = null;
            } else {
               var1.setline(444);
               var3 = var1.getglobal("None");
               var1.setlocal(9, var3);
               var3 = null;
            }

            var1.setline(445);
            var3 = var1.getlocal(6).__iter__();

            while(true) {
               var1.setline(445);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(7, var4);
               var1.setline(446);
               var5 = var1.getlocal(7);
               var10000 = var5._eq(PyString.fromInterned("C"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(448);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(7), PyString.fromInterned("LC_MESSAGES"), PyString.fromInterned("%s.mo")._mod(var1.getlocal(0)));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(449);
               if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(10)).__nonzero__()) {
                  var1.setline(450);
                  if (!var1.getlocal(3).__nonzero__()) {
                     var1.setline(453);
                     var5 = var1.getlocal(10);
                     var1.f_lasti = -1;
                     return var5;
                  }

                  var1.setline(451);
                  var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(10));
               }
            }

            var1.setline(454);
            var5 = var1.getlocal(9);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(7, var4);
         var1.setline(437);
         var5 = var1.getglobal("_expand_lang").__call__(var2, var1.getlocal(7)).__iter__();

         while(true) {
            var1.setline(437);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(8, var6);
            var1.setline(438);
            PyObject var7 = var1.getlocal(8);
            var10000 = var7._notin(var1.getlocal(6));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(439);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
            }
         }
      }
   }

   public PyObject translation$30(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(463);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(464);
         var3 = var1.getglobal("GNUTranslations");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(465);
      var10000 = var1.getglobal("find");
      PyObject[] var9 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), Py.newInteger(1)};
      String[] var4 = new String[]{"all"};
      var10000 = var10000.__call__(var2, var9, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(466);
      if (var1.getlocal(6).__not__().__nonzero__()) {
         var1.setline(467);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(468);
            var3 = var1.getglobal("NullTranslations").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(469);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, var1.getglobal("ENOENT"), (PyObject)PyString.fromInterned("No translation file found for domain"), (PyObject)var1.getlocal(0)));
         }
      } else {
         var1.setline(472);
         PyObject var10 = var1.getglobal("None");
         var1.setlocal(7, var10);
         var4 = null;
         var1.setline(473);
         var10 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(473);
            PyObject var5 = var10.__iternext__();
            if (var5 == null) {
               var1.setline(489);
               var3 = var1.getlocal(7);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(8, var5);
            var1.setline(474);
            PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(8))});
            var1.setlocal(9, var6);
            var6 = null;
            var1.setline(475);
            PyObject var11 = var1.getglobal("_translations").__getattr__("get").__call__(var2, var1.getlocal(9));
            var1.setlocal(10, var11);
            var6 = null;
            var1.setline(476);
            var11 = var1.getlocal(10);
            var10000 = var11._is(var1.getglobal("None"));
            var6 = null;
            if (var10000.__nonzero__()) {
               label53: {
                  ContextManager var12;
                  PyObject var7 = (var12 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

                  try {
                     var1.setlocal(11, var7);
                     var1.setline(478);
                     var7 = var1.getglobal("_translations").__getattr__("setdefault").__call__(var2, var1.getlocal(9), var1.getlocal(3).__call__(var2, var1.getlocal(11)));
                     var1.setlocal(10, var7);
                     var7 = null;
                  } catch (Throwable var8) {
                     if (var12.__exit__(var2, Py.setException(var8, var1))) {
                        break label53;
                     }

                     throw (Throwable)Py.makeException();
                  }

                  var12.__exit__(var2, (PyException)null);
               }
            }

            var1.setline(482);
            var11 = var1.getglobal("copy").__getattr__("copy").__call__(var2, var1.getlocal(10));
            var1.setlocal(10, var11);
            var6 = null;
            var1.setline(483);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(484);
               var1.getlocal(10).__getattr__("set_output_charset").__call__(var2, var1.getlocal(5));
            }

            var1.setline(485);
            var11 = var1.getlocal(7);
            var10000 = var11._is(var1.getglobal("None"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(486);
               var11 = var1.getlocal(10);
               var1.setlocal(7, var11);
               var6 = null;
            } else {
               var1.setline(488);
               var1.getlocal(7).__getattr__("add_fallback").__call__(var2, var1.getlocal(10));
            }
         }
      }
   }

   public PyObject install$31(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var10000 = var1.getglobal("translation");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getglobal("True"), var1.getlocal(3)};
      String[] var4 = new String[]{"fallback", "codeset"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(494);
      var1.getlocal(5).__getattr__("install").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject textdomain$32(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(509);
         var3 = var1.getlocal(0);
         var1.setglobal("_current_domain", var3);
         var3 = null;
      }

      var1.setline(510);
      var3 = var1.getglobal("_current_domain");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject bindtextdomain$33(PyFrame var1, ThreadState var2) {
      var1.setline(515);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(516);
         var3 = var1.getlocal(1);
         var1.getglobal("_localedirs").__setitem__(var1.getlocal(0), var3);
         var3 = null;
      }

      var1.setline(517);
      var3 = var1.getglobal("_localedirs").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getglobal("_default_localedir"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject bind_textdomain_codeset$34(PyFrame var1, ThreadState var2) {
      var1.setline(522);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(523);
         var3 = var1.getlocal(1);
         var1.getglobal("_localecodesets").__setitem__(var1.getlocal(0), var3);
         var3 = null;
      }

      var1.setline(524);
      var3 = var1.getglobal("_localecodesets").__getattr__("get").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dgettext$35(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      try {
         var1.setline(529);
         PyObject var10000 = var1.getglobal("translation");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getglobal("_localedirs").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getglobal("None")), var1.getglobal("_localecodesets").__getattr__("get").__call__(var2, var1.getlocal(0))};
         String[] var8 = new String[]{"codeset"};
         var10000 = var10000.__call__(var2, var6, var8);
         var3 = null;
         PyObject var7 = var10000;
         var1.setlocal(2, var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(532);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(533);
      var4 = var1.getlocal(2).__getattr__("gettext").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject ldgettext$36(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      try {
         var1.setline(537);
         PyObject var10000 = var1.getglobal("translation");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getglobal("_localedirs").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getglobal("None")), var1.getglobal("_localecodesets").__getattr__("get").__call__(var2, var1.getlocal(0))};
         String[] var8 = new String[]{"codeset"};
         var10000 = var10000.__call__(var2, var6, var8);
         var3 = null;
         PyObject var7 = var10000;
         var1.setlocal(2, var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(540);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(541);
      var4 = var1.getlocal(2).__getattr__("lgettext").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject dngettext$37(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      PyException var3;
      PyObject var4;
      try {
         var1.setline(545);
         var10000 = var1.getglobal("translation");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getglobal("_localedirs").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getglobal("None")), var1.getglobal("_localecodesets").__getattr__("get").__call__(var2, var1.getlocal(0))};
         String[] var8 = new String[]{"codeset"};
         var10000 = var10000.__call__(var2, var6, var8);
         var3 = null;
         PyObject var7 = var10000;
         var1.setlocal(4, var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(548);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(549);
               var4 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var4;
            }

            var1.setline(551);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(552);
      var4 = var1.getlocal(4).__getattr__("ngettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject ldngettext$38(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      PyException var3;
      PyObject var4;
      try {
         var1.setline(556);
         var10000 = var1.getglobal("translation");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getglobal("_localedirs").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getglobal("None")), var1.getglobal("_localecodesets").__getattr__("get").__call__(var2, var1.getlocal(0))};
         String[] var8 = new String[]{"codeset"};
         var10000 = var10000.__call__(var2, var6, var8);
         var3 = null;
         PyObject var7 = var10000;
         var1.setlocal(4, var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(559);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(560);
               var4 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var4;
            }

            var1.setline(562);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(563);
      var4 = var1.getlocal(4).__getattr__("lngettext").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject gettext$39(PyFrame var1, ThreadState var2) {
      var1.setline(566);
      PyObject var3 = var1.getglobal("dgettext").__call__(var2, var1.getglobal("_current_domain"), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lgettext$40(PyFrame var1, ThreadState var2) {
      var1.setline(569);
      PyObject var3 = var1.getglobal("ldgettext").__call__(var2, var1.getglobal("_current_domain"), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ngettext$41(PyFrame var1, ThreadState var2) {
      var1.setline(572);
      PyObject var3 = var1.getglobal("dngettext").__call__(var2, var1.getglobal("_current_domain"), var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lngettext$42(PyFrame var1, ThreadState var2) {
      var1.setline(575);
      PyObject var3 = var1.getglobal("ldngettext").__call__(var2, var1.getglobal("_current_domain"), var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public gettext$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"condition", "true", "false"};
      test$1 = Py.newCode(3, var2, var1, "test", 61, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"plural", "StringIO", "token", "tokenize", "tokens", "danger", "_[87_18]", "x", "stack", "c", "s", "repl", "expr"};
      String[] var10001 = var2;
      gettext$py var10007 = self;
      var2 = new String[]{"repl", "expr"};
      c2py$2 = Py.newCode(1, var10001, var1, "c2py", 75, false, false, var10007, 2, var2, (String[])null, 2, 4097);
      var2 = new String[]{"x"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"expr", "repl"};
      repl$3 = Py.newCode(1, var10001, var1, "repl", 105, false, false, var10007, 3, (String[])null, var2, 0, 4097);
      var2 = new String[]{"locale", "normalize", "COMPONENT_CODESET", "COMPONENT_TERRITORY", "COMPONENT_MODIFIER", "mask", "pos", "modifier", "codeset", "territory", "language", "ret", "i", "val"};
      _expand_lang$4 = Py.newCode(1, var2, var1, "_expand_lang", 130, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NullTranslations$5 = Py.newCode(0, var2, var1, "NullTranslations", 173, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp"};
      __init__$6 = Py.newCode(2, var2, var1, "__init__", 174, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp"};
      _parse$7 = Py.newCode(2, var2, var1, "_parse", 182, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fallback"};
      add_fallback$8 = Py.newCode(2, var2, var1, "add_fallback", 185, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      gettext$9 = Py.newCode(2, var2, var1, "gettext", 191, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      lgettext$10 = Py.newCode(2, var2, var1, "lgettext", 196, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msgid1", "msgid2", "n"};
      ngettext$11 = Py.newCode(4, var2, var1, "ngettext", 201, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msgid1", "msgid2", "n"};
      lngettext$12 = Py.newCode(4, var2, var1, "lngettext", 209, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      ugettext$13 = Py.newCode(2, var2, var1, "ugettext", 217, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msgid1", "msgid2", "n"};
      ungettext$14 = Py.newCode(4, var2, var1, "ungettext", 222, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      info$15 = Py.newCode(1, var2, var1, "info", 230, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      charset$16 = Py.newCode(1, var2, var1, "charset", 233, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      output_charset$17 = Py.newCode(1, var2, var1, "output_charset", 236, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset"};
      set_output_charset$18 = Py.newCode(2, var2, var1, "set_output_charset", 239, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unicode", "names", "__builtin__"};
      install$19 = Py.newCode(3, var2, var1, "install", 242, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      GNUTranslations$20 = Py.newCode(0, var2, var1, "GNUTranslations", 257, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "unpack", "filename", "catalog", "buf", "buflen", "magic", "version", "msgcount", "masteridx", "transidx", "ii", "i", "mlen", "moff", "mend", "tlen", "toff", "tend", "msg", "tmsg", "lastk", "k", "item", "v", "plural", "msgid1", "msgid2", "_[331_28]", "x"};
      _parse$21 = Py.newCode(2, var2, var1, "_parse", 262, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n"};
      f$22 = Py.newCode(1, var2, var1, "<lambda>", 269, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "missing", "tmsg"};
      gettext$23 = Py.newCode(2, var2, var1, "gettext", 343, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "missing", "tmsg"};
      lgettext$24 = Py.newCode(2, var2, var1, "lgettext", 357, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msgid1", "msgid2", "n", "tmsg"};
      ngettext$25 = Py.newCode(4, var2, var1, "ngettext", 368, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msgid1", "msgid2", "n", "tmsg"};
      lngettext$26 = Py.newCode(4, var2, var1, "lngettext", 384, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "missing", "tmsg"};
      ugettext$27 = Py.newCode(2, var2, var1, "ugettext", 398, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msgid1", "msgid2", "n", "tmsg"};
      ungettext$28 = Py.newCode(4, var2, var1, "ungettext", 407, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "localedir", "languages", "all", "envar", "val", "nelangs", "lang", "nelang", "result", "mofile"};
      find$29 = Py.newCode(4, var2, var1, "find", 421, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "localedir", "languages", "class_", "fallback", "codeset", "mofiles", "result", "mofile", "key", "t", "fp"};
      translation$30 = Py.newCode(6, var2, var1, "translation", 461, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "localedir", "unicode", "codeset", "names", "t"};
      install$31 = Py.newCode(5, var2, var1, "install", 492, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain"};
      textdomain$32 = Py.newCode(1, var2, var1, "textdomain", 506, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "localedir"};
      bindtextdomain$33 = Py.newCode(2, var2, var1, "bindtextdomain", 513, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "codeset"};
      bind_textdomain_codeset$34 = Py.newCode(2, var2, var1, "bind_textdomain_codeset", 520, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "message", "t"};
      dgettext$35 = Py.newCode(2, var2, var1, "dgettext", 527, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "message", "t"};
      ldgettext$36 = Py.newCode(2, var2, var1, "ldgettext", 535, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "msgid1", "msgid2", "n", "t"};
      dngettext$37 = Py.newCode(4, var2, var1, "dngettext", 543, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"domain", "msgid1", "msgid2", "n", "t"};
      ldngettext$38 = Py.newCode(4, var2, var1, "ldngettext", 554, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message"};
      gettext$39 = Py.newCode(1, var2, var1, "gettext", 565, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message"};
      lgettext$40 = Py.newCode(1, var2, var1, "lgettext", 568, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msgid1", "msgid2", "n"};
      ngettext$41 = Py.newCode(3, var2, var1, "ngettext", 571, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msgid1", "msgid2", "n"};
      lngettext$42 = Py.newCode(3, var2, var1, "lngettext", 574, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new gettext$py("gettext$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(gettext$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.test$1(var2, var3);
         case 2:
            return this.c2py$2(var2, var3);
         case 3:
            return this.repl$3(var2, var3);
         case 4:
            return this._expand_lang$4(var2, var3);
         case 5:
            return this.NullTranslations$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this._parse$7(var2, var3);
         case 8:
            return this.add_fallback$8(var2, var3);
         case 9:
            return this.gettext$9(var2, var3);
         case 10:
            return this.lgettext$10(var2, var3);
         case 11:
            return this.ngettext$11(var2, var3);
         case 12:
            return this.lngettext$12(var2, var3);
         case 13:
            return this.ugettext$13(var2, var3);
         case 14:
            return this.ungettext$14(var2, var3);
         case 15:
            return this.info$15(var2, var3);
         case 16:
            return this.charset$16(var2, var3);
         case 17:
            return this.output_charset$17(var2, var3);
         case 18:
            return this.set_output_charset$18(var2, var3);
         case 19:
            return this.install$19(var2, var3);
         case 20:
            return this.GNUTranslations$20(var2, var3);
         case 21:
            return this._parse$21(var2, var3);
         case 22:
            return this.f$22(var2, var3);
         case 23:
            return this.gettext$23(var2, var3);
         case 24:
            return this.lgettext$24(var2, var3);
         case 25:
            return this.ngettext$25(var2, var3);
         case 26:
            return this.lngettext$26(var2, var3);
         case 27:
            return this.ugettext$27(var2, var3);
         case 28:
            return this.ungettext$28(var2, var3);
         case 29:
            return this.find$29(var2, var3);
         case 30:
            return this.translation$30(var2, var3);
         case 31:
            return this.install$31(var2, var3);
         case 32:
            return this.textdomain$32(var2, var3);
         case 33:
            return this.bindtextdomain$33(var2, var3);
         case 34:
            return this.bind_textdomain_codeset$34(var2, var3);
         case 35:
            return this.dgettext$35(var2, var3);
         case 36:
            return this.ldgettext$36(var2, var3);
         case 37:
            return this.dngettext$37(var2, var3);
         case 38:
            return this.ldngettext$38(var2, var3);
         case 39:
            return this.gettext$39(var2, var3);
         case 40:
            return this.lgettext$40(var2, var3);
         case 41:
            return this.ngettext$41(var2, var3);
         case 42:
            return this.lngettext$42(var2, var3);
         default:
            return null;
      }
   }
}
