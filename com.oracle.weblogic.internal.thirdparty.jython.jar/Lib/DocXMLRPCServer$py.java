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
@Filename("DocXMLRPCServer.py")
public class DocXMLRPCServer$py extends PyFunctionTable implements PyRunnable {
   static DocXMLRPCServer$py self;
   static final PyCode f$0;
   static final PyCode ServerHTMLDoc$1;
   static final PyCode markup$2;
   static final PyCode docroutine$3;
   static final PyCode docserver$4;
   static final PyCode XMLRPCDocGenerator$5;
   static final PyCode __init__$6;
   static final PyCode set_server_title$7;
   static final PyCode set_server_name$8;
   static final PyCode set_server_documentation$9;
   static final PyCode generate_html_documentation$10;
   static final PyCode DocXMLRPCRequestHandler$11;
   static final PyCode do_GET$12;
   static final PyCode DocXMLRPCServer$13;
   static final PyCode __init__$14;
   static final PyCode DocCGIXMLRPCRequestHandler$15;
   static final PyCode handle_get$16;
   static final PyCode __init__$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Self documenting XML-RPC Server.\n\nThis module can be used to create XML-RPC servers that\nserve pydoc-style documentation in response to HTTP\nGET requests. This documentation is dynamically generated\nbased on the functions and methods registered with the\nserver.\n\nThis module is built upon the pydoc and SimpleXMLRPCServer\nmodules.\n"));
      var1.setline(11);
      PyString.fromInterned("Self documenting XML-RPC Server.\n\nThis module can be used to create XML-RPC servers that\nserve pydoc-style documentation in response to HTTP\nGET requests. This documentation is dynamically generated\nbased on the functions and methods registered with the\nserver.\n\nThis module is built upon the pydoc and SimpleXMLRPCServer\nmodules.\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("pydoc", var1, -1);
      var1.setlocal("pydoc", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("inspect", var1, -1);
      var1.setlocal("inspect", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(18);
      String[] var5 = new String[]{"SimpleXMLRPCServer", "SimpleXMLRPCRequestHandler", "CGIXMLRPCRequestHandler", "resolve_dotted_attribute"};
      PyObject[] var6 = imp.importFrom("SimpleXMLRPCServer", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("SimpleXMLRPCServer", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("SimpleXMLRPCRequestHandler", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("CGIXMLRPCRequestHandler", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("resolve_dotted_attribute", var4);
      var4 = null;
      var1.setline(23);
      var6 = new PyObject[]{var1.getname("pydoc").__getattr__("HTMLDoc")};
      var4 = Py.makeClass("ServerHTMLDoc", var6, ServerHTMLDoc$1);
      var1.setlocal("ServerHTMLDoc", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(134);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("XMLRPCDocGenerator", var6, XMLRPCDocGenerator$5);
      var1.setlocal("XMLRPCDocGenerator", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(215);
      var6 = new PyObject[]{var1.getname("SimpleXMLRPCRequestHandler")};
      var4 = Py.makeClass("DocXMLRPCRequestHandler", var6, DocXMLRPCRequestHandler$11);
      var1.setlocal("DocXMLRPCRequestHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(243);
      var6 = new PyObject[]{var1.getname("SimpleXMLRPCServer"), var1.getname("XMLRPCDocGenerator")};
      var4 = Py.makeClass("DocXMLRPCServer", var6, DocXMLRPCServer$13);
      var1.setlocal("DocXMLRPCServer", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(258);
      var6 = new PyObject[]{var1.getname("CGIXMLRPCRequestHandler"), var1.getname("XMLRPCDocGenerator")};
      var4 = Py.makeClass("DocCGIXMLRPCRequestHandler", var6, DocCGIXMLRPCRequestHandler$15);
      var1.setlocal("DocCGIXMLRPCRequestHandler", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ServerHTMLDoc$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class used to generate pydoc HTML document for a server"));
      var1.setline(24);
      PyString.fromInterned("Class used to generate pydoc HTML document for a server");
      var1.setline(26);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, markup$2, PyString.fromInterned("Mark up some plain text, given a context of symbols to look for.\n        Each context dictionary maps object names to anchor names."));
      var1.setlocal("markup", var4);
      var3 = null;
      var1.setline(67);
      var3 = new PyObject[]{var1.getname("None"), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, docroutine$3, PyString.fromInterned("Produce HTML documentation for a function or method object."));
      var1.setlocal("docroutine", var4);
      var3 = null;
      var1.setline(109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, docserver$4, PyString.fromInterned("Produce HTML documentation for an XML-RPC server."));
      var1.setlocal("docserver", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject markup$2(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyString.fromInterned("Mark up some plain text, given a context of symbols to look for.\n        Each context dictionary maps object names to anchor names.");
      var1.setline(29);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("escape");
      }

      PyObject var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(30);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(31);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\b((http|ftp)://\\S+[\\w/]|RFC[- ]?(\\d+)|PEP[- ]?(\\d+)|(self\\.)?((?:\\w|\\.)+))\\b"));
      var1.setlocal(8, var3);
      var3 = null;

      while(true) {
         var1.setline(41);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(42);
         var3 = var1.getlocal(8).__getattr__("search").__call__(var2, var1.getlocal(1), var1.getlocal(7));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(43);
         if (var1.getlocal(9).__not__().__nonzero__()) {
            break;
         }

         var1.setline(44);
         var3 = var1.getlocal(9).__getattr__("span").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(10, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(11, var5);
         var5 = null;
         var3 = null;
         var1.setline(45);
         var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(2).__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(7), var1.getlocal(10), (PyObject)null)));
         var1.setline(47);
         var3 = var1.getlocal(9).__getattr__("groups").__call__(var2);
         var4 = Py.unpackSequence(var3, 6);
         var5 = var4[0];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(13, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(14, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(16, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(17, var5);
         var5 = null;
         var3 = null;
         var1.setline(48);
         if (var1.getlocal(13).__nonzero__()) {
            var1.setline(49);
            var3 = var1.getlocal(2).__call__(var2, var1.getlocal(12)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("&quot;"));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(50);
            var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(18)})));
         } else {
            var1.setline(51);
            if (var1.getlocal(14).__nonzero__()) {
               var1.setline(52);
               var3 = PyString.fromInterned("http://www.rfc-editor.org/rfc/rfc%d.txt")._mod(var1.getglobal("int").__call__(var2, var1.getlocal(14)));
               var1.setlocal(18, var3);
               var3 = null;
               var1.setline(53);
               var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(2).__call__(var2, var1.getlocal(12))})));
            } else {
               var1.setline(54);
               if (var1.getlocal(15).__nonzero__()) {
                  var1.setline(55);
                  var3 = PyString.fromInterned("http://www.python.org/dev/peps/pep-%04d/")._mod(var1.getglobal("int").__call__(var2, var1.getlocal(15)));
                  var1.setlocal(18, var3);
                  var3 = null;
                  var1.setline(56);
                  var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("<a href=\"%s\">%s</a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(2).__call__(var2, var1.getlocal(12))})));
               } else {
                  var1.setline(57);
                  var3 = var1.getlocal(1).__getslice__(var1.getlocal(11), var1.getlocal(11)._add(Py.newInteger(1)), (PyObject)null);
                  var10000 = var3._eq(PyString.fromInterned("("));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(58);
                     var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("namelink").__call__(var2, var1.getlocal(17), var1.getlocal(5), var1.getlocal(3), var1.getlocal(4)));
                  } else {
                     var1.setline(59);
                     if (var1.getlocal(16).__nonzero__()) {
                        var1.setline(60);
                        var1.getlocal(6).__getattr__("append").__call__(var2, PyString.fromInterned("self.<strong>%s</strong>")._mod(var1.getlocal(17)));
                     } else {
                        var1.setline(62);
                        var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("namelink").__call__(var2, var1.getlocal(17), var1.getlocal(4)));
                     }
                  }
               }
            }
         }

         var1.setline(63);
         var3 = var1.getlocal(11);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(64);
      var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(2).__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null)));
      var1.setline(65);
      var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docroutine$3(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Produce HTML documentation for a function or method object.");
      var1.setline(71);
      Object var10000 = var1.getlocal(7);
      if (((PyObject)var10000).__nonzero__()) {
         var10000 = var1.getlocal(7).__getattr__("__name__");
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      PyObject var3 = ((PyObject)var10000)._add(PyString.fromInterned("-"))._add(var1.getlocal(2));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(72);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(9, var6);
      var3 = null;
      var1.setline(74);
      var3 = PyString.fromInterned("<a name=\"%s\"><strong>%s</strong></a>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getlocal(8)), var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getlocal(2))}));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(77);
      PyObject[] var4;
      PyObject var5;
      String[] var7;
      PyObject[] var8;
      PyObject var10;
      if (var1.getglobal("inspect").__getattr__("ismethod").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(78);
         var3 = var1.getglobal("inspect").__getattr__("getargspec").__call__(var2, var1.getlocal(1).__getattr__("im_func"));
         var4 = Py.unpackSequence(var3, 4);
         var5 = var4[0];
         var1.setlocal(11, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(13, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(14, var5);
         var5 = null;
         var3 = null;
         var1.setline(81);
         var10 = var1.getglobal("inspect").__getattr__("formatargspec");
         var8 = new PyObject[]{var1.getlocal(11).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14), var1.getlocal(0).__getattr__("formatvalue")};
         var7 = new String[]{"formatvalue"};
         var10 = var10.__call__(var2, var8, var7);
         var3 = null;
         var3 = var10;
         var1.setlocal(15, var3);
         var3 = null;
      } else {
         var1.setline(88);
         if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(89);
            var3 = var1.getglobal("inspect").__getattr__("getargspec").__call__(var2, var1.getlocal(1));
            var4 = Py.unpackSequence(var3, 4);
            var5 = var4[0];
            var1.setlocal(11, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(12, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(13, var5);
            var5 = null;
            var5 = var4[3];
            var1.setlocal(14, var5);
            var5 = null;
            var3 = null;
            var1.setline(90);
            var10 = var1.getglobal("inspect").__getattr__("formatargspec");
            var8 = new PyObject[]{var1.getlocal(11), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14), var1.getlocal(0).__getattr__("formatvalue")};
            var7 = new String[]{"formatvalue"};
            var10 = var10.__call__(var2, var8, var7);
            var3 = null;
            var3 = var10;
            var1.setlocal(15, var3);
            var3 = null;
         } else {
            var1.setline(93);
            var6 = PyString.fromInterned("(...)");
            var1.setlocal(15, var6);
            var3 = null;
         }
      }

      var1.setline(95);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(96);
         var10 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         if (!var10.__nonzero__()) {
            var10 = var1.getlocal(15);
         }

         var3 = var10;
         var1.setlocal(15, var3);
         var3 = null;
         var1.setline(97);
         var10000 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("");
         }

         Object var9 = var10000;
         var1.setlocal(16, (PyObject)var9);
         var3 = null;
      } else {
         var1.setline(99);
         var3 = var1.getglobal("pydoc").__getattr__("getdoc").__call__(var2, var1.getlocal(1));
         var1.setlocal(16, var3);
         var3 = null;
      }

      var1.setline(101);
      var10 = var1.getlocal(10)._add(var1.getlocal(15));
      PyObject var10001 = var1.getlocal(9);
      if (var10001.__nonzero__()) {
         var10001 = var1.getlocal(0).__getattr__("grey").__call__(var2, PyString.fromInterned("<font face=\"helvetica, arial\">%s</font>")._mod(var1.getlocal(9)));
      }

      var3 = var10._add(var10001);
      var1.setlocal(17, var3);
      var3 = null;
      var1.setline(104);
      var10 = var1.getlocal(0).__getattr__("markup");
      var8 = new PyObject[]{var1.getlocal(16), var1.getlocal(0).__getattr__("preformat"), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      var3 = var10.__call__(var2, var8);
      var1.setlocal(18, var3);
      var3 = null;
      var1.setline(106);
      var10 = var1.getlocal(18);
      if (var10.__nonzero__()) {
         var10 = PyString.fromInterned("<dd><tt>%s</tt></dd>")._mod(var1.getlocal(18));
      }

      var3 = var10;
      var1.setlocal(18, var3);
      var3 = null;
      var1.setline(107);
      var3 = PyString.fromInterned("<dl><dt>%s</dt>%s</dl>\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(17), var1.getlocal(18)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject docserver$4(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("Produce HTML documentation for an XML-RPC server.");
      var1.setline(112);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(113);
      PyObject var7 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(113);
         PyObject var4 = var7.__iternext__();
         PyObject[] var5;
         PyObject var6;
         if (var4 == null) {
            var1.setline(117);
            var7 = var1.getlocal(0).__getattr__("escape").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var7);
            var3 = null;
            var1.setline(118);
            var7 = PyString.fromInterned("<big><big><strong>%s</strong></big></big>")._mod(var1.getlocal(1));
            var1.setlocal(7, var7);
            var3 = null;
            var1.setline(119);
            var7 = var1.getlocal(0).__getattr__("heading").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)PyString.fromInterned("#ffffff"), (PyObject)PyString.fromInterned("#7799ee"));
            var1.setlocal(8, var7);
            var3 = null;
            var1.setline(121);
            var7 = var1.getlocal(0).__getattr__("markup").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("preformat"), var1.getlocal(4));
            var1.setlocal(9, var7);
            var3 = null;
            var1.setline(122);
            PyObject var10000 = var1.getlocal(9);
            if (var10000.__nonzero__()) {
               var10000 = PyString.fromInterned("<tt>%s</tt>")._mod(var1.getlocal(9));
            }

            var7 = var10000;
            var1.setlocal(9, var7);
            var3 = null;
            var1.setline(123);
            var7 = var1.getlocal(8)._add(PyString.fromInterned("<p>%s</p>\n")._mod(var1.getlocal(9)));
            var1.setlocal(8, var7);
            var3 = null;
            var1.setline(125);
            PyList var9 = new PyList(Py.EmptyObjects);
            var1.setlocal(10, var9);
            var3 = null;
            var1.setline(126);
            var7 = var1.getglobal("sorted").__call__(var2, var1.getlocal(3).__getattr__("items").__call__(var2));
            var1.setlocal(11, var7);
            var3 = null;
            var1.setline(127);
            var7 = var1.getlocal(11).__iter__();

            while(true) {
               var1.setline(127);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(129);
                  var7 = var1.getlocal(8)._add(var1.getlocal(0).__getattr__("bigsection").__call__(var2, PyString.fromInterned("Methods"), PyString.fromInterned("#ffffff"), PyString.fromInterned("#eeaa77"), var1.getglobal("pydoc").__getattr__("join").__call__(var2, var1.getlocal(10))));
                  var1.setlocal(8, var7);
                  var3 = null;
                  var1.setline(132);
                  var7 = var1.getlocal(8);
                  var1.f_lasti = -1;
                  return var7;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(128);
               var10000 = var1.getlocal(10).__getattr__("append");
               PyObject var10002 = var1.getlocal(0).__getattr__("docroutine");
               var5 = new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(4)};
               String[] var10 = new String[]{"funcs"};
               var10002 = var10002.__call__(var2, var5, var10);
               var5 = null;
               var10000.__call__(var2, var10002);
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(114);
         PyObject var8 = PyString.fromInterned("#-")._add(var1.getlocal(5));
         var1.getlocal(4).__setitem__(var1.getlocal(5), var8);
         var5 = null;
         var1.setline(115);
         var8 = var1.getlocal(4).__getitem__(var1.getlocal(5));
         var1.getlocal(4).__setitem__(var1.getlocal(6), var8);
         var5 = null;
      }
   }

   public PyObject XMLRPCDocGenerator$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Generates documentation for an XML-RPC server.\n\n    This class is designed as mix-in and should not\n    be constructed directly.\n    "));
      var1.setline(139);
      PyString.fromInterned("Generates documentation for an XML-RPC server.\n\n    This class is designed as mix-in and should not\n    be constructed directly.\n    ");
      var1.setline(141);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(149);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_server_title$7, PyString.fromInterned("Set the HTML title of the generated server documentation"));
      var1.setlocal("set_server_title", var4);
      var3 = null;
      var1.setline(154);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_server_name$8, PyString.fromInterned("Set the name of the generated HTML server documentation"));
      var1.setlocal("set_server_name", var4);
      var3 = null;
      var1.setline(159);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_server_documentation$9, PyString.fromInterned("Set the documentation string for the entire server."));
      var1.setlocal("set_server_documentation", var4);
      var3 = null;
      var1.setline(164);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, generate_html_documentation$10, PyString.fromInterned("generate_html_documentation() => html documentation for the server\n\n        Generates HTML documentation for the server using introspection for\n        installed functions and instances that do not implement the\n        _dispatch method. Alternatively, instances can choose to implement\n        the _get_method_argstring(method_name) method to provide the\n        argument string used in the documentation and the\n        _methodHelp(method_name) method to provide the help text used\n        in the documentation."));
      var1.setlocal("generate_html_documentation", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString var3 = PyString.fromInterned("XML-RPC Server Documentation");
      var1.getlocal(0).__setattr__((String)"server_name", var3);
      var3 = null;
      var1.setline(144);
      var3 = PyString.fromInterned("This server exports the following methods through the XML-RPC protocol.");
      var1.getlocal(0).__setattr__((String)"server_documentation", var3);
      var3 = null;
      var1.setline(147);
      var3 = PyString.fromInterned("XML-RPC Server Documentation");
      var1.getlocal(0).__setattr__((String)"server_title", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_server_title$7(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyString.fromInterned("Set the HTML title of the generated server documentation");
      var1.setline(152);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("server_title", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_server_name$8(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyString.fromInterned("Set the name of the generated HTML server documentation");
      var1.setline(157);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("server_name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_server_documentation$9(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyString.fromInterned("Set the documentation string for the entire server.");
      var1.setline(162);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("server_documentation", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject generate_html_documentation$10(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyString.fromInterned("generate_html_documentation() => html documentation for the server\n\n        Generates HTML documentation for the server using introspection for\n        installed functions and instances that do not implement the\n        _dispatch method. Alternatively, instances can choose to implement\n        the _get_method_argstring(method_name) method to provide the\n        argument string used in the documentation and the\n        _methodHelp(method_name) method to provide the help text used\n        in the documentation.");
      var1.setline(175);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(177);
      PyObject var8 = var1.getlocal(0).__getattr__("system_listMethods").__call__(var2).__iter__();

      while(true) {
         var1.setline(177);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(206);
            var8 = var1.getglobal("ServerHTMLDoc").__call__(var2);
            var1.setlocal(5, var8);
            var3 = null;
            var1.setline(207);
            var8 = var1.getlocal(5).__getattr__("docserver").__call__(var2, var1.getlocal(0).__getattr__("server_name"), var1.getlocal(0).__getattr__("server_documentation"), var1.getlocal(1));
            var1.setlocal(6, var8);
            var3 = null;
            var1.setline(213);
            var8 = var1.getlocal(5).__getattr__("page").__call__(var2, var1.getlocal(0).__getattr__("server_title"), var1.getlocal(6));
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(2, var4);
         var1.setline(178);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("funcs"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(179);
            var5 = var1.getlocal(0).__getattr__("funcs").__getitem__(var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
         } else {
            var1.setline(180);
            var5 = var1.getlocal(0).__getattr__("instance");
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(201);
               if (var1.getglobal("__debug__").__nonzero__() && !Py.newInteger(0).__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Could not find method in self.functions and no instance installed"));
               }
            } else {
               var1.setline(181);
               PyList var9 = new PyList(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
               var1.setlocal(4, var9);
               var5 = null;
               var1.setline(182);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_get_method_argstring")).__nonzero__()) {
                  var1.setline(183);
                  var5 = var1.getlocal(0).__getattr__("instance").__getattr__("_get_method_argstring").__call__(var2, var1.getlocal(2));
                  var1.getlocal(4).__setitem__((PyObject)Py.newInteger(0), var5);
                  var5 = null;
               }

               var1.setline(184);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_methodHelp")).__nonzero__()) {
                  var1.setline(185);
                  var5 = var1.getlocal(0).__getattr__("instance").__getattr__("_methodHelp").__call__(var2, var1.getlocal(2));
                  var1.getlocal(4).__setitem__((PyObject)Py.newInteger(1), var5);
                  var5 = null;
               }

               var1.setline(187);
               var5 = var1.getglobal("tuple").__call__(var2, var1.getlocal(4));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(188);
               var5 = var1.getlocal(4);
               var10000 = var5._ne(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(189);
                  var5 = var1.getlocal(4);
                  var1.setlocal(3, var5);
                  var5 = null;
               } else {
                  var1.setline(190);
                  if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("instance"), (PyObject)PyString.fromInterned("_dispatch")).__not__().__nonzero__()) {
                     try {
                        var1.setline(192);
                        var5 = var1.getglobal("resolve_dotted_attribute").__call__(var2, var1.getlocal(0).__getattr__("instance"), var1.getlocal(2));
                        var1.setlocal(3, var5);
                        var5 = null;
                     } catch (Throwable var7) {
                        PyException var10 = Py.setException(var7, var1);
                        if (!var10.match(var1.getglobal("AttributeError"))) {
                           throw var10;
                        }

                        var1.setline(197);
                        PyObject var6 = var1.getlocal(4);
                        var1.setlocal(3, var6);
                        var6 = null;
                     }
                  } else {
                     var1.setline(199);
                     var5 = var1.getlocal(4);
                     var1.setlocal(3, var5);
                     var5 = null;
                  }
               }
            }
         }

         var1.setline(204);
         var5 = var1.getlocal(3);
         var1.getlocal(1).__setitem__(var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject DocXMLRPCRequestHandler$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("XML-RPC and documentation request handler class.\n\n    Handles all HTTP POST requests and attempts to decode them as\n    XML-RPC requests.\n\n    Handles all HTTP GET requests and interprets them as requests\n    for documentation.\n    "));
      var1.setline(223);
      PyString.fromInterned("XML-RPC and documentation request handler class.\n\n    Handles all HTTP POST requests and attempts to decode them as\n    XML-RPC requests.\n\n    Handles all HTTP GET requests and interprets them as requests\n    for documentation.\n    ");
      var1.setline(225);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, do_GET$12, PyString.fromInterned("Handles the HTTP GET request.\n\n        Interpret all HTTP GET requests as requests for server\n        documentation.\n        "));
      var1.setlocal("do_GET", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject do_GET$12(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("Handles the HTTP GET request.\n\n        Interpret all HTTP GET requests as requests for server\n        documentation.\n        ");
      var1.setline(232);
      if (var1.getlocal(0).__getattr__("is_rpc_path_valid").__call__(var2).__not__().__nonzero__()) {
         var1.setline(233);
         var1.getlocal(0).__getattr__("report_404").__call__(var2);
         var1.setline(234);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(236);
         PyObject var3 = var1.getlocal(0).__getattr__("server").__getattr__("generate_html_documentation").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(237);
         var1.getlocal(0).__getattr__("send_response").__call__((ThreadState)var2, (PyObject)Py.newInteger(200));
         var1.setline(238);
         var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-type"), (PyObject)PyString.fromInterned("text/html"));
         var1.setline(239);
         var1.getlocal(0).__getattr__("send_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-length"), (PyObject)var1.getglobal("str").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))));
         var1.setline(240);
         var1.getlocal(0).__getattr__("end_headers").__call__(var2);
         var1.setline(241);
         var1.getlocal(0).__getattr__("wfile").__getattr__("write").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject DocXMLRPCServer$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("XML-RPC and HTML documentation server.\n\n    Adds the ability to serve server documentation to the capabilities\n    of SimpleXMLRPCServer.\n    "));
      var1.setline(249);
      PyString.fromInterned("XML-RPC and HTML documentation server.\n\n    Adds the ability to serve server documentation to the capabilities\n    of SimpleXMLRPCServer.\n    ");
      var1.setline(251);
      PyObject[] var3 = new PyObject[]{var1.getname("DocXMLRPCRequestHandler"), Py.newInteger(1), var1.getname("False"), var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyObject var10000 = var1.getglobal("SimpleXMLRPCServer").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      var10000.__call__(var2, var3);
      var1.setline(256);
      var1.getglobal("XMLRPCDocGenerator").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DocCGIXMLRPCRequestHandler$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handler for XML-RPC data and documentation requests passed through\n    CGI"));
      var1.setline(261);
      PyString.fromInterned("Handler for XML-RPC data and documentation requests passed through\n    CGI");
      var1.setline(263);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, handle_get$16, PyString.fromInterned("Handles the HTTP GET request.\n\n        Interpret all HTTP GET requests as requests for server\n        documentation.\n        "));
      var1.setlocal("handle_get", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject handle_get$16(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyString.fromInterned("Handles the HTTP GET request.\n\n        Interpret all HTTP GET requests as requests for server\n        documentation.\n        ");
      var1.setline(270);
      PyObject var3 = var1.getlocal(0).__getattr__("generate_html_documentation").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(272);
      Py.println(PyString.fromInterned("Content-Type: text/html"));
      var1.setline(273);
      Py.println(PyString.fromInterned("Content-Length: %d")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(1))));
      var1.setline(274);
      Py.println();
      var1.setline(275);
      var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      var1.getglobal("CGIXMLRPCRequestHandler").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(279);
      var1.getglobal("XMLRPCDocGenerator").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public DocXMLRPCServer$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ServerHTMLDoc$1 = Py.newCode(0, var2, var1, "ServerHTMLDoc", 23, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "text", "escape", "funcs", "classes", "methods", "results", "here", "pattern", "match", "start", "end", "all", "scheme", "rfc", "pep", "selfdot", "name", "url"};
      markup$2 = Py.newCode(6, var2, var1, "markup", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object", "name", "mod", "funcs", "classes", "methods", "cl", "anchor", "note", "title", "args", "varargs", "varkw", "defaults", "argspec", "docstring", "decl", "doc"};
      docroutine$3 = Py.newCode(8, var2, var1, "docroutine", 67, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "server_name", "package_documentation", "methods", "fdict", "key", "value", "head", "result", "doc", "contents", "method_items"};
      docserver$4 = Py.newCode(4, var2, var1, "docserver", 109, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      XMLRPCDocGenerator$5 = Py.newCode(0, var2, var1, "XMLRPCDocGenerator", 134, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$6 = Py.newCode(1, var2, var1, "__init__", 141, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "server_title"};
      set_server_title$7 = Py.newCode(2, var2, var1, "set_server_title", 149, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "server_name"};
      set_server_name$8 = Py.newCode(2, var2, var1, "set_server_name", 154, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "server_documentation"};
      set_server_documentation$9 = Py.newCode(2, var2, var1, "set_server_documentation", 159, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "methods", "method_name", "method", "method_info", "documenter", "documentation"};
      generate_html_documentation$10 = Py.newCode(1, var2, var1, "generate_html_documentation", 164, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocXMLRPCRequestHandler$11 = Py.newCode(0, var2, var1, "DocXMLRPCRequestHandler", 215, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "response"};
      do_GET$12 = Py.newCode(1, var2, var1, "do_GET", 225, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocXMLRPCServer$13 = Py.newCode(0, var2, var1, "DocXMLRPCServer", 243, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "addr", "requestHandler", "logRequests", "allow_none", "encoding", "bind_and_activate"};
      __init__$14 = Py.newCode(7, var2, var1, "__init__", 251, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DocCGIXMLRPCRequestHandler$15 = Py.newCode(0, var2, var1, "DocCGIXMLRPCRequestHandler", 258, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "response"};
      handle_get$16 = Py.newCode(1, var2, var1, "handle_get", 263, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __init__$17 = Py.newCode(1, var2, var1, "__init__", 277, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new DocXMLRPCServer$py("DocXMLRPCServer$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(DocXMLRPCServer$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ServerHTMLDoc$1(var2, var3);
         case 2:
            return this.markup$2(var2, var3);
         case 3:
            return this.docroutine$3(var2, var3);
         case 4:
            return this.docserver$4(var2, var3);
         case 5:
            return this.XMLRPCDocGenerator$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.set_server_title$7(var2, var3);
         case 8:
            return this.set_server_name$8(var2, var3);
         case 9:
            return this.set_server_documentation$9(var2, var3);
         case 10:
            return this.generate_html_documentation$10(var2, var3);
         case 11:
            return this.DocXMLRPCRequestHandler$11(var2, var3);
         case 12:
            return this.do_GET$12(var2, var3);
         case 13:
            return this.DocXMLRPCServer$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.DocCGIXMLRPCRequestHandler$15(var2, var3);
         case 16:
            return this.handle_get$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         default:
            return null;
      }
   }
}
