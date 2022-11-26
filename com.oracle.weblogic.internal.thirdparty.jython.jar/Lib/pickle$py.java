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
@Filename("pickle.py")
public class pickle$py extends PyFunctionTable implements PyRunnable {
   static pickle$py self;
   static final PyCode f$0;
   static final PyCode PickleError$1;
   static final PyCode PicklingError$2;
   static final PyCode UnpicklingError$3;
   static final PyCode _Stop$4;
   static final PyCode __init__$5;
   static final PyCode Pickler$6;
   static final PyCode __init__$7;
   static final PyCode clear_memo$8;
   static final PyCode dump$9;
   static final PyCode memoize$10;
   static final PyCode put$11;
   static final PyCode get$12;
   static final PyCode save$13;
   static final PyCode persistent_id$14;
   static final PyCode save_pers$15;
   static final PyCode save_reduce$16;
   static final PyCode save_none$17;
   static final PyCode save_bool$18;
   static final PyCode save_int$19;
   static final PyCode save_long$20;
   static final PyCode save_float$21;
   static final PyCode save_string$22;
   static final PyCode save_unicode$23;
   static final PyCode save_string$24;
   static final PyCode save_tuple$25;
   static final PyCode save_empty_tuple$26;
   static final PyCode save_list$27;
   static final PyCode _batch_appends$28;
   static final PyCode save_dict$29;
   static final PyCode _batch_setitems$30;
   static final PyCode save_inst$31;
   static final PyCode save_global$32;
   static final PyCode _keep_alive$33;
   static final PyCode whichmodule$34;
   static final PyCode Unpickler$35;
   static final PyCode __init__$36;
   static final PyCode load$37;
   static final PyCode marker$38;
   static final PyCode load_eof$39;
   static final PyCode load_proto$40;
   static final PyCode load_persid$41;
   static final PyCode load_binpersid$42;
   static final PyCode load_none$43;
   static final PyCode load_false$44;
   static final PyCode load_true$45;
   static final PyCode load_int$46;
   static final PyCode load_binint$47;
   static final PyCode load_binint1$48;
   static final PyCode load_binint2$49;
   static final PyCode load_long$50;
   static final PyCode load_long1$51;
   static final PyCode load_long4$52;
   static final PyCode load_float$53;
   static final PyCode load_binfloat$54;
   static final PyCode load_string$55;
   static final PyCode load_binstring$56;
   static final PyCode load_unicode$57;
   static final PyCode load_binunicode$58;
   static final PyCode load_short_binstring$59;
   static final PyCode load_tuple$60;
   static final PyCode load_empty_tuple$61;
   static final PyCode load_tuple1$62;
   static final PyCode load_tuple2$63;
   static final PyCode load_tuple3$64;
   static final PyCode load_empty_list$65;
   static final PyCode load_empty_dictionary$66;
   static final PyCode load_list$67;
   static final PyCode load_dict$68;
   static final PyCode _instantiate$69;
   static final PyCode load_inst$70;
   static final PyCode load_obj$71;
   static final PyCode load_newobj$72;
   static final PyCode load_global$73;
   static final PyCode load_ext1$74;
   static final PyCode load_ext2$75;
   static final PyCode load_ext4$76;
   static final PyCode get_extension$77;
   static final PyCode find_class$78;
   static final PyCode load_reduce$79;
   static final PyCode load_pop$80;
   static final PyCode load_pop_mark$81;
   static final PyCode load_dup$82;
   static final PyCode load_get$83;
   static final PyCode load_binget$84;
   static final PyCode load_long_binget$85;
   static final PyCode load_put$86;
   static final PyCode load_binput$87;
   static final PyCode load_long_binput$88;
   static final PyCode load_append$89;
   static final PyCode load_appends$90;
   static final PyCode load_setitem$91;
   static final PyCode load_setitems$92;
   static final PyCode load_build$93;
   static final PyCode load_mark$94;
   static final PyCode load_stop$95;
   static final PyCode _EmptyClass$96;
   static final PyCode encode_long$97;
   static final PyCode decode_long$98;
   static final PyCode dump$99;
   static final PyCode dumps$100;
   static final PyCode load$101;
   static final PyCode loads$102;
   static final PyCode _test$103;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Create portable serialized representations of Python objects.\n\nSee module cPickle for a (much) faster implementation.\nSee module copy_reg for a mechanism for registering custom picklers.\nSee module pickletools source for extensive comments.\n\nClasses:\n\n    Pickler\n    Unpickler\n\nFunctions:\n\n    dump(object, file)\n    dumps(object) -> string\n    load(file) -> object\n    loads(string) -> object\n\nMisc variables:\n\n    __version__\n    format_version\n    compatible_formats\n\n"));
      var1.setline(25);
      PyString.fromInterned("Create portable serialized representations of Python objects.\n\nSee module cPickle for a (much) faster implementation.\nSee module copy_reg for a mechanism for registering custom picklers.\nSee module pickletools source for extensive comments.\n\nClasses:\n\n    Pickler\n    Unpickler\n\nFunctions:\n\n    dump(object, file)\n    dumps(object) -> string\n    load(file) -> object\n    loads(string) -> object\n\nMisc variables:\n\n    __version__\n    format_version\n    compatible_formats\n\n");
      var1.setline(27);
      PyString var3 = PyString.fromInterned("$Revision: 72223 $");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(29);
      imp.importAll("types", var1, -1);
      var1.setline(30);
      String[] var9 = new String[]{"dispatch_table"};
      PyObject[] var10 = imp.importFrom("copy_reg", var9, var1, -1);
      PyObject var4 = var10[0];
      var1.setlocal("dispatch_table", var4);
      var4 = null;
      var1.setline(31);
      var9 = new String[]{"_extension_registry", "_inverted_registry", "_extension_cache"};
      var10 = imp.importFrom("copy_reg", var9, var1, -1);
      var4 = var10[0];
      var1.setlocal("_extension_registry", var4);
      var4 = null;
      var4 = var10[1];
      var1.setlocal("_inverted_registry", var4);
      var4 = null;
      var4 = var10[2];
      var1.setlocal("_extension_cache", var4);
      var4 = null;
      var1.setline(32);
      PyObject var11 = imp.importOne("marshal", var1, -1);
      var1.setlocal("marshal", var11);
      var3 = null;
      var1.setline(33);
      var11 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var11);
      var3 = null;
      var1.setline(34);
      var11 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var11);
      var3 = null;
      var1.setline(35);
      var11 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var11);
      var3 = null;
      var1.setline(37);
      PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("PickleError"), PyString.fromInterned("PicklingError"), PyString.fromInterned("UnpicklingError"), PyString.fromInterned("Pickler"), PyString.fromInterned("Unpickler"), PyString.fromInterned("dump"), PyString.fromInterned("dumps"), PyString.fromInterned("load"), PyString.fromInterned("loads")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(41);
      var3 = PyString.fromInterned("2.0");
      var1.setlocal("format_version", var3);
      var3 = null;
      var1.setline(42);
      var12 = new PyList(new PyObject[]{PyString.fromInterned("1.0"), PyString.fromInterned("1.1"), PyString.fromInterned("1.2"), PyString.fromInterned("1.3"), PyString.fromInterned("2.0")});
      var1.setlocal("compatible_formats", var12);
      var3 = null;
      var1.setline(51);
      PyInteger var13 = Py.newInteger(2);
      var1.setlocal("HIGHEST_PROTOCOL", var13);
      var3 = null;
      var1.setline(56);
      var11 = var1.getname("marshal").__getattr__("loads");
      var1.setlocal("mloads", var11);
      var3 = null;
      var1.setline(58);
      var10 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("PickleError", var10, PickleError$1);
      var1.setlocal("PickleError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(62);
      var10 = new PyObject[]{var1.getname("PickleError")};
      var4 = Py.makeClass("PicklingError", var10, PicklingError$2);
      var1.setlocal("PicklingError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(69);
      var10 = new PyObject[]{var1.getname("PickleError")};
      var4 = Py.makeClass("UnpicklingError", var10, UnpicklingError$3);
      var1.setlocal("UnpicklingError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(82);
      var10 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("_Stop", var10, _Stop$4);
      var1.setlocal("_Stop", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);

      PyException var14;
      try {
         var1.setline(88);
         var9 = new String[]{"PyStringMap"};
         var10 = imp.importFrom("org.python.core", var9, var1, -1);
         var4 = var10[0];
         var1.setlocal("PyStringMap", var4);
         var4 = null;
      } catch (Throwable var8) {
         var14 = Py.setException(var8, var1);
         if (!var14.match(var1.getname("ImportError"))) {
            throw var14;
         }

         var1.setline(90);
         var4 = var1.getname("None");
         var1.setlocal("PyStringMap", var4);
         var4 = null;
      }

      try {
         var1.setline(94);
         var1.getname("UnicodeType");
      } catch (Throwable var7) {
         var14 = Py.setException(var7, var1);
         if (!var14.match(var1.getname("NameError"))) {
            throw var14;
         }

         var1.setline(96);
         var4 = var1.getname("None");
         var1.setlocal("UnicodeType", var4);
         var4 = null;
      }

      var1.setline(102);
      var3 = PyString.fromInterned("(");
      var1.setlocal("MARK", var3);
      var3 = null;
      var1.setline(103);
      var3 = PyString.fromInterned(".");
      var1.setlocal("STOP", var3);
      var3 = null;
      var1.setline(104);
      var3 = PyString.fromInterned("0");
      var1.setlocal("POP", var3);
      var3 = null;
      var1.setline(105);
      var3 = PyString.fromInterned("1");
      var1.setlocal("POP_MARK", var3);
      var3 = null;
      var1.setline(106);
      var3 = PyString.fromInterned("2");
      var1.setlocal("DUP", var3);
      var3 = null;
      var1.setline(107);
      var3 = PyString.fromInterned("F");
      var1.setlocal("FLOAT", var3);
      var3 = null;
      var1.setline(108);
      var3 = PyString.fromInterned("I");
      var1.setlocal("INT", var3);
      var3 = null;
      var1.setline(109);
      var3 = PyString.fromInterned("J");
      var1.setlocal("BININT", var3);
      var3 = null;
      var1.setline(110);
      var3 = PyString.fromInterned("K");
      var1.setlocal("BININT1", var3);
      var3 = null;
      var1.setline(111);
      var3 = PyString.fromInterned("L");
      var1.setlocal("LONG", var3);
      var3 = null;
      var1.setline(112);
      var3 = PyString.fromInterned("M");
      var1.setlocal("BININT2", var3);
      var3 = null;
      var1.setline(113);
      var3 = PyString.fromInterned("N");
      var1.setlocal("NONE", var3);
      var3 = null;
      var1.setline(114);
      var3 = PyString.fromInterned("P");
      var1.setlocal("PERSID", var3);
      var3 = null;
      var1.setline(115);
      var3 = PyString.fromInterned("Q");
      var1.setlocal("BINPERSID", var3);
      var3 = null;
      var1.setline(116);
      var3 = PyString.fromInterned("R");
      var1.setlocal("REDUCE", var3);
      var3 = null;
      var1.setline(117);
      var3 = PyString.fromInterned("S");
      var1.setlocal("STRING", var3);
      var3 = null;
      var1.setline(118);
      var3 = PyString.fromInterned("T");
      var1.setlocal("BINSTRING", var3);
      var3 = null;
      var1.setline(119);
      var3 = PyString.fromInterned("U");
      var1.setlocal("SHORT_BINSTRING", var3);
      var3 = null;
      var1.setline(120);
      var3 = PyString.fromInterned("V");
      var1.setlocal("UNICODE", var3);
      var3 = null;
      var1.setline(121);
      var3 = PyString.fromInterned("X");
      var1.setlocal("BINUNICODE", var3);
      var3 = null;
      var1.setline(122);
      var3 = PyString.fromInterned("a");
      var1.setlocal("APPEND", var3);
      var3 = null;
      var1.setline(123);
      var3 = PyString.fromInterned("b");
      var1.setlocal("BUILD", var3);
      var3 = null;
      var1.setline(124);
      var3 = PyString.fromInterned("c");
      var1.setlocal("GLOBAL", var3);
      var3 = null;
      var1.setline(125);
      var3 = PyString.fromInterned("d");
      var1.setlocal("DICT", var3);
      var3 = null;
      var1.setline(126);
      var3 = PyString.fromInterned("}");
      var1.setlocal("EMPTY_DICT", var3);
      var3 = null;
      var1.setline(127);
      var3 = PyString.fromInterned("e");
      var1.setlocal("APPENDS", var3);
      var3 = null;
      var1.setline(128);
      var3 = PyString.fromInterned("g");
      var1.setlocal("GET", var3);
      var3 = null;
      var1.setline(129);
      var3 = PyString.fromInterned("h");
      var1.setlocal("BINGET", var3);
      var3 = null;
      var1.setline(130);
      var3 = PyString.fromInterned("i");
      var1.setlocal("INST", var3);
      var3 = null;
      var1.setline(131);
      var3 = PyString.fromInterned("j");
      var1.setlocal("LONG_BINGET", var3);
      var3 = null;
      var1.setline(132);
      var3 = PyString.fromInterned("l");
      var1.setlocal("LIST", var3);
      var3 = null;
      var1.setline(133);
      var3 = PyString.fromInterned("]");
      var1.setlocal("EMPTY_LIST", var3);
      var3 = null;
      var1.setline(134);
      var3 = PyString.fromInterned("o");
      var1.setlocal("OBJ", var3);
      var3 = null;
      var1.setline(135);
      var3 = PyString.fromInterned("p");
      var1.setlocal("PUT", var3);
      var3 = null;
      var1.setline(136);
      var3 = PyString.fromInterned("q");
      var1.setlocal("BINPUT", var3);
      var3 = null;
      var1.setline(137);
      var3 = PyString.fromInterned("r");
      var1.setlocal("LONG_BINPUT", var3);
      var3 = null;
      var1.setline(138);
      var3 = PyString.fromInterned("s");
      var1.setlocal("SETITEM", var3);
      var3 = null;
      var1.setline(139);
      var3 = PyString.fromInterned("t");
      var1.setlocal("TUPLE", var3);
      var3 = null;
      var1.setline(140);
      var3 = PyString.fromInterned(")");
      var1.setlocal("EMPTY_TUPLE", var3);
      var3 = null;
      var1.setline(141);
      var3 = PyString.fromInterned("u");
      var1.setlocal("SETITEMS", var3);
      var3 = null;
      var1.setline(142);
      var3 = PyString.fromInterned("G");
      var1.setlocal("BINFLOAT", var3);
      var3 = null;
      var1.setline(144);
      var3 = PyString.fromInterned("I01\n");
      var1.setlocal("TRUE", var3);
      var3 = null;
      var1.setline(145);
      var3 = PyString.fromInterned("I00\n");
      var1.setlocal("FALSE", var3);
      var3 = null;
      var1.setline(149);
      var3 = PyString.fromInterned("\u0080");
      var1.setlocal("PROTO", var3);
      var3 = null;
      var1.setline(150);
      var3 = PyString.fromInterned("\u0081");
      var1.setlocal("NEWOBJ", var3);
      var3 = null;
      var1.setline(151);
      var3 = PyString.fromInterned("\u0082");
      var1.setlocal("EXT1", var3);
      var3 = null;
      var1.setline(152);
      var3 = PyString.fromInterned("\u0083");
      var1.setlocal("EXT2", var3);
      var3 = null;
      var1.setline(153);
      var3 = PyString.fromInterned("\u0084");
      var1.setlocal("EXT4", var3);
      var3 = null;
      var1.setline(154);
      var3 = PyString.fromInterned("\u0085");
      var1.setlocal("TUPLE1", var3);
      var3 = null;
      var1.setline(155);
      var3 = PyString.fromInterned("\u0086");
      var1.setlocal("TUPLE2", var3);
      var3 = null;
      var1.setline(156);
      var3 = PyString.fromInterned("\u0087");
      var1.setlocal("TUPLE3", var3);
      var3 = null;
      var1.setline(157);
      var3 = PyString.fromInterned("\u0088");
      var1.setlocal("NEWTRUE", var3);
      var3 = null;
      var1.setline(158);
      var3 = PyString.fromInterned("\u0089");
      var1.setlocal("NEWFALSE", var3);
      var3 = null;
      var1.setline(159);
      var3 = PyString.fromInterned("\u008a");
      var1.setlocal("LONG1", var3);
      var3 = null;
      var1.setline(160);
      var3 = PyString.fromInterned("\u008b");
      var1.setlocal("LONG4", var3);
      var3 = null;
      var1.setline(162);
      var12 = new PyList(new PyObject[]{var1.getname("EMPTY_TUPLE"), var1.getname("TUPLE1"), var1.getname("TUPLE2"), var1.getname("TUPLE3")});
      var1.setlocal("_tuplesize2code", var12);
      var3 = null;
      var1.setline(165);
      PyObject var10000 = var1.getname("__all__").__getattr__("extend");
      PyList var10002 = new PyList();
      var11 = var10002.__getattr__("append");
      var1.setlocal("_[165_16]", var11);
      var3 = null;
      var1.setline(165);
      var11 = var1.getname("dir").__call__(var2).__iter__();

      while(true) {
         var1.setline(165);
         var4 = var11.__iternext__();
         if (var4 == null) {
            var1.setline(165);
            var1.dellocal("_[165_16]");
            var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setline(166);
            var1.dellocal("x");
            var1.setline(171);
            var10 = Py.EmptyObjects;
            var4 = Py.makeClass("Pickler", var10, Pickler$6);
            var1.setlocal("Pickler", var4);
            var4 = null;
            Arrays.fill(var10, (Object)null);
            var1.setline(777);
            var10 = Py.EmptyObjects;
            PyFunction var17 = new PyFunction(var1.f_globals, var10, _keep_alive$33, PyString.fromInterned("Keeps a reference to the object x in the memo.\n\n    Because we remember objects by their id, we have\n    to assure that possibly temporary objects are kept\n    alive by referencing them.\n    We store a reference at the id of the memo, which should\n    normally not be used unless someone tries to deepcopy\n    the memo itself...\n    "));
            var1.setlocal("_keep_alive", var17);
            var3 = null;
            var1.setline(797);
            PyDictionary var18 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal("classmap", var18);
            var3 = null;
            var1.setline(799);
            var10 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var10, whichmodule$34, PyString.fromInterned("Figure out the module in which a function occurs.\n\n    Search sys.modules for the module.\n    Cache in classmap.\n    Return a module name.\n    If the function cannot be found, return \"__main__\".\n    "));
            var1.setlocal("whichmodule", var17);
            var3 = null;
            var1.setline(827);
            var10 = Py.EmptyObjects;
            var4 = Py.makeClass("Unpickler", var10, Unpickler$35);
            var1.setlocal("Unpickler", var4);
            var4 = null;
            Arrays.fill(var10, (Object)null);
            var1.setline(1261);
            var10 = Py.EmptyObjects;
            var4 = Py.makeClass("_EmptyClass", var10, _EmptyClass$96);
            var1.setlocal("_EmptyClass", var4);
            var4 = null;
            Arrays.fill(var10, (Object)null);
            var1.setline(1266);
            var11 = imp.importOneAs("binascii", var1, -1);
            var1.setlocal("_binascii", var11);
            var3 = null;
            var1.setline(1268);
            var10 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var10, encode_long$97, PyString.fromInterned("Encode a long to a two's complement little-endian binary string.\n    Note that 0L is a special case, returning an empty string, to save a\n    byte in the LONG1 pickling context.\n\n    >>> encode_long(0L)\n    ''\n    >>> encode_long(255L)\n    '\\xff\\x00'\n    >>> encode_long(32767L)\n    '\\xff\\x7f'\n    >>> encode_long(-256L)\n    '\\x00\\xff'\n    >>> encode_long(-32768L)\n    '\\x00\\x80'\n    >>> encode_long(-128L)\n    '\\x80'\n    >>> encode_long(127L)\n    '\\x7f'\n    >>>\n    "));
            var1.setlocal("encode_long", var17);
            var3 = null;
            var1.setline(1334);
            var10 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var10, decode_long$98, PyString.fromInterned("Decode a long from a two's complement little-endian binary string.\n\n    >>> decode_long('')\n    0L\n    >>> decode_long(\"\\xff\\x00\")\n    255L\n    >>> decode_long(\"\\xff\\x7f\")\n    32767L\n    >>> decode_long(\"\\x00\\xff\")\n    -256L\n    >>> decode_long(\"\\x00\\x80\")\n    -32768L\n    >>> decode_long(\"\\x80\")\n    -128L\n    >>> decode_long(\"\\x7f\")\n    127L\n    "));
            var1.setlocal("decode_long", var17);
            var3 = null;

            try {
               var1.setline(1365);
               var9 = new String[]{"StringIO"};
               var10 = imp.importFrom("cStringIO", var9, var1, -1);
               var4 = var10[0];
               var1.setlocal("StringIO", var4);
               var4 = null;
            } catch (Throwable var6) {
               var14 = Py.setException(var6, var1);
               if (!var14.match(var1.getname("ImportError"))) {
                  throw var14;
               }

               var1.setline(1367);
               String[] var15 = new String[]{"StringIO"};
               PyObject[] var16 = imp.importFrom("StringIO", var15, var1, -1);
               PyObject var5 = var16[0];
               var1.setlocal("StringIO", var5);
               var5 = null;
            }

            var1.setline(1369);
            var10 = new PyObject[]{var1.getname("None")};
            var17 = new PyFunction(var1.f_globals, var10, dump$99, (PyObject)null);
            var1.setlocal("dump", var17);
            var3 = null;
            var1.setline(1372);
            var10 = new PyObject[]{var1.getname("None")};
            var17 = new PyFunction(var1.f_globals, var10, dumps$100, (PyObject)null);
            var1.setlocal("dumps", var17);
            var3 = null;
            var1.setline(1377);
            var10 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var10, load$101, (PyObject)null);
            var1.setlocal("load", var17);
            var3 = null;
            var1.setline(1380);
            var10 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var10, loads$102, (PyObject)null);
            var1.setlocal("loads", var17);
            var3 = null;
            var1.setline(1386);
            var10 = Py.EmptyObjects;
            var17 = new PyFunction(var1.f_globals, var10, _test$103, (PyObject)null);
            var1.setlocal("_test", var17);
            var3 = null;
            var1.setline(1390);
            var11 = var1.getname("__name__");
            var10000 = var11._eq(PyString.fromInterned("__main__"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1391);
               var1.getname("_test").__call__(var2);
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("x", var4);
         var1.setline(165);
         if (var1.getname("re").__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[A-Z][A-Z0-9_]+$"), (PyObject)var1.getname("x")).__nonzero__()) {
            var1.setline(165);
            var1.getname("_[165_16]").__call__(var2, var1.getname("x"));
         }
      }
   }

   public PyObject PickleError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A common base class for the other pickling exceptions."));
      var1.setline(59);
      PyString.fromInterned("A common base class for the other pickling exceptions.");
      var1.setline(60);
      return var1.getf_locals();
   }

   public PyObject PicklingError$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This exception is raised when an unpicklable object is passed to the\n    dump() method.\n\n    "));
      var1.setline(66);
      PyString.fromInterned("This exception is raised when an unpicklable object is passed to the\n    dump() method.\n\n    ");
      var1.setline(67);
      return var1.getf_locals();
   }

   public PyObject UnpicklingError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This exception is raised when there is a problem unpickling an object,\n    such as a security violation.\n\n    Note that other exceptions may also be raised during unpickling, including\n    (but not necessarily limited to) AttributeError, EOFError, ImportError,\n    and IndexError.\n\n    "));
      var1.setline(77);
      PyString.fromInterned("This exception is raised when there is a problem unpickling an object,\n    such as a security violation.\n\n    Note that other exceptions may also be raised during unpickling, including\n    (but not necessarily limited to) AttributeError, EOFError, ImportError,\n    and IndexError.\n\n    ");
      var1.setline(78);
      return var1.getf_locals();
   }

   public PyObject _Stop$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(83);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("value", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Pickler$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(173);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, PyString.fromInterned("This takes a file-like object for writing a pickle data stream.\n\n        The optional protocol argument tells the pickler to use the\n        given protocol; supported protocols are 0, 1, 2.  The default\n        protocol is 0, to be backwards compatible.  (Protocol 0 is the\n        only protocol that can be written to a file opened in text\n        mode and read back successfully.  When using a protocol higher\n        than 0, make sure the file is opened in binary mode, both when\n        pickling and unpickling.)\n\n        Protocol 1 is more efficient than protocol 0; protocol 2 is\n        more efficient than protocol 1.\n\n        Specifying a negative protocol version selects the highest\n        protocol version supported.  The higher the protocol used, the\n        more recent the version of Python needed to read the pickle\n        produced.\n\n        The file parameter must have a write() method that accepts a single\n        string argument.  It can thus be an open file object, a StringIO\n        object, or any other custom object that meets this interface.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(209);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_memo$8, PyString.fromInterned("Clears the pickler's \"memo\".\n\n        The memo is the data structure that remembers which objects the\n        pickler has already seen, so that shared or recursive objects are\n        pickled by reference and not by value.  This method is useful when\n        re-using picklers.\n\n        "));
      var1.setlocal("clear_memo", var4);
      var3 = null;
      var1.setline(220);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump$9, PyString.fromInterned("Write a pickled representation of obj to the open file."));
      var1.setlocal("dump", var4);
      var3 = null;
      var1.setline(227);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, memoize$10, PyString.fromInterned("Store an object in the memo."));
      var1.setlocal("memoize", var4);
      var3 = null;
      var1.setline(250);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, put$11, (PyObject)null);
      var1.setlocal("put", var4);
      var3 = null;
      var1.setline(260);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, get$12, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(269);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save$13, (PyObject)null);
      var1.setlocal("save", var4);
      var3 = null;
      var1.setline(333);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, persistent_id$14, (PyObject)null);
      var1.setlocal("persistent_id", var4);
      var3 = null;
      var1.setline(337);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_pers$15, (PyObject)null);
      var1.setlocal("save_pers", var4);
      var3 = null;
      var1.setline(345);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, save_reduce$16, (PyObject)null);
      var1.setlocal("save_reduce", var4);
      var3 = null;
      var1.setline(424);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("dispatch", var5);
      var3 = null;
      var1.setline(426);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_none$17, (PyObject)null);
      var1.setlocal("save_none", var4);
      var3 = null;
      var1.setline(428);
      PyObject var6 = var1.getname("save_none");
      var1.getname("dispatch").__setitem__(var1.getname("NoneType"), var6);
      var3 = null;
      var1.setline(430);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_bool$18, (PyObject)null);
      var1.setlocal("save_bool", var4);
      var3 = null;
      var1.setline(435);
      var6 = var1.getname("save_bool");
      var1.getname("dispatch").__setitem__(var1.getname("bool"), var6);
      var3 = null;
      var1.setline(437);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, save_int$19, (PyObject)null);
      var1.setlocal("save_int", var4);
      var3 = null;
      var1.setline(459);
      var6 = var1.getname("save_int");
      var1.getname("dispatch").__setitem__(var1.getname("IntType"), var6);
      var3 = null;
      var1.setline(461);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, save_long$20, (PyObject)null);
      var1.setlocal("save_long", var4);
      var3 = null;
      var1.setline(471);
      var6 = var1.getname("save_long");
      var1.getname("dispatch").__setitem__(var1.getname("LongType"), var6);
      var3 = null;
      var1.setline(473);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, save_float$21, (PyObject)null);
      var1.setlocal("save_float", var4);
      var3 = null;
      var1.setline(478);
      var6 = var1.getname("save_float");
      var1.getname("dispatch").__setitem__(var1.getname("FloatType"), var6);
      var3 = null;
      var1.setline(480);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, save_string$22, (PyObject)null);
      var1.setlocal("save_string", var4);
      var3 = null;
      var1.setline(490);
      var6 = var1.getname("save_string");
      var1.getname("dispatch").__setitem__(var1.getname("StringType"), var6);
      var3 = null;
      var1.setline(492);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, save_unicode$23, (PyObject)null);
      var1.setlocal("save_unicode", var4);
      var3 = null;
      var1.setline(502);
      var6 = var1.getname("save_unicode");
      var1.getname("dispatch").__setitem__(var1.getname("UnicodeType"), var6);
      var3 = null;
      var1.setline(504);
      var6 = var1.getname("StringType");
      PyObject var10000 = var6._is(var1.getname("UnicodeType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(506);
         var3 = new PyObject[]{var1.getname("struct").__getattr__("pack")};
         var4 = new PyFunction(var1.f_globals, var3, save_string$24, (PyObject)null);
         var1.setlocal("save_string", var4);
         var3 = null;
         var1.setline(530);
         var6 = var1.getname("save_string");
         var1.getname("dispatch").__setitem__(var1.getname("StringType"), var6);
         var3 = null;
      }

      var1.setline(532);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_tuple$25, (PyObject)null);
      var1.setlocal("save_tuple", var4);
      var3 = null;
      var1.setline(583);
      var6 = var1.getname("save_tuple");
      var1.getname("dispatch").__setitem__(var1.getname("TupleType"), var6);
      var3 = null;
      var1.setline(588);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_empty_tuple$26, (PyObject)null);
      var1.setlocal("save_empty_tuple", var4);
      var3 = null;
      var1.setline(591);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_list$27, (PyObject)null);
      var1.setlocal("save_list", var4);
      var3 = null;
      var1.setline(602);
      var6 = var1.getname("save_list");
      var1.getname("dispatch").__setitem__(var1.getname("ListType"), var6);
      var3 = null;
      var1.setline(606);
      PyInteger var7 = Py.newInteger(1000);
      var1.setlocal("_BATCHSIZE", var7);
      var3 = null;
      var1.setline(608);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _batch_appends$28, (PyObject)null);
      var1.setlocal("_batch_appends", var4);
      var3 = null;
      var1.setline(640);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_dict$29, (PyObject)null);
      var1.setlocal("save_dict", var4);
      var3 = null;
      var1.setline(651);
      var6 = var1.getname("save_dict");
      var1.getname("dispatch").__setitem__(var1.getname("DictionaryType"), var6);
      var3 = null;
      var1.setline(652);
      var6 = var1.getname("PyStringMap");
      var10000 = var6._is(var1.getname("None"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(653);
         var6 = var1.getname("save_dict");
         var1.getname("dispatch").__setitem__(var1.getname("PyStringMap"), var6);
         var3 = null;
      }

      var1.setline(655);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _batch_setitems$30, (PyObject)null);
      var1.setlocal("_batch_setitems", var4);
      var3 = null;
      var1.setline(690);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, save_inst$31, (PyObject)null);
      var1.setlocal("save_inst", var4);
      var3 = null;
      var1.setline(728);
      var6 = var1.getname("save_inst");
      var1.getname("dispatch").__setitem__(var1.getname("InstanceType"), var6);
      var3 = null;
      var1.setline(730);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("struct").__getattr__("pack")};
      var4 = new PyFunction(var1.f_globals, var3, save_global$32, (PyObject)null);
      var1.setlocal("save_global", var4);
      var3 = null;
      var1.setline(770);
      var6 = var1.getname("save_global");
      var1.getname("dispatch").__setitem__(var1.getname("ClassType"), var6);
      var3 = null;
      var1.setline(771);
      var6 = var1.getname("save_global");
      var1.getname("dispatch").__setitem__(var1.getname("FunctionType"), var6);
      var3 = null;
      var1.setline(772);
      var6 = var1.getname("save_global");
      var1.getname("dispatch").__setitem__(var1.getname("BuiltinFunctionType"), var6);
      var3 = null;
      var1.setline(773);
      var6 = var1.getname("save_global");
      var1.getname("dispatch").__setitem__(var1.getname("TypeType"), var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyString.fromInterned("This takes a file-like object for writing a pickle data stream.\n\n        The optional protocol argument tells the pickler to use the\n        given protocol; supported protocols are 0, 1, 2.  The default\n        protocol is 0, to be backwards compatible.  (Protocol 0 is the\n        only protocol that can be written to a file opened in text\n        mode and read back successfully.  When using a protocol higher\n        than 0, make sure the file is opened in binary mode, both when\n        pickling and unpickling.)\n\n        Protocol 1 is more efficient than protocol 0; protocol 2 is\n        more efficient than protocol 1.\n\n        Specifying a negative protocol version selects the highest\n        protocol version supported.  The higher the protocol used, the\n        more recent the version of Python needed to read the pickle\n        produced.\n\n        The file parameter must have a write() method that accepts a single\n        string argument.  It can thus be an open file object, a StringIO\n        object, or any other custom object that meets this interface.\n\n        ");
      var1.setline(197);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(198);
         var5 = Py.newInteger(0);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(199);
      var3 = var1.getlocal(2);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(200);
         var3 = var1.getglobal("HIGHEST_PROTOCOL");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(201);
         var5 = Py.newInteger(0);
         PyObject var10001 = var1.getlocal(2);
         PyInteger var7 = var5;
         var3 = var10001;
         PyObject var4;
         if ((var4 = var7._le(var10001)).__nonzero__()) {
            var4 = var3._le(var1.getglobal("HIGHEST_PROTOCOL"));
         }

         var3 = null;
         if (var4.__not__().__nonzero__()) {
            var1.setline(202);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("pickle protocol must be <= %d")._mod(var1.getglobal("HIGHEST_PROTOCOL"))));
         }
      }

      var1.setline(203);
      var3 = var1.getlocal(1).__getattr__("write");
      var1.getlocal(0).__setattr__("write", var3);
      var3 = null;
      var1.setline(204);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"memo", var6);
      var3 = null;
      var1.setline(205);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("proto", var3);
      var3 = null;
      var1.setline(206);
      var3 = var1.getlocal(2);
      var10000 = var3._ge(Py.newInteger(1));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("bin", var3);
      var3 = null;
      var1.setline(207);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"fast", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_memo$8(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      PyString.fromInterned("Clears the pickler's \"memo\".\n\n        The memo is the data structure that remembers which objects the\n        pickler has already seen, so that shared or recursive objects are\n        pickled by reference and not by value.  This method is useful when\n        re-using picklers.\n\n        ");
      var1.setline(218);
      var1.getlocal(0).__getattr__("memo").__getattr__("clear").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump$9(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyString.fromInterned("Write a pickled representation of obj to the open file.");
      var1.setline(222);
      PyObject var3 = var1.getlocal(0).__getattr__("proto");
      PyObject var10000 = var3._ge(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(223);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("PROTO")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(0).__getattr__("proto"))));
      }

      var1.setline(224);
      var1.getlocal(0).__getattr__("save").__call__(var2, var1.getlocal(1));
      var1.setline(225);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("STOP"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject memoize$10(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyString.fromInterned("Store an object in the memo.");
      var1.setline(242);
      if (var1.getlocal(0).__getattr__("fast").__nonzero__()) {
         var1.setline(243);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(244);
         PyObject var3;
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getglobal("id").__call__(var2, var1.getlocal(1));
            PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("memo"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(245);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("memo"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(246);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("put").__call__(var2, var1.getlocal(2)));
         var1.setline(247);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
         var1.getlocal(0).__getattr__("memo").__setitem__((PyObject)var1.getglobal("id").__call__(var2, var1.getlocal(1)), var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject put$11(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(252);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._lt(Py.newInteger(256));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(253);
            var3 = var1.getglobal("BINPUT")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(255);
            var3 = var1.getglobal("LONG_BINPUT")._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(257);
         var3 = var1.getglobal("PUT")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get$12(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(262);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._lt(Py.newInteger(256));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(263);
            var3 = var1.getglobal("BINGET")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(265);
            var3 = var1.getglobal("LONG_BINGET")._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(267);
         var3 = var1.getglobal("GET")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject save$13(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getlocal(0).__getattr__("persistent_id").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(272);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(273);
         var1.getlocal(0).__getattr__("save_pers").__call__(var2, var1.getlocal(2));
         var1.setline(274);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(277);
         var3 = var1.getlocal(0).__getattr__("memo").__getattr__("get").__call__(var2, var1.getglobal("id").__call__(var2, var1.getlocal(1)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(278);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(279);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0))));
            var1.setline(280);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(283);
            var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(284);
            var3 = var1.getlocal(0).__getattr__("dispatch").__getattr__("get").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(285);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(286);
               var1.getlocal(5).__call__(var2, var1.getlocal(0), var1.getlocal(1));
               var1.setline(287);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(290);
               var3 = var1.getglobal("dispatch_table").__getattr__("get").__call__(var2, var1.getlocal(4));
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(291);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(292);
                  var3 = var1.getlocal(6).__call__(var2, var1.getlocal(1));
                  var1.setlocal(7, var3);
                  var3 = null;
               } else {
                  try {
                     var1.setline(296);
                     var3 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(4), var1.getglobal("TypeType"));
                     var1.setlocal(8, var3);
                     var3 = null;
                  } catch (Throwable var5) {
                     PyException var8 = Py.setException(var5, var1);
                     if (!var8.match(var1.getglobal("TypeError"))) {
                        throw var8;
                     }

                     var1.setline(298);
                     PyInteger var4 = Py.newInteger(0);
                     var1.setlocal(8, var4);
                     var4 = null;
                  }

                  var1.setline(299);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(300);
                     var1.getlocal(0).__getattr__("save_global").__call__(var2, var1.getlocal(1));
                     var1.setline(301);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(304);
                  var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__reduce_ex__"), (PyObject)var1.getglobal("None"));
                  var1.setlocal(6, var3);
                  var3 = null;
                  var1.setline(305);
                  if (var1.getlocal(6).__nonzero__()) {
                     var1.setline(306);
                     var3 = var1.getlocal(6).__call__(var2, var1.getlocal(0).__getattr__("proto"));
                     var1.setlocal(7, var3);
                     var3 = null;
                  } else {
                     var1.setline(308);
                     var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__reduce__"), (PyObject)var1.getglobal("None"));
                     var1.setlocal(6, var3);
                     var3 = null;
                     var1.setline(309);
                     if (!var1.getlocal(6).__nonzero__()) {
                        var1.setline(312);
                        throw Py.makeException(var1.getglobal("PicklingError").__call__(var2, PyString.fromInterned("Can't pickle %r object: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4).__getattr__("__name__"), var1.getlocal(1)}))));
                     }

                     var1.setline(310);
                     var3 = var1.getlocal(6).__call__(var2);
                     var1.setlocal(7, var3);
                     var3 = null;
                  }
               }

               var1.setline(316);
               var3 = var1.getglobal("type").__call__(var2, var1.getlocal(7));
               PyObject var10000 = var3._is(var1.getglobal("StringType"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(317);
                  var1.getlocal(0).__getattr__("save_global").__call__(var2, var1.getlocal(1), var1.getlocal(7));
                  var1.setline(318);
                  var1.f_lasti = -1;
                  return Py.None;
               } else {
                  var1.setline(321);
                  var3 = var1.getglobal("type").__call__(var2, var1.getlocal(7));
                  var10000 = var3._isnot(var1.getglobal("TupleType"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(322);
                     throw Py.makeException(var1.getglobal("PicklingError").__call__(var2, PyString.fromInterned("%s must return string or tuple")._mod(var1.getlocal(6))));
                  } else {
                     var1.setline(325);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(326);
                     PyInteger var9 = Py.newInteger(2);
                     PyObject var10001 = var1.getlocal(9);
                     PyInteger var11 = var9;
                     var3 = var10001;
                     PyObject var6;
                     if ((var6 = var11._le(var10001)).__nonzero__()) {
                        var6 = var3._le(Py.newInteger(5));
                     }

                     var3 = null;
                     if (var6.__not__().__nonzero__()) {
                        var1.setline(327);
                        throw Py.makeException(var1.getglobal("PicklingError").__call__(var2, PyString.fromInterned("Tuple returned by %s must have two to five elements")._mod(var1.getlocal(6))));
                     } else {
                        var1.setline(331);
                        var10000 = var1.getlocal(0).__getattr__("save_reduce");
                        PyObject[] var10 = new PyObject[]{var1.getlocal(1)};
                        String[] var7 = new String[]{"obj"};
                        var10000._callextra(var10, var7, var1.getlocal(7), (PyObject)null);
                        var3 = null;
                        var1.f_lasti = -1;
                        return Py.None;
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject persistent_id$14(PyFrame var1, ThreadState var2) {
      var1.setline(335);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject save_pers$15(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(340);
         var1.getlocal(0).__getattr__("save").__call__(var2, var1.getlocal(1));
         var1.setline(341);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BINPERSID"));
      } else {
         var1.setline(343);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("PERSID")._add(var1.getglobal("str").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_reduce$16(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("TupleType")).__not__().__nonzero__()) {
         var1.setline(351);
         throw Py.makeException(var1.getglobal("PicklingError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("args from reduce() should be a tuple")));
      } else {
         var1.setline(354);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__call__")).__not__().__nonzero__()) {
            var1.setline(355);
            throw Py.makeException(var1.getglobal("PicklingError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("func from reduce should be callable")));
         } else {
            var1.setline(357);
            PyObject var3 = var1.getlocal(0).__getattr__("save");
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(358);
            var3 = var1.getlocal(0).__getattr__("write");
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(361);
            var3 = var1.getlocal(0).__getattr__("proto");
            PyObject var10000 = var3._ge(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__name__"), (PyObject)PyString.fromInterned(""));
               var10000 = var3._eq(PyString.fromInterned("__newobj__"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(388);
               var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(389);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("__new__")).__not__().__nonzero__()) {
                  var1.setline(390);
                  throw Py.makeException(var1.getglobal("PicklingError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("args[0] from __newobj__ args has no __new__")));
               }

               var1.setline(392);
               var3 = var1.getlocal(6);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(9);
                  var10000 = var3._isnot(var1.getlocal(6).__getattr__("__class__"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(393);
                  throw Py.makeException(var1.getglobal("PicklingError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("args[0] from __newobj__ args has the wrong class")));
               }

               var1.setline(395);
               var3 = var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(396);
               var1.getlocal(7).__call__(var2, var1.getlocal(9));
               var1.setline(397);
               var1.getlocal(7).__call__(var2, var1.getlocal(2));
               var1.setline(398);
               var1.getlocal(8).__call__(var2, var1.getglobal("NEWOBJ"));
            } else {
               var1.setline(400);
               var1.getlocal(7).__call__(var2, var1.getlocal(1));
               var1.setline(401);
               var1.getlocal(7).__call__(var2, var1.getlocal(2));
               var1.setline(402);
               var1.getlocal(8).__call__(var2, var1.getglobal("REDUCE"));
            }

            var1.setline(404);
            var3 = var1.getlocal(6);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(405);
               var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(6));
            }

            var1.setline(412);
            var3 = var1.getlocal(4);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(413);
               var1.getlocal(0).__getattr__("_batch_appends").__call__(var2, var1.getlocal(4));
            }

            var1.setline(415);
            var3 = var1.getlocal(5);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(416);
               var1.getlocal(0).__getattr__("_batch_setitems").__call__(var2, var1.getlocal(5));
            }

            var1.setline(418);
            var3 = var1.getlocal(3);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(419);
               var1.getlocal(7).__call__(var2, var1.getlocal(3));
               var1.setline(420);
               var1.getlocal(8).__call__(var2, var1.getglobal("BUILD"));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject save_none$17(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("NONE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_bool$18(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyObject var3 = var1.getlocal(0).__getattr__("proto");
      PyObject var10000 = var3._ge(Py.newInteger(2));
      var3 = null;
      PyObject var10002;
      if (var10000.__nonzero__()) {
         var1.setline(432);
         var10000 = var1.getlocal(0).__getattr__("write");
         var10002 = var1.getlocal(1);
         if (var10002.__nonzero__()) {
            var10002 = var1.getglobal("NEWTRUE");
         }

         if (!var10002.__nonzero__()) {
            var10002 = var1.getglobal("NEWFALSE");
         }

         var10000.__call__(var2, var10002);
      } else {
         var1.setline(434);
         var10000 = var1.getlocal(0).__getattr__("write");
         var10002 = var1.getlocal(1);
         if (var10002.__nonzero__()) {
            var10002 = var1.getglobal("TRUE");
         }

         if (!var10002.__nonzero__()) {
            var10002 = var1.getglobal("FALSE");
         }

         var10000.__call__(var2, var10002);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_int$19(PyFrame var1, ThreadState var2) {
      var1.setline(438);
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(443);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(444);
            var3 = var1.getlocal(1);
            var10000 = var3._le(Py.newInteger(255));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(445);
               var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BININT1")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(1))));
               var1.setline(446);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(447);
            var3 = var1.getlocal(1);
            var10000 = var3._le(Py.newInteger(65535));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(448);
               var1.getlocal(0).__getattr__("write").__call__(var2, PyString.fromInterned("%c%c%c")._mod(new PyTuple(new PyObject[]{var1.getglobal("BININT2"), var1.getlocal(1)._and(Py.newInteger(255)), var1.getlocal(1)._rshift(Py.newInteger(8))})));
               var1.setline(449);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(451);
         var3 = var1.getlocal(1)._rshift(Py.newInteger(31));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(452);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._eq(Py.newInteger(-1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(455);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BININT")._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(1))));
            var1.setline(456);
            var1.f_lasti = -1;
            return Py.None;
         }
      }

      var1.setline(458);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("INT")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_long$20(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyObject var3 = var1.getlocal(0).__getattr__("proto");
      PyObject var10000 = var3._ge(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(463);
         var3 = var1.getglobal("encode_long").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(464);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(465);
         var3 = var1.getlocal(4);
         var10000 = var3._lt(Py.newInteger(256));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(466);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("LONG1")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(4)))._add(var1.getlocal(3)));
         } else {
            var1.setline(468);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("LONG4")._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(4)))._add(var1.getlocal(3)));
         }

         var1.setline(469);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(470);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("LONG")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n")));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject save_float$21(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(475);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BINFLOAT")._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">d"), (PyObject)var1.getlocal(1))));
      } else {
         var1.setline(477);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("FLOAT")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_string$22(PyFrame var1, ThreadState var2) {
      var1.setline(481);
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(482);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(483);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._lt(Py.newInteger(256));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(484);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("SHORT_BINSTRING")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(3)))._add(var1.getlocal(1)));
         } else {
            var1.setline(486);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BINSTRING")._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(3)))._add(var1.getlocal(1)));
         }
      } else {
         var1.setline(488);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("STRING")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n")));
      }

      var1.setline(489);
      var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_unicode$23(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(494);
         var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(495);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(496);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BINUNICODE")._add(var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(4)))._add(var1.getlocal(3)));
      } else {
         var1.setline(498);
         var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("\\u005c"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(499);
         var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)PyString.fromInterned("\\u000a"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(500);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("UNICODE")._add(var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raw-unicode-escape")))._add(PyString.fromInterned("\n")));
      }

      var1.setline(501);
      var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_string$24(PyFrame var1, ThreadState var2) {
      var1.setline(507);
      PyObject var3 = var1.getlocal(1).__getattr__("isunicode").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(509);
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(510);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(511);
            var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(512);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(513);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._lt(Py.newInteger(256));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(514);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("SHORT_BINSTRING")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(4)))._add(var1.getlocal(1)));
         } else {
            var1.setline(516);
            var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(4));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(517);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(518);
               var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BINUNICODE")._add(var1.getlocal(5))._add(var1.getlocal(1)));
            } else {
               var1.setline(520);
               var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("BINSTRING")._add(var1.getlocal(5))._add(var1.getlocal(1)));
            }
         }
      } else {
         var1.setline(522);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(523);
            var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("\\u005c"));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(524);
            var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)PyString.fromInterned("\\u000a"));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(525);
            var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raw-unicode-escape"));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(526);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("UNICODE")._add(var1.getlocal(1))._add(PyString.fromInterned("\n")));
         } else {
            var1.setline(528);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("STRING")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n")));
         }
      }

      var1.setline(529);
      var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_tuple$25(PyFrame var1, ThreadState var2) {
      var1.setline(533);
      PyObject var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(534);
      var3 = var1.getlocal(0).__getattr__("proto");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(536);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(537);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(538);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(539);
            var1.getlocal(2).__call__(var2, var1.getglobal("EMPTY_TUPLE"));
         } else {
            var1.setline(541);
            var1.getlocal(2).__call__(var2, var1.getglobal("MARK")._add(var1.getglobal("TUPLE")));
         }

         var1.setline(542);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(544);
         var3 = var1.getlocal(0).__getattr__("save");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(545);
         var3 = var1.getlocal(0).__getattr__("memo");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(546);
         var3 = var1.getlocal(4);
         var10000 = var3._le(Py.newInteger(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._ge(Py.newInteger(2));
            var3 = null;
         }

         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(547);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(547);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(550);
                  var3 = var1.getglobal("id").__call__(var2, var1.getlocal(1));
                  var10000 = var3._in(var1.getlocal(6));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(551);
                     var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(6).__getitem__(var1.getglobal("id").__call__(var2, var1.getlocal(1))).__getitem__(Py.newInteger(0)));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(552);
                     var1.getlocal(2).__call__(var2, var1.getglobal("POP")._mul(var1.getlocal(4))._add(var1.getlocal(8)));
                  } else {
                     var1.setline(554);
                     var1.getlocal(2).__call__(var2, var1.getglobal("_tuplesize2code").__getitem__(var1.getlocal(4)));
                     var1.setline(555);
                     var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
                  }

                  var1.setline(556);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(7, var4);
               var1.setline(548);
               var1.getlocal(5).__call__(var2, var1.getlocal(7));
            }
         } else {
            var1.setline(560);
            var1.getlocal(2).__call__(var2, var1.getglobal("MARK"));
            var1.setline(561);
            var3 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(561);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(564);
                  var3 = var1.getglobal("id").__call__(var2, var1.getlocal(1));
                  var10000 = var3._in(var1.getlocal(6));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(572);
                     var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(6).__getitem__(var1.getglobal("id").__call__(var2, var1.getlocal(1))).__getitem__(Py.newInteger(0)));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(573);
                     if (var1.getlocal(3).__nonzero__()) {
                        var1.setline(574);
                        var1.getlocal(2).__call__(var2, var1.getglobal("POP_MARK")._add(var1.getlocal(8)));
                     } else {
                        var1.setline(576);
                        var1.getlocal(2).__call__(var2, var1.getglobal("POP")._mul(var1.getlocal(4)._add(Py.newInteger(1)))._add(var1.getlocal(8)));
                     }

                     var1.setline(577);
                     var1.f_lasti = -1;
                     return Py.None;
                  } else {
                     var1.setline(580);
                     var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("TUPLE"));
                     var1.setline(581);
                     var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
                     var1.f_lasti = -1;
                     return Py.None;
                  }
               }

               var1.setlocal(7, var4);
               var1.setline(562);
               var1.getlocal(5).__call__(var2, var1.getlocal(7));
            }
         }
      }
   }

   public PyObject save_empty_tuple$26(PyFrame var1, ThreadState var2) {
      var1.setline(589);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("EMPTY_TUPLE"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_list$27(PyFrame var1, ThreadState var2) {
      var1.setline(592);
      PyObject var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(594);
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(595);
         var1.getlocal(2).__call__(var2, var1.getglobal("EMPTY_LIST"));
      } else {
         var1.setline(597);
         var1.getlocal(2).__call__(var2, var1.getglobal("MARK")._add(var1.getglobal("LIST")));
      }

      var1.setline(599);
      var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
      var1.setline(600);
      var1.getlocal(0).__getattr__("_batch_appends").__call__(var2, var1.getglobal("iter").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _batch_appends$28(PyFrame var1, ThreadState var2) {
      var1.setline(610);
      PyObject var3 = var1.getlocal(0).__getattr__("save");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(611);
      var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(613);
      PyObject var4;
      if (var1.getlocal(0).__getattr__("bin").__not__().__nonzero__()) {
         var1.setline(614);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(614);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(617);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(615);
            var1.getlocal(2).__call__(var2, var1.getlocal(4));
            var1.setline(616);
            var1.getlocal(3).__call__(var2, var1.getglobal("APPEND"));
         }
      } else {
         var1.setline(619);
         var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(0).__getattr__("_BATCHSIZE"));
         var1.setlocal(5, var3);
         var3 = null;

         while(true) {
            while(true) {
               var1.setline(620);
               var3 = var1.getlocal(1);
               PyObject var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(621);
               PyList var9 = new PyList(Py.EmptyObjects);
               var1.setlocal(6, var9);
               var3 = null;
               var1.setline(622);
               var3 = var1.getlocal(5).__iter__();

               while(true) {
                  var1.setline(622);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(7, var4);

                  PyException var5;
                  try {
                     var1.setline(624);
                     PyObject var8 = var1.getlocal(1).__getattr__("next").__call__(var2);
                     var1.setlocal(4, var8);
                     var5 = null;
                     var1.setline(625);
                     var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(4));
                  } catch (Throwable var7) {
                     var5 = Py.setException(var7, var1);
                     if (var5.match(var1.getglobal("StopIteration"))) {
                        var1.setline(627);
                        PyObject var6 = var1.getglobal("None");
                        var1.setlocal(1, var6);
                        var6 = null;
                        break;
                     }

                     throw var5;
                  }
               }

               var1.setline(629);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(630);
               var3 = var1.getlocal(8);
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(631);
                  var1.getlocal(3).__call__(var2, var1.getglobal("MARK"));
                  var1.setline(632);
                  var3 = var1.getlocal(6).__iter__();

                  while(true) {
                     var1.setline(632);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(634);
                        var1.getlocal(3).__call__(var2, var1.getglobal("APPENDS"));
                        break;
                     }

                     var1.setlocal(4, var4);
                     var1.setline(633);
                     var1.getlocal(2).__call__(var2, var1.getlocal(4));
                  }
               } else {
                  var1.setline(635);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(636);
                     var1.getlocal(2).__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0)));
                     var1.setline(637);
                     var1.getlocal(3).__call__(var2, var1.getglobal("APPEND"));
                  }
               }
            }
         }
      }
   }

   public PyObject save_dict$29(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyObject var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(643);
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(644);
         var1.getlocal(2).__call__(var2, var1.getglobal("EMPTY_DICT"));
      } else {
         var1.setline(646);
         var1.getlocal(2).__call__(var2, var1.getglobal("MARK")._add(var1.getglobal("DICT")));
      }

      var1.setline(648);
      var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
      var1.setline(649);
      var1.getlocal(0).__getattr__("_batch_setitems").__call__(var2, var1.getlocal(1).__getattr__("iteritems").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _batch_setitems$30(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyObject var3 = var1.getlocal(0).__getattr__("save");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(658);
      var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(660);
      PyObject var4;
      PyObject var6;
      PyObject[] var11;
      if (var1.getlocal(0).__getattr__("bin").__not__().__nonzero__()) {
         var1.setline(661);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(661);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(665);
               var1.f_lasti = -1;
               return Py.None;
            }

            var11 = Py.unpackSequence(var4, 2);
            var6 = var11[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var11[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(662);
            var1.getlocal(2).__call__(var2, var1.getlocal(4));
            var1.setline(663);
            var1.getlocal(2).__call__(var2, var1.getlocal(5));
            var1.setline(664);
            var1.getlocal(3).__call__(var2, var1.getglobal("SETITEM"));
         }
      } else {
         var1.setline(667);
         var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(0).__getattr__("_BATCHSIZE"));
         var1.setlocal(6, var3);
         var3 = null;

         while(true) {
            while(true) {
               var1.setline(668);
               var3 = var1.getlocal(1);
               PyObject var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(669);
               PyList var10 = new PyList(Py.EmptyObjects);
               var1.setlocal(7, var10);
               var3 = null;
               var1.setline(670);
               var3 = var1.getlocal(6).__iter__();

               PyException var5;
               while(true) {
                  var1.setline(670);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(8, var4);

                  try {
                     var1.setline(672);
                     var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("next").__call__(var2));
                  } catch (Throwable var7) {
                     var5 = Py.setException(var7, var1);
                     if (var5.match(var1.getglobal("StopIteration"))) {
                        var1.setline(674);
                        var6 = var1.getglobal("None");
                        var1.setlocal(1, var6);
                        var6 = null;
                        break;
                     }

                     throw var5;
                  }
               }

               var1.setline(676);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(677);
               var3 = var1.getlocal(9);
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(678);
                  var1.getlocal(3).__call__(var2, var1.getglobal("MARK"));
                  var1.setline(679);
                  var3 = var1.getlocal(7).__iter__();

                  while(true) {
                     var1.setline(679);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(682);
                        var1.getlocal(3).__call__(var2, var1.getglobal("SETITEMS"));
                        break;
                     }

                     var11 = Py.unpackSequence(var4, 2);
                     var6 = var11[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var11[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var1.setline(680);
                     var1.getlocal(2).__call__(var2, var1.getlocal(4));
                     var1.setline(681);
                     var1.getlocal(2).__call__(var2, var1.getlocal(5));
                  }
               } else {
                  var1.setline(683);
                  if (var1.getlocal(9).__nonzero__()) {
                     var1.setline(684);
                     var3 = var1.getlocal(7).__getitem__(Py.newInteger(0));
                     PyObject[] var8 = Py.unpackSequence(var3, 2);
                     PyObject var9 = var8[0];
                     var1.setlocal(4, var9);
                     var5 = null;
                     var9 = var8[1];
                     var1.setlocal(5, var9);
                     var5 = null;
                     var3 = null;
                     var1.setline(685);
                     var1.getlocal(2).__call__(var2, var1.getlocal(4));
                     var1.setline(686);
                     var1.getlocal(2).__call__(var2, var1.getlocal(5));
                     var1.setline(687);
                     var1.getlocal(3).__call__(var2, var1.getglobal("SETITEM"));
                  }
               }
            }
         }
      }
   }

   public PyObject save_inst$31(PyFrame var1, ThreadState var2) {
      var1.setline(691);
      PyObject var3 = var1.getlocal(1).__getattr__("__class__");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(693);
      var3 = var1.getlocal(0).__getattr__("memo");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(694);
      var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(695);
      var3 = var1.getlocal(0).__getattr__("save");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(697);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__getinitargs__")).__nonzero__()) {
         var1.setline(698);
         var3 = var1.getlocal(1).__getattr__("__getinitargs__").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(699);
         var1.getglobal("len").__call__(var2, var1.getlocal(6));
         var1.setline(700);
         var1.getglobal("_keep_alive").__call__(var2, var1.getlocal(6), var1.getlocal(3));
      } else {
         var1.setline(702);
         PyTuple var6 = new PyTuple(Py.EmptyObjects);
         var1.setlocal(6, var6);
         var3 = null;
      }

      var1.setline(704);
      var1.getlocal(4).__call__(var2, var1.getglobal("MARK"));
      var1.setline(706);
      PyObject var4;
      if (var1.getlocal(0).__getattr__("bin").__nonzero__()) {
         var1.setline(707);
         var1.getlocal(5).__call__(var2, var1.getlocal(2));
         var1.setline(708);
         var3 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(708);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(710);
               var1.getlocal(4).__call__(var2, var1.getglobal("OBJ"));
               break;
            }

            var1.setlocal(7, var4);
            var1.setline(709);
            var1.getlocal(5).__call__(var2, var1.getlocal(7));
         }
      } else {
         var1.setline(712);
         var3 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(712);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(714);
               var1.getlocal(4).__call__(var2, var1.getglobal("INST")._add(var1.getlocal(2).__getattr__("__module__"))._add(PyString.fromInterned("\n"))._add(var1.getlocal(2).__getattr__("__name__"))._add(PyString.fromInterned("\n")));
               break;
            }

            var1.setlocal(7, var4);
            var1.setline(713);
            var1.getlocal(5).__call__(var2, var1.getlocal(7));
         }
      }

      var1.setline(716);
      var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));

      label32: {
         try {
            var1.setline(719);
            var3 = var1.getlocal(1).__getattr__("__getstate__");
            var1.setlocal(8, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("AttributeError"))) {
               var1.setline(721);
               var4 = var1.getlocal(1).__getattr__("__dict__");
               var1.setlocal(9, var4);
               var4 = null;
               break label32;
            }

            throw var7;
         }

         var1.setline(723);
         var4 = var1.getlocal(8).__call__(var2);
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(724);
         var1.getglobal("_keep_alive").__call__(var2, var1.getlocal(9), var1.getlocal(3));
      }

      var1.setline(725);
      var1.getlocal(5).__call__(var2, var1.getlocal(9));
      var1.setline(726);
      var1.getlocal(4).__call__(var2, var1.getglobal("BUILD"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_global$32(PyFrame var1, ThreadState var2) {
      var1.setline(731);
      PyObject var3 = var1.getlocal(0).__getattr__("write");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(732);
      var3 = var1.getlocal(0).__getattr__("memo");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(734);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(735);
         var3 = var1.getlocal(1).__getattr__("__name__");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(737);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("__module__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(738);
      var3 = var1.getlocal(6);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(739);
         var3 = var1.getglobal("whichmodule").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(6, var3);
         var3 = null;
      }

      try {
         var1.setline(742);
         var1.getglobal("__import__").__call__(var2, var1.getlocal(6));
         var1.setline(743);
         var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(6));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(744);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(7), var1.getlocal(2));
         var1.setlocal(8, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("KeyError"), var1.getglobal("AttributeError")}))) {
            var1.setline(746);
            throw Py.makeException(var1.getglobal("PicklingError").__call__(var2, PyString.fromInterned("Can't pickle %r: it's not found as %s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6), var1.getlocal(2)}))));
         }

         throw var6;
      }

      var1.setline(750);
      PyObject var4 = var1.getlocal(8);
      var10000 = var4._isnot(var1.getlocal(1));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(751);
         throw Py.makeException(var1.getglobal("PicklingError").__call__(var2, PyString.fromInterned("Can't pickle %r: it's not the same object as %s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6), var1.getlocal(2)}))));
      } else {
         var1.setline(755);
         var3 = var1.getlocal(0).__getattr__("proto");
         var10000 = var3._ge(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(756);
            var3 = var1.getglobal("_extension_registry").__getattr__("get").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(2)})));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(757);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(758);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var3 = var1.getlocal(9);
                  var10000 = var3._gt(Py.newInteger(0));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }

               var1.setline(759);
               var3 = var1.getlocal(9);
               var10000 = var3._le(Py.newInteger(255));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(760);
                  var1.getlocal(4).__call__(var2, var1.getglobal("EXT1")._add(var1.getglobal("chr").__call__(var2, var1.getlocal(9))));
               } else {
                  var1.setline(761);
                  var3 = var1.getlocal(9);
                  var10000 = var3._le(Py.newInteger(65535));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(762);
                     var1.getlocal(4).__call__(var2, PyString.fromInterned("%c%c%c")._mod(new PyTuple(new PyObject[]{var1.getglobal("EXT2"), var1.getlocal(9)._and(Py.newInteger(255)), var1.getlocal(9)._rshift(Py.newInteger(8))})));
                  } else {
                     var1.setline(764);
                     var1.getlocal(4).__call__(var2, var1.getglobal("EXT4")._add(var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<i"), (PyObject)var1.getlocal(9))));
                  }
               }

               var1.setline(765);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(767);
         var1.getlocal(4).__call__(var2, var1.getglobal("GLOBAL")._add(var1.getlocal(6))._add(PyString.fromInterned("\n"))._add(var1.getlocal(2))._add(PyString.fromInterned("\n")));
         var1.setline(768);
         var1.getlocal(0).__getattr__("memoize").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _keep_alive$33(PyFrame var1, ThreadState var2) {
      var1.setline(786);
      PyString.fromInterned("Keeps a reference to the object x in the memo.\n\n    Because we remember objects by their id, we have\n    to assure that possibly temporary objects are kept\n    alive by referencing them.\n    We store a reference at the id of the memo, which should\n    normally not be used unless someone tries to deepcopy\n    the memo itself...\n    ");

      try {
         var1.setline(788);
         var1.getlocal(1).__getitem__(var1.getglobal("id").__call__(var2, var1.getlocal(1))).__getattr__("append").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(791);
         PyList var4 = new PyList(new PyObject[]{var1.getlocal(0)});
         var1.getlocal(1).__setitem__((PyObject)var1.getglobal("id").__call__(var2, var1.getlocal(1)), var4);
         var4 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject whichmodule$34(PyFrame var1, ThreadState var2) {
      var1.setline(806);
      PyString.fromInterned("Figure out the module in which a function occurs.\n\n    Search sys.modules for the module.\n    Cache in classmap.\n    Return a module name.\n    If the function cannot be found, return \"__main__\".\n    ");
      var1.setline(808);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__module__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(809);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(810);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(811);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._in(var1.getglobal("classmap"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(812);
            var3 = var1.getglobal("classmap").__getitem__(var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(814);
            var4 = var1.getglobal("sys").__getattr__("modules").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(814);
               PyObject var5 = var4.__iternext__();
               PyObject[] var6;
               if (var5 == null) {
                  var1.setline(820);
                  PyString var9 = PyString.fromInterned("__main__");
                  var1.setlocal(3, var9);
                  var6 = null;
                  break;
               }

               var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(3, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(4, var7);
               var7 = null;
               var1.setline(815);
               PyObject var8 = var1.getlocal(4);
               var10000 = var8._is(var1.getglobal("None"));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(817);
                  var8 = var1.getlocal(3);
                  var10000 = var8._ne(PyString.fromInterned("__main__"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getglobal("None"));
                     var10000 = var8._is(var1.getlocal(0));
                     var6 = null;
                  }

                  if (var10000.__nonzero__()) {
                     break;
                  }
               }
            }

            var1.setline(821);
            var4 = var1.getlocal(3);
            var1.getglobal("classmap").__setitem__(var1.getlocal(0), var4);
            var4 = null;
            var1.setline(822);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject Unpickler$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(829);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$36, PyString.fromInterned("This takes a file-like object for reading a pickle data stream.\n\n        The protocol version of the pickle is detected automatically, so no\n        proto argument is needed.\n\n        The file-like object must have two methods, a read() method that\n        takes an integer argument, and a readline() method that requires no\n        arguments.  Both methods should return a string.  Thus file-like\n        object can be a file object opened for reading, a StringIO object,\n        or any other custom object that meets this interface.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(845);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load$37, PyString.fromInterned("Read a pickled object representation from the open file.\n\n        Return the reconstituted object hierarchy specified in the file.\n        "));
      var1.setlocal("load", var4);
      var3 = null;
      var1.setline(870);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, marker$38, (PyObject)null);
      var1.setlocal("marker", var4);
      var3 = null;
      var1.setline(877);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("dispatch", var5);
      var3 = null;
      var1.setline(879);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_eof$39, (PyObject)null);
      var1.setlocal("load_eof", var4);
      var3 = null;
      var1.setline(881);
      PyObject var6 = var1.getname("load_eof");
      var1.getname("dispatch").__setitem__((PyObject)PyString.fromInterned(""), var6);
      var3 = null;
      var1.setline(883);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_proto$40, (PyObject)null);
      var1.setlocal("load_proto", var4);
      var3 = null;
      var1.setline(887);
      var6 = var1.getname("load_proto");
      var1.getname("dispatch").__setitem__(var1.getname("PROTO"), var6);
      var3 = null;
      var1.setline(889);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_persid$41, (PyObject)null);
      var1.setlocal("load_persid", var4);
      var3 = null;
      var1.setline(892);
      var6 = var1.getname("load_persid");
      var1.getname("dispatch").__setitem__(var1.getname("PERSID"), var6);
      var3 = null;
      var1.setline(894);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binpersid$42, (PyObject)null);
      var1.setlocal("load_binpersid", var4);
      var3 = null;
      var1.setline(897);
      var6 = var1.getname("load_binpersid");
      var1.getname("dispatch").__setitem__(var1.getname("BINPERSID"), var6);
      var3 = null;
      var1.setline(899);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_none$43, (PyObject)null);
      var1.setlocal("load_none", var4);
      var3 = null;
      var1.setline(901);
      var6 = var1.getname("load_none");
      var1.getname("dispatch").__setitem__(var1.getname("NONE"), var6);
      var3 = null;
      var1.setline(903);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_false$44, (PyObject)null);
      var1.setlocal("load_false", var4);
      var3 = null;
      var1.setline(905);
      var6 = var1.getname("load_false");
      var1.getname("dispatch").__setitem__(var1.getname("NEWFALSE"), var6);
      var3 = null;
      var1.setline(907);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_true$45, (PyObject)null);
      var1.setlocal("load_true", var4);
      var3 = null;
      var1.setline(909);
      var6 = var1.getname("load_true");
      var1.getname("dispatch").__setitem__(var1.getname("NEWTRUE"), var6);
      var3 = null;
      var1.setline(911);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_int$46, (PyObject)null);
      var1.setlocal("load_int", var4);
      var3 = null;
      var1.setline(923);
      var6 = var1.getname("load_int");
      var1.getname("dispatch").__setitem__(var1.getname("INT"), var6);
      var3 = null;
      var1.setline(925);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binint$47, (PyObject)null);
      var1.setlocal("load_binint", var4);
      var3 = null;
      var1.setline(927);
      var6 = var1.getname("load_binint");
      var1.getname("dispatch").__setitem__(var1.getname("BININT"), var6);
      var3 = null;
      var1.setline(929);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binint1$48, (PyObject)null);
      var1.setlocal("load_binint1", var4);
      var3 = null;
      var1.setline(931);
      var6 = var1.getname("load_binint1");
      var1.getname("dispatch").__setitem__(var1.getname("BININT1"), var6);
      var3 = null;
      var1.setline(933);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binint2$49, (PyObject)null);
      var1.setlocal("load_binint2", var4);
      var3 = null;
      var1.setline(935);
      var6 = var1.getname("load_binint2");
      var1.getname("dispatch").__setitem__(var1.getname("BININT2"), var6);
      var3 = null;
      var1.setline(937);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_long$50, (PyObject)null);
      var1.setlocal("load_long", var4);
      var3 = null;
      var1.setline(939);
      var6 = var1.getname("load_long");
      var1.getname("dispatch").__setitem__(var1.getname("LONG"), var6);
      var3 = null;
      var1.setline(941);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_long1$51, (PyObject)null);
      var1.setlocal("load_long1", var4);
      var3 = null;
      var1.setline(945);
      var6 = var1.getname("load_long1");
      var1.getname("dispatch").__setitem__(var1.getname("LONG1"), var6);
      var3 = null;
      var1.setline(947);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_long4$52, (PyObject)null);
      var1.setlocal("load_long4", var4);
      var3 = null;
      var1.setline(951);
      var6 = var1.getname("load_long4");
      var1.getname("dispatch").__setitem__(var1.getname("LONG4"), var6);
      var3 = null;
      var1.setline(953);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_float$53, (PyObject)null);
      var1.setlocal("load_float", var4);
      var3 = null;
      var1.setline(955);
      var6 = var1.getname("load_float");
      var1.getname("dispatch").__setitem__(var1.getname("FLOAT"), var6);
      var3 = null;
      var1.setline(957);
      var3 = new PyObject[]{var1.getname("struct").__getattr__("unpack")};
      var4 = new PyFunction(var1.f_globals, var3, load_binfloat$54, (PyObject)null);
      var1.setlocal("load_binfloat", var4);
      var3 = null;
      var1.setline(959);
      var6 = var1.getname("load_binfloat");
      var1.getname("dispatch").__setitem__(var1.getname("BINFLOAT"), var6);
      var3 = null;
      var1.setline(961);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_string$55, (PyObject)null);
      var1.setlocal("load_string", var4);
      var3 = null;
      var1.setline(972);
      var6 = var1.getname("load_string");
      var1.getname("dispatch").__setitem__(var1.getname("STRING"), var6);
      var3 = null;
      var1.setline(974);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binstring$56, (PyObject)null);
      var1.setlocal("load_binstring", var4);
      var3 = null;
      var1.setline(977);
      var6 = var1.getname("load_binstring");
      var1.getname("dispatch").__setitem__(var1.getname("BINSTRING"), var6);
      var3 = null;
      var1.setline(979);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_unicode$57, (PyObject)null);
      var1.setlocal("load_unicode", var4);
      var3 = null;
      var1.setline(981);
      var6 = var1.getname("load_unicode");
      var1.getname("dispatch").__setitem__(var1.getname("UNICODE"), var6);
      var3 = null;
      var1.setline(983);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binunicode$58, (PyObject)null);
      var1.setlocal("load_binunicode", var4);
      var3 = null;
      var1.setline(986);
      var6 = var1.getname("load_binunicode");
      var1.getname("dispatch").__setitem__(var1.getname("BINUNICODE"), var6);
      var3 = null;
      var1.setline(988);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_short_binstring$59, (PyObject)null);
      var1.setlocal("load_short_binstring", var4);
      var3 = null;
      var1.setline(991);
      var6 = var1.getname("load_short_binstring");
      var1.getname("dispatch").__setitem__(var1.getname("SHORT_BINSTRING"), var6);
      var3 = null;
      var1.setline(993);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_tuple$60, (PyObject)null);
      var1.setlocal("load_tuple", var4);
      var3 = null;
      var1.setline(996);
      var6 = var1.getname("load_tuple");
      var1.getname("dispatch").__setitem__(var1.getname("TUPLE"), var6);
      var3 = null;
      var1.setline(998);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_empty_tuple$61, (PyObject)null);
      var1.setlocal("load_empty_tuple", var4);
      var3 = null;
      var1.setline(1000);
      var6 = var1.getname("load_empty_tuple");
      var1.getname("dispatch").__setitem__(var1.getname("EMPTY_TUPLE"), var6);
      var3 = null;
      var1.setline(1002);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_tuple1$62, (PyObject)null);
      var1.setlocal("load_tuple1", var4);
      var3 = null;
      var1.setline(1004);
      var6 = var1.getname("load_tuple1");
      var1.getname("dispatch").__setitem__(var1.getname("TUPLE1"), var6);
      var3 = null;
      var1.setline(1006);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_tuple2$63, (PyObject)null);
      var1.setlocal("load_tuple2", var4);
      var3 = null;
      var1.setline(1008);
      var6 = var1.getname("load_tuple2");
      var1.getname("dispatch").__setitem__(var1.getname("TUPLE2"), var6);
      var3 = null;
      var1.setline(1010);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_tuple3$64, (PyObject)null);
      var1.setlocal("load_tuple3", var4);
      var3 = null;
      var1.setline(1012);
      var6 = var1.getname("load_tuple3");
      var1.getname("dispatch").__setitem__(var1.getname("TUPLE3"), var6);
      var3 = null;
      var1.setline(1014);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_empty_list$65, (PyObject)null);
      var1.setlocal("load_empty_list", var4);
      var3 = null;
      var1.setline(1016);
      var6 = var1.getname("load_empty_list");
      var1.getname("dispatch").__setitem__(var1.getname("EMPTY_LIST"), var6);
      var3 = null;
      var1.setline(1018);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_empty_dictionary$66, (PyObject)null);
      var1.setlocal("load_empty_dictionary", var4);
      var3 = null;
      var1.setline(1020);
      var6 = var1.getname("load_empty_dictionary");
      var1.getname("dispatch").__setitem__(var1.getname("EMPTY_DICT"), var6);
      var3 = null;
      var1.setline(1022);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_list$67, (PyObject)null);
      var1.setlocal("load_list", var4);
      var3 = null;
      var1.setline(1025);
      var6 = var1.getname("load_list");
      var1.getname("dispatch").__setitem__(var1.getname("LIST"), var6);
      var3 = null;
      var1.setline(1027);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_dict$68, (PyObject)null);
      var1.setlocal("load_dict", var4);
      var3 = null;
      var1.setline(1036);
      var6 = var1.getname("load_dict");
      var1.getname("dispatch").__setitem__(var1.getname("DICT"), var6);
      var3 = null;
      var1.setline(1043);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _instantiate$69, (PyObject)null);
      var1.setlocal("_instantiate", var4);
      var3 = null;
      var1.setline(1066);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_inst$70, (PyObject)null);
      var1.setlocal("load_inst", var4);
      var3 = null;
      var1.setline(1071);
      var6 = var1.getname("load_inst");
      var1.getname("dispatch").__setitem__(var1.getname("INST"), var6);
      var3 = null;
      var1.setline(1073);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_obj$71, (PyObject)null);
      var1.setlocal("load_obj", var4);
      var3 = null;
      var1.setline(1078);
      var6 = var1.getname("load_obj");
      var1.getname("dispatch").__setitem__(var1.getname("OBJ"), var6);
      var3 = null;
      var1.setline(1080);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_newobj$72, (PyObject)null);
      var1.setlocal("load_newobj", var4);
      var3 = null;
      var1.setline(1085);
      var6 = var1.getname("load_newobj");
      var1.getname("dispatch").__setitem__(var1.getname("NEWOBJ"), var6);
      var3 = null;
      var1.setline(1087);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_global$73, (PyObject)null);
      var1.setlocal("load_global", var4);
      var3 = null;
      var1.setline(1092);
      var6 = var1.getname("load_global");
      var1.getname("dispatch").__setitem__(var1.getname("GLOBAL"), var6);
      var3 = null;
      var1.setline(1094);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_ext1$74, (PyObject)null);
      var1.setlocal("load_ext1", var4);
      var3 = null;
      var1.setline(1097);
      var6 = var1.getname("load_ext1");
      var1.getname("dispatch").__setitem__(var1.getname("EXT1"), var6);
      var3 = null;
      var1.setline(1099);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_ext2$75, (PyObject)null);
      var1.setlocal("load_ext2", var4);
      var3 = null;
      var1.setline(1102);
      var6 = var1.getname("load_ext2");
      var1.getname("dispatch").__setitem__(var1.getname("EXT2"), var6);
      var3 = null;
      var1.setline(1104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_ext4$76, (PyObject)null);
      var1.setlocal("load_ext4", var4);
      var3 = null;
      var1.setline(1107);
      var6 = var1.getname("load_ext4");
      var1.getname("dispatch").__setitem__(var1.getname("EXT4"), var6);
      var3 = null;
      var1.setline(1109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_extension$77, (PyObject)null);
      var1.setlocal("get_extension", var4);
      var3 = null;
      var1.setline(1122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find_class$78, (PyObject)null);
      var1.setlocal("find_class", var4);
      var3 = null;
      var1.setline(1129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_reduce$79, (PyObject)null);
      var1.setlocal("load_reduce", var4);
      var3 = null;
      var1.setline(1135);
      var6 = var1.getname("load_reduce");
      var1.getname("dispatch").__setitem__(var1.getname("REDUCE"), var6);
      var3 = null;
      var1.setline(1137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_pop$80, (PyObject)null);
      var1.setlocal("load_pop", var4);
      var3 = null;
      var1.setline(1139);
      var6 = var1.getname("load_pop");
      var1.getname("dispatch").__setitem__(var1.getname("POP"), var6);
      var3 = null;
      var1.setline(1141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_pop_mark$81, (PyObject)null);
      var1.setlocal("load_pop_mark", var4);
      var3 = null;
      var1.setline(1144);
      var6 = var1.getname("load_pop_mark");
      var1.getname("dispatch").__setitem__(var1.getname("POP_MARK"), var6);
      var3 = null;
      var1.setline(1146);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_dup$82, (PyObject)null);
      var1.setlocal("load_dup", var4);
      var3 = null;
      var1.setline(1148);
      var6 = var1.getname("load_dup");
      var1.getname("dispatch").__setitem__(var1.getname("DUP"), var6);
      var3 = null;
      var1.setline(1150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_get$83, (PyObject)null);
      var1.setlocal("load_get", var4);
      var3 = null;
      var1.setline(1152);
      var6 = var1.getname("load_get");
      var1.getname("dispatch").__setitem__(var1.getname("GET"), var6);
      var3 = null;
      var1.setline(1154);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binget$84, (PyObject)null);
      var1.setlocal("load_binget", var4);
      var3 = null;
      var1.setline(1157);
      var6 = var1.getname("load_binget");
      var1.getname("dispatch").__setitem__(var1.getname("BINGET"), var6);
      var3 = null;
      var1.setline(1159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_long_binget$85, (PyObject)null);
      var1.setlocal("load_long_binget", var4);
      var3 = null;
      var1.setline(1162);
      var6 = var1.getname("load_long_binget");
      var1.getname("dispatch").__setitem__(var1.getname("LONG_BINGET"), var6);
      var3 = null;
      var1.setline(1164);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_put$86, (PyObject)null);
      var1.setlocal("load_put", var4);
      var3 = null;
      var1.setline(1166);
      var6 = var1.getname("load_put");
      var1.getname("dispatch").__setitem__(var1.getname("PUT"), var6);
      var3 = null;
      var1.setline(1168);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_binput$87, (PyObject)null);
      var1.setlocal("load_binput", var4);
      var3 = null;
      var1.setline(1171);
      var6 = var1.getname("load_binput");
      var1.getname("dispatch").__setitem__(var1.getname("BINPUT"), var6);
      var3 = null;
      var1.setline(1173);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_long_binput$88, (PyObject)null);
      var1.setlocal("load_long_binput", var4);
      var3 = null;
      var1.setline(1176);
      var6 = var1.getname("load_long_binput");
      var1.getname("dispatch").__setitem__(var1.getname("LONG_BINPUT"), var6);
      var3 = null;
      var1.setline(1178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_append$89, (PyObject)null);
      var1.setlocal("load_append", var4);
      var3 = null;
      var1.setline(1183);
      var6 = var1.getname("load_append");
      var1.getname("dispatch").__setitem__(var1.getname("APPEND"), var6);
      var3 = null;
      var1.setline(1185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_appends$90, (PyObject)null);
      var1.setlocal("load_appends", var4);
      var3 = null;
      var1.setline(1191);
      var6 = var1.getname("load_appends");
      var1.getname("dispatch").__setitem__(var1.getname("APPENDS"), var6);
      var3 = null;
      var1.setline(1193);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_setitem$91, (PyObject)null);
      var1.setlocal("load_setitem", var4);
      var3 = null;
      var1.setline(1199);
      var6 = var1.getname("load_setitem");
      var1.getname("dispatch").__setitem__(var1.getname("SETITEM"), var6);
      var3 = null;
      var1.setline(1201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_setitems$92, (PyObject)null);
      var1.setlocal("load_setitems", var4);
      var3 = null;
      var1.setline(1209);
      var6 = var1.getname("load_setitems");
      var1.getname("dispatch").__setitem__(var1.getname("SETITEMS"), var6);
      var3 = null;
      var1.setline(1211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_build$93, (PyObject)null);
      var1.setlocal("load_build", var4);
      var3 = null;
      var1.setline(1248);
      var6 = var1.getname("load_build");
      var1.getname("dispatch").__setitem__(var1.getname("BUILD"), var6);
      var3 = null;
      var1.setline(1250);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_mark$94, (PyObject)null);
      var1.setlocal("load_mark", var4);
      var3 = null;
      var1.setline(1252);
      var6 = var1.getname("load_mark");
      var1.getname("dispatch").__setitem__(var1.getname("MARK"), var6);
      var3 = null;
      var1.setline(1254);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_stop$95, (PyObject)null);
      var1.setlocal("load_stop", var4);
      var3 = null;
      var1.setline(1257);
      var6 = var1.getname("load_stop");
      var1.getname("dispatch").__setitem__(var1.getname("STOP"), var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$36(PyFrame var1, ThreadState var2) {
      var1.setline(840);
      PyString.fromInterned("This takes a file-like object for reading a pickle data stream.\n\n        The protocol version of the pickle is detected automatically, so no\n        proto argument is needed.\n\n        The file-like object must have two methods, a read() method that\n        takes an integer argument, and a readline() method that requires no\n        arguments.  Both methods should return a string.  Thus file-like\n        object can be a file object opened for reading, a StringIO object,\n        or any other custom object that meets this interface.\n        ");
      var1.setline(841);
      PyObject var3 = var1.getlocal(1).__getattr__("readline");
      var1.getlocal(0).__setattr__("readline", var3);
      var3 = null;
      var1.setline(842);
      var3 = var1.getlocal(1).__getattr__("read");
      var1.getlocal(0).__setattr__("read", var3);
      var3 = null;
      var1.setline(843);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"memo", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load$37(PyFrame var1, ThreadState var2) {
      var1.setline(849);
      PyString.fromInterned("Read a pickled object representation from the open file.\n\n        Return the reconstituted object hierarchy specified in the file.\n        ");
      var1.setline(850);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.getlocal(0).__setattr__("mark", var3);
      var3 = null;
      var1.setline(851);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var6);
      var3 = null;
      var1.setline(852);
      var3 = var1.getlocal(0).__getattr__("stack").__getattr__("append");
      var1.getlocal(0).__setattr__("append", var3);
      var3 = null;
      var1.setline(853);
      var3 = var1.getlocal(0).__getattr__("read");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(854);
      var3 = var1.getlocal(0).__getattr__("dispatch");
      var1.setlocal(2, var3);
      var3 = null;

      try {
         while(true) {
            var1.setline(856);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(857);
            var3 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(858);
            var1.getlocal(2).__getitem__(var1.getlocal(3)).__call__(var2, var1.getlocal(0));
         }
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (var7.match(var1.getglobal("_Stop"))) {
            PyObject var4 = var7.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(860);
            var4 = var1.getlocal(4).__getattr__("value");
            var1.f_lasti = -1;
            return var4;
         }

         throw var7;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject marker$38(PyFrame var1, ThreadState var2) {
      var1.setline(871);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(872);
      var3 = var1.getlocal(0).__getattr__("mark");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(873);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1));
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(874);
         var3 = var1.getlocal(1).__getitem__(var1.getlocal(3));
         PyObject var10000 = var3._isnot(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(875);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(874);
         var3 = var1.getlocal(3)._sub(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
      }
   }

   public PyObject load_eof$39(PyFrame var1, ThreadState var2) {
      var1.setline(880);
      throw Py.makeException(var1.getglobal("EOFError"));
   }

   public PyObject load_proto$40(PyFrame var1, ThreadState var2) {
      var1.setline(884);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(885);
      PyInteger var5 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(1);
      PyInteger var10000 = var5;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._le(Py.newInteger(2));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(886);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unsupported pickle protocol: %d")._mod(var1.getlocal(1)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject load_persid$41(PyFrame var1, ThreadState var2) {
      var1.setline(890);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(891);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("persistent_load").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binpersid$42(PyFrame var1, ThreadState var2) {
      var1.setline(895);
      PyObject var3 = var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(896);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("persistent_load").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_none$43(PyFrame var1, ThreadState var2) {
      var1.setline(900);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_false$44(PyFrame var1, ThreadState var2) {
      var1.setline(904);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("False"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_true$45(PyFrame var1, ThreadState var2) {
      var1.setline(908);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("True"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_int$46(PyFrame var1, ThreadState var2) {
      var1.setline(912);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(913);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("FALSE").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(914);
         var3 = var1.getglobal("False");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(915);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("TRUE").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(916);
            var3 = var1.getglobal("True");
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            try {
               var1.setline(919);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
               var1.setlocal(2, var3);
               var3 = null;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (!var6.match(var1.getglobal("ValueError"))) {
                  throw var6;
               }

               var1.setline(921);
               PyObject var4 = var1.getglobal("long").__call__(var2, var1.getlocal(1));
               var1.setlocal(2, var4);
               var4 = null;
            }
         }
      }

      var1.setline(922);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binint$47(PyFrame var1, ThreadState var2) {
      var1.setline(926);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4)))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binint1$48(PyFrame var1, ThreadState var2) {
      var1.setline(930);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binint2$49(PyFrame var1, ThreadState var2) {
      var1.setline(934);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)))._add(PyString.fromInterned("\u0000\u0000"))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_long$50(PyFrame var1, ThreadState var2) {
      var1.setline(938);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("long").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), (PyObject)Py.newInteger(0)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_long1$51(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(943);
      var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(944);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("decode_long").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_long4$52(PyFrame var1, ThreadState var2) {
      var1.setline(948);
      PyObject var3 = var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(949);
      var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(950);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("decode_long").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_float$53(PyFrame var1, ThreadState var2) {
      var1.setline(954);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binfloat$54(PyFrame var1, ThreadState var2) {
      var1.setline(958);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">d"), (PyObject)var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(8))).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_string$55(PyFrame var1, ThreadState var2) {
      var1.setline(962);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(963);
      var3 = PyString.fromInterned("\"'").__iter__();

      do {
         var1.setline(963);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(970);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("insecure string pickle"));
         }

         var1.setlocal(2, var4);
         var1.setline(964);
      } while(!var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(2)).__nonzero__());

      var1.setline(965);
      if (var1.getlocal(1).__getattr__("endswith").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
         var1.setline(966);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("insecure string pickle"));
      } else {
         var1.setline(967);
         PyObject var5 = var1.getlocal(1).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2)), var1.getglobal("len").__call__(var2, var1.getlocal(2)).__neg__(), (PyObject)null);
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(971);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("string-escape")));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject load_binstring$56(PyFrame var1, ThreadState var2) {
      var1.setline(975);
      PyObject var3 = var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(976);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_unicode$57(PyFrame var1, ThreadState var2) {
      var1.setline(980);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), (PyObject)PyString.fromInterned("raw-unicode-escape")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binunicode$58(PyFrame var1, ThreadState var2) {
      var1.setline(984);
      PyObject var3 = var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(985);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("utf-8")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_short_binstring$59(PyFrame var1, ThreadState var2) {
      var1.setline(989);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(990);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_tuple$60(PyFrame var1, ThreadState var2) {
      var1.setline(994);
      PyObject var3 = var1.getlocal(0).__getattr__("marker").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(995);
      PyList var4 = new PyList(new PyObject[]{var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("stack").__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null))});
      var1.getlocal(0).__getattr__("stack").__setslice__(var1.getlocal(1), (PyObject)null, (PyObject)null, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_empty_tuple$61(PyFrame var1, ThreadState var2) {
      var1.setline(999);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_tuple1$62(PyFrame var1, ThreadState var2) {
      var1.setline(1003);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1))});
      var1.getlocal(0).__getattr__("stack").__setitem__((PyObject)Py.newInteger(-1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_tuple2$63(PyFrame var1, ThreadState var2) {
      var1.setline(1007);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-2)), var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1))})});
      var1.getlocal(0).__getattr__("stack").__setslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_tuple3$64(PyFrame var1, ThreadState var2) {
      var1.setline(1011);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-3)), var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-2)), var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1))})});
      var1.getlocal(0).__getattr__("stack").__setslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_empty_list$65(PyFrame var1, ThreadState var2) {
      var1.setline(1015);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_empty_dictionary$66(PyFrame var1, ThreadState var2) {
      var1.setline(1019);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_list$67(PyFrame var1, ThreadState var2) {
      var1.setline(1023);
      PyObject var3 = var1.getlocal(0).__getattr__("marker").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1024);
      PyList var4 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("stack").__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
      var1.getlocal(0).__getattr__("stack").__setslice__(var1.getlocal(1), (PyObject)null, (PyObject)null, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_dict$68(PyFrame var1, ThreadState var2) {
      var1.setline(1028);
      PyObject var3 = var1.getlocal(0).__getattr__("marker").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1029);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1030);
      var3 = var1.getlocal(0).__getattr__("stack").__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1031);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(1031);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1035);
            PyList var7 = new PyList(new PyObject[]{var1.getlocal(2)});
            var1.getlocal(0).__getattr__("stack").__setslice__(var1.getlocal(1), (PyObject)null, (PyObject)null, var7);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(1032);
         PyObject var5 = var1.getlocal(3).__getitem__(var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1033);
         var5 = var1.getlocal(3).__getitem__(var1.getlocal(4)._add(Py.newInteger(1)));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(1034);
         var5 = var1.getlocal(6);
         var1.getlocal(2).__setitem__(var1.getlocal(5), var5);
         var5 = null;
      }
   }

   public PyObject _instantiate$69(PyFrame var1, ThreadState var2) {
      var1.setline(1044);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("stack").__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1045);
      var1.getlocal(0).__getattr__("stack").__delslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
      var1.setline(1046);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(1047);
      PyObject var10000 = var1.getlocal(3).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var3._is(var1.getglobal("ClassType"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__getinitargs__")).__not__();
         }
      }

      PyException var9;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1051);
            var3 = var1.getglobal("_EmptyClass").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1052);
            var3 = var1.getlocal(1);
            var1.getlocal(5).__setattr__("__class__", var3);
            var3 = null;
            var1.setline(1053);
            var7 = Py.newInteger(1);
            var1.setlocal(4, var7);
            var3 = null;
         } catch (Throwable var6) {
            var9 = Py.setException(var6, var1);
            if (!var9.match(var1.getglobal("RuntimeError"))) {
               throw var9;
            }

            var1.setline(1057);
         }
      }

      var1.setline(1058);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         try {
            var1.setline(1060);
            var10000 = var1.getlocal(1);
            PyObject[] var10 = Py.EmptyObjects;
            String[] var8 = new String[0];
            var10000 = var10000._callextra(var10, var8, var1.getlocal(3), (PyObject)null);
            var3 = null;
            var3 = var10000;
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var5) {
            var9 = Py.setException(var5, var1);
            if (var9.match(var1.getglobal("TypeError"))) {
               PyObject var4 = var9.value;
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(1062);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("in constructor for %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("__name__"), var1.getglobal("str").__call__(var2, var1.getlocal(6))})), var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
            }

            throw var9;
         }
      }

      var1.setline(1064);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_inst$70(PyFrame var1, ThreadState var2) {
      var1.setline(1067);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1068);
      var3 = var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1069);
      var3 = var1.getlocal(0).__getattr__("find_class").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1070);
      var1.getlocal(0).__getattr__("_instantiate").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("marker").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_obj$71(PyFrame var1, ThreadState var2) {
      var1.setline(1075);
      PyObject var3 = var1.getlocal(0).__getattr__("marker").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1076);
      var3 = var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2, var1.getlocal(1)._add(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1077);
      var1.getlocal(0).__getattr__("_instantiate").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_newobj$72(PyFrame var1, ThreadState var2) {
      var1.setline(1081);
      PyObject var3 = var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1082);
      var3 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1083);
      PyObject var10000 = var1.getlocal(2).__getattr__("__new__");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1084);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__getattr__("stack").__setitem__((PyObject)Py.newInteger(-1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_global$73(PyFrame var1, ThreadState var2) {
      var1.setline(1088);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1089);
      var3 = var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1090);
      var3 = var1.getlocal(0).__getattr__("find_class").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1091);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_ext1$74(PyFrame var1, ThreadState var2) {
      var1.setline(1095);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1096);
      var1.getlocal(0).__getattr__("get_extension").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_ext2$75(PyFrame var1, ThreadState var2) {
      var1.setline(1100);
      PyObject var3 = var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2)))._add(PyString.fromInterned("\u0000\u0000")));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1101);
      var1.getlocal(0).__getattr__("get_extension").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_ext4$76(PyFrame var1, ThreadState var2) {
      var1.setline(1105);
      PyObject var3 = var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1106);
      var1.getlocal(0).__getattr__("get_extension").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_extension$77(PyFrame var1, ThreadState var2) {
      var1.setline(1110);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1111);
      PyObject var5 = var1.getglobal("_extension_cache").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1112);
      var5 = var1.getlocal(3);
      PyObject var10000 = var5._isnot(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1113);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(3));
         var1.setline(1114);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1115);
         var5 = var1.getglobal("_inverted_registry").__getattr__("get").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(1116);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(1117);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unregistered extension code %d")._mod(var1.getlocal(1))));
         } else {
            var1.setline(1118);
            var10000 = var1.getlocal(0).__getattr__("find_class");
            PyObject[] var6 = Py.EmptyObjects;
            String[] var4 = new String[0];
            var10000 = var10000._callextra(var6, var4, var1.getlocal(4), (PyObject)null);
            var3 = null;
            var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(1119);
            var5 = var1.getlocal(3);
            var1.getglobal("_extension_cache").__setitem__(var1.getlocal(1), var5);
            var3 = null;
            var1.setline(1120);
            var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject find_class$78(PyFrame var1, ThreadState var2) {
      var1.setline(1124);
      var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
      var1.setline(1125);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1126);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1127);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load_reduce$79(PyFrame var1, ThreadState var2) {
      var1.setline(1130);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1131);
      var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1132);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1133);
      PyObject var10000 = var1.getlocal(3);
      PyObject[] var5 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1134);
      var3 = var1.getlocal(4);
      var1.getlocal(1).__setitem__((PyObject)Py.newInteger(-1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_pop$80(PyFrame var1, ThreadState var2) {
      var1.setline(1138);
      var1.getlocal(0).__getattr__("stack").__delitem__((PyObject)Py.newInteger(-1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_pop_mark$81(PyFrame var1, ThreadState var2) {
      var1.setline(1142);
      PyObject var3 = var1.getlocal(0).__getattr__("marker").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1143);
      var1.getlocal(0).__getattr__("stack").__delslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_dup$82(PyFrame var1, ThreadState var2) {
      var1.setline(1147);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_get$83(PyFrame var1, ThreadState var2) {
      var1.setline(1151);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("memo").__getitem__(var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binget$84(PyFrame var1, ThreadState var2) {
      var1.setline(1155);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1156);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("memo").__getitem__(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_long_binget$85(PyFrame var1, ThreadState var2) {
      var1.setline(1160);
      PyObject var3 = var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1161);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("memo").__getitem__(var1.getglobal("repr").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_put$86(PyFrame var1, ThreadState var2) {
      var1.setline(1165);
      PyObject var3 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1));
      var1.getlocal(0).__getattr__("memo").__setitem__(var1.getlocal(0).__getattr__("readline").__call__(var2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_binput$87(PyFrame var1, ThreadState var2) {
      var1.setline(1169);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1170);
      var3 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1));
      var1.getlocal(0).__getattr__("memo").__setitem__(var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_long_binput$88(PyFrame var1, ThreadState var2) {
      var1.setline(1174);
      PyObject var3 = var1.getglobal("mloads").__call__(var2, PyString.fromInterned("i")._add(var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1175);
      var3 = var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1));
      var1.getlocal(0).__getattr__("memo").__setitem__(var1.getglobal("repr").__call__(var2, var1.getlocal(1)), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_append$89(PyFrame var1, ThreadState var2) {
      var1.setline(1179);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1180);
      var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1181);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1182);
      var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_appends$90(PyFrame var1, ThreadState var2) {
      var1.setline(1186);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1187);
      var3 = var1.getlocal(0).__getattr__("marker").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1188);
      var3 = var1.getlocal(1).__getitem__(var1.getlocal(2)._sub(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1189);
      var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
      var1.setline(1190);
      var1.getlocal(1).__delslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_setitem$91(PyFrame var1, ThreadState var2) {
      var1.setline(1194);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1195);
      var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1196);
      var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1197);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1198);
      var3 = var1.getlocal(2);
      var1.getlocal(4).__setitem__(var1.getlocal(3), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_setitems$92(PyFrame var1, ThreadState var2) {
      var1.setline(1202);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1203);
      var3 = var1.getlocal(0).__getattr__("marker").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1204);
      var3 = var1.getlocal(1).__getitem__(var1.getlocal(2)._sub(Py.newInteger(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1205);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(2)).__iter__();

      while(true) {
         var1.setline(1205);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1208);
            var1.getlocal(1).__delslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(1206);
         PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(4)._add(Py.newInteger(1)));
         var1.getlocal(3).__setitem__(var1.getlocal(1).__getitem__(var1.getlocal(4)), var5);
         var5 = null;
      }
   }

   public PyObject load_build$93(PyFrame var1, ThreadState var2) {
      var1.setline(1212);
      PyObject var3 = var1.getlocal(0).__getattr__("stack");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1213);
      var3 = var1.getlocal(1).__getattr__("pop").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1214);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1215);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("__setstate__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1216);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1217);
         var1.getlocal(4).__call__(var2, var1.getlocal(2));
         var1.setline(1218);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1219);
         var3 = var1.getglobal("None");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1220);
         PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple"));
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
         }

         PyObject var5;
         if (var10000.__nonzero__()) {
            var1.setline(1221);
            var3 = var1.getlocal(2);
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(1222);
         PyObject[] var6;
         PyObject var10;
         PyObject var11;
         PyObject[] var12;
         if (var1.getlocal(2).__nonzero__()) {
            PyException var13;
            try {
               var1.setline(1224);
               var3 = var1.getlocal(3).__getattr__("__dict__");
               var1.setlocal(6, var3);
               var3 = null;

               try {
                  var1.setline(1226);
                  var3 = var1.getlocal(2).__getattr__("iteritems").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(1226);
                     var10 = var3.__iternext__();
                     if (var10 == null) {
                        break;
                     }

                     var12 = Py.unpackSequence(var10, 2);
                     var11 = var12[0];
                     var1.setlocal(7, var11);
                     var6 = null;
                     var11 = var12[1];
                     var1.setlocal(8, var11);
                     var6 = null;
                     var1.setline(1227);
                     var5 = var1.getlocal(8);
                     var1.getlocal(6).__setitem__(var1.getglobal("intern").__call__(var2, var1.getlocal(7)), var5);
                     var5 = null;
                  }
               } catch (Throwable var8) {
                  var13 = Py.setException(var8, var1);
                  if (!var13.match(var1.getglobal("TypeError"))) {
                     throw var13;
                  }

                  var1.setline(1231);
                  var1.getlocal(6).__getattr__("update").__call__(var2, var1.getlocal(2));
               }
            } catch (Throwable var9) {
               var13 = Py.setException(var9, var1);
               if (!var13.match(var1.getglobal("RuntimeError"))) {
                  throw var13;
               }

               var1.setline(1243);
               var10 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(1243);
                  var5 = var10.__iternext__();
                  if (var5 == null) {
                     break;
                  }

                  var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(8, var7);
                  var7 = null;
                  var1.setline(1244);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(3), var1.getlocal(7), var1.getlocal(8));
               }
            }
         }

         var1.setline(1245);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(1246);
            var3 = var1.getlocal(5).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(1246);
               var10 = var3.__iternext__();
               if (var10 == null) {
                  break;
               }

               var12 = Py.unpackSequence(var10, 2);
               var11 = var12[0];
               var1.setlocal(7, var11);
               var6 = null;
               var11 = var12[1];
               var1.setlocal(8, var11);
               var6 = null;
               var1.setline(1247);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(3), var1.getlocal(7), var1.getlocal(8));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject load_mark$94(PyFrame var1, ThreadState var2) {
      var1.setline(1251);
      var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("mark"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_stop$95(PyFrame var1, ThreadState var2) {
      var1.setline(1255);
      PyObject var3 = var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1256);
      throw Py.makeException(var1.getglobal("_Stop").__call__(var2, var1.getlocal(1)));
   }

   public PyObject _EmptyClass$96(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1262);
      return var1.getf_locals();
   }

   public PyObject encode_long$97(PyFrame var1, ThreadState var2) {
      var1.setline(1288);
      PyString.fromInterned("Encode a long to a two's complement little-endian binary string.\n    Note that 0L is a special case, returning an empty string, to save a\n    byte in the LONG1 pickling context.\n\n    >>> encode_long(0L)\n    ''\n    >>> encode_long(255L)\n    '\\xff\\x00'\n    >>> encode_long(32767L)\n    '\\xff\\x7f'\n    >>> encode_long(-256L)\n    '\\x00\\xff'\n    >>> encode_long(-32768L)\n    '\\x00\\x80'\n    >>> encode_long(-128L)\n    '\\x80'\n    >>> encode_long(127L)\n    '\\x7f'\n    >>>\n    ");
      var1.setline(1290);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1291);
         PyString var5 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(1292);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._gt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1293);
            var4 = var1.getglobal("hex").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(1294);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0x")).__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }

            var1.setline(1295);
            var4 = Py.newInteger(2)._add(var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L")));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1296);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1297);
            if (var1.getlocal(3)._and(Py.newInteger(1)).__nonzero__()) {
               var1.setline(1299);
               var4 = PyString.fromInterned("0x0")._add(var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
               var1.setlocal(1, var4);
               var4 = null;
            } else {
               var1.setline(1300);
               var4 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(2)), (PyObject)Py.newInteger(16));
               var10000 = var4._ge(Py.newInteger(8));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1302);
                  var4 = PyString.fromInterned("0x00")._add(var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
                  var1.setlocal(1, var4);
                  var4 = null;
               }
            }
         } else {
            var1.setline(1307);
            var4 = var1.getglobal("hex").__call__(var2, var1.getlocal(0).__neg__());
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(1308);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0x")).__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }

            var1.setline(1309);
            var4 = Py.newInteger(2)._add(var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L")));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1310);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1311);
            if (var1.getlocal(3)._and(Py.newInteger(1)).__nonzero__()) {
               var1.setline(1313);
               var4 = var1.getlocal(3);
               var4 = var4._iadd(Py.newInteger(1));
               var1.setlocal(3, var4);
            }

            var1.setline(1314);
            var4 = var1.getlocal(3)._mul(Py.newInteger(4));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(1315);
            var4 = var1.getlocal(0);
            var4 = var4._iadd(Py.newLong("1")._lshift(var1.getlocal(4)));
            var1.setlocal(0, var4);
            var1.setline(1316);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var4 = var1.getlocal(0);
               var10000 = var4._gt(Py.newInteger(0));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(1317);
            var4 = var1.getglobal("hex").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(1318);
            var4 = Py.newInteger(2)._add(var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L")));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1319);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(var1.getlocal(2));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(1320);
            var4 = var1.getlocal(5);
            var10000 = var4._lt(var1.getlocal(3));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1321);
               var4 = PyString.fromInterned("0x")._add(PyString.fromInterned("0")._mul(var1.getlocal(3)._sub(var1.getlocal(5))))._add(var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
               var1.setlocal(1, var4);
               var4 = null;
            }

            var1.setline(1322);
            var4 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(2)), (PyObject)Py.newInteger(16));
            var10000 = var4._lt(Py.newInteger(8));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1324);
               var4 = PyString.fromInterned("0xff")._add(var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
               var1.setlocal(1, var4);
               var4 = null;
            }
         }

         var1.setline(1326);
         if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L")).__nonzero__()) {
            var1.setline(1327);
            var4 = var1.getlocal(1).__getslice__(Py.newInteger(2), Py.newInteger(-1), (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
         } else {
            var1.setline(1329);
            var4 = var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1330);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._and(Py.newInteger(1));
            var10000 = var4._eq(Py.newInteger(0));
            var4 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}));
            }
         }

         var1.setline(1331);
         var4 = var1.getglobal("_binascii").__getattr__("unhexlify").__call__(var2, var1.getlocal(1));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(1332);
         var3 = var1.getlocal(6).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject decode_long$98(PyFrame var1, ThreadState var2) {
      var1.setline(1351);
      PyString.fromInterned("Decode a long from a two's complement little-endian binary string.\n\n    >>> decode_long('')\n    0L\n    >>> decode_long(\"\\xff\\x00\")\n    255L\n    >>> decode_long(\"\\xff\\x7f\")\n    32767L\n    >>> decode_long(\"\\x00\\xff\")\n    -256L\n    >>> decode_long(\"\\x00\\x80\")\n    -32768L\n    >>> decode_long(\"\\x80\")\n    -128L\n    >>> decode_long(\"\\x7f\")\n    127L\n    ");
      var1.setline(1353);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1354);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1355);
         PyLong var5 = Py.newLong("0");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(1356);
         PyObject var4 = var1.getglobal("_binascii").__getattr__("hexlify").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1)));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1357);
         var4 = var1.getglobal("long").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(16));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1358);
         var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
         var10000 = var4._ge(PyString.fromInterned("\u0080"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1359);
            var4 = var1.getlocal(3);
            var4 = var4._isub(Py.newLong("1")._lshift(var1.getlocal(1)._mul(Py.newInteger(8))));
            var1.setlocal(3, var4);
         }

         var1.setline(1360);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject dump$99(PyFrame var1, ThreadState var2) {
      var1.setline(1370);
      var1.getglobal("Pickler").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__getattr__("dump").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dumps$100(PyFrame var1, ThreadState var2) {
      var1.setline(1373);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1374);
      var1.getglobal("Pickler").__call__(var2, var1.getlocal(2), var1.getlocal(1)).__getattr__("dump").__call__(var2, var1.getlocal(0));
      var1.setline(1375);
      var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject load$101(PyFrame var1, ThreadState var2) {
      var1.setline(1378);
      PyObject var3 = var1.getglobal("Unpickler").__call__(var2, var1.getlocal(0)).__getattr__("load").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject loads$102(PyFrame var1, ThreadState var2) {
      var1.setline(1381);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1382);
      var3 = var1.getglobal("Unpickler").__call__(var2, var1.getlocal(1)).__getattr__("load").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _test$103(PyFrame var1, ThreadState var2) {
      var1.setline(1387);
      PyObject var3 = imp.importOne("doctest", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1388);
      var3 = var1.getlocal(0).__getattr__("testmod").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public pickle$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PickleError$1 = Py.newCode(0, var2, var1, "PickleError", 58, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PicklingError$2 = Py.newCode(0, var2, var1, "PicklingError", 62, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnpicklingError$3 = Py.newCode(0, var2, var1, "UnpicklingError", 69, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _Stop$4 = Py.newCode(0, var2, var1, "_Stop", 82, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "value"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 83, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Pickler$6 = Py.newCode(0, var2, var1, "Pickler", 171, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "protocol"};
      __init__$7 = Py.newCode(3, var2, var1, "__init__", 173, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear_memo$8 = Py.newCode(1, var2, var1, "clear_memo", 209, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj"};
      dump$9 = Py.newCode(2, var2, var1, "dump", 220, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "memo_len"};
      memoize$10 = Py.newCode(2, var2, var1, "memoize", 227, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "pack"};
      put$11 = Py.newCode(3, var2, var1, "put", 250, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "pack"};
      get$12 = Py.newCode(3, var2, var1, "get", 260, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "pid", "x", "t", "f", "reduce", "rv", "issc", "l"};
      save$13 = Py.newCode(2, var2, var1, "save", 269, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj"};
      persistent_id$14 = Py.newCode(2, var2, var1, "persistent_id", 333, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pid"};
      save_pers$15 = Py.newCode(2, var2, var1, "save_pers", 337, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "args", "state", "listitems", "dictitems", "obj", "save", "write", "cls"};
      save_reduce$16 = Py.newCode(7, var2, var1, "save_reduce", 345, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj"};
      save_none$17 = Py.newCode(2, var2, var1, "save_none", 426, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj"};
      save_bool$18 = Py.newCode(2, var2, var1, "save_bool", 430, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "pack", "high_bits"};
      save_int$19 = Py.newCode(3, var2, var1, "save_int", 437, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "pack", "bytes", "n"};
      save_long$20 = Py.newCode(3, var2, var1, "save_long", 461, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "pack"};
      save_float$21 = Py.newCode(3, var2, var1, "save_float", 473, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "pack", "n"};
      save_string$22 = Py.newCode(3, var2, var1, "save_string", 480, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "pack", "encoding", "n"};
      save_unicode$23 = Py.newCode(3, var2, var1, "save_unicode", 492, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "pack", "unicode", "l", "s"};
      save_string$24 = Py.newCode(3, var2, var1, "save_string", 506, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "write", "proto", "n", "save", "memo", "element", "get"};
      save_tuple$25 = Py.newCode(2, var2, var1, "save_tuple", 532, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj"};
      save_empty_tuple$26 = Py.newCode(2, var2, var1, "save_empty_tuple", 588, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "write"};
      save_list$27 = Py.newCode(2, var2, var1, "save_list", 591, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items", "save", "write", "x", "r", "tmp", "i", "n"};
      _batch_appends$28 = Py.newCode(2, var2, var1, "_batch_appends", 608, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "write"};
      save_dict$29 = Py.newCode(2, var2, var1, "save_dict", 640, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items", "save", "write", "k", "v", "r", "tmp", "i", "n"};
      _batch_setitems$30 = Py.newCode(2, var2, var1, "_batch_setitems", 655, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "cls", "memo", "write", "save", "args", "arg", "getstate", "stuff"};
      save_inst$31 = Py.newCode(2, var2, var1, "save_inst", 690, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "name", "pack", "write", "memo", "module", "mod", "klass", "code"};
      save_global$32 = Py.newCode(4, var2, var1, "save_global", 730, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "memo"};
      _keep_alive$33 = Py.newCode(2, var2, var1, "_keep_alive", 777, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "funcname", "mod", "name", "module"};
      whichmodule$34 = Py.newCode(2, var2, var1, "whichmodule", 799, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Unpickler$35 = Py.newCode(0, var2, var1, "Unpickler", 827, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file"};
      __init__$36 = Py.newCode(2, var2, var1, "__init__", 829, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "read", "dispatch", "key", "stopinst"};
      load$37 = Py.newCode(1, var2, var1, "load", 845, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stack", "mark", "k"};
      marker$38 = Py.newCode(1, var2, var1, "marker", 870, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_eof$39 = Py.newCode(1, var2, var1, "load_eof", 879, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "proto"};
      load_proto$40 = Py.newCode(1, var2, var1, "load_proto", 883, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pid"};
      load_persid$41 = Py.newCode(1, var2, var1, "load_persid", 889, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pid"};
      load_binpersid$42 = Py.newCode(1, var2, var1, "load_binpersid", 894, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_none$43 = Py.newCode(1, var2, var1, "load_none", 899, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_false$44 = Py.newCode(1, var2, var1, "load_false", 903, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_true$45 = Py.newCode(1, var2, var1, "load_true", 907, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "val"};
      load_int$46 = Py.newCode(1, var2, var1, "load_int", 911, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_binint$47 = Py.newCode(1, var2, var1, "load_binint", 925, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_binint1$48 = Py.newCode(1, var2, var1, "load_binint1", 929, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_binint2$49 = Py.newCode(1, var2, var1, "load_binint2", 933, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_long$50 = Py.newCode(1, var2, var1, "load_long", 937, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "bytes"};
      load_long1$51 = Py.newCode(1, var2, var1, "load_long1", 941, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "bytes"};
      load_long4$52 = Py.newCode(1, var2, var1, "load_long4", 947, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_float$53 = Py.newCode(1, var2, var1, "load_float", 953, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unpack"};
      load_binfloat$54 = Py.newCode(2, var2, var1, "load_binfloat", 957, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rep", "q"};
      load_string$55 = Py.newCode(1, var2, var1, "load_string", 961, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len"};
      load_binstring$56 = Py.newCode(1, var2, var1, "load_binstring", 974, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_unicode$57 = Py.newCode(1, var2, var1, "load_unicode", 979, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len"};
      load_binunicode$58 = Py.newCode(1, var2, var1, "load_binunicode", 983, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "len"};
      load_short_binstring$59 = Py.newCode(1, var2, var1, "load_short_binstring", 988, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k"};
      load_tuple$60 = Py.newCode(1, var2, var1, "load_tuple", 993, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_empty_tuple$61 = Py.newCode(1, var2, var1, "load_empty_tuple", 998, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_tuple1$62 = Py.newCode(1, var2, var1, "load_tuple1", 1002, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_tuple2$63 = Py.newCode(1, var2, var1, "load_tuple2", 1006, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_tuple3$64 = Py.newCode(1, var2, var1, "load_tuple3", 1010, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_empty_list$65 = Py.newCode(1, var2, var1, "load_empty_list", 1014, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_empty_dictionary$66 = Py.newCode(1, var2, var1, "load_empty_dictionary", 1018, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k"};
      load_list$67 = Py.newCode(1, var2, var1, "load_list", 1022, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k", "d", "items", "i", "key", "value"};
      load_dict$68 = Py.newCode(1, var2, var1, "load_dict", 1027, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "klass", "k", "args", "instantiated", "value", "err"};
      _instantiate$69 = Py.newCode(3, var2, var1, "_instantiate", 1043, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "name", "klass"};
      load_inst$70 = Py.newCode(1, var2, var1, "load_inst", 1066, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k", "klass"};
      load_obj$71 = Py.newCode(1, var2, var1, "load_obj", 1073, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "cls", "obj"};
      load_newobj$72 = Py.newCode(1, var2, var1, "load_newobj", 1080, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "name", "klass"};
      load_global$73 = Py.newCode(1, var2, var1, "load_global", 1087, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code"};
      load_ext1$74 = Py.newCode(1, var2, var1, "load_ext1", 1094, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code"};
      load_ext2$75 = Py.newCode(1, var2, var1, "load_ext2", 1099, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code"};
      load_ext4$76 = Py.newCode(1, var2, var1, "load_ext4", 1104, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "code", "nil", "obj", "key"};
      get_extension$77 = Py.newCode(2, var2, var1, "get_extension", 1109, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "name", "mod", "klass"};
      find_class$78 = Py.newCode(3, var2, var1, "find_class", 1122, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stack", "args", "func", "value"};
      load_reduce$79 = Py.newCode(1, var2, var1, "load_reduce", 1129, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_pop$80 = Py.newCode(1, var2, var1, "load_pop", 1137, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "k"};
      load_pop_mark$81 = Py.newCode(1, var2, var1, "load_pop_mark", 1141, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_dup$82 = Py.newCode(1, var2, var1, "load_dup", 1146, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_get$83 = Py.newCode(1, var2, var1, "load_get", 1150, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      load_binget$84 = Py.newCode(1, var2, var1, "load_binget", 1154, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      load_long_binget$85 = Py.newCode(1, var2, var1, "load_long_binget", 1159, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_put$86 = Py.newCode(1, var2, var1, "load_put", 1164, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      load_binput$87 = Py.newCode(1, var2, var1, "load_binput", 1168, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      load_long_binput$88 = Py.newCode(1, var2, var1, "load_long_binput", 1173, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stack", "value", "list"};
      load_append$89 = Py.newCode(1, var2, var1, "load_append", 1178, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stack", "mark", "list"};
      load_appends$90 = Py.newCode(1, var2, var1, "load_appends", 1185, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stack", "value", "key", "dict"};
      load_setitem$91 = Py.newCode(1, var2, var1, "load_setitem", 1193, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stack", "mark", "dict", "i"};
      load_setitems$92 = Py.newCode(1, var2, var1, "load_setitems", 1201, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stack", "state", "inst", "setstate", "slotstate", "d", "k", "v"};
      load_build$93 = Py.newCode(1, var2, var1, "load_build", 1211, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      load_mark$94 = Py.newCode(1, var2, var1, "load_mark", 1250, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      load_stop$95 = Py.newCode(1, var2, var1, "load_stop", 1254, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _EmptyClass$96 = Py.newCode(0, var2, var1, "_EmptyClass", 1261, false, false, self, 96, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x", "ashex", "njunkchars", "nibbles", "nbits", "newnibbles", "binary"};
      encode_long$97 = Py.newCode(1, var2, var1, "encode_long", 1268, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data", "nbytes", "ashex", "n"};
      decode_long$98 = Py.newCode(1, var2, var1, "decode_long", 1334, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "file", "protocol"};
      dump$99 = Py.newCode(3, var2, var1, "dump", 1369, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "protocol", "file"};
      dumps$100 = Py.newCode(2, var2, var1, "dumps", 1372, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file"};
      load$101 = Py.newCode(1, var2, var1, "load", 1377, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str", "file"};
      loads$102 = Py.newCode(1, var2, var1, "loads", 1380, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"doctest"};
      _test$103 = Py.newCode(0, var2, var1, "_test", 1386, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pickle$py("pickle$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pickle$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PickleError$1(var2, var3);
         case 2:
            return this.PicklingError$2(var2, var3);
         case 3:
            return this.UnpicklingError$3(var2, var3);
         case 4:
            return this._Stop$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.Pickler$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.clear_memo$8(var2, var3);
         case 9:
            return this.dump$9(var2, var3);
         case 10:
            return this.memoize$10(var2, var3);
         case 11:
            return this.put$11(var2, var3);
         case 12:
            return this.get$12(var2, var3);
         case 13:
            return this.save$13(var2, var3);
         case 14:
            return this.persistent_id$14(var2, var3);
         case 15:
            return this.save_pers$15(var2, var3);
         case 16:
            return this.save_reduce$16(var2, var3);
         case 17:
            return this.save_none$17(var2, var3);
         case 18:
            return this.save_bool$18(var2, var3);
         case 19:
            return this.save_int$19(var2, var3);
         case 20:
            return this.save_long$20(var2, var3);
         case 21:
            return this.save_float$21(var2, var3);
         case 22:
            return this.save_string$22(var2, var3);
         case 23:
            return this.save_unicode$23(var2, var3);
         case 24:
            return this.save_string$24(var2, var3);
         case 25:
            return this.save_tuple$25(var2, var3);
         case 26:
            return this.save_empty_tuple$26(var2, var3);
         case 27:
            return this.save_list$27(var2, var3);
         case 28:
            return this._batch_appends$28(var2, var3);
         case 29:
            return this.save_dict$29(var2, var3);
         case 30:
            return this._batch_setitems$30(var2, var3);
         case 31:
            return this.save_inst$31(var2, var3);
         case 32:
            return this.save_global$32(var2, var3);
         case 33:
            return this._keep_alive$33(var2, var3);
         case 34:
            return this.whichmodule$34(var2, var3);
         case 35:
            return this.Unpickler$35(var2, var3);
         case 36:
            return this.__init__$36(var2, var3);
         case 37:
            return this.load$37(var2, var3);
         case 38:
            return this.marker$38(var2, var3);
         case 39:
            return this.load_eof$39(var2, var3);
         case 40:
            return this.load_proto$40(var2, var3);
         case 41:
            return this.load_persid$41(var2, var3);
         case 42:
            return this.load_binpersid$42(var2, var3);
         case 43:
            return this.load_none$43(var2, var3);
         case 44:
            return this.load_false$44(var2, var3);
         case 45:
            return this.load_true$45(var2, var3);
         case 46:
            return this.load_int$46(var2, var3);
         case 47:
            return this.load_binint$47(var2, var3);
         case 48:
            return this.load_binint1$48(var2, var3);
         case 49:
            return this.load_binint2$49(var2, var3);
         case 50:
            return this.load_long$50(var2, var3);
         case 51:
            return this.load_long1$51(var2, var3);
         case 52:
            return this.load_long4$52(var2, var3);
         case 53:
            return this.load_float$53(var2, var3);
         case 54:
            return this.load_binfloat$54(var2, var3);
         case 55:
            return this.load_string$55(var2, var3);
         case 56:
            return this.load_binstring$56(var2, var3);
         case 57:
            return this.load_unicode$57(var2, var3);
         case 58:
            return this.load_binunicode$58(var2, var3);
         case 59:
            return this.load_short_binstring$59(var2, var3);
         case 60:
            return this.load_tuple$60(var2, var3);
         case 61:
            return this.load_empty_tuple$61(var2, var3);
         case 62:
            return this.load_tuple1$62(var2, var3);
         case 63:
            return this.load_tuple2$63(var2, var3);
         case 64:
            return this.load_tuple3$64(var2, var3);
         case 65:
            return this.load_empty_list$65(var2, var3);
         case 66:
            return this.load_empty_dictionary$66(var2, var3);
         case 67:
            return this.load_list$67(var2, var3);
         case 68:
            return this.load_dict$68(var2, var3);
         case 69:
            return this._instantiate$69(var2, var3);
         case 70:
            return this.load_inst$70(var2, var3);
         case 71:
            return this.load_obj$71(var2, var3);
         case 72:
            return this.load_newobj$72(var2, var3);
         case 73:
            return this.load_global$73(var2, var3);
         case 74:
            return this.load_ext1$74(var2, var3);
         case 75:
            return this.load_ext2$75(var2, var3);
         case 76:
            return this.load_ext4$76(var2, var3);
         case 77:
            return this.get_extension$77(var2, var3);
         case 78:
            return this.find_class$78(var2, var3);
         case 79:
            return this.load_reduce$79(var2, var3);
         case 80:
            return this.load_pop$80(var2, var3);
         case 81:
            return this.load_pop_mark$81(var2, var3);
         case 82:
            return this.load_dup$82(var2, var3);
         case 83:
            return this.load_get$83(var2, var3);
         case 84:
            return this.load_binget$84(var2, var3);
         case 85:
            return this.load_long_binget$85(var2, var3);
         case 86:
            return this.load_put$86(var2, var3);
         case 87:
            return this.load_binput$87(var2, var3);
         case 88:
            return this.load_long_binput$88(var2, var3);
         case 89:
            return this.load_append$89(var2, var3);
         case 90:
            return this.load_appends$90(var2, var3);
         case 91:
            return this.load_setitem$91(var2, var3);
         case 92:
            return this.load_setitems$92(var2, var3);
         case 93:
            return this.load_build$93(var2, var3);
         case 94:
            return this.load_mark$94(var2, var3);
         case 95:
            return this.load_stop$95(var2, var3);
         case 96:
            return this._EmptyClass$96(var2, var3);
         case 97:
            return this.encode_long$97(var2, var3);
         case 98:
            return this.decode_long$98(var2, var3);
         case 99:
            return this.dump$99(var2, var3);
         case 100:
            return this.dumps$100(var2, var3);
         case 101:
            return this.load$101(var2, var3);
         case 102:
            return this.loads$102(var2, var3);
         case 103:
            return this._test$103(var2, var3);
         default:
            return null;
      }
   }
}
