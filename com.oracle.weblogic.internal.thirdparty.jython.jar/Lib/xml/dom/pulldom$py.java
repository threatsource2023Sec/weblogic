package xml.dom;

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
@Filename("xml/dom/pulldom.py")
public class pulldom$py extends PyFunctionTable implements PyRunnable {
   static pulldom$py self;
   static final PyCode f$0;
   static final PyCode PullDOM$1;
   static final PyCode __init__$2;
   static final PyCode pop$3;
   static final PyCode setDocumentLocator$4;
   static final PyCode startPrefixMapping$5;
   static final PyCode endPrefixMapping$6;
   static final PyCode startElementNS$7;
   static final PyCode endElementNS$8;
   static final PyCode startElement$9;
   static final PyCode endElement$10;
   static final PyCode comment$11;
   static final PyCode processingInstruction$12;
   static final PyCode ignorableWhitespace$13;
   static final PyCode characters$14;
   static final PyCode startDocument$15;
   static final PyCode buildDocument$16;
   static final PyCode endDocument$17;
   static final PyCode clear$18;
   static final PyCode ErrorHandler$19;
   static final PyCode warning$20;
   static final PyCode error$21;
   static final PyCode fatalError$22;
   static final PyCode DOMEventStream$23;
   static final PyCode __init__$24;
   static final PyCode reset$25;
   static final PyCode __getitem__$26;
   static final PyCode next$27;
   static final PyCode __iter__$28;
   static final PyCode expandNode$29;
   static final PyCode getEvent$30;
   static final PyCode _slurp$31;
   static final PyCode _emit$32;
   static final PyCode clear$33;
   static final PyCode SAX2DOM$34;
   static final PyCode startElementNS$35;
   static final PyCode startElement$36;
   static final PyCode processingInstruction$37;
   static final PyCode ignorableWhitespace$38;
   static final PyCode characters$39;
   static final PyCode parse$40;
   static final PyCode parseString$41;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("xml.sax", var1, -1);
      var1.setlocal("xml", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("xml.sax.handler", var1, -1);
      var1.setlocal("xml", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;

      PyList var4;
      try {
         var1.setline(6);
         PyList var8 = new PyList(new PyObject[]{var1.getname("types").__getattr__("StringType"), var1.getname("types").__getattr__("UnicodeType")});
         var1.setlocal("_StringTypes", var8);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getname("AttributeError"))) {
            throw var7;
         }

         var1.setline(8);
         var4 = new PyList(new PyObject[]{var1.getname("types").__getattr__("StringType")});
         var1.setlocal("_StringTypes", var4);
         var4 = null;
      }

