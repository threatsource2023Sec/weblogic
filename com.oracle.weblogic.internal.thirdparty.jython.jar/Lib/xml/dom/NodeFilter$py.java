package xml.dom;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("xml/dom/NodeFilter.py")
public class NodeFilter$py extends PyFunctionTable implements PyRunnable {
   static NodeFilter$py self;
   static final PyCode f$0;
   static final PyCode NodeFilter$1;
   static final PyCode acceptNode$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(4);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("NodeFilter", var3, NodeFilter$1);
      var1.setlocal("NodeFilter", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NodeFilter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This is the DOM2 NodeFilter interface. It contains only constants.\n    "));
      var1.setline(7);
      PyString.fromInterned("\n    This is the DOM2 NodeFilter interface. It contains only constants.\n    ");
      var1.setline(8);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("FILTER_ACCEPT", var3);
      var3 = null;
      var1.setline(9);
      var3 = Py.newInteger(2);
      var1.setlocal("FILTER_REJECT", var3);
      var3 = null;
      var1.setline(10);
      var3 = Py.newInteger(3);
      var1.setlocal("FILTER_SKIP", var3);
      var3 = null;
      var1.setline(12);
      PyLong var4 = Py.newLong("4294967295");
      var1.setlocal("SHOW_ALL", var4);
      var3 = null;
      var1.setline(13);
      var3 = Py.newInteger(1);
      var1.setlocal("SHOW_ELEMENT", var3);
      var3 = null;
      var1.setline(14);
      var3 = Py.newInteger(2);
      var1.setlocal("SHOW_ATTRIBUTE", var3);
      var3 = null;
      var1.setline(15);
      var3 = Py.newInteger(4);
      var1.setlocal("SHOW_TEXT", var3);
      var3 = null;
      var1.setline(16);
      var3 = Py.newInteger(8);
      var1.setlocal("SHOW_CDATA_SECTION", var3);
      var3 = null;
      var1.setline(17);
      var3 = Py.newInteger(16);
      var1.setlocal("SHOW_ENTITY_REFERENCE", var3);
      var3 = null;
      var1.setline(18);
      var3 = Py.newInteger(32);
      var1.setlocal("SHOW_ENTITY", var3);
      var3 = null;
      var1.setline(19);
      var3 = Py.newInteger(64);
      var1.setlocal("SHOW_PROCESSING_INSTRUCTION", var3);
      var3 = null;
      var1.setline(20);
      var3 = Py.newInteger(128);
      var1.setlocal("SHOW_COMMENT", var3);
      var3 = null;
      var1.setline(21);
      var3 = Py.newInteger(256);
      var1.setlocal("SHOW_DOCUMENT", var3);
      var3 = null;
      var1.setline(22);
      var3 = Py.newInteger(512);
      var1.setlocal("SHOW_DOCUMENT_TYPE", var3);
      var3 = null;
      var1.setline(23);
      var3 = Py.newInteger(1024);
      var1.setlocal("SHOW_DOCUMENT_FRAGMENT", var3);
      var3 = null;
      var1.setline(24);
      var3 = Py.newInteger(2048);
      var1.setlocal("SHOW_NOTATION", var3);
      var3 = null;
      var1.setline(26);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, acceptNode$2, (PyObject)null);
      var1.setlocal("acceptNode", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject acceptNode$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public NodeFilter$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NodeFilter$1 = Py.newCode(0, var2, var1, "NodeFilter", 4, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node"};
      acceptNode$2 = Py.newCode(2, var2, var1, "acceptNode", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new NodeFilter$py("xml/dom/NodeFilter$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(NodeFilter$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NodeFilter$1(var2, var3);
         case 2:
            return this.acceptNode$2(var2, var3);
         default:
            return null;
      }
   }
}
