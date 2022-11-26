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
@MTime(1498849384000L)
@Filename("locale.py")
public class locale$py extends PyFunctionTable implements PyRunnable {
   static locale$py self;
   static final PyCode f$0;
   static final PyCode _unicode$1;
   static final PyCode localeconv$2;
   static final PyCode setlocale$3;
   static final PyCode strcoll$4;
   static final PyCode strxfrm$5;
   static final PyCode localeconv$6;
   static final PyCode _grouping_intervals$7;
   static final PyCode _group$8;
   static final PyCode _strip_padding$9;
   static final PyCode format$10;
   static final PyCode _format$11;
   static final PyCode format_string$12;
   static final PyCode currency$13;
   static final PyCode str$14;
   static final PyCode atof$15;
   static final PyCode atoi$16;
   static final PyCode _test$17;
   static final PyCode f$18;
   static final PyCode normalize$19;
   static final PyCode _parse_localename$20;
   static final PyCode _build_localename$21;
   static final PyCode getdefaultlocale$22;
   static final PyCode getlocale$23;
   static final PyCode setlocale$24;
   static final PyCode resetlocale$25;
   static final PyCode getpreferredencoding$26;
   static final PyCode getpreferredencoding$27;
   static final PyCode getpreferredencoding$28;
   static final PyCode getpreferredencoding$29;
   static final PyCode _print_locale$30;
   static final PyCode _init_categories$31;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Locale support.\n\n    The module provides low-level access to the C lib's locale APIs\n    and adds high level number formatting APIs as well as a locale\n    aliasing engine to complement these.\n\n    The aliasing engine includes support for many commonly used locale\n    names and maps them to values suitable for passing to the C lib's\n    setlocale() function. It also includes default encodings for all\n    supported locale names.\n\n"));
      var1.setline(12);
      PyString.fromInterned(" Locale support.\n\n    The module provides low-level access to the C lib's locale APIs\n    and adds high level number formatting APIs as well as a locale\n    aliasing engine to complement these.\n\n    The aliasing engine includes support for many commonly used locale\n    names and maps them to values suitable for passing to the C lib's\n    setlocale() function. It also includes default encodings for all\n    supported locale names.\n\n");
      var1.setline(14);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("encodings", var1, -1);
      var1.setlocal("encodings", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("encodings.aliases", var1, -1);
      var1.setlocal("encodings", var3);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(18);
      var3 = imp.importOne("operator", var1, -1);
      var1.setlocal("operator", var3);
      var3 = null;
      var1.setline(19);
      var3 = imp.importOne("functools", var1, -1);
      var1.setlocal("functools", var3);
      var3 = null;

      PyObject[] var4;
      PyException var11;
      try {
         var1.setline(22);
         var3 = var1.getname("unicode");
         var1.setlocal("_unicode", var3);
         var3 = null;
      } catch (Throwable var9) {
         var11 = Py.setException(var9, var1);
         if (!var11.match(var1.getname("NameError"))) {
            throw var11;
         }

         var1.setline(26);
         var4 = new PyObject[]{var1.getname("object")};
         PyObject var5 = Py.makeClass("_unicode", var4, _unicode$1);
         var1.setlocal("_unicode", var5);
         var5 = null;
         Arrays.fill(var4, (Object)null);
      }

      var1.setline(35);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("getlocale"), PyString.fromInterned("getdefaultlocale"), PyString.fromInterned("getpreferredencoding"), PyString.fromInterned("Error"), PyString.fromInterned("setlocale"), PyString.fromInterned("resetlocale"), PyString.fromInterned("localeconv"), PyString.fromInterned("strcoll"), PyString.fromInterned("strxfrm"), PyString.fromInterned("str"), PyString.fromInterned("atof"), PyString.fromInterned("atoi"), PyString.fromInterned("format"), PyString.fromInterned("format_string"), PyString.fromInterned("currency"), PyString.fromInterned("normalize"), PyString.fromInterned("LC_CTYPE"), PyString.fromInterned("LC_COLLATE"), PyString.fromInterned("LC_TIME"), PyString.fromInterned("LC_MONETARY"), PyString.fromInterned("LC_NUMERIC"), PyString.fromInterned("LC_ALL"), PyString.fromInterned("CHAR_MAX")});
      var1.setlocal("__all__", var12);
      var3 = null;

      PyObject var14;
      PyFunction var17;
      try {
         var1.setline(43);
         imp.importAll("_locale", var1, -1);
      } catch (Throwable var8) {
         var11 = Py.setException(var8, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(49);
         PyInteger var10 = Py.newInteger(127);
         var1.setlocal("CHAR_MAX", var10);
         var4 = null;
         var1.setline(50);
         var10 = Py.newInteger(6);
         var1.setlocal("LC_ALL", var10);
         var4 = null;
         var1.setline(51);
         var10 = Py.newInteger(3);
         var1.setlocal("LC_COLLATE", var10);
         var4 = null;
         var1.setline(52);
         var10 = Py.newInteger(0);
         var1.setlocal("LC_CTYPE", var10);
         var4 = null;
         var1.setline(53);
         var10 = Py.newInteger(5);
         var1.setlocal("LC_MESSAGES", var10);
         var4 = null;
         var1.setline(54);
         var10 = Py.newInteger(4);
         var1.setlocal("LC_MONETARY", var10);
         var4 = null;
         var1.setline(55);
         var10 = Py.newInteger(1);
         var1.setlocal("LC_NUMERIC", var10);
         var4 = null;
         var1.setline(56);
         var10 = Py.newInteger(2);
         var1.setlocal("LC_TIME", var10);
         var4 = null;
         var1.setline(57);
         var14 = var1.getname("ValueError");
         var1.setlocal("Error", var14);
         var4 = null;
         var1.setline(59);
         var4 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var4, localeconv$2, PyString.fromInterned(" localeconv() -> dict.\n            Returns numeric and monetary locale-specific parameters.\n        "));
         var1.setlocal("localeconv", var17);
         var4 = null;
         var1.setline(83);
         var4 = new PyObject[]{var1.getname("None")};
         var17 = new PyFunction(var1.f_globals, var4, setlocale$3, PyString.fromInterned(" setlocale(integer,string=None) -> string.\n            Activates/queries locale processing.\n        "));
         var1.setlocal("setlocale", var17);
         var4 = null;
         var1.setline(91);
         var4 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var4, strcoll$4, PyString.fromInterned(" strcoll(string,string) -> int.\n            Compares two strings according to the locale.\n        "));
         var1.setlocal("strcoll", var17);
         var4 = null;
         var1.setline(97);
         var4 = Py.EmptyObjects;
         var17 = new PyFunction(var1.f_globals, var4, strxfrm$5, PyString.fromInterned(" strxfrm(string) -> string.\n            Returns a string that behaves for cmp locale-aware.\n        "));
         var1.setlocal("strxfrm", var17);
         var4 = null;
      }

      var1.setline(104);
      var3 = var1.getname("localeconv");
      var1.setlocal("_localeconv", var3);
      var3 = null;
      var1.setline(108);
      PyDictionary var13 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_override_localeconv", var13);
      var3 = null;
      var1.setline(110);
      PyObject[] var15 = Py.EmptyObjects;
      PyFunction var16 = new PyFunction(var1.f_globals, var15, localeconv$6, (PyObject)null);
      var3 = var1.getname("functools").__getattr__("wraps").__call__(var2, var1.getname("_localeconv")).__call__((ThreadState)var2, (PyObject)var16);
      var1.setlocal("localeconv", var3);
      var3 = null;
      var1.setline(124);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, _grouping_intervals$7, (PyObject)null);
      var1.setlocal("_grouping_intervals", var16);
      var3 = null;
      var1.setline(140);
      var15 = new PyObject[]{var1.getname("False")};
      var16 = new PyFunction(var1.f_globals, var15, _group$8, (PyObject)null);
      var1.setlocal("_group", var16);
      var3 = null;
      var1.setline(171);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, _strip_padding$9, (PyObject)null);
      var1.setlocal("_strip_padding", var16);
      var3 = null;
      var1.setline(182);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%(?:\\((?P<key>.*?)\\))?(?P<modifiers>[-#0-9 +*.hlL]*?)[eEfFgGdiouxXcrs%]"));
      var1.setlocal("_percent_re", var3);
      var3 = null;
      var1.setline(185);
      var15 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      var16 = new PyFunction(var1.f_globals, var15, format$10, PyString.fromInterned("Returns the locale-aware substitution of a %? specifier\n    (percent).\n\n    additional is for format strings which contain one or more\n    '*' modifiers."));
      var1.setlocal("format", var16);
      var3 = null;
      var1.setline(198);
      var15 = new PyObject[]{var1.getname("False"), var1.getname("False")};
      var16 = new PyFunction(var1.f_globals, var15, _format$11, (PyObject)null);
      var1.setlocal("_format", var16);
      var3 = null;
      var1.setline(222);
      var15 = new PyObject[]{var1.getname("False")};
      var16 = new PyFunction(var1.f_globals, var15, format_string$12, PyString.fromInterned("Formats a string in the same way that the % formatting would use,\n    but takes the current locale into account.\n    Grouping is applied if the third parameter is true."));
      var1.setlocal("format_string", var16);
      var3 = null;
      var1.setline(256);
      var15 = new PyObject[]{var1.getname("True"), var1.getname("False"), var1.getname("False")};
      var16 = new PyFunction(var1.f_globals, var15, currency$13, PyString.fromInterned("Formats val according to the currency settings\n    in the current locale."));
      var1.setlocal("currency", var16);
      var3 = null;
      var1.setline(301);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, str$14, PyString.fromInterned("Convert float to integer, taking the locale into account."));
      var1.setlocal("str", var16);
      var3 = null;
      var1.setline(305);
      var15 = new PyObject[]{var1.getname("float")};
      var16 = new PyFunction(var1.f_globals, var15, atof$15, PyString.fromInterned("Parses a string as a float according to the locale settings."));
      var1.setlocal("atof", var16);
      var3 = null;
      var1.setline(318);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, atoi$16, PyString.fromInterned("Converts a string to an integer according to the locale settings."));
      var1.setlocal("atoi", var16);
      var3 = null;
      var1.setline(322);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, _test$17, (PyObject)null);
      var1.setlocal("_test", var16);
      var3 = null;
      var1.setline(338);
      var3 = var1.getname("setlocale");
      var1.setlocal("_setlocale", var3);
      var3 = null;
      var1.setline(342);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      var1.setline(343);
      var15 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var15, f$18, (PyObject)null);
      PyObject var10002 = var17.__call__(var2, var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__());
      Arrays.fill(var15, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.setlocal("_ascii_lower_map", var3);
      var3 = null;
      var1.setline(347);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, normalize$19, PyString.fromInterned(" Returns a normalized locale code for the given locale\n        name.\n\n        The returned locale code is formatted for use with\n        setlocale().\n\n        If normalization fails, the original name is returned\n        unchanged.\n\n        If the given encoding is not known, the function defaults to\n        the default encoding for the locale code just like setlocale()\n        does.\n\n    "));
      var1.setlocal("normalize", var16);
      var3 = null;
      var1.setline(415);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, _parse_localename$20, PyString.fromInterned(" Parses the locale code for localename and returns the\n        result as tuple (language code, encoding).\n\n        The localename is normalized and passed through the locale\n        alias engine. A ValueError is raised in case the locale name\n        cannot be parsed.\n\n        The language code corresponds to RFC 1766.  code and encoding\n        can be None in case the values cannot be determined or are\n        unknown to this implementation.\n\n    "));
      var1.setlocal("_parse_localename", var16);
      var3 = null;
      var1.setline(445);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, _build_localename$21, PyString.fromInterned(" Builds a locale code from the given tuple (language code,\n        encoding).\n\n        No aliasing or normalizing takes place.\n\n    "));
      var1.setlocal("_build_localename", var16);
      var3 = null;
      var1.setline(461);
      var15 = new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("LC_ALL"), PyString.fromInterned("LC_CTYPE"), PyString.fromInterned("LANG"), PyString.fromInterned("LANGUAGE")})};
      var16 = new PyFunction(var1.f_globals, var15, getdefaultlocale$22, PyString.fromInterned(" Tries to determine the default locale settings and returns\n        them as tuple (language code, encoding).\n\n        According to POSIX, a program which has not called\n        setlocale(LC_ALL, \"\") runs using the portable 'C' locale.\n        Calling setlocale(LC_ALL, \"\") lets it use the default locale as\n        defined by the LANG variable. Since we don't want to interfere\n        with the current locale setting we thus emulate the behavior\n        in the way described above.\n\n        To maintain compatibility with other platforms, not only the\n        LANG variable is tested, but a list of variables given as\n        envvars parameter. The first found to be defined will be\n        used. envvars defaults to the search path used in GNU gettext;\n        it must always contain the variable name 'LANG'.\n\n        Except for the code 'C', the language code corresponds to RFC\n        1766.  code and encoding can be None in case the values cannot\n        be determined.\n\n    "));
      var1.setlocal("getdefaultlocale", var16);
      var3 = null;
      var1.setline(519);
      var15 = new PyObject[]{var1.getname("LC_CTYPE")};
      var16 = new PyFunction(var1.f_globals, var15, getlocale$23, PyString.fromInterned(" Returns the current setting for the given locale category as\n        tuple (language code, encoding).\n\n        category may be one of the LC_* value except LC_ALL. It\n        defaults to LC_CTYPE.\n\n        Except for the code 'C', the language code corresponds to RFC\n        1766.  code and encoding can be None in case the values cannot\n        be determined.\n\n    "));
      var1.setlocal("getlocale", var16);
      var3 = null;
      var1.setline(537);
      var15 = new PyObject[]{var1.getname("None")};
      var16 = new PyFunction(var1.f_globals, var15, setlocale$24, PyString.fromInterned(" Set the locale for the given category.  The locale can be\n        a string, an iterable of two strings (language code and encoding),\n        or None.\n\n        Iterables are converted to strings using the locale aliasing\n        engine.  Locale strings are passed directly to the C lib.\n\n        category may be given as one of the LC_* values.\n\n    "));
      var1.setlocal("setlocale", var16);
      var3 = null;
      var1.setline(554);
      var15 = new PyObject[]{var1.getname("LC_ALL")};
      var16 = new PyFunction(var1.f_globals, var15, resetlocale$25, PyString.fromInterned(" Sets the locale for category to the default setting.\n\n        The default setting is determined by calling\n        getdefaultlocale(). category defaults to LC_ALL.\n\n    "));
      var1.setlocal("resetlocale", var16);
      var3 = null;
      var1.setline(564);
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(565);
         String[] var18 = new String[]{"Charset"};
         var15 = imp.importFrom("java.nio.charset", var18, var1, -1);
         var14 = var15[0];
         var1.setlocal("Charset", var14);
         var4 = null;
         var1.setline(566);
         var15 = new PyObject[]{var1.getname("True")};
         var16 = new PyFunction(var1.f_globals, var15, getpreferredencoding$26, (PyObject)null);
         var1.setlocal("getpreferredencoding", var16);
         var3 = null;
      } else {
         var1.setline(568);
         if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("win")).__nonzero__()) {
            var1.setline(570);
            var15 = new PyObject[]{var1.getname("True")};
            var16 = new PyFunction(var1.f_globals, var15, getpreferredencoding$27, PyString.fromInterned("Return the charset that the user is likely using."));
            var1.setlocal("getpreferredencoding", var16);
            var3 = null;
         } else {
            label79: {
               try {
                  var1.setline(577);
                  var1.getname("CODESET");
               } catch (Throwable var7) {
                  var11 = Py.setException(var7, var1);
                  if (var11.match(var1.getname("NameError"))) {
                     var1.setline(580);
                     var4 = new PyObject[]{var1.getname("True")};
                     var17 = new PyFunction(var1.f_globals, var4, getpreferredencoding$28, PyString.fromInterned("Return the charset that the user is likely using,\n            by looking at environment variables."));
                     var1.setlocal("getpreferredencoding", var17);
                     var4 = null;
                     break label79;
                  }

                  throw var11;
               }

               var1.setline(585);
               var4 = new PyObject[]{var1.getname("True")};
               var17 = new PyFunction(var1.f_globals, var4, getpreferredencoding$29, PyString.fromInterned("Return the charset that the user is likely using,\n            according to the system configuration."));
               var1.setlocal("getpreferredencoding", var17);
               var4 = null;
            }
         }
      }

      var1.setline(616);
      var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("437"), PyString.fromInterned("C"), PyString.fromInterned("c"), PyString.fromInterned("C"), PyString.fromInterned("en"), PyString.fromInterned("ISO8859-1"), PyString.fromInterned("jis"), PyString.fromInterned("JIS7"), PyString.fromInterned("jis7"), PyString.fromInterned("JIS7"), PyString.fromInterned("ajec"), PyString.fromInterned("eucJP"), PyString.fromInterned("ascii"), PyString.fromInterned("ISO8859-1"), PyString.fromInterned("latin_1"), PyString.fromInterned("ISO8859-1"), PyString.fromInterned("iso8859_1"), PyString.fromInterned("ISO8859-1"), PyString.fromInterned("iso8859_10"), PyString.fromInterned("ISO8859-10"), PyString.fromInterned("iso8859_11"), PyString.fromInterned("ISO8859-11"), PyString.fromInterned("iso8859_13"), PyString.fromInterned("ISO8859-13"), PyString.fromInterned("iso8859_14"), PyString.fromInterned("ISO8859-14"), PyString.fromInterned("iso8859_15"), PyString.fromInterned("ISO8859-15"), PyString.fromInterned("iso8859_16"), PyString.fromInterned("ISO8859-16"), PyString.fromInterned("iso8859_2"), PyString.fromInterned("ISO8859-2"), PyString.fromInterned("iso8859_3"), PyString.fromInterned("ISO8859-3"), PyString.fromInterned("iso8859_4"), PyString.fromInterned("ISO8859-4"), PyString.fromInterned("iso8859_5"), PyString.fromInterned("ISO8859-5"), PyString.fromInterned("iso8859_6"), PyString.fromInterned("ISO8859-6"), PyString.fromInterned("iso8859_7"), PyString.fromInterned("ISO8859-7"), PyString.fromInterned("iso8859_8"), PyString.fromInterned("ISO8859-8"), PyString.fromInterned("iso8859_9"), PyString.fromInterned("ISO8859-9"), PyString.fromInterned("iso2022_jp"), PyString.fromInterned("JIS7"), PyString.fromInterned("shift_jis"), PyString.fromInterned("SJIS"), PyString.fromInterned("tactis"), PyString.fromInterned("TACTIS"), PyString.fromInterned("euc_jp"), PyString.fromInterned("eucJP"), PyString.fromInterned("euc_kr"), PyString.fromInterned("eucKR"), PyString.fromInterned("utf_8"), PyString.fromInterned("UTF-8"), PyString.fromInterned("koi8_r"), PyString.fromInterned("KOI8-R"), PyString.fromInterned("koi8_u"), PyString.fromInterned("KOI8-U")});
      var1.setlocal("locale_encoding_alias", var13);
      var3 = null;
      var1.setline(747);
      PyObject[] var19 = new PyObject[1686];
      set$$0(var19);
      var13 = new PyDictionary(var19);
      var1.setlocal("locale_alias", var13);
      var3 = null;
      var1.setline(1606);
      var19 = new PyObject[416];
      set$$1(var19);
      var13 = new PyDictionary(var19);
      var1.setlocal("windows_locale", var13);
      var3 = null;
      var1.setline(1819);
      var15 = Py.EmptyObjects;
      var16 = new PyFunction(var1.f_globals, var15, _print_locale$30, PyString.fromInterned(" Test function.\n    "));
      var1.setlocal("_print_locale", var16);
      var3 = null;

      label48: {
         try {
            var1.setline(1878);
            var1.getname("LC_MESSAGES");
         } catch (Throwable var6) {
            var11 = Py.setException(var6, var1);
            if (var11.match(var1.getname("NameError"))) {
               var1.setline(1880);
               break label48;
            }

            throw var11;
         }

         var1.setline(1882);
         var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LC_MESSAGES"));
      }

      var1.setline(1884);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1885);
         Py.println(PyString.fromInterned("Locale aliasing:"));
         var1.setline(1886);
         Py.println();
         var1.setline(1887);
         var1.getname("_print_locale").__call__(var2);
         var1.setline(1888);
         Py.println();
         var1.setline(1889);
         Py.println(PyString.fromInterned("Number formatting:"));
         var1.setline(1890);
         Py.println();
         var1.setline(1891);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unicode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(27);
      return var1.getf_locals();
   }

   public PyObject localeconv$2(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned(" localeconv() -> dict.\n            Returns numeric and monetary locale-specific parameters.\n        ");
      var1.setline(64);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("grouping"), new PyList(new PyObject[]{Py.newInteger(127)}), PyString.fromInterned("currency_symbol"), PyString.fromInterned(""), PyString.fromInterned("n_sign_posn"), Py.newInteger(127), PyString.fromInterned("p_cs_precedes"), Py.newInteger(127), PyString.fromInterned("n_cs_precedes"), Py.newInteger(127), PyString.fromInterned("mon_grouping"), new PyList(Py.EmptyObjects), PyString.fromInterned("n_sep_by_space"), Py.newInteger(127), PyString.fromInterned("decimal_point"), PyString.fromInterned("."), PyString.fromInterned("negative_sign"), PyString.fromInterned(""), PyString.fromInterned("positive_sign"), PyString.fromInterned(""), PyString.fromInterned("p_sep_by_space"), Py.newInteger(127), PyString.fromInterned("int_curr_symbol"), PyString.fromInterned(""), PyString.fromInterned("p_sign_posn"), Py.newInteger(127), PyString.fromInterned("thousands_sep"), PyString.fromInterned(""), PyString.fromInterned("mon_thousands_sep"), PyString.fromInterned(""), PyString.fromInterned("frac_digits"), Py.newInteger(127), PyString.fromInterned("mon_decimal_point"), PyString.fromInterned(""), PyString.fromInterned("int_frac_digits"), Py.newInteger(127)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setlocale$3(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyString.fromInterned(" setlocale(integer,string=None) -> string.\n            Activates/queries locale processing.\n        ");
      var1.setline(87);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), PyString.fromInterned(""), PyString.fromInterned("C")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(88);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("_locale emulation only supports \"C\" locale"));
      } else {
         var1.setline(89);
         PyString var4 = PyString.fromInterned("C");
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject strcoll$4(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned(" strcoll(string,string) -> int.\n            Compares two strings according to the locale.\n        ");
      var1.setline(95);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject strxfrm$5(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyString.fromInterned(" strxfrm(string) -> string.\n            Returns a string that behaves for cmp locale-aware.\n        ");
      var1.setline(101);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject localeconv$6(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getglobal("_localeconv").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(113);
      if (var1.getglobal("_override_localeconv").__nonzero__()) {
         var1.setline(114);
         var1.getlocal(0).__getattr__("update").__call__(var2, var1.getglobal("_override_localeconv"));
      }

      var1.setline(115);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _grouping_intervals$7(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      label42: {
         label45: {
            Object var10000;
            PyObject var6;
            switch (var1.f_lasti) {
               case 0:
               default:
                  var1.setline(125);
                  var3 = var1.getglobal("None");
                  var1.setlocal(1, var3);
                  var3 = null;
                  var1.setline(126);
                  var3 = var1.getlocal(0).__iter__();
                  break;
               case 1:
                  var5 = var1.f_savedlocals;
                  var3 = (PyObject)var5[3];
                  var4 = (PyObject)var5[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var7 = (PyObject)var10000;
                  break label45;
               case 2:
                  var5 = var1.f_savedlocals;
                  var3 = (PyObject)var5[3];
                  var4 = (PyObject)var5[4];
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var7 = (PyObject)var10000;
                  var1.setline(137);
                  var6 = var1.getlocal(2);
                  var1.setlocal(1, var6);
                  var5 = null;
            }

            var1.setline(126);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(128);
            var6 = var1.getlocal(2);
            var7 = var6._eq(var1.getglobal("CHAR_MAX"));
            var5 = null;
            if (var7.__nonzero__()) {
               var1.setline(129);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(131);
            var6 = var1.getlocal(2);
            var7 = var6._eq(Py.newInteger(0));
            var5 = null;
            if (!var7.__nonzero__()) {
               break label42;
            }

            var1.setline(132);
            var6 = var1.getlocal(1);
            var7 = var6._is(var1.getglobal("None"));
            var5 = null;
            if (var7.__nonzero__()) {
               var1.setline(133);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid grouping")));
            }
         }

         var1.setline(134);
         if (var1.getglobal("True").__nonzero__()) {
            var1.setline(135);
            var1.setline(135);
            var7 = var1.getlocal(1);
            var1.f_lasti = 1;
            var5 = new Object[7];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var7;
         }
      }

      var1.setline(136);
      var1.setline(136);
      var7 = var1.getlocal(2);
      var1.f_lasti = 2;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject _group$8(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getglobal("localeconv").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(142);
      PyObject var10000 = var1.getlocal(2);
      Object var10001 = var1.getlocal(1);
      if (((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("mon_thousands_sep");
      }

      if (!((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("thousands_sep");
      }

      var3 = var10000.__getitem__((PyObject)var10001);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(143);
      var10000 = var1.getlocal(2);
      var10001 = var1.getlocal(1);
      if (((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("mon_grouping");
      }

      if (!((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("grouping");
      }

      var3 = var10000.__getitem__((PyObject)var10001);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(144);
      PyTuple var7;
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(145);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(0), Py.newInteger(0)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(146);
         PyObject var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
         var10000 = var4._eq(PyString.fromInterned(" "));
         var4 = null;
         PyString var8;
         if (var10000.__nonzero__()) {
            var1.setline(147);
            var4 = var1.getlocal(0).__getattr__("rstrip").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(148);
            var4 = var1.getlocal(0).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)null, (PyObject)null);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(149);
            var4 = var1.getlocal(5);
            var1.setlocal(0, var4);
            var4 = null;
         } else {
            var1.setline(151);
            var8 = PyString.fromInterned("");
            var1.setlocal(6, var8);
            var4 = null;
         }

         var1.setline(152);
         var8 = PyString.fromInterned("");
         var1.setlocal(7, var8);
         var4 = null;
         var1.setline(153);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var9);
         var4 = null;
         var1.setline(154);
         var4 = var1.getglobal("_grouping_intervals").__call__(var2, var1.getlocal(4)).__iter__();

         while(true) {
            var1.setline(154);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(9, var5);
            var1.setline(155);
            var10000 = var1.getlocal(0).__not__();
            PyObject var6;
            if (!var10000.__nonzero__()) {
               var6 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
               var10000 = var6._notin(PyString.fromInterned("0123456789"));
               var6 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(157);
               var6 = var1.getlocal(0);
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(158);
               PyString var10 = PyString.fromInterned("");
               var1.setlocal(0, var10);
               var6 = null;
               break;
            }

            var1.setline(160);
            var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(9).__neg__(), (PyObject)null, (PyObject)null));
            var1.setline(161);
            var6 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(9).__neg__(), (PyObject)null);
            var1.setlocal(0, var6);
            var6 = null;
         }

         var1.setline(162);
         if (var1.getlocal(0).__nonzero__()) {
            var1.setline(163);
            var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(0));
         }

         var1.setline(164);
         var1.getlocal(8).__getattr__("reverse").__call__(var2);
         var1.setline(165);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(7)._add(var1.getlocal(3).__getattr__("join").__call__(var2, var1.getlocal(8)))._add(var1.getlocal(6)), var1.getglobal("len").__call__(var2, var1.getlocal(3))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(8))._sub(Py.newInteger(1)))});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _strip_padding$9(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(173);
         PyObject var10000 = var1.getlocal(1);
         PyObject var4;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getitem__(var1.getlocal(2));
            var10000 = var4._eq(PyString.fromInterned(" "));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(176);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0))._sub(Py.newInteger(1));
            var1.setlocal(3, var4);
            var3 = null;

            while(true) {
               var1.setline(177);
               var10000 = var1.getlocal(1);
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getitem__(var1.getlocal(3));
                  var10000 = var4._eq(PyString.fromInterned(" "));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(180);
                  var4 = var1.getlocal(0).__getslice__(var1.getlocal(2), var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null);
                  var1.f_lasti = -1;
                  return var4;
               }

               var1.setline(178);
               var4 = var1.getlocal(3);
               var4 = var4._isub(Py.newInteger(1));
               var1.setlocal(3, var4);
               var1.setline(179);
               var4 = var1.getlocal(1);
               var4 = var4._isub(Py.newInteger(1));
               var1.setlocal(1, var4);
            }
         }

         var1.setline(174);
         var4 = var1.getlocal(2);
         var4 = var4._iadd(Py.newInteger(1));
         var1.setlocal(2, var4);
         var1.setline(175);
         var4 = var1.getlocal(1);
         var4 = var4._isub(Py.newInteger(1));
         var1.setlocal(1, var4);
      }
   }

   public PyObject format$10(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyString.fromInterned("Returns the locale-aware substitution of a %? specifier\n    (percent).\n\n    additional is for format strings which contain one or more\n    '*' modifiers.");
      var1.setline(192);
      PyObject var3 = var1.getglobal("_percent_re").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(193);
      PyObject var10000 = var1.getlocal(5).__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("group").__call__(var2));
         var10000 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(194);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("format() must be given exactly one %%char format specifier, %s not valid")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.setline(196);
         var10000 = var1.getglobal("_format");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var5, var4, var1.getlocal(4), (PyObject)null);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _format$11(PyFrame var1, ThreadState var2) {
      var1.setline(199);
      PyObject var3;
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(200);
         var3 = var1.getlocal(0)._mod((new PyTuple(new PyObject[]{var1.getlocal(1)}))._add(var1.getlocal(4)));
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(202);
         var3 = var1.getlocal(0)._mod(var1.getlocal(1));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(204);
      var3 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
      PyObject var10000 = var3._in(PyString.fromInterned("eEfFgG"));
      var3 = null;
      String[] var4;
      PyObject var5;
      PyObject[] var6;
      PyInteger var7;
      PyObject[] var8;
      if (var10000.__nonzero__()) {
         var1.setline(205);
         var7 = Py.newInteger(0);
         var1.setlocal(6, var7);
         var3 = null;
         var1.setline(206);
         var3 = var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(207);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(208);
            var10000 = var1.getglobal("_group");
            var8 = new PyObject[]{var1.getlocal(7).__getitem__(Py.newInteger(0)), var1.getlocal(3)};
            var4 = new String[]{"monetary"};
            var10000 = var10000.__call__(var2, var8, var4);
            var3 = null;
            var3 = var10000;
            var6 = Py.unpackSequence(var3, 2);
            var5 = var6[0];
            var1.getlocal(7).__setitem__((PyObject)Py.newInteger(0), var5);
            var5 = null;
            var5 = var6[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(209);
         var10000 = var1.getglobal("localeconv").__call__(var2);
         Object var10001 = var1.getlocal(3);
         if (((PyObject)var10001).__nonzero__()) {
            var10001 = PyString.fromInterned("mon_decimal_point");
         }

         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = PyString.fromInterned("decimal_point");
         }

         var3 = var10000.__getitem__((PyObject)var10001);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(211);
         var3 = var1.getlocal(8).__getattr__("join").__call__(var2, var1.getlocal(7));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(212);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(213);
            var3 = var1.getglobal("_strip_padding").__call__(var2, var1.getlocal(5), var1.getlocal(6));
            var1.setlocal(5, var3);
            var3 = null;
         }
      } else {
         var1.setline(214);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
         var10000 = var3._in(PyString.fromInterned("diu"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(215);
            var7 = Py.newInteger(0);
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(216);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(217);
               var10000 = var1.getglobal("_group");
               var8 = new PyObject[]{var1.getlocal(5), var1.getlocal(3)};
               var4 = new String[]{"monetary"};
               var10000 = var10000.__call__(var2, var8, var4);
               var3 = null;
               var3 = var10000;
               var6 = Py.unpackSequence(var3, 2);
               var5 = var6[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var6[1];
               var1.setlocal(6, var5);
               var5 = null;
               var3 = null;
            }

            var1.setline(218);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(219);
               var3 = var1.getglobal("_strip_padding").__call__(var2, var1.getlocal(5), var1.getlocal(6));
               var1.setlocal(5, var3);
               var3 = null;
            }
         }
      }

      var1.setline(220);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_string$12(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyString.fromInterned("Formats a string in the same way that the % formatting would use,\n    but takes the current locale into account.\n    Grouping is applied if the third parameter is true.");
      var1.setline(226);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getglobal("_percent_re").__getattr__("finditer").__call__(var2, var1.getlocal(0)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getglobal("_percent_re").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s"), (PyObject)var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(229);
      PyObject var10000;
      PyObject var4;
      PyObject var5;
      PyList var7;
      if (var1.getglobal("operator").__getattr__("isMappingType").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(230);
         var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(231);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(231);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(6, var4);
            var1.setline(232);
            var5 = var1.getlocal(6).__getattr__("group").__call__(var2).__getitem__(Py.newInteger(-1));
            var10000 = var5._eq(PyString.fromInterned("%"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(233);
               var1.getlocal(5).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
            } else {
               var1.setline(235);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getglobal("format").__call__(var2, var1.getlocal(6).__getattr__("group").__call__(var2), var1.getlocal(1), var1.getlocal(2)));
            }
         }
      } else {
         var1.setline(237);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__not__().__nonzero__()) {
            var1.setline(238);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1)});
            var1.setlocal(1, var8);
            var3 = null;
         }

         var1.setline(239);
         var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(240);
         PyInteger var9 = Py.newInteger(0);
         var1.setlocal(7, var9);
         var3 = null;
         var1.setline(241);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(241);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(6, var4);
            var1.setline(242);
            var5 = var1.getlocal(6).__getattr__("group").__call__(var2).__getitem__(Py.newInteger(-1));
            var10000 = var5._eq(PyString.fromInterned("%"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(243);
               var1.getlocal(5).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
            } else {
               var1.setline(245);
               var5 = var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("modifiers")).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("*"));
               var1.setlocal(8, var5);
               var5 = null;
               var1.setline(246);
               var10000 = var1.getlocal(5).__getattr__("append");
               PyObject var10002 = var1.getglobal("_format");
               PyObject[] var10 = new PyObject[]{var1.getlocal(6).__getattr__("group").__call__(var2), var1.getlocal(1).__getitem__(var1.getlocal(7)), var1.getlocal(2), var1.getglobal("False")};
               String[] var6 = new String[0];
               var10002 = var10002._callextra(var10, var6, var1.getlocal(1).__getslice__(var1.getlocal(7)._add(Py.newInteger(1)), var1.getlocal(7)._add(Py.newInteger(1))._add(var1.getlocal(8)), (PyObject)null), (PyObject)null);
               var5 = null;
               var10000.__call__(var2, var10002);
               var1.setline(251);
               var5 = var1.getlocal(7);
               var5 = var5._iadd(Py.newInteger(1)._add(var1.getlocal(8)));
               var1.setlocal(7, var5);
            }
         }
      }

      var1.setline(252);
      var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(5));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(254);
      var3 = var1.getlocal(4)._mod(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject currency$13(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyString.fromInterned("Formats val according to the currency settings\n    in the current locale.");
      var1.setline(259);
      PyObject var3 = var1.getglobal("localeconv").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(262);
      PyObject var10000 = var1.getlocal(4);
      Object var10001 = var1.getlocal(3);
      if (((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("int_frac_digits");
      }

      if (!((PyObject)var10001).__nonzero__()) {
         var10001 = PyString.fromInterned("frac_digits");
      }

      var3 = var10000.__getitem__((PyObject)var10001);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(263);
      var3 = var1.getlocal(5);
      var10000 = var3._eq(Py.newInteger(127));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(264);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Currency formatting is not possible using the 'C' locale.")));
      } else {
         var1.setline(267);
         var10000 = var1.getglobal("format");
         PyObject[] var5 = new PyObject[]{PyString.fromInterned("%%.%if")._mod(var1.getlocal(5)), var1.getglobal("abs").__call__(var2, var1.getlocal(0)), var1.getlocal(2), var1.getglobal("True")};
         String[] var4 = new String[]{"monetary"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(269);
         var3 = PyString.fromInterned("<")._add(var1.getlocal(6))._add(PyString.fromInterned(">"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(271);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(272);
            var10000 = var1.getlocal(4);
            var10001 = var1.getlocal(3);
            if (((PyObject)var10001).__nonzero__()) {
               var10001 = PyString.fromInterned("int_curr_symbol");
            }

            if (!((PyObject)var10001).__nonzero__()) {
               var10001 = PyString.fromInterned("currency_symbol");
            }

            var3 = var10000.__getitem__((PyObject)var10001);
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(273);
            var10000 = var1.getlocal(4);
            var3 = var1.getlocal(0);
            var10001 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (((PyObject)var10001).__nonzero__()) {
               var10001 = PyString.fromInterned("n_cs_precedes");
            }

            if (!((PyObject)var10001).__nonzero__()) {
               var10001 = PyString.fromInterned("p_cs_precedes");
            }

            var3 = var10000.__getitem__((PyObject)var10001);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(274);
            var10000 = var1.getlocal(4);
            var3 = var1.getlocal(0);
            var10001 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (((PyObject)var10001).__nonzero__()) {
               var10001 = PyString.fromInterned("n_sep_by_space");
            }

            if (!((PyObject)var10001).__nonzero__()) {
               var10001 = PyString.fromInterned("p_sep_by_space");
            }

            var3 = var10000.__getitem__((PyObject)var10001);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(276);
            if (var1.getlocal(8).__nonzero__()) {
               var1.setline(277);
               var10000 = var1.getlocal(7);
               var10001 = var1.getlocal(9);
               if (((PyObject)var10001).__nonzero__()) {
                  var10001 = PyString.fromInterned(" ");
               }

               if (!((PyObject)var10001).__nonzero__()) {
                  var10001 = PyString.fromInterned("");
               }

               var3 = var10000._add((PyObject)var10001)._add(var1.getlocal(6));
               var1.setlocal(6, var3);
               var3 = null;
            } else {
               var1.setline(279);
               var10000 = var1.getlocal(6);
               var10001 = var1.getlocal(9);
               if (((PyObject)var10001).__nonzero__()) {
                  var10001 = PyString.fromInterned(" ");
               }

               if (!((PyObject)var10001).__nonzero__()) {
                  var10001 = PyString.fromInterned("");
               }

               var3 = var10000._add((PyObject)var10001)._add(var1.getlocal(7));
               var1.setlocal(6, var3);
               var3 = null;
            }
         }

         var1.setline(281);
         var10000 = var1.getlocal(4);
         var3 = var1.getlocal(0);
         var10001 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (((PyObject)var10001).__nonzero__()) {
            var10001 = PyString.fromInterned("n_sign_posn");
         }

         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = PyString.fromInterned("p_sign_posn");
         }

         var3 = var10000.__getitem__((PyObject)var10001);
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(282);
         var10000 = var1.getlocal(4);
         var3 = var1.getlocal(0);
         var10001 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (((PyObject)var10001).__nonzero__()) {
            var10001 = PyString.fromInterned("negative_sign");
         }

         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = PyString.fromInterned("positive_sign");
         }

         var3 = var10000.__getitem__((PyObject)var10001);
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(284);
         var3 = var1.getlocal(10);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(285);
            var3 = PyString.fromInterned("(")._add(var1.getlocal(6))._add(PyString.fromInterned(")"));
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(286);
            var3 = var1.getlocal(10);
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(287);
               var3 = var1.getlocal(11)._add(var1.getlocal(6));
               var1.setlocal(6, var3);
               var3 = null;
            } else {
               var1.setline(288);
               var3 = var1.getlocal(10);
               var10000 = var3._eq(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(289);
                  var3 = var1.getlocal(6)._add(var1.getlocal(11));
                  var1.setlocal(6, var3);
                  var3 = null;
               } else {
                  var1.setline(290);
                  var3 = var1.getlocal(10);
                  var10000 = var3._eq(Py.newInteger(3));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(291);
                     var3 = var1.getlocal(6).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)var1.getlocal(11));
                     var1.setlocal(6, var3);
                     var3 = null;
                  } else {
                     var1.setline(292);
                     var3 = var1.getlocal(10);
                     var10000 = var3._eq(Py.newInteger(4));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(293);
                        var3 = var1.getlocal(6).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)var1.getlocal(11));
                        var1.setlocal(6, var3);
                        var3 = null;
                     } else {
                        var1.setline(297);
                        var3 = var1.getlocal(11)._add(var1.getlocal(6));
                        var1.setlocal(6, var3);
                        var3 = null;
                     }
                  }
               }
            }
         }

         var1.setline(299);
         var3 = var1.getlocal(6).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned(""));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject str$14(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyString.fromInterned("Convert float to integer, taking the locale into account.");
      var1.setline(303);
      PyObject var3 = var1.getglobal("format").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%.12g"), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject atof$15(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Parses a string as a float according to the locale settings.");
      var1.setline(308);
      PyObject var3 = var1.getglobal("localeconv").__call__(var2).__getitem__(PyString.fromInterned("thousands_sep"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(309);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(310);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned(""));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(312);
      var3 = var1.getglobal("localeconv").__call__(var2).__getitem__(PyString.fromInterned("decimal_point"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(313);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(314);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("."));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(316);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject atoi$16(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyString.fromInterned("Converts a string to an integer according to the locale settings.");
      var1.setline(320);
      PyObject var3 = var1.getglobal("atof").__call__(var2, var1.getlocal(0), var1.getglobal("int"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _test$17(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      var1.getglobal("setlocale").__call__((ThreadState)var2, (PyObject)var1.getglobal("LC_ALL"), (PyObject)PyString.fromInterned(""));
      var1.setline(325);
      PyObject var3 = var1.getglobal("format").__call__((ThreadState)var2, PyString.fromInterned("%d"), (PyObject)Py.newInteger(123456789), (PyObject)Py.newInteger(1));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(326);
      Py.printComma(var1.getlocal(0));
      Py.printComma(PyString.fromInterned("is"));
      Py.println(var1.getglobal("atoi").__call__(var2, var1.getlocal(0)));
      var1.setline(328);
      var3 = var1.getglobal("str").__call__((ThreadState)var2, (PyObject)Py.newFloat(3.14));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(329);
      Py.printComma(var1.getlocal(0));
      Py.printComma(PyString.fromInterned("is"));
      Py.println(var1.getglobal("atof").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$18(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(344);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(344);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(343);
         var1.setline(343);
         var7 = var1.getglobal("chr");
         var1.setline(343);
         PyObject var6 = var1.getlocal(1);
         PyObject var10002 = var6._ge(var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A")));
         var5 = null;
         if (var10002.__nonzero__()) {
            var6 = var1.getlocal(1);
            var10002 = var6._le(var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Z")));
            var5 = null;
         }

         var7 = var7.__call__(var2, var10002.__nonzero__() ? var1.getlocal(1)._add(Py.newInteger(32)) : var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject normalize$19(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyString.fromInterned(" Returns a normalized locale code for the given locale\n        name.\n\n        The returned locale code is formatted for use with\n        setlocale().\n\n        If normalization fails, the original name is returned\n        unchanged.\n\n        If the given encoding is not known, the function defaults to\n        the default encoding for the locale code just like setlocale()\n        does.\n\n    ");
      var1.setline(364);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_unicode")).__nonzero__()) {
         var1.setline(365);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(366);
      var3 = var1.getlocal(0).__getattr__("translate").__call__(var2, var1.getglobal("_ascii_lower_map"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(367);
      PyString var7 = PyString.fromInterned(":");
      PyObject var10000 = var7._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(369);
         var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)PyString.fromInterned("."));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(370);
      var7 = PyString.fromInterned(".");
      var10000 = var7._in(var1.getlocal(1));
      var3 = null;
      PyObject[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(371);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(372);
         var3 = var1.getlocal(2)._add(PyString.fromInterned("."))._add(var1.getlocal(3));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(374);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(375);
         var7 = PyString.fromInterned("");
         var1.setlocal(3, var7);
         var3 = null;
      }

      var1.setline(378);
      var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(379);
      var3 = var1.getlocal(4).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(380);
      var3 = var1.getlocal(2)._add(PyString.fromInterned("."))._add(var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(381);
      var3 = var1.getglobal("locale_alias").__getattr__("get").__call__(var2, var1.getlocal(5), var1.getglobal("None"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(382);
      var3 = var1.getlocal(6);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(383);
         var3 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(387);
         PyObject var8 = var1.getglobal("locale_alias").__getattr__("get").__call__(var2, var1.getlocal(2), var1.getglobal("None"));
         var1.setlocal(6, var8);
         var4 = null;
         var1.setline(388);
         var8 = var1.getlocal(6);
         var10000 = var8._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(390);
            PyString var9 = PyString.fromInterned(".");
            var10000 = var9._in(var1.getlocal(6));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(391);
               var8 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
               PyObject[] var10 = Py.unpackSequence(var8, 2);
               PyObject var6 = var10[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var10[1];
               var1.setlocal(7, var6);
               var6 = null;
               var4 = null;
            } else {
               var1.setline(393);
               var8 = var1.getlocal(6);
               var1.setlocal(2, var8);
               var4 = null;
               var1.setline(394);
               var9 = PyString.fromInterned("");
               var1.setlocal(7, var9);
               var4 = null;
            }

            var1.setline(395);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(397);
               var8 = var1.getglobal("encodings").__getattr__("normalize_encoding").__call__(var2, var1.getlocal(3));
               var1.setlocal(4, var8);
               var4 = null;
               var1.setline(399);
               var8 = var1.getglobal("encodings").__getattr__("aliases").__getattr__("aliases").__getattr__("get").__call__(var2, var1.getlocal(4), var1.getlocal(4));
               var1.setlocal(4, var8);
               var4 = null;
               var1.setline(402);
               var8 = var1.getglobal("locale_encoding_alias").__getattr__("get").__call__(var2, var1.getlocal(4), var1.getlocal(4));
               var1.setlocal(3, var8);
               var4 = null;
            } else {
               var1.setline(405);
               var8 = var1.getlocal(7);
               var1.setlocal(3, var8);
               var4 = null;
            }

            var1.setline(407);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(408);
               var3 = var1.getlocal(2)._add(PyString.fromInterned("."))._add(var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(410);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(413);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _parse_localename$20(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyString.fromInterned(" Parses the locale code for localename and returns the\n        result as tuple (language code, encoding).\n\n        The localename is normalized and passed through the locale\n        alias engine. A ValueError is raised in case the locale name\n        cannot be parsed.\n\n        The language code corresponds to RFC 1766.  code and encoding\n        can be None in case the values cannot be determined or are\n        unknown to this implementation.\n\n    ");
      var1.setline(429);
      PyObject var3 = var1.getglobal("normalize").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(430);
      PyString var6 = PyString.fromInterned("@");
      PyObject var10000 = var6._in(var1.getlocal(1));
      var3 = null;
      PyObject[] var4;
      PyTuple var9;
      if (var10000.__nonzero__()) {
         var1.setline(432);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@"));
         var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(433);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("euro"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var6 = PyString.fromInterned(".");
            var10000 = var6._notin(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(437);
            var9 = new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned("iso-8859-15")});
            var1.f_lasti = -1;
            return var9;
         }
      }

      var1.setline(439);
      PyString var7 = PyString.fromInterned(".");
      var10000 = var7._in(var1.getlocal(1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(440);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(441);
         PyObject var8 = var1.getlocal(1);
         var10000 = var8._eq(PyString.fromInterned("C"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(442);
            var9 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var9;
         } else {
            var1.setline(443);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown locale: %s")._mod(var1.getlocal(0)));
         }
      }
   }

   public PyObject _build_localename$21(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyString.fromInterned(" Builds a locale code from the given tuple (language code,\n        encoding).\n\n        No aliasing or normalizing takes place.\n\n    ");
      var1.setline(453);
      PyObject var3 = var1.getlocal(0);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(454);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(455);
         PyString var6 = PyString.fromInterned("C");
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(456);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(457);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(459);
         var3 = var1.getlocal(1)._add(PyString.fromInterned("."))._add(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getdefaultlocale$22(PyFrame var1, ThreadState var2) {
      var1.setline(483);
      PyString.fromInterned(" Tries to determine the default locale settings and returns\n        them as tuple (language code, encoding).\n\n        According to POSIX, a program which has not called\n        setlocale(LC_ALL, \"\") runs using the portable 'C' locale.\n        Calling setlocale(LC_ALL, \"\") lets it use the default locale as\n        defined by the LANG variable. Since we don't want to interfere\n        with the current locale setting we thus emulate the behavior\n        in the way described above.\n\n        To maintain compatibility with other platforms, not only the\n        LANG variable is tested, but a list of variables given as\n        envvars parameter. The first found to be defined will be\n        used. envvars defaults to the search path used in GNU gettext;\n        it must always contain the variable name 'LANG'.\n\n        Except for the code 'C', the language code corresponds to RFC\n        1766.  code and encoding can be None in case the values cannot\n        be determined.\n\n    ");
      var1.setline(485);
      PyException var4;
      PyTuple var8;
      PyObject var11;
      if (var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(486);
         String[] var9 = new String[]{"Locale"};
         PyObject[] var10 = imp.importFrom("java.util", var9, var1, -1);
         var11 = var10[0];
         var1.setlocal(1, var11);
         var4 = null;
         var1.setline(487);
         var9 = new String[]{"Charset"};
         var10 = imp.importFrom("java.nio.charset", var9, var1, -1);
         var11 = var10[0];
         var1.setlocal(2, var11);
         var4 = null;
         var1.setline(488);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("getDefault").__call__(var2).__getattr__("toString").__call__(var2).__getattr__("__str__").__call__(var2), var1.getlocal(2).__getattr__("defaultCharset").__call__(var2).__getattr__("name").__call__(var2).__getattr__("__str__").__call__(var2)});
         var1.f_lasti = -1;
         return var8;
      } else {
         PyObject var5;
         PyObject var6;
         PyObject var10000;
         try {
            var1.setline(492);
            var11 = imp.importOne("_locale", var1, -1);
            var1.setlocal(3, var11);
            var4 = null;
            var1.setline(493);
            var11 = var1.getlocal(3).__getattr__("_getdefaultlocale").__call__(var2);
            PyObject[] var12 = Py.unpackSequence(var11, 2);
            var6 = var12[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal(5, var6);
            var6 = null;
            var4 = null;
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("AttributeError")}))) {
               var1.setline(495);
               var1.setline(506);
               var11 = imp.importOne("os", var1, -1);
               var1.setlocal(6, var11);
               var4 = null;
               var1.setline(507);
               var11 = var1.getlocal(6).__getattr__("environ").__getattr__("get");
               var1.setlocal(7, var11);
               var4 = null;
               var1.setline(508);
               var11 = var1.getlocal(0).__iter__();

               while(true) {
                  var1.setline(508);
                  var5 = var11.__iternext__();
                  if (var5 == null) {
                     var1.setline(515);
                     PyString var13 = PyString.fromInterned("C");
                     var1.setlocal(9, var13);
                     var6 = null;
                     break;
                  }

                  var1.setlocal(8, var5);
                  var1.setline(509);
                  var6 = var1.getlocal(7).__call__(var2, var1.getlocal(8), var1.getglobal("None"));
                  var1.setlocal(9, var6);
                  var6 = null;
                  var1.setline(510);
                  if (var1.getlocal(9).__nonzero__()) {
                     var1.setline(511);
                     var6 = var1.getlocal(8);
                     var10000 = var6._eq(PyString.fromInterned("LANGUAGE"));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(512);
                        var6 = var1.getlocal(9).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":")).__getitem__(Py.newInteger(0));
                        var1.setlocal(9, var6);
                        var6 = null;
                     }
                     break;
                  }
               }

               var1.setline(516);
               PyObject var3 = var1.getglobal("_parse_localename").__call__(var2, var1.getlocal(9));
               var1.f_lasti = -1;
               return var3;
            }

            throw var4;
         }

         var1.setline(498);
         var5 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var5._eq(PyString.fromInterned("win32"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
               var10000 = var5._eq(PyString.fromInterned("0x"));
               var5 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(500);
            var5 = var1.getglobal("windows_locale").__getattr__("get").__call__(var2, var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0)));
            var1.setlocal(4, var5);
            var5 = null;
         }

         var1.setline(503);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.f_lasti = -1;
         return var8;
      }
   }

   public PyObject getlocale$23(PyFrame var1, ThreadState var2) {
      var1.setline(531);
      PyString.fromInterned(" Returns the current setting for the given locale category as\n        tuple (language code, encoding).\n\n        category may be one of the LC_* value except LC_ALL. It\n        defaults to LC_CTYPE.\n\n        Except for the code 'C', the language code corresponds to RFC\n        1766.  code and encoding can be None in case the values cannot\n        be determined.\n\n    ");
      var1.setline(532);
      PyObject var3 = var1.getglobal("_setlocale").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(533);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getglobal("LC_ALL"));
      var3 = null;
      if (var10000.__nonzero__()) {
         PyString var4 = PyString.fromInterned(";");
         var10000 = var4._in(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(534);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("category LC_ALL is not supported"));
      } else {
         var1.setline(535);
         var3 = var1.getglobal("_parse_localename").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setlocale$24(PyFrame var1, ThreadState var2) {
      var1.setline(548);
      PyString.fromInterned(" Set the locale for the given category.  The locale can be\n        a string, an iterable of two strings (language code and encoding),\n        or None.\n\n        Iterables are converted to strings using the locale aliasing\n        engine.  Locale strings are passed directly to the C lib.\n\n        category may be given as one of the LC_* values.\n\n    ");
      var1.setline(549);
      PyObject var10000 = var1.getlocal(1);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var3._isnot(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(551);
         var3 = var1.getglobal("normalize").__call__(var2, var1.getglobal("_build_localename").__call__(var2, var1.getlocal(1)));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(552);
      var3 = var1.getglobal("_setlocale").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject resetlocale$25(PyFrame var1, ThreadState var2) {
      var1.setline(561);
      PyString.fromInterned(" Sets the locale for category to the default setting.\n\n        The default setting is determined by calling\n        getdefaultlocale(). category defaults to LC_ALL.\n\n    ");
      var1.setline(562);
      var1.getglobal("_setlocale").__call__(var2, var1.getlocal(0), var1.getglobal("_build_localename").__call__(var2, var1.getglobal("getdefaultlocale").__call__(var2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getpreferredencoding$26(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyObject var3 = var1.getglobal("Charset").__getattr__("defaultCharset").__call__(var2).__getattr__("name").__call__(var2).__getattr__("__str__").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getpreferredencoding$27(PyFrame var1, ThreadState var2) {
      var1.setline(571);
      PyString.fromInterned("Return the charset that the user is likely using.");
      var1.setline(572);
      PyObject var3 = imp.importOne("_locale", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(573);
      var3 = var1.getlocal(1).__getattr__("_getdefaultlocale").__call__(var2).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getpreferredencoding$28(PyFrame var1, ThreadState var2) {
      var1.setline(582);
      PyString.fromInterned("Return the charset that the user is likely using,\n            by looking at environment variables.");
      var1.setline(583);
      PyObject var3 = var1.getglobal("getdefaultlocale").__call__(var2).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getpreferredencoding$29(PyFrame var1, ThreadState var2) {
      var1.setline(587);
      PyString.fromInterned("Return the charset that the user is likely using,\n            according to the system configuration.");
      var1.setline(588);
      PyObject var3;
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(589);
         var3 = var1.getglobal("setlocale").__call__(var2, var1.getglobal("LC_CTYPE"));
         var1.setlocal(1, var3);
         var3 = null;

         try {
            var1.setline(591);
            var1.getglobal("setlocale").__call__((ThreadState)var2, (PyObject)var1.getglobal("LC_CTYPE"), (PyObject)PyString.fromInterned(""));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (!var5.match(var1.getglobal("Error"))) {
               throw var5;
            }

            var1.setline(593);
         }

         var1.setline(594);
         var3 = var1.getglobal("nl_langinfo").__call__(var2, var1.getglobal("CODESET"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(595);
         var1.getglobal("setlocale").__call__(var2, var1.getglobal("LC_CTYPE"), var1.getlocal(1));
         var1.setline(596);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(598);
         var3 = var1.getglobal("nl_langinfo").__call__(var2, var1.getglobal("CODESET"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   private static void set$$0(PyObject[] var0) {
      var0[0] = PyString.fromInterned("a3");
      var0[1] = PyString.fromInterned("a3_AZ.KOI8-C");
      var0[2] = PyString.fromInterned("a3_az");
      var0[3] = PyString.fromInterned("a3_AZ.KOI8-C");
      var0[4] = PyString.fromInterned("a3_az.koi8c");
      var0[5] = PyString.fromInterned("a3_AZ.KOI8-C");
      var0[6] = PyString.fromInterned("af");
      var0[7] = PyString.fromInterned("af_ZA.ISO8859-1");
      var0[8] = PyString.fromInterned("af_za");
      var0[9] = PyString.fromInterned("af_ZA.ISO8859-1");
      var0[10] = PyString.fromInterned("af_za.iso88591");
      var0[11] = PyString.fromInterned("af_ZA.ISO8859-1");
      var0[12] = PyString.fromInterned("am");
      var0[13] = PyString.fromInterned("am_ET.UTF-8");
      var0[14] = PyString.fromInterned("am_et");
      var0[15] = PyString.fromInterned("am_ET.UTF-8");
      var0[16] = PyString.fromInterned("american");
      var0[17] = PyString.fromInterned("en_US.ISO8859-1");
      var0[18] = PyString.fromInterned("american.iso88591");
      var0[19] = PyString.fromInterned("en_US.ISO8859-1");
      var0[20] = PyString.fromInterned("ar");
      var0[21] = PyString.fromInterned("ar_AA.ISO8859-6");
      var0[22] = PyString.fromInterned("ar_aa");
      var0[23] = PyString.fromInterned("ar_AA.ISO8859-6");
      var0[24] = PyString.fromInterned("ar_aa.iso88596");
      var0[25] = PyString.fromInterned("ar_AA.ISO8859-6");
      var0[26] = PyString.fromInterned("ar_ae");
      var0[27] = PyString.fromInterned("ar_AE.ISO8859-6");
      var0[28] = PyString.fromInterned("ar_ae.iso88596");
      var0[29] = PyString.fromInterned("ar_AE.ISO8859-6");
      var0[30] = PyString.fromInterned("ar_bh");
      var0[31] = PyString.fromInterned("ar_BH.ISO8859-6");
      var0[32] = PyString.fromInterned("ar_bh.iso88596");
      var0[33] = PyString.fromInterned("ar_BH.ISO8859-6");
      var0[34] = PyString.fromInterned("ar_dz");
      var0[35] = PyString.fromInterned("ar_DZ.ISO8859-6");
      var0[36] = PyString.fromInterned("ar_dz.iso88596");
      var0[37] = PyString.fromInterned("ar_DZ.ISO8859-6");
      var0[38] = PyString.fromInterned("ar_eg");
      var0[39] = PyString.fromInterned("ar_EG.ISO8859-6");
      var0[40] = PyString.fromInterned("ar_eg.iso88596");
      var0[41] = PyString.fromInterned("ar_EG.ISO8859-6");
      var0[42] = PyString.fromInterned("ar_iq");
      var0[43] = PyString.fromInterned("ar_IQ.ISO8859-6");
      var0[44] = PyString.fromInterned("ar_iq.iso88596");
      var0[45] = PyString.fromInterned("ar_IQ.ISO8859-6");
      var0[46] = PyString.fromInterned("ar_jo");
      var0[47] = PyString.fromInterned("ar_JO.ISO8859-6");
      var0[48] = PyString.fromInterned("ar_jo.iso88596");
      var0[49] = PyString.fromInterned("ar_JO.ISO8859-6");
      var0[50] = PyString.fromInterned("ar_kw");
      var0[51] = PyString.fromInterned("ar_KW.ISO8859-6");
      var0[52] = PyString.fromInterned("ar_kw.iso88596");
      var0[53] = PyString.fromInterned("ar_KW.ISO8859-6");
      var0[54] = PyString.fromInterned("ar_lb");
      var0[55] = PyString.fromInterned("ar_LB.ISO8859-6");
      var0[56] = PyString.fromInterned("ar_lb.iso88596");
      var0[57] = PyString.fromInterned("ar_LB.ISO8859-6");
      var0[58] = PyString.fromInterned("ar_ly");
      var0[59] = PyString.fromInterned("ar_LY.ISO8859-6");
      var0[60] = PyString.fromInterned("ar_ly.iso88596");
      var0[61] = PyString.fromInterned("ar_LY.ISO8859-6");
      var0[62] = PyString.fromInterned("ar_ma");
      var0[63] = PyString.fromInterned("ar_MA.ISO8859-6");
      var0[64] = PyString.fromInterned("ar_ma.iso88596");
      var0[65] = PyString.fromInterned("ar_MA.ISO8859-6");
      var0[66] = PyString.fromInterned("ar_om");
      var0[67] = PyString.fromInterned("ar_OM.ISO8859-6");
      var0[68] = PyString.fromInterned("ar_om.iso88596");
      var0[69] = PyString.fromInterned("ar_OM.ISO8859-6");
      var0[70] = PyString.fromInterned("ar_qa");
      var0[71] = PyString.fromInterned("ar_QA.ISO8859-6");
      var0[72] = PyString.fromInterned("ar_qa.iso88596");
      var0[73] = PyString.fromInterned("ar_QA.ISO8859-6");
      var0[74] = PyString.fromInterned("ar_sa");
      var0[75] = PyString.fromInterned("ar_SA.ISO8859-6");
      var0[76] = PyString.fromInterned("ar_sa.iso88596");
      var0[77] = PyString.fromInterned("ar_SA.ISO8859-6");
      var0[78] = PyString.fromInterned("ar_sd");
      var0[79] = PyString.fromInterned("ar_SD.ISO8859-6");
      var0[80] = PyString.fromInterned("ar_sd.iso88596");
      var0[81] = PyString.fromInterned("ar_SD.ISO8859-6");
      var0[82] = PyString.fromInterned("ar_sy");
      var0[83] = PyString.fromInterned("ar_SY.ISO8859-6");
      var0[84] = PyString.fromInterned("ar_sy.iso88596");
      var0[85] = PyString.fromInterned("ar_SY.ISO8859-6");
      var0[86] = PyString.fromInterned("ar_tn");
      var0[87] = PyString.fromInterned("ar_TN.ISO8859-6");
      var0[88] = PyString.fromInterned("ar_tn.iso88596");
      var0[89] = PyString.fromInterned("ar_TN.ISO8859-6");
      var0[90] = PyString.fromInterned("ar_ye");
      var0[91] = PyString.fromInterned("ar_YE.ISO8859-6");
      var0[92] = PyString.fromInterned("ar_ye.iso88596");
      var0[93] = PyString.fromInterned("ar_YE.ISO8859-6");
      var0[94] = PyString.fromInterned("arabic");
      var0[95] = PyString.fromInterned("ar_AA.ISO8859-6");
      var0[96] = PyString.fromInterned("arabic.iso88596");
      var0[97] = PyString.fromInterned("ar_AA.ISO8859-6");
      var0[98] = PyString.fromInterned("as");
      var0[99] = PyString.fromInterned("as_IN.UTF-8");
      var0[100] = PyString.fromInterned("az");
      var0[101] = PyString.fromInterned("az_AZ.ISO8859-9E");
      var0[102] = PyString.fromInterned("az_az");
      var0[103] = PyString.fromInterned("az_AZ.ISO8859-9E");
      var0[104] = PyString.fromInterned("az_az.iso88599e");
      var0[105] = PyString.fromInterned("az_AZ.ISO8859-9E");
      var0[106] = PyString.fromInterned("be");
      var0[107] = PyString.fromInterned("be_BY.CP1251");
      var0[108] = PyString.fromInterned("be@latin");
      var0[109] = PyString.fromInterned("be_BY.UTF-8@latin");
      var0[110] = PyString.fromInterned("be_by");
      var0[111] = PyString.fromInterned("be_BY.CP1251");
      var0[112] = PyString.fromInterned("be_by.cp1251");
      var0[113] = PyString.fromInterned("be_BY.CP1251");
      var0[114] = PyString.fromInterned("be_by.microsoftcp1251");
      var0[115] = PyString.fromInterned("be_BY.CP1251");
      var0[116] = PyString.fromInterned("be_by.utf8@latin");
      var0[117] = PyString.fromInterned("be_BY.UTF-8@latin");
      var0[118] = PyString.fromInterned("be_by@latin");
      var0[119] = PyString.fromInterned("be_BY.UTF-8@latin");
      var0[120] = PyString.fromInterned("bg");
      var0[121] = PyString.fromInterned("bg_BG.CP1251");
      var0[122] = PyString.fromInterned("bg_bg");
      var0[123] = PyString.fromInterned("bg_BG.CP1251");
      var0[124] = PyString.fromInterned("bg_bg.cp1251");
      var0[125] = PyString.fromInterned("bg_BG.CP1251");
      var0[126] = PyString.fromInterned("bg_bg.iso88595");
      var0[127] = PyString.fromInterned("bg_BG.ISO8859-5");
      var0[128] = PyString.fromInterned("bg_bg.koi8r");
      var0[129] = PyString.fromInterned("bg_BG.KOI8-R");
      var0[130] = PyString.fromInterned("bg_bg.microsoftcp1251");
      var0[131] = PyString.fromInterned("bg_BG.CP1251");
      var0[132] = PyString.fromInterned("bn_in");
      var0[133] = PyString.fromInterned("bn_IN.UTF-8");
      var0[134] = PyString.fromInterned("bokmal");
      var0[135] = PyString.fromInterned("nb_NO.ISO8859-1");
      var0[136] = PyString.fromInterned("bokmål");
      var0[137] = PyString.fromInterned("nb_NO.ISO8859-1");
      var0[138] = PyString.fromInterned("br");
      var0[139] = PyString.fromInterned("br_FR.ISO8859-1");
      var0[140] = PyString.fromInterned("br_fr");
      var0[141] = PyString.fromInterned("br_FR.ISO8859-1");
      var0[142] = PyString.fromInterned("br_fr.iso88591");
      var0[143] = PyString.fromInterned("br_FR.ISO8859-1");
      var0[144] = PyString.fromInterned("br_fr.iso885914");
      var0[145] = PyString.fromInterned("br_FR.ISO8859-14");
      var0[146] = PyString.fromInterned("br_fr.iso885915");
      var0[147] = PyString.fromInterned("br_FR.ISO8859-15");
      var0[148] = PyString.fromInterned("br_fr.iso885915@euro");
      var0[149] = PyString.fromInterned("br_FR.ISO8859-15");
      var0[150] = PyString.fromInterned("br_fr.utf8@euro");
      var0[151] = PyString.fromInterned("br_FR.UTF-8");
      var0[152] = PyString.fromInterned("br_fr@euro");
      var0[153] = PyString.fromInterned("br_FR.ISO8859-15");
      var0[154] = PyString.fromInterned("bs");
      var0[155] = PyString.fromInterned("bs_BA.ISO8859-2");
      var0[156] = PyString.fromInterned("bs_ba");
      var0[157] = PyString.fromInterned("bs_BA.ISO8859-2");
      var0[158] = PyString.fromInterned("bs_ba.iso88592");
      var0[159] = PyString.fromInterned("bs_BA.ISO8859-2");
      var0[160] = PyString.fromInterned("bulgarian");
      var0[161] = PyString.fromInterned("bg_BG.CP1251");
      var0[162] = PyString.fromInterned("c");
      var0[163] = PyString.fromInterned("C");
      var0[164] = PyString.fromInterned("c-french");
      var0[165] = PyString.fromInterned("fr_CA.ISO8859-1");
      var0[166] = PyString.fromInterned("c-french.iso88591");
      var0[167] = PyString.fromInterned("fr_CA.ISO8859-1");
      var0[168] = PyString.fromInterned("c.en");
      var0[169] = PyString.fromInterned("C");
      var0[170] = PyString.fromInterned("c.iso88591");
      var0[171] = PyString.fromInterned("en_US.ISO8859-1");
      var0[172] = PyString.fromInterned("c_c");
      var0[173] = PyString.fromInterned("C");
      var0[174] = PyString.fromInterned("c_c.c");
      var0[175] = PyString.fromInterned("C");
      var0[176] = PyString.fromInterned("ca");
      var0[177] = PyString.fromInterned("ca_ES.ISO8859-1");
      var0[178] = PyString.fromInterned("ca_ad");
      var0[179] = PyString.fromInterned("ca_AD.ISO8859-1");
      var0[180] = PyString.fromInterned("ca_ad.iso88591");
      var0[181] = PyString.fromInterned("ca_AD.ISO8859-1");
      var0[182] = PyString.fromInterned("ca_ad.iso885915");
      var0[183] = PyString.fromInterned("ca_AD.ISO8859-15");
      var0[184] = PyString.fromInterned("ca_ad.iso885915@euro");
      var0[185] = PyString.fromInterned("ca_AD.ISO8859-15");
      var0[186] = PyString.fromInterned("ca_ad.utf8@euro");
      var0[187] = PyString.fromInterned("ca_AD.UTF-8");
      var0[188] = PyString.fromInterned("ca_ad@euro");
      var0[189] = PyString.fromInterned("ca_AD.ISO8859-15");
      var0[190] = PyString.fromInterned("ca_es");
      var0[191] = PyString.fromInterned("ca_ES.ISO8859-1");
      var0[192] = PyString.fromInterned("ca_es.iso88591");
      var0[193] = PyString.fromInterned("ca_ES.ISO8859-1");
      var0[194] = PyString.fromInterned("ca_es.iso885915");
      var0[195] = PyString.fromInterned("ca_ES.ISO8859-15");
      var0[196] = PyString.fromInterned("ca_es.iso885915@euro");
      var0[197] = PyString.fromInterned("ca_ES.ISO8859-15");
      var0[198] = PyString.fromInterned("ca_es.utf8@euro");
      var0[199] = PyString.fromInterned("ca_ES.UTF-8");
      var0[200] = PyString.fromInterned("ca_es@euro");
      var0[201] = PyString.fromInterned("ca_ES.ISO8859-15");
      var0[202] = PyString.fromInterned("ca_fr");
      var0[203] = PyString.fromInterned("ca_FR.ISO8859-1");
      var0[204] = PyString.fromInterned("ca_fr.iso88591");
      var0[205] = PyString.fromInterned("ca_FR.ISO8859-1");
      var0[206] = PyString.fromInterned("ca_fr.iso885915");
      var0[207] = PyString.fromInterned("ca_FR.ISO8859-15");
      var0[208] = PyString.fromInterned("ca_fr.iso885915@euro");
      var0[209] = PyString.fromInterned("ca_FR.ISO8859-15");
      var0[210] = PyString.fromInterned("ca_fr.utf8@euro");
      var0[211] = PyString.fromInterned("ca_FR.UTF-8");
      var0[212] = PyString.fromInterned("ca_fr@euro");
      var0[213] = PyString.fromInterned("ca_FR.ISO8859-15");
      var0[214] = PyString.fromInterned("ca_it");
      var0[215] = PyString.fromInterned("ca_IT.ISO8859-1");
      var0[216] = PyString.fromInterned("ca_it.iso88591");
      var0[217] = PyString.fromInterned("ca_IT.ISO8859-1");
      var0[218] = PyString.fromInterned("ca_it.iso885915");
      var0[219] = PyString.fromInterned("ca_IT.ISO8859-15");
      var0[220] = PyString.fromInterned("ca_it.iso885915@euro");
      var0[221] = PyString.fromInterned("ca_IT.ISO8859-15");
      var0[222] = PyString.fromInterned("ca_it.utf8@euro");
      var0[223] = PyString.fromInterned("ca_IT.UTF-8");
      var0[224] = PyString.fromInterned("ca_it@euro");
      var0[225] = PyString.fromInterned("ca_IT.ISO8859-15");
      var0[226] = PyString.fromInterned("catalan");
      var0[227] = PyString.fromInterned("ca_ES.ISO8859-1");
      var0[228] = PyString.fromInterned("cextend");
      var0[229] = PyString.fromInterned("en_US.ISO8859-1");
      var0[230] = PyString.fromInterned("cextend.en");
      var0[231] = PyString.fromInterned("en_US.ISO8859-1");
      var0[232] = PyString.fromInterned("chinese-s");
      var0[233] = PyString.fromInterned("zh_CN.eucCN");
      var0[234] = PyString.fromInterned("chinese-t");
      var0[235] = PyString.fromInterned("zh_TW.eucTW");
      var0[236] = PyString.fromInterned("croatian");
      var0[237] = PyString.fromInterned("hr_HR.ISO8859-2");
      var0[238] = PyString.fromInterned("cs");
      var0[239] = PyString.fromInterned("cs_CZ.ISO8859-2");
      var0[240] = PyString.fromInterned("cs_cs");
      var0[241] = PyString.fromInterned("cs_CZ.ISO8859-2");
      var0[242] = PyString.fromInterned("cs_cs.iso88592");
      var0[243] = PyString.fromInterned("cs_CS.ISO8859-2");
      var0[244] = PyString.fromInterned("cs_cz");
      var0[245] = PyString.fromInterned("cs_CZ.ISO8859-2");
      var0[246] = PyString.fromInterned("cs_cz.iso88592");
      var0[247] = PyString.fromInterned("cs_CZ.ISO8859-2");
      var0[248] = PyString.fromInterned("cy");
      var0[249] = PyString.fromInterned("cy_GB.ISO8859-1");
      var0[250] = PyString.fromInterned("cy_gb");
      var0[251] = PyString.fromInterned("cy_GB.ISO8859-1");
      var0[252] = PyString.fromInterned("cy_gb.iso88591");
      var0[253] = PyString.fromInterned("cy_GB.ISO8859-1");
      var0[254] = PyString.fromInterned("cy_gb.iso885914");
      var0[255] = PyString.fromInterned("cy_GB.ISO8859-14");
      var0[256] = PyString.fromInterned("cy_gb.iso885915");
      var0[257] = PyString.fromInterned("cy_GB.ISO8859-15");
      var0[258] = PyString.fromInterned("cy_gb@euro");
      var0[259] = PyString.fromInterned("cy_GB.ISO8859-15");
      var0[260] = PyString.fromInterned("cz");
      var0[261] = PyString.fromInterned("cs_CZ.ISO8859-2");
      var0[262] = PyString.fromInterned("cz_cz");
      var0[263] = PyString.fromInterned("cs_CZ.ISO8859-2");
      var0[264] = PyString.fromInterned("czech");
      var0[265] = PyString.fromInterned("cs_CZ.ISO8859-2");
      var0[266] = PyString.fromInterned("da");
      var0[267] = PyString.fromInterned("da_DK.ISO8859-1");
      var0[268] = PyString.fromInterned("da.iso885915");
      var0[269] = PyString.fromInterned("da_DK.ISO8859-15");
      var0[270] = PyString.fromInterned("da_dk");
      var0[271] = PyString.fromInterned("da_DK.ISO8859-1");
      var0[272] = PyString.fromInterned("da_dk.88591");
      var0[273] = PyString.fromInterned("da_DK.ISO8859-1");
      var0[274] = PyString.fromInterned("da_dk.885915");
      var0[275] = PyString.fromInterned("da_DK.ISO8859-15");
      var0[276] = PyString.fromInterned("da_dk.iso88591");
      var0[277] = PyString.fromInterned("da_DK.ISO8859-1");
      var0[278] = PyString.fromInterned("da_dk.iso885915");
      var0[279] = PyString.fromInterned("da_DK.ISO8859-15");
      var0[280] = PyString.fromInterned("da_dk@euro");
      var0[281] = PyString.fromInterned("da_DK.ISO8859-15");
      var0[282] = PyString.fromInterned("danish");
      var0[283] = PyString.fromInterned("da_DK.ISO8859-1");
      var0[284] = PyString.fromInterned("danish.iso88591");
      var0[285] = PyString.fromInterned("da_DK.ISO8859-1");
      var0[286] = PyString.fromInterned("dansk");
      var0[287] = PyString.fromInterned("da_DK.ISO8859-1");
      var0[288] = PyString.fromInterned("de");
      var0[289] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[290] = PyString.fromInterned("de.iso885915");
      var0[291] = PyString.fromInterned("de_DE.ISO8859-15");
      var0[292] = PyString.fromInterned("de_at");
      var0[293] = PyString.fromInterned("de_AT.ISO8859-1");
      var0[294] = PyString.fromInterned("de_at.iso88591");
      var0[295] = PyString.fromInterned("de_AT.ISO8859-1");
      var0[296] = PyString.fromInterned("de_at.iso885915");
      var0[297] = PyString.fromInterned("de_AT.ISO8859-15");
      var0[298] = PyString.fromInterned("de_at.iso885915@euro");
      var0[299] = PyString.fromInterned("de_AT.ISO8859-15");
      var0[300] = PyString.fromInterned("de_at.utf8@euro");
      var0[301] = PyString.fromInterned("de_AT.UTF-8");
      var0[302] = PyString.fromInterned("de_at@euro");
      var0[303] = PyString.fromInterned("de_AT.ISO8859-15");
      var0[304] = PyString.fromInterned("de_be");
      var0[305] = PyString.fromInterned("de_BE.ISO8859-1");
      var0[306] = PyString.fromInterned("de_be.iso88591");
      var0[307] = PyString.fromInterned("de_BE.ISO8859-1");
      var0[308] = PyString.fromInterned("de_be.iso885915");
      var0[309] = PyString.fromInterned("de_BE.ISO8859-15");
      var0[310] = PyString.fromInterned("de_be.iso885915@euro");
      var0[311] = PyString.fromInterned("de_BE.ISO8859-15");
      var0[312] = PyString.fromInterned("de_be.utf8@euro");
      var0[313] = PyString.fromInterned("de_BE.UTF-8");
      var0[314] = PyString.fromInterned("de_be@euro");
      var0[315] = PyString.fromInterned("de_BE.ISO8859-15");
      var0[316] = PyString.fromInterned("de_ch");
      var0[317] = PyString.fromInterned("de_CH.ISO8859-1");
      var0[318] = PyString.fromInterned("de_ch.iso88591");
      var0[319] = PyString.fromInterned("de_CH.ISO8859-1");
      var0[320] = PyString.fromInterned("de_ch.iso885915");
      var0[321] = PyString.fromInterned("de_CH.ISO8859-15");
      var0[322] = PyString.fromInterned("de_ch@euro");
      var0[323] = PyString.fromInterned("de_CH.ISO8859-15");
      var0[324] = PyString.fromInterned("de_de");
      var0[325] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[326] = PyString.fromInterned("de_de.88591");
      var0[327] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[328] = PyString.fromInterned("de_de.885915");
      var0[329] = PyString.fromInterned("de_DE.ISO8859-15");
      var0[330] = PyString.fromInterned("de_de.885915@euro");
      var0[331] = PyString.fromInterned("de_DE.ISO8859-15");
      var0[332] = PyString.fromInterned("de_de.iso88591");
      var0[333] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[334] = PyString.fromInterned("de_de.iso885915");
      var0[335] = PyString.fromInterned("de_DE.ISO8859-15");
      var0[336] = PyString.fromInterned("de_de.iso885915@euro");
      var0[337] = PyString.fromInterned("de_DE.ISO8859-15");
      var0[338] = PyString.fromInterned("de_de.utf8@euro");
      var0[339] = PyString.fromInterned("de_DE.UTF-8");
      var0[340] = PyString.fromInterned("de_de@euro");
      var0[341] = PyString.fromInterned("de_DE.ISO8859-15");
      var0[342] = PyString.fromInterned("de_lu");
      var0[343] = PyString.fromInterned("de_LU.ISO8859-1");
      var0[344] = PyString.fromInterned("de_lu.iso88591");
      var0[345] = PyString.fromInterned("de_LU.ISO8859-1");
      var0[346] = PyString.fromInterned("de_lu.iso885915");
      var0[347] = PyString.fromInterned("de_LU.ISO8859-15");
      var0[348] = PyString.fromInterned("de_lu.iso885915@euro");
      var0[349] = PyString.fromInterned("de_LU.ISO8859-15");
      var0[350] = PyString.fromInterned("de_lu.utf8@euro");
      var0[351] = PyString.fromInterned("de_LU.UTF-8");
      var0[352] = PyString.fromInterned("de_lu@euro");
      var0[353] = PyString.fromInterned("de_LU.ISO8859-15");
      var0[354] = PyString.fromInterned("deutsch");
      var0[355] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[356] = PyString.fromInterned("dutch");
      var0[357] = PyString.fromInterned("nl_NL.ISO8859-1");
      var0[358] = PyString.fromInterned("dutch.iso88591");
      var0[359] = PyString.fromInterned("nl_BE.ISO8859-1");
      var0[360] = PyString.fromInterned("ee");
      var0[361] = PyString.fromInterned("ee_EE.ISO8859-4");
      var0[362] = PyString.fromInterned("ee_ee");
      var0[363] = PyString.fromInterned("ee_EE.ISO8859-4");
      var0[364] = PyString.fromInterned("ee_ee.iso88594");
      var0[365] = PyString.fromInterned("ee_EE.ISO8859-4");
      var0[366] = PyString.fromInterned("eesti");
      var0[367] = PyString.fromInterned("et_EE.ISO8859-1");
      var0[368] = PyString.fromInterned("el");
      var0[369] = PyString.fromInterned("el_GR.ISO8859-7");
      var0[370] = PyString.fromInterned("el_gr");
      var0[371] = PyString.fromInterned("el_GR.ISO8859-7");
      var0[372] = PyString.fromInterned("el_gr.iso88597");
      var0[373] = PyString.fromInterned("el_GR.ISO8859-7");
      var0[374] = PyString.fromInterned("el_gr@euro");
      var0[375] = PyString.fromInterned("el_GR.ISO8859-15");
      var0[376] = PyString.fromInterned("en");
      var0[377] = PyString.fromInterned("en_US.ISO8859-1");
      var0[378] = PyString.fromInterned("en.iso88591");
      var0[379] = PyString.fromInterned("en_US.ISO8859-1");
      var0[380] = PyString.fromInterned("en_au");
      var0[381] = PyString.fromInterned("en_AU.ISO8859-1");
      var0[382] = PyString.fromInterned("en_au.iso88591");
      var0[383] = PyString.fromInterned("en_AU.ISO8859-1");
      var0[384] = PyString.fromInterned("en_be");
      var0[385] = PyString.fromInterned("en_BE.ISO8859-1");
      var0[386] = PyString.fromInterned("en_be@euro");
      var0[387] = PyString.fromInterned("en_BE.ISO8859-15");
      var0[388] = PyString.fromInterned("en_bw");
      var0[389] = PyString.fromInterned("en_BW.ISO8859-1");
      var0[390] = PyString.fromInterned("en_bw.iso88591");
      var0[391] = PyString.fromInterned("en_BW.ISO8859-1");
      var0[392] = PyString.fromInterned("en_ca");
      var0[393] = PyString.fromInterned("en_CA.ISO8859-1");
      var0[394] = PyString.fromInterned("en_ca.iso88591");
      var0[395] = PyString.fromInterned("en_CA.ISO8859-1");
      var0[396] = PyString.fromInterned("en_gb");
      var0[397] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[398] = PyString.fromInterned("en_gb.88591");
      var0[399] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[400] = PyString.fromInterned("en_gb.iso88591");
      var0[401] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[402] = PyString.fromInterned("en_gb.iso885915");
      var0[403] = PyString.fromInterned("en_GB.ISO8859-15");
      var0[404] = PyString.fromInterned("en_gb@euro");
      var0[405] = PyString.fromInterned("en_GB.ISO8859-15");
      var0[406] = PyString.fromInterned("en_hk");
      var0[407] = PyString.fromInterned("en_HK.ISO8859-1");
      var0[408] = PyString.fromInterned("en_hk.iso88591");
      var0[409] = PyString.fromInterned("en_HK.ISO8859-1");
      var0[410] = PyString.fromInterned("en_ie");
      var0[411] = PyString.fromInterned("en_IE.ISO8859-1");
      var0[412] = PyString.fromInterned("en_ie.iso88591");
      var0[413] = PyString.fromInterned("en_IE.ISO8859-1");
      var0[414] = PyString.fromInterned("en_ie.iso885915");
      var0[415] = PyString.fromInterned("en_IE.ISO8859-15");
      var0[416] = PyString.fromInterned("en_ie.iso885915@euro");
      var0[417] = PyString.fromInterned("en_IE.ISO8859-15");
      var0[418] = PyString.fromInterned("en_ie.utf8@euro");
      var0[419] = PyString.fromInterned("en_IE.UTF-8");
      var0[420] = PyString.fromInterned("en_ie@euro");
      var0[421] = PyString.fromInterned("en_IE.ISO8859-15");
      var0[422] = PyString.fromInterned("en_in");
      var0[423] = PyString.fromInterned("en_IN.ISO8859-1");
      var0[424] = PyString.fromInterned("en_nz");
      var0[425] = PyString.fromInterned("en_NZ.ISO8859-1");
      var0[426] = PyString.fromInterned("en_nz.iso88591");
      var0[427] = PyString.fromInterned("en_NZ.ISO8859-1");
      var0[428] = PyString.fromInterned("en_ph");
      var0[429] = PyString.fromInterned("en_PH.ISO8859-1");
      var0[430] = PyString.fromInterned("en_ph.iso88591");
      var0[431] = PyString.fromInterned("en_PH.ISO8859-1");
      var0[432] = PyString.fromInterned("en_sg");
      var0[433] = PyString.fromInterned("en_SG.ISO8859-1");
      var0[434] = PyString.fromInterned("en_sg.iso88591");
      var0[435] = PyString.fromInterned("en_SG.ISO8859-1");
      var0[436] = PyString.fromInterned("en_uk");
      var0[437] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[438] = PyString.fromInterned("en_us");
      var0[439] = PyString.fromInterned("en_US.ISO8859-1");
      var0[440] = PyString.fromInterned("en_us.88591");
      var0[441] = PyString.fromInterned("en_US.ISO8859-1");
      var0[442] = PyString.fromInterned("en_us.885915");
      var0[443] = PyString.fromInterned("en_US.ISO8859-15");
      var0[444] = PyString.fromInterned("en_us.iso88591");
      var0[445] = PyString.fromInterned("en_US.ISO8859-1");
      var0[446] = PyString.fromInterned("en_us.iso885915");
      var0[447] = PyString.fromInterned("en_US.ISO8859-15");
      var0[448] = PyString.fromInterned("en_us.iso885915@euro");
      var0[449] = PyString.fromInterned("en_US.ISO8859-15");
      var0[450] = PyString.fromInterned("en_us@euro");
      var0[451] = PyString.fromInterned("en_US.ISO8859-15");
      var0[452] = PyString.fromInterned("en_us@euro@euro");
      var0[453] = PyString.fromInterned("en_US.ISO8859-15");
      var0[454] = PyString.fromInterned("en_za");
      var0[455] = PyString.fromInterned("en_ZA.ISO8859-1");
      var0[456] = PyString.fromInterned("en_za.88591");
      var0[457] = PyString.fromInterned("en_ZA.ISO8859-1");
      var0[458] = PyString.fromInterned("en_za.iso88591");
      var0[459] = PyString.fromInterned("en_ZA.ISO8859-1");
      var0[460] = PyString.fromInterned("en_za.iso885915");
      var0[461] = PyString.fromInterned("en_ZA.ISO8859-15");
      var0[462] = PyString.fromInterned("en_za@euro");
      var0[463] = PyString.fromInterned("en_ZA.ISO8859-15");
      var0[464] = PyString.fromInterned("en_zw");
      var0[465] = PyString.fromInterned("en_ZW.ISO8859-1");
      var0[466] = PyString.fromInterned("en_zw.iso88591");
      var0[467] = PyString.fromInterned("en_ZW.ISO8859-1");
      var0[468] = PyString.fromInterned("eng_gb");
      var0[469] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[470] = PyString.fromInterned("eng_gb.8859");
      var0[471] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[472] = PyString.fromInterned("english");
      var0[473] = PyString.fromInterned("en_EN.ISO8859-1");
      var0[474] = PyString.fromInterned("english.iso88591");
      var0[475] = PyString.fromInterned("en_EN.ISO8859-1");
      var0[476] = PyString.fromInterned("english_uk");
      var0[477] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[478] = PyString.fromInterned("english_uk.8859");
      var0[479] = PyString.fromInterned("en_GB.ISO8859-1");
      var0[480] = PyString.fromInterned("english_united-states");
      var0[481] = PyString.fromInterned("en_US.ISO8859-1");
      var0[482] = PyString.fromInterned("english_united-states.437");
      var0[483] = PyString.fromInterned("C");
      var0[484] = PyString.fromInterned("english_us");
      var0[485] = PyString.fromInterned("en_US.ISO8859-1");
      var0[486] = PyString.fromInterned("english_us.8859");
      var0[487] = PyString.fromInterned("en_US.ISO8859-1");
      var0[488] = PyString.fromInterned("english_us.ascii");
      var0[489] = PyString.fromInterned("en_US.ISO8859-1");
      var0[490] = PyString.fromInterned("eo");
      var0[491] = PyString.fromInterned("eo_XX.ISO8859-3");
      var0[492] = PyString.fromInterned("eo_eo");
      var0[493] = PyString.fromInterned("eo_EO.ISO8859-3");
      var0[494] = PyString.fromInterned("eo_eo.iso88593");
      var0[495] = PyString.fromInterned("eo_EO.ISO8859-3");
      var0[496] = PyString.fromInterned("eo_xx");
      var0[497] = PyString.fromInterned("eo_XX.ISO8859-3");
      var0[498] = PyString.fromInterned("eo_xx.iso88593");
      var0[499] = PyString.fromInterned("eo_XX.ISO8859-3");
      var0[500] = PyString.fromInterned("es");
      var0[501] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[502] = PyString.fromInterned("es_ar");
      var0[503] = PyString.fromInterned("es_AR.ISO8859-1");
      var0[504] = PyString.fromInterned("es_ar.iso88591");
      var0[505] = PyString.fromInterned("es_AR.ISO8859-1");
      var0[506] = PyString.fromInterned("es_bo");
      var0[507] = PyString.fromInterned("es_BO.ISO8859-1");
      var0[508] = PyString.fromInterned("es_bo.iso88591");
      var0[509] = PyString.fromInterned("es_BO.ISO8859-1");
      var0[510] = PyString.fromInterned("es_cl");
      var0[511] = PyString.fromInterned("es_CL.ISO8859-1");
      var0[512] = PyString.fromInterned("es_cl.iso88591");
      var0[513] = PyString.fromInterned("es_CL.ISO8859-1");
      var0[514] = PyString.fromInterned("es_co");
      var0[515] = PyString.fromInterned("es_CO.ISO8859-1");
      var0[516] = PyString.fromInterned("es_co.iso88591");
      var0[517] = PyString.fromInterned("es_CO.ISO8859-1");
      var0[518] = PyString.fromInterned("es_cr");
      var0[519] = PyString.fromInterned("es_CR.ISO8859-1");
      var0[520] = PyString.fromInterned("es_cr.iso88591");
      var0[521] = PyString.fromInterned("es_CR.ISO8859-1");
      var0[522] = PyString.fromInterned("es_do");
      var0[523] = PyString.fromInterned("es_DO.ISO8859-1");
      var0[524] = PyString.fromInterned("es_do.iso88591");
      var0[525] = PyString.fromInterned("es_DO.ISO8859-1");
      var0[526] = PyString.fromInterned("es_ec");
      var0[527] = PyString.fromInterned("es_EC.ISO8859-1");
      var0[528] = PyString.fromInterned("es_ec.iso88591");
      var0[529] = PyString.fromInterned("es_EC.ISO8859-1");
      var0[530] = PyString.fromInterned("es_es");
      var0[531] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[532] = PyString.fromInterned("es_es.88591");
      var0[533] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[534] = PyString.fromInterned("es_es.iso88591");
      var0[535] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[536] = PyString.fromInterned("es_es.iso885915");
      var0[537] = PyString.fromInterned("es_ES.ISO8859-15");
      var0[538] = PyString.fromInterned("es_es.iso885915@euro");
      var0[539] = PyString.fromInterned("es_ES.ISO8859-15");
      var0[540] = PyString.fromInterned("es_es.utf8@euro");
      var0[541] = PyString.fromInterned("es_ES.UTF-8");
      var0[542] = PyString.fromInterned("es_es@euro");
      var0[543] = PyString.fromInterned("es_ES.ISO8859-15");
      var0[544] = PyString.fromInterned("es_gt");
      var0[545] = PyString.fromInterned("es_GT.ISO8859-1");
      var0[546] = PyString.fromInterned("es_gt.iso88591");
      var0[547] = PyString.fromInterned("es_GT.ISO8859-1");
      var0[548] = PyString.fromInterned("es_hn");
      var0[549] = PyString.fromInterned("es_HN.ISO8859-1");
      var0[550] = PyString.fromInterned("es_hn.iso88591");
      var0[551] = PyString.fromInterned("es_HN.ISO8859-1");
      var0[552] = PyString.fromInterned("es_mx");
      var0[553] = PyString.fromInterned("es_MX.ISO8859-1");
      var0[554] = PyString.fromInterned("es_mx.iso88591");
      var0[555] = PyString.fromInterned("es_MX.ISO8859-1");
      var0[556] = PyString.fromInterned("es_ni");
      var0[557] = PyString.fromInterned("es_NI.ISO8859-1");
      var0[558] = PyString.fromInterned("es_ni.iso88591");
      var0[559] = PyString.fromInterned("es_NI.ISO8859-1");
      var0[560] = PyString.fromInterned("es_pa");
      var0[561] = PyString.fromInterned("es_PA.ISO8859-1");
      var0[562] = PyString.fromInterned("es_pa.iso88591");
      var0[563] = PyString.fromInterned("es_PA.ISO8859-1");
      var0[564] = PyString.fromInterned("es_pa.iso885915");
      var0[565] = PyString.fromInterned("es_PA.ISO8859-15");
      var0[566] = PyString.fromInterned("es_pa@euro");
      var0[567] = PyString.fromInterned("es_PA.ISO8859-15");
      var0[568] = PyString.fromInterned("es_pe");
      var0[569] = PyString.fromInterned("es_PE.ISO8859-1");
      var0[570] = PyString.fromInterned("es_pe.iso88591");
      var0[571] = PyString.fromInterned("es_PE.ISO8859-1");
      var0[572] = PyString.fromInterned("es_pe.iso885915");
      var0[573] = PyString.fromInterned("es_PE.ISO8859-15");
      var0[574] = PyString.fromInterned("es_pe@euro");
      var0[575] = PyString.fromInterned("es_PE.ISO8859-15");
      var0[576] = PyString.fromInterned("es_pr");
      var0[577] = PyString.fromInterned("es_PR.ISO8859-1");
      var0[578] = PyString.fromInterned("es_pr.iso88591");
      var0[579] = PyString.fromInterned("es_PR.ISO8859-1");
      var0[580] = PyString.fromInterned("es_py");
      var0[581] = PyString.fromInterned("es_PY.ISO8859-1");
      var0[582] = PyString.fromInterned("es_py.iso88591");
      var0[583] = PyString.fromInterned("es_PY.ISO8859-1");
      var0[584] = PyString.fromInterned("es_py.iso885915");
      var0[585] = PyString.fromInterned("es_PY.ISO8859-15");
      var0[586] = PyString.fromInterned("es_py@euro");
      var0[587] = PyString.fromInterned("es_PY.ISO8859-15");
      var0[588] = PyString.fromInterned("es_sv");
      var0[589] = PyString.fromInterned("es_SV.ISO8859-1");
      var0[590] = PyString.fromInterned("es_sv.iso88591");
      var0[591] = PyString.fromInterned("es_SV.ISO8859-1");
      var0[592] = PyString.fromInterned("es_sv.iso885915");
      var0[593] = PyString.fromInterned("es_SV.ISO8859-15");
      var0[594] = PyString.fromInterned("es_sv@euro");
      var0[595] = PyString.fromInterned("es_SV.ISO8859-15");
      var0[596] = PyString.fromInterned("es_us");
      var0[597] = PyString.fromInterned("es_US.ISO8859-1");
      var0[598] = PyString.fromInterned("es_us.iso88591");
      var0[599] = PyString.fromInterned("es_US.ISO8859-1");
      var0[600] = PyString.fromInterned("es_uy");
      var0[601] = PyString.fromInterned("es_UY.ISO8859-1");
      var0[602] = PyString.fromInterned("es_uy.iso88591");
      var0[603] = PyString.fromInterned("es_UY.ISO8859-1");
      var0[604] = PyString.fromInterned("es_uy.iso885915");
      var0[605] = PyString.fromInterned("es_UY.ISO8859-15");
      var0[606] = PyString.fromInterned("es_uy@euro");
      var0[607] = PyString.fromInterned("es_UY.ISO8859-15");
      var0[608] = PyString.fromInterned("es_ve");
      var0[609] = PyString.fromInterned("es_VE.ISO8859-1");
      var0[610] = PyString.fromInterned("es_ve.iso88591");
      var0[611] = PyString.fromInterned("es_VE.ISO8859-1");
      var0[612] = PyString.fromInterned("es_ve.iso885915");
      var0[613] = PyString.fromInterned("es_VE.ISO8859-15");
      var0[614] = PyString.fromInterned("es_ve@euro");
      var0[615] = PyString.fromInterned("es_VE.ISO8859-15");
      var0[616] = PyString.fromInterned("estonian");
      var0[617] = PyString.fromInterned("et_EE.ISO8859-1");
      var0[618] = PyString.fromInterned("et");
      var0[619] = PyString.fromInterned("et_EE.ISO8859-15");
      var0[620] = PyString.fromInterned("et_ee");
      var0[621] = PyString.fromInterned("et_EE.ISO8859-15");
      var0[622] = PyString.fromInterned("et_ee.iso88591");
      var0[623] = PyString.fromInterned("et_EE.ISO8859-1");
      var0[624] = PyString.fromInterned("et_ee.iso885913");
      var0[625] = PyString.fromInterned("et_EE.ISO8859-13");
      var0[626] = PyString.fromInterned("et_ee.iso885915");
      var0[627] = PyString.fromInterned("et_EE.ISO8859-15");
      var0[628] = PyString.fromInterned("et_ee.iso88594");
      var0[629] = PyString.fromInterned("et_EE.ISO8859-4");
      var0[630] = PyString.fromInterned("et_ee@euro");
      var0[631] = PyString.fromInterned("et_EE.ISO8859-15");
      var0[632] = PyString.fromInterned("eu");
      var0[633] = PyString.fromInterned("eu_ES.ISO8859-1");
      var0[634] = PyString.fromInterned("eu_es");
      var0[635] = PyString.fromInterned("eu_ES.ISO8859-1");
      var0[636] = PyString.fromInterned("eu_es.iso88591");
      var0[637] = PyString.fromInterned("eu_ES.ISO8859-1");
      var0[638] = PyString.fromInterned("eu_es.iso885915");
      var0[639] = PyString.fromInterned("eu_ES.ISO8859-15");
      var0[640] = PyString.fromInterned("eu_es.iso885915@euro");
      var0[641] = PyString.fromInterned("eu_ES.ISO8859-15");
      var0[642] = PyString.fromInterned("eu_es.utf8@euro");
      var0[643] = PyString.fromInterned("eu_ES.UTF-8");
      var0[644] = PyString.fromInterned("eu_es@euro");
      var0[645] = PyString.fromInterned("eu_ES.ISO8859-15");
      var0[646] = PyString.fromInterned("fa");
      var0[647] = PyString.fromInterned("fa_IR.UTF-8");
      var0[648] = PyString.fromInterned("fa_ir");
      var0[649] = PyString.fromInterned("fa_IR.UTF-8");
      var0[650] = PyString.fromInterned("fa_ir.isiri3342");
      var0[651] = PyString.fromInterned("fa_IR.ISIRI-3342");
      var0[652] = PyString.fromInterned("fi");
      var0[653] = PyString.fromInterned("fi_FI.ISO8859-15");
      var0[654] = PyString.fromInterned("fi.iso885915");
      var0[655] = PyString.fromInterned("fi_FI.ISO8859-15");
      var0[656] = PyString.fromInterned("fi_fi");
      var0[657] = PyString.fromInterned("fi_FI.ISO8859-15");
      var0[658] = PyString.fromInterned("fi_fi.88591");
      var0[659] = PyString.fromInterned("fi_FI.ISO8859-1");
      var0[660] = PyString.fromInterned("fi_fi.iso88591");
      var0[661] = PyString.fromInterned("fi_FI.ISO8859-1");
      var0[662] = PyString.fromInterned("fi_fi.iso885915");
      var0[663] = PyString.fromInterned("fi_FI.ISO8859-15");
      var0[664] = PyString.fromInterned("fi_fi.iso885915@euro");
      var0[665] = PyString.fromInterned("fi_FI.ISO8859-15");
      var0[666] = PyString.fromInterned("fi_fi.utf8@euro");
      var0[667] = PyString.fromInterned("fi_FI.UTF-8");
      var0[668] = PyString.fromInterned("fi_fi@euro");
      var0[669] = PyString.fromInterned("fi_FI.ISO8859-15");
      var0[670] = PyString.fromInterned("finnish");
      var0[671] = PyString.fromInterned("fi_FI.ISO8859-1");
      var0[672] = PyString.fromInterned("finnish.iso88591");
      var0[673] = PyString.fromInterned("fi_FI.ISO8859-1");
      var0[674] = PyString.fromInterned("fo");
      var0[675] = PyString.fromInterned("fo_FO.ISO8859-1");
      var0[676] = PyString.fromInterned("fo_fo");
      var0[677] = PyString.fromInterned("fo_FO.ISO8859-1");
      var0[678] = PyString.fromInterned("fo_fo.iso88591");
      var0[679] = PyString.fromInterned("fo_FO.ISO8859-1");
      var0[680] = PyString.fromInterned("fo_fo.iso885915");
      var0[681] = PyString.fromInterned("fo_FO.ISO8859-15");
      var0[682] = PyString.fromInterned("fo_fo@euro");
      var0[683] = PyString.fromInterned("fo_FO.ISO8859-15");
      var0[684] = PyString.fromInterned("fr");
      var0[685] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[686] = PyString.fromInterned("fr.iso885915");
      var0[687] = PyString.fromInterned("fr_FR.ISO8859-15");
      var0[688] = PyString.fromInterned("fr_be");
      var0[689] = PyString.fromInterned("fr_BE.ISO8859-1");
      var0[690] = PyString.fromInterned("fr_be.88591");
      var0[691] = PyString.fromInterned("fr_BE.ISO8859-1");
      var0[692] = PyString.fromInterned("fr_be.iso88591");
      var0[693] = PyString.fromInterned("fr_BE.ISO8859-1");
      var0[694] = PyString.fromInterned("fr_be.iso885915");
      var0[695] = PyString.fromInterned("fr_BE.ISO8859-15");
      var0[696] = PyString.fromInterned("fr_be.iso885915@euro");
      var0[697] = PyString.fromInterned("fr_BE.ISO8859-15");
      var0[698] = PyString.fromInterned("fr_be.utf8@euro");
      var0[699] = PyString.fromInterned("fr_BE.UTF-8");
      var0[700] = PyString.fromInterned("fr_be@euro");
      var0[701] = PyString.fromInterned("fr_BE.ISO8859-15");
      var0[702] = PyString.fromInterned("fr_ca");
      var0[703] = PyString.fromInterned("fr_CA.ISO8859-1");
      var0[704] = PyString.fromInterned("fr_ca.88591");
      var0[705] = PyString.fromInterned("fr_CA.ISO8859-1");
      var0[706] = PyString.fromInterned("fr_ca.iso88591");
      var0[707] = PyString.fromInterned("fr_CA.ISO8859-1");
      var0[708] = PyString.fromInterned("fr_ca.iso885915");
      var0[709] = PyString.fromInterned("fr_CA.ISO8859-15");
      var0[710] = PyString.fromInterned("fr_ca@euro");
      var0[711] = PyString.fromInterned("fr_CA.ISO8859-15");
      var0[712] = PyString.fromInterned("fr_ch");
      var0[713] = PyString.fromInterned("fr_CH.ISO8859-1");
      var0[714] = PyString.fromInterned("fr_ch.88591");
      var0[715] = PyString.fromInterned("fr_CH.ISO8859-1");
      var0[716] = PyString.fromInterned("fr_ch.iso88591");
      var0[717] = PyString.fromInterned("fr_CH.ISO8859-1");
      var0[718] = PyString.fromInterned("fr_ch.iso885915");
      var0[719] = PyString.fromInterned("fr_CH.ISO8859-15");
      var0[720] = PyString.fromInterned("fr_ch@euro");
      var0[721] = PyString.fromInterned("fr_CH.ISO8859-15");
      var0[722] = PyString.fromInterned("fr_fr");
      var0[723] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[724] = PyString.fromInterned("fr_fr.88591");
      var0[725] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[726] = PyString.fromInterned("fr_fr.iso88591");
      var0[727] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[728] = PyString.fromInterned("fr_fr.iso885915");
      var0[729] = PyString.fromInterned("fr_FR.ISO8859-15");
      var0[730] = PyString.fromInterned("fr_fr.iso885915@euro");
      var0[731] = PyString.fromInterned("fr_FR.ISO8859-15");
      var0[732] = PyString.fromInterned("fr_fr.utf8@euro");
      var0[733] = PyString.fromInterned("fr_FR.UTF-8");
      var0[734] = PyString.fromInterned("fr_fr@euro");
      var0[735] = PyString.fromInterned("fr_FR.ISO8859-15");
      var0[736] = PyString.fromInterned("fr_lu");
      var0[737] = PyString.fromInterned("fr_LU.ISO8859-1");
      var0[738] = PyString.fromInterned("fr_lu.88591");
      var0[739] = PyString.fromInterned("fr_LU.ISO8859-1");
      var0[740] = PyString.fromInterned("fr_lu.iso88591");
      var0[741] = PyString.fromInterned("fr_LU.ISO8859-1");
      var0[742] = PyString.fromInterned("fr_lu.iso885915");
      var0[743] = PyString.fromInterned("fr_LU.ISO8859-15");
      var0[744] = PyString.fromInterned("fr_lu.iso885915@euro");
      var0[745] = PyString.fromInterned("fr_LU.ISO8859-15");
      var0[746] = PyString.fromInterned("fr_lu.utf8@euro");
      var0[747] = PyString.fromInterned("fr_LU.UTF-8");
      var0[748] = PyString.fromInterned("fr_lu@euro");
      var0[749] = PyString.fromInterned("fr_LU.ISO8859-15");
      var0[750] = PyString.fromInterned("français");
      var0[751] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[752] = PyString.fromInterned("fre_fr");
      var0[753] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[754] = PyString.fromInterned("fre_fr.8859");
      var0[755] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[756] = PyString.fromInterned("french");
      var0[757] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[758] = PyString.fromInterned("french.iso88591");
      var0[759] = PyString.fromInterned("fr_CH.ISO8859-1");
      var0[760] = PyString.fromInterned("french_france");
      var0[761] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[762] = PyString.fromInterned("french_france.8859");
      var0[763] = PyString.fromInterned("fr_FR.ISO8859-1");
      var0[764] = PyString.fromInterned("ga");
      var0[765] = PyString.fromInterned("ga_IE.ISO8859-1");
      var0[766] = PyString.fromInterned("ga_ie");
      var0[767] = PyString.fromInterned("ga_IE.ISO8859-1");
      var0[768] = PyString.fromInterned("ga_ie.iso88591");
      var0[769] = PyString.fromInterned("ga_IE.ISO8859-1");
      var0[770] = PyString.fromInterned("ga_ie.iso885914");
      var0[771] = PyString.fromInterned("ga_IE.ISO8859-14");
      var0[772] = PyString.fromInterned("ga_ie.iso885915");
      var0[773] = PyString.fromInterned("ga_IE.ISO8859-15");
      var0[774] = PyString.fromInterned("ga_ie.iso885915@euro");
      var0[775] = PyString.fromInterned("ga_IE.ISO8859-15");
      var0[776] = PyString.fromInterned("ga_ie.utf8@euro");
      var0[777] = PyString.fromInterned("ga_IE.UTF-8");
      var0[778] = PyString.fromInterned("ga_ie@euro");
      var0[779] = PyString.fromInterned("ga_IE.ISO8859-15");
      var0[780] = PyString.fromInterned("galego");
      var0[781] = PyString.fromInterned("gl_ES.ISO8859-1");
      var0[782] = PyString.fromInterned("galician");
      var0[783] = PyString.fromInterned("gl_ES.ISO8859-1");
      var0[784] = PyString.fromInterned("gd");
      var0[785] = PyString.fromInterned("gd_GB.ISO8859-1");
      var0[786] = PyString.fromInterned("gd_gb");
      var0[787] = PyString.fromInterned("gd_GB.ISO8859-1");
      var0[788] = PyString.fromInterned("gd_gb.iso88591");
      var0[789] = PyString.fromInterned("gd_GB.ISO8859-1");
      var0[790] = PyString.fromInterned("gd_gb.iso885914");
      var0[791] = PyString.fromInterned("gd_GB.ISO8859-14");
      var0[792] = PyString.fromInterned("gd_gb.iso885915");
      var0[793] = PyString.fromInterned("gd_GB.ISO8859-15");
      var0[794] = PyString.fromInterned("gd_gb@euro");
      var0[795] = PyString.fromInterned("gd_GB.ISO8859-15");
      var0[796] = PyString.fromInterned("ger_de");
      var0[797] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[798] = PyString.fromInterned("ger_de.8859");
      var0[799] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[800] = PyString.fromInterned("german");
      var0[801] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[802] = PyString.fromInterned("german.iso88591");
      var0[803] = PyString.fromInterned("de_CH.ISO8859-1");
      var0[804] = PyString.fromInterned("german_germany");
      var0[805] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[806] = PyString.fromInterned("german_germany.8859");
      var0[807] = PyString.fromInterned("de_DE.ISO8859-1");
      var0[808] = PyString.fromInterned("gl");
      var0[809] = PyString.fromInterned("gl_ES.ISO8859-1");
      var0[810] = PyString.fromInterned("gl_es");
      var0[811] = PyString.fromInterned("gl_ES.ISO8859-1");
      var0[812] = PyString.fromInterned("gl_es.iso88591");
      var0[813] = PyString.fromInterned("gl_ES.ISO8859-1");
      var0[814] = PyString.fromInterned("gl_es.iso885915");
      var0[815] = PyString.fromInterned("gl_ES.ISO8859-15");
      var0[816] = PyString.fromInterned("gl_es.iso885915@euro");
      var0[817] = PyString.fromInterned("gl_ES.ISO8859-15");
      var0[818] = PyString.fromInterned("gl_es.utf8@euro");
      var0[819] = PyString.fromInterned("gl_ES.UTF-8");
      var0[820] = PyString.fromInterned("gl_es@euro");
      var0[821] = PyString.fromInterned("gl_ES.ISO8859-15");
      var0[822] = PyString.fromInterned("greek");
      var0[823] = PyString.fromInterned("el_GR.ISO8859-7");
      var0[824] = PyString.fromInterned("greek.iso88597");
      var0[825] = PyString.fromInterned("el_GR.ISO8859-7");
      var0[826] = PyString.fromInterned("gu_in");
      var0[827] = PyString.fromInterned("gu_IN.UTF-8");
      var0[828] = PyString.fromInterned("gv");
      var0[829] = PyString.fromInterned("gv_GB.ISO8859-1");
      var0[830] = PyString.fromInterned("gv_gb");
      var0[831] = PyString.fromInterned("gv_GB.ISO8859-1");
      var0[832] = PyString.fromInterned("gv_gb.iso88591");
      var0[833] = PyString.fromInterned("gv_GB.ISO8859-1");
      var0[834] = PyString.fromInterned("gv_gb.iso885914");
      var0[835] = PyString.fromInterned("gv_GB.ISO8859-14");
      var0[836] = PyString.fromInterned("gv_gb.iso885915");
      var0[837] = PyString.fromInterned("gv_GB.ISO8859-15");
      var0[838] = PyString.fromInterned("gv_gb@euro");
      var0[839] = PyString.fromInterned("gv_GB.ISO8859-15");
      var0[840] = PyString.fromInterned("he");
      var0[841] = PyString.fromInterned("he_IL.ISO8859-8");
      var0[842] = PyString.fromInterned("he_il");
      var0[843] = PyString.fromInterned("he_IL.ISO8859-8");
      var0[844] = PyString.fromInterned("he_il.cp1255");
      var0[845] = PyString.fromInterned("he_IL.CP1255");
      var0[846] = PyString.fromInterned("he_il.iso88598");
      var0[847] = PyString.fromInterned("he_IL.ISO8859-8");
      var0[848] = PyString.fromInterned("he_il.microsoftcp1255");
      var0[849] = PyString.fromInterned("he_IL.CP1255");
      var0[850] = PyString.fromInterned("hebrew");
      var0[851] = PyString.fromInterned("iw_IL.ISO8859-8");
      var0[852] = PyString.fromInterned("hebrew.iso88598");
      var0[853] = PyString.fromInterned("iw_IL.ISO8859-8");
      var0[854] = PyString.fromInterned("hi");
      var0[855] = PyString.fromInterned("hi_IN.ISCII-DEV");
      var0[856] = PyString.fromInterned("hi_in");
      var0[857] = PyString.fromInterned("hi_IN.ISCII-DEV");
      var0[858] = PyString.fromInterned("hi_in.isciidev");
      var0[859] = PyString.fromInterned("hi_IN.ISCII-DEV");
      var0[860] = PyString.fromInterned("hne");
      var0[861] = PyString.fromInterned("hne_IN.UTF-8");
      var0[862] = PyString.fromInterned("hr");
      var0[863] = PyString.fromInterned("hr_HR.ISO8859-2");
      var0[864] = PyString.fromInterned("hr_hr");
      var0[865] = PyString.fromInterned("hr_HR.ISO8859-2");
      var0[866] = PyString.fromInterned("hr_hr.iso88592");
      var0[867] = PyString.fromInterned("hr_HR.ISO8859-2");
      var0[868] = PyString.fromInterned("hrvatski");
      var0[869] = PyString.fromInterned("hr_HR.ISO8859-2");
      var0[870] = PyString.fromInterned("hu");
      var0[871] = PyString.fromInterned("hu_HU.ISO8859-2");
      var0[872] = PyString.fromInterned("hu_hu");
      var0[873] = PyString.fromInterned("hu_HU.ISO8859-2");
      var0[874] = PyString.fromInterned("hu_hu.iso88592");
      var0[875] = PyString.fromInterned("hu_HU.ISO8859-2");
      var0[876] = PyString.fromInterned("hungarian");
      var0[877] = PyString.fromInterned("hu_HU.ISO8859-2");
      var0[878] = PyString.fromInterned("icelandic");
      var0[879] = PyString.fromInterned("is_IS.ISO8859-1");
      var0[880] = PyString.fromInterned("icelandic.iso88591");
      var0[881] = PyString.fromInterned("is_IS.ISO8859-1");
      var0[882] = PyString.fromInterned("id");
      var0[883] = PyString.fromInterned("id_ID.ISO8859-1");
      var0[884] = PyString.fromInterned("id_id");
      var0[885] = PyString.fromInterned("id_ID.ISO8859-1");
      var0[886] = PyString.fromInterned("in");
      var0[887] = PyString.fromInterned("id_ID.ISO8859-1");
      var0[888] = PyString.fromInterned("in_id");
      var0[889] = PyString.fromInterned("id_ID.ISO8859-1");
      var0[890] = PyString.fromInterned("is");
      var0[891] = PyString.fromInterned("is_IS.ISO8859-1");
      var0[892] = PyString.fromInterned("is_is");
      var0[893] = PyString.fromInterned("is_IS.ISO8859-1");
      var0[894] = PyString.fromInterned("is_is.iso88591");
      var0[895] = PyString.fromInterned("is_IS.ISO8859-1");
      var0[896] = PyString.fromInterned("is_is.iso885915");
      var0[897] = PyString.fromInterned("is_IS.ISO8859-15");
      var0[898] = PyString.fromInterned("is_is@euro");
      var0[899] = PyString.fromInterned("is_IS.ISO8859-15");
      var0[900] = PyString.fromInterned("iso-8859-1");
      var0[901] = PyString.fromInterned("en_US.ISO8859-1");
      var0[902] = PyString.fromInterned("iso-8859-15");
      var0[903] = PyString.fromInterned("en_US.ISO8859-15");
      var0[904] = PyString.fromInterned("iso8859-1");
      var0[905] = PyString.fromInterned("en_US.ISO8859-1");
      var0[906] = PyString.fromInterned("iso8859-15");
      var0[907] = PyString.fromInterned("en_US.ISO8859-15");
      var0[908] = PyString.fromInterned("iso_8859_1");
      var0[909] = PyString.fromInterned("en_US.ISO8859-1");
      var0[910] = PyString.fromInterned("iso_8859_15");
      var0[911] = PyString.fromInterned("en_US.ISO8859-15");
      var0[912] = PyString.fromInterned("it");
      var0[913] = PyString.fromInterned("it_IT.ISO8859-1");
      var0[914] = PyString.fromInterned("it.iso885915");
      var0[915] = PyString.fromInterned("it_IT.ISO8859-15");
      var0[916] = PyString.fromInterned("it_ch");
      var0[917] = PyString.fromInterned("it_CH.ISO8859-1");
      var0[918] = PyString.fromInterned("it_ch.iso88591");
      var0[919] = PyString.fromInterned("it_CH.ISO8859-1");
      var0[920] = PyString.fromInterned("it_ch.iso885915");
      var0[921] = PyString.fromInterned("it_CH.ISO8859-15");
      var0[922] = PyString.fromInterned("it_ch@euro");
      var0[923] = PyString.fromInterned("it_CH.ISO8859-15");
      var0[924] = PyString.fromInterned("it_it");
      var0[925] = PyString.fromInterned("it_IT.ISO8859-1");
      var0[926] = PyString.fromInterned("it_it.88591");
      var0[927] = PyString.fromInterned("it_IT.ISO8859-1");
      var0[928] = PyString.fromInterned("it_it.iso88591");
      var0[929] = PyString.fromInterned("it_IT.ISO8859-1");
      var0[930] = PyString.fromInterned("it_it.iso885915");
      var0[931] = PyString.fromInterned("it_IT.ISO8859-15");
      var0[932] = PyString.fromInterned("it_it.iso885915@euro");
      var0[933] = PyString.fromInterned("it_IT.ISO8859-15");
      var0[934] = PyString.fromInterned("it_it.utf8@euro");
      var0[935] = PyString.fromInterned("it_IT.UTF-8");
      var0[936] = PyString.fromInterned("it_it@euro");
      var0[937] = PyString.fromInterned("it_IT.ISO8859-15");
      var0[938] = PyString.fromInterned("italian");
      var0[939] = PyString.fromInterned("it_IT.ISO8859-1");
      var0[940] = PyString.fromInterned("italian.iso88591");
      var0[941] = PyString.fromInterned("it_IT.ISO8859-1");
      var0[942] = PyString.fromInterned("iu");
      var0[943] = PyString.fromInterned("iu_CA.NUNACOM-8");
      var0[944] = PyString.fromInterned("iu_ca");
      var0[945] = PyString.fromInterned("iu_CA.NUNACOM-8");
      var0[946] = PyString.fromInterned("iu_ca.nunacom8");
      var0[947] = PyString.fromInterned("iu_CA.NUNACOM-8");
      var0[948] = PyString.fromInterned("iw");
      var0[949] = PyString.fromInterned("he_IL.ISO8859-8");
      var0[950] = PyString.fromInterned("iw_il");
      var0[951] = PyString.fromInterned("he_IL.ISO8859-8");
      var0[952] = PyString.fromInterned("iw_il.iso88598");
      var0[953] = PyString.fromInterned("he_IL.ISO8859-8");
      var0[954] = PyString.fromInterned("ja");
      var0[955] = PyString.fromInterned("ja_JP.eucJP");
      var0[956] = PyString.fromInterned("ja.jis");
      var0[957] = PyString.fromInterned("ja_JP.JIS7");
      var0[958] = PyString.fromInterned("ja.sjis");
      var0[959] = PyString.fromInterned("ja_JP.SJIS");
      var0[960] = PyString.fromInterned("ja_jp");
      var0[961] = PyString.fromInterned("ja_JP.eucJP");
      var0[962] = PyString.fromInterned("ja_jp.ajec");
      var0[963] = PyString.fromInterned("ja_JP.eucJP");
      var0[964] = PyString.fromInterned("ja_jp.euc");
      var0[965] = PyString.fromInterned("ja_JP.eucJP");
      var0[966] = PyString.fromInterned("ja_jp.eucjp");
      var0[967] = PyString.fromInterned("ja_JP.eucJP");
      var0[968] = PyString.fromInterned("ja_jp.iso-2022-jp");
      var0[969] = PyString.fromInterned("ja_JP.JIS7");
      var0[970] = PyString.fromInterned("ja_jp.iso2022jp");
      var0[971] = PyString.fromInterned("ja_JP.JIS7");
      var0[972] = PyString.fromInterned("ja_jp.jis");
      var0[973] = PyString.fromInterned("ja_JP.JIS7");
      var0[974] = PyString.fromInterned("ja_jp.jis7");
      var0[975] = PyString.fromInterned("ja_JP.JIS7");
      var0[976] = PyString.fromInterned("ja_jp.mscode");
      var0[977] = PyString.fromInterned("ja_JP.SJIS");
      var0[978] = PyString.fromInterned("ja_jp.pck");
      var0[979] = PyString.fromInterned("ja_JP.SJIS");
      var0[980] = PyString.fromInterned("ja_jp.sjis");
      var0[981] = PyString.fromInterned("ja_JP.SJIS");
      var0[982] = PyString.fromInterned("ja_jp.ujis");
      var0[983] = PyString.fromInterned("ja_JP.eucJP");
      var0[984] = PyString.fromInterned("japan");
      var0[985] = PyString.fromInterned("ja_JP.eucJP");
      var0[986] = PyString.fromInterned("japanese");
      var0[987] = PyString.fromInterned("ja_JP.eucJP");
      var0[988] = PyString.fromInterned("japanese-euc");
      var0[989] = PyString.fromInterned("ja_JP.eucJP");
      var0[990] = PyString.fromInterned("japanese.euc");
      var0[991] = PyString.fromInterned("ja_JP.eucJP");
      var0[992] = PyString.fromInterned("japanese.sjis");
      var0[993] = PyString.fromInterned("ja_JP.SJIS");
      var0[994] = PyString.fromInterned("jp_jp");
      var0[995] = PyString.fromInterned("ja_JP.eucJP");
      var0[996] = PyString.fromInterned("ka");
      var0[997] = PyString.fromInterned("ka_GE.GEORGIAN-ACADEMY");
      var0[998] = PyString.fromInterned("ka_ge");
      var0[999] = PyString.fromInterned("ka_GE.GEORGIAN-ACADEMY");
      var0[1000] = PyString.fromInterned("ka_ge.georgianacademy");
      var0[1001] = PyString.fromInterned("ka_GE.GEORGIAN-ACADEMY");
      var0[1002] = PyString.fromInterned("ka_ge.georgianps");
      var0[1003] = PyString.fromInterned("ka_GE.GEORGIAN-PS");
      var0[1004] = PyString.fromInterned("ka_ge.georgianrs");
      var0[1005] = PyString.fromInterned("ka_GE.GEORGIAN-ACADEMY");
      var0[1006] = PyString.fromInterned("kl");
      var0[1007] = PyString.fromInterned("kl_GL.ISO8859-1");
      var0[1008] = PyString.fromInterned("kl_gl");
      var0[1009] = PyString.fromInterned("kl_GL.ISO8859-1");
      var0[1010] = PyString.fromInterned("kl_gl.iso88591");
      var0[1011] = PyString.fromInterned("kl_GL.ISO8859-1");
      var0[1012] = PyString.fromInterned("kl_gl.iso885915");
      var0[1013] = PyString.fromInterned("kl_GL.ISO8859-15");
      var0[1014] = PyString.fromInterned("kl_gl@euro");
      var0[1015] = PyString.fromInterned("kl_GL.ISO8859-15");
      var0[1016] = PyString.fromInterned("km_kh");
      var0[1017] = PyString.fromInterned("km_KH.UTF-8");
      var0[1018] = PyString.fromInterned("kn");
      var0[1019] = PyString.fromInterned("kn_IN.UTF-8");
      var0[1020] = PyString.fromInterned("kn_in");
      var0[1021] = PyString.fromInterned("kn_IN.UTF-8");
      var0[1022] = PyString.fromInterned("ko");
      var0[1023] = PyString.fromInterned("ko_KR.eucKR");
      var0[1024] = PyString.fromInterned("ko_kr");
      var0[1025] = PyString.fromInterned("ko_KR.eucKR");
      var0[1026] = PyString.fromInterned("ko_kr.euc");
      var0[1027] = PyString.fromInterned("ko_KR.eucKR");
      var0[1028] = PyString.fromInterned("ko_kr.euckr");
      var0[1029] = PyString.fromInterned("ko_KR.eucKR");
      var0[1030] = PyString.fromInterned("korean");
      var0[1031] = PyString.fromInterned("ko_KR.eucKR");
      var0[1032] = PyString.fromInterned("korean.euc");
      var0[1033] = PyString.fromInterned("ko_KR.eucKR");
      var0[1034] = PyString.fromInterned("ks");
      var0[1035] = PyString.fromInterned("ks_IN.UTF-8");
      var0[1036] = PyString.fromInterned("ks_in@devanagari");
      var0[1037] = PyString.fromInterned("ks_IN@devanagari.UTF-8");
      var0[1038] = PyString.fromInterned("kw");
      var0[1039] = PyString.fromInterned("kw_GB.ISO8859-1");
      var0[1040] = PyString.fromInterned("kw_gb");
      var0[1041] = PyString.fromInterned("kw_GB.ISO8859-1");
      var0[1042] = PyString.fromInterned("kw_gb.iso88591");
      var0[1043] = PyString.fromInterned("kw_GB.ISO8859-1");
      var0[1044] = PyString.fromInterned("kw_gb.iso885914");
      var0[1045] = PyString.fromInterned("kw_GB.ISO8859-14");
      var0[1046] = PyString.fromInterned("kw_gb.iso885915");
      var0[1047] = PyString.fromInterned("kw_GB.ISO8859-15");
      var0[1048] = PyString.fromInterned("kw_gb@euro");
      var0[1049] = PyString.fromInterned("kw_GB.ISO8859-15");
      var0[1050] = PyString.fromInterned("ky");
      var0[1051] = PyString.fromInterned("ky_KG.UTF-8");
      var0[1052] = PyString.fromInterned("ky_kg");
      var0[1053] = PyString.fromInterned("ky_KG.UTF-8");
      var0[1054] = PyString.fromInterned("lithuanian");
      var0[1055] = PyString.fromInterned("lt_LT.ISO8859-13");
      var0[1056] = PyString.fromInterned("lo");
      var0[1057] = PyString.fromInterned("lo_LA.MULELAO-1");
      var0[1058] = PyString.fromInterned("lo_la");
      var0[1059] = PyString.fromInterned("lo_LA.MULELAO-1");
      var0[1060] = PyString.fromInterned("lo_la.cp1133");
      var0[1061] = PyString.fromInterned("lo_LA.IBM-CP1133");
      var0[1062] = PyString.fromInterned("lo_la.ibmcp1133");
      var0[1063] = PyString.fromInterned("lo_LA.IBM-CP1133");
      var0[1064] = PyString.fromInterned("lo_la.mulelao1");
      var0[1065] = PyString.fromInterned("lo_LA.MULELAO-1");
      var0[1066] = PyString.fromInterned("lt");
      var0[1067] = PyString.fromInterned("lt_LT.ISO8859-13");
      var0[1068] = PyString.fromInterned("lt_lt");
      var0[1069] = PyString.fromInterned("lt_LT.ISO8859-13");
      var0[1070] = PyString.fromInterned("lt_lt.iso885913");
      var0[1071] = PyString.fromInterned("lt_LT.ISO8859-13");
      var0[1072] = PyString.fromInterned("lt_lt.iso88594");
      var0[1073] = PyString.fromInterned("lt_LT.ISO8859-4");
      var0[1074] = PyString.fromInterned("lv");
      var0[1075] = PyString.fromInterned("lv_LV.ISO8859-13");
      var0[1076] = PyString.fromInterned("lv_lv");
      var0[1077] = PyString.fromInterned("lv_LV.ISO8859-13");
      var0[1078] = PyString.fromInterned("lv_lv.iso885913");
      var0[1079] = PyString.fromInterned("lv_LV.ISO8859-13");
      var0[1080] = PyString.fromInterned("lv_lv.iso88594");
      var0[1081] = PyString.fromInterned("lv_LV.ISO8859-4");
      var0[1082] = PyString.fromInterned("mai");
      var0[1083] = PyString.fromInterned("mai_IN.UTF-8");
      var0[1084] = PyString.fromInterned("mi");
      var0[1085] = PyString.fromInterned("mi_NZ.ISO8859-1");
      var0[1086] = PyString.fromInterned("mi_nz");
      var0[1087] = PyString.fromInterned("mi_NZ.ISO8859-1");
      var0[1088] = PyString.fromInterned("mi_nz.iso88591");
      var0[1089] = PyString.fromInterned("mi_NZ.ISO8859-1");
      var0[1090] = PyString.fromInterned("mk");
      var0[1091] = PyString.fromInterned("mk_MK.ISO8859-5");
      var0[1092] = PyString.fromInterned("mk_mk");
      var0[1093] = PyString.fromInterned("mk_MK.ISO8859-5");
      var0[1094] = PyString.fromInterned("mk_mk.cp1251");
      var0[1095] = PyString.fromInterned("mk_MK.CP1251");
      var0[1096] = PyString.fromInterned("mk_mk.iso88595");
      var0[1097] = PyString.fromInterned("mk_MK.ISO8859-5");
      var0[1098] = PyString.fromInterned("mk_mk.microsoftcp1251");
      var0[1099] = PyString.fromInterned("mk_MK.CP1251");
      var0[1100] = PyString.fromInterned("ml");
      var0[1101] = PyString.fromInterned("ml_IN.UTF-8");
      var0[1102] = PyString.fromInterned("mr");
      var0[1103] = PyString.fromInterned("mr_IN.UTF-8");
      var0[1104] = PyString.fromInterned("mr_in");
      var0[1105] = PyString.fromInterned("mr_IN.UTF-8");
      var0[1106] = PyString.fromInterned("ms");
      var0[1107] = PyString.fromInterned("ms_MY.ISO8859-1");
      var0[1108] = PyString.fromInterned("ms_my");
      var0[1109] = PyString.fromInterned("ms_MY.ISO8859-1");
      var0[1110] = PyString.fromInterned("ms_my.iso88591");
      var0[1111] = PyString.fromInterned("ms_MY.ISO8859-1");
      var0[1112] = PyString.fromInterned("mt");
      var0[1113] = PyString.fromInterned("mt_MT.ISO8859-3");
      var0[1114] = PyString.fromInterned("mt_mt");
      var0[1115] = PyString.fromInterned("mt_MT.ISO8859-3");
      var0[1116] = PyString.fromInterned("mt_mt.iso88593");
      var0[1117] = PyString.fromInterned("mt_MT.ISO8859-3");
      var0[1118] = PyString.fromInterned("nb");
      var0[1119] = PyString.fromInterned("nb_NO.ISO8859-1");
      var0[1120] = PyString.fromInterned("nb_no");
      var0[1121] = PyString.fromInterned("nb_NO.ISO8859-1");
      var0[1122] = PyString.fromInterned("nb_no.88591");
      var0[1123] = PyString.fromInterned("nb_NO.ISO8859-1");
      var0[1124] = PyString.fromInterned("nb_no.iso88591");
      var0[1125] = PyString.fromInterned("nb_NO.ISO8859-1");
      var0[1126] = PyString.fromInterned("nb_no.iso885915");
      var0[1127] = PyString.fromInterned("nb_NO.ISO8859-15");
      var0[1128] = PyString.fromInterned("nb_no@euro");
      var0[1129] = PyString.fromInterned("nb_NO.ISO8859-15");
      var0[1130] = PyString.fromInterned("nl");
      var0[1131] = PyString.fromInterned("nl_NL.ISO8859-1");
      var0[1132] = PyString.fromInterned("nl.iso885915");
      var0[1133] = PyString.fromInterned("nl_NL.ISO8859-15");
      var0[1134] = PyString.fromInterned("nl_be");
      var0[1135] = PyString.fromInterned("nl_BE.ISO8859-1");
      var0[1136] = PyString.fromInterned("nl_be.88591");
      var0[1137] = PyString.fromInterned("nl_BE.ISO8859-1");
      var0[1138] = PyString.fromInterned("nl_be.iso88591");
      var0[1139] = PyString.fromInterned("nl_BE.ISO8859-1");
      var0[1140] = PyString.fromInterned("nl_be.iso885915");
      var0[1141] = PyString.fromInterned("nl_BE.ISO8859-15");
      var0[1142] = PyString.fromInterned("nl_be.iso885915@euro");
      var0[1143] = PyString.fromInterned("nl_BE.ISO8859-15");
      var0[1144] = PyString.fromInterned("nl_be.utf8@euro");
      var0[1145] = PyString.fromInterned("nl_BE.UTF-8");
      var0[1146] = PyString.fromInterned("nl_be@euro");
      var0[1147] = PyString.fromInterned("nl_BE.ISO8859-15");
      var0[1148] = PyString.fromInterned("nl_nl");
      var0[1149] = PyString.fromInterned("nl_NL.ISO8859-1");
      var0[1150] = PyString.fromInterned("nl_nl.88591");
      var0[1151] = PyString.fromInterned("nl_NL.ISO8859-1");
      var0[1152] = PyString.fromInterned("nl_nl.iso88591");
      var0[1153] = PyString.fromInterned("nl_NL.ISO8859-1");
      var0[1154] = PyString.fromInterned("nl_nl.iso885915");
      var0[1155] = PyString.fromInterned("nl_NL.ISO8859-15");
      var0[1156] = PyString.fromInterned("nl_nl.iso885915@euro");
      var0[1157] = PyString.fromInterned("nl_NL.ISO8859-15");
      var0[1158] = PyString.fromInterned("nl_nl.utf8@euro");
      var0[1159] = PyString.fromInterned("nl_NL.UTF-8");
      var0[1160] = PyString.fromInterned("nl_nl@euro");
      var0[1161] = PyString.fromInterned("nl_NL.ISO8859-15");
      var0[1162] = PyString.fromInterned("nn");
      var0[1163] = PyString.fromInterned("nn_NO.ISO8859-1");
      var0[1164] = PyString.fromInterned("nn_no");
      var0[1165] = PyString.fromInterned("nn_NO.ISO8859-1");
      var0[1166] = PyString.fromInterned("nn_no.88591");
      var0[1167] = PyString.fromInterned("nn_NO.ISO8859-1");
      var0[1168] = PyString.fromInterned("nn_no.iso88591");
      var0[1169] = PyString.fromInterned("nn_NO.ISO8859-1");
      var0[1170] = PyString.fromInterned("nn_no.iso885915");
      var0[1171] = PyString.fromInterned("nn_NO.ISO8859-15");
      var0[1172] = PyString.fromInterned("nn_no@euro");
      var0[1173] = PyString.fromInterned("nn_NO.ISO8859-15");
      var0[1174] = PyString.fromInterned("no");
      var0[1175] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1176] = PyString.fromInterned("no@nynorsk");
      var0[1177] = PyString.fromInterned("ny_NO.ISO8859-1");
      var0[1178] = PyString.fromInterned("no_no");
      var0[1179] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1180] = PyString.fromInterned("no_no.88591");
      var0[1181] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1182] = PyString.fromInterned("no_no.iso88591");
      var0[1183] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1184] = PyString.fromInterned("no_no.iso885915");
      var0[1185] = PyString.fromInterned("no_NO.ISO8859-15");
      var0[1186] = PyString.fromInterned("no_no.iso88591@bokmal");
      var0[1187] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1188] = PyString.fromInterned("no_no.iso88591@nynorsk");
      var0[1189] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1190] = PyString.fromInterned("no_no@euro");
      var0[1191] = PyString.fromInterned("no_NO.ISO8859-15");
      var0[1192] = PyString.fromInterned("norwegian");
      var0[1193] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1194] = PyString.fromInterned("norwegian.iso88591");
      var0[1195] = PyString.fromInterned("no_NO.ISO8859-1");
      var0[1196] = PyString.fromInterned("nr");
      var0[1197] = PyString.fromInterned("nr_ZA.ISO8859-1");
      var0[1198] = PyString.fromInterned("nr_za");
      var0[1199] = PyString.fromInterned("nr_ZA.ISO8859-1");
      var0[1200] = PyString.fromInterned("nr_za.iso88591");
      var0[1201] = PyString.fromInterned("nr_ZA.ISO8859-1");
      var0[1202] = PyString.fromInterned("nso");
      var0[1203] = PyString.fromInterned("nso_ZA.ISO8859-15");
      var0[1204] = PyString.fromInterned("nso_za");
      var0[1205] = PyString.fromInterned("nso_ZA.ISO8859-15");
      var0[1206] = PyString.fromInterned("nso_za.iso885915");
      var0[1207] = PyString.fromInterned("nso_ZA.ISO8859-15");
      var0[1208] = PyString.fromInterned("ny");
      var0[1209] = PyString.fromInterned("ny_NO.ISO8859-1");
      var0[1210] = PyString.fromInterned("ny_no");
      var0[1211] = PyString.fromInterned("ny_NO.ISO8859-1");
      var0[1212] = PyString.fromInterned("ny_no.88591");
      var0[1213] = PyString.fromInterned("ny_NO.ISO8859-1");
      var0[1214] = PyString.fromInterned("ny_no.iso88591");
      var0[1215] = PyString.fromInterned("ny_NO.ISO8859-1");
      var0[1216] = PyString.fromInterned("ny_no.iso885915");
      var0[1217] = PyString.fromInterned("ny_NO.ISO8859-15");
      var0[1218] = PyString.fromInterned("ny_no@euro");
      var0[1219] = PyString.fromInterned("ny_NO.ISO8859-15");
      var0[1220] = PyString.fromInterned("nynorsk");
      var0[1221] = PyString.fromInterned("nn_NO.ISO8859-1");
      var0[1222] = PyString.fromInterned("oc");
      var0[1223] = PyString.fromInterned("oc_FR.ISO8859-1");
      var0[1224] = PyString.fromInterned("oc_fr");
      var0[1225] = PyString.fromInterned("oc_FR.ISO8859-1");
      var0[1226] = PyString.fromInterned("oc_fr.iso88591");
      var0[1227] = PyString.fromInterned("oc_FR.ISO8859-1");
      var0[1228] = PyString.fromInterned("oc_fr.iso885915");
      var0[1229] = PyString.fromInterned("oc_FR.ISO8859-15");
      var0[1230] = PyString.fromInterned("oc_fr@euro");
      var0[1231] = PyString.fromInterned("oc_FR.ISO8859-15");
      var0[1232] = PyString.fromInterned("or");
      var0[1233] = PyString.fromInterned("or_IN.UTF-8");
      var0[1234] = PyString.fromInterned("pa");
      var0[1235] = PyString.fromInterned("pa_IN.UTF-8");
      var0[1236] = PyString.fromInterned("pa_in");
      var0[1237] = PyString.fromInterned("pa_IN.UTF-8");
      var0[1238] = PyString.fromInterned("pd");
      var0[1239] = PyString.fromInterned("pd_US.ISO8859-1");
      var0[1240] = PyString.fromInterned("pd_de");
      var0[1241] = PyString.fromInterned("pd_DE.ISO8859-1");
      var0[1242] = PyString.fromInterned("pd_de.iso88591");
      var0[1243] = PyString.fromInterned("pd_DE.ISO8859-1");
      var0[1244] = PyString.fromInterned("pd_de.iso885915");
      var0[1245] = PyString.fromInterned("pd_DE.ISO8859-15");
      var0[1246] = PyString.fromInterned("pd_de@euro");
      var0[1247] = PyString.fromInterned("pd_DE.ISO8859-15");
      var0[1248] = PyString.fromInterned("pd_us");
      var0[1249] = PyString.fromInterned("pd_US.ISO8859-1");
      var0[1250] = PyString.fromInterned("pd_us.iso88591");
      var0[1251] = PyString.fromInterned("pd_US.ISO8859-1");
      var0[1252] = PyString.fromInterned("pd_us.iso885915");
      var0[1253] = PyString.fromInterned("pd_US.ISO8859-15");
      var0[1254] = PyString.fromInterned("pd_us@euro");
      var0[1255] = PyString.fromInterned("pd_US.ISO8859-15");
      var0[1256] = PyString.fromInterned("ph");
      var0[1257] = PyString.fromInterned("ph_PH.ISO8859-1");
      var0[1258] = PyString.fromInterned("ph_ph");
      var0[1259] = PyString.fromInterned("ph_PH.ISO8859-1");
      var0[1260] = PyString.fromInterned("ph_ph.iso88591");
      var0[1261] = PyString.fromInterned("ph_PH.ISO8859-1");
      var0[1262] = PyString.fromInterned("pl");
      var0[1263] = PyString.fromInterned("pl_PL.ISO8859-2");
      var0[1264] = PyString.fromInterned("pl_pl");
      var0[1265] = PyString.fromInterned("pl_PL.ISO8859-2");
      var0[1266] = PyString.fromInterned("pl_pl.iso88592");
      var0[1267] = PyString.fromInterned("pl_PL.ISO8859-2");
      var0[1268] = PyString.fromInterned("polish");
      var0[1269] = PyString.fromInterned("pl_PL.ISO8859-2");
      var0[1270] = PyString.fromInterned("portuguese");
      var0[1271] = PyString.fromInterned("pt_PT.ISO8859-1");
      var0[1272] = PyString.fromInterned("portuguese.iso88591");
      var0[1273] = PyString.fromInterned("pt_PT.ISO8859-1");
      var0[1274] = PyString.fromInterned("portuguese_brazil");
      var0[1275] = PyString.fromInterned("pt_BR.ISO8859-1");
      var0[1276] = PyString.fromInterned("portuguese_brazil.8859");
      var0[1277] = PyString.fromInterned("pt_BR.ISO8859-1");
      var0[1278] = PyString.fromInterned("posix");
      var0[1279] = PyString.fromInterned("C");
      var0[1280] = PyString.fromInterned("posix-utf2");
      var0[1281] = PyString.fromInterned("C");
      var0[1282] = PyString.fromInterned("pp");
      var0[1283] = PyString.fromInterned("pp_AN.ISO8859-1");
      var0[1284] = PyString.fromInterned("pp_an");
      var0[1285] = PyString.fromInterned("pp_AN.ISO8859-1");
      var0[1286] = PyString.fromInterned("pp_an.iso88591");
      var0[1287] = PyString.fromInterned("pp_AN.ISO8859-1");
      var0[1288] = PyString.fromInterned("pt");
      var0[1289] = PyString.fromInterned("pt_PT.ISO8859-1");
      var0[1290] = PyString.fromInterned("pt.iso885915");
      var0[1291] = PyString.fromInterned("pt_PT.ISO8859-15");
      var0[1292] = PyString.fromInterned("pt_br");
      var0[1293] = PyString.fromInterned("pt_BR.ISO8859-1");
      var0[1294] = PyString.fromInterned("pt_br.88591");
      var0[1295] = PyString.fromInterned("pt_BR.ISO8859-1");
      var0[1296] = PyString.fromInterned("pt_br.iso88591");
      var0[1297] = PyString.fromInterned("pt_BR.ISO8859-1");
      var0[1298] = PyString.fromInterned("pt_br.iso885915");
      var0[1299] = PyString.fromInterned("pt_BR.ISO8859-15");
      var0[1300] = PyString.fromInterned("pt_br@euro");
      var0[1301] = PyString.fromInterned("pt_BR.ISO8859-15");
      var0[1302] = PyString.fromInterned("pt_pt");
      var0[1303] = PyString.fromInterned("pt_PT.ISO8859-1");
      var0[1304] = PyString.fromInterned("pt_pt.88591");
      var0[1305] = PyString.fromInterned("pt_PT.ISO8859-1");
      var0[1306] = PyString.fromInterned("pt_pt.iso88591");
      var0[1307] = PyString.fromInterned("pt_PT.ISO8859-1");
      var0[1308] = PyString.fromInterned("pt_pt.iso885915");
      var0[1309] = PyString.fromInterned("pt_PT.ISO8859-15");
      var0[1310] = PyString.fromInterned("pt_pt.iso885915@euro");
      var0[1311] = PyString.fromInterned("pt_PT.ISO8859-15");
      var0[1312] = PyString.fromInterned("pt_pt.utf8@euro");
      var0[1313] = PyString.fromInterned("pt_PT.UTF-8");
      var0[1314] = PyString.fromInterned("pt_pt@euro");
      var0[1315] = PyString.fromInterned("pt_PT.ISO8859-15");
      var0[1316] = PyString.fromInterned("ro");
      var0[1317] = PyString.fromInterned("ro_RO.ISO8859-2");
      var0[1318] = PyString.fromInterned("ro_ro");
      var0[1319] = PyString.fromInterned("ro_RO.ISO8859-2");
      var0[1320] = PyString.fromInterned("ro_ro.iso88592");
      var0[1321] = PyString.fromInterned("ro_RO.ISO8859-2");
      var0[1322] = PyString.fromInterned("romanian");
      var0[1323] = PyString.fromInterned("ro_RO.ISO8859-2");
      var0[1324] = PyString.fromInterned("ru");
      var0[1325] = PyString.fromInterned("ru_RU.UTF-8");
      var0[1326] = PyString.fromInterned("ru.koi8r");
      var0[1327] = PyString.fromInterned("ru_RU.KOI8-R");
      var0[1328] = PyString.fromInterned("ru_ru");
      var0[1329] = PyString.fromInterned("ru_RU.UTF-8");
      var0[1330] = PyString.fromInterned("ru_ru.cp1251");
      var0[1331] = PyString.fromInterned("ru_RU.CP1251");
      var0[1332] = PyString.fromInterned("ru_ru.iso88595");
      var0[1333] = PyString.fromInterned("ru_RU.ISO8859-5");
      var0[1334] = PyString.fromInterned("ru_ru.koi8r");
      var0[1335] = PyString.fromInterned("ru_RU.KOI8-R");
      var0[1336] = PyString.fromInterned("ru_ru.microsoftcp1251");
      var0[1337] = PyString.fromInterned("ru_RU.CP1251");
      var0[1338] = PyString.fromInterned("ru_ua");
      var0[1339] = PyString.fromInterned("ru_UA.KOI8-U");
      var0[1340] = PyString.fromInterned("ru_ua.cp1251");
      var0[1341] = PyString.fromInterned("ru_UA.CP1251");
      var0[1342] = PyString.fromInterned("ru_ua.koi8u");
      var0[1343] = PyString.fromInterned("ru_UA.KOI8-U");
      var0[1344] = PyString.fromInterned("ru_ua.microsoftcp1251");
      var0[1345] = PyString.fromInterned("ru_UA.CP1251");
      var0[1346] = PyString.fromInterned("rumanian");
      var0[1347] = PyString.fromInterned("ro_RO.ISO8859-2");
      var0[1348] = PyString.fromInterned("russian");
      var0[1349] = PyString.fromInterned("ru_RU.ISO8859-5");
      var0[1350] = PyString.fromInterned("rw");
      var0[1351] = PyString.fromInterned("rw_RW.ISO8859-1");
      var0[1352] = PyString.fromInterned("rw_rw");
      var0[1353] = PyString.fromInterned("rw_RW.ISO8859-1");
      var0[1354] = PyString.fromInterned("rw_rw.iso88591");
      var0[1355] = PyString.fromInterned("rw_RW.ISO8859-1");
      var0[1356] = PyString.fromInterned("sd");
      var0[1357] = PyString.fromInterned("sd_IN@devanagari.UTF-8");
      var0[1358] = PyString.fromInterned("se_no");
      var0[1359] = PyString.fromInterned("se_NO.UTF-8");
      var0[1360] = PyString.fromInterned("serbocroatian");
      var0[1361] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1362] = PyString.fromInterned("sh");
      var0[1363] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1364] = PyString.fromInterned("sh_ba.iso88592@bosnia");
      var0[1365] = PyString.fromInterned("sr_CS.ISO8859-2");
      var0[1366] = PyString.fromInterned("sh_hr");
      var0[1367] = PyString.fromInterned("sh_HR.ISO8859-2");
      var0[1368] = PyString.fromInterned("sh_hr.iso88592");
      var0[1369] = PyString.fromInterned("hr_HR.ISO8859-2");
      var0[1370] = PyString.fromInterned("sh_sp");
      var0[1371] = PyString.fromInterned("sr_CS.ISO8859-2");
      var0[1372] = PyString.fromInterned("sh_yu");
      var0[1373] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1374] = PyString.fromInterned("si");
      var0[1375] = PyString.fromInterned("si_LK.UTF-8");
      var0[1376] = PyString.fromInterned("si_lk");
      var0[1377] = PyString.fromInterned("si_LK.UTF-8");
      var0[1378] = PyString.fromInterned("sinhala");
      var0[1379] = PyString.fromInterned("si_LK.UTF-8");
      var0[1380] = PyString.fromInterned("sk");
      var0[1381] = PyString.fromInterned("sk_SK.ISO8859-2");
      var0[1382] = PyString.fromInterned("sk_sk");
      var0[1383] = PyString.fromInterned("sk_SK.ISO8859-2");
      var0[1384] = PyString.fromInterned("sk_sk.iso88592");
      var0[1385] = PyString.fromInterned("sk_SK.ISO8859-2");
      var0[1386] = PyString.fromInterned("sl");
      var0[1387] = PyString.fromInterned("sl_SI.ISO8859-2");
      var0[1388] = PyString.fromInterned("sl_cs");
      var0[1389] = PyString.fromInterned("sl_CS.ISO8859-2");
      var0[1390] = PyString.fromInterned("sl_si");
      var0[1391] = PyString.fromInterned("sl_SI.ISO8859-2");
      var0[1392] = PyString.fromInterned("sl_si.iso88592");
      var0[1393] = PyString.fromInterned("sl_SI.ISO8859-2");
      var0[1394] = PyString.fromInterned("slovak");
      var0[1395] = PyString.fromInterned("sk_SK.ISO8859-2");
      var0[1396] = PyString.fromInterned("slovene");
      var0[1397] = PyString.fromInterned("sl_SI.ISO8859-2");
      var0[1398] = PyString.fromInterned("slovenian");
      var0[1399] = PyString.fromInterned("sl_SI.ISO8859-2");
      var0[1400] = PyString.fromInterned("sp");
      var0[1401] = PyString.fromInterned("sr_CS.ISO8859-5");
      var0[1402] = PyString.fromInterned("sp_yu");
      var0[1403] = PyString.fromInterned("sr_CS.ISO8859-5");
      var0[1404] = PyString.fromInterned("spanish");
      var0[1405] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[1406] = PyString.fromInterned("spanish.iso88591");
      var0[1407] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[1408] = PyString.fromInterned("spanish_spain");
      var0[1409] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[1410] = PyString.fromInterned("spanish_spain.8859");
      var0[1411] = PyString.fromInterned("es_ES.ISO8859-1");
      var0[1412] = PyString.fromInterned("sq");
      var0[1413] = PyString.fromInterned("sq_AL.ISO8859-2");
      var0[1414] = PyString.fromInterned("sq_al");
      var0[1415] = PyString.fromInterned("sq_AL.ISO8859-2");
      var0[1416] = PyString.fromInterned("sq_al.iso88592");
      var0[1417] = PyString.fromInterned("sq_AL.ISO8859-2");
      var0[1418] = PyString.fromInterned("sr");
      var0[1419] = PyString.fromInterned("sr_RS.UTF-8");
      var0[1420] = PyString.fromInterned("sr@cyrillic");
      var0[1421] = PyString.fromInterned("sr_RS.UTF-8");
      var0[1422] = PyString.fromInterned("sr@latin");
      var0[1423] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1424] = PyString.fromInterned("sr@latn");
      var0[1425] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1426] = PyString.fromInterned("sr_cs");
      var0[1427] = PyString.fromInterned("sr_RS.UTF-8");
      var0[1428] = PyString.fromInterned("sr_cs.iso88592");
      var0[1429] = PyString.fromInterned("sr_CS.ISO8859-2");
      var0[1430] = PyString.fromInterned("sr_cs.iso88592@latn");
      var0[1431] = PyString.fromInterned("sr_CS.ISO8859-2");
      var0[1432] = PyString.fromInterned("sr_cs.iso88595");
      var0[1433] = PyString.fromInterned("sr_CS.ISO8859-5");
      var0[1434] = PyString.fromInterned("sr_cs.utf8@latn");
      var0[1435] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1436] = PyString.fromInterned("sr_cs@latn");
      var0[1437] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1438] = PyString.fromInterned("sr_me");
      var0[1439] = PyString.fromInterned("sr_ME.UTF-8");
      var0[1440] = PyString.fromInterned("sr_rs");
      var0[1441] = PyString.fromInterned("sr_RS.UTF-8");
      var0[1442] = PyString.fromInterned("sr_rs.utf8@latn");
      var0[1443] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1444] = PyString.fromInterned("sr_rs@latin");
      var0[1445] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1446] = PyString.fromInterned("sr_rs@latn");
      var0[1447] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1448] = PyString.fromInterned("sr_sp");
      var0[1449] = PyString.fromInterned("sr_CS.ISO8859-2");
      var0[1450] = PyString.fromInterned("sr_yu");
      var0[1451] = PyString.fromInterned("sr_RS.UTF-8@latin");
      var0[1452] = PyString.fromInterned("sr_yu.cp1251@cyrillic");
      var0[1453] = PyString.fromInterned("sr_CS.CP1251");
      var0[1454] = PyString.fromInterned("sr_yu.iso88592");
      var0[1455] = PyString.fromInterned("sr_CS.ISO8859-2");
      var0[1456] = PyString.fromInterned("sr_yu.iso88595");
      var0[1457] = PyString.fromInterned("sr_CS.ISO8859-5");
      var0[1458] = PyString.fromInterned("sr_yu.iso88595@cyrillic");
      var0[1459] = PyString.fromInterned("sr_CS.ISO8859-5");
      var0[1460] = PyString.fromInterned("sr_yu.microsoftcp1251@cyrillic");
      var0[1461] = PyString.fromInterned("sr_CS.CP1251");
      var0[1462] = PyString.fromInterned("sr_yu.utf8@cyrillic");
      var0[1463] = PyString.fromInterned("sr_RS.UTF-8");
      var0[1464] = PyString.fromInterned("sr_yu@cyrillic");
      var0[1465] = PyString.fromInterned("sr_RS.UTF-8");
      var0[1466] = PyString.fromInterned("ss");
      var0[1467] = PyString.fromInterned("ss_ZA.ISO8859-1");
      var0[1468] = PyString.fromInterned("ss_za");
      var0[1469] = PyString.fromInterned("ss_ZA.ISO8859-1");
      var0[1470] = PyString.fromInterned("ss_za.iso88591");
      var0[1471] = PyString.fromInterned("ss_ZA.ISO8859-1");
      var0[1472] = PyString.fromInterned("st");
      var0[1473] = PyString.fromInterned("st_ZA.ISO8859-1");
      var0[1474] = PyString.fromInterned("st_za");
      var0[1475] = PyString.fromInterned("st_ZA.ISO8859-1");
      var0[1476] = PyString.fromInterned("st_za.iso88591");
      var0[1477] = PyString.fromInterned("st_ZA.ISO8859-1");
      var0[1478] = PyString.fromInterned("sv");
      var0[1479] = PyString.fromInterned("sv_SE.ISO8859-1");
      var0[1480] = PyString.fromInterned("sv.iso885915");
      var0[1481] = PyString.fromInterned("sv_SE.ISO8859-15");
      var0[1482] = PyString.fromInterned("sv_fi");
      var0[1483] = PyString.fromInterned("sv_FI.ISO8859-1");
      var0[1484] = PyString.fromInterned("sv_fi.iso88591");
      var0[1485] = PyString.fromInterned("sv_FI.ISO8859-1");
      var0[1486] = PyString.fromInterned("sv_fi.iso885915");
      var0[1487] = PyString.fromInterned("sv_FI.ISO8859-15");
      var0[1488] = PyString.fromInterned("sv_fi.iso885915@euro");
      var0[1489] = PyString.fromInterned("sv_FI.ISO8859-15");
      var0[1490] = PyString.fromInterned("sv_fi.utf8@euro");
      var0[1491] = PyString.fromInterned("sv_FI.UTF-8");
      var0[1492] = PyString.fromInterned("sv_fi@euro");
      var0[1493] = PyString.fromInterned("sv_FI.ISO8859-15");
      var0[1494] = PyString.fromInterned("sv_se");
      var0[1495] = PyString.fromInterned("sv_SE.ISO8859-1");
      var0[1496] = PyString.fromInterned("sv_se.88591");
      var0[1497] = PyString.fromInterned("sv_SE.ISO8859-1");
      var0[1498] = PyString.fromInterned("sv_se.iso88591");
      var0[1499] = PyString.fromInterned("sv_SE.ISO8859-1");
      var0[1500] = PyString.fromInterned("sv_se.iso885915");
      var0[1501] = PyString.fromInterned("sv_SE.ISO8859-15");
      var0[1502] = PyString.fromInterned("sv_se@euro");
      var0[1503] = PyString.fromInterned("sv_SE.ISO8859-15");
      var0[1504] = PyString.fromInterned("swedish");
      var0[1505] = PyString.fromInterned("sv_SE.ISO8859-1");
      var0[1506] = PyString.fromInterned("swedish.iso88591");
      var0[1507] = PyString.fromInterned("sv_SE.ISO8859-1");
      var0[1508] = PyString.fromInterned("ta");
      var0[1509] = PyString.fromInterned("ta_IN.TSCII-0");
      var0[1510] = PyString.fromInterned("ta_in");
      var0[1511] = PyString.fromInterned("ta_IN.TSCII-0");
      var0[1512] = PyString.fromInterned("ta_in.tscii");
      var0[1513] = PyString.fromInterned("ta_IN.TSCII-0");
      var0[1514] = PyString.fromInterned("ta_in.tscii0");
      var0[1515] = PyString.fromInterned("ta_IN.TSCII-0");
      var0[1516] = PyString.fromInterned("te");
      var0[1517] = PyString.fromInterned("te_IN.UTF-8");
      var0[1518] = PyString.fromInterned("tg");
      var0[1519] = PyString.fromInterned("tg_TJ.KOI8-C");
      var0[1520] = PyString.fromInterned("tg_tj");
      var0[1521] = PyString.fromInterned("tg_TJ.KOI8-C");
      var0[1522] = PyString.fromInterned("tg_tj.koi8c");
      var0[1523] = PyString.fromInterned("tg_TJ.KOI8-C");
      var0[1524] = PyString.fromInterned("th");
      var0[1525] = PyString.fromInterned("th_TH.ISO8859-11");
      var0[1526] = PyString.fromInterned("th_th");
      var0[1527] = PyString.fromInterned("th_TH.ISO8859-11");
      var0[1528] = PyString.fromInterned("th_th.iso885911");
      var0[1529] = PyString.fromInterned("th_TH.ISO8859-11");
      var0[1530] = PyString.fromInterned("th_th.tactis");
      var0[1531] = PyString.fromInterned("th_TH.TIS620");
      var0[1532] = PyString.fromInterned("th_th.tis620");
      var0[1533] = PyString.fromInterned("th_TH.TIS620");
      var0[1534] = PyString.fromInterned("thai");
      var0[1535] = PyString.fromInterned("th_TH.ISO8859-11");
      var0[1536] = PyString.fromInterned("tl");
      var0[1537] = PyString.fromInterned("tl_PH.ISO8859-1");
      var0[1538] = PyString.fromInterned("tl_ph");
      var0[1539] = PyString.fromInterned("tl_PH.ISO8859-1");
      var0[1540] = PyString.fromInterned("tl_ph.iso88591");
      var0[1541] = PyString.fromInterned("tl_PH.ISO8859-1");
      var0[1542] = PyString.fromInterned("tn");
      var0[1543] = PyString.fromInterned("tn_ZA.ISO8859-15");
      var0[1544] = PyString.fromInterned("tn_za");
      var0[1545] = PyString.fromInterned("tn_ZA.ISO8859-15");
      var0[1546] = PyString.fromInterned("tn_za.iso885915");
      var0[1547] = PyString.fromInterned("tn_ZA.ISO8859-15");
      var0[1548] = PyString.fromInterned("tr");
      var0[1549] = PyString.fromInterned("tr_TR.ISO8859-9");
      var0[1550] = PyString.fromInterned("tr_tr");
      var0[1551] = PyString.fromInterned("tr_TR.ISO8859-9");
      var0[1552] = PyString.fromInterned("tr_tr.iso88599");
      var0[1553] = PyString.fromInterned("tr_TR.ISO8859-9");
      var0[1554] = PyString.fromInterned("ts");
      var0[1555] = PyString.fromInterned("ts_ZA.ISO8859-1");
      var0[1556] = PyString.fromInterned("ts_za");
      var0[1557] = PyString.fromInterned("ts_ZA.ISO8859-1");
      var0[1558] = PyString.fromInterned("ts_za.iso88591");
      var0[1559] = PyString.fromInterned("ts_ZA.ISO8859-1");
      var0[1560] = PyString.fromInterned("tt");
      var0[1561] = PyString.fromInterned("tt_RU.TATAR-CYR");
      var0[1562] = PyString.fromInterned("tt_ru");
      var0[1563] = PyString.fromInterned("tt_RU.TATAR-CYR");
      var0[1564] = PyString.fromInterned("tt_ru.koi8c");
      var0[1565] = PyString.fromInterned("tt_RU.KOI8-C");
      var0[1566] = PyString.fromInterned("tt_ru.tatarcyr");
      var0[1567] = PyString.fromInterned("tt_RU.TATAR-CYR");
      var0[1568] = PyString.fromInterned("turkish");
      var0[1569] = PyString.fromInterned("tr_TR.ISO8859-9");
      var0[1570] = PyString.fromInterned("turkish.iso88599");
      var0[1571] = PyString.fromInterned("tr_TR.ISO8859-9");
      var0[1572] = PyString.fromInterned("uk");
      var0[1573] = PyString.fromInterned("uk_UA.KOI8-U");
      var0[1574] = PyString.fromInterned("uk_ua");
      var0[1575] = PyString.fromInterned("uk_UA.KOI8-U");
      var0[1576] = PyString.fromInterned("uk_ua.cp1251");
      var0[1577] = PyString.fromInterned("uk_UA.CP1251");
      var0[1578] = PyString.fromInterned("uk_ua.iso88595");
      var0[1579] = PyString.fromInterned("uk_UA.ISO8859-5");
      var0[1580] = PyString.fromInterned("uk_ua.koi8u");
      var0[1581] = PyString.fromInterned("uk_UA.KOI8-U");
      var0[1582] = PyString.fromInterned("uk_ua.microsoftcp1251");
      var0[1583] = PyString.fromInterned("uk_UA.CP1251");
      var0[1584] = PyString.fromInterned("univ");
      var0[1585] = PyString.fromInterned("en_US.utf");
      var0[1586] = PyString.fromInterned("universal");
      var0[1587] = PyString.fromInterned("en_US.utf");
      var0[1588] = PyString.fromInterned("universal.utf8@ucs4");
      var0[1589] = PyString.fromInterned("en_US.UTF-8");
      var0[1590] = PyString.fromInterned("ur");
      var0[1591] = PyString.fromInterned("ur_PK.CP1256");
      var0[1592] = PyString.fromInterned("ur_pk");
      var0[1593] = PyString.fromInterned("ur_PK.CP1256");
      var0[1594] = PyString.fromInterned("ur_pk.cp1256");
      var0[1595] = PyString.fromInterned("ur_PK.CP1256");
      var0[1596] = PyString.fromInterned("ur_pk.microsoftcp1256");
      var0[1597] = PyString.fromInterned("ur_PK.CP1256");
      var0[1598] = PyString.fromInterned("uz");
      var0[1599] = PyString.fromInterned("uz_UZ.UTF-8");
      var0[1600] = PyString.fromInterned("uz_uz");
      var0[1601] = PyString.fromInterned("uz_UZ.UTF-8");
      var0[1602] = PyString.fromInterned("uz_uz.iso88591");
      var0[1603] = PyString.fromInterned("uz_UZ.ISO8859-1");
      var0[1604] = PyString.fromInterned("uz_uz.utf8@cyrillic");
      var0[1605] = PyString.fromInterned("uz_UZ.UTF-8");
      var0[1606] = PyString.fromInterned("uz_uz@cyrillic");
      var0[1607] = PyString.fromInterned("uz_UZ.UTF-8");
      var0[1608] = PyString.fromInterned("ve");
      var0[1609] = PyString.fromInterned("ve_ZA.UTF-8");
      var0[1610] = PyString.fromInterned("ve_za");
      var0[1611] = PyString.fromInterned("ve_ZA.UTF-8");
      var0[1612] = PyString.fromInterned("vi");
      var0[1613] = PyString.fromInterned("vi_VN.TCVN");
      var0[1614] = PyString.fromInterned("vi_vn");
      var0[1615] = PyString.fromInterned("vi_VN.TCVN");
      var0[1616] = PyString.fromInterned("vi_vn.tcvn");
      var0[1617] = PyString.fromInterned("vi_VN.TCVN");
      var0[1618] = PyString.fromInterned("vi_vn.tcvn5712");
      var0[1619] = PyString.fromInterned("vi_VN.TCVN");
      var0[1620] = PyString.fromInterned("vi_vn.viscii");
      var0[1621] = PyString.fromInterned("vi_VN.VISCII");
      var0[1622] = PyString.fromInterned("vi_vn.viscii111");
      var0[1623] = PyString.fromInterned("vi_VN.VISCII");
      var0[1624] = PyString.fromInterned("wa");
      var0[1625] = PyString.fromInterned("wa_BE.ISO8859-1");
      var0[1626] = PyString.fromInterned("wa_be");
      var0[1627] = PyString.fromInterned("wa_BE.ISO8859-1");
      var0[1628] = PyString.fromInterned("wa_be.iso88591");
      var0[1629] = PyString.fromInterned("wa_BE.ISO8859-1");
      var0[1630] = PyString.fromInterned("wa_be.iso885915");
      var0[1631] = PyString.fromInterned("wa_BE.ISO8859-15");
      var0[1632] = PyString.fromInterned("wa_be.iso885915@euro");
      var0[1633] = PyString.fromInterned("wa_BE.ISO8859-15");
      var0[1634] = PyString.fromInterned("wa_be@euro");
      var0[1635] = PyString.fromInterned("wa_BE.ISO8859-15");
      var0[1636] = PyString.fromInterned("xh");
      var0[1637] = PyString.fromInterned("xh_ZA.ISO8859-1");
      var0[1638] = PyString.fromInterned("xh_za");
      var0[1639] = PyString.fromInterned("xh_ZA.ISO8859-1");
      var0[1640] = PyString.fromInterned("xh_za.iso88591");
      var0[1641] = PyString.fromInterned("xh_ZA.ISO8859-1");
      var0[1642] = PyString.fromInterned("yi");
      var0[1643] = PyString.fromInterned("yi_US.CP1255");
      var0[1644] = PyString.fromInterned("yi_us");
      var0[1645] = PyString.fromInterned("yi_US.CP1255");
      var0[1646] = PyString.fromInterned("yi_us.cp1255");
      var0[1647] = PyString.fromInterned("yi_US.CP1255");
      var0[1648] = PyString.fromInterned("yi_us.microsoftcp1255");
      var0[1649] = PyString.fromInterned("yi_US.CP1255");
      var0[1650] = PyString.fromInterned("zh");
      var0[1651] = PyString.fromInterned("zh_CN.eucCN");
      var0[1652] = PyString.fromInterned("zh_cn");
      var0[1653] = PyString.fromInterned("zh_CN.gb2312");
      var0[1654] = PyString.fromInterned("zh_cn.big5");
      var0[1655] = PyString.fromInterned("zh_TW.big5");
      var0[1656] = PyString.fromInterned("zh_cn.euc");
      var0[1657] = PyString.fromInterned("zh_CN.eucCN");
      var0[1658] = PyString.fromInterned("zh_cn.gb18030");
      var0[1659] = PyString.fromInterned("zh_CN.gb18030");
      var0[1660] = PyString.fromInterned("zh_cn.gb2312");
      var0[1661] = PyString.fromInterned("zh_CN.gb2312");
      var0[1662] = PyString.fromInterned("zh_cn.gbk");
      var0[1663] = PyString.fromInterned("zh_CN.gbk");
      var0[1664] = PyString.fromInterned("zh_hk");
      var0[1665] = PyString.fromInterned("zh_HK.big5hkscs");
      var0[1666] = PyString.fromInterned("zh_hk.big5");
      var0[1667] = PyString.fromInterned("zh_HK.big5");
      var0[1668] = PyString.fromInterned("zh_hk.big5hk");
      var0[1669] = PyString.fromInterned("zh_HK.big5hkscs");
      var0[1670] = PyString.fromInterned("zh_hk.big5hkscs");
      var0[1671] = PyString.fromInterned("zh_HK.big5hkscs");
      var0[1672] = PyString.fromInterned("zh_tw");
      var0[1673] = PyString.fromInterned("zh_TW.big5");
      var0[1674] = PyString.fromInterned("zh_tw.big5");
      var0[1675] = PyString.fromInterned("zh_TW.big5");
      var0[1676] = PyString.fromInterned("zh_tw.euc");
      var0[1677] = PyString.fromInterned("zh_TW.eucTW");
      var0[1678] = PyString.fromInterned("zh_tw.euctw");
      var0[1679] = PyString.fromInterned("zh_TW.eucTW");
      var0[1680] = PyString.fromInterned("zu");
      var0[1681] = PyString.fromInterned("zu_ZA.ISO8859-1");
      var0[1682] = PyString.fromInterned("zu_za");
      var0[1683] = PyString.fromInterned("zu_ZA.ISO8859-1");
      var0[1684] = PyString.fromInterned("zu_za.iso88591");
      var0[1685] = PyString.fromInterned("zu_ZA.ISO8859-1");
   }

   private static void set$$1(PyObject[] var0) {
      var0[0] = Py.newInteger(1078);
      var0[1] = PyString.fromInterned("af_ZA");
      var0[2] = Py.newInteger(1052);
      var0[3] = PyString.fromInterned("sq_AL");
      var0[4] = Py.newInteger(1156);
      var0[5] = PyString.fromInterned("gsw_FR");
      var0[6] = Py.newInteger(1118);
      var0[7] = PyString.fromInterned("am_ET");
      var0[8] = Py.newInteger(1025);
      var0[9] = PyString.fromInterned("ar_SA");
      var0[10] = Py.newInteger(2049);
      var0[11] = PyString.fromInterned("ar_IQ");
      var0[12] = Py.newInteger(3073);
      var0[13] = PyString.fromInterned("ar_EG");
      var0[14] = Py.newInteger(4097);
      var0[15] = PyString.fromInterned("ar_LY");
      var0[16] = Py.newInteger(5121);
      var0[17] = PyString.fromInterned("ar_DZ");
      var0[18] = Py.newInteger(6145);
      var0[19] = PyString.fromInterned("ar_MA");
      var0[20] = Py.newInteger(7169);
      var0[21] = PyString.fromInterned("ar_TN");
      var0[22] = Py.newInteger(8193);
      var0[23] = PyString.fromInterned("ar_OM");
      var0[24] = Py.newInteger(9217);
      var0[25] = PyString.fromInterned("ar_YE");
      var0[26] = Py.newInteger(10241);
      var0[27] = PyString.fromInterned("ar_SY");
      var0[28] = Py.newInteger(11265);
      var0[29] = PyString.fromInterned("ar_JO");
      var0[30] = Py.newInteger(12289);
      var0[31] = PyString.fromInterned("ar_LB");
      var0[32] = Py.newInteger(13313);
      var0[33] = PyString.fromInterned("ar_KW");
      var0[34] = Py.newInteger(14337);
      var0[35] = PyString.fromInterned("ar_AE");
      var0[36] = Py.newInteger(15361);
      var0[37] = PyString.fromInterned("ar_BH");
      var0[38] = Py.newInteger(16385);
      var0[39] = PyString.fromInterned("ar_QA");
      var0[40] = Py.newInteger(1067);
      var0[41] = PyString.fromInterned("hy_AM");
      var0[42] = Py.newInteger(1101);
      var0[43] = PyString.fromInterned("as_IN");
      var0[44] = Py.newInteger(1068);
      var0[45] = PyString.fromInterned("az_AZ");
      var0[46] = Py.newInteger(2092);
      var0[47] = PyString.fromInterned("az_AZ");
      var0[48] = Py.newInteger(1133);
      var0[49] = PyString.fromInterned("ba_RU");
      var0[50] = Py.newInteger(1069);
      var0[51] = PyString.fromInterned("eu_ES");
      var0[52] = Py.newInteger(1059);
      var0[53] = PyString.fromInterned("be_BY");
      var0[54] = Py.newInteger(1093);
      var0[55] = PyString.fromInterned("bn_IN");
      var0[56] = Py.newInteger(8218);
      var0[57] = PyString.fromInterned("bs_BA");
      var0[58] = Py.newInteger(5146);
      var0[59] = PyString.fromInterned("bs_BA");
      var0[60] = Py.newInteger(1150);
      var0[61] = PyString.fromInterned("br_FR");
      var0[62] = Py.newInteger(1026);
      var0[63] = PyString.fromInterned("bg_BG");
      var0[64] = Py.newInteger(1027);
      var0[65] = PyString.fromInterned("ca_ES");
      var0[66] = Py.newInteger(4);
      var0[67] = PyString.fromInterned("zh_CHS");
      var0[68] = Py.newInteger(1028);
      var0[69] = PyString.fromInterned("zh_TW");
      var0[70] = Py.newInteger(2052);
      var0[71] = PyString.fromInterned("zh_CN");
      var0[72] = Py.newInteger(3076);
      var0[73] = PyString.fromInterned("zh_HK");
      var0[74] = Py.newInteger(4100);
      var0[75] = PyString.fromInterned("zh_SG");
      var0[76] = Py.newInteger(5124);
      var0[77] = PyString.fromInterned("zh_MO");
      var0[78] = Py.newInteger(31748);
      var0[79] = PyString.fromInterned("zh_CHT");
      var0[80] = Py.newInteger(1155);
      var0[81] = PyString.fromInterned("co_FR");
      var0[82] = Py.newInteger(1050);
      var0[83] = PyString.fromInterned("hr_HR");
      var0[84] = Py.newInteger(4122);
      var0[85] = PyString.fromInterned("hr_BA");
      var0[86] = Py.newInteger(1029);
      var0[87] = PyString.fromInterned("cs_CZ");
      var0[88] = Py.newInteger(1030);
      var0[89] = PyString.fromInterned("da_DK");
      var0[90] = Py.newInteger(1164);
      var0[91] = PyString.fromInterned("gbz_AF");
      var0[92] = Py.newInteger(1125);
      var0[93] = PyString.fromInterned("div_MV");
      var0[94] = Py.newInteger(1043);
      var0[95] = PyString.fromInterned("nl_NL");
      var0[96] = Py.newInteger(2067);
      var0[97] = PyString.fromInterned("nl_BE");
      var0[98] = Py.newInteger(1033);
      var0[99] = PyString.fromInterned("en_US");
      var0[100] = Py.newInteger(2057);
      var0[101] = PyString.fromInterned("en_GB");
      var0[102] = Py.newInteger(3081);
      var0[103] = PyString.fromInterned("en_AU");
      var0[104] = Py.newInteger(4105);
      var0[105] = PyString.fromInterned("en_CA");
      var0[106] = Py.newInteger(5129);
      var0[107] = PyString.fromInterned("en_NZ");
      var0[108] = Py.newInteger(6153);
      var0[109] = PyString.fromInterned("en_IE");
      var0[110] = Py.newInteger(7177);
      var0[111] = PyString.fromInterned("en_ZA");
      var0[112] = Py.newInteger(8201);
      var0[113] = PyString.fromInterned("en_JA");
      var0[114] = Py.newInteger(9225);
      var0[115] = PyString.fromInterned("en_CB");
      var0[116] = Py.newInteger(10249);
      var0[117] = PyString.fromInterned("en_BZ");
      var0[118] = Py.newInteger(11273);
      var0[119] = PyString.fromInterned("en_TT");
      var0[120] = Py.newInteger(12297);
      var0[121] = PyString.fromInterned("en_ZW");
      var0[122] = Py.newInteger(13321);
      var0[123] = PyString.fromInterned("en_PH");
      var0[124] = Py.newInteger(16393);
      var0[125] = PyString.fromInterned("en_IN");
      var0[126] = Py.newInteger(17417);
      var0[127] = PyString.fromInterned("en_MY");
      var0[128] = Py.newInteger(18441);
      var0[129] = PyString.fromInterned("en_IN");
      var0[130] = Py.newInteger(1061);
      var0[131] = PyString.fromInterned("et_EE");
      var0[132] = Py.newInteger(1080);
      var0[133] = PyString.fromInterned("fo_FO");
      var0[134] = Py.newInteger(1124);
      var0[135] = PyString.fromInterned("fil_PH");
      var0[136] = Py.newInteger(1035);
      var0[137] = PyString.fromInterned("fi_FI");
      var0[138] = Py.newInteger(1036);
      var0[139] = PyString.fromInterned("fr_FR");
      var0[140] = Py.newInteger(2060);
      var0[141] = PyString.fromInterned("fr_BE");
      var0[142] = Py.newInteger(3084);
      var0[143] = PyString.fromInterned("fr_CA");
      var0[144] = Py.newInteger(4108);
      var0[145] = PyString.fromInterned("fr_CH");
      var0[146] = Py.newInteger(5132);
      var0[147] = PyString.fromInterned("fr_LU");
      var0[148] = Py.newInteger(6156);
      var0[149] = PyString.fromInterned("fr_MC");
      var0[150] = Py.newInteger(1122);
      var0[151] = PyString.fromInterned("fy_NL");
      var0[152] = Py.newInteger(1110);
      var0[153] = PyString.fromInterned("gl_ES");
      var0[154] = Py.newInteger(1079);
      var0[155] = PyString.fromInterned("ka_GE");
      var0[156] = Py.newInteger(1031);
      var0[157] = PyString.fromInterned("de_DE");
      var0[158] = Py.newInteger(2055);
      var0[159] = PyString.fromInterned("de_CH");
      var0[160] = Py.newInteger(3079);
      var0[161] = PyString.fromInterned("de_AT");
      var0[162] = Py.newInteger(4103);
      var0[163] = PyString.fromInterned("de_LU");
      var0[164] = Py.newInteger(5127);
      var0[165] = PyString.fromInterned("de_LI");
      var0[166] = Py.newInteger(1032);
      var0[167] = PyString.fromInterned("el_GR");
      var0[168] = Py.newInteger(1135);
      var0[169] = PyString.fromInterned("kl_GL");
      var0[170] = Py.newInteger(1095);
      var0[171] = PyString.fromInterned("gu_IN");
      var0[172] = Py.newInteger(1128);
      var0[173] = PyString.fromInterned("ha_NG");
      var0[174] = Py.newInteger(1037);
      var0[175] = PyString.fromInterned("he_IL");
      var0[176] = Py.newInteger(1081);
      var0[177] = PyString.fromInterned("hi_IN");
      var0[178] = Py.newInteger(1038);
      var0[179] = PyString.fromInterned("hu_HU");
      var0[180] = Py.newInteger(1039);
      var0[181] = PyString.fromInterned("is_IS");
      var0[182] = Py.newInteger(1057);
      var0[183] = PyString.fromInterned("id_ID");
      var0[184] = Py.newInteger(1117);
      var0[185] = PyString.fromInterned("iu_CA");
      var0[186] = Py.newInteger(2141);
      var0[187] = PyString.fromInterned("iu_CA");
      var0[188] = Py.newInteger(2108);
      var0[189] = PyString.fromInterned("ga_IE");
      var0[190] = Py.newInteger(1040);
      var0[191] = PyString.fromInterned("it_IT");
      var0[192] = Py.newInteger(2064);
      var0[193] = PyString.fromInterned("it_CH");
      var0[194] = Py.newInteger(1041);
      var0[195] = PyString.fromInterned("ja_JP");
      var0[196] = Py.newInteger(1099);
      var0[197] = PyString.fromInterned("kn_IN");
      var0[198] = Py.newInteger(1087);
      var0[199] = PyString.fromInterned("kk_KZ");
      var0[200] = Py.newInteger(1107);
      var0[201] = PyString.fromInterned("kh_KH");
      var0[202] = Py.newInteger(1158);
      var0[203] = PyString.fromInterned("qut_GT");
      var0[204] = Py.newInteger(1159);
      var0[205] = PyString.fromInterned("rw_RW");
      var0[206] = Py.newInteger(1111);
      var0[207] = PyString.fromInterned("kok_IN");
      var0[208] = Py.newInteger(1042);
      var0[209] = PyString.fromInterned("ko_KR");
      var0[210] = Py.newInteger(1088);
      var0[211] = PyString.fromInterned("ky_KG");
      var0[212] = Py.newInteger(1108);
      var0[213] = PyString.fromInterned("lo_LA");
      var0[214] = Py.newInteger(1062);
      var0[215] = PyString.fromInterned("lv_LV");
      var0[216] = Py.newInteger(1063);
      var0[217] = PyString.fromInterned("lt_LT");
      var0[218] = Py.newInteger(2094);
      var0[219] = PyString.fromInterned("dsb_DE");
      var0[220] = Py.newInteger(1134);
      var0[221] = PyString.fromInterned("lb_LU");
      var0[222] = Py.newInteger(1071);
      var0[223] = PyString.fromInterned("mk_MK");
      var0[224] = Py.newInteger(1086);
      var0[225] = PyString.fromInterned("ms_MY");
      var0[226] = Py.newInteger(2110);
      var0[227] = PyString.fromInterned("ms_BN");
      var0[228] = Py.newInteger(1100);
      var0[229] = PyString.fromInterned("ml_IN");
      var0[230] = Py.newInteger(1082);
      var0[231] = PyString.fromInterned("mt_MT");
      var0[232] = Py.newInteger(1153);
      var0[233] = PyString.fromInterned("mi_NZ");
      var0[234] = Py.newInteger(1146);
      var0[235] = PyString.fromInterned("arn_CL");
      var0[236] = Py.newInteger(1102);
      var0[237] = PyString.fromInterned("mr_IN");
      var0[238] = Py.newInteger(1148);
      var0[239] = PyString.fromInterned("moh_CA");
      var0[240] = Py.newInteger(1104);
      var0[241] = PyString.fromInterned("mn_MN");
      var0[242] = Py.newInteger(2128);
      var0[243] = PyString.fromInterned("mn_CN");
      var0[244] = Py.newInteger(1121);
      var0[245] = PyString.fromInterned("ne_NP");
      var0[246] = Py.newInteger(1044);
      var0[247] = PyString.fromInterned("nb_NO");
      var0[248] = Py.newInteger(2068);
      var0[249] = PyString.fromInterned("nn_NO");
      var0[250] = Py.newInteger(1154);
      var0[251] = PyString.fromInterned("oc_FR");
      var0[252] = Py.newInteger(1096);
      var0[253] = PyString.fromInterned("or_IN");
      var0[254] = Py.newInteger(1123);
      var0[255] = PyString.fromInterned("ps_AF");
      var0[256] = Py.newInteger(1065);
      var0[257] = PyString.fromInterned("fa_IR");
      var0[258] = Py.newInteger(1045);
      var0[259] = PyString.fromInterned("pl_PL");
      var0[260] = Py.newInteger(1046);
      var0[261] = PyString.fromInterned("pt_BR");
      var0[262] = Py.newInteger(2070);
      var0[263] = PyString.fromInterned("pt_PT");
      var0[264] = Py.newInteger(1094);
      var0[265] = PyString.fromInterned("pa_IN");
      var0[266] = Py.newInteger(1131);
      var0[267] = PyString.fromInterned("quz_BO");
      var0[268] = Py.newInteger(2155);
      var0[269] = PyString.fromInterned("quz_EC");
      var0[270] = Py.newInteger(3179);
      var0[271] = PyString.fromInterned("quz_PE");
      var0[272] = Py.newInteger(1048);
      var0[273] = PyString.fromInterned("ro_RO");
      var0[274] = Py.newInteger(1047);
      var0[275] = PyString.fromInterned("rm_CH");
      var0[276] = Py.newInteger(1049);
      var0[277] = PyString.fromInterned("ru_RU");
      var0[278] = Py.newInteger(9275);
      var0[279] = PyString.fromInterned("smn_FI");
      var0[280] = Py.newInteger(4155);
      var0[281] = PyString.fromInterned("smj_NO");
      var0[282] = Py.newInteger(5179);
      var0[283] = PyString.fromInterned("smj_SE");
      var0[284] = Py.newInteger(1083);
      var0[285] = PyString.fromInterned("se_NO");
      var0[286] = Py.newInteger(2107);
      var0[287] = PyString.fromInterned("se_SE");
      var0[288] = Py.newInteger(3131);
      var0[289] = PyString.fromInterned("se_FI");
      var0[290] = Py.newInteger(8251);
      var0[291] = PyString.fromInterned("sms_FI");
      var0[292] = Py.newInteger(6203);
      var0[293] = PyString.fromInterned("sma_NO");
      var0[294] = Py.newInteger(7227);
      var0[295] = PyString.fromInterned("sma_SE");
      var0[296] = Py.newInteger(1103);
      var0[297] = PyString.fromInterned("sa_IN");
      var0[298] = Py.newInteger(3098);
      var0[299] = PyString.fromInterned("sr_SP");
      var0[300] = Py.newInteger(7194);
      var0[301] = PyString.fromInterned("sr_BA");
      var0[302] = Py.newInteger(2074);
      var0[303] = PyString.fromInterned("sr_SP");
      var0[304] = Py.newInteger(6170);
      var0[305] = PyString.fromInterned("sr_BA");
      var0[306] = Py.newInteger(1115);
      var0[307] = PyString.fromInterned("si_LK");
      var0[308] = Py.newInteger(1132);
      var0[309] = PyString.fromInterned("ns_ZA");
      var0[310] = Py.newInteger(1074);
      var0[311] = PyString.fromInterned("tn_ZA");
      var0[312] = Py.newInteger(1051);
      var0[313] = PyString.fromInterned("sk_SK");
      var0[314] = Py.newInteger(1060);
      var0[315] = PyString.fromInterned("sl_SI");
      var0[316] = Py.newInteger(1034);
      var0[317] = PyString.fromInterned("es_ES");
      var0[318] = Py.newInteger(2058);
      var0[319] = PyString.fromInterned("es_MX");
      var0[320] = Py.newInteger(3082);
      var0[321] = PyString.fromInterned("es_ES");
      var0[322] = Py.newInteger(4106);
      var0[323] = PyString.fromInterned("es_GT");
      var0[324] = Py.newInteger(5130);
      var0[325] = PyString.fromInterned("es_CR");
      var0[326] = Py.newInteger(6154);
      var0[327] = PyString.fromInterned("es_PA");
      var0[328] = Py.newInteger(7178);
      var0[329] = PyString.fromInterned("es_DO");
      var0[330] = Py.newInteger(8202);
      var0[331] = PyString.fromInterned("es_VE");
      var0[332] = Py.newInteger(9226);
      var0[333] = PyString.fromInterned("es_CO");
      var0[334] = Py.newInteger(10250);
      var0[335] = PyString.fromInterned("es_PE");
      var0[336] = Py.newInteger(11274);
      var0[337] = PyString.fromInterned("es_AR");
      var0[338] = Py.newInteger(12298);
      var0[339] = PyString.fromInterned("es_EC");
      var0[340] = Py.newInteger(13322);
      var0[341] = PyString.fromInterned("es_CL");
      var0[342] = Py.newInteger(14346);
      var0[343] = PyString.fromInterned("es_UR");
      var0[344] = Py.newInteger(15370);
      var0[345] = PyString.fromInterned("es_PY");
      var0[346] = Py.newInteger(16394);
      var0[347] = PyString.fromInterned("es_BO");
      var0[348] = Py.newInteger(17418);
      var0[349] = PyString.fromInterned("es_SV");
      var0[350] = Py.newInteger(18442);
      var0[351] = PyString.fromInterned("es_HN");
      var0[352] = Py.newInteger(19466);
      var0[353] = PyString.fromInterned("es_NI");
      var0[354] = Py.newInteger(20490);
      var0[355] = PyString.fromInterned("es_PR");
      var0[356] = Py.newInteger(21514);
      var0[357] = PyString.fromInterned("es_US");
      var0[358] = Py.newInteger(1089);
      var0[359] = PyString.fromInterned("sw_KE");
      var0[360] = Py.newInteger(1053);
      var0[361] = PyString.fromInterned("sv_SE");
      var0[362] = Py.newInteger(2077);
      var0[363] = PyString.fromInterned("sv_FI");
      var0[364] = Py.newInteger(1114);
      var0[365] = PyString.fromInterned("syr_SY");
      var0[366] = Py.newInteger(1064);
      var0[367] = PyString.fromInterned("tg_TJ");
      var0[368] = Py.newInteger(2143);
      var0[369] = PyString.fromInterned("tmz_DZ");
      var0[370] = Py.newInteger(1097);
      var0[371] = PyString.fromInterned("ta_IN");
      var0[372] = Py.newInteger(1092);
      var0[373] = PyString.fromInterned("tt_RU");
      var0[374] = Py.newInteger(1098);
      var0[375] = PyString.fromInterned("te_IN");
      var0[376] = Py.newInteger(1054);
      var0[377] = PyString.fromInterned("th_TH");
      var0[378] = Py.newInteger(2129);
      var0[379] = PyString.fromInterned("bo_BT");
      var0[380] = Py.newInteger(1105);
      var0[381] = PyString.fromInterned("bo_CN");
      var0[382] = Py.newInteger(1055);
      var0[383] = PyString.fromInterned("tr_TR");
      var0[384] = Py.newInteger(1090);
      var0[385] = PyString.fromInterned("tk_TM");
      var0[386] = Py.newInteger(1152);
      var0[387] = PyString.fromInterned("ug_CN");
      var0[388] = Py.newInteger(1058);
      var0[389] = PyString.fromInterned("uk_UA");
      var0[390] = Py.newInteger(1070);
      var0[391] = PyString.fromInterned("wen_DE");
      var0[392] = Py.newInteger(1056);
      var0[393] = PyString.fromInterned("ur_PK");
      var0[394] = Py.newInteger(2080);
      var0[395] = PyString.fromInterned("ur_IN");
      var0[396] = Py.newInteger(1091);
      var0[397] = PyString.fromInterned("uz_UZ");
      var0[398] = Py.newInteger(2115);
      var0[399] = PyString.fromInterned("uz_UZ");
      var0[400] = Py.newInteger(1066);
      var0[401] = PyString.fromInterned("vi_VN");
      var0[402] = Py.newInteger(1106);
      var0[403] = PyString.fromInterned("cy_GB");
      var0[404] = Py.newInteger(1160);
      var0[405] = PyString.fromInterned("wo_SN");
      var0[406] = Py.newInteger(1076);
      var0[407] = PyString.fromInterned("xh_ZA");
      var0[408] = Py.newInteger(1157);
      var0[409] = PyString.fromInterned("sah_RU");
      var0[410] = Py.newInteger(1144);
      var0[411] = PyString.fromInterned("ii_CN");
      var0[412] = Py.newInteger(1130);
      var0[413] = PyString.fromInterned("yo_NG");
      var0[414] = Py.newInteger(1077);
      var0[415] = PyString.fromInterned("zu_ZA");
   }

   public PyObject _print_locale$30(PyFrame var1, ThreadState var2) {
      var1.setline(1822);
      PyString.fromInterned(" Test function.\n    ");
      var1.setline(1823);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1824);
      PyObject[] var10 = new PyObject[]{var1.getlocal(0)};
      PyFunction var11 = new PyFunction(var1.f_globals, var10, _init_categories$31, (PyObject)null);
      var1.setlocal(1, var11);
      var3 = null;
      var1.setline(1828);
      var1.getlocal(1).__call__(var2);
      var1.setline(1829);
      var1.getlocal(0).__delitem__((PyObject)PyString.fromInterned("LC_ALL"));
      var1.setline(1831);
      Py.println(PyString.fromInterned("Locale defaults as determined by getdefaultlocale():"));
      var1.setline(1832);
      Py.println(PyString.fromInterned("-")._mul(Py.newInteger(72)));
      var1.setline(1833);
      PyObject var13 = var1.getglobal("getdefaultlocale").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var13, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1834);
      Py.printComma(PyString.fromInterned("Language: "));
      Object var10000 = var1.getlocal(2);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("(undefined)");
      }

      Py.println((PyObject)var10000);
      var1.setline(1835);
      Py.printComma(PyString.fromInterned("Encoding: "));
      var10000 = var1.getlocal(3);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("(undefined)");
      }

      Py.println((PyObject)var10000);
      var1.setline(1836);
      Py.println();
      var1.setline(1838);
      Py.println(PyString.fromInterned("Locale settings on startup:"));
      var1.setline(1839);
      Py.println(PyString.fromInterned("-")._mul(Py.newInteger(72)));
      var1.setline(1840);
      var13 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1840);
         PyObject var12 = var13.__iternext__();
         PyObject var6;
         PyObject var7;
         PyObject[] var14;
         PyObject[] var15;
         if (var12 == null) {
            var1.setline(1847);
            Py.println();
            var1.setline(1848);
            Py.println(PyString.fromInterned("Locale settings after calling resetlocale():"));
            var1.setline(1849);
            Py.println(PyString.fromInterned("-")._mul(Py.newInteger(72)));
            var1.setline(1850);
            var1.getglobal("resetlocale").__call__(var2);
            var1.setline(1851);
            var13 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(1851);
               var12 = var13.__iternext__();
               if (var12 == null) {
                  label76: {
                     try {
                        var1.setline(1859);
                        var1.getglobal("setlocale").__call__((ThreadState)var2, (PyObject)var1.getglobal("LC_ALL"), (PyObject)PyString.fromInterned(""));
                     } catch (Throwable var9) {
                        Py.setException(var9, var1);
                        var1.setline(1861);
                        Py.println(PyString.fromInterned("NOTE:"));
                        var1.setline(1862);
                        Py.println(PyString.fromInterned("setlocale(LC_ALL, \"\") does not support the default locale"));
                        var1.setline(1863);
                        Py.println(PyString.fromInterned("given in the OS environment variables."));
                        break label76;
                     }

                     var1.setline(1865);
                     Py.println();
                     var1.setline(1866);
                     Py.println(PyString.fromInterned("Locale settings after calling setlocale(LC_ALL, \"\"):"));
                     var1.setline(1867);
                     Py.println(PyString.fromInterned("-")._mul(Py.newInteger(72)));
                     var1.setline(1868);
                     var12 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(1868);
                        var5 = var12.__iternext__();
                        if (var5 == null) {
                           break;
                        }

                        var15 = Py.unpackSequence(var5, 2);
                        var7 = var15[0];
                        var1.setlocal(4, var7);
                        var7 = null;
                        var7 = var15[1];
                        var1.setlocal(5, var7);
                        var7 = null;
                        var1.setline(1869);
                        Py.printComma(var1.getlocal(4));
                        Py.println(PyString.fromInterned("..."));
                        var1.setline(1870);
                        var6 = var1.getglobal("getlocale").__call__(var2, var1.getlocal(5));
                        PyObject[] var16 = Py.unpackSequence(var6, 2);
                        PyObject var8 = var16[0];
                        var1.setlocal(2, var8);
                        var8 = null;
                        var8 = var16[1];
                        var1.setlocal(3, var8);
                        var8 = null;
                        var6 = null;
                        var1.setline(1871);
                        Py.printComma(PyString.fromInterned("   Language: "));
                        var10000 = var1.getlocal(2);
                        if (!((PyObject)var10000).__nonzero__()) {
                           var10000 = PyString.fromInterned("(undefined)");
                        }

                        Py.println((PyObject)var10000);
                        var1.setline(1872);
                        Py.printComma(PyString.fromInterned("   Encoding: "));
                        var10000 = var1.getlocal(3);
                        if (!((PyObject)var10000).__nonzero__()) {
                           var10000 = PyString.fromInterned("(undefined)");
                        }

                        Py.println((PyObject)var10000);
                        var1.setline(1873);
                        Py.println();
                     }
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var14 = Py.unpackSequence(var12, 2);
               var6 = var14[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var14[1];
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(1852);
               Py.printComma(var1.getlocal(4));
               Py.println(PyString.fromInterned("..."));
               var1.setline(1853);
               var5 = var1.getglobal("getlocale").__call__(var2, var1.getlocal(5));
               var15 = Py.unpackSequence(var5, 2);
               var7 = var15[0];
               var1.setlocal(2, var7);
               var7 = null;
               var7 = var15[1];
               var1.setlocal(3, var7);
               var7 = null;
               var5 = null;
               var1.setline(1854);
               Py.printComma(PyString.fromInterned("   Language: "));
               var10000 = var1.getlocal(2);
               if (!((PyObject)var10000).__nonzero__()) {
                  var10000 = PyString.fromInterned("(undefined)");
               }

               Py.println((PyObject)var10000);
               var1.setline(1855);
               Py.printComma(PyString.fromInterned("   Encoding: "));
               var10000 = var1.getlocal(3);
               if (!((PyObject)var10000).__nonzero__()) {
                  var10000 = PyString.fromInterned("(undefined)");
               }

               Py.println((PyObject)var10000);
               var1.setline(1856);
               Py.println();
            }
         }

         var14 = Py.unpackSequence(var12, 2);
         var6 = var14[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var14[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(1841);
         Py.printComma(var1.getlocal(4));
         Py.println(PyString.fromInterned("..."));
         var1.setline(1842);
         var5 = var1.getglobal("getlocale").__call__(var2, var1.getlocal(5));
         var15 = Py.unpackSequence(var5, 2);
         var7 = var15[0];
         var1.setlocal(2, var7);
         var7 = null;
         var7 = var15[1];
         var1.setlocal(3, var7);
         var7 = null;
         var5 = null;
         var1.setline(1843);
         Py.printComma(PyString.fromInterned("   Language: "));
         var10000 = var1.getlocal(2);
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("(undefined)");
         }

         Py.println((PyObject)var10000);
         var1.setline(1844);
         Py.printComma(PyString.fromInterned("   Encoding: "));
         var10000 = var1.getlocal(3);
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("(undefined)");
         }

         Py.println((PyObject)var10000);
         var1.setline(1845);
         Py.println();
      }
   }

   public PyObject _init_categories$31(PyFrame var1, ThreadState var2) {
      var1.setline(1825);
      PyObject var3 = var1.getglobal("globals").__call__(var2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1825);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(1826);
         PyObject var7 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         PyObject var10000 = var7._eq(PyString.fromInterned("LC_"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1827);
            var7 = var1.getlocal(2);
            var1.getlocal(0).__setitem__(var1.getlocal(1), var7);
            var5 = null;
         }
      }
   }

   public locale$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _unicode$1 = Py.newCode(0, var2, var1, "_unicode", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      localeconv$2 = Py.newCode(0, var2, var1, "localeconv", 59, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"category", "value"};
      setlocale$3 = Py.newCode(2, var2, var1, "setlocale", 83, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b"};
      strcoll$4 = Py.newCode(2, var2, var1, "strcoll", 91, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      strxfrm$5 = Py.newCode(1, var2, var1, "strxfrm", 97, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"d"};
      localeconv$6 = Py.newCode(0, var2, var1, "localeconv", 110, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"grouping", "last_interval", "interval"};
      _grouping_intervals$7 = Py.newCode(1, var2, var1, "_grouping_intervals", 124, false, false, self, 7, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"s", "monetary", "conv", "thousands_sep", "grouping", "stripped", "right_spaces", "left_spaces", "groups", "interval"};
      _group$8 = Py.newCode(2, var2, var1, "_group", 140, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "amount", "lpos", "rpos"};
      _strip_padding$9 = Py.newCode(2, var2, var1, "_strip_padding", 171, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"percent", "value", "grouping", "monetary", "additional", "match"};
      format$10 = Py.newCode(5, var2, var1, "format", 185, true, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"percent", "value", "grouping", "monetary", "additional", "formatted", "seps", "parts", "decimal_point"};
      _format$11 = Py.newCode(5, var2, var1, "_format", 198, true, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "val", "grouping", "percents", "new_f", "new_val", "perc", "i", "starcount"};
      format_string$12 = Py.newCode(3, var2, var1, "format_string", 222, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"val", "symbol", "grouping", "international", "conv", "digits", "s", "smb", "precedes", "separated", "sign_pos", "sign"};
      currency$13 = Py.newCode(4, var2, var1, "currency", 256, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"val"};
      str$14 = Py.newCode(1, var2, var1, "str", 301, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "func", "ts", "dd"};
      atof$15 = Py.newCode(2, var2, var1, "atof", 305, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str"};
      atoi$16 = Py.newCode(1, var2, var1, "atoi", 318, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s1"};
      _test$17 = Py.newCode(0, var2, var1, "_test", 322, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "x"};
      f$18 = Py.newCode(1, var2, var1, "<genexpr>", 343, false, false, self, 18, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"localename", "fullname", "langname", "encoding", "norm_encoding", "lookup_name", "code", "defenc"};
      normalize$19 = Py.newCode(1, var2, var1, "normalize", 347, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"localename", "code", "modifier"};
      _parse_localename$20 = Py.newCode(1, var2, var1, "_parse_localename", 415, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"localetuple", "language", "encoding"};
      _build_localename$21 = Py.newCode(1, var2, var1, "_build_localename", 445, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"envvars", "Locale", "Charset", "_locale", "code", "encoding", "os", "lookup", "variable", "localename"};
      getdefaultlocale$22 = Py.newCode(1, var2, var1, "getdefaultlocale", 461, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"category", "localename"};
      getlocale$23 = Py.newCode(1, var2, var1, "getlocale", 519, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"category", "locale"};
      setlocale$24 = Py.newCode(2, var2, var1, "setlocale", 537, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"category"};
      resetlocale$25 = Py.newCode(1, var2, var1, "resetlocale", 554, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"do_setlocale"};
      getpreferredencoding$26 = Py.newCode(1, var2, var1, "getpreferredencoding", 566, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"do_setlocale", "_locale"};
      getpreferredencoding$27 = Py.newCode(1, var2, var1, "getpreferredencoding", 570, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"do_setlocale"};
      getpreferredencoding$28 = Py.newCode(1, var2, var1, "getpreferredencoding", 580, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"do_setlocale", "oldloc", "result"};
      getpreferredencoding$29 = Py.newCode(1, var2, var1, "getpreferredencoding", 585, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"categories", "_init_categories", "lang", "enc", "name", "category"};
      _print_locale$30 = Py.newCode(0, var2, var1, "_print_locale", 1819, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"categories", "k", "v"};
      _init_categories$31 = Py.newCode(1, var2, var1, "_init_categories", 1824, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new locale$py("locale$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(locale$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._unicode$1(var2, var3);
         case 2:
            return this.localeconv$2(var2, var3);
         case 3:
            return this.setlocale$3(var2, var3);
         case 4:
            return this.strcoll$4(var2, var3);
         case 5:
            return this.strxfrm$5(var2, var3);
         case 6:
            return this.localeconv$6(var2, var3);
         case 7:
            return this._grouping_intervals$7(var2, var3);
         case 8:
            return this._group$8(var2, var3);
         case 9:
            return this._strip_padding$9(var2, var3);
         case 10:
            return this.format$10(var2, var3);
         case 11:
            return this._format$11(var2, var3);
         case 12:
            return this.format_string$12(var2, var3);
         case 13:
            return this.currency$13(var2, var3);
         case 14:
            return this.str$14(var2, var3);
         case 15:
            return this.atof$15(var2, var3);
         case 16:
            return this.atoi$16(var2, var3);
         case 17:
            return this._test$17(var2, var3);
         case 18:
            return this.f$18(var2, var3);
         case 19:
            return this.normalize$19(var2, var3);
         case 20:
            return this._parse_localename$20(var2, var3);
         case 21:
            return this._build_localename$21(var2, var3);
         case 22:
            return this.getdefaultlocale$22(var2, var3);
         case 23:
            return this.getlocale$23(var2, var3);
         case 24:
            return this.setlocale$24(var2, var3);
         case 25:
            return this.resetlocale$25(var2, var3);
         case 26:
            return this.getpreferredencoding$26(var2, var3);
         case 27:
            return this.getpreferredencoding$27(var2, var3);
         case 28:
            return this.getpreferredencoding$28(var2, var3);
         case 29:
            return this.getpreferredencoding$29(var2, var3);
         case 30:
            return this._print_locale$30(var2, var3);
         case 31:
            return this._init_categories$31(var2, var3);
         default:
            return null;
      }
   }
}
