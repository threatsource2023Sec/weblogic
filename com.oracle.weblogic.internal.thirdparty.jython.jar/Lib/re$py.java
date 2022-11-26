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
@Filename("re.py")
public class re$py extends PyFunctionTable implements PyRunnable {
   static re$py self;
   static final PyCode f$0;
   static final PyCode match$1;
   static final PyCode search$2;
   static final PyCode sub$3;
   static final PyCode subn$4;
   static final PyCode split$5;
   static final PyCode findall$6;
   static final PyCode finditer$7;
   static final PyCode compile$8;
   static final PyCode purge$9;
   static final PyCode template$10;
   static final PyCode escape$11;
   static final PyCode _compile$12;
   static final PyCode _compile_repl$13;
   static final PyCode _expand$14;
   static final PyCode _subx$15;
   static final PyCode filter$16;
   static final PyCode _pickle$17;
   static final PyCode Scanner$18;
   static final PyCode __init__$19;
   static final PyCode scan$20;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Support for regular expressions (RE).\n\nThis module provides regular expression matching operations similar to\nthose found in Perl.  It supports both 8-bit and Unicode strings; both\nthe pattern and the strings being processed can contain null bytes and\ncharacters outside the US ASCII range.\n\nRegular expressions can contain both special and ordinary characters.\nMost ordinary characters, like \"A\", \"a\", or \"0\", are the simplest\nregular expressions; they simply match themselves.  You can\nconcatenate ordinary characters, so last matches the string 'last'.\n\nThe special characters are:\n    \".\"      Matches any character except a newline.\n    \"^\"      Matches the start of the string.\n    \"$\"      Matches the end of the string or just before the newline at\n             the end of the string.\n    \"*\"      Matches 0 or more (greedy) repetitions of the preceding RE.\n             Greedy means that it will match as many repetitions as possible.\n    \"+\"      Matches 1 or more (greedy) repetitions of the preceding RE.\n    \"?\"      Matches 0 or 1 (greedy) of the preceding RE.\n    *?,+?,?? Non-greedy versions of the previous three special characters.\n    {m,n}    Matches from m to n repetitions of the preceding RE.\n    {m,n}?   Non-greedy version of the above.\n    \"\\\\\"     Either escapes special characters or signals a special sequence.\n    []       Indicates a set of characters.\n             A \"^\" as the first character indicates a complementing set.\n    \"|\"      A|B, creates an RE that will match either A or B.\n    (...)    Matches the RE inside the parentheses.\n             The contents can be retrieved or matched later in the string.\n    (?iLmsux) Set the I, L, M, S, U, or X flag for the RE (see below).\n    (?:...)  Non-grouping version of regular parentheses.\n    (?P<name>...) The substring matched by the group is accessible by name.\n    (?P=name)     Matches the text matched earlier by the group named name.\n    (?#...)  A comment; ignored.\n    (?=...)  Matches if ... matches next, but doesn't consume the string.\n    (?!...)  Matches if ... doesn't match next.\n    (?<=...) Matches if preceded by ... (must be fixed length).\n    (?<!...) Matches if not preceded by ... (must be fixed length).\n    (?(id/name)yes|no) Matches yes pattern if the group with id/name matched,\n                       the (optional) no pattern otherwise.\n\nThe special sequences consist of \"\\\\\" and a character from the list\nbelow.  If the ordinary character is not on the list, then the\nresulting RE will match the second character.\n    \\number  Matches the contents of the group of the same number.\n    \\A       Matches only at the start of the string.\n    \\Z       Matches only at the end of the string.\n    \\b       Matches the empty string, but only at the start or end of a word.\n    \\B       Matches the empty string, but not at the start or end of a word.\n    \\d       Matches any decimal digit; equivalent to the set [0-9].\n    \\D       Matches any non-digit character; equivalent to the set [^0-9].\n    \\s       Matches any whitespace character; equivalent to [ \\t\\n\\r\\f\\v].\n    \\S       Matches any non-whitespace character; equiv. to [^ \\t\\n\\r\\f\\v].\n    \\w       Matches any alphanumeric character; equivalent to [a-zA-Z0-9_].\n             With LOCALE, it will match the set [0-9_] plus characters defined\n             as letters for the current locale.\n    \\W       Matches the complement of \\w.\n    \\\\       Matches a literal backslash.\n\nThis module exports the following functions:\n    match    Match a regular expression pattern to the beginning of a string.\n    search   Search a string for the presence of a pattern.\n    sub      Substitute occurrences of a pattern found in a string.\n    subn     Same as sub, but also return the number of substitutions made.\n    split    Split a string by the occurrences of a pattern.\n    findall  Find all occurrences of a pattern in a string.\n    finditer Return an iterator yielding a match object for each match.\n    compile  Compile a pattern into a RegexObject.\n    purge    Clear the regular expression cache.\n    escape   Backslash all non-alphanumerics in a string.\n\nSome of the functions in this module takes flags as optional parameters:\n    I  IGNORECASE  Perform case-insensitive matching.\n    L  LOCALE      Make \\w, \\W, \\b, \\B, dependent on the current locale.\n    M  MULTILINE   \"^\" matches the beginning of lines (after a newline)\n                   as well as the string.\n                   \"$\" matches the end of lines (before a newline) as well\n                   as the end of the string.\n    S  DOTALL      \".\" matches any character at all, including the newline.\n    X  VERBOSE     Ignore whitespace and comments for nicer looking RE's.\n    U  UNICODE     Make \\w, \\W, \\b, \\B, dependent on the Unicode locale.\n\nThis module also defines an exception 'error'.\n\n"));
      var1.setline(102);
      PyString.fromInterned("Support for regular expressions (RE).\n\nThis module provides regular expression matching operations similar to\nthose found in Perl.  It supports both 8-bit and Unicode strings; both\nthe pattern and the strings being processed can contain null bytes and\ncharacters outside the US ASCII range.\n\nRegular expressions can contain both special and ordinary characters.\nMost ordinary characters, like \"A\", \"a\", or \"0\", are the simplest\nregular expressions; they simply match themselves.  You can\nconcatenate ordinary characters, so last matches the string 'last'.\n\nThe special characters are:\n    \".\"      Matches any character except a newline.\n    \"^\"      Matches the start of the string.\n    \"$\"      Matches the end of the string or just before the newline at\n             the end of the string.\n    \"*\"      Matches 0 or more (greedy) repetitions of the preceding RE.\n             Greedy means that it will match as many repetitions as possible.\n    \"+\"      Matches 1 or more (greedy) repetitions of the preceding RE.\n    \"?\"      Matches 0 or 1 (greedy) of the preceding RE.\n    *?,+?,?? Non-greedy versions of the previous three special characters.\n    {m,n}    Matches from m to n repetitions of the preceding RE.\n    {m,n}?   Non-greedy version of the above.\n    \"\\\\\"     Either escapes special characters or signals a special sequence.\n    []       Indicates a set of characters.\n             A \"^\" as the first character indicates a complementing set.\n    \"|\"      A|B, creates an RE that will match either A or B.\n    (...)    Matches the RE inside the parentheses.\n             The contents can be retrieved or matched later in the string.\n    (?iLmsux) Set the I, L, M, S, U, or X flag for the RE (see below).\n    (?:...)  Non-grouping version of regular parentheses.\n    (?P<name>...) The substring matched by the group is accessible by name.\n    (?P=name)     Matches the text matched earlier by the group named name.\n    (?#...)  A comment; ignored.\n    (?=...)  Matches if ... matches next, but doesn't consume the string.\n    (?!...)  Matches if ... doesn't match next.\n    (?<=...) Matches if preceded by ... (must be fixed length).\n    (?<!...) Matches if not preceded by ... (must be fixed length).\n    (?(id/name)yes|no) Matches yes pattern if the group with id/name matched,\n                       the (optional) no pattern otherwise.\n\nThe special sequences consist of \"\\\\\" and a character from the list\nbelow.  If the ordinary character is not on the list, then the\nresulting RE will match the second character.\n    \\number  Matches the contents of the group of the same number.\n    \\A       Matches only at the start of the string.\n    \\Z       Matches only at the end of the string.\n    \\b       Matches the empty string, but only at the start or end of a word.\n    \\B       Matches the empty string, but not at the start or end of a word.\n    \\d       Matches any decimal digit; equivalent to the set [0-9].\n    \\D       Matches any non-digit character; equivalent to the set [^0-9].\n    \\s       Matches any whitespace character; equivalent to [ \\t\\n\\r\\f\\v].\n    \\S       Matches any non-whitespace character; equiv. to [^ \\t\\n\\r\\f\\v].\n    \\w       Matches any alphanumeric character; equivalent to [a-zA-Z0-9_].\n             With LOCALE, it will match the set [0-9_] plus characters defined\n             as letters for the current locale.\n    \\W       Matches the complement of \\w.\n    \\\\       Matches a literal backslash.\n\nThis module exports the following functions:\n    match    Match a regular expression pattern to the beginning of a string.\n    search   Search a string for the presence of a pattern.\n    sub      Substitute occurrences of a pattern found in a string.\n    subn     Same as sub, but also return the number of substitutions made.\n    split    Split a string by the occurrences of a pattern.\n    findall  Find all occurrences of a pattern in a string.\n    finditer Return an iterator yielding a match object for each match.\n    compile  Compile a pattern into a RegexObject.\n    purge    Clear the regular expression cache.\n    escape   Backslash all non-alphanumerics in a string.\n\nSome of the functions in this module takes flags as optional parameters:\n    I  IGNORECASE  Perform case-insensitive matching.\n    L  LOCALE      Make \\w, \\W, \\b, \\B, dependent on the current locale.\n    M  MULTILINE   \"^\" matches the beginning of lines (after a newline)\n                   as well as the string.\n                   \"$\" matches the end of lines (before a newline) as well\n                   as the end of the string.\n    S  DOTALL      \".\" matches any character at all, including the newline.\n    X  VERBOSE     Ignore whitespace and comments for nicer looking RE's.\n    U  UNICODE     Make \\w, \\W, \\b, \\B, dependent on the Unicode locale.\n\nThis module also defines an exception 'error'.\n\n");
      var1.setline(104);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(105);
      var3 = imp.importOne("sre_compile", var1, -1);
      var1.setlocal("sre_compile", var3);
      var3 = null;
      var1.setline(106);
      var3 = imp.importOne("sre_parse", var1, -1);
      var1.setlocal("sre_parse", var3);
      var3 = null;
      var1.setline(109);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("match"), PyString.fromInterned("search"), PyString.fromInterned("sub"), PyString.fromInterned("subn"), PyString.fromInterned("split"), PyString.fromInterned("findall"), PyString.fromInterned("compile"), PyString.fromInterned("purge"), PyString.fromInterned("template"), PyString.fromInterned("escape"), PyString.fromInterned("I"), PyString.fromInterned("L"), PyString.fromInterned("M"), PyString.fromInterned("S"), PyString.fromInterned("X"), PyString.fromInterned("U"), PyString.fromInterned("IGNORECASE"), PyString.fromInterned("LOCALE"), PyString.fromInterned("MULTILINE"), PyString.fromInterned("DOTALL"), PyString.fromInterned("VERBOSE"), PyString.fromInterned("UNICODE"), PyString.fromInterned("error")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(114);
      PyString var6 = PyString.fromInterned("2.2.1");
      var1.setlocal("__version__", var6);
      var3 = null;
      var1.setline(117);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_IGNORECASE");
      var1.setlocal("I", var3);
      var1.setlocal("IGNORECASE", var3);
      var1.setline(118);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_LOCALE");
      var1.setlocal("L", var3);
      var1.setlocal("LOCALE", var3);
      var1.setline(119);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_UNICODE");
      var1.setlocal("U", var3);
      var1.setlocal("UNICODE", var3);
      var1.setline(120);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_MULTILINE");
      var1.setlocal("M", var3);
      var1.setlocal("MULTILINE", var3);
      var1.setline(121);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_DOTALL");
      var1.setlocal("S", var3);
      var1.setlocal("DOTALL", var3);
      var1.setline(122);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_VERBOSE");
      var1.setlocal("X", var3);
      var1.setlocal("VERBOSE", var3);
      var1.setline(125);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_TEMPLATE");
      var1.setlocal("T", var3);
      var1.setlocal("TEMPLATE", var3);
      var1.setline(126);
      var3 = var1.getname("sre_compile").__getattr__("SRE_FLAG_DEBUG");
      var1.setlocal("DEBUG", var3);
      var3 = null;
      var1.setline(129);
      var3 = var1.getname("sre_compile").__getattr__("error");
      var1.setlocal("error", var3);
      var3 = null;
      var1.setline(134);
      PyObject[] var7 = new PyObject[]{Py.newInteger(0)};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, match$1, PyString.fromInterned("Try to apply the pattern at the start of the string, returning\n    a match object, or None if no match was found."));
      var1.setlocal("match", var8);
      var3 = null;
      var1.setline(139);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, search$2, PyString.fromInterned("Scan through string looking for a match to the pattern, returning\n    a match object, or None if no match was found."));
      var1.setlocal("search", var8);
      var3 = null;
      var1.setline(144);
      var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, sub$3, PyString.fromInterned("Return the string obtained by replacing the leftmost\n    non-overlapping occurrences of the pattern in string by the\n    replacement repl.  repl can be either a string or a callable;\n    if a string, backslash escapes in it are processed.  If it is\n    a callable, it's passed the match object and must return\n    a replacement string to be used."));
      var1.setlocal("sub", var8);
      var3 = null;
      var1.setline(153);
      var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, subn$4, PyString.fromInterned("Return a 2-tuple containing (new_string, number).\n    new_string is the string obtained by replacing the leftmost\n    non-overlapping occurrences of the pattern in the source\n    string by the replacement repl.  number is the number of\n    substitutions that were made. repl can be either a string or a\n    callable; if a string, backslash escapes in it are processed.\n    If it is a callable, it's passed the match object and must\n    return a replacement string to be used."));
      var1.setlocal("subn", var8);
      var3 = null;
      var1.setline(164);
      var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, split$5, PyString.fromInterned("Split the source string by the occurrences of the pattern,\n    returning a list containing the resulting substrings."));
      var1.setlocal("split", var8);
      var3 = null;
      var1.setline(169);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, findall$6, PyString.fromInterned("Return a list of all non-overlapping matches in the string.\n\n    If one or more groups are present in the pattern, return a\n    list of groups; this will be a list of tuples if the pattern\n    has more than one group.\n\n    Empty matches are included in the result."));
      var1.setlocal("findall", var8);
      var3 = null;
      var1.setline(179);
      var3 = var1.getname("sys").__getattr__("hexversion");
      PyObject var10000 = var3._ge(Py.newInteger(33685504));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(180);
         var1.getname("__all__").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("finditer"));
         var1.setline(181);
         var7 = new PyObject[]{Py.newInteger(0)};
         var8 = new PyFunction(var1.f_globals, var7, finditer$7, PyString.fromInterned("Return an iterator over all non-overlapping matches in the\n        string.  For each match, the iterator returns a match object.\n\n        Empty matches are included in the result."));
         var1.setlocal("finditer", var8);
         var3 = null;
      }

      var1.setline(188);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, compile$8, PyString.fromInterned("Compile a regular expression pattern, returning a pattern object."));
      var1.setlocal("compile", var8);
      var3 = null;
      var1.setline(192);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, purge$9, PyString.fromInterned("Clear the regular expression cache"));
      var1.setlocal("purge", var8);
      var3 = null;
      var1.setline(197);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, template$10, PyString.fromInterned("Compile a template pattern, returning a pattern object"));
      var1.setlocal("template", var8);
      var3 = null;
      var1.setline(201);
      var3 = var1.getname("frozenset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
      var1.setlocal("_alphanum", var3);
      var3 = null;
      var1.setline(204);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, escape$11, PyString.fromInterned("Escape all non-alphanumeric characters in pattern."));
      var1.setlocal("escape", var8);
      var3 = null;
      var1.setline(219);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_cache", var9);
      var3 = null;
      var1.setline(220);
      var9 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_cache_repl", var9);
      var3 = null;
      var1.setline(222);
      var3 = var1.getname("type").__call__(var2, var1.getname("sre_compile").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)Py.newInteger(0)));
      var1.setlocal("_pattern_type", var3);
      var3 = null;
      var1.setline(224);
      PyInteger var10 = Py.newInteger(100);
      var1.setlocal("_MAXCACHE", var10);
      var3 = null;
      var1.setline(226);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _compile$12, (PyObject)null);
      var1.setlocal("_compile", var8);
      var3 = null;
      var1.setline(248);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _compile_repl$13, (PyObject)null);
      var1.setlocal("_compile_repl", var8);
      var3 = null;
      var1.setline(263);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _expand$14, (PyObject)null);
      var1.setlocal("_expand", var8);
      var3 = null;
      var1.setline(268);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _subx$15, (PyObject)null);
      var1.setlocal("_subx", var8);
      var3 = null;
      var1.setline(280);
      var3 = imp.importOne("copy_reg", var1, -1);
      var1.setlocal("copy_reg", var3);
      var3 = null;
      var1.setline(282);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _pickle$17, (PyObject)null);
      var1.setlocal("_pickle", var8);
      var3 = null;
      var1.setline(285);
      var1.getname("copy_reg").__getattr__("pickle").__call__(var2, var1.getname("_pattern_type"), var1.getname("_pickle"), var1.getname("_compile"));
      var1.setline(290);
      var7 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Scanner", var7, Scanner$18);
      var1.setlocal("Scanner", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject match$1(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyString.fromInterned("Try to apply the pattern at the start of the string, returning\n    a match object, or None if no match was found.");
      var1.setline(137);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(2)).__getattr__("match").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject search$2(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Scan through string looking for a match to the pattern, returning\n    a match object, or None if no match was found.");
      var1.setline(142);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(2)).__getattr__("search").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sub$3(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("Return the string obtained by replacing the leftmost\n    non-overlapping occurrences of the pattern in string by the\n    replacement repl.  repl can be either a string or a callable;\n    if a string, backslash escapes in it are processed.  If it is\n    a callable, it's passed the match object and must return\n    a replacement string to be used.");
      var1.setline(151);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(4)).__getattr__("sub").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject subn$4(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("Return a 2-tuple containing (new_string, number).\n    new_string is the string obtained by replacing the leftmost\n    non-overlapping occurrences of the pattern in the source\n    string by the replacement repl.  number is the number of\n    substitutions that were made. repl can be either a string or a\n    callable; if a string, backslash escapes in it are processed.\n    If it is a callable, it's passed the match object and must\n    return a replacement string to be used.");
      var1.setline(162);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(4)).__getattr__("subn").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject split$5(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyString.fromInterned("Split the source string by the occurrences of the pattern,\n    returning a list containing the resulting substrings.");
      var1.setline(167);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(3)).__getattr__("split").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject findall$6(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyString.fromInterned("Return a list of all non-overlapping matches in the string.\n\n    If one or more groups are present in the pattern, return a\n    list of groups; this will be a list of tuples if the pattern\n    has more than one group.\n\n    Empty matches are included in the result.");
      var1.setline(177);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(2)).__getattr__("findall").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject finditer$7(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyString.fromInterned("Return an iterator over all non-overlapping matches in the\n        string.  For each match, the iterator returns a match object.\n\n        Empty matches are included in the result.");
      var1.setline(186);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(2)).__getattr__("finditer").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compile$8(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyString.fromInterned("Compile a regular expression pattern, returning a pattern object.");
      var1.setline(190);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject purge$9(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyString.fromInterned("Clear the regular expression cache");
      var1.setline(194);
      var1.getglobal("_cache").__getattr__("clear").__call__(var2);
      var1.setline(195);
      var1.getglobal("_cache_repl").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject template$10(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyString.fromInterned("Compile a template pattern, returning a pattern object");
      var1.setline(199);
      PyObject var3 = var1.getglobal("_compile").__call__(var2, var1.getlocal(0), var1.getlocal(1)._or(var1.getglobal("T")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject escape$11(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyString.fromInterned("Escape all non-alphanumeric characters in pattern.");
      var1.setline(206);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getglobal("_alphanum");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(208);
      var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(208);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(214);
            var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(0), (PyObject)null).__getattr__("join").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(209);
         PyObject var7 = var1.getlocal(4);
         PyObject var10000 = var7._notin(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(210);
            var7 = var1.getlocal(4);
            var10000 = var7._eq(PyString.fromInterned("\u0000"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(211);
               PyString var8 = PyString.fromInterned("\\000");
               var1.getlocal(1).__setitem__((PyObject)var1.getlocal(3), var8);
               var5 = null;
            } else {
               var1.setline(213);
               var7 = PyString.fromInterned("\\")._add(var1.getlocal(4));
               var1.getlocal(1).__setitem__(var1.getlocal(3), var7);
               var5 = null;
            }
         }
      }
   }

   public PyObject _compile$12(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyObject var3 = (new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)))}))._add(var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(229);
      var3 = var1.getglobal("_cache").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(230);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(231);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(232);
         PyObject var4 = var1.getlocal(0);
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(233);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("_pattern_type")).__nonzero__()) {
            var1.setline(234);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(235);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot process flags argument with a compiled pattern")));
            } else {
               var1.setline(236);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            var1.setline(237);
            if (var1.getglobal("sre_compile").__getattr__("isstring").__call__(var2, var1.getlocal(3)).__not__().__nonzero__()) {
               var1.setline(238);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("first argument must be string or compiled pattern"));
            } else {
               try {
                  var1.setline(240);
                  var4 = var1.getglobal("sre_compile").__getattr__("compile").__call__(var2, var1.getlocal(3), var1.getlocal(4));
                  var1.setlocal(2, var4);
                  var4 = null;
               } catch (Throwable var7) {
                  PyException var8 = Py.setException(var7, var1);
                  if (var8.match(var1.getglobal("error"))) {
                     PyObject var9 = var8.value;
                     var1.setlocal(5, var9);
                     var5 = null;
                     var1.setline(242);
                     throw Py.makeException(var1.getglobal("error"), var1.getlocal(5));
                  }

                  throw var8;
               }

               var1.setline(243);
               var4 = var1.getglobal("len").__call__(var2, var1.getglobal("_cache"));
               var10000 = var4._ge(var1.getglobal("_MAXCACHE"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(244);
                  var1.getglobal("_cache").__getattr__("clear").__call__(var2);
               }

               var1.setline(245);
               var4 = var1.getlocal(2);
               var1.getglobal("_cache").__setitem__(var1.getlocal(1), var4);
               var4 = null;
               var1.setline(246);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _compile_repl$13(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyObject var3 = var1.getglobal("_cache_repl").__getattr__("get").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(251);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(252);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(253);
         PyObject var4 = var1.getlocal(0);
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;

         try {
            var1.setline(255);
            var4 = var1.getglobal("sre_parse").__getattr__("parse_template").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(1, var4);
            var4 = null;
         } catch (Throwable var7) {
            PyException var8 = Py.setException(var7, var1);
            if (var8.match(var1.getglobal("error"))) {
               PyObject var9 = var8.value;
               var1.setlocal(4, var9);
               var5 = null;
               var1.setline(257);
               throw Py.makeException(var1.getglobal("error"), var1.getlocal(4));
            }

            throw var8;
         }

         var1.setline(258);
         var4 = var1.getglobal("len").__call__(var2, var1.getglobal("_cache_repl"));
         var10000 = var4._ge(var1.getglobal("_MAXCACHE"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(259);
            var1.getglobal("_cache_repl").__getattr__("clear").__call__(var2);
         }

         var1.setline(260);
         var4 = var1.getlocal(1);
         var1.getglobal("_cache_repl").__setitem__(var1.getlocal(0), var4);
         var4 = null;
         var1.setline(261);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _expand$14(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var3 = var1.getglobal("sre_parse").__getattr__("parse_template").__call__(var2, var1.getlocal(2), var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(266);
      var3 = var1.getglobal("sre_parse").__getattr__("expand_template").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _subx$15(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyObject var3 = var1.getglobal("_compile_repl").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(271);
      PyObject var10000 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1)));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(273);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(274);
         PyObject[] var4 = new PyObject[]{var1.getlocal(1)};
         PyFunction var5 = new PyFunction(var1.f_globals, var4, filter$16, (PyObject)null);
         var1.setlocal(2, var5);
         var4 = null;
         var1.setline(276);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject filter$16(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyObject var3 = var1.getglobal("sre_parse").__getattr__("expand_template").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _pickle$17(PyFrame var1, ThreadState var2) {
      var1.setline(283);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("_compile"), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("pattern"), var1.getlocal(0).__getattr__("flags")})});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Scanner$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(291);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(305);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, scan$20, (PyObject)null);
      var1.setlocal("scan", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      String[] var3 = new String[]{"BRANCH", "SUBPATTERN"};
      PyObject[] var7 = imp.importFrom("sre_constants", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(3, var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(293);
      PyObject var8 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("lexicon", var8);
      var3 = null;
      var1.setline(295);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(296);
      var8 = var1.getglobal("sre_parse").__getattr__("Pattern").__call__(var2);
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(297);
      var8 = var1.getlocal(2);
      var1.getlocal(6).__setattr__("flags", var8);
      var3 = null;
      var1.setline(298);
      var8 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(298);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(302);
            var8 = var1.getglobal("len").__call__(var2, var1.getlocal(5))._add(Py.newInteger(1));
            var1.getlocal(6).__setattr__("groups", var8);
            var3 = null;
            var1.setline(303);
            var8 = var1.getglobal("sre_parse").__getattr__("SubPattern").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(3), new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(5)})})})));
            var1.setlocal(5, var8);
            var3 = null;
            var1.setline(304);
            var8 = var1.getglobal("sre_compile").__getattr__("compile").__call__(var2, var1.getlocal(5));
            var1.getlocal(0).__setattr__("scanner", var8);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(299);
         var1.getlocal(5).__getattr__("append").__call__(var2, var1.getglobal("sre_parse").__getattr__("SubPattern").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(5))._add(Py.newInteger(1)), var1.getglobal("sre_parse").__getattr__("parse").__call__(var2, var1.getlocal(7), var1.getlocal(2))})})}))));
      }
   }

   public PyObject scan$20(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(307);
      PyObject var4 = var1.getlocal(2).__getattr__("append");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(308);
      var4 = var1.getlocal(0).__getattr__("scanner").__getattr__("scanner").__call__(var2, var1.getlocal(1)).__getattr__("match");
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(309);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(5, var5);
      var3 = null;

      while(true) {
         var1.setline(310);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(311);
         var4 = var1.getlocal(4).__call__(var2);
         var1.setlocal(6, var4);
         var3 = null;
         var1.setline(312);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            break;
         }

         var1.setline(314);
         var4 = var1.getlocal(6).__getattr__("end").__call__(var2);
         var1.setlocal(7, var4);
         var3 = null;
         var1.setline(315);
         var4 = var1.getlocal(5);
         PyObject var10000 = var4._eq(var1.getlocal(7));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(317);
         var4 = var1.getlocal(0).__getattr__("lexicon").__getitem__(var1.getlocal(6).__getattr__("lastindex")._sub(Py.newInteger(1))).__getitem__(Py.newInteger(1));
         var1.setlocal(8, var4);
         var3 = null;
         var1.setline(318);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
            var1.setline(319);
            var4 = var1.getlocal(6);
            var1.getlocal(0).__setattr__("match", var4);
            var3 = null;
            var1.setline(320);
            var4 = var1.getlocal(8).__call__(var2, var1.getlocal(0), var1.getlocal(6).__getattr__("group").__call__(var2));
            var1.setlocal(8, var4);
            var3 = null;
         }

         var1.setline(321);
         var4 = var1.getlocal(8);
         var10000 = var4._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(322);
            var1.getlocal(3).__call__(var2, var1.getlocal(8));
         }

         var1.setline(323);
         var4 = var1.getlocal(7);
         var1.setlocal(5, var4);
         var3 = null;
      }

      var1.setline(324);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null)});
      var1.f_lasti = -1;
      return var6;
   }

   public re$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pattern", "string", "flags"};
      match$1 = Py.newCode(3, var2, var1, "match", 134, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "string", "flags"};
      search$2 = Py.newCode(3, var2, var1, "search", 139, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "repl", "string", "count", "flags"};
      sub$3 = Py.newCode(5, var2, var1, "sub", 144, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "repl", "string", "count", "flags"};
      subn$4 = Py.newCode(5, var2, var1, "subn", 153, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "string", "maxsplit", "flags"};
      split$5 = Py.newCode(4, var2, var1, "split", 164, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "string", "flags"};
      findall$6 = Py.newCode(3, var2, var1, "findall", 169, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "string", "flags"};
      finditer$7 = Py.newCode(3, var2, var1, "finditer", 181, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "flags"};
      compile$8 = Py.newCode(2, var2, var1, "compile", 188, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      purge$9 = Py.newCode(0, var2, var1, "purge", 192, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "flags"};
      template$10 = Py.newCode(2, var2, var1, "template", 197, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "s", "alphanum", "i", "c"};
      escape$11 = Py.newCode(1, var2, var1, "escape", 204, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key", "cachekey", "p", "pattern", "flags", "v"};
      _compile$12 = Py.newCode(1, var2, var1, "_compile", 226, true, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key", "p", "repl", "pattern", "v"};
      _compile_repl$13 = Py.newCode(1, var2, var1, "_compile_repl", 248, true, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "match", "template"};
      _expand$14 = Py.newCode(3, var2, var1, "_expand", 263, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pattern", "template", "filter"};
      _subx$15 = Py.newCode(2, var2, var1, "_subx", 268, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"match", "template"};
      filter$16 = Py.newCode(2, var2, var1, "filter", 274, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p"};
      _pickle$17 = Py.newCode(1, var2, var1, "_pickle", 282, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Scanner$18 = Py.newCode(0, var2, var1, "Scanner", 290, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "lexicon", "flags", "BRANCH", "SUBPATTERN", "p", "s", "phrase", "action"};
      __init__$19 = Py.newCode(3, var2, var1, "__init__", 291, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "result", "append", "match", "i", "m", "j", "action"};
      scan$20 = Py.newCode(2, var2, var1, "scan", 305, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new re$py("re$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(re$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.match$1(var2, var3);
         case 2:
            return this.search$2(var2, var3);
         case 3:
            return this.sub$3(var2, var3);
         case 4:
            return this.subn$4(var2, var3);
         case 5:
            return this.split$5(var2, var3);
         case 6:
            return this.findall$6(var2, var3);
         case 7:
            return this.finditer$7(var2, var3);
         case 8:
            return this.compile$8(var2, var3);
         case 9:
            return this.purge$9(var2, var3);
         case 10:
            return this.template$10(var2, var3);
         case 11:
            return this.escape$11(var2, var3);
         case 12:
            return this._compile$12(var2, var3);
         case 13:
            return this._compile_repl$13(var2, var3);
         case 14:
            return this._expand$14(var2, var3);
         case 15:
            return this._subx$15(var2, var3);
         case 16:
            return this.filter$16(var2, var3);
         case 17:
            return this._pickle$17(var2, var3);
         case 18:
            return this.Scanner$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.scan$20(var2, var3);
         default:
            return null;
      }
   }
}
