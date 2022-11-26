package xml.etree;

import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("xml/etree/cElementTree.py")
public class cElementTree$py extends PyFunctionTable implements PyRunnable {
   static cElementTree$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(3);
      String[] var3 = new String[]{"Comment", "Element", "ElementPath", "ElementTree", "PI", "ParseError", "ProcessingInstruction", "QName", "SubElement", "TreeBuilder", "VERSION", "XML", "XMLID", "XMLParser", "XMLTreeBuilder", "_Element", "_ElementInterface", "_SimpleElementPath", "__all__", "__doc__", "__file__", "__name__", "__package__", "_encode", "_escape_attrib", "_escape_cdata", "_namespace_map", "_raise_serialization_error", "dump", "fromstring", "fromstringlist", "iselement", "iterparse", "parse", "re", "register_namespace", "sys", "tostring", "tostringlist"};
      PyObject[] var5 = imp.importFrom("xml.etree.ElementTree", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("Comment", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Element", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("ElementPath", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("ElementTree", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("PI", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("ParseError", var4);
      var4 = null;
      var4 = var5[6];
      var1.setlocal("ProcessingInstruction", var4);
      var4 = null;
      var4 = var5[7];
      var1.setlocal("QName", var4);
      var4 = null;
      var4 = var5[8];
      var1.setlocal("SubElement", var4);
      var4 = null;
      var4 = var5[9];
      var1.setlocal("TreeBuilder", var4);
      var4 = null;
      var4 = var5[10];
      var1.setlocal("VERSION", var4);
      var4 = null;
      var4 = var5[11];
      var1.setlocal("XML", var4);
      var4 = null;
      var4 = var5[12];
      var1.setlocal("XMLID", var4);
      var4 = null;
      var4 = var5[13];
      var1.setlocal("XMLParser", var4);
      var4 = null;
      var4 = var5[14];
      var1.setlocal("XMLTreeBuilder", var4);
      var4 = null;
      var4 = var5[15];
      var1.setlocal("_Element", var4);
      var4 = null;
      var4 = var5[16];
      var1.setlocal("_ElementInterface", var4);
      var4 = null;
      var4 = var5[17];
      var1.setlocal("_SimpleElementPath", var4);
      var4 = null;
      var4 = var5[18];
      var1.setlocal("__all__", var4);
      var4 = null;
      var4 = var5[19];
      var1.setlocal("__doc__", var4);
      var4 = null;
      var4 = var5[20];
      var1.setlocal("__file__", var4);
      var4 = null;
      var4 = var5[21];
      var1.setlocal("__name__", var4);
      var4 = null;
      var4 = var5[22];
      var1.setlocal("__package__", var4);
      var4 = null;
      var4 = var5[23];
      var1.setlocal("_encode", var4);
      var4 = null;
      var4 = var5[24];
      var1.setlocal("_escape_attrib", var4);
      var4 = null;
      var4 = var5[25];
      var1.setlocal("_escape_cdata", var4);
      var4 = null;
      var4 = var5[26];
      var1.setlocal("_namespace_map", var4);
      var4 = null;
      var4 = var5[27];
      var1.setlocal("_raise_serialization_error", var4);
      var4 = null;
      var4 = var5[28];
      var1.setlocal("dump", var4);
      var4 = null;
      var4 = var5[29];
      var1.setlocal("fromstring", var4);
      var4 = null;
      var4 = var5[30];
      var1.setlocal("fromstringlist", var4);
      var4 = null;
      var4 = var5[31];
      var1.setlocal("iselement", var4);
      var4 = null;
      var4 = var5[32];
      var1.setlocal("iterparse", var4);
      var4 = null;
      var4 = var5[33];
      var1.setlocal("parse", var4);
      var4 = null;
      var4 = var5[34];
      var1.setlocal("re", var4);
      var4 = null;
      var4 = var5[35];
      var1.setlocal("register_namespace", var4);
      var4 = null;
      var4 = var5[36];
      var1.setlocal("sys", var4);
      var4 = null;
      var4 = var5[37];
      var1.setlocal("tostring", var4);
      var4 = null;
      var4 = var5[38];
      var1.setlocal("tostringlist", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public cElementTree$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cElementTree$py("xml/etree/cElementTree$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cElementTree$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         default:
            return null;
      }
   }
}
