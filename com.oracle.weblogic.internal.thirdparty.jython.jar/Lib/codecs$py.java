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
@Filename("codecs.py")
public class codecs$py extends PyFunctionTable implements PyRunnable {
   static codecs$py self;
   static final PyCode f$0;
   static final PyCode CodecInfo$1;
   static final PyCode __new__$2;
   static final PyCode __repr__$3;
   static final PyCode Codec$4;
   static final PyCode encode$5;
   static final PyCode decode$6;
   static final PyCode IncrementalEncoder$7;
   static final PyCode __init__$8;
   static final PyCode encode$9;
   static final PyCode reset$10;
   static final PyCode getstate$11;
   static final PyCode setstate$12;
   static final PyCode BufferedIncrementalEncoder$13;
   static final PyCode __init__$14;
   static final PyCode _buffer_encode$15;
   static final PyCode encode$16;
   static final PyCode reset$17;
   static final PyCode getstate$18;
   static final PyCode setstate$19;
   static final PyCode IncrementalDecoder$20;
   static final PyCode __init__$21;
   static final PyCode decode$22;
   static final PyCode reset$23;
   static final PyCode getstate$24;
   static final PyCode setstate$25;
   static final PyCode BufferedIncrementalDecoder$26;
   static final PyCode __init__$27;
   static final PyCode _buffer_decode$28;
   static final PyCode decode$29;
   static final PyCode reset$30;
   static final PyCode getstate$31;
   static final PyCode setstate$32;
   static final PyCode StreamWriter$33;
   static final PyCode __init__$34;
   static final PyCode write$35;
   static final PyCode writelines$36;
   static final PyCode reset$37;
   static final PyCode seek$38;
   static final PyCode __getattr__$39;
   static final PyCode __enter__$40;
   static final PyCode __exit__$41;
   static final PyCode StreamReader$42;
   static final PyCode __init__$43;
   static final PyCode decode$44;
   static final PyCode read$45;
   static final PyCode readline$46;
   static final PyCode readlines$47;
   static final PyCode reset$48;
   static final PyCode seek$49;
   static final PyCode next$50;
   static final PyCode __iter__$51;
   static final PyCode __getattr__$52;
   static final PyCode __enter__$53;
   static final PyCode __exit__$54;
   static final PyCode StreamReaderWriter$55;
   static final PyCode __init__$56;
   static final PyCode read$57;
   static final PyCode readline$58;
   static final PyCode readlines$59;
   static final PyCode next$60;
   static final PyCode __iter__$61;
   static final PyCode write$62;
   static final PyCode writelines$63;
   static final PyCode reset$64;
   static final PyCode seek$65;
   static final PyCode __getattr__$66;
   static final PyCode __enter__$67;
   static final PyCode __exit__$68;
   static final PyCode StreamRecoder$69;
   static final PyCode __init__$70;
   static final PyCode read$71;
   static final PyCode readline$72;
   static final PyCode readlines$73;
   static final PyCode next$74;
   static final PyCode __iter__$75;
   static final PyCode write$76;
   static final PyCode writelines$77;
   static final PyCode reset$78;
   static final PyCode __getattr__$79;
   static final PyCode __enter__$80;
   static final PyCode __exit__$81;
   static final PyCode open$82;
   static final PyCode EncodedFile$83;
   static final PyCode getencoder$84;
   static final PyCode getdecoder$85;
   static final PyCode getincrementalencoder$86;
   static final PyCode getincrementaldecoder$87;
   static final PyCode getreader$88;
   static final PyCode getwriter$89;
   static final PyCode iterencode$90;
   static final PyCode iterdecode$91;
   static final PyCode make_identity_dict$92;
   static final PyCode make_encoding_map$93;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" codecs -- Python Codec Registry, API and helpers.\n\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n"));
      var1.setline(8);
      PyString.fromInterned(" codecs -- Python Codec Registry, API and helpers.\n\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n\n");
      var1.setline(10);
      PyObject var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;

      PyObject var4;
      PyException var7;
      try {
         var1.setline(15);
         imp.importAll("_codecs", var1, -1);
      } catch (Throwable var5) {
         var7 = Py.setException(var5, var1);
         if (var7.match(var1.getname("ImportError"))) {
            var4 = var7.value;
            var1.setlocal("why", var4);
            var4 = null;
            var1.setline(17);
            throw Py.makeException(var1.getname("SystemError").__call__(var2, PyString.fromInterned("Failed to load the builtin codecs: %s")._mod(var1.getname("why"))));
         }

         throw var7;
      }

      var1.setline(19);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("register"), PyString.fromInterned("lookup"), PyString.fromInterned("open"), PyString.fromInterned("EncodedFile"), PyString.fromInterned("BOM"), PyString.fromInterned("BOM_BE"), PyString.fromInterned("BOM_LE"), PyString.fromInterned("BOM32_BE"), PyString.fromInterned("BOM32_LE"), PyString.fromInterned("BOM64_BE"), PyString.fromInterned("BOM64_LE"), PyString.fromInterned("BOM_UTF8"), PyString.fromInterned("BOM_UTF16"), PyString.fromInterned("BOM_UTF16_LE"), PyString.fromInterned("BOM_UTF16_BE"), PyString.fromInterned("BOM_UTF32"), PyString.fromInterned("BOM_UTF32_LE"), PyString.fromInterned("BOM_UTF32_BE"), PyString.fromInterned("strict_errors"), PyString.fromInterned("ignore_errors"), PyString.fromInterned("replace_errors"), PyString.fromInterned("xmlcharrefreplace_errors"), PyString.fromInterned("register_error"), PyString.fromInterned("lookup_error")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(36);
      PyString var9 = PyString.fromInterned("ï»¿");
      var1.setlocal("BOM_UTF8", var9);
      var3 = null;
      var1.setline(39);
      var9 = PyString.fromInterned("ÿþ");
      var1.setlocal("BOM_LE", var9);
      var1.setlocal("BOM_UTF16_LE", var9);
      var1.setline(42);
      var9 = PyString.fromInterned("þÿ");
      var1.setlocal("BOM_BE", var9);
      var1.setlocal("BOM_UTF16_BE", var9);
      var1.setline(45);
      var9 = PyString.fromInterned("ÿþ\u0000\u0000");
      var1.setlocal("BOM_UTF32_LE", var9);
      var3 = null;
      var1.setline(48);
      var9 = PyString.fromInterned("\u0000\u0000þÿ");
      var1.setlocal("BOM_UTF32_BE", var9);
      var3 = null;
      var1.setline(50);
      var3 = var1.getname("sys").__getattr__("byteorder");
      PyObject var10000 = var3._eq(PyString.fromInterned("little"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(53);
         var3 = var1.getname("BOM_UTF16_LE");
         var1.setlocal("BOM", var3);
         var1.setlocal("BOM_UTF16", var3);
         var1.setline(56);
         var3 = var1.getname("BOM_UTF32_LE");
         var1.setlocal("BOM_UTF32", var3);
         var3 = null;
      } else {
         var1.setline(61);
         var3 = var1.getname("BOM_UTF16_BE");
         var1.setlocal("BOM", var3);
         var1.setlocal("BOM_UTF16", var3);
         var1.setline(64);
         var3 = var1.getname("BOM_UTF32_BE");
         var1.setlocal("BOM_UTF32", var3);
         var3 = null;
      }

      var1.setline(67);
      var3 = var1.getname("BOM_UTF16_LE");
      var1.setlocal("BOM32_LE", var3);
      var3 = null;
      var1.setline(68);
      var3 = var1.getname("BOM_UTF16_BE");
      var1.setlocal("BOM32_BE", var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getname("BOM_UTF32_LE");
      var1.setlocal("BOM64_LE", var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getname("BOM_UTF32_BE");
      var1.setlocal("BOM64_BE", var3);
      var3 = null;
      var1.setline(75);
      PyObject[] var10 = new PyObject[]{var1.getname("tuple")};
      var4 = Py.makeClass("CodecInfo", var10, CodecInfo$1);
      var1.setlocal("CodecInfo", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(92);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Codec", var10, Codec$4);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(156);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("IncrementalEncoder", var10, IncrementalEncoder$7);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(196);
      var10 = new PyObject[]{var1.getname("IncrementalEncoder")};
      var4 = Py.makeClass("BufferedIncrementalEncoder", var10, BufferedIncrementalEncoder$13);
      var1.setlocal("BufferedIncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(229);
      var10 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("IncrementalDecoder", var10, IncrementalDecoder$20);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(278);
      var10 = new PyObject[]{var1.getname("IncrementalDecoder")};
      var4 = Py.makeClass("BufferedIncrementalDecoder", var10, BufferedIncrementalDecoder$26);
      var1.setlocal("BufferedIncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(320);
      var10 = new PyObject[]{var1.getname("Codec")};
      var4 = Py.makeClass("StreamWriter", var10, StreamWriter$33);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(393);
      var10 = new PyObject[]{var1.getname("Codec")};
      var4 = Py.makeClass("StreamReader", var10, StreamReader$42);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(638);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("StreamReaderWriter", var10, StreamReaderWriter$55);
      var1.setlocal("StreamReaderWriter", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(725);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("StreamRecoder", var10, StreamRecoder$69);
      var1.setlocal("StreamRecoder", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(841);
      var10 = new PyObject[]{PyString.fromInterned("rb"), var1.getname("None"), PyString.fromInterned("strict"), Py.newInteger(1)};
      PyFunction var11 = new PyFunction(var1.f_globals, var10, open$82, PyString.fromInterned(" Open an encoded file using the given mode and return\n        a wrapped version providing transparent encoding/decoding.\n\n        Note: The wrapped version will only accept the object format\n        defined by the codecs, i.e. Unicode objects for most builtin\n        codecs. Output is also codec dependent and will usually be\n        Unicode as well.\n\n        Files are always opened in binary mode, even if no binary mode\n        was specified. This is done to avoid data loss due to encodings\n        using 8-bit values. The default file mode is 'rb' meaning to\n        open the file in binary read mode.\n\n        encoding specifies the encoding which is to be used for the\n        file.\n\n        errors may be given to define the error handling. It defaults\n        to 'strict' which causes ValueErrors to be raised in case an\n        encoding error occurs.\n\n        buffering has the same meaning as for the builtin open() API.\n        It defaults to line buffered.\n\n        The returned wrapped file object provides an extra attribute\n        .encoding which allows querying the used encoding. This\n        attribute is only available if an encoding was specified as\n        parameter.\n\n    "));
      var1.setlocal("open", var11);
      var3 = null;
      var1.setline(890);
      var10 = new PyObject[]{var1.getname("None"), PyString.fromInterned("strict")};
      var11 = new PyFunction(var1.f_globals, var10, EncodedFile$83, PyString.fromInterned(" Return a wrapped version of file which provides transparent\n        encoding translation.\n\n        Strings written to the wrapped file are interpreted according\n        to the given data_encoding and then written to the original\n        file as string using file_encoding. The intermediate encoding\n        will usually be Unicode but depends on the specified codecs.\n\n        Strings are read from the file using file_encoding and then\n        passed back to the caller as string using data_encoding.\n\n        If file_encoding is not given, it defaults to data_encoding.\n\n        errors may be given to define the error handling. It defaults\n        to 'strict' which causes ValueErrors to be raised in case an\n        encoding error occurs.\n\n        The returned wrapped file object provides two extra attributes\n        .data_encoding and .file_encoding which reflect the given\n        parameters of the same name. The attributes can be used for\n        introspection by Python programs.\n\n    "));
      var1.setlocal("EncodedFile", var11);
      var3 = null;
      var1.setline(928);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, getencoder$84, PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its encoder function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    "));
      var1.setlocal("getencoder", var11);
      var3 = null;
      var1.setline(938);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, getdecoder$85, PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its decoder function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    "));
      var1.setlocal("getdecoder", var11);
      var3 = null;
      var1.setline(948);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, getincrementalencoder$86, PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its IncrementalEncoder class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found\n        or the codecs doesn't provide an incremental encoder.\n\n    "));
      var1.setlocal("getincrementalencoder", var11);
      var3 = null;
      var1.setline(962);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, getincrementaldecoder$87, PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its IncrementalDecoder class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found\n        or the codecs doesn't provide an incremental decoder.\n\n    "));
      var1.setlocal("getincrementaldecoder", var11);
      var3 = null;
      var1.setline(976);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, getreader$88, PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its StreamReader class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    "));
      var1.setlocal("getreader", var11);
      var3 = null;
      var1.setline(986);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, getwriter$89, PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its StreamWriter class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    "));
      var1.setlocal("getwriter", var11);
      var3 = null;
      var1.setline(996);
      var10 = new PyObject[]{PyString.fromInterned("strict")};
      var11 = new PyFunction(var1.f_globals, var10, iterencode$90, PyString.fromInterned("\n    Encoding iterator.\n\n    Encodes the input strings from the iterator using a IncrementalEncoder.\n\n    errors and kwargs are passed through to the IncrementalEncoder\n    constructor.\n    "));
      var1.setlocal("iterencode", var11);
      var3 = null;
      var1.setline(1014);
      var10 = new PyObject[]{PyString.fromInterned("strict")};
      var11 = new PyFunction(var1.f_globals, var10, iterdecode$91, PyString.fromInterned("\n    Decoding iterator.\n\n    Decodes the input strings from the iterator using a IncrementalDecoder.\n\n    errors and kwargs are passed through to the IncrementalDecoder\n    constructor.\n    "));
      var1.setlocal("iterdecode", var11);
      var3 = null;
      var1.setline(1034);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, make_identity_dict$92, PyString.fromInterned(" make_identity_dict(rng) -> dict\n\n        Return a dictionary where elements of the rng sequence are\n        mapped to themselves.\n\n    "));
      var1.setlocal("make_identity_dict", var11);
      var3 = null;
      var1.setline(1047);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, make_encoding_map$93, PyString.fromInterned(" Creates an encoding map from a decoding map.\n\n        If a target mapping in the decoding map occurs multiple\n        times, then that target is mapped to None (undefined mapping),\n        causing an exception when encountered by the charmap codec\n        during translation.\n\n        One example where this happens is cp875.py which decodes\n        multiple character to \\u001a.\n\n    "));
      var1.setlocal("make_encoding_map", var11);
      var3 = null;

      try {
         var1.setline(1071);
         var3 = var1.getname("lookup_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("strict"));
         var1.setlocal("strict_errors", var3);
         var3 = null;
         var1.setline(1072);
         var3 = var1.getname("lookup_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ignore"));
         var1.setlocal("ignore_errors", var3);
         var3 = null;
         var1.setline(1073);
         var3 = var1.getname("lookup_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("replace"));
         var1.setlocal("replace_errors", var3);
         var3 = null;
         var1.setline(1074);
         var3 = var1.getname("lookup_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xmlcharrefreplace"));
         var1.setlocal("xmlcharrefreplace_errors", var3);
         var3 = null;
         var1.setline(1075);
         var3 = var1.getname("lookup_error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("backslashreplace"));
         var1.setlocal("backslashreplace_errors", var3);
         var3 = null;
      } catch (Throwable var6) {
         var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("LookupError"))) {
            throw var7;
         }

         var1.setline(1078);
         var4 = var1.getname("None");
         var1.setlocal("strict_errors", var4);
         var4 = null;
         var1.setline(1079);
         var4 = var1.getname("None");
         var1.setlocal("ignore_errors", var4);
         var4 = null;
         var1.setline(1080);
         var4 = var1.getname("None");
         var1.setlocal("replace_errors", var4);
         var4 = null;
         var1.setline(1081);
         var4 = var1.getname("None");
         var1.setlocal("xmlcharrefreplace_errors", var4);
         var4 = null;
         var1.setline(1082);
         var4 = var1.getname("None");
         var1.setlocal("backslashreplace_errors", var4);
         var4 = null;
      }

      var1.setline(1086);
      PyInteger var12 = Py.newInteger(0);
      var1.setlocal("_false", var12);
      var3 = null;
      var1.setline(1087);
      if (var1.getname("_false").__nonzero__()) {
         var1.setline(1088);
         var3 = imp.importOne("encodings", var1, -1);
         var1.setlocal("encodings", var3);
         var3 = null;
      }

      var1.setline(1092);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1095);
         var3 = var1.getname("EncodedFile").__call__((ThreadState)var2, var1.getname("sys").__getattr__("stdout"), (PyObject)PyString.fromInterned("latin-1"), (PyObject)PyString.fromInterned("utf-8"));
         var1.getname("sys").__setattr__("stdout", var3);
         var3 = null;
         var1.setline(1098);
         var3 = var1.getname("EncodedFile").__call__((ThreadState)var2, var1.getname("sys").__getattr__("stdin"), (PyObject)PyString.fromInterned("utf-8"), (PyObject)PyString.fromInterned("latin-1"));
         var1.getname("sys").__setattr__("stdin", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CodecInfo$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(77);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __new__$2, (PyObject)null);
      var1.setlocal("__new__", var4);
      var3 = null;
      var1.setline(89);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$2(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getglobal("tuple").__getattr__("__new__").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(80);
      var3 = var1.getlocal(7);
      var1.getlocal(8).__setattr__("name", var3);
      var3 = null;
      var1.setline(81);
      var3 = var1.getlocal(1);
      var1.getlocal(8).__setattr__("encode", var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getlocal(2);
      var1.getlocal(8).__setattr__("decode", var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(5);
      var1.getlocal(8).__setattr__("incrementalencoder", var3);
      var3 = null;
      var1.setline(84);
      var3 = var1.getlocal(6);
      var1.getlocal(8).__setattr__("incrementaldecoder", var3);
      var3 = null;
      var1.setline(85);
      var3 = var1.getlocal(4);
      var1.getlocal(8).__setattr__("streamwriter", var3);
      var3 = null;
      var1.setline(86);
      var3 = var1.getlocal(3);
      var1.getlocal(8).__setattr__("streamreader", var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = PyString.fromInterned("<%s.%s object for encoding %s at 0x%x>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__module__"), var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(0).__getattr__("name"), var1.getglobal("id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Codec$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" Defines the interface for stateless encoders/decoders.\n\n        The .encode()/.decode() methods may use different error\n        handling schemes by providing the errors argument. These\n        string values are predefined:\n\n         'strict' - raise a ValueError error (or a subclass)\n         'ignore' - ignore the character and continue with the next\n         'replace' - replace with a suitable replacement character;\n                    Python will use the official U+FFFD REPLACEMENT\n                    CHARACTER for the builtin Unicode codecs on\n                    decoding and '?' on encoding.\n         'xmlcharrefreplace' - Replace with the appropriate XML\n                               character reference (only for encoding).\n         'backslashreplace'  - Replace with backslashed escape sequences\n                               (only for encoding).\n\n        The set of allowed values can be extended via register_error.\n\n    "));
      var1.setline(113);
      PyString.fromInterned(" Defines the interface for stateless encoders/decoders.\n\n        The .encode()/.decode() methods may use different error\n        handling schemes by providing the errors argument. These\n        string values are predefined:\n\n         'strict' - raise a ValueError error (or a subclass)\n         'ignore' - ignore the character and continue with the next\n         'replace' - replace with a suitable replacement character;\n                    Python will use the official U+FFFD REPLACEMENT\n                    CHARACTER for the builtin Unicode codecs on\n                    decoding and '?' on encoding.\n         'xmlcharrefreplace' - Replace with the appropriate XML\n                               character reference (only for encoding).\n         'backslashreplace'  - Replace with backslashed escape sequences\n                               (only for encoding).\n\n        The set of allowed values can be extended via register_error.\n\n    ");
      var1.setline(114);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$5, PyString.fromInterned(" Encodes the object input and returns a tuple (output\n            object, length consumed).\n\n            errors defines the error handling to apply. It defaults to\n            'strict' handling.\n\n            The method may not store state in the Codec instance. Use\n            StreamCodec for codecs which have to keep state in order to\n            make encoding/decoding efficient.\n\n            The encoder must be able to handle zero length input and\n            return an empty object of the output object type in this\n            situation.\n\n        "));
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(133);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$6, PyString.fromInterned(" Decodes the object input and returns a tuple (output\n            object, length consumed).\n\n            input must be an object which provides the bf_getreadbuf\n            buffer slot. Python strings, buffer objects and memory\n            mapped files are examples of objects providing this slot.\n\n            errors defines the error handling to apply. It defaults to\n            'strict' handling.\n\n            The method may not store state in the Codec instance. Use\n            StreamCodec for codecs which have to keep state in order to\n            make encoding/decoding efficient.\n\n            The decoder must be able to handle zero length input and\n            return an empty object of the output object type in this\n            situation.\n\n        "));
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyString.fromInterned(" Encodes the object input and returns a tuple (output\n            object, length consumed).\n\n            errors defines the error handling to apply. It defaults to\n            'strict' handling.\n\n            The method may not store state in the Codec instance. Use\n            StreamCodec for codecs which have to keep state in order to\n            make encoding/decoding efficient.\n\n            The encoder must be able to handle zero length input and\n            return an empty object of the output object type in this\n            situation.\n\n        ");
      var1.setline(131);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject decode$6(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned(" Decodes the object input and returns a tuple (output\n            object, length consumed).\n\n            input must be an object which provides the bf_getreadbuf\n            buffer slot. Python strings, buffer objects and memory\n            mapped files are examples of objects providing this slot.\n\n            errors defines the error handling to apply. It defaults to\n            'strict' handling.\n\n            The method may not store state in the Codec instance. Use\n            StreamCodec for codecs which have to keep state in order to\n            make encoding/decoding efficient.\n\n            The decoder must be able to handle zero length input and\n            return an empty object of the output object type in this\n            situation.\n\n        ");
      var1.setline(154);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject IncrementalEncoder$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    An IncrementalEncoder encodes an input in multiple steps. The input can be\n    passed piece by piece to the encode() method. The IncrementalEncoder remembers\n    the state of the Encoding process between calls to encode().\n    "));
      var1.setline(161);
      PyString.fromInterned("\n    An IncrementalEncoder encodes an input in multiple steps. The input can be\n    passed piece by piece to the encode() method. The IncrementalEncoder remembers\n    the state of the Encoding process between calls to encode().\n    ");
      var1.setline(162);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, PyString.fromInterned("\n        Creates an IncrementalEncoder instance.\n\n        The IncrementalEncoder may use different error handling schemes by\n        providing the errors keyword argument. See the module docstring\n        for a list of possible values.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(173);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$9, PyString.fromInterned("\n        Encodes input and returns the resulting object.\n        "));
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$10, PyString.fromInterned("\n        Resets the encoder to the initial state.\n        "));
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$11, PyString.fromInterned("\n        Return the current state of the encoder.\n        "));
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(190);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$12, PyString.fromInterned("\n        Set the current state of the encoder. state must have been\n        returned by getstate().\n        "));
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyString.fromInterned("\n        Creates an IncrementalEncoder instance.\n\n        The IncrementalEncoder may use different error handling schemes by\n        providing the errors keyword argument. See the module docstring\n        for a list of possible values.\n        ");
      var1.setline(170);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.setline(171);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject encode$9(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyString.fromInterned("\n        Encodes input and returns the resulting object.\n        ");
      var1.setline(177);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject reset$10(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyString.fromInterned("\n        Resets the encoder to the initial state.\n        ");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$11(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("\n        Return the current state of the encoder.\n        ");
      var1.setline(188);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setstate$12(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyString.fromInterned("\n        Set the current state of the encoder. state must have been\n        returned by getstate().\n        ");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BufferedIncrementalEncoder$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This subclass of IncrementalEncoder can be used as the baseclass for an\n    incremental encoder if the encoder must keep some of the output in a\n    buffer between calls to encode().\n    "));
      var1.setline(201);
      PyString.fromInterned("\n    This subclass of IncrementalEncoder can be used as the baseclass for an\n    incremental encoder if the encoder must keep some of the output in a\n    buffer between calls to encode().\n    ");
      var1.setline(202);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _buffer_encode$15, (PyObject)null);
      var1.setlocal("_buffer_encode", var4);
      var3 = null;
      var1.setline(211);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$16, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(219);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$17, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$18, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(226);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$19, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      var1.getglobal("IncrementalEncoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(204);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _buffer_encode$15(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject encode$16(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer")._add(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getlocal(0).__getattr__("_buffer_encode").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("errors"), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(216);
      var3 = var1.getlocal(3).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$17(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      var1.getglobal("IncrementalEncoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(221);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$18(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      Object var10000 = var1.getlocal(0).__getattr__("buffer");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = Py.newInteger(0);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject setstate$19(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      Object var10000 = var1.getlocal(1);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      Object var3 = var10000;
      var1.getlocal(0).__setattr__((String)"buffer", (PyObject)var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IncrementalDecoder$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    An IncrementalDecoder decodes an input in multiple steps. The input can be\n    passed piece by piece to the decode() method. The IncrementalDecoder\n    remembers the state of the decoding process between calls to decode().\n    "));
      var1.setline(234);
      PyString.fromInterned("\n    An IncrementalDecoder decodes an input in multiple steps. The input can be\n    passed piece by piece to the decode() method. The IncrementalDecoder\n    remembers the state of the decoding process between calls to decode().\n    ");
      var1.setline(235);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, PyString.fromInterned("\n        Creates a IncrementalDecoder instance.\n\n        The IncrementalDecoder may use different error handling schemes by\n        providing the errors keyword argument. See the module docstring\n        for a list of possible values.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(245);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, decode$22, PyString.fromInterned("\n        Decodes input and returns the resulting object.\n        "));
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(251);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$23, PyString.fromInterned("\n        Resets the decoder to the initial state.\n        "));
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$24, PyString.fromInterned("\n        Return the current state of the decoder.\n\n        This must be a (buffered_input, additional_state_info) tuple.\n        buffered_input must be a bytes object containing bytes that\n        were passed to decode() that have not yet been converted.\n        additional_state_info must be a non-negative integer\n        representing the state of the decoder WITHOUT yet having\n        processed the contents of buffered_input.  In the initial state\n        and after reset(), getstate() must return (b\"\", 0).\n        "));
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$25, PyString.fromInterned("\n        Set the current state of the decoder.\n\n        state must have been returned by getstate().  The effect of\n        setstate((b\"\", 0)) must be equivalent to reset().\n        "));
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyString.fromInterned("\n        Creates a IncrementalDecoder instance.\n\n        The IncrementalDecoder may use different error handling schemes by\n        providing the errors keyword argument. See the module docstring\n        for a list of possible values.\n        ");
      var1.setline(243);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$22(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("\n        Decodes input and returns the resulting object.\n        ");
      var1.setline(249);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject reset$23(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyString.fromInterned("\n        Resets the decoder to the initial state.\n        ");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$24(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyString.fromInterned("\n        Return the current state of the decoder.\n\n        This must be a (buffered_input, additional_state_info) tuple.\n        buffered_input must be a bytes object containing bytes that\n        were passed to decode() that have not yet been converted.\n        additional_state_info must be a non-negative integer\n        representing the state of the decoder WITHOUT yet having\n        processed the contents of buffered_input.  In the initial state\n        and after reset(), getstate() must return (b\"\", 0).\n        ");
      var1.setline(268);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setstate$25(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyString.fromInterned("\n        Set the current state of the decoder.\n\n        state must have been returned by getstate().  The effect of\n        setstate((b\"\", 0)) must be equivalent to reset().\n        ");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BufferedIncrementalDecoder$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This subclass of IncrementalDecoder can be used as the baseclass for an\n    incremental decoder if the decoder must be able to handle incomplete byte\n    sequences.\n    "));
      var1.setline(283);
      PyString.fromInterned("\n    This subclass of IncrementalDecoder can be used as the baseclass for an\n    incremental decoder if the decoder must be able to handle incomplete byte\n    sequences.\n    ");
      var1.setline(284);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$27, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(288);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _buffer_decode$28, (PyObject)null);
      var1.setlocal("_buffer_decode", var4);
      var3 = null;
      var1.setline(293);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, decode$29, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(301);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$30, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(305);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$31, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$32, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$27(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      var1.getglobal("IncrementalDecoder").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(286);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _buffer_decode$28(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject decode$29(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyObject var3 = var1.getlocal(0).__getattr__("buffer")._add(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(296);
      var3 = var1.getlocal(0).__getattr__("_buffer_decode").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("errors"), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(298);
      var3 = var1.getlocal(3).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(299);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$30(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      var1.getglobal("IncrementalDecoder").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(303);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$31(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("buffer"), Py.newInteger(0)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setstate$32(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyObject var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      var1.getlocal(0).__setattr__("buffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamWriter$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(322);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$34, PyString.fromInterned(" Creates a StreamWriter instance.\n\n            stream must be a file-like object open for writing\n            (binary) data.\n\n            The StreamWriter may use different error handling\n            schemes by providing the errors keyword argument. These\n            parameters are predefined:\n\n             'strict' - raise a ValueError (or a subclass)\n             'ignore' - ignore the character and continue with the next\n             'replace'- replace with a suitable replacement character\n             'xmlcharrefreplace' - Replace with the appropriate XML\n                                   character reference.\n             'backslashreplace'  - Replace with backslashed escape\n                                   sequences (only for encoding).\n\n            The set of allowed parameter values can be extended via\n            register_error.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$35, PyString.fromInterned(" Writes the object's contents encoded to self.stream.\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(354);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writelines$36, PyString.fromInterned(" Writes the concatenated list of strings to the stream\n            using .write().\n        "));
      var1.setlocal("writelines", var4);
      var3 = null;
      var1.setline(361);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$37, PyString.fromInterned(" Flushes and resets the codec buffers used for keeping state.\n\n            Calling this method should ensure that the data on the\n            output is put into a clean state, that allows appending\n            of new fresh data without having to rescan the whole\n            stream to recover state.\n\n        "));
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(373);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$38, (PyObject)null);
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(378);
      var3 = new PyObject[]{var1.getname("getattr")};
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$39, PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        "));
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(385);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$40, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(388);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$41, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$34(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyString.fromInterned(" Creates a StreamWriter instance.\n\n            stream must be a file-like object open for writing\n            (binary) data.\n\n            The StreamWriter may use different error handling\n            schemes by providing the errors keyword argument. These\n            parameters are predefined:\n\n             'strict' - raise a ValueError (or a subclass)\n             'ignore' - ignore the character and continue with the next\n             'replace'- replace with a suitable replacement character\n             'xmlcharrefreplace' - Replace with the appropriate XML\n                                   character reference.\n             'backslashreplace'  - Replace with backslashed escape\n                                   sequences (only for encoding).\n\n            The set of allowed parameter values can be extended via\n            register_error.\n        ");
      var1.setline(344);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(345);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$35(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyString.fromInterned(" Writes the object's contents encoded to self.stream.\n        ");
      var1.setline(351);
      PyObject var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(352);
      var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writelines$36(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyString.fromInterned(" Writes the concatenated list of strings to the stream\n            using .write().\n        ");
      var1.setline(359);
      var1.getlocal(0).__getattr__("write").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$37(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyString.fromInterned(" Flushes and resets the codec buffers used for keeping state.\n\n            Calling this method should ensure that the data on the\n            output is put into a clean state, that allows appending\n            of new fresh data without having to rescan the whole\n            stream to recover state.\n\n        ");
      var1.setline(371);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seek$38(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      var1.getlocal(0).__getattr__("stream").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(375);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(376);
         var1.getlocal(0).__getattr__("reset").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$39(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        ");
      var1.setline(383);
      PyObject var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("stream"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __enter__$40(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$41(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamReader$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(395);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$43, PyString.fromInterned(" Creates a StreamReader instance.\n\n            stream must be a file-like object open for reading\n            (binary) data.\n\n            The StreamReader may use different error handling\n            schemes by providing the errors keyword argument. These\n            parameters are predefined:\n\n             'strict' - raise a ValueError (or a subclass)\n             'ignore' - ignore the character and continue with the next\n             'replace'- replace with a suitable replacement character;\n\n            The set of allowed parameter values can be extended via\n            register_error.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(421);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$44, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(424);
      var3 = new PyObject[]{Py.newInteger(-1), Py.newInteger(-1), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, read$45, PyString.fromInterned(" Decodes data from the stream self.stream and returns the\n            resulting object.\n\n            chars indicates the number of characters to read from the\n            stream. read() will never return more than chars\n            characters, but it might return less, if there are not enough\n            characters available.\n\n            size indicates the approximate maximum number of bytes to\n            read from the stream for decoding purposes. The decoder\n            can modify this setting as appropriate. The default value\n            -1 indicates to read and decode as much as possible.  size\n            is intended to prevent having to decode huge files in one\n            step.\n\n            If firstline is true, and a UnicodeDecodeError happens\n            after the first line terminator in the input only the first line\n            will be returned, the rest of the input will be kept until the\n            next call to read().\n\n            The method should use a greedy read strategy meaning that\n            it should read as much data as is allowed within the\n            definition of the encoding and the given size, e.g.  if\n            optional encoding endings or state markers are available\n            on the stream, these should be read too.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(503);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, readline$46, PyString.fromInterned(" Read one line from the input stream and return the\n            decoded data.\n\n            size, if given, is passed as size argument to the\n            read() method.\n\n        "));
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(576);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, readlines$47, PyString.fromInterned(" Read all lines available on the input stream\n            and return them as list of lines.\n\n            Line breaks are implemented using the codec's decoder\n            method and are included in the list entries.\n\n            sizehint, if given, is ignored since there is no efficient\n            way to finding the true end-of-line.\n\n        "));
      var1.setlocal("readlines", var4);
      var3 = null;
      var1.setline(591);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$48, PyString.fromInterned(" Resets the codec buffers used for keeping state.\n\n            Note that no stream repositioning should take place.\n            This method is primarily intended to be able to recover\n            from decoding errors.\n\n        "));
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(604);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$49, PyString.fromInterned(" Set the input stream's current position.\n\n            Resets the codec buffers used for keeping state.\n        "));
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(612);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$50, PyString.fromInterned(" Return the next decoded line from the input stream."));
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(620);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$51, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(623);
      var3 = new PyObject[]{var1.getname("getattr")};
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$52, PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        "));
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(630);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$53, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(633);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$54, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$43(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyString.fromInterned(" Creates a StreamReader instance.\n\n            stream must be a file-like object open for reading\n            (binary) data.\n\n            The StreamReader may use different error handling\n            schemes by providing the errors keyword argument. These\n            parameters are predefined:\n\n             'strict' - raise a ValueError (or a subclass)\n             'ignore' - ignore the character and continue with the next\n             'replace'- replace with a suitable replacement character;\n\n            The set of allowed parameter values can be extended via\n            register_error.\n        ");
      var1.setline(413);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(414);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.setline(415);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"bytebuffer", var4);
      var3 = null;
      var1.setline(418);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"charbuffer", var4);
      var3 = null;
      var1.setline(419);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("linebuffer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$44(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject read$45(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyString.fromInterned(" Decodes data from the stream self.stream and returns the\n            resulting object.\n\n            chars indicates the number of characters to read from the\n            stream. read() will never return more than chars\n            characters, but it might return less, if there are not enough\n            characters available.\n\n            size indicates the approximate maximum number of bytes to\n            read from the stream for decoding purposes. The decoder\n            can modify this setting as appropriate. The default value\n            -1 indicates to read and decode as much as possible.  size\n            is intended to prevent having to decode huge files in one\n            step.\n\n            If firstline is true, and a UnicodeDecodeError happens\n            after the first line terminator in the input only the first line\n            will be returned, the rest of the input will be kept until the\n            next call to read().\n\n            The method should use a greedy read strategy meaning that\n            it should read as much data as is allowed within the\n            definition of the encoding and the given size, e.g.  if\n            optional encoding endings or state markers are available\n            on the stream, these should be read too.\n        ");
      var1.setline(453);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("linebuffer").__nonzero__()) {
         var1.setline(454);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("linebuffer"));
         var1.getlocal(0).__setattr__("charbuffer", var3);
         var3 = null;
         var1.setline(455);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("linebuffer", var3);
         var3 = null;
      }

      PyObject var10000;
      do {
         var1.setline(458);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(460);
         var3 = var1.getlocal(2);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(461);
            var3 = var1.getlocal(1);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(462);
               if (var1.getlocal(0).__getattr__("charbuffer").__nonzero__()) {
                  break;
               }
            } else {
               var1.setline(464);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("charbuffer"));
               var10000 = var3._ge(var1.getlocal(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }
            }
         } else {
            var1.setline(467);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("charbuffer"));
            var10000 = var3._ge(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }
         }

         var1.setline(470);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(471);
            var3 = var1.getlocal(0).__getattr__("stream").__getattr__("read").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(473);
            var3 = var1.getlocal(0).__getattr__("stream").__getattr__("read").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(475);
         var3 = var1.getlocal(0).__getattr__("bytebuffer")._add(var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;

         PyObject var4;
         PyObject[] var5;
         PyObject var8;
         try {
            var1.setline(477);
            var3 = var1.getlocal(0).__getattr__("decode").__call__(var2, var1.getlocal(5), var1.getlocal(0).__getattr__("errors"));
            PyObject[] var9 = Py.unpackSequence(var3, 2);
            var8 = var9[0];
            var1.setlocal(6, var8);
            var5 = null;
            var8 = var9[1];
            var1.setlocal(7, var8);
            var5 = null;
            var3 = null;
         } catch (Throwable var7) {
            PyException var10 = Py.setException(var7, var1);
            if (!var10.match(var1.getglobal("UnicodeDecodeError"))) {
               throw var10;
            }

            var4 = var10.value;
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(479);
            if (!var1.getlocal(3).__nonzero__()) {
               var1.setline(485);
               throw Py.makeException();
            }

            var1.setline(480);
            var4 = var1.getlocal(0).__getattr__("decode").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(8).__getattr__("start"), (PyObject)null), var1.getlocal(0).__getattr__("errors"));
            var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var4 = null;
            var1.setline(481);
            var4 = var1.getlocal(6).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(482);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
            var10000 = var4._le(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(483);
               throw Py.makeException();
            }
         }

         var1.setline(487);
         var3 = var1.getlocal(5).__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("bytebuffer", var3);
         var3 = null;
         var1.setline(489);
         var10000 = var1.getlocal(0);
         String var11 = "charbuffer";
         var4 = var10000;
         var8 = var4.__getattr__(var11);
         var8 = var8._iadd(var1.getlocal(6));
         var4.__setattr__(var11, var8);
         var1.setline(491);
      } while(!var1.getlocal(4).__not__().__nonzero__());

      var1.setline(493);
      var3 = var1.getlocal(2);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(495);
         var3 = var1.getlocal(0).__getattr__("charbuffer");
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(496);
         PyString var12 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"charbuffer", var12);
         var3 = null;
      } else {
         var1.setline(499);
         var3 = var1.getlocal(0).__getattr__("charbuffer").__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(500);
         var3 = var1.getlocal(0).__getattr__("charbuffer").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("charbuffer", var3);
         var3 = null;
      }

      var1.setline(501);
      var3 = var1.getlocal(10);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$46(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyString.fromInterned(" Read one line from the input stream and return the\n            decoded data.\n\n            size, if given, is passed as size argument to the\n            read() method.\n\n        ");
      var1.setline(514);
      PyObject var3;
      PyObject var14;
      if (var1.getlocal(0).__getattr__("linebuffer").__nonzero__()) {
         var1.setline(515);
         var3 = var1.getlocal(0).__getattr__("linebuffer").__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(516);
         var1.getlocal(0).__getattr__("linebuffer").__delitem__((PyObject)Py.newInteger(0));
         var1.setline(517);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("linebuffer"));
         var14 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var14.__nonzero__()) {
            var1.setline(520);
            var3 = var1.getlocal(0).__getattr__("linebuffer").__getitem__(Py.newInteger(0));
            var1.getlocal(0).__setattr__("charbuffer", var3);
            var3 = null;
            var1.setline(521);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("linebuffer", var3);
            var3 = null;
         }

         var1.setline(522);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(523);
            var3 = var1.getlocal(3).__getattr__("splitlines").__call__(var2, var1.getglobal("False")).__getitem__(Py.newInteger(0));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(524);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(526);
         Object var10000 = var1.getlocal(1);
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = Py.newInteger(72);
         }

         Object var4 = var10000;
         var1.setlocal(4, (PyObject)var4);
         var4 = null;
         var1.setline(527);
         PyString var7 = PyString.fromInterned("");
         var1.setlocal(3, var7);
         var4 = null;

         while(true) {
            var1.setline(529);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(530);
            var14 = var1.getlocal(0).__getattr__("read");
            PyObject[] var8 = new PyObject[]{var1.getlocal(4), var1.getglobal("True")};
            String[] var5 = new String[]{"firstline"};
            var14 = var14.__call__(var2, var8, var5);
            var4 = null;
            PyObject var10 = var14;
            var1.setlocal(5, var10);
            var4 = null;
            var1.setline(531);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(535);
               if (var1.getlocal(5).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r")).__nonzero__()) {
                  var1.setline(536);
                  var10 = var1.getlocal(5);
                  var14 = var1.getlocal(0).__getattr__("read");
                  PyObject[] var9 = new PyObject[]{Py.newInteger(1), Py.newInteger(1)};
                  String[] var6 = new String[]{"size", "chars"};
                  var14 = var14.__call__(var2, var9, var6);
                  var5 = null;
                  var10 = var10._iadd(var14);
                  var1.setlocal(5, var10);
               }
            }

            var1.setline(538);
            var10 = var1.getlocal(3);
            var10 = var10._iadd(var1.getlocal(5));
            var1.setlocal(3, var10);
            var1.setline(539);
            var10 = var1.getlocal(3).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
            var1.setlocal(6, var10);
            var4 = null;
            var1.setline(540);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(541);
               var10 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
               var14 = var10._gt(Py.newInteger(1));
               var4 = null;
               if (var14.__nonzero__()) {
                  var1.setline(544);
                  var10 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                  var1.setlocal(3, var10);
                  var4 = null;
                  var1.setline(545);
                  var1.getlocal(6).__delitem__((PyObject)Py.newInteger(0));
                  var1.setline(546);
                  var10 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
                  var14 = var10._gt(Py.newInteger(1));
                  var4 = null;
                  if (var14.__nonzero__()) {
                     var1.setline(548);
                     var14 = var1.getlocal(6);
                     PyInteger var13 = Py.newInteger(-1);
                     PyObject var11 = var14;
                     PyObject var12 = var11.__getitem__(var13);
                     var12 = var12._iadd(var1.getlocal(0).__getattr__("charbuffer"));
                     var11.__setitem__((PyObject)var13, var12);
                     var1.setline(549);
                     var10 = var1.getlocal(6);
                     var1.getlocal(0).__setattr__("linebuffer", var10);
                     var4 = null;
                     var1.setline(550);
                     var10 = var1.getglobal("None");
                     var1.getlocal(0).__setattr__("charbuffer", var10);
                     var4 = null;
                  } else {
                     var1.setline(553);
                     var10 = var1.getlocal(6).__getitem__(Py.newInteger(0))._add(var1.getlocal(0).__getattr__("charbuffer"));
                     var1.getlocal(0).__setattr__("charbuffer", var10);
                     var4 = null;
                  }

                  var1.setline(554);
                  if (var1.getlocal(2).__not__().__nonzero__()) {
                     var1.setline(555);
                     var10 = var1.getlocal(3).__getattr__("splitlines").__call__(var2, var1.getglobal("False")).__getitem__(Py.newInteger(0));
                     var1.setlocal(3, var10);
                     var4 = null;
                  }
                  break;
               }

               var1.setline(557);
               var10 = var1.getlocal(6).__getitem__(Py.newInteger(0));
               var1.setlocal(7, var10);
               var4 = null;
               var1.setline(558);
               var10 = var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("splitlines").__call__(var2, var1.getglobal("False")).__getitem__(Py.newInteger(0));
               var1.setlocal(8, var10);
               var4 = null;
               var1.setline(559);
               var10 = var1.getlocal(7);
               var14 = var10._ne(var1.getlocal(8));
               var4 = null;
               if (var14.__nonzero__()) {
                  var1.setline(561);
                  var10 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null))._add(var1.getlocal(0).__getattr__("charbuffer"));
                  var1.getlocal(0).__setattr__("charbuffer", var10);
                  var4 = null;
                  var1.setline(562);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(563);
                     var10 = var1.getlocal(7);
                     var1.setlocal(3, var10);
                     var4 = null;
                  } else {
                     var1.setline(565);
                     var10 = var1.getlocal(8);
                     var1.setlocal(3, var10);
                     var4 = null;
                  }
                  break;
               }
            }

            var1.setline(568);
            var14 = var1.getlocal(5).__not__();
            if (!var14.__nonzero__()) {
               var10 = var1.getlocal(1);
               var14 = var10._isnot(var1.getglobal("None"));
               var4 = null;
            }

            if (var14.__nonzero__()) {
               var1.setline(569);
               var14 = var1.getlocal(3);
               if (var14.__nonzero__()) {
                  var14 = var1.getlocal(2).__not__();
               }

               if (var14.__nonzero__()) {
                  var1.setline(570);
                  var10 = var1.getlocal(3).__getattr__("splitlines").__call__(var2, var1.getglobal("False")).__getitem__(Py.newInteger(0));
                  var1.setlocal(3, var10);
                  var4 = null;
               }
               break;
            }

            var1.setline(572);
            var10 = var1.getlocal(4);
            var14 = var10._lt(Py.newInteger(8000));
            var4 = null;
            if (var14.__nonzero__()) {
               var1.setline(573);
               var10 = var1.getlocal(4);
               var10 = var10._imul(Py.newInteger(2));
               var1.setlocal(4, var10);
            }
         }

         var1.setline(574);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readlines$47(PyFrame var1, ThreadState var2) {
      var1.setline(587);
      PyString.fromInterned(" Read all lines available on the input stream\n            and return them as list of lines.\n\n            Line breaks are implemented using the codec's decoder\n            method and are included in the list entries.\n\n            sizehint, if given, is ignored since there is no efficient\n            way to finding the true end-of-line.\n\n        ");
      var1.setline(588);
      PyObject var3 = var1.getlocal(0).__getattr__("read").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(589);
      var3 = var1.getlocal(3).__getattr__("splitlines").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$48(PyFrame var1, ThreadState var2) {
      var1.setline(599);
      PyString.fromInterned(" Resets the codec buffers used for keeping state.\n\n            Note that no stream repositioning should take place.\n            This method is primarily intended to be able to recover\n            from decoding errors.\n\n        ");
      var1.setline(600);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"bytebuffer", var3);
      var3 = null;
      var1.setline(601);
      PyUnicode var4 = PyUnicode.fromInterned("");
      var1.getlocal(0).__setattr__((String)"charbuffer", var4);
      var3 = null;
      var1.setline(602);
      PyObject var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("linebuffer", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seek$49(PyFrame var1, ThreadState var2) {
      var1.setline(608);
      PyString.fromInterned(" Set the input stream's current position.\n\n            Resets the codec buffers used for keeping state.\n        ");
      var1.setline(609);
      var1.getlocal(0).__getattr__("stream").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(610);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject next$50(PyFrame var1, ThreadState var2) {
      var1.setline(614);
      PyString.fromInterned(" Return the next decoded line from the input stream.");
      var1.setline(615);
      PyObject var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(616);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(617);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(618);
         throw Py.makeException(var1.getglobal("StopIteration"));
      }
   }

   public PyObject __iter__$51(PyFrame var1, ThreadState var2) {
      var1.setline(621);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getattr__$52(PyFrame var1, ThreadState var2) {
      var1.setline(627);
      PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        ");
      var1.setline(628);
      PyObject var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("stream"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __enter__$53(PyFrame var1, ThreadState var2) {
      var1.setline(631);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$54(PyFrame var1, ThreadState var2) {
      var1.setline(634);
      var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamReaderWriter$55(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" StreamReaderWriter instances allow wrapping streams which\n        work in both read and write modes.\n\n        The design is such that one can use the factory functions\n        returned by the codec.lookup() function to construct the\n        instance.\n\n    "));
      var1.setline(647);
      PyString.fromInterned(" StreamReaderWriter instances allow wrapping streams which\n        work in both read and write modes.\n\n        The design is such that one can use the factory functions\n        returned by the codec.lookup() function to construct the\n        instance.\n\n    ");
      var1.setline(649);
      PyString var3 = PyString.fromInterned("unknown");
      var1.setlocal("encoding", var3);
      var3 = null;
      var1.setline(651);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$56, PyString.fromInterned(" Creates a StreamReaderWriter instance.\n\n            stream must be a Stream-like object.\n\n            Reader, Writer must be factory functions or classes\n            providing the StreamReader, StreamWriter interface resp.\n\n            Error handling is done in the same way as defined for the\n            StreamWriter/Readers.\n\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(669);
      var4 = new PyObject[]{Py.newInteger(-1)};
      var5 = new PyFunction(var1.f_globals, var4, read$57, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(673);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, readline$58, (PyObject)null);
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(677);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, readlines$59, (PyObject)null);
      var1.setlocal("readlines", var5);
      var3 = null;
      var1.setline(681);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$60, PyString.fromInterned(" Return the next decoded line from the input stream."));
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(686);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iter__$61, (PyObject)null);
      var1.setlocal("__iter__", var5);
      var3 = null;
      var1.setline(689);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$62, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(693);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writelines$63, (PyObject)null);
      var1.setlocal("writelines", var5);
      var3 = null;
      var1.setline(697);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, reset$64, (PyObject)null);
      var1.setlocal("reset", var5);
      var3 = null;
      var1.setline(702);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, seek$65, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(708);
      var4 = new PyObject[]{var1.getname("getattr")};
      var5 = new PyFunction(var1.f_globals, var4, __getattr__$66, PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        "));
      var1.setlocal("__getattr__", var5);
      var3 = null;
      var1.setline(717);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __enter__$67, (PyObject)null);
      var1.setlocal("__enter__", var5);
      var3 = null;
      var1.setline(720);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __exit__$68, (PyObject)null);
      var1.setlocal("__exit__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$56(PyFrame var1, ThreadState var2) {
      var1.setline(663);
      PyString.fromInterned(" Creates a StreamReaderWriter instance.\n\n            stream must be a Stream-like object.\n\n            Reader, Writer must be factory functions or classes\n            providing the StreamReader, StreamWriter interface resp.\n\n            Error handling is done in the same way as defined for the\n            StreamWriter/Readers.\n\n        ");
      var1.setline(664);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(665);
      var3 = var1.getlocal(2).__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.getlocal(0).__setattr__("reader", var3);
      var3 = null;
      var1.setline(666);
      var3 = var1.getlocal(3).__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.getlocal(0).__setattr__("writer", var3);
      var3 = null;
      var1.setline(667);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$57(PyFrame var1, ThreadState var2) {
      var1.setline(671);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$58(PyFrame var1, ThreadState var2) {
      var1.setline(675);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readline").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readlines$59(PyFrame var1, ThreadState var2) {
      var1.setline(679);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readlines").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$60(PyFrame var1, ThreadState var2) {
      var1.setline(683);
      PyString.fromInterned(" Return the next decoded line from the input stream.");
      var1.setline(684);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("next").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$61(PyFrame var1, ThreadState var2) {
      var1.setline(687);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$62(PyFrame var1, ThreadState var2) {
      var1.setline(691);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writelines$63(PyFrame var1, ThreadState var2) {
      var1.setline(695);
      PyObject var3 = var1.getlocal(0).__getattr__("writer").__getattr__("writelines").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$64(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      var1.getlocal(0).__getattr__("reader").__getattr__("reset").__call__(var2);
      var1.setline(700);
      var1.getlocal(0).__getattr__("writer").__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject seek$65(PyFrame var1, ThreadState var2) {
      var1.setline(703);
      var1.getlocal(0).__getattr__("stream").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(704);
      var1.getlocal(0).__getattr__("reader").__getattr__("reset").__call__(var2);
      var1.setline(705);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(706);
         var1.getlocal(0).__getattr__("writer").__getattr__("reset").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$66(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        ");
      var1.setline(713);
      PyObject var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("stream"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __enter__$67(PyFrame var1, ThreadState var2) {
      var1.setline(718);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$68(PyFrame var1, ThreadState var2) {
      var1.setline(721);
      var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamRecoder$69(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" StreamRecoder instances provide a frontend - backend\n        view of encoding data.\n\n        They use the complete set of APIs returned by the\n        codecs.lookup() function to implement their task.\n\n        Data written to the stream is first decoded into an\n        intermediate format (which is dependent on the given codec\n        combination) and then written to the stream using an instance\n        of the provided Writer class.\n\n        In the other direction, data is read from the stream using a\n        Reader instance and then return encoded data to the caller.\n\n    "));
      var1.setline(741);
      PyString.fromInterned(" StreamRecoder instances provide a frontend - backend\n        view of encoding data.\n\n        They use the complete set of APIs returned by the\n        codecs.lookup() function to implement their task.\n\n        Data written to the stream is first decoded into an\n        intermediate format (which is dependent on the given codec\n        combination) and then written to the stream using an instance\n        of the provided Writer class.\n\n        In the other direction, data is read from the stream using a\n        Reader instance and then return encoded data to the caller.\n\n    ");
      var1.setline(743);
      PyString var3 = PyString.fromInterned("unknown");
      var1.setlocal("data_encoding", var3);
      var3 = null;
      var1.setline(744);
      var3 = PyString.fromInterned("unknown");
      var1.setlocal("file_encoding", var3);
      var3 = null;
      var1.setline(746);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$70, PyString.fromInterned(" Creates a StreamRecoder instance which implements a two-way\n            conversion: encode and decode work on the frontend (the\n            input to .read() and output of .write()) while\n            Reader and Writer work on the backend (reading and\n            writing to the stream).\n\n            You can use these objects to do transparent direct\n            recodings from e.g. latin-1 to utf-8 and back.\n\n            stream must be a file-like object.\n\n            encode, decode must adhere to the Codec interface, Reader,\n            Writer must be factory functions or classes providing the\n            StreamReader, StreamWriter interface resp.\n\n            encode and decode are needed for the frontend translation,\n            Reader and Writer for the backend translation. Unicode is\n            used as intermediate encoding.\n\n            Error handling is done in the same way as defined for the\n            StreamWriter/Readers.\n\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(779);
      var4 = new PyObject[]{Py.newInteger(-1)};
      var5 = new PyFunction(var1.f_globals, var4, read$71, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(785);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, readline$72, (PyObject)null);
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(794);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, readlines$73, (PyObject)null);
      var1.setlocal("readlines", var5);
      var3 = null;
      var1.setline(800);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$74, PyString.fromInterned(" Return the next decoded line from the input stream."));
      var1.setlocal("next", var5);
      var3 = null;
      var1.setline(807);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iter__$75, (PyObject)null);
      var1.setlocal("__iter__", var5);
      var3 = null;
      var1.setline(810);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$76, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(815);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, writelines$77, (PyObject)null);
      var1.setlocal("writelines", var5);
      var3 = null;
      var1.setline(821);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, reset$78, (PyObject)null);
      var1.setlocal("reset", var5);
      var3 = null;
      var1.setline(826);
      var4 = new PyObject[]{var1.getname("getattr")};
      var5 = new PyFunction(var1.f_globals, var4, __getattr__$79, PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        "));
      var1.setlocal("__getattr__", var5);
      var3 = null;
      var1.setline(833);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __enter__$80, (PyObject)null);
      var1.setlocal("__enter__", var5);
      var3 = null;
      var1.setline(836);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __exit__$81, (PyObject)null);
      var1.setlocal("__exit__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$70(PyFrame var1, ThreadState var2) {
      var1.setline(771);
      PyString.fromInterned(" Creates a StreamRecoder instance which implements a two-way\n            conversion: encode and decode work on the frontend (the\n            input to .read() and output of .write()) while\n            Reader and Writer work on the backend (reading and\n            writing to the stream).\n\n            You can use these objects to do transparent direct\n            recodings from e.g. latin-1 to utf-8 and back.\n\n            stream must be a file-like object.\n\n            encode, decode must adhere to the Codec interface, Reader,\n            Writer must be factory functions or classes providing the\n            StreamReader, StreamWriter interface resp.\n\n            encode and decode are needed for the frontend translation,\n            Reader and Writer for the backend translation. Unicode is\n            used as intermediate encoding.\n\n            Error handling is done in the same way as defined for the\n            StreamWriter/Readers.\n\n        ");
      var1.setline(772);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(773);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("encode", var3);
      var3 = null;
      var1.setline(774);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("decode", var3);
      var3 = null;
      var1.setline(775);
      var3 = var1.getlocal(4).__call__(var2, var1.getlocal(1), var1.getlocal(6));
      var1.getlocal(0).__setattr__("reader", var3);
      var3 = null;
      var1.setline(776);
      var3 = var1.getlocal(5).__call__(var2, var1.getlocal(1), var1.getlocal(6));
      var1.getlocal(0).__setattr__("writer", var3);
      var3 = null;
      var1.setline(777);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("errors", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$71(PyFrame var1, ThreadState var2) {
      var1.setline(781);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(782);
      var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("errors"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(783);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$72(PyFrame var1, ThreadState var2) {
      var1.setline(787);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(788);
         var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readline").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(790);
         var3 = var1.getlocal(0).__getattr__("reader").__getattr__("readline").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(791);
      var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("errors"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(792);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readlines$73(PyFrame var1, ThreadState var2) {
      var1.setline(796);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("read").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(797);
      var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("errors"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(798);
      var3 = var1.getlocal(2).__getattr__("splitlines").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$74(PyFrame var1, ThreadState var2) {
      var1.setline(802);
      PyString.fromInterned(" Return the next decoded line from the input stream.");
      var1.setline(803);
      PyObject var3 = var1.getlocal(0).__getattr__("reader").__getattr__("next").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(804);
      var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(805);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$75(PyFrame var1, ThreadState var2) {
      var1.setline(808);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$76(PyFrame var1, ThreadState var2) {
      var1.setline(812);
      PyObject var3 = var1.getlocal(0).__getattr__("decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(813);
      var3 = var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writelines$77(PyFrame var1, ThreadState var2) {
      var1.setline(817);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(818);
      var3 = var1.getlocal(0).__getattr__("decode").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("errors"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(819);
      var3 = var1.getlocal(0).__getattr__("writer").__getattr__("write").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$78(PyFrame var1, ThreadState var2) {
      var1.setline(823);
      var1.getlocal(0).__getattr__("reader").__getattr__("reset").__call__(var2);
      var1.setline(824);
      var1.getlocal(0).__getattr__("writer").__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$79(PyFrame var1, ThreadState var2) {
      var1.setline(830);
      PyString.fromInterned(" Inherit all other methods from the underlying stream.\n        ");
      var1.setline(831);
      PyObject var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("stream"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __enter__$80(PyFrame var1, ThreadState var2) {
      var1.setline(834);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$81(PyFrame var1, ThreadState var2) {
      var1.setline(837);
      var1.getlocal(0).__getattr__("stream").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$82(PyFrame var1, ThreadState var2) {
      var1.setline(871);
      PyString.fromInterned(" Open an encoded file using the given mode and return\n        a wrapped version providing transparent encoding/decoding.\n\n        Note: The wrapped version will only accept the object format\n        defined by the codecs, i.e. Unicode objects for most builtin\n        codecs. Output is also codec dependent and will usually be\n        Unicode as well.\n\n        Files are always opened in binary mode, even if no binary mode\n        was specified. This is done to avoid data loss due to encodings\n        using 8-bit values. The default file mode is 'rb' meaning to\n        open the file in binary read mode.\n\n        encoding specifies the encoding which is to be used for the\n        file.\n\n        errors may be given to define the error handling. It defaults\n        to 'strict' which causes ValueErrors to be raised in case an\n        encoding error occurs.\n\n        buffering has the same meaning as for the builtin open() API.\n        It defaults to line buffered.\n\n        The returned wrapped file object provides an extra attribute\n        .encoding which allows querying the used encoding. This\n        attribute is only available if an encoding was specified as\n        parameter.\n\n    ");
      var1.setline(872);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(873);
         PyString var5 = PyString.fromInterned("U");
         var10000 = var5._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(875);
            var3 = var1.getlocal(1).__getattr__("strip").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("U"), (PyObject)PyString.fromInterned(""));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(876);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var3._notin(var1.getglobal("set").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rwa")));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(877);
               var3 = PyString.fromInterned("r")._add(var1.getlocal(1));
               var1.setlocal(1, var3);
               var3 = null;
            }
         }

         var1.setline(878);
         var5 = PyString.fromInterned("b");
         var10000 = var5._notin(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(880);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("b"));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(881);
      var3 = var1.getglobal("__builtin__").__getattr__("open").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(882);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(883);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(884);
         PyObject var4 = var1.getglobal("lookup").__call__(var2, var1.getlocal(2));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(885);
         var4 = var1.getglobal("StreamReaderWriter").__call__(var2, var1.getlocal(5), var1.getlocal(6).__getattr__("streamreader"), var1.getlocal(6).__getattr__("streamwriter"), var1.getlocal(3));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(887);
         var4 = var1.getlocal(2);
         var1.getlocal(7).__setattr__("encoding", var4);
         var4 = null;
         var1.setline(888);
         var3 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject EncodedFile$83(PyFrame var1, ThreadState var2) {
      var1.setline(914);
      PyString.fromInterned(" Return a wrapped version of file which provides transparent\n        encoding translation.\n\n        Strings written to the wrapped file are interpreted according\n        to the given data_encoding and then written to the original\n        file as string using file_encoding. The intermediate encoding\n        will usually be Unicode but depends on the specified codecs.\n\n        Strings are read from the file using file_encoding and then\n        passed back to the caller as string using data_encoding.\n\n        If file_encoding is not given, it defaults to data_encoding.\n\n        errors may be given to define the error handling. It defaults\n        to 'strict' which causes ValueErrors to be raised in case an\n        encoding error occurs.\n\n        The returned wrapped file object provides two extra attributes\n        .data_encoding and .file_encoding which reflect the given\n        parameters of the same name. The attributes can be used for\n        introspection by Python programs.\n\n    ");
      var1.setline(915);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(916);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(917);
      var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(918);
      var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(919);
      var10000 = var1.getglobal("StreamRecoder");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(4).__getattr__("encode"), var1.getlocal(4).__getattr__("decode"), var1.getlocal(5).__getattr__("streamreader"), var1.getlocal(5).__getattr__("streamwriter"), var1.getlocal(3)};
      var3 = var10000.__call__(var2, var4);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(922);
      var3 = var1.getlocal(1);
      var1.getlocal(6).__setattr__("data_encoding", var3);
      var3 = null;
      var1.setline(923);
      var3 = var1.getlocal(2);
      var1.getlocal(6).__setattr__("file_encoding", var3);
      var3 = null;
      var1.setline(924);
      var3 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getencoder$84(PyFrame var1, ThreadState var2) {
      var1.setline(935);
      PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its encoder function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    ");
      var1.setline(936);
      PyObject var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(0)).__getattr__("encode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getdecoder$85(PyFrame var1, ThreadState var2) {
      var1.setline(945);
      PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its decoder function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    ");
      var1.setline(946);
      PyObject var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(0)).__getattr__("decode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getincrementalencoder$86(PyFrame var1, ThreadState var2) {
      var1.setline(956);
      PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its IncrementalEncoder class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found\n        or the codecs doesn't provide an incremental encoder.\n\n    ");
      var1.setline(957);
      PyObject var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(0)).__getattr__("incrementalencoder");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(958);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(959);
         throw Py.makeException(var1.getglobal("LookupError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(960);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getincrementaldecoder$87(PyFrame var1, ThreadState var2) {
      var1.setline(970);
      PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its IncrementalDecoder class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found\n        or the codecs doesn't provide an incremental decoder.\n\n    ");
      var1.setline(971);
      PyObject var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(0)).__getattr__("incrementaldecoder");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(972);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(973);
         throw Py.makeException(var1.getglobal("LookupError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(974);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getreader$88(PyFrame var1, ThreadState var2) {
      var1.setline(983);
      PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its StreamReader class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    ");
      var1.setline(984);
      PyObject var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(0)).__getattr__("streamreader");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getwriter$89(PyFrame var1, ThreadState var2) {
      var1.setline(993);
      PyString.fromInterned(" Lookup up the codec for the given encoding and return\n        its StreamWriter class or factory function.\n\n        Raises a LookupError in case the encoding cannot be found.\n\n    ");
      var1.setline(994);
      PyObject var3 = var1.getglobal("lookup").__call__(var2, var1.getlocal(0)).__getattr__("streamwriter");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterencode$90(PyFrame var1, ThreadState var2) {
      label31: {
         Object var10000;
         Object[] var3;
         PyObject var4;
         Object[] var5;
         PyObject var6;
         PyObject var10;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(1004);
               PyString.fromInterned("\n    Encoding iterator.\n\n    Encodes the input strings from the iterator using a IncrementalEncoder.\n\n    errors and kwargs are passed through to the IncrementalEncoder\n    constructor.\n    ");
               var1.setline(1005);
               var10 = var1.getglobal("getincrementalencoder").__call__(var2, var1.getlocal(1));
               PyObject[] var7 = new PyObject[]{var1.getlocal(2)};
               String[] var8 = new String[0];
               var10 = var10._callextra(var7, var8, (PyObject)null, var1.getlocal(3));
               var3 = null;
               var6 = var10;
               var1.setlocal(4, var6);
               var3 = null;
               var1.setline(1006);
               var6 = var1.getlocal(0).__iter__();
               break;
            case 1:
               var5 = var1.f_savedlocals;
               var6 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var10 = (PyObject)var10000;
               break;
            case 2:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var10 = (PyObject)var10000;
               break label31;
         }

         while(true) {
            var1.setline(1006);
            var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(1010);
               var6 = var1.getlocal(4).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)var1.getglobal("True"));
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(1011);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(1012);
                  var1.setline(1012);
                  var10 = var1.getlocal(6);
                  var1.f_lasti = 2;
                  var3 = new Object[6];
                  var1.f_savedlocals = var3;
                  return var10;
               }
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(1007);
            PyObject var9 = var1.getlocal(4).__getattr__("encode").__call__(var2, var1.getlocal(5));
            var1.setlocal(6, var9);
            var5 = null;
            var1.setline(1008);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(1009);
               var1.setline(1009);
               var10 = var1.getlocal(6);
               var1.f_lasti = 1;
               var5 = new Object[]{null, null, null, var6, var4, null};
               var1.f_savedlocals = var5;
               return var10;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject iterdecode$91(PyFrame var1, ThreadState var2) {
      label31: {
         Object var10000;
         Object[] var3;
         PyObject var4;
         Object[] var5;
         PyObject var6;
         PyObject var10;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(1022);
               PyString.fromInterned("\n    Decoding iterator.\n\n    Decodes the input strings from the iterator using a IncrementalDecoder.\n\n    errors and kwargs are passed through to the IncrementalDecoder\n    constructor.\n    ");
               var1.setline(1023);
               var10 = var1.getglobal("getincrementaldecoder").__call__(var2, var1.getlocal(1));
               PyObject[] var7 = new PyObject[]{var1.getlocal(2)};
               String[] var8 = new String[0];
               var10 = var10._callextra(var7, var8, (PyObject)null, var1.getlocal(3));
               var3 = null;
               var6 = var10;
               var1.setlocal(4, var6);
               var3 = null;
               var1.setline(1024);
               var6 = var1.getlocal(0).__iter__();
               break;
            case 1:
               var5 = var1.f_savedlocals;
               var6 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var10 = (PyObject)var10000;
               break;
            case 2:
               var3 = var1.f_savedlocals;
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var10 = (PyObject)var10000;
               break label31;
         }

         while(true) {
            var1.setline(1024);
            var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(1028);
               var6 = var1.getlocal(4).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)var1.getglobal("True"));
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(1029);
               if (var1.getlocal(6).__nonzero__()) {
                  var1.setline(1030);
                  var1.setline(1030);
                  var10 = var1.getlocal(6);
                  var1.f_lasti = 2;
                  var3 = new Object[6];
                  var1.f_savedlocals = var3;
                  return var10;
               }
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(1025);
            PyObject var9 = var1.getlocal(4).__getattr__("decode").__call__(var2, var1.getlocal(5));
            var1.setlocal(6, var9);
            var5 = null;
            var1.setline(1026);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(1027);
               var1.setline(1027);
               var10 = var1.getlocal(6);
               var1.f_lasti = 1;
               var5 = new Object[]{null, null, null, var6, var4, null};
               var1.f_savedlocals = var5;
               return var10;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject make_identity_dict$92(PyFrame var1, ThreadState var2) {
      var1.setline(1041);
      PyString.fromInterned(" make_identity_dict(rng) -> dict\n\n        Return a dictionary where elements of the rng sequence are\n        mapped to themselves.\n\n    ");
      var1.setline(1042);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1043);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1043);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1045);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(1044);
         PyObject var5 = var1.getlocal(2);
         var1.getlocal(1).__setitem__(var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject make_encoding_map$93(PyFrame var1, ThreadState var2) {
      var1.setline(1059);
      PyString.fromInterned(" Creates an encoding map from a decoding map.\n\n        If a target mapping in the decoding map occurs multiple\n        times, then that target is mapped to None (undefined mapping),\n        causing an exception when encountered by the charmap codec\n        during translation.\n\n        One example where this happens is cp875.py which decodes\n        multiple character to \\u001a.\n\n    ");
      var1.setline(1060);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1061);
      PyObject var7 = var1.getlocal(0).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1061);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(1066);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(1062);
         PyObject var8 = var1.getlocal(3);
         PyObject var10000 = var8._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(1063);
            var8 = var1.getlocal(2);
            var1.getlocal(1).__setitem__(var1.getlocal(3), var8);
            var5 = null;
         } else {
            var1.setline(1065);
            var8 = var1.getglobal("None");
            var1.getlocal(1).__setitem__(var1.getlocal(3), var8);
            var5 = null;
         }
      }
   }

   public codecs$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CodecInfo$1 = Py.newCode(0, var2, var1, "CodecInfo", 75, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls", "encode", "decode", "streamreader", "streamwriter", "incrementalencoder", "incrementaldecoder", "name", "self"};
      __new__$2 = Py.newCode(8, var2, var1, "__new__", 77, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 89, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Codec$4 = Py.newCode(0, var2, var1, "Codec", 92, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 114, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$6 = Py.newCode(3, var2, var1, "decode", 133, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$7 = Py.newCode(0, var2, var1, "IncrementalEncoder", 156, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$8 = Py.newCode(2, var2, var1, "__init__", 162, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final"};
      encode$9 = Py.newCode(3, var2, var1, "encode", 173, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$10 = Py.newCode(1, var2, var1, "reset", 179, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$11 = Py.newCode(1, var2, var1, "getstate", 184, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$12 = Py.newCode(2, var2, var1, "setstate", 190, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedIncrementalEncoder$13 = Py.newCode(0, var2, var1, "BufferedIncrementalEncoder", 196, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$14 = Py.newCode(2, var2, var1, "__init__", 202, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "final"};
      _buffer_encode$15 = Py.newCode(4, var2, var1, "_buffer_encode", 206, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "data", "result", "consumed"};
      encode$16 = Py.newCode(3, var2, var1, "encode", 211, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$17 = Py.newCode(1, var2, var1, "reset", 219, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$18 = Py.newCode(1, var2, var1, "getstate", 223, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$19 = Py.newCode(2, var2, var1, "setstate", 226, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$20 = Py.newCode(0, var2, var1, "IncrementalDecoder", 229, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$21 = Py.newCode(2, var2, var1, "__init__", 235, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final"};
      decode$22 = Py.newCode(3, var2, var1, "decode", 245, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$23 = Py.newCode(1, var2, var1, "reset", 251, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$24 = Py.newCode(1, var2, var1, "getstate", 256, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$25 = Py.newCode(2, var2, var1, "setstate", 270, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BufferedIncrementalDecoder$26 = Py.newCode(0, var2, var1, "BufferedIncrementalDecoder", 278, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors"};
      __init__$27 = Py.newCode(2, var2, var1, "__init__", 284, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "final"};
      _buffer_decode$28 = Py.newCode(4, var2, var1, "_buffer_decode", 288, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "data", "result", "consumed"};
      decode$29 = Py.newCode(3, var2, var1, "decode", 293, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$30 = Py.newCode(1, var2, var1, "reset", 301, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$31 = Py.newCode(1, var2, var1, "getstate", 305, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state"};
      setstate$32 = Py.newCode(2, var2, var1, "setstate", 309, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$33 = Py.newCode(0, var2, var1, "StreamWriter", 320, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors"};
      __init__$34 = Py.newCode(3, var2, var1, "__init__", 322, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "data", "consumed"};
      write$35 = Py.newCode(2, var2, var1, "write", 347, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list"};
      writelines$36 = Py.newCode(2, var2, var1, "writelines", 354, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$37 = Py.newCode(1, var2, var1, "reset", 361, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "whence"};
      seek$38 = Py.newCode(3, var2, var1, "seek", 373, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "getattr"};
      __getattr__$39 = Py.newCode(3, var2, var1, "__getattr__", 378, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$40 = Py.newCode(1, var2, var1, "__enter__", 385, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "tb"};
      __exit__$41 = Py.newCode(4, var2, var1, "__exit__", 388, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamReader$42 = Py.newCode(0, var2, var1, "StreamReader", 393, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors"};
      __init__$43 = Py.newCode(3, var2, var1, "__init__", 395, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$44 = Py.newCode(3, var2, var1, "decode", 421, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "chars", "firstline", "newdata", "data", "newchars", "decodedbytes", "exc", "lines", "result"};
      read$45 = Py.newCode(4, var2, var1, "read", 424, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "keepends", "line", "readsize", "data", "lines", "line0withend", "line0withoutend"};
      readline$46 = Py.newCode(3, var2, var1, "readline", 503, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sizehint", "keepends", "data"};
      readlines$47 = Py.newCode(3, var2, var1, "readlines", 576, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$48 = Py.newCode(1, var2, var1, "reset", 591, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "whence"};
      seek$49 = Py.newCode(3, var2, var1, "seek", 604, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      next$50 = Py.newCode(1, var2, var1, "next", 612, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$51 = Py.newCode(1, var2, var1, "__iter__", 620, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "getattr"};
      __getattr__$52 = Py.newCode(3, var2, var1, "__getattr__", 623, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$53 = Py.newCode(1, var2, var1, "__enter__", 630, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "tb"};
      __exit__$54 = Py.newCode(4, var2, var1, "__exit__", 633, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamReaderWriter$55 = Py.newCode(0, var2, var1, "StreamReaderWriter", 638, false, false, self, 55, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "Reader", "Writer", "errors"};
      __init__$56 = Py.newCode(5, var2, var1, "__init__", 651, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$57 = Py.newCode(2, var2, var1, "read", 669, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      readline$58 = Py.newCode(2, var2, var1, "readline", 673, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sizehint"};
      readlines$59 = Py.newCode(2, var2, var1, "readlines", 677, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      next$60 = Py.newCode(1, var2, var1, "next", 681, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$61 = Py.newCode(1, var2, var1, "__iter__", 686, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      write$62 = Py.newCode(2, var2, var1, "write", 689, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list"};
      writelines$63 = Py.newCode(2, var2, var1, "writelines", 693, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$64 = Py.newCode(1, var2, var1, "reset", 697, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "whence"};
      seek$65 = Py.newCode(3, var2, var1, "seek", 702, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "getattr"};
      __getattr__$66 = Py.newCode(3, var2, var1, "__getattr__", 708, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$67 = Py.newCode(1, var2, var1, "__enter__", 717, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "tb"};
      __exit__$68 = Py.newCode(4, var2, var1, "__exit__", 720, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamRecoder$69 = Py.newCode(0, var2, var1, "StreamRecoder", 725, false, false, self, 69, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "encode", "decode", "Reader", "Writer", "errors"};
      __init__$70 = Py.newCode(7, var2, var1, "__init__", 746, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "data", "bytesencoded"};
      read$71 = Py.newCode(2, var2, var1, "read", 779, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "data", "bytesencoded"};
      readline$72 = Py.newCode(2, var2, var1, "readline", 785, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sizehint", "data", "bytesencoded"};
      readlines$73 = Py.newCode(2, var2, var1, "readlines", 794, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "bytesencoded"};
      next$74 = Py.newCode(1, var2, var1, "next", 800, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$75 = Py.newCode(1, var2, var1, "__iter__", 807, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "bytesdecoded"};
      write$76 = Py.newCode(2, var2, var1, "write", 810, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "data", "bytesdecoded"};
      writelines$77 = Py.newCode(2, var2, var1, "writelines", 815, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$78 = Py.newCode(1, var2, var1, "reset", 821, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "getattr"};
      __getattr__$79 = Py.newCode(3, var2, var1, "__getattr__", 826, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$80 = Py.newCode(1, var2, var1, "__enter__", 833, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "tb"};
      __exit__$81 = Py.newCode(4, var2, var1, "__exit__", 836, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "mode", "encoding", "errors", "buffering", "file", "info", "srw"};
      open$82 = Py.newCode(5, var2, var1, "open", 841, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "data_encoding", "file_encoding", "errors", "data_info", "file_info", "sr"};
      EncodedFile$83 = Py.newCode(4, var2, var1, "EncodedFile", 890, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding"};
      getencoder$84 = Py.newCode(1, var2, var1, "getencoder", 928, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding"};
      getdecoder$85 = Py.newCode(1, var2, var1, "getdecoder", 938, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "encoder"};
      getincrementalencoder$86 = Py.newCode(1, var2, var1, "getincrementalencoder", 948, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "decoder"};
      getincrementaldecoder$87 = Py.newCode(1, var2, var1, "getincrementaldecoder", 962, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding"};
      getreader$88 = Py.newCode(1, var2, var1, "getreader", 976, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding"};
      getwriter$89 = Py.newCode(1, var2, var1, "getwriter", 986, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"iterator", "encoding", "errors", "kwargs", "encoder", "input", "output"};
      iterencode$90 = Py.newCode(4, var2, var1, "iterencode", 996, false, true, self, 90, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"iterator", "encoding", "errors", "kwargs", "decoder", "input", "output"};
      iterdecode$91 = Py.newCode(4, var2, var1, "iterdecode", 1014, false, true, self, 91, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"rng", "res", "i"};
      make_identity_dict$92 = Py.newCode(1, var2, var1, "make_identity_dict", 1034, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"decoding_map", "m", "k", "v"};
      make_encoding_map$93 = Py.newCode(1, var2, var1, "make_encoding_map", 1047, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new codecs$py("codecs$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(codecs$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CodecInfo$1(var2, var3);
         case 2:
            return this.__new__$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this.Codec$4(var2, var3);
         case 5:
            return this.encode$5(var2, var3);
         case 6:
            return this.decode$6(var2, var3);
         case 7:
            return this.IncrementalEncoder$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.encode$9(var2, var3);
         case 10:
            return this.reset$10(var2, var3);
         case 11:
            return this.getstate$11(var2, var3);
         case 12:
            return this.setstate$12(var2, var3);
         case 13:
            return this.BufferedIncrementalEncoder$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this._buffer_encode$15(var2, var3);
         case 16:
            return this.encode$16(var2, var3);
         case 17:
            return this.reset$17(var2, var3);
         case 18:
            return this.getstate$18(var2, var3);
         case 19:
            return this.setstate$19(var2, var3);
         case 20:
            return this.IncrementalDecoder$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.decode$22(var2, var3);
         case 23:
            return this.reset$23(var2, var3);
         case 24:
            return this.getstate$24(var2, var3);
         case 25:
            return this.setstate$25(var2, var3);
         case 26:
            return this.BufferedIncrementalDecoder$26(var2, var3);
         case 27:
            return this.__init__$27(var2, var3);
         case 28:
            return this._buffer_decode$28(var2, var3);
         case 29:
            return this.decode$29(var2, var3);
         case 30:
            return this.reset$30(var2, var3);
         case 31:
            return this.getstate$31(var2, var3);
         case 32:
            return this.setstate$32(var2, var3);
         case 33:
            return this.StreamWriter$33(var2, var3);
         case 34:
            return this.__init__$34(var2, var3);
         case 35:
            return this.write$35(var2, var3);
         case 36:
            return this.writelines$36(var2, var3);
         case 37:
            return this.reset$37(var2, var3);
         case 38:
            return this.seek$38(var2, var3);
         case 39:
            return this.__getattr__$39(var2, var3);
         case 40:
            return this.__enter__$40(var2, var3);
         case 41:
            return this.__exit__$41(var2, var3);
         case 42:
            return this.StreamReader$42(var2, var3);
         case 43:
            return this.__init__$43(var2, var3);
         case 44:
            return this.decode$44(var2, var3);
         case 45:
            return this.read$45(var2, var3);
         case 46:
            return this.readline$46(var2, var3);
         case 47:
            return this.readlines$47(var2, var3);
         case 48:
            return this.reset$48(var2, var3);
         case 49:
            return this.seek$49(var2, var3);
         case 50:
            return this.next$50(var2, var3);
         case 51:
            return this.__iter__$51(var2, var3);
         case 52:
            return this.__getattr__$52(var2, var3);
         case 53:
            return this.__enter__$53(var2, var3);
         case 54:
            return this.__exit__$54(var2, var3);
         case 55:
            return this.StreamReaderWriter$55(var2, var3);
         case 56:
            return this.__init__$56(var2, var3);
         case 57:
            return this.read$57(var2, var3);
         case 58:
            return this.readline$58(var2, var3);
         case 59:
            return this.readlines$59(var2, var3);
         case 60:
            return this.next$60(var2, var3);
         case 61:
            return this.__iter__$61(var2, var3);
         case 62:
            return this.write$62(var2, var3);
         case 63:
            return this.writelines$63(var2, var3);
         case 64:
            return this.reset$64(var2, var3);
         case 65:
            return this.seek$65(var2, var3);
         case 66:
            return this.__getattr__$66(var2, var3);
         case 67:
            return this.__enter__$67(var2, var3);
         case 68:
            return this.__exit__$68(var2, var3);
         case 69:
            return this.StreamRecoder$69(var2, var3);
         case 70:
            return this.__init__$70(var2, var3);
         case 71:
            return this.read$71(var2, var3);
         case 72:
            return this.readline$72(var2, var3);
         case 73:
            return this.readlines$73(var2, var3);
         case 74:
            return this.next$74(var2, var3);
         case 75:
            return this.__iter__$75(var2, var3);
         case 76:
            return this.write$76(var2, var3);
         case 77:
            return this.writelines$77(var2, var3);
         case 78:
            return this.reset$78(var2, var3);
         case 79:
            return this.__getattr__$79(var2, var3);
         case 80:
            return this.__enter__$80(var2, var3);
         case 81:
            return this.__exit__$81(var2, var3);
         case 82:
            return this.open$82(var2, var3);
         case 83:
            return this.EncodedFile$83(var2, var3);
         case 84:
            return this.getencoder$84(var2, var3);
         case 85:
            return this.getdecoder$85(var2, var3);
         case 86:
            return this.getincrementalencoder$86(var2, var3);
         case 87:
            return this.getincrementaldecoder$87(var2, var3);
         case 88:
            return this.getreader$88(var2, var3);
         case 89:
            return this.getwriter$89(var2, var3);
         case 90:
            return this.iterencode$90(var2, var3);
         case 91:
            return this.iterdecode$91(var2, var3);
         case 92:
            return this.make_identity_dict$92(var2, var3);
         case 93:
            return this.make_encoding_map$93(var2, var3);
         default:
            return null;
      }
   }
}
