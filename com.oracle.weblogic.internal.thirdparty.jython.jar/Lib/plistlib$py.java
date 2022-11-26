import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("plistlib.py")
public class plistlib$py extends PyFunctionTable implements PyRunnable {
   static plistlib$py self;
   static final PyCode f$0;
   static final PyCode readPlist$1;
   static final PyCode writePlist$2;
   static final PyCode readPlistFromString$3;
   static final PyCode writePlistToString$4;
   static final PyCode readPlistFromResource$5;
   static final PyCode writePlistToResource$6;
   static final PyCode DumbXMLWriter$7;
   static final PyCode __init__$8;
   static final PyCode beginElement$9;
   static final PyCode endElement$10;
   static final PyCode simpleElement$11;
   static final PyCode writeln$12;
   static final PyCode _dateFromString$13;
   static final PyCode _dateToString$14;
   static final PyCode _escapeAndEncode$15;
   static final PyCode PlistWriter$16;
   static final PyCode __init__$17;
   static final PyCode writeValue$18;
   static final PyCode writeData$19;
   static final PyCode writeDict$20;
   static final PyCode writeArray$21;
   static final PyCode _InternalDict$22;
   static final PyCode __getattr__$23;
   static final PyCode __setattr__$24;
   static final PyCode __delattr__$25;
   static final PyCode Dict$26;
   static final PyCode __init__$27;
   static final PyCode Plist$28;
   static final PyCode __init__$29;
   static final PyCode fromFile$30;
   static final PyCode write$31;
   static final PyCode _encodeBase64$32;
   static final PyCode Data$33;
   static final PyCode __init__$34;
   static final PyCode fromBase64$35;
   static final PyCode asBase64$36;
   static final PyCode __cmp__$37;
   static final PyCode __repr__$38;
   static final PyCode PlistParser$39;
   static final PyCode __init__$40;
   static final PyCode parse$41;
   static final PyCode handleBeginElement$42;
   static final PyCode handleEndElement$43;
   static final PyCode handleData$44;
   static final PyCode addObject$45;
   static final PyCode getData$46;
   static final PyCode begin_dict$47;
   static final PyCode end_dict$48;
   static final PyCode end_key$49;
   static final PyCode begin_array$50;
   static final PyCode end_array$51;
   static final PyCode end_true$52;
   static final PyCode end_false$53;
   static final PyCode end_integer$54;
   static final PyCode end_real$55;
   static final PyCode end_string$56;
   static final PyCode end_data$57;
   static final PyCode end_date$58;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("plistlib.py -- a tool to generate and parse MacOSX .plist files.\n\nThe PropertyList (.plist) file format is a simple XML pickle supporting\nbasic object types, like dictionaries, lists, numbers and strings.\nUsually the top level object is a dictionary.\n\nTo write out a plist file, use the writePlist(rootObject, pathOrFile)\nfunction. 'rootObject' is the top level object, 'pathOrFile' is a\nfilename or a (writable) file object.\n\nTo parse a plist from a file, use the readPlist(pathOrFile) function,\nwith a file name or a (readable) file object as the only argument. It\nreturns the top level object (again, usually a dictionary).\n\nTo work with plist data in strings, you can use readPlistFromString()\nand writePlistToString().\n\nValues can be strings, integers, floats, booleans, tuples, lists,\ndictionaries, Data or datetime.datetime objects. String values (including\ndictionary keys) may be unicode strings -- they will be written out as\nUTF-8.\n\nThe <data> plist type is supported through the Data class. This is a\nthin wrapper around a Python string.\n\nGenerate Plist example:\n\n    pl = dict(\n        aString=\"Doodah\",\n        aList=[\"A\", \"B\", 12, 32.1, [1, 2, 3]],\n        aFloat=0.1,\n        anInt=728,\n        aDict=dict(\n            anotherString=\"<hello & hi there!>\",\n            aUnicodeValue=u'M\\xe4ssig, Ma\\xdf',\n            aTrueValue=True,\n            aFalseValue=False,\n        ),\n        someData=Data(\"<binary gunk>\"),\n        someMoreData=Data(\"<lots of binary gunk>\" * 10),\n        aDate=datetime.datetime.fromtimestamp(time.mktime(time.gmtime())),\n    )\n    # unicode keys are possible, but a little awkward to use:\n    pl[u'\\xc5benraa'] = \"That was a unicode key.\"\n    writePlist(pl, fileName)\n\nParse Plist example:\n\n    pl = readPlist(pathOrFile)\n    print pl[\"aKey\"]\n"));
      var1.setline(51);
      PyString.fromInterned("plistlib.py -- a tool to generate and parse MacOSX .plist files.\n\nThe PropertyList (.plist) file format is a simple XML pickle supporting\nbasic object types, like dictionaries, lists, numbers and strings.\nUsually the top level object is a dictionary.\n\nTo write out a plist file, use the writePlist(rootObject, pathOrFile)\nfunction. 'rootObject' is the top level object, 'pathOrFile' is a\nfilename or a (writable) file object.\n\nTo parse a plist from a file, use the readPlist(pathOrFile) function,\nwith a file name or a (readable) file object as the only argument. It\nreturns the top level object (again, usually a dictionary).\n\nTo work with plist data in strings, you can use readPlistFromString()\nand writePlistToString().\n\nValues can be strings, integers, floats, booleans, tuples, lists,\ndictionaries, Data or datetime.datetime objects. String values (including\ndictionary keys) may be unicode strings -- they will be written out as\nUTF-8.\n\nThe <data> plist type is supported through the Data class. This is a\nthin wrapper around a Python string.\n\nGenerate Plist example:\n\n    pl = dict(\n        aString=\"Doodah\",\n        aList=[\"A\", \"B\", 12, 32.1, [1, 2, 3]],\n        aFloat=0.1,\n        anInt=728,\n        aDict=dict(\n            anotherString=\"<hello & hi there!>\",\n            aUnicodeValue=u'M\\xe4ssig, Ma\\xdf',\n            aTrueValue=True,\n            aFalseValue=False,\n        ),\n        someData=Data(\"<binary gunk>\"),\n        someMoreData=Data(\"<lots of binary gunk>\" * 10),\n        aDate=datetime.datetime.fromtimestamp(time.mktime(time.gmtime())),\n    )\n    # unicode keys are possible, but a little awkward to use:\n    pl[u'\\xc5benraa'] = \"That was a unicode key.\"\n    writePlist(pl, fileName)\n\nParse Plist example:\n\n    pl = readPlist(pathOrFile)\n    print pl[\"aKey\"]\n");
      var1.setline(54);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("readPlist"), PyString.fromInterned("writePlist"), PyString.fromInterned("readPlistFromString"), PyString.fromInterned("writePlistToString"), PyString.fromInterned("readPlistFromResource"), PyString.fromInterned("writePlistToResource"), PyString.fromInterned("Plist"), PyString.fromInterned("Data"), PyString.fromInterned("Dict")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(61);
      PyObject var5 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var5);
      var3 = null;
      var1.setline(62);
      var5 = imp.importOne("datetime", var1, -1);
      var1.setlocal("datetime", var5);
      var3 = null;
      var1.setline(63);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(64);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(65);
      var5 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var5);
      var3 = null;
      var1.setline(68);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, readPlist$1, PyString.fromInterned("Read a .plist file. 'pathOrFile' may either be a file name or a\n    (readable) file object. Return the unpacked root object (which\n    usually is a dictionary).\n    "));
      var1.setlocal("readPlist", var8);
      var3 = null;
      var1.setline(84);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, writePlist$2, PyString.fromInterned("Write 'rootObject' to a .plist file. 'pathOrFile' may either be a\n    file name or a (writable) file object.\n    "));
      var1.setlocal("writePlist", var8);
      var3 = null;
      var1.setline(100);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, readPlistFromString$3, PyString.fromInterned("Read a plist data from a string. Return the root object.\n    "));
      var1.setlocal("readPlistFromString", var8);
      var3 = null;
      var1.setline(106);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, writePlistToString$4, PyString.fromInterned("Return 'rootObject' as a plist-formatted string.\n    "));
      var1.setlocal("writePlistToString", var8);
      var3 = null;
      var1.setline(114);
      var7 = new PyObject[]{PyString.fromInterned("plst"), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, readPlistFromResource$5, PyString.fromInterned("Read plst resource from the resource fork of path.\n    "));
      var1.setlocal("readPlistFromResource", var8);
      var3 = null;
      var1.setline(130);
      var7 = new PyObject[]{PyString.fromInterned("plst"), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, writePlistToResource$6, PyString.fromInterned("Write 'rootObject' as a plst resource to the resource fork of path.\n    "));
      var1.setlocal("writePlistToResource", var8);
      var3 = null;
      var1.setline(151);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("DumbXMLWriter", var7, DumbXMLWriter$7);
      var1.setlocal("DumbXMLWriter", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(187);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?P<year>\\d\\d\\d\\d)(?:-(?P<month>\\d\\d)(?:-(?P<day>\\d\\d)(?:T(?P<hour>\\d\\d)(?::(?P<minute>\\d\\d)(?::(?P<second>\\d\\d))?)?)?)?)?Z"));
      var1.setlocal("_dateParser", var5);
      var3 = null;
      var1.setline(189);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _dateFromString$13, (PyObject)null);
      var1.setlocal("_dateFromString", var8);
      var3 = null;
      var1.setline(200);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _dateToString$14, (PyObject)null);
      var1.setlocal("_dateToString", var8);
      var3 = null;
      var1.setline(208);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\x00\\x01\\x02\\x03\\x04\\x05\\x06\\x07\\x08\\x0b\\x0c\\x0e\\x0f\\x10\\x11\\x12\\x13\\x14\\x15\\x16\\x17\\x18\\x19\\x1a\\x1b\\x1c\\x1d\\x1e\\x1f]"));
      var1.setlocal("_controlCharPat", var5);
      var3 = null;
      var1.setline(212);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _escapeAndEncode$15, (PyObject)null);
      var1.setlocal("_escapeAndEncode", var8);
      var3 = null;
      var1.setline(225);
      PyString var9 = PyString.fromInterned("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
      var1.setlocal("PLISTHEADER", var9);
      var3 = null;
      var1.setline(230);
      var7 = new PyObject[]{var1.getname("DumbXMLWriter")};
      var4 = Py.makeClass("PlistWriter", var7, PlistWriter$16);
      var1.setlocal("PlistWriter", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(291);
      var7 = new PyObject[]{var1.getname("dict")};
      var4 = Py.makeClass("_InternalDict", var7, _InternalDict$22);
      var1.setlocal("_InternalDict", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(322);
      var7 = new PyObject[]{var1.getname("_InternalDict")};
      var4 = Py.makeClass("Dict", var7, Dict$26);
      var1.setlocal("Dict", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(331);
      var7 = new PyObject[]{var1.getname("_InternalDict")};
      var4 = Py.makeClass("Plist", var7, Plist$28);
      var1.setlocal("Plist", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(356);
      var7 = new PyObject[]{Py.newInteger(76)};
      var8 = new PyFunction(var1.f_globals, var7, _encodeBase64$32, (PyObject)null);
      var1.setlocal("_encodeBase64", var8);
      var3 = null;
      var1.setline(365);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Data", var7, Data$33);
      var1.setlocal("Data", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(393);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("PlistParser", var7, PlistParser$39);
      var1.setlocal("PlistParser", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readPlist$1(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Read a .plist file. 'pathOrFile' may either be a file name or a\n    (readable) file object. Return the unpacked root object (which\n    usually is a dictionary).\n    ");
      var1.setline(73);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(74);
      PyObject var4;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__nonzero__()) {
         var1.setline(75);
         var4 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var4);
         var3 = null;
         var1.setline(76);
         var3 = Py.newInteger(1);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(77);
      var4 = var1.getglobal("PlistParser").__call__(var2);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(78);
      var4 = var1.getlocal(2).__getattr__("parse").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(79);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(80);
         var1.getlocal(0).__getattr__("close").__call__(var2);
      }

      var1.setline(81);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject writePlist$2(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyString.fromInterned("Write 'rootObject' to a .plist file. 'pathOrFile' may either be a\n    file name or a (writable) file object.\n    ");
      var1.setline(88);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(89);
      PyObject var4;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__nonzero__()) {
         var1.setline(90);
         var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(1, var4);
         var3 = null;
         var1.setline(91);
         var3 = Py.newInteger(1);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(92);
      var4 = var1.getglobal("PlistWriter").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(93);
      var1.getlocal(3).__getattr__("writeln").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<plist version=\"1.0\">"));
      var1.setline(94);
      var1.getlocal(3).__getattr__("writeValue").__call__(var2, var1.getlocal(0));
      var1.setline(95);
      var1.getlocal(3).__getattr__("writeln").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</plist>"));
      var1.setline(96);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(97);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readPlistFromString$3(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyString.fromInterned("Read a plist data from a string. Return the root object.\n    ");
      var1.setline(103);
      PyObject var3 = var1.getglobal("readPlist").__call__(var2, var1.getglobal("StringIO").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writePlistToString$4(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyString.fromInterned("Return 'rootObject' as a plist-formatted string.\n    ");
      var1.setline(109);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(110);
      var1.getglobal("writePlist").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(111);
      var3 = var1.getlocal(1).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readPlistFromResource$5(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyString.fromInterned("Read plst resource from the resource fork of path.\n    ");
      var1.setline(117);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warnpy3k");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("In 3.x, readPlistFromResource is removed."), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(119);
      String[] var5 = new String[]{"FSRef", "FSGetResourceForkName"};
      var3 = imp.importFrom("Carbon.File", var5, var1, -1);
      PyObject var6 = var3[0];
      var1.setlocal(3, var6);
      var4 = null;
      var6 = var3[1];
      var1.setlocal(4, var6);
      var4 = null;
      var1.setline(120);
      var5 = new String[]{"fsRdPerm"};
      var3 = imp.importFrom("Carbon.Files", var5, var1, -1);
      var6 = var3[0];
      var1.setlocal(5, var6);
      var4 = null;
      var1.setline(121);
      var5 = new String[]{"Res"};
      var3 = imp.importFrom("Carbon", var5, var1, -1);
      var6 = var3[0];
      var1.setlocal(6, var6);
      var4 = null;
      var1.setline(122);
      PyObject var7 = var1.getlocal(3).__call__(var2, var1.getlocal(0));
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(123);
      var7 = var1.getlocal(6).__getattr__("FSOpenResourceFile").__call__(var2, var1.getlocal(7), var1.getlocal(4).__call__(var2), var1.getlocal(5));
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(124);
      var1.getlocal(6).__getattr__("UseResFile").__call__(var2, var1.getlocal(8));
      var1.setline(125);
      var7 = var1.getlocal(6).__getattr__("Get1Resource").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__getattr__("data");
      var1.setlocal(9, var7);
      var3 = null;
      var1.setline(126);
      var1.getlocal(6).__getattr__("CloseResFile").__call__(var2, var1.getlocal(8));
      var1.setline(127);
      var7 = var1.getglobal("readPlistFromString").__call__(var2, var1.getlocal(9));
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject writePlistToResource$6(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyString.fromInterned("Write 'rootObject' as a plst resource to the resource fork of path.\n    ");
      var1.setline(133);
      PyObject var10000 = var1.getglobal("warnings").__getattr__("warnpy3k");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("In 3.x, writePlistToResource is removed."), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(134);
      String[] var6 = new String[]{"FSRef", "FSGetResourceForkName"};
      var3 = imp.importFrom("Carbon.File", var6, var1, -1);
      PyObject var7 = var3[0];
      var1.setlocal(4, var7);
      var4 = null;
      var7 = var3[1];
      var1.setlocal(5, var7);
      var4 = null;
      var1.setline(135);
      var6 = new String[]{"fsRdWrPerm"};
      var3 = imp.importFrom("Carbon.Files", var6, var1, -1);
      var7 = var3[0];
      var1.setlocal(6, var7);
      var4 = null;
      var1.setline(136);
      var6 = new String[]{"Res"};
      var3 = imp.importFrom("Carbon", var6, var1, -1);
      var7 = var3[0];
      var1.setlocal(7, var7);
      var4 = null;
      var1.setline(137);
      PyObject var8 = var1.getglobal("writePlistToString").__call__(var2, var1.getlocal(0));
      var1.setlocal(8, var8);
      var3 = null;
      var1.setline(138);
      var8 = var1.getlocal(4).__call__(var2, var1.getlocal(1));
      var1.setlocal(9, var8);
      var3 = null;
      var1.setline(139);
      var8 = var1.getlocal(7).__getattr__("FSOpenResourceFile").__call__(var2, var1.getlocal(9), var1.getlocal(5).__call__(var2), var1.getlocal(6));
      var1.setlocal(10, var8);
      var3 = null;
      var1.setline(140);
      var1.getlocal(7).__getattr__("UseResFile").__call__(var2, var1.getlocal(10));

      try {
         var1.setline(142);
         var1.getlocal(7).__getattr__("Get1Resource").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__getattr__("RemoveResource").__call__(var2);
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getlocal(7).__getattr__("Error"))) {
            throw var9;
         }

         var1.setline(144);
      }

      var1.setline(145);
      var8 = var1.getlocal(7).__getattr__("Resource").__call__(var2, var1.getlocal(8));
      var1.setlocal(11, var8);
      var3 = null;
      var1.setline(146);
      var1.getlocal(11).__getattr__("AddResource").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned(""));
      var1.setline(147);
      var1.getlocal(11).__getattr__("WriteResource").__call__(var2);
      var1.setline(148);
      var1.getlocal(7).__getattr__("CloseResFile").__call__(var2, var1.getlocal(10));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DumbXMLWriter$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(153);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("\t")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, beginElement$9, (PyObject)null);
      var1.setlocal("beginElement", var4);
      var3 = null;
      var1.setline(164);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, endElement$10, (PyObject)null);
      var1.setlocal("endElement", var4);
      var3 = null;
      var1.setline(170);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, simpleElement$11, (PyObject)null);
      var1.setlocal("simpleElement", var4);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeln$12, (PyObject)null);
      var1.setlocal("writeln", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(155);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var4);
      var3 = null;
      var1.setline(156);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("indentLevel", var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("indent", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject beginElement$9(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(161);
      var1.getlocal(0).__getattr__("writeln").__call__(var2, PyString.fromInterned("<%s>")._mod(var1.getlocal(1)));
      var1.setline(162);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "indentLevel";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endElement$10(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("indentLevel");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(166);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(167);
      var10000 = var1.getlocal(0);
      String var6 = "indentLevel";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._isub(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.setline(168);
      var1.getlocal(0).__getattr__("writeln").__call__(var2, PyString.fromInterned("</%s>")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject simpleElement$11(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(172);
         var3 = var1.getglobal("_escapeAndEncode").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(173);
         var1.getlocal(0).__getattr__("writeln").__call__(var2, PyString.fromInterned("<%s>%s</%s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(1)})));
      } else {
         var1.setline(175);
         var1.getlocal(0).__getattr__("writeln").__call__(var2, PyString.fromInterned("<%s/>")._mod(var1.getlocal(1)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writeln$12(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(179);
         var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("indentLevel")._mul(var1.getlocal(0).__getattr__("indent"))._add(var1.getlocal(1))._add(PyString.fromInterned("\n")));
      } else {
         var1.setline(181);
         var1.getlocal(0).__getattr__("file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _dateFromString$13(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("year"), PyString.fromInterned("month"), PyString.fromInterned("day"), PyString.fromInterned("hour"), PyString.fromInterned("minute"), PyString.fromInterned("second")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(191);
      PyObject var6 = var1.getglobal("_dateParser").__getattr__("match").__call__(var2, var1.getlocal(0)).__getattr__("groupdict").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(192);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(193);
      var6 = var1.getlocal(1).__iter__();

      PyObject var10000;
      while(true) {
         var1.setline(193);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(4, var4);
         var1.setline(194);
         PyObject var5 = var1.getlocal(2).__getitem__(var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(195);
         var5 = var1.getlocal(5);
         var10000 = var5._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(197);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(5)));
      }

      var1.setline(198);
      var10000 = var1.getglobal("datetime").__getattr__("datetime");
      PyObject[] var9 = Py.EmptyObjects;
      String[] var8 = new String[0];
      var10000 = var10000._callextra(var9, var8, var1.getlocal(3), (PyObject)null);
      var3 = null;
      var6 = var10000;
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _dateToString$14(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = PyString.fromInterned("%04d-%02d-%02dT%02d:%02d:%02dZ")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month"), var1.getlocal(0).__getattr__("day"), var1.getlocal(0).__getattr__("hour"), var1.getlocal(0).__getattr__("minute"), var1.getlocal(0).__getattr__("second")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _escapeAndEncode$15(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyObject var3 = var1.getglobal("_controlCharPat").__getattr__("search").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(215);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("strings can't contains control characters; use plistlib.Data instead")));
      } else {
         var1.setline(217);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"), (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(218);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r"), (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(219);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("&"), (PyObject)PyString.fromInterned("&amp;"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(220);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)PyString.fromInterned("&lt;"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(221);
         var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)PyString.fromInterned("&gt;"));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(222);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject PlistWriter$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(232);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("\t"), Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(237);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeValue$18, (PyObject)null);
      var1.setlocal("writeValue", var4);
      var3 = null;
      var1.setline(262);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeData$19, (PyObject)null);
      var1.setlocal("writeData", var4);
      var3 = null;
      var1.setline(273);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeDict$20, (PyObject)null);
      var1.setlocal("writeDict", var4);
      var3 = null;
      var1.setline(284);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeArray$21, (PyObject)null);
      var1.setlocal("writeArray", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(234);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getglobal("PLISTHEADER"));
      }

      var1.setline(235);
      var1.getglobal("DumbXMLWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writeValue$18(PyFrame var1, ThreadState var2) {
      var1.setline(238);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__nonzero__()) {
         var1.setline(239);
         var1.getlocal(0).__getattr__("simpleElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("string"), (PyObject)var1.getlocal(1));
      } else {
         var1.setline(240);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bool")).__nonzero__()) {
            var1.setline(243);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(244);
               var1.getlocal(0).__getattr__("simpleElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("true"));
            } else {
               var1.setline(246);
               var1.getlocal(0).__getattr__("simpleElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("false"));
            }
         } else {
            var1.setline(247);
            if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
               var1.setline(248);
               var1.getlocal(0).__getattr__("simpleElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("integer"), (PyObject)PyString.fromInterned("%d")._mod(var1.getlocal(1)));
            } else {
               var1.setline(249);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__nonzero__()) {
                  var1.setline(250);
                  var1.getlocal(0).__getattr__("simpleElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("real"), (PyObject)var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
               } else {
                  var1.setline(251);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("dict")).__nonzero__()) {
                     var1.setline(252);
                     var1.getlocal(0).__getattr__("writeDict").__call__(var2, var1.getlocal(1));
                  } else {
                     var1.setline(253);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Data")).__nonzero__()) {
                        var1.setline(254);
                        var1.getlocal(0).__getattr__("writeData").__call__(var2, var1.getlocal(1));
                     } else {
                        var1.setline(255);
                        if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime").__getattr__("datetime")).__nonzero__()) {
                           var1.setline(256);
                           var1.getlocal(0).__getattr__("simpleElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("date"), (PyObject)var1.getglobal("_dateToString").__call__(var2, var1.getlocal(1)));
                        } else {
                           var1.setline(257);
                           if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("tuple"), var1.getglobal("list")}))).__nonzero__()) {
                              var1.setline(260);
                              throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("unsuported type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
                           }

                           var1.setline(258);
                           var1.getlocal(0).__getattr__("writeArray").__call__(var2, var1.getlocal(1));
                        }
                     }
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writeData$19(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      var1.getlocal(0).__getattr__("beginElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data"));
      var1.setline(264);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "indentLevel";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._isub(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(265);
      PyObject var6 = Py.newInteger(76)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("indent").__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\t"), (PyObject)PyString.fromInterned(" ")._mul(Py.newInteger(8)))._mul(var1.getlocal(0).__getattr__("indentLevel"))));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(267);
      var6 = var1.getlocal(1).__getattr__("asBase64").__call__(var2, var1.getlocal(2)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

      while(true) {
         var1.setline(267);
         var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(270);
            var10000 = var1.getlocal(0);
            var3 = "indentLevel";
            var4 = var10000;
            var5 = var4.__getattr__(var3);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var3, var5);
            var1.setline(271);
            var1.getlocal(0).__getattr__("endElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("data"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(268);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(269);
            var1.getlocal(0).__getattr__("writeln").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject writeDict$20(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      var1.getlocal(0).__getattr__("beginElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dict"));
      var1.setline(275);
      PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(276);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(277);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(277);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(282);
            var1.getlocal(0).__getattr__("endElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dict"));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(278);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__not__().__nonzero__()) {
            var1.setline(279);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("keys must be strings")));
         }

         var1.setline(280);
         var1.getlocal(0).__getattr__("simpleElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("key"), (PyObject)var1.getlocal(3));
         var1.setline(281);
         var1.getlocal(0).__getattr__("writeValue").__call__(var2, var1.getlocal(4));
      }
   }

   public PyObject writeArray$21(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      var1.getlocal(0).__getattr__("beginElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("array"));
      var1.setline(286);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(286);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(288);
            var1.getlocal(0).__getattr__("endElement").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("array"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(287);
         var1.getlocal(0).__getattr__("writeValue").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject _InternalDict$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(297);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __getattr__$23, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setattr__$24, (PyObject)null);
      var1.setlocal("__setattr__", var4);
      var3 = null;
      var1.setline(313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delattr__$25, (PyObject)null);
      var1.setlocal("__delattr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __getattr__$23(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var6;
      try {
         var1.setline(299);
         var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(301);
            throw Py.makeException(var1.getglobal("AttributeError"), var1.getlocal(1));
         }

         throw var3;
      }

      var1.setline(302);
      String[] var7 = new String[]{"warn"};
      PyObject[] var8 = imp.importFrom("warnings", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(303);
      var1.getlocal(3).__call__((ThreadState)var2, PyString.fromInterned("Attribute access from plist dicts is deprecated, use d[key] notation instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(305);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject __setattr__$24(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      String[] var3 = new String[]{"warn"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(309);
      var1.getlocal(3).__call__((ThreadState)var2, PyString.fromInterned("Attribute access from plist dicts is deprecated, use d[key] notation instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(311);
      PyObject var6 = var1.getlocal(2);
      var1.getlocal(0).__setitem__(var1.getlocal(1), var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delattr__$25(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(315);
         var1.getlocal(0).__delitem__(var1.getlocal(1));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(317);
            throw Py.makeException(var1.getglobal("AttributeError"), var1.getlocal(1));
         }

         throw var3;
      }

      var1.setline(318);
      String[] var6 = new String[]{"warn"};
      PyObject[] var7 = imp.importFrom("warnings", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(319);
      var1.getlocal(2).__call__((ThreadState)var2, PyString.fromInterned("Attribute access from plist dicts is deprecated, use d[key] notation instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Dict$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(324);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$27, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$27(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      String[] var3 = new String[]{"warn"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(326);
      var1.getlocal(2).__call__((ThreadState)var2, PyString.fromInterned("The plistlib.Dict class is deprecated, use builtin dict instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(328);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("Dict"), var1.getlocal(0)).__getattr__("__init__");
      var5 = Py.EmptyObjects;
      String[] var6 = new String[0];
      var10000._callextra(var5, var6, (PyObject)null, var1.getlocal(1));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Plist$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class has been deprecated. Use readPlist() and writePlist()\n    functions instead, together with regular dict objects.\n    "));
      var1.setline(335);
      PyString.fromInterned("This class has been deprecated. Use readPlist() and writePlist()\n    functions instead, together with regular dict objects.\n    ");
      var1.setline(337);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$29, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(343);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fromFile$30, PyString.fromInterned("Deprecated. Use the readPlist() function instead."));
      var1.setlocal("fromFile", var4);
      var3 = null;
      var1.setline(349);
      PyObject var5 = var1.getname("classmethod").__call__(var2, var1.getname("fromFile"));
      var1.setlocal("fromFile", var5);
      var3 = null;
      var1.setline(351);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$31, PyString.fromInterned("Deprecated. Use the writePlist() function instead."));
      var1.setlocal("write", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      String[] var3 = new String[]{"warn"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(339);
      var1.getlocal(2).__call__((ThreadState)var2, PyString.fromInterned("The Plist class is deprecated, use the readPlist() and writePlist() functions instead"), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(341);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("Plist"), var1.getlocal(0)).__getattr__("__init__");
      var5 = Py.EmptyObjects;
      String[] var6 = new String[0];
      var10000._callextra(var5, var6, (PyObject)null, var1.getlocal(1));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fromFile$30(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyString.fromInterned("Deprecated. Use the readPlist() function instead.");
      var1.setline(345);
      PyObject var3 = var1.getglobal("readPlist").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(346);
      var3 = var1.getlocal(0).__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(347);
      var1.getlocal(3).__getattr__("update").__call__(var2, var1.getlocal(2));
      var1.setline(348);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$31(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyString.fromInterned("Deprecated. Use the writePlist() function instead.");
      var1.setline(353);
      var1.getglobal("writePlist").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _encodeBase64$32(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyObject var3 = var1.getlocal(1)._floordiv(Py.newInteger(4))._mul(Py.newInteger(3));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(359);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(360);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(0)), (PyObject)var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(360);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(363);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(361);
         PyObject var5 = var1.getlocal(0).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(var1.getlocal(2)), (PyObject)null);
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(362);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("binascii").__getattr__("b2a_base64").__call__(var2, var1.getlocal(5)));
      }
   }

   public PyObject Data$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Wrapper for binary data."));
      var1.setline(367);
      PyString.fromInterned("Wrapper for binary data.");
      var1.setline(369);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$34, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(372);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fromBase64$35, (PyObject)null);
      var1.setlocal("fromBase64", var4);
      var3 = null;
      var1.setline(376);
      PyObject var5 = var1.getname("classmethod").__call__(var2, var1.getname("fromBase64"));
      var1.setlocal("fromBase64", var5);
      var3 = null;
      var1.setline(378);
      var3 = new PyObject[]{Py.newInteger(76)};
      var4 = new PyFunction(var1.f_globals, var3, asBase64$36, (PyObject)null);
      var1.setlocal("asBase64", var4);
      var3 = null;
      var1.setline(381);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$37, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(389);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$38, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$34(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("data", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fromBase64$35(PyFrame var1, ThreadState var2) {
      var1.setline(375);
      PyObject var3 = var1.getlocal(0).__call__(var2, var1.getglobal("binascii").__getattr__("a2b_base64").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject asBase64$36(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyObject var3 = var1.getglobal("_encodeBase64").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$37(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("__class__")).__nonzero__()) {
         var1.setline(383);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1).__getattr__("data"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(384);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(385);
            var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("data"), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(387);
            var3 = var1.getglobal("cmp").__call__(var2, var1.getglobal("id").__call__(var2, var1.getlocal(0)), var1.getglobal("id").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __repr__$38(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyObject var3 = PyString.fromInterned("%s(%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("data"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject PlistParser$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(395);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$40, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(400);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$41, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(409);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handleBeginElement$42, (PyObject)null);
      var1.setlocal("handleBeginElement", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handleEndElement$43, (PyObject)null);
      var1.setlocal("handleEndElement", var4);
      var3 = null;
      var1.setline(420);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, handleData$44, (PyObject)null);
      var1.setlocal("handleData", var4);
      var3 = null;
      var1.setline(423);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addObject$45, (PyObject)null);
      var1.setlocal("addObject", var4);
      var3 = null;
      var1.setline(433);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getData$46, (PyObject)null);
      var1.setlocal("getData", var4);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, begin_dict$47, (PyObject)null);
      var1.setlocal("begin_dict", var4);
      var3 = null;
      var1.setline(448);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_dict$48, (PyObject)null);
      var1.setlocal("end_dict", var4);
      var3 = null;
      var1.setline(451);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_key$49, (PyObject)null);
      var1.setlocal("end_key", var4);
      var3 = null;
      var1.setline(454);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, begin_array$50, (PyObject)null);
      var1.setlocal("begin_array", var4);
      var3 = null;
      var1.setline(458);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_array$51, (PyObject)null);
      var1.setlocal("end_array", var4);
      var3 = null;
      var1.setline(461);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_true$52, (PyObject)null);
      var1.setlocal("end_true", var4);
      var3 = null;
      var1.setline(463);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_false$53, (PyObject)null);
      var1.setlocal("end_false", var4);
      var3 = null;
      var1.setline(465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_integer$54, (PyObject)null);
      var1.setlocal("end_integer", var4);
      var3 = null;
      var1.setline(467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_real$55, (PyObject)null);
      var1.setlocal("end_real", var4);
      var3 = null;
      var1.setline(469);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_string$56, (PyObject)null);
      var1.setlocal("end_string", var4);
      var3 = null;
      var1.setline(471);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_data$57, (PyObject)null);
      var1.setlocal("end_data", var4);
      var3 = null;
      var1.setline(473);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_date$58, (PyObject)null);
      var1.setlocal("end_date", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$40(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stack", var3);
      var3 = null;
      var1.setline(397);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("currentKey", var4);
      var3 = null;
      var1.setline(398);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("root", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$41(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      String[] var3 = new String[]{"ParserCreate"};
      PyObject[] var5 = imp.importFrom("xml.parsers.expat", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(402);
      PyObject var6 = var1.getlocal(2).__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(403);
      var6 = var1.getlocal(0).__getattr__("handleBeginElement");
      var1.getlocal(3).__setattr__("StartElementHandler", var6);
      var3 = null;
      var1.setline(404);
      var6 = var1.getlocal(0).__getattr__("handleEndElement");
      var1.getlocal(3).__setattr__("EndElementHandler", var6);
      var3 = null;
      var1.setline(405);
      var6 = var1.getlocal(0).__getattr__("handleData");
      var1.getlocal(3).__setattr__("CharacterDataHandler", var6);
      var3 = null;
      var1.setline(406);
      var1.getlocal(3).__getattr__("ParseFile").__call__(var2, var1.getlocal(1));
      var1.setline(407);
      var6 = var1.getlocal(0).__getattr__("root");
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject handleBeginElement$42(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"data", var3);
      var3 = null;
      var1.setline(411);
      PyObject var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("begin_")._add(var1.getlocal(1)), var1.getglobal("None"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(412);
      var4 = var1.getlocal(3);
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(413);
         var1.getlocal(3).__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handleEndElement$43(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("end_")._add(var1.getlocal(1)), var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(417);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(418);
         var1.getlocal(2).__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handleData$44(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      var1.getlocal(0).__getattr__("data").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addObject$45(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      PyObject var3 = var1.getlocal(0).__getattr__("currentKey");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(425);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)).__setitem__(var1.getlocal(0).__getattr__("currentKey"), var3);
         var3 = null;
         var1.setline(426);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("currentKey", var3);
         var3 = null;
      } else {
         var1.setline(427);
         if (var1.getlocal(0).__getattr__("stack").__not__().__nonzero__()) {
            var1.setline(429);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("root", var3);
            var3 = null;
         } else {
            var1.setline(431);
            var1.getlocal(0).__getattr__("stack").__getitem__(Py.newInteger(-1)).__getattr__("append").__call__(var2, var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getData$46(PyFrame var1, ThreadState var2) {
      var1.setline(434);
      PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("data"));
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(436);
         var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         var1.setlocal(1, var3);
         var3 = null;
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("UnicodeError"))) {
            throw var5;
         }

         var1.setline(438);
      }

      var1.setline(439);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"data", var6);
      var3 = null;
      var1.setline(440);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject begin_dict$47(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyObject var3 = var1.getglobal("_InternalDict").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(446);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getlocal(2));
      var1.setline(447);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_dict$48(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_key$49(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyObject var3 = var1.getlocal(0).__getattr__("getData").__call__(var2);
      var1.getlocal(0).__setattr__("currentKey", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject begin_array$50(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(456);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getlocal(2));
      var1.setline(457);
      var1.getlocal(0).__getattr__("stack").__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_array$51(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      var1.getlocal(0).__getattr__("stack").__getattr__("pop").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_true$52(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getglobal("True"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_false$53(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getglobal("False"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_integer$54(PyFrame var1, ThreadState var2) {
      var1.setline(466);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("getData").__call__(var2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_real$55(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0).__getattr__("getData").__call__(var2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_string$56(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getlocal(0).__getattr__("getData").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_data$57(PyFrame var1, ThreadState var2) {
      var1.setline(472);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getglobal("Data").__getattr__("fromBase64").__call__(var2, var1.getlocal(0).__getattr__("getData").__call__(var2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_date$58(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      var1.getlocal(0).__getattr__("addObject").__call__(var2, var1.getglobal("_dateFromString").__call__(var2, var1.getlocal(0).__getattr__("getData").__call__(var2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public plistlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"pathOrFile", "didOpen", "p", "rootObject"};
      readPlist$1 = Py.newCode(1, var2, var1, "readPlist", 68, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"rootObject", "pathOrFile", "didOpen", "writer"};
      writePlist$2 = Py.newCode(2, var2, var1, "writePlist", 84, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data"};
      readPlistFromString$3 = Py.newCode(1, var2, var1, "readPlistFromString", 100, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"rootObject", "f"};
      writePlistToString$4 = Py.newCode(1, var2, var1, "writePlistToString", 106, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "restype", "resid", "FSRef", "FSGetResourceForkName", "fsRdPerm", "Res", "fsRef", "resNum", "plistData"};
      readPlistFromResource$5 = Py.newCode(3, var2, var1, "readPlistFromResource", 114, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"rootObject", "path", "restype", "resid", "FSRef", "FSGetResourceForkName", "fsRdWrPerm", "Res", "plistData", "fsRef", "resNum", "res"};
      writePlistToResource$6 = Py.newCode(4, var2, var1, "writePlistToResource", 130, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DumbXMLWriter$7 = Py.newCode(0, var2, var1, "DumbXMLWriter", 151, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "indentLevel", "indent"};
      __init__$8 = Py.newCode(4, var2, var1, "__init__", 153, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      beginElement$9 = Py.newCode(2, var2, var1, "beginElement", 159, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element"};
      endElement$10 = Py.newCode(2, var2, var1, "endElement", 164, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "value"};
      simpleElement$11 = Py.newCode(3, var2, var1, "simpleElement", 170, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      writeln$12 = Py.newCode(2, var2, var1, "writeln", 177, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "order", "gd", "lst", "key", "val"};
      _dateFromString$13 = Py.newCode(1, var2, var1, "_dateFromString", 189, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"d"};
      _dateToString$14 = Py.newCode(1, var2, var1, "_dateToString", 200, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "m"};
      _escapeAndEncode$15 = Py.newCode(1, var2, var1, "_escapeAndEncode", 212, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PlistWriter$16 = Py.newCode(0, var2, var1, "PlistWriter", 230, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "indentLevel", "indent", "writeHeader"};
      __init__$17 = Py.newCode(5, var2, var1, "__init__", 232, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      writeValue$18 = Py.newCode(2, var2, var1, "writeValue", 237, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "maxlinelength", "line"};
      writeData$19 = Py.newCode(2, var2, var1, "writeData", 262, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d", "items", "key", "value"};
      writeDict$20 = Py.newCode(2, var2, var1, "writeDict", 273, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "array", "value"};
      writeArray$21 = Py.newCode(2, var2, var1, "writeArray", 284, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _InternalDict$22 = Py.newCode(0, var2, var1, "_InternalDict", 291, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "attr", "value", "warn"};
      __getattr__$23 = Py.newCode(2, var2, var1, "__getattr__", 297, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr", "value", "warn"};
      __setattr__$24 = Py.newCode(3, var2, var1, "__setattr__", 307, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr", "warn"};
      __delattr__$25 = Py.newCode(2, var2, var1, "__delattr__", 313, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Dict$26 = Py.newCode(0, var2, var1, "Dict", 322, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kwargs", "warn"};
      __init__$27 = Py.newCode(2, var2, var1, "__init__", 324, false, true, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Plist$28 = Py.newCode(0, var2, var1, "Plist", 331, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kwargs", "warn"};
      __init__$29 = Py.newCode(2, var2, var1, "__init__", 337, false, true, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "pathOrFile", "rootObject", "plist"};
      fromFile$30 = Py.newCode(2, var2, var1, "fromFile", 343, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pathOrFile"};
      write$31 = Py.newCode(2, var2, var1, "write", 351, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "maxlinelength", "maxbinsize", "pieces", "i", "chunk"};
      _encodeBase64$32 = Py.newCode(2, var2, var1, "_encodeBase64", 356, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Data$33 = Py.newCode(0, var2, var1, "Data", 365, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data"};
      __init__$34 = Py.newCode(2, var2, var1, "__init__", 369, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "data"};
      fromBase64$35 = Py.newCode(2, var2, var1, "fromBase64", 372, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "maxlinelength"};
      asBase64$36 = Py.newCode(2, var2, var1, "asBase64", 378, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$37 = Py.newCode(2, var2, var1, "__cmp__", 381, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$38 = Py.newCode(1, var2, var1, "__repr__", 389, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PlistParser$39 = Py.newCode(0, var2, var1, "PlistParser", 393, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$40 = Py.newCode(1, var2, var1, "__init__", 395, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fileobj", "ParserCreate", "parser"};
      parse$41 = Py.newCode(2, var2, var1, "parse", 400, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "attrs", "handler"};
      handleBeginElement$42 = Py.newCode(3, var2, var1, "handleBeginElement", 409, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "element", "handler"};
      handleEndElement$43 = Py.newCode(2, var2, var1, "handleEndElement", 415, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handleData$44 = Py.newCode(2, var2, var1, "handleData", 420, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      addObject$45 = Py.newCode(2, var2, var1, "addObject", 423, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      getData$46 = Py.newCode(1, var2, var1, "getData", 433, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "d"};
      begin_dict$47 = Py.newCode(2, var2, var1, "begin_dict", 444, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_dict$48 = Py.newCode(1, var2, var1, "end_dict", 448, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_key$49 = Py.newCode(1, var2, var1, "end_key", 451, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "a"};
      begin_array$50 = Py.newCode(2, var2, var1, "begin_array", 454, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_array$51 = Py.newCode(1, var2, var1, "end_array", 458, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_true$52 = Py.newCode(1, var2, var1, "end_true", 461, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_false$53 = Py.newCode(1, var2, var1, "end_false", 463, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_integer$54 = Py.newCode(1, var2, var1, "end_integer", 465, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_real$55 = Py.newCode(1, var2, var1, "end_real", 467, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_string$56 = Py.newCode(1, var2, var1, "end_string", 469, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_data$57 = Py.newCode(1, var2, var1, "end_data", 471, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_date$58 = Py.newCode(1, var2, var1, "end_date", 473, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new plistlib$py("plistlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(plistlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.readPlist$1(var2, var3);
         case 2:
            return this.writePlist$2(var2, var3);
         case 3:
            return this.readPlistFromString$3(var2, var3);
         case 4:
            return this.writePlistToString$4(var2, var3);
         case 5:
            return this.readPlistFromResource$5(var2, var3);
         case 6:
            return this.writePlistToResource$6(var2, var3);
         case 7:
            return this.DumbXMLWriter$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.beginElement$9(var2, var3);
         case 10:
            return this.endElement$10(var2, var3);
         case 11:
            return this.simpleElement$11(var2, var3);
         case 12:
            return this.writeln$12(var2, var3);
         case 13:
            return this._dateFromString$13(var2, var3);
         case 14:
            return this._dateToString$14(var2, var3);
         case 15:
            return this._escapeAndEncode$15(var2, var3);
         case 16:
            return this.PlistWriter$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.writeValue$18(var2, var3);
         case 19:
            return this.writeData$19(var2, var3);
         case 20:
            return this.writeDict$20(var2, var3);
         case 21:
            return this.writeArray$21(var2, var3);
         case 22:
            return this._InternalDict$22(var2, var3);
         case 23:
            return this.__getattr__$23(var2, var3);
         case 24:
            return this.__setattr__$24(var2, var3);
         case 25:
            return this.__delattr__$25(var2, var3);
         case 26:
            return this.Dict$26(var2, var3);
         case 27:
            return this.__init__$27(var2, var3);
         case 28:
            return this.Plist$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.fromFile$30(var2, var3);
         case 31:
            return this.write$31(var2, var3);
         case 32:
            return this._encodeBase64$32(var2, var3);
         case 33:
            return this.Data$33(var2, var3);
         case 34:
            return this.__init__$34(var2, var3);
         case 35:
            return this.fromBase64$35(var2, var3);
         case 36:
            return this.asBase64$36(var2, var3);
         case 37:
            return this.__cmp__$37(var2, var3);
         case 38:
            return this.__repr__$38(var2, var3);
         case 39:
            return this.PlistParser$39(var2, var3);
         case 40:
            return this.__init__$40(var2, var3);
         case 41:
            return this.parse$41(var2, var3);
         case 42:
            return this.handleBeginElement$42(var2, var3);
         case 43:
            return this.handleEndElement$43(var2, var3);
         case 44:
            return this.handleData$44(var2, var3);
         case 45:
            return this.addObject$45(var2, var3);
         case 46:
            return this.getData$46(var2, var3);
         case 47:
            return this.begin_dict$47(var2, var3);
         case 48:
            return this.end_dict$48(var2, var3);
         case 49:
            return this.end_key$49(var2, var3);
         case 50:
            return this.begin_array$50(var2, var3);
         case 51:
            return this.end_array$51(var2, var3);
         case 52:
            return this.end_true$52(var2, var3);
         case 53:
            return this.end_false$53(var2, var3);
         case 54:
            return this.end_integer$54(var2, var3);
         case 55:
            return this.end_real$55(var2, var3);
         case 56:
            return this.end_string$56(var2, var3);
         case 57:
            return this.end_data$57(var2, var3);
         case 58:
            return this.end_date$58(var2, var3);
         default:
            return null;
      }
   }
}
