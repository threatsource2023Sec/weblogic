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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("textwrap.py")
public class textwrap$py extends PyFunctionTable implements PyRunnable {
   static textwrap$py self;
   static final PyCode f$0;
   static final PyCode _unicode$1;
   static final PyCode TextWrapper$2;
   static final PyCode __init__$3;
   static final PyCode _munge_whitespace$4;
   static final PyCode _split$5;
   static final PyCode _fix_sentence_endings$6;
   static final PyCode _handle_long_word$7;
   static final PyCode _wrap_chunks$8;
   static final PyCode wrap$9;
   static final PyCode fill$10;
   static final PyCode wrap$11;
   static final PyCode fill$12;
   static final PyCode dedent$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Text wrapping and filling.\n"));
      var1.setline(2);
      PyString.fromInterned("Text wrapping and filling.\n");
      var1.setline(8);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(10);
      PyObject var7 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var7);
      var3 = null;
      var7 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var7);
      var3 = null;

      PyObject[] var4;
      try {
         var1.setline(13);
         var7 = var1.getname("unicode");
         var1.setlocal("_unicode", var7);
         var3 = null;
      } catch (Throwable var6) {
         PyException var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getname("NameError"))) {
            throw var9;
         }

         var1.setline(17);
         var4 = new PyObject[]{var1.getname("object")};
         PyObject var5 = Py.makeClass("_unicode", var4, _unicode$1);
         var1.setlocal("_unicode", var5);
         var5 = null;
         Arrays.fill(var4, (Object)null);
      }

      var1.setline(28);
      PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("TextWrapper"), PyString.fromInterned("wrap"), PyString.fromInterned("fill"), PyString.fromInterned("dedent")});
      var1.setlocal("__all__", var10);
      var3 = null;
      var1.setline(38);
      var3 = PyString.fromInterned("\t\n\u000b\f\r ");
      var1.setlocal("_whitespace", var3);
      var3 = null;
      var1.setline(40);
      PyObject[] var11 = Py.EmptyObjects;
      PyObject var8 = Py.makeClass("TextWrapper", var11, TextWrapper$2);
      var1.setlocal("TextWrapper", var8);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(343);
      var11 = new PyObject[]{Py.newInteger(70)};
      PyFunction var12 = new PyFunction(var1.f_globals, var11, wrap$11, PyString.fromInterned("Wrap a single paragraph of text, returning a list of wrapped lines.\n\n    Reformat the single paragraph in 'text' so it fits in lines of no\n    more than 'width' columns, and return a list of wrapped lines.  By\n    default, tabs in 'text' are expanded with string.expandtabs(), and\n    all other whitespace characters (including newline) are converted to\n    space.  See TextWrapper class for available keyword args to customize\n    wrapping behaviour.\n    "));
      var1.setlocal("wrap", var12);
      var3 = null;
      var1.setline(356);
      var11 = new PyObject[]{Py.newInteger(70)};
      var12 = new PyFunction(var1.f_globals, var11, fill$12, PyString.fromInterned("Fill a single paragraph of text, returning a new string.\n\n    Reformat the single paragraph in 'text' to fit in lines of no more\n    than 'width' columns, and return a new string containing the entire\n    wrapped paragraph.  As with wrap(), tabs are expanded and other\n    whitespace characters converted to space.  See TextWrapper class for\n    available keyword args to customize wrapping behaviour.\n    "));
      var1.setlocal("fill", var12);
      var3 = null;
      var1.setline(371);
      var7 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[ \t]+$"), (PyObject)var1.getname("re").__getattr__("MULTILINE"));
      var1.setlocal("_whitespace_only_re", var7);
      var3 = null;
      var1.setline(372);
      var7 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(^[ \t]*)(?:[^ \t\n])"), (PyObject)var1.getname("re").__getattr__("MULTILINE"));
      var1.setlocal("_leading_whitespace_re", var7);
      var3 = null;
      var1.setline(374);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, dedent$13, PyString.fromInterned("Remove any common leading whitespace from every line in `text`.\n\n    This can be used to make triple-quoted strings line up with the left\n    edge of the display, while still presenting them in the source code\n    in indented form.\n\n    Note that tabs and spaces are both treated as whitespace, but they\n    are not equal: the lines \"  hello\" and \"\thello\" are\n    considered to have no common leading whitespace.  (This behaviour is\n    new in Python 2.5; older versions of this module incorrectly\n    expanded tabs before searching for common leading whitespace.)\n    "));
      var1.setlocal("dedent", var12);
      var3 = null;
      var1.setline(422);
      var7 = var1.getname("__name__");
      PyObject var10000 = var7._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(425);
         Py.println(var1.getname("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hello there.\n  This is indented.")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unicode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      return var1.getf_locals();
   }

   public PyObject TextWrapper$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Object for wrapping/filling text.  The public interface consists of\n    the wrap() and fill() methods; the other methods are just there for\n    subclasses to override in order to tweak the default behaviour.\n    If you want to completely replace the main wrapping algorithm,\n    you'll probably have to override _wrap_chunks().\n\n    Several instance attributes control various aspects of wrapping:\n      width (default: 70)\n        the maximum width of wrapped lines (unless break_long_words\n        is false)\n      initial_indent (default: \"\")\n        string that will be prepended to the first line of wrapped\n        output.  Counts towards the line's width.\n      subsequent_indent (default: \"\")\n        string that will be prepended to all lines save the first\n        of wrapped output; also counts towards each line's width.\n      expand_tabs (default: true)\n        Expand tabs in input text to spaces before further processing.\n        Each tab will become 1 .. 8 spaces, depending on its position in\n        its line.  If false, each tab is treated as a single character.\n      replace_whitespace (default: true)\n        Replace all whitespace characters in the input text by spaces\n        after tab expansion.  Note that if expand_tabs is false and\n        replace_whitespace is true, every tab will be converted to a\n        single space!\n      fix_sentence_endings (default: false)\n        Ensure that sentence-ending punctuation is always followed\n        by two spaces.  Off by default because the algorithm is\n        (unavoidably) imperfect.\n      break_long_words (default: true)\n        Break words longer than 'width'.  If false, those words will not\n        be broken, and some lines might be longer than 'width'.\n      break_on_hyphens (default: true)\n        Allow breaking hyphenated words. If true, wrapping will occur\n        preferably on whitespaces and right after hyphens part of\n        compound words.\n      drop_whitespace (default: true)\n        Drop leading and trailing whitespace from lines.\n    "));
      var1.setline(80);
      PyString.fromInterned("\n    Object for wrapping/filling text.  The public interface consists of\n    the wrap() and fill() methods; the other methods are just there for\n    subclasses to override in order to tweak the default behaviour.\n    If you want to completely replace the main wrapping algorithm,\n    you'll probably have to override _wrap_chunks().\n\n    Several instance attributes control various aspects of wrapping:\n      width (default: 70)\n        the maximum width of wrapped lines (unless break_long_words\n        is false)\n      initial_indent (default: \"\")\n        string that will be prepended to the first line of wrapped\n        output.  Counts towards the line's width.\n      subsequent_indent (default: \"\")\n        string that will be prepended to all lines save the first\n        of wrapped output; also counts towards each line's width.\n      expand_tabs (default: true)\n        Expand tabs in input text to spaces before further processing.\n        Each tab will become 1 .. 8 spaces, depending on its position in\n        its line.  If false, each tab is treated as a single character.\n      replace_whitespace (default: true)\n        Replace all whitespace characters in the input text by spaces\n        after tab expansion.  Note that if expand_tabs is false and\n        replace_whitespace is true, every tab will be converted to a\n        single space!\n      fix_sentence_endings (default: false)\n        Ensure that sentence-ending punctuation is always followed\n        by two spaces.  Off by default because the algorithm is\n        (unavoidably) imperfect.\n      break_long_words (default: true)\n        Break words longer than 'width'.  If false, those words will not\n        be broken, and some lines might be longer than 'width'.\n      break_on_hyphens (default: true)\n        Allow breaking hyphenated words. If true, wrapping will occur\n        preferably on whitespaces and right after hyphens part of\n        compound words.\n      drop_whitespace (default: true)\n        Drop leading and trailing whitespace from lines.\n    ");
      var1.setline(82);
      PyObject var3 = var1.getname("string").__getattr__("maketrans").__call__(var2, var1.getname("_whitespace"), PyString.fromInterned(" ")._mul(var1.getname("len").__call__(var2, var1.getname("_whitespace"))));
      var1.setlocal("whitespace_trans", var3);
      var3 = null;
      var1.setline(84);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("unicode_whitespace_trans", var6);
      var3 = null;
      var1.setline(85);
      var3 = var1.getname("ord").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned(" "));
      var1.setlocal("uspace", var3);
      var3 = null;
      var1.setline(86);
      var3 = var1.getname("map").__call__(var2, var1.getname("ord"), var1.getname("_whitespace")).__iter__();

      while(true) {
         var1.setline(86);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(95);
            var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\s+|[^\\s\\w]*\\w+[^0-9\\W]-(?=\\w+[^0-9\\W])|(?<=[\\w\\!\\\"\\'\\&\\.\\,\\?])-{2,}(?=\\w))"));
            var1.setlocal("wordsep_re", var3);
            var3 = null;
            var1.setline(104);
            var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\s+)"));
            var1.setlocal("wordsep_simple_re", var3);
            var3 = null;
            var1.setline(108);
            var3 = var1.getname("re").__getattr__("compile").__call__(var2, PyString.fromInterned("[%s][\\.\\!\\?][\\\"\\']?\\Z")._mod(var1.getname("string").__getattr__("lowercase")));
            var1.setlocal("sentence_end_re", var3);
            var3 = null;
            var1.setline(115);
            PyObject[] var7 = new PyObject[]{Py.newInteger(70), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("True"), var1.getname("True"), var1.getname("False"), var1.getname("True"), var1.getname("True"), var1.getname("True")};
            PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$3, (PyObject)null);
            var1.setlocal("__init__", var8);
            var3 = null;
            var1.setline(146);
            var7 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var7, _munge_whitespace$4, PyString.fromInterned("_munge_whitespace(text : string) -> string\n\n        Munge whitespace in text: expand tabs and convert all other\n        whitespace characters to spaces.  Eg. \" foo\tbar\n\nbaz\"\n        becomes \" foo    bar  baz\".\n        "));
            var1.setlocal("_munge_whitespace", var8);
            var3 = null;
            var1.setline(163);
            var7 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var7, _split$5, PyString.fromInterned("_split(text : string) -> [string]\n\n        Split the text to wrap into indivisible chunks.  Chunks are\n        not quite the same as words; see _wrap_chunks() for full\n        details.  As an example, the text\n          Look, goof-ball -- use the -b option!\n        breaks into the following chunks:\n          'Look,', ' ', 'goof-', 'ball', ' ', '--', ' ',\n          'use', ' ', 'the', ' ', '-b', ' ', 'option!'\n        if break_on_hyphens is True, or in:\n          'Look,', ' ', 'goof-ball', ' ', '--', ' ',\n          'use', ' ', 'the', ' ', '-b', ' ', option!'\n        otherwise.\n        "));
            var1.setlocal("_split", var8);
            var3 = null;
            var1.setline(192);
            var7 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var7, _fix_sentence_endings$6, PyString.fromInterned("_fix_sentence_endings(chunks : [string])\n\n        Correct for sentence endings buried in 'chunks'.  Eg. when the\n        original text contains \"... foo.\nBar ...\", munge_whitespace()\n        and split() will convert that to [..., \"foo.\", \" \", \"Bar\", ...]\n        which has one too few spaces; this method simply changes the one\n        space to two.\n        "));
            var1.setlocal("_fix_sentence_endings", var8);
            var3 = null;
            var1.setline(210);
            var7 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var7, _handle_long_word$7, PyString.fromInterned("_handle_long_word(chunks : [string],\n                             cur_line : [string],\n                             cur_len : int, width : int)\n\n        Handle a chunk of text (most likely a word, not whitespace) that\n        is too long to fit in any line.\n        "));
            var1.setlocal("_handle_long_word", var8);
            var3 = null;
            var1.setline(243);
            var7 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var7, _wrap_chunks$8, PyString.fromInterned("_wrap_chunks(chunks : [string]) -> [string]\n\n        Wrap a sequence of text chunks and return a list of lines of\n        length 'self.width' or less.  (If 'break_long_words' is false,\n        some lines may be longer than this.)  Chunks correspond roughly\n        to words and the whitespace between them: each chunk is\n        indivisible (modulo 'break_long_words'), but a line break can\n        come between any two chunks.  Chunks should not have internal\n        whitespace; ie. a chunk is either all whitespace or a \"word\".\n        Whitespace chunks will be removed from the beginning and end of\n        lines, but apart from that whitespace is preserved.\n        "));
            var1.setlocal("_wrap_chunks", var8);
            var3 = null;
            var1.setline(316);
            var7 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var7, wrap$9, PyString.fromInterned("wrap(text : string) -> [string]\n\n        Reformat the single paragraph in 'text' so it fits in lines of\n        no more than 'self.width' columns, and return a list of wrapped\n        lines.  Tabs in 'text' are expanded with string.expandtabs(),\n        and all other whitespace characters (including newline) are\n        converted to space.\n        "));
            var1.setlocal("wrap", var8);
            var3 = null;
            var1.setline(331);
            var7 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var7, fill$10, PyString.fromInterned("fill(text : string) -> string\n\n        Reformat the single paragraph in 'text' to fit in lines of no\n        more than 'self.width' columns, and return a new string\n        containing the entire wrapped paragraph.\n        "));
            var1.setlocal("fill", var8);
            var3 = null;
            return var1.getf_locals();
         }

         var1.setlocal("x", var4);
         var1.setline(87);
         PyObject var5 = var1.getname("uspace");
         var1.getname("unicode_whitespace_trans").__setitem__(var1.getname("x"), var5);
         var5 = null;
      }
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("width", var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("initial_indent", var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("subsequent_indent", var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("expand_tabs", var3);
      var3 = null;
      var1.setline(129);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("replace_whitespace", var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("fix_sentence_endings", var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("break_long_words", var3);
      var3 = null;
      var1.setline(132);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("drop_whitespace", var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("break_on_hyphens", var3);
      var3 = null;
      var1.setline(138);
      var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(0).__getattr__("wordsep_re").__getattr__("pattern"), var1.getglobal("re").__getattr__("U"));
      var1.getlocal(0).__setattr__("wordsep_re_uni", var3);
      var3 = null;
      var1.setline(139);
      var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(0).__getattr__("wordsep_simple_re").__getattr__("pattern"), var1.getglobal("re").__getattr__("U"));
      var1.getlocal(0).__setattr__("wordsep_simple_re_uni", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _munge_whitespace$4(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyString.fromInterned("_munge_whitespace(text : string) -> string\n\n        Munge whitespace in text: expand tabs and convert all other\n        whitespace characters to spaces.  Eg. \" foo\tbar\n\nbaz\"\n        becomes \" foo    bar  baz\".\n        ");
      var1.setline(153);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("expand_tabs").__nonzero__()) {
         var1.setline(154);
         var3 = var1.getlocal(1).__getattr__("expandtabs").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(155);
      if (var1.getlocal(0).__getattr__("replace_whitespace").__nonzero__()) {
         var1.setline(156);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(157);
            var3 = var1.getlocal(1).__getattr__("translate").__call__(var2, var1.getlocal(0).__getattr__("whitespace_trans"));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(158);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_unicode")).__nonzero__()) {
               var1.setline(159);
               var3 = var1.getlocal(1).__getattr__("translate").__call__(var2, var1.getlocal(0).__getattr__("unicode_whitespace_trans"));
               var1.setlocal(1, var3);
               var3 = null;
            }
         }
      }

      var1.setline(160);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _split$5(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyString.fromInterned("_split(text : string) -> [string]\n\n        Split the text to wrap into indivisible chunks.  Chunks are\n        not quite the same as words; see _wrap_chunks() for full\n        details.  As an example, the text\n          Look, goof-ball -- use the -b option!\n        breaks into the following chunks:\n          'Look,', ' ', 'goof-', 'ball', ' ', '--', ' ',\n          'use', ' ', 'the', ' ', '-b', ' ', 'option!'\n        if break_on_hyphens is True, or in:\n          'Look,', ' ', 'goof-ball', ' ', '--', ' ',\n          'use', ' ', 'the', ' ', '-b', ' ', option!'\n        otherwise.\n        ");
      var1.setline(178);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_unicode")).__nonzero__()) {
         var1.setline(179);
         if (var1.getlocal(0).__getattr__("break_on_hyphens").__nonzero__()) {
            var1.setline(180);
            var3 = var1.getlocal(0).__getattr__("wordsep_re_uni");
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(182);
            var3 = var1.getlocal(0).__getattr__("wordsep_simple_re_uni");
            var1.setlocal(2, var3);
            var3 = null;
         }
      } else {
         var1.setline(184);
         if (var1.getlocal(0).__getattr__("break_on_hyphens").__nonzero__()) {
            var1.setline(185);
            var3 = var1.getlocal(0).__getattr__("wordsep_re");
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(187);
            var3 = var1.getlocal(0).__getattr__("wordsep_simple_re");
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(188);
      var3 = var1.getlocal(2).__getattr__("split").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(189);
      var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(190);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _fix_sentence_endings$6(PyFrame var1, ThreadState var2) {
      var1.setline(200);
      PyString.fromInterned("_fix_sentence_endings(chunks : [string])\n\n        Correct for sentence endings buried in 'chunks'.  Eg. when the\n        original text contains \"... foo.\nBar ...\", munge_whitespace()\n        and split() will convert that to [..., \"foo.\", \" \", \"Bar\", ...]\n        which has one too few spaces; this method simply changes the one\n        space to two.\n        ");
      var1.setline(201);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(202);
      PyObject var4 = var1.getlocal(0).__getattr__("sentence_end_re").__getattr__("search");
      var1.setlocal(3, var4);
      var3 = null;

      while(true) {
         var1.setline(203);
         var4 = var1.getlocal(2);
         PyObject var10000 = var4._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1)));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(204);
         var4 = var1.getlocal(1).__getitem__(var1.getlocal(2)._add(Py.newInteger(1)));
         var10000 = var4._eq(PyString.fromInterned(" "));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(2)));
         }

         if (var10000.__nonzero__()) {
            var1.setline(205);
            PyString var5 = PyString.fromInterned("  ");
            var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2)._add(Py.newInteger(1)), var5);
            var3 = null;
            var1.setline(206);
            var4 = var1.getlocal(2);
            var4 = var4._iadd(Py.newInteger(2));
            var1.setlocal(2, var4);
         } else {
            var1.setline(208);
            var4 = var1.getlocal(2);
            var4 = var4._iadd(Py.newInteger(1));
            var1.setlocal(2, var4);
         }
      }
   }

   public PyObject _handle_long_word$7(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      PyString.fromInterned("_handle_long_word(chunks : [string],\n                             cur_line : [string],\n                             cur_len : int, width : int)\n\n        Handle a chunk of text (most likely a word, not whitespace) that\n        is too long to fit in any line.\n        ");
      var1.setline(220);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._lt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(221);
         PyInteger var4 = Py.newInteger(1);
         var1.setlocal(5, var4);
         var3 = null;
      } else {
         var1.setline(223);
         var3 = var1.getlocal(4)._sub(var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(227);
      if (var1.getlocal(0).__getattr__("break_long_words").__nonzero__()) {
         var1.setline(228);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null));
         var1.setline(229);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null);
         var1.getlocal(1).__setitem__((PyObject)Py.newInteger(-1), var3);
         var3 = null;
      } else {
         var1.setline(234);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(235);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("pop").__call__(var2));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _wrap_chunks$8(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyString.fromInterned("_wrap_chunks(chunks : [string]) -> [string]\n\n        Wrap a sequence of text chunks and return a list of lines of\n        length 'self.width' or less.  (If 'break_long_words' is false,\n        some lines may be longer than this.)  Chunks correspond roughly\n        to words and the whitespace between them: each chunk is\n        indivisible (modulo 'break_long_words'), but a line break can\n        come between any two chunks.  Chunks should not have internal\n        whitespace; ie. a chunk is either all whitespace or a \"word\".\n        Whitespace chunks will be removed from the beginning and end of\n        lines, but apart from that whitespace is preserved.\n        ");
      var1.setline(256);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(257);
      PyObject var4 = var1.getlocal(0).__getattr__("width");
      PyObject var10000 = var4._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(258);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid width %r (must be > 0)")._mod(var1.getlocal(0).__getattr__("width"))));
      } else {
         var1.setline(262);
         var1.getlocal(1).__getattr__("reverse").__call__(var2);

         while(true) {
            var1.setline(264);
            if (!var1.getlocal(1).__nonzero__()) {
               var1.setline(311);
               var4 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var4;
            }

            var1.setline(268);
            var3 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(269);
            PyInteger var5 = Py.newInteger(0);
            var1.setlocal(4, var5);
            var3 = null;
            var1.setline(272);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(273);
               var4 = var1.getlocal(0).__getattr__("subsequent_indent");
               var1.setlocal(5, var4);
               var3 = null;
            } else {
               var1.setline(275);
               var4 = var1.getlocal(0).__getattr__("initial_indent");
               var1.setlocal(5, var4);
               var3 = null;
            }

            var1.setline(278);
            var4 = var1.getlocal(0).__getattr__("width")._sub(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
            var1.setlocal(6, var4);
            var3 = null;
            var1.setline(282);
            var10000 = var1.getlocal(0).__getattr__("drop_whitespace");
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getattr__("strip").__call__(var2);
               var10000 = var4._eq(PyString.fromInterned(""));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(2);
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(283);
               var1.getlocal(1).__delitem__((PyObject)Py.newInteger(-1));
            }

            while(true) {
               var1.setline(285);
               if (!var1.getlocal(1).__nonzero__()) {
                  break;
               }

               var1.setline(286);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
               var1.setlocal(7, var4);
               var3 = null;
               var1.setline(289);
               var4 = var1.getlocal(4)._add(var1.getlocal(7));
               var10000 = var4._le(var1.getlocal(6));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  break;
               }

               var1.setline(290);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("pop").__call__(var2));
               var1.setline(291);
               var4 = var1.getlocal(4);
               var4 = var4._iadd(var1.getlocal(7));
               var1.setlocal(4, var4);
            }

            var1.setline(299);
            var10000 = var1.getlocal(1);
            if (var10000.__nonzero__()) {
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(-1)));
               var10000 = var4._gt(var1.getlocal(6));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(300);
               var1.getlocal(0).__getattr__("_handle_long_word").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(4), var1.getlocal(6));
            }

            var1.setline(303);
            var10000 = var1.getlocal(0).__getattr__("drop_whitespace");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(3).__getitem__(Py.newInteger(-1)).__getattr__("strip").__call__(var2);
                  var10000 = var4._eq(PyString.fromInterned(""));
                  var3 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(304);
               var1.getlocal(3).__delitem__((PyObject)Py.newInteger(-1));
            }

            var1.setline(308);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(309);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5)._add(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3))));
            }
         }
      }
   }

   public PyObject wrap$9(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyString.fromInterned("wrap(text : string) -> [string]\n\n        Reformat the single paragraph in 'text' so it fits in lines of\n        no more than 'self.width' columns, and return a list of wrapped\n        lines.  Tabs in 'text' are expanded with string.expandtabs(),\n        and all other whitespace characters (including newline) are\n        converted to space.\n        ");
      var1.setline(325);
      PyObject var3 = var1.getlocal(0).__getattr__("_munge_whitespace").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(326);
      var3 = var1.getlocal(0).__getattr__("_split").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(327);
      if (var1.getlocal(0).__getattr__("fix_sentence_endings").__nonzero__()) {
         var1.setline(328);
         var1.getlocal(0).__getattr__("_fix_sentence_endings").__call__(var2, var1.getlocal(2));
      }

      var1.setline(329);
      var3 = var1.getlocal(0).__getattr__("_wrap_chunks").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fill$10(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyString.fromInterned("fill(text : string) -> string\n\n        Reformat the single paragraph in 'text' to fit in lines of no\n        more than 'self.width' columns, and return a new string\n        containing the entire wrapped paragraph.\n        ");
      var1.setline(338);
      PyObject var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("wrap").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject wrap$11(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyString.fromInterned("Wrap a single paragraph of text, returning a list of wrapped lines.\n\n    Reformat the single paragraph in 'text' so it fits in lines of no\n    more than 'width' columns, and return a list of wrapped lines.  By\n    default, tabs in 'text' are expanded with string.expandtabs(), and\n    all other whitespace characters (including newline) are converted to\n    space.  See TextWrapper class for available keyword args to customize\n    wrapping behaviour.\n    ");
      var1.setline(353);
      PyObject var10000 = var1.getglobal("TextWrapper");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"width"};
      var10000 = var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(354);
      var5 = var1.getlocal(3).__getattr__("wrap").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject fill$12(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyString.fromInterned("Fill a single paragraph of text, returning a new string.\n\n    Reformat the single paragraph in 'text' to fit in lines of no more\n    than 'width' columns, and return a new string containing the entire\n    wrapped paragraph.  As with wrap(), tabs are expanded and other\n    whitespace characters converted to space.  See TextWrapper class for\n    available keyword args to customize wrapping behaviour.\n    ");
      var1.setline(365);
      PyObject var10000 = var1.getglobal("TextWrapper");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"width"};
      var10000 = var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(366);
      var5 = var1.getlocal(3).__getattr__("fill").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject dedent$13(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyString.fromInterned("Remove any common leading whitespace from every line in `text`.\n\n    This can be used to make triple-quoted strings line up with the left\n    edge of the display, while still presenting them in the source code\n    in indented form.\n\n    Note that tabs and spaces are both treated as whitespace, but they\n    are not equal: the lines \"  hello\" and \"\thello\" are\n    considered to have no common leading whitespace.  (This behaviour is\n    new in Python 2.5; older versions of this module incorrectly\n    expanded tabs before searching for common leading whitespace.)\n    ");
      var1.setline(389);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(390);
      var3 = var1.getglobal("_whitespace_only_re").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(391);
      var3 = var1.getglobal("_leading_whitespace_re").__getattr__("findall").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(392);
      var3 = var1.getlocal(2).__iter__();

      PyObject var10000;
      PyObject var4;
      while(true) {
         var1.setline(392);
         var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(3, var4);
         var1.setline(393);
         PyObject var5 = var1.getlocal(1);
         var10000 = var5._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(394);
            var5 = var1.getlocal(3);
            var1.setlocal(1, var5);
            var5 = null;
         } else {
            var1.setline(398);
            if (var1.getlocal(3).__getattr__("startswith").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(399);
            } else {
               var1.setline(403);
               if (!var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(3)).__nonzero__()) {
                  var1.setline(409);
                  PyString var6 = PyString.fromInterned("");
                  var1.setlocal(1, var6);
                  var5 = null;
                  break;
               }

               var1.setline(404);
               var5 = var1.getlocal(3);
               var1.setlocal(1, var5);
               var5 = null;
            }
         }
      }

      var1.setline(413);
      Object var7 = Py.newInteger(0);
      if (((PyObject)var7).__nonzero__()) {
         var7 = var1.getlocal(1);
      }

      if (((PyObject)var7).__nonzero__()) {
         var1.setline(414);
         var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(414);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(415);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var10000 = var1.getlocal(4).__not__();
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4).__getattr__("startswith").__call__(var2, var1.getlocal(1));
               }

               if (!var10000.__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("line = %r, margin = %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(1)})));
               }
            }
         }
      }

      var1.setline(418);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(419);
         var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(?m)^")._add(var1.getlocal(1)), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(420);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public textwrap$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _unicode$1 = Py.newCode(0, var2, var1, "_unicode", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TextWrapper$2 = Py.newCode(0, var2, var1, "TextWrapper", 40, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "width", "initial_indent", "subsequent_indent", "expand_tabs", "replace_whitespace", "fix_sentence_endings", "break_long_words", "drop_whitespace", "break_on_hyphens"};
      __init__$3 = Py.newCode(10, var2, var1, "__init__", 115, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      _munge_whitespace$4 = Py.newCode(2, var2, var1, "_munge_whitespace", 146, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "pat", "chunks"};
      _split$5 = Py.newCode(2, var2, var1, "_split", 163, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chunks", "i", "patsearch"};
      _fix_sentence_endings$6 = Py.newCode(2, var2, var1, "_fix_sentence_endings", 192, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reversed_chunks", "cur_line", "cur_len", "width", "space_left"};
      _handle_long_word$7 = Py.newCode(5, var2, var1, "_handle_long_word", 210, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chunks", "lines", "cur_line", "cur_len", "indent", "width", "l"};
      _wrap_chunks$8 = Py.newCode(2, var2, var1, "_wrap_chunks", 243, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "chunks"};
      wrap$9 = Py.newCode(2, var2, var1, "wrap", 316, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      fill$10 = Py.newCode(2, var2, var1, "fill", 331, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "width", "kwargs", "w"};
      wrap$11 = Py.newCode(3, var2, var1, "wrap", 343, false, true, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "width", "kwargs", "w"};
      fill$12 = Py.newCode(3, var2, var1, "fill", 356, false, true, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "margin", "indents", "indent", "line"};
      dedent$13 = Py.newCode(1, var2, var1, "dedent", 374, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new textwrap$py("textwrap$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(textwrap$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._unicode$1(var2, var3);
         case 2:
            return this.TextWrapper$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._munge_whitespace$4(var2, var3);
         case 5:
            return this._split$5(var2, var3);
         case 6:
            return this._fix_sentence_endings$6(var2, var3);
         case 7:
            return this._handle_long_word$7(var2, var3);
         case 8:
            return this._wrap_chunks$8(var2, var3);
         case 9:
            return this.wrap$9(var2, var3);
         case 10:
            return this.fill$10(var2, var3);
         case 11:
            return this.wrap$11(var2, var3);
         case 12:
            return this.fill$12(var2, var3);
         case 13:
            return this.dedent$13(var2, var3);
         default:
            return null;
      }
   }
}
