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
@Filename("formatter.py")
public class formatter$py extends PyFunctionTable implements PyRunnable {
   static formatter$py self;
   static final PyCode f$0;
   static final PyCode NullFormatter$1;
   static final PyCode __init__$2;
   static final PyCode end_paragraph$3;
   static final PyCode add_line_break$4;
   static final PyCode add_hor_rule$5;
   static final PyCode add_label_data$6;
   static final PyCode add_flowing_data$7;
   static final PyCode add_literal_data$8;
   static final PyCode flush_softspace$9;
   static final PyCode push_alignment$10;
   static final PyCode pop_alignment$11;
   static final PyCode push_font$12;
   static final PyCode pop_font$13;
   static final PyCode push_margin$14;
   static final PyCode pop_margin$15;
   static final PyCode set_spacing$16;
   static final PyCode push_style$17;
   static final PyCode pop_style$18;
   static final PyCode assert_line_data$19;
   static final PyCode AbstractFormatter$20;
   static final PyCode __init__$21;
   static final PyCode end_paragraph$22;
   static final PyCode add_line_break$23;
   static final PyCode add_hor_rule$24;
   static final PyCode add_label_data$25;
   static final PyCode format_counter$26;
   static final PyCode format_letter$27;
   static final PyCode format_roman$28;
   static final PyCode add_flowing_data$29;
   static final PyCode add_literal_data$30;
   static final PyCode flush_softspace$31;
   static final PyCode push_alignment$32;
   static final PyCode pop_alignment$33;
   static final PyCode push_font$34;
   static final PyCode pop_font$35;
   static final PyCode push_margin$36;
   static final PyCode pop_margin$37;
   static final PyCode set_spacing$38;
   static final PyCode push_style$39;
   static final PyCode pop_style$40;
   static final PyCode assert_line_data$41;
   static final PyCode NullWriter$42;
   static final PyCode __init__$43;
   static final PyCode flush$44;
   static final PyCode new_alignment$45;
   static final PyCode new_font$46;
   static final PyCode new_margin$47;
   static final PyCode new_spacing$48;
   static final PyCode new_styles$49;
   static final PyCode send_paragraph$50;
   static final PyCode send_line_break$51;
   static final PyCode send_hor_rule$52;
   static final PyCode send_label_data$53;
   static final PyCode send_flowing_data$54;
   static final PyCode send_literal_data$55;
   static final PyCode AbstractWriter$56;
   static final PyCode new_alignment$57;
   static final PyCode new_font$58;
   static final PyCode new_margin$59;
   static final PyCode new_spacing$60;
   static final PyCode new_styles$61;
   static final PyCode send_paragraph$62;
   static final PyCode send_line_break$63;
   static final PyCode send_hor_rule$64;
   static final PyCode send_label_data$65;
   static final PyCode send_flowing_data$66;
   static final PyCode send_literal_data$67;
   static final PyCode DumbWriter$68;
   static final PyCode __init__$69;
   static final PyCode reset$70;
   static final PyCode send_paragraph$71;
   static final PyCode send_line_break$72;
   static final PyCode send_hor_rule$73;
   static final PyCode send_literal_data$74;
   static final PyCode send_flowing_data$75;
   static final PyCode test$76;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Generic output formatting.\n\nFormatter objects transform an abstract flow of formatting events into\nspecific output events on writer objects. Formatters manage several stack\nstructures to allow various properties of a writer object to be changed and\nrestored; writers need not be able to handle relative changes nor any sort\nof ``change back'' operation. Specific writer properties which may be\ncontrolled via formatter objects are horizontal alignment, font, and left\nmargin indentations. A mechanism is provided which supports providing\narbitrary, non-exclusive style settings to a writer as well. Additional\ninterfaces facilitate formatting events which are not reversible, such as\nparagraph separation.\n\nWriter objects encapsulate device interfaces. Abstract devices, such as\nfile formats, are supported as well as physical devices. The provided\nimplementations all work with abstract devices. The interface makes\navailable mechanisms for setting the properties which formatter objects\nmanage and inserting data into the output.\n"));
      var1.setline(19);
      PyString.fromInterned("Generic output formatting.\n\nFormatter objects transform an abstract flow of formatting events into\nspecific output events on writer objects. Formatters manage several stack\nstructures to allow various properties of a writer object to be changed and\nrestored; writers need not be able to handle relative changes nor any sort\nof ``change back'' operation. Specific writer properties which may be\ncontrolled via formatter objects are horizontal alignment, font, and left\nmargin indentations. A mechanism is provided which supports providing\narbitrary, non-exclusive style settings to a writer as well. Additional\ninterfaces facilitate formatting events which are not reversible, such as\nparagraph separation.\n\nWriter objects encapsulate device interfaces. Abstract devices, such as\nfile formats, are supported as well as physical devices. The provided\nimplementations all work with abstract devices. The interface makes\navailable mechanisms for setting the properties which formatter objects\nmanage and inserting data into the output.\n");
      var1.setline(21);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(24);
      var3 = var1.getname("None");
      var1.setlocal("AS_IS", var3);
      var3 = null;
      var1.setline(27);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("NullFormatter", var5, NullFormatter$1);
      var1.setlocal("NullFormatter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(61);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("AbstractFormatter", var5, AbstractFormatter$20);
      var1.setlocal("AbstractFormatter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(295);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("NullWriter", var5, NullWriter$42);
      var1.setlocal("NullWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(318);
      var5 = new PyObject[]{var1.getname("NullWriter")};
      var4 = Py.makeClass("AbstractWriter", var5, AbstractWriter$56);
      var1.setlocal("AbstractWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(360);
      var5 = new PyObject[]{var1.getname("NullWriter")};
      var4 = Py.makeClass("DumbWriter", var5, DumbWriter$68);
      var1.setlocal("DumbWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(427);
      var5 = new PyObject[]{var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, test$76, (PyObject)null);
      var1.setlocal("test", var6);
      var3 = null;
      var1.setline(444);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(445);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NullFormatter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A formatter which does nothing.\n\n    If the writer parameter is omitted, a NullWriter instance is created.\n    No methods of the writer are called by NullFormatter instances.\n\n    Implementations should inherit from this class if implementing a writer\n    interface but don't need to inherit any implementation.\n\n    "));
      var1.setline(36);
      PyString.fromInterned("A formatter which does nothing.\n\n    If the writer parameter is omitted, a NullWriter instance is created.\n    No methods of the writer are called by NullFormatter instances.\n\n    Implementations should inherit from this class if implementing a writer\n    interface but don't need to inherit any implementation.\n\n    ");
      var1.setline(38);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_paragraph$3, (PyObject)null);
      var1.setlocal("end_paragraph", var4);
      var3 = null;
      var1.setline(43);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_line_break$4, (PyObject)null);
      var1.setlocal("add_line_break", var4);
      var3 = null;
      var1.setline(44);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_hor_rule$5, (PyObject)null);
      var1.setlocal("add_hor_rule", var4);
      var3 = null;
      var1.setline(45);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, add_label_data$6, (PyObject)null);
      var1.setlocal("add_label_data", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_flowing_data$7, (PyObject)null);
      var1.setlocal("add_flowing_data", var4);
      var3 = null;
      var1.setline(47);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_literal_data$8, (PyObject)null);
      var1.setlocal("add_literal_data", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush_softspace$9, (PyObject)null);
      var1.setlocal("flush_softspace", var4);
      var3 = null;
      var1.setline(49);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_alignment$10, (PyObject)null);
      var1.setlocal("push_alignment", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_alignment$11, (PyObject)null);
      var1.setlocal("pop_alignment", var4);
      var3 = null;
      var1.setline(51);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_font$12, (PyObject)null);
      var1.setlocal("push_font", var4);
      var3 = null;
      var1.setline(52);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_font$13, (PyObject)null);
      var1.setlocal("pop_font", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_margin$14, (PyObject)null);
      var1.setlocal("push_margin", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_margin$15, (PyObject)null);
      var1.setlocal("pop_margin", var4);
      var3 = null;
      var1.setline(55);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_spacing$16, (PyObject)null);
      var1.setlocal("set_spacing", var4);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_style$17, (PyObject)null);
      var1.setlocal("push_style", var4);
      var3 = null;
      var1.setline(57);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, pop_style$18, (PyObject)null);
      var1.setlocal("pop_style", var4);
      var3 = null;
      var1.setline(58);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, assert_line_data$19, (PyObject)null);
      var1.setlocal("assert_line_data", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(40);
         var3 = var1.getglobal("NullWriter").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(41);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("writer", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_paragraph$3(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_line_break$4(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_hor_rule$5(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_label_data$6(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_flowing_data$7(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_literal_data$8(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush_softspace$9(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_alignment$10(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_alignment$11(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_font$12(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_font$13(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_margin$14(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_margin$15(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_spacing$16(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_style$17(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_style$18(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assert_line_data$19(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject AbstractFormatter$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The standard formatter.\n\n    This implementation has demonstrated wide applicability to many writers,\n    and may be used directly in most circumstances.  It has been used to\n    implement a full-featured World Wide Web browser.\n\n    "));
      var1.setline(68);
      PyString.fromInterned("The standard formatter.\n\n    This implementation has demonstrated wide applicability to many writers,\n    and may be used directly in most circumstances.  It has been used to\n    implement a full-featured World Wide Web browser.\n\n    ");
      var1.setline(75);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(90);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, end_paragraph$22, (PyObject)null);
      var1.setlocal("end_paragraph", var4);
      var3 = null;
      var1.setline(101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_line_break$23, (PyObject)null);
      var1.setlocal("add_line_break", var4);
      var3 = null;
      var1.setline(108);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_hor_rule$24, (PyObject)null);
      var1.setlocal("add_hor_rule", var4);
      var3 = null;
      var1.setline(115);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, add_label_data$25, (PyObject)null);
      var1.setlocal("add_label_data", var4);
      var3 = null;
      var1.setline(127);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_counter$26, (PyObject)null);
      var1.setlocal("format_counter", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_letter$27, (PyObject)null);
      var1.setlocal("format_letter", var4);
      var3 = null;
      var1.setline(153);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_roman$28, (PyObject)null);
      var1.setlocal("format_roman", var4);
      var3 = null;
      var1.setline(177);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_flowing_data$29, (PyObject)null);
      var1.setlocal("add_flowing_data", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_literal_data$30, (PyObject)null);
      var1.setlocal("add_literal_data", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush_softspace$31, (PyObject)null);
      var1.setlocal("flush_softspace", var4);
      var3 = null;
      var1.setline(213);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_alignment$32, (PyObject)null);
      var1.setlocal("push_alignment", var4);
      var3 = null;
      var1.setline(221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_alignment$33, (PyObject)null);
      var1.setlocal("pop_alignment", var4);
      var3 = null;
      var1.setline(231);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_font$34, (PyObject)null);
      var1.setlocal("push_font", var4);
      var3 = null;
      var1.setline(247);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_font$35, (PyObject)null);
      var1.setlocal("pop_font", var4);
      var3 = null;
      var1.setline(256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_margin$36, (PyObject)null);
      var1.setlocal("push_margin", var4);
      var3 = null;
      var1.setline(263);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pop_margin$37, (PyObject)null);
      var1.setlocal("pop_margin", var4);
      var3 = null;
      var1.setline(273);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_spacing$38, (PyObject)null);
      var1.setlocal("set_spacing", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, push_style$39, (PyObject)null);
      var1.setlocal("push_style", var4);
      var3 = null;
      var1.setline(286);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, pop_style$40, (PyObject)null);
      var1.setlocal("pop_style", var4);
      var3 = null;
      var1.setline(290);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, assert_line_data$41, (PyObject)null);
      var1.setlocal("assert_line_data", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("writer", var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("align", var3);
      var3 = null;
      var1.setline(78);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"align_stack", var4);
      var3 = null;
      var1.setline(79);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"font_stack", var4);
      var3 = null;
      var1.setline(80);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"margin_stack", var4);
      var3 = null;
      var1.setline(81);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("spacing", var3);
      var3 = null;
      var1.setline(82);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"style_stack", var4);
      var3 = null;
      var1.setline(83);
      PyInteger var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"nospace", var5);
      var3 = null;
      var1.setline(84);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"softspace", var5);
      var3 = null;
      var1.setline(85);
      var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"para_end", var5);
      var3 = null;
      var1.setline(86);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"parskip", var5);
      var3 = null;
      var1.setline(87);
      var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"hard_break", var5);
      var3 = null;
      var1.setline(88);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"have_label", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_paragraph$22(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyInteger var3;
      if (var1.getlocal(0).__getattr__("hard_break").__not__().__nonzero__()) {
         var1.setline(92);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_line_break").__call__(var2);
         var1.setline(93);
         var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"have_label", var3);
         var3 = null;
      }

      var1.setline(94);
      PyObject var4 = var1.getlocal(0).__getattr__("parskip");
      PyObject var10000 = var4._lt(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("have_label").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(95);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_paragraph").__call__(var2, var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("parskip")));
         var1.setline(96);
         var4 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("parskip", var4);
         var3 = null;
         var1.setline(97);
         var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"have_label", var3);
         var3 = null;
      }

      var1.setline(98);
      var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"hard_break", var3);
      var1.getlocal(0).__setattr__((String)"nospace", var3);
      var1.getlocal(0).__setattr__((String)"para_end", var3);
      var1.setline(99);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"softspace", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_line_break$23(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var10000 = var1.getlocal(0).__getattr__("hard_break");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("para_end");
      }

      PyInteger var3;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(103);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_line_break").__call__(var2);
         var1.setline(104);
         var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"have_label", var3);
         var1.getlocal(0).__setattr__((String)"parskip", var3);
      }

      var1.setline(105);
      var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"hard_break", var3);
      var1.getlocal(0).__setattr__((String)"nospace", var3);
      var1.setline(106);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"softspace", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_hor_rule$24(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      if (var1.getlocal(0).__getattr__("hard_break").__not__().__nonzero__()) {
         var1.setline(110);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_line_break").__call__(var2);
      }

      var1.setline(111);
      PyObject var10000 = var1.getlocal(0).__getattr__("writer").__getattr__("send_hor_rule");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.setline(112);
      PyInteger var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"hard_break", var5);
      var1.getlocal(0).__setattr__((String)"nospace", var5);
      var1.setline(113);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"have_label", var5);
      var1.getlocal(0).__setattr__((String)"para_end", var5);
      var1.getlocal(0).__setattr__((String)"softspace", var5);
      var1.getlocal(0).__setattr__((String)"parskip", var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_label_data$25(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var10000 = var1.getlocal(0).__getattr__("have_label");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("hard_break").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(117);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_line_break").__call__(var2);
      }

      var1.setline(118);
      if (var1.getlocal(0).__getattr__("para_end").__not__().__nonzero__()) {
         var1.setline(119);
         var10000 = var1.getlocal(0).__getattr__("writer").__getattr__("send_paragraph");
         Object var10002 = var1.getlocal(3);
         if (((PyObject)var10002).__nonzero__()) {
            var10002 = Py.newInteger(1);
         }

         if (!((PyObject)var10002).__nonzero__()) {
            var10002 = Py.newInteger(0);
         }

         var10000.__call__((ThreadState)var2, (PyObject)var10002);
      }

      var1.setline(120);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(121);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_label_data").__call__(var2, var1.getlocal(0).__getattr__("format_counter").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      } else {
         var1.setline(123);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_label_data").__call__(var2, var1.getlocal(1));
      }

      var1.setline(124);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"nospace", var3);
      var1.getlocal(0).__setattr__((String)"have_label", var3);
      var1.getlocal(0).__setattr__((String)"hard_break", var3);
      var1.getlocal(0).__setattr__((String)"para_end", var3);
      var1.setline(125);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"softspace", var3);
      var1.getlocal(0).__setattr__((String)"parskip", var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_counter$26(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(129);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(129);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(140);
            var6 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(130);
         PyObject var5 = var1.getlocal(4);
         PyObject var10000 = var5._eq(PyString.fromInterned("1"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var5 = var1.getlocal(3)._add(PyString.fromInterned("%d")._mod(var1.getlocal(2)));
            var1.setlocal(3, var5);
            var5 = null;
         } else {
            var1.setline(132);
            var5 = var1.getlocal(4);
            var10000 = var5._in(PyString.fromInterned("aA"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(133);
               var5 = var1.getlocal(2);
               var10000 = var5._gt(Py.newInteger(0));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(134);
                  var5 = var1.getlocal(3)._add(var1.getlocal(0).__getattr__("format_letter").__call__(var2, var1.getlocal(4), var1.getlocal(2)));
                  var1.setlocal(3, var5);
                  var5 = null;
               }
            } else {
               var1.setline(135);
               var5 = var1.getlocal(4);
               var10000 = var5._in(PyString.fromInterned("iI"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(136);
                  var5 = var1.getlocal(2);
                  var10000 = var5._gt(Py.newInteger(0));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(137);
                     var5 = var1.getlocal(3)._add(var1.getlocal(0).__getattr__("format_roman").__call__(var2, var1.getlocal(4), var1.getlocal(2)));
                     var1.setlocal(3, var5);
                     var5 = null;
                  }
               } else {
                  var1.setline(139);
                  var5 = var1.getlocal(3)._add(var1.getlocal(4));
                  var1.setlocal(3, var5);
                  var5 = null;
               }
            }
         }
      }
   }

   public PyObject format_letter$27(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(144);
         PyObject var6 = var1.getlocal(2);
         PyObject var10000 = var6._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(151);
            var6 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setline(145);
         var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2)._sub(Py.newInteger(1)), (PyObject)Py.newInteger(26));
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(149);
         var6 = var1.getglobal("chr").__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(1))._add(var1.getlocal(4)));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(150);
         var6 = var1.getlocal(5)._add(var1.getlocal(3));
         var1.setlocal(3, var6);
         var3 = null;
      }
   }

   public PyObject format_roman$28(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("i"), PyString.fromInterned("x"), PyString.fromInterned("c"), PyString.fromInterned("m")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(155);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("v"), PyString.fromInterned("l"), PyString.fromInterned("d")});
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(156);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0)});
      PyObject[] var4 = Py.unpackSequence(var6, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;

      while(true) {
         var1.setline(158);
         PyObject var7 = var1.getlocal(2);
         PyObject var10000 = var7._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(173);
            var7 = var1.getlocal(1);
            var10000 = var7._eq(PyString.fromInterned("I"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(174);
               var7 = var1.getlocal(5).__getattr__("upper").__call__(var2);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(175);
            var7 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(159);
         var7 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(10));
         var4 = Py.unpackSequence(var7, 2);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(160);
         var7 = var1.getlocal(7);
         var10000 = var7._eq(Py.newInteger(9));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(161);
            var7 = var1.getlocal(3).__getitem__(var1.getlocal(6))._add(var1.getlocal(3).__getitem__(var1.getlocal(6)._add(Py.newInteger(1))))._add(var1.getlocal(5));
            var1.setlocal(5, var7);
            var3 = null;
         } else {
            var1.setline(162);
            var7 = var1.getlocal(7);
            var10000 = var7._eq(Py.newInteger(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(163);
               var7 = var1.getlocal(3).__getitem__(var1.getlocal(6))._add(var1.getlocal(4).__getitem__(var1.getlocal(6)))._add(var1.getlocal(5));
               var1.setlocal(5, var7);
               var3 = null;
            } else {
               var1.setline(165);
               var7 = var1.getlocal(7);
               var10000 = var7._ge(Py.newInteger(5));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(166);
                  var7 = var1.getlocal(4).__getitem__(var1.getlocal(6));
                  var1.setlocal(8, var7);
                  var3 = null;
                  var1.setline(167);
                  var7 = var1.getlocal(7)._sub(Py.newInteger(5));
                  var1.setlocal(7, var7);
                  var3 = null;
               } else {
                  var1.setline(169);
                  PyString var8 = PyString.fromInterned("");
                  var1.setlocal(8, var8);
                  var3 = null;
               }

               var1.setline(170);
               var7 = var1.getlocal(8)._add(var1.getlocal(3).__getitem__(var1.getlocal(6))._mul(var1.getlocal(7)));
               var1.setlocal(8, var7);
               var3 = null;
               var1.setline(171);
               var7 = var1.getlocal(8)._add(var1.getlocal(5));
               var1.setlocal(5, var7);
               var3 = null;
            }
         }

         var1.setline(172);
         var7 = var1.getlocal(6)._add(Py.newInteger(1));
         var1.setlocal(6, var7);
         var3 = null;
      }
   }

   public PyObject add_flowing_data$29(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(178);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(179);
         PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null).__getattr__("isspace").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(180);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null).__getattr__("isspace").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(181);
         var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(1).__getattr__("split").__call__(var2));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(182);
         PyObject var10000 = var1.getlocal(0).__getattr__("nospace");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(183);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(184);
            var10000 = var1.getlocal(2);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("softspace");
            }

            PyInteger var4;
            if (var10000.__nonzero__()) {
               var1.setline(185);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(186);
                  if (var1.getlocal(0).__getattr__("nospace").__not__().__nonzero__()) {
                     var1.setline(187);
                     var4 = Py.newInteger(1);
                     var1.getlocal(0).__setattr__((String)"softspace", var4);
                     var3 = null;
                     var1.setline(188);
                     var4 = Py.newInteger(0);
                     var1.getlocal(0).__setattr__((String)"parskip", var4);
                     var3 = null;
                  }

                  var1.setline(189);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(190);
               if (var1.getlocal(0).__getattr__("nospace").__not__().__nonzero__()) {
                  var1.setline(191);
                  var3 = PyString.fromInterned(" ")._add(var1.getlocal(1));
                  var1.setlocal(1, var3);
                  var3 = null;
               }
            }

            var1.setline(192);
            var4 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"hard_break", var4);
            var1.getlocal(0).__setattr__((String)"nospace", var4);
            var1.getlocal(0).__setattr__((String)"para_end", var4);
            var1.getlocal(0).__setattr__((String)"parskip", var4);
            var1.getlocal(0).__setattr__((String)"have_label", var4);
            var1.setline(194);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("softspace", var3);
            var3 = null;
            var1.setline(195);
            var1.getlocal(0).__getattr__("writer").__getattr__("send_flowing_data").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject add_literal_data$30(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(198);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(199);
         if (var1.getlocal(0).__getattr__("softspace").__nonzero__()) {
            var1.setline(200);
            var1.getlocal(0).__getattr__("writer").__getattr__("send_flowing_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
         }

         var1.setline(201);
         PyObject var3 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
         PyObject var10000 = var3._eq(PyString.fromInterned("\n"));
         var3 = null;
         var3 = var10000;
         var1.getlocal(0).__setattr__("hard_break", var3);
         var3 = null;
         var1.setline(202);
         PyInteger var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"nospace", var4);
         var1.getlocal(0).__setattr__((String)"para_end", var4);
         var1.getlocal(0).__setattr__((String)"softspace", var4);
         var1.getlocal(0).__setattr__((String)"parskip", var4);
         var1.getlocal(0).__setattr__((String)"have_label", var4);
         var1.setline(204);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_literal_data").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject flush_softspace$31(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      if (var1.getlocal(0).__getattr__("softspace").__nonzero__()) {
         var1.setline(208);
         PyInteger var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"hard_break", var3);
         var1.getlocal(0).__setattr__((String)"para_end", var3);
         var1.getlocal(0).__setattr__((String)"parskip", var3);
         var1.getlocal(0).__setattr__((String)"have_label", var3);
         var1.getlocal(0).__setattr__((String)"softspace", var3);
         var1.setline(210);
         var3 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"nospace", var3);
         var3 = null;
         var1.setline(211);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_flowing_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_alignment$32(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      PyObject var10000 = var1.getlocal(1);
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getlocal(0).__getattr__("align"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(215);
         var1.getlocal(0).__getattr__("writer").__getattr__("new_alignment").__call__(var2, var1.getlocal(1));
         var1.setline(216);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("align", var3);
         var3 = null;
         var1.setline(217);
         var1.getlocal(0).__getattr__("align_stack").__getattr__("append").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(219);
         var1.getlocal(0).__getattr__("align_stack").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("align"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_alignment$33(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      if (var1.getlocal(0).__getattr__("align_stack").__nonzero__()) {
         var1.setline(223);
         var1.getlocal(0).__getattr__("align_stack").__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.setline(224);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("align_stack").__nonzero__()) {
         var1.setline(225);
         var3 = var1.getlocal(0).__getattr__("align_stack").__getitem__(Py.newInteger(-1));
         var1.getlocal(0).__setattr__("align", var3);
         var1.setlocal(1, var3);
         var1.setline(226);
         var1.getlocal(0).__getattr__("writer").__getattr__("new_alignment").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(228);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("align", var3);
         var3 = null;
         var1.setline(229);
         var1.getlocal(0).__getattr__("writer").__getattr__("new_alignment").__call__(var2, var1.getglobal("None"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_font$34(PyFrame var1, ThreadState var2) {
      var1.setline(232);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(233);
      if (var1.getlocal(0).__getattr__("softspace").__nonzero__()) {
         var1.setline(234);
         PyInteger var6 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"hard_break", var6);
         var1.getlocal(0).__setattr__((String)"para_end", var6);
         var1.getlocal(0).__setattr__((String)"softspace", var6);
         var1.setline(235);
         var6 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"nospace", var6);
         var3 = null;
         var1.setline(236);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_flowing_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
      }

      var1.setline(237);
      if (var1.getlocal(0).__getattr__("font_stack").__nonzero__()) {
         var1.setline(238);
         var3 = var1.getlocal(0).__getattr__("font_stack").__getitem__(Py.newInteger(-1));
         var4 = Py.unpackSequence(var3, 4);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(9, var5);
         var5 = null;
         var3 = null;
         var1.setline(239);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._is(var1.getglobal("AS_IS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(239);
            var3 = var1.getlocal(6);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(240);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("AS_IS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(240);
            var3 = var1.getlocal(7);
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(241);
         var3 = var1.getlocal(4);
         var10000 = var3._is(var1.getglobal("AS_IS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(241);
            var3 = var1.getlocal(8);
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(242);
         var3 = var1.getlocal(5);
         var10000 = var3._is(var1.getglobal("AS_IS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(242);
            var3 = var1.getlocal(9);
            var1.setlocal(5, var3);
            var3 = null;
         }
      }

      var1.setline(243);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)});
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(244);
      var1.getlocal(0).__getattr__("font_stack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(245);
      var1.getlocal(0).__getattr__("writer").__getattr__("new_font").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_font$35(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      if (var1.getlocal(0).__getattr__("font_stack").__nonzero__()) {
         var1.setline(249);
         var1.getlocal(0).__getattr__("font_stack").__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.setline(250);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("font_stack").__nonzero__()) {
         var1.setline(251);
         var3 = var1.getlocal(0).__getattr__("font_stack").__getitem__(Py.newInteger(-1));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(253);
         var3 = var1.getglobal("None");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(254);
      var1.getlocal(0).__getattr__("writer").__getattr__("new_font").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_margin$36(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      var1.getlocal(0).__getattr__("margin_stack").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(258);
      PyObject var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(0).__getattr__("margin_stack"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(259);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(260);
         var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(261);
      var1.getlocal(0).__getattr__("writer").__getattr__("new_margin").__call__(var2, var1.getlocal(1), var1.getglobal("len").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pop_margin$37(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      if (var1.getlocal(0).__getattr__("margin_stack").__nonzero__()) {
         var1.setline(265);
         var1.getlocal(0).__getattr__("margin_stack").__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.setline(266);
      PyObject var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(0).__getattr__("margin_stack"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(267);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(268);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(270);
         var3 = var1.getglobal("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(271);
      var1.getlocal(0).__getattr__("writer").__getattr__("new_margin").__call__(var2, var1.getlocal(2), var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_spacing$38(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("spacing", var3);
      var3 = null;
      var1.setline(275);
      var1.getlocal(0).__getattr__("writer").__getattr__("new_spacing").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject push_style$39(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      if (var1.getlocal(0).__getattr__("softspace").__nonzero__()) {
         var1.setline(279);
         PyInteger var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"hard_break", var3);
         var1.getlocal(0).__setattr__((String)"para_end", var3);
         var1.getlocal(0).__setattr__((String)"softspace", var3);
         var1.setline(280);
         var3 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"nospace", var3);
         var3 = null;
         var1.setline(281);
         var1.getlocal(0).__getattr__("writer").__getattr__("send_flowing_data").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
      }

      var1.setline(282);
      PyObject var5 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(282);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(284);
            var1.getlocal(0).__getattr__("writer").__getattr__("new_styles").__call__(var2, var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("style_stack")));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(283);
         var1.getlocal(0).__getattr__("style_stack").__getattr__("append").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject pop_style$40(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      var1.getlocal(0).__getattr__("style_stack").__delslice__(var1.getlocal(1).__neg__(), (PyObject)null, (PyObject)null);
      var1.setline(288);
      var1.getlocal(0).__getattr__("writer").__getattr__("new_styles").__call__(var2, var1.getglobal("tuple").__call__(var2, var1.getlocal(0).__getattr__("style_stack")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assert_line_data$41(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getlocal(1).__not__();
      var1.getlocal(0).__setattr__("nospace", var3);
      var1.getlocal(0).__setattr__("hard_break", var3);
      var1.setline(292);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"para_end", var4);
      var1.getlocal(0).__setattr__((String)"parskip", var4);
      var1.getlocal(0).__setattr__((String)"have_label", var4);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NullWriter$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Minimal writer interface to use in testing & inheritance.\n\n    A writer which only provides the interface definition; no actions are\n    taken on any methods.  This should be the base class for all writers\n    which do not need to inherit any implementation methods.\n\n    "));
      var1.setline(302);
      PyString.fromInterned("Minimal writer interface to use in testing & inheritance.\n\n    A writer which only provides the interface definition; no actions are\n    taken on any methods.  This should be the base class for all writers\n    which do not need to inherit any implementation methods.\n\n    ");
      var1.setline(303);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$43, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(304);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$44, (PyObject)null);
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(305);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_alignment$45, (PyObject)null);
      var1.setlocal("new_alignment", var4);
      var3 = null;
      var1.setline(306);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_font$46, (PyObject)null);
      var1.setlocal("new_font", var4);
      var3 = null;
      var1.setline(307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_margin$47, (PyObject)null);
      var1.setlocal("new_margin", var4);
      var3 = null;
      var1.setline(308);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_spacing$48, (PyObject)null);
      var1.setlocal("new_spacing", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_styles$49, (PyObject)null);
      var1.setlocal("new_styles", var4);
      var3 = null;
      var1.setline(310);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_paragraph$50, (PyObject)null);
      var1.setlocal("send_paragraph", var4);
      var3 = null;
      var1.setline(311);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_line_break$51, (PyObject)null);
      var1.setlocal("send_line_break", var4);
      var3 = null;
      var1.setline(312);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_hor_rule$52, (PyObject)null);
      var1.setlocal("send_hor_rule", var4);
      var3 = null;
      var1.setline(313);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_label_data$53, (PyObject)null);
      var1.setlocal("send_label_data", var4);
      var3 = null;
      var1.setline(314);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_flowing_data$54, (PyObject)null);
      var1.setlocal("send_flowing_data", var4);
      var3 = null;
      var1.setline(315);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_literal_data$55, (PyObject)null);
      var1.setlocal("send_literal_data", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$43(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$44(PyFrame var1, ThreadState var2) {
      var1.setline(304);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_alignment$45(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_font$46(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_margin$47(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_spacing$48(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_styles$49(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_paragraph$50(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_line_break$51(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_hor_rule$52(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_label_data$53(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_flowing_data$54(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_literal_data$55(PyFrame var1, ThreadState var2) {
      var1.setline(315);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject AbstractWriter$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A writer which can be used in debugging formatters, but not much else.\n\n    Each method simply announces itself by printing its name and\n    arguments on standard output.\n\n    "));
      var1.setline(324);
      PyString.fromInterned("A writer which can be used in debugging formatters, but not much else.\n\n    Each method simply announces itself by printing its name and\n    arguments on standard output.\n\n    ");
      var1.setline(326);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, new_alignment$57, (PyObject)null);
      var1.setlocal("new_alignment", var4);
      var3 = null;
      var1.setline(329);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_font$58, (PyObject)null);
      var1.setlocal("new_font", var4);
      var3 = null;
      var1.setline(332);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_margin$59, (PyObject)null);
      var1.setlocal("new_margin", var4);
      var3 = null;
      var1.setline(335);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_spacing$60, (PyObject)null);
      var1.setlocal("new_spacing", var4);
      var3 = null;
      var1.setline(338);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, new_styles$61, (PyObject)null);
      var1.setlocal("new_styles", var4);
      var3 = null;
      var1.setline(341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_paragraph$62, (PyObject)null);
      var1.setlocal("send_paragraph", var4);
      var3 = null;
      var1.setline(344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_line_break$63, (PyObject)null);
      var1.setlocal("send_line_break", var4);
      var3 = null;
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_hor_rule$64, (PyObject)null);
      var1.setlocal("send_hor_rule", var4);
      var3 = null;
      var1.setline(350);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_label_data$65, (PyObject)null);
      var1.setlocal("send_label_data", var4);
      var3 = null;
      var1.setline(353);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_flowing_data$66, (PyObject)null);
      var1.setlocal("send_flowing_data", var4);
      var3 = null;
      var1.setline(356);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_literal_data$67, (PyObject)null);
      var1.setlocal("send_literal_data", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject new_alignment$57(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      Py.println(PyString.fromInterned("new_alignment(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_font$58(PyFrame var1, ThreadState var2) {
      var1.setline(330);
      Py.println(PyString.fromInterned("new_font(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_margin$59(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      Py.println(PyString.fromInterned("new_margin(%r, %d)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_spacing$60(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      Py.println(PyString.fromInterned("new_spacing(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_styles$61(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      Py.println(PyString.fromInterned("new_styles(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_paragraph$62(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      Py.println(PyString.fromInterned("send_paragraph(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_line_break$63(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      Py.println(PyString.fromInterned("send_line_break()"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_hor_rule$64(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      Py.println(PyString.fromInterned("send_hor_rule()"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_label_data$65(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      Py.println(PyString.fromInterned("send_label_data(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_flowing_data$66(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      Py.println(PyString.fromInterned("send_flowing_data(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_literal_data$67(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      Py.println(PyString.fromInterned("send_literal_data(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DumbWriter$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Simple writer class which writes output on the file object passed in\n    as the file parameter or, if file is omitted, on standard output.  The\n    output is simply word-wrapped to the number of columns specified by\n    the maxcol parameter.  This class is suitable for reflowing a sequence\n    of paragraphs.\n\n    "));
      var1.setline(367);
      PyString.fromInterned("Simple writer class which writes output on the file object passed in\n    as the file parameter or, if file is omitted, on standard output.  The\n    output is simply word-wrapped to the number of columns specified by\n    the maxcol parameter.  This class is suitable for reflowing a sequence\n    of paragraphs.\n\n    ");
      var1.setline(369);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(72)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$69, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(375);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$70, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(379);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_paragraph$71, (PyObject)null);
      var1.setlocal("send_paragraph", var4);
      var3 = null;
      var1.setline(384);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_line_break$72, (PyObject)null);
      var1.setlocal("send_line_break", var4);
      var3 = null;
      var1.setline(389);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_hor_rule$73, (PyObject)null);
      var1.setlocal("send_hor_rule", var4);
      var3 = null;
      var1.setline(396);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_literal_data$74, (PyObject)null);
      var1.setlocal("send_literal_data", var4);
      var3 = null;
      var1.setline(406);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, send_flowing_data$75, (PyObject)null);
      var1.setlocal("send_flowing_data", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$69(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyObject var10000 = var1.getlocal(1);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("stdout");
      }

      PyObject var3 = var10000;
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(371);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("maxcol", var3);
      var3 = null;
      var1.setline(372);
      var1.getglobal("NullWriter").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(373);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$70(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"col", var3);
      var3 = null;
      var1.setline(377);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"atbreak", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_paragraph$71(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, PyString.fromInterned("\n")._mul(var1.getlocal(1)));
      var1.setline(381);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"col", var3);
      var3 = null;
      var1.setline(382);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"atbreak", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_line_break$72(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      var1.getlocal(0).__getattr__("file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(386);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"col", var3);
      var3 = null;
      var1.setline(387);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"atbreak", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_hor_rule$73(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      var1.getlocal(0).__getattr__("file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(391);
      var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, PyString.fromInterned("-")._mul(var1.getlocal(0).__getattr__("maxcol")));
      var1.setline(392);
      var1.getlocal(0).__getattr__("file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(393);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"col", var3);
      var3 = null;
      var1.setline(394);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"atbreak", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_literal_data$74(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      var1.getlocal(0).__getattr__("file").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setline(398);
      PyObject var3 = var1.getlocal(1).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(399);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(400);
         var4 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"col", var4);
         var3 = null;
         var1.setline(401);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(402);
      var3 = var1.getlocal(1).__getattr__("expandtabs").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(403);
      var3 = var1.getlocal(0).__getattr__("col")._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.getlocal(0).__setattr__("col", var3);
      var3 = null;
      var1.setline(404);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"atbreak", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject send_flowing_data$75(PyFrame var1, ThreadState var2) {
      var1.setline(407);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(407);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(408);
         PyObject var10000 = var1.getlocal(0).__getattr__("atbreak");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("isspace").__call__(var2);
         }

         PyObject var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(409);
         var3 = var1.getlocal(0).__getattr__("col");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(410);
         var3 = var1.getlocal(0).__getattr__("maxcol");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(411);
         var3 = var1.getlocal(0).__getattr__("file").__getattr__("write");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(412);
         var3 = var1.getlocal(1).__getattr__("split").__call__(var2).__iter__();

         while(true) {
            var1.setline(412);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(423);
               var3 = var1.getlocal(3);
               var1.getlocal(0).__setattr__("col", var3);
               var3 = null;
               var1.setline(424);
               var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getattr__("isspace").__call__(var2);
               var1.getlocal(0).__setattr__("atbreak", var3);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(6, var4);
            var1.setline(413);
            PyObject var5;
            PyInteger var6;
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(414);
               var5 = var1.getlocal(3)._add(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
               var10000 = var5._ge(var1.getlocal(4));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(415);
                  var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
                  var1.setline(416);
                  var6 = Py.newInteger(0);
                  var1.setlocal(3, var6);
                  var5 = null;
               } else {
                  var1.setline(418);
                  var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
                  var1.setline(419);
                  var5 = var1.getlocal(3)._add(Py.newInteger(1));
                  var1.setlocal(3, var5);
                  var5 = null;
               }
            }

            var1.setline(420);
            var1.getlocal(5).__call__(var2, var1.getlocal(6));
            var1.setline(421);
            var5 = var1.getlocal(3)._add(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(422);
            var6 = Py.newInteger(1);
            var1.setlocal(2, var6);
            var5 = null;
         }
      }
   }

   public PyObject test$76(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyObject var3 = var1.getglobal("DumbWriter").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(429);
      var3 = var1.getglobal("AbstractFormatter").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(430);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(431);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(432);
         if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__nonzero__()) {
            var1.setline(433);
            var3 = var1.getglobal("open").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(1)));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(435);
            var3 = var1.getglobal("sys").__getattr__("stdin");
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(436);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(436);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(441);
            var1.getlocal(2).__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(437);
         PyObject var5 = var1.getlocal(4);
         var10000 = var5._eq(PyString.fromInterned("\n"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(438);
            var1.getlocal(2).__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         } else {
            var1.setline(440);
            var1.getlocal(2).__getattr__("add_flowing_data").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public formatter$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NullFormatter$1 = Py.newCode(0, var2, var1, "NullFormatter", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "writer"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blankline"};
      end_paragraph$3 = Py.newCode(2, var2, var1, "end_paragraph", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      add_line_break$4 = Py.newCode(1, var2, var1, "add_line_break", 43, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kw"};
      add_hor_rule$5 = Py.newCode(3, var2, var1, "add_hor_rule", 44, true, true, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "counter", "blankline"};
      add_label_data$6 = Py.newCode(4, var2, var1, "add_label_data", 45, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      add_flowing_data$7 = Py.newCode(2, var2, var1, "add_flowing_data", 46, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      add_literal_data$8 = Py.newCode(2, var2, var1, "add_literal_data", 47, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush_softspace$9 = Py.newCode(1, var2, var1, "flush_softspace", 48, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "align"};
      push_alignment$10 = Py.newCode(2, var2, var1, "push_alignment", 49, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pop_alignment$11 = Py.newCode(1, var2, var1, "pop_alignment", 50, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      push_font$12 = Py.newCode(2, var2, var1, "push_font", 51, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pop_font$13 = Py.newCode(1, var2, var1, "pop_font", 52, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "margin"};
      push_margin$14 = Py.newCode(2, var2, var1, "push_margin", 53, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      pop_margin$15 = Py.newCode(1, var2, var1, "pop_margin", 54, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "spacing"};
      set_spacing$16 = Py.newCode(2, var2, var1, "set_spacing", 55, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "styles"};
      push_style$17 = Py.newCode(2, var2, var1, "push_style", 56, true, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      pop_style$18 = Py.newCode(2, var2, var1, "pop_style", 57, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      assert_line_data$19 = Py.newCode(2, var2, var1, "assert_line_data", 58, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractFormatter$20 = Py.newCode(0, var2, var1, "AbstractFormatter", 61, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "writer"};
      __init__$21 = Py.newCode(2, var2, var1, "__init__", 75, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blankline"};
      end_paragraph$22 = Py.newCode(2, var2, var1, "end_paragraph", 90, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      add_line_break$23 = Py.newCode(1, var2, var1, "add_line_break", 101, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kw"};
      add_hor_rule$24 = Py.newCode(3, var2, var1, "add_hor_rule", 108, true, true, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "counter", "blankline"};
      add_label_data$25 = Py.newCode(4, var2, var1, "add_label_data", 115, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "counter", "label", "c"};
      format_counter$26 = Py.newCode(3, var2, var1, "format_counter", 127, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "case", "counter", "label", "x", "s"};
      format_letter$27 = Py.newCode(3, var2, var1, "format_letter", 142, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "case", "counter", "ones", "fives", "label", "index", "x", "s"};
      format_roman$28 = Py.newCode(3, var2, var1, "format_roman", 153, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "prespace", "postspace"};
      add_flowing_data$29 = Py.newCode(2, var2, var1, "add_flowing_data", 177, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      add_literal_data$30 = Py.newCode(2, var2, var1, "add_literal_data", 197, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush_softspace$31 = Py.newCode(1, var2, var1, "flush_softspace", 206, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "align"};
      push_alignment$32 = Py.newCode(2, var2, var1, "push_alignment", 213, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "align"};
      pop_alignment$33 = Py.newCode(1, var2, var1, "pop_alignment", 221, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "font", "size", "i", "b", "tt", "csize", "ci", "cb", "ctt"};
      push_font$34 = Py.newCode(2, var2, var1, "push_font", 231, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "font"};
      pop_font$35 = Py.newCode(1, var2, var1, "pop_font", 247, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "margin", "fstack"};
      push_margin$36 = Py.newCode(2, var2, var1, "push_margin", 256, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fstack", "margin"};
      pop_margin$37 = Py.newCode(1, var2, var1, "pop_margin", 263, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "spacing"};
      set_spacing$38 = Py.newCode(2, var2, var1, "set_spacing", 273, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "styles", "style"};
      push_style$39 = Py.newCode(2, var2, var1, "push_style", 277, true, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      pop_style$40 = Py.newCode(2, var2, var1, "pop_style", 286, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      assert_line_data$41 = Py.newCode(2, var2, var1, "assert_line_data", 290, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NullWriter$42 = Py.newCode(0, var2, var1, "NullWriter", 295, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$43 = Py.newCode(1, var2, var1, "__init__", 303, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$44 = Py.newCode(1, var2, var1, "flush", 304, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "align"};
      new_alignment$45 = Py.newCode(2, var2, var1, "new_alignment", 305, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "font"};
      new_font$46 = Py.newCode(2, var2, var1, "new_font", 306, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "margin", "level"};
      new_margin$47 = Py.newCode(3, var2, var1, "new_margin", 307, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "spacing"};
      new_spacing$48 = Py.newCode(2, var2, var1, "new_spacing", 308, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "styles"};
      new_styles$49 = Py.newCode(2, var2, var1, "new_styles", 309, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blankline"};
      send_paragraph$50 = Py.newCode(2, var2, var1, "send_paragraph", 310, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      send_line_break$51 = Py.newCode(1, var2, var1, "send_line_break", 311, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kw"};
      send_hor_rule$52 = Py.newCode(3, var2, var1, "send_hor_rule", 312, true, true, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send_label_data$53 = Py.newCode(2, var2, var1, "send_label_data", 313, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send_flowing_data$54 = Py.newCode(2, var2, var1, "send_flowing_data", 314, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send_literal_data$55 = Py.newCode(2, var2, var1, "send_literal_data", 315, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AbstractWriter$56 = Py.newCode(0, var2, var1, "AbstractWriter", 318, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "align"};
      new_alignment$57 = Py.newCode(2, var2, var1, "new_alignment", 326, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "font"};
      new_font$58 = Py.newCode(2, var2, var1, "new_font", 329, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "margin", "level"};
      new_margin$59 = Py.newCode(3, var2, var1, "new_margin", 332, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "spacing"};
      new_spacing$60 = Py.newCode(2, var2, var1, "new_spacing", 335, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "styles"};
      new_styles$61 = Py.newCode(2, var2, var1, "new_styles", 338, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blankline"};
      send_paragraph$62 = Py.newCode(2, var2, var1, "send_paragraph", 341, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      send_line_break$63 = Py.newCode(1, var2, var1, "send_line_break", 344, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kw"};
      send_hor_rule$64 = Py.newCode(3, var2, var1, "send_hor_rule", 347, true, true, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send_label_data$65 = Py.newCode(2, var2, var1, "send_label_data", 350, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send_flowing_data$66 = Py.newCode(2, var2, var1, "send_flowing_data", 353, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      send_literal_data$67 = Py.newCode(2, var2, var1, "send_literal_data", 356, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DumbWriter$68 = Py.newCode(0, var2, var1, "DumbWriter", 360, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "maxcol"};
      __init__$69 = Py.newCode(3, var2, var1, "__init__", 369, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$70 = Py.newCode(1, var2, var1, "reset", 375, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blankline"};
      send_paragraph$71 = Py.newCode(2, var2, var1, "send_paragraph", 379, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      send_line_break$72 = Py.newCode(1, var2, var1, "send_line_break", 384, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kw"};
      send_hor_rule$73 = Py.newCode(3, var2, var1, "send_hor_rule", 389, true, true, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "i"};
      send_literal_data$74 = Py.newCode(2, var2, var1, "send_literal_data", 396, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "atbreak", "col", "maxcol", "write", "word"};
      send_flowing_data$75 = Py.newCode(2, var2, var1, "send_flowing_data", 406, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "w", "f", "fp", "line"};
      test$76 = Py.newCode(1, var2, var1, "test", 427, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new formatter$py("formatter$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(formatter$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.NullFormatter$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.end_paragraph$3(var2, var3);
         case 4:
            return this.add_line_break$4(var2, var3);
         case 5:
            return this.add_hor_rule$5(var2, var3);
         case 6:
            return this.add_label_data$6(var2, var3);
         case 7:
            return this.add_flowing_data$7(var2, var3);
         case 8:
            return this.add_literal_data$8(var2, var3);
         case 9:
            return this.flush_softspace$9(var2, var3);
         case 10:
            return this.push_alignment$10(var2, var3);
         case 11:
            return this.pop_alignment$11(var2, var3);
         case 12:
            return this.push_font$12(var2, var3);
         case 13:
            return this.pop_font$13(var2, var3);
         case 14:
            return this.push_margin$14(var2, var3);
         case 15:
            return this.pop_margin$15(var2, var3);
         case 16:
            return this.set_spacing$16(var2, var3);
         case 17:
            return this.push_style$17(var2, var3);
         case 18:
            return this.pop_style$18(var2, var3);
         case 19:
            return this.assert_line_data$19(var2, var3);
         case 20:
            return this.AbstractFormatter$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.end_paragraph$22(var2, var3);
         case 23:
            return this.add_line_break$23(var2, var3);
         case 24:
            return this.add_hor_rule$24(var2, var3);
         case 25:
            return this.add_label_data$25(var2, var3);
         case 26:
            return this.format_counter$26(var2, var3);
         case 27:
            return this.format_letter$27(var2, var3);
         case 28:
            return this.format_roman$28(var2, var3);
         case 29:
            return this.add_flowing_data$29(var2, var3);
         case 30:
            return this.add_literal_data$30(var2, var3);
         case 31:
            return this.flush_softspace$31(var2, var3);
         case 32:
            return this.push_alignment$32(var2, var3);
         case 33:
            return this.pop_alignment$33(var2, var3);
         case 34:
            return this.push_font$34(var2, var3);
         case 35:
            return this.pop_font$35(var2, var3);
         case 36:
            return this.push_margin$36(var2, var3);
         case 37:
            return this.pop_margin$37(var2, var3);
         case 38:
            return this.set_spacing$38(var2, var3);
         case 39:
            return this.push_style$39(var2, var3);
         case 40:
            return this.pop_style$40(var2, var3);
         case 41:
            return this.assert_line_data$41(var2, var3);
         case 42:
            return this.NullWriter$42(var2, var3);
         case 43:
            return this.__init__$43(var2, var3);
         case 44:
            return this.flush$44(var2, var3);
         case 45:
            return this.new_alignment$45(var2, var3);
         case 46:
            return this.new_font$46(var2, var3);
         case 47:
            return this.new_margin$47(var2, var3);
         case 48:
            return this.new_spacing$48(var2, var3);
         case 49:
            return this.new_styles$49(var2, var3);
         case 50:
            return this.send_paragraph$50(var2, var3);
         case 51:
            return this.send_line_break$51(var2, var3);
         case 52:
            return this.send_hor_rule$52(var2, var3);
         case 53:
            return this.send_label_data$53(var2, var3);
         case 54:
            return this.send_flowing_data$54(var2, var3);
         case 55:
            return this.send_literal_data$55(var2, var3);
         case 56:
            return this.AbstractWriter$56(var2, var3);
         case 57:
            return this.new_alignment$57(var2, var3);
         case 58:
            return this.new_font$58(var2, var3);
         case 59:
            return this.new_margin$59(var2, var3);
         case 60:
            return this.new_spacing$60(var2, var3);
         case 61:
            return this.new_styles$61(var2, var3);
         case 62:
            return this.send_paragraph$62(var2, var3);
         case 63:
            return this.send_line_break$63(var2, var3);
         case 64:
            return this.send_hor_rule$64(var2, var3);
         case 65:
            return this.send_label_data$65(var2, var3);
         case 66:
            return this.send_flowing_data$66(var2, var3);
         case 67:
            return this.send_literal_data$67(var2, var3);
         case 68:
            return this.DumbWriter$68(var2, var3);
         case 69:
            return this.__init__$69(var2, var3);
         case 70:
            return this.reset$70(var2, var3);
         case 71:
            return this.send_paragraph$71(var2, var3);
         case 72:
            return this.send_line_break$72(var2, var3);
         case 73:
            return this.send_hor_rule$73(var2, var3);
         case 74:
            return this.send_literal_data$74(var2, var3);
         case 75:
            return this.send_flowing_data$75(var2, var3);
         case 76:
            return this.test$76(var2, var3);
         default:
            return null;
      }
   }
}
