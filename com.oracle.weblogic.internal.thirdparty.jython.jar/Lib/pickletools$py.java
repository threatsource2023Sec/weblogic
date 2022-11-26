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
@Filename("pickletools.py")
public class pickletools$py extends PyFunctionTable implements PyRunnable {
   static pickletools$py self;
   static final PyCode f$0;
   static final PyCode ArgumentDescriptor$1;
   static final PyCode __init__$2;
   static final PyCode read_uint1$3;
   static final PyCode read_uint2$4;
   static final PyCode read_int4$5;
   static final PyCode read_stringnl$6;
   static final PyCode read_stringnl_noescape$7;
   static final PyCode read_stringnl_noescape_pair$8;
   static final PyCode read_string4$9;
   static final PyCode read_string1$10;
   static final PyCode read_unicodestringnl$11;
   static final PyCode read_unicodestring4$12;
   static final PyCode read_decimalnl_short$13;
   static final PyCode read_decimalnl_long$14;
   static final PyCode read_floatnl$15;
   static final PyCode read_float8$16;
   static final PyCode read_long1$17;
   static final PyCode read_long4$18;
   static final PyCode StackObject$19;
   static final PyCode __init__$20;
   static final PyCode __repr__$21;
   static final PyCode OpcodeInfo$22;
   static final PyCode __init__$23;
   static final PyCode assure_pickle_consistency$24;
   static final PyCode genops$25;
   static final PyCode f$26;
   static final PyCode optimize$27;
   static final PyCode dis$28;
   static final PyCode _Example$29;
   static final PyCode __init__$30;
   static final PyCode _test$31;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\"Executable documentation\" for the pickle module.\n\nExtensive comments about the pickle protocols and pickle-machine opcodes\ncan be found here.  Some functions meant for external use:\n\ngenops(pickle)\n   Generate all the opcodes in a pickle, as (opcode, arg, position) triples.\n\ndis(pickle, out=None, memo=None, indentlevel=4)\n   Print a symbolic disassembly of a pickle.\n"));
      var1.setline(11);
      PyString.fromInterned("\"Executable documentation\" for the pickle module.\n\nExtensive comments about the pickle protocols and pickle-machine opcodes\ncan be found here.  Some functions meant for external use:\n\ngenops(pickle)\n   Generate all the opcodes in a pickle, as (opcode, arg, position) triples.\n\ndis(pickle, out=None, memo=None, indentlevel=4)\n   Print a symbolic disassembly of a pickle.\n");
      var1.setline(13);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("dis"), PyString.fromInterned("genops"), PyString.fromInterned("optimize")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(142);
      PyString.fromInterned("\n\"A pickle\" is a program for a virtual pickle machine (PM, but more accurately\ncalled an unpickling machine).  It's a sequence of opcodes, interpreted by the\nPM, building an arbitrarily complex Python object.\n\nFor the most part, the PM is very simple:  there are no looping, testing, or\nconditional instructions, no arithmetic and no function calls.  Opcodes are\nexecuted once each, from first to last, until a STOP opcode is reached.\n\nThe PM has two data areas, \"the stack\" and \"the memo\".\n\nMany opcodes push Python objects onto the stack; e.g., INT pushes a Python\ninteger object on the stack, whose value is gotten from a decimal string\nliteral immediately following the INT opcode in the pickle bytestream.  Other\nopcodes take Python objects off the stack.  The result of unpickling is\nwhatever object is left on the stack when the final STOP opcode is executed.\n\nThe memo is simply an array of objects, or it can be implemented as a dict\nmapping little integers to objects.  The memo serves as the PM's \"long term\nmemory\", and the little integers indexing the memo are akin to variable\nnames.  Some opcodes pop a stack object into the memo at a given index,\nand others push a memo object at a given index onto the stack again.\n\nAt heart, that's all the PM has.  Subtleties arise for these reasons:\n\n+ Object identity.  Objects can be arbitrarily complex, and subobjects\n  may be shared (for example, the list [a, a] refers to the same object a\n  twice).  It can be vital that unpickling recreate an isomorphic object\n  graph, faithfully reproducing sharing.\n\n+ Recursive objects.  For example, after \"L = []; L.append(L)\", L is a\n  list, and L[0] is the same list.  This is related to the object identity\n  point, and some sequences of pickle opcodes are subtle in order to\n  get the right result in all cases.\n\n+ Things pickle doesn't know everything about.  Examples of things pickle\n  does know everything about are Python's builtin scalar and container\n  types, like ints and tuples.  They generally have opcodes dedicated to\n  them.  For things like module references and instances of user-defined\n  classes, pickle's knowledge is limited.  Historically, many enhancements\n  have been made to the pickle protocol in order to do a better (faster,\n  and/or more compact) job on those.\n\n+ Backward compatibility and micro-optimization.  As explained below,\n  pickle opcodes never go away, not even when better ways to do a thing\n  get invented.  The repertoire of the PM just keeps growing over time.\n  For example, protocol 0 had two opcodes for building Python integers (INT\n  and LONG), protocol 1 added three more for more-efficient pickling of short\n  integers, and protocol 2 added two more for more-efficient pickling of\n  long integers (before protocol 2, the only ways to pickle a Python long\n  took time quadratic in the number of digits, for both pickling and\n  unpickling).  \"Opcode bloat\" isn't so much a subtlety as a source of\n  wearying complication.\n\n\nPickle protocols:\n\nFor compatibility, the meaning of a pickle opcode never changes.  Instead new\npickle opcodes get added, and each version's unpickler can handle all the\npickle opcodes in all protocol versions to date.  So old pickles continue to\nbe readable forever.  The pickler can generally be told to restrict itself to\nthe subset of opcodes available under previous protocol versions too, so that\nusers can create pickles under the current version readable by older\nversions.  However, a pickle does not contain its version number embedded\nwithin it.  If an older unpickler tries to read a pickle using a later\nprotocol, the result is most likely an exception due to seeing an unknown (in\nthe older unpickler) opcode.\n\nThe original pickle used what's now called \"protocol 0\", and what was called\n\"text mode\" before Python 2.3.  The entire pickle bytestream is made up of\nprintable 7-bit ASCII characters, plus the newline character, in protocol 0.\nThat's why it was called text mode.  Protocol 0 is small and elegant, but\nsometimes painfully inefficient.\n\nThe second major set of additions is now called \"protocol 1\", and was called\n\"binary mode\" before Python 2.3.  This added many opcodes with arguments\nconsisting of arbitrary bytes, including NUL bytes and unprintable \"high bit\"\nbytes.  Binary mode pickles can be substantially smaller than equivalent\ntext mode pickles, and sometimes faster too; e.g., BININT represents a 4-byte\nint as 4 bytes following the opcode, which is cheaper to unpickle than the\n(perhaps) 11-character decimal string attached to INT.  Protocol 1 also added\na number of opcodes that operate on many stack elements at once (like APPENDS\nand SETITEMS), and \"shortcut\" opcodes (like EMPTY_DICT and EMPTY_TUPLE).\n\nThe third major set of additions came in Python 2.3, and is called \"protocol\n2\".  This added:\n\n- A better way to pickle instances of new-style classes (NEWOBJ).\n\n- A way for a pickle to identify its protocol (PROTO).\n\n- Time- and space- efficient pickling of long ints (LONG{1,4}).\n\n- Shortcuts for small tuples (TUPLE{1,2,3}}.\n\n- Dedicated opcodes for bools (NEWTRUE, NEWFALSE).\n\n- The \"extension registry\", a vector of popular objects that can be pushed\n  efficiently by index (EXT{1,2,4}).  This is akin to the memo and GET, but\n  the registry contents are predefined (there's nothing akin to the memo's\n  PUT).\n\nAnother independent change with Python 2.3 is the abandonment of any\npretense that it might be safe to load pickles received from untrusted\nparties -- no sufficient security analysis has been done to guarantee\nthis and there isn't a use case that warrants the expense of such an\nanalysis.\n\nTo this end, all tests for __safe_for_unpickling__ or for\ncopy_reg.safe_constructors are removed from the unpickling code.\nReferences to these variables in the descriptions below are to be seen\nas describing unpickling in Python 2.2 and before.\n");
      var1.setline(158);
      PyInteger var7 = Py.newInteger(-1);
      var1.setlocal("UP_TO_NEWLINE", var7);
      var3 = null;
      var1.setline(162);
      var7 = Py.newInteger(-2);
      var1.setlocal("TAKEN_FROM_ARGUMENT1", var7);
      var3 = null;
      var1.setline(163);
      var7 = Py.newInteger(-3);
      var1.setlocal("TAKEN_FROM_ARGUMENT4", var7);
      var3 = null;
      var1.setline(165);
      PyObject[] var10 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("ArgumentDescriptor", var10, ArgumentDescriptor$1);
      var1.setlocal("ArgumentDescriptor", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(199);
      String[] var11 = new String[]{"unpack"};
      var10 = imp.importFrom("struct", var11, var1, -1);
      var4 = var10[0];
      var1.setlocal("_unpack", var4);
      var4 = null;
      var1.setline(201);
      var10 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var10, read_uint1$3, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_uint1(StringIO.StringIO('\\xff'))\n    255\n    "));
      var1.setlocal("read_uint1", var12);
      var3 = null;
      var1.setline(213);
      PyObject var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("uint1"), Py.newInteger(1), var1.getname("read_uint1"), PyString.fromInterned("One-byte unsigned integer.")};
      String[] var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      PyObject var13 = var10000;
      var1.setlocal("uint1", var13);
      var3 = null;
      var1.setline(220);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_uint2$4, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_uint2(StringIO.StringIO('\\xff\\x00'))\n    255\n    >>> read_uint2(StringIO.StringIO('\\xff\\xff'))\n    65535\n    "));
      var1.setlocal("read_uint2", var12);
      var3 = null;
      var1.setline(234);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("uint2"), Py.newInteger(2), var1.getname("read_uint2"), PyString.fromInterned("Two-byte unsigned integer, little-endian.")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("uint2", var13);
      var3 = null;
      var1.setline(241);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_int4$5, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_int4(StringIO.StringIO('\\xff\\x00\\x00\\x00'))\n    255\n    >>> read_int4(StringIO.StringIO('\\x00\\x00\\x00\\x80')) == -(2**31)\n    True\n    "));
      var1.setlocal("read_int4", var12);
      var3 = null;
      var1.setline(255);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("int4"), Py.newInteger(4), var1.getname("read_int4"), PyString.fromInterned("Four-byte signed integer, little-endian, 2's complement.")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("int4", var13);
      var3 = null;
      var1.setline(262);
      var10 = new PyObject[]{var1.getname("True"), var1.getname("True")};
      var12 = new PyFunction(var1.f_globals, var10, read_stringnl$6, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_stringnl(StringIO.StringIO(\"'abcd'\\nefg\\n\"))\n    'abcd'\n\n    >>> read_stringnl(StringIO.StringIO(\"\\n\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: no string quotes around ''\n\n    >>> read_stringnl(StringIO.StringIO(\"\\n\"), stripquotes=False)\n    ''\n\n    >>> read_stringnl(StringIO.StringIO(\"''\\n\"))\n    ''\n\n    >>> read_stringnl(StringIO.StringIO('\"abcd\"'))\n    Traceback (most recent call last):\n    ...\n    ValueError: no newline found when trying to read stringnl\n\n    Embedded escapes are undone in the result.\n    >>> read_stringnl(StringIO.StringIO(r\"'a\\n\\\\b\\x00c\\td'\" + \"\\n'e'\"))\n    'a\\n\\\\b\\x00c\\td'\n    "));
      var1.setlocal("read_stringnl", var12);
      var3 = null;
      var1.setline(311);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("stringnl"), var1.getname("UP_TO_NEWLINE"), var1.getname("read_stringnl"), PyString.fromInterned("A newline-terminated string.\n\n                   This is a repr-style string, with embedded escapes, and\n                   bracketing quotes.\n                   ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("stringnl", var13);
      var3 = null;
      var1.setline(321);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_stringnl_noescape$7, (PyObject)null);
      var1.setlocal("read_stringnl_noescape", var12);
      var3 = null;
      var1.setline(324);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("stringnl_noescape"), var1.getname("UP_TO_NEWLINE"), var1.getname("read_stringnl_noescape"), PyString.fromInterned("A newline-terminated string.\n\n                        This is a str-style string, without embedded escapes,\n                        or bracketing quotes.  It should consist solely of\n                        printable ASCII characters.\n                        ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("stringnl_noescape", var13);
      var3 = null;
      var1.setline(335);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_stringnl_noescape_pair$8, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_stringnl_noescape_pair(StringIO.StringIO(\"Queue\\nEmpty\\njunk\"))\n    'Queue Empty'\n    "));
      var1.setlocal("read_stringnl_noescape_pair", var12);
      var3 = null;
      var1.setline(344);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("stringnl_noescape_pair"), var1.getname("UP_TO_NEWLINE"), var1.getname("read_stringnl_noescape_pair"), PyString.fromInterned("A pair of newline-terminated strings.\n\n                             These are str-style strings, without embedded\n                             escapes, or bracketing quotes.  They should\n                             consist solely of printable ASCII characters.\n                             The pair is returned as a single string, with\n                             a single blank separating the two strings.\n                             ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("stringnl_noescape_pair", var13);
      var3 = null;
      var1.setline(357);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_string4$9, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_string4(StringIO.StringIO(\"\\x00\\x00\\x00\\x00abc\"))\n    ''\n    >>> read_string4(StringIO.StringIO(\"\\x03\\x00\\x00\\x00abcdef\"))\n    'abc'\n    >>> read_string4(StringIO.StringIO(\"\\x00\\x00\\x00\\x03abcdef\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: expected 50331648 bytes in a string4, but only 6 remain\n    "));
      var1.setlocal("read_string4", var12);
      var3 = null;
      var1.setline(379);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("string4"), var1.getname("TAKEN_FROM_ARGUMENT4"), var1.getname("read_string4"), PyString.fromInterned("A counted string.\n\n              The first argument is a 4-byte little-endian signed int giving\n              the number of bytes in the string, and the second argument is\n              that many bytes.\n              ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("string4", var13);
      var3 = null;
      var1.setline(391);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_string1$10, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_string1(StringIO.StringIO(\"\\x00\"))\n    ''\n    >>> read_string1(StringIO.StringIO(\"\\x03abcdef\"))\n    'abc'\n    "));
      var1.setlocal("read_string1", var12);
      var3 = null;
      var1.setline(408);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("string1"), var1.getname("TAKEN_FROM_ARGUMENT1"), var1.getname("read_string1"), PyString.fromInterned("A counted string.\n\n              The first argument is a 1-byte unsigned int giving the number\n              of bytes in the string, and the second argument is that many\n              bytes.\n              ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("string1", var13);
      var3 = null;
      var1.setline(420);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_unicodestringnl$11, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_unicodestringnl(StringIO.StringIO(\"abc\\uabcd\\njunk\"))\n    u'abc\\uabcd'\n    "));
      var1.setlocal("read_unicodestringnl", var12);
      var3 = null;
      var1.setline(434);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("unicodestringnl"), var1.getname("UP_TO_NEWLINE"), var1.getname("read_unicodestringnl"), PyString.fromInterned("A newline-terminated Unicode string.\n\n                      This is raw-unicode-escape encoded, so consists of\n                      printable ASCII characters, and may contain embedded\n                      escape sequences.\n                      ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("unicodestringnl", var13);
      var3 = null;
      var1.setline(445);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_unicodestring4$12, PyString.fromInterned("\n    >>> import StringIO\n    >>> s = u'abcd\\uabcd'\n    >>> enc = s.encode('utf-8')\n    >>> enc\n    'abcd\\xea\\xaf\\x8d'\n    >>> n = chr(len(enc)) + chr(0) * 3  # little-endian 4-byte length\n    >>> t = read_unicodestring4(StringIO.StringIO(n + enc + 'junk'))\n    >>> s == t\n    True\n\n    >>> read_unicodestring4(StringIO.StringIO(n + enc[:-1]))\n    Traceback (most recent call last):\n    ...\n    ValueError: expected 7 bytes in a unicodestring4, but only 6 remain\n    "));
      var1.setlocal("read_unicodestring4", var12);
      var3 = null;
      var1.setline(472);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("unicodestring4"), var1.getname("TAKEN_FROM_ARGUMENT4"), var1.getname("read_unicodestring4"), PyString.fromInterned("A counted Unicode string.\n\n                    The first argument is a 4-byte little-endian signed int\n                    giving the number of bytes in the string, and the second\n                    argument-- the UTF-8 encoding of the Unicode string --\n                    contains that many bytes.\n                    ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("unicodestring4", var13);
      var3 = null;
      var1.setline(485);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_decimalnl_short$13, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_decimalnl_short(StringIO.StringIO(\"1234\\n56\"))\n    1234\n\n    >>> read_decimalnl_short(StringIO.StringIO(\"1234L\\n56\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: trailing 'L' not allowed in '1234L'\n    "));
      var1.setlocal("read_decimalnl_short", var12);
      var3 = null;
      var1.setline(514);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_decimalnl_long$14, PyString.fromInterned("\n    >>> import StringIO\n\n    >>> read_decimalnl_long(StringIO.StringIO(\"1234\\n56\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: trailing 'L' required in '1234'\n\n    Someday the trailing 'L' will probably go away from this output.\n\n    >>> read_decimalnl_long(StringIO.StringIO(\"1234L\\n56\"))\n    1234L\n\n    >>> read_decimalnl_long(StringIO.StringIO(\"123456789012345678901234L\\n6\"))\n    123456789012345678901234L\n    "));
      var1.setlocal("read_decimalnl_long", var12);
      var3 = null;
      var1.setline(538);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("decimalnl_short"), var1.getname("UP_TO_NEWLINE"), var1.getname("read_decimalnl_short"), PyString.fromInterned("A newline-terminated decimal integer literal.\n\n                          This never has a trailing 'L', and the integer fit\n                          in a short Python int on the box where the pickle\n                          was written -- but there's no guarantee it will fit\n                          in a short Python int on the box where the pickle\n                          is read.\n                          ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("decimalnl_short", var13);
      var3 = null;
      var1.setline(551);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("decimalnl_long"), var1.getname("UP_TO_NEWLINE"), var1.getname("read_decimalnl_long"), PyString.fromInterned("A newline-terminated decimal integer literal.\n\n                         This has a trailing 'L', and can represent integers\n                         of any size.\n                         ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("decimalnl_long", var13);
      var3 = null;
      var1.setline(562);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_floatnl$15, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_floatnl(StringIO.StringIO(\"-1.25\\n6\"))\n    -1.25\n    "));
      var1.setlocal("read_floatnl", var12);
      var3 = null;
      var1.setline(571);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("floatnl"), var1.getname("UP_TO_NEWLINE"), var1.getname("read_floatnl"), PyString.fromInterned("A newline-terminated decimal floating literal.\n\n              In general this requires 17 significant digits for roundtrip\n              identity, and pickling then unpickling infinities, NaNs, and\n              minus zero doesn't work across boxes, or on some boxes even\n              on itself (e.g., Windows can't read the strings it produces\n              for infinities or NaNs).\n              ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("floatnl", var13);
      var3 = null;
      var1.setline(584);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_float8$16, PyString.fromInterned("\n    >>> import StringIO, struct\n    >>> raw = struct.pack(\">d\", -1.25)\n    >>> raw\n    '\\xbf\\xf4\\x00\\x00\\x00\\x00\\x00\\x00'\n    >>> read_float8(StringIO.StringIO(raw + \"\\n\"))\n    -1.25\n    "));
      var1.setlocal("read_float8", var12);
      var3 = null;
      var1.setline(600);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("float8"), Py.newInteger(8), var1.getname("read_float8"), PyString.fromInterned("An 8-byte binary representation of a float, big-endian.\n\n             The format is unique to Python, and shared with the struct\n             module (format string '>d') \"in theory\" (the struct and cPickle\n             implementations don't share the code -- they should).  It's\n             strongly related to the IEEE-754 double format, and, in normal\n             cases, is in fact identical to the big-endian 754 double format.\n             On other boxes the dynamic range is limited to that of a 754\n             double, and \"add a half and chop\" rounding is used to reduce\n             the precision to 53 bits.  However, even on a 754 box,\n             infinities, NaNs, and minus zero may not be handled correctly\n             (may not survive roundtrip pickling intact).\n             ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("float8", var13);
      var3 = null;
      var1.setline(620);
      var11 = new String[]{"decode_long"};
      var10 = imp.importFrom("pickle", var11, var1, -1);
      var4 = var10[0];
      var1.setlocal("decode_long", var4);
      var4 = null;
      var1.setline(622);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_long1$17, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_long1(StringIO.StringIO(\"\\x00\"))\n    0L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\xff\\x00\"))\n    255L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\xff\\x7f\"))\n    32767L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\x00\\xff\"))\n    -256L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\x00\\x80\"))\n    -32768L\n    "));
      var1.setlocal("read_long1", var12);
      var3 = null;
      var1.setline(643);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("long1"), var1.getname("TAKEN_FROM_ARGUMENT1"), var1.getname("read_long1"), PyString.fromInterned("A binary long, little-endian, using 1-byte size.\n\n    This first reads one byte as an unsigned size, then reads that\n    many bytes and interprets them as a little-endian 2's-complement long.\n    If the size is 0, that's taken as a shortcut for the long 0L.\n    ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("long1", var13);
      var3 = null;
      var1.setline(654);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, read_long4$18, PyString.fromInterned("\n    >>> import StringIO\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\xff\\x00\"))\n    255L\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\xff\\x7f\"))\n    32767L\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\x00\\xff\"))\n    -256L\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\x00\\x80\"))\n    -32768L\n    >>> read_long1(StringIO.StringIO(\"\\x00\\x00\\x00\\x00\"))\n    0L\n    "));
      var1.setlocal("read_long4", var12);
      var3 = null;
      var1.setline(677);
      var10000 = var1.getname("ArgumentDescriptor");
      var10 = new PyObject[]{PyString.fromInterned("long4"), var1.getname("TAKEN_FROM_ARGUMENT4"), var1.getname("read_long4"), PyString.fromInterned("A binary representation of a long, little-endian.\n\n    This first reads four bytes as a signed size (but requires the\n    size to be >= 0), then reads that many bytes and interprets them\n    as a little-endian 2's-complement long.  If the size is 0, that's taken\n    as a shortcut for the long 0L, although LONG1 should really be used\n    then instead (and in any case where # of bytes < 256).\n    ")};
      var8 = new String[]{"name", "n", "reader", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("long4", var13);
      var3 = null;
      var1.setline(697);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("StackObject", var10, StackObject$19);
      var1.setlocal("StackObject", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(727);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("int"), var1.getname("int"), PyString.fromInterned("A short (as opposed to long) Python integer object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pyint", var13);
      var3 = null;
      var1.setline(732);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("long"), var1.getname("long"), PyString.fromInterned("A long (as opposed to short) Python integer object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pylong", var13);
      var3 = null;
      var1.setline(737);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("int_or_bool"), new PyTuple(new PyObject[]{var1.getname("int"), var1.getname("long"), var1.getname("bool")}), PyString.fromInterned("A Python integer object (short or long), or a Python bool.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pyinteger_or_bool", var13);
      var3 = null;
      var1.setline(743);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("bool"), new PyTuple(new PyObject[]{var1.getname("bool")}), PyString.fromInterned("A Python bool object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pybool", var13);
      var3 = null;
      var1.setline(748);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("float"), var1.getname("float"), PyString.fromInterned("A Python float object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pyfloat", var13);
      var3 = null;
      var1.setline(753);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("str"), var1.getname("str"), PyString.fromInterned("A Python string object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pystring", var13);
      var3 = null;
      var1.setline(758);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("unicode"), var1.getname("unicode"), PyString.fromInterned("A Python Unicode string object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pyunicode", var13);
      var3 = null;
      var1.setline(763);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("None"), var1.getname("type").__call__(var2, var1.getname("None")), PyString.fromInterned("The Python None object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pynone", var13);
      var3 = null;
      var1.setline(768);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("tuple"), var1.getname("tuple"), PyString.fromInterned("A Python tuple object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pytuple", var13);
      var3 = null;
      var1.setline(773);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("list"), var1.getname("list"), PyString.fromInterned("A Python list object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pylist", var13);
      var3 = null;
      var1.setline(778);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("dict"), var1.getname("dict"), PyString.fromInterned("A Python dict object.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("pydict", var13);
      var3 = null;
      var1.setline(783);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("any"), var1.getname("object"), PyString.fromInterned("Any kind of object whatsoever.")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("anyobject", var13);
      var3 = null;
      var1.setline(788);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("mark"), var1.getname("StackObject"), PyString.fromInterned("'The mark' is a unique object.\n\n                 Opcodes that operate on a variable number of objects\n                 generally don't embed the count of objects in the opcode,\n                 or pull it off the stack.  Instead the MARK opcode is used\n                 to push a special marker object on the stack, and then\n                 some other opcodes grab all the objects from the top of\n                 the stack down to (but not including) the topmost marker\n                 object.\n                 ")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("markobject", var13);
      var3 = null;
      var1.setline(802);
      var10000 = var1.getname("StackObject");
      var10 = new PyObject[]{PyString.fromInterned("stackslice"), var1.getname("StackObject"), PyString.fromInterned("An object representing a contiguous slice of the stack.\n\n                 This is used in conjuction with markobject, to represent all\n                 of the stack following the topmost markobject.  For example,\n                 the POP_MARK opcode changes the stack from\n\n                     [..., markobject, stackslice]\n                 to\n                     [...]\n\n                 No matter how many object are on the stack after the topmost\n                 markobject, POP_MARK gets rid of all of them (including the\n                 topmost markobject too).\n                 ")};
      var8 = new String[]{"name", "obtype", "doc"};
      var10000 = var10000.__call__(var2, var10, var8);
      var3 = null;
      var13 = var10000;
      var1.setlocal("stackslice", var13);
      var3 = null;
      var1.setline(823);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("OpcodeInfo", var10, OpcodeInfo$22);
      var1.setlocal("OpcodeInfo", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(882);
      var13 = var1.getname("OpcodeInfo");
      var1.setlocal("I", var13);
      var3 = null;
      var1.setline(883);
      PyObject[] var10002 = new PyObject[53];
      PyObject var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("INT"), PyString.fromInterned("I"), var1.getname("decimalnl_short"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyinteger_or_bool")}), Py.newInteger(0), PyString.fromInterned("Push an integer or bool.\n\n      The argument is a newline-terminated decimal literal string.\n\n      The intent may have been that this always fit in a short Python int,\n      but INT can be generated in pickles written on a 64-bit box that\n      require a Python long on a 32-bit box.  The difference between this\n      and LONG then is that INT skips a trailing 'L', and produces a short\n      int whenever possible.\n\n      Another difference is due to that, when bool was introduced as a\n      distinct type in 2.3, builtin names True and False were also added to\n      2.2.2, mapping to ints 1 and 0.  For compatibility in both directions,\n      True gets pickled as INT + \"I01\\n\", and False as INT + \"I00\\n\".\n      Leading zeroes are never produced for a genuine integer.  The 2.3\n      (and later) unpicklers special-case these and return bool instead;\n      earlier unpicklers ignore the leading \"0\" and return the int.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[0] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BININT"), PyString.fromInterned("J"), var1.getname("int4"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyint")}), Py.newInteger(1), PyString.fromInterned("Push a four-byte signed integer.\n\n      This handles the full range of Python (short) integers on a 32-bit\n      box, directly as binary bytes (1 for the opcode and 4 for the integer).\n      If the integer is non-negative and fits in 1 or 2 bytes, pickling via\n      BININT1 or BININT2 saves space.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[1] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BININT1"), PyString.fromInterned("K"), var1.getname("uint1"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyint")}), Py.newInteger(1), PyString.fromInterned("Push a one-byte unsigned integer.\n\n      This is a space optimization for pickling very small non-negative ints,\n      in range(256).\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[2] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BININT2"), PyString.fromInterned("M"), var1.getname("uint2"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyint")}), Py.newInteger(1), PyString.fromInterned("Push a two-byte unsigned integer.\n\n      This is a space optimization for pickling small positive ints, in\n      range(256, 2**16).  Integers in range(256) can also be pickled via\n      BININT2, but BININT1 instead saves a byte.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[3] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("LONG"), PyString.fromInterned("L"), var1.getname("decimalnl_long"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pylong")}), Py.newInteger(0), PyString.fromInterned("Push a long integer.\n\n      The same as INT, except that the literal ends with 'L', and always\n      unpickles to a Python long.  There doesn't seem a real purpose to the\n      trailing 'L'.\n\n      Note that LONG takes time quadratic in the number of digits when\n      unpickling (this is simply due to the nature of decimal->binary\n      conversion).  Proto 2 added linear-time (in C; still quadratic-time\n      in Python) LONG1 and LONG4 opcodes.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[4] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("LONG1"), PyString.fromInterned("\u008a"), var1.getname("long1"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pylong")}), Py.newInteger(2), PyString.fromInterned("Long integer using one-byte length.\n\n      A more efficient encoding of a Python long; the long1 encoding\n      says it all.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[5] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("LONG4"), PyString.fromInterned("\u008b"), var1.getname("long4"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pylong")}), Py.newInteger(2), PyString.fromInterned("Long integer using found-byte length.\n\n      A more efficient encoding of a Python long; the long4 encoding\n      says it all.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[6] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("STRING"), PyString.fromInterned("S"), var1.getname("stringnl"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pystring")}), Py.newInteger(0), PyString.fromInterned("Push a Python string object.\n\n      The argument is a repr-style string, with bracketing quote characters,\n      and perhaps embedded escapes.  The argument extends until the next\n      newline character.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[7] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BINSTRING"), PyString.fromInterned("T"), var1.getname("string4"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pystring")}), Py.newInteger(1), PyString.fromInterned("Push a Python string object.\n\n      There are two arguments:  the first is a 4-byte little-endian signed int\n      giving the number of bytes in the string, and the second is that many\n      bytes, which are taken literally as the string content.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[8] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("SHORT_BINSTRING"), PyString.fromInterned("U"), var1.getname("string1"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pystring")}), Py.newInteger(1), PyString.fromInterned("Push a Python string object.\n\n      There are two arguments:  the first is a 1-byte unsigned int giving\n      the number of bytes in the string, and the second is that many bytes,\n      which are taken literally as the string content.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[9] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("NONE"), PyString.fromInterned("N"), var1.getname("None"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pynone")}), Py.newInteger(0), PyString.fromInterned("Push None on the stack.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[10] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("NEWTRUE"), PyString.fromInterned("\u0088"), var1.getname("None"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pybool")}), Py.newInteger(2), PyString.fromInterned("True.\n\n      Push True onto the stack.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[11] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("NEWFALSE"), PyString.fromInterned("\u0089"), var1.getname("None"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pybool")}), Py.newInteger(2), PyString.fromInterned("True.\n\n      Push False onto the stack.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[12] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("UNICODE"), PyString.fromInterned("V"), var1.getname("unicodestringnl"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyunicode")}), Py.newInteger(0), PyString.fromInterned("Push a Python Unicode string object.\n\n      The argument is a raw-unicode-escape encoding of a Unicode string,\n      and so may contain embedded escape sequences.  The argument extends\n      until the next newline character.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[13] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BINUNICODE"), PyString.fromInterned("X"), var1.getname("unicodestring4"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyunicode")}), Py.newInteger(1), PyString.fromInterned("Push a Python Unicode string object.\n\n      There are two arguments:  the first is a 4-byte little-endian signed int\n      giving the number of bytes in the string.  The second is that many\n      bytes, and is the UTF-8 encoding of the Unicode string.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[14] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("FLOAT"), PyString.fromInterned("F"), var1.getname("floatnl"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyfloat")}), Py.newInteger(0), PyString.fromInterned("Newline-terminated decimal float literal.\n\n      The argument is repr(a_float), and in general requires 17 significant\n      digits for roundtrip conversion to be an identity (this is so for\n      IEEE-754 double precision values, which is what Python float maps to\n      on most boxes).\n\n      In general, FLOAT cannot be used to transport infinities, NaNs, or\n      minus zero across boxes (or even on a single box, if the platform C\n      library can't read the strings it produces for such things -- Windows\n      is like that), but may do less damage than BINFLOAT on boxes with\n      greater precision or dynamic range than IEEE-754 double.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[15] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BINFLOAT"), PyString.fromInterned("G"), var1.getname("float8"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pyfloat")}), Py.newInteger(1), PyString.fromInterned("Float stored in binary form, with 8 bytes of data.\n\n      This generally requires less than half the space of FLOAT encoding.\n      In general, BINFLOAT cannot be used to transport infinities, NaNs, or\n      minus zero, raises an exception if the exponent exceeds the range of\n      an IEEE-754 double, and retains no more than 53 bits of precision (if\n      there are more than that, \"add a half and chop\" rounding is used to\n      cut it back to 53 significant bits).\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[16] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("EMPTY_LIST"), PyString.fromInterned("]"), var1.getname("None"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pylist")}), Py.newInteger(1), PyString.fromInterned("Push an empty list.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[17] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("APPEND"), PyString.fromInterned("a"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("pylist"), var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("pylist")}), Py.newInteger(0), PyString.fromInterned("Append an object to a list.\n\n      Stack before:  ... pylist anyobject\n      Stack after:   ... pylist+[anyobject]\n\n      although pylist is really extended in-place.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[18] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("APPENDS"), PyString.fromInterned("e"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("pylist"), var1.getname("markobject"), var1.getname("stackslice")}), new PyList(new PyObject[]{var1.getname("pylist")}), Py.newInteger(1), PyString.fromInterned("Extend a list by a slice of stack objects.\n\n      Stack before:  ... pylist markobject stackslice\n      Stack after:   ... pylist+stackslice\n\n      although pylist is really extended in-place.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[19] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("LIST"), PyString.fromInterned("l"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("markobject"), var1.getname("stackslice")}), new PyList(new PyObject[]{var1.getname("pylist")}), Py.newInteger(0), PyString.fromInterned("Build a list out of the topmost stack slice, after markobject.\n\n      All the stack entries following the topmost markobject are placed into\n      a single Python list, which single list object replaces all of the\n      stack from the topmost markobject onward.  For example,\n\n      Stack before: ... markobject 1 2 3 'abc'\n      Stack after:  ... [1, 2, 3, 'abc']\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[20] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("EMPTY_TUPLE"), PyString.fromInterned(")"), var1.getname("None"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pytuple")}), Py.newInteger(1), PyString.fromInterned("Push an empty tuple.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[21] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("TUPLE"), PyString.fromInterned("t"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("markobject"), var1.getname("stackslice")}), new PyList(new PyObject[]{var1.getname("pytuple")}), Py.newInteger(0), PyString.fromInterned("Build a tuple out of the topmost stack slice, after markobject.\n\n      All the stack entries following the topmost markobject are placed into\n      a single Python tuple, which single tuple object replaces all of the\n      stack from the topmost markobject onward.  For example,\n\n      Stack before: ... markobject 1 2 3 'abc'\n      Stack after:  ... (1, 2, 3, 'abc')\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[22] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("TUPLE1"), PyString.fromInterned("\u0085"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("pytuple")}), Py.newInteger(2), PyString.fromInterned("Build a one-tuple out of the topmost item on the stack.\n\n      This code pops one value off the stack and pushes a tuple of\n      length 1 whose one item is that value back onto it.  In other\n      words:\n\n          stack[-1] = tuple(stack[-1:])\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[23] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("TUPLE2"), PyString.fromInterned("\u0086"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject"), var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("pytuple")}), Py.newInteger(2), PyString.fromInterned("Build a two-tuple out of the top two items on the stack.\n\n      This code pops two values off the stack and pushes a tuple of\n      length 2 whose items are those values back onto it.  In other\n      words:\n\n          stack[-2:] = [tuple(stack[-2:])]\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[24] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("TUPLE3"), PyString.fromInterned("\u0087"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject"), var1.getname("anyobject"), var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("pytuple")}), Py.newInteger(2), PyString.fromInterned("Build a three-tuple out of the top three items on the stack.\n\n      This code pops three values off the stack and pushes a tuple of\n      length 3 whose items are those values back onto it.  In other\n      words:\n\n          stack[-3:] = [tuple(stack[-3:])]\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[25] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("EMPTY_DICT"), PyString.fromInterned("}"), var1.getname("None"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("pydict")}), Py.newInteger(1), PyString.fromInterned("Push an empty dict.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[26] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("DICT"), PyString.fromInterned("d"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("markobject"), var1.getname("stackslice")}), new PyList(new PyObject[]{var1.getname("pydict")}), Py.newInteger(0), PyString.fromInterned("Build a dict out of the topmost stack slice, after markobject.\n\n      All the stack entries following the topmost markobject are placed into\n      a single Python dict, which single dict object replaces all of the\n      stack from the topmost markobject onward.  The stack slice alternates\n      key, value, key, value, ....  For example,\n\n      Stack before: ... markobject 1 2 3 'abc'\n      Stack after:  ... {1: 2, 3: 'abc'}\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[27] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("SETITEM"), PyString.fromInterned("s"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("pydict"), var1.getname("anyobject"), var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("pydict")}), Py.newInteger(0), PyString.fromInterned("Add a key+value pair to an existing dict.\n\n      Stack before:  ... pydict key value\n      Stack after:   ... pydict\n\n      where pydict has been modified via pydict[key] = value.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[28] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("SETITEMS"), PyString.fromInterned("u"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("pydict"), var1.getname("markobject"), var1.getname("stackslice")}), new PyList(new PyObject[]{var1.getname("pydict")}), Py.newInteger(1), PyString.fromInterned("Add an arbitrary number of key+value pairs to an existing dict.\n\n      The slice of the stack following the topmost markobject is taken as\n      an alternating sequence of keys and values, added to the dict\n      immediately under the topmost markobject.  Everything at and after the\n      topmost markobject is popped, leaving the mutated dict at the top\n      of the stack.\n\n      Stack before:  ... pydict markobject key_1 value_1 ... key_n value_n\n      Stack after:   ... pydict\n\n      where pydict has been modified via pydict[key_i] = value_i for i in\n      1, 2, ..., n, and in that order.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[29] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("POP"), PyString.fromInterned("0"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject")}), new PyList(Py.EmptyObjects), Py.newInteger(0), PyString.fromInterned("Discard the top stack item, shrinking the stack by one item.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[30] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("DUP"), PyString.fromInterned("2"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("anyobject"), var1.getname("anyobject")}), Py.newInteger(0), PyString.fromInterned("Push the top stack item onto the stack again, duplicating it.")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[31] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("MARK"), PyString.fromInterned("("), var1.getname("None"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("markobject")}), Py.newInteger(0), PyString.fromInterned("Push markobject onto the stack.\n\n      markobject is a unique object, used by other opcodes to identify a\n      region of the stack containing a variable number of objects for them\n      to work on.  See markobject.doc for more detail.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[32] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("POP_MARK"), PyString.fromInterned("1"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("markobject"), var1.getname("stackslice")}), new PyList(Py.EmptyObjects), Py.newInteger(1), PyString.fromInterned("Pop all the stack objects at and above the topmost markobject.\n\n      When an opcode using a variable number of stack objects is done,\n      POP_MARK is used to remove those objects, and to remove the markobject\n      that delimited their starting position on the stack.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[33] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("GET"), PyString.fromInterned("g"), var1.getname("decimalnl_short"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(0), PyString.fromInterned("Read an object from the memo and push it on the stack.\n\n      The index of the memo object to push is given by the newline-terminated\n      decimal string following.  BINGET and LONG_BINGET are space-optimized\n      versions.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[34] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BINGET"), PyString.fromInterned("h"), var1.getname("uint1"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(1), PyString.fromInterned("Read an object from the memo and push it on the stack.\n\n      The index of the memo object to push is given by the 1-byte unsigned\n      integer following.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[35] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("LONG_BINGET"), PyString.fromInterned("j"), var1.getname("int4"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(1), PyString.fromInterned("Read an object from the memo and push it on the stack.\n\n      The index of the memo object to push is given by the 4-byte signed\n      little-endian integer following.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[36] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("PUT"), PyString.fromInterned("p"), var1.getname("decimalnl_short"), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(0), PyString.fromInterned("Store the stack top into the memo.  The stack is not popped.\n\n      The index of the memo location to write into is given by the newline-\n      terminated decimal string following.  BINPUT and LONG_BINPUT are\n      space-optimized versions.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[37] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BINPUT"), PyString.fromInterned("q"), var1.getname("uint1"), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(1), PyString.fromInterned("Store the stack top into the memo.  The stack is not popped.\n\n      The index of the memo location to write into is given by the 1-byte\n      unsigned integer following.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[38] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("LONG_BINPUT"), PyString.fromInterned("r"), var1.getname("int4"), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(1), PyString.fromInterned("Store the stack top into the memo.  The stack is not popped.\n\n      The index of the memo location to write into is given by the 4-byte\n      signed little-endian integer following.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[39] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("EXT1"), PyString.fromInterned("\u0082"), var1.getname("uint1"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(2), PyString.fromInterned("Extension code.\n\n      This code and the similar EXT2 and EXT4 allow using a registry\n      of popular objects that are pickled by name, typically classes.\n      It is envisioned that through a global negotiation and\n      registration process, third parties can set up a mapping between\n      ints and object names.\n\n      In order to guarantee pickle interchangeability, the extension\n      code registry ought to be global, although a range of codes may\n      be reserved for private use.\n\n      EXT1 has a 1-byte integer argument.  This is used to index into the\n      extension registry, and the object at that index is pushed on the stack.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[40] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("EXT2"), PyString.fromInterned("\u0083"), var1.getname("uint2"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(2), PyString.fromInterned("Extension code.\n\n      See EXT1.  EXT2 has a two-byte integer argument.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[41] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("EXT4"), PyString.fromInterned("\u0084"), var1.getname("int4"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(2), PyString.fromInterned("Extension code.\n\n      See EXT1.  EXT4 has a four-byte integer argument.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[42] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("GLOBAL"), PyString.fromInterned("c"), var1.getname("stringnl_noescape_pair"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(0), PyString.fromInterned("Push a global object (module.attr) on the stack.\n\n      Two newline-terminated strings follow the GLOBAL opcode.  The first is\n      taken as a module name, and the second as a class name.  The class\n      object module.class is pushed on the stack.  More accurately, the\n      object returned by self.find_class(module, class) is pushed on the\n      stack, so unpickling subclasses can override this form of lookup.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[43] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("REDUCE"), PyString.fromInterned("R"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject"), var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(0), PyString.fromInterned("Push an object built from a callable and an argument tuple.\n\n      The opcode is named to remind of the __reduce__() method.\n\n      Stack before: ... callable pytuple\n      Stack after:  ... callable(*pytuple)\n\n      The callable and the argument tuple are the first two items returned\n      by a __reduce__ method.  Applying the callable to the argtuple is\n      supposed to reproduce the original object, or at least get it started.\n      If the __reduce__ method returns a 3-tuple, the last component is an\n      argument to be passed to the object's __setstate__, and then the REDUCE\n      opcode is followed by code to create setstate's argument, and then a\n      BUILD opcode to apply  __setstate__ to that argument.\n\n      If type(callable) is not ClassType, REDUCE complains unless the\n      callable has been registered with the copy_reg module's\n      safe_constructors dict, or the callable has a magic\n      '__safe_for_unpickling__' attribute with a true value.  I'm not sure\n      why it does this, but I've sure seen this complaint often enough when\n      I didn't want to <wink>.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[44] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BUILD"), PyString.fromInterned("b"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject"), var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(0), PyString.fromInterned("Finish building an object, via __setstate__ or dict update.\n\n      Stack before: ... anyobject argument\n      Stack after:  ... anyobject\n\n      where anyobject may have been mutated, as follows:\n\n      If the object has a __setstate__ method,\n\n          anyobject.__setstate__(argument)\n\n      is called.\n\n      Else the argument must be a dict, the object must have a __dict__, and\n      the object is updated via\n\n          anyobject.__dict__.update(argument)\n\n      This may raise RuntimeError in restricted execution mode (which\n      disallows access to __dict__ directly); in that case, the object\n      is updated instead via\n\n          for k, v in argument.items():\n              anyobject[k] = v\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[45] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("INST"), PyString.fromInterned("i"), var1.getname("stringnl_noescape_pair"), new PyList(new PyObject[]{var1.getname("markobject"), var1.getname("stackslice")}), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(0), PyString.fromInterned("Build a class instance.\n\n      This is the protocol 0 version of protocol 1's OBJ opcode.\n      INST is followed by two newline-terminated strings, giving a\n      module and class name, just as for the GLOBAL opcode (and see\n      GLOBAL for more details about that).  self.find_class(module, name)\n      is used to get a class object.\n\n      In addition, all the objects on the stack following the topmost\n      markobject are gathered into a tuple and popped (along with the\n      topmost markobject), just as for the TUPLE opcode.\n\n      Now it gets complicated.  If all of these are true:\n\n        + The argtuple is empty (markobject was at the top of the stack\n          at the start).\n\n        + It's an old-style class object (the type of the class object is\n          ClassType).\n\n        + The class object does not have a __getinitargs__ attribute.\n\n      then we want to create an old-style class instance without invoking\n      its __init__() method (pickle has waffled on this over the years; not\n      calling __init__() is current wisdom).  In this case, an instance of\n      an old-style dummy class is created, and then we try to rebind its\n      __class__ attribute to the desired class object.  If this succeeds,\n      the new instance object is pushed on the stack, and we're done.  In\n      restricted execution mode it can fail (assignment to __class__ is\n      disallowed), and I'm not really sure what happens then -- it looks\n      like the code ends up calling the class object's __init__ anyway,\n      via falling into the next case.\n\n      Else (the argtuple is not empty, it's not an old-style class object,\n      or the class object does have a __getinitargs__ attribute), the code\n      first insists that the class object have a __safe_for_unpickling__\n      attribute.  Unlike as for the __safe_for_unpickling__ check in REDUCE,\n      it doesn't matter whether this attribute has a true or false value, it\n      only matters whether it exists (XXX this is a bug; cPickle\n      requires the attribute to be true).  If __safe_for_unpickling__\n      doesn't exist, UnpicklingError is raised.\n\n      Else (the class object does have a __safe_for_unpickling__ attr),\n      the class object obtained from INST's arguments is applied to the\n      argtuple obtained from the stack, and the resulting instance object\n      is pushed on the stack.\n\n      NOTE:  checks for __safe_for_unpickling__ went away in Python 2.3.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[46] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("OBJ"), PyString.fromInterned("o"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("markobject"), var1.getname("anyobject"), var1.getname("stackslice")}), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(1), PyString.fromInterned("Build a class instance.\n\n      This is the protocol 1 version of protocol 0's INST opcode, and is\n      very much like it.  The major difference is that the class object\n      is taken off the stack, allowing it to be retrieved from the memo\n      repeatedly if several instances of the same class are created.  This\n      can be much more efficient (in both time and space) than repeatedly\n      embedding the module and class names in INST opcodes.\n\n      Unlike INST, OBJ takes no arguments from the opcode stream.  Instead\n      the class object is taken off the stack, immediately above the\n      topmost markobject:\n\n      Stack before: ... markobject classobject stackslice\n      Stack after:  ... new_instance_object\n\n      As for INST, the remainder of the stack above the markobject is\n      gathered into an argument tuple, and then the logic seems identical,\n      except that no __safe_for_unpickling__ check is done (XXX this is\n      a bug; cPickle does test __safe_for_unpickling__).  See INST for\n      the gory details.\n\n      NOTE:  In Python 2.3, INST and OBJ are identical except for how they\n      get the class object.  That was always the intent; the implementations\n      had diverged for accidental reasons.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[47] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("NEWOBJ"), PyString.fromInterned("\u0081"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject"), var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(2), PyString.fromInterned("Build an object instance.\n\n      The stack before should be thought of as containing a class\n      object followed by an argument tuple (the tuple being the stack\n      top).  Call these cls and args.  They are popped off the stack,\n      and the value returned by cls.__new__(cls, *args) is pushed back\n      onto the stack.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[48] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("PROTO"), PyString.fromInterned("\u0080"), var1.getname("uint1"), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(2), PyString.fromInterned("Protocol version indicator.\n\n      For protocol 2 and above, a pickle must start with this opcode.\n      The argument is the protocol version, an int in range(2, 256).\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[49] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("STOP"), PyString.fromInterned("."), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject")}), new PyList(Py.EmptyObjects), Py.newInteger(0), PyString.fromInterned("Stop the unpickling machine.\n\n      Every pickle ends with this opcode.  The object at the top of the stack\n      is popped, and that's the result of unpickling.  The stack should be\n      empty then.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[50] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("PERSID"), PyString.fromInterned("P"), var1.getname("stringnl_noescape"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(0), PyString.fromInterned("Push an object identified by a persistent ID.\n\n      The pickle module doesn't define what a persistent ID means.  PERSID's\n      argument is a newline-terminated str-style (no embedded escapes, no\n      bracketing quote characters) string, which *is* \"the persistent ID\".\n      The unpickler passes this string to self.persistent_load().  Whatever\n      object that returns is pushed on the stack.  There is no implementation\n      of persistent_load() in Python's unpickler:  it must be supplied by an\n      unpickler subclass.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[51] = var10005;
      var10005 = var1.getname("I");
      var10 = new PyObject[]{PyString.fromInterned("BINPERSID"), PyString.fromInterned("Q"), var1.getname("None"), new PyList(new PyObject[]{var1.getname("anyobject")}), new PyList(new PyObject[]{var1.getname("anyobject")}), Py.newInteger(1), PyString.fromInterned("Push an object identified by a persistent ID.\n\n      Like PERSID, except the persistent ID is popped off the stack (instead\n      of being a string embedded in the opcode bytestream).  The persistent\n      ID is passed to self.persistent_load(), and whatever object that\n      returns is pushed on the stack.  See PERSID for more detail.\n      ")};
      var8 = new String[]{"name", "code", "arg", "stack_before", "stack_after", "proto", "doc"};
      var10005 = var10005.__call__(var2, var10, var8);
      var3 = null;
      var10002[52] = var10005;
      var3 = new PyList(var10002);
      var1.setlocal("opcodes", var3);
      var3 = null;
      var1.setline(1734);
      var1.dellocal("I");
      var1.setline(1737);
      PyDictionary var14 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("name2i", var14);
      var3 = null;
      var1.setline(1738);
      var14 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("code2i", var14);
      var3 = null;
      var1.setline(1740);
      var13 = var1.getname("enumerate").__call__(var2, var1.getname("opcodes")).__iter__();

      while(true) {
         var1.setline(1740);
         var4 = var13.__iternext__();
         PyObject[] var5;
         PyObject var9;
         if (var4 == null) {
            var1.setline(1751);
            var1.dellocal("name2i");
            var1.dellocal("code2i");
            var1.dellocal("i");
            var1.dellocal("d");
            var1.setline(1758);
            var14 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal("code2op", var14);
            var3 = null;
            var1.setline(1759);
            var13 = var1.getname("opcodes").__iter__();

            while(true) {
               var1.setline(1759);
               var4 = var13.__iternext__();
               if (var4 == null) {
                  var1.setline(1761);
                  var1.dellocal("d");
                  var1.setline(1763);
                  var10 = new PyObject[]{var1.getname("False")};
                  var12 = new PyFunction(var1.f_globals, var10, assure_pickle_consistency$24, (PyObject)null);
                  var1.setlocal("assure_pickle_consistency", var12);
                  var3 = null;
                  var1.setline(1801);
                  var1.getname("assure_pickle_consistency").__call__(var2);
                  var1.setline(1802);
                  var1.dellocal("assure_pickle_consistency");
                  var1.setline(1807);
                  var10 = Py.EmptyObjects;
                  var12 = new PyFunction(var1.f_globals, var10, genops$25, PyString.fromInterned("Generate all the opcodes in a pickle.\n\n    'pickle' is a file-like object, or string, containing the pickle.\n\n    Each opcode in the pickle is generated, from the current pickle position,\n    stopping after a STOP opcode is delivered.  A triple is generated for\n    each opcode:\n\n        opcode, arg, pos\n\n    opcode is an OpcodeInfo record, describing the current opcode.\n\n    If the opcode has an argument embedded in the pickle, arg is its decoded\n    value, as a Python object.  If the opcode doesn't have an argument, arg\n    is None.\n\n    If the pickle has a tell() method, pos was the value of pickle.tell()\n    before reading the current opcode.  If the pickle is a string object,\n    it's wrapped in a StringIO object, and the latter's tell() result is\n    used.  Else (the pickle doesn't have a tell(), and it's not obvious how\n    to query its current position) pos is None.\n    "));
                  var1.setlocal("genops", var12);
                  var3 = null;
                  var1.setline(1864);
                  var10 = Py.EmptyObjects;
                  var12 = new PyFunction(var1.f_globals, var10, optimize$27, PyString.fromInterned("Optimize a pickle string by removing unused PUT opcodes"));
                  var1.setlocal("optimize", var12);
                  var3 = null;
                  var1.setline(1891);
                  var10 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(4)};
                  var12 = new PyFunction(var1.f_globals, var10, dis$28, PyString.fromInterned("Produce a symbolic disassembly of a pickle.\n\n    'pickle' is a file-like object, or string, containing a (at least one)\n    pickle.  The pickle is disassembled from the current position, through\n    the first STOP opcode encountered.\n\n    Optional arg 'out' is a file-like object to which the disassembly is\n    printed.  It defaults to sys.stdout.\n\n    Optional arg 'memo' is a Python dict, used as the pickle's memo.  It\n    may be mutated by dis(), if the pickle contains PUT or BINPUT opcodes.\n    Passing the same memo object to another dis() call then allows disassembly\n    to proceed across multiple pickles that were all created by the same\n    pickler with the same memo.  Ordinarily you don't need to worry about this.\n\n    Optional arg indentlevel is the number of blanks by which to indent\n    a new MARK level.  It defaults to 4.\n\n    In addition to printing the disassembly, some sanity checks are made:\n\n    + All embedded opcode arguments \"make sense\".\n\n    + Explicit and implicit pop operations have enough items on the stack.\n\n    + When an opcode implicitly refers to a markobject, a markobject is\n      actually on the stack.\n\n    + A memo entry isn't referenced before it's defined.\n\n    + The markobject isn't stored in the memo.\n\n    + A memo entry isn't redefined.\n    "));
                  var1.setlocal("dis", var12);
                  var3 = null;
                  var1.setline(2028);
                  var10 = Py.EmptyObjects;
                  var4 = Py.makeClass("_Example", var10, _Example$29);
                  var1.setlocal("_Example", var4);
                  var4 = null;
                  Arrays.fill(var10, (Object)null);
                  var1.setline(2032);
                  PyString var15 = PyString.fromInterned("\n>>> import pickle\n>>> x = [1, 2, (3, 4), {'abc': u\"def\"}]\n>>> pkl = pickle.dumps(x, 0)\n>>> dis(pkl)\n    0: (    MARK\n    1: l        LIST       (MARK at 0)\n    2: p    PUT        0\n    5: I    INT        1\n    8: a    APPEND\n    9: I    INT        2\n   12: a    APPEND\n   13: (    MARK\n   14: I        INT        3\n   17: I        INT        4\n   20: t        TUPLE      (MARK at 13)\n   21: p    PUT        1\n   24: a    APPEND\n   25: (    MARK\n   26: d        DICT       (MARK at 25)\n   27: p    PUT        2\n   30: S    STRING     'abc'\n   37: p    PUT        3\n   40: V    UNICODE    u'def'\n   45: p    PUT        4\n   48: s    SETITEM\n   49: a    APPEND\n   50: .    STOP\nhighest protocol among opcodes = 0\n\nTry again with a \"binary\" pickle.\n\n>>> pkl = pickle.dumps(x, 1)\n>>> dis(pkl)\n    0: ]    EMPTY_LIST\n    1: q    BINPUT     0\n    3: (    MARK\n    4: K        BININT1    1\n    6: K        BININT1    2\n    8: (        MARK\n    9: K            BININT1    3\n   11: K            BININT1    4\n   13: t            TUPLE      (MARK at 8)\n   14: q        BINPUT     1\n   16: }        EMPTY_DICT\n   17: q        BINPUT     2\n   19: U        SHORT_BINSTRING 'abc'\n   24: q        BINPUT     3\n   26: X        BINUNICODE u'def'\n   34: q        BINPUT     4\n   36: s        SETITEM\n   37: e        APPENDS    (MARK at 3)\n   38: .    STOP\nhighest protocol among opcodes = 1\n\nExercise the INST/OBJ/BUILD family.\n\n>>> import pickletools\n>>> dis(pickle.dumps(pickletools.dis, 0))\n    0: c    GLOBAL     'pickletools dis'\n   17: p    PUT        0\n   20: .    STOP\nhighest protocol among opcodes = 0\n\n>>> from pickletools import _Example\n>>> x = [_Example(42)] * 2\n>>> dis(pickle.dumps(x, 0))\n    0: (    MARK\n    1: l        LIST       (MARK at 0)\n    2: p    PUT        0\n    5: (    MARK\n    6: i        INST       'pickletools _Example' (MARK at 5)\n   28: p    PUT        1\n   31: (    MARK\n   32: d        DICT       (MARK at 31)\n   33: p    PUT        2\n   36: S    STRING     'value'\n   45: p    PUT        3\n   48: I    INT        42\n   52: s    SETITEM\n   53: b    BUILD\n   54: a    APPEND\n   55: g    GET        1\n   58: a    APPEND\n   59: .    STOP\nhighest protocol among opcodes = 0\n\n>>> dis(pickle.dumps(x, 1))\n    0: ]    EMPTY_LIST\n    1: q    BINPUT     0\n    3: (    MARK\n    4: (        MARK\n    5: c            GLOBAL     'pickletools _Example'\n   27: q            BINPUT     1\n   29: o            OBJ        (MARK at 4)\n   30: q        BINPUT     2\n   32: }        EMPTY_DICT\n   33: q        BINPUT     3\n   35: U        SHORT_BINSTRING 'value'\n   42: q        BINPUT     4\n   44: K        BININT1    42\n   46: s        SETITEM\n   47: b        BUILD\n   48: h        BINGET     2\n   50: e        APPENDS    (MARK at 3)\n   51: .    STOP\nhighest protocol among opcodes = 1\n\nTry \"the canonical\" recursive-object test.\n\n>>> L = []\n>>> T = L,\n>>> L.append(T)\n>>> L[0] is T\nTrue\n>>> T[0] is L\nTrue\n>>> L[0][0] is L\nTrue\n>>> T[0][0] is T\nTrue\n>>> dis(pickle.dumps(L, 0))\n    0: (    MARK\n    1: l        LIST       (MARK at 0)\n    2: p    PUT        0\n    5: (    MARK\n    6: g        GET        0\n    9: t        TUPLE      (MARK at 5)\n   10: p    PUT        1\n   13: a    APPEND\n   14: .    STOP\nhighest protocol among opcodes = 0\n\n>>> dis(pickle.dumps(L, 1))\n    0: ]    EMPTY_LIST\n    1: q    BINPUT     0\n    3: (    MARK\n    4: h        BINGET     0\n    6: t        TUPLE      (MARK at 3)\n    7: q    BINPUT     1\n    9: a    APPEND\n   10: .    STOP\nhighest protocol among opcodes = 1\n\nNote that, in the protocol 0 pickle of the recursive tuple, the disassembler\nhas to emulate the stack in order to realize that the POP opcode at 16 gets\nrid of the MARK at 0.\n\n>>> dis(pickle.dumps(T, 0))\n    0: (    MARK\n    1: (        MARK\n    2: l            LIST       (MARK at 1)\n    3: p        PUT        0\n    6: (        MARK\n    7: g            GET        0\n   10: t            TUPLE      (MARK at 6)\n   11: p        PUT        1\n   14: a        APPEND\n   15: 0        POP\n   16: 0        POP        (MARK at 0)\n   17: g    GET        1\n   20: .    STOP\nhighest protocol among opcodes = 0\n\n>>> dis(pickle.dumps(T, 1))\n    0: (    MARK\n    1: ]        EMPTY_LIST\n    2: q        BINPUT     0\n    4: (        MARK\n    5: h            BINGET     0\n    7: t            TUPLE      (MARK at 4)\n    8: q        BINPUT     1\n   10: a        APPEND\n   11: 1        POP_MARK   (MARK at 0)\n   12: h    BINGET     1\n   14: .    STOP\nhighest protocol among opcodes = 1\n\nTry protocol 2.\n\n>>> dis(pickle.dumps(L, 2))\n    0: \\x80 PROTO      2\n    2: ]    EMPTY_LIST\n    3: q    BINPUT     0\n    5: h    BINGET     0\n    7: \\x85 TUPLE1\n    8: q    BINPUT     1\n   10: a    APPEND\n   11: .    STOP\nhighest protocol among opcodes = 2\n\n>>> dis(pickle.dumps(T, 2))\n    0: \\x80 PROTO      2\n    2: ]    EMPTY_LIST\n    3: q    BINPUT     0\n    5: h    BINGET     0\n    7: \\x85 TUPLE1\n    8: q    BINPUT     1\n   10: a    APPEND\n   11: 0    POP\n   12: h    BINGET     1\n   14: .    STOP\nhighest protocol among opcodes = 2\n");
                  var1.setlocal("_dis_test", var15);
                  var3 = null;
                  var1.setline(2237);
                  var15 = PyString.fromInterned("\n>>> import pickle\n>>> from StringIO import StringIO\n>>> f = StringIO()\n>>> p = pickle.Pickler(f, 2)\n>>> x = [1, 2, 3]\n>>> p.dump(x)\n>>> p.dump(x)\n>>> f.seek(0)\n>>> memo = {}\n>>> dis(f, memo=memo)\n    0: \\x80 PROTO      2\n    2: ]    EMPTY_LIST\n    3: q    BINPUT     0\n    5: (    MARK\n    6: K        BININT1    1\n    8: K        BININT1    2\n   10: K        BININT1    3\n   12: e        APPENDS    (MARK at 5)\n   13: .    STOP\nhighest protocol among opcodes = 2\n>>> dis(f, memo=memo)\n   14: \\x80 PROTO      2\n   16: h    BINGET     0\n   18: .    STOP\nhighest protocol among opcodes = 2\n");
                  var1.setlocal("_memo_test", var15);
                  var3 = null;
                  var1.setline(2265);
                  var14 = new PyDictionary(new PyObject[]{PyString.fromInterned("disassembler_test"), var1.getname("_dis_test"), PyString.fromInterned("disassembler_memo_test"), var1.getname("_memo_test")});
                  var1.setlocal("__test__", var14);
                  var3 = null;
                  var1.setline(2269);
                  var10 = Py.EmptyObjects;
                  var12 = new PyFunction(var1.f_globals, var10, _test$31, (PyObject)null);
                  var1.setlocal("_test", var12);
                  var3 = null;
                  var1.setline(2273);
                  var13 = var1.getname("__name__");
                  var10000 = var13._eq(PyString.fromInterned("__main__"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2274);
                     var1.getname("_test").__call__(var2);
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal("d", var4);
               var1.setline(1760);
               var9 = var1.getname("d");
               var1.getname("code2op").__setitem__(var1.getname("d").__getattr__("code"), var9);
               var5 = null;
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal("i", var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal("d", var6);
         var6 = null;
         var1.setline(1741);
         var9 = var1.getname("d").__getattr__("name");
         var10000 = var9._in(var1.getname("name2i"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1742);
            throw Py.makeException(var1.getname("ValueError").__call__(var2, PyString.fromInterned("repeated name %r at indices %d and %d")._mod(new PyTuple(new PyObject[]{var1.getname("d").__getattr__("name"), var1.getname("name2i").__getitem__(var1.getname("d").__getattr__("name")), var1.getname("i")}))));
         }

         var1.setline(1744);
         var9 = var1.getname("d").__getattr__("code");
         var10000 = var9._in(var1.getname("code2i"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1745);
            throw Py.makeException(var1.getname("ValueError").__call__(var2, PyString.fromInterned("repeated code %r at indices %d and %d")._mod(new PyTuple(new PyObject[]{var1.getname("d").__getattr__("code"), var1.getname("code2i").__getitem__(var1.getname("d").__getattr__("code")), var1.getname("i")}))));
         }

         var1.setline(1748);
         var9 = var1.getname("i");
         var1.getname("name2i").__setitem__(var1.getname("d").__getattr__("name"), var9);
         var5 = null;
         var1.setline(1749);
         var9 = var1.getname("i");
         var1.getname("code2i").__setitem__(var1.getname("d").__getattr__("code"), var9);
         var5 = null;
      }
   }

   public PyObject ArgumentDescriptor$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(166);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("n"), PyString.fromInterned("reader"), PyString.fromInterned("doc")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(184);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(186);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(188);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("int"));
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(2);
               var10000 = var3._ge(Py.newInteger(0));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(2);
                  var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("UP_TO_NEWLINE"), var1.getglobal("TAKEN_FROM_ARGUMENT1"), var1.getglobal("TAKEN_FROM_ARGUMENT4")}));
                  var3 = null;
               }
            }

            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(192);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("n", var3);
         var3 = null;
         var1.setline(194);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("reader", var3);
         var3 = null;
         var1.setline(196);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("str")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(197);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setattr__("doc", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject read_uint1$3(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_uint1(StringIO.StringIO('\\xff'))\n    255\n    ");
      var1.setline(208);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(209);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(210);
         var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(211);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not enough data in stream to read uint1")));
      }
   }

   public PyObject read_uint2$4(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_uint2(StringIO.StringIO('\\xff\\x00'))\n    255\n    >>> read_uint2(StringIO.StringIO('\\xff\\xff'))\n    65535\n    ");
      var1.setline(229);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(230);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(231);
         var3 = var1.getglobal("_unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<H"), (PyObject)var1.getlocal(1)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(232);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not enough data in stream to read uint2")));
      }
   }

   public PyObject read_int4$5(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_int4(StringIO.StringIO('\\xff\\x00\\x00\\x00'))\n    255\n    >>> read_int4(StringIO.StringIO('\\x00\\x00\\x00\\x80')) == -(2**31)\n    True\n    ");
      var1.setline(250);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(251);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(252);
         var3 = var1.getglobal("_unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(1)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(253);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not enough data in stream to read int4")));
      }
   }

   public PyObject read_stringnl$6(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_stringnl(StringIO.StringIO(\"'abcd'\\nefg\\n\"))\n    'abcd'\n\n    >>> read_stringnl(StringIO.StringIO(\"\\n\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: no string quotes around ''\n\n    >>> read_stringnl(StringIO.StringIO(\"\\n\"), stripquotes=False)\n    ''\n\n    >>> read_stringnl(StringIO.StringIO(\"''\\n\"))\n    ''\n\n    >>> read_stringnl(StringIO.StringIO('\"abcd\"'))\n    Traceback (most recent call last):\n    ...\n    ValueError: no newline found when trying to read stringnl\n\n    Embedded escapes are undone in the result.\n    >>> read_stringnl(StringIO.StringIO(r\"'a\\n\\\\b\\x00c\\td'\" + \"\\n'e'\"))\n    'a\\n\\\\b\\x00c\\td'\n    ");
      var1.setline(289);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(290);
      if (var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
         var1.setline(291);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no newline found when trying to read stringnl")));
      } else {
         var1.setline(292);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(294);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(295);
            var3 = PyString.fromInterned("'\"").__iter__();

            do {
               var1.setline(295);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(303);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("no string quotes around %r")._mod(var1.getlocal(3))));
               }

               var1.setlocal(4, var4);
               var1.setline(296);
            } while(!var1.getlocal(3).__getattr__("startswith").__call__(var2, var1.getlocal(4)).__nonzero__());

            var1.setline(297);
            if (var1.getlocal(3).__getattr__("endswith").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
               var1.setline(298);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("strinq quote %r not found at both ends of %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)}))));
            }

            var1.setline(300);
            PyObject var5 = var1.getlocal(3).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(3, var5);
            var5 = null;
         }

         var1.setline(307);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(308);
            var3 = var1.getlocal(3).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("string_escape"));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(309);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read_stringnl_noescape$7(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyObject var10000 = var1.getglobal("read_stringnl");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getglobal("False"), var1.getglobal("False")};
      String[] var4 = new String[]{"decode", "stripquotes"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject read_stringnl_noescape_pair$8(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_stringnl_noescape_pair(StringIO.StringIO(\"Queue\\nEmpty\\njunk\"))\n    'Queue Empty'\n    ");
      var1.setline(342);
      PyObject var3 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("read_stringnl_noescape").__call__(var2, var1.getlocal(0)), var1.getglobal("read_stringnl_noescape").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read_string4$9(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_string4(StringIO.StringIO(\"\\x00\\x00\\x00\\x00abc\"))\n    ''\n    >>> read_string4(StringIO.StringIO(\"\\x03\\x00\\x00\\x00abcdef\"))\n    'abc'\n    >>> read_string4(StringIO.StringIO(\"\\x00\\x00\\x00\\x03abcdef\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: expected 50331648 bytes in a string4, but only 6 remain\n    ");
      var1.setline(370);
      PyObject var3 = var1.getglobal("read_int4").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(371);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(372);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("string4 byte count < 0: %d")._mod(var1.getlocal(1))));
      } else {
         var1.setline(373);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(374);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(375);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(376);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("expected %d bytes in a string4, but only %d remain")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(2))}))));
         }
      }
   }

   public PyObject read_string1$10(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_string1(StringIO.StringIO(\"\\x00\"))\n    ''\n    >>> read_string1(StringIO.StringIO(\"\\x03abcdef\"))\n    'abc'\n    ");
      var1.setline(400);
      PyObject var3 = var1.getglobal("read_uint1").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(401);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(402);
      var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(403);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(404);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(405);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("expected %d bytes in a string1, but only %d remain")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(2))}))));
      }
   }

   public PyObject read_unicodestringnl$11(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_unicodestringnl(StringIO.StringIO(\"abc\\uabcd\\njunk\"))\n    u'abc\\uabcd'\n    ");
      var1.setline(427);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(428);
      if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
         var1.setline(429);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no newline found when trying to read unicodestringnl")));
      } else {
         var1.setline(431);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(432);
         var3 = var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("raw-unicode-escape"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read_unicodestring4$12(PyFrame var1, ThreadState var2) {
      var1.setline(461);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> s = u'abcd\\uabcd'\n    >>> enc = s.encode('utf-8')\n    >>> enc\n    'abcd\\xea\\xaf\\x8d'\n    >>> n = chr(len(enc)) + chr(0) * 3  # little-endian 4-byte length\n    >>> t = read_unicodestring4(StringIO.StringIO(n + enc + 'junk'))\n    >>> s == t\n    True\n\n    >>> read_unicodestring4(StringIO.StringIO(n + enc[:-1]))\n    Traceback (most recent call last):\n    ...\n    ValueError: expected 7 bytes in a unicodestring4, but only 6 remain\n    ");
      var1.setline(463);
      PyObject var3 = var1.getglobal("read_int4").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(464);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(465);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unicodestring4 byte count < 0: %d")._mod(var1.getlocal(1))));
      } else {
         var1.setline(466);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(467);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(468);
            var3 = var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("utf-8"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(469);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("expected %d bytes in a unicodestring4, but only %d remain")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(2))}))));
         }
      }
   }

   public PyObject read_decimalnl_short$13(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_decimalnl_short(StringIO.StringIO(\"1234\\n56\"))\n    1234\n\n    >>> read_decimalnl_short(StringIO.StringIO(\"1234L\\n56\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: trailing 'L' not allowed in '1234L'\n    ");
      var1.setline(497);
      PyObject var10000 = var1.getglobal("read_stringnl");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getglobal("False"), var1.getglobal("False")};
      String[] var4 = new String[]{"decode", "stripquotes"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(498);
      if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L")).__nonzero__()) {
         var1.setline(499);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("trailing 'L' not allowed in %r")._mod(var1.getlocal(1))));
      } else {
         var1.setline(504);
         var6 = var1.getlocal(1);
         var10000 = var6._eq(PyString.fromInterned("00"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(505);
            var6 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var6;
         } else {
            var1.setline(506);
            PyObject var7 = var1.getlocal(1);
            var10000 = var7._eq(PyString.fromInterned("01"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(507);
               var6 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var6;
            } else {
               try {
                  var1.setline(510);
                  var6 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var6;
               } catch (Throwable var5) {
                  PyException var8 = Py.setException(var5, var1);
                  if (var8.match(var1.getglobal("OverflowError"))) {
                     var1.setline(512);
                     var6 = var1.getglobal("long").__call__(var2, var1.getlocal(1));
                     var1.f_lasti = -1;
                     return var6;
                  } else {
                     throw var8;
                  }
               }
            }
         }
      }
   }

   public PyObject read_decimalnl_long$14(PyFrame var1, ThreadState var2) {
      var1.setline(530);
      PyString.fromInterned("\n    >>> import StringIO\n\n    >>> read_decimalnl_long(StringIO.StringIO(\"1234\\n56\"))\n    Traceback (most recent call last):\n    ...\n    ValueError: trailing 'L' required in '1234'\n\n    Someday the trailing 'L' will probably go away from this output.\n\n    >>> read_decimalnl_long(StringIO.StringIO(\"1234L\\n56\"))\n    1234L\n\n    >>> read_decimalnl_long(StringIO.StringIO(\"123456789012345678901234L\\n6\"))\n    123456789012345678901234L\n    ");
      var1.setline(532);
      PyObject var10000 = var1.getglobal("read_stringnl");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getglobal("False"), var1.getglobal("False")};
      String[] var4 = new String[]{"decode", "stripquotes"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(533);
      if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L")).__not__().__nonzero__()) {
         var1.setline(534);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("trailing 'L' required in %r")._mod(var1.getlocal(1))));
      } else {
         var1.setline(535);
         var5 = var1.getglobal("long").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject read_floatnl$15(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_floatnl(StringIO.StringIO(\"-1.25\\n6\"))\n    -1.25\n    ");
      var1.setline(568);
      PyObject var10000 = var1.getglobal("read_stringnl");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getglobal("False"), var1.getglobal("False")};
      String[] var4 = new String[]{"decode", "stripquotes"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(569);
      var5 = var1.getglobal("float").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject read_float8$16(PyFrame var1, ThreadState var2) {
      var1.setline(592);
      PyString.fromInterned("\n    >>> import StringIO, struct\n    >>> raw = struct.pack(\">d\", -1.25)\n    >>> raw\n    '\\xbf\\xf4\\x00\\x00\\x00\\x00\\x00\\x00'\n    >>> read_float8(StringIO.StringIO(raw + \"\\n\"))\n    -1.25\n    ");
      var1.setline(594);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(8));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(595);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(8));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(596);
         var3 = var1.getglobal("_unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">d"), (PyObject)var1.getlocal(1)).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(597);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not enough data in stream to read float8")));
      }
   }

   public PyObject read_long1$17(PyFrame var1, ThreadState var2) {
      var1.setline(635);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_long1(StringIO.StringIO(\"\\x00\"))\n    0L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\xff\\x00\"))\n    255L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\xff\\x7f\"))\n    32767L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\x00\\xff\"))\n    -256L\n    >>> read_long1(StringIO.StringIO(\"\\x02\\x00\\x80\"))\n    -32768L\n    ");
      var1.setline(637);
      PyObject var3 = var1.getglobal("read_uint1").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(638);
      var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(639);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._ne(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(640);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not enough data in stream to read long1")));
      } else {
         var1.setline(641);
         var3 = var1.getglobal("decode_long").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read_long4$18(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      PyString.fromInterned("\n    >>> import StringIO\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\xff\\x00\"))\n    255L\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\xff\\x7f\"))\n    32767L\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\x00\\xff\"))\n    -256L\n    >>> read_long4(StringIO.StringIO(\"\\x02\\x00\\x00\\x00\\x00\\x80\"))\n    -32768L\n    >>> read_long1(StringIO.StringIO(\"\\x00\\x00\\x00\\x00\"))\n    0L\n    ");
      var1.setline(669);
      PyObject var3 = var1.getglobal("read_int4").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(670);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(671);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("long4 byte count < 0: %d")._mod(var1.getlocal(1))));
      } else {
         var1.setline(672);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(673);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._ne(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(674);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not enough data in stream to read long4")));
         } else {
            var1.setline(675);
            var3 = var1.getglobal("decode_long").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject StackObject$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(698);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("obtype"), PyString.fromInterned("doc")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(710);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(723);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$21, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(711);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(712);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(714);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("type"));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple"));
            }

            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(715);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple")).__nonzero__()) {
            var1.setline(716);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(716);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(4, var4);
               var1.setline(717);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("type")).__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }
         }

         var1.setline(718);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("obtype", var3);
         var3 = null;
         var1.setline(720);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("str")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(721);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("doc", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __repr__$21(PyFrame var1, ThreadState var2) {
      var1.setline(724);
      PyObject var3 = var1.getlocal(0).__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OpcodeInfo$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(825);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("code"), PyString.fromInterned("arg"), PyString.fromInterned("stack_before"), PyString.fromInterned("stack_after"), PyString.fromInterned("proto"), PyString.fromInterned("doc")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(854);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(856);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(857);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(859);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(860);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._eq(Py.newInteger(1));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(861);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("code", var3);
            var3 = null;
            var1.setline(863);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("ArgumentDescriptor"));
               }

               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(864);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("arg", var3);
            var3 = null;
            var1.setline(866);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("list")).__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            } else {
               var1.setline(867);
               var3 = var1.getlocal(4).__iter__();

               do {
                  var1.setline(867);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(869);
                     var3 = var1.getlocal(4);
                     var1.getlocal(0).__setattr__("stack_before", var3);
                     var3 = null;
                     var1.setline(871);
                     if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("list")).__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }

                     var1.setline(872);
                     var3 = var1.getlocal(5).__iter__();

                     do {
                        var1.setline(872);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.setline(874);
                           var3 = var1.getlocal(5);
                           var1.getlocal(0).__setattr__("stack_after", var3);
                           var3 = null;
                           var1.setline(876);
                           if (var1.getglobal("__debug__").__nonzero__()) {
                              var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("int"));
                              if (var10000.__nonzero__()) {
                                 PyInteger var5 = Py.newInteger(0);
                                 PyObject var10001 = var1.getlocal(6);
                                 PyInteger var6 = var5;
                                 var3 = var10001;
                                 if ((var4 = var6._le(var10001)).__nonzero__()) {
                                    var4 = var3._le(Py.newInteger(2));
                                 }

                                 var10000 = var4;
                                 var3 = null;
                              }

                              if (!var10000.__nonzero__()) {
                                 var10000 = Py.None;
                                 throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                              }
                           }

                           var1.setline(877);
                           var3 = var1.getlocal(6);
                           var1.getlocal(0).__setattr__("proto", var3);
                           var3 = null;
                           var1.setline(879);
                           if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("str")).__nonzero__()) {
                              var10000 = Py.None;
                              throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                           }

                           var1.setline(880);
                           var3 = var1.getlocal(7);
                           var1.getlocal(0).__setattr__("doc", var3);
                           var3 = null;
                           var1.f_lasti = -1;
                           return Py.None;
                        }

                        var1.setlocal(8, var4);
                        var1.setline(873);
                     } while(!var1.getglobal("__debug__").__nonzero__() || var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("StackObject")).__nonzero__());

                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }

                  var1.setlocal(8, var4);
                  var1.setline(868);
               } while(!var1.getglobal("__debug__").__nonzero__() || var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("StackObject")).__nonzero__());

               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }
      }
   }

   public PyObject assure_pickle_consistency$24(PyFrame var1, ThreadState var2) {
      var1.setline(1764);
      PyObject var3 = imp.importOne("pickle", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1766);
      var3 = var1.getglobal("code2op").__getattr__("copy").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1767);
      var3 = var1.getlocal(1).__getattr__("__all__").__iter__();

      while(true) {
         var1.setline(1767);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1795);
            if (!var1.getlocal(3).__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(1796);
               PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("we appear to have pickle opcodes that pickle.py doesn't have:")});
               var1.setlocal(7, var7);
               var3 = null;
               var1.setline(1797);
               var3 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(1797);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1799);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(7))));
                  }

                  PyObject[] var8 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var8[0];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var6 = var8[1];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var1.setline(1798);
                  var1.getlocal(7).__getattr__("append").__call__(var2, PyString.fromInterned("    name %r with code %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("name"), var1.getlocal(8)})));
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(1768);
         if (var1.getlocal(2).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[A-Z][A-Z0-9_]+$"), (PyObject)var1.getlocal(4)).__not__().__nonzero__()) {
            var1.setline(1769);
            if (var1.getlocal(0).__nonzero__()) {
               var1.setline(1770);
               Py.println(PyString.fromInterned("skipping %r: it doesn't look like an opcode name")._mod(var1.getlocal(4)));
            }
         } else {
            var1.setline(1772);
            PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(4));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(1773);
            PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("str")).__not__();
            if (!var10000.__nonzero__()) {
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
               var10000 = var5._ne(Py.newInteger(1));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1774);
               if (var1.getlocal(0).__nonzero__()) {
                  var1.setline(1775);
                  Py.println(PyString.fromInterned("skipping %r: value %r doesn't look like a pickle code")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
               }
            } else {
               var1.setline(1778);
               var5 = var1.getlocal(5);
               var10000 = var5._in(var1.getlocal(3));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1792);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("pickle.py appears to have a pickle opcode with name %r and code %r, but we don't")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}))));
               }

               var1.setline(1779);
               if (var1.getlocal(0).__nonzero__()) {
                  var1.setline(1780);
                  Py.println(PyString.fromInterned("checking name %r w/ code %r for consistency")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
               }

               var1.setline(1782);
               var5 = var1.getlocal(3).__getitem__(var1.getlocal(5));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(1783);
               var5 = var1.getlocal(6).__getattr__("name");
               var10000 = var5._ne(var1.getlocal(4));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1784);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("for pickle code %r, pickle.py uses name %r but we're using name %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4), var1.getlocal(6).__getattr__("name")}))));
               }

               var1.setline(1790);
               var1.getlocal(3).__delitem__(var1.getlocal(5));
            }
         }
      }
   }

   public PyObject genops$25(PyFrame var1, ThreadState var2) {
      label55: {
         Object[] var3;
         PyObject var5;
         PyObject[] var6;
         PyObject var8;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(1829);
               PyString.fromInterned("Generate all the opcodes in a pickle.\n\n    'pickle' is a file-like object, or string, containing the pickle.\n\n    Each opcode in the pickle is generated, from the current pickle position,\n    stopping after a STOP opcode is delivered.  A triple is generated for\n    each opcode:\n\n        opcode, arg, pos\n\n    opcode is an OpcodeInfo record, describing the current opcode.\n\n    If the opcode has an argument embedded in the pickle, arg is its decoded\n    value, as a Python object.  If the opcode doesn't have an argument, arg\n    is None.\n\n    If the pickle has a tell() method, pos was the value of pickle.tell()\n    before reading the current opcode.  If the pickle is a string object,\n    it's wrapped in a StringIO object, and the latter's tell() result is\n    used.  Else (the pickle doesn't have a tell(), and it's not obvious how\n    to query its current position) pos is None.\n    ");
               var1.setline(1831);
               var5 = imp.importOneAs("cStringIO", var1, -1);
               var1.setlocal(1, var5);
               var3 = null;
               var1.setline(1833);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
                  var1.setline(1834);
                  var5 = var1.getlocal(1).__getattr__("StringIO").__call__(var2, var1.getlocal(0));
                  var1.setlocal(0, var5);
                  var3 = null;
               }

               var1.setline(1836);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("tell")).__nonzero__()) {
                  var1.setline(1837);
                  var5 = var1.getlocal(0).__getattr__("tell");
                  var1.setlocal(2, var5);
                  var3 = null;
               } else {
                  var1.setline(1839);
                  var1.setline(1839);
                  var6 = Py.EmptyObjects;
                  PyFunction var7 = new PyFunction(var1.f_globals, var6, f$26);
                  var1.setlocal(2, var7);
                  var3 = null;
               }
               break;
            case 1:
               var3 = var1.f_savedlocals;
               Object var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var8 = (PyObject)var10000;
               var1.setline(1857);
               var5 = var1.getlocal(4);
               var8 = var5._eq(PyString.fromInterned("."));
               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(1858);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var5 = var1.getlocal(5).__getattr__("name");
                     var8 = var5._eq(PyString.fromInterned("STOP"));
                     var3 = null;
                     if (!var8.__nonzero__()) {
                        var8 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var8);
                     }
                  }
                  break label55;
               }
         }

         var1.setline(1841);
         if (var1.getglobal("True").__nonzero__()) {
            var1.setline(1842);
            var5 = var1.getlocal(2).__call__(var2);
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(1843);
            var5 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(4, var5);
            var3 = null;
            var1.setline(1844);
            var5 = var1.getglobal("code2op").__getattr__("get").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var5);
            var3 = null;
            var1.setline(1845);
            var5 = var1.getlocal(5);
            var8 = var5._is(var1.getglobal("None"));
            var3 = null;
            if (var8.__nonzero__()) {
               var1.setline(1846);
               var5 = var1.getlocal(4);
               var8 = var5._eq(PyString.fromInterned(""));
               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(1847);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pickle exhausted before seeing STOP")));
               }

               var1.setline(1849);
               var8 = var1.getglobal("ValueError");
               PyString var10002 = PyString.fromInterned("at position %s, opcode %r unknown");
               var6 = new PyObject[2];
               PyObject var4 = var1.getlocal(3);
               Object var10003 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (((PyObject)var10003).__nonzero__()) {
                  var10003 = PyString.fromInterned("<unknown>");
               }

               if (!((PyObject)var10003).__nonzero__()) {
                  var10003 = var1.getlocal(3);
               }

               var6[0] = (PyObject)var10003;
               var6[1] = var1.getlocal(4);
               PyTuple var10 = new PyTuple(var6);
               Arrays.fill(var6, (Object)null);
               throw Py.makeException(var8.__call__(var2, var10002._mod(var10)));
            }

            var1.setline(1852);
            var5 = var1.getlocal(5).__getattr__("arg");
            var8 = var5._is(var1.getglobal("None"));
            var3 = null;
            if (var8.__nonzero__()) {
               var1.setline(1853);
               var5 = var1.getglobal("None");
               var1.setlocal(6, var5);
               var3 = null;
            } else {
               var1.setline(1855);
               var5 = var1.getlocal(5).__getattr__("arg").__getattr__("reader").__call__(var2, var1.getlocal(0));
               var1.setlocal(6, var5);
               var3 = null;
            }

            var1.setline(1856);
            var1.setline(1856);
            var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(3)};
            PyTuple var9 = new PyTuple(var6);
            Arrays.fill(var6, (Object)null);
            var1.f_lasti = 1;
            var3 = new Object[6];
            var1.f_savedlocals = var3;
            return var9;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$26(PyFrame var1, ThreadState var2) {
      var1.setline(1839);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject optimize$27(PyFrame var1, ThreadState var2) {
      var1.setline(1865);
      PyString.fromInterned("Optimize a pickle string by removing unused PUT opcodes");
      var1.setline(1866);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1867);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(1868);
      var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1869);
      var3 = var1.getglobal("genops").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(1869);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var9;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(1879);
            var8 = new PyList(Py.EmptyObjects);
            var1.setlocal(8, var8);
            var3 = null;
            var1.setline(1880);
            PyInteger var10 = Py.newInteger(0);
            var1.setlocal(9, var10);
            var3 = null;
            var1.setline(1881);
            var3 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(1881);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1885);
                  var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(9), (PyObject)null, (PyObject)null));
                  var1.setline(1886);
                  var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(8));
                  var1.f_lasti = -1;
                  return var3;
               }

               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(10, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(11, var6);
               var6 = null;
               var1.setline(1882);
               var1.setline(1882);
               var9 = var1.getlocal(5);
               var10000 = var9._in(var1.getlocal(1));
               var5 = null;
               var9 = var10000.__nonzero__() ? var1.getlocal(11) : var1.getlocal(10);
               var1.setlocal(12, var9);
               var5 = null;
               var1.setline(1883);
               var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(9), var1.getlocal(12), (PyObject)null));
               var1.setline(1884);
               var9 = var1.getlocal(11);
               var1.setlocal(9, var9);
               var5 = null;
            }
         }

         var5 = Py.unpackSequence(var4, 3);
         var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(1870);
         var9 = var1.getlocal(3);
         var10000 = var9._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1871);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(3), var1.getlocal(6)})));
            var1.setline(1872);
            var9 = var1.getglobal("None");
            var1.setlocal(3, var9);
            var5 = null;
         }

         var1.setline(1873);
         PyString var11 = PyString.fromInterned("PUT");
         var10000 = var11._in(var1.getlocal(4).__getattr__("name"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1874);
            PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
            PyObject[] var13 = Py.unpackSequence(var12, 2);
            PyObject var7 = var13[0];
            var1.setlocal(7, var7);
            var7 = null;
            var7 = var13[1];
            var1.setlocal(3, var7);
            var7 = null;
            var5 = null;
         } else {
            var1.setline(1875);
            var11 = PyString.fromInterned("GET");
            var10000 = var11._in(var1.getlocal(4).__getattr__("name"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1876);
               var1.getlocal(1).__getattr__("add").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject dis$28(PyFrame var1, ThreadState var2) {
      var1.setline(1924);
      PyString.fromInterned("Produce a symbolic disassembly of a pickle.\n\n    'pickle' is a file-like object, or string, containing a (at least one)\n    pickle.  The pickle is disassembled from the current position, through\n    the first STOP opcode encountered.\n\n    Optional arg 'out' is a file-like object to which the disassembly is\n    printed.  It defaults to sys.stdout.\n\n    Optional arg 'memo' is a Python dict, used as the pickle's memo.  It\n    may be mutated by dis(), if the pickle contains PUT or BINPUT opcodes.\n    Passing the same memo object to another dis() call then allows disassembly\n    to proceed across multiple pickles that were all created by the same\n    pickler with the same memo.  Ordinarily you don't need to worry about this.\n\n    Optional arg indentlevel is the number of blanks by which to indent\n    a new MARK level.  It defaults to 4.\n\n    In addition to printing the disassembly, some sanity checks are made:\n\n    + All embedded opcode arguments \"make sense\".\n\n    + Explicit and implicit pop operations have enough items on the stack.\n\n    + When an opcode implicitly refers to a markobject, a markobject is\n      actually on the stack.\n\n    + A memo entry isn't referenced before it's defined.\n\n    + The markobject isn't stored in the memo.\n\n    + A memo entry isn't redefined.\n    ");
      var1.setline(1930);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1931);
      PyObject var8 = var1.getlocal(2);
      PyObject var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1932);
         PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var9);
         var3 = null;
      }

      var1.setline(1933);
      PyInteger var10 = Py.newInteger(-1);
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(1934);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1935);
      var8 = PyString.fromInterned(" ")._mul(var1.getlocal(3));
      var1.setlocal(7, var8);
      var3 = null;
      var1.setline(1936);
      var8 = var1.getglobal("None");
      var1.setlocal(8, var8);
      var3 = null;
      var1.setline(1937);
      var8 = var1.getglobal("genops").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(1937);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(2023);
            var8 = var1.getlocal(1);
            Py.printComma(var8, PyString.fromInterned("highest protocol among opcodes ="));
            Py.println(var8, var1.getlocal(5));
            var1.setline(2024);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(2025);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("stack not empty after STOP: %r")._mod(var1.getlocal(4))));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(11, var6);
         var6 = null;
         var1.setline(1938);
         PyObject var11 = var1.getlocal(11);
         var10000 = var11._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1939);
            var11 = var1.getlocal(1);
            Py.printComma(var11, PyString.fromInterned("%5d:")._mod(var1.getlocal(11)));
         }

         var1.setline(1941);
         var11 = PyString.fromInterned("%-4s %s%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(9).__getattr__("code")).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null), var1.getlocal(7)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(6))), var1.getlocal(9).__getattr__("name")}));
         var1.setlocal(12, var11);
         var5 = null;
         var1.setline(1945);
         var11 = var1.getglobal("max").__call__(var2, var1.getlocal(5), var1.getlocal(9).__getattr__("proto"));
         var1.setlocal(5, var11);
         var5 = null;
         var1.setline(1946);
         var11 = var1.getlocal(9).__getattr__("stack_before");
         var1.setlocal(13, var11);
         var5 = null;
         var1.setline(1947);
         var11 = var1.getlocal(9).__getattr__("stack_after");
         var1.setlocal(14, var11);
         var5 = null;
         var1.setline(1948);
         var11 = var1.getglobal("len").__call__(var2, var1.getlocal(13));
         var1.setlocal(15, var11);
         var5 = null;
         var1.setline(1951);
         var11 = var1.getglobal("None");
         var1.setlocal(16, var11);
         var5 = null;
         var1.setline(1952);
         var11 = var1.getglobal("markobject");
         var10000 = var11._in(var1.getlocal(13));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var11 = var1.getlocal(9).__getattr__("name");
            var10000 = var11._eq(PyString.fromInterned("POP"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var11 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
                  var10000 = var11._is(var1.getglobal("markobject"));
                  var5 = null;
               }
            }
         }

         PyString var13;
         if (var10000.__nonzero__()) {
            var1.setline(1955);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var11 = var1.getglobal("markobject");
               var10000 = var11._notin(var1.getlocal(14));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(1956);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var1.setline(1957);
               var11 = var1.getglobal("markobject");
               var10000 = var11._in(var1.getlocal(13));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1958);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var11 = var1.getlocal(13).__getitem__(Py.newInteger(-1));
                     var10000 = var11._is(var1.getglobal("stackslice"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }
               }
            }

            var1.setline(1959);
            if (!var1.getlocal(6).__nonzero__()) {
               var1.setline(1976);
               var13 = PyString.fromInterned("no MARK exists on stack");
               var1.setlocal(8, var13);
               var1.setlocal(16, var13);
            } else {
               var1.setline(1960);
               var11 = var1.getlocal(6).__getattr__("pop").__call__(var2);
               var1.setlocal(17, var11);
               var5 = null;
               var1.setline(1961);
               var11 = var1.getlocal(17);
               var10000 = var11._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1962);
                  var13 = PyString.fromInterned("(MARK at unknown opcode offset)");
                  var1.setlocal(16, var13);
                  var5 = null;
               } else {
                  var1.setline(1964);
                  var11 = PyString.fromInterned("(MARK at %d)")._mod(var1.getlocal(17));
                  var1.setlocal(16, var11);
                  var5 = null;
               }

               while(true) {
                  var1.setline(1966);
                  var11 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
                  var10000 = var11._isnot(var1.getglobal("markobject"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(1968);
                     var1.getlocal(4).__getattr__("pop").__call__(var2);

                     try {
                        var1.setline(1971);
                        var11 = var1.getlocal(13).__getattr__("index").__call__(var2, var1.getglobal("markobject"));
                        var1.setlocal(15, var11);
                        var5 = null;
                     } catch (Throwable var7) {
                        PyException var14 = Py.setException(var7, var1);
                        if (!var14.match(var1.getglobal("ValueError"))) {
                           throw var14;
                        }

                        var1.setline(1973);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var6 = var1.getlocal(9).__getattr__("name");
                           var10000 = var6._eq(PyString.fromInterned("POP"));
                           var6 = null;
                           if (!var10000.__nonzero__()) {
                              var10000 = Py.None;
                              throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                           }
                        }

                        var1.setline(1974);
                        PyInteger var12 = Py.newInteger(0);
                        var1.setlocal(15, var12);
                        var6 = null;
                     }
                     break;
                  }

                  var1.setline(1967);
                  var1.getlocal(4).__getattr__("pop").__call__(var2);
               }
            }
         }

         var1.setline(1979);
         var11 = var1.getlocal(9).__getattr__("name");
         var10000 = var11._in(new PyTuple(new PyObject[]{PyString.fromInterned("PUT"), PyString.fromInterned("BINPUT"), PyString.fromInterned("LONG_BINPUT")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1980);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var11 = var1.getlocal(10);
               var10000 = var11._isnot(var1.getglobal("None"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(1981);
            var11 = var1.getlocal(10);
            var10000 = var11._in(var1.getlocal(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1982);
               var11 = PyString.fromInterned("memo key %r already defined")._mod(var1.getlocal(10));
               var1.setlocal(8, var11);
               var5 = null;
            } else {
               var1.setline(1983);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  var1.setline(1984);
                  var13 = PyString.fromInterned("stack is empty -- can't store into memo");
                  var1.setlocal(8, var13);
                  var5 = null;
               } else {
                  var1.setline(1985);
                  var11 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
                  var10000 = var11._is(var1.getglobal("markobject"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1986);
                     var13 = PyString.fromInterned("can't store markobject in the memo");
                     var1.setlocal(8, var13);
                     var5 = null;
                  } else {
                     var1.setline(1988);
                     var11 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
                     var1.getlocal(2).__setitem__(var1.getlocal(10), var11);
                     var5 = null;
                  }
               }
            }
         } else {
            var1.setline(1990);
            var11 = var1.getlocal(9).__getattr__("name");
            var10000 = var11._in(new PyTuple(new PyObject[]{PyString.fromInterned("GET"), PyString.fromInterned("BINGET"), PyString.fromInterned("LONG_BINGET")}));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1991);
               var11 = var1.getlocal(10);
               var10000 = var11._in(var1.getlocal(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1992);
                  if (var1.getglobal("__debug__").__nonzero__()) {
                     var11 = var1.getglobal("len").__call__(var2, var1.getlocal(14));
                     var10000 = var11._eq(Py.newInteger(1));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     }
                  }

                  var1.setline(1993);
                  PyList var15 = new PyList(new PyObject[]{var1.getlocal(2).__getitem__(var1.getlocal(10))});
                  var1.setlocal(14, var15);
                  var5 = null;
               } else {
                  var1.setline(1995);
                  var11 = PyString.fromInterned("memo key %r has never been stored into")._mod(var1.getlocal(10));
                  var1.setlocal(8, var11);
                  var5 = null;
               }
            }
         }

         var1.setline(1997);
         var11 = var1.getlocal(10);
         var10000 = var11._isnot(var1.getglobal("None"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(16);
         }

         if (var10000.__nonzero__()) {
            var1.setline(1999);
            var11 = var1.getlocal(12);
            var11 = var11._iadd(PyString.fromInterned(" ")._mul(Py.newInteger(10)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(9).__getattr__("name")))));
            var1.setlocal(12, var11);
            var1.setline(2000);
            var11 = var1.getlocal(10);
            var10000 = var11._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2001);
               var11 = var1.getlocal(12);
               var11 = var11._iadd(PyString.fromInterned(" ")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(10))));
               var1.setlocal(12, var11);
            }

            var1.setline(2002);
            if (var1.getlocal(16).__nonzero__()) {
               var1.setline(2003);
               var11 = var1.getlocal(12);
               var11 = var11._iadd(PyString.fromInterned(" ")._add(var1.getlocal(16)));
               var1.setlocal(12, var11);
            }
         }

         var1.setline(2004);
         var11 = var1.getlocal(1);
         Py.println(var11, var1.getlocal(12));
         var1.setline(2006);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(2009);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(8)));
         }

         var1.setline(2012);
         var11 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var11._lt(var1.getlocal(15));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2013);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("tries to pop %d items from stack with only %d items")._mod(new PyTuple(new PyObject[]{var1.getlocal(15), var1.getglobal("len").__call__(var2, var1.getlocal(4))}))));
         }

         var1.setline(2015);
         if (var1.getlocal(15).__nonzero__()) {
            var1.setline(2016);
            var1.getlocal(4).__delslice__(var1.getlocal(15).__neg__(), (PyObject)null, (PyObject)null);
         }

         var1.setline(2017);
         var11 = var1.getglobal("markobject");
         var10000 = var11._in(var1.getlocal(14));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2018);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var11 = var1.getglobal("markobject");
               var10000 = var11._notin(var1.getlocal(13));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(2019);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(11));
         }

         var1.setline(2021);
         var1.getlocal(4).__getattr__("extend").__call__(var2, var1.getlocal(14));
      }
   }

   public PyObject _Example$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2029);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$30, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$30(PyFrame var1, ThreadState var2) {
      var1.setline(2030);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _test$31(PyFrame var1, ThreadState var2) {
      var1.setline(2270);
      PyObject var3 = imp.importOne("doctest", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(2271);
      var3 = var1.getlocal(0).__getattr__("testmod").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public pickletools$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ArgumentDescriptor$1 = Py.newCode(0, var2, var1, "ArgumentDescriptor", 165, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "n", "reader", "doc"};
      __init__$2 = Py.newCode(5, var2, var1, "__init__", 184, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "data"};
      read_uint1$3 = Py.newCode(1, var2, var1, "read_uint1", 201, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "data"};
      read_uint2$4 = Py.newCode(1, var2, var1, "read_uint2", 220, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "data"};
      read_int4$5 = Py.newCode(1, var2, var1, "read_int4", 241, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "decode", "stripquotes", "data", "q"};
      read_stringnl$6 = Py.newCode(3, var2, var1, "read_stringnl", 262, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f"};
      read_stringnl_noescape$7 = Py.newCode(1, var2, var1, "read_stringnl_noescape", 321, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f"};
      read_stringnl_noescape_pair$8 = Py.newCode(1, var2, var1, "read_stringnl_noescape_pair", 335, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "n", "data"};
      read_string4$9 = Py.newCode(1, var2, var1, "read_string4", 357, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "n", "data"};
      read_string1$10 = Py.newCode(1, var2, var1, "read_string1", 391, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "data"};
      read_unicodestringnl$11 = Py.newCode(1, var2, var1, "read_unicodestringnl", 420, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "n", "data"};
      read_unicodestring4$12 = Py.newCode(1, var2, var1, "read_unicodestring4", 445, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "s"};
      read_decimalnl_short$13 = Py.newCode(1, var2, var1, "read_decimalnl_short", 485, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "s"};
      read_decimalnl_long$14 = Py.newCode(1, var2, var1, "read_decimalnl_long", 514, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "s"};
      read_floatnl$15 = Py.newCode(1, var2, var1, "read_floatnl", 562, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "data"};
      read_float8$16 = Py.newCode(1, var2, var1, "read_float8", 584, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "n", "data"};
      read_long1$17 = Py.newCode(1, var2, var1, "read_long1", 622, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "n", "data"};
      read_long4$18 = Py.newCode(1, var2, var1, "read_long4", 654, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StackObject$19 = Py.newCode(0, var2, var1, "StackObject", 697, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "obtype", "doc", "contained"};
      __init__$20 = Py.newCode(4, var2, var1, "__init__", 710, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$21 = Py.newCode(1, var2, var1, "__repr__", 723, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OpcodeInfo$22 = Py.newCode(0, var2, var1, "OpcodeInfo", 823, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "code", "arg", "stack_before", "stack_after", "proto", "doc", "x"};
      __init__$23 = Py.newCode(8, var2, var1, "__init__", 854, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"verbose", "pickle", "re", "copy", "name", "picklecode", "d", "msg", "code"};
      assure_pickle_consistency$24 = Py.newCode(1, var2, var1, "assure_pickle_consistency", 1763, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pickle", "StringIO", "getpos", "pos", "code", "opcode", "arg"};
      genops$25 = Py.newCode(1, var2, var1, "genops", 1807, false, false, self, 25, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      f$26 = Py.newCode(0, var2, var1, "<lambda>", 1839, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"p", "gets", "puts", "prevpos", "opcode", "arg", "pos", "prevarg", "s", "i", "start", "stop", "j"};
      optimize$27 = Py.newCode(1, var2, var1, "optimize", 1864, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pickle", "out", "memo", "indentlevel", "stack", "maxproto", "markstack", "indentchunk", "errormsg", "opcode", "arg", "pos", "line", "before", "after", "numtopop", "markmsg", "markpos"};
      dis$28 = Py.newCode(4, var2, var1, "dis", 1891, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Example$29 = Py.newCode(0, var2, var1, "_Example", 2028, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      __init__$30 = Py.newCode(2, var2, var1, "__init__", 2029, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"doctest"};
      _test$31 = Py.newCode(0, var2, var1, "_test", 2269, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pickletools$py("pickletools$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pickletools$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ArgumentDescriptor$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.read_uint1$3(var2, var3);
         case 4:
            return this.read_uint2$4(var2, var3);
         case 5:
            return this.read_int4$5(var2, var3);
         case 6:
            return this.read_stringnl$6(var2, var3);
         case 7:
            return this.read_stringnl_noescape$7(var2, var3);
         case 8:
            return this.read_stringnl_noescape_pair$8(var2, var3);
         case 9:
            return this.read_string4$9(var2, var3);
         case 10:
            return this.read_string1$10(var2, var3);
         case 11:
            return this.read_unicodestringnl$11(var2, var3);
         case 12:
            return this.read_unicodestring4$12(var2, var3);
         case 13:
            return this.read_decimalnl_short$13(var2, var3);
         case 14:
            return this.read_decimalnl_long$14(var2, var3);
         case 15:
            return this.read_floatnl$15(var2, var3);
         case 16:
            return this.read_float8$16(var2, var3);
         case 17:
            return this.read_long1$17(var2, var3);
         case 18:
            return this.read_long4$18(var2, var3);
         case 19:
            return this.StackObject$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.__repr__$21(var2, var3);
         case 22:
            return this.OpcodeInfo$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.assure_pickle_consistency$24(var2, var3);
         case 25:
            return this.genops$25(var2, var3);
         case 26:
            return this.f$26(var2, var3);
         case 27:
            return this.optimize$27(var2, var3);
         case 28:
            return this.dis$28(var2, var3);
         case 29:
            return this._Example$29(var2, var3);
         case 30:
            return this.__init__$30(var2, var3);
         case 31:
            return this._test$31(var2, var3);
         default:
            return null;
      }
   }
}
