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
@Filename("string.py")
public class string$py extends PyFunctionTable implements PyRunnable {
   static string$py self;
   static final PyCode f$0;
   static final PyCode capwords$1;
   static final PyCode f$2;
   static final PyCode maketrans$3;
   static final PyCode _multimap$4;
   static final PyCode __init__$5;
   static final PyCode __getitem__$6;
   static final PyCode _TemplateMetaclass$7;
   static final PyCode __init__$8;
   static final PyCode Template$9;
   static final PyCode __init__$10;
   static final PyCode _invalid$11;
   static final PyCode substitute$12;
   static final PyCode convert$13;
   static final PyCode safe_substitute$14;
   static final PyCode convert$15;
   static final PyCode lower$16;
   static final PyCode upper$17;
   static final PyCode swapcase$18;
   static final PyCode strip$19;
   static final PyCode lstrip$20;
   static final PyCode rstrip$21;
   static final PyCode split$22;
   static final PyCode rsplit$23;
   static final PyCode join$24;
   static final PyCode index$25;
   static final PyCode rindex$26;
   static final PyCode count$27;
   static final PyCode find$28;
   static final PyCode rfind$29;
   static final PyCode atof$30;
   static final PyCode atoi$31;
   static final PyCode atol$32;
   static final PyCode ljust$33;
   static final PyCode rjust$34;
   static final PyCode center$35;
   static final PyCode zfill$36;
   static final PyCode expandtabs$37;
   static final PyCode translate$38;
   static final PyCode capitalize$39;
   static final PyCode replace$40;
   static final PyCode Formatter$41;
   static final PyCode format$42;
   static final PyCode vformat$43;
   static final PyCode _vformat$44;
   static final PyCode get_value$45;
   static final PyCode check_unused_args$46;
   static final PyCode format_field$47;
   static final PyCode convert_field$48;
   static final PyCode parse$49;
   static final PyCode get_field$50;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A collection of string operations (most are no longer used).\n\nWarning: most of the code you see here isn't normally used nowadays.\nBeginning with Python 1.6, many of these functions are implemented as\nmethods on the standard string object. They used to be implemented by\na built-in module called strop, but strop is now obsolete itself.\n\nPublic module variables:\n\nwhitespace -- a string containing all characters considered whitespace\nlowercase -- a string containing all characters considered lowercase letters\nuppercase -- a string containing all characters considered uppercase letters\nletters -- a string containing all characters considered letters\ndigits -- a string containing all characters considered decimal digits\nhexdigits -- a string containing all characters considered hexadecimal digits\noctdigits -- a string containing all characters considered octal digits\npunctuation -- a string containing all characters considered punctuation\nprintable -- a string containing all characters considered printable\n\n"));
      var1.setline(20);
      PyString.fromInterned("A collection of string operations (most are no longer used).\n\nWarning: most of the code you see here isn't normally used nowadays.\nBeginning with Python 1.6, many of these functions are implemented as\nmethods on the standard string object. They used to be implemented by\na built-in module called strop, but strop is now obsolete itself.\n\nPublic module variables:\n\nwhitespace -- a string containing all characters considered whitespace\nlowercase -- a string containing all characters considered lowercase letters\nuppercase -- a string containing all characters considered uppercase letters\nletters -- a string containing all characters considered letters\ndigits -- a string containing all characters considered decimal digits\nhexdigits -- a string containing all characters considered hexadecimal digits\noctdigits -- a string containing all characters considered octal digits\npunctuation -- a string containing all characters considered punctuation\nprintable -- a string containing all characters considered printable\n\n");
      var1.setline(23);
      PyString var3 = PyString.fromInterned(" \t\n\r\u000b\f");
      var1.setlocal("whitespace", var3);
      var3 = null;
      var1.setline(24);
      var3 = PyString.fromInterned("abcdefghijklmnopqrstuvwxyz");
      var1.setlocal("lowercase", var3);
      var3 = null;
      var1.setline(25);
      var3 = PyString.fromInterned("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
      var1.setlocal("uppercase", var3);
      var3 = null;
      var1.setline(26);
      PyObject var6 = var1.getname("lowercase")._add(var1.getname("uppercase"));
      var1.setlocal("letters", var6);
      var3 = null;
      var1.setline(27);
      var6 = var1.getname("lowercase");
      var1.setlocal("ascii_lowercase", var6);
      var3 = null;
      var1.setline(28);
      var6 = var1.getname("uppercase");
      var1.setlocal("ascii_uppercase", var6);
      var3 = null;
      var1.setline(29);
      var6 = var1.getname("ascii_lowercase")._add(var1.getname("ascii_uppercase"));
      var1.setlocal("ascii_letters", var6);
      var3 = null;
      var1.setline(30);
      var3 = PyString.fromInterned("0123456789");
      var1.setlocal("digits", var3);
      var3 = null;
      var1.setline(31);
      var6 = var1.getname("digits")._add(PyString.fromInterned("abcdef"))._add(PyString.fromInterned("ABCDEF"));
      var1.setlocal("hexdigits", var6);
      var3 = null;
      var1.setline(32);
      var3 = PyString.fromInterned("01234567");
      var1.setlocal("octdigits", var3);
      var3 = null;
      var1.setline(33);
      var3 = PyString.fromInterned("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
      var1.setlocal("punctuation", var3);
      var3 = null;
      var1.setline(34);
      var6 = var1.getname("digits")._add(var1.getname("letters"))._add(var1.getname("punctuation"))._add(var1.getname("whitespace"));
      var1.setlocal("printable", var6);
      var3 = null;
      var1.setline(38);
      var6 = var1.getname("map").__call__(var2, var1.getname("chr"), var1.getname("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)));
      var1.setlocal("l", var6);
      var3 = null;
      var1.setline(39);
      var6 = var1.getname("str").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")).__getattr__("join").__call__(var2, var1.getname("l"));
      var1.setlocal("_idmap", var6);
      var3 = null;
      var1.setline(40);
      var1.dellocal("l");
      var1.setline(45);
      PyObject[] var7 = new PyObject[]{var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, capwords$1, PyString.fromInterned("capwords(s [,sep]) -> string\n\n    Split the argument into words using split, capitalize each\n    word using capitalize, and join the capitalized words using\n    join.  If the optional second argument sep is absent or None,\n    runs of whitespace characters are replaced by a single space\n    and leading and trailing whitespace are removed, otherwise\n    sep is used to split and join the words.\n\n    "));
      var1.setlocal("capwords", var8);
      var3 = null;
      var1.setline(60);
      var6 = var1.getname("None");
      var1.setlocal("_idmapL", var6);
      var3 = null;
      var1.setline(61);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, maketrans$3, PyString.fromInterned("maketrans(frm, to) -> string\n\n    Return a translation table (a string of 256 bytes long)\n    suitable for use in string.translate.  The strings frm and to\n    must be of the same length.\n\n    "));
      var1.setlocal("maketrans", var8);
      var3 = null;
      var1.setline(83);
      var6 = imp.importOneAs("re", var1, -1);
      var1.setlocal("_re", var6);
      var3 = null;
      var1.setline(85);
      var7 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("_multimap", var7, _multimap$4);
      var1.setlocal("_multimap", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(102);
      var7 = new PyObject[]{var1.getname("type")};
      var4 = Py.makeClass("_TemplateMetaclass", var7, _TemplateMetaclass$7);
      var1.setlocal("_TemplateMetaclass", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(124);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Template", var7, Template$9);
      var1.setlocal("Template", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(214);
      var6 = var1.getname("ValueError");
      var1.setlocal("index_error", var6);
      var3 = null;
      var1.setline(215);
      var6 = var1.getname("ValueError");
      var1.setlocal("atoi_error", var6);
      var3 = null;
      var1.setline(216);
      var6 = var1.getname("ValueError");
      var1.setlocal("atof_error", var6);
      var3 = null;
      var1.setline(217);
      var6 = var1.getname("ValueError");
      var1.setlocal("atol_error", var6);
      var3 = null;
      var1.setline(220);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, lower$16, PyString.fromInterned("lower(s) -> string\n\n    Return a copy of the string s converted to lowercase.\n\n    "));
      var1.setlocal("lower", var8);
      var3 = null;
      var1.setline(229);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, upper$17, PyString.fromInterned("upper(s) -> string\n\n    Return a copy of the string s converted to uppercase.\n\n    "));
      var1.setlocal("upper", var8);
      var3 = null;
      var1.setline(238);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, swapcase$18, PyString.fromInterned("swapcase(s) -> string\n\n    Return a copy of the string s with upper case characters\n    converted to lowercase and vice versa.\n\n    "));
      var1.setlocal("swapcase", var8);
      var3 = null;
      var1.setline(248);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, strip$19, PyString.fromInterned("strip(s [,chars]) -> string\n\n    Return a copy of the string s with leading and trailing\n    whitespace removed.\n    If chars is given and not None, remove characters in chars instead.\n    If chars is unicode, S will be converted to unicode before stripping.\n\n    "));
      var1.setlocal("strip", var8);
      var3 = null;
      var1.setline(260);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, lstrip$20, PyString.fromInterned("lstrip(s [,chars]) -> string\n\n    Return a copy of the string s with leading whitespace removed.\n    If chars is given and not None, remove characters in chars instead.\n\n    "));
      var1.setlocal("lstrip", var8);
      var3 = null;
      var1.setline(270);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, rstrip$21, PyString.fromInterned("rstrip(s [,chars]) -> string\n\n    Return a copy of the string s with trailing whitespace removed.\n    If chars is given and not None, remove characters in chars instead.\n\n    "));
      var1.setlocal("rstrip", var8);
      var3 = null;
      var1.setline(281);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(-1)};
      var8 = new PyFunction(var1.f_globals, var7, split$22, PyString.fromInterned("split(s [,sep [,maxsplit]]) -> list of strings\n\n    Return a list of the words in the string s, using sep as the\n    delimiter string.  If maxsplit is given, splits at no more than\n    maxsplit places (resulting in at most maxsplit+1 words).  If sep\n    is not specified or is None, any whitespace string is a separator.\n\n    (split and splitfields are synonymous)\n\n    "));
      var1.setlocal("split", var8);
      var3 = null;
      var1.setline(293);
      var6 = var1.getname("split");
      var1.setlocal("splitfields", var6);
      var3 = null;
      var1.setline(296);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(-1)};
      var8 = new PyFunction(var1.f_globals, var7, rsplit$23, PyString.fromInterned("rsplit(s [,sep [,maxsplit]]) -> list of strings\n\n    Return a list of the words in the string s, using sep as the\n    delimiter string, starting at the end of the string and working\n    to the front.  If maxsplit is given, at most maxsplit splits are\n    done. If sep is not specified or is None, any whitespace string\n    is a separator.\n    "));
      var1.setlocal("rsplit", var8);
      var3 = null;
      var1.setline(308);
      var7 = new PyObject[]{PyString.fromInterned(" ")};
      var8 = new PyFunction(var1.f_globals, var7, join$24, PyString.fromInterned("join(list [,sep]) -> string\n\n    Return a string composed of the words in list, with\n    intervening occurrences of sep.  The default separator is a\n    single space.\n\n    (joinfields and join are synonymous)\n\n    "));
      var1.setlocal("join", var8);
      var3 = null;
      var1.setline(319);
      var6 = var1.getname("join");
      var1.setlocal("joinfields", var6);
      var3 = null;
      var1.setline(322);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, index$25, PyString.fromInterned("index(s, sub [,start [,end]]) -> int\n\n    Like find but raises ValueError when the substring is not found.\n\n    "));
      var1.setlocal("index", var8);
      var3 = null;
      var1.setline(331);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, rindex$26, PyString.fromInterned("rindex(s, sub [,start [,end]]) -> int\n\n    Like rfind but raises ValueError when the substring is not found.\n\n    "));
      var1.setlocal("rindex", var8);
      var3 = null;
      var1.setline(340);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, count$27, PyString.fromInterned("count(s, sub[, start[,end]]) -> int\n\n    Return the number of occurrences of substring sub in string\n    s[start:end].  Optional arguments start and end are\n    interpreted as in slice notation.\n\n    "));
      var1.setlocal("count", var8);
      var3 = null;
      var1.setline(351);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, find$28, PyString.fromInterned("find(s, sub [,start [,end]]) -> in\n\n    Return the lowest index in s where substring sub is found,\n    such that sub is contained within s[start,end].  Optional\n    arguments start and end are interpreted as in slice notation.\n\n    Return -1 on failure.\n\n    "));
      var1.setlocal("find", var8);
      var3 = null;
      var1.setline(364);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, rfind$29, PyString.fromInterned("rfind(s, sub [,start [,end]]) -> int\n\n    Return the highest index in s where substring sub is found,\n    such that sub is contained within s[start,end].  Optional\n    arguments start and end are interpreted as in slice notation.\n\n    Return -1 on failure.\n\n    "));
      var1.setlocal("rfind", var8);
      var3 = null;
      var1.setline(377);
      var6 = var1.getname("float");
      var1.setlocal("_float", var6);
      var3 = null;
      var1.setline(378);
      var6 = var1.getname("int");
      var1.setlocal("_int", var6);
      var3 = null;
      var1.setline(379);
      var6 = var1.getname("long");
      var1.setlocal("_long", var6);
      var3 = null;
      var1.setline(382);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, atof$30, PyString.fromInterned("atof(s) -> float\n\n    Return the floating point number represented by the string s.\n\n    "));
      var1.setlocal("atof", var8);
      var3 = null;
      var1.setline(392);
      var7 = new PyObject[]{Py.newInteger(10)};
      var8 = new PyFunction(var1.f_globals, var7, atoi$31, PyString.fromInterned("atoi(s [,base]) -> int\n\n    Return the integer represented by the string s in the given\n    base, which defaults to 10.  The string s must consist of one\n    or more digits, possibly preceded by a sign.  If base is 0, it\n    is chosen from the leading characters of s, 0 for octal, 0x or\n    0X for hexadecimal.  If base is 16, a preceding 0x or 0X is\n    accepted.\n\n    "));
      var1.setlocal("atoi", var8);
      var3 = null;
      var1.setline(407);
      var7 = new PyObject[]{Py.newInteger(10)};
      var8 = new PyFunction(var1.f_globals, var7, atol$32, PyString.fromInterned("atol(s [,base]) -> long\n\n    Return the long integer represented by the string s in the\n    given base, which defaults to 10.  The string s must consist\n    of one or more digits, possibly preceded by a sign.  If base\n    is 0, it is chosen from the leading characters of s, 0 for\n    octal, 0x or 0X for hexadecimal.  If base is 16, a preceding\n    0x or 0X is accepted.  A trailing L or l is not accepted,\n    unless base is 0.\n\n    "));
      var1.setlocal("atol", var8);
      var3 = null;
      var1.setline(423);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, ljust$33, PyString.fromInterned("ljust(s, width[, fillchar]) -> string\n\n    Return a left-justified version of s, in a field of the\n    specified width, padded with spaces as needed.  The string is\n    never truncated.  If specified the fillchar is used instead of spaces.\n\n    "));
      var1.setlocal("ljust", var8);
      var3 = null;
      var1.setline(434);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, rjust$34, PyString.fromInterned("rjust(s, width[, fillchar]) -> string\n\n    Return a right-justified version of s, in a field of the\n    specified width, padded with spaces as needed.  The string is\n    never truncated.  If specified the fillchar is used instead of spaces.\n\n    "));
      var1.setlocal("rjust", var8);
      var3 = null;
      var1.setline(445);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, center$35, PyString.fromInterned("center(s, width[, fillchar]) -> string\n\n    Return a center version of s, in a field of the specified\n    width. padded with spaces as needed.  The string is never\n    truncated.  If specified the fillchar is used instead of spaces.\n\n    "));
      var1.setlocal("center", var8);
      var3 = null;
      var1.setline(458);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, zfill$36, PyString.fromInterned("zfill(x, width) -> string\n\n    Pad a numeric string x with zeros on the left, to fill a field\n    of the specified width.  The string x is never truncated.\n\n    "));
      var1.setlocal("zfill", var8);
      var3 = null;
      var1.setline(471);
      var7 = new PyObject[]{Py.newInteger(8)};
      var8 = new PyFunction(var1.f_globals, var7, expandtabs$37, PyString.fromInterned("expandtabs(s [,tabsize]) -> string\n\n    Return a copy of the string s with all tab characters replaced\n    by the appropriate number of spaces, depending on the current\n    column, and the tabsize (default 8).\n\n    "));
      var1.setlocal("expandtabs", var8);
      var3 = null;
      var1.setline(482);
      var7 = new PyObject[]{PyString.fromInterned("")};
      var8 = new PyFunction(var1.f_globals, var7, translate$38, PyString.fromInterned("translate(s,table [,deletions]) -> string\n\n    Return a copy of the string s, where all characters occurring\n    in the optional argument deletions are removed, and the\n    remaining characters have been mapped through the given\n    translation table, which must be a string of length 256.  The\n    deletions argument is not allowed for Unicode strings.\n\n    "));
      var1.setlocal("translate", var8);
      var3 = null;
      var1.setline(501);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, capitalize$39, PyString.fromInterned("capitalize(s) -> string\n\n    Return a copy of the string s with only its first character\n    capitalized.\n\n    "));
      var1.setlocal("capitalize", var8);
      var3 = null;
      var1.setline(511);
      var7 = new PyObject[]{Py.newInteger(-1)};
      var8 = new PyFunction(var1.f_globals, var7, replace$40, PyString.fromInterned("replace (str, old, new[, maxreplace]) -> string\n\n    Return a copy of string str with all occurrences of substring\n    old replaced by new. If the optional argument maxreplace is\n    given, only the first maxreplace occurrences are replaced.\n\n    "));
      var1.setlocal("replace", var8);
      var3 = null;

      try {
         var1.setline(528);
         String[] var10 = new String[]{"maketrans", "lowercase", "uppercase", "whitespace"};
         var7 = imp.importFrom("strop", var10, var1, -1);
         var4 = var7[0];
         var1.setlocal("maketrans", var4);
         var4 = null;
         var4 = var7[1];
         var1.setlocal("lowercase", var4);
         var4 = null;
         var4 = var7[2];
         var1.setlocal("uppercase", var4);
         var4 = null;
         var4 = var7[3];
         var1.setlocal("whitespace", var4);
         var4 = null;
         var1.setline(529);
         var6 = var1.getname("lowercase")._add(var1.getname("uppercase"));
         var1.setlocal("letters", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(531);
      }

      var1.setline(543);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Formatter", var7, Formatter$41);
      var1.setlocal("Formatter", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject capwords$1(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("capwords(s [,sep]) -> string\n\n    Split the argument into words using split, capitalize each\n    word using capitalize, and join the capitalized words using\n    join.  If the optional second argument sep is absent or None,\n    runs of whitespace characters are replaced by a single space\n    and leading and trailing whitespace are removed, otherwise\n    sep is used to split and join the words.\n\n    ");
      var1.setline(56);
      Object var10000 = var1.getlocal(1);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned(" ");
      }

      PyObject var6 = ((PyObject)var10000).__getattr__("join");
      var1.setline(56);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, f$2, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getlocal(0).__getattr__("split").__call__(var2, var1.getlocal(1)).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var6.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(56);
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

      var1.setline(56);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(56);
         var1.setline(56);
         var6 = var1.getlocal(1).__getattr__("capitalize").__call__(var2);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject maketrans$3(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("maketrans(frm, to) -> string\n\n    Return a translation table (a string of 256 bytes long)\n    suitable for use in string.translate.  The strings frm and to\n    must be of the same length.\n\n    ");
      var1.setline(69);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(70);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("maketrans arguments must have same length"));
      } else {
         var1.setline(72);
         if (var1.getglobal("_idmapL").__not__().__nonzero__()) {
            var1.setline(73);
            var3 = var1.getglobal("list").__call__(var2, var1.getglobal("_idmap"));
            var1.setglobal("_idmapL", var3);
            var3 = null;
         }

         var1.setline(74);
         var3 = var1.getglobal("_idmapL").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(75);
         var3 = var1.getglobal("map").__call__(var2, var1.getglobal("ord"), var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(76);
         var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

         while(true) {
            var1.setline(76);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(78);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var4);
            var1.setline(77);
            PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(3));
            var1.getlocal(2).__setitem__(var1.getlocal(0).__getitem__(var1.getlocal(3)), var5);
            var5 = null;
         }
      }
   }

   public PyObject _multimap$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Helper class for combining multiple mappings.\n\n    Used by .{safe_,}substitute() to combine the mapping and keyword\n    arguments.\n    "));
      var1.setline(90);
      PyString.fromInterned("Helper class for combining multiple mappings.\n\n    Used by .{safe_,}substitute() to combine the mapping and keyword\n    arguments.\n    ");
      var1.setline(91);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(95);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$6, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_primary", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_secondary", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$6(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(97);
         var3 = var1.getlocal(0).__getattr__("_primary").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(99);
            var3 = var1.getlocal(0).__getattr__("_secondary").__getitem__(var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _TemplateMetaclass$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(103);
      PyString var3 = PyString.fromInterned("\n    %(delim)s(?:\n      (?P<escaped>%(delim)s) |   # Escape sequence of two delimiters\n      (?P<named>%(id)s)      |   # delimiter and a Python identifier\n      {(?P<braced>%(id)s)}   |   # delimiter and a braced identifier\n      (?P<invalid>)              # Other ill-formed delimiter exprs\n    )\n    ");
      var1.setlocal("pattern", var3);
      var3 = null;
      var1.setline(112);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      var1.getglobal("super").__call__(var2, var1.getglobal("_TemplateMetaclass"), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(114);
      PyString var3 = PyString.fromInterned("pattern");
      PyObject var10000 = var3._in(var1.getlocal(3));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(115);
         var4 = var1.getlocal(0).__getattr__("pattern");
         var1.setlocal(4, var4);
         var3 = null;
      } else {
         var1.setline(117);
         var4 = var1.getglobal("_TemplateMetaclass").__getattr__("pattern")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("delim"), var1.getglobal("_re").__getattr__("escape").__call__(var2, var1.getlocal(0).__getattr__("delimiter")), PyString.fromInterned("id"), var1.getlocal(0).__getattr__("idpattern")}));
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(121);
      var4 = var1.getglobal("_re").__getattr__("compile").__call__(var2, var1.getlocal(4), var1.getglobal("_re").__getattr__("IGNORECASE")._or(var1.getglobal("_re").__getattr__("VERBOSE")));
      var1.getlocal(0).__setattr__("pattern", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Template$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A string class for supporting $-substitutions."));
      var1.setline(125);
      PyString.fromInterned("A string class for supporting $-substitutions.");
      var1.setline(126);
      PyObject var3 = var1.getname("_TemplateMetaclass");
      var1.setlocal("__metaclass__", var3);
      var3 = null;
      var1.setline(128);
      PyString var4 = PyString.fromInterned("$");
      var1.setlocal("delimiter", var4);
      var3 = null;
      var1.setline(129);
      var4 = PyString.fromInterned("[_a-z][_a-z0-9]*");
      var1.setlocal("idpattern", var4);
      var3 = null;
      var1.setline(131);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$10, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(136);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _invalid$11, (PyObject)null);
      var1.setlocal("_invalid", var6);
      var3 = null;
      var1.setline(148);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, substitute$12, (PyObject)null);
      var1.setlocal("substitute", var6);
      var3 = null;
      var1.setline(174);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, safe_substitute$14, (PyObject)null);
      var1.setlocal("safe_substitute", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$10(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("template", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _invalid$11(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject var3 = var1.getlocal(1).__getattr__("start").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(138);
      var3 = var1.getlocal(0).__getattr__("template").__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(139);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(140);
         PyInteger var4 = Py.newInteger(1);
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(141);
         var4 = Py.newInteger(1);
         var1.setlocal(5, var4);
         var3 = null;
      } else {
         var1.setline(143);
         var3 = var1.getlocal(2)._sub(var1.getglobal("len").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null))));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(144);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(145);
      throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Invalid placeholder in string: line %d, col %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4)}))));
   }

   public PyObject substitute$12(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(149);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(150);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Too many positional arguments")));
      } else {
         var1.setline(151);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(152);
            var3 = var1.getlocal(2);
            var1.setderef(1, var3);
            var3 = null;
         } else {
            var1.setline(153);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(154);
               var3 = var1.getglobal("_multimap").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)));
               var1.setderef(1, var3);
               var3 = null;
            } else {
               var1.setline(156);
               var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
               var1.setderef(1, var3);
               var3 = null;
            }
         }

         var1.setline(158);
         PyObject[] var4 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var4;
         PyCode var10004 = convert$13;
         var4 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
         PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(172);
         var3 = var1.getderef(0).__getattr__("pattern").__getattr__("sub").__call__(var2, var1.getlocal(3), var1.getderef(0).__getattr__("template"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject convert$13(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyObject var10000 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("named"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("braced"));
      }

      PyObject var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(161);
      var3 = var1.getlocal(1);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(162);
         var3 = var1.getderef(0).__getitem__(var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(165);
         var3 = PyString.fromInterned("%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(166);
         PyObject var4 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("escaped"));
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(167);
            var3 = var1.getderef(1).__getattr__("delimiter");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(168);
            var4 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid"));
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(169);
               var1.getderef(1).__getattr__("_invalid").__call__(var2, var1.getlocal(0));
            }

            var1.setline(170);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unrecognized named group in pattern"), (PyObject)var1.getderef(1).__getattr__("pattern")));
         }
      }
   }

   public PyObject safe_substitute$14(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(175);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(176);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Too many positional arguments")));
      } else {
         var1.setline(177);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(178);
            var3 = var1.getlocal(2);
            var1.setderef(1, var3);
            var3 = null;
         } else {
            var1.setline(179);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(180);
               var3 = var1.getglobal("_multimap").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getitem__(Py.newInteger(0)));
               var1.setderef(1, var3);
               var3 = null;
            } else {
               var1.setline(182);
               var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
               var1.setderef(1, var3);
               var3 = null;
            }
         }

         var1.setline(184);
         PyObject[] var4 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var4;
         PyCode var10004 = convert$15;
         var4 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
         PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(205);
         var3 = var1.getderef(0).__getattr__("pattern").__getattr__("sub").__call__(var2, var1.getlocal(3), var1.getderef(0).__getattr__("template"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject convert$15(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyObject var3 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("named"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(186);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyException var7;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(190);
            var3 = PyString.fromInterned("%s")._mod(new PyTuple(new PyObject[]{var1.getderef(0).__getitem__(var1.getlocal(1))}));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var6) {
            var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("KeyError"))) {
               var1.setline(192);
               var3 = var1.getderef(1).__getattr__("delimiter")._add(var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var7;
            }
         }
      } else {
         var1.setline(193);
         PyObject var4 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("braced"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(194);
         var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(196);
               var3 = PyString.fromInterned("%s")._mod(new PyTuple(new PyObject[]{var1.getderef(0).__getitem__(var1.getlocal(2))}));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var5) {
               var7 = Py.setException(var5, var1);
               if (var7.match(var1.getglobal("KeyError"))) {
                  var1.setline(198);
                  var3 = var1.getderef(1).__getattr__("delimiter")._add(PyString.fromInterned("{"))._add(var1.getlocal(2))._add(PyString.fromInterned("}"));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  throw var7;
               }
            }
         } else {
            var1.setline(199);
            var4 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("escaped"));
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(200);
               var3 = var1.getderef(1).__getattr__("delimiter");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(201);
               var4 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid"));
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(202);
                  var3 = var1.getderef(1).__getattr__("delimiter");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(203);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unrecognized named group in pattern"), (PyObject)var1.getderef(1).__getattr__("pattern")));
               }
            }
         }
      }
   }

   public PyObject lower$16(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyString.fromInterned("lower(s) -> string\n\n    Return a copy of the string s converted to lowercase.\n\n    ");
      var1.setline(226);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject upper$17(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyString.fromInterned("upper(s) -> string\n\n    Return a copy of the string s converted to uppercase.\n\n    ");
      var1.setline(235);
      PyObject var3 = var1.getlocal(0).__getattr__("upper").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject swapcase$18(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyString.fromInterned("swapcase(s) -> string\n\n    Return a copy of the string s with upper case characters\n    converted to lowercase and vice versa.\n\n    ");
      var1.setline(245);
      PyObject var3 = var1.getlocal(0).__getattr__("swapcase").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject strip$19(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyString.fromInterned("strip(s [,chars]) -> string\n\n    Return a copy of the string s with leading and trailing\n    whitespace removed.\n    If chars is given and not None, remove characters in chars instead.\n    If chars is unicode, S will be converted to unicode before stripping.\n\n    ");
      var1.setline(257);
      PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lstrip$20(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyString.fromInterned("lstrip(s [,chars]) -> string\n\n    Return a copy of the string s with leading whitespace removed.\n    If chars is given and not None, remove characters in chars instead.\n\n    ");
      var1.setline(267);
      PyObject var3 = var1.getlocal(0).__getattr__("lstrip").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rstrip$21(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyString.fromInterned("rstrip(s [,chars]) -> string\n\n    Return a copy of the string s with trailing whitespace removed.\n    If chars is given and not None, remove characters in chars instead.\n\n    ");
      var1.setline(277);
      PyObject var3 = var1.getlocal(0).__getattr__("rstrip").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject split$22(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("split(s [,sep [,maxsplit]]) -> list of strings\n\n    Return a list of the words in the string s, using sep as the\n    delimiter string.  If maxsplit is given, splits at no more than\n    maxsplit places (resulting in at most maxsplit+1 words).  If sep\n    is not specified or is None, any whitespace string is a separator.\n\n    (split and splitfields are synonymous)\n\n    ");
      var1.setline(292);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rsplit$23(PyFrame var1, ThreadState var2) {
      var1.setline(304);
      PyString.fromInterned("rsplit(s [,sep [,maxsplit]]) -> list of strings\n\n    Return a list of the words in the string s, using sep as the\n    delimiter string, starting at the end of the string and working\n    to the front.  If maxsplit is given, at most maxsplit splits are\n    done. If sep is not specified or is None, any whitespace string\n    is a separator.\n    ");
      var1.setline(305);
      PyObject var3 = var1.getlocal(0).__getattr__("rsplit").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject join$24(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyString.fromInterned("join(list [,sep]) -> string\n\n    Return a string composed of the words in list, with\n    intervening occurrences of sep.  The default separator is a\n    single space.\n\n    (joinfields and join are synonymous)\n\n    ");
      var1.setline(318);
      PyObject var3 = var1.getlocal(1).__getattr__("join").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject index$25(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyString.fromInterned("index(s, sub [,start [,end]]) -> int\n\n    Like find but raises ValueError when the substring is not found.\n\n    ");
      var1.setline(328);
      PyObject var10000 = var1.getlocal(0).__getattr__("index");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject rindex$26(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyString.fromInterned("rindex(s, sub [,start [,end]]) -> int\n\n    Like rfind but raises ValueError when the substring is not found.\n\n    ");
      var1.setline(337);
      PyObject var10000 = var1.getlocal(0).__getattr__("rindex");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject count$27(PyFrame var1, ThreadState var2) {
      var1.setline(347);
      PyString.fromInterned("count(s, sub[, start[,end]]) -> int\n\n    Return the number of occurrences of substring sub in string\n    s[start:end].  Optional arguments start and end are\n    interpreted as in slice notation.\n\n    ");
      var1.setline(348);
      PyObject var10000 = var1.getlocal(0).__getattr__("count");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject find$28(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      PyString.fromInterned("find(s, sub [,start [,end]]) -> in\n\n    Return the lowest index in s where substring sub is found,\n    such that sub is contained within s[start,end].  Optional\n    arguments start and end are interpreted as in slice notation.\n\n    Return -1 on failure.\n\n    ");
      var1.setline(361);
      PyObject var10000 = var1.getlocal(0).__getattr__("find");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject rfind$29(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyString.fromInterned("rfind(s, sub [,start [,end]]) -> int\n\n    Return the highest index in s where substring sub is found,\n    such that sub is contained within s[start,end].  Optional\n    arguments start and end are interpreted as in slice notation.\n\n    Return -1 on failure.\n\n    ");
      var1.setline(374);
      PyObject var10000 = var1.getlocal(0).__getattr__("rfind");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject atof$30(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyString.fromInterned("atof(s) -> float\n\n    Return the floating point number represented by the string s.\n\n    ");
      var1.setline(388);
      PyObject var3 = var1.getglobal("_float").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject atoi$31(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("atoi(s [,base]) -> int\n\n    Return the integer represented by the string s in the given\n    base, which defaults to 10.  The string s must consist of one\n    or more digits, possibly preceded by a sign.  If base is 0, it\n    is chosen from the leading characters of s, 0 for octal, 0x or\n    0X for hexadecimal.  If base is 16, a preceding 0x or 0X is\n    accepted.\n\n    ");
      var1.setline(403);
      PyObject var3 = var1.getglobal("_int").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject atol$32(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyString.fromInterned("atol(s [,base]) -> long\n\n    Return the long integer represented by the string s in the\n    given base, which defaults to 10.  The string s must consist\n    of one or more digits, possibly preceded by a sign.  If base\n    is 0, it is chosen from the leading characters of s, 0 for\n    octal, 0x or 0X for hexadecimal.  If base is 16, a preceding\n    0x or 0X is accepted.  A trailing L or l is not accepted,\n    unless base is 0.\n\n    ");
      var1.setline(419);
      PyObject var3 = var1.getglobal("_long").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ljust$33(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      PyString.fromInterned("ljust(s, width[, fillchar]) -> string\n\n    Return a left-justified version of s, in a field of the\n    specified width, padded with spaces as needed.  The string is\n    never truncated.  If specified the fillchar is used instead of spaces.\n\n    ");
      var1.setline(431);
      PyObject var10000 = var1.getlocal(0).__getattr__("ljust");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject rjust$34(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyString.fromInterned("rjust(s, width[, fillchar]) -> string\n\n    Return a right-justified version of s, in a field of the\n    specified width, padded with spaces as needed.  The string is\n    never truncated.  If specified the fillchar is used instead of spaces.\n\n    ");
      var1.setline(442);
      PyObject var10000 = var1.getlocal(0).__getattr__("rjust");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject center$35(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyString.fromInterned("center(s, width[, fillchar]) -> string\n\n    Return a center version of s, in a field of the specified\n    width. padded with spaces as needed.  The string is never\n    truncated.  If specified the fillchar is used instead of spaces.\n\n    ");
      var1.setline(453);
      PyObject var10000 = var1.getlocal(0).__getattr__("center");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject zfill$36(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyString.fromInterned("zfill(x, width) -> string\n\n    Pad a numeric string x with zeros on the left, to fill a field\n    of the specified width.  The string x is never truncated.\n\n    ");
      var1.setline(465);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__not__().__nonzero__()) {
         var1.setline(466);
         var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(467);
      var3 = var1.getlocal(0).__getattr__("zfill").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject expandtabs$37(PyFrame var1, ThreadState var2) {
      var1.setline(478);
      PyString.fromInterned("expandtabs(s [,tabsize]) -> string\n\n    Return a copy of the string s with all tab characters replaced\n    by the appropriate number of spaces, depending on the current\n    column, and the tabsize (default 8).\n\n    ");
      var1.setline(479);
      PyObject var3 = var1.getlocal(0).__getattr__("expandtabs").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject translate$38(PyFrame var1, ThreadState var2) {
      var1.setline(491);
      PyString.fromInterned("translate(s,table [,deletions]) -> string\n\n    Return a copy of the string s, where all characters occurring\n    in the optional argument deletions are removed, and the\n    remaining characters have been mapped through the given\n    translation table, which must be a string of length 256.  The\n    deletions argument is not allowed for Unicode strings.\n\n    ");
      var1.setline(492);
      PyObject var10000 = var1.getlocal(2);
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(493);
         var3 = var1.getlocal(0).__getattr__("translate").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(498);
         var3 = var1.getlocal(0).__getattr__("translate").__call__(var2, var1.getlocal(1)._add(var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(0), (PyObject)null)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject capitalize$39(PyFrame var1, ThreadState var2) {
      var1.setline(507);
      PyString.fromInterned("capitalize(s) -> string\n\n    Return a copy of the string s with only its first character\n    capitalized.\n\n    ");
      var1.setline(508);
      PyObject var3 = var1.getlocal(0).__getattr__("capitalize").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject replace$40(PyFrame var1, ThreadState var2) {
      var1.setline(518);
      PyString.fromInterned("replace (str, old, new[, maxreplace]) -> string\n\n    Return a copy of string str with all occurrences of substring\n    old replaced by new. If the optional argument maxreplace is\n    given, only the first maxreplace occurrences are replaced.\n\n    ");
      var1.setline(519);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Formatter$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(544);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, format$42, (PyObject)null);
      var1.setlocal("format", var4);
      var3 = null;
      var1.setline(547);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, vformat$43, (PyObject)null);
      var1.setlocal("vformat", var4);
      var3 = null;
      var1.setline(553);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _vformat$44, (PyObject)null);
      var1.setlocal("_vformat", var4);
      var3 = null;
      var1.setline(587);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_value$45, (PyObject)null);
      var1.setlocal("get_value", var4);
      var3 = null;
      var1.setline(594);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, check_unused_args$46, (PyObject)null);
      var1.setlocal("check_unused_args", var4);
      var3 = null;
      var1.setline(598);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_field$47, (PyObject)null);
      var1.setlocal("format_field", var4);
      var3 = null;
      var1.setline(602);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, convert_field$48, (PyObject)null);
      var1.setlocal("convert_field", var4);
      var3 = null;
      var1.setline(620);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$49, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(629);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_field$50, (PyObject)null);
      var1.setlocal("get_field", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject format$42(PyFrame var1, ThreadState var2) {
      var1.setline(545);
      PyObject var3 = var1.getlocal(0).__getattr__("vformat").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject vformat$43(PyFrame var1, ThreadState var2) {
      var1.setline(548);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(549);
      PyObject var10000 = var1.getlocal(0).__getattr__("_vformat");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), Py.newInteger(2)};
      var3 = var10000.__call__(var2, var4);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(550);
      var1.getlocal(0).__getattr__("check_unused_args").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
      var1.setline(551);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _vformat$44(PyFrame var1, ThreadState var2) {
      var1.setline(554);
      PyObject var3 = var1.getlocal(5);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(555);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Max string recursion exceeded")));
      } else {
         var1.setline(556);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(6, var8);
         var3 = null;
         var1.setline(557);
         var3 = var1.getlocal(0).__getattr__("parse").__call__(var2, var1.getlocal(1)).__iter__();

         while(true) {
            var1.setline(557);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(584);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6));
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 4);
            PyObject var6 = var5[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(10, var6);
            var6 = null;
            var1.setline(561);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(562);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(7));
            }

            var1.setline(565);
            PyObject var9 = var1.getlocal(8);
            var10000 = var9._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(571);
               var9 = var1.getlocal(0).__getattr__("get_field").__call__(var2, var1.getlocal(8), var1.getlocal(2), var1.getlocal(3));
               PyObject[] var10 = Py.unpackSequence(var9, 2);
               PyObject var7 = var10[0];
               var1.setlocal(11, var7);
               var7 = null;
               var7 = var10[1];
               var1.setlocal(12, var7);
               var7 = null;
               var5 = null;
               var1.setline(572);
               var1.getlocal(4).__getattr__("add").__call__(var2, var1.getlocal(12));
               var1.setline(575);
               var9 = var1.getlocal(0).__getattr__("convert_field").__call__(var2, var1.getlocal(11), var1.getlocal(10));
               var1.setlocal(11, var9);
               var5 = null;
               var1.setline(578);
               var10000 = var1.getlocal(0).__getattr__("_vformat");
               var5 = new PyObject[]{var1.getlocal(9), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)._sub(Py.newInteger(1))};
               var9 = var10000.__call__(var2, var5);
               var1.setlocal(9, var9);
               var5 = null;
               var1.setline(582);
               var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("format_field").__call__(var2, var1.getlocal(11), var1.getlocal(9)));
            }
         }
      }
   }

   public PyObject get_value$45(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(589);
         var3 = var1.getlocal(2).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(591);
         var3 = var1.getlocal(3).__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject check_unused_args$46(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_field$47(PyFrame var1, ThreadState var2) {
      var1.setline(599);
      PyObject var3 = var1.getglobal("format").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject convert_field$48(PyFrame var1, ThreadState var2) {
      var1.setline(604);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(605);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(606);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("s"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(607);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(608);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("r"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(609);
               var3 = var1.getglobal("repr").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(610);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unknown conversion specifier {0!s}").__getattr__("format").__call__(var2, var1.getlocal(2))));
            }
         }
      }
   }

   public PyObject parse$49(PyFrame var1, ThreadState var2) {
      var1.setline(621);
      PyObject var3 = var1.getlocal(1).__getattr__("_formatter_parser").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_field$50(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyObject var3 = var1.getlocal(1).__getattr__("_formatter_field_name_split").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(632);
      var3 = var1.getlocal(0).__getattr__("get_value").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(636);
      var3 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(636);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(642);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(4)});
            var1.f_lasti = -1;
            return var8;
         }

         PyObject[] var9 = Py.unpackSequence(var7, 2);
         PyObject var6 = var9[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(637);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(638);
            var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(6), var1.getlocal(8));
            var1.setlocal(6, var5);
            var5 = null;
         } else {
            var1.setline(640);
            var5 = var1.getlocal(6).__getitem__(var1.getlocal(8));
            var1.setlocal(6, var5);
            var5 = null;
         }
      }
   }

   public string$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "sep", "_(56_29)"};
      capwords$1 = Py.newCode(2, var2, var1, "capwords", 45, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "x"};
      f$2 = Py.newCode(1, var2, var1, "<genexpr>", 56, false, false, self, 2, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"fromstr", "tostr", "L", "i"};
      maketrans$3 = Py.newCode(2, var2, var1, "maketrans", 61, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _multimap$4 = Py.newCode(0, var2, var1, "_multimap", 85, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "primary", "secondary"};
      __init__$5 = Py.newCode(3, var2, var1, "__init__", 91, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __getitem__$6 = Py.newCode(2, var2, var1, "__getitem__", 95, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _TemplateMetaclass$7 = Py.newCode(0, var2, var1, "_TemplateMetaclass", 102, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "name", "bases", "dct", "pattern"};
      __init__$8 = Py.newCode(4, var2, var1, "__init__", 112, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Template$9 = Py.newCode(0, var2, var1, "Template", 124, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "template"};
      __init__$10 = Py.newCode(2, var2, var1, "__init__", 131, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mo", "i", "lines", "colno", "lineno"};
      _invalid$11 = Py.newCode(2, var2, var1, "_invalid", 136, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kws", "convert", "mapping"};
      String[] var10001 = var2;
      string$py var10007 = self;
      var2 = new String[]{"self", "mapping"};
      substitute$12 = Py.newCode(3, var10001, var1, "substitute", 148, true, true, var10007, 12, var2, (String[])null, 1, 4097);
      var2 = new String[]{"mo", "named", "val"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mapping", "self"};
      convert$13 = Py.newCode(1, var10001, var1, "convert", 158, false, false, var10007, 13, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "args", "kws", "convert", "mapping"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "mapping"};
      safe_substitute$14 = Py.newCode(3, var10001, var1, "safe_substitute", 174, true, true, var10007, 14, var2, (String[])null, 1, 4097);
      var2 = new String[]{"mo", "named", "braced"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"mapping", "self"};
      convert$15 = Py.newCode(1, var10001, var1, "convert", 184, false, false, var10007, 15, (String[])null, var2, 0, 4097);
      var2 = new String[]{"s"};
      lower$16 = Py.newCode(1, var2, var1, "lower", 220, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      upper$17 = Py.newCode(1, var2, var1, "upper", 229, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      swapcase$18 = Py.newCode(1, var2, var1, "swapcase", 238, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "chars"};
      strip$19 = Py.newCode(2, var2, var1, "strip", 248, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "chars"};
      lstrip$20 = Py.newCode(2, var2, var1, "lstrip", 260, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "chars"};
      rstrip$21 = Py.newCode(2, var2, var1, "rstrip", 270, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "sep", "maxsplit"};
      split$22 = Py.newCode(3, var2, var1, "split", 281, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "sep", "maxsplit"};
      rsplit$23 = Py.newCode(3, var2, var1, "rsplit", 296, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"words", "sep"};
      join$24 = Py.newCode(2, var2, var1, "join", 308, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "args"};
      index$25 = Py.newCode(2, var2, var1, "index", 322, true, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "args"};
      rindex$26 = Py.newCode(2, var2, var1, "rindex", 331, true, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "args"};
      count$27 = Py.newCode(2, var2, var1, "count", 340, true, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "args"};
      find$28 = Py.newCode(2, var2, var1, "find", 351, true, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "args"};
      rfind$29 = Py.newCode(2, var2, var1, "rfind", 364, true, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      atof$30 = Py.newCode(1, var2, var1, "atof", 382, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "base"};
      atoi$31 = Py.newCode(2, var2, var1, "atoi", 392, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "base"};
      atol$32 = Py.newCode(2, var2, var1, "atol", 407, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "width", "args"};
      ljust$33 = Py.newCode(3, var2, var1, "ljust", 423, true, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "width", "args"};
      rjust$34 = Py.newCode(3, var2, var1, "rjust", 434, true, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "width", "args"};
      center$35 = Py.newCode(3, var2, var1, "center", 445, true, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "width"};
      zfill$36 = Py.newCode(2, var2, var1, "zfill", 458, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "tabsize"};
      expandtabs$37 = Py.newCode(2, var2, var1, "expandtabs", 471, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "table", "deletions"};
      translate$38 = Py.newCode(3, var2, var1, "translate", 482, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      capitalize$39 = Py.newCode(1, var2, var1, "capitalize", 501, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "old", "new", "maxreplace"};
      replace$40 = Py.newCode(4, var2, var1, "replace", 511, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Formatter$41 = Py.newCode(0, var2, var1, "Formatter", 543, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "format_string", "args", "kwargs"};
      format$42 = Py.newCode(4, var2, var1, "format", 544, true, true, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format_string", "args", "kwargs", "used_args", "result"};
      vformat$43 = Py.newCode(4, var2, var1, "vformat", 547, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format_string", "args", "kwargs", "used_args", "recursion_depth", "result", "literal_text", "field_name", "format_spec", "conversion", "obj", "arg_used"};
      _vformat$44 = Py.newCode(6, var2, var1, "_vformat", 553, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "args", "kwargs"};
      get_value$45 = Py.newCode(4, var2, var1, "get_value", 587, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "used_args", "args", "kwargs"};
      check_unused_args$46 = Py.newCode(4, var2, var1, "check_unused_args", 594, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "format_spec"};
      format_field$47 = Py.newCode(3, var2, var1, "format_field", 598, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "conversion"};
      convert_field$48 = Py.newCode(3, var2, var1, "convert_field", 602, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format_string"};
      parse$49 = Py.newCode(2, var2, var1, "parse", 620, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "field_name", "args", "kwargs", "first", "rest", "obj", "is_attr", "i"};
      get_field$50 = Py.newCode(4, var2, var1, "get_field", 629, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new string$py("string$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(string$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.capwords$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.maketrans$3(var2, var3);
         case 4:
            return this._multimap$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.__getitem__$6(var2, var3);
         case 7:
            return this._TemplateMetaclass$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.Template$9(var2, var3);
         case 10:
            return this.__init__$10(var2, var3);
         case 11:
            return this._invalid$11(var2, var3);
         case 12:
            return this.substitute$12(var2, var3);
         case 13:
            return this.convert$13(var2, var3);
         case 14:
            return this.safe_substitute$14(var2, var3);
         case 15:
            return this.convert$15(var2, var3);
         case 16:
            return this.lower$16(var2, var3);
         case 17:
            return this.upper$17(var2, var3);
         case 18:
            return this.swapcase$18(var2, var3);
         case 19:
            return this.strip$19(var2, var3);
         case 20:
            return this.lstrip$20(var2, var3);
         case 21:
            return this.rstrip$21(var2, var3);
         case 22:
            return this.split$22(var2, var3);
         case 23:
            return this.rsplit$23(var2, var3);
         case 24:
            return this.join$24(var2, var3);
         case 25:
            return this.index$25(var2, var3);
         case 26:
            return this.rindex$26(var2, var3);
         case 27:
            return this.count$27(var2, var3);
         case 28:
            return this.find$28(var2, var3);
         case 29:
            return this.rfind$29(var2, var3);
         case 30:
            return this.atof$30(var2, var3);
         case 31:
            return this.atoi$31(var2, var3);
         case 32:
            return this.atol$32(var2, var3);
         case 33:
            return this.ljust$33(var2, var3);
         case 34:
            return this.rjust$34(var2, var3);
         case 35:
            return this.center$35(var2, var3);
         case 36:
            return this.zfill$36(var2, var3);
         case 37:
            return this.expandtabs$37(var2, var3);
         case 38:
            return this.translate$38(var2, var3);
         case 39:
            return this.capitalize$39(var2, var3);
         case 40:
            return this.replace$40(var2, var3);
         case 41:
            return this.Formatter$41(var2, var3);
         case 42:
            return this.format$42(var2, var3);
         case 43:
            return this.vformat$43(var2, var3);
         case 44:
            return this._vformat$44(var2, var3);
         case 45:
            return this.get_value$45(var2, var3);
         case 46:
            return this.check_unused_args$46(var2, var3);
         case 47:
            return this.format_field$47(var2, var3);
         case 48:
            return this.convert_field$48(var2, var3);
         case 49:
            return this.parse$49(var2, var3);
         case 50:
            return this.get_field$50(var2, var3);
         default:
            return null;
      }
   }
}