      var1.setline(10);
      PyString var9 = PyString.fromInterned("START_ELEMENT");
      var1.setlocal("START_ELEMENT", var9);
      var3 = null;
      var1.setline(11);
      var9 = PyString.fromInterned("END_ELEMENT");
      var1.setlocal("END_ELEMENT", var9);
      var3 = null;
      var1.setline(12);
      var9 = PyString.fromInterned("COMMENT");
      var1.setlocal("COMMENT", var9);
      var3 = null;
      var1.setline(13);
      var9 = PyString.fromInterned("START_DOCUMENT");
      var1.setlocal("START_DOCUMENT", var9);
      var3 = null;
      var1.setline(14);
      var9 = PyString.fromInterned("END_DOCUMENT");
      var1.setlocal("END_DOCUMENT", var9);
      var3 = null;
      var1.setline(15);
      var9 = PyString.fromInterned("PROCESSING_INSTRUCTION");
      var1.setlocal("PROCESSING_INSTRUCTION", var9);
      var3 = null;
      var1.setline(16);
      var9 = PyString.fromInterned("IGNORABLE_WHITESPACE");
      var1.setlocal("IGNORABLE_WHITESPACE", var9);
      var3 = null;
      var1.setline(17);
      var9 = PyString.fromInterned("CHARACTERS");
      var1.setlocal("CHARACTERS", var9);
      var3 = null;
      var1.setline(19);
      PyObject[] var10 = new PyObject[]{var1.getname("xml").__getattr__("sax").__getattr__("ContentHandler")};
      PyObject var6 = Py.makeClass("PullDOM", var10, PullDOM$1);
      var1.setlocal("PullDOM", var6);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(201);
      var10 = Py.EmptyObjects;
      var6 = Py.makeClass("ErrorHandler", var10, ErrorHandler$19);
      var1.setlocal("ErrorHandler", var6);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(209);
      var10 = Py.EmptyObjects;
      var6 = Py.makeClass("DOMEventStream", var10, DOMEventStream$23);
      var1.setlocal("DOMEventStream", var6);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(296);
      var10 = new PyObject[]{var1.getname("PullDOM")};
      var6 = Py.makeClass("SAX2DOM", var10, SAX2DOM$34);
      var1.setlocal("SAX2DOM", var6);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(329);
      var3 = Py.newInteger(2)._pow(Py.newInteger(14))._sub(Py.newInteger(20));
      var1.setlocal("default_bufsize", var3);
      var3 = null;
      var1.setline(331);
      var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var11 = new PyFunction(var1.f_globals, var10, parse$40, (PyObject)null);
      var1.setlocal("parse", var11);
      var3 = null;
      var1.setline(342);
      var10 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var10, parseString$41, (PyObject)null);
      var1.setlocal("parseString", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PullDOM$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(20);
      PyObject var3 = var1.getname("None");
      var1.setlocal("_locator", var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getname("None");
      var1.setlocal("document", var3);
      var3 = null;
      var1.setline(23);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(39);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, pop$3, (PyObject)null);
      var1.setlocal("pop", var5);
      var3 = null;
      var1.setline(44);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setDocumentLocator$4, (PyObject)null);
      var1.setlocal("setDocumentLocator", var5);
      var3 = null;
      var1.setline(47);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startPrefixMapping$5, (PyObject)null);
      var1.setlocal("startPrefixMapping", var5);
      var3 = null;
      var1.setline(54);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, endPrefixMapping$6, (PyObject)null);
      var1.setlocal("endPrefixMapping", var5);
      var3 = null;
      var1.setline(57);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startElementNS$7, (PyObject)null);
      var1.setlocal("startElementNS", var5);
      var3 = null;
      var1.setline(113);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, endElementNS$8, (PyObject)null);
      var1.setlocal("endElementNS", var5);
      var3 = null;
      var1.setline(117);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startElement$9, (PyObject)null);
      var1.setlocal("startElement", var5);
      var3 = null;
      var1.setline(132);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, endElement$10, (PyObject)null);
      var1.setlocal("endElement", var5);
      var3 = null;
      var1.setline(136);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, comment$11, (PyObject)null);
      var1.setlocal("comment", var5);
      var3 = null;
      var1.setline(145);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, processingInstruction$12, (PyObject)null);
      var1.setlocal("processingInstruction", var5);
      var3 = null;
      var1.setline(154);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ignorableWhitespace$13, (PyObject)null);
      var1.setlocal("ignorableWhitespace", var5);
      var3 = null;
      var1.setline(159);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, characters$14, (PyObject)null);
      var1.setlocal("characters", var5);
      var3 = null;
      var1.setline(164);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startDocument$15, (PyObject)null);
      var1.setlocal("startDocument", var5);
      var3 = null;
      var1.setline(169);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, buildDocument$16, (PyObject)null);
      var1.setlocal("buildDocument", var5);
      var3 = null;
      var1.setline(193);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, endDocument$17, (PyObject)null);
      var1.setlocal("endDocument", var5);
      var3 = null;
      var1.setline(197);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, clear$18, PyString.fromInterned("clear(): Explicitly release parsing structures"));
      var1.setlocal("clear", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      String[] var3 = new String[]{"XML_NAMESPACE"};
      PyObject[] var6 = imp.importFrom("xml.dom", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(25);
      PyObject var7 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("documentFactory", var7);
      var3 = null;
      var1.setline(26);
      PyList var8 = new PyList(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
      var1.getlocal(0).__setattr__((String)"firstEvent", var8);
      var3 = null;
      var1.setline(27);
      var7 = var1.getlocal(0).__getattr__("firstEvent");
      var1.getlocal(0).__setattr__("lastEvent", var7);
      var3 = null;
      var1.setline(28);
      var8 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"elementStack", var8);
      var3 = null;
      var1.setline(29);
      var7 = var1.getlocal(0).__getattr__("elementStack").__getattr__("append");
      var1.getlocal(0).__setattr__("push", var7);
      var3 = null;

      try {
         var1.setline(31);
         var7 = var1.getlocal(0).__getattr__("elementStack").__getattr__("pop");
         var1.getlocal(0).__setattr__("pop", var7);
         var3 = null;
      } catch (Throwable var5) {
         PyException var9 = Py.setException(var5, var1);
         if (!var9.match(var1.getglobal("AttributeError"))) {
            throw var9;
         }

         var1.setline(34);
      }

      var1.setline(35);
      var8 = new PyList(new PyObject[]{new PyDictionary(new PyObject[]{var1.getlocal(2), PyString.fromInterned("xml")})});
      var1.getlocal(0).__setattr__((String)"_ns_contexts", var8);
      var3 = null;
      var1.setline(36);
      var7 = var1.getlocal(0).__getattr__("_ns_contexts").__getitem__(Py.newInteger(-1));
      var1.getlocal(0).__setattr__("_current_context", var7);
      var3 = null;
      var1.setline(37);
      var8 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"pending_events", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop$3(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(41);
      var1.getlocal(0).__getattr__("elementStack").__delitem__((PyObject)Py.newInteger(-1));
      var1.setline(42);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setDocumentLocator$4(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_locator", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startPrefixMapping$5(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyList var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_xmlns_attrs")).__not__().__nonzero__()) {
         var1.setline(49);
         var3 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_xmlns_attrs", var3);
         var3 = null;
      }

      var1.setline(50);
      PyObject var10000 = var1.getlocal(0).__getattr__("_xmlns_attrs").__getattr__("append");
      PyTuple var10002 = new PyTuple;
      PyObject[] var10004 = new PyObject[2];
      Object var10007 = var1.getlocal(1);
      if (!((PyObject)var10007).__nonzero__()) {
         var10007 = PyString.fromInterned("xmlns");
      }

      var10004[0] = (PyObject)var10007;
      var10004[1] = var1.getlocal(2);
      var10002.<init>(var10004);
      var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setline(51);
      var1.getlocal(0).__getattr__("_ns_contexts").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_current_context").__getattr__("copy").__call__(var2));
      var1.setline(52);
      var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("None");
      }

      PyObject var4 = var10000;
      var1.getlocal(0).__getattr__("_current_context").__setitem__(var1.getlocal(2), var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject endPrefixMapping$6(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getlocal(0).__getattr__("_ns_contexts").__getattr__("pop").__call__(var2);
      var1.getlocal(0).__setattr__("_current_context", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElementNS$7(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(59);
      PyObject var10000;
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(63);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(64);
            var3 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(4));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(65);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(66);
               var3 = var1.getlocal(6)._add(PyString.fromInterned(":"))._add(var1.getlocal(5));
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(68);
               var3 = var1.getlocal(5);
               var1.setlocal(2, var3);
               var3 = null;
            }
         }

         var1.setline(69);
         if (var1.getlocal(0).__getattr__("document").__nonzero__()) {
            var1.setline(70);
            var3 = var1.getlocal(0).__getattr__("document").__getattr__("createElementNS").__call__(var2, var1.getlocal(4), var1.getlocal(2));
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(72);
            var3 = var1.getlocal(0).__getattr__("buildDocument").__call__(var2, var1.getlocal(4), var1.getlocal(2));
            var1.setlocal(7, var3);
            var3 = null;
         }
      } else {
         var1.setline(76);
         if (var1.getlocal(0).__getattr__("document").__nonzero__()) {
            var1.setline(77);
            var3 = var1.getlocal(0).__getattr__("document").__getattr__("createElement").__call__(var2, var1.getlocal(5));
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(79);
            var3 = var1.getlocal(0).__getattr__("buildDocument").__call__(var2, var1.getglobal("None"), var1.getlocal(5));
            var1.setlocal(7, var3);
            var3 = null;
         }
      }

      var1.setline(82);
      PyString var11 = PyString.fromInterned("http://www.w3.org/2000/xmlns/");
      var1.setlocal(8, var11);
      var3 = null;
      var1.setline(83);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("_xmlns_attrs"), (PyObject)var1.getglobal("None"));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(84);
      var3 = var1.getlocal(9);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject var6;
      PyObject var8;
      PyObject[] var9;
      PyList var12;
      if (var10000.__nonzero__()) {
         var1.setline(85);
         var3 = var1.getlocal(9).__iter__();

         while(true) {
            var1.setline(85);
            var8 = var3.__iternext__();
            if (var8 == null) {
               var1.setline(93);
               var12 = new PyList(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"_xmlns_attrs", var12);
               var3 = null;
               break;
            }

            var9 = Py.unpackSequence(var8, 2);
            var6 = var9[0];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(11, var6);
            var6 = null;
            var1.setline(86);
            var5 = var1.getlocal(10);
            var10000 = var5._eq(PyString.fromInterned("xmlns"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(87);
               var5 = var1.getlocal(10);
               var1.setlocal(12, var5);
               var5 = null;
            } else {
               var1.setline(89);
               var5 = PyString.fromInterned("xmlns:")._add(var1.getlocal(10));
               var1.setlocal(12, var5);
               var5 = null;
            }

            var1.setline(90);
            var5 = var1.getlocal(0).__getattr__("document").__getattr__("createAttributeNS").__call__(var2, var1.getlocal(8), var1.getlocal(12));
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(91);
            var5 = var1.getlocal(11);
            var1.getlocal(13).__setattr__("value", var5);
            var5 = null;
            var1.setline(92);
            var1.getlocal(7).__getattr__("setAttributeNodeNS").__call__(var2, var1.getlocal(13));
         }
      }

      var1.setline(94);
      var3 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(94);
         var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(109);
            var12 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("START_ELEMENT"), var1.getlocal(7)}), var1.getglobal("None")});
            var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var12);
            var3 = null;
            var1.setline(110);
            var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
            var1.getlocal(0).__setattr__("lastEvent", var3);
            var3 = null;
            var1.setline(111);
            var1.getlocal(0).__getattr__("push").__call__(var2, var1.getlocal(7));
            var1.f_lasti = -1;
            return Py.None;
         }

         var9 = Py.unpackSequence(var8, 2);
         var6 = var9[0];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(11, var6);
         var6 = null;
         var1.setline(95);
         var5 = var1.getlocal(10);
         PyObject[] var10 = Py.unpackSequence(var5, 2);
         PyObject var7 = var10[0];
         var1.setlocal(14, var7);
         var7 = null;
         var7 = var10[1];
         var1.setlocal(15, var7);
         var7 = null;
         var5 = null;
         var1.setline(96);
         if (var1.getlocal(14).__nonzero__()) {
            var1.setline(97);
            var5 = var1.getlocal(0).__getattr__("_current_context").__getitem__(var1.getlocal(14));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(98);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(99);
               var5 = var1.getlocal(6)._add(PyString.fromInterned(":"))._add(var1.getlocal(15));
               var1.setlocal(12, var5);
               var5 = null;
            } else {
               var1.setline(101);
               var5 = var1.getlocal(15);
               var1.setlocal(12, var5);
               var5 = null;
            }

            var1.setline(102);
            var5 = var1.getlocal(0).__getattr__("document").__getattr__("createAttributeNS").__call__(var2, var1.getlocal(14), var1.getlocal(12));
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(103);
            var1.getlocal(7).__getattr__("setAttributeNodeNS").__call__(var2, var1.getlocal(13));
         } else {
            var1.setline(105);
            var5 = var1.getlocal(0).__getattr__("document").__getattr__("createAttribute").__call__(var2, var1.getlocal(15));
            var1.setlocal(13, var5);
            var5 = null;
            var1.setline(106);
            var1.getlocal(7).__getattr__("setAttributeNode").__call__(var2, var1.getlocal(13));
         }

         var1.setline(107);
         var5 = var1.getlocal(11);
         var1.getlocal(13).__setattr__("value", var5);
         var5 = null;
      }
   }

   public PyObject endElementNS$8(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("END_ELEMENT"), var1.getlocal(0).__getattr__("pop").__call__(var2)}), var1.getglobal("None")});
      var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var3);
      var3 = null;
      var1.setline(115);
      PyObject var4 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("lastEvent", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$9(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("document").__nonzero__()) {
         var1.setline(119);
         var3 = var1.getlocal(0).__getattr__("document").__getattr__("createElement").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(121);
         var3 = var1.getlocal(0).__getattr__("buildDocument").__call__(var2, var1.getglobal("None"), var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(123);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(123);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(128);
            PyList var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("START_ELEMENT"), var1.getlocal(3)}), var1.getglobal("None")});
            var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var7);
            var3 = null;
            var1.setline(129);
            var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
            var1.getlocal(0).__setattr__("lastEvent", var3);
            var3 = null;
            var1.setline(130);
            var1.getlocal(0).__getattr__("push").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(124);
         PyObject var8 = var1.getlocal(0).__getattr__("document").__getattr__("createAttribute").__call__(var2, var1.getlocal(4));
         var1.setlocal(6, var8);
         var5 = null;
         var1.setline(125);
         var8 = var1.getlocal(5);
         var1.getlocal(6).__setattr__("value", var8);
         var5 = null;
         var1.setline(126);
         var1.getlocal(3).__getattr__("setAttributeNode").__call__(var2, var1.getlocal(6));
      }
   }

   public PyObject endElement$10(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("END_ELEMENT"), var1.getlocal(0).__getattr__("pop").__call__(var2)}), var1.getglobal("None")});
      var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var3);
      var3 = null;
      var1.setline(134);
      PyObject var4 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("lastEvent", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject comment$11(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject var3;
      PyList var4;
      if (var1.getlocal(0).__getattr__("document").__nonzero__()) {
         var1.setline(138);
         var3 = var1.getlocal(0).__getattr__("document").__getattr__("createComment").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(139);
         var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("COMMENT"), var1.getlocal(2)}), var1.getglobal("None")});
         var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var4);
         var3 = null;
         var1.setline(140);
         var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
         var1.getlocal(0).__setattr__("lastEvent", var3);
         var3 = null;
      } else {
         var1.setline(142);
         var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("COMMENT"), var1.getlocal(1)}), var1.getglobal("None")});
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(143);
         var1.getlocal(0).__getattr__("pending_events").__getattr__("append").__call__(var2, var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$12(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyObject var3;
      PyList var4;
      if (var1.getlocal(0).__getattr__("document").__nonzero__()) {
         var1.setline(147);
         var3 = var1.getlocal(0).__getattr__("document").__getattr__("createProcessingInstruction").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(148);
         var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("PROCESSING_INSTRUCTION"), var1.getlocal(3)}), var1.getglobal("None")});
         var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var4);
         var3 = null;
         var1.setline(149);
         var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
         var1.getlocal(0).__setattr__("lastEvent", var3);
         var3 = null;
      } else {
         var1.setline(151);
         var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("PROCESSING_INSTRUCTION"), var1.getlocal(1), var1.getlocal(2)}), var1.getglobal("None")});
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(152);
         var1.getlocal(0).__getattr__("pending_events").__getattr__("append").__call__(var2, var1.getlocal(4));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$13(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("createTextNode").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(156);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("IGNORABLE_WHITESPACE"), var1.getlocal(2)}), var1.getglobal("None")});
      var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var4);
      var3 = null;
      var1.setline(157);
      var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("lastEvent", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$14(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyObject var3 = var1.getlocal(0).__getattr__("document").__getattr__("createTextNode").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(161);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("CHARACTERS"), var1.getlocal(2)}), var1.getglobal("None")});
      var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var4);
      var3 = null;
      var1.setline(162);
      var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("lastEvent", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startDocument$15(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyObject var3 = var1.getlocal(0).__getattr__("documentFactory");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(166);
         var3 = imp.importOne("xml.dom.minidom", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(167);
         var3 = var1.getlocal(1).__getattr__("dom").__getattr__("minidom").__getattr__("Document").__getattr__("implementation");
         var1.getlocal(0).__setattr__("documentFactory", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject buildDocument$16(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getlocal(0).__getattr__("documentFactory").__getattr__("createDocument").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("document", var3);
      var3 = null;
      var1.setline(174);
      PyList var8 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("START_DOCUMENT"), var1.getlocal(3)}), var1.getglobal("None")});
      var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var8);
      var3 = null;
      var1.setline(175);
      var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(1));
      var1.getlocal(0).__setattr__("lastEvent", var3);
      var3 = null;
      var1.setline(176);
      var1.getlocal(0).__getattr__("push").__call__(var2, var1.getlocal(3));
      var1.setline(178);
      var3 = var1.getlocal(0).__getattr__("pending_events").__iter__();

      while(true) {
         var1.setline(178);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(190);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("pending_events", var3);
            var3 = null;
            var1.setline(191);
            var3 = var1.getlocal(3).__getattr__("firstChild");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(179);
         PyObject var5 = var1.getlocal(4).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._eq(var1.getglobal("PROCESSING_INSTRUCTION"));
         var5 = null;
         PyTuple var9;
         if (var10000.__nonzero__()) {
            var1.setline(180);
            var5 = var1.getlocal(4).__getitem__(Py.newInteger(0));
            PyObject[] var6 = Py.unpackSequence(var5, 3);
            PyObject var7 = var6[0];
            var1.setlocal(5, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(6, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(7, var7);
            var7 = null;
            var5 = null;
            var1.setline(181);
            var5 = var1.getlocal(0).__getattr__("document").__getattr__("createProcessingInstruction").__call__(var2, var1.getlocal(6), var1.getlocal(7));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(182);
            var9 = new PyTuple(new PyObject[]{var1.getglobal("PROCESSING_INSTRUCTION"), var1.getlocal(8)});
            var1.getlocal(4).__setitem__((PyObject)Py.newInteger(0), var9);
            var5 = null;
         } else {
            var1.setline(183);
            var5 = var1.getlocal(4).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(var1.getglobal("COMMENT"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(187);
               throw Py.makeException(var1.getglobal("AssertionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unknown pending event "), (PyObject)var1.getlocal(4).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0))));
            }

            var1.setline(184);
            var5 = var1.getlocal(0).__getattr__("document").__getattr__("createComment").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(185);
            var9 = new PyTuple(new PyObject[]{var1.getglobal("COMMENT"), var1.getlocal(8)});
            var1.getlocal(4).__setitem__((PyObject)Py.newInteger(0), var9);
            var5 = null;
         }

         var1.setline(188);
         var5 = var1.getlocal(4);
         var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var5);
         var5 = null;
         var1.setline(189);
         var5 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("lastEvent", var5);
         var5 = null;
      }
   }

   public PyObject endDocument$17(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("END_DOCUMENT"), var1.getlocal(0).__getattr__("document")}), var1.getglobal("None")});
      var1.getlocal(0).__getattr__("lastEvent").__setitem__((PyObject)Py.newInteger(1), var3);
      var3 = null;
      var1.setline(195);
      var1.getlocal(0).__getattr__("pop").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear$18(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyString.fromInterned("clear(): Explicitly release parsing structures");
      var1.setline(199);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("document", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ErrorHandler$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(202);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, warning$20, (PyObject)null);
      var1.setlocal("warning", var4);
      var3 = null;
      var1.setline(204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$21, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fatalError$22, (PyObject)null);
      var1.setlocal("fatalError", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject warning$20(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      Py.println(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$21(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      throw Py.makeException(var1.getlocal(1));
   }

   public PyObject fatalError$22(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      throw Py.makeException(var1.getlocal(1));
   }

   public PyObject DOMEventStream$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(210);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$24, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(218);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$25, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(224);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$26, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$27, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$28, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(239);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, expandNode$29, (PyObject)null);
      var1.setlocal("expandNode", var4);
      var3 = null;
      var1.setline(254);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getEvent$30, (PyObject)null);
      var1.setlocal("getEvent", var4);
      var3 = null;
      var1.setline(269);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _slurp$31, PyString.fromInterned(" Fallback replacement for getEvent() using the\n            standard SAX2 interface, which means we slurp the\n            SAX events into memory (no performance gain, but\n            we are compatible to all SAX parsers).\n        "));
      var1.setlocal("_slurp", var4);
      var3 = null;
      var1.setline(279);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _emit$32, PyString.fromInterned(" Fallback replacement for getEvent() that emits\n            the events that _slurp() read previously.\n        "));
      var1.setlocal("_emit", var4);
      var3 = null;
      var1.setline(289);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$33, PyString.fromInterned("clear(): Explicitly release parsing objects"));
      var1.setlocal("clear", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$24(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(212);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("parser", var3);
      var3 = null;
      var1.setline(213);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("bufsize", var3);
      var3 = null;
      var1.setline(214);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("parser"), (PyObject)PyString.fromInterned("feed")).__not__().__nonzero__()) {
         var1.setline(215);
         var3 = var1.getlocal(0).__getattr__("_slurp");
         var1.getlocal(0).__setattr__("getEvent", var3);
         var3 = null;
      }

      var1.setline(216);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$25(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyObject var3 = var1.getglobal("PullDOM").__call__(var2);
      var1.getlocal(0).__setattr__("pulldom", var3);
      var3 = null;
      var1.setline(221);
      var1.getlocal(0).__getattr__("parser").__getattr__("setFeature").__call__((ThreadState)var2, (PyObject)var1.getglobal("xml").__getattr__("sax").__getattr__("handler").__getattr__("feature_namespaces"), (PyObject)Py.newInteger(1));
      var1.setline(222);
      var1.getlocal(0).__getattr__("parser").__getattr__("setContentHandler").__call__(var2, var1.getlocal(0).__getattr__("pulldom"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$26(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyObject var3 = var1.getlocal(0).__getattr__("getEvent").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(226);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(227);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(228);
         throw Py.makeException(var1.getglobal("IndexError"));
      }
   }

   public PyObject next$27(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var3 = var1.getlocal(0).__getattr__("getEvent").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(232);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(233);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(234);
         throw Py.makeException(var1.getglobal("StopIteration"));
      }
   }

   public PyObject __iter__$28(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject expandNode$29(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyObject var3 = var1.getlocal(0).__getattr__("getEvent").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(241);
      PyList var6 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.setlocal(3, var6);
      var3 = null;

      while(true) {
         var1.setline(242);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(243);
         var3 = var1.getlocal(2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(244);
         var3 = var1.getlocal(5);
         PyObject var10000 = var3._is(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(245);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(246);
         var3 = var1.getlocal(4);
         var10000 = var3._ne(var1.getglobal("END_ELEMENT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(247);
            var1.getlocal(3).__getitem__(Py.newInteger(-1)).__getattr__("appendChild").__call__(var2, var1.getlocal(5));
         }

         var1.setline(248);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(var1.getglobal("START_ELEMENT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(249);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         } else {
            var1.setline(250);
            var3 = var1.getlocal(4);
            var10000 = var3._eq(var1.getglobal("END_ELEMENT"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(251);
               var1.getlocal(3).__delitem__((PyObject)Py.newInteger(-1));
            }
         }

         var1.setline(252);
         var3 = var1.getlocal(0).__getattr__("getEvent").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }
   }

   public PyObject getEvent$30(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__getitem__(Py.newInteger(1)).__not__().__nonzero__()) {
         var1.setline(258);
         var3 = var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent");
         var1.getlocal(0).__getattr__("pulldom").__setattr__("lastEvent", var3);
         var3 = null;
      }

      while(true) {
         var1.setline(259);
         if (!var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__getitem__(Py.newInteger(1)).__not__().__nonzero__()) {
            var1.setline(265);
            PyObject var4 = var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(266);
            var4 = var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
            var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__setitem__((PyObject)Py.newInteger(1), var4);
            var4 = null;
            var1.setline(267);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(260);
         var3 = var1.getlocal(0).__getattr__("stream").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("bufsize"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(261);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(262);
            var1.getlocal(0).__getattr__("parser").__getattr__("close").__call__(var2);
            var1.setline(263);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(264);
         var1.getlocal(0).__getattr__("parser").__getattr__("feed").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject _slurp$31(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyString.fromInterned(" Fallback replacement for getEvent() using the\n            standard SAX2 interface, which means we slurp the\n            SAX events into memory (no performance gain, but\n            we are compatible to all SAX parsers).\n        ");
      var1.setline(275);
      var1.getlocal(0).__getattr__("parser").__getattr__("parse").__call__(var2, var1.getlocal(0).__getattr__("stream"));
      var1.setline(276);
      PyObject var3 = var1.getlocal(0).__getattr__("_emit");
      var1.getlocal(0).__setattr__("getEvent", var3);
      var3 = null;
      var1.setline(277);
      var3 = var1.getlocal(0).__getattr__("_emit").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _emit$32(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyString.fromInterned(" Fallback replacement for getEvent() that emits\n            the events that _slurp() read previously.\n        ");
      var1.setline(283);
      PyObject var3 = var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__getitem__(Py.newInteger(1));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(284);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(285);
         PyObject var4 = var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(286);
         var4 = var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__getitem__(Py.newInteger(1)).__getitem__(Py.newInteger(1));
         var1.getlocal(0).__getattr__("pulldom").__getattr__("firstEvent").__setitem__((PyObject)Py.newInteger(1), var4);
         var4 = null;
         var1.setline(287);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject clear$33(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyString.fromInterned("clear(): Explicitly release parsing objects");
      var1.setline(291);
      var1.getlocal(0).__getattr__("pulldom").__getattr__("clear").__call__(var2);
      var1.setline(292);
      var1.getlocal(0).__delattr__("pulldom");
      var1.setline(293);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("parser", var3);
      var3 = null;
      var1.setline(294);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SAX2DOM$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(298);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, startElementNS$35, (PyObject)null);
      var1.setlocal("startElementNS", var4);
      var3 = null;
      var1.setline(304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startElement$36, (PyObject)null);
      var1.setlocal("startElement", var4);
      var3 = null;
      var1.setline(310);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, processingInstruction$37, (PyObject)null);
      var1.setlocal("processingInstruction", var4);
      var3 = null;
      var1.setline(316);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ignorableWhitespace$38, (PyObject)null);
      var1.setlocal("ignorableWhitespace", var4);
      var3 = null;
      var1.setline(322);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, characters$39, (PyObject)null);
      var1.setlocal("characters", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject startElementNS$35(PyFrame var1, ThreadState var2) {
      var1.setline(299);
      var1.getglobal("PullDOM").__getattr__("startElementNS").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(300);
      PyObject var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(301);
      var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(302);
      var1.getlocal(5).__getattr__("appendChild").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startElement$36(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      var1.getglobal("PullDOM").__getattr__("startElement").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(306);
      PyObject var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(307);
      var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(308);
      var1.getlocal(4).__getattr__("appendChild").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject processingInstruction$37(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      var1.getglobal("PullDOM").__getattr__("processingInstruction").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(312);
      PyObject var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(313);
      var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(314);
      var1.getlocal(4).__getattr__("appendChild").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ignorableWhitespace$38(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      var1.getglobal("PullDOM").__getattr__("ignorableWhitespace").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(318);
      PyObject var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(319);
      var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(320);
      var1.getlocal(3).__getattr__("appendChild").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject characters$39(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      var1.getglobal("PullDOM").__getattr__("characters").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(324);
      PyObject var3 = var1.getlocal(0).__getattr__("lastEvent").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(325);
      var3 = var1.getlocal(0).__getattr__("elementStack").__getitem__(Py.newInteger(-1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(326);
      var1.getlocal(3).__getattr__("appendChild").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$40(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(333);
         var3 = var1.getglobal("default_bufsize");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(334);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      var10000 = var3._in(var1.getglobal("_StringTypes"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(335);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(337);
         var3 = var1.getlocal(0);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(338);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(339);
         var3 = var1.getglobal("xml").__getattr__("sax").__getattr__("make_parser").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(340);
      var3 = var1.getglobal("DOMEventStream").__call__(var2, var1.getlocal(3), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parseString$41(PyFrame var1, ThreadState var2) {
      PyException var3;
      String[] var4;
      try {
         var1.setline(344);
         String[] var7 = new String[]{"StringIO"};
         PyObject[] var8 = imp.importFrom("cStringIO", var7, var1, -1);
         PyObject var11 = var8[0];
         var1.setlocal(2, var11);
         var4 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(346);
         var4 = new String[]{"StringIO"};
         PyObject[] var10 = imp.importFrom("StringIO", var4, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal(2, var5);
         var5 = null;
      }

      var1.setline(348);
      PyObject var9 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(349);
      var9 = var1.getlocal(2).__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(350);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(351);
         var9 = var1.getglobal("xml").__getattr__("sax").__getattr__("make_parser").__call__(var2);
         var1.setlocal(1, var9);
         var3 = null;
      }

      var1.setline(352);
      var9 = var1.getglobal("DOMEventStream").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return var9;
   }

   public pulldom$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PullDOM$1 = Py.newCode(0, var2, var1, "PullDOM", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "documentFactory", "XML_NAMESPACE"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 23, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      pop$3 = Py.newCode(1, var2, var1, "pop", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "locator"};
      setDocumentLocator$4 = Py.newCode(2, var2, var1, "setDocumentLocator", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix", "uri"};
      startPrefixMapping$5 = Py.newCode(3, var2, var1, "startPrefixMapping", 47, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefix"};
      endPrefixMapping$6 = Py.newCode(2, var2, var1, "endPrefixMapping", 54, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "tagName", "attrs", "uri", "localname", "prefix", "node", "xmlns_uri", "xmlns_attrs", "aname", "value", "qname", "attr", "a_uri", "a_localname"};
      startElementNS$7 = Py.newCode(4, var2, var1, "startElementNS", 57, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "tagName"};
      endElementNS$8 = Py.newCode(3, var2, var1, "endElementNS", 113, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attrs", "node", "aname", "value", "attr"};
      startElement$9 = Py.newCode(3, var2, var1, "startElement", 117, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      endElement$10 = Py.newCode(2, var2, var1, "endElement", 132, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "node", "event"};
      comment$11 = Py.newCode(2, var2, var1, "comment", 136, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data", "node", "event"};
      processingInstruction$12 = Py.newCode(3, var2, var1, "processingInstruction", 145, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars", "node"};
      ignorableWhitespace$13 = Py.newCode(2, var2, var1, "ignorableWhitespace", 154, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars", "node"};
      characters$14 = Py.newCode(2, var2, var1, "characters", 159, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "xml"};
      startDocument$15 = Py.newCode(1, var2, var1, "startDocument", 164, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "uri", "tagname", "node", "e", "_", "target", "data", "n"};
      buildDocument$16 = Py.newCode(3, var2, var1, "buildDocument", 169, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      endDocument$17 = Py.newCode(1, var2, var1, "endDocument", 193, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$18 = Py.newCode(1, var2, var1, "clear", 197, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ErrorHandler$19 = Py.newCode(0, var2, var1, "ErrorHandler", 201, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "exception"};
      warning$20 = Py.newCode(2, var2, var1, "warning", 202, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      error$21 = Py.newCode(2, var2, var1, "error", 204, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exception"};
      fatalError$22 = Py.newCode(2, var2, var1, "fatalError", 206, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DOMEventStream$23 = Py.newCode(0, var2, var1, "DOMEventStream", 209, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "parser", "bufsize"};
      __init__$24 = Py.newCode(4, var2, var1, "__init__", 210, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$25 = Py.newCode(1, var2, var1, "reset", 218, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "rc"};
      __getitem__$26 = Py.newCode(2, var2, var1, "__getitem__", 224, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rc"};
      next$27 = Py.newCode(1, var2, var1, "next", 230, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$28 = Py.newCode(1, var2, var1, "__iter__", 236, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "event", "parents", "token", "cur_node"};
      expandNode$29 = Py.newCode(2, var2, var1, "expandNode", 239, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "buf", "rc"};
      getEvent$30 = Py.newCode(1, var2, var1, "getEvent", 254, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _slurp$31 = Py.newCode(1, var2, var1, "_slurp", 269, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rc"};
      _emit$32 = Py.newCode(1, var2, var1, "_emit", 279, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear$33 = Py.newCode(1, var2, var1, "clear", 289, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SAX2DOM$34 = Py.newCode(0, var2, var1, "SAX2DOM", 296, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "tagName", "attrs", "curNode", "parentNode"};
      startElementNS$35 = Py.newCode(4, var2, var1, "startElementNS", 298, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "attrs", "curNode", "parentNode"};
      startElement$36 = Py.newCode(3, var2, var1, "startElement", 304, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "data", "node", "parentNode"};
      processingInstruction$37 = Py.newCode(3, var2, var1, "processingInstruction", 310, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars", "node", "parentNode"};
      ignorableWhitespace$38 = Py.newCode(2, var2, var1, "ignorableWhitespace", 316, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chars", "node", "parentNode"};
      characters$39 = Py.newCode(2, var2, var1, "characters", 322, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stream_or_string", "parser", "bufsize", "stream"};
      parse$40 = Py.newCode(3, var2, var1, "parse", 331, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string", "parser", "StringIO", "bufsize", "buf"};
      parseString$41 = Py.newCode(2, var2, var1, "parseString", 342, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pulldom$py("xml/dom/pulldom$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pulldom$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PullDOM$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.pop$3(var2, var3);
         case 4:
            return this.setDocumentLocator$4(var2, var3);
         case 5:
            return this.startPrefixMapping$5(var2, var3);
         case 6:
            return this.endPrefixMapping$6(var2, var3);
         case 7:
            return this.startElementNS$7(var2, var3);
         case 8:
            return this.endElementNS$8(var2, var3);
         case 9:
            return this.startElement$9(var2, var3);
         case 10:
            return this.endElement$10(var2, var3);
         case 11:
            return this.comment$11(var2, var3);
         case 12:
            return this.processingInstruction$12(var2, var3);
         case 13:
            return this.ignorableWhitespace$13(var2, var3);
         case 14:
            return this.characters$14(var2, var3);
         case 15:
            return this.startDocument$15(var2, var3);
         case 16:
            return this.buildDocument$16(var2, var3);
         case 17:
            return this.endDocument$17(var2, var3);
         case 18:
            return this.clear$18(var2, var3);
         case 19:
            return this.ErrorHandler$19(var2, var3);
         case 20:
            return this.warning$20(var2, var3);
         case 21:
            return this.error$21(var2, var3);
         case 22:
            return this.fatalError$22(var2, var3);
         case 23:
            return this.DOMEventStream$23(var2, var3);
         case 24:
            return this.__init__$24(var2, var3);
         case 25:
            return this.reset$25(var2, var3);
         case 26:
            return this.__getitem__$26(var2, var3);
         case 27:
            return this.next$27(var2, var3);
         case 28:
            return this.__iter__$28(var2, var3);
         case 29:
            return this.expandNode$29(var2, var3);
         case 30:
            return this.getEvent$30(var2, var3);
         case 31:
            return this._slurp$31(var2, var3);
         case 32:
            return this._emit$32(var2, var3);
         case 33:
            return this.clear$33(var2, var3);
         case 34:
            return this.SAX2DOM$34(var2, var3);
         case 35:
            return this.startElementNS$35(var2, var3);
         case 36:
            return this.startElement$36(var2, var3);
         case 37:
            return this.processingInstruction$37(var2, var3);
         case 38:
            return this.ignorableWhitespace$38(var2, var3);
         case 39:
            return this.characters$39(var2, var3);
         case 40:
            return this.parse$40(var2, var3);
         case 41:
            return this.parseString$41(var2, var3);
         default:
            return null;
      }
   }
}
