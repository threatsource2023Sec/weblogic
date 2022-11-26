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
import org.python.core.PyFloat;
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
@Filename("difflib.py")
public class difflib$py extends PyFunctionTable implements PyRunnable {
   static difflib$py self;
   static final PyCode f$0;
   static final PyCode _calculate_ratio$1;
   static final PyCode SequenceMatcher$2;
   static final PyCode __init__$3;
   static final PyCode set_seqs$4;
   static final PyCode set_seq1$5;
   static final PyCode set_seq2$6;
   static final PyCode _SequenceMatcher__chain_b$7;
   static final PyCode find_longest_match$8;
   static final PyCode get_matching_blocks$9;
   static final PyCode get_opcodes$10;
   static final PyCode get_grouped_opcodes$11;
   static final PyCode ratio$12;
   static final PyCode f$13;
   static final PyCode quick_ratio$14;
   static final PyCode real_quick_ratio$15;
   static final PyCode get_close_matches$16;
   static final PyCode _count_leading$17;
   static final PyCode Differ$18;
   static final PyCode __init__$19;
   static final PyCode compare$20;
   static final PyCode _dump$21;
   static final PyCode _plain_replace$22;
   static final PyCode _fancy_replace$23;
   static final PyCode _fancy_helper$24;
   static final PyCode _qformat$25;
   static final PyCode IS_LINE_JUNK$26;
   static final PyCode IS_CHARACTER_JUNK$27;
   static final PyCode _format_range_unified$28;
   static final PyCode unified_diff$29;
   static final PyCode _format_range_context$30;
   static final PyCode context_diff$31;
   static final PyCode f$32;
   static final PyCode f$33;
   static final PyCode ndiff$34;
   static final PyCode _mdiff$35;
   static final PyCode _make_line$36;
   static final PyCode record_sub_info$37;
   static final PyCode _line_iterator$38;
   static final PyCode _line_pair_iterator$39;
   static final PyCode HtmlDiff$40;
   static final PyCode __init__$41;
   static final PyCode make_file$42;
   static final PyCode _tab_newline_replace$43;
   static final PyCode expand_tabs$44;
   static final PyCode _split_line$45;
   static final PyCode _line_wrapper$46;
   static final PyCode _collect_lines$47;
   static final PyCode _format_line$48;
   static final PyCode _make_prefix$49;
   static final PyCode _convert_flags$50;
   static final PyCode make_table$51;
   static final PyCode restore$52;
   static final PyCode _test$53;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nModule difflib -- helpers for computing deltas between objects.\n\nFunction get_close_matches(word, possibilities, n=3, cutoff=0.6):\n    Use SequenceMatcher to return list of the best \"good enough\" matches.\n\nFunction context_diff(a, b):\n    For two lists of strings, return a delta in context diff format.\n\nFunction ndiff(a, b):\n    Return a delta: the difference between `a` and `b` (lists of strings).\n\nFunction restore(delta, which):\n    Return one of the two sequences that generated an ndiff delta.\n\nFunction unified_diff(a, b):\n    For two lists of strings, return a delta in unified diff format.\n\nClass SequenceMatcher:\n    A flexible class for comparing pairs of sequences of any type.\n\nClass Differ:\n    For producing human-readable deltas from sequences of lines of text.\n\nClass HtmlDiff:\n    For producing HTML side by side comparison with change highlights.\n"));
      var1.setline(29);
      PyString.fromInterned("\nModule difflib -- helpers for computing deltas between objects.\n\nFunction get_close_matches(word, possibilities, n=3, cutoff=0.6):\n    Use SequenceMatcher to return list of the best \"good enough\" matches.\n\nFunction context_diff(a, b):\n    For two lists of strings, return a delta in context diff format.\n\nFunction ndiff(a, b):\n    Return a delta: the difference between `a` and `b` (lists of strings).\n\nFunction restore(delta, which):\n    Return one of the two sequences that generated an ndiff delta.\n\nFunction unified_diff(a, b):\n    For two lists of strings, return a delta in unified diff format.\n\nClass SequenceMatcher:\n    A flexible class for comparing pairs of sequences of any type.\n\nClass Differ:\n    For producing human-readable deltas from sequences of lines of text.\n\nClass HtmlDiff:\n    For producing HTML side by side comparison with change highlights.\n");
      var1.setline(31);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("get_close_matches"), PyString.fromInterned("ndiff"), PyString.fromInterned("restore"), PyString.fromInterned("SequenceMatcher"), PyString.fromInterned("Differ"), PyString.fromInterned("IS_CHARACTER_JUNK"), PyString.fromInterned("IS_LINE_JUNK"), PyString.fromInterned("context_diff"), PyString.fromInterned("unified_diff"), PyString.fromInterned("HtmlDiff"), PyString.fromInterned("Match")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(35);
      PyObject var5 = imp.importOne("heapq", var1, -1);
      var1.setlocal("heapq", var5);
      var3 = null;
      var1.setline(36);
      String[] var6 = new String[]{"namedtuple"};
      PyObject[] var7 = imp.importFrom("collections", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("_namedtuple", var4);
      var4 = null;
      var1.setline(37);
      var6 = new String[]{"reduce"};
      var7 = imp.importFrom("functools", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("reduce", var4);
      var4 = null;
      var1.setline(39);
      var5 = var1.getname("_namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Match"), (PyObject)PyString.fromInterned("a b size"));
      var1.setlocal("Match", var5);
      var3 = null;
      var1.setline(41);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, _calculate_ratio$1, (PyObject)null);
      var1.setlocal("_calculate_ratio", var8);
      var3 = null;
      var1.setline(46);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("SequenceMatcher", var7, SequenceMatcher$2);
      var1.setlocal("SequenceMatcher", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(703);
      var7 = new PyObject[]{Py.newInteger(3), Py.newFloat(0.6)};
      var8 = new PyFunction(var1.f_globals, var7, get_close_matches$16, PyString.fromInterned("Use SequenceMatcher to return list of the best \"good enough\" matches.\n\n    word is a sequence for which close matches are desired (typically a\n    string).\n\n    possibilities is a list of sequences against which to match word\n    (typically a list of strings).\n\n    Optional arg n (default 3) is the maximum number of close matches to\n    return.  n must be > 0.\n\n    Optional arg cutoff (default 0.6) is a float in [0, 1].  Possibilities\n    that don't score at least that similar to word are ignored.\n\n    The best (no more than n) matches among the possibilities are returned\n    in a list, sorted by similarity score, most similar first.\n\n    >>> get_close_matches(\"appel\", [\"ape\", \"apple\", \"peach\", \"puppy\"])\n    ['apple', 'ape']\n    >>> import keyword as _keyword\n    >>> get_close_matches(\"wheel\", _keyword.kwlist)\n    ['while']\n    >>> get_close_matches(\"apple\", _keyword.kwlist)\n    []\n    >>> get_close_matches(\"accept\", _keyword.kwlist)\n    ['except']\n    "));
      var1.setlocal("get_close_matches", var8);
      var3 = null;
      var1.setline(751);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _count_leading$17, PyString.fromInterned("\n    Return number of `ch` characters at the start of `line`.\n\n    Example:\n\n    >>> _count_leading('   abc', ' ')\n    3\n    "));
      var1.setlocal("_count_leading", var8);
      var3 = null;
      var1.setline(766);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Differ", var7, Differ$18);
      var1.setlocal("Differ", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(1106);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(1108);
      var7 = new PyObject[]{var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s*#?\\s*$")).__getattr__("match")};
      var8 = new PyFunction(var1.f_globals, var7, IS_LINE_JUNK$26, PyString.fromInterned("\n    Return 1 for ignorable line: iff `line` is blank or contains a single '#'.\n\n    Examples:\n\n    >>> IS_LINE_JUNK('\\n')\n    True\n    >>> IS_LINE_JUNK('  #   \\n')\n    True\n    >>> IS_LINE_JUNK('hello\\n')\n    False\n    "));
      var1.setlocal("IS_LINE_JUNK", var8);
      var3 = null;
      var1.setline(1124);
      var7 = new PyObject[]{PyString.fromInterned(" \t")};
      var8 = new PyFunction(var1.f_globals, var7, IS_CHARACTER_JUNK$27, PyString.fromInterned("\n    Return 1 for ignorable character: iff `ch` is a space or tab.\n\n    Examples:\n\n    >>> IS_CHARACTER_JUNK(' ')\n    True\n    >>> IS_CHARACTER_JUNK('\\t')\n    True\n    >>> IS_CHARACTER_JUNK('\\n')\n    False\n    >>> IS_CHARACTER_JUNK('x')\n    False\n    "));
      var1.setlocal("IS_CHARACTER_JUNK", var8);
      var3 = null;
      var1.setline(1147);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _format_range_unified$28, PyString.fromInterned("Convert range to the \"ed\" format"));
      var1.setlocal("_format_range_unified", var8);
      var3 = null;
      var1.setline(1158);
      var7 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), Py.newInteger(3), PyString.fromInterned("\n")};
      var8 = new PyFunction(var1.f_globals, var7, unified_diff$29, PyString.fromInterned("\n    Compare two sequences of lines; generate the delta as a unified diff.\n\n    Unified diffs are a compact way of showing line changes and a few\n    lines of context.  The number of context lines is set by 'n' which\n    defaults to three.\n\n    By default, the diff control lines (those with ---, +++, or @@) are\n    created with a trailing newline.  This is helpful so that inputs\n    created from file.readlines() result in diffs that are suitable for\n    file.writelines() since both the inputs and outputs have trailing\n    newlines.\n\n    For inputs that do not have trailing newlines, set the lineterm\n    argument to \"\" so that the output will be uniformly newline free.\n\n    The unidiff format normally has a header for filenames and modification\n    times.  Any or all of these may be specified using strings for\n    'fromfile', 'tofile', 'fromfiledate', and 'tofiledate'.\n    The modification times are normally expressed in the ISO 8601 format.\n\n    Example:\n\n    >>> for line in unified_diff('one two three four'.split(),\n    ...             'zero one tree four'.split(), 'Original', 'Current',\n    ...             '2005-01-26 23:30:50', '2010-04-02 10:20:52',\n    ...             lineterm=''):\n    ...     print line                  # doctest: +NORMALIZE_WHITESPACE\n    --- Original        2005-01-26 23:30:50\n    +++ Current         2010-04-02 10:20:52\n    @@ -1,4 +1,4 @@\n    +zero\n     one\n    -two\n    -three\n    +tree\n     four\n    "));
      var1.setlocal("unified_diff", var8);
      var3 = null;
      var1.setline(1230);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _format_range_context$30, PyString.fromInterned("Convert range to the \"ed\" format"));
      var1.setlocal("_format_range_context", var8);
      var3 = null;
      var1.setline(1242);
      var7 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), Py.newInteger(3), PyString.fromInterned("\n")};
      var8 = new PyFunction(var1.f_globals, var7, context_diff$31, PyString.fromInterned("\n    Compare two sequences of lines; generate the delta as a context diff.\n\n    Context diffs are a compact way of showing line changes and a few\n    lines of context.  The number of context lines is set by 'n' which\n    defaults to three.\n\n    By default, the diff control lines (those with *** or ---) are\n    created with a trailing newline.  This is helpful so that inputs\n    created from file.readlines() result in diffs that are suitable for\n    file.writelines() since both the inputs and outputs have trailing\n    newlines.\n\n    For inputs that do not have trailing newlines, set the lineterm\n    argument to \"\" so that the output will be uniformly newline free.\n\n    The context diff format normally has a header for filenames and\n    modification times.  Any or all of these may be specified using\n    strings for 'fromfile', 'tofile', 'fromfiledate', and 'tofiledate'.\n    The modification times are normally expressed in the ISO 8601 format.\n    If not specified, the strings default to blanks.\n\n    Example:\n\n    >>> print ''.join(context_diff('one\\ntwo\\nthree\\nfour\\n'.splitlines(1),\n    ...       'zero\\none\\ntree\\nfour\\n'.splitlines(1), 'Original', 'Current')),\n    *** Original\n    --- Current\n    ***************\n    *** 1,4 ****\n      one\n    ! two\n    ! three\n      four\n    --- 1,4 ----\n    + zero\n      one\n    ! tree\n      four\n    "));
      var1.setlocal("context_diff", var8);
      var3 = null;
      var1.setline(1316);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("IS_CHARACTER_JUNK")};
      var8 = new PyFunction(var1.f_globals, var7, ndiff$34, PyString.fromInterned("\n    Compare `a` and `b` (lists of strings); return a `Differ`-style delta.\n\n    Optional keyword parameters `linejunk` and `charjunk` are for filter\n    functions (or None):\n\n    - linejunk: A function that should accept a single string argument, and\n      return true iff the string is junk.  The default is None, and is\n      recommended; as of Python 2.3, an adaptive notion of \"noise\" lines is\n      used that does a good job on its own.\n\n    - charjunk: A function that should accept a string of length 1. The\n      default is module-level function IS_CHARACTER_JUNK, which filters out\n      whitespace characters (a blank or tab; note: bad idea to include newline\n      in this!).\n\n    Tools/scripts/ndiff.py is a command-line front-end to this function.\n\n    Example:\n\n    >>> diff = ndiff('one\\ntwo\\nthree\\n'.splitlines(1),\n    ...              'ore\\ntree\\nemu\\n'.splitlines(1))\n    >>> print ''.join(diff),\n    - one\n    ?  ^\n    + ore\n    ?  ^\n    - two\n    - three\n    ?  -\n    + tree\n    + emu\n    "));
      var1.setlocal("ndiff", var8);
      var3 = null;
      var1.setline(1352);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("IS_CHARACTER_JUNK")};
      var8 = new PyFunction(var1.f_globals, var7, _mdiff$35, PyString.fromInterned("Returns generator yielding marked up from/to side by side differences.\n\n    Arguments:\n    fromlines -- list of text lines to compared to tolines\n    tolines -- list of text lines to be compared to fromlines\n    context -- number of context lines to display on each side of difference,\n               if None, all from/to text lines will be generated.\n    linejunk -- passed on to ndiff (see ndiff documentation)\n    charjunk -- passed on to ndiff (see ndiff documentation)\n\n    This function returns an interator which returns a tuple:\n    (from line tuple, to line tuple, boolean flag)\n\n    from/to line tuple -- (line num, line text)\n        line num -- integer or None (to indicate a context separation)\n        line text -- original line text with following markers inserted:\n            '\\0+' -- marks start of added text\n            '\\0-' -- marks start of deleted text\n            '\\0^' -- marks start of changed text\n            '\\1' -- marks end of added/deleted/changed text\n\n    boolean flag -- None indicates context separation, True indicates\n        either \"from\" or \"to\" line contains a change, otherwise False.\n\n    This function/iterator was originally developed to generate side by side\n    file difference for making HTML pages (see HtmlDiff class for example\n    usage).\n\n    Note, this function utilizes the ndiff function to generate the side by\n    side difference markup.  Optional ndiff arguments may be passed to this\n    function and they in turn will be passed to ndiff.\n    "));
      var1.setlocal("_mdiff", var8);
      var3 = null;
      var1.setline(1616);
      PyString var9 = PyString.fromInterned("\n<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n          \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n\n<html>\n\n<head>\n    <meta http-equiv=\"Content-Type\"\n          content=\"text/html; charset=ISO-8859-1\" />\n    <title></title>\n    <style type=\"text/css\">%(styles)s\n    </style>\n</head>\n\n<body>\n    %(table)s%(legend)s\n</body>\n\n</html>");
      var1.setlocal("_file_template", var9);
      var3 = null;
      var1.setline(1636);
      var9 = PyString.fromInterned("\n        table.diff {font-family:Courier; border:medium;}\n        .diff_header {background-color:#e0e0e0}\n        td.diff_header {text-align:right}\n        .diff_next {background-color:#c0c0c0}\n        .diff_add {background-color:#aaffaa}\n        .diff_chg {background-color:#ffff77}\n        .diff_sub {background-color:#ffaaaa}");
      var1.setlocal("_styles", var9);
      var3 = null;
      var1.setline(1645);
      var9 = PyString.fromInterned("\n    <table class=\"diff\" id=\"difflib_chg_%(prefix)s_top\"\n           cellspacing=\"0\" cellpadding=\"0\" rules=\"groups\" >\n        <colgroup></colgroup> <colgroup></colgroup> <colgroup></colgroup>\n        <colgroup></colgroup> <colgroup></colgroup> <colgroup></colgroup>\n        %(header_row)s\n        <tbody>\n%(data_rows)s        </tbody>\n    </table>");
      var1.setlocal("_table_template", var9);
      var3 = null;
      var1.setline(1655);
      var9 = PyString.fromInterned("\n    <table class=\"diff\" summary=\"Legends\">\n        <tr> <th colspan=\"2\"> Legends </th> </tr>\n        <tr> <td> <table border=\"\" summary=\"Colors\">\n                      <tr><th> Colors </th> </tr>\n                      <tr><td class=\"diff_add\">&nbsp;Added&nbsp;</td></tr>\n                      <tr><td class=\"diff_chg\">Changed</td> </tr>\n                      <tr><td class=\"diff_sub\">Deleted</td> </tr>\n                  </table></td>\n             <td> <table border=\"\" summary=\"Links\">\n                      <tr><th colspan=\"2\"> Links </th> </tr>\n                      <tr><td>(f)irst change</td> </tr>\n                      <tr><td>(n)ext change</td> </tr>\n                      <tr><td>(t)op</td> </tr>\n                  </table></td> </tr>\n    </table>");
      var1.setlocal("_legend", var9);
      var3 = null;
      var1.setline(1672);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("HtmlDiff", var7, HtmlDiff$40);
      var1.setlocal("HtmlDiff", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(2020);
      var1.dellocal("re");
      var1.setline(2022);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, restore$52, PyString.fromInterned("\n    Generate one of the two sequences that generated a delta.\n\n    Given a `delta` produced by `Differ.compare()` or `ndiff()`, extract\n    lines originating from file 1 or 2 (parameter `which`), stripping off line\n    prefixes.\n\n    Examples:\n\n    >>> diff = ndiff('one\\ntwo\\nthree\\n'.splitlines(1),\n    ...              'ore\\ntree\\nemu\\n'.splitlines(1))\n    >>> diff = list(diff)\n    >>> print ''.join(restore(diff, 1)),\n    one\n    two\n    three\n    >>> print ''.join(restore(diff, 2)),\n    ore\n    tree\n    emu\n    "));
      var1.setlocal("restore", var8);
      var3 = null;
      var1.setline(2054);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _test$53, (PyObject)null);
      var1.setlocal("_test", var8);
      var3 = null;
      var1.setline(2058);
      var5 = var1.getname("__name__");
      PyObject var10000 = var5._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2059);
         var1.getname("_test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _calculate_ratio$1(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(43);
         PyObject var4 = Py.newFloat(2.0)._mul(var1.getlocal(0))._div(var1.getlocal(1));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(44);
         PyFloat var3 = Py.newFloat(1.0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject SequenceMatcher$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    SequenceMatcher is a flexible class for comparing pairs of sequences of\n    any type, so long as the sequence elements are hashable.  The basic\n    algorithm predates, and is a little fancier than, an algorithm\n    published in the late 1980's by Ratcliff and Obershelp under the\n    hyperbolic name \"gestalt pattern matching\".  The basic idea is to find\n    the longest contiguous matching subsequence that contains no \"junk\"\n    elements (R-O doesn't address junk).  The same idea is then applied\n    recursively to the pieces of the sequences to the left and to the right\n    of the matching subsequence.  This does not yield minimal edit\n    sequences, but does tend to yield matches that \"look right\" to people.\n\n    SequenceMatcher tries to compute a \"human-friendly diff\" between two\n    sequences.  Unlike e.g. UNIX(tm) diff, the fundamental notion is the\n    longest *contiguous* & junk-free matching subsequence.  That's what\n    catches peoples' eyes.  The Windows(tm) windiff has another interesting\n    notion, pairing up elements that appear uniquely in each sequence.\n    That, and the method here, appear to yield more intuitive difference\n    reports than does diff.  This method appears to be the least vulnerable\n    to synching up on blocks of \"junk lines\", though (like blank lines in\n    ordinary text files, or maybe \"<P>\" lines in HTML files).  That may be\n    because this is the only method of the 3 that has a *concept* of\n    \"junk\" <wink>.\n\n    Example, comparing two strings, and considering blanks to be \"junk\":\n\n    >>> s = SequenceMatcher(lambda x: x == \" \",\n    ...                     \"private Thread currentThread;\",\n    ...                     \"private volatile Thread currentThread;\")\n    >>>\n\n    .ratio() returns a float in [0, 1], measuring the \"similarity\" of the\n    sequences.  As a rule of thumb, a .ratio() value over 0.6 means the\n    sequences are close matches:\n\n    >>> print round(s.ratio(), 3)\n    0.866\n    >>>\n\n    If you're only interested in where the sequences match,\n    .get_matching_blocks() is handy:\n\n    >>> for block in s.get_matching_blocks():\n    ...     print \"a[%d] and b[%d] match for %d elements\" % block\n    a[0] and b[0] match for 8 elements\n    a[8] and b[17] match for 21 elements\n    a[29] and b[38] match for 0 elements\n\n    Note that the last tuple returned by .get_matching_blocks() is always a\n    dummy, (len(a), len(b), 0), and this is the only case in which the last\n    tuple element (number of elements matched) is 0.\n\n    If you want to know how to change the first sequence into the second,\n    use .get_opcodes():\n\n    >>> for opcode in s.get_opcodes():\n    ...     print \"%6s a[%d:%d] b[%d:%d]\" % opcode\n     equal a[0:8] b[0:8]\n    insert a[8:8] b[8:17]\n     equal a[8:29] b[17:38]\n\n    See the Differ class for a fancy human-friendly file differencer, which\n    uses SequenceMatcher both to compare sequences of lines, and to compare\n    sequences of characters within similar (near-matching) lines.\n\n    See also function get_close_matches() in this module, which shows how\n    simple code building on SequenceMatcher can be used to do useful work.\n\n    Timing:  Basic R-O is cubic time worst case and quadratic time expected\n    case.  SequenceMatcher is quadratic time for the worst case and has\n    expected-case behavior dependent in a complicated way on how many\n    elements the sequences have in common; best case time is linear.\n\n    Methods:\n\n    __init__(isjunk=None, a='', b='')\n        Construct a SequenceMatcher.\n\n    set_seqs(a, b)\n        Set the two sequences to be compared.\n\n    set_seq1(a)\n        Set the first sequence to be compared.\n\n    set_seq2(b)\n        Set the second sequence to be compared.\n\n    find_longest_match(alo, ahi, blo, bhi)\n        Find longest matching block in a[alo:ahi] and b[blo:bhi].\n\n    get_matching_blocks()\n        Return list of triples describing matching subsequences.\n\n    get_opcodes()\n        Return list of 5-tuples describing how to turn a into b.\n\n    ratio()\n        Return a measure of the sequences' similarity (float in [0,1]).\n\n    quick_ratio()\n        Return an upper bound on .ratio() relatively quickly.\n\n    real_quick_ratio()\n        Return an upper bound on ratio() very quickly.\n    "));
      var1.setline(152);
      PyString.fromInterned("\n    SequenceMatcher is a flexible class for comparing pairs of sequences of\n    any type, so long as the sequence elements are hashable.  The basic\n    algorithm predates, and is a little fancier than, an algorithm\n    published in the late 1980's by Ratcliff and Obershelp under the\n    hyperbolic name \"gestalt pattern matching\".  The basic idea is to find\n    the longest contiguous matching subsequence that contains no \"junk\"\n    elements (R-O doesn't address junk).  The same idea is then applied\n    recursively to the pieces of the sequences to the left and to the right\n    of the matching subsequence.  This does not yield minimal edit\n    sequences, but does tend to yield matches that \"look right\" to people.\n\n    SequenceMatcher tries to compute a \"human-friendly diff\" between two\n    sequences.  Unlike e.g. UNIX(tm) diff, the fundamental notion is the\n    longest *contiguous* & junk-free matching subsequence.  That's what\n    catches peoples' eyes.  The Windows(tm) windiff has another interesting\n    notion, pairing up elements that appear uniquely in each sequence.\n    That, and the method here, appear to yield more intuitive difference\n    reports than does diff.  This method appears to be the least vulnerable\n    to synching up on blocks of \"junk lines\", though (like blank lines in\n    ordinary text files, or maybe \"<P>\" lines in HTML files).  That may be\n    because this is the only method of the 3 that has a *concept* of\n    \"junk\" <wink>.\n\n    Example, comparing two strings, and considering blanks to be \"junk\":\n\n    >>> s = SequenceMatcher(lambda x: x == \" \",\n    ...                     \"private Thread currentThread;\",\n    ...                     \"private volatile Thread currentThread;\")\n    >>>\n\n    .ratio() returns a float in [0, 1], measuring the \"similarity\" of the\n    sequences.  As a rule of thumb, a .ratio() value over 0.6 means the\n    sequences are close matches:\n\n    >>> print round(s.ratio(), 3)\n    0.866\n    >>>\n\n    If you're only interested in where the sequences match,\n    .get_matching_blocks() is handy:\n\n    >>> for block in s.get_matching_blocks():\n    ...     print \"a[%d] and b[%d] match for %d elements\" % block\n    a[0] and b[0] match for 8 elements\n    a[8] and b[17] match for 21 elements\n    a[29] and b[38] match for 0 elements\n\n    Note that the last tuple returned by .get_matching_blocks() is always a\n    dummy, (len(a), len(b), 0), and this is the only case in which the last\n    tuple element (number of elements matched) is 0.\n\n    If you want to know how to change the first sequence into the second,\n    use .get_opcodes():\n\n    >>> for opcode in s.get_opcodes():\n    ...     print \"%6s a[%d:%d] b[%d:%d]\" % opcode\n     equal a[0:8] b[0:8]\n    insert a[8:8] b[8:17]\n     equal a[8:29] b[17:38]\n\n    See the Differ class for a fancy human-friendly file differencer, which\n    uses SequenceMatcher both to compare sequences of lines, and to compare\n    sequences of characters within similar (near-matching) lines.\n\n    See also function get_close_matches() in this module, which shows how\n    simple code building on SequenceMatcher can be used to do useful work.\n\n    Timing:  Basic R-O is cubic time worst case and quadratic time expected\n    case.  SequenceMatcher is quadratic time for the worst case and has\n    expected-case behavior dependent in a complicated way on how many\n    elements the sequences have in common; best case time is linear.\n\n    Methods:\n\n    __init__(isjunk=None, a='', b='')\n        Construct a SequenceMatcher.\n\n    set_seqs(a, b)\n        Set the two sequences to be compared.\n\n    set_seq1(a)\n        Set the first sequence to be compared.\n\n    set_seq2(b)\n        Set the second sequence to be compared.\n\n    find_longest_match(alo, ahi, blo, bhi)\n        Find longest matching block in a[alo:ahi] and b[blo:bhi].\n\n    get_matching_blocks()\n        Return list of triples describing matching subsequences.\n\n    get_opcodes()\n        Return list of 5-tuples describing how to turn a into b.\n\n    ratio()\n        Return a measure of the sequences' similarity (float in [0,1]).\n\n    quick_ratio()\n        Return an upper bound on .ratio() relatively quickly.\n\n    real_quick_ratio()\n        Return an upper bound on ratio() very quickly.\n    ");
      var1.setline(154);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Construct a SequenceMatcher.\n\n        Optional arg isjunk is None (the default), or a one-argument\n        function that takes a sequence element and returns true iff the\n        element is junk.  None is equivalent to passing \"lambda x: 0\", i.e.\n        no elements are considered to be junk.  For example, pass\n            lambda x: x in \" \\t\"\n        if you're comparing lines as sequences of characters, and don't\n        want to synch up on blanks or hard tabs.\n\n        Optional arg a is the first of two sequences to be compared.  By\n        default, an empty string.  The elements of a must be hashable.  See\n        also .set_seqs() and .set_seq1().\n\n        Optional arg b is the second of two sequences to be compared.  By\n        default, an empty string.  The elements of b must be hashable. See\n        also .set_seqs() and .set_seq2().\n\n        Optional arg autojunk should be set to False to disable the\n        \"automatic junk heuristic\" that treats popular elements as junk\n        (see module documentation for more information).\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_seqs$4, PyString.fromInterned("Set the two sequences to be compared.\n\n        >>> s = SequenceMatcher()\n        >>> s.set_seqs(\"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        "));
      var1.setlocal("set_seqs", var4);
      var3 = null;
      var1.setline(235);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_seq1$5, PyString.fromInterned("Set the first sequence to be compared.\n\n        The second sequence to be compared is not changed.\n\n        >>> s = SequenceMatcher(None, \"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        >>> s.set_seq1(\"bcde\")\n        >>> s.ratio()\n        1.0\n        >>>\n\n        SequenceMatcher computes and caches detailed information about the\n        second sequence, so if you want to compare one sequence S against\n        many sequences, use .set_seq2(S) once and call .set_seq1(x)\n        repeatedly for each of the other sequences.\n\n        See also set_seqs() and set_seq2().\n        "));
      var1.setlocal("set_seq1", var4);
      var3 = null;
      var1.setline(261);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_seq2$6, PyString.fromInterned("Set the second sequence to be compared.\n\n        The first sequence to be compared is not changed.\n\n        >>> s = SequenceMatcher(None, \"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        >>> s.set_seq2(\"abcd\")\n        >>> s.ratio()\n        1.0\n        >>>\n\n        SequenceMatcher computes and caches detailed information about the\n        second sequence, so if you want to compare one sequence S against\n        many sequences, use .set_seq2(S) once and call .set_seq1(x)\n        repeatedly for each of the other sequences.\n\n        See also set_seqs() and set_seq1().\n        "));
      var1.setlocal("set_seq2", var4);
      var3 = null;
      var1.setline(306);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _SequenceMatcher__chain_b$7, (PyObject)null);
      var1.setlocal("_SequenceMatcher__chain_b", var4);
      var3 = null;
      var1.setline(350);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_longest_match$8, PyString.fromInterned("Find longest matching block in a[alo:ahi] and b[blo:bhi].\n\n        If isjunk is not defined:\n\n        Return (i,j,k) such that a[i:i+k] is equal to b[j:j+k], where\n            alo <= i <= i+k <= ahi\n            blo <= j <= j+k <= bhi\n        and for all (i',j',k') meeting those conditions,\n            k >= k'\n            i <= i'\n            and if i == i', j <= j'\n\n        In other words, of all maximal matching blocks, return one that\n        starts earliest in a, and of all those maximal matching blocks that\n        start earliest in a, return the one that starts earliest in b.\n\n        >>> s = SequenceMatcher(None, \" abcd\", \"abcd abcd\")\n        >>> s.find_longest_match(0, 5, 0, 9)\n        Match(a=0, b=4, size=5)\n\n        If isjunk is defined, first the longest matching block is\n        determined as above, but with the additional restriction that no\n        junk element appears in the block.  Then that block is extended as\n        far as possible by matching (only) junk elements on both sides.  So\n        the resulting block never matches on junk except as identical junk\n        happens to be adjacent to an \"interesting\" match.\n\n        Here's the same example as before, but considering blanks to be\n        junk.  That prevents \" abcd\" from matching the \" abcd\" at the tail\n        end of the second sequence directly.  Instead only the \"abcd\" can\n        match, and matches the leftmost \"abcd\" in the second sequence:\n\n        >>> s = SequenceMatcher(lambda x: x==\" \", \" abcd\", \"abcd abcd\")\n        >>> s.find_longest_match(0, 5, 0, 9)\n        Match(a=1, b=0, size=4)\n\n        If no blocks match, return (alo, blo, 0).\n\n        >>> s = SequenceMatcher(None, \"ab\", \"c\")\n        >>> s.find_longest_match(0, 2, 0, 1)\n        Match(a=0, b=0, size=0)\n        "));
      var1.setlocal("find_longest_match", var4);
      var3 = null;
      var1.setline(460);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_matching_blocks$9, PyString.fromInterned("Return list of triples describing matching subsequences.\n\n        Each triple is of the form (i, j, n), and means that\n        a[i:i+n] == b[j:j+n].  The triples are monotonically increasing in\n        i and in j.  New in Python 2.5, it's also guaranteed that if\n        (i, j, n) and (i', j', n') are adjacent triples in the list, and\n        the second is not the last triple in the list, then i+n != i' or\n        j+n != j'.  IOW, adjacent triples never describe adjacent equal\n        blocks.\n\n        The last triple is a dummy, (len(a), len(b), 0), and is the only\n        triple with n==0.\n\n        >>> s = SequenceMatcher(None, \"abxcd\", \"abcd\")\n        >>> s.get_matching_blocks()\n        [Match(a=0, b=0, size=2), Match(a=3, b=2, size=2), Match(a=5, b=4, size=0)]\n        "));
      var1.setlocal("get_matching_blocks", var4);
      var3 = null;
      var1.setline(531);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_opcodes$10, PyString.fromInterned("Return list of 5-tuples describing how to turn a into b.\n\n        Each tuple is of the form (tag, i1, i2, j1, j2).  The first tuple\n        has i1 == j1 == 0, and remaining tuples have i1 == the i2 from the\n        tuple preceding it, and likewise for j1 == the previous j2.\n\n        The tags are strings, with these meanings:\n\n        'replace':  a[i1:i2] should be replaced by b[j1:j2]\n        'delete':   a[i1:i2] should be deleted.\n                    Note that j1==j2 in this case.\n        'insert':   b[j1:j2] should be inserted at a[i1:i1].\n                    Note that i1==i2 in this case.\n        'equal':    a[i1:i2] == b[j1:j2]\n\n        >>> a = \"qabxcd\"\n        >>> b = \"abycdf\"\n        >>> s = SequenceMatcher(None, a, b)\n        >>> for tag, i1, i2, j1, j2 in s.get_opcodes():\n        ...    print (\"%7s a[%d:%d] (%s) b[%d:%d] (%s)\" %\n        ...           (tag, i1, i2, a[i1:i2], j1, j2, b[j1:j2]))\n         delete a[0:1] (q) b[0:0] ()\n          equal a[1:3] (ab) b[0:2] (ab)\n        replace a[3:4] (x) b[2:3] (y)\n          equal a[4:6] (cd) b[3:5] (cd)\n         insert a[6:6] () b[5:6] (f)\n        "));
      var1.setlocal("get_opcodes", var4);
      var3 = null;
      var1.setline(586);
      var3 = new PyObject[]{Py.newInteger(3)};
      var4 = new PyFunction(var1.f_globals, var3, get_grouped_opcodes$11, PyString.fromInterned(" Isolate change clusters by eliminating ranges with no changes.\n\n        Return a generator of groups with upto n lines of context.\n        Each group is in the same format as returned by get_opcodes().\n\n        >>> from pprint import pprint\n        >>> a = map(str, range(1,40))\n        >>> b = a[:]\n        >>> b[8:8] = ['i']     # Make an insertion\n        >>> b[20] += 'x'       # Make a replacement\n        >>> b[23:28] = []      # Make a deletion\n        >>> b[30] += 'y'       # Make another replacement\n        >>> pprint(list(SequenceMatcher(None,a,b).get_grouped_opcodes()))\n        [[('equal', 5, 8, 5, 8), ('insert', 8, 8, 8, 9), ('equal', 8, 11, 9, 12)],\n         [('equal', 16, 19, 17, 20),\n          ('replace', 19, 20, 20, 21),\n          ('equal', 20, 22, 21, 23),\n          ('delete', 22, 27, 23, 23),\n          ('equal', 27, 30, 23, 26)],\n         [('equal', 31, 34, 27, 30),\n          ('replace', 34, 35, 30, 31),\n          ('equal', 35, 38, 31, 34)]]\n        "));
      var1.setlocal("get_grouped_opcodes", var4);
      var3 = null;
      var1.setline(636);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ratio$12, PyString.fromInterned("Return a measure of the sequences' similarity (float in [0,1]).\n\n        Where T is the total number of elements in both sequences, and\n        M is the number of matches, this is 2.0*M / T.\n        Note that this is 1 if the sequences are identical, and 0 if\n        they have nothing in common.\n\n        .ratio() is expensive to compute if you haven't already computed\n        .get_matching_blocks() or .get_opcodes(), in which case you may\n        want to try .quick_ratio() or .real_quick_ratio() first to get an\n        upper bound.\n\n        >>> s = SequenceMatcher(None, \"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        >>> s.quick_ratio()\n        0.75\n        >>> s.real_quick_ratio()\n        1.0\n        "));
      var1.setlocal("ratio", var4);
      var3 = null;
      var1.setline(662);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, quick_ratio$14, PyString.fromInterned("Return an upper bound on ratio() relatively quickly.\n\n        This isn't defined beyond that it is an upper bound on .ratio(), and\n        is faster to compute.\n        "));
      var1.setlocal("quick_ratio", var4);
      var3 = null;
      var1.setline(691);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, real_quick_ratio$15, PyString.fromInterned("Return an upper bound on ratio() very quickly.\n\n        This isn't defined beyond that it is an upper bound on .ratio(), and\n        is faster to compute than either .ratio() or .quick_ratio().\n        "));
      var1.setlocal("real_quick_ratio", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyString.fromInterned("Construct a SequenceMatcher.\n\n        Optional arg isjunk is None (the default), or a one-argument\n        function that takes a sequence element and returns true iff the\n        element is junk.  None is equivalent to passing \"lambda x: 0\", i.e.\n        no elements are considered to be junk.  For example, pass\n            lambda x: x in \" \\t\"\n        if you're comparing lines as sequences of characters, and don't\n        want to synch up on blanks or hard tabs.\n\n        Optional arg a is the first of two sequences to be compared.  By\n        default, an empty string.  The elements of a must be hashable.  See\n        also .set_seqs() and .set_seq1().\n\n        Optional arg b is the second of two sequences to be compared.  By\n        default, an empty string.  The elements of b must be hashable. See\n        also .set_seqs() and .set_seq2().\n\n        Optional arg autojunk should be set to False to disable the\n        \"automatic junk heuristic\" that treats popular elements as junk\n        (see module documentation for more information).\n        ");
      var1.setline(218);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("isjunk", var3);
      var3 = null;
      var1.setline(219);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("a", var3);
      var1.getlocal(0).__setattr__("b", var3);
      var1.setline(220);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("autojunk", var3);
      var3 = null;
      var1.setline(221);
      var1.getlocal(0).__getattr__("set_seqs").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_seqs$4(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("Set the two sequences to be compared.\n\n        >>> s = SequenceMatcher()\n        >>> s.set_seqs(\"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        ");
      var1.setline(232);
      var1.getlocal(0).__getattr__("set_seq1").__call__(var2, var1.getlocal(1));
      var1.setline(233);
      var1.getlocal(0).__getattr__("set_seq2").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_seq1$5(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyString.fromInterned("Set the first sequence to be compared.\n\n        The second sequence to be compared is not changed.\n\n        >>> s = SequenceMatcher(None, \"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        >>> s.set_seq1(\"bcde\")\n        >>> s.ratio()\n        1.0\n        >>>\n\n        SequenceMatcher computes and caches detailed information about the\n        second sequence, so if you want to compare one sequence S against\n        many sequences, use .set_seq2(S) once and call .set_seq1(x)\n        repeatedly for each of the other sequences.\n\n        See also set_seqs() and set_seq2().\n        ");
      var1.setline(256);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getlocal(0).__getattr__("a"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(257);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(258);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("a", var3);
         var3 = null;
         var1.setline(259);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("matching_blocks", var3);
         var1.getlocal(0).__setattr__("opcodes", var3);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject set_seq2$6(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyString.fromInterned("Set the second sequence to be compared.\n\n        The first sequence to be compared is not changed.\n\n        >>> s = SequenceMatcher(None, \"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        >>> s.set_seq2(\"abcd\")\n        >>> s.ratio()\n        1.0\n        >>>\n\n        SequenceMatcher computes and caches detailed information about the\n        second sequence, so if you want to compare one sequence S against\n        many sequences, use .set_seq2(S) once and call .set_seq1(x)\n        repeatedly for each of the other sequences.\n\n        See also set_seqs() and set_seq1().\n        ");
      var1.setline(282);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getlocal(0).__getattr__("b"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(283);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(284);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("b", var3);
         var3 = null;
         var1.setline(285);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("matching_blocks", var3);
         var1.getlocal(0).__setattr__("opcodes", var3);
         var1.setline(286);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("fullbcount", var3);
         var3 = null;
         var1.setline(287);
         var1.getlocal(0).__getattr__("_SequenceMatcher__chain_b").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _SequenceMatcher__chain_b$7(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyObject var3 = var1.getlocal(0).__getattr__("b");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(318);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"b2j", var7);
      var1.setlocal(2, var7);
      var1.setline(320);
      var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(320);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var8;
         if (var4 == null) {
            var1.setline(325);
            var3 = var1.getglobal("set").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(326);
            var3 = var1.getlocal(0).__getattr__("isjunk");
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(327);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(328);
               var3 = var1.getglobal("list").__call__(var2, var1.getlocal(2).__getattr__("keys").__call__(var2)).__iter__();

               while(true) {
                  var1.setline(328);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(329);
                  if (var1.getlocal(7).__call__(var2, var1.getlocal(4)).__nonzero__()) {
                     var1.setline(330);
                     var1.getlocal(6).__getattr__("add").__call__(var2, var1.getlocal(4));
                     var1.setline(331);
                     var1.getlocal(2).__delitem__(var1.getlocal(4));
                  }
               }
            }

            var1.setline(334);
            var3 = var1.getglobal("set").__call__(var2);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(335);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(336);
            PyObject var10000 = var1.getlocal(0).__getattr__("autojunk");
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(9);
               var10000 = var3._ge(Py.newInteger(200));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(337);
               var3 = var1.getlocal(9)._floordiv(Py.newInteger(100))._add(Py.newInteger(1));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(338);
               var3 = var1.getglobal("list").__call__(var2, var1.getlocal(2).__getattr__("items").__call__(var2)).__iter__();

               while(true) {
                  var1.setline(338);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(11, var6);
                  var6 = null;
                  var1.setline(339);
                  var8 = var1.getglobal("len").__call__(var2, var1.getlocal(11));
                  var10000 = var8._gt(var1.getlocal(10));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(340);
                     var1.getlocal(8).__getattr__("add").__call__(var2, var1.getlocal(4));
                     var1.setline(341);
                     var1.getlocal(2).__delitem__(var1.getlocal(4));
                  }
               }
            }

            var1.setline(347);
            var3 = var1.getlocal(6).__getattr__("__contains__");
            var1.getlocal(0).__setattr__("isbjunk", var3);
            var3 = null;
            var1.setline(348);
            var3 = var1.getlocal(8).__getattr__("__contains__");
            var1.getlocal(0).__setattr__("isbpopular", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(321);
         var8 = var1.getlocal(2).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyList(Py.EmptyObjects)));
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(322);
         var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject find_longest_match$8(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyString.fromInterned("Find longest matching block in a[alo:ahi] and b[blo:bhi].\n\n        If isjunk is not defined:\n\n        Return (i,j,k) such that a[i:i+k] is equal to b[j:j+k], where\n            alo <= i <= i+k <= ahi\n            blo <= j <= j+k <= bhi\n        and for all (i',j',k') meeting those conditions,\n            k >= k'\n            i <= i'\n            and if i == i', j <= j'\n\n        In other words, of all maximal matching blocks, return one that\n        starts earliest in a, and of all those maximal matching blocks that\n        start earliest in a, return the one that starts earliest in b.\n\n        >>> s = SequenceMatcher(None, \" abcd\", \"abcd abcd\")\n        >>> s.find_longest_match(0, 5, 0, 9)\n        Match(a=0, b=4, size=5)\n\n        If isjunk is defined, first the longest matching block is\n        determined as above, but with the additional restriction that no\n        junk element appears in the block.  Then that block is extended as\n        far as possible by matching (only) junk elements on both sides.  So\n        the resulting block never matches on junk except as identical junk\n        happens to be adjacent to an \"interesting\" match.\n\n        Here's the same example as before, but considering blanks to be\n        junk.  That prevents \" abcd\" from matching the \" abcd\" at the tail\n        end of the second sequence directly.  Instead only the \"abcd\" can\n        match, and matches the leftmost \"abcd\" in the second sequence:\n\n        >>> s = SequenceMatcher(lambda x: x==\" \", \" abcd\", \"abcd abcd\")\n        >>> s.find_longest_match(0, 5, 0, 9)\n        Match(a=1, b=0, size=4)\n\n        If no blocks match, return (alo, blo, 0).\n\n        >>> s = SequenceMatcher(None, \"ab\", \"c\")\n        >>> s.find_longest_match(0, 2, 0, 1)\n        Match(a=0, b=0, size=0)\n        ");
      var1.setline(406);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("a"), var1.getlocal(0).__getattr__("b"), var1.getlocal(0).__getattr__("b2j"), var1.getlocal(0).__getattr__("isbjunk")});
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(407);
      var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3), Py.newInteger(0)});
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(411);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(12, var10);
      var3 = null;
      var1.setline(412);
      PyList var12 = new PyList(Py.EmptyObjects);
      var1.setlocal(13, var12);
      var3 = null;
      var1.setline(413);
      PyObject var13 = var1.getglobal("xrange").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(413);
         PyObject var11 = var13.__iternext__();
         PyObject var10000;
         if (var11 == null) {
            while(true) {
               var1.setline(433);
               var13 = var1.getlocal(9);
               var10000 = var13._gt(var1.getlocal(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var13 = var1.getlocal(10);
                  var10000 = var13._gt(var1.getlocal(3));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(8).__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(10)._sub(Py.newInteger(1)))).__not__();
                     if (var10000.__nonzero__()) {
                        var13 = var1.getlocal(5).__getitem__(var1.getlocal(9)._sub(Py.newInteger(1)));
                        var10000 = var13._eq(var1.getlocal(6).__getitem__(var1.getlocal(10)._sub(Py.newInteger(1))));
                        var3 = null;
                     }
                  }
               }

               if (!var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(437);
                     var13 = var1.getlocal(9)._add(var1.getlocal(11));
                     var10000 = var13._lt(var1.getlocal(2));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var13 = var1.getlocal(10)._add(var1.getlocal(11));
                        var10000 = var13._lt(var1.getlocal(4));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(8).__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(10)._add(var1.getlocal(11)))).__not__();
                           if (var10000.__nonzero__()) {
                              var13 = var1.getlocal(5).__getitem__(var1.getlocal(9)._add(var1.getlocal(11)));
                              var10000 = var13._eq(var1.getlocal(6).__getitem__(var1.getlocal(10)._add(var1.getlocal(11))));
                              var3 = null;
                           }
                        }
                     }

                     if (!var10000.__nonzero__()) {
                        while(true) {
                           var1.setline(449);
                           var13 = var1.getlocal(9);
                           var10000 = var13._gt(var1.getlocal(1));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var13 = var1.getlocal(10);
                              var10000 = var13._gt(var1.getlocal(3));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(8).__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(10)._sub(Py.newInteger(1))));
                                 if (var10000.__nonzero__()) {
                                    var13 = var1.getlocal(5).__getitem__(var1.getlocal(9)._sub(Py.newInteger(1)));
                                    var10000 = var13._eq(var1.getlocal(6).__getitem__(var1.getlocal(10)._sub(Py.newInteger(1))));
                                    var3 = null;
                                 }
                              }
                           }

                           if (!var10000.__nonzero__()) {
                              while(true) {
                                 var1.setline(453);
                                 var13 = var1.getlocal(9)._add(var1.getlocal(11));
                                 var10000 = var13._lt(var1.getlocal(2));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var13 = var1.getlocal(10)._add(var1.getlocal(11));
                                    var10000 = var13._lt(var1.getlocal(4));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var10000 = var1.getlocal(8).__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(10)._add(var1.getlocal(11))));
                                       if (var10000.__nonzero__()) {
                                          var13 = var1.getlocal(5).__getitem__(var1.getlocal(9)._add(var1.getlocal(11)));
                                          var10000 = var13._eq(var1.getlocal(6).__getitem__(var1.getlocal(10)._add(var1.getlocal(11))));
                                          var3 = null;
                                       }
                                    }
                                 }

                                 if (!var10000.__nonzero__()) {
                                    var1.setline(458);
                                    var13 = var1.getglobal("Match").__call__(var2, var1.getlocal(9), var1.getlocal(10), var1.getlocal(11));
                                    var1.f_lasti = -1;
                                    return var13;
                                 }

                                 var1.setline(456);
                                 var13 = var1.getlocal(11)._add(Py.newInteger(1));
                                 var1.setlocal(11, var13);
                                 var3 = null;
                              }
                           }

                           var1.setline(452);
                           var3 = new PyTuple(new PyObject[]{var1.getlocal(9)._sub(Py.newInteger(1)), var1.getlocal(10)._sub(Py.newInteger(1)), var1.getlocal(11)._add(Py.newInteger(1))});
                           var4 = Py.unpackSequence(var3, 3);
                           var5 = var4[0];
                           var1.setlocal(9, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(10, var5);
                           var5 = null;
                           var5 = var4[2];
                           var1.setlocal(11, var5);
                           var5 = null;
                           var3 = null;
                        }
                     }

                     var1.setline(440);
                     var13 = var1.getlocal(11);
                     var13 = var13._iadd(Py.newInteger(1));
                     var1.setlocal(11, var13);
                  }
               }

               var1.setline(436);
               var3 = new PyTuple(new PyObject[]{var1.getlocal(9)._sub(Py.newInteger(1)), var1.getlocal(10)._sub(Py.newInteger(1)), var1.getlocal(11)._add(Py.newInteger(1))});
               var4 = Py.unpackSequence(var3, 3);
               var5 = var4[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(10, var5);
               var5 = null;
               var5 = var4[2];
               var1.setlocal(11, var5);
               var5 = null;
               var3 = null;
            }
         }

         var1.setlocal(14, var11);
         var1.setline(416);
         var5 = var1.getlocal(12).__getattr__("get");
         var1.setlocal(15, var5);
         var5 = null;
         var1.setline(417);
         PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(16, var15);
         var5 = null;
         var1.setline(418);
         var5 = var1.getlocal(7).__getattr__("get").__call__(var2, var1.getlocal(5).__getitem__(var1.getlocal(14)), var1.getlocal(13)).__iter__();

         while(true) {
            var1.setline(418);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(17, var6);
            var1.setline(420);
            PyObject var7 = var1.getlocal(17);
            var10000 = var7._lt(var1.getlocal(3));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(422);
               var7 = var1.getlocal(17);
               var10000 = var7._ge(var1.getlocal(4));
               var7 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(424);
               var7 = var1.getlocal(15).__call__((ThreadState)var2, (PyObject)var1.getlocal(17)._sub(Py.newInteger(1)), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
               var1.setlocal(18, var7);
               var1.getlocal(16).__setitem__(var1.getlocal(17), var7);
               var1.setline(425);
               var7 = var1.getlocal(18);
               var10000 = var7._gt(var1.getlocal(11));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(426);
                  PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(14)._sub(var1.getlocal(18))._add(Py.newInteger(1)), var1.getlocal(17)._sub(var1.getlocal(18))._add(Py.newInteger(1)), var1.getlocal(18)});
                  PyObject[] var8 = Py.unpackSequence(var14, 3);
                  PyObject var9 = var8[0];
                  var1.setlocal(9, var9);
                  var9 = null;
                  var9 = var8[1];
                  var1.setlocal(10, var9);
                  var9 = null;
                  var9 = var8[2];
                  var1.setlocal(11, var9);
                  var9 = null;
                  var7 = null;
               }
            }
         }

         var1.setline(427);
         var5 = var1.getlocal(16);
         var1.setlocal(12, var5);
         var5 = null;
      }
   }

   public PyObject get_matching_blocks$9(PyFrame var1, ThreadState var2) {
      var1.setline(477);
      PyString.fromInterned("Return list of triples describing matching subsequences.\n\n        Each triple is of the form (i, j, n), and means that\n        a[i:i+n] == b[j:j+n].  The triples are monotonically increasing in\n        i and in j.  New in Python 2.5, it's also guaranteed that if\n        (i, j, n) and (i', j', n') are adjacent triples in the list, and\n        the second is not the last triple in the list, then i+n != i' or\n        j+n != j'.  IOW, adjacent triples never describe adjacent equal\n        blocks.\n\n        The last triple is a dummy, (len(a), len(b), 0), and is the only\n        triple with n==0.\n\n        >>> s = SequenceMatcher(None, \"abxcd\", \"abcd\")\n        >>> s.get_matching_blocks()\n        [Match(a=0, b=0, size=2), Match(a=3, b=2, size=2), Match(a=5, b=4, size=0)]\n        ");
      var1.setline(479);
      PyObject var3 = var1.getlocal(0).__getattr__("matching_blocks");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(480);
         var3 = var1.getlocal(0).__getattr__("matching_blocks");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(481);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("a")), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("b"))});
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var4 = null;
         var1.setline(489);
         PyList var9 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(0), var1.getlocal(1), Py.newInteger(0), var1.getlocal(2)})});
         var1.setlocal(3, var9);
         var4 = null;
         var1.setline(490);
         var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var9);
         var4 = null;

         while(true) {
            var1.setline(491);
            PyObject var10;
            if (!var1.getlocal(3).__nonzero__()) {
               var1.setline(503);
               var1.getlocal(4).__getattr__("sort").__call__(var2);
               var1.setline(508);
               PyInteger var13 = Py.newInteger(0);
               var1.setlocal(13, var13);
               var1.setlocal(14, var13);
               var1.setlocal(15, var13);
               var1.setline(509);
               var9 = new PyList(Py.EmptyObjects);
               var1.setlocal(16, var9);
               var4 = null;
               var1.setline(510);
               var10 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(510);
                  PyObject var11 = var10.__iternext__();
                  if (var11 == null) {
                     var1.setline(524);
                     if (var1.getlocal(15).__nonzero__()) {
                        var1.setline(525);
                        var1.getlocal(16).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(13), var1.getlocal(14), var1.getlocal(15)})));
                     }

                     var1.setline(527);
                     var1.getlocal(16).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(0)})));
                     var1.setline(528);
                     var10 = var1.getlocal(16);
                     var1.getlocal(0).__setattr__("matching_blocks", var10);
                     var4 = null;
                     var1.setline(529);
                     var3 = var1.getglobal("map").__call__(var2, var1.getglobal("Match").__getattr__("_make"), var1.getlocal(0).__getattr__("matching_blocks"));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  PyObject[] var14 = Py.unpackSequence(var11, 3);
                  PyObject var7 = var14[0];
                  var1.setlocal(17, var7);
                  var7 = null;
                  var7 = var14[1];
                  var1.setlocal(18, var7);
                  var7 = null;
                  var7 = var14[2];
                  var1.setlocal(19, var7);
                  var7 = null;
                  var1.setline(512);
                  var6 = var1.getlocal(13)._add(var1.getlocal(15));
                  var10000 = var6._eq(var1.getlocal(17));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var6 = var1.getlocal(14)._add(var1.getlocal(15));
                     var10000 = var6._eq(var1.getlocal(18));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(516);
                     var6 = var1.getlocal(15);
                     var6 = var6._iadd(var1.getlocal(19));
                     var1.setlocal(15, var6);
                  } else {
                     var1.setline(521);
                     if (var1.getlocal(15).__nonzero__()) {
                        var1.setline(522);
                        var1.getlocal(16).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(13), var1.getlocal(14), var1.getlocal(15)})));
                     }

                     var1.setline(523);
                     PyTuple var15 = new PyTuple(new PyObject[]{var1.getlocal(17), var1.getlocal(18), var1.getlocal(19)});
                     PyObject[] var12 = Py.unpackSequence(var15, 3);
                     PyObject var8 = var12[0];
                     var1.setlocal(13, var8);
                     var8 = null;
                     var8 = var12[1];
                     var1.setlocal(14, var8);
                     var8 = null;
                     var8 = var12[2];
                     var1.setlocal(15, var8);
                     var8 = null;
                     var6 = null;
                  }
               }
            }

            var1.setline(492);
            var10 = var1.getlocal(3).__getattr__("pop").__call__(var2);
            var5 = Py.unpackSequence(var10, 4);
            var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
            var1.setline(493);
            var10 = var1.getlocal(0).__getattr__("find_longest_match").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8));
            var5 = Py.unpackSequence(var10, 3);
            var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(11, var6);
            var6 = null;
            var1.setlocal(12, var10);
            var1.setline(497);
            if (var1.getlocal(11).__nonzero__()) {
               var1.setline(498);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(12));
               var1.setline(499);
               var10 = var1.getlocal(5);
               var10000 = var10._lt(var1.getlocal(9));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var10 = var1.getlocal(7);
                  var10000 = var10._lt(var1.getlocal(10));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(500);
                  var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9), var1.getlocal(7), var1.getlocal(10)})));
               }

               var1.setline(501);
               var10 = var1.getlocal(9)._add(var1.getlocal(11));
               var10000 = var10._lt(var1.getlocal(6));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var10 = var1.getlocal(10)._add(var1.getlocal(11));
                  var10000 = var10._lt(var1.getlocal(8));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(502);
                  var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9)._add(var1.getlocal(11)), var1.getlocal(6), var1.getlocal(10)._add(var1.getlocal(11)), var1.getlocal(8)})));
               }
            }
         }
      }
   }

   public PyObject get_opcodes$10(PyFrame var1, ThreadState var2) {
      var1.setline(558);
      PyString.fromInterned("Return list of 5-tuples describing how to turn a into b.\n\n        Each tuple is of the form (tag, i1, i2, j1, j2).  The first tuple\n        has i1 == j1 == 0, and remaining tuples have i1 == the i2 from the\n        tuple preceding it, and likewise for j1 == the previous j2.\n\n        The tags are strings, with these meanings:\n\n        'replace':  a[i1:i2] should be replaced by b[j1:j2]\n        'delete':   a[i1:i2] should be deleted.\n                    Note that j1==j2 in this case.\n        'insert':   b[j1:j2] should be inserted at a[i1:i1].\n                    Note that i1==i2 in this case.\n        'equal':    a[i1:i2] == b[j1:j2]\n\n        >>> a = \"qabxcd\"\n        >>> b = \"abycdf\"\n        >>> s = SequenceMatcher(None, a, b)\n        >>> for tag, i1, i2, j1, j2 in s.get_opcodes():\n        ...    print (\"%7s a[%d:%d] (%s) b[%d:%d] (%s)\" %\n        ...           (tag, i1, i2, a[i1:i2], j1, j2, b[j1:j2]))\n         delete a[0:1] (q) b[0:0] ()\n          equal a[1:3] (ab) b[0:2] (ab)\n        replace a[3:4] (x) b[2:3] (y)\n          equal a[4:6] (cd) b[3:5] (cd)\n         insert a[6:6] () b[5:6] (f)\n        ");
      var1.setline(560);
      PyObject var3 = var1.getlocal(0).__getattr__("opcodes");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(561);
         var3 = var1.getlocal(0).__getattr__("opcodes");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(562);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(1, var4);
         var1.setlocal(2, var4);
         var1.setline(563);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"opcodes", var9);
         var1.setlocal(3, var9);
         var1.setline(564);
         PyObject var10 = var1.getlocal(0).__getattr__("get_matching_blocks").__call__(var2).__iter__();

         while(true) {
            var1.setline(564);
            PyObject var5 = var10.__iternext__();
            if (var5 == null) {
               var1.setline(584);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 3);
            PyObject var7 = var6[0];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(5, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(570);
            PyString var11 = PyString.fromInterned("");
            var1.setlocal(7, var11);
            var6 = null;
            var1.setline(571);
            PyObject var12 = var1.getlocal(1);
            var10000 = var12._lt(var1.getlocal(4));
            var6 = null;
            if (var10000.__nonzero__()) {
               var12 = var1.getlocal(2);
               var10000 = var12._lt(var1.getlocal(5));
               var6 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(572);
               var11 = PyString.fromInterned("replace");
               var1.setlocal(7, var11);
               var6 = null;
            } else {
               var1.setline(573);
               var12 = var1.getlocal(1);
               var10000 = var12._lt(var1.getlocal(4));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(574);
                  var11 = PyString.fromInterned("delete");
                  var1.setlocal(7, var11);
                  var6 = null;
               } else {
                  var1.setline(575);
                  var12 = var1.getlocal(2);
                  var10000 = var12._lt(var1.getlocal(5));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(576);
                     var11 = PyString.fromInterned("insert");
                     var1.setlocal(7, var11);
                     var6 = null;
                  }
               }
            }

            var1.setline(577);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(578);
               var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(1), var1.getlocal(4), var1.getlocal(2), var1.getlocal(5)})));
            }

            var1.setline(579);
            PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(var1.getlocal(6)), var1.getlocal(5)._add(var1.getlocal(6))});
            PyObject[] var13 = Py.unpackSequence(var14, 2);
            PyObject var8 = var13[0];
            var1.setlocal(1, var8);
            var8 = null;
            var8 = var13[1];
            var1.setlocal(2, var8);
            var8 = null;
            var6 = null;
            var1.setline(582);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(583);
               var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("equal"), var1.getlocal(4), var1.getlocal(1), var1.getlocal(5), var1.getlocal(2)})));
            }
         }
      }
   }

   public PyObject get_grouped_opcodes$11(PyFrame var1, ThreadState var2) {
      label59: {
         Object[] var3;
         PyObject var4;
         Object[] var5;
         PyObject[] var6;
         PyObject var8;
         PyObject[] var12;
         Object var10000;
         PyObject var17;
         PyObject var18;
         PyTuple var10002;
         PyList var19;
         PyTuple var20;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(609);
               PyString.fromInterned(" Isolate change clusters by eliminating ranges with no changes.\n\n        Return a generator of groups with upto n lines of context.\n        Each group is in the same format as returned by get_opcodes().\n\n        >>> from pprint import pprint\n        >>> a = map(str, range(1,40))\n        >>> b = a[:]\n        >>> b[8:8] = ['i']     # Make an insertion\n        >>> b[20] += 'x'       # Make a replacement\n        >>> b[23:28] = []      # Make a deletion\n        >>> b[30] += 'y'       # Make another replacement\n        >>> pprint(list(SequenceMatcher(None,a,b).get_grouped_opcodes()))\n        [[('equal', 5, 8, 5, 8), ('insert', 8, 8, 8, 9), ('equal', 8, 11, 9, 12)],\n         [('equal', 16, 19, 17, 20),\n          ('replace', 19, 20, 20, 21),\n          ('equal', 20, 22, 21, 23),\n          ('delete', 22, 27, 23, 23),\n          ('equal', 27, 30, 23, 26)],\n         [('equal', 31, 34, 27, 30),\n          ('replace', 34, 35, 30, 31),\n          ('equal', 35, 38, 31, 34)]]\n        ");
               var1.setline(611);
               var8 = var1.getlocal(0).__getattr__("get_opcodes").__call__(var2);
               var1.setlocal(2, var8);
               var3 = null;
               var1.setline(612);
               PyObject[] var9;
               PyObject[] var10;
               PyList var11;
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(613);
                  var9 = new PyObject[1];
                  var10 = new PyObject[]{PyString.fromInterned("equal"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0), Py.newInteger(1)};
                  var20 = new PyTuple(var10);
                  Arrays.fill(var10, (Object)null);
                  var9[0] = var20;
                  var19 = new PyList(var9);
                  Arrays.fill(var9, (Object)null);
                  var11 = var19;
                  var1.setlocal(2, var11);
                  var3 = null;
               }

               var1.setline(615);
               var8 = var1.getlocal(2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
               var18 = var8._eq(PyString.fromInterned("equal"));
               var3 = null;
               PyTuple var16;
               if (var18.__nonzero__()) {
                  var1.setline(616);
                  var8 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var10 = Py.unpackSequence(var8, 5);
                  var17 = var10[0];
                  var1.setlocal(3, var17);
                  var5 = null;
                  var17 = var10[1];
                  var1.setlocal(4, var17);
                  var5 = null;
                  var17 = var10[2];
                  var1.setlocal(5, var17);
                  var5 = null;
                  var17 = var10[3];
                  var1.setlocal(6, var17);
                  var5 = null;
                  var17 = var10[4];
                  var1.setlocal(7, var17);
                  var5 = null;
                  var3 = null;
                  var1.setline(617);
                  var9 = new PyObject[]{var1.getlocal(3), var1.getglobal("max").__call__(var2, var1.getlocal(4), var1.getlocal(5)._sub(var1.getlocal(1))), var1.getlocal(5), var1.getglobal("max").__call__(var2, var1.getlocal(6), var1.getlocal(7)._sub(var1.getlocal(1))), var1.getlocal(7)};
                  var20 = new PyTuple(var9);
                  Arrays.fill(var9, (Object)null);
                  var16 = var20;
                  var1.getlocal(2).__setitem__((PyObject)Py.newInteger(0), var16);
                  var3 = null;
               }

               var1.setline(618);
               var8 = var1.getlocal(2).__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
               var18 = var8._eq(PyString.fromInterned("equal"));
               var3 = null;
               if (var18.__nonzero__()) {
                  var1.setline(619);
                  var8 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
                  var10 = Py.unpackSequence(var8, 5);
                  var17 = var10[0];
                  var1.setlocal(3, var17);
                  var5 = null;
                  var17 = var10[1];
                  var1.setlocal(4, var17);
                  var5 = null;
                  var17 = var10[2];
                  var1.setlocal(5, var17);
                  var5 = null;
                  var17 = var10[3];
                  var1.setlocal(6, var17);
                  var5 = null;
                  var17 = var10[4];
                  var1.setlocal(7, var17);
                  var5 = null;
                  var3 = null;
                  var1.setline(620);
                  var9 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getglobal("min").__call__(var2, var1.getlocal(5), var1.getlocal(4)._add(var1.getlocal(1))), var1.getlocal(6), var1.getglobal("min").__call__(var2, var1.getlocal(7), var1.getlocal(6)._add(var1.getlocal(1)))};
                  var20 = new PyTuple(var9);
                  Arrays.fill(var9, (Object)null);
                  var16 = var20;
                  var1.getlocal(2).__setitem__((PyObject)Py.newInteger(-1), var16);
                  var3 = null;
               }

               var1.setline(622);
               var8 = var1.getlocal(1)._add(var1.getlocal(1));
               var1.setlocal(8, var8);
               var3 = null;
               var1.setline(623);
               var9 = Py.EmptyObjects;
               var19 = new PyList(var9);
               Arrays.fill(var9, (Object)null);
               var11 = var19;
               var1.setlocal(9, var11);
               var3 = null;
               var1.setline(624);
               var8 = var1.getlocal(2).__iter__();
               break;
            case 1:
               var5 = var1.f_savedlocals;
               var8 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
               var1.setline(630);
               var12 = Py.EmptyObjects;
               var19 = new PyList(var12);
               Arrays.fill(var12, (Object)null);
               PyList var13 = var19;
               var1.setlocal(9, var13);
               var5 = null;
               var1.setline(631);
               var12 = new PyObject[]{var1.getglobal("max").__call__(var2, var1.getlocal(4), var1.getlocal(5)._sub(var1.getlocal(1))), var1.getglobal("max").__call__(var2, var1.getlocal(6), var1.getlocal(7)._sub(var1.getlocal(1)))};
               var20 = new PyTuple(var12);
               Arrays.fill(var12, (Object)null);
               PyTuple var15 = var20;
               var6 = Py.unpackSequence(var15, 2);
               PyObject var7 = var6[0];
               var1.setlocal(4, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(6, var7);
               var7 = null;
               var5 = null;
               var1.setline(632);
               var18 = var1.getlocal(9).__getattr__("append");
               var12 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
               var10002 = new PyTuple(var12);
               Arrays.fill(var12, (Object)null);
               var18.__call__((ThreadState)var2, (PyObject)var10002);
               break;
            case 2:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
               break label59;
         }

         while(true) {
            var1.setline(624);
            var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(633);
               var18 = var1.getlocal(9);
               if (var18.__nonzero__()) {
                  var8 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
                  var18 = var8._eq(Py.newInteger(1));
                  var3 = null;
                  if (var18.__nonzero__()) {
                     var8 = var1.getlocal(9).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
                     var18 = var8._eq(PyString.fromInterned("equal"));
                     var3 = null;
                  }

                  var18 = var18.__not__();
               }

               if (var18.__nonzero__()) {
                  var1.setline(634);
                  var1.setline(634);
                  var18 = var1.getlocal(9);
                  var1.f_lasti = 2;
                  var3 = new Object[8];
                  var1.f_savedlocals = var3;
                  return var18;
               }
               break;
            }

            var12 = Py.unpackSequence(var4, 5);
            PyObject var14 = var12[0];
            var1.setlocal(3, var14);
            var6 = null;
            var14 = var12[1];
            var1.setlocal(4, var14);
            var6 = null;
            var14 = var12[2];
            var1.setlocal(5, var14);
            var6 = null;
            var14 = var12[3];
            var1.setlocal(6, var14);
            var6 = null;
            var14 = var12[4];
            var1.setlocal(7, var14);
            var6 = null;
            var1.setline(627);
            var17 = var1.getlocal(3);
            var18 = var17._eq(PyString.fromInterned("equal"));
            var5 = null;
            if (var18.__nonzero__()) {
               var17 = var1.getlocal(5)._sub(var1.getlocal(4));
               var18 = var17._gt(var1.getlocal(8));
               var5 = null;
            }

            if (var18.__nonzero__()) {
               var1.setline(628);
               var18 = var1.getlocal(9).__getattr__("append");
               var12 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getglobal("min").__call__(var2, var1.getlocal(5), var1.getlocal(4)._add(var1.getlocal(1))), var1.getlocal(6), var1.getglobal("min").__call__(var2, var1.getlocal(7), var1.getlocal(6)._add(var1.getlocal(1)))};
               var10002 = new PyTuple(var12);
               Arrays.fill(var12, (Object)null);
               var18.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setline(629);
               var1.setline(629);
               var18 = var1.getlocal(9);
               var1.f_lasti = 1;
               var5 = new Object[7];
               var5[3] = var8;
               var5[4] = var4;
               var1.f_savedlocals = var5;
               return var18;
            }

            var1.setline(632);
            var18 = var1.getlocal(9).__getattr__("append");
            var12 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
            var10002 = new PyTuple(var12);
            Arrays.fill(var12, (Object)null);
            var18.__call__((ThreadState)var2, (PyObject)var10002);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ratio$12(PyFrame var1, ThreadState var2) {
      var1.setline(656);
      PyString.fromInterned("Return a measure of the sequences' similarity (float in [0,1]).\n\n        Where T is the total number of elements in both sequences, and\n        M is the number of matches, this is 2.0*M / T.\n        Note that this is 1 if the sequences are identical, and 0 if\n        they have nothing in common.\n\n        .ratio() is expensive to compute if you haven't already computed\n        .get_matching_blocks() or .get_opcodes(), in which case you may\n        want to try .quick_ratio() or .real_quick_ratio() first to get an\n        upper bound.\n\n        >>> s = SequenceMatcher(None, \"abcd\", \"bcde\")\n        >>> s.ratio()\n        0.75\n        >>> s.quick_ratio()\n        0.75\n        >>> s.real_quick_ratio()\n        1.0\n        ");
      var1.setline(658);
      PyObject var10000 = var1.getglobal("reduce");
      var1.setline(658);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, new PyFunction(var1.f_globals, var3, f$13), (PyObject)var1.getlocal(0).__getattr__("get_matching_blocks").__call__(var2), (PyObject)Py.newInteger(0));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(660);
      var4 = var1.getglobal("_calculate_ratio").__call__(var2, var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("a"))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("b"))));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$13(PyFrame var1, ThreadState var2) {
      var1.setline(658);
      PyObject var3 = var1.getlocal(0)._add(var1.getlocal(1).__getitem__(Py.newInteger(-1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject quick_ratio$14(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      PyString.fromInterned("Return an upper bound on ratio() relatively quickly.\n\n        This isn't defined beyond that it is an upper bound on .ratio(), and\n        is faster to compute.\n        ");
      var1.setline(672);
      PyObject var3 = var1.getlocal(0).__getattr__("fullbcount");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      PyObject var5;
      PyDictionary var6;
      if (var10000.__nonzero__()) {
         var1.setline(673);
         var6 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"fullbcount", var6);
         var1.setlocal(1, var6);
         var1.setline(674);
         var3 = var1.getlocal(0).__getattr__("b").__iter__();

         while(true) {
            var1.setline(674);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(675);
            var5 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
            var1.getlocal(1).__setitem__(var1.getlocal(2), var5);
            var5 = null;
         }
      }

      var1.setline(676);
      var3 = var1.getlocal(0).__getattr__("fullbcount");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(679);
      var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(680);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("__contains__"), Py.newInteger(0)});
      PyObject[] var7 = Py.unpackSequence(var8, 2);
      var5 = var7[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(681);
      var3 = var1.getlocal(0).__getattr__("a").__iter__();

      while(true) {
         var1.setline(681);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(689);
            var3 = var1.getglobal("_calculate_ratio").__call__(var2, var1.getlocal(5), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("a"))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("b"))));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(682);
         if (var1.getlocal(4).__call__(var2, var1.getlocal(2)).__nonzero__()) {
            var1.setline(683);
            var5 = var1.getlocal(3).__getitem__(var1.getlocal(2));
            var1.setlocal(6, var5);
            var5 = null;
         } else {
            var1.setline(685);
            var5 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(686);
         var5 = var1.getlocal(6)._sub(Py.newInteger(1));
         var1.getlocal(3).__setitem__(var1.getlocal(2), var5);
         var5 = null;
         var1.setline(687);
         var5 = var1.getlocal(6);
         var10000 = var5._gt(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(688);
            var5 = var1.getlocal(5)._add(Py.newInteger(1));
            var1.setlocal(5, var5);
            var5 = null;
         }
      }
   }

   public PyObject real_quick_ratio$15(PyFrame var1, ThreadState var2) {
      var1.setline(696);
      PyString.fromInterned("Return an upper bound on ratio() very quickly.\n\n        This isn't defined beyond that it is an upper bound on .ratio(), and\n        is faster to compute than either .ratio() or .quick_ratio().\n        ");
      var1.setline(698);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("a")), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("b"))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(701);
      PyObject var6 = var1.getglobal("_calculate_ratio").__call__(var2, var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(2)), var1.getlocal(1)._add(var1.getlocal(2)));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject get_close_matches$16(PyFrame var1, ThreadState var2) {
      var1.setline(730);
      PyString.fromInterned("Use SequenceMatcher to return list of the best \"good enough\" matches.\n\n    word is a sequence for which close matches are desired (typically a\n    string).\n\n    possibilities is a list of sequences against which to match word\n    (typically a list of strings).\n\n    Optional arg n (default 3) is the maximum number of close matches to\n    return.  n must be > 0.\n\n    Optional arg cutoff (default 0.6) is a float in [0, 1].  Possibilities\n    that don't score at least that similar to word are ignored.\n\n    The best (no more than n) matches among the possibilities are returned\n    in a list, sorted by similarity score, most similar first.\n\n    >>> get_close_matches(\"appel\", [\"ape\", \"apple\", \"peach\", \"puppy\"])\n    ['apple', 'ape']\n    >>> import keyword as _keyword\n    >>> get_close_matches(\"wheel\", _keyword.kwlist)\n    ['while']\n    >>> get_close_matches(\"apple\", _keyword.kwlist)\n    []\n    >>> get_close_matches(\"accept\", _keyword.kwlist)\n    ['except']\n    ");
      var1.setline(732);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(733);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("n must be > 0: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)}))));
      } else {
         var1.setline(734);
         PyFloat var7 = Py.newFloat(0.0);
         PyObject var10001 = var1.getlocal(3);
         PyFloat var10 = var7;
         var3 = var10001;
         PyObject var4;
         if ((var4 = var10._le(var10001)).__nonzero__()) {
            var4 = var3._le(Py.newFloat(1.0));
         }

         var3 = null;
         if (var4.__not__().__nonzero__()) {
            var1.setline(735);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("cutoff must be in [0.0, 1.0]: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)}))));
         } else {
            var1.setline(736);
            PyList var8 = new PyList(Py.EmptyObjects);
            var1.setlocal(4, var8);
            var3 = null;
            var1.setline(737);
            var3 = var1.getglobal("SequenceMatcher").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(738);
            var1.getlocal(5).__getattr__("set_seq2").__call__(var2, var1.getlocal(0));
            var1.setline(739);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(739);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(747);
                  var3 = var1.getglobal("heapq").__getattr__("nlargest").__call__(var2, var1.getlocal(2), var1.getlocal(4));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(749);
                  PyList var11 = new PyList();
                  var3 = var11.__getattr__("append");
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(749);
                  var3 = var1.getlocal(4).__iter__();

                  while(true) {
                     var1.setline(749);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(749);
                        var1.dellocal(7);
                        var8 = var11;
                        var1.f_lasti = -1;
                        return var8;
                     }

                     PyObject[] var9 = Py.unpackSequence(var4, 2);
                     PyObject var6 = var9[0];
                     var1.setlocal(8, var6);
                     var6 = null;
                     var6 = var9[1];
                     var1.setlocal(6, var6);
                     var6 = null;
                     var1.setline(749);
                     var1.getlocal(7).__call__(var2, var1.getlocal(6));
                  }
               }

               var1.setlocal(6, var4);
               var1.setline(740);
               var1.getlocal(5).__getattr__("set_seq1").__call__(var2, var1.getlocal(6));
               var1.setline(741);
               PyObject var5 = var1.getlocal(5).__getattr__("real_quick_ratio").__call__(var2);
               var10000 = var5._ge(var1.getlocal(3));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(5).__getattr__("quick_ratio").__call__(var2);
                  var10000 = var5._ge(var1.getlocal(3));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var5 = var1.getlocal(5).__getattr__("ratio").__call__(var2);
                     var10000 = var5._ge(var1.getlocal(3));
                     var5 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(744);
                  var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("ratio").__call__(var2), var1.getlocal(6)})));
               }
            }
         }
      }
   }

   public PyObject _count_leading$17(PyFrame var1, ThreadState var2) {
      var1.setline(759);
      PyString.fromInterned("\n    Return number of `ch` characters at the start of `line`.\n\n    Example:\n\n    >>> _count_leading('   abc', ' ')\n    3\n    ");
      var1.setline(761);
      PyTuple var3 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(0))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(762);
         PyObject var6 = var1.getlocal(2);
         PyObject var10000 = var6._lt(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var6 = var1.getlocal(0).__getitem__(var1.getlocal(2));
            var10000 = var6._eq(var1.getlocal(1));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(764);
            var6 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(763);
         var6 = var1.getlocal(2);
         var6 = var6._iadd(Py.newInteger(1));
         var1.setlocal(2, var6);
      }
   }

   public PyObject Differ$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Differ is a class for comparing sequences of lines of text, and\n    producing human-readable differences or deltas.  Differ uses\n    SequenceMatcher both to compare sequences of lines, and to compare\n    sequences of characters within similar (near-matching) lines.\n\n    Each line of a Differ delta begins with a two-letter code:\n\n        '- '    line unique to sequence 1\n        '+ '    line unique to sequence 2\n        '  '    line common to both sequences\n        '? '    line not present in either input sequence\n\n    Lines beginning with '? ' attempt to guide the eye to intraline\n    differences, and were not present in either input sequence.  These lines\n    can be confusing if the sequences contain tab characters.\n\n    Note that Differ makes no claim to produce a *minimal* diff.  To the\n    contrary, minimal diffs are often counter-intuitive, because they synch\n    up anywhere possible, sometimes accidental matches 100 pages apart.\n    Restricting synch points to contiguous matches preserves some notion of\n    locality, at the occasional cost of producing a longer diff.\n\n    Example: Comparing two texts.\n\n    First we set up the texts, sequences of individual single-line strings\n    ending with newlines (such sequences can also be obtained from the\n    `readlines()` method of file-like objects):\n\n    >>> text1 = '''  1. Beautiful is better than ugly.\n    ...   2. Explicit is better than implicit.\n    ...   3. Simple is better than complex.\n    ...   4. Complex is better than complicated.\n    ... '''.splitlines(1)\n    >>> len(text1)\n    4\n    >>> text1[0][-1]\n    '\\n'\n    >>> text2 = '''  1. Beautiful is better than ugly.\n    ...   3.   Simple is better than complex.\n    ...   4. Complicated is better than complex.\n    ...   5. Flat is better than nested.\n    ... '''.splitlines(1)\n\n    Next we instantiate a Differ object:\n\n    >>> d = Differ()\n\n    Note that when instantiating a Differ object we may pass functions to\n    filter out line and character 'junk'.  See Differ.__init__ for details.\n\n    Finally, we compare the two:\n\n    >>> result = list(d.compare(text1, text2))\n\n    'result' is a list of strings, so let's pretty-print it:\n\n    >>> from pprint import pprint as _pprint\n    >>> _pprint(result)\n    ['    1. Beautiful is better than ugly.\\n',\n     '-   2. Explicit is better than implicit.\\n',\n     '-   3. Simple is better than complex.\\n',\n     '+   3.   Simple is better than complex.\\n',\n     '?     ++\\n',\n     '-   4. Complex is better than complicated.\\n',\n     '?            ^                     ---- ^\\n',\n     '+   4. Complicated is better than complex.\\n',\n     '?           ++++ ^                      ^\\n',\n     '+   5. Flat is better than nested.\\n']\n\n    As a single multi-line string it looks like this:\n\n    >>> print ''.join(result),\n        1. Beautiful is better than ugly.\n    -   2. Explicit is better than implicit.\n    -   3. Simple is better than complex.\n    +   3.   Simple is better than complex.\n    ?     ++\n    -   4. Complex is better than complicated.\n    ?            ^                     ---- ^\n    +   4. Complicated is better than complex.\n    ?           ++++ ^                      ^\n    +   5. Flat is better than nested.\n\n    Methods:\n\n    __init__(linejunk=None, charjunk=None)\n        Construct a text differencer, with optional filters.\n\n    compare(a, b)\n        Compare two sequences of lines; generate the resulting delta.\n    "));
      var1.setline(858);
      PyString.fromInterned("\n    Differ is a class for comparing sequences of lines of text, and\n    producing human-readable differences or deltas.  Differ uses\n    SequenceMatcher both to compare sequences of lines, and to compare\n    sequences of characters within similar (near-matching) lines.\n\n    Each line of a Differ delta begins with a two-letter code:\n\n        '- '    line unique to sequence 1\n        '+ '    line unique to sequence 2\n        '  '    line common to both sequences\n        '? '    line not present in either input sequence\n\n    Lines beginning with '? ' attempt to guide the eye to intraline\n    differences, and were not present in either input sequence.  These lines\n    can be confusing if the sequences contain tab characters.\n\n    Note that Differ makes no claim to produce a *minimal* diff.  To the\n    contrary, minimal diffs are often counter-intuitive, because they synch\n    up anywhere possible, sometimes accidental matches 100 pages apart.\n    Restricting synch points to contiguous matches preserves some notion of\n    locality, at the occasional cost of producing a longer diff.\n\n    Example: Comparing two texts.\n\n    First we set up the texts, sequences of individual single-line strings\n    ending with newlines (such sequences can also be obtained from the\n    `readlines()` method of file-like objects):\n\n    >>> text1 = '''  1. Beautiful is better than ugly.\n    ...   2. Explicit is better than implicit.\n    ...   3. Simple is better than complex.\n    ...   4. Complex is better than complicated.\n    ... '''.splitlines(1)\n    >>> len(text1)\n    4\n    >>> text1[0][-1]\n    '\\n'\n    >>> text2 = '''  1. Beautiful is better than ugly.\n    ...   3.   Simple is better than complex.\n    ...   4. Complicated is better than complex.\n    ...   5. Flat is better than nested.\n    ... '''.splitlines(1)\n\n    Next we instantiate a Differ object:\n\n    >>> d = Differ()\n\n    Note that when instantiating a Differ object we may pass functions to\n    filter out line and character 'junk'.  See Differ.__init__ for details.\n\n    Finally, we compare the two:\n\n    >>> result = list(d.compare(text1, text2))\n\n    'result' is a list of strings, so let's pretty-print it:\n\n    >>> from pprint import pprint as _pprint\n    >>> _pprint(result)\n    ['    1. Beautiful is better than ugly.\\n',\n     '-   2. Explicit is better than implicit.\\n',\n     '-   3. Simple is better than complex.\\n',\n     '+   3.   Simple is better than complex.\\n',\n     '?     ++\\n',\n     '-   4. Complex is better than complicated.\\n',\n     '?            ^                     ---- ^\\n',\n     '+   4. Complicated is better than complex.\\n',\n     '?           ++++ ^                      ^\\n',\n     '+   5. Flat is better than nested.\\n']\n\n    As a single multi-line string it looks like this:\n\n    >>> print ''.join(result),\n        1. Beautiful is better than ugly.\n    -   2. Explicit is better than implicit.\n    -   3. Simple is better than complex.\n    +   3.   Simple is better than complex.\n    ?     ++\n    -   4. Complex is better than complicated.\n    ?            ^                     ---- ^\n    +   4. Complicated is better than complex.\n    ?           ++++ ^                      ^\n    +   5. Flat is better than nested.\n\n    Methods:\n\n    __init__(linejunk=None, charjunk=None)\n        Construct a text differencer, with optional filters.\n\n    compare(a, b)\n        Compare two sequences of lines; generate the resulting delta.\n    ");
      var1.setline(860);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, PyString.fromInterned("\n        Construct a text differencer, with optional filters.\n\n        The two optional keyword parameters are for filter functions:\n\n        - `linejunk`: A function that should accept a single string argument,\n          and return true iff the string is junk. The module-level function\n          `IS_LINE_JUNK` may be used to filter out lines without visible\n          characters, except for at most one splat ('#').  It is recommended\n          to leave linejunk None; as of Python 2.3, the underlying\n          SequenceMatcher class has grown an adaptive notion of \"noise\" lines\n          that's better than any static definition the author has ever been\n          able to craft.\n\n        - `charjunk`: A function that should accept a string of length 1. The\n          module-level function `IS_CHARACTER_JUNK` may be used to filter out\n          whitespace characters (a blank or tab; **note**: bad idea to include\n          newline in this!).  Use of IS_CHARACTER_JUNK is recommended.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(884);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compare$20, PyString.fromInterned("\n        Compare two sequences of lines; generate the resulting delta.\n\n        Each sequence must contain individual single-line strings ending with\n        newlines. Such sequences can be obtained from the `readlines()` method\n        of file-like objects.  The delta generated also consists of newline-\n        terminated strings, ready to be printed as-is via the writeline()\n        method of a file-like object.\n\n        Example:\n\n        >>> print ''.join(Differ().compare('one\\ntwo\\nthree\\n'.splitlines(1),\n        ...                                'ore\\ntree\\nemu\\n'.splitlines(1))),\n        - one\n        ?  ^\n        + ore\n        ?  ^\n        - two\n        - three\n        ?  -\n        + tree\n        + emu\n        "));
      var1.setlocal("compare", var4);
      var3 = null;
      var1.setline(925);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _dump$21, PyString.fromInterned("Generate comparison results for a same-tagged range."));
      var1.setlocal("_dump", var4);
      var3 = null;
      var1.setline(930);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _plain_replace$22, (PyObject)null);
      var1.setlocal("_plain_replace", var4);
      var3 = null;
      var1.setline(945);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fancy_replace$23, PyString.fromInterned("\n        When replacing one block of lines with another, search the blocks\n        for *similar* lines; the best-matching pair (if any) is used as a\n        synch point, and intraline difference marking is done on the\n        similar pair. Lots of work, but often worth it.\n\n        Example:\n\n        >>> d = Differ()\n        >>> results = d._fancy_replace(['abcDefghiJkl\\n'], 0, 1,\n        ...                            ['abcdefGhijkl\\n'], 0, 1)\n        >>> print ''.join(results),\n        - abcDefghiJkl\n        ?    ^  ^  ^\n        + abcdefGhijkl\n        ?    ^  ^  ^\n        "));
      var1.setlocal("_fancy_replace", var4);
      var3 = null;
      var1.setline(1043);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _fancy_helper$24, (PyObject)null);
      var1.setlocal("_fancy_helper", var4);
      var3 = null;
      var1.setline(1056);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _qformat$25, PyString.fromInterned("\n        Format \"?\" output and deal with leading tabs.\n\n        Example:\n\n        >>> d = Differ()\n        >>> results = d._qformat('\\tabcDefghiJkl\\n', '\\tabcdefGhijkl\\n',\n        ...                      '  ^ ^  ^      ', '  ^ ^  ^      ')\n        >>> for line in results: print repr(line)\n        ...\n        '- \\tabcDefghiJkl\\n'\n        '? \\t ^ ^  ^\\n'\n        '+ \\tabcdefGhijkl\\n'\n        '? \\t ^ ^  ^\\n'\n        "));
      var1.setlocal("_qformat", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(879);
      PyString.fromInterned("\n        Construct a text differencer, with optional filters.\n\n        The two optional keyword parameters are for filter functions:\n\n        - `linejunk`: A function that should accept a single string argument,\n          and return true iff the string is junk. The module-level function\n          `IS_LINE_JUNK` may be used to filter out lines without visible\n          characters, except for at most one splat ('#').  It is recommended\n          to leave linejunk None; as of Python 2.3, the underlying\n          SequenceMatcher class has grown an adaptive notion of \"noise\" lines\n          that's better than any static definition the author has ever been\n          able to craft.\n\n        - `charjunk`: A function that should accept a string of length 1. The\n          module-level function `IS_CHARACTER_JUNK` may be used to filter out\n          whitespace characters (a blank or tab; **note**: bad idea to include\n          newline in this!).  Use of IS_CHARACTER_JUNK is recommended.\n        ");
      var1.setline(881);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("linejunk", var3);
      var3 = null;
      var1.setline(882);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("charjunk", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compare$20(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(907);
            PyString.fromInterned("\n        Compare two sequences of lines; generate the resulting delta.\n\n        Each sequence must contain individual single-line strings ending with\n        newlines. Such sequences can be obtained from the `readlines()` method\n        of file-like objects.  The delta generated also consists of newline-\n        terminated strings, ready to be printed as-is via the writeline()\n        method of a file-like object.\n\n        Example:\n\n        >>> print ''.join(Differ().compare('one\\ntwo\\nthree\\n'.splitlines(1),\n        ...                                'ore\\ntree\\nemu\\n'.splitlines(1))),\n        - one\n        ?  ^\n        + ore\n        ?  ^\n        - two\n        - three\n        ?  -\n        + tree\n        + emu\n        ");
            var1.setline(909);
            var3 = var1.getglobal("SequenceMatcher").__call__(var2, var1.getlocal(0).__getattr__("linejunk"), var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(910);
            var3 = var1.getlocal(3).__getattr__("get_opcodes").__call__(var2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
            var1.setline(922);
            var6 = var5.__iternext__();
            if (var6 != null) {
               var1.setlocal(10, var6);
               var1.setline(923);
               var1.setline(923);
               var9 = var1.getlocal(10);
               var1.f_lasti = 1;
               var7 = new Object[]{null, null, null, var3, var4, var5, var6};
               var1.f_savedlocals = var7;
               return var9;
            }
      }

      do {
         var1.setline(910);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var8 = Py.unpackSequence(var4, 5);
         var6 = var8[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var8[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var8[2];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var8[3];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var8[4];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(911);
         var5 = var1.getlocal(4);
         var9 = var5._eq(PyString.fromInterned("replace"));
         var5 = null;
         if (var9.__nonzero__()) {
            var1.setline(912);
            var9 = var1.getlocal(0).__getattr__("_fancy_replace");
            var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(5), var1.getlocal(6), var1.getlocal(2), var1.getlocal(7), var1.getlocal(8)};
            var5 = var9.__call__(var2, var8);
            var1.setlocal(9, var5);
            var5 = null;
         } else {
            var1.setline(913);
            var5 = var1.getlocal(4);
            var9 = var5._eq(PyString.fromInterned("delete"));
            var5 = null;
            if (var9.__nonzero__()) {
               var1.setline(914);
               var5 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("-"), var1.getlocal(1), var1.getlocal(5), var1.getlocal(6));
               var1.setlocal(9, var5);
               var5 = null;
            } else {
               var1.setline(915);
               var5 = var1.getlocal(4);
               var9 = var5._eq(PyString.fromInterned("insert"));
               var5 = null;
               if (var9.__nonzero__()) {
                  var1.setline(916);
                  var5 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("+"), var1.getlocal(2), var1.getlocal(7), var1.getlocal(8));
                  var1.setlocal(9, var5);
                  var5 = null;
               } else {
                  var1.setline(917);
                  var5 = var1.getlocal(4);
                  var9 = var5._eq(PyString.fromInterned("equal"));
                  var5 = null;
                  if (!var9.__nonzero__()) {
                     var1.setline(920);
                     var9 = var1.getglobal("ValueError");
                     PyString var10001 = PyString.fromInterned("unknown tag %r");
                     var8 = new PyObject[]{var1.getlocal(4)};
                     PyTuple var10002 = new PyTuple(var8);
                     Arrays.fill(var8, (Object)null);
                     throw Py.makeException(var9, var10001._mod(var10002));
                  }

                  var1.setline(918);
                  var5 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned(" "), var1.getlocal(1), var1.getlocal(5), var1.getlocal(6));
                  var1.setlocal(9, var5);
                  var5 = null;
               }
            }
         }

         var1.setline(922);
         var5 = var1.getlocal(9).__iter__();
         var1.setline(922);
         var6 = var5.__iternext__();
      } while(var6 == null);

      var1.setlocal(10, var6);
      var1.setline(923);
      var1.setline(923);
      var9 = var1.getlocal(10);
      var1.f_lasti = 1;
      var7 = new Object[]{null, null, null, var3, var4, var5, var6};
      var1.f_savedlocals = var7;
      return var9;
   }

   public PyObject _dump$21(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(926);
            PyString.fromInterned("Generate comparison results for a same-tagged range.");
            var1.setline(927);
            var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(3), var1.getlocal(4)).__iter__();
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

      var1.setline(927);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(5, var4);
         var1.setline(928);
         var1.setline(928);
         PyString var8 = PyString.fromInterned("%s %s");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2).__getitem__(var1.getlocal(5))};
         PyTuple var10001 = new PyTuple(var6);
         Arrays.fill(var6, (Object)null);
         var7 = var8._mod(var10001);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject _plain_replace$22(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(931);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(2);
               var9 = var3._lt(var1.getlocal(3));
               var3 = null;
               if (var9.__nonzero__()) {
                  var3 = var1.getlocal(5);
                  var9 = var3._lt(var1.getlocal(6));
                  var3 = null;
               }

               if (!var9.__nonzero__()) {
                  var9 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var9);
               }
            }

            var1.setline(934);
            var3 = var1.getlocal(6)._sub(var1.getlocal(5));
            var9 = var3._lt(var1.getlocal(3)._sub(var1.getlocal(2)));
            var3 = null;
            if (var9.__nonzero__()) {
               var1.setline(935);
               var3 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("+"), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(936);
               var3 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("-"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
               var1.setlocal(8, var3);
               var3 = null;
            } else {
               var1.setline(938);
               var3 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("-"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(939);
               var3 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("+"), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
               var1.setlocal(8, var3);
               var3 = null;
            }

            var1.setline(941);
            PyObject[] var8 = new PyObject[]{var1.getlocal(7), var1.getlocal(8)};
            PyTuple var10 = new PyTuple(var8);
            Arrays.fill(var8, (Object)null);
            var3 = var10.__iter__();
            var1.setline(941);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(9, var4);
            var1.setline(942);
            var5 = var1.getlocal(9).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(942);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(10, var6);
            var1.setline(943);
            var1.setline(943);
            var9 = var1.getlocal(10);
            var1.f_lasti = 1;
            var7 = new Object[]{null, null, null, var3, var4, var5, var6};
            var1.f_savedlocals = var7;
            return var9;
         }

         var1.setline(941);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(9, var4);
         var1.setline(942);
         var5 = var1.getlocal(9).__iter__();
      }
   }

   public PyObject _fancy_replace$23(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var21;
      label152: {
         label153: {
            PyObject[] var11;
            label132: {
               label144: {
                  PyObject var6;
                  PyObject var7;
                  Object[] var10;
                  PyTuple var12;
                  PyObject[] var13;
                  PyObject var14;
                  PyTuple var22;
                  Object var10000;
                  label124:
                  switch (var1.f_lasti) {
                     case 0:
                     default:
                        var1.setline(962);
                        PyString.fromInterned("\n        When replacing one block of lines with another, search the blocks\n        for *similar* lines; the best-matching pair (if any) is used as a\n        synch point, and intraline difference marking is done on the\n        similar pair. Lots of work, but often worth it.\n\n        Example:\n\n        >>> d = Differ()\n        >>> results = d._fancy_replace(['abcDefghiJkl\\n'], 0, 1,\n        ...                            ['abcdefGhijkl\\n'], 0, 1)\n        >>> print ''.join(results),\n        - abcDefghiJkl\n        ?    ^  ^  ^\n        + abcdefGhijkl\n        ?    ^  ^  ^\n        ");
                        var1.setline(966);
                        var11 = new PyObject[]{Py.newFloat(0.74), Py.newFloat(0.75)};
                        var22 = new PyTuple(var11);
                        Arrays.fill(var11, (Object)null);
                        var12 = var22;
                        var13 = Py.unpackSequence(var12, 2);
                        var14 = var13[0];
                        var1.setlocal(7, var14);
                        var5 = null;
                        var14 = var13[1];
                        var1.setlocal(8, var14);
                        var5 = null;
                        var3 = null;
                        var1.setline(967);
                        var3 = var1.getglobal("SequenceMatcher").__call__(var2, var1.getlocal(0).__getattr__("charjunk"));
                        var1.setlocal(9, var3);
                        var3 = null;
                        var1.setline(968);
                        var11 = new PyObject[]{var1.getglobal("None"), var1.getglobal("None")};
                        var22 = new PyTuple(var11);
                        Arrays.fill(var11, (Object)null);
                        var12 = var22;
                        var13 = Py.unpackSequence(var12, 2);
                        var14 = var13[0];
                        var1.setlocal(10, var14);
                        var5 = null;
                        var14 = var13[1];
                        var1.setlocal(11, var14);
                        var5 = null;
                        var3 = null;
                        var1.setline(973);
                        var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(5), var1.getlocal(6)).__iter__();

                        while(true) {
                           var1.setline(973);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(993);
                              var3 = var1.getlocal(7);
                              var21 = var3._lt(var1.getlocal(8));
                              var3 = null;
                              if (var21.__nonzero__()) {
                                 var1.setline(995);
                                 var3 = var1.getlocal(10);
                                 var21 = var3._is(var1.getglobal("None"));
                                 var3 = null;
                                 if (var21.__nonzero__()) {
                                    var1.setline(997);
                                    var21 = var1.getlocal(0).__getattr__("_plain_replace");
                                    var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
                                    var3 = var21.__call__(var2, var11).__iter__();
                                    break label152;
                                 }

                                 var1.setline(1001);
                                 var11 = new PyObject[]{var1.getlocal(10), var1.getlocal(11), Py.newFloat(1.0)};
                                 var22 = new PyTuple(var11);
                                 Arrays.fill(var11, (Object)null);
                                 var12 = var22;
                                 var13 = Py.unpackSequence(var12, 3);
                                 var14 = var13[0];
                                 var1.setlocal(16, var14);
                                 var5 = null;
                                 var14 = var13[1];
                                 var1.setlocal(17, var14);
                                 var5 = null;
                                 var14 = var13[2];
                                 var1.setlocal(7, var14);
                                 var5 = null;
                                 var3 = null;
                              } else {
                                 var1.setline(1004);
                                 var3 = var1.getglobal("None");
                                 var1.setlocal(10, var3);
                                 var3 = null;
                              }

                              var1.setline(1010);
                              var21 = var1.getlocal(0).__getattr__("_fancy_helper");
                              var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(16), var1.getlocal(4), var1.getlocal(5), var1.getlocal(17)};
                              var3 = var21.__call__(var2, var11).__iter__();
                              break label124;
                           }

                           var1.setlocal(12, var4);
                           var1.setline(974);
                           var14 = var1.getlocal(4).__getitem__(var1.getlocal(12));
                           var1.setlocal(13, var14);
                           var5 = null;
                           var1.setline(975);
                           var1.getlocal(9).__getattr__("set_seq2").__call__(var2, var1.getlocal(13));
                           var1.setline(976);
                           var14 = var1.getglobal("xrange").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__iter__();

                           while(true) {
                              var1.setline(976);
                              var6 = var14.__iternext__();
                              if (var6 == null) {
                                 break;
                              }

                              var1.setlocal(14, var6);
                              var1.setline(977);
                              var7 = var1.getlocal(1).__getitem__(var1.getlocal(14));
                              var1.setlocal(15, var7);
                              var7 = null;
                              var1.setline(978);
                              var7 = var1.getlocal(15);
                              var21 = var7._eq(var1.getlocal(13));
                              var7 = null;
                              PyObject[] var8;
                              PyObject var9;
                              PyObject[] var15;
                              PyTuple var16;
                              if (var21.__nonzero__()) {
                                 var1.setline(979);
                                 var7 = var1.getlocal(10);
                                 var21 = var7._is(var1.getglobal("None"));
                                 var7 = null;
                                 if (var21.__nonzero__()) {
                                    var1.setline(980);
                                    var15 = new PyObject[]{var1.getlocal(14), var1.getlocal(12)};
                                    var22 = new PyTuple(var15);
                                    Arrays.fill(var15, (Object)null);
                                    var16 = var22;
                                    var8 = Py.unpackSequence(var16, 2);
                                    var9 = var8[0];
                                    var1.setlocal(10, var9);
                                    var9 = null;
                                    var9 = var8[1];
                                    var1.setlocal(11, var9);
                                    var9 = null;
                                    var7 = null;
                                 }
                              } else {
                                 var1.setline(982);
                                 var1.getlocal(9).__getattr__("set_seq1").__call__(var2, var1.getlocal(15));
                                 var1.setline(989);
                                 var7 = var1.getlocal(9).__getattr__("real_quick_ratio").__call__(var2);
                                 var21 = var7._gt(var1.getlocal(7));
                                 var7 = null;
                                 if (var21.__nonzero__()) {
                                    var7 = var1.getlocal(9).__getattr__("quick_ratio").__call__(var2);
                                    var21 = var7._gt(var1.getlocal(7));
                                    var7 = null;
                                    if (var21.__nonzero__()) {
                                       var7 = var1.getlocal(9).__getattr__("ratio").__call__(var2);
                                       var21 = var7._gt(var1.getlocal(7));
                                       var7 = null;
                                    }
                                 }

                                 if (var21.__nonzero__()) {
                                    var1.setline(992);
                                    var15 = new PyObject[]{var1.getlocal(9).__getattr__("ratio").__call__(var2), var1.getlocal(14), var1.getlocal(12)};
                                    var22 = new PyTuple(var15);
                                    Arrays.fill(var15, (Object)null);
                                    var16 = var22;
                                    var8 = Py.unpackSequence(var16, 3);
                                    var9 = var8[0];
                                    var1.setlocal(7, var9);
                                    var9 = null;
                                    var9 = var8[1];
                                    var1.setlocal(16, var9);
                                    var9 = null;
                                    var9 = var8[2];
                                    var1.setlocal(17, var9);
                                    var9 = null;
                                    var7 = null;
                                 }
                              }
                           }
                        }
                     case 1:
                        var5 = var1.f_savedlocals;
                        var3 = (PyObject)var5[3];
                        var4 = (PyObject)var5[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var21 = (PyObject)var10000;
                        break label152;
                     case 2:
                        var5 = var1.f_savedlocals;
                        var3 = (PyObject)var5[3];
                        var4 = (PyObject)var5[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var21 = (PyObject)var10000;
                        break;
                     case 3:
                        var5 = var1.f_savedlocals;
                        var3 = (PyObject)var5[3];
                        var4 = (PyObject)var5[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var21 = (PyObject)var10000;
                        break label144;
                     case 4:
                        var10 = var1.f_savedlocals;
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var21 = (PyObject)var10000;
                        break label132;
                     case 5:
                        var5 = var1.f_savedlocals;
                        var3 = (PyObject)var5[3];
                        var4 = (PyObject)var5[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var21 = (PyObject)var10000;
                        break label153;
                  }

                  var1.setline(1010);
                  var4 = var3.__iternext__();
                  if (var4 != null) {
                     var1.setlocal(18, var4);
                     var1.setline(1011);
                     var1.setline(1011);
                     var21 = var1.getlocal(18);
                     var1.f_lasti = 2;
                     var5 = new Object[10];
                     var5[3] = var3;
                     var5[4] = var4;
                     var1.f_savedlocals = var5;
                     return var21;
                  }

                  var1.setline(1014);
                  var11 = new PyObject[]{var1.getlocal(1).__getitem__(var1.getlocal(16)), var1.getlocal(4).__getitem__(var1.getlocal(17))};
                  var22 = new PyTuple(var11);
                  Arrays.fill(var11, (Object)null);
                  var12 = var22;
                  var13 = Py.unpackSequence(var12, 2);
                  var14 = var13[0];
                  var1.setlocal(19, var14);
                  var5 = null;
                  var14 = var13[1];
                  var1.setlocal(20, var14);
                  var5 = null;
                  var3 = null;
                  var1.setline(1015);
                  var3 = var1.getlocal(10);
                  var21 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (!var21.__nonzero__()) {
                     var1.setline(1037);
                     var1.setline(1037);
                     var21 = PyString.fromInterned("  ")._add(var1.getlocal(19));
                     var1.f_lasti = 4;
                     var10 = new Object[10];
                     var1.f_savedlocals = var10;
                     return var21;
                  }

                  var1.setline(1017);
                  PyString var19 = PyString.fromInterned("");
                  var1.setlocal(21, var19);
                  var1.setlocal(22, var19);
                  var1.setline(1018);
                  var1.getlocal(9).__getattr__("set_seqs").__call__(var2, var1.getlocal(19), var1.getlocal(20));
                  var1.setline(1019);
                  var3 = var1.getlocal(9).__getattr__("get_opcodes").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(1019);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(1033);
                        var3 = var1.getlocal(0).__getattr__("_qformat").__call__(var2, var1.getlocal(19), var1.getlocal(20), var1.getlocal(21), var1.getlocal(22)).__iter__();
                        break;
                     }

                     PyObject[] var18 = Py.unpackSequence(var4, 5);
                     var6 = var18[0];
                     var1.setlocal(23, var6);
                     var6 = null;
                     var6 = var18[1];
                     var1.setlocal(24, var6);
                     var6 = null;
                     var6 = var18[2];
                     var1.setlocal(25, var6);
                     var6 = null;
                     var6 = var18[3];
                     var1.setlocal(26, var6);
                     var6 = null;
                     var6 = var18[4];
                     var1.setlocal(27, var6);
                     var6 = null;
                     var1.setline(1020);
                     var18 = new PyObject[]{var1.getlocal(25)._sub(var1.getlocal(24)), var1.getlocal(27)._sub(var1.getlocal(26))};
                     var22 = new PyTuple(var18);
                     Arrays.fill(var18, (Object)null);
                     PyTuple var20 = var22;
                     PyObject[] var17 = Py.unpackSequence(var20, 2);
                     var7 = var17[0];
                     var1.setlocal(28, var7);
                     var7 = null;
                     var7 = var17[1];
                     var1.setlocal(29, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(1021);
                     var14 = var1.getlocal(23);
                     var21 = var14._eq(PyString.fromInterned("replace"));
                     var5 = null;
                     if (var21.__nonzero__()) {
                        var1.setline(1022);
                        var14 = var1.getlocal(21);
                        var14 = var14._iadd(PyString.fromInterned("^")._mul(var1.getlocal(28)));
                        var1.setlocal(21, var14);
                        var1.setline(1023);
                        var14 = var1.getlocal(22);
                        var14 = var14._iadd(PyString.fromInterned("^")._mul(var1.getlocal(29)));
                        var1.setlocal(22, var14);
                     } else {
                        var1.setline(1024);
                        var14 = var1.getlocal(23);
                        var21 = var14._eq(PyString.fromInterned("delete"));
                        var5 = null;
                        if (var21.__nonzero__()) {
                           var1.setline(1025);
                           var14 = var1.getlocal(21);
                           var14 = var14._iadd(PyString.fromInterned("-")._mul(var1.getlocal(28)));
                           var1.setlocal(21, var14);
                        } else {
                           var1.setline(1026);
                           var14 = var1.getlocal(23);
                           var21 = var14._eq(PyString.fromInterned("insert"));
                           var5 = null;
                           if (var21.__nonzero__()) {
                              var1.setline(1027);
                              var14 = var1.getlocal(22);
                              var14 = var14._iadd(PyString.fromInterned("+")._mul(var1.getlocal(29)));
                              var1.setlocal(22, var14);
                           } else {
                              var1.setline(1028);
                              var14 = var1.getlocal(23);
                              var21 = var14._eq(PyString.fromInterned("equal"));
                              var5 = null;
                              if (!var21.__nonzero__()) {
                                 var1.setline(1032);
                                 var21 = var1.getglobal("ValueError");
                                 PyString var10001 = PyString.fromInterned("unknown tag %r");
                                 var18 = new PyObject[]{var1.getlocal(23)};
                                 PyTuple var10002 = new PyTuple(var18);
                                 Arrays.fill(var18, (Object)null);
                                 throw Py.makeException(var21, var10001._mod(var10002));
                              }

                              var1.setline(1029);
                              var14 = var1.getlocal(21);
                              var14 = var14._iadd(PyString.fromInterned(" ")._mul(var1.getlocal(28)));
                              var1.setlocal(21, var14);
                              var1.setline(1030);
                              var14 = var1.getlocal(22);
                              var14 = var14._iadd(PyString.fromInterned(" ")._mul(var1.getlocal(29)));
                              var1.setlocal(22, var14);
                           }
                        }
                     }
                  }
               }

               var1.setline(1033);
               var4 = var3.__iternext__();
               if (var4 != null) {
                  var1.setlocal(18, var4);
                  var1.setline(1034);
                  var1.setline(1034);
                  var21 = var1.getlocal(18);
                  var1.f_lasti = 3;
                  var5 = new Object[10];
                  var5[3] = var3;
                  var5[4] = var4;
                  var1.f_savedlocals = var5;
                  return var21;
               }
            }

            var1.setline(1040);
            var21 = var1.getlocal(0).__getattr__("_fancy_helper");
            var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(16)._add(Py.newInteger(1)), var1.getlocal(3), var1.getlocal(4), var1.getlocal(17)._add(Py.newInteger(1)), var1.getlocal(6)};
            var3 = var21.__call__(var2, var11).__iter__();
         }

         var1.setline(1040);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(18, var4);
         var1.setline(1041);
         var1.setline(1041);
         var21 = var1.getlocal(18);
         var1.f_lasti = 5;
         var5 = new Object[10];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var21;
      }

      var1.setline(997);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.setline(999);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(18, var4);
         var1.setline(998);
         var1.setline(998);
         var21 = var1.getlocal(18);
         var1.f_lasti = 1;
         var5 = new Object[10];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var21;
      }
   }

   public PyObject _fancy_helper$24(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1044);
            PyObject[] var6 = Py.EmptyObjects;
            PyList var9 = new PyList(var6);
            Arrays.fill(var6, (Object)null);
            PyList var7 = var9;
            var1.setlocal(7, var7);
            var3 = null;
            var1.setline(1045);
            var3 = var1.getlocal(2);
            var8 = var3._lt(var1.getlocal(3));
            var3 = null;
            if (var8.__nonzero__()) {
               var1.setline(1046);
               var3 = var1.getlocal(5);
               var8 = var3._lt(var1.getlocal(6));
               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(1047);
                  var8 = var1.getlocal(0).__getattr__("_fancy_replace");
                  var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
                  var3 = var8.__call__(var2, var6);
                  var1.setlocal(7, var3);
                  var3 = null;
               } else {
                  var1.setline(1049);
                  var3 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("-"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
                  var1.setlocal(7, var3);
                  var3 = null;
               }
            } else {
               var1.setline(1050);
               var3 = var1.getlocal(5);
               var8 = var3._lt(var1.getlocal(6));
               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(1051);
                  var3 = var1.getlocal(0).__getattr__("_dump").__call__(var2, PyString.fromInterned("+"), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
                  var1.setlocal(7, var3);
                  var3 = null;
               }
            }

            var1.setline(1053);
            var3 = var1.getlocal(7).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      var1.setline(1053);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(8, var4);
         var1.setline(1054);
         var1.setline(1054);
         var8 = var1.getlocal(8);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject _qformat$25(PyFrame var1, ThreadState var2) {
      label38: {
         Object var10000;
         PyTuple var10001;
         Object[] var3;
         PyObject[] var4;
         PyObject var6;
         PyString var7;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(1071);
               PyString.fromInterned("\n        Format \"?\" output and deal with leading tabs.\n\n        Example:\n\n        >>> d = Differ()\n        >>> results = d._qformat('\\tabcDefghiJkl\\n', '\\tabcdefGhijkl\\n',\n        ...                      '  ^ ^  ^      ', '  ^ ^  ^      ')\n        >>> for line in results: print repr(line)\n        ...\n        '- \\tabcDefghiJkl\\n'\n        '? \\t ^ ^  ^\\n'\n        '+ \\tabcdefGhijkl\\n'\n        '? \\t ^ ^  ^\\n'\n        ");
               var1.setline(1074);
               PyObject var5 = var1.getglobal("min").__call__(var2, var1.getglobal("_count_leading").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("\t")), var1.getglobal("_count_leading").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("\t")));
               var1.setlocal(5, var5);
               var3 = null;
               var1.setline(1076);
               var5 = var1.getglobal("min").__call__(var2, var1.getlocal(5), var1.getglobal("_count_leading").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null), (PyObject)PyString.fromInterned(" ")));
               var1.setlocal(5, var5);
               var3 = null;
               var1.setline(1077);
               var5 = var1.getglobal("min").__call__(var2, var1.getlocal(5), var1.getglobal("_count_leading").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null), (PyObject)PyString.fromInterned(" ")));
               var1.setlocal(5, var5);
               var3 = null;
               var1.setline(1078);
               var5 = var1.getlocal(3).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null).__getattr__("rstrip").__call__(var2);
               var1.setlocal(3, var5);
               var3 = null;
               var1.setline(1079);
               var5 = var1.getlocal(4).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null).__getattr__("rstrip").__call__(var2);
               var1.setlocal(4, var5);
               var3 = null;
               var1.setline(1081);
               var1.setline(1081);
               var6 = PyString.fromInterned("- ")._add(var1.getlocal(1));
               var1.f_lasti = 1;
               var3 = new Object[4];
               var1.f_savedlocals = var3;
               return var6;
            case 1:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var6 = (PyObject)var10000;
               var1.setline(1082);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(1083);
                  var1.setline(1083);
                  var7 = PyString.fromInterned("? %s%s\n");
                  var4 = new PyObject[]{PyString.fromInterned("\t")._mul(var1.getlocal(5)), var1.getlocal(3)};
                  var10001 = new PyTuple(var4);
                  Arrays.fill(var4, (Object)null);
                  var6 = var7._mod(var10001);
                  var1.f_lasti = 2;
                  var3 = new Object[4];
                  var1.f_savedlocals = var3;
                  return var6;
               }
               break;
            case 2:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var6 = (PyObject)var10000;
               break;
            case 3:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var6 = (PyObject)var10000;
               var1.setline(1086);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(1087);
                  var1.setline(1087);
                  var7 = PyString.fromInterned("? %s%s\n");
                  var4 = new PyObject[]{PyString.fromInterned("\t")._mul(var1.getlocal(5)), var1.getlocal(4)};
                  var10001 = new PyTuple(var4);
                  Arrays.fill(var4, (Object)null);
                  var6 = var7._mod(var10001);
                  var1.f_lasti = 4;
                  var3 = new Object[4];
                  var1.f_savedlocals = var3;
                  return var6;
               }
               break label38;
            case 4:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var6 = (PyObject)var10000;
               break label38;
         }

         var1.setline(1085);
         var1.setline(1085);
         var6 = PyString.fromInterned("+ ")._add(var1.getlocal(2));
         var1.f_lasti = 3;
         var3 = new Object[4];
         var1.f_savedlocals = var3;
         return var6;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IS_LINE_JUNK$26(PyFrame var1, ThreadState var2) {
      var1.setline(1120);
      PyString.fromInterned("\n    Return 1 for ignorable line: iff `line` is blank or contains a single '#'.\n\n    Examples:\n\n    >>> IS_LINE_JUNK('\\n')\n    True\n    >>> IS_LINE_JUNK('  #   \\n')\n    True\n    >>> IS_LINE_JUNK('hello\\n')\n    False\n    ");
      var1.setline(1122);
      PyObject var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IS_CHARACTER_JUNK$27(PyFrame var1, ThreadState var2) {
      var1.setline(1138);
      PyString.fromInterned("\n    Return 1 for ignorable character: iff `ch` is a space or tab.\n\n    Examples:\n\n    >>> IS_CHARACTER_JUNK(' ')\n    True\n    >>> IS_CHARACTER_JUNK('\\t')\n    True\n    >>> IS_CHARACTER_JUNK('\\n')\n    False\n    >>> IS_CHARACTER_JUNK('x')\n    False\n    ");
      var1.setline(1140);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _format_range_unified$28(PyFrame var1, ThreadState var2) {
      var1.setline(1148);
      PyString.fromInterned("Convert range to the \"ed\" format");
      var1.setline(1150);
      PyObject var3 = var1.getlocal(0)._add(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1151);
      var3 = var1.getlocal(1)._sub(var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1152);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1153);
         var3 = PyString.fromInterned("{}").__getattr__("format").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1154);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(1155);
            PyObject var4 = var1.getlocal(2);
            var4 = var4._isub(Py.newInteger(1));
            var1.setlocal(2, var4);
         }

         var1.setline(1156);
         var3 = PyString.fromInterned("{},{}").__getattr__("format").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unified_diff$29(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var7;
      Object[] var10;
      PyObject var17;
      PyTuple var18;
      label153: {
         PyObject var6;
         PyObject var8;
         Object[] var9;
         Object var12;
         PyObject[] var14;
         Object var10000;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(1197);
               PyString.fromInterned("\n    Compare two sequences of lines; generate the delta as a unified diff.\n\n    Unified diffs are a compact way of showing line changes and a few\n    lines of context.  The number of context lines is set by 'n' which\n    defaults to three.\n\n    By default, the diff control lines (those with ---, +++, or @@) are\n    created with a trailing newline.  This is helpful so that inputs\n    created from file.readlines() result in diffs that are suitable for\n    file.writelines() since both the inputs and outputs have trailing\n    newlines.\n\n    For inputs that do not have trailing newlines, set the lineterm\n    argument to \"\" so that the output will be uniformly newline free.\n\n    The unidiff format normally has a header for filenames and modification\n    times.  Any or all of these may be specified using strings for\n    'fromfile', 'tofile', 'fromfiledate', and 'tofiledate'.\n    The modification times are normally expressed in the ISO 8601 format.\n\n    Example:\n\n    >>> for line in unified_diff('one two three four'.split(),\n    ...             'zero one tree four'.split(), 'Original', 'Current',\n    ...             '2005-01-26 23:30:50', '2010-04-02 10:20:52',\n    ...             lineterm=''):\n    ...     print line                  # doctest: +NORMALIZE_WHITESPACE\n    --- Original        2005-01-26 23:30:50\n    +++ Current         2010-04-02 10:20:52\n    @@ -1,4 +1,4 @@\n    +zero\n     one\n    -two\n    -three\n    +tree\n     four\n    ");
               var1.setline(1199);
               var3 = var1.getglobal("False");
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(1200);
               var3 = var1.getglobal("SequenceMatcher").__call__(var2, var1.getglobal("None"), var1.getlocal(0), var1.getlocal(1)).__getattr__("get_grouped_opcodes").__call__(var2, var1.getlocal(6)).__iter__();
               var1.setline(1200);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(9, var4);
               var1.setline(1201);
               if (var1.getlocal(8).__not__().__nonzero__()) {
                  var1.setline(1202);
                  var5 = var1.getglobal("True");
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(1203);
                  var1.setline(1203);
                  var12 = var1.getlocal(4).__nonzero__() ? PyString.fromInterned("\t{}").__getattr__("format").__call__(var2, var1.getlocal(4)) : PyString.fromInterned("");
                  var1.setlocal(10, (PyObject)var12);
                  var5 = null;
                  var1.setline(1204);
                  var1.setline(1204);
                  var12 = var1.getlocal(5).__nonzero__() ? PyString.fromInterned("\t{}").__getattr__("format").__call__(var2, var1.getlocal(5)) : PyString.fromInterned("");
                  var1.setlocal(11, (PyObject)var12);
                  var5 = null;
                  var1.setline(1205);
                  var1.setline(1205);
                  var17 = PyString.fromInterned("--- {}{}{}").__getattr__("format").__call__(var2, var1.getlocal(2), var1.getlocal(10), var1.getlocal(7));
                  var1.f_lasti = 1;
                  var10 = new Object[]{null, null, null, var3, var4, null};
                  var1.f_savedlocals = var10;
                  return var17;
               }
               break label153;
            case 1:
               var10 = var1.f_savedlocals;
               var3 = (PyObject)var10[3];
               var4 = (PyObject)var10[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               var1.setline(1206);
               var1.setline(1206);
               var17 = PyString.fromInterned("+++ {}{}{}").__getattr__("format").__call__(var2, var1.getlocal(3), var1.getlocal(11), var1.getlocal(7));
               var1.f_lasti = 2;
               var10 = new Object[]{null, null, null, var3, var4, null};
               var1.f_savedlocals = var10;
               return var17;
            case 2:
               var10 = var1.f_savedlocals;
               var3 = (PyObject)var10[3];
               var4 = (PyObject)var10[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               break label153;
            case 3:
               var10 = var1.f_savedlocals;
               var3 = (PyObject)var10[3];
               var4 = (PyObject)var10[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               var1.setline(1213);
               var5 = var1.getlocal(9).__iter__();
               break;
            case 4:
               var9 = var1.f_savedlocals;
               var3 = (PyObject)var9[3];
               var4 = (PyObject)var9[4];
               var5 = (PyObject)var9[5];
               var6 = (PyObject)var9[6];
               var7 = (PyObject)var9[7];
               var8 = (PyObject)var9[8];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               var1.setline(1215);
               var8 = var7.__iternext__();
               if (var8 != null) {
                  var1.setlocal(21, var8);
                  var1.setline(1216);
                  var1.setline(1216);
                  var17 = PyString.fromInterned(" ")._add(var1.getlocal(21));
                  var1.f_lasti = 4;
                  var9 = new Object[9];
                  var9[3] = var3;
                  var9[4] = var4;
                  var9[5] = var5;
                  var9[6] = var6;
                  var9[7] = var7;
                  var9[8] = var8;
                  var1.f_savedlocals = var9;
                  return var17;
               }
               break;
            case 5:
               var9 = var1.f_savedlocals;
               var3 = (PyObject)var9[3];
               var4 = (PyObject)var9[4];
               var5 = (PyObject)var9[5];
               var6 = (PyObject)var9[6];
               var7 = (PyObject)var9[7];
               var8 = (PyObject)var9[8];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               var1.setline(1219);
               var8 = var7.__iternext__();
               if (var8 != null) {
                  var1.setlocal(21, var8);
                  var1.setline(1220);
                  var1.setline(1220);
                  var17 = PyString.fromInterned("-")._add(var1.getlocal(21));
                  var1.f_lasti = 5;
                  var9 = new Object[10];
                  var9[3] = var3;
                  var9[4] = var4;
                  var9[5] = var5;
                  var9[6] = var6;
                  var9[7] = var7;
                  var9[8] = var8;
                  var1.f_savedlocals = var9;
                  return var17;
               }

               var1.setline(1221);
               var7 = var1.getlocal(16);
               var14 = new PyObject[]{PyString.fromInterned("replace"), PyString.fromInterned("insert")};
               var18 = new PyTuple(var14);
               Arrays.fill(var14, (Object)null);
               var17 = var7._in(var18);
               var7 = null;
               if (var17.__nonzero__()) {
                  var1.setline(1222);
                  var7 = var1.getlocal(1).__getslice__(var1.getlocal(19), var1.getlocal(20), (PyObject)null).__iter__();
                  var1.setline(1222);
                  var8 = var7.__iternext__();
                  if (var8 != null) {
                     var1.setlocal(21, var8);
                     var1.setline(1223);
                     var1.setline(1223);
                     var17 = PyString.fromInterned("+")._add(var1.getlocal(21));
                     var1.f_lasti = 6;
                     var9 = new Object[10];
                     var9[3] = var3;
                     var9[4] = var4;
                     var9[5] = var5;
                     var9[6] = var6;
                     var9[7] = var7;
                     var9[8] = var8;
                     var1.f_savedlocals = var9;
                     return var17;
                  }
               }
               break;
            case 6:
               var9 = var1.f_savedlocals;
               var3 = (PyObject)var9[3];
               var4 = (PyObject)var9[4];
               var5 = (PyObject)var9[5];
               var6 = (PyObject)var9[6];
               var7 = (PyObject)var9[7];
               var8 = (PyObject)var9[8];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var17 = (PyObject)var10000;
               var1.setline(1222);
               var8 = var7.__iternext__();
               if (var8 != null) {
                  var1.setlocal(21, var8);
                  var1.setline(1223);
                  var1.setline(1223);
                  var17 = PyString.fromInterned("+")._add(var1.getlocal(21));
                  var1.f_lasti = 6;
                  var9 = new Object[10];
                  var9[3] = var3;
                  var9[4] = var4;
                  var9[5] = var5;
                  var9[6] = var6;
                  var9[7] = var7;
                  var9[8] = var8;
                  var1.f_savedlocals = var9;
                  return var17;
               }
         }

         while(true) {
            var1.setline(1213);
            var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(1200);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(9, var4);
               var1.setline(1201);
               if (var1.getlocal(8).__not__().__nonzero__()) {
                  var1.setline(1202);
                  var5 = var1.getglobal("True");
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(1203);
                  var1.setline(1203);
                  var12 = var1.getlocal(4).__nonzero__() ? PyString.fromInterned("\t{}").__getattr__("format").__call__(var2, var1.getlocal(4)) : PyString.fromInterned("");
                  var1.setlocal(10, (PyObject)var12);
                  var5 = null;
                  var1.setline(1204);
                  var1.setline(1204);
                  var12 = var1.getlocal(5).__nonzero__() ? PyString.fromInterned("\t{}").__getattr__("format").__call__(var2, var1.getlocal(5)) : PyString.fromInterned("");
                  var1.setlocal(11, (PyObject)var12);
                  var5 = null;
                  var1.setline(1205);
                  var1.setline(1205);
                  var17 = PyString.fromInterned("--- {}{}{}").__getattr__("format").__call__(var2, var1.getlocal(2), var1.getlocal(10), var1.getlocal(7));
                  var1.f_lasti = 1;
                  var10 = new Object[]{null, null, null, var3, var4, null};
                  var1.f_savedlocals = var10;
                  return var17;
               }
               break;
            }

            PyObject[] var13 = Py.unpackSequence(var6, 5);
            var8 = var13[0];
            var1.setlocal(16, var8);
            var8 = null;
            var8 = var13[1];
            var1.setlocal(17, var8);
            var8 = null;
            var8 = var13[2];
            var1.setlocal(18, var8);
            var8 = null;
            var8 = var13[3];
            var1.setlocal(19, var8);
            var8 = null;
            var8 = var13[4];
            var1.setlocal(20, var8);
            var8 = null;
            var1.setline(1214);
            var7 = var1.getlocal(16);
            var17 = var7._eq(PyString.fromInterned("equal"));
            var7 = null;
            if (var17.__nonzero__()) {
               var1.setline(1215);
               var7 = var1.getlocal(0).__getslice__(var1.getlocal(17), var1.getlocal(18), (PyObject)null).__iter__();
               var1.setline(1215);
               var8 = var7.__iternext__();
               if (var8 != null) {
                  var1.setlocal(21, var8);
                  var1.setline(1216);
                  var1.setline(1216);
                  var17 = PyString.fromInterned(" ")._add(var1.getlocal(21));
                  var1.f_lasti = 4;
                  var9 = new Object[9];
                  var9[3] = var3;
                  var9[4] = var4;
                  var9[5] = var5;
                  var9[6] = var6;
                  var9[7] = var7;
                  var9[8] = var8;
                  var1.f_savedlocals = var9;
                  return var17;
               }
            } else {
               var1.setline(1218);
               var7 = var1.getlocal(16);
               var14 = new PyObject[]{PyString.fromInterned("replace"), PyString.fromInterned("delete")};
               var18 = new PyTuple(var14);
               Arrays.fill(var14, (Object)null);
               var17 = var7._in(var18);
               var7 = null;
               if (var17.__nonzero__()) {
                  var1.setline(1219);
                  var7 = var1.getlocal(0).__getslice__(var1.getlocal(17), var1.getlocal(18), (PyObject)null).__iter__();
                  var1.setline(1219);
                  var8 = var7.__iternext__();
                  if (var8 != null) {
                     var1.setlocal(21, var8);
                     var1.setline(1220);
                     var1.setline(1220);
                     var17 = PyString.fromInterned("-")._add(var1.getlocal(21));
                     var1.f_lasti = 5;
                     var9 = new Object[10];
                     var9[3] = var3;
                     var9[4] = var4;
                     var9[5] = var5;
                     var9[6] = var6;
                     var9[7] = var7;
                     var9[8] = var8;
                     var1.f_savedlocals = var9;
                     return var17;
                  }
               }

               var1.setline(1221);
               var7 = var1.getlocal(16);
               var14 = new PyObject[]{PyString.fromInterned("replace"), PyString.fromInterned("insert")};
               var18 = new PyTuple(var14);
               Arrays.fill(var14, (Object)null);
               var17 = var7._in(var18);
               var7 = null;
               if (var17.__nonzero__()) {
                  var1.setline(1222);
                  var7 = var1.getlocal(1).__getslice__(var1.getlocal(19), var1.getlocal(20), (PyObject)null).__iter__();
                  var1.setline(1222);
                  var8 = var7.__iternext__();
                  if (var8 != null) {
                     var1.setlocal(21, var8);
                     var1.setline(1223);
                     var1.setline(1223);
                     var17 = PyString.fromInterned("+")._add(var1.getlocal(21));
                     var1.f_lasti = 6;
                     var9 = new Object[10];
                     var9[3] = var3;
                     var9[4] = var4;
                     var9[5] = var5;
                     var9[6] = var6;
                     var9[7] = var7;
                     var9[8] = var8;
                     var1.f_savedlocals = var9;
                     return var17;
                  }
               }
            }
         }
      }

      var1.setline(1208);
      PyObject[] var15 = new PyObject[]{var1.getlocal(9).__getitem__(Py.newInteger(0)), var1.getlocal(9).__getitem__(Py.newInteger(-1))};
      var18 = new PyTuple(var15);
      Arrays.fill(var15, (Object)null);
      PyTuple var16 = var18;
      PyObject[] var11 = Py.unpackSequence(var16, 2);
      var7 = var11[0];
      var1.setlocal(12, var7);
      var7 = null;
      var7 = var11[1];
      var1.setlocal(13, var7);
      var7 = null;
      var5 = null;
      var1.setline(1209);
      var5 = var1.getglobal("_format_range_unified").__call__(var2, var1.getlocal(12).__getitem__(Py.newInteger(1)), var1.getlocal(13).__getitem__(Py.newInteger(2)));
      var1.setlocal(14, var5);
      var5 = null;
      var1.setline(1210);
      var5 = var1.getglobal("_format_range_unified").__call__(var2, var1.getlocal(12).__getitem__(Py.newInteger(3)), var1.getlocal(13).__getitem__(Py.newInteger(4)));
      var1.setlocal(15, var5);
      var5 = null;
      var1.setline(1211);
      var1.setline(1211);
      var17 = PyString.fromInterned("@@ -{} +{} @@{}").__getattr__("format").__call__(var2, var1.getlocal(14), var1.getlocal(15), var1.getlocal(7));
      var1.f_lasti = 3;
      var10 = new Object[8];
      var10[3] = var3;
      var10[4] = var4;
      var1.f_savedlocals = var10;
      return var17;
   }

   public PyObject _format_range_context$30(PyFrame var1, ThreadState var2) {
      var1.setline(1231);
      PyString.fromInterned("Convert range to the \"ed\" format");
      var1.setline(1233);
      PyObject var3 = var1.getlocal(0)._add(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1234);
      var3 = var1.getlocal(1)._sub(var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1235);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(1236);
         var3 = var1.getlocal(2);
         var3 = var3._isub(Py.newInteger(1));
         var1.setlocal(2, var3);
      }

      var1.setline(1237);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._le(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1238);
         var3 = PyString.fromInterned("{}").__getattr__("format").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1239);
         var3 = PyString.fromInterned("{},{}").__getattr__("format").__call__(var2, var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(3))._sub(Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject context_diff$31(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var7;
      Object[] var10;
      PyObject[] var11;
      PyObject var19;
      label168: {
         label169: {
            label158: {
               PyObject var6;
               PyObject var8;
               Object[] var9;
               PyObject[] var15;
               label159: {
                  PyFunction var13;
                  Object var10000;
                  PyObject var10002;
                  switch (var1.f_lasti) {
                     case 0:
                     default:
                        var1.setline(1283);
                        PyString.fromInterned("\n    Compare two sequences of lines; generate the delta as a context diff.\n\n    Context diffs are a compact way of showing line changes and a few\n    lines of context.  The number of context lines is set by 'n' which\n    defaults to three.\n\n    By default, the diff control lines (those with *** or ---) are\n    created with a trailing newline.  This is helpful so that inputs\n    created from file.readlines() result in diffs that are suitable for\n    file.writelines() since both the inputs and outputs have trailing\n    newlines.\n\n    For inputs that do not have trailing newlines, set the lineterm\n    argument to \"\" so that the output will be uniformly newline free.\n\n    The context diff format normally has a header for filenames and\n    modification times.  Any or all of these may be specified using\n    strings for 'fromfile', 'tofile', 'fromfiledate', and 'tofiledate'.\n    The modification times are normally expressed in the ISO 8601 format.\n    If not specified, the strings default to blanks.\n\n    Example:\n\n    >>> print ''.join(context_diff('one\\ntwo\\nthree\\nfour\\n'.splitlines(1),\n    ...       'zero\\none\\ntree\\nfour\\n'.splitlines(1), 'Original', 'Current')),\n    *** Original\n    --- Current\n    ***************\n    *** 1,4 ****\n      one\n    ! two\n    ! three\n      four\n    --- 1,4 ----\n    + zero\n      one\n    ! tree\n      four\n    ");
                        var1.setline(1285);
                        var19 = var1.getglobal("dict");
                        PyObject[] var12 = new PyObject[]{PyString.fromInterned("+ "), PyString.fromInterned("- "), PyString.fromInterned("! "), PyString.fromInterned("  ")};
                        String[] var14 = new String[]{"insert", "delete", "replace", "equal"};
                        var19 = var19.__call__(var2, var12, var14);
                        var3 = null;
                        var3 = var19;
                        var1.setlocal(8, var3);
                        var3 = null;
                        var1.setline(1286);
                        var3 = var1.getglobal("False");
                        var1.setlocal(9, var3);
                        var3 = null;
                        var1.setline(1287);
                        var3 = var1.getglobal("SequenceMatcher").__call__(var2, var1.getglobal("None"), var1.getlocal(0), var1.getlocal(1)).__getattr__("get_grouped_opcodes").__call__(var2, var1.getlocal(6)).__iter__();
                        break label158;
                     case 1:
                        var10 = var1.f_savedlocals;
                        var3 = (PyObject)var10[3];
                        var4 = (PyObject)var10[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var19 = (PyObject)var10000;
                        var1.setline(1293);
                        var1.setline(1293);
                        var19 = PyString.fromInterned("--- {}{}{}").__getattr__("format").__call__(var2, var1.getlocal(3), var1.getlocal(12), var1.getlocal(7));
                        var1.f_lasti = 2;
                        var10 = new Object[]{null, null, null, var3, var4, null};
                        var1.f_savedlocals = var10;
                        return var19;
                     case 2:
                        var10 = var1.f_savedlocals;
                        var3 = (PyObject)var10[3];
                        var4 = (PyObject)var10[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var19 = (PyObject)var10000;
                        break label168;
                     case 3:
                        var10 = var1.f_savedlocals;
                        var3 = (PyObject)var10[3];
                        var4 = (PyObject)var10[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var19 = (PyObject)var10000;
                        var1.setline(1298);
                        var5 = var1.getglobal("_format_range_context").__call__(var2, var1.getlocal(13).__getitem__(Py.newInteger(1)), var1.getlocal(14).__getitem__(Py.newInteger(2)));
                        var1.setlocal(15, var5);
                        var5 = null;
                        var1.setline(1299);
                        var1.setline(1299);
                        var19 = PyString.fromInterned("*** {} ****{}").__getattr__("format").__call__(var2, var1.getlocal(15), var1.getlocal(7));
                        var1.f_lasti = 4;
                        var10 = new Object[8];
                        var10[3] = var3;
                        var10[4] = var4;
                        var1.f_savedlocals = var10;
                        return var19;
                     case 4:
                        var10 = var1.f_savedlocals;
                        var3 = (PyObject)var10[3];
                        var4 = (PyObject)var10[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var19 = (PyObject)var10000;
                        var1.setline(1301);
                        var19 = var1.getglobal("any");
                        var1.setline(1301);
                        var11 = Py.EmptyObjects;
                        var13 = new PyFunction(var1.f_globals, var11, f$32, (PyObject)null);
                        var10002 = var13.__call__(var2, var1.getlocal(10).__iter__());
                        Arrays.fill(var11, (Object)null);
                        if (!var19.__call__(var2, var10002).__nonzero__()) {
                           break label169;
                        }

                        var1.setline(1302);
                        var5 = var1.getlocal(10).__iter__();
                        break;
                     case 5:
                        var9 = var1.f_savedlocals;
                        var3 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var5 = (PyObject)var9[5];
                        var6 = (PyObject)var9[6];
                        var7 = (PyObject)var9[7];
                        var8 = (PyObject)var9[8];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var19 = (PyObject)var10000;
                        var1.setline(1304);
                        var8 = var7.__iternext__();
                        if (var8 != null) {
                           var1.setlocal(21, var8);
                           var1.setline(1305);
                           var1.setline(1305);
                           var19 = var1.getlocal(8).__getitem__(var1.getlocal(17))._add(var1.getlocal(21));
                           var1.f_lasti = 5;
                           var9 = new Object[9];
                           var9[3] = var3;
                           var9[4] = var4;
                           var9[5] = var5;
                           var9[6] = var6;
                           var9[7] = var7;
                           var9[8] = var8;
                           var1.f_savedlocals = var9;
                           return var19;
                        }
                        break;
                     case 6:
                        var10 = var1.f_savedlocals;
                        var3 = (PyObject)var10[3];
                        var4 = (PyObject)var10[4];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var19 = (PyObject)var10000;
                        var1.setline(1310);
                        var19 = var1.getglobal("any");
                        var1.setline(1310);
                        var11 = Py.EmptyObjects;
                        var13 = new PyFunction(var1.f_globals, var11, f$33, (PyObject)null);
                        var10002 = var13.__call__(var2, var1.getlocal(10).__iter__());
                        Arrays.fill(var11, (Object)null);
                        if (!var19.__call__(var2, var10002).__nonzero__()) {
                           break label158;
                        }

                        var1.setline(1311);
                        var5 = var1.getlocal(10).__iter__();
                        break label159;
                     case 7:
                        var9 = var1.f_savedlocals;
                        var3 = (PyObject)var9[3];
                        var4 = (PyObject)var9[4];
                        var5 = (PyObject)var9[5];
                        var6 = (PyObject)var9[6];
                        var7 = (PyObject)var9[7];
                        var8 = (PyObject)var9[8];
                        var10000 = var1.getGeneratorInput();
                        if (var10000 instanceof PyException) {
                           throw (Throwable)var10000;
                        }

                        var19 = (PyObject)var10000;
                        var1.setline(1313);
                        var8 = var7.__iternext__();
                        if (var8 != null) {
                           var1.setlocal(21, var8);
                           var1.setline(1314);
                           var1.setline(1314);
                           var19 = var1.getlocal(8).__getitem__(var1.getlocal(17))._add(var1.getlocal(21));
                           var1.f_lasti = 7;
                           var9 = new Object[10];
                           var9[3] = var3;
                           var9[4] = var4;
                           var9[5] = var5;
                           var9[6] = var6;
                           var9[7] = var7;
                           var9[8] = var8;
                           var1.f_savedlocals = var9;
                           return var19;
                        }
                        break label159;
                  }

                  do {
                     do {
                        var1.setline(1302);
                        var6 = var5.__iternext__();
                        if (var6 == null) {
                           break label169;
                        }

                        var15 = Py.unpackSequence(var6, 5);
                        var8 = var15[0];
                        var1.setlocal(17, var8);
                        var8 = null;
                        var8 = var15[1];
                        var1.setlocal(18, var8);
                        var8 = null;
                        var8 = var15[2];
                        var1.setlocal(19, var8);
                        var8 = null;
                        var8 = var15[3];
                        var1.setlocal(20, var8);
                        var8 = null;
                        var8 = var15[4];
                        var1.setlocal(20, var8);
                        var8 = null;
                        var1.setline(1303);
                        var7 = var1.getlocal(17);
                        var19 = var7._ne(PyString.fromInterned("insert"));
                        var7 = null;
                     } while(!var19.__nonzero__());

                     var1.setline(1304);
                     var7 = var1.getlocal(0).__getslice__(var1.getlocal(18), var1.getlocal(19), (PyObject)null).__iter__();
                     var1.setline(1304);
                     var8 = var7.__iternext__();
                  } while(var8 == null);

                  var1.setlocal(21, var8);
                  var1.setline(1305);
                  var1.setline(1305);
                  var19 = var1.getlocal(8).__getitem__(var1.getlocal(17))._add(var1.getlocal(21));
                  var1.f_lasti = 5;
                  var9 = new Object[9];
                  var9[3] = var3;
                  var9[4] = var4;
                  var9[5] = var5;
                  var9[6] = var6;
                  var9[7] = var7;
                  var9[8] = var8;
                  var1.f_savedlocals = var9;
                  return var19;
               }

               do {
                  do {
                     var1.setline(1311);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        break label158;
                     }

                     var15 = Py.unpackSequence(var6, 5);
                     var8 = var15[0];
                     var1.setlocal(17, var8);
                     var8 = null;
                     var8 = var15[1];
                     var1.setlocal(20, var8);
                     var8 = null;
                     var8 = var15[2];
                     var1.setlocal(20, var8);
                     var8 = null;
                     var8 = var15[3];
                     var1.setlocal(24, var8);
                     var8 = null;
                     var8 = var15[4];
                     var1.setlocal(25, var8);
                     var8 = null;
                     var1.setline(1312);
                     var7 = var1.getlocal(17);
                     var19 = var7._ne(PyString.fromInterned("delete"));
                     var7 = null;
                  } while(!var19.__nonzero__());

                  var1.setline(1313);
                  var7 = var1.getlocal(1).__getslice__(var1.getlocal(24), var1.getlocal(25), (PyObject)null).__iter__();
                  var1.setline(1313);
                  var8 = var7.__iternext__();
               } while(var8 == null);

               var1.setlocal(21, var8);
               var1.setline(1314);
               var1.setline(1314);
               var19 = var1.getlocal(8).__getitem__(var1.getlocal(17))._add(var1.getlocal(21));
               var1.f_lasti = 7;
               var9 = new Object[10];
               var9[3] = var3;
               var9[4] = var4;
               var9[5] = var5;
               var9[6] = var6;
               var9[7] = var7;
               var9[8] = var8;
               var1.f_savedlocals = var9;
               return var19;
            }

            var1.setline(1287);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(10, var4);
            var1.setline(1288);
            if (var1.getlocal(9).__not__().__nonzero__()) {
               var1.setline(1289);
               var5 = var1.getglobal("True");
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(1290);
               var1.setline(1290);
               Object var18 = var1.getlocal(4).__nonzero__() ? PyString.fromInterned("\t{}").__getattr__("format").__call__(var2, var1.getlocal(4)) : PyString.fromInterned("");
               var1.setlocal(11, (PyObject)var18);
               var5 = null;
               var1.setline(1291);
               var1.setline(1291);
               var18 = var1.getlocal(5).__nonzero__() ? PyString.fromInterned("\t{}").__getattr__("format").__call__(var2, var1.getlocal(5)) : PyString.fromInterned("");
               var1.setlocal(12, (PyObject)var18);
               var5 = null;
               var1.setline(1292);
               var1.setline(1292);
               var19 = PyString.fromInterned("*** {}{}{}").__getattr__("format").__call__(var2, var1.getlocal(2), var1.getlocal(11), var1.getlocal(7));
               var1.f_lasti = 1;
               var10 = new Object[]{null, null, null, var3, var4, null};
               var1.f_savedlocals = var10;
               return var19;
            }
            break label168;
         }

         var1.setline(1307);
         var5 = var1.getglobal("_format_range_context").__call__(var2, var1.getlocal(13).__getitem__(Py.newInteger(3)), var1.getlocal(14).__getitem__(Py.newInteger(4)));
         var1.setlocal(22, var5);
         var5 = null;
         var1.setline(1308);
         var1.setline(1308);
         var19 = PyString.fromInterned("--- {} ----{}").__getattr__("format").__call__(var2, var1.getlocal(22), var1.getlocal(7));
         var1.f_lasti = 6;
         var10 = new Object[10];
         var10[3] = var3;
         var10[4] = var4;
         var1.f_savedlocals = var10;
         return var19;
      }

      var1.setline(1295);
      var11 = new PyObject[]{var1.getlocal(10).__getitem__(Py.newInteger(0)), var1.getlocal(10).__getitem__(Py.newInteger(-1))};
      PyTuple var20 = new PyTuple(var11);
      Arrays.fill(var11, (Object)null);
      PyTuple var17 = var20;
      PyObject[] var16 = Py.unpackSequence(var17, 2);
      var7 = var16[0];
      var1.setlocal(13, var7);
      var7 = null;
      var7 = var16[1];
      var1.setlocal(14, var7);
      var7 = null;
      var5 = null;
      var1.setline(1296);
      var1.setline(1296);
      var19 = PyString.fromInterned("***************")._add(var1.getlocal(7));
      var1.f_lasti = 3;
      var10 = new Object[8];
      var10[3] = var3;
      var10[4] = var4;
      var1.f_savedlocals = var10;
      return var19;
   }

   public PyObject f$32(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var10;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1301);
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

            var10 = (PyObject)var10000;
      }

      var1.setline(1301);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var8 = Py.unpackSequence(var4, 5);
         PyObject var6 = var8[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var8[1];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var8[2];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var8[3];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var8[4];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(1301);
         var1.setline(1301);
         PyObject var9 = var1.getlocal(1);
         PyObject[] var7 = new PyObject[]{PyString.fromInterned("replace"), PyString.fromInterned("delete")};
         PyTuple var11 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var10 = var9._in(var11);
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[8];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var10;
      }
   }

   public PyObject f$33(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var10;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1310);
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

            var10 = (PyObject)var10000;
      }

      var1.setline(1310);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var8 = Py.unpackSequence(var4, 5);
         PyObject var6 = var8[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var8[1];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var8[2];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var8[3];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var8[4];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(1310);
         var1.setline(1310);
         PyObject var9 = var1.getlocal(1);
         PyObject[] var7 = new PyObject[]{PyString.fromInterned("replace"), PyString.fromInterned("insert")};
         PyTuple var11 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var10 = var9._in(var11);
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[8];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var10;
      }
   }

   public PyObject ndiff$34(PyFrame var1, ThreadState var2) {
      var1.setline(1349);
      PyString.fromInterned("\n    Compare `a` and `b` (lists of strings); return a `Differ`-style delta.\n\n    Optional keyword parameters `linejunk` and `charjunk` are for filter\n    functions (or None):\n\n    - linejunk: A function that should accept a single string argument, and\n      return true iff the string is junk.  The default is None, and is\n      recommended; as of Python 2.3, an adaptive notion of \"noise\" lines is\n      used that does a good job on its own.\n\n    - charjunk: A function that should accept a string of length 1. The\n      default is module-level function IS_CHARACTER_JUNK, which filters out\n      whitespace characters (a blank or tab; note: bad idea to include newline\n      in this!).\n\n    Tools/scripts/ndiff.py is a command-line front-end to this function.\n\n    Example:\n\n    >>> diff = ndiff('one\\ntwo\\nthree\\n'.splitlines(1),\n    ...              'ore\\ntree\\nemu\\n'.splitlines(1))\n    >>> print ''.join(diff),\n    - one\n    ?  ^\n    + ore\n    ?  ^\n    - two\n    - three\n    ?  -\n    + tree\n    + emu\n    ");
      var1.setline(1350);
      PyObject var3 = var1.getglobal("Differ").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__getattr__("compare").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _mdiff$35(PyFrame var1, ThreadState var2) {
      label169: {
         Object[] var3;
         PyObject[] var4;
         PyObject var5;
         PyObject[] var6;
         PyTuple var7;
         PyObject var8;
         PyInteger var10;
         PyObject var11;
         PyList var12;
         PyTuple var13;
         Object var10000;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(1385);
               PyString.fromInterned("Returns generator yielding marked up from/to side by side differences.\n\n    Arguments:\n    fromlines -- list of text lines to compared to tolines\n    tolines -- list of text lines to be compared to fromlines\n    context -- number of context lines to display on each side of difference,\n               if None, all from/to text lines will be generated.\n    linejunk -- passed on to ndiff (see ndiff documentation)\n    charjunk -- passed on to ndiff (see ndiff documentation)\n\n    This function returns an interator which returns a tuple:\n    (from line tuple, to line tuple, boolean flag)\n\n    from/to line tuple -- (line num, line text)\n        line num -- integer or None (to indicate a context separation)\n        line text -- original line text with following markers inserted:\n            '\\0+' -- marks start of added text\n            '\\0-' -- marks start of deleted text\n            '\\0^' -- marks start of changed text\n            '\\1' -- marks end of added/deleted/changed text\n\n    boolean flag -- None indicates context separation, True indicates\n        either \"from\" or \"to\" line contains a change, otherwise False.\n\n    This function/iterator was originally developed to generate side by side\n    file difference for making HTML pages (see HtmlDiff class for example\n    usage).\n\n    Note, this function utilizes the ndiff function to generate the side by\n    side difference markup.  Optional ndiff arguments may be passed to this\n    function and they in turn will be passed to ndiff.\n    ");
               var1.setline(1386);
               var8 = imp.importOne("re", var1, -1);
               var1.setlocal(5, var8);
               var3 = null;
               var1.setline(1389);
               var8 = var1.getlocal(5).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\++|\\-+|\\^+)"));
               var1.setderef(3, var8);
               var3 = null;
               var1.setline(1392);
               var8 = var1.getglobal("ndiff").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(3), var1.getlocal(4));
               var1.setderef(0, var8);
               var3 = null;
               var1.setline(1394);
               var6 = new PyObject[1];
               var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
               var12 = new PyList(var4);
               Arrays.fill(var4, (Object)null);
               var6[0] = var12;
               PyObject var10002 = var1.f_globals;
               PyObject[] var10003 = var6;
               PyCode var10004 = _make_line$36;
               PyString var10005 = PyString.fromInterned("Returns line of text with user's change markup and line formatting.\n\n        lines -- list of lines from the ndiff generator to produce a line of\n                 text from.  When producing the line of text to return, the\n                 lines used are removed from this list.\n        format_key -- '+' return first line in list with \"add\" markup around\n                          the entire line.\n                      '-' return first line in list with \"delete\" markup around\n                          the entire line.\n                      '?' return first line in list with add/delete/change\n                          intraline markup (indices obtained from second line)\n                      None return first line in list with no markup\n        side -- indice into the num_lines list (0=from,1=to)\n        num_lines -- from/to current line number.  This is NOT intended to be a\n                     passed parameter.  It is present as a keyword argument to\n                     maintain memory of the current line numbers between calls\n                     of this function.\n\n        Note, this function is purposefully not defined at the module scope so\n        that data it needs from its parent function (within whose context it\n        is defined) does not need to be of module scope.\n        ");
               var6 = new PyObject[]{var1.getclosure(3)};
               PyFunction var9 = new PyFunction(var10002, var10003, var10004, var10005, var6);
               var1.setderef(1, var9);
               var3 = null;
               var1.setline(1450);
               var6 = Py.EmptyObjects;
               var10002 = var1.f_globals;
               var10003 = var6;
               var10004 = _line_iterator$38;
               var10005 = PyString.fromInterned("Yields from/to lines of text with a change indication.\n\n        This function is an iterator.  It itself pulls lines from a\n        differencing iterator, processes them and yields them.  When it can\n        it yields both a \"from\" and a \"to\" line, otherwise it will yield one\n        or the other.  In addition to yielding the lines of from/to text, a\n        boolean flag is yielded to indicate if the text line(s) have\n        differences in them.\n\n        Note, this function is purposefully not defined at the module scope so\n        that data it needs from its parent function (within whose context it\n        is defined) does not need to be of module scope.\n        ");
               var6 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
               var9 = new PyFunction(var10002, var10003, var10004, var10005, var6);
               var1.setderef(2, var9);
               var3 = null;
               var1.setline(1541);
               var6 = Py.EmptyObjects;
               var10002 = var1.f_globals;
               var10003 = var6;
               var10004 = _line_pair_iterator$39;
               var10005 = PyString.fromInterned("Yields from/to lines of text with a change indication.\n\n        This function is an iterator.  It itself pulls lines from the line\n        iterator.  Its difference from that iterator is that this function\n        always yields a pair of from/to text lines (with the change\n        indication).  If necessary it will collect single from/to lines\n        until it has a matching pair from/to pair to yield.\n\n        Note, this function is purposefully not defined at the module scope so\n        that data it needs from its parent function (within whose context it\n        is defined) does not need to be of module scope.\n        ");
               var6 = new PyObject[]{var1.getclosure(2)};
               var9 = new PyFunction(var10002, var10003, var10004, var10005, var6);
               var1.setlocal(6, var9);
               var3 = null;
               var1.setline(1571);
               var8 = var1.getlocal(6).__call__(var2);
               var1.setlocal(7, var8);
               var3 = null;
               var1.setline(1572);
               var8 = var1.getlocal(2);
               var11 = var8._is(var1.getglobal("None"));
               var3 = null;
               if (var11.__nonzero__()) {
                  var1.setline(1573);
                  if (var1.getglobal("True").__nonzero__()) {
                     var1.setline(1574);
                     var1.setline(1574);
                     var11 = var1.getlocal(7).__getattr__("next").__call__(var2);
                     var1.f_lasti = 1;
                     var3 = new Object[5];
                     var1.f_savedlocals = var3;
                     return var11;
                  }
                  break label169;
               }

               var1.setline(1578);
               var8 = var1.getlocal(2);
               var8 = var8._iadd(Py.newInteger(1));
               var1.setlocal(2, var8);
               var1.setline(1579);
               var10 = Py.newInteger(0);
               var1.setlocal(8, var10);
               var3 = null;
               var1.setline(1580);
               if (!var1.getglobal("True").__nonzero__()) {
                  break label169;
               }

               var1.setline(1584);
               var6 = new PyObject[]{Py.newInteger(0), null};
               var4 = new PyObject[]{var1.getglobal("None")};
               var12 = new PyList(var4);
               Arrays.fill(var4, (Object)null);
               var6[1] = var12._mul(var1.getlocal(2));
               var13 = new PyTuple(var6);
               Arrays.fill(var6, (Object)null);
               var7 = var13;
               var4 = Py.unpackSequence(var7, 2);
               var5 = var4[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(10, var5);
               var5 = null;
               var3 = null;
               var1.setline(1585);
               var8 = var1.getglobal("False");
               var1.setlocal(11, var8);
               var3 = null;
               break;
            case 1:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var11 = (PyObject)var10000;
               var1.setline(1573);
               if (var1.getglobal("True").__nonzero__()) {
                  var1.setline(1574);
                  var1.setline(1574);
                  var11 = var1.getlocal(7).__getattr__("next").__call__(var2);
                  var1.f_lasti = 1;
                  var3 = new Object[5];
                  var1.f_savedlocals = var3;
                  return var11;
               }
               break label169;
            case 2:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var11 = (PyObject)var10000;
               var1.setline(1595);
               var8 = var1.getlocal(2);
               var1.setlocal(8, var8);
               var3 = null;
               var1.setline(1599);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(1600);
                  var8 = var1.getlocal(9)._mod(var1.getlocal(2));
                  var1.setlocal(14, var8);
                  var3 = null;
                  var1.setline(1601);
                  var8 = var1.getlocal(9);
                  var8 = var8._iadd(Py.newInteger(1));
                  var1.setlocal(9, var8);
                  var1.setline(1602);
                  var1.setline(1602);
                  var11 = var1.getlocal(10).__getitem__(var1.getlocal(14));
                  var1.f_lasti = 3;
                  var3 = new Object[6];
                  var1.f_savedlocals = var3;
                  return var11;
               }

               var1.setline(1605);
               var8 = var1.getlocal(2)._sub(Py.newInteger(1));
               var1.setlocal(8, var8);
               var3 = null;
               var1.setline(1606);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(1607);
                  var8 = var1.getlocal(7).__getattr__("next").__call__(var2);
                  var4 = Py.unpackSequence(var8, 3);
                  var5 = var4[0];
                  var1.setlocal(12, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(11, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(1609);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(1610);
                     var8 = var1.getlocal(2)._sub(Py.newInteger(1));
                     var1.setlocal(8, var8);
                     var3 = null;
                  } else {
                     var1.setline(1612);
                     var8 = var1.getlocal(8);
                     var8 = var8._isub(Py.newInteger(1));
                     var1.setlocal(8, var8);
                  }

                  var1.setline(1613);
                  var1.setline(1613);
                  var6 = new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(11)};
                  var13 = new PyTuple(var6);
                  Arrays.fill(var6, (Object)null);
                  var1.f_lasti = 4;
                  var3 = new Object[6];
                  var1.f_savedlocals = var3;
                  return var13;
               }

               var1.setline(1580);
               if (!var1.getglobal("True").__nonzero__()) {
                  break label169;
               }

               var1.setline(1584);
               var6 = new PyObject[]{Py.newInteger(0), null};
               var4 = new PyObject[]{var1.getglobal("None")};
               var12 = new PyList(var4);
               Arrays.fill(var4, (Object)null);
               var6[1] = var12._mul(var1.getlocal(2));
               var13 = new PyTuple(var6);
               Arrays.fill(var6, (Object)null);
               var7 = var13;
               var4 = Py.unpackSequence(var7, 2);
               var5 = var4[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(10, var5);
               var5 = null;
               var3 = null;
               var1.setline(1585);
               var8 = var1.getglobal("False");
               var1.setlocal(11, var8);
               var3 = null;
               break;
            case 3:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var11 = (PyObject)var10000;
               var1.setline(1603);
               var8 = var1.getlocal(8);
               var8 = var8._isub(Py.newInteger(1));
               var1.setlocal(8, var8);
               var1.setline(1599);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(1600);
                  var8 = var1.getlocal(9)._mod(var1.getlocal(2));
                  var1.setlocal(14, var8);
                  var3 = null;
                  var1.setline(1601);
                  var8 = var1.getlocal(9);
                  var8 = var8._iadd(Py.newInteger(1));
                  var1.setlocal(9, var8);
                  var1.setline(1602);
                  var1.setline(1602);
                  var11 = var1.getlocal(10).__getitem__(var1.getlocal(14));
                  var1.f_lasti = 3;
                  var3 = new Object[6];
                  var1.f_savedlocals = var3;
                  return var11;
               }

               var1.setline(1605);
               var8 = var1.getlocal(2)._sub(Py.newInteger(1));
               var1.setlocal(8, var8);
               var3 = null;
               var1.setline(1606);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(1607);
                  var8 = var1.getlocal(7).__getattr__("next").__call__(var2);
                  var4 = Py.unpackSequence(var8, 3);
                  var5 = var4[0];
                  var1.setlocal(12, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(11, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(1609);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(1610);
                     var8 = var1.getlocal(2)._sub(Py.newInteger(1));
                     var1.setlocal(8, var8);
                     var3 = null;
                  } else {
                     var1.setline(1612);
                     var8 = var1.getlocal(8);
                     var8 = var8._isub(Py.newInteger(1));
                     var1.setlocal(8, var8);
                  }

                  var1.setline(1613);
                  var1.setline(1613);
                  var6 = new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(11)};
                  var13 = new PyTuple(var6);
                  Arrays.fill(var6, (Object)null);
                  var1.f_lasti = 4;
                  var3 = new Object[6];
                  var1.f_savedlocals = var3;
                  return var13;
               }

               var1.setline(1580);
               if (!var1.getglobal("True").__nonzero__()) {
                  break label169;
               }

               var1.setline(1584);
               var6 = new PyObject[]{Py.newInteger(0), null};
               var4 = new PyObject[]{var1.getglobal("None")};
               var12 = new PyList(var4);
               Arrays.fill(var4, (Object)null);
               var6[1] = var12._mul(var1.getlocal(2));
               var13 = new PyTuple(var6);
               Arrays.fill(var6, (Object)null);
               var7 = var13;
               var4 = Py.unpackSequence(var7, 2);
               var5 = var4[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(10, var5);
               var5 = null;
               var3 = null;
               var1.setline(1585);
               var8 = var1.getglobal("False");
               var1.setlocal(11, var8);
               var3 = null;
               break;
            case 4:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var11 = (PyObject)var10000;
               var1.setline(1606);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(1607);
                  var8 = var1.getlocal(7).__getattr__("next").__call__(var2);
                  var4 = Py.unpackSequence(var8, 3);
                  var5 = var4[0];
                  var1.setlocal(12, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(11, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(1609);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(1610);
                     var8 = var1.getlocal(2)._sub(Py.newInteger(1));
                     var1.setlocal(8, var8);
                     var3 = null;
                  } else {
                     var1.setline(1612);
                     var8 = var1.getlocal(8);
                     var8 = var8._isub(Py.newInteger(1));
                     var1.setlocal(8, var8);
                  }

                  var1.setline(1613);
                  var1.setline(1613);
                  var6 = new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(11)};
                  var13 = new PyTuple(var6);
                  Arrays.fill(var6, (Object)null);
                  var1.f_lasti = 4;
                  var3 = new Object[6];
                  var1.f_savedlocals = var3;
                  return var13;
               }

               var1.setline(1580);
               if (!var1.getglobal("True").__nonzero__()) {
                  break label169;
               }

               var1.setline(1584);
               var6 = new PyObject[]{Py.newInteger(0), null};
               var4 = new PyObject[]{var1.getglobal("None")};
               var12 = new PyList(var4);
               Arrays.fill(var4, (Object)null);
               var6[1] = var12._mul(var1.getlocal(2));
               var13 = new PyTuple(var6);
               Arrays.fill(var6, (Object)null);
               var7 = var13;
               var4 = Py.unpackSequence(var7, 2);
               var5 = var4[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(10, var5);
               var5 = null;
               var3 = null;
               var1.setline(1585);
               var8 = var1.getglobal("False");
               var1.setlocal(11, var8);
               var3 = null;
         }

         label168:
         while(true) {
            while(true) {
               var1.setline(1586);
               var8 = var1.getlocal(11);
               var11 = var8._is(var1.getglobal("False"));
               var3 = null;
               if (!var11.__nonzero__()) {
                  var1.setline(1593);
                  var8 = var1.getlocal(9);
                  var11 = var8._gt(var1.getlocal(2));
                  var3 = null;
                  if (var11.__nonzero__()) {
                     var1.setline(1594);
                     var1.setline(1594);
                     var6 = new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None")};
                     var13 = new PyTuple(var6);
                     Arrays.fill(var6, (Object)null);
                     var1.f_lasti = 2;
                     var3 = new Object[6];
                     var1.f_savedlocals = var3;
                     return var13;
                  }

                  var1.setline(1597);
                  var8 = var1.getlocal(9);
                  var1.setlocal(8, var8);
                  var3 = null;
                  var1.setline(1598);
                  var10 = Py.newInteger(0);
                  var1.setlocal(9, var10);
                  var3 = null;
                  var1.setline(1599);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1600);
                     var8 = var1.getlocal(9)._mod(var1.getlocal(2));
                     var1.setlocal(14, var8);
                     var3 = null;
                     var1.setline(1601);
                     var8 = var1.getlocal(9);
                     var8 = var8._iadd(Py.newInteger(1));
                     var1.setlocal(9, var8);
                     var1.setline(1602);
                     var1.setline(1602);
                     var11 = var1.getlocal(10).__getitem__(var1.getlocal(14));
                     var1.f_lasti = 3;
                     var3 = new Object[6];
                     var1.f_savedlocals = var3;
                     return var11;
                  }

                  var1.setline(1605);
                  var8 = var1.getlocal(2)._sub(Py.newInteger(1));
                  var1.setlocal(8, var8);
                  var3 = null;
                  var1.setline(1606);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1607);
                     var8 = var1.getlocal(7).__getattr__("next").__call__(var2);
                     var4 = Py.unpackSequence(var8, 3);
                     var5 = var4[0];
                     var1.setlocal(12, var5);
                     var5 = null;
                     var5 = var4[1];
                     var1.setlocal(13, var5);
                     var5 = null;
                     var5 = var4[2];
                     var1.setlocal(11, var5);
                     var5 = null;
                     var3 = null;
                     var1.setline(1609);
                     if (var1.getlocal(11).__nonzero__()) {
                        var1.setline(1610);
                        var8 = var1.getlocal(2)._sub(Py.newInteger(1));
                        var1.setlocal(8, var8);
                        var3 = null;
                     } else {
                        var1.setline(1612);
                        var8 = var1.getlocal(8);
                        var8 = var8._isub(Py.newInteger(1));
                        var1.setlocal(8, var8);
                     }

                     var1.setline(1613);
                     var1.setline(1613);
                     var6 = new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(11)};
                     var13 = new PyTuple(var6);
                     Arrays.fill(var6, (Object)null);
                     var1.f_lasti = 4;
                     var3 = new Object[6];
                     var1.f_savedlocals = var3;
                     return var13;
                  }

                  var1.setline(1580);
                  if (!var1.getglobal("True").__nonzero__()) {
                     break label168;
                  }

                  var1.setline(1584);
                  var6 = new PyObject[]{Py.newInteger(0), null};
                  var4 = new PyObject[]{var1.getglobal("None")};
                  var12 = new PyList(var4);
                  Arrays.fill(var4, (Object)null);
                  var6[1] = var12._mul(var1.getlocal(2));
                  var13 = new PyTuple(var6);
                  Arrays.fill(var6, (Object)null);
                  var7 = var13;
                  var4 = Py.unpackSequence(var7, 2);
                  var5 = var4[0];
                  var1.setlocal(9, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(10, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(1585);
                  var8 = var1.getglobal("False");
                  var1.setlocal(11, var8);
                  var3 = null;
               } else {
                  var1.setline(1587);
                  var8 = var1.getlocal(7).__getattr__("next").__call__(var2);
                  var4 = Py.unpackSequence(var8, 3);
                  var5 = var4[0];
                  var1.setlocal(12, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(13, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(11, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(1588);
                  var8 = var1.getlocal(9)._mod(var1.getlocal(2));
                  var1.setlocal(14, var8);
                  var3 = null;
                  var1.setline(1589);
                  var6 = new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(11)};
                  var13 = new PyTuple(var6);
                  Arrays.fill(var6, (Object)null);
                  var7 = var13;
                  var1.getlocal(10).__setitem__((PyObject)var1.getlocal(14), var7);
                  var3 = null;
                  var1.setline(1590);
                  var8 = var1.getlocal(9);
                  var8 = var8._iadd(Py.newInteger(1));
                  var1.setlocal(9, var8);
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _make_line$36(PyFrame var1, ThreadState var2) {
      var1.setline(1416);
      PyString.fromInterned("Returns line of text with user's change markup and line formatting.\n\n        lines -- list of lines from the ndiff generator to produce a line of\n                 text from.  When producing the line of text to return, the\n                 lines used are removed from this list.\n        format_key -- '+' return first line in list with \"add\" markup around\n                          the entire line.\n                      '-' return first line in list with \"delete\" markup around\n                          the entire line.\n                      '?' return first line in list with add/delete/change\n                          intraline markup (indices obtained from second line)\n                      None return first line in list with no markup\n        side -- indice into the num_lines list (0=from,1=to)\n        num_lines -- from/to current line number.  This is NOT intended to be a\n                     passed parameter.  It is present as a keyword argument to\n                     maintain memory of the current line numbers between calls\n                     of this function.\n\n        Note, this function is purposefully not defined at the module scope so\n        that data it needs from its parent function (within whose context it\n        is defined) does not need to be of module scope.\n        ");
      var1.setline(1417);
      PyObject var10000 = var1.getlocal(3);
      PyObject var3 = var1.getlocal(2);
      PyObject var4 = var10000;
      PyObject var5 = var4.__getitem__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setitem__(var3, var5);
      var1.setline(1420);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyTuple var10;
      if (var10000.__nonzero__()) {
         var1.setline(1421);
         var10 = new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(var1.getlocal(2)), var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(1423);
         var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("?"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1424);
            PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)), var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0))});
            PyObject[] var12 = Py.unpackSequence(var11, 2);
            PyObject var6 = var12[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal(5, var6);
            var6 = null;
            var4 = null;
            var1.setline(1426);
            PyList var13 = new PyList(Py.EmptyObjects);
            var1.setlocal(6, var13);
            var4 = null;
            var1.setline(1427);
            PyObject[] var14 = new PyObject[]{var1.getlocal(6)};
            PyFunction var15 = new PyFunction(var1.f_globals, var14, record_sub_info$37, (PyObject)null);
            var1.setlocal(7, var15);
            var4 = null;
            var1.setline(1430);
            var1.getderef(0).__getattr__("sub").__call__(var2, var1.getlocal(7), var1.getlocal(5));
            var1.setline(1433);
            var4 = var1.getlocal(6).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1)).__iter__();

            while(true) {
               var1.setline(1433);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(1435);
                  var4 = var1.getlocal(4).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
                  var1.setlocal(4, var4);
                  var4 = null;
                  break;
               }

               PyObject[] var16 = Py.unpackSequence(var5, 2);
               PyObject var7 = var16[0];
               var1.setlocal(8, var7);
               var7 = null;
               var7 = var16[1];
               PyObject[] var8 = Py.unpackSequence(var7, 2);
               PyObject var9 = var8[0];
               var1.setlocal(9, var9);
               var9 = null;
               var9 = var8[1];
               var1.setlocal(10, var9);
               var9 = null;
               var7 = null;
               var1.setline(1434);
               var6 = var1.getlocal(4).__getslice__(Py.newInteger(0), var1.getlocal(9), (PyObject)null)._add(PyString.fromInterned("\u0000"))._add(var1.getlocal(8))._add(var1.getlocal(4).__getslice__(var1.getlocal(9), var1.getlocal(10), (PyObject)null))._add(PyString.fromInterned("\u0001"))._add(var1.getlocal(4).__getslice__(var1.getlocal(10), (PyObject)null, (PyObject)null));
               var1.setlocal(4, var6);
               var6 = null;
            }
         } else {
            var1.setline(1438);
            var4 = var1.getlocal(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1441);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(1442);
               PyString var17 = PyString.fromInterned(" ");
               var1.setlocal(4, var17);
               var4 = null;
            }

            var1.setline(1444);
            var4 = PyString.fromInterned("\u0000")._add(var1.getlocal(1))._add(var1.getlocal(4))._add(PyString.fromInterned("\u0001"));
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(1448);
         var10 = new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(var1.getlocal(2)), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var10;
      }
   }

   public PyObject record_sub_info$37(PyFrame var1, ThreadState var2) {
      var1.setline(1428);
      var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getattr__("span").__call__(var2)})));
      var1.setline(1429);
      PyObject var3 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _line_iterator$38(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject[] var4;
      PyObject[] var8;
      PyObject var11;
      PyObject var13;
      PyTuple var16;
      label154: {
         label160: {
            PyObject var5;
            PyTuple var10;
            Object var10000;
            switch (var1.f_lasti) {
               case 0:
               default:
                  var1.setline(1463);
                  PyString.fromInterned("Yields from/to lines of text with a change indication.\n\n        This function is an iterator.  It itself pulls lines from a\n        differencing iterator, processes them and yields them.  When it can\n        it yields both a \"from\" and a \"to\" line, otherwise it will yield one\n        or the other.  In addition to yielding the lines of from/to text, a\n        boolean flag is yielded to indicate if the text line(s) have\n        differences in them.\n\n        Note, this function is purposefully not defined at the module scope so\n        that data it needs from its parent function (within whose context it\n        is defined) does not need to be of module scope.\n        ");
                  var1.setline(1464);
                  var8 = Py.EmptyObjects;
                  PyList var15 = new PyList(var8);
                  Arrays.fill(var8, (Object)null);
                  PyList var9 = var15;
                  var1.setlocal(0, var9);
                  var3 = null;
                  var1.setline(1465);
                  var8 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
                  var16 = new PyTuple(var8);
                  Arrays.fill(var8, (Object)null);
                  var10 = var16;
                  var4 = Py.unpackSequence(var10, 2);
                  var5 = var4[0];
                  var1.setlocal(1, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(2, var5);
                  var5 = null;
                  var3 = null;
                  break;
               case 1:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 2:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 3:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 4:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 5:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 6:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 7:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 8:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break;
               case 9:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break label160;
               case 10:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
                  break label154;
               case 11:
                  var3 = var1.f_savedlocals;
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var13 = (PyObject)var10000;
            }

            var1.setline(1466);
            if (!var1.getglobal("True").__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            label145:
            while(true) {
               var1.setline(1470);
               var11 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
               var13 = var11._lt(Py.newInteger(4));
               var3 = null;
               if (!var13.__nonzero__()) {
                  var1.setline(1475);
                  var13 = PyString.fromInterned("").__getattr__("join");
                  PyList var10002 = new PyList();
                  var11 = var10002.__getattr__("append");
                  var1.setlocal(4, var11);
                  var3 = null;
                  var1.setline(1475);
                  var11 = var1.getlocal(0).__iter__();

                  while(true) {
                     var1.setline(1475);
                     PyObject var7 = var11.__iternext__();
                     if (var7 == null) {
                        var1.setline(1475);
                        var1.dellocal(4);
                        var11 = var13.__call__((ThreadState)var2, (PyObject)var10002);
                        var1.setlocal(3, var11);
                        var3 = null;
                        var1.setline(1476);
                        if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X")).__nonzero__()) {
                           var1.setline(1480);
                           var11 = var1.getlocal(1);
                           var1.setlocal(2, var11);
                           var3 = null;
                        } else {
                           var1.setline(1481);
                           if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-?+?")).__nonzero__()) {
                              var1.setline(1483);
                              var1.setline(1483);
                              var8 = new PyObject[]{var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(0)), var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(1)), var1.getglobal("True")};
                              var16 = new PyTuple(var8);
                              Arrays.fill(var8, (Object)null);
                              var1.f_lasti = 1;
                              var3 = new Object[6];
                              var1.f_savedlocals = var3;
                              return var16;
                           }

                           var1.setline(1485);
                           if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--++")).__nonzero__()) {
                              var1.setline(1488);
                              var11 = var1.getlocal(1);
                              var11 = var11._isub(Py.newInteger(1));
                              var1.setlocal(1, var11);
                              var1.setline(1489);
                              var1.setline(1489);
                              var8 = new PyObject[]{var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("-"), (PyObject)Py.newInteger(0)), var1.getglobal("None"), var1.getglobal("True")};
                              var16 = new PyTuple(var8);
                              Arrays.fill(var8, (Object)null);
                              var1.f_lasti = 2;
                              var3 = new Object[6];
                              var1.f_savedlocals = var3;
                              return var16;
                           }

                           var1.setline(1491);
                           var13 = var1.getlocal(3).__getattr__("startswith");
                           var8 = new PyObject[]{PyString.fromInterned("--?+"), PyString.fromInterned("--+"), PyString.fromInterned("- ")};
                           PyTuple var14 = new PyTuple(var8);
                           Arrays.fill(var8, (Object)null);
                           if (var13.__call__((ThreadState)var2, (PyObject)var14).__nonzero__()) {
                              var1.setline(1494);
                              var8 = new PyObject[]{var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("-"), (PyObject)Py.newInteger(0)), var1.getglobal("None")};
                              var16 = new PyTuple(var8);
                              Arrays.fill(var8, (Object)null);
                              var10 = var16;
                              var4 = Py.unpackSequence(var10, 2);
                              var5 = var4[0];
                              var1.setlocal(6, var5);
                              var5 = null;
                              var5 = var4[1];
                              var1.setlocal(7, var5);
                              var5 = null;
                              var3 = null;
                              var1.setline(1495);
                              var8 = new PyObject[]{var1.getlocal(1)._sub(Py.newInteger(1)), Py.newInteger(0)};
                              var16 = new PyTuple(var8);
                              Arrays.fill(var8, (Object)null);
                              var10 = var16;
                              var4 = Py.unpackSequence(var10, 2);
                              var5 = var4[0];
                              var1.setlocal(2, var5);
                              var5 = null;
                              var5 = var4[1];
                              var1.setlocal(1, var5);
                              var5 = null;
                              var3 = null;
                           } else {
                              var1.setline(1496);
                              if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-+?")).__nonzero__()) {
                                 var1.setline(1498);
                                 var1.setline(1498);
                                 var8 = new PyObject[]{var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0)), var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(1)), var1.getglobal("True")};
                                 var16 = new PyTuple(var8);
                                 Arrays.fill(var8, (Object)null);
                                 var1.f_lasti = 3;
                                 var3 = new Object[6];
                                 var1.f_savedlocals = var3;
                                 return var16;
                              }

                              var1.setline(1500);
                              if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-?+")).__nonzero__()) {
                                 var1.setline(1502);
                                 var1.setline(1502);
                                 var8 = new PyObject[]{var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("?"), (PyObject)Py.newInteger(0)), var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(1)), var1.getglobal("True")};
                                 var16 = new PyTuple(var8);
                                 Arrays.fill(var8, (Object)null);
                                 var1.f_lasti = 4;
                                 var3 = new Object[6];
                                 var1.f_savedlocals = var3;
                                 return var16;
                              }

                              var1.setline(1504);
                              if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-")).__nonzero__()) {
                                 var1.setline(1506);
                                 var11 = var1.getlocal(1);
                                 var11 = var11._isub(Py.newInteger(1));
                                 var1.setlocal(1, var11);
                                 var1.setline(1507);
                                 var1.setline(1507);
                                 var8 = new PyObject[]{var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("-"), (PyObject)Py.newInteger(0)), var1.getglobal("None"), var1.getglobal("True")};
                                 var16 = new PyTuple(var8);
                                 Arrays.fill(var8, (Object)null);
                                 var1.f_lasti = 5;
                                 var3 = new Object[6];
                                 var1.f_savedlocals = var3;
                                 return var16;
                              }

                              var1.setline(1509);
                              if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+--")).__nonzero__()) {
                                 var1.setline(1512);
                                 var11 = var1.getlocal(1);
                                 var11 = var11._iadd(Py.newInteger(1));
                                 var1.setlocal(1, var11);
                                 var1.setline(1513);
                                 var1.setline(1513);
                                 var8 = new PyObject[]{var1.getglobal("None"), var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("+"), (PyObject)Py.newInteger(1)), var1.getglobal("True")};
                                 var16 = new PyTuple(var8);
                                 Arrays.fill(var8, (Object)null);
                                 var1.f_lasti = 6;
                                 var3 = new Object[6];
                                 var1.f_savedlocals = var3;
                                 return var16;
                              }

                              var1.setline(1515);
                              var13 = var1.getlocal(3).__getattr__("startswith");
                              var8 = new PyObject[]{PyString.fromInterned("+ "), PyString.fromInterned("+-")};
                              var14 = new PyTuple(var8);
                              Arrays.fill(var8, (Object)null);
                              if (var13.__call__((ThreadState)var2, (PyObject)var14).__nonzero__()) {
                                 var1.setline(1517);
                                 var8 = new PyObject[]{var1.getglobal("None"), var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("+"), (PyObject)Py.newInteger(1))};
                                 var16 = new PyTuple(var8);
                                 Arrays.fill(var8, (Object)null);
                                 var10 = var16;
                                 var4 = Py.unpackSequence(var10, 2);
                                 var5 = var4[0];
                                 var1.setlocal(6, var5);
                                 var5 = null;
                                 var5 = var4[1];
                                 var1.setlocal(7, var5);
                                 var5 = null;
                                 var3 = null;
                                 var1.setline(1518);
                                 var8 = new PyObject[]{var1.getlocal(1)._add(Py.newInteger(1)), Py.newInteger(0)};
                                 var16 = new PyTuple(var8);
                                 Arrays.fill(var8, (Object)null);
                                 var10 = var16;
                                 var4 = Py.unpackSequence(var10, 2);
                                 var5 = var4[0];
                                 var1.setlocal(2, var5);
                                 var5 = null;
                                 var5 = var4[1];
                                 var1.setlocal(1, var5);
                                 var5 = null;
                                 var3 = null;
                              } else {
                                 var1.setline(1519);
                                 if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("+")).__nonzero__()) {
                                    var1.setline(1521);
                                    var11 = var1.getlocal(1);
                                    var11 = var11._iadd(Py.newInteger(1));
                                    var1.setlocal(1, var11);
                                    var1.setline(1522);
                                    var1.setline(1522);
                                    var8 = new PyObject[]{var1.getglobal("None"), var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("+"), (PyObject)Py.newInteger(1)), var1.getglobal("True")};
                                    var16 = new PyTuple(var8);
                                    Arrays.fill(var8, (Object)null);
                                    var1.f_lasti = 7;
                                    var3 = new Object[6];
                                    var1.f_savedlocals = var3;
                                    return var16;
                                 }

                                 var1.setline(1524);
                                 if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" ")).__nonzero__()) {
                                    var1.setline(1526);
                                    var1.setline(1526);
                                    var8 = new PyObject[]{var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0).__getslice__((PyObject)null, (PyObject)null, (PyObject)null), (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(0)), var1.getderef(1).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(1)), var1.getglobal("False")};
                                    var16 = new PyTuple(var8);
                                    Arrays.fill(var8, (Object)null);
                                    var1.f_lasti = 8;
                                    var3 = new Object[6];
                                    var1.f_savedlocals = var3;
                                    return var16;
                                 }
                              }
                           }
                        }
                        break label145;
                     }

                     var1.setlocal(5, var7);
                     var1.setline(1475);
                     var1.getlocal(4).__call__(var2, var1.getlocal(5).__getitem__(Py.newInteger(0)));
                  }
               }

               try {
                  var1.setline(1472);
                  var1.getlocal(0).__getattr__("append").__call__(var2, var1.getderef(0).__getattr__("next").__call__(var2));
               } catch (Throwable var6) {
                  PyException var12 = Py.setException(var6, var1);
                  if (!var12.match(var1.getglobal("StopIteration"))) {
                     throw var12;
                  }

                  var1.setline(1474);
                  var1.getlocal(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X"));
               }
            }
         }

         var1.setline(1530);
         var11 = var1.getlocal(2);
         var13 = var11._lt(Py.newInteger(0));
         var3 = null;
         if (var13.__nonzero__()) {
            var1.setline(1531);
            var11 = var1.getlocal(2);
            var11 = var11._iadd(Py.newInteger(1));
            var1.setlocal(2, var11);
            var1.setline(1532);
            var1.setline(1532);
            var8 = new PyObject[]{var1.getglobal("None"), null, null};
            var4 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("\n")};
            var16 = new PyTuple(var4);
            Arrays.fill(var4, (Object)null);
            var8[1] = var16;
            var8[2] = var1.getglobal("True");
            var16 = new PyTuple(var8);
            Arrays.fill(var8, (Object)null);
            var1.f_lasti = 9;
            var3 = new Object[6];
            var1.f_savedlocals = var3;
            return var16;
         }
      }

      var1.setline(1533);
      var11 = var1.getlocal(2);
      var13 = var11._gt(Py.newInteger(0));
      var3 = null;
      if (!var13.__nonzero__()) {
         var1.setline(1536);
         if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X")).__nonzero__()) {
            var1.setline(1537);
            throw Py.makeException(var1.getglobal("StopIteration"));
         } else {
            var1.setline(1539);
            var1.setline(1539);
            var8 = new PyObject[]{var1.getlocal(6), var1.getlocal(7), var1.getglobal("True")};
            var16 = new PyTuple(var8);
            Arrays.fill(var8, (Object)null);
            var1.f_lasti = 11;
            var3 = new Object[6];
            var1.f_savedlocals = var3;
            return var16;
         }
      } else {
         var1.setline(1534);
         var11 = var1.getlocal(2);
         var11 = var11._isub(Py.newInteger(1));
         var1.setlocal(2, var11);
         var1.setline(1535);
         var1.setline(1535);
         var8 = new PyObject[3];
         var4 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("\n")};
         var16 = new PyTuple(var4);
         Arrays.fill(var4, (Object)null);
         var8[0] = var16;
         var8[1] = var1.getglobal("None");
         var8[2] = var1.getglobal("True");
         var16 = new PyTuple(var8);
         Arrays.fill(var8, (Object)null);
         var1.f_lasti = 10;
         var3 = new Object[6];
         var1.f_savedlocals = var3;
         return var16;
      }
   }

   public PyObject _line_pair_iterator$39(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject[] var4;
      PyObject var5;
      PyObject var6;
      PyObject[] var7;
      PyObject var9;
      PyTuple var11;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1553);
            PyString.fromInterned("Yields from/to lines of text with a change indication.\n\n        This function is an iterator.  It itself pulls lines from the line\n        iterator.  Its difference from that iterator is that this function\n        always yields a pair of from/to text lines (with the change\n        indication).  If necessary it will collect single from/to lines\n        until it has a matching pair from/to pair to yield.\n\n        Note, this function is purposefully not defined at the module scope so\n        that data it needs from its parent function (within whose context it\n        is defined) does not need to be of module scope.\n        ");
            var1.setline(1554);
            var6 = var1.getderef(0).__call__(var2);
            var1.setlocal(0, var6);
            var3 = null;
            var1.setline(1555);
            var7 = new PyObject[2];
            var4 = Py.EmptyObjects;
            PyList var10 = new PyList(var4);
            Arrays.fill(var4, (Object)null);
            var7[0] = var10;
            var4 = Py.EmptyObjects;
            var10 = new PyList(var4);
            Arrays.fill(var4, (Object)null);
            var7[1] = var10;
            var11 = new PyTuple(var7);
            Arrays.fill(var7, (Object)null);
            PyTuple var8 = var11;
            var4 = Py.unpackSequence(var8, 2);
            var5 = var4[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      var1.setline(1556);
      if (!var1.getglobal("True").__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         while(true) {
            var1.setline(1558);
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var9 = var6._eq(Py.newInteger(0));
            var3 = null;
            if (!var9.__nonzero__()) {
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var9 = var6._eq(Py.newInteger(0));
               var3 = null;
            }

            if (!var9.__nonzero__()) {
               var1.setline(1565);
               var6 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var4 = Py.unpackSequence(var6, 2);
               var5 = var4[0];
               var1.setlocal(3, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(6, var5);
               var5 = null;
               var3 = null;
               var1.setline(1566);
               var6 = var1.getlocal(2).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var4 = Py.unpackSequence(var6, 2);
               var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(7, var5);
               var5 = null;
               var3 = null;
               var1.setline(1567);
               var1.setline(1567);
               var7 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), null};
               var9 = var1.getlocal(6);
               if (!var9.__nonzero__()) {
                  var9 = var1.getlocal(7);
               }

               var7[2] = var9;
               var11 = new PyTuple(var7);
               Arrays.fill(var7, (Object)null);
               var1.f_lasti = 1;
               var3 = new Object[6];
               var1.f_savedlocals = var3;
               return var11;
            }

            var1.setline(1559);
            var6 = var1.getlocal(0).__getattr__("next").__call__(var2);
            var4 = Py.unpackSequence(var6, 3);
            var5 = var4[0];
            var1.setlocal(3, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
            var1.setline(1560);
            var6 = var1.getlocal(3);
            var9 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            PyTuple var10002;
            if (var9.__nonzero__()) {
               var1.setline(1561);
               var9 = var1.getlocal(1).__getattr__("append");
               var7 = new PyObject[]{var1.getlocal(3), var1.getlocal(5)};
               var10002 = new PyTuple(var7);
               Arrays.fill(var7, (Object)null);
               var9.__call__((ThreadState)var2, (PyObject)var10002);
            }

            var1.setline(1562);
            var6 = var1.getlocal(4);
            var9 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            if (var9.__nonzero__()) {
               var1.setline(1563);
               var9 = var1.getlocal(2).__getattr__("append");
               var7 = new PyObject[]{var1.getlocal(4), var1.getlocal(5)};
               var10002 = new PyTuple(var7);
               Arrays.fill(var7, (Object)null);
               var9.__call__((ThreadState)var2, (PyObject)var10002);
            }
         }
      }
   }

   public PyObject HtmlDiff$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("For producing HTML side by side comparison with change highlights.\n\n    This class can be used to create an HTML table (or a complete HTML file\n    containing the table) showing a side by side, line by line comparison\n    of text with inter-line and intra-line change highlights.  The table can\n    be generated in either full or contextual difference mode.\n\n    The following methods are provided for HTML generation:\n\n    make_table -- generates HTML for a single side by side table\n    make_file -- generates complete HTML file with a single side by side table\n\n    See tools/scripts/diff.py for an example usage of this class.\n    "));
      var1.setline(1686);
      PyString.fromInterned("For producing HTML side by side comparison with change highlights.\n\n    This class can be used to create an HTML table (or a complete HTML file\n    containing the table) showing a side by side, line by line comparison\n    of text with inter-line and intra-line change highlights.  The table can\n    be generated in either full or contextual difference mode.\n\n    The following methods are provided for HTML generation:\n\n    make_table -- generates HTML for a single side by side table\n    make_file -- generates complete HTML file with a single side by side table\n\n    See tools/scripts/diff.py for an example usage of this class.\n    ");
      var1.setline(1688);
      PyObject var3 = var1.getname("_file_template");
      var1.setlocal("_file_template", var3);
      var3 = null;
      var1.setline(1689);
      var3 = var1.getname("_styles");
      var1.setlocal("_styles", var3);
      var3 = null;
      var1.setline(1690);
      var3 = var1.getname("_table_template");
      var1.setlocal("_table_template", var3);
      var3 = null;
      var1.setline(1691);
      var3 = var1.getname("_legend");
      var1.setlocal("_legend", var3);
      var3 = null;
      var1.setline(1692);
      PyInteger var4 = Py.newInteger(0);
      var1.setlocal("_default_prefix", var4);
      var3 = null;
      var1.setline(1694);
      PyObject[] var5 = new PyObject[]{Py.newInteger(8), var1.getname("None"), var1.getname("None"), var1.getname("IS_CHARACTER_JUNK")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$41, PyString.fromInterned("HtmlDiff instance initializer\n\n        Arguments:\n        tabsize -- tab stop spacing, defaults to 8.\n        wrapcolumn -- column number where lines are broken and wrapped,\n            defaults to None where lines are not wrapped.\n        linejunk,charjunk -- keyword arguments passed into ndiff() (used to by\n            HtmlDiff() to generate the side by side HTML differences).  See\n            ndiff() documentation for argument default values and descriptions.\n        "));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1711);
      var5 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("False"), Py.newInteger(5)};
      var6 = new PyFunction(var1.f_globals, var5, make_file$42, PyString.fromInterned("Returns HTML file of side by side comparison with change highlights\n\n        Arguments:\n        fromlines -- list of \"from\" lines\n        tolines -- list of \"to\" lines\n        fromdesc -- \"from\" file column header string\n        todesc -- \"to\" file column header string\n        context -- set to True for contextual differences (defaults to False\n            which shows full differences).\n        numlines -- number of context lines.  When context is set True,\n            controls number of lines displayed before and after the change.\n            When context is False, controls the number of lines to place\n            the \"next\" link anchors before the next change (so click of\n            \"next\" link jumps to just before the change).\n        "));
      var1.setlocal("make_file", var6);
      var3 = null;
      var1.setline(1735);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _tab_newline_replace$43, PyString.fromInterned("Returns from/to line lists with tabs expanded and newlines removed.\n\n        Instead of tab characters being replaced by the number of spaces\n        needed to fill in to the next tab stop, this function will fill\n        the space with tab characters.  This is done so that the difference\n        algorithms can identify changes in a file when tabs are replaced by\n        spaces and vice versa.  At the end of the HTML generation, the tab\n        characters will be replaced with a nonbreakable space.\n        "));
      var1.setlocal("_tab_newline_replace", var6);
      var3 = null;
      var1.setline(1758);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _split_line$45, PyString.fromInterned("Builds list of text lines by splitting text lines at wrap point\n\n        This function will determine if the input text line needs to be\n        wrapped (split) into separate lines.  If so, the first wrap point\n        will be determined and the first line appended to the output\n        text line list.  This function is used recursively to handle\n        the second part of the split line to further split it.\n        "));
      var1.setlocal("_split_line", var6);
      var3 = null;
      var1.setline(1813);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _line_wrapper$46, PyString.fromInterned("Returns iterator that splits (wraps) mdiff text lines"));
      var1.setlocal("_line_wrapper", var6);
      var3 = null;
      var1.setline(1841);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _collect_lines$47, PyString.fromInterned("Collects mdiff output into separate lists\n\n        Before storing the mdiff from/to data into a list, it is converted\n        into a single line of text with HTML markup.\n        "));
      var1.setlocal("_collect_lines", var6);
      var3 = null;
      var1.setline(1862);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _format_line$48, PyString.fromInterned("Returns HTML markup of \"from\" / \"to\" text lines\n\n        side -- 0 or 1 indicating \"from\" or \"to\" text\n        flag -- indicates if difference on line\n        linenum -- line number (used for line number column)\n        text -- line text to be marked up\n        "));
      var1.setlocal("_format_line", var6);
      var3 = null;
      var1.setline(1885);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _make_prefix$49, PyString.fromInterned("Create unique anchor prefixes"));
      var1.setlocal("_make_prefix", var6);
      var3 = null;
      var1.setline(1896);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _convert_flags$50, PyString.fromInterned("Makes list of \"next\" links"));
      var1.setlocal("_convert_flags", var6);
      var3 = null;
      var1.setline(1943);
      var5 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("False"), Py.newInteger(5)};
      var6 = new PyFunction(var1.f_globals, var5, make_table$51, PyString.fromInterned("Returns HTML table of side by side comparison with change highlights\n\n        Arguments:\n        fromlines -- list of \"from\" lines\n        tolines -- list of \"to\" lines\n        fromdesc -- \"from\" file column header string\n        todesc -- \"to\" file column header string\n        context -- set to True for contextual differences (defaults to False\n            which shows full differences).\n        numlines -- number of context lines.  When context is set True,\n            controls number of lines displayed before and after the change.\n            When context is False, controls the number of lines to place\n            the \"next\" link anchors before the next change (so click of\n            \"next\" link jumps to just before the change).\n        "));
      var1.setlocal("make_table", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(1705);
      PyString.fromInterned("HtmlDiff instance initializer\n\n        Arguments:\n        tabsize -- tab stop spacing, defaults to 8.\n        wrapcolumn -- column number where lines are broken and wrapped,\n            defaults to None where lines are not wrapped.\n        linejunk,charjunk -- keyword arguments passed into ndiff() (used to by\n            HtmlDiff() to generate the side by side HTML differences).  See\n            ndiff() documentation for argument default values and descriptions.\n        ");
      var1.setline(1706);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_tabsize", var3);
      var3 = null;
      var1.setline(1707);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_wrapcolumn", var3);
      var3 = null;
      var1.setline(1708);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_linejunk", var3);
      var3 = null;
      var1.setline(1709);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_charjunk", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_file$42(PyFrame var1, ThreadState var2) {
      var1.setline(1727);
      PyString.fromInterned("Returns HTML file of side by side comparison with change highlights\n\n        Arguments:\n        fromlines -- list of \"from\" lines\n        tolines -- list of \"to\" lines\n        fromdesc -- \"from\" file column header string\n        todesc -- \"to\" file column header string\n        context -- set to True for contextual differences (defaults to False\n            which shows full differences).\n        numlines -- number of context lines.  When context is set True,\n            controls number of lines displayed before and after the change.\n            When context is False, controls the number of lines to place\n            the \"next\" link anchors before the next change (so click of\n            \"next\" link jumps to just before the change).\n        ");
      var1.setline(1729);
      PyObject var10000 = var1.getlocal(0).__getattr__("_file_template");
      PyObject var10001 = var1.getglobal("dict");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("_styles"), var1.getlocal(0).__getattr__("_legend"), null};
      PyObject var10003 = var1.getlocal(0).__getattr__("make_table");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      String[] var5 = new String[]{"context", "numlines"};
      var10003 = var10003.__call__(var2, var4, var5);
      var4 = null;
      var3[2] = var10003;
      String[] var7 = new String[]{"styles", "legend", "table"};
      var10001 = var10001.__call__(var2, var3, var7);
      var3 = null;
      PyObject var6 = var10000._mod(var10001);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _tab_newline_replace$43(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(1744);
      PyString.fromInterned("Returns from/to line lists with tabs expanded and newlines removed.\n\n        Instead of tab characters being replaced by the number of spaces\n        needed to fill in to the next tab stop, this function will fill\n        the space with tab characters.  This is done so that the difference\n        algorithms can identify changes in a file when tabs are replaced by\n        spaces and vice versa.  At the end of the HTML generation, the tab\n        characters will be replaced with a nonbreakable space.\n        ");
      var1.setline(1745);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = expand_tabs$44;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1754);
      PyList var10000 = new PyList();
      PyObject var6 = var10000.__getattr__("append");
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1754);
      var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1754);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1754);
            var1.dellocal(4);
            PyList var7 = var10000;
            var1.setlocal(1, var7);
            var3 = null;
            var1.setline(1755);
            var10000 = new PyList();
            var6 = var10000.__getattr__("append");
            var1.setlocal(6, var6);
            var3 = null;
            var1.setline(1755);
            var6 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(1755);
               var4 = var6.__iternext__();
               if (var4 == null) {
                  var1.setline(1755);
                  var1.dellocal(6);
                  var7 = var10000;
                  var1.setlocal(2, var7);
                  var3 = null;
                  var1.setline(1756);
                  PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setlocal(5, var4);
               var1.setline(1755);
               var1.getlocal(6).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(5)));
            }
         }

         var1.setlocal(5, var4);
         var1.setline(1754);
         var1.getlocal(4).__call__(var2, var1.getlocal(3).__call__(var2, var1.getlocal(5)));
      }
   }

   public PyObject expand_tabs$44(PyFrame var1, ThreadState var2) {
      var1.setline(1747);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("\u0000"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1749);
      var3 = var1.getlocal(0).__getattr__("expandtabs").__call__(var2, var1.getderef(0).__getattr__("_tabsize"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1752);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("\t"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1753);
      var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000"), (PyObject)PyString.fromInterned(" ")).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _split_line$45(PyFrame var1, ThreadState var2) {
      var1.setline(1766);
      PyString.fromInterned("Builds list of text lines by splitting text lines at wrap point\n\n        This function will determine if the input text line needs to be\n        wrapped (split) into separate lines.  If so, the first wrap point\n        will be determined and the first line appended to the output\n        text line list.  This function is used recursively to handle\n        the second part of the split line to further split it.\n        ");
      var1.setline(1768);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(1769);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         var1.setline(1770);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1773);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1774);
         var3 = var1.getlocal(0).__getattr__("_wrapcolumn");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1775);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._le(var1.getlocal(5));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(4)._sub(var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000"))._mul(Py.newInteger(3)));
            var10000 = var3._le(var1.getlocal(5));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1776);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
            var1.setline(1777);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(1781);
            PyInteger var4 = Py.newInteger(0);
            var1.setlocal(6, var4);
            var3 = null;
            var1.setline(1782);
            var4 = Py.newInteger(0);
            var1.setlocal(7, var4);
            var3 = null;
            var1.setline(1783);
            PyString var5 = PyString.fromInterned("");
            var1.setlocal(8, var5);
            var3 = null;

            while(true) {
               var1.setline(1784);
               var3 = var1.getlocal(7);
               var10000 = var3._lt(var1.getlocal(5));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(6);
                  var10000 = var3._lt(var1.getlocal(4));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(1797);
                  var3 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(6), (PyObject)null);
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(1798);
                  var3 = var1.getlocal(3).__getslice__(var1.getlocal(6), (PyObject)null, (PyObject)null);
                  var1.setlocal(10, var3);
                  var3 = null;
                  var1.setline(1803);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1804);
                     var3 = var1.getlocal(9)._add(PyString.fromInterned("\u0001"));
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(1805);
                     var3 = PyString.fromInterned("\u0000")._add(var1.getlocal(8))._add(var1.getlocal(10));
                     var1.setlocal(10, var3);
                     var3 = null;
                  }

                  var1.setline(1808);
                  var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(9)})));
                  var1.setline(1811);
                  var1.getlocal(0).__getattr__("_split_line").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned(">"), (PyObject)var1.getlocal(10));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(1785);
               var3 = var1.getlocal(3).__getitem__(var1.getlocal(6));
               var10000 = var3._eq(PyString.fromInterned("\u0000"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1786);
                  var3 = var1.getlocal(6);
                  var3 = var3._iadd(Py.newInteger(1));
                  var1.setlocal(6, var3);
                  var1.setline(1787);
                  var3 = var1.getlocal(3).__getitem__(var1.getlocal(6));
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(1788);
                  var3 = var1.getlocal(6);
                  var3 = var3._iadd(Py.newInteger(1));
                  var1.setlocal(6, var3);
               } else {
                  var1.setline(1789);
                  var3 = var1.getlocal(3).__getitem__(var1.getlocal(6));
                  var10000 = var3._eq(PyString.fromInterned("\u0001"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1790);
                     var3 = var1.getlocal(6);
                     var3 = var3._iadd(Py.newInteger(1));
                     var1.setlocal(6, var3);
                     var1.setline(1791);
                     var5 = PyString.fromInterned("");
                     var1.setlocal(8, var5);
                     var3 = null;
                  } else {
                     var1.setline(1793);
                     var3 = var1.getlocal(6);
                     var3 = var3._iadd(Py.newInteger(1));
                     var1.setlocal(6, var3);
                     var1.setline(1794);
                     var3 = var1.getlocal(7);
                     var3 = var3._iadd(Py.newInteger(1));
                     var1.setlocal(7, var3);
                  }
               }
            }
         }
      }
   }

   public PyObject _line_wrapper$46(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var10;
      PyObject[] var11;
      PyTuple var12;
      PyObject var14;
      PyTuple var15;
      Object var10000;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1814);
            PyString.fromInterned("Returns iterator that splits (wraps) mdiff text lines");
            var1.setline(1817);
            var3 = var1.getlocal(1).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var14 = (PyObject)var10000;
            break;
         case 2:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var14 = (PyObject)var10000;
            var1.setline(1830);
            var14 = var1.getlocal(9);
            if (!var14.__nonzero__()) {
               var14 = var1.getlocal(10);
            }

            if (var14.__nonzero__()) {
               var1.setline(1831);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(1832);
                  var10 = var1.getlocal(9).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setlocal(2, var10);
                  var5 = null;
               } else {
                  var1.setline(1834);
                  var11 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(" ")};
                  var15 = new PyTuple(var11);
                  Arrays.fill(var11, (Object)null);
                  var12 = var15;
                  var1.setlocal(2, var12);
                  var5 = null;
               }

               var1.setline(1835);
               if (var1.getlocal(10).__nonzero__()) {
                  var1.setline(1836);
                  var10 = var1.getlocal(10).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  var1.setlocal(3, var10);
                  var5 = null;
               } else {
                  var1.setline(1838);
                  var11 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(" ")};
                  var15 = new PyTuple(var11);
                  Arrays.fill(var11, (Object)null);
                  var12 = var15;
                  var1.setlocal(3, var12);
                  var5 = null;
               }

               var1.setline(1839);
               var1.setline(1839);
               var11 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
               var15 = new PyTuple(var11);
               Arrays.fill(var11, (Object)null);
               var1.f_lasti = 2;
               var5 = new Object[10];
               var5[3] = var3;
               var5[4] = var4;
               var1.f_savedlocals = var5;
               return var15;
            }
      }

      do {
         var1.setline(1817);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var11 = Py.unpackSequence(var4, 3);
         PyObject var6 = var11[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var11[2];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(1819);
         var10 = var1.getlocal(4);
         var14 = var10._is(var1.getglobal("None"));
         var5 = null;
         if (var14.__nonzero__()) {
            var1.setline(1820);
            var1.setline(1820);
            var11 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
            var15 = new PyTuple(var11);
            Arrays.fill(var11, (Object)null);
            var1.f_lasti = 1;
            var5 = new Object[7];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var15;
         }

         var1.setline(1822);
         var11 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
         var15 = new PyTuple(var11);
         Arrays.fill(var11, (Object)null);
         var12 = var15;
         PyObject[] var13 = Py.unpackSequence(var12, 2);
         PyObject var7 = var13[0];
         PyObject[] var8 = Py.unpackSequence(var7, 2);
         PyObject var9 = var8[0];
         var1.setlocal(5, var9);
         var9 = null;
         var9 = var8[1];
         var1.setlocal(6, var9);
         var9 = null;
         var7 = null;
         var7 = var13[1];
         var8 = Py.unpackSequence(var7, 2);
         var9 = var8[0];
         var1.setlocal(7, var9);
         var9 = null;
         var9 = var8[1];
         var1.setlocal(8, var9);
         var9 = null;
         var7 = null;
         var5 = null;
         var1.setline(1825);
         var11 = new PyObject[2];
         var13 = Py.EmptyObjects;
         PyList var16 = new PyList(var13);
         Arrays.fill(var13, (Object)null);
         var11[0] = var16;
         var13 = Py.EmptyObjects;
         var16 = new PyList(var13);
         Arrays.fill(var13, (Object)null);
         var11[1] = var16;
         var15 = new PyTuple(var11);
         Arrays.fill(var11, (Object)null);
         var12 = var15;
         var13 = Py.unpackSequence(var12, 2);
         var7 = var13[0];
         var1.setlocal(9, var7);
         var7 = null;
         var7 = var13[1];
         var1.setlocal(10, var7);
         var7 = null;
         var5 = null;
         var1.setline(1826);
         var1.getlocal(0).__getattr__("_split_line").__call__(var2, var1.getlocal(9), var1.getlocal(5), var1.getlocal(6));
         var1.setline(1827);
         var1.getlocal(0).__getattr__("_split_line").__call__(var2, var1.getlocal(10), var1.getlocal(7), var1.getlocal(8));
         var1.setline(1830);
         var14 = var1.getlocal(9);
         if (!var14.__nonzero__()) {
            var14 = var1.getlocal(10);
         }
      } while(!var14.__nonzero__());

      var1.setline(1831);
      if (var1.getlocal(9).__nonzero__()) {
         var1.setline(1832);
         var10 = var1.getlocal(9).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(2, var10);
         var5 = null;
      } else {
         var1.setline(1834);
         var11 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(" ")};
         var15 = new PyTuple(var11);
         Arrays.fill(var11, (Object)null);
         var12 = var15;
         var1.setlocal(2, var12);
         var5 = null;
      }

      var1.setline(1835);
      if (var1.getlocal(10).__nonzero__()) {
         var1.setline(1836);
         var10 = var1.getlocal(10).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setlocal(3, var10);
         var5 = null;
      } else {
         var1.setline(1838);
         var11 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(" ")};
         var15 = new PyTuple(var11);
         Arrays.fill(var11, (Object)null);
         var12 = var15;
         var1.setlocal(3, var12);
         var5 = null;
      }

      var1.setline(1839);
      var1.setline(1839);
      var11 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      var15 = new PyTuple(var11);
      Arrays.fill(var11, (Object)null);
      var1.f_lasti = 2;
      var5 = new Object[10];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var15;
   }

   public PyObject _collect_lines$47(PyFrame var1, ThreadState var2) {
      var1.setline(1846);
      PyString.fromInterned("Collects mdiff output into separate lists\n\n        Before storing the mdiff from/to data into a list, it is converted\n        into a single line of text with HTML markup.\n        ");
      var1.setline(1848);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
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
      var1.setline(1850);
      PyObject var8 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1850);
         PyObject var9 = var8.__iternext__();
         if (var9 == null) {
            var1.setline(1860);
            var3 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var10 = Py.unpackSequence(var9, 3);
         PyObject var6 = var10[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var10[2];
         var1.setlocal(7, var6);
         var6 = null;

         try {
            var1.setline(1853);
            PyObject var10000 = var1.getlocal(2).__getattr__("append");
            PyObject var10002 = var1.getlocal(0).__getattr__("_format_line");
            var10 = new PyObject[]{Py.newInteger(0), var1.getlocal(7)};
            String[] var12 = new String[0];
            var10002 = var10002._callextra(var10, var12, var1.getlocal(5), (PyObject)null);
            var5 = null;
            var10000.__call__(var2, var10002);
            var1.setline(1854);
            var10000 = var1.getlocal(3).__getattr__("append");
            var10002 = var1.getlocal(0).__getattr__("_format_line");
            var10 = new PyObject[]{Py.newInteger(1), var1.getlocal(7)};
            var12 = new String[0];
            var10002 = var10002._callextra(var10, var12, var1.getlocal(6), (PyObject)null);
            var5 = null;
            var10000.__call__(var2, var10002);
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (!var11.match(var1.getglobal("TypeError"))) {
               throw var11;
            }

            var1.setline(1857);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("None"));
            var1.setline(1858);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("None"));
         }

         var1.setline(1859);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
      }
   }

   public PyObject _format_line$48(PyFrame var1, ThreadState var2) {
      var1.setline(1869);
      PyString.fromInterned("Returns HTML markup of \"from\" / \"to\" text lines\n\n        side -- 0 or 1 indicating \"from\" or \"to\" text\n        flag -- indicates if difference on line\n        linenum -- line number (used for line number column)\n        text -- line text to be marked up\n        ");

      PyException var3;
      PyObject var6;
      try {
         var1.setline(1871);
         var6 = PyString.fromInterned("%d")._mod(var1.getlocal(3));
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(1872);
         var6 = PyString.fromInterned(" id=\"%s%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_prefix").__getitem__(var1.getlocal(1)), var1.getlocal(3)}));
         var1.setlocal(5, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("TypeError"))) {
            throw var3;
         }

         var1.setline(1875);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(5, var4);
         var4 = null;
      }

      var1.setline(1877);
      var6 = var1.getlocal(4).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1880);
      var6 = var1.getlocal(4).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("&nbsp;")).__getattr__("rstrip").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1882);
      var6 = PyString.fromInterned("<td class=\"diff_header\"%s>%s</td><td nowrap=\"nowrap\">%s</td>")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(3), var1.getlocal(4)}));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _make_prefix$49(PyFrame var1, ThreadState var2) {
      var1.setline(1886);
      PyString.fromInterned("Create unique anchor prefixes");
      var1.setline(1890);
      PyObject var3 = PyString.fromInterned("from%d_")._mod(var1.getglobal("HtmlDiff").__getattr__("_default_prefix"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1891);
      var3 = PyString.fromInterned("to%d_")._mod(var1.getglobal("HtmlDiff").__getattr__("_default_prefix"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1892);
      PyObject var10000 = var1.getglobal("HtmlDiff");
      String var6 = "_default_prefix";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.setline(1894);
      PyList var7 = new PyList(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.getlocal(0).__setattr__((String)"_prefix", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _convert_flags$50(PyFrame var1, ThreadState var2) {
      var1.setline(1897);
      PyString.fromInterned("Makes list of \"next\" links");
      var1.setline(1900);
      PyObject var3 = var1.getlocal(0).__getattr__("_prefix").__getitem__(Py.newInteger(1));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1903);
      var3 = (new PyList(new PyObject[]{PyString.fromInterned("")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1904);
      var3 = (new PyList(new PyObject[]{PyString.fromInterned("")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1905);
      PyTuple var8 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("False")});
      PyObject[] var4 = Py.unpackSequence(var8, 2);
      PyObject var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var3 = null;
      var1.setline(1906);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(11, var9);
      var3 = null;
      var1.setline(1907);
      var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(3)).__iter__();

      while(true) {
         var1.setline(1907);
         PyObject var7 = var3.__iternext__();
         if (var7 == null) {
            var1.setline(1925);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(1926);
               PyList var11 = new PyList(new PyObject[]{var1.getglobal("False")});
               var1.setlocal(3, var11);
               var3 = null;
               var1.setline(1927);
               var11 = new PyList(new PyObject[]{PyString.fromInterned("")});
               var1.setlocal(7, var11);
               var3 = null;
               var1.setline(1928);
               var11 = new PyList(new PyObject[]{PyString.fromInterned("")});
               var1.setlocal(8, var11);
               var3 = null;
               var1.setline(1929);
               var9 = Py.newInteger(0);
               var1.setlocal(11, var9);
               var3 = null;
               var1.setline(1930);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(1931);
                  var11 = new PyList(new PyObject[]{PyString.fromInterned("<td></td><td>&nbsp;No Differences Found&nbsp;</td>")});
                  var1.setlocal(1, var11);
                  var3 = null;
                  var1.setline(1932);
                  var3 = var1.getlocal(1);
                  var1.setlocal(2, var3);
                  var3 = null;
               } else {
                  var1.setline(1934);
                  var11 = new PyList(new PyObject[]{PyString.fromInterned("<td></td><td>&nbsp;Empty File&nbsp;</td>")});
                  var1.setlocal(1, var11);
                  var1.setlocal(2, var11);
               }
            }

            var1.setline(1936);
            if (var1.getlocal(3).__getitem__(Py.newInteger(0)).__not__().__nonzero__()) {
               var1.setline(1937);
               var3 = PyString.fromInterned("<a href=\"#difflib_chg_%s_0\">f</a>")._mod(var1.getlocal(6));
               var1.getlocal(8).__setitem__((PyObject)Py.newInteger(0), var3);
               var3 = null;
            }

            var1.setline(1939);
            var3 = PyString.fromInterned("<a href=\"#difflib_chg_%s_top\">t</a>")._mod(var1.getlocal(6));
            var1.getlocal(8).__setitem__(var1.getlocal(11), var3);
            var3 = null;
            var1.setline(1941);
            var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(8), var1.getlocal(7)});
            var1.f_lasti = -1;
            return var8;
         }

         PyObject[] var10 = Py.unpackSequence(var7, 2);
         PyObject var6 = var10[0];
         var1.setlocal(12, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(13, var6);
         var6 = null;
         var1.setline(1908);
         if (var1.getlocal(13).__nonzero__()) {
            var1.setline(1909);
            if (var1.getlocal(10).__not__().__nonzero__()) {
               var1.setline(1910);
               var5 = var1.getglobal("True");
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(1911);
               var5 = var1.getlocal(12);
               var1.setlocal(11, var5);
               var5 = null;
               var1.setline(1915);
               var5 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(0), var1.getlocal(12)._sub(var1.getlocal(5))})));
               var1.setlocal(12, var5);
               var5 = null;
               var1.setline(1916);
               var5 = PyString.fromInterned(" id=\"difflib_chg_%s_%d\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(9)}));
               var1.getlocal(7).__setitem__(var1.getlocal(12), var5);
               var5 = null;
               var1.setline(1919);
               var5 = var1.getlocal(9);
               var5 = var5._iadd(Py.newInteger(1));
               var1.setlocal(9, var5);
               var1.setline(1920);
               var5 = PyString.fromInterned("<a href=\"#difflib_chg_%s_%d\">n</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(9)}));
               var1.getlocal(8).__setitem__(var1.getlocal(11), var5);
               var5 = null;
            }
         } else {
            var1.setline(1923);
            var5 = var1.getglobal("False");
            var1.setlocal(10, var5);
            var5 = null;
         }
      }
   }

   public PyObject make_table$51(PyFrame var1, ThreadState var2) {
      var1.setline(1959);
      PyString.fromInterned("Returns HTML table of side by side comparison with change highlights\n\n        Arguments:\n        fromlines -- list of \"from\" lines\n        tolines -- list of \"to\" lines\n        fromdesc -- \"from\" file column header string\n        todesc -- \"to\" file column header string\n        context -- set to True for contextual differences (defaults to False\n            which shows full differences).\n        numlines -- number of context lines.  When context is set True,\n            controls number of lines displayed before and after the change.\n            When context is False, controls the number of lines to place\n            the \"next\" link anchors before the next change (so click of\n            \"next\" link jumps to just before the change).\n        ");
      var1.setline(1963);
      var1.getlocal(0).__getattr__("_make_prefix").__call__(var2);
      var1.setline(1967);
      PyObject var3 = var1.getlocal(0).__getattr__("_tab_newline_replace").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1970);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(1971);
         var3 = var1.getlocal(6);
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(1973);
         var3 = var1.getglobal("None");
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(1974);
      PyObject var10000 = var1.getglobal("_mdiff");
      PyObject[] var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(7), var1.getlocal(0).__getattr__("_linejunk"), var1.getlocal(0).__getattr__("_charjunk")};
      String[] var6 = new String[]{"linejunk", "charjunk"};
      var10000 = var10000.__call__(var2, var7, var6);
      var3 = null;
      var3 = var10000;
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1978);
      if (var1.getlocal(0).__getattr__("_wrapcolumn").__nonzero__()) {
         var1.setline(1979);
         var3 = var1.getlocal(0).__getattr__("_line_wrapper").__call__(var2, var1.getlocal(8));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1982);
      var3 = var1.getlocal(0).__getattr__("_collect_lines").__call__(var2, var1.getlocal(8));
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(1985);
      var10000 = var1.getlocal(0).__getattr__("_convert_flags");
      var7 = new PyObject[]{var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(5), var1.getlocal(6)};
      var3 = var10000.__call__(var2, var7);
      var4 = Py.unpackSequence(var3, 5);
      var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(11, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(12, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(13, var5);
      var5 = null;
      var3 = null;
      var1.setline(1988);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(14, var9);
      var3 = null;
      var1.setline(1989);
      var3 = PyString.fromInterned("            <tr><td class=\"diff_next\"%s>%s</td>%s")._add(PyString.fromInterned("<td class=\"diff_next\">%s</td>%s</tr>\n"));
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(1991);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(11))).__iter__();

      while(true) {
         var1.setline(1991);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(2000);
            var10000 = var1.getlocal(3);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(4);
            }

            if (var10000.__nonzero__()) {
               var1.setline(2001);
               var3 = PyString.fromInterned("<thead><tr>%s%s%s%s</tr></thead>")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("<th class=\"diff_next\"><br /></th>"), PyString.fromInterned("<th colspan=\"2\" class=\"diff_header\">%s</th>")._mod(var1.getlocal(3)), PyString.fromInterned("<th class=\"diff_next\"><br /></th>"), PyString.fromInterned("<th colspan=\"2\" class=\"diff_header\">%s</th>")._mod(var1.getlocal(4))}));
               var1.setlocal(17, var3);
               var3 = null;
            } else {
               var1.setline(2007);
               PyString var10 = PyString.fromInterned("");
               var1.setlocal(17, var10);
               var3 = null;
            }

            var1.setline(2009);
            var10000 = var1.getlocal(0).__getattr__("_table_template");
            PyObject var10001 = var1.getglobal("dict");
            var7 = new PyObject[]{PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(14)), var1.getlocal(17), var1.getlocal(0).__getattr__("_prefix").__getitem__(Py.newInteger(1))};
            var6 = new String[]{"data_rows", "header_row", "prefix"};
            var10001 = var10001.__call__(var2, var7, var6);
            var3 = null;
            var3 = var10000._mod(var10001);
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(2014);
            var3 = var1.getlocal(18).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000+"), (PyObject)PyString.fromInterned("<span class=\"diff_add\">")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000-"), (PyObject)PyString.fromInterned("<span class=\"diff_sub\">")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000^"), (PyObject)PyString.fromInterned("<span class=\"diff_chg\">")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0001"), (PyObject)PyString.fromInterned("</span>")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"), (PyObject)PyString.fromInterned("&nbsp;"));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(16, var8);
         var1.setline(1992);
         var5 = var1.getlocal(11).__getitem__(var1.getlocal(16));
         var10000 = var5._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1995);
            var5 = var1.getlocal(16);
            var10000 = var5._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1996);
               var1.getlocal(14).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("        </tbody>        \n        <tbody>\n"));
            }
         } else {
            var1.setline(1998);
            var1.getlocal(14).__getattr__("append").__call__(var2, var1.getlocal(15)._mod(new PyTuple(new PyObject[]{var1.getlocal(13).__getitem__(var1.getlocal(16)), var1.getlocal(12).__getitem__(var1.getlocal(16)), var1.getlocal(9).__getitem__(var1.getlocal(16)), var1.getlocal(12).__getitem__(var1.getlocal(16)), var1.getlocal(10).__getitem__(var1.getlocal(16))})));
         }
      }
   }

   public PyObject restore$52(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var11;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(2043);
            PyString.fromInterned("\n    Generate one of the two sequences that generated a delta.\n\n    Given a `delta` produced by `Differ.compare()` or `ndiff()`, extract\n    lines originating from file 1 or 2 (parameter `which`), stripping off line\n    prefixes.\n\n    Examples:\n\n    >>> diff = ndiff('one\\ntwo\\nthree\\n'.splitlines(1),\n    ...              'ore\\ntree\\nemu\\n'.splitlines(1))\n    >>> diff = list(diff)\n    >>> print ''.join(restore(diff, 1)),\n    one\n    two\n    three\n    >>> print ''.join(restore(diff, 2)),\n    ore\n    tree\n    emu\n    ");

            PyObject[] var8;
            try {
               var1.setline(2045);
               var8 = new PyObject[]{Py.newInteger(1), PyString.fromInterned("- "), Py.newInteger(2), PyString.fromInterned("+ ")};
               PyDictionary var12 = new PyDictionary(var8);
               Arrays.fill(var8, (Object)null);
               var3 = var12.__getitem__(var1.getglobal("int").__call__(var2, var1.getlocal(1)));
               var1.setlocal(2, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var7 = Py.setException(var6, var1);
               if (var7.match(var1.getglobal("KeyError"))) {
                  var1.setline(2047);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown delta choice (must be 1 or 2): %r")._mod(var1.getlocal(1)));
               }

               throw var7;
            }

            var1.setline(2049);
            var8 = new PyObject[]{PyString.fromInterned("  "), var1.getlocal(2)};
            PyTuple var13 = new PyTuple(var8);
            Arrays.fill(var8, (Object)null);
            PyTuple var9 = var13;
            var1.setlocal(3, var9);
            var3 = null;
            var1.setline(2050);
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

            var11 = (PyObject)var10000;
      }

      do {
         var1.setline(2050);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(2051);
         PyObject var10 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
         var11 = var10._in(var1.getlocal(3));
         var5 = null;
      } while(!var11.__nonzero__());

      var1.setline(2052);
      var1.setline(2052);
      var11 = var1.getlocal(4).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var11;
   }

   public PyObject _test$53(PyFrame var1, ThreadState var2) {
      var1.setline(2055);
      PyObject var3 = imp.importOne("doctest", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var3 = imp.importOne("difflib", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2056);
      var3 = var1.getlocal(0).__getattr__("testmod").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public difflib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"matches", "length"};
      _calculate_ratio$1 = Py.newCode(2, var2, var1, "_calculate_ratio", 41, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SequenceMatcher$2 = Py.newCode(0, var2, var1, "SequenceMatcher", 46, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "isjunk", "a", "b", "autojunk"};
      __init__$3 = Py.newCode(5, var2, var1, "__init__", 154, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b"};
      set_seqs$4 = Py.newCode(3, var2, var1, "set_seqs", 223, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a"};
      set_seq1$5 = Py.newCode(2, var2, var1, "set_seq1", 235, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b"};
      set_seq2$6 = Py.newCode(2, var2, var1, "set_seq2", 261, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "b", "b2j", "i", "elt", "indices", "junk", "isjunk", "popular", "n", "ntest", "idxs"};
      _SequenceMatcher__chain_b$7 = Py.newCode(1, var2, var1, "_SequenceMatcher__chain_b", 306, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "alo", "ahi", "blo", "bhi", "a", "b", "b2j", "isbjunk", "besti", "bestj", "bestsize", "j2len", "nothing", "i", "j2lenget", "newj2len", "j", "k"};
      find_longest_match$8 = Py.newCode(5, var2, var1, "find_longest_match", 350, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "la", "lb", "queue", "matching_blocks", "alo", "ahi", "blo", "bhi", "i", "j", "k", "x", "i1", "j1", "k1", "non_adjacent", "i2", "j2", "k2"};
      get_matching_blocks$9 = Py.newCode(1, var2, var1, "get_matching_blocks", 460, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j", "answer", "ai", "bj", "size", "tag"};
      get_opcodes$10 = Py.newCode(1, var2, var1, "get_opcodes", 531, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "codes", "tag", "i1", "i2", "j1", "j2", "nn", "group"};
      get_grouped_opcodes$11 = Py.newCode(2, var2, var1, "get_grouped_opcodes", 586, false, false, self, 11, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "matches"};
      ratio$12 = Py.newCode(1, var2, var1, "ratio", 636, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sum", "triple"};
      f$13 = Py.newCode(2, var2, var1, "<lambda>", 658, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullbcount", "elt", "avail", "availhas", "matches", "numb"};
      quick_ratio$14 = Py.newCode(1, var2, var1, "quick_ratio", 662, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "la", "lb"};
      real_quick_ratio$15 = Py.newCode(1, var2, var1, "real_quick_ratio", 691, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"word", "possibilities", "n", "cutoff", "result", "s", "x", "_[749_12]", "score"};
      get_close_matches$16 = Py.newCode(4, var2, var1, "get_close_matches", 703, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "ch", "i", "n"};
      _count_leading$17 = Py.newCode(2, var2, var1, "_count_leading", 751, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Differ$18 = Py.newCode(0, var2, var1, "Differ", 766, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "linejunk", "charjunk"};
      __init__$19 = Py.newCode(3, var2, var1, "__init__", 860, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "cruncher", "tag", "alo", "ahi", "blo", "bhi", "g", "line"};
      compare$20 = Py.newCode(3, var2, var1, "compare", 884, false, false, self, 20, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "tag", "x", "lo", "hi", "i"};
      _dump$21 = Py.newCode(5, var2, var1, "_dump", 925, false, false, self, 21, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "a", "alo", "ahi", "b", "blo", "bhi", "first", "second", "g", "line"};
      _plain_replace$22 = Py.newCode(7, var2, var1, "_plain_replace", 930, false, false, self, 22, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "a", "alo", "ahi", "b", "blo", "bhi", "best_ratio", "cutoff", "cruncher", "eqi", "eqj", "j", "bj", "i", "ai", "best_i", "best_j", "line", "aelt", "belt", "atags", "btags", "tag", "ai1", "ai2", "bj1", "bj2", "la", "lb"};
      _fancy_replace$23 = Py.newCode(7, var2, var1, "_fancy_replace", 945, false, false, self, 23, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "a", "alo", "ahi", "b", "blo", "bhi", "g", "line"};
      _fancy_helper$24 = Py.newCode(7, var2, var1, "_fancy_helper", 1043, false, false, self, 24, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "aline", "bline", "atags", "btags", "common"};
      _qformat$25 = Py.newCode(5, var2, var1, "_qformat", 1056, false, false, self, 25, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"line", "pat"};
      IS_LINE_JUNK$26 = Py.newCode(2, var2, var1, "IS_LINE_JUNK", 1108, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ch", "ws"};
      IS_CHARACTER_JUNK$27 = Py.newCode(2, var2, var1, "IS_CHARACTER_JUNK", 1124, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"start", "stop", "beginning", "length"};
      _format_range_unified$28 = Py.newCode(2, var2, var1, "_format_range_unified", 1147, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b", "fromfile", "tofile", "fromfiledate", "tofiledate", "n", "lineterm", "started", "group", "fromdate", "todate", "first", "last", "file1_range", "file2_range", "tag", "i1", "i2", "j1", "j2", "line"};
      unified_diff$29 = Py.newCode(8, var2, var1, "unified_diff", 1158, false, false, self, 29, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"start", "stop", "beginning", "length"};
      _format_range_context$30 = Py.newCode(2, var2, var1, "_format_range_context", 1230, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a", "b", "fromfile", "tofile", "fromfiledate", "tofiledate", "n", "lineterm", "prefix", "started", "group", "fromdate", "todate", "first", "last", "file1_range", "_(1301_15)", "tag", "i1", "i2", "_", "line", "file2_range", "_(1310_15)", "j1", "j2"};
      context_diff$31 = Py.newCode(8, var2, var1, "context_diff", 1242, false, false, self, 31, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "tag", "_"};
      f$32 = Py.newCode(1, var2, var1, "<genexpr>", 1301, false, false, self, 32, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "tag", "_"};
      f$33 = Py.newCode(1, var2, var1, "<genexpr>", 1310, false, false, self, 33, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"a", "b", "linejunk", "charjunk"};
      ndiff$34 = Py.newCode(4, var2, var1, "ndiff", 1316, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fromlines", "tolines", "context", "linejunk", "charjunk", "re", "_line_pair_iterator", "line_pair_iterator", "lines_to_write", "index", "contextLines", "found_diff", "from_line", "to_line", "i", "diff_lines_iterator", "_make_line", "_line_iterator", "change_re"};
      String[] var10001 = var2;
      difflib$py var10007 = self;
      var2 = new String[]{"diff_lines_iterator", "_make_line", "_line_iterator", "change_re"};
      _mdiff$35 = Py.newCode(5, var10001, var1, "_mdiff", 1352, false, false, var10007, 35, var2, (String[])null, 4, 4129);
      var2 = new String[]{"lines", "format_key", "side", "num_lines", "text", "markers", "sub_info", "record_sub_info", "key", "begin", "end"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"change_re"};
      _make_line$36 = Py.newCode(4, var10001, var1, "_make_line", 1394, false, false, var10007, 36, (String[])null, var2, 0, 4097);
      var2 = new String[]{"match_object", "sub_info"};
      record_sub_info$37 = Py.newCode(2, var2, var1, "record_sub_info", 1427, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"lines", "num_blanks_pending", "num_blanks_to_yield", "s", "_[1475_25]", "line", "from_line", "to_line"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"diff_lines_iterator", "_make_line"};
      _line_iterator$38 = Py.newCode(0, var10001, var1, "_line_iterator", 1450, false, false, var10007, 38, (String[])null, var2, 0, 4129);
      var2 = new String[]{"line_iterator", "fromlines", "tolines", "from_line", "to_line", "found_diff", "fromDiff", "to_diff"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_line_iterator"};
      _line_pair_iterator$39 = Py.newCode(0, var10001, var1, "_line_pair_iterator", 1541, false, false, var10007, 39, (String[])null, var2, 0, 4129);
      var2 = new String[0];
      HtmlDiff$40 = Py.newCode(0, var2, var1, "HtmlDiff", 1672, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tabsize", "wrapcolumn", "linejunk", "charjunk"};
      __init__$41 = Py.newCode(5, var2, var1, "__init__", 1694, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fromlines", "tolines", "fromdesc", "todesc", "context", "numlines"};
      make_file$42 = Py.newCode(7, var2, var1, "make_file", 1711, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fromlines", "tolines", "expand_tabs", "_[1754_21]", "line", "_[1755_19]"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      _tab_newline_replace$43 = Py.newCode(3, var10001, var1, "_tab_newline_replace", 1735, false, false, var10007, 43, var2, (String[])null, 0, 4097);
      var2 = new String[]{"line"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      expand_tabs$44 = Py.newCode(1, var10001, var1, "expand_tabs", 1745, false, false, var10007, 44, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "data_list", "line_num", "text", "size", "max", "i", "n", "mark", "line1", "line2"};
      _split_line$45 = Py.newCode(4, var2, var1, "_split_line", 1758, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "diffs", "fromdata", "todata", "flag", "fromline", "fromtext", "toline", "totext", "fromlist", "tolist"};
      _line_wrapper$46 = Py.newCode(2, var2, var1, "_line_wrapper", 1813, false, false, self, 46, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "diffs", "fromlist", "tolist", "flaglist", "fromdata", "todata", "flag"};
      _collect_lines$47 = Py.newCode(2, var2, var1, "_collect_lines", 1841, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "side", "flag", "linenum", "text", "id"};
      _format_line$48 = Py.newCode(5, var2, var1, "_format_line", 1862, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fromprefix", "toprefix"};
      _make_prefix$49 = Py.newCode(1, var2, var1, "_make_prefix", 1885, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fromlist", "tolist", "flaglist", "context", "numlines", "toprefix", "next_id", "next_href", "num_chg", "in_change", "last", "i", "flag"};
      _convert_flags$50 = Py.newCode(6, var2, var1, "_convert_flags", 1896, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fromlines", "tolines", "fromdesc", "todesc", "context", "numlines", "context_lines", "diffs", "fromlist", "tolist", "flaglist", "next_href", "next_id", "s", "fmt", "i", "header_row", "table"};
      make_table$51 = Py.newCode(7, var2, var1, "make_table", 1943, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"delta", "which", "tag", "prefixes", "line"};
      restore$52 = Py.newCode(2, var2, var1, "restore", 2022, false, false, self, 52, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"doctest", "difflib"};
      _test$53 = Py.newCode(0, var2, var1, "_test", 2054, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new difflib$py("difflib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(difflib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._calculate_ratio$1(var2, var3);
         case 2:
            return this.SequenceMatcher$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.set_seqs$4(var2, var3);
         case 5:
            return this.set_seq1$5(var2, var3);
         case 6:
            return this.set_seq2$6(var2, var3);
         case 7:
            return this._SequenceMatcher__chain_b$7(var2, var3);
         case 8:
            return this.find_longest_match$8(var2, var3);
         case 9:
            return this.get_matching_blocks$9(var2, var3);
         case 10:
            return this.get_opcodes$10(var2, var3);
         case 11:
            return this.get_grouped_opcodes$11(var2, var3);
         case 12:
            return this.ratio$12(var2, var3);
         case 13:
            return this.f$13(var2, var3);
         case 14:
            return this.quick_ratio$14(var2, var3);
         case 15:
            return this.real_quick_ratio$15(var2, var3);
         case 16:
            return this.get_close_matches$16(var2, var3);
         case 17:
            return this._count_leading$17(var2, var3);
         case 18:
            return this.Differ$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.compare$20(var2, var3);
         case 21:
            return this._dump$21(var2, var3);
         case 22:
            return this._plain_replace$22(var2, var3);
         case 23:
            return this._fancy_replace$23(var2, var3);
         case 24:
            return this._fancy_helper$24(var2, var3);
         case 25:
            return this._qformat$25(var2, var3);
         case 26:
            return this.IS_LINE_JUNK$26(var2, var3);
         case 27:
            return this.IS_CHARACTER_JUNK$27(var2, var3);
         case 28:
            return this._format_range_unified$28(var2, var3);
         case 29:
            return this.unified_diff$29(var2, var3);
         case 30:
            return this._format_range_context$30(var2, var3);
         case 31:
            return this.context_diff$31(var2, var3);
         case 32:
            return this.f$32(var2, var3);
         case 33:
            return this.f$33(var2, var3);
         case 34:
            return this.ndiff$34(var2, var3);
         case 35:
            return this._mdiff$35(var2, var3);
         case 36:
            return this._make_line$36(var2, var3);
         case 37:
            return this.record_sub_info$37(var2, var3);
         case 38:
            return this._line_iterator$38(var2, var3);
         case 39:
            return this._line_pair_iterator$39(var2, var3);
         case 40:
            return this.HtmlDiff$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this.make_file$42(var2, var3);
         case 43:
            return this._tab_newline_replace$43(var2, var3);
         case 44:
            return this.expand_tabs$44(var2, var3);
         case 45:
            return this._split_line$45(var2, var3);
         case 46:
            return this._line_wrapper$46(var2, var3);
         case 47:
            return this._collect_lines$47(var2, var3);
         case 48:
            return this._format_line$48(var2, var3);
         case 49:
            return this._make_prefix$49(var2, var3);
         case 50:
            return this._convert_flags$50(var2, var3);
         case 51:
            return this.make_table$51(var2, var3);
         case 52:
            return this.restore$52(var2, var3);
         case 53:
            return this._test$53(var2, var3);
         default:
            return null;
      }
   }
}
