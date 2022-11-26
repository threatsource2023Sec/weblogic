package encodings;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("encodings/_java.py")
public class _java$py extends PyFunctionTable implements PyRunnable {
   static _java$py self;
   static final PyCode f$0;
   static final PyCode _java_factory$1;
   static final PyCode Codec$2;
   static final PyCode __init__$3;
   static final PyCode decode$4;
   static final PyCode encode$5;
   static final PyCode NonfinalCodec$6;
   static final PyCode decode$7;
   static final PyCode IncrementalEncoder$8;
   static final PyCode __init__$9;
   static final PyCode encode$10;
   static final PyCode IncrementalDecoder$11;
   static final PyCode __init__$12;
   static final PyCode decode$13;
   static final PyCode reset$14;
   static final PyCode getstate$15;
   static final PyCode setstate$16;
   static final PyCode StreamWriter$17;
   static final PyCode __init__$18;
   static final PyCode StreamReader$19;
   static final PyCode __init__$20;
   static final PyCode _process_decode_errors$21;
   static final PyCode _process_incomplete_decode$22;
   static final PyCode _get_unicode$23;
   static final PyCode _process_encode_errors$24;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(3);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"array"};
      PyObject[] var6 = imp.importFrom("array", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("array", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"partial"};
      var6 = imp.importFrom("functools", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("partial", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"StringBuilder"};
      var6 = imp.importFrom("java.lang", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("StringBuilder", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"ByteBuffer", "CharBuffer"};
      var6 = imp.importFrom("java.nio", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("ByteBuffer", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CharBuffer", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"Charset", "IllegalCharsetNameException"};
      var6 = imp.importFrom("java.nio.charset", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Charset", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("IllegalCharsetNameException", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"StringIO"};
      var6 = imp.importFrom("StringIO", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(13);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("cp932"), PyString.fromInterned("cp942"), PyString.fromInterned("iso2022_jp"), PyString.fromInterned("ISO-2022-JP"), PyString.fromInterned("iso2022_jp_2"), PyString.fromInterned("ISO-2022-JP-2"), PyString.fromInterned("iso2022_kr"), PyString.fromInterned("ISO-2022-KR"), PyString.fromInterned("shift_jisx0213"), PyString.fromInterned("x-SJIS_0213")});
      var1.setlocal("python_to_java", var7);
      var3 = null;
      var1.setline(23);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, _java_factory$1, (PyObject)null);
      var1.setlocal("_java_factory", var8);
      var3 = null;
      var1.setline(47);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Codec", var6, Codec$2);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(94);
      var6 = new PyObject[]{var1.getname("Codec")};
      var4 = Py.makeClass("NonfinalCodec", var6, NonfinalCodec$6);
      var1.setlocal("NonfinalCodec", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(100);
      var6 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var6, IncrementalEncoder$8);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(130);
      var6 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var6, IncrementalDecoder$11);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(177);
      var6 = new PyObject[]{var1.getname("NonfinalCodec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var6, StreamWriter$17);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(184);
      var6 = new PyObject[]{var1.getname("NonfinalCodec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var6, StreamReader$19);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(191);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _process_decode_errors$21, (PyObject)null);
      var1.setlocal("_process_decode_errors", var8);
      var3 = null;
      var1.setline(211);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _process_incomplete_decode$22, (PyObject)null);
      var1.setlocal("_process_incomplete_decode", var8);
      var3 = null;
      var1.setline(231);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _get_unicode$23, (PyObject)null);
      var1.setlocal("_get_unicode", var8);
      var3 = null;
      var1.setline(235);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _process_encode_errors$24, (PyObject)null);
      var1.setlocal("_process_encode_errors", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _java_factory$1(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getglobal("python_to_java").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("False");
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(28);
         var3 = var1.getglobal("Charset").__getattr__("isSupported").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (!var8.match(var1.getglobal("IllegalCharsetNameException"))) {
            throw var8;
         }

         var1.setline(30);
      }

      var1.setline(31);
      PyTuple var10;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(32);
         var10 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("set").__call__(var2)});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(34);
         PyObject var4 = var1.getglobal("Charset").__getattr__("forName").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(35);
         PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
         PyObject[] var9 = new PyObject[]{var1.getlocal(0), var1.getglobal("Codec").__call__(var2, var1.getlocal(0)).__getattr__("encode"), var1.getglobal("Codec").__call__(var2, var1.getlocal(0)).__getattr__("decode"), null, null, null, null};
         PyObject var10002 = var1.getglobal("partial");
         PyObject[] var5 = new PyObject[]{var1.getglobal("IncrementalEncoder"), var1.getlocal(0)};
         String[] var6 = new String[]{"encoding"};
         var10002 = var10002.__call__(var2, var5, var6);
         var5 = null;
         var9[3] = var10002;
         var10002 = var1.getglobal("partial");
         var5 = new PyObject[]{var1.getglobal("IncrementalDecoder"), var1.getlocal(0)};
         var6 = new String[]{"encoding"};
         var10002 = var10002.__call__(var2, var5, var6);
         var5 = null;
         var9[4] = var10002;
         var10002 = var1.getglobal("partial");
         var5 = new PyObject[]{var1.getglobal("StreamReader"), var1.getlocal(0)};
         var6 = new String[]{"encoding"};
         var10002 = var10002.__call__(var2, var5, var6);
         var5 = null;
         var9[5] = var10002;
         var10002 = var1.getglobal("partial");
         var5 = new PyObject[]{var1.getglobal("StreamWriter"), var1.getlocal(0)};
         var6 = new String[]{"encoding"};
         var10002 = var10002.__call__(var2, var5, var6);
         var5 = null;
         var9[6] = var10002;
         String[] var11 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
         var10000 = var10000.__call__(var2, var9, var11);
         var4 = null;
         var4 = var10000;
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(44);
         var10 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2).__getattr__("aliases").__call__(var2)});
         var1.f_lasti = -1;
         return var10;
      }
   }

   public PyObject Codec$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(49);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(52);
      var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, decode$4, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(72);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, encode$5, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("encoding", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decode$4(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyObject var3 = var1.getglobal("codecs").__getattr__("lookup_error").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("ByteBuffer").__getattr__("wrap").__call__(var2, var1.getglobal("array").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"), (PyObject)var1.getlocal(1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getglobal("Charset").__getattr__("forName").__call__(var2, var1.getlocal(0).__getattr__("encoding")).__getattr__("newDecoder").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getglobal("CharBuffer").__getattr__("allocate").__call__(var2, var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getglobal("int").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))._div(Py.newInteger(2))), (PyObject)Py.newInteger(256)), (PyObject)Py.newInteger(1024)));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getglobal("StringBuilder").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(6).__getattr__("averageCharsPerByte").__call__(var2)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1)))));
      var1.setlocal(8, var3);
      var3 = null;

      while(true) {
         var1.setline(59);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(60);
         var3 = var1.getlocal(6).__getattr__("decode").__call__(var2, var1.getlocal(5), var1.getlocal(7), var1.getglobal("False"));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(61);
         var3 = var1.getlocal(7).__getattr__("position").__call__(var2);
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(62);
         var1.getlocal(7).__getattr__("rewind").__call__(var2);
         var1.setline(63);
         var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(7).__getattr__("subSequence").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(10)));
         var1.setline(64);
         PyObject var10000;
         PyObject[] var4;
         if (var1.getlocal(9).__getattr__("isUnderflow").__call__(var2).__nonzero__()) {
            var1.setline(65);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(66);
               var10000 = var1.getglobal("_process_incomplete_decode");
               var4 = new PyObject[]{var1.getlocal(0).__getattr__("encoding"), var1.getlocal(1), var1.getlocal(4), var1.getlocal(5), var1.getlocal(8)};
               var10000.__call__(var2, var4);
            }
            break;
         }

         var1.setline(68);
         var10000 = var1.getglobal("_process_decode_errors");
         var4 = new PyObject[]{var1.getlocal(0).__getattr__("encoding"), var1.getlocal(1), var1.getlocal(9), var1.getlocal(4), var1.getlocal(5), var1.getlocal(8)};
         var10000.__call__(var2, var4);
      }

      var1.setline(70);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(8).__getattr__("toString").__call__(var2), var1.getlocal(5).__getattr__("position").__call__(var2)});
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getglobal("codecs").__getattr__("lookup_error").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getglobal("CharBuffer").__getattr__("allocate").__call__(var2, var1.getglobal("StringBuilder").__call__(var2, var1.getlocal(1)).__getattr__("length").__call__(var2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(76);
      var1.getlocal(4).__getattr__("put").__call__(var2, var1.getlocal(1));
      var1.setline(77);
      var1.getlocal(4).__getattr__("rewind").__call__(var2);
      var1.setline(78);
      var3 = var1.getglobal("Charset").__getattr__("forName").__call__(var2, var1.getlocal(0).__getattr__("encoding")).__getattr__("newEncoder").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(79);
      var3 = var1.getglobal("ByteBuffer").__getattr__("allocate").__call__(var2, var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))._mul(Py.newInteger(2)), (PyObject)Py.newInteger(256)), (PyObject)Py.newInteger(1024)));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(80);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;

      while(true) {
         var1.setline(82);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(83);
         var3 = var1.getlocal(5).__getattr__("encode").__call__(var2, var1.getlocal(4), var1.getlocal(6), var1.getglobal("True"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(84);
         var3 = var1.getlocal(6).__getattr__("position").__call__(var2);
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(85);
         var1.getlocal(6).__getattr__("rewind").__call__(var2);
         var1.setline(86);
         var1.getlocal(7).__getattr__("write").__call__(var2, var1.getlocal(6).__getattr__("array").__call__(var2).__getslice__(Py.newInteger(0), var1.getlocal(9), (PyObject)null).__getattr__("tostring").__call__(var2));
         var1.setline(87);
         if (var1.getlocal(8).__getattr__("isUnderflow").__call__(var2).__nonzero__()) {
            break;
         }

         var1.setline(89);
         PyObject var10000 = var1.getglobal("_process_encode_errors");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0).__getattr__("encoding"), var1.getlocal(1), var1.getlocal(8), var1.getlocal(3), var1.getlocal(4), var1.getlocal(7)};
         var10000.__call__(var2, var4);
      }

      var1.setline(91);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(7).__getattr__("getvalue").__call__(var2), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject NonfinalCodec$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(96);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$7, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyObject var10000 = var1.getglobal("Codec").__getattr__("decode");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getglobal("False")};
      String[] var4 = new String[]{"final"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject IncrementalEncoder$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(102);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(109);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, encode$10, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(104);
         PyObject var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("encoding", var3);
         var3 = null;
         var1.setline(105);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("errors", var3);
         var3 = null;
         var1.setline(106);
         var3 = var1.getglobal("Charset").__getattr__("forName").__call__(var2, var1.getlocal(0).__getattr__("encoding")).__getattr__("newEncoder").__call__(var2);
         var1.getlocal(0).__setattr__("encoder", var3);
         var3 = null;
         var1.setline(107);
         var3 = var1.getglobal("ByteBuffer").__getattr__("allocate").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024));
         var1.getlocal(0).__setattr__("output_buffer", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject encode$10(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getglobal("codecs").__getattr__("lookup_error").__call__(var2, var1.getlocal(0).__getattr__("errors"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getglobal("CharBuffer").__getattr__("allocate").__call__(var2, var1.getglobal("StringBuilder").__call__(var2, var1.getlocal(1)).__getattr__("length").__call__(var2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(113);
      var1.getlocal(4).__getattr__("put").__call__(var2, var1.getlocal(1));
      var1.setline(114);
      var1.getlocal(4).__getattr__("rewind").__call__(var2);
      var1.setline(115);
      var1.getlocal(0).__getattr__("output_buffer").__getattr__("rewind").__call__(var2);
      var1.setline(116);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;

      while(true) {
         var1.setline(118);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(119);
         var3 = var1.getlocal(0).__getattr__("encoder").__getattr__("encode").__call__(var2, var1.getlocal(4), var1.getlocal(0).__getattr__("output_buffer"), var1.getlocal(2));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(120);
         var3 = var1.getlocal(0).__getattr__("output_buffer").__getattr__("position").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(121);
         var1.getlocal(0).__getattr__("output_buffer").__getattr__("rewind").__call__(var2);
         var1.setline(122);
         var1.getlocal(5).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("output_buffer").__getattr__("array").__call__(var2).__getslice__(Py.newInteger(0), var1.getlocal(7), (PyObject)null).__getattr__("tostring").__call__(var2));
         var1.setline(123);
         if (var1.getlocal(6).__getattr__("isUnderflow").__call__(var2).__nonzero__()) {
            break;
         }

         var1.setline(125);
         PyObject var10000 = var1.getglobal("_process_encode_errors");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0).__getattr__("encoding"), var1.getlocal(1), var1.getlocal(6), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var10000.__call__(var2, var4);
      }

      var1.setline(127);
      var3 = var1.getlocal(5).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(132);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(140);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, decode$13, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      var1.setline(163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$14, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(167);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getstate$15, (PyObject)null);
      var1.setlocal("getstate", var4);
      var3 = null;
      var1.setline(171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setstate$16, (PyObject)null);
      var1.setlocal("setstate", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(134);
         PyObject var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("encoding", var3);
         var3 = null;
         var1.setline(135);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("errors", var3);
         var3 = null;
         var1.setline(136);
         var3 = var1.getglobal("Charset").__getattr__("forName").__call__(var2, var1.getlocal(0).__getattr__("encoding")).__getattr__("newDecoder").__call__(var2);
         var1.getlocal(0).__setattr__("decoder", var3);
         var3 = null;
         var1.setline(137);
         var3 = var1.getglobal("CharBuffer").__getattr__("allocate").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024));
         var1.getlocal(0).__setattr__("output_buffer", var3);
         var3 = null;
         var1.setline(138);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"buffer", var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject decode$13(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getglobal("codecs").__getattr__("lookup_error").__call__(var2, var1.getlocal(0).__getattr__("errors"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(142);
      var3 = var1.getglobal("array").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"), (PyObject)var1.getlocal(0).__getattr__("buffer")._add(var1.getglobal("str").__call__(var2, var1.getlocal(1))));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(143);
      var3 = var1.getglobal("ByteBuffer").__getattr__("wrap").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(144);
      var3 = var1.getglobal("StringBuilder").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("decoder").__getattr__("averageCharsPerByte").__call__(var2)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1)))));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(145);
      var1.getlocal(0).__getattr__("output_buffer").__getattr__("rewind").__call__(var2);

      while(true) {
         var1.setline(147);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(148);
         var3 = var1.getlocal(0).__getattr__("decoder").__getattr__("decode").__call__(var2, var1.getlocal(5), var1.getlocal(0).__getattr__("output_buffer"), var1.getlocal(2));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(149);
         var3 = var1.getlocal(0).__getattr__("output_buffer").__getattr__("position").__call__(var2);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(150);
         var1.getlocal(0).__getattr__("output_buffer").__getattr__("rewind").__call__(var2);
         var1.setline(151);
         var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("output_buffer").__getattr__("subSequence").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(8)));
         var1.setline(152);
         PyObject var10000;
         PyObject[] var4;
         if (var1.getlocal(7).__getattr__("isUnderflow").__call__(var2).__nonzero__()) {
            var1.setline(153);
            if (var1.getlocal(2).__not__().__nonzero__()) {
               var1.setline(155);
               var3 = var1.getlocal(4).__getslice__(var1.getlocal(5).__getattr__("position").__call__(var2), var1.getlocal(5).__getattr__("limit").__call__(var2), (PyObject)null).__getattr__("tostring").__call__(var2);
               var1.getlocal(0).__setattr__("buffer", var3);
               var3 = null;
            } else {
               var1.setline(157);
               var10000 = var1.getglobal("_process_incomplete_decode");
               var4 = new PyObject[]{var1.getlocal(0).__getattr__("encoding"), var1.getlocal(1), var1.getlocal(3), var1.getlocal(5), var1.getlocal(6)};
               var10000.__call__(var2, var4);
            }
            break;
         }

         var1.setline(159);
         var10000 = var1.getglobal("_process_decode_errors");
         var4 = new PyObject[]{var1.getlocal(0).__getattr__("encoding"), var1.getlocal(1), var1.getlocal(7), var1.getlocal(3), var1.getlocal(5), var1.getlocal(6)};
         var10000.__call__(var2, var4);
      }

      var1.setline(161);
      var3 = var1.getlocal(6).__getattr__("toString").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reset$14(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var3);
      var3 = null;
      var1.setline(165);
      var1.getlocal(0).__getattr__("decoder").__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getstate$15(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyTuple var10000 = new PyTuple;
      PyObject[] var10002 = new PyObject[2];
      Object var10005 = var1.getlocal(0).__getattr__("buffer");
      if (!((PyObject)var10005).__nonzero__()) {
         var10005 = PyString.fromInterned("");
      }

      var10002[0] = (PyObject)var10005;
      var10002[1] = Py.newInteger(0);
      var10000.<init>(var10002);
      PyTuple var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setstate$16(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      Object var10000 = var1.getlocal(1);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)});
      }

      Object var3 = var10000;
      PyObject[] var4 = Py.unpackSequence((PyObject)var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("buffer", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(174);
      var1.getlocal(0).__getattr__("decoder").__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamWriter$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(179);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      var1.getglobal("NonfinalCodec").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(3));
      var1.setline(181);
      var1.getglobal("codecs").__getattr__("StreamWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject StreamReader$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(186);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      var1.getglobal("NonfinalCodec").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(3));
      var1.setline(188);
      var1.getglobal("codecs").__getattr__("StreamReader").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _process_decode_errors$21(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      if (var1.getlocal(2).__getattr__("isError").__call__(var2).__nonzero__()) {
         var1.setline(193);
         PyObject var10000 = var1.getglobal("UnicodeDecodeError");
         PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(4).__getattr__("position").__call__(var2), var1.getlocal(4).__getattr__("position").__call__(var2)._add(var1.getlocal(2).__getattr__("length").__call__(var2)), PyString.fromInterned("illegal multibyte sequence")};
         PyObject var6 = var10000.__call__(var2, var3);
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(199);
         var6 = var1.getlocal(3).__call__(var2, var1.getlocal(6));
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
         var1.setline(200);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("unicode")).__not__().__nonzero__()) {
            var1.setline(201);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2));
         }

         var1.setline(202);
         var6 = var1.getglobal("int").__call__(var2, var1.getlocal(8));
         var1.setlocal(8, var6);
         var3 = null;
         var1.setline(203);
         var6 = var1.getlocal(8);
         var10000 = var6._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(204);
            var6 = var1.getlocal(4).__getattr__("limit").__call__(var2)._add(var1.getlocal(8));
            var1.setlocal(8, var6);
            var3 = null;
         }

         var1.setline(205);
         var6 = var1.getlocal(8);
         var10000 = var6._gt(var1.getlocal(4).__getattr__("limit").__call__(var2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(206);
            throw Py.makeException(var1.getglobal("IndexError").__call__(var2));
         }

         var1.setline(207);
         var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(7));
         var1.setline(208);
         var1.getlocal(4).__getattr__("position").__call__(var2, var1.getlocal(8));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _process_incomplete_decode$22(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyObject var3 = var1.getlocal(3).__getattr__("position").__call__(var2);
      PyObject var10000 = var3._lt(var1.getlocal(3).__getattr__("limit").__call__(var2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(213);
         var10000 = var1.getglobal("UnicodeDecodeError");
         PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(3).__getattr__("position").__call__(var2), var1.getlocal(3).__getattr__("limit").__call__(var2), PyString.fromInterned("illegal multibyte sequence")};
         var3 = var10000.__call__(var2, var6);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(219);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(5));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(220);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("unicode")).__not__().__nonzero__()) {
            var1.setline(221);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2));
         }

         var1.setline(222);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(223);
         var3 = var1.getlocal(7);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(224);
            var3 = var1.getlocal(3).__getattr__("limit").__call__(var2)._add(var1.getlocal(7));
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(225);
         var3 = var1.getlocal(7);
         var10000 = var3._gt(var1.getlocal(3).__getattr__("limit").__call__(var2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(226);
            throw Py.makeException(var1.getglobal("IndexError").__call__(var2));
         }

         var1.setline(227);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
         var1.setline(228);
         var1.getlocal(3).__getattr__("position").__call__(var2, var1.getlocal(7));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_unicode$23(PyFrame var1, ThreadState var2) {
      var1.setline(232);
      PyObject var3 = var1.getlocal(0).__getattr__("subSequence").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1).__getattr__("length").__call__(var2)).__getattr__("toString").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _process_encode_errors$24(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      if (var1.getlocal(2).__getattr__("isError").__call__(var2).__nonzero__()) {
         var1.setline(237);
         PyObject var10000 = var1.getglobal("UnicodeEncodeError");
         PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(4).__getattr__("position").__call__(var2), var1.getlocal(4).__getattr__("position").__call__(var2)._add(var1.getlocal(2).__getattr__("length").__call__(var2)), PyString.fromInterned("illegal multibyte sequence")};
         PyObject var6 = var10000.__call__(var2, var3);
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(243);
         var6 = var1.getlocal(3).__call__(var2, var1.getlocal(6));
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
         var1.setline(244);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("unicode")).__not__().__nonzero__()) {
            var1.setline(245);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2));
         }

         var1.setline(246);
         var6 = var1.getglobal("int").__call__(var2, var1.getlocal(8));
         var1.setlocal(8, var6);
         var3 = null;
         var1.setline(247);
         var6 = var1.getlocal(8);
         var10000 = var6._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(248);
            var6 = var1.getlocal(4).__getattr__("limit").__call__(var2)._add(var1.getlocal(8));
            var1.setlocal(8, var6);
            var3 = null;
         }

         var1.setline(249);
         var6 = var1.getlocal(8);
         var10000 = var6._gt(var1.getlocal(4).__getattr__("limit").__call__(var2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(250);
            throw Py.makeException(var1.getglobal("IndexError").__call__(var2));
         }

         var1.setline(251);
         var1.getlocal(5).__getattr__("write").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(7)));
         var1.setline(252);
         var1.getlocal(4).__getattr__("position").__call__(var2, var1.getlocal(8));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public _java$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"encoding", "supported", "charset", "entry"};
      _java_factory$1 = Py.newCode(1, var2, var1, "_java_factory", 23, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Codec$2 = Py.newCode(0, var2, var1, "Codec", 47, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "encoding"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 49, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "final", "error_function", "input_buffer", "decoder", "output_buffer", "builder", "result", "pos"};
      decode$4 = Py.newCode(4, var2, var1, "decode", 52, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors", "error_function", "input_buffer", "encoder", "output_buffer", "builder", "result", "pos"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 72, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NonfinalCodec$6 = Py.newCode(0, var2, var1, "NonfinalCodec", 94, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      decode$7 = Py.newCode(3, var2, var1, "decode", 96, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$8 = Py.newCode(0, var2, var1, "IncrementalEncoder", 100, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors", "encoding"};
      __init__$9 = Py.newCode(3, var2, var1, "__init__", 102, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "error_function", "input_buffer", "builder", "result", "pos"};
      encode$10 = Py.newCode(3, var2, var1, "encode", 109, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$11 = Py.newCode(0, var2, var1, "IncrementalDecoder", 130, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "errors", "encoding"};
      __init__$12 = Py.newCode(3, var2, var1, "__init__", 132, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "final", "error_function", "input_array", "input_buffer", "builder", "result", "pos"};
      decode$13 = Py.newCode(3, var2, var1, "decode", 140, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$14 = Py.newCode(1, var2, var1, "reset", 163, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getstate$15 = Py.newCode(1, var2, var1, "getstate", 167, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "state", "_"};
      setstate$16 = Py.newCode(2, var2, var1, "setstate", 171, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$17 = Py.newCode(0, var2, var1, "StreamWriter", 177, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors", "encoding"};
      __init__$18 = Py.newCode(4, var2, var1, "__init__", 179, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamReader$19 = Py.newCode(0, var2, var1, "StreamReader", 184, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "errors", "encoding"};
      __init__$20 = Py.newCode(4, var2, var1, "__init__", 186, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "input", "result", "error_function", "input_buffer", "builder", "e", "replacement", "pos"};
      _process_decode_errors$21 = Py.newCode(6, var2, var1, "_process_decode_errors", 191, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "input", "error_function", "input_buffer", "builder", "e", "replacement", "pos"};
      _process_incomplete_decode$22 = Py.newCode(5, var2, var1, "_process_incomplete_decode", 211, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"input_buffer", "result"};
      _get_unicode$23 = Py.newCode(2, var2, var1, "_get_unicode", 231, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "input", "result", "error_function", "input_buffer", "builder", "e", "replacement", "pos"};
      _process_encode_errors$24 = Py.newCode(6, var2, var1, "_process_encode_errors", 235, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _java$py("encodings/_java$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_java$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._java_factory$1(var2, var3);
         case 2:
            return this.Codec$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.decode$4(var2, var3);
         case 5:
            return this.encode$5(var2, var3);
         case 6:
            return this.NonfinalCodec$6(var2, var3);
         case 7:
            return this.decode$7(var2, var3);
         case 8:
            return this.IncrementalEncoder$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.encode$10(var2, var3);
         case 11:
            return this.IncrementalDecoder$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.decode$13(var2, var3);
         case 14:
            return this.reset$14(var2, var3);
         case 15:
            return this.getstate$15(var2, var3);
         case 16:
            return this.setstate$16(var2, var3);
         case 17:
            return this.StreamWriter$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.StreamReader$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this._process_decode_errors$21(var2, var3);
         case 22:
            return this._process_incomplete_decode$22(var2, var3);
         case 23:
            return this._get_unicode$23(var2, var3);
         case 24:
            return this._process_encode_errors$24(var2, var3);
         default:
            return null;
      }
   }
}
